---
title: Spring Data JPA
sidebar_position: 20
_i18n_hash: 3fe8c744a49adaaa35e1e30c53b5c60f
---
Spring Data JPA on de facto -standardi tietojen käsittelyssä Spring-sovelluksissa, tarjoten varastointiapstraktioita, kyselymenetelmiä ja spesifikaatioita monimutkaisille kyselyille. webforJ `SpringDataRepository` -adapteri yhdistää Spring Data -varastot webforJ:n käyttöliittymäkomponentteihin, mahdollistaen JPA-entiteettien sitomisen suoraan käyttöliittymäkomponentteihin, dynaamisen suodattamisen JPA-spesifikaatioiden avulla ja sivutuksen käsittelyn.

Adapteri tunnistaa, mitä Spring Data -rajapintoja varastosi toteuttaa - olipa se sitten `CrudRepository`, `PagingAndSortingRepository` tai `JpaSpecificationExecutor` - ja tarjoaa automaattisesti vastaavat ominaisuudet käyttöliittymässäsi. Tämä tarkoittaa, että nykyiset Spring Data -varastot toimivat webforJ-komponenttien kanssa ilman muutoksia, ylläpitäen tyyppiturvallisuutta ja käyttäen olemassa olevaa domenimalliasi.

:::tip[Opi lisää Spring Data JPA:sta]
Saadaksesi kattavan ymmärryksen Spring Data JPA:n ominaisuuksista ja kyselymenetelmistä, katso [Spring Data JPA -dokumentaatio](https://docs.spring.io/spring-data/jpa/reference/).
:::

## Using SpringDataRepository {#using-springdatarepository}

`SpringDataRepository`-luokka yhdistää Spring Data JPA -varastot webforJ:n Repository-rajapintaan, tehden niistä yhteensopivia käyttöliittymäkomponenttien, kuten [`Table`](../../components/table/overview), kanssa säilyttäen kaikki Spring Data -ominaisuudet.

```java
// Your Spring Data repository
@Autowired
private PersonRepository personRepository;

// Wrap it with SpringDataRepository
SpringDataRepository<Person, Long> adapter = new SpringDataRepository<>(personRepository);

// Use with webforJ Table
Table<Person> table = new Table<>();
table.setRepository(adapter);
```

### Interface detection {#interface-detection}

Spring Data -varastot käyttävät rajapinnan perintää lisätäkseen ominaisuuksia. Aloitat perus CRUD-käytöistä ja lisäät rajapintoja ominaisuuksille, kuten sivutukselle tai spesifikaatioille:

```java
// Basic CRUD only
public interface CustomerRepository extends CrudRepository<Customer, Long> {}

// CRUD + Pagination + Sorting
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {}

// Full featured repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {}
```

`SpringDataRepository` tutkii, mitä rajapintoja varastosi toteuttaa ja mukauttaa käyttäytymistään vastaavasti. Jos varastosi tukee sivutusta, adapteri mahdollistaa sivutettuja kyselyjä. Jos se toteuttaa `JpaSpecificationExecutor`-rajapinnan, voit käyttää dynaamista suodattamista spesifikaatioiden avulla.

### Repository capabilities {#repository-capabilities}

Jokainen Spring Data -rajapinta lisää erityisiä ominaisuuksia, joita `SpringDataRepository` voi hyödyntää:

- **CrudRepository** - Perustoiminnot: `save`, `delete`, `findById`, `findAll`
- **PagingAndSortingRepository** - Lisää sivutettuja kyselyjä ja lajittelun
- **JpaRepository** - Yhdistää CRUD- ja sivutus/lajittelu-toiminnot yhdessä erätoimintojen kanssa
- **JpaSpecificationExecutor** - Dynaamisia kyselyitä käyttäen JPA-spesifikaatioita

### Creating a Spring Data repository {#creating-a-spring-data-repository}

Maksimaalisen yhteensopivuuden saavuttamiseksi webforJ-komponenttien kanssa, luo varastoja, jotka toteuttavat sekä `JpaRepository` että `JpaSpecificationExecutor`:

```java title="PersonRepository.java"
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository
  extends JpaRepository<Person, Long>,
      JpaSpecificationExecutor<Person> {
  // Räätälöityjen kyselymenetelmien määrittelyt voidaan lisätä tänne
}
```

Tämä yhdistelmä tarjoaa:

- Löydä ID:llä -toiminnot
- Sivutus optimaalisella suorituskyvyllä
- Lajittelutoiminnot
- Java Persistence API -spesifikaation suodatus
- Laskentatoiminnot suodattimien kanssa ja ilman

## Working with `Table` {#working-with-table}

Seuraavassa esimerkissä käytetään `PersonRepository`:ta, joka laajentaa `JpaRepository` ja `JpaSpecificationExecutor`. Tämä yhdistelmä mahdollistaa lajittelun sarakeotsikoiden kautta ja dynaamisen suodattamisen spesifikaatioiden avulla.

```java title="TableView.java"
@Route
public class TableView extends Composite<Div> {
  private SpringDataRepository<Person, Long> repository;
  private Table<Person> table = new Table<>();

  public TableView(PersonRepository personRepository) {
    // Kääri Spring Data -varasto webforJ:lle
    repository = new SpringDataRepository<>(personRepository);
    
    // Yhdistä tauluun
    table.setRepository(repository);
    
    // Määritä sarakkeet
    table.addColumn("name", Person::getFullName)
          .setPropertyName("firstName"); // Lajittele varsinaisella JPA-ominaisuudella
    table.addColumn("email", Person::getEmail);
    table.addColumn("age", person -> 
          person.getAge() != null ? person.getAge().toString() : "");
    table.addColumn("city", Person::getCity);
    table.addColumn("profession", Person::getProfession);
    
    // Ota lajittelu käyttöön
    table.getColumns().forEach(column -> column.setSortable(true));
  }
}
```

`setPropertyName()` -menetelmä on tärkeä lajittelua varten - se kertoo adapterille, mitä JPA-ominaisuutta käyttää `ORDER BY` -lausekkeessa, kun lajitetaan kyseisen sarakkeen mukaan. Ilman sitä lajittelu ei toimi lasketuissa ominaisuuksissa, kuten `getFullName()`.

## Filtering with JPA specifications {#filtering-with-jpa-specifications}

`SpringDataRepository` käyttää JPA-spesifikaatioita dynaamisille kyselyille ja niitä sovelletaan varaston `findBy` ja `count` -toimintoihin.

:::tip[Opi lisää suodattamisesta]
Ymmärtääksesi, kuinka suodatus toimii webforJ-varastoissa, mukaan lukien perussuodattimet ja suodattimien yhdistely, katso [Repository-dokumentaatio](../../advanced/repository/overview).
::: 

```java
// Suodata kaupungin mukaan
Specification<Person> cityFilter = (root, query, cb) -> 
  cb.equal(root.get("city"), "New York");
repository.setFilter(cityFilter);

// Useita ehtoja
Specification<Person> complexFilter = (root, query, cb) -> 
  cb.and(
    cb.equal(root.get("profession"), "Engineer"),
    cb.greaterThanOrEqualTo(root.get("age"), 25)
  );
repository.setFilter(complexFilter);

// Tyhjennä suodatin
repository.setFilter(null);
```
