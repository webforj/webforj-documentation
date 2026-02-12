---
title: UI Components
sidebar_position: 85
hide_table_of_contents: true
sidebar_class_name: has-new-content
hide_giscus_comments: true
_i18n_hash: 80950952d9226a7a35503663c4155da7
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

在 webforJ 中，应用程序是使用称为组件的模块化单元创建的，这些单元便于快速高效的用户界面开发。该框架提供了一系列基本组件，例如按钮、输入元素和布局容器。在掌握基本知识后，您可以查阅 [JavaDocs](https://javadoc.io/doc/com.webforj) 以获取所有组件及其功能的详细概述。

## 布局 {#layouts}

布局组件为构建用户界面提供了基础，使开发人员能够高效地组织内容。这些组件提供多种控制子组件排列的方式，无论是简单布局还是复杂布局。

以下布局组件旨在处理从响应式设计到高级内容管理的广泛用例。

<GalleryGrid>
  <GalleryCard header="AppLayout" href="app-layout" image="/img/components/AppLayout.png">
    <p>一个提供结构化布局的容器组件，用于顶级应用导航和内容组织。</p>
  </GalleryCard>

  <GalleryCard header="Toolbar" href="toolbar" image="/img/components/Toolbar.png">
    <p>一个水平容器组件，包含一组动作按钮、图标或其他控件，通常用于执行与当前上下文相关的任务。</p>
  </GalleryCard>

  <GalleryCard header="FlexLayout" href="flex-layout" image="/img/components/FlexLayout.png">
    <p>一个使用弹性盒（flexbox）规则排列子组件的布局组件，适用于响应式设计和对齐。</p>
  </GalleryCard>

  <GalleryCard header="ColumnsLayout" href="columns-layout" image="/img/components/ColumnsLayout.png">
    <p>一个将子组件排列成多个垂直列的布局组件，适用于创建表单和网格状结构。</p>
  </GalleryCard>

  <GalleryCard header="Splitter" href="splitter" image="/img/components/Splitter.png" effect="slideLeftRightScale">
    <p>一个布局组件，将可用空间在两个子组件之间划分，允许用户通过拖动分隔条来调整其大小。</p>
  </GalleryCard>

  <GalleryCard header="Drawer" href="drawer" image="/img/components/Drawer.png" effect="slideUp">
    <p>一个滑动面板组件，通常用于侧边导航或容纳可以显示或隐藏的额外内容。</p>
  </GalleryCard>

  <GalleryCard header="Dialog" href="dialog" image="/img/components/Dialog.png">
    <p>一个模态窗口组件，覆盖内容以显示重要信息或提示用户交互，通常需要用户操作才能关闭。</p>
  </GalleryCard>

  <GalleryCard header="Login" href="login" image="/img/components/Login.png">
    <p>一个提供用户身份验证的预构建 UI 的组件，通常包括用户名和密码字段以及提交按钮。</p>
  </GalleryCard>

  <GalleryCard header="TabbedPane" href="tabbedpane" image="/img/components/TabbedPane.png">
    <p>一个将内容组织到多个选项卡中的容器组件，允许用户在不同视图或部分之间切换。</p>
  </GalleryCard>
</GalleryGrid>

## 数据输入 {#data-entry}

数据输入组件为捕获用户输入和管理应用内交互提供了基本工具。这些组件灵活多变，使构建交互式表单和收集各种类型的数据变得轻松。

<GalleryGrid>
  <GalleryCard header="TextField" href="fields/textfield" image="/img/components/TextField.png">
    <p>一个用于输入和编辑文本数据的单行输入组件。</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TextField" href="fields/masked/textfield" image="/img/components/MaskedTextField.png">
    <p>一个限制用户输入特定格式或模式的文本输入组件，通常用于电话号码、日期或信用卡号码等字段。</p>
  </GalleryCard>

  <GalleryCard header="NumberField" href="fields/numberfield" image="/img/components/NumberField.png">
    <p>一个提供默认基于浏览器的输入字段，用于输入数字值的组件，带有用于增加或减少值的内置控件。</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>NumberField" href="fields/masked/numberfield" image="/img/components/MaskedNumberField.png">
    <p>一个限制用户输入特定数字格式或模式的数字输入组件，确保输入有效数字，如货币、百分比或其他格式化数字。</p>
  </GalleryCard>

  <GalleryCard header="PasswordField" href="fields/passwordfield" image="/img/components/PasswordField.png">
    <p>一个用于安全输入和掩盖密码数据的单行输入组件。</p>
  </GalleryCard>

  <GalleryCard header="DateField" href="fields/datefield" image="/img/components/DateField.png">
    <p>一个提供默认浏览器日期选择器的组件，允许通过输入字段选择日期。</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>DateField" href="fields/masked/datefield" image="/img/components/MaskedDateField.png">
    <p>一个强制特定日期格式或模式的日期输入组件，确保用户根据定义的掩码输入有效日期。</p>
  </GalleryCard>

  <GalleryCard header="TimeField" href="fields/timefield" image="/img/components/TimeField.png">
    <p>一个提供默认浏览器时间选择器的组件，允许通过输入字段选择时间值。</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TimeField" href="fields/masked/timefield" image="/img/components/MaskedTimeField.png">
    <p>一个强制特定时间格式或模式的时间输入组件，确保用户根据定义的掩码输入有效时间。</p>
  </GalleryCard>

  <GalleryCard header="DateTimeField" href="fields/datetimefield" image="/img/components/DateTimeField.png">
    <p>一个提供默认浏览器日期和时间选择器的组件，允许通过一个输入字段选择日期和时间。</p>
  </GalleryCard>

  <GalleryCard header="ColorField" href="fields/colorfield" image="/img/components/ColorField.png">
    <p>一个提供默认浏览器颜色选择器的组件，允许用户从输入字段选择颜色。</p>
  </GalleryCard>

  <GalleryCard header="TextArea" href="textarea" image="/img/components/TextArea.png">
    <p>一个允许用户输入或编辑大块文本的多行文本输入组件。</p>
  </GalleryCard>

  <GalleryCard header="CheckBox" href="checkbox" image="/img/components/CheckBox.png">
    <p>一个表示二元选项的组件，允许用户在选中（真）或未选中（假）状态之间切换。</p>
  </GalleryCard>

  <GalleryCard header="RadioButton" href="radiobutton" image="/img/components/RadioButton.png">
    <p>一个允许用户从一组互斥选项中选择一个选项的组件。</p>
  </GalleryCard>

  <GalleryCard header="Switch" href="radiobutton#switches" image="/img/components/Switch.png">
    <p>一个切换组件，允许用户在两个状态（开/关或真/假）之间切换，具有滑动行为。</p>
  </GalleryCard>

  <GalleryCard header="ChoiceBox" href="lists/choicebox" image="/img/components/ChoiceBox.png">
    <p>一个提供预定义选项下拉列表的组件，允许用户从列表中选择一个选项。</p>
  </GalleryCard>

  <GalleryCard header="ComboBox" href="lists/combobox" image="/img/components/ComboBox.png">
    <p>一个将下拉列表与可编辑文本输入相结合的组件，允许用户从列表中选择选项或输入自定义值。</p>
  </GalleryCard>

  <GalleryCard header="ListBox" href="lists/listbox" image="/img/components/ListBox.png">
    <p>一个显示可滚动选项列表的组件，允许用户从列表中选择一个或多个项目。</p>
  </GalleryCard>
</GalleryGrid>

## 选项对话框 {#option-dialogs}

选项对话框提供了一种向用户展示选择或在执行操作之前提示其确认的方式。这些组件对于创建交互式决策驱动的工作流程至关重要，使用户能够以清晰和结构化的方式确认、取消或选择多种选项。

<GalleryGrid>
  <GalleryCard header="MessageDialog" href="option-dialogs/message" image="/img/components/MessageDialog.png">
    <p>用于向用户显示信息消息或警报的对话框组件，通常带有一个 `OK` 按钮以确认消息。</p>
  </GalleryCard>

  <GalleryCard header="ConfirmDialog" href="option-dialogs/confirm" image="/img/components/ConfirmDialog.png">
    <p>询问用户确认或取消操作的对话框组件，通常提供 `是` 和 `否` 或 `OK` 和 `取消` 按钮。</p>
  </GalleryCard>
  
  <GalleryCard header="InputDialog" href="option-dialogs/input" image="/img/components/InputDialog.png">
    <p>提示用户输入文本或数据的对话框组件，通常提供输入字段以及 `OK` 和 `取消` 等操作按钮。</p>
  </GalleryCard>

  <GalleryCard header="FileChooserDialog" href="option-dialogs/file-chooser" image="/img/components/FileChooserDialog.png">
    <p>允许用户浏览并从服务器文件系统中选择文件的对话框组件。</p>
  </GalleryCard>

  <GalleryCard header="FileUploadDialog" href="option-dialogs/file-upload" image="/img/components/FileUploadDialog.png">
    <p>使用户能够将文件从其本地文件系统上传到应用程序的对话框组件。</p>
  </GalleryCard>

  <GalleryCard header="FileSaveDialog" href="option-dialogs/file-save" image="/img/components/FileSaveDialog.png">
    <p>允许用户将文件保存到服务器文件系统指定位置的对话框组件。</p>
  </GalleryCard>
</GalleryGrid>

## 交互和显示 {#interaction-and-display}

这一类别包括促进用户交互和动态展示数据或应用状态的组件。这些组件帮助用户导航应用程序、触发操作，并通过动态视觉元素了解进度或结果。

<GalleryGrid>
  <GalleryCard header="Table" href="table/overview" image="/img/components/Table.png">
    <p>一个用于以结构化、表格格式显示数据的组件，支持排序和分页等功能。</p>
  </GalleryCard>

  <GalleryCard header="GoogleCharts" href="google-charts" image="/img/components/GoogleCharts.png">
    <p>一个与 Google Charts 集成以在应用程序中显示各种类型图表和视觉数据表示的组件。</p>
  </GalleryCard>

  <GalleryCard header="Button" href="button" image="/img/components/Button.png">
    <p>一个可点击的组件，在按下时触发一个操作或事件。</p>
  </GalleryCard>

  <GalleryCard header="Toast" href="toast" image="/img/components/Toast.png" effect="slideUp">
    <p>一个轻量级的、非阻塞的通知组件，短暂显示消息给用户，然后自动消失。</p>
  </GalleryCard>

  <GalleryCard header="Alert" href="alert" image="/img/components/Alert.png">
    <p>一个以显著格式显示重要消息或警告的组件，以吸引用户注意。</p>
  </GalleryCard>

  <GalleryCard header="DesktopNotification" href="desktop-notification" image="/img/components/DesktopNotification.png">
    <p>一个利用浏览器的本地通知 API 来通过自定义桌面通知提醒用户的组件。</p>
  </GalleryCard>
  
  <GalleryCard header="Navigator" href="navigator" image="/img/components/Navigator.png">
    <p>一个可自定义的分页组件，用于在数据集之间导航，支持带有“第一页”、“最后一页”、“下一页”、“上一页”按钮和快速跳转字段的布局。</p>
  </GalleryCard>

  <GalleryCard header="ProgressBar" href="progressbar" image="/img/components/ProgressBar.png">
    <p>一个视觉上呈现任务或过程进度的组件，通常显示为一个横向条形，随着进度的增加而填充。</p>
  </GalleryCard>

  <GalleryCard header="Slider" href="slider" image="/img/components/Slider.png">
    <p>一个允许用户通过沿轨道拖动手柄从定义范围内选择值的组件。</p>
  </GalleryCard>

  <GalleryCard header="BusyIndicator" href="busyindicator" image="/img/components/BusyIndicator.png">
    <p>一个全应用的视觉指示器，通常是一个旋转器，表明全局过程正在进行。</p>
  </GalleryCard>

  <GalleryCard header="Loading" href="loading" image="/img/components/Loading.png">
    <p>一个特定范围内的加载指示器，在特定父组件中显示，指示该部分正在加载内容或数据。</p>
  </GalleryCard>

  <GalleryCard header="Spinner" href="spinner" image="/img/components/Spinner.png">
    <p>一个显示旋转动画的组件，通常用于指示过程或操作正在进行。</p>
  </GalleryCard>

  <GalleryCard header="AppNav" href="appnav" image="/img/components/AppNav.png" effect="slideFromLeft">
    <p>一个为应用程序提供导航菜单的组件，通常用于列出链接或导航项，方便在不同部分或视图之间切换。</p>
  </GalleryCard>

  <GalleryCard header="Icon" href="icon" image="/img/components/Icons.png">
    <p>一个显示图形符号或图像的组件，通常用于在用户界面中表示动作、状态或类别。</p>
  </GalleryCard>

  <GalleryCard header="Terminal" href="terminal" image="/img/components/Terminal.png">
    <p>一个在应用程序中模拟命令行界面（CLI）的组件，允许用户输入和执行基于文本的命令。</p>
  </GalleryCard>
  
  <GalleryCard header="InfiniteScroll" href="infinitescroll" image="/img/components/InfiniteScroll.png">
    <p>一个在滚动时加载更多项目的组件，显示加载指示器，并跟踪所有内容是否已抓取。</p>
  </GalleryCard>

  <GalleryCard header="Refresher" href="refresher" image="/img/components/Refresher.png">
    <p>一个允许在可滚动容器内进行下拉刷新交互的组件——非常适合动态数据加载。</p>
  </GalleryCard>

  <GalleryCard header="Tree" href="tree" image="/img/components/Tree.png">
    <p>一个用于显示层次数据的组件，允许用户展开、折叠并与嵌套项目进行交互。</p>
  </GalleryCard>
  
  <GalleryCard header="Avatar" href="avatar" image="/img/components/Avatar.png">
    <p>一个用于显示用户头像或首字母的组件，支持不同的大小、形状和主题。</p>
  </GalleryCard>
  
</GalleryGrid>
