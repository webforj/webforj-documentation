---
title: Working with Data
sidebar_position: 3
description: Step 2 - Use Spring to work with data.
_i18n_hash: c5bf8e9751b676f3490a2f01512647ca
---
Tässä vaiheessa opit, kuinka luodaan tietomalli Springissä ja näytetään nämä tiedot visuaalisesti.
Tämän vaiheen lopussa aiemmin luodussa sovelluksessa, [Perus sovelluksen luominen](/docs/introduction/tutorial/creating-a-basic-app), on taulukko, joka näyttää tietoja asiakkaista. Seuraamalla mukana opit:

- Spring-anotaatiot
- Datan hallinta
- webforJ `Table`-komponentti

Tämän vaiheen suorittaminen luo version [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data).

## Sovelluksen suorittaminen {#running-the-app}

Sovellustasi kehittäessäsi voit käyttää [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data) vertailukohtana. Näkyäksesi sovelluksen toiminnassa:

1. Siirry yläpään hakemistoon, jossa `pom.xml`-tiedosto sijaitsee; tämä on `2-working-with-data`, jos seuraat GitHubin versiota.

2. Suorita seuraava Maven-komento ajaaksesi Spring Boot -sovelluksen paikallisesti:
    ```bash
    mvn
    ```

Sovelluksen suorittaminen avaa automaattisesti uuden selaimen osoitteessa `http://localhost:8080`.

## Riippuvuudet ja konfiguroinnit {#dependencies-and-configurations}

