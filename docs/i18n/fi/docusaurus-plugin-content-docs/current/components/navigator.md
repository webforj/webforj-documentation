---
title: Navigator
sidebar_position: 75
_i18n_hash: 920c1d604673e69a32f58161e3fd4e14
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-navigator" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="foundation" location="com/webforj/component/navigator/Navigator" top='true'/>

`Navigator`-komponentti on mukautettava sivutuskomponentti, joka on suunniteltu navigoimaan tietojoukoissa ja tukee useita asetteluja. Voit konfiguroida sen näyttämään erilaisia navigointikontrolleja, kuten ensimmäiset, viimeiset, seuraavat ja edelliset painikkeet, sekä sivunumeroita tai nopeaa hyppyaluetta asetteluasetuksen mukaan.

Se tukee navigointipainikkeiden automaattista poistamista käytöstä nykyisen sivun ja kokonaismäärän mukaan sekä tarjoaa mukauttamisasetuksia tekstille ja työkaluvihjeille eri osille navigaattoria. Lisäksi voit sitoa sen `Paginator`-instanssiin hallitsemaan datan sivutuslogiikkaa ja heijastamaan muutoksia navigointikontrolleissa.

## Sitoen tietovarastoihin {#binding-to-repositories}

Usein `Navigator`-komponentti näyttää tietoa, joka löytyy sidotusta `Repository`:stä. Tämä sitominen mahdollistaa `Navigator`:in automaattisen sivutuksen rekisteröidystä datasta ja muiden sidottavien komponenttien, kuten taulukoiden, päivittämisen navigoidun datan perusteella.

Voit tehdä tämän yksinkertaisesti välittämällä halutun `Repository`-objektin soveltuvan `Navigator`-objektin konstruktorille:

<ComponentDemo 
path='/webforj/navigatortable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorTableView.java'
height='475px'
/>

Tässä esimerkissä luodaan `Navigator` ja [`Table`](table/overview) samalla `Repository`-instanssilla. Tämä tarkoittaa, että kun navigoit uudelle sivulle `Navigator`illa, [`Table`](table/overview) tunnistaa tämän muutoksen ja renderöi sen uudelleen.

## Sivutus {#pagination}

`Navigator`-komponentti on tiiviisti sidoksissa `Paginator`-malliluokkaan, joka laskee sivutuksen metatietoja, kuten sivujen kokonaismäärän, alkupään/loppupään indeksit nykyisellä sivulla ja matriisin sivunumeroista navigointiin.

Vaikka se ei ole välttämätöntä, `Paginator`in käyttö mahdollistaa navigoinnin taustalla olevan logiikan. Kun integroituu `Paginator`in kanssa, navigaattori reagoi kaikkiin muutoksiin `Paginator`issa. `Navigator`-objekteilla on pääsy sisäänrakennettuun `Paginator`iin käyttämällä `getPaginator()`-menetelmää. Se voi myös hyväksyä `Paginator`-instanssin `setPaginator()`-menetelmän kautta tai käyttämällä jotakin soveltuvaa konstruktoria.

Tässä osiossa on käytännön koodinäytteitä havainnollistamaan, kuinka tämä integraatio toimii käytännössä.

### Elementit {#items}

Termi "elementit" tarkoittaa yksittäisiä sivutettuja yksiköitä tai tietoehdokkaita. Nämä voivat olla tietueita, merkintöjä tai mitä tahansa erillisiä yksiköitä tietojoukossa. Voit asettaa elementtien kokonaismäärän `setTotalItems()`-menetelmällä.

```java
navigator.getPaginator().setTotalItems(totalItems);
```

:::info
Repository, joka liittyy `Paginator`-instanssiin, hallitsee suoraan yksiköiden kokonaismäärää, eikä sitä voida asettaa suoraan.
:::

### Maksimimäärä sivuja {#maximum-pages}

`setMax()`-menetelmän avulla voit määrittää maksimaalisen sivulinkkien määrän, joka näytetään sivutuksen navigoinnissa. Tämä on erityisen hyödyllistä käsiteltäessä suurta määrää sivuja, sillä se hallitsee näkyvien sivulinkkien määrää käyttäjälle mihin tahansa aikaan.

```java
navigator.getPaginator().setMax(maxPages);
```

<ComponentDemo 
path='/webforj/navigatorpages?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorPagesView.java'
height='125px'
/>

Tämä ohjelma näyttää enintään viisi sivua `Navigator`issa kerrallaan käyttämällä `getPaginator()`-menetelmää noutamaan `Paginator`-instanssi, joka liittyy `Navigator`-objektiin, ja käyttämällä sitten `setMax()`-menetelmää määrittämään halutun enimmäismäärän näkyvissä olevia sivuja.

### Sivukoko {#page-size}

`setSize()`-menetelmän avulla voit määrittää esitettävien elementtien määrän kullakin sivulla sivutuksessa. Kun kutsut tätä menetelmää ja annat uuden sivukoon, se säätelee sivutusta vastaavasti.

```java
navigator.getPaginator().setSize(pageSize);
```

## Painikkeiden, tekstin ja työkaluvihjeiden mukauttaminen {#customizing-buttons-text-and-tooltips}

`Navigator`-komponentti tarjoaa laajat mukautusvaihtoehdot painikkeille, teksteille ja työkaluvihjeille. Vaihtaaksesi näytettävää tekstiä `Navigator`-komponentissa, käytä `setText()`-menetelmää. Tämä menetelmä ottaa tekstin sekä halutun osan `Navigator`:ista.

Seuraavassa esimerkissä `setText()`-menetelmä näyttää käyttäjälle numeerisen arvon. Painikkeeseen napsauttaessasi laukaisee `Navigator`in `onChange`-menetelmän, joka tulee napsautetun painikkeen `Direction`-arvosta.

