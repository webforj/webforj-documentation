---
title: "Java file upload: build a web UI with the webforJ Upload"
description: "Build a Java file upload web UI with the webforJ Upload component. Set filters and size limits, track progress, save files with a Spring service and list them."
slug: java-file-upload
date: 2026-09-18
authors: Eric Handtke
tags: [components, spring, back end]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-09-18-java-file-upload/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-09-18-java-file-upload/cover.jpg)

For a Java file upload web UI, use the webforJ `Upload` component. Create an `Upload`, add filters and limits, listen for `onUpload`, and move each `UploadedFile` out of the temporary folder into your own storage. The component gives you the picker, the drop zone, the file list and progress reporting, and all of your handling code stays in Java.

The `Upload` component is available since webforJ 26.01. This post builds a complete upload page: one or many files, accepted types and size limits, drag and drop, progress, a Spring service that saves the files, a `Table` that lists them, and file download.

<!-- truncate -->

## How do you add an upload field? {#add-upload}

Create the component, add it to a layout, and give it at least one filter. A filter is a description plus glob patterns separated by semicolons:

```java
Upload upload = new Upload();
upload.addFilter("Documents", "*.pdf;*.docx");
upload.addFilter("Images", "*.png;*.jpg");
layout.add(upload);
```

By default the component shows a select button, a drop area, the list of picked files, and an upload button. The user picks files, clicks **Upload**, and the files travel to the server. You don't write markup, a servlet, or a multipart parser.

`Upload` renders inline in the page. If you need a modal that blocks the app until the user finishes, the [`FileUploadDialog`](/docs/components/option-dialogs/file-upload) returns the same `UploadedFile` type from a single call, `OptionDialog.showFileUploadDialog("Upload a file")`. Use the inline component when picking files is part of a larger form, which covers most cases.

## How do you upload multiple files? {#multiple-files}

Selection mode decides how many files the user can pick. `MULTIPLE` is the default, and `SINGLE` suits a field that holds one value, such as a profile photo:

```java
upload.setSelectionMode(Upload.SelectionMode.SINGLE);
```

Folders work too. `setPicker(Upload.Picker.DIRECTORY)` uploads the top-level files of a folder, and `DIRECTORY_RECURSIVE` walks the whole tree.

## How do you limit file types and size? {#limits}

Filters narrow what the picker offers. `setMaxFileSize` caps the bytes per file and `setMaxFiles` caps the batch. Both run in the browser before any bytes move, so a file that's too large never consumes bandwidth. When a picked or dropped file breaks a limit, the component fires `UploadRejectEvent`:

```java
upload.setMaxFiles(5d);
upload.setMaxFileSize(10d * 1024 * 1024); // 10 MB
upload.onReject(e -> Toast.show(e.getMessage()));
```

Treat these limits as guidance for the user. A browser can be bypassed, so [the server has to enforce its own](/blog/java-web-app-security-best-practices). Set `webforj.fileUpload.maxSize` in your [configuration](/docs/configuration/properties) as a hard ceiling in bytes, and `webforj.fileUpload.accept` to restrict allowed types. With Spring, both go in `application.properties`. Then re-check the name or type of every file in your own code before you store it, as the service below does.

## How does drag and drop work? {#drag-and-drop}

Drag and drop is on by default. Files dropped on the component are handled like picked files, and the drop label changes while a file hovers over it. Turn it off with `setDrop(false)` when files should only come from the picker.

Auto upload removes the need to click **Upload**. `ON_SELECT` starts the transfer when files are picked, `ON_DROP` when they're dropped, and `ALWAYS` covers both:

```java
upload.setAutoUpload(Upload.AutoUpload.ON_SELECT);
upload.setVisible(false, Upload.Part.UPLOAD_BUTTON);
```

Presets reshape the component for common layouts. `INLINE` fits a form field, `BUTTON_ONLY` shows just the picker button, and `DROPZONE` shows only the drop label and the list. With `HEADLESS`, every built-in part is hidden and the children you `add()` become the visible surface of the drop area. Call `setPreset()` before `setVisible()` when you use both. On phones, `setCapture(Upload.Capture.ENVIRONMENT)` opens the rear camera, and a matching image filter makes it open in still mode.

## How do you show upload progress? {#progress}

The component reports progress per file and for the whole batch. `onProgress` fires repeatedly during one file's transfer. `onListProgress` fires alongside it with batch totals, which is what a single progress bar needs:

```java
ProgressBar progress = new ProgressBar();
upload.onListProgress(e -> progress.setValue((int) e.getListProgress()));
```

The other events cover the rest of the life cycle:

| Event | Fires |
| --- | --- |
| `UploadChangeEvent` | The list of picked files changes |
| `UploadEvent` | The files have reached the server |
| `UploadRejectEvent` | A file breaks a client-side limit, before any bytes move |
| `UploadErrorEvent` | A transfer fails after it started |
| `UploadCompleteEvent` | Every queued file finished, with `getUploadedFiles()` and `getFailedFiles()` |
| `UploadCancelEvent` | The user clicks **Cancel** |

`UploadEvent` is the place to save files. `UploadCompleteEvent` is the place to update the screen when the batch is done. All listeners run on the UI thread, so they can update components directly.

## What happens on the server? {#server-side}

`UploadEvent` gives you `getFiles()`, a list of `UploadedFile` objects. Each one exposes `getSanitizedClientName()`, `size()`, `exists()`, `move(String path)` and `delete()`.

