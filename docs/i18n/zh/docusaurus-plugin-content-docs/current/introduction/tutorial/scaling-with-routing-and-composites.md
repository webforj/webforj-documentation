---
title: 使用路由和复合组件进行扩展
sidebar_position: 4
_i18n_hash: 50cd3b00cb1fb7731b6328708d6d45ba
---
此步骤专注于实现路由，以增强应用结构的可扩展性和组织性。为此，将更新应用以处理多个视图，允许在不同的功能之间导航，例如编辑和创建客户条目。它将概述为这些功能创建视图的过程，使用像 `Composite` 这样的组件构建模块化和可重用的布局。

在[之前的步骤](./working-with-data)中创建的应用将具有支持多个视图的路由设置，使用户能够更有效地管理客户数据，同时保持干净且可扩展的代码库。要运行应用：

- 转到 `3-scaling-with-routing-and-composites` 目录
- 运行 `mvn jetty:run` 命令

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/scaling-with-routing-and-composites.mp4" type="video/mp4"/>
  </video>
</div>

## 路由 {#routing}

[路由](../../routing/overview)是允许您的应用管理不同视图或页面之间导航的机制。路由使您能够将应用拆分为更小、更专注的组件，而不是将所有逻辑和行为放在单一位置。

从本质上讲，路由将特定的URL连接到处理这些URL的视图或组件。当用户与您的应用互动时，例如单击按钮或直接在其浏览器中输入URL，路由器将URL解析为适当的视图，初始化它并在屏幕上显示。这种方法使管理导航和维护应用程序状态变得简单。

此步骤专注于更改 `App` 类、为视图创建文件并配置路由，以实现不同部分之间的平滑导航。

而不是将所有逻辑放入 `App` 的 `run()` 方法中，像 `DemoView` 和 `FormView` 这样的视图被实现为独立的类。这种方法更接近于标准Java实践。

- **DemoView**：处理显示表格并导航到 `FormView`。
- **FormView**：管理添加和编辑客户数据。

### 更改 `App` 类 {#changing-the-app-class}

要在您的应用中启用路由，请使用 `@Routify` 注解更新 `App` 类。这告诉 webforJ 激活路由并扫描指定的包以查找启用路由的视图。

```java title="DemoApplication.java" {1}
@Routify(packages = "com.webforj.demos.views", debug = true)
public class DemoApplication extends App {  
}
```

- **`packages`**：指定扫描哪些包以查找定义路由的视图。
- **`debug`**：启用调试模式，以便于开发期间的故障排除。

### 创建视图文件并配置路由 {#creating-files-for-the-views-and-configuring-routes}

一旦启用路由，将为应用包含的每个视图创建单独的Java文件，在这种情况下，`DemoView.java` 和 `FormView.java`。使用 `@Route` 注解为这些视图分配唯一的路由。这确保每个视图都可以通过特定的URL访问。

当与类关联的 `@Route` 注解的某个后缀没有值时，webforJ 会自动将该类的名称（不带后缀）分配为路由。例如，`DemoView` 将默认映射到 `/demo` 的路由。因为在这种情况下，`DemoView` 应该是默认路由，所以您将为其分配一个路由。

`/` 路由作为应用的默认入口点。将此路由分配给视图确保这是用户访问应用时看到的第一个页面。在大多数情况下，仪表板或摘要视图将分配给 `/`。

```java title="DemoView.java" {1}
@Route("/")
@FrameTitle("Demo")
public class DemoView extends Composite<Div> {
  // DemoView 逻辑
}
```

:::info 
有关不同路由类型的更多信息，请参见[这里](../../routing/defining-routes)。
:::

对于 `FormView`，路由 `customer/:id?` 使用可选参数 `id` 来确定 `FormView` 的模式。

- **添加模式**：当未提供 `id` 时，`FormView` 将初始化一个空表单以添加新客户数据。
- **编辑模式**：当提供 `id` 时，`FormView` 使用 `Service` 获取相应客户的数据并预填充表单，允许编辑现有条目。

```java title="FormView.java" {1}
@Route("customer/:id?")
@FrameTitle("Customer Form")
public class FormView extends Composite<Div> implements DidEnterObserver {
  // FormView 逻辑
}
```

:::info 
有关实现这些路由模式的不同方法的更多信息，请参见[这里](../../routing/route-patterns)。
:::

## 使用 `Composite` 组件显示页面 {#using-composite-components-to-display-pages}

webforJ 中的复合组件，例如 `Composite<Div>`，允许您将UI逻辑和结构封装在可重用的容器中。通过扩展 `Composite`，您限制了对应用其余部分暴露的方法和数据，从而确保代码更加清晰，确保更好的封装。

例如，`DemoView` 扩展 `Composite<Div>` 而不是直接扩展 `Div`：

