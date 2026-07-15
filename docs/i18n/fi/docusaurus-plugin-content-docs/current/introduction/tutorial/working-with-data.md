---
title: Working with Data
sidebar_position: 3
description: Step 2 - Use Spring to work with data.
_i18n_hash: 99491f42d1a848f6270686a0730d9d08
---
Tässä vaiheessa opit, kuinka luodaan tietomalli käyttämällä Springiä ja näytetään dataa visuaalisesti. Tämän vaiheen lopussa aiemmin luodussa sovelluksessa, [Basic Appin luominen](./creating-a-basic-app), on taulukko, joka esittää tietoja asiakkaista. Seuraamalla mukana opit:

- Spring-anotaatiot
- Datan hallinta
- webforJ `Table` -komponentti

Tämän vaiheen suorittaminen luo version [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data).

## Sovelluksen ajaminen {#running-the-app}

Kun kehität sovellustasi, voit käyttää [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data) vertailukohtana. Näet sovelluksen toiminnassa:

1. Siirry korkeimman tason hakemistoon, jossa `pom.xml` -tiedosto on, tämä on `2-working-with-data`, jos seuraat GitHubin versiota.

2. Käytä seuraavaa Maven-komentoa ajaaksesi Spring Boot -sovellusta paikallisesti:
    ```bash
    mvn
    ```

Sovelluksen ajaminen avaa automaattisesti uuden selaimen osoitteeseen `http://localhost:8080`.

## Riippuvuudet ja konfiguraatiot {#dependencies-and-configurations}

Tässä oppaassa käytetään [H2-tietokantaa](https://www.h2database.com/html/main.html) ja tulevassa vaiheessa Jakarta Persistence API:ta (JPA) [Spring Data JPA:n](https://docs.spring.io/spring-data/jpa/reference/index.html) kautta. Tämä vaatii, että lisäät riippuvuuksia `pom.xml` -tiedostoon ja päivität `application.properties` -tiedoston. Tämä on viimeinen kerta, kun sinun tarvitsee muokata näitä kahta tiedostoa koko oppaan ajan.

Lisää POM-tiedostoon seuraavat riippuvuudet:

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
  <groupId>com.h2database</groupId>
  <artifactId>h2</artifactId>
</dependency>
```

Lisää `application.properties` -tiedostoon, joka sijaitsee `src/main/resources`-hakemistossa, seuraavat:

```
# H2-tietokannan konfigurointi
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA-konfigurointi
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
```

:::info Datan käyttö
Tässä oppaassa käytetään muistiin perustuvaa tietokantaa ja oletustunnuksia tiedon käyttöön. Siirry Springin [Data Access](https://docs.spring.io/spring-boot/how-to/data-access.html) -dokumentaatioon oppiaksesi erityisiä Spring Boot -konfigurointivaihtoehtoja.
:::

## Spring-beanit {#spring-beans}

Yksi tärkeä osa Spring-kehyksen käyttöä on ymmärtää, mitä beanit ovat. Beanit ovat objekteja, joilla on määritellyt Spring-anotaatiot, mikä helpottaa Springin konfigurointia, koska se tietää luokan tarkoitetun tarkoituksen. Siirry Springin [Bean Overview](https://docs.spring.io/spring-framework/reference/core/beans/definition.html) -dokumentaatioon oppiaksesi lisää.

## Tietomallin luominen {#creating-a-data-model}

Ennen kuin näytetään visuaalisesti tai luodaan dataa, tässä oppaassa tarvitaan tapa edustaa kunkin asiakkaan tietoja, mukaan lukien heidän nimi, maa ja yritys. Springiä käyttäen tämä tapahtuu luokan avulla, jolla on `@Entity`-anotaatio.

Luo luokka `src/main/java/com/webforj/tutorial/entity`-hakemistoon nimeltä `Customer.java`. Siinä tulee olla `@Entity`-anotaatio ja sisältää getter- ja setter-metodit asiakastiedoille, lukuun ottamatta `id`:tä. Käytä `id`-arvojen luomiseen `@Id` ja `@GeneratedValue` -anotaatiota varmistaaksesi, että jokaisella asiakkaalla on ainutlaatuinen `id`.

```java
@Entity
  @Table(name = "customers")
  public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName = "";
    private String lastName = "";
    private String company = "";
    private Country country = Country.UNKNOWN;

    public enum Country {
      UNKNOWN,
      GERMANY,
      ENGLAND,
      ITALY,
      USA
    }

    public Customer(String firstName, String lastName, String company, Country country) {
      setFirstName(firstName);
      setLastName(lastName);
      setCompany(company);
      setCountry(country);
    }

    public Customer(String firstName, String lastName, String company) {
      this(firstName, lastName, company, Country.UNKNOWN);
    }

    public Customer(String firstName, String lastName) {
      this(firstName, lastName, "");
    }

    public Customer(String firstName) {
      this(firstName, "");
    }

    public Customer() {
    }

    public void setFirstName(String newName) {
      firstName = newName;
    }

    public String getFirstName() {
      return firstName;
    }

    public void setLastName(String newName) {
      lastName = newName;
    }

    public String getLastName() {
      return lastName;
    }

    public void setCompany(String newCompany) {
      company = newCompany;
    }

    public String getCompany() {
      return company;
    }

    public void setCountry(Country newCountry) {
      country = newCountry;
    }

    public Country getCountry() {
      return country;
    }

    public Long getId() {
      return id;
    }

  }
