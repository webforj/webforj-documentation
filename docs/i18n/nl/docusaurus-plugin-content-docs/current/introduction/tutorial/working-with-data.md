---
title: Working with Data
sidebar_position: 3
description: Step 2 - Use Spring to work with data.
_i18n_hash: c5bf8e9751b676f3490a2f01512647ca
---
In deze stap leer je hoe je een datamodel kunt maken met Spring en die gegevens visueel weergeven. Aan het einde van deze stap heeft de app die in de vorige stap is gemaakt, [Creating a Basic App](/docs/introduction/tutorial/creating-a-basic-app), een tabel die gegevens over klanten weergeeft. Het volgen van deze stappen leert je over:

- Spring annotaties
- Gegevensbeheer
- De webforJ `Table` component

Het voltooien van deze stap creëert een versie van [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data).

## De app uitvoeren {#running-the-app}

Tijdens de ontwikkeling van je app kun je [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data) als referentie gebruiken. Om de app in actie te zien:

1. Navigeer naar de bovenste directory met het `pom.xml` bestand, dit is `2-working-with-data` als je de versie op GitHub volgt.

2. Gebruik de volgende Maven-opdracht om de Spring Boot-app lokaal uit te voeren:
    ```bash
    mvn
    ```

De app opent automatisch een nieuwe browser op `http://localhost:8080`.

## Afhankelijkheden en configuraties {#dependencies-and-configurations}

