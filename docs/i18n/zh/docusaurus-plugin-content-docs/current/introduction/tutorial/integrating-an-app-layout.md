---
title: Integrating an App Layout
sidebar_position: 7
description: Step 6 - Using the AppLayout and FlexLayout components.
_i18n_hash: ddf62eb6d62a711c38f9ddaf9caeabad
---
在此步骤中，您将把应用程序的所有部分整合到一个协调的应用布局中。在该步骤结束时，您的应用结构将与 [SideMenu 原型](/docs/building-ui/archetypes/sidemenu)  closely resembling，并且您将更好地理解以下组件和概念是如何工作的：

- [`FlexLayout`](/docs/components/flex-layout)
- [Route Outlets](/docs/routing/route-hierarchy/route-outlets)
- [`AppLayout`](/docs/components/app-layout)
- [`AppNav`](/docs/components/appnav)

## 运行应用 {#running-the-app}

在开发应用时，您可以使用 [6-integrating-an-app-layout](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout) 作为比较。要查看应用的运行情况：

1. 导航到包含 `pom.xml` 文件的顶级目录，如果您正在按照 GitHub 上的版本学习，则为 `6-integrating-an-app-layout`。

2. 使用以下 Maven 命令在本地运行 Spring Boot 应用：
    ```bash
    mvn
    ```

运行应用会自动在 `http://localhost:8080` 打开一个新浏览器。

## 创建可重用组件 {#creating-a-reusable-component}

在之前的步骤中，[Routing and Composites](/docs/introduction/tutorial/routing-and-composites)，您创建了两个组合组件，其中包含客户表和客户表单的内容。
作为此步骤的一部分，您将创建一个更小的可重用组合组件，在侧菜单和关于页面中显示应用名称。如果您决定将来更改应用名称，您只需在此组件中更新即可。

在 `src/main/java/com/webforj/tutorial/components` 中创建一个名为 `AppTitle` 的类。`AppTitle` 的绑定组件将是 `FlexLayout`，这是在此步骤中使用的容器组件，用于向您展示如何制作更复杂的布局。
对于这个 `FlexLayout`，您将安排项目的方向和项目之间的间距。这是通过分别使用 `setDirection()` 和 `setSpacing()` 方法完成的。

```java title='AppTitle.java'
// 将绑定组件设为 FlexLayout
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();

  public AppTitle() {

    // 垂直排列项目
    self.setDirection(FlexDirection.COLUMN);

    // 设置项目之间的间隙
    self.setSpacing("0px");
  }
}
```

然后使用标准的 HTML 元素来创建标题和副标题。将标题元素的底部边距设置为 `0px` 会使元素更接近，您可以使用 [DWC CSS 变量](/docs/styling/css-variables) 来样式化副标题。

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

### 可选呈现 {#optional-rendering}

尽管 `AppTitle` 是简单的，但向构造函数添加一个布尔参数可以让您控制何时呈现组件的某些部分，例如副标题。

```java title='AppTitle.java'
// 添加一个布尔参数
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

综合起来，可重用组件应如下所示：

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

添加新创建的 `AppTitle` 组件的第一个地方将是关于页面。该页面包括一个图像和 `AppTitle` 组件，通过使用另一个 `FlexLayout` 将其居中。

### 使用 `FlexLayout` 居中内容 {#centering-content-using-a-flexlayout}

目标是使用 `FlexLayout` 居中关于页面的内容。`FlexLayout` 组件遵循 [CSS flexbox 布局模型](https://css-tricks.com/snippets/css/a-guide-to-flexbox/)。用于 `FlexLayout` 的方法，例如先前用于将项目排列为列的方法，是排列项目的不同方式。

排列 `FlexLayout` 中项目的方法使用相对方向系统。与其考虑水平和垂直轴，不如将与项目平行的轴视为主轴，与项目垂直的轴视为交叉轴。

将 `FlexJustifyContent` 和 `FlexAlignment` 属性均设置为 `CENTER` 将在 `FlexLayout` 中沿主轴和交叉轴居中项目，并使 `FlexLayout` 占据其父容器的全部空间，使其在页面中居中。

```java
private final FlexLayout layout = new FlexLayout();

// 填充父元素的整个空间
layout.setSize("100%", "100%");

// 主轴方向为垂直
layout.setDirection(FlexDirection.COLUMN);

// 在交叉轴上居中项目
layout.setAlignment(FlexAlignment.CENTER);

