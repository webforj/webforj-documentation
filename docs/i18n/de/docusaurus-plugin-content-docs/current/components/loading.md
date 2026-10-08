---
title: Loading
sidebar_position: 65
description: >-
  Overlay a parent container with the Loading component to block interaction
  during async tasks, with backdrop and spinner customization.
_i18n_hash: 6e4493f64eb29033ed8a5d594accdb33
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

Die `Loading`-Komponente zeigt eine Überlagerung über einem bestimmten Komponent oder Bereich an und signalisiert, dass ein Vorgang im Gange ist, während sie vorübergehend die Interaktion blockiert. Sie eignet sich gut für Aufgaben wie Datenladung, Berechnungen oder Hintergrundprozesse. Für globale, anwendungsweite Prozesse deckt die [`BusyIndicator`](../components/busyindicator)-Komponente stattdessen die gesamte Schnittstelle ab.

<!-- INTRO_END -->

Ein initialisiertes `Loading`-Element ohne weitere Einstellungen zeigt einen Spinner über dem übergeordneten Inhalt an. Übergeben Sie eine Nachricht, wie im folgenden Beispiel, wenn der Vorgang mehr Kontext benötigt.

<ComponentDemo
path='/webforj/loadingdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java',
  'src/main/frontend/css/loadingstyles/loadingdemo.css',
]}
height='300px'
/>

## Scoping {#scoping}

Die `Loading`-Komponente in webforJ kann sich auf einen bestimmten Elterncontainer, wie ein `Div`, beschränken und stellt sicher, dass sie nur die Benutzerinteraktion innerhalb dieses Elements blockiert. Standardmäßig ist die `Loading`-Komponente relativ zu ihrem Elternteil, was bedeutet, dass sie die übergeordnete Komponente überlagert, anstatt die gesamte Anwendung.

Um die `Loading`-Komponente auf ihr Elternteil zu beschränken, fügen Sie einfach die `Loading`-Komponente dem Elterncontainer hinzu. Wenn Sie sie beispielsweise zu einem `Div` hinzufügen, gilt die Ladeansicht nur für dieses `Div`:

```java
Div parentDiv = new Div();
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Loading blockiert nur die Interaktion innerhalb des parentDiv
```

## Backdrop {#backdrop}

Die `Loading`-Komponente in webforJ ermöglicht es Ihnen, einen Hintergrund (Backdrop) anzuzeigen, um die Benutzerinteraktion während eines laufenden Prozesses zu blockieren. Standardmäßig aktiviert die Komponente den Hintergrund, aber Sie haben die Möglichkeit, ihn bei Bedarf auszuschalten.

Für die `Loading`-Komponente ist der Hintergrund standardmäßig sichtbar. Sie können ihn explizit aktivieren oder deaktivieren, indem Sie die Methode `setBackdropVisible()` verwenden:

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // Deaktiviert den Hintergrund
loading.open();
```
:::info Hintergrund Aus
Auch wenn Sie den Hintergrund ausschalten, blockiert die `Loading`-Komponente weiterhin die Benutzerinteraktion, um sicherzustellen, dass der zugrunde liegende Prozess ohne Unterbrechung abgeschlossen wird. Der Hintergrund steuert lediglich die visuelle Überlagerung, nicht das Verhalten der Interaktionsblockierung.
:::

## `Spinner` {#spinner}

Die `Loading`-Komponente in webforJ enthält einen `Spinner`, der visuell anzeigt, dass ein Hintergrundvorgang im Gange ist. Sie können diesen Spinner mit mehreren Optionen anpassen, einschließlich Größe, Geschwindigkeit, Richtung, Thema und Sichtbarkeit.

Hier ist ein Beispiel, wie Sie den Spinner innerhalb einer `Loading`-Komponente anpassen können:

<ComponentDemo
path='/webforj/loadingspinnerdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingSpinnerDemoView.java',
]}
height='300px'
/>

## Anwendungsfälle {#use-cases}
- **Datenabruf**
   Bei der Abholung von Daten von einem Server oder einer API überlagert die `Loading`-Komponente einen bestimmten Abschnitt der Benutzeroberfläche, wie eine Karte oder ein Formular, um die Benutzer darüber zu informieren, dass das System im Hintergrund arbeitet. Dies ist ideal, wenn Sie den Fortschritt nur in einem Teil des Bildschirms anzeigen möchten, ohne die gesamte Schnittstelle zu blockieren.

- **Inhalt Laden in Karten/Abschnitten**
   Die `Loading`-Komponente kann auf bestimmte Bereiche einer Seite, wie einzelne Karten oder Container, beschränkt werden. Dies ist nützlich, wenn Sie anzeigen möchten, dass ein bestimmter Abschnitt der Benutzeroberfläche noch geladen wird, während die Benutzer mit anderen Teilen der Seite interagieren können.

- **Komplexe Formularübermittlungen**
   Bei längeren Formularübermittlungen, bei denen Validierung oder Verarbeitung Zeit in Anspruch nimmt, bietet die `Loading`-Komponente visuelles Feedback für die Benutzer und beruhigt sie, dass ihre Eingaben aktiv verarbeitet werden.

## Styling {#styling}

<TableBuilder name="Loading" />
