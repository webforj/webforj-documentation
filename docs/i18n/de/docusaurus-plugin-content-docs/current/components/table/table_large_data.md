---
sidebar_position: 25
title: Große Datensätze
slug: data
description: >-
  Handle large Table datasets efficiently with virtual scrolling, dynamic row
  loading, DOM recycling, and overscan tuning.
_i18n_hash: 63e45952d3d3f88d3aa435435c0905e6
---
## Virtuelles Scrollen {#virtual-scrolling}

Die `Table`-Komponente ist darauf ausgelegt, große Datensätze effizient zu verarbeiten. Dazu verwendet sie virtuelles Scrollen – eine Technik, mit der Webanwendungen das Rendern und die Leistung großer Listen oder Tabellen optimieren, indem nur die Elemente gerendert werden, die gerade auf dem Bildschirm sichtbar sind.

### Initiales Rendern {#initial-render}

Virtuelles Scrollen ist ein Entwurfsmuster, bei dem zunächst nur eine kleine Teilmenge der Elemente gerendert wird, die in den sichtbaren Bereich des scrollbaren Containers passt. Dadurch wird die Anzahl der erstellten DOM-Elemente minimiert und der initiale Rendering-Prozess beschleunigt.

### Dynamisches Laden {#dynamic-loading}
Wenn der Benutzer nach unten oder oben scrollt, werden neue Elemente dynamisch in die Ansicht geladen. Diese Elemente werden normalerweise anhand der aktuellen Scrollposition aus der Datenquelle abgerufen.

### Wiederverwendung von Elementen {#item-recycling}
Anstatt für jedes Element ein neues DOM-Element zu erstellen, werden beim virtuellen Scrollen häufig vorhandene DOM-Elemente wiederverwendet. Sobald ein Element den sichtbaren Bereich verlässt, wird sein DOM-Element wiederverwendet und für ein neues Element umfunktioniert, das in den sichtbaren Bereich gelangt. Dieser Wiederverwendungsprozess trägt dazu bei, den Speicherverbrauch zu senken und die Leistung zu verbessern.

### Leistungsvorteile: {#performance-benefits}

Der Hauptvorteil des virtuellen Scrollens ist die bessere Leistung, insbesondere bei einer großen Anzahl von Elementen. Es reduziert die Anzahl der DOM-Manipulationen und verbessert die allgemeine Reaktionsfähigkeit der Benutzeroberfläche.

Die folgende `Table` zeigt alle olympischen Sieger – ein großer Datensatz, der erheblich von der Funktion für virtuelles Scrollen der Tabelle profitiert:

<ComponentDemo
path='/webforj/tableolympicwinners'
files={['src/main/java/com/webforj/samples/views/table/TableOlympicWinnersView.java']}
height='600px'
/>

## Overscan {#overscan}

Mit der Eigenschaft `Overscan` der Tabelle wird festgelegt, wie viele Zeilen außerhalb des sichtbaren Bereichs gerendert werden. Diese Einstellung lässt sich mit der Methode `setOverscan(double value)` konfigurieren.

Ein höherer Overscan-Wert kann die Häufigkeit des Renderns beim Scrollen verringern, allerdings werden dadurch mehr Zeilen gerendert, als jeweils sichtbar sind. Dies kann einen Kompromiss zwischen Rendering-Leistung und flüssigem Scrollen bedeuten.
