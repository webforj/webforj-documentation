---
title: Navigator
sidebar_position: 75
description: >-
  Add pagination controls with the Navigator component, binding to a Paginator
  or Repository to drive page size, navigation, and labels.
_i18n_hash: 1223e167b76000411cd73c4bbbbda3d5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-navigator" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="foundation" location="com/webforj/component/navigator/Navigator" top='true'/>

`Navigator`-komponentti lisää sivutuksen hallintapainikkeet tietojoukoissa navigointia varten. Se voi näyttää ensimmäiset, viimeiset, seuraavat ja edelliset painikkeet yhdessä sivunumeroiden tai pikasiirtymiskentän kanssa, ja se poistaa automaattisesti hallintapainikkeet käytöstä, kun niitä ei ole saatavilla. Se sitoo `Paginator`-instanssin hallitakseen taustalla olevaa sivutusta.

<!-- INTRO_END -->

## Siteille sitoutuminen {#binding-to-repositories}

Usein `Navigator`-komponentti näyttää tietoja, jotka löytyvät sidotusta `Repository`:sta. Tämä sitoutuminen mahdollistaa `Navigator`in automaattisen sivutuksen repositorion hallitsemalle datalle ja muiden sidottavien komponenttien, kuten taulukoiden, päivittämisen, perustuen navigoituun dataan.

Tätä varten siirrä haluttu `Repository`-objekti asianmukaiseen `Navigator`-olion konstruktoriin:

<ComponentDemo
path='/webforj/navigatortable'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorTableView.java']}
height='475px'
/>

Tässä esimerkissä luodaan `Navigator` ja [`Table`](table/overview) samalla `Repository`-instanssilla. Tämä tarkoittaa, että kun navigoidaan uuteen sivuun `Navigator`:in avulla, [`Table`](table/overview) tunnistaa tämän muutoksen ja uudelleenrenderöi.

## Sivutus {#pagination}

`Navigator`-komponentti on tiiviisti sidoksissa `Paginator`-malliluokkaan, joka laskee sivutuksen metatiedot, kuten sivujen kokonaismäärän, nykyisellä sivulla olevien kohteiden aloitus-/lopetusindeksit, sekä taulukon sivunumeroita navigointia varten.

Vaikka se ei ole välttämätöntä, `Paginator`:n käyttäminen mahdollistaa navigointilogiikan taustalla. Kun yhdistetään `Paginator`:in kanssa, navigator reagoi kaikkiin muutoksiin `Paginator`:in sisällä. `Navigator`-oliot voivat käyttää sisäänrakennettua `Paginator`:ia `getPaginator()`-metodin kautta. Se voi myös hyväksyä `Paginator`-instanssin `setPaginator()`-metodilla tai käyttää yhtä soveltuvista konstruktoreista.

Tässä osiossa on käytännön koodinpätkiä havainnollistamaan, kuinka tämä integrointi toimii käytännössä.

### Kohteet {#items}

Termi "kohteet" tarkoittaa yksittäisiä sivutettuja elementtejä tai tietueita. Nämä voivat olla tietueita, merkintöjä tai mitä tahansa erillisiä yksiköitä tietojoukkossa. Voit asettaa kohteiden kokonaismäärän käyttämällä `setTotalItems()`-metodia.

```java
navigator.getPaginator().setTotalItems(totalItems);
```

:::info
Repositio, joka on sidottu `Paginator`-instanssiin, hallitsee suoraan repositioon liittyvää kohteiden kokonaismäärää ja sitä ei voida suoraan asettaa.
:::

### Maksimi sivut {#maximum-pages}

`setMax()`-metodi sallii sinun määrittää maksimimäärän sivulinkkejä, jotka näytetään sivutuksen navigoinnissa. Tämä on erityisen hyödyllistä suurten sivumäärien kanssa, koska se ohjaa näkyvien sivulinkkien määrää käyttäjälle minkä tahansa ajan.

```java
navigator.getPaginator().setMax(maxPages);
```

<ComponentDemo
path='/webforj/navigatorpages'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorPagesView.java']}
height='125px'
/>

Tämä ohjelma näyttää maksimissaan viisi sivua `Navigator`:issa kerrallaan käyttämällä `getPaginator()`-metodia saadakseen `Paginator`:in, joka liittyy `Navigator`-olioon, ja sitten käyttämällä `setMax()`-metodia määritelläkseen halutun maksimisivujen määrän, joka näytetään.

### Sivukoko {#page-size}

`setSize()`-metodi sallii sinun määrittää, kuinka monta kohdetta näytetään jokaisella sivulla sivutuksessa. Kun kutsut tätä metodia ja annat uuden sivukoon, se säätää sivutusta vastaavasti.

```java
navigator.getPaginator().setSize(pageSize);
```

## Painikkeiden, tekstin ja työkaluvihjeiden mukauttaminen {#customizing-buttons-text-and-tooltips}

`Navigator`-komponentti tarjoaa laajasti mukauttamisen mahdollisuuksia painikkeille, tekstille ja työkaluvihjeille. Vaihtaaksesi näytettävää tekstiä `Navigator`-komponentissa, käytä `setText()`-metodia. Tämä metodi ottaa tekstin sekä halutun `Part`-osan `Navigator`:ista.

Seuraavassa esimerkissä `setText()`-metodi näyttää numerollisen arvon käyttäjälle. Painikkeiden napsauttaminen laukaisee `Navigator`:in `onChange`-metodin, johon tulee `Direction`-arvo napsautetusta painikkeesta.

