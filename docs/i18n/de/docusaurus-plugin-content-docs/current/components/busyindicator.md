---
title: BusyIndicator
sidebar_position: 10
_i18n_hash: a61f487d0d763856c6055898a7284011
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

Der `BusyIndicator` bietet visuelle Hinweise, um sicherzustellen, dass Benutzer über laufende Prozesse informiert sind, wodurch sie daran gehindert werden, vorzeitig mit dem System zu interagieren. Er deckt typischerweise die gesamte Benutzeroberfläche der App für globale Vorgänge ab.

Während die Komponente [`Loading`](../components/loading) sich auf spezifische Bereiche oder Komponenten innerhalb der App konzentriert, behandelt der `BusyIndicator` globale, appweite Prozesse und blockiert die Interaktion über die gesamte Benutzeroberfläche. Dieser Unterschied im Umfang macht die Komponente [`Loading`](../components/loading) ideal für lokalere, komponentenspezifische Szenarien, wie das Laden von Daten in einem bestimmten Abschnitt einer Seite. Im Gegensatz dazu ist der `BusyIndicator` für systemweite Vorgänge geeignet, die die gesamte App betreffen, wie das Initialisieren der App oder das Durchführen einer größeren Daten-Synchronisation.

## Grundlagen {#basics}

Der `BusyIndicator` in webforJ wird als einfacher Spinner angezeigt, was die Verwendung ohne Konfiguration erleichtert. Sie können ihn jedoch anpassen, indem Sie eine Nachricht hinzufügen, das Thema des Spinners anpassen oder die Sichtbarkeitseinstellungen ändern. Dies ermöglicht es Ihnen, mehr Kontext oder Stil zu bieten, während eine funktionale, sofort einsatzbereite Lösung erhalten bleibt.

In diesem Beispiel verhindert der `BusyIndicator` jegliche Benutzeraktionen über die gesamte Benutzeroberfläche, bis der Vorgang abgeschlossen ist.

<ComponentDemo 
path='/webforj/busydemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java'
height = '300px'
/>

## Hintergründe {#backdrops}

Die `BusyIndicator`-Komponente in webforJ ermöglicht es Ihnen, einen Hintergrund anzuzeigen, um die Benutzerinteraktion während eines laufenden Prozesses zu blockieren. Standardmäßig aktiviert die Komponente den Hintergrund, aber Sie haben die Möglichkeit, ihn bei Bedarf auszuschalten.

Der `BusyIndicator` zeigt standardmäßig einen Hintergrund an. Sie können die Sichtbarkeit des Hintergrunds mit der Methode `setBackdropVisible()` steuern, wie unten gezeigt:

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Deaktiviert den Hintergrund
busyIndicator.open();
```
:::info Deaktivieren des Hintergrunds
Selbst wenn Sie den Hintergrund deaktivieren, blockiert die `BusyIndicator`-Komponente weiterhin die Benutzerinteraktion, um sicherzustellen, dass der zugrunde liegende Prozess ununterbrochen abgeschlossen wird. Der Hintergrund steuert lediglich die visuelle Überlagerung, nicht das Verhalten der Interaktionssperre.
:::

## `Spinner` {#spinner}

Die `BusyIndicator`-Komponente in webforJ umfasst einen `Spinner`, der visuell anzeigt, dass ein Hintergrundvorgang im Gange ist. Sie können diesen Spinner mit verschiedenen Optionen anpassen, einschließlich seiner Größe, Geschwindigkeit, Richtung, Thematik und Sichtbarkeit.

Hier ist ein Beispiel dafür, wie Sie den Spinner innerhalb einer `BusyIndicator`-Komponente anpassen können:

<ComponentDemo 
path='/webforj/busyspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java'
height = '200px'
/>

## Anwendungsfälle {#use-cases}
- **Seitenweite Verarbeitung**  
   Der `BusyIndicator` eignet sich gut für größere, seitenweite Vorgänge, wie wenn ein Benutzer eine Aufgabe initiiert, die die gesamte Seite betrifft, beispielsweise beim Hochladen einer Datei oder beim Verarbeiten von Daten über mehrere Abschnitte hinweg. Er kann Benutzer darüber informieren, dass die gesamte App arbeitet und eine weitere Interaktion bis zum Abschluss des Vorgangs verhindert.

- **Kritische Systemoperationen**  
   Bei der Durchführung systemkritischer Aufgaben, wie dem Synchronisieren von Daten, der Anwendung systemweiter Updates oder der Verarbeitung sensibler Informationen, bietet der `BusyIndicator` ein klares visuelles Feedback, dass ein größeres Verfahren im Gange ist, sodass der Benutzer warten kann, bis es abgeschlossen ist.

- **Asynchrone Datenläufe**  
   In Szenarien, in denen asynchrone Datenverarbeitung beteiligt ist, wie beim Aufrufen mehrerer APIs oder beim Warten auf komplexe Berechnungen, zeigt die `BusyIndicator`-Komponente aktiv an, dass das System beschäftigt ist, und fordert Benutzer auf, zu warten, bevor sie weitere Aktionen durchführen.

## Stilgestaltung {#styling}

<TableBuilder name="BusyIndicator" />
