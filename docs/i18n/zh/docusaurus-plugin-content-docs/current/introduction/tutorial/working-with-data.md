---
title: Working with Data
sidebar_position: 3
description: Step 2 - Use Spring to work with data.
_i18n_hash: eb93bafc77e98de6453cfb0fa0ea60a3
---
在此步骤中，您将学习如何使用 Spring 创建数据模型并以可视方式展示该数据。
到本步骤结束时，前一步创建的应用程序 [Creating a Basic App](./creating-a-basic-app) 将有一个显示客户数据的表格。跟随本教程将教您以下内容：

- Spring 注解
- 管理数据
- webforJ `Table` 组件

完成此步骤将创建一个 [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data) 的版本。

## 运行应用程序 {#running-the-app}

在开发应用程序时，您可以使用 [2-working-with-data](https://github.com/webforj/webforj-tutorial/tree/main/2-working-with-data) 作为比较。要查看应用程序的实际运行：

1. 导航到包含 `pom.xml` 文件的顶层目录，如果您正在按照 GitHub 上的版本进行操作，则该目录为 `2-working-with-data`。

2. 使用以下 Maven 命令在本地运行 Spring Boot 应用程序：
    ```bash
    mvn
    ```

运行应用程序会自动在 `http://localhost:8080` 打开一个新的浏览器。

## 依赖关系和配置 {#dependencies-and-configurations}

本教程使用 [H2 数据库](https://www.h2database.com/html/main.html)，在将来的步骤中，通过 [Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html) 使用 Jakarta 持久性 API (JPA)。这要求您在 `pom.xml` 中添加依赖并更新 `application.properties`。在整个教程的其余部分，这将是您最后一次需要修改这两个文件。

在您的 POM 中，添加以下依赖：

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

在 `application.properties` 中，位于 `src/main/resources` 目录下，添加以下内容：

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
本教程使用内存数据库和访问数据的默认凭据。请访问 Spring 的 [Data Access](https://docs.spring.io/spring-boot/how-to/data-access.html) 文档以了解特定 Spring Boot 配置选项。
:::

## Spring Beans {#spring-beans}

使用 Spring 框架的一个关键部分是理解什么是 Beans。Beans 是具有定义了 Spring 注解的对象，Spring 通过了解类的预期目的来更轻松地配置它们。请访问 Spring 的 [Bean 概述](https://docs.spring.io/spring-framework/reference/core/beans/definition.html) 文档以了解更多信息。

## 创建数据模型 {#creating-a-data-model}

在以可视方式展示或创建数据之前，本教程需要一种表示每个客户数据的方法，包括他们的姓名、国家和公司。使用 Spring，这通过具有 `@Entity` 注解的类来完成。

在 `src/main/java/com/webforj/tutorial/entity` 中创建一个名为 `Customer.java` 的类。它应该具有 `@Entity` 注解，并包括客户值的 getter 和 setter 方法，除了 `id`。为了确保每个客户都有一个唯一的 `id`，请使用 `@Id` 和 `@GeneratedValue` 注解，而不是使用创建方法来生成 `id` 值。

有了 `Customer` 数据模型，您现在可以开始为您的应用程序添加业务逻辑。

## 管理数据 {#managing-data}

在创建数据模型后，您将创建一个仓库和一个服务来管理客户数据。创建这些类型的类使您的应用程序能够执行添加、删除和更新客户记录等操作。

### 创建仓库 {#creating-a-repository}

创建一个仓库使实体的数据可访问，从而使您的应用程序能够包含多个客户。本教程的目标是使数据可编辑、可排序和可验证。您通过使用的 Spring Data 仓库来确定仓库的功能。

在将来的步骤中，您将需要访问 Spring Data JPA 以验证客户属性。因此，合适的仓库是 `JpaRepository`。

在 `src/main/java/com/webforj/tutorial/repository` 中，创建一个具有 Spring `@Repository` 注解的仓库接口并扩展 `JpaRepository`。您还需要指定此仓库中有哪些类型的实体，以及 `id` 是什么类型的对象。为了更好地扩展功能，您还应该扩展 `JpaSpecificationExecutor`。这个附加项允许您在需要时实现高级过滤选项。

```java title="CustomerRepository.java"
@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Long>,
        JpaSpecificationExecutor<Customer> {
}
```

您刚刚创建的 `CustomerRepository` 将不会有声明的方法。管理数据的方法（应用程序的业务逻辑）将位于服务类中。

:::info Spring 文档链接

以下是四个链接到 Spring 文档的资源，可以帮助您更好地理解 Spring 仓库：

- [使用 Spring Data 仓库](https://docs.spring.io/spring-data/commons/reference/repositories.html)
- [Spring Data JPA 概述](https://docs.spring.io/spring-data/jpa/reference/index.html)
- [Spring Data JPA 规范](https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html)
- [`JpaRepository`](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html)
:::

### 创建服务 {#creating-a-service}

在 `src/main/java/com/webforj/tutorial/service` 中，创建一个 `CustomerService` 类。此服务将包含使用 `CustomerRepository` 创建、更新、删除和查询客户的方法。

此外，该服务需要一种机制将 Spring Data 仓库连接到 webforJ 的 UI 组件。使用 `SpringDataRepository` webforJ 类让您创建这个桥梁。它通过允许您的 webforJ 表格和表单与您通过 Spring 管理的数据层自由工作，简化了数据绑定和 CRUD 操作。有关 webforJ 的 Spring 集成的更多信息，请参阅 [Spring Data JPA](/docs/integrations/spring/spring-data-jpa) 文章。

对于此服务类，您将使用两个 Spring 注解：

- **`@Service`** - 此注解标记一个类作为 Spring 中的服务组件，使其自动被检测并作为业务逻辑或可重用操作的 bean 进行管理。

- **`@Transactional`** - 此注解告知 Spring 在数据库事务中运行方法或类，因此所有内部操作将一起提交或回滚。Spring 的文档 [使用 @Transactional](https://docs.spring.io/spring-framework/reference/data-access/transaction/declarative/annotations.html#page-title) 提供了更多细节。

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

对于本教程，初始客户数据集来自 JSON 文件。为了防止直接通过浏览器访问，该文件资源应该创建在 `src/main/resources/static` 之外。为了方便，您可以在 `src/main/resources/data` 中创建 JSON 文件，使用以下数据：

然后，应用程序需要一种在启动时检索这些数据的方法。在 `src/main/java/com/webforj/tutorial/config` 中，创建一个 `DataInitializer` 类。现在，当应用程序运行时，如果未检测到客户，它将从 JSON 文件加载客户并将其放入 H2 数据库：

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

## 以可视方式展示数据 {#displaying-data-visually}

此步骤的最后部分是使用 [`Table`](/docs/components/table/overview) 组件并将其连接到 Spring 数据。

webforJ `Table` 的一个实例需要一个数据类型才能工作，即之前步骤中创建的实体类：

```java
Table<Customer> table = new Table<>();
```

一旦有了 `Table`，每个客户属性都将有自己的列。对于您添加的每一列，使用属性名称、其在 `Customer` 实体中的 getter 方法和 `setLabel()` 方法来按您希望的顺序显示信息：

```java
table.addColumn("firstName", Customer::getFirstName).setLabel("First Name");
table.addColumn("lastName", Customer::getLastName).setLabel("Last Name");
table.addColumn("company", Customer::getCompany).setLabel("Company");
table.addColumn("country", Customer::getCountry).setLabel("Country");
```

添加列后，您需要指定 `Table` 应该使用哪个仓库来填充其数据。该应用程序从创建的 `CustomerService` 中的 `getRepositoryAdapter()` 方法获取仓库：

```java
table.setRepository(customerService.getRepositoryAdapter());
```

### 表格大小 {#table-sizing}

对于表格，您可以使用 `setSize()` 来设置其大小（以像素或其他 [CSS 单位](https://developer.mozilla.org/en-US/docs/Learn_web_development/Core/Styling_basics/Values_and_units)）。通过设置相对于屏幕宽度的最大宽度，您帮助您的应用程序更适应较小的屏幕。

对于列，您可以单独设置宽度，或者使用 `Table` 方法之一，例如 `setColumnsToAutoFit()`，让 webforJ 为您处理宽度：

```java
table.setSize("1000px", "294px");
table.setMaxWidth("90vw");
table.setColumnsToAutoFit();
```

### 用户交互 {#user-interactions}

`Table` 组件还有方法来控制用户如何与列进行交互：

```java
table.setColumnsToResizable(false);
table.getColumns().forEach(column -> column.setSortable(true));
```

`Application` 类中突出显示的部分添加了 `Table` 组件，定义了其列，并使用 `CustomerService` 来检索仓库：

```java title="Application.java" {7-12,24-25,30-40,46-47}
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")
@AppProfile(name = "Customer Application", shortName = "CustomerApp")
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
    Paragraph tutorial = new Paragraph("Tutorial App!");
    Button btn = new Button("Info");

    // 添加 Table 组件
    Table<Customer> table = new Table<>();

    mainFrame.setWidth("fit-content");
    mainFrame.addClassName("card");

    // 样式 Table 组件，设置列，并设置仓库
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

    // 将 Table 添加到 Frame
    mainFrame.add(tutorial, btn, table);
  }

}
```

## 下一步 {#next-step}

通过这些更改，应用程序将客户数据加载到数据库中，然后在 `Table` 组件中显示它。下一步， [Routing and Composites](/docs/introduction/tutorial/routing-and-composites)，将介绍路由和多个视图，用于添加新客户。
