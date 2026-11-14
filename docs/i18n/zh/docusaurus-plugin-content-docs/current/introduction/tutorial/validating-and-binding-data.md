---
title: 数据验证与绑定
sidebar_position: 6
pagination_next: null
description: Step 5 - Add validation checks and bind data to the UI.
_i18n_hash: 97f423188eb353ac27b8e9179e93b224
---
你的应用可以使用 `FormView` 编辑现有客户数据，相关内容请参阅[观察器和路由参数](/docs/introduction/tutorial/observers-and-route-parameters)。本步骤将使用[数据绑定](/docs/data-binding/overview)，将 UI 组件直接连接到数据模型，以自动同步值。这样可以减少应用中的样板代码，并让你为 Spring 实体 `Customer` 添加验证检查，确保用户填写表单时提供完整且准确的信息。本步骤涵盖以下概念：

- [Jakarta 验证](https://beanvalidation.org)
- 使用 [`BindingContext`](https://javadoc.io/doc/com.webforj/webforj-data/latest/com/webforj/data/binding/BindingContext.html) 类

完成本步骤后，你将得到 [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data) 的一个版本。

<!-- <VideoPlayer video="tutorial/validating-and-binding-data" width={1920} height={1080} title="Validating and binding data" /> -->

## 运行应用 {#running-the-app}

开发应用时，可以将 [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data) 作为参考进行比较。要查看应用的运行效果：

1. 转到包含 `pom.xml` 文件的顶层目录。如果你使用 GitHub 上的版本，请转到 `5-validating-and-binding-data` 目录。

2. 使用以下 Maven 命令在本地运行 Spring Boot 应用：
    ```bash
    mvn
    ```

运行应用后，会自动在新浏览器窗口中打开 `http://localhost:8080`。

## 定义验证规则 {#defining-validation-rules}

开发可编辑数据的应用时，应当加入验证。验证检查有助于确保用户提交的数据有意义且准确。如果不加检查，可能会导致问题，因此，实时捕获用户填写表单时可能出现的错误十分重要。

由于不同属性的有效条件可能不同，你需要为每个属性定义有效条件，并在属性值无效时告知用户。幸运的是，使用 [Jakarta Validation](https://beanvalidation.org) 即可轻松实现。Jakarta Validation 允许你通过注解为属性添加约束。

本教程使用两个 Jakarta 注解：`@NotEmpty` 和 `@Pattern`。`@NotEmpty` 用于检查值是否为 null 或空字符串，而 `@Pattern` 用于检查属性是否与指定的正则表达式匹配。这两个注解都允许你添加一条消息，在属性值变为无效时显示。

要确保名和姓必填且只包含字母，同时让公司名称为选填，并允许其中包含字母、数字和空格，请将以下注解应用于 `Customer` 实体：

<!-- vale off -->
<ExpandableCode title="Customer.java" language="java" startLine={8} endLine={28}>

```java
@Entity
@Table(name = "customers")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

// highlight-next-line
  @NotEmpty(message = "Customer first name is required")
// highlight-next-line
  @Pattern(regexp = "[a-zA-Z]*", message = "Invalid characters")
  private String firstName = "";

// highlight-next-line
  @NotEmpty(message = "Customer last name is required")
// highlight-next-line
  @Pattern(regexp = "[a-zA-Z]*", message = "Invalid characters")
  private String lastName = "";

// highlight-next-line
  @Pattern(regexp = "[a-zA-Z0-9 ]*", message = "Invalid characters")
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

</ExpandableCode>
<!-- vale on -->

如需查看完整的验证列表，请参阅 [Jakarta Bean Validation 约束参考](https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/package-summary.html)，或阅读 [webforJ Jakarta Validation 文章](/docs/data-binding/validation/jakarta-validation)了解更多信息。

## 绑定字段 {#binding-the-fields}

要在 `FormView` 的 UI 中使用 `Customer` 的验证检查，你需要创建一个用于数据绑定的 `BindingContext`。在数据绑定之前，`FormView` 中的每个字段都需要一个事件侦听器，以便手动与 Spring 实体 `Customer` 同步。现在，只需在 `FormView` 中创建 `BindingContext`，它就会将 `Customer` 数据模型绑定到 UI 组件并自动同步。

### 创建 `BindingContext` {#creating-a-bindingcontext}

`BindingContext` 实例需要传入要与绑定同步的 Spring bean。在 `FormView` 中，使用 `Customer` 实体声明一个 `BindingContext`：

```java title="FormView.java" {4}
public class FormView extends Composite<Div> implements WillEnterObserver {
  private final CustomerService customerService;

  private BindingContext<Customer> context;

  Customer customer = new Customer();
```

然后，要根据组件名称自动将 UI 组件绑定到 bean 属性，请使用 `BindingContext.of()` 并传入以下参数：

- **`this`**：之前已将 `context` 声明为 `BindingContext`。第一个参数指定包含可绑定组件的对象。
- **`Customer.class`**：第二个参数是用于绑定的 bean 类。
- **`true`**：第三个参数启用 Jakarta 验证，使上下文能够使用为 `Customer` 设置的验证规则。这样会改变无效组件的样式，并显示已设置的消息。

合起来，代码如下：

```java
context = BindingContext.of(this, Customer.class, true);
```

### 让表单具备响应能力 {#making-the-form-responsive}

使用数据绑定后，应用现在会自动执行验证检查。你可以为这些检查添加事件侦听器，以阻止用户提交无效表单。添加以下代码，让提交按钮仅在表单有效时启用：

```java {2}
context = BindingContext.of(this, Customer.class, true);
context.onValidate(e -> submit.setEnabled(e.isValid()));
```

### 移除组件的事件侦听器 {#removing-event-listeners-for-components}

现在，每次 UI 发生变化，都会自动与 `BindingContext` 同步。这意味着你可以移除各字段的事件侦听器：

**之前**
```java title="FormView.java"
// Without data binding
TextField firstName = new TextField("First Name", e -> customer.setFirstName(e.getValue()));
TextField lastName = new TextField("Last Name", e -> customer.setLastName(e.getValue()));
TextField company = new TextField("Company", e -> customer.setCompany(e.getValue()));
ChoiceBox country = new ChoiceBox("Country",
    e -> customer.setCountry(Country.valueOf(e.getSelectedItem().getText())));
```

**之后**
```java title="FormView.java"
// With data binding
TextField firstName = new TextField("First Name");
TextField lastName = new TextField("Last Name");
TextField company = new TextField("Company");
ChoiceBox country = new ChoiceBox("Country");
```

### 按属性名称绑定 {#binding-by-property-names}

由于每个组件的名称都与数据模型中的属性名称匹配，webforJ 应用了[自动绑定](/docs/data-binding/automatic-binding)。如果名称不匹配，可以使用 `@UseProperty` 注解进行映射。

```java
@UseProperty("firstName")
TextField firstNameField = new TextField("First Name");
```

### 在 `fillForm()` 方法中读取数据 {#reading-data-in-the-fillForm()-method}

以前，在 `fillForm()` 方法中，你需要手动从 `Customer` 副本中获取数据，并初始化每个组件的值。现在，由于使用了 `BindingContext`，可以改用 `read()` 方法。此方法会使用 `Customer` 副本中的相关属性填充每个已绑定组件。

在 `fillForm()` 方法中，将 `setValue()` 方法替换为 `read()`：

```java title="FormView.java" {6}
public void fillForm(Long customerId) {
  customer = customerService.getCustomerByKey(customerId);

  // Removed each setValue() method for the UI components

    context.read(customer);
  }
```

### 为 `submitCustomer()` 添加验证 {#adding-validation-to-submitcustomer}

本步骤对 `FormView` 的最后一项修改，是为 `submitCustomer()` 方法添加保护措施。在将更改提交到 H2 数据库之前，应用会使用 `write()` 方法对绑定上下文的结果执行最终验证。

`write()` 方法会使用 `BindingContext` 中已绑定的 UI 组件更新 bean 的属性，并返回一个 `ValidationResult`。

使用 `write()` 方法，通过 `FormView` 中已绑定的组件将数据写入 `Customer` 副本。然后，如果返回的 `ValidationResult` 有效，就使用写入的数据更新 H2 数据库。

```java title="FormView.java" {2-3}
private void submitCustomer() {
  ValidationResult results = context.write(customer);
  if (results.isValid()) {
    if (customerService.doesCustomerExist(customerId)) {
      customerService.updateCustomer(customer);
    } else {
      customerService.createCustomer(customer);
    }
    navigateToMain();
  }
}
```

### 完成后的 `FormView` {#completed-formview}

完成这些修改后，`FormView` 如下所示。现在，应用支持使用 Spring Boot 和 webforJ 进行数据绑定和验证。表单输入会自动与模型同步，并根据验证规则进行检查。

<!-- vale off -->
<ExpandableCode title="FormView.java" language="java" startLine={1} endLine={15}>

```java
@Route("customer/:id?<[0-9]+>")
@FrameTitle("Customer Form")
public class FormView extends Composite<Div> implements WillEnterObserver {
  private final CustomerService customerService;
  private BindingContext<Customer> context;
  private Customer customer = new Customer();
  private Long customerId = 0L;
  private Div self = getBoundComponent();
  private TextField firstName = new TextField("First Name");
  private TextField lastName = new TextField("Last Name");
  private TextField company = new TextField("Company");
  private ChoiceBox country = new ChoiceBox("Country");
  private Button submit = new Button("Submit", ButtonTheme.PRIMARY, e -> submitCustomer());
  private Button cancel = new Button("Cancel", ButtonTheme.OUTLINED_PRIMARY, e -> navigateToMain());
  private ColumnsLayout layout = new ColumnsLayout(
      firstName, lastName,
      company, country,
      submit, cancel);

  public FormView(CustomerService customerService) {
    this.customerService = customerService;
    context = BindingContext.of(this, Customer.class, true);
    context.onValidate(e -> submit.setEnabled(e.isValid()));
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
    ValidationResult results = context.write(customer);
    if (results.isValid()) {
      if (customerService.doesCustomerExist(customerId)) {
        customerService.updateCustomer(customer);
      } else {
        customerService.createCustomer(customer);
      }
      navigateToMain();
    }
  }

  private void navigateToMain() {
    Router.getCurrent().navigate(MainView.class);
  }

  @Override
  public void onWillEnter(WillEnterEvent event, ParametersBag parameters) {
    parameters.getInt("id").ifPresentOrElse(id -> {
      customerId = Long.valueOf(id);
      if (customerService.doesCustomerExist(customerId)) {
        event.accept();
        fillForm(customerId);
      } else {
        event.reject();
        navigateToMain();
      }

    }, () -> event.accept());
  }

  public void fillForm(Long customerId) {
    customer = customerService.getCustomerByKey(customerId);
    context.read(customer);
  }
}
```

</ExpandableCode>
<!-- vale on -->

## 下一步 {#next-step}

下一步[集成应用布局](/docs/introduction/tutorial/integrating-an-app-layout)将介绍如何使用 `AppLayout` 添加侧边菜单，让用户在客户表格页和客户表单页都能访问该菜单。你还将了解另一个布局工具：`FlexLayout` 组件。
