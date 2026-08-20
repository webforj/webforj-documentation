---
title: Routing and Composites
sidebar_position: 4
description: Step 3 - Make your app navigable.
_i18n_hash: f32a8552d85a9c85b565fe6f026c93bb
---
到目前为止，本教程只有一个单页应用程序。这一步将改变这一点。  
您将把在[处理数据](/docs/introduction/tutorial/working-with-data)中创建的 UI 移动到自己的页面，并创建另一个页面以添加新客户。  
然后，您将连接这些页面，以便应用能够通过应用这些概念在它们之间导航：

- [路由](/docs/routing/overview)
- [组合组件](/docs/building-ui/composing-components)
- [`ColumnsLayout`](/docs/components/columns-layout) 组件

完成此步骤将创建一个版本的 [3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites)。

<!-- Insert video here -->

## 运行应用 {#running-the-app}

在开发应用程序时，您可以使用 [3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites) 作为比较。要查看应用程序的运行效果：

1. 导航到包含 `pom.xml` 文件的顶级目录；如果您正在按照 GitHub 上的版本进行操作，则该目录为 `3-routing-and-composites`。

2. 使用以下 Maven 命令在本地运行 Spring Boot 应用：
    ```bash
    mvn
    ```

运行应用程序会自动在 `http://localhost:8080` 打开一个新浏览器。

## 可路由应用 {#routable-apps}

之前，您的应用程序只有一个功能：显示现有客户数据的表格。  
在这一阶段，您的应用程序还将能够通过添加新客户来修改客户数据。  
将显示和修改的用户界面分开对于长远的维护和测试是有益的，因此您将这个功能添加为一个单独的页面。  
您将使您的应用程序可[路由](/docs/routing/overview)，以便 webforJ 可以单独访问和加载这两个 UI。

可路由应用会基于 URL 渲染用户界面。注释继承 `App` 类的类时使用 [`@Routify`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/Routify.html) 启用路由，`packages` 元素告知 webforJ 哪些包包含 UI 组件。

当您将 `@Routify` 注解添加到 `Application` 时，请移除 `run()` 方法。您将把该方法中的组件移动到您在 `com.webforj.tutorial.views` 包中创建的类中。您更新后的 `Application.java` 文件应如下所示：

```java title="Application.java" {5-6,15}
@SpringBootApplication
@BundleEntry("css/card.css")
@AppTheme("system")

// 添加了 @Routify 注解
@Routify(packages = "com.webforj.tutorial.views")

@AppProfile(name = "CustomerApplication", shortName = "CustomerApplication")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

// 移除了重写的 App.run() 方法

}
```

:::tip 全局 CSS
将 `@BundleEntry` 注解保留在 `Application` 中，会将 CSS 文件添加到应用级前端包中，因此样式在路由视图之间保持可用。
:::

### 创建路由 {#creating-routes}

添加 `@Routify` 注解使您的应用程序可路由。一旦它可路由，您的应用程序将查看 `com.webforj.tutorial.views` 包中的路由。  
您需要为您的 UI 创建路由，并指定它们的 [路由类型](/docs/routing/route-hierarchy/route-types)。路由类型决定如何将 UI 内容映射到 URL。

第一个路由类型是 `View`。这种类型的路由直接映射到应用中的特定 URL 段。表格的 UI 和新客户表单的 UI 都将是 `View` 路由。

第二个路由类型是 `Layout`，它包含在多个页面上显示的 UI，如页眉或侧边栏。布局路由不会对 URL 做出贡献，但会包装子视图。

要指定类的路由类型，请将路由类型附加到类名的末尾作为后缀。  
例如，`MainView` 是一个 `View` 路由类型。

为了将应用程序的两个功能分开，您的应用需要将 UI 映射到两个唯一的 `View` 路由：一个用于表格，另一个用于客户表单。在 `/src/main/java/com/webforj/tutorial/views` 中，创建两个带 `View` 后缀的类：

- **`MainView`**：此视图将具有先前在 `Application` 类中的 `Table`。
- **`FormView`**：此视图将具有用于添加新客户的表单。

### 将 URL 映射到组件 {#mapping-urls-to-components}

您的应用程序可以路由并知道要查找两个 `View` 路由，`MainView` 和 `FormView`，但它没有特定的 URL 来加载它们。通过在视图类上使用 `@Route` 注解，您可以告诉 webforJ 在给定的 URL 段中在哪里加载它。  
例如，在视图中使用 `@Route("about")` 可以将类本地映射到 `http://localhost:8080/about`。

