---
title: Working With Data
sidebar_position: 3
_i18n_hash: 3afbf6e4eb4921183cc11d87c8457150
---
此步骤专注于向演示应用程序添加数据管理和显示功能。为此，将创建关于各种 `Customer` 对象的虚拟数据，并更新应用程序以处理这些数据并在添加到先前应用程序中的 [`Table`](../../components/table/overview) 中展示。

将概述创建 `Customer` 模型类，并将其与 `Service` 类集成，以使用存储库的实现访问和管理所需的数据。然后，将详细说明如何使用检索到的数据在应用程序中实现 `Table` 组件，以交互和结构化的格式显示客户信息。

到本步骤结束时，在 [前一步骤](./creating-a-basic-app) 中创建的应用程序将显示一个包含创建数据的表格，随后可以在接下来的步骤中扩展。要运行此应用程序：

- 转到 `2-working-with-data` 目录
- 运行 `mvn jetty:run`

<!-- vale off -->

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/working-with-data.mp4" type="video/mp4"/>
  </video>
</div>

<!-- vale on -->

## 创建数据模型 {#creating-a-data-model}

为了创建一个在主应用程序中显示数据的 `Table`，需要创建一个可以与 `Table` 一起使用以显示数据的 Java bean 类。

在本程序中，位于 `src/main/java/com/webforj/demos/data/Customer.java` 的 `Customer` 类做到了这一点。此类作为应用程序的核心数据模型，封装了与客户相关的属性，如 `firstName`、`lastName`、`company` 和 `country`。该模型还将包含一个唯一的 ID。

```java title="Customer.java"
public class Customer implements HasEntityKey {
  private String firstName = "";
  private String lastName = "";
  private String company = "";
  private Country country = Country.UNKNOWN;
  private UUID uuid = UUID.randomUUID();

  public enum Country {

    @SerializedName("Germany")
    GERMANY,

    // 其余国家
  }

    // Getter 和 Setter

  @Override
  public Object getEntityKey() {
    return uuid;
  }
}
```

:::info 使用 `HasEntityKey` 进行唯一标识符

实现 `HasEntityKey` 接口对于管理与 `Table` 一起使用的模型中的唯一标识符至关重要。它确保模型的每个实例都有一个唯一的密钥，使 `Table` 能有效识别和管理行。

在此演示中，`getEntityKey()` 方法返回每个客户的 UUID，以确保唯一识别。虽然在这里使用 UUID 为了简便，在实际应用中，数据库主键通常是生成唯一密钥的更好选择。

如果不实现 `HasEntityKey`，`Table` 将默认使用 Java 哈希代码作为密钥。由于哈希代码不能保证唯一性，这可能在管理 `Table` 中的行时造成冲突。
:::

有了 `Customer` 数据模型，下一步是在应用程序中管理和组织这些模型。

## 创建 `Service` 类 {#creating-a-service-class}

作为集中数据管理者，`Service` 类不仅加载 `Customer` 数据，还提供了高效的访问和交互接口。

在 `src/main/java/com/webforj/demos/data` 中创建 `Service.java` 类。`Service` 作为共享资源，允许感兴趣的方轻松检索和交互数据，而无需在组件或类之间手动传递数据。

在本演示中，`Service` 类从位于 `src/main/resources/data/customers.json` 的 JSON 文件读取客户数据。数据映射到 `Customer` 对象并存储在 `ArrayList` 中，这构成了表格 `Repository` 的基础。

在 webforJ 中，`Repository` 类提供了一种结构化的方式来管理和检索实体集合。它作为您的应用程序与数据之间的接口，提供查询、计数和刷新数据的方法，同时保持干净和一致的结构。`Table` 类用于展示存储在其中的数据。

尽管 `Repository` 不包括用于更新或删除实体的方法，但它作为对象集合的结构化包装，适合提供组织良好、高效的数据访问。

```java
public class Service {
  private List<Customer> data = new ArrayList<>();
  private CollectionRepository<Customer> repository;

  private Service() {
    data = buildDemoList();
    repository = new CollectionRepository<>(data);
  }

  // 剩余实现
}
```

