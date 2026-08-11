---
title: Routing and Composites
sidebar_position: 4
description: Step 3 - Make your app navigable.
_i18n_hash: 6016bff3255689b6be8a69997542a372
---
到目前为止，本教程仅仅是一个单页应用程序。这一步改变了这一点。
您将把[处理数据](/docs/introduction/tutorial/working-with-data)中创建的UI移动到其自己的页面，并创建另一个页面用于添加新客户。
然后，您将连接这些页面，使您的应用能够通过应用这些概念在它们之间导航：

- [路由](/docs/routing/overview)
- [组合组件](/docs/building-ui/composing-components)
- [`ColumnsLayout`](/docs/components/columns-layout)组件

完成此步骤后，您将创建一个[3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites)的版本。

<!-- Insert video here -->

## 运行应用程序 {#running-the-app}

在开发应用程序时，您可以使用[3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites)作为比较。要查看应用程序的运行情况：

1. 导航到包含`pom.xml`文件的顶级目录；如果您按照GitHub上的版本进行操作，那么就是`3-routing-and-composites`。

2. 使用以下Maven命令在本地运行Spring Boot应用程序：
    ```bash
    mvn
    ```

运行应用程序会自动在`http://localhost:8080`打开一个新浏览器。

## 可路由的应用程序 {#routable-apps}

以前，您的应用程序只有一个功能：显示现有客户数据的表格。
在这一步中，您的应用程序还能够通过添加新客户来修改客户数据。
将显示和修改的UI分开有利于长期维护和测试，因此您将把此功能作为单独页面添加。
您将使您的应用程序[可路由](/docs/routing/overview)，以便webforJ可以单独访问和加载两个UI。

可路由的应用程序根据URL呈现UI。通过将扩展`App`类的类注解为[`@Routify`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/Routify.html)，可以启用路由，而`packages`元素告诉webforJ哪些包包含UI组件。

当您将`@Routify`注解添加到`Application`时，请移除`run()`方法。您将把该方法中的组件移动到您将在`com.webforj.tutorial.views`包中创建的类中。您更新后的`Application.java`文件应如下所示：

```java title="Application.java" {5-6,15}
@SpringBootApplication
@BundleEntry("css/card.css")
@AppTheme("system")

//添加@Routify注解
@Routify(packages = "com.webforj.tutorial.views")

@AppProfile(name = "CustomerApplication", shortName = "CustomerApplication")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

// 移除了重写的App.run()方法

}
```

:::tip 全局CSS
将`@BundleEntry`注解保留在`Application`中，将CSS文件添加到应用程序级前端包中，因此样式在路由视图间保持可用。
:::

### 创建路由 {#creating-routes}

添加`@Routify`注解使您的应用程序可路由。一旦它可路由，您的应用程序将在`com.webforj.tutorial.views`包中查找路由。
您需要为您的UI创建路由，并指定它们的[路由类型](/docs/routing/route-hierarchy/route-types)。路由类型决定了如何将UI内容映射到URL。

第一个路由类型是`View`。这种类型的路由直接映射到您的应用程序中某个特定的URL段。用于表格和新客户表单的UI都会是`View`路由。

第二种路由类型是`Layout`，它包含在多个页面中出现的UI，例如页眉或侧边栏。布局路由还可以包装子视图，而不对URL产生影响。

要指定类的路由类型，可以在类名后面追加路由类型作为后缀。
例如，`MainView`就是一个`View`路由类型。

为了保持应用程序的两个功能分开，您的应用需要将UI映射到两个唯一的`View`路由：一个用于表格，一个用于客户表单。在`/src/main/java/com/webforj/tutorial/views`中，创建两个类，后缀为`View`：

- **`MainView`**：此视图将包含之前在`Application`类中的`Table`。
- **`FormView`**：此视图将包含一个用于添加新客户的表单。

### 将URL映射到组件 {#mapping-urls-to-components}

您的应用程序是可路由的，并且知道要寻找两个`View`路由，`MainView`和`FormView`，但它没有特定的URL来加载它们。使用`@Route`注解在视图类上，您可以告诉webforJ在给定的URL段上加载它。例如，使用`@Route("about")`在视图上将类本地映射到`http://localhost:8080/about`。

顾名思义，`MainView`是您希望在应用程序运行时最初加载的类。为此，添加一个`@Route`注解，将`MainView`映射到您应用程序的根URL：

```java title="MainView.java" {1}
@Route("/")
public class MainView {

  public MainView() {
  }

}
```

对于`FormView`，将视图映射，使其在用户访问`http://localhost:8080/customer`时加载：

