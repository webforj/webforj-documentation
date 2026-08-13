---
sidebar_position: 20
title: Rendering
slug: rendering
description: >-
  Transform Table cells into text, badges, icons, links, or custom HTML with
  built-in renderers and the setRenderer method.
_i18n_hash: 314a210c06d9b920038308ed7c357f97
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/renderer/Renderer" top='true'/>

渲染器控制列中每个单元格的显示方式。渲染器将每个单元格的数据转换为样式化文本、图标、徽章、链接、操作按钮或其他视觉元素，从而使数据更易于阅读和操作。

渲染完全在浏览器中进行。服务器发送原始数据，客户端处理展示，使得“表格”无论行数多少，都快速响应。

使用 `setRenderer()` 方法将渲染器分配给一列。该渲染器统一应用于该列的每个单元格：

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

:::tip 渲染器与值提供者
如果您只需转换或格式化单元格值，而不生成任何 DOM 结构，请使用 [值提供者](/docs/components/table/columns#value-providers)。渲染器为每个渲染行创建额外的 DOM 元素，这在渲染时会带来额外开销。保留渲染器用于视觉输出，例如图标、徽章、按钮或任何基于 HTML 的呈现。
:::

webforJ 自带了内置的渲染器，适用于最常见的用例。对于特定于您的应用程序的内容，可以扩展 `Renderer` 并实现 `build()` 方法，以返回一个在浏览器中为每个单元格运行的 lodash 模板字符串。

## 共同渲染器 {#common-renderers}

以下示例介绍了四个常用的渲染器，并演示了 `setRenderer()` 模式的实际应用。

### 文本渲染器 {#text-renderer}

将单元格内容显示为普通或样式化文本。为列应用主题颜色或文本装饰，而无需更改其结构，例如使用红色突出显示优先级字段或将关键标识符加粗。

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);
renderer.setDecorations(EnumSet.of(TextDecoration.BOLD));

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

### 徽章渲染器 {#badge-renderer}

将单元格值包装在徽章元素中。支持主题、外观、颜色播种（为唯一值自动分配不同颜色）和可选的前导图标。可用于分类值，例如标签、类型或标签，帮助用户快速扫描和比较行。

```java
BadgeRenderer<MusicRecord> renderer = new BadgeRenderer<>();
renderer.setTheme(BadgeTheme.PRIMARY);

table.addColumn("musicType", MusicRecord::getMusicType).setRenderer(renderer);
```

### 布尔渲染器 {#boolean-renderer}

用图标替代 `true`、`false` 和 `null` 值。可用于任何布尔值列，其中图标可以比文本更快地传达值，例如功能标志、活动/非活动状态或选项字段。

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

### 货币渲染器 {#currency-renderer}

根据提供的 `Locale` 规则将数字值格式化为货币金额。适用于任何金钱列，尤其是地方格式（符号、分隔符、小数位数）至关重要。

```java
// 美元
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// 欧元（德国地区）
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

## 条件渲染 {#conditional-rendering}

`ConditionalRenderer` 根据单元格的值选择不同的渲染器。条件按顺序评估，首先匹配的条件优先。可以使用 `otherwise()` 设置后备条件。

以下示例显示了条件渲染应用于发票状态列，它根据值在 `BadgeRenderer` 变体之间切换：

<!-- vale off -->
<ComponentDemo
path='/webforj/invoicelist'
files={[
  'src/main/java/com/webforj/samples/views/table/renderers/InvoiceListView.java',
  'src/main/java/com/webforj/samples/views/table/renderers/Invoice.java',
  'src/main/java/com/webforj/samples/views/table/renderers/InvoiceService.java',
]}
height='600px'
/>
<!-- vale on -->

它在数字阈值下也能很好地工作。此服务器仪表板使用 `ConditionalRenderer` 根据 CPU 和内存使用级别切换 `ProgressBarRenderer` 主题：

<!-- vale off -->
<ComponentDemo
path='/webforj/serverdashboard'
files={[
  'src/main/java/com/webforj/samples/views/table/renderers/ServerDashboardView.java',
  'src/main/java/com/webforj/samples/views/table/renderers/Server.java',
  'src/main/java/com/webforj/samples/views/table/renderers/ServerService.java',
]}
height='600px'
/>
<!-- vale on -->

### 条件 API {#condition-api}

条件是使用静态工厂方法构建的，可以通过 `and()`、`or()` 和 `negate()` 组合。

```java
// 值相等
Condition.equalTo("active")
Condition.equalToIgnoreCase("active")
Condition.in("active", "pending", "new")

