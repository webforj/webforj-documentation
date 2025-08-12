---
title: Spring Data JPA
sidebar_position: 20
_i18n_hash: 5aee4b031f1464780e7fd06e71946951
---
Spring Data JPA on de facto -standardi tietojen käsittelyssä Spring-sovelluksissa, tarjoten varastoabstraktioita, kyselymenetelmiä ja spesifikaatioita monimutkaisille kyselyille. webforJ `SpringDataRepository` -sovitin yhdistää Spring Data -varastot webforJ:n UI-komponenttien kanssa, mahdollistaen JPA-olioiden sitomisen suoraan UI-komponentteihin, dynaamisen suodattamisen JPA-spesifikaatioiden avulla ja sivutuksen käsittelyn.

Sovitin tunnistaa, mitkä Spring Data -rajapinnat varastosi toteuttaa - olipa se `CrudRepository`, `PagingAndSortingRepository` tai `JpaSpecificationExecutor` - ja tarjoaa automaattisesti vastaavat ominaisuudet UI:ssasi. Tämä tarkoittaa, että olemassa olevat Spring Data -varastosi toimivat webforJ-komponenttien kanssa ilman muutoksia, samalla säilyttäen tyyppiturvallisuuden ja käyttäen olemassa olevaa sovellusmalliasi.

:::tip[Lisätietoa Spring Data JPA:sta]
Saadaksesi kattavan käsityksen Spring Data JPA:n ominaisuuksista ja kyselymenetelmistä, katso [Spring Data JPA -dokumentaatio](https://docs.spring.io/spring-data/jpa/reference/).
:::

## SpringDataRepository käyttö {#using-springdatarepository}

`SpringDataRepository`-luokka yhdistää Spring Data JPA -varastot webforJ:n Repository-rajapintaan, tehden niistä yhteensopivia UI-komponenttien, kuten [`Table`](../../components/table/overview), kanssa samalla säilyttäen kaikki Spring Data -ominaisuudet.

```java
// Olet Spring Data -varasto
@Autowired
private PersonRepository personRepository;

// Kääri se SpringDataRepositoryyn
SpringDataRepository<Person, Long> adapter = new SpringDataRepository<>(personRepository);

// Käytä webforJ Taulukossa
Table<Person> table = new Table<>();
table.setRepository(adapter);
```

### Rajapinnan tunnistus {#interface-detection}

Spring Data -varastot käyttävät rajapinnan perimistä ominaisuuksien lisäämiseksi. Aloitat perus CRUD-toiminnoista ja lisäät rajapintoja ominaisuuksille, kuten sivutukselle tai spesifikaatioille:

```java
// Perus CRUD vain
public interface CustomerRepository extends CrudRepository<Customer, Long> {}

// CRUD + Sivutus + Järjestäminen
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {}

// Täysin varusteltu varasto
public interface CustomerRepository extends JpaRepository<Customer, Long>, 
                                           JpaSpecificationExecutor<Customer> {}
```

`SpringDataRepository` tarkistaa, mitkä rajapinnat varastosi toteuttaa ja mukauttaa käyttäytymistään tämän mukaan. Jos varastosi tukee sivutusta, sovitin mahdollistaa sivutuksetut kyselyt. Jos se toteuttaa `JpaSpecificationExecutor`-rajapinnan, voit käyttää dynaamista suodattamista spesifikaatioiden avulla.

### Varaston ominaisuudet {#repository-capabilities}

Jokainen Spring Data -rajapinta lisää erityisiä ominaisuuksia, joita `SpringDataRepository` voi käyttää:

- **CrudRepository** - Perustoiminnot: `save`, `delete`, `findById`, `findAll`
- **PagingAndSortingRepository** - Lisää sivutettuja kyselyitä ja lajittelua
- **JpaRepository** - Yhdistää CRUD:n ja sivutuksen/lajittelun joukkotoimintoihin
- **JpaSpecificationExecutor** - Dynaamiset kyselyt JPA-specifikaatioiden avulla

### Spring Data -varaston luominen {#creating-a-spring-data-repository}

Maksimaalisen yhteensopivuuden saavuttamiseksi webforJ-komponenttien kanssa, luo varastoja, jotka toteuttavat sekä `JpaRepository` että `JpaSpecificationExecutor`:

```java title="PersonRepository.java"
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository
    extends JpaRepository<Person, Long>,
            JpaSpecificationExecutor<Person> {
    // Mukautetut kyselymenetelmät voivat olla täällä
}
```

Tämä yhdistelmä tarjoaa:

- Löydä ID:llä -toiminnot
- Sivutus optimaalisen suorituskyvyn avulla
- Lajittelukyvykkyydet
- Java Persistence API Specification -suodatus
- Laskentatoiminnot suodattimilla ja ilman suodattimia

## Työskentely `Table`-komponentin kanssa {#working-with-table}

Seuraava esimerkki käyttää `PersonRepositorya`, joka laajentaa `JpaRepository` ja `JpaSpecificationExecutor`. Tämä yhdistelmä mahdollistaa lajittelun sarakeotsikoiden kautta ja dynaamisen suodattamisen spesifikaatioiden avulla.

```java title="TableView.java"
@Route
public class TableView extends Composite<Div> {
  private SpringDataRepository<Person, Long> repository;
  private Table<Person> table = new Table<>();

  public TableView(PersonRepository personRepository) {
    // Kääri Spring Data -varasto webforJ:lle
    repository = new SpringDataRepository<>(personRepository);
    
    // Yhdistä taulukkoon
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

`setPropertyName()`-metodi on tärkeä lajittelua varten - se kertoo sovittimelle, mitä JPA-ominaisuutta käytetään `ORDER BY`-lausessa, kun lajittelet kyseistä saraketta. Ilman sitä lajittelu ei toimi laskettujen ominaisuuksien, kuten `getFullName()`, kohdalla.

## Suodatus JPA-spesifikaatioiden avulla {#filtering-with-jpa-specifications}

`SpringDataRepository` käyttää JPA-spesifikaatioita dynaamisiin kyselyihin, ja niitä sovelletaan varaston `findBy`- ja `count`-toimintoihin.

:::tip[Lisätietoa suodattamisesta]
Saadaksesi ymmärryksen siitä, miten suodatus toimii webforJ-varastoissa, mukaan lukien perussuodattimet ja suodattimien yhdistäminen, katso [Varasto-dokumentaatio](../../advanced/repository/overview).
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

// Poista suodatus
repository.setFilter(null);
```
