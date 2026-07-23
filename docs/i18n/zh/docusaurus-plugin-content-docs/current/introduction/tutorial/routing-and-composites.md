---
title: Routing and Composites
sidebar_position: 4
description: Step 3 - Make your app navigable.
_i18n_hash: b6d14f241d64208bfcfff527691bf8e9
---
到目前为止，本教程仅是一个单页面应用程序。这一步骤将改变这一点。您将把在[处理数据](/docs/introduction/tutorial/working-with-data)中创建的UI移动到自己的页面，并为添加新客户创建另一个页面。接下来，您将连接这些页面，使您的应用能够通过应用以下概念在它们之间导航：

- [路由](/docs/routing/overview)
- [组合组件](/docs/building-ui/composing-components)
- [`ColumnsLayout`](/docs/components/columns-layout) 组件

完成此步骤后，您将创建一个[3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites)版本。

<!-- 在此插入视频 -->

## 运行应用程序 {#running-the-app}

在开发应用程序时，您可以使用[3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites)作为比较。要查看应用程序的运行情况：

1. 导航到包含`pom.xml`文件的顶级目录；如果您在跟随GitHub上的版本，则为`3-routing-and-composites`。

2. 使用以下Maven命令在本地运行Spring Boot应用程序：
    ```bash
    mvn
    ```

运行应用程序会自动在`http://localhost:8080`打开一个新浏览器。

## 可路由应用程序 {#routable-apps}

之前，您的应用仅有一个功能：显示现有客户数据的表。在此步骤中，您的应用还将能够通过添加新客户来修改客户数据。将显示和修改的UI分开对长期维护和测试是有利的，因此您将此功能添加为单独的页面。您将使您的应用变得[可路由](/docs/routing/overview)，这样webforJ就可以单独访问并加载这两个UI。

可路由应用程序根据URL呈现UI。通过用[`@Routify`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/Routify.html)注解扩展`App`类的类，可以启用路由。`packages`元素告诉webforJ哪些包包含UI组件。

当您将`@Routify`注解添加到`Application`时，请删除`run()`方法。您将把该方法中的组件移动到您将在`com.webforj.tutorial.views`包中创建的类中。您更新后的`Application.java`文件应如下所示：

```java title="Application.java" {5-6,15}
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")

//添加了 @Routify注解
@Routify(packages = "com.webforj.tutorial.views")

@AppProfile(name = "CustomerApplication", shortName = "CustomerApplication")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

// 删除了被覆盖的 App.run() 方法

}
```

:::tip 全局 CSS
在`Application`中保留`@StyleSheet`注解将CSS应用于全局。
:::

### 创建路由 {#creating-routes}

添加`@Routify`注解使您的应用可路由。一旦可路由，您的应用将查看`com.webforj.tutorial.views`包中的路由。您需要为您的UI创建路由，并指定它们的[路由类型](/docs/routing/route-hierarchy/route-types)。路由类型决定如何将UI内容映射到URL。

第一种路由类型是`View`。这种类型的路由直接映射到应用中的特定URL片段。表和新客户表单的UI都将是`View`路由。

第二种路由类型是`Layout`，它包含在多个页面上出现的UI，例如标题或侧边栏。布局路由还包裹子视图，而不会对URL做出贡献。

要指定类的路由类型，您可以在类名末尾添加路由类型作为后缀。例如，`MainView`是一个`View`路由类型。

为了使应用的两个功能分开，您的应用需要将UI映射到两个唯一的`View`路由：一个用于表，另一个用于客户表单。在`/src/main/java/com/webforj/tutorial/views`中，创建两个后缀为`View`的类：

- **`MainView`**：该视图将包含之前在`Application`类中的`Table`。
- **`FormView`**：该视图将包含用于添加新客户的表单。

### 将URL映射到组件 {#mapping-urls-to-components}

