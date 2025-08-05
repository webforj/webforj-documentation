---
title: Querying data
sidebar_position: 3
_i18n_hash: c5508e014de2ca1de7543b34e39731bc
---
<!-- vale off -->
# Consultando datos <DocChip chip='since' label='25.02' />
<!-- vale on -->

La interfaz <JavadocLink type="data" location="com/webforj/data/repository/QueryableRepository" code="true">QueryableRepository</JavadocLink> extiende `Repository` con consultas avanzadas a través de <JavadocLink type="data" location="com/webforj/data/repository/RepositoryCriteria" code="true">RepositoryCriteria</JavadocLink>. A diferencia de los repositorios básicos que solo admiten filtrado simple, los repositorios consultables proporcionan consultas estructuradas con tipos de filtro personalizados, ordenamiento y paginación.

## Entendiendo los tipos de filtro {#understanding-filter-types}

<JavadocLink type="data" location="com/webforj/data/repository/QueryableRepository" code="true">QueryableRepository</JavadocLink> introduce un segundo parámetro genérico para el tipo de filtro: `QueryableRepository<T, F>` donde `T` es tu tipo de entidad y `F` es tu tipo de filtro personalizado.

Esta separación existe porque diferentes fuentes de datos utilizan diferentes lenguajes de consulta:

```java
// Filtros Predicate para colecciones en memoria
QueryableRepository<Product, Predicate<Product>> inMemoryRepo = 
    new CollectionRepository<>(products);

// Objetos de filtro personalizados para APIs REST o bases de datos  
QueryableRepository<User, UserFilter> apiRepo = 
    new DelegatingRepository<>(/* implementación */);

// Consultas en cadena para motores de búsqueda
QueryableRepository<Document, String> searchRepo = 
    new DelegatingRepository<>(/* implementación */);
```

`CollectionRepository` utiliza `Predicate<Product>` porque filtra objetos Java en memoria. El repositorio de API REST utiliza `UserFilter` - una clase personalizada con campos como `department` y `status` que se mapean a parámetros de consulta. El repositorio de búsqueda utiliza cadenas simples para consultas de texto completo.

Los componentes de UI no se preocupan por estas diferencias. Llaman a `setBaseFilter()` con cualquier tipo de filtro que el repositorio espera, y el repositorio se encarga de la traducción.

## Construyendo consultas con criterios de repositorio {#building-queries-with-repository-criteria}

<JavadocLink type="data" location="com/webforj/data/repository/RepositoryCriteria" code="true">RepositoryCriteria</JavadocLink> agrupa todos los parámetros de consulta en un solo objeto inmutable. En lugar de llamar a métodos separados para filtrar, ordenar y paginar, pasas todo de una vez:

```java
// Consulta completa con todos los parámetros
RepositoryCriteria<Product, Predicate<Product>> criteria = 
    new RepositoryCriteria<>(
        20,                                       // offset - omitir los primeros 20
        10,                                       // limit - tomar 10 elementos  
        orderCriteria,                           // reglas de ordenamiento
        product -> product.getPrice() < 100.0    // condición de filtro
    );

// Ejecutar la consulta
Stream<Product> results = repository.findBy(criteria);
int totalMatching = repository.size(criteria);
```

El método `findBy()` ejecuta la consulta completa - aplica el filtro, ordena los resultados, omite el offset y toma el límite. El método `size()` cuenta todos los elementos que coinciden con el filtro, ignorando la paginación.

También puedes crear criterios con solo las partes que necesitas:

```java
// Solo filtro
RepositoryCriteria<Product, Predicate<Product>> filterOnly = 
    new RepositoryCriteria<>(product -> product.isActive());

// Solo paginación  
RepositoryCriteria<Product, Predicate<Product>> pageOnly = 
    new RepositoryCriteria<>(0, 25);
```

## Trabajando con diferentes tipos de filtro {#working-with-different-filter-types}

### Filtros Predicate {#predicate-filters}

Para colecciones en memoria, utiliza `Predicate<T>` para componer filtros funcionales:

