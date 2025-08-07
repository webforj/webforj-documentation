---
title: Scaling with Routing and Composites
sidebar_position: 4
_i18n_hash: fdfd4b4255de20775bb12bcd863630f7
---
这一步骤专注于实现路由，以增强应用程序结构的可扩展性和组织性。为此，将更新应用程序以处理多个视图，允许在不同功能之间导航，例如编辑和创建客户条目。它将概述这些功能的视图创建，使用像 `Composite` 这样的组件来构建模块化和可重用的布局。

在 [上一步](./working-with-data) 中创建的应用程序将拥有一个支持多个视图的路由设置，使用户能够更有效地管理客户数据，同时保持干净和可扩展的代码库。要运行应用程序：

- 转到 `3-scaling-with-routing-and-composites` 目录
- 运行 `mvn jetty:run` 命令

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/scaling-with-routing-and-composites.mp4" type="video/mp4"/>
  </video>
</div>

## 路由 {#routing}

[路由](../../routing/overview) 是允许你的应用管理不同视图或页面之间导航的机制。路由使你能够将应用拆分为更小、更专注的组件，而不是将所有逻辑和行为保留在单个位置。

从本质上讲，路由将特定的URL连接到处理这些URL的视图或组件。当用户与应用互动时，例如点击按钮或在浏览器中直接输入URL，路由器将URL解析到适当的视图，初始化它，并在屏幕上显示。这种方法使得管理导航和维护应用的状态变得简单。

这一阶段专注于更改 `App` 类、为视图创建文件以及配置路由以实现不同部分之间的平滑导航。

我们不再将所有逻辑放在 `App` 的 `run()` 方法中，而是将像 `DemoView` 和 `FormView` 这样的视图实现为独立的类。这种方法更符合标准Java实践。

- **DemoView**：处理显示表格并导航到 `FormView`。
- **FormView**：管理添加和编辑客户数据。

### 更改 `App` 类 {#changing-the-app-class}

要在应用中启用路由，请使用 `@Routify` 注解更新 `App` 类。这告诉 webforJ 激活路由并扫描指定的包以寻找启用路由的视图。

```java title="DemoApplication.java" {1}
@Routify(packages = "com.webforj.demos.views", debug = true)
public class DemoApplication extends App {  
}
```

- **`packages`**：指定要扫描的包，以查找定义路由的视图。
- **`debug`**：启用调试模式，以便在开发过程中更容易进行故障排除。

### 创建视图文件并配置路由 {#creating-files-for-the-views-and-configuring-routes}

启用路由后，为应用包含的每个视图创建单独的Java文件，在这种情况下为 `DemoView.java` 和 `FormView.java`。使用 `@Route` 注解为这些视图分配唯一的路由。这确保每个视图都可以通过特定的URL访问。

当与类相关的 `@Route` 注解没有值时，webforJ 会自动将类名（不包括后缀）作为路由分配。例如，`DemoView` 默认将映射到 `/demo` 路由。在这种情况下，`DemoView` 被认为是默认路由，因此你将分配一个路由。

`/` 路由作为应用的默认入口点。将此路由分配给视图可确保这是用户访问应用时看到的第一页。在大多数情况下，仪表板或摘要视图被分配给 `/`。

```java title="DemoView.java" {1}
@Route("/")
@FrameTitle("演示")
public class DemoView extends Composite<Div> {
  // DemoView 逻辑
}
```

:::info 
有关不同路由类型的更多信息可在 [这里](../../routing/defining-routes) 找到。
:::

对于 `FormView`，路由 `customer/:id?` 使用可选参数 `id` 来确定 `FormView` 的模式。

- **添加模式**：当未提供 `id` 时，`FormView` 初始化为空白表单以添加新客户数据。
- **编辑模式**：当提供 `id` 时，`FormView` 使用 `Service` 获取相应客户的数据并预填表单，允许对现有条目进行修改。

```java title="FormView.java" {1}
@Route("customer/:id?")
@FrameTitle("客户表单")
public class FormView extends Composite<Div> implements DidEnterObserver {
  // FormView 逻辑
}
```

:::info 
有关实现这些路由模式的不同方式的更多信息可在 [这里](../../routing/route-patterns) 找到。
:::

## 使用 `Composite` 组件显示页面 {#using-composite-components-to-display-pages}

