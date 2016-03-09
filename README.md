
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
Spring Framework - [Apache License 2.0.]
Hibernate Framework - [LGPL V2.1]
Vert.x Framework - [Apache License 2.0.]
Mod-Socket io - [Apache License 2.0.]
logback - [LGPL V2.1]
Apache POI -[Apache License 2.0.]
JQuery - [MIT License]
Bootstrap - [MIT License]
AdminLTE - [MIT License]
Modernizr - [MIT License]
Calendario - [MIT License]
Chart.js - [MIT License]
Datatable - [MIT License]
Prettydate - [MIT License]

License
=====
Teamkerbell is released under [MIT License] 

[MIT License]: https://github.com/seongahjo/Teamkerbell/blob/master/LICENSE
[Apache License 2.0.]: https://www.apache.org/licenses/LICENSE-2.0.html
[LGPL V2.1]: http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
