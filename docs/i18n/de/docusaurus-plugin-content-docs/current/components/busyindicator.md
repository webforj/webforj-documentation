---
title: BusyIndicator
sidebar_position: 10
_i18n_hash: e8d5c8ba0e26f0cc8fb98a640069347f
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

Der `BusyIndicator` ist ein Vollbild-Overlay, das einen laufenden Prozess signalisiert und die Benutzerinteraktion blockiert, bis dieser abgeschlossen ist. Es deckt die gesamte Benutzeroberfläche während Vorgängen wie Initialisierung oder Datensynchronisierung ab. Während die [`Loading`](../components/loading)-Komponente sich auf spezifische Bereiche innerhalb der Benutzeroberfläche konzentriert, wird der `BusyIndicator` global angewendet.

<!-- INTRO_END -->

## Grundlagen {#basics}

Der `BusyIndicator` in webforJ wird als einfacher Spinner angezeigt, der ohne Konfiguration leicht zu verwenden ist. Sie können ihn jedoch anpassen, indem Sie eine Nachricht hinzufügen, das Design des Spinners ändern oder die Sichtbarkeitseinstellungen modifizieren. Dies ermöglicht es Ihnen, mehr Kontext oder Stil zu bieten und gleichzeitig eine funktionsfähige, standardmäßige Lösung beizubehalten.

In diesem Beispiel verhindert der `BusyIndicator`, dass Benutzeraktionen über die gesamte Benutzeroberfläche hinweg durchgeführt werden, bis der Vorgang abgeschlossen ist.

<ComponentDemo 
path='/webforj/busydemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java'
height = '300px'
/>

## Hintergründe {#backdrops}

Die `BusyIndicator`-Komponente in webforJ ermöglicht es Ihnen, einen Hintergrund anzuzeigen, um die Benutzerinteraktion während eines laufenden Prozesses zu blockieren. Standardmäßig aktiviert die Komponente den Hintergrund, aber Sie haben die Möglichkeit, ihn bei Bedarf auszuschalten.

Der `BusyIndicator` zeigt standardmäßig einen Hintergrund an. Sie können die Sichtbarkeit des Hintergrunds mithilfe der Methode `setBackdropVisible()` steuern, wie unten gezeigt:

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Deaktiviert den Hintergrund
busyIndicator.open();
```
:::info Hintergrund deaktivieren
Auch wenn Sie den Hintergrund deaktivieren, blockiert die `BusyIndicator`-Komponente weiterhin die Benutzerinteraktion, um sicherzustellen, dass der zugrunde liegende Prozess unterbrechungsfrei abgeschlossen wird. Der Hintergrund steuert lediglich die visuelle Überlagerung, nicht das Blockieren der Interaktion.
:::

## `Spinner` {#spinner}

Die `BusyIndicator`-Komponente in webforJ umfasst einen `Spinner`, der visuell anzeigt, dass ein Hintergrundvorgang im Gange ist. Sie können diesen Spinner mit mehreren Optionen anpassen, einschließlich seiner Größe, Geschwindigkeit, Richtung, Themen und Sichtbarkeit.

Hier ist ein Beispiel, wie Sie den Spinner innerhalb einer `BusyIndicator`-Komponente anpassen können:

<ComponentDemo 
path='/webforj/busyspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java'
height = '200px'
/>

## Anwendungsfälle {#use-cases}
- **Seitenweite Verarbeitung**  
   Der `BusyIndicator` eignet sich gut für größere, seitenweite Vorgänge, wie wenn ein Benutzer eine Aufgabe initiiert, die die gesamte Seite betrifft, wie das Hochladen einer Datei oder die Verarbeitung von Daten über mehrere Abschnitte. Er kann den Benutzern anzeigen, dass die gesamte App arbeitet, und weitere Interaktionen verhindern, bis der Prozess abgeschlossen ist.

- **Kritische Systemoperationen**  
   Bei der Durchführung systemkritischer Aufgaben wie der Datensynchronisierung, dem Anwenden systemweiter Updates oder der Verarbeitung sensibler Informationen gibt der `BusyIndicator` klares visuelles Feedback, dass ein größeres Vorgang im Gange ist, sodass der Benutzer warten kann, bis es abgeschlossen ist.

- **Asynchrone Datenladungen**  
   In Szenarien, in denen asynchrone Datenverarbeitung beteiligt ist, wie beim Aufrufen mehrerer APIs oder Warten auf komplexe Berechnungen, zeigt die `BusyIndicator`-Komponente aktiv an, dass das System beschäftigt ist und fordert die Benutzer auf, zu warten, bevor sie weitere Aktionen durchführen.

## Styling {#styling}

<TableBuilder name="BusyIndicator" />
