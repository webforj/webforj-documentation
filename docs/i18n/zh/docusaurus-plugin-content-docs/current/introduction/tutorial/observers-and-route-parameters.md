---
title: Observers and Route Parameters
sidebar_position: 5
description: Step 4 - Use route parameters to control what content loads.
_i18n_hash: a1b1dbf791378ed2fd17db610223475e
---
来自[路由和复合](/docs/introduction/tutorial/routing-and-composites)的应用程序只能向数据库添加新客户。使用以下概念，您将为用户提供编辑现有客户数据的功能：

- 路由模式
- 通过 URL 传递参数值
- 生命周期观察者

完成此步骤后，将创建一个[4-观察者和路由参数](https://github.com/webforj/webforj-tutorial/tree/main/4-observers-and-route-parameters)的版本。

## 运行应用 {#running-the-app}

在您开发应用时，可以使用[4-观察者和路由参数](https://github.com/webforj/webforj-tutorial/tree/main/4-observers-and-route-parameters)进行比较。要查看应用的运行效果：

1. 导航到包含 `pom.xml` 文件的顶级目录，如果您在 GitHub 上进行操作，则为 `4-observers-and-route-parameters`。

2. 使用以下 Maven 命令在本地运行 Spring Boot 应用：
    ```bash
    mvn
    ```

运行应用会自动在 `http://localhost:8080` 打开一个新浏览器。

## 使用客户的 `id` {#using-the-customers-id}

要使用 `FormView` 编辑现有客户，您需要一种告诉它要编辑哪个客户的方法。您可以通过向 `FormView` 提供一个代表客户 ID 的初始参数来做到这一点。在[数据处理](/docs/introduction/tutorial/working-with-data)中，您创建了一个 `Customer` 实体，在将客户添加到数据库时将一个数字 `Long` 值分配为唯一的 `id`。

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

在此步骤中，您将对 `FormView` 进行更改，以便它在加载任何内容之前使用 `id` 作为初始参数。然后，您将让 `FormView` 评估 `id` 以确定表单是为添加新客户还是更新现有客户。最后，您将修改 `MainView` 以便在导航到 `FormView` 时发送 `id` 值。

## 向 `FormView` 添加路由模式 {#adding-a-route-pattern}

在上一步中，将 `FormView` 的路由设置为 `@Route(customer)` 将类本地映射到 `http://localhost:8080/customer`。添加路由模式可以让您将 `id` 作为初始参数添加到 `FormView`。

[路由模式](/docs/routing/route-patterns)允许您在 URL 中添加参数，使其可选，并设置有效模式的约束。使用 `@Route` 注解，这使得 `id` 成为 `FormView` 的可选路由参数：

- **`/:id`** 为路由提供了一个命名参数 `id`，因此访问 `http://localhost:8080/customer/6` 将以 `id` 参数为 `6` 加载 `FormView`。

- **`?`** 使 `id` 参数成为可选的。默认情况下，参数是必需的，但使 `id` 可选使您能够将 `FormView` 用于添加尚未具有 `id` 的新客户。

- **`<[0-9]+>`** 将 `id` 限制为正数。在角括号 `< >` 中，您可以向参数添加约束作为正则表达式。如果 `id` 不符合约束，例如 `http://localhost:8080/customer/john-smith`，则会将用户发送到 404 页面。

要将可选路由参数添加到 `FormView`，请将 `@Route` 注解更改为：

```java
@Route("customer/:id?<[0-9]+>")
```

## 路由到 `FormView` {#routing-to-formview}

`FormView` 现在接受一个可选的 `id` 参数，仅在 `id` 是一个完整正数时加载。

然而，当用户手动输入一个不存在的客户的 URL 时，比如 `http://localhost:8080/customer/5000`，`FormView` 仍然可以加载。在进入 `FormView` 之前添加一个生命周期观察者可以让您的应用决定如何处理传入的 `id` 值。

### 条件路由 {#conditional-routing}

生命周期观察者允许组件在特定阶段对生命周期事件做出反应。[生命周期观察者](/docs/routing/navigation-lifecycle/observers)文章列出了可用的观察者，但此步骤仅使用 `WillEnterObserver`。

`WillEnterObserver` 的时机发生在组件路由完成之前。使用此观察者，您可以评估传入的 `id`。如果 `id` 与现有客户不匹配，您可以将用户重定向回 `MainView` 以查找有效的客户进行编辑。

在讨论 `WillEnterObserver` 的代码之前，以下流程图列出了进入 `FormView` 时可能的结果：

```mermaid
flowchart TD
    A[进入 FormView] --> B{是否有 id 参数？}
    B -->|否| C[转到空的 FormView]
    B -->|是| D{该 id 值是否与客户 id 匹配？}
    D -->|是| E[转到已填充的 FormView]
    D -->|否| F[重定向到 MainView]
```

### 使用 `WillEnterObserver` {#using-the-willenterobserver}

使用在组件完全加载之前触发的生命周期观察者 `WillEnterObserver`，可以添加条件以确定应用程序是否应该继续到 `FormView`，或者是否需要将用户重定向到 `MainView`。

每个生命周期观察者都是一个接口，因此将 `WillEnterObserver` 实现为 `FormView` 声明的一部分：

```java
public class FormView extends Composite<Div> implements WillEnterObserver {
```

`WillEnterObserver` 观察者有一个 `onWillEnter()` 方法，当进行路由到组件时，webforJ 会调用该方法。此方法有两个参数：`WillEnterEvent` 和 `ParametersBag`。

`WillEnterEvent` 确定是否继续路由到组件，通过 `accept()` 方法，或者使用 `reject()` 方法停止路由。在拒绝当前路由后，您需要将用户重定向到其他地方。

`ParametersBag` 包含来自 URL 的路由参数。您将在下一节中使用 `ParametersBag` 来创建 `onWillEnter()` 的条件逻辑，使用 `id` 参数。

以下是只有两个结果的 `onWillEnter()` 示例：

```java
@Override
public void onWillEnter(WillEnterEvent event, ParametersBag parameters) {

  //添加条件逻辑
  if (<condition>) {

    //允许路由到 FormView 继续
    event.accept();

  } else {

    //停止路由到 FormView
    event.reject();

    //将用户发送到 MainView
    navigateToMain();
  }
}
```

### 使用 `ParametersBag` {#using-the-parametersbag}

如前一节中简要提到的，`ParametersBag` 包含来自 URL 的路由参数。每个生命周期观察者都可以访问此对象，在应用中使用它可以获得 `id` 值。

`ParametersBag` 对象提供了多个查询方法，以特定对象类型检索参数。例如，`getInt()` 可以将参数作为 `Integer` 获取。

但是，由于某些参数是可选的，`getInt()` 实际返回的是 `Optional<Integer>`。在 `Optional<Integer>` 上使用 `ifPresentOrElse()` 方法允许您使用 `Integer` 设置一个变量。

当没有 `id` 存在时，用户可以继续访问 `FormView` 以添加新客户。

```java
@Override
public void onWillEnter(WillEnterEvent event, ParametersBag parameters) {

  //确定要获取哪个参数，并检查其是否存在
  parameters.getInt("id").ifPresentOrElse(id -> {

    //将 id 作为变量使用
    customerId = Long.valueOf(id);

  //没有 id 存在时，继续进入 FormView 以添加新客户
  }, () -> event.accept());

}
```

### `id` 是否有效？ {#is-the-id-valid}

目前，上一节中的 `WillEnterObserver` 只在没有 `id` 存在时接受路由。观察者在继续进入 `FormView` 之前需要执行一次验证：验证 `id` 是否与现有客户匹配。

现在 `FormView` 可以使用 `CustomerService` 通过 `doesCustomerExist()` 方法确认客户是否存在。如果没有匹配项，则应用可以拒绝当前路由并使用 `navigateToMain()` 将用户重定向到 `MainView`。

当给出有效的 `id` 时，应用可以使用 `accept()` 继续路由到 `FormView`。创建一个 `fillForm()` 方法，将 `customer` 变量分配给数据库中与相应 `id` 对应的客户并设置字段的值：

```java
public void fillForm(Long customerId) {
  customer = customerService.getCustomerByKey(customerId);
  firstName.setValue(customer.getFirstName());
  lastName.setValue(customer.getLastName());
  company.setValue(customer.getCompany());
  country.selectKey(customer.getCountry());
}
```

与添加新客户一样，使用工作副本允许用户在 UI 中编辑客户数据，而无需直接编辑存储库。

### 完整的 `onWillEnter()` {#completed-onwillenter}

最后两节详细介绍了如何使用 `ParametersBag` 和 `CustomerService` 处理进入 `FormView` 的每种结果。

以下是 `FormView` 的完整 `onWillEnter()`，它使用 `ParametersBag` 来拒绝或接受传入的路由，并调用其他方法来填充表单或将用户重定向到 `MainView`：

```java
@Override
public void onWillEnter(WillEnterEvent event, ParametersBag parameters) {

  //确定要获取哪个参数，并检查其是否存在
  parameters.getInt("id").ifPresentOrElse(id -> {
    customerId = Long.valueOf(id);
    //检查此 id 是否有客户
    if (customerService.doesCustomerExist(customerId)) {
        //该客户存在，因此继续进入 FormView，并使用 id 初始化字段
        event.accept();
        fillForm(customerId);
      } else {
        //该客户不存在，因此重定向到 MainView
        event.reject();
        navigateToMain();
      }

  //没有存在的 id，因此继续进入 FormView 以添加新客户
  }, () -> event.accept());

}
```

## 添加或编辑客户 {#adding-or-editing-a-customer}

之前版本的此应用程序仅在用户提交表单时添加新客户。现在用户可以编辑现有客户，`submitCustomer()` 方法必须在更新数据库之前验证客户是否已经存在。

最初，在 `FormView` 中将客户 `id` 分配为变量是没有必要的，因为新客户在提交到数据库时会被分配一个唯一的 `id`。但是，如果您在 `FormView` 中声明 `customerId` 为一个未使用的初始变量 `id` 值，那么对新客户它将保持不变，并在 `onWillEnter()` 中重写用于现有客户。

这使您能够使用 `doesCustomerExist()` 来验证是添加新客户还是更新现有客户。

```java
private Long customerId = 0L;

//...

private void submitCustomer() {
  if (customerService.doesCustomerExist(customerId)) {
    customerService.updateCustomer(customer);
  } else {
    customerService.createCustomer(customer);
  }
  navigateToMain();
}
```

## 完整的 `FormView` {#completed-formview}

以下是 `FormView` 的样子，现在它可以处理编辑现有客户的功能：

```java
@Route("customer/:id?<[0-9]+>")
@FrameTitle("客户表单")
public class FormView extends Composite<Div> implements WillEnterObserver {
    private final CustomerService customerService;
    private Customer customer = new Customer();
    private Long customerId = 0L;
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
      if (customerService.doesCustomerExist(customerId)) {
        customerService.updateCustomer(customer);
      } else {
        customerService.createCustomer(customer);
      }
      navigateToMain();
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
      firstName.setValue(customer.getFirstName());
      lastName.setValue(customer.getLastName());
      company.setValue(customer.getCompany());
      country.selectKey(customer.getCountry());
    }
}
```

## 从 `MainView` 导航到 `FormView` 以编辑客户 {#navigating-from-mainview-to-formview-to-edit-customers}

在此步骤早些时候，您使用现有的 `ParametersBag` 来确定 `id` 的值。创建一个新的 `ParametersBag` 让您可以通过所选参数直接在类之间导航。使用 `Table` 中的数据发送用户到 `FormView` 的客户 `id` 是一个可行的选项。

与按钮类似，将导航与用户选择的操作绑定在一起，让他们决定何时前往 `FormView`。为 `Table` 添加事件监听器后，您可以将用户发送到 `FormView`，并创建一个 `ParametersBag`：

```java
table.addItemClickListener(this::editCustomer);

private void editCustomer(TableItemClickEvent<Customer> e) {
  Router.getCurrent().navigate(FormView.class,
      ParametersBag.of("id=" + e.getItemKey()));
}
```

但是，`Table` 项目的键默认是自动生成的。您可以通过使用 `setKeyProvider()` 方法显式将每个键与客户的 `id` 相关联：

```java
table.setKeyProvider(Customer::getId);
```

在 `MainView` 中，将 `addItemClickListener()` 和 `setKeyProvider()` 方法添加到 `buildTable()`，然后添加一个方法，将用户使用 `ParametersBag` 发送到 `FormView`，其值为用户在表格上单击时的 `id`：

```java title="MainView.java" {30-31,34-37}
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
    table.setKeyProvider(Customer::getId);
    table.addItemClickListener(this::editCustomer);
  }

  private void editCustomer(TableItemClickEvent<Customer> e) {
    Router.getCurrent().navigate(FormView.class,
        ParametersBag.of("id=" + e.getItemKey()));
  }
}
```

## 下一步 {#next-step}

现在用户可以直接编辑客户数据，您的应用应该在将更改提交到存储库之前验证这些更改。在[验证和绑定数据](/docs/introduction/tutorial/validating-and-binding-data)中，您将创建验证规则，并直接将数据模型与 UI 关联，以便组件在数据无效时显示错误消息。
