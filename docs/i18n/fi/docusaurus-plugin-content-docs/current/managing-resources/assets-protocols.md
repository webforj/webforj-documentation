---
sidebar_position: 2
title: Assets Protocols
_i18n_hash: a7482285684e797c3cfc30d025a95482
---
webforJ tukee mukautettuja omaisuutta koskevia protokollia, jotka mahdollistavat jäsennellyn ja tehokkaan resurssien käytön. Nämä protokollat abstruoivat staattisten ja dynaamisten resurssien hakuun liittyvät monimutkaisuudet, varmistamalla, että omaisuudet haetaan ja liitetään saumattomasti sovellukseen.

## Webpalvelinprotokolla {#the-webserver-protocol}

**`ws://`**-protokolla mahdollistaa omaisuuksien hakemisen webforJ-sovelluksen staattisesta kansiosta. Kaikki tiedostot, jotka sijaitsevat sovelluksen luokkapolulla `src/main/resources/static`, ovat suoraan käytettävissä selaimesta. Esimerkiksi, jos sinulla on tiedosto nimeltä **css/app.css** kansiossa **resources/static**, se voidaan avata osoitteessa: **`/static/css/app.css`**

**ws://**-protokolla poistaa tarpeen kovakoodata `static`-etuliite resurssi-URL-osoitteissa, suojaten muuttuneilta etuliitteiltä riippuen käyttöönoton kontekstista. Jos web-sovellus on otettu käyttöön muun kontekstin alla kuin juuri, kuten **/mycontext**, osoite **css/app.css**:lle olisi: **`/mycontext/static/css/app.css`**

:::tip Staattisen etuliitteen konfigurointi
Voit ohjata `static`-etuliitettä käyttämällä [webforj-konfiguraatiota](../configuration/properties#configuration-options) vaihtoehdolla `webforj.assetsDir`. Tämä asetus määrittelee reitin nimen, jota käytetään staattisten tiedostojen palvelemiseen, kun taas **fyysinen kansio pysyy nimeltään `static`**. Tämä on erityisen hyödyllinen, jos oletusstaattinen reitti on ristiriidassa sovelluksesi reitin kanssa, sillä se mahdollistaa reitin nimen muuttamisen ilman, että kansiota täytyy nimetä uudelleen.
:::

Voit käyttää <JavadocLink type="foundation" location="com/webforj/utilities/Assets" code='true'>Assets</JavadocLink> -apuluokkaa ratkaistaksesi annetun web-palvelimen URL-osoitteen. Tämä voi olla hyödyllistä, jos sinulla on mukautettu komponentti, joka tarvitsee tukea tälle protokollalle.

```java
String url = Assets.resolveWebServerUrl("ws://js/app.js");
```

## Kontekstiproto {#the-context-protocol}

Kontekstiproto kartoittaa resursseja sovelluksen luokkapolulla `src/main/resources`. Jotkut resurssi-API-menetelmät ja -annotaatiot tukevat tätä protokollaa lukemaan tiedoston sisältö resurssikansiosta ja lähettämään sen sisällön selaimelle. Esimerkiksi, `InlineJavaScript`-annotaatio mahdollistaa JavaScript-tiedoston sisällön lukemisen resurssikansiosta ja sen upottamisen asiakaspäähän.

Esimerkiksi:

```java
String content = Assets.contentOf(
  Assets.resolveContextUrl("context://data/customers.json")
);
```

## Ikoni-protokolla {#the-icons-protocol}

**`icons://`**-protokolla tarjoaa dynaamisen lähestymistavan ikoninhallintaan, mikä tekee siitä tehokkaan ja joustavan ikoneiden luomiseen ja palvelemiseen [asennettavissa sovelluksissa](../configuration/installable-apps). Tämä protokolla sallii ikonien luomisen lennossa pyydetyn tiedoston nimen ja parametrien perusteella, poistaen monissa tapauksissa ennakkoon luotujen ikonien tarpeen.

```java
Img icon = new Img("icons://icon-192x192.png")
```

### Perusikoni {#base-icon}

Kun ikonia pyydetään käyttäen `icons://`-protokollaa, peruskuva johdetaan dynaamisesti pyydetystä ikonitiedoston nimestä. Tämä varmistaa johdonmukaisuuden ikonisuunnittelussa ja mahdollistaa automaattisen varautumisen oletuskuvaan, jos perusikonia ei ole annettu.

- **Esimerkki 1:** Pyyntö: `/icons/icon-192x192.png` → Perusikoni: `resources/icons/icon.png`
- **Esimerkki 2:** Pyyntö: `/icons/icon-different-192x192.png` → Perusikoni: `resources/icons/icon-different.png`

Jos pyydetylle ikonille ei ole olemassa peruskuvaa, oletuswebforJ-logo käytetään varakuvana.

:::tip `webforj.iconsDir`
Oletusarvoisesti webforJ palvelee ikoneita `resources/icons/`-hakemistosta. Voit muuttaa päätepisteen nimeä asettamalla `webforj.iconsDir`-ominaisuuden webforJ-konfigurointitiedostoon. Tämä muuttaa vain URL-päätepisteen, jota käytetään ikoneiden hakemiseen, ei varsinaisen kansion nimeä. Oletuspäätepiste on `icons/`.
:::

### Ikonien ylikirjoittaminen {#overriding-icons}

Voit ylikirjoittaa tiettyjä ikonikokoja sijoittamalla ennakkoon luotuja kuvia `resources/icons/`-hakemistoon. Tämä antaa suuremman hallinnan ikonien ulkonäöstä, kun tietyt koot tai tyylit tarvitsevat muokkausta.

- **Esimerkki:** Jos `resources/icons/icon-192x192.png` on olemassa, se palvellaan suoraan sen sijaan, että se luotaisiin dynaamisesti.

### Ikonien ulkoasun mukauttaminen {#customizing-icon-appearance}

`icons://`-protokolla tukee lisäparametreja, joiden avulla voit mukauttaa dynaamisesti luotujen ikonien ulkoasua. Tämä sisältää vaihtoehtoja reunamarginaalille ja taustavärille.

- **Reunamarginaali**: `padding`-parametria voidaan käyttää hallitsemaan ikonille annettavaa reunamarginaalia. Hyväksytyt arvot vaihtelevat `0`:sta, jolloin reunamarginaalia ei ole, `1`:een, jolloin reunamarginaali on suurin.
  - **Esimerkki:** `/icons/icon-192x192.png?padding=0.3`

- **Taustaväri**: `background`-parametri mahdollistaa ikonin taustavärin asettamisen. Se tukee seuraavia arvoja:
  - **`transparent`**: Ei taustaväriä.
  <!-- vale off -->
  - **Heksadeemiset väriarvot**: Mukautetut taustavärit (esim. `FFFFFF` valkoiselle).
  <!-- vale on -->
  - **`auto`**: Yrittää automaattisesti havaita ikonille taustavärin.

  Esimerkiksi: 
  
  - **Esimerkki 1:** `/icons/icon-192x192.png?background=000000`
  - **Esimerkki 2:** `/icons/icon-192x192.png?background=auto`
