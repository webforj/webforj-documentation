---
sidebar_position: 5
title: Columns
slug: columns
sidebar_class_name: new-content
_i18n_hash: 280a70bb65c45d3b200157f3462d7b10
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

Die `Table`-Klasse verwendet Spalteninstanzen, um zu definieren und anzupassen, wie Daten angezeigt werden. Spalten steuern, welche Daten angezeigt werden, wie sie aussehen und wie Benutzer damit interagieren können. Diese Seite behandelt die Identität der Spalte, Präsentation, Größe, Benutzerinteraktionen und verwandte Ereignisse.

## Identität der Spalte {#column-identity}

Die Identität einer Spalte definiert, wie sie in der `Table` erkannt wird. Dazu gehören ihr Label, der Wert, den sie bereitstellt, und ob sie sichtbar oder navigierbar ist.

### Label {#label}

Das Label einer Spalte ist ihr öffentlich sichtbarer Bezeichner, der hilft, die angezeigten Daten zu klären.  

Verwenden Sie `setLabel()`, um das Label festzulegen oder zu ändern.

:::tip
Standardmäßig hat das Label denselben Namen wie die Spalten-ID.
:::

```java
table.addColumn("Product ID", Product::getProductId).setLabel("ID");
```

### Wertanbieter {#value-providers}

Ein Wertanbieter ist eine Funktion, die dafür verantwortlich ist, Rohdaten aus dem zugrunde liegenden Datensatz in ein Format zu übersetzen, das für die Anzeige in einer bestimmten Spalte geeignet ist. Die Funktion, die Sie definieren, nimmt eine Instanz des Zeilendatentyps (T) und gibt den Wert zurück, der in der zugehörigen Spalte für diese bestimmte Zeile angezeigt werden soll.

Um einen Wertanbieter in einer Spalte festzulegen, verwenden Sie eine der Methoden `addColumn()` aus der `Table`-Komponente.

Im folgenden Snippet versucht eine Spalte, Daten aus einem JSON-Objekt abzurufen und rendert sie nur, wenn die Daten nicht null sind.

