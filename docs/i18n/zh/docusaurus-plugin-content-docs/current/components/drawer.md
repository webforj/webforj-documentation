---
title: Drawer
sidebar_position: 35
_i18n_hash: e3b531e5fb7f1554e035f4d05aad8512
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

抽屉是一个容器，可以滑入视口以显示额外的选项和信息。在一个应用程序中可以创建多个抽屉，它们会叠加在一起。

Drawer 组件可以用于许多不同的场景，例如提供可以切换的导航菜单、显示补充或上下文信息的面板，或优化移动设备上的使用。以下示例将展示一个移动应用程序，其中使用了 webforJ AppLayout 组件，在首次加载时在底部显示“欢迎弹出”抽屉。此外，用户可以通过点击汉堡菜单在应用程序中切换导航抽屉组件。

<AppLayoutViewer path='/webforj/drawerwelcome?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java'
cssURL='/css/drawer/drawerWelcome.css'
/>

## 用法 {#usages}

1. **导航菜单**：抽屉组件的一个常见用法是作为导航菜单。它提供了一种节省空间的方式来显示指向应用程序各个部分或页面的链接，特别是在移动或响应式布局中。用户可以打开和关闭抽屉以访问导航选项，而不干扰主要内容区域。

2. **过滤器和侧边栏**：抽屉可以用作显示项目列表的应用程序中的过滤器或侧边栏。用户可以展开抽屉以显示过滤选项、排序控件或与列表项相关的其他信息。这可以使主要内容集中的同时以可访问的方式提供高级功能。

3. **用户个人资料或设置**：您可以使用抽屉显示用户个人资料信息或应用程序设置。这使得这些信息在需要时可以轻松访问，但在不需要时保持隐藏，从而保持干净整洁的界面。用户可以打开抽屉来更新他们的个人资料或调整设置。

4. **通知**：对于具有通知或警报的应用程序，抽屉可以滑入以显示新消息或更新。用户可以快速检查并处理通知，而无需离开当前视图。

<ComponentDemo
path='/webforj/drawerdemo?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerDemoView.java'
height='600px'
/>

## 自定义 {#customization}

各种属性存在，可以对 Drawer 组件的各个属性进行定制。本节概述了这些属性及其修改示例。

## 自动聚焦 {#autofocus}

自动聚焦属性旨在通过在打开抽屉时自动聚焦第一个项目来增强可访问性和可用性。此功能消除了用户手动导航到所需项目的需要，节省了时间和精力。

当抽屉被触发打开时，无论是通过事件、默认或任何其他交互，用户的焦点都会指向抽屉中的第一个项目。这个第一个项目可以是按钮、链接、菜单选项或任何其他可聚焦元素。

:::tip
通过自动聚焦第一个项目，开发人员确保用户可以立即与最相关或最常用的选项进行交互，而无需通过整个抽屉进行标签或滚动。这种行为简化了用户体验，并促进了 UI 内的高效导航。
:::

此属性对于依赖键盘导航或辅助技术（如屏幕阅读器）的人特别有益。它提供了一个在抽屉内的明确起点，并允许用户访问所需的功能，而不需要不必要的手动输入。

<ComponentDemo
path='/webforj/drawerautofocus?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java'
height='600px'
/>

## 标签 {#label}

抽屉标签属性是一个旨在增强可访问性并为用户界面中的抽屉提供描述性上下文的功能。该属性允许开发人员为抽屉分配标签，主要出于可访问性目的，确保屏幕阅读器和其他辅助技术能够准确传达抽屉的目的和内容给用户。

当使用抽屉标签属性时，分配的标签成为抽屉可访问性基础设施的重要组成部分。它使依赖辅助技术的用户能够理解抽屉的功能并更有效地在界面中导航。

通过为抽屉提供标签，开发人员确保屏幕阅读器可以向视觉障碍用户宣布抽屉的目的。这些信息使用户能够做出关于与抽屉交互的知情决定，因为他们可以理解其内容及其在更广泛用户界面中的相关性。

标签属性可以根据应用程序的特定上下文和设计要求进行自定义。开发人员可以灵活地提供简洁且描述性的标签，准确代表抽屉的内容或功能。

## 放置 {#placement}

Drawer UI 组件的放置属性允许开发人员指定抽屉在视口中的位置和对齐方式。该属性提供一系列枚举值，提供在主内容附近确定抽屉出现位置的灵活性。

放置属性的可用枚举值如下：

- **顶部**：此值将抽屉放置在视口的顶部，使其占据最上方区域。

- **顶部居中**：使用此值时，抽屉位于视口顶部部分的中心，水平居中，创建一个平衡的布局。

- **底部**：使用此值时，抽屉位于视口的底部，出现在主要内容下方。

- **底部居中**：此值使抽屉在视口底部水平居中，提供视觉上的平衡组成。

- **左侧**：选择此值，抽屉将被放置在视口的左侧，靠近主要内容。

- **右侧**：使用此值时，抽屉放置在视口的右侧，与主要内容保持接近。

放置属性允许开发人员根据特定的设计和用户体验要求选择最适合的抽屉位置。枚举值提供多种放置选项，以适应不同的接口布局和视觉层次结构。

通过利用放置属性，开发人员可以创建直观和高效的用户界面。例如，将抽屉放在左侧或右侧可以快速访问额外的功能或导航选项，而顶部或底部放置则非常适合上下文信息或补充内容。

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## 样式 {#styling}

<TableBuilder name="Drawer" />

## 最佳实践 {#best-practices}

为确保在使用 `Drawer` 组件时获得最佳用户体验，请考虑以下最佳实践：

1. **放置**：决定抽屉是应该从左侧、右侧、顶部还是底部滑入，基于您的应用程序布局和用户体验的考虑。考虑用户偏好和设计惯例。

2. **可访问性**：特别关注可访问性。确保用户可以使用键盘控件打开和关闭抽屉，并且屏幕阅读器可以宣布其存在和状态。根据需要提供 ARIA 角色和标签。

3. **滑动手势**：在支持触控的设备上，支持滑动手势以打开和关闭抽屉。这是一种直观的用户交互方式。
