---
sidebar_position: 5
title: Columns
slug: columns
_i18n_hash: 19fe294c57ad6b7d105039c25aedab11
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

Die `Table`-Klasse verwendet Spalteninstanzen, um zu definieren und anzupassen, wie Daten angezeigt werden. Spalten steuern, welche Daten angezeigt werden, wie sie aussehen und wie Benutzer mit ihnen interagieren können. Diese Seite behandelt die Identität von Spalten, deren Präsentation, Größe, Benutzerinteraktionen und verwandte Ereignisse.

## Identität der Spalte {#column-identity}

Die Identität einer Spalte definiert, wie sie in der `Table` erkannt wird. Dazu gehören ihr Label, der Wert, den sie bereitstellt, und ob sie sichtbar oder navigierbar ist.

### Label {#label}

Das Label einer Spalte ist ihr öffentliches Identifikationsmerkmal, das hilft, die angezeigten Daten zu klären.  

Verwenden Sie `setLabel()`, um das Label festzulegen oder zu ändern.

:::tip
Standardmäßig wird das Label dasselbe wie die Spalten-ID sein.
:::

```java
table.addColumn("Produkt-ID", Product::getProductId).setLabel("ID");
```

### Wertanbieter {#value-providers}

Ein Wertanbieter ist eine Funktion, die dafür verantwortlich ist, Rohdaten aus dem zugrunde liegenden Datensatz in ein Format zu übersetzen, das für die Anzeige in einer bestimmten Spalte geeignet ist. Die Funktion, die Sie definieren, nimmt eine Instanz des Zeilendatentyps (T) und gibt den Wert zurück, der in der zugehörigen Spalte für diese bestimmte Zeile angezeigt werden soll.

Um einen Wertanbieter für eine Spalte festzulegen, verwenden Sie eine der `addColumn()`-Methoden der `Table`-Komponente.

Im folgenden Snippet versucht eine Spalte, Daten aus einem JSON-Objekt zuzugreifen und rendert diese nur, wenn die Daten nicht null sind.

