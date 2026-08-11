---
sidebar_position: 5
title: Columns
slug: columns
description: >-
  Define Table columns with labels, value providers, visibility, navigability,
  sizing, and user-interaction events.
_i18n_hash: 5ca9d9959c258db42780e52dad8c463d
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

Die Klasse `Table` verwendet Spalteninstanzen, um zu definieren und anzupassen, wie Daten angezeigt werden. Spalten kontrollieren, welche Daten angezeigt werden, wie sie aussehen und wie Benutzer mit ihnen interagieren können. Diese Seite behandelt die Identität von Spalten, Präsentation, Größenanpassung, Benutzerinteraktionen und verwandte Ereignisse.

## Identität der Spalte {#column-identity}

Die Identität einer Spalte definiert, wie sie in der `Table` erkannt wird. Dazu gehören ihr Label, der Wert, den sie bereitstellt, und ob sie sichtbar oder navigierbar ist.

### Label {#label}

Das Label einer Spalte ist ihr öffentlich erkennbarer Bezeichner, der hilft, die angezeigten Daten zu klären.

Verwenden Sie `setLabel()`, um das Label festzulegen oder zu ändern.

:::tip
Standardmäßig wird das Label dasselbe wie die Spalten-ID sein.
:::

```java
table.addColumn("Produkt-ID", Product::getProductId).setLabel("ID");
```

### Wertanbieter {#value-providers}

Ein Wertanbieter ist eine Funktion, die dafür verantwortlich ist, Rohdaten aus dem zugrunde liegenden Datensatz in ein für die Anzeige einer bestimmten Spalte geeignetes Format zu übersetzen. Die von Ihnen definierte Funktion nimmt eine Instanz des Zeilendatentyps (T) und gibt den Wert zurück, der in der zugehörigen Spalte für diese bestimmte Zeile angezeigt werden soll.

Um einen Wertanbieter für eine Spalte festzulegen, verwenden Sie eine der Methoden `addColumn()` aus der `Table`-Komponente.

Im folgenden Snippet wird versucht, Daten aus einem JSON-Objekt abzufragen und es nur anzuzeigen, wenn die Daten nicht null sind.

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

Es ist möglich, die Sichtbarkeit einer Spalte festzulegen, um zu bestimmen, ob sie in der `Table` angezeigt wird oder nicht. Dies kann nützlich sein, um unter anderem zu entscheiden, ob sensible Informationen angezeigt werden sollen.

```java
table.addColumn("Kreditkarte", Customer::getCreditCardNumber).setHidden(true);
```

### Navigierbar {#navigable}

Das Attribut navigierbar bestimmt, ob Benutzer während der Navigation mit einer Spalte interagieren können. Das Setzen von `setSuppressNavigable()` auf true schränkt die Benutzerinteraktion mit der Spalte ein und bietet ein schreibgeschütztes Erlebnis.

```java
table.addColumn("Schreibgeschützte Spalte", Product::getDescription).setSuppressNavigable(true);
```

## Layout und Formatierung {#layout-and-formatting}

Nachdem die Identität einer Spalte festgelegt wurde, ist der nächste Schritt, zu steuern, wie ihr Inhalt den Benutzern erscheint. Layoutoptionen wie Ausrichtung und Anheften bestimmen, wo Daten sitzen und wie sie sichtbar bleiben, während Sie mit einer `Table` arbeiten.

### Ausrichtung {#alignment}

Die Ausrichtung einer Spalte festzulegen, ermöglicht es Ihnen, organisierte Tabellen zu erstellen, die Benutzern helfen können, die verschiedenen Abschnitte in der `Table` zu identifizieren.

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

- `Column.Alignment.LEFT`: Eignet sich für textuelle oder beschreibende Daten, bei denen es intuitiv ist, einen linken Fluss beizubehalten. Nützlich, wenn der Ausgangspunkt des Inhalts betont wird.
- `Column.Alignment.CENTER`: Mittig ausgerichtete Spalten sind ideal für kürzere Werte, wie einen Keys, Status oder alles andere, das eine ausgewogene Präsentation hat.
- `Column.Alignment.RIGHT`: Es kann sinnvoll sein, eine rechtsbündig ausgerichtete Spalte für numerische Werte zu verwenden, die leicht überflogen werden können, wie Daten, Beträge und Prozentsätze.

