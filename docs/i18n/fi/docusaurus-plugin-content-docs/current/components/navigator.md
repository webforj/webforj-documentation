---
title: Navigator
sidebar_position: 75
_i18n_hash: 7c09a72456eb84a8227da3ff263b6c69
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-navigator" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="foundation" location="com/webforj/component/navigator/Navigator" top='true'/>

`Navigator`-komponentti on mukautettava sivunvaihtokomponentti, joka on suunniteltu navigoimaan tietojoukoissa ja tukee useita asetteluja. Voit konfiguroida sen näyttämään erilaisia navigointiohjaimia, kuten ensimmäiset, viimeiset, seuraavat ja edelliset painikkeet, yhdessä sivunumeroiden tai pikasiirtymäkentän kanssa asetteluasetuksesta riippuen.

Se tukee automaattista navigointipainikkeiden poistamista käytöstä nykyisen sivun ja kokonaismäärän perusteella ja tarjoaa mukautusvaihtoehtoja tekstille ja työkaluviesteille eri osille navigaattoria. Lisäksi voit sitoa sen `Paginator`-instanssiin hallitsemaan tietojoukon sivunvaihtologiikkaa ja heijastamaan muutoksia navigointiohjaimissa.

## Sitoaminen varastoihin {#binding-to-repositories}

Usein `Navigator`-komponentti näyttää tietoja, jotka löytyvät sidotusta `Repository`:sta. Tämä sitoutuminen mahdollistaa `Navigator`:in automaattisen sivunvaihdon varaston hallitseman tiedon osalta ja muiden sidottavien komponenttien, kuten taulukoiden, päivittämisen navigoidun tiedon perusteella.

Tätä varten välitä vain haluttu `Repository`-objekti soveltuvan `Navigator`-objektin rakentajalle:

<ComponentDemo 
path='/webforj/navigatortable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorTableView.java'
height='475px'
/>

Tämä esimerkki luo `Navigator`:in ja [`Table`](table/overview) samalle `Repository`-instanssille. Tämä tarkoittaa, että kun navigoit uudelle sivulle `Navigator`:illa, [`Table`](table/overview) tunnistaa tämän muutoksen ja renderöi sen uudelleen.

## Sivunvaihto {#pagination}

`Navigator`-komponentti on tiiviisti sidoksissa `Paginator`-malliluokkaan, joka laskee sivunvaihdon metadataa, kuten sivujen kokonaismäärän, nykyisen sivun alkua/ loppuindeksit ja taulukon sivunumeroita navigointia varten.

Vaikka se ei ole välttämätöntä, `Paginator`in käyttäminen mahdollistaa navigointia koskevan logiikan. Integroidessasi `Paginator`:n kanssa, navigaattori reagoi kaikkiin muutoksiin `Paginator`:issa. `Navigator`-objekteilla on pääsy sisäänrakennettuun `Paginator`:iin käyttämällä `getPaginator()`-metodia. Se voi myös hyväksyä `Paginator`-instanssin `setPaginator()`-metodin kautta tai hyödyntämällä yhtä soveltuvaa rakentajista.

Tässä osiossa on käytännön koodinpätkiä, jotka havainnollistavat, miten tämä integraatio toimii käytännössä.

### Kohteet {#items}

Termi "kohteet" tarkoittaa yksittäisiä sivunvaihdettuja elementtejä tai tietokirjauksia. Nämä voivat olla tietueita, merkintöjä tai mitä tahansa erillisiä yksiköitä tietojoukossa. Voit asettaa kohteiden kokonaismäärän käyttämällä `setTotalItems()`-metodia.

```java
navigator.getPaginator().setTotalItems(totalItems);
```

:::info
Varasto, joka on liitetty `Paginator`-instanssiin, hallitsee suoraan varaston kautta kokoa ja sitä ei voi suoraan asettaa.
:::

### Maksimisivut {#maximum-pages}

`setMax()`-metodilla voit määrittää maksimaalisen sivulinkkien määrän, jota näytetään sivunvaihdon navigoinnissa. Tämä on erityisen hyödyllistä, kun käsitellään suurta määrää sivuja, sillä se hallitsee käyttäjälle näkyvien sivulinkkien määrää.

```java
navigator.getPaginator().setMax(maxPages);
```

<ComponentDemo 
path='/webforj/navigatorpages?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorPagesView.java'
height='125px'
/>

Tämä ohjelma näyttää enintään viisi sivua `Navigator`-komponentissa kerrallaan käyttämällä `getPaginator()`-metodia saadakseen `Paginator`:n, joka liittyy `Navigator`-objektiin, ja sitten käyttämällä `setMax()`-metodia määrittämään halutun maksimi sivujen määrän.

### Sivukoko {#page-size}

`setSize()`-metodilla voit määrittää, kuinka monta kohdetta näytetään jokaisella sivulla sivunvaihdossa. Kun kutsut tätä metodia ja tarjoat uuden sivukoon, se säätää sivunvaihdon vastaavasti.

```java
navigator.getPaginator().setSize(pageSize);
```

## Painikkeiden, tekstin ja työkaluvihjeiden mukauttaminen {#customizing-buttons-text-and-tooltips}

`Navigator`-komponentti tarjoaa kattavat mukautusvaihtoehdot painikkeiden, tekstin ja työkaluvihjeiden osalta. Muuttaaksesi `Navigator`-komponentissa näkyvää tekstiä, käytä `setText()`-metodia. Tämä metodi ottaa tekstin sekä halutun `Part`:in `Navigator`:ista.

Seuraavassa esimerkissä `setText()`-metodi näyttää numeron käyttäjälle. Painikkeiden klikkaaminen laukaisee `Navigator`in `onChange`-metodin, johon tulee `Direction`-arvo napsautetusta painikkeesta.

