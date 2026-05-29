---
sidebar_position: 25
title: Large Data Sets
slug: data
_i18n_hash: 9431d33c6fea2dd9d4ff4b165877e7d5
---
## Virtuelles Scrollen {#virtual-scrolling}

Die `Table`-Komponente ist so konzipiert, dass sie große Datensätze effizient verwalten kann, indem sie virtuelles Scrollen verwendet, eine Technik, die in Webanwendungen eingesetzt wird, um das Rendern und die Leistung großer Listen oder Tabellen zu optimieren, indem nur die sichtbaren Elemente auf dem Bildschirm gerendert werden.

### Erstes Rendering {#initial-render}

Virtuelles Scrollen ist ein Entwurfsmuster, bei dem zunächst nur eine kleine Teilmenge von Elementen gerendert wird, die in den sichtbaren Bereich des scrollbaren Containers passen. Dies minimiert die Anzahl der erstellten DOM-Elemente und beschleunigt den initialen Renderprozess.

### Dynamisches Laden {#dynamic-loading}

Während der Benutzer nach unten oder oben scrollt, werden neue Elemente dynamisch in die Ansicht geladen. Diese Elemente werden typischerweise von der Datenquelle basierend auf der aktuellen Scrollposition abgerufen.

### Element-Recycling {#item-recycling}

Anstatt für jedes Element ein neues DOM-Element zu erstellen, verwendet das virtuelle Scrollen häufig vorhandene DOM-Elemente wieder. Wenn ein Element aus dem sichtbaren Bereich herausbewegt wird, wird sein DOM-Element recycelnd und für ein neues Element, das in den sichtbaren Bereich eintritt, wiederverwendet. Dieser Recyclingprozess hilft, den Speicherverbrauch zu reduzieren und die Leistung zu verbessern.

### Leistungsbenefits: {#performance-benefits}

Der Hauptvorteil des virtuellen Scrollens ist die verbesserte Leistung, insbesondere beim Umgang mit einer großen Anzahl von Elementen. Es reduziert die Menge an DOM-Manipulationen und verbessert die allgemeine Reaktionsfähigkeit der Benutzeroberfläche.

Die folgende `Table` zeigt alle olympischen Gewinner - einen großen Datensatz, der stark von der Funktionalität des virtuellen Scrollens der Tabelle profitiert:

<ComponentDemo
path='/webforj/tableolympicwinners'
files={[
  'src/main/java/com/webforj/samples/views/table/TableOlympicWinnersView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Overscan {#overscan}

Die Einstellung der `Overscan`-Eigenschaft der Tabelle bestimmt, wie viele Zeilen außerhalb des sichtbaren Bereichs gerendert werden. Diese Einstellung kann mit der Methode `setOverscan(double value)` konfiguriert werden.

Ein höherer Overscan-Wert kann die Häufigkeit des Renderns beim Scrollen reduzieren, allerdings auf Kosten der Darstellung von mehr Zeilen, als zu einem bestimmten Zeitpunkt sichtbar sind. Dies kann ein Kompromiss zwischen Renderleistung und Scroll-Smoothness sein.
