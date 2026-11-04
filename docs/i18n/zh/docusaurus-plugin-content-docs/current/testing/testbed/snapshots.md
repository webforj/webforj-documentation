---
sidebar_position: 5
title: 快照
description: >-
  Compare everything a view shows with a stored text description of it, read
  that description, and add the state of your own components to it.
_i18n_hash: 2f709890e8d1fd70a61debc1de3ae63f
---
逐项检查视图，需要为每个标签、按钮和字段编写一条断言。快照则可以一次检查所有内容。它会逐行记录用户在视图中看到的所有内容，每个组件占一行，并将描述与测试一同保存。之后每次运行都会重新描述视图并进行比较，因此页面显示内容的任何变化都会导致测试失败；变化内容会以纯文本差异的形式显示在代码审查中。

<!-- INTRO_END -->

## 匹配快照 {#matching-a-snapshot}

以下摘要显示一个订单号、一个备注字段、一条隐藏的折扣信息、一个带图标的 **Pay** 按钮，以及一个已禁用的 **Cancel** 按钮：

```java title="OrderSummary.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.icons.TablerIcon;

public class OrderSummary extends Composite<Div> {

  public OrderSummary() {
    Paragraph order = new Paragraph("Order 1042");
    TextField note = new TextField("Note")
        .setPlaceholder("Delivery instructions");
    Paragraph discount = new Paragraph("Discount applied");
    discount.setVisible(false);
    Button pay = new Button("Pay", ButtonTheme.PRIMARY);
    pay.setPrefixComponent(TablerIcon.create("credit-card"));
    Button cancel = new Button("Cancel");
    cancel.setEnabled(false);

    getBoundComponent().add(order, note, discount, pay, cancel);
  }
}
```

测试会添加该摘要，并使用 `matchesSnapshot()` 对其进行检查：

```java title="OrderSummaryTest.java" showLineNumbers
import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.samples.views.testbed.OrderSummary;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class OrderSummaryTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var summary = new OrderSummary();
    context.add(summary);

    verifyThat(summary).matchesSnapshot();
  }
}
```

第一次运行会将描述写入文件，并通过测试。之后每次运行，如果视图与保存的文件不再匹配，测试就会失败：

```snapshot title="OrderSummaryTest.shouldMatchTheSnapshot.yml" showLineNumbers
# webforJ testbed snapshot v1
- OrderSummary [composite=Div] [node=div]:
  - Paragraph "Order 1042" [composite=Element] [node=p]
  - TextField [label=Note] [placeholder="Delivery instructions"]
  - Paragraph "Discount applied" [composite=Element] [node=p] [hidden]
  - Button "Pay" [theme=primary]:
    - Icon [slot=prefix] [name=credit-card] [composite=Element] [node=dwc-icon]
  - Button "Cancel" [disabled]
```

`matchesSnapshot` 是 `verifyThat` 的一种检查方式，与 [Assertions](./assertions) 页面中介绍的其他检查一样，因此它适用于任何组件，而不仅限于视图。

:::tip 何时使用快照
快照会固定整个视图。对于内容不应变化的视图，可以使用快照；对于单个事实（例如点击后按钮上的文本），则使用[断言](./assertions)进行检查。
:::

## 阅读快照 {#reading-a-snapshot}

