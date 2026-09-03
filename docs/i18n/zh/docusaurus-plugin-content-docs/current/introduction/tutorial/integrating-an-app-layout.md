---
title: 集成 AppLayout
sidebar_position: 7
description: Step 6 - Using the AppLayout and FlexLayout components.
_i18n_hash: 3a2148bdfb680284a597a17c263609da
---
在此步骤中，您将把应用的所有部分组合成一个连贯的应用布局。在此步骤结束时，您的应用结构将与 [SideMenu 原型](/docs/building-ui/archetypes/sidemenu) 非常相似，并且您将更好地理解以下组件和概念是如何工作的：

- [`FlexLayout`](/docs/components/flex-layout)
- [路由出口](/docs/routing/route-hierarchy/route-outlets)
- [`AppLayout`](/docs/components/app-layout)
- [`AppNav`](/docs/components/appnav)

## 运行应用 {#running-the-app}

在开发应用时，您可以使用 [6-integrating-an-app-layout](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout) 进行比较。要查看应用的运行效果：

1. 导航到包含 `pom.xml` 文件的顶层目录，如果您跟随 GitHub 上的版本，则为 `6-integrating-an-app-layout`。

2. 使用以下 Maven 命令在本地运行 Spring Boot 应用：
    ```bash
    mvn
    ```

运行应用会自动在 `http://localhost:8080` 打开一个新浏览器。

## 创建可重用组件 {#creating-a-reusable-component}

在之前的步骤中，[路由与组合组件](/docs/introduction/tutorial/routing-and-composites)，您创建了两个组合组件，包含客户表和客户表单的内容。
作为此步骤的一部分，您将创建一个较小的、可重用的组合组件，以在侧菜单和关于页面中显示应用的名称。如果您将来决定更改应用的名称，仅需在此组件中进行更新。

在 `src/main/java/com/webforj/tutorial/components` 下创建一个名为 `AppTitle` 的类。`AppTitle` 绑定的组件将是一个 `FlexLayout`，这是在此步骤中用于展示如何构建更复杂布局的容器组件。
对于这个 `FlexLayout`，您将排列项的方向和项之间的间距。通过分别使用 `setDirection()` 和 `setSpacing()` 方法来完成。

```java title='AppTitle.java'
// 使绑定组件为 FlexLayout
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();

  public AppTitle() {

    // 垂直排列项目
    self.setDirection(FlexDirection.COLUMN);

    // 设置项目之间的间距
    self.setSpacing("0px");
  }
}
```

然后使用标准的 HTML 元素创建标题和副标题。将标题元素的底部边距设置为 `0px` 可以让元素靠得更近，并且您可以使用 [DWC CSS 变量](/docs/styling/css-variables) 来样式化副标题。

```java title='AppTitle.java' {3-4,7-9,13}
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private H2 title = new H2("客户管理器");
  private Paragraph subTitle = new Paragraph("一个简单的记录系统");

  public AppTitle() {
    title.setStyle("margin-bottom", "0px");
    subTitle.setStyle("color", "var(--dwc-color-gray-50)");
    subTitle.setStyle("font-size", "var(--dwc-font-size-m)");

    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("0px")
        .add(title, subTitle);
  }
}
```

### 可选渲染 {#optional-rendering}

尽管 `AppTitle` 很简单，但在构造函数中添加一个布尔参数允许您控制何时渲染组件的某些部分，例如副标题。

```java title='AppTitle.java'
// 添加布尔参数
public AppTitle(boolean showSubTitle) {

  self.setDirection(FlexDirection.COLUMN)
      .setSpacing("0px")

      // 默认添加标题
      .add(title);

  // 可选显示副标题
  if (showSubTitle) {
    self.add(subTitle);
  }
}
```

### 完成的 `AppTitle` {#completed-app-title}

总的来说，可重用组件应如下所示：

