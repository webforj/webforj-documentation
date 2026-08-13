---
sidebar_position: 1
title: Accordion
description: >-
  Group collapsible panels with the Accordion and AccordionPanel components to
  toggle visibility and coordinate expand or collapse behavior.
_i18n_hash: b11e2d2ef8854f757454635c984da1d6
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='name' label="dwc-accordion-panel" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>

`Accordion` 组件提供了一组垂直堆叠的可折叠面板。每个面板都有一个可点击的头部，切换其内容的可见性。`AccordionPanel` 可以作为独立的披露部分使用，或在 `Accordion` 内部分组，以协调多个面板的展开和折叠行为。

<!-- INTRO_END -->

:::tip 何时使用手风琴
手风琴适用于常见问题解答、设置页面和逐步流程，其中一次性显示所有内容会导致布局难以承受。如果各部分同样重要且用户从同时查看中受益，可以考虑 [选项卡](/docs/components/tabbedpane) 替代。
:::

## `AccordionPanel` {#accordion-panel}

`AccordionPanel` 是手风琴系统的核心构建块。将标签字符串传递给构造函数以设置头部文本，然后添加子组件以填充主体。面板可以独立工作，而无需任何周围的 `Accordion` 组，使其成为一个轻量级的披露控件，当你只需要一个可折叠部分时非常有用。不带参数的构造函数也可用，当您希望在构造后完全配置该面板时适用。

```java
// 仅标签 - 单独添加主体内容
AccordionPanel panel = new AccordionPanel("部分标题");
panel.add(new Paragraph("主体内容在此处。"));

// 标签和主体内容直接传递给构造函数
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

可以在任何时间以编程方式控制打开/关闭状态。在决定该做什么之前，如果需要读取当前状态，`isOpened()` 非常有用。例如，您可能会切换面板到相反状态或条件性地显示或隐藏用户界面的其他部分。

```java
// 展开面板
panel.open();

// 折叠面板
panel.close();

// 如果当前已展开则返回 true
boolean isOpen = panel.isOpened();
```

使用 `setLabel()` 更新构造后的头部文本。`setText()` 是同一操作的别名，因此标签可以与动态数据保持同步：

```java
panel.setLabel("更新的标签");
```

## 手风琴组 {#accordion-groups}

将多个 `AccordionPanel` 实例包装在 `Accordion` 中会创建一个协调的组。默认情况下，该组使用 **单模式**：打开一个面板会自动折叠所有其他面板，同时只保持一个部分可见。这个默认设置是故意的，它让用户专注于一块内容，并防止页面在面板具有大量主体内容时变得视觉上过于拥挤。

面板是独立构造的并传递给 `Accordion`，因此您可以在将它们分组之前配置每一个。多个独立的 `Accordion` 实例也可以存在于同一页面上——每个组独立管理自己的状态，因此在一个组中展开一个面板不会对另一个产生影响。

```java
AccordionPanel panel1 = new AccordionPanel("webforJ 是什么？");
AccordionPanel panel2 = new AccordionPanel("分组面板是如何工作的？");
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

### 多模式 {#multiple-mode}

多模式允许任意数量的面板同时保持展开。当用户需要同时比较多个部分的内容时，或者当每个面板足够短以至于一次展开多个不会造成杂乱的布局时，这非常有用。

```java
accordion.setMultiple(true);
```

在激活多模式时，组中的所有面板都可以使用批量方法一次性展开或折叠：

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

:::info 单模式限制
`openAll()` 仅在启用多模式时可用。在单模式下调用它没有效果。`closeAll()` 在两种模式下均有效。
:::

<!-- vale off -->
## 禁用状态 {#disabled-state}
<!-- vale on -->

可以禁用单个面板，以防止用户交互，同时仍然保持可见。这在加载状态或某些部分条件不可用时非常有用，显示面板结构而不允许过早的交互。已经打开的禁用面板仍保持展开状态，但其标题不再可单击以折叠它。禁用 `Accordion` 组会将禁用状态同时应用于所有包含的面板，因此您无需逐个循环面板。

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

除了标签和基本的开关行为，每个 `AccordionPanel` 支持更丰富的自定义，包括其头部内容和展开/折叠图标。

### 自定义头部 {#custom-header}

面板的头部默认呈现为普通文本。使用 `addToHeader()` 用任何组件或组件组合替换该文本，使其容易在面板标签旁边包含图标、徽章、状态指示器或其他丰富标记。这在仪表板或设置面板中尤其有用，其中每个部分的标题需要在一瞥中传达额外的上下文信息，例如项目计数、警告徽章或完成状态，而无须用户先展开面板。

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("带图标的自定义标题"));
panel.addToHeader(headerContent);
```

:::info 标签替换
通过 `addToHeader()` 添加的内容完全替代默认标签文本。`setLabel()` 和 `setText()` 继续与 `addToHeader()` 一起工作，但由于头部位置的视觉优先级，标签文本不会显示，除非您在插槽内容中明确包含它。
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomheader'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionCustomHeaderView.java']}
height='300px'
/>
<!-- vale on -->

### 自定义图标 {#custom-icon}

展开/折叠指示器默认是一个在打开和关闭状态下均可见的箭头。`setIcon()` 用任何 [`Icon`](/docs/components/icon) 组件替换它，这在品牌图标或当不同的视觉隐喻更符合内容时非常有用。传递 `null` 会恢复默认箭头。`getIcon()` 返回当前设置的图标，如果使用默认箭头则返回 `null`。

```java
// 用加号图标替换默认箭头
panel.setIcon(FeatherIcon.PLUS.create());

// 恢复默认箭头
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

手风琴可以嵌套在其他手风琴面板中，这对于表示层次结构内容（例如分类设置或多级导航）非常有用。将内部 `Accordion` 添加到外部 `AccordionPanel` 作为其他子组件。保持嵌套简单。通常一到两个层级就足够了。较深的层级往往更难以导航，并且通常意味着内容结构本身需要重新思考。

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

`AccordionPanel` 在每个切换生命周期阶段触发事件。这三种事件类型涵盖了不同的时刻，因此根据您的逻辑需要运行的时机进行选择：

| 事件 | 触发 |
|-------|-------|
| `AccordionPanelToggleEvent` | 在状态改变之前 |
| `AccordionPanelOpenEvent` | 在面板完全打开后 |
| `AccordionPanelCloseEvent` | 在面板完全关闭后 |

```java
panel.onToggle(e -> {
    // 在面板状态改变之前触发。
    // e.isOpened() 反映正在过渡到的状态，而不是当前状态。
    String direction = e.isOpened() ? "正在打开" : "正在关闭";
});

panel.onOpen(e -> {
    // 在面板完全打开后触发——适合懒加载内容。
});

panel.onClose(e -> {
    // 在面板完全关闭后触发——适合清理或更新汇总。
});
```

## 样式 {#styling}

<TableBuilder name={['Accordion', 'AccordionPanel']} />
