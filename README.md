# Plaza Comida - Microservicio de Usuarios

Este es el microservicio de usuarios para la aplicación Plaza Comida, desarrollado siguiendo la arquitectura hexagonal (Clean Architecture) con Spring Boot.

## 🏗️ Arquitectura

El proyecto sigue la arquitectura hexagonal con las siguientes capas:

- **Domain**: Contiene la lógica de negocio, modelos y puertos
- **Application**: Contiene los casos de uso, handlers y DTOs
- **Infrastructure**: Contiene adaptadores, entidades y configuraciones

## 🚀 Tecnologías

- **Spring Boot 2.7.3**
- **Spring Security** - Autenticación y autorización
- **Spring Data JPA** - Persistencia de datos
- **MySQL** - Base de datos
- **MapStruct** - Mapeo de objetos
- **Lombok** - Reducción de código boilerplate
- **Swagger/OpenAPI** - Documentación de API
- **JWT** - Tokens de autenticación

## 📋 Roles del Sistema

El sistema maneja 4 roles principales, los cuales son estáticos y no pueden ser modificados ni gestionados por API. Estos roles se crean automáticamente al iniciar la aplicación:

1. **CLIENTE**: Usuario que puede realizar pedidos en los restaurantes
2. **ADMIN**: Administrador del sistema con acceso completo
3. **PROPIETARIO**: Propietario de restaurante que puede gestionar su establecimiento
4. **EMPLEADO**: Empleado de restaurante que puede preparar pedidos

> **Nota:** No existen endpoints para crear, eliminar o modificar roles. Los roles son fijos y se insertan automáticamente en la base de datos al iniciar el sistema.

## 🗄️ Base de Datos

### Tablas principales:

- **usuarios**: Información de usuarios del sistema
- **roles**: Roles disponibles en el sistema


## 🔧 Configuración

### Variables de entorno:

Crea un archivo `.env` en la raíz del proyecto con las siguientes variables:

jwt:
  secret: plazaComidasSecretKey2024ForUserMicroservice
  expiration: 86400000 # 24 horas
```

## 🚀 Ejecución

### Prerrequisitos:
- Java 17 o superior
- MySQL 8.0
- Gradle

### Pasos:
1. Crear la base de datos MySQL:
```sql
CREATE DATABASE users;
```

2. Ejecutar la aplicación:
```bash
./gradlew bootRun
```

3. Acceder a la documentación:
```
http://localhost:8081/swagger-ui/index.html
```

## 📚 Endpoints

### Autenticación
- `POST /api/v1/auth/login` - Iniciar sesión
- `POST /api/v1/auth/refresh` - Refrescar token de acceso

### Roles
- `GET /api/v1/roles` - Listar todos los roles (id, nombre, descripción)
- `GET /api/v1/roles/{id}/name` - Obtener el nombre del rol por ID
- `GET /api/v1/roles/{id}/description` - Obtener la descripción del rol por ID

### Usuarios
- `GET /api/v1/users` - Obtener todos los usuarios
- `GET /api/v1/users/{id}` - Obtener usuario por ID
- `GET /api/v1/users/email/{email}` - Obtener usuario por email
- `GET /api/v1/users/document/{documentNumber}` - Obtener usuario por número de documento
- `POST /api/v1/users` - Crear nuevo usuario
- `DELETE /api/v1/users/{id}` - Eliminar usuario

### Objetos (Object)
- `GET /api/v1/object` - Obtener todos los objetos
- `POST /api/v1/object` - Crear nuevo objeto

## 🔐 Seguridad

- Las contraseñas se encriptan con BCrypt
- Autenticación basada en JWT
- CORS configurado para desarrollo
- Endpoints de autenticación y documentación públicos
- Variables de entorno para credenciales sensibles
- Relaciones JPA configuradas con `@EntityGraph` para evitar `LazyInitializationException`
- Roles dinámicos basados en la base de datos

## 📝 Estructura del Proyecto

```
src/main/java/com/pragma/plazacomida/
├── application/
│   ├── dto/
│   │   ├── request/
│   │   └── response/
│   ├── handler/
│   │   └── impl/
│   └── mapper/
├── domain/
│   ├── api/
│   ├── model/
│   ├── spi/
│   └── usecase/
└── infrastructure/
    ├── configuration/
    ├── documentation/
    ├── exception/
    ├── exceptionhandler/
    ├── input/
    │   └── rest/
    └── output/
        ├── adapter/
        ├── entity/
        ├── mapper/
        └── repository/
```

## 🧪 Testing

Ejecutar tests:
```bash
./gradlew test
```

## 📦 Build

Generar JAR:
```bash
./gradlew build
```

## 🔄 Próximos Pasos

Este microservicio está preparado para integrarse con otros microservicios de la plaza de comidas:
- Microservicio de Restaurantes
- Microservicio de Pedidos
- Microservicio de Platos

Cada historia de usuario (HU) se desarrollará en una rama separada siguiendo Git Flow.


