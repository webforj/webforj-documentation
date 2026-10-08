---
title: Upload
sidebar_position: 160
description: >-
  Select and upload one or more files from the local machine with the Upload
  component using drag-and-drop, filters, and per-file or batch event tracking.
_i18n_hash: df26b1e4b51f3ba6ece7602ca1a1f33f
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-upload" />
<DocChip chip='since' label='26.01' />
<JavadocLink type="foundation" location="com/webforj/component/upload/Upload" top='true'/>

`Upload` 组件是一个内联文件选择器，允许用户从本地计算机选择一个或多个文件并将其发送到服务器。与 [`FileUploadDialog`](/docs/components/option-dialogs/file-upload) 不同，后者在模态框中展示选择器，阻止用户在完成之前进行其他操作，而 `Upload` 组件则直接渲染在页面布局中。它适合放置在文件输入应该出现的任何地方：个人资料表单、评论框旁的附件字段或媒体管理页面上的拖放区域。

<!-- INTRO_END -->

:::tip 使用 `Upload` 的时机
当文件选择与工作流程中的其他操作（如编辑个人资料或创建帖子）相伴时，请使用 `Upload` 组件。当文件上传必须是模态时，例如在用户可以继续之前严格需要上传文件，请使用 [`FileUploadDialog`](/docs/components/option-dialogs/file-upload)。
:::

## 创建上传 {#creating-an-upload}

默认情况下，`Upload` 组件显示一个选择按钮、一个拖放区域、当前文件列表和一个上传按钮。取消按钮默认隐藏。创建 `Upload` 后，您可以添加过滤器，例如允许的文件类型，并更改可见的部分。

```java
Upload upload = new Upload();
upload.addFilter("图像", "*.png;*.jpg");
upload.setVisible(false, Upload.Part.LIST);
layout.add(upload);
```

以下示例将简历 `Upload` 拖放到招聘表单中，旁边有一个姓名字段和提交按钮。

<ComponentDemo
path='/webforj/upload'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadView.java',
  'src/main/frontend/css/upload/upload.css'
]}
height='550px'
/>

## 选择文件 {#picking-files}

选择器的行为由几个独立的设置控制：用户一次可以选择多少文件、可以从本地文件系统中选择什么，以及文件对话框中可见的类型。这些共同构成了选择体验，以适应字段。

以下是一个配置了图像和视频过滤器、多文件选择和 20 文件限制的画廊上传器：

<ComponentDemo
path='/webforj/uploadpickingfiles'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadPickingFilesView.java',
  'src/main/frontend/css/upload/upload.css'
]}
height='450px'
/>

### 选择模式 {#selection-mode}

选择模式限制选择器为一个文件或多个文件。`MULTIPLE` 是默认模式，适合用于批处理操作，如照片图库或发票附件。`SINGLE` 适合概念上只持有一个值的字段，例如个人资料照片或签署的合同。

```java
upload.setSelectionMode(Upload.SelectionMode.SINGLE);
upload.setSelectionMode(Upload.SelectionMode.MULTIPLE);
```

### 选择器源 {#picker-source}

选择器源决定用户可以从本地文件系统中选择什么。默认设置 `FILES` 打开标准文件对话框。`DIRECTORY` 让用户选择一个文件夹并上传其顶级文件。`DIRECTORY_RECURSIVE` 遍历整个树并上传内部的每个文件。

```java
upload.setPicker(Upload.Picker.DIRECTORY_RECURSIVE);
```

目录上传适合镜像文件夹结构的工具，例如部署系统、资产管理应用程序或备份工具。对于大多数表单字段，默认文件选择器是合适的选择。

### 过滤器 {#filters}

过滤器限制用户可以从本地文件系统中选择的内容。每个过滤器都有描述和一个或多个用分号分隔的通配符模式。活动过滤器会在选择器按钮旁边的下拉菜单中显示，用户可以在它们之间切换。

```java
upload.addFilter("图像", "*.png;*.jpg;*.jpeg");
upload.addFilter("文档", "*.pdf;*.docx");
upload.setActiveFilter("图像");
```

一些相关设置影响过滤器下拉菜单的行为：`setFiltersVisible(false)` 隐藏下拉菜单，同时保持过滤器活动，`setMultiFilterSelection(true)` 允许用户组合过滤器，`setAllFilesFilterEnabled(false)` 则移除隐含的 "所有文件" 选项。

其中一些设置仅适用于标准选择器。当使用文件系统访问 API 时，原生操作系统选择器会自行管理过滤器选择，因此 `setFiltersVisible(false)` 将被忽略，`setMultiFilterSelection(true)` 不会产生效果（原生选择器一次只接受一个过滤器）。可通过 `setFileSystemAccess(false)` 禁用文件系统访问 API，以确保这些设置在不同浏览器间的可靠性。

