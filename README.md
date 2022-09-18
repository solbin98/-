# 💪 Brogrammers

![test](https://user-images.githubusercontent.com/76681977/190908548-eae20c4d-2564-456f-80a2-5390b156425b.gif)

## 프로젝트 소개


기존에 존재하는 일반적인 알고리즘 문제풀이 사이트들 ( Backjoon Online Judge, Programmers, Code

Force .. 등등 ) 과 같이, 온라인 소스코드 채점 시스템 구현을 목표로 하는 개인 토이 프로젝트입니다.

<br>

## 프로젝트 목적


* 문제 조회, 질문 및 답변, 제출 현황 조회, 랭킹 조회 등과 같은 일반적인 웹 사이트로서의 기능 구현

* 사용자가 제출한 소스 코드를 실행하고 정답과 비교하여 결과를 실시간으로 사용자에게 전달해주는 

  채점 시스템 구현

<br>

## 프로젝트 구현

### 💻 시스템 구성

![시스템 구성도-1](https://user-images.githubusercontent.com/76681977/190891757-c0800f43-cf0a-483e-a0a5-dc847b9e7b5f.png)

### 💾 데이터 베이스 ERD

![데이터 베이스](https://user-images.githubusercontent.com/76681977/190892825-f9aa137a-a06b-41a5-91fe-31b7c7152c59.png)


### 📂 프로젝트 디렉터리



![프로젝트 디렉터리](https://user-images.githubusercontent.com/76681977/190892968-765ebba8-2e49-4cb1-8913-6b1aeed2c87e.png)

* 서버단 디렉터리 구성

![프로젝트 디렉터리 - 프론트](https://user-images.githubusercontent.com/76681977/190892986-c6eaf3b8-5f21-4a5d-8de8-985af0f53904.png)

* 화면단 디렉터리 구성


### 🔨 구현된 기능 목록

#### 🌏 웹 서버

1. 로그인 

2. 회원가입 ( 이메일 인증 방식 )

3. 문제 목록 조회

4. 문제에 대한 ( 조회, 추가 )

5. 소스코드 제출

6. 채점 현황 조회 ( 채점 % 실시간 조회, 채점 결과 조회 )

7. 질문 & 답변에 대한 ( 조회, 수정, 삭제, 추가 )

8. 유저 랭킹 조회

9. 개인 프로필 조회


#### 💯 채점 서버

1. 제출된 소스코드 채점

2. 채점 과정 & 결과 실시간 전송 

<br>

## 📝 구현된 기능 설명

### 🌏 웹 서버

## 로그인 기능 

프로젝트에서의 인가 및 인증은 Spring FrameWork 의 하위 프레임워크인 Spring Security를 활용해서

진행했습니다.

<br>

Security 에서 제공하는 form-login 기능을 사용했으며, 인증 및 인가는 세션 기반으로 동작합니다.

form-login 방식 인증 로직을 수행하기 위해 필요한 PrincialDetails / PrincipalDetailsService / User /

SuccessHandler / FailureHandler 클래스를 구현했습니다.

<br>

사용자 인증이 필요한 페이지와 요청에 대하여 인증 여부를 검사하도록 설정했습니다. 예를 들어 관리자의

권한이 요구되는 페이지 (문제 출제 등) 에 대하여 ROLE_ADMIN 을 가진 사용자만 접근 가능하도록 했고, 

질문 작성 페이지에 접근하기 위해서는 ROLE_ADMIN 또는 ROLE_USER 권한을 가져야 합니다. 

<br>


인증에 실패했을 경우 호출되는 failureHandler 를 커스텀으로 구현해서, 로그인이 실패한 이유를 

error 타입에 따라 분기하여 사용자에게 전달하도록 구현했습니다.

<br>

Security jsp 태그를 사용하여 인증 여부에 따라 다른 UI 를 구현했습니다.

인증된 사용자에게는 로그아웃 메뉴가 보이고, 인증되지 않은 사용자들에게는 로그인 및 회원가입 

메뉴가 보이는 등등의 동작을 수행할 때, Security jsp 태그를 활용하는 식입니다.

## 사진

<br>

![로그인 화편](https://user-images.githubusercontent.com/76681977/190894198-f9d35370-4747-4f5c-a600-dc4674951fc9.png)

* 로그인 화면

![로그인 실패 화면](https://user-images.githubusercontent.com/76681977/190894389-d9578493-3552-41eb-bc4e-583505c25bee.png)

* 로그인 실패 화면

<br>

![시큐리티 적용-1](https://user-images.githubusercontent.com/76681977/190894427-cf66123a-a829-4f6d-869e-3aecd90a70be.png)

* 로그인이 안됬으므로 답변 작성 불가능

![시큐리티 적용-2](https://user-images.githubusercontent.com/76681977/190895220-c74fd34f-254c-472b-b9c6-19766f47858a.png)

* 로그인 안된 사용자

![시큐리티 적용-3](https://user-images.githubusercontent.com/76681977/190895231-2194f9d1-d3f7-4e17-96eb-cf19637e1579.png)

* 로그인 된 사용자

<br>

## 회원가입 기능

회원가입 폼에서는 아이디, 비밀번호, 닉네임, 이메일 주소, 자기소개에 대한 정보를 입력받습니다.

<br>

회원 가입의 데이터 검증은 Spring Framework 에서 제공하는 @Valid 어노테이션과 BindingResult 클래스

를 활용하여 공백 여부와 올바른 이메일 형식인지를 우선적으로 검사합니다. 추후에 Service 로직을 

호출하여 검증이 필요한 부분들, 예를 들면 중복된 사용자가 있는지 등의 여부를 검사하는 로직은 

Spring Framework 의 Validator 클래스를 상속받아 구현한 커스텀 Validator 를 통해 검사합니다. 

마지막으로 bindingResult 객체를 통해 에러 존재 여부를 검사해서, 에러가 존재하는 경우 

다시 회원가입 페이지로 이동하는데, 이때 bindingResult 객체와 연동이 가능한 error 태그를 활용해서

에러 내용을 출력하도록 구현했습니다.

<br>

회원이 본인임을 인증하기 위한 수단으로는 이메일 인증을 선택했습니다.

사용자가 이메일 전송 요청을 보내면, 서버에서는 6자리의 난수(인증코드)를 생성합니다.

입력한 주소로 이메일 인증 코드를 전송하고 email_auth 라는 테이블에 발급한 인증 코드를 저장해둡

니다. 사용자가 인증 코드를 입력하면 서버에서는 사용자가 입력한 인증 코드와 email_auth  테이블에

저장된 인증 코드를 비교해서, 올바른 인증 코드인 경우 인증 완료 여부를 체크해줍니다. 이것은 

추후에 커스텀 Validator 클래스에서도 검사되는 부분입니다.


## 사진

<br>

![회원가입 화면](https://user-images.githubusercontent.com/76681977/190895829-c3e3969b-d105-457a-9fca-678341a13496.png)

* 회원가입 화면

![회원가입 실패 화면](https://user-images.githubusercontent.com/76681977/190895834-de390844-dfdc-4717-a4c9-ff0689ffcf0e.png)

* 회원가입 실패시 화면

![이메일 인증 - 전송 버튼](https://user-images.githubusercontent.com/76681977/190895840-62413d7e-03f3-4a02-8a15-bc157cb5f14e.png)

* 이메일 전송하기

![이메일 도착](https://user-images.githubusercontent.com/76681977/190895843-ad45f331-212c-4c42-ab9e-9039b1d6c5ba.png)

* 이메일 도착한 모습

![이메일 인증 완료](https://user-images.githubusercontent.com/76681977/190895849-ee8ab081-791e-403b-8a35-6fc43d0ae65a.png)

* 이메일 인증 성공


<br>

## 문제 목록 조회

사용자는 메뉴 탭에서, '문제' 라는 메뉴를 선택해서 문제 목록을 조회할 수 있습니다.

모든 문제를 보여주지 않고, 한 페이지에 10개의 문제를 보여주도록 페이징을 적용했습니다.

## 사진

<br>

![문제 리스트 페이지](https://user-images.githubusercontent.com/76681977/190896285-19cf16ed-795c-4894-8e82-dbde38461190.png)


<br>

## 문제 조회 및 추가

사용자는 문제의 세부 페이지를 조회할 수 있습니다. 문제 세부 페이지의 하단에 있는 제출하기 

버튼을 눌러서 소스코드를 제출할 수 있습니다.

<br>

관리자는 문제 출제 메뉴를 클릭해서 문제를 제작하는 페이지로 이동할 수 있습니다.

문제의 본문을 작성하기 위한 툴로, Toast Editor 3.0 을 사용했습니다. toast 에디터와 더불어 

네이버 스마트 에디터를 조사했었는데, 2022년 11월 부로 서비스를 종료한다는 공지도 있었고, 

markdown 형식의 에디터를 사용하고 싶어서 Toast Editor로 결정했습니다.

<br>

Toast Editor의 이미지 첨부 기능을 구현하기 위해서는 editor 객체의 hook 프로퍼티에 있는

addImageBlobHook 함수를 구현해야 했습니다. 해당 함수는 에디터에 이미지를 추가할 때마다 

호출되는 함수입니다. 해당 함수의 인자로 이미지 파일을 받을 수 있습니다. 이것을 배열이나 

리스트 같은 곳에 저장해두었다가, 문제 출제 요청을 보냄과 동시에 서버로 전송 할 수도 있겠

지만, 사용자 입장에서 이미지가 서버에 정상적으로 전송되었는지를 첨부 직후에 바로 확인할 수

있도록 하기 위해서 해당 함수가 호출됨과 동시에 서버로 이미지를 전송하고, 서버에서는 

이미지를 저장하도록 구현했습니다. 서버에 비동기적으로 요청을 보내주기 위한 수단으로는 

ajax 를 활용했습니다.

<br>

이미지가 전송되면, 서버측에 구현한 ImageController 가 요청을 받고, 전송받은 이미지를 

서버 로컬 저장소에 저장합니다. 이때, 저장되는 이미지의 파일 이름은 충돌을 피하기 위해 

uuid를 생성하여 붙인 다음 저장합니다. 이후에는 이미지의 정보를 담은 

file 테이블에 삽입하기 위한 row를 만듭니다. 이 row 에는 used 라는 tinyint 타입의 칼럼이

포함됩니다. 이 칼럼은 이미지가 실제로 사용되고 있는 이미지인지, 아니면 더 이상 사용되지

않는 이미지 인지를 구분하기 위해 존재합니다. ( 사용 되지 않는

이미지란, 그 이미지가 포함된 게시글이 삭제되었거나 그 이미지를 한번 첨부하긴 했는데 

최종적으로는 첨부하지 않는다거나, 게시글을 작성하던 사용자가 페이지를 벗어나 버린다음 

게시글 작성을 그만둔다는지 하는 모든 동작에서 생긴 쓰레기 파일을 의미합니다. )

<br>

일단 used 라는 칼럼은 모두 0 으로 설정됩니다. 그렇게 file 테이블에 used 가 0 인 row 를

저장함과 동시에, 삽입된 row 의 고유 키인 file_id 값을 클라이언트에게 건네줍니다. 

클라이언트는 이 file_id 값과 이미지 파일의 이름을 한쌍으로 allImage 라는 리스트에 추가

합니다. 이후에 클라이언트가 최종적으로 문제 작성 요청 버튼을 누르면, 에디터에서 html 

코드를 읽어와서 html 코드에 존재하는 모든 이미지 파일 이름을 추출합니다. 그렇게 추출한

이미지 파일 이름들을 바탕으로 allImage 에서 일치하는 쌍을 찾아 거기서 file_id 를 가져와서

하나의 리스트에 저장합니다. 최종적으로, 사용하는 이미지의 file_id 가 담긴 그 리스트가 

서버까지 전송됩니다. 서버에서는 이 리스트에 있는 file_id 값들을 가진 file 테이블 속

row 들의 used 칼럼을 1로 설정합니다. 

<br>

used 칼럼이 0 인 이 이미지들의 삭제는 매일 새벽 3시에 Spring Scheduler 로 구현한 

스케쥴러에 의해 진행됩니다. 또한 생성된지 약 하루가 지난 이미지 파일들만 삭제하는데,

이는 새벽 3시경에 게시글을 작성하고 있는 사용자가 첨부한 이미지 파일들이 게시글 

작성 완료 이전에 삭제되버릴 수가 있기 때문입니다.

<br>

입력 및 출력 테스트케이스 데이터는 txt 파일로 첨부하여 서버로 전달합니다. 이를 위해

클라이언트에서는 js의 formData 클래스를 활용하고 서버측에서는 spring framework 의 

MultipartFile 클래스를 활용했습니다.


## 사진

<br>

![문제 페이지-1](https://user-images.githubusercontent.com/76681977/190896318-2cb6563e-335e-4150-8f27-910f5ee69c8d.png)
![문제 페이지-2](https://user-images.githubusercontent.com/76681977/190896319-341f35d1-e826-4032-8408-6037bcab76e8.png)
* 문제 세부 정보 페이지

![문제 출제 페이지-1](https://user-images.githubusercontent.com/76681977/190899786-fa7720ba-609f-4129-bd56-adac640a7bdd.png)
![문제 출제 페이지-2](https://user-images.githubusercontent.com/76681977/190899788-8aaf80c9-47a2-49f0-8415-3ca2ba8e30fb.png)
![문제 출제 페이지-3](https://user-images.githubusercontent.com/76681977/190899790-d8a15caa-1348-44ee-b65f-e3e8cf587b49.png)
* 문제 출제 페이지

![이미지 첨부](https://user-images.githubusercontent.com/76681977/190899870-263974ee-1e26-4999-ac3c-f27118707d7f.png)
* 이미지 첨부


<br>

## 소스코드 제출 및 채점 현황 조회

문제 세부 조회 페이지의 하단에 있는 제출하기 버튼을 누르면 소스코드 제출 페이지로 넘어갑니다.

제출 언어로는 C 와 C++ 를 지원합니다.

문제를 제출하면, 채점 현황 페이지로 넘어가게 됩니다. 이곳에서 채점 결과를 조회할 수 있습니다.

## 사진

![제출 페이지](https://user-images.githubusercontent.com/76681977/190900246-a396ec09-28bc-48c5-893c-6c82461456ec.png)
* 소스코드 제출 페이지

![제출 현황 페이지](https://user-images.githubusercontent.com/76681977/190900248-c5544d10-a0a2-4d6a-a505-7ce8d4a1c69a.png)
* 채점 현황 페이지

<br>

## 질문 & 답변에 대한 ( 조회, 수정, 추가, 삭제 ) / 랭킹 페이지 조회 / 개인 프로필 조회

사용자는 질답 게시판 메뉴 탭을 클릭해서 질답 게시판 페이지로 이동할 수 있습니다.

질답 게시판에서 사용하는 이미지 처리 로직은 문제 제작 페이지에서 사용하는 그것과 같습니다.

사용자는 마음에 드는 질문 게시글에 추천을 누를 수 있습니다.

랭킹 페이지에서는 사용자들의 순위를 확인할 수 있습니다. 맞힌 문제 수를 기준으로 내림

차순으로 정렬됩니다. 유저들의 맞힌 문제 수, 제출 수는 Spring Scheduler 로 구현한 스케

쥴러가 15분 마다 갱신합니다. 개인 프로필 페이지에서는 본인의 아이디 및 닉네임, 맞힌 문

제 수와 시도한 문제 수, 맞힌 문제들의 번호를 확인할 수 있습니다.

## 사진

![질답 게시판](https://user-images.githubusercontent.com/76681977/190900309-56f8ffca-d090-45b2-8047-019ed5a552a7.png)
* 질답 게시판

![게시판 세부 페이지](https://user-images.githubusercontent.com/76681977/190900333-c086349e-787c-4385-841a-0e70f2233b02.png)
* 게시판 세부 

![질문 작성 페이지](https://user-images.githubusercontent.com/76681977/190900857-9103b38c-e3fc-41ed-9df4-2aeca1c326f3.png)
* 질문 작성 페이지

![랭킹 페이지](https://user-images.githubusercontent.com/76681977/190900459-9908cad2-3436-49e8-b434-a4ca2775cc6f.png)
* 랭킹 페이지

![프로필 페이지](https://user-images.githubusercontent.com/76681977/190900475-360a4ca3-6d58-4b60-a982-28b1faa71620.png)
* 프로필 페이지

<br>

### 💯 채점 서버

## 제출된 소스코드 채점 / 채점 과정 & 결과 실시간 전송 

가장 처음에는 웹 서버 내에 채점 로직을 삽입하면 어떨까 하는 생각도 했습니다. 하지만 

웹 서버는 여러 요청에 대해 스레드들을 생성해야 하고 각각의 실행 흐름에서 데이터 

처리의 양이 많은 채점 로직을 넣어서 딜레이를 발생시키는 것은 좋지 못한 방법이라는 

생각이 들었습니다. 따라서 웹 서버와 채점 서버를 분리 하기로 결정했습니다. 채점 

서버와 웹 서버를 분리했으므로 채점 로직을 JAVA로 구현해야 할 이유도 없어졌습니다.

<br>

최종적으로 채점 서버를 구현하기 위한 언어는 C++ 로 결정했습니다. 3 가지 이유가 있습니다.

* C/C++ 은 빠릅니다.

* 학부 2학년때, 시스템 프로그래밍 수업을 수강하며 채점 로직 구현에 필요한 fork() 과 

  exce*() 계열 함수의 사용법을 배운 적 있습니다.

* C++ 의 기본적인 문법을 숙지한 상태입니다. 

<br>

채점 프로그램은 시스템 구성도에 나타냈듯이, 다음과 같은 순서로 수행됩니다.

![채점 서버 로직](https://user-images.githubusercontent.com/76681977/190901678-8c14b068-c88a-4df5-8b0e-43d434172834.png)

1. C 언어의 mysql 라이브러리를 사용해서 mysql과 통신합니다. Submission 테이블에서 채점 

   미완료 목록을 받아옵니다. 이 동작은 1초마다 반복됩니다.

2. 받아온 미완료 채점 데이터 row 들에서 데이터를 뽑아서 미리 선언해둔 

   SubmissionData 구조체에 담습니다. 

3. 이렇게 만든 SubmissionData 구조체를 벡터 객체에 push 합니다.

4. 백터 객체의 모든 원소에 대하여 아래의 동작을 수행합니다.

<br>

* SubmissionData 객체에서 제출된 소스코드를 불러오고, 소스코드가 쓰인 .cpp 파일을 생성합니다.

  ofstream 함수를 사용합니다.

* system("gcc -o submittedCode.out submittedCode.cpp") 함수를 호출해서, 프로젝트 폴더 내부에 

  실행 파일을 생성합니다.

* 에러가 발생하면, SubmissionData 의 gradingResult 속성값에 COMPILE_ERROR(매크로) 값을 넣고, 

  다음 SubmissionData로 continue 합니다.

* 에러가 발생하지 않는다면 생성된 실행 파일에 대해서, 모든 입력 테스트 케이스들을 
  
  넣어서 실행시킬 것입니다. 

* 입력 테스트 케이스 파일의 경로를 전달받는 run() 함수를 테케마다 호출합니다. 
  
  이 함수 내부에서 다음과 같은 동작이 수행됩니다.

<br>

   > 함수의 초반부부터 fork() 함수를 호출하여 부모 / 자식으로 프로세스를 분기합니다.
   
   > 자식 프로세스라면, 우선 setrlimit() 함수를 호출하여 가용한 수행 시간과 메모리를 제한합니다. 
   
   >> ( 이렇게 하지 않으면 클라이언트가 무한히 실행되는 소스코드를 제출하면, 채점 서버가 멈춰버리게 됩니다. 또한 문제의 제한 조건도 걸러내지 못합니다.)
   
   >> 다음으로, freopen 함수를 활용해서 stdin, stdout, stderr 를 사용자 지정해줍니다.

   >> 이후에 execl() 함수를 호출합니다. 이렇게 되면 stdin 스트림에 입력 테스트케이스 txt 파일 내용이 들어가서, 소스코드 내부의 scanf 나
   
   >> cin 과 같은 입력 함수에 입력이 자동으로 들어가게 됩니다. 또한 stdout 으로 지정한 userAnswer.txt 에 printf 나 cout 의 결과가 기록되게 됩니다.

   > 부모 프로세스라면, wait4() 함수를 사용해서 자식 프로세스가 종료되기를 기다립니다.
   
   >> 자식 프로세스가 종료되면, wait4() 함수의 인자로 넘겼던 rusage 구조체에 자식 프로세스의 실행 결과 ( 메모리, 수행시간 ) 가 담깁니다.
   
   >> 또한 wait4()의 결과로 반환되는 int 값을 통해 시간 초과, 메모리 초과, 런타임 에러 등을 구분할 수 있습니다.
   
   >> 정상수행 되었다면, 실행 결과를 SubmissionData 의 속성들에 저장합니다. 이렇게 채점이 끝납니다.
   
<br> 

위의 동작은 하나의 입력 테스트 케이스에 대한 동작입니다. 모든 입력 테스트 케이스들에 대해 

성공해야 사용자는 AC를 받을 수 있습니다.

<br>

사용자에게 실시간으로 채점 결과를 전송해 주기 위해서,

하나의 입력에 대한 채점 결과가 나올 때 마다 채점 결과를 UDP 패킷으로 로컬에서 가동되고 있는

, node.js 기반의 웹 소켓 서버로 전달합니다. 웹 소켓 서버는 udp 소켓으로 채점 결과를 담은 패

킷을 수신하고, 자신과 연결되어 있는 클라이언트들에게 채점 현황 데이터를 다시 전송합니다.

이렇게 클라이언트들은 실시간으로 채점 현황을 확인할 수 있습니다.

<br>
<br>

## 🔨 사용 기술

## 프론트 엔드

<img alt="Html" src ="https://img.shields.io/badge/HTML5-E34F26.svg?&style=for-the-badge&logo=HTML&logoColor=white"/> <img alt="Css" src ="https://img.shields.io/badge/CSS3-1572B6.svg?&style=for-the-badge&logo=CSS&logoColor=white"/> <img alt="JavaScript" src ="https://img.shields.io/badge/JavaScriipt-F7DF1E.svg?&style=for-the-badge&logo=JavaScript&logoColor=black"/> <img src="https://img.shields.io/badge/jquery-0769AD?style=for-the-badge&logo=jquery&logoColor=white">

## 백엔드

### 언어
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/c++-00599C?style=for-the-badge&logo=c%2B%2B&logoColor=white"> <img alt="JavaScript" src ="https://img.shields.io/badge/JavaScriipt-F7DF1E.svg?&style=for-the-badge&logo=JavaScript&logoColor=black"/>

### 프레임 워크 
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/express-000000?style=for-the-badge&logo=express&logoColor=white"> 

### 환경
<img src="https://img.shields.io/badge/Amazon AWS-232F3E?style=flat-square&logo=Amazon%20AWS&logoColor=white"/> <img src="https://img.shields.io/badge/apache tomcat-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=white"> 

