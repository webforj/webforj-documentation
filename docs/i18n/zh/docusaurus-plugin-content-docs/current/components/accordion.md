---
sidebar_position: 1
title: Accordion
_i18n_hash: 207c70347cc18d88661a8a9279988417
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='name' label="dwc-accordion-panel" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>

`Accordion` 组件提供了一组垂直堆叠的可折叠面板。每个面板都有一个可单击的标题，切换其主体内容的可见性。 `AccordionPanel` 可以作为独立的披露部分使用，或者在 `Accordion` 内部分组以协调多个面板的展开和折叠行为。

<!-- INTRO_END -->

:::tip 何时使用手风琴
手风琴非常适合常见问题解答、设置页面和逐步流程，因为一次显示所有内容会造成压倒性的布局。如果各部分同样重要，且用户受益于同时查看它们，请考虑使用 [tabs](/docs/components/tabbedpane)。
:::

## `AccordionPanel` {#accordion-panel}

`AccordionPanel` 是手风琴系统的核心构建块。将标签字符串传递给构造函数以设置标题文本，然后添加子组件以填充主体。面板可以独立工作，而无需任何包围的 `Accordion` 组，使其成为一个有用的轻量级披露小部件，当你只需要一个可折叠的部分时。无参数构造函数也可用，以便在构造后完全配置面板。

```java
// 仅标签 - 单独添加主体内容
AccordionPanel panel = new AccordionPanel("部分标题");
panel.add(new Paragraph("主体内容在这里。"));

// 标签和主体内容直接传递到构造函数中
AccordionPanel panel = new AccordionPanel("标题", new Paragraph("主体内容。"));
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionbasic'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionBasicView.java']}
height='550px'
/>
<!-- vale on -->

### 开关 {#opening-and-closing}

可以在任何时候以编程方式控制开启/关闭状态。 `isOpened()` 在你需要在决定下一步做什么之前读取当前状态时非常有用。例如，你可能会切换面板到相反状态或有条件地显示或隐藏用户界面的其他部分。

```java
// 展开面板
panel.open();

// 折叠面板
panel.close();                    

// 如果当前已展开，则返回 true
boolean isOpen = panel.isOpened();
```

使用 `setLabel()` 在构造后更新标题文本。 `setText()` 是同一操作的别名，因此标签可以与动态数据保持同步：

```java
panel.setLabel("更新标签");
```

## 手风琴组 {#accordion-groups}

将多个 `AccordionPanel` 实例包裹在一个 `Accordion` 内部会创建一个协调的组。默认情况下，组使用 **单一模式**：打开一个面板会自动折叠所有其他面板，同时只保持一个部分可见。这个默认设置是故意的，它使用户集中于一部分内容，并防止页面在面板拥有大量主体内容时变得视觉上过于拥挤。

面板是在独立构建的并传递给 `Accordion`，因此你可以在分组之前配置每一个。多个独立的 `Accordion` 实例也可以在同一页面上存在——每个组独立管理自己的状态，因此在一个组中扩展面板不会对另一个组产生影响。

```java
AccordionPanel panel1 = new AccordionPanel("webforJ 是什么？");
AccordionPanel panel2 = new AccordionPanel("分组面板如何工作？");
AccordionPanel panel3 = new AccordionPanel("我可以有多个组吗？");

Accordion accordion = new Accordion(panel1, panel2, panel3);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordiongroup'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionGroupView.java']}
height='400px'
/>
<!-- vale on -->

### 多重模式 {#multiple-mode}

多重模式允许任意数量的面板同时保持展开。这在用户需要同时比较多个部分的内容时非常有用，或者当每个面板足够短，以至于一次展开多个不会造成混乱的布局时。

```java
accordion.setMultiple(true);
```

在多重模式激活时，组中的所有面板都可以使用批量方法同时展开或折叠：

