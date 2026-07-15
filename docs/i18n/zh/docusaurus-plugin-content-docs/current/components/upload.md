---
title: Upload
sidebar_position: 160
sidebar_class_name: new-content
description: >-
  Select and upload one or more files from the local machine with the Upload
  component using drag-and-drop, filters, and per-file or batch event tracking.
_i18n_hash: 76f8c00c7754fed0a87c27e7963e2877
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-upload" />
<DocChip chip='since' label='26.01' />
<JavadocLink type="foundation" location="com/webforj/component/upload/Upload" top='true'/>

`Upload` 组件是一个内联文件选择器，允许用户从本地计算机选择一个或多个文件并将其发送到服务器。与 [`FileUploadDialog`](/docs/components/option-dialogs/file-upload) 不同，它在用户完成之前不会阻塞应用程序，而是直接在页面布局中渲染。它适合放在文件输入应该存在的任何地方：个人资料表单、评论框旁的附件字段，或媒体管理页面上的拖放区域。

<!-- INTRO_END -->

:::tip 何时使用 `Upload`
当文件选择伴随工作流程中的其他操作时，例如编辑个人资料或创建帖子时，使用 `Upload` 组件。相反，当上传必须是模态窗口时，例如在用户可以继续之前需要严格要求文件时，可以选择 [`FileUploadDialog`](/docs/components/option-dialogs/file-upload)。
:::

## 创建上传 {#creating-an-upload}

默认情况下，`Upload` 组件显示一个选择按钮、一个拖放区域、当前文件列表和一个上传按钮。取消按钮默认是隐藏的。创建 `Upload` 后，您可以添加过滤器，例如允许的文件类型，并更改可见的部分。

```java
Upload upload = new Upload();
upload.addFilter("Images", "*.png;*.jpg");
upload.setVisible(false, Upload.Part.LIST);
layout.add(upload);
```

以下示例在招聘表单中放置一个简历 `Upload`，与姓名字段和提交按钮并列。

<ComponentDemo
path='/webforj/upload'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadView.java',
  'src/main/frontend/css/upload/upload.css'
]}
height='550px'
/>

## 选择文件 {#picking-files}

选择器的行为由几个独立设置控制：用户可以一次选择多少文件，从本地文件系统可选择哪些内容，以及在文件对话框中可见哪些类型。这些设置共同塑造选择体验，以适应字段。

这是一个配置了图像和视频过滤器的画廊上传器，支持多文件选择，文件上限为20个：

<ComponentDemo
path='/webforj/uploadpickingfiles'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadPickingFilesView.java',
  'src/main/frontend/css/upload/upload.css'
]}
height='450px'
/>

### 选择模式 {#selection-mode}

选择模式限制选择器只能选择一个文件还是多个文件。`MULTIPLE` 是默认模式，适合批处理操作，例如照片库或发票附件。`SINGLE` 适用于概念上只包含一个值的字段，例如个人资料照片或签名合同。

```java
upload.setSelectionMode(Upload.SelectionMode.SINGLE);
upload.setSelectionMode(Upload.SelectionMode.MULTIPLE);
```

### 选择器来源 {#picker-source}

选择器来源决定用户可以从本地文件系统中选择什么。默认的 `FILES` 打开标准文件对话框。`DIRECTORY` 允许用户选择一个文件夹并上传其顶层文件。`DIRECTORY_RECURSIVE` 则遍历整个树形结构并上传内部的所有文件。

```java
upload.setPicker(Upload.Picker.DIRECTORY_RECURSIVE);
```

目录上传适合于镜像文件夹结构的工具，例如部署系统、资产管理应用程序或备份工具。对于大多数表单字段，默认文件选择器是正确的选择。

### 过滤器 {#filters}

过滤器限制用户可以从本地文件系统中选择的内容。每个过滤器都有一个描述和一个或多个用分号分隔的通配符模式。活动过滤器会出现在选择器按钮旁的下拉框中，用户可以在它们之间切换。

```java
upload.addFilter("Images", "*.png;*.jpg;*.jpeg");
upload.addFilter("Documents", "*.pdf;*.docx");
upload.setActiveFilter("Images");
```

一些相关设置会影响过滤器下拉框的行为：`setFiltersVisible(false)` 会隐藏下拉框同时保持过滤器活动，`setMultiFilterSelection(true)` 允许用户组合过滤器，`setAllFilesFilterEnabled(false)` 则去除隐含的“所有文件”选项。

其中一些设置仅适用于标准选择器。当使用文件系统访问 API 时，本机操作系统选择器会自行管理过滤器选择，因此 `setFiltersVisible(false)` 被忽略，`setMultiFilterSelection(true)` 没有效果（本机选择器一次仅接受一个过滤器）。通过 `setFileSystemAccess(false)` 禁用文件系统访问 API 以在各浏览器间使这些设置变得可靠。

