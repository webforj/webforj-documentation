---
sidebar_position: 6
title: 测试应用程序
description: >-
  Start a TestBed test with the routes or the whole app, navigate, test secured
  routes, answer dialogs, control timers, and answer for the browser.
_i18n_hash: 4194d4d355b0e26f86b8c3c53ea353c9
---
视图很少独立存在。路由会带用户进入视图，路由安全机制可能会保护它，视图会通过对话框询问用户，会按计时器更新自己，也会向浏览器请求结果。在运行中的应用里，服务器、用户、时钟和浏览器各司其职。在 TestBed 测试中，这些角色都由测试扮演：测试决定运行多少应用代码，像地址栏一样导航，代替用户回答对话框，决定时间何时流逝，并代替浏览器提供结果。

<!-- INTRO_END -->

## 选择测试的初始状态 {#choosing-what-a-test-starts-with}

`@WebforjTest` 的属性决定每个测试的初始状态：

- **不指定属性。** 一个空页面。测试通过 `context.add()` 添加要测试的组件，正如其他页面中的测试所做的那样。
- **`routeClasses`。** 列出的 `@Route` 视图，路径取自其注解；应用中不包含其他内容。
- **`routePackages`。** 列出的包中所有 `@Route` 视图，发现方式与 `@Routify` 相同。
- **`app`。** 启动应用类，方式与部署时相同。它的 `run()` 方法会运行，它的 `@Routify` 注解会注册路由，并且它的生命周期监听器和路由安全机制也会运行。此时 `routeClasses` 和 `routePackages` 不再生效。

测试只需从满足需求的最小状态开始。测试单个视图时，只注册该视图；测试应用如何启动或其路由安全机制如何决策时，则启动应用。`context.getApp()` 返回正在运行的应用，`context.getFrame()` 返回应用创建的第一个 frame。

自行设置上下文的测试会调用 `WebforjTestbed.setup()`，并传入一个包含相同选项的 `WebforjTestbedOptions`，最后调用 `WebforjTestbed.tearDown()`。这样的类不带 `@WebforjTest`。这些选项还接受一个接收 `RouteRegistry` 的函数，用于注册注解无法表达的路由，例如将视图注册到不同于其注解中指定路径的位置。

## 导航路由 {#navigating-routes}

测试像用户一样访问视图：通过路由访问，或点击导航链接访问。下面的列表链接到两个产品，产品视图则显示路由指定的产品：

```java title="ProductListView.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.router.Router;
import com.webforj.router.annotation.Route;
import com.webforj.router.history.ParametersBag;
import java.util.List;

@Route("testbed/products")
public class ProductListView extends Composite<Div> {

  public ProductListView() {
    for (String id : List.of("41", "42")) {
      getBoundComponent()
          .add(
              new Button(
                  "Product " + id,
                  e ->
                      Router.getCurrent()
                          .navigate(
                              ProductView.class,
                              ParametersBag.of("id=" + id))));
    }
  }
}
```

```java title="ProductView.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.router.annotation.Route;
import com.webforj.router.event.DidEnterEvent;
import com.webforj.router.history.ParametersBag;
import com.webforj.router.observer.DidEnterObserver;

@Route("testbed/products/:id")
public class ProductView extends Composite<Div> implements DidEnterObserver {
  private final Paragraph title = new Paragraph();

  public ProductView() {
    getBoundComponent().add(title);
  }

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    title.setText("Product " + parameters.get("id").orElse(""));
  }
}
```

测试会注册这两个视图。第一个测试点击列表中的某个产品，第二个测试则直接导航到某个产品：

```java title="ProductListViewTest.java" showLineNumbers
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.router.history.ParametersBag;
import com.webforj.samples.views.testbed.ProductListView;
import com.webforj.samples.views.testbed.ProductView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest(routeClasses = {ProductListView.class, ProductView.class})
class ProductListViewTest {
  @Test
  void shouldOpenTheProductTheUserClicks(WebforjTestbed context) {
    context.navigate(ProductListView.class);

    UserInteraction.click(
        ComponentLocator.of(Button.class).byText("Product 42").locate());

    assertTrue(
        ComponentLocator.of(Paragraph.class).byText("Product 42").isPresent());
  }

  @Test
  void shouldShowTheProductOfTheRoute(WebforjTestbed context) {
    var product = context.navigate(
        ProductView.class, ParametersBag.of("id=42"));

    assertTrue(
        ComponentLocator.of(Paragraph.class)
            .within(product)
            .byText("Product 42")
            .isPresent());
    assertEquals(
        "/testbed/products/42",
        context.getRouter()
            .orElseThrow()
            .getHistory()
            .getLocation()
            .orElseThrow()
            .getFullURI());
  }
}
```