```java title="FormView.java" {1}
@Route("customer")
public class FormView {

  public FormView() {
  }

}
```

:::tip 默认行为
如果您没有为`@Route`注解明确分配值，则URL段为类名转换为小写，去掉`View`后缀。

- `MainView`将映射到`/main`
- `FormView`将映射到`/form`
:::

## 共享特性 {#shared-characteristics}

除了都是视图路由外，`MainView`和`FormView`还具有额外的特性。一些共享特性，例如使用`Composite`组件，是使用webforJ应用程序的基础，而另一些只是使管理您的应用程序更容易。

### 使用`Composite`组件 {#using-composite-components}

当应用程序是单页时，您将组件存储在一个`Frame`中。向前看，随着应用程序显示多个视图，您需要将这些UI组件包装在[`Composite`组件](/docs/building-ui/composing-components)内部。

`Composite`组件是包装器，可以轻松创建可重用组件。
要创建`Composite`组件，扩展`Composite`类，并指定一个作为该类基础的绑定组件，例如`Composite<FlexLayout>`。

本教程使用`Div`元素作为绑定组件，但它们可以是任何组件，例如[`FlexLayout`](/docs/components/flex-layout)或[`AppLayout`](/docs/components/app-layout)。使用`getBoundComponent()`方法，您可以引用绑定组件并访问其方法。这让您可以设置大小、添加CSS类名、添加要在`Composite`组件中显示的组件，并访问特定于组件的方法。

对于`MainView`和`FormView`，将`Composite`与`Div`扩展为绑定组件。然后，引用该绑定组件，以便您可以稍后添加UI。这两个视图的结构应类似于以下内容：

```java
// 扩展Composite与绑定组件
public class MainView extends Composite<Div> {

  // 访问绑定组件
  private Div self = getBoundComponent();

  // 创建一个组件UI
  private Button submit = new Button("Submit");

  public MainView() {

    // 将UI组件添加到绑定组件中
    self.add(submit);
  }
}
```

### 设置框架标题 {#setting-the-frame-tile}

当用户在浏览器中有多个标签时，一个唯一的框架标题帮助他们快速识别他们打开的应用程序部分。

[`@FrameTitle`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/FrameTitle.html)注解定义在浏览器的标题或页面标签中显示的内容。对于这两个视图，使用`@FrameTitle`注解添加框架标题：

<Tabs>
  <TabItem value="MainView" label="MainView">
  ```java title="MainView.java" {2}
  @Route("/")
  @FrameTitle("客户表")
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

### 共享CSS {#shared-css}

通过在`MainView`和`FormView`中可以引用的绑定组件，您可以使用CSS进行样式设置。
您可以使用第一步中的CSS，[创建基本应用](/docs/introduction/tutorial/creating-a-basic-app#referencing-a-css-file)，为两个视图提供相同的UI容器样式。
在每个视图的绑定组件中添加CSS类名`card`：

<Tabs>
  <TabItem value="MainView" label="MainView">
    ```java {9} title="MainView.java"
    @Route("/")
    @FrameTitle("客户表")
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

### 使用`CustomerService` {#using-customerservice}

视图的最后一个共享特性是使用`CustomerService`类。
`MainView`中的`Table`显示每个客户，而`FormView`添加新客户。由于两个视图都与客户数据进行交互，因此它们需要访问应用程序的业务逻辑。

