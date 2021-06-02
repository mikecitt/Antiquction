# Antiquction
### Web Auction Application for an antique items seller

## Server
#### Used technologies:

 - Java 11
 - Spring Boot 2.4.5
 - Maven
 - h2 in-memory database

#### Work environment:

 - Visual Studio Code

#### Using
    mvn clean install
    mvn -Djasypt.encryptor.password=SECRET_KEY spring-boot:run
* SECREY_KEY should be replaced with company name in lowercase -> s****c

## Documenation
#### It is available on server on address: http://localhost:8080/swagger-ui.html

## Client
#### Used technologies:

 - Angular
 - ng-bootstrap (design)

#### Using

    npm install // to install dependencies
    ng serve    // start
    
## Notes
Email notifications are sending to antiquction@gmailnator.com which you can access at this link: https://www.gmailnator.com/inbox/#antiquction@gmailnator.com
