---
title: Working with Data
sidebar_position: 3
description: Step 2 - Use Spring to work with data.
_i18n_hash: c5bf8e9751b676f3490a2f01512647ca
---
在这一步，您将学习如何使用 Spring 创建数据模型并以视觉方式展示该数据。
到这一步结束时，前一步创建的应用程序 [创建基本应用](/docs/introduction/tutorial/creating-a-basic-app) 将有一个表格来展示客户数据。跟着学习将教您：

- Spring 注解
- 管理数据
- webforJ 的 `Table` 组件

完成此步骤后，将创建一个版本的 [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data)。

## 运行应用程序 {#running-the-app}

在开发您的应用程序时，您可以使用 [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data) 作为比较。要查看应用程序的运行情况：

1. 导航到包含 `pom.xml` 文件的顶层目录，如果您正在跟随 GitHub 上的版本，此目录为 `2-working-with-data`。

2. 使用以下 Maven 命令在本地运行 Spring Boot 应用程序：
    ```bash
    mvn
    ```

运行应用程序会自动在 `http://localhost:8080` 打开一个新浏览器。

## 依赖关系和配置 {#dependencies-and-configurations}

本教程使用 [H2 数据库](https://www.h2database.com/html/main.html)，在未来的步骤中，将通过 [Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html) 使用 Jakarta 持久化 API (JPA)。这需要您在 `pom.xml` 中添加依赖项并更新 `application.properties`。在整个教程中，这将是您最后一次需要修改这两个文件。

在您的 POM 中，添加以下依赖项：

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

在 `application.properties` 中，位于 `src/main/resources` 内，添加以下内容：

```
# H2 数据库配置
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA 配置
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
```

:::info 访问数据
本教程使用内存数据库和访问数据的默认凭据。请访问 Spring 的 [数据访问](https://docs.spring.io/spring-boot/how-to/data-access.html) 文档，了解具体的 Spring Boot 配置选项。
:::

## Spring Beans {#spring-beans}

使用 Spring 框架的一个关键部分是理解 Beans 的概念。Beans是具有定义的 Spring 注解的对象，使得 Spring 更容易通过了解类的预期目的来配置它们。请访问 Spring 的 [Bean 概述](https://docs.spring.io/spring-framework/reference/core/beans/definition.html) 文档以了解更多信息。

## 创建数据模型 {#creating-a-data-model}

在以视觉方式显示或创建数据之前，本教程需要一种表示每个客户数据的方法，包括其姓名、国家和公司。使用 Spring，可以通过带有 `@Entity` 注解的类来实现。

在 `src/main/java/com/webforj/tutorial/entity` 中创建一个名为 `Customer.java` 的类。它应该带有 `@Entity` 注解并包括客户值的 getter 和 setter 方法，除了 `id`。不使用创建方法来获取 `id` 值，而是使用 `@Id` 和 `@GeneratedValue` 注解，以确保每个客户都获得唯一的 `id`。

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

有了 `Customer` 数据模型，您可以开始向应用程序中添加业务逻辑。

## 管理数据 {#managing-data}

创建完数据模型后，您将创建一个仓库和一个服务来管理客户数据。在您的应用程序中制作这些类可以让您包含添加、删除和更新客户记录等操作。

### 创建仓库 {#creating-a-repository}

创建一个仓库使实体的数据可访问，因此您的应用程序可以包含多个客户。本教程的目标是使数据可编辑、可排序和可验证。您通过使用的 Spring Data 仓库来确定仓库的能力。

在以后的步骤中，[验证和绑定数据](/docs/introduction/tutorial/validating-and-binding-data)，您需要访问 Spring Data JPA 以验证客户属性。因此，合适的仓库是 `JpaRepository`。

在 `src/main/java/com/webforj/tutorial/repository` 中，创建一个带有 Spring `@Repository` 注解并扩展 `JpaRepository` 的仓库接口。您需要指定此仓库中包含的实体类型以及 `id` 的对象类型。为了谨慎起见，还要扩展 `JpaSpecificationExecutor`。此附加功能允许您在需要时实现高级过滤选项。

```java title="CustomerRepository.java"
@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Long>,
        JpaSpecificationExecutor<Customer> {
}
```

您刚刚创建的 `CustomerRepository` 不会有声明的方法。管理数据（应用程序的业务逻辑）的方法将位于服务类中。

:::info Spring 文档链接

以下是四个指向 Spring 文档的链接，帮助您更好地理解 Spring 仓库：

- [使用 Spring Data 仓库](https://docs.spring.io/spring-data/commons/reference/repositories.html)
- [Spring Data JPA 概述](https://docs.spring.io/spring-data/jpa/reference/index.html)
- [Spring Data JPA 规范](https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html)
- [`JpaRepository`](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html)
:::

### 创建服务 {#creating-a-service}

在 `src/main/java/com/webforj/tutorial/service` 中，创建一个 `CustomerService` 类。此服务将包含用于创建、更新、删除和查询客户的方法，使用 `CustomerRepository`。

此外，此服务需要一个机制将 Spring Data 仓库连接到 webforJ 的 UI 组件。使用 `SpringDataRepository` webforJ 类使您能够创建此桥接。它通过允许您的 webforJ 表格和表单与 Spring 管理的数据层自由工作，从而简化数据绑定和 CRUD 操作。有关 webforJ 的 Spring 集成的更多信息，请参见 [Spring Data JPA](/docs/integrations/spring/spring-data-jpa) 文章。

对于这个服务类，您将使用两个 Spring 注解：

- **`@Service`** - 这将类标记为 Spring 中的服务组件，使其自动被检测并管理为商业逻辑或可重用操作的 bean。

- **`@Transactional`** - 此注解告诉 Spring 在数据库事务内运行方法或类，以便所有内部操作一起提交或回滚。更多详细信息可在 Spring 的文档中找到，[使用 @Transactional](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html#page-title)。

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
      throw new IllegalArgumentException("找不到 ID 为: " + customer.getId() + " 的客户。");
    }
    return repository.save(customer);
  }

  public void deleteCustomer(Long id) {
    if (!repository.existsById(id)) {
      throw new IllegalArgumentException("找不到 ID 为: " + id + " 的客户。");
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
        .orElseThrow(() -> new IllegalArgumentException("找不到 ID 为: " + id + " 的客户。"));
  }

  public boolean doesCustomerExist(Long id) {
    return repository.existsById(id);
  }

}
```

## 加载初始数据 {#loading-initial-data}

在本教程中，初始客户数据集来自一个 JSON 文件。Java 应用程序加载文件，而不是浏览器，因此请在 `src/main/resources/data` 中使用以下数据创建它：

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

然后，应用程序需要一种方法来检索此数据以便启动。在 `src/main/java/com/webforj/tutorial/config` 中，创建一个 `DataInitializer` 类。现在，当应用程序运行时，如果没有检测到客户，它将从 JSON 文件加载客户并将其放入 H2 数据库：

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

## 以视觉方式展示数据 {#displaying-data-visually}

该步骤的最后部分是使用 [`Table`](/docs/components/table/overview) 组件并将其连接到 Spring 数据。

webforJ `Table` 的一个实例需要有一个数据类型来工作，就是之前在此步骤中创建的实体类：

```java
Table<Customer> table = new Table<>();
```

一旦有了 `Table`，每个客户属性将拥有自己的列。对于您添加的每一列，使用属性名、`Customer` 实体中的 getter 方法和 `setLabel()` 方法以您想要的顺序显示信息：

```java
table.addColumn("firstName", Customer::getFirstName).setLabel("名字");
table.addColumn("lastName", Customer::getLastName).setLabel("姓氏");
table.addColumn("company", Customer::getCompany).setLabel("公司");
table.addColumn("country", Customer::getCountry).setLabel("国家");
```

添加完列后，您需要指定 `Table` 应该使用哪个仓库来填充其数据。该应用程序从创建的 `CustomerService` 中的 `getRepositoryAdapter()` 方法获取仓库：

```java
table.setRepository(customerService.getRepositoryAdapter());
```

### 表格大小 {#table-sizing}

对于表，您可以使用 `setSize()` 设置其大小（以像素或其他 [CSS 单位](https://developer.mozilla.org/en-US/docs/Learn_web_development/Core/Styling_basics/Values_and_units)）。通过设置相对于屏幕宽度的最大宽度，您可以帮助应用程序更好地适应较小屏幕。

对于列，您可以单独设置宽度，或使用 `Table` 的方法之一，如 `setColumnsToAutoFit()`，让 webforJ 为您处理宽度：

```java
table.setSize("1000px", "294px");
table.setMaxWidth("90vw");
table.setColumnsToAutoFit();
```

### 用户交互 {#user-interactions}

`Table` 组件还具有控制用户如何与列交互的方法：

```java
table.setColumnsToResizable(false);
table.getColumns().forEach(column -> column.setSortable(true));
```

`Application` 类中的高亮部分添加了 `Table` 组件，定义了其列，并使用 `CustomerService` 检索仓库：

```java title="Application.java" {7-12,24-25,30-40,46-47}
@SpringBootApplication
@BundleEntry("css/card.css")
@AppTheme("system")
@AppProfile(name = "客户应用程序", shortName = "客户应用")
public class Application extends App {

  // 添加 CustomerService 的构造函数注入
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
    Paragraph tutorial = new Paragraph("教程应用程序！");
    Button btn = new Button("信息");

    // 添加 Table 组件
    Table<Customer> table = new Table<>();

    mainFrame.setWidth("fit-content");
    mainFrame.addClassName("card");

    // 样式设置 Table 组件，设置列，并设置仓库
    table.setSize("1000px", "294px");
    table.setMaxWidth("90vw");
    table.addColumn("firstName", Customer::getFirstName).setLabel("名字");
    table.addColumn("lastName", Customer::getLastName).setLabel("姓氏");
    table.addColumn("company", Customer::getCompany).setLabel("公司");
    table.addColumn("country", Customer::getCountry).setLabel("国家");
    table.setColumnsToAutoFit();
    table.setColumnsToResizable(false);
    table.getColumns().forEach(column -> column.setSortable(true));
    table.setRepository(customerService.getRepositoryAdapter());

    btn.setTheme(ButtonTheme.PRIMARY)
        .setMaxWidth(200)
        .addClickListener(e -> OptionDialog.showMessageDialog("这是一个教程！", "信息"));

    // 将 Table 添加到 Frame
    mainFrame.add(tutorial, btn, table);
  }

}
```

## 下一步 {#next-step}

通过这些更改，应用程序加载客户数据到数据库中，然后在 `Table` 组件中展示它。下一步 [路由和组合](/docs/introduction/tutorial/routing-and-composites) 将介绍路由和多个视图以添加新客户。