视图通过在[处理数据](/docs/introduction/tutorial/working-with-data#creating-a-service)中创建的Spring服务`CustomerService`访问该服务。要在每个视图中使用Spring服务，请将`CustomerService`设为构造函数参数：

<Tabs>
  <TabItem value="MainView" label="MainView">
    ```java {7-8} title="MainView.java"
    @Route("/")
    @FrameTitle("客户表")
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

## 创建`MainView` {#creating-mainview}

在使您的应用可路由、为视图提供`Composite`组件包装器并包括`CustomerService`之后，您准备好构建每个视图独特的UI。如前所述，`MainView`包含在`Application`中的UI组件。这个类还需要一种方法来导航到`FormView`。

### 组合`Table`方法 {#grouping-the-table-methods}

在将组件从`Application`移动到`MainView`的同时，开始对应用程序的部分进行分段，以便一个自定义方法可以一次性更改`Table`是个好主意。现在对代码进行分段可以在应用程序变得更复杂时使其更易于管理。

现在，您的`MainView`构造函数应仅调用一个`buildTable()`方法，该方法添加列、设置大小并引用存储库：

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

### 导航到`FormView`{#navigating-to-formview}

用户需要一种通过UI从`MainView`导航到`FormView`的方法。

在webforJ中，您可以通过使用视图的类直接导航到新视图。通过类而不是URL段进行路由可以确保webforJ能够找到加载视图的正确路径。

要导航到不同的视图，请使用[`Router`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/router/Router.html)类获取当前位置，使用`getCurrent()`，然后使用`navigate()`方法，参数为视图类：

```java
Router.getCurrent().navigate(FormView.class);
```

这段代码将通过编程将用户发送到新的客户表单，但导航需要连接到用户操作。
要允许用户添加新客户，您可以修改或替换`Application`中的信息按钮。该按钮可以导航到`FormView`类，而不是打开消息对话框：

```java
private Button addCustomer = new Button("添加客户", ButtonTheme.PRIMARY,
    e -> Router.getCurrent().navigate(FormView.class));
```

## 完成的`MainView` {#completed-mainview}

通过导航到`FormView`和组合表方法，以下是您在创建`FormView`之前`MainView`应该看起来的样子：

<!-- vale off -->
<ExpandableCode title="MainView.java" language="java" startLine={1} endLine={15}>
{`@Route("/")
  @FrameTitle("客户表")
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
}`}
</ExpandableCode>
<!-- vale on -->

## 创建`FormView` {#creating-formview}

`FormView`将显示一个表单以添加新客户。对于每个客户属性，`FormView`将有一个可编辑的组件供用户交互。此外，它还将有一个按钮让用户提交数据，还有一个取消按钮来放弃数据。

### 创建`Customer`实例 {#creating-a-customer-instance}

当用户正在编辑新客户的数据时，只有在他们准备提交表单时，才能将更改应用于存储库。使用`Customer`对象的实例是一种方便的方式，可以编辑和维护新数据，而无需直接编辑存储库。在`FormView`中创建一个新的`Customer`实例以供表单使用：

```java
private Customer customer = new Customer();
```

为了使`Customer`实例可编辑，除了`id`之外的每个属性都应该与一个可编辑的组件相关联。用户在UI中所做的更改应反映在`Customer`实例中。

### 添加`TextField`组件 {#adding-textfield-components}

`Customer`中的前三个可编辑属性（`firstName`、`lastName`和`company`）都是`String`值，应使用单行文本编辑器表示。[`TextField`](/docs/components/fields/textfield)组件非常适合表示这些属性。

使用`TextField`组件，您可以添加标签以及在字段值更改时触发的事件监听器。每个事件监听器应更新`Customer`实例的相应属性。

添加三个`TextField`组件以更新`Customer`实例：

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
将组件命名为它们所代表的`Customer`实体属性，使将来在[验证和绑定数据](/docs/introduction/tutorial/validating-and-binding-data)步骤中绑定数据变得更容易。
:::

### 添加`ChoiceBox`组件 {#adding-a-choicebox-component}

将`country`属性表示为`TextField`并不是理想选择，因为该属性只能是五个枚举值之一：`UNKNOWN`、`GERMANY`、`ENGLAND`、`ITALY`和`USA`。

选择预定义选项列表的更好组件是[`ChoiceBox`](/docs/components/lists/choicebox)。

`ChoiceBox`组件的每个选项都表示为一个`ListItem`。每个`ListItem`有两个值，一个是`Object`键，另一个是要在UI中显示的`String`文本。为每个选项提供两个值，可让您在内部处理该`Object`，同时为用户在UI中提供更易读的选项。

例如，`Object`键可以是国际标准书号（ISBN），而`String`文本是书名，更容易理解。

```java
new ListItem(isbn, bookTitle);
```

但是，在此应用程序中处理的是国家名称列表，而不是书籍。对于每个`ListItem`，您希望`Object`为`Customer.Country`枚举，而文本则为其`String`表示。

要将所有`country`选项添加到`ChoiceBox`中，您可以使用迭代器为每个`Customer.Country`枚举创建一个`ListItem`，并将它们放入`ArrayList<ListItem>`中。然后，您可以将该`ArrayList<ListItem>`插入`ChoiceBox`组件中：

```java
// 创建ChoiceBox组件
private ChoiceBox country = new ChoiceBox("国家");

// 创建ListItem对象的ArrayList
ArrayList<ListItem> listCountries = new ArrayList<>();

// 添加一个迭代器，为每个Customer.Country选项创建ListItem
for (Country countryItem : Customer.Country.values()) {
  listCountries.add(new ListItem(countryItem, countryItem.toString()));
}

// 将填充的ArrayList插入ChoiceBox
country.insert(listCountries);

// 使表单加载时第一个ListItem成为默认选项
country.selectIndex(0);
```

然后，当用户在`ChoiceBox`中选择一个选项时，`Customer`实例应使用所选项的键进行更新，这个键是一个`Customer.Country`值。

```java
private ChoiceBox country = new ChoiceBox("国家",
    e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));
