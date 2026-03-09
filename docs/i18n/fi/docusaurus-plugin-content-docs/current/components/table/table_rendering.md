---
sidebar_position: 20
title: Rendering
slug: rendering
_i18n_hash: e536c2f1d5c965ed0f8ee139e38ee0f7
---
# Rikas sisältö ja asiakassivun renderointi

Taulukot webforJ:ssä ovat myös konfiguroitavissa käyttämällä seuraavia työkaluja rikkaan sisällön näyttämiseen soluissa. Tämä sisältää interaktiivisia komponentteja tai muotoiltuja tietoja taulukon soluissa.

Nämä elementit renderöidään asiakassivulla, mikä tarkoittaa, että rikkaan sisällön tuottaminen ja näyttäminen tapahtuu suoraan selaimessa, käyttäen JavaScriptiä vain tarvittaessa, mikä parantaa sovellusten suorituskykyä käyttäen `Table`.

## Lodash-renderoijat {#lodash-renderers}

Renderoijat tarjoavat tehokkaan mekanismin tietojen näyttämisen mukauttamiseksi `Table`:ssa. Pääluokka, `Renderer`, on suunniteltu laajennettavaksi mukautettujen renderoijien luomiseksi lodash-malleihin perustuen, mahdollistamalla dynaamisen ja interaktiivisen sisällön renderoinnin.

Lodash-mallit mahdollistavat HTML:n suoran lisäämisen taulukon soluihin, jolloin ne ovat erittäin tehokkaita monimutkaisten solutietojen renderoimisessa `Table`:ssa. Tämä lähestymistapa mahdollistaa HTML:n dynaamisen tuottamisen solutietojen perusteella, helpottaen rikasta ja interaktiivista taulukon solu- sisältöä.

### Lodash-syntaksi {#lodash-syntax}

Tässä osiossa kuvataan Lodash-syntaksin perusteet. Vaikka tämä ei ole kattava tai täydellinen yleiskatsaus, sitä voidaan käyttää auttamaan Lodashin käyttämisessä `Table`-komponentissa.

#### Syntaksin yleiskuvaus lodash-malleille: {#syntax-overview-for-lodash-templates}

- `<%= ... %>` - Interpoloi arvoja, liittämällä JavaScript-koodin tuloksen malliin.
- `<% ... %>` - Suorittaa JavaScript-koodia, sallien silmukoita, ehtoja ja muuta.
- `<%- ... %>` - Pakenee HTML-sisältöä, varmistaen, että palautettu data on suojattu HTML-injektiohyökkäyksiltä.

#### Esimerkit solutietojen käytöstä: {#examples-using-cell-data}

**1. Yksinkertainen arvon interpolointi**: näytä suoraan solun arvo.

`<%= cell.value %>`

**2. Ehtoiseen renderointiin**: käytä JavaScript-logiikkaa sisällön ehdoittamiseen.

`<% if (cell.value > 100) { %> 'Korkea' <% } else { %> 'Normaali' <% } %>`

**3. Datan kenttien yhdistäminen**: renderoi sisältö käyttäen useita datakenttiä solusta.

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. HTML-sisällön pakkaaminen**: renderoi turvallisesti käyttäjien tuottamaa sisältöä.

Renderoijalla on pääsy yksityiskohtaisiin solun, rivin ja sarakkeen ominaisuuksiin asiakaspinnassa:

**TableCell-ominaisuudet:**

| Ominaisuus | Tyyppi | Kuvaus |
|------------|--------|--------|
| column     | `TableColumn` | Liitetty sarakeobjekti. |
| first      | `boolean` | Ilmaisee, onko solu ensimmäinen rivillä. |
| id         | `String` | Solun tunnus. |
| index      | `int` | Solun indeksi sen rivillä. |
| last       | `boolean` | Ilmaisee, onko solu viimeinen rivillä. |
| row        | `TableRow` | Liitetty riviobjekti solulle. |
| value      | `Object` | Solun raakatieto, suoraan tietolähteestä. |

**TableRow-ominaisuudet:**

| Ominaisuus | Tyyppi | Kuvaus |
|------------|--------|--------|
| cells      | `TableCell[]` | Rivillä olevat solut. |
| data       | `Object` | Sovelluksen antama data riville. |
| even       | `boolean` | Ilmaisee, onko rivi parittomalla numerolla (tyylitarkoituksia varten). |
| first      | `boolean` | Ilmaisee, onko rivi ensimmäinen taulukossa. |
| id         | `String` | Rivin ainutlaatuinen tunnus. |
| index      | `int` | Rivin indeksi. |
| last       | `boolean` | Ilmaisee, onko rivi viimeinen taulukossa. |
| odd        | `boolean` | Ilmaisee, onko rivi parillinen numerolta (tyylitarkoituksia varten). |

**TableColumn-ominaisuudet:**

| Ominaisuus | Tyyppi | Kuvaus |
|------------|--------|--------|
| align      | ColumnAlignment | Sarakkeen kohdistus (vasen, keski, oikea). |
| id         | String | Kenttä rivin objektista, josta saadaan solun data. |
| label      | String | Nimi, joka renderöidään sarakkeen otsikossa. |
| pinned     | ColumnPinDirection | Sarakkeen kiinnityssuunta (vasen, oikea, automaattinen). |
| sortable    | boolean | Jos tosi, saraketta voidaan lajitella. |
| sort       | SortDirection | Sarakkeen lajittelujärjestys. |
| type       | ColumnType | Sarakkeen tyyppi (teksti, numero, boolean jne.). |
| minWidth   | number | Sarakkeen vähimmäisleveys pikseleinä. |

## Saatavilla olevat renderoijat {#available-renderers}

Vaikka mukautettuja renderoijia voidaan luoda, on useita esikonfiguroituja renderoijia käytettävissä `Table`:ssä. Seuraavat ovat käytettävissä kehittäjille ilman tarvetta luoda mukautettua renderoijaa:

>- `ButtonRenderer` - Renderoija webforJ -napille.
>- `NativeButtonRenderer` - Renderoija natiiville HTML-napille.
>- `ElementRenderer` - Perusluokka kaikille renderoijille, jotka renderoivat HTML-tagin **sisältöineen**.
>- `VoidElementRenderer` - Perusluokka kaikille renderoijille, jotka renderoivat tyhjää elementtiä tai HTML-tagia **ilman** sisältöä.
>- `IconRenderer` - Renderoija ikonille - **[katso tämä](../../components/icon)** artikkeli lisätietoja varten ikoneista.

Renderoijat mahdollistavat myös mukautettujen tapahtumien kirjoittamisen laajentamalla mitä tahansa tuettua perusrenderoijaa. Tällä hetkellä renderoijat sisältävät `RendererClickEvent` kehittäjien käytettäväksi.

Alla on esimerkki `Table`:sta, joka käyttää renderoijia rikkaan sisällön näyttämiseksi:

<ComponentDemo 
path='/webforj/tablerichcontent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRichContentView.java'
cssURL='/css/table/tableRichContent.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
