# Pastetext
## Описание проекта
REST API повторяющий функциональность pastebin. 
Реализованна возможность авторизации с помощью JWT и Spring Security.
Генератор уникальных url вынесен в отдельный микросервис.
Текстовый контент храниться в отдельном s3 совместимом хранилище Minio

## Стек технологий
<li>Java</li>
<li>Maven</li>
<li>Spring Boot</li>
<li>Spring Security</li>
<li>JWT</li>
<li>Spring Data JPA</li>
<li>PostgreSQL</li>
<li>Minio</li>
<li>Eureka</li>
<li>Springdoc-openapi</li>

## Архетиктура
- eureka-server - модуль модуль, который использует Spring Cloud Netflix Eureka в качестве встроенного сервера обнаружения.
- GeneratorUrl - микросервис, отвечающий за генерацию уникальных и коротких url
- paste-service - микросервис, отвечающий за авторизацию и создание, получение, удаление текстовых публикаций

## Примеры REST-запросов
### GeneratorUrl
#### Получение уникального коротокого url
```http request
GET /api/generated-url
```
Ответ:
```json
{
  "id": 0,
  "url": "string"
}
```

### paste-service
#### Регистрация
```json lines
POST /api/auth/registration
{
  "username": "string",
  "password": "string",
  "email": "string"
}
```
Ответ:
```json
{
  "token": "string"
}
```
#### Получение токена
```json lines
POST /api/auth/authenticate
{
  "username": "string",
  "password": "string"
}
```
Ответ:
```json
{
  "token": "string"
}
```
#### Инвалидация токена
```json lines
GET /api/auth/logout
```
Ответ:
```json
{
  "message": "successfully logged out"
}
```
#### Добавить новую пасту
```json lines
POST /api/pastes
{
  "title": "string",
  "content": "string",
  "visibility": "string",
  "pasteExpirationMinutes": 0,
  "permissions": [
    "string"
  ]
}
```
Ответ:
```json
{
  "url": "string",
  "title": "string"
}
```
#### Получить пасту по url
```http request
GET /api/pastes/{paste-id}
```
Ответ:
```json
{
  "title": "string",
  "content": "string",
  "createdAt": "2024-04-13T00:31:28.870Z",
  "expiredAt": "2024-04-13T00:31:28.870Z",
  "visibility": "PRIVATE",
  "lastVisited": "2024-04-13T00:31:28.870Z",
  "author": "string"
}
```
#### Удалить пасту по url
```http request
DELETE /api/pastes/{paste-id}
```
Ответ:
```json
{
  "message": "The paste has been successfully removed"
}
```
