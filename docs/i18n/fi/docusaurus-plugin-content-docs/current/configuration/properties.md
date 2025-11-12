---
title: Ominaisuuden Konfigurointi
sidebar_position: 30
sidebar_class_name: updated-content
_i18n_hash: fe000276baa9ac8b0773e5c4372d8463
---
# WebforJ-ominaisuuksien määrittäminen

WebforJ-sovelluksen menestyksekkääseen käyttöönottoon ja suorittamiseen tarvitaan muutamia avainmäärittelytiedostoja: `webforj.conf` ja `web.xml`. Kukin näistä tiedostoista säätelee sovelluksen käyttäytymisen eri näkökohtia, kuten sisäänkäyntipisteitä ja virheenkorjausasetuksia sekä servlet-mappingia.

## `webforj.conf` määrittäminen {#configuring-webforjconf}

`webforj.conf` -tiedosto on keskeinen määrittelytiedosto webforJ:ssä, joka määrittelee sovelluksen asetuksia, kuten sisäänkäyntipisteet, virheenkorjaustilan ja asiakas-palvelin-vuorovaikutuksen. Tiedosto on [HOCON-muodossa](https://github.com/lightbend/config/blob/master/HOCON.md) ja sen tulee sijaita `resources` -hakemistossa.

:::tip
Jos integroituu [Springin](../integrations/spring/overview.md) kanssa, voit asettaa nämä `webforj.conf` -asetukset `application.properties` -tiedostoon.
:::

### Esimerkki `webforj.conf` -tiedostosta {#example-webforjconf-file}

```Ini
# Tämä määrittelytiedosto on HOCON-muodossa:
# https://github.com/lightbend/config/blob/master/HOCON.md

webforj.entry = com.webforj.samples.Application
webforj.debug = true
webforj.reloadOnServerError = on
webforj.clientHeartbeatRate = 1s
```

### Määrittelyvaihtoehdot {#configuration-options}

| Ominaisuus                            | Tyyppi    | Selitys                                                       | Oletus                |
|---------------------------------------|-----------|--------------------------------------------------------------|-----------------------|
| **`webforj.assetsCacheControl`**     | Merkkijono | Cache-Control-otsake staattisille resursseille.             | `null` |
| **`webforj.assetsDir`**              | Merkkijono | Reitinnimi, jota käytetään staattisten tiedostojen toimittamiseen, kun varsinaisen kansion nimi on `static`. Tämä määrittely on hyödyllinen, jos oletusreitti `static` on ristiriidassa sovelluksessa määritellyn reitin kanssa, jolloin voit muuttaa reittinimen ilman kansion uudelleennimeämistä. | `null` |
| **`webforj.assetsExt`**              | Merkkijono | Oletustiedostotunnus staattisille tiedostoille.             | `null` |
| **`webforj.assetsIndex`**            | Merkkijono | Oletustiedosto, joka toimitetaan hakupyyntöjen yhteydessä (esim. index.html). | `null` |
| **`webforj.clientHeartbeatRate`**    | Merkkijono | Aikaväli, jolloin asiakas pingaa palvelinta nähdäksesi, onko se edelleen toiminnassa. Kehityksessä tämä tulisi asettaa lyhyemmäksi aikaväliksi, esimerkiksi `8s`, jotta palvelimen saatavuus voidaan nopeasti tarkistaa. Aseta 50 sekuntia tai enemmän tuotannossa liiallisen liikenteen välttämiseksi. | `50s` |
| **`webforj.components`**             | Merkkijono | Kun määritetään, peruspolku määrittelee, mistä DWC-komponentit ladataan. Oletusarvoisesti komponentit ladataan sovellusta isännöivältä palvelimelta. Mukautetun peruspolun asettaminen mahdollistaa komponenttien lataamisen vaihtoehtoiselta palvelimelta tai CDN:stä. Esimerkiksi, lataa komponentit jsdelivr.com-sivustolta asettamalla peruspolku: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version}. On tärkeää, että ladatut komponentit ovat yhteensopivia käytössä olevan webforJ-kehyksen version kanssa; mu otherwise sovellus ei toimi odotetusti. Tätä asetusta ei huomioida tavanomaisessa BBj-asennuksessa ilman moottoria. Tavanomaisessa BBj-asennuksessa asetusta voidaan hallita `!COMPONENTS` STBL:llä. | `null` |
| **`webforj.debug`**                  | Boolean    | Ota käyttöön virheenkorjaustila. Virheenkorjaustilassa webforJ tulostaa lisätietoja konsoliin ja näyttää kaikki poikkeukset selaimessa. Virheenkorjaustila on oletuksena pois käytöstä. | `null` |
| **`webforj.entry`**                  | Merkkijono | Määrittelee sovelluksen sisäänkäyntipisteen määrittämällä täysin määritellyn nimen luokalle, joka laajentaa `webforj.App`:ta. Jos sisäänkäyntipistettä ei ole määritetty, webforJ skannaa automaattisesti luokkareppua luokkien löytämiseksi, jotka laajentavat `webforj.App`:ta. Jos useita luokkia löytyy, virhe käy ilmi. Kun paketti sisältää enemmän kuin yhden mahdollisen sisäänkäyntipisteen, tämän määrittäminen nimenomaisesti on tarpeen epäselvyyden välttämiseksi, tai vaihtoehtoisesti `AppEntry`-annotaatiota voidaan käyttää sisäänkäyntipisteen määrittämiseksi ajon aikana. | `null` |
| **`webforj.fileUpload.accept`**      | Lista      | Sallittujen tiedostotyyppien luettelo tiedostojen lataamiseen. Oletusarvoisesti kaikki tiedostotyypit ovat sallittuja. Tuetut muodot sisältävät MIME-tyypit, kuten `image/*`, `application/pdf`, `text/plain`, tai tiedostotunnisteet, kuten `*.txt`. Tavanomaisessa BBj-asennuksessa tätä asetusta ei huomioida, ja se hallitaan `fileupload-accept.txt`:n kautta. | `[]` |
| **`webforj.fileUpload.maxSize`**     | Pitkä      | Maksimitiedostokoko, joka on sallittu tiedostojen lataamiseen, tavuina. Oletusarvoisesti rajoja ei ole. Tavanomaisessa BBj-asennuksessa tätä asetusta ei huomioida, ja se hallitaan `fileupload-accept.txt`:n kautta. | `null` |
| **`webforj.iconsDir`**               | Merkkijono | URL-päätepiste kuvakkeiden hakemistolle (oletusarvoisesti toimitetaan `resources/icons/`-kansiosta). | `icons/` |
| **`webforj.license.cfg`**            | Merkkijono | Lisenssin määrittelyhakemisto. Oletuksena se on sama kuin webforJ-määrittelyhakemisto, mutta sitä voidaan mukauttaa tarpeen mukaan. | `"."` |
| **`webforj.license.startupTimeout`** | Kokonaisluku| Lisenssin käynnistysaika sekunteina. | `null` |
| **`webforj.locale`**                 | Merkkijono | Sovelluksen kieli, joka määrittää kielen, alueasetukset ja päivämäärien, aikojen ja numeroiden muotoilut. | `null` |
| **`webforj.quiet`**                  | Boolean    | Poistaa ladattavan kuvakkeen käytöstä sovelluksen käynnistyksen aikana. | `false` |
| **`webforj.reloadOnServerError`**    | Boolean    | **Kehitysympäristöt vain.** Kehitysympäristössä automaattisesti lataa sivu uudelleen, kun virheitä liittyen kuumaan uudelleenkäynnistykseen ilmenee, mutta ei muiden virhetyyppien osalta. Kuuman uudelleenkäynnistyksen käytön yhteydessä, jos asiakas lähettää pyyntö palvelimelle sen ollessa käynnistymässä, virhe voi tapahtua, kun WAR-tiedostoa vaihdetaan. Koska palvelin todennäköisesti palaa pian verkkoon, tämä asetus mahdollistaa asiakkaan yrittää automaattisesti ladata sivu uudelleen. | `false` |
| **`webforj.servlets[n].name`**       | Merkkijono | Servletin nimi (käyttää luokan nimeä, jos ei ole määritetty). | `null` |
| **`webforj.servlets[n].className`**  | Merkkijono | Servletin täysin määritelty luokan nimi. | `null` |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Servletin alustamismääritykset. | `null` |
| **`webforj.sessionTimeout`**         | Kokonaisluku| Istunnon aikakatkaisu sekunneissa. | `60` |
| **`webforj.stringTable`**            | `Map<String,String>` | Avain-arvo-pareista koostuva kartta, jota käytetään merkkijonojen tallentamiseen sovelluksessa. Hyödyllinen sovelluksen viestien tai nimilappujen tallentamiseen. Lisätietoja `StringTable`:sta löytyy [täältä](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}` |
| **`webforj.mime.extensions`**        | `Map<String,String>` | Mukautetut MIME-tyyppikartoitukset tiedostotunnisteille staattisten tiedostojen palvelemisen yhteydessä. Mahdollistaa oletus MIME-tyyppien ylikirjoittamisen tai MIME-tyyppien määrittämisen mukautetuille tunnisteille. Karttakey on tiedostotunniste (ilman pistettä), ja arvo on MIME-tyyppi. | `{}` |

## `web.xml` määrittäminen {#configuring-webxml}

`web.xml` -tiedosto on olennainen määrittelytiedosto Java-verkkosovelluksille, ja webforJ:ssä se määrittää tärkeitä asetuksia, kuten servlet-määrityksen, URL-mallit ja tervetuloa-sivut. Tämän tiedoston tulee sijaita projektisi käyttöönottoarkkitehtuurin `WEB-INF` -hakemistossa.

| Asetus                                  | Selitys                                                                                                                                                                                   | Oletusarvo                       |
|-----------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------|
| **`<display-name>`**                   | Määrittää verkkosovelluksen näyttönimen, joka yleensä johdetaan projektin nimestä. Tämä nimi näkyy sovelluspalvelimien hallintakonsolissa.                                                  | `${project.name}`                |
| **`<servlet>` ja `<servlet-mapping>`** | Määrittää `WebforjServlet`:n, joka on ydinsovitin webforJ-pyyntöjen käsittelyyn. Tämä servlet on kartoittunut kaikkiin URL-osoitteisiin (`/*`), mikä tekee siitä pääsisäänkäynnin verkkopyynnöille. | `WebforjServlet`                  |
| **`<load-on-startup>`**                | Määrittää, että `WebforjServlet` tulisi ladata, kun sovellus käynnistyy. Asettaessaan tämän `1`:een, servlet ladataan välittömästi, mikä parantaa alkuperäisten pyyntöjen käsittelyä. | `1`                              |