### 拖放区域 {#drop-zone}

文件可以从桌面拖动并在组件上放下。当文件悬停在其上时，放下标签会改变，表示接受放下。拖放默认是开启的，可以在选择器只接受文件来自文件对话框时禁用。

```java
upload.setDrop(false);
```

## 验证和限制 {#validation-and-limits}

`setMaxFileSize` 限制单个文件的字节大小，`setMaxFiles` 限制批处理中的文件总数。这两者会在任何字节传输前运行，因此超出大小的文件会在客户端被拒绝，而不会消耗带宽。

```java
upload.setMaxFileSize(5 * 1024 * 1024); // 5 MB
upload.setMaxFiles(10);
```

当选择或拖放的文件超过任一上限时，会触发 `UploadRejectEvent` 事件并给出原因。服务器端的 `webforj.fileUpload.maxSize` 属性仍然适用并作为硬性上限，无论客户端限制如何。

:::warning 服务器端验证
过滤器、最大大小和最大文件计数在 UI 中得到强制执行，以指导用户，而不是保护服务器。每个上传的文件应该在存储之前在服务器上重新检查，并且临时文件应该在上传完成后不久被移动或删除。
:::

## 上传行为 {#upload-behavior}

一旦选择了文件，剩下的两个决策是：何时开始上传，以及用户再次选择时现有条目会发生什么。默认情况下，用户点击 **上传** 开始传输，现有条目会留在列表中，直到明确清除。

### 自动上传 {#auto-upload}

默认模式是 `NONE`，用户点击 **上传** 开始传输。`setAutoUpload()` 会去掉该点击，开始传输，一旦选择了文件、拖放或两者。

- **`NONE`** 留给用户上传，用户点击 **上传**。
- **`ON_SELECT`** 在通过文件对话框选择文件时立即上传。
- **`ON_DROP`** 在文件拖放到组件上时立即上传。
- **`ALWAYS`** 覆盖这两种情况。

:::tip 配对预设
自动上传与 `BUTTON_ONLY` 或 `INLINE` 预设搭配良好，这里用户不需要点击上传按钮。对于用户在发送前需要审核选择的工作流，保持自动上传关闭。
:::

### 自动清除 {#auto-clear}

当用户选择新的一批时，自动清除决定如何处理列表中已存在的条目。清除发生在下次选择的时刻，而不是在上传完成时，因此已完成的上传会在用户再次选择之前保持可见。

- **`COMPLETED`** 清除成功上传的条目。
- **`IN_PROGRESS`** 取消并清除仍在传输中的条目。
- **`ALL`** 清除所有条目。
排队中尚未开始上传的条目会根据设置被保留。

```java
upload.setAutoClear(Upload.AutoClear.COMPLETED);
upload.setAutoClear(Upload.AutoClear.IN_PROGRESS);
upload.setAutoClear(Upload.AutoClear.ALL);
```

:::warning 自动清除有微妙触发
自动清除仅在之前选择的文件实际开始上传或上传完成后才生效。在选择之间没有上传时，没有文件与过滤器匹配，列表不断增长。
:::

在那些在多个操作中持续显示在屏幕上的上传器中可以使用 `COMPLETED`，例如每条消息都有各自附件的聊天 Composer，或每个回复重复使用的评论表单。如果没有它，用户操作时之前成功的列表会不断累积。

### 编程操作 {#programmatic-actions}

大多数上传从用户点击开始，但相同的操作也可以从服务器代码中调用。两者都在用户已经选定的文件上操作；没有方法可以在服务器上代替用户选择文件。

```java
// 像用户点击上传一样上传当前选择
upload.upload();

// 取消任何正在进行的传输
upload.cancel();
```

调用 `upload()` 以在组件外部从控件触发传输，例如通过一个更大表单共享的单个提交按钮。调用 `cancel()` 来自组件外部的“停止”按钮，或在用户中途导航时的路由守卫。

## 移动捕获 {#mobile-capture}

在移动设备上，捕获会打开相机或麦克风作为选择器来源，而不是文件浏览器。`USER` 目标前摄像头或麦克风，`ENVIRONMENT` 目标后摄像头，`NONE`（默认）使用标准文件选择器。

```java
upload.setCapture(Upload.Capture.ENVIRONMENT);
upload.addFilter("Photo", "*.jpg;*.png");
```

:::tip 捕获和过滤器
将选择限制为图像扩展名，以便相机以静态模式打开，或限制为视频扩展名，以便它进入录制模式。如果没有相应的过滤器，捕获模式在大多数平台上会退回到标准选择器。桌面浏览器完全忽略捕获设置。
:::

