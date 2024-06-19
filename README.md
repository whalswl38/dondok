1. 프로젝트명 : 돈독
2. 프로젝트 주제 : 돈독한 사이인 사람들과 행복한 여행을 할 수 있도록 여행 계획을 짤 수 있고, 일정 공유할 수 있는 사이트
3.  DB설계<br/>
![image](https://github.com/whalswl38/dondok/assets/59720196/b49715bf-e8c5-4bc7-b736-33f6d9abc41b)
<br/>
4.  담당역할<br/>
(1) DB구현<br/>
: 회원 서비스에 사용할 DB구현 및 테이블 생성<br/>
(2) 회원서비스(가입 / 정보수정 / 탈퇴 / 계정 찾기 / 로그인 / 로그아웃) 구현<br/>
- Servlet의 MVC model2 방식을 적용하여 회원가입 및 로그인 기능 구현<br/>
- 세션 별 화면 구성 및 기능 차별화<br/>
- Ajax 통신을 이용한 실시간 회원가입 입력 및 중복 여부 안내 표시<br/>
- Servlet 환경에 mantis를 적용하여 기본 prepared statement를 통한 쿼리에 비해 직관적이고 가변적인 쿼리적용을 할 수 있는 환경 설정<br/>
- log4j 설정으로 빌더 및 개발 시 디버깅을 좀 더 쉽게 할 수 있는 환경 설정<br/>
- 프로젝트 시작시점에 Servlet 환경에서의 프로젝트 기초 환경 설정 및 웹 클라이언트 화면에서 db까지의 통신이 가능한 기본 가이드용 샘플화면 설정<br/>


