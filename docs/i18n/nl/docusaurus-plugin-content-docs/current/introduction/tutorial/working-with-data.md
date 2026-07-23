---
title: Working with Data
sidebar_position: 3
description: Step 2 - Use Spring to work with data.
_i18n_hash: 99491f42d1a848f6270686a0730d9d08
---
In deze stap leert u hoe u een datamodel maakt met Spring en die gegevens visueel weergeeft. Aan het eind van deze stap zal de app die in de vorige stap is gemaakt, [Een Basis App Maken](./creating-a-basic-app), een tabel bevatten die gegevens over klanten weergeeft. Het volgen van deze stappen leert u over:

- Spring annotaties
- Gegevensbeheer
- De webforJ `Table` component

Het voltooien van deze stap creëert een versie van [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data).

## De app uitvoeren {#running-the-app}

Terwijl u uw app ontwikkelt, kunt u [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data) als vergelijking gebruiken. Om de app in actie te zien:

1. Navigeer naar de top-level directory met het `pom.xml` bestand, dit is `2-working-with-data` als u de versie op GitHub volgt.

2. Gebruik de volgende Maven-opdracht om de Spring Boot-app lokaal uit te voeren:
    ```bash
    mvn
    ```

Wanneer de app draait, opent er automatisch een nieuwe browser op `http://localhost:8080`.

## Afhankelijkheden en configuraties {#dependencies-and-configurations}

