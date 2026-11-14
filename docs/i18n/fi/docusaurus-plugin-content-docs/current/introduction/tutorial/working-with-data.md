---
title: Tietojen käsittely
sidebar_position: 3
description: Step 2 - Use Spring to work with data.
_i18n_hash: f1c000ec07cb278db9d60d5f90d71743
---
Tässä vaiheessa opit luomaan tietomallin Springillä ja näyttämään tiedot visuaalisesti.
Tämän vaiheen lopussa edellisessä vaiheessa luodussa sovelluksessa [Perussovelluksen luominen](/docs/introduction/tutorial/creating-a-basic-app) on taulukko, jossa näytetään asiakastietoja. Vaiheen aikana opit muun muassa:

- Spring-annotaatioita
- Tietojen hallintaa
- webforJ:n `Table`-komponentin käyttöä

Tämän vaiheen suorittaminen luo version [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data).

<!-- <VideoPlayer video="tutorial/working-with-data" width={1920} height={1080} title="Working with data" /> -->

## Sovelluksen suorittaminen {#running-the-app}

Voit verrata kehittämäsi sovellusta versioon [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data). Näin näet sovelluksen toiminnassa:

1. Siirry ylimmän tason hakemistoon, jossa `pom.xml`-tiedosto sijaitsee. Jos käytät GitHubissa olevaa versiota, hakemisto on `2-working-with-data`.

2. Suorita Spring Boot -sovellus paikallisesti seuraavalla Maven-komennolla:
    ```bash
    mvn
    ```

Sovelluksen suorittaminen avaa automaattisesti uuden selainikkunan osoitteeseen `http://localhost:8080`.

## Riippuvuudet ja määritykset {#dependencies-and-configurations}

