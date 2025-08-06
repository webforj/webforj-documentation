---
title: Spring Data JPA
sidebar_position: 20
_i18n_hash: f97a3247bcdeadf193a049bdb6d1a3bc
---
Spring Data JPA ist der de facto Standard für den Datenzugriff in Spring-Anwendungen und bietet Repository-Abstraktionen, Abfragemethoden und Spezifikationen für komplexe Abfragen. Der webforJ `SpringDataRepository`-Adapter verbindet Spring Data-Repositories mit den UI-Komponenten von webforJ, wodurch Sie JPA-Entitäten direkt mit UI-Komponenten verknüpfen, dynamisches Filtern mit JPA-Spezifikationen implementieren und die Seitenpagination verwalten können.

Der Adapter erkennt, welche Spring Data-Schnittstellen Ihr Repository implementiert - ob es sich um `CrudRepository`, `PagingAndSortingRepository` oder `JpaSpecificationExecutor` handelt - und stellt automatisch die entsprechenden Funktionen in Ihrer UI bereit. Das bedeutet, dass Ihre bestehenden Spring Data-Repositories ohne Änderungen mit webforJ-Komponenten funktionieren, während die Typensicherheit erhalten bleibt und Ihr bestehendes Domänenmodell genutzt wird.

:::tip[Mehr über Spring Data JPA erfahren]
Für ein umfassendes Verständnis der Funktionen und Abfragemethoden von Spring Data JPA siehe die [Spring Data JPA-Dokumentation](https://docs.spring.io/spring-data/jpa/reference/).
:::

## Verwendung von SpringDataRepository {#using-springdatarepository}

Die `SpringDataRepository`-Klasse verbindet Spring Data JPA-Repositories mit der Repository-Schnittstelle von webforJ, wodurch sie mit UI-Komponenten wie [`Table`](../../components/table/overview) kompatibel ist und gleichzeitig alle Funktionen von Spring Data beibehält.

```java
// Ihr Spring Data-Repository
@Autowired
private PersonRepository personRepository;

// Mit SpringDataRepository umschließen
SpringDataRepository<Person, Long> adapter = new SpringDataRepository<>(personRepository);

// Verwendung mit webforJ Table
Table<Person> table = new Table<>();
table.setRepository(adapter);
```

### Schnittstellenerkennung {#interface-detection}

Spring Data-Repositories verwenden Schnittstellenvererbung, um Funktionen hinzuzufügen. Sie beginnen mit grundlegenden CRUD-Operationen und fügen Schnittstellen für Funktionen wie Pagination oder Spezifikationen hinzu:

```java
// Nur grundlegendes CRUD
public interface CustomerRepository extends CrudRepository<Customer, Long> {}

// CRUD + Pagination + Sortierung
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {}

// Vollständiges Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, 
                                           JpaSpecificationExecutor<Customer> {}
```

`SpringDataRepository` untersucht, welche Schnittstellen Ihr Repository implementiert und passt sein Verhalten entsprechend an. Wenn Ihr Repository die Seitenpagination unterstützt, aktiviert der Adapter paginierte Abfragen. Wenn es `JpaSpecificationExecutor` implementiert, können Sie dynamisches Filtern mit Spezifikationen verwenden.

### Repository-Funktionen {#repository-capabilities}

Jede Spring Data-Schnittstelle fügt spezifische Funktionen hinzu, die `SpringDataRepository` verwenden kann:

- **CrudRepository** - Grundlegende Operationen: `save`, `delete`, `findById`, `findAll`
- **PagingAndSortingRepository** - Fügt paginierte Abfragen und Sortierung hinzu
- **JpaRepository** - Kombiniert CRUD und Pagination/Sortierung mit Batch-Operationen
- **JpaSpecificationExecutor** - Dynamische Abfragen mit JPA-Spezifikationen

### Erstellen eines Spring Data-Repositorys {#creating-a-spring-data-repository}

Für maximale Kompatibilität mit webforJ-Komponenten erstellen Sie Repositories, die sowohl `JpaRepository` als auch `JpaSpecificationExecutor` implementieren:

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
- Pagination mit optimaler Leistung
- Sortierfunktionen
- Filterung nach Java Persistence API-Spezifikation
- Zähloperationen mit und ohne Filter

## Arbeiten mit `Table` {#working-with-table}

Das folgende Beispiel verwendet ein `PersonRepository`, das `JpaRepository` und `JpaSpecificationExecutor` erweitert. Diese Kombination ermöglicht das Sortieren über Spaltenüberschriften und dynamisches Filtern mit Spezifikationen.

```java title="TableView.java"
@Route
public class TableView extends Composite<Div> {
  private SpringDataRepository<Person, Long> repository;
  private Table<Person> table = new Table<>();

  public TableView(PersonRepository personRepository) {
    // Spring Data-Repository für webforJ umschließen
    repository = new SpringDataRepository<>(personRepository);
    
    // Mit Tabelle verbinden
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

Die Methode `setPropertyName()` ist wichtig für die Sortierung - sie teilt dem Adapter mit, welche JPA-Eigenschaft in der `ORDER BY`-Klausel verwendet werden soll, wenn nach dieser Spalte sortiert wird. Ohne sie funktioniert die Sortierung nicht für berechnete Eigenschaften wie `getFullName()`.

## Filtern mit JPA-Spezifikationen {#filtering-with-jpa-specifications}

`SpringDataRepository` verwendet JPA-Spezifikationen für dynamische Abfragen, die auf den Repository-`findBy`- und `count`-Operationen angewendet werden.

:::tip[Mehr über Filtern erfahren]
Um zu verstehen, wie das Filtern mit webforJ-Repositories funktioniert, einschließlich Basisfiltern und Filterzusammensetzung, siehe die [Repository-Dokumentation](../../advanced/repository/overview).
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

// Filter zurücksetzen
repository.setFilter(null);
```
