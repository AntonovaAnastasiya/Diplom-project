**Дипломный проект профессии «Тестировщик»**

1. *План автоматизации тестирования*
[Plan.md](https://github.com/AntonovaAnastasiya/Diplom-project/files/8985065/Plan.md)

2. *Для запуска автотестов необходимо:*

1). Запустить Docker  на ПК.

2). В терминале запустить необходимые базы данных и нужные контейнеры командой: docker-compose up 


3). В новой вкладке терминала ввести следующую команду в зависимости от базы данных:

  java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar ./artifacts/aqa-shop.jar  (для базы данных MySQL);

  java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar ./artifacts/aqa-shop.jar (для базы данных PostgreSQL);


4). Проверить доступность приложения в браузере по адресу: http://localhost:8080/


5). В новой вкладке терминала Запустить автотесты командой:

  ./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"  (для базы данных MySQL);

  ./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"  (для базы данных PostgreSQL);

