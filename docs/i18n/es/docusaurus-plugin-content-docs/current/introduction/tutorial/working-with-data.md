---
title: Trabajando con datos
sidebar_position: 3
description: Step 2 - Use Spring to work with data.
_i18n_hash: f1c000ec07cb278db9d60d5f90d71743
---
En este paso, aprenderás a crear un modelo de datos con Spring y a mostrar esos datos visualmente.
Al terminar este paso, la aplicación creada en el paso anterior, [Crear una aplicación básica](/docs/introduction/tutorial/creating-a-basic-app), tendrá una tabla que muestra información sobre clientes. Al seguir las instrucciones, aprenderás sobre:

- Anotaciones de Spring
- Gestión de datos
- El componente `Table` de webforJ

Al completar este paso, crearás una versión de [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data).

<!-- <VideoPlayer video="tutorial/working-with-data" width={1920} height={1080} title="Working with data" /> -->

## Ejecutar la aplicación {#running-the-app}

Mientras desarrollas la aplicación, puedes usar [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data) como referencia. Para ver la aplicación en acción:

1. Ve al directorio de nivel superior que contiene el archivo `pom.xml`. Si sigues las instrucciones con la versión de GitHub, este directorio es `2-working-with-data`.

2. Usa el siguiente comando de Maven para ejecutar la aplicación Spring Boot de forma local:
    ```bash
    mvn
    ```

Al ejecutar la aplicación, se abrirá automáticamente una nueva ventana del navegador en `http://localhost:8080`.

## Dependencias y configuraciones {#dependencies-and-configurations}