### 拖放区域 {#drop-zone}

文件可以从桌面拖放到组件上。当文件悬停在拖放标签上时，标签会改变，表示拖放将被接受。默认情况下开启拖放功能，但可以在选择器应该只接受文件对话框中的文件时禁用此功能。

```java
upload.setDrop(false);
```

## 验证和限制 {#validation-and-limits}

`setMaxFileSize` 限制单个文件的字节大小，而 `setMaxFiles` 限制批处理中的文件总数。这两个限制在任何字节传输之前运行，因此，超大文件在客户端会被拒绝，而不消耗带宽。

```java
upload.setMaxFileSize(5 * 1024 * 1024); // 5 MB
upload.setMaxFiles(10);
```

当选择或拖放的文件超过任一上限时，会触发 `UploadRejectEvent` 事件，并给出原因。服务器端的 `webforj.fileUpload.maxSize` 属性仍然适用，并作为一个强硬的限制，无论客户端限制是什么。

:::warning 服务器端验证
过滤器、最大大小和最大文件计数在 UI 中强制执行，以指导用户，而不是保护服务器。每个上传的文件在存储之前都应在服务器上重新检查，并且临时文件应在上传完成后短时间内移动或删除。
:::

## 上传行为 {#upload-behavior}

一旦选择了文件，还有两个决策：何时开始上传，以及当用户再次选择文件时，现有条目会发生什么。默认情况下，用户单击 **上传** 按钮以开始传输，现有条目会保留在列表中，直到用户明确清除。

### 自动上传 {#auto-upload}

默认模式为 `NONE`，用户单击 **上传** 开始传输。`setAutoUpload()` 移除这一点击操作，并在选择、拖放或两者都发生时立即开始传输。

- **`NONE`** 由用户单击 **上传** 进行上传。
- **`ON_SELECT`** 在通过文件对话框选定文件后立即上传。
- **`ON_DROP`** 在文件拖放到组件上后立即上传。
- **`ALWAYS`** 同时涵盖两条路径。

:::tip 与预设配对
自动上传与 `BUTTON_ONLY` 或 `INLINE` 预设很好配对，因为这些情况下用户根本没有上传按钮可点击。对于用户需要在发送之前回顾选择的工作流，关闭自动上传功能。
:::

### 自动清除 {#auto-clear}

当用户选择新的批次时，自动清除决定应如何处理列表中已有的条目。清除在下次选择时发生，而不是在上传完成时发生，因此已完成的上传在用户再次选择时仍然可见。

- **`COMPLETED`** 清除成功上传的条目。
- **`IN_PROGRESS`** 撤销并清除仍在传输中的条目。
- **`ALL`** 清除所有条目。
排队中的条目如果尚未开始上传则会被保留，无论设置如何。

```java
upload.setAutoClear(Upload.AutoClear.COMPLETED);
upload.setAutoClear(Upload.AutoClear.IN_PROGRESS);
upload.setAutoClear(Upload.AutoClear.ALL);
```

:::warning 自动清除的微妙触发
自动清除仅在先前选择的文件实际开始上传或完成后生效。在未上传之间，列表不会匹配过滤器，并且列表会不断增长。
:::

在需要在多个操作中保持屏幕上显示的上传器中（例如每条消息都有其附件的聊天构建器或重复使用的评论表单）使用 `COMPLETED`。如果没有该设置，随着用户的工作，之前成功的列表会不断累积。

### 编程操作 {#programmatic-actions}

大多数上传从用户点击开始，但相同的操作也可以从服务器代码中执行。这两种操作都是在用户已经选择的文件上进行的；没有办法从服务器为用户选择文件。

```java
// 像用户点击上传一样上传当前选择的文件
upload.upload();

// 取消任何正在进行的传输
upload.cancel();
```

调用 `upload()` 以从组件外部的控制触发传输，例如由较大表单共享的单个提交按钮。调用 `cancel()` 来自组件外部的“停止”按钮，或在用户在传输过程中导航离开时来自路由保护。

## 移动捕捉 {#mobile-capture}

在移动设备上，捕捉会打开相机或麦克风作为选择器源，而不是文件浏览器。`USER` 针对前置相机或麦克风，`ENVIRONMENT` 针对后置相机，`NONE`（默认）使用标准文件选择器。

```java
upload.setCapture(Upload.Capture.ENVIRONMENT);
upload.addFilter("照片", "*.jpg;*.png");
```