```

为了保持代码整洁，创建`ArrayList<ListItem>`并将其添加到`ChoiceBox`的迭代器应放在一个单独的方法中。
在添加允许用户选择`country`属性的`ChoiceBox`后，`FormView`应如下所示：

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

### 添加`Button`组件 {#adding-button-components}

使用新客户表单时，用户应该能够保存或放弃他们的更改。
创建两个`Button`组件以实现此功能：

```java
private Button submit = new Button("提交");
private Button cancel = new Button("取消");
```

提交和取消按钮都应使用户返回`MainView`。
这让用户可以立即查看他们的操作结果，无论是看到表格中的新客户还是它保持不变。
由于`FormView`中的多个输入将用户带到`MainView`，因此导航应放在一个可以调用的方法中：

```java
private void navigateToMain(){
  Router.getCurrent().navigate(MainView.class);
}
```

**取消按钮**

放弃表单中的更改不需要额外的事件代码，只需返回`MainView`。但是，由于取消不是主要操作，将按钮的主题设置为轮廓样式可以使提交按钮更突出。
按钮组件页面中的[主题](/docs/components/button#themes)部分列出了所有可用主题。

```java
private Button cancel = new Button("取消", ButtonTheme.OUTLINED_PRIMARY,
    e -> navigateToMain());
```

**提交按钮**

当用户按下提交按钮时，`Customer`实例中的值应被用来在存储库中创建新的条目。

使用`CustomerService`，您可以使用`Customer`实例来更新H2数据库。当执行此操作时，给该`Customer`分配一个新的唯一`id`。更新存储库后，您可以将用户重定向到`MainView`，他们可以在表格中看到新的客户。

```java
private Button submit = new Button("提交", ButtonTheme.PRIMARY,
    e -> submitCustomer());

//...

private void submitCustomer() {
  customerService.createCustomer(customer);
  navigateToMain();
}
```

### 使用`ColumnsLayout` {#using-a-columnslayout}

通过添加`TextField`、`ChoiceBox`和`Button`组件，您现在拥有了表单的所有交互部分。
此步骤中`FormView`的最后一个改进是将这六个组件进行视觉上的组织。

该表单可以使用[`ColumnsLayout`](/docs/components/columns-layout)将组件分成两列，而无需设置任何交互组件的宽度。
要创建`ColumnsLayout`，请指定应在布局内部的每个组件：

```java
private ColumnsLayout layout = new ColumnsLayout(
  firstName, lastName,
  company, country,
  submit, cancel);
```

要设置`ColumnsLayout`的列数，请使用`Breakpoint`对象的`List`。每个`Breakpoint`告诉`ColumnsLayout`根据最小宽度应用指定的列数。通过使用`ColumnsLayout`，您可以创建一个表单，只有在屏幕足够宽以显示两列时才会显示两列。在较小的屏幕上，组件将显示在单一列中。

`ColumnsLayout`文章中的[断点](https://docs/components/columns-layout#breakpoints)部分更详细地解释了断点。

为了保持代码可维护性，将断点设置在单独的方法中。在该方法中，您还可以使用`setSpacing()`方法控制组件之间的水平和垂直间距。

```java
private void setColumnsLayout() {

  // 在宽度大于600px时有两列
  List<Breakpoint> breakpoints = List.of(
    new Breakpoint(600, 2));

  // 添加断点列表
  layout.setBreakpoints(breakpoints);

  // 设置组件之间的间距，使用DWC CSS变量
  layout.setSpacing("var(--dwc-space-l)")
}
```

最后，您可以将新创建的`ColumnsLayout`添加到`FormView`的绑定组件中，同时设置最大宽度，并添加之前的类名：

```java
self.setMaxWidth(600)
  .addClassName("card")
  .add(layout);
```

## 完成的`FormView` {#completed-formview}

在添加了`Customer`实例、交互组件和`ColumnsLayout`之后，您的`FormView`应如下所示：

<!-- vale off -->
<ExpandableCode title="FormView.java" language="java" startLine={1} endLine={15}>
{`@Route("customer")
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
`}
</ExpandableCode>
<!-- vale on -->

## 下一步 {#next-step}

由于用户现在可以添加客户，因此您的应用程序也应该能够使用相同的表单编辑现有客户。在下一步中，[观察者和路由参数](/docs/introduction/tutorial/observers-and-route-parameters)，您将允许客户`id`作为`FormView`的初始参数，从而填充该客户的数据表单并允许用户更改属性。
