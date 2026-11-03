---
sidebar_position: 2
title: 查找组件
description: >-
  Describe the components a TestBed test acts on the way a user picks them out
  on the page, by type, text, label and what they contain.
_i18n_hash: 3e31c9bc7b51ebd2ebaf1a5e44c6e94f
---
用户点击按钮之前，会先在页面上找到它：**Save** 按钮，或者标有电子邮件地址的字段。TestBed 测试也以同样的方式查找组件，使用 `ComponentLocator`。定位器通过组件的类型以及用户在组件上看到的内容来描述组件，并在测试请求结果时搜索页面。

测试不会持有视图字段来访问组件。它像用户一样查找组件，因此只要页面显示的内容相同，即使视图的构建方式发生变化，测试仍然有效。

<!-- INTRO_END -->

## 定位组件 {#locating-a-component}

`ComponentLocator.of(type)` 从组件类型开始构建描述。`byText` 等筛选器可以进一步缩小范围，最后调用 `locate()` 等方法搜索页面并返回结果。

以下组合组件会统计用户点击 **Add** 按钮的次数：

```java title="ClickCounter.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class ClickCounter extends Composite<Div> {
  private int clicks = 0;

  public ClickCounter() {
    Paragraph count = new Paragraph("Clicked 0 times");
    Button add = new Button(
        "Add", e -> count.setText("Clicked " + (++clicks) + " times"));

    getBoundComponent().add(add, count);
  }
}
```

测试按文本查找按钮，点击两次，然后查找显示计数的段落：

```java title="ClickCounterTest.java" showLineNumbers
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.samples.views.testbed.ClickCounter;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ClickCounterTest {
  @Test
  void shouldCountEveryClick(WebforjTestbed context) {
    context.add(new ClickCounter());

    var button = ComponentLocator.of(Button.class).byText("Add").locate();

    UserInteraction.click(button);
    UserInteraction.click(button);

    assertTrue(
        ComponentLocator.of(Paragraph.class)
            .byText("Clicked 2 times")
            .isPresent());
  }
}
```

定位器会在执行最终方法时搜索页面，因此点击后执行的搜索会找到更新后的文本。

## 描述组件 {#describing-the-component}

描述从组件类型开始，再按用户辨认组件的顺序补充其特征：先是用户在组件上看到的内容，然后是组件在页面上的位置。每个筛选器都会保留符合条件的组件，而且筛选器可以串联使用，因此组件必须符合所有筛选条件。

- `byText`、`byTextContaining`、`byLabel` 和 `byPlaceholder` 匹配用户在组件上看到的内容。
- `byValue`、`byTheme`、`byExpanse`、`byClassName`、`byStyle` 和 `byAttribute` 匹配应用设置在组件上的内容。
- `byName` 匹配应用赋予不显示文本的组件的名称，例如只有图标的按钮。
- `has` 根据组件内部的另一个组件来匹配组件。
- `filter` 根据测试编写的任意条件进行匹配。

如果组件没有筛选器要读取的内容，该筛选器就会跳过它，因此 `byLabel` 遇到 `Div` 时会跳过，而不是导致搜索失败。

### 用户看到的内容 {#what-the-user-reads}

以下登录表单包含一个带标签的电子邮件字段、一个带占位符的密码字段，以及一个只显示图标的帮助按钮：

```java title="SignInForm.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.field.PasswordField;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.icons.TablerIcon;

public class SignInForm extends Composite<Div> {

  public SignInForm() {
    TextField email = new TextField("Email");
    PasswordField password = new PasswordField().setPlaceholder("Password");
    Paragraph hint = new Paragraph();
    Button help = new Button(
        TablerIcon.create("help"), e -> hint.setText("Use your work email"));
    help.setName("help");

    getBoundComponent().add(email, password, help, hint);
  }
}
```

测试分别按标签、占位符和名称查找这三个组件，填写字段并请求帮助：

```java title="SignInFormTest.java" showLineNumbers
@Test
void shouldShowTheHintWhenTheUserAsksForHelp(WebforjTestbed context) {
  context.add(new SignInForm());

  var email = ComponentLocator.of(TextField.class).byLabel("Email").locate();
  var password = ComponentLocator.of(PasswordField.class)
      .byPlaceholder("Password")
      .locate();
  var help = ComponentLocator.of(Button.class).byName("help").locate();

  UserInteraction.type(email, "ada@example.com");
  UserInteraction.type(password, "secret");
  UserInteraction.click(help);

  assertEquals("ada@example.com", email.getValue());
  assertTrue(
      ComponentLocator.of(Paragraph.class)
          .byText("Use your work email")
          .isPresent());
}
```

