---
title: Property Configuration
sidebar_position: 30
_i18n_hash: dea9eb679150ca6124fb625c7d04d27c
---
# webforJ-ominaisuuksien konfigurointi

Jotta webforJ-sovellus voidaan onnistuneesti ottaa käyttöön ja ajattaa, tarvitaan muutama keskeinen konfigurointitiedosto: `webforJ.conf` ja `web.xml`. Kukin näistä tiedostoista ohjaa sovelluksen eri käyttäytymisen osia, kuten sisääntulopisteitä ja virheenkorjausasetuksia sekä servlet-kartoituksia.

## `webforj.conf` konfigurointi {#configuring-webforjconf}

`webforJ.conf` -tiedosto on keskeinen konfigurointitiedosto webforJ:ssä, joka määrittelee sovelluksen asetuksia, kuten sisääntulopisteet, virheenkorjaustila ja asiakas-palvelin vuorovaikutus. Tiedosto on kirjoitettu [HOCON-muodossa](https://github.com/lightbend/config/blob/master/HOCON.md) ja sen tulisi sijaita `resources`-hakemistossa.

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

| Ominaisuus                       | Selitys                                                                                                                                                                              | Oletusarvo         |
|----------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------|
| **`webforj.entry`**              | Määrittelee sovelluksen sisääntulopisteen, määrittämällä täysin määritellyn nimen luokalle, joka laajentaa `webforj.App`:ia. Jos sisääntulopistettä ei määritetä, webforJ tarkistaa automaattisesti luokkalistan luokkia, jotka laajentavat `webforj.App`:ia. Jos useita luokkia löydetään, virhe ilmenee. Kun paketti sisältää useampia mahdollisia sisääntulopisteitä, tämän asettaminen selkeästi on pakollista epäselvyyksien estämiseksi, tai vaihtoehtoisesti `AppEntry`-annotaatiota voidaan käyttää sisääntulopisteen määrittämiseksi ajonaikaisesti. | `null`              |
| **`webforj.debug`**              | Aktivoi virheenkorjaustilan. Virheenkorjaustilassa webforJ tulostaa lisätietoja konsoliin ja näyttää kaikki poikkeukset selaimessa. Virheenkorjaustila on oletusarvoisesti pois päältä. | `null`              |
| **`webforj.reloadOnServerError`** | Käytettäessä kuumaa uudelleenlähetystä, koko WAR-tiedosto vaihdetaan. Jos asiakas yrittää lähettää pyynnön palvelimelle sen käynnistettäessä, virhe ilmenee. Tämä asetus antaa asiakkaan yrittää sivun lataamista uudelleen, jos palvelin on tilapäisesti pois käytöstä, toivoen sen olevan pian jälleen käytettävissä. Tämä pätee vain kehitysympäristöihin ja käsittelee vain kuumaan uudelleenlähetykseen liittyviä virheitä, ei muita virheitä. | `on`                |
| **`webforj.clientHeartbeatRate`** | Määrittelee aikavälin, jolla asiakas pingaa palvelinta nähdäksesi, onko se edelleen toiminnassa. Tämä auttaa ylläpitämään viestintää. Kehityksessä tämä tulisi asettaa lyhyemmäksi aikaväliksi, esimerkiksi `8s`, jotta palvelimen saatavuus voidaan nopeasti havaita. Älä aseta tätä alle 50 sekunnin tuotannossa liiallisen kyselyjen välttämiseksi. | `50s`               |
| **`webforj.components`**         | Kun määritetään, peruspolku määrittää, mistä DWC-komponentit ladataan. Oletuksena komponentit ladataan sovellusta isännöivältä palvelimelta. Kuitenkin määrittäminen mukautetun peruspolun avulla sallii komponenttien lataamisen vaihtoehtoiselta palvelimelta tai CDN:ltä. Esimerkiksi ladata komponentteja jsdelivr.com:sta, aseta peruspolku: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version}. On tärkeää, että ladatut komponentit ovat yhteensopivia käytössä olevan webforJ-kehyksen version kanssa; muuten sovellus ei välttämättä toimi odotetusti. Tätä asetusta ei huomioida käytettäessä tavanomaista BBj-asennusta ilman moottoria. Tavanomaisessa BBj-asennuksessa asetusta voidaan hallita `!COMPONENTS` STBL:llä. | `null`              |
| **`webforj.locale`**             | Määrittelee sovelluksen kielialueen, joka määrää kielen, alueasetukset ja päivämäärien, aikojen ja numeroiden muodot. | `null`              |
| **`webforj.assetsDir`**          | Määrittelee reittinimen, jota käytetään staattisten tiedostojen palveluun, kun fyysinen hakemistonimi pysyy `static`:na. Tämä konfigurointi on hyödyllinen, jos oletus `static`-reitti on ristiriidassa sovelluksessa määritetyn reitin kanssa, jolloin voit muuttaa reittinimeä ilman, että hakemistoa itsessään tarvitsee nimetä uudelleen. | `static`            |
| **`webforj.stringTable`**        | Avain-arvo-pareista koostuva kartta, jota käytetään merkkijonojen tallentamiseen sovelluksessa käytettävää varten. Hyödyllinen sovelluksen viestien tai tunnisteiden tallentamiseen. Lisätietoa `StringTable`:sta löytyy [täällä](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`                |
| **`webforj.fileUpload.accept`**   | Määrittelee sallitut tiedostotyypit tiedostojen lataamiseen. Oletuksena kaikki tiedostotyypit ovat sallittuja. Tuetut muodot sisältävät MIME-tyypit kuten `image/*`, `application/pdf`, `text/plain`, tai tiedostopäätteet kuten `*.txt`. Kun käytetään tavanomaista BBj-asennusta, tätä asetusta ei huomioida ja se hallitaan `fileupload-accept.txt`:n kautta. | `[]`                |
| **`webforj.fileUpload.maxSize`**  | Määrittelee enimmäiskoko, joka on sallittu tiedostojen latauksille, tavuina. Oletuksena ei ole rajoitusta. Kun käytetään tavanomaista BBj-asennusta, tätä asetusta ei huomioida ja se hallitaan `fileupload-accept.txt`:n kautta. | `null`              |
| **`license.cfg`**                 | Konfiguroi lisenssinsäätön hakemiston. Oletuksena se on sama kuin webforJ:n konfigurointihakemisto, mutta sen voi mukauttaa tarpeen mukaan. | `"."`               |

## `web.xml` konfigurointi {#configuring-webxml}

web.xml-tiedosto on olennainen konfigurointitiedosto Java-web-sovelluksille, ja webforJ:ssä se määrittelee tärkeitä asetuksia, kuten servletin konfiguroinnin, URL-mallit ja tervetulossivut. Tämä tiedosto tulisi sijaita projektisi käyttöönoton `WEB-INF`-hakemistossa.

| Asetus                                 | Selitys                                                                                                                                                                                  | Oletusarvo               |
|---------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------|
| **`<display-name>`**                  | Määrittää web-sovelluksen näyttönimen, joka on yleensä johdettu projektin nimestä. Tämä nimi näkyy sovelluspalvelinten hallintakonsolissa.                                               | `${project.name}`        |
| **`<servlet>` ja `<servlet-mapping>`** | Määrittelee `WebforjServlet`-palvelimen, joka on webforJ-pyyntöjen käsittelyyn käytettävä ydin servlet. Tämä servlet on kartoitettu kaikkiin URL-osoitteisiin (`/*`), mikä tekee siitä pääsisääntulopisteen web-pyynnöille. | `WebforjServlet`         |
| **`<load-on-startup>`**               | Määrittää, että `WebforjServlet` tulisi ladata, kun sovellus käynnistetään. Tämä asettaminen arvoon `1` takaa, että servlet ladataan heti, mikä parantaa alkuperäisten pyyntöjen käsittelyä. | `1`                      |
