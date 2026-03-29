---
sidebar_position: 20
title: Rendering
slug: rendering
sidebar_class_name: new-content
_i18n_hash: c6f33a66de68ddcd600382bf0dc449f2
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/renderer/Renderer" top='true'/>

渲染器控制每列中的每个单元格如何显示。它不是显示原始值，而是将每个单元格的数据转换为样式文本、图标、徽章、链接、操作按钮或任何其他使数据更易于阅读和操作的视觉元素。

渲染完全在浏览器中进行。服务器发送原始数据，客户端处理呈现，无论行数多少，“表格”都能快速响应。

使用 `setRenderer()` 为列分配渲染器。渲染器统一应用于该列的每个单元格：

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

:::tip 渲染器与值提供者
如果您只需要转换或格式化单元格值，而不需要生成任何 DOM 结构，使用[值提供者](/docs/components/table/columns#value-providers)。渲染器为每个渲染的行创建额外的 DOM 元素，这在渲染时会有开销。保留渲染器用于视觉输出，例如图标、徽章、按钮或任何基于 HTML 的呈现。
:::

webforJ随附内置渲染器，适用于最常见的用例。对于您的应用程序特定需求，请扩展 `Renderer` 并实现 `build()` 以返回一个在浏览器中运行的 lodash 模板字符串，用于每个单元格。

## 常见渲染器 {#common-renderers}

以下示例演示四个常用渲染器，并展示了 `setRenderer()` 模式的实际应用。

### TextRenderer {#text-renderer}

以普通或样式文本的形式显示单元格内容。应用主题颜色或文本修饰，使列在不改变其结构的情况下突出显示，例如将优先级字段高亮为红色或将关键标识符加粗。

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);
renderer.setDecorations(EnumSet.of(TextDecoration.BOLD));

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

### BadgeRenderer {#badge-renderer}

将单元格值包装在徽章元素中。支持主题、扩展、颜色种子（根据独特值自动分配不同颜色）以及可选的前导图标。用于分类值，如标签、类型或标签，在这些情况下，独特的视觉元素可以帮助用户快速浏览和比较行。

```java
BadgeRenderer<MusicRecord> renderer = new BadgeRenderer<>();
renderer.setTheme(BadgeTheme.PRIMARY);

table.addColumn("musicType", MusicRecord::getMusicType).setRenderer(renderer);
```

### BooleanRenderer {#boolean-renderer}

用图标替换 `true`、`false` 和 `null` 值。用于任何 true/false 列，其中图标比文本更快速地传达值，例如功能标志、活动/不活动状态或选择字段。

```java
// 默认图标
BooleanRenderer<Task> renderer = new BooleanRenderer<>();
table.addColumn("completed", Task::isCompleted).setRenderer(renderer);

// 自定义图标
BooleanRenderer<Task> custom = new BooleanRenderer<>(
  TablerIcon.create("thumb-up").setTheme(Theme.SUCCESS),
  TablerIcon.create("thumb-down").setTheme(Theme.DANGER)
);
table.addColumn("completed", Task::isCompleted).setRenderer(custom);
```

### CurrencyRenderer {#currency-renderer}

使用提供的 `Locale` 规则将数字值格式化为货币金额。用于任何货币列，其中区域设置正确的格式（符号、分隔符、小数位数）很重要。

```java
// 美元
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// 欧元，使用德国区域设置
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

## 条件渲染 {#conditional-rendering}

`ConditionalRenderer` 根据单元格的值选择不同的渲染器。条件按顺序评估；第一个匹配的条件将生效。可以使用 `otherwise()` 设置一个备选方案。

以下示例展示了条件渲染应用于发票状态列，根据值在 `BadgeRenderer` 变体之间切换：

<!-- vale off -->
<ComponentDemo
path='/webforj/invoicelist'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/InvoiceListView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/Invoice.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/InvoiceService.java']}
height='600px'
/>
<!-- vale on -->

它也非常适合数字阈值。此服务器仪表板使用 `ConditionalRenderer` 根据 CPU 和内存使用水平切换 `ProgressBarRenderer` 主题：

<!-- vale off -->
<ComponentDemo
path='/webforj/serverdashboard?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/ServerDashboardView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/Server.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/ServerService.java']}
height='600px'
/>
<!-- vale on -->

### 条件 API {#condition-api}

条件使用静态工厂方法构建，并可以通过 `and()`、`or()` 和 `negate()` 进行组合。

```java
// 值相等
Condition.equalTo("active")
Condition.equalToIgnoreCase("active")
Condition.in("active", "pending", "new")

