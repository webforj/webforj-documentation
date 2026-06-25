---
title: Navigator
sidebar_position: 75
_i18n_hash: db351d8f9fdf344a571d374e8d373f22
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-navigator" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="foundation" location="com/webforj/component/navigator/Navigator" top='true'/>

`Navigator`-komponentti lisää sivuohjausvaihtoehtoja tietoaineistojen tutkimiseen. Se voi näyttää ensimmäiset, viimeiset, seuraavat ja edelliset painikkeet yhdessä sivunumeroiden tai nopean hyppykentän kanssa, ja automaattisesti poistaa käytöstä ohjausvaihtoehdot, kun niitä ei voida käyttää. Se sitoo `Paginator`-instanssin hallitsemaan taustalla olevaa sivuohjauslogiikkaa.

<!-- INTRO_END -->

## Sidonta varastoihin {#binding-to-repositories}

Usein `Navigator`-komponentti näyttää tietoa, joka löytyy sidotusta `Repository`:sta. Tämä sidonta mahdollistaa `Navigator`:in automaattisen sivuohjauksen varaston hallinnoimasta datasta ja päivitykset muihin sidottaviin komponentteihin, kuten taulukoihin, perustuen navigoituun dataan.

Tätä varten siirrä vain haluttu `Repository`-objekti soveltuvan `Navigator`-olion konstruktoriin:

<ComponentDemo
path='/webforj/navigatortable'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorTableView.java']}
height='475px'
/>

Tässä esimerkissä luodaan `Navigator` ja [`Table`](table/overview) saman `Repository`-instanssin kanssa. Tämä tarkoittaa, että kun navigoidaan uuteen sivuun `Navigator`:in avulla, [`Table`](table/overview) tunnistaa tämän muutoksen ja renderoi sen uudelleen.

## Sivuohjaus {#pagination}

`Navigator`-komponentti on tiiviisti sidottu `Paginator`-malliluokkaan, joka laskee sivuohjaustiedot, kuten sivujen kokonaismäärä, nykyisen sivun alkupään/päätepisteet ja taulukon sivunumeroista navigointia varten.

Vaikka se ei ole suorastaan tarpeen, `Paginator`:in käyttäminen mahdollistaa navigoinnin logiikan. Integroitaessa `Paginator`:in kanssa, navigator reagoi kaikkiin muutoksiin `Paginator`:issa. `Navigator`-objekteilla on pääsy sisäänrakennettuun `Paginator`:iin käyttämällä `getPaginator()`-metodia. Se voi myös hyväksyä `Paginator`-instanssin `setPaginator()`-metodin kautta tai hyödyntämällä yhtä soveltuvista konstruktoreista.

Tässä osiossa on käytännön koodinpätkiä, jotka havainnollistavat, miten tämä integraatio toimii käytännössä.

### Kappaleet {#items}

Termi "kappaleet" tarkoittaa yksittäisiä sivutettuja elementtejä tai datakirjoituksia. Nämä voivat olla tietueita, merkintöjä tai mitä tahansa yksittäisiä yksiköitä tietoaineistossa. Voit asettaa kappaleiden kokonaismäärän käyttämällä `setTotalItems()`-metodia.

```java
navigator.getPaginator().setTotalItems(totalItems);
```

:::info
Varastoon liitetty `Paginator`-instanssi hallinnoi suoraan varastoituja kappaleiden kokonaismäärää ja sitä ei voida suoraan asettaa.
:::

### Maksimimäntä {#maximum-pages}

`setMax()`-metodi sallii sinun määrittää maksimimäärän sivulinkkejä, jotka näytetään sivuohjausnavigoinnissa. Tämä on erityisen hyödyllistä käsiteltäessä suurta sivumäärää, koska se hallitsee näkyvien sivulinkkien määrää käyttäjälle mihin aikaan tahansa.

```java
navigator.getPaginator().setMax(maxPages);
```

<ComponentDemo
path='/webforj/navigatorpages'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorPagesView.java']}
height='125px'
/>

Tämä ohjelma näyttää kerralla maksimissaan viisi sivua `Navigator`:issa käyttämällä `getPaginator()`-metodia `Navigator`-olion mukana olevan `Paginator`in hakemiseen ja sitten käyttäen `setMax()`-metodia määrittämään halutun maksimi sivumäärän näyttämiseksi.

### Sivukoko {#page-size}

`setSize()`-metodi sallii sinun määrittää näytettävien kappaleiden määrän jokaisella sivulla sivuohjauksessa. Kun kutsut tätä metodia ja annat uuden sivukoon, se säätää sivuohjauksen vastaavasti.

```java
navigator.getPaginator().setSize(pageSize);
```

## Painikkeiden, tekstin ja työkaluvihjeiden mukauttaminen {#customizing-buttons-text-and-tooltips}

`Navigator`-komponentti tarjoaa laajat mukautusmahdollisuudet painikkeiden, tekstin ja työkaluvihjeiden osalta. Muuttaaksesi näytettävää tekstiä `Navigator`-komponentissa, käytä `setText()`-metodia. Tämä metodi ottaa tekstin sekä halutun `Part`-osan `Navigator`:ista.

Seuraavassa esimerkissä `setText()`-metodi näyttää numeerisen arvon käyttäjälle. Painikkeiden napsauttaminen laukaisee `Navigator`:in `onChange`-metodin, jonka mukana on `Direction`-arvo napsautetusta painikkeesta.

<ComponentDemo
path='/webforj/navigatorbasic'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorBasicView.java']}
height='100px'
/>

