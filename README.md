# S3-Compatible Mini-Storage Service

Разрабатываю S3-хранилище c использованием облачных технологий для решения задач масштабируемого хранения неструктурированных данных.  
Использую MinIO для доступа к файлам, Apache Cassandra для управления метаданными, Spring Cloud Stream для обработки асинхронных событий (event-driven).  
Также планируется реализация erasure coding для экономии памяти, развертывание приложения с Docker, интеграционное тестирование с Testcontainers.

## Функции (Реализовано)

* Базовые REST API операции для работы с объектами
* Интеграция с MinIO 
* Настройка среды разработки с Docker Compose

## Функции (Планируемые)

*   **Асинхронные операции с объектами:** REST API операции с объектами (загрузка - включая multipart, скачивание и удаление).
*   **Хранение метаданных:** Reactive Cassandra Driver для масштабируемого хранения метаданных и Redis для кэширования.
*   **Event-driven архитектура:** Асинхронная обработка событий через Spring Cloud Stream.
*   **Паттерн Saga:** Управление транзакциями для обеспечения консистентности данных.
*   **Отказоустойчивость:** Реализация данных между несколькими узлами MinIO.
*   **Storage Tiering:** Автоматическое перемещение редко используемых данных из "горячего" хранилища в "холодное" хранилище.
*   **Erasure Coding:** Реализация кодирования Рида-Соломона для хранения данных в "холодном" хранилище.

## Стек технологий

* **Основа:** Java 21, Spring Boot 3.5.4 c WebFlux
* **Хранение данных:** MinIO (S3-совместимое)
* **Метаданные:** Apache Cassandra (Reactive)
* **Кэш:** Redis
* **Обработка событий:** Spring Cloud Stream (Kafka Binder)
* **Безопасность:** Spring Security (JWT)
* **Надежность:** Resilience4j
* **Erasure Coding:** Backblaze Reed-Solomon
* **Сборка:** Maven
* **Тестирование:** JUnit 5, Testcontainers
* **Развертывание:** Docker, Docker Compose
* **Утилиты:** Micrometer (Prometeus), OpenTelemetry, Logback (MDC)

## Локальный запуск

1.  Для запуска приложения необходимы Docker и Docker Compose.
2.  Клонируйте репозиторий: `git clone https://github.com/truestayhere/s3-stream-engine`
3.  Перейдите в папку проекта: `cd s3-stream-engine`
4.  Создайте файл `src/main/resources/application-local.properties` на основе шаблона `application-local.properties.example` и укажите в нем свои значения.
5.  Запустите среду разработки:
    ```bash
    docker-compose up -d
    ```
6.  Запустите приложение:
    ```bash
    ./mvnw spring-boot:run
    ```
Приложение доступно по адресу: `http://localhost:8080`.

---
*README будет обновляться по мере развития проекта.*