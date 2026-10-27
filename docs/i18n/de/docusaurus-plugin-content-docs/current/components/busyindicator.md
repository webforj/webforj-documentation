---
title: BusyIndicator
sidebar_position: 10
description: >-
  Block the entire interface during long-running operations using the
  BusyIndicator overlay with a customizable spinner, message, and backdrop.
_i18n_hash: 663fb0d605695631bad3753aadf178e5
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

Der `BusyIndicator` ist ein Vollbild-Overlay, das einen laufenden Prozess signalisiert und die Benutzerinteraktion blockiert, bis dieser abgeschlossen ist. Es deckt die gesamte Benutzeroberfläche während Vorgängen wie der Initialisierung oder der Datensynchronisierung ab. Während die [`Loading`](../components/loading) Komponente sich auf spezifische Bereiche innerhalb der Benutzeroberfläche konzentriert, wird der `BusyIndicator` global angewendet.

Der `BusyIndicator` wird als Spinner angezeigt, ohne dass eine Konfiguration erforderlich ist. Fügen Sie eine Nachricht hinzu, ändern Sie das Thema des Spinners oder passen Sie die Sichtbarkeitseinstellungen an, wenn ein Prozess mehr Kontext benötigt.

<!-- INTRO_END -->

<ComponentDemo
path='/webforj/busydemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java']}
height='300px'
/>

## Hintergründe {#backdrops}

Die `BusyIndicator` Komponente in webforJ ermöglicht es, einen Hintergrund anzuzeigen, um die Benutzerinteraktion zu blockieren, während ein Prozess im Gange ist. Standardmäßig aktiviert die Komponente den Hintergrund, aber Sie haben die Möglichkeit, ihn bei Bedarf auszuschalten.

Der `BusyIndicator` zeigt standardmäßig einen Hintergrund an. Sie können die Sichtbarkeit des Hintergrunds mit der Methode `setBackdropVisible()` steuern, wie im Folgenden gezeigt:

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Deaktiviert den Hintergrund
busyIndicator.open();
```
:::info Deaktivierung des Hintergrunds
Selbst wenn Sie den Hintergrund ausschalten, blockiert die `BusyIndicator` Komponente weiterhin die Benutzerinteraktion, um sicherzustellen, dass der zugrunde liegende Prozess ununterbrochen abgeschlossen wird. Der Hintergrund steuert lediglich die visuelle Überlagerung, nicht das Verhalten der Interaktionssperre.
:::

## `Spinner` {#spinner}

Die `BusyIndicator` Komponente in webforJ enthält einen `Spinner`, der visuell anzeigt, dass eine Hintergrundoperation im Gange ist. Sie können diesen Spinner mit mehreren Optionen anpassen, einschließlich seiner Größe, Geschwindigkeit, Richtung, Thema und Sichtbarkeit.

Hier ist ein Beispiel, wie Sie den Spinner innerhalb einer `BusyIndicator` Komponente anpassen können:

<ComponentDemo
path='/webforj/busyspinnerdemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java']}
height='200px'
/>

## Anwendungsfälle {#use-cases}
- **Seitenweite Verarbeitung**
   Der `BusyIndicator` eignet sich gut für größere, seitenweite Vorgänge, zum Beispiel wenn ein Benutzer eine Aufgabe initiiert, die die gesamte Seite betrifft, wie das Hochladen einer Datei oder die Verarbeitung von Daten über mehrere Bereiche hinweg. Er kann die Benutzer informieren, dass die gesamte Anwendung arbeitet, und weitere Interaktionen verhindern, bis der Prozess abgeschlossen ist.

- **Kritische Systemoperationen**
   Bei der Durchführung systemkritischer Aufgaben wie der Datensynchronisierung, der Anwendung systemweiter Updates oder der Verarbeitung sensibler Informationen bietet der `BusyIndicator` ein klares visuelles Feedback, das eine wichtige Operation im Gange signalisiert, wodurch der Benutzer warten kann, bis sie abgeschlossen ist.

- **Asynchrone Datenladungen**
   In Szenarien, in denen eine asynchrone Datenverarbeitung stattfindet, wie beim Aufrufen mehrerer APIs oder beim Warten auf komplexe Berechnungen, signalisiert die `BusyIndicator` Komponente aktiv, dass das System beschäftigt ist, und fordert die Benutzer auf, zu warten, bevor sie zusätzliche Aktionen durchführen.

## Styling {#styling}

<TableBuilder name="BusyIndicator" />
