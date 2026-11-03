---
sidebar_position: 4
title: 断言
description: >-
  Check what the user would see on a component with verifyThat, and read
  failures that name the component and both values.
_i18n_hash: 6414e2fab20d3bf7828413494af79b74
---
测试执行操作后，会检查用户实际看到的内容：按钮上的文本、字段是否可编辑，以及列表中显示为已选中的项目。`verifyThat(component)` 会用同样的词语描述这些预期，例如 `isReadOnly()` 或 `hasText("Save")`。每项检查都会读取组件的状态，也就是浏览器显示的状态；检查失败时，会指出组件以及预期值和实际值。

<!-- INTRO_END -->

## 检查组件 {#checking-a-component}

下面的编辑器会显示一个名称，在用户点击 **Edit** 之前无法更改。点击后，字段变为可编辑并获得焦点，按钮则变为 **Save**：

```java title="ProfileEditor.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;

public class ProfileEditor extends Composite<Div> {

  public ProfileEditor() {
    TextField name = new TextField("Name", "Ada");
    name.setReadOnly(true);
    Button edit = new Button("Edit");
    edit.onClick(
        e -> {
          name.setReadOnly(false).focus();
          edit.setText("Save");
        });

    getBoundComponent().add(name, edit);
  }
}
```

测试会在点击前后检查字段：

```java title="ProfileEditorTest.java" showLineNumbers
@Test
void shouldMakeTheNameEditableWhenTheUserClicksEdit(WebforjTestbed context) {
  context.add(new ProfileEditor());

  var name = ComponentLocator.of(TextField.class).byLabel("Name").locate();
  var edit = ComponentLocator.of(Button.class).byText("Edit").locate();

  verifyThat(name).hasValue("Ada").isReadOnly();

  UserInteraction.click(edit);

  verifyThat(name).isEditable().isFocused();
  verifyThat(edit).hasText("Save");
}
```

`verifyThat` 来自 `com.webforj.testbed.assertion.WebforjAssertions`，并通过静态导入引入。每项检查都会返回断言对象，因此可以在一条语句中链式检查同一个组件。检查会按顺序执行，第一项失败时测试就会结束。

每种状态都有对应的正反两种检查，例如 `isReadOnly()` 和 `isEditable()`，或 `isVisible()` 和 `isHidden()`，因此测试可以直接说明预期状态。

## 检查读取的内容 {#what-a-check-reads}

