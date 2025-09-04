---
sidebar_position: 5
title: Columns
slug: columns
sidebar_class_name: new-content
_i18n_hash: 6be8663364f0b321c603eb8b870cc104
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

Die `Table`-Klasse verwendet Spalteninstanzen, um zu definieren und anzupassen, wie Daten angezeigt werden. Spalten steuern, welche Daten angezeigt werden, wie sie aussehen und wie Benutzer mit ihnen interagieren können. Diese Seite behandelt die Identität der Spalten, die Präsentation, die Größe, Benutzerinteraktionen und verwandte Ereignisse.

## Spaltenidentität {#column-identity}

Die Identität einer Spalte definiert, wie sie in der `Table` erkannt wird. Dazu gehören ihr Label, der entsprechende Wert sowie die Sichtbarkeit und Navigierbarkeit.

### Label {#label}

Das Label einer Spalte ist ihr öffentlich sichtbarer Bezeichner, der hilft, die angezeigten Daten zu klären.  

Verwenden Sie `setLabel()`, um das Label festzulegen oder zu ändern.

:::tip
Standardmäßig wird das Label dasselbe sein wie die Spalten-ID.
:::

```java
table.addColumn("Produkt-ID", Product::getProductId).setLabel("ID");
```

### Wertanbieter {#value-providers}

Ein Wertanbieter ist eine Funktion, die dafür verantwortlich ist, Rohdaten aus dem zugrunde liegenden Datensatz in ein Format zu übersetzen, das zur Anzeige in einer bestimmten Spalte geeignet ist. Die von Ihnen definierte Funktion nimmt eine Instanz des Zeilendatentyps (T) und gibt den Wert zurück, der in der zugehörigen Spalte für diese spezielle Zeile angezeigt werden soll.

Um einen Wertanbieter für eine Spalte festzulegen, verwenden Sie eine der `addColumn()`-Methoden des `Table`-Komponents.

Im folgenden Snippet versucht eine Spalte, Daten aus einem JSON-Objekt abzurufen, und zeigt sie nur an, wenn die Daten nicht null sind.

