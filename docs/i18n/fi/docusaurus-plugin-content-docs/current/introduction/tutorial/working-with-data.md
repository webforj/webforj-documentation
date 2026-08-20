---
title: Working with Data
sidebar_position: 3
description: Step 2 - Use Spring to work with data.
_i18n_hash: d2e2ee90a1ad908c6884df92fb575c5b
---
Tässä vaiheessa opit luomaan tietomallin käyttäen Springiä ja näyttämään tiedot visuaalisesti. Tämän vaiheen loppuun mennessä aiemmin luotu sovellus, [Perus sovelluksen luominen](/docs/introduction/tutorial/creating-a-basic-app), sisältää taulukon, joka näyttää asiakasdataa. Seuraamalla mukana opit:

- Spring-annotaatioita
- Datan hallintaa
- webforJ `Table` -komponenttia

Tämän vaiheen suorittaminen luo version [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data).

## Sovelluksen käynnistäminen {#running-the-app}

Sovellusta kehitettäessä voit käyttää [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data) vertailupohjana. Näet sovelluksen toiminnassa:

1. Siirry ylimmälle tasolle hakemistoon, jossa `pom.xml`-tiedosto sijaitsee, tämä on `2-working-with-data`, jos seuraat GitHubin versiota.

2. Suorita seuraava Maven-komento Spring Boot -sovelluksen ajamiseksi paikallisesti:
    ```bash
    mvn
    ```

Sovelluksen käynnistäminen avaa automaattisesti uuden selaimen osoitteeseen `http://localhost:8080`.

## Riippuvuudet ja konfiguraatiot {#dependencies-and-configurations}