:::tip 捕捉和过滤器
通过限制选择为图像扩展，使相机以静态模式打开，或限制为视频扩展，以便打开录制模式。没有相应的过滤器，捕捉模式在大多数平台上会回退到标准选择器。桌面浏览器会完全忽略捕捉设置。
:::

对于移动优先的应用，捕捉与 [可安装应用](/docs/configuration/installable-apps) 很好地配对，使相机和麦克风成为主屏幕体验的自然组成部分。

## 原生文件系统访问 {#native-file-system-access}

该组件在平台支持的情况下使用浏览器的 [文件系统访问 API](https://developer.mozilla.org/en-US/docs/Web/API/File_System_Access_API)。原生选择器可以授予页面对文件夹的持久权限，因此用户只需选择一次，随后从同一文件夹的上传将跳过对话框。在不支持该功能的浏览器中，组件会自动回退到标准选择器。

```java
upload.setFileSystemAccess(false); // 强制使用标准选择器
```

在每次上传都应该从新对话框开始，或在不同浏览器间需要一致行为更重要于持久权限便利时，禁用此功能。

## 自定义布局 {#customizing-the-layout}

该组件由五个部分构成：选择按钮、拖放标签、文件列表、上传按钮和取消按钮。前四个默认情况下可见；取消按钮隐藏，并可以通过 `setVisible(true, Upload.Part.CANCEL_BUTTON)` 显示。布局可以通过通用选择器形状的预设或通过各部分可见性控制进行细微的调整。

### 预设 {#presets}

预设将几个部分可见性设置捆绑到命名的选择器形状中。它们比单独切换部分更快地达到常见配置。

- **`FULL`**：选择按钮、拖放标签、文件列表和上传按钮。默认设置。
- **`INLINE`**：选择按钮和拖放标签，当前选择的文本呈现在选择器旁边。适用于紧凑的表单字段。
- **`BUTTON_ONLY`**：单独的选择按钮。当周围的 UI 已显示选定的文件时非常有用。
- **`DROPZONE`**：拖放标签和文件列表，没有选择按钮。当拖放应该是添加文件的唯一方式时非常有用。
- **`HEADLESS`**：所有部分隐藏，外边框、圆角和填充会合并，以使投影内容完全放置在组件边界内。

```java
upload.setPreset(Upload.Preset.INLINE);
```

<ComponentDemo
path='/webforj/uploadpresets'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadPresetsView.java',
]}
height='650px'
/>

### 部分可见性 {#part-visibility}

当预设接近但不完全达到期望形状时，可以显示或隐藏个别部分。这对于小的调整很有用，例如在即时上传的单文件选择器上隐藏取消按钮，或在仍允许拖放的仅按钮字段上隐藏拖放标签。当一起使用 `setPreset()` 和 `setVisible()` 时，请首先调用 `setPreset()`。

```java
upload.setVisible(false, Upload.Part.DROP_LABEL);
upload.setVisible(false, Upload.Part.CANCEL_BUTTON);
```

### 默认插槽 {#default-slot}

`Upload` 实现了 `HasComponents`。通过 `add()` 添加的子组件将在拖放区域内呈现，并叠加在标准样式上。结合 `HEADLESS` 预设，插槽允许您完全接管视觉表面，同时保持选择器、拖放和上传行为完整。

```java
upload.setPreset(Upload.Preset.HEADLESS);
upload.add(new Table<>());
```

在以下示例中，`HEADLESS` 预设用于将 `Table` 投影到 Upload 的边界内。拖放一个 CSV 文件，其行会直接呈现在组件中，列的生成基于文件的表头行。

<ComponentDemo
path='/webforj/uploaddefaultslot'
files={['src/main/java/com/webforj/samples/views/upload/UploadDefaultSlotView.java']}
height='400px'
/>

## 事件 {#events}

`Upload` 在三个层次上发出事件：用户对整个组件所做的操作、单个文件的传输状态，以及整个批次的生命周期。大多数应用根据需要在这些层次上注册几个监听器。一个表单可能只需要 `onUpload` 来了解文件何时到达服务器；一个具有进度 UI 的上传器需要 `onListProgress` 和 `onComplete`；一个必须上报拒绝的拖放区块需要 `onReject`。

大多数携带文件的事件都暴露了 `getFile()`（有效负载中的第一个或唯一文件）和 `getFiles()`（完整列表）。对于单文件事件，例如 `onReject`，使用 `getFile()`；而在您预期一个批次时，使用 `getFiles()`。`UploadCompleteEvent` 是例外；它有自己独特的 `getUploadedFiles()` 和 `getFailedFiles()` 访问器，因为批次结果在成功和失败之间分开。

### 用户操作 {#user-actions}

这些事件在用户对整个组件执行某项操作时触发。它们并不涉及传输进度，仅表明用户已执行某项应用可能希望对此作出反应的操作。

