
Teamkerbell
=====
Private Project Repository For University Students
<br>
Project Support Application For University Students

* Youtube : https://www.youtube.com/watch?v=76V_DYsX0VQ 
* Youtube (new) : https://www.youtube.com/watch?v=ctLO8N2dzgQ
* SlideShare : http://www.slideshare.net/seongside/teamkerbell


Core Features
=====
* Document VCS (Version Control System)
* Automatically Make Tags via analyzing documents' contents
 * They'll be used for easier & quicker searching 
* Automatically Synchronize up to date Documents
* All the changes will be notified by Mobile App

Application Stack
=====
![app stack](https://cloud.githubusercontent.com/assets/10272119/19376711/49ebd3c0-921b-11e6-9dbf-adf45e996a39.png)


Demo
=====
http://175.126.112.144/


How To Use
=====

 Using <a href="https://docs.docker.com/compose/">DockerCompose</a>
 
 ```
 wget https://raw.githubusercontent.com/seongahjo/teamkerbell/master/docker-compose.yml
 
 docker-compose up
 ```

OR


If You have installed MySQL, Redis

You can use MavenWrapper instead of DockerCompose

```  
 
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
 
 * Access the deployed web application at [http://localhost:8080]

 ```

##### Developed by IntelliJ


License
=====
Teamkerbell is released under [MIT License] 

[MIT License]: https://github.com/seongahjo/Teamkerbell/blob/master/LICENSE
