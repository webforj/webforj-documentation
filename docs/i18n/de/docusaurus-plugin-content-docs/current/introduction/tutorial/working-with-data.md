---
title: Working with Data
sidebar_position: 3
description: Step 2 - Use Spring to work with data.
_i18n_hash: d2e2ee90a1ad908c6884df92fb575c5b
---
In diesem Schritt lernen Sie, wie Sie ein Datenmodell mit Spring erstellen und diese Daten visuell darstellen. Am Ende dieses Schrittes wird die in dem vorherigen Schritt erstellte App, [Creating a Basic App](/docs/introduction/tutorial/creating-a-basic-app), eine Tabelle haben, die Daten über Kunden anzeigt. Das Mitverfolgen dieses Schrittes wird Ihnen Folgendes vermitteln:

- Spring Annotationen
- Datenverwaltung
- Die webforJ `Table` Komponente

Das Abschließen dieses Schrittes erstellt eine Version von [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data).

## Die App ausführen {#running-the-app}

Während Sie Ihre App entwickeln, können Sie [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data) als Vergleich verwenden. Um die App in Aktion zu sehen:

1. Navigieren Sie zum obersten Verzeichnis, das die `pom.xml`-Datei enthält. Dies ist `2-working-with-data`, wenn Sie der Version auf GitHub folgen.

2. Verwenden Sie den folgenden Maven-Befehl, um die Spring Boot-App lokal auszuführen:
    ```bash
    mvn
    ```

Die Ausführung der App öffnet automatisch einen neuen Browser unter `http://localhost:8080`.

## Abhängigkeiten und Konfigurationen {#dependencies-and-configurations}

