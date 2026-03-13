---
title: Routing and Composites
sidebar_position: 4
description: Step 3 - Make your app navigable.
_i18n_hash: 673861b579764a7f9b81512fc0b1e576
---
到目前为止，这个教程仅仅是一个单页面应用。这个步骤将改变这一点。
您将把在[处理数据](/docs/introduction/tutorial/working-with-data)中创建的用户界面移动到自己的页面，并为添加新客户创建另一个页面。
然后，您将连接这些页面，以便您的应用能够通过应用这些概念在它们之间导航：

- [路由](/docs/routing/overview)
- [组合组件](/docs/building-ui/composite-components)
- [`ColumnsLayout`](/docs/components/columns-layout) 组件

完成此步骤将创建[3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites)的一个版本。

<!-- 在这里插入视频 -->

## 运行应用 {#running-the-app}

在开发您的应用时，您可以使用[3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites)作为参考。要查看应用的实际运行：

1. 导航到包含`pom.xml`文件的顶级目录；如果您是按照GitHub上的版本进行的，这就是`3-routing-and-composites`。

2. 使用以下Maven命令在本地运行Spring Boot应用：
    ```bash
    mvn
    ```

运行应用后，会自动在`http://localhost:8080`打开一个新浏览器。

## 可路由的应用 {#routable-apps}

以前，您的应用只有一个功能：显示现有客户数据的表格。
在此步骤中，您的应用还将能够通过添加新客户来修改客户数据。
分离显示和修改的用户界面有利于长期维护和测试，因此您将此功能添加为单独的页面。
您将使您的应用[可路由的](/docs/routing/overview)，以便webforJ可以单独访问和加载这两个用户界面。

可路由的应用是基于URL渲染用户界面的。通过在扩展`App`类的类上注释`@Routify`来启用路由，`packages`元素告诉webforJ哪些包包含用户界面组件。

当您将`@Routify`注释添加到`Application`时，请删除`run()`方法。您将把该方法中的组件移动到您将在`com.webforj.tutorial.views`包中创建的类中。您更新后的`Application.java`文件应如下所示：

```java title="Application.java" {5-6,15}
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")

//添加了@Routify注释
@Routify(packages = "com.webforj.tutorial.views")

@AppProfile(name = "CustomerApplication", shortName = "CustomerApplication")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

// 删除了重写的App.run()方法

}
```

:::tip 全局CSS
在`Application`中保留`@StyleSheet`注释会使该CSS全局应用。
:::

### 创建路由 {#creating-routes}

添加`@Routify`注释使您的应用可路由。 一旦它可路由，您的应用将在`com.webforj.tutorial.views`包中查找路由。
您需要为您的用户界面创建路由，并指定它们的[路由类型](/docs/routing/route-hierarchy/route-types)。路由类型决定了如何将用户界面内容映射到URL。

第一个路由类型是`View`。这种类型的路由直接映射到应用中的特定URL段。表格和新客户表单的用户界面都将是`View`路由。

第二个路由类型是`Layout`，它包含在多个页面上显示的用户界面，例如标题或侧边栏。布局路由也会包装子视图，而不会对URL产生影响。

要指定类的路由类型，请将路由类型作为后缀附加到类名的末尾。
例如，`MainView`是`View`路由类型。

为了将应用的两个功能分开，您的应用需要将用户界面映射到两个唯一的`View`路由：一个用于表格，另一个用于客户表单。在`/src/main/java/com/webforj/tutorial/views`中，创建两个类，并以`View`为后缀：

- **`MainView`**：该视图将具有原本在`Application`类中的`Table`。
- **`FormView`**：该视图将具有一个添加新客户的表单。

### 将URL映射到组件 {#mapping-urls-to-components}

您的应用是可路由的，并知道查找两个`View`路由，`MainView`和`FormView`，但是它没有一个特定的URL来加载它们。通过在视图类上使用`@Route`注释，您可以告诉webforJ根据给定的URL段在哪里加载它。例如，使用`@Route("about")`在视图中将类本地映射到`http://localhost:8080/about`。

顾名思义，`MainView`是您希望在应用运行时最初加载的类。要实现这一点，请添加一个`@Route`注释，将`MainView`映射到应用根URL：