```java
List<String> columnsList = List.of("athlet", "alter", "land", "jahr", "sport", "gold", "silber", "bronze", "gesamt");
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

Es ist möglich, die Sichtbarkeit einer Spalte festzulegen, um zu bestimmen, ob sie innerhalb der `Table` angezeigt wird. Dies kann nützlich sein, wenn unter anderem entschieden werden muss, ob sensible Informationen angezeigt werden sollen. 

```java
table.addColumn("Kreditkarte", Customer::getCreditCardNumber).setHidden(true);
```

### Navigierbar {#navigable}

Das Attribut navigierbar bestimmt, ob Benutzer während der Navigation mit einer Spalte interagieren können. Wenn `setSuppressNavigable()` auf true gesetzt wird, wird die Benutzerinteraktion mit der Spalte eingeschränkt, was eine schreibgeschützte Erfahrung bietet.

```java
table.addColumn("Schreibgeschützte Spalte", Product::getDescription).setSuppressNavigable(true);
```

## Layout und Formatierung {#layout-and-formatting}

Nachdem die Identität einer Spalte festgelegt wurde, besteht der nächste Schritt darin, zu steuern, wie ihr Inhalt den Benutzern angezeigt wird. Layoutoptionen wie Ausrichtung und Anheften bestimmen, wo die Daten sitzen und wie sie sichtbar bleiben, während Sie mit einer `Table` arbeiten.

### Ausrichtung {#alignment}

Die Ausrichtung einer Spalte festzulegen, ermöglicht es Ihnen, organisierte Tabellen zu erstellen, die den Benutzern helfen können, die verschiedenen Abschnitte in der `Table` zu identifizieren.

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

- `Column.Alignment.LEFT`: Geeignet für textuelle oder beschreibende Daten, bei denen eine linksseitige Ausrichtung intuitiv ist. Nützlich, wenn der Ausgangspunkt des Inhalts hervorgehoben werden soll.
- `Column.Alignment.CENTER`: Zentriert ausgerichtete Spalten sind ideal für kürzere Werte, wie einen Zeichenschlüssel, Status oder alles andere mit einer ausgewogenen Darstellung.
- `Column.Alignment.RIGHT`: Bei numerischen Werten, die hilfreich sind, um schnell gescannt zu werden, wie Daten, Beträge und Prozentsätze, kann eine rechtsbündige Spalte in Betracht gezogen werden.

Im vorhergehenden Beispiel wurde die letzte Spalte für `Kosten` rechtsbündig ausgerichtet, um eine deutlichere visuelle Unterscheidung zu bieten.

### Anheften {#pinning}

Das Anheften von Spalten ist eine Funktion, die es Benutzern ermöglicht, eine Spalte an eine bestimmte Seite der `Table` anzupinnen oder "anzufixieren". Dies ist nützlich, wenn bestimmte Spalten, wie Identifikatoren oder wesentliche Informationen, sichtbar bleiben müssen, während Sie horizontal durch eine Tabelle scrollen.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnpinning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

Es gibt drei mögliche Richtungen für das Anheften einer Spalte:

- `PinDirection.LEFT`: Haftet die Spalte an die linke Seite.
- `PinDirection.RIGHT`: Haftet die Spalte an die rechte Seite.
- `PinDirection.AUTO`: Die Spalte erscheint basierend auf der Einfügeordnung.

Das Anheften kann programmatisch festgelegt werden, sodass Sie die Anheitrichtung basierend auf Benutzerinteraktionen oder durch die Logik der App ändern können.

## Spaltengrößen <DocChip chip='since' label='25.03' /> {#column-sizing} 

### Feste Breite {#fixed-width}

Legen Sie eine genaue Breite für eine Spalte mit der `setWidth()`-Methode fest, indem Sie die gewünschte Breite in Pixeln angeben:

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

Die Breite-Eigenschaft definiert die gewünschte Anfangsbreite für die Spalte. Wie diese Breite verwendet wird, hängt von anderen Eigenschaften und dem Spaltentyp ab:

- **Reguläre Spalten**: Bei nur festgelegter Breite wird die Spalte mit der angegebenen Breite gerendert, kann jedoch proportional schrumpfen, wenn der Container zu klein ist. Die ursprüngliche Breite dient als die gewünschte Breite, aber ohne explizite Mindestbeschränkungen kann die Spalte kleiner als die festgelegte Breite gerendert werden.
- [**Angeheftete Spalten**](#pinning): Behalten immer ihre genaue Breite bei und nehmen niemals an einer responsiven Schrumpfung teil.
- [**Flex-Spalten**](#flex-sizing): Die Breite festzulegen, ist inkompatibel mit Flex. Verwenden Sie entweder die Breite (fest) oder Flex (proportional), nicht beides.

Wenn nicht angegeben, verwendet die Spalte ihre geschätzte Breite basierend auf der Analyse des Inhalts der ersten paar Zeilen.

```java
// Aktuelle Breite abrufen
float currentWidth = column.getWidth();
```

### Minimale Breite {#minimum-width}

Die `setMinWidth()`-Methode ermöglicht es Ihnen, die minimale Breite einer Spalte festzulegen. Wenn die minimale Breite nicht vorhanden ist, wird die `Table` die minimale Breite basierend auf dem Spalteninhalt berechnen.

```java
table.addColumn("Preis", Product::getPrice).setMinWidth(100f);
```

Der übergebene Wert repräsentiert die minimale Breite in Pixeln.

Die minimale Breite-Eigenschaft kontrolliert die kleinste Breite, die eine Spalte haben kann:

- **Reguläre Spalten**: Bei nur festgelegter minimaler Breite verwendet die Spalte die minimale Breite sowohl als gewünschte als auch als minimale Breite. Bei Breite + minimaler Breite kann die Spalte von der Breite bis zur minimalen Breite schrumpfen, jedoch nicht weiter.
- [**Angeheftete Spalten**](#pinning): Wenn nur die minimale Breite festgelegt ist (keine Breite), wird sie zur festen Breite.
- [**Flex-Spalten**](#flex-sizing): Verhindert, dass die Spalte unter diese Breite schrumpft, selbst wenn der Platz im Container begrenzt ist.

```java
// Aktuelle minimale Breite abrufen
float minWidth = column.getMinWidth();
```

### Maximale Breite {#maximum-width}

Die `setMaxWidth()`-Methode begrenzt, wie breit eine Spalte wachsen kann, um zu verhindern, dass Spalten mit langen Inhalten zu breit werden und die Lesbarkeit beeinträchtigen:

```java
table.addColumn("Beschreibung", Product::getDescription)
  .setMinWidth(100f)
  .setMaxWidth(300f);
