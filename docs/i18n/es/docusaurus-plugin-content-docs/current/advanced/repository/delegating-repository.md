---
title: Fuentes de datos personalizadas
sidebar_position: 4
_i18n_hash: 44f087c7c2308fc7a0c3b8c4c4246531
---
<!-- vale off -->
# Fuentes de datos personalizadas <DocChip chip='since' label='25.02' />
<!-- vale on -->

Cuando tus datos están fuera de tu aplicación - en una API REST, base de datos o servicio externo - necesitas crear una implementación de repositorio personalizada. La clase <JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> hace esto sencillo al permitirte proporcionar funciones en lugar de implementar una clase completa.

## Cómo funciona `DelegatingRepository` {#how-delegatingrepository-works}

<JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> es una clase concreta que extiende <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink>. En lugar de implementar métodos abstractos, proporcionas tres funciones en el constructor:

```java
DelegatingRepository<User, UserFilter> repository = new DelegatingRepository<>(
    // 1. Función de búsqueda - devuelve datos filtrados/ordenados/paginados
    criteria -> userService.findUsers(criteria),
    
    // 2. Función de conteo - devuelve el conteo total para el filtro
    criteria -> userService.countUsers(criteria),
    
    // 3. Función de búsqueda por clave - devuelve una única entidad por ID
    userId -> userService.findById(userId)
);
```

Cada función cumple un propósito específico:

**Función de búsqueda** recibe un objeto `RepositoryCriteria` que contiene:
- `getFilter()` - tu objeto de filtro personalizado (el parámetro de tipo `F`)
- `getOffset()` y `getLimit()` - para paginación
- `getOrderCriteria()` - lista de reglas de ordenación

Esta función debe devolver un `Stream<T>` de entidades que coincidan con los criterios. El flujo puede estar vacío si no se encuentran coincidencias.

**Función de conteo** también recibe los criterios, pero generalmente solo utiliza la parte del filtro. Devuelve el conteo total de entidades coincidentes, ignorando la paginación. Esto es utilizado por los componentes de UI para mostrar resultados totales o calcular páginas.

**Función de búsqueda por clave** recibe una clave de entidad (generalmente un ID) y devuelve un `Optional<T>`. Devuelve `Optional.empty()` si la entidad no existe.

## Ejemplo de API REST {#rest-api-example}

Al integrarte con una API REST, necesitas convertir los criterios del repositorio en parámetros de solicitud HTTP. Comienza definiendo una clase de filtro que coincida con las capacidades de consulta de tu API:

```java
public class UserFilter {
    private String department;
    private String status;
    // getters and setters...
}
```

Esta clase de filtro representa los parámetros de búsqueda que tu API acepta. El repositorio pasará instancias de esta clase a tus funciones cuando se aplique el filtrado.

Crea el repositorio con funciones que traduzcan criterios a llamadas a la API:

```java
DelegatingRepository<User, UserFilter> apiRepository = new DelegatingRepository<>(
    // Encontrar usuarios
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

El método `buildParams()` extraería valores de los criterios y los convertiría en parámetros de consulta como `?department=Sales&status=active&offset=20&limit=10`. Tu cliente REST luego realiza la solicitud HTTP real y deserializa la respuesta.

## Ejemplo de base de datos {#database-example}

La integración con bases de datos sigue un patrón similar pero convierte los criterios en consultas SQL. La diferencia clave es manejar la generación de SQL y el enlace de parámetros:

```java
DelegatingRepository<Customer, CustomerFilter> dbRepository = new DelegatingRepository<>(
    // Consulta con filtro, orden, paginación
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

El método `buildQuery()` construiría una consulta SQL como:
```sql
SELECT * FROM customers 
WHERE status = ? AND region = ?
ORDER BY created_date DESC, name ASC
LIMIT ? OFFSET ?
```

Las propiedades de tu objeto de filtro se mapean a las condiciones de la cláusula `WHERE`, mientras que la paginación y el orden se manejan a través de las cláusulas `LIMIT/OFFSET` y `ORDER BY`.

## Uso con componentes de UI {#using-with-ui-components}

La belleza del patrón de repositorio es que los componentes de UI no saben ni les importa de dónde provienen los datos. Ya sea que se trate de una colección en memoria, una API REST o una base de datos, el uso es idéntico:

```java
// Crear y configurar el repositorio
Repository<User> repository = createApiRepository();
UserFilter filter = new UserFilter();
filter.setDepartment("Engineering");
repository.setBaseFilter(filter);

// Conectar a la tabla
Table<User> table = new Table<>();
table.setRepository(repository);

// La tabla muestra automáticamente los usuarios filtrados de ingeniería
```

Cuando los usuarios interactúan con la [`Table`](../../components/table/overview) (ordenando columnas, cambiando páginas), la `Table` llama a tus funciones de repositorio con criterios actualizados. Tus funciones traducen estos en llamadas a la API o consultas SQL, y la tabla se actualiza automáticamente con los resultados.

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
