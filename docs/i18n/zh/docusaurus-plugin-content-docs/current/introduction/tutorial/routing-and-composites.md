---
title: Routing and Composites
sidebar_position: 4
description: Step 3 - Make your app navigable.
_i18n_hash: 1ffb9e9bf7ba8d863dad3bc0c42c11d7
---
到目前为止，本教程只涉及单页应用程序。这一步将改变这一点。  
您将把在 [处理数据](/docs/introduction/tutorial/working-with-data) 中创建的 UI 移动到它自己的页面，并为添加新客户创建另一个页面。  
然后，您将连接这些页面，以便您的应用能够通过应用以下概念在它们之间导航：

- [路由](/docs/routing/overview)
- [组合组件](/docs/building-ui/composing-components)
- [`ColumnsLayout`](/docs/components/columns-layout) 组件

完成此步骤后，将创建 [3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites) 的一个版本。

<!-- 插入视频 -->

## 运行应用 {#running-the-app}

在开发应用时，您可以将 [3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites) 作为比较。要查看应用的运行情况：

1. 导航到包含 `pom.xml` 文件的顶层目录；如果您按照 GitHub 上的版本进行操作，这就是 `3-routing-and-composites`。

2. 使用以下 Maven 命令在本地运行 Spring Boot 应用：
    ```bash
    mvn
    ```

运行应用会自动打开一个新的浏览器窗口，地址为 `http://localhost:8080`。

## 可路由应用 {#routable-apps}

之前，您的应用只有一个功能：显示现有客户数据的表格。  
在此步骤中，您的应用还能够通过添加新客户来修改客户数据。  
将显示和修改的 UI 分开有利于长期维护和测试，因此您将这个功能添加为一个单独的页面。  
您将使您的应用 [可路由](/docs/routing/overview)，以便 webforJ 可以单独访问和加载这两个 UI。

可路由应用根据 URL 渲染 UI。通过给扩展 `App` 类的类添加 [`@Routify`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/Routify.html) 注释来启用路由，而 `packages` 元素告诉 webforJ 哪些包包含 UI 组件。

当您给 `Application` 添加 `@Routify` 注释时，请删除 `run()` 方法。您将把该方法中的组件移动到您将在 `com.webforj.tutorial.views` 包中创建的类中。您更新后的 `Application.java` 文件应如下所示：

```java title="Application.java" {5-6,15}
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")

// 添加了 @Routify 注释
@Routify(packages = "com.webforj.tutorial.views")

@AppProfile(name = "CustomerApplication", shortName = "CustomerApplication")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

// 删除覆盖的 App.run() 方法

}
```

:::tip 全局 CSS
将 `@StyleSheet` 注释保留在 `Application` 中会全局应用该 CSS。
:::

### 创建路由 {#creating-routes}

添加 `@Routify` 注释使您的应用可路由。一旦可路由，您的应用将查看 `com.webforj.tutorial.views` 包中的路由。  
您需要为您的 UI 创建路由，还需要指定它们的 [路由类型](/docs/routing/route-hierarchy/route-types)。路由类型决定如何将 UI 内容映射到 URL。

第一个路由类型是 `View`。此类路由直接映射到应用中的特定 URL 段。 表格和新客户表单的 UI 都将是 `View` 路由。

第二个路由类型是 `Layout`，其中包含多个页面上出现的 UI，例如页眉或侧边栏。布局路由还会包裹子视图，而不会对 URL 产生影响。

要指定类的路由类型，请在类名的末尾追加路由类型作为后缀。  
例如， `MainView` 是一个 `View` 路由类型。

为了保持应用的两个功能分开，您的应用需要将 UI 映射到两个唯一的 `View` 路由：一个用于表格，另一个用于客户表单。在 `/src/main/java/com/webforj/tutorial/views` 中创建两个具有 `View` 后缀的类：

- **`MainView`**：该视图将具有以前在 `Application` 类中的 `Table`。
- **`FormView`**：该视图将具有用于添加新客户的表单。

### 将 URL 映射到组件 {#mapping-urls-to-components}

您的应用是可路由的，并且知道查找两个 `View` 路由，`MainView` 和 `FormView`，但它还没有特定的 URL 来加载它们。使用 `@Route` 注释在视图类上，您可以告诉 webforJ 基于给定的 URL 段加载它。例如，使用 `@Route("about")` 在视图中将类本地映射到 `http://localhost:8080/about`。

