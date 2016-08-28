
Teamkerbell
=====
Private Project Repository For University Students

* Youtube : https://www.youtube.com/watch?v=76V_DYsX0VQ 
* SlideShare : http://www.slideshare.net/seongside/teamkerbell


Core Features
=====
* Document VCS (Version Control System)
* Automatically Make Tags via analyzing documents' contents
 * They'll be used for easier & quicker searching 
* Automatically Synchronize up to date Documents
* All the changes will be notified by Mobile App


How To Use
=====
```  
 
 * If you want to run this app, you should install MySQL, Redis in advance
 
 * git clone https://github.com/seongahjo/Teamkerbell.git
 
 * cd Teamkerbell
 
 * vi src/main/resources/spring.properties
 
   app.jdbc.driverClassName=com.mysql.jdbc.Driver
   app.jdbc.url=jdbc:mysql:// Your Mysql Address
   app.jdbc.username=ID
   app.jdbc.password=PASSWORD
   app.socket.url=http:// localhost or your IP Address + 9999 (ex. http://localhost:9999)
   redis.property.address= your redis Address (ex. 127.0.0.1)
   
   + if you want to use kafka
   kafka.property.use=true
   kafka.property.address= localhost or your IP Address + 9092 (ex. localhost:9092)
 
 * ./mvnw tomcat7:run
 
 * 실행후 http://localhost:8080 에 접속

 ```
 
##### Developed by IntelliJ

License
=====
Teamkerbell is released under [MIT License] 