// 在主轴上居中项目
layout.setJustifyContent(FlexJustifyContent.CENTER);
```

为了帮助可视化不同方法的工作方式，请查看博客文章 [FlexWrap your mind around webforJ's FlexLayout](/blog/2025/08/26/flexlayout-container)。

### 添加资源 {#adding-resources}

将放在居中的 `FlexLayout` 中的一个项目是图像。在本教程中，您可以在 GitHub 上查看并下载 [关于页面的图像](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout/src/main/resources/static/images/Files.svg)。
下载后，将其添加到项目的静态文件夹中，路径为 `src/main/resources/static/images`，并命名为 `Files.svg`。

将此图像放入静态文件夹中，可以使用 [Webserver 协议](/docs/managing-resources/assets-protocols#the-webserver-protocol) 引用它。然后，您可以像这样在应用中使用它作为 HTML 元素：

```java
private Img fileImg = new Img("ws://images/Files.svg");
```

### 创建 `AboutView` {#creating-about-view}

与两个现有应用页面一样，关于页面将是一个可路由视图。在 `src/main/java/com/webforj/tutorial/views` 中添加名为 `AboutView` 的类。将 `FlexLayout` 用作绑定组件，如您在 `AppTitle` 中所做的那样。

由于您已将类命名为 `AboutView`，因此不需要为 URL 映射提供自定义值；该页面默认在 `http://localhost:8080/about` 处呈现。

这是在使用前面步骤中的概念以及新创建的组件创建新视图及居中内容时的样子：

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

在 [Routing and Composites](/docs/introduction/tutorial/routing-and-composites) 步骤中简要提到，但有两种 [路由类型](/docs/routing/route-hierarchy/route-types)。`MainView`、`FormView` 和 `AboutView` 都是 `View` 路由，而您将用于创建应用侧菜单的路由类型是 `Layout` 路由。

布局路由包装子视图，并允许某些 UI 部分在视图之间保持不变，例如侧菜单。在 `src/main/java/com/webforj/tutorial/layouts` 中创建一个名为 `MainLayout` 的类。

### 路由出口 {#route-outlets}

与视图路由类似，`MainLayout` 需要 `@Route` 注解。然而，由于其后缀为 `Layout`，且布局路由不需要为 URL 贡献，因此该注解不需要任何参数。

```java title="MainLayout.java" {1}
@Route
public class MainLayout {

  public MainLayout() {

  }
}
```

应用通过在每个视图中声明布局类作为 [路由出口](/docs/routing/route-hierarchy/route-outlets) 来知道渲染哪个视图。前面步骤中，`@Route` 注解仅设置了 `value` 属性，因此现在您需要明确说明视图类的 `value` 和 `outlet` 属性。

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

:::note 最后调整
这是此步骤中对 `FormView` 和 `AboutView` 的最后修改，因此在运行应用之前，请记得更新这些视图的 `@Route` 注解。
:::

## 使用 `AppLayout` 组件 {#using-the-app-layout-component}

现在您的应用在 `MainLayout` 中渲染视图，您可以选择这些组件的渲染位置。选择将 `AppLayout` 作为 `MainLayout` 的绑定组件，允许您默认将视图存储在主内容区域，同时为标题和侧菜单添加不同区域。

### 插槽 {#slots}

对于许多 webforJ 容器，使用 `add()` 方法将 UI 组件添加到主内容区域。在 `AppLayout` 组件中，有多个区域可用于添加 UI 组件，每个区域在一个单独的插槽中。
通过将 `MainLayout` 标记为布局路由并将其绑定组件设置为 `AppLayout`，视图会自动在主内容插槽中呈现。

在此步骤中，您将使用 `drawer-title` 和 `drawer` 插槽创建侧菜单，并使用 `header` 插槽显示用户所在的页面以及侧菜单的切换。

### 制作侧菜单 {#making-a-side-menu}

当设备上有足够的屏幕空间时，`AppLayout` 组件会显示一个抽屉。这是您将再次添加 `AppTitle` 和允许用户导航应用的项目的地方。

默认情况下，`AppLayout` 不会显示抽屉标题，但使用 `setDrawerHeaderVisible()` 方法可以显示 `drawer-title` 插槽内的项目，也就是包含 `AppTitle` 及其显示副标题的。