为了用数据填充 `Repository`，`Service` 类作为中央管理者，处理在应用中的资产加载和组织。客户数据从一个 JSON 文件读取，并映射到 `Repository` 中的 `Customer` 对象。

webforJ 中的 `Assets` 工具使其轻松使用上下文 URL 动态加载这些数据。要在 webforJ 中加载资源和数据，`Service` 类使用包含 `Assets` 工具的上下文 URL。例如，可以如下加载客户数据：

```java
String content = Assets.contentOf(Assets.resolveContextUrl("context://data/customers.json"));
```

:::tip 使用 `ObjectTable`
`Service` 类使用 `ObjectTable` 动态管理实例，而不是依赖静态字段。这种方法解决了使用 servlet 时的一个关键限制：静态字段与服务器的生命周期相关联，可能在具有多个请求或并发会话的环境中引发问题。`ObjectTable` 的作用域与用户会话相当，并且使用它可以确保类似单例的行为，避免这些限制，从而实现一致和可扩展的数据管理。
:::

```java title="Service.java"
public class Service {

  private List<Customer> data = new ArrayList<>();
  private CollectionRepository<Customer> repository;

  // 私有构造函数以强制控制实例化
  private Service() {
    // 实现
  }

  // 检索当前的 Service 实例，或在不存在时创建一个
  public static Service getCurrent() {
    // 实现
  }

  // 从 JSON 文件中加载客户数据并将其映射到 Customer 对象
  private List<Customer> buildDemoList() {
    // 实现
  }

  // Getter...
}
```

## 创建和使用 `Table` {#creating-and-using-a-table}

现在所需的数据已通过 `Customer` 类正确创建，并且可以通过 `Service` 类返回为 `Repository`，本步骤的最后任务是将 `Table` 组件集成到应用中以显示客户数据。

:::tip 更多关于 `Table`
有关 `Table` 各种功能行为的更详细概述，请参见 [这篇文章](../../components/table/overview)。
:::

`Table` 提供了一种动态灵活的方式在您的应用中显示结构化数据。它旨在与 `Repository` 类集成，启用数据查询、分页和高效更新等功能。`Table` 高度可配置，允许您定义列、控制外观，并将其轻松绑定到数据存储库。

### 在应用中实现 `Table` {#implementing-the-table-in-the-app}

由于 `Table` 的数据完全通过 `Service` 类处理，因此在 `DemoApplication.java` 中的主要任务是配置 `Table` 并将其链接到 `Service` 提供的 `Repository`。

要配置 `Table`：

- 使用 `setHeight()` 和 `setWidth()` 方法设置其布局的宽度和高度。
- 定义列，指定其名称和获取每列数据的方法。
- 分配 `Repository` 以动态提供数据。

完成此操作后，代码将类似于以下代码片段：

```java title="DemoApplication.java"
public class DemoApplication extends App {
  // 第一步的其他组件

  // 用于显示客户数据的 Table 组件
  Table<Customer> table = new Table<>();

  @Override
  public void run() throws WebforjException {
    // 第一步的先前实现
    buildTable();
    mainFrame.add(demo, btn, table);
  }

  private void buildTable() {
    // 将表格的高度设置为 300 像素
    table.setHeight("300px");
    // 将表格的宽度设置为 1000 像素
    table.setWidth(1000);

    // 添加各种列标题并赋予适当的 getter
    table.addColumn("First Name", Customer::getFirstName);
    table.addColumn("Last Name", Customer::getLastName);
    table.addColumn("Company", Customer::getCompany);
    table.addColumn("Country", Customer::getCountry);

    // 将 Table 绑定到包含 Customer 数据的 Repository
    // 通过 Service 类检索 Repository
    table.setRepository(Service.getCurrent().getCustomers());
  }
}
```

在实施完成的应用程序更改后，当应用程序运行时，将发生以下步骤：

1. `Service` 类从 JSON 文件中检索 `Customer` 数据并将其存储在 `Repository` 中。
2. `Table` 集成 `Repository` 以获取数据并动态填充其行。

现在 `Table` 显示 `Customer` 数据，下一步将专注于创建一个新屏幕以修改客户详细信息，并将路由集成到应用中。

这将更加有效地组织应用程序的逻辑，将其从主 `App` 类移出，并转移到通过路由访问的组成屏幕中。
