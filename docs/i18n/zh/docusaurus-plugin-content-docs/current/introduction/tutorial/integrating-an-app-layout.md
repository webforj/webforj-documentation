---
title: Integrating an App Layout
sidebar_position: 7
description: Step 6 - Using the AppLayout and FlexLayout components.
_i18n_hash: e56d98e67ff6ee74a4dc1ee81346350d
---
在此步骤中，您将把应用程序的所有部分整合到一个统一的应用布局中。到此步骤结束时，您的应用程序结构将与[侧边菜单原型](/docs/building-ui/archetypes/sidemenu)非常相似，并且您将更好地理解以下组件和概念的工作原理：

- [`FlexLayout`](/docs/components/flex-layout)
- [路由出口](/docs/routing/route-hierarchy/route-outlets)
- [`AppLayout`](/docs/components/app-layout)
- [`AppNav`](/docs/components/appnav)

## 运行应用程序 {#running-the-app}

在开发应用程序时，您可以将[6-integrating-an-app-layout](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout)用作比较。要查看应用程序的运行情况：

1. 导航到包含 `pom.xml` 文件的顶级目录，如果您按照 GitHub 上的版本进行操作，这里就是 `6-integrating-an-app-layout`。

2. 使用以下 Maven 命令在本地运行 Spring Boot 应用程序：
    ```bash
    mvn
    ```

运行应用程序会自动在 `http://localhost:8080` 处打开一个新浏览器。

## 创建可重用组件 {#creating-a-reusable-component}

在之前的步骤[路由与组合](/docs/introduction/tutorial/routing-and-composites)中，您创建了两个组合组件，包含客户表的内容和客户表单。作为此步骤的一部分，您将创建一个更小的可重用组合组件，用于在侧边菜单中显示应用程序名称和关于页面。如果将来您决定更改应用程序名称，只需在此组件中进行更新。

在 `src/main/java/com/webforj/tutorial/components` 中，创建一个名为 `AppTitle` 的类。`AppTitle` 的绑定组件将是 `FlexLayout`，这是一个在此步骤中用于展示如何制作更复杂布局的容器组件。对于此 `FlexLayout`，您将安排项目的方向和项目之间的间距。这是通过使用 `setDirection()` 和 `setSpacing()` 方法分别完成的。

```java title='AppTitle.java'
// 使绑定组件为 FlexLayout
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();

  public AppTitle() {

    // 垂直排列项目
    self.setDirection(FlexDirection.COLUMN);

    // 设置项目间距
    self.setSpacing("0px");
  }
}
```

然后使用标准 HTML 元素创建标题和副标题。将标题元素的下边距设置为 `0px` 可以让元素更靠近，您可以使用[DWC CSS变量](/docs/styling/css-variables)样式化副标题。

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

尽管 `AppTitle` 很简单，但在构造函数方法中添加一个布尔参数可以控制何时渲染组件的某些部分，例如副标题。

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

总体来看，可重用组件应如下所示：

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

添加新创建的 `AppTitle` 组件的第一个地方将是关于页面。该页面包括一张图像和 `AppTitle` 组件，通过使用另一个 `FlexLayout` 将其居中。

### 使用 `FlexLayout` 居中内容 {#centering-content-using-a-flexlayout}