```

Die `maxWidth`-Eigenschaft begrenzt das Wachstum der Spalte für alle Spaltentypen und wird nie überschritten, unabhängig vom Inhalt, der Containergröße oder den Flex-Einstellungen.

```java
// Aktuelle maximale Breite abrufen
float maxWidth = column.getMaxWidth();
```

### Flex-Größenanpassung {#flex-sizing}

Die `setFlex()`-Methode ermöglicht die proportionale Spaltengrößenanpassung, sodass Spalten den verfügbaren Platz teilen, nachdem die Spalten mit fester Breite zugewiesen wurden:

```java
// Die Titelsäule erhält den doppelten Raum der Künstlersäule
table.addColumn("Titel", Product::getTitle).setFlex(2f);
table.addColumn("Künstler", Product::getArtist).setFlex(1f);
```

Wichtige Flex-Verhalten:

- **Flex-Wert**: Bestimmt den Anteil des verfügbaren Raums. Eine Spalte mit flex=2 erhält den doppelten Raum einer Spalte mit flex=1.
- **Inkompatibel mit Breite**: Kann nicht zusammen mit der Breite-Eigenschaft verwendet werden. Wenn Flex größer als null ist, hat es Vorrang vor der Breite-Einstellung.
- **Berücksichtigt Einschränkungen**: Arbeitet mit den Mindestbreiten-/Höchstbreitenbeschränkungen. Ohne Mindestbreite können Flex-Spalten auf 0 schrumpfen.

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
Breiten- und Flex-Eigenschaften sind sich gegenseitig ausschließend. Das Festlegen einer wird automatisch die andere löschen. Verwenden Sie die Breite für präzise Kontrolle oder Flex für responsives Verhalten.
:::

### Automatische Größenanpassung {#automatic-sizing}

Neben manuellen Breiten- und Flex-Einstellungen können Spalten auch automatisch dimensioniert werden. Die automatische Größenanpassung lässt die `Table` optimale Breiten bestimmen, entweder durch Analyse des Inhalts oder durch proportionale Verteilung des Raums.

#### Inhaltbasierte automatische Größenanpassung {#content-based-auto-sizing}

Automatische Größenanpassung der Spalten basierend auf ihrem Inhalt. Die `Table` analysiert die Daten in jeder Spalte und berechnet die optimale Breite, um den Inhalt ohne Abschneiden darzustellen.

```java
// Alle Spalten automatisch anpassen, um zum Inhalt zu passen
table.setColumnsToAutoSize().thenAccept(c -> {
  // Größenanpassung abgeschlossen - Spalten passen jetzt zu ihrem Inhalt
});

// Bestimmte Spalte automatisch anpassen
table.setColumnToAutoSize("beschreibung");
```

#### Proportionale automatische Anpassung {#proportional-auto-fit}

Alle Spalten proportional über die verfügbare `Table`-Breite verteilen. Dieser Vorgang setzt jede Spalte auf flex=1, sodass sie die gesamte `Table`-Breite gleichermaßen teilen, unabhängig von der Länge ihres Inhalts. Spalten werden sich ausdehnen oder zusammenziehen, um die genauen Dimensionen der `Table` ohne verbleibenden Platz auszufüllen.

```java
// Spalten an die Tabellenbreite anpassen (entspricht dem Setzen von flex=1 auf alle)
table.setColumnsToAutoFit().thenAccept(ignored -> {
  // Alle Spalten teilen sich jetzt den Platz gleichmäßig
});
```

:::info Asynchrone Operationen
Automatische Größenanpassungsmethoden geben `PendingResult<Void>` zurück, da sie clientseitige Berechnungen erfordern. Verwenden Sie `thenAccept()`, um Code auszuführen, nachdem die Größenanpassung abgeschlossen ist. Wenn Sie nicht auf den Abschluss warten müssen, können Sie die Methoden auch ohne `thenAccept()` aufrufen.
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

Die Spaltenanpassung gibt Benutzern die Kontrolle über den Raum, den jede Spalte einnimmt, indem sie die Spaltenränder ziehen.

Sie können das Anpassungsverhalten für einzelne Spalten festlegen, wenn Sie Ihre Tabelle erstellen:

```java
// Benutzeranpassung für diese Spalte aktivieren
table.addColumn("Titel", Product::getTitle).setResizable(true);

