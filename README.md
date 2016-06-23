
Teamkerbell
=====
학생들을 위한 조별과제 서포트 애플리케이션 
* Youtube : https://www.youtube.com/watch?v=76V_DYsX0VQ 
* SlideShare : http://www.slideshare.net/seongside/teamkerbell

How To Use in MAC
=====
```  
 
 * brew install mysql
 
 * git clone https://github.com/seongahjo/Teamkerbell.git
 
 * cd Teamkerbell
 
 * /src/main/resources/spring.properties를 설정합니다. 
  app.jdbc.driverClassName=com.mysql.jdbc.Driver
  app.jdbc.url=jdbc:mysql://주소
  app.jdbc.username=ID
  app.jdbc.password=PASSWORD
  
  app.socket.url=localhost 혹은 자기 자신 IP
 
 * ./mvnw tomcat7:run
 
 * 실행후 http://localhost:8080 에 접속

 ```
 
#####오픈소스 개발툴 IntelliJ를 통해 개발되었습니다. 

Open Source License
=====
* Spring Framework - [Apache License 2.0.]
* Hibernate Framework - [LGPL V2.1]
* Vert.x Framework - [Apache License 2.0.]
* Mod-Socket io - [Apache License 2.0.]
* logback - [LGPL V2.1]
* Apache POI - [Apache License 2.0.]
* JQuery - [MIT License]
* Bootstrap - [MIT License]
* AdminLTE - [MIT License]
* Modernizr - [MIT License]
* Calendario - [MIT License]
* Chart.js - [MIT License]
* Datatable - [MIT License]
* Prettydate - [MIT License]
* html2canvas - [MIT License]
* jsPDF - [MIT License]

License
=====
Teamkerbell is released under [MIT License] 

[MIT License]: https://github.com/seongahjo/Teamkerbell/blob/master/LICENSE
[Apache License 2.0.]: https://www.apache.org/licenses/LICENSE-2.0.html
[LGPL V2.1]: http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
