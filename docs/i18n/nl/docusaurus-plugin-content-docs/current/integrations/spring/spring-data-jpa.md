---
title: Spring Data JPA
sidebar_position: 20
_i18n_hash: f97a3247bcdeadf193a049bdb6d1a3bc
---
Spring Data JPA is de facto standaard voor gegevens toegang in Spring-toepassingen, en biedt repository-abstraheringen, query-methoden en specificaties voor complexe queries. De webforJ `SpringDataRepository` adapter verbindt Spring Data repositories met webforJ's UI-componenten, waardoor je JPA-entiteiten direct aan UI-componenten kunt koppelen, dynamische filtering met JPA-specificaties kunt implementeren en paginering kunt afhandelen.

De adapter detecteert welke Spring Data-interfaces jouw repository implementeert - of het nu `CrudRepository`, `PagingAndSortingRepository` of `JpaSpecificationExecutor` is - en biedt automatisch de bijbehorende functies in jouw UI. Dit betekent dat jouw bestaande Spring Data-repositories werken met webforJ-componenten zonder aanpassingen, terwijl typeveiligheid behouden blijft en gebruik wordt gemaakt van je bestaande domeinmodel.

:::tip[Leer meer over Spring Data JPA]
Voor een uitgebreide uitleg van de functies en query-methoden van Spring Data JPA, zie [Spring Data JPA-documentatie](https://docs.spring.io/spring-data/jpa/reference/).
:::

## Gebruik van SpringDataRepository {#using-springdatarepository}

De `SpringDataRepository`-klasse verbindt Spring Data JPA-repositories met de repository-interface van webforJ, zodat ze compatibel zijn met UI-componenten zoals [`Table`](../../components/table/overview) terwijl alle Spring Data-functies behouden blijven.

```java
// Jouw Spring Data repository
@Autowired
private PersonRepository personRepository;

// Wikkel het in SpringDataRepository
SpringDataRepository<Person, Long> adapter = new SpringDataRepository<>(personRepository);

// Gebruik met webforJ Table
Table<Person> table = new Table<>();
table.setRepository(adapter);
```

### Interface-detectie {#interface-detection}

Spring Data-repositories gebruiken interface-erfenis om mogelijkheden toe te voegen. Je begint met basis CRUD-bewerkingen en voegt interfaces toe voor functies zoals paginering of specificaties:

```java
// Alleen basis CRUD
public interface CustomerRepository extends CrudRepository<Customer, Long> {}

// CRUD + Paginering + Sorteren
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {}

// Volledig functionele repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, 
                                           JpaSpecificationExecutor<Customer> {}
```

`SpringDataRepository` onderzoekt welke interfaces jouw repository implementeert en past zijn gedrag dienovereenkomstig aan. Als jouw repository paginering ondersteunt, zal de adapter gepagineerde queries inschakelen. Als het `JpaSpecificationExecutor` implementeert, kun je dynamische filtering met specificaties gebruiken.

### Repository-mogelijkheden {#repository-capabilities}

Elke Spring Data-interface voegt specifieke mogelijkheden toe die `SpringDataRepository` kan gebruiken:

- **CrudRepository** - Basisbewerkingen: `save`, `delete`, `findById`, `findAll`
- **PagingAndSortingRepository** - Voegt gepagineerde queries en sorteren toe
- **JpaRepository** - Combineert CRUD en paginering/sorteren met batchbewerkingen
- **JpaSpecificationExecutor** - Dynamische queries met JPA-specificaties

### Een Spring Data-repository maken {#creating-a-spring-data-repository}

Voor maximale compatibiliteit met webforJ-componenten, maak repositories die zowel `JpaRepository` als `JpaSpecificationExecutor` implementeren:

```java title="PersonRepository.java"
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository
    extends JpaRepository<Person, Long>,
            JpaSpecificationExecutor<Person> {
    // Aangepaste query-methoden kunnen hier komen
}
```

Deze combinatie biedt:

- Zoekoperaties op basis van ID
- Paginering met optimale prestaties
- Sorteermogelijkheden
- Java Persistence API-specifiëring filtering
- Tellen van operaties met en zonder filters

## Werken met `Table` {#working-with-table}

Het volgende voorbeeld gebruikt een `PersonRepository` dat `JpaRepository` en `JpaSpecificationExecutor` uitbreidt. Deze combinatie maakt sorteren door kolomkoppen en dynamische filtering met specificaties mogelijk.

```java title="TableView.java"
@Route
public class TableView extends Composite<Div> {
  private SpringDataRepository<Person, Long> repository;
  private Table<Person> table = new Table<>();

  public TableView(PersonRepository personRepository) {
    // Wikkel Spring Data repository voor webforJ
    repository = new SpringDataRepository<>(personRepository);
    
    // Verbind met de tabel
    table.setRepository(repository);
    
    // Definieer kolommen
    table.addColumn("name", Person::getFullName)
          .setPropertyName("firstName"); // Sorteer op werkelijke JPA-eigenschap
    table.addColumn("email", Person::getEmail);
    table.addColumn("age", person -> 
          person.getAge() != null ? person.getAge().toString() : "");
    table.addColumn("city", Person::getCity);
    table.addColumn("profession", Person::getProfession);
    
    // Sorteermogelijkheid inschakelen
    table.getColumns().forEach(column -> column.setSortable(true));
  }
}
```

De `setPropertyName()` methode is belangrijk voor sorteren - het vertelt de adapter welke JPA-eigenschap te gebruiken in de `ORDER BY` clausule bij sorteren op die kolom. Zonder deze zal sorteren niet werken voor berekende eigenschappen zoals `getFullName()`.

## Filtering met JPA-specificaties {#filtering-with-jpa-specifications}

`SpringDataRepository` gebruikt JPA-specificaties voor dynamische queries en deze worden toegepast op de repository `findBy` en `count` operaties.

:::tip[Leer meer over filtering]
Om te begrijpen hoe filtering werkt met webforJ-repositories, inclusief basisfilters en filtercompositie, zie de [Repository-documentatie](../../advanced/repository/overview).
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

// Wegenemen filter
repository.setFilter(null);
```
