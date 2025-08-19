---
title: Working With Data
sidebar_position: 3
_i18n_hash: 42dff7cecf07f976ccbe007e04e78a22
---
这一步骤专注于为演示应用添加数据管理和显示功能。为此，将创建关于各种 `Customer` 对象的示例数据，并更新应用以处理这些数据，并在之前的应用中添加 [`Table`](../../components/table/overview) 进行显示。

它将概述如何创建 `Customer` 模型类，并将其与一个 `Service` 类集成，以使用存储库的实现来访问和管理所需的数据。接着，将详细说明如何使用检索到的数据在应用中实现 `Table` 组件，以交互和结构化的格式显示客户信息。

到本步骤结束时，在 [上一步骤](./creating-a-basic-app) 创建的应用将显示一个包含创建数据的表格，后续步骤可以对此进行扩展。要运行应用：

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

为了创建一个在主应用中显示数据的 `Table`，需要创建一个 Java bean 类，该类可以与 `Table` 一起使用以显示数据。

在该程序中，`src/main/java/com/webforj/demos/data/Customer.java` 中的 `Customer` 类执行此任务。此类作为应用的核心数据模型，封装了与客户相关的属性，例如 `firstName`、`lastName`、`company` 和 `country`。此模型还将包含一个唯一的 ID。

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

    // 其他国家
  }

    // Getter 和 Setter

  @Override
  public Object getEntityKey() {
    return uuid;
  }
}
```

:::info 使用 `HasEntityKey` 进行唯一标识符

实现 `HasEntityKey` 接口对于管理与 `Table` 一起使用的模型中的唯一标识符至关重要。它确保模型的每个实例都有一个唯一的键，从而允许 `Table` 有效地识别和管理行。

在此演示中，`getEntityKey()` 方法为每个客户返回一个 UUID，以确保唯一性。虽然在这里使用 UUID 是为了简化，但在实际应用中，数据库主键通常是生成唯一键的更好选择。

如果不实现 `HasEntityKey`，则 `Table` 将默认使用 Java 哈希代码作为键。由于哈希代码不能保证唯一，这可能会导致在 `Table` 中管理行时发生冲突。
:::

在 `Customer` 数据模型到位后，下一步是在应用中管理和组织这些模型。

## 创建 `Service` 类 {#creating-a-service-class}

作为集中式数据管理器，`Service` 类不仅加载 `Customer` 数据，还提供了一个高效的接口，用于访问和与之交互。

`Service.java` 类是在 `src/main/java/com/webforj/demos/data` 中创建的。`Service` 作为一个共享资源，允许相关方轻松检索和交互数据，而无需在组件或类之间手动传递数据。

在本演示中，`Service` 类从位于 `src/main/resources/data/customers.json` 的 JSON 文件中读取客户数据。数据映射到 `Customer` 对象并存储在一个 `ArrayList` 中，这为表的 `Repository` 打下基础。

在 webforJ 中，`Repository` 类提供了一种结构化的方法来管理和检索实体集合。它在应用及其数据之间充当接口，提供查询、计数和刷新数据的方法，同时保持结构的清晰和一致。`Table` 类可以使用它来显示存储的数据。

虽然 `Repository` 不包括更新或删除实体的方法，但它作为一个结构化的包装器，围绕对象集合提供。这使其非常适合提供有组织、高效的数据访问。

```java
public class Service {
  private List<Customer> data = new ArrayList<>();
  private CollectionRepository<Customer> repository;

  private Service() {
    data = buildDemoList();
    repository = new CollectionRepository<>(data);
  }