<ComponentDemo 
path='/webforj/navigatorbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorBasicView.java'
height='100px'
/>

### Painikkeet ja komponentin teksti {#buttons-and-component-text}

`setText()`-metodi arvioi tekstiparametrin JavaScript-lausekkeena seuraavilla parametreilla:

- `page` - nykyisen sivun numero
- `current` - tällä hetkellä valitun sivun numero
- `x` - aliaksena nykyiselle sivulle
- `startIndex` - nykyisen sivun aloitusindeksi.
- `endIndex` - nykyisen sivun loppuindeksi.
- `totalItems` - kohteiden kokonaismäärä.
- `startPage` - aloitussivun numero.
- `endPage` - loppusivun numero.
- `component` - Navigator-asiakaskomponentti.

<!-- vale off -->
Esimerkiksi asettaaksesi viimeisen sivupainikkeen tekstiksi `Navigator`:issa, jossa on 10 sivua "Siirry sivulle 10", käytä seuraavaa koodipätkää: 
<!-- vale on -->

```java
navigator.setText("'Siirry sivulle ' + endPage", Navigator.Part.LAST_BUTTON);
```

### Työkaluvihjeteksti {#tooltip-text}

Voit mukauttaa työkaluvihjeitä eri osille `Navigator`-komponenttia käyttämällä `setTooltipText()`-metodia. Työkaluvihjeet tarjoavat hyödyllisiä vihjeitä käyttäjille, kun he vievät hiiren navigointielementtien päälle.

:::info
Työkaluvihjeteksti ei arvioi Javascriptinä, toisin kuin `setText()`-metodilla käytetty teksti
:::

<!-- vale off -->
Esimerkiksi asettaaksesi viimeisen sivupainikkeen työkaluvihjetekstiksi `Navigator`:ssa "Siirry viimeiselle sivulle", käytä seuraavaa koodipätkää:
<!-- vale on -->

```java
navigator.setTooltipText("Siirry viimeiselle sivulle", Navigator.Part.LAST_BUTTON);
```

## Asettelut {#layouts}

Erilaisia asetteluvalintoja on olemassa `Navigator`-komponentille, jotta se tarjoaa joustavuutta sivunvaihto-ohjaimien näyttämiseen. Päästäksesi näihin asetteluvalintoihin, käytä `Navigator.Layout`-enum:in arvoja. Vaihtoehdot ovat seuraavat:

<ComponentDemo 
path='/webforj/navigatorlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorLayoutView.java'
height='200px'
/>

### 1. Ei asetelmaa {#1-none-layout}

`NONE`-asettelu ei renderöi tekstiä `Navigator`:issa, vaan näyttää vain navigointipainikkeet ilman oletustekstistä näyttöä. Aktivoi tämä asettelu käyttämällä:

```java
navigator.setLayout(Navigator.Layout.NONE);
```

### 2. Numeroitu asettelu {#2-numbered-layout}

Numeroitu asettelu näyttää numeroidut sirut, jotka vastaavat kutakin sivua `Navigator`:n näyttöalueella. Tämän asettelun käyttäminen on ihanteellista tilanteissa, joissa käyttäjät mieluummin navigoivat suoraan tiettyihin sivuihin. Aktivoi tämä asettelu käyttämällä:

```java
navigator.setLayout(Navigator.Layout.PAGES);
```

### 3. Esikatselu asettelu {#3-preview-layout}

Esikatselu asettelu näyttää nykyisen sivun numeron ja sivujen kokonaismäärän, ja se sopii kompaktiin sivunvaihto käyttöliittymiin, joissa on rajallisesti tilaa.

:::info
Esikatselu on oletusarvoinen `Navigator`-asettelu.
:::

Aktivoi tämä asettelu käyttämällä:

```java
navigator.setLayout(Navigator.Layout.PREVIEW);
```

### 4. Pikasiirtymä asettelu {#4-quick-jump-layout}

Pikasiirtymä asettelu tarjoaa [NumberField](./fields/number-field.md) käyttäjille, joille syötetään sivunumero nopeaa navigointia varten. Tämä on hyödyllistä, kun käyttäjät tarvitsevat nopeaa siirtymistä tietylle sivulle, erityisesti suurissa tietojoukoissa. Aktivoi tämä asettelu käyttämällä:

```java
navigator.setLayout(Navigator.Layout.QUICK_JUMP);
```

## Tyylittely {#styling}

<TableBuilder name="Navigator" />

## Parhaat käytännöt {#best-practices}

Jotta `Navigator`-komponentin käytössä varmistettaisiin optimaalinen käyttäjäkokemus, ota huomioon seuraavat parhaat käytännöt:

- **Ymmärrä tietojoukko**: Ennen kuin integroi `Navigator`-komponentti sovellukseesi, ymmärrä perusteellisesti käyttäjiesi tietoselausvaatimukset. Ota huomioon tekijät, kuten tietojoukon koko, tyypilliset käyttäjäinteraktiot ja suosituimmat navigointimallit.

- **Valitse sopiva asettelu**: Valitse `Navigator`-komponentille asettelu, joka vastaa käyttäjäkokemus tavoitteita ja käytettävissä olevaa näyttötasoa.

- **Mukauta tekstejä ja työkaluvihjeitä**: Mukauta `Navigator`-komponentin tekstejä ja työkaluviestejä vastaamaan sovelluksesi kieltä ja terminologiaa. Anna kuvaavia etikettejä ja hyödyllisiä vihjeitä auttaaksesi käyttäjiä navigoimaan tietojoukossa tehokkaasti.
