---
title: Spring Data JPA
sidebar_position: 20
_i18n_hash: 5aee4b031f1464780e7fd06e71946951
---
Spring Data JPA ist der De-facto-Standard für den Datenzugriff in Spring-Anwendungen und bietet Repository-Abstraktionen, Abfragemethoden und Spezifikationen für komplexe Abfragen. Der webforJ `SpringDataRepository`-Adapter verbindet Spring Data-Repositories mit den UI-Komponenten von webforJ und ermöglicht es Ihnen, JPA-Entitäten direkt an UI-Komponenten zu binden, dynamisches Filtern mit JPA-Spezifikationen zu implementieren und die Paginierung zu verwalten.

Der Adapter erkennt, welche Spring Data-Schnittstellen Ihr Repository implementiert - sei es `CrudRepository`, `PagingAndSortingRepository` oder `JpaSpecificationExecutor` - und stellt automatisch die entsprechenden Funktionen in Ihrer UI zur Verfügung. Das bedeutet, dass Ihre vorhandenen Spring Data-Repositories ohne Änderungen mit webforJ-Komponenten funktionieren, während die Typensicherheit gewahrt bleibt und Ihr bestehendes Domänenmodell verwendet wird.

:::tip[Erfahren Sie mehr über Spring Data JPA]
Für ein umfassendes Verständnis der Funktionen und Abfragemethoden von Spring Data JPA siehe die [Spring Data JPA-Dokumentation](https://docs.spring.io/spring-data/jpa/reference/).
:::

## Verwendung von SpringDataRepository {#using-springdatarepository}

Die Klasse `SpringDataRepository` verbindet Spring Data JPA-Repositories mit der Repository-Schnittstelle von webforJ und macht sie kompatibel mit UI-Komponenten wie [`Table`](../../components/table/overview), während alle Funktionen von Spring Data erhalten bleiben.

```java
// Ihr Spring Data-Repository
@Autowired
private PersonRepository personRepository;

// Mit SpringDataRepository umschließen
SpringDataRepository<Person, Long> adapter = new SpringDataRepository<>(personRepository);

// Mit webforJ Table verwenden
Table<Person> table = new Table<>();
table.setRepository(adapter);
```

### Schnittstellenerkennung {#interface-detection}

Spring Data-Repositories verwenden Schnittstellenerbschaft, um Funktionen hinzuzufügen. Sie beginnen mit grundlegenden CRUD-Operationen und fügen Schnittstellen für Funktionen wie Paginierung oder Spezifikationen hinzu:

```java
// Nur grundlegende CRUD
public interface CustomerRepository extends CrudRepository<Customer, Long> {}

// CRUD + Paginierung + Sortierung
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {}

// Vollständiges Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, 
                                           JpaSpecificationExecutor<Customer> {}
```

`SpringDataRepository` überprüft, welche Schnittstellen Ihr Repository implementiert, und passt sein Verhalten entsprechend an. Wenn Ihr Repository Paginierung unterstützt, ermöglicht der Adapter paginierte Abfragen. Wenn es `JpaSpecificationExecutor` implementiert, können Sie dynamisches Filtern mit Spezifikationen verwenden.

### Repository-Funktionen {#repository-capabilities}

Jede Spring Data-Schnittstelle fügt spezifische Funktionen hinzu, die `SpringDataRepository` nutzen kann:

- **CrudRepository** - Grundoperationen: `save`, `delete`, `findById`, `findAll`
- **PagingAndSortingRepository** - Fügt paginierte Abfragen und Sortierung hinzu
- **JpaRepository** - Kombiniert CRUD und Paginierung/Sortierung mit Batch-Operationen
- **JpaSpecificationExecutor** - Dynamische Abfragen mithilfe von JPA-Spezifikationen

### Erstellen eines Spring Data-Repositories {#creating-a-spring-data-repository}

Für maximale Kompatibilität mit webforJ-Komponenten sollten Sie Repositories erstellen, die sowohl `JpaRepository` als auch `JpaSpecificationExecutor` implementieren:

```java title="PersonRepository.java"
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository
    extends JpaRepository<Person, Long>,
            JpaSpecificationExecutor<Person> {
    // Benutzerdefinierte Abfragemethoden können hier hinzugefügt werden
}
```

Diese Kombination bietet:

- Finden nach ID-Operationen
- Paginierung mit optimaler Leistung
- Sortierungsfähigkeiten
- Filterung mithilfe der Java Persistence API-Spezifikation
- Zähloperationen mit und ohne Filter

## Arbeiten mit `Table` {#working-with-table}

Das folgende Beispiel verwendet ein `PersonRepository`, das `JpaRepository` und `JpaSpecificationExecutor` erweitert. Diese Kombination ermöglicht das Sortieren über Kopfzeilen und dynamisches Filtern mit Spezifikationen.

```java title="TableView.java"
@Route
public class TableView extends Composite<Div> {
  private SpringDataRepository<Person, Long> repository;
  private Table<Person> table = new Table<>();

  public TableView(PersonRepository personRepository) {
    // Spring Data-Repository für webforJ umschließen
    repository = new SpringDataRepository<>(personRepository);
    
    // Mit der Tabelle verbinden
    table.setRepository(repository);
    
    // Spalten definieren
    table.addColumn("name", Person::getFullName)
          .setPropertyName("firstName"); // Nach tatsächlicher JPA-Eigenschaft sortieren
    table.addColumn("email", Person::getEmail);
    table.addColumn("age", person -> 
          person.getAge() != null ? person.getAge().toString() : "");
    table.addColumn("city", Person::getCity);
    table.addColumn("profession", Person::getProfession);
    
    // Sortierung aktivieren
    table.getColumns().forEach(column -> column.setSortable(true));
  }
}
```

Die Methode `setPropertyName()` ist wichtig für das Sortieren - sie sagt dem Adapter, welche JPA-Eigenschaft in der `ORDER BY`-Klausel verwendet werden soll, wenn nach dieser Spalte sortiert wird. Ohne sie funktioniert das Sortieren nicht für berechnete Eigenschaften wie `getFullName()`.

## Filtern mit JPA-Spezifikationen {#filtering-with-jpa-specifications}

`SpringDataRepository` verwendet JPA-Spezifikationen für dynamische Abfragen, die auf die Repository-Operationen `findBy` und `count` angewendet werden.

:::tip[Erfahren Sie mehr über das Filtern]
Um zu verstehen, wie das Filtern mit webforJ-Repositories funktioniert, einschließlich Basisfiltern und Filterkomposition, siehe die [Repository-Dokumentation](../../advanced/repository/overview).
::: 

```java
// Nach Stadt filtern
Specification<Person> cityFilter = (root, query, cb) -> 
    cb.equal(root.get("city"), "New York");
repository.setFilter(cityFilter);

// Mehrere Bedingungen
Specification<Person> complexFilter = (root, query, cb) -> 
    cb.and(
        cb.equal(root.get("profession"), "Engineer"),
        cb.greaterThanOrEqualTo(root.get("age"), 25)
    );
repository.setFilter(complexFilter);

// Filter löschen
repository.setFilter(null);
```
