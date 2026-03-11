Modificado: 10/03/2026

larosproject-api:
- Es un servicio BackEnd de APIs del curso de Algaworks.
- Levanta correctamente:
  - Get http://localhost:8080/categorias
  - Get http://localhost:8080/personas
  - etc.
- Postman Collection:
  - LarosProject - API
- Configuración de seguridad:
  - BasicToken (Auth Type: Basic Auth):
    - http://localhost:8080/categorias
  - o Oauth2 (Token y Refesh Token):
    - http://localhost:8080/oauth/token 
- Conecta a BBDD mysql, configurada con docker-compose.yml
  - Adminer: http://localhost:8086/?server=mysql_lp
  - bbdd: jdbc:mysql://localhost:3306/larosprojectapi
- A este servicio BackEnd se conecta este cliente:
  - larosproject-ui: http://localhost:4200/login


Creado: 2020

AlgaWorks - Fullstack Angular e Spring:
https://app.algaworks.com/meus-cursos/angular-rest-spring-boot


Git

Repositorio
https://github.com/arellano84/larosproject-api

Crear Tag
git tag -a v1.1.1 -m "v1.1.1 Versión PRODUCCIÓN hasta <Apêndice: Combos dependentes> incluso."
