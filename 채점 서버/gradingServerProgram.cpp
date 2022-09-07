#include <stdio.h>
#include <stdlib.h>
#include <vector>
#include <string>
#include <iostream>
#include <mysql/mysql.h>
#include <typeinfo>
#include <fstream>
#include <time.h>
#include <fcntl.h>
#include <unistd.h>
#include <wait.h>
#include <sys/resource.h>
#include <thread>
#include <mutex>

using namespace std;

#define MEMORY_LIMIT_EXCEEDED -4
#define TIME_LIMIT_EXCEEDED -3
#define RUNTIME_ERROR -2
#define COMPILE_ERROR -1
#define WRONG_ANSWER 0
#define ANSWER_CORRECT 1
#define PROCEEDING 2

mutex m;


void initMysql(MYSQL* mysql, MYSQL_RES* res, MYSQL_ROW* row){
    
    
    mysql_init(mysql);

    if (!mysql_real_connect(mysql, NULL, "", "", "", , (char*)NULL, 0))
    {
        printf("%s\n", mysql_error(mysql));
        exit(1);
    }

    printf("\n\n");    
    printf("-- 데이터베이스 연결 성공--\n");

    if (mysql_query(mysql, "USE onlineJudge")){
        printf("%s\n", mysql_error(mysql));
        exit(1);
    }

    printf("-- onlineJudge 데이터 베이스 연결 성공--\n");
}

typedef struct SubmissionData{
    int submission_id;
    int problem_id;
    int gradingResult;
    int time_limit;
    int memory_limit;
    int runningTime;
    int memoryUsage;
    string code;
}SubmissionData;


SubmissionData getSubmissionDataFromRow(const MYSQL_ROW & row){
    return {stoi(row[0]), stoi(row[1]), PROCEEDING, stoi(row[3]), stoi(row[4]), 0, 0, string(row[2])};
}


void printGradingResult(const int & code){
    if(code == TIME_LIMIT_EXCEEDED) printf("시간 초과!");
    else if(code == MEMORY_LIMIT_EXCEEDED) printf("메모리 초과!"); 
    else if(code == RUNTIME_ERROR) printf("런타임 에러");
    else if(code == COMPILE_ERROR) printf("컴파일 에러"); 
    else if(code == PROCEEDING) printf("채점중....");
    else if(code == WRONG_ANSWER) printf("틀렸습니다!");
    else if(code == ANSWER_CORRECT) printf("맞았습니다!");
}


void printSubmissionData(const SubmissionData & data){
    printf("submitId : %d, problemId : %d ", data.submission_id, data.problem_id);
    printf(" state : "); 
    printGradingResult(data.gradingResult);
    printf(" runningTime : %d ms, memoryUsage : %d kb", data.runningTime, data.memoryUsage);
    printf("\n");
}

void printSubmissionQueue(const vector<SubmissionData> & queue){
    int size = queue.size();
    printf("\n\n----- 채점 큐-----\n\n");
    for(int i=0;i<size;i++) printSubmissionData(queue[i]);
    printf("\n------------------\n\n");
}

void pushSubmissionsToQueue(vector<SubmissionData> & queue, MYSQL_RES * res){
    MYSQL_ROW row;
    while((row = mysql_fetch_row(res))) 
	queue.push_back(getSubmissionDataFromRow(row));
}

void makeSubmittedCodeFile(const string & code){
    ofstream fout("submittedCode.cpp");
    fout << code;
    fout.close();
}

int compile(){
    return system("gcc -o submittedCode.out submittedCode.cpp");
}

string getInputFilePath(const string & problem_id, const string & num){
    string path = "/home/ubuntu/files/testcases/" + problem_id + "/input" + num + ".txt";
    return path;
}


string getOutputFilePath(const string & problem_id, const string & num){
    string path = "/home/ubuntu/files/testcases/" + problem_id + "/output" + num + ".txt";
    return path;
}

int isEndOfInputFile(const string & path){
    ifstream fin(path);
    int ret = !fin;
    fin.close();
    return ret;
}


