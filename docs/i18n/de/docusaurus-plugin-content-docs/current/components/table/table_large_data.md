---
sidebar_position: 25
title: Large Data Sets
slug: data
_i18n_hash: a8c510d518375e324ae1f1f0c95b5004
---
## Virtuelles Scrollen {#virtual-scrolling}

Die `Table`-Komponente ist entwickelt worden, um große Datensätze effizient zu verarbeiten, indem virtuelles Scrollen genutzt wird, eine Technik, die in Webanwendungen verwendet wird, um das Rendering und die Leistung großer Listen oder Tabellen zu optimieren, indem nur die sichtbaren Elemente auf dem Bildschirm gerendert werden.

### Erstes Rendering {#initial-render}

Virtuelles Scrollen ist ein Gestaltungsprinzip, bei dem zunächst nur eine kleine Teilmenge von Elementen, die in den sichtbaren Bereich des scrollbaren Containers passen, gerendert wird. Dies minimiert die Anzahl der erzeugten DOM-Elemente und beschleunigt den anfänglichen Renderprozess.

### Dynamisches Laden {#dynamic-loading}
Während der Benutzer nach unten oder oben scrollt, werden neue Elemente dynamisch in die Ansicht geladen. Diese Elemente werden typischerweise anhand der aktuellen Scrollposition aus der Datenquelle abgerufen.

### Elementwiederverwertung {#item-recycling}
Anstatt für jedes Element ein neues DOM-Element zu erstellen, verwendet das virtuelle Scrollen oft vorhandene DOM-Elemente wieder. Wenn ein Element aus dem sichtbaren Bereich verschwindet, wird sein DOM-Element recycelt und für ein neues Element, das in den sichtbaren Bereich eintritt, wiederverwendet. Dieser Recyclingprozess hilft, den Speicherverbrauch zu reduzieren und die Leistung zu verbessern.

### Leistungs Vorteile: {#performance-benefits}

Der Hauptvorteil des virtuellen Scrollens ist die verbesserte Leistung, insbesondere beim Umgang mit einer großen Anzahl von Elementen. Es reduziert die Menge an DOM-Manipulation und verbessert die Gesamtreaktionsfähigkeit der Benutzeroberfläche.

Die folgende `Table` zeigt alle olympischen Gewinner - ein großer Datensatz, der erheblich von der virtuellen Scrollfunktionalität der Tabelle profitiert:

<ComponentDemo
path='/webforj/tableolympicwinners?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableOlympicWinnersView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Überwachung {#overscan}

Das Setzen der `Overscan`-Eigenschaft der Tabelle bestimmt, wie viele Zeilen außerhalb des sichtbaren Bereichs gerendert werden sollen. Diese Einstellung kann mit der Methode `setOverscan(double value)` konfiguriert werden.

Ein höherer Overscan-Wert kann die Häufigkeit des Renderns beim Scrollen verringern, jedoch auf Kosten des Renderns von mehr Zeilen, als zu jedem Zeitpunkt sichtbar sind. Dies kann ein Kompromiss zwischen Renderleistungsfähigkeit und Scroll-Smoothness sein.
