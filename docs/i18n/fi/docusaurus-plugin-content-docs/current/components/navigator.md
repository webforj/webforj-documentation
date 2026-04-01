---
title: Navigator
sidebar_position: 75
_i18n_hash: be5e20c3abb0d7b64b7a0eff03f7aded
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-navigator" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="foundation" location="com/webforj/component/navigator/Navigator" top='true'/>

Komponentti `Navigator` lisää sivutuksen hallintakontrolleja tietojoukoissa navigointia varten. Se voi näyttää ensimmäiset, viimeiset, seuraavat ja edelliset painikkeet sekä sivunumerot tai nopean hyppykentän, ja se poistaa automaattisesti hallintalaitteiden käytön, kun ne eivät ole soveltuvia. Se sitoo `Paginator`-instanssiin hallitakseen taustalla olevaa sivutuksen logiikkaa.

<!-- INTRO_END -->

## Sidonta säilytyksiin {#binding-to-repositories}

Usein `Navigator`-komponentti näyttää tietoa, joka löytyy sidotusta `Repository`-objekti. Tämä sitominen mahdollistaa `Navigator`-komponentin automaattisen sivutuksen tietojoukoista, joita säilytys hallitsee, ja muiden sidottavien komponenttien, kuten taulukoiden, päivittämisen navigoituja tietoja perusteella.

Tämä onnistuu yksinkertaisesti syöttämällä haluttu `Repository`-objekti soveltuvan `Navigator`-objektin konstruktorille:

<ComponentDemo 
path='/webforj/navigatortable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorTableView.java'
height='475px'
/>

Tässä esimerkissä luodaan `Navigator` ja [`Table`](table/overview) samalla `Repository`-instanssilla. Tämä tarkoittaa, että kun navigoit uuteen sivuun `Navigatorin` avulla, [`Table`](table/overview) tunnistaa tämän muutoksen ja piirretään uudelleen.

## Sivutus {#pagination}

`Navigator`-komponentti on läheisesti yhteydessä `Paginator`-malliluokkaan, joka laskee sivutuksen metadataa, kuten sivujen kokonaismäärää, alkupään/päätteen indeksejä nykyisellä sivulla ja taulukkoa sivunumeroista navigointia varten.

Vaikka se ei ole ilmeisen tarpeellista, `Paginatorin` käyttäminen mahdollistaa navigointilogiikan taustalla. Kun integroidaan `Paginator`-komponentin kanssa, navigaattori reagoi muutoksiin `Paginatorissa`. `Navigator`-objekteilla on pääsy sisäiseen `Paginator`-komponenttiin käyttämällä `getPaginator()`-metodia. Se voi myös hyväksyä `Paginator`-instanssin `setPaginator()`-metodin kautta tai käyttämällä yhtä soveltuvista konstruktoreista.

Tässä osassa on käytännön koodinpätkiä havainnollistamaan, miten tämä integraatio toimii käytännössä.

### Kohteet {#items}

Termi "kohteet" tarkoittaa yksittäisiä sivutettuja elementtejä tai tietojen merkintöjä. Nämä voivat olla tietueita, merkintöjä tai mitä tahansa erillisiä yksiköitä tietojoukossa. Voit asettaa kohteiden kokonaismäärän käyttämällä `setTotalItems()`-metodia. 

```java
navigator.getPaginator().setTotalItems(totalItems);
```

:::info
Säilytykselle, joka on liitetty `Paginator`-instanssiin, on suoraan hallittavakseen kohteiden kokonaismäärä, jota ei voi suoraan asettaa.
:::

### Suurin määrä sivuja {#maximum-pages}

`setMax()`-metodi sallii sinun määrittää suurimman määrän sivulinkkejä, jotka näytetään sivutuksen navigoinnissa. Tämä on erityisen hyödyllistä, kun käsitellään suurta määrää sivuja, sillä se hallitsee näkyvien sivulinkkien määrää käyttäjälle mihin tahansa aikaan.

```java
navigator.getPaginator().setMax(maxPages);
```

<ComponentDemo 
path='/webforj/navigatorpages?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorPagesView.java'
height='125px'
/>

Tässä ohjelmassa näytetään enintään viisi sivua `Navigatorissa` kerrallaan käyttämällä `getPaginator()`-metodia saadaksesi `Paginator`-komponentin, joka liittyy `Navigator`-objektiin, ja sitten käyttämällä `setMax()`-metodia määrittääksesi halutun suurimman sivujen määrän, joka näytetään.

### Sivukoko {#page-size}

`setSize()`-metodi sallii sinun määrittää kohteiden määrän, joka näytetään sivulla sivutuksessa. Kun kutsut tätä metodia ja annat uuden sivukoon, se säätää sivutuksen sen mukaisesti. 

```java
navigator.getPaginator().setSize(pageSize);
```

## Painikkeiden, tekstin ja työkaluvihjeiden mukauttaminen {#customizing-buttons-text-and-tooltips}

`Navigator`-komponentti tarjoaa laajat mukautusmahdollisuudet painikkeille, tekstille ja työkaluvihjeille. Muuttaaksesi näytettävää tekstiä `Navigator`-komponentissa, käytä `setText()`-metodia. Tämä metodi ottaa tekstin sekä halutun `Part`-osan `Navigatorista`. 

Seuraavassa esimerkissä `setText()`-metodi näyttää käyttäjälle numeerisen arvon. Painikkeiden klikkaaminen käynnistää `Navigatorin` `onChange`-metodin, joka tulee yhdessä painettua painiketta osoittavan `Direction`-arvon kanssa. 

