---
title: Querying data
sidebar_position: 3
_i18n_hash: 96551b4f47c7019b8bdd43b57f716c88
---
<!-- vale off -->
# Abfragen von Daten <DocChip chip='since' label='25.02' />
<!-- vale on -->

Das <JavadocLink type="data" location="com/webforj/data/repository/QueryableRepository" code="true">QueryableRepository</JavadocLink>-Interface erweitert `Repository` mit erweiterten Abfragemöglichkeiten über <JavadocLink type="data" location="com/webforj/data/repository/RepositoryCriteria" code="true">RepositoryCriteria</JavadocLink>. Im Gegensatz zu einfachen Repositories, die nur grundlegendes Filtern unterstützen, bieten abfragefähige Repositories strukturierte Abfragen mit benutzerdefinierten Filtertypen, Sortierung und Pagination.

## Verständnis der Filtertypen {#understanding-filter-types}

<JavadocLink type="data" location="com/webforj/data/repository/QueryableRepository" code="true">QueryableRepository</JavadocLink> führt einen zweiten generischen Parameter für den Filtertyp ein: `QueryableRepository<T, F>`, wobei `T` der Entitätstyp und `F` der benutzerdefinierte Filtertyp ist.

Diese Trennung existiert, weil unterschiedliche Datenquellen unterschiedliche Abfragesprachen verwenden:

```java
// Prädikat-Filter für In-Memory-Sammlungen
QueryableRepository<Product, Predicate<Product>> inMemoryRepo = 
    new CollectionRepository<>(products);

// Benutzerdefinierte Filterobjekte für REST-APIs oder Datenbanken  
QueryableRepository<User, UserFilter> apiRepo = 
    new DelegatingRepository<>(/* implementation */);

// String-Abfragen für Suchmaschinen
QueryableRepository<Document, String> searchRepo = 
    new DelegatingRepository<>(/* implementation */);
```

`CollectionRepository` verwendet `Predicate<Product>`, da es Java-Objekte im Speicher filtert. Das REST-API-Repository verwendet `UserFilter` – eine benutzerdefinierte Klasse mit Feldern wie `department` und `status`, die auf Abfrageparameter abgebildet werden. Das Suchrepository verwendet einfache Strings für Volltextabfragen.

UI-Komponenten sind sich dieser Unterschiede nicht bewusst. Sie rufen `setBaseFilter()` mit dem Filtertyp auf, den das Repository erwartet, und das Repository kümmert sich um die Übersetzung.

## Erstellen von Abfragen mit Repository-Kriterien {#building-queries-with-repository-criteria}

<JavadocLink type="data" location="com/webforj/data/repository/RepositoryCriteria" code="true">RepositoryCriteria</JavadocLink> bündelt alle Abfrageparameter in einem unveränderlichen Objekt. Anstatt separate Methoden für Filter, Sortierung und Pagination aufzurufen, übergeben Sie alles auf einmal:

```java
// Vollständige Abfrage mit allen Parametern
RepositoryCriteria<Product, Predicate<Product>> criteria = 
    new RepositoryCriteria<>(
        20,                                       // offset - überspringe die ersten 20
        10,                                       // limit - nimm 10 Elemente  
        orderCriteria,                           // Sortierregeln
        product -> product.getPrice() < 100.0    // Filterbedingung
    );

// Abfrage ausführen
Stream<Product> results = repository.findBy(criteria);
int totalMatching = repository.size(criteria);
```

Die Methode `findBy()` führt die vollständige Abfrage aus - sie wendet den Filter an, sortiert die Ergebnisse, überspringt das Offset und nimmt das Limit. Die Methode `size()` zählt alle Elemente, die dem Filter entsprechen, und ignoriert die Pagination.

Sie können auch Kriterien mit nur den benötigten Teilen erstellen:

```java
// Nur Filter
RepositoryCriteria<Product, Predicate<Product>> filterOnly = 
    new RepositoryCriteria<>(product -> product.isActive());

// Nur Pagination  
RepositoryCriteria<Product, Predicate<Product>> pageOnly = 
    new RepositoryCriteria<>(0, 25);
```

## Arbeiten mit verschiedenen Filtertypen {#working-with-different-filter-types}

### Prädikat-Filter {#predicate-filters}

Für In-Memory-Sammlungen verwenden Sie `Predicate<T>`, um funktionale Filter zu erstellen:

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
- Für REST-APIs: In Abfrageparameter umwandeln wie `?category=Electronics&maxPrice=99.99&inStock=true`
- Für SQL: Eine WHERE-Klausel wie `WHERE category = ? AND price <= ? AND stock > 0` erstellen
- Für GraphQL: Eine Abfrage mit den entsprechenden Auswahlfeldern konstruieren

Die `Repository`-Implementierung sollte diese Übersetzung handhaben, sodass Ihr UI-Code sauber bleibt.

## Sortieren von Daten {#sorting-data}

<JavadocLink type="data" location="com/webforj/data/repository/OrderCriteria" code="true">OrderCriteria</JavadocLink> definiert, wie Ihre Daten sortiert werden. Jede `OrderCriteria` benötigt einen Wertanbieter (wie man den Wert aus Ihrer Entität erhält) und eine Richtung:

```java
// Sortierung nach einem einzelnen Feld
OrderCriteria<Employee, String> byName = 
    new OrderCriteria<>(Employee::getName, OrderCriteria.Direction.ASC);

// Mehrdimensionale Sortierung - Abteilung zuerst, dann Gehalt, dann Name
OrderCriteriaList<Employee> sorting = new OrderCriteriaList<>();
sorting.add(new OrderCriteria<>(Employee::getDepartment, OrderCriteria.Direction.ASC));
sorting.add(new OrderCriteria<>(Employee::getSalary, OrderCriteria.Direction.DESC));  
sorting.add(new OrderCriteria<>(Employee::getName, OrderCriteria.Direction.ASC));

// In Kriterien verwenden
RepositoryCriteria<Employee, Predicate<Employee>> criteria = 
    new RepositoryCriteria<>(0, 50, sorting, employee -> employee.isActive());
```

Der Wertanbieter (`Employee::getName`) funktioniert für die In-Memory-Sortierung. Aber externe Datenquellen können Java-Funktionen nicht ausführen. In diesen Fällen akzeptiert OrderCriteria einen Eigenschaftsnamen:

```java
// Für externe Repositories - sowohl Wertanbieter als auch Eigenschaftsnamen angeben
OrderCriteria<Employee, String> byName = new OrderCriteria<>(
    Employee::getName,           // Für die In-Memory-Sortierung
    Direction.ASC,
    null,                       // Benutzerdefinierter Vergleich (optional)
    "name"                      // Eigenschaftsname für Backend-Sortierung
);
```

`CollectionRepository` verwendet den Wertanbieter, um Java-Objekte zu sortieren. Implementierungen von `DelegatingRepository` können den Eigenschaftsnamen verwenden, um Sortierklauseln in SQL oder `sort=name:asc` in REST-APIs zu erstellen.

## Steuerung der Pagination {#controlling-pagination}

Setzen Sie Offset und Limit, um festzulegen, welcher Teil der Daten geladen werden soll:

```java
// Seitenbasierte Pagination
int page = 2;          // nullbasierte Seitenzahl
int pageSize = 20;     // Elemente pro Seite
int offset = page * pageSize;

RepositoryCriteria<Product, Predicate<Product>> criteria = 
    new RepositoryCriteria<>(offset, pageSize, null, yourFilter);

// Fortschreitendes Laden - Daten schrittweise laden  
int currentlyLoaded = 50;
int loadMore = 25;

repository.setOffset(0);
repository.setLimit(currentlyLoaded + loadMore);
```