Tämä opas käyttää [H2-tietokantaa](https://www.h2database.com/html/main.html) ja tulevassa vaiheessa Jakarta Persistence API:ta (JPA) [Spring Data JPA:n](https://docs.spring.io/spring-data/jpa/reference/index.html) kautta. Tämä vaatii, että lisäät riippuvuuksia `pom.xml`-tiedostoon ja päivität `application.properties`. Tämä on viimeinen kerta, kun sinun tarvitsee muokata näitä kahta tiedostoa koko oppaan aikana.

Lisää pom-tiedostoon seuraavat riippuvuudet:

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

Lisää `application.properties`:iin, joka sijaitsee `src/main/resources`-hakemistossa, seuraavat:

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

:::info Datan hankkiminen
Tässä oppaassa käytetään muistissa olevaa tietokantaa ja oletustunnistetietoja datan hankkimiseen. Käy Springin [Datan käyttö](https://docs.spring.io/spring-boot/how-to/data-access.html) -dokumentaatiossa oppiaksesi tietyistä Spring Boot -konfigurointivaihtoehdoista.
:::

## Springin beanit {#spring-beans}

Yksi tärkeä osa Spring-kehyksen käyttöä on ymmärtää, mitä beanit ovat. Beanit ovat objekteja, joilla on määriteltyjä Spring-annotaatioita, jolloin Spring voi helpommin konfiguroida niitä tietäen luokan tarkoitetun tarkoituksen. Käy Springin [Beanin yleiskuvaus](https://docs.spring.io/spring-framework/reference/core/beans/definition.html) -dokumentaatiossa saadaksesi lisätietoja.

## Tietomallin luominen {#creating-a-data-model}

Ennen kuin tiedot voidaan esittää visuaalisesti tai luoda ne, tässä oppaassa tarvitaan tapa edustaa jokaisen asiakkaan tietoja, mukaan lukien nimi, maa ja yritys. Springin avulla tämä tapahtuu luomalla luokka, joka sisältää `@Entity`-annotaation.

Luo luokka `src/main/java/com/webforj/tutorial/entity`-hakemistoon nimeltä `Customer.java`. Sen tulisi sisältää `@Entity`-annotaatio ja getter- ja setter-metodit asiakasarvoille, paitsi `id`:lle. `id`-arvojen luomismetodin sijasta käytä `@Id`- ja `@GeneratedValue`-annotaatioita varmistaaksesi, että jokaiselle asiakkaalle annetaan ainutlaatuinen `id`.

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

Tietomallin luomisen jälkeen luot repositorion ja palvelun asiakasdatan hallitsemiseksi. Tällaiset luokat sovelluksessasi mahdollistavat toimintoja, kuten asiakkaiden tietojen lisäämistä, poistamista ja päivittämistä.

### Repositorion luominen {#creating-a-repository}

Repositorion luominen tekee entiteettien datasta saatavilla olevaa, jotta sovelluksesi voi sisältää useita asiakkaita. Tämän oppaan tavoitteena on tehdä datasta muokattava, lajitteltava ja validoitava. Määrität repositorion kyvyt käyttämällä Spring Data -repositorya.

Tulevassa vaiheessa [Datan validointi ja sitominen](/docs/introduction/tutorial/validating-and-binding-data) tarvitset pääsyn Spring Data JPA:han asiakkaiden ominaisuuksien validoimiseksi. Siksi sopiva repository käyttää on `JpaRepository`.

Luo `src/main/java/com/webforj/tutorial/repository`-hakemistoon repositoryliittymä, jossa on Springin `@Repository`-annotaatio ja joka laajentaa `JpaRepository`:a. Sinun täytyy määrittää, minkä tyyppisiä entiteettejä tämä repository sisältää ja minkä tyyppinen objekti `id` on. Hyvänä käytäntönä laajenna myös `JpaSpecificationExecutor`:ia. Tämä lisäys mahdollistaa kehittyneiden suodatusvaihtoehtojen toteuttamisen myöhemmin, jos tarpeen.

```java title="CustomerRepository.java"
@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Long>,
        JpaSpecificationExecutor<Customer> {
}
```

Juuri luomasi `CustomerRepository` ei sisällä määriteltyjä metodeja. Datan hallintaan liittyvät (sovelluksen liiketoimintalogiikka) metodit asuvat palveluluokassa.

:::info Spring-dokumentaatio-linkit

Tässä on neljä linkkiä Springin dokumentaatioon, jotka auttavat sinua ymmärtämään paremmin Spring-repositoryt:

- [Työskentely Spring Data Repositories -ohjeiden](https://docs.spring.io/spring-data/commons/reference/repositories.html)
- [Spring Data JPA -yleiskatsaus](https://docs.spring.io/spring-data/jpa/reference/index.html)
- [Spring Data JPA -erityisominaisuudet](https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html)
- [`JpaRepository`](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html)
:::

### Palvelun luominen {#creating-a-service}

Luo `src/main/java/com/webforj/tutorial/service`-hakemistoon `CustomerService`-luokka. Tämä palvelu sisältää metodeja asiakkaiden luomiseen, päivittämiseen, poistamiseen ja kyselyyn käyttämällä `CustomerRepository`:a.

Lisäksi tällä palvelulla on mekanismi yhdistää Spring Data repositories webforJ:n käyttöliittymäkomponentteihin. Käyttämällä `SpringDataRepository` webforJ-luokkaa voit luoda tämän sillan. Se yksinkertaistaa datan sitomista ja CRUD-toimintoja, jolloin webforJ-taulut ja -lomakkeet voivat toimia vapaasti Spring-hallittavan datakerroksesi kanssa. Lisätietoja webforJ:n Spring-integraatiosta löydät [Spring Data JPA](/docs/integrations/spring/spring-data-jpa) -artikkelista.

Tätä palveluluokkaa varten käytät kahta Spring-annotaatiota:

- **`@Service`** - Tämä merkitsee luokan palvelukomponentiksi Springissä, jolloin se havaitaan ja hallitaan automaattisesti beanina liiketoimintalogiikalle tai uudelleenkäytettäville toiminnoille.

- **`@Transactional`** - Tämä annotaatio kertoo Springille, että metodi tai luokka suoritetaan tietokantatransaktiossa, jolloin kaikki sisällä olevat toiminnot vahvistetaan tai peruutetaan yhdessä. Lisätietoja on Springin dokumentaatiossa, [Käyttämällä @Transactional](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html#page-title).

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

## Alustavan datan lataaminen {#loading-initial-data}

Tässä oppaassa alustava asiakasdatan kokoelma tulee JSON-tiedostosta. Java-sovellus lataa tiedoston, ei selain, joten luo se `src/main/resources/data`-hakemistoon seuraavilla tiedoilla:

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

Sitten sovellukselle tarvitaan tapa hakea tämä data käynnistyessään. Luo `src/main/java/com/webforj/tutorial/config`-hakemistoon `DataInitializer`-luokka. Nyt, kun sovellus toimii, jos asiakkaita ei havaita, se lataa asiakkaita JSON-tiedostosta ja lisää ne H2-tietokantaan:

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

Tämän vaiheen viimeinen osa on käyttää [`Table`](/docs/components/table/overview) komponenttia ja yhdistää se Spring-dataan.

webforJ `Table`-instanssin on oltava tietotyyppi toimiakseen, ja se on aiemmin tässä vaiheessa luotu entiteettiluokka:

```java
Table<Customer> table = new Table<>();
```

Kun sinulla on `Table`, jokaisen asiakkaan ominaisuuden tulee saada oma sarake. Jokaiselle lisäämällesi sarakkeelle käytä ominaisuuden nimeä, sen getter-metodia `Customer`-entiteetissä ja `setLabel()`-metodia tietojen näyttämiseen haluamassasi järjestyksessä:

```java
table.addColumn("firstName", Customer::getFirstName).setLabel("Etunimi");
table.addColumn("lastName", Customer::getLastName).setLabel("Sukunimi");
table.addColumn("company", Customer::getCompany).setLabel("Yritys");
table.addColumn("country", Customer::getCountry).setLabel("Maa");
```

Sarakkeiden lisäämisen jälkeen sinun on määritettävä, mikä repository `Table`n tulisi käyttää datansa täyttämiseen. Tämä sovellus saa repositoryn `getRepositoryAdapter()`-metodista luodusta `CustomerService`:stä:

```java
table.setRepository(customerService.getRepositoryAdapter());
```

### Taulukon koko {#table-sizing}

Taulukolle voit käyttää `setSize()`-metodia asettaaksesi sen koon pikseleinä tai muina [CSS-yksikköinä](https://developer.mozilla.org/en-US/docs/Learn_web_development/Core/Styling_basics/Values_and_units). Asettamalla maksimi-leveyden suhteessa näytön leveyteen autat sovellustasi olemaan mukautuvampi pienille näytöille.

Sarakkeille voit asettaa leveydet yksilöllisesti tai käyttää yhtä `Table`-metodia, kuten `setColumnsToAutoFit()`, jolloin webforJ hoitaa leveydet puolestasi:

```java
table.setSize("1000px", "294px");
table.setMaxWidth("90vw");
table.setColumnsToAutoFit();
```

### Käyttäjäinteraktiot {#user-interactions}

`Table`-komponentilla on myös metodeja, joilla hallita, miten käyttäjät voivat vuorovaikuttaa sarakkeiden kanssa:

```java
table.setColumnsToResizable(false);
table.getColumns().forEach(column -> column.setSortable(true));
```

Korostetut osat `Application`-luokasta lisäävät `Table`-komponentin, määrittelevät sen sarakkeet ja käyttävät `CustomerService`-luokkaa repositoryn noutamiseen:

```java title="Application.java" {7-12,24-25,30-40,46-47}
@SpringBootApplication
@BundleEntry("css/card.css")
@AppTheme("system")
@AppProfile(name = "Asiakassovellus", shortName = "CustomerApp")
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
    Paragraph tutorial = new Paragraph("Opastusohjelma!");
    Button btn = new Button("Info");

    // Lisää Table-komponentti
    Table<Customer> table = new Table<>();

    mainFrame.setWidth("fit-content");
    mainFrame.addClassName("card");

    // Muotoile Table-komponentti, aseta sarakkeet ja aseta repository
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
        .addClickListener(e -> OptionDialog.showMessageDialog("Tämä on opastusohjelma!", "Info"));

    // Lisää Table Frameen
    mainFrame.add(tutorial, btn, table);
  }

}
```

## Seuraava vaihe {#next-step}

Näiden muutosten myötä sovellus lataa asiakasdatan tietokantaan ja näyttää sen sitten `Table`-komponentissa. Seuraavassa vaiheessa, [Reititys ja komposiitit](/docs/introduction/tutorial/routing-and-composites), esitellään reititettäminen ja useita näkymiä uusien asiakkaiden lisäämistä varten.
