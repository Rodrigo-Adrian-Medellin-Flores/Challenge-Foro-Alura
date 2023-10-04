# Challenge Foro Alura

API REST que permite operaciones CRUD para el Foro Alura desarrollado para el Curso ONE - Alura Grupo 5.

En esta API establecemos la comunicación necesaria para registrar cursos, topicos, usuarios y respuestas en el Foro Alura, además podemos obtener los datos registrados, editarlos o eliminarlos según los permisos que tenga nuestro usuario.

## Funciones y Caracteristicas

Esta sección esta en construcción.


## Acceso a la aplicación

En este repositorio se pone a disposición el archivo "Base de Datos - foro_alura.sql", que es el script que se utilizó para crear la base de datos de esta API.

Las tablas de la base de datos se generan al ejecutar la API gracias a la implementación de Flyway Migration.

Además se pone a disposición el archivo "Foro.jar", que es un ejecutable de la API.

Para usar el proyecto se necesitan descargar los ficheros "Base de Datos - foro_alura.sql" y "Foro.jar". 

Antes de ejecutar la aplicación deberá tener instalada la base de datos MySQL y deberá ejecutar el script proporcionado. Las credenciales por defecto que buscará esta aplicación para acceder a la base de datos son: Url:jdbc:mysql://localhost:3306/foro_alura   Usuario:root   Contraseña:root

Sin embargo también puede especificar sus credenciales en variables de entorno, en ese caso los nombres de las variables deberán ser: DATASOURCE_URL:valor    DATASOURCE_USERNAME:valor    DATASOURCE_PASSWORD:valor

Una vez que las credenciales de la base de datos sean las adecuadas y se haya ejecutado el script para generar la base de datos, podemos ir a la carpeta de descargas de nuestro ordenador y ejecutar la aplicación "Foro.jar" con uno de los siguientes métodos:
- Con un doble click
- Con la opción "Abrir" del menú contextual del archivo (el menú contextual se despliega al hacer click derecho sobre el archivo)
- Seleccionando el archivo y presionando la tecla 'Enter'

Después de ejecutar la API puede dirigirse a la ruta:

http://ruta-de-su-servidor:puerto-en-que-se-ejecuta/swagger-ui.html

En esa ruta encontrará la interfaz gráfica Swagger UI que permite probar los métodos de la API REST, también puede probar estos métodos desde una API externa como Insomnia o Postman, sin embargo Swagger UI nos permite visualizar información acerca de los datos necesarios para realizar nuestra solicitud adecuadamente.

Para probar los Endpoints que requieren permiso de administrador deberá registrar un usuario con el siguiente correo electronico ficticio: admin@alura.com
Después de registrar ese usuario podrá autenticarlo y obtener los permisos de administrador.

Si lo prefiere puede especificar un email de administrador diferente, para ello deberá crear una variable de entorno llamada: API_ADMIN:valor (debe ser un correo electronico)

También puede modificar la clave para firmar los JWT (Json Web Token), esta clave por defecto es: 12345, pero puede crear una variable de entorno con el valor deseado, esta variable de entorno deberá llamarse:
JWT_SECRET:valor

Para cerrar la API solo debe realizar una solicitud HTTP del tipo POST en el endpoint /actuator/shutdown:
http://ruta-de-su-servidor:puerto-en-que-se-ejecuta/actuator/shutdown

Esta solicitud no requiere de ningun cuerpo o header.


### Requisitos

Java SE 17
Base de datos MySQL


## Técnologias Usadas

- Lenguaje Java (JDK17.0.8)
- IDE VSCode (Extension Pack for Java v0.25.13)
- Manejo de Dependencias Maven
- Spring Boot (3.1.4)
- Spring Data Jpa
- Spring Security
- Base de Datos MySQL
- Flyway Migration
- Swagger UI proporcionada por Springdoc (2.2.0)


## Autor

Proyecto desarrollado con fines educativos por: Rodrigo Adrian Medellín Flores.

Curso ONE Grupo 5 - Alura Latam.