// 数字比较
Condition.greaterThan(100)
Condition.lessThanOrEqual(0)
Condition.between(10, 50)

// 布尔值/空值
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

`CompositeRenderer` 将多个渲染器并排组合在一个单元格中，使用弹性布局。可用于将图标与文本配对、显示头像和名称一起，或在状态指示器旁堆叠徽章。

下面的员工目录在 *员工* 列上使用 `CompositeRenderer`，为了在每个员工的姓名旁显示自动生成的头像：

<!-- vale off -->
<ComponentDemo
path='/webforj/employeedirectory'
files={['src/main/java/com/webforj/samples/views/table/renderers/EmployeeDirectoryView.java']}
height='600px'
/>
<!-- vale on -->

## 自定义渲染器 {#custom-renderers}

当没有内置渲染器适合您的用例时，扩展 `Renderer` 并实现 `build()` 方法。该方法返回一个在浏览器中为该列的每个单元格运行的 lodash 模板字符串，该字符串由 HTML 和 JavaScript 的组合表示。

### 创建自定义渲染器 {#creating-a-custom-renderer}

**步骤 1：** 使用您的行数据类型扩展 `Renderer`。

```java
public class RatingRenderer extends Renderer<MusicRecord> {
```

**步骤 2：** 重写 `build()` 方法并返回一个 lodash 模板字符串。

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

**步骤 3：** 将渲染器分配给一列。

```java
table.addColumn("rating", MusicRecord::getRating)
     .setRenderer(new RatingRenderer());
```