```java
// 展开组中的每个面板
accordion.openAll();

// 折叠组中的每个面板
accordion.closeAll();   
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionmultiple'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionMultipleView.java']}
height='575px'
/>
<!-- vale on -->

:::info 单一模式限制
`openAll()` 仅在启用多重模式时可用。在单一模式下调用它没有效果。 `closeAll()` 在两种模式下均有效。
:::

<!-- vale off -->
## 禁用状态 {#disabled-state}
<!-- vale on -->

可以禁用单个面板以防止用户交互，同时仍然保持可见。这在加载状态期间或某些部分条件不可用时非常方便，显示面板结构而不允许过早的交互。已打开的禁用面板保持展开，但其标题不再可以单击以折叠。禁用 `Accordion` 组会同时将禁用状态应用于所有包含的面板，因此你无需单独遍历面板。

```java
// 禁用单个面板
panel.setEnabled(false);

// 禁用组中的所有面板
accordion.setEnabled(false);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordiondisabled'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionDisabledView.java']}
height='650px'
/>
<!-- vale on -->

## 自定义面板 {#customizing-panels}

除了标签和基本的开启/关闭行为，每个 `AccordionPanel` 支持更丰富的自定义，包括其标题内容和展开/折叠图标。

### 自定义标题 {#custom-header}

面板的标题默认将其标签呈现为纯文本。使用 `addToHeader()` 将该文本替换为任何组件或组件组合，使得将图标、徽章、状态指示器或其他丰富标记与面板标签一起包含变得简单。这在仪表板或设置面板中特别有用，在这些地方每个部分标题需要快速传达额外的上下文，例如项目计数、警告徽章或完成状态，而无需用户首先展开面板。

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("带图标的自定义标题"));
panel.addToHeader(headerContent);
```

:::info 标签替换
通过 `addToHeader()` 添加的内容将完全替换默认的标签文本。 `setLabel()` 和 `setText()` 可以与 `addToHeader()` 一起使用，但由于标题槽占有视觉优先权，除非你在插槽内容中显式包含它，否则标签文本不会显示。
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomheader'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionCustomHeaderView.java']}
height='300px'
/>
<!-- vale on -->

### 自定义图标 {#custom-icon}

展开/折叠指示器默认是一个在开启和关闭状态下均可见的切角。 `setIcon()` 将其替换为任何 [`Icon`](/docs/components/icon) 组件，非常适合品牌图标或当不同的视觉隐喻更适合内容时。传递 `null` 会恢复默认的切角。 `getIcon()` 返回当前设置的图标，如果使用的是默认的切角，则返回 `null`。

```java
// 用加号图标替换默认的切角
panel.setIcon(FeatherIcon.PLUS.create());

// 恢复默认的切角
panel.setIcon(null);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomicon'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionCustomIconView.java']}
height='200px'
/>
<!-- vale on -->

## 嵌套手风琴 {#nested-accordions}

手风琴可以嵌套在其他手风琴面板内，这对于展示层次内容（如分类设置或多级导航）很有用。将内部 `Accordion` 添加到外部 `AccordionPanel` 作为任何其他子组件。保持嵌套尽量浅。通常一到两个层级就足够了。更深的层次结构往往更难以导航，通常意味着内容结构本身需要重新考虑。

```java
AccordionPanel innerA = new AccordionPanel("内部面板 A");
AccordionPanel innerB = new AccordionPanel("内部面板 B");
Accordion innerAccordion = new Accordion(innerA, innerB);

AccordionPanel outer = new AccordionPanel("外部面板");
outer.add(innerAccordion);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionnested'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionNestedView.java']}
height='550px'
/>
<!-- vale on -->

## 事件 {#events}

`AccordionPanel` 在切换生命周期的每个阶段都会触发事件。三种事件类型涵盖不同的时刻，因此根据你的逻辑需要运行的时间选择：

| 事件 | 触发 |
|-------|-------|
| `AccordionPanelToggleEvent` | 在状态改变之前 |
| `AccordionPanelOpenEvent` | 在面板完全展开之后 |
| `AccordionPanelCloseEvent` | 在面板完全关闭之后 |

```java
panel.onToggle(e -> {
    // 在面板状态改变之前触发。
    // e.isOpened() 反映要过渡到的状态，而不是当前状态。
    String direction = e.isOpened() ? "打开" : "关闭";
});

panel.onOpen(e -> {
    // 在面板完全展开后触发 — 适合延迟加载内容。
});

panel.onClose(e -> {
    // 在面板完全关闭后触发 — 适合清理或摘要更新。
});
```

## 样式 {#styling}

<TableBuilder name={['Accordion', 'AccordionPanel']} />
