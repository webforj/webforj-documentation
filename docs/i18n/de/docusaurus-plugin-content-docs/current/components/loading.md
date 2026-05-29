---
title: Loading
sidebar_position: 65
_i18n_hash: c81b8d0ced3e4097693a186a05f18dbf
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

Die `Loading`-Komponente zeigt eine Überlagerung auf einer bestimmten Komponente oder einem Bereich an, um signalisiert, dass eine Operation im Gange ist und temporär die Interaktion blockiert. Sie eignet sich gut für Aufgaben wie Datenladen, Berechnungen oder Hintergrundprozesse. Für globale, app-weite Prozesse deckt die [`BusyIndicator`](../components/busyindicator)-Komponente die gesamte Benutzeroberfläche ab.

<!-- INTRO_END -->

## Grundlagen {#basics}

Die einfachste Möglichkeit, eine `Loading`-Komponente zu erstellen, besteht darin, sie ohne zusätzliche Einstellungen zu initialisieren. Standardmäßig zeigt dies einen einfachen Spinner über dem übergeordneten Inhalt an. Sie können jedoch auch eine Nachricht für mehr Kontext bereitstellen.

Hier ist ein Beispiel zum Erstellen einer `Loading`-Komponente mit einer Nachricht:

<ComponentDemo
path='/webforj/loadingdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java',
  'src/main/resources/static/css/loadingstyles/loadingdemo.css',
]}
height='300px'
/>

## Eingrenzung {#scoping}

Die `Loading`-Komponente in webforJ kann sich auf einen bestimmten übergeordneten Container, wie zum Beispiel ein `Div`, eingrenzen, sodass sie nur die Interaktion des Benutzers innerhalb dieses Elements blockiert. Standardmäßig ist die `Loading`-Komponente relativ zu ihrem Elternteil, was bedeutet, dass sie die übergeordnete Komponente und nicht die gesamte App überlagert.

Um die `Loading`-Komponente auf ihren Elternteil zu beschränken, fügen Sie einfach die `Loading`-Komponente zum übergeordneten Container hinzu. Wenn Sie sie beispielsweise zu einem `Div` hinzufügen, gilt die Ladeüberlagerung nur für dieses `Div`:

```java
Div parentDiv = new Div();  
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Loading blockiert nur die Interaktion innerhalb des parentDiv
```

## Hintergrund {#backdrop}

Die `Loading`-Komponente in webforJ ermöglicht es Ihnen, einen Hintergrund anzuzeigen, um die Benutzerinteraktion zu blockieren, während ein Prozess läuft. Standardmäßig aktiviert die Komponente den Hintergrund, aber Sie haben die Möglichkeit, ihn bei Bedarf auszuschalten.

Für die `Loading`-Komponente ist der Hintergrund standardmäßig sichtbar. Sie können ihn explizit aktivieren oder ausschalten, indem Sie die Methode `setBackdropVisible()` verwenden:

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // Deaktiviert den Hintergrund
loading.open();
```
:::info Hintergrund Aus
Selbst wenn Sie den Hintergrund ausschalten, blockiert die `Loading`-Komponente weiterhin die Benutzerinteraktion, um sicherzustellen, dass der zugrunde liegende Prozess ungestört abgeschlossen wird. Der Hintergrund steuert lediglich die visuelle Überlagerung, nicht das Verhalten der Interaktionsblockierung.
:::

## `Spinner` {#spinner}

Die `Loading`-Komponente in webforJ enthält einen `Spinner`, der visuell anzeigt, dass ein Hintergrundvorgang im Gange ist. Sie können diesen Spinner mit mehreren Optionen anpassen, einschließlich seiner Größe, Geschwindigkeit, Richtung, Thema und Sichtbarkeit.

Hier ist ein Beispiel, wie Sie den Spinner innerhalb einer `Loading`-Komponente anpassen können:

<ComponentDemo
path='/webforj/loadingspinnerdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingSpinnerDemoView.java',
  'src/main/resources/static/css/loadingstyles/loadingspinnerdemo.css',
]}
height='300px'
/>

## Anwendungsfälle {#use-cases}
- **Datenabruf**  
   Beim Abrufen von Daten von einem Server oder einer API überlagert die `Loading`-Komponente einen bestimmten Abschnitt der Benutzeroberfläche, wie eine Karte oder ein Formular, um die Benutzer zu informieren, dass das System im Hintergrund arbeitet. Dies ist ideal, wenn Sie den Fortschritt nur in einem Teil des Bildschirms anzeigen möchten, ohne die gesamte Benutzeroberfläche zu blockieren.

- **Inhalte laden in Karten/Abschnitten**  
   Die `Loading`-Komponente kann auf bestimmte Bereiche einer Seite, wie einzelne Karten oder Container, eingrenzt werden. Dies ist nützlich, wenn Sie anzeigen möchten, dass ein bestimmter Abschnitt der Benutzeroberfläche noch geladen wird, während die Benutzer mit anderen Teilen der Seite interagieren können.

- **Komplexe Formularübermittlungen**  
   Bei längeren Formularübermittlungen, bei denen die Validierung oder Verarbeitung Zeit in Anspruch nimmt, bietet die `Loading`-Komponente visuelles Feedback an die Benutzer und versichert ihnen, dass ihre Eingabe aktiv verarbeitet wird.

## Styling {#styling}

<TableBuilder name="Loading" />