### 组件包含的内容 {#what-a-component-contains}

有些组件只能通过其内部内容来区分，例如显示不同名称的卡片。`has` 会在另一个定位器于组件内部找到匹配项时保留该组件。以下组合组件为每个联系人显示一张卡片，姓名作为标题，电子邮件作为说明文字，并带有一个 **Delete** 按钮：

```java title="ContactCards.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.card.Card;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Span;

public class ContactCards extends Composite<Div> {

  public ContactCards() {
    getBoundComponent()
        .add(
            card("Ada", "ada@example.com"), card("Linus", "linus@example.com"));
  }

  private Card card(String name, String email) {
    Card card = new Card();
    card.addToTitle(new Span(name))
        .addToCaption(new Span(email))
        .addToFooter(new Button("Delete", e -> card.destroy()));

    return card;
  }
}
```

测试通过卡片内的姓名查找 Linus 的卡片，然后检查该卡片上显示的电子邮件地址是否属于他：

```java title="ContactCardsTest.java" showLineNumbers
@Test
void shouldShowTheEmailOnTheCardOfTheContact(WebforjTestbed context) {
  context.add(new ContactCards());

  var linus =
      ComponentLocator.of(Card.class)
          .has(ComponentLocator.of(Span.class).byText("Linus"))
          .locate();

  assertTrue(
      ComponentLocator.of(Span.class)
          .within(linus)
          .byText("linus@example.com")
          .isPresent());
}
```

只会匹配卡片内部的组件，不会匹配卡片本身。

### 其他任意条件 {#any-other-condition}

`filter` 接受针对定位器所搜索类型的条件，因此可以读取该类型提供的任何内容。以下表单有三个字段，其中两个是必填字段：

```java title="ShippingForm.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;

public class ShippingForm extends Composite<Div> {

  public ShippingForm() {
    getBoundComponent()
        .add(
            new TextField("Name").setRequired(true),
            new TextField("Company"),
            new TextField("City").setRequired(true));
  }
}
```

测试使用 `filter` 查找必填字段，并读取它们的标签：

```java title="ShippingFormTest.java" showLineNumbers
@Test
void shouldMarkTheNameAndTheCityAsRequired(WebforjTestbed context) {
  context.add(new ShippingForm());

  var required = ComponentLocator.of(TextField.class)
      .filter(TextField::isRequired)
      .all();

  assertEquals(
      List.of("Name", "City"),
      required.stream().map(TextField::getLabel).toList());
}
```

<JavadocLink type="automation" location="com/webforj/automation/find/ComponentLocator" code="true">ComponentLocator</JavadocLink> 参考文档列出了所有筛选器以及它们读取的组件。

## 在组件内部搜索 {#searching-inside-a-component}

如果用户发现页面上有两个相同的按钮，就会查看目标按钮所在的页面区域。`within(component)` 也采用同样的方式，将搜索范围限制在该组件及其内部组件中。

