---
sidebar_position: 1
title: Accordion
sidebar_class_name: new-content
_i18n_hash: 560172f4743427476d9ecaadebd1d61d
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='name' label="dwc-accordion-panel" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>

`Accordion` 组件提供了一组垂直堆叠的可折叠面板。每个面板都有一个可点击的标题，可以切换其主体内容的可见性。`AccordionPanel` 可以作为一个独立的披露部分使用，或者在 `Accordion` 内部组合，以协调多个面板的展开和折叠行为。

<!-- INTRO_END -->

:::tip 何时使用手风琴
手风琴非常适合常见问题解答、设置页面以及逐步流程，因为一次性显示所有内容会造成布局过于拥挤。如果各个部分同样重要，并且用户受益于同时查看它们，考虑使用 [选项卡](/docs/components/tabbedpane)。
:::

## `AccordionPanel` {#accordion-panel}

`AccordionPanel` 是手风琴系统的核心构建块。将标签字符串传递给构造函数以设置标题文本，然后添加子组件来填充主体。面板可以独立工作，而不需要任何周围的 `Accordion` 组，使其成为一个有用的轻量级披露小部件，当您只需要一个单一的可折叠部分时。无参数构造函数也可用，当您更喜欢在构造后完全配置面板时。

```java
// 仅标签 - 分别添加主体内容
AccordionPanel panel = new AccordionPanel("部分标题");
panel.add(new Paragraph("主体内容在这里。"));

// 标签和主体内容直接传递给构造函数
AccordionPanel panel = new AccordionPanel("标题", new Paragraph("主体内容。"));
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionbasic'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionBasicView.java'
height='500px'
/>
<!-- vale on -->

### 开合 {#opening-and-closing}

可以在任何时候以编程方式控制打开/关闭状态。当您需要在决定如何操作之前读取当前状态时，`isOpened()` 非常有用。例如，您可以将面板切换到相反的状态或有条件地显示或隐藏用户界面的其他部分。

```java
// 展开面板
panel.open();

// 收起面板
panel.close();                    

// 如果当前已展开，则返回 true
boolean isOpen = panel.isOpened();
```

使用 `setLabel()` 在构造后更新标题文本。`setText()` 是同一操作的别名，因此标签可以与动态数据保持同步：

```java
panel.setLabel("更新标签");
```

## 手风琴组 {#accordion-groups}

将多个 `AccordionPanel` 实例包装在一个 `Accordion` 内部可以创建一个协调组。默认情况下，该组使用 **单模式**：打开一个面板会自动折叠所有其他面板，始终只显示一个部分。这个默认设置是故意的，它使用户集中于一块内容，并防止页面在面板具有大量主体内容时变得视觉上过于拥挤。

面板是独立构造并传递给 `Accordion` 的，因此您可以在将它们分组之前对每个面板进行配置。多个独立的 `Accordion` 实例也可以存在于同一页面上——每个组独立管理自己的状态，因此在一个组中展开面板不会对另一个组产生影响。

```java
AccordionPanel panel1 = new AccordionPanel("什么是 webforJ？");
AccordionPanel panel2 = new AccordionPanel("分组面板是如何工作的？");
AccordionPanel panel3 = new AccordionPanel("我可以有多个组吗？");

Accordion accordion = new Accordion(panel1, panel2, panel3);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordiongroup'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionGroupView.java'
height='400px'
/>
<!-- vale on -->

### 多重模式 {#multiple-mode}

多重模式允许任意数量的面板同时保持展开。这在用户需要同时比较多个部分的内容时非常有用，或者在每个面板足够短，以至于一次展开多个不会造成杂乱布局时。

```java
accordion.setMultiple(true);
```

在多重模式激活时，可以使用批量方法同时展开或收起组中的所有面板：