```java
CollectionRepository<Product> repository = new CollectionRepository<>(products);

// Construir predicados complejos
Predicate<Product> activeProducts = product -> product.isActive();
Predicate<Product> inStock = product -> product.getStock() > 0;
Predicate<Product> affordable = product -> product.getPrice() < 50.0;

// Combinar condiciones
repository.setBaseFilter(activeProducts.and(inStock).and(affordable));

// Filtrado dinámico
Predicate<Product> filter = product -> true;
if (categoryFilter != null) {
    filter = filter.and(p -> p.getCategory().equals(categoryFilter));
}
if (maxPrice != null) {
    filter = filter.and(p -> p.getPrice() <= maxPrice);
}
repository.setBaseFilter(filter);
```

### Objetos de filtro personalizados {#custom-filter-objects}

Las fuentes de datos externas no pueden ejecutar predicados de Java. En su lugar, debes crear clases de filtro que representen lo que tu backend puede buscar:

```java
public class ProductFilter {
    private String category;
    private BigDecimal maxPrice;
    private Boolean inStock;
    
    // getters y setters...
}

// Usar con repositorio personalizado
ProductFilter filter = new ProductFilter();
filter.setCategory("Electrónica");
filter.setMaxPrice(new BigDecimal("99.99"));
filter.setInStock(true);

RepositoryCriteria<Product, ProductFilter> criteria = 
    new RepositoryCriteria<>(filter);

Stream<Product> results = customRepository.findBy(criteria);
```

Dentro del método `findBy()` de tu repositorio personalizado, deberías traducir este objeto de filtro:
- Para APIs REST: Convertir a parámetros de consulta como `?category=Electronics&maxPrice=99.99&inStock=true`
- Para SQL: Construir una cláusula where como `WHERE category = ? AND price <= ? AND stock > 0`
- Para GraphQL: Construir una consulta con las selecciones de campos apropiadas

La implementación de `Repository` debería manejar esta traducción, manteniendo tu código de UI limpio.

## Ordenando datos {#sorting-data}

<JavadocLink type="data" location="com/webforj/data/repository/OrderCriteria" code="true">OrderCriteria</JavadocLink> define cómo ordenar tus datos. Cada `OrderCriteria` necesita un proveedor de valores (cómo obtener el valor de tu entidad) y una dirección:

```java
// Ordenamiento por un solo campo
OrderCriteria<Employee, String> byName = 
    new OrderCriteria<>(Employee::getName, OrderCriteria.Direction.ASC);

// Ordenamiento multilínea - departamento primero, luego salario, luego nombre
OrderCriteriaList<Employee> sorting = new OrderCriteriaList<>();
sorting.add(new OrderCriteria<>(Employee::getDepartment, OrderCriteria.Direction.ASC));
sorting.add(new OrderCriteria<>(Employee::getSalary, OrderCriteria.Direction.DESC));  
sorting.add(new OrderCriteria<>(Employee::getName, OrderCriteria.Direction.ASC));

// Usar en criterios
RepositoryCriteria<Employee, Predicate<Employee>> criteria = 
    new RepositoryCriteria<>(0, 50, sorting, employee -> employee.isActive());
```

El proveedor de valores (`Employee::getName`) funciona para ordenamiento en memoria. Pero las fuentes de datos externas no pueden ejecutar funciones de Java. Para esos casos, `OrderCriteria` acepta un nombre de propiedad:

```java
// Para repositorios externos - proporcionar tanto el getter de valor como el nombre de propiedad
OrderCriteria<Employee, String> byName = new OrderCriteria<>(
    Employee::getName,           // Para ordenamiento en memoria
    Direction.ASC,
    null,                       // Comparador personalizado (opcional)
    "name"                      // Nombre de propiedad para ordenamiento en backend
);
```

`CollectionRepository` utiliza el proveedor de valor para ordenar objetos Java. Las implementaciones de `DelegatingRepository` pueden usar el nombre de propiedad para construir cláusulas de orden en SQL o `sort=name:asc` en APIs REST.

## Controlando la paginación {#controlling-pagination}

Establece offset y limit para controlar qué porción de datos cargar:

```java
// Paginación basada en páginas
int page = 2;          // número de página basado en cero
int pageSize = 20;     // elementos por página
int offset = page * pageSize;

RepositoryCriteria<Product, Predicate<Product>> criteria = 
    new RepositoryCriteria<>(offset, pageSize, null, yourFilter);

// Carga progresiva - cargar más datos de forma incremental  
int currentlyLoaded = 50;
int loadMore = 25;

repository.setOffset(0);
repository.setLimit(currentlyLoaded + loadMore);
```