```java
    List<String> columnsList = Arrays.asList("athlet", "alter", "land", "jahr", "sport", "gold", "silber", "bronze", "gesamt");

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

Es ist möglich, die Sichtbarkeit einer Spalte festzulegen, und damit zu bestimmen, ob sie innerhalb der `Table` angezeigt wird oder nicht. Dies kann nützlich sein, um unter anderem zu entscheiden, ob sensible Informationen angezeigt werden sollen. 

```java
table.addColumn("Kreditkarte", Customer::getCreditCardNumber).setHidden(true);
```

### Navigierbar {#navigable}

Das Attribut navigierbar bestimmt, ob Benutzer während der Navigation mit einer Spalte interagieren können. Das Setzen von `setSuppressNavigable()` auf true schränkt die Benutzerinteraktion mit der Spalte ein und bietet eine schreibgeschützte Erfahrung.

```java
table.addColumn("Schreibgeschützte Spalte", Product::getDescription).setSuppressNavigable(true);
```

## Layout und Formatierung {#layout-and-formatting}

Nachdem die Identität einer Spalte festgelegt wurde, besteht der nächste Schritt darin, zu steuern, wie der Inhalt für die Benutzer erscheint. Layout-Optionen wie Ausrichtung und Anheften bestimmen, wo die Daten platziert werden und wie sie sichtbar bleiben, während Sie mit einer `Table` arbeiten.

### Ausrichtung {#alignment}

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

- `Column.Alignment.LEFT`: Geeignet für textuelle oder beschreibende Daten, bei denen ein linksseitiger Fluss intuitiv ist. Nützlich, wenn der Ausgangspunkt des Inhalts hervorgehoben wird.
- `Column.Alignment.CENTER`: Zentralausgerichtete Spalten eignen sich ideal für kürzere Werte, wie einen Zeichenschlüssel, Status oder alles andere, das eine ausgewogene Präsentation hat.
- `Column.Alignment.RIGHT`: Ziehen Sie in Betracht, eine rechtsbündige Spalte für numerische Werte zu verwenden, die hilfreich sind, um schnell durchzuscannen, wie z.B. Daten, Beträge und Prozentsätze.

Im vorherigen Beispiel wurde die letzte Spalte für `Kosten` rechtsbündig ausgerichtet, um einen offensichtlicheren visuellen Unterschied zu schaffen.

### Anheften {#pinning}

Das Anheften von Spalten ist eine Funktion, die es Benutzern ermöglicht, eine Spalte an eine bestimmte Seite der `Table` anzupinnen. Dies ist nützlich, wenn bestimmte Spalten, wie Identifikatoren oder wichtige Informationen, während des horizontalen Scrollens durch eine Tabelle sichtbar bleiben müssen.

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

- `PinDirection.LEFT`: Pinnt die Spalte an die linke Seite.
- `PinDirection.RIGHT`: Pinnt die Spalte an die rechte Seite.
- `PinDirection.AUTO`: Die Spalte erscheint basierend auf der Einfüge-Reihenfolge.

Das Anheften kann programmatisch festgelegt werden, sodass Sie die Anheirichtung basierend auf Benutzerinteraktionen oder der Logik der App ändern können.

## Spaltengröße <DocChip chip='since' label='25.03' /> {#column-sizing} 

### Feste Breite {#fixed-width}

Legen Sie eine genaue Breite für eine Spalte mit der Methode `setWidth()` fest, indem Sie die gewünschte Breite in Pixeln angeben:

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

Die Breiteigenschaft definiert die gewünschte Anfangsbreite für die Spalte. Wie diese Breite verwendet wird, hängt von anderen Eigenschaften und dem Spalentyp ab:

- **Reguläre Spalten**: Bei nur festgelegter Breite rendert die Spalte in der angegebenen Breite, kann aber proportional schrumpfen, wenn der Container zu klein ist. Die ursprüngliche Breite dient als gewünschte Breite, doch ohne ausdrückliche Mindestbeschränkungen kann die Spalte kleiner gerendert werden als die festgelegte Breite.
- [**Angeheftete Spalten**](#pinning): Behalten immer ihre exakte Breite und nehmen nicht am responsiven Schrumpfen teil.
- [**Flex-Spalten**](#flex-sizing): Die Angabe der Breite ist mit Flex unvereinbar. Verwenden Sie entweder Breite (fest) oder Flex (proportional), nicht beides.

Wenn nicht angegeben, verwendet die Spalte ihre geschätzte Breite basierend auf der Inhaltsanalyse der ersten paar Zeilen.

```java
// Aktuelle Breite abrufen
float currentWidth = column.getWidth();
```

### Minimale Breite {#minimum-width}

Die Methode `setMinWidth()` ermöglicht es Ihnen, die minimale Breite einer Spalte zu definieren. Wenn die minimale Breite nicht angegeben wird, berechnet die `Table` die minimale Breite basierend auf dem Spalteninhalt.

```java
table.addColumn("Preis", Product::getPrice).setMinWidth(100f);
```

Der übergebene Wert steht für die minimale Breite in Pixeln.

Die Mindestbreiteigenschaft kontrolliert die kleinste Breite, die eine Spalte haben kann:

- **Reguläre Spalten**: Bei nur festgelegter Mindestbreite verwendet die Spalte die Mindestbreite als sowohl gewünschte als auch Mindestbreite. Bei Breite + Mindestbreite kann die Spalte von der Breite auf die Mindestbreite, aber nicht weiter schrumpfen.
- [**Angeheftete Spalten**](#pinning): Wenn nur die Mindestbreite festgelegt ist (keine Breite), wird sie zur festen Breite.
- [**Flex-Spalten**](#flex-sizing): Verhindert, dass die Spalte unterhalb dieser Breite schrumpft, auch wenn der Platz im Container begrenzt ist.

```java
// Aktuelle minimale Breite abrufen
float minWidth = column.getMinWidth();
```

### Maximale Breite {#maximum-width}

Die Methode `setMaxWidth()` begrenzt, wie breit eine Spalte wachsen kann, um zu verhindern, dass Spalten mit langen Inhalten zu breit werden und die Lesbarkeit beeinträchtigen:

```java
table.addColumn("Beschreibung", Product::getDescription)
    .setMinWidth(100f)
    .setMaxWidth(300f);
