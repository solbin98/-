#include <stdio.h>
#include <stdlib.h>
#include <vector>
#include <string>
#include <iostream>
#include <mysql/mysql.h>
#include <typeinfo>
#include <fstream>

using namespace std;

#define RUNTIME_ERROR -2
#define COMPILE_ERROR -1
#define WRONG_ANSWER 0
#define ANSWER_CORRECT 1
#define PROCEEDING 2

void initMysql(MYSQL* mysql, MYSQL_RES* res, MYSQL_ROW* row){
    mysql_init(mysql);

    if (!mysql_real_connect(mysql, NULL, "", "", "", , (char*)NULL, 0))
    {
        printf("%s\n", mysql_error(mysql));
        exit(1);
    }
    
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
	string code;
}SubmissionData;


void printSubmissionData(SubmissionData & data){
    printf("submitId : %d, problemId : %d\n", data.submission_id, data.problem_id);
}

void printSubmissionQueue(vector<SubmissionData >& queue){
    int size = queue.size();
    printf("----- 채점 큐-----\n");
    for(int i=0;i<size;i++){
	printSubmissionData(queue[i]);
    }
}

SubmissionData getSubmissionDataFromRow(MYSQL_ROW row){
       return {stoi(row[0]), stoi(row[1]), PROCEEDING, string(row[2])};
}

void setSubmissionQueue(vector<SubmissionData> & queue, MYSQL_RES * res){
    MYSQL_ROW row;
    while((row = mysql_fetch_row(res))){
	queue.push_back(getSubmissionDataFromRow(row));
    }
}

void makeSubmittedCodeFile(string & code){
    //cout << code << endl;
    ofstream fout("submittedCode.cpp");
    fout << code;
    fout.close();
}

void compile(){
    system("gcc -o submittedCode.out submittedCode.cpp");
}

string getInputFilePath(string problem_id, string num){
    string path = "/home/ubuntu/files/testcases/" + problem_id + "/input" + num + ".txt";
    return path;
}

int isEndOfInputFile(string path){
    ifstream fin(path);
    int ret = !fin;
    fin.close();
    return ret;
}

int run(int & problem_id, int & num, string inputFilePath){
    if(isEndOfInputFile(inputFilePath)) return -1;
    string scommand = "./submittedCode.out < " + inputFilePath + " > userAnswer.txt";
    const char * command = scommand.c_str();
    system(command);
    return 1;
}

int compareToAnswer(int & problem_id, int & num, string inputFilePath){
    ifstream userResultStream("userAnswer.txt");
    ifstream answerStream(inputFilePath);
    
    if(!answerStream){
	cout << "파일 못찾음" << endl;
    }

    string answer;
    string userAnswer;
    
    while(std::getline(answerStream, answer)){
	if(!std::getline(userResultStream, userAnswer)) return WRONG_ANSWER;
	cout << "answer : " << answer << " userAnswer : " << userAnswer << endl;
	if(answer != userAnswer) return WRONG_ANSWER;
    }

    return ANSWER_CORRECT;
}

void printGradingResult(int code){
    if(code == WRONG_ANSWER) printf("틀렸습니다.!\n");
    if(code == ANSWER_CORRECT) printf("맞았습니다.!\n");
}

void grading(vector<SubmissionData> & queue){
    int size = queue.size();
    for(int i=0;i<size;i++){
	makeSubmittedCodeFile(queue[i].code);
	compile();
	
	int inputFileNum = 0;
	int problem_id = queue[i].problem_id;
	int resultCode = 0;
	int gradingResult = 0;
	do{ 
	   inputFileNum++;
	   string inputFilePath = getInputFilePath(std::to_string(problem_id), std::to_string(inputFileNum));
	   resultCode = run(problem_id, inputFileNum, inputFilePath);
	   gradingResult = compareToAnswer(problem_id, inputFileNum, inputFilePath);
	   
	   printGradingResult(gradingResult);
	}
	while(resultCode > 0);

    }	
}


int main()
{
    MYSQL mysql;
    MYSQL_RES* res;
    MYSQL_ROW row;

    //mysql 객체들  초기화    
    initMysql(&mysql, res, &row);
    
    int testcase = 1;
    while(testcase--){
	// 제출 목록을 담고 있는 벡터 
	vector<SubmissionData> queue;

	mysql_query(&mysql, "select submission_id, problem_id, code from submission");
	res = mysql_store_result(&mysql);
	
	setSubmissionQueue(queue, res);
	printSubmissionQueue(queue);
	grading(queue);

    }  
    mysql_close(&mysql);
    return 0;
}
