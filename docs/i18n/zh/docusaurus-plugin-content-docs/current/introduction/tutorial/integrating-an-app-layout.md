---
title: Integrating an App Layout
sidebar_position: 7
description: Step 6 - Using the AppLayout and FlexLayout components.
sidebar_class_name: new-content
_i18n_hash: 66c364d83c3b6d574acaca5156bbb018
---
在这一步，您将把应用程序的所有部分合并为一个一致的应用程序布局。在这一结束时，您的应用程序结构将与 [SideMenu 原型](/docs/building-ui/archetypes/sidemenu) 非常相似，并且您将更好地理解以下组件和概念是如何工作的：

- [`FlexLayout`](/docs/components/flex-layout)
- [路由出口](/docs/routing/route-hierarchy/route-outlets)
- [`AppLayout`](/docs/components/app-layout)
- [`AppNav`](/docs/components/appnav)

## 运行应用程序 {#running-the-app}

在开发应用程序时，您可以使用 [6-integrating-an-app-layout](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout) 作为对照。要查看应用程序的运行情况：

1. 导航到包含 `pom.xml` 文件的顶级目录，如果您是在 GitHub 上跟随版本，这就是 `6-integrating-an-app-layout`。

2. 使用以下 Maven 命令在本地运行 Spring Boot 应用程序：
    ```bash
    mvn
    ```

运行应用程序将自动在 `http://localhost:8080` 打开一个新浏览器窗口。

## 创建可重用组件 {#creating-a-reusable-component}

在之前的步骤中，[路由和组合](/docs/introduction/tutorial/routing-and-composites)，您创建了两个组合组件，其中包含客户表和客户表单的内容。
作为这一步的一部分，您将创建一个更小的、可重用的组合组件，以在侧边菜单和关于页面中显示应用程序的名称。如果您决定将来更改应用程序的名称，您只需在此组件中更新即可。

在 `src/main/java/com/webforj/tutorial/components` 中，创建一个名为 `AppTitle` 的类。`AppTitle` 的绑定组件将是 `FlexLayout`，这是一个在本步骤中用于向您展示如何创建更复杂布局的容器组件。
对于这个 `FlexLayout`，您将安排项目的方向和项目之间的间距。这分别通过使用 `setDirection()` 和 `setSpacing()` 方法完成。

```java title='AppTitle.java'
// 将绑定组件设为 FlexLayout
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  
  public AppTitle() {

    // 竖直排列项目
    self.setDirection(FlexDirection.COLUMN);

    // 设置项目之间的间距
    self.setSpacing("0px");
  }
}
```

然后使用标准 HTML 元素创建标题和副标题。将标题元素的底部边距设置为 `0px` 可以使元素更紧凑，并且您可以使用 [DWC CSS 变量](/docs/styling/css-variables) 来样式化副标题。

```java title='AppTitle.java' {3-4,7-9,13}
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private H2 title = new H2("客户管理");
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

尽管 `AppTitle` 很简单，但在构造方法中添加一个布尔参数允许您控制何时渲染组件的某些部分，例如副标题。

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

所有内容组合在一起，可重用组件应如下所示：

```java title='AppTitle.java'
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private H2 title = new H2("客户管理");
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

添加新创建的 `AppTitle` 组件的第一处将是关于页面。此页面包括一张图像和 `AppTitle` 组件，使用另一个 `FlexLayout` 将其居中。

### 使用 `FlexLayout` 居中内容 {#centering-content-using-a-flexlayout}