您的应用是可路由的，并知道要寻找两个`View`路由，`MainView`和`FormView`，但它没有特定的URL来加载它们。通过在视图类上使用`@Route`注解，您可以告诉webforJ在给定的URL片段基础上在哪里加载它。例如，使用`@Route("about")`在视图中本地将类映射到`http://localhost:8080/about`。

顾名思义，`MainView`是您希望在应用运行时最初加载的类。为此，添加一个`@Route`注解，将`MainView`映射到应用的根URL：

```java title="MainView.java" {1}
@Route("/")
public class MainView {

  public MainView() {
  }

}
```

对于`FormView`，将视图映射为当用户访问`http://localhost:8080/customer`时加载：

```java title="FormView.java" {1}
@Route("customer")
public class FormView {

  public FormView() {
  }

}
```

:::tip 默认行为
如果您未显式分配`@Route`注解的值，则URL片段为类名转换为小写，并删除`View`后缀。

- `MainView`将映射到`/main`
- `FormView`将映射到`/form`
:::

## 共享特征 {#shared-characteristics}

除了都是视图路由外，`MainView`和`FormView`共享其他特征。其中一些共享特性，如使用`Composite`组件，是使用webforJ应用程序的基本要素，而其他的一些则使管理您的应用程序更容易。

### 使用`Composite`组件 {#using-composite-components}

当应用是单页时，您将组件存储在`Frame`中。往前看，随着应用拥有多个视图，您需要将这些UI组件封装在[`Composite`组件](/docs/building-ui/composing-components)中。

`Composite`组件是包装器，使得创建可重用组件变得轻松。要创建`Composite`组件，请使用一个指定的绑定组件扩展`Composite`类，该组件作为类的基础，例如，`Composite<FlexLayout>`。

本教程使用`Div`元素作为绑定组件，但它们可以是任何组件，例如[`FlexLayout`](/docs/components/flex-layout)或[`AppLayout`](/docs/components/app-layout)。使用`getBoundComponent()`方法，您可以引用绑定组件并访问其方法。这让您可以设置大小、添加CSS类名、添加要在`Composite`组件中显示的组件，并访问特定于组件的方法。

对于`MainView`和`FormView`，将`Composite`与`Div`作为绑定组件进行扩展。然后，引用该绑定组件，以便您可以稍后添加UI。两个视图应看起来类似于以下结构：

```java
// 使用绑定组件扩展 Composite
public class MainView extends Composite<Div> {

  // 访问绑定组件
  private Div self = getBoundComponent();

  // 创建组件UI
  private Button submit = new Button("Submit");

  public MainView() {

    // 将UI组件添加到绑定组件
    self.add(submit);
  }
}
```

### 设置框架标题 {#setting-the-frame-tile}

当用户在浏览器中打开多个标签时，唯一的框架标题可以帮助他们快速识别他们所打开的应用程序部分。

[`@FrameTitle`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/FrameTitle.html)注解定义在浏览器的标题或页面的标签中显示的内容。对于两个视图，请使用`@FrameTitle`注解添加框架标题：

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

### 共享 CSS {#shared-css}