```

Die Eigenschaft `maxWidth` begrenzt das Wachstum der Spalten für alle Spalentypen und wird niemals überschritten, unabhängig vom Inhalt, der Größe des Containers oder den Flex-Einstellungen.

```java
// Aktuelle maximale Breite abrufen
float maxWidth = column.getMaxWidth();
```

### Flex-Größe {#flex-sizing}

Die Methode `setFlex()` ermöglicht eine proportionale Spaltengröße, sodass Spalten den verfügbaren Platz teilen, nachdem die Spalten mit fester Breite zugewiesen wurden:

```java
// Die Titel-Spalte erhält die doppelte Fläche der Künstler-Spalte
table.addColumn("Titel", Product::getTitle).setFlex(2f);
table.addColumn("Künstler", Product::getArtist).setFlex(1f);
```

Wichtiges Verhalten bei Flex:

- **Flex-Wert**: Bestimmt den Anteil des verfügbaren Platzes. Eine Spalte mit flex=2 erhält die doppelte Fläche einer Spalte mit flex=1.
- **Unvereinbar mit Breite**: Kann nicht zusammen mit der Breite Eigenschaft verwendet werden. Wenn Flex größer als null ist, hat es Vorrang vor der Breiteinstellung.
- **Respektiert Einschränkungen**: Funktioniert mit den Mindestbreiten-/Höchstbreiteneinschränkungen. Ohne Mindestbreite können Flex-Spalten bis auf 0 schrumpfen.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnflexsizing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnFlexSizingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='550px'
/>
<!-- vale on -->

:::info Breite vs. Flex
Die Breiten- und Flex-Eigenschaften sind einander ausschließend. Das Festlegen einer von beiden entfernt automatisch die andere. Verwenden Sie die Breite für präzise Kontrolle oder Flex für responsives Verhalten.
:::

### Automatische Größenanpassung {#automatic-sizing}

Über manuelle Breiten- und Flexeinstellungen hinaus können Spalten auch automatisch dimensioniert werden. Die automatische Größenanpassung ermöglicht es der `Table`, optimale Breiten durch Analyse des Inhalts oder durch proportionale Verteilung des Raums zu bestimmen.

#### Inhaltsbasierte automatische Größenanpassung {#content-based-auto-sizing}

Spalten werden automatisch basierend auf ihrem Inhalt dimensioniert. Die `Table` analysiert die Daten in jeder Spalte und berechnet die optimale Breite, um den Inhalt ohne Truncation anzuzeigen.

```java
// Alle Spalten automatisch an den Inhalt anpassen
table.setColumnsToAutoSize().thenAccept(c -> {
    // Größenanpassung abgeschlossen - Spalten passen nun zu ihrem Inhalt
});

// Bestimmte Spalte automatisch anpassen
table.setColumnToAutoSize("beschreibung");
```

#### Proportionale automatische Anpassung {#proportional-auto-fit}

Verteilen Sie alle Spalten proportional über die verfügbare `Table`-Breite. Diese Operation setzt jede Spalte auf flex=1 und lässt sie die gesamte `Table`-Breite gleichmäßig teilen, unabhängig von ihrer Inhaltslänge. Die Spalten werden sich ausdehnen oder zusammenziehen, um die genauen Abmessungen der `Table` ohne verbleibenden Platz zu füllen.

```java
// Spalten an die Tabellenbreite anpassen (entspricht dem Setzen von flex=1 für alle)
table.setColumnsToAutoFit().thenAccept(ignored -> {
    // Alle Spalten teilen jetzt den Platz gleichmäßig
});
```

:::info Async-Operationen
Auto-Größenanpassungsmethoden geben `PendingResult<Void>` zurück, da sie clientseitige Berechnungen erfordern. Verwenden Sie `thenAccept()`, um Code auszuführen, nachdem die Größenanpassung abgeschlossen ist. Wenn Sie nicht auf den Abschluss warten müssen, können Sie die Methoden ohne `thenAccept()` aufrufen.
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

### Spaltenanpassung {#column-resizing}

Die Spaltenanpassung gibt Benutzern die Kontrolle darüber, wie viel Platz jede Spalte einnimmt, indem sie die Spaltenränder ziehen.

Sie können das Anpassungsverhalten für einzelne Spalten festlegen, wenn Sie Ihre Tabelle erstellen:

```java
// Benutzerspaltengröße für diese Spalte aktivieren
table.addColumn("Titel", Product::getTitle).setResizable(true);

