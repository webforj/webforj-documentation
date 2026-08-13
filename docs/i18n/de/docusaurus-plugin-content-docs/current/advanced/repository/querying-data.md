---
title: Querying data
sidebar_position: 3
description: >-
  Build typed filters, sorting, and pagination with QueryableRepository and
  RepositoryCriteria for in-memory collections or external sources.
_i18n_hash: 4bb2af4f510fd31035042c2f1e3a24c7
---
<!-- vale off -->
# Abfragen von Daten <DocChip chip='since' label='25.02' />
<!-- vale on -->

Das <JavadocLink type="data" location="com/webforj/data/repository/QueryableRepository" code="true">QueryableRepository</JavadocLink>-Interface erweitert `Repository` mit erweiterten Abfragemöglichkeiten durch <JavadocLink type="data" location="com/webforj/data/repository/RepositoryCriteria" code="true">RepositoryCriteria</JavadocLink>. Im Gegensatz zu grundlegenden Repositories, die nur einfache Filterung unterstützen, bieten abfragbare Repositories eine strukturierte Abfrage mit benutzerdefinierten Filtertypen, Sortierung und Seitenaufteilung.

## Verständnis der Filtertypen {#understanding-filter-types}

<JavadocLink type="data" location="com/webforj/data/repository/QueryableRepository" code="true">QueryableRepository</JavadocLink> führt einen zweiten generischen Parameter für den Filtertyp ein: `QueryableRepository<T, F>` wobei `T` Ihr Entitätstyp und `F` Ihr benutzerdefinierter Filtertyp ist.

Diese Trennung existiert, weil verschiedene Datenquellen verschiedene Abfragesprachen sprechen:

```java
// Prädikatsfilter für In-Memory-Sammlungen
QueryableRepository<Product, Predicate<Product>> inMemoryRepo =
  new CollectionRepository<>(products);

// Benutzerdefinierte Filterobjekte für REST-APIs oder Datenbanken
QueryableRepository<User, UserFilter> apiRepo =
  new DelegatingRepository<>(/* implementation */);

// String-Abfragen für Suchmaschinen
QueryableRepository<Document, String> searchRepo =
  new DelegatingRepository<>(/* implementation */);
```

`CollectionRepository` verwendet `Predicate<Product>`, weil es Java-Objekte im Speicher filtert. Das REST-API-Repository verwendet `UserFilter` - eine benutzerdefinierte Klasse mit Feldern wie `department` und `status`, die den Abfrageparametern zugeordnet sind. Das Suchrepository verwendet reine Strings für Volltextabfragen.

UI-Komponenten kümmern sich nicht um diese Unterschiede. Sie rufen `setBaseFilter()` mit dem Filtertyp auf, den das Repository erwartet, und das Repository übernimmt die Übersetzung.

## Abfragen mit Repository-Kriterien erstellen {#building-queries-with-repository-criteria}

<JavadocLink type="data" location="com/webforj/data/repository/RepositoryCriteria" code="true">RepositoryCriteria</JavadocLink> bündelt alle Abfrageparameter in einem immutablen Objekt. Anstatt separate Methoden für Filter, Sortierung und Seitenaufteilung aufzurufen, geben Sie alles auf einmal weiter:

```java
// Vollständige Abfrage mit allen Parametern
RepositoryCriteria<Product, Predicate<Product>> criteria =
  new RepositoryCriteria<>(
    20,                                       // offset - die ersten 20 überspringen
    10,                                       // limit - 10 Elemente nehmen
    orderCriteria,                           // Sortierregeln
    product -> product.getPrice() < 100.0    // Filterbedingung
  );

// Führen Sie die Abfrage aus
Stream<Product> results = repository.findBy(criteria);
int totalMatching = repository.size(criteria);
```

Die `findBy()`-Methode führt die vollständige Abfrage aus - sie wendet den Filter an, sortiert die Ergebnisse, überspringt den Offset und nimmt das Limit. Die `size()`-Methode zählt alle Elemente, die dem Filter entsprechen, und ignoriert die Seitenaufteilung.

Sie können auch Kriterien mit nur den Teilen erstellen, die Sie benötigen:

```java
// Nur Filter
RepositoryCriteria<Product, Predicate<Product>> filterOnly =
  new RepositoryCriteria<>(product -> product.isActive());

// Nur Seitenaufteilung
RepositoryCriteria<Product, Predicate<Product>> pageOnly =
  new RepositoryCriteria<>(0, 25);
```

