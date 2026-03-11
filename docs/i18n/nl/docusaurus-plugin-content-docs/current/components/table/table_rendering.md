---
sidebar_position: 20
title: Rendering
slug: rendering
_i18n_hash: e536c2f1d5c965ed0f8ee139e38ee0f7
---
# Rijke inhoud en client-side rendering

Tabellen in webforJ zijn ook configureerbaar met behulp van de volgende tools om rijke inhoud binnen cellen weer te geven. Dit omvat interactieve componenten of geformatteerde gegevens binnen de tabelcellen.

Deze elementen worden client-side gerenderd, wat betekent dat het genereren en weergeven van rijke inhoud direct in de browser gebeurt, waarbij JavaScript alleen wanneer nodig wordt gebruikt, waardoor de prestaties van applicaties die de `Table` gebruiken, verhoogd worden.

## Lodash-renderers {#lodash-renderers}

Renderers bieden een krachtige mechanismen voor het aanpassen van de manier waarop gegevens binnen een `Table` worden weergegeven. De primaire klasse, `Renderer`, is ontworpen om te worden uitgebreid om aangepaste renderers te maken op basis van lodash-sjablonen, wat dynamische en interactieve inhoud rendering mogelijk maakt.

Lodash-sjablonen maken de invoeging van HTML direct in tabelcellen mogelijk, waardoor ze zeer effectief zijn voor het weergeven van complexe celgegevens in een `Table`. Deze aanpak maakt het mogelijk om HTML dynamisch te genereren op basis van celgegevens, wat rijke en interactieve inhoud van tabelcellen vergemakkelijkt.

### Lodash-syntaxis {#lodash-syntax}

De volgende sectie beschrijft de basisprincipes van Lodash-syntaxis. Hoewel dit geen uitputtend of uitgebreid overzicht is, kan het worden gebruikt om te helpen met het beginnen met Lodash binnen de `Table`-component.

#### Overzicht van de syntaxis voor lodash-sjablonen: {#syntax-overview-for-lodash-templates}

- `<%= ... %>` - Interpoleert waarden, en voegt het resultaat van de JavaScript-code in de sjabloon in.
- `<% ... %>` - Voert JavaScript-code uit, en staat loops, conditionals, en meer toe.
- `<%- ... %>` - Escapet HTML-inhoud, zodat geïnterpoleerde gegevens veilig zijn voor HTML-injectieaanvallen.

#### Voorbeelden met celgegevens: {#examples-using-cell-data}

**1. Eenvoudige waarde-interpolatie**: geef direct de waarde van de cel weer.

`<%= cell.value %>`

**2. Voorwaardelijke rendering**: gebruik JavaScript-logica om inhoud voorwaardelijk weer te geven.

`<% if (cell.value > 100) { %> 'Hoog' <% } else { %> 'Normaal' <% } %>`

**3. Gegevensvelden combineren**: geef inhoud weer met behulp van meerdere gegevensvelden uit de cel.

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. HTML-inhoud escapen**: veilig gebruikersgegenereerde inhoud weergeven.

De renderer heeft toegang tot gedetailleerde eigenschappen van cellen, rijen en kolommen aan de clientzijde:

**TableCell-eigenschappen:**

|Eigenschap	|Type	|Beschrijving|
|-|-|-|
|column|`TableColumn`|Het bijbehorende kolomobject.|
|first|`boolean`|Geeft aan of de cel de eerste in de rij is.|
|id|`String`|De cel-ID.|
|index|`int`|De index van de cel binnen zijn rij.|
|last|`boolean`|Geeft aan of de cel de laatste in de rij is.|
|row|`TableRow`|Het bijbehorende rijobject voor de cel.|
|value|`Object`|De ruwe waarde van de cel, direct van de gegevensbron.|

**TableRow-eigenschappen:**

|Eigenschap|Type|Beschrijving|
|-|-|-|
|cells|`TableCell[]`|De cellen binnen de rij.|
|data|`Object`|De gegevens die door de applicatie voor de rij zijn opgegeven.|
|even|`boolean`|Geeft aan of de rij even genummerd is (voor stijl doeleinden).|
|first|`boolean`|Geeft aan of de rij de eerste in de tabel is.|
|id|`String`|Unieke ID voor de rij.|
|index|`int`|De rij-index.|
|last|`boolean`|Geeft aan of de rij de laatste in de tabel is.|
|odd|`boolean`|Geeft aan of de rij oneven genummerd is (voor stijl doeleinden).|

**TableColumn-eigenschappen:**

|Eigenschap	|Type	|Beschrijving|
|-|-|-|
|align|ColumnAlignment|De uitlijning van de kolom (links, midden, rechts).|
|id|String|Het veld van het rijobject om de gegevens van de cel uit te halen.|
|label|String|De naam die in de kolomkop moet worden weergegeven.|
|pinned|ColumnPinDirection|De vastlegrichting van de kolom (links, rechts, automatisch).|
|sortable|boolean|Als dit waar is, kan de kolom worden gesorteerd.|
|sort|SortDirection|De sorteervolgorde van de kolom.|
|type|ColumnType|Het type van de kolom (tekst, nummer, boolean, etc.).|
|minWidth|number|De minimale breedte van de kolom in pixels.|

## Beschikbare renderers {#available-renderers}

Hoewel aangepaste renderers kunnen worden gemaakt, zijn er verschillende vooraf geconfigureerde renderers beschikbaar voor gebruik binnen een `Table`. De volgende zijn beschikbaar voor ontwikkelaars om direct te gebruiken zonder dat een aangepaste renderer hoeft te worden gemaakt:

>- `ButtonRenderer` - Renderer voor een webforJ-knop.
>- `NativeButtonRenderer` - Renderer voor een native HTML-knop.
>- `ElementRenderer` - De basis klasse voor alle renderers die een HTML-tag **met** inhoud renderen.
>- `VoidElementRenderer` - De basis klasse voor alle renderers die een void element, of een HTML-tag **zonder** inhoud renderen.
>- `IconRenderer` - Renderer voor een icoon - **[zie dit](../../components/icon)** artikel voor meer informatie over iconen.

Renderers stellen ook in staat om aangepaste evenementen te schrijven door een van de ondersteunde basisrenderers uit te breiden. Momenteel worden renderers geleverd met een `RendererClickEvent` dat beschikbaar is voor gebruik door ontwikkelaars.

Hieronder staat een voorbeeld van een `Table` die renderers gebruikt om rijke inhoud weer te geven:

<ComponentDemo 
path='/webforj/tablerichcontent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRichContentView.java'
cssURL='/css/table/tableRichContent.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
