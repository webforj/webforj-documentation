---
title: 使用数据
sidebar_position: 3
description: Step 2 - Use Spring to work with data.
_i18n_hash: f1c000ec07cb278db9d60d5f90d71743
---
在此步骤中，你将学习如何使用 Spring 创建数据模型，并以可视化方式显示数据。

完成此步骤后，前一步中创建的应用 [创建基本应用](/docs/introduction/tutorial/creating-a-basic-app) 将包含一个显示客户数据的表格。通过本教程，你将了解：

- Spring 注解
- 数据管理
- webforJ `Table` 组件

完成此步骤后，你将得到 [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data) 的一个版本。

<!-- <VideoPlayer video="tutorial/working-with-data" width={1920} height={1080} title="Working with data" /> -->

## 运行应用 {#running-the-app}

开发应用时，可以将 [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data) 作为对照。若要查看应用的运行效果：

1. 导航到包含 `pom.xml` 文件的顶层目录。如果你正在跟着 GitHub 上的版本操作，该目录就是 `2-working-with-data`。

2. 使用以下 Maven 命令在本地运行 Spring Boot 应用：
    ```bash
    mvn
    ```

运行应用后，系统会自动在新浏览器窗口中打开 `http://localhost:8080`。

## 依赖项和配置 {#dependencies-and-configurations}

