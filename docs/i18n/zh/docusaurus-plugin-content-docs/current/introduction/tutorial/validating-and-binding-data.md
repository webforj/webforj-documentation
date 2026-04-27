---
title: Validating and Binding Data
sidebar_position: 6
pagination_next: null
description: Step 5 - Add validation checks and bind data to the UI.
_i18n_hash: bb0d88755455ff4e639e598c104b6d68
---
您的应用程序可以使用 `FormView` 来编辑现有客户数据。此步骤使用 [数据绑定](/docs/data-binding/overview)，它直接将 UI 组件连接到数据模型以自动同步值。这减少了应用程序中的样板代码，并允许您对 Spring 实体 `Customer` 添加验证检查，使用户在填写表单时提供完整和准确的信息。此步骤涵盖以下概念：

- [Jakarta 验证](https://beanvalidation.org)
- 使用 [`BindingContext`](https://javadoc.io/doc/com.webforj/webforj-data/latest/com/webforj/data/binding/BindingContext.html) 类

完成此步骤将创建 [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data) 的一个版本。

## 运行应用程序 {#running-the-app}

在开发应用程序时，您可以使用 [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data) 进行比较。要查看应用程序的运行情况：

1. 导航到包含 `pom.xml` 文件的顶级目录，如果您跟随 GitHub 上的版本，则为 `5-validating-and-binding-data`。

2. 使用以下 Maven 命令在本地运行 Spring Boot 应用程序：
    ```bash
    mvn
    ```

运行应用程序会自动在 `http://localhost:8080` 打开一个新浏览器。

## 定义验证规则 {#defining-validation-rules}

开发具有可编辑数据的应用程序应包含验证。验证检查有助于维护有意义和准确的用户提交数据。如果不加以检查，可能会导致问题，因此实时捕捉用户填写表单时可能出现的错误是很重要的。

由于各个属性的有效性标准可能不同，您需要定义每个属性的有效标准，并告知用户是否存在无效内容。幸运的是，您可以使用 [Jakarta 验证](https://beanvalidation.org) 轻松做到这一点。Jakarta 验证允许您将约束作为注解添加到属性中。

本教程使用两个 Jakarta 注解，`@NotEmpty` 和 `@Pattern`。`@NotEmpty` 检查 null 和空字符串，而 `@Pattern` 检查属性是否与您设置的正则表达式匹配。两个注解都允许您添加一条消息以在属性变无效时显示。

要要求名和姓都是必填项且只能包含字母，同时让公司名称为可选并允许字母、数字和空格，请将以下注解应用于 `Customer` 实体：

```java
@Entity
  @Table(name = "customers")
  public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Customer first name is required")
    @Pattern(regexp = "[a-zA-Z]*", message = "Invalid characters")
    private String firstName = "";

    @NotEmpty(message = "Customer last name is required")
    @Pattern(regexp = "[a-zA-Z]*", message = "Invalid characters")
    private String lastName = "";

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

有关完整的验证列表，请参阅 [Jakarta Bean 验证约束参考](https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/package-summary.html)，或从 [webforJ Jakarta 验证文章](/docs/data-binding/validation/jakarta-validation) 了解更多信息。

## 绑定字段 {#binding-the-fields}

要在 `FormView` 中为 UI 使用 `Customer` 的验证检查，您需要创建一个用于数据绑定的 `BindingContext`。在数据绑定之前，`FormView` 中的每个字段都需要事件监听器来手动与 Spring 实体 `Customer` 进行同步。在 `FormView` 中创建 `BindingContext` 可以将 `Customer` 数据模型绑定并自动同步到 UI 组件。

### 创建 `BindingContext` {#creating-a-bindingcontext}

`BindingContext` 实例需要与之同步的 Spring bean。在 `FormView` 中，使用 `Customer` 实体声明 `BindingContext`：

```java
public class FormView extends Composite<Div> implements WillEnterObserver {
  private final CustomerService customerService;

  private BindingContext<Customer> context;

  Customer customer = new Customer();
```

然后，使用 `BindingContext.of()` 和以下参数自动将 UI 组件绑定到 bean 属性：

- **`this`** : 之前您将 `context` 声明为 `BindingContext`。第一个参数设置哪个对象包含可绑定组件。
- **`Customer.class`** : 第二个参数是用于绑定的 bean 的类。
- **`true`** : 第三个参数启用 Jakarta 验证，允许上下文使用您为 `Customer` 设置的验证。这样做会更改无效组件的样式并显示设置的消息。

所有这些结合在一起的代码如下：

```java
context = BindingContext.of(this, Customer.class, true);
```

### 使表单响应 {#making-the-form-responsive}

通过数据绑定，您的应用程序现在会自动执行验证检查。通过为检查添加事件监听器，您可以防止用户提交无效的表单。添加以下内容以使提交按钮仅在表单有效时激活：

```java
context = BindingContext.of(this, Customer.class, true);
context.onValidate(e -> submit.setEnabled(e.isValid()));
```

### 移除组件的事件监听器 {#removing-event-listeners-for-components}

现在，每次 UI 变化都与 `BindingContext` 自动同步。这意味着您可以移除每个字段的事件监听器：

**之前**
```java
// Without data binding
TextField firstName = new TextField("First Name", e -> customer.setFirstName(e.getValue()));
TextField lastName = new TextField("Last Name", e -> customer.setLastName(e.getValue()));
TextField company = new TextField("Company", e -> customer.setCompany(e.getValue()));
ChoiceBox country = new ChoiceBox("Country",
    e -> customer.setCountry(Country.valueOf(e.getSelectedItem().getText())));
```

**之后**
```java
// With data binding
TextField firstName = new TextField("First Name");
TextField lastName = new TextField("Last Name");
TextField company = new TextField("Company");
ChoiceBox country = new ChoiceBox("Country");
```

### 按属性名称绑定 {#binding-by-property-names}

由于每个组件的名称与数据模型匹配，webforJ 应用了 [自动绑定](/docs/data-binding/automatic-binding)。如果名称不匹配，您可以使用 `@UseProperty` 注解进行映射。

```java
@UseProperty("firstName")
TextField firstNameField = new TextField("First Name");
```

### 在 `fillForm()` 方法中读取数据 {#reading-data-in-the-fillForm()-method}

以前，在 `fillForm()` 方法中，您通过手动从 `Customer` 副本检索数据来初始化每个组件的值。但现在，由于您使用的是 `BindingContext`，可以使用 `read()` 方法。此方法将每个绑定组件填充与 `Customer` 副本中的属性关联的数据。

在 `fillForm()` 方法中，将 `setValue()` 方法替换为 `read()`：

```java
public void fillForm(Long customerId) {
  customer = customerService.getCustomerByKey(customerId);
  context.read(customer);
}
```

### 为 `submitCustomer()` 添加验证 {#adding-validation-to-submitcustomer}

此步骤对 `FormView` 的最后更改将是向 `submitCustomer()` 方法添加保护措施。在将更改提交到 H2 数据库之前，应用程序将使用 `write()` 方法对绑定上下文的结果进行最后验证。

`write()` 方法使用 `BindingContext` 中的绑定 UI 组件更新 bean 的属性，并返回 `ValidationResult`。

使用 `write()` 方法通过在 `FormView` 中绑定的组件写入 `Customer` 副本。然后，如果返回的 `ValidationResult` 有效，使用写入的数据更新 H2 数据库。

```java
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

### 完成的 `FormView`

通过这些更改，`FormView` 看起来如下。该应用程序现在支持使用 Spring Boot 和 webforJ 的数据绑定和验证。表单输入会自动与模型同步，并根据验证规则进行检查。

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

## 下一步 {#next-step}

下一步，[集成应用布局](/docs/introduction/tutorial/integrating-an-app-layout) 专注于使用 `AppLayout` 添加侧边菜单，使用户在客户表格和客户表单页面上都可用。您还将了解另一种布局工具，`FlexLayout` 组件。
