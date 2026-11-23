---
title: 界面组件
sidebar_position: 85
hide_table_of_contents: true
hide_giscus_comments: true
description: 浏览 webforJ UI 组件目录，涵盖布局、数据输入、导航、反馈和可视化组件。
_i18n_title_hash: 45ac037def4dc03161f6bedffcc3b307
_i18n_description_hash: 97182b12d746c9ed301eeeced3480321
_i18n_hash: 9bc06f09a5141c15a7f4f21d22457571
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<Head>
  <title>Java UI 组件 | webforJ</title>
</Head>

在 webforJ 中，应用由称为组件的模块化单元构建，这些组件有助于快速、高效地开发 UI。该框架提供一系列基础组件，例如按钮、输入元素和布局容器。掌握基础知识后，您可以查阅 [JavaDocs](https://javadoc.io/doc/com.webforj)，详细了解所有组件及其功能。

## 布局 {#layouts}

布局组件为构建用户界面提供基础，让开发者能够高效地组织内容。这些组件提供多种控制子组件排列方式的方法，适用于简单或复杂的布局。

以下布局组件旨在满足多种使用场景，从响应式设计到高级内容管理。

<GalleryGrid>
  <GalleryCard header="AppLayout" href="app-layout" image="/img/components/light/AppLayout.webp" imageDark="/img/components/dark/AppLayout.webp">
    <p>一种容器组件，为顶层应用导航和内容组织提供结构化布局。</p>
  </GalleryCard>

  <GalleryCard header="Toolbar" href="toolbar" image="/img/components/light/Toolbar.webp" imageDark="/img/components/dark/Toolbar.webp">
    <p>一种水平容器组件，用于放置一组操作按钮、图标或其他控件，通常用来执行与当前上下文相关的任务。</p>
  </GalleryCard>

  <GalleryCard header="FlexLayout" href="flex-layout" image="/img/components/light/FlexLayout.webp" imageDark="/img/components/dark/FlexLayout.webp">
    <p>一种布局组件，使用弹性盒（flexbox）规则排列子组件，支持响应式设计和对齐。</p>
  </GalleryCard>

  <GalleryCard header="ColumnsLayout" href="columns-layout" image="/img/components/light/ColumnsLayout.webp" imageDark="/img/components/dark/ColumnsLayout.webp">
    <p>一种布局组件，可将子组件排列成多个垂直列，适用于创建表单和类似网格的结构。</p>
  </GalleryCard>

  <GalleryCard header="Splitter" href="splitter" image="/img/components/light/Splitter.webp" imageDark="/img/components/dark/Splitter.webp">
    <p>一种布局组件，可在两个子组件之间分配可用空间，用户可以拖动分隔栏调整它们的大小。</p>
  </GalleryCard>

  <GalleryCard header="Drawer" href="drawer" image="/img/components/light/Drawer.webp" imageDark="/img/components/dark/Drawer.webp">
    <p>一种滑出面板组件，通常用于侧边导航或容纳可显示或隐藏的附加内容。</p>
  </GalleryCard>

  <GalleryCard header="Dialog" href="dialog" image="/img/components/light/Dialog.webp" imageDark="/img/components/dark/Dialog.webp">
    <p>一种模态窗口组件，可覆盖在内容之上以显示重要信息或提示用户进行交互，通常需要用户执行操作才能关闭。</p>
  </GalleryCard>

  <GalleryCard header="Login" href="login" image="/img/components/light/Login.webp" imageDark="/img/components/dark/Login.webp">
    <p>一种提供预构建用户身份验证界面的组件，通常包含用户名和密码字段以及提交按钮。</p>
  </GalleryCard>

  <GalleryCard header="Accordion" href="accordion" image="/img/components/light/Accordion.webp" imageDark="/img/components/dark/Accordion.webp">
    <p>一组垂直堆叠的可折叠面板，每个面板都有可点击的标题，用于切换其主体内容的显示状态。</p>
  </GalleryCard>

  <GalleryCard header="TabbedPane" href="tabbedpane" image="/img/components/light/TabbedPane.webp" imageDark="/img/components/dark/TabbedPane.webp">
    <p>一种容器组件，可将内容组织到多个选项卡中，让用户能够在不同视图或部分之间切换。</p>
  </GalleryCard>

  <GalleryCard header="Card" href="card" image="/img/components/light/Card.webp" imageDark="/img/components/dark/Card.webp">
    <p>一种用于组合相关内容和操作的表面区域，包含媒体、标题、主体内容和页脚等区域。</p>
  </GalleryCard>
</GalleryGrid>

## 数据输入 {#data-entry}

数据输入组件提供捕获用户输入和管理应用内交互所需的基本工具。这些组件用途广泛，可轻松构建交互式表单并收集各种类型的数据。

<GalleryGrid>
  <GalleryCard header="TextField" href="fields/textfield" image="/img/components/light/TextField.webp" imageDark="/img/components/dark/TextField.webp">
    <p>一种单行输入组件，用于输入和编辑文本数据。</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TextField" href="fields/masked/textfield" image="/img/components/light/MaskedTextField.webp" imageDark="/img/components/dark/MaskedTextField.webp">
    <p>一种文本输入组件，可将用户输入限制为特定格式或模式，通常用于电话号码、日期或信用卡号等字段。</p>
  </GalleryCard>

  <GalleryCard header="NumberField" href="fields/numberfield" image="/img/components/light/NumberField.webp" imageDark="/img/components/dark/NumberField.webp">
    <p>一种提供默认浏览器数值输入框的组件，支持输入数值，并内置用于增大或减小数值的控件。</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>NumberField" href="fields/masked/numberfield" image="/img/components/light/MaskedNumberField.webp" imageDark="/img/components/dark/MaskedNumberField.webp">
    <p>一种数值输入组件，可将用户输入限制为特定的数值格式或模式，确保输入的数字有效，例如货币、百分比或其他格式化数值。</p>
  </GalleryCard>

  <GalleryCard header="PasswordField" href="fields/passwordfield" image="/img/components/light/PasswordField.webp" imageDark="/img/components/dark/PasswordField.webp">
    <p>一种单行输入组件，用于安全地输入和遮蔽密码数据。</p>
  </GalleryCard>

  <GalleryCard header="DateField" href="fields/datefield" image="/img/components/light/DateField.webp" imageDark="/img/components/dark/DateField.webp">
    <p>一种提供默认浏览器日期选择器的组件，用户可通过输入框选择日期。</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>DateField" href="fields/masked/datefield" image="/img/components/light/MaskedDateField.webp" imageDark="/img/components/dark/MaskedDateField.webp">
    <p>一种日期输入组件，可强制使用特定的日期格式或模式，确保用户根据定义的掩码输入有效日期。</p>
  </GalleryCard>

  <GalleryCard header="TimeField" href="fields/timefield" image="/img/components/light/TimeField.webp" imageDark="/img/components/dark/TimeField.webp">
    <p>一种提供默认浏览器时间选择器的组件，用户可通过输入框选择时间值。</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TimeField" href="fields/masked/timefield" image="/img/components/light/MaskedTimeField.webp" imageDark="/img/components/dark/MaskedTimeField.webp">
    <p>一种时间输入组件，可强制使用特定的时间格式或模式，确保用户根据定义的掩码输入有效时间。</p>
  </GalleryCard>

  <GalleryCard header="DateTimeField" href="fields/datetimefield" image="/img/components/light/DateTimeField.webp" imageDark="/img/components/dark/DateTimeField.webp">
    <p>一种提供默认浏览器日期和时间选择器的组件，用户可通过单个输入框选择日期和时间。</p>
  </GalleryCard>

  <GalleryCard header="ColorField" href="fields/colorfield" image="/img/components/light/ColorField.webp" imageDark="/img/components/dark/ColorField.webp">
    <p>一种提供默认浏览器颜色选择器的组件，用户可通过输入框选择颜色。</p>
  </GalleryCard>

  <GalleryCard header="TextArea" href="textarea" image="/img/components/light/TextArea.webp" imageDark="/img/components/dark/TextArea.webp">
    <p>一种多行文本输入组件，用户可输入或编辑较长的文本内容。</p>
  </GalleryCard>

  <GalleryCard header="CheckBox" href="checkbox" image="/img/components/light/CheckBox.webp" imageDark="/img/components/dark/CheckBox.webp">
    <p>一种表示二元选项的组件，用户可以在选中（true）和未选中（false）状态之间切换。</p>
  </GalleryCard>

  <GalleryCard header="RadioButton" href="radiobutton" image="/img/components/light/RadioButton.webp" imageDark="/img/components/dark/RadioButton.webp">
    <p>一种组件，用户可从一组互斥选项中选择一个选项。</p>
  </GalleryCard>

  <GalleryCard header="Switch" href="radiobutton#switches" image="/img/components/light/Switch.webp" imageDark="/img/components/dark/Switch.webp">
    <p>一种切换组件，用户可通过滑动操作在两种状态之间切换，例如开/关或 true/false。</p>
  </GalleryCard>

  <GalleryCard header="ChoiceBox" href="lists/choicebox" image="/img/components/light/ChoiceBox.webp" imageDark="/img/components/dark/ChoiceBox.webp">
    <p>一种提供预定义选项下拉列表的组件，用户可从列表中选择一个选项。</p>
  </GalleryCard>

  <GalleryCard header="ComboBox" href="lists/combobox" image="/img/components/light/ComboBox.webp" imageDark="/img/components/dark/ComboBox.webp">
    <p>一种将下拉列表与可编辑文本输入框相结合的组件，用户既可以从列表中选择选项，也可以输入自定义值。</p>
  </GalleryCard>

  <GalleryCard header="ListBox" href="lists/listbox" image="/img/components/light/ListBox.webp" imageDark="/img/components/dark/ListBox.webp">
    <p>一种显示可滚动选项列表的组件，用户可以从列表中选择一个或多个项目。</p>
  </GalleryCard>

  <GalleryCard header="Upload" href="upload" image="/img/components/light/Upload.webp" imageDark="/img/components/dark/Upload.webp">
    <p>一种内嵌文件选择器，用户可从本地设备选择一个或多个文件并上传到服务器，同时支持拖放、筛选器以及逐文件事件跟踪。</p>
  </GalleryCard>
</GalleryGrid>

## 选项对话框 {#option-dialogs}

选项对话框可向用户展示选项，或在执行操作前请求确认。这些组件对于构建交互式、由决策驱动的工作流至关重要，能够让用户以清晰、有序的方式确认、取消或从多个选项中进行选择。

<GalleryGrid>
  <GalleryCard header="MessageDialog" href="option-dialogs/message" image="/img/components/light/MessageDialog.webp" imageDark="/img/components/dark/MessageDialog.webp">
    <p>一种对话框组件，用于向用户显示信息或警告，通常包含一个 `OK` 按钮以确认已读消息。</p>
  </GalleryCard>

  <GalleryCard header="ConfirmDialog" href="option-dialogs/confirm" image="/img/components/light/ConfirmDialog.webp" imageDark="/img/components/dark/ConfirmDialog.webp">
    <p>一种对话框组件，用于请求用户确认或取消某项操作，通常提供 `Yes` 和 `No` 或 `OK` 和 `Cancel` 按钮。</p>
  </GalleryCard>

  <GalleryCard header="InputDialog" href="option-dialogs/input" image="/img/components/light/InputDialog.webp" imageDark="/img/components/dark/InputDialog.webp">
    <p>一种对话框组件，用于提示用户输入文本或数据，通常包含一个输入框以及 `OK` 和 `Cancel` 等操作按钮。</p>
  </GalleryCard>

  <GalleryCard header="FileChooserDialog" href="option-dialogs/file-chooser" image="/img/components/light/FileChooserDialog.webp" imageDark="/img/components/dark/FileChooserDialog.webp">
    <p>一种对话框组件，用户可通过它浏览并选择服务器文件系统中的文件。</p>
  </GalleryCard>

  <GalleryCard header="FileUploadDialog" href="option-dialogs/file-upload" image="/img/components/light/FileUploadDialog.webp" imageDark="/img/components/dark/FileUploadDialog.webp">
    <p>一种对话框组件，用户可通过它将本地文件系统中的文件上传到应用。</p>
  </GalleryCard>

  <GalleryCard header="FileSaveDialog" href="option-dialogs/file-save" image="/img/components/light/FileSaveDialog.webp" imageDark="/img/components/dark/FileSaveDialog.webp">
    <p>一种对话框组件，用户可通过它将文件保存到服务器文件系统中的指定位置。</p>
  </GalleryCard>
</GalleryGrid>

## 交互与显示 {#interaction-and-display}

此类别包含用于促进用户交互以及直观显示数据或应用状态的组件。这些组件通过动态视觉元素帮助用户浏览应用、触发操作，并了解进度或结果。

<GalleryGrid>
  <GalleryCard header="Table" href="table/overview" image="/img/components/light/Table.webp" imageDark="/img/components/dark/Table.webp">
    <p>一种以结构化表格形式（包含行和列）显示数据的组件，并支持排序和分页等功能。</p>
  </GalleryCard>

  <GalleryCard header="GoogleCharts" href="google-charts" image="/img/components/light/GoogleCharts.webp" imageDark="/img/components/dark/GoogleCharts.webp">
    <p>一种与 Google Charts 集成的组件，可在应用中显示各种图表和数据可视化内容。</p>
  </GalleryCard>

  <GalleryCard header="Button" href="button" image="/img/components/light/Button.webp" imageDark="/img/components/dark/Button.webp">
    <p>一种可点击的组件，按下时会触发操作或事件。</p>
  </GalleryCard>

  <GalleryCard header="Toast" href="toast" image="/img/components/light/Toast.webp" imageDark="/img/components/dark/Toast.webp">
    <p>一种轻量、非阻塞的通知组件，可短暂向用户显示消息，然后自动消失。</p>
  </GalleryCard>

  <GalleryCard header="Alert" href="alert" image="/img/components/light/Alert.webp" imageDark="/img/components/dark/Alert.webp">
    <p>一种以醒目形式显示重要消息或警告，从而吸引用户注意的组件。</p>
  </GalleryCard>

  <GalleryCard header="Badge" href="badge" image="/img/components/light/Badge.webp" imageDark="/img/components/dark/Badge.webp">
    <p>一种小型标签组件，用于显示计数、状态或简短元数据，并支持主题、尺寸和图标。</p>
  </GalleryCard>

  <GalleryCard header="DesktopNotification" href="desktop-notification" image="/img/components/light/DesktopNotification.webp" imageDark="/img/components/dark/DesktopNotification.webp">
    <p>一种利用浏览器原生 Notification API 向用户发送自定义桌面通知的组件。</p>
  </GalleryCard>

  <GalleryCard header="Navigator" href="navigator" image="/img/components/light/Navigator.webp" imageDark="/img/components/dark/Navigator.webp">
    <p>一种可自定义的分页组件，用于浏览数据集，支持包含首页、末页、下一页和上一页按钮以及快速跳转字段的布局。</p>
  </GalleryCard>

  <GalleryCard header="ProgressBar" href="progressbar" image="/img/components/light/ProgressBar.webp" imageDark="/img/components/dark/ProgressBar.webp">
    <p>一种直观呈现任务或流程进度的组件，通常显示为水平进度条，并随着进度推进逐渐填充。</p>
  </GalleryCard>

  <GalleryCard header="Slider" href="slider" image="/img/components/light/Slider.webp" imageDark="/img/components/dark/Slider.webp">
    <p>一种组件，用户可沿轨道拖动滑块，从指定范围内选择一个值。</p>
  </GalleryCard>

  <GalleryCard header="BusyIndicator" href="busyindicator" image="/img/components/light/BusyIndicator.webp" imageDark="/img/components/dark/BusyIndicator.webp">
    <p>一种适用于整个应用的视觉指示器，通常为旋转图标，用于表示全局流程正在进行。</p>
  </GalleryCard>

  <GalleryCard header="Loading" href="loading" image="/img/components/light/Loading.webp" imageDark="/img/components/dark/Loading.webp">
    <p>一种作用域限定的加载指示器，显示在特定父组件中，表示该区域正在加载内容或数据。</p>
  </GalleryCard>

  <GalleryCard header="Spinner" href="spinner" image="/img/components/light/Spinner.webp" imageDark="/img/components/dark/Spinner.webp">
    <p>一种显示旋转动画的组件，通常用于表示某个流程或操作正在进行。</p>
  </GalleryCard>

  <GalleryCard header="AppNav" href="appnav" image="/img/components/light/AppNav.webp" imageDark="/img/components/dark/AppNav.webp">
    <p>一种为应用提供导航菜单的组件，通常用于列出链接或导航项，以便在不同部分或视图之间切换。</p>
  </GalleryCard>

  <GalleryCard header="Icon" href="icon" image="/img/components/light/Icon.webp" imageDark="/img/components/dark/Icon.webp">
    <p>一种显示图形符号或图像的组件，常用于在用户界面中表示操作、状态或类别。</p>
  </GalleryCard>

  <GalleryCard header="Terminal" href="terminal" image="/img/components/light/Terminal.webp" imageDark="/img/components/dark/Terminal.webp">
    <p>一种在应用中模拟命令行界面（CLI）的组件，用户可通过它输入并执行基于文本的命令。</p>
  </GalleryCard>

  <GalleryCard header="InfiniteScroll" href="infinitescroll" image="/img/components/light/InfiniteScroll.webp" imageDark="/img/components/dark/InfiniteScroll.webp">
    <p>一种在滚动时加载更多项目、显示加载器并跟踪所有内容是否已加载完毕的组件。</p>
  </GalleryCard>

  <GalleryCard header="Refresher" href="refresher" image="/img/components/light/Refresher.webp" imageDark="/img/components/dark/Refresher.webp">
    <p>一种可在可滚动容器中实现下拉刷新交互的组件，非常适合动态加载数据。</p>
  </GalleryCard>

  <GalleryCard header="Tree" href="tree" image="/img/components/light/Tree.webp" imageDark="/img/components/dark/Tree.webp">
    <p>一种用于显示层次结构数据的组件，用户可以展开、折叠并与嵌套项目交互。</p>
  </GalleryCard>

  <GalleryCard header="Avatar" href="avatar" image="/img/components/light/Avatar.webp" imageDark="/img/components/dark/Avatar.webp">
    <p>一种用于显示用户头像或姓名首字母的组件，并支持不同的尺寸、形状和主题。</p>
  </GalleryCard>

  <GalleryCard header="MarkdownViewer" href="markdownviewer" image="/img/components/light/MarkdownViewer.webp" imageDark="/img/components/dark/MarkdownViewer.webp">
    <p>一种用于显示 Markdown 内容的组件，支持按字符逐步渲染，非常适合 AI 聊天界面和流式文本。</p>
  </GalleryCard>

</GalleryGrid>
