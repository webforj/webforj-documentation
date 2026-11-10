---
title: "Extract data from an image in Java with AI and webforJ"
description: "Extract data from an image in Java with a vision-capable AI model. Upload an invoice with webforJ, send it to Spring AI, and get typed fields back."
slug: ai-image-data-extraction-java
date: 2026-07-29
authors: Eric Handtke
tags: [ai, spring-ai, spring]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-07-29-ai-image-data-extraction-java/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-07-29-ai-image-data-extraction-java/cover.jpg)

To extract data from an image in Java, upload the file with the webforJ `Upload` component, send the bytes to a vision-capable model through Spring AI, and ask for a typed result. Spring AI maps the answer to a Java record, so your view gets the vendor, invoice number, date, and total as fields. You do not need a separate OCR library or a text parsing step.

<!-- truncate -->

:::tip Run the finished app
The complete project, [Invoice Reader](https://github.com/webforj/built-with-webforj/tree/main/webforj-invoicereader), is published on GitHub, so you can view the code and run it with your own Gemini API key. The project is kept up to date with the latest webforJ release, so its framework version can be newer than the one this post was written for.
:::

<div style={{textAlign: 'center'}}>

![Invoice Reader showing the vendor, invoice number, date and total read from an uploaded invoice](https://cdn.webforj.com/webforj-documentation/blogs/2026-07-29-ai-image-data-extraction-java/invoicereader.png)

</div>

## Upload images in a Java web app {#upload-images-in-a-java-web-app}

Start with the file picker. The `Upload` component is an inline file picker that ships with webforJ 26.01. It handles picking, dropping, and sending the file to the server. You do not need a modal dialog for this.

1. Limit the picker to image types with `addFilter`.
2. Allow one file with `SelectionMode.SINGLE`.
3. Cap the file size with `setMaxFileSize`.
4. Start the transfer when the file is picked with `AutoUpload.ALWAYS`, then hide the Upload button.

```java
private final Upload upload = new Upload();

public InvoiceUploadView(ChatClient chat, TaskExecutor executor) {
  this.chat = chat;
  this.executor = executor;

  upload.setSelectionMode(Upload.SelectionMode.SINGLE);
  upload.addFilter("Invoice images", "*.png;*.jpg;*.jpeg");
  upload.setMaxFileSize(10d * 1024d * 1024d);
  upload.setAutoUpload(Upload.AutoUpload.ALWAYS);
  upload.setVisible(false, Upload.Part.UPLOAD_BUTTON);
  upload.onUpload(e -> extract(e.getFile()));

  self.add(upload, status, result);
}
```

`onUpload` fires once the file has reached the server. `e.getFile()` returns that file, because this picker holds one file at a time.

The size cap runs in the browser. A file over the limit fires `UploadRejectEvent` before any bytes move, so the model never sees it. Filters only shape what the picker offers. Check the extension on the server too, with `getClientExtension()`, before you send anything to a model. The `extract` method in the next section maps `png` to the PNG type and falls back to JPEG for everything else, so a file with another extension still reaches the model. Reject it there if your app should accept only these two types. The filename comes from the client, so read it through `getSanitizedClientName()` whenever it becomes part of a path or a message.

## Send the image to the model {#send-the-image-to-the-model}

Spring AI's `ChatClient` accepts media inside a user message. You pass the MIME type and a resource for the image. You then call `entity` with a class, and the client asks the model for output that matches that class and converts the answer. Spring AI auto-configures a `ChatClient.Builder`, so declare a `ChatClient` bean that returns `builder.build()`, and Spring injects it into the view.

The uploaded file sits in webforJ's upload cache. Call `move()` to take it out of the cache before you use it. `move()` needs the session's environment, so call it in the upload listener, on the UI thread. The model call takes seconds, so hand it to Spring's shared `TaskExecutor`, which the view receives through its constructor like the `ChatClient`. A pool thread serves every user of the app, so the listener reads the session's `Environment` with `Environment.getCurrent()` and passes it to `Environment.runLater` when the answer arrives.

```java
private void extract(UploadedFile uploaded) {
  status.setText("Reading " + uploaded.getSanitizedClientName() + "...");

  File file;
  try {
    file = uploaded.move();
  } catch (IOException ex) {
    status.setText("Could not read " + uploaded.getSanitizedClientName());
    return;
  }
  MimeType type = uploaded.getClientExtension().equalsIgnoreCase("png")
      ? MimeTypeUtils.IMAGE_PNG
      : MimeTypeUtils.IMAGE_JPEG;

  Environment env = Environment.getCurrent();
  executor.execute(() -> {
    try {
      InvoiceData data = chat.prompt()
          .user(u -> u.text(PROMPT).media(type, new FileSystemResource(file)))
          .call()
          .entity(InvoiceData.class);
      if (data == null) {
        throw new IllegalStateException("the model returned no fields");
      }

      Environment.runLater(env, () -> show(data));
    } catch (Exception ex) {
      Environment.runLater(env, () -> {
        status.setText("Extraction failed: " + ex.getMessage());
      });
    } finally {
      file.delete();
    }
  });
}
```

The `status` label changes and the file leaves the cache before the task starts, so the user sees feedback at once. `move()` without an argument places the file in the app's working directory, so the `finally` block deletes it once the model has read it. `entity` can return `null`, for example when the model sends back no content, so the method turns that case into a failure. The catch block reports a failure through the same `runLater` path, which keeps every component update on the UI thread.

The prompt is the contract with the model. It names the fields you want and says what to do with values the model cannot read. In this sketch it is:

```java
private static final String PROMPT =
    "Extract the vendor, invoice number, invoice date, currency, and total from this invoice. "
        + "Use null for any field you cannot read.";
```

Keep the prompt short and literal. Each field name should match a record component, so the answer maps without guesswork.

## Map the answer to a record {#map-the-answer-to-a-record}

Define the shape you want as a record. Each component becomes one field the model fills in. Use `BigDecimal` for the total, so the amount is never stored as a binary floating point value.

```java
record InvoiceData(
    String vendor,
    String invoiceNumber,
    String invoiceDate,
    String currency,
    BigDecimal total) {}

private void show(InvoiceData data) {
  status.setText("Extracted invoice " + data.invoiceNumber());
  result.setText(data.vendor() + ", " + data.invoiceDate() + ", "
      + data.total() + " " + data.currency());
}
```

Treat every field as optional. The prompt asks for `null` when a value is unreadable, so any component of the record can be `null`. This sketch prints those values as the text `null`. In your UI, show an empty field with a short warning. Dates arrive as text here, so parse them with `LocalDate` only after you check that they match the format you expect.

## Keep the UI thread free {#keep-the-ui-thread-free}

A model call takes far longer than a button click. If you make the call inside the `onUpload` listener, the session waits until the answer returns. The task executor in `extract` avoids that wait. The executor is one pool for the whole app, so a pool thread cannot know which session it works for. That is why `extract` reads `Environment.getCurrent()` on the UI thread and passes it to every `Environment.runLater` call, and the page of the user who uploaded is the one that updates.

The code in the earlier sections attaches no chat memory. Each call sends one image and one prompt, so no conversation builds up between uploads.

## Checklist {#checklist}

- [ ] Filter the picker to image types.
- [ ] Cap the file size with `setMaxFileSize`.
- [ ] Check the extension on the server with `getClientExtension()`.
- [ ] Move the upload out of the cache in the upload listener, before the task starts.
- [ ] Delete the file once the model has read it.
- [ ] Run the model call on Spring's shared `TaskExecutor`.
- [ ] Update components only inside `Environment.runLater`, with the `Environment` read on the UI thread.
- [ ] Treat every extracted field as optional.
- [ ] Compare the extracted total with the invoice before you save it.

## Next step {#next-step}

Create a project with [startforJ](https://docs.webforj.com/startforj) or follow the [getting started guide](/docs/introduction/getting-started). Add the [Upload component](/docs/components/upload) to a view, then add the Spring AI starter for the model you use. Run the view with an invoice image before you change the prompt. If the upload should open on demand, use the [file upload dialog](/docs/components/option-dialogs/file-upload) instead.

## Frequently asked questions {#faq}

### Can an LLM read invoices in Java? {#can-an-llm-read-invoices-in-java}

Yes, when the model accepts image input. The code in the previous sections sends the invoice as media in the user message, and the answer comes back as a Java record. Name the fields you need in the prompt, so the model returns the same shape every time.

### Do I need OCR software first? {#do-i-need-ocr-software-first}

Not with this approach. The model reads the image directly, so there is no separate OCR step to set up. Still compare the extracted totals with the invoice before you save anything. The Spring AI [reference documentation](https://docs.spring.io/spring-ai/reference/) covers the chat client and model setup.
