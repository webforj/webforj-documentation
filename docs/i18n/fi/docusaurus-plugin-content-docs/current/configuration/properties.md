---
title: Property Configuration
sidebar_position: 30
description: >-
  Set webforJ entry points, debug mode, locales, file upload limits, and servlet
  mappings through webforj.conf and web.xml.
_i18n_hash: 2eb59302da44bcdd27d6366419bd78ad
---
# webforJ-ominaisuuksien määrittäminen

Jotta webforJ-sovellus voidaan onnistuneesti ottaa käyttöön ja käyttää, tarvitaan muutamia keskeisiä määritystiedostoja: `webforj.conf` ja `web.xml`. Jokainen näistä tiedostoista ohjaa sovelluksen käyttäytymistä eri tavoilla, kuten sisäänkäyntipisteitä ja virheiden vianmääritysasetuksia sekä servlet-ratkaisuja.

## `webforj.conf` määrittäminen {#configuring-webforjconf}

`webforj.conf` -tiedosto on keskeinen määritystiedosto webforJ:ssä, joka määrittää sovelluksen asetuksia, kuten sisäänkäyntipisteet, virheenkorjaustilan ja asiakas-palvelin -vuorovaikutuksen. Tiedosto on [HOCON-muodossa](https://github.com/lightbend/config/blob/master/HOCON.md) ja sen tulisi sijaita `resources`-hakemistossa.

:::tip
Jos integroituu [Springin](../integrations/spring/overview.md) kanssa, voit asettaa nämä `webforj.conf` -ominaisuudet `application.properties` -tiedostossa.
:::


### Esimerkki `webforj.conf` -tiedostosta {#example-webforjconf-file}

```Ini
# Tämä määritystiedosto on HOCON-muodossa:
# https://github.com/lightbend/config/blob/master/HOCON.md

webforj.entry = com.webforj.samples.Application
webforj.debug = true
webforj.reloadOnServerError = on
webforj.clientHeartbeatRate = 1s
```

### Määritysasetukset {#configuration-options}

| Ominaisuus                             | Tyyppi    | Selitys                                                       | Oletusarvo                |
|--------------------------------------|---------|-------------------------------------------------------------------|------------------------|
| **`webforj.assetsCacheControl`**     | Merkkijono  | Cache-Control-otsake staattisten resurssien varten.                        | `null` |
| **`webforj.assetsDir`**              | Merkkijono  | Reittinimi, jota käytetään staattisten tiedostojen tarjoamiseen, kun taas todellinen kansion nimi pysyy `static`. Tämä määritys on hyödyllinen, jos oletusreitti `static` on ristiriidassa sovelluksessa määritellyn reitin kanssa, jolloin voit muuttaa reitin nimeä ilman kansion nimeämistä uudelleen.       | `null`               |
| **`webforj.assetsExt`**              | Merkkijono  | Oletustiedostopääte staattisille tiedostoille. | `null` |
| **`webforj.assetsIndex`**            | Merkkijono  | Oletustiedosto, joka palvellaan hakupyyntöjen yhteydessä (esim. index.html). | `null` |
| **`webforj.clientHeartbeatRate`**    | Merkkijono  | Väli, jonka aikana asiakas pingaa palvelinta varmistaakseen, että se on yhä elossa. Kehityksessä voit asettaa tämän lyhyemmäksi ajaksi, esimerkiksi `8s`, havaitaksesi palvelimen saatavuuden nopeasti. Aseta 50 sekuntia tai enemmän tuotannossa liiallisten pyyntöjen välttämiseksi. | `50s`           |
| **`webforj.components`**             | Merkkijono  | Määriteltynä peruspolku, joka määrää, mistä DWC-komponentit ladataan. Oletuksena komponentit ladataan sovellusta isännöivältä palvelimelta. Kustomoidun peruspolun asettaminen mahdollistaa komponenttien lataamisen vaihtoehtoiselta palvelimelta tai CDN:ltä. Esimerkiksi komponenttien lataamiseksi jsdelivr.com-sivustolta, aseta peruspolku: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version}. On tärkeää, että ladatut komponentit ovat yhteensopivia käytössä olevan webforJ-kehyksen version kanssa; muuten sovellus ei ehkä toimi odotetusti. Tämä asetusta ei oteta huomioon, kun käytetään tavallista BBj-asennusta ilman moottoria. Tavallisessa BBj-asennuksessa tätä asetusta voidaan hallita `!COMPONENTS` STBL:llä. | `null`          |
| **`webforj.debug`**                  | Boolean | Ota käyttöön virheenkorjaustila. Virheenkorjaustilassa webforJ tulostaa lisätietoja konsoliin ja näyttää kaikki poikkeukset selaimessa. Virheenkorjaustila on oletuksena pois päältä. | `null`          |
| **`webforj.entry`**                  | Merkkijono  | Määrittää sovelluksen sisäänkäyntipisteen määrittämällä täysin kvalifioitu nimen luokalle, joka laajentaa `webforj.App`. Jos sisäänkäyntipistettä ei ole määritetty, webforJ skannaa automaattisesti luokkareitin löytääkseen luokat, jotka laajentavat `webforj.App`. Jos useita luokkia löytyy, virhe ilmenee. Kun paketti sisältää enemmän kuin yhden potentiaalisen sisäänkäyntipisteen, sen määrittäminen erikseen on tarpeen epäselvyyksien välttämiseksi, tai vaihtoehtoisesti `AppEntry`-annotaatiota voidaan käyttää määrittämään sisäänkäyntipiste käytön aikana. | `null`          |
| **`webforj.i18n.supported-locales`**&nbsp;<DocChip chip='since' label='25.12' /> | Lista | Lista tuetuista alueista BCP 47 kielitagien muodossa (esim. `"en"`, `"en-US"`, `"fr"`, `"de-DE"`). Kun automaattinen havaitseminen on käytössä, selaimen suosikit alueet verrataan tähän listaan. Listan ensimmäistä aluetta käytetään oletusvarayksikkönä. Katso [Käännös](../advanced/i18n-localization.md). | `[]` |
| **`webforj.i18n.auto-detect`**&nbsp;<DocChip chip='since' label='25.12' /> | Boolean | Kun `true`, sovelluksen alue määritetään automaattisesti selaimen suosikista kielellä käynnistyksen aikana. Alue ratkaistaan vertaamalla selaimen suosikkialueita `supported-locales` -listaan. Kun `false` tai kun `supported-locales` on tyhjennetty, sovellus käyttää `webforj.locale`:a. Katso [Käännös](../advanced/i18n-localization.md). | `false` |
| **`webforj.fileUpload.accept`**      | Lista    | Sallittuja tiedostotyyppejä tiedostojen lataamista varten. Oletuksena kaikki tiedostotyypit ovat sallittuja. Tuetut muodot sisältävät MIME-tyypit, kuten `image/*`, `application/pdf`, `text/plain`, tai tiedostopäätteet, kuten `*.txt`. Kun käytetään tavallista BBj-asennusta, tätä asetusta ei oteta huomioon ja se hallitaan `fileupload-accept.txt`:n kautta. | `[]`            |
| **`webforj.fileUpload.maxSize`**     | Pitkä    | Suurin tiedostokoko, joka on sallittu tiedostojen lataamisessa tavuina. Oletuksena rajoituksia ei ole. Kun käytetään tavallista BBj-asennusta, tätä asetusta ei oteta huomioon ja se hallitaan `fileupload-accept.txt`:n kautta. | `null`          |
| **`webforj.iconsDir`**               | Merkkijono  | URL-päätepiste kuvakkeiden hakemistolle (oletuspalvelu `resources/icons/`-hakemistosta). | `icons/` |
| **`webforj.license.cfg`**            | Merkkijono  | Lisenssimääritysten hakemisto. Oletuksena se on sama kuin webforJ-määrityshakemisto, mutta tätä voidaan mukauttaa tarpeen mukaan. | `"."`  |
| **`webforj.license.startupTimeout`** | Integer | Lisenssin käynnistysaika sekunneissa. | `null` |
| **`webforj.locale`**                 | Merkkijono  | Sovelluksen alue, joka määrittää kielen, alueasetukset sekä päivämäärien, aikojen ja numeroiden muodot. | `null` |
| **`webforj.quiet`**                  | Boolean | Poistaa latauskuvan käytöstä sovelluksen käynnistyksen aikana. | `false` |
| **`webforj.reloadOnServerError`**    | Boolean | **Kehitysympäristöt vain.** Kehitysympäristössä automaattisesti lataa sivu virhetilanteissa, jotka liittyvät kuumaan uudelleenkäynnistykseen, mutta ei muiden virhetietotyypin kohdalla. Kun käytetään kuumaa uudelleenkäynnistystä, jos asiakas lähettää pyynnön palvelimelle yhtä aikaa, kun se on käynnistämässä, virhe voi ilmetä, kun WAR-tiedostoa vaihdetaan. Koska palvelin todennäköisesti on pian jälleen käytettävissä, tämä asetus mahdollistaa asiakkaan yrittää ladata sivun automaattisesti.  | `false` |
| **`webforj.security.maxContentLength`**&nbsp;<DocChip chip='since' label='25.10' /> | Integer | Suurin pyyntö, jonka sovellus hyväksyy, tavuina, suojana ylisuuriin pyyntöihin, jotka on tarkoitettu loppuottamaan palvelimen muisti. Aseta `0` poistaaksesi rajoituksen. | `0` |
| **`webforj.security.maxInitPerMinute`**&nbsp;<DocChip chip='since' label='25.10' /> | Integer | Kuinka monta uutta sovellusistuntoa sovellus aloittaa minuutissa, suojana nopeille istuntojen luomisille, jotka tähtäävät palvelinresurssien loppuun. Aseta `0` poistaaksesi aikarajoituksen. | `0` |
| **`webforj.servlets[n].name`**       | Merkkijono  | Servletin nimi (käyttää luokan nimeä, jos ei määritetty). | `null` |
| **`webforj.servlets[n].className`**  | Merkkijono | Servletin täysin kvalifioitu luokan nimi. | `null` |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Servletin alustamisparametrit. | `null` |
| **`webforj.sessionTimeout`**         | Integer | Istunnon aikakatkaisu sekunneissa. | `60` |
| **`webforj.stringTable`**            | `Map<String,String>` | Avain-arvo-pareja, joita käytetään merkkijonojen tallentamiseen sovelluksessa. Hyödyllinen sovellusviestien tai -etikettien tallentamiseen. Lisätietoa `StringTable`:sta löytyy [täältä](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`            |
| **`webforj.mime.extensions`**            | `Map<String,String>` | Mukautetut MIME-tyyppikartoitukset tiedostopäätteille staattisten tiedostojen palveluja varten. Mahdollistaa oletus MIME-tyyppien ylittämisen tai mukautettujen päätteiden MIME-tyyppien määrittelyn. Kartan avain on tiedostopäätteen, ilman pistettä, ja arvo on MIME-tyyppi. | `{}`            |

## `web.xml` määrittäminen {#configuring-webxml}

`web.xml` -tiedosto on olennaisesti määrittänyt tiedosto Java-verkkosovelluksille, ja webforJ:ssä se määrittelee tärkeitä asetuksia, kuten servlet-määritykset, URL-mallit ja tervetulossivut. Tämän tiedoston tulisi sijaita projektin `WEB-INF`-hakemistossa.

| Asetus                                 | Selitys                                                                                                                                                                                   | Oletusarvo               |
| --------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------- |
| **`<display-name>`**                    | Määrittää verkkosovelluksen näyttönimen, joka yleensä on johdettu projektin nimestä. Tämä nimi näkyy sovelluspalvelimien hallintakonsolissa.                                                        | `${project.name}`           |
| **`<servlet>` ja `<servlet-mapping>`** | Määrittää `WebforjServlet`:in, ydinasemman webforJ-pyyntöjen käsittelyyn. Tämä servlet on kartoitettu kaikkiin URL-osoitteisiin (`/*`), mikä tekee siitä pääsisäänkäynnin verkkopyynnöille.                     | `WebforjServlet`            |
| **`<load-on-startup>`**                 | Määrittää, että `WebforjServlet` pitäisi ladata sovelluksen käynnistyessä. Kun tämä asetetaan arvoon `1`, servlet ladataan välittömästi, mikä parantaa alkuperäisten pyyntöjen käsittelyä.                | `1`                         |
