
Teamkerbell
=====
학생들을 위한 조별과제 서포트 애플리케이션 
* Youtube : https://www.youtube.com/watch?v=76V_DYsX0VQ 
* SlideShare : http://www.slideshare.net/seongside/teamkerbell

주요기능
=====
* 과제방을 통한 프로젝트별 관리

* Calendar UI를 통한 날짜별 오프라인 회의 일정 및 파일 확인
  * 회의의 구체적인 정보, 예를 들면 날짜, 장소, 시간를 확인 가능
  
* 오프라인 회의 일정조율 기능
  * 한 사용자가 날짜범위를 지정하여 사용자들이 가능한 날짜를 선택할 수 있음
  * 대다수의 사용자가 가능한 날짜를 등록
 
* 온라인 회의를 위한 채팅과 내용을 정리하는 회의록 기능
  * WebSocket을 통한 실시간 채팅 및 회의록 기록 기능을 통해 liveness를 제공

* To-do list를 통한 Task관리

* 프로젝트 완료시 진행사항을 문서화하여 제출근거로 제공
  * 팀원의 무임승차 방지
  * 교수님 평가 자료로서 활용 가능

 발전가능성
=====

* 과제방 강좌 별 분류
 * 해당 강좌에 대한 완료된 프로젝트의 파일을 예시파일로서 제공
 
* 과제방내 파일 관리자 메뉴 추가
 * 효율적인 파일 관리를 가능케함
 
* 태그를 통한 문서 관리
  * 파일 업로드시 문서 내용을 분석하여 자동으로 태그를 생성 
    * H2TLib, POI등 라이브러리를 사용하여 문서를 파싱
    
* 각 대학마다 차별화된 조별과제 서포트 애플리케이션으로 수정하여 강좌에서 활용할 수 있는 환경제공 

사용한 오픈소스 
=====
#####BACKEND 
* Spring Framework
* Hibernate Framework
* Vert.x
* Mod-Socket io
* Apache
* logback
* H2TLib
* POI 등

#####FRONT END
* AdminLTE
* Bootstrap
* modernizer
* prettydate
* data.js
* Chart.min.js
* Calendario
* Datatable 등

실행방법 
=====
 1. ```/src/main/resources/spring.properties```를 설정합니다.

 ``` 
app.jdbc.driverClassName=com.mysql.jdbc.Driver
app.jdbc.url=jdbc:mysql://주소
app.jdbc.username=ID
app.jdbc.password=PASSWORD
```

 2. project.jsp의 소켓 연결 주소를 자신의 서버 주소로 변경 (로컬환경에서 테스트를 할경우에는 localhost여도 상관없음)
 
```
$(document).ready(function () {
        socket = io.connect("http://주소:9999");
        ...
```

#####오픈소스 개발툴 IntelliJ를 통해 개발되었습니다. 
