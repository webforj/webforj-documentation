---
sidebar_position: 20
title: Rendering
slug: rendering
_i18n_hash: e536c2f1d5c965ed0f8ee139e38ee0f7
---
# Reichhaltiger Inhalt und clientseitiges Rendering

Tabellen in webforJ sind ebenfalls konfigurierbar und nutzen die folgenden Tools, um reichhaltigen Inhalt innerhalb der Zellen darzustellen. Dazu gehören interaktive Komponenten oder formatierte Daten innerhalb der Tabellenzellen.

Diese Elemente werden clientseitig gerendert, was bedeutet, dass der Prozess der Generierung und Anzeige von reichhaltigem Inhalt direkt im Browser erfolgt, wobei JavaScript nur bei Bedarf verwendet wird, was die Leistung der Anwendungen, die die `Table` verwenden, erhöht.

## Lodash-Renderer {#lodash-renderers}

Renderer bieten einen leistungsstarken Mechanismus zur Anpassung der Art und Weise, wie Daten innerhalb einer `Table` angezeigt werden. Die Hauptklasse, `Renderer`, wurde entwickelt, um erweitert zu werden, um benutzerdefinierte Renderer basierend auf Lodash-Vorlagen zu erstellen, die dynamisches und interaktives Rendern von Inhalten ermöglichen.

Lodash-Vorlagen ermöglichen das Einfügen von HTML direkt in die Tabellenzellen, wodurch sie äußerst effektiv für das Rendern von komplexen Zellendaten in einer `Table` sind. Dieser Ansatz erlaubt die dynamische Generierung von HTML basierend auf Zellendaten und erleichtert reichhaltige und interaktive Inhalte in den Tabellenzellen.

### Lodash-Syntax {#lodash-syntax}

Der folgende Abschnitt beschreibt die Grundlagen der Lodash-Syntax. Während dies kein erschöpfender oder umfassender Überblick ist, kann er helfen, mit Lodash innerhalb der `Table`-Komponente zu beginnen.

#### Syntaxübersicht für Lodash-Vorlagen: {#syntax-overview-for-lodash-templates}

- `<%= ... %>` - Interpoliert Werte und fügt das Ergebnis des JavaScript-Codes in die Vorlage ein.
- `<% ... %>` - Führt JavaScript-Code aus, was Schleifen, Bedingungen und mehr ermöglicht.
- `<%- ... %>` - Entkommt HTML-Inhalten und stellt sicher, dass interpolierte Daten vor HTML-Injection-Angriffen geschützt sind.

#### Beispiele mit Zellendaten: {#examples-using-cell-data}

**1. Einfache Wertinterpolation**: direkt den Wert der Zelle anzeigen.

`<%= cell.value %>`

**2. Bedingtes Rendering**: verwenden Sie JavaScript-Logik, um Inhalte bedingt darzustellen.

`<% if (cell.value > 100) { %> 'Hoch' <% } else { %> 'Normal' <% } %>`

**3. Kombinieren von Datenfeldern**: Inhalte unter Verwendung mehrerer Datenfelder aus der Zelle rendern.

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. HTML-Inhalte entkommen**: Benutzergenerierte Inhalte sicher rendern.

Der Renderer hat Zugriff auf detaillierte Eigenschaften von Zelle, Zeile und Spalte auf der Clientseite:

**Eigenschaften von TableCell:**

|Eigenschaft	|Typ	|Beschreibung|
|-|-|-|
|column|`TableColumn`|Das zugehörige Spaltenobjekt.|
|first|`boolean`|Gibt an, ob die Zelle die erste in der Zeile ist.|
|id|`String`|Die Zell-ID.|
|index|`int`|Der Index der Zelle innerhalb ihrer Zeile.|
|last|`boolean`|Gibt an, ob die Zelle die letzte in der Zeile ist.|
|row|`TableRow`|Das zugehörige Zeilenobjekt für die Zelle.|
|value|`Object`|Der rohe Wert der Zelle, direkt aus der Datenquelle.|

**Eigenschaften von TableRow:**

|Eigenschaft|Typ|Beschreibung|
|-|-|-|
|cells|`TableCell[]`|Die Zellen innerhalb der Zeile.|
|data|`Object`|Die vom Anwendung bereitgestellten Daten für die Zeile.|
|even|`boolean`|Gibt an, ob die Zeile eine gerade Nummer hat (für Styling-Zwecke).|
|first|`boolean`|Gibt an, ob die Zeile die erste in der Tabelle ist.|
|id|`String`|Eindeutige ID für die Zeile.|
|index|`int`|Den Zeilenindex.|
|last|`boolean`|Gibt an, ob die Zeile die letzte in der Tabelle ist.|
|odd|`boolean`|Gibt an, ob die Zeile eine ungerade Nummer hat (für Styling-Zwecke).|

**Eigenschaften von TableColumn:**

|Eigenschaft	|Typ	|Beschreibung|
|-|-|-|
|align|ColumnAlignment|Die Ausrichtung der Spalte (links, Mitte, rechts).|
|id|String|Das Feld des Zeilenobjekts, um die Daten der Zelle abzurufen.|
|label|String|Der Name, der im Spaltenkopf gerendert wird.|
|pinned|ColumnPinDirection|Die Anheftungsrichtung der Spalte (links, rechts, automatisch).|
|sortable|boolean|Wenn wahr, kann die Spalte sortiert werden.|
|sort|SortDirection|Die Sortierreihenfolge der Spalte.|
|type|ColumnType|Der Typ der Spalte (Text, Zahl, Boolean usw.).|
|minWidth|number|Die Mindestbreite der Spalte in Pixeln.|

## Verfügbare Renderer {#available-renderers}

Während benutzerdefinierte Renderer erstellt werden können, stehen mehrere vorkonfigurierte Renderer zur Verfügung, die innerhalb einer `Table` verwendet werden können. Die folgenden sind für Entwickler ohne die Notwendigkeit, einen benutzerdefinierten Renderer zu erstellen, sofort einsatzbereit:

>- `ButtonRenderer` - Renderer für eine webforJ-Schaltfläche.
>- `NativeButtonRenderer` - Renderer für eine native HTML-Schaltfläche.
>- `ElementRenderer` - Die Basisklasse für alle Renderer, die ein HTML-Tag **mit** Inhalt rendern.
>- `VoidElementRenderer` - Die Basisklasse für alle Renderer, die ein leeres Element oder ein HTML-Tag **ohne** Inhalt rendern.
>- `IconRenderer` - Renderer für ein Icon - **[siehe diesen](../../components/icon)** Artikel für weitere Informationen zu Icons.

Renderer erlauben es auch, benutzerdefinierte Ereignisse zu schreiben, indem jeder unterstützte Basisklassen-Renderer erweitert wird. Derzeit bieten Renderer ein `RendererClickEvent`, das von Entwicklern verwendet werden kann.

Im Folgenden finden Sie ein Beispiel für eine `Table`, die Renderer verwendet, um reichhaltigen Inhalt anzuzeigen:

<ComponentDemo 
path='/webforj/tablerichcontent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRichContentView.java'
cssURL='/css/table/tableRichContent.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