本教程使用 [H2 数据库](https://www.h2database.com/html/main.html)，并将在后续步骤中通过
[Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html) 使用 Jakarta Persistence API (JPA)。因此，你需要在 `pom.xml` 中添加依赖项，并更新 `application.properties`。在本教程余下的部分中，你无需再修改这两个文件。

在 POM 中添加以下依赖项：

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

在 `src/main/resources` 目录下的 `application.properties` 中添加以下内容：

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

:::info 访问数据
本教程使用内存数据库和默认凭据来访问数据。请参阅 Spring 的[数据访问](https://docs.spring.io/spring-boot/how-to/data-access.html)文档，了解具体的 Spring Boot 配置选项。
:::

## Spring Bean {#spring-beans}

使用 Spring 框架的关键之一是理解 Bean 的概念。Bean 是带有特定 Spring 注解的对象，这些注解让 Spring 能够了解类的预期用途，从而更轻松地对其进行配置。请参阅 Spring 的 [Bean 概览](https://docs.spring.io/spring-framework/reference/core/beans/definition.html)文档，了解更多信息。

## 创建数据模型 {#creating-a-data-model}

在以可视化方式显示或创建数据之前，本教程需要一种表示每位客户数据的方式，包括其姓名、国家/地区和公司。使用 Spring 时，可以通过带有 `@Entity` 注解的类来实现。

在 `src/main/java/com/webforj/tutorial/entity` 中创建一个名为 `Customer.java` 的类。该类应带有 `@Entity` 注解，并包含客户各项属性的 getter 和 setter 方法，`id` 除外。不要为 `id` 值使用创建方法，而是使用 `@Id` 和 `@GeneratedValue` 注解，确保每位客户都拥有唯一的 `id`。


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

有了 `Customer` 数据模型后，就可以开始向应用添加业务逻辑了。

## 管理数据 {#managing-data}

创建数据模型后，接下来要创建一个存储库和一个服务，用于管理客户数据。在应用中创建这类类，可以添加客户记录、删除客户记录和更新客户记录等操作。

### 创建存储库 {#creating-a-repository}

创建存储库可以访问实体数据，使应用能够包含多位客户。本教程的目标是让数据可编辑、可排序并可验证。存储库具备哪些功能，取决于你使用的 Spring Data 存储库类型。

在后续步骤[验证和绑定数据](/docs/introduction/tutorial/validating-and-binding-data)中，你需要访问 Spring Data JPA，以验证客户属性。因此，适合使用的存储库是 `JpaRepository`。

在 `src/main/java/com/webforj/tutorial/repository` 中创建一个存储库接口，为其添加 Spring `@Repository` 注解，并让它扩展 `JpaRepository`。你需要指定此存储库中实体的类型，以及 `id` 的对象类型。另外，还可以扩展 `JpaSpecificationExecutor`，以便在之后需要时实现高级筛选选项。

```java title="CustomerRepository.java"
@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Long>,
        JpaSpecificationExecutor<Customer> {
}
```

刚创建的 `CustomerRepository` 不会声明任何方法。用于管理数据（即应用业务逻辑）的方法将放在服务类中。

:::info Spring 文档链接

以下四个 Spring 文档链接有助于你更好地理解 Spring 存储库：

- [使用 Spring Data 存储库](https://docs.spring.io/spring-data/commons/reference/repositories.html)
- [Spring Data JPA 概览](https://docs.spring.io/spring-data/jpa/reference/index.html)
- [Spring Data JPA 规范](https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html)
- [`JpaRepository`](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html)
:::

### 创建服务 {#creating-a-service}

在 `src/main/java/com/webforj/tutorial/service` 中创建一个 `CustomerService` 类。此服务将使用 `CustomerRepository` 提供创建、更新、删除和查询客户的方法。

此外，此服务还需要一种机制，将 Spring Data 存储库连接到 webforJ 的 UI 组件。使用 webforJ 的 `SpringDataRepository` 类即可建立这一连接。它简化了数据绑定和 CRUD 操作，让 webforJ 表格和表单能够直接使用由 Spring 管理的数据层。有关 webforJ 与 Spring 集成的更多信息，请参阅 [Spring Data JPA](/docs/integrations/spring/spring-data-jpa) 文章。

此服务类将使用两个 Spring 注解：

- **`@Service`** - 此注解将类标记为 Spring 服务组件，使其能自动被检测到，并作为用于业务逻辑或可复用操作的 Bean 进行管理。

- **`@Transactional`** - 此注解告诉 Spring 在数据库事务中运行方法或类，这样其中的所有操作要么一起提交，要么一起回滚。更多详情请参阅 Spring 文档[使用 @Transactional](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html#page-title)。


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

## 加载初始数据 {#loading-initial-data}

在本教程中，初始客户数据集来自 JSON 文件。由 Java 应用而非浏览器加载该文件，因此请在 `src/main/resources/data` 中创建文件，并使用以下数据：

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

接下来，应用需要在启动时获取这些数据。在 `src/main/java/com/webforj/tutorial/config` 中创建一个 `DataInitializer` 类。这样，应用运行时如果没有检测到任何客户，就会从 JSON 文件加载客户，并将其存入 H2 数据库：

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

## 以可视化方式显示数据 {#displaying-data-visually}

本步骤的最后一部分是使用 [`Table`](/docs/components/table/overview) 组件，并将其连接到 Spring 数据。

webforJ `Table` 实例需要指定数据类型才能正常工作；这里使用本步骤中创建的实体类：

```java
Table<Customer> table = new Table<>();
```

创建 `Table` 后，每个客户属性都会对应一个独立的列。添加每列时，使用属性名称、`Customer` 实体中的 getter 方法，以及 `setLabel()` 方法，以所需顺序显示信息：

```java
table.addColumn("firstName", Customer::getFirstName).setLabel("First Name");
table.addColumn("lastName", Customer::getLastName).setLabel("Last Name");
table.addColumn("company", Customer::getCompany).setLabel("Company");
table.addColumn("country", Customer::getCountry).setLabel("Country");
```

添加列后，需要指定 `Table` 应使用哪个存储库来填充数据。此应用通过所创建的 `CustomerService` 中的 `getRepositoryAdapter()` 方法获取存储库：

```java
table.setRepository(customerService.getRepositoryAdapter());
```

### 表格尺寸 {#table-sizing}

对于表格，可以使用 `setSize()` 以像素或其他 [CSS 单位](https://developer.mozilla.org/en-US/docs/Learn_web_development/Core/Styling_basics/Values_and_units)设置尺寸。将最大宽度设置为相对于屏幕宽度的值，有助于应用更好地适配较小的屏幕。

对于各列，可以分别设置宽度，也可以使用 `Table` 的方法之一（例如 `setColumnsToAutoFit()`），让 webforJ 自动处理列宽：

```java
table.setSize("1000px", "294px");
table.setMaxWidth("90vw");
table.setColumnsToAutoFit();
```

### 用户交互 {#user-interactions}

`Table` 组件还提供了用于控制用户与列交互方式的方法：

```java
table.setColumnsToResizable(false);
table.getColumns().forEach(column -> column.setSortable(true));
```

`Application` 类中高亮显示的部分添加了 `Table` 组件、定义了其列，并使用 `CustomerService` 获取存储库：

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

## 下一步 {#next-step}

完成这些更改后，应用会将客户数据加载到数据库中，然后在 `Table` 组件中显示数据。下一步[路由和组合组件](/docs/introduction/tutorial/routing-and-composites)将介绍路由和多个视图，以便添加新客户。