| 事件 | 触发时机 |
| --- | --- |
| `UploadChangeEvent` | 当选择的文件列表发生变化时 |
| `UploadEvent` | 当用户点击 **上传** 并且文件到达服务器时 |
| `UploadCancelEvent` | 当用户点击 **取消** 时 |
| `UploadFilterChangeEvent` | 当活动过滤器发生变化时 |

```java
upload.onChange(e -> {
    // 每当选择的文件列表发生变化时触发。
    List<UploadedFile> files = e.getFiles();
});

upload.onUpload(e -> {
    // 当上传被触发时触发；文件已达到服务器。
});
```

`UploadEvent` 和 `UploadCompleteEvent` 在表面上看起来很相似，但回答的是不同的问题。`UploadEvent` 在用户明确触发上传时触发（或者 `setAutoUpload()` 代表他们触发时），是持久化或传递上传文件的自然位置。`UploadCompleteEvent` 在每个排队文件的传输完成后触发，是进行“批次完成” UI 更新的正确钩子。

### 每文件传输 {#per-file-transfer}

这些事件一次针对每个文件，在传输进行时或在传输失败后立即触发。当 UI 需要反映个别文件的状态而不是批次时，请使用它们。

| 事件 | 触发时机 |
| --- | --- |
| `UploadProgressEvent` | 单个文件正在传输时 |
| `UploadErrorEvent` | 当单个文件传输失败时 |
| `UploadRejectEvent` | 当选定或拖放的文件未满足配置限制时 |

```java
upload.onProgress(e -> {
    // 在单个文件传输期间重复触发。
    double percent = e.getProgress();
});

upload.onReject(e -> {
    // 当文件因大小、数量或过滤器原因被拒绝时触发。
    String reason = e.getMessage();
});
```

在这一组中，`UploadRejectEvent` 是不同的。它在任何字节移动之前触发，当文件未通过客户端限制（例如 `setMaxFileSize` 或 `setMaxFiles`）时。而 `UploadErrorEvent` 则在传输开始后触发，并且在到达服务器的过程中出现问题。

### 整个批次 {#whole-batch}

这些事件针对批次而不是任何一个文件触发。用于聚合 UI，例如整体进度条或汇总整个选择的“完成”消息。

| 事件 | 触发时机 |
| --- | --- |
| `UploadListProgressEvent` | 在 `UploadProgressEvent` 触发时，携带整个列表的状态 |
| `UploadCompleteEvent` | 每批次一次，当每个文件都完成传输时 |

```java
upload.onComplete(e -> {
    // 当整个批次完成时触发一次。
    List<UploadedFile> succeeded = e.getUploadedFiles();
    List<UploadedFile> failed = e.getFailedFiles();
});
```

`onProgress` 和 `onListProgress` 从两个角度覆盖同一传输。`onProgress` 是每个文件的，并且是每个文件都有自己的进度 UI 时的正确钩子。`onListProgress` 与之并行触发，提供聚合计数器（`getListTotal`、`getListRemaining`、`getListProgress`）用作批次的单一指示器。

在以下示例中，`onChange`、`onListProgress` 和 `onComplete` 驱动着一个进度条和状态行，随着文件列表变化和文件传输而更新。

<ComponentDemo
path='/webforj/uploadevents'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadEventsView.java',
]}
height='450px'
/>

## 国际化 (i18n) {#internationalization-i18n}

组件内的标签和消息可以通过 `FileUploadI18n` 包进行自定义。该包类型保留 `FileUploadI18n` 名称，因为它与模态 [`FileUploadDialog`](/docs/components/option-dialogs/file-upload) 共享。

```java
FileUploadI18n bundle = new FileUploadI18n();
bundle.setUpload("发送");
bundle.setCancel("丢弃");
bundle.setDropFile("将文件拖到这里");
upload.setI18n(bundle);
```

## 主题 {#themes}

`UploadTheme` 反映标准 DWC 主题调色板，并包括轮廓变体，具有较轻的视觉重量。主题适用于选择器、上传和取消按钮。列表和拖放区域保持中性样式，无论主题如何。

```java
upload.setTheme(UploadTheme.PRIMARY);
upload.setTheme(UploadTheme.SUCCESS);
upload.setTheme(UploadTheme.OUTLINED_GRAY);
```

下面的演示展示了 `PRIMARY` 主题与 `INLINE` 预设结合使用。

<ComponentDemo
path='/webforj/uploadthemes'
files={['src/main/java/com/webforj/samples/views/upload/UploadThemesView.java']}
height='200px'
/>

## 样式 {#styling}

<TableBuilder name="Upload" />
