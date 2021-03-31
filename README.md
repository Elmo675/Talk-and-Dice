[![Build Status](https://github.com/elmo675/diary/actions/workflows/CI.yml/badge.svg)](https://github.com/Elmo675/Diary/actions/workflows/CI.yml?query=workflow%3ACI)

# Talk&Dice

**Talk&Dice** is a web application which allows you to play dice games with your friends. Api will suport you with player cards, dice throwing, chat and many more. You will be able to create your own games or just download the existing ones. 

## Instalation

1. Git clone this repository to your main repository
2. Create sesion_database in your MySql server
3. In main/resources/application.properties set up your connection with MySql server
4. Run mvn package to create jar file

Note: Check if your MySql server is working properly

## Starting Application

1. Run java -jar on jar file in target folder
2. Your Application is now online on localhost:8080

## Mapping

You can get all sessions from localhost:8080/api/v1/entry or a specific session from /api/v1/entry/{id}

You can post new sessions on localhost:8080/api/v1/entry or edit existing ones on /api/vi/entry/{id} 

You can delete existing sessions on localhost:8080/api/v1/entry/{id}


## Tools

* Maven
* Spring Boot
* Github Actions
* MySQL
* Project Lombock
* JPA

## License

**Talk&Dice** is published under [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).

