---
sidebar_position: 5
title: Columns
slug: columns
_i18n_hash: 59dc1d0f2eff7880d818123654e8febf
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

Die Klasse `Table` verwendet Spalteninstanzen, um zu definieren und anzupassen, wie Daten angezeigt werden. Spalten steuern, welche Daten angezeigt werden, wie sie aussehen und wie die Benutzer damit interagieren können. Diese Seite behandelt die Identität der Spalten, Präsentation, Größe, Benutzerinteraktionen und verwandte Ereignisse.

## Column identity {#column-identity}

Die Identität einer Spalte definiert, wie sie in der `Table` erkannt wird. Dies umfasst ihr Label, den Wert, den sie bereitstellt, und ob sie sichtbar oder navigierbar ist.

### Label {#label}

Das Label einer Spalte ist ihr öffentlich verwendbarer Identifikator, der hilft, die angezeigten Daten zu klären.

Verwenden Sie `setLabel()`, um das Label festzulegen oder zu ändern.

:::tip
Standardmäßig wird das Label dasselbe wie die Spalten-ID sein.
:::

```java
table.addColumn("Produkt-ID", Product::getProductId).setLabel("ID");
```

### Value providers {#value-providers}

Ein Wertanbieter ist eine Funktion, die dafür verantwortlich ist, Rohdaten aus dem zugrunde liegenden Datensatz in ein für die Anzeige innerhalb einer bestimmten Spalte geeignetes Format zu übersetzen. Die von Ihnen definierte Funktion nimmt eine Instanz des Zeilendatentyps (T) und gibt den Wert zurück, der in der zugehörigen Spalte für diese bestimmte Zeile angezeigt werden soll.

Um einen Wertanbieter für eine Spalte festzulegen, verwenden Sie eine der `addColumn()`-Methoden aus der `Table`-Komponente.

Im folgenden Snippet versucht eine Spalte, Daten aus einem JSON-Objekt abzurufen, und rendert sie nur, wenn die Daten nicht null sind.

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

### Visibility {#visibility}

Es ist möglich, die Sichtbarkeit einer Spalte festzulegen, um zu bestimmen, ob sie innerhalb der `Table` angezeigt wird oder nicht. Dies kann nützlich sein, um unter anderem zu entscheiden, ob sensible Informationen angezeigt werden sollen.

```java
table.addColumn("Kreditkarte", Customer::getCreditCardNumber).setHidden(true);
```

### Navigable {#navigable}

Das navigierbare Attribut bestimmt, ob Benutzer während der Navigation mit einer Spalte interagieren können. Das Setzen von `setSuppressNavigable()` auf true schränkt die Benutzerinteraktion mit der Spalte ein und bietet eine schreibgeschützte Erfahrung.

```java
table.addColumn("Schreibgeschützte Spalte", Product::getDescription).setSuppressNavigable(true);
```

## Layout und Formatting {#layout-and-formatting}

Nachdem die Identität einer Spalte festgelegt wurde, besteht der nächste Schritt darin, zu steuern, wie der Inhalt den Benutzern angezeigt wird. Layoutoptionen wie Ausrichtung und Anheften bestimmen, wo die Daten sitzen und wie sie sichtbar bleiben, während Sie mit einer `Table` arbeiten.

### Alignment {#alignment}

Die Festlegung der Ausrichtung einer Spalte ermöglicht es Ihnen, organisierte Tabellen zu erstellen, die den Benutzern helfen können, die verschiedenen Abschnitte in der `Table` zu identifizieren.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnAlignmentView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

Die `Table`-Komponente unterstützt drei Ausrichtungsoptionen:

- `Column.Alignment.LEFT`: Eignet sich für Text- oder Beschreibungdaten, bei denen eine linksgerichtete Ausrichtung intuitiv ist. Nützlich, wenn der Ausgangspunkt des Inhalts betont werden soll.
- `Column.Alignment.CENTER`: Zentriert ausgerichtete Spalten sind ideal für kürzere Werte, wie einen Zeichensatz, Status oder alles andere, das eine ausgewogene Präsentation hat.
- `Column.Alignment.RIGHT`: Ziehen Sie in Betracht, eine rechtsbündige Spalte für numerische Werte zu verwenden, die hilfreich sind, um schnell durchzugehen, wie Daten, Beträge und Prozentsätze.

