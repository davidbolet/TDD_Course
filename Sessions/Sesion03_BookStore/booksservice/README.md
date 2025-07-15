# Microservicio de Librería

Un microservicio completo para la gestión de libros desarrollado con Spring Boot, H2 Database y JPA.

## Características

- ✅ Base de datos H2 en memoria
- ✅ API REST completa con validaciones
- ✅ Arquitectura en capas (Controller, Service, Repository)
- ✅ Manejo de excepciones global
- ✅ Pruebas unitarias con JUnit 5 y Mockito
- ✅ Pruebas de integración con AssertJ
- ✅ Cobertura de código con JaCoCo
- ✅ Datos de prueba automáticos
- ✅ Documentación completa

## Tecnologías Utilizadas

- **Spring Boot 3.5.3**
- **Spring Data JPA**
- **H2 Database**
- **JUnit 5**
- **Mockito**
- **AssertJ**
- **JaCoCo**
- **Maven**

## Estructura del Proyecto

```
src/
├── main/java/com/talant/bootcamp/demoservice/
│   ├── controller/
│   │   └── BookController.java
│   ├── service/
│   │   └── BookService.java
│   ├── repository/
│   │   └── BookRepository.java
│   ├── model/
│   │   ├── Book.java
│   │   └── BookCategory.java
│   ├── dto/
│   │   ├── BookRequest.java
│   │   └── BookResponse.java
│   ├── exception/
│   │   ├── BookNotFoundException.java
│   │   ├── DuplicateIsbnException.java
│   │   └── GlobalExceptionHandler.java
│   └── config/
│       └── DataLoader.java
└── test/java/com/talant/bootcamp/demoservice/
    ├── service/
    │   └── BookServiceTest.java
    ├── controller/
    │   └── BookControllerTest.java
    └── integration/
        └── BookIntegrationTest.java
```

## Instalación y Ejecución

### Prerrequisitos

- Java 17 o superior
- Maven 3.6 o superior

### Pasos de Instalación

1. **Clonar el repositorio**
   ```bash
   git clone <repository-url>
   cd demoservice
   ```

2. **Compilar el proyecto**
   ```bash
   mvn clean compile
   ```

3. **Ejecutar las pruebas**
   ```bash
   mvn test
   ```

4. **Ejecutar la aplicación**
   ```bash
   mvn spring-boot:run
   ```

5. **Verificar cobertura de código**
   ```bash
   mvn jacoco:report
   ```

## Endpoints de la API

### Operaciones CRUD Básicas

#### Crear Libro
```http
POST /api/books
Content-Type: application/json

{
  "title": "El Señor de los Anillos",
  "author": "J.R.R. Tolkien",
  "isbn": "9788445071405",
  "description": "Una épica historia de fantasía",
  "price": 29.99,
  "stock": 50,
  "category": "FANTASY"
}
```

#### Obtener Todos los Libros
```http
GET /api/books
```

#### Obtener Libro por ID
```http
GET /api/books/{id}
```

#### Obtener Libro por ISBN
```http
GET /api/books/isbn/{isbn}
```

#### Actualizar Libro
```http
PUT /api/books/{id}
Content-Type: application/json

{
  "title": "Título Actualizado",
  "author": "Autor Actualizado",
  "isbn": "9788445071405",
  "description": "Descripción actualizada",
  "price": 39.99,
  "stock": 25,
  "category": "SCIENCE_FICTION"
}
```

#### Eliminar Libro
```http
DELETE /api/books/{id}
```

### Búsquedas y Filtros

#### Buscar por Autor
```http
GET /api/books/author/{author}
```

#### Buscar por Título
```http
GET /api/books/title/{title}
```

#### Buscar por Categoría
```http
GET /api/books/category/{category}
```

#### Libros con Stock Disponible
```http
GET /api/books/in-stock
```

#### Libros sin Stock
```http
GET /api/books/out-of-stock
```

#### Libros con Stock Bajo
```http
GET /api/books/low-stock
```

#### Búsqueda por Texto (Título o Autor)
```http
GET /api/books/search?q={searchTerm}
```

### Filtros por Precio

#### Por Rango de Precio
```http
GET /api/books/price-range?minPrice=20.00&maxPrice=40.00
```

#### Por Precio Máximo
```http
GET /api/books/max-price/{maxPrice}
```

#### Por Precio Mínimo
```http
GET /api/books/min-price/{minPrice}
```

### Ordenamiento

#### Por Precio Ascendente
```http
GET /api/books/sorted/price-asc
```

#### Por Precio Descendente
```http
GET /api/books/sorted/price-desc
```

#### Por Título
```http
GET /api/books/sorted/title
```

