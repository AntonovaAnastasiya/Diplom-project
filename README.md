**Дипломный проект профессии «Тестировщик»**


1. *План автоматизации тестирования*
[Plan.md](https://github.com/AntonovaAnastasiya/Diplom-project/blob/1ba580bdc75f233acc7d75c2474e9a1999e77f2f/Docs/Plan.md)

_____________
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
  
6) Для запуска и просмотра отчетов по результатам тестирования, с помощью "Allure", выполнить по очереди команды:

./gradlew allureReport

./gradlew allureServe
________________

3. *Отчётный документ по итогам тестирования*
[Report.md](https://github.com/AntonovaAnastasiya/Diplom-project/blob/1970f7f62433557befbfc0094aabe353a11fad66/Docs/Report.md)
_______________
4. *Отчётный документ по итогам автоматизации*
[Summary.md](https://github.com/AntonovaAnastasiya/Diplom-project/blob/e427c11a1fb6fbfda3358de8f67aae01b50436ad/Docs/Summary.md)