```java
    List<String> columnsList = Arrays.asList("athlete", "age", "country", "year", "sport", "gold", "silver", "bronze", "total");

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

Es ist möglich, die Sichtbarkeit einer Spalte festzulegen, um zu bestimmen, ob sie in der `Table` angezeigt wird oder nicht. Dies kann nützlich sein, um unter anderem festzustellen, ob sensible Informationen angezeigt werden sollen oder nicht.

```java
table.addColumn("Credit Card", Customer::getCreditCardNumber).setHidden(true);
```

### Navigierbar {#navigable}

Das Attribut navigierbar bestimmt, ob Benutzer während der Navigation mit einer Spalte interagieren können. Wenn `setSuppressNavigable()` auf true gesetzt wird, wird die Benutzerinteraktion mit der Spalte eingeschränkt, was ein schreibgeschütztes Erlebnis bietet.

```java
table.addColumn("ReadOnly Column", Product::getDescription).setSuppressNavigable(true);
```

## Layout und Formatierung {#layout-and-formatting}

Nachdem die Identität einer Spalte festgelegt wurde, ist der nächste Schritt, zu steuern, wie ihr Inhalt den Benutzern angezeigt wird. Layout-Optionen wie Ausrichtung und Anheften bestimmen, wo die Daten sitzen, und wie sie sichtbar bleiben, während Sie mit einer `Table` arbeiten.

### Ausrichtung {#alignment}

Die Ausrichtung einer Spalte festzulegen, ermöglicht es Ihnen, organisierte Tabellen zu erstellen, die Benutzern helfen können, die verschiedenen Abschnitte in der `Table` zu identifizieren.

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

- `Column.Alignment.LEFT`: Geeignet für textuelle oder beschreibende Daten, bei denen ein linksgerichteter Fluss intuitiv ist. Nützlich, um den Ausgangspunkt des Inhalts zu betonen.
- `Column.Alignment.CENTER`: Zentriert ausgerichtete Spalten sind ideal für kürzere Werte, wie einen Zeichenschlüssel, Status oder alles andere, das eine ausgewogene Präsentation hat.
- `Column.Alignment.RIGHT`: Verwenden Sie eine rechtsbündige Spalte für numerische Werte, die hilfreich sind, um schnell durchgescannt zu werden, wie Daten, Beträge und Prozentsätze.

Im vorherigen Beispiel wurde die letzte Spalte für `Cost` rechtsbündig ausgerichtet, um eine offensichtlichere visuelle Unterscheidung zu bieten.

### Anheften {#pinning}

Das Anheften von Spalten ist eine Funktion, die es Benutzern ermöglicht, eine Spalte an eine bestimmte Seite der `Table` zu befestigen oder "anzuhängen". Dies ist nützlich, wenn bestimmte Spalten, wie Identifikatoren oder wichtige Informationen, sichtbar bleiben müssen, während horizontal durch eine Tabelle gescrollt wird.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnpinning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

Es gibt drei verfügbare Richtungen zum Anheften einer Spalte:

- `PinDirection.LEFT`: Heftet die Spalte an die linke Seite an.
- `PinDirection.RIGHT`: Heftet die Spalte an die rechte Seite an.
- `PinDirection.AUTO`: Spalte erscheint basierend auf der Einfügeordnung.

Das Anheften kann programmgesteuert festgelegt werden, sodass Sie die Anhegerichtung basierend auf Benutzerinteraktionen oder durch die Logik der App ändern können.

## Spaltengröße <DocChip chip='since' label='25.03' /> {#column-sizing} 

### Feste Breite {#fixed-width}

Legen Sie eine exakte Breite für eine Spalte mit der Methode `setWidth()` fest, indem Sie die gewünschte Breite in Pixel angeben:

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

Die Breiteigenschaft definiert die gewünschte Anfangsbreite für die Spalte. Wie diese Breite verwendet wird, hängt von anderen Eigenschaften und dem Spaltentyp ab:

- **Reguläre Spalten**: Wenn nur die Breite festgelegt ist, wird die Spalte in der angegebenen Breite gerendert, kann jedoch proportional schrumpfen, wenn der Container zu klein ist. Die ursprüngliche Breite dient als gewünschte Breite, jedoch kann die Spalte ohne explizite Mindestbeschränkungen kleiner gerendert werden als die eingestellte Breite.
- [**Angeheftete Spalten**](#pinning): Behalten immer ihre genaue Breite bei und beteiligen sich nie am responsiven Schrumpfen.
- [**Flex-Spalten**](#flex-sizing): Das Festlegen der Breite ist mit Flex inkompatibel. Verwenden Sie entweder Breite (fest) oder Flex (proportional), nicht beides.

Wenn nicht angegeben, verwendet die Spalte ihre geschätzte Breite basierend auf der Inhaltsanalyse der ersten paar Zeilen.

```java
// Aktuelle Breite abrufen
float currentWidth = column.getWidth();
```

### Mindestbreite {#minimum-width}

Mit der Methode `setMinWidth()` können Sie die Mindestbreite einer Spalte festlegen. Wenn keine Mindestbreite angegeben ist, berechnet die `Table` die Mindestbreite basierend auf dem Spalteninhalt.

```java
table.addColumn("Price", Product::getPrice).setMinWidth(100f);
```

Der übergebene Wert repräsentiert die Mindestbreite in Pixel.

Die Mindestbreite-Eigenschaft steuert die kleinste Breite, die eine Spalte haben kann:

- **Reguläre Spalten**: Wenn nur die Mindestbreite festgelegt ist, verwendet die Spalte die Mindestbreite sowohl als gewünschte als auch als Mindestbreite. Mit Breite + Mindestbreite kann die Spalte von der Breite bis zur Mindestbreite schrumpfen, jedoch nicht weiter.
- [**Angeheftete Spalten**](#pinning): Wenn nur die Mindestbreite festgelegt ist (keine Breite), wird sie zur festen Breite.
- [**Flex-Spalten**](#flex-sizing): Verhindert, dass die Spalte unter dieser Breite schrumpft, selbst wenn der Platz im Container begrenzt ist.

```java
// Aktuelle Mindestbreite abrufen
float minWidth = column.getMinWidth();
```

### Maximalbreite {#maximum-width}

Die Methode `setMaxWidth()` begrenzt, wie breit eine Spalte wachsen kann, um zu verhindern, dass Spalten mit langen Inhalten zu breit werden und die Lesbarkeit beeinträchtigen:

```java
table.addColumn("Description", Product::getDescription)
    .setMinWidth(100f)
    .setMaxWidth(300f);