Im vorhergehenden Beispiel wurde die letzte Spalte für `Kosten` rechtsbündig ausgerichtet, um eine offensichtlichere visuelle Unterscheidung zu bieten.

### Pinning {#pinning}

Das Anheften von Spalten ist eine Funktion, die es Benutzern ermöglicht, eine Spalte an eine bestimmte Seite der `Table` zu heften oder "anzupinnen". Dies ist nützlich, wenn bestimmte Spalten, wie Identifikatoren oder wichtige Informationen, while scrolling horizontal durch eine Tabelle sichtbar bleiben müssen.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnpinning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

Es gibt drei verfügbare Richtungen für das Anheften einer Spalte:

- `PinDirection.LEFT`: Heftet die Spalte an die linke Seite an.
- `PinDirection.RIGHT`: Heftet die Spalte an die rechte Seite an.
- `PinDirection.AUTO`: Spalte erscheint basierend auf der Einfügeordnung.

Das Anheften kann programmgesteuert festgelegt werden, sodass Sie die AnheRichterschaft je nach Benutzerinteraktionen oder der Logik der Anwendung ändern können.

## Column sizing <DocChip chip='since' label='25.03' /> {#column-sizing}

### Fixed width {#fixed-width}

Legen Sie eine genaue Breite für eine Spalte fest, indem Sie die Methode `setWidth()` verwenden und die gewünschte Breite in Pixeln angeben:

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

Die Breiteigenschaft definiert die gewünschte Anfangsbreite der Spalte. Wie diese Breite verwendet wird, hängt von anderen Eigenschaften und dem Spaltentyp ab:

