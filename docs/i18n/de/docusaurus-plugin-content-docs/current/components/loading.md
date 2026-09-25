---
title: Loading
sidebar_position: 65
description: >-
  Overlay a parent container with the Loading component to block interaction
  during async tasks, with backdrop and spinner customization.
_i18n_hash: 8106f15ba96904324822afd0169ec09b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

Die `Loading`-Komponente zeigt ein Overlay über einer bestimmten Komponente oder einem Bereich an und signalisiert, dass eine Operation im Gange ist und die Interaktion vorübergehend blockiert wird. Sie eignet sich gut für Aufgaben wie das Laden von Daten, Berechnungen oder Hintergrundprozesse. Für globale, anwendungsweite Prozesse deckt die [`BusyIndicator`](../components/busyindicator)-Komponente stattdessen die gesamte Benutzeroberfläche ab.

<!-- INTRO_END -->

Die Initialisierung einer `Loading`-Komponente ohne zusätzliche Einstellungen zeigt einen Spinner über dem übergeordneten Inhalt an. Geben Sie eine Nachricht an, wie im folgenden Beispiel, wenn der Prozess mehr Kontext benötigt.

<ComponentDemo
path='/webforj/loadingdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java',
  'src/main/frontend/css/loadingstyles/loadingdemo.css',
]}
height='300px'
/>

## Bereichseingrenzung {#scoping}

Die `Loading`-Komponente in webforJ kann sich auf einen bestimmten übergeordneten Container, wie einen `Div`, beschränken und sicherstellen, dass sie nur die Benutzerinteraktion innerhalb dieses Elements blockiert. Standardmäßig ist die `Loading`-Komponente relativ zu ihrem übergeordneten Element, was bedeutet, dass sie die übergeordnete Komponente überlagert und nicht die gesamte Anwendung.

Um die `Loading`-Komponente auf ihren übergeordneten Container zu beschränken, fügen Sie einfach die `Loading`-Komponente dem übergeordneten Container hinzu. Wenn Sie sie beispielsweise zu einem `Div` hinzufügen, gilt das Ladeoverlay nur für diesen `Div`:

```java
Div parentDiv = new Div();
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Loading blockiert nur die Interaktion innerhalb des parentDiv
```

## Hintergrund {#backdrop}

Die `Loading`-Komponente in webforJ ermöglicht es Ihnen, einen Hintergrund anzuzeigen, um die Benutzerinteraktion zu blockieren, während ein Prozess läuft. Standardmäßig aktiviert die Komponente den Hintergrund, aber Sie haben die Möglichkeit, ihn bei Bedarf auszuschalten.

Für die `Loading`-Komponente ist der Hintergrund standardmäßig sichtbar. Sie können ihn explizit aktivieren oder mit der Methode `setBackdropVisible()` deaktivieren:

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // Deaktiviert den Hintergrund
loading.open();
```
:::info Hintergrund Aus
Selbst wenn Sie den Hintergrund deaktivieren, blockiert die `Loading`-Komponente weiterhin die Benutzerinteraktion, um sicherzustellen, dass der zugrunde liegende Prozess ununterbrochen abgeschlossen wird. Der Hintergrund steuert lediglich das visuelle Overlay, nicht das Verhalten der Interaktionsblockierung.
:::

## `Spinner` {#spinner}

Die `Loading`-Komponente in webforJ enthält einen `Spinner`, der visuell anzeigt, dass ein Hintergrundvorgang im Gange ist. Sie können diesen Spinner mit mehreren Optionen anpassen, einschließlich seiner Größe, Geschwindigkeit, Richtung, Themen und Sichtbarkeit.

Hier ist ein Beispiel, wie Sie den Spinner innerhalb einer `Loading`-Komponente anpassen können:

<ComponentDemo
path='/webforj/loadingspinnerdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingSpinnerDemoView.java',
  'src/main/frontend/css/loadingstyles/loadingspinnerdemo.css',
]}
height='300px'
/>

## Anwendungsfälle {#use-cases}
- **Datenabfrage**
   Bei der Abfrage von Daten von einem Server oder einer API überlagert die `Loading`-Komponente einen bestimmten Abschnitt der Benutzeroberfläche, wie eine Karte oder ein Formular, um die Benutzer darüber zu informieren, dass das System im Hintergrund arbeitet. Dies ist ideal, wenn Sie den Fortschritt nur in einem Teil des Bildschirms anzeigen möchten, ohne die gesamte Benutzeroberfläche zu blockieren.

- **Inhalt Laden in Karten/Abschnitten**
   Die `Loading`-Komponente kann auf bestimmte Bereiche einer Seite, wie einzelne Karten oder Container, beschränkt werden. Dies ist nützlich, wenn Sie anzeigen möchten, dass ein bestimmter Abschnitt der Benutzeroberfläche noch geladen wird, während Benutzer mit anderen Teilen der Seite interagieren können.

- **Komplexe Formularübermittlungen**
   Bei längeren Formularübermittlungen, bei denen Validierung oder Verarbeitung Zeit in Anspruch nimmt, bietet die `Loading`-Komponente visuelles Feedback für die Benutzer und beruhigt sie, dass ihre Eingaben aktiv verarbeitet werden.

## Stil {#styling}

<TableBuilder name="Loading" />