[两张联系人卡片](#what-a-component-contains)都有一个 **Delete** 按钮，因此单独使用 `byText("Delete")` 会匹配两个按钮。以下测试只在第一张卡片中搜索：

```java title="ContactCardsTest.java" showLineNumbers
@Test
void shouldRemoveOnlyTheCardWhoseDeleteButtonTheUserClicks(
    WebforjTestbed context) {
  context.add(new ContactCards());

  var ada = ComponentLocator.of(Card.class).all().get(0);

  UserInteraction.click(
      ComponentLocator.of(Button.class).within(ada).byText("Delete").locate());

  assertEquals(1, ComponentLocator.of(Card.class).count());
  assertTrue(ComponentLocator.of(Span.class).byText("Linus").isPresent());
}
```

只有第一张卡片会被移除，Linus 的卡片仍然保留。

## 读取结果 {#reading-the-result}

最终方法决定测试会得到什么结果。`locate()` 返回唯一匹配的组件。`all()` 按页面上的顺序返回所有匹配项；`first()` 和 `last()` 分别返回第一个和最后一个匹配项；`count()` 返回匹配项的数量；`isPresent()` 则返回是否存在匹配项。

以下列表为每个项目显示一个复选框：

```java title="GroceryList.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.optioninput.CheckBox;
import java.util.List;

public class GroceryList extends Composite<Div> {

  public GroceryList() {
    for (String item : List.of("Milk", "Bread", "Eggs")) {
      getBoundComponent().add(new CheckBox(item));
    }
  }
}
```

测试使用 `all()` 读取所有项目，使用 `first()` 和 `last()` 获取第一个和最后一个项目，并使用 `count()` 获取项目数量：

```java title="GroceryListTest.java" showLineNumbers
@Test
void shouldListEveryItemInOrder(WebforjTestbed context) {
  context.add(new GroceryList());

  var items = ComponentLocator.of(CheckBox.class).all();

  assertEquals(
      List.of("Milk", "Bread", "Eggs"),
      items.stream().map(CheckBox::getText).toList());
  assertEquals(
      "Milk",
      ComponentLocator.of(CheckBox.class).first().orElseThrow().getText());
  assertEquals(
      "Eggs",
      ComponentLocator.of(CheckBox.class).last().orElseThrow().getText());
  assertEquals(3, ComponentLocator.of(CheckBox.class).count());
}
```

:::tip 已销毁的组件
应用销毁的组件不再位于页面上，因此无论使用哪种方法结束搜索，定位器都找不到它。
:::

## 描述不正确时 {#when-the-description-is-wrong}

`locate()` 要求恰好匹配一个组件，因此，如果描述没有匹配任何组件或匹配多个组件，就会失败，而不是随机选择一个。[两张联系人卡片](#what-a-component-contains)都有一个 **Delete** 按钮。以下测试只根据文本描述按钮，并读取错误信息：

```java title="ContactCardsTest.java" showLineNumbers
@Test
void shouldNameEveryMatchWhenTheDescriptionFitsTwoButtons(
    WebforjTestbed context) {
  context.add(new ContactCards());

  var delete = ComponentLocator.of(Button.class).byText("Delete");

  var failure = assertThrows(IllegalStateException.class, delete::locate);

  assertEquals(
      """
      Expected exactly one Button but found 2 in the tree:
      - Frame:
        - ContactCards [composite=Div] [node=div]:
          - Card [composite=Element] [node=dwc-card]:
            - Span [slot=title] [composite=Element] [node=span]
            - Span [slot=caption] [composite=Element] [node=span]
            - Button "Delete" [slot=footer]
          - Card [composite=Element] [node=dwc-card]:
            - Span [slot=title] [composite=Element] [node=span]
            - Span [slot=caption] [composite=Element] [node=span]
            - Button "Delete" [slot=footer]
      """,
      failure.getMessage());
}
```

错误信息会显示组件类型和匹配数量，后面跟着定位器搜索的组件树。树中显示了两个 **Delete** 按钮，每张卡片各有一个，因此解决方法是在某张卡片的[范围内搜索](#searching-inside-a-component)。

## 组合组件 {#composites}

定位器既可以按 `Composite` 自身的类型查找它，也可以按它绑定的组件类型查找它。以下组合组件是一个电子邮件字段。它绑定了一个 `TextField`，为其设置标签和占位符，并通过 `getAddress()` 返回输入的地址：

```java title="EmailField.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.field.TextField;

public class EmailField extends Composite<TextField> {

  public EmailField() {
    getBoundComponent().setLabel("Email").setPlaceholder("name@example.com");
  }

  public String getAddress() {
    return getBoundComponent().getValue();
  }
}
```

测试按组合组件自身的类型查找它，也按 `TextField` 类型及其标签查找它绑定的字段。测试在字段中输入文本，然后通过组合组件读取地址：

```java title="EmailFieldTest.java" showLineNumbers
@Test
void shouldReadTheAddressTheUserTypedIntoTheBoundField(WebforjTestbed context) {
  context.add(new EmailField());

  var email = ComponentLocator.of(EmailField.class).locate();
  var field = ComponentLocator.of(TextField.class).byLabel("Email").locate();

  UserInteraction.type(field, "ada@example.com");

  assertEquals("ada@example.com", email.getAddress());
}
```

当组合组件绑定另一个组合组件时，链中的每种类型都可以找到它。