Tässä opetusohjelmassa käytetään [H2-tietokantaa](https://www.h2database.com/html/main.html), ja myöhemmässä vaiheessa Jakarta Persistence API:a (JPA) [Spring Data JPA:n](https://docs.spring.io/spring-data/jpa/reference/index.html) kautta. Tätä varten sinun on lisättävä riippuvuudet `pom.xml`-tiedostoon ja päivitettävä `application.properties`-tiedosto. Nämä ovat viimeiset muutokset, jotka sinun tarvitsee tehdä näihin kahteen tiedostoon tämän opetusohjelman aikana.

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

Lisää `src/main/resources`-hakemistossa olevaan `application.properties`-tiedostoon seuraavat rivit:

```
# H2 Database configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA configuration
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
```

:::info Tietojen käyttäminen
Tässä opetusohjelmassa käytetään muistinvaraista tietokantaa ja tietojen käytön oletustunnistetietoja. Lue Springin [Data Access](https://docs.spring.io/spring-boot/how-to/data-access.html) -dokumentaatiosta lisää Spring Bootin määritysasetuksista.
:::

## Spring-beanit {#spring-beans}

Spring-kehyksen käytön kannalta on tärkeää ymmärtää, mitä beanit ovat. Beanit ovat objekteja, joihin on määritetty Spring-annotaatioita. Niiden avulla Spring voi määrittää objektit helpommin luokan käyttötarkoituksen perusteella. Lue lisää Springin [Bean Overview](https://docs.spring.io/spring-framework/reference/core/beans/definition.html) -dokumentaatiosta.

## Tietomallin luominen {#creating-a-data-model}

Ennen tietojen näyttämistä tai luomista tarvitset tavan esittää kunkin asiakkaan tiedot, kuten nimen, maan ja yrityksen. Springissä tämä tehdään luokalla, jossa on `@Entity`-annotaatio.

Luo luokka hakemistoon `src/main/java/com/webforj/tutorial/entity` ja anna sille nimeksi `Customer.java`. Lisää luokkaan `@Entity`-annotaatio ja getter- ja setter-metodit asiakkaan arvoille `id`-arvoa lukuun ottamatta. Sen sijaan, että käyttäisit `id`-arvojen luontiin metodia, käytä `@Id`- ja `@GeneratedValue`-annotaatioita varmistaaksesi, että jokaisella asiakkaalla on yksilöllinen `id`.


<!-- vale off -->
<ExpandableCode title="Customer.java" language="java" startLine={1} endLine={15}>

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

</ExpandableCode>
<!-- vale on -->

Kun `Customer`-tietomalli on valmis, voit lisätä sovellukseen liiketoimintalogiikkaa.

## Tietojen hallinta {#managing-data}

Tietomallin luomisen jälkeen luot repositorion ja palvelun asiakastietojen hallintaa varten. Näiden luokkien avulla voit lisätä, poistaa ja päivittää asiakastietueita sovelluksessasi.

### Repositorion luominen {#creating-a-repository}

Repositorio tekee entiteettien tiedot käytettäviksi, jolloin sovelluksessasi voi olla useita asiakkaita. Tämän opetusohjelman tavoitteena on tehdä tiedoista muokattavia, lajiteltavia ja validoitavia. Repositorion ominaisuudet määräytyvät käyttämäsi Spring Data -repositorion mukaan.

Myöhemmässä vaiheessa, [Tietojen validointi ja sidonta](/docs/introduction/tutorial/validating-and-binding-data), tarvitset Spring Data JPA:ta asiakasominaisuuksien validointiin. Siksi tähän tarkoitukseen sopiva repositorio on `JpaRepository`.

Luo hakemistoon `src/main/java/com/webforj/tutorial/repository` repositoriorajapinta, jossa on Springin `@Repository`-annotaatio ja joka laajentaa `JpaRepository`-rajapintaa. Määritä, minkä tyyppisiä entiteettejä repositorio sisältää ja minkä tyyppinen objekti `id` on. Laajenna lisäksi `JpaSpecificationExecutor`-rajapintaa. Näin voit tarvittaessa toteuttaa myöhemmin edistyneitä suodatusvaihtoehtoja.

```java title="CustomerRepository.java"
@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Long>,
        JpaSpecificationExecutor<Customer> {
}
```

Juuri luomassasi `CustomerRepository`-luokassa ei ole määritettyjä metodeja. Tietojen hallintaan käytettävät metodit eli sovelluksen liiketoimintalogiikka sijaitsevat palveluluokassa.

:::info Linkkejä Spring-dokumentaatioon

Seuraavat neljä linkkiä Springin dokumentaatioon auttavat ymmärtämään Spring-repositorioita paremmin:

- [Working with Spring Data Repositories](https://docs.spring.io/spring-data/commons/reference/repositories.html)
- [Spring Data JPA Overview](https://docs.spring.io/spring-data/jpa/reference/index.html)
- [Spring Data JPA Specifications](https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html)
- [`JpaRepository`](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html)
:::

### Palvelun luominen {#creating-a-service}

Luo `src/main/java/com/webforj/tutorial/service`-hakemistoon `CustomerService`-luokka. Tämä palvelu sisältää metodit asiakkaiden luomiseen, päivittämiseen, poistamiseen ja kyselyyn `CustomerRepository`-luokan avulla.

Palvelu tarvitsee lisäksi mekanismin, jolla Spring Data -repositoriot yhdistetään webforJ:n käyttöliittymäkomponentteihin. webforJ:n `SpringDataRepository`-luokan avulla voit luoda tämän yhteyden. Se yksinkertaistaa tietojen sidontaa ja CRUD-toimintoja, koska webforJ:n taulukot ja lomakkeet voivat toimia suoraan Springin hallitseman tietokerroksen kanssa. Lue lisää webforJ:n Spring-integraatiosta [Spring Data JPA](/docs/integrations/spring/spring-data-jpa) -artikkelista.

Tässä palveluluokassa käytetään kahta Spring-annotaatiota:

- **`@Service`** – Tämä merkitsee luokan Springin palvelukomponentiksi, jolloin se havaitaan ja sitä hallitaan automaattisesti beanina liiketoimintalogiikkaa tai uudelleenkäytettäviä toimintoja varten.

- **`@Transactional`** – Tämä annotaatio ohjaa Springiä suorittamaan metodin tai luokan tietokantatapahtuman sisällä, jolloin kaikki sen sisältämät toiminnot vahvistetaan tai peruutetaan yhdessä. Lisätietoja on Springin dokumentaation kohdassa [Using @Transactional](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html#page-title).


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
      throw new IllegalArgumentException("Customer not found with ID: " + customer.getId());
    }
    return repository.save(customer);
  }

  public void deleteCustomer(Long id) {
    if (!repository.existsById(id)) {
      throw new IllegalArgumentException("Customer not found with ID: " + id);
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
        .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + id));
  }

  public boolean doesCustomerExist(Long id) {
    return repository.existsById(id);
  }

}
```

## Alkutilan tietojen lataaminen {#loading-initial-data}

Tässä opetusohjelmassa alkuperäiset asiakastiedot luetaan JSON-tiedostosta. Java-sovellus lataa tiedoston, ei selain, joten luo se hakemistoon `src/main/resources/data` seuraavilla tiedoilla:

<!-- vale off -->
<ExpandableCode title="customers.json" language="json" startLine={1} endLine={13}>

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

</ExpandableCode>
<!-- vale on -->

Sovellus tarvitsee sitten tavan noutaa nämä tiedot käynnistyessään. Luo `src/main/java/com/webforj/tutorial/config`-hakemistoon `DataInitializer`-luokka. Kun sovellus käynnistyy, se tarkistaa, onko asiakkaita olemassa. Jos niitä ei ole, se lataa asiakkaat JSON-tiedostosta ja tallentaa ne H2-tietokantaan:

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

## Tietojen näyttäminen visuaalisesti {#displaying-data-visually}

Tämän vaiheen viimeisessä osassa käytetään [`Table`](/docs/components/table/overview)-komponenttia ja yhdistetään se Springin tietoihin.

webforJ:n `Table`-instanssi tarvitsee tietotyypin toimiakseen. Tässä tapauksessa se on aiemmin tässä vaiheessa luotu entiteettiluokka:

```java
Table<Customer> table = new Table<>();
```

Kun `Table` on luotu, jokaiselle asiakkaan ominaisuudelle lisätään oma sarake. Käytä jokaisessa lisäämässäsi sarakkeessa ominaisuuden nimeä, sen getter-metodia `Customer`-entiteetissä ja `setLabel()`-metodia tietojen näyttämiseen haluamassasi järjestyksessä:

```java
table.addColumn("firstName", Customer::getFirstName).setLabel("First Name");
table.addColumn("lastName", Customer::getLastName).setLabel("Last Name");
table.addColumn("company", Customer::getCompany).setLabel("Company");
table.addColumn("country", Customer::getCountry).setLabel("Country");
```

Kun sarakkeet on lisätty, määritä, mitä repositoriota `Table` käyttää tietojen täyttämiseen. Tässä sovelluksessa repositorio saadaan luodun `CustomerService`-luokan `getRepositoryAdapter()`-metodista:

```java
table.setRepository(customerService.getRepositoryAdapter());
```

### Taulukon koon määrittäminen {#table-sizing}

Voit määrittää taulukon koon pikseleinä tai muina [CSS-yksikköinä](https://developer.mozilla.org/en-US/docs/Learn_web_development/Core/Styling_basics/Values_and_units) käyttämällä `setSize()`-metodia. Määrittämällä enimmäisleveyden suhteessa näytön leveyteen teet sovelluksesta mukautuvamman pienemmillä näytöillä.

Voit määrittää sarakkeiden leveydet yksitellen tai antaa webforJ:n huolehtia niistä esimerkiksi `Table`-luokan `setColumnsToAutoFit()`-metodilla:

```java
table.setSize("1000px", "294px");
table.setMaxWidth("90vw");
table.setColumnsToAutoFit();
```

### Käyttäjän vuorovaikutus {#user-interactions}

`Table`-komponentissa on myös metodeja, joilla voit hallita käyttäjien vuorovaikutusta sarakkeiden kanssa:

```java
table.setColumnsToResizable(false);
table.getColumns().forEach(column -> column.setSortable(true));
```

`Application`-luokan korostetut osat lisäävät `Table`-komponentin, määrittävät sen sarakkeet ja käyttävät `CustomerService`-luokkaa repositorion noutamiseen:

```java title="Application.java" {7-12,24-25,30-40,46-47}
@SpringBootApplication
@BundleEntry("css/card.css")
@AppTheme("system")
@AppProfile(name = "Customer Application", shortName = "CustomerApp")
public class Application extends App {

  //Add a constructor injection for CustomerService
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
    Paragraph tutorial = new Paragraph("Tutorial App!");
    Button btn = new Button("Info");

    //Add the Table component
    Table<Customer> table = new Table<>();

    mainFrame.setWidth("fit-content");
    mainFrame.addClassName("card");

    //Style the Table component, set the columns, and set the repository
    table.setSize("1000px", "294px");
    table.setMaxWidth("90vw");
    table.addColumn("firstName", Customer::getFirstName).setLabel("First Name");
    table.addColumn("lastName", Customer::getLastName).setLabel("Last Name");
    table.addColumn("company", Customer::getCompany).setLabel("Company");
    table.addColumn("country", Customer::getCountry).setLabel("Country");
    table.setColumnsToAutoFit();
    table.setColumnsToResizable(false);
    table.getColumns().forEach(column -> column.setSortable(true));
    table.setRepository(customerService.getRepositoryAdapter());

    btn.setTheme(ButtonTheme.PRIMARY)
        .setMaxWidth(200)
        .addClickListener(e -> OptionDialog.showMessageDialog("This is a tutorial!", "Info"));

    //Add the Table to the Frame
    mainFrame.add(tutorial, btn, table);
  }

}
```

## Seuraava vaihe {#next-step}

Näiden muutosten jälkeen sovellus lataa asiakastiedot tietokantaan ja näyttää ne `Table`-komponentissa. Seuraavassa vaiheessa [Reititys ja yhdistelmäkomponentit](/docs/introduction/tutorial/routing-and-composites) esitellään reititys ja useat näkymät uusien asiakkaiden lisäämistä varten.
