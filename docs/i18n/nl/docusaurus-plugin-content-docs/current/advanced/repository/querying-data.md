---
title: Querying data
sidebar_position: 3
_i18n_hash: 745f13099f9adff4b07124e4c8230600
---
<!-- vale off -->
# Gegevens opvragen <DocChip chip='since' label='25.02' />
<!-- vale on -->

De <JavadocLink type="data" location="com/webforj/data/repository/QueryableRepository" code="true">QueryableRepository</JavadocLink> interface breidt `Repository` uit met geavanceerd opvragen via <JavadocLink type="data" location="com/webforj/data/repository/RepositoryCriteria" code="true">RepositoryCriteria</JavadocLink>. In tegenstelling tot basisrepositories die alleen eenvoudige filtering ondersteunen, bieden opvraagbare repositories gestructureerd opvragen met aangepaste filtertypes, sorteren en paginering.

## Filtertypes begrijpen {#understanding-filter-types}

<JavadocLink type="data" location="com/webforj/data/repository/QueryableRepository" code="true">QueryableRepository</JavadocLink> introduceert een tweede generieke parameter voor het filtertype: `QueryableRepository<T, F>` waar `T` je entiteitstype is en `F` je aangepaste filtertype.

Deze scheiding bestaat omdat verschillende gegevensbronnen verschillende querytalen spreken:

```java
// Predicate-filters voor in-memory collecties
QueryableRepository<Product, Predicate<Product>> inMemoryRepo = 
  new CollectionRepository<>(products);

// Aangepaste filterobjecten voor REST-API's of databases  
QueryableRepository<User, UserFilter> apiRepo = 
  new DelegatingRepository<>(/* implementatie */);

// String-query's voor zoekmachines
QueryableRepository<Document, String> searchRepo = 
  new DelegatingRepository<>(/* implementatie */);
```

`CollectionRepository` gebruikt `Predicate<Product>` omdat het Java-objecten in het geheugen filtert. De REST-API repository gebruikt `UserFilter` - een aangepaste klasse met velden zoals `department` en `status` die overeenkomen met queryparameters. De zoekrepository gebruikt reguliere strings voor full-text queries.

UI-componenten geven niet om deze verschillen. Ze roepen `setBaseFilter()` aan met welk filtertype de repository verwacht, en de repository behandelt de vertaling.

## Query's bouwen met repositorycriteria {#building-queries-with-repository-criteria}

<JavadocLink type="data" location="com/webforj/data/repository/RepositoryCriteria" code="true">RepositoryCriteria</JavadocLink> bundelt alle queryparameters in één onveranderlijk object. In plaats van aparte methoden aan te roepen voor filter, sorteren en paginering, geef je alles in één keer door:

```java
// Volledige query met alle parameters
RepositoryCriteria<Product, Predicate<Product>> criteria = 
  new RepositoryCriteria<>(
    20,                                       // offset - overslaan van de eerste 20
    10,                                       // limiet - neem 10 items  
    orderCriteria,                           // sorteervelden
    product -> product.getPrice() < 100.0    // filtervoorwaarde
  );

// Voer de query uit
Stream<Product> results = repository.findBy(criteria);
int totalMatching = repository.size(criteria);
```

De `findBy()`-methode voert de volledige query uit - hij past de filter toe, sorteert de resultaten, slaat de offset over en neemt de limiet. De `size()`-methode telt alle items die overeenkomen met de filter, zonder paginering mee te tellen.

Je kunt ook criteria maken met alleen de delen die je nodig hebt:

```java
// Alleen filter
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

// Bouw complexe predicates
Predicate<Product> activeProducts = product -> product.isActive();
Predicate<Product> inStock = product -> product.getStock() > 0;
Predicate<Product> affordable = product -> product.getPrice() < 50.0;

// Combineer voorwaarden
repository.setBaseFilter(activeProducts.and(inStock).and(affordable));

// Dynamische filtering
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

Externe gegevensbronnen kunnen geen Java-predicates uitvoeren. In plaats daarvan maak je filterklassen die vertegenwoordigen wat je backend kan opzoeken:

```java
public class ProductFilter {
  private String category;
  private BigDecimal maxPrice;
  private Boolean inStock;
  