在`MainView`和`FormView`中可以引用的绑定组件，您可以用CSS进行样式设置。您可以使用第一步中的CSS，[创建基本应用程序](/docs/introduction/tutorial/creating-a-basic-app#referencing-a-css-file)，为两个视图提供相同的UI容器样式。在每个视图中的绑定组件上添加CSS类名`card`：

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

两个视图的最后一个共享特征是使用`CustomerService`类。`MainView`中的`Table`显示每个客户，而`FormView`则添加新客户。因为两个视图都与客户数据交互，它们需要访问应用的业务逻辑。

通过在[处理数据](/docs/introduction/tutorial/working-with-data#creating-a-service)中创建的Spring服务，视图可以访问`CustomerService`。要在每个视图中使用Spring服务，请将`CustomerService`作为构造函数参数：

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

在使您的应用可路由、给视图添加`Composite`组件包装器并包括`CustomerService`后，您准备好构建每个视图独特的UI。如前所述，`MainView`包含最初在`Application`中的UI组件。此类还需要一种导航到`FormView`的方法。

### 组合`Table`方法 {#grouping-the-table-methods}

在将组件从`Application`移动到`MainView`时，最好开始将应用的某些部分分区，以便一个自定义方法可以一次性对`Table`进行更改。现在对代码的分区可以使其在应用趋于复杂时更易于管理。

现在，您的`MainView`构造函数只应调用一个`buildTable()`方法，该方法添加列、设置大小并引用存储库：

```java
private void buildTable() {
  table.setSize("1000px", "294px");
  table.setMaxWidth("90vw");
  table.addColumn("firstName", Customer::getFirstName).setLabel("名");
  table.addColumn("lastName", Customer::getLastName).setLabel("姓");
  table.addColumn("company", Customer::getCompany).setLabel("公司");
  table.addColumn("country", Customer::getCountry).setLabel("国家");
  table.setColumnsToAutoFit();
  table.getColumns().forEach(column -> column.setSortable(true));
  table.setRepository(customerService.getRepositoryAdapter());
}
```

### 导航到`FormView` {#navigating-to-formview}

用户需要一种方式通过用户界面从`MainView`导航到`FormView`。

在webforJ中，您可以使用视图的类直接导航到新视图。通过类而不是URL片段进行路由可确保webforJ正确加载视图。

要导航到不同的视图，请使用[`Router`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/router/Router.html)类，以`getCurrent()`获取当前位置，然后使用`navigate()`方法，将视图的类作为参数：

```java
Router.getCurrent().navigate(FormView.class);
```

这段代码将程序性地将用户发送到新客户表单，但导航需要连接到用户操作。为了允许用户添加新客户，您可以修改或替换`Application`中的信息按钮。而不是打开消息对话框，按钮可以导航到`FormView`类：

```java
private Button addCustomer = new Button("添加客户", ButtonTheme.PRIMARY,
    e -> Router.getCurrent().navigate(FormView.class));
```

## 完成的`MainView` {#completed-mainview}

通过导航到`FormView`和组合表的方法，以下是`MainView`在继续创建`FormView`之前的样子：

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
      table.addColumn("firstName", Customer::getFirstName).setLabel("名");
      table.addColumn("lastName", Customer::getLastName).setLabel("姓");
      table.addColumn("company", Customer::getCompany).setLabel("公司");
      table.addColumn("country", Customer::getCountry).setLabel("国家");
      table.setColumnsToAutoFit();
      table.setColumnsToResizable(false);
      table.getColumns().forEach(column -> column.setSortable(true));
      table.setRepository(customerService.getRepositoryAdapter());
    }

  }
`}
</ExpandableCode>
<!-- vale on -->

## 创建`FormView` {#creating-formview}

`FormView`将显示用于添加新客户的表单。对于每个客户属性，`FormView`将具有一个可编辑组件供用户与之交互。此外，它将有一个按钮供用户提交数据，以及一个取消按钮以放弃数据。

### 创建`Customer`实例 {#creating-a-customer-instance}

当用户正在编辑新客户的数据时，只有在他们准备好提交表单时，才能将更改应用于存储库。使用`Customer`对象的实例是编辑和维护新数据的一个方便方法。请在`FormView`中创建一个新的`Customer`以用于该表单：

```java
private Customer customer = new Customer();
```

要使`Customer`实例可编辑，除了`id`外，每个属性都应与一个可编辑组件关联。用户在UI中所做的更改应反映在`Customer`实例中。

### 添加`TextField`组件 {#adding-textfield-components}

`Customer`中的前三个可编辑属性（`firstName`、`lastName`和`company`）都是`String`值，应该用单行文本编辑器表示。[`TextField`](/docs/components/fields/textfield)组件非常适合表示这些属性。

使用`TextField`组件，您可以添加一个标签和一个事件监听器，当字段值更改时触发。每个事件监听器应更新对应属性的`Customer`实例。

添加三个`TextField`组件来更新`Customer`实例：

```java title="FormView.java" {6-8}
public class FormView extends Composite<Div> {
  private final CustomerService customerService;
  private Customer customer = new Customer();
  private Div self = getBoundComponent();