```

Die `maxWidth`-Eigenschaft begrenzt das Wachstum von Spalten für alle Spaltentypen und wird niemals überschritten, unabhängig von Inhalt, Containergröße oder Flex-Einstellungen.

```java
// Aktuelle Maximalbreite abrufen
float maxWidth = column.getMaxWidth();
```

### Flex-Größe {#flex-sizing}

Die Methode `setFlex()` ermöglicht eine proportionale Spaltengröße, indem sie Spalten den verfügbaren Platz nach der Zuteilung fester Breiten teilen lässt:

```java
// Die Titelspalte erhält doppelt so viel Platz wie die Künstler-Spalte
table.addColumn("Title", Product::getTitle).setFlex(2f);
table.addColumn("Artist", Product::getArtist).setFlex(1f);
```

Wichtige Flex-Verhalten:

- **Flex-Wert**: Bestimmt den Anteil des verfügbaren Raums. Eine Spalte mit flex=2 erhält doppelt so viel Platz wie eine Spalte mit flex=1.
- **Inkompatibel mit Breite**: Kann nicht zusammen mit der Breiten-Eigenschaft verwendet werden. Wenn flex größer als null ist, hat es Vorrang vor der Breite.
- **Respektiert Einschränkungen**: Funktioniert mit Mindestbreiten-/Maximalbreiten-Einschränkungen. Wenn keine Mindestbreite vorhanden ist, können Flex-Spalten auf 0 schrumpfen.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnflexsizing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnFlexSizingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='550px'
/>
<!-- vale on -->

:::info Breite vs Flex
Breite und Flex-Eigenschaften sind gegenseitig ausschließend. Das Festlegen einer von beiden löscht automatisch die andere. Verwenden Sie Breite für präzise Steuerung oder Flex für responsives Verhalten.
:::

### Automatische Größenbestimmung {#automatic-sizing}

Über manuelle Breiten- und Flex-Einstellungen hinaus können Spalten auch automatisch dimensioniert werden. Die automatische Größenbestimmung lässt die `Table` optimale Breiten bestimmen, indem entweder der Inhalt analysiert oder der Platz proportional verteilt wird.

#### Inhaltsbasierte automatische Größenbestimmung {#content-based-auto-sizing}

Automatisch Spalten basierend auf ihrem Inhalt dimensionieren. Die `Table` analysiert die Daten in jeder Spalte und berechnet die optimale Breite, um den Inhalt ohne Truncation anzuzeigen.

```java
// Automatische Größenbestimmung aller Spalten, um den Inhalt anzupassen
table.setColumnsToAutoSize().thenAccept(c -> {
    // Größenbestimmung abgeschlossen - Spalten passen jetzt zu ihrem Inhalt
});

// Automatische Größenbestimmung einer spezifischen Spalte
table.setColumnToAutoSize("description");
```

#### Proportionale automatische Anpassung {#proportional-auto-fit}

Verteilen Sie alle Spalten proportional über die verfügbare `Table`-Breite. Diese Operation setzt jede Spalte auf flex=1, sodass sie die gesamte `Table`-Breite gleichmäßig teilen, unabhängig von ihrer Inhaltslänge. Spalten erweitern oder ziehen sich zusammen, um die genauen Abmessungen der `Table` ohne verbleibenden Raum auszufüllen.

```java
// Spalten an die Tabellenbreite anpassen (entspricht der Einstellung flex=1 für alle)
table.setColumnsToAutoFit().thenAccept(ignored -> {
    // Alle Spalten teilen nun den Platz gleichmäßig
});
```

:::info Asynchrone Vorgänge
Automatische Größenbestimmungsverfahren geben `PendingResult<Void>` zurück, da sie clientseitige Berechnungen erfordern. Verwenden Sie `thenAccept()`, um nach Abschluss der Größenbestimmung Code auszuführen. Wenn Sie nicht auf den Abschluss warten müssen, können Sie die Methoden auch ohne `thenAccept()` aufrufen.
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

## Benutzerinteraktionen <DocChip chip='since' label='25.03' /> {#user-interactions}

### Spaltenvergrößerung {#column-resizing}

Die Spaltenvergrößerung gibt Benutzern die Kontrolle darüber, wie viel Platz jede Spalte einnimmt, indem sie die Spaltengrenzen ziehen.

Sie können das Größenverhalten für einzelne Spalten steuern, wenn Sie Ihre Tabelle erstellen:

```java
// Benutzergrößenänderung für diese Spalte aktivieren
table.addColumn("Title", Product::getTitle).setResizable(true);

