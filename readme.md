# 📋 Employees Service - Documentación Integral del Proyecto

Esta documentación consolida toda la arquitectura, configuración, código fuente y despliegue del microservicio **Employees Service** desarrollado en **Spring Boot**.

---

## 🏗️ 1. Estructura de Paquetes y Clases

El proyecto sigue una arquitectura en capas estándar de Spring Boot:

```text
com.employeesservice.employees
├── EmployeesApplication.java
├── controller
│   └── EmployeesController.java
├── service
│   └── EmployeeService.java
├── repository
│   └── EmployeeRepository.java
├── model
│   └── Employee.java
├── dto
│   └── RequestEmployeeDto.java
└── mapper
    └── EmployeeMapper.java
```

---


## 🐳 2. Configuración de Docker y Despliegue

### `Dockerfile`
```dockerfile
# Etapa de compilación con Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa de ejecución con JRE
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### `docker-compose.yml`
```yaml
services:
  mysql:
    image: mysql:8.0
    container_name: mysql_db
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: pass
      MYSQL_DATABASE: empleadosdb
      MYSQL_USER: emp
      MYSQL_PASSWORD: emp
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql

  employees-service:
    build: .
    container_name: employees_service_app
    restart: always
    ports:
      - "8080:8080"
    environment:
      - SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/empleadosdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
      - SPRING_DATASOURCE_USERNAME=emp
      - SPRING_DATASOURCE_PASSWORD=emp
      - SPRING_JPA_HIBERNATE_DDL_AUTO=update
    depends_on:
      - mysql

volumes:
  mysql_data:
```

---

## 🚀 3. Comandos de Ejecución Rápidos

1. **Levantar todo el entorno (Base de datos + Aplicación):**
   ```bash
   docker compose up --build
   ```
2. **Endpoints disponibles:**
   * `GET http://localhost:8080/employees` (Lista activos)
   * `GET http://localhost:8080/employees/{id}` (Obtiene un empleado)
   * `GET http://localhost:8080/employees/search?name=` (Busca coincidencias por firstName y MiddleName)
   * `POST http://localhost:8080/employees/` (Crea empleado)
   * `PUT http://localhost:8080/employees/{id}` (Actualiza empleado)
   * `DELETE http://localhost:8080/employees/{id}` (Pone empleado como inactivo)
 * `GET http://localhost:8080/actuator` (Health Check)"# EmployeeService" 