  // getters en setters...
}

// Gebruik met aangepaste repository
ProductFilter filter = new ProductFilter();
filter.setCategory("Electronics");
filter.setMaxPrice(new BigDecimal("99.99"));
filter.setInStock(true);

RepositoryCriteria<Product, ProductFilter> criteria = 
  new RepositoryCriteria<>(filter);

Stream<Product> results = customRepository.findBy(criteria);
```

Binnen de `findBy()`-methode van je aangepaste repository zou je dit filterobject vertalen:
- Voor REST-API's: Converteer naar queryparameters zoals `?category=Electronics&maxPrice=99.99&inStock=true`
- Voor SQL: Bouw een where-clausule zoals `WHERE category = ? AND price <= ? AND stock > 0`
- Voor GraphQL: Constructie van een query met de juiste veldselecties

De implementatie van de `Repository` zou deze vertaling moeten afhandelen, zodat je UI-code schoon blijft.

## Gegevens sorteren {#sorting-data}

<JavadocLink type="data" location="com/webforj/data/repository/OrderCriteria" code="true">OrderCriteria</JavadocLink> definieert hoe je je gegevens sorteert. Elke `OrderCriteria` heeft een waardeprovider nodig (hoe je de waarde uit je entiteit haalt) en een richting:

```java
// Sorteren op één veld
OrderCriteria<Employee, String> byName = 
  new OrderCriteria<>(Employee::getName, OrderCriteria.Direction.ASC);

// Meerdere niveaus sorteren - afdeling eerst, dan salaris, dan naam
OrderCriteriaList<Employee> sorting = new OrderCriteriaList<>();
sorting.add(new OrderCriteria<>(Employee::getDepartment, OrderCriteria.Direction.ASC));
sorting.add(new OrderCriteria<>(Employee::getSalary, OrderCriteria.Direction.DESC));  
sorting.add(new OrderCriteria<>(Employee::getName, OrderCriteria.Direction.ASC));

// Gebruik in criteria
RepositoryCriteria<Employee, Predicate<Employee>> criteria = 
  new RepositoryCriteria<>(0, 50, sorting, employee -> employee.isActive());
```

De waardeprovider (`Employee::getName`) werkt voor in-memory sorteren. Maar externe gegevensbronnen kunnen geen Java-functies uitvoeren. Voor die gevallen accepteert OrderCriteria een eigenschapsnaam:

```java
// Voor externe repositories - geef zowel waardegetters als eigenschapsnaam
OrderCriteria<Employee, String> byName = new OrderCriteria<>(
  Employee::getName,           // Voor in-memory sorteren
  Direction.ASC,
  null,                       // Aangepaste comparator (optioneel)
  "name"                      // Eigenschapsnaam voor backend sorteren
);
```

`CollectionRepository` gebruikt de waardeprovider om Java-objecten te sorteren. `DelegatingRepository` implementaties kunnen de eigenschapsnaam gebruiken om sorteerclausules in SQL of `sort=name:asc` in REST-API's op te bouwen.

## Paginering beheersen {#controlling-pagination}

Stel offset en limiet in om te beheersen welk deel van de gegevens te laden:

```java
// Paginabased paginering
int page = 2;          // paginanummer op basis van nul
int pageSize = 20;     // items per pagina
int offset = page * pageSize;

RepositoryCriteria<Product, Predicate<Product>> criteria = 
  new RepositoryCriteria<>(offset, pageSize, null, yourFilter);

// Progressieve loading - laad meer gegevens geleidelijk  
int currentlyLoaded = 50;
int loadMore = 25;

repository.setOffset(0);
repository.setLimit(currentlyLoaded + loadMore);
```
