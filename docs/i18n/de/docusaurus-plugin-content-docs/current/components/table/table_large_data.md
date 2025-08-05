---
sidebar_position: 25
title: Large Data Sets
slug: data
_i18n_hash: 83619419eb87c85aa5e309ee153af7fb
---
## Virtuelles Scrollen {#virtual-scrolling}

Die `Table`-Komponente wurde entwickelt, um große Datensätze effizient zu verarbeiten, indem virtuelles Scrollen genutzt wird, eine Technik, die in Webanwendungen eingesetzt wird, um das Rendering und die Leistung großer Listen oder Tabellen zu optimieren, indem nur die sichtbaren Elemente auf dem Bildschirm gerendert werden.

### Erster Render {#initial-render}

Virtuelles Scrollen ist ein Designmuster, bei dem zunächst nur eine kleine Teilmenge von Elementen gerendert wird, die im sichtbaren Bereich des scrollbaren Containers passt. Dies minimiert die Anzahl der erstellten DOM-Elemente und beschleunigt den anfänglichen Renderprozess.

### Dynamisches Laden {#dynamic-loading}
Während der Benutzer nach unten oder oben scrollt, werden neue Elemente dynamisch in die Ansicht geladen. Diese Elemente werden normalerweise basierend auf der aktuellen Scrollposition aus der Datenquelle abgerufen.

### Element-Recycling {#item-recycling}
Anstatt ein neues DOM-Element für jedes Element zu erstellen, verwendet das virtuelle Scrollen häufig vorhandene DOM-Elemente wieder. Wenn ein Element aus dem sichtbaren Bereich verschwindet, wird sein DOM-Element recycelt und für ein neues Element verwendet, das in den sichtbaren Bereich eintritt. Dieser Recyclingprozess hilft, den Speicherverbrauch zu reduzieren und die Leistung zu verbessern.

### Leistungsverbesserungen: {#performance-benefits}

Der Hauptvorteil von virtuellem Scrollen ist die verbesserte Leistung, insbesondere beim Umgang mit einer großen Anzahl von Elementen. Es reduziert die Menge an DOM-Manipulation und verbessert die allgemeine Reaktionsfähigkeit der Benutzeroberfläche.

Die folgende `Table` zeigt alle olympischen Gewinner - ein großer Datensatz, der stark von der Funktionalität des virtuellen Scrollens der Tabelle profitiert:

<ComponentDemo
path='/webforj/tableolympicwinners?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableOlympicWinnersView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Overscan {#overscan}

Die Einstellung der `Overscan`-Eigenschaft der Tabelle bestimmt, wie viele Zeilen außerhalb des sichtbaren Bereichs gerendert werden sollen. Diese Einstellung kann mit der Methode `setOverscan(double value)` konfiguriert werden.

Ein höherer Overscan-Wert kann die Häufigkeit des Renderns beim Scrollen reduzieren, aber auf Kosten, dass mehr Zeilen gerendert werden, als zu einem gegebenen Zeitpunkt sichtbar sind. Dies kann ein Kompromiss zwischen Rendering-Leistung und Scroll-Geschmeidigkeit sein.