```java title="MainView.java" {1}
@Route("/")
public class MainView {

  public MainView() {
  }

}
```

对于`FormView`，将视图映射到当用户访问`http://localhost:8080/customer`时加载：

```java title="FormView.java" {1}
@Route("customer")
public class FormView {

  public FormView() {
  }

}
```

:::tip 默认行为
如果您没有显式为`@Route`注释分配值，则URL段为类名转换为小写，删除`View`后缀。

- `MainView` 将映射到 `/main`
- `FormView` 将映射到 `/form`
:::

## 共享特性 {#shared-characteristics}

除了都是视图路由外，`MainView`和`FormView`还共享其他特性。其中一些共享特性，例如使用`Composite`组件，是使用webforJ应用的基本要求，而其他的则只是使管理应用更容易。

### 使用`Composite`组件 {#using-composite-components}

当应用为单页时，您将组件存储在`Frame`内部。向前发展，随着多个视图的应用，您需要将这些用户界面组件包装在[`Composite`组件](/docs/building-ui/composite-components)内。

`Composite`组件是包装器，使创建可重用组件变得容易。
要创建一个`Composite`组件，扩展`Composite`类并指定一个作为类基础的绑定组件，例如`Composite<FlexLayout>`。

本教程使用`Div`元素作为绑定组件，但它们可以是任何组件，例如[`FlexLayout`](/docs/components/flex-layout)或[`AppLayout`](/docs/components/app-layout)。使用`getBoundComponent()`方法，您可以引用绑定组件并访问其方法。这使您能够设置大小、添加CSS类名、添加您希望在`Composite`组件中显示的组件，并访问特定于组件的方法。

对于`MainView`和`FormView`，用`Div`扩展`Composite`作为绑定组件。然后，引用该绑定组件，以便稍后添加用户界面。这两个视图的结构应与以下类似：

```java
// 用绑定组件扩展Composite
public class MainView extends Composite<Div> {

  // 访问绑定组件
  private Div self = getBoundComponent();

  // 创建组件用户界面
  private Button submit = new Button("Submit");

  public MainView() {

    // 将用户界面组件添加到绑定组件中
    self.add(submit);
  }
}
```

### 设置框架标题 {#setting-the-frame-tile}

当用户在其浏览器中有多个选项卡时，唯一的框架标题可以帮助他们快速识别他们打开的应用的哪个部分。

[`@FrameTitle`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/FrameTitle.html)注释定义在浏览器的标题或页面选项卡中显示的内容。对于这两个视图，使用`@FrameTitle`注释添加框架标题：

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

