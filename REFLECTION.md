# Reflection file

Daily self-reflection on the tasks finished and problems arisen.

=== Phase 0: Foundation ===

## Day 1
_22.08.2025_

Started working on the S3 Stream Engine project!

- Initialized a new SpringBoot project with [Spring Initializr](https://start.spring.io/) chosen Spring Web and Spring Boot Actuator dependencies.
- Configured docker-compose.yml so with command ```docker-compose up -d``` starts: MiniO, Cassandra, Kafka & Zookeeper.
- Created initial application.properties file.
- Created private application-local.properties file for local secrets.
- Created template application-local.properties.example file for other users to recreate local secrets file.
- Added an initial README.md file.
- pushed everything on GitHub!

=== END Phase 0: Foundation === 

=== Phase 1: Reactive MVP ===

## Day 2
_01.09.2025_

- Adopted Hexagonal Architecture to separate business logic and infrastructure.
- Structured the project into domain (core logic), adapters (external integrations) and config layers.
- Implemented reactive file upload flow with Spring WebFlux:
  - StoragePort and UploadObjectUseCase interfaces to define contracts.
  - MiniOStorageAdapter to handle interactions with MiniO SDK.
  - Solved blocking problem by offloading MiniO calls to the boundedElastic scheduler.
- Implemented GlobalExceptionHandler for error handling.



-------

To add later:
- ...



