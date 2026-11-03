---
title: BusyIndicator
sidebar_position: 10
description: >-
  Block the entire interface during long-running operations using the
  BusyIndicator overlay with a customizable spinner, message, and backdrop.
_i18n_hash: a6ab827c0422e8f10369500acc310007
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

Der `BusyIndicator` ist ein Vollbild-Overlay, das einen laufenden Prozess signalisiert und die Benutzerinteraktion blockiert, bis dieser abgeschlossen ist. Er verdeckt die gesamte Benutzeroberfläche während Vorgängen wie Initialisierung oder Datensynchronisierung. Während die [`Loading`](../components/loading) Komponente sich auf spezifische Bereiche innerhalb der Benutzeroberfläche konzentriert, wird der `BusyIndicator` global angewendet.

Der `BusyIndicator` wird als Spinner angezeigt, ohne dass eine Konfiguration erforderlich ist. Fügen Sie eine Nachricht hinzu, ändern Sie das Design des Spinners oder passen Sie die Sichtbarkeitseinstellungen an, wenn ein Prozess mehr Kontext benötigt.

<!-- INTRO_END -->

<ComponentDemo
path='/webforj/busydemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java']}
height='300px'
/>

## Hintergründe {#backdrops}

Die `BusyIndicator` Komponente in webforJ ermöglicht es Ihnen, einen Hintergrund anzuzeigen, um die Benutzerinteraktion während eines laufenden Prozesses zu blockieren. Standardmäßig aktiviert die Komponente den Hintergrund, aber Sie haben die Möglichkeit, ihn bei Bedarf zu deaktivieren.

Der `BusyIndicator` zeigt standardmäßig einen Hintergrund an. Sie können die Sichtbarkeit des Hintergrunds mit der Methode `setBackdropVisible()` steuern, wie unten gezeigt:

```java
BusyIndicator busyIndicator = App.getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Deaktiviert den Hintergrund
busyIndicator.open();
```
:::info Hintergrund Deaktivieren
Selbst wenn Sie den Hintergrund deaktivieren, blockiert die `BusyIndicator` Komponente weiterhin die Benutzerinteraktion, um sicherzustellen, dass der zugrunde liegende Prozess ungestört abgeschlossen wird. Der Hintergrund steuert lediglich die visuelle Überlagerung, nicht das Verhalten der Interaktionssperre.
:::

## `Spinner` {#spinner}

Die `BusyIndicator` Komponente in webforJ enthält einen `Spinner`, der visuell anzeigt, dass ein Hintergrundvorgang im Gange ist. Sie können diesen Spinner mit mehreren Optionen anpassen, einschließlich seiner Größe, Geschwindigkeit, Richtung, des Themas und der Sichtbarkeit.

Hier ist ein Beispiel, wie Sie den Spinner innerhalb einer `BusyIndicator` Komponente anpassen können:

<ComponentDemo
path='/webforj/busyspinnerdemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java']}
height='200px'
/>

## Anwendungsfälle {#use-cases}
- **Seitenweite Verarbeitung**
   Der `BusyIndicator` eignet sich gut für größere, seitenweite Operationen, wie wenn ein Benutzer eine Aufgabe initiiert, die die gesamte Seite beeinflusst, zum Beispiel beim Hochladen einer Datei oder beim Verarbeiten von Daten über mehrere Abschnitte. Er kann die Benutzer darüber informieren, dass die gesamte Anwendung arbeitet, und weitere Interaktionen bis zum Abschluss des Prozesses verhindern.

- **Kritische Systemoperationen**
   Bei der Durchführung von systemkritischen Aufgaben, wie dem Synchronisieren von Daten, dem Anwenden systemweiter Updates oder dem Verarbeiten sensibler Informationen, gibt der `BusyIndicator` ein klares visuelles Feedback, dass eine bedeutende Operation im Gange ist, und ermöglicht es dem Benutzer, zu warten, bis sie abgeschlossen ist.

- **Asynchrone Datenladevorgänge**
   In Szenarien, in denen asynchrone Datenverarbeitung beteiligt ist, wie beim Aufrufen mehrerer APIs oder beim Warten auf komplexe Berechnungen, zeigt die `BusyIndicator` Komponente aktiv an, dass das System beschäftigt ist, und fordert die Benutzer auf, bevor sie weitere Aktionen durchführen, zu warten.

## Gestaltung {#styling}

<TableBuilder name="BusyIndicator" />