检查通过[关注点接口](/docs/building-ui/component-fundamentals#concern-interfaces)读取组件，因此适用于实现了该接口的所有组件，包括应用中的组件。`hasLabel` 适用于所有带标签的组件，`isReadOnly` 则适用于所有可设为只读的组件。检查涵盖以下内容：

- 用户读取的内容：文本、HTML 内容、标签、占位符、辅助文本和工具提示。
- 组件的状态：可见、已启用、只读、必填、有效和已聚焦。
- 字段的值，以及列表、表格和树的项目与选中状态。
- 应用设置的内容：名称、属性、特性、样式、主题和扩展。
- 容器包含的组件，以及组件是否已附加或已销毁。

<JavadocLink type="testbed-foundation" location="com/webforj/testbed/assertion/AbstractComponentAssert" code="true">AbstractComponentAssert</JavadocLink> 参考文档列出了每项检查及其读取的接口。

## 值 {#values}

下面的字段会根据用户输入的数量计算总数：

```java title="OrderQuantity.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.field.NumberField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class OrderQuantity extends Composite<Div> {

  public OrderQuantity() {
    NumberField quantity = new NumberField("Quantity");
    Paragraph total = new Paragraph("Total: 0");
    quantity.onValueChange(
        e -> total.setText("Total: " + (int) (e.getValue() * 5)));

    getBoundComponent().add(quantity, total);
  }
}
```

测试会输入数量，然后检查字段的值和总数：

```java title="OrderQuantityTest.java" showLineNumbers
@Test
void shouldHoldTheTypedQuantityAsANumber(WebforjTestbed context) {
  context.add(new OrderQuantity());

  var quantity = ComponentLocator.of(NumberField.class)
      .byLabel("Quantity")
      .locate();

  UserInteraction.type(quantity, "3");

  verifyThat(quantity).hasValue(3.0);
  verifyThat(ComponentLocator.of(Paragraph.class).locate())
      .hasText("Total: 15");
}
```

`hasValue` 使用 `equals` 进行比较，因此预期值的类型必须与字段持有的类型相同。`NumberField` 持有 `Double`，所以测试应预期 `3.0`，而不是 `3`。

:::info 值的类型
`DateField` 持有 `LocalDate`，而 `TimeField` 持有 `LocalTime`。请使用相应类型的值作为预期值。
:::

## 子组件 {#child-components}

`hasComponentCount` 用于检查容器包含多少个组件，`hasComponent` 用于检查容器是否包含指定组件。下面的编辑器会将用户输入的每个标签添加到标签列表中：

```java title="TagEditor.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Span;

public class TagEditor extends Composite<Div> {

  public TagEditor() {
    TextField tag = new TextField("Tag");
    Div tags = new Div();
    tags.setName("tags");
    Button add = new Button("Add", e -> tags.add(new Span(tag.getValue())));

    getBoundComponent().add(tag, add, tags);
  }
}
```

测试会添加两个标签并检查列表：

```java title="TagEditorTest.java" showLineNumbers
@Test
void shouldAddEveryTagTheUserEnters(WebforjTestbed context) {
  context.add(new TagEditor());

  var tag = ComponentLocator.of(TextField.class).byLabel("Tag").locate();
  var add = ComponentLocator.of(Button.class).byText("Add").locate();
  var tags = ComponentLocator.of(Div.class).byName("tags").locate();

  UserInteraction.type(tag, "java");
  UserInteraction.click(add);
  UserInteraction.type(tag, "kotlin");
  UserInteraction.click(add);

  verifyThat(tags)
      .hasComponentCount(2)
      .hasComponent(ComponentLocator.of(Span.class).byText("kotlin").locate());
}
```

## 选择 {#selection}

选择相关检查会读取列表、表格或树中显示为已选中的项目，可以按项目、索引或键进行检查。下面的列表提供三种水果：

```java title="FruitPicker.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.list.ListBox;

public class FruitPicker extends Composite<Div> {

  public FruitPicker() {
    ListBox fruits = new ListBox("Fruit");
    fruits.add("apple", "Apple");
    fruits.add("pear", "Pear");
    fruits.add("plum", "Plum");

    getBoundComponent().add(fruits);
  }
}
```

测试会先检查没有任何项目被选中，然后选择一种水果，并按键和索引检查选择结果：

```java title="FruitPickerTest.java" showLineNumbers
@Test
void shouldSelectTheFruitTheUserPicked(WebforjTestbed context) {
  context.add(new FruitPicker());

  var fruits = ComponentLocator.of(ListBox.class).byLabel("Fruit").locate();

  verifyThat(fruits).hasNoSelection();

  UserInteraction.selectKey(fruits, "pear");

  verifyThat(fruits).hasSelectedKey("pear").hasSelectedIndex(1);
}
```

对于允许多选的组件，只要预期项目在已选项目中，`hasSelected`、`hasSelectedIndex` 和 `hasSelectedKey` 就会通过。`hasItemCount` 用于检查带有数据仓库的组件（例如 `Table`）中的项目数量。

## 生命周期 {#lifecycle}

`isAttached()` 用于检查组件是否位于页面上，`isDestroyed()` 用于检查应用是否已销毁组件。下面的横幅会在用户将其关闭时销毁自身：

```java title="WelcomeBanner.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class WelcomeBanner extends Composite<Div> {

  public WelcomeBanner() {
    getBoundComponent()
        .add(
            new Paragraph("Welcome back"),
            new Button("Dismiss", e -> destroy()));
  }
}
```

测试会关闭横幅并检查它：

```java title="WelcomeBannerTest.java" showLineNumbers
@Test
void shouldRemoveTheBannerWhenTheUserDismissesIt(WebforjTestbed context) {
  context.add(new WelcomeBanner());

  var banner = ComponentLocator.of(WelcomeBanner.class).locate();

  verifyThat(banner).isAttached();

  UserInteraction.click(
      ComponentLocator.of(Button.class).byText("Dismiss").locate());

  verifyThat(banner).isDestroyed();
}
```

## 失败消息 {#failure-messages}

检查失败时会抛出 `AssertionError`，其中会指出组件类型、预期值和实际值。下面的测试会故意让检查失败，然后读取错误消息：

```java title="ProfileEditorTest.java" showLineNumbers
@Test
void shouldNameTheComponentAndBothValuesOnFailure(WebforjTestbed context) {
  context.add(new ProfileEditor());

  var name = ComponentLocator.of(TextField.class).byLabel("Name").locate();

  var failure = assertThrows(
      AssertionError.class, () -> verifyThat(name).hasValue("Grace"));

  assertEquals(
      "Expected TextField to have value <Grace> but it has <Ada>.",
      failure.getMessage());
}
```

如果组件没有公开相应状态，对该组件进行检查会失败，并在消息中指出缺少的接口。`Div` 不持有值，因此对 `Div` 调用 `hasValue` 会失败，并指出 `ValueAware`。

## 为自己的组件编写检查 {#checks-for-your-own-components}

应用中的组件只要实现了关注点接口，就无需额外代码即可使用相应检查。对于只有组件自身才知道的状态，可以编写断言类。下面的评分组件会统计用户点击的星星数量，并通过 `getStars()` 返回该数量：

```java title="StarRating.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Div;

public class StarRating extends Composite<Div> {
  private int stars = 0;

  public StarRating() {
    for (int i = 1; i <= 5; i++) {
      int value = i;
      getBoundComponent()
          .add(new Button(String.valueOf(i), e -> stars = value));
    }
  }

  public int getStars() {
    return stars;
  }
}
```

断言类扩展 `AbstractComponentAssert`，并添加一项用于检查星星数量的检查。静态方法 `verifyThat` 会创建该断言类，因此测试可以像调用内置检查一样调用它：

```java title="StarRatingAssert.java" showLineNumbers
import com.webforj.samples.views.testbed.StarRating;
import com.webforj.testbed.assertion.AbstractComponentAssert;

public final class StarRatingAssert
    extends AbstractComponentAssert<StarRatingAssert, StarRating> {

  private StarRatingAssert(StarRating actual) {
    super(actual, StarRatingAssert.class);
  }

  public static StarRatingAssert verifyThat(StarRating actual) {
    return new StarRatingAssert(actual);
  }

  public StarRatingAssert hasStars(int stars) {
    isNotNull();
    if (actual.getStars() != stars) {
      failWithMessage(
          "Expected %s to have %s stars but it has %s.",
          componentName(),
          stars,
          actual.getStars());
    }

    return myself;
  }
}
```

在检查方法中，`actual` 是组件，`failWithMessage` 会使用格式化消息使检查失败，`componentName()` 会指出组件名称，而返回 `myself` 则允许链式调用检查。新类是添加检查，并不会替换任何检查。测试会静态导入两个 `verifyThat` 方法，Java 会根据参数选择匹配的方法：`StarRating` 会使用 `StarRatingAssert`，其他组件则使用 `WebforjAssertions`。评分组件仍然可以使用内置检查，例如 `isAttached()`：

```java title="StarRatingTest.java" showLineNumbers
import static com.webforj.samples.unit.views.testbed.StarRatingAssert.verifyThat;
import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.samples.views.testbed.StarRating;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class StarRatingTest {
  @Test
  void shouldHoldTheStarsTheUserClicked(WebforjTestbed context) {
    context.add(new StarRating());

    var rating = ComponentLocator.of(StarRating.class).locate();

    UserInteraction.click(
        ComponentLocator.of(Button.class).byText("4").locate());

    verifyThat(rating).hasStars(4).isAttached();
  }

  @Test
  void shouldCheckTheRatingAndTheButtonInOneTest(WebforjTestbed context) {
    context.add(new StarRating());

    var rating = ComponentLocator.of(StarRating.class).locate();
    var four = ComponentLocator.of(Button.class).byText("4").locate();

    UserInteraction.click(four);

    verifyThat(rating).hasStars(4);
    verifyThat(four).hasText("4").isEnabled();
  }
}
```
