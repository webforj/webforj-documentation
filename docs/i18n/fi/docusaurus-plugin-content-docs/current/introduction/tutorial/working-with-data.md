---
title: Working with Data
sidebar_position: 3
description: Step 2 - Use Spring to work with data.
_i18n_hash: eb93bafc77e98de6453cfb0fa0ea60a3
---
Tässä vaiheessa opit luomaan tietomallin käyttäen Springiä ja esittämään tiedot visuaalisesti. 
Vaiheen loppuun mennessä aiemmassa vaiheessa luodussa sovelluksessa, [Perus sovelluksen luominen](./creating-a-basic-app), on taulukko, joka näyttää tietoja asiakkaita koskien. Seuraamalla mukana opit seuraavat asiat:

- Springin annotaatiot
- Tietojen hallinta
- webforJ:n `Table`-komponentti

Tämän vaiheen loppuunsaattaminen luo version [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data).

## Sovelluksen suorittaminen {#running-the-app}

Sovellustasi kehittäessäsi voit käyttää [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data) vertailukohtana. Näet sovelluksen toiminnassa:

1. Siirry pääkansioon, jossa on `pom.xml`-tiedosto, tämä on `2-working-with-data`, jos seuraat GitHubin versiota.

2. Käytä seuraavaa Maven-komentoa suorittaaksesi Spring Boot -sovelluksen paikallisesti:
    ```bash
    mvn
    ```

Sovelluksen suorittaminen avaa automaattisesti uuden selaimen osoitteeseen `http://localhost:8080`.

## Riippuvuudet ja asetukset {#dependencies-and-configurations}

