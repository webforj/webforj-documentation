---
title: Spring Data JPA
sidebar_position: 20
description: >-
  Adapt Spring Data JPA repositories to webforJ Table and Repository components
  with SpringDataRepository, pagination, sorting, and Specifications.
_i18n_hash: 04bda665f576fabc5db3284e9ec201e9
---
Spring Data JPA is de facto standaard voor gegevens toegang in Spring-applicaties en biedt repository abstracties, query-methoden en specificaties voor complexe queries. De webforJ `SpringDataRepository` adapter verbindt Spring Data repositories met de UI-componenten van webforJ, waardoor je JPA-entiteiten direct kunt binden aan UI-componenten, dynamische filtering kunt implementeren met JPA Specificaties en paginering kunt afhandelen.

De adapter detecteert welke Spring Data interfaces jouw repository implementeert - of het nu `CrudRepository`, `PagingAndSortingRepository` of `JpaSpecificationExecutor` is - en biedt automatisch de bijbehorende functionaliteiten in jouw UI. Dit betekent dat je bestaande Spring Data repositories werken met webforJ componenten zonder aanpassingen, terwijl typeveiligheid behouden blijft en je gebruik kunt maken van jouw bestaande domeinmodel.

:::tip[Leer meer over Spring Data JPA]
Voor een uitgebreide uitleg van de functies en query-methoden van Spring Data JPA, zie de [Spring Data JPA documentatie](https://docs.spring.io/spring-data/jpa/reference/).
:::

## Gebruik van SpringDataRepository {#using-springdatarepository}

De `SpringDataRepository` klasse verbindt Spring Data JPA repositories met de Repository-interface van webforJ, waardoor ze compatibel zijn met UI-componenten zoals [`Table`](../../components/table/overview) terwijl alle Spring Data functies behouden blijven.

```java
// Jouw Spring Data repository
@Autowired
private PersonRepository personRepository;

// Verpak het met SpringDataRepository
SpringDataRepository<Person, Long> adapter = new SpringDataRepository<>(personRepository);

// Gebruik met webforJ Table
Table<Person> table = new Table<>();
table.setRepository(adapter);
```

### Interface detectie {#interface-detection}

Spring Data repositories gebruiken interface-erfelijkheid om functionaliteiten toe te voegen. Je begint met basis CRUD-operaties en voegt interfaces toe voor functies zoals paginering of specificaties:

```java
// Alleen basis CRUD
public interface CustomerRepository extends CrudRepository<Customer, Long> {}

// CRUD + Paginering + Sorteren
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {}

// Volledig uitgeruste repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {}
```

`SpringDataRepository` examineert welke interfaces jouw repository implementeert en past zijn gedrag dienovereenkomstig aan. Als jouw repository paginering ondersteunt, stelt de adapter paginated queries in staat. Als het `JpaSpecificationExecutor` implementeert, kun je dynamische filtering gebruiken met specificaties.

### Repository capaciteiten {#repository-capabilities}

Elke Spring Data interface voegt specifieke mogelijkheden toe die `SpringDataRepository` kan gebruiken:

- **CrudRepository** - Basisoperaties: `save`, `delete`, `findById`, `findAll`
- **PagingAndSortingRepository** - Voegt paginated queries en sorteren toe
- **JpaRepository** - Combineert CRUD en paging/sorting met batchoperaties
- **JpaSpecificationExecutor** - Dynamische queries met behulp van JPA Specificaties

### Een Spring Data repository maken {#creating-a-spring-data-repository}

Voor maximale compatibiliteit met webforJ-componenten, maak repositories die zowel `JpaRepository` als `JpaSpecificationExecutor` implementeren:

```java title="PersonRepository.java"
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository
  extends JpaRepository<Person, Long>,
      JpaSpecificationExecutor<Person> {
  // aangepaste query-methoden kunnen hier gaan
}
```

Deze combinatie biedt:

- Vind-operaties op basis van ID
- Paginering met optimale prestaties
- Sorteerfunctionaliteit
- Java Persistence API Specificatie filtering
- Tel-operaties met en zonder filters

## Werken met `Table` {#working-with-table}

Het volgende voorbeeld gebruikt een `PersonRepository` die `JpaRepository` en `JpaSpecificationExecutor` uitbreidt. Deze combinatie stelt sorteren door kolomkoppen en dynamische filtering met specificaties in staat.

```java title="TableView.java"
@Route
public class TableView extends Composite<Div> {
  private SpringDataRepository<Person, Long> repository;
  private Table<Person> table = new Table<>();

  public TableView(PersonRepository personRepository) {
    // Verpak Spring Data repository voor webforJ
    repository = new SpringDataRepository<>(personRepository);

    // Verbinden met tabel
    table.setRepository(repository);

    // Definieer kolommen
    table.addColumn("name", Person::getFullName)
          .setPropertyName("firstName"); // Sorteren op echte JPA-eigenschap
    table.addColumn("email", Person::getEmail);
    table.addColumn("age", person ->
          person.getAge() != null ? person.getAge().toString() : "");
    table.addColumn("city", Person::getCity);
    table.addColumn("profession", Person::getProfession);

    // Sorteerfunctionaliteit inschakelen
    table.getColumns().forEach(column -> column.setSortable(true));
  }
}
```

De methode `setPropertyName()` is belangrijk voor sorteren - het vertelt de adapter welke JPA-eigenschap te gebruiken in de `ORDER BY` clausule wanneer gesorteerd wordt op die kolom. Zonder deze zal sorteren niet werken voor berekende eigenschappen zoals `getFullName()`.

## Filteren met JPA specificaties {#filtering-with-jpa-specifications}

`SpringDataRepository` gebruikt JPA Specificaties voor dynamische queries en deze worden toegepast op de repository `findBy` en `count` operaties.

:::tip[Leer meer over filteren]
Om te begrijpen hoe filteren werkt met webforJ repositories, inclusief basisfilters en filtercompositie, zie de [Repository documentatie](../../advanced/repository/overview).
:::

```java
// Filter op stad
Specification<Person> cityFilter = (root, query, cb) ->
  cb.equal(root.get("city"), "New York");
repository.setFilter(cityFilter);

// Meerdere voorwaarden
Specification<Person> complexFilter = (root, query, cb) ->
  cb.and(
    cb.equal(root.get("profession"), "Engineer"),
    cb.greaterThanOrEqualTo(root.get("age"), 25)
  );
repository.setFilter(complexFilter);

// Filter wissen
repository.setFilter(null);
```
