---
title: Property Configuration
sidebar_position: 30
_i18n_hash: 66df4ab330f26adccbed654c27c6be23
---
# WebforJ-ominaisuuksien määrittäminen

Jotta voit onnistuneesti julkaista ja suorittaa webforJ-sovelluksen, tarvitaan useita keskeisiä konfiguraatiotiedostoja: `webforj.conf` ja `web.xml`. Kukin näistä tiedostoista hallitsee sovelluksen käyttäytymistä eri näkökohdista, kuten sisäänkäynneistä ja virheiden debug-asetuksista servlet-mappauksiin.

## `webforj.conf` määrittäminen {#configuring-webforjconf}

`webforj.conf`-tiedosto on keskeinen konfiguraatiotiedosto webforJ:ssä, jossa määritellään sovelluksen asetuksia, kuten sisäänkäynnit, debug-tila ja asiakas-palvelin-vuorovaikutus. Tiedoston on oltava [HOCON-muodossa](https://github.com/lightbend/config/blob/master/HOCON.md), ja se tulisi sijoittaa `resources`-hakemistoon.

:::tip
Jos integroituu [Spring Frameworkin](../integrations/spring/overview.md) kanssa, voit määrittää nämä `webforj.conf` -ominaisuudet `application.properties` -tiedostoon.
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

### Määrittelyvaihtoehdot {#configuration-options}

| Ominaisuus                           | Tyyppi   | Selitys                                                       | Oletusarvo                |
|--------------------------------------|----------|--------------------------------------------------------------|---------------------------|
| **`webforj.assetsCacheControl`**     | Merkkijono | Cache-Control -otsake staattisia resursseja varten.        | `null`                    |
| **`webforj.assetsDir`**              | Merkkijono | Reitin nimi, jota käytetään staattisten tiedostojen palvelemiseen, kun taas todellinen kansion nimi pysyy `static`. Tämä konfiguraatio on hyödyllinen, jos oletusreitti `static` voi olla ristiriidassa sovelluksesi määrittämän reitin kanssa, jolloin voit vaihtaa reitin nimeä ilman, että tarvitsee nimetä kansiota uudelleen. | `null`                    |
| **`webforj.assetsExt`**              | Merkkijono | Oletustiedostopääte staattisille tiedostoille.              | `null`                    |
| **`webforj.assetsIndex`**            | Merkkijono | Oletustiedosto, joka tarjotaan hakupyyntöjen yhteydessä (esim. index.html). | `null`                    |
| **`webforj.clientHeartbeatRate`**    | Merkkijono | Väli, jolla asiakas kutsuu palvelinta, jotta tiedetään, onko se yhä toiminnassa. Kehityksessä tämä tulisi asettaa lyhyemmäksi väliksi, esimerkiksi `8s`, jotta palvelimen saatavuus voitaisiin nopeasti havaita. Aseta tuotannossa 50 sekuntia tai yli välttääksesi liialliset pyynnöt. | `50s`                     |
| **`webforj.components`**             | Merkkijono | Kun määritetään, peruspolku määrää, mistä DWC-komponentit ladataan. Oletuksena komponentit ladataan sovellusta hostaavalta palvelimelta. Kuitenkin mukautetun peruspolun määrittäminen mahdollistaa komponenttien lataamisen vaihtoehtoiselta palvelimelta tai CDN:ltä. Esimerkiksi lataamiseksi komponentteja jsdelivr.com:sta, aseta peruspolku: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version}. On tärkeää, että ladatut komponentit ovat yhteensopivia käytössä olevan webforJ-kehyksen version kanssa; muuten sovellus ei ehkä toimi odotetusti. Tämä asetusta ei oteta huomioon, kun käytetään standardia BBj-asennusta ilman moottoria. Standardin BBj-asennuksen kohdalla asetusta voidaan hallita `!COMPONENTS` STBL:llä. | `null`                    |
| **`webforj.debug`**                  | Boolean  | Ota debug-tila käyttöön. Debug-tilassa webforJ tulostaa lisätietoja konsoliin ja näyttää kaikki poikkeukset selaimessa. Debug-tila on oletuksena pois päältä. | `null`                    |
| **`webforj.entry`**                  | Merkkijono | Määrittää sovelluksen sisäänkäynnin määrittämällä luokan täydellinen nimi, joka laajentaa `webforj.App`:ia. Jos sisäänkäyntiä ei ole määritetty, webforJ skannaa automaattisesti luokkareitin luokat, jotka laajentavat `webforj.App`:ia. Jos useita luokkia löytyy, virhe tapahtuu. Kun paketti sisältää useita mahdollisia sisäänkäyntejä, tämän eksplisiittinen asettaminen on vaadittua epäselvyyksien välttämiseksi, tai vaihtoehtoisesti voit käyttää `AppEntry`-annotaatiota määrittääksesi sisäänkäynnin ajonaikaisesti. | `null`                    |
| **`webforj.fileUpload.accept`**      | Lista     | Sallitut tiedostotyypit tiedostojen lataamiseen. Oletuksena kaikki tiedostotyypit ovat sallittuja. Tuetut muodot sisältävät MIME-tyypit kuten `image/*`, `application/pdf`, `text/plain`, tai tiedostopäätteet kuten `*.txt`. Kun käytetään standardia BBj-asennusta, tätä asetusta ei oteta huomioon ja sitä hallitaan `fileupload-accept.txt`:n kautta. | `[]`                      |
| **`webforj.fileUpload.maxSize`**     | Pitkä    | Suurin tiedostokoko, joka on sallittu tiedostojen lataamiseen, tavuina. Oletuksena ei ole rajoituksia. Kun käytetään standardia BBj-asennusta, tätä asetusta ei oteta huomioon ja sitä hallitaan `fileupload-accept.txt`:n kautta. | `null`                    |
| **`webforj.iconsDir`**               | Merkkijono | URL-päätepiste ikoneille (oletuksena palvelu `resources/icons/`-hakemistosta). | `icons/`                  |
| **`webforj.license.cfg`**            | Merkkijono | Lisenssikonfiguraation hakemisto. Oletuksena se on sama kuin webforJ:n konfiguraatiohakemisto, mutta tätä voidaan mukauttaa tarvittaessa. | `"."`                      |
| **`webforj.license.startupTimeout`** | Kokonaisluku | Lisenssin käynnistyysaika sekunteina. | `null`                    |
| **`webforj.locale`**                 | Merkkijono | Sovelluksen kieli, joka määrittää kielen, alueasetukset sekä päivämäärien, aikojen ja numeroiden muodot. | `null`                    |
| **`webforj.quiet`**                  | Boolean  | Estää latauskuvakkeen lataamiseksi sovelluksen käynnistyksen aikana. | `false`                   |
| **`webforj.reloadOnServerError`**    | Boolean  | **Vain kehitysympäristöt.** Kehitysympäristössä sivu ladataan automaattisesti uudelleen virheiden vuoksi ja kuumia uudelleen käyttöönottoja, mutta ei muitten virhetyyppien osalta. Kun käytetään kuumaa uudelleen käyttöönottoa, asiakas saattaa lähettää pyynnön palvelimelle, kun se on käynnistämässä uudelleen, jolloin virhe voi ilmetä, kun WAR-tiedosto vaihdetaan. Koska palvelin todennäköisesti tulee takaisin verkkoyhteyteen pian, tämä asetus sallii asiakkaan yrittää sivun automaattista uudelleenlatausta. | `false`                   |
| **`webforj.servlets[n].name`**       | Merkkijono | Servletin nimi (käyttää luokan nimeä, jos ei määritetty). | `null`                    |
| **`webforj.servlets[n].className`**  | Merkkijono | Servletin täydellinen luokan nimi. | `null`                    |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Servletin alustusparametrit. | `null`                    |
| **`webforj.sessionTimeout`**         | Kokonaisluku | Istunnon aikaraja sekunneissa. | `60`                      |
| **`webforj.stringTable`**            | `Map<String,String>` | Avain-arvo-pareista koostuva kartta, jota käytetään merkkijonojen tallentamiseen sovelluksessa. Hyödyllinen sovelluksen viestien tai etikettien tallentamiseen. Lisätietoja `StringTable`:sta löytyy [tästä](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`                       |

## `web.xml` määrittäminen {#configuring-webxml}

`web.xml`-tiedosto on olennainen konfiguraatiotiedosto Java-verkkosovelluksille, ja webforJ:ssä se määrittelee tärkeitä asetuksia, kuten servlet-konfiguraation, URL-kuviot ja tervetuloissivut. Tämän tiedoston tulisi sijaita projektisi julkaisuarkkitehtuurin `WEB-INF`-hakemistossa.

| Asetus                                 | Selitys                                                                                                                                                                                       | Oletusarvo               |
| --------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------- |
| **`<display-name>`**                    | Asettaa verkkosovelluksen näytön nimen, joka tyypillisesti saadaan projektin nimestä. Tämä nimi näkyy sovelluspalvelimien hallintakonsolissa.                                               | `${project.name}`         |
| **`<servlet>` ja `<servlet-mapping>`** | Määrittelee `WebforjServlet`:in, joka on keskeinen servlet webforJ-pyyntöjen käsittelyyn. Tämä servlet on mapattu kaikkiin URL-osoitteisiin (`/*`), mikä tekee siitä pääsisäänkäynnin verkkopyynnöille. | `WebforjServlet`          |
| **`<load-on-startup>`**                 | Määrittää, että `WebforjServlet` tulisi ladata, kun sovellus käynnistyy. Asettamalla tämän `1`:ksi servlet ladataan välittömästi, mikä parantaa alkuperäistä pyyntöjen käsittelyä.          | `1`                       |
