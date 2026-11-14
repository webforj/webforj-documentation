---
title: Werken met gegevens
sidebar_position: 3
description: Step 2 - Use Spring to work with data.
_i18n_hash: f1c000ec07cb278db9d60d5f90d71743
---
In deze stap leer je hoe je met Spring een gegevensmodel maakt en de gegevens visueel weergeeft.  
Aan het einde van deze stap bevat de app die je in de vorige stap hebt gemaakt, [Een basisapp maken](/docs/introduction/tutorial/creating-a-basic-app), een tabel met klantgegevens. Door de stappen te volgen, leer je meer over:

- Spring-annotaties
- Gegevens beheren
- De webforJ-component `Table`

Als je deze stap voltooit, maak je een versie van [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data).

<!-- <VideoPlayer video="tutorial/working-with-data" width={1920} height={1080} title="Working with data" /> -->

## De app uitvoeren {#running-the-app}

Tijdens het ontwikkelen van je app kun je [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data) als vergelijkingsmateriaal gebruiken. Zo kun je de app bekijken:

1. Ga naar de hoofdmap met het bestand `pom.xml`. Als je de versie op GitHub volgt, is dit de map `2-working-with-data`.

2. Gebruik de volgende Maven-opdracht om de Spring Boot-app lokaal uit te voeren:
    ```bash
    mvn
    ```

Wanneer je de app uitvoert, wordt automatisch een nieuw browservenster geopend op `http://localhost:8080`.

## Afhankelijkheden en configuraties {#dependencies-and-configurations}

