---
title: UI Components
sidebar_position: 85
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Browse the webforJ UI component catalog covering layouts, data entry,
  navigation, feedback, and visualization components.
_i18n_hash: 5533b760c7585442c917bc506c2dd763
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<Head>
  <title>UI 组件 | 用户界面应用构建组件</title>
</Head>

在 webforJ 中，应用程序是使用称为组件的模块化单元创建的，这些单元便于快速高效地进行用户界面开发。该框架提供一系列基本组件，如按钮、输入元素和布局容器。掌握基础知识后，您可以查阅 [JavaDocs](https://javadoc.io/doc/com.webforj) 以获取所有组件及其功能的详细概述。

## 布局 {#layouts}

布局组件为结构化用户界面提供基础，帮助开发人员高效地组织内容。这些组件提供多种控制子组件排列的方式，无论是简单还是复杂的布局。

以下布局组件设计用于处理各种用例，从响应式设计到高级内容管理  

<GalleryGrid>
  <GalleryCard header="AppLayout" href="app-layout" image="/img/components/light/AppLayout.webp" imageDark="/img/components/dark/AppLayout.webp">
    <p>一个容器组件，为顶级应用导航和内容组织提供结构化布局。</p>
  </GalleryCard>

  <GalleryCard header="Toolbar" href="toolbar" image="/img/components/light/Toolbar.webp" imageDark="/img/components/dark/Toolbar.webp">
    <p>一个水平容器组件，包含一组操作按钮、图标或其他控件，通常用于执行与当前上下文相关的任务。</p>
  </GalleryCard>

  <GalleryCard header="FlexLayout" href="flex-layout" image="/img/components/light/FlexLayout.webp" imageDark="/img/components/dark/FlexLayout.webp">
    <p>一个使用弹性盒模型 (flexbox) 规则进行子组件排列的布局组件，适用于响应式设计和对齐。</p>
  </GalleryCard>

  <GalleryCard header="ColumnsLayout" href="columns-layout" image="/img/components/light/ColumnsLayout.webp" imageDark="/img/components/dark/ColumnsLayout.webp">
    <p>一个将子组件排列成多个垂直列的布局组件，适合创建表单和网格状结构。</p>
  </GalleryCard>

  <GalleryCard header="Splitter" href="splitter" image="/img/components/light/Splitter.webp" imageDark="/img/components/dark/Splitter.webp">
    <p>一个布局组件，将可用空间在两个子组件之间分隔，允许用户通过拖动分隔条来调整其大小。</p>
  </GalleryCard>

  <GalleryCard header="Drawer" href="drawer" image="/img/components/light/Drawer.webp" imageDark="/img/components/dark/Drawer.webp">
    <p>一个滑动面板组件，通常用于侧边导航或容纳可以显示或隐藏的附加内容。</p>
  </GalleryCard>

  <GalleryCard header="Dialog" href="dialog" image="/img/components/light/Dialog.webp" imageDark="/img/components/dark/Dialog.webp">
    <p>一个模态窗口组件，覆盖内容以显示重要信息或提示用户交互，通常需要用户操作才能关闭。</p>
  </GalleryCard>

  <GalleryCard header="Login" href="login" image="/img/components/light/Login.webp" imageDark="/img/components/dark/Login.webp">
    <p>一个提供预构建用户身份验证界面的组件，通常包括用户名和密码字段以及提交按钮。</p>
  </GalleryCard>

  <GalleryCard header="Accordion" href="accordion" image="/img/components/light/Accordion.webp" imageDark="/img/components/dark/Accordion.webp">
    <p>一组垂直堆叠的可折叠面板，每个面板都有一个可点击的标题，用于切换其正文内容的可见性。</p>
  </GalleryCard>

  <GalleryCard header="TabbedPane" href="tabbedpane" image="/img/components/light/TabbedPane.webp" imageDark="/img/components/dark/TabbedPane.webp">
    <p>一个容器组件，将内容组织为多个选项卡，允许用户在不同的视图或部分之间切换。</p>
  </GalleryCard>
</GalleryGrid>

## 数据输入 {#data-entry}

数据输入组件提供了捕获用户输入和管理应用程序内部交互的基本工具。这些组件功能多样，使构建交互式表单和收集各种数据类型变得简单。

<GalleryGrid>
  <GalleryCard header="TextField" href="fields/textfield" image="/img/components/light/TextField.webp" imageDark="/img/components/dark/TextField.webp">
    <p>一个用于输入和编辑文本数据的单行输入组件。</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TextField" href="fields/masked/textfield" image="/img/components/light/MaskedTextField.webp" imageDark="/img/components/dark/MaskedTextField.webp">
    <p>一个文本输入组件，限制用户输入特定格式或模式，通常用于像电话号码、日期或信用卡号码这样的字段。</p>
  </GalleryCard>

  <GalleryCard header="NumberField" href="fields/numberfield" image="/img/components/light/NumberField.webp" imageDark="/img/components/dark/NumberField.webp">
    <p>一个提供默认浏览器输入框组件，用于输入数值，内置增减值的控件。</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>NumberField" href="fields/masked/numberfield" image="/img/components/light/MaskedNumberField.webp" imageDark="/img/components/dark/MaskedNumberField.webp">
    <p>一个数字输入组件，限制用户输入特定数字格式或模式，确保有效的数字输入，例如货币、百分比或其他格式化数字。</p>
  </GalleryCard>

  <GalleryCard header="PasswordField" href="fields/passwordfield" image="/img/components/light/PasswordField.webp" imageDark="/img/components/dark/PasswordField.webp">
    <p>一个用于安全输入和屏蔽密码数据的单行输入组件。</p>
  </GalleryCard>

  <GalleryCard header="DateField" href="fields/datefield" image="/img/components/light/DateField.webp" imageDark="/img/components/dark/DateField.webp">
    <p>一个提供默认浏览器日期选择器的组件，通过输入字段选择日期。</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>DateField" href="fields/masked/datefield" image="/img/components/light/MaskedDateField.webp" imageDark="/img/components/dark/MaskedDateField.webp">
    <p>一个日期输入组件，强制执行特定的日期格式或模式，确保用户输入有效日期符合定义的掩码。</p>
  </GalleryCard>

  <GalleryCard header="TimeField" href="fields/timefield" image="/img/components/light/TimeField.webp" imageDark="/img/components/dark/TimeField.webp">
    <p>一个提供默认浏览器时间选择器的组件，通过输入字段选择时间值。</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TimeField" href="fields/masked/timefield" image="/img/components/light/MaskedTimeField.webp" imageDark="/img/components/dark/MaskedTimeField.webp">
    <p>一个时间输入组件，强制执行特定的时间格式或模式，确保用户输入有效时间符合定义的掩码。</p>
  </GalleryCard>

  <GalleryCard header="DateTimeField" href="fields/datetimefield" image="/img/components/light/DateTimeField.webp" imageDark="/img/components/dark/DateTimeField.webp">
    <p>一个提供默认浏览器的日期和时间选择器的组件，通过单个输入字段选择日期和时间。</p>
  </GalleryCard>

  <GalleryCard header="ColorField" href="fields/colorfield" image="/img/components/light/ColorField.webp" imageDark="/img/components/dark/ColorField.webp">
    <p>一个提供默认浏览器颜色选择器的组件，允许用户从输入字段中选择颜色。</p>
  </GalleryCard>

  <GalleryCard header="TextArea" href="textarea" image="/img/components/light/TextArea.webp" imageDark="/img/components/dark/TextArea.webp">
    <p>一个多行文本输入组件，允许用户输入或编辑较大的文本块。</p>
  </GalleryCard>

  <GalleryCard header="CheckBox" href="checkbox" image="/img/components/light/CheckBox.webp" imageDark="/img/components/dark/CheckBox.webp">
    <p>一个表示二元选项的组件，允许用户在选中（真）或未选中（假）状态之间切换。</p>
  </GalleryCard>

  <GalleryCard header="RadioButton" href="radiobutton" image="/img/components/light/RadioButton.webp" imageDark="/img/components/dark/RadioButton.webp">
    <p>一个允许用户从一组互斥选项中选择单一选项的组件。</p>
  </GalleryCard>

  <GalleryCard header="Switch" href="radiobutton#switches" image="/img/components/light/Switch.webp" imageDark="/img/components/dark/Switch.webp">
    <p>一个切换组件，允许用户在两个状态之间切换，例如开/关或真/假，使用滑动操作。</p>
  </GalleryCard>

  <GalleryCard header="ChoiceBox" href="lists/choicebox" image="/img/components/light/ChoiceBox.webp" imageDark="/img/components/dark/ChoiceBox.webp">
    <p>一个提供下拉列表的组件，允许用户从预定义选项中选择一个选项。</p>
  </GalleryCard>

  <GalleryCard header="ComboBox" href="lists/combobox" image="/img/components/light/ComboBox.webp" imageDark="/img/components/dark/ComboBox.webp">
    <p>一个将下拉列表与可编辑文本输入相结合的组件，允许用户从列表中选择一个选项或输入自定义值。</p>
  </GalleryCard>

  <GalleryCard header="ListBox" href="lists/listbox" image="/img/components/light/ListBox.webp" imageDark="/img/components/dark/ListBox.webp">
    <p>一个显示可滚动选项列表的组件，允许用户从列表中选择一个或多个项目。</p>
  </GalleryCard>

  <GalleryCard header="Upload" href="upload" image="/img/components/light/Upload.webp" imageDark="/img/components/dark/Upload.webp">
    <p>一个内联文件选择器，让用户从本地计算机选择一个或多个文件并上传到服务器，支持拖放、过滤和每文件事件跟踪。</p>
  </GalleryCard>
</GalleryGrid>

## 选项对话框 {#option-dialogs}

选项对话框提供了一种向用户展示选择或在执行操作之前提示其确认的方法。这些组件对创建交互式、决策驱动的工作流程至关重要，使用户能够以清晰和结构化的方式确认、取消或从多个选项中进行选择。

<GalleryGrid>
  <GalleryCard header="MessageDialog" href="option-dialogs/message" image="/img/components/light/MessageDialog.webp" imageDark="/img/components/dark/MessageDialog.webp">
    <p>一个用于向用户显示信息消息或警报的对话框组件，通常带有一个单一的 `确定` 按钮以确认消息。</p>
  </GalleryCard>

  <GalleryCard header="ConfirmDialog" href="option-dialogs/confirm" image="/img/components/light/ConfirmDialog.webp" imageDark="/img/components/dark/ConfirmDialog.webp">
    <p>一个要求用户确认或取消操作的对话框组件，通常提供 `是` 和 `否` 或 `确定` 和 `取消` 按钮。</p>
  </GalleryCard>

  <GalleryCard header="InputDialog" href="option-dialogs/input" image="/img/components/light/InputDialog.webp" imageDark="/img/components/dark/InputDialog.webp">
    <p>一个提示用户输入文本或数据的对话框组件，通常提供一个输入字段和类似 `确定` 和 `取消` 的操作按钮。</p>
  </GalleryCard>

  <GalleryCard header="FileChooserDialog" href="option-dialogs/file-chooser" image="/img/components/light/FileChooserDialog.webp" imageDark="/img/components/dark/FileChooserDialog.webp">
    <p>一个允许用户浏览和选择来自服务器文件系统的文件的对话框组件。</p>
  </GalleryCard>

  <GalleryCard header="FileUploadDialog" href="option-dialogs/file-upload" image="/img/components/light/FileUploadDialog.webp" imageDark="/img/components/dark/FileUploadDialog.webp">
    <p>一个允许用户从本地文件系统上传文件到应用的对话框组件。</p>
  </GalleryCard>

  <GalleryCard header="FileSaveDialog" href="option-dialogs/file-save" image="/img/components/light/FileSaveDialog.webp" imageDark="/img/components/dark/FileSaveDialog.webp">
    <p>一个允许用户将文件保存到服务器文件系统上指定位置的对话框组件。</p>
  </GalleryCard>
</GalleryGrid>

## 交互与显示 {#interaction-and-display}

该类别包括一些便于用户交互和可视化显示数据或应用状态的组件。这些组件帮助用户浏览应用程序、触发操作，并通过动态视觉元素了解进度或结果。

<GalleryGrid>
  <GalleryCard header="Table" href="table/overview" image="/img/components/light/Table.webp" imageDark="/img/components/dark/Table.webp">
    <p>一个用于以结构化、表格格式展示数据的组件，支持排序和分页等功能。</p>
  </GalleryCard>

  <GalleryCard header="GoogleCharts" href="google-charts" image="/img/components/light/GoogleCharts.webp" imageDark="/img/components/dark/GoogleCharts.webp">
    <p>一个与 Google Charts 集成的组件，用于在应用中显示各种类型的图表和可视化数据表示。</p>
  </GalleryCard>

  <GalleryCard header="Button" href="button" image="/img/components/light/Button.webp" imageDark="/img/components/dark/Button.webp">
    <p>一个可点击的组件，当按下时触发操作或事件。</p>
  </GalleryCard>

  <GalleryCard header="Toast" href="toast" image="/img/components/light/Toast.webp" imageDark="/img/components/dark/Toast.webp">
    <p>一个轻量级、非阻塞的通知组件，短暂显示消息给用户，然后自动消失。</p>
  </GalleryCard>

  <GalleryCard header="Alert" href="alert" image="/img/components/light/Alert.webp" imageDark="/img/components/dark/Alert.webp">
    <p>一个以显著格式显示重要消息或警告的组件，以引起用户注意。</p>
  </GalleryCard>

  <GalleryCard header="Badge" href="badge" image="/img/components/light/Badge.webp" imageDark="/img/components/dark/Badge.webp">
    <p>一个小标签组件，用于显示计数、状态或简短元数据，支持主题、大小和图标。</p>
  </GalleryCard>

  <GalleryCard header="DesktopNotification" href="desktop-notification" image="/img/components/light/DesktopNotification.webp" imageDark="/img/components/dark/DesktopNotification.webp">
    <p>一个利用浏览器原生通知 API 警示用户的组件，显示自定义桌面通知。</p>
  </GalleryCard>

  <GalleryCard header="Navigator" href="navigator" image="/img/components/light/Navigator.webp" imageDark="/img/components/dark/Navigator.webp">
    <p>一个可自定义的分页组件，用于在数据集之间导航，支持具有第一个、最后一个、下一个、上一个按钮和快速跳转字段的布局。</p>
  </GalleryCard>

  <GalleryCard header="ProgressBar" href="progressbar" image="/img/components/light/ProgressBar.webp" imageDark="/img/components/dark/ProgressBar.webp">
    <p>一个可视化表示任务或过程进展的组件，通常显示为一个水平条，随着进度的推进而填充。</p>
  </GalleryCard>

  <GalleryCard header="Slider" href="slider" image="/img/components/light/Slider.webp" imageDark="/img/components/dark/Slider.webp">
    <p>一个允许用户通过拖动轨道上的把手从定义范围中选择值的组件。</p>
  </GalleryCard>

  <GalleryCard header="BusyIndicator" href="busyindicator" image="/img/components/light/BusyIndicator.webp" imageDark="/img/components/dark/BusyIndicator.webp">
    <p>一个应用范围的视觉指示器，通常是一个旋转的图标，表示全球过程正在进行中。</p>
  </GalleryCard>

  <GalleryCard header="Loading" href="loading" image="/img/components/light/Loading.webp" imageDark="/img/components/dark/Loading.webp">
    <p>一个范围加载指示器，显示在特定父组件内，指示该部分正在加载内容或数据。</p>
  </GalleryCard>

  <GalleryCard header="Spinner" href="spinner" image="/img/components/light/Spinner.webp" imageDark="/img/components/dark/Spinner.webp">
    <p>一个显示旋转动画的组件，通常用于指示过程或操作正在进行中。</p>
  </GalleryCard>

  <GalleryCard header="AppNav" href="appnav" image="/img/components/light/AppNav.webp" imageDark="/img/components/dark/AppNav.webp">
    <p>一个提供应用导航菜单的组件，通常用于列出链接或导航项，以切换不同部分或视图。</p>
  </GalleryCard>

  <GalleryCard header="Icon" href="icon" image="/img/components/light/Icon.webp" imageDark="/img/components/dark/Icon.webp">
    <p>一个显示图形符号或图像的组件，通常用于表示用户界面中的操作、状态或类别。</p>
  </GalleryCard>

  <GalleryCard header="Terminal" href="terminal" image="/img/components/light/Terminal.webp" imageDark="/img/components/dark/Terminal.webp">
    <p>一个在应用中模拟命令行界面 (CLI) 的组件，允许用户输入和执行基于文本的命令。</p>
  </GalleryCard>

  <GalleryCard header="InfiniteScroll" href="infinitescroll" image="/img/components/light/InfiniteScroll.webp" imageDark="/img/components/dark/InfiniteScroll.webp">
    <p>一个在滚动时加载更多项目的组件，显示加载指示器，并跟踪所有内容的获取状态。</p>
  </GalleryCard>

  <GalleryCard header="Refresher" href="refresher" image="/img/components/light/Refresher.webp" imageDark="/img/components/dark/Refresher.webp">
    <p>一个允许在可滚动容器内进行下拉刷新交互的组件，非常适合动态数据加载。</p>
  </GalleryCard>

  <GalleryCard header="Tree" href="tree" image="/img/components/light/Tree.webp" imageDark="/img/components/dark/Tree.webp">
    <p>一个用于显示层次数据的组件，允许用户展开、折叠和与嵌套项目互动。</p>
  </GalleryCard>

  <GalleryCard header="Avatar" href="avatar" image="/img/components/light/Avatar.webp" imageDark="/img/components/dark/Avatar.webp">
    <p>一个用于显示用户个人资料图片或首字母的组件，支持不同尺寸、形状和主题。</p>
  </GalleryCard>

  <GalleryCard header="MarkdownViewer" href="markdownviewer" image="/img/components/light/MarkdownViewer.webp" imageDark="/img/components/dark/MarkdownViewer.webp">
    <p>一个用于显示 markdown 内容的组件，具有逐字逐句渲染的功能，非常适合 AI 聊天界面和流式文本。</p>
  </GalleryCard>

</GalleryGrid>