顾名思义，`MainView` 是您希望在应用程序运行时最初加载的类。要实现这一点，添加一个将 `MainView` 映射到应用程序根 URL 的 `@Route` 注解：

```java title="MainView.java" {1}
@Route("/")
public class MainView {

  public MainView() {
  }

}
```

对于 `FormView`，将视图映射到用户转到 `http://localhost:8080/customer` 时加载：

```java title="FormView.java" {1}
@Route("customer")
public class FormView {

  public FormView() {
  }

}
```

:::tip 默认行为
如果您没有明确为 `@Route` 注解赋值，则 URL 段为类名小写，并去掉 `View` 后缀。

- `MainView` 会映射到 `/main`
- `FormView` 会映射到 `/form`
:::

## 共享特性 {#shared-characteristics}

除了都是视图路由外，`MainView` 和 `FormView` 还具有其他共享特性。其中一些特性，如使用 `Composite` 组件，是使用 WebforJ 应用程序的基本要求，而其他特性则使管理应用程序更容易。

### 使用 `Composite` 组件 {#using-composite-components}

当应用程序是单页时，您将组件存储在 `Frame` 中。  
展望未来，随着应用程序具有多个视图，您需要将这些 UI 组件包装在 [`Composite` 组件](/docs/building-ui/composing-components) 中。

`Composite` 组件是可重用组件的包装器。  
要创建 `Composite` 组件，请扩展 `Composite` 类，并指定作为类基础的绑定组件，例如，`Composite<FlexLayout>`。

本教程使用 `Div` 元素作为绑定组件，但它们可以是任何组件，例如 [`FlexLayout`](/docs/components/flex-layout) 或 [`AppLayout`](/docs/components/app-layout)。使用 `getBoundComponent()` 方法，您可以引用绑定组件并访问其方法。这让您能够设置大小，添加 CSS 类名，添加希望显示在 `Composite` 组件中的组件，以及访问特定于组件的方法。

对于 `MainView` 和 `FormView`，请将 `Composite` 与 `Div` 作为绑定组件进行扩展。然后，引用该绑定组件以便稍后可以添加 UI。两个视图应如下结构所示：

```java
// 将 Composite 扩展为绑定组件
public class MainView extends Composite<Div> {

  // 访问绑定组件
  private Div self = getBoundComponent();

  // 创建一个组件 UI
  private Button submit = new Button("Submit");

  public MainView() {

    // 将 UI 组件添加到绑定组件
    self.add(submit);
  }
}
```

### 设置框架标题 {#setting-the-frame-tile}

当用户在浏览器中打开多个选项卡时，唯一的框架标题可以帮助他们快速识别他们打开的应用程序的哪个部分。

[`@FrameTitle`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/FrameTitle.html) 注解定义了在浏览器的标题或页面选项卡中显示的内容。对于两个视图，使用 `@FrameTitle` 注解添加框架标题：

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