## Arbeiten mit verschiedenen Filtertypen {#working-with-different-filter-types}

### Prädikatsfilter {#predicate-filters}

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

Externe Datenquellen können Java-Prädikate nicht ausführen. Stattdessen erstellen Sie Filterklassen, die repräsentieren, wonach Ihr Backend suchen kann:

```java
public class ProductFilter {
  private String category;
  private BigDecimal maxPrice;
  private Boolean inStock;

  // Getter und Setter...
}

// Verwendung mit benutzerdefiniertem Repository
ProductFilter filter = new ProductFilter();
filter.setCategory("Electronics");
filter.setMaxPrice(new BigDecimal("99.99"));
filter.setInStock(true);

RepositoryCriteria<Product, ProductFilter> criteria =
  new RepositoryCriteria<>(filter);

Stream<Product> results = customRepository.findBy(criteria);
```

Innerhalb der `findBy()`-Methode Ihres benutzerdefinierten Repositories würden Sie dieses Filterobjekt übersetzen:
- Für REST-APIs: Umwandlung in Abfrageparameter wie `?category=Electronics&maxPrice=99.99&inStock=true`
- Für SQL: Aufbau einer Where-Klausel wie `WHERE category = ? AND price <= ? AND stock > 0`
- Für GraphQL: Konstruktion einer Abfrage mit den entsprechenden Feldauswahlen

Die `Repository`-Implementierung sollte diese Übersetzung übernehmen, um Ihren UI-Code sauber zu halten.

## Daten sortieren {#sorting-data}

<JavadocLink type="data" location="com/webforj/data/repository/OrderCriteria" code="true">OrderCriteria</JavadocLink> definiert, wie Ihre Daten sortiert werden. Jede `OrderCriteria` benötigt einen Wertanbieter (wie man den Wert von Ihrer Entität erhält) und eine Richtung:

```java
// Sortierung nach einem einzelnen Feld
OrderCriteria<Employee, String> byName =
  new OrderCriteria<>(Employee::getName, OrderCriteria.Direction.ASC);

// Mehrstufige Sortierung - zuerst Abteilung, dann Gehalt, dann Name
OrderCriteriaList<Employee> sorting = new OrderCriteriaList<>();
sorting.add(new OrderCriteria<>(Employee::getDepartment, OrderCriteria.Direction.ASC));
sorting.add(new OrderCriteria<>(Employee::getSalary, OrderCriteria.Direction.DESC));
sorting.add(new OrderCriteria<>(Employee::getName, OrderCriteria.Direction.ASC));

// Verwendung in Kriterien
RepositoryCriteria<Employee, Predicate<Employee>> criteria =
  new RepositoryCriteria<>(0, 50, sorting, employee -> employee.isActive());
```

Der Wertanbieter (`Employee::getName`) funktioniert für die In-Memory-Sortierung. Externe Datenquellen können jedoch keine Java-Funktionen ausführen. Für diese Fälle akzeptiert OrderCriteria einen Eigenschaftsnamen:

```java
// Für externe Repositories - sowohl Wertgetter als auch Eigenschaftsnamen bereitstellen
OrderCriteria<Employee, String> byName = new OrderCriteria<>(
  Employee::getName,           // Für die In-Memory-Sortierung
  Direction.ASC,
  null,                       // Benutzerdefinierter Comparator (optional)
  "name"                      // Eigenschaftsname für das Backend-Sortieren
);
```

`CollectionRepository` verwendet den Wertanbieter, um Java-Objekte zu sortieren. Implementierungen von `DelegatingRepository` können den Eigenschaftsnamen verwenden, um Sortierklauseln in SQL oder `sort=name:asc` in REST-APIs zu erstellen.

## Kontrolle der Seitenaufteilung {#controlling-pagination}

Setzen Sie Offset und Limit, um auszuwählen, welcher Teil der Daten geladen werden soll:

```java
// Seitenbasierte Seitenaufteilung
int page = 2;          // nullbasierte Seitenzahl
int pageSize = 20;     // Elemente pro Seite
int offset = page * pageSize;

RepositoryCriteria<Product, Predicate<Product>> criteria =
  new RepositoryCriteria<>(offset, pageSize, null, yourFilter);

// Fortschreitendes Laden - mehr Daten inkrementell laden
int currentlyLoaded = 50;
int loadMore = 25;

repository.setOffset(0);
repository.setLimit(currentlyLoaded + loadMore);
```
