# Plaza Comida - Microservicio de Usuarios

Este es el microservicio de usuarios para la aplicación Plaza Comida, desarrollado siguiendo la **arquitectura hexagonal (Clean Architecture)** con Spring Boot 3.2.12.

## 🏗️ Arquitectura Hexagonal

El proyecto implementa una arquitectura hexagonal completa con las siguientes capas:

### **Domain Layer (Núcleo de Negocio)**
- **api/**: Interfaces de puertos de entrada (IUserApi, IRoleServicePort)
- **model/**: Modelos de dominio (User, Role)
- **usecase/**: Casos de uso de negocio (UserUseCase, RoleUseCase)
- **spi/**: Interfaces de puertos de salida (IUserPersistencePort, IRolePersistencePort)
- **exception/**: Excepciones de dominio

### **Application Layer (Orquestación)**
- **dto/request/**: DTOs de entrada (UserRequest, RoleRequest)
- **dto/response/**: DTOs de salida (UserResponse, RoleResponse)
- **handler/**: Manejadores de peticiones (UserHandler, RoleHandler)
- **mapper/**: Mapeadores entre DTOs y modelos (IUserMapper)

### **Infrastructure Layer (Adaptadores)**
- **input/rest/**: Controladores REST (UserController, RoleController)
- **output/adapter/**: Adaptadores de persistencia (UserJpaAdapter, RoleJpaAdapter)
- **output/entity/**: Entidades JPA (UserEntity, RoleEntity)
- **output/repository/**: Repositorios JPA (IUserRepository, IRoleRepository)
- **output/mapper/**: Mapeadores de entidades (IUserEntityMapper)
- **configuration/**: Configuraciones de Spring
- **exceptionhandler/**: Manejadores de excepciones

## 🚀 Tecnologías

- **Spring Boot 3.2.12** - Framework principal
- **Spring Security 6** - Configuración de seguridad
- **Spring Data JPA** - Persistencia de datos
- **H2 Database** - Base de datos en memoria para desarrollo
- **MapStruct** - Mapeo de objetos
- **Lombok** - Reducción de código boilerplate
- **OpenAPI 3** - Documentación de API
- **Jakarta Validation** - Validación de datos
- **UUID** - Identificadores únicos universales
- **BCrypt** - Encriptación de contraseñas


## 🚀 Ejecución

### **Prerrequisitos:**
- Java 17 o superior
- Gradle 8.x

### **Pasos:**
1. **Ejecutar la aplicación:**
```bash
./gradlew bootRun
```

2. **Acceder a la documentación OpenAPI:**
```
http://localhost:8080/swagger-ui/index.html
```

### **Ejecutar todos los tests:**
```bash
./gradlew test
```

### **Generar JAR:**
```bash
./gradlew build
```

## 📖 Documentación

- **OpenAPI/Swagger**: http://localhost:8080/swagger-ui/index.html
- **API Docs JSON**: http://localhost:8080/v3/api-docs



