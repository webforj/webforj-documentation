---
title: Arbeiten mit Daten
sidebar_position: 3
description: Step 2 - Use Spring to work with data.
_i18n_hash: f1c000ec07cb278db9d60d5f90d71743
---
In diesem Schritt lernst du, wie du mit Spring ein Datenmodell erstellst und die Daten visuell darstellst.  
Am Ende dieses Schritts enthält die App aus dem vorherigen Schritt [Erstellen einer einfachen App](/docs/introduction/tutorial/creating-a-basic-app) eine Tabelle mit Kundendaten. Dabei lernst du Folgendes kennen:

- Spring-Annotationen
- Datenverwaltung
- Die webforJ-Komponente `Table`

Nach Abschluss dieses Schritts hast du eine Version von [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data).

<!-- <VideoPlayer video="tutorial/working-with-data" width={1920} height={1080} title="Working with data" /> -->

## App ausführen {#running-the-app}

Während der Entwicklung deiner App kannst du [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data) als Vergleich heranziehen. So kannst du die App ausprobieren:

1. Wechsle in das Verzeichnis der obersten Ebene, das die Datei `pom.xml` enthält. Wenn du der GitHub-Version folgst, ist das Verzeichnis `2-working-with-data`.

2. Führe den folgenden Maven-Befehl aus, um die Spring-Boot-App lokal zu starten:
    ```bash
    mvn
    ```

Beim Start der App wird automatisch ein neues Browserfenster mit `http://localhost:8080` geöffnet.

## Abhängigkeiten und Konfigurationen {#dependencies-and-configurations}

