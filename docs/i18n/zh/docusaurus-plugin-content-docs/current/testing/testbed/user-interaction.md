---
sidebar_position: 3
title: 用户交互
description: >-
  Act on the components of a TestBed test through the same gestures a user
  makes, and get the same events a user causes.
_i18n_hash: 1abb4d1f79dc5eb1556e58df714dfac7
---
你的代码可以对任何组件调用 `setValue` 或 `setEnabled`，但用户不行。用户会在看得见的字段中输入内容，并点击已启用的按钮。TestBed 测试则通过 `UserInteraction`，以用户的方式执行操作。它的每个动词都会检查用户是否能够做出该操作，然后执行操作，并让组件像用户在浏览器中操作时那样，将操作报告给监听器。

每个动词都会在测试运行下一行之前完成，因此测试会在操作后立即检查结果。

<!-- INTRO_END -->

## 像用户一样操作 {#acting-like-a-user}

下面的表单用于订阅邮件。只有在字段中输入了邮件地址后，**Subscribe** 按钮才会启用：

```java title="NewsletterForm.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class NewsletterForm extends Composite<Div> {

  public NewsletterForm() {
    TextField email = new TextField("Email");
    Paragraph status = new Paragraph();
    Button subscribe =
        new Button(
            "Subscribe", e -> status.setText("Subscribed " + email.getValue()));
    subscribe.setEnabled(false);
    email.onModify(e -> subscribe.setEnabled(!email.getValue().isBlank()));

    getBoundComponent().add(email, subscribe, status);
  }
}
```

测试输入邮件地址并点击 **Subscribe**，模拟用户做出的两个操作：

```java title="NewsletterFormTest.java" showLineNumbers
@Test
void shouldSubscribeTheEmailTheUserTyped(WebforjTestbed context) {
  context.add(new NewsletterForm());

  var email = ComponentLocator.of(TextField.class).byLabel("Email").locate();
  var subscribe = ComponentLocator.of(Button.class)
      .byText("Subscribe")
      .locate();

  UserInteraction.type(email, "ada@example.com");
  UserInteraction.click(subscribe);

  assertTrue(
      ComponentLocator.of(Paragraph.class)
          .byText("Subscribed ada@example.com")
          .isPresent());
}
```

输入操作会将更改报告给字段的修改监听器，监听器随即启用按钮。随后发生的点击会传递给按钮的监听器，显示确认信息。

## 动词 {#verbs}

`UserInteraction` 为许多组件共有的每种操作提供了一个动词：

- `click` 和 `doubleClick` 按下组件，或组件中的某个项目，例如表格中的一行或树中的一个节点。
- `type` 替换字段中的文本，`keypress` 在字段中按下按键，`focus` 将焦点移到组件上。
- `check` 和 `uncheck` 设置复选框、单选按钮或开关。
- `select`、`selectIndex`、`selectKey` 和 `deselect` 更改列表、表格、树和选项卡面板中的选中项。
- `open`、`close` 和 `cancel` 打开或关闭面板、对话框、抽屉和下拉菜单，并取消登录或上传操作。

动词会按与用户操作相同的顺序报告操作。例如，选择一个选项卡时，会先报告上一个选项卡失去选中状态，再报告新选项卡被选中。操作的鼠标按键、位置和修饰键通过 `MouseOptions` 和 `KeyOptions` 传入，它们是 `click`、`doubleClick` 和 `keypress` 的最后一个参数。

<JavadocLink type="automation" location="com/webforj/automation/act/UserInteraction" code="true">UserInteraction</JavadocLink> 参考列出了响应每个动词的组件。

## 点击会传递给周围的组件 {#clicks-reach-the-components-around}

在浏览器中，点击会传递给被点击的组件，然后传递给它周围的每个组件；动词也会如此。在下面的卡片中，只有卡片会监听点击，里面的头像不会：

```java title="MemberCard.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.avatar.Avatar;
import com.webforj.component.card.Card;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class MemberCard extends Composite<Div> {

  public MemberCard() {
    Paragraph profile = new Paragraph();
    Card card = new Card(new Avatar("Ada Lovelace"));
    card.onClick(e -> profile.setText("Profile of Ada Lovelace"));

    getBoundComponent().add(card, profile);
  }
}
```