```

Kun `Customer`-tietomalli on paikoillaan, voit nyt alkaa lisätä liiketoimintalogiikkaa sovellukseesi.

## Datan hallinta {#managing-data}

Kun olet luonut tietomallin, luot repositorion ja palvelun asiakastietojen hallintaan. Tällaiset luokat sovelluksessasi mahdollistavat toimintojen, kuten asiakasrekisterien lisäämisen, poistamisen ja päivittämisen.

### Repositorion luominen {#creating-a-repository}

Repositorion luominen tekee entiteettien datasta saavutettavaa, joten sovelluksesi voi sisältää useita asiakkaita. Tämän oppaan tavoitteena on tehdä datasta muokattavaa, lajittelemista ja validoitavissa olevaa. Määrität repositorion kyvyt käyttämällä Spring Data -repositoriota.

Tulevassa vaiheessa, [Datan validointi ja sitominen](/docs/introduction/tutorial/validating-and-binding-data), tarvitset pääsyn Spring Data JPA:han validoidaksesi asiakashenkilöitä. Siksi sopiva repositorio käytettäväksi on `JpaRepository`.

Luo `src/main/java/com/webforj/tutorial/repository`-hakemistoon repositorio-rajapinta, jolla on Springin `@Repository`-anotaatio ja joka laajentaa `JpaRepository`. Sinun on määritettävä, minkä tyyppisiä entiteettejä tämä repositorio sisältää ja minkä tyyppinen objekti `id` on. Varmuuden vuoksi laajenna myös `JpaSpecificationExecutor`. Tämä lisäys mahdollistaa edistyneiden suodatusvaihtoehtojen toteuttamisen myöhemmin, jos tarpeen.

```java title="CustomerRepository.java"
@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Long>,
        JpaSpecificationExecutor<Customer> {
}
```

Juuri luomassasi `CustomerRepository`-rajapinnassa ei ole määriteltyjä metodeja. Datan hallintaan (sovelluksen liiketoimintalogiikkaan) liittyvät metodit sijaitsevat palveluluokassa.

:::info Springin dokumentaatio-linkit

Tässä on neljä linkkiä Springin dokumentaatioon, jotka auttavat sinua ymmärtämään paremmin Spring-repositorioita:

- [Työskentely Spring Data Repositorioiden kanssa](https://docs.spring.io/spring-data/commons/reference/repositories.html)
- [Spring Data JPA Yhteenveto](https://docs.spring.io/spring-data/jpa/reference/index.html)
- [Spring Data JPA -spesifikaatiot](https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html)
- [`JpaRepository`](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html)
:::

### Palvelun luominen {#creating-a-service}

Luo `src/main/java/com/webforj/tutorial/service`-hakemistoon `CustomerService`-luokka. Tämä palvelu sisältää metodeja asiakastietojen luomiseen, päivittämiseen, poistamiseen ja tietojen kysymiseen `CustomerRepository`-rajapinnan avulla.

Lisäksi tällä palvelulla on mekanismi yhdistää Spring Data -repositoriot webforJ:n UI-komponentteihin. Käyttämällä `SpringDataRepository`-kelasoa webforJ:ssä voit luoda tämän sillan. Se yksinkertaistaa datan sitomista ja CRUD-toimia, jolloin webforJ-taulukot ja -lomakkeet voivat toimia vapaasti Spring-hallinnoidun datakerroksen kanssa. Lisätietoja webforJ:n Spring-integraatiosta löytyy [Spring Data JPA](/docs/integrations/spring/spring-data-jpa) -artikkelista.

Tässä palveluluokassa käytät kahta Spring-anotaatiota:

- **`@Service`** - Tämä merkitsee luokkaa palvelukomponentiksi Springissä, jolloin se havaitaan automaattisesti ja hallitaan beanina liiketoimintalogiikkaa tai käytettäviä operaatioita varten.

- **`@Transactional`** - Tämä anotaatiot kertoo Springille, että metodi tai luokka suoritetaan tietokantatapahtumassa, joten kaikki sisällä olevat toiminnot vahvistetaan tai peruutetaan yhdessä. Lisätietoa on saatavilla Springin dokumentaatiossa, [Käyttämällä @Transactional](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html#page-title).

```java title="CustomerService.java"
@Service
@Transactional
public class CustomerService {
  private final CustomerRepository repository;