In diesem Tutorial werden die [H2-Datenbank](https://www.h2database.com/html/main.html) und in einem späteren Schritt die Jakarta Persistence API (JPA) über [Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html) verwendet. Dafür musst du Abhängigkeiten zu `pom.xml` hinzufügen und `application.properties` aktualisieren. Für den Rest des Tutorials brauchst du diese beiden Dateien nicht mehr zu ändern.

Füge deinem POM die folgenden Abhängigkeiten hinzu:

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

Füge in `application.properties` im Verzeichnis `src/main/resources` Folgendes hinzu:

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

:::info Zugriff auf Daten
In diesem Tutorial wird eine In-Memory-Datenbank mit den Standardanmeldedaten für den Datenzugriff verwendet. In der Spring-Dokumentation zu [Datenzugriff](https://docs.spring.io/spring-boot/how-to/data-access.html) erfährst du mehr über bestimmte Konfigurationsoptionen von Spring Boot.
:::

## Spring-Beans {#spring-beans}

Ein wichtiger Aspekt bei der Verwendung des Spring-Frameworks ist das Verständnis von Beans. Beans sind Objekte mit festgelegten Spring-Annotationen. Dadurch kann Spring sie leichter konfigurieren, da die vorgesehene Funktion der Klasse bekannt ist. Weitere Informationen findest du in der Spring-Dokumentation zur [Übersicht über Beans](https://docs.spring.io/spring-framework/reference/core/beans/definition.html).

## Datenmodell erstellen {#creating-a-data-model}

Bevor du die Daten visuell darstellst oder erstellst, brauchst du in diesem Tutorial eine Möglichkeit, die Daten jedes Kunden abzubilden, einschließlich Name, Land und Unternehmen. Mit Spring wird dafür eine Klasse mit der Annotation `@Entity` verwendet.

Erstelle im Verzeichnis `src/main/java/com/webforj/tutorial/entity` eine Klasse namens `Customer.java`. Sie sollte die Annotation `@Entity` enthalten und Getter- und Setter-Methoden für die Kundenwerte bereitstellen, mit Ausnahme von `id`. Verwende statt einer Erstellungsmethode für `id`-Werte die Annotationen `@Id` und `@GeneratedValue`, um sicherzustellen, dass jeder Kunde eine eindeutige `id` erhält.


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

Nachdem das Datenmodell `Customer` erstellt ist, kannst du deiner App Geschäftslogik hinzufügen.

## Daten verwalten {#managing-data}

Nach dem Erstellen eines Datenmodells erstellst du ein Repository und einen Service zur Verwaltung der Kundendaten. Mit diesen Klassen kannst du in deiner App Vorgänge zum Hinzufügen, Löschen und Aktualisieren von Kundendatensätzen bereitstellen.

### Repository erstellen {#creating-a-repository}

Ein Repository macht die Daten der Entitäten zugänglich, sodass deine App mehrere Kunden enthalten kann. Ziel dieses Tutorials ist es, die Daten bearbeitbar, sortierbar und validierbar zu machen. Welche Funktionen ein Repository bietet, hängt vom verwendeten Spring-Data-Repository ab.

In einem späteren Schritt, [Daten validieren und binden](/docs/introduction/tutorial/validating-and-binding-data), benötigst du Zugriff auf Spring Data JPA, um Kundeneigenschaften zu validieren. Daher eignet sich `JpaRepository` als Repository.

Erstelle im Verzeichnis `src/main/java/com/webforj/tutorial/repository` eine Repository-Schnittstelle mit der Spring-Annotation `@Repository`, die `JpaRepository` erweitert. Du musst angeben, welche Art von Entitäten sich in diesem Repository befindet und welchen Typ das Objekt `id` hat. Erweitere außerdem `JpaSpecificationExecutor`. Damit kannst du bei Bedarf später erweiterte Filteroptionen implementieren.

```java title="CustomerRepository.java"
@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Long>,
        JpaSpecificationExecutor<Customer> {
}
```

Das gerade erstellte `CustomerRepository` enthält keine deklarierten Methoden. Die Methoden zur Datenverwaltung – also die Geschäftslogik der App – werden in einer Service-Klasse untergebracht.

:::info Links zur Spring-Dokumentation

Die folgenden vier Links zur Spring-Dokumentation helfen dir, Spring-Repositorys besser zu verstehen:

- [Arbeiten mit Spring Data Repositorys](https://docs.spring.io/spring-data/commons/reference/repositories.html)
- [Spring Data JPA – Übersicht](https://docs.spring.io/spring-data/jpa/reference/index.html)
- [Spring Data JPA Specifications](https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html)
- [`JpaRepository`](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html)
:::

### Service erstellen {#creating-a-service}

Erstelle im Verzeichnis `src/main/java/com/webforj/tutorial/service` eine Klasse `CustomerService`. Dieser Service enthält Methoden zum Erstellen, Aktualisieren, Löschen und Abfragen von Kunden mithilfe von `CustomerRepository`.

Außerdem benötigt dieser Service einen Mechanismus, um Spring-Data-Repositorys mit den UI-Komponenten von webforJ zu verbinden. Mit der webforJ-Klasse `SpringDataRepository` kannst du diese Verbindung herstellen. Sie vereinfacht die Datenbindung und CRUD-Vorgänge, sodass deine webforJ-Tabellen und -Formulare direkt mit der von Spring verwalteten Datenschicht arbeiten können. Weitere Informationen zur Spring-Integration von webforJ findest du im Artikel [Spring Data JPA](/docs/integrations/spring/spring-data-jpa).

Für diese Service-Klasse verwendest du zwei Spring-Annotationen:

- **`@Service`** – Kennzeichnet eine Klasse als Service-Komponente in Spring. Dadurch wird sie automatisch erkannt und als Bean für Geschäftslogik oder wiederverwendbare Vorgänge verwaltet.

- **`@Transactional`** – Diese Annotation weist Spring an, die Methode oder Klasse innerhalb einer Datenbanktransaktion auszuführen. So werden alle darin enthaltenen Vorgänge gemeinsam übernommen oder zurückgerollt. Weitere Informationen findest du in der Spring-Dokumentation unter [Using @Transactional](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html#page-title).


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

## Anfangsdaten laden {#loading-initial-data}

In diesem Tutorial stammen die anfänglichen Kundendaten aus einer JSON-Datei. Die Java-App lädt die Datei, nicht der Browser. Erstelle sie daher im Verzeichnis `src/main/resources/data` und füge die folgenden Daten ein:

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

Anschließend benötigt die App eine Möglichkeit, diese Daten beim Start abzurufen. Erstelle im Verzeichnis `src/main/java/com/webforj/tutorial/config` eine Klasse `DataInitializer`. Wenn die App nun gestartet wird und keine Kunden gefunden werden, lädt sie die Kunden aus der JSON-Datei und fügt sie in die H2-Datenbank ein:

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

## Daten visuell darstellen {#displaying-data-visually}

Im letzten Teil dieses Schritts verwendest du die Komponente [`Table`](/docs/components/table/overview) und verbindest sie mit den Spring-Daten.

Eine Instanz von webforJ `Table` benötigt einen Datentyp. Dafür verwendest du die in diesem Schritt erstellte Entitätsklasse:

```java
Table<Customer> table = new Table<>();
```

Sobald du eine `Table` hast, erhält jede Kundeneigenschaft eine eigene Spalte. Verwende für jede hinzugefügte Spalte den Eigenschaftsnamen, die zugehörige Getter-Methode in der `Customer`-Entität und die Methode `setLabel()`, um die Informationen in der gewünschten Reihenfolge anzuzeigen:

```java
table.addColumn("firstName", Customer::getFirstName).setLabel("First Name");
table.addColumn("lastName", Customer::getLastName).setLabel("Last Name");
table.addColumn("company", Customer::getCompany).setLabel("Company");
table.addColumn("country", Customer::getCountry).setLabel("Country");
```

Nachdem du die Spalten hinzugefügt hast, musst du festlegen, welches Repository die `Table` zum Befüllen ihrer Daten verwenden soll. Diese App ruft das Repository über die Methode `getRepositoryAdapter()` des erstellten `CustomerService` ab:

```java
table.setRepository(customerService.getRepositoryAdapter());
```

### Tabellengröße {#table-sizing}

Mit `setSize()` kannst du die Größe der Tabelle in Pixeln oder anderen [CSS-Einheiten](https://developer.mozilla.org/en-US/docs/Learn_web_development/Core/Styling_basics/Values_and_units) festlegen. Eine maximale Breite relativ zur Bildschirmbreite sorgt dafür, dass sich deine App besser an kleinere Bildschirme anpasst.

Du kannst die Breite jeder Spalte einzeln festlegen oder eine Methode der `Table` wie `setColumnsToAutoFit()` verwenden, damit webforJ die Breiten automatisch anpasst:

```java
table.setSize("1000px", "294px");
table.setMaxWidth("90vw");
table.setColumnsToAutoFit();
```

### Benutzerinteraktionen {#user-interactions}

Die Komponente `Table` bietet außerdem Methoden, mit denen du festlegen kannst, wie Benutzer mit den Spalten interagieren:

```java
table.setColumnsToResizable(false);
table.getColumns().forEach(column -> column.setSortable(true));
```

Die hervorgehobenen Abschnitte der Klasse `Application` fügen die Komponente `Table` hinzu, definieren ihre Spalten und verwenden `CustomerService`, um das Repository abzurufen:

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

## Nächster Schritt {#next-step}

Mit diesen Änderungen lädt die App Kundendaten in die Datenbank und zeigt sie anschließend in einer `Table`-Komponente an. Im nächsten Schritt, [Routing und Composites](/docs/introduction/tutorial/routing-and-composites), lernst du Routing und mehrere Ansichten kennen, mit denen du neue Kunden hinzufügen kannst.