```java title="DemoView.java"
public class DemoView extends Composite<Div> {
  private Table<Customer> table = new Table<>();
  private Button add = new Button("Add Customer", ButtonTheme.PRIMARY);  

  public DemoView() {
    setupLayout();
  }

  private void setupLayout() {
    FlexLayout layout = FlexLayout.create(table, add)
        .vertical().contentAlign().center().build().setPadding("var(--dwc-space-l)");
    getBoundComponent().add(layout);
  }
}
```

## 连接路由 {#connecting-the-routes}

在配置路由并设置视图之后，使用事件侦听器和服务方法连接视图和数据。第一步是添加一个或多个UI元素以从一个视图导航到另一个视图。

### 按钮导航 {#button-navigation}

`Button` 组件触发导航事件，以通过 `Router` 类从一个视图过渡到另一个视图。例如：

```java title="DemoView.java"
private Button add = new Button("Add Customer", ButtonTheme.PRIMARY,
  e -> Router.getCurrent().navigate(FormView.class));
```

:::info
Router 类使用给定的类解析路由并构建要导航的URL。所有浏览器导航都将被处理，因此不需要担心历史管理和视图初始化。
有关导航的更多细节，请参见[路由导航文章](../../routing/route-navigation)。
:::

### 表格编辑 {#table-editing}

除了通过按钮单击导航之外，许多应用也允许在双击 `Table` 中的项时导航到应用的其他部分。以下更改使用户能够双击表格中的项目以导航到预填充该项目详细信息的表单。

一旦在适当的屏幕上编辑了详细信息，便会保存更改，并更新 `Table` 以显示所选项目中的更改数据。

为了便于此导航，表格中的项单击由 `TableItemClickEvent<Customer>` 侦听器处理。该事件包含单击客户的 `id`，它通过使用带有 `ParametersBag` 的 `navigate()` 方法将其传递给 `FormView`：

```java title="DemoView.java" 
private void editCustomer(TableItemClickEvent<Customer> e) {
  Router.getCurrent().navigate(FormView.class,
    ParametersBag.of("id=" + e.getItemKey()));
}
```

### 使用 `onDidEnter` 处理初始化 {#handling-initialization-with-ondidenter}

webforJ 中的 `onDidEnter` 方法是路由生命周期的一部分，并在视图变为活动时触发。

当 `Router` 导航到一个视图时，`onDidEnter` 作为生命周期的一部分触发，以：
- **加载数据**：根据路由参数初始化或获取所需的数据。
- **设置视图**：根据上下文动态更新UI元素。
- **响应状态更改**：执行依赖于视图处于活动状态的操作，例如重置表单或突出显示组件。

`FormView` 中的 `onDidEnter` 方法检查路由中是否存在 `id` 参数，并相应地调整表单的行为：

- **编辑模式**：如果提供 `id`，该方法使用 `Service` 获取相应客户的数据并预填充表单字段。`Submit` 按钮配置为更新现有条目。
- **添加模式**：如果未提供 `id`，表单保持空白，`Submit` 按钮配置为创建新客户。

```java
@Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("id").ifPresentOrElse(id -> {
      customer = Service.getCurrent().getCustomerByKey(UUID.fromString(id));
      firstName.setValue(customer.getFirstName());
      lastName.setValue(customer.getLastName());
      company.setValue(customer.getCompany());
      country.selectKey(customer.getCountry());
      submit.addClickListener(e -> submit("edit"));
    }, () -> submit.addClickListener(e -> submit("add")));
  }
```

### 提交数据 {#submitting-data}

编辑数据完成后，必须将其提交给处理存储库的服务。因此，在本教程的前一步中已经设置好的 `Service` 类现在需要增强附加方法，以允许用户添加和编辑客户。

下面的代码片段展示了如何实现这一点：

```java title="Service.java"
public void addCustomer(Customer newCustomer) {
  data.add(newCustomer);
  repository.commit(newCustomer);
}

public void editCustomer(Customer editedCustomer) {
  repository.commit(editedCustomer);
}
```

### 使用 `commit()` {#using-commit}

`Repository` 类中的 `commit()` 方法保持应用的数据和UI同步。它提供了刷新存储在 `Repository` 中的数据的机制，确保应用中反映最新状态。

此方法可以以两种方式使用：

1) **刷新所有数据：**
  不带参数调用 `commit()` 会从存储库的基础数据源（例如数据库或服务类）重新加载所有实体。

2) **刷新单个实体：**
  调用 `commit(T entity)` 会重新加载特定实体，确保其状态匹配最新的数据源更改。

在 `Repository` 中数据更改时，例如在数据源中添加或修改实体后，调用 `commit()`。

```java
// 刷新存储库中的所有客户数据
customerRepository.commit();

// 刷新单个客户实体
Customer updatedCustomer = ...; // 从外部源更新
customerRepository.commit(updatedCustomer);
```

通过这些更改，实现了以下目标：

1. 实现了路由并设置，以便将来可以轻松集成视图。
2. 将UI实现从 `App` 中移除到单独的视图中。
3. 添加了一个额外视图以操作显示在客户表中的数据。

随着客户详细信息的修改和路由的完成，下一步将专注于实现数据绑定并利用它来促进验证。
