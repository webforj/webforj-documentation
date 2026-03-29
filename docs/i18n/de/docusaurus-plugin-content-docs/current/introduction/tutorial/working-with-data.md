---
title: Working with Data
sidebar_position: 3
description: Step 2 - Use Spring to work with data.
_i18n_hash: eb93bafc77e98de6453cfb0fa0ea60a3
---
In diesem Schritt lernen Sie, wie Sie ein Datenmodell mit Spring erstellen und diese Daten visuell anzeigen. 
Am Ende dieses Schrittes wird die im vorherigen Schritt erstellte App, [Creating a Basic App](./creating-a-basic-app), über eine Tabelle verfügen, die Daten zu Kunden anzeigt. Mit dem folgenden Verlauf erfahren Sie mehr über:

- Spring-Anmerkungen
- Datenverwaltung
- Die webforJ `Table`-Komponente

Die Fertigstellung dieses Schrittes erstellt eine Version von [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data).

## Die App ausführen {#running-the-app}

Während Sie Ihre App entwickeln, können Sie [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data) als Vergleich verwenden. Um die App in Aktion zu sehen:

1. Navigieren Sie zum obersten Verzeichnis, das die Datei `pom.xml` enthält, dies ist `2-working-with-data`, wenn Sie der Version auf GitHub folgen.

2. Verwenden Sie den folgenden Maven-Befehl, um die Spring Boot-App lokal zu starten:
    ```bash
    mvn
    ```

Das Ausführen der App öffnet automatisch einen neuen Browser unter `http://localhost:8080`.

## Abhängigkeiten und Konfigurationen {#dependencies-and-configurations}