顾名思义，`MainView` 是您希望在应用运行时首先加载的类。为此，请添加一个 `@Route` 注释，将 `MainView` 映射到应用的根 URL：

```java title="MainView.java" {1}
@Route("/")
public class MainView {

  public MainView() {
  }

}
```

对于 `FormView`，将视图映射为在用户访问 `http://localhost:8080/customer` 时加载：

```java title="FormView.java" {1}
@Route("customer")
public class FormView {

  public FormView() {
  }

}
```

:::tip 默认行为
如果您没有为 `@Route` 注释显式分配值，则 URL 段是类名转换为小写后去掉 `View` 后缀的结果。

- `MainView` 将映射到 `/main`
- `FormView` 将映射到 `/form`
:::

## 共享特性 {#shared-characteristics}

除了都是视图路由之外，`MainView` 和 `FormView` 还有其他共同特性。其中一些共享特性，例如使用 `Composite` 组件，是使用 webforJ 应用的基础，而其他的则使管理您的应用变得更加容易。

### 使用 `Composite` 组件 {#using-composite-components}

当应用是单页时，您将组件存储在 `Frame` 内。往后，在多视图的应用中，您需要将这些 UI 组件包装在 [`Composite` 组件](/docs/building-ui/composing-components) 内。

`Composite` 组件是包装器，使您可以轻松创建可重用的组件。  
要创建 `Composite` 组件，请扩展 `Composite` 类并指定一个作为类基础的绑定组件，例如 `Composite<FlexLayout>`。

本教程使用 `Div` 元素作为绑定组件，但它们可以是任何组件，例如 [`FlexLayout`](/docs/components/flex-layout) 或 [`AppLayout`](/docs/components/app-layout)。使用 `getBoundComponent()` 方法，您可以引用绑定组件并访问其方法。这使您可以设置大小、添加 CSS 类名称、添加想要显示在 `Composite` 组件中的组件，并访问组件特定的方法。

对于 `MainView` 和 `FormView`，请将 `Composite` 与 `Div` 作为绑定组件扩展。然后，引用该绑定组件，以便稍后可以添加 UI。两个视图应具有类似于以下结构：

```java
// 将 Composite 与绑定组件扩展
public class MainView extends Composite<Div> {

  // 访问绑定组件
  private Div self = getBoundComponent();

  // 创建组件 UI
  private Button submit = new Button("Submit");

  public MainView() {

    // 将 UI 组件添加到绑定组件
    self.add(submit);
  }
}
```

### 设置框架标题 {#setting-the-frame-tile}

当用户在浏览器中有多个选项卡时，唯一的框架标题可以帮助他们快速识别他们打开的应用的哪个部分。

[`@FrameTitle`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/FrameTitle.html) 注释定义了在浏览器的标题或页面的选项卡中显示的内容。对于这两个视图，通过使用 `@FrameTitle` 注释添加框架标题：

<Tabs>
  <TabItem value="MainView" label="MainView">
  ```java title="MainView.java" {2}
  @Route("/")
  @FrameTitle("客户表格")
  public class MainView extends Composite<Div> {

    private Div self = getBoundComponent();

    public MainView(CustomerService customerService) {
    }
  }
  ```
  </TabItem>
  <TabItem value="FormView" label="FormView">
  ```java title="FormView.java" {2}
  @Route("customer")
  @FrameTitle("客户表单")
  public class FormView extends Composite<Div> {

    private Div self = getBoundComponent();

    public FormView(CustomerService customerService) {
    }
  }
  ```
  </TabItem>
</Tabs>

### 共享 CSS {#shared-css}

