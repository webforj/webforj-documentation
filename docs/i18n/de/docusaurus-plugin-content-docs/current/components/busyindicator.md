---
title: BusyIndicator
sidebar_position: 10
_i18n_hash: 456b6118cd6219f530c5292611ba46e0
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

Der `BusyIndicator` ist ein Vollbildüberlagerungselement, das einen laufenden Prozess signalisiert und die Benutzerinteraktion blockiert, bis dieser abgeschlossen ist. Es verdeckt die gesamte Schnittstelle während Vorgängen wie der Initialisierung oder Datensynchronisierung. Während die [`Loading`](../components/loading) Komponente sich auf spezifische Bereiche innerhalb der Schnittstelle konzentriert, wird der `BusyIndicator` global angewendet.

<!-- INTRO_END -->

## Grundlagen {#basics}

Der `BusyIndicator` in webforJ wird als einfacher Spinner angezeigt, der einfach zu verwenden ist, ohne Konfiguration. Sie können ihn jedoch anpassen, indem Sie eine Nachricht hinzufügen, das Thema des Spinners ändern oder die Sichtbarkeitseinstellungen anpassen. Dadurch können Sie mehr Kontext oder Stil bieten und gleichzeitig eine funktionale, sofort einsatzbereite Lösung beibehalten.

In diesem Beispiel verhindert der `BusyIndicator`, dass Benutzeraktionen in der gesamten Schnittstelle durchgeführt werden, bis der Vorgang abgeschlossen ist.

<ComponentDemo
path='/webforj/busydemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java']}
height='300px'
/>

## Hintergründe {#backdrops}

Die `BusyIndicator`-Komponente in webforJ ermöglicht es Ihnen, einen Hintergrund anzuzeigen, um die Benutzerinteraktion zu blockieren, während ein Prozess im Gange ist. Standardmäßig aktiviert die Komponente den Hintergrund, aber Sie haben die Möglichkeit, ihn bei Bedarf auszuschalten.

Der `BusyIndicator` zeigt standardmäßig einen Hintergrund an. Sie können die Sichtbarkeit des Hintergrunds mithilfe der `setBackdropVisible()`-Methode steuern, wie im Folgenden gezeigt:

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Deaktiviert den Hintergrund
busyIndicator.open();
```
:::info Hintergrund ausschalten
Selbst wenn Sie den Hintergrund ausschalten, blockiert die `BusyIndicator`-Komponente weiterhin die Benutzerinteraktion, um sicherzustellen, dass der zugrunde liegende Prozess ununterbrochen abgeschlossen wird. Der Hintergrund steuert einfach die visuelle Überlagerung, nicht das Verhalten der Interaktionsblockierung.
:::

## `Spinner` {#spinner}

Die `BusyIndicator`-Komponente in webforJ umfasst einen `Spinner`, der visuell anzeigt, dass ein Hintergrundprozess im Gange ist. Sie können diesen Spinner mit verschiedenen Optionen anpassen, darunter Größe, Geschwindigkeit, Richtung, Thema und Sichtbarkeit.

Hier ist ein Beispiel, wie Sie den Spinner innerhalb einer `BusyIndicator`-Komponente anpassen können:

<ComponentDemo
path='/webforj/busyspinnerdemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java']}
height='200px'
/>

## Anwendungsfälle {#use-cases}
- **Seitenweite Verarbeitung**  
   Der `BusyIndicator` eignet sich gut für größere, seitenweite Vorgänge, z. B. wenn ein Benutzer eine Aufgabe initiiert, die die gesamte Seite betrifft, wie das Hochladen einer Datei oder die Verarbeitung von Daten über mehrere Abschnitte hinweg. Er kann den Benutzern mitteilen, dass die gesamte Anwendung arbeitet, und weitere Interaktionen verhindern, bis der Prozess abgeschlossen ist.

- **Kritische Systemoperationen**  
   Bei der Durchführung von systemkritischen Aufgaben wie der Datensynchronisierung, der Anwendung systemweiter Updates oder der Verarbeitung sensibler Informationen gibt der `BusyIndicator` klares visuelles Feedback, dass ein großer Vorgang im Gange ist, und ermöglicht es dem Benutzer, zu warten, bis er abgeschlossen ist.

- **Asynchrone Datenladevorgänge**  
   In Szenarien, in denen asynchrone Datenverarbeitung beteiligt ist, z. B. beim Aufrufen mehrerer APIs oder beim Warten auf komplexe Berechnungen, zeigt die `BusyIndicator`-Komponente aktiv an, dass das System beschäftigt ist, und fordert die Benutzer auf, zu warten, bevor sie weitere Aktionen durchführen.

## Stilierung {#styling}

<TableBuilder name="BusyIndicator" />
