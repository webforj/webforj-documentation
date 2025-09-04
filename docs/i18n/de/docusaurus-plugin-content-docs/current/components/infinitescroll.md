---
title: InfiniteScroll
sidebar_position: 60
_i18n_hash: 3384cb35d5087561cc9be2c11b95c7e1
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-infinite-scroll" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="infinite-scroll" location="com/webforj/component/infinitescroll/InfiniteScroll" top='true'/>

Die `InfiniteScroll`-Komponente in webforJ lädt automatisch mehr Inhalte, während Benutzer nach unten scrollen, und beseitigt so die Notwendigkeit für die Paginierung. Dies schafft ein reibungsloses Erlebnis für Listen, Feeds und datenintensive Ansichten, indem Inhalte nur bei Bedarf geladen werden.

Wenn Benutzer den unteren Rand des scrollbaren Inhalts erreichen, löst `InfiniteScroll` ein Ereignis zum Laden weiterer Daten aus. Während neuer Inhalt geladen wird, zeigt es einen [`Spinner`](../components/spinner) mit anpassbarem Text an, um anzuzeigen, dass weitere Elemente unterwegs sind.

<AppLayoutViewer
path='/webforj/infinitescroll?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollView.java'
cssURL='/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

## Zustandsverwaltung {#state-management}

Die `InfiniteScroll`-Komponente gibt Ereignisse aus und verwaltet den internen Zustand, um zu helfen, wie und wann Inhalte geladen werden.

Um mehr Daten abzurufen, wenn der Benutzer scrollt, verwenden Sie die Methode `onScroll()` oder `addScrollListener()`, um einen Listener zu registrieren. Innerhalb des Listeners laden Sie typischerweise zusätzliche Inhalte und rufen `update()` auf, um den Zustand von `InfiniteScroll` zu aktualisieren.

```java
infiniteScroll.onScroll(event -> {
    infiniteScroll.add(new Paragraph("Gelaadener Artikel"));
    infiniteScroll.update();
});
```

Sobald alle Inhalte geladen sind, markieren Sie das Scrollen als abgeschlossen, um weitere Auslösungen zu verhindern. Nach dem Setzen auf abgeschlossen, denken Sie daran, `update()` aufzurufen, um den neuen Zustand anzuwenden:

```java
infiniteScroll.setCompleted(true);
infiniteScroll.update();
```
Dies deaktiviert das weitere unendliche Scrollverhalten.

:::tip Ladeflag zurücksetzen
Sie können dieses Flag mit `setCompleted(false)` zurücksetzen, wenn Sie später Benutzern erlauben, mehr Inhalte zu laden (z. B. nach einem Refresh).
:::

## Anpassung des Ladeanzeigen {#loading-indicator-customization}

Standardmäßig zeigt `InfiniteScroll` eine eingebaute Ladeanzeige - einen kleinen animierten [`Spinner`](../components/spinner) zusammen mit einem Text „Daten werden geladen“. Sie können den angezeigten Text ändern, indem Sie eine benutzerdefinierte Nachricht an den Konstruktor von `InfiniteScroll` übergeben oder `setText()` verwenden.

```java
InfiniteScroll infiniteScroll = new InfiniteScroll("Weitere Datensätze abrufen...");
infiniteScroll.setText("Mehr Artikel laden...");
```

Ebenso können Sie das während des Ladens angezeigte [`Icon`](../components/icon) mithilfe von `setIcon()` anpassen.

<AppLayoutViewer
path='/webforj/infinitescrollloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollLoadingView.java'
cssURL='/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

### Vollständige Anpassung {#full-customization}

Wenn Sie sowohl den [`Spinner`](../components/spinner) als auch den Text vollständig durch Ihr eigenes Markup ersetzen möchten,
können Sie Inhalte direkt in den speziellen Inhaltsbereich mit `addToContent()` einfügen.

Wenn Sie den Inhaltsbereich füllen, wird das Standard-Lade-Layout vollständig ersetzt.

<AppLayoutViewer
path='/webforj/infinitescrollcustomloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollCustomLoadingView.java'
cssURL='/css/infinitescroll/infinitescrollcustom.css'
height = '400px'
mobile='true'
/>

## Stil {#styling}

<TableBuilder name="InfiniteScroll" />