目标是使用 `FlexLayout` 居中关于页面的内容。`FlexLayout` 组件遵循 [CSS flexbox 布局模型](https://css-tricks.com/snippets/css/a-guide-to-flexbox/)。用于 `FlexLayout` 的方法，如先前用于将项目定向到列的那些，是安排项目的不同方法。

用于在 `FlexLayout` 中排列项目的方法使用相对方向系统。与其考虑水平和垂直轴，不如将与项目平行的轴视为主轴，与项目垂直的轴视为交叉轴。

将 `FlexJustifyContent` 和 `FlexAlignment` 属性都设置为 `CENTER` 将在 `FlexLayout` 中沿主轴和交叉轴居中项目，并使 `FlexLayout` 占据其父容器的全部，从而使其在页面居中。

```java
private final FlexLayout layout = new FlexLayout();

// 填充父元素的整个空间
layout.setSize("100%", "100%");

// 使主轴垂直
layout.setDirection(FlexDirection.COLUMN);

// 在交叉轴上居中项目
layout.setAlignment(FlexAlignment.CENTER);

// 在主轴上居中项目
layout.setJustifyContent(FlexJustifyContent.CENTER);
```

为了帮助可视化不同方法的工作方式，请查看博文 [FlexWrap your mind around webforJ's FlexLayout](/blog/2025/08/26/flexlayout-container)。

### 添加资源 {#adding-resources}

将放入居中 `FlexLayout` 中的一项是图像。对于本教程，您可以查看并下载 [关于页面的图像](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout/src/main/resources/static/images/Files.svg) 在 GitHub 上。
下载后，将其添加到项目的静态文件夹中，路径为 `src/main/resources/static/images`，并将其命名为 `Files.svg`。

将此图像放在静态文件夹中可以让您使用 Web 服务器协议引用它，正如您在第一步 [创建基本应用程序](/docs/introduction/tutorial/creating-a-basic-app) 中引用 CSS 文件时所做的。然后，您可以像这样在应用程序中用 HTML 元素使用它：

```java
private Img fileImg = new Img("ws://images/Files.svg");
```

### 创建 `AboutView` {#creating-about-view}

与两个现有应用页面一样，关于页面将是一个可路由视图。在 `src/main/java/com/webforj/tutorial/views` 中，添加一个名为 `AboutView` 的类。将 `FlexLayout` 用作绑定组件，正如您对 `AppTitle` 所做的那样。

由于您已将类命名为 `AboutView`，因此不需要为 URL 映射提供自定义值；此页面默认在 `http://localhost:8080/about` 渲染。

下面是当您使用之前步骤中的概念与新创建的组件结合起来创建新视图并居中内容时的样子： 

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

在 [路由和组合](/docs/introduction/tutorial/routing-and-composites) 步骤中稍有提及，但有两种 [路由类型](/docs/routing/route-hierarchy/route-types)。`MainView`、`FormView` 和 `AboutView` 都是 `View` 路由，而您将用于创建应用程序侧菜单的路由类型是 `Layout` 路由。

布局路由包裹子视图，并允许某些 UI 部分在视图间保持不变，例如侧边菜单。在 `src/main/java/com/webforj/tutorial/layouts` 中，创建一个名为 `MainLayout` 的类。

### 路由出口 {#route-outlets}

与视图路由一样，`MainLayout` 也需要一个 `@Route` 注解。然而，由于它有 `Layout` 作为后缀，并且布局路由不对 URL 产生贡献，因此此注解不需要任何参数。

```java title="MainLayout.java" {1}
@Route
public class MainLayout {

  public MainLayout() {

  }
}
```

应用程序知道在 `MainLayout` 中渲染哪些视图，通过在每个视图中声明布局类作为 [路由出口](/docs/routing/route-hierarchy/route-outlets)。先前的步骤仅在 `@Route` 注解中设置了 `value` 属性，因此现在您需要明确说明视图类的 `value` 和 `outlet` 属性。

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
这是在此步骤中对 `FormView` 和 `AboutView` 的最后修改，因此在运行应用程序之前，请记得更新这些视图的 `@Route` 注解。
:::

## 使用 `AppLayout` 组件 {#using-the-app-layout-component}

现在您的应用程序在 `MainLayout` 中渲染视图，您可以选择这些组件的渲染位置。选择 `AppLayout` 作为 `MainLayout` 的绑定组件使您能够默认存储视图在主内容区域，同时还提供了不同区域以向顶部和侧边菜单添加项目。

### 插槽 {#slots}

对于许多 webforJ 容器，使用 `add()` 方法将 UI 组件添加到主内容区域。在 `AppLayout` 组件中，有多个区域用于添加 UI 组件，每个区域在一个单独的插槽中。
通过将 `MainLayout` 标记为布局路由并将其绑定组件设置为 `AppLayout`，视图会自动渲染到主内容插槽中。

在此步骤中，您将使用 `drawer-title` 和 `drawer` 插槽来创建侧边菜单，并使用 `header` 插槽显示用户所在页面及侧边菜单的切换。

### 创建侧边菜单 {#making-a-side-menu}

当设备上有足够的屏幕空间时，`AppLayout` 组件会显示一个抽屉。在这里，您将再次添加 `AppTitle` 以及允许用户导航应用程序的项目。

默认情况下，`AppLayout` 不显示抽屉标题，但使用 `setDrawerHeaderVisible()` 方法可以显示位于 `drawer-title` 插槽中的项目，这将是显示 `AppTitle` 及其副标题。

```java
private AppLayout appLayout = new AppLayout();

// 显示抽屉标题
appLayout.setDrawerHeaderVisible(true);

// 将 AppTitle 添加到抽屉标题中并显示其副标题
appLayout.addToDrawerTitle(new AppTitle(true));
```

`drawer` 插槽则应该包含允许用户在应用程序中导航的组件。使用 [`AppNav`](/docs/components/appnav) 组件可以轻松创建新的导航选项。对于每个链接，您只需创建一个 `AppNavItem`。
本教程中的 `AppNavItem` 组件使用三个参数：

- 链接的标签
- 目标视图
- 一个可选的 [`Icon`](/docs/components/icon) 组件，使用来自 [Tabler](https://tabler.io/icons) 的图像

将所有抽屉设置分组在 `MainLayout` 中如下所示：

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

### 创建一个头部 {#making-a-header}

`header` 插槽应包括两个项目：一个显示或隐藏侧边菜单的切换和一种显示框架标题的方式。这两个项目都将在 [Toolbar](/docs/components/toolbar) 组件中，是另一种组织组件的方式。

您可以使用 `AppDrawerToggle` 组件在 `AppLayout` 抽屉中包含切换。此组件已经使用一个常用的隐藏菜单选项的图标样式，并目标是打开和关闭抽屉。 

```java
// 创建容器组件
private AppLayout appLayout = new AppLayout();
private Toolbar toolbar = new Toolbar();

// 将 Toolbar 添加到 AppLayout 头部
appLayout.addToHeader(toolbar);

// 将 AppDrawerToggle 添加到工具栏
toolbar.addToStart(new AppDrawerToggle());
```

头部还可以使用导航事件显示框架标题，通过检索有关传入组件的详细信息，同时拥有事件监听器以移除注册以防止内存泄漏。

```java
// 创建 H1 元素和导航注册
private H1 title = new H1("");
private ListenerRegistration<NavigateEvent> navigateRegistration;

// 注册导航事件
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

## 完成的 `MainLayout`

这是 `MainLayout`，其中创建了抽屉和头部的内容，位于 `AppLayout` 内部：

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

如前所述，对 `FormView` 唯一的更改是 `@Route` 注解。

  ```java
  @Route(value = "customer/:id?<[0-9]+>", outlet = MainLayout.class)
  ```

## 更新 `MainView` {#updating-main-view}

对于 `MainView`，您将绑定组件从 `Div` 更改为 `FlexLayout`。这使您能够居中表格，同时将特定组件移动到布局中。使用 `setItemAlignment()` 方法可让您选择布局中的组件并移动它，因此您可以将表格保持居中，同时将添加客户按钮锚定在布局的右上角。

```java
// 将绑定组件更改为 FlexLayout
private FlexLayout self = getBoundComponent();

// 将按钮对齐到交叉轴的末端
self.setItemAlignment(FlexAlignment.END, addCustomer);
```

您可以在这里进行的另一个改进是表格的宽度。您可以将其设置为与其父容器 FlexLayout 匹配，而不是固定宽度。然后，该 FlexLayout 可以具有最大宽度，以便它在较大屏幕上不会过度拉伸。 

```java 
private FlexLayout self = getBoundComponent();
private Table<Customer> table = new Table<>();

self.setSize("100%", "100%");
self.setMaxWidth(2000);

table.setSize("100%", "294px");
```

将这些结合在一起并创建另一个方法来使 FlexLayout 居中，如前面的做法，使得 `MainView` 看起来如下所示，突出显示了所做的更改：

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
      table.addColumn("firstName", Customer::getFirstName).setLabel("姓");
      table.addColumn("lastName", Customer::getLastName).setLabel("名");
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
