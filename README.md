# Task Manager Synapse

**Менеджер задач** с аутентификацией, загрузкой файлов и кэшированием.

Демонстрационное веб-приложение для создания, управления и отслеживания задач. Каждый пользователь видит только свои задачи, может загружать файлы к ним, изменять статус, название и описание. Проект реализован с акцентом на чистую архитектуру, безопасность и готовность к развертыванию.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.4-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)
![Redis](https://img.shields.io/badge/Redis-Cache-red)
![MinIO](https://img.shields.io/badge/MinIO-Object%20Storage-00A1E9)
![Docker](https://img.shields.io/badge/Docker-Containerized-2496ED)
![FreeMarker](https://img.shields.io/badge/FreeMarker-Templating-00B3A1)

## ⚠ Disclaimer

Данный репозиторий — **пет-проект** для демонстрации моих навыков Java/Spring разработки.

- Некоторые архитектурные решения могут быть неоптимальными
- Код может содержать избыточность и места для рефакторинга
- Проект не предназначен для использования в реальной продакшен-среде

Я активно продолжаю его развивать и улучшать.
##  Основные возможности

- Полная **регистрация и аутентификация** пользователей (Spring Security)
- Создание, просмотр, редактирование и удаление задач
- Изменение статуса задачи (NEW, IN_PROGRESS, DONE и др.)
- Inline-редактирование названия и описания задачи
- Загрузка и хранение файлов/аттачментов через **MinIO**
- Кэширование задач с помощью **Redis**
- Валидация прав доступа (пользователь может редактировать только свои задачи)
- Миграции базы данных через **Flyway**
- Удобный веб-интерфейс на FreeMarker

##  Технологический стек

**Backend:**
- Java 17
- Spring Boot 3.2 (Web, Data JPA, Security, Cache, Mail)
- Spring Data JPA + Hibernate
- Lombok

**Frontend:**
- FreeMarker (Server-Side Rendering)
- HTML + CSS + JavaScript (частично)

**Database & Storage:**
- PostgreSQL
- Flyway (миграции)
- Redis (кэширование)
- MinIO (S3-совместимое хранилище файлов)

**DevOps:**
- Maven
- Docker + Docker Compose

**Дополнительно:**
- DTO паттерн
- Stream API для маппинга
- @Transactional + Cacheable/CacheEvict
- Exception Handling + Access Control

## Запуск проекта

### 1. Самый быстрый способ — Docker Compose

```bash
git clone https://github.com/Akryze/Task-Manager-Synapse.git
```
```bash
cd Task-Manager-Synapse
```
```bash
docker-compose up --build
```

Приложение будет доступно по адресу: ```http://localhost:8080```
### 2. Локальный запуск (без Docker)


1. Запусти PostgreSQL, Redis и MinIO
2. Настрой подключение в application.properties
3. Выполни:

```bash
./mvnw spring-boot:run
```