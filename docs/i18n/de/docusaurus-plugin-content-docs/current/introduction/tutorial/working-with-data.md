---
title: Working with Data
sidebar_position: 3
description: Step 2 - Use Spring to work with data.
_i18n_hash: c5bf8e9751b676f3490a2f01512647ca
---
In diesem Schritt lernen Sie, wie Sie ein Datenmodell mit Spring erstellen und diese Daten visuell anzeigen. Am Ende dieses Schrittes wird die im vorherigen Schritt erstellte App, [Creating a Basic App](/docs/introduction/tutorial/creating-a-basic-app), eine Tabelle haben, die Daten über Kunden anzeigt. Das Mitverfolgen wird Ihnen Folgendes beibringen:

- Spring-Anmerkungen
- Datenverwaltung
- Die webforJ `Table`-Komponente

Der Abschluss dieses Schrittes erstellt eine Version von [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data).

## App ausführen {#running-the-app}

Während Sie Ihre App entwickeln, können Sie [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data) als Vergleich verwenden. Um die App in Aktion zu sehen:

1. Navigieren Sie zum übergeordneten Verzeichnis, das die `pom.xml`-Datei enthält. Dies ist `2-working-with-data`, wenn Sie mit der Version auf GitHub folgen.

2. Verwenden Sie den folgenden Maven-Befehl, um die Spring Boot App lokal auszuführen:
    ```bash
    mvn
    ```

Die Ausführung der App öffnet automatisch einen neuen Browser unter `http://localhost:8080`.

## Abhängigkeiten und Konfigurationen {#dependencies-and-configurations}