Deze tutorial gebruikt de [H2-database](https://www.h2database.com/html/main.html) en in een latere stap de Jakarta Persistence API (JPA) via
[Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html). Hiervoor moet je afhankelijkheden toevoegen aan `pom.xml` en `application.properties` bijwerken. Dit is de laatste keer dat je deze twee bestanden tijdens de rest van de tutorial hoeft aan te passen.

Voeg de volgende afhankelijkheden toe aan je POM:

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

Voeg het volgende toe aan `application.properties` in `src/main/resources`:

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

:::info Toegang tot gegevens
Deze tutorial gebruikt een database in het geheugen en de standaardreferenties voor toegang tot gegevens. Raadpleeg de documentatie van Spring over [gegevenstoegang](https://docs.spring.io/spring-boot/how-to/data-access.html) voor meer informatie over specifieke configuratieopties van Spring Boot.
:::

## Spring-beans {#spring-beans}

Een belangrijk onderdeel van het gebruik van het Spring-framework is begrijpen wat beans zijn. Beans zijn objecten met gedefinieerde Spring-annotaties. Daardoor kan Spring ze eenvoudiger configureren op basis van het beoogde doel van de klasse. Raadpleeg de documentatie van Spring over [beans](https://docs.spring.io/spring-framework/reference/core/beans/definition.html) voor meer informatie.

## Een gegevensmodel maken {#creating-a-data-model}

Voordat je de gegevens visueel weergeeft of aanmaakt, heb je in deze tutorial een manier nodig om de gegevens van elke klant weer te geven, waaronder de naam, het land en het bedrijf. In Spring doe je dit met een klasse met de annotatie `@Entity`.

Maak een klasse met de naam `Customer.java` in `src/main/java/com/webforj/tutorial/entity`. Deze klasse moet de annotatie `@Entity` hebben en getter- en settermethoden bevatten voor de klantgegevens, met uitzondering van `id`. Gebruik voor `id`-waarden geen aanmaakmethode, maar de annotaties `@Id` en `@GeneratedValue` om te garanderen dat elke klant een unieke `id` krijgt.


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

Nu het gegevensmodel `Customer` klaar is, kun je bedrijfslogica aan je app toevoegen.

## Gegevens beheren {#managing-data}

Nadat je een gegevensmodel hebt gemaakt, maak je een repository en een service om de klantgegevens te beheren. Met dit soort klassen in je app kun je bewerkingen toevoegen zoals het toevoegen, verwijderen en bijwerken van klantrecords.

### Een repository maken {#creating-a-repository}

Met een repository worden de gegevens van entiteiten toegankelijk, zodat je app meerdere klanten kan bevatten. Het doel van deze tutorial is om de gegevens bewerkbaar, sorteerbaar en valideerbaar te maken. De mogelijkheden van een repository hangen af van de Spring Data-repository die je gebruikt.

In een latere stap, [Gegevens valideren en binden](/docs/introduction/tutorial/validating-and-binding-data), heb je toegang tot Spring Data JPA nodig om klanteigenschappen te valideren. Daarom is `JpaRepository` de juiste repository om te gebruiken.

Maak in `src/main/java/com/webforj/tutorial/repository` een repository-interface met de Spring-annotatie `@Repository` die `JpaRepository` uitbreidt. Je moet opgeven welke typen entiteiten deze repository bevat en van welk type het object `id` is. Breid voor de volledigheid ook `JpaSpecificationExecutor` uit. Zo kun je later, indien nodig, geavanceerde filteropties implementeren.

```java title="CustomerRepository.java"
@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Long>,
        JpaSpecificationExecutor<Customer> {
}
```

De `CustomerRepository` die je zojuist hebt gemaakt, heeft geen gedeclareerde methoden. De methoden voor het beheren van de gegevens (de bedrijfslogica van de app) komen in een serviceklasse.

:::info Links naar Spring-documentatie

Hier vind je vier links naar de Spring-documentatie die je helpen Spring-repositories beter te begrijpen:

- [Werken met Spring Data-repositories](https://docs.spring.io/spring-data/commons/reference/repositories.html)
- [Overzicht van Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html)
- [Spring Data JPA-specificaties](https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html)
- [`JpaRepository`](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html)
:::

### Een service maken {#creating-a-service}

Maak in `src/main/java/com/webforj/tutorial/service` een klasse `CustomerService`. Deze service bevat methoden om klanten aan te maken, bij te werken, te verwijderen en op te vragen met behulp van `CustomerRepository`.

Daarnaast heeft deze service een mechanisme nodig om Spring Data-repositories te verbinden met de UI-componenten van webforJ. Met de webforJ-klasse `SpringDataRepository` kun je deze koppeling maken. Dit vereenvoudigt gegevensbinding en CRUD-bewerkingen doordat je webforJ-tabellen en -formulieren rechtstreeks met je door Spring beheerde gegevenslaag kunnen werken. Lees meer over de Spring-integratie van webforJ in het artikel [Spring Data JPA](/docs/integrations/spring/spring-data-jpa).

Voor deze serviceklasse gebruik je twee Spring-annotaties:

- **`@Service`** - Hiermee markeer je een klasse als servicecomponent in Spring. De klasse wordt dan automatisch gedetecteerd en als bean beheerd voor bedrijfslogica of herbruikbare bewerkingen.

- **`@Transactional`** - Met deze annotatie geef je Spring de opdracht de methode of klasse uit te voeren binnen een databasetransactie. Alle bewerkingen binnen de transactie worden dan samen vastgelegd of teruggedraaid. Meer informatie vind je in de Spring-documentatie, [Using @Transactional](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html#page-title).


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

## Initiële gegevens laden {#loading-initial-data}

Voor deze tutorial komt de initiële dataset met klantgegevens uit een JSON-bestand. De Java-app laadt het bestand, niet de browser. Maak het bestand daarom in `src/main/resources/data` met de volgende gegevens:

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

Vervolgens heeft de app een manier nodig om deze gegevens bij het opstarten op te halen. Maak in `src/main/java/com/webforj/tutorial/config` een klasse `DataInitializer`. Wanneer de app wordt uitgevoerd, laadt deze de klanten uit het JSON-bestand en slaat ze op in de H2-database als er nog geen klanten zijn gevonden:

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

## Gegevens visueel weergeven {#displaying-data-visually}

Het laatste onderdeel van deze stap is het gebruik van de component [`Table`](/docs/components/table/overview) en de koppeling ervan met de Spring-gegevens.

Een instantie van een webforJ-`Table` heeft een gegevenstype nodig om te kunnen werken. Dat is de entiteitsklasse die je eerder in deze stap hebt gemaakt:

```java
Table<Customer> table = new Table<>();
```

Zodra je een `Table` hebt, krijgt elke klanteigenschap een eigen kolom. Geef voor elke kolom die je toevoegt de eigenschapsnaam en de getter-methode in de entiteit `Customer` op. Gebruik de methode `setLabel()` om de informatie weer te geven in de gewenste volgorde:

```java
table.addColumn("firstName", Customer::getFirstName).setLabel("First Name");
table.addColumn("lastName", Customer::getLastName).setLabel("Last Name");
table.addColumn("company", Customer::getCompany).setLabel("Company");
table.addColumn("country", Customer::getCountry).setLabel("Country");
```

Nadat je de kolommen hebt toegevoegd, geef je op welke repository de `Table` moet gebruiken om de gegevens te vullen. Deze app haalt de repository op via de methode `getRepositoryAdapter()` in de gemaakte `CustomerService`:

```java
table.setRepository(customerService.getRepositoryAdapter());
```

### De tabelgrootte instellen {#table-sizing}

Voor de tabel kun je `setSize()` gebruiken om de grootte in pixels of andere [CSS-eenheden](https://developer.mozilla.org/en-US/docs/Learn_web_development/Core/Styling_basics/Values_and_units) in te stellen. Door een maximale breedte in te stellen die is afgestemd op de schermbreedte, zorg je ervoor dat je app zich beter aan kleinere schermen aanpast.

Je kunt de breedte van de kolommen afzonderlijk instellen, of een van de `Table`-methoden gebruiken, zoals `setColumnsToAutoFit()`, zodat webforJ de breedtes voor je bepaalt:

```java
table.setSize("1000px", "294px");
table.setMaxWidth("90vw");
table.setColumnsToAutoFit();
```

### Gebruikersinteracties {#user-interactions}

De component `Table` heeft ook methoden waarmee je bepaalt hoe gebruikers met de kolommen kunnen werken:

```java
table.setColumnsToResizable(false);
table.getColumns().forEach(column -> column.setSortable(true));
```

De gemarkeerde delen van de klasse `Application` voegen de component `Table` toe, definiëren de kolommen en gebruiken `CustomerService` om de repository op te halen:

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

## Volgende stap {#next-step}

Met deze wijzigingen laadt de app klantgegevens in de database en geeft ze vervolgens weer in een component `Table`. In de volgende stap, [Routing en composites](/docs/introduction/tutorial/routing-and-composites), maak je kennis met routing en meerdere weergaven om nieuwe klanten toe te voegen.
