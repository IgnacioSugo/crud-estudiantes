# API de gestión de estudiantes

El proyecto presentado es una implementación básica de una API REST desarrollada con Spring Boot para gestionar estudiantes. La arquitectura se divide en tres capas:

- Controller (Manejo de solicitudes HTTP y exposición de endpoints)
- Service (implementación de la lógica de negocio)
- Repository (Acceso a los datos mediante Spring Data JPA)

Se implementaron DTOs para evitar exponer las entidades del dominio directamente.
Además se utilizaron mecanismos de paginación y ordenamiento, manejo global de errores con @ControllerAdvice y un sistema de Logging estructurado de distintos niveles

## Lista de Tecnologías utilizadas

- Java 17
- Spring Boot 4.0.2
- Maven 4.0.0
- PostgreSQL
- Hibernate / JPA
- Lombok
- Spring Data JPA
- Spring DevTools
- SLF4J + Logback

## Arquitectura

El proyecto utiliza una arquitectura en capas, cada capa tiene una responsabilidad específica:

- Controller: Expone los endpoints REST, recibe y valida las solicitudes HTTP (requests) y delega la ejecución de la lógica de negocio a la capa de servicio. Se encarga de devolver las respuestas (responses) al cliente
- Service: Contiene la lógica de negocio de la aplicación. Realiza las operaciones necesarias, interactúa con los repositorios y gestiona reglas de negocio, validaciones y transacciones
- Repository: Representa la capa de persistencia. Se encarga de acceder a datos utilizando Spring Data JPA, lo que permite realizar operaciones CRUD sobre la base de datos mediante entidades mapeadas con JPA

## Funcionalidades

- CRUD completo para estudiantes (Create, Read, Update, Delete)
- Validación de datos con Bean Validation
- Manejo global de excepciones con @ControllerAdvice
- Logging configurado con distintos niveles (DEBUG, WARN, ERROR) utilizando SLF4J + Logback
- Paginación y ordenamiento con Page y Pageable
- Actualización parcial de recursos mediante PATCH
- Manejo de transacciones con @Transactional

## Perfiles

Se cuenta con dos perfiles:

- dev: Base de datos H2 en memoria con los datos precargados
- prod: Configuración para PostgreSQL

## Datos de prueba

El proyecto incluye un archivo data.sql que carga 5 estudiantes automáticamente en el perfil "dev" utilizando una base de datos H2 en memoria

## Endpoints

| Método | Endpoint | Descripción |
|--------|----------|------------|
| GET | /api/estudiantes | Lista paginada |
| GET | /api/estudiantes/{id} | Buscar por ID |
| GET | /api/estudiantes/buscar | Buscar por nombre |
| POST | /api/estudiantes | Crear estudiante |
| PUT | /api/estudiantes/{id} | Actualización completa |
| PATCH | /api/estudiantes/{id} | Actualización parcial |
| DELETE | /api/estudiantes/{id} | Eliminar estudiante |

## Ejecución del proyecto

Para ejecutar, usamos los siguientes comandos:

1. mvn clean package (genera el .jar en target/crud.estudiantes-0.0.1-SNAPSHOT.jar)
2. java -jar target/crud.estudiantes-0.0.1-SNAPSHOT.jar

La API estará disponible en http://localhost:8080 

Para ejecutar con perfil dev:

- mvn spring-boot:run -Dspring-boot.run.profiles=dev

## Ejecutar en producción (PostgreSQL)

Definir variables de entorno:

```bash
export DB_URL=jdbc:postgresql://localhost:5432/estudiantes
export DB_USERNAME=postgres
export DB_PASSWORD=tu_password
```

Una vez hecho esto, ejecutar:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## Ejemplo de uso

GET /api/estudiantes?page=0&size=5&sort=nombre,asc

```json
 {
    "content": [
        {
            "id": 1,
            "nombre": "Juan Perez",
            "email": "juan@email.com",
            "edad": 25,
            "fechaIngreso": "2024-01-10"
        }

        // otros estudiantes
    ],
    "totalElements": 5,
    "totalPages": 1,
    "size": 5,
    "number": 0,
    "sort": {
        "empty": false,
        "unsorted": false,
        "sorted": true
    }
 }
 ```

## Mejoras futuras

- [ ] Implementar autenticación con Spring Security (JWT)
- [ ] Agregar documentación con Swagger y OpenAPI
- [ ] Incorporar pruebas unitarias y de integración (JUnit + Mockito)
- [ ] Dockerizar la aplicación
- [ ] Implementar busqueda avanzada con Specifications
- [ ] Agregar manejo de soft delete
- [ ] Configurar CI/CD con GitHub Actions