// 数字比较
Condition.greaterThan(100)
Condition.lessThanOrEqual(0)
Condition.between(10, 50)

// 布尔/空值
Condition.isTrue()
Condition.isFalse()
Condition.isEmpty()

// 字符串匹配
Condition.contains("error")
Condition.containsIgnoreCase("warn")

// 组合
Condition.greaterThan(0).and(Condition.lessThan(100))
Condition.isEmpty().or(Condition.equalTo("N/A"))
Condition.isTrue().negate()

// 跨列检查
Condition.column("status").equalTo("active")

// 原始 JavaScript 表达式
Condition.expression("cell.value % 2 === 0")
```

## 复合渲染 {#composite-rendering}

`CompositeRenderer` 在单个单元格中使用灵活布局并排组合多个渲染器。用于将图标与文本配对、显示头像与名称并排，或者在状态指示器旁边堆叠徽章。

下面的员工目录在*员工*列上使用了 `CompositeRenderer`，以显示每个员工名称旁边的自动生成头像：

<!-- vale off -->
<ComponentDemo
path='/webforj/employeedirectory?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/EmployeeDirectoryView.java'
height='600px'
/>
<!-- vale on -->

## 自定义渲染器 {#custom-renderers}

当没有适合您用例的内置渲染器时，扩展 `Renderer` 并实现 `build()`。该方法返回一个在浏览器中运行的 lodash 模板字符串，针对该列的每个单元格表示为 HTML 和 JavaScript 的组合。

### 创建自定义渲染器 {#creating-a-custom-renderer}

**步骤 1：** 扩展 `Renderer`，提供您的行数据类型。

```java
public class RatingRenderer extends Renderer<MusicRecord> {
```

**步骤 2：** 重写 `build()` 并返回一个 lodash 模板字符串。

```java
  @Override
  public String build() {
    return /* html */"""
      <%
        const rating = Number(cell.value);
        const stars  = Math.round(Math.min(Math.max(rating, 0), 5));
        const full   = '★'.repeat(stars);
        const empty  = '☆'.repeat(5 - stars);
      %>
      <span><%= full %><%= empty %></span>
      <span style="color: var(--dwc-color-body-text)">(<%= rating.toFixed(1) %>)</span>
    """;
  }
}
```

**步骤 3：** 将渲染器分配给列。

```java
table.addColumn("rating", MusicRecord::getRating)
     .setRenderer(new RatingRenderer());
```

:::tip
有关如何使用 Lodash 语法访问单元格信息并创建信息丰富的渲染器的更多信息，请参阅[此参考部分](#template-reference)。
:::

### 访问多个列 {#accessing-multiple-columns}

使用 `cell.row.getValue("columnId")` 在模板内读取兄弟列。这对于组合字段、计算差异或交叉引用相关数据很有用。

```java
public class ArtistAvatarRenderer extends Renderer<MusicRecord> {
  @Override
  public String build() {
    return /* html */"""
      <%
        const name     = cell.row.getValue("artist");
        const initials = name
          ? name.split(' ').map(w => w.charAt(0)).join('').substring(0, 2).toUpperCase()
          : '?';
      %>
      <div style="display: flex; align-items: center; gap: 8px;">
        <div style="width: 28px; height: 28px; border-radius: 50%;
          background: var(--dwc-color-primary); color: white;
          display: flex; align-items: center; justify-content: center;
          font-size: 11px; font-weight: 600;">
          <%= initials %>
        </div>
        <span><%= name %></span>
      </div>
    """;
  }
}
```

### 点击事件 {#click-events}

`IconButtonRenderer` 和 `ButtonRenderer` 提供 `addClickListener()`。点击事件通过 `e.getItem()` 提供对行数据对象的访问。

```java
IconButtonRenderer<MusicRecord> deleteBtn = new IconButtonRenderer<>(
  TablerIcon.create("trash").setTheme(Theme.DANGER)
);
deleteBtn.addClickListener(e -> {
  MusicRecord record = e.getItem();
  repository.delete(record);
  table.refresh();
});

table.addColumn("delete", r -> "").setRenderer(deleteBtn);
```

## 性能：懒渲染 <DocChip chip='since' label='25.12' /> {#lazy-rendering}

对于使用视觉上昂贵的渲染器的列，如徽章、进度条、头像或 Web 组件，启用懒渲染以提高滚动性能。

```java
table.addColumn("status", Order::getStatus)
     .setRenderer(new BadgeRenderer<>())
     .setLazyRender(true);
