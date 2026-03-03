---
title: Property Configuration
sidebar_position: 30
_i18n_hash: 668649d2e0f92ebc4012e0c58cd1b706
---
# webforJ-ominaisuuksien konfigurointi

Jotta webforJ-sovellus voidaan onnistuneesti tehdä käyttöön ja ajaa, tarvitaan muutamia keskeisiä konfiguraatiotiedostoja: `webforj.conf` ja `web.xml`. Kummatkin tiedostot ohjaavat sovelluksen käyttäytymisen eri osa-alueita, kuten sisäänkäyntejä ja virheenkorjausasetuksia sekä servlettikartoituksia.

## `webforj.conf` konfigurointi {#configuring-webforjconf}

`webforj.conf` tiedosto on keskeinen konfiguraatiotiedosto webforJ:ssä, joka määrittää sovelluksen asetuksia, kuten sisäänkäynnit, virheenkorjaustilan ja asiakas-palvelin vuorovaikutuksen. Tiedosto on [HOCON-muodossa](https://github.com/lightbend/config/blob/master/HOCON.md) ja sen tulisi sijaita `resources`-hakemistossa.

:::tip
Jos integroit [Springin](../integrations/spring/overview.md) kanssa, voit asettaa nämä `webforj.conf` ominaisuudet `application.properties`-tiedostoon.
:::



### Esimerkkitiedosto `webforj.conf` {#example-webforjconf-file}

```Ini
# Tämä konfiguraatiotiedosto on HOCON-muodossa:
# https://github.com/lightbend/config/blob/master/HOCON.md

webforj.entry = com.webforj.samples.Application
webforj.debug = true
webforj.reloadOnServerError = on
webforj.clientHeartbeatRate = 1s
```

### Konfigurointivaihtoehdot {#configuration-options}

| Ominaisuus                             | Tyyppi    | Selitys                                                       | Oletusarvo                |
|--------------------------------------|---------|-------------------------------------------------------------------|------------------------|
| **`webforj.assetsCacheControl`**     | Merkkijono  | Cache-Control-otsake staattisia resursseja varten.                        | `null` |
| **`webforj.assetsDir`**              | Merkkijono  | Reittinimi, jota käytetään staattisten tiedostojen toimittamiseen, kun taas itse hakemiston nimi pysyy `static`. Tämä konfiguraatio on hyödyllinen, jos oletus `static` reitti on ristiriidassa sovelluksessa määritellyn reitin kanssa, jolloin voit muuttaa reittinimen nimeämättä hakemistoa uudelleen.       | `null`               |
| **`webforj.assetsExt`**              | Merkkijono  | Oletustiedostotunnus staattisille tiedostoille. | `null` |
| **`webforj.assetsIndex`**            | Merkkijono  | Oletustiedosto, joka toimitetaan hakemistopyynnöille (esim. index.html). | `null` |
| **`webforj.clientHeartbeatRate`**    | Merkkijono  | Aikaväli, jolla asiakas pingaa palvelinta selvittääkseen, onko se yhä toiminnassa. Kehityksessä tämä tulisi asettaa lyhyemmäksi aikaväliksi, esimerkiksi `8s`, jotta palvelimen saatavuus voidaan nopeasti havaita. Tuotannossa se tulisi asettaa 50 sekunniksi tai pidemmäksi, jotta vältetään liialliset pyynnöt. | `50s`           |
| **`webforj.components`**             | Merkkijono  | Kun määritetään, peruspolku määrittää, mistä DWC-komponentteja ladataan. Oletusarvoisesti komponentit ladataan sovellusta isännöivältä palvelimelta. Kuitenkin, mukautetun peruspolun asettaminen mahdollistaa komponenttien lataamisen vaihtoehtoiselta palvelimelta tai CDN:ltä. Esimerkiksi, ladataksesi komponentteja jsdelivr.com-sivustolta, aseta peruspolku: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version} On tärkeää, että ladatut komponentit ovat yhteensopivia käytössä olevan webforJ-kehyksen version kanssa; muuten sovellus ei ehkä toimi odotetusti. Tämä asetusta ei huomioida, kun käytetään standardia BBj-asennusta ilman moottoria. Standardissa BBj-asennuksessa asetusta voidaan hallita `!COMPONENTS` STBL:n avulla. | `null`          |
| **`webforj.debug`**                  | Boolean | Ota käyttöön virheenkorjaustila. Virheenkorjaustilassa webforJ tulostaa lisätietoja konsoliin ja näyttää kaikki poikkeukset selaimessa. Virheenkorjaustila on oletusarvoisesti pois päältä. | `null`          |
| **`webforj.entry`**                  | Merkkijono  | Määrittää sovelluksen sisäänkäynnin määrittämällä täysin kvalifioidun nimen luokasta, joka laajentaa `webforj.App`. Jos sisäänkäyntiä ei määritetä, webforJ skannaa automaattisesti luokkareittiä luokkia, jotka laajentavat `webforj.App`. Jos useita luokkia löytyy, virhe tapahtuu. Kun paketissa on enemmän kuin yksi potentiaalinen sisäänkäynti, tämän määrittäminen eksplisiittisesti on tarpeen epäselvyyksien estämiseksi, tai vaihtoehtoisesti `AppEntry`-annotaatiota voidaan käyttää sisäänkäynnin määrittämiseen ajoittain. | `null`          |
| **`webforj.i18n.supported-locales`**&nbsp;<DocChip chip='since' label='25.12' /> | Luettelo | Luettelo tuetuista alueista BCP 47 kielen tunnisteina (esim. `"en"`, `"en-US"`, `"fr"`, `"de-DE"`). Kun automaattinen havaitseminen on käytössä, selaimen suosituimmat alueet verrataan tähän luetteloon. Lista ensimmäinen alue käytetään oletusvarantoina. Katso [Käännös](../advanced/i18n-localization.md). | `[]` |
| **`webforj.i18n.auto-detect`**&nbsp;<DocChip chip='since' label='25.12' /> | Boolean | Kun `true`, sovelluksen alue asetetaan automaattisesti selaimen suosituista kielistä käynnistyksen yhteydessä. Alue ratkaistaan vertaamalla selaimen suosituimmat alueet `supported-locales` luetteloon. Kun `false` tai `supported-locales` on tyhjällä, sovellus käyttää `webforj.locale`. Katso [Käännös](../advanced/i18n-localization.md). | `false` |
| **`webforj.fileUpload.accept`**      | Luettelo | Sallitut tiedostotyypit tiedostojen lataamiseen. Oletusarvoisesti kaikki tiedostotyypit ovat sallittuja. Tuettuja muotoja ovat MIME-tyypit, kuten `image/*`, `application/pdf`, `text/plain`, tai tiedostopäätteet, kuten `*.txt`. Kun käytät standardia BBj-asennusta, tätä asetusta ei huomioida ja sitä hallitaan `fileupload-accept.txt` avulla. | `[]`            |
| **`webforj.fileUpload.maxSize`**     | Pitkä    | Suurin tiedostokokoraja tiedostojen lataamiselle tavuina. Oletusarvoisesti rajoitusta ei ole. Kun käytät standardia BBj-asennusta, tätä asetusta ei huomioida ja sitä hallitaan `fileupload-accept.txt` avulla. | `null`          |
| **`webforj.iconsDir`**               | Merkkijono  | Kuvakkeiden hakemiston URL-piste (oletusarvoisesti tosiasialliset `resources/icons/`). | `icons/` |
| **`webforj.license.cfg`**            | Merkkijono  | Lisenssikonfiguraation hakemisto. Oletusarvoisesti se on sama kuin webforJ:n konfiguraatiohakemisto, mutta tätä voidaan mukauttaa tarpeen mukaan. | `"."`  |
| **`webforj.license.startupTimeout`** | Kokonaisluku | Lisenssin käynnistysaika sekunteina. | `null` |
| **`webforj.locale`**                 | Merkkijono  | Sovelluksen alue, joka määrittää kielen, alueen asetukset ja päivämäärien, aikojen ja numeroiden muodot. | `null` |
| **`webforj.quiet`**                  | Boolean | Estää kuormituskuvan näyttämisen sovelluksen käynnistyksen aikana. | `false` |
| **`webforj.reloadOnServerError`**    | Boolean | **Vain kehitysympäristöt.** Kehitysympäristössä, automaattisesti lataa sivu uudelleen virheiden osalta, jotka liittyvät kuumakäynnistykseen, mutta ei muihin virhetyyppeihin. Kun käytät kuumakäynnistystä, jos asiakas lähettää pyynnön palvelimelle, kun se käynnistää, virhe voi tapahtua, kun WAR-tiedostoa vaihdetaan. Koska palvelimen todennäköisesti on pian oltava online, tämä asetus antaa asiakkaan yrittää ladata sivua automaattisesti.  | `false` |
| **`webforj.servlets[n].name`**       | Merkkijono  | Servletti nimeä (käyttää luokan nimeä, jos ei määritetty). | `null` |
| **`webforj.servlets[n].className`**  | Merkkijono | Servletin täydellinen luokan nimi. | `null` |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Servletin alustamisen parametrit. | `null` |
| **`webforj.sessionTimeout`**         | Kokonaisluku | Istunnon aikaraja sekunteina. | `60` |
| **`webforj.stringTable`**            | `Map<String,String>` | Avain-arvo-pareja, joita käytetään merkkijonojen tallentamiseen sovelluksessa. Hyödyllinen sovelluksen viestien tai etikettien tallentamiseen. Lisätietoja `StringTable`:sta löytyy [täältä](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`            |
| **`webforj.mime.extensions`**            | `Map<String,String>` | Mukautetut MIME-tyypin kartoitukset tiedostopäätteille, kun palvelessa staattisia tiedostoja. Mahdollistaa oletus MIME-tyyppien ylikirjoittamisen tai määrittelyn mukautetuille päätteille. Kartan avain on tiedostopäätte (ilman pistettä), ja arvo on MIME-tyyppi. | `{}`            |

## `web.xml` konfigurointi {#configuring-webxml}

`web.xml` tiedosto on olennainen konfiguraatiotiedosto Java-verkkosovelluksille, ja webforJ:ssä se määrittää tärkeitä asetuksia, kuten servlettikonfiguraation, URL-kaaviot ja tervetuloa-sivut. Tämän tiedoston tulisi sijaita projektisi `WEB-INF`-hakemistossa.

| Asetus                                 | Selitys                                                                                                                                                                                   | Oletusarvo               |
| --------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------- |
| **`<display-name>`**                    | Määrittää verkkosovelluksen näyttönimen, joka johtuu yleensä projektin nimestä. Tämä nimi näkyy sovelluspalvelimien hallintakonsolissa.                                                        | `${project.name}`         |
| **`<servlet>` ja `<servlet-mapping>`** | Määrittää `WebforjServlet`:in, ydinservletin webforJ-pyyntöjen käsittelemiseksi. Tämä servlette on kartoitettu kaikille URL-osoitteille (`/*`), jolloin se on pääsisäänkäyntisi verkkopyynnöille.                     | `WebforjServlet`          |
| **`<load-on-startup>`**                 | Määrittää, että `WebforjServlet` tulee ladata, kun sovellus käynnistyy. Asettamalla tämän arvoon `1` servlette ladataan heti, mikä parantaa ensisijaista pyyntöjen käsittelyä.                | `1`                       |