Tässä oppaassa käytetään [H2-tietokantaa](https://www.h2database.com/html/main.html) ja tulevassa vaiheessa Jakarta Persistence API:ta (JPA) [Spring Data JPA:n](https://docs.spring.io/spring-data/jpa/reference/index.html) kautta. Tämä edellyttää, että lisäät riippuvuuksia `pom.xml`-tiedostoon ja päivität `application.properties`-tiedoston. Tämä on viimeinen kerta, kun sinun tarvitsee muuttaa näitä kahta tiedostoa oppaan loppuosan aikana.

Lisää POM-tiedostoosi seuraavat riippuvuudet:

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

Lisää `application.properties`-tiedostoon, joka sijaitsee `src/main/resources`-hakemistossa seuraavat:

```
# H2 Tietokannan konfigurointi
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA konfigurointi
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
```

:::info Datan käyttämisestä
Tässä oppaassa käytetään muistin tietokantaa ja oletustunnuksia datan käyttämiseksi. Siirry Springin [Data Access](https://docs.spring.io/spring-boot/how-to/data-access.html) -dokumentaatioon oppiaksesi tiettyjä Spring Boot -konfiguraatio-oppaita.
:::

## Spring beans {#spring-beans}

Yksi tärkeä osa Spring-viitekehyksen käyttöä on ymmärtää, mitä beans ovat. Beans ovat objekteja, joilla on määriteltyjä Spring-anotaatioita, jotka helpottavat Springiä konfiguroimasta niitä tunnistamalla luokan aiotun tarkoituksen. Siirry Springin [Bean Overview](https://docs.spring.io/spring-framework/reference/core/beans/definition.html) -dokumentaatioon saadaksesi lisätietoa.

## Tietomallin luominen {#creating-a-data-model}

Ennen kuin näytät tai luot tietoja visuaalisesti, tässä oppaassa tarvitaan tapa edustaa kunkin asiakkaan tietoja, mukaan lukien heidän nimensä, maansa ja yrityksensä. Springin avulla tämä tehdään luomalla luokka, jolla on `@Entity`-anotaatio.

Luo luokka hakemistoon `src/main/java/com/webforj/tutorial/entity`, nimeltään `Customer.java`. Sen tulisi sisältää `@Entity`-anotaatio ja getter- ja setter-menetelmät asiakastietojen arvoille, poislukien `id`. Sen sijaan, että käyttäisit luontimenetelmää `id`-arvoille, käytä `@Id` ja `@GeneratedValue` -anotaatioita varmistaaksesi, että jokaisella asiakkaalla on ainutlaatuinen `id`.


<ExpandableCode title="Customer.java" language="java" startLine={1} endLine={15}>
{`@Entity
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

`}
</ExpandableCode>

Kun `Customer`-tietomalli on luotu, voit nyt alkaa lisätä liiketoimintalogiikkaa sovellukseesi.

## Datan hallinta {#managing-data}

Tietomallin luomisen jälkeen luot repositorion ja palvelun asiakastietojen hallintaan. Tällaiset luokat sovelluksessasi mahdollistavat operaatioiden, kuten asiakastietojen lisäämisen, poistamisen ja päivittämisen.

### Repositorion luominen {#creating-a-repository}

Repositorion luominen tekee entiteettien tiedoista saatavilla olevia, jotta sovelluksesi voi sisältää useita asiakkaita. Tämän oppaan tavoite on tehdä tiedoista muokattavia, lajiteltavia ja validoitavia. Määrität repositorion ominaisuudet käyttämällä Spring Data -repositoriota.

Tulevassa vaiheessa, [Datan validoiminen ja sitominen](/docs/introduction/tutorial/validating-and-binding-data), tarvitset pääsyn Spring Data JPA:han validoidaksesi asiakashallinnan. Siksi asianmukainen repositorio käytettäväksi on `JpaRepository`.

Hakemistossa `src/main/java/com/webforj/tutorial/repository`, luo repositorio liittymä, jolla on Springin `@Repository`-anotaatio ja joka laajentaa `JpaRepository`-rajapintaa. Sinun on määritettävä, minkä tyyppisiä entiteettejä tässä repositoriossa on ja minkä tyyppinen objekti `id` on. Varmuuden vuoksi laajenna myös `JpaSpecificationExecutor`-rajapintaa. Tämä lisäys mahdollistaa edistyksellisten suodatusvaihtoehtojen toteuttamisen myöhemmin, mikäli tarpeen.

```java title="CustomerRepository.java"
@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Long>,
        JpaSpecificationExecutor<Customer> {
}
```

Luomassasi `CustomerRepository`-rajapinnassa ei ole määriteltyjä menetelmiä. Datan hallintaan (sovelluksen liiketoimintalogiikkaan) tarvittavat menetelmät löytyvät palveluluokasta.

:::info Spring-dokumentaation linkkejä

Tässä on neljä linkkiä Springin dokumentaatioon, jotka auttavat sinua ymmärtämään paremmin Spring-repositorion tyyppejä:

- [Työskentely Spring Data Repositorien kanssa](https://docs.spring.io/spring-data/commons/reference/repositories.html)
- [Spring Data JPA:n yleiskatsaus](https://docs.spring.io/spring-data/jpa/reference/index.html)
- [Spring Data JPA -spesifikaatiot](https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html)
- [`JpaRepository`](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html)
:::

### Palvelun luominen {#creating-a-service}

Hakemistossa `src/main/java/com/webforj/tutorial/service`, luo `CustomerService`-luokka. Tämä palvelu sisältää menetelmiä asiakkaiden luomiseen, päivittämiseen, poistamiseen ja kyselyyn käyttäen `CustomerRepository`-rajapintaa.

Lisäksi tämän palvelun on oltava yhteydessä Spring Data -repositorioihin webforJ:n UI-komponenttien kautta. Käyttämällä `SpringDataRepository`-luokkaa webforJ:ssa voit luoda tämän sillan. Se yksinkertaistaa datan sitomista ja CRUD-toimintoja, mikä mahdollistaa webforJ-taulukoiden ja lomakkeiden saumattoman yhteyden Spring-hallittavaan datakerrokseesi. Lisätietoja webforJ:n Spring-integraatiosta on [Spring Data JPA](/docs/integrations/spring/spring-data-jpa) -artikkelissa.

Tässä palveluluokassa käytetään kahta Spring-anotaatiota:

- **`@Service`** - Tämä merkitsee luokan palvelukomponentiksi Springissä, jolloin se havaitaan ja hallitaan automaattisesti beanina liiketoimintalogiikkaa tai uudelleenkäytettävää toimintoa varten.

- **`@Transactional`** - Tämä anotaatiota kertoo Springille suorittaa menetelmän tai luokan tietokantatransaktiossa, jotta kaikki operaatioita sisällä sitoutuvat tai peruutetaan yhdessä. Tarkempaa tietoa löytyy Springin dokumentaatiosta, [Käyttämällä @Transactional](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html#page-title).

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

## Aloitustietojen lataaminen {#loading-initial-data}

Tässä oppaassa aloitusasiakastiedot tulevat JSON-tiedostosta. Java-sovellus lataa tiedoston, ei selain, joten luo se hakemistoon `src/main/resources/data` käyttäen seuraavia tietoja:

<ExpandableCode title="customers.json" language="json" startLine={1} endLine={13}>
{`[
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
`}
</ExpandableCode>

Sen jälkeen sovellukselle on annettava tapa noutaa nämä tiedot, kun se käynnistyy. Luo `src/main/java/com/webforj/tutorial/config`-hakemistoon `DataInitializer`-luokka. Nyt, kun sovellus käynnistyy, jos asiakkaita ei havaita, se lataa asiakkaita JSON-tiedostosta ja tallentaa ne H2-tietokantaan:

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

## Datan näyttäminen visuaalisesti {#displaying-data-visually}

Tämän vaiheen viimeinen osa on käyttää [`Table`](/docs/components/table/overview) komponenttia ja liittää se Spring-dataan.

webforJ `Table`-instanssin on käytettävä tietotyyppiä toimiakseen, ja se on aikaisemmin luotu entiteettiluokka:

```java
Table<Customer> table = new Table<>();
```

Kun sinulla on `Table`, jokaiselle asiakastiedolle on oma sarake. Jokaisen lisäämääsi sarakkeen osalta käytä ominaisuusnimeä, sen getter-menetelmää `Customer`-entiteetissä ja `setLabel()`-menetelmää tiedon näyttämiseksi haluamassasi järjestyksessä:

```java
table.addColumn("firstName", Customer::getFirstName).setLabel("Etunimi");
table.addColumn("lastName", Customer::getLastName).setLabel("Sukunimi");
table.addColumn("company", Customer::getCompany).setLabel("Yritys");
table.addColumn("country", Customer::getCountry).setLabel("Maa");
```

Sarakkeet lisäämisen jälkeen sinun on määritettävä, mikä repositorio `Table`-komponentin tulisi käyttää tietojensa täyttämiseen. Tämä sovellus saa repositorion `getRepositoryAdapter()`-menetelmästä luodusta `CustomerService`-luokasta:

```java
table.setRepository(customerService.getRepositoryAdapter());
```

### Taulukon koko {#table-sizing}

Taulukolle voit käyttää `setSize()`-menetelmää asetaksesi sen koon pikseleinä tai muina [CSS-yksikköinä](https://developer.mozilla.org/en-US/docs/Learn_web_development/Core/Styling_basics/Values_and_units). Asettamalla enimmäisleveyden suhteessa näytön leveyteen autat sovellustasi mukautumaan paremmin pienille näytöille.

Sarakkeille voit asettaa leveyksiä yksittäin tai käyttää yhtä `Table`-menetelmistä, kuten `setColumnsToAutoFit()`, jotta webforJ käsittelee leveyksiä puolestasi:

```java
table.setSize("1000px", "294px");
table.setMaxWidth("90vw");
table.setColumnsToAutoFit();
```

### Käyttäjävuorovaikutus {#user-interactions}

`Table`-komponentissa on myös menetelmiä käyttäjävuorovaikutuksen hallintaan sarakkeiden kanssa:

```java
table.setColumnsToResizable(false);
table.getColumns().forEach(column -> column.setSortable(true));
```

Kohokohdat `Application`-luokasta lisäävät `Table`-komponentin, määrittävät sen sarakkeet ja käyttävät `CustomerService`:ä rekisteröidäkseen repositorion:

```java title="Application.java" {7-12,24-25,30-40,46-47}
@SpringBootApplication
@BundleEntry("css/card.css")
@AppTheme("system")
@AppProfile(name = "Asiakasohjelma", shortName = "CustomerApp")
public class Application extends App {

  // Lisää konstruktorin injektointi CustomerService:lle
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
    Paragraph tutorial = new Paragraph("Opetusohjelma!");
    Button btn = new Button("Tietoa");

    // Lisää Table-komponentti
    Table<Customer> table = new Table<>();

    mainFrame.setWidth("fit-content");
    mainFrame.addClassName("card");

    // Muotoile Table-komponentti, aseta sarakkeet ja aseta repositorio
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
        .addClickListener(e -> OptionDialog.showMessageDialog("Tämä on opetusohjelma!", "Tietoa"));

    // Lisää Table pääikkunaan
    mainFrame.add(tutorial, btn, table);
  }

}
```

## Seuraava vaihe {#next-step}

Näiden muutosten myötä sovellus lataa asiakastiedot tietokantaan ja näyttää ne `Table`-komponentissa. Seuraavassa vaiheessa, [Reititys ja yhdistelmät](/docs/introduction/tutorial/routing-and-composites), esitellään reititys ja useita näkymiä uusien asiakkaiden lisäämiseksi.
