---
title: InfiniteScroll
sidebar_position: 60
_i18n_hash: b41c992436f501c03ae93b1dfc2c254b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-infinite-scroll" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="infinite-scroll" location="com/webforj/component/infinitescroll/InfiniteScroll" top='true'/>

Die `InfiniteScroll`-Komponente in webforJ lädt automatisch mehr Inhalte, während die Benutzer nach unten scrollen, und beseitigt somit die Notwendigkeit für eine Seitenummerierung. Dies schafft ein nahtloses Erlebnis für Listen, Feeds und datenschwere Ansichten, indem Inhalte nur bei Bedarf geladen werden.

Wenn Benutzer das Ende des scrollbaren Inhalts erreichen, löst `InfiniteScroll` ein Ereignis aus, um mehr Daten zu laden. Während neue Inhalte geladen werden, wird ein [`Spinner`](../components/spinner) mit anpassbarem Text angezeigt, um darauf hinzuweisen, dass weitere Elemente auf dem Weg sind.

<!-- INTRO_END -->

## Zustandsverwaltung {#state-management}

Die `InfiniteScroll`-Komponente gibt Ereignisse aus und verwaltet ihren internen Zustand, um zu helfen, wie und wann Inhalte geladen werden.

<AppLayoutViewer
path='/webforj/infinitescroll?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollView.java'
cssURL='/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

Um mehr Daten abzurufen, wenn der Benutzer scrollt, verwenden Sie die Methode `onScroll()` oder `addScrollListener()`, um einen Listener zu registrieren. Innerhalb des Listeners laden Sie typischerweise zusätzliche Inhalte und rufen `update()` auf, um den Zustand von `InfiniteScroll` zu aktualisieren.

```java
infiniteScroll.onScroll(event -> {
  infiniteScroll.add(new Paragraph("Lade Element"));
  infiniteScroll.update();
});
```

Sobald alle Inhalte geladen wurden, markieren Sie das Scrollen als abgeschlossen, um weitere Trigger zu verhindern. Nach dem Setzen auf abgeschlossen, denken Sie daran, `update()` aufzurufen, um den neuen Zustand anzuwenden:

```java
infiniteScroll.setCompleted(true);
infiniteScroll.update();
```
Dies deaktiviert das weitere Verhalten des unendlichen Scrollens.

:::tip Lade-Flag zurücksetzen
Sie können dieses Flag mit `setCompleted(false)` zurücksetzen, wenn Sie später dem Benutzer erlauben, weitere Inhalte zu laden (z. B. nach einer Aktualisierung).
:::


## Anpassung des Ladeindikators {#loading-indicator-customization}

Standardmäßig zeigt `InfiniteScroll` einen eingebauten Ladeindikator - einen kleinen animierten [`Spinner`](../components/spinner) sowie den Text „Daten werden geladen“. Sie können den angezeigten Text ändern, indem Sie eine benutzerdefinierte Nachricht an den Konstruktor von `InfiniteScroll` übergeben oder `setText()` verwenden.

```java
InfiniteScroll infiniteScroll = new InfiniteScroll("Weitere Datensätze abrufen...");
infiniteScroll.setText("Weitere Elemente laden...");
```

Ähnlich können Sie das während des Ladevorgangs angezeigte [`Icon`](../components/icon) mit `setIcon()` anpassen.

<AppLayoutViewer
path='/webforj/infinitescrollloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollLoadingView.java'
cssURL='/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

### Vollständige Anpassung {#full-customization}

Wenn Sie sowohl den [`Spinner`](../components/spinner) als auch den Text vollständig durch Ihre eigene Markup ersetzen möchten, können Sie Inhalte direkt in den speziellen Inhalts-Slot mit `addToContent()` einfügen.

Wenn Sie den Inhalts-Slot befüllen, ersetzt er das standardmäßige Lade-Layout vollständig.

<AppLayoutViewer
path='/webforj/infinitescrollcustomloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollCustomLoadingView.java'
cssURL='/css/infinitescroll/infinitescrollcustom.css'
height = '400px'
mobile='true'
/>

## Gestaltung {#styling}

<TableBuilder name="InfiniteScroll" />
