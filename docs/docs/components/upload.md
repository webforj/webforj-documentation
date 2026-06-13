---
title: Upload
sidebar_position: 160
sidebar_class_name: new-content
description: Select and upload one or more files from the local machine with the Upload component using drag-and-drop, filters, and per-file or batch event tracking.
---

<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-upload" />
<DocChip chip='since' label='26.01' />
<JavadocLink type="foundation" location="com/webforj/component/upload/Upload" top='true'/>


The `Upload` component is an inline file picker that lets the user select one or more files from their local machine and send them to the server. Unlike [`FileUploadDialog`](./option-dialogs/file-upload), which presents the picker in a modal that blocks the app until the user finishes, `Upload` renders directly in the page layout. It fits anywhere a file input belongs: a profile form, an attachment field next to a comment box, or a dropzone on a media management page.

<!-- INTRO_END -->
 
:::tip When to use Upload
Use `Upload` when file selection is part of a workflow the user is already in, like editing a profile or building a post. Reach for [`FileUploadDialog`](./option-dialogs/file-upload) instead when uploads should be modal, for example when a file is strictly required before the user can continue.
:::
 
Create an `Upload` and add it to a layout. The default configuration shows a select button, a drop area, the current file list, and upload and cancel controls.

```java
Upload upload = new Upload();
upload.addFilter("Images", "*.png;*.jpg");
layout.add(upload);
```
 
The example below drops a resume `Upload` into a hiring form, alongside a name field and submit button.
 
<ComponentDemo
path='/webforj/upload'
files={['src/main/java/com/webforj/samples/views/upload/UploadView.java']}
height='550px'
/>

## Picking files {#picking-files}

How the picker behaves is controlled by a few independent settings: how many files the user can pick at once, what's selectable from the local filesystem, and what types are visible in the file dialog. Together they shape the picking experience to fit the field.

 
Here's a gallery uploader configured with both image and video filters, multi-file selection, and a 20-file cap:

<ComponentDemo
path='/webforj/uploadpickingfiles'
files={['src/main/java/com/webforj/samples/views/upload/UploadPickingFilesView.java']}
height='450px'
/>

### Selection mode {#selection-mode}

Selection mode caps the picker at one file or many. `MULTIPLE` is the default and suits batch operations like photo galleries or invoice attachments. `SINGLE` fits fields that conceptually hold one value, like a profile photo or a signed contract.

```java
upload.setSelectionMode(Upload.SelectionMode.SINGLE);
upload.setSelectionMode(Upload.SelectionMode.MULTIPLE);
```

### Picker source {#picker-source}

The picker source determines what the user can select from the local filesystem. The default, `FILES`, opens a standard file dialog. `DIRECTORY` lets the user pick a folder and uploads its top-level files. `DIRECTORY_RECURSIVE` walks the entire tree and uploads every file inside.

```java
upload.setPicker(Upload.Picker.DIRECTORY_RECURSIVE);
```

Directory uploads suit tools that mirror folder structures, like deployment systems, asset management apps, or backup utilities. For most form fields, the default file picker is the right choice.

### Filters {#filters}

Filters limit what the user can pick from the local filesystem. Each filter has a description and one or more glob patterns separated by semicolons. The active filter appears in a dropdown next to the picker button, and the user can switch between them.

```java
upload.addFilter("Images", "*.png;*.jpg;*.jpeg");
upload.addFilter("Documents", "*.pdf;*.docx");
upload.setActiveFilter("Images");
```

A few related settings shape how the filter dropdown behaves: `setFiltersVisible(false)` hides the dropdown while keeping the filters active, `setMultiFilterSelection(true)` lets the user combine filters, and `setAllFilesFilterEnabled(false)` removes the implicit "All Files" option.

A couple of these settings only apply to the standard picker. When the File System Access API is in use, the native OS picker manages filter selection itself, so `setFiltersVisible(false)` is ignored and `setMultiFilterSelection(true)` has no effect (the native picker accepts only one filter at a time). Disable the File System Access API with `setFileSystemAccess(false)` to make those settings reliable across browsers.

### Drop zone {#drop-zone}

