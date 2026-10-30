---
sidebar_position: 1
title: Accordion
description: >-
  Group collapsible panels with the Accordion and AccordionPanel components to
  toggle visibility and coordinate expand or collapse behavior.
_i18n_hash: 6537c2d24bfc99dd866f27277ed4f197
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>

`Accordion` 组件提供了一组垂直堆叠的可折叠面板。每个面板都有一个可点击的标题，用于切换其内容的可见性。`AccordionPanel` 可以作为独立的披露部分使用，或组装在一个 `Accordion` 内，以协调多个面板之间的展开和收起行为。

<!-- INTRO_END -->

:::tip 何时使用手风琴
手风琴适用于常见问答、设置页面和逐步流程，在这些情况下，一次呈现所有内容会造成视觉上的压倒性。如果各个部分同等重要，并且用户从同时查看它们中受益，那么考虑使用 [选项卡](/docs/components/tabbedpane)。
:::

## `AccordionPanel` <DocChip chip='name' label="dwc-accordion-panel" /> {#accordion-panel}

`AccordionPanel` 是手风琴系统的核心构建模块。将标签字符串传递给构造函数以设置标题文本，然后添加子组件以填充主体。当您只需要一个可折叠的部分时，面板可以独立工作，而不需要任何围绕的 `Accordion` 组，成为一个有用的轻量级披露小部件。无参数构造函数也可用，当您希望在构造后完全配置面板时使用。

```java
// 仅标签 - 单独添加主体内容
AccordionPanel panel = new AccordionPanel("部分标题");
panel.add(new Paragraph("主体内容在此."));

// 标签和主体内容直接传递给构造函数
AccordionPanel panel = new AccordionPanel("标题", new Paragraph("主体内容."));
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionbasic'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionBasicView.java']}
height='550px'
/>
<!-- vale on -->

### 打开和关闭 {#opening-and-closing}

可以随时程序化控制打开/关闭状态。`isOpened()` 在您需要在决定做什么之前读取当前状态时非常有用。例如，您可能会切换面板到相反状态，或者有条件地显示或隐藏用户界面的其他部分。

```java
// 展开面板
panel.open();

// 收起面板
panel.close();

// 如果当前展开则返回true
boolean isOpen = panel.isOpened();
```

使用 `setLabel()` 在构造后更新标题文本。`setText()` 是同一操作的别名，因此标签可以与动态数据保持同步：

```java
panel.setLabel("更新的标签");
```

## 手风琴组 {#accordion-groups}

将多个 `AccordionPanel` 实例包装在一个 `Accordion` 内创建一个协调组。默认情况下，组使用 **单模式**：打开一个面板会自动收起所有其他面板，只保留一个部分可见。这个默认设置是故意的，它让用户专注于一块内容，并防止页面在面板具有大量主体内容时变得视觉上过于拥挤。

面板是独立构造并传递给 `Accordion` 的，因此您可以在将它们分组之前配置每个面板。多个独立的 `Accordion` 实例也可以存在于同一页面上——每个组独立管理自己的状态，因此在一个组中展开面板不会影响另一个组。

```java
AccordionPanel panel1 = new AccordionPanel("什么是webforJ?");
AccordionPanel panel2 = new AccordionPanel("分组面板是如何工作的?");
AccordionPanel panel3 = new AccordionPanel("我可以有多个组吗?");

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

多重模式允许任何数量的面板同时展开。当用户需要同时比较多个部分的内容时，这非常有用，或者当每个面板足够短时，展开多个面板不会造成布局混乱。

```java
accordion.setMultiple(true);
```

在多重模式激活的情况下，可以使用批量方法同时展开或收起组中的所有面板：

```java
// 展开组中的每个面板
accordion.openAll();

// 收起组中的每个面板
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
`openAll()` 仅在启用多重模式时可用。在单模式下调用它没有效果。`closeAll()` 在两种模式下均可用。
:::

<!-- vale off -->
## 禁用状态 {#disabled-state}
<!-- vale on -->

可以禁用单个面板以防止用户交互，同时仍然可见。这在加载状态或某些部分有条件不可用时很有用，显示面板结构而不允许过早的交互。已经打开的禁用面板保持展开，但其标题不再可点击以收起。禁用 `Accordion` 组会将禁用状态应用于所有包含的面板，因此您无需单独循环面板。

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

除了标签和基本的打开/关闭行为，每个 `AccordionPanel` 支持对其标题内容和展开/折叠图标的更丰富的自定义。

### 自定义标题 {#custom-header}

面板的标题默认将标签呈现为普通文本。使用 `addToHeader()` 将该文本替换为任何组件或组件组合，使其可以轻松包含图标、徽章、状态指示器或其他丰富的标记，伴随面板标签。这在仪表板或设置面板中特别有用，因为每个部分标题需要在一瞥中传达额外的上下文，例如项目数量、警告徽章或完成状态，而无需用户首先展开面板。

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("带图标的自定义标题"));
panel.addToHeader(headerContent);
```

:::info 标签替换
通过 `addToHeader()` 添加的内容会完全替换默认的标签文本。`setLabel()` 和 `setText()` 仍然可以与 `addToHeader()` 一起使用，但由于标题槽具有视觉优先权，标签文本不会显示，除非您在插槽内容中显式包含它。
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomheader'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionCustomHeaderView.java']}
height='300px'
/>
<!-- vale on -->

### 自定义图标 {#custom-icon}

展开/折叠指示符默认为一个在打开和关闭状态下均可见的箭头。`setIcon()` 用任何 [`Icon`](/docs/components/icon) 组件替换它，这对品牌图标或当不同的视觉隐喻更适合内容时非常有用。传递 `null` 将恢复默认箭头。`getIcon()` 返回当前设置的图标，如果使用默认的箭头，则返回 `null`。

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

手风琴可以嵌套在其他手风琴面板内部，这对于表示分层内容（如分类设置或多级导航）非常有用。向外层 `AccordionPanel` 添加一个内层 `Accordion`，就像添加任何其他子组件一样。保持嵌套的浅层。一到两个级别通常就足够了。更深的层次往往更难导航，并且通常表明内容结构本身需要重新考虑。

```java
AccordionPanel innerA = new AccordionPanel("内层面板A");
AccordionPanel innerB = new AccordionPanel("内层面板B");
Accordion innerAccordion = new Accordion(innerA, innerB);

AccordionPanel outer = new AccordionPanel("外层面板");
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

`AccordionPanel` 在切换生命周期的每个阶段都会触发事件。这三种事件类型涵盖不同的时刻，因此根据逻辑需要运行的时机进行选择：

| 事件 | 触发时机 |
|-------|-------|
| `AccordionPanelToggleEvent` | 在状态更改之前 |
| `AccordionPanelOpenEvent` | 在面板完全打开后 |
| `AccordionPanelCloseEvent` | 在面板完全关闭后 |

```java
panel.onToggle(e -> {
    // 在面板更改状态之前触发。
    // e.isOpened() 反映正在转换到的状态，而不是当前状态。
    String direction = e.isOpened() ? "打开中" : "关闭中";
});

panel.onOpen(e -> {
    // 在面板完全打开后触发——适用于懒加载内容。
});

panel.onClose(e -> {
    // 在面板完全关闭后触发——适用于清理或摘要更新。
});
```

## 样式 {#styling}

<TableBuilder name={['Accordion', 'AccordionPanel']} />