  public CustomerService(CustomerRepository repository) {
    this.repository = repository;
  }

  public Customer createCustomer(Customer customer) {
    return repository.save(customer);
  }

  public Customer updateCustomer(Customer customer) {
    if (!repository.existsById(customer.getId())) {
      throw new IllegalArgumentException("Asiakasta ei löydy ID:llä: " + customer.getId());
    }
    return repository.save(customer);
  }

  public void deleteCustomer(Long id) {
    if (!repository.existsById(id)) {
      throw new IllegalArgumentException("Asiakasta ei löydy ID:llä: " + id);
    }
    repository.deleteById(id);
  }

  public long getTotalCustomersCount() {
    return repository.count();
  }

  public SpringDataRepository<Customer, Long> getRepositoryAdapter() {
    return new SpringDataRepository<>(repository);
  }

  public Customer getCustomerByKey(Long id) {
    return repository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Asiakasta ei löydy ID:llä: " + id));
  }

  public boolean doesCustomerExist(Long id) {
    return repository.existsById(id);
  }

}
```

## Alkuperäisten tietojen lataaminen {#loading-initial-data}

Tässä oppaassa alkuperäinen asiakastietojoukko tulee JSON-tiedostosta. Estääkseen suoran pääsyn selaimelta, tiedostoresurssi tulisi luoda `src/main/resources/static` -hakemiston ulkopuolelle. Käteväksi voit luoda JSON-tiedoston `src/main/resources/data`-hakemistoon käyttäen seuraavia tietoja:

```json
[
    {
      "firstName": "Alice",
      "lastName": "Smith",
      "company": "TechCorp",
      "country": "GERMANY"
    },
    {
      "firstName": "John",
      "lastName": "Doe",
      "company": "Innovatech",
      "country": "ITALY"
    },
    {
      "firstName": "Emma",
      "lastName": "Brown",
      "company": "SoftSolutions",
      "country": "ENGLAND"
    },
    {
      "firstName": "Liam",
      "lastName": "Jones",
      "company": "FinWise",
      "country": "UNKNOWN"
    },
    {
      "firstName": "Sophia",
      "lastName": "Taylor",
      "company": "DataWorks",
      "country": "GERMANY"
    },
    {
      "firstName": "Noah",
      "lastName": "Wilson",
      "company": "EcoBuild",
      "country": "ITALY"
    },
    {
      "firstName": "Olivia",
      "lastName": "Moore",
      "company": "NextGen",
      "country": "ENGLAND"
    },
    {
      "firstName": "James",
      "lastName": "Anderson",
      "company": "BlueTech",
      "country": "UNKNOWN"
    },
    {
      "firstName": "Isabella",
      "lastName": "Thomas",
      "company": "FutureLogic",
      "country": "GERMANY"
    },
    {
      "firstName": "Lucas",
      "lastName": "White",
      "company": "GreenEnergy",
      "country": "ITALY"
    }
]
```

Sen jälkeen sovelluksen on saatava tapa noutaa nämä tiedot käynnistyessään. Luo `src/main/java/com/webforj/tutorial/config`-hakemistoon `DataInitializer`-luokka. Nyt, kun sovellus käynnistyy, jos asiakkaita ei havaita, se lataa asiakkaat JSON-tiedostosta ja sijoittaa ne H2-tietokantaan:

```java title="DataInitializer.java"
@Component
public class DataInitializer implements CommandLineRunner {
  private final CustomerService customerService;

  public DataInitializer(CustomerService customerService) {
    this.customerService = customerService;
  }

  @Override
  public void run(String... args) {
    if (customerService.getTotalCustomersCount() == 0) {
      loadCustomersFromJson();
    }
  }

