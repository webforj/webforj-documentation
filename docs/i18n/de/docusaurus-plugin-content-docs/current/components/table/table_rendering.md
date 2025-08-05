---
sidebar_position: 20
title: Rendering
slug: rendering
_i18n_hash: 12791f1583592c6f000a45b74e52ba65
---
# Reichhaltige Inhalte und clientseitiges Rendering

Tabellen in webforJ sind ebenfalls konfigurierbar und nutzen die folgenden Werkzeuge, um reichhaltige Inhalte innerhalb von Zellen anzuzeigen. Dazu gehören interaktive Komponenten oder formatierte Daten innerhalb der Tabellenzellen.

Diese Elemente werden clientseitig gerendert, was bedeutet, dass der Prozess der Erstellung und Anzeige reichhaltiger Inhalte direkt im Browser erfolgt, wobei JavaScript nur nach Bedarf verwendet wird, um die Leistung von Anwendungen, die die `Table` verwenden, zu steigern.

## Lodash-Renderer {#lodash-renderers}

Renderer bieten einen leistungsstarken Mechanismus zur Anpassung der Art und Weise, wie Daten innerhalb einer `Table` angezeigt werden. Die Hauptklasse `Renderer` ist so konzipiert, dass sie erweitert werden kann, um benutzerdefinierte Renderer basierend auf Lodash-Vorlagen zu erstellen, die eine dynamische und interaktive Inhaltsdarstellung ermöglichen.

Lodash-Vorlagen ermöglichen das Einfügen von HTML direkt in Tabellenzellen, was sie äußerst effektiv für das Rendern komplexer Zellendaten in einer `Table` macht. Dieser Ansatz ermöglicht die dynamische Generierung von HTML basierend auf Zellendaten und erleichtert reichhaltige und interaktive Inhalte in Tabellenzellen.

### Lodash-Syntax {#lodash-syntax}

Der folgende Abschnitt skizziert die Grundlagen der Lodash-Syntax. Während dies keine umfassende Übersicht ist, kann es verwendet werden, um mit Lodash innerhalb der `Table`-Komponente zu beginnen.

#### Syntaxübersicht für Lodash-Vorlagen: {#syntax-overview-for-lodash-templates}

- `<%= ... %>` - Interpoliert Werte und fügt das Ergebnis des JavaScript-Codes in die Vorlage ein.
- `<% ... %>` - Führt JavaScript-Code aus und ermöglicht Schleifen, Bedingungen und mehr.
- `<%- ... %>` - Escaped HTML-Inhalte und stellt sicher, dass interpolierte Daten vor HTML-Injection-Angriffen sicher sind.

#### Beispiele mit Zellendaten: {#examples-using-cell-data}

**1. Einfache Wertinterpolation**: Direkt den Wert der Zelle anzeigen.

`<%= cell.value %>`

**2. Bedingtes Rendering**: JavaScript-Logik verwenden, um Inhalte bedingt darzustellen.

`<% if (cell.value > 100) { %> 'Hoch' <% } else { %> 'Normal' <% } %>`

**3. Kombination von Datenfeldern**: Inhalte unter Verwendung mehrerer Datenfelder aus der Zelle rendern.

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. HTML-Inhalte escapen**: Benutzerdefinierte Inhalte sicher rendern.

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
|data|`Object`|Die vom Anwendung für die Zeile bereitgestellten Daten.|
|even|`boolean`|Gibt an, ob die Zeile eine gerade Nummer hat (aus stylischen Gründen).|
|first|`boolean`|Gibt an, ob die Zeile die erste in der Tabelle ist.|
|id|`String`|Eindeutige ID für die Zeile.|
|index|`int`|Der Zeilenindex.|
|last|`boolean`|Gibt an, ob die Zeile die letzte in der Tabelle ist.|
|odd|`boolean`|Gibt an, ob die Zeile eine ungerade Nummer hat (aus stylischen Gründen).|

**TableColumn-Eigenschaften:**

|Eigenschaft	|Typ	|Beschreibung|
|-|-|-|
|align|ColumnAlignment|Die Ausrichtung der Spalte (links, zentriert, rechts).|
|id|String|Das Feld des Zeilenobjekts, um die Daten der Zelle abzurufen.|
|label|String|Der Name, der in der Spaltenüberschrift angezeigt wird.|
|pinned|ColumnPinDirection|Die Fixierungsrichtung der Spalte (links, rechts, automatisch).|
|sortable|boolean|Wenn wahr, kann die Spalte sortiert werden.|
|sort|SortDirection|Die Sortierreihenfolge der Spalte.|
|type|ColumnType|Der Typ der Spalte (Text, Zahl, Boolean usw.).|
|minWidth|number|Die minimalen Breite der Spalte in Pixeln.|

## Verfügbare Renderer {#available-renderers}

Während benutzerdefinierte Renderer erstellt werden können, gibt es mehrere vorkonfigurierte Renderer, die innerhalb einer `Table` verwendet werden können. Die folgenden stehen Entwicklern sofort zur Verfügung, ohne dass ein benutzerdefinierter Renderer erstellt werden muss:

>- `ButtonRenderer` - Renderer für eine webforJ-Schaltfläche.
>- `NativeButtonRenderer` - Renderer für eine native HTML-Schaltfläche.
>- `ElementRenderer` - Die Basisklasse für alle Renderer, die ein HTML-Tag **mit** Inhalt rendern.
>- `VoidElementRenderer` - Die Basisklasse für alle Renderer, die ein leeres Element oder ein HTML-Tag **ohne** Inhalt rendern.
>- `IconRenderer` - Renderer für ein Symbol - **[siehe dazu diesen](../../components/icon)** Artikel für weitere Informationen zu Symbolen.

Renderer ermöglichen es auch, benutzerdefinierte Ereignisse zu schreiben, indem sie eine der unterstützten Basisklassen-Renderer erweitern. Derzeit kommen Renderer mit einem `RendererClickEvent`, das von Entwicklern genutzt werden kann.

Nachfolgend ein Beispiel für eine `Table`, die Renderer verwendet, um reichhaltige Inhalte anzuzeigen:

<ComponentDemo 
path='/webforj/tablerichcontent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRichContentView.java'
cssURL='/css/table/tableRichContent.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