```java
// 展开组中的每个面板
accordion.openAll();

// 收起组中的每个面板
accordion.closeAll();   
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionmultiple'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionMultipleView.java'
height='500px'
/>
<!-- vale on -->

:::info 单模式限制
在启用多重模式时，`openAll()` 才可用。在单模式下调用它没有效果。`closeAll()` 在两种模式下均有效。
:::

<!-- vale off -->
## 禁用状态 {#disabled-state}
<!-- vale on -->

可以禁用单个面板，以防止用户交互，同时仍保持可见。这在加载状态期间或当某些部分有条件不可用时非常方便，显示面板结构，但不允许过早交互。一个已经打开的禁用面板将保持展开，但其标题将不再可点击以折叠它。禁用整个 `Accordion` 组会将禁用状态同时应用于所有包含的面板，因此您不需要逐个循环面板。

```java
// 禁用单个面板
panel.setEnabled(false);

// 禁用组中的所有面板
accordion.setEnabled(false);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordiondisabled'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionDisabledView.java'
height='600px'
/>
<!-- vale on -->

## 自定义面板 {#customizing-panels}

除了标签和基本的打开/关闭行为之外，每个 `AccordionPanel` 还支持其标题内容和展开/折叠图标的更丰富的自定义。

### 自定义标题 {#custom-header}

面板的标题默认为纯文本。使用 `addToHeader()` 将该文本替换为任何组件或组件组合，使得将图标、徽章、状态指示器或其他丰富标记与面板标签一起包含变得简单。这在仪表盘或设置面板中尤其有用，其中每个部分标题需要传达额外的上下文，例如项目数量、警告徽章或完成状态，而不要求用户先展开面板。

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("带图标的自定义标题"));
panel.addToHeader(headerContent);
```

:::info 标签替换
通过 `addToHeader()` 添加的内容将完全取代默认标签文本。`setLabel()` 和 `setText()` 继续在与 `addToHeader()` 一起使用时有效，但由于标题槽的视觉优先级，标签文本将不会显示，除非您在插槽内容中明确包含它。
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomheader'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionCustomHeaderView.java'
height='300px'
/>
<!-- vale on -->

### 自定义图标 {#custom-icon}

展开/折叠指示器默认显示为在打开和关闭状态下可见的-chevron。`setIcon()` 将其替换为任何 [`Icon`](/docs/components/icon) 组件，这对品牌图标或当其他视觉隐喻更适合内容时很有用。传递 `null` 可以恢复为默认的-chevron。`getIcon()` 返回当前设置的图标，如果使用的是默认的-chevron，则返回 `null`。

```java
// 将默认的-chevron 替换为加号图标
panel.setIcon(FeatherIcon.PLUS.create());

// 恢复默认的-chevron
panel.setIcon(null);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomicon'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionCustomIconView.java'
height='200px'
/>
<!-- vale on -->

## 嵌套手风琴 {#nested-accordions}

手风琴可以嵌套在其他手风琴面板内，这对于表示层次结构内容（例如分类设置或多级导航）非常有用。将一个内层 `Accordion` 添加到外层 `AccordionPanel` 中，就像其他子组件一样。保持嵌套的浅层。一到两层通常足够。更深的层次结构往往更难以导航，通常表明内容结构本身需要重新思考。

```java
AccordionPanel innerA = new AccordionPanel("内层面板 A");
AccordionPanel innerB = new AccordionPanel("内层面板 B");
Accordion innerAccordion = new Accordion(innerA, innerB);

AccordionPanel outer = new AccordionPanel("外层面板");
outer.add(innerAccordion);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionnested'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionNestedView.java'
height='550px'
/>
<!-- vale on -->

## 事件 {#events}

`AccordionPanel` 在切换生命周期的每个阶段都触发事件。三种事件类型涵盖不同的时刻，因此根据您的逻辑需要运行的时间进行选择：

| 事件 | 触发 |
|-------|-------|
| `AccordionPanelToggleEvent` | 在状态更改之前 |
| `AccordionPanelOpenEvent` | 在面板完全打开之后 |
| `AccordionPanelCloseEvent` | 在面板完全关闭之后 |

```java
panel.onToggle(e -> {
    // 在面板状态更改之前触发。
    // e.isOpened() 反映的是正在过渡到的状态，而不是当前状态。
    String direction = e.isOpened() ? "打开" : "关闭";
});

panel.onOpen(e -> {
    // 在面板完全打开后触发——适合懒加载内容。
});

panel.onClose(e -> {
    // 在面板完全关闭后触发——适合清理或摘要更新。
});
```

## 样式 {#styling}

<TableBuilder name="AccordionPanel" />