```java title='AppTitle.java'
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private H2 title = new H2("客户管理器");
  private Paragraph subTitle = new Paragraph("一个简单的记录系统");

  public AppTitle(boolean showSubTitle) {
    title.setStyle("margin-bottom", "0");
    subTitle.setStyle("color", "var(--dwc-color-gray-50)");
    subTitle.setStyle("font-size", "var(--dwc-font-size-m)");

    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("0px")
        .add(title);

    if (showSubTitle) {
      self.add(subTitle);
    }
  }
}
```

## 创建关于页面 {#creating-an-about-page}

第一个添加新创建的 `AppTitle` 组件的地方将是关于页面。该页面包含一张图片和 `AppTitle` 组件，通过使用另一个 `FlexLayout` 将其居中。

### 使用 `FlexLayout` 居中内容 {#centering-content-using-a-flexlayout}

目标是使用 `FlexLayout` 居中关于页面的内容。`FlexLayout` 组件遵循 [CSS flexbox 布局模型](https://css-tricks.com/snippets/css/a-guide-to-flexbox/)。用于 `FlexLayout` 的方法，例如之前用于将项目排列为一列的那些，是排列项目的不同方式。

排列 `FlexLayout` 中项目的方法使用相对方向系统。与其考虑水平和垂直轴，不如将与项目平行的轴视为主轴，与项目垂直的轴视为交叉轴。

将 `FlexJustifyContent` 和 `FlexAlignment` 属性都设置为 `CENTER` 将使 `FlexLayout` 中的项目在主轴和交叉轴上都居中，并使 `FlexLayout` 占据其父容器的全部空间将使其在页面上居中。

```java
private final FlexLayout layout = new FlexLayout();

// 填满父元素的整个空间
layout.setSize("100%", "100%");

// 使主轴垂直
layout.setDirection(FlexDirection.COLUMN);

// 在交叉轴上居中项目
layout.setAlignment(FlexAlignment.CENTER);

// 在主轴上居中项目
layout.setJustifyContent(FlexJustifyContent.CENTER);
```

为了帮助直观理解不同方法的工作原理，可以查看博客文章 [FlexWrap your mind around webforJ's FlexLayout](/blog/2025/08/26/flexlayout-container)。

### 添加资源 {#adding-resources}

居中 `FlexLayout` 内部的一个项目将是一张图片。对于本教程，您可以在 GitHub 上查看并下载 [关于页面的图片](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout/src/main/resources/static/images/Files.svg)。
下载后，将其添加到项目的静态文件夹中，路径为 `src/main/resources/static/images`，并将其命名为 `Files.svg`。

将此图片放入静态文件夹中允许您
使用 [Webserver 协议](/docs/managing-resources/assets-protocols#the-webserver-protocol) 引用它。然后，您可以将其作为 HTML 元素使用，如下所示：

```java
private Img fileImg = new Img("ws://images/Files.svg");
```

### 创建 `AboutView` {#creating-about-view}

与之前的两个应用页面一样，关于页面将是一个可路由视图。在 `src/main/java/com/webforj/tutorial/views` 中，添加一个名为 `AboutView` 的类。与 `AppTitle` 一样，使用 `FlexLayout` 作为绑定组件。

由于您已将类命名为 `AboutView`，因此无需为 URL 映射指定自定义值；该页面默认在 `http://localhost:8080/about` 渲染。

以下是使用之前步骤中的概念和新创建的组件创建新视图以居中内容的效果：

```java title='AboutView.java'
@Route()
@FrameTitle("关于")
public class AboutView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private Img fileImg = new Img("ws://images/Files.svg");

  public AboutView() {
    fileImg.setWidth(250);
    self.setSize("100%", "100%")
        .setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .add(fileImg, new AppTitle(false));
  }
}
```

## 创建 `Layout` 路由 {#creating-the-layout-route}

在 [路由与组合组件](/docs/introduction/tutorial/routing-and-composites) 步骤中简要提到过，但有两种 [路由类型](/docs/routing/route-hierarchy/route-types)。`MainView`、`FormView` 和 `AboutView` 都是 `View` 路由，而您将用于创建应用侧菜单的路由类型是 `Layout` 路由。

布局路由包装子视图，并允许某些 UI 部分在视图之间保持，例如侧菜单。在 `src/main/java/com/webforj/tutorial/layouts` 中，创建一个名为 `MainLayout` 的类。

### 路由出口 {#route-outlets}

与视图路由一样，`MainLayout` 需要一个 `@Route` 注解。 然而，由于它的后缀是 `Layout`，且布局路由不对 URL 产生影响，因此该注解不需要任何参数。

```java title="MainLayout.java" {1}
@Route
public class MainLayout {

  public MainLayout() {

  }
}
```

应用通过在每个视图中将布局类声明为 [路由出口](/docs/routing/route-hierarchy/route-outlets) 来知道在 `MainLayout` 中渲染哪些视图。之前的步骤中只设置了 `@Route` 注解中的 `value` 属性，因此现在您需要明确声明视图类的 `value` 和 `outlet` 属性。

<!-- vale Google.Quotes = NO -->
<Tabs>
  <TabItem value="MainView" label="MainView">
  ```java
  @Route(value = "/", outlet = MainLayout.class)
  ```
  </TabItem>
  <TabItem value="FormView" label="FormView">
  ```java
  @Route(value = "customer/:id?<[0-9]+>", outlet = MainLayout.class)
  ```
  </TabItem>
  <TabItem value="AboutView" label="AboutView">
  ```java
  @Route(outlet = MainLayout.class)
  ```
  </TabItem>
</Tabs>
<!-- vale Google.Quotes = YES -->

:::note 最后修饰
这是在此步骤中对 `FormView` 和 `AboutView` 的最后修改，因此在运行应用之前，请记得更新这些视图的 `@Route` 注释。
:::

## 使用 `AppLayout` 组件 {#using-the-app-layout-component}

现在您的应用在 `MainLayout` 中渲染视图，您可以选择这些组件渲染的位置。选择将 `AppLayout` 作为 `MainLayout` 的绑定组件，可以默认将视图存储在主内容区域，同时为标题和侧菜单添加不同区域。

### 槽 {#slots}

对于许多 webforJ 容器，使用 `add()` 方法将 UI 组件添加到主内容区域。在 `AppLayout` 组件中，有多个区域用于添加 UI 组件，每个在一个单独的槽中。
通过将 `MainLayout` 标记为布局路由并将其绑定组件设置为 `AppLayout`，视图会自动在主内容槽中渲染。

在此步骤中，您将在 `drawer-title` 和 `drawer` 槽中使用，以创建侧菜单，而 `header` 槽将显示用户所在的页面及用于切换侧菜单的按钮。

### 创建侧菜单 {#making-a-side-menu}

当设备上有足够的屏幕空间时，`AppLayout` 组件显示一个抽屉。在这个抽屉中，您将再次添加 `AppTitle` 以及允许用户导航应用的项。

默认情况下，`AppLayout` 不显示抽屉标题，通过使用 `setDrawerHeaderVisible()` 方法允许您显示位于 `drawer-title` 槽内的项目，该项目将是带有副标题的 `AppTitle`。

```java
private AppLayout appLayout = new AppLayout();

// 显示抽屉标题
appLayout.setDrawerHeaderVisible(true);

// 将带有副标题的 AppTitle 添加到抽屉标题
appLayout.addToDrawerTitle(new AppTitle(true));
```

`drawer` 槽则应包含允许用户在应用中导航的组件。使用 [`AppNav`](/docs/components/appnav) 组件使创建新的导航选项变得简单。对于每个链接，您只需创建一个 `AppNavItem`。
此教程中的 `AppNavItem` 组件使用三个参数：

- 链接的标签
- 目标视图
- 可选的 [`Icon`](/docs/components/icon) 组件，使用 [Tabler](https://tabler.io/icons) 的图像

在 `MainLayout` 中将所有抽屉设置组合在一起如下所示：

```java title="MainLayout"
@Route
public class MainLayout extends Composite<AppLayout> {
  private AppLayout self = getBoundComponent();
  private AppNav appNav = new AppNav();

  public MainLayout() {
    setDrawer();
  }

  private void setDrawer() {
    self.setDrawerHeaderVisible(true)
        .addToDrawerTitle(new AppTitle(true));

    appNav.addItem(new AppNavItem("仪表板", MainView.class,
        TablerIcon.create("archive")));
    appNav.addItem(new AppNavItem("关于", AboutView.class,
        TablerIcon.create("info-circle")));
    self.addToDrawer(appNav);
  }
```

### 创建标题 {#making-a-header}

`header` 槽应包含两个项目：用于显示或隐藏侧菜单的切换按钮和显示框架标题的方式。这两个项目将位于另一个用于组织组件的 [Toolbar](/docs/components/toolbar) 组件内。

您可以使用 `AppDrawerToggle` 组件在 `AppLayout` 抽屉中包含切换按钮。此组件已经使用常用图标样式化，用于隐藏菜单选项，并针对抽屉打开和关闭。

```java
// 创建容器组件
private AppLayout appLayout = new AppLayout();
private Toolbar toolbar = new Toolbar();

// 将工具栏添加到 AppLayout 标头
appLayout.addToHeader(toolbar);

// 将 AppDrawerToggle 添加到工具栏
toolbar.addToStart(new AppDrawerToggle());
```

标题还可以通过使用导航事件来显示框架标题，以检索有关传入组件的详细信息，并拥有事件侦听器以删除注册，从而防止内存泄漏。

```java
// 创建 H1 元素和导航注册
private H1 title = new H1("");
private ListenerRegistration<NavigateEvent> navigateRegistration;

// 在导航时注册事件
navigateRegistration = Router.getCurrent().onNavigate(this::onNavigate);

// 在 MainLayout 销毁之前移除监听器
@Override
protected void onDidDestroy() {
  if (navigateRegistration != null) {
    navigateRegistration.remove();
  }
}

// 从传入视图类中检索框架标题
private void onNavigate(NavigateEvent ev) {
  Component component = ev.getContext().getComponent();
  if (component != null) {
    FrameTitle frameTitle = component.getClass().getAnnotation(FrameTitle.class);
    title.setText(frameTitle != null ? frameTitle.value() : "");
  }
}
```

## 完成的 `MainLayout` {#completed-mainlayout}

以下是包含设置好的抽屉和标题内容的 `MainLayout`，其内部为 `AppLayout`：

<!-- vale off -->
<ExpandableCode title="MainLayout.java" language="java">

```java
@Route
public class MainLayout extends Composite<AppLayout> {
  private AppLayout self = getBoundComponent();
  private H1 title = new H1("");
  private ListenerRegistration<NavigateEvent> navigateRegistration;
  private Toolbar toolbar = new Toolbar();
  private AppNav appNav = new AppNav();

  public MainLayout() {
    setHeader();
    setDrawer();
    navigateRegistration = Router.getCurrent().onNavigate(this::onNavigate);
  }

  private void setHeader() {
    self.addToHeader(toolbar);

    toolbar.addToStart(new AppDrawerToggle());
    toolbar.addToTitle(title);
  }

  private void setDrawer() {
    self.setDrawerHeaderVisible(true)
        .addToDrawerTitle(new AppTitle(true));

    appNav.addItem(new AppNavItem("仪表板", MainView.class,
        TablerIcon.create("archive")));
    appNav.addItem(new AppNavItem("关于", AboutView.class,
        TablerIcon.create("info-circle")));
    self.addToDrawer(appNav);
  }

  @Override
  protected void onDidDestroy() {
    if (navigateRegistration != null) {
      navigateRegistration.remove();
    }
  }

  private void onNavigate(NavigateEvent ev) {
    Component component = ev.getContext().getComponent();
    if (component != null) {
      FrameTitle frameTitle = component.getClass().getAnnotation(FrameTitle.class);
      title.setText(frameTitle != null ? frameTitle.value() : "");
    }
  }
}
```

</ExpandableCode>
<!-- vale on -->

## 更新 `FormView` {#updating-form-view}

如前所述，`FormView` 唯一的更改是 `@Route` 注释。

  ```java
  @Route(value = "customer/:id?<[0-9]+>", outlet = MainLayout.class)
  ```

## 更新 `MainView` {#updating-main-view}

对于 `MainView`，您将绑定组件从 `Div` 更改为 `FlexLayout`。这允许您居中表格，同时将特定组件移入布局内。使用 `setItemAlignment()` 方法可以选择布局中的组件并移动它，因此您可以保持表格居中，同时将添加客户按钮锚定到布局的右上角。

```java
// 将绑定组件更改为 FlexLayout
private FlexLayout self = getBoundComponent();

// 将按钮对齐到交叉轴的末尾
self.setItemAlignment(FlexAlignment.END, addCustomer);
```

您还可以在此处进行的一项改进是表格的宽度。您可以将其设置为与其父容器 `FlexLayout` 相匹配，而不是固定宽度。然后，此 `FlexLayout` 可以具有最大宽度，以便在较大屏幕上不被过度拉伸。

```java
private FlexLayout self = getBoundComponent();
private Table<Customer> table = new Table<>();

self.setSize("100%", "100%");
self.setMaxWidth(2000);

table.setSize("100%", "294px");
```

将这些结合在一起并创建另一个方法以获取如之前那样居中的 `FlexLayout`，使 `MainView` 具有突出显示的更改：

<!-- vale off -->
<ExpandableCode title="MainView.java" language="java">

```java
@Route(value = "/", outlet = MainLayout.class)
@FrameTitle("客户表")
// highlight-next-line
public class MainView extends Composite<FlexLayout> {
  private final CustomerService customerService;
  // highlight-next-line
  private FlexLayout self = getBoundComponent();
  private Table<Customer> table = new Table<>();
  private Button addCustomer = new Button("添加客户", ButtonTheme.PRIMARY,
      e -> Router.getCurrent().navigate(FormView.class));

  public MainView(CustomerService customerService) {
    this.customerService = customerService;
    addCustomer.setWidth(200);
    buildTable();
    // highlight-next-line
    setFlexLayout();
    // highlight-next-line
    self.add(addCustomer, table);
    // highlight-next-line
    self.setItemAlignment(FlexAlignment.END, addCustomer);
  }

  private void buildTable() {
    // highlight-next-line
    table.setSize("100%", "294px");
    table.addColumn("firstName", Customer::getFirstName).setLabel("名");
    table.addColumn("lastName", Customer::getLastName).setLabel("姓");
    table.addColumn("company", Customer::getCompany).setLabel("公司");
    table.addColumn("country", Customer::getCountry).setLabel("国家");
    table.setColumnsToAutoFit();
    table.setColumnsToResizable(false);
    table.getColumns().forEach(column -> column.setSortable(true));
    table.setRepository(customerService.getRepositoryAdapter());
    table.setKeyProvider(Customer::getId);
    table.addItemClickListener(this::editCustomer);
  }

  // highlight-next-line
  private void setFlexLayout() {
    // highlight-next-line
    self.setSize("100%", "100%")
        // highlight-next-line
        .setMargin("auto")
        // highlight-next-line
        .setMaxWidth(2000)
        // highlight-next-line
        .setDirection(FlexDirection.COLUMN)
        // highlight-next-line
        .setAlignment(FlexAlignment.CENTER);
        // highlight-next-line
  }

  private void editCustomer(TableItemClickEvent<Customer> e) {
    Router.getCurrent().navigate(FormView.class,
        ParametersBag.of("id=" + e.getItemKey()));
  }
}
```

</ExpandableCode>
<!-- vale on -->