对于移动优先应用程序，捕获与 [可安装应用程序](/docs/configuration/installable-apps) 结合使用，摄像头和麦克风成为主屏幕体验的自然组成部分。

## 原生文件系统访问 {#native-file-system-access}

当平台支持时，组件使用浏览器的 [文件系统访问 API](https://developer.mozilla.org/en-US/docs/Web/API/File_System_Access_API)。本机选择器可以授予页面对某个文件夹的持久权限，因此用户选择一次后，从同一文件夹的后续上传可跳过对话框。在不支持的浏览器上，组件会自动退回到标准选择器。

```java
upload.setFileSystemAccess(false); // 强制使用标准选择器
```

当每次上传都应从新的对话框开始，或在需要跨所有浏览器保持一致的行为比持久权限的便利性更重要时，关闭它。

## 自定义布局 {#customizing-the-layout}

该组件由五个部分构成：选择按钮、拖放标签、文件列表、上传按钮和取消按钮。前四个默认可见；取消按钮是隐藏的，可以通过 `setVisible(true, Upload.Part.CANCEL_BUTTON)` 显示。可以使用预设对常见选择器形状的布局进行重塑，或使用按部分可见性控件进行更细微的调整。

### 预设 {#presets}

预设把几个部分可见性设置捆绑成命名的选择器形状。这比单独切换部分更快地达到常见配置。

- **`FULL`**：选择按钮、拖放标签、文件列表和上传按钮。默认值。
- **`INLINE`**：选择按钮和拖放标签，当前选择作为文本渲染在选择器旁边。适用于紧凑表单字段。
- **`BUTTON_ONLY`**：单独的选择按钮。当周围的 UI 已经显示所选文件时很有用。
- **`DROPZONE`**：拖放标签和文件列表，没有选择按钮。当拖放应该是添加文件的唯一方式时很有用。
- **`HEADLESS`**：每一部分都隐藏，外部边框、半径和填充折叠，使投影内容与组件边界完全贴合。

```java
upload.setPreset(Upload.Preset.INLINE);
```

<ComponentDemo
path='/webforj/uploadpresets'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadPresetsView.java',
  'src/main/frontend/css/upload/uploadPresets.css'
]}
height='650px'
/>

### 部分可见性 {#part-visibility}

当预设接近但不完全符合所需形状时，可以显示或隐藏单独部分。这对于较小的调整很有用，例如在单文件选择器上隐藏取消按钮或在仅按钮字段上隐藏拖放标签，该字段仍允许拖放。当一起使用 `setPreset()` 和 `setVisible()` 时，先调用 `setPreset()`。

```java
upload.setVisible(false, Upload.Part.DROP_LABEL);
upload.setVisible(false, Upload.Part.CANCEL_BUTTON);
```

### 默认插槽 {#default-slot}

`Upload` 实现了 `HasComponents`。通过 `add()` 添加的子组件会渲染在拖放区域内，位于标准外观之上。结合 `HEADLESS` 预设，插槽使您可以完全控制可视表面，同时保持选择器、拖放和上传行为完整。

```java
upload.setPreset(Upload.Preset.HEADLESS);
upload.add(new Table<>());
```

在以下示例中，使用 `HEADLESS` 预设将 `Table` 投影到 Upload 的边界内。拖放一个 CSV，其行会直接在组件内部渲染，列由文件的标题行构建。

<ComponentDemo
path='/webforj/uploaddefaultslot'
files={['src/main/java/com/webforj/samples/views/upload/UploadDefaultSlotView.java']}
height='400px'
/>

## 事件 {#events}

`Upload` 在三个层级上发出事件：用户对整个组件的操作、单个文件的传输状态，以及整个批次的生命周期。大多数应用程序通常会根据需要在这些层级上注册一些监听器。一个表单可能只需要 `onUpload` 来知道文件何时到达服务器；一个具有进度 UI 的上传器需要 `onListProgress` 和 `onComplete`；一个必须显示拒绝的拖放区则需要 `onReject`。

大多数承载文件的事件都公开 `getFile()`（有效负载中的第一个或唯一文件）和 `getFiles()`（完整列表）。对于像 `onReject` 这样的单文件事件使用 `getFile()`，对于预期批次的情况使用 `getFiles()`。`UploadCompleteEvent` 是例外；它有自己的 `getUploadedFiles()` 和 `getFailedFiles()` 访问器，因为批次结果被成功和失败分开。

### 用户操作 {#user-actions}

这些事件响应用户在组件上所做的操作。这些事件不会反映任何传输进度，仅表示用户做了应用程序可能想要响应的事情。

