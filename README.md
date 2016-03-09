
Teamkerbell
=====
학생들을 위한 조별과제 서포트 애플리케이션 
* Youtube : https://www.youtube.com/watch?v=76V_DYsX0VQ 
* SlideShare : http://www.slideshare.net/seongside/teamkerbell

How To Use
=====
 * ```/src/main/resources/spring.properties```를 설정합니다. 
 
``` 
  app.jdbc.driverClassName=com.mysql.jdbc.Driver
  app.jdbc.url=jdbc:mysql://주소
  app.jdbc.username=ID
  app.jdbc.password=PASSWORD
  ```
 
 * project.jsp의 소켓 연결 주소를 자신의 서버 주소로 변경 (로컬환경에서 테스트를 할경우에는 localhost여도 상관없음)
 
  ```
  $(document).ready(function () {
        socket = io.connect("http://주소:9999");
        ...
   ```

 * 실행후 ```http://localhost:8080``` 에 접속
 
#####오픈소스 개발툴 IntelliJ를 통해 개발되었습니다. 

Open Source License
=====


License
=====