- **Regelmäßige Spalten**: Mit nur festgelegter Breite wird die Spalte in der angegebenen Breite gerendert, kann aber proportional schrumpfen, wenn der Container zu klein ist. Die ursprüngliche Breite dient als gewünschte Breite, aber ohne explizite Mindestbeschränkungen kann die Spalte kleiner als die festgelegte Breite gerendert werden.
- [**Angeheftete Spalten**](#pinning): Behalten immer ihre genaue Breite bei und nehmen niemals am responsiven Schrumpfen teil.
- [**Flex-Spalten**](#flex-sizing): Die Festlegung der Breite ist mit Flex inkompatibel. Verwenden Sie entweder Breite (fest) oder Flex (proportional), nicht beides.

Wenn nicht angegeben, verwendet die Spalte ihre geschätzte Breite basierend auf der Inhaltsanalyse der ersten paar Zeilen.

```java
// Aktuelle Breite abrufen
float currentWidth = column.getWidth();
```

### Minimum width {#minimum-width}

Die Methode `setMinWidth()` ermöglicht es Ihnen, die minimale Breite einer Spalte zu definieren. Wenn die minimale Breite nicht angegeben ist, berechnet die `Table` die minimale Breite basierend auf dem Spalteninhalt.

```java
table.addColumn("Preis", Product::getPrice).setMinWidth(100f);
```

Der übergebene Wert stellt die minimale Breite in Pixeln dar.

Die minimale Breiteigenschaft steuert die kleinste Breite, die eine Spalte haben kann:

- **Regelmäßige Spalten**: Mit nur festgelegter minimaler Breite verwendet die Spalte die minimale Breite sowohl als gewünschte als auch als minimale Breite. Mit Breite + minimaler Breite kann die Spalte von der Breite auf die minimale Breite schrumpfen, aber nicht weiter.
- [**Angeheftete Spalten**](#pinning): Wenn nur die minimale Breite festgelegt ist (keine Breite), wird sie zur festen Breite.
- [**Flex-Spalten**](#flex-sizing): Verhindert, dass die Spalte unterhalb dieser Breite schrumpft, selbst wenn der Platz im Container begrenzt ist.

```java
// Aktuelle minimale Breite abrufen
float minWidth = column.getMinWidth();
```

### Maximum width {#maximum-width}

Die Methode `setMaxWidth()` begrenzt, wie breit eine Spalte wachsen kann, um zu verhindern, dass Spalten mit langen Inhalten zu breit werden und die Lesbarkeit beeinträchtigen:

```java
table.addColumn("Beschreibung", Product::getDescription)
    .setMinWidth(100f)
    .setMaxWidth(300f);
```

Die `maxWidth`-Eigenschaft begrenzt das Wachstum der Spalte für alle Spaltentypen und wird niemals überschritten, unabhängig vom Inhalt, der Containergröße oder den Flex-Einstellungen.

```java
// Aktuelle maximale Breite abrufen
float maxWidth = column.getMaxWidth();
```

### Flex sizing {#flex-sizing}

Die Methode `setFlex()` ermöglicht die proportionale Spaltengröße, sodass Spalten den verfügbaren Platz teilen, nachdem die Spalten mit fester Breite zugewiesen wurden:

```java
// Die Spaltenüberschrift erhält doppelt so viel Platz wie die Künstler-Spalte
table.addColumn("Titel", Product::getTitle).setFlex(2f);
table.addColumn("Künstler", Product::getArtist).setFlex(1f);
```

Wichtige Flex-Verhalten:

- **Flex-Wert**: Bestimmt den Anteil des verfügbaren Raums. Eine Spalte mit flex=2 erhält den doppelten Raum einer Spalte mit flex=1.
- **Inkompatibel mit Breite**: Kann nicht zusammen mit der Breite eigenschaft verwendet werden. Wenn Flex größer als null ist, hat sie Vorrang vor der Breiten Einstellung.
- **Respektiert Beschränkungen**: Funktioniert mit Mindestbreiten-/Maximalbreitenbeschränkungen. Ohne Mindestbreite können Flex-Spalten auf 0 schrumpfen.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnflexsizing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnFlexSizingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='550px'
/>
<!-- vale on -->

:::info Width vs Flex
Breite und Flex-Eigenschaften sind gegenseitig ausschließend. Das Festlegen von einem löscht automatisch den anderen. Verwenden Sie Breite für präzise Steuerung oder Flex für responsives Verhalten.
:::

### Automatic sizing {#automatic-sizing}

Neben manuellen Breiten- und Flexeinstellungen können Spalten auch automatisch dimensioniert werden. Die automatische Größenanpassung ermöglicht es der `Table`, optimale Breiten zu bestimmen, indem sie Inhalte analysiert oder den Platz proportional verteilt.

#### Content-based auto-sizing {#content-based-auto-sizing}

Passen Sie die Größe der Spalten automatisch basierend auf deren Inhalt an. Die `Table` analysiert die Daten in jeder Spalte und berechnet die optimale Breite, um den Inhalt ohne Abschneidung darzustellen.

```java
// Alle Spalten automatisch anpassen, um den Inhalt zu passen
table.setColumnsToAutoSize().thenAccept(c -> {
    // Größenanpassung abgeschlossen - Spalten passen jetzt zu ihrem Inhalt
});

// Spezifische Spalte automatisch anpassen
table.setColumnToAutoSize("beschreibung");
```

#### Proportional auto-fit {#proportional-auto-fit}

Verteilen Sie alle Spalten proportional über die verfügbare Breite der `Table`. Dieser Vorgang setzt jede Spalte auf flex=1, sodass sie die gesamte Breite der `Table` gleichmäßig teilen, unabhängig von der Länge des Inhalts. Spalten dehnen oder ziehen sich zusammen, um die genauen Dimensionen der `Table` ohne verbleibenden Platz zu füllen.

```java
// Spalten an die Tabellenbreite anpassen (entspricht dem Setzen von flex=1 für alle)
table.setColumnsToAutoFit().thenAccept(ignored -> {
    // Alle Spalten teilen jetzt den Platz gleichmäßig
});
```

:::info Async Operations
Automatische Größenänderungsmethoden geben `PendingResult<Void>` zurück, da sie clientseitige Berechnungen erfordern. Verwenden Sie `thenAccept()`, um Code auszuführen, nachdem die Größenänderung abgeschlossen ist. Wenn Sie nicht auf den Abschluss warten müssen, können Sie die Methoden ohne `thenAccept()` aufrufen.
:::

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnautosizing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnAutoSizingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='550px'
/>
<!-- vale on -->

## User interactions <DocChip chip='since' label='25.03' /> {#user-interactions}

### Column resizing {#column-resizing}

Das Ändern der Spaltengröße gibt den Benutzern die Kontrolle darüber, wie viel Platz jede Spalte einnimmt, indem sie die Spaltenränder ziehen.

Sie können das Größenänderungsverhalten für einzelne Spalten steuern, wenn Sie Ihre Tabelle erstellen:

```java
// Benutzergrößenänderung für diese Spalte aktivieren
table.addColumn("Titel", Product::getTitle).setResizable(true);

// Größenänderung deaktivieren
table.addColumn("ID", Product::getId).setResizable(false);

// Aktuellen Zustand überprüfen
boolean canResize = column.isResizable();
```

Für Tabellen, bei denen Sie ein konsistentes Verhalten über mehrere Spalten hinweg wünschen, verwenden Sie die Bulk-Konfigurationsmethoden:

```java
// Alle vorhandenen Spalten anpassbar machen
table.setColumnsToResizable(true);

// Alle vorhandenen Spalten von der Größenänderung sperren
table.setColumnsToResizable(false);
```

### Column reordering {#column-reordering}

Die Neuanordnung der Spalten ermöglicht es Benutzern, Spalten per Drag & Drop in ihre bevorzugte Reihenfolge zu ziehen und das Layout der `Table` an ihren Workflow anzupassen.

Konfigurieren Sie die Berechtigungen für die Spaltenbewegung beim Einrichten Ihrer Tabelle:

```java
// Benutzern erlauben, diese Spalte zu verschieben
table.addColumn("Titel", Product::getTitle).setMovable(true);

// Spaltenbewegung verhindern (nützlich für ID- oder Aktionsspalten)
table.addColumn("ID", Product::getId).setMovable(false);

// Aktuellen Zustand überprüfen
boolean canMove = column.isMovable();
```

Wenden Sie die Bewegungseinstellungen gleichzeitig auf mehrere Spalten an:

```java
// Neuanordnung für alle vorhandenen Spalten aktivieren
table.setColumnsToMovable(true);

// Neuanordnung für alle vorhandenen Spalten deaktivieren  
table.setColumnsToMovable(false);
```

:::note Bulk Operations
Die Methoden `setColumnsToResizable()` und `setColumnsToMovable()` betreffen nur vorhandene Spalten zum Zeitpunkt der Aufruf. Sie setzen keine Standards für zukünftige Spalten.
:::

### Programmatic column movement {#programmatic-column-movement} 

Neben Drag-and-Drop können Sie Spalten auch programmgesteuert nach Index oder ID verschieben. Beachten Sie, dass der Index nur auf sichtbare Spalten basiert; alle versteckten Spalten werden bei der Berechnung der Positionen ignoriert.

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

## Event handling {#event-handling}

Die `Table`-Komponente gibt Ereignisse aus, wenn Benutzer mit Spalten interagieren, sodass Sie auf Layoutänderungen reagieren und Benutzerpräferenzen speichern können.

Unterstützte Ereignisse:

- `TableColumnResizeEvent`: Wird ausgelöst, wenn ein Benutzer eine Spalte durch Ziehen ihres Randes vergrößert oder verkleinert.
- `TableColumnMoveEvent`: Wird ausgelöst, wenn ein Benutzer eine Spalte durch Ziehen ihres Headers neu anordnet.

Sie können Listener an die `Table` anhängen, um zu reagieren, wenn Benutzer das Tabellenlayout ändern.

```java
Table<Product> table = new Table<>();

table.onColumnResize(event -> {
  // Ereignis für die Größenänderung der Spalte behandeln
  // Zugriff: event.getColumn(), event.getOldWidth(), event.getNewWidth()
});

table.onColumnMove(event -> {
  // Ereignis für die Neuanordnung der Spalte behandeln  
  // Zugriff: event.getColumn(), event.getOldIndex(), event.getNewIndex()
});
```
