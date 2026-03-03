---
title: InfiniteScroll
sidebar_position: 60
_i18n_hash: b6795a86cf03a60d9ef9e7d89749c9ab
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-infinite-scroll" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="infinite-scroll" location="com/webforj/component/infinitescroll/InfiniteScroll" top='true'/>

Die `InfiniteScroll`-Komponente in webforJ lädt automatisch mehr Inhalte, während die Benutzer nach unten scrollen, wodurch die Notwendigkeit für Seitenpagination entfällt. Dies schafft ein reibungsloses Erlebnis für Listen, Feeds und datenintensive Ansichten, indem Inhalte nur bei Bedarf geladen werden.

Wenn Benutzer den unteren Rand des scrollbaren Inhalts erreichen, löst `InfiniteScroll` ein Ereignis zum Laden weiterer Daten aus. Während neue Inhalte geladen werden, wird ein [`Spinner`](../components/spinner) mit anpassbarem Text angezeigt, um anzuzeigen, dass weitere Elemente auf dem Weg sind.

<!-- INTRO_END -->

## Zustandsverwaltung {#state-management}

Die `InfiniteScroll`-Komponente gibt Ereignisse aus und verwaltet einen internen Zustand, um zu helfen, wie und wann Inhalte geladen werden.

<AppLayoutViewer
path='/webforj/infinitescroll?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollView.java'
cssURL='/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

Um mehr Daten zu laden, wenn der Benutzer scrollt, verwenden Sie die Methode `onScroll()` oder `addScrollListener()`, um einen Listener zu registrieren. Innerhalb des Listeners laden Sie typischerweise zusätzliche Inhalte und rufen `update()` auf, um den Zustand von `InfiniteScroll` zu aktualisieren.

```java
infiniteScroll.onScroll(event -> {
    infiniteScroll.add(new Paragraph("Element geladen"));
    infiniteScroll.update();
});
```

Sobald alle Inhalte geladen sind, markieren Sie das Scrollen als abgeschlossen, um weitere Auslösungen zu verhindern. Nach dem Setzen von abgeschlossen, denken Sie daran, `update()` aufzurufen, um den neuen Zustand anzuwenden:

```java
infiniteScroll.setCompleted(true);
infiniteScroll.update();
```
Dies deaktiviert das weitere Verhalten des unendlichen Scrollens.

:::tip Setzen Sie das Ladeflag zurück
Sie können dieses Flag mit `setCompleted(false)` zurücksetzen, wenn Sie dem Benutzer später erlauben, mehr Inhalte zu laden (z.B. nach einer Aktualisierung).
:::


## Anpassung des Ladeindikators {#loading-indicator-customization}

Standardmäßig zeigt `InfiniteScroll` einen integrierten Ladeindikator - einen kleinen animierten [`Spinner`](../components/spinner) zusammen mit einem „Daten laden“-Text. Sie können den angezeigten Text ändern, indem Sie eine benutzerdefinierte Nachricht an den `InfiniteScroll`-Konstruktor übergeben oder `setText()` verwenden.

```java
InfiniteScroll infiniteScroll = new InfiniteScroll("Weitere Datensätze abrufen...");
infiniteScroll.setText("Weitere Elemente laden...");
```

Ebenso können Sie das während des Ladens angezeigte [`Icon`](../components/icon) mit `setIcon()` anpassen.

<AppLayoutViewer
path='/webforj/infinitescrollloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollLoadingView.java'
cssURL='/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

### Vollständige Anpassung {#full-customization}

Wenn Sie sowohl den [`Spinner`](../components/spinner) als auch den Text vollständig durch eigenes Markup ersetzen möchten, können Sie direkt in den speziellen Inhaltsbereich mit `addToContent()` Inhalte hinzufügen.

Wenn Sie den Inhaltsbereich bevölkern, ersetzt dies vollständig das Standard-Lade-Layout.

<AppLayoutViewer
path='/webforj/infinitescrollcustomloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollCustomLoadingView.java'
cssURL='/css/infinitescroll/infinitescrollcustom.css'
height = '400px'
mobile='true'
/>

## Styling {#styling}

<TableBuilder name="InfiniteScroll" />
