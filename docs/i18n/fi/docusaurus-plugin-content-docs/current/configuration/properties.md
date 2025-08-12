---
title: Property Configuration
sidebar_position: 30
_i18n_hash: e2cc183e859c85e0d1f4a24c196b8a55
---
# WebforJ-ominaisuuksien määrittäminen

Jotta webforJ-sovellus voidaan onnistuneesti ottaa käyttöön ja suorittaa, tarvitaan useita keskeisiä konfiguraatiotiedostoja: `webforJ.conf` ja `web.xml`. Kukin näistä tiedostoista hallitsee erilaisia sovelluksen käyttäytymisen osa-alueita, kuten sisäänkäyntipisteitä ja virheenkorjausasetuksia servlet-kartoituksiin.

## `webforJ.conf` -tiedoston määrittäminen {#configuring-webforjconf}

`webforJ.conf` -tiedosto on keskeinen konfiguraatiotiedosto webforJ:ssä, joka määrittää sovelluksen asetukset, kuten sisäänkäyntipisteet, virheenkorjaustilan ja asiakas-palvelin-vuorovaikutuksen. Tiedosto on kirjoitettu [HOCON-muodossa](https://github.com/lightbend/config/blob/master/HOCON.md), ja sen tulisi sijaita `resources`-hakemistossa.

### Esimerkki `webforJ.conf` -tiedostosta {#example-webforjconf-file}

```Ini
# Tämä konfiguraatiotiedosto on HOCON-muodossa:
# https://github.com/lightbend/config/blob/master/HOCON.md

webforj.entry = com.webforj.samples.Application
webforj.debug = true
webforj.reloadOnServerError = on
webforj.clientHeartbeatRate = 1s
```

### Konfiguraatioasetukset {#configuration-options}

| Ominaisuus                       | Selitys                                                                                                                                                                            | Oletusarvo        |
|----------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------|
| **`webforj.entry`**              | Määrittää sovelluksen sisäänkäyntipisteen määrittämällä luokan täysi nimi, joka laajentaa `webforj.App`:ia. Jos sisäänkäyntipistettä ei ole määritetty, webforJ skannaa automaattisesti luokkareitin löytääkseen luokkia, jotka laajentavat `webforj.App`:ia. Jos useita luokkia löytyy, syntyy virhe. Kun paketissa on enemmän kuin yksi mahdollinen sisäänkäyntipiste, tämän asettaminen selkeästi on pakollista epäselvyyksien estämiseksi, tai vaihtoehtoisesti `AppEntry`-annotaatiota voidaan käyttää sisäänkäyntipisteen määrittämiseen ajonaikaisesti. | `null`             |
| **`webforj.debug`**              | Ota käyttöön virheenkorjaustila. Virheenkorjaustilassa webforJ tulostaa lisätietoja konsoliin ja näyttää kaikki poikkeukset selaimessa. Virheenkorjaustila on oletuksena pois päältä. | `null`             |
| **`webforj.reloadOnServerError`**| Käytettäessä hot-redeployia, koko WAR-tiedosto vaihdetaan. Jos asiakas yrittää lähettää palvelimelle pyyntöä sen ollessa käynnistämässä, syntyy virhe. Tämä asetus sallii asiakkaan yrittää ladata sivua uudelleen, jos palvelin on väliaikaisesti poissa käytöstä, toivoen, että se tulee pian takaisin verkkoon. Tämä koskee vain kehitysympäristöjä ja käsittelee vain hot-vaihtoon liittyviä virheitä, ei muita virheitä. | `on`               |
| **`webforj.clientHeartbeatRate`**| Määrittää väliajan, jonka kuluessa asiakas kysyy palvelimelta, onko se yhä toiminnassa. Tämä auttaa ylläpitämään yhteyttä. Kehityksessä tämä on hyvä asettaa lyhyemmäksi, esimerkiksi `8s`, jotta palvelimen saatavuus havaitaan nopeasti. Älä aseta tätä alle 50 sekunnin tuotannossa liiallisen kysyntärasitteen välttämiseksi. | `50s`              |
| **`webforj.components`**         | Kun se on määritetty, peruspolku määrää, mistä DWC-komponentit ladataan. Oletuksena komponentit ladataan sovellusta isännöivältä palvelimelta. Kuitenkin, mukautetun peruspolun asettaminen mahdollistaa komponenttien lataamisen vaihtoehtoiselta palvelimelta tai CDN:ltä. Esimerkiksi, jotta komponentit ladataan jsdelivr.com:ista, aseta peruspolku seuraavasti: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version}. On tärkeää, että ladatut komponentit ovat yhteensopivia käytön alla olevan webforJ-kehyksen version kanssa; muuten sovellus ei välttämättä toimi odotetusti. Tämä asetus ohitetaan, kun käytetään normaalia BBj-asennusta ilman moottoria. Normaalille BBj-asennukselle tämä asetus voidaan hallita `!COMPONENTS` STBL:llä. | `null`             |
| **`webforj.locale`**             | Määrittää sovelluksen paikallisuuden, joka määrittää kielen, alueasetukset sekä päivämäärien, aikojen ja numeroiden muotoilut. | `null`             |
| **`webforj.assetsDir`**          | Määrittää reitinnimen, jota käytetään staattisten tiedostojen palvelemiseen, samalla kun fyysinen kansionimi pysyy `static`:na. Tämä konfiguraatio on hyödyllinen, jos oletusreitti `static` on ristiriidassa sovelluksessasi määritetyn reitin kanssa, jolloin voit muuttaa reitinnimen ilman, että kansionimikään vaihdetaan. | `static`           |
| **`webforj.stringTable`**        | Avain-arvo-parien kartta, jota käytetään merkkijonojen tallentamiseen sovelluksessa. Käytännöllinen sovellusviestien tai etikettien tallentamiseen. Lisätietoja `StringTable`-luokasta löytyy [täältä](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`               |
| **`webforj.fileUpload.accept`**   | Määrittää sallitut tiedostotyypit tiedostojen latauksille. Oletuksena kaikki tiedostotyypit ovat sallittuja. Tuetut muodot sisältävät MIME-tyypit, kuten `image/*`, `application/pdf`, `text/plain` tai tiedostopäätteet, kuten `*.txt`. Kun käytetään normaalia BBj-asennusta, tätä asetusta ei huomioida ja sitä hallitaan `fileupload-accept.txt`:n kautta. | `[]`               |
| **`webforj.fileUpload.maxSize`**  | Määrittää ylärajan tiedoston koon sallimiselle tiedostojen latauksissa, tavuina. Oletuksena ei ole ylärajaa. Kun käytetään normaalia BBj-asennusta, tätä asetusta ei huomioida ja sitä hallitaan `fileupload-accept.txt`:n kautta. | `null`             |
| **`license.cfg`**                 | Määrittää lisenssikonfiguraation hakemiston. Oletuksena se on sama kuin webforJ-konfiguraatiohakemisto, mutta tämä voidaan mukauttaa tarvittaessa. | `"."`              |

## `web.xml` -tiedoston määrittäminen {#configuring-webxml}

`web.xml` -tiedosto on olennainen konfiguraatiotiedosto Java-verkkosovelluksille, ja webforJ:ssä se määrittää tärkeitä asetuksia, kuten servlet-konfiguraation, URL-mallit ja tervetuloa-sivut. Tämä tiedosto tulisi sijaita projektisi käyttöönoton `WEB-INF`-hakemistossa.

| Asetus                                     | Selitys                                                                                                                                                                                      | Oletusarvo                   |
|--------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------|
| **`<display-name>`**                       | Määrittää verkkosovelluksen näkyvän nimen, joka tyypillisesti johdetaan projektin nimestä. Tämä nimi näkyy sovelluspalvelimien hallintakonsolissa.                                         | `${project.name}`            |
| **`<servlet>` ja `<servlet-mapping>`**    | Määrittää `WebforjServlet`:in, joka on keskeinen servlet webforJ:lle saapuvien pyyntöjen käsittelyyn. Tämä servlet on kartoitettu kaikkiin URL-osoitteisiin (`/*`), mikä tekee siitä pääsovelluksen verkkopyyntöjen sisäänkäynnin. | `WebforjServlet`             |
| **`<load-on-startup>`**                    | Määrittää, että `WebforjServlet` pitäisi ladata, kun sovellus käynnistyy. Asettamalla tämän arvoksi `1` varmistetaan, että servlet lataa heti, mikä parantaa ensimmäisten pyyntöjen käsittelyä. | `1`                           |