Deze tutorial maakt gebruik van de [H2-database](https://www.h2database.com/html/main.html) en in een toekomstige stap, de Jakarta Persistence API (JPA) via [Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html). Dit vereist dat u afhankelijkheden toevoegt aan `pom.xml` en `application.properties` bijwerkt. Dit is de laatste keer dat u deze twee bestanden moet wijzigen voor de rest van de tutorial.

Voeg in uw POM de volgende afhankelijkheden toe:

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
Deze tutorial maakt gebruik van een in-memory database en de standaard referenties voor het toegang krijgen tot gegevens. Ga naar de [Data Access](https://docs.spring.io/spring-boot/how-to/data-access.html) documentatie van Spring om te leren over specifieke Spring Boot configuratieopties.
:::

## Spring beans {#spring-beans}

Een belangrijk onderdeel van het gebruik van het Spring-framework is het begrijpen van wat beans zijn. Beans zijn objecten met gedefinieerde Spring-annotaties die het voor Spring gemakkelijker maken om ze te configureren door de bedoeling van de klasse te kennen. Ga naar de [Bean Overview](https://docs.spring.io/spring-framework/reference/core/beans/definition.html) documentatie van Spring om meer te leren.

## Een datamodel maken {#creating-a-data-model}

Voordat u de gegevens visueel weergeeft of aanmaakt, heeft deze tutorial een manier nodig om de gegevens van elke klant weer te geven, inclusief hun naam, land en bedrijf. Met behulp van Spring gebeurt dit met een klasse die de `@Entity` annotatie heeft.

Maak een klasse in `src/main/java/com/webforj/tutorial/entity` genaamd `Customer.java`. Deze moet de `@Entity` annotatie hebben en getter- en setter-methoden voor de klantwaarden bevatten, behalve voor de `id`. In plaats van een aanmaakmethode voor `id` waarden te gebruiken, gebruikt u de annotaties `@Id` en `@GeneratedValue` om te garanderen dat elke klant een unieke `id` krijgt.

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

Met het `Customer` datamodel op zijn plaats, kunt u nu beginnen met het toevoegen van bedrijfslogica aan uw app.

## Gegevensbeheer {#managing-data}

Na het maken van een datamodel, zult u een repository en een service maken om de klantgegevens te beheren. Deze soorten klassen in uw app maken het mogelijk om bewerkingen zoals het toevoegen, verwijderen en bijwerken van klantrecords op te nemen.

### Een repository maken {#creating-a-repository}

Het maken van een repository maakt de gegevens van de entiteiten toegankelijk, zodat uw app meerdere klanten kan bevatten. Het doel van deze tutorial is om de gegevens bewerkbaar, sorteerbaar en validerend te maken. U bepaalt de mogelijkheden van een repository door de Spring Data repository die u gebruikt.

In een toekomstige stap, [Valideren en Binden van Gegevens](/docs/introduction/tutorial/validating-and-binding-data), heeft u toegang tot Spring Data JPA nodig om klant eigenschappen te valideren. Daarom is de geschikte repository om te gebruiken de `JpaRepository`.

Maak in `src/main/java/com/webforj/tutorial/repository` een repository-interface die de Spring `@Repository` annotatie heeft en `JpaRepository` uitbreidt. U moet specificeren welk type entiteiten in deze repository zijn, en welk type object de `id` is. Ter goede trouw, breidt u ook `JpaSpecificationExecutor` uit. Deze toevoeging stelt u in staat om later, indien nodig, geavanceerde filteropties te implementeren.

```java title="CustomerRepository.java"
@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Long>,
        JpaSpecificationExecutor<Customer> {
}
```

De `CustomerRepository` die u net hebt gemaakt zal geen gedefinieerde methoden hebben. De methoden voor het beheren van de gegevens (de bedrijfslogica van de app) zullen in een serviceklasse staan.

:::info Spring documentatie links

Hier zijn vier links naar de documentatie van Spring die u helpen om beter te begrijpen wat Spring repositories zijn:

- [Werken met Spring Data Repositories](https://docs.spring.io/spring-data/commons/reference/repositories.html)
- [Overzicht van Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html)
- [Spring Data JPA Specificaties](https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html)
- [`JpaRepository`](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html)
:::

### Een service maken {#creating-a-service}

Maak in `src/main/java/com/webforj/tutorial/service` een `CustomerService` klasse. Deze service zal methoden bevatten om klanten te creëren, bij te werken, te verwijderen en op te vragen met behulp van `CustomerRepository`.

Bovendien heeft deze service een mechanisme nodig om Spring Data repositories te koppelen aan de webforJ UI-componenten. Het gebruik van de `SpringDataRepository` webforJ klasse laat u deze brug creëren. Het vereenvoudigt gegevensbinding en CRUD-bewerkingen door te zorgen dat uw webforJ tabellen en formulieren vrij kunnen werken met uw door Spring beheerde gegevenslaag. Zie meer informatie over de integratie van webforJ met Spring in het artikel [Spring Data JPA](/docs/integrations/spring/spring-data-jpa).

Voor deze serviceklasse gebruikt u twee Spring-annotaties:

- **`@Service`** - Dit markeert een klasse als een servicecomponent in Spring, waardoor deze automatisch wordt gedetecteerd en beheerd als een bean voor bedrijfslogica of herbruikbare bewerkingen.

- **`@Transactional`** - Deze annotatie vertelt Spring om de methode of klasse binnen een database-transactie te draaien, zodat alle bewerkingen binnenin samen worden gecommitteerd of teruggedraaid. Meer details zijn beschikbaar in de documentatie van Spring, [Het gebruik van @Transactional](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html#page-title).

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

## Startgegevens laden {#loading-initial-data}

Voor deze tutorial komt de initiële klantendataset uit een JSON-bestand. Om directe toegang via de browser te voorkomen, moet de bestandsresource buiten `src/main/resources/static` worden gemaakt. Voor uw gemak kunt u het JSON-bestand binnen `src/main/resources/data` maken met de volgende gegevens:

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

Vervolgens heeft de app een manier nodig om deze gegevens op te halen wanneer deze opstart. Maak in `src/main/java/com/webforj/tutorial/config` een `DataInitializer` klasse. Nu, wanneer de app draait, als er geen klanten worden gedetecteerd, laadt deze klanten uit het JSON-bestand en plaatst ze in de H2-database:

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

Het laatste deel van deze stap is het gebruik van de [`Table`](/docs/components/table/overview) component en het verbinden ervan met de Spring-gegevens.

Een instantie van een webforJ `Table` heeft een datatype nodig om te kunnen werken, dat is de entiteitsklasse die eerder in deze stap is gemaakt:

```java
Table<Customer> table = new Table<>();
```

Zodra u een `Table` heeft, krijgt elke klantproperty zijn eigen kolom. Voor elke kolom die u toevoegt, gebruikt u de propertynaam, de getter-methode in de `Customer` entiteit en de `setLabel()` methode om de informatie in de volgorde weer te geven die u wilt:

```java
table.addColumn("firstName", Customer::getFirstName).setLabel("Voornaam");
table.addColumn("lastName", Customer::getLastName).setLabel("Achternaam");
table.addColumn("company", Customer::getCompany).setLabel("Bedrijf");
table.addColumn("country", Customer::getCountry).setLabel("Land");
```

Nadat u de kolommen heeft toegevoegd, moet u specificeren welke repository de `Table` moet gebruiken om zijn gegevens te vullen. Deze app krijgt de repository van de `getRepositoryAdapter()` methode in de gemaakte `CustomerService`:

```java
table.setRepository(customerService.getRepositoryAdapter());
```

### Tabelgrootte {#table-sizing}

Voor de tabel kunt u `setSize()` gebruiken om de grootte in pixels of andere [CSS-eenheden](https://developer.mozilla.org/en-US/docs/Learn_web_development/Core/Styling_basics/Values_and_units) in te stellen. Door een maximale breedte in te stellen ten opzichte van de breedte van het scherm, helpt u uw app om zich aan te passen aan kleinere schermen.

Voor de kolommen kunt u de breedtes individueel instellen, of een van de `Table` methoden zoals `setColumnsToAutoFit()` gebruiken om het webforJ voor u de breedtes te laten beheren:

```java
table.setSize("1000px", "294px");
table.setMaxWidth("90vw");
table.setColumnsToAutoFit();
```

### Gebruikersinteracties {#user-interactions}

De `Table` component heeft ook methoden om te controleren hoe gebruikers met de kolommen interactie hebben:

```java
table.setColumnsToResizable(false);
table.getColumns().forEach(column -> column.setSortable(true));
```

De gemarkeerde delen van de `Application` klasse voegen de `Table` component toe, definiëren de kolommen en gebruiken `CustomerService` om de repository op te halen:

```java title="Application.java" {7-12,24-25,30-40,46-47}
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")
@AppProfile(name = "Klantapplicatie", shortName = "KlantApp")
public class Application extends App {

  //Voeg een constructor-injectie toe voor CustomerService
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

Met deze wijzigingen laadt de app klantgegevens in de database en geeft deze weer in een `Table` component. De volgende stap, [Routing en Composities](/docs/introduction/tutorial/routing-and-composites), introduceert routing en meerdere weergaven voor het toevoegen van nieuwe klanten.