  private TextField firstName = new TextField("名", e -> customer.setFirstName(e.getValue()));
  private TextField lastName = new TextField("姓", e -> customer.setLastName(e.getValue()));
  private TextField company = new TextField("公司", e -> customer.setCompany(e.getValue()));

  public FormView(CustomerService customerService) {
    this.customerService = customerService;
    self.addClassName("card");
  }
}
```

:::tip 共享命名约定
将组件命名为与它们所表示的`Customer`实体的属性相同，使得在将来的步骤中绑定数据变得更容易，[验证与绑定数据](/docs/introduction/tutorial/validating-and-binding-data)。
:::

### 添加`ChoiceBox`组件 {#adding-a-choicebox-component}

使用`TextField`为`country`属性并不理想，因为该属性只能是五个枚举值之一：`UNKNOWN`、`GERMANY`、`ENGLAND`、`ITALY`和`USA`。

一个更好的选择是[`ChoiceBox`](/docs/components/lists/choicebox)组件用来从预定义的选项列表中进行选择。

每个`ChoiceBox`组件的选项表示为`ListItem`。每个`ListItem`有两个值，一个`Object`键和一个用于在UI中显示的`String`文本。为每个选项拥有两个值使得您可以在内部处理`Object`，同时在UI中呈现更具可读性的选项。

例如，`Object`键可以是国际标准书号（ISBN），而`String`文本是更人性化的书名。

```java
new ListItem(isbn, bookTitle);
```

然而，这个应用程序处理的是国家名称的列表，而不是书籍。对于每个`ListItem`，您希望`Object`是`Customer.Country`枚举，而文本可以是其`String`表示。

要将所有`country`选项添加到`ChoiceBox`中，您可以使用迭代器为每个`Customer.Country`枚举创建一个`ListItem`，并将它们放入一个`ArrayList<ListItem>`中。然后，您可以将该`ArrayList<ListItem>`插入到`ChoiceBox`组件中：

```java
// 创建 ChoiceBox 组件
private ChoiceBox country = new ChoiceBox("国家");

// 创建 ListItem对象的 ArrayList
ArrayList<ListItem> listCountries = new ArrayList<>();

// 添加一个迭代器为每个 Customer.Country 选项创建 ListItem
for (Country countryItem : Customer.Country.values()) {
  listCountries.add(new ListItem(countryItem, countryItem.toString()));
}

// 将填充的 ArrayList 插入到 ChoiceBox
country.insert(listCountries);

// 使第一个`ListItem`在表单加载时为默认值
country.selectIndex(0);
```

然后，当用户在`ChoiceBox`中选择一个选项时，`Customer`实例应使用所选项的键更新，这就是一个`Customer.Country`值。

```java
private ChoiceBox country = new ChoiceBox("国家",
    e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));