:::tip
有关 Lodash 语法如何用于访问单元格信息和创建信息渲染器的更多信息，请参见 [此参考部分](#template-reference)。
:::

### 访问多个列 {#accessing-multiple-columns}

使用 `cell.row.getValue("columnId")` 在模板内部读取兄弟列。这对于组合字段、计算增量或交叉引用相关数据非常有用。

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

`IconButtonRenderer` 和 `ButtonRenderer` 默认提供 `addClickListener()`。点击事件通过 `e.getItem()` 提供对行数据对象的访问。

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

对于使用视觉上耗资源的渲染器（如徽章、进度条、头像或网络组件）的列，启用懒渲染可以提高滚动性能。

```java
table.addColumn("status", Order::getStatus)
     .setRenderer(new BadgeRenderer<>())
     .setLazyRender(true);
```

当对一列设置 `setLazyRender(true)` 时，单元格在用户滚动时显示轻量级动画占位符。实际的单元格内容会在滚动停止后渲染。这是一个列级别的设置，可以仅针对受益的列进行选择性启用。

<!-- vale off -->
<ComponentDemo
path='/webforj/lazyrender'
files={['src/main/java/com/webforj/samples/views/table/renderers/LazyRenderView.java']}
height='600px'
/>
<!-- vale on -->

:::tip 何时启用懒渲染
单元格渲染器在 DOM 中创建更多实体，无论哪个渲染器创建它，都意味着在渲染过程中需要更多的 CPU 工作。

如果确实需要渲染器，懒渲染可以帮助减少性能影响。如果您只需更改或格式化值，并且没有创建复杂的 DOM，请使用值提供者来转换值。
:::

## 内置渲染器参考 {#built-in-renderers}

webforJ 提供了一整套适用于最常见用例的渲染器。使用 `column.setRenderer(renderer)` 将它们分配给列。

<!-- vale off -->
<ComponentDemo
path='/webforj/productcatalog'
files={[
  'src/main/java/com/webforj/samples/views/table/renderers/ProductCatalogView.java',
  'src/main/java/com/webforj/samples/views/table/renderers/Product.java',
  'src/main/java/com/webforj/samples/views/table/renderers/ProductService.java',
]}
height='600px'
/>
<!-- vale on -->

### 文本和标签 {#text-and-labels}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>文本渲染器</strong>  -  带有可选主题和装饰的样式文本
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

将单元格内容显示为普通或样式化文本。支持主题颜色和文本装饰，如加粗、斜体和下划线。

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
<strong>徽章渲染器</strong>  -  值显示在徽章芯片内
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

将单元格值包装在徽章元素中。支持主题、外观、颜色播种（自动分配不同颜色）和可选的前导图标。

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
<strong>空值渲染器</strong>  -  用于空值或空值的占位符
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

当单元格值为 `null` 或为空时，呈现一个可配置的后备字符串；否则照常呈现值。

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
<strong>布尔渲染器</strong>  -  true/false/null 显示为图标
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

用图标替代 `true`、`false` 和 `null` 值。默认使用对勾、叉和破折号。

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
<strong>状态点渲染器</strong>  -  在单元格文本旁边的彩色指示点
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

在单元格值左侧呈现一个小的彩色点。为每个单独的值映射主题、CSS 颜色字符串或 `java.awt.Color` 实例。

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
<strong>货币渲染器</strong>  -  与地区相关的货币格式
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

根据提供的 `Locale` 规则将数字值格式化为货币金额。

```java
// 美元
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// 欧元（德国地区）
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>百分比渲染器</strong>  -  可选的迷你进度条显示百分比
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

将数字值显示为百分比。将第二个构造参数设置为 `false` 以防止在文本下方呈现细进度条。

```java
PercentageRenderer renderer = new PercentageRenderer<>(Theme.PRIMARY, true);
table.addColumn("completion", Task::getCompletion).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>进度条渲染器</strong>  -  针对数字值的完整进度条
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

呈现一个全宽进度条，具有可配置的最小和最大边界、不确定模式，以及条纹或动画显示。使用 `setText()` 方法与 lodash 表达式覆盖进度条上的自定义文本。

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
<strong>掩码文本渲染器</strong>  -  使用文本掩码格式化的字符串
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

对字符串值应用字符掩码。`#` 匹配任何数字；保留字面字符。请参阅 [文本掩码规则](/docs/components/fields/masked/textfield#mask-rules) 以获取所有支持的掩码字符。

```java
table.addColumn("ssn", Employee::getSsn)
     .setRenderer(new MaskedTextRenderer<>("###-##-####"));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>掩码数字渲染器</strong>  -  使用数字掩码格式化的数字值
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

使用带有地区相关分隔符的模式字符串格式化数字值。`0` 强制数字；`#` 是可选的。请参阅 [数字掩码规则](/docs/components/fields/masked/numberfield#mask-rules) 以获取所有支持的掩码字符。

```java
table.addColumn("price", Product::getPrice)
     .setRenderer(new MaskedNumberRenderer<>("###,##0.00", Locale.US));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>掩码日期时间渲染器</strong>  -  带有日期掩码的日期/时间值
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

根据模式标记格式化日期或时间值：`%Mz`（月）、`%Dz`（日）、`%Yz`（年）等。请参阅 [日期掩码规则](/docs/components/fields/masked/datefield#mask-rules) 以获取所有可用的标记。

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
<strong>电子邮件渲染器</strong>  -  电子邮件地址作为可点击的 mailto 链接
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

将单元格值包裹在 `mailto:` 锚中。默认情况下，使用主要主题的邮件图标作为视觉提示。

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
<strong>电话渲染器</strong>  -  电话号码作为可点击的 tel 链接
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

将单元格值包裹在 `tel:` 锚中。在移动设备上，点击会打开拨号器。默认情况下显示主要主题的电话图标。

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
<strong>锚渲染器</strong>  -  单元格值作为可配置的超链接
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
<strong>图像渲染器</strong>  -  单元格内的内联图像
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

显示图像。`src` 属性支持 lodash 模板表达式，因此每行都可以显示不同的图像。

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

### 人员和头像 {#people-and-avatars}

<AccordionGroup>
<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>头像渲染器</strong>  -  带有自动生成的首字母的头像
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

呈现头像组件。首字母根据单元格值自动生成。支持主题和后备图标。

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
<strong>图标渲染器</strong>  -  独立图标，可单击
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

呈现单个图标。为交互行为附加点击监听器。

```java
IconRenderer renderer = new IconRenderer<>(TablerIcon.create("music"));
table.addColumn("type", MusicRecord::getMusicType).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>图标按钮渲染器</strong>  -  具有行访问权限的可操作图标按钮
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

呈现可点击的图标按钮。点击事件通过 `e.getItem()` 提供对行项目的访问，非常适合行级操作。

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
<strong>按钮渲染器</strong>  -  单元格内的主题按钮
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

在单元格内呈现完整的 `Button` 组件。

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
<strong>元素渲染器</strong>  -  带有 lodash 内容的原始 HTML 元素
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

使用 lodash 模板内容字符串渲染任何 HTML 元素。这是没有适合内置渲染器情况的逃逸通道。

```java
ElementRenderer renderer = new ElementRenderer<>("span", "<%= cell.value %>");
table.addColumn("custom", MusicRecord::getTitle).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

## 模板参考 {#template-reference}

渲染器提供了一种强大的机制，用于定制在 `Table` 中显示数据的方式。主要类 `Renderer` 旨在扩展，以创建基于 lodash 模板的自定义渲染器，实现动态和交互式内容渲染。

Lodash 模板支持直接将 HTML 插入表格单元中，因而在渲染复杂单元格数据时极为有效。这种方式允许根据单元格数据动态生成 HTML，从而促进丰富和互动的表格单元内容。

### Lodash 语法 {#lodash-syntax}

以下部分概述了 Lodash 语法的基础知识。虽然这不是一个详尽或全面的概述，但可以帮助您开始在 `Table` 组件中使用 Lodash。

#### Lodash 模板语法概述 {#syntax-overview-for-lodash-templates}

- `<%= ... %>` - 插入值，将 JavaScript 代码的结果插入到模板中。
- `<% ... %>` - 执行 JavaScript 代码，允许循环、条件等。
- `<%- ... %>` - 转义 HTML 内容，确保插入的数据安全，不受 HTML 注入攻击。

#### 使用单元格数据的示例 {#examples-using-cell-data}

**1. 简单值插值**：直接显示单元格的值。

`<%= cell.value %>`

**2. 条件渲染**：使用 JavaScript 逻辑有条件地渲染内容。

`<% if (cell.value > 100) { %> '高' <% } else { %> '正常' <% } %>`

**3. 组合数据字段**：使用来自单元格的多个数据字段渲染内容。

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. 转义 HTML 内容**：安全渲染用户生成的内容。

渲染器可以访问详细的单元格、行和列属性：

**TableCell 属性：**

|属性	|类型	|描述|
|-|-|-|
|column|`TableColumn`|关联的列对象。|
|first|`boolean`|指示该单元格是否为行中的第一个单元格。|
|id|`String`|单元格 ID。|
|index|`int`|单元格在行中的索引。|
|last|`boolean`|指示该单元格是否为行中的最后一个单元格。|
|row|`TableRow`|与单元格关联的行对象。|
|value|`Object`|单元格的原始值，直接来自数据源。|

**TableRow 属性：**

|属性|类型|描述|
|-|-|-|
|cells|`TableCell[]`|行中的单元格。|
|data|`Object`|应用程序为行提供的数据。|
|even|`boolean`|指示该行是否为偶数行（供样式使用）。|
|first|`boolean`|指示该行是否为表中的第一行。|
|id|`String`|该行的唯一 ID。|
|index|`int`|该行的索引。|
|last|`boolean`|指示该行是否为表中的最后一行。|
|odd|`boolean`|指示该行是否为奇数行（供样式使用）。|

**TableColumn 属性：**

|属性	|类型	|描述|
|-|-|-|
|align|ColumnAlignment|列的对齐方式（左、中、右）。|
|id|String|从中获取单元格数据的行对象字段。|
|label|String|在列标题中渲染的名称。|
|pinned|ColumnPinDirection|列的固定方向（左、右、自动）。|
|sortable|boolean|如果为真，则该列可以排序。|
|sort|SortDirection|列的排序顺序。|
|type|ColumnType|列的类型（文本、数字、布尔值等）。|
|minWidth|number|列的最小宽度（以像素为单位）。|
