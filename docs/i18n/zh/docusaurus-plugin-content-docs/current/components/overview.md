---
title: UI Components
sidebar_position: 85
hide_table_of_contents: true
sidebar_class_name: has-new-content
hide_giscus_comments: true
_i18n_hash: 3cecf991ebc3086900ecf15b1d0a7b20
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

在 webforJ 中，应用程序是使用称为组件的模块化单元创建的，这些单元促进了快速高效的 UI 开发。该框架提供了一系列基本组件，如按钮、输入元素和布局容器。掌握基础知识后，您可以查阅 [JavaDocs](https://javadoc.io/doc/com.webforj) 以获取所有组件及其功能的详细概述。

## 布局 {#layouts}

布局组件为结构化用户界面提供了基础，使开发者能够高效地组织内容。这些组件提供了多种控制子组件排列方式的方法，适用于简单或复杂的布局。

以下布局组件旨在处理广泛的使用案例，从响应式设计到高级内容管理。

<GalleryGrid>
  <GalleryCard header="AppLayout" href="app-layout" image="/img/components/AppLayout.png">
    <p>一个容器组件，为顶级应用导航和内容组织提供结构化布局。</p>
  </GalleryCard>

  <GalleryCard header="Toolbar" href="toolbar" image="/img/components/Toolbar.png">
    <p>一个水平容器组件，包含一组操作按钮、图标或其他控件，通常用于执行与当前上下文相关的任务。</p>
  </GalleryCard>

  <GalleryCard header="FlexLayout" href="flex-layout" image="/img/components/FlexLayout.png">
    <p>一个布局组件，它使用灵活盒子（flexbox）规则排列其子元素，以实现响应式设计和对齐。</p>
  </GalleryCard>

  <GalleryCard header="ColumnsLayout" href="columns-layout" image="/img/components/ColumnsLayout.png">
    <p>一个布局组件，它将子元素排列成多个垂直列，适用于创建表单和网格状结构。</p>
  </GalleryCard>

  <GalleryCard header="Splitter" href="splitter" image="/img/components/Splitter.png" effect="slideLeftRightScale">
    <p>一个布局组件，将可用空间划分给两个子组件，允许用户通过拖动分隔条来调整它们的大小。</p>
  </GalleryCard>

  <GalleryCard header="Drawer" href="drawer" image="/img/components/Drawer.png" effect="slideUp">
    <p>一个滑动面板组件，通常用于侧面导航或容纳可以显示或隐藏的附加内容。</p>
  </GalleryCard>

  <GalleryCard header="Dialog" href="dialog" image="/img/components/Dialog.png">
    <p>一个模态窗口组件，覆盖内容以显示重要信息或提示用户交互，通常需要用户操作才能关闭。</p>
  </GalleryCard>

  <GalleryCard header="Login" href="login" image="/img/components/Login.png">
    <p>一个提供用户身份验证的预构建 UI 组件，通常包括用户名和密码字段以及提交按钮。</p>
  </GalleryCard>

  <GalleryCard header="TabbedPane" href="tabbedpane" image="/img/components/TabbedPane.png">
    <p>一个容器组件，将内容组织到多个标签中，允许用户在不同视图或部分之间切换。</p>
  </GalleryCard>
</GalleryGrid>

## 数据输入 {#data-entry}

数据输入组件提供了捕获用户输入和管理应用内交互的基本工具。这些组件灵活，便于构建交互式表单并收集各种类型的数据。

<GalleryGrid>
  <GalleryCard header="TextField" href="fields/textfield" image="/img/components/TextField.png">
    <p>用于输入和编辑文本数据的一行输入组件。</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TextField" href="fields/masked/textfield" image="/img/components/MaskedTextField.png">
    <p>一个文本输入组件，将用户输入限制为特定格式或模式，通常用于电话号、日期或信用卡号等字段。</p>
  </GalleryCard>

  <GalleryCard header="NumberField" href="fields/numberfield" image="/img/components/NumberField.png">
    <p>一个为输入数值提供默认的基于浏览器的输入字段的组件，带有增减值的内置控件。</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>NumberField" href="fields/masked/numberfield" image="/img/components/MaskedNumberField.png">
    <p>一个数值输入组件，限制用户输入特定的数值格式或模式，确保有效的数字输入，如货币、百分比或其他格式化数字。</p>
  </GalleryCard>

  <GalleryCard header="PasswordField" href="fields/passwordfield" image="/img/components/PasswordField.png">
    <p>一个用于安全输入和掩盖密码数据的一行输入组件。</p>
  </GalleryCard>

  <GalleryCard header="DateField" href="fields/datefield" image="/img/components/DateField.png">
    <p>一个提供默认基于浏览器的日期选择器的组件，通过输入字段选择日期。</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>DateField" href="fields/masked/datefield" image="/img/components/MaskedDateField.png">
    <p>一个日期输入组件，强制执行特定的日期格式或模式，确保用户根据定义的掩码输入有效日期。</p>
  </GalleryCard>

  <GalleryCard header="TimeField" href="fields/timefield" image="/img/components/TimeField.png">
    <p>一个提供默认的基于浏览器的时间选择器的组件，通过输入字段选择时间值。</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TimeField" href="fields/masked/timefield" image="/img/components/MaskedTimeField.png">
    <p>一个时间输入组件，强制执行特定的时间格式或模式，确保用户根据定义的掩码输入有效时间。</p>
  </GalleryCard>

  <GalleryCard header="DateTimeField" href="fields/datetimefield" image="/img/components/DateTimeField.png">
    <p>一个提供默认基于浏览器的日期和时间选择器的组件，通过单个输入字段选择日期和时间。</p>
  </GalleryCard>

  <GalleryCard header="ColorField" href="fields/colorfield" image="/img/components/ColorField.png">
    <p>一个提供默认基于浏览器的颜色选择器的组件，允许用户从输入字段中选择颜色。</p>
  </GalleryCard>

  <GalleryCard header="TextArea" href="textarea" image="/img/components/TextArea.png">
    <p>一个多行文本输入组件，允许用户输入或编辑较大的文本块。</p>
  </GalleryCard>

  <GalleryCard header="CheckBox" href="checkbox" image="/img/components/CheckBox.png">
    <p>一个表示二元选项的组件，允许用户在选中（真）或未选中（假）状态之间切换。</p>
  </GalleryCard>

  <GalleryCard header="RadioButton" href="radiobutton" image="/img/components/RadioButton.png">
    <p>一个允许用户从一组互斥选择中选择单个选项的组件。</p>
  </GalleryCard>

  <GalleryCard header="Switch" href="radiobutton#switches" image="/img/components/Switch.png">
    <p>一个切换组件，允许用户在两种状态之间切换，例如开/关或真/假，带有滑动操作。</p>
  </GalleryCard>

  <GalleryCard header="ChoiceBox" href="lists/choicebox" image="/img/components/ChoiceBox.png">
    <p>一个提供预定义选项下拉列表的组件，允许用户从列表中选择一个选项。</p>
  </GalleryCard>

  <GalleryCard header="ComboBox" href="lists/combobox" image="/img/components/ComboBox.png">
    <p>一个将下拉列表与可编辑文本输入结合的组件，允许用户从列表中选择一个选项或输入自定义值。</p>
  </GalleryCard>

  <GalleryCard header="ListBox" href="lists/listbox" image="/img/components/ListBox.png">
    <p>一个显示可滚动选项列表的组件，允许用户从列表中选择一个或多个项。</p>
  </GalleryCard>
</GalleryGrid>

## 选项对话框 {#option-dialogs}

选项对话框提供了一种向用户展示选择或在继续执行操作之前要求他们确认的方式。这些组件对于创建交互式、基于决策的工作流程至关重要，使用户能够以清晰和结构化的方式确认、取消或从各种选项中选择。

<GalleryGrid>
  <GalleryCard header="MessageDialog" href="option-dialogs/message" image="/img/components/MessageDialog.png">
    <p>一个对话框组件，用于向用户显示信息消息或警报，通常带有一个 `OK` 按钮以确认消息。</p>
  </GalleryCard>

  <GalleryCard header="ConfirmDialog" href="option-dialogs/confirm" image="/img/components/ConfirmDialog.png">
    <p>一个对话框组件，询问用户确认或取消某个操作，通常提供 `Yes` 和 `No` 或 `OK` 和 `Cancel` 按钮。</p>
  </GalleryCard>
  
  <GalleryCard header="InputDialog" href="option-dialogs/input" image="/img/components/InputDialog.png">
    <p>一个对话框组件，提示用户输入文本或数据，通常提供一个输入字段以及操作按钮如 `OK` 和 `Cancel`。</p>
  </GalleryCard>

  <GalleryCard header="FileChooserDialog" href="option-dialogs/file-chooser" image="/img/components/FileChooserDialog.png">
    <p>一个对话框组件，允许用户浏览并选择服务器文件系统中的文件。</p>
  </GalleryCard>

  <GalleryCard header="FileUploadDialog" href="option-dialogs/file-upload" image="/img/components/FileUploadDialog.png">
    <p>一个对话框组件，使用户能够从其本地文件系统向应用程序上传文件。</p>
  </GalleryCard>

  <GalleryCard header="FileSaveDialog" href="option-dialogs/file-save" image="/img/components/FileSaveDialog.png">
    <p>一个对话框组件，允许用户将文件保存到服务器文件系统的指定位置。</p>
  </GalleryCard>
</GalleryGrid>

## 交互与显示 {#interaction-and-display}

此类别包括促进用户交互和动态显示数据或应用程序状态的组件。这些组件帮助用户导航应用程序，触发操作，并通过动态视觉元素理解进度或结果。

<GalleryGrid>
  <GalleryCard header="Table" href="table/overview" image="/img/components/Table.png">
    <p>一个组件，用于以结构化的表格式显示数据，支持排序和分页等功能。</p>
  </GalleryCard>

  <GalleryCard header="GoogleCharts" href="google-charts" image="/img/components/GoogleCharts.png">
    <p>一个与 Google 图表集成的组件，在应用程序中展示各种类型的图表和可视化数据表示。</p>
  </GalleryCard>

  <GalleryCard header="Button" href="button" image="/img/components/Button.png">
    <p>一个可点击组件，按下时触发一个操作或事件。</p>
  </GalleryCard>

  <GalleryCard header="Toast" href="toast" image="/img/components/Toast.png"  effect="slideUp">
    <p>一个轻量级、非阻塞的通知组件，简短地向用户显示信息后自动消失。</p>
  </GalleryCard>

  <GalleryCard header="Alert" href="alert" image="/img/components/Alert.png">
    <p>一个以显眼格式显示重要消息或警告的组件，以吸引用户注意。</p>
  </GalleryCard>

  <GalleryCard header="DesktopNotification" href="desktop-notification" image="/img/components/DesktopNotification.png">
    <p>一个利用浏览器的本地通知 API 向用户发送自定义桌面通知的组件。</p>
  </GalleryCard>
  
  <GalleryCard header="Navigator" href="navigator" image="/img/components/Navigator.png">
    <p>一个可自定义的分页组件，用于在数据集之间进行导航，支持带有第一个、最后一个、下一个、上一个按钮和快速跳转字段的布局。</p>
  </GalleryCard>

  <GalleryCard header="ProgressBar" href="progressbar" image="/img/components/ProgressBar.png">
    <p>一个直观表示任务或流程进度的组件，通常以水平条的形式显示，随着进度的推进而填充。</p>
  </GalleryCard>

  <GalleryCard header="Slider" href="slider" image="/img/components/Slider.png">
    <p>一个允许用户从定义范围内选择值的组件，通过在轨道上拖动滑块。</p>
  </GalleryCard>

  <GalleryCard header="BusyIndicator" href="busyindicator" image="/img/components/BusyIndicator.png">
    <p>一个应用程序范围内的视觉指示器，通常是旋转的指示器，表示一个全局过程正在进行中。</p>
  </GalleryCard>

  <GalleryCard header="Loading" href="loading" image="/img/components/Loading.png">
    <p>一个特定范围内的加载指示器，在特定父组件中显示，指示该部分的内容或数据正在加载。</p>
  </GalleryCard>

  <GalleryCard header="Spinner" href="spinner" image="/img/components/Spinner.png">
    <p>一个显示旋转动画的组件，通常用于表示一个过程或操作正在进行中。</p>
  </GalleryCard>

  <GalleryCard header="AppNav" href="appnav" image="/img/components/AppNav.png" effect="slideFromLeft">
    <p>一个为应用程序提供导航菜单的组件，通常用于列出链接或导航项，以在不同部分或视图之间切换。</p>
  </GalleryCard>

  <GalleryCard header="Icon" href="icon" image="/img/components/Icons.png">
    <p>一个显示图形符号或图像的组件，通常用于表示用户界面中的操作、状态或类别。</p>
  </GalleryCard>

  <GalleryCard header="Terminal" href="terminal" image="/img/components/Terminal.png">
    <p>一个在应用程序中模拟命令行界面（CLI）的组件，允许用户输入和执行基于文本的命令。</p>
  </GalleryCard>
  
  <GalleryCard header="InfiniteScroll" href="infinitescroll" image="/img/components/InfiniteScroll.png">
    <p>一个在滚动时加载更多项目的组件，显示加载器，并跟踪何时获取所有内容。</p>
  </GalleryCard>

  <GalleryCard header="Refresher" href="refresher" image="/img/components/Refresher.png">
    <p>一个允许在可滚动容器中执行下拉刷新交互的组件，非常适合动态数据加载。</p>
  </GalleryCard>

  <GalleryCard header="Tree" href="tree" image="/img/components/Tree.png">
    <p>一个用于显示层次数据的组件，让用户展开、折叠并与嵌套项进行交互。</p>
  </GalleryCard>
  
  <GalleryCard header="Avatar" href="avatar" image="/img/components/Avatar.png">
    <p>一个用于显示用户头像或首字母的组件，支持不同的大小、形状和主题。</p>
  </GalleryCard>
  
  <GalleryCard header="MarkdownViewer" href="markdownviewer" image="/img/components/MarkdownViewer.png">
    <p>一个用于显示markdown内容的组件，支持逐字渲染，理想应用于AI聊天界面和文本流。</p>
  </GalleryCard>
  
</GalleryGrid>
