---
title: BusyIndicator
sidebar_position: 10
description: >-
  Block the entire interface during long-running operations using the
  BusyIndicator overlay with a customizable spinner, message, and backdrop.
_i18n_hash: 30ca15f8b8170f6d7da6a786ddafea7f
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

Der `BusyIndicator` ist ein Overlay über den gesamten Bildschirm, das einen laufenden Prozess signalisiert und die Benutzerinteraktion blockiert, bis dieser abgeschlossen ist. Es deckt die gesamte Benutzeroberfläche während Vorgängen wie der Initialisierung oder Datensynchronisation ab. Während die [`Loading`](../components/loading) Komponente sich auf spezifische Bereiche innerhalb der Benutzeroberfläche konzentriert, wirkt der `BusyIndicator` global.

<!-- INTRO_END -->

## Grundlagen {#basics}

Der `BusyIndicator` in webforJ wird als einfacher Spinner angezeigt, was ihn ohne Konfiguration leicht verwendbar macht. Sie können ihn jedoch anpassen, indem Sie eine Nachricht hinzufügen, das Design des Spinners ändern oder die Sichtbarkeitseinstellungen modifizieren. Dies ermöglicht es Ihnen, mehr Kontext oder Stil bereitzustellen und gleichzeitig eine funktionale, sofort einsatzbereite Lösung aufrechtzuerhalten.

In diesem Beispiel verhindert der `BusyIndicator`, dass Benutzeraktionen über die gesamte Benutzeroberfläche hinweg ausgeführt werden, bis der Vorgang abgeschlossen ist.

<ComponentDemo
path='/webforj/busydemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java']}
height='300px'
/>

## Hintergründe {#backdrops}

Die `BusyIndicator`-Komponente in webforJ ermöglicht es, einen Hintergrund anzuzeigen, um die Benutzerinteraktion zu blockieren, während ein Vorgang läuft. Standardmäßig aktiviert die Komponente den Hintergrund, aber Sie haben die Möglichkeit, ihn bei Bedarf auszuschalten.

Der `BusyIndicator` zeigt standardmäßig einen Hintergrund an. Sie können die Sichtbarkeit des Hintergrunds mit der Methode `setBackdropVisible()` steuern, wie unten gezeigt:

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Deaktiviert den Hintergrund
busyIndicator.open();
```
:::info Hintergrund Ausschalten
Selbst wenn Sie den Hintergrund ausschalten, blockiert die `BusyIndicator`-Komponente weiterhin die Benutzerinteraktion, um sicherzustellen, dass der zugrunde liegende Prozess ununterbrochen abgeschlossen wird. Der Hintergrund steuert lediglich die visuelle Überlagerung, nicht das Verhalten des Blockierens der Interaktion.
:::

## `Spinner` {#spinner}

Die `BusyIndicator`-Komponente in webforJ enthält einen `Spinner`, der visuell anzeigt, dass ein Hintergrundvorgang im Gange ist. Sie können diesen Spinner mit verschiedenen Optionen anpassen, einschließlich Größe, Geschwindigkeit, Richtung, Design und Sichtbarkeit.

Hier ist ein Beispiel, wie Sie den Spinner innerhalb einer `BusyIndicator`-Komponente anpassen können:

<ComponentDemo
path='/webforj/busyspinnerdemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java']}
height='200px'
/>

## Anwendungsfälle {#use-cases}
- **Seitenweite Verarbeitung**
   Der `BusyIndicator` eignet sich gut für umfangreiche, seitenweite Vorgänge, wie wenn ein Benutzer eine Aufgabe initiiert, die die gesamte Seite betrifft, wie das Hochladen einer Datei oder die Verarbeitung von Daten über mehrere Abschnitte. Er kann die Benutzer informieren, dass die gesamte Anwendung arbeitet, und verhindern, dass weitere Interaktionen erfolgen, bis der Prozess abgeschlossen ist.

- **Kritische Systemoperationen**
   Bei systemkritischen Aufgaben wie der Datensynchronisation, der Anwendung von systemweiten Updates oder der Verarbeitung sensibler Informationen bietet der `BusyIndicator` eine klare visuelle Rückmeldung, dass eine wichtige Operation im Gange ist, und ermöglicht es dem Benutzer, zu warten, bis sie abgeschlossen ist.

- **Asynchrone Datenladungen**
   In Szenarien, in denen asynchrone Datenverarbeitung beteiligt ist, wie bei der Abfrage mehrerer APIs oder dem Warten auf komplexe Berechnungen, zeigt die `BusyIndicator`-Komponente aktiv an, dass das System beschäftigt ist, und fordert die Benutzer auf, zu warten, bevor sie zusätzliche Aktionen durchführen.

## Styling {#styling}

<TableBuilder name="BusyIndicator" />