### Painikkeet ja komponentin teksti {#buttons-and-component-text}

`setText()`-metodi arvioi tekstiparametrin JavaScript-lausekkeen avulla seuraavilla parametreilla:

- `page` - nykyinen sivunumero
- `current` - tällä hetkellä valitun sivun numero
- `x` - alias nykyiselle sivulle
- `startIndex` - nykyisen sivun alkupään indeksi.
- `endIndex` - nykyisen sivun päätepiste.
- `totalItems` - kappaleiden kokonaismäärä.
- `startPage` - alkusivunumero.
- `endPage` - päättymissivunumero.
- `component` - Navigator-asiakas komponentti.

<!-- vale off -->
Esimerkiksi, asettaaksesi viimeisen sivupainikkeen tekstin `Navigator`:issa, jossa on 10 sivua, "Siirry sivulle 10", käytä seuraavaa koodinpätkää: 
<!-- vale on -->

```java
navigator.setText("'Siirry sivulle ' + endPage", Navigator.Part.LAST_BUTTON);
```

### Työkaluvihjeen teksti {#tooltip-text}

Voit mukauttaa työkaluvihjeitä `Navigator`-komponentin eri osille käyttämällä `setTooltipText()`-metodia. Työkaluvihjeet antavat käyttäjille hyödyllisiä vihjeitä, kun he vievät hiiren navigointi-elementtien päälle.

:::info
Työkaluvihjeen teksti ei arvioidu JavaScriptiksi, toisin kuin `setText()`-metodin käytettävä teksti
:::

<!-- vale off -->
Esimerkiksi, asettaaksesi viimeisen sivupainikkeen työkaluvihjeen tekstiksi `Navigator`:issa "Siirry viimeiselle sivulle", käytä seuraavaa koodinpätkää:
<!-- vale on -->

```java
navigator.setTooltipText("Siirry viimeiselle sivulle", Navigator.Part.LAST_BUTTON);
```

## Asettelu {#layouts}

`Navigator`-komponentille on olemassa useita asettelu vaihtoehtoja, jotka tarjoavat joustavuutta sivuohjausvaihtoehtojen näyttämisessä. Näiden asettelujen käyttöön saat käyttämällä `Navigator.Layout`-enum:n arvoja. Vaihtoehdot ovat seuraavat:

<ComponentDemo
path='/webforj/navigatorlayout'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorLayoutView.java']}
height='200px'
/>

### 1. Ei asettelua {#1-none-layout}

`NONE`-asettelu ei renderöi mitään tekstiä `Navigator`:issa, vaan näyttää vain navigointipainikkeet ilman oletuksena olevaa tekstinäyttöä. Aktivoi tämä asettelu käyttämällä:

```java
navigator.setLayout(Navigator.Layout.NONE);
```

### 2. Numeroitu asettelu {#2-numbered-layout}

Numeroitu asettelu näyttää numeroituja chippejä, jotka vastaavat kutakin sivua `Navigator`:in näyttöalueella. Tämän asettelun käyttäminen on ihanteellista tilanteissa, joissa käyttäjät mieluummin navigoivat suoraan tiettyihin sivuihin. Aktivoi tämä asettelu käyttämällä:

```java
navigator.setLayout(Navigator.Layout.PAGES);
```

### 3. Esikatselu asettelu {#3-preview-layout}

Esikatselu-asettelu näyttää nykyisen sivun numeron ja sivujen kokonaismäärän, ja se on sopiva tiiviisiin sivuohjausliittymiin, joissa on rajallisesti tilaa.

:::info
Esikatselu on oletusarvoinen `Navigator`-asettelu.
:::

Aktivoi tämä asettelu käyttämällä:

```java
navigator.setLayout(Navigator.Layout.PREVIEW);
```

### 4. Nopean hyppäämisen asettelu {#4-quick-jump-layout}

Nopean hyppäämisen asettelu tarjoaa [NumberField](./fields/number-field.md) käyttäjille, joiden avulla he voivat syöttää sivun numeron nopeaa navigointia varten. Tämä on hyödyllistä, kun käyttäjät tarvitsevat nopeasti siirtyä tiettyyn sivuun, erityisesti suurilla tietoaineistoilla. Aktivoi tämä asettelu käyttämällä:

```java
navigator.setLayout(Navigator.Layout.QUICK_JUMP);
```

## Tyylittely {#styling}

<TableBuilder name="Navigator" />

## Parhaat käytännöt {#best-practices}

Optimaalisen käyttäjäkokemuksen varmistamiseksi `Navigator`-komponentin käytössä, harkitse seuraavia parhaita käytäntöjä:

- **Ymmärrä tietoaineisto**: Ennen kuin integroi `Navigator`-komponentin sovellukseesi, ymmärrä perusteellisesti käyttäjiesi tietohakutarpeet. Huomioi tekijät, kuten tietoaineiston koko, tyypilliset käyttäjäinteraktiot ja suositut navigointimallit.

- **Valitse sopiva asettelu**: Valitse `Navigator`-komponentille asettelu, joka vastaa käyttäjäkokemustavoitteita ja saatavilla olevaa näyttötilaa.

- **Mukauta tekstiä ja työkaluvihjeitä**: Mukauta `Navigator`-komponentin teksti ja työkaluvihjeet vastaamaan sovelluksessasi käytettävää kieltä ja terminologiaa. Tarjoa kuvailevia etikettejä ja hyödyllisiä vinkkejä auttaaksesi käyttäjiä navigoimaan tietoaineistossa tehokkaasti.
