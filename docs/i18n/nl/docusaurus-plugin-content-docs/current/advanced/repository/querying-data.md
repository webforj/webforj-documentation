---
title: Querying data
sidebar_position: 3
description: >-
  Build typed filters, sorting, and pagination with QueryableRepository and
  RepositoryCriteria for in-memory collections or external sources.
_i18n_hash: 4bb2af4f510fd31035042c2f1e3a24c7
---
<!-- vale off -->
# Gegevens opvragen <DocChip chip='since' label='25.02' />
<!-- vale on -->

De <JavadocLink type="data" location="com/webforj/data/repository/QueryableRepository" code="true">QueryableRepository</JavadocLink> interface breidt `Repository` uit met geavanceerde queryfunctionaliteit via <JavadocLink type="data" location="com/webforj/data/repository/RepositoryCriteria" code="true">RepositoryCriteria</JavadocLink>. In tegenstelling tot basisrepositories die alleen eenvoudige filtering ondersteunen, bieden doorzoekbare repositories gestructureerde querying met aangepaste filtertypes, sortering en paginering.

## Begrijpen van filtertypes {#understanding-filter-types}

<JavadocLink type="data" location="com/webforj/data/repository/QueryableRepository" code="true">QueryableRepository</JavadocLink> introduceert een tweede generieke parameter voor het filtertype: `QueryableRepository<T, F>` waarbij `T` je entiteitstype is en `F` je aangepaste filtertype.

Deze scheiding bestaat omdat verschillende gegevensbronnen verschillende querytalen spreken:

```java
// Predicate filters voor in-memory collecties
QueryableRepository<Product, Predicate<Product>> inMemoryRepo =
  new CollectionRepository<>(products);

// Aangepaste filterobjecten voor REST API's of databases
QueryableRepository<User, UserFilter> apiRepo =
  new DelegatingRepository<>(/* implementatie */);

// String queries voor zoekmachines
QueryableRepository<Document, String> searchRepo =
  new DelegatingRepository<>(/* implementatie */);
```

`CollectionRepository` gebruikt `Predicate<Product>` omdat het Java-objecten in het geheugen filtert. De REST API-repository gebruikt `UserFilter` - een aangepaste klasse met velden zoals `department` en `status` die overeenkomen met queryparameters. De zoekrepository gebruikt gewone strings voor full-text queries.

UI-componenten maken zich geen zorgen over deze verschillen. Ze roepen `setBaseFilter()` aan met welk filtertype de repository ook verwacht, en de repository handelt de vertaling af.

## Bouw queries met repositorycriteria {#building-queries-with-repository-criteria}

<JavadocLink type="data" location="com/webforj/data/repository/RepositoryCriteria" code="true">RepositoryCriteria</JavadocLink> bundelt alle queryparameters in één onveranderlijk object. In plaats van afzonderlijke methoden voor filter, sorteren en paginering aan te roepen, geef je alles in één keer door:

```java
// Volledige query met alle parameters
RepositoryCriteria<Product, Predicate<Product>> criteria =
  new RepositoryCriteria<>(
    20,                                       // offset - sla de eerste 20 over
    10,                                       // limiet - neem 10 items
    orderCriteria,                           // sorteervoorschriften
    product -> product.getPrice() < 100.0    // filterconditie
  );

// Voer de query uit
Stream<Product> results = repository.findBy(criteria);
int totalMatching = repository.size(criteria);
```

De `findBy()`-methode voert de volledige query uit - het past de filter toe, sorteert de resultaten, slaat de offset over en neemt de limiet. De `size()`-methode telt alle items die overeenkomen met de filter, waarbij paginering wordt genegeerd.

Je kunt ook criteria maken met alleen de onderdelen die je nodig hebt:

```java
// Alleen filter
RepositoryCriteria<Product, Predicate<Product>> filterOnly =
  new RepositoryCriteria<>(product -> product.isActive());

// Alleen paginering
RepositoryCriteria<Product, Predicate<Product>> pageOnly =
  new RepositoryCriteria<>(0, 25);
```

## Werken met verschillende filtertypes {#working-with-different-filter-types}

### Predicate filters {#predicate-filters}

Voor in-memory collecties, gebruik `Predicate<T>` om functionele filters samen te stellen:

```java
CollectionRepository<Product> repository = new CollectionRepository<>(products);

// Bouw complexe predicaten
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

Externe gegevensbronnen kunnen geen Java-predicaten uitvoeren. In plaats daarvan maak je filterklassen die vertegenwoordigen wat je backend kan doorzoeken:

```java
public class ProductFilter {
  private String category;
  private BigDecimal maxPrice;
  private Boolean inStock;

  // getters en setters...
}

// Gebruik met een aangepaste repository
ProductFilter filter = new ProductFilter();
filter.setCategory("Elektronica");
filter.setMaxPrice(new BigDecimal("99.99"));
filter.setInStock(true);

RepositoryCriteria<Product, ProductFilter> criteria =
  new RepositoryCriteria<>(filter);

Stream<Product> results = customRepository.findBy(criteria);
```

Binnen de `findBy()`-methode van je aangepaste repository zou je dit filterobject vertalen:
- Voor REST API's: Omzetten naar queryparameters zoals `?category=Elektronica&maxPrice=99.99&inStock=true`
- Voor SQL: Bouw een where-clausule zoals `WHERE category = ? AND price <= ? AND stock > 0`
- Voor GraphQL: Stel een query samen met de juiste veldselecties

De `Repository`-implementatie zou deze vertaling moeten afhandelen, zodat je UI-code schoon blijft.

## Gegevens sorteren {#sorting-data}

<JavadocLink type="data" location="com/webforj/data/repository/OrderCriteria" code="true">OrderCriteria</JavadocLink> definieert hoe je gegevens sorteert. Elke `OrderCriteria` heeft een waardeprovider (hoe de waarde uit je entiteit te krijgen) en een richting:

```java
// Sorteren op één veld
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

De waardeprovider (`Employee::getName`) werkt voor in-memory sortering. Maar externe gegevensbronnen kunnen geen Java-functies uitvoeren. Voor die gevallen accepteert OrderCriteria een propertynaam:

```java
// Voor externe repositories - geef zowel waardegetters als propertynamen op
OrderCriteria<Employee, String> byName = new OrderCriteria<>(
  Employee::getName,           // Voor in-memory sortering
  Direction.ASC,
  null,                       // Aangepaste comparator (optioneel)
  "name"                      // Propertynaam voor backend sortering
);
```

`CollectionRepository` gebruikt de waardeprovider om Java-objecten te sorteren. `DelegatingRepository`-implementaties kunnen de propertynaam gebruiken om orderclausules in SQL of `sort=name:asc` in REST API's te bouwen.

## Paginering beheersen {#controlling-pagination}

Stel offset en limiet in om te bepalen welke gegevensslice je wilt laden:

```java
// Pagina-gebaseerde paginering
int page = 2;          // nul-gebaseerd paginanummer
int pageSize = 20;     // items per pagina
int offset = page * pageSize;

RepositoryCriteria<Product, Predicate<Product>> criteria =
  new RepositoryCriteria<>(offset, pageSize, null, yourFilter);

// Progressieve laading - laad meer gegevens geleidelijk
int currentlyLoaded = 50;
int loadMore = 25;

repository.setOffset(0);
repository.setLimit(currentlyLoaded + loadMore);
```