Dieses Tutorial verwendet [H2-Datenbank](https://www.h2database.com/html/main.html) und in einem zukünftigen Schritt die Jakarta Persistence API (JPA) über
[Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html). Dazu müssen Sie Abhängigkeiten zu `pom.xml` hinzufügen und `application.properties` aktualisieren. Dies wird das letzte Mal sein, dass Sie diese beiden Dateien im Rest des Tutorials ändern müssen.

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

Fügen Sie in `application.properties`, innerhalb von `src/main/resources`, Folgendes hinzu:

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
Dieses Tutorial verwendet eine In-Memory-Datenbank und die Standardanmeldeinformationen zum Zugriff auf Daten. Besuchen Sie die [Datenzugriffs](https://docs.spring.io/spring-boot/how-to/data-access.html)-Dokumentation von Spring, um mehr über spezifische Spring Boot-Konfigurationsoptionen zu erfahren.
:::

## Spring-Beans {#spring-beans}

Ein wichtiger Teil der Verwendung des Spring-Frameworks ist das Verständnis, was Beans sind. Beans sind Objekte mit definierten Spring-Anmerkungen, die es Spring erleichtern, sie zu konfigurieren, indem sie den beabsichtigten Zweck der Klasse kennen. Besuchen Sie die [Bean-Übersicht](https://docs.spring.io/spring-framework/reference/core/beans/definition.html)-Dokumentation von Spring, um mehr zu erfahren.

## Erstellen eines Datenmodells {#creating-a-data-model}

Bevor die Daten visuell angezeigt oder erstellt werden, benötigt dieses Tutorial eine Möglichkeit, die Daten jedes Kunden darzustellen, einschließlich deren Namen, Land und Firma. Dies wird in Spring mit einer Klasse erreicht, die eine `@Entity`-Anmerkung hat.

Erstellen Sie eine Klasse in `src/main/java/com/webforj/tutorial/entity` mit dem Namen `Customer.java`. Sie sollte die `@Entity`-Anmerkung haben und Getter- und Setter-Methoden für die Kundenwerte enthalten, mit Ausnahme von `id`. Anstelle einer Methode zur Erstellung von `id`-Werten verwenden Sie die Anmerkungen `@Id` und `@GeneratedValue`, um sicherzustellen, dass jeder Kunde eine eindeutige `id` erhält.

Mit dem `Customer`-Datenmodell können Sie nun beginnen, Geschäftslogik in Ihre App einzufügen.

## Daten verwalten {#managing-data}

Nach der Erstellung eines Datenmodells erstellen Sie ein Repository und einen Service, um die Kundendaten zu verwalten. Das Erstellen dieser Typen von Klassen in Ihrer App ermöglicht es Ihnen, Operationen wie das Hinzufügen, Löschen und Aktualisieren von Kundenaufzeichnungen einzuschließen.

### Repository erstellen {#creating-a-repository}

Ein Repository zu erstellen, macht die Daten der Entitäten zugänglich, sodass Ihre App mehrere Kunden enthalten kann. Ziel dieses Tutorials ist es, die Daten bearbeitbar, sortierbar und validierbar zu machen. Die Fähigkeiten eines Repositories bestimmen Sie durch das verwendete Spring Data-Repository.

In einem zukünftigen Schritt, [Validating and Binding Data](/docs/introduction/tutorial/validating-and-binding-data), benötigen Sie Zugriff auf Spring Data JPA, um die Eigenschaften von Kunden zu validieren. Daher ist das geeignete Repository, das zu verwenden ist, das `JpaRepository`.

Erstellen Sie in `src/main/java/com/webforj/tutorial/repository` ein Repository-Interface, das die Spring `@Repository`-Anmerkung hat und `JpaRepository` erweitert. Sie müssen angeben, um welchen Typ von Entitäten es sich in diesem Repository handelt und welchen Typ von Objekt die `id` ist. Um es abzurunden, erweitern Sie auch `JpaSpecificationExecutor`. Diese Ergänzung ermöglicht es Ihnen, später, wenn nötig, erweiterte Filteroptionen umzusetzen.

```java title="CustomerRepository.java"
@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Long>,
        JpaSpecificationExecutor<Customer> {
}
```

Das `CustomerRepository`, das Sie gerade erstellt haben, hat keine deklarierten Methoden. Die Methoden zur Verwaltung der Daten (der Geschäftslogik der App) werden in einer Serviceklasse untergebracht.

:::info Links zur Spring-Dokumentation

Hier sind vier Links zur Dokumentation von Spring, die Ihnen helfen werden, Spring-Repositories besser zu verstehen:

- [Arbeiten mit Spring Data-Repositories](https://docs.spring.io/spring-data/commons/reference/repositories.html)
- [Spring Data JPA Überblick](https://docs.spring.io/spring-data/jpa/reference/index.html)
- [Spring Data JPA-Spezifikationen](https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html)
- [`JpaRepository`](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html)
:::

### Service erstellen {#creating-a-service}

Erstellen Sie in `src/main/java/com/webforj/tutorial/service` eine `CustomerService`-Klasse. Dieser Service enthält Methoden zum Erstellen, Aktualisieren, Löschen und Abfragen von Kunden mit dem `CustomerRepository`.

Darüber hinaus benötigt dieser Service einen Mechanismus, um Spring Data-Repositories mit den UI-Komponenten von webforJ zu verbinden. Die Verwendung der `SpringDataRepository`-Klasse von webforJ ermöglicht es Ihnen, diese Brücke zu schaffen. Sie vereinfacht die Datenbindung und CRUD-Operationen, indem sie es Ihren webforJ-Tabellen und -Formularen ermöglicht, frei mit Ihrer von Spring verwalteten Datenschicht zu interagieren. Weitere Informationen zur Spring-Integration von webforJ finden Sie im Artikel [Spring Data JPA](/docs/integrations/spring/spring-data-jpa).

Für diese Serviceklasse verwenden Sie zwei Spring-Anmerkungen:

- **`@Service`** - Dies kennzeichnet eine Klasse als Servicekomponente in Spring, sodass sie automatisch als Bean für Geschäftslogik oder wiederverwendbare Operationen erkannt und verwaltet wird.

- **`@Transactional`** - Diese Anmerkung weist Spring an, die Methode oder Klasse innerhalb einer Datenbanktransaktion auszuführen, sodass alle darin enthaltenen Operationen zusammen committet oder zurückgerollt werden. Weitere Details finden Sie in der Spring-Dokumentation, [Verwendung von @Transactional](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html#page-title).

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

## Initialdaten laden {#loading-initial-data}

Für dieses Tutorial stammt der initiale Kundendatensatz aus einer JSON-Datei. Die Java-App lädt die Datei, nicht der Browser, also erstellen Sie sie in `src/main/resources/data` mit den folgenden Daten:

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

Nun benötigt die App eine Möglichkeit, diese Daten beim Start abzurufen. Erstellen Sie in `src/main/java/com/webforj/tutorial/config` eine `DataInitializer`-Klasse. Wenn die App läuft, wird sie, wenn keine Kunden erkannt werden, die Kunden aus der JSON-Datei laden und in die H2-Datenbank einfügen:

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

Eine Instanz einer webforJ `Table` benötigt einen Datentyp, damit sie funktioniert, das ist die zuvor in diesem Schritt erstellte Entitätsklasse:

```java
Table<Customer> table = new Table<>();
```

Sobald Sie eine `Table` haben, erhält jede Kunden-Eigenschaft ihre eigene Spalte. Für jede Spalte, die Sie hinzufügen, verwenden Sie den Eigenschaftsnamen, die Getter-Methode in der `Customer`-Entität und die `setLabel()`-Methode, um die Informationen in der gewünschten Reihenfolge anzuzeigen:

```java
table.addColumn("firstName", Customer::getFirstName).setLabel("Vorname");
table.addColumn("lastName", Customer::getLastName).setLabel("Nachname");
table.addColumn("company", Customer::getCompany).setLabel("Firma");
table.addColumn("country", Customer::getCountry).setLabel("Land");
```

Nachdem Sie die Spalten hinzugefügt haben, müssen Sie angeben, welches Repository die `Table` verwenden soll, um ihre Daten zu befüllen. Diese App erhält das Repository über die `getRepositoryAdapter()`-Methode im erstellten `CustomerService`:

```java
table.setRepository(customerService.getRepositoryAdapter());
```

### Tabellengrößen {#table-sizing}

Für die Tabelle können Sie `setSize()` verwenden, um ihre Größe in Pixeln oder anderen [CSS-Einheiten](https://developer.mozilla.org/en-US/docs/Learn_web_development/Core/Styling_basics/Values_and_units) festzulegen. Indem Sie eine maximale Breite relativ zur Bildschirmbreite festlegen, helfen Sie Ihrer App, sich an kleinere Bildschirme anzupassen.

Für die Spalten können Sie die Breiten individuell festlegen oder eine der Methoden der `Table` wie `setColumnsToAutoFit()` verwenden, um die Breiten von webforJ automatisch verwalten zu lassen:

```java
table.setSize("1000px", "294px");
table.setMaxWidth("90vw");
table.setColumnsToAutoFit();
```

### Benutzerinteraktionen {#user-interactions}

Die `Table`-Komponente hat auch Methoden zur Steuerung, wie Benutzer mit den Spalten interagieren können:

```java
table.setColumnsToResizable(false);
table.getColumns().forEach(column -> column.setSortable(true));
```

Die hervorgehobenen Teile der `Application`-Klasse fügen die `Table`-Komponente hinzu, definieren ihre Spalten und verwenden den `CustomerService`, um das Repository abzurufen:

```java title="Application.java" {7-12,24-25,30-40,46-47}
@SpringBootApplication
@BundleEntry("css/card.css")
@AppTheme("system")
@AppProfile(name = "Kundenanwendung", shortName = "CustomerApp")
public class Application extends App {

  // Fügen Sie eine Konstruktorinjektion für CustomerService hinzu
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

    // Fügen Sie die Table-Komponente hinzu
    Table<Customer> table = new Table<>();

    mainFrame.setWidth("fit-content");
    mainFrame.addClassName("card");

    // Stylen Sie die Table-Komponente, setzen Sie die Spalten und setzen Sie das Repository
    table.setSize("1000px", "294px");
    table.setMaxWidth("90vw");
    table.addColumn("firstName", Customer::getFirstName).setLabel("Vorname");
    table.addColumn("lastName", Customer::getLastName).setLabel("Nachname");
    table.addColumn("company", Customer::getCompany).setLabel("Firma");
    table.addColumn("country", Customer::getCountry).setLabel("Land");
    table.setColumnsToAutoFit();
    table.setColumnsToResizable(false);
    table.getColumns().forEach(column -> column.setSortable(true));
    table.setRepository(customerService.getRepositoryAdapter());

    btn.setTheme(ButtonTheme.PRIMARY)
        .setMaxWidth(200)
        .addClickListener(e -> OptionDialog.showMessageDialog("Das ist ein Tutorial!", "Info"));

    // Fügen Sie die Table zum Frame hinzu
    mainFrame.add(tutorial, btn, table);
  }

}
```

## Nächster Schritt {#next-step}

Mit diesen Änderungen lädt die App Kundendaten in die Datenbank und zeigt sie in einer `Table`-Komponente an. Der nächste Schritt, [Routing and Composites](/docs/introduction/tutorial/routing-and-composites), führt in das Routing und mehrere Ansichten zur Hinzufügung neuer Kunden ein.