Deze tutorial gebruikt [H2 database](https://www.h2database.com/html/main.html) en in een toekomstige stap de Jakarta Persistence API (JPA) via [Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html). Dit vereist dat je afhankelijkheden toevoegt aan `pom.xml` en `application.properties` bijwerkt. Dit is de laatste keer dat je deze twee bestanden zult moeten wijzigen voor de rest van de tutorial.

Voeg in je POM de volgende afhankelijkheden toe:

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

Voeg in `application.properties`, binnen `src/main/resources`, het volgende toe:

```
# H2 Database configuratie
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA configuratie
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
```

:::info Toegang tot gegevens
Deze tutorial maakt gebruik van een in-memory database en de standaard referenties voor toegang tot gegevens. Ga naar Spring's [Data Access](https://docs.spring.io/spring-boot/how-to/data-access.html) documentatie om meer te leren over specifieke Spring Boot configuratie-opties.
:::

## Spring beans {#spring-beans}

Een belangrijk onderdeel van het gebruik van het Spring-framework is het begrijpen van wat beans zijn. Beans zijn objecten met gedefinieerde Spring annotaties die het gemakkelijker maken voor Spring om ze te configureren door de beoogde functie van de klasse te kennen. Ga naar Spring’s [Bean Overview](https://docs.spring.io/spring-framework/reference/core/beans/definition.html) documentatie om meer te leren.

## Een datamodel maken {#creating-a-data-model}

Voordat je de gegevens visueel weergeeft of maakt, heeft deze tutorial een manier nodig om de gegevens van elke klant weer te geven, inclusief hun naam, land en bedrijf. Met Spring gebeurt dit met een klasse die een `@Entity` annotatie heeft.

Maak een klasse in `src/main/java/com/webforj/tutorial/entity` genaamd `Customer.java`. Het moet de `@Entity` annotatie hebben en getter- en setter-methoden voor de klantwaarden bevatten, met uitzondering van de `id`. In plaats van een creatiemethode voor `id` waarden te gebruiken, gebruik je de annotaties `@Id` en `@GeneratedValue` om te garanderen dat elke klant een unieke `id` krijgt.

<!-- vale off -->
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
<!-- vale on -->

Met het `Customer` datamodel op zijn plaats, kun je nu beginnen met het toevoegen van bedrijfslogica aan je app.

## Gegevens beheren {#managing-data}

Na het maken van een datamodel, ga je een repository en een service maken om de klantgegevens te beheren. Het maken van deze soorten klassen in je app stelt je in staat om bewerkingen uit te voeren zoals het toevoegen, verwijderen en bijwerken van klantrecords.

### Een repository maken {#creating-a-repository}

Het maken van een repository maakt de gegevens van de entiteiten toegankelijk, zodat je app meerdere klanten kan bevatten. Het doel van deze tutorial is om de gegevens bewerkbaar, sorteerbaar en valideerbaar te maken. Je bepaalt de mogelijkheden van een repository door de Spring Data repository die je gebruikt.

In een toekomstige stap, [Validating and Binding Data](/docs/introduction/tutorial/validating-and-binding-data), heb je toegang nodig tot Spring Data JPA om klant eigenschappen te valideren. Daarom is de juiste repository die je moet gebruiken de `JpaRepository`.

Maak in `src/main/java/com/webforj/tutorial/repository` een repository-interface die de Spring `@Repository` annotatie heeft en `JpaRepository` uitbreidt. Je moet specificeren welke type entiteiten in deze repository staan, en welk type object de `id` is. Om het zeker te maken, breid ook `JpaSpecificationExecutor` uit. Deze toevoeging stelt je in staat om later geavanceerde filteropties te implementeren, indien nodig.

```java title="CustomerRepository.java"
@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Long>,
        JpaSpecificationExecutor<Customer> {
}
```

De `CustomerRepository` die je zojuist hebt gemaakt, heeft geen gedeclareerde methoden. De methoden voor het beheren van de gegevens (de bedrijfslogica van de app) zullen zich in een serviceklasse bevinden.

:::info Spring documentatie links

Hier zijn vier links naar de documentatie van Spring die je zullen helpen om een beter begrip van Spring repositories te krijgen:

- [Werken met Spring Data Repositories](https://docs.spring.io/spring-data/commons/reference/repositories.html)
- [Overzicht Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html)
- [Spring Data JPA Specificaties](https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html)
- [`JpaRepository`](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html)
:::

### Een service maken {#creating-a-service}

In `src/main/java/com/webforj/tutorial/service`, maak een `CustomerService` klasse. Deze service zal methoden bevatten om klanten te creëren, bij te werken, te verwijderen en op te vragen met behulp van `CustomerRepository`.

Daarnaast heeft deze service een mechanisme nodig om Spring Data repositories te verbinden met de UI-componenten van webforJ. Het gebruik van de `SpringDataRepository` webforJ-klasse stelt je in staat om deze brug te creëren. Het vereenvoudigt gegevensbinding en CRUD-bewerkingen door het je webforJ-tabellen en formulieren mogelijk te maken om vrij met je door Spring beheerde gegevenslaag te werken. Zie meer informatie over de Spring-integratie van webforJ in het artikel [Spring Data JPA](/docs/integrations/spring/spring-data-jpa).

Voor deze serviceklasse gebruik je twee Spring annotaties:

- **`@Service`** - Dit markeert een klasse als een servicecomponent in Spring, waardoor deze automatisch wordt gedetecteerd en beheerd als een bean voor bedrijfslogica of herbruikbare bewerkingen.

- **`@Transactional`** - Deze annotatie vertelt Spring om de methode of klasse binnen een database-transactie uit te voeren, zodat alle bewerkingen samen worden gecommit of teruggedraaid. Meer details zijn beschikbaar in de documentatie van Spring, [Using @Transactional](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html#page-title).

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
      throw new IllegalArgumentException("Klant niet gevonden met ID: " + customer.getId());
    }
    return repository.save(customer);
  }

  public void deleteCustomer(Long id) {
    if (!repository.existsById(id)) {
      throw new IllegalArgumentException("Klant niet gevonden met ID: " + id);
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
        .orElseThrow(() -> new IllegalArgumentException("Klant niet gevonden met ID: " + id));
  }

  public boolean doesCustomerExist(Long id) {
    return repository.existsById(id);
  }

}
```

## Initiële gegevens laden {#loading-initial-data}

Voor deze tutorial komt de initiële klanten dataset uit een JSON-bestand. De Java-app laadt het bestand, niet de browser, dus maak het binnen `src/main/resources/data` met de volgende gegevens:

<!-- vale off -->
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
<!-- vale on -->

Vervolgens heeft de app een manier nodig om deze gegevens op te halen wanneer deze wordt gestart. Maak in `src/main/java/com/webforj/tutorial/config` een `DataInitializer` klasse. Nu, wanneer de app draait, als er geen klanten worden gedetecteerd, worden de klanten uit het JSON-bestand geladen en in de H2-database geplaatst:

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

Het laatste deel van deze stap is het gebruik van de [`Table`](/docs/components/table/overview) component en deze verbinden met de Spring-gegevens.

Een instantie van een webforJ `Table` moet een datatype hebben om te werken, dat is de entiteitsklasse die eerder in deze stap is gemaakt:

```java
Table<Customer> table = new Table<>();
```

Zodra je een `Table` hebt, krijgt elke klant eigenschap zijn eigen kolom. Voor elke kolom die je toevoegt, gebruik je de eigenschapsnaam, de getter-methode in de `Customer` entiteit en de `setLabel()` methode om de informatie weer te geven in de volgorde die je wilt:

```java
table.addColumn("firstName", Customer::getFirstName).setLabel("Voornaam");
table.addColumn("lastName", Customer::getLastName).setLabel("Achternaam");
table.addColumn("company", Customer::getCompany).setLabel("Bedrijf");
table.addColumn("country", Customer::getCountry).setLabel("Land");
```

Nadat je de kolommen hebt toegevoegd, moet je specificeren welke repository de `Table` moet gebruiken om de gegevens te populaten. Deze app haalt de repository uit de `getRepositoryAdapter()` methode in de gemaakte `CustomerService`:

```java
table.setRepository(customerService.getRepositoryAdapter());
```

### Tabelgrootte {#table-sizing}

Voor de tabel kun je `setSize()` gebruiken om de grootte in pixels of andere [CSS-eenheden](https://developer.mozilla.org/en-US/docs/Learn_web_development/Core/Styling_basics/Values_and_units) in te stellen. Door een maximale breedte in te stellen die relatief is aan de breedte van het scherm, help je je app om zich beter aan te passen aan kleinere schermen.

Voor de kolommen kun je de breedtes individueel instellen, of een van de `Table` methoden zoals `setColumnsToAutoFit()` gebruiken om webforJ de breedtes voor je te laten afhandelen:

```java
table.setSize("1000px", "294px");
table.setMaxWidth("90vw");
table.setColumnsToAutoFit();
```

### Gebruikersinteracties {#user-interactions}

De `Table` component heeft ook methoden om te bepalen hoe gebruikers met de kolommen interactie hebben:

```java
table.setColumnsToResizable(false);
table.getColumns().forEach(column -> column.setSortable(true));
```

De gemarkeerde delen van de `Application` klasse voegen de `Table` component toe, definiëren de kolommen en gebruiken `CustomerService` om de repository op te halen:

```java title="Application.java" {7-12,24-25,30-40,46-47}
@SpringBootApplication
@BundleEntry("css/card.css")
@AppTheme("system")
@AppProfile(name = "Klant Applicatie", shortName = "KlantApp")
public class Application extends App {

  //Voeg constructor injectie toe voor CustomerService
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

    //Voeg de Table component toe
    Table<Customer> table = new Table<>();

    mainFrame.setWidth("fit-content");
    mainFrame.addClassName("card");

    //Stijl de Table component, stel de kolommen in en stel de repository in
    table.setSize("1000px", "294px");
    table.setMaxWidth("90vw");
    table.addColumn("firstName", Customer::getFirstName).setLabel("Voornaam");
    table.addColumn("lastName", Customer::getLastName).setLabel("Achternaam");
    table.addColumn("company", Customer::getCompany).setLabel("Bedrijf");
    table.addColumn("country", Customer::getCountry).setLabel("Land");
    table.setColumnsToAutoFit();
    table.setColumnsToResizable(false);
    table.getColumns().forEach(column -> column.setSortable(true));
    table.setRepository(customerService.getRepositoryAdapter());

    btn.setTheme(ButtonTheme.PRIMARY)
        .setMaxWidth(200)
        .addClickListener(e -> OptionDialog.showMessageDialog("Dit is een tutorial!", "Info"));

    //Voeg de Table toe aan het Frame
    mainFrame.add(tutorial, btn, table);
  }

}
```

## Volgende stap {#next-step}

Met deze wijzigingen laadt de app klantgegevens in de database en geeft deze weer in een `Table` component. De volgende stap, [Routing and Composites](/docs/introduction/tutorial/routing-and-composites), introduceert routing en meerdere weergaven voor het toevoegen van nieuwe klanten.
