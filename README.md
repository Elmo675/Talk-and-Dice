# Diary

**Diary** is a web application which allows you to maitain your own diary in a simple, nice way. You can add friends who will see your diarys if you allow them to. You can make public diares which people around the world will be able to read and publish and of course you can make your own diary which will be available only to you.

## Instalation

1. Git clone this repository to your main repository
2. Create diaries_database in your MySql server
3. In main/resources/application.properties set up your connection with MySql server
4. Run mvn package to create jar file

Note: Check if your MySql server is working properly

## Starting Application

1. Run java -jar on jar file in target folder
2. Your Application is now online on localhost:8080

## Mapping

You can get all diaries from localhost:8080/api/v1/entry or a specific diary from /api/v1/entry/{id}

You can post new diaries on localhost:8080/api/v1/entry or edit existing ones on /api/vi/entry/{id} 

You can delete existing diaries on localhost:8080/api/v1/entry/{id}


## Tools

* Maven
* Spring Boot
* Github Actions
* MySQL
* Project Lombock
* JPA

## License

**Diary** is published under [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).