```

当在列上设置 `setLazyRender(true)` 时，单元格在用户滚动时显示轻量级动画占位符。实际单元格内容在滚动停止后呈现。此设置为列级，因此您可以根据需要仅对受益的列进行选择性启用。

<!-- vale off -->
<ComponentDemo
path='/webforj/lazyrender?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/LazyRenderView.java'
height='600px'
/>
<!-- vale on -->

:::tip 何时启用懒渲染
单元格渲染器在 DOM 中创建更多实体，这意味着在渲染过程中需要更多 CPU 工作，无论是哪个渲染器生成它。

如果确实需要使用渲染器，则懒渲染可以帮助降低性能影响。如果您只需要更改或格式化值，且不需要创建复杂的 DOM，请改为使用值提供者来转换值。
:::

## 内置渲染器参考 {#built-in-renderers} 

webforJ 配备了全面的渲染器集合，以满足最常见的用例。使用 `column.setRenderer(renderer)` 将其分配给列。

<!-- vale off -->
<ComponentDemo
path='/webforj/productcatalog?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/ProductCatalogView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/Product.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/renderers/ProductService.java']}
height='600px'
/>
<!-- vale on -->

### 文本和标签 {#text-and-labels}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>TextRenderer</strong>  -  带有可选主题和装饰的样式文本
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

以普通或样式文本的形式显示单元格内容。支持主题颜色和文本修饰，如粗体、斜体和下划线。

```java
TextRenderer renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);
renderer.setDecorations(EnumSet.of(TextDecoration.BOLD));

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>BadgeRenderer</strong>  -  值显示在徽章芯片中
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

将单元格值包装在徽章元素中。支持主题、扩展、颜色种子（根据独特值自动分配不同颜色）以及可选的前导图标。

```java
BadgeRenderer renderer = new BadgeRenderer<>();
renderer.setTheme(BadgeTheme.PRIMARY);

table.addColumn("musicType", MusicRecord::getMusicType).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>NullRenderer</strong>  -  null 或空值的占位符
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

当单元格值为 `null` 或空时，呈现可配置的回退字符串；否则按原样呈现值。

```java
table.addColumn("notes", MusicRecord::getNotes)
     .setRenderer(new NullRenderer<>("N/A"));
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

### 状态和指示器 {#status-and-indicators}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>BooleanRenderer</strong>  -  true/false/null 显示为图标
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

用图标替换 `true`、`false` 和 `null` 值。默认为对勾、叉和破折号。

```java
// 默认图标
BooleanRenderer renderer = new BooleanRenderer<>();
table.addColumn("completed", Task::isCompleted).setRenderer(renderer);

// 自定义图标
BooleanRenderer custom = new BooleanRenderer<>(
  TablerIcon.create("thumb-up").setTheme(Theme.SUCCESS),
  TablerIcon.create("thumb-down").setTheme(Theme.DANGER)
);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>StatusDotRenderer</strong>  -  在单元格文本旁边的彩色指示点
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

在单元格值的左侧呈现一个小彩色点。将各个值映射到主题、CSS 颜色字符串或 `java.awt.Color` 实例。

```java
StatusDotRenderer renderer = new StatusDotRenderer<>();
renderer.addMapping("Active",    Theme.SUCCESS);
renderer.addMapping("Pending",   Theme.WARNING);
renderer.addMapping("Cancelled", Theme.DANGER);

table.addColumn("status", Order::getStatus).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

### 数字、货币和日期 {#numbers-currency-and-dates}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>CurrencyRenderer</strong>  -  语言环境感知的货币格式
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

使用提供的 `Locale` 规则将数字值格式化为货币金额。

```java
// 美元
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// 欧元，使用德国区域设置
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>PercentageRenderer</strong>  -  带有可选迷你进度条的百分比
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

将数值显示为百分比。将第二个构造函数参数设置为 `false`，以防止在文本下方呈现细进度条。

```java
PercentageRenderer renderer = new PercentageRenderer<>(Theme.PRIMARY, true);
table.addColumn("completion", Task::getCompletion).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>ProgressBarRenderer</strong>  -  数值的全宽进度条
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

呈现具有可配置最小值和最大值、无确定模式，以及条纹或动画显示的全宽进度条。使用 `setText()` 与 lodash 表达式在条上覆盖自定义文本。

