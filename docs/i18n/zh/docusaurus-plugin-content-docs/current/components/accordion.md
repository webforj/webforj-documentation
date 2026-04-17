---
sidebar_position: 1
title: Accordion
sidebar_class_name: new-content
_i18n_hash: 99f4482faa552334ce209b3f9296f4f5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='name' label="dwc-accordion-panel" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>

`Accordion` 组件提供了一组垂直堆叠的可折叠面板。每个面板都有一个可点击的标题，用于切换其内容的可见性。`AccordionPanel` 可以作为独立的披露部分使用，或作为 `Accordion` 内部的一部分，以协调多个面板的展开和折叠行为。

<!-- INTRO_END -->

:::tip 何时使用手风琴
手风琴适用于常见问题解答、设置页面以及逐步流程，其中一次性显示所有内容会导致布局过于拥挤。如果各部分同样重要，并且用户从同时查看它们中受益，则考虑使用 [标签](/docs/components/tabbedpane)。
:::

## `AccordionPanel` {#accordion-panel}

`AccordionPanel` 是手风琴系统的核心构建块。将标签字符串传递给构造函数以设置标题文本，然后添加子组件以填充正文。面板可以独立工作，而无需任何围绕的 `Accordion` 组，这使其成为一个有用的轻量级披露部件，当您只需要一个可折叠的部分时。也提供无参数构造函数，当您希望在构造后完全配置面板时可以使用。

```java
// 仅标签 - 分别添加正文内容
AccordionPanel panel = new AccordionPanel("部分标题");
panel.add(new Paragraph("正文内容放在这里。"));

// 标签和正文内容直接传递给构造函数
AccordionPanel panel = new AccordionPanel("标题", new Paragraph("正文内容。"));
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionbasic'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionBasicView.java'
height='500px'
/>
<!-- vale on -->

### 开放和关闭 {#opening-and-closing}

可以在任何时间以编程方式控制打开/关闭状态。`isOpened()` 在您需要在决定做什么之前读取当前状态时很有用。例如，您可能会切换面板到相反的状态，或条件性地显示或隐藏用户界面的其他部分。

```java
// 展开面板
panel.open();

// 折叠面板
panel.close();                    

// 如果当前已展开，则返回 true
boolean isOpen = panel.isOpened();
```

使用 `setLabel()` 更新构造后标题文本。`setText()` 是同一操作的别名，因此标签可以与动态数据保持同步：

```java
panel.setLabel("更新的标签");
```

## 手风琴组 {#accordion-groups}

将多个 `AccordionPanel` 实例包装在 `Accordion` 内部会创建一个协调的组。默认情况下，组使用 **单一模式**：打开一个面板会自动折叠所有其他面板，只保持一个部分可见。这个默认设置是有意为之，它让用户专注于一块内容，并防止页面在面板具有大量正文内容时变得视觉上过于拥挤。

面板是独立构建并传递给 `Accordion` 的，因此您可以在分组之前配置每个面板。在同一页面上也可以存在多个独立的 `Accordion` 实例——每个组独立管理其状态，因此在一个组中展开面板对另一个没有影响。

```java
AccordionPanel panel1 = new AccordionPanel("什么是 webforJ？");
AccordionPanel panel2 = new AccordionPanel("组合面板是如何工作的？");
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

多重模式允许任何数量的面板同时保持展开。这在用户需要同时比较多个部分的内容时非常有用，或者当每个面板足够短以至于同时展开多个不会创建杂乱的布局时。

```java
accordion.setMultiple(true);
```

在激活多重模式的情况下，组中的所有面板都可以通过批量方法一次性展开或折叠：

```java
// 展开组中的每个面板
accordion.openAll();

// 折叠组中的每个面板
accordion.closeAll();   
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionmultiple'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionMultipleView.java'
height='500px'
/>
<!-- vale on -->

:::info 单一模式限制
`openAll()` 仅在启用多重模式时可用。在单一模式下调用它没有效果。`closeAll()` 在两种模式下都有效。
:::

<!-- vale off -->
## 禁用状态 {#disabled-state}
<!-- vale on -->

可以禁用单个面板以防止用户交互，同时仍然保持可见。这在加载状态或某些部分条件不可用时非常方便，显示面板结构，但不允许过早交互。已打开的禁用面板仍然保持展开，但无法再单击其标题以折叠。禁用 `Accordion` 组会将禁用状态应用于所有包含的面板，因此您无需单独循环遍历面板。

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

除了标签和基本打开/关闭行为，每个 `AccordionPanel` 还支持其标题内容和展开/折叠图标的更丰富自定义。

### 自定义标题 {#custom-header}

面板的标题默认以纯文本呈现其标签。使用 `addToHeader()` 将该文本替换为任何组件或组件组合，使其可以轻松地在面板标签旁边包含图标、徽章、状态指示器或其他丰富的标记。这在仪表板或设置面板中特别有用，其中每个部分标题需要传达额外的上下文，例如项目数量、警告徽章或完成状态，而无需用户首先展开面板。

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("带图标的自定义标题"));
panel.addToHeader(headerContent);
```

:::info 标签替换
通过 `addToHeader()` 添加的内容会完全替换默认标签文本。`setLabel()` 和 `setText()` 仍然可以与 `addToHeader()` 一起使用，但由于标题插槽具有视觉优先权，标签文本不会显示，除非您在插槽内容中明确包含它。
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomheader'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionCustomHeaderView.java'
height='300px'
/>
<!-- vale on -->

### 自定义图标 {#custom-icon}

展开/折叠指示器默认是一个在打开和关闭状态下都可见的箭头图标。`setIcon()` 将其替换为任何 [`Icon`](/docs/components/icon) 组件，这对于品牌标识或当不同的视觉隐喻更适合内容时很有用。传入 `null` 会恢复默认箭头。`getIcon()` 返回当前设置的图标，或者如果使用的是默认箭头则返回 `null`。

```java
// 用加号图标替换默认箭头
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

## 嵌套手风琴 {#nested-accordions}

手风琴可以嵌套在其他手风琴面板内，这对于表示分层内容（如分类设置或多级导航）非常有用。像任何其他子组件一样，将内部 `Accordion` 添加到外部 `AccordionPanel`。保持嵌套浅层。通常一到两级就足够了。更深的层级往往更难以导航，通常表明内容结构本身需要重新思考。

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
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionNestedView.java'
height='550px'
/>
<!-- vale on -->

## 事件 {#events}

`AccordionPanel` 在切换生命周期的每个阶段触发事件。三种事件类型覆盖不同的时刻，因此根据您的逻辑需要运行的时机进行选择：

| 事件 | 触发时机 |
|-------|-------|
| `AccordionPanelToggleEvent` | 在状态变化之前 |
| `AccordionPanelOpenEvent` | 在面板完全打开后 |
| `AccordionPanelCloseEvent` | 在面板完全关闭后 |

```java
panel.onToggle(e -> {
    // 在面板状态变化之前触发。
    // e.isOpened() 反映即将转换到的状态，而不是当前状态。
    String direction = e.isOpened() ? "打开" : "关闭";
});

panel.onOpen(e -> {
    // 在面板完全打开后触发 — 适合懒加载内容。
});

panel.onClose(e -> {
    // 在面板完全关闭后触发 — 适合清理或摘要更新。
});
```

## 样式 {#styling}

<TableBuilder name={['Accordion', 'AccordionPanel']} />
