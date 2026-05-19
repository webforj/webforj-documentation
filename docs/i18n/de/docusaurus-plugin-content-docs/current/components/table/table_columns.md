---
sidebar_position: 5
title: Columns
slug: columns
_i18n_hash: fbae9063370715e9f6dc2cb490a27511
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

Die `Table`-Klasse verwendet Spalteninstanzen, um zu definieren und anzupassen, wie Daten angezeigt werden. Spalten steuern, welche Daten angezeigt werden, wie sie aussehen und wie Benutzer mit ihnen interagieren können. Diese Seite behandelt die Spaltenidentität, Präsentation, Größenanpassung, Benutzerinteraktionen und verwandte Ereignisse.

## Spaltenidentität {#column-identity}

Die Identität einer Spalte definiert, wie sie in der `Table` erkannt wird. Dazu gehören ihr Label, der Wert, den sie bereitstellt, und ob sie sichtbar oder navigierbar ist.

### Label {#label}

Das Label einer Spalte ist ihr öffentlich sichtbarer Bezeichner, der hilft, die angezeigten Daten zu klären.  

Verwenden Sie `setLabel()`, um das Label festzulegen oder zu ändern.

:::tip
Standardmäßig ist das Label dasselbe wie die Spalten-ID.
:::

```java
table.addColumn("Produkt-ID", Product::getProductId).setLabel("ID");
```

### Wertanbieter {#value-providers}

Ein Wertanbieter ist eine Funktion, die dafür verantwortlich ist, Rohdaten aus dem zugrunde liegenden Datensatz in ein Format zu übersetzen, das für die Anzeige innerhalb einer bestimmten Spalte geeignet ist. Die von Ihnen definierte Funktion nimmt eine Instanz des Zeilendatentyps (T) und gibt den Wert zurück, der in der zugehörigen Spalte für diese bestimmte Zeile angezeigt werden soll.

Um einen Wertanbieter für eine Spalte festzulegen, verwenden Sie eine der `addColumn()`-Methoden aus der `Table`-Komponente.

Im folgenden Snippet wird eine Spalte versuchen, Daten aus einem JSON-Objekt abzurufen und rendert diese nur, wenn die Daten nicht null sind.

```java
List<String> columnsList = List.of("athlete", "age", "country", "year", "sport", "gold", "silver", "bronze", "total");
for (String column : columnsList) {
  table.addColumn(column, (JsonObject person) -> {
    JsonElement element = person.get(column);
    if (!element.isJsonNull()) {
      return element.getAsString();
    }
    return "";
  });
}
```

### Sichtbarkeit {#visibility}

Es ist möglich, die Sichtbarkeit einer Spalte festzulegen, um zu bestimmen, ob sie innerhalb der `Table` angezeigt wird oder nicht. Dies kann nützlich sein, um unter anderem zu entscheiden, ob sensible Informationen angezeigt werden sollen. 

```java
table.addColumn("Kreditkarte", Customer::getCreditCardNumber).setHidden(true);
```

### Navigierbar {#navigable}

Das Attribut "navigierbar" bestimmt, ob Benutzer während der Navigation mit einer Spalte interagieren können. Wenn `setSuppressNavigable()` auf true gesetzt wird, wird die Benutzerinteraktion mit der Spalte eingeschränkt, was eine schreibgeschützte Erfahrung bietet.

```java
table.addColumn("Schreibgeschützte Spalte", Product::getDescription).setSuppressNavigable(true);
```

## Layout und Formatierung {#layout-and-formatting}

Nachdem die Identität einer Spalte festgelegt wurde, besteht der nächste Schritt darin, zu steuern, wie ihr Inhalt den Benutzern angezeigt wird. Layout-Optionen wie Ausrichtung und Anheften bestimmen, wo Daten angezeigt werden und wie sie sichtbar bleiben, während Sie mit einer `Table` arbeiten.

### Ausrichtung {#alignment}

Die Festlegung der Ausrichtung einer Spalte ermöglicht es Ihnen, organisierte Tabellen zu erstellen, die den Benutzern helfen können, die verschiedenen Abschnitte in der `Table` zu identifizieren.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumnalignment'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnAlignmentView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

Die `Table`-Komponente unterstützt drei Ausrichtungsoptionen:

- `Column.Alignment.LEFT`: Eignet sich für textuelle oder beschreibende Daten, bei denen ein linksseitiger Fluss intuitiv ist. Nützlich, um den Anfang des Inhalts zu betonen.
- `Column.Alignment.CENTER`: Zentral ausgerichtete Spalten sind ideal für kürzere Werte, wie einen Schlüssel, Status oder alles, was eine ausgewogene Präsentation hat.
- `Column.Alignment.RIGHT`: Ziehen Sie in Betracht, eine rechts ausgerichtete Spalte für numerische Werte zu verwenden, die hilfreich sind, um schnell durchzugehen, wie Daten, Beträge und Prozentsätze.

Im vorhergehenden Beispiel wurde die letzte Spalte für `Kosten` nach rechts ausgerichtet, um einen auffälligeren visuellen Unterschied zu schaffen.

### Anheften {#pinning}

Das Anheften von Spalten ist eine Funktion, die es Benutzern ermöglicht, eine Spalte an einer bestimmten Seite der `Table` zu befestigen oder "anzupinnen". Dies ist nützlich, wenn bestimmte Spalten, wie Identifikatoren oder essentielle Informationen, sichtbar bleiben sollen, während Sie horizontal durch eine Tabelle scrollen.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumnpinning'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

Es gibt drei verfügbare Richtungen für das Anheften einer Spalte:

- `PinDirection.LEFT`: Befestigt die Spalte an der linken Seite.
- `PinDirection.RIGHT`: Befestigt die Spalte an der rechten Seite.
- `PinDirection.AUTO`: Die Spalte erscheint basierend auf der Einfügeordnung.

Das Anheften kann programmatisch festgelegt werden, sodass Sie die Pin-Richtung basierend auf Benutzerinteraktionen oder der Logik der Anwendung ändern können.

## Spaltengrößenanpassung <DocChip chip='since' label='25.03' /> {#column-sizing} 

### Feste Breite {#fixed-width}

Legen Sie eine exakte Breite für eine Spalte mit der Methode `setWidth()` fest und geben Sie die gewünschte Breite in Pixeln an:

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

Die Breite-Eigenschaft definiert die gewünschte Anfangsbreite für die Spalte. Wie diese Breite verwendet wird, hängt von anderen Eigenschaften und dem Spaltentyp ab:

- **Reguläre Spalten**: Wenn nur die Breite festgelegt wird, wird die Spalte mit der angegebenen Breite gerendert, kann jedoch proportional schrumpfen, wenn der Container zu klein ist. Die ursprüngliche Breite dient als gewünschte Breite, aber ohne explizite Mindestbeschränkungen kann die Spalte kleiner als die festgelegte Breite dargestellt werden.
- [**Angenommene Spalten**](#pinning): Halten immer ihre exakte Breite und nehmen niemals am responsiven Schrumpfen teil.
- [**Flex-Spalten**](#flex-sizing): Das Festlegen der Breite ist mit Flex inkompatibel. Verwenden Sie entweder Breite (fest) oder Flex (proportional), nicht beides.

Wenn nicht angegeben, verwendet die Spalte ihre geschätzte Breite basierend auf der Inhaltsanalyse der ersten paar Zeilen.

```java
// Aktuelle Breite abrufen
float currentWidth = column.getWidth();
```

### Minimale Breite {#minimum-width}

Die Methode `setMinWidth()` ermöglicht es Ihnen, die minimale Breite einer Spalte festzulegen. Wenn die minimale Breite nicht bereitgestellt wird, berechnet die `Table` die minimale Breite basierend auf dem Spalteninhalt.

```java
table.addColumn("Preis", Product::getPrice).setMinWidth(100f);
```

Der angegebene Wert stellt die minimale Breite in Pixeln dar.

Die Mindestbreite-Eigenschaft steuert die kleinste Breite, die eine Spalte haben kann:

- **Reguläre Spalten**: Wird nur die Mindestbreite festgelegt, verwendet die Spalte die Mindestbreite sowohl als gewünschte als auch als Mindestbreite. Bei Breite + Mindestbreite kann die Spalte von der Breite auf die Mindestbreite schrumpfen, aber nicht weiter.
- [**Angenommene Spalten**](#pinning): Wenn nur die Mindestbreite festgelegt ist (keine Breite), wird sie zur festen Breite.
- [**Flex-Spalten**](#flex-sizing): Verhindern, dass die Spalte unterhalb dieser Breite schrumpft, selbst wenn der Platz im Container begrenzt ist.

```java
// Aktuelle Mindestbreite abrufen
float minWidth = column.getMinWidth();
```

### Maximale Breite {#maximum-width}

Die Methode `setMaxWidth()` beschränkt, wie breit eine Spalte wachsen kann, und verhindert, dass Spalten mit langen Inhalten zu breit werden und die Lesbarkeit beeinträchtigen:

```java
table.addColumn("Beschreibung", Product::getDescription)
  .setMinWidth(100f)
  .setMaxWidth(300f);
```

Die `maxWidth`-Eigenschaft begrenzt das Wachstum der Spalte für alle Spaltentypen und wird nie überschritten, unabhängig von Inhalt, Containergröße oder Flex-Einstellungen.

```java
// Aktuelle maximale Breite abrufen
float maxWidth = column.getMaxWidth();
```

### Flex-Größenanpassung {#flex-sizing}

Die Methode `setFlex()` ermöglicht eine proportionale Spaltengrößenanpassung, sodass die Spalten den verfügbaren Raum nach der Zuordnung von festen Breiten-Spalten teilen:

```java
// Die Titelsäule erhält doppelt so viel Platz wie die Künstler-Spalte
table.addColumn("Titel", Product::getTitle).setFlex(2f);
table.addColumn("Künstler", Product::getArtist).setFlex(1f);
```

Wichtige Flex-Verhalten:

- **Flex-Wert**: Bestimmt den Anteil des verfügbaren Raums. Eine Spalte mit flex=2 erhält doppelt so viel Platz wie eine Spalte mit flex=1.
- **Inkompatibel mit Breite**: Kann nicht zusammen mit der Breiten-Eigenschaft verwendet werden. Wenn flex größer als null ist, hat es Vorrang vor der Breiten-Einstellung.
- **Respektiert Beschränkungen**: Funktioniert mit Mindestbreite-/Höchstbreitenbeschränkungen. Ohne Mindestbreite können Flex-Spalten auf 0 schrumpfen.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumnflexsizing'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnFlexSizingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='550px'
/>
<!-- vale on -->

:::info Breite vs Flex
Die Eigenschaften Breite und Flex sind gegenseitig ausschließend. Das Festlegen einer bewirkt automatisch das Löschen der anderen. Verwenden Sie Breite für präzise Kontrolle oder Flex für responsive Verhaltensweisen.
:::

### Automatische Größenanpassung {#automatic-sizing}

Über manuelle Breiten- und Flex-Einstellungen hinaus können Spalten auch automatisch dimensioniert werden. Die automatische Größenanpassung lässt die `Table` die optimalen Breiten entweder durch Analyse des Inhalts oder durch proportionale Verteilung des Raums bestimmen.

#### Inhaltsbasierte automatische Größenanpassung {#content-based-auto-sizing}

Spalten basierend auf ihrem Inhalt automatisch dimensionieren. Die `Table` analysiert die Daten in jeder Spalte und berechnet die optimale Breite, um den Inhalt ohne Truncation anzuzeigen.

```java
// Alle Spalten automatisch anpassen, um den Inhalt zu passen
table.setColumnsToAutoSize().thenAccept(c -> {
  // Größenanpassung abgeschlossen - Spalten passen jetzt zu ihrem Inhalt
});

// Bestimmte Spalte automatisch anpassen
table.setColumnToAutoSize("beschreibung");
```

#### Proportionaler Auto-Fit {#proportional-auto-fit}

Verteilen Sie alle Spalten proportional über die verfügbare `Table`-Breite. Diese Operation setzt jede Spalte auf flex=1, sodass sie den gesamten `Table`-Breite gleichmäßig teilen, unabhängig von ihrer Inhaltslänge. Die Spalten werden erweitert oder verkleinert, um die genauen Abmessungen der `Table` ohne verbleibenden Platz auszufüllen.

```java
// Spalten auf die Tabellenbreite anpassen (entspricht dem Setzen von flex=1 für alle)
table.setColumnsToAutoFit().thenAccept(ignored -> {
  // Alle Spalten teilen sich jetzt den Platz gleichmäßig
});
```

:::info Asynchrone Operationen
Auto-Größenanpassungsmethoden geben `PendingResult<Void>` zurück, da sie clientseitige Berechnungen erfordern. Verwenden Sie `thenAccept()`, um Code nach Abschluss der Größenanpassung auszuführen. Wenn Sie nicht auf den Abschluss warten müssen, können Sie die Methoden ohne `thenAccept()` aufrufen.
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumnautosizing'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnAutoSizingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='550px'
/>
<!-- vale on -->

## Benutzerinteraktionen <DocChip chip='since' label='25.03' /> {#user-interactions}

### Spaltenvergrößerung {#column-resizing}

Die Spaltenvergrößerung gibt Benutzern die Kontrolle darüber, wie viel Platz jede Spalte einnimmt, indem sie die Spaltenränder ziehen.

Sie können das Vergrößerungsverhalten für einzelne Spalten steuern, wenn Sie Ihre Tabelle erstellen:

```java
// Benutzervergrößerung für diese Spalte aktivieren
table.addColumn("Titel", Product::getTitle).setResizable(true);

// Vergrößerung deaktivieren
table.addColumn("ID", Product::getId).setResizable(false);

// Aktuellen Status prüfen
boolean canResize = column.isResizable();
```

Für Tabellen, bei denen Sie ein konsistentes Verhalten über mehrere Spalten hinweg wünschen, verwenden Sie die Methoden zur Massenkonfiguration:

```java
// Alle vorhandenen Spalten vergrößerbar machen
table.setColumnsToResizable(true);

// Alle vorhandenen Spalten vom Vergrößern abhält
table.setColumnsToResizable(false);
```

### Spaltenneuanordnung {#column-reordering}

Die Spaltenneuanordnung ermöglicht es Benutzern, Spalten per Drag & Drop in ihre bevorzugte Reihenfolge zu verschieben, wodurch das Layout der `Table` für ihren Arbeitsablauf personalisiert wird.

Konfigurieren Sie die Berechtigungen für die Spaltenbewegung beim Einrichten Ihrer Tabelle:

```java
// Ermöglichen Sie Benutzern, diese Spalte zu verschieben
table.addColumn("Titel", Product::getTitle).setMovable(true);

// Spaltenbewegung verhindern (nützlich für ID- oder Aktionsspalten)
table.addColumn("ID", Product::getId).setMovable(false);

// Aktuellen Status prüfen
boolean canMove = column.isMovable();
```

Wenden Sie die Bewegungseinstellungen gleichzeitig auf mehrere Spalten an:

```java
// Neuanordnung für alle vorhandenen Spalten aktivieren
table.setColumnsToMovable(true);

// Neuanordnung für alle vorhandenen Spalten deaktivieren  
table.setColumnsToMovable(false);
```

:::note Massenoperationen
Die Methoden `setColumnsToResizable()` und `setColumnsToMovable()` betreffen nur vorhandene Spalten zum Zeitpunkt des Aufrufs. Sie setzen keine Standards für zukünftige Spalten.
:::

### Programmatische Spaltenbewegung {#programmatic-column-movement} 

Neben Drag-and-Drop können Sie Spalten auch programmgesteuert nach Index oder ID repositionieren. Bedenken Sie, dass der Index nur auf sichtbare Spalten basiert; alle versteckten Spalten werden bei der Berechnung der Positionen ignoriert.

```java
// Spalte an erste Position verschieben
table.moveColumn("titel", 0);

// Spalte an letzte Position verschieben
table.moveColumn(titleColumn, table.getColumns().size() - 1);

// Asynchrone Bewegung mit Rückruf
table.moveColumn("beschreibung", 2).thenAccept(c -> {
  // Spalte erfolgreich verschoben
});
```

## Ereignisbehandlung {#event-handling}

Die `Table`-Komponente gibt Ereignisse aus, wenn Benutzer mit Spalten interagieren, sodass Sie auf Layoutänderungen reagieren und Benutzereinstellungen speichern können.

Unterstützte Ereignisse:

- `TableColumnResizeEvent`: Wird ausgelöst, wenn ein Benutzer eine Spalte durch Ziehen ihres Randes vergrößert.
- `TableColumnMoveEvent`: Wird ausgelöst, wenn ein Benutzer eine Spalte durch Ziehen ihrer Kopfzeile neu anordnet.

Sie können Listener an die `Table` anhängen, um zu reagieren, wenn Benutzer das Tabellenlayout ändern.

```java
Table<Product> table = new Table<>();

table.onColumnResize(event -> {
  // Ereignis für die Spaltenvergrößerung behandeln
  // Zugriff: event.getColumn(), event.getOldWidth(), event.getNewWidth()
});

table.onColumnMove(event -> {
  // Ereignis für die Spaltenbewegung behandeln  
  // Zugriff: event.getColumn(), event.getOldIndex(), event.getNewIndex()
});
```