通过在`MainView`和`FormView`中引用的绑定组件，您可以使用CSS对其进行样式设置。
您可以使用第一步中[创建基本应用](/docs/introduction/tutorial/creating-a-basic-app#referencing-a-css-file)的CSS为两个视图提供相同的用户界面容器样式。
在每个视图中的绑定组件上添加CSS类名`card`：

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

视图的最后一个共享特征是使用`CustomerService`类。
`MainView`中的`Table`显示每个客户，而`FormView`则添加新客户。由于两个视图都与客户数据交互，因此它们需要访问应用的业务逻辑。

视图通过在[处理数据](/docs/introduction/tutorial/working-with-data#creating-a-service)中创建的Spring服务`CustomerService`获得访问权限。要在每个视图中使用Spring服务，请将`CustomerService`作为构造函数参数：

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

在使您的应用可路由、给视图提供`Composite`组件包装，并包含`CustomerService`之后，您准备好构建每个视图特有的用户界面。如上所述，`MainView`包含最初在`Application`中的用户界面组件。此类还需要能够导航到`FormView`的方法。

### 分组`Table`方法 {#grouping-the-table-methods}

在将组件从`Application`移到`MainView`时，开始对应用的部分进行分块是个好主意，这样一个自定义方法就可以一次性对`Table`进行更改。现在对代码进行分块，使其在应用变得更复杂时更加可管理。

现在，您的`MainView`构造函数只需调用一个`buildTable()`方法，该方法添加列、设置大小并引用存储库：

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

### 导航到`FormView` {#navigating-to-formview}

用户需要一种从`MainView`导航到`FormView`的方式，通过用户界面。

在webforJ中，您可以直接使用视图的类导航到新视图。通过类而不是URL段进行路由可以保证webforJ沿着正确的路径加载视图。

要导航到不同的视图，请使用[`Router`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/router/Router.html)类通过`getCurrent()`获取当前位置信息，然后使用`navigate()`方法，将视图的类作为参数：

```java
Router.getCurrent().navigate(FormView.class);
```

此代码将程序化地将用户引导到新的客户表单，但导航需要与用户操作连接起来。
为了允许用户添加新客户，您可以修改或替换来自`Application`的信息按钮。按钮不再打开消息对话框，而是可以导航到`FormView`类：

```java
private Button addCustomer = new Button("添加客户", ButtonTheme.PRIMARY,
    e -> Router.getCurrent().navigate(FormView.class));
```

## 完成的`MainView` {#completed-mainview}

通过对`FormView`的导航和分组表格方法，以下是`MainView`在继续创建`FormView`之前的样子：

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
`}
</ExpandableCode>
<!-- vale on -->

## 创建`FormView` {#creating-formview}

`FormView`将显示一个表单以添加新客户。对于每个客户属性，`FormView`将具有一个可编辑的组件，供用户进行交互。此外，它将有一个按钮以供用户提交数据，另一个用于取消并丢弃数据。

### 创建`Customer`实例 {#creating-a-customer-instance}

当用户编辑新客户的数据时，只有当他们准备好提交表单时，才应将更改应用于存储库。使用`Customer`对象的实例是一种方便的方法，编辑和维护新数据，而无需直接编辑存储库。在`FormView`中创建一个新的`Customer`实例以供表单使用：

```java
private Customer customer = new Customer();
```

为了使`Customer`实例可编辑，除`id`外的每个属性都应该与一个可编辑组件相关联。用户在UI中的更改应该反映在`Customer`实例中。

### 添加`TextField`组件 {#adding-textfield-components}

`Customer`中的前面三个可编辑属性（`firstName`，`lastName`和`company`）都是`String`值，应该用单行文本编辑器表示。 [`TextField`](/docs/components/fields/textfield)组件是表示这些属性的很好选择。

使用`TextField`组件，您可以添加标签和在字段值变化时触发的事件监听器。每个事件监听器应该在对应的属性上更新`Customer`实例。

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
将组件命名为与它们所代表的`Customer`实体属性相同，可以使未来的绑定数据更方便，[验证和绑定数据](/docs/introduction/tutorial/validating-and-binding-data)的步骤。
:::

### 添加`ChoiceBox`组件 {#adding-a-choicebox-component}

对于`country`属性，使用`TextField`并不理想，因为该属性只能是五个枚举值之一：`UNKNOWN`，`GERMANY`，`ENGLAND`，`ITALY`和`USA`。

选择预定义选项列表的更好组件是[`ChoiceBox`](/docs/components/lists/choicebox)。

`ChoiceBox`组件的每个选项由一个`ListItem`表示。每个`ListItem`具有两个值，一个是`Object`键，另一个是在UI中显示的`String`文本。对于每个选项有两个值，可以在内部处理`Object`，同时在UI中呈现更易读的选项。

例如，`Object`键可以是国际标准书号（ISBN），而`String`文本是书名，这更加人性化。

```java
new ListItem(isbn, bookTitle);
```

然而，这个应用处理的是国家名称列表，而不是书。对于每个`ListItem`，您希望`Object`是`Customer.Country`枚举，而文本可以是其`String`表示形式。

为了将所有`country`选项添加到`ChoiceBox`中，您可以使用迭代器为每个`Customer.Country`枚举创建一个`ListItem`，并将它们放入一个`ArrayList<ListItem>`中。然后，您可以将该`ArrayList<ListItem>`插入到`ChoiceBox`组件中：

```java
// 创建ChoiceBox组件
private ChoiceBox country = new ChoiceBox("国家");

// 创建ListItem对象的ArrayList
ArrayList<ListItem> listCountries = new ArrayList<>();

// 添加迭代器，为每个Customer.Country选项创建ListItem
for (Country countryItem : Customer.Country.values()) {
  listCountries.add(new ListItem(countryItem, countryItem.toString()));
}

// 将填充的ArrayList插入到ChoiceBox中
country.insert(listCountries);

// 将第一个`ListItem`设为表单加载时的默认选项
country.selectIndex(0);
```

然后，当用户在`ChoiceBox`中选择一个选项时，`Customer`实例应使用所选项的键更新，其中是`Customer.Country`值。

```java
private ChoiceBox country = new ChoiceBox("国家",
    e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));
```

为了保持代码的整洁，创建`ArrayList<ListItem>`并将其添加到`ChoiceBox`的迭代器应在单独的方法中。
在您添加允许用户选择`country`属性的`ChoiceBox`后，`FormView`应如下所示：

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

提交和取消按钮都应该将用户返回到`MainView`。
这允许用户立即查看他们的操作结果，无论是看到表格中有新客户，还是保持不变。
由于`FormView`中的多个输入将用户引导到`MainView`，因此导航应放入可回调方法中：

```java
private void navigateToMain(){
  Router.getCurrent().navigate(MainView.class);
}
```

**取消按钮**

放弃表单中的更改不需要额外的事件代码，只需返回`MainView`。不过，由于取消不是主要操作，将按钮的主题设置为轮廓状可以让提交按钮更加突出。
`Button`组件页面的[主题](/docs/components/button#themes)部分列出了所有可用主题。

```java
private Button cancel = new Button("取消", ButtonTheme.OUTLINED_PRIMARY,
    e -> navigateToMain());
```

**提交按钮**

当用户按下提交按钮时，`Customer`实例中的值应该用于在存储库中创建一个新条目。

使用`CustomerService`，您可以将`Customer`实例用于更新H2数据库。当这样做时，将为该`Customer`分配一个新的唯一`id`。更新存储库后，您可以将用户重定向到`MainView`，让他们看到表格中的新客户。

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

通过添加`TextField`、`ChoiceBox`和`Button`组件，您现在具有了表单的所有交互部分。
在此步骤中，对`FormView`的最后一个改进是视觉上组织这六个组件。

该表单可以使用[`ColumnsLayout`](/docs/components/columns-layout)将组件分成两列，而无需设置任何交互组件的宽度。
要创建`ColumnsLayout`，请指定应放在布局中的每个组件：

```java
private ColumnsLayout layout = new ColumnsLayout(
  firstName, lastName,
  company, country,
  submit, cancel);
```

要为`ColumnsLayout`设置列数，请使用`Breakpoint`对象的`List`。每个`Breakpoint`告诉`ColumnsLayout`在应用指定数量的列之前必须具有的最小宽度。通过使用`ColumnsLayout`，您可以制作一个有两列的表单，但只有在屏幕足够宽以显示两列时，组件才会以这种方式显示。在更小的屏幕上，组件以单列显示。

`ColumnsLayout`文章中的[断点](/docs/components/columns-layout#breakpoints)部分进一步解释了断点。

为了保持代码可维护，您可以在单独的方法中设置断点。在该方法中，您还可以使用`setSpacing()`方法控制`ColumnsLayout`内部组件之间的水平和垂直间距。

```java
private void setColumnsLayout() {

  // 如果宽度超过600px，则在ColumnsLayout中有两列
  List<Breakpoint> breakpoints = List.of(
    new Breakpoint(600, 2));

  // 添加断点的List
  layout.setBreakpoints(breakpoints);

  // 使用DWC CSS变量设置组件之间的间距
  layout.setSpacing("var(--dwc-space-l)")
}
```

最后，您可以将新创建的`ColumnsLayout`添加到`FormView`的绑定组件中，同时设置最大宽度，并添加前面提到的类名：

```java
self.setMaxWidth(600)
  .addClassName("card")
  .add(layout);
```

## 完成的`FormView` {#completed-formview}

在添加`Customer`实例、交互组件和`ColumnsLayout`之后，您的`FormView`应如下所示：

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

由于用户现在可以添加客户，因此您的应用应能够使用相同的表单编辑现有客户。在下一步中，[观察者和路由参数](/docs/introduction/tutorial/observers-and-route-parameters)，您将允许客户`id`成为`FormView`的初始参数，以便能够使用该客户的数据填充表单并允许用户更改属性。
