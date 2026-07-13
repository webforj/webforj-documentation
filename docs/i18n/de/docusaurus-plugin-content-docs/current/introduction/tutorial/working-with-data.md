---
title: Working with Data
sidebar_position: 3
description: Step 2 - Use Spring to work with data.
_i18n_hash: 99491f42d1a848f6270686a0730d9d08
---
In diesem Schritt lernen Sie, wie man ein Datenmodell mit Spring erstellt und diese Daten visuell darstellt. Am Ende dieses Schrittes wird die im vorherigen Schritt erstellte App, [Erstellen einer grundlegenden App](./creating-a-basic-app), eine Tabelle haben, die Daten über Kunden anzeigt. Das Nachvollziehen des Vorgehens wird Ihnen Folgendes vermitteln:

- Spring-Anmerkungen
- Datenverwaltung
- Die webforJ `Table`-Komponente

Das Abschließen dieses Schrittes erstellt eine Version von [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data).

## App ausführen {#running-the-app}

Während Sie Ihre App entwickeln, können Sie [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data) als Vergleich verwenden. Um die App in Aktion zu sehen:

1. Navigieren Sie zum obersten Verzeichnis, das die `pom.xml`-Datei enthält. Dies ist `2-working-with-data`, wenn Sie dem auf GitHub verfügbaren Version folgen.

2. Verwenden Sie den folgenden Maven-Befehl, um die Spring Boot-App lokal auszuführen:
    ```bash
    mvn
    ```

Das Ausführen der App öffnet automatisch einen neuen Browser unter `http://localhost:8080`.

## Abhängigkeiten und Konfigurationen {#dependencies-and-configurations}

