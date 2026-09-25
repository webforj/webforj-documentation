---
title: Trabajando con datos
sidebar_position: 3
description: Step 2 - Use Spring to work with data.
_i18n_hash: d2e2ee90a1ad908c6884df92fb575c5b
---
En este paso, aprenderás cómo crear un modelo de datos utilizando Spring y mostrar esos datos visualmente. Al final de este paso, la aplicación creada en el paso anterior, [Creando una Aplicación Básica](/docs/introduction/tutorial/creating-a-basic-app), tendrá una tabla que muestra datos sobre los clientes. Seguir este tutorial te enseñará sobre:

- Anotaciones de Spring
- Gestión de datos
- El componente `Table` de webforJ

Completar este paso crea una versión de [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data).

## Ejecutando la aplicación {#running-the-app}

Mientras desarrollas tu aplicación, puedes usar [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data) como comparativa. Para ver la aplicación en acción:

1. Navega al directorio de nivel superior que contiene el archivo `pom.xml`, este es `2-working-with-data` si estás siguiendo la versión en GitHub.

2. Usa el siguiente comando de Maven para ejecutar la aplicación Spring Boot localmente:
    ```bash
    mvn
    ```

Ejecutar la aplicación abre automáticamente un nuevo navegador en `http://localhost:8080`.

## Dependencias y configuraciones {#dependencies-and-configurations}