// Anpassung deaktivieren
table.addColumn("ID", Product::getId).setResizable(false);

// Aktuellen Zustand überprüfen
boolean canResize = column.isResizable();
```

Für Tabellen, bei denen Sie konsequentes Verhalten über mehrere Spalten wünschen, verwenden Sie die Methoden zur Massenkonfiguration:

```java
// Alle vorhandenen Spalten anpassbar machen
table.setColumnsToResizable(true);

// Alle vorhandenen Spalten vom Anpassen abhalten
table.setColumnsToResizable(false);
```

### Spaltenreihenfolge {#column-reordering}

Die Spaltenreihenfolge ermöglicht es Benutzern, Spalten durch Ziehen und Ablegen in ihre bevorzugte Reihenfolge zu bringen, was das Layout der `Table` für ihren Workflow personalisiert.

Konfigurieren Sie die Erlaubnis zur Spaltenbewegung, wenn Sie Ihre Tabelle einrichten:

```java
// Benutzern erlauben, diese Spalte zu verschieben
table.addColumn("Titel", Product::getTitle).setMovable(true);

// Spaltenbewegungen verhindern (nützlich für ID- oder Aktionsspalten)
table.addColumn("ID", Product::getId).setMovable(false);

// Aktuellen Zustand überprüfen
boolean canMove = column.isMovable();
```

Anwendung der Bewegungseinstellungen auf mehrere Spalten gleichzeitig:

```java
// Reordering für alle vorhandenen Spalten aktivieren
table.setColumnsToMovable(true);

// Reordering für alle vorhandenen Spalten deaktivieren  
table.setColumnsToMovable(false);
```

:::note Massenoperationen
Die Methoden `setColumnsToResizable()` und `setColumnsToMovable()` betreffen nur vorhandene Spalten zum Zeitpunkt des Aufrufs. Sie setzen keine Standardwerte für zukünftige Spalten.
:::

### Programmatische Spaltenbewegung {#programmatic-column-movement} 

Zusätzlich zum Ziehen und Ablegen können Sie Spalten auch programmatisch nach Index oder ID repositionieren. Beachten Sie, dass der Index nur auf sichtbare Spalten basiert; alle versteckten Spalten werden bei der Berechnung der Positionen ignoriert.

```java
// Spalte an erste Position verschieben
table.moveColumn("titel", 0);

// Spalte an letzte Position verschieben
table.moveColumn(titleColumn, table.getColumns().size() - 1);

// Async-Bewegung mit Callback
table.moveColumn("beschreibung", 2).thenAccept(c -> {
  // Spalte erfolgreich verschoben
});
```

## Ereignisbehandlung {#event-handling}

Die `Table`-Komponente gibt Ereignisse aus, wenn Benutzer mit Spalten interagieren, sodass Sie auf Layoutänderungen reagieren und Benutzerpräferenzen speichern können.

Unterstützte Ereignisse:

- `TableColumnResizeEvent`: Wird ausgelöst, wenn ein Benutzer eine Spalte durch Ziehen ihrer Grenze anpasst.
- `TableColumnMoveEvent`: Wird ausgelöst, wenn ein Benutzer eine Spalte durch Ziehen ihrer Kopfzeile neu anordnet.

Sie können Listener an die `Table` anhängen, um zu reagieren, wenn Benutzer das Tabellenlayout ändern.

```java
Table<Product> table = new Table<>();

table.onColumnResize(event -> {
  // Ereignis zur Spaltenanpassung verarbeiten
  // Zugriff: event.getColumn(), event.getOldWidth(), event.getNewWidth()
});

table.onColumnMove(event -> {
  // Ereignis zur Spaltenbewegung verarbeiten  
  // Zugriff: event.getColumn(), event.getOldIndex(), event.getNewIndex()
});
```