Tässä oppaassa käytetään [H2-tietokantaa](https://www.h2database.com/html/main.html) ja tulevassa vaiheessa Jakarta Persistence API:ta (JPA) [Spring Data JPA:n](https://docs.spring.io/spring-data/jpa/reference/index.html) kautta. Tämä vaatii, että lisäät riippuvuudet `pom.xml`:ään ja päivität `application.properties`:in. Tämä on viimeinen kerta, kun sinun tarvitsee muokata näitä kahta tiedostoa koko oppaan aikana.

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

Lisää `application.properties`:ssa, sijainnissa `src/main/resources`, seuraavat asetukset:

```
# H2-tietokannan kokoonpano
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA-asetukset
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
```

:::info Tietojen pääsy
Tässä oppaassa käytetään muistin sisäistä tietokantaa ja oletustunnistetietoja tietojen käsittelyyn. Siirry Springin [Data Access](https://docs.spring.io/spring-boot/how-to/data-access.html) -dokumentaatioon oppiaksesi erityisiä Spring Boot -kokoonpanoasetuksia.
:::

## Spring-pavut {#spring-beans}

Keskeinen osa Spring-kehyksen käyttöä on ymmärtää, mitä pavut ovat. Pavut ovat olioita, joilla on määritellyt Spring-annotaatiot, jotka helpottavat Springin konfigurointia tietäen luokan tarkoitetun tarkoituksen. Siirry Springin [Bean Overview](https://docs.spring.io/spring-framework/reference/core/beans/definition.html) -dokumentaatioon oppiaksesi lisää.

## Tietomallin luominen {#creating-a-data-model}

Ennen kuin voit esittää tai luoda tietoja visuaalisesti, tässä oppaassa tarvitaan tapa edustaa jokaisen asiakkaan tietoja, mukaan lukien heidän nimensä, maansa ja yrityksensä. Springiä käyttäen tämä tehdään luomalla luokka, jossa on `@Entity`-annotaatio.

Luo luokka `src/main/java/com/webforj/tutorial/entity`-kansioon nimeltä `Customer.java`. Sen tulee sisältää `@Entity`-annotaatio ja getter- sekä setter-metodit asiakastietojen varalle, lukuun ottamatta `id`:tä. Sen sijaan, että käyttäisit luontimenetelmää `id`-arvoille, käytä `@Id`- ja `@GeneratedValue`-annotaatioita taataaksesi, että jokaiselle asiakkaalle annetaan ainutlaatuinen `id`.

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

Kun `Customer`-tietomalli on luotu, voit nyt alkaa lisätä liiketoimintalogiikkaa sovellukseesi.

## Tietojen hallinta {#managing-data}

Tietomallin luomisen jälkeen luot repositorion ja palvelun asiakastietojen hallitsemiseksi. Tämän tyyppisten luokkien tekeminen sovellukseesi antaa sinun sisällyttää toimintoja, kuten asiakastietojen lisääminen, poistaminen ja päivittäminen.

### Repositorion luominen {#creating-a-repository}

Repositorioli luominen tekee entiteettiensä tiedoista saatavilla olevia, joten sovelluksesi voi sisältää useita asiakkaita. Tämän oppaan tavoite on tehdä datasta muokattavaa, lajiteltavaa ja validoitavaa. Määrität repositorion kyvyt käyttämällä Spring Data -repositorioita.

Tulevassa vaiheessa, [Tietojen validoiminen ja sitominen](/docs/introduction/tutorial/validating-and-binding-data), tarvitset pääsyn Spring Data JPA:han voidaksesi validoida asiakasominaisuudet. Siksi sopiva repositorio on `JpaRepository`.

Luo `src/main/java/com/webforj/tutorial/repository`-kansioon repositorio-liittyvä rajapinta, joka sisältää Springin `@Repository`-annotaation ja laajentaa `JpaRepository`:ta. Sinun on määritettävä, minkä tyyppisiä entiteettejä tämä repositorio sisältää ja minkä tyyppinen objekti `id` on. Varotoimenpiteenä laajenna myös `JpaSpecificationExecutor`:a. Tämä lisäys mahdollistaa kehittyneiden suodatusvaihtoehtojen toteuttamisen myöhemmin, jos tarpeen.

```java title="CustomerRepository.java"
@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Long>,
        JpaSpecificationExecutor<Customer> {
}
```

Luomasi `CustomerRepository` ei vielä sisällä julkisia metodeja. Tiedonhallintaan liittyvät metodit (sovelluksen liiketoimintalogiikka) sijaitsevat palveluluokassa.

:::info Spring-dokumentaatio linkkejä

Tässä on neljä linkkiä Springin dokumentaatioon, jotka auttavat sinua ymmärtämään Spring-repositorioita paremmin:

- [Työskentely Spring Data -repositorioiden kanssa](https://docs.spring.io/spring-data/commons/reference/repositories.html)
- [Spring Data JPA -näkymä](https://docs.spring.io/spring-data/jpa/reference/index.html)
- [Spring Data JPA -specifikaatiot](https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html)
- [`JpaRepository`](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html)
:::

### Palvelun luominen {#creating-a-service}

Luo `src/main/java/com/webforj/tutorial/service`-kansioon `CustomerService`-luokka. Tämä palvelu sisältää metodit asiakkaiden luomiseen, päivittämiseen, poistamiseen ja kyselyihin käyttäen `CustomerRepository`:a. 

Lisäksi tällä palvelulla on tarvittava mekanismi yhdistää Spring Data -repositoriot webforJ:n UI-komponentteihin. Käyttämällä `SpringDataRepository`-webforJ-luokkaa voit luoda tämän sillan. Se yksinkertaistaa datan sidontaa ja CRUD-toimintoja, jolloin webforJ-taulukot ja lomakkeet voivat toimia vapaasti Spring-hallinnoidun datakerroksesi kanssa. Lisätietoja webforJ:n Spring-integraatiosta löytyy artikkelista [Spring Data JPA](/docs/integrations/spring/spring-data-jpa).

Tässä palveluluokassa käytät kahta Spring-annotaatiota:

- **`@Service`** - Tämä merkitsee luokkaa palvelukomponenttina Springissä, jolloin se havaitaan ja hallitaan automaattisesti beanina liiketoimintalogiikalla tai uudelleenkäytettävillä toiminnoilla.

- **`@Transactional`** - Tämä annotaatio kertoo Springille suorittaa metodi tai luokka tietokantatransaktiossa, joten kaikki siihen sisältyvät toiminnot sitoutuvat tai peruutetaan yhdessä. Lisää tietoja on saatavilla Springin dokumentaatiossa, [Käyttäen @Transactional](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html#page-title).

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
      throw new IllegalArgumentException("Asiakasta ei löytynyt ID:llä: " + customer.getId());
    }
    return repository.save(customer);
  }

  public void deleteCustomer(Long id) {
    if (!repository.existsById(id)) {
      throw new IllegalArgumentException("Asiakasta ei löytynyt ID:llä: " + id);
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
        .orElseThrow(() -> new IllegalArgumentException("Asiakasta ei löytynyt ID:llä: " + id));
  }

  public boolean doesCustomerExist(Long id) {
    return repository.existsById(id);
  }

}
```

## Alkuperäisten tietojen lataaminen {#loading-initial-data}

Tässä oppaassa alkuperäinen asiakastietojoukko tulee JSON-tiedostosta. Suoran selaimen pääsyn estämiseksi tiedostoresurssi tulisi luoda `src/main/resources/static`-kansion ulkopuolelle. Kätevästi voit luoda JSON-tiedoston `src/main/resources/data`-kansioon käyttäen seuraavia tietoja:

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

Sen jälkeen sovelluksen pitää pystyä hakemaan nämä tiedot käynnistyessään. Luo `src/main/java/com/webforj/tutorial/config`-kansioon `DataInitializer`-luokka. Nyt, kun sovellus käynnistyy, jos asiakkaita ei havaita, se lataa asiakkaita JSON-tiedostosta ja lisää ne H2-tietokantaan:

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

## Tietojen esittäminen visuaalisesti {#displaying-data-visually}

Tämän vaiheen viimeinen osa on käyttää [`Table`](/docs/components/table/overview) -komponenttia ja liittää se Spring-tietoihin.

webforJ `Table` -instanssin on oltava tietotyyppi toimiakseen, tämä on aikaisemmin luotu entiteetti-luokka:

```java
Table<Customer> table = new Table<>();
```

Kun sinulla on `Table`, jokaisen asiakkaan ominaisuus saa oman sarakkeen. Jokaiselle sarakkeelle, jonka lisäät, käytä ominaisuuden nimeä, sen getter-metodia `Customer`-entiteetissä ja `setLabel()`-metodia tietojen näyttämiseksi haluamassasi järjestyksessä:

```java
table.addColumn("firstName", Customer::getFirstName).setLabel("Etunimi");
table.addColumn("lastName", Customer::getLastName).setLabel("Sukunimi");
table.addColumn("company", Customer::getCompany).setLabel("Yritys");
table.addColumn("country", Customer::getCountry).setLabel("Maa");
```

Sarakkeiden lisäämisen jälkeen sinun on määritettävä, mitä repositoriota `Table` käyttää sen tietojen täyttämiseen. Tämä sovellus saa repositorion `getRepositoryAdapter()`-metodista luodusta `CustomerService`:stä:

```java
table.setRepository(customerService.getRepositoryAdapter());
```

### Taulukon kokoaminen {#table-sizing}

Taulukolle voit käyttää `setSize()`-metodia määrittääksesi sen koon pikseleinä tai muina [CSS-yksikköinä](https://developer.mozilla.org/en-US/docs/Learn_web_development/Core/Styling_basics/Values_and_units). Asettamalla suurimman leveyden suhteessa näytön leveyteen autat sovellustasi sopeutumaan pienemmille näytöille.

Sarakkeille voit määrittää leveydet erikseen tai käyttää yhtä `Table`-metodista, kuten `setColumnsToAutoFit()`, jolloin webforJ hoitaa leveydet puolestasi:

```java
table.setSize("1000px", "294px");
table.setMaxWidth("90vw");
table.setColumnsToAutoFit();
```

### Käyttäjävuorovaikutukset {#user-interactions}

`Table`-komponentilla on myös menetelmiä, joilla voit hallita, miten käyttäjät vuorovaikuttavat sarakkeiden kanssa:

```java
table.setColumnsToResizable(false);
table.getColumns().forEach(column -> column.setSortable(true));
```

Kohokohdat `Application`-luokasta lisäävät `Table`-komponentin, määrittävät sen sarakkeet ja käyttävät `CustomerService`:a saadakseen repositorion:

```java title="Application.java" {7-12,24-25,30-40,46-47}
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")
@AppProfile(name = "Asiakassovellus", shortName = "CustomerApp")
public class Application extends App {
  
  // Lisää konstruktori-injektio CustomerService:lle
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
    Paragraph tutorial = new Paragraph("Opastus App!");
    Button btn = new Button("Tietoa");

    // Lisää Table-komponentti
    Table<Customer> table = new Table<>();

    mainFrame.setWidth("fit-content");
    mainFrame.addClassName("card");

    // Muotoile Table-komponentti, määritä sarakkeet ja määritä repositorio
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
        .addClickListener(e -> OptionDialog.showMessageDialog("Tämä on opastus!", "Tietoa"));

    // Lisää Table pääkehykseen
    mainFrame.add(tutorial, btn, table);
  }

}
```

## Seuraava vaihe {#next-step}

Näiden muutosten myötä sovellus lataa asiakastiedot tietokantaan ja näyttää sen `Table`-komponentissa. Seuraava vaihe, [Reititys ja komposiitit](/docs/introduction/tutorial/routing-and-composites), esittelee reitityksen ja useat näkymät uusien asiakkaiden lisäämiseksi.