Este tutorial utiliza [H2 database](https://www.h2database.com/html/main.html) y en un paso futuro, la API de Persistencia de Jakarta (JPA) a través de [Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html). Esto requiere que agregues dependencias a `pom.xml` y actualices `application.properties`. Esta será la última vez que necesitarás modificar estos dos archivos durante el resto del tutorial.

En tu POM, agrega las siguientes dependencias:

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
# Configuración de la base de datos H2
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# Configuración de JPA
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
```

:::info Accediendo a datos
Este tutorial utiliza una base de datos en memoria y las credenciales predeterminadas para acceder a los datos. Ve a la documentación de [Acceso a Datos](https://docs.spring.io/spring-boot/how-to/data-access.html) de Spring para aprender sobre las opciones de configuración específicas de Spring Boot.
:::

## Beans de Spring {#spring-beans}

Una parte clave de usar el marco Spring es comprender qué son los beans. Los beans son objetos con anotaciones de Spring definidas que facilitan que Spring los configure al conocer el propósito previsto de la clase. Ve a la documentación de [Descripción General de Beans](https://docs.spring.io/spring-framework/reference/core/beans/definition.html) de Spring para aprender más.

## Creando un modelo de datos {#creating-a-data-model}

Antes de mostrar visualmente o crear los datos, este tutorial necesita una forma de representar los datos de cada cliente, incluidos su nombre, país y empresa. Utilizando Spring, esto se hace con una clase que tiene una anotación `@Entity`.

Crea una clase en `src/main/java/com/webforj/tutorial/entity` llamada `Customer.java`. Debe tener la anotación `@Entity` e incluir métodos getter y setter para los valores del cliente, excepto para el `id`. En lugar de usar un método de creación para los valores de `id`, usa las anotaciones `@Id` y `@GeneratedValue` para garantizar que cada cliente tenga un `id` único.

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

Con el modelo de datos `Customer` en su lugar, ahora puedes comenzar a agregar lógica de negocio a tu aplicación.

## Gestionando los datos {#managing-data}

Después de crear un modelo de datos, crearás un repositorio y un servicio para gestionar los datos de los clientes. Crear este tipo de clases en tu aplicación te permite incluir operaciones como agregar, eliminar y actualizar registros de clientes.

### Creando un repositorio {#creating-a-repository}

Crear un repositorio hace que los datos de las entidades sean accesibles, por lo que tu aplicación puede contener múltiples clientes. El objetivo de este tutorial es hacer que los datos sean editables, ordenables y validables. Determinas las capacidades de un repositorio mediante el repositorio de Spring Data que uses.

En un paso futuro, [Validar y Vincular Datos](/docs/introduction/tutorial/validating-and-binding-data), necesitarás acceso a Spring Data JPA para validar las propiedades del cliente. Por lo tanto, el repositorio apropiado a utilizar es `JpaRepository`.

En `src/main/java/com/webforj/tutorial/repository`, crea una interfaz de repositorio que tenga la anotación `@Repository` de Spring y extienda `JpaRepository`. Necesitarás especificar qué tipo de entidades hay en este repositorio y qué tipo de objeto es el `id`. Para mayor seguridad, también extiende `JpaSpecificationExecutor`. Esta adición te permite implementar opciones de filtrado avanzadas más tarde, si es necesario.

```java title="CustomerRepository.java"
@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Long>,
        JpaSpecificationExecutor<Customer> {
}
```

El `CustomerRepository` que acabas de crear no tendrá métodos declarados. Los métodos para gestionar los datos (la lógica de negocio de la aplicación) residirán en una clase de servicio.

:::info Enlaces a la documentación de Spring

Aquí hay cuatro enlaces a la documentación de Spring que te ayudarán a comprender mejor los repositorios de Spring:

- [Trabajando con Repositorios de Spring Data](https://docs.spring.io/spring-data/commons/reference/repositories.html)
- [Descripción General de Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html)
- [Especificaciones de Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html)
- [`JpaRepository`](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html)
:::

### Creando un servicio {#creating-a-service}

En `src/main/java/com/webforj/tutorial/service`, crea una clase `CustomerService`. Este servicio contendrá métodos para crear, actualizar, eliminar y consultar clientes utilizando `CustomerRepository`.

Además, este servicio necesita un mecanismo para conectar los repositorios de Spring Data a los componentes de UI de webforJ. Usar la clase `SpringDataRepository` de webforJ te permite crear este puente. Simplifica la vinculación de datos y las operaciones CRUD permitiendo que tus tablas y formularios webforJ funcionen libremente con tu capa de datos gestionada por Spring. Consulta más información sobre la integración de Spring de webforJ en el artículo de [Spring Data JPA](/docs/integrations/spring/spring-data-jpa).

Para esta clase de servicio, usarás dos anotaciones de Spring:

- **`@Service`** - Esto marca una clase como componente de servicio en Spring, haciéndola automáticamente detectada y gestionada como un bean para lógica de negocio u operaciones reutilizables.

- **`@Transactional`** - Esta anotación le dice a Spring que ejecute el método o la clase dentro de una transacción de base de datos, para que todas las operaciones dentro sean confirmadas o revertidas juntas. Más detalles están disponibles en la documentación de Spring, [Uso de @Transactional](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html#page-title).

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

## Cargando datos iniciales {#loading-initial-data}

Para este tutorial, el conjunto de datos inicial de clientes proviene de un archivo JSON. La aplicación Java carga el archivo, no el navegador, así que créalo dentro de `src/main/resources/data` usando los siguientes datos:

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

Luego, la aplicación necesita una forma de recuperar estos datos cuando se inicie. En `src/main/java/com/webforj/tutorial/config`, crea una clase `DataInitializer`. Ahora, cuando la aplicación se ejecute, si no se detectan clientes, cargará los clientes desde el archivo JSON y los pondrá en la base de datos H2:

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

## Mostrando los datos visualmente {#displaying-data-visually}

La parte final de este paso es utilizar el componente [`Table`](/docs/components/table/overview) y conectarlo a los datos de Spring.

Una instancia de un `Table` de webforJ necesita tener un tipo de datos para funcionar, que es la clase de entidad creada anteriormente en este paso:

```java
Table<Customer> table = new Table<>();
```

Una vez que tengas un `Table`, cada propiedad del cliente obtiene su propia columna. Para cada columna que agregues, usa el nombre de la propiedad, su método getter en la entidad `Customer`, y el método `setLabel()` para mostrar la información en el orden que desees:

```java
table.addColumn("firstName", Customer::getFirstName).setLabel("First Name");
table.addColumn("lastName", Customer::getLastName).setLabel("Last Name");
table.addColumn("company", Customer::getCompany).setLabel("Company");
table.addColumn("country", Customer::getCountry).setLabel("Country");
```

Después de agregar las columnas, necesitas especificar qué repositorio debe usar el `Table` para rellenar sus datos. Esta aplicación obtiene el repositorio del método `getRepositoryAdapter()` en el `CustomerService` creado:

```java
table.setRepository(customerService.getRepositoryAdapter());
```

### Tamaño de la tabla {#table-sizing}

Para la tabla, puedes usar `setSize()` para establecer su tamaño en píxeles u otras [unidades CSS](https://developer.mozilla.org/en-US/docs/Learn_web_development/Core/Styling_basics/Values_and_units). Al establecer un ancho máximo relativo al ancho de la pantalla, ayudas a que tu aplicación sea más adaptable a pantallas más pequeñas.

Para las columnas, puedes establecer los anchos individualmente, o usar uno de los métodos de `Table` como `setColumnsToAutoFit()` para dejar que webforJ maneje los anchos por ti:

```java
table.setSize("1000px", "294px");
table.setMaxWidth("90vw");
table.setColumnsToAutoFit();
```

### Interacciones del usuario {#user-interactions}

El componente `Table` también tiene métodos para controlar cómo los usuarios interactúan con las columnas:

```java
table.setColumnsToResizable(false);
table.getColumns().forEach(column -> column.setSortable(true));
```

Las partes destacadas de la clase `Application` agregan el componente `Table`, definen sus columnas y usan `CustomerService` para recuperar el repositorio:

```java title="Application.java" {7-12,24-25,30-40,46-47}
@SpringBootApplication
@BundleEntry("css/card.css")
@AppTheme("system")
@AppProfile(name = "Customer Application", shortName = "CustomerApp")
public class Application extends App {

  //Agregar una inyección de constructor para CustomerService
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
    Paragraph tutorial = new Paragraph("¡Aplicación Tutorial!");
    Button btn = new Button("Información");

    //Agregar el componente Table
    Table<Customer> table = new Table<>();

    mainFrame.setWidth("fit-content");
    mainFrame.addClassName("card");

    //Estilizar el componente Table, establecer las columnas y establecer el repositorio
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
        .addClickListener(e -> OptionDialog.showMessageDialog("¡Esto es un tutorial!", "Información"));

    //Agregar la tabla al marco
    mainFrame.add(tutorial, btn, table);
  }

}
```

## Siguiente paso {#next-step}

Con estos cambios, la aplicación carga los datos de los clientes en la base de datos, luego los muestra en un componente `Table`. El siguiente paso, [Ruteo y Compuestos](/docs/introduction/tutorial/routing-and-composites), introduce el ruteo y múltiples vistas para agregar nuevos clientes.