// Größenanpassung deaktivieren
table.addColumn("ID", Product::getId).setResizable(false);

// Aktuellen Status überprüfen
boolean canResize = column.isResizable();
```

Für Tabellen, in denen Sie ein konsistentes Verhalten über mehrere Spalten hinweg wünschen, verwenden Sie die Gruppeneinstellungsmethoden:

```java
// Alle vorhandenen Spalten anpassbar machen
table.setColumnsToResizable(true);

// Alle vorhandenen Spalten von der Größenanpassung ausschließen
table.setColumnsToResizable(false);
```

### Spaltenneuanordnung {#column-reordering}

Die Spaltenneuanordnung ermöglicht es den Benutzern, Spalten per Drag & Drop in ihre bevorzugte Reihenfolge zu verschieben und das Layout der `Table` für ihren Workflow zu personalisieren.

Konfigurieren Sie die Berechtigungen für die Spaltenbewegung, wenn Sie Ihre Tabelle einrichten:

```java
// Benutzern erlauben, diese Spalte zu verschieben
table.addColumn("Titel", Product::getTitle).setMovable(true);

// Spaltenbewegung verhindern (nützlich für ID- oder Aktionsspalten)
table.addColumn("ID", Product::getId).setMovable(false);

// Aktuellen Status überprüfen
boolean canMove = column.isMovable();
```

Wenden Sie die Bewegungseinstellungen gleichzeitig auf mehrere Spalten an:

```java
// Neuanordnung für alle vorhandenen Spalten aktivieren
table.setColumnsToMovable(true);

// Neuanordnung für alle vorhandenen Spalten deaktivieren  
table.setColumnsToMovable(false);
```

:::note Gruppierungen von Operationen
Die Methoden `setColumnsToResizable()` und `setColumnsToMovable()` betreffen nur bestehende Spalten zum Zeitpunkt der Ausführung. Sie setzen keine Standards für zukünftige Spalten.
:::

### Programmgesteuerte Spaltenbewegung {#programmatic-column-movement} 

Neben Drag & Drop können Sie Spalten auch programmgesteuert nach Index oder ID neu positionieren. Beachten Sie, dass der Index nur auf sichtbare Spalten basiert; alle ausgeblendeten Spalten werden bei der Berechnung der Positionen ignoriert.

```java
// Spalte in die erste Position verschieben
table.moveColumn("titel", 0);

// Spalte in die letzte Position verschieben
table.moveColumn(titleColumn, table.getColumns().size() - 1);

// Asynchrone Bewegung mit Callback
table.moveColumn("beschreibung", 2).thenAccept(c -> {
    // Spalte erfolgreich verschoben
});
```

## Ereignisbehandlung {#event-handling}

Die `Table`-Komponente gibt Ereignisse aus, wenn Benutzer mit Spalten interagieren, sodass Sie auf Layoutänderungen reagieren und Benutzerpräferenzen speichern können.

Unterstützte Ereignisse:

- `TableColumnResizeEvent`: Wird ausgelöst, wenn ein Benutzer eine Spalte durch Ziehen ihrer Kante anpasst.
- `TableColumnMoveEvent`: Wird ausgelöst, wenn ein Benutzer eine Spalte durch Ziehen ihres Headers neu anordnet.

Sie können Listener an die `Table` anhängen, um zu reagieren, wenn Benutzer das Tabellenlayout ändern.

```java
Table<Product> table = new Table<>();

table.onColumnResize(event -> {
  // Ereignis für die Spaltenanpassung verarbeiten
  // Zugriff: event.getColumn(), event.getOldWidth(), event.getNewWidth()
});

table.onColumnMove(event -> {
  // Ereignis für die Spaltenbewegung verarbeiten  
  // Zugriff: event.getColumn(), event.getOldIndex(), event.getNewIndex()
});
```