Im vorherigen Beispiel wurde die letzte Spalte für `Kosten` rechtsbündig ausgerichtet, um einen deutlich erkennbaren visuellen Unterschied zu bieten.

### Anheften {#pinning}

Das Anheften von Spalten ist eine Funktion, die es ermöglicht, eine Spalte an einer bestimmten Seite der `Table` zu befestigen oder zu "heften". Dies ist nützlich, wenn bestimmte Spalten, wie Identifikatoren oder wichtige Informationen, sichtbar bleiben müssen, während horizontal durch eine Tabelle gescrollt wird.

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

Es gibt drei verfügbare Richtungen, um eine Spalte zu pinnen:

- `PinDirection.LEFT`: Heftet die Spalte an die linke Seite.
- `PinDirection.RIGHT`: Heftet die Spalte an die rechte Seite.
- `PinDirection.AUTO`: Die Spalte erscheint basierend auf der Einfügereihenfolge.

Das Anheften kann programmgesteuert festgelegt werden, wodurch Sie die Pinnrichtung basierend auf Benutzerinteraktionen oder der Logik der App ändern können.

## Größenanpassung der Spalte <DocChip chip='since' label='25.03' /> {#column-sizing}

### Feste Breite {#fixed-width}

Legen Sie eine genaue Breite für eine Spalte fest, indem Sie die Methode `setWidth()` verwenden und die gewünschte Breite in Pixel angeben:

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

Die Breite-Eigenschaft definiert die gewünschte ursprüngliche Breite für die Spalte. Wie diese Breite genutzt wird, hängt von anderen Eigenschaften und dem Spaltentyp ab:

