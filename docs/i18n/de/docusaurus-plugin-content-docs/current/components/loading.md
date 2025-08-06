---
title: Loading
sidebar_position: 65
_i18n_hash: fd3e1e31d1a614494358f9d67a9d3cd8
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

Die `Loading`-Komponente in webforJ zeigt ein Overlay an, das den Verarbeitungsvorgang signalisiert und vorübergehend die Benutzerinteraktion verhindert, bis die Aufgabe abgeschlossen ist. Diese Funktion verbessert das Benutzererlebnis, insbesondere in Situationen, in denen Aufgaben wie Datenladen, Berechnungen oder Hintergrundprozesse einige Zeit in Anspruch nehmen können. Für globale, appweite Prozesse sollten Sie die [`BusyIndicator`](../components/busyindicator)-Komponente verwenden, die die Interaktion über die gesamte Benutzeroberfläche blockiert.

## Grundlagen {#basics}

Der einfachste Weg, eine `Loading`-Komponente zu erstellen, besteht darin, sie ohne zusätzliche Einstellungen zu initialisieren. Standardmäßig wird dadurch ein einfacher Spinner über dem übergeordneten Inhalt angezeigt. Sie können jedoch auch eine Nachricht für zusätzlichen Kontext bereitstellen.

Hier ist ein Beispiel, wie man eine `Loading`-Komponente mit einer Nachricht erstellt:

<ComponentDemo 
path='/webforj/loadingdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java'
cssURL='/css/loadingstyles/loadingdemo.css'
height = '300px'
/>

## Scoping {#scoping}

Die `Loading`-Komponente in webforJ kann sich auf einen bestimmten übergeordneten Container, wie ein `Div`, beschränken, sodass sie nur die Benutzerinteraktion innerhalb dieses Elements blockiert. Standardmäßig ist die `Loading`-Komponente relativ zu ihrem übergeordneten Element, was bedeutet, dass sie die übergeordnete Komponente überlagert und nicht die gesamte App.

Um die `Loading`-Komponente auf ihren übergeordneten Container zu beschränken, fügen Sie einfach die `Loading`-Komponente dem übergeordneten Container hinzu. Wenn Sie sie beispielsweise zu einem `Div` hinzufügen, gilt das Ladeoverlay nur für dieses `Div`:

```java
Div parentDiv = new Div();  
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Loading blockiert nur die Interaktion innerhalb des parentDiv
```

## Hintergrund {#backdrop}

Die `Loading`-Komponente in webforJ ermöglicht es Ihnen, einen Hintergrund anzuzeigen, um die Benutzerinteraktion während eines laufenden Prozesses zu blockieren. Standardmäßig wird der Hintergrund aktiviert, Sie haben jedoch die Möglichkeit, ihn bei Bedarf auszuschalten.

Für die `Loading`-Komponente ist der Hintergrund standardmäßig sichtbar. Sie können diesen explizit aktivieren oder mit der Methode `setBackdropVisible()` ausschalten:

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // Deaktiviert den Hintergrund
loading.open();
```
:::info Hintergrund Aus
Selbst wenn Sie den Hintergrund ausschalten, blockiert die `Loading`-Komponente weiterhin die Benutzerinteraktion, um sicherzustellen, dass der zugrunde liegende Prozess ununterbrochen abgeschlossen wird. Der Hintergrund steuert lediglich das visuelle Overlay, nicht das Verhalten der Interaktionsblockierung.
:::

## `Spinner` {#spinner}

Die `Loading`-Komponente in webforJ enthält einen `Spinner`, der visuell anzeigt, dass im Hintergrund ein Vorgang ausgeführt wird. Sie können diesen Spinner mit verschiedenen Optionen anpassen, darunter Größe, Geschwindigkeit, Richtung, Thema und Sichtbarkeit.

Hier ist ein Beispiel, wie Sie den Spinner innerhalb einer `Loading`-Komponente anpassen können:

<ComponentDemo 
path='/webforj/loadingspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/loading/LoadingSpinnerDemoView.java'
cssURL='/css/loadingstyles/loadingspinnerdemo.css'
height = '300px'
/>

## Anwendungsfälle {#use-cases}
- **Daten abrufen**  
   Wenn Daten von einem Server oder API abgerufen werden, überlagert die `Loading`-Komponente einen bestimmten Abschnitt der Benutzeroberfläche, z. B. eine Karte oder ein Formular, um die Benutzer darüber zu informieren, dass das System im Hintergrund arbeitet. Dies ist ideal, wenn Sie den Fortschritt nur in einem Teil des Bildschirms anzeigen möchten, ohne die gesamte Benutzeroberfläche zu blockieren.

- **Inhalte in Karten/Abschnitten laden**  
   Die `Loading`-Komponente kann auf bestimmte Bereiche einer Seite beschränkt werden, wie z. B. individuelle Karten oder Container. Dies ist nützlich, wenn Sie anzeigen möchten, dass ein bestimmter Abschnitt der Benutzeroberfläche noch geladen wird, während die Benutzer mit anderen Teilen der Seite interagieren können.

- **Komplexe Formularübermittlungen**  
   Bei längeren Formularübermittlungen, bei denen die Validierung oder Verarbeitung Zeit in Anspruch nimmt, bietet die `Loading`-Komponente den Benutzern visuelles Feedback und versichert ihnen, dass ihre Eingaben aktiv verarbeitet werden.

## Styling {#styling}

<TableBuilder name="Loading" />