目标是使用 `FlexLayout` 居中关于页面的内容。`FlexLayout` 组件遵循[CSS flexbox 布局模型](https://css-tricks.com/snippets/css/a-guide-to-flexbox/)。`FlexLayout` 的方法，例如之前用于将项目定向为列的方法，是排列项目的不同方式。

在 `FlexLayout` 中排列项目的方法使用相对方向系统。与其考虑水平和垂直轴，不如将与项目平行的轴视为主轴，而与项目垂直的轴视为交叉轴。

将 `FlexJustifyContent` 和 `FlexAlignment` 属性都设置为 `CENTER` 将使项目在 `FlexLayout` 的主轴和交叉轴上居中，并使 `FlexLayout` 占据其父容器的全部内容使其在页面上居中。

```java
private final FlexLayout layout = new FlexLayout();

// 填充父元素的整个空间
layout.setSize("100%", "100%");

// 设置主轴为垂直
layout.setDirection(FlexDirection.COLUMN);

// 在交叉轴上居中项目
layout.setAlignment(FlexAlignment.CENTER);

// 在主轴上居中项目
layout.setJustifyContent(FlexJustifyContent.CENTER);
```

为了帮助可视化不同方法的工作原理，请查看博客文章[FlexWrap you mind around webforJ's FlexLayout](/blog/2025/08/26/flexlayout-container)。

### 添加资源 {#adding-resources}

将放入居中 `FlexLayout` 中的项目之一是图像。对于本教程，您可以查看并下载[关于页面的图像](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout/src/main/resources/static/images/Files.svg)。
下载后，将其添加到项目的静态文件夹中，位置在 `src/main/resources/static/images`，并命名为 `Files.svg`。

将此图像放在静态文件夹中，可以通过 Web 服务器协议引用它，就像您在第一步中引用 CSS 文件[创建基础应用](/docs/introduction/tutorial/creating-a-basic-app)时所做的那样。然后，您可以将其用作 HTML 元素，如下所示：

```java
private Img fileImg = new Img("ws://images/Files.svg");
```

### 创建 `AboutView` {#creating-about-view}

与两个现有应用页面一样，关于页面将是一个可路由的视图。在 `src/main/java/com/webforj/tutorial/views` 中，添加一个名为 `AboutView` 的类。使用 `FlexLayout` 作为绑定组件，与 `AppTitle` 中一样。

由于您已将类命名为 `AboutView`，因此无需为 URL 映射提供自定义值；该页面默认在 `http://localhost:8080/about` 渲染。

以下是使用之前步骤中的概念与新创建的组件结合创建新视图并居中内容时的情况：

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

在[路由与组合](/docs/introduction/tutorial/routing-and-composites)步骤中简要提到，但有两种[路由类型](/docs/routing/route-hierarchy/route-types)。`MainView`、`FormView`和`AboutView`都是 `View` 路由，而您将用来创建应用程序侧菜单的路由类型是 `Layout` 路由。

布局路由包装子视图，并允许某些 UI 部分在视图间保持存在，例如侧边菜单。在 `src/main/java/com/webforj/tutorial/layouts` 中创建一个名为 `MainLayout` 的类。

### 路由出口 {#route-outlets}

与视图路由一样，`MainLayout` 也需要 `@Route` 注解。但是，由于它有 `Layout` 作为后缀，并且布局路由不会贡献 URL，因此此注解不需要任何参数。

```java title="MainLayout.java" {1}
@Route
public class MainLayout {

  public MainLayout() {

  }
}
```

应用程序知道在 `MainLayout` 内渲染哪些视图，通过在每个视图中将布局类声明为[路由出口](/docs/routing/route-hierarchy/route-outlets)。之前的步骤中只设置了 `@Route` 注解中的 `value` 属性，因此现在您需要明确说明视图类的 `value` 和 `outlet` 属性。

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
这是本步骤中对 `FormView` 和 `AboutView` 的最后修改，因此请记得在运行应用程序之前更新这些视图的 `@Route` 注解。
:::

## 使用 `AppLayout` 组件 {#using-the-app-layout-component}

现在您的应用程序在 `MainLayout` 内渲染视图，您可以选择这些组件的渲染位置。将 `AppLayout` 作为 `MainLayout` 的绑定组件允许您在默认情况下将视图存储在主内容区域，同时还提供了多个区域来为标题和侧边菜单添加项目。

### 插槽 {#slots}

对于许多 webforJ 容器，使用 `add()` 方法会将 UI 组件添加到主内容区域。在 `AppLayout` 组件中，有多个区域用于添加 UI 组件，每个区域在一个单独的插槽中。通过将 `MainLayout` 标记为布局路由，并将其绑定组件设置为 `AppLayout`，视图会自动渲染在主内容插槽中。

在此步骤中，您将使用 `drawer-title` 和 `drawer` 插槽来创建侧边菜单，而使用 `header` 插槽来显示用户在该页面上的状态和侧边菜单的切换。

### 创建侧边菜单 {#making-a-side-menu}

当设备上有足够的屏幕空间时，`AppLayout` 组件显示一个抽屉。在此处您将再次添加 `AppTitle` 和允许用户导航应用程序的项目。

默认情况下，`AppLayout` 不显示抽屉头，但使用 `setDrawerHeaderVisible()` 方法可以显示位于 `drawer-title` 插槽内的项目，这将是显示其副标题的 `AppTitle`。

```java
private AppLayout appLayout = new AppLayout();

// 显示抽屉头
appLayout.setDrawerHeaderVisible(true);

// 将 AppTitle 添加到抽屉头并显示其副标题
appLayout.addToDrawerTitle(new AppTitle(true));
```

`drawer` 插槽应包含允许用户在应用程序中导航的组件。使用 [`AppNav`](/docs/components/appnav) 组件很容易创建新的导航选项。对于每个链接，您只需创建一个 `AppNavItem`。
本教程中的 `AppNavItem` 组件使用三个参数：

- 链接的标签
- 目标视图
- 可选的 [`Icon`](/docs/components/icon) 组件，使用来自[Tabler](https://tabler.io/icons) 的图像

将所有抽屉设置组合到 `MainLayout` 中如下所示：

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

### 创建头部 {#making-a-header}

`header` 插槽应包括两个项目：用于显示或隐藏侧边菜单的切换和显示框架标题的方法。这两个项目都将位于一个[工具栏](/docs/components/toolbar)组件中，这是组织组件的另一种方式。

您可以使用 `AppDrawerToggle` 组件包含 `AppLayout` 抽屉的切换。此组件已经用常用的隐藏菜单选项图标进行了样式设置，并将目标设置为打开和关闭抽屉。

```java
// 创建容器组件
private AppLayout appLayout = new AppLayout();
private Toolbar toolbar = new Toolbar();

// 将工具栏添加到 AppLayout 头部
appLayout.addToHeader(toolbar);

// 将 AppDrawerToggle 添加到工具栏
toolbar.addToStart(new AppDrawerToggle());
```

头部还可以通过使用导航事件来检索有关传入组件的详细信息来显示框架标题，同时监听事件以删除注册以防止内存泄漏。

```java
// 创建 H1 元素和导航注册
private H1 title = new H1("");
private ListenerRegistration<NavigateEvent> navigateRegistration;

// 导航时注册事件
navigateRegistration = Router.getCurrent().onNavigate(this::onNavigate);

// 在 MainLayout 被销毁之前移除监听器
@Override
protected void onDidDestroy() {
  if (navigateRegistration != null) {
    navigateRegistration.remove();
  }
}

// 从传入视图类检索框架标题
private void onNavigate(NavigateEvent ev) {
  Component component = ev.getContext().getComponent();
  if (component != null) {
    FrameTitle frameTitle = component.getClass().getAnnotation(FrameTitle.class);
    title.setText(frameTitle != null ? frameTitle.value() : "");
  }
}
```

## 完成的 `MainLayout` {#completed-mainlayout}

这是在 `AppLayout` 中为抽屉和头部创建内容的 `MainLayout`：

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

如前所述，对 `FormView` 的唯一更改是 `@Route` 注解。

  ```java
  @Route(value = "customer/:id?<[0-9]+>", outlet = MainLayout.class)
  ```

## 更新 `MainView` {#updating-main-view}

对于 `MainView`，您将绑定组件更改为 `FlexLayout`。这使您能够居中表，同时也将特定组件移动到布局内。使用 `setItemAlignment()` 方法可让您选择布局中的组件并移动它，因此可以将表保持居中，同时将添加客户按钮固定在布局的右上角。

```java
// 将绑定组件更改为 FlexLayout
private FlexLayout self = getBoundComponent();

// 将按钮对齐到交叉轴的末尾
self.setItemAlignment(FlexAlignment.END, addCustomer);
```

在这里可以进行的另一项改进是表的宽度。您可以将其设置为与其父容器 `FlexLayout` 匹配，而不是固定宽度。然后该 `FlexLayout` 可以有一个最大宽度，因此不会在较大屏幕上过度拉伸。

```java
private FlexLayout self = getBoundComponent();
private Table<Customer> table = new Table<>();

self.setSize("100%", "100%");
self.setMaxWidth(2000);

table.setSize("100%", "294px");
```

将这些组合在一起，并制作另一个方法来使 `FlexLayout` 像以前一样居中，使得 `MainView` 具有突出的变化：

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
