---
title: BusyIndicator
sidebar_position: 10
_i18n_hash: 0ecb07a1364b90d27e17484ade2199ae
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

Der `BusyIndicator` bietet visuelle Hinweise, um sicherzustellen, dass die Benutzer über laufende Prozesse informiert sind, und verhindert, dass sie vorzeitig mit dem System interagieren. Er überdeckt normalerweise die gesamte Benutzeroberfläche der App bei globalen Operationen.

Während die [`Loading`](../components/loading) Komponente sich auf bestimmte Bereiche oder Komponenten innerhalb der App konzentriert, behandelt der `BusyIndicator` globale, appweite Prozesse und blockiert die Interaktion über die gesamte Benutzeroberfläche. Dieser Unterschied im Umfang macht die [`Loading`](../components/loading) Komponente ideal für lokalere, komponentenspezifische Szenarien, wie das Laden von Daten in einem bestimmten Abschnitt einer Seite. Im Gegensatz dazu ist der `BusyIndicator` für systemweite Operationen geeignet, die die gesamte App betreffen, wie das Initialisieren der App oder das Durchführen einer umfangreichen Datensynchronisation.

## Grundlagen {#basics}

Der `BusyIndicator` in webforJ wird als einfacher Spinner angezeigt, was eine Verwendung ohne Konfiguration erleichtert. Sie können ihn jedoch anpassen, indem Sie eine Nachricht hinzufügen, das Thema des Spinners anpassen oder die Sichtbarkeitseinstellungen ändern. Dadurch können Sie mehr Kontext oder Stil bieten, während Sie eine funktionale, sofort einsatzbereite Lösung aufrechterhalten.

In diesem Beispiel verhindert der `BusyIndicator`, dass Benutzeraktionen über die gesamte Benutzeroberfläche ausgeführt werden, bis der Vorgang abgeschlossen ist.

<ComponentDemo 
path='/webforj/busydemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java'
height = '300px'
/>

## Hintergründe {#backdrops}

Die `BusyIndicator` Komponente in webforJ ermöglicht es Ihnen, einen Hintergrund anzuzeigen, um die Benutzerinteraktion zu blockieren, während ein Prozess im Gange ist. Standardmäßig aktiviert die Komponente den Hintergrund, aber Sie haben die Möglichkeit, ihn bei Bedarf auszuschalten.

Der `BusyIndicator` zeigt standardmäßig einen Hintergrund an. Sie können die Sichtbarkeit des Hintergrunds mithilfe der Methode `setBackdropVisible()` steuern, wie unten dargestellt:

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Deaktiviert den Hintergrund
busyIndicator.open();
```
:::info Hintergrund ausschalten
Selbst wenn Sie den Hintergrund ausschalten, blockiert die `BusyIndicator` Komponente weiterhin die Benutzerinteraktion, um sicherzustellen, dass der zugrunde liegende Prozess ununterbrochen abgeschlossen wird. Der Hintergrund steuert lediglich die visuelle Überlagerung, nicht das Verhalten der Interaktionsblockierung.
:::

## `Spinner` {#spinner}

Die `BusyIndicator` Komponente in webforJ beinhaltet einen `Spinner`, der visuell anzeigt, dass ein Hintergrundvorgang im Gange ist. Sie können diesen Spinner mit mehreren Optionen anpassen, einschließlich seiner Größe, Geschwindigkeit, Richtung, Thema und Sichtbarkeit.

Hier ist ein Beispiel dafür, wie Sie den Spinner innerhalb einer `BusyIndicator` Komponente anpassen können:

<ComponentDemo 
path='/webforj/busyspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java'
height = '200px'
/>

## Anwendungsfälle {#use-cases}
- **Seitenweite Verarbeitung**  
   Der `BusyIndicator` eignet sich gut für größere, seitenweite Operationen, wie wenn ein Benutzer eine Aufgabe initiiert, die die gesamte Seite betrifft, z. B. das Hochladen einer Datei oder das Verarbeiten von Daten über mehrere Abschnitte hinweg. Er kann den Benutzern mitteilen, dass die gesamte App arbeitet, und weitere Interaktionen verhindern, bis der Prozess abgeschlossen ist.

- **Kritische Systemoperationen**  
   Bei der Durchführung systemkritischer Aufgaben wie der Synchronisierung von Daten, der Anwendung systemweiter Updates oder der Verarbeitung sensibler Informationen gibt der `BusyIndicator` deutlich visuelles Feedback, dass eine größere Operation im Gange ist, und ermöglicht es dem Benutzer, abzuwarten, bis sie abgeschlossen ist.

- **Asynchrone Datenladungen**  
   In Szenarien, in denen asynchrone Datenverarbeitung beteiligt ist, wie beim Aufrufen mehrerer APIs oder Warten auf komplexe Berechnungen, zeigt die `BusyIndicator` Komponente aktiv an, dass das System beschäftigt ist, und fordert die Benutzer auf, zu warten, bevor sie weitere Aktionen ausführen.

## Styling {#styling}

<TableBuilder name="BusyIndicator" />