#### Por Autor
```http
GET /api/books/sorted/author
```

### Operaciones Especiales

#### Actualizar Stock
```http
PATCH /api/books/{id}/stock?stock=25
```

#### Verificar Existencia por ISBN
```http
GET /api/books/exists/{isbn}
```

#### Obtener Categorías Disponibles
```http
GET /api/books/categories
```

## Categorías de Libros Disponibles

- `FICTION` - Ficción
- `NON_FICTION` - No Ficción
- `SCIENCE_FICTION` - Ciencia Ficción
- `FANTASY` - Fantasía
- `MYSTERY` - Misterio
- `THRILLER` - Suspenso
- `ROMANCE` - Romance
- `BIOGRAPHY` - Biografía
- `HISTORY` - Historia
- `SCIENCE` - Ciencia
- `TECHNOLOGY` - Tecnología
- `BUSINESS` - Negocios
- `SELF_HELP` - Autoayuda
- `COOKING` - Cocina
- `TRAVEL` - Viajes
- `CHILDREN` - Infantil
- `YOUNG_ADULT` - Juvenil
- `ACADEMIC` - Académico
- `REFERENCE` - Referencia
- `OTHER` - Otro

## Validaciones

### Campos Requeridos
- `title`: Título del libro (1-255 caracteres)
- `author`: Autor del libro (1-255 caracteres)
- `isbn`: ISBN del libro (10 o 13 dígitos)
- `price`: Precio del libro (mayor a 0, máximo 9999.99)
- `stock`: Cantidad en stock (0-999999)
- `category`: Categoría del libro

### Validaciones Específicas
- **ISBN**: Debe tener exactamente 10 o 13 dígitos numéricos
- **Precio**: Debe ser mayor a 0 y no exceder 9999.99
- **Stock**: No puede ser negativo y no puede exceder 999999
- **Título y Autor**: No pueden estar vacíos y no pueden exceder 255 caracteres
- **Descripción**: Opcional, máximo 1000 caracteres

## Manejo de Errores

### Códigos de Estado HTTP

- `200 OK` - Operación exitosa
- `201 Created` - Recurso creado exitosamente
- `204 No Content` - Recurso eliminado exitosamente
- `400 Bad Request` - Datos de entrada inválidos
- `404 Not Found` - Libro no encontrado
- `409 Conflict` - ISBN duplicado
- `500 Internal Server Error` - Error interno del servidor

### Formato de Respuesta de Error

```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Error de validación",
  "message": "Los datos proporcionados no son válidos",
  "details": {
    "title": "El título es obligatorio",
    "isbn": "El ISBN debe tener 10 o 13 dígitos"
  }
}
```

## Pruebas

### Ejecutar Todas las Pruebas
```bash
mvn test
```

### Ejecutar Pruebas Unitarias
```bash
mvn test -Dtest=*Test
```

### Ejecutar Pruebas de Integración
```bash
mvn test -Dtest=*IntegrationTest
```

### Verificar Cobertura de Código
```bash
mvn jacoco:report
```

El reporte de cobertura se genera en: `target/site/jacoco/index.html`

## Base de Datos

### H2 Console
La consola de H2 está disponible en: http://localhost:8080/h2-console

**Configuración de conexión:**
- JDBC URL: `jdbc:h2:mem:bookstoredb`
- Username: `sa`
- Password: `password`

### Datos de Prueba
La aplicación carga automáticamente 15 libros de muestra al iniciar, incluyendo:
- El Señor de los Anillos
- 1984
- Cien años de soledad
- El Principito
- Don Quijote de la Mancha
- Clean Code
- Design Patterns
- Steve Jobs
- Sapiens
- El arte de la guerra
- Los 7 hábitos de la gente altamente efectiva
- Cocina para principiantes
- Viajes por España
- Harry Potter y la piedra filosofal
- El código Da Vinci

## Ejemplos de Uso

### Crear un Nuevo Libro
```bash
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Clean Architecture",
    "author": "Robert C. Martin",
    "isbn": "9780134494166",
    "description": "Guía para arquitectura de software",
    "price": 49.99,
    "stock": 20,
    "category": "TECHNOLOGY"
  }'
```

### Buscar Libros por Autor
```bash
curl http://localhost:8080/api/books/author/Tolkien
```

### Obtener Libros con Stock Disponible
```bash
curl http://localhost:8080/api/books/in-stock
```

### Actualizar Stock de un Libro
```bash
curl -X PATCH "http://localhost:8080/api/books/1/stock?stock=30"
```

## Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## Contacto

Para preguntas o sugerencias, por favor abre un issue en el repositorio. 