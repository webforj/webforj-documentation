---
title: Custom data sources
sidebar_position: 4
_i18n_hash: dbcaaa420987ee45f54d3f4059e98d92
---
<!-- vale off -->
# Fuentes de datos personalizadas <DocChip chip='since' label='25.02' />
<!-- vale on -->

Cuando tus datos se encuentran fuera de tu aplicación - en una API REST, base de datos o servicio externo - necesitas crear una implementación de repositorio personalizada. La clase <JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> lo hace sencillo al permitirte proporcionar funciones en lugar de implementar una clase completa.

## Cómo funciona `DelegatingRepository` {#how-delegatingrepository-works}

<JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> es una clase concreta que extiende <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink>. En lugar de implementar métodos abstractos, proporcionas tres funciones en el constructor:

```java
DelegatingRepository<User, UserFilter> repository = new DelegatingRepository<>(
    // 1. Función de búsqueda - devuelve datos filtrados/ordenados/paginados
    criteria -> userService.findUsers(criteria),
    
    // 2. Función de conteo - devuelve el total de elementos para el filtro
    criteria -> userService.countUsers(criteria),
    
    // 3. Función de búsqueda por clave - devuelve una única entidad por ID
    userId -> userService.findById(userId)
);
```

Cada función tiene un propósito específico:

**Función de búsqueda** recibe un objeto `RepositoryCriteria` que contiene:
- `getFilter()` - tu objeto de filtro personalizado (el parámetro de tipo `F`)
- `getOffset()` y `getLimit()` - para paginación
- `getOrderCriteria()` - lista de reglas de ordenamiento

Esta función debe retornar un `Stream<T>` de entidades que coincidan con los criterios. El stream puede estar vacío si no se encuentran coincidencias.

**Función de conteo** también recibe los criterios pero típicamente solo utiliza la parte del filtro. Retorna el conteo total de entidades coincidentes, ignorando la paginación. Esto es utilizado por los componentes de la UI para mostrar el total de resultados o calcular páginas.

**Función de búsqueda por clave** recibe una clave de entidad (usualmente un ID) y retorna un `Optional<T>`. Retorna `Optional.empty()` si la entidad no existe.

## Ejemplo de API REST {#rest-api-example}

Al integrar con una API REST, necesitas convertir los criterios del repositorio en parámetros de solicitudes HTTP. Comienza definiendo una clase de filtro que coincida con las capacidades de consulta de tu API:

```java
public class UserFilter {
    private String department;
    private String status;
    // getters y setters...
}
```

Esta clase de filtro representa los parámetros de búsqueda que tu API acepta. El repositorio pasará instancias de esta clase a tus funciones cuando se aplique el filtrado.

Crea el repositorio con funciones que traduce los criterios a llamadas a la API:

```java
DelegatingRepository<User, UserFilter> apiRepository = new DelegatingRepository<>(
    // Buscar usuarios
    criteria -> {
        Map<String, String> params = buildParams(criteria);
        List<User> users = restClient.get("/users", params);
        return users.stream();
    },
    
    // Contar usuarios
    criteria -> {
        Map<String, String> filterParams = buildFilterParams(criteria.getFilter());
        return restClient.getCount("/users/count", filterParams);
    },
    
    // Buscar por ID
    userId -> restClient.getById("/users/" + userId)
);
```

El método `buildParams()` extraería valores de los criterios y los convertiría en parámetros de consulta como `?department=Sales&status=active&offset=20&limit=10`. Tu cliente REST entonces realiza la solicitud HTTP real y deserializa la respuesta.

## Ejemplo de base de datos {#database-example}

La integración con bases de datos sigue un patrón similar pero convierte los criterios en consultas SQL. La principal diferencia es el manejo de la generación de SQL y la vinculación de parámetros:

```java
DelegatingRepository<Customer, CustomerFilter> dbRepository = new DelegatingRepository<>(
    // Consultar con filtro, ordenamiento, paginación
    criteria -> {
        String sql = buildQuery(criteria);
        return jdbcTemplate.queryForStream(sql, rowMapper);
    },
    
    // Contar registros coincidentes
    criteria -> {
        String countSql = buildCountQuery(criteria.getFilter());
        return jdbcTemplate.queryForObject(countSql, Integer.class);
    },
    
    // Buscar por clave primaria
    customerId -> {
        String sql = "SELECT * FROM customers WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, customerId);
    }
);
```

El método `buildQuery()` construiría SQL como:
```sql
SELECT * FROM customers 
WHERE status = ? AND region = ?
ORDER BY created_date DESC, name ASC
LIMIT ? OFFSET ?
```

Las propiedades de tu objeto de filtro se mapean a condiciones de la cláusula `WHERE`, mientras que la paginación y el ordenamiento se manejan a través de las cláusulas `LIMIT/OFFSET` y `ORDER BY`.

## Usando con componentes de UI {#using-with-ui-components}

La belleza del patrón de repositorio es que los componentes de la UI no saben ni les importa de dónde provienen los datos. Ya sea una colección en memoria, una API REST o una base de datos, el uso es idéntico:

```java
// Crear y configurar repositorio
Repository<User> repository = createApiRepository();
UserFilter filter = new UserFilter();
filter.setDepartment("Engineering");
repository.setBaseFilter(filter);

// Adjuntar a la tabla
Table<User> table = new Table<>();
table.setRepository(repository);

// La tabla muestra automáticamente a los usuarios filtrados de ingeniería
```

Cuando los usuarios interactúan con la [`Table`](../../components/table/overview) (ordenando columnas, cambiando páginas), la `Table` llama a tus funciones del repositorio con criterios actualizados. Tus funciones traducen esto a llamadas a la API o consultas SQL, y la tabla se actualiza automáticamente con los resultados.

## Cuándo extender `AbstractQueryableRepository` {#when-to-extend-abstractqueryablerepository}

Si necesitas métodos personalizados o inicialización compleja, extiende <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink> directamente:

```java
public class CustomUserRepository extends AbstractQueryableRepository<User, UserFilter> {
    @Override
    public Stream<User> findBy(RepositoryCriteria<User, UserFilter> criteria) {
        // Implementación
    }
    
    @Override
    public int size(RepositoryCriteria<User, UserFilter> criteria) {
        // Implementación
    }
    
    @Override
    public Optional<User> find(Object key) {
        // Implementación
    }
    
    // Agregar métodos personalizados
    public List<User> findActiveManagers() {
        // Lógica de consulta personalizada
    }
}
```