| 事件 | 触发条件 |
| --- | --- |
| `UploadChangeEvent` | 当所选文件列表发生变化时 |
| `UploadEvent` | 当用户点击 **上传**，文件到达服务器时 |
| `UploadCancelEvent` | 当用户点击 **取消** 时 |
| `UploadFilterChangeEvent` | 当活动过滤器发生变化时 |

```java
upload.onChange(e -> {
    // 每当所选文件列表变化时触发。
    List<UploadedFile> files = e.getFiles();
});

upload.onUpload(e -> {
    // 当触发上传时触发；文件已到达服务器。
});
```

`UploadEvent` 和 `UploadCompleteEvent` 表面上看起来相似，但回答的是不同的问题。`UploadEvent` 在用户显式触发上传时触发（或者 `setAutoUpload()` 代表用户触发它），是持久化或转交上传文件的自然位置。`UploadCompleteEvent` 在每个排队文件的传输完成后触发，是进行“批次完成” UI 更新的正确钩子。

### 每个文件的传输 {#per-file-transfer}

这些事件在传输发生时或在传输失败后每个文件触发一次。当 UI 需要反映单个文件的状态而不是批次时，使用它们。

| 事件 | 触发条件 |
| --- | --- |
| `UploadProgressEvent` | 当单个文件正在传输时 |
| `UploadErrorEvent` | 当单个文件传输失败时 |
| `UploadRejectEvent` | 当所选或拖放的文件未满足配置约束时 |

```java
upload.onProgress(e -> {
    // 在单个文件传输期间重复触发。
    double percent = e.getProgress();
});

upload.onReject(e -> {
    // 当文件因大小、计数或过滤原因被拒绝时触发。
    String reason = e.getMessage();
});
```

在这个组内，`UploadRejectEvent` 是个特殊情况。它在任何字节传输之前触发，当文件因 `setMaxFileSize` 或 `setMaxFiles` 等客户端约束失败时触发。相比之下，`UploadErrorEvent` 在传输开始后并且在传输到服务器中途出了问题时触发。

### 整个批次 {#whole-batch}

这些事件在批次上触发，而不是在任何一个文件上。使用它们进行聚合 UI，例如整体进度条或“完成”消息，总结整个选择。

| 事件 | 触发条件 |
| --- | --- |
| `UploadListProgressEvent` | 与 `UploadProgressEvent` 一起，表示整个列表的状态 |
| `UploadCompleteEvent` | 每批次一次，所有文件传输完成时触发 |

```java
upload.onComplete(e -> {
    // 当整个批次完成时触发一次。
    List<UploadedFile> succeeded = e.getUploadedFiles();
    List<UploadedFile> failed = e.getFailedFiles();
});
```

`onProgress` 和 `onListProgress` 从两个角度涵盖同一次传输。`onProgress` 是每个文件的事件，当每个文件有自己的进度 UI 时，这是合适的钩子。`onListProgress` 在它旁边触发，提供汇总计数器（`getListTotal`、`getListRemaining`、`getListProgress`）以供单个批次显示整体指示器。

在以下示例中，`onChange`、`onListProgress` 和 `onComplete` 驱动着一个进度条和状态行，它们在文件列表变化和文件传输时更新。

<ComponentDemo
path='/webforj/uploadevents'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadEventsView.java',
  'src/main/frontend/css/upload/uploadEvents.css'
]}
height='450px'
/>

## 国际化 (i18n) {#internationalization-i18n}

组件内的标签和消息可以通过 `FileUploadI18n` 包进行自定义。包类型保持 `FileUploadI18n` 名称，因为它与模态 [`FileUploadDialog`](/docs/components/option-dialogs/file-upload) 共享。

```java
FileUploadI18n bundle = new FileUploadI18n();
bundle.setUpload("发送");
bundle.setCancel("放弃");
bundle.setDropFile("将文件放在这里");
upload.setI18n(bundle);
```

## 主题 {#themes}

`UploadTheme` 反映了标准 DWC 主题调色板，并包括线框变体以减轻视觉重量。主题应用于选择器、上传和取消按钮。列表和拖放区域保持中性样式，无论主题如何。

```java
upload.setTheme(UploadTheme.PRIMARY);
upload.setTheme(UploadTheme.SUCCESS);
upload.setTheme(UploadTheme.OUTLINED_GRAY);
```

下面的演示展示了 `PRIMARY` 主题与 `INLINE` 预设相结合的效果。

<ComponentDemo
path='/webforj/uploadthemes'
files={['src/main/java/com/webforj/samples/views/upload/UploadThemesView.java']}
height='200px'
/>

## 样式 {#styling}

<TableBuilder name="Upload" />