测试点击头像，也就是用户会点击的位置：

```java title="MemberCardTest.java" showLineNumbers
@Test
void shouldOpenTheProfileWhenTheUserClicksTheAvatar(WebforjTestbed context) {
  context.add(new MemberCard());

  UserInteraction.click(ComponentLocator.of(Avatar.class).locate());

  assertTrue(
      ComponentLocator.of(Paragraph.class)
          .byText("Profile of Ada Lovelace")
          .isPresent());
}
```

点击会传递给包围头像的卡片的监听器，显示个人资料。

## 用户无法执行的操作 {#what-a-user-cant-do}

只有用户能够执行某个操作时，动词才会运行。在输入邮件地址之前，[邮件订阅表单](#acting-like-a-user)中的 **Subscribe** 按钮处于禁用状态，因此测试也无法点击它：

```java title="NewsletterFormTest.java" showLineNumbers
@Test
void shouldRejectTheClickWhileNoEmailIsTyped(WebforjTestbed context) {
  context.add(new NewsletterForm());

  var subscribe = ComponentLocator.of(Button.class)
      .byText("Subscribe")
      .locate();

  var failure =
      assertThrows(
          InteractionRejectedException.class,
          () -> UserInteraction.click(subscribe));

  assertEquals(
      """
      The component Button is disabled and a user cannot interact with it.
      - Frame:
        - NewsletterForm [composite=Div] [node=div]:
          - TextField [label=Email]
          - Button "Subscribe" [disabled]
          - Paragraph [composite=Element] [node=p]
      """,
      failure.getMessage());
  assertFalse(
      ComponentLocator.of(Paragraph.class)
          .byTextContaining("Subscribed")
          .isPresent());
}
```

点击操作会抛出 `InteractionRejectedException`，按钮的监听器不会运行。测试会检查异常消息：消息会指出组件和原因，随后列出组件树，这样测试失败时就能显示页面中有哪些内容。如果组件本身或周围的组件被禁用或隐藏，或者组件位于已关闭的对话框或抽屉中，操作就会被拒绝。向只读字段输入内容会被拒绝，组件不允许的操作也会被拒绝，例如关闭一个没有关闭按钮的警告框。

:::tip 尚未附加的组件
尚未附加的组件在执行任何动词时都会抛出 `IllegalStateException`，并提示你先将其添加到 context 中。
:::

## 针对单个组件的操作 {#gestures-of-one-component}

有些操作只适用于单个组件，例如拖动滑块、对表格排序或展开树节点。每个具有这类操作的 webforJ 组件都有一个交互类，而 `UserInteraction.perform` 会在执行与各动词相同的检查后，运行其中的一项操作。下面的控件显示滑块所设置的音量：

```java title="VolumeControl.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.slider.Slider;

public class VolumeControl extends Composite<Div> {

  public VolumeControl() {
    Paragraph volume = new Paragraph("Volume 50");
    Slider slider = new Slider(50, 0, 100);
    slider.onSlide(e -> volume.setText("Volume " + e.getValue()));

    getBoundComponent().add(slider, volume);
  }
}
```

测试将滑块拖到 80，然后尝试设置为 150，这个值超出了滑块的范围：

```java title="VolumeControlTest.java" showLineNumbers
@Test
void shouldShowTheVolumeTheUserSlidTo(WebforjTestbed context) {
  context.add(new VolumeControl());

  var slider = ComponentLocator.of(Slider.class).locate();

  UserInteraction.perform(slider, SliderInteraction.slide(80));

  assertTrue(
      ComponentLocator.of(Paragraph.class).byText("Volume 80").isPresent());

  var failure =
      assertThrows(
          InteractionRejectedException.class,
          () -> UserInteraction.perform(slider, SliderInteraction.slide(150)));

  assertEquals(
      """
      The component Slider ranges from 0 to 100 and a user cannot slide it to 150.
      - Frame:
        - VolumeControl [composite=Div] [node=div]:
          - Slider [min=0] [max=100]
          - Paragraph [composite=Element] [node=p]
      """,
      failure.getMessage());
}
```

拖到 80 会触发滑动监听器，显示新的音量。尝试拖到 150 会像动词操作一样被拒绝，因为用户无法将滑块拖过其边界。异常消息会说明滑块的范围，以及测试尝试设置的值。

每个具有专属操作的 webforJ 组件都有一个以组件命名的交互类，例如 `Slider` 对应的 `SliderInteraction`。该类的每个静态方法都表示一种操作。要查看组件支持的操作，请打开其交互类的 Javadoc，例如包含 `slide` 的 <JavadocLink type="automation" location="com/webforj/automation/act/component/slider/SliderInteraction" code="true">SliderInteraction</JavadocLink>、包含 `sort`、`moveColumn` 和 `resizeColumn` 的 <JavadocLink type="automation" location="com/webforj/automation/act/component/table/TableInteraction" code="true">TableInteraction</JavadocLink>，或包含 `expand` 和 `collapse` 的 <JavadocLink type="automation" location="com/webforj/automation/act/component/tree/TreeInteraction" code="true">TreeInteraction</JavadocLink>。<JavadocLink type="automation" location="index">webforj-automation 参考</JavadocLink> 按组件列出了所有交互类，每个组件对应 `com.webforj.automation.act.component` 下的一个包。

## 上传和下载 {#uploads-and-downloads}

用户会从自己的计算机上传文件，因此测试需要先将文件交给 context。下面的上传控件会列出收到的文件名：

```java title="PhotoUpload.java" showLineNumbers
import com.webforj.UploadedFile;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.upload.Upload;
import java.util.stream.Collectors;

public class PhotoUpload extends Composite<Div> {

  public PhotoUpload() {
    Paragraph received = new Paragraph();
    Upload upload = new Upload();
    upload.onUpload(
        e ->
            received.setText(
                e.getFiles().stream()
                    .map(UploadedFile::getClientName)
                    .collect(Collectors.joining(", "))));

    getBoundComponent().add(upload, received);
  }
}
```

测试通过 `pickFile` 选择两个文件，然后按文件名上传：

```java title="PhotoUploadTest.java" showLineNumbers
@Test
void shouldReceiveThePhotosTheUserUploaded(WebforjTestbed context) {
  context.add(new PhotoUpload());

  var upload = ComponentLocator.of(Upload.class).locate();

  context.pickFile("beach.jpg", "beach".getBytes(StandardCharsets.UTF_8));
  context.pickFile("sunset.png", "sunset".getBytes(StandardCharsets.UTF_8));
  UserInteraction.perform(
      upload, UploadInteraction.upload("beach.jpg", "sunset.png"));

  assertTrue(
      ComponentLocator.of(Paragraph.class)
          .byText("beach.jpg, sunset.png")
          .isPresent());
}
```

如果文件数量超过上限、文件大小超过上限，或文件类型不受上传控件支持，系统会将其报告给拒绝监听器，并且不会上传。

应用发送给用户的文件由 context 保存。下面的按钮会发送一份报告：

```java title="ReportDownload.java" showLineNumbers
import com.webforj.Page;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import java.nio.charset.StandardCharsets;

public class ReportDownload extends Composite<Div> {

  public ReportDownload() {
    Button download =
        new Button(
            "Download report",
            e ->
                Page.getCurrent()
                    .download(
                        "Orders: 42".getBytes(StandardCharsets.UTF_8),
                        "report.txt"));

    getBoundComponent().add(download);
  }
}
```

测试点击按钮，然后通过 `getDownloads()` 读取用户收到的文件：

```java title="ReportDownloadTest.java" showLineNumbers
@Test
void shouldSendTheReportWhenTheUserClicksDownload(WebforjTestbed context) {
  context.add(new ReportDownload());

  UserInteraction.click(
      ComponentLocator.of(Button.class).byText("Download report").locate());

  var report = context.getDownloads().get(0);
  assertEquals("report.txt", report.getName());
  assertEquals(
      "Orders: 42", new String(report.getContents(), StandardCharsets.UTF_8));
}
```

## 没有对应操作的事件 {#events-without-a-gesture}

有些事件会在某个操作之后到达组件，但它们来自浏览器而非用户，例如抽屉关闭完成的报告。下面的视图会显示帮助抽屉是否打开：

```java title="HelpDrawer.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.drawer.Drawer;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class HelpDrawer extends Composite<Div> {

  public HelpDrawer() {
    Paragraph status = new Paragraph("Help is open");
    Drawer drawer = new Drawer("Help");
    drawer.onClose(e -> status.setText("Help is closed"));
    drawer.open();

    getBoundComponent().add(drawer, status);
  }
}
```

`close` 会关闭抽屉，测试随后通过 `UserInteraction.fireEvent` 触发浏览器报告的关闭事件：

```java title="HelpDrawerTest.java" showLineNumbers
@Test
void shouldReportTheClosedHelp(WebforjTestbed context) {
  context.add(new HelpDrawer());

  var drawer = ComponentLocator.of(Drawer.class).locate();

  UserInteraction.close(drawer);
  UserInteraction.fireEvent(new DrawerCloseEvent(drawer, Map.of()));

  assertFalse(drawer.isOpened());
  assertTrue(
      ComponentLocator.of(Paragraph.class)
          .byText("Help is closed")
          .isPresent());
}
```

`fireEvent` 会将事件传递给组件的监听器，并且只检查组件是否已附加，因为事件并非来自用户。对话框、抽屉和应用布局中的抽屉的打开与关闭事件，以及上传操作的进度事件，都属于这类事件。`Element` 按名称监听事件，而 `UserInteraction.fireEvent(element, eventName, data)` 会将事件传递给该名称对应的监听器。

## 自定义操作 {#gestures-of-your-own}

`perform` 接受组件的任意 `Consumer`，因此测试可以定义自己的操作。下面的测试通过[邮件订阅表单](#acting-like-a-user)完成订阅，并使用一个在收到的表单中输入内容并点击按钮的操作：

```java title="NewsletterFormTest.java" showLineNumbers
@Test
void shouldSubscribeThroughTheGestureOfTheTest(WebforjTestbed context) {
  context.add(new NewsletterForm());

  var form = ComponentLocator.of(NewsletterForm.class).locate();

  UserInteraction.perform(form, subscribe("ada@example.com"));

  assertTrue(
      ComponentLocator.of(Paragraph.class)
          .byText("Subscribed ada@example.com")
          .isPresent());
}

private static Consumer<NewsletterForm> subscribe(String address) {
  return form -> {
    UserInteraction.type(
        ComponentLocator.of(TextField.class).within(form).locate(), address);
    UserInteraction.click(
        ComponentLocator.of(Button.class)
            .within(form)
            .byText("Subscribe")
            .locate());
  };
}
```

测试读起来就像用户执行的步骤，其他测试也可以复用相同的操作。

## 自定义组件 {#your-own-components}

应用中的组件会自动响应其实现的[关注点接口](/docs/building-ui/component-fundamentals#concern-interfaces)所对应的动词，无需额外代码。通过 `HasValue` 实现 `Boolean` 值的组件，会像复选框一样响应 `check`、`uncheck` 和 `click`。下面的开关用于表示某个项目是否为收藏项：

```java title="FavoriteToggle.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Span;
import com.webforj.concern.HasValue;
import com.webforj.data.event.ValueChangeEvent;
import com.webforj.dispatcher.EventDispatcher;
import com.webforj.dispatcher.EventListener;
import com.webforj.dispatcher.ListenerRegistration;

public class FavoriteToggle
    extends Composite<Span> implements HasValue<FavoriteToggle, Boolean> {
  private final EventDispatcher dispatcher = new EventDispatcher();
  private boolean favorite = false;

  public FavoriteToggle() {
    getBoundComponent().setText("Add to favorites");
  }

  @Override
  public Boolean getValue() {
    return favorite;
  }

  @Override
  public FavoriteToggle setValue(Boolean value) {
    favorite = value;
    getBoundComponent()
        .setText(favorite ? "Remove from favorites" : "Add to favorites");
    dispatcher.dispatchEvent(new ValueChangeEvent<>(this, value));

    return this;
  }

  @Override
  public ListenerRegistration<ValueChangeEvent<Boolean>> addValueChangeListener(
      EventListener<ValueChangeEvent<Boolean>> listener) {
    return dispatcher.addListener(ValueChangeEvent.class, listener);
  }
}
```

测试先选中开关，再点击它：

```java title="FavoriteToggleTest.java" showLineNumbers
@Test
void shouldToggleTheFavoriteLikeACheckbox(WebforjTestbed context) {
  context.add(new FavoriteToggle());

  var toggle = ComponentLocator.of(FavoriteToggle.class).locate();
  var label = ComponentLocator.of(Span.class).locate();

  UserInteraction.check(toggle);
  assertEquals("Remove from favorites", label.getText());

  UserInteraction.click(toggle);
  assertEquals("Add to favorites", label.getText());
}
```

同理，同时实现 `HasReadOnly` 和 `HasValue` 或 `HasText` 的组件会响应 `type`，实现 webforJ 选择接口的组件则会响应 `select`。

### 让组件支持这些动词 {#teaching-a-component-the-verbs}

如果没有合适的关注点接口，可以用交互类定义某个动词如何作用于你的组件。下面的展开器在展开后会显示详细信息：

```java title="Expander.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class Expander extends Composite<Div> {
  private final Paragraph details = new Paragraph("Ships in 2 days");

  public Expander() {
    details.setVisible(false);
    getBoundComponent().add(new Paragraph("Shipping"), details);
  }

  public void expand() {
    details.setVisible(true);
  }

  public void collapse() {
    details.setVisible(false);
  }

  public boolean isExpanded() {
    return details.isVisible();
  }
}
```

交互类会在构造函数中指定组件类，并为它支持的每个动词实现一个接口；此处实现的是 `OpenInteraction` 和 `CloseInteraction`。`reject` 会为组件不允许的操作创建异常：

```java title="ExpanderInteraction.java" showLineNumbers
import com.webforj.automation.act.AbstractComponentInteraction;
import com.webforj.automation.act.CloseInteraction;
import com.webforj.automation.act.OpenInteraction;
import com.webforj.samples.views.testbed.Expander;

public final class ExpanderInteraction
    extends AbstractComponentInteraction<Expander>
    implements OpenInteraction<Expander>, CloseInteraction<Expander> {

  public ExpanderInteraction() {
    super(Expander.class);
  }

  @Override
  public void open(Expander expander) {
    expander.expand();
  }

  @Override
  public void close(Expander expander) {
    if (!expander.isExpanded()) {
      throw reject(expander, "is already collapsed");
    }

    expander.collapse();
  }
}
```

在测试资源目录下的文件中，通过全限定名声明该类：

```text title="src/test/resources/META-INF/services/com.webforj.automation.act.ComponentInteraction"
com.webforj.samples.unit.views.testbed.ExpanderInteraction
```

随后，测试使用这些动词展开和折叠展开器。再次尝试折叠时会被拒绝，并给出该类指定的原因：

```java title="ExpanderTest.java" showLineNumbers
@Test
void shouldOpenAndCloseTheDetailsThroughTheVerbs(WebforjTestbed context) {
  context.add(new Expander());

  var expander = ComponentLocator.of(Expander.class).locate();
  var details = ComponentLocator.of(Paragraph.class)
      .byText("Ships in 2 days")
      .locate();

  UserInteraction.open(expander);
  verifyThat(details).isVisible();

  UserInteraction.close(expander);
  verifyThat(details).isHidden();

  var failure =
      assertThrows(
          InteractionRejectedException.class,
          () -> UserInteraction.close(expander));

  assertEquals(
      """
      The component Expander is already collapsed.
      - Frame:
        - Expander [composite=Div] [node=div]:
          - Paragraph [composite=Element] [node=p]
      """,
      failure.getMessage());
}
```

对于同一个组件，应用自定义的交互类会优先于 webforJ 提供的同类交互类来响应动词。如果应用中的两个交互类为同一个组件响应同一个动词，测试就会失败，并指出这两个交互类。若只想让交互类支持某个类的部分实例，可以重写 `isSupported(component)`。
