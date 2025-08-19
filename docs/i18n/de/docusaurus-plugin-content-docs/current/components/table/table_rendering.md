---
sidebar_position: 20
title: Rendering
slug: rendering
_i18n_hash: 9bf580ccd6532be7fc66e2825d083724
---
# Reichhaltige Inhalte und clientseitige Darstellung

Tabellen in webforJ sind ebenfalls konfigurierbar und nutzen die folgenden Werkzeuge, um reichhaltige Inhalte in den Zellen anzuzeigen. Dazu gehören interaktive Komponenten oder formatierte Daten innerhalb der Tabellenzellen.

Diese Elemente werden clientseitig gerendert, was bedeutet, dass der Prozess zur Erstellung und Anzeige von reichhaltigen Inhalten direkt im Browser erfolgt, unter Verwendung von JavaScript nur bei Bedarf, was die Leistung von Anwendungen, die die `Table` verwenden, erhöht.

## Lodash-Renderer {#lodash-renderers}

Renderer bieten einen leistungsstarken Mechanismus zur Anpassung der Art und Weise, wie Daten innerhalb einer `Table` angezeigt werden. Die Hauptklasse, `Renderer`, ist dafür konzipiert, erweitert zu werden, um benutzerdefinierte Renderer basierend auf Lodash-Vorlagen zu erstellen, die dynamische und interaktive Inhaltsdarstellung ermöglichen.

Lodash-Vorlagen ermöglichen das Einfügen von HTML direkt in die Tabellenzellen, was sie äußerst effektiv für das Rendern komplexer Zellendaten in einer `Table` macht. Dieser Ansatz ermöglicht die dynamische Generierung von HTML basierend auf Zellendaten und erleichtert damit reichhaltige und interaktive Inhalte in den Tabellenzellen.

### Lodash-Syntax {#lodash-syntax}

Der folgende Abschnitt skizziert die Grundlagen der Lodash-Syntax. Während dies keine umfassende Übersicht ist, kann sie als Ausgangspunkt zur Verwendung von Lodash innerhalb der `Table`-Komponente dienen.

#### Syntaxübersicht für Lodash-Vorlagen: {#syntax-overview-for-lodash-templates}

- `<%= ... %>` - Interpoliert Werte und fügt das Ergebnis des JavaScript-Codes in die Vorlage ein.
- `<% ... %>` - Führt JavaScript-Code aus, der Schleifen, Bedingungen und mehr zulässt.
- `<%- ... %>` - Entzieht HTML-Inhalte, um sicherzustellen, dass interpolierte Daten vor HTML-Injection-Angriffen geschützt sind.

#### Beispiele mit Zellendaten: {#examples-using-cell-data}

**1. Einfache Wertinterpolation**: Direkt den Wert der Zelle anzeigen.

`<%= cell.value %>`

**2. Bedingte Darstellung**: Verwenden Sie JavaScript-Logik, um Inhalte bedingt darzustellen.

`<% if (cell.value > 100) { %> 'Hoch' <% } else { %> 'Normal' <% } %>`

**3. Kombinieren von Datenfeldern**: Inhalte unter Verwendung mehrerer Datenfelder aus der Zelle darstellen.

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. HTML-Inhalte entziehen**: Sicheres Rendern von benutzergenerierten Inhalten.

Der Renderer hat Zugriff auf detaillierte Zell-, Zeilen- und Spalteneigenschaften auf der Clientseite:

**TableCell-Eigenschaften:**

|Eigenschaft	|Typ	|Beschreibung|
|-|-|-|
|column|`TableColumn`|Das zugehörige Spaltenobjekt.|
|first|`boolean`|Gibt an, ob die Zelle die erste in der Zeile ist.|
|id|`String`|Die Zell-ID.|
|index|`int`|Der Index der Zelle innerhalb ihrer Zeile.|
|last|`boolean`|Gibt an, ob die Zelle die letzte in der Zeile ist.|
|row|`TableRow`|Das zugehörige Zeilenobjekt für die Zelle.|
|value|`Object`|Der Rohwert der Zelle, direkt aus der Datenquelle.|

**TableRow-Eigenschaften:**

|Eigenschaft|Typ|Beschreibung|
|-|-|-|
|cells|`TableCell[]`|Die Zellen innerhalb der Zeile.|
|data|`Object`|Die vom Anwender bereitgestellten Daten für die Zeile.|
|even|`boolean`|Gibt an, ob die Zeile eine gerade Zahl hat (zu Styling-Zwecken).|
|first|`boolean`|Gibt an, ob die Zeile die erste in der Tabelle ist.|
|id|`String`|Eindeutige ID für die Zeile.|
|index|`int`|Der Zeilenindex.|
|last|`boolean`|Gibt an, ob die Zeile die letzte in der Tabelle ist.|
|odd|`boolean`|Gibt an, ob die Zeile eine ungerade Zahl hat (zu Styling-Zwecken).|

**TableColumn-Eigenschaften:**

|Eigenschaft	|Typ	|Beschreibung|
|-|-|-|
|align|ColumnAlignment|Die Ausrichtung der Spalte (links, Mitte, rechts).|
|id|String|Das Feld des Zeilenobjekts, aus dem die Zellendaten abgerufen werden.|
|label|String|Der Name, der im Spaltenkopf angezeigt werden soll.|
|pinned|ColumnPinDirection|Die Fixierrichtung der Spalte (links, rechts, automatisch).|
|sortable|boolean|Wenn wahr, kann die Spalte sortiert werden.|
|sort|SortDirection|Die Sortierreihenfolge der Spalte.|
|type|ColumnType|Der Typ der Spalte (Text, Zahl, Boolean usw.).|
|minWidth|number|Die minimale Breite der Spalte in Pixeln.|

## Verfügbare Renderer {#available-renderers}

Während benutzerdefinierte Renderer erstellt werden können, stehen mehrere vorkonfigurierte Renderer zur Verwendung innerhalb einer `Table` zur Verfügung. Die folgenden sind für Entwickler sofort einsatzbereit, ohne dass ein benutzerdefinierter Renderer erstellt werden muss:

>- `ButtonRenderer` - Renderer für einen WebforJ-Button.
>- `NativeButtonRenderer` - Renderer für einen nativen HTML-Button.
>- `ElementRenderer` - Die Basisklasse für alle Renderer, die ein HTML-Tag **mit** Inhalt rendern.
>- `VoidElementRenderer` - Die Basisklasse für alle Renderer, die ein leeres Element oder ein HTML-Tag **ohne** Inhalt rendern.
>- `IconRenderer` - Renderer für ein Icon - **[siehe dies](../../components/icon)** Artikel für weitere Informationen zu Icons.

Renderer erlauben es, auch benutzerdefinierte Ereignisse zu schreiben, indem jeder der unterstützten Basisrenderer erweitert wird. Derzeit kommen Renderer mit einem `RendererClickEvent`, das von Entwicklern genutzt werden kann.

Im Folgenden finden Sie ein Beispiel für eine `Table`, die Renderer verwendet, um reichhaltige Inhalte darzustellen:

<ComponentDemo 
path='/webforj/tablerichcontent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRichContentView.java'
cssURL='/css/table/tableRichContent.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
