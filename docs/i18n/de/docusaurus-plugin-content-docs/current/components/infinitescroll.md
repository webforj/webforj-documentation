---
title: InfiniteScroll
sidebar_position: 60
description: >-
  Load more content as users scroll with the InfiniteScroll component, emitting
  scroll events and showing a customizable loading spinner.
_i18n_hash: f021168e8d6187e38da9107bd2f3ad65
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-infinite-scroll" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="infinite-scroll" location="com/webforj/component/infinitescroll/InfiniteScroll" top='true'/>

Die `InfiniteScroll`-Komponente in webforJ lädt automatisch mehr Inhalte, während Benutzer nach unten scrollen, wodurch die Notwendigkeit von Seitenumbrüchen entfällt. Dies sorgt für ein flüssiges Erlebnis bei Listen, Feeds und datenintensiven Ansichten, da Inhalte nur bei Bedarf geladen werden.

Wenn Benutzer den unteren Rand des scrollbaren Inhalts erreichen, löst `InfiniteScroll` ein Ereignis zum Laden weiterer Daten aus. Während neue Inhalte geladen werden, wird ein [`Spinner`](../components/spinner) mit anpassbarem Text angezeigt, um anzuzeigen, dass weitere Elemente unterwegs sind.

<!-- INTRO_END -->

## Zustandsverwaltung {#state-management}

Die `InfiniteScroll`-Komponente gibt Ereignisse aus und verwaltet den internen Zustand, um zu helfen, wie und wann Inhalte geladen werden.

<ComponentDemo
path='/webforj/infinitescroll'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollView.java',
  'src/main/frontend/css/infinitescroll/infinitescroll.css',
]}
/>

Um mehr Daten zu laden, wenn der Benutzer scrollt, verwenden Sie die Methode `onScroll()` oder `addScrollListener()`, um einen Listener zu registrieren. Innerhalb des Listeners laden Sie typischerweise zusätzliche Inhalte und rufen `update()` auf, um den Zustand von `InfiniteScroll` zu aktualisieren.

```java
infiniteScroll.onScroll(event -> {
  infiniteScroll.add(new Paragraph("Geladenes Element"));
  infiniteScroll.update();
});
```

Sobald alle Inhalte geladen wurden, markieren Sie das Scrollen als abgeschlossen, um weitere Trigger zu verhindern. Nach dem Setzen auf abgeschlossen, denken Sie daran, `update()` aufzurufen, um den neuen Zustand anzuwenden:

```java
infiniteScroll.setCompleted(true);
infiniteScroll.update();
```
Dies deaktiviert weiteres unendliches Scrollverhalten.

:::tip Ladeflag zurücksetzen
Sie können dieses Flag zurücksetzen, indem Sie `setCompleted(false)` verwenden, wenn Sie später dem Benutzer ermöglichen, mehr Inhalte zu laden (z. B. nach einer Aktualisierung).
:::


## Anpassung des Ladeanzeigers {#loading-indicator-customization}

Standardmäßig zeigt `InfiniteScroll` einen eingebauten Ladeanzeiger - einen kleinen animierten [`Spinner`](../components/spinner) zusammen mit dem Text „Daten werden geladen“. Sie können den angezeigten Text ändern, indem Sie eine benutzerdefinierte Nachricht an den Konstruktor von `InfiniteScroll` übergeben oder `setText()` verwenden.

```java
InfiniteScroll infiniteScroll = new InfiniteScroll("Weitere Aufzeichnungen werden abgerufen...");
infiniteScroll.setText("Weitere Elemente werden geladen...");
```

Ebenso können Sie das während des Ladevorgangs angezeigte [`Icon`](../components/icon) anpassen, indem Sie `setIcon()` verwenden.

<ComponentDemo
path='/webforj/infinitescrollloading'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollLoadingView.java',
  'src/main/frontend/css/infinitescroll/infinitescroll.css',
]}
/>

### Vollständige Anpassung {#full-customization}

Wenn Sie sowohl den [`Spinner`](../components/spinner) als auch den Text vollständig durch Ihr eigenes Markup ersetzen möchten,
können Sie Inhalte direkt in den speziellen Inhaltsplatzhalter mit `addToContent()` einfügen.

Wenn Sie den Inhaltsplatz füllen, ersetzt er das Standard-Lade-Layout vollständig.

<ComponentDemo
path='/webforj/infinitescrollcustomloading'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollCustomLoadingView.java',
  'src/main/frontend/css/infinitescroll/infinitescrollcustom.css',
]}
/>

## Gestaltung {#styling}

<TableBuilder name="InfiniteScroll" />
