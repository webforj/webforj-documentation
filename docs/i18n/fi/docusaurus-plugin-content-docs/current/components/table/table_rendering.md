---
sidebar_position: 20
title: Rendering
slug: rendering
_i18n_hash: 9bf580ccd6532be7fc66e2825d083724
---
# Runsas sisältö ja asiakaspään renderointi

Taulukot webforJ:ssä ovat myös konfiguroitavissa käyttäen seuraavia työkaluja runsaan sisällön esittämiseen soluissa. Tämä sisältää interaktiiviset komponentit tai muotoillut tiedot taulukon soluissa.

Nämä elementit renderoidaan asiakaspään, mikä tarkoittaa, että runsaan sisällön luonti ja esittäminen tapahtuu suoraan selaimessa, käyttäen JavaScriptiä vain tarvittaessa, mikä parantaa sovellusten suorituskykyä käyttäen `Table`.

## Lodash-renderöijät {#lodash-renderers}

Renderöijät tarjoavat tehokkaan mekanismin tietojen näyttämisen mukauttamiseen `Table`-komponentissa. Pääluokka, `Renderer`, on suunniteltu laajennettavaksi mukautettujen renderöijien luomiseksi lodash-malleihin perustuen, mahdollistaen dynaamisen ja interaktiivisen sisällön renderoinnin.

Lodash-mallit mahdollistavat HTML:n suoran upottamisen taulukon soluihin, mikä tekee niistä erittäin tehokkaita monimutkaisten solutietojen renderoimiseen `Table`-komponentissa. Tämä lähestymistapa sallii HTML:n dynaamisen luomisen solutiedon perusteella, helpottaen runsasta ja interaktiivista taulukonsisältöä.

### Lodash-syntaksi {#lodash-syntax}

Seuraava osio kuvaa Lodash-syntaksin perusteet. Vaikka tämä ei ole kattava tai ytimekäs esitys, sitä voidaan käyttää Lodashin aloittamiseen `Table`-komponentissa.

#### Syntaksin yleiskatsaus lodash-malleille: {#syntax-overview-for-lodash-templates}

- `<%= ... %>` - Interpoloi arvoja, upottaen JavaScript-koodin tuloksen malliin.
- `<% ... %>` - Suorittaa JavaScript-koodia, mahdollistaa silmukat, ehtolauseet ja muut.
- `<%- ... %>` - Pakottaa HTML-sisällön, varmistaen, että interpoloidut tiedot ovat suojassa HTML-injektiohyökkäyksiltä.

#### Esimerkkejä solutiedoista: {#examples-using-cell-data}

**1. Yksinkertainen arvon interpolointi**: Näyttää suoraan solun arvon.

`<%= cell.value %>`

**2. Ehtopohjainen renderointi**: Käyttää JavaScript-logiikkaa sisällön ehtopohjaiseen renderointiin.

`<% if (cell.value > 100) { %> 'Korkea' <% } else { %> 'Normaali' <% } %>`

**3. Tietokenttien yhdistäminen**: Renderoi sisältöä käyttäen useita tietokenttiä solusta.

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. HTML-sisällön pakottaminen**: Renderoi turvallisesti käyttäjän luomaa sisältöä.

Renderöijällä on pääsy yksityiskohtaisiin solu-, rivi- ja sarakeominaisuuksiin asiakaspäässä:

**TaulukonSolun Ominaisuudet:**

|Ominaisuus	|Tyyppi	|Kuvaus|
|-|-|-|
|column|`TableColumn`|Liitetty sarakeobjekti.|
|first|`boolean`|Ilmaisee, onko solu ensimmäinen rivissä.|
|id|`String`|Solun ID.|
|index|`int`|Solun indeksi sen rivissä.|
|last|`boolean`|Ilmaisee, onko solu viimeinen rivissä.|
|row|`TableRow`|Liitetty riviobjekti solulle.|
|value|`Object`|Solun raaka arvo, suoraan tietolähteestä.|

**TaulukonRivin Ominaisuudet:**

|Ominaisuus|Tyyppi|Kuvaus|
|-|-|-|
|cells|`TableCell[]`|Rivin sisällä olevat solut.
|data|`Object`|Sovelluksen antamat tiedot riville.
|even|`boolean`|Ilmaisee, onko rivi parillinen (tyylitarkoituksiin).
|first|`boolean`|Ilmaisee, onko rivi ensimmäinen taulukossa.
|id|`String`|Rivin ainutlaatuinen ID.
|index|`int`|Rivin indeksi.
|last|`boolean`|Ilmaisee, onko rivi viimeinen taulukossa.
|odd|`boolean`|Ilmaisee, onko rivi pariton (tyylitarkoituksiin).

**TaulukonSarake Ominaisuudet:**

|Ominaisuus	|Tyyppi	|Kuvaus|
|-|-|-|
|align|ColumnAlignment|Sarakkeen tasaus (vasen, keskellä, oikea).
|id|String|Rivillä olevan objektin kenttä, josta saadaan solun tiedot.
|label|String|Nimi, joka näytetään sarakkeen otsikossa.
|pinned|ColumnPinDirection|Sarakkeen kiinnityssuunta (vasen, oikea, automaattinen).
|sortable|boolean|Jos tosi, sarake voidaan lajitella.
|sort|SortDirection|Sarakkeen lajittelujärjestys.
|type|ColumnType|Sarakkeen tyyppi (teksti, numero, boolean jne.).
|minWidth|number|Sarakkeen vähimmäisleveys pikseleinä.

## Saatavilla olevat renderöijät {#available-renderers}

Vaikka mukautettuja renderöijä voidaan luoda, on useita valmiita renderöijöitä, jotka ovat käytettävissä `Table`-komponentin sisällä. Seuraavat ovat saatavilla kehittäjille käytettäväksi suoraan ilman tarvetta luoda mukautettua renderöijää:

>- `ButtonRenderer` - Renderöijä webforJ-napille.
>- `NativeButtonRenderer` - Renderöijä natiiville HTML-napille.
>- `ElementRenderer` - Perusluokka kaikille renderöijille, jotka renderöivät HTML-tagin **sisällön kanssa**.
>- `VoidElementRenderer` - Perusluokka kaikille renderöijille, jotka renderöivät tyhjää elementtiä, tai HTML-tagin **ilman** sisältöä.
>- `IconRenderer` - Renderöijä ikonille - **[katso tämä](../../components/icon)** artikkeli saadaksesi lisätietoja ikoneista.

Renderöijät sallivat myös mukautettujen tapahtumien kirjoittamisen laajentamalla mitä tahansa tuettua perus renderöijää. Tällä hetkellä renderöijät tulevat mukana `RendererClickEvent` -tapahtumalla, jota kehittäjät voivat käyttää.

Alla on esimerkki `Table`-komponentista, joka käyttää renderöijöitä näyttämään runsasta sisältöä:

<ComponentDemo 
path='/webforj/tablerichcontent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRichContentView.java'
cssURL='/css/table/tableRichContent.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