[保存的快照](#matching-a-snapshot)中每一行描述一个组件。该组件包含的子组件列在其下方，并缩进两个空格；包含其他组件的行以冒号结尾。每行按以下顺序读取：

1. 组件类型，例如 `Button`。
2. 组件显示的文本（如果有），用双引号括起，例如 `"Pay"`。
3. 属性，格式为 `[key=value]`，例如 `[theme=primary]` 或 `[label=Note]`。
4. 标记，格式为 `[key]`，例如 `[disabled]` 或 `[hidden]`。

每行只会打印与组件默认值不同的内容，因此摘要一目了然。**Cancel** 带有 `[disabled]`，而启用状态的 **Pay** 按钮不会显示任何状态信息。**Pay** 带有 `[theme=primary]`，而使用默认主题的按钮不会显示主题信息。尺寸、样式和类名都不会打印。

有些属性用于说明组件的位置：

- `slot` 表示组件填充其父组件的哪个部分。**Pay** 的图标位于文本之前，因此显示为 `[slot=prefix]`。
- `composite` 表示 `Composite` 绑定的组件，`node` 表示它渲染的元素。摘要绑定了一个 `Div`，因此显示为 `[composite=Div] [node=div]`。
- `route` 表示视图注册时所用的路由。
- `hidden` 标记隐藏的组件，例如折扣信息行。隐藏或显示组件都会改变快照。

文本始终用引号括起。属性值中如果包含字母、数字、`.`、`_`、`+`、`/` 或 `-` 以外的字符，也会加上引号，例如 `[placeholder="Delivery instructions"]`。

## 长列表 {#long-lists}

长列表会掩盖视图的其他内容，因此列表项、表格行和树节点在其父组件下最多打印十行。以下列表包含十二个国家/地区：

```java title="CountryList.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.list.ListBox;
import java.util.List;

public class CountryList extends Composite<Div> {

  public CountryList() {
    ListBox countries = new ListBox("Country");
    for (String country :
        List.of(
            "Austria", "Belgium", "Canada", "Denmark", "Egypt", "France",
            "Germany", "Hungary",
            "India", "Japan", "Kenya", "Mexico", "Hyyan")) {
      countries.add(country);
    }

    getBoundComponent().add(countries);
  }
}
```

```java title="CountryListTest.java" showLineNumbers
@Test
void shouldMatchTheSnapshot(WebforjTestbed context) {
  var list = new CountryList();
  context.add(list);

  verifyThat(list).matchesSnapshot();
}
```

保存的快照会打印前十个国家/地区，并用一行表示后续的三个国家/地区：

```snapshot title="CountryListTest.shouldMatchTheSnapshot.yml" showLineNumbers
# webforJ testbed snapshot v1
- CountryList [composite=Div] [node=div]:
  - ListBox [label=Country]:
    - ListItem "Austria" [key=Austria]
    - ListItem "Belgium" [key=Belgium]
    - ListItem "Canada" [key=Canada]
    - ListItem "Denmark" [key=Denmark]
    - ListItem "Egypt" [key=Egypt]
    - ListItem "France" [key=France]
    - ListItem "Germany" [key=Germany]
    - ListItem "Hungary" [key=Hungary]
    - ListItem "India" [key=India]
    - ListItem "Japan" [key=Japan]
    - … 3 more
```

若要保存更多项，请在[快照选项](#snapshot-options)中通过 `setMaxWidth` 传入数量限制。`matchesSnapshot(name, options)` 会严格按照接收到的选项进行渲染，因此测试还会启用已保存快照中的其他三个设置，并为快照指定名称：

```java title="CountryListTest.java" showLineNumbers
@Test
void shouldMatchTheSnapshotOfEveryCountry(WebforjTestbed context) {
  var list = new CountryList();
  context.add(list);

  verifyThat(list)
      .matchesSnapshot(
          "every-country",
          new SnapshotOptions()
              .setMaxWidth(20)
              .setBrowserState(true)
              .setHiddenComponents(true)
              .setComposites(true));
}
```

保存的快照会包含全部十二个国家/地区，并以快照名称命名文件：

```snapshot title="CountryListTest.every-country.yml" showLineNumbers
# webforJ testbed snapshot v1
- CountryList [composite=Div] [node=div]:
  - ListBox [label=Country]:
    - ListItem "Austria" [key=Austria]
    - ListItem "Belgium" [key=Belgium]
    - ListItem "Canada" [key=Canada]
    - ListItem "Denmark" [key=Denmark]
    - ListItem "Egypt" [key=Egypt]
    - ListItem "France" [key=France]
    - ListItem "Germany" [key=Germany]
    - ListItem "Hungary" [key=Hungary]
    - ListItem "India" [key=India]
    - ListItem "Japan" [key=Japan]
    - ListItem "Kenya" [key=Kenya]
    - ListItem "Mexico" [key=Mexico]
    - ListItem "Hyyan" [key=Hyyan]
```

:::tip 表格和树
表格不会统计其存储库中的项，因此结尾会显示单独的 `- …`，而不带数量。限制分别应用于每个父组件，因此树的每一层最多显示十个节点。
:::

## 快照的存储位置 {#where-snapshots-are-stored}

快照文件存放在 `src/test/resources/webforj-snapshots` 下，按照测试类所属包的目录结构组织。每个文件以测试类命名，后接快照名称和 `.yml`：

```text title="src/test/resources"
src/test/resources/webforj-snapshots/
  com/webforj/samples/unit/views/testbed/
    CountryListTest.shouldMatchTheSnapshot.yml
    OrderSummaryTest.shouldMatchTheSnapshot.yml
```

这些文件应与测试一起纳入版本控制。每个文件的第一行会标明格式版本。如果文件使用了其他版本，测试会失败并同时显示两个版本号，而不是显示差异。

## 为快照命名 {#naming-snapshots}

`matchesSnapshot()` 会使用正在运行的测试方法为快照命名，因此 `OrderSummaryTest` 中 `shouldMatchTheSnapshot` 的快照会保存为 `OrderSummaryTest.shouldMatchTheSnapshot.yml`。如果一个测试要检查多个快照，可以为每个快照调用 `matchesSnapshot(name)` 并传入名称；该名称会取代文件名中的方法名。名称可以包含字母、数字、空格、句点、连字符和 `_` 字符，其他字符都会转换为连字符，因此 `save/form:v2` 会保存为 `save-form-v2`。

`matchesSnapshot(name, options)` 还可以接收用于渲染的 [`SnapshotOptions`](#snapshot-options)，例如，用它来保存比默认数量更多的表格行。

## 快照不匹配时 {#when-a-snapshot-doesnt-match}

如果视图与保存的文件不再匹配，检查就会失败，并打印发生变化的内容。[订单摘要](#matching-a-snapshot)中的 **Pay** 按钮文本改为 **Pay now** 后，运行 `mvn test` 会报告：

```diff title="Terminal"
java.lang.AssertionError:
Snapshot <shouldMatchTheSnapshot> does not match src/test/resources/webforj-snapshots/…/OrderSummaryTest.shouldMatchTheSnapshot.yml.
First difference at line 5.
--- stored
+++ actual
@@ -3,5 +3,5 @@
   - TextField [label=Note] [placeholder="Delivery instructions"]
   - Paragraph "Discount applied" [composite=Element] [node=p] [hidden]
-  - Button "Pay" [theme=primary]:
+  - Button "Pay now" [theme=primary]:
     - Icon [slot=prefix] [name=credit-card] [composite=Element] [node=dwc-icon]
   - Button "Cancel" [disabled]
Actual:
- OrderSummary [composite=Div] [node=div]:
  - Paragraph "Order 1042" [composite=Element] [node=p]
  - TextField [label=Note] [placeholder="Delivery instructions"]
  - Paragraph "Discount applied" [composite=Element] [node=p] [hidden]
  - Button "Pay now" [theme=primary]:
    - Icon [slot=prefix] [name=credit-card] [composite=Element] [node=dwc-icon]
  - Button "Cancel" [disabled]
Run with -Dwebforj.testbed.updateSnapshots=true to update the snapshot, or verify more than one snapshot in the same test with explicit names.
```

## 更新快照 {#updating-snapshots}

如果视图发生了预期的变化，请将系统属性 `webforj.testbed.updateSnapshots` 设为 `true`，然后运行一次测试。此次运行检查的每个快照都会重新写入，不再被任何测试检查的快照文件也会被删除。提交之前，请审查这些文件的差异。

```bash title="Terminal"
mvn test -Dwebforj.testbed.updateSnapshots=true
```

## 过时的快照 {#obsolete-snapshots}

类中的所有测试运行完毕后，TestBed 会查找该类中没有任何测试检查的快照文件，例如重命名后的测试所对应的文件。如果存在此类文件，测试类就会失败，并列出这些文件，要求你将其删除，或者使用 `-Dwebforj.testbed.updateSnapshots=true` 运行，以便删除这些文件。

:::info 单个测试方法
只有类中的所有测试都运行后，才会执行此项检查。从 IDE 单独启动某个测试方法时，会跳过此检查。
:::

## 持续集成 {#continuous-integration}

快照会在本地写入并提交。当 `CI` 环境变量设为 `false` 或 `0` 以外的值时（大多数持续集成服务都是如此），TestBed 不会写入快照文件：

- 如果快照不存在，测试会失败并指出预期文件的名称，而不会写入该文件。
- 如果使用 `-Dwebforj.testbed.updateSnapshots=true` 运行，测试会失败并要求你在本地更新快照。

## 打印组件 {#printing-a-component}

`Snapshot.render(component)` 会将组件的描述作为字符串返回，而不会写入文件。编写测试时，可以打印该字符串以查看页面包含的内容。除非传入的选项包含这些内容，否则它不会显示浏览器持有的状态（例如用户输入的值）或隐藏的组件。

## 快照选项 {#snapshot-options}

`SnapshotOptions` 决定描述中包含多少内容。`setMaxWidth` 设置每个父组件下打印的项数，默认值为十。`setBrowserState` 会加入浏览器持有的状态，例如用户输入的值和焦点。`setHiddenComponents` 会加入带有 `hidden` 标记的隐藏组件，`setComposites` 则会加入 `composite` 和 `node`。

保存的快照包含所有内容：浏览器状态、隐藏组件和组合组件。`Snapshot.render` 和 `new SnapshotOptions()` 默认都不包含这些内容。`matchesSnapshot(name, options)` 会严格按照接收到的选项进行渲染，因此要保存更多项，需要设置更大的 `maxWidth`，并启用其他三个设置。<JavadocLink type="automation" location="com/webforj/automation/see/SnapshotOptions" code="true">SnapshotOptions</JavadocLink> 参考文档介绍了每项设置。

## 为自定义组件生成快照 {#snapshots-for-your-own-components}

应用中的组件无需额外代码，就会打印其类型、文本以及所实现的每个[关注点接口](/docs/building-ui/component-fundamentals#concern-interfaces)的状态，例如标签或主题。只有组件自身才知道的状态需要使用 `SnapshotSerializer`。以下评分组件会统计用户点击的星星，并通过 `getStars()` 返回数量：

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

序列化器会在 `getContract()` 中指出所描述的组件类，并在 `describe` 中将星星数量添加到组件所在行：

```java title="StarRatingSerializer.java" showLineNumbers
import com.webforj.automation.see.SnapshotNode;
import com.webforj.automation.see.SnapshotSerializer;
import com.webforj.samples.views.testbed.StarRating;

public final class StarRatingSerializer
    implements SnapshotSerializer<StarRating> {

  @Override
  public Class<StarRating> getContract() {
    return StarRating.class;
  }

  @Override
  public void describe(StarRating rating, SnapshotNode node) {
    node.setAttribute("stars", rating.getStars());
  }
}
```

在测试资源目录下的文件中，使用序列化器的完全限定名称列出它：

```text title="src/test/resources/META-INF/services/com.webforj.automation.see.SnapshotSerializer"
com.webforj.samples.unit.views.testbed.StarRatingSerializer
```

之后，该测试运行中的所有快照都会使用此序列化器。以下测试点击四颗星并检查快照：

```java title="StarRatingSnapshotTest.java" showLineNumbers
import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.samples.views.testbed.StarRating;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class StarRatingSnapshotTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var rating = new StarRating();
    context.add(rating);

    UserInteraction.click(
        ComponentLocator.of(Button.class).byText("4").locate());

    verifyThat(rating).matchesSnapshot();
  }
}
```

评分组件所在行会显示星星数量：

```snapshot title="StarRatingSnapshotTest.shouldMatchTheSnapshot.yml" showLineNumbers
# webforJ testbed snapshot v1
- StarRating [composite=Div] [node=div] [stars=4]:
  - Button "1"
  - Button "2"
  - Button "3"
  - Button "4"
  - Button "5"
```

除了 `setAttribute`，序列化器接收到的 `SnapshotNode` 还可以设置该行的文本和标记，并在其下方添加子行，以描述组件的各个部分，例如表格的列和行。只有当 `isBrowserState()` 返回 `true` 时，序列化器才会读取浏览器持有的状态。<JavadocLink type="automation" location="com/webforj/automation/see/SnapshotNode" code="true">SnapshotNode</JavadocLink> 参考文档列出了所有方法。

应用中的序列化器会在 webforJ 自带的序列化器之后运行，因此，如果它设置的属性与 webforJ 设置的属性键相同，就会替换该值。同一个组件类有两个序列化器，或者两个序列化器在同一行写入相同属性时，快照检查会失败，并显示两个类的名称。
