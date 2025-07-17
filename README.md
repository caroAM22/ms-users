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

El sistema maneja 4 roles principales:

1. **CLIENTE**: Usuario que puede realizar pedidos en los restaurantes
2. **ADMIN**: Administrador del sistema con acceso completo
3. **PROPIETARIO**: Propietario de restaurante que puede gestionar su establecimiento
4. **EMPLEADO**: Empleado de restaurante que puede preparar pedidos

## 🗄️ Base de Datos

### Tablas principales:

- **usuarios**: Información de usuarios del sistema
- **roles**: Roles disponibles en el sistema

### Configuración:
- Base de datos: `plaza_comidas_usuarios`
- Puerto: `3306`
- Usuario: `root`
- Contraseña: `1234`

## 🔧 Configuración

### Variables de entorno:
```yaml
server:
  port: 8081

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/plaza_comidas_usuarios
    username: root
    password: 1234

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
CREATE DATABASE plaza_comidas_usuarios;
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

### Roles
- `GET /api/v1/roles` - Obtener todos los roles
- `GET /api/v1/roles/{id}` - Obtener rol por ID
- `GET /api/v1/roles/nombre/{nombre}` - Obtener rol por nombre
- `POST /api/v1/roles` - Crear nuevo rol
- `DELETE /api/v1/roles/{id}` - Eliminar rol

### Usuarios
- `GET /api/v1/usuarios` - Obtener todos los usuarios
- `GET /api/v1/usuarios/{id}` - Obtener usuario por ID
- `GET /api/v1/usuarios/correo/{correo}` - Obtener usuario por correo
- `GET /api/v1/usuarios/documento/{numeroDocumento}` - Obtener usuario por documento
- `POST /api/v1/usuarios` - Crear nuevo usuario
- `DELETE /api/v1/usuarios/{id}` - Eliminar usuario

## 🔐 Seguridad

- Las contraseñas se encriptan con BCrypt
- Autenticación basada en JWT
- CORS configurado para desarrollo
- Endpoints de autenticación y documentación públicos

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