通过在 `MainView` 和 `FormView` 中可引用的绑定组件，您可以使用 CSS 对其进行样式。  
您可以使用第一步中的 CSS，[创建基本应用](/docs/introduction/tutorial/creating-a-basic-app#referencing-a-css-file)，为两个视图提供相同的 UI 容器样式。  
将 CSS 类名 `card` 添加到每个视图中的绑定组件：

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
`MainView` 中的 `Table` 显示每位客户，而 `FormView` 添加新客户。由于这两个视图都与客户数据交互，因此它们需要访问应用程序的业务逻辑。

视图通过在[处理数据](/docs/introduction/tutorial/working-with-data#creating-a-service)中创建的 Spring 服务 `CustomerService` 获得访问权限。  
要在每个视图中使用 Spring 服务，请将 `CustomerService` 作为构造函数参数：

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

在使应用程序可路由、为视图提供 `Composite` 组件包装及包含 `CustomerService` 后，您准备好构建每个视图独特的用户界面。  
如前所述，`MainView` 包含最初在 `Application` 中的 UI 组件。此类还需要导航到 `FormView` 的方式。

### 组合 `Table` 方法 {#grouping-the-table-methods}

当您将组件从 `Application` 移动到 `MainView` 时，最好开始对应用程序的部分进行分组，以便一个自定义方法可以一次对 `Table` 进行更改。  
现在对代码进行分区可以使其在应用程序变得更加复杂时更易于管理。

现在，您的 `MainView` 构造函数只应调用一个 `buildTable()` 方法，该方法用于添加列、设置大小并引用存储库：

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

### 导航到 `FormView` {#navigating-to-formview}

用户需要一种通过 UI 从 `MainView` 导航到 `FormView` 的方式。

在 webforJ 中，您可以直接使用视图的类导航到新视图。通过类而不是 URL 段进行路由可以确保 webforJ 将路径加载视图的正确方式。

要导航到不同的视图，请使用 [`Router`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/router/Router.html) 类来获取当前的位置，然后使用 `getCurrent()` 方法，接着使用 `navigate()` 方法将视图的类作为参数：

```java
Router.getCurrent().navigate(FormView.class);
```

这段代码将以编程方式将用户发送到新客户表单，但导航需要与用户操作连接。  
为了让用户添加新客户，您可以修改或替换 `Application` 中的信息按钮。按钮可以导航到 `FormView` 类，而不是打开消息对话框：

```java
private Button addCustomer = new Button("添加客户", ButtonTheme.PRIMARY,
    e -> Router.getCurrent().navigate(FormView.class));
```

## 完成的 `MainView` {#completed-mainview}

通过对 `FormView` 的导航和组合表格方法，以下是 `MainView` 在创建 `FormView` 之前的样子：

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

`FormView` 将显示一个用于添加新客户的表单。对于每个客户属性，`FormView` 将有一个可编辑组件供用户进行交互。此外，它将有一个按钮供用户提交数据和一个取消按钮以放弃数据。

### 创建 `Customer` 实例 {#creating-a-customer-instance}

当用户正在编辑新客户的数据时，只有在他们准备提交表单时，变化才应该应用于存储库。使用 `Customer` 对象的实例是一种方便的方法，以便在不直接编辑存储库的情况下编辑和维护新数据。  
在 `FormView` 中创建一个新的 `Customer` 以用于表单：

```java
private Customer customer = new Customer();
```

为了使 `Customer` 实例可编辑，除了 `id` 之外的每个属性都应该与可编辑组件相关联。用户在 UI 中所做的更改应该反映在 `Customer` 实例中。

### 添加 `TextField` 组件 {#adding-textfield-components}

`Customer` 中的前三个可编辑属性（`firstName`、`lastName` 和 `company`）都是 `String` 值，并且应该用单行文本编辑器表示。 [`TextField`](/docs/components/fields/textfield) 组件是表示这些属性的不错选择。

使用 `TextField` 组件，您可以添加标签和事件侦听器，每当字段值发生变化时就会触发。每个事件侦听器应更新 `Customer` 实例中的相应属性。

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
将组件命名为它们所代表的 `Customer` 实体的属性，使将来步骤中的数据绑定更容易。 
:::

### 添加 `ChoiceBox` 组件 {#adding-a-choicebox-component}

对于 `country` 属性，使用 `TextField` 并不理想，因为该属性只能是五个枚举值之一：`UNKNOWN`、`GERMANY`、`ENGLAND`、`ITALY` 和 `USA`。

选择预定义选项列表的更好组件是 [`ChoiceBox`](/docs/components/lists/choicebox)。

`ChoiceBox` 组件的每个选项由 `ListItem` 表示。每个 `ListItem` 有两个值，一个是 `Object` 键，另一个是 UI 中显示的 `String` 文本。拥有每个选项的两个值可以使您在内部处理 `Object`，同时为用户在 UI 中呈现更易读的选项。

例如，`Object` 键可以是国际标准书号 (ISBN)，而 `String` 文本是书名，更加易于人类读取。

```java
new ListItem(isbn, bookTitle);
```

然而，此应用程序处理的是国家名称列表，而不是书籍。  
对于每个 `ListItem`，您希望 `Object` 是 `Customer.Country` 枚举，而文本则可以是其 `String` 表示。

要将所有 `country` 选项添加到 `ChoiceBox` 中，您可以使用迭代器为每个 `Customer.Country` 枚举创建一个 `ListItem`，并将其放入一个 `ArrayList<ListItem>` 中。然后，您可以将该 `ArrayList<ListItem>` 插入到 `ChoiceBox` 组件中：

```java
// 创建 ChoiceBox 组件
private ChoiceBox country = new ChoiceBox("国家");

// 创建 ListItem 对象的 ArrayList
ArrayList<ListItem> listCountries = new ArrayList<>();

// 添加一个迭代器，为每个 Customer.Country 选项创建 ListItem
for (Country countryItem : Customer.Country.values()) {
  listCountries.add(new ListItem(countryItem, countryItem.toString()));
}

// 将填充的 ArrayList 插入到 ChoiceBox 中
country.insert(listCountries);

// 使表单加载时第一个 `ListItem` 成为默认选项
country.selectIndex(0);
```

然后，当用户在 `ChoiceBox` 中选择一个选项时，`Customer` 实例应该使用所选项的键进行更新，该键是 `Customer.Country` 值。

```java
private ChoiceBox country = new ChoiceBox("国家",
    e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));
```

为了保持代码清晰，创建 `ArrayList<ListItem>` 并将其添加到 `ChoiceBox` 的迭代器应该放在一个单独的方法中。  
在您添加一个允许用户选择 `country` 属性的 `ChoiceBox` 后，`FormView` 应如下所示：

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

使用新客户表单时，用户应该能够保存或放弃其更改。  
创建两个 `Button` 组件来实现此功能：

```java
private Button submit = new Button("提交");
private Button cancel = new Button("取消");
```

提交和取消按钮都应将用户返回到 `MainView`。  
这允许用户立即看到他们的操作的结果，无论他们在表格中看到的新客户，还是保持不变。  
由于 `FormView` 中的多个输入将用户带到 `MainView`，因此导航应放入可调用的方法中：

```java
private void navigateToMain(){
  Router.getCurrent().navigate(MainView.class);
}
```

**取消按钮**

放弃表单上的更改不需要超出返回到 `MainView` 的事件的任何附加代码。但由于取消不是主要操作，因此将按钮的主题设置为轮廓可以让提交按钮更突出。  
`Button` 组件页面的[主题](/docs/components/button#themes)部分列出了所有可用主题。

```java
private Button cancel = new Button("取消", ButtonTheme.OUTLINED_PRIMARY,
    e -> navigateToMain());
```

**提交按钮**

当用户按下提交按钮时，`Customer` 实例中的值应用于在存储库中创建新条目。

使用 `CustomerService`，您可以使用 `Customer` 实例来更新 H2 数据库。当此操作发生时，将为该 `Customer` 分配一个新的唯一 `id`。更新存储库后，您可以将用户重定向到 `MainView`，在该视图中，他们可以在表格中看到新客户。

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
在此步骤中，`FormView` 的最后改进是以视觉方式组织这六个组件。

此表单可以使用 [`ColumnsLayout`](/docs/components/columns-layout) 将组件分为两列，而无需设置任何交互式组件的宽度。  
要创建 `ColumnsLayout`，请指定应该在布局内的每个组件：

```java
private ColumnsLayout layout = new ColumnsLayout(
  firstName, lastName,
  company, country,
  submit, cancel);
```

要为 `ColumnsLayout` 设置列数，请使用 `Breakpoint` 对象的 `List`。每个 `Breakpoint` 告诉 `ColumnsLayout` 必须具有的最小宽度，以便应用指定的列数。通过使用 `ColumnsLayout`，您可以制作一个具有两列的表单，但只有在屏幕足够宽以显示两列时。在较小的屏幕上，组件将显示在单列中。

[`ColumnsLayout` 文章](https://docs.webforj.com/components/columns-layout#breakpoints) 中的 [断点](/docs/components/columns-layout#breakpoints) 部分详细说明了断点。

为了保持代码可维护，将断点设置放在单独的方法中。在该方法中，您还可以使用 `setSpacing()` 方法控制 `ColumnsLayout` 中组件之间的水平和垂直间距。

```java
private void setColumnsLayout() {

  // 如果宽度大于 600px，则在 ColumnsLayout 中有两列
  List<Breakpoint> breakpoints = List.of(
    new Breakpoint(600, 2));

  // 添加断点列表
  layout.setBreakpoints(breakpoints);

  // 使用 DWC CSS 变量设置组件之间的间距
  layout.setSpacing("var(--dwc-space-l)")
}
```

最后，您可以将新创建的 `ColumnsLayout` 添加到 `FormView` 的绑定组件中，同时设置最大宽度，并添加之前的类名：

```java
self.setMaxWidth(600)
  .addClassName("card")
  .add(layout);
```

## 完成的 `FormView` {#completed-formview}

在添加了 `Customer` 实例、互动组件和 `ColumnsLayout` 之后，您的 `FormView` 应如下所示：

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

由于用户现在可以添加客户，因此您的应用程序也应该能够使用相同的表单来编辑现有客户。在下一步中，[观察者和路由参数](/docs/introduction/tutorial/observers-and-route-parameters)，您将允许客户 `id` 成为 `FormView` 的初始参数，以便填充表单中的客户数据并允许用户更改属性。
