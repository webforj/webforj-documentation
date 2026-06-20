---
title: Property Configuration
sidebar_position: 30
description: >-
  Set webforJ entry points, debug mode, locales, file upload limits, and servlet
  mappings through webforj.conf and web.xml.
sidebar_class_name: updated-content
_i18n_hash: c58a4908cfbde685bc0b30f6023e1df6
---
# webforJ-ominaisuuksien määrittäminen

Jotta webforJ-sovellus voidaan onnistuneesti ottaa käyttöön ja suorittaa, tarvitaan muutama keskeinen konfigurointitiedosto: `webforj.conf` ja `web.xml`. Kukin näistä tiedostoista hallitsee sovelluksen käyttäytymisen eri puolia, aloituspisteistä ja virheenkorjausasetuksista servlet-karttoihin.

## `webforj.conf` -tiedoston määrittäminen {#configuring-webforjconf}

`webforj.conf` -tiedosto on keskeinen konfigurointitiedosto webforJ:ssä, joka määrittää sovelluksen asetukset kuten aloituspisteet, virheenkorjaustilan ja asiakas-palvelin vuorovaikutuksen. Tiedosto on [HOCON-muodossa](https://github.com/lightbend/config/blob/master/HOCON.md) ja sen tulisi sijaita `resources`-hakemistossa.

:::tip
Jos integroit [Springin](../integrations/spring/overview.md) kanssa, voit asettaa nämä `webforj.conf` -ominaisuudet `application.properties` -tiedostoon.
:::

### Esimerkki `webforj.conf` -tiedostosta {#example-webforjconf-file}

```Ini
# Tämä konfigurointitiedosto on HOCON-muodossa:
# https://github.com/lightbend/config/blob/master/HOCON.md

webforj.entry = com.webforj.samples.Application
webforj.debug = true
webforj.reloadOnServerError = on
webforj.clientHeartbeatRate = 1s
```

### Määrittelyvaihtoehdot {#configuration-options}

| Ominaisuus                             | Tyyppi    | Selitys                                                       | Oletus                |
|--------------------------------------|---------|-------------------------------------------------------------------|------------------------|
| **`webforj.assetsCacheControl`**     | Merkkijono  | Cache-Control-pääte staattisille resursseille.                        | `null` |
| **`webforj.assetsDir`**              | Merkkijono  | Reitinnimi, jota käytetään staattisten tiedostojen palvelemiseen, kun taas todellinen kansion nimi on edelleen `static`. Tämä konfigurointi on hyödyllinen, jos oletus `static`-reitti on ristiriidassa sovelluksessa määritellyn reitin kanssa, jolloin voit muuttaa reitinnimeä muuttamatta itse kansion nimeä.       | `null`               |
| **`webforj.assetsExt`**              | Merkkijono  | Oletustiedostopääte staattisille tiedostoille. | `null` |
| **`webforj.assetsIndex`**            | Merkkijono  | Oletustiedosto, joka palvellaan hakupyynnöissä (esim. index.html). | `null` |
| **`webforj.clientHeartbeatRate`**    | Merkkijono  | Aikaväli, jonka aikana asiakas lähettää palvelimelle pingin selvittääkseen, onko se edelleen aktiivia. Kehityksessä aseta tämä lyhyemmäksi aikaväliksi, esimerkiksi `8s`, jotta voit nopeasti havaita palvelimen saatavuuden. Aseta tuotannossa 50 sekuntia tai pidemmäksi, jotta vältetään liialliset pyynnöt. | `50s`           |
| **`webforj.components`**             | Merkkijono  | Kun määritetään, peruspolku määrää, mistä DWC-komponentit ladataan. Oletusarvoisesti komponentit ladataan sovellusta isännöivältä palvelimelta. Kustomoidun peruspolun määrittäminen mahdollistaa komponenttien lataamisen vaihtoehtoiselta palvelimelta tai CDN:ltä. Esimerkiksi ladataksesi komponentteja jsdelivr.comista, aseta peruspolku: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version} On tärkeää, että ladatut komponentit ovat yhteensopivia käytössä olevan webforJ-kehyksen version kanssa; muuten sovellus ei ehkä toimi odotetusti. Tämä asetus ohitetaan, kun käytetään standardia BBj-asennusta ilman moottoria. Standardissa BBj-asennuksessa asetus voidaan hallita `!COMPONENTS` STBL:n avulla. | `null`          |
| **`webforj.debug`**                  | Boolean | Aktivoi virheenkorjaustilan. Virheenkorjaustilassa webforJ tulostaa lisätietoja konsoliin ja näyttää kaikki poikkeukset selaimessa. Virheenkorjaustila on oletusarvoisesti pois päältä. | `null`          |
| **`webforj.entry`**                  | Merkkijono  | Määrittää sovelluksen aloituspisteen ilmoittamalla luokan täydellisen nimen, joka laajentaa `webforj.App`:ia. Jos aloituspistettä ei määritetä, webforJ skannaa automaattisesti luokkaväylän luokkia, jotka laajentavat `webforj.App`:ia. Jos useita luokkia löydetään, virhe tapahtuu. Kun paketti sisältää enemmän kuin yhden mahdollisen aloituspisteen, tämän asettaminen nimenomaisesti on tarpeen epätietoisuuden estämiseksi, tai vaihtoehtoisesti `AppEntry`-annotaatiota voidaan käyttää aloituspisteen määrittämiseen suoritusvaiheessa. | `null`          |
| **`webforj.i18n.supported-locales`**&nbsp;<DocChip chip='since' label='25.12' /> | Lista | Tuettujen paikallisten kielten lista BCP 47 -kielitagien muodossa (esim. `"en"`, `"en-US"`, `"fr"`, `"de-DE"`). Kun automaattinen tunnistus on käytössä, selaimen suosituimmat paikalliset kielet verrataan tähän lista. Lista ensimmäisestä paikallisesta kielestä käytetään oletusvaratehtävänä. Katso [Käännös](../advanced/i18n-localization.md). | `[]` |
| **`webforj.i18n.auto-detect`**&nbsp;<DocChip chip='since' label='25.12' /> | Boolean | Kun `true`, sovelluksen paikallinen kieli asetetaan automaattisesti selaimen suosituimmasta kielestä käynnistyksessä. Paikallinen kieli ratkaistaan vertaamalla selaimen suosituimpia kieliä `supported-locales` -listaan. Kun `false` tai kun `supported-locales` on tyhjät, sovellus käyttää `webforj.locale`:a. Katso [Käännös](../advanced/i18n-localization.md). | `false` |
| **`webforj.fileUpload.accept`**      | Lista    | Sallittujen tiedostotyyppien lista tiedostojen lataukselle. Oletusarvoisesti kaikki tiedostotyypit ovat sallittuja. Tuetut muodot sisältävät MIME-tyypit kuten `image/*`, `application/pdf`, `text/plain`, tai tiedostopäätteet kuten `*.txt`. Kun käytetään standardia BBj-asennusta, tämä asetus ohitetaan ja hallitaan `fileupload-accept.txt`:llä. | `[]`            |
| **`webforj.fileUpload.maxSize`**     | Pitkä    | Suurin sallittu tiedostokoko tiedostojen latauksille, tavuina. Oletusarvoisesti ei ole rajoituksia. Kun käytetään standardia BBj-asennusta, tämä asetus ohitetaan ja hallitaan `fileupload-accept.txt`:llä. | `null`          |
| **`webforj.iconsDir`**               | Merkkijono  | URL-päätepiste ikoni-hakemistolle (oletus palvelee `resources/icons/`-hakemistoa). | `icons/` |
| **`webforj.legacyHtmlInText`**&nbsp;<DocChip chip='since' label='26.01' /> | Boolean | Kun `true`, `<html>`:n sisällä oleva arvo renderöi sen sisällön HTML:nä. Kun `false`, sama arvo näytetään kirjaimellisesti. | `true` |
| **`webforj.license.cfg`**            | Merkkijono  | Lisenssikonfiguraation hakemisto. Oletusarvoisesti se on sama kuin webforJ-konfiguraation hakemisto, mutta tämän voi muuttaa tarvittaessa. | `"."`  |
| **`webforj.license.startupTimeout`** | Kokonaisluku | Lisenssins käynnistys-aika raja sekunneissa. | `null` |
| **`webforj.locale`**                 | Merkkijono  | Sovelluksen alue, joka määrittää kielen, alueasetukset ja muotoilut päivämäärille, ajoille ja numeroille. | `null` |
| **`webforj.quiet`**                  | Boolean | Poistaa latauskuvion käytöstä sovelluksen käynnistämisen aikana. | `false` |
| **`webforj.reloadOnServerError`**    | Boolean | **Vain kehitysympäristöissä.** Kehitysympäristössä, sivu ladataan automaattisesti virheiden yhteydessä, jotka liittyvät kuumaan uudelleenkäynnistykseen, mutta ei muihin virhetyyppeihin. Kun käytetään kuumasuojausta, jos asiakas lähettää pyynnön palvelimelle, kun se on käynnistämässä uudelleen, virhe voi tapahtua WAR-tiedostoa vaihdettaessa. Koska palvelin todennäköisesti tulee takaisin verkkoon pian, tämä asetus sallii asiakkaan yrittää ladata sivun automaattisesti.  | `false` |
| **`webforj.security.maxContentLength`**&nbsp;<DocChip chip='since' label='25.10' /> | Kokonaisluku | Suurin pyyntö, jonka sovellus hyväksyy, tavuina, suojana ylisuuria pyyntöjä vastaan, jotka on tarkoitettu palvelemisen muistia tyhjentämiseksi. Aseta `0` poistaaksesi rajoituksen. | `0` |
| **`webforj.security.maxInitPerMinute`**&nbsp;<DocChip chip='since' label='25.10' /> | Kokonaisluku | Kuinka monta uutta sovellussessiota sovellus aloittaa joka minuutti, suojana nopean sessioiden luomisen estämiseksi, joka on tarkoitettu palvelemisen resurssien tyhjentämiseksi. Aseta `0` poistaaksesi nopeusrajoituksen. | `0` |
| **`webforj.servlets[n].name`**       | Merkkijono  | Servletin nimi (käyttää luokan nimeä, jos ei määritetä). | `null` |
| **`webforj.servlets[n].className`**  | Merkkijono | Servletin täydellinen luokan nimi. | `null` |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Servletin alustusparametrit. | `null` |
| **`webforj.sessionTimeout`**         | Kokonaisluku | Istunnon aikaraja sekunneissa. | `60` |
| **`webforj.stringTable`**            | `Map<String,String>` | Avain-arvo-parit, jotka käytetään merkkijonojen tallentamiseen sovelluksessa. Hyödyllinen sovelluksen viestien tai etikettien tallentamisessa. Lisätietoja `StringTable`:sta löytyy [täältä](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`            |
| **`webforj.mime.extensions`**            | `Map<String,String>` | Räätälöidyt MIME-tyypin kartat tiedostopäätteille staattisten tiedostojen palvelemisessa. Mahdollistaa oletus MIME-tyyppien ylikirjoittamisen tai MIME-tyyppien määrittämisen mukautetuille päätteille. Karttaavain on tiedostopäätteen (ilman pistettä) ja arvo on MIME-tyyppi. | `{}`            |

## `web.xml` -tiedoston määrittäminen {#configuring-webxml}

`web.xml` -tiedosto on olennainen konfigurointitiedosto Java-verkkosovelluksille, ja webforJ:ssä se määrittää tärkeitä asetuksia, kuten servlet-konfiguraation, URL-mallit ja tervetuloissivut. Tämän tiedoston tulisi sijaita projektisi käyttöönottoarkkitehtuurin `WEB-INF`-hakemistossa.

| Asetus                                 | Selitys                                                                                                                                                                                   | Oletusarvo               |
| --------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------- |
| **`<display-name>`**                    | Asettaa sovelluksen näytön nimen, joka tyypillisesti johdetaan projektin nimestä. Tämä nimi näkyy sovelluspalvelinten hallintakonsolissa.                                                        | `${project.name}`           |
| **`<servlet>` ja `<servlet-mapping>`** | Määrittää `WebforjServlet`, ydin servletin, joka käsittelee webforJ-pyyntöjä. Tämä servlet on kartoitettuna kaikkiin URL-osoitteisiin (`/*`), mikä tekee siitä pääasiallisen pääsyn verkkopyynnöille.                     | `WebforjServlet`            |
| **`<load-on-startup>`**                 | Määrittää, että `WebforjServlet` tulisi ladata, kun sovellus käynnistyy. Asettamalla tämä arvoon `1` servlet ladataan heti, mikä parantaa ensimmäisten pyyntöjen käsittelyä.                | `1`                         |