Files can be dragged from the desktop and dropped onto the component. The drop label changes when a file hovers over it, signaling that the drop will be accepted. Drop is on by default, and can be disabled when the picker should only accept files from the file dialog.

```java
upload.setDrop(false);
```

## Validation and limits {#validation-and-limits}

`setMaxFileSize` caps the byte size of a single file, and `setMaxFiles` caps the total number of files in a batch. Both run before any bytes are transferred, so an oversized file is rejected on the client without consuming bandwidth.

```java
upload.setMaxFileSize(5 * 1024 * 1024); // 5 MB
upload.setMaxFiles(10);
```

When a picked or dropped file exceeds either cap, `UploadRejectEvent` fires with the reason. The server-side `webforj.fileUpload.maxSize` property still applies and acts as a hard ceiling, regardless of the client-side limit.

:::warning Server-side validation
Filters, max size, and max file count are enforced in the UI to guide the user, not to protect the server. Every uploaded file should be re-checked on the server before being stored, and the temporary files should be moved or deleted shortly after the upload completes.
:::

## Upload behavior {#upload-behavior}
 
Once files are picked, two decisions remain: when the upload starts, and what happens to existing entries when the user picks again. By default the user clicks **Upload** to start the transfer, and existing entries stay in the list until they're explicitly cleared.
 
### Auto upload {#auto-upload}
 
The default mode is `NONE`, where the user clicks **Upload** to start the transfer. `setAutoUpload()` removes that click and starts the transfer as soon as files are picked, dropped, or both.
 
- **`NONE`** leaves uploading to the user, who clicks **Upload**.
- **`ON_SELECT`** uploads as soon as files are picked through the file dialog.
- **`ON_DROP`** uploads as soon as files are dropped onto the component.
- **`ALWAYS`** covers both paths.
 
:::tip Pairing with presets
Auto upload pairs well with the `BUTTON_ONLY` or `INLINE` presets, where there's no Upload button for the user to click anyway. For workflows where the user needs to review the selection before sending, leave auto upload off.
:::
 
### Auto clear {#auto-clear}
 
When the user picks a new batch, auto clear decides what to do with the entries already in the list. Clearing happens at the moment of the next pick, not on upload completion, so finished uploads stay visible until the user picks again.

- **`COMPLETED`** clears successfully uploaded entries.
- **`IN_PROGRESS`** cancels and clears entries still transferring.
- **`ALL`** clears everything.
Queued entries that haven't started uploading are kept regardless of the setting.
 
```java
upload.setAutoClear(Upload.AutoClear.COMPLETED);
upload.setAutoClear(Upload.AutoClear.IN_PROGRESS);
upload.setAutoClear(Upload.AutoClear.ALL);
```

:::warning Auto clear has subtle triggers
Auto clear only takes effect once a previously picked file has actually started uploading or finished. Without an upload between picks, no file matches the filter and the list keeps growing.
:::
 
Reach for `COMPLETED` in uploaders that live on screen across multiple actions, like a chat composer where every message has its own attachments, or a comment form that's reused for each reply. Without it, the list of previous successes accumulates as the user works.

### Programmatic actions {#programmatic-actions}

Most uploads start from a user click, but the same actions are available from server code:

```java
// Start uploading staged files
upload.upload();

// Cancel any in-progress transfers
upload.cancel();
```

Call `upload()` after staging files through some other path, like a server-side selection. Call `cancel()` from a "stop" button outside the component, or from a route guard when the user navigates away mid-transfer.

## Mobile capture {#mobile-capture}

On mobile devices, capture opens the camera or microphone as the picker source instead of the file browser. `USER` targets the front camera or microphone, `ENVIRONMENT` targets the rear camera, and `NONE` (the default) uses the standard file picker.

```java
upload.setCapture(Upload.Capture.ENVIRONMENT);
upload.addFilter("Photo", "*.jpg;*.png");
```

:::tip Capture and filters
Restrict selection to image extensions so the camera opens in still mode, or to video extensions so it opens in recording mode. Without a corresponding filter, a capture mode falls back to the standard picker on most platforms. Desktop browsers ignore the capture setting entirely.
:::

For mobile-first apps, capture pairs well with [installable apps](/docs/configuration/installable-apps), where the camera and microphone become a natural part of the home-screen experience.