webforJ 中的组合组件，如 `Composite<Div>`，允许你在可重用的容器中封装 UI 逻辑和结构。通过扩展 `Composite`，你限制了向应用其余部分公开的方法和数据，从而确保代码更简洁和更好地封装。

例如，`DemoView` 扩展 `Composite<Div>` 而不是直接扩展 `Div`：

```java title="DemoView.java"
public class DemoView extends Composite<Div> {
  private Table<Customer> table = new Table<>();
  private Button add = new Button("添加客户", ButtonTheme.PRIMARY);  

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

在配置路由和设置视图后，使用事件监听器和服务方法连接视图和数据。第一步是添加一个或多个 UI 元素以便从一个视图导航到另一个视图。

### 按钮导航 {#button-navigation}

`Button` 组件触发导航事件以通过 `Router` 类从一个视图转换到另一个视图。例如：

```java title="DemoView.java"
private Button add = new Button("添加客户", ButtonTheme.PRIMARY,
  e -> Router.getCurrent().navigate(FormView.class));
```

:::info
Router 类使用给定类解析路由并构建导航的URL。所有浏览器导航都将被处理，从而无需担心历史管理和视图初始化。
有关导航的更多细节，请参见 [路由导航文章](../../routing/route-navigation)。
:::

### 表格编辑 {#table-editing}

除了通过按钮点击进行导航外，许多应用还允许在 `Table` 中双击项以导航到预填充了项详情的表单。为了允许用户双击表格中的项目进行导航，需要进行以下更改。

在适当的屏幕上编辑完详细信息后，保存更改，并更新 `Table` 以显示所选项的更改数据。

为了实现这种导航，表格中的项点击通过 `TableItemClickEvent<Customer>` 监听器进行处理。该事件包含被点击客户的 `id`，并通过 `navigate()` 方法利用 `ParametersBag` 将其传递给 `FormView`：

```java title="DemoView.java" 
private void editCustomer(TableItemClickEvent<Customer> e) {
  Router.getCurrent().navigate(FormView.class,
    ParametersBag.of("id=" + e.getItemKey()));
}
```

### 使用 `onDidEnter` 处理初始化 {#handling-initialization-with-ondidenter}

webforJ 中的 `onDidEnter` 方法是路由生命周期的一部分，并在视图变为活动时触发。

当 `Router` 导航到视图时，`onDidEnter` 作为生命周期的一部分被触发，以：
- **加载数据**：根据路由参数初始化或获取视图所需的数据。
- **设置视图**：根据上下文动态更新 UI 元素。
- **响应状态更改**：执行依赖于视图处于活动状态的操作，例如重置表单或突出显示组件。

`FormView` 中的 `onDidEnter` 方法检查路由中 `id` 参数的存在，并相应地调整表单的行为：

- **编辑模式**：如果提供了 `id`，该方法使用 `Service` 获取相应客户的数据并预填表单字段。`提交` 按钮被配置为更新现有条目。
- **添加模式**：如果没有提供 `id`，表单保持空白，并将 `提交` 按钮配置为创建新客户。

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

在完成数据编辑后，必须将其提交到处理存储库的服务。因此，之前步骤中已经设置的 `Service` 类现在需要增强附加方法，以允许用户添加和编辑客户。

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

`Repository` 类中的 `commit()` 方法保持应用的数据和 UI 的同步。它提供了一种机制来刷新存储在 `Repository` 中的数据，确保最新状态在应用中反映。

此方法可通过两种方式使用：

1) **刷新所有数据**：
  调用不带参数的 `commit()` 会从存储库的底层数据源（例如数据库或服务类）重新加载所有实体。

2) **刷新单个实体**：
  调用 `commit(T entity)` 会重新加载特定实体，确保其状态与最新数据源更改匹配。

在 `Repository` 中数据更改时，例如在数据源中添加或修改实体后，请调用 `commit()`。

```java
// 刷新存储库中的所有客户数据
customerRepository.commit();

// 刷新单个客户实体
Customer updatedCustomer = ...; // 从外部源更新
customerRepository.commit(updatedCustomer);
```

通过这些更改，已经实现了以下目标：

1. 实现了路由并进行了设置，以便未来的视图可以轻松集成。
2. 将 UI 实现从 `App` 中移除，并放入单独的视图中。
3. 添加了一个额外的视图以操控显示在客户表格中的数据。

随着客户详细信息和路由的修改完成，下一步将专注于实现数据绑定，并利用它来促进验证。
