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
#include <arpa/inet.h>
#include <sys/types.h>
#include <sys/un.h>
using namespace std;

#define MEMORY_LIMIT_EXCEEDED -4
#define TIME_LIMIT_EXCEEDED -3
#define RUNTIME_ERROR -2
#define COMPILE_ERROR -1
#define WRONG_ANSWER 0
#define ANSWER_CORRECT 1
#define PROCEEDING 2
#define PORT 9001
#define MAXLINE 100

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

void sendMessageToWebSocket(const char * message){
    int sockfd; 
    char buffer[MAXLINE]; 
    struct sockaddr_in servaddr; 
    
    if ((sockfd = socket(AF_INET, SOCK_DGRAM, 0)) < 0 ) { 
        perror("socket creation failed"); 
        exit(EXIT_FAILURE); 
    } 
    
    memset(&servaddr, 0, sizeof(servaddr)); 
        
    servaddr.sin_family = AF_INET; 
    servaddr.sin_port = htons(PORT); 
    servaddr.sin_addr.s_addr = INADDR_ANY; 
        
    int n, len; 
        
    sendto(sockfd, (const char *)message, strlen(message), 
        MSG_CONFIRM, (const struct sockaddr *) &servaddr,  
            sizeof(servaddr)); 
            
    close(sockfd);  
}

void grading(vector<SubmissionData> & queue){
    int size = queue.size();
    for(int i=0;i<size;i++){
	int inputFileNum = 0;
	int gradingResult = 1;
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
	    gradingResult = COMPILE_ERROR;
	    queue[i].gradingResult = gradingResult;
	}
	
	// 데이터 채점
	while(gradingResult > 0){ 
	   ++inputFileNum;
	   string inputFilePath = getInputFilePath(std::to_string(problem_id), std::to_string(inputFileNum));
	   string outputFilePath = getOutputFilePath(std::to_string(problem_id), std::to_string(inputFileNum));
	   
	   int resultCode = run(problem_id, inputFileNum, runningTime, memoryUsage, queue[i].time_limit, queue[i].memory_limit, inputFilePath);   
	   if(resultCode < 0){ // 런타임 에러, 메모리 초과, 시간 초과 등등의 경우 음수가 반환됨, 그대로 에러코드로 출력하기.
	       if(resultCode != -1) gradingResult = resultCode;
	       printGradingResult(gradingResult);
	       printf("\n");
	       break;	
	   }
	
	   // 유저가 제출한 소스코드의 결과와 정답을 비교
	   gradingResult = compareToAnswer(problem_id, inputFileNum, outputFilePath);
	   printGradingResult(gradingResult);
	   printf(", 실행시간 : %ld ms 메모리 사용: %ld kb\n",runningTime, memoryUsage);
	   sendMessageToWebSocket((std::to_string(gradingResult) + ":"  + std::to_string(submission_id) + ":" + std::to_string(inputFileNum)).c_str());
	}
	printf("\n\n");


	sendMessageToWebSocket((std::to_string(gradingResult) + ":"  + std::to_string(submission_id) + ":" + std::to_string(inputFileNum) + ":" + std::to_string(runningTime) + ":" + std::to_string(memoryUsage)).c_str());

	// 채점 결과 저장
	queue[i].gradingResult = gradingResult;
	queue[i].runningTime = runningTime;
	queue[i].memoryUsage = memoryUsage;
    }    
}

string getStringStateCode(int code){
    if(code == TIME_LIMIT_EXCEEDED) return "TLE";
    else if(code == MEMORY_LIMIT_EXCEEDED) return "MLE";
    else if(code == RUNTIME_ERROR) return "RE";
    else if(code == COMPILE_ERROR) return "CE"; 
    else if(code == PROCEEDING) return "PC";
    else if(code == WRONG_ANSWER) return "WA";
    else if(code == ANSWER_CORRECT) return "AC";
}

void updateSubmissionState(const vector<SubmissionData> & queue, MYSQL & mysql){
    for(int i=0;i<queue.size();i++){
	string sid = std::to_string(queue[i].submission_id);
	string stateCode = getStringStateCode(queue[i].gradingResult);
	string runningTime = std::to_string(queue[i].runningTime);
	string memoryUsage = std::to_string(queue[i].memoryUsage);

	string query = "update submission set state = \"" + stateCode  + "\", time = "+ runningTime + ", memory =  " + memoryUsage + " where submission_id = " + sid + "\0"; 
	cout << query << endl;
	int ret = mysql_query(&mysql, query.c_str());
	printf("ret : %d\n",ret);
    }
}


int main()
{
    MYSQL mysql;
    MYSQL_RES* res;
    MYSQL_ROW row;

    //mysql 객체들  초기화    
    initMysql(&mysql, res, &row);
    
    int testcase = 10000000;
    while(testcase--){
	// 제출 목록을 담고 있는 벡터 
	vector<SubmissionData> queue;
	mysql_query(&mysql, "select submission_id, submission.problem_id, code, problem.time_limit, problem.memory_limit from submission inner join problem on submission.problem_id = problem.problem_id where state = \"PC\" order by submission_id desc");
	res = mysql_store_result(&mysql);
	
	pushSubmissionsToQueue(queue, res);
	
	printSubmissionQueue(queue);
	grading(queue);
	printSubmissionQueue(queue);

	updateSubmissionState(queue, mysql);
	sleep(1);
    }  
    mysql_close(&mysql);
    return 0;
}