  // 其余实现
}
```

为了填充 `Repository` 数据，`Service` 类作为中央管理器，处理资产的加载和组织。客户数据从 JSON 文件中读取并映射到 `Repository` 中的 `Customer` 对象。

webforJ 中的 `Assets` 工具使得使用上下文 URL 动态加载这些数据变得简单。要在 webforJ 中加载资产和数据，`Service` 类使用带有 `Assets` 工具的上下文 URL。例如，可以通过以下方式从 JSON 文件加载客户数据：

```java
String content = Assets.contentOf(Assets.resolveContextUrl("context://data/customers.json"));
```

:::tip 使用 `ObjectTable`
`Service` 类使用 `ObjectTable` 动态管理实例，而不是依赖静态字段。此方法解决了使用 servlet 时的一个关键限制：静态字段与服务器的生命周期相关，可能会导致在多个请求或并发会话中出现问题。`ObjectTable` 的作用域限于用户会话，使用它确保了类似单例的行为，没有这些限制，从而实现了一致和可扩展的数据管理。
:::

```java title="Service.java"
public class Service {

  private List<Customer> data = new ArrayList<>();
  private CollectionRepository<Customer> repository;

  // 私有构造函数以强制控制实例化
  private Service() {
    // 实现
  }

  // 检索当前的 Service 实例，如果不存在则创建一个
  public static Service getCurrent() {
    // 实现
  }

  // 从 JSON 文件加载客户数据并映射到 Customer 对象
  private List<Customer> buildDemoList() {
    // 实现
  }

  // Getter...
}
```

## 创建和使用 `Table` {#creating-and-using-a-table}

现在所需的数据已经通过 `Customer` 类正确创建，并可以通过 `Service` 类作为 `Repository` 返回，最后一步是在应用中集成 `Table` 组件以显示客户数据。

:::tip 了解更多关于 `Table`
有关 `Table` 各种功能和行为的更详细概述，请参见 [这篇文章](../../components/table/overview)。
:::

`Table` 提供了一种动态且灵活的方式在应用中显示结构化数据。它被设计成与 `Repository` 类集成，支持数据查询、分页和高效更新等功能。`Table` 高度可配置，允许您定义列、控制其外观，并与数据仓库进行绑定，尽可能少地进行努力。

### 在应用中实现 `Table` {#implementing-the-table-in-the-app}

由于 `Table` 的数据完全通过 `Service` 类处理，因此 `DemoApplication.java` 中的主要任务是配置 `Table` 并将其链接到 `Service` 提供的 `Repository`。

要配置 `Table`：

- 使用 `setHeight()` 和 `setWidth()` 方法为布局目的设置其高度和宽度。
- 定义列，指定它们的名称和用于获取每列数据的方法。
- 指定 `Repository` 以动态提供数据。

完成这些后，代码将类似于以下代码片段：

```java title="DemoApplication.java"
public class DemoApplication extends App {
  // 第一阶段的其他组件

  // 用于显示 Customer 数据的 Table 组件
  Table<Customer> table = new Table<>();

  @Override
  public void run() throws WebforjException {
    // 第一阶段的先前实现
    buildTable();
    mainFrame.add(demo, btn, table);
  }

  private void buildTable() {
    // 将表的高度设置为 300 像素
    table.setHeight("300px");
    // 将表的宽度设置为 1000 像素
    table.setWidth(1000);

    // 添加各种列标题并分配适当的 getter
    table.addColumn("First Name", Customer::getFirstName);
    table.addColumn("Last Name", Customer::getLastName);
    table.addColumn("Company", Customer::getCompany);
    table.addColumn("Country", Customer::getCountry);

    // 将 Table 绑定到包含 Customer 数据的 Repository
    // Repository 通过 Service 类检索
    table.setRepository(Service.getCurrent().getCustomers());
  }
}
```

在实现了对应用的更改后，当应用运行时将发生以下步骤：

1. `Service` 类从 JSON 文件中检索 `Customer` 数据并存储在 `Repository` 中。
2. `Table` 集成 `Repository` 以获取数据，并动态填充其行。

随着 `Table` 现在显示 `Customer` 数据，下一步将专注于创建一个新的屏幕以修改客户详细信息，并将路由集成到应用中。

这将使应用的逻辑更有效地组织，从主 `App` 类中移出，并通过路由访问的组成屏幕进行组织。
