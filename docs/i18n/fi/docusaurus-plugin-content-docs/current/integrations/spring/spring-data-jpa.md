---
title: Spring Data JPA
sidebar_position: 20
_i18n_hash: f97a3247bcdeadf193a049bdb6d1a3bc
---
Spring Data JPA on de facto -standardi datan käsittelyssä Spring-sovelluksissa, tarjoten varastoabstraktioita, kyselymenetelmiä ja spesifikaatioita monimutkaisille kyselyille. webforJ `SpringDataRepository` -adapteri yhdistää Spring Data -varastot webforJ:n UI-komponentteihin, mahdollistaen JPA-olioiden yhdistämisen suoraan UI-komponentteihin, dynaamisen suodattamisen JPA Specificationsin avulla ja sivutuksen hallinnan.

Adapteri tunnistaa, mitkä Spring Data -rajapinnat sinun varastosi toteuttaa - olipa se sitten `CrudRepository`, `PagingAndSortingRepository` tai `JpaSpecificationExecutor` - ja tarjoaa automaattisesti vastaavat ominaisuudet UI:ssasi. Tämä tarkoittaa, että olemassa olevat Spring Data -varastosi toimivat webforJ-komponenttien kanssa ilman muutoksia, säilyttäen samalla tyyppiturvallisuuden ja käyttäen olemassa olevaa domain-malliasi.

:::tip[Opi lisää Spring Data JPA:sta]
Kattavan ymmärryksen saamiseksi Spring Data JPA:n ominaisuuksista ja kyselymenetelmistä, katso [Spring Data JPA -dokumentaatio](https://docs.spring.io/spring-data/jpa/reference/).
:::

## Käyttäminen SpringDataRepository {#using-springdatarepository}

`SpringDataRepository`-luokka yhdistää Spring Data JPA -varastot webforJ:n Repository-rajapintaan, tehden niistä yhteensopivia UI-komponenttien, kuten [`Table`](../../components/table/overview), kanssa samalla säilyttäen kaikki Spring Data -ominaisuudet.

```java
// Oma Spring Data varasto
@Autowired
private PersonRepository personRepository;

// Kääri se SpringDataRepositoryn ympärille
SpringDataRepository<Person, Long> adapter = new SpringDataRepository<>(personRepository);

// Käytä webforJ-taulukon kanssa
Table<Person> table = new Table<>();
table.setRepository(adapter);
```

### Rajapinnan tunnistus {#interface-detection}

Spring Data -varastot käyttävät rajapinnan perimistä lisäominaisuuksien lisäämiseksi. Aloitat perus CRUD-toiminnoista ja lisäät rajapintoja ominaisuuksille, kuten sivutukselle tai spesifikaatioille:

```java
// Vain perus CRUD
public interface CustomerRepository extends CrudRepository<Customer, Long> {}

// CRUD + Sivutus + Lajittelu
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {}

// Täysipainoinen varasto
public interface CustomerRepository extends JpaRepository<Customer, Long>, 
                                           JpaSpecificationExecutor<Customer> {}
```

`SpringDataRepository` tarkistaa, mitkä rajapinnat varastosi toteuttaa ja mukauttaa käyttäytymistään sen mukaan. Jos varastosi tukee sivutusta, adapteri mahdollistaa sivutettujen kyselyjen tekemisen. Jos se toteuttaa `JpaSpecificationExecutor`, voit käyttää dynaamista suodattamista spesifikaatioiden avulla.

### Varaston ominaisuudet {#repository-capabilities}

Jokainen Spring Data -rajapinta lisää erityisiä ominaisuuksia, joita `SpringDataRepository` voi käyttää:

- **CrudRepository** - Perustoiminnot: `save`, `delete`, `findById`, `findAll`
- **PagingAndSortingRepository** - Lisää sivutettujen kyselyjen ja lajittelun
- **JpaRepository** - Yhdistää CRUD- ja sivutus/lajittelu -toiminnot pakettitoimintojen kanssa
- **JpaSpecificationExecutor** - Dynaamiset kyselyt käyttäen JPA Specifications

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

- Löydä ID:n mukaan -toiminnot
- Sivutus optimaalisen suorituskyvyn kanssa
- Lajitteluominaisuudet
- Java Persistence API -spesifikaation suodatus
- Laskentatoiminnot suodattimien kanssa ja ilman

## Työskentely `Table`-komponentin kanssa {#working-with-table}

Seuraava esimerkki käyttää `PersonRepository`-varastoa, joka laajentaa `JpaRepository` ja `JpaSpecificationExecutor`. Tämä yhdistelmä mahdollistaa lajittelun sarakeotsikoiden kautta ja dynaamisen suodattamisen spesifikaatioiden avulla.

```java title="TableView.java"
@Route
public class TableView extends Composite<Div> {
  private SpringDataRepository<Person, Long> repository;
  private Table<Person> table = new Table<>();

  public TableView(PersonRepository personRepository) {
    // Kääri Spring Data varasto webforJ:n ympärille
    repository = new SpringDataRepository<>(personRepository);
    
    // Liitä taulukoihin
    table.setRepository(repository);
    
    // Määritä sarakkeet
    table.addColumn("name", Person::getFullName)
          .setPropertyName("firstName"); // Lajittele oikean JPA ominaisuuden mukaan
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

`setPropertyName()` -metodi on tärkeä lajittelua varten - se kertoo adapterille, mikä JPA-ominaisuus käytetään `ORDER BY` -lauseessa, kun lajitellaan kyseisen sarakkeen perusteella. Ilman sitä lajittelu ei toimi laskennallisille ominaisuuksille, kuten `getFullName()`.

## Suodattaminen JPA spesifikaatioilla {#filtering-with-jpa-specifications}

`SpringDataRepository` käyttää JPA spesifikaatioita dynaamisille kyselyille, ja niitä sovelletaan varaston `findBy`- ja `count`-toimintoihin.

:::tip[Opi lisää suodattamisesta]
Ymmärtääksesi, kuinka suodattaminen toimii webforJ-varastoilla, mukaan lukien perussuodattimet ja suodattamisen yhdistäminen, katso [Varastodokumentaatio](../../advanced/repository/overview).
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