Dieses Tutorial verwendet die [H2-Datenbank](https://www.h2database.com/html/main.html) und in einem zukünftigen Schritt die Jakarta Persistence API (JPA) über [Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html). Dazu müssen Sie Abhängigkeiten zu `pom.xml` hinzufügen und `application.properties` aktualisieren. Dies wird das letzte Mal sein, dass Sie diese beiden Dateien für den Rest des Tutorials ändern müssen.

Fügen Sie in Ihrer POM die folgenden Abhängigkeiten hinzu:

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

Fügen Sie in `application.properties`, innerhalb von `src/main/resources`, Folgendes hinzu:

```
# H2 Datenbankkonfiguration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA Konfiguration
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
```

:::info Zugriff auf Daten
Dieses Tutorial verwendet eine In-Memory-Datenbank und die Standardanmeldeinformationen für den Datenzugriff. Gehen Sie zu Springs [Data Access](https://docs.spring.io/spring-boot/how-to/data-access.html) Dokumentation, um mehr über die spezifischen Konfigurationsoptionen von Spring Boot zu erfahren.
:::

## Spring Beans {#spring-beans}

Ein wichtiger Teil der Verwendung des Spring-Frameworks ist das Verständnis, was Beans sind. Beans sind Objekte mit definierten Spring-Annotationen, die es Spring erleichtern, sie zu konfigurieren, indem sie den beabsichtigten Zweck der Klasse kennen. Gehen Sie zu Springs [Bean-Überblick](https://docs.spring.io/spring-framework/reference/core/beans/definition.html) Dokumentation, um mehr zu erfahren.

## Ein Datenmodell erstellen {#creating-a-data-model}

Bevor die Daten visuell angezeigt oder erstellt werden, benötigt dieses Tutorial eine Möglichkeit, die Daten jedes Kunden darzustellen, einschließlich ihres Namens, Landes und Unternehmens. Dies geschieht mit Spring durch eine Klasse mit einer `@Entity` Annotation.

Erstellen Sie eine Klasse in `src/main/java/com/webforj/tutorial/entity` mit dem Namen `Customer.java`. Sie sollte die `@Entity` Annotation haben und Getter- und Setter-Methoden für die Kundenwerte enthalten, mit Ausnahme von `id`. Anstelle einer Erstellungs-Methode für `id`-Werte verwenden Sie die Annotationen `@Id` und `@GeneratedValue`, um sicherzustellen, dass jeder Kunde eine eindeutige `id` erhält.

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

Mit dem `Customer` Datenmodell können Sie nun anfangen, Geschäftslogik in Ihre App hinzuzufügen.

## Daten verwalten {#managing-data}

Nachdem Sie ein Datenmodell erstellt haben, erstellen Sie ein Repository und einen Dienst, um die Kundendaten zu verwalten. Die Erstellung dieser Arten von Klassen in Ihrer App ermöglicht es Ihnen, Operationen wie das Hinzufügen, Löschen und Aktualisieren von Kundenaufzeichnungen einzuschließen.

### Ein Repository erstellen {#creating-a-repository}

Die Erstellung eines Repositories macht die Daten der Entitäten zugänglich, sodass Ihre App mehrere Kunden enthalten kann. Ziel dieses Tutorials ist es, die Daten bearbeitbar, sortierbar und validierbar zu machen. Die Fähigkeiten eines Repositories bestimmen sich durch das Spring Data-Repository, das Sie verwenden.

In einem zukünftigen Schritt, [Validating and Binding Data](/docs/introduction/tutorial/validating-and-binding-data), benötigen Sie Zugriff auf Spring Data JPA, um Kundenattribute zu validieren. Daher ist das geeignete Repository, das Sie verwenden sollten, das `JpaRepository`.

Erstellen Sie in `src/main/java/com/webforj/tutorial/repository` eine Repository-Schnittstelle, die die Spring `@Repository` Annotation hat und `JpaRepository` erweitert. Sie müssen angeben, welche Art von Entitäten in diesem Repository enthalten sind und welcher Typ das `id` sein sollte. Zu Ihrer Sicherheit erweitern Sie auch `JpaSpecificationExecutor`. Diese Ergänzung ermöglicht es Ihnen, später bei Bedarf erweiterte Filteroptionen zu implementieren.

```java
@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Long>,
        JpaSpecificationExecutor<Customer> {
}
```

Das `CustomerRepository`, das Sie gerade erstellt haben, wird keine deklarierten Methoden haben. Die Methoden zum Verwalten der Daten (der Geschäftslogik der App) finden sich in einer Dienstklasse.

:::info Links zur Spring-Dokumentation

Hier sind vier Links zu Springs Dokumentation, die Ihnen helfen, Spring-Repositories besser zu verstehen:

- [Working with Spring Data Repositories](https://docs.spring.io/spring-data/commons/reference/repositories.html)
- [Spring Data JPA Overview](https://docs.spring.io/spring-data/jpa/reference/index.html)
- [Spring Data JPA Specifications](https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html)
- [`JpaRepository`](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html)
:::

### Einen Dienst erstellen {#creating-a-service}

In `src/main/java/com/webforj/tutorial/service` erstellen Sie eine `CustomerService` Klasse. Dieser Dienst enthält Methoden zum Erstellen, Aktualisieren, Löschen und Abfragen von Kunden mit `CustomerRepository`.

Zusätzlich benötigt dieser Dienst einen Mechanismus, um Spring Data-Repositories mit den UI-Komponenten von webforJ zu verbinden. Die Verwendung der `SpringDataRepository` Klasse von webforJ ermöglicht es Ihnen, diese Brücke zu erstellen. Sie vereinfacht die Datenbindung und CRUD-Operationen, indem sie es Ihren webforJ-Tabellen und -Formularen ermöglicht, problemlos mit Ihrer von Spring verwalteten Datenebene zu arbeiten. Weitere Informationen zur Spring-Integration von webforJ finden Sie im Artikel [Spring Data JPA](/docs/integrations/spring/spring-data-jpa).

Für diese Dienstklasse verwenden Sie zwei Spring-Annotationen:

- **`@Service`** - Dadurch wird eine Klasse als Dienstkomponente in Spring markiert, sodass sie automatisch als Bean für Geschäftslogik oder wiederverwendbare Operationen erkannt und verwaltet wird.

- **`@Transactional`** - Diese Annotation sagt Spring, dass die Methode oder Klasse innerhalb einer Datenbanktransaktion ausgeführt werden soll, sodass alle Operationen zusammen verpflichtet oder zurückgerollt werden. Weitere Details finden Sie in Springs Dokumentation, [Verwendung von @Transactional](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html#page-title).

```java
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

## Initiale Daten laden {#loading-initial-data}

Für dieses Tutorial stammt der Satz anfänglicher Kundendaten aus einer JSON-Datei. Die Java-App lädt die Datei, nicht der Browser. Erstellen Sie sie in `src/main/resources/data` mit den folgenden Daten:

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

Anschließend benötigt die App eine Möglichkeit, diese Daten beim Start abzurufen. Erstellen Sie in `src/main/java/com/webforj/tutorial/config` eine `DataInitializer` Klasse. Wenn die App gestartet wird und keine Kunden erkannt werden, lädt sie Kunden aus der JSON-Datei und fügt sie in die H2-Datenbank ein:

```java
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

Der letzte Teil dieses Schrittes besteht darin, die [`Table`](/docs/components/table/overview) Komponente zu verwenden und sie mit den Spring-Daten zu verbinden.

Eine Instanz von einer webforJ `Table` benötigt einen Datentyp, um zu funktionieren. Das ist die Entitätsklasse, die zuvor in diesem Schritt erstellt wurde:

```java
Table<Customer> table = new Table<>();
```

Sobald Sie eine `Table` haben, bekommt jede Kunden-Eigenschaft ihre eigene Spalte. Für jede Spalte, die Sie hinzufügen, verwenden Sie den Eigenschaftsnamen, dessen Getter-Methode in der `Customer` Entität und die Methode `setLabel()`, um die Informationen in der Reihenfolge darzustellen, die Sie wünschen:

```java
table.addColumn("firstName", Customer::getFirstName).setLabel("Vorname");
table.addColumn("lastName", Customer::getLastName).setLabel("Nachname");
table.addColumn("company", Customer::getCompany).setLabel("Unternehmen");
table.addColumn("country", Customer::getCountry).setLabel("Land");
```

Nachdem Sie die Spalten hinzugefügt haben, müssen Sie angeben, welches Repository die `Table` verwenden soll, um ihre Daten zu befüllen. Diese App erhält das Repository von der Methode `getRepositoryAdapter()` im erstellten `CustomerService`:

```java
table.setRepository(customerService.getRepositoryAdapter());
```

### Tabellengröße {#table-sizing}

Für die Tabelle können Sie `setSize()` verwenden, um ihre Größe in Pixel oder anderen [CSS-Einheiten](https://developer.mozilla.org/en-US/docs/Learn_web_development/Core/Styling_basics/Values_and_units) festzulegen. Indem Sie eine maximale Breite relativ zur Bildschirmbreite festlegen, helfen Sie Ihrer App, sich an kleinere Bildschirme anzupassen.

Für die Spalten können Sie die Breiten einzeln festlegen oder eine der Methoden `Table` wie `setColumnsToAutoFit()` verwenden, um webforJ die Breiten für Sie einstellen zu lassen:

```java
table.setSize("1000px", "294px");
table.setMaxWidth("90vw");
table.setColumnsToAutoFit();
```

### Benutzerinteraktionen {#user-interactions}

Die `Table` Komponente hat auch Methoden, um zu steuern, wie Benutzer mit den Spalten interagieren:

```java
table.setColumnsToResizable(false);
table.getColumns().forEach(column -> column.setSortable(true));
```

Die hervorgehobenen Abschnitte der `Application` Klasse fügen die `Table` Komponente hinzu, definieren deren Spalten und verwenden `CustomerService`, um das Repository abzurufen:

```java
@SpringBootApplication
@BundleEntry("css/card.css")
@AppTheme("system")
@AppProfile(name = "Kundenanwendung", shortName = "CustomerApp")
public class Application extends App {

  //Fügen Sie eine Konstruktoreinspritzung für CustomerService hinzu
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

    //Stylen Sie die Table-Komponente, legen Sie die Spalten und das Repository fest
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

Durch diese Änderungen lädt die App Kundendaten in die Datenbank und zeigt sie dann in einer `Table` Komponente an. Der nächste Schritt, [Routing and Composites](/docs/introduction/tutorial/routing-and-composites), führt das Routing und mehrere Ansichten zum Hinzufügen neuer Kunden ein.
