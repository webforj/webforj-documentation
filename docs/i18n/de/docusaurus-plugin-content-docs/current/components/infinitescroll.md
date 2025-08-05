---
title: InfiniteScroll
sidebar_position: 60
_i18n_hash: afeb43fb31ce58db2860ceddd8e8527c
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-infinite-scroll" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="infinite-scroll" location="com/webforj/component/infinitescroll/InfiniteScroll" top='true'/>

Die `InfiniteScroll`-Komponente in webforJ lädt automatisch mehr Inhalte, während die Benutzer nach unten scrollen, und erspart so die Notwendigkeit von Pagination. Dies schafft eine flüssige Erfahrung für Listen, Feeds und datenschwere Ansichten, indem Inhalte nur bei Bedarf geladen werden.

Wenn Benutzer das Ende des scrollbaren Inhalts erreichen, löst `InfiniteScroll` ein Ereignis zum Laden weiterer Daten aus. Während neue Inhalte geladen werden, wird ein [`Spinner`](../components/spinner) mit anpassbarem Text angezeigt, um anzuzeigen, dass weitere Elemente auf dem Weg sind.

<AppLayoutViewer
path='/webforj/infinitescroll?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollView.java'
cssURL='/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

## Zustandsverwaltung {#state-management}

Die `InfiniteScroll`-Komponente gibt Ereignisse aus und verwaltet den internen Zustand, um zu helfen, wie und wann Inhalte geladen werden.

Um mehr Daten zu laden, wenn der Benutzer scrollt, verwenden Sie die Methode `onScroll()` oder `addScrollListener()`, um einen Listener zu registrieren. Innerhalb des Listeners laden Sie normalerweise zusätzliche Inhalte und rufen `update()` auf, um den `InfiniteScroll`-Zustand zu aktualisieren.

```java
infiniteScroll.onScroll(event -> {
    infiniteScroll.add(new Paragraph("Geladenes Element"));
    infiniteScroll.update();
});
```

Sobald alle Inhalte geladen sind, markieren Sie das Scrollen als abgeschlossen, um weitere Auslösungen zu verhindern. Nachdem Sie abgeschlossen gesetzt haben, denken Sie daran, `update()` aufzurufen, um den neuen Zustand anzuwenden:

```java
infiniteScroll.setCompleted(true);
infiniteScroll.update();
```
Dies deaktiviert das Verhalten des unendlichen Scrollens.

:::tip Setzen Sie das Ladeflag zurück
Sie können dieses Flag mit `setCompleted(false)` zurücksetzen, wenn Sie später dem Benutzer erlauben, mehr Inhalte zu laden (z. B. nach einem Refresh).
:::

## Anpassung des Ladeindikators {#loading-indicator-customization}

Standardmäßig zeigt `InfiniteScroll` einen integrierten Ladeindikator - einen kleinen animierten [`Spinner`](../components/spinner) zusammen mit einem „Daten werden geladen“-Text. Sie können den angezeigten Text ändern, indem Sie eine benutzerdefinierte Nachricht an den `InfiniteScroll`-Konstruktor übergeben oder `setText()` verwenden.

```java
InfiniteScroll infiniteScroll = new InfiniteScroll("Weitere Datensätze werden abgerufen...");
infiniteScroll.setText("Weitere Elemente werden geladen...");
```

Ebenso können Sie das während des Ladevorgangs angezeigte [`Icon`](../components/icon) mit `setIcon()` anpassen.

<AppLayoutViewer
path='/webforj/infinitescrollloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollLoadingView.java'
cssURL='/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

### Vollständige Anpassung {#full-customization}

Wenn Sie sowohl den [`Spinner`](../components/spinner) als auch den Text vollständig durch Ihr eigenes Markup ersetzen möchten, können Sie Inhalte direkt in den speziellen Inhaltsbereich mithilfe von `addToContent()` hinzufügen.

Wenn Sie den Inhaltsbereich füllen, ersetzt dies das Standard-Lade-Layout vollständig.

<AppLayoutViewer
path='/webforj/infinitescrollcustomloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollCustomLoadingView.java'
cssURL='/css/infinitescroll/infinitescrollcustom.css'
height = '400px'
mobile='true'
/>

## Styling {#styling}

<TableBuilder name="InfiniteScroll" />