  private void loadCustomersFromJson() {
    ObjectMapper mapper = new ObjectMapper();
    try (InputStream is = getClass().getResourceAsStream("/data/customers.json")) {
      List<Customer> customers = mapper.readValue(is, new TypeReference<List<Customer>>() {
      });
      for (Customer customer : customers) {
        customerService.createCustomer(customer);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
```

## Datan esittäminen visuaalisesti {#displaying-data-visually}

Tämän vaiheen viimeinen osa on käyttää [`Table`](/docs/components/table/overview) komponenttia ja yhdistää se Spring-datan kanssa.

webforJ `Table` instanssin on oltava tietotyyppi toimiakseen, se on aiemmin tässä vaiheessa luotu entiteettiluokka:

```java
Table<Customer> table = new Table<>();
```

Kun sinulla on `Table`, jokaiselle asiakastiedolle saat oma sarakkeensa. Lisäämällä jokaisen sarakkeen kohdalle käytä ominaisuuden nimeä, sen getter-metodia `Customer`-entiteetissä ja `setLabel()`-metodia tietojen näyttämiseksi haluamassasi järjestyksessä:

```java
table.addColumn("firstName", Customer::getFirstName).setLabel("Etunimi");
table.addColumn("lastName", Customer::getLastName).setLabel("Sukunimi");
table.addColumn("company", Customer::getCompany).setLabel("Yritys");
table.addColumn("country", Customer::getCountry).setLabel("Maa");
```

Sarakkeiden lisäämisen jälkeen sinun on määriteltävä, mitä repositoriota `Table` käyttää datan täyttämiseen. Tämä sovellus saa repositorion `getRepositoryAdapter()`-metodista luodussa `CustomerService`:ssä:

```java
table.setRepository(customerService.getRepositoryAdapter());
```

### Taulukon kokoaminen {#table-sizing}

Taulukolle voit käyttää `setSize()` -metodia asettaaksesi sen koon pikseleinä tai muina [CSS-yksikköinä](https://developer.mozilla.org/en-US/docs/Learn_web_development/Core/Styling_basics/Values_and_units). Asettamalla maksimileveyden suhteessa näytön leveyteen autat sovellustasi mukautumaan pienempiin näyttöihin.

Sarakkeille voit asettaa leveydet yksilöllisesti tai käyttää jotain `Table`-menetelmistä kuten `setColumnsToAutoFit()` antaaksesi webforJ:lle hoitaa leveydet puolestasi:

```java
table.setSize("1000px", "294px");
table.setMaxWidth("90vw");
table.setColumnsToAutoFit();
```

### Käyttäjäinteraktiot {#user-interactions}

`Table`-komponentilla on myös menetelmiä käyttäjien vuorovaikutuksen hallintaan sarakkeiden kanssa:

```java
table.setColumnsToResizable(false);
table.getColumns().forEach(column -> column.setSortable(true));
```

Korostetut osat `Application`-luokassa lisäävät `Table`-komponentin, määrittelevät sen sarakkeet ja käyttävät `CustomerService`:ä saadakseen repositorion:

```java title="Application.java" {7-12,24-25,30-40,46-47}
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")
@AppProfile(name = "Asiakasohjelma", shortName = "AsiakasApp")
public class Application extends App {

  // Lisää konstruktorin injektointi CustomerService
  private final CustomerService customerService;

  public Application(CustomerService customerService) {
    this.customerService = customerService;
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run() throws WebforjException {
    Frame mainFrame = new Frame();
    Paragraph tutorial = new Paragraph("Oppimisohjelma!");
    Button btn = new Button("Tietoja");

    // Lisää Table -komponentti
    Table<Customer> table = new Table<>();

    mainFrame.setWidth("fit-content");
    mainFrame.addClassName("card");

    // Tyylittele Table -komponentti, aseta sarakkeet ja aseta repositorio
    table.setSize("1000px", "294px");
    table.setMaxWidth("90vw");
    table.addColumn("firstName", Customer::getFirstName).setLabel("Etunimi");
    table.addColumn("lastName", Customer::getLastName).setLabel("Sukunimi");
    table.addColumn("company", Customer::getCompany).setLabel("Yritys");
    table.addColumn("country", Customer::getCountry).setLabel("Maa");
    table.setColumnsToAutoFit();
    table.setColumnsToResizable(false);
    table.getColumns().forEach(column -> column.setSortable(true));
    table.setRepository(customerService.getRepositoryAdapter());

    btn.setTheme(ButtonTheme.PRIMARY)
        .setMaxWidth(200)
        .addClickListener(e -> OptionDialog.showMessageDialog("Tämä on oppimisohjelma!", "Tietoja"));

    // Lisää Table kehykseen
    mainFrame.add(tutorial, btn, table);
  }

}
```

## Seuraava vaihe {#next-step}

Näiden muutosten myötä sovellus lataa asiakastiedot tietokantaan ja näyttää ne `Table`-komponentissa. Seuraava vaihe, [Reititys ja yhdistelmät](/docs/introduction/tutorial/routing-and-composites), esittelee reitityksen ja useita näkymiä uusien asiakkaiden lisäämiseksi.
