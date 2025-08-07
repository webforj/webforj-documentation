---
sidebar_position: 2
title: Assets Protocols
_i18n_hash: b37158687b15dc2b94a7543624eaa09f
---
webforJ tukee mukautettuja omaisuuspöytäkirjoja, jotka mahdollistavat rakenteellisen ja tehokkaan resurssien käytön. Nämä pöytäkirjat abstraktoivat staattisten ja dynaamisten resurssien palauttamisen monimutkaisuudet varmistaen, että omaisuudet haetaan ja integroidaan saumattomasti sovellukseen.

## The webserver protocol {#the-webserver-protocol}

**`ws://`**-pöytäkirja mahdollistaa omaisuuksien hakemisen webforJ-sovelluksen staattisesta kansiosta. Kaikki tiedostot, jotka sijaitsevat sovelluksen luokkatiellä `src/main/resources/static`, ovat suoraan saavutettavissa selaimesta. Esimerkiksi, jos sinulla on tiedosto nimeltä **css/app.css** kansiossa **resources/static**, se voidaan saavuttaa osoitteessa: **`/static/css/app.css`**

**ws://**-pöytäkirja poistaa tarpeen kovakoodata `static`-etuliite resurssi-URL-osoitteisiisi, suojaten muutoksilta etuliitteissä riippuen käyttöyhteydestä. Jos web-sovellus on julkaistu kontekstissa, joka ei ole juurisijainti, kuten **/mycontext**, osoite **css/app.css**:lle olisi: **`/mycontext/static/css/app.css`**

:::tip Configure the static prefix
Voit hallita `static`-etuliitettä käyttämällä [webforj-konfigurointia](../configuration/properties#configuration-options) asetusta `webforj.assetsDir`. Tämä asetus määrittää reitin nimen, jota käytetään staattisten tiedostojen tarjoamiseen, samalla kun **fyysinen kansio pysyy nimeltään `static`**. Tämä on erityisen hyödyllinen, jos oletusstaattinen reitti on ristiriidassa sovelluksesi reitin kanssa, sillä se mahdollistaa reitin nimen muuttamisen ilman kansion nimeämistä uudelleen.
:::

Voit käyttää <JavadocLink type="foundation" location="com/webforj/utilities/Assets" code='true'>Assets</JavadocLink> -apuluokkaa ratkaistaksesi annetun web-palvelimen URL-osoitteen. Tämä voi olla hyödyllistä, jos sinulla on mukautettu komponentti, joka tarvitsee tukea tälle pöytäkirjalle.

```java
String url = Assets.resolveWebServerUrl("ws://js/app.js");
```

## The context protocol {#the-context-protocol}

Kontekstipöytäkirja vastaa resursseista sovelluksen luokkatiellä `src/main/resources`. Jotkut resurssin API-menetelmät ja annotaatiot tukevat tätä pöytäkirjaa lukeakseen tiedoston sisältöä resursseista ja lähettääkseen sen sisällön selaimelle. Esimerkiksi `InlineJavaScript`-annotaatio mahdollistaa JavaScript-tiedoston sisällön lukemisen resurssikansiosta ja sen inline-tyylin käyttö asiakaspuolella.

Esimerkiksi:

```java
String content = Assets.contentOf(
  Assets.resolveContextUrl("context://data/customers.json")
);
```

## The icons protocol {#the-icons-protocol}

**`icons://`**-pöytäkirja tarjoaa dynaamisen lähestymistavan ikonien hallintaan, mikä tekee siitä tehokkaan ja joustavan ikonien luomiseen ja tarjoamiseen [asennettavissa sovelluksissa](../configuration/installable-apps). Tämä pöytäkirja mahdollistaa ikonien luomisen lennossa pyydetyn tiedostonimen ja parametrien perusteella, vähentäen monissa tapauksissa ennakolta luodun ikonitavan tarvetta.

```java
Img icon = new Img("icons://icon-192x192.png")
```

### Base icon {#base-icon}

Kun ikonia pyydetään käyttäen `icons://`-pöytäkirjaa, peruskuva johdetaan dynaamisesti pyydetystä ikonin tiedostonimestä. Tämä varmistaa ikonimuotoilun johdonmukaisuuden ja mahdollistaa automaattisen varayhteyden oletuskuvaan, jos perusikonia ei ole tarjolla.

- **Esimerkki 1:** Pyyntö: `/icons/icon-192x192.png` → Perusikoni: `resources/icons/icon.png`
- **Esimerkki 2:** Pyyntö: `/icons/icon-different-192x192.png` → Perusikoni: `resources/icons/icon-different.png`

Jos peruskuvaa ei ole olemassa pyydetylle ikonille, käytetään oletus webforJ -logo varayhteytenä.

:::tip `webforj.iconsDir`
Oletusarvoisesti webforJ tarjoaa ikoneita `resources/icons/`-kansiosta. Voit muuttaa päätepisteen nimeä asettamalla `webforj.iconsDir` -ominaisuuden webforJ-konfiguraatiotiedostoon. Tämä muuttaa vain URL-päätepisteen, jota käytetään ikoneiden hakemiseen, ei varsinaista kansion nimeä. Oletuspäätepiste on `icons/`.
:::

### Overriding Icons {#overriding-icons}

Voit ylittää tietyt ikonikooot sijoittamalla ennakkoluodut kuvat `resources/icons/`-kansioon. Tämä antaa suuremman hallinnan ikonien ulkonäköön, kun tietyt koot tai tyylit vaativat mukauttamista.

- **Esimerkki:** Jos `resources/icons/icon-192x192.png` on olemassa, se palvelee suoraan sen sijaan, että se luotaisiin dynaamisesti.

### Customizing icon appearance {#customizing-icon-appearance}

`icons://`-pöytäkirja tukee lisäparametreja, jotka mahdollistavat dynaamisesti luotujen ikonien ulkonäön mukauttamisen. Tämä sisältää vaihtoehdot tyhjentämiselle ja taustavärille.

- **Tyhjennys**: `padding`-parametriä voidaan käyttää säätämään tilaa ikonien ympärillä. Hyväksyttävät arvot vaihtelevat `0`, mikä tarkoittaa, että tilaa ei ole, ja `1`, mikä tarkoittaa suurinta tilaa.
  - **Esimerkki:** `/icons/icon-192x192.png?padding=0.3`
  
- **Taustaväri**: `background`-parametri mahdollistaa ikonien taustavärin asettamisen. Se tukee seuraavia arvoja:
  - **`transparent`**: Ei taustaväriä.
  <!-- vale off -->
  - **Heeksadesimaaliset väri-koodit**: Mukautetut taustavärit (esim. `FFFFFF` valkoiselle).
  <!-- vale on -->
  - **`auto`**: Yrittää automaattisesti tunnistaa ikonin taustavärin.

Esimerkiksi: 
  
  - **Esimerkki 1:** `/icons/icon-192x192.png?background=000000`
  - **Esimerkki 2:** `/icons/icon-192x192.png?background=auto`
