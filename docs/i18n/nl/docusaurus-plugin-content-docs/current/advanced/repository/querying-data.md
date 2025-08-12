---
title: Querying data
sidebar_position: 3
_i18n_hash: 96551b4f47c7019b8bdd43b57f716c88
---
<!-- vale off -->
# Gegevens opvragen <DocChip chip='since' label='25.02' />
<!-- vale on -->

De <JavadocLink type="data" location="com/webforj/data/repository/QueryableRepository" code="true">QueryableRepository</JavadocLink> interface breidt `Repository` uit met geavanceerd opvragen via <JavadocLink type="data" location="com/webforj/data/repository/RepositoryCriteria" code="true">RepositoryCriteria</JavadocLink>. In tegenstelling tot basisrepositories die alleen eenvoudige filtering ondersteunen, bieden opvraagbare repositories gestructureerd opvragen met aangepaste filtertypes, sorteren en paginering.

## Het begrijpen van filtertypes {#understanding-filter-types}

<JavadocLink type="data" location="com/webforj/data/repository/QueryableRepository" code="true">QueryableRepository</JavadocLink> introduceert een tweede generieke parameter voor het filtertype: `QueryableRepository<T, F>` waarbij `T` uw entiteitstype is en `F` uw aangepaste filtertype.

Deze scheiding bestaat omdat verschillende gegevensbronnen verschillende querytalen gebruiken:

```java
// Predicate-filters voor in-memory collecties
QueryableRepository<Product, Predicate<Product>> inMemoryRepo = 
    new CollectionRepository<>(products);

// Aangepaste filterobjecten voor REST-API's of databases  
QueryableRepository<User, UserFilter> apiRepo = 
    new DelegatingRepository<>(/* implementatie */);

// String-queries voor zoekmachines
QueryableRepository<Document, String> searchRepo = 
    new DelegatingRepository<>(/* implementatie */);
```

`CollectionRepository` gebruikt `Predicate<Product>` omdat het Java-objecten in het geheugen filtreert. Het REST API-repository gebruikt `UserFilter` - een aangepaste klasse met velden zoals `department` en `status` die overeenkomen met queryparameters. Het zoekrepository gebruikt gewone strings voor volledige tekstqueries.

UI-componenten maken zich geen zorgen over deze verschillen. Ze roepen `setBaseFilter()` aan met welk filtertype de repository verwacht, en de repository zorgt voor de vertaling.

## Query's bouwen met repositorycriteria {#building-queries-with-repository-criteria}

<JavadocLink type="data" location="com/webforj/data/repository/RepositoryCriteria" code="true">RepositoryCriteria</JavadocLink> bundelt alle queryparameters in één onveranderlijk object. In plaats van afzonderlijke methoden aan te roepen voor filteren, sorteren en paginering, geeft u alles in één keer door:

```java
// Volledige query met alle parameters
RepositoryCriteria<Product, Predicate<Product>> criteria = 
    new RepositoryCriteria<>(
        20,                                       // offset - sla de eerste 20 over
        10,                                       // limiet - neem 10 items  
        orderCriteria,                           // sorteerregels
        product -> product.getPrice() < 100.0    // filtervoorwaarde
    );

// Voer de query uit
Stream<Product> results = repository.findBy(criteria);
int totalMatching = repository.size(criteria);
```

De `findBy()`-methode voert de volledige query uit - het past de filter toe, sorteert de resultaten, slaat de offset over en neemt de limiet. De `size()`-methode telt alle items die overeenkomen met de filter, waarbij paginering wordt genegeerd.

U kunt ook criteria maken met alleen de delen die u nodig heeft:

```java
// Alleen filteren
RepositoryCriteria<Product, Predicate<Product>> filterOnly = 
    new RepositoryCriteria<>(product -> product.isActive());

// Alleen paginering  
RepositoryCriteria<Product, Predicate<Product>> pageOnly = 
    new RepositoryCriteria<>(0, 25);
```

## Werken met verschillende filtertypes {#working-with-different-filter-types}

### Predicate-filters {#predicate-filters}

Voor in-memory collecties, gebruik `Predicate<T>` om functionele filters samen te stellen:

```java
CollectionRepository<Product> repository = new CollectionRepository<>(products);

// Bouw complexe predikaten
Predicate<Product> activeProducts = product -> product.isActive();
Predicate<Product> inStock = product -> product.getStock() > 0;
Predicate<Product> affordable = product -> product.getPrice() < 50.0;

// Combineer voorwaarden
repository.setBaseFilter(activeProducts.and(inStock).and(affordable));

// Dynamisch filteren
Predicate<Product> filter = product -> true;
if (categoryFilter != null) {
    filter = filter.and(p -> p.getCategory().equals(categoryFilter));
}
if (maxPrice != null) {
    filter = filter.and(p -> p.getPrice() <= maxPrice);
}
repository.setBaseFilter(filter);
```

### Aangepaste filterobjecten {#custom-filter-objects}

Externe gegevensbronnen kunnen Java-predicaten niet uitvoeren. In plaats daarvan maakt u filterklassen die vertegenwoordigen wat uw backend kan doorzoeken:

```java
public class ProductFilter {
    private String category;
    private BigDecimal maxPrice;
    private Boolean inStock;
    
    // getters en setters...
}

// Gebruik met een aangepaste repository
ProductFilter filter = new ProductFilter();
filter.setCategory("Electronics");
filter.setMaxPrice(new BigDecimal("99.99"));
filter.setInStock(true);

RepositoryCriteria<Product, ProductFilter> criteria = 
    new RepositoryCriteria<>(filter);

Stream<Product> results = customRepository.findBy(criteria);
```

Binnen de `findBy()`-methode van uw aangepaste repository, zou u dit filterobject vertalen:
- Voor REST-API's: Converteren naar queryparameters zoals `?category=Electronics&maxPrice=99.99&inStock=true`
- Voor SQL: Een where-clausule bouwen zoals `WHERE category = ? AND price <= ? AND stock > 0`
- Voor GraphQL: Een query opstellen met de juiste veldselecties

De implementatie van de `Repository` moet deze vertaling afhandelen, zodat uw UI-code schoon blijft.

## Gegevens sorteren {#sorting-data}

<JavadocLink type="data" location="com/webforj/data/repository/OrderCriteria" code="true">OrderCriteria</JavadocLink> definieert hoe u uw gegevens sorteert. Elke `OrderCriteria` heeft een waardeprovider nodig (hoe u de waarde uit uw entiteit haalt) en een richting:

```java
// Sorteren op enkel veld
OrderCriteria<Employee, String> byName = 
    new OrderCriteria<>(Employee::getName, OrderCriteria.Direction.ASC);

// Multi-level sorteren - afdeling eerst, dan salaris, dan naam
OrderCriteriaList<Employee> sorting = new OrderCriteriaList<>();
sorting.add(new OrderCriteria<>(Employee::getDepartment, OrderCriteria.Direction.ASC));
sorting.add(new OrderCriteria<>(Employee::getSalary, OrderCriteria.Direction.DESC));  
sorting.add(new OrderCriteria<>(Employee::getName, OrderCriteria.Direction.ASC));

// Gebruik in criteria
RepositoryCriteria<Employee, Predicate<Employee>> criteria = 
    new RepositoryCriteria<>(0, 50, sorting, employee -> employee.isActive());
```

De waardeprovider (`Employee::getName`) werkt voor in-memory sorteren. Maar externe gegevensbronnen kunnen geen Java-functies uitvoeren. Voor die gevallen accepteert OrderCriteria een property-naam:

```java
// Voor externe repositories - geef zowel waardegetter als property-naam op
OrderCriteria<Employee, String> byName = new OrderCriteria<>(
    Employee::getName,           // Voor in-memory sorteren
    Direction.ASC,
    null,                       // Aangepaste comparator (optioneel)
    "name"                      // Property-naam voor backend sorteren
);
```

`CollectionRepository` gebruikt de waardeprovider om Java-objecten te sorteren. `DelegatingRepository`-implementaties kunnen de property-naam gebruiken om orderclausules in SQL of `sort=name:asc` in REST-API's te bouwen.

## Paginering beheersen {#controlling-pagination}

Stel offset en limiet in om te bepalen welk gedeelte van de gegevens moet worden geladen:

```java
// Pagina-gebaseerde paginering
int page = 2;          // nul-gebaseerd paginanummer
int pageSize = 20;     // items per pagina
int offset = page * pageSize;

RepositoryCriteria<Product, Predicate<Product>> criteria = 
    new RepositoryCriteria<>(offset, pageSize, null, yourFilter);

// Progressief laden - laad meer gegevens geleidelijk  
int currentlyLoaded = 50;
int loadMore = 25;

repository.setOffset(0);
repository.setLimit(currentlyLoaded + loadMore);
```
