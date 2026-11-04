---
sidebar_position: 20
title: 渲染
slug: rendering
description: >-
  Transform Table cells into text, badges, icons, links, or custom HTML with
  built-in renderers and the setRenderer method.
_i18n_hash: 7d9beff25d6b372fb4309fe491ecad08
---
<!-- vale Google.Headings = NO -->
<!-- vale Google.Colons = NO -->
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/renderer/Renderer" top='true'/>

渲染器控制列中每个单元格的显示方式。渲染器不会显示原始值，而是将每个单元格的数据转换为带样式的文本、图标、徽章、链接、操作按钮，或其他任何能让数据更易于阅读和操作的视觉元素。

渲染完全在浏览器中进行。服务器发送原始数据，由客户端负责呈现，因此无论行数多少，`Table` 都能保持快速。

使用 `setRenderer()` 为列指定渲染器。该渲染器会统一应用于该列中的每个单元格：

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

:::tip 渲染器与值提供器
如果只需转换或格式化单元格的值，而不生成任何 DOM 结构，请改用[值提供器](/docs/components/table/columns#value-providers)。渲染器会为每个渲染的行创建额外的 DOM 元素，这会带来渲染开销。仅在需要图标、徽章、按钮或任何基于 HTML 的呈现方式等视觉输出时使用渲染器。
:::

webforJ 内置了适用于最常见场景的渲染器。对于应用中特有的需求，可以扩展 `Renderer` 并实现 `build()`，返回一个 lodash 模板字符串，该模板会在浏览器中针对每个单元格运行。

## 常用渲染器 {#common-renderers}

以下示例介绍四种常用渲染器，并展示如何在实践中使用 `setRenderer()`。

### TextRenderer {#text-renderer}

将单元格内容显示为普通文本或带样式的文本。无需改变列的结构，即可应用主题颜色或文本装饰，例如将优先级字段标为红色，或将重要标识符加粗。

```java
TextRenderer<MusicRecord> renderer = new TextRenderer<>();
renderer.setTheme(Theme.PRIMARY);
renderer.setDecorations(EnumSet.of(TextDecoration.BOLD));

table.addColumn("title", MusicRecord::getTitle).setRenderer(renderer);
```

### BadgeRenderer {#badge-renderer}

将单元格值放入徽章元素中。支持主题、大小、颜色种子（为每个不同的值自动分配不同颜色），以及可选的前置图标。适用于标签、类型或分类等类别值，便于用户快速浏览和比较各行。

```java
BadgeRenderer<MusicRecord> renderer = new BadgeRenderer<>();
renderer.setTheme(BadgeTheme.PRIMARY);

table.addColumn("musicType", MusicRecord::getMusicType).setRenderer(renderer);
```

### BooleanRenderer {#boolean-renderer}

将 `true`、`false` 和 `null` 值替换为图标。适用于所有布尔值列：相比文本，图标可以更快地传达值，例如功能标志、启用/停用状态或选择加入字段。

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

使用指定 `Locale` 的规则将数值格式化为货币金额。适用于需要根据区域设置正确格式化（货币符号、分隔符、小数位数）的金额列。

```java
// 美元
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// 使用德国区域设置格式化欧元
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

## 条件渲染 {#conditional-rendering}

`ConditionalRenderer` 会根据单元格的值为每个单元格选择不同的渲染器。条件按顺序求值；第一个匹配的条件生效。可以通过 `otherwise()` 设置兜底渲染器。

以下示例展示了如何对发票状态列进行条件渲染，根据值切换不同的 `BadgeRenderer`：

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

条件渲染也适用于数值阈值。此服务器仪表板使用 `ConditionalRenderer`，根据 CPU 和内存使用率切换 `ProgressBarRenderer` 的主题：

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

条件通过静态工厂方法构建，并可使用 `and()`、`or()` 和 `negate()` 组合。

```java
// 值相等
Condition.equalTo("active")
Condition.equalToIgnoreCase("active")
Condition.in("active", "pending", "new")

// 数值比较
Condition.greaterThan(100)
Condition.lessThanOrEqual(0)
Condition.between(10, 50)

// 布尔值 / 空值检查
Condition.isTrue()
Condition.isFalse()
Condition.isEmpty()

// 字符串匹配
Condition.contains("error")
Condition.containsIgnoreCase("warn")

// 条件组合
Condition.greaterThan(0).and(Condition.lessThan(100))
Condition.isEmpty().or(Condition.equalTo("N/A"))
Condition.isTrue().negate()

// 跨列检查
Condition.column("status").equalTo("active")

// 原始 JavaScript 表达式
Condition.expression("cell.value % 2 === 0")
```

## 组合渲染 {#composite-rendering}

`CompositeRenderer` 使用 flex 布局，在单个单元格中并排组合多个渲染器。可用于将图标与文本配对、在姓名旁显示头像，或在状态指示器旁放置徽章。

下方的员工目录在 *Employee* 列中使用 `CompositeRenderer`，在每位员工姓名旁显示自动生成的头像：

<!-- vale off -->
<ComponentDemo
path='/webforj/employeedirectory'
files={[
  'src/main/java/com/webforj/samples/views/table/renderers/EmployeeDirectoryView.java',
  'src/main/java/com/webforj/samples/views/table/renderers/Employee.java',
  'src/main/java/com/webforj/samples/views/table/renderers/EmployeeService.java',
]}
height='600px'
/>
<!-- vale on -->

## 自定义渲染器 {#custom-renderers}

如果内置渲染器无法满足需求，可以扩展 `Renderer` 并实现 `build()`。该方法返回一个 lodash 模板字符串，其中包含 HTML 和 JavaScript 混合内容，会在浏览器中针对该列的每个单元格运行。

### 创建自定义渲染器 {#creating-a-custom-renderer}

**第 1 步：**使用行数据类型扩展 `Renderer`。

```java
public class RatingRenderer extends Renderer<MusicRecord> {
```

**第 2 步：**重写 `build()` 并返回一个 lodash 模板字符串。

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

**第 3 步：**将渲染器指定给列。

```java
table.addColumn("rating", MusicRecord::getRating)
     .setRenderer(new RatingRenderer());
```

:::tip
有关如何使用 Lodash 语法访问单元格信息并创建信息丰富的渲染器，请参阅[此参考章节](#template-reference)。
:::

### 访问多列数据 {#accessing-multiple-columns}

在模板中使用 `cell.row.getValue("columnId")` 读取同一行中的其他列。此方法适用于组合字段、计算差值或交叉引用相关数据。

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

`IconButtonRenderer` 和 `ButtonRenderer` 开箱即支持 `addClickListener()`。点击事件可通过 `e.getItem()` 访问行数据对象。

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

## 性能：延迟渲染 <DocChip chip='since' label='25.12' /> {#lazy-rendering}

对于使用徽章、进度条、头像或 Web 组件等渲染成本较高的列，可以启用延迟渲染以提升滚动性能。

```java
table.addColumn("status", Order::getStatus)
     .setRenderer(new BadgeRenderer<>())
     .setLazyRender(true);
```

在列上设置 `setLazyRender(true)` 后，用户滚动时，单元格会显示轻量的动态占位符。滚动停止后才会渲染实际单元格内容。这是列级设置，因此可以仅对受益的列选择性启用。

<!-- vale off -->
<ComponentDemo
path='/webforj/lazyrender'
files={[
  'src/main/java/com/webforj/samples/views/table/renderers/LazyRenderView.java',
  'src/main/java/com/webforj/samples/views/table/renderers/Server.java',
  'src/main/java/com/webforj/samples/views/table/renderers/ServerService.java',
]}
height='600px'
/>
<!-- vale on -->

:::tip 何时启用延迟渲染
单元格渲染器会在 DOM 中创建更多实体，无论使用哪种渲染器，都会增加渲染时的 CPU 开销。

如果确实需要使用渲染器，延迟渲染可以帮助降低对性能的影响。如果只需更改或格式化值，并且不需要创建复杂的 DOM，请改用值提供器来转换该值。
:::

## 内置渲染器参考 {#built-in-renderers}

webforJ 提供了一套全面的渲染器，适用于最常见的场景。使用 `column.setRenderer(renderer)` 即可将其中任意渲染器指定给列。

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
<strong>TextRenderer</strong>  -  带样式的文本，可选主题和装饰
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

将单元格内容显示为普通文本或带样式的文本。支持主题颜色和粗体、斜体、下划线等文本装饰。

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
<strong>BadgeRenderer</strong>  -  在徽章中显示值
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

将单元格值放入徽章元素中。支持主题、大小、颜色种子（为每个不同的值自动分配不同颜色），以及可选的前置图标。

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
<strong>NullRenderer</strong>  -  null 或空值的占位内容
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

当单元格值为 `null` 或空值时，渲染可配置的备用字符串；否则按原样渲染该值。

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
<strong>BooleanRenderer</strong>  -  使用图标显示 true/false/null
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

将 `true`、`false` 和 `null` 值替换为图标。默认使用勾号、叉号和短横线。

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
<strong>StatusDotRenderer</strong>  -  在单元格文本旁显示彩色指示圆点
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

在单元格值左侧显示一个小的彩色圆点。可以将各个值映射到主题、CSS 颜色字符串或 `java.awt.Color` 实例。

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
<strong>CurrencyRenderer</strong>  -  根据区域设置格式化货币
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

使用指定 `Locale` 的规则将数值格式化为货币金额。

```java
// 美元
table.addColumn("cost", MusicRecord::getCost)
     .setRenderer(new CurrencyRenderer<>(Locale.US));

// 使用德国区域设置格式化欧元
table.addColumn("retail", MusicRecord::getRetail)
     .setRenderer(new CurrencyRenderer<>(Locale.GERMANY));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>PercentageRenderer</strong>  -  百分比，可选迷你进度条
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

将数值显示为百分比。将构造函数的第二个参数设为 `false`，可阻止在文本下方渲染细进度条。

```java
PercentageRenderer renderer = new PercentageRenderer<>(Theme.PRIMARY, true);
table.addColumn("completion", Task::getCompletion).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>ProgressBarRenderer</strong>  -  用于数值的完整进度条
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

渲染全宽进度条，支持配置最小值和最大值、启用不确定模式，以及设置条纹或动画效果。可使用带有 lodash 表达式的 `setText()` 在进度条上显示自定义文本。

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
<strong>MaskedTextRenderer</strong>  -  使用文本掩码格式化字符串
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

对字符串值应用字符掩码。`#` 匹配任意数字；字面字符会原样保留。所有支持的掩码字符请参阅[文本掩码规则](/docs/components/fields/masked/textfield#mask-rules)。

```java
table.addColumn("ssn", Employee::getSsn)
     .setRenderer(new MaskedTextRenderer<>("###-##-####"));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedNumberRenderer</strong>  -  使用数字掩码格式化数值
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

使用带区域设置分隔符的格式字符串格式化数值。`0` 表示必须有数字；`#` 表示可选数字。所有支持的掩码字符请参阅[数字掩码规则](/docs/components/fields/masked/numberfield#mask-rules)。

```java
table.addColumn("price", Product::getPrice)
     .setRenderer(new MaskedNumberRenderer<>("###,##0.00", Locale.US));
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>MaskedDateTimeRenderer</strong>  -  使用日期掩码格式化日期/时间值
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

使用格式标记格式化日期或时间值，例如 `%Mz`（月份）、`%Dz`（日期）和 `%Yz`（年份）。所有可用标记请参阅[日期掩码规则](/docs/components/fields/masked/datefield#mask-rules)。

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
<strong>EmailRenderer</strong>  -  将电子邮件地址显示为可点击的 mailto 链接
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

将单元格值包装在 `mailto:` 锚点中。默认使用主题为 primary 的邮件图标作为视觉提示。

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
<strong>PhoneRenderer</strong>  -  将电话号码显示为可点击的 tel 链接
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

将单元格值包装在 `tel:` 锚点中。在移动设备上点击链接会打开拨号器。默认显示主题为 primary 的电话图标。

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
<strong>AnchorRenderer</strong>  -  将单元格值显示为可配置的超链接
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

渲染可点击的锚点元素。`href` 支持 lodash 模板表达式，因此可以根据单元格值动态构建 URL。

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
<strong>ImageRenderer</strong>  -  在单元格中显示内嵌图片
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

显示图片。`src` 属性支持 lodash 模板表达式，因此每行都可以显示不同的图片。

```java
ImageRenderer renderer = new ImageRenderer<>();
renderer.setSrc("https://placehold.co/40x40?text=<%= cell.value %>");
renderer.setAlt("Cover");

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
<strong>AvatarRenderer</strong>  -  自动生成姓名首字母的头像
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

渲染头像组件。系统会根据单元格值自动生成姓名首字母。支持主题和备用图标。

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
<strong>IconRenderer</strong>  -  独立图标，可选点击交互
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

渲染单个图标。可附加点击监听器以实现交互行为。

```java
IconRenderer renderer = new IconRenderer<>(TablerIcon.create("music"));
table.addColumn("type", MusicRecord::getMusicType).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>

<Accordion disableGutters>
<AccordionSummary expandIcon={<ExpandMoreIcon />}>
<strong>IconButtonRenderer</strong>  -  可访问行数据的操作图标按钮
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='25.12' />

渲染可点击的图标按钮。点击事件通过 `e.getItem()` 提供行项目，因此非常适合执行行级操作。

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
<strong>ButtonRenderer</strong>  -  在单元格中显示带主题的按钮
</AccordionSummary>
<AccordionDetails>
<div>

<DocChip chip='since' label='24.00' />

在单元格中渲染完整的 `Button` 组件。

```java
ButtonRenderer renderer = new ButtonRenderer<>("Edit");
renderer.setTheme(ButtonTheme.PRIMARY);
renderer.addClickListener(e -> openEditor(e.getItem()));

table.addColumn("edit", r -> "Edit").setRenderer(renderer);
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

使用 lodash 模板内容字符串渲染任意 HTML 元素。当没有内置渲染器适用时，可使用此灵活方案。

```java
ElementRenderer renderer = new ElementRenderer<>("span", "<%= cell.value %>");
table.addColumn("custom", MusicRecord::getTitle).setRenderer(renderer);
```

</div>
</AccordionDetails>
</Accordion>
</AccordionGroup>

## 模板参考 {#template-reference}

渲染器提供了强大的机制，可自定义 `Table` 中数据的显示方式。核心类 `Renderer` 可供扩展，用于基于 lodash 模板创建自定义渲染器，从而实现动态、交互式的内容渲染。

Lodash 模板允许直接将 HTML 插入表格单元格，因此非常适合在 `Table` 中渲染复杂的单元格数据。这种方式可以根据单元格数据动态生成 HTML，从而呈现丰富且交互性强的单元格内容。

### Lodash 语法 {#lodash-syntax}

以下部分介绍 Lodash 语法基础。虽然并非详尽全面的概述，但可以帮助你开始在 `Table` 组件中使用 Lodash。

#### Lodash 模板语法概览： {#syntax-overview-for-lodash-templates}

- `<%= ... %>` - 插入值，将 JavaScript 代码的结果写入模板。
- `<% ... %>` - 执行 JavaScript 代码，可用于循环、条件判断等。
- `<%- ... %>` - 转义 HTML 内容，确保插入的数据不会受到 HTML 注入攻击。

#### 使用单元格数据的示例： {#examples-using-cell-data}

**1. 简单的值插入**：直接显示单元格的值。

`<%= cell.value %>`

**2. 条件渲染**：使用 JavaScript 逻辑有条件地渲染内容。

`<% if (cell.value > 100) { %> 'High' <% } else { %> 'Normal' <% } %>`

**3. 组合数据字段**：使用单元格中的多个数据字段渲染内容。

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. 转义 HTML 内容**：安全地渲染用户生成的内容。

渲染器可以访问客户端上的详细单元格、行和列属性：

**TableCell 属性：**

|属性|类型|说明|
|-|-|-|
|column|`TableColumn`|关联的列对象。|
|first|`boolean`|指示该单元格是否为行中的第一个单元格。|
|id|`String`|单元格 ID。|
|index|`int`|单元格在所在行中的索引。|
|last|`boolean`|指示该单元格是否为行中的最后一个单元格。|
|row|`TableRow`|与该单元格关联的行对象。|
|value|`Object`|单元格的原始值，直接来自数据源。|

**TableRow 属性：**

|属性|类型|说明|
|-|-|-|
|cells|`TableCell[]`|该行中的单元格。|
|data|`Object`|应用为该行提供的数据。|
|even|`boolean`|指示该行是否为偶数行（用于样式设置）。|
|first|`boolean`|指示该行是否为表格中的第一行。|
|id|`String`|该行的唯一 ID。|
|index|`int`|该行的索引。|
|last|`boolean`|指示该行是否为表格中的最后一行。|
|odd|`boolean`|指示该行是否为奇数行（用于样式设置）。|

**TableColumn 属性：**

|属性|类型|说明|
|-|-|-|
|align|ColumnAlignment|列的对齐方式（左、中、右）。|
|id|String|从行对象中获取单元格数据的字段。|
|label|String|要在列标题中显示的名称。|
|pinned|ColumnPinDirection|列的固定方向（左、右、自动）。|
|sortable|boolean|如果为 true，则可以对列进行排序。|
|sort|SortDirection|排序顺序。|
|type|ColumnType|列的类型（文本、数字、布尔值等）。|
|minWidth|number|列的最小宽度，单位为像素。|
