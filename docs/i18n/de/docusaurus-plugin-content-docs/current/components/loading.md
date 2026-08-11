---
title: Loading
sidebar_position: 65
description: >-
  Overlay a parent container with the Loading component to block interaction
  during async tasks, with backdrop and spinner customization.
_i18n_hash: e17c9249d41752ed1f4b98d18028371a
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

Die `Loading`-Komponente zeigt eine Überlagerung über einem bestimmten Element oder Bereich an, um anzuzeigen, dass eine Operation im Gange ist und vorübergehend die Interaktion blockiert. Sie ist ideal für Aufgaben wie das Laden von Daten, Berechnungen oder Hintergrundprozesse. Für globale, appweite Prozesse deckt die [`BusyIndicator`](../components/busyindicator) Komponente die gesamte Schnittstelle ab.

<!-- INTRO_END -->

## Grundlagen {#basics}

Der einfachste Weg, eine `Loading`-Komponente zu erstellen, besteht darin, sie ohne zusätzliche Einstellungen zu initialisieren. Standardmäßig zeigt dies einen grundlegenden Spinner über dem übergeordneten Inhalt an. Sie können jedoch auch eine Nachricht für mehr Kontext bereitstellen.

Hier ist ein Beispiel für die Erstellung einer `Loading`-Komponente mit einer Nachricht:

<ComponentDemo
path='/webforj/loadingdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java',
  'src/main/frontend/css/loadingstyles/loadingdemo.css',
]}
height='300px'
/>

## Bereichseingrenzung {#scoping}

Die `Loading`-Komponente in webforJ kann sich auf einen bestimmten übergeordneten Container, wie ein `Div`, beschränken, sodass sie nur die Benutzerinteraktion innerhalb dieses Elements blockiert. Standardmäßig ist die `Loading`-Komponente relativ zu ihrem übergeordneten Element, was bedeutet, dass sie das übergeordnete Element überlagert, anstatt die gesamte App.

Um die `Loading`-Komponente auf ihr übergeordnetes Element zu beschränken, fügen Sie einfach die `Loading`-Komponente dem übergeordneten Container hinzu. Zum Beispiel, wenn Sie sie zu einem `Div` hinzufügen, gilt die Ladeüberlagerung nur für dieses `Div`:

```java
Div parentDiv = new Div();
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Loading blockiert nur die Interaktion innerhalb des parentDiv
```

## Hintergrund {#backdrop}

Die `Loading`-Komponente in webforJ ermöglicht es Ihnen, einen Hintergrund anzuzeigen, um die Benutzerinteraktion zu blockieren, während ein Prozess läuft. Standardmäßig aktiviert die Komponente den Hintergrund, aber Sie haben die Möglichkeit, ihn bei Bedarf auszuschalten.

Für die `Loading`-Komponente ist der Hintergrund standardmäßig sichtbar. Sie können ihn mithilfe der Methode `setBackdropVisible()` explizit aktivieren oder deaktivieren:

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // Deaktiviert den Hintergrund
loading.open();
```
:::info Hintergrund Aus
Selbst wenn Sie den Hintergrund deaktivieren, blockiert die `Loading`-Komponente weiterhin die Benutzerinteraktion, um sicherzustellen, dass der zugrunde liegende Prozess ununterbrochen abgeschlossen wird. Der Hintergrund steuert lediglich die visuelle Überlagerung, nicht das Verhalten des Interaktionsblockierens.
:::

## `Spinner` {#spinner}

Die `Loading`-Komponente in webforJ enthält einen `Spinner`, der visuell anzeigt, dass ein Hintergrundvorgang ausgeführt wird. Sie können diesen Spinner mit mehreren Optionen anpassen, einschließlich seiner Größe, Geschwindigkeit, Richtung, Thema und Sichtbarkeit.

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
   Beim Abrufen von Daten von einem Server oder API überlagert die `Loading`-Komponente einen bestimmten Abschnitt der Benutzeroberfläche, z. B. eine Karte oder ein Formular, um die Benutzer darüber zu informieren, dass das System im Hintergrund arbeitet. Dies ist ideal, wenn Sie den Fortschritt nur an einem Teil des Bildschirms anzeigen möchten, ohne die gesamte Schnittstelle zu blockieren.

- **Inhalt laden in Karten/Abschnitten**
   Die `Loading`-Komponente kann auf spezifische Bereiche einer Seite beschränkt werden, z. B. einzelne Karten oder Container. Dies ist nützlich, wenn Sie anzeigen möchten, dass ein bestimmter Abschnitt der Benutzeroberfläche noch geladen wird, während Benutzer mit anderen Teilen der Seite interagieren können.

- **Komplexe Formularübermittlungen**
   Bei längeren Formularübermittlungen, bei denen die Validierung oder Verarbeitung Zeit in Anspruch nimmt, bietet die `Loading`-Komponente visuelles Feedback für die Benutzer und versichert ihnen, dass ihre Eingabe aktiv verarbeitet wird.

## Styling {#styling}

<TableBuilder name="Loading" />
