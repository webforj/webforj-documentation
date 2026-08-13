---
title: Spring Data JPA
sidebar_position: 20
description: >-
  Adapt Spring Data JPA repositories to webforJ Table and Repository components
  with SpringDataRepository, pagination, sorting, and Specifications.
_i18n_hash: 04bda665f576fabc5db3284e9ec201e9
---
Spring Data JPA on de facto -standardi tietojen käsittelyssä Spring-sovelluksissa, tarjoten varastointiabstraktioita, kyselymenetelmiä ja spesifikaatioita monimutkaisille kyselyille. webforJ:n `SpringDataRepository` -sovitin yhdistää Spring Data -varastot webforJ:n käyttöliittymäkomponentteihin, mahdollistaen JPA-entiteettien sitomisen suoraan käyttöliittymäkomponentteihin, dynaamisen suodatuksen toteuttamisen JPA-spesifikaatioiden avulla ja sivutuksen käsittelemisen.

Sovitin havaitsee, mitä Spring Data -rajapintoja varastosi toteuttaa - oli se sitten `CrudRepository`, `PagingAndSortingRepository` tai `JpaSpecificationExecutor` - ja tarjoaa automaattisesti vastaavat ominaisuudet käyttöliittymässäsi. Tämä tarkoittaa, että olemassa olevat Spring Data -varastosi toimivat webforJ:n komponenttien kanssa ilman muutoksia, samalla säilyttäen tyyppiturvallisuuden ja käyttäen olemassa olevaa kenttämalliasi.

:::tip[Lisätietoja Spring Data JPA:sta]
Kattavan ymmärryksen saamiseksi Spring Data JPA:n ominaisuuksista ja kyselymenetelmistä, katso [Spring Data JPA -dokumentaatio](https://docs.spring.io/spring-data/jpa/reference/).
:::

## Käyttäen SpringDataRepositorya {#using-springdatarepository}

`SpringDataRepository`-luokka yhdistää Spring Data JPA -varastot webforJ:n Repository-rajapintaan, tehden niistä yhteensopivia käyttöliittymäkomponenttien kuten [`Table`](../../components/table/overview) kanssa säilyttäen kuitenkin kaikki Spring Data -ominaisuudet.

```java
// Oma Spring Data -varastosi
@Autowired
private PersonRepository personRepository;

// Kääri se SpringDataRepositoryyn
SpringDataRepository<Person, Long> adapter = new SpringDataRepository<>(personRepository);

// Käytä webforJ Taulukon kanssa
Table<Person> table = new Table<>();
table.setRepository(adapter);
```

### Rajapinnojen havaitseminen {#interface-detection}

Spring Data -varastot hyödyntävät rajapintojen perintää lisätäksesi ominaisuuksia. Aloitat perus CRUD-toiminnoista ja lisäät rajapintoja ominaisuuksille kuten sivutukselle tai spesifikaatioille:

```java
// Perus CRUD vain
public interface CustomerRepository extends CrudRepository<Customer, Long> {}

// CRUD + Sivutus + Lajittelu
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {}

// Täysin varusteltu varasto
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {}
```

`SpringDataRepository` tutkii, mitä rajapintoja varastosi toteuttaa ja mukauttaa käyttäytymistään sen mukaisesti. Jos varastosi tukee sivutusta, sovitin mahdollistaa sivutetut kyselyt. Jos se toteuttaa `JpaSpecificationExecutor`, voit käyttää dynaamista suodatusta spesifikaatioiden avulla.

### Varaston kyvyt {#repository-capabilities}

Jokainen Spring Data -rajapinta lisää erityisiä kykyjä, joita `SpringDataRepository` voi käyttää:

- **CrudRepository** - Perustoiminnallisuudet: `save`, `delete`, `findById`, `findAll`
- **PagingAndSortingRepository** - Lisää sivutettuja kyselyitä ja lajittelua
- **JpaRepository** - Yhdistää CRUD:n ja sivutuksen/lajittelun yhdessä erätoimintojen kanssa
- **JpaSpecificationExecutor** - Dynaamiset kyselyt käyttäen JPA-spesifikaatioita

### Spring Data -varaston luominen {#creating-a-spring-data-repository}

Maksimaalisen yhteensopivuuden varmistamiseksi webforJ-komponenttien kanssa, luo varastoja, jotka toteuttavat sekä `JpaRepository` että `JpaSpecificationExecutor`:

```java title="PersonRepository.java"
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository
  extends JpaRepository<Person, Long>,
      JpaSpecificationExecutor<Person> {
  // Mukautetut kyselymenetelmät voidaan lisätä tänne
}
```

Tämä yhdistelmä tarjoaa:

- Etsi ID:n mukaan -toiminnot
- Sivutus optimaalisen suorituskyvyn kanssa
- Lajittelukykyjä
- Java Persistence API Specification -suodatus
- Laskentatoiminnot suodattimella ja ilman

## Työskentely `Table`-komponentin kanssa {#working-with-table}

Seuraava esimerkki käyttää `PersonRepository`-luokkaa, joka laajentaa `JpaRepository` ja `JpaSpecificationExecutor`. Tämä yhdistelmä mahdollistaa lajittelun sarakeotsikoiden kautta ja dynaamisen suodatuksen spesifikaatioiden avulla.

```java title="TableView.java"
@Route
public class TableView extends Composite<Div> {
  private SpringDataRepository<Person, Long> repository;
  private Table<Person> table = new Table<>();

  public TableView(PersonRepository personRepository) {
    // Kääri Spring Data -varasto webforJ:lle
    repository = new SpringDataRepository<>(personRepository);

    // Liitä taulukkoon
    table.setRepository(repository);

    // Määritä sarakkeet
    table.addColumn("name", Person::getFullName)
          .setPropertyName("firstName"); // Lajittele varsinaisen JPA-ominaisuuden mukaan
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

`setPropertyName()`-menetelmä on tärkeä lajittelulle - se kertoo sovittimelle, mitä JPA-ominaisuutta käyttää `ORDER BY`-lauseessa kyseistä saraketta lajiteltaessa. Ilman sitä lajittelu ei toimi lasketuille ominaisuuksille, kuten `getFullName()`.

## Suodatus JPA-spesifikaatioiden avulla {#filtering-with-jpa-specifications}

`SpringDataRepository` käyttää JPA-spesifikaatioita dynaamisille kyselyille, ja niitä sovelletaan varaston `findBy` ja `count` toiminnoille.

:::tip[Lisätietoja suodatuksesta]
Ymmärtääksesi, miten suodatus toimii webforJ-varastoissa, mukaan lukien perussuodattimet ja suodattimien yhdistelmät, katso [Varasto-dokumentaatio](../../advanced/repository/overview).
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
