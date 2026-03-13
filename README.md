
# larosproject-api

**Última modificación:** 13/03/2026  
**Creado:** 2020

## Descripción

**larosproject-api** es un servicio **Backend** que expone APIs REST desarrolladas durante el curso **AlgaWorks - Fullstack Angular e Spring**.

Este servicio proporciona endpoints para la gestión de recursos como **movimientos**, **categorías** y **personas**, y puede ser consumido por clientes externos como aplicaciones web o herramientas de prueba de APIs.

---

## Arquitectura

El sistema está compuesto por los siguientes servicios:

- **MySQL** — Base de datos utilizada por el Backend.
- **Adminer** — Herramienta de administración para la base de datos.

---

## Servicios e Integraciones

### Base de Datos

El servicio se conecta a una base de datos **MySQL** configurada mediante `docker-compose.yml`.

**Base de datos:** `larosprojectapidb`

**URL de conexión JDBC**

```
jdbc:mysql://localhost:3306/larosprojectapidb
```

**Herramienta de administración (Adminer)**

```
http://localhost:8086/?server=mysql_lpa
```

---

## Endpoints disponibles

Una vez iniciado el servicio **Backend**, la API expone endpoints como los siguientes:

```
GET http://localhost:8080/categorias
GET http://localhost:8080/personas
...
```

Dependiendo de la versión del servicio, pueden existir endpoints adicionales.

---

## Postman Collection

Para facilitar las pruebas de la API se incluye una colección de Postman.

**Colección**

```
LarosProject - API
```


---

## Configuración de Seguridad

El servicio soporta dos mecanismos de autenticación configurados mediante **Spring Profiles**.

| Spring Profile | Tipo de autenticación |
|----------------|-----------------------|
| basic-security | HTTP Basic Authentication |
| oauth-security | OAuth2 (Access Token / Refresh Token) |

---

### Basic Authentication

Autenticación directa utilizando **HTTP Basic Authentication**.

El cliente debe enviar las credenciales en el encabezado:

```
Authorization --> Auth Type=Basic Auth
```
```
http://localhost:8080/categorias
```

---

### OAuth2

La API implementa **OAuth2** para la autenticación y autorización de los clientes.

El flujo de autenticación utilizado es:

1. **Password Grant**
2. **Refresh Token mediante Cookie**
3. **Acceso a endpoints protegidos mediante Access Token**

---

#### Flujo de Autenticación

##### 1. Obtención inicial de tokens (Password Grant)

El cliente debe solicitar un token utilizando `grant_type=password` en el endpoint:

```
POST http://localhost:8080/oauth/token
```

El **Refresh Token** es almacenado automáticamente en una **cookie HTTP llamada `refreshToken`**.

---

##### 2. Renovación del Access Token (Refresh Token)

Cuando el **Access Token** expira, el cliente puede solicitar uno nuevo utilizando:

```
grant_type=refresh_token
```
```
POST http://localhost:8080/oauth/token
```
El cliente **no necesita enviar manualmente el `refresh_token`**.

El filtro `RefreshTokenCookiePreProcessorFilter` lee el `refreshToken` desde la cookie y lo inserta automáticamente como parámetro `refresh_token` en la request hacia `/oauth/token`.

---

##### 3. Acceso a endpoints protegidos

Una vez obtenido el **Access Token**, este debe enviarse en el encabezado de las peticiones a los endpoints protegidos:

```
Authorization: Bearer <access_token>
```
```
GET http://localhost:8080/categorias
```

---

## Cliente FrontEnd

Este servicio Backend es consumido por el cliente FrontEnd.

**Proyecto:** `larosproject-ui`

```
http://localhost:4200/login
```

Para más información sobre el servicio FrontEnd, consulte:

```
larosproject-ui/README.md
```

---

## Ejecución del proyecto

### Requisitos

- Java 8
- Maven
- Docker Desktop
- Docker Compose

### Base de datos

La base de datos MySQL se inicia mediante:

```bash
docker compose up -d
```

---

## Origen del proyecto

Este proyecto fue desarrollado siguiendo el curso:

**AlgaWorks - Fullstack Angular e Spring**

https://app.algaworks.com/meus-cursos/angular-rest-spring-boot

---

## Git

### Repositorio

https://github.com/arellano84/larosproject-api

### Crear Tag

Comando de creación de tag:

```bash
git tag -a v1.1.1 -m "v1.1.1 Versión PRODUCCIÓN hasta <Apêndice: Combos dependentes> incluso."
```