```java
ProgressBarRenderer renderer = new ProgressBarRenderer<>();
renderer.setMax(100);
renderer.setTheme(Theme.SUCCESS);
renderer.setTextVisible(true);
renderer.setText("<%= cell.value %>/100");

table.addColumn("progress", Task::getProgress).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedTextRenderer</strong>  -  带有文本掩码的字符串格式
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

对字符串值应用字符掩码。`#` 匹配任何数字； 字面字符被保留。有关所有支持的掩码字符，请参阅[文本掩码规则](/docs/components/fields/masked/textfield#mask-rules)。

```java
table.addColumn("ssn", Employee::getSsn)
     .setRenderer(new MaskedTextRenderer<>("###-##-####"));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedNumberRenderer</strong>  -  数值使用数字掩码格式
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

使用带有区域设置感知分隔符的模式字符串对数值进行格式化。`0` 强制一个数字； `#` 是可选的。有关所有支持的掩码字符，请参阅[数字掩码规则](/docs/components/fields/masked/numberfield#mask-rules)。

```java
table.addColumn("price", Product::getPrice)
     .setRenderer(new MaskedNumberRenderer<>("###,##0.00", Locale.US));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedDateTimeRenderer</strong>  -  带有日期掩码的日期/时间值
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

使用模式令牌对日期或时间值进行格式化：`%Mz`（月份）、`%Dz`（日期）、`%Yz`（年份）等。有关所有可用令牌，请参阅[日期掩码规则](/docs/components/fields/masked/datefield#mask-rules)。

```java
table.addColumn("released", MusicRecord::getReleaseDate)
     .setRenderer(new MaskedDateTimeRenderer<>("%Mz/%Dz/%Yz"));
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

### 链接和媒体 {#links-and-media}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>EmailRenderer</strong>  -  邮件地址作为可点击的 mailto 链接
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

将单元格值包装在 `mailto:` 锚中。默认情况下，主要主题的邮件图标作为视觉提示。

```java
// 默认邮件图标
table.addColumn("email", Contact::getEmail)
     .setRenderer(new EmailRenderer<>());

// 自定义图标
table.addColumn("email", Contact::getEmail)
     .setRenderer(new EmailRenderer<>(TablerIcon.create("at")));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>PhoneRenderer</strong>  -  电话号码作为可点击的 tel 链接
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

将单元格值包装在 `tel:` 锚中。在移动设备上，点击时打开拨号器。默认情况下显示主要主题的电话图标。

```java
// 默认电话图标
table.addColumn("phone", Contact::getPhone)
     .setRenderer(new PhoneRenderer<>());

// 自定义图标
table.addColumn("phone", Contact::getPhone)
     .setRenderer(new PhoneRenderer<>(TablerIcon.create("device-mobile")));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>AnchorRenderer</strong>  -  单元格值作为可配置超链接
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

呈现可点击的锚元素。`href` 支持 lodash 模板表达式，因此您可以根据单元格值动态构建 URL。

```java
AnchorRenderer renderer = new AnchorRenderer<>();
renderer.setHref("https://www.google.com/search?q=<%= cell.value %>");
renderer.setTarget("_blank");

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>ImageRenderer</strong>  -  单元格中的内联图像
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

显示一张图像。`src` 属性支持 lodash 模板表达式，因此每行可以显示不同的图像。

```java
ImageRenderer renderer = new ImageRenderer<>();
renderer.setSrc("https://placehold.co/40x40?text=<%= cell.value %>");
renderer.setAlt("封面");

table.addColumn("cover", MusicRecord::getArtist).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

### 人物和头像 {#people-and-avatars}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>AvatarRenderer</strong>  -  带有自动生成的首字母的头像
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

呈现头像组件。首字母自动从单元格值中派生。支持主题和回退图标。

```java
AvatarRenderer renderer = new AvatarRenderer<>();
renderer.setTheme(AvatarTheme.PRIMARY);
renderer.setIcon(TablerIcon.create("user"));

table.addColumn("artist", MusicRecord::getArtist).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

### 图标和操作 {#icons-and-actions}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>IconRenderer</strong>  -  独立图标，可点击
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

呈现一个独立的图标。附加点击监听器以实现交互行为。

```java
IconRenderer renderer = new IconRenderer<>(TablerIcon.create("music"));
table.addColumn("type", MusicRecord::getMusicType).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>IconButtonRenderer</strong>  -  可操作的图标按钮，具有行访问权限
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