Este tutorial usa [H2 database](https://www.h2database.com/html/main.html) y, en un paso posterior, la Jakarta Persistence API (JPA) mediante
[Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html). Para ello, debes agregar dependencias a `pom.xml` y actualizar `application.properties`. Esta será la última vez que tendrás que modificar estos dos archivos durante el resto del tutorial.

En el POM, agrega las siguientes dependencias:

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

En `application.properties`, dentro de `src/main/resources`, agrega lo siguiente:

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

:::info Acceder a los datos
Este tutorial usa una base de datos en memoria y las credenciales predeterminadas para acceder a los datos. Consulta la documentación de Spring sobre [Acceso a datos](https://docs.spring.io/spring-boot/how-to/data-access.html) para conocer opciones de configuración específicas de Spring Boot.
:::

## Beans de Spring {#spring-beans}

Una parte clave del uso del framework Spring es entender qué son los beans. Los beans son objetos con anotaciones de Spring definidas que facilitan la configuración de Spring, ya que indican el propósito previsto de la clase. Consulta la documentación de Spring sobre la [Introducción a los beans](https://docs.spring.io/spring-framework/reference/core/beans/definition.html) para obtener más información.

## Crear un modelo de datos {#creating-a-data-model}

Antes de mostrar o crear los datos visualmente, este tutorial necesita una forma de representar los datos de cada cliente, incluidos su nombre, país y empresa. Con Spring, esto se hace mediante una clase que lleva la anotación `@Entity`.

Crea una clase llamada `Customer.java` en `src/main/java/com/webforj/tutorial/entity`. Debe llevar la anotación `@Entity` e incluir métodos getter y setter para los valores del cliente, excepto para `id`. En lugar de usar un método de creación para los valores de `id`, usa las anotaciones `@Id` y `@GeneratedValue` para garantizar que cada cliente tenga un `id` único.


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

Con el modelo de datos `Customer` listo, ya puedes empezar a agregar lógica de negocio a la aplicación.

## Gestionar datos {#managing-data}

Después de crear un modelo de datos, crearás un repositorio y un servicio para gestionar los datos de los clientes. Al crear este tipo de clases en la aplicación, podrás incluir operaciones como agregar, eliminar y actualizar registros de clientes.

### Crear un repositorio {#creating-a-repository}

Crear un repositorio permite acceder a los datos de las entidades para que la aplicación pueda contener varios clientes. El objetivo de este tutorial es que los datos se puedan editar, ordenar y validar. Las capacidades de un repositorio dependen del repositorio de Spring Data que uses.

En un paso posterior, [Validar y enlazar datos](/docs/introduction/tutorial/validating-and-binding-data), necesitarás acceso a Spring Data JPA para validar las propiedades de los clientes. Por lo tanto, el repositorio adecuado es `JpaRepository`.

En `src/main/java/com/webforj/tutorial/repository`, crea una interfaz de repositorio que tenga la anotación de Spring `@Repository` y extienda `JpaRepository`. Deberás especificar qué tipo de entidades contiene el repositorio y qué tipo de objeto es el `id`. Para completar la configuración, extiende también `JpaSpecificationExecutor`. Esto te permitirá implementar opciones de filtrado avanzadas más adelante, si es necesario.

```java title="CustomerRepository.java"
@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Long>,
        JpaSpecificationExecutor<Customer> {
}
```

El `CustomerRepository` que acabas de crear no tendrá métodos declarados. Los métodos para gestionar los datos (la lógica de negocio de la aplicación) estarán en una clase de servicio.

:::info Enlaces a la documentación de Spring

Aquí tienes cuatro enlaces a la documentación de Spring que te ayudarán a comprender mejor los repositorios de Spring:

- [Trabajar con repositorios de Spring Data](https://docs.spring.io/spring-data/commons/reference/repositories.html)
- [Introducción a Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html)
- [Especificaciones de Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html)
- [`JpaRepository`](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html)
:::

### Crear un servicio {#creating-a-service}

En `src/main/java/com/webforj/tutorial/service`, crea una clase `CustomerService`. Este servicio contendrá métodos para crear, actualizar, eliminar y consultar clientes mediante `CustomerRepository`.

Además, este servicio necesita un mecanismo para conectar los repositorios de Spring Data con los componentes de interfaz de usuario de webforJ. La clase `SpringDataRepository` de webforJ permite crear este puente. Simplifica el enlace de datos y las operaciones CRUD al permitir que las tablas y los formularios de webforJ trabajen directamente con la capa de datos gestionada por Spring. Consulta el artículo [Spring Data JPA](/docs/integrations/spring/spring-data-jpa) para obtener más información sobre la integración de webforJ con Spring.

Para esta clase de servicio, usarás dos anotaciones de Spring:

- **`@Service`**: indica que una clase es un componente de servicio en Spring, por lo que se detecta y gestiona automáticamente como un bean para la lógica de negocio o las operaciones reutilizables.

- **`@Transactional`**: esta anotación indica a Spring que ejecute el método o la clase dentro de una transacción de base de datos, de modo que todas las operaciones se confirmen o reviertan juntas. Encontrarás más detalles en la documentación de Spring, [Usar @Transactional](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html#page-title).


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

## Cargar datos iniciales {#loading-initial-data}

En este tutorial, el conjunto inicial de datos de clientes proviene de un archivo JSON. La aplicación Java carga el archivo, no el navegador, así que créalo dentro de `src/main/resources/data` con los siguientes datos:

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

A continuación, la aplicación necesita una forma de recuperar estos datos al iniciarse. En `src/main/java/com/webforj/tutorial/config`, crea una clase `DataInitializer`. Así, cuando se ejecute la aplicación y no se detecten clientes, se cargarán los clientes del archivo JSON y se guardarán en la base de datos H2:

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

## Mostrar datos visualmente {#displaying-data-visually}

La última parte de este paso consiste en usar el componente [`Table`](/docs/components/table/overview) y conectarlo a los datos de Spring.

Una instancia de `Table` de webforJ necesita un tipo de datos para funcionar: la clase de entidad creada anteriormente en este paso.

```java
Table<Customer> table = new Table<>();
```

Una vez que tengas una `Table`, cada propiedad del cliente tendrá su propia columna. Para agregar cada columna, usa el nombre de la propiedad, su método getter en la entidad `Customer` y el método `setLabel()` para mostrar la información en el orden que desees:

```java
table.addColumn("firstName", Customer::getFirstName).setLabel("First Name");
table.addColumn("lastName", Customer::getLastName).setLabel("Last Name");
table.addColumn("company", Customer::getCompany).setLabel("Company");
table.addColumn("country", Customer::getCountry).setLabel("Country");
```

Después de agregar las columnas, debes especificar qué repositorio debe usar `Table` para cargar los datos. Esta aplicación obtiene el repositorio del método `getRepositoryAdapter()` de la clase `CustomerService` que creaste:

```java
table.setRepository(customerService.getRepositoryAdapter());
```

### Ajustar el tamaño de la tabla {#table-sizing}

Para la tabla, puedes usar `setSize()` para establecer su tamaño en píxeles u otras [unidades CSS](https://developer.mozilla.org/en-US/docs/Learn_web_development/Core/Styling_basics/Values_and_units). Al establecer un ancho máximo relativo al ancho de la pantalla, ayudas a que la aplicación se adapte mejor a pantallas más pequeñas.

Puedes establecer los anchos de las columnas individualmente o usar uno de los métodos de `Table`, como `setColumnsToAutoFit()`, para que webforJ los ajuste automáticamente:

```java
table.setSize("1000px", "294px");
table.setMaxWidth("90vw");
table.setColumnsToAutoFit();
```

### Interacciones del usuario {#user-interactions}

El componente `Table` también tiene métodos para controlar cómo interactúan los usuarios con las columnas:

```java
table.setColumnsToResizable(false);
table.getColumns().forEach(column -> column.setSortable(true));
```

Las secciones resaltadas de la clase `Application` agregan el componente `Table`, definen sus columnas y usan `CustomerService` para recuperar el repositorio:

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

## Paso siguiente {#next-step}

Con estos cambios, la aplicación carga los datos de los clientes en la base de datos y luego los muestra en un componente `Table`. El siguiente paso, [Enrutamiento y componentes compuestos](/docs/introduction/tutorial/routing-and-composites), presenta el enrutamiento y varias vistas para agregar nuevos clientes.
