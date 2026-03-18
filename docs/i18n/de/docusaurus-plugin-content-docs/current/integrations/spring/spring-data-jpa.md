---
title: Spring Data JPA
sidebar_position: 20
_i18n_hash: 3fe8c744a49adaaa35e1e30c53b5c60f
---
Spring Data JPA ist der Standard für den Datenzugriff in Spring-Anwendungen und bietet Repository-Abstraktionen, Abfragemethoden und Spezifikationen für komplexe Abfragen. Der webforJ `SpringDataRepository` Adapter verbindet Spring Data-Repositories mit den UI-Komponenten von webforJ und ermöglicht es Ihnen, JPA-Entitäten direkt an UI-Komponenten zu binden, dynamische Filterung mit JPA-Spezifikationen umzusetzen und die Paginierung zu verwalten.

Der Adapter erkennt, welche Spring Data-Schnittstellen Ihr Repository implementiert - ob es sich um `CrudRepository`, `PagingAndSortingRepository` oder `JpaSpecificationExecutor` handelt - und stellt automatisch die entsprechenden Funktionen in Ihrer UI bereit. Das bedeutet, dass Ihre bestehenden Spring Data-Repositories ohne Modifikation mit webforJ-Komponenten funktionieren, während die Typensicherheit und Ihr bestehendes Domänenmodell beibehalten werden.

:::tip[Erfahren Sie mehr über Spring Data JPA]
Für ein umfassendes Verständnis der Funktionen und Abfragemethoden von Spring Data JPA siehe die [Spring Data JPA-Dokumentation](https://docs.spring.io/spring-data/jpa/reference/).
:::

## Verwendung von SpringDataRepository {#using-springdatarepository}

Die Klasse `SpringDataRepository` verbindet Spring Data JPA-Repositories mit der Repository-Schnittstelle von webforJ, wodurch sie mit UI-Komponenten wie [`Table`](../../components/table/overview) kompatibel werden, während alle Funktionen von Spring Data erhalten bleiben.

```java
// Ihr Spring Data-Repository
@Autowired
private PersonRepository personRepository;

// Wrapping mit SpringDataRepository
SpringDataRepository<Person, Long> adapter = new SpringDataRepository<>(personRepository);

// Verwendung mit webforJ Table
Table<Person> table = new Table<>();
table.setRepository(adapter);
```

### Schnittstellenerkennung {#interface-detection}

Spring Data-Repositories verwenden die Schnittstellenerbfolge, um Funktionen hinzuzufügen. Sie beginnen mit grundlegenden CRUD-Operationen und fügen Schnittstellen für Funktionen wie Paginierung oder Spezifikationen hinzu:

```java
// Nur grundlegende CRUD
public interface CustomerRepository extends CrudRepository<Customer, Long> {}

// CRUD + Paginierung + Sortierung
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {}

// Voll funktionsfähiges Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {}
```

`SpringDataRepository` prüft, welche Schnittstellen Ihr Repository implementiert, und passt sein Verhalten entsprechend an. Wenn Ihr Repository die Paginierung unterstützt, ermöglicht der Adapter paginierte Abfragen. Wenn es `JpaSpecificationExecutor` implementiert, können Sie dynamische Filterung mit Spezifikationen verwenden.

### Repository-Funktionen {#repository-capabilities}

Jede Spring Data-Schnittstelle fügt spezifische Funktionen hinzu, die `SpringDataRepository` nutzen kann:

- **CrudRepository** - Basisoperationen: `save`, `delete`, `findById`, `findAll`
- **PagingAndSortingRepository** - Fügt paginierte Abfragen und Sortierung hinzu
- **JpaRepository** - Kombiniert CRUD und Paginierung/Sortierung mit Batch-Operationen
- **JpaSpecificationExecutor** - Dynamische Abfragen mit JPA-Spezifikationen

### Erstellen eines Spring Data-Repositories {#creating-a-spring-data-repository}

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
- Paginierung mit optimaler Leistung
- Sortierungsfunktionen
- Filterung mit der Java Persistence API Specification
- Zähloperationen mit und ohne Filter

## Arbeiten mit `Table` {#working-with-table}

Im folgenden Beispiel wird ein `PersonRepository` verwendet, das `JpaRepository` und `JpaSpecificationExecutor` erweitert. Diese Kombination ermöglicht die Sortierung über Spaltenüberschriften und die dynamische Filterung mit Spezifikationen.

```java title="TableView.java"
@Route
public class TableView extends Composite<Div> {
  private SpringDataRepository<Person, Long> repository;
  private Table<Person> table = new Table<>();

  public TableView(PersonRepository personRepository) {
    // Wrap Spring Data-Repository für webforJ
    repository = new SpringDataRepository<>(personRepository);
    
    // Verbindung zur Tabelle
    table.setRepository(repository);
    
    // Definieren Sie die Spalten
    table.addColumn("name", Person::getFullName)
          .setPropertyName("firstName"); // Sortieren nach tatsächlicher JPA-Eigenschaft
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

Die Methode `setPropertyName()` ist wichtig für die Sortierung - sie gibt dem Adapter an, welche JPA-Eigenschaft im `ORDER BY`-Clause verwendet werden soll, wenn nach dieser Spalte sortiert wird. Ohne sie funktioniert die Sortierung nicht für berechnete Eigenschaften wie `getFullName()`.

## Filtern mit JPA-Spezifikationen {#filtering-with-jpa-specifications}

`SpringDataRepository` verwendet JPA-Spezifikationen für dynamische Abfragen, die auf die Repository-Operationen `findBy` und `count` angewendet werden.

:::tip[Erfahren Sie mehr über Filtering]
Um zu verstehen, wie das Filtern mit webforJ-Repositories funktioniert, einschließlich Basisfilter und Filterkomposition, siehe die [Repository-Dokumentation](../../advanced/repository/overview).
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
