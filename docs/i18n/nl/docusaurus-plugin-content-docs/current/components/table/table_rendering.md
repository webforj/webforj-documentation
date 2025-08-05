---
sidebar_position: 20
title: Rendering
slug: rendering
_i18n_hash: 12791f1583592c6f000a45b74e52ba65
---
# Rijke inhoud en client-side rendering

Tabellen in webforJ zijn ook configureerbaar met behulp van de volgende tools om rijke inhoud binnen cellen weer te geven. Dit omvat interactieve componenten of geformatteerde gegevens binnen de tabelcellen.

Deze elementen worden client-side gerenderd, wat betekent dat het proces van het genereren en weergeven van rijke inhoud direct in de browser gebeurt, waarbij JavaScript alleen wordt gebruikt wanneer dat nodig is, wat de prestaties van toepassingen die de `Table` gebruiken, verbetert.

## Lodash renderers {#lodash-renderers}

Renderers bieden een krachtige mechanismen om de manier waarop gegevens binnen een `Table` worden weergegeven aan te passen. De primaire klasse, `Renderer`, is ontworpen om te worden uitgebreid om aangepaste renderers te creëren op basis van lodash-sjablonen, waardoor dynamische en interactieve inhoud rendering mogelijk is.

Lodash-sjablonen maken het mogelijk om HTML direct in tabelcellen in te voegen, waardoor ze zeer effectief zijn voor het weergeven van complexe celgegevens in een `Table`. Deze aanpak maakt dynamische generatie van HTML op basis van celgegevens mogelijk, wat zorgt voor rijke en interactieve inhoud in tabelcellen.

### Lodash-syntaxis {#lodash-syntax}

De volgende sectie schetst de basisprincipes van Lodash-syntaxis. Hoewel dit geen uitputtende of uitgebreide overzicht is, kan het worden gebruikt als startpunt voor het gebruik van Lodash binnen de `Table`-component.

#### Syntaxisoverzicht voor lodash-sjablonen: {#syntax-overview-for-lodash-templates}

- `<%= ... %>` - Interpoleert waarden, waarbij het resultaat van de JavaScript-code in de sjabloon wordt ingevoegd.
- `<% ... %>` - Voert JavaScript-code uit, waardoor lussen, voorwaardelijke logica en meer mogelijk zijn.
- `<%- ... %>` - Escapet HTML-inhoud, waardoor ervoor wordt gezorgd dat geïnterpoleerde gegevens veilig zijn tegen HTML-injectie-aanvallen.

#### Voorbeelden met celgegevens: {#examples-using-cell-data}

**1. Eenvoudige waarde-interpolatie**: Toon direct de waarde van de cel.

`<%= cell.value %>`

**2. Voorwaardelijke rendering**: Gebruik JavaScript-logica om inhoud voorwaardelijk weer te geven.

`<% if (cell.value > 100) { %> 'Hoog' <% } else { %> 'Normaal' <% } %>`

**3. Gecombineerde gegevensvelden**: Render inhoud met meerdere gegevensvelden uit de cel.

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. Escapen van HTML-inhoud**: Veilige weergave van door gebruikers gegenereerde inhoud.

De renderer heeft toegang tot gedetailleerde cel-, rij- en kolom-eigenschappen aan de clientzijde:

**Tabelcel-eigenschappen:**

|Eigenschap	|Type	|Beschrijving|
|-|-|-|
|column|`TableColumn`|Het bijbehorende kolomobject.|
|first|`boolean`|Geeft aan of de cel de eerste in de rij is.|
|id|`String`|De cel-ID.|
|index|`int`|De index van de cel binnen zijn rij.|
|last|`boolean`|Geeft aan of de cel de laatste in de rij is.|
|row|`TableRow`|Het bijbehorende rijobject voor de cel.|
|value|`Object`|De rauwe waarde van de cel, rechtstreeks van de gegevensbron.|

**Tabelrij-eigenschappen:**

|Eigenschap|Type|Beschrijving|
|-|-|-|
|cells|`TableCell[]`|De cellen binnen de rij.|
|data|`Object`|De gegevens die door de toepassing voor de rij zijn opgegeven.|
|even|`boolean`|Geeft aan of de rij een even nummer is (voor stijlen).|
|first|`boolean`|Geeft aan of de rij de eerste in de tabel is.|
|id|`String`|Unieke ID voor de rij.|
|index|`int`|De rij-index.|
|last|`boolean`|Geeft aan of de rij de laatste in de tabel is.|
|odd|`boolean`|Geeft aan of de rij een oneven nummer is (voor stijlen).|

**Tabelkolom-eigenschappen:**

|Eigenschap	|Type	|Beschrijving|
|-|-|-|
|align|ColumnAlignment|De uitlijning van de kolom (links, midden, rechts).|
|id|String|Het veld van het rijobject waarvan de gegevens voor de cel moeten komen.|
|label|String|De naam die in de kolomkop moet worden weergegeven.|
|pinned|ColumnPinDirection|De pinrichting van de kolom (links, rechts, automatisch).|
|sortable|boolean|Indien waar, kan de kolom gesorteerd worden.|
|sort|SortDirection|De sorteervolgorde van de kolom.|
|type|ColumnType|Het type van de kolom (tekst, nummer, boolean, enz.).|
|minWidth|number|De minimale breedte van de kolom in pixels.|

## Beschikbare renderers {#available-renderers}

Hoewel aangepaste renderers kunnen worden gemaakt, zijn er meerdere vooraf geconfigureerde renderers beschikbaar voor gebruik binnen een `Table`. De volgende zijn beschikbaar voor ontwikkelaars om direct te gebruiken zonder een aangepaste renderer te creëren:

>- `ButtonRenderer` - Renderer voor een webforJ-knop.
>- `NativeButtonRenderer` - Renderer voor een native HTML-knop.
>- `ElementRenderer` - De basisklasse voor alle renderers die een HTML-tag **met** inhoud renderen.
>- `VoidElementRenderer` - De basisklasse voor alle renderers die een void-element of een HTML-tag **zonder** inhoud renderen.
>- `IconRenderer` - Renderer voor een pictogram - **[zie dit](../../components/icon)** artikel voor meer informatie over pictogrammen.

Renderers maken het ook mogelijk om aangepaste evenementen te schrijven door een van de ondersteunde basisrenderers uit te breiden. Momenteel komen renderers met een `RendererClickEvent` die beschikbaar is voor gebruik door ontwikkelaars.

Hieronder staat een voorbeeld van een `Table` die renderers gebruikt om rijke inhoud weer te geven:

<ComponentDemo 
path='/webforj/tablerichcontent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRichContentView.java'
cssURL='/css/table/tableRichContent.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