Dieses Tutorial verwendet die [H2-Datenbank](https://www.h2database.com/html/main.html) und in einem zukünftigen Schritt die Jakarta Persistence API (JPA) über [Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html). Dazu müssen Sie Abhängigkeiten zu `pom.xml` hinzufügen und `application.properties` aktualisieren. Dies wird das letzte Mal sein, dass Sie diese beiden Dateien für den Rest des Tutorials ändern müssen.

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

Fügen Sie in `application.properties`, in `src/main/resources`, Folgendes hinzu:

```
# H2 Datenbankkonfiguration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA-Konfiguration
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
```

:::info Datenzugriff
Dieses Tutorial verwendet eine In-Memory-Datenbank und die Standardanmeldeinformationen für den Datenzugriff. Gehen Sie zu Springs [Datenzugriffs](https://docs.spring.io/spring-boot/how-to/data-access.html) Dokumentation, um mehr über spezifische Spring Boot-Konfigurationsoptionen zu erfahren.
:::

## Spring-Besitzobjekte {#spring-beans}

Ein wichtiger Teil der Verwendung des Spring-Frameworks ist das Verständnis, was Beans sind. Beans sind Objekte mit definierten Spring-Anmerkungen, die es Spring erleichtern, sie zu konfigurieren, da der beabsichtigte Zweck der Klasse bekannt ist. Gehen Sie zur [Bean-Übersicht](https://docs.spring.io/spring-framework/reference/core/beans/definition.html) in der Spring-Dokumentation, um mehr zu erfahren.

## Erstellen eines Datenmodells {#creating-a-data-model}

Bevor die Daten visuell dargestellt oder erstellt werden können, benötigt dieses Tutorial eine Möglichkeit, die Daten jedes Kunden darzustellen, einschließlich deren Name, Land und Unternehmen. Mit Spring geschieht dies durch eine Klasse, die die Anmerkung `@Entity` hat.

Erstellen Sie eine Klasse in `src/main/java/com/webforj/tutorial/entity` mit dem Namen `Customer.java`. Sie sollte die Anmerkung `@Entity` besitzen und Getter- und Setter-Methoden für die Kundenwerte beinhalten, mit Ausnahme der `id`. Anstelle einer Erstellungsmetode für `id`-Werte sollten die Anmerkungen `@Id` und `@GeneratedValue` verwendet werden, um sicherzustellen, dass jeder Kunde eine eindeutige `id` erhält.

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

Mit dem `Customer`-Datenmodell können Sie nun beginnen, Geschäftslogik in Ihre App einzufügen.

## Datenverwaltung {#managing-data}

Nachdem Sie ein Datenmodell erstellt haben, erstellen Sie ein Repository und einen Service zur Verwaltung der Kundendaten. Diese Arten von Klassen in Ihrer App zu erstellen, ermöglicht es Ihnen, Operationen wie das Hinzufügen, Löschen und Aktualisieren von Kundenaufzeichnungen zu enthalten.

### Ein Repository erstellen {#creating-a-repository}

Ein Repository zu erstellen, macht die Daten der Entitäten zugänglich, sodass Ihre App mehrere Kunden enthalten kann. Das Ziel dieses Tutorials ist es, die Daten bearbeitbar, sortierbar und validierbar zu machen. Sie bestimmen die Fähigkeiten eines Repositories durch das Spring Data Repository, das Sie verwenden.

In einem zukünftigen Schritt, [Daten validieren und binden](/docs/introduction/tutorial/validating-and-binding-data), benötigen Sie Zugriff auf Spring Data JPA, um die Eigenschaften von Kunden zu validieren. Daher ist das passende Repository das `JpaRepository`.

Erstellen Sie in `src/main/java/com/webforj/tutorial/repository` ein Repository-Interface, das die Spring-Anmerkung `@Repository` hat und `JpaRepository` erweitert. Sie müssen angeben, welche Art von Entitäten in diesem Repository enthalten sind und welchen Typ das `id` ist. Zu Ihrer Sicherheit sollten Sie auch `JpaSpecificationExecutor` erweitern. Diese Ergänzung ermöglicht es Ihnen, später, falls erforderlich, erweiterte Filteroptionen zu implementieren.

```java title="CustomerRepository.java"
@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Long>,
        JpaSpecificationExecutor<Customer> {
}
```

Das `CustomerRepository`, das Sie gerade erstellt haben, wird keine deklarierten Methoden haben. Die Methoden zur Verwaltung der Daten (die Geschäftslogik der App) werden in einer Service-Klasse untergebracht.

:::info Links zur Spring-Dokumentation

Hier sind vier Links zur Dokumentation von Spring, die Ihnen helfen werden, Spring-Repositories besser zu verstehen:

- [Mit Spring Data Repositories arbeiten](https://docs.spring.io/spring-data/commons/reference/repositories.html)
- [Überblick über Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html)
- [Spring Data JPA-Spezifikationen](https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html)
- [`JpaRepository`](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html)
:::

### Einen Service erstellen {#creating-a-service}

Erstellen Sie in `src/main/java/com/webforj/tutorial/service` eine Klasse `CustomerService`. Dieser Service enthält Methoden, um Kunden zu erstellen, zu aktualisieren, zu löschen und nach Kunden mit `CustomerRepository` zu suchen.

Darüber hinaus benötigt dieser Service einen Mechanismus, um Spring Data Repositories mit den UI-Komponenten von webforJ zu verbinden. Die Verwendung der Klasse `SpringDataRepository` von webforJ ermöglicht es Ihnen, diese Brücke zu erstellen. Sie vereinfacht die Datenbindung und CRUD-Operationen, indem sie es Ihren webforJ-Tabellen und -Formularen ermöglicht, frei mit Ihrer von Spring verwalteten Datenschicht zu arbeiten. Weitere Informationen zur Spring-Integration von webforJ finden Sie im Artikel [Spring Data JPA](/docs/integrations/spring/spring-data-jpa).

Für diese Service-Klasse verwenden Sie zwei Spring-Anmerkungen:

- **`@Service`** - Diese kennzeichnet eine Klasse als Service-Komponente in Spring, sodass sie automatisch erkannt und als Bean für Geschäftslogik oder wiederverwendbare Operationen verwaltet wird.

- **`@Transactional`** - Diese Annotation sagt Spring, dass die Methode oder Klasse innerhalb einer Datenbanktransaktion ausgeführt werden soll, sodass alle Operationen zusammen festgeschrieben oder zurückgerollt werden. Weitere Details finden Sie in der Spring-Dokumentation, [Verwendung von @Transactional](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html#page-title).

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
      throw new IllegalArgumentException("Kunde mit ID nicht gefunden: " + customer.getId());
    }
    return repository.save(customer);
  }

  public void deleteCustomer(Long id) {
    if (!repository.existsById(id)) {
      throw new IllegalArgumentException("Kunde mit ID nicht gefunden: " + id);
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
        .orElseThrow(() -> new IllegalArgumentException("Kunde mit ID nicht gefunden: " + id));
  }

  public boolean doesCustomerExist(Long id) {
    return repository.existsById(id);
  }

}
```

## Initiale Daten laden {#loading-initial-data}

Für dieses Tutorial stammt der initiale Kundendatensatz aus einer JSON-Datei. Um den direkten Zugriff über den Browser zu verhindern, sollte die Datei außerhalb von `src/main/resources/static` erstellt werden. Zu Ihrem Vorteil können Sie die JSON-Datei in `src/main/resources/data` mit den folgenden Daten erstellen:

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

Anschließend benötigt die App eine Möglichkeit, diese Daten beim Start abzurufen. Erstellen Sie in `src/main/java/com/webforj/tutorial/config` eine `DataInitializer`-Klasse. Wenn die App ausgeführt wird und keine Kunden festgestellt werden, lädt sie die Kunden aus der JSON-Datei und fügt sie in die H2-Datenbank ein:

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

Eine Instanz einer webforJ `Table` benötigt einen Datentyp, um zu funktionieren, das ist die vorher in diesem Schritt erstellte Entitätsklasse:

```java
Table<Customer> table = new Table<>();
```

Sobald Sie ein `Table` haben, erhält jede Kundeneigenschaft ihre eigene Spalte. Für jede Spalte, die Sie hinzufügen, verwenden Sie den Eigenschaftsnamen, die Getter-Methode in der `Customer`-Entität und die Methode `setLabel()`, um die Informationen in der von Ihnen gewünschten Reihenfolge anzuzeigen:

```java
table.addColumn("firstName", Customer::getFirstName).setLabel("Vorname");
table.addColumn("lastName", Customer::getLastName).setLabel("Nachname");
table.addColumn("company", Customer::getCompany).setLabel("Unternehmen");
table.addColumn("country", Customer::getCountry).setLabel("Land");
```

Nachdem die Spalten hinzugefügt wurden, müssen Sie angeben, welches Repository die `Table` verwenden soll, um ihre Daten zu füllen. Diese App erhält das Repository von der `getRepositoryAdapter()`-Methode im erstellten `CustomerService`:

```java
table.setRepository(customerService.getRepositoryAdapter());
```

### Tabellenformatierung {#table-sizing}

Für die Tabelle können Sie `setSize()` verwenden, um ihre Größe in Pixel oder anderen [CSS-Einheiten](https://developer.mozilla.org/en-US/docs/Learn_web_development/Core/Styling_basics/Values_and_units) festzulegen. Indem Sie eine maximale Breite relativ zur Bildschirmbreite festlegen, helfen Sie Ihrer App, besser auf kleinere Bildschirme zu reagieren.

Für die Spalten können Sie die Breiten einzeln festlegen oder eine der `Table`-Methoden wie `setColumnsToAutoFit()` verwenden, um webforJ die Breiten für Sie anpassen zu lassen:

```java
table.setSize("1000px", "294px");
table.setMaxWidth("90vw");
table.setColumnsToAutoFit();
```

### Nutzerinteraktionen {#user-interactions}

Die `Table`-Komponente hat auch Methoden, um zu steuern, wie Benutzer mit den Spalten interagieren:

```java
table.setColumnsToResizable(false);
table.getColumns().forEach(column -> column.setSortable(true));
```

Die hervorgehobenen Teile der Klasse `Application` fügen die `Table`-Komponente hinzu, definieren ihre Spalten und verwenden den `CustomerService`, um das Repository abzurufen:

```java title="Application.java" {7-12,24-25,30-40,46-47}
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")
@AppProfile(name = "Kundenanwendung", shortName = "KundenApp")
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

    //Formatieren Sie die Table-Komponente, legen Sie die Spalten fest und setzen Sie das Repository
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
        .addClickListener(e -> OptionDialog.showMessageDialog("Dies ist ein Tutorial!", "Info"));

    //Fügen Sie die Tabelle zum Frame hinzu
    mainFrame.add(tutorial, btn, table);
  }

}
```

## Nächster Schritt {#next-step}

Mit diesen Änderungen lädt die App Kundendaten in die Datenbank und zeigt sie dann in einer `Table`-Komponente an. Der nächste Schritt, [Routing und Komposite](/docs/introduction/tutorial/routing-and-composites), führt in das Routing und mehrere Ansichten zum Hinzufügen neuer Kunden ein.