呈现一个可点击的图标按钮。点击事件通过 `e.getItem()` 揭示行项，非常适合行级操作。

```java
IconButtonRenderer renderer = new IconButtonRenderer<>(TablerIcon.create("edit"));
renderer.addClickListener(e -> openEditor(e.getItem()));

table.addColumn("actions", r -> "").setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>ButtonRenderer</strong>  -  单元格中的主题按钮
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

在单元格内部渲染完整的 `Button` 组件。

```java
ButtonRenderer renderer = new ButtonRenderer<>("编辑");
renderer.setTheme(ButtonTheme.PRIMARY);
renderer.addClickListener(e -> openEditor(e.getItem()));

table.addColumn("edit", r -> "编辑").setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>ElementRenderer</strong>  -  使用 lodash 内容的原始 HTML 元素
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

使用 lodash 模板内容字符串呈现任何 HTML 元素。这是没有适合的内置渲染器的情况下的逃生通道。

```java
ElementRenderer renderer = new ElementRenderer<>("span", "<%= cell.value %>");
table.addColumn("custom", MusicRecord::getTitle).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

## 模板参考 {#template-reference}

渲染器提供了一种强大的机制，用于自定义数据在 `Table` 中的显示方式。主类 `Renderer` 旨在扩展以创建基于 lodash 模板的自定义渲染器，使动态和交互式内容渲染成为可能。

Lodash 模板使得直接将 HTML 插入表格单元格成为可能，使其在表格中渲染复杂单元格数据方面极为有效。此方法允许根据单元格数据动态生成 HTML，促进丰富和交互式表格单元格内容的制作。

### Lodash 语法 {#lodash-syntax}

以下部分概述了 Lodash 语法的基础知识。虽然这不是一个详尽的或全面的概述，但可以帮助您开始在 `Table` 组件中使用 Lodash。

#### Lodash 模板语法概述： {#syntax-overview-for-lodash-templates}

- `<%= ... %>` - 插值值，将 JavaScript 代码的结果插入模板中。
- `<% ... %>` - 执行 JavaScript 代码，允许循环、条件等。
- `<%- ... %>` - 转义 HTML 内容，以确保插值数据免受 HTML 注入攻击。

#### 使用单元格数据的示例: {#examples-using-cell-data}

**1. 简单值插值**：直接显示单元格的值。

`<%= cell.value %>`

**2. 条件渲染**：使用 JavaScript 逻辑有条件地渲染内容。

`<% if (cell.value > 100) { %> '高' <% } else { %> '正常' <% } %>`

**3. 结合数据字段**：使用单元格的多个数据字段渲染内容。

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. 转义 HTML 内容**：安全地渲染用户生成的内容。

渲染器在客户端可以访问详细的单元格、行和列属性：

**TableCell 属性：**

|属性	|类型	|描述|
|-|-|-|
|column|`TableColumn`|关联的列对象。|
|first|`boolean`|指示该单元格是否为行中的第一个。|
|id|`String`|单元格 ID。|
|index|`int`|单元格在其行中的索引。|
|last|`boolean`|指示该单元格是否为行中的最后一个。|
|row|`TableRow`|与单元格关联的行对象。|
|value|`Object`|单元格的原始值，直接来自数据源。|

**TableRow 属性：**

|属性|类型|描述|
|-|-|-|
|cells|`TableCell[]`|行中的单元格。|
|data|`Object`|由应用程序提供的行数据。|
|even|`boolean`|指示行是否为偶数（用于样式目的）。|
|first|`boolean`|指示行是否为表格中的第一个。|
|id|`String`|行的唯一 ID。|
|index|`int`|行索引。|
|last|`boolean`|指示行是否为表格中的最后一个。|
|odd|`boolean`|指示行是否为奇数（用于样式目的）。|

**TableColumn 属性：**

|属性	|类型	|描述|
|-|-|-|
|align|ColumnAlignment|列的对齐方式（左、中、右）。|
|id|String|从行对象获取单元格数据的字段。|
|label|String|在列标题中渲染的名称。|
|pinned|ColumnPinDirection|列的固定方向（左、右、自动）。|
|sortable|boolean|如果为 true，则可以对该列进行排序。|
|sort|SortDirection|列的排序顺序。|
|type|ColumnType|列的类型（文本、数字、布尔值等）。|
|minWidth|number|列的最小宽度（以像素为单位）。|
