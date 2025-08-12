---
title: Drawer
sidebar_position: 35
_i18n_hash: 73da264dca1e3f8cfd58b697e3e9d0dc
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

抽屉是一个容器，滑入视口以显示附加选项和信息。一个应用程序可以创建多个抽屉，它们将按层叠加在一起。

抽屉组件可以在许多不同的场景中使用，例如提供一个可以切换的导航菜单，一个显示补充或上下文信息的面板，或者优化移动设备上的使用。以下示例将展示一个使用webforJ AppLayout组件的移动应用程序，并在首次加载时在底部显示“欢迎弹出”抽屉。此外，用户通过点击汉堡菜单可以在应用程序中切换导航抽屉组件。

<AppLayoutViewer path='/webforj/drawerwelcome?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java'
cssURL='/css/drawer/drawerWelcome.css'
/>

## 用法 {#usages}

1. **导航菜单**：抽屉组件的一个常见用法是作为导航菜单。它提供了一种空间高效的方式来显示链接到应用程序各个部分或页面的选项，特别是在移动或响应式布局中。用户可以打开和关闭抽屉来访问导航选项，而不会使主内容区域显得杂乱。

2. **过滤器和侧边栏**：抽屉可以用作显示项目列表的应用程序中的过滤器或侧边栏。用户可以展开抽屉以显示过滤选项、排序控件或与列表项相关的附加信息。这使得主内容可以集中于列表，同时以便捷的方式提供高级功能。

3. **用户个人资料或设置**：您可以使用抽屉显示用户个人资料信息或应用程序设置。这样可以保持信息的可及性，同时在不需要时将其隐藏，维护一个整洁的用户界面。用户可以打开抽屉来更新其个人资料或调整设置。

4. **通知**：对于有通知或警报的应用，抽屉可以滑入以显示新消息或更新。用户可以快速检查和关闭通知，而不离开当前视图。

<ComponentDemo
path='/webforj/drawerdemo?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerDemoView.java'
height='600px'
/>

## 定制 {#customization}

存在各种属性，允许定制抽屉组件的不同属性。本节概述了这些属性及其修改示例。

## 自动聚焦 {#autofocus}

自动聚焦属性旨在通过在抽屉打开时自动聚焦于第一个项目来增强可访问性和可用性。此功能消除了用户手动导航到所需项目的需要，节省了时间和精力。

当抽屉被触发打开时，无论是通过事件、默认或其他任何交互，用户的焦点会被引导到抽屉中的第一个项目。这个第一个项目可以是一个按钮、一个链接、一个菜单选项或任何其他可聚焦的元素。

:::tip
通过自动聚焦第一个项目，开发者确保用户可以立即与最相关或最常用的选项进行交互，而不必通过整个抽屉进行选项切换或滚动。这种行为简化了用户体验，并促进了用户在UI内的高效导航。
:::

该属性对那些依赖键盘导航或辅助技术（如屏幕阅读器）的人尤其有益。它提供了抽屉内的明确起点，使用户可以在不进行不必要手动输入的情况下访问所需功能。

<ComponentDemo
path='/webforj/drawerautofocus?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java'
height='600px'
/>

## 标签 {#label}

抽屉标签属性是一个旨在增强可访问性并为用户界面中的抽屉提供描述性上下文的功能。这个属性允许开发人员为抽屉分配一个标签，主要出于可访问性目的，确保屏幕阅读器和其他辅助技术可以准确传达抽屉的目的和内容。

当使用抽屉标签属性时，所分配的标签将成为抽屉可访问性基础设施的一个重要部分。它使依赖辅助技术的用户能够理解抽屉的功能，能够更有效地在界面之间进行导航。

通过为抽屉提供标签，开发者确保屏幕阅读器向视力受损的用户宣告抽屉的目的。这些信息使得个体能够对与抽屉的互动做出明智的决定，因为他们可以理解其内容和在更广泛用户界面中的相关性。

标签属性可以根据应用的特定上下文和设计要求进行定制。开发者可以灵活提供简洁和描述性标签，准确表示抽屉的内容或功能。

## 放置 {#placement}

抽屉UI组件的放置属性允许开发人员指定抽屉在视口中的位置和对齐。这一属性提供了一系列枚举值，可灵活确定抽屉相对于主内容的出现位置。

放置属性的可用枚举值如下：

- **顶部**：此值将抽屉放置在视口的顶部，让它占据最上方区域。

- **顶部中心**：使用此值，抽屉位于视口顶端的中心。它在水平方向上居中，创建出平衡的布局。

- **底部**：使用此值时，抽屉位于视口的底部，处于主内容之下。

- **底部中心**：此值在视口底部水平居中抽屉。提供了一种视觉平衡的构图。

- **左侧**：选择此值会使抽屉位于视口的左侧，紧邻主内容。

- **右侧**：使用此值，抽屉位于视口的右侧，保持与主内容的接近。

放置属性允许开发者根据特定的设计和用户体验要求选择最合适的抽屉位置。枚举值提供多种放置选项，以适应不同的界面布局和视觉层级。

通过利用放置属性，开发者可以创建直观且高效的用户界面。例如，将抽屉放在左侧或右侧使额外的功能或导航选项快速可及，而顶部或底部的放置则适合上下文信息或补充内容。

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## 样式 {#styling}

<TableBuilder name="Drawer" />

## 最佳实践 {#best-practices}

为了确保在使用`Drawer`组件时获得最佳的用户体验，请考虑以下最佳实践：

1. **放置**：根据您的应用程序布局和用户体验考虑，决定抽屉应该从左、右、顶部或底部滑入。同时考虑用户偏好和设计规范。

2. **可访问性**：特别注意可访问性。确保用户可以使用键盘控制打开和关闭抽屉，并且屏幕阅读器可以宣告其存在及状态。根据需要提供ARIA角色和标签。

3. **滑动手势**：在支持触控的设备上，支持滑动手势以打开和关闭抽屉。这是用户与其交互的一种直观方式。