<ComponentDemo 
path='/webforj/navigatorbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorBasicView.java'
height='100px'
/>

### Painikkeet ja komponentin teksti {#buttons-and-component-text}

`setText()`-menetelmä arvioi tekstiparametrin JavaScript-lausekkeena käyttämällä seuraavia parametrejä:

- `page` - nykyinen sivunumero
- `current` - tällä hetkellä valittu sivunumero
- `x` - aliaksena nykyiselle sivulle
- `startIndex` - nykyisen sivun alkupään indeksi.
- `endIndex` - nykyisen sivun loppupään indeksi.
- `totalItems` - elementtien kokonaismäärä.
- `startPage` - alkusivun numero.
- `endPage` - loppusivun numero.
- `component` - Navigator-asiakaskomponentti.

<!-- vale off -->
Esimerkiksi asettaaksesi viimeisen sivun painikkeen tekstiksi `Navigator`issa, jossa on 10 sivua, "Siirry sivulle 10", käytä seuraavaa koodinäytettä: 
<!-- vale on -->

```java
navigator.setText("'Siirry sivulle ' + endPage", Navigator.Part.LAST_BUTTON);
```

### Työkaluvihjeen teksti {#tooltip-text}

Voit mukauttaa työkaluvihjeitä erilaisille osille `Navigator`-komponentissa käyttämällä `setTooltipText()`-menetelmää. Työkaluvihjeet tarjoavat hyödyllisiä vinkkejä käyttäjille, kun he viettävät hiirtään navigointielementtien päällä.

:::info
Työkaluvihjetekstiä ei arvioida JavaScriptiksi, toisin kuin `setText()`-menetelmän käyttämä teksti.
:::

<!-- vale off -->
Esimerkiksi asettaaksesi viimeisen sivun painikkeen työkaluvihjetekstiksi `Navigator`issa "Siirry viimeiselle sivulle", käytä seuraavaa koodinäytettä:
<!-- vale on -->

```java
navigator.setTooltipText("Siirry viimeiselle sivulle", Navigator.Part.LAST_BUTTON);
```

## Asettelu {#layouts}

`Navigator`-komponentille on olemassa erilaisia asetteluvalintoja, jotka tarjoavat joustavuutta sivutuskontrollien esittämisessä. Voit käyttää näitä asetteluja käyttämällä `Navigator.Layout`-enum'n arvoja. Vaihtoehdot ovat seuraavat:

<ComponentDemo 
path='/webforj/navigatorlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorLayoutView.java'
height='200px'
/>

### 1. Ei asettelua {#1-none-layout}

`NONE`-asettelu ei renderöi tekstiä `Navigator`issa, vaan näyttää vain navigointipainikkeet ilman oletuksellista tekstiesitystä. Aktivoidaksesi tämän asettelun, käytä:

```java
navigator.setLayout(Navigator.Layout.NONE);
```

### 2. Numeroitu asettelu {#2-numbered-layout}

Numeroitu asettelu näyttää numeroituja siruja, jotka vastaavat jokaista sivua `Navigator`in näyttöalueella. Tämän asettelun käyttö on ihanteellista tilanteissa, joissa käyttäjät haluavat siirtyä suoraan tiettyihin sivuihin. Aktivoidaksesi tämän asettelun, käytä:

```java
navigator.setLayout(Navigator.Layout.PAGES);
```

### 3. Esikatselu asettelu {#3-preview-layout}

Esikatselu asettelu näyttää nykyisen sivun numeron ja kokonaismäärän, ja se on soveltuva tiiviisiin sivutusliittymiin, joissa on rajallisesti tilaa.

:::info
Esikatselu on oletusarvoinen `Navigator`-asettelu.
:::

Aktivoidaksesi tämän asettelun, käytä:

```java
navigator.setLayout(Navigator.Layout.PREVIEW);
```

### 4. Nopean hypyn asettelu {#4-quick-jump-layout}

Nopean hypyn asettelu tarjoaa [NumberField](./fields/number-field.md), johon käyttäjät voivat syöttää sivun numeron nopeaa navigointia varten. Tämä on hyödyllistä, kun käyttäjät tarvitsevat siirtyä nopeasti tiettyyn sivuun, erityisesti suurilla tietojoukoilla. Aktivoidaksesi tämän asettelun, käytä:

```java
navigator.setLayout(Navigator.Layout.QUICK_JUMP);
```

## Tyylittäminen {#styling}

<TableBuilder name="Navigator" />

## Parhaat käytännöt {#best-practices}

Optimaalisen käyttäjäkokemuksen varmistamiseksi `Navigator`-komponentin käytössä harkitse seuraavia parhaita käytäntöjä:

- **Ymmärrä tietojoukko**: Ennen kuin integroidaan `Navigator`-komponentti sovellukseesi, ymmärrä perusteellisesti käyttäjiesi tietojenkatselutarpeet. Harkitse tekijöitä, kuten tietojoukon kokoa, tyypillisiä käyttäjävuorovaikutuksia ja suosittuja navigointimalleja.

- **Valitse sopiva asettelu**: Valitse `Navigator`-komponentille asettelu, joka vastaa käyttäjäkokemustavoitteita ja saatavilla olevaa ruututilaa.

- **Mukauta teksti ja työkaluvihjeet**: Mukauta `Navigator`-komponenteista teksti ja työkaluvihjeet vastaamaan sovelluksesi kieltä ja terminologiaa. Tarjoa kuvailevia etikettejä ja hyödyllisiä vinkkejä käyttäjille, jotta he voivat navigoida tietojoukon läpi tehokkaasti.
