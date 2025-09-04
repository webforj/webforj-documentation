---
title: Property Configuration
sidebar_position: 30
_i18n_hash: 3e14c2d47a7963fe901feda071971419
---
# webforJ-ominaisuuksien määrittäminen

Jotta webforJ-sovellus voidaan julkaista ja suorittaa onnistuneesti, tarvitaan muutama keskeinen määritystiedosto: `webforj.conf` ja `web.xml`. Kukin näistä tiedostoista ohjaa sovelluksen käyttäytymisen eri puolia, kuten sisäänkäyntipisteitä ja virheenkorjausasetuksia sekä servlettikartoituksia.

## `webforj.conf` määrittäminen {#configuring-webforjconf}

`webforj.conf`-tiedosto on webforJ:n ydinmääritystiedosto, joka määrittelee sovelluksen asetuksia, kuten sisäänkäyntipisteitä, virheenkorjaustilaa ja asiakas-palvelin -vuorovaikutusta. Tiedosto on [HOCON-muodossa](https://github.com/lightbend/config/blob/master/HOCON.md) ja sen tulisi sijaita `resources`-hakemistossa.

:::tip
Jos integroituu [Spring Frameworkin](../integrations/spring/overview.md) kanssa, voit asettaa nämä `webforj.conf`-asetukset `application.properties`-tiedostoon.
:::



### Esimerkki `webforj.conf` tiedostosta {#example-webforjconf-file}

```Ini
# Tämä määritystiedosto on HOCON-muodossa:
# https://github.com/lightbend/config/blob/master/HOCON.md

webforj.entry = com.webforj.samples.Application
webforj.debug = true
webforj.reloadOnServerError = on
webforj.clientHeartbeatRate = 1s
```

### Määritysvaihtoehdot {#configuration-options}

| Ominaisuus                           | Tyyppi    | Selitys                                                       | Oletusarvo                |
|--------------------------------------|---------|-------------------------------------------------------------------|------------------------|
| **`webforj.assetsCacheControl`**     | Merkkijono  | Cache-Control-otsikko staattisille resursseille.                        | `null` |
| **`webforj.assetsDir`**              | Merkkijono  | Reittinimi, jota käytetään staattisten tiedostojen tarjoamiseen, kun taas itse kansion nimeä säilytetään `static`. Tämä määritys on hyödyllinen, jos oletusreitti `static` ristiriidassa sovelluksesi määrittelemän reitin kanssa, jolloin voit muuttaa reittinimeä ilman kansion nimeämistä uudelleen.       | `null`               |
| **`webforj.assetsExt`**              | Merkkijono  | Oletustiedostopääte staattisille tiedostoille. | `null` |
| **`webforj.assetsIndex`**            | Merkkijono  | Oletustiedosto, joka toimitetaan hakupyyntöihin (esim. index.html). | `null` |
| **`webforj.clientHeartbeatRate`**    | Merkkijono  | Aika, jolloin asiakas pingaa palvelinta nähdäksesi, onko se yhä elossa. Kehityksessä kannattaa asettaa tämä lyhyemmäksi ajaksi, esimerkiksi `8s`, jotta palvelimen saatavuus voidaan nopeasti havaita. Aseta tuotannossa 50 sekuntia tai enemmän liiallisen pyyntöjen välttämiseksi. | `50s`           |
| **`webforj.components`**             | Merkkijono  | Kun määritetään, peruspolku määrää, mistä DWC-komponentteja ladataan. Oletuksena komponentteja ladataan sovellusta isännöivältä palvelimelta. Kuitenkin, kun asetetaan mukautettu peruspolku, komponentteja voidaan ladata vaihtoehtoiselta palvelimelta tai CDN:ltä. Esimerkiksi, ladata komponentteja jsdelivr.com:sta, aseta peruspolku seuraavasti: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version}. On tärkeää, että ladatut komponentit ovat yhteensopivia käytössä olevan webforJ-kehyksen version kanssa; muuten sovellus ei toimi odotetusti. Tätä asetusta ei huomioida käytettäessä normaalia BBj-asennusta ilman moottoria. Normaalia BBj-asennusta varten asetusta voidaan hallita `!COMPONENTS` STBL:llä. | `null`          |
| **`webforj.debug`**                  | Boolean | Ota käyttöön virheenkorjaustila. Virheenkorjaustilassa webforJ tulostaa lisätietoja konsoliin ja näyttää kaikki poikkeukset selaimessa. Virheenkorjaustila on oletuksena pois päältä. | `null`          |
| **`webforj.entry`**                  | Merkkijono  | Määrittää sovelluksen sisäänkäyntipisteen määrittämällä täysin laaditun nimen luokasta, joka laajentaa `webforj.App`:ia. Jos sisäänkäyntipistettä ei ole määritetty, webforJ skannaa automaattisesti luokkateitä etsiäkseen luokkia, jotka laajentavat `webforj.App`:ia. Jos havaitaan useita luokkia, virhe ilmenee. Kun paketti sisältää useita mahdollisia sisäänkäyntipisteitä, tämän asettaminen nimenomaisesti on tarpeen epäselvyyksien estämiseksi, tai vaihtoehtoisesti `AppEntry`-annotaatiota voidaan käyttää sisäänkäyntipisteen määrittämiseen ajonaikana. | `null`          |
| **`webforj.fileUpload.accept`**      | Lista    | Sallitut tiedostotyypit tiedostojen lataamista varten. Oletuksena kaikki tiedostotyypit ovat sallittuja. Tuetut muodot sisältävät MIME-tyypit kuten `image/*`, `application/pdf`, `text/plain`, tai tiedostopäätteet kuten `*.txt`. Kun käytetään normaalia BBj-asennusta, tätä asetusta ei huomioida, ja se hallitaan `fileupload-accept.txt`:llä. | `[]`            |
| **`webforj.fileUpload.maxSize`**     | Pitkä    | Suurin sallittu tiedostokoko tiedostojen lataamista varten, tavuina. Oletuksena ei ole ylärajaa. Kun käytetään normaalia BBj-asennusta, tätä asetusta ei huomioida, ja se hallitaan `fileupload-accept.txt`:llä. | `null`          |
| **`webforj.iconsDir`**               | Merkkijono  | URL-päätepiste ikonikansiolle (oletuspalvelin `resources/icons/` -hakemistosta). | `icons/` |
| **`webforj.license.cfg`**            | Merkkijono  | Lisenssikonfiguraation hakemisto. Oletuksena se on sama kuin webforJ-määrityshakemisto, mutta sen voi mukauttaa tarvittaessa. | `"."`  |
| **`webforj.license.startupTimeout`** | Kokonaisluku | Lisenssin käynnistysaika sekunneissa. | `null` |
| **`webforj.locale`**                 | Merkkijono  | Sovelluksen kieli, joka määrittää kielen, alueasetukset sekä päivämäärien, aikojen ja numeroiden muodot. | `null` |
| **`webforj.quiet`**                  | Boolean | Poistaa latauskuvan sovelluksen käynnistyksen aikana. | `false` |
| **`webforj.reloadOnServerError`**    | Boolean | **Ainoastaan kehitysympäristöissä.** Kehitysympäristössä sivu ladataan automaattisesti virheiden vuoksi, jotka liittyvät kuumaan uudelleenladontaan, mutta ei muihin virhetyyppeihin. Kun käytetään kuumaa uudelleenlatausta, jos asiakas lähettää palvelimelle pyynnön sen ollessa käynnistymässä, virhe voi tapahtua, kun WAR-tiedostoa vaihdetaan. Koska palvelin on todennäköisesti pian takaisin online-tilassa, tämä asetus mahdollistaa asiakkaan yrittää automaattisesti ladata sivun uudelleen.  | `false` |
| **`webforj.servlets[n].name`**       | Merkkijono  | Servlettin nimi (käyttää luokan nimeä, jos ei ole määritetty). | `null` |
| **`webforj.servlets[n].className`**  | Merkkijono | Servletin täysin laadittu luokanimi. | `null` |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Servletin alustusparametrit. | `null` |
| **`webforj.sessionTimeout`**         | Kokonaisluku | Istunnon aikakatkaisuaika sekunneissa. | `60` |
| **`webforj.stringTable`**            | `Map<String,String>` | Avain-arvo-pareista koostuva kartta, jota käytetään merkkijonojen tallentamiseen sovelluksessa. Hyödyllinen sovellusviestien tai otsikoiden tallentamiseen. Lisää tietoa `StringTable`-koodista löytyy [tästä](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`            |

## `web.xml` määrittäminen {#configuring-webxml}

web.xml-tiedosto on olennaisesti tärkeä määritystiedosto Java-verkkosovelluksille, ja webforJ:ssä se määrittää tärkeitä asetuksia, kuten servlettimäärityksen, URL-kuviot ja tervetuloissivut. Tämän tiedoston tulisi sijaita projektisi `WEB-INF`-hakemistossa.

| Asetus                                   | Selitys                                                                                                                                                                                    | Oletusarvo               |
| --------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------- |
| **`<display-name>`**                    | Asettaa verkkosovelluksen näyttönimen, joka yleensä johdetaan projektin nimestä. Tämä nimi näkyy sovelluspalvelimen hallintakonsolissa.                                                   | `${project.name}`         |
| **`<servlet>` ja `<servlet-mapping>`** | Määrittää `WebforjServlet`-servletin, joka on ydinsovellus webforJ-pyyntöjen käsittelemiseksi. Tämä servlet on kartoitettu kaikkiin URL-osoitteisiin (`/*`), mikä tekee siitä pääsisäänkäyntipisteen verkkopyynnöille. | `WebforjServlet`          |
| **`<load-on-startup>`**                 | Määrittää, että `WebforjServlet` tulee ladata, kun sovellus käynnistyy. Asettamalla tämä arvoon `1`, servlettiyhdiste ladataan heti, mikä parantaa alkuperäisen pyynnön käsittelyä.         | `1`                       |
