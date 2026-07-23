---
title: Working with Data
sidebar_position: 3
description: Step 2 - Use Spring to work with data.
_i18n_hash: 99491f42d1a848f6270686a0730d9d08
---
在本步骤中，您将学习如何使用 Spring 创建数据模型并以可视方式展示数据。在本步骤结束时，在上一步创建的应用程序 [创建基本应用](./creating-a-basic-app) 将有一个显示客户数据的表格。跟随本教程将使您了解：

- Spring 注释
- 数据管理
- webforJ `Table` 组件

完成此步骤后，您将创建一个版本的 [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data)。

## 运行应用程序 {#running-the-app}

在开发应用程序时，您可以使用 [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data) 作为比较。要查看应用程序的运行效果：

1. 导航到包含 `pom.xml` 文件的顶级目录，如果您正在跟随 GitHub 上的版本，这就是 `2-working-with-data`。

2. 使用以下 Maven 命令在本地运行 Spring Boot 应用程序：
    ```bash
    mvn
    ```

运行应用程序会自动打开一个新的浏览器窗口，地址为 `http://localhost:8080`。

## 依赖项和配置 {#dependencies-and-configurations}

本教程使用 [H2 数据库](https://www.h2database.com/html/main.html)，在后续步骤中，使用 Jakarta 持久化 API (JPA) 通过 [Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html)。这需要您向 `pom.xml` 添加依赖项并更新 `application.properties`。在本教程的剩余部分，您将最后一次需要修改这两个文件。

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

在 `application.properties` 中，位于 `src/main/resources`，添加以下内容：

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
本教程使用内存数据库和默认凭证访问数据。请参阅 Spring 的 [数据访问](https://docs.spring.io/spring-boot/how-to/data-access.html) 文档，以了解特定的 Spring Boot 配置选项。
:::

## Spring beans {#spring-beans}

使用 Spring 框架的一个关键部分是理解什么是 beans。Beans 是具有定义 Spring 注释的对象，这使得 Spring 更轻松地配置它们，知道类的预期目的。请访问 Spring 的 [Bean 概述](https://docs.spring.io/spring-framework/reference/core/beans/definition.html) 文档以了解更多信息。

## 创建数据模型 {#creating-a-data-model}

在可视化显示或创建数据之前，本教程需要一种方式来表示每个客户的数据，包括他们的名字、国家和公司。使用 Spring，这通过具有 `@Entity` 注释的类来完成。

在 `src/main/java/com/webforj/tutorial/entity` 中创建一个名为 `Customer.java` 的类。它应该具有 `@Entity` 注释，并包括客户值的 getter 和 setter 方法，`id` 除外。对于 `id` 值，使用 `@Id` 和 `@GeneratedValue` 注释来确保每个客户都获得一个唯一的 `id`。

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

有了 `Customer` 数据模型，您现在可以开始向应用程序添加业务逻辑。

## 管理数据 {#managing-data}

创建数据模型后，您将创建一个存储库和服务来管理客户数据。在应用程序中创建这些类型的类使您能够包括添加、删除和更新客户记录的操作。

### 创建存储库 {#creating-a-repository}

创建存储库使实体数据可访问，这样您的应用程序就可以包含多个客户。此教程的目标是使数据可编辑、可排序和可验证。您确定存储库的功能由您使用的 Spring Data 存储库决定。

在后续步骤 [验证和绑定数据](/docs/introduction/tutorial/validating-and-binding-data) 中，您将需要访问 Spring Data JPA 以验证客户属性。因此，适用的存储库是 `JpaRepository`。

在 `src/main/java/com/webforj/tutorial/repository` 中，创建一个带有 Spring `@Repository` 注释并扩展 `JpaRepository` 的存储库接口。您需要指定此存储库中有哪些类型的实体，以及 `id` 的类型。为了谨慎起见，还可以扩展 `JpaSpecificationExecutor`。此添加允许您在需要时实施高级筛选选项。

```java title="CustomerRepository.java"
@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Long>,
        JpaSpecificationExecutor<Customer> {
}
```

您刚创建的 `CustomerRepository` 将没有声明的方法。数据管理（应用程序的业务逻辑）的方法将在服务类中实现。

:::info Spring 文档链接

以下是四个链接到 Spring 文档，可以帮助您更好地理解 Spring 存储库：

- [使用 Spring Data 存储库](https://docs.spring.io/spring-data/commons/reference/repositories.html)
- [Spring Data JPA 概述](https://docs.spring.io/spring-data/jpa/reference/index.html)
- [Spring Data JPA 规格](https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html)
- [`JpaRepository`](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html)
:::

### 创建服务 {#creating-a-service}

在 `src/main/java/com/webforj/tutorial/service` 中，创建一个 `CustomerService` 类。该服务将包含方法来创建、更新、删除和查询客户，使用 `CustomerRepository`。

此外，此服务需要一个机制，将 Spring Data 存储库连接到 webforJ 的 UI 组件。使用 `SpringDataRepository` webforJ 类让您创建此桥梁。它通过允许您的 webforJ 表格和表单与 Spring 管理的数据层自由工作，简化数据绑定和 CRUD 操作。有关 webforJ 的 Spring 集成的更多信息，请参见 [Spring Data JPA](/docs/integrations/spring/spring-data-jpa) 文章。

对于此服务类，您将使用两个 Spring 注释：

- **`@Service`** - 这将类标记为 Spring 中的服务组件，使其被自动检测并作为商用逻辑或可重用操作的 bean 进行管理。

- **`@Transactional`** - 此注释告知 Spring 在数据库事务中运行方法或类，因此内部的所有操作均被一起提交或回滚。有关详细信息，请参见 Spring 的文档 [使用 @Transactional](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html#page-title)。

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
      throw new IllegalArgumentException("未找到 ID 为: " + customer.getId() + " 的客户");
    }
    return repository.save(customer);
  }

  public void deleteCustomer(Long id) {
    if (!repository.existsById(id)) {
      throw new IllegalArgumentException("未找到 ID 为: " + id + " 的客户");
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
        .orElseThrow(() -> new IllegalArgumentException("未找到 ID 为: " + id + " 的客户"));
  }

  public boolean doesCustomerExist(Long id) {
    return repository.existsById(id);
  }

}
```

## 加载初始数据 {#loading-initial-data}

对于本教程，初始客户数据集来自 JSON 文件。为了防止直接浏览器访问，文件资源应创建在 `src/main/resources/static` 外部。为了方便，您可以在 `src/main/resources/data` 中创建 JSON 文件，数据如下：

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

然后，您的应用程序需要一种方式在启动时检索这些数据。在 `src/main/java/com/webforj/tutorial/config` 中，创建一个 `DataInitializer` 类。现在，当应用程序运行时，如果检测到没有客户，它将从 JSON 文件加载客户，并将其放入 H2 数据库：

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

## 以可视方式显示数据 {#displaying-data-visually}

本步骤的最后部分是使用 [`Table`](/docs/components/table/overview) 组件并将其连接到 Spring 数据。

webforJ `Table` 的实例需要具有数据类型才能工作，即此步骤早些时候创建的实体类：

```java
Table<Customer> table = new Table<>();
```

一旦您拥有 `Table`，每个客户属性都将获得自己的一列。对于您添加的每个列，请使用属性名称、`Customer` 实体中的 getter 方法和 `setLabel()` 方法，以按您想要的顺序显示信息：

```java
table.addColumn("firstName", Customer::getFirstName).setLabel("名");
table.addColumn("lastName", Customer::getLastName).setLabel("姓");
table.addColumn("company", Customer::getCompany).setLabel("公司");
table.addColumn("country", Customer::getCountry).setLabel("国家");
```

添加列后，您需要指定 `Table` 应该使用哪个存储库来填充其数据。此应用程序从创建的 `CustomerService` 中的 `getRepositoryAdapter()` 方法获取存储库：

```java
table.setRepository(customerService.getRepositoryAdapter());
```

### 表格大小 {#table-sizing}

对于表格，您可以使用 `setSize()` 以像素或其他 [CSS 单位](https://developer.mozilla.org/en-US/docs/Learn_web_development/Core/Styling_basics/Values_and_units) 设置其大小。通过相对于屏幕宽度设置最大宽度，可以帮助您的应用程序更适应较小的屏幕。

对于列，您可以单独设置宽度，或者使用 `Table` 的某些方法，如 `setColumnsToAutoFit()`，让 webforJ 为您处理宽度：

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

`Application` 类中突出显示的部分添加了 `Table` 组件，定义了其列，并使用 `CustomerService` 检索存储库：

```java title="Application.java" {7-12,24-25,30-40,46-47}
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")
@AppProfile(name = "客户应用", shortName = "CustomerApp")
public class Application extends App {

  //为 CustomerService 添加构造函数注入
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
    Paragraph tutorial = new Paragraph("教程应用！");
    Button btn = new Button("信息");

    //添加 Table 组件
    Table<Customer> table = new Table<>();

    mainFrame.setWidth("fit-content");
    mainFrame.addClassName("card");

    //样式表组件，设置列和存储库
    table.setSize("1000px", "294px");
    table.setMaxWidth("90vw");
    table.addColumn("firstName", Customer::getFirstName).setLabel("名");
    table.addColumn("lastName", Customer::getLastName).setLabel("姓");
    table.addColumn("company", Customer::getCompany).setLabel("公司");
    table.addColumn("country", Customer::getCountry).setLabel("国家");
    table.setColumnsToAutoFit();
    table.setColumnsToResizable(false);
    table.getColumns().forEach(column -> column.setSortable(true));
    table.setRepository(customerService.getRepositoryAdapter());

    btn.setTheme(ButtonTheme.PRIMARY)
        .setMaxWidth(200)
        .addClickListener(e -> OptionDialog.showMessageDialog("这是一个教程！", "信息"));

    //将 Table 添加到 Frame
    mainFrame.add(tutorial, btn, table);
  }

}
```

## 下一步 {#next-step}

通过这些更改，应用程序将客户数据加载到数据库中，然后在 `Table` 组件中显示它。下一步 [路由和组合](/docs/introduction/tutorial/routing-and-composites) 将介绍路由和添加新客户的多个视图。