`context.navigate(view)` 会导航到视图对应的路由，并返回渲染出的视图。`context.navigate(view, parameters)` 会填入路由参数，因此 `id=42` 会在 `/testbed/products/42` 渲染对应的产品。`context.navigate(location)` 接受路径，例如 `"testbed/products/42"`，并返回该路径下渲染的内容。

导航会像运行中的应用一样执行[路由生命周期观察器](/docs/routing/navigation-lifecycle/observers)，因此 `navigate` 返回之前，产品视图的 `onDidEnter` 已经运行。若要使用路由器提供的其他功能，例如替换历史记录项的导航，请调用测试的路由器，它由 `context.getRouter()` 返回。其历史记录也包含当前位置。

## 测试受保护的路由 {#testing-secured-routes}

测试路由安全机制时，需要启动应用，按照应用读取用户信息的方式设置用户，然后执行导航。在使用[自定义安全实现](/docs/security/architecture/custom-implementation)的应用中，测试会通过应用自身安全管理器的登录方法登录。

:::info 路由安全机制需要应用
使用 `@WebforjTest(app = ...)` 启动应用时，路由安全机制才会运行。使用 `routeClasses` 或 `routePackages` 时，视图会在没有路由安全机制的情况下渲染，因此测试可以独立检查受保护的视图，而无需登录。
:::

每个测试都会使用新的会话，因此未登录用户的测试会以匿名访客身份导航。

