---
title: Property Configuration
sidebar_position: 1
description: >-
  Set webforJ entry points, debug mode, locales, file upload limits, and servlet
  mappings through webforj.conf and web.xml.
sidebar_class_name: updated-content
_i18n_hash: 0f672146394b053aaa5d59a7e59841b2
---
# WebforJ-ominaisuuksien määrittäminen

Jotta webforJ-sovellus voidaan onnistuneesti ottaa käyttöön ja käyttää, tarvitaan muutama keskeinen konfiguraatiotiedosto: `webforj.conf` ja `web.xml`. Kukin näistä tiedostoista hallitsee sovelluksen käytöksen eri näkökohtia, sisäänkäynneistä ja virheenkäsittelyasetuksista servlet-kartoituksiin.

## `webforj.conf` määrittäminen {#configuring-webforjconf}

`webforj.conf`-tiedosto on keskeinen konfiguraatiotiedosto webforJ:ssä, joka määrittää sovelluksen asetuksia, kuten sisäänkäynnit, virheenkäsittelytila ja asiakas-palvelin-vuorovaikutus. Tiedosto on [HOCON-muodossa](https://github.com/lightbend/config/blob/master/HOCON.md) ja sen tulisi sijaita `resources`-hakemistossa.

:::tip
Jos integroit [Springin](../integrations/spring/overview.md) kanssa, voit määrittää nämä `webforj.conf`-ominaisuudet `application.properties`-tiedostossa.
:::

### Esimerkki `webforj.conf`-tiedostosta {#example-webforjconf-file}

```Ini
# Tämä konfiguraatiotiedosto on HOCON-muodossa:
# https://github.com/lightbend/config/blob/master/HOCON.md

webforj.entry = com.webforj.samples.Application
webforj.debug = true
webforj.reloadOnServerError = on
webforj.clientHeartbeatRate = 1s
```

### Määrittelyvaihtoehdot {#configuration-options}

| Ominaisuus                             | Tyyppi    | Selitys                                                       | Oletus                |
|--------------------------------------|---------|-------------------------------------------------------------------|------------------------|
| **`webforj.assetsCacheControl`**     | String  | Cache-Control-otsake staattisille resursseille.                        | `null` |
| **`webforj.assetsDir`**              | String  | Reittinimi, jota käytetään staattisten tiedostojen tarjoamiseen, samalla kun todellinen kansion nimi pysyy `static`:na. Tämä konfiguraatio on hyödyllinen, jos oletus `static`-reitti on ristiriidassa sovelluksessa määritetyn reitin kanssa, jolloin voit muuttaa reittinimeä ilman, että kansiota tarvitsee nimetä uudelleen.       | `null`               |
| **`webforj.assetsExt`**              | String  | Oletustiedostopääte staattisille tiedostoille. | `null` |
| **`webforj.assetsIndex`**            | String  | Oletustiedosto, joka palvellaan hakupyyntöjen yhteydessä (esim., index.html). | `null` |
| **`webforj.clientHeartbeatRate`**    | String  | Aikaväli, jolla asiakas pyytää palvelimelta, onko se vielä toiminnassa. Kehityksessä tämän tulisi olla lyhyempi aikaväli, esimerkiksi `8s`, jotta palvelimen saatavuus voidaan nopeasti havaita. Tuotannossa on suositeltavaa asettaa vähintään 50 sekuntia, jotta vältetään liialliset pyynnöt. | `50s`           |
| **`webforj.components`**             | String  | Kun määritetään, peruspolku määrittää, mistä DWC-komponentit ladataan. Oletusarvoisesti komponentit ladataan sovellusta isännöivältä palvelimelta. Kuitenkin, mukautetun peruspolun määrittäminen mahdollistaa komponenttien lataamisen vaihtoehtoiselta palvelimelta tai CDN:ltä. Esimerkiksi, ladataksesi komponentteja jsdelivr.comista, aseta peruspolku seuraavaksi: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version}. On tärkeää, että ladattavat komponentit ovat yhteensopivia käytössä olevan webforJ-kehyksen version kanssa; muuten sovellus ei välttämättä toimi odotetusti. Tätä asetusta ei oteta huomioon, kun käytetään standardia BBj-asennusta ilman moottoria. Standardia BBj-asennusta varten asetus voidaan hallita `!COMPONENTS` STBL:llä. | `null`          |
| **`webforj.debug`**                  | Boolean | Ota debug-tila käyttöön. Debug-tilassa webforJ tulostaa lisätietoja konsoliin ja näyttää kaikki poikkeukset selainikkunassa. Debug-tila on oletusarvoisesti pois käytöstä. | `null`          |
| **`webforj.devtools.craftforj.enabled`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | **Vain kehitysympäristöt.** Käynnistää [craftforJ](../craftforj/overview.md), kehitysympäristön, joka tarkkailee käynnissä olevaa sovellusta, muokkaa komponenttien ominaisuuksia ja kirjoittaa muutokset takaisin Java-lähdekoodiin. Vaatii myös `webforj.debug`-asetuksen olevan päällä. Kumpikaan asetus ei ole riittävä yksinään. | `false` |
| **`webforj.devtools.craftforj.hosts-allowed`**&nbsp;<DocChip chip='since' label='26.02' /> | Lista | Asiakasosoitteet, jotka saavat käyttää craftforJ:ta koneelta, jonka sovellus toimii. Oletusarvoisesti vain sen koneen selain voi käyttää sitä. Merkkijono, joka päättyy `*`: een, vastaa etuliitettä, ja yksittäinen `*` poistaa rajoituksen. Katso [craftforJ:n turvallisuus](../craftforj/security.md). | vain loopback |
| **`webforj.devtools.craftforj.project-root`**&nbsp;<DocChip chip='since' label='26.02' /> | String | Hakemisto, jota craftforJ katsoo lähteiden etsimiseksi tilanteissa, joissa se ei pysty tunnistamaan sitä siitä, miten sovellus käynnistettiin. | havaittu |
| **`webforj.devtools.craftforj.source-changes`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | Saako craftforJ kirjoittaa ominaisuusmuutoksia ja reittipääsääntöjä Java-lähteeseesi. | `true` |
| **`webforj.devtools.craftforj.stylesheet-changes`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | Saako craftforJ tallentaa teemoja ja tyylejä sovelluksesi tyylitiedostoon. | `true` |
| **`webforj.devtools.craftforj.ai.enabled`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | Onko [craftforJ AI -avustaja](../craftforj/ai.md) käytettävissä. | `true` |
| **`webforj.devtools.craftforj.ai.freeform-changes`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | Saako avustaja kirjoittaa omaa Javaansa sen sijaan, että vain muuttaisi ominaisuuksia. Jokaisen muokkauksen on silti oltava käännettävä ja se tarvitsee silti hyväksynnän. | `true` |
| **`webforj.entry`**                  | String  | Määrittää sovelluksen sisäänkäynnin määrittelemällä luokan, joka laajentaa `webforj.App`, täysin määritellyn nimen. Jos sisäänkäyntiä ei ole määritetty, webforJ skannaa automaattisesti luokkareittitietojärjestelmän luokkia, jotka laajentavat `webforj.App`: ta. Jos useita luokkia löytyy, virhe tapahtuu. Kun paketti sisältää useita mahdollisia sisäänkäyntejä, tämän eksplisiittinen asettaminen on tarpeen epäselvyyksien estämiseksi, tai vaihtoehtoisesti `AppEntry`-annotaatiota voidaan käyttää sisäänkäynnin määrittämiseksi ajon aikana. | `null`          |
| **`webforj.i18n.supported-locales`**&nbsp;<DocChip chip='since' label='25.12' /> | Lista | Luettelo tuetuista alueista BCP 47 -kielietiketteinä (esim. `"en"`, `"en-US"`, `"fr"`, `"de-DE"`). Kun automaattinen tunnistus on käytössä, selaimen suosituimmat alueet verrataan tähän luetteloon. Luettelon ensimmäistä aluetta käytetään oletusvarana. Katso [Käännös](../advanced/i18n-localization.md). | `[]` |
| **`webforj.i18n.auto-detect`**&nbsp;<DocChip chip='since' label='25.12' /> | Boolean | Kun `true`, sovelluksen alue asetetaan automaattisesti selaimen suosituista kielistä käynnistyksessä. Alue ratkaistaan vertaamalla selaimen suosituimpia alueita `supported-locales`-luetteloon. Kun `false` tai kun `supported-locales` on tyhjää, sovellus käyttää `webforj.locale`:a. Katso [Käännös](../advanced/i18n-localization.md). | `false` |
| **`webforj.fileUpload.accept`**      | Lista    | Sallitut tiedostotyypit tiedostojen lataamiseen. Oletusarvoisesti kaikki tiedostotyypit ovat sallittuja. Tuetut muodot sisältävät MIME-tyypit, kuten `image/*`, `application/pdf`, `text/plain`, tai tiedostopäätteet, kuten `*.txt`. Kun käytetään standardia BBj-asennusta, tätä asetusta ei oteta huomioon ja se hallitaan `fileupload-accept.txt`:n kautta. | `[]`            |
| **`webforj.fileUpload.maxSize`**     | Long    | Suurin tiedostokoko, joka sallitaan tiedostojen lataamisessa, tavuina. Oletusarvoisesti ei ole rajaa. Kun käytetään standardia BBj-asennusta, tätä asetusta ei oteta huomioon ja se hallitaan `fileupload-accept.txt`:n kautta. | `null`          |
| **`webforj.iconsDir`**               | String  | URL-päätepiste ikonikansioon (oletusarvoisesti tarjoa `resources/icons/`-hakemistosta). | `icons/` |
| **`webforj.legacyHtmlInText`**&nbsp;<DocChip chip='since' label='26.01' /> | Boolean | Kun `true`, `<html>`-elementtiin kääritty arvo renderöi sen sisällön HTML:na. Kun `false`, sama arvo näytetään kirjaimellisesti. | `true` |
| **`webforj.license.cfg`**            | String  | Lisenssikonfiguraation hakemisto. Oletusarvoisesti se on sama kuin webforJ-konfiguraatiohakemisto, mutta tätä voidaan mukauttaa, jos tarpeen. | `"."`  |
| **`webforj.license.startupTimeout`** | Integer | Lisenssin käynnistysaika sekunneissa. | `null` |
| **`webforj.locale`**                 | String  | Sovelluksen alue, joka määrittää kielen, alueasetukset ja päivämäärien, aikojen ja lukujen muodot. | `null` |
| **`webforj.quiet`**                  | Boolean | Poistaa latauskuvion käytöstä sovelluksen käynnistämisen aikana. | `false` |
| **`webforj.reloadOnServerError`**    | Boolean | **Vain kehitysympäristöt.** Kehitysympäristössä automaattisesti lataa sivu virheiden sattuessa, jotka liittyvät kuumaan uudelleenasennukseen, mutta eivät muihin virhetyyppeihin. Kun käytetään kuumaa uudelleenasennusta, jos asiakas lähettää pyynnön palvelimelle sen ollessa käynnistymässä, virhe voi tapahtua WAR-tiedoston vaihtaessa. Koska palvelin todennäköisesti tulee takaisin verkkoon pian, tämä asetus antaa asiakkaan yrittää ladata sivun automaattisesti.  | `false` |
| **`webforj.security.maxContentLength`**&nbsp;<DocChip chip='since' label='25.10' /> | Integer | Suurin pyyntö, jonka sovellus hyväksyy, tavuina, suojana liian suuria pyyntöjä vastaan, jotka on tarkoitettu palvelimen muistin tyhjentämiseen. Aseta arvoksi `0`, jotta rajoitus poistuu. | `0` |
| **`webforj.security.maxInitPerMinute`**&nbsp;<DocChip chip='since' label='25.10' /> | Integer | Kuinka monta uutta sovellussessiota sovellus aloittaa minuutissa, suojana nopeille sessioiden luomisille, jotka on tarkoitettu palvelinresurssien tyhjentämiseen. Aseta arvoksi `0`, jotta nopeusrajoitus poistuu. | `0` |
| **`webforj.servlets[n].name`**       | String  | Servletin nimi (käyttää luokan nimeä, jos ei määritetty). | `null` |
| **`webforj.servlets[n].className`**  | String | Servletin täysin määritelty luokan nimi. | `null` |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Servletin alustamisparametrit. | `null` |
| **`webforj.sessionTimeout`**         | Integer | Istunnon aikakatkaisuaika sekunneissa. | `60` |
| **`webforj.stringTable`**            | `Map<String,String>` | Avain-arvo-pareista koostuva kartta, jota käytetään merkkijonojen tallentamiseen sovelluksessa. Hyödyllinen sovelluksen viestien tai etikettien tallentamisessa. Lisätietoja `StringTable`:sta löytyy [täältä](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`            |
| **`webforj.mime.extensions`**            | `Map<String,String>` | Mukautetut MIME-tyypin kartoitukset tiedostopäätteille staattisten tiedostojen tarjoamisen aikana. Mahdollistaa oletus MIME-tyyppien ylittämisen tai mukautettujen päätteiden MIME-tyyppien määrittämisen. Kartan avain on tiedostopäätte (ilman pistettä), ja arvo on MIME-tyyppi. | `{}`            |

## `web.xml` määrittäminen {#configuring-webxml}

`web.xml`-tiedosto on olennainen konfiguraatiotiedosto Java-verkkosovelluksille, ja webforJ:ssä se määrittää tärkeitä asetuksia, kuten servlet-konfiguraation, URL-mallit ja tervetuloissivut. Tämän tiedoston tulisi sijaita projektisi käyttöönottohierarkian `WEB-INF`-hakemistossa.

| Asetus                                 | Selitys                                                                                                                                                                                   | Oletusarvo               |
| --------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------- |
| **`<display-name>`**                    | Asettaa verkkosovelluksen näyttönimen, joka tyypillisesti johdetaan projektin nimestä. Tämä nimi näkyy sovelluspalvelimien hallintakonsolissa.                                                        | `${project.name}`           |
| **`<servlet>` ja `<servlet-mapping>`** | Määrittää `WebforjServlet`:in, webforJ: n ydinsalviran, joka käsittelee webforJ-pyyntöjä. Tämä servlet on kartoitettu kaikkiin URL-osoitteisiin (`/*`), mikä tekee siitä verkkopyyntöjen pääsisäänkäynnin.                     | `WebforjServlet`            |
| **`<load-on-startup>`**                 | Määrittää, että `WebforjServlet` -servletin tulisi ladata, kun sovellus käynnistyy. Asettaminen arvoon `1` saa servlettia lataamaan heti, mikä parantaa aloituspyyntöjen käsittelyä.                | `1`                         |
