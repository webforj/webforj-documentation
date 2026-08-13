---
title: Validating and Binding Data
sidebar_position: 6
pagination_next: null
description: Step 5 - Add validation checks and bind data to the UI.
_i18n_hash: b99d289f94de614d85524e9846bdcd92
---
您的应用程序来自 [Observers and Route Parameters](/docs/introduction/tutorial/observers-and-route-parameters)，可以使用 `FormView` 来编辑现有客户数据。此步骤使用 [数据绑定](/docs/data-binding/overview)，将 UI 组件直接连接到数据模型，以实现自动值同步。这减少了应用程序中的样板代码，并使您能够在 Spring 实体 `Customer` 中添加验证检查，从而要求用户在填写表单时提供完整和准确的信息。此步骤涵盖以下概念：

- [Jakarta 验证](https://beanvalidation.org)
- 使用 [`BindingContext`](https://javadoc.io/doc/com.webforj/webforj-data/latest/com/webforj/data/binding/BindingContext.html) 类

完成此步骤后会创建一个 [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data) 的版本。

## 运行应用程序 {#running-the-app}

在您开发应用程序时，可以将 [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data) 作为比较。要查看应用程序的运行情况，请执行以下操作：

1. 导航到包含 `pom.xml` 文件的顶层目录，如果您跟随 GitHub 上的版本，则为 `5-validating-and-binding-data`。

2. 使用以下 Maven 命令在本地运行 Spring Boot 应用程序：
    ```bash
    mvn
    ```

运行应用程序将自动在 `http://localhost:8080` 打开一个新浏览器。

## 定义验证规则 {#defining-validation-rules}

开发具有可编辑数据的应用程序应该包括验证。验证检查有助于维护用户提交的有效和准确的数据。如果不加以检查，可能会导致问题，因此重要的是实时捕捉用户在填写表单时可能犯的错误。

由于不同属性之间的有效性标准可能有所不同，因此您需要定义每个属性的有效标准，并告知用户是否有不合规的内容。幸运的是，您可以很容易地通过 [Jakarta 验证](https://beanvalidation.org) 来实现。Jakarta 验证允许您将约束作为注解添加到属性上。

本教程使用两个 Jakarta 注解，`@NotEmpty` 和 `@Pattern`。`@NotEmpty` 检查空值和空字符串，而 `@Pattern` 检查属性是否匹配您设置的正则表达式。这两个注解都允许您添加消息，以在属性变为无效时显示。

要要求名和姓都是强制性的并且仅包含字母，同时让公司名称可选并允许字母、数字和空格，请将以下注解应用于 `Customer` 实体：

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

请参阅 [Jakarta Bean Validation 约束参考](https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/package-summary.html) 以获取完整的验证列表，或者从 [webforJ Jakarta Validation 文章](/docs/data-binding/validation/jakarta-validation) 获得更多信息。

## 绑定字段 {#binding-the-fields}

要在 `FormView` 中使用 `Customer` 的验证检查进行数据绑定，您需要创建一个 `BindingContext`。在进行数据绑定之前，`FormView` 中的每个字段都需要事件监听器来手动与 Spring 实体 `Customer` 进行同步。在 `FormView` 中创建 `BindingContext` 会将 `Customer` 数据模型绑定并自动同步到 UI 组件。

### 创建 `BindingContext` {#creating-a-bindingcontext}

`BindingContext` 的一个实例需要与之同步的 Spring bean。在 `FormView` 中，使用 `Customer` 实体声明一个 `BindingContext`：

```java title="FormView.java" {4}
public class FormView extends Composite<Div> implements WillEnterObserver {
  private final CustomerService customerService;

  private BindingContext<Customer> context;

  Customer customer = new Customer();
```

然后，使用 `BindingContext.of()` 自动将 UI 组件绑定到基于其名称的 bean 属性，使用以下参数：

- **`this`** : 此前，您将 `context` 声明为 `BindingContext`。第一个参数设置包含可绑定组件的对象。
- **`Customer.class`** : 第二个参数是用于绑定的 bean 的类。
- **`true`** : 第三个参数启用 Jakarta 验证，允许上下文使用您为 `Customer` 设置的验证。这样做将改变无效组件的样式并显示设置的消息。

所有代码将如下所示：

```java
context = BindingContext.of(this, Customer.class, true);
```

### 使表单响应 {#making-the-form-responsive}

通过数据绑定，您的应用程序现在会自动执行验证检查。通过向检查添加事件监听器，可以防止用户提交无效表单。添加以下内容，使提交按钮仅在表单有效时可用：

```java {2}
context = BindingContext.of(this, Customer.class, true);
context.onValidate(e -> submit.setEnabled(e.isValid()));
```

### 删除组件的事件监听器 {#removing-event-listeners-for-components}

现在每次 UI 更改都会自动与 `BindingContext` 同步。这意味着您现在可以删除每个字段的事件监听器：

**之前**
```java title="FormView.java"
// 没有数据绑定
TextField firstName = new TextField("First Name", e -> customer.setFirstName(e.getValue()));
TextField lastName = new TextField("Last Name", e -> customer.setLastName(e.getValue()));
TextField company = new TextField("Company", e -> customer.setCompany(e.getValue()));
ChoiceBox country = new ChoiceBox("Country",
    e -> customer.setCountry(Country.valueOf(e.getSelectedItem().getText())));
```

**之后**
```java title="FormView.java"
// 使用数据绑定
TextField firstName = new TextField("First Name");
TextField lastName = new TextField("Last Name");
TextField company = new TextField("Company");
ChoiceBox country = new ChoiceBox("Country");
```

### 按属性名称绑定 {#binding-by-property-names}

由于每个组件的名称与数据模型相匹配，webforJ 应用了 [自动绑定](/docs/data-binding/automatic-binding)。如果名称不匹配，您可以使用 `@UseProperty` 注解将它们进行映射。

```java
@UseProperty("firstName")
TextField firstNameField = new TextField("First Name");
```

### 在 `fillForm()` 方法中读取数据 {#reading-data-in-the-fillForm()-method}

之前，在 `fillForm()` 方法中，您通过手动从 `Customer` 副本中检索数据来初始化每个组件的值。但现在，由于您使用 `BindingContext`，可以使用 `read()` 方法。此方法将每个绑定组件填充与 `Customer` 副本中的相关属性。

在 `fillForm()` 方法中，用 `read()` 替换 `setValue()` 方法：

```java title="FormView.java" {6}
public void fillForm(Long customerId) {
  customer = customerService.getCustomerByKey(customerId);

  // 移除每个 UI 组件的 setValue() 方法

    context.read(customer);
  }
```

### 向 `submitCustomer()` 添加验证 {#adding-validation-to-submitcustomer}

此步骤对 `FormView` 的最后修改是向 `submitCustomer()` 方法添加保护措施。在将更改提交到 H2 数据库之前，应用程序将对绑定上下文的结果执行最终验证，使用 `write()` 方法。

`write()` 方法使用 `BindingContext` 中绑定的 UI 组件更新 bean 的属性，并返回 `ValidationResult`。

使用 `write()` 方法使用 `FormView` 中绑定的组件将数据写入 `Customer` 副本。然后，如果返回的 `ValidationResult` 是有效的，使用写入的数据更新 H2 数据库。

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

### 完成的 `FormView` {#completed-formview}

通过这些更改，`FormView` 的样子如下。该应用程序现在支持使用 Spring Boot 和 webforJ 的数据绑定和验证。表单输入会自动与模型同步并根据验证规则进行检查。

```java title="FormView.java" {1-15}
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

## 下一步 {#next-step}

下一步 [Integrating an App Layout](/docs/introduction/tutorial/integrating-an-app-layout) 将重点介绍使用 `AppLayout` 添加侧边菜单，该菜单对客户表和客户表单页面的用户可用。您还将学习另一种布局工具，`FlexLayout` 组件。
