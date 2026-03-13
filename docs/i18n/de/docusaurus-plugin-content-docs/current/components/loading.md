---
title: Loading
sidebar_position: 65
_i18n_hash: 45fa6bcfc4a2fd5995a06dc98b6f91bf
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

Die `Loading`-Komponente zeigt ein Overlay über einer bestimmten Komponente oder Fläche an und signalisiert, dass ein Vorgang im Gange ist, und blockiert vorübergehend die Interaktion. Sie eignet sich gut für Aufgaben wie das Laden von Daten, Berechnungen oder Hintergrundprozesse. Für globale, appweite Prozesse überdeckt die [`BusyIndicator`](../components/busyindicator)-Komponente stattdessen die gesamte Benutzeroberfläche.

<!-- INTRO_END -->

## Grundlagen {#basics}

Die einfachste Möglichkeit, eine `Loading`-Komponente zu erstellen, besteht darin, sie ohne zusätzliche Einstellungen zu initialisieren. Standardmäßig zeigt dies einen einfachen Spinner über dem übergeordneten Inhalt an. Sie können jedoch auch eine Nachricht zur weiteren Erklärung bereitstellen.

Hier ist ein Beispiel für die Erstellung einer `Loading`-Komponente mit einer Nachricht:

<ComponentDemo 
path='/webforj/loadingdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java'
cssURL='/css/loadingstyles/loadingdemo.css'
height = '300px'
/>

## Bereichszuweisung {#scoping}

Die `Loading`-Komponente in webforJ kann sich auf einen bestimmten übergeordneten Container wie ein `Div` beschränken und dabei sicherstellen, dass sie nur die Benutzerinteraktion innerhalb dieses Elements blockiert. Standardmäßig ist die `Loading`-Komponente relativ zu ihrem Elternelement, was bedeutet, dass sie die übergeordnete Komponente überlagert, anstatt die gesamte App.

Um die `Loading`-Komponente auf ihr übergeordnetes Element zu beschränken, fügen Sie die `Loading`-Komponente einfach dem übergeordneten Container hinzu. Wenn Sie sie beispielsweise zu einem `Div` hinzufügen, gilt das Ladeoverlay nur für dieses `Div`:

```java
Div parentDiv = new Div();  
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Loading blockiert nur die Interaktion innerhalb des parentDiv
```

## Hintergrund {#backdrop}

Die `Loading`-Komponente in webforJ ermöglicht es Ihnen, einen Hintergrund anzuzeigen, um die Benutzerinteraktion zu blockieren, während ein Prozess im Gange ist. Standardmäßig aktiviert die Komponente den Hintergrund, aber Sie haben die Möglichkeit, ihn bei Bedarf auszuschalten.

Für die `Loading`-Komponente ist der Hintergrund standardmäßig sichtbar. Sie können ihn mithilfe der Methode `setBackdropVisible()` explizit aktivieren oder ausschalten:

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // Deaktiviert den Hintergrund
loading.open();
```
:::info Hintergrund aus
Selbst wenn Sie den Hintergrund deaktivieren, blockiert die `Loading`-Komponente weiterhin die Benutzerinteraktion, um sicherzustellen, dass der zugrunde liegende Prozess ununterbrochen abgeschlossen wird. Der Hintergrund steuert lediglich die visuelle Überlagerung, nicht das Verhalten der Interaktionsblockierung.
:::

## `Spinner` {#spinner}

Die `Loading`-Komponente in webforJ enthält einen `Spinner`, der visuell anzeigt, dass ein Hintergrundvorgang im Gange ist. Sie können diesen Spinner mit mehreren Optionen anpassen, einschließlich seiner Größe, Geschwindigkeit, Richtung, Thema und Sichtbarkeit.

Hier ist ein Beispiel, wie Sie den Spinner innerhalb einer `Loading`-Komponente anpassen können:

<ComponentDemo 
path='/webforj/loadingspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/loading/LoadingSpinnerDemoView.java'
cssURL='/css/loadingstyles/loadingspinnerdemo.css'
height = '300px'
/>

## Anwendungsfälle {#use-cases}
- **Datenabruf**  
   Beim Abrufen von Daten von einem Server oder einer API überlagert die `Loading`-Komponente einen bestimmten Abschnitt der Benutzeroberfläche, wie z. B. eine Karte oder ein Formular, um die Benutzer darüber zu informieren, dass das System im Hintergrund arbeitet. Dies ist ideal, wenn Sie den Fortschritt nur in einem Teil des Bildschirms anzeigen möchten, ohne die gesamte Benutzeroberfläche zu blockieren.

- **Inhalte laden in Karten/Abschnitten**  
   Die `Loading`-Komponente kann auf bestimmte Bereiche einer Seite beschränkt werden, wie z. B. einzelne Karten oder Container. Dies ist nützlich, wenn Sie anzeigen möchten, dass ein bestimmter Abschnitt der Benutzeroberfläche noch geladen wird, während Benutzer mit anderen Teilen der Seite interagieren können.

- **Komplexe Formularübermittlungen**  
   Bei längeren Formularübermittlungen, bei denen die Validierung oder Verarbeitung Zeit in Anspruch nimmt, bietet die `Loading`-Komponente visuelles Feedback für die Benutzer und beruhigt sie, dass ihre Eingaben aktiv verarbeitet werden.

## Styling {#styling}

<TableBuilder name="Loading" />
