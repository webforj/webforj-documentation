---
title: "Spring AI RAG web UI: chat with your documents in Java"
description: "Build a Spring AI RAG web UI in pure Java with webforJ: upload documents, index them in a vector store, and stream answers with their sources into the page."
slug: spring-ai-rag
date: 2026-11-04T08:00:00Z
authors: Hyyan Abo Fakher
tags: [ai, spring, chat]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-10-26-spring-ai-rag/cover-v2.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-10-26-spring-ai-rag/cover-v2.jpg)

The fastest way to build a Spring AI RAG web UI is to write the whole thing in Java with webforJ. The `Upload` component takes the files, Spring AI reads, splits and embeds them into a `VectorStore`, a `RetrievalAugmentationAdvisor` on a `ChatClient` grounds every answer in the retrieved chunks, and a `MarkdownViewer` renders the streamed answer. There is no REST layer, no JavaScript and no separate frontend project.

This guide builds a chat-with-your-documents app that way. It is two classes: a Spring service that owns the knowledge base and a webforJ view that talks to it. The complete app, [AskDocs](https://github.com/webforj/built-with-webforj/tree/main/webforj-askdocs), is on GitHub and runs with your own Gemini API key.

<!-- truncate -->

![AskDocs answering a question from an uploaded travel handbook, with the source file listed under the answer](https://cdn.webforj.com/webforj-documentation/blogs/2026-10-26-spring-ai-rag/askdocs.png)

## Spring AI RAG web UI architecture {#architecture}

A document Q&A app has two flows. Ingestion turns files into searchable chunks. Querying finds the chunks closest to a question and hands them to the model as context.

| Step | Where it happens | Class |
| --- | --- | --- |
| Pick and transfer files | Browser to server | webforJ `Upload` |
| Read text out of PDF, DOCX or TXT | Server | Spring AI `TikaDocumentReader` |
| Split into chunks | Server | Spring AI `TokenTextSplitter` |
| Embed and store | Server | Spring AI `VectorStore` |
| Retrieve and augment the prompt | Server | `RetrievalAugmentationAdvisor` |
| Stream and render the answer | Server to browser | `MarkdownViewer` |

Because webforJ runs your UI code on the server, the view can call a Spring bean directly. The uploaded file lands on the server, the service reads it, and the answer streams back into a component. Nothing in that path needs an HTTP endpoint you write yourself.

## Dependencies {#dependencies}

Start from a webforJ Spring Boot project, then add the Spring AI BOM, a model provider, and the three RAG artifacts. This example uses Gemini for both the answers and the embeddings:

```xml
<dependency>
  <groupId>org.springframework.ai</groupId>
  <artifactId>spring-ai-starter-model-google-genai</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.ai</groupId>
  <artifactId>spring-ai-google-genai-embedding</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.ai</groupId>
  <artifactId>spring-ai-rag</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.ai</groupId>
  <artifactId>spring-ai-vector-store</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.ai</groupId>
  <artifactId>spring-ai-tika-document-reader</artifactId>
</dependency>
```

Choose the models in `application.properties`, and pass the API key through the environment variables `SPRING_AI_GOOGLE_GENAI_API_KEY` and `SPRING_AI_GOOGLE_GENAI_EMBEDDING_API_KEY` so it never lands in a file:

```properties
spring.ai.google.genai.chat.options.model=gemini-3.5-flash-lite
spring.ai.google.genai.embedding.text.options.model=gemini-embedding-001
```

The [Spring AI reference](https://docs.spring.io/spring-ai/reference/api/retrieval-augmented-generation.html) covers the RAG modules in detail. On the webforJ side the app uses the [Spring integration](/docs/integrations/spring/overview), so views receive Spring beans through their constructors.

## Index documents in a vector store {#index-documents}

The whole RAG pipeline is one Spring service. It builds a `SimpleVectorStore` from the `EmbeddingModel` that the starter configures, and a `ChatClient` that has the RAG advisor attached.

```java
@Service
public class KnowledgeBase {
  private final VectorStore store;
  private final ChatClient chat;

  public KnowledgeBase(EmbeddingModel embeddings, ChatClient.Builder builder) {
    store = SimpleVectorStore.builder(embeddings).build();

    chat = builder
        .defaultSystem("Answer in short Markdown. Use only the context you are given.")
        .defaultAdvisors(RetrievalAugmentationAdvisor.builder()
            .documentRetriever(VectorStoreDocumentRetriever.builder()
                .vectorStore(store)
                .similarityThreshold(0.5)
                .topK(4)
                .build())
            .build())
        .build();
  }

  public int add(Resource document, String name) {
    List<Document> pages = new TikaDocumentReader(document).get();
    pages.forEach(page -> page.getMetadata().put("source", name));
    List<Document> chunks = TokenTextSplitter.builder().build().apply(pages);
    store.add(chunks);
    return chunks.size();
  }

  public Flux<ChatClientResponse> ask(String question) {
    return chat.prompt().user(question).stream().chatClientResponse();
  }
}
```

`add` is the whole indexing pipeline. `TikaDocumentReader` extracts text from a PDF, Word, Markdown, or text file. The `source` metadata records the name to show next to an answer. `TokenTextSplitter` cuts the text into chunks that fit an embedding request, and `store.add` embeds each chunk and keeps the vector next to its text and metadata.

`add` takes a Spring `Resource`, so an uploaded file (`FileSystemResource`) and a document bundled with the app (`ClassPathResource`) go through the same code.

`ask` asks for `chatClientResponse()` instead of plain `content()`. That keeps the advisor context attached to every streamed item, which is how the view finds out which documents were retrieved.

`SimpleVectorStore` keeps vectors in memory. It suits a demo or a small internal tool. Swapping it for a database-backed store changes one line in the constructor, because the rest of the code only sees the `VectorStore` interface.

## Add documents with the webforJ Upload component {#upload}

The [`Upload`](/docs/components/upload) component (since 26.01) handles the picker, the drop zone, the file list, and the progress display. Configure it once and listen for one event:

```java
upload.setSelectionMode(Upload.SelectionMode.MULTIPLE)
    .setAutoUpload(Upload.AutoUpload.ALWAYS)
    .addFilter("Documents", "*.pdf;*.docx;*.txt;*.md");
upload.setVisible(false, Upload.Part.UPLOAD_BUTTON);
upload.onUpload(e -> e.getFiles().forEach(this::index));
```

`AutoUpload.ALWAYS` starts the transfer as soon as files are picked or dropped, so the Upload button can be hidden. `onUpload` fires when the files have reached the server. Each `UploadedFile` is then moved out of the upload cache into a folder the view owns:

```java
private void index(UploadedFile uploaded) {
  try {
    String target = uploads.resolve(UUID.randomUUID() + "-" + uploaded.getSanitizedClientName())
        .toString();
    File saved = uploaded.move(target);
    add(new FileSystemResource(saved), uploaded.getClientName());
  } catch (Exception ex) {
    Toast.show("Could not read " + uploaded.getClientName(), Theme.DANGER);
  }
}
```

`uploads` is a temporary folder the view creates with `Files.createTempDirectory`. Give every file a unique target. Calling `move()` without an argument places the file in the app's working directory under its client name, so adding the same file twice fails. Build paths from `getSanitizedClientName()`, never from the raw client name.

## Stream the answer into a MarkdownViewer {#stream-answer}

Models answer in Markdown, so the [`MarkdownViewer`](/docs/components/markdownviewer) is the natural target. It renders appended text incrementally, and `setAutoScroll(true)` keeps the newest text in view while the answer grows.

The stream arrives on a Reactor thread, not on the webforJ UI thread, so every UI change goes back to the UI thread with `Environment.runLater`, as described in [Asynchronous Updates](/docs/advanced/asynchronous-updates). Capture the `Environment` while you are still on the UI thread and pass it in:

```java
private void ask() {
  String text = question.getValue();
  if (text == null || text.isBlank() || !ask.isEnabled()) {
    return;
  }

  Environment env = Environment.getCurrent();
  Set<String> used = new LinkedHashSet<>();
  startAnswer();

  kb.ask(text).subscribe(
      response -> {
        used.addAll(KnowledgeBase.sourcesOf(response));
        String chunk = response.chatResponse().getResult().getOutput().getText();
        Environment.runLater(env, () -> showChunk(chunk));
      },
      error -> Environment.runLater(env, () -> {
        showChunk("Sorry, the answer failed: " + error.getMessage());
        finishAnswer(Set.of());
      }),
      () -> Environment.runLater(env, () -> finishAnswer(used)));
}
```

Tasks queued with `runLater` run in the order they were submitted, so chunks appear in the right sequence.

## Show a spinner while searching {#spinner}

Retrieval and the first tokens take a few seconds, so the user needs to see that something is happening. A `Spinner` with a short label shows in place of the answer until the first chunk arrives:

```java
thinking.add(new Spinner(SpinnerExpanse.SMALL), new Span("Searching your documents..."));
```

The three state methods switch between waiting, streaming and done:

```java
private void startAnswer() {
  ask.setEnabled(false);
  question.setEnabled(false);
  answer.clear();
  answer.setVisible(false);
  sources.removeAll();
  sources.setVisible(false);
  thinking.setVisible(true);
}

private void showChunk(String chunk) {
  if (chunk == null || chunk.isEmpty()) {
    return;
  }
  thinking.setVisible(false);
  answer.setVisible(true);
  answer.append(chunk);
}

private void finishAnswer(Set<String> used) {
  thinking.setVisible(false);
  answer.setVisible(true);
  if (!used.isEmpty()) {
    sources.add(new Span("Sources"));
    used.forEach(name -> sources.add(new Span(name)));
    sources.setVisible(true);
  }
  ask.setEnabled(true);
  question.setEnabled(true);
  question.focus();
}
```

The question field and the Ask button stay disabled until the stream ends or fails, which stops a second question from interleaving with the first answer.

## Show which documents the answer used {#show-sources}

The RAG advisor puts the retrieved chunks into the response context under `RetrievalAugmentationAdvisor.DOCUMENT_CONTEXT`. Each chunk is a Spring AI `Document` that carries the `source` name set in `add`, so a short helper on the service turns the context into a list of names:

```java
@SuppressWarnings("unchecked")
public static List<String> sourcesOf(ChatClientResponse response) {
  List<Document> docs = (List<Document>) response.context()
      .getOrDefault(RetrievalAugmentationAdvisor.DOCUMENT_CONTEXT, List.of());
  return docs.stream().map(doc -> String.valueOf(doc.getMetadata().get("source"))).toList();
}
```

The `Set` in `ask()` removes duplicates, since several chunks often come from one file. These are the documents the retriever handed to the model, and they let a reader open the original file to check the answer.

## Tune what counts as relevant {#empty-results}

`similarityThreshold(0.5)` drops chunks that are not close enough to the question, and `topK(4)` caps how many reach the model. Tune both against your own documents. A higher threshold gives fewer but more relevant chunks, and the system prompt keeps the model from answering beyond the context it is given.

## Production concerns {#production}

A few things deserve attention before this leaves a prototype:

- **Tenancy.** The vector store in this service is shared by every user of the app. Tag chunks with an owner in their metadata and pass a filter expression per request with `.advisors(a -> a.param(VectorStoreDocumentRetriever.FILTER_EXPRESSION, "owner == 'alice'"))`.
- **Upload limits.** Set `webforj.fileUpload.maxSize` and `webforj.fileUpload.accept` in `webforj.conf` to enforce limits on the server, and `setMaxFileSize` and `setMaxFiles` on the component to give the user instant feedback. See [configuration properties](/docs/configuration/properties).
- **Long indexing.** Embedding a large PDF takes a while. Run `add` from an `@Async` method and report progress with `Environment.runLater`, as shown in [background jobs](/docs/integrations/spring/background-jobs).
- **Persistence.** Replace `SimpleVectorStore` with a database-backed store when documents must survive a restart.

## Why webforJ fits AI apps {#why-webforj}

A RAG app is a server-side workflow with a UI on top. Files arrive at the server, embeddings and model calls happen on the server, and secrets such as API keys stay there. webforJ keeps the UI on the server too, so the view calls `KnowledgeBase` as a plain Java method and receives results as Java objects. The streaming handoff is one call to `Environment.runLater`, and the components you need for this app (`Upload`, `MarkdownViewer`, `TextField`, `Button`) are part of the framework. The result is a document Q&A app that one Java developer can read top to bottom.

If you want the broader pattern around Spring AI and a chat surface, the earlier post [Adding an AI chat feature to a Java app](/blog/add-ai-to-java-app) walks through a streaming chat with tools.

## Next step {#next-step}

Clone [AskDocs](https://github.com/webforj/built-with-webforj/tree/main/webforj-askdocs) to run the finished app, or generate a project with [startforJ](https://docs.webforj.com/startforj), add the Spring AI starter for your model provider, and add the service and the view from this guide. Then read the [`Upload`](/docs/components/upload) docs to shape the picker for your users.

## Frequently asked questions {#faq}

### What do I need for a Spring AI RAG web UI in Java? {#faq-requirements}

You need a Spring Boot app with the webforJ Spring integration, a Spring AI model starter that provides chat and embedding models, and the `spring-ai-rag`, `spring-ai-vector-store` and `spring-ai-tika-document-reader` artifacts. The webforJ `Upload` and `MarkdownViewer` components cover the UI, so no frontend code is required.

### Which Spring AI class adds retrieval to a ChatClient? {#faq-advisor}

`RetrievalAugmentationAdvisor` from the `spring-ai-rag` module. You pass it a `VectorStoreDocumentRetriever` and register it with `defaultAdvisors` on the `ChatClient.Builder`. Every prompt then runs a similarity search first and sends the matching chunks to the model as context.

### How do I show which documents an answer came from? {#faq-sources}

Request `chatClientResponse()` from the stream and read `RetrievalAugmentationAdvisor.DOCUMENT_CONTEXT` from the response context. It holds the retrieved `Document` objects, and their metadata carries the source name you set while indexing. Show the distinct names under the answer when the stream completes.

### Why do I need Environment.runLater when streaming? {#faq-runlater}

The reactive stream delivers chunks on a thread that is not the webforJ UI thread, and components may only change on the UI thread. `Environment.runLater(env, task)` queues the update for the UI thread and keeps submission order, so the answer text appears in sequence.