// Größenänderung deaktivieren
table.addColumn("ID", Product::getId).setResizable(false);

// Aktuellen Zustand überprüfen
boolean canResize = column.isResizable();
```

Für Tabellen, bei denen Sie ein konsistentes Verhalten über mehrere Spalten hinweg wünschen, verwenden Sie die Methoden zur massenhaften Konfiguration:

```java
// Alle vorhandenen Spalten für die Größenänderung aktivieren
table.setColumnsToResizable(true);

// Alle vorhandenen Spalten von der Größenänderung ausschließen
table.setColumnsToResizable(false);
```

### Spaltenneuanordnung {#column-reordering}

Die Neuanordnung von Spalten ermöglicht es Benutzern, Spalten per Drag & Drop in ihre bevorzugte Reihenfolge zu ziehen und so das Layout der `Table` für ihren Arbeitsablauf zu personalisieren.

Konfigurieren Sie die Erlaubnis für das Verschieben von Spalten, wenn Sie Ihre Tabelle einrichten:

```java
// Benutzer das Verschieben dieser Spalte erlauben
table.addColumn("Title", Product::getTitle).setMovable(true);

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

:::note Massenoperationen
Die Methoden `setColumnsToResizable()` und `setColumnsToMovable()` wirken sich nur auf vorhandene Spalten zum Zeitpunkt des Aufrufs aus. Sie setzen keine Standards für zukünftige Spalten.
:::

### Programmgesteuerte Spaltenbewegung {#programmatic-column-movement} 

Zusätzlich zu Drag-and-Drop können Sie Spalten auch programmgesteuert nach Index oder ID repositionieren. Beachten Sie, dass der Index nur auf sichtbare Spalten basiert; versteckte Spalten werden beim Berechnen der Positionen ignoriert.

```java
// Spalte an die erste Position verschieben
table.moveColumn("title", 0);

// Spalte an die letzte Position verschieben
table.moveColumn(titleColumn, table.getColumns().size() - 1);

// Asynchrone Bewegung mit Rückruf
table.moveColumn("description", 2).thenAccept(c -> {
    // Spalte erfolgreich verschoben
});
```

## Ereignisbehandlung {#event-handling}

Die `Table`-Komponente emitziert Ereignisse, wenn Benutzer mit den Spalten interagieren, und ermöglicht es Ihnen, auf Layoutänderungen zu reagieren und die Benutzerpräferenzen zu speichern.

Unterstützte Ereignisse:

- `TableColumnResizeEvent`: Wird ausgelöst, wenn ein Benutzer eine Spalte durch Ziehen ihrer Grenze vergrößert.
- `TableColumnMoveEvent`: Wird ausgelöst, wenn ein Benutzer eine Spalte durch Ziehen ihres Headers neu anordnet.

Sie können Listener an die `Table` anhängen, um zu reagieren, wenn Benutzer das Tabellenlayout ändern.

```java
Table<Product> table = new Table<>();

table.onColumnResize(event -> {
  // Ereignis zur Größenänderung der Spalte bearbeiten
  // Zugriff: event.getColumn(), event.getOldWidth(), event.getNewWidth()
});

table.onColumnMove(event -> {
  // Ereignis zur Neuanordnung der Spalte bearbeiten  
  // Zugriff: event.getColumn(), event.getOldIndex(), event.getNewIndex()
});
```