:::tip Spring Boot 应用
在使用 Spring Security 的 Spring Boot 应用中，测试通过 `@WithMockUser` 设置用户，详情请参阅 [Spring 测试](/docs/integrations/spring/testing#testing-secured-routes)。
:::

### 检查重定向 {#checking-redirects}

路由安全机制拒绝访问时，会重定向到应用配置的认证位置或拒绝访问位置。`navigate(String)` 会返回在那里渲染的组件，因此测试可以通过该组件的类型检查重定向。

`navigate(Class)` 会要求返回指定视图。如果路由安全机制改为渲染了其他视图，它会抛出 `IllegalStateException`，指出这两个视图，并提示使用 `navigate(String)`。

如果访问被拒绝，且应用未配置认证位置或拒绝访问位置，则不会渲染任何内容。此时两种 `navigate` 方法都会失败，错误消息会指出路由并说明导航已被否决。路由生命周期观察器否决导航时也会发生同样的错误。

## 回答对话框 {#answering-dialogs}

[选项对话框](/docs/components/option-dialogs/overview)会暂停显示它的代码，直到用户作出回答。测试中没有用户来回答，因此测试需要在代码显示对话框之前提供答案。下面的视图会在删除报告前请求用户确认：

```java title="DeleteReport.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.optiondialog.ConfirmDialog;
import com.webforj.component.optiondialog.OptionDialog;

public class DeleteReport extends Composite<Div> {

  public DeleteReport() {
    Paragraph status = new Paragraph("report.txt");
    Button delete =
        new Button(
            "Delete",
            e -> {
              ConfirmDialog.Result answer =
                  OptionDialog.showConfirmDialog(
                      "Delete report.txt?",
                      "Delete",
                      ConfirmDialog.OptionType.OK_CANCEL);
              if (answer == ConfirmDialog.Result.OK) {
                status.setText("Deleted");
              }
            });

    getBoundComponent().add(status, delete);
  }
}
```

第一个测试在点击 **Delete** 之前回答 **OK**，第二个测试则回答 **Cancel**：

```java title="DeleteReportTest.java" showLineNumbers
@Test
void shouldDeleteTheReportOnceTheUserConfirms(WebforjTestbed context) {
  context.add(new DeleteReport());

  context.answerConfirmDialog(ConfirmDialog.Result.OK);
  UserInteraction.click(
      ComponentLocator.of(Button.class).byText("Delete").locate());

  assertTrue(
      ComponentLocator.of(Paragraph.class).byText("Deleted").isPresent());

  var dialog = context.getShownOptionDialogs().get(0);
  assertEquals("Delete", dialog.getTitle());
  assertEquals("Delete report.txt?", dialog.getMessage());
}

@Test
void shouldKeepTheReportWhenTheUserCancels(WebforjTestbed context) {
  context.add(new DeleteReport());

  context.answerConfirmDialog(ConfirmDialog.Result.CANCEL);
  UserInteraction.click(
      ComponentLocator.of(Button.class).byText("Delete").locate());

  assertTrue(
      ComponentLocator.of(Paragraph.class).byText("report.txt").isPresent());
}
```

`context.getShownOptionDialogs()` 会按顺序返回应用在测试期间显示的所有对话框，以及各自的标题和消息，因此第一个测试也会检查对话框询问的内容。

每种对话框都有对应的回答方法。下面的视图通过输入对话框请求新名称：

```java title="RenameReport.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.optiondialog.OptionDialog;

public class RenameReport extends Composite<Div> {

  public RenameReport() {
    Paragraph name = new Paragraph("report.txt");
    Button rename =
        new Button(
            "Rename",
            e -> {
              String answer = OptionDialog.showInputDialog(
                  "New name", "Rename", name.getText());
              if (answer != null) {
                name.setText(answer);
              }
            });

    getBoundComponent().add(name, rename);
  }
}
```

测试会提供用户可能输入的文本作为答案：

```java title="RenameReportTest.java" showLineNumbers
@Test
void shouldRenameTheReportToTheNameTheUserEnters(WebforjTestbed context) {
  context.add(new RenameReport());

  context.answerInputDialog("summary.txt");
  UserInteraction.click(
      ComponentLocator.of(Button.class).byText("Rename").locate());

  assertTrue(
      ComponentLocator.of(Paragraph.class).byText("summary.txt").isPresent());
}
```

`answerConfirmDialog` 接受用户按下的按钮，`answerInputDialog` 接受用户输入的文本。文件对话框则通过 `answerFileChooserDialog`、`answerFileSaveDialog` 和 `answerFileUploadDialog` 接受用户选择的路径或文件名。向输入或文件对话框传入 `null`，效果与用户选择 **Cancel** 相同。消息对话框不需要答案，因为用户只能将其关闭。

测试提供的答案会按顺序对应相应类型的对话框，因此连续请求两次的视图会在第二个对话框中收到第二个答案。显示对话框时如果没有可用答案，测试会失败并抛出 `IllegalStateException`，指出对话框以及需要调用的回答方法。

## 计时器 {#timers}

测试不会等待时间流逝，而是通过 `context.runIntervals()` 决定时间何时流逝。下面的视图每五秒刷新一次：

```java title="RefreshCounter.java" showLineNumbers
import com.webforj.Interval;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class RefreshCounter extends Composite<Div> {
  private int refreshes = 0;

  public RefreshCounter() {
    Paragraph status = new Paragraph("Refreshed 0 times");
    Interval interval =
        new Interval(
            5f, e -> status.setText("Refreshed " + (++refreshes) + " times"));
    interval.start();

    getBoundComponent().add(status);
  }
}
```

每次调用 `runIntervals()`，都会模拟所有正在运行的[间隔计时器](/docs/advanced/interval)各自经过一次延迟，并返回触发的计时器数量：

```java title="RefreshCounterTest.java" showLineNumbers
@Test
void shouldRefreshEveryTimeTheIntervalElapses(WebforjTestbed context) {
  context.add(new RefreshCounter());

  assertEquals(1, context.runIntervals());
  assertEquals(1, context.runIntervals());

  assertTrue(
      ComponentLocator.of(Paragraph.class)
          .byText("Refreshed 2 times")
          .isPresent());
}
```

[防抖器](/docs/advanced/debouncing)也以同样的方式等待。下面的搜索框会等到用户停止输入后才执行搜索：

```java title="SearchBox.java" showLineNumbers
import com.webforj.Debouncer;
import com.webforj.component.Composite;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class SearchBox extends Composite<Div> {

  public SearchBox() {
    TextField search = new TextField("Search");
    Paragraph results = new Paragraph();
    Debouncer debouncer = new Debouncer(0.3f);
    search.onModify(
        e -> debouncer.run(
            () -> results.setText("Results for " + search.getValue())));

    getBoundComponent().add(search, results);
  }
}
```

测试会输入两次。在调用 `runIntervals()` 之前不会执行搜索；调用之后，防抖器只会运行一次，并使用最后一次输入：

```java title="SearchBoxTest.java" showLineNumbers
@Test
void shouldSearchOnlyForTheLastInput(WebforjTestbed context) {
  context.add(new SearchBox());

  var search = ComponentLocator.of(TextField.class).byLabel("Search").locate();
  var results = ComponentLocator.of(Paragraph.class).locate();

  UserInteraction.type(search, "jav");
  UserInteraction.type(search, "java");

  assertEquals("", results.getText());

  context.runIntervals();

  assertEquals("Results for java", results.getText());
}
```

[`Environment.runLater`](/docs/advanced/asynchronous-updates) 无需额外调用。无论是从测试还是由测试驱动的代码中调用，它都会在返回前运行任务，其 `PendingResult` 也已完成。

## 回答浏览器 {#answering-the-browser}

有些代码会向浏览器请求结果，例如窗口宽度。测试中没有浏览器，因此测试需要代替浏览器提供答案。下面的视图会显示浏览器报告的宽度：

```java title="WindowWidth.java" showLineNumbers
import com.webforj.Page;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class WindowWidth extends Composite<Div> {

  public WindowWidth() {
    Paragraph width = new Paragraph();
    Button measure =
        new Button(
            "Measure",
            e -> width.setText(
                "Width: " + Page.getCurrent().executeJs("window.innerWidth")));

    getBoundComponent().add(measure, width);
  }
}
```

`answerExecuteJs` 会提供下一次 `executeJs` 调用的结果，因此测试需要在点击前提供答案：

```java title="WindowWidthTest.java" showLineNumbers
@Test
void shouldShowTheWidthTheBrowserReports(WebforjTestbed context) {
  context.add(new WindowWidth());

  context.answerExecuteJs(1280);
  UserInteraction.click(
      ComponentLocator.of(Button.class).byText("Measure").locate());

  assertTrue(
      ComponentLocator.of(Paragraph.class).byText("Width: 1280").isPresent());
}
```

如果测试没有提供结果，`executeJs` 会返回 `null`。不等待浏览器的代码会使用 `executeJsAsync`，它会在之后完成。下面的按钮会复制页面链接，并在浏览器操作完成后显示确认信息：

```java title="CopyLink.java" showLineNumbers
import com.webforj.Page;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class CopyLink extends Composite<Div> {

  public CopyLink() {
    Paragraph status = new Paragraph();
    Button copy =
        new Button(
            "Copy link",
            e ->
                Page.getCurrent()
                    .executeJsAsync(
                        "navigator.clipboard.writeText(location.href)")
                    .thenAccept(result -> status.setText("Link copied")));

    getBoundComponent().add(copy, status);
  }
}
```

脚本会在点击后启动，因此测试需要在点击后通过 `answerExecuteJsAsync` 提供答案；该方法会完成最早一个仍在等待的脚本：

```java title="CopyLinkTest.java" showLineNumbers
@Test
void shouldConfirmOnceTheBrowserCopiedTheLink(WebforjTestbed context) {
  context.add(new CopyLink());

  UserInteraction.click(
      ComponentLocator.of(Button.class).byText("Copy link").locate());
  context.answerExecuteJsAsync(null);

  assertTrue(
      ComponentLocator.of(Paragraph.class).byText("Link copied").isPresent());
}
```

没有脚本在等待结果时，`answerExecuteJsAsync` 会抛出 `IllegalStateException`。`Element` 会等待浏览器定义它，因此 `whenDefined()` 及其排入队列的调用（例如 `callJsFunctionAsync`）会一直等待，直到测试使用 `UserInteraction.fireEvent` 为该元素触发 `ElementDefinedEvent`。

<JavadocLink type="testbed-foundation" location="com/webforj/testbed/WebforjTestbed" code="true">WebforjTestbed</JavadocLink> 参考文档列出了测试上下文的所有方法。