- **Reguläre Spalten**: Mit nur festgelegter Breite wird die Spalte in der angegebenen Breite gerendert, kann jedoch proportional schrumpfen, wenn der Container zu klein ist. Die ursprüngliche Breite dient als gewünschte Breite, aber ohne explizite Mindestbeschränkungen kann die Spalte kleiner als die festgelegte Breite gerendert werden.
- [**Fixierte Spalten**](#pinning): Behalten immer ihre genaue Breite und nehmen nie am responsiven Schrumpfen teil.
- [**Flex-Spalten**](#flex-sizing): Das Festlegen der Breite ist mit flex inkompatibel. Verwenden Sie entweder Breite (fest) oder flex (proportional), nicht beides.

Wenn nicht angegeben, verwendet die Spalte ihre geschätzte Breite basierend auf der Inhaltsanalyse der ersten paar Zeilen.

```java
// Aktuelle Breite abrufen
float currentWidth = column.getWidth();
```

### Mindestbreite {#minimum-width}

Die Methode `setMinWidth()` ermöglicht es Ihnen, die Mindestbreite einer Spalte zu definieren. Wenn die Mindestbreite nicht angegeben wird, berechnet die `Table` die Mindestbreite basierend auf dem Spalteninhalt.

```java
table.addColumn("Preis", Product::getPrice).setMinWidth(100f);
```

Der übergebene Wert stellt die Mindestbreite in Pixel dar.

Die Mindestbreite-Eigenschaft steuert die kleinste Breite, die eine Spalte haben kann:

- **Reguläre Spalten**: Bei nur festgelegter Mindestbreite verwendet die Spalte die Mindestbreite als sowohl gewünschte als auch Mindestbreite. Bei Breite + Mindestbreite kann die Spalte von der Breite auf die Mindestbreite schrumpfen, jedoch nicht weiter.
- [**Fixierte Spalten**](#pinning): Wenn nur die Mindestbreite festgelegt ist (keine Breite), wird sie zur festen Breite.
- [**Flex-Spalten**](#flex-sizing): Verhindert, dass die Spalte unter dieser Breite schrumpft, selbst wenn der Platz im Container begrenzt ist.

```java
// Aktuelle Mindestbreite abrufen
float minWidth = column.getMinWidth();
```

### Maximalbreite {#maximum-width}

Die Methode `setMaxWidth()` begrenzt, wie breit eine Spalte wachsen kann, um zu verhindern, dass Spalten mit langen Inhalten zu breit werden und die Lesbarkeit beeinträchtigen:

```java
table.addColumn("Beschreibung", Product::getDescription)
  .setMinWidth(100f)
  .setMaxWidth(300f);
```

Die `maxWidth`-Eigenschaft begrenzt das Wachstum der Spalte für alle Spaltentypen und wird niemals überschritten, unabhängig von Inhalten, Containergröße oder flex-Einstellungen.

```java
// Aktuelle Maximalbreite abrufen
float maxWidth = column.getMaxWidth();
```

### Flex-Größenanpassung {#flex-sizing}

Die Methode `setFlex()` ermöglicht eine proportionale Größenanpassung der Spalten, sodass Spalten den verfügbaren Platz teilen, nachdem feststehende Breiten-Spalten zugewiesen wurden:

```java
// Die Titelspalte erhält doppelt so viel Platz wie die Künstlerspalte
table.addColumn("Titel", Product::getTitle).setFlex(2f);
table.addColumn("Künstler", Product::getArtist).setFlex(1f);
```

Wichtige Flex-Verhaltensweisen:

- **Flex-Wert**: Bestimmt den Anteil des verfügbaren Platzes. Eine Spalte mit flex=2 erhält doppelt so viel Platz wie eine Spalte mit flex=1.
- **Inkompatibel mit Breite**: Kann nicht zusammen mit der Breite-Eigenschaft verwendet werden. Wenn die Flex größer als null ist, hat sie Vorrang vor der Breite-Einstellung.
- **Respektiert Einschränkungen**: Arbeitet mit den Einschränkungen der Mindestbreite/maximalen Breite. Ohne Mindestbreite können Flex-Spalten auf 0 schrumpfen.

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

:::info Breite vs. Flex
Breite und Flex-Eigenschaften sind gegenseitig ausschließend. Das Festlegen einer entfernt automatisch die andere. Verwenden Sie die Breite für präzise Kontrolle oder Flex für responsives Verhalten.
:::

### Automatische Größenanpassung {#automatic-sizing}

Über die manuellen Breiten- und Flex-Einstellungen hinaus können Spalten auch automatisch dimensioniert werden. Die automatische Größenanpassung ermöglicht es der `Table`, optimale Breiten zu bestimmen, indem sie Inhalte analysiert oder den Platz proportional verteilt.

#### Inhaltsbasierte automatische Größenanpassung {#content-based-auto-sizing}

Spalten automatisch basierend auf ihrem Inhalt dimensionieren. Die `Table` analysiert die Daten in jeder Spalte und berechnet die optimale Breite, um den Inhalt ohne Abschneiden anzuzeigen.

```java
// Alle Spalten automatisch dimensionieren, um den Inhalt anzupassen
table.setColumnsToAutoSize().thenAccept(c -> {
  // Größenanpassung abgeschlossen - Spalten passen jetzt zu ihrem Inhalt
});

// Bestimmte Spalte automatisch dimensionieren
table.setColumnToAutoSize("beschreibung");
```

#### Proportionale Anpassung {#proportional-auto-fit}

Alle Spalten proportional über die verfügbare `Table`-Breite verteilen. Dieser Vorgang setzt jede Spalte auf flex=1, sodass sie die gesamte Breite der `Table` gleichmäßig teilen, unabhängig von der Länge ihres Inhalts. Spalten werden sich erweitern oder zusammenziehen, um die genauen Maße der `Table` ohne verbleibenden Platz auszufüllen.

```java
// Spalten an die Tabellenbreite anpassen (entspricht dem Festlegen von flex=1 für alle)
table.setColumnsToAutoFit().thenAccept(ignored -> {
  // Alle Spalten teilen sich jetzt den Platz gleichmäßig
});
```

:::info Asynchrone Operationen
Automatische Größenanpassungsmethoden geben `PendingResult<Void>` zurück, da sie clientseitige Berechnungen erfordern. Verwenden Sie `thenAccept()`, um Code nach Abschluss der Größenanpassung auszuführen. Wenn Sie nicht auf den Abschluss warten müssen, können Sie die Methoden auch ohne `thenAccept()` aufrufen.
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

### Größenanpassung der Spalten {#column-resizing}

Die Größenanpassung der Spalten gibt Benutzern die Kontrolle darüber, wie viel Platz jede Spalte einnimmt, indem sie die Spaltenränder ziehen.

Sie können das Größenanpassungsverhalten für einzelne Spalten beim Erstellen Ihrer Tabelle steuern:

```java
// Benutzeranpassung für diese Spalte aktivieren
table.addColumn("Titel", Product::getTitle).setResizable(true);

// Größenanpassung deaktivieren
table.addColumn("ID", Product::getId).setResizable(false);

// Aktuellen Status überprüfen
boolean canResize = column.isResizable();
```

Für Tabellen, bei denen Sie ein konsistentes Verhalten über mehrere Spalten hinweg wünschen, verwenden Sie die massenweisen Konfigurationsmethoden:

```java
// Alle vorhandenen Spalten größenanpassbar machen
table.setColumnsToResizable(true);

// Alle vorhandenen Spalten von der Größenanpassung sperren
table.setColumnsToResizable(false);
```

### Neuordnung der Spalten {#column-reordering}

Die Neuordnung der Spalten ermöglicht es Benutzern, Spalten per Drag & Drop in ihre bevorzugte Reihenfolge zu ziehen und das Layout der `Table` für ihren Workflow zu personalisieren.

Konfigurieren Sie die Erlaubnis für die Bewegung von Spalten, wenn Sie Ihre Tabelle einrichten:

```java
// Benutzern erlauben, diese Spalte zu verschieben
table.addColumn("Titel", Product::getTitle).setMovable(true);

// Bewegung der Spalte verhindern (nützlich für ID- oder Aktionsspalten)
table.addColumn("ID", Product::getId).setMovable(false);

// Aktuellen Status überprüfen
boolean canMove = column.isMovable();
```

Wenden Sie die Bewegungseinstellungen gleichzeitig auf mehrere Spalten an:

```java
// Neuordnung für alle vorhandenen Spalten aktivieren
table.setColumnsToMovable(true);

// Neuordnung für alle vorhandenen Spalten deaktivieren
table.setColumnsToMovable(false);
```

:::note Massenoperationen
Die Methoden `setColumnsToResizable()` und `setColumnsToMovable()` betreffen nur vorhandene Spalten zum Zeitpunkt des Aufrufs. Sie setzen keine Standardwerte für zukünftige Spalten.
:::

### Programmgesteuerte Spaltenbewegung {#programmatic-column-movement}

Zusätzlich zu Drag & Drop können Sie Spalten auch programmgesteuert nach Index oder ID repositionieren. Bedenken Sie, dass der Index nur auf sichtbaren Spalten basiert; alle ausgeblendeten Spalten werden beim Berechnen der Positionen ignoriert.

```java
// Spalte an die erste Position verschieben
table.moveColumn("titel", 0);

// Spalte an die letzte Position verschieben
table.moveColumn(titleColumn, table.getColumns().size() - 1);

// Asynchrone Bewegung mit Rückruf
table.moveColumn("beschreibung", 2).thenAccept(c -> {
  // Spalte erfolgreich verschoben
});
```

## Ereignisbehandlung {#event-handling}

Die `Table`-Komponente gibt Ereignisse aus, wenn Benutzer mit Spalten interagieren, sodass Sie auf Layoutänderungen reagieren und Benutzerpräferenzen speichern können.

Unterstützte Ereignisse:

- `TableColumnResizeEvent`: Wird ausgelöst, wenn ein Benutzer eine Spalte durch Ziehen ihres Randes anpasst.
- `TableColumnMoveEvent`: Wird ausgelöst, wenn ein Benutzer eine Spalte durch Ziehen ihres Headers neu anordnet.

Sie können Listener an die `Table` anhängen, um zu reagieren, wenn Benutzer das Tabellenlayout ändern.

```java
Table<Product> table = new Table<>();

table.onColumnResize(event -> {
  // Ereignis zur Größenänderung der Spalte behandeln
  // Zugriff: event.getColumn(), event.getOldWidth(), event.getNewWidth()
});

table.onColumnMove(event -> {
  // Ereignis zur Bewegung der Spalte behandeln
  // Zugriff: event.getColumn(), event.getOldIndex(), event.getNewIndex()
});
```