Dieses Tutorial verwendet [H2-Datenbank](https://www.h2database.com/html/main.html) und in einem zukünftigen Schritt die Jakarta Persistence API (JPA) über [Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html). Dies erfordert, dass Sie Abhängigkeiten zu `pom.xml` hinzufügen und `application.properties` aktualisieren. Dies wird das letzte Mal sein, dass Sie diese beiden Dateien für den Rest des Tutorials ändern müssen.

Fügen Sie in Ihrem POM die folgenden Abhängigkeiten hinzu:

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

Fügen Sie in `application.properties` im Verzeichnis `src/main/resources` Folgendes hinzu:

```
# H2-Datenbankkonfiguration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA-Konfiguration
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
```

:::info Datenzugriff
Dieses Tutorial verwendet eine In-Memory-Datenbank und die Standardanmeldeinformationen für den Datenzugriff. Gehen Sie zur Spring [Datenzugriffs](https://docs.spring.io/spring-boot/how-to/data-access.html) Dokumentation, um spezifische Spring Boot-Konfigurationsoptionen zu lernen.
:::

## Spring Beans {#spring-beans}

Ein wichtiger Teil der Verwendung des Spring-Frameworks besteht darin, zu verstehen, was Beans sind. Beans sind Objekte mit definierten Spring-Anmerkungen, die es Spring erleichtern, sie zu konfigurieren, indem es den beabsichtigten Zweck der Klasse kennt. Gehen Sie zur Spring [Bean-Übersicht](https://docs.spring.io/spring-framework/reference/core/beans/definition.html) Dokumentation, um mehr zu erfahren.

## Erstellen eines Datenmodells {#creating-a-data-model}

Bevor Sie die Daten visuell anzeigen oder erstellen, benötigt dieses Tutorial eine Möglichkeit, die Daten jedes Kunden darzustellen, einschließlich ihres Namens, Landes und Unternehmens. Dies wird mit Spring durch eine Klasse erreicht, die eine `@Entity`-Annotation hat.

Erstellen Sie eine Klasse in `src/main/java/com/webforj/tutorial/entity` mit dem Namen `Customer.java`. Sie sollte die `@Entity`-Annotation enthalten und Getter- und Setter-Methoden für die Kundenwerte besitzen, mit Ausnahme des `id`. Anstatt eine Erstellungsmetode für `id`-Werte zu verwenden, verwenden Sie die Annotations `@Id` und `@GeneratedValue`, um sicherzustellen, dass jeder Kunde eine eindeutige `id` erhält.

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

Mit dem `Customer`-Datenmodell können Sie nun beginnen, Geschäftslogik in Ihre App zu integrieren.

## Datenverwaltung {#managing-data}

Nach der Erstellung eines Datenmodells erstellen Sie ein Repository und einen Dienst, um die Kundendaten zu verwalten. Das Erstellen dieser Typen von Klassen in Ihrer App ermöglicht es Ihnen, Operationen wie das Hinzufügen, Löschen und Aktualisieren von Kundenaufzeichnungen einzuschließen.

### Erstellen eines Repositories {#creating-a-repository}

Das Erstellen eines Repositories macht die Daten der Entitäten zugänglich, damit Ihre App mehrere Kunden enthalten kann. Das Ziel dieses Tutorials ist es, die Daten editierbar, sortierbar und validierbar zu machen. Sie bestimmen die Möglichkeiten eines Repositories durch das verwendete Spring Data Repository.

In einem zukünftigen Schritt, [Validating and Binding Data](/docs/introduction/tutorial/validating-and-binding-data), werden Sie Zugriff auf Spring Data JPA benötigen, um die Eigenschaften von Kunden zu validieren. Daher ist das passende Repository, das Sie verwenden sollten, `JpaRepository`.

Erstellen Sie in `src/main/java/com/webforj/tutorial/repository` ein Repository-Interface, das die Spring `@Repository`-Annotation hat und `JpaRepository` erweitert. Sie müssen angeben, welche Art von Entitäten sich in diesem Repository befinden und welche Art von Objekt die `id` ist. Zum guten Maß erweitern Sie auch `JpaSpecificationExecutor`. Diese Ergänzung ermöglicht es Ihnen, bei Bedarf später erweiterte Filteroptionen zu implementieren.

```java title="CustomerRepository.java"
@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Long>,
        JpaSpecificationExecutor<Customer> {
}
```

Das `CustomerRepository`, das Sie gerade erstellt haben, wird keine deklarierten Methoden haben. Die Methoden zur Verwaltung der Daten (die Geschäftslogik der App) werden in einer Dienstklasse beheimatet sein.

:::info Links zur Spring-Dokumentation

Hier sind vier Links zur Spring-Dokumentation, die Ihnen helfen werden, Spring-Repositories besser zu verstehen:

- [Arbeiten mit Spring Data Repositories](https://docs.spring.io/spring-data/commons/reference/repositories.html)
- [Überblick über Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html)
- [Spring Data JPA-Spezifikationen](https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html)
- [`JpaRepository`](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html)
:::

### Erstellen eines Dienstes {#creating-a-service}

Erstellen Sie in `src/main/java/com/webforj/tutorial/service` eine Klasse `CustomerService`. Dieser Dienst wird Methoden enthalten, um Kunden zu erstellen, zu aktualisieren, zu löschen und abzufragen, indem Sie `CustomerRepository` verwenden.

Darüber hinaus benötigt dieser Dienst einen Mechanismus, um Spring Data-Repositories mit den UI-Komponenten von webforJ zu verbinden. Die Verwendung der `SpringDataRepository`-Klasse von webforJ ermöglicht es Ihnen, diese Brücke zu schaffen. Es vereinfacht die Datenbindung und CRUD-Operationen, wodurch Ihre webforJ-Tabellen und -Formulare problemlos mit Ihrer von Spring verwalteten Datenebene funktionieren. Weitere Informationen zur Spring-Integration von webforJ finden Sie im Artikel [Spring Data JPA](/docs/integrations/spring/spring-data-jpa).

Für diese Dienstklasse verwenden Sie zwei Spring-Anmerkungen:

- **`@Service`** - Dies kennzeichnet eine Klasse als Dienstkomponente in Spring, wodurch sie automatisch erkannt und als Bean für Geschäftslogik oder wiederverwendbare Operationen verwaltet wird.

- **`@Transactional`** - Diese Annotation weist Spring an, die Methode oder Klasse innerhalb einer Datenbanktransaktion auszuführen, sodass alle internen Operationen zusammen bestätigt oder zurückgerollt werden. Weitere Details finden Sie in der Spring-Dokumentation, [Verwendung von @Transactional](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html#page-title).

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
      throw new IllegalArgumentException("Kunde nicht gefunden mit ID: " + customer.getId());
    }
    return repository.save(customer);
  }

  public void deleteCustomer(Long id) {
    if (!repository.existsById(id)) {
      throw new IllegalArgumentException("Kunde nicht gefunden mit ID: " + id);
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
        .orElseThrow(() -> new IllegalArgumentException("Kunde nicht gefunden mit ID: " + id));
  }

  public boolean doesCustomerExist(Long id) {
    return repository.existsById(id);
  }

}
```

## Laden von Anfangsdaten {#loading-initial-data}

Für dieses Tutorial stammt das anfängliche Kundendatenset aus einer JSON-Datei. Um einen direkten Browserzugriff zu verhindern, sollte die Dateiressource außerhalb von `src/main/resources/static` erstellt werden. Zu Ihrer Bequemlichkeit können Sie die JSON-Datei im Verzeichnis `src/main/resources/data` mit den folgenden Daten erstellen:

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

Anschließend benötigt die App eine Möglichkeit, diese Daten beim Start abzurufen. Erstellen Sie in `src/main/java/com/webforj/tutorial/config` eine Klasse `DataInitializer`. Wenn die App ausgeführt wird und keine Kunden erkannt werden, lädt sie die Kunden aus der JSON-Datei und speichert sie in der H2-Datenbank:

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

## Daten visuell anzeigen {#displaying-data-visually}

Der letzte Teil dieses Schrittes besteht darin, die [`Table`](/docs/components/table/overview)-Komponente zu verwenden und sie mit den Spring-Daten zu verbinden.

Eine Instanz einer webforJ `Table` benötigt einen Datentyp, um zu funktionieren, das ist die zuvor in diesem Schritt erstellte Entitätsklasse:

```java
Table<Customer> table = new Table<>();
```

Sobald Sie eine `Table` haben, erhält jede Kundenproperty ihre eigene Spalte. Für jede Spalte, die Sie hinzufügen, verwenden Sie den Eigenschaftsnamen, die Getter-Methode in der `Customer`-Entität und die `setLabel()`-Methode, um die Informationen in der von Ihnen gewünschten Reihenfolge anzuzeigen:

```java
table.addColumn("firstName", Customer::getFirstName).setLabel("Vorname");
table.addColumn("lastName", Customer::getLastName).setLabel("Nachname");
table.addColumn("company", Customer::getCompany).setLabel("Unternehmen");
table.addColumn("country", Customer::getCountry).setLabel("Land");
```

Nachdem Sie die Spalten hinzugefügt haben, müssen Sie angeben, welches Repository die `Table` verwenden soll, um ihre Daten zu befüllen. Diese App erhält das Repository aus der Methode `getRepositoryAdapter()` im erstellten `CustomerService`:

```java
table.setRepository(customerService.getRepositoryAdapter());
```

### Tabellenanpassung {#table-sizing}

Für die Tabelle können Sie `setSize()` verwenden, um ihre Größe in Pixeln oder anderen [CSS-Einheiten](https://developer.mozilla.org/en-US/docs/Learn_web_development/Core/Styling_basics/Values_and_units) festzulegen. Wenn Sie eine maximale Breite im Verhältnis zur Breite des Bildschirms festlegen, helfen Sie Ihrer App, sich besser an kleinere Bildschirme anzupassen.

Für die Spalten können Sie die Breiten einzeln festlegen oder eine der Methoden `Table` wie `setColumnsToAutoFit()` verwenden, damit webforJ die Breiten für Sie verwaltet:

```java
table.setSize("1000px", "294px");
table.setMaxWidth("90vw");
table.setColumnsToAutoFit();
```

### Benutzerinteraktionen {#user-interactions}

Die `Table`-Komponente hat auch Methoden, um zu steuern, wie Benutzer mit den Spalten interagieren:

```java
table.setColumnsToResizable(false);
table.getColumns().forEach(column -> column.setSortable(true));
```

Die herausgestellten Teile der `Application`-Klasse fügen die `Table`-Komponente hinzu, definieren ihre Spalten und verwenden `CustomerService`, um das Repository abzurufen:

```java title="Application.java" {7-12,24-25,30-40,46-47}
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")
@AppProfile(name = "Customer Application", shortName = "CustomerApp")
public class Application extends App {
  
  //Fügen Sie eine Konstruktorinjektion für CustomerService hinzu
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
    Paragraph tutorial = new Paragraph("Tutorial-App!");
    Button btn = new Button("Info");

    //Fügen Sie die Table-Komponente hinzu
    Table<Customer> table = new Table<>();

    mainFrame.setWidth("fit-content");
    mainFrame.addClassName("card");

    //Gestalten Sie die Table-Komponente, legen Sie die Spalten fest und setzen Sie das Repository
    table.setSize("1000px", "294px");
    table.setMaxWidth("90vw");
    table.addColumn("firstName", Customer::getFirstName).setLabel("Vorname");
    table.addColumn("lastName", Customer::getLastName).setLabel("Nachname");
    table.addColumn("company", Customer::getCompany).setLabel("Unternehmen");
    table.addColumn("country", Customer::getCountry).setLabel("Land");
    table.setColumnsToAutoFit();
    table.setColumnsToResizable(false);
    table.getColumns().forEach(column -> column.setSortable(true));
    table.setRepository(customerService.getRepositoryAdapter());

    btn.setTheme(ButtonTheme.PRIMARY)
        .setMaxWidth(200)
        .addClickListener(e -> OptionDialog.showMessageDialog("Das ist ein Tutorial!", "Info"));

    //Fügen Sie die Tabelle zum Frame hinzu
    mainFrame.add(tutorial, btn, table);
  }

}
```

## Nächster Schritt {#next-step}

Mit diesen Änderungen lädt die App Kundendaten in die Datenbank und zeigt sie anschließend in einer `Table`-Komponente an. Der nächste Schritt, [Routing and Composites](/docs/introduction/tutorial/routing-and-composites), führt das Routing und mehrere Ansichten zum Hinzufügen neuer Kunden ein.
