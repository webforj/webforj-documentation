---
title: Loading
sidebar_position: 65
_i18n_hash: 9bdb4d5c978b4070d3628566e5105088
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

Die `Loading`-Komponente in webforJ zeigt ein Overlay an, das signalisiert, dass eine Operation bearbeitet wird, wodurch die Benutzerinteraktion vorübergehend verhindert wird, bis die Aufgabe abgeschlossen ist. Diese Funktion verbessert die Benutzererfahrung, insbesondere in Situationen, in denen Aufgaben wie das Laden von Daten, Berechnungen oder Hintergrundprozesse einige Zeit in Anspruch nehmen können. Für globale, anwendungsweite Prozesse sollten Sie die [`BusyIndicator`](../components/busyindicator)-Komponente in Betracht ziehen, die die Interaktion über die gesamte Benutzeroberfläche blockiert.

## Grundlagen {#basics}

Der einfachste Weg, eine `Loading`-Komponente zu erstellen, besteht darin, sie ohne zusätzliche Einstellungen zu initialisieren. Standardmäßig wird dabei ein einfacher Spinner über dem übergeordneten Inhalt angezeigt. Sie können auch eine Nachricht bereitstellen, um mehr Kontext zu geben.

Hier ist ein Beispiel für die Erstellung einer `Loading`-Komponente mit einer Nachricht:

<ComponentDemo 
path='/webforj/loadingdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java'
cssURL='/css/loadingstyles/loadingdemo.css'
height = '300px'
/>

## Bereichseingrenzung {#scoping}

Die `Loading`-Komponente in webforJ kann sich auf einen spezifischen übergeordneten Container, wie ein `Div`, beziehen und gewährleistet somit, dass sie nur die Benutzerinteraktion innerhalb dieses Elements blockiert. Standardmäßig ist die `Loading`-Komponente relativ zu ihrem übergeordneten Element, was bedeutet, dass sie das Elternkomponenten overlayt, anstatt die gesamte Anwendung.

Um die `Loading`-Komponente auf ihr übergeordnetes Element zu beschränken, fügen Sie einfach die `Loading`-Komponente dem übergeordneten Container hinzu. Wenn Sie sie beispielsweise zu einem `Div` hinzufügen, wird das Ladeoverlay nur auf dieses `Div` angewendet:

```java
Div parentDiv = new Div();  
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Loading blockiert nur die Interaktion innerhalb des parentDiv
```

## Hintergrund {#backdrop}

Die `Loading`-Komponente in webforJ ermöglicht es Ihnen, einen Hintergrund anzuzeigen, um die Benutzerinteraktion während eines laufenden Prozesses zu blockieren. Standardmäßig aktiviert die Komponente den Hintergrund, Sie haben jedoch die Möglichkeit, ihn bei Bedarf auszuschalten.

Für die `Loading`-Komponente ist der Hintergrund standardmäßig sichtbar. Sie können ihn mithilfe der Methode `setBackdropVisible()` explizit aktivieren oder ausschalten:

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // Deaktiviert den Hintergrund
loading.open();
```
:::info Hintergrund Aus
Selbst wenn Sie den Hintergrund ausschalten, blockiert die `Loading`-Komponente weiterhin die Benutzerinteraktion, um sicherzustellen, dass der zugrunde liegende Prozess ununterbrochen abgeschlossen wird. Der Hintergrund steuert lediglich die visuelle Überlagerung, nicht das Verhalten des Interaktionsblocks.
:::

## `Spinner` {#spinner}

Die `Loading`-Komponente in webforJ enthält einen `Spinner`, der visuell anzeigt, dass eine Hintergrundoperation im Gange ist. Sie können diesen Spinner mit mehreren Optionen anpassen, einschließlich Größe, Geschwindigkeit, Richtung, Thema und Sichtbarkeit.

Hier ist ein Beispiel, wie Sie den Spinner innerhalb einer `Loading`-Komponente anpassen können:

<ComponentDemo 
path='/webforj/loadingspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/loading/LoadingSpinnerDemoView.java'
cssURL='/css/loadingstyles/loadingspinnerdemo.css'
height = '300px'
/>

## Anwendungsfälle {#use-cases}
- **Datenabruf**  
   Beim Abrufen von Daten von einem Server oder API overlayt die `Loading`-Komponente einen bestimmten Bereich der Benutzeroberfläche, wie z. B. eine Karte oder ein Formular, um die Benutzer darüber zu informieren, dass das System im Hintergrund arbeitet. Dies ist ideal, wenn Sie den Fortschritt nur in einem Teil des Bildschirms anzeigen möchten, ohne die gesamte Benutzeroberfläche zu blockieren.

- **Inhalte laden in Karten/Bereichen**  
   Die `Loading`-Komponente kann auf bestimmte Bereiche einer Seite beschränkt werden, z. B. auf einzelne Karten oder Container. Dies ist nützlich, wenn Sie anzeigen möchten, dass ein bestimmter Bereich der Benutzeroberfläche noch geladen wird, während die Benutzer mit anderen Teilen der Seite interagieren können.

- **Komplexe Formularübermittlungen**  
   Bei längeren Formularübermittlungen, bei denen Validierung oder Verarbeitung Zeit in Anspruch nehmen, bietet die `Loading`-Komponente visuelles Feedback für die Benutzer und beruhigt sie, dass ihre Eingaben aktiv verarbeitet werden.

## Stil {#styling}

<TableBuilder name="Loading" />