## Native file system access {#native-file-system-access}

The component uses the browser's [File System Access API](https://developer.mozilla.org/en-US/docs/Web/API/File_System_Access_API) when the platform supports it. The native picker can grant the page persistent permission to a folder, so the user picks once and subsequent uploads from the same folder skip the dialog. On browsers without support, the component automatically falls back to the standard picker.

```java
upload.setFileSystemAccess(false); // force the standard picker
```

Turn it off when every upload should start from a fresh dialog, or when consistent behavior across every browser matters more than the persistent-permission convenience.

## Customizing the layout {#customizing-the-layout}

The component is built from five parts: the picker button, the drop label, the file list, the upload button, and the cancel button. The first four are visible by default; the cancel button is hidden and can be shown with `setVisible(true, Upload.Part.CANCEL_BUTTON)`. The layout can be reshaped with presets for common picker shapes, or with per-part visibility controls for finer adjustments.

### Presets {#presets}

Presets bundle several part visibility settings into named picker shapes. They're a faster way to reach a common configuration than toggling parts individually.

- **`FULL`**: Picker button, drop label, file list, and upload button. The default.
- **`INLINE`**: Picker button and drop label, with the current selection rendered as text next to the picker. Useful for compact form fields.
- **`BUTTON_ONLY`**: The picker button on its own. Useful when the surrounding UI already shows the selected files.
- **`DROPZONE`**: Drop label and file list, no picker button. Useful when drag-and-drop should be the only way to add files.
- **`HEADLESS`**: Every part hidden, with the outer border, radius, and padding collapsed so projected content sits flush inside the component bounds.

```java
upload.setPreset(Upload.Preset.INLINE);
```

<ComponentDemo
path='/webforj/uploadpresets'
files={['src/main/java/com/webforj/samples/views/upload/UploadPresetsView.java']}
height='650px'
/>

### Part visibility {#part-visibility}

When a preset gets close but not quite to the desired shape, individual parts can be shown or hidden. This is useful for small adjustments like hiding the cancel button on a single-file picker that uploads instantly, or hiding the drop label on a button-only field that still allows drops.

```java
upload.setVisible(false, Upload.Part.DROP_LABEL);
upload.setVisible(false, Upload.Part.CANCEL_BUTTON);
```

### Default slot {#default-slot}

`Upload` implements `HasComponents`. Children added through `add()` render inside the drop area, on top of the standard chrome. Combined with the `HEADLESS` preset, the slot lets you take over the visual surface entirely while keeping the picker, drop, and upload behavior intact.

```java
upload.setPreset(Upload.Preset.HEADLESS);
upload.add(new Table<>());
```

In the demo below, the `HEADLESS` preset is used to project a `Table` into the Upload's bounds. Drop a CSV and its rows render directly inside the component, with columns built from the file's header row.

<ComponentDemo
path='/webforj/uploaddefaultslot'
files={['src/main/java/com/webforj/samples/views/upload/UploadDefaultSlotView.java']}
height='400px'
/>

## Events {#events}
 
`Upload` emits events at three levels: things the user does to the whole component, the transfer state of a single file, and the lifecycle of the batch as a whole. Most apps register a couple of listeners across these tiers depending on what they need to react to. A form might only need `onUpload` to know when files reach the server; an uploader with a progress UI needs `onListProgress` and `onComplete`; a dropzone that has to surface rejections needs `onReject`.
 
Most events that carry files expose both `getFile()` (the first or only file in the payload) and `getFiles()` (the full list). Use `getFile()` for single-file events like `onReject`, and `getFiles()` when you expect a batch. `UploadCompleteEvent` is the exception; it has its own `getUploadedFiles()` and `getFailedFiles()` accessors since the batch result is split between successes and failures.
 
### User actions {#user-actions}
 
These fire in response to something the user does on the component as a whole. They don't say anything about transfer progress, just that the user has done something the app might want to react to.
 
| Event | Fires |
| --- | --- |
| `UploadChangeEvent` | When the list of picked files changes |
| `UploadEvent` | When the user clicks **Upload** and the files reach the server |
| `UploadCancelEvent` | When the user clicks **Cancel** |
| `UploadFilterChangeEvent` | When the active filter changes |
 