<ComponentDemo 
path='/webforj/navigatorbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorBasicView.java'
height='100px'
/>

### Painikkeet ja komponentin teksti {#buttons-and-component-text}

`setText()`-metodi arvioi tekstiparametrin JavaScript-lausekkeena, käyttäen seuraavia parametreja:

- `page` - nykyinen sivunumero
- `current` - tällä hetkellä valittu sivunumero
- `x` - alias nykyiselle sivulle
- `startIndex` - Nykyisen sivun aloitusindeksi.
- `endIndex` - Nykyisen sivun päättymisindeksi.
- `totalItems` - Kohteiden kokonaismäärä.
- `startPage` - Aloitussivun numero.
- `endPage` - Päätysivun numero.
- `component` - Navigator-asiakaskomponentti.

<!-- vale off -->
Esimerkiksi, määrittääksesi viimeisen sivun painikkeen tekstiksi `Navigatorissa`, jossa on 10 sivua, käytä seuraavaa koodinpätkää: 
<!-- vale on -->

```java
navigator.setText("'Go to page ' + endPage", Navigator.Part.LAST_BUTTON);
```

### Työkaluvihjeteksti {#tooltip-text}

Voit mukauttaa työkaluvihjeitä eri osille `Navigator`-komponenttia käyttämällä `setTooltipText()`-metodia. Työkaluvihjeet tarjoavat käyttäjille hyödyllisiä vihjeitä, kun he vievät hiiren navigointielementtien päälle.

:::info
Työkaluvihjeteksti ei arvioi JavaScriptiksi, toisin kuin `setText()`-metodin käyttämä teksti.
:::

<!-- vale off -->
Esimerkiksi, määrittääksesi viimeisen sivun painikkeen työkaluvihjetekstiksi `Navigatorissa` "Go to the last page", käytä seuraavaa koodinpätkää:
<!-- vale on -->

```java
navigator.setTooltipText("Go to the last page", Navigator.Part.LAST_BUTTON);
```

## Asettelu {#layouts}

Eri asettelu- vaihtoehtoja on olemassa `Navigator`-komponentille, jotta voidaan tarjota joustavuutta sivutuksen kontrollien näyttämisessä. Käytä `Navigator.Layout`-enum:in arvoja päästäksesi näihin asetteluihin. Vaihtoehdot ovat seuraavat:

<ComponentDemo 
path='/webforj/navigatorlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorLayoutView.java'
height='200px'
/>

### 1. Ei-asettelu {#1-none-layout}

`NONE`-asettelu ei renderöi tekstiä `Navigatorissa`, vaan näyttää vain navigointipainikkeet ilman oletustekstistä näyttöä. Aktivoi tämä asettelu käyttämällä:

```java
navigator.setLayout(Navigator.Layout.NONE);
```

### 2. Numeroitu asettelu {#2-numbered-layout}

Numeroitu asettelu näyttää numeroituja chippejä, jotka vastaavat kutakin sivua `Navigatorin` näyttöalueella. Tämän asettelun käyttäminen on ihanteellista tilanteissa, joissa käyttäjät suosivat suoraa navigointia tiettyihin sivuihin. Aktivoi tämä asettelu käyttämällä:

```java
navigator.setLayout(Navigator.Layout.PAGES);
```

### 3. Esikatselu asettelu {#3-preview-layout}

Esikatselu asettelu näyttää nykyisen sivun numeron ja sivujen kokonaismäärän, ja se on sopiva kompakteille sivutustyökaluille rajoitetussa tilassa.

:::info
Esikatselu on oletusarvoinen `Navigator`-asettelu.
:::

Aktivoi tämä asettelu käyttämällä:

```java
navigator.setLayout(Navigator.Layout.PREVIEW);
```

### 4. Nopean hyppäyksen asettelu {#4-quick-jump-layout}

Nopean hyppäyksen asettelu tarjoaa [NumberField](./fields/number-field.md) käyttäjille, joilla on mahdollisuus syöttää sivunumero nopeaa navigointia varten. Tämä on hyödyllistä, kun käyttäjät tarvitsevat päästä nopeasti tiettyyn sivuun, erityisesti suurissa tietojoukoissa. Aktivoi tämä asettelu käyttämällä:

```java
navigator.setLayout(Navigator.Layout.QUICK_JUMP);
```

## Tyylit {#styling}

<TableBuilder name="Navigator" />

## Parhaat käytännöt {#best-practices}

Jotta `Navigator`-komponentin käyttö takaisi optimaalisen käyttäjäkokemuksen, harkitse seuraavia parhaita käytäntöjä: 

- **Ymmärrä tietojoukko**: Ennen kuin integroit `Navigator`-komponentin sovellukseesi, ymmärrä perusteellisesti käyttäjiesi tietoselausvaatimukset. Ota huomioon tekijät, kuten tietojoukon koko, tyypilliset käyttäjäinteraktiot ja suosikkinautiopatternit.

- **Valitse sopiva asettelu**: Valitse `Navigator`-komponentille sellainen asettelu, joka vastaa käyttäjäkokemus tavoitteita ja käytettävissä olevaa näyttötilaa.

- **Mukauta tekstiä ja työkaluvihjeitä**: Mukauta `Navigator`-komponentin teksti ja työkaluvihjeet vastaamaan sovelluksesi käytettävää kieltä ja terminologiaa. Tarjoa kuvailevia etikettejä ja hyödyllisiä vihjeitä käyttäjien avustamiseksi tietojoukon navigoinnissa tehokkaasti.
