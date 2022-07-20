**Дипломный проект профессии «Тестировщик»**

1. *План автоматизации тестирования*
[Plan.md](https://github.com/AntonovaAnastasiya/Diplom-project/files/8985065/Plan.md)

2. *Для запуска автотестов необходимо:*
1) В терминале запустить необходимые базы данных и нужные контейнеры командой: docker-compose up 

2) В новой вкладке терминала ввести следующую команду в зависимости от базы данных:

  java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar ./artifacts/aqa-shop.jar  (для базы данных MySQL);

  java "-Dspring.datasource.url=jdbc:mysql://localhost:5432/app" -jar ./artifacts/aqa-shop.jar (для базы данных PostgreSQL);


3) Проверить доступность приложения в браузере по адресу: http://localhost:8080/

4) В новой вкладке терминала Запустить автотесты командой:

  ./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"  (для базы данных MySQL);

  ./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"  (для базы данных PostgreSQL);

