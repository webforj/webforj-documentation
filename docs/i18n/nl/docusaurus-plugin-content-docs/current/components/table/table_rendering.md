---
sidebar_position: 20
title: Rendering
slug: rendering
_i18n_hash: 9bf580ccd6532be7fc66e2825d083724
---
# Rijke inhoud en client-side rendering

Tabellen in webforJ zijn ook configureerbaar met behulp van de volgende tools om rijke inhoud binnen de cellen weer te geven. Dit omvat interactieve componenten of geformatteerde gegevens binnen de tabelcellen.

Deze elementen worden client-side weergegeven, wat betekent dat het proces van het genereren en weergeven van rijke inhoud rechtstreeks in de browser gebeurt, waarbij JavaScript alleen wanneer nodig wordt gebruikt, wat de prestaties van applicaties die de `Table` gebruiken verhoogt.

## Lodash renderers {#lodash-renderers}

Renderers bieden een krachtig mechanisme voor het aanpassen van de manier waarop gegevens binnen een `Table` worden weergegeven. De primaire klasse, `Renderer`, is ontworpen om uitgebreid te worden om aangepaste renderers op basis van lodash-sjablonen te maken, waardoor dynamische en interactieve inhoud kan worden weergegeven.

Lodash-sjablonen maken het mogelijk om HTML rechtstreeks in tabelcellen in te voegen, waardoor ze uiterst effectief zijn voor het weergeven van complexe celgegevens in een `Table`. Deze benadering maakt de dynamische generatie van HTML op basis van celgegevens mogelijk, waardoor rijke en interactieve tabelcelinhoud wordt vergemakkelijkt.

### Lodash-syntaxis {#lodash-syntax}

De volgende sectie schetst de basisprincipes van de Lodash-syntaxis. Hoewel dit geen uitputtend of volledig overzicht is, kan het helpen om te beginnen met het gebruik van Lodash binnen de `Table`-component.

#### Syntaxisoverzicht voor lodash-sjablonen: {#syntax-overview-for-lodash-templates}

- `<%= ... %>` - Interpoleert waarden en voegt het resultaat van de JavaScript-code in het sjabloon in.
- `<% ... %>` - Voert JavaScript-code uit, waardoor lussen, voorwaardelijke logica en meer mogelijk zijn.
- `<%- ... %>` - Escapet HTML-inhoud, waardoor ervoor wordt gezorgd dat geïnterpoleerde gegevens veilig zijn voor HTML-injectie-aanvallen.

#### Voorbeelden met celgegevens: {#examples-using-cell-data}

**1. Eenvoudige waarde-interpolatie**: Geef direct de waarde van de cel weer.

`<%= cell.value %>`

**2. Voorwaardelijke weergave**: Gebruik JavaScript-logica om inhoud voorwaardelijk weer te geven.

`<% if (cell.value > 100) { %> 'Hoog' <% } else { %> 'Normaal' <% } %>`

**3. Gegevensvelden combineren**: Geef inhoud weer met behulp van meerdere gegevensvelden uit de cel.

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. HTML-inhoud escapen**: Weergave van door gebruikers gegenereerde inhoud op een veilige manier.

De renderer heeft toegang tot gedetailleerde eigenschappen van cel, rij en kolom aan de clientzijde:

**TableCell-eigenschappen:**

| Eigenschap    | Type               | Beschrijving                                    |
|---------------|--------------------|-------------------------------------------------|
| column        | `TableColumn`      | Het bijbehorende kolomobject.                  |
| first         | `boolean`          | Geeft aan of de cel de eerste in de rij is.     |
| id            | `String`           | De cel-ID.                                     |
| index         | `int`              | De index van de cel binnen zijn rij.            |
| last          | `boolean`          | Geeft aan of de cel de laatste in de rij is.    |
| row           | `TableRow`        | Het bijbehorende rijobject voor de cel.        |
| value         | `Object`           | De ruwe waarde van de cel, rechtstreeks uit de gegevensbron. |

**TableRow-eigenschappen:**

| Eigenschap    | Type               | Beschrijving                                    |
|---------------|--------------------|-------------------------------------------------|
| cells         | `TableCell[]`      | De cellen binnen de rij.                        |
| data          | `Object`           | De gegevens die door de applicatie voor de rij zijn opgegeven. |
| even          | `boolean`          | Geeft aan of de rij een even nummer is (voor stylering). |
| first         | `boolean`          | Geeft aan of de rij de eerste in de tabel is.  |
| id            | `String`           | Unieke ID voor de rij.                          |
| index         | `int`              | De rij-index.                                   |
| last          | `boolean`          | Geeft aan of de rij de laatste in de tabel is. |
| odd           | `boolean`          | Geeft aan of de rij een oneven nummer is (voor stylering). |

**TableColumn-eigenschappen:**

| Eigenschap    | Type               | Beschrijving                                    |
|---------------|--------------------|-------------------------------------------------|
| align         | ColumnAlignment     | De uitlijning van de kolom (links, centrum, rechts). |
| id            | String             | Het veld van het rijobject om de gegevens van de cel op te halen. |
| label         | String             | De naam die in de kolomkop moet worden weergegeven. |
| pinned        | ColumnPinDirection  | De pinrichting van de kolom (links, rechts, automatisch). |
| sortable      | boolean            | Als deze waar is, kan de kolom worden gesorteerd. |
| sort          | SortDirection       | De sorteervolgorde van de kolom.               |
| type          | ColumnType         | Het type van de kolom (tekst, nummer, boolean, enz.). |
| minWidth      | number             | De minimale breedte van de kolom in pixels.    |

## Beschikbare renderers {#available-renderers}

Hoewel aangepaste renderers kunnen worden gemaakt, zijn er meerdere vooraf geconfigureerde renderers beschikbaar voor gebruik binnen een `Table`. De volgende zijn beschikbaar voor ontwikkelaars om direct te gebruiken zonder dat een aangepaste renderer hoeft te worden gemaakt:

>- `ButtonRenderer` - Renderer voor een webforJ-knop.
>- `NativeButtonRenderer` - Renderer voor een native HTML-knop.
>- `ElementRenderer` - De basisklasse voor alle renderers die een HTML-tag **met** inhoud renderen.
>- `VoidElementRenderer` - De basisklasse voor alle renderers die een void-element renderen, of een HTML-tag **zonder** inhoud.
>- `IconRenderer` - Renderer voor een pictogram - **[zie dit](../../components/icon)** artikel voor meer informatie over pictogrammen.

Renderers stellen het ook in staat om aangepaste evenementen te schrijven door een van de ondersteunde basrenderers uit te breiden. Momenteel worden renderers geleverd met een `RendererClickEvent` die beschikbaar is voor gebruik door ontwikkelaars.

Hieronder staat een voorbeeld van een `Table` die renderers gebruikt om rijke inhoud weer te geven:

<ComponentDemo 
path='/webforj/tablerichcontent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRichContentView.java'
cssURL='/css/table/tableRichContent.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
