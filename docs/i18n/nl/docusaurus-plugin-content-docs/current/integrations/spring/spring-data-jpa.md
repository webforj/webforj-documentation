---
title: Spring Data JPA
sidebar_position: 20
_i18n_hash: 5aee4b031f1464780e7fd06e71946951
---
Spring Data JPA is de facto standaard voor gegevensaccess in Spring-toepassingen, en biedt repository-abstraheringen, query-methoden en specificaties voor complexe queries. De webforJ `SpringDataRepository` adapter overbrugt Spring Data repositories met de UI-componenten van webforJ, waardoor je JPA-entiteiten rechtstreeks kunt koppelen aan UI-componenten, dynamische filtering kunt implementeren met JPA-specificaties en paginering kunt afhandelen.

De adapter detecteert welke Spring Data-interfaces jouw repository implementeert - of het nu `CrudRepository`, `PagingAndSortingRepository` of `JpaSpecificationExecutor` is - en biedt automatisch de bijbehorende functies in jouw UI aan. Dit betekent dat jouw bestaande Spring Data repositories werken met webforJ-componenten zonder wijziging, terwijl de typeveiligheid behouden blijft en je je bestaande domeinmodel gebruikt.

:::tip[Leer meer over Spring Data JPA]
Voor een uitgebreide kennismaking met de functies en query-methoden van Spring Data JPA, zie [Spring Data JPA documentatie](https://docs.spring.io/spring-data/jpa/reference/).
:::

## Gebruik van SpringDataRepository {#using-springdatarepository}

De `SpringDataRepository` klasse overbrugt Spring Data JPA repositories met de Repository-interface van webforJ, waardoor ze compatibel zijn met UI-componenten zoals [`Table`](../../components/table/overview) terwijl alle functies van Spring Data behouden blijven.

```java
// Jouw Spring Data repository
@Autowired
private PersonRepository personRepository;

// Verpak het met SpringDataRepository
SpringDataRepository<Person, Long> adapter = new SpringDataRepository<>(personRepository);

// Gebruik met webforJ Tabel
Table<Person> table = new Table<>();
table.setRepository(adapter);
```

### Interface detectie {#interface-detection}

Spring Data repositories gebruiken interface-erfelijkheid om mogelijkheden toe te voegen. Je begint met basis CRUD-operaties en voegt interfaces toe voor functies zoals paginering of specificaties:

```java
// Alleen basis CRUD
public interface CustomerRepository extends CrudRepository<Customer, Long> {}

// CRUD + Paginering + Sorteren
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {}

// Volledig uitgeruste repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, 
                                           JpaSpecificationExecutor<Customer> {}
```

`SpringDataRepository` onderzoekt welke interfaces jouw repository implementeert en past zijn gedrag dienovereenkomstig aan. Als jouw repository paginering ondersteunt, staat de adapter gepagineerde queries toe. Als het `JpaSpecificationExecutor` implementeert, kun je dynamische filtering gebruiken met specificaties.

### Repository-mogelijkheden {#repository-capabilities}

Elke Spring Data interface voegt specifieke mogelijkheden toe die `SpringDataRepository` kan gebruiken:

- **CrudRepository** - Basisoperaties: `save`, `delete`, `findById`, `findAll`
- **PagingAndSortingRepository** - Voegt gepagineerde queries en sorteren toe
- **JpaRepository** - Combineert CRUD en pagineren/sorteren met batch-operaties
- **JpaSpecificationExecutor** - Dynamische queries met JPA-specificaties

### Een Spring Data repository aanmaken {#creating-a-spring-data-repository}

Voor maximale compatibiliteit met webforJ-componenten, maak repositories die zowel `JpaRepository` als `JpaSpecificationExecutor` implementeren:

```java title="PersonRepository.java"
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository
    extends JpaRepository<Person, Long>,
            JpaSpecificationExecutor<Person> {
    // Aangepaste query-methoden kunnen hier worden toegevoegd
}
```

Deze combinatie biedt:

- Vinden op ID-operaties
- Paginering met optimale prestaties
- Sorteermogelijkheden
- Java Persistence API Specification-filtering
- Teloperaties met en zonder filters

## Werken met `Table` {#working-with-table}

Het volgende voorbeeld gebruikt een `PersonRepository` dat `JpaRepository` en `JpaSpecificationExecutor` uitbreidt. Deze combinatie maakt sorteren via kolomkoppen en dynamische filtering met specificaties mogelijk.

```java title="TableView.java"
@Route
public class TableView extends Composite<Div> {
  private SpringDataRepository<Person, Long> repository;
  private Table<Person> table = new Table<>();

  public TableView(PersonRepository personRepository) {
    // Verpak Spring Data repository voor webforJ
    repository = new SpringDataRepository<>(personRepository);
    
    // Verbind met tabel
    table.setRepository(repository);
    
    // Definieer kolommen
    table.addColumn("name", Person::getFullName)
          .setPropertyName("firstName"); // Sorteer op de werkelijke JPA-eigenschap
    table.addColumn("email", Person::getEmail);
    table.addColumn("age", person -> 
          person.getAge() != null ? person.getAge().toString() : "");
    table.addColumn("city", Person::getCity);
    table.addColumn("profession", Person::getProfession);
    
    // Sorteren inschakelen
    table.getColumns().forEach(column -> column.setSortable(true));
  }
}
```

De `setPropertyName()` methode is belangrijk voor sorteren - het vertelt de adapter welke JPA-eigenschap te gebruiken in de `ORDER BY` clausule bij het sorteren op die kolom. Zonder deze zal sorteren niet werken voor berekende eigenschappen zoals `getFullName()`.

## Filteren met JPA-specificaties {#filtering-with-jpa-specifications}

`SpringDataRepository` gebruikt JPA-specificaties voor dynamische queries en deze worden toegepast op de repository `findBy` en `count` operaties.

:::tip[Leer meer over filteren]
Om te begrijpen hoe filteren werkt met webforJ repositories, inclusief basisfilters en filtercompositie, zie de [Repository documentatie](../../advanced/repository/overview).
::: 

```java
// Filteren op stad
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