```java
private AppLayout appLayout = new AppLayout();

// 显示抽屉标题
appLayout.setDrawerHeaderVisible(true);

// 将 AppTitle 及其副标题添加到抽屉标题中
appLayout.addToDrawerTitle(new AppTitle(true));
```

`drawer` 插槽应包含允许用户在应用中导航的组件。使用 [`AppNav`](/docs/components/appnav) 组件可以轻松创建新的导航选项。对于每个链接，您只需创建一个 `AppNavItem`。
本教程中的 `AppNavItem` 组件使用三个参数：

- 链接的标签
- 目标视图
- 一个可选的 [`Icon`](/docs/components/icon) 组件，使用 [Tabler](https://tabler.io/icons) 中的图像

在 `MainLayout` 中将抽屉设置组合在一起看起来如下：

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

### 制作标题 {#making-a-header}

`header` 插槽应包含两个项目：一个切换以显示或隐藏侧菜单以及一种显示框架标题的方式。这两个项目都将位于一个 [Toolbar](/docs/components/toolbar) 组件内，另一种组织组件的方式。

您可以通过 `AppDrawerToggle` 组件包含 `AppLayout` 抽屉的切换。该组件已使用常用的隐藏菜单选项图标进行样式设置，并且目标是打开和关闭抽屉。

```java
// 创建容器组件
private AppLayout appLayout = new AppLayout();
private Toolbar toolbar = new Toolbar();

// 将工具条添加到 AppLayout 标头中
appLayout.addToHeader(toolbar);

// 将 AppDrawerToggle 添加到工具条中
toolbar.addToStart(new AppDrawerToggle());
```

标题也可以通过使用导航事件检索有关传入组件的详细信息来显示框架标题，同时设置事件监听器以删除注册以防止内存泄漏。

```java
// 创建 H1 元素和导航注册
private H1 title = new H1("");
private ListenerRegistration<NavigateEvent> navigateRegistration;

// 在导航时注册事件
navigateRegistration = Router.getCurrent().onNavigate(this::onNavigate);

// 在 MainLayout 被销毁之前移除监听器
@Override
protected void onDidDestroy() {
  if (navigateRegistration != null) {
    navigateRegistration.remove();
  }
}

// 从传入的视图类中检索框架标题
private void onNavigate(NavigateEvent ev) {
  Component component = ev.getContext().getComponent();
  if (component != null) {
    FrameTitle frameTitle = component.getClass().getAnnotation(FrameTitle.class);
    title.setText(frameTitle != null ? frameTitle.value() : "");
  }
}
```

## 完成的 `MainLayout` {#completed-mainlayout}

这是包含在 `AppLayout` 中的抽屉和标题内容的 `MainLayout`：

<!-- vale off -->
<ExpandableCode title="MainLayout.java" language="java">
{`@Route
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
`}
</ExpandableCode>
<!-- vale on -->

## 更新 `FormView` {#updating-form-view}

如前所述， `FormView` 的唯一更改是 `@Route` 注解。

  ```java
  @Route(value = "customer/:id?<[0-9]+>", outlet = MainLayout.class)
  ```

## 更新 `MainView` {#updating-main-view}

对于 `MainView`，您将绑定组件从 `Div` 更改为 `FlexLayout`。这允许您居中表，同时将特定组件移动到布局中。使用 `setItemAlignment()` 方法让您选择布局中的组件并移动它，因此您可以保持表居中，同时将添加客户按钮锚定到布局的右上角。

```java
// 将绑定组件更改为 FlexLayout
private FlexLayout self = getBoundComponent();

// 将按钮对齐到交叉轴的末尾
self.setItemAlignment(FlexAlignment.END, addCustomer);
```

您还可以在这里做另一个改进，即表的宽度。与其使用固定宽度，不如将其设置为匹配其父容器 `FlexLayout`。然后该 `FlexLayout` 可以有一个最大宽度，以便在较大屏幕上不会过于拉伸。

```java
private FlexLayout self = getBoundComponent();
private Table<Customer> table = new Table<>();

self.setSize("100%", "100%");
self.setMaxWidth(2000);

table.setSize("100%", "294px");
```

将这些结合在一起，并制作另一个方法使 `FlexLayout` 像以前一样居中，`MainView` 将具有突出显示的更改：

<!-- vale off -->
<ExpandableCode title="MainView.java" language="java">
{`@Route(value = "/", outlet = MainLayout.class)
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
`}
</ExpandableCode>
<!-- vale on -->
