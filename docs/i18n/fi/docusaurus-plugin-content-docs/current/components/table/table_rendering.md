---
sidebar_position: 20
title: Rendering
slug: rendering
_i18n_hash: 12791f1583592c6f000a45b74e52ba65
---
# Rikas sisältö ja asiakaspuolen renderointi

Taulukot webforJ:ssa ovat myös konfiguroitavissa seuraavien työkalujen avulla, jotka näyttävät rikasta sisältöä soluissa. Tämä sisältää interaktiivisia komponentteja tai muotoiltua tietoa taulukon soluissa.

Nämä elementit renderöidään asiakaspuolella, mikä tarkoittaa, että rikkaan sisällön luonti ja näyttäminen tapahtuu suoraan selaimessa, käyttäen JavaScriptiä vain tarpeen mukaan, mikä parantaa sovellusten suorituskykyä, jotka käyttävät `Table`-komponenttia.

## Lodash-renderöijät {#lodash-renderers}

Renderöijät tarjoavat tehokkaan mekanismin mukauttaa tapaa, jolla tiedot näytetään `Table`:ssa. Pääluokka, `Renderer`, on suunniteltu laajennettavaksi mukautettujen renderöijien luomiseksi lodash-malleihin perustuen, mahdollistaen dynaamisen ja interaktiivisen sisällön renderoinnin.

Lodash-mallit mahdollistavat HTML:n suoran sijoittamisen taulukon soluihin, mikä tekee niistä erittäin tehokkaita monimutkaisen soludatan renderoinnissa `Table`:ssa. Tämä lähestymistapa mahdollistaa HTML:n dynaamisen luomisen solutiedon perusteella, helpottaen rikasta ja interaktiivista taulukon solusisältöä.

### Lodash-syntaksi {#lodash-syntax}

Seuraava osio käsittelee Lodash-syntaksin perusteita. Vaikka tämä ei ole tyhjentävä tai kattava katsaus, se voi auttaa Lodashin käyttämisessä `Table`-komponentissa.

#### Syntaksikatsaus lodash-malleille: {#syntax-overview-for-lodash-templates}

- `<%= ... %>` - Interpoloi arvot, sijoittaen JavaScript-koodin tuloksen malliin.
- `<% ... %>` - Suorittaa JavaScript-koodia, mahdollistamalla silmukat, ehtolauseet, ja enemmän.
- `<%- ... %>` - Pakkaa HTML-sisällön, varmistaen, että interpoloitu data on suojattu HTML-injektiohyökkäyksiltä.

#### Esimerkkejä soludatasta: {#examples-using-cell-data}

**1. Yksinkertainen arvon interpolointi**: Näytä suoraan solun arvo.

`<%= cell.value %>`

**2. Ehdollinen renderointi**: Käytä JavaScript-logiikkaa sisällön ehdolliseen renderointiin.

`<% if (cell.value > 100) { %> 'Korkea' <% } else { %> 'Normaali' <% } %>`

**3. Tietokenttien yhdistäminen**: Renderoi sisältöä käyttämällä useita tietokenttiä solusta.

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. HTML-sisällön pakkaaminen**: Renderoi turvallisesti käyttäjän luomaa sisältöä.

Renderöijällä on pääsy yksityiskohtaisiin solun, rivin ja sarakkeen ominaisuuksiin asiakaspuolella:

**Taulukon solun ominaisuudet:**

|Ominaisuus	|Tyyppi	|Kuvaus|
|-|-|-|
|column|`TableColumn`|Liitetty sarakeobjekti.|
|first|`boolean`|Ilmaisee, onko solu rivin ensimmäinen.|
|id|`String`|Solun ID.|
|index|`int`|Solun indeksi rivissään.|
|last|`boolean`|Ilmaisee, onko solu rivin viimeinen.|
|row|`TableRow`|Liitetty riviobjekti solulle.|
|value|`Object`|Solun raakatieto suoraan tietolähteestä.|

**Taulukon rivin ominaisuudet:**

|Ominaisuus|Tyyppi|Kuvaus|
|-|-|-|
|cells|`TableCell[]`|Rivin sisällä olevat solut.|
|data|`Object`|Sovelluksen tarjoama data riville.|
|even|`boolean`|Ilmaisee, onko rivi parillinen (tyylitarkoituksia varten).|
|first|`boolean`|Ilmaisee, onko rivi taulukon ensimmäinen.|
|id|`String`|Rivin ainutlaatuinen ID.|
|index|`int`|Rivin indeksi.|
|last|`boolean`|Ilmaisee, onko rivi taulukon viimeinen.|
|odd|`boolean`|Ilmaisee, onko rivi pariton (tyylitarkoituksia varten).|

**Taulukon sarakkeen ominaisuudet:**

|Ominaisuus	|Tyyppi	|Kuvaus|
|-|-|-|
|align|ColumnAlignment|Sarakkeen tasaus (vasen, keski, oikea).|
|id|String|Riviodasta haettavan solun datan kenttä.|
|label|String|Nimi, joka renderöidään sarakeotsikossa.|
|pinned|ColumnPinDirection|Sarakkeen kiinnityssuunta (vasen, oikea, automaattinen).|
|sortable|boolean|Jos tosi, saraketta voidaan järjestää.|
|sort|SortDirection|Sarakkeen järjestys.|
|type|ColumnType|Sarakkeen tyyppi (teksti, numero, boolean jne.).|
|minWidth|number|Sarakkeen vähimmäisleveys pikseleinä.

## Saatavilla olevat renderöijät {#available-renderers}

Vaikka mukautettuja renderöijöitä voidaan luoda, käytettävissä on useita esikonfiguroituja renderöijöitä, joita voidaan käyttää `Table`:ssa. Seuraavat ovat saatavilla kehittäjien käyttöön ilman tarvetta luoda mukautettua renderöijää:

>- `ButtonRenderer` - Renderöijä webforJ-painikkeelle.
>- `NativeButtonRenderer` - Renderöijä natiiville HTML-painikkeelle.
>- `ElementRenderer` - Perusluokka kaikille renderöijille, jotka renderöivät HTML-tagin **sisällön kanssa**.
>- `VoidElementRenderer` - Perusluokka kaikille renderöijille, jotka renderöivät tyhjää elementtiä tai HTML-tagia **ilman** sisältöä.
>- `IconRenderer` - Renderöijä ikonille - **[katso tämä](../../components/icon)** artikkeli lisätietoja varten ikoista.

Renderöijät mahdollistavat mukautettujen tapahtumien kirjoittamisen myös laajentamalla mitä tahansa tuettua perusrenderöijää. Tällä hetkellä renderöijät tarjoavat `RendererClickEvent`-tapahtuman kehittäjien käyttöön.

Alla on esimerkki `Table`:sta, joka käyttää renderöijöitä näyttämään rikasta sisältöä:

<ComponentDemo 
path='/webforj/tablerichcontent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRichContentView.java'
cssURL='/css/table/tableRichContent.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