<ComponentDemo
path='/webforj/navigatorbasic'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorBasicView.java']}
height='100px'
/>

### Painikkeet ja komponentti-teksti {#buttons-and-component-text}

`setText()`-metodi arvioi tekstiparametrin Javascript-lausekkeena käyttäen seuraavia parametreja:

- `page` - nykyinen sivunumero
- `current` - tällä hetkellä valittu sivunumero
- `x` - nykyisen sivun jäljennös
- `startIndex` - nykyisen sivun aloitusindeksi.
- `endIndex` - nykyisen sivun lopetusindeksi.
- `totalItems` - kohteiden kokonaismäärä.
- `startPage` - aloitussivun numero.
- `endPage` - lopetussivun numero.
- `component` - Navigator-asiakasjäsen.

<!-- vale off -->
Esimerkiksi, asettaaksesi viimeisen sivupainikkeen tekstin `Navigator`:issa, jossa on 10 sivua, tekstiksi "Siirry sivulle 10", käytä seuraavaa koodinpätkää:
<!-- vale on -->

```java
navigator.setText("'Siirry sivulle ' + endPage", Navigator.Part.LAST_BUTTON);
```

### Työkaluvihje-teksti {#tooltip-text}

Voit mukauttaa työkaluvihjeitä `Navigator`-komponentin eri osille käyttämällä `setTooltipText()`-metodia. Työkaluvihjeet tarjoavat hyödyllisiä vihjeitä käyttäjille, kun he vievät hiiren navigointielementtien päälle.

:::info
Työkaluvihje-tekstiä ei arvioida Javascriptiksi, toisin kuin `setText()`-metodin käyttämä teksti
:::

<!-- vale off -->
Esimerkiksi, asettaaksesi viimeisen sivupainikkeen työkaluvihjeen tekstin `Navigator`-komponentissa "Siirry viimeiselle sivulle", käytä seuraavaa koodinpätkää:
<!-- vale on -->

```java
navigator.setTooltipText("Siirry viimeiselle sivulle", Navigator.Part.LAST_BUTTON);
```

## Asettelu {#layouts}

`Navigator`-komponentille on olemassa erilaisia asettelu vaihtoehtoja, jotka tarjoavat joustavuutta sivutusohjainten näyttämisessä. Päästäksesi näihin asetteluihin, käytä `Navigator.Layout`-enumeration arvoja. Vaihtoehdot ovat seuraavat:

<ComponentDemo
path='/webforj/navigatorlayout'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorLayoutView.java']}
height='200px'
/>

### 1. Ei asetusta {#1-none-layout}

`NONE`-asettelu ei renderöi tekstiä `Navigator`-komponenttiin, vaan näyttää vain navigointipainikkeet ilman oletustekstinäyttöä. Tämä asettelu aktivoituu käyttämällä:

```java
navigator.setLayout(Navigator.Layout.NONE);
```

### 2. Numerointi-asettelu {#2-numbered-layout}

Numerointi-asettelu näyttää numeroituja chippejä, jotka vastaavat jokaista sivua `Navigator`-komponentin näyttöalueella. Tämän asettelun käyttäminen on ihanteellista tilanteissa, joissa käyttäjät mieluummin navigoivat suoraan tietyille sivuille. Tämä asettelu aktivoituu käyttämällä:

```java
navigator.setLayout(Navigator.Layout.PAGES);
```

### 3. Esikatselu-asettelu {#3-preview-layout}

Esikatselu-asettelu näyttää nykyisen sivun numeron ja sivujen kokonaismäärän, ja se on sopiva tiiviille sivutusohteluille rajoitetussa tilassa.

:::info
Esikatselu on oletusarvoinen `Navigator`-asettelu.
:::

Aktivoimiseen käytä:

```java
navigator.setLayout(Navigator.Layout.PREVIEW);
```

### 4. Pikasiirtymä-asettelu {#4-quick-jump-layout}

Pikasiirtymä-asettelu tarjoaa [NumberField](./fields/number-field.md)-kentän, johon käyttäjät voivat syöttää sivunumeron nopeaa navigointia varten. Tämä on hyödyllistä, kun käyttäjien täytyy nopeasti navigoida tietylle sivulle, erityisesti suuria tietojoukkoja käsiteltäessä. Aktivoimiseen käytä:

```java
navigator.setLayout(Navigator.Layout.QUICK_JUMP);
```

## Tyylittely {#styling}

<TableBuilder name="Navigator" />

## Parhaat käytännöt {#best-practices}

Varmistaaksesi optimaalisen käyttäjäkokemuksen `Navigator`-komponentin käytössä, ota huomioon seuraavat parhaat käytännöt:

- **Ymmärrä tietojoukko**: Ennen kuin integroi `Navigator`-komponentin sovellukseesi, ymmärrä perusteellisesti käyttäjiesi datan selausvaatimukset. Ota huomioon sellaiset tekijät kuin tietojoukon koko, tyypilliset käyttäjävuorovaikutukset ja suosituimmat navigointimallit.

- **Valitse sopiva asettelu**: Valitse `Navigator`-komponentille asettelu, joka on linjassa käyttäjäkokemustavoitteiden ja käytettävissä olevan näytön real estate -tilan kanssa.

- **Mukauta teksti ja työkaluvihjeet**: Mukauta `Navigator`-komponentin teksti ja työkaluvihjeet vastaamaan sovelluksesi käyttämää kieltä ja terminologiaa. Tarjoa kuvaavia etikettejä ja hyödyllisiä vinkkejä, jotka auttavat käyttäjiä navigoimaan tietojoukossa tehokkaasti.
