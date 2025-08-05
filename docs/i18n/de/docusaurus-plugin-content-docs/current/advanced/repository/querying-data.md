---
title: Querying data
sidebar_position: 3
_i18n_hash: c5508e014de2ca1de7543b34e39731bc
---
<!-- vale off -->
# Abfragen von Daten <DocChip chip='since' label='25.02' />
<!-- vale on -->

Das <JavadocLink type="data" location="com/webforj/data/repository/QueryableRepository" code="true">QueryableRepository</JavadocLink> Interface erweitert `Repository` um erweiterte Abfragefunktionen über <JavadocLink type="data" location="com/webforj/data/repository/RepositoryCriteria" code="true">RepositoryCriteria</JavadocLink>. Im Gegensatz zu grundlegenden Repositories, die nur einfache Filter unterstützen, bieten abfragbare Repositories strukturierte Abfragen mit benutzerdefinierten Filtertypen, Sortierung und Pagination.

## Verständnis der Filtertypen {#understanding-filter-types}

<JavadocLink type="data" location="com/webforj/data/repository/QueryableRepository" code="true">QueryableRepository</JavadocLink> führt einen zweiten generischen Parameter für den Filtertyp ein: `QueryableRepository<T, F>` wobei `T` Ihr Entitätstyp und `F` Ihr benutzerdefinierter Filtertyp ist.

Diese Trennung existiert, weil unterschiedliche Datenquellen unterschiedliche Abfragesprachen verwenden:

```java
// Prädikatfilter für In-Memory-Kollektionen
QueryableRepository<Product, Predicate<Product>> inMemoryRepo = 
    new CollectionRepository<>(products);

// Benutzerdefinierte Filterobjekte für REST-APIs oder Datenbanken  
QueryableRepository<User, UserFilter> apiRepo = 
    new DelegatingRepository<>(/* Implementierung */);

// String-Abfragen für Suchmaschinen
QueryableRepository<Document, String> searchRepo = 
    new DelegatingRepository<>(/* Implementierung */);
```

`CollectionRepository` verwendet `Predicate<Product>`, weil es Java-Objekte im Speicher filtert. Das REST-API-Repository verwendet `UserFilter` - eine benutzerdefinierte Klasse mit Feldern wie `department` und `status`, die zu Abfrageparametern zugeordnet werden. Das Suchrepository verwendet einfache Strings für Volltextabfragen.

UI-Komponenten kümmern sich nicht um diese Unterschiede. Sie rufen `setBaseFilter()` mit dem Filtertyp auf, den das Repository erwartet, und das Repository übernimmt die Übersetzung.

## Erstellen von Abfragen mit Repository-Kriterien {#building-queries-with-repository-criteria}

<JavadocLink type="data" location="com/webforj/data/repository/RepositoryCriteria" code="true">RepositoryCriteria</JavadocLink> bündelt alle Abfrageparameter in einem unveränderlichen Objekt. Anstatt separate Methoden für Filter, Sortierung und Pagination aufzurufen, übergeben Sie alles auf einmal:

```java
// Vollständige Abfrage mit allen Parametern
RepositoryCriteria<Product, Predicate<Product>> criteria = 
    new RepositoryCriteria<>(
        20,                                       // Offset - erste 20 überspringen
        10,                                       // Limit - 10 Elemente nehmen  
        orderCriteria,                           // Sortierregeln
        product -> product.getPrice() < 100.0    // Filterbedingung
    );

// Abfrage ausführen
Stream<Product> results = repository.findBy(criteria);
int totalMatching = repository.size(criteria);
```

Die Methode `findBy()` führt die vollständige Abfrage aus - sie wendet den Filter an, sortiert die Ergebnisse, überspringt das Offset und übernimmt das Limit. Die Methode `size()` zählt alle Elemente, die dem Filter entsprechen, unabhängig von der Pagination.

Sie können auch Kriterien mit nur den Teilen erstellen, die Sie benötigen:

```java
// Nur Filter
RepositoryCriteria<Product, Predicate<Product>> filterOnly = 
    new RepositoryCriteria<>(product -> product.isActive());

// Nur Pagination  
RepositoryCriteria<Product, Predicate<Product>> pageOnly = 
    new RepositoryCriteria<>(0, 25);
```

## Arbeiten mit verschiedenen Filtertypen {#working-with-different-filter-types}

### Prädikatfilter {#predicate-filters}

Für In-Memory-Kollektionen verwenden Sie `Predicate<T>`, um funktionale Filter zu erstellen:

```java
CollectionRepository<Product> repository = new CollectionRepository<>(products);

// Komplexe Prädikate erstellen
Predicate<Product> activeProducts = product -> product.isActive();
Predicate<Product> inStock = product -> product.getStock() > 0;
Predicate<Product> affordable = product -> product.getPrice() < 50.0;

// Bedingungen kombinieren
repository.setBaseFilter(activeProducts.and(inStock).and(affordable));

// Dynamisches Filtern
Predicate<Product> filter = product -> true;
if (categoryFilter != null) {
    filter = filter.and(p -> p.getCategory().equals(categoryFilter));
}
if (maxPrice != null) {
    filter = filter.and(p -> p.getPrice() <= maxPrice);
}
repository.setBaseFilter(filter);
```

### Benutzerdefinierte Filterobjekte {#custom-filter-objects}

Externe Datenquellen können Java-Prädikate nicht ausführen. Stattdessen erstellen Sie Filterklassen, die darstellen, wonach Ihr Backend suchen kann:

```java
public class ProductFilter {
    private String category;
    private BigDecimal maxPrice;
    private Boolean inStock;
    
    // Getter und Setter...
}

// Mit benutzerdefiniertem Repository verwenden
ProductFilter filter = new ProductFilter();
filter.setCategory("Elektronik");
filter.setMaxPrice(new BigDecimal("99.99"));
filter.setInStock(true);

RepositoryCriteria<Product, ProductFilter> criteria = 
    new RepositoryCriteria<>(filter);

Stream<Product> results = customRepository.findBy(criteria);
```

Innerhalb der `findBy()`-Methode Ihres benutzerdefinierten Repositories würden Sie dieses Filterobjekt übersetzen:
- Für REST-APIs: In Abfrageparameter umwandeln wie `?category=Elektronik&maxPrice=99.99&inStock=true`
- Für SQL: Eine Where-Klausel erstellen wie `WHERE category = ? AND price <= ? AND stock > 0`
- Für GraphQL: Eine Abfrage mit den entsprechenden Feldauswahlen konstruieren

Die Implementierung des `Repository` sollte diese Übersetzung durchführen, um Ihren UI-Code sauber zu halten.

## Daten sortieren {#sorting-data}

<JavadocLink type="data" location="com/webforj/data/repository/OrderCriteria" code="true">OrderCriteria</JavadocLink> definiert, wie Ihre Daten sortiert werden. Jedes `OrderCriteria` benötigt einen Wertgeber (wie man den Wert von Ihrer Entität erhält) und eine Richtung:

```java
// Sortierung nach einem einzelnen Feld
OrderCriteria<Employee, String> byName = 
    new OrderCriteria<>(Employee::getName, OrderCriteria.Direction.ASC);

// Mehrstufige Sortierung - Abteilung zuerst, dann Gehalt, dann Name
OrderCriteriaList<Employee> sorting = new OrderCriteriaList<>();
sorting.add(new OrderCriteria<>(Employee::getDepartment, OrderCriteria.Direction.ASC));
sorting.add(new OrderCriteria<>(Employee::getSalary, OrderCriteria.Direction.DESC));  
sorting.add(new OrderCriteria<>(Employee::getName, OrderCriteria.Direction.ASC));

// In Kriterien verwenden
RepositoryCriteria<Employee, Predicate<Employee>> criteria = 
    new RepositoryCriteria<>(0, 50, sorting, employee -> employee.isActive());
```

Der Wertgeber (`Employee::getName`) funktioniert für In-Memory-Sortierung. Aber externe Datenquellen können Java-Funktionen nicht ausführen. Für diese Fälle akzeptiert OrderCriteria einen Eigenschaftsnamen:

```java
// Für externe Repositories - sowohl Wertgeber als auch Eigenschaftsnamen angeben
OrderCriteria<Employee, String> byName = new OrderCriteria<>( 
    Employee::getName,           // Für In-Memory-Sortierung
    Direction.ASC,
    null,                       // Benutzerdefinierter Comparator (optional)
    "name"                      // Eigenschaftsname für Backend-Sortierung
);
```

`CollectionRepository` verwendet den Wertgeber, um Java-Objekte zu sortieren. Implementierungen von `DelegatingRepository` können den Eigenschaftsnamen verwenden, um Sortierklauseln in SQL oder `sort=name:asc` in REST-APIs zu erstellen.

## Pagination steuern {#controlling-pagination}

Setzen Sie Offset und Limit, um zu steuern, welchen Teil der Daten Sie laden möchten:

```java
// Seitenbasierte Pagination
int page = 2;          // nullbasierte Seitennummer
int pageSize = 20;     // Elemente pro Seite
int offset = page * pageSize;

RepositoryCriteria<Product, Predicate<Product>> criteria = 
    new RepositoryCriteria<>(offset, pageSize, null, yourFilter);

// Progressive Laden - laden Sie mehr Daten schrittweise  
int currentlyLoaded = 50;
int loadMore = 25;

repository.setOffset(0);
repository.setLimit(currentlyLoaded + loadMore);
```