int run(const int & problem_id, const int & num, long int & runningTime, long int & memoryUsage, const int & time_limit, const int & memory_limit, string inputFilePath){  
    // 구현 해야 할 것 : 시간제한, 메모리 제한, 런타임 에러 
    if(isEndOfInputFile(inputFilePath)) return -1;
    
    struct rusage usage;
    int pid = fork();
    
    if(pid == 0){
	const char *programPath = "./submittedCode.out";
	freopen(inputFilePath.c_str(), "r", stdin);  
	freopen("userAnswer.txt", "w", stdout);  
	freopen("error.txt", "a+", stderr);

	// 시간 제한
	struct rlimit limit = { time_limit, time_limit };
	setrlimit(RLIMIT_CPU, &limit);
	
	// 메모리 제한
	limit = { memory_limit*1024*1024, memory_limit*1024*1024};
	setrlimit(RLIMIT_DATA, &limit);

	int a = execl(programPath, programPath, (char *)NULL);
	exit(1);
    }
    else{
	int status = 0;
        wait4(0, &status, 0, &usage);
        
	struct timeval *tv = &usage.ru_utime;
	memoryUsage = max(memoryUsage, usage.ru_maxrss);
	runningTime = max(runningTime, (long int)(1e-3 * tv->tv_usec));

	int exitcode = WEXITSTATUS(status);

	if(exitcode == 0x05 || exitcode == 0) printf("정상 수행// ");	
	else printf("비정상 종료");

	if(WIFSIGNALED(status)){
	    int signal = WTERMSIG(status);
	    if(signal == 8) return RUNTIME_ERROR;
	    else if(signal == 9) return TIME_LIMIT_EXCEEDED;
	    else if(signal == 11) return MEMORY_LIMIT_EXCEEDED;
	}
    }
    
    return 1;
}

int compareToAnswer(const int & problem_id, const int & num, const string & outputFilePath){
    
    ifstream userResultStream("userAnswer.txt");
    ifstream answerStream(outputFilePath);
    
    string answer;
    string userAnswer;
    
    while(std::getline(answerStream, answer)){
	if(!std::getline(userResultStream, userAnswer)) return WRONG_ANSWER;
	if(answer != userAnswer){
	    printf("%d 번째 테스트케이스, ",num);
	    return WRONG_ANSWER;
	}
	printf("%d 번째 테스트케이스, ",num);
    }

    return ANSWER_CORRECT;
}

void grading(vector<SubmissionData> & queue){
    int size = queue.size();
    for(int i=0;i<size;i++){
	int inputFileNum = 0;
	int gradingResult = 0;
	int resultCode = 0;
	long int runningTime = 0;
	long int memoryUsage = 0;

	int submission_id = queue[i].submission_id;
	int problem_id = queue[i].problem_id;
	string code = queue[i].code;

	printf("채점번호 %d // 채점 시작\n", submission_id); 
	
	makeSubmittedCodeFile(code);
	if(compile()){
	    // 1이 반환된 경우 컴파일 실패
	    queue[i].gradingResult = COMPILE_ERROR;
	    continue;
	}
	
	// 데이터 채점
	while(1){ 
	   ++inputFileNum;
	   string inputFilePath = getInputFilePath(std::to_string(problem_id), std::to_string(inputFileNum));
	   string outputFilePath = getOutputFilePath(std::to_string(problem_id), std::to_string(inputFileNum));
	   
	   int resultCode = run(problem_id, inputFileNum, runningTime, memoryUsage, queue[i].time_limit, queue[i].memory_limit, inputFilePath);   
	   if(resultCode < 0){
	       if(resultCode != -1) gradingResult = resultCode;
	       printGradingResult(gradingResult);
	       printf("\n");
	       break;	
	   }
	
	   // 유저가 제출한 소스코드의 결과와 정답을 비교
	   gradingResult = compareToAnswer(problem_id, inputFileNum, outputFilePath);
	   printGradingResult(gradingResult);
	   printf(", 실행시간 : %ld ms 메모리 사용: %ld kb\n",runningTime, memoryUsage);
	   if(gradingResult == WRONG_ANSWER) break;
	}
	printf("\n\n");

	// 채점 결과 저장
	queue[i].gradingResult = gradingResult;
	queue[i].runningTime = runningTime;
	queue[i].memoryUsage = memoryUsage;
    }    
}

void makeSocket(mutex & m){
    printf("-- 웹 소켓 초기화 --\n");




}

void updateSubmissionState(){


}


int main()
{
    thread socketThread;
    socketThread = thread(makeSocket);
    socketThread.join();

    MYSQL mysql;
    MYSQL_RES* res;
    MYSQL_ROW row;

    //mysql 객체들  초기화    
    initMysql(&mysql, res, &row);
    
    int testcase = 1;
    while(testcase--){
	// 제출 목록을 담고 있는 벡터 
	vector<SubmissionData> queue;
	mysql_query(&mysql, "select submission_id, submission.problem_id, code, problem.time_limit, problem.memory_limit from submission inner join problem on submission.problem_id = problem.problem_id;");
	res = mysql_store_result(&mysql);
	
	pushSubmissionsToQueue(queue, res);
	
	printSubmissionQueue(queue);
	grading(queue);
	printSubmissionQueue(queue);

	updateSubmissionState();

    }  
    mysql_close(&mysql);
    return 0;
}
