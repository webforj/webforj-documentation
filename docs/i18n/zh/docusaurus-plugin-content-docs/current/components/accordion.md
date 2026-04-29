---
sidebar_position: 1
title: Accordion
sidebar_class_name: new-content
_i18n_hash: 2bf90130b3a767840e2604045504ee91
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='name' label="dwc-accordion-panel" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>

`Accordion` 组件提供了一组垂直堆叠的可折叠面板。每个面板都有一个可点击的标题，可以切换其主体内容的可见性。`AccordionPanel` 可以作为独立的披露部分使用，或组合在 `Accordion` 内以协调多个面板的展开和折叠行为。

<!-- INTRO_END -->

:::tip 使用 Accordion 的时机
Accordion 适用于常见问题解答、设置页面和分步流程，其中一次性显示所有内容会导致布局过于复杂。如果各部分同样重要，用户受益于同时查看它们，则可以考虑使用 [标签页](/docs/components/tabbedpane)。
:::

## `AccordionPanel` {#accordion-panel}

`AccordionPanel` 是 accordion 系统的核心构建块。将标签字符串传递给构造函数以设置标题文本，然后添加子组件以填充主体。面板在没有任何周围 `Accordion` 组的情况下也能独立工作，使其成为一个轻量级的披露小部件，当您只需要一个单一的可折叠部分时非常有用。当您希望在构造后完全配置面板时，也可以使用不带参数的构造函数。

```java
// 仅标签 - 单独添加主体内容
AccordionPanel panel = new AccordionPanel("部分标题");
panel.add(new Paragraph("主体内容在此。"));

// 在构造函数中直接传入标签和主体内容
AccordionPanel panel = new AccordionPanel("标题", new Paragraph("主体内容。"));
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionbasic'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionBasicView.java'
height='550px'
/>
<!-- vale on -->

### 开启和关闭 {#opening-and-closing}

可以在任何时候以编程方式控制开启/关闭状态。当您需要在决定采取何种操作之前读取当前状态时，`isOpened()` 很有用。例如，您可能会切换面板到相反的状态，或根据条件显示或隐藏 UI 的其他部分。

```java
// 展开面板
panel.open();

// 收起面板
panel.close();                    

// 如果当前已展开，则返回 true
boolean isOpen = panel.isOpened();
```

使用 `setLabel()` 在构造后更新标题文本。`setText()` 是相同操作的别名，因此可以保持标签与动态数据同步：

```java
panel.setLabel("已更新标签");
```

## Accordion 组 {#accordion-groups}

将多个 `AccordionPanel` 实例包装在一个 `Accordion` 内创建一个协调组。默认情况下，该组使用 **单一模式**：打开一个面板会自动折叠所有其他面板，每次只保留一个部分可见。这个默认设置是故意的，它使用户专注于一个内容片段，并防止页面在面板具有大量主体内容时变得视觉上令人不堪重负。

面板是独立构建并传递给 `Accordion` 的，因此您可以在将它们分组之前配置每个面板。多个独立的 `Accordion` 实例也可以在同一页面上存在——每个组单独管理其自己的状态，因此在一个组中扩展面板不会影响另一个组。

```java
AccordionPanel panel1 = new AccordionPanel("什么是 webforJ？");
AccordionPanel panel2 = new AccordionPanel("分组面板如何工作？");
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

多重模式允许任何数量的面板同时展开。当用户需要同时比较多个部分的内容时，这很有用，或者当每个面板足够短时，展开多个面板不会导致布局混乱。

```java
accordion.setMultiple(true);
```

在启用多重模式的情况下，可以使用批量方法同时展开或折叠组中的所有面板：

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
height='575px'
/>
<!-- vale on -->

:::info 单一模式限制
`openAll()` 仅在启用多重模式时可用。在单一模式下调用它没有效果。`closeAll()` 在两种模式下都有效。
:::

<!-- vale off -->
## 禁用状态 {#disabled-state}
<!-- vale on -->

可以禁用单个面板以防止用户交互，同时仍然保持可见。这在加载状态期间或某些部分条件不可用时非常方便，显示面板结构，但不允许过早交互。已经打开的禁用面板保持展开，但其标题不再可点击以折叠。禁用 `Accordion` 组会将禁用状态应用于所有包含的面板，因此您无需逐个遍历面板。

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
height='650px'
/>
<!-- vale on -->

## 自定义面板 {#customizing-panels}

除了标签和基本的开放/关闭行为外，每个 `AccordionPanel` 支持对其标题内容和展开/折叠图标的更丰富的自定义。

### 自定义标题 {#custom-header}

面板的标题默认将其标签作为普通文本呈现。使用 `addToHeader()` 将该文本替换为任何组件或组件组合，使其能够轻松包括图标、徽章、状态指示器或其他丰富的标记与面板标签并排显示。这在仪表盘或设置面板中尤其有用，其中每个部分标题需要在一目了然的情况下传达额外上下文，例如项目计数、警告徽章或完成状态，而无需用户首先展开面板。

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("带图标的自定义标题"));
panel.addToHeader(headerContent);
```

:::info 标签替换
通过 `addToHeader()` 添加的内容会完全替换默认的标签文本。`setLabel()` 和 `setText()` 可以与 `addToHeader()` 一起使用，但由于标题插槽具有视觉优先权，因此标签文本不会显示，除非您在插槽内容中显式包含它。
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomheader'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionCustomHeaderView.java'
height='300px'
/>
<!-- vale on -->

### 自定义图标 {#custom-icon}

展开/折叠指示器默认为一个在打开和关闭状态下均可见的箭头。`setIcon()` 可以将其替换为任何 [`Icon`](/docs/components/icon) 组件，这对于品牌图标或在视觉隐喻更适合内容时很有用。传递 `null` 将恢复默认箭头。`getIcon()` 返回当前设置的图标，如果使用的是默认箭头，则返回 `null`。

```java
// 将默认箭头替换为加号图标
panel.setIcon(FeatherIcon.PLUS.create());

// 恢复默认箭头
panel.setIcon(null);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomicon'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionCustomIconView.java'
height='200px'
/>
<!-- vale on -->

## 嵌套 Accordion {#nested-accordions}

Accordion 可以嵌套在其他 accordion 面板内，这对表示分级内容（例如分类设置或多级导航）很有用。将内层 `Accordion` 添加到外层 `AccordionPanel` 像任何其他子组件一样。保持嵌套浅层。一到两层通常就足够了。更深的层次往往更难以导航，并且通常意味着内容结构本身需要重新考虑。

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

`AccordionPanel` 在切换生命周期的每个阶段都会触发事件。这三种事件类型涵盖不同的时刻，因此根据您的逻辑需要运行的时机进行选择：

| 事件 | 触发时机 |
|-------|-------|
| `AccordionPanelToggleEvent` | 在状态更改之前 |
| `AccordionPanelOpenEvent` | 在面板完全展开后 |
| `AccordionPanelCloseEvent` | 在面板完全关闭后 |

```java
panel.onToggle(e -> {
    // 在面板状态改变之前触发。
    // e.isOpened() 反映正在转换到的状态，而不是当前状态。
    String direction = e.isOpened() ? "打开中" : "关闭中";
});

panel.onOpen(e -> {
    // 在面板完全打开后触发 - 适合延迟加载内容。
});

panel.onClose(e -> {
    // 在面板完全关闭后触发 - 适合清理或摘要更新。
});
```

## 样式 {#styling}

<TableBuilder name={['Accordion', 'AccordionPanel']} />
