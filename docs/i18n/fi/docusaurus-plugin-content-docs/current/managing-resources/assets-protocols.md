---
sidebar_position: 2
title: Assets Protocols
description: >-
  Reference app resources with the webforJ ws, context, and icons protocols to
  load static files, classpath content, and dynamic icons.
_i18n_hash: 3928ba255cb9887c80c20f904baf62b8
---
webforJ tukee mukautettuja resurssiprotokollia, jotka mahdollistavat rakenteellisen ja tehokkaan resurssien käytön. Nämä protokollat abstrahoivat staattisten ja dynaamisten resurssien noutamisen monimutkaisuudet, varmistaen, että varat haetaan ja integroidaan saumattomasti sovellukseen.

## Verkkopalvelinprotokolla {#the-webserver-protocol}

**`ws://`**-protokolla mahdollistaa varojen noutamisen webforJ-sovelluksen staattisesta kansiosta. Kaikki tiedostot, jotka sijaitsevat sovelluksen luokkatiessä `src/main/resources/static`, ovat suoraan saatavilla selaimesta. Esimerkiksi, jos sinulla on tiedosto nimeltä **css/app.css** kansiossa **resources/static**, se voidaan saavuttaa osoitteessa: **`/static/css/app.css`**

**ws://**-protokolla poistaa tarpeen kovakoodata `static`-etuliite resurssien URL-osoitteisiin, suojaten muuttumisilta etuliitteiltä käyttöönoton yhteydessä. Jos verkkosovellus on käyttöönotettu kontekstissa, joka ei ole juuritaso, kuten **/mycontext**, URL-osoite **css/app.css**:lle olisi: **`/mycontext/static/css/app.css`**

:::tip Staattisen etuliitteen konfigurointi
Voit hallita `static`-etuliitettä käyttämällä [webforj-konfiguraatiota](../configuration/properties#configuration-options) vaihtoehtoa `webforj.assetsDir`. Tämä asetus määrittää reitin nimen, jota käytetään staattisten tiedostojen tarjoamiseen, kun taas **fyysinen kansio pysyy nimeltään `static`**. Tämä on erityisen hyödyllistä, jos oletusstaattinen reitti on ristiriidassa sovelluksesi reitin kanssa, sillä se mahdollistaa reitin nimen muuttamisen ilman kansion nimeämistä uudelleen.
:::

Voit käyttää <JavadocLink type="foundation" location="com/webforj/utilities/Assets" code='true'>Assets</JavadocLink> utilities-luokkaa, jotta voit ratkaista annetun verkkopalvelimen URL-osoitteen. Tämä voi olla hyödyllistä, jos sinulla on mukautettu komponentti, joka tarvitsee tukea tälle protokollalle.

```java
String url = Assets.resolveWebServerUrl("ws://js/app.js");
```

## Kontekstiprotokolla {#the-context-protocol}

Kontekstiprotokolla kartoittaa resursseja sovelluksen luokkatiessä `src/main/resources`. Jotkin resurssin API-menetelmät ja annotaatiot tukevat tätä protokollaa lukeakseen tiedoston sisältöä, joka sijaitsee resurssikansiossa, ja lähettääkseen sen sisällön selaimeen. Esimerkiksi `InlineJavaScript`-annotaatio mahdollistaa JavaScript-tiedoston sisällön lukemisen resurssikansiosta ja sen inline-käytön asiakaspäähän.

Esimerkiksi:

```java
String content = Assets.contentOf(
  Assets.resolveContextUrl("context://data/customers.json")
);
```

## Ikonitprotokolla {#the-icons-protocol}

**`icons://`**-protokolla tarjoaa dynaamisen lähestymistavan ikonien hallintaan, mikä tekee siitä tehokasta ja joustavaa ikoneiden generoimiseen ja tarjoamiseen [asennettavissa sovelluksissa](../configuration/installable-apps). Tämä protokolla mahdollistaa ikonien luomisen lennossa pyydetyn tiedoston nimen ja parametrien perusteella, poistaen tarpeen ennakkoon generoituja ikoneja monessa tapauksessa.

```java
Img icon = new Img("icons://icon-192x192.png")
```

### Perusikoni {#base-icon}

Kun ikonia pyydetään `icons://`-protokollalla, peruskuva johdetaan dynaamisesti pyydetystä ikonitiedoston nimestä. Tämä varmistaa ikonimuotoilun johdonmukaisuuden ja mahdollistaa automaattisen varautumisen oletuskuvaan, jos perusikonia ei ole saatavilla.

- **Esimerkki 1:** Pyyntö: `/icons/icon-192x192.png` → Perusikoni: `resources/icons/icon.png`
- **Esimerkki 2:** Pyyntö: `/icons/icon-different-192x192.png` → Perusikoni: `resources/icons/icon-different.png`

Jos pyydetylle ikonille ei ole olemassa peruskuvaa, oletus webforJ-logo käytetään varakuvaajana.

:::tip `webforj.iconsDir`
Oletuksena webforJ tarjoaa ikooneja `resources/icons/`-kansiosta. Voit muuttaa päätepisteen nimeä asettamalla `webforj.iconsDir` -ominaisuuden webforJ-konfiguraatiotiedostoon. Tämä muuttaa vain URL-päätepisteen, jota käytetään ikonien saavuttamiseen, ei varsinaisen kansion nimeä. Oletus päätepiste on `icons/`.
:::

### Ikojen ylikirjoittaminen {#overriding-icons}

Voit ylikirjoittaa erityiset ikonikoot asettamalla ennakkoon generoituja kuvia `resources/icons/`-kansioon. Tämä tarjoaa suuremman hallinnan ikonien ulkoasuun, kun tietyt koot tai tyylit tarvitsevat mukauttamista.

- **Esimerkki:** Jos `resources/icons/icon-192x192.png` on olemassa, se tarjoillaan suoraan sen sijaan, että se generoituisi dynaamisesti.

### Ikonien ulkoasun mukauttaminen {#customizing-icon-appearance}

`icons://`-protokolla tukee lisäparametreja, jotka mahdollistavat dynaamisesti luotujen ikonien ulkoasun mukauttamisen. Tämä sisältää vaihtoehtoja täytölle ja taustavärille.

- **Täyttö**: `padding`-parametria voidaan käyttää ohjaamaan täyttöä ikonin ympärillä. Hyväksyttävät arvot vaihtelevat `0`:sta, mikä tarkoittaa ei täyttöä, `1`:een, mikä tarkoittaa maksimitäyttöä.
  - **Esimerkki:** `/icons/icon-192x192.png?padding=0.3`

- **Taustaväri**: `background`-parametrilla voit asettaa ikonin taustavärin. Se tukee seuraavia arvoja:
  - **`transparent`**: Ei taustaväriä.
  <!-- vale off -->
  - **Heksadesimaalivärikoodit**: Mukautetut taustavärit (esim. `FFFFFF` valkoiselle).
  <!-- vale on -->
  - **`auto`**: Yrittää automaattisesti tunnistaa ikonin taustavärin.

  Esimerkiksi:

  - **Esimerkki 1:** `/icons/icon-192x192.png?background=000000`
  - **Esimerkki 2:** `/icons/icon-192x192.png?background=auto`