```java
upload.onChange(e -> {
    // Fires whenever the picked file list changes.
    List<UploadedFile> files = e.getFiles();
});
 
upload.onUpload(e -> {
    // Fires when the upload is triggered; files have reached the server.
});
```
 
`UploadEvent` and `UploadCompleteEvent` look similar at a glance, but they answer different questions. `UploadEvent` fires when the user explicitly triggers the upload (or `setAutoUpload()` triggers it on their behalf), and is the natural place to persist or hand off the uploaded files. `UploadCompleteEvent` fires once the transfer of every queued file has finished, and is the right hook for "the batch is done" UI updates.
 
### Per-file transfer {#per-file-transfer}
 
These fire once per file, while a transfer is happening or right after it fails. Use them when the UI needs to reflect the state of individual files rather than the batch.
 
| Event | Fires |
| --- | --- |
| `UploadProgressEvent` | While a single file is being transferred |
| `UploadErrorEvent` | When a single file transfer fails |
| `UploadRejectEvent` | When a picked or dropped file doesn't meet the configured constraints |
 
```java
upload.onProgress(e -> {
    // Fires repeatedly during a single file's transfer.
    int percent = (int) e.getProgress();
});
 
upload.onReject(e -> {
    // Fires when a file is rejected for size, count, or filter reasons.
    String reason = e.getMessage();
});
```
 
Within this group, `UploadRejectEvent` is the odd one out. It fires before any bytes move, when a file fails a client-side check like `setMaxFileSize` or `setMaxFiles`. `UploadErrorEvent`, by contrast, fires after the transfer started and something went wrong on the way to the server.
 
### Whole batch {#whole-batch}
 
These fire on the batch rather than on any one file. Use them for aggregate UI like an overall progress bar or a "done" message that summarizes the whole pick.
 
| Event | Fires |
| --- | --- |
| `UploadListProgressEvent` | Alongside `UploadProgressEvent`, with the whole list state |
| `UploadCompleteEvent` | Once per batch, when every file has finished transferring |
 
```java
upload.onComplete(e -> {
    // Fires once when the whole batch is done.
    List<UploadedFile> succeeded = e.getUploadedFiles();
    List<UploadedFile> failed = e.getFailedFiles();
});
```
 
`onProgress` and `onListProgress` cover the same transfer from two angles. `onProgress` is per-file, and is the right hook when each file has its own progress UI. `onListProgress` fires alongside it with aggregate counters (`getListTotal`, `getListRemaining`, `getListProgress`) for a single batch-wide indicator.
 
In the example below, `onChange`, `onListProgress`, and `onComplete` drive a progress bar and status line that update as the file list changes and as files transfer.
 
<ComponentDemo
path='/webforj/uploadevents'
files={['src/main/java/com/webforj/samples/views/upload/UploadEventsView.java']}
height='450px'
/>

## Internationalization (i18n) {#internationalization-i18n}

The labels and messages inside the component are customizable through the `FileUploadI18n` bundle. The bundle type keeps the `FileUploadI18n` name because it's shared with the modal [`FileUploadDialog`](./option-dialogs/file-upload).

```java
FileUploadI18n bundle = new FileUploadI18n();
bundle.setUpload("Send");
bundle.setCancel("Discard");
bundle.setDropFile("Drop the file here");
upload.setI18n(bundle);
```

## Themes {#themes}

`UploadTheme` mirrors the standard DWC theme palette and includes outlined variants for a lighter visual weight. Themes apply to the picker, upload, and cancel buttons. The list and drop area keep neutral styling regardless of the theme.

```java
upload.setTheme(UploadTheme.PRIMARY);
upload.setTheme(UploadTheme.SUCCESS);
upload.setTheme(UploadTheme.OUTLINED_GRAY);
```

The demo below shows the `PRIMARY` theme combined with the `INLINE` preset.

<ComponentDemo
path='/webforj/uploadthemes'
files={['src/main/java/com/webforj/samples/views/upload/UploadThemesView.java']}
height='200px'
/>

## Styling {#styling}

<TableBuilder name="Upload" />