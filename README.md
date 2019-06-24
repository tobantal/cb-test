# cb-test

Инструкция по сборке и запуску проекта

В проекте использовался SpringBoot, поэтому с развертыванием проблем не должно быть. 

Клонируем код из репозитория: в командной строке запускаем git clone https://github.com/tobantal/cb-test.git.

Вариант развертывания 1.
Запускаем в командной строке mvn spring-boot:run (тесты будут пропущены). 
Сайт будет развернут на 8080 порту. 
Стартовая страница http://localhost:8080/person/info.
Далее по навигации вверху можно просматривать таблицы или вносить данные.

Таблицы также можно заполнить через H2-console. Для этого заходим на http://localhost:8080/h2-console.
В поле JDBC URL прописываем jdbc:h2:mem:testdb, пользователь sa, пароль pas.
Заходим в редактор базы данных, добавляем, удаляем данные.

Вариант развертывания 2.
После клонирования. Запускаем в терминале mvn install - начинаются скачиваться зависимости и запускаться тесты, может потребоваться время. 
В результате скомпилируется запускаемый jar-файл, который запускается java -jar target/tobolkin-0.0.1-SNAPSHOT.jar.
Сайт также развернется на 8080 порту, стартовая страница http://localhost:8080/person/info.

В качестве ручного тестирования сервиса предлагаю проделать следуюущую операцию.
В форме ввода "Добавить сотрудника" вводим (фамилия - Mikhailov, имя - Oleg), остальные поля - любые допустимые.
Нажимаем "добавить", затем "отправить".
Через какое-то время Mikhailov Oleg добавится в таблицу "Телефонная книга" и у него появится компания, в которой он работает.

Для уточняющих вопросов мои контакты: tobantal@gmail.com, +7(962)784-50-62 