通过在 `MainView` 和 `FormView` 中可以引用的绑定组件，您可以使用 CSS 对其进行样式设置。  
您可以使用第一步中的 CSS，[创建基本应用](/docs/introduction/tutorial/creating-a-basic-app#referencing-a-css-file)，为两个视图提供相同的 UI 容器样式。  
将 CSS 类名称 `card` 添加到每个视图中的绑定组件：

<Tabs>
  <TabItem value="MainView" label="MainView">
    ```java {9} title="MainView.java"
    @Route("/")
    @FrameTitle("客户表格")
    public class MainView extends Composite<Div> {

      private Div self = getBoundComponent();

      public MainView() {

        self.addClassName("card");
      }
    }
    ```
  </TabItem>
  <TabItem value="FormView" label="FormView">
    ```java {9} title="FormView.java"
    @Route("customer")
    @FrameTitle("客户表单")
    public class FormView extends Composite<Div> {

      private Div self = getBoundComponent();

      public FormView() {

        self.addClassName("card");
      }
    }
    ```
  </TabItem>
</Tabs>

### 使用 `CustomerService` {#using-customerservice}

视图的最后一个共享特性是使用 `CustomerService` 类。  
`MainView` 中的 `Table` 显示每个客户，而 `FormView` 添加新客户。由于这两个视图都与客户数据交互，因此它们需要访问应用的业务逻辑。

视图通过在 [处理数据](/docs/introduction/tutorial/working-with-data#creating-a-service) 中创建的 Spring 服务 `CustomerService` 获得访问权限。要在每个视图中使用 Spring 服务，请将 `CustomerService` 作为构造函数参数：

<Tabs>
  <TabItem value="MainView" label="MainView">
    ```java {7-8} title="MainView.java"
    @Route("/")
    @FrameTitle("客户表格")
    public class MainView extends Composite<Div> {

      private Div self = getBoundComponent();

      public MainView(CustomerService customerService) {
        this.customerService = customerService;
        self.addClassName("card");
      }
    }
    ```
  </TabItem>
  <TabItem value="FormView" label="FormView">
    ```java {7-8} title="FormView.java"
    @Route("customer")
    @FrameTitle("客户表单")
    public class FormView extends Composite<Div> {

      private Div self = getBoundComponent();

      public FormView(CustomerService customerService) {
        this.customerService = customerService;
        self.addClassName("card");
      }
    }
    ```
  </TabItem>
</Tabs>

## 创建 `MainView` {#creating-mainview}

在使您的应用可路由、给视图提供 `Composite` 组件包装和包括 `CustomerService` 之后，您准备构建每个视图独特的 UI。如前所述，`MainView` 包含最初在 `Application` 中的 UI 组件。该类还需要一种导航到 `FormView` 的方式。

### 分组 `Table` 方法 {#grouping-the-table-methods}

当您从 `Application` 中移动组件到 `MainView` 时，开始部分应用的代码，使一个自定义方法可以一次性对 `Table` 进行更改是个好主意。现在划分代码将使其更易于管理，因为应用会变得越来越复杂。

现在，`MainView` 构造函数应只调用一个 `buildTable()` 方法，该方法添加列、设置大小并引用存储库：

```java
private void buildTable() {
  table.setSize("1000px", "294px");
  table.setMaxWidth("90vw");
  table.addColumn("firstName", Customer::getFirstName).setLabel("名字");
  table.addColumn("lastName", Customer::getLastName).setLabel("姓氏");
  table.addColumn("company", Customer::getCompany).setLabel("公司");
  table.addColumn("country", Customer::getCountry).setLabel("国家");
  table.setColumnsToAutoFit();
  table.getColumns().forEach(column -> column.setSortable(true));
  table.setRepository(customerService.getRepositoryAdapter());
}
```

### 导航到 `FormView`{#navigating-to-formview}

用户需要一种从 `MainView` 导航到 `FormView` 的方式，通过 UI 来实现。

在 webforJ 中，您可以直接通过使用视图的类来导航到新视图。通过类进行路由，而不是 URL 段，可以保证 webforJ 会采取正确的路径加载视图。

要导航到不同的视图，请使用 [`Router`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/router/Router.html) 类获取当前位置，使用 `getCurrent()`，然后使用 `navigate()` 方法，将视图类作为参数传递：

```java
Router.getCurrent().navigate(FormView.class);
```

这段代码将以编程方式将用户发送到新客户表单，但导航需要与用户操作连接在一起。  
为了允许用户添加新客户，您可以修改或替换来自 `Application` 的信息按钮。按钮可以导航到 `FormView` 类：

```java
private Button addCustomer = new Button("添加客户", ButtonTheme.PRIMARY,
    e -> Router.getCurrent().navigate(FormView.class));
```

## 完成的 `MainView` {#completed-mainview}

通过导航到 `FormView` 和对表格方法进行分组，以下是您在创建 `FormView` 之前应该得到的 `MainView`：

<!-- vale off -->
<ExpandableCode title="MainView.java" language="java" startLine={1} endLine={15}>

```java
@Route("/")
@FrameTitle("客户表格")
public class MainView extends Composite<Div> {
  private final CustomerService customerService;
  private Div self = getBoundComponent();
  private Table<Customer> table = new Table<>();
  private Button addCustomer = new Button("添加客户", ButtonTheme.PRIMARY,
      e -> Router.getCurrent().navigate(FormView.class));

  public MainView(CustomerService customerService) {
    this.customerService = customerService;
    addCustomer.setWidth(200);
    buildTable();
    self.setWidth("fit-content")
        .addClassName("card")
        .add(table, addCustomer);
  }

  private void buildTable() {
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
  }

}
```

</ExpandableCode>
<!-- vale on -->

## 创建 `FormView` {#creating-formview}

`FormView` 将显示一个表单以添加新客户。对于每个客户属性，`FormView` 将具有一个可编辑组件供用户交互。此外，它将拥有一个用于用户提交数据的按钮和一个取消按钮以丢弃数据。

### 创建 `Customer` 实例 {#creating-a-customer-instance}

当用户正在编辑一个新客户的数据时，只有在他们准备提交表单时，才能将更改应用到存储库。使用 `Customer` 对象的一个实例是一种方便的方式来编辑和维护新数据，而不直接编辑存储库。在 `FormView` 中创建一个新的 `Customer` 实例以用于表单：

```java
private Customer customer = new Customer();
```

为了使 `Customer` 实例可编辑，除了 `id` 之外的每个属性都应与一个可编辑组件关联。用户在 UI 中所做的更改应反映在 `Customer` 实例中。

### 添加 `TextField` 组件 {#adding-textfield-components}

`Customer` 中的前三个可编辑属性（`firstName`、`lastName`、`company`）都是 `String` 值，应该由单行文本编辑器表示。 [`TextField`](/docs/components/fields/textfield) 组件是表示这些属性的好选择。

使用 `TextField` 组件，您可以添加一个标签和一个事件监听器，该监听器在字段值更改时触发。每个事件监听器应更新 `Customer` 实例中的相应属性。

添加三个 `TextField` 组件，以更新 `Customer` 实例：

```java title="FormView.java" {6-8}
public class FormView extends Composite<Div> {
  private final CustomerService customerService;
  private Customer customer = new Customer();
  private Div self = getBoundComponent();

  private TextField firstName = new TextField("名字", e -> customer.setFirstName(e.getValue()));
  private TextField lastName = new TextField("姓氏", e -> customer.setLastName(e.getValue()));
  private TextField company = new TextField("公司", e -> customer.setCompany(e.getValue()));

  public FormView(CustomerService customerService) {
    this.customerService = customerService;
    self.addClassName("card");
  }
}
```

:::tip 共享命名约定
将组件命名为与其代表的 `Customer` 实体的属性相同，有助于在未来的步骤中进行数据绑定， [验证和绑定数据](/docs/introduction/tutorial/validating-and-binding-data)。
:::

### 添加 `ChoiceBox` 组件 {#adding-a-choicebox-component}

对于 `country` 属性，使用 `TextField` 并不理想，因为该属性只能是五个枚举值之一：`UNKNOWN`、`GERMANY`、`ENGLAND`、`ITALY` 和 `USA`。

选择预定义选项列表的更好组件是 [`ChoiceBox`](/docs/components/lists/choicebox)。

每个 `ChoiceBox` 组件的选项都表示为 `ListItem`。每个 `ListItem` 有两个值，一个是 `Object` 键，另一个是用于在 UI 中显示的 `String` 文本。为每个选项提供两个值使您可以在内部处理 `Object`，同时在 UI 中呈现更可读的选项。

例如，`Object` 键可以是国际标准书号（ISBN），而 `String` 文本是书名，更易于人类阅读。

```java
new ListItem(isbn, bookTitle);
```

然而，这个应用处理的是国家名称列表，而不是书籍。对于每个 `ListItem`，您希望 `Object` 是 `Customer.Country` 枚举，而文本可以是其 `String` 表示。

要将所有 `country` 选项添加到 `ChoiceBox` 中，您可以使用迭代器为每个 `Customer.Country` 枚举创建一个 `ListItem`，并将它们放入一个 `ArrayList<ListItem>` 中。然后，您可以将该 `ArrayList<ListItem>` 插入到 `ChoiceBox` 组件中：

```java
// 创建 ChoiceBox 组件
private ChoiceBox country = new ChoiceBox("国家");

// 创建一个 ListItem 对象的 ArrayList
ArrayList<ListItem> listCountries = new ArrayList<>();

// 添加一个迭代器，为每个 Customer.Country 选项创建一个 ListItem
for (Country countryItem : Customer.Country.values()) {
  listCountries.add(new ListItem(countryItem, countryItem.toString()));
}

// 将填充的 ArrayList 插入 ChoiceBox
country.insert(listCountries);

// 使第一个 ListItem 成为表单加载时的默认选项
country.selectIndex(0);
```

然后，当用户在 `ChoiceBox` 中选择一个选项时，`Customer` 实例应使用所选项的键更新，即 `Customer.Country` 值。

```java
private ChoiceBox country = new ChoiceBox("国家",
    e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));
```

为了保持代码整洁，创建 `ArrayList<ListItem>` 并将其添加到 `ChoiceBox` 的迭代器应该在一个单独的方法中。 
在您添加允许用户选择 `country` 属性的 `ChoiceBox` 后，`FormView` 应如下所示：

```java title="FormView.java" {9-10,15,18-25}
public class FormView extends Composite<Div> {
  private final CustomerService customerService;
  private Customer customer = new Customer();
  private Div self = getBoundComponent();
  private TextField firstName = new TextField("名字", e -> customer.setFirstName(e.getValue()));
  private TextField lastName = new TextField("姓氏", e -> customer.setLastName(e.getValue()));
  private TextField company = new TextField("公司", e -> customer.setCompany(e.getValue()));

  private ChoiceBox country = new ChoiceBox("国家",
      e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));

  public FormView(CustomerService customerService) {
    this.customerService = customerService;
    self.addClassName("card");
    fillCountries();
  }

  private void fillCountries() {
    ArrayList<ListItem> listCountries = new ArrayList<>();
    for (Country countryItem : Customer.Country.values()) {
      listCountries.add(new ListItem(countryItem, countryItem.toString()));
    }
    country.insert(listCountries);
    country.selectIndex(0);
  }

}
```

### 添加 `Button` 组件 {#adding-button-components}

在使用新客户表单时，用户应该能够保存或丢弃他们的更改。  
创建两个 `Button` 组件以实现此功能：

```java
private Button submit = new Button("提交");
private Button cancel = new Button("取消");
```

提交和取消按钮都应该将用户返回到 `MainView`。  
这使用户能够立即看到他们的操作结果，无论他们在表中看到新客户还是保持不变。  
由于 `FormView` 中的多个输入将用户带至 `MainView`，导航应放入可以回调的方法中：

```java
private void navigateToMain(){
  Router.getCurrent().navigate(MainView.class);
}
```

**取消按钮**

丢弃表单上的更改不需要任何额外的事件代码，只需返回 `MainView`。但是，由于取消不是主要操作，因此将按钮的主题设置为轮廓，使提交按钮更为显著。  
[主题](/docs/components/button#themes)部分中的 `Button` 组件页面列出了所有可用主题。

```java
private Button cancel = new Button("取消", ButtonTheme.OUTLINED_PRIMARY,
    e -> navigateToMain());
```

**提交按钮**

当用户按下提交按钮时，`Customer` 实例中的值应用于在存储库中创建新条目。

使用 `CustomerService`，您可以获取 `Customer` 实例以更新 H2 数据库。当此操作发生时，将为该 `Customer` 分配一个新的唯一 `id`。更新存储库后，您可以将用户重定向到 `MainView`，使他们能够在表中查看新客户。

```java
private Button submit = new Button("提交", ButtonTheme.PRIMARY,
    e -> submitCustomer());

//...

private void submitCustomer() {
  customerService.createCustomer(customer);
  navigateToMain();
}
```

### 使用 `ColumnsLayout` {#using-a-columnslayout}

通过添加 `TextField`、`ChoiceBox` 和 `Button` 组件，您现在拥有表单的所有交互部分。  
本步骤中对 `FormView` 的最后改进是以视觉方式组织这六个组件。

此表单可以使用 [`ColumnsLayout`](/docs/components/columns-layout) 将组件分成两列，而无需设置任何交互组件的宽度。  
要创建 `ColumnsLayout`，请指定每个应该在布局内部的组件：

```java
private ColumnsLayout layout = new ColumnsLayout(
  firstName, lastName,
  company, country,
  submit, cancel);
```

要为 `ColumnsLayout` 设置列数，请使用 `Breakpoint` 对象的 `List`。每个 `Breakpoint` 告诉 `ColumnsLayout` 其必须具有的最小宽度以应用指定数量的列。通过使用 `ColumnsLayout`，您可以创建一个具有两列的表单，但只有在屏幕足够宽以显示两列时。在较小的屏幕上，组件将以单列方式显示。

[断点](/docs/components/columns-layout#breakpoints)部分中详细解释了断点。

为了保持代码的可维护性，请在单独的方法中设置断点。在该方法中，您还可以使用 `setSpacing()` 方法控制 `ColumnsLayout` 内部组件之间的水平和垂直间距。

```java
private void setColumnsLayout() {

  // 如果宽度超过 600px，则 ColumnsLayout 中有两列
  List<Breakpoint> breakpoints = List.of(
    new Breakpoint(600, 2));

  // 添加断点列表
  layout.setBreakpoints(breakpoints);

  // 使用 DWC CSS 变量设置组件之间的间距
  layout.setSpacing("var(--dwc-space-l)")
}
```

最后，您可以将新创建的 `ColumnsLayout` 添加到 `FormView` 的绑定组件，同时设置最大宽度，并添加早前的类名称：

```java
self.setMaxWidth(600)
  .addClassName("card")
  .add(layout);
```

## 完成的 `FormView` {#completed-formview}

在添加 `Customer` 实例、交互组件和 `ColumnsLayout` 之后，您的 `FormView` 应该如下所示：

<!-- vale off -->
<ExpandableCode title="FormView.java" language="java" startLine={1} endLine={15}>

```java
@Route("customer")
@FrameTitle("客户表单")
public class FormView extends Composite<Div> {
  private final CustomerService customerService;
  private Customer customer = new Customer();
  private Div self = getBoundComponent();
  private TextField firstName = new TextField("名字", e -> customer.setFirstName(e.getValue()));
  private TextField lastName = new TextField("姓氏", e -> customer.setLastName(e.getValue()));
  private TextField company = new TextField("公司", e -> customer.setCompany(e.getValue()));
  private ChoiceBox country = new ChoiceBox("国家",
      e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));
  private Button submit = new Button("提交", ButtonTheme.PRIMARY, e -> submitCustomer());
  private Button cancel = new Button("取消", ButtonTheme.OUTLINED_PRIMARY, e -> navigateToMain());
  private ColumnsLayout layout = new ColumnsLayout(
      firstName, lastName,
      company, country,
      submit, cancel);

  public FormView(CustomerService customerService) {
    this.customerService = customerService;
    fillCountries();
    setColumnsLayout();
    self.setMaxWidth(600)
        .addClassName("card")
        .add(layout);
    submit.setStyle("margin-top", "var(--dwc-space-l)");
    cancel.setStyle("margin-top", "var(--dwc-space-l)");
  }

  private void setColumnsLayout() {
    List<Breakpoint> breakpoints = List.of(
        new Breakpoint(600, 2));
    layout.setSpacing("var(--dwc-space-l)")
        .setBreakpoints(breakpoints);
  }

  private void fillCountries() {
    ArrayList<ListItem> listCountries = new ArrayList<>();
    for (Country countryItem : Customer.Country.values()) {
      listCountries.add(new ListItem(countryItem, countryItem.toString()));
    }
    country.insert(listCountries);
    country.selectIndex(0);
  }

  private void submitCustomer() {
    customerService.createCustomer(customer);
    navigateToMain();
  }

  private void navigateToMain() {
    Router.getCurrent().navigate(MainView.class);
  }

}
```

</ExpandableCode>
<!-- vale on -->

## 下一步 {#next-step}

由于用户现在可以添加客户，您的应用还应该能够使用相同的表单编辑现有客户。在下一步中，[观察者和路由参数](/docs/introduction/tutorial/observers-and-route-parameters)，您将允许客户 `id` 成为 `FormView` 的初始参数，以便能够用该客户的数据填充表单并允许用户更改属性。