```

为了保持代码整洁，创建`ArrayList<ListItem>`并将其添加到`ChoiceBox`的迭代器应该放在一个单独的方法中。在您添加一个允许用户选择`country`属性的`ChoiceBox`后，`FormView`应该如下所示：

```java title="FormView.java" {9-10,15,18-25}
public class FormView extends Composite<Div> {
  private final CustomerService customerService;
  private Customer customer = new Customer();
  private Div self = getBoundComponent();
  private TextField firstName = new TextField("名", e -> customer.setFirstName(e.getValue()));
  private TextField lastName = new TextField("姓", e -> customer.setLastName(e.getValue()));
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

在使用新客户表单时，用户应该能够保存或放弃他们的更改。创建两个`Button`组件以实现此功能：

```java
private Button submit = new Button("提交");
private Button cancel = new Button("取消");
```

无论是提交还是取消按钮都应该让用户返回`MainView`。这使得用户可以立即看到他们的操作结果，无论是看到新客户在表中，还是保持不变。由于`FormView`中的多个输入将用户带到`MainView`，因此导航代码应该放入一个可调用的方法中：

```java
private void navigateToMain(){
  Router.getCurrent().navigate(MainView.class);
}
```

**取消按钮**

放弃表单上的更改不需要在事件中做任何额外代码，仅需返回`MainView`。但是，由于取消并不是主要操作，将按钮的主题设置为轮廓样式，可以让提交按钮更为突出。[按钮](/docs/components/button#themes)组件页面的主题部分列出了所有可用的主题。

```java
private Button cancel = new Button("取消", ButtonTheme.OUTLINED_PRIMARY,
    e -> navigateToMain());
```

**提交按钮**

当用户按下提交按钮时，`Customer`实例中的值应用于在存储库中创建一个新条目。

使用`CustomerService`，您可以使用`Customer`实例来更新H2数据库。当此操作发生时，将为该`Customer`分配一个新的唯一`id`。更新存储库后，您可以将用户重定向到`MainView`，在该视图中可以看到新的客户出现在表中。

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

添加`TextField`、`ChoiceBox`和`Button`组件后，您现在拥有了表单的所有交互部分。在此步骤中，对`FormView`的最后改进是对六个组件进行视觉组织。

该表单可以使用[`ColumnsLayout`](/docs/components/columns-layout)将组件分成两列，而无需设置任何交互组件的宽度。要创建`ColumnsLayout`，指定每个组件应放置在布局中：

```java
private ColumnsLayout layout = new ColumnsLayout(
  firstName, lastName,
  company, country,
  submit, cancel);
```

要为`ColumnsLayout`设置列数，请使用`Breakpoint`对象的`List`。每个`Breakpoint`告诉`ColumnsLayout`必须具有的最小宽度，以应用指定数量的列。通过使用`ColumnsLayout`，您可以创建一个具有两列的表单，但仅在屏幕宽度足以显示两列时才适用。在较小的屏幕上，组件将显示在一列中。

`ColumnsLayout`文章中的[断点](/docs/components/columns-layout#breakpoints)部分详细解释了断点。

为了保持代码的可维护性，在一个单独的方法中设置断点。在该方法中，您还可以使用`setSpacing()`方法控制`ColumnsLayout`组件之间的水平和垂直间距。

```java
private void setColumnsLayout() {

  // 如果宽度超过600px，则在ColumnsLayout中有两列
  List<Breakpoint> breakpoints = List.of(
    new Breakpoint(600, 2));

  // 添加断点列表
  layout.setBreakpoints(breakpoints);

  // 使用DWC CSS变量设置组件之间的间距
  layout.setSpacing("var(--dwc-space-l)")
}
```

最后，您可以将新创建的`ColumnsLayout`添加到`FormView`的绑定组件中，同时设置最大宽度并添加之前的类名：

```java
self.setMaxWidth(600)
  .addClassName("card")
  .add(layout);
```

## 完成的`FormView` {#completed-formview}

在添加`Customer`实例、交互组件和`ColumnsLayout`后，您的`FormView`应该看起来如下：

<!-- vale off -->
<ExpandableCode title="FormView.java" language="java" startLine={1} endLine={15}>
{`@Route("customer")
  @FrameTitle("客户表单")
  public class FormView extends Composite<Div> {
    private final CustomerService customerService;
    private Customer customer = new Customer();
    private Div self = getBoundComponent();
    private TextField firstName = new TextField("名", e -> customer.setFirstName(e.getValue()));
    private TextField lastName = new TextField("姓", e -> customer.setLastName(e.getValue()));
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

由于用户现在可以添加客户，您的应用应该能够使用相同的表单编辑现有客户。在下一步中，[观察者与路由参数](/docs/introduction/tutorial/observers-and-route-parameters)，您将允许客户`id`成为`FormView`的初始参数，以便它可以用该客户的数据填充表单并允许用户更改属性。
