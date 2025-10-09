---
title: Property Configuration
sidebar_position: 30
_i18n_hash: e7a922cb3f035dd19fdc282d245bdf2c
---
# WebforJ-ominaisuuksien konfigurointi

Jotta voit onnistuneesti käyttää ja suorittaa webforJ-sovellusta, tarvitaan muutamia keskeisiä konfigurointitiedostoja: `webforj.conf` ja `web.xml`. Kumpikin näistä tiedostoista ohjaa sovelluksen käyttäytymisen eri osa-alueita, kuten sisääntuloja, virheenkorjauksia ja servlet-mappausta.

## `webforj.conf`-tiedoston konfigurointi {#configuring-webforjconf}

`webforj.conf`-tiedosto on keskeinen konfigurointitiedosto webforJ:ssa, joka määrittelee sovelluksen asetuksia, kuten sisääntulot, virheenkorjaustila ja asiakas-palvelin vuorovaikutus. Tiedosto on [HOCON-muodossa](https://github.com/lightbend/config/blob/master/HOCON.md) ja sen pitäisi sijaita `resources`-hakemistossa.

:::tip
Jos integroit [Springin](../integrations/spring/overview.md) kanssa, voit määrittää nämä `webforj.conf` -asetukset `application.properties` -tiedostossa.
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

### Konfigurointivaihtoehdot {#configuration-options}

| Ominaisuus                            | Tyyppi  | Selitys                                                          | Oletusarvo              |
|---------------------------------------|---------|------------------------------------------------------------------|-------------------------|
| **`webforj.assetsCacheControl`**      | Merkkijono | Cache-Control-otsikko staattisille resursseille.                  | `null`                  |
| **`webforj.assetsDir`**               | Merkkijono | Reitin nimi, joka käytetään staattisten tiedostojen tarjoamiseen, samalla kun todellinen hakemiston nimi pysyy `static`. Tämä konfiguraatio on hyödyllinen, jos oletus `static` reitti on ristiriidassa sovelluksesi määrittämän reitin kanssa, jolloin voit muuttaa reitin nimeä ilman, että hakemistoa tarvitsee nimetä uudelleen. | `null`                  |
| **`webforj.assetsExt`**               | Merkkijono | Oletustiedostopääte staattisille tiedostoille.                   | `null`                  |
| **`webforj.assetsIndex`**             | Merkkijono | Oletustiedosto, joka palvellaan hakemuspyyntöjä varten (esim. index.html). | `null`        |
| **`webforj.clientHeartbeatRate`**     | Merkkijono | Aikaväli, jolla asiakas pingaa palvelinta varmistaakseen, että se on edelleen toiminnassa. Kehityksessä tämä on asetettava lyhyemmäksi aikaväliksi, esimerkiksi `8s`, jotta palvelimen saatavuus havaitaan nopeasti. Aseta tuotannossa 50 sekuntia tai enemmän välttääksesi liiallisia pyyntöjä. | `50s`                   |
| **`webforj.components`**              | Merkkijono | Kun määritetty, peruspolku määrää, mistä DWC-komponentit ladataan. Oletusarvoisesti komponentit ladataan sovellusta palvelevasta palvelimesta. Kuitenkin, mukautetun peruspolun määrittäminen mahdollistaa komponenttien lataamisen vaihtoehtoisesta palvelimesta tai CDN:stä. Esimerkiksi lataamalla komponentteja jsdelivr.comista, aseta peruspolku: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version} On tärkeää, että ladatut komponentit ovat yhteensopivia käytössä olevan webforJ-kehyksen version kanssa; muuten sovellus ei ehkä toimi odotetusti. Tämä asetus ohitetaan, kun käytetään standardia BBj-asennusta ilman moottoria. Standardin BBj-asennuksen yhteydessä asetusta voidaan hallita `!COMPONENTS` STBL:n avulla. | `null`                  |
| **`webforj.debug`**                   | Boolean | Ota käyttöön virheenkorjaustila. Virheenkorjaustilassa webforJ tulostaa lisätietoja konsoliin ja näyttää kaikki poikkeukset selaimessa. Virheenkorjaustila on oletusarvoisesti pois päältä. | `null`                  |
| **`webforj.entry`**                   | Merkkijono | Määrittelee sovelluksen sisääntulopisteen määrittämällä luokan täydellisen nimen, joka laajentaa `webforj.App`:ta. Jos sisääntulopistettä ei ole määritetty, webforJ skannaa automaattisesti luokkahakemiston luokat, jotka laajentavat `webforj.App`:ta. Jos useita luokkia löytyy, virhe ilmenee. Kun paketissa on useampi kuin yksi mahdollinen sisääntulopiste, tämän määrittäminen selvästi on pakollista, jotta vältetään epäselvyys, tai vaihtoehtoisesti, `AppEntry`-annotaatiota voidaan käyttää määrittelemään sisääntulopiste aikarajoituksessa. | `null`                  |
| **`webforj.fileUpload.accept`**       | Lista   | Sallitut tiedostotyypit tiedostojen lataamiseen. Oletusarvoisesti kaikki tiedostotyypit ovat sallittuja. Tuetut muodot sisältävät MIME-tyypit kuten `image/*`, `application/pdf`, `text/plain`, tai tiedostopäätteet kuten `*.txt`. Kun käytetään standardia BBj-asennusta, tätä asetusta ohitetaan ja sitä hallitaan `fileupload-accept.txt` -tiedoston kautta. | `[]`                    |
| **`webforj.fileUpload.maxSize`**      | Pitkä   | Maksimimäärä tiedostokoolle, joka on sallittu tiedostojen lataamisessa, tavuina. Oletusarvoisesti ei ole rajoituksia. Kun käytetään standardia BBj-asennusta, tätä asetusta ohitetaan ja sitä hallitaan `fileupload-accept.txt` -tiedoston kautta. | `null`                  |
| **`webforj.iconsDir`**                | Merkkijono | URL-pistekuva, jossa on kuvakkeiden hakemisto (oletusarvoisesti palvellaan `resources/icons/`-kansiosta). | `icons/`                |
| **`webforj.license.cfg`**             | Merkkijono | Lisenssisäätöksen hakemisto. Oletusarvoisesti se on sama kuin webforJ:n konfigurointihakemisto, mutta tätä voidaan tarvittaessa mukauttaa. | `"."`                    |
| **`webforj.license.startupTimeout`**  | Kokonaisluku | Lisenssin aloitusaika sekunneissa. | `null`                  |
| **`webforj.locale`**                  | Merkkijono | Sovelluksen lokaali, joka määrittää kielen, alueasetukset ja päivämäärien, aikojen ja numeroiden muodot. | `null`                  |
| **`webforj.quiet`**                   | Boolean | Poistaa kuormituksen kuvan sovelluksen käynnistyksen aikana. | `false`                 |
| **`webforj.reloadOnServerError`**     | Boolean | **Vain kehitysympäristöissä.** Kehitysympäristössä sivun automaattinen lataaminen virheiden vuoksi, jotka liittyvät kuumaan uudelleenkäynnistykseen, mutta ei muihin virhetyyppeihin. Kun käytetään kuumaa uudelleenlatausta, jos asiakas lähettää pyynnön palvelimelle sen käynnistyessä uudelleen, virhe voi ilmetä, kun WAR-tiedostoa vaihdetaan. Koska palvelin on todennäköisesti pian takaisin verkossa, tämä asetus sallii asiakkaan yrittää automaattisesti ladata sivun uudelleen. | `false`                 |
| **`webforj.servlets[n].name`**        | Merkkijono | Servletin nimi (käyttää luokan nimeä, jos ei määritetty). | `null`                  |
| **`webforj.servlets[n].className`**   | Merkkijono | Servletin täydellinen luokan nimi. | `null`                  |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Servletin alustamisparametrit. | `null`                  |
| **`webforj.sessionTimeout`**          | Kokonaisluku | Istunnon aikaraja sekunneissa. | `60`                    |
| **`webforj.stringTable`**             | `Map<String,String>` | Avain-arvo-pareista koostuva kartta, jota käytetään merkkijonojen tallentamiseen sovelluksessa. Hyödyllinen sovelluksen viestien tai etikettien tallentamiseen. Lisätietoa `StringTable`-asetuksesta löytyy [tästä](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`                    |

## `web.xml`-tiedoston konfigurointi {#configuring-webxml}

`web.xml`-tiedosto on olennainen konfigurointitiedosto Java-web-sovelluksille, ja webforJ:ssa se määrittelee tärkeitä asetuksia, kuten servletin konfiguroinnin, URL-mallit ja tervetuliasivut. Tämän tiedoston pitäisi sijaita projektisi `WEB-INF`-hakemistossa.

| Asetus                                      | Selitys                                                                                                                                                                           | Oletusarvo                  |
|----------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------|
| **`<display-name>`**                         | Määrittää web-sovelluksen näytön nimen, joka tuletaan tyypillisesti projektin nimestä. Tämä nimi näkyy sovelluspalvelimien hallintakonsolissa.                                   | `${project.name}`            |
| **`<servlet>` ja `<servlet-mapping>`**      | Määrittelee `WebforjServlet`:in, ydinservletin, joka käsittelee webforJ-pyyntöjä. Tämä servlet on kartoitettu kaikille URL-osoitteille (`/*`), mikä tekee siitä pääpisteen verkkopyynnöille. | `WebforjServlet`             |
| **`<load-on-startup>`**                      | Määrittää, että `WebforjServlet` tulee ladata, kun sovellus käynnistyy. Asettamalla tämän `1`:ksi, servlet ladataan heti, mikä parantaa alkuperäisten pyyntöjen käsittelyä.  | `1`                          |