webforJ first stores the file in a temporary folder that gets cleaned regularly, so a file you don't move is eventually lost. `move()` moves it to the path you give and returns a `java.io.File`. Use `getSanitizedClientName()` for the filename, never the raw client name, because the sanitized version guards against directory traversal and invalid characters. If you reject a file, call `delete()` on it. From there you can also hand an uploaded image to an AI model, as [extracting data from an image in Java](/blog/ai-image-data-extraction-java) shows.

## How do you save files with Spring? {#spring-service}

Keep storage in a Spring service and keep the view thin. This service checks the name against an allow list, moves the file into a configured directory under a unique id, and remembers what it stored:

```java
@Service
public class FileStorageService {
  private static final Pattern ALLOWED =
      Pattern.compile(".+\\.(pdf|docx|png|jpg)", Pattern.CASE_INSENSITIVE);
  private final Path root;
  private final List<StoredFile> files = new ArrayList<>();

  public FileStorageService(@Value("${app.upload-dir:uploads}") String dir) throws IOException {
    root = Files.createDirectories(Path.of(dir));
  }

  public StoredFile store(UploadedFile upload) throws IOException {
    String name = upload.getSanitizedClientName();
    if (!ALLOWED.matcher(name).matches()) {
      upload.delete();
      throw new IllegalArgumentException("File type not allowed: " + name);
    }
    String id = UUID.randomUUID().toString();
    File saved = upload.move(root.resolve(id + "-" + name).toString());
    StoredFile stored = new StoredFile(id, name, saved.length(), saved.toPath());
    files.add(stored);
    return stored;
  }

  public List<StoredFile> findAll() {
    return List.copyOf(files);
  }
}

public record StoredFile(String id, String name, long size, Path path) {}
```

The unique prefix keeps two users who upload `report.pdf` from overwriting each other. In a real app you would replace the in-memory list with a Spring Data repository and keep the metadata in your database. The route that uses the service receives it through constructor injection, as the [Spring routing guide](/docs/integrations/spring/routing) describes.

## How do you list uploaded files? {#table}

The view wires the pieces together. The upload saves each file through the service, and a `Table` shows what's stored. Clicking a row downloads the file:

```java
@Route("files")
public class FilesView extends Composite<FlexLayout> {
  private final FileStorageService storage;
  private final Table<StoredFile> table = new Table<>();

  public FilesView(FileStorageService storage) {
    this.storage = storage;
    getBoundComponent().setDirection(FlexDirection.COLUMN).setSpacing("var(--dwc-space-m)");

    Upload upload = new Upload();
    upload.addFilter("Documents", "*.pdf;*.docx");
    upload.setMaxFileSize(10d * 1024 * 1024);
    upload.setAutoUpload(Upload.AutoUpload.ON_SELECT);
    upload.onReject(e -> Toast.show(e.getMessage()));
    upload.onUpload(e -> {
      for (UploadedFile file : e.getFiles()) {
        try {
          storage.store(file);
        } catch (IOException | IllegalArgumentException ex) {
          Toast.show(ex.getMessage());
        }
      }
      refresh();
    });

    table.addColumn("name", StoredFile::name).setLabel("Name");
    table.addColumn("size", StoredFile::size).setLabel("Bytes");
    table.onItemClick(e -> Page.getCurrent().download(e.getItem().path().toFile(), e.getItem().name()));

    getBoundComponent().add(upload, table);
    refresh();
  }

  private void refresh() {
    table.setRepository(new CollectionRepository<>(storage.findAll()));
  }
}
```

If a list suits the screen better than a grid, the same `StoredFile` items fit a list component too. The [Table overview](/docs/components/table/overview) covers columns and repositories, and [table renderers](/blog/table-renderers-custom-cells) show how to turn a column into a download button.

## How do you download a file? {#download}

`Page.getCurrent().download(...)` sends a file to the browser. It accepts an `InputStream`, a `byte[]`, a `File`, or a resource path such as a `context://` URL, each with the filename the user should see. The row click in the view uses the `File` overload. The [downloading files](/docs/managing-resources/downloading-files) page lists them all.

## Next step {#next-step}

Add an `Upload` to one of your forms and wire `onUpload` to a service that calls `move()`. To start a new Spring Boot project with the pieces in place, use [startforJ](https://docs.webforj.com/startforj). The full list of settings, parts and events is on the [Upload component page](/docs/components/upload).

## Frequently asked questions {#faq}

### Can I upload files without JavaScript? {#faq-no-js}
Use the webforJ `Upload` component. It renders the picker, drop zone and file list, and your Java code receives an `UploadedFile` in the `onUpload` listener.

### How do I limit upload size? {#faq-size}
Call `setMaxFileSize` on the component to reject large files in the browser, and `setMaxFiles` to cap the batch. Also set `webforj.fileUpload.maxSize` on the server, because browser limits only guide the user.

### Where are uploaded files stored? {#faq-storage}
In a temporary folder that webforJ cleans regularly. Call `move()` on the `UploadedFile` to copy it to your own directory, and use `getSanitizedClientName()` for the filename.

### Can the user drag and drop files? {#faq-drop}
Yes. Drag and drop is on by default, and `setDrop(false)` turns it off. With the `DROPZONE` preset, the drop area becomes the main way to add files.
