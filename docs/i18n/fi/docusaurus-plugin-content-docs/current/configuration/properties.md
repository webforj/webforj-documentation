---
title: Ominaisuuksien määrittäminen
sidebar_position: 1
description: >-
  Set webforJ entry points, debug mode, locales, file upload limits, and servlet
  mappings through webforj.conf and web.xml.
sidebar_class_name: updated-content
_i18n_hash: fd84f72aa62a993485d33fe9a94ab0d1
---
# webforJ:n asetusten määrittäminen

Jotta webforJ-sovellus voidaan onnistuneesti ottaa käyttöön ja suorittaa, tarvitaan useita keskeisiä konfigurointitiedostoja: `webforj.conf` ja `web.xml`. Kukin näistä tiedostoista ohjaa erilaisia sovelluksen toimintoja, kuten sisäänkäyntipisteitä ja virheenkorjausasetuksia sekä servlet-kartoituksia.

## `webforj.conf`:n määrittäminen {#configuring-webforjconf}

`webforj.conf` -tiedosto on keskeinen konfigurointitiedosto webforJ:ssä, ja se määrittää sovelluksen asetukset, kuten sisäänkäyntipisteet, virheenkorjaustilan ja asiakas-palvelin -vuorovaikutuksen. Tiedosto on [HOCON-formaatissa](https://github.com/lightbend/config/blob/master/HOCON.md), ja sen tulisi sijaita `resources`-hakemistossa.

:::tip
Jos integroitat [Springin](../integrations/spring/overview.md) kanssa, voit määrittää nämä `webforj.conf` -asetukset `application.properties` -tiedostossa.
:::

### Esimerkkitiedosto `webforj.conf` {#example-webforjconf-file}

```Ini
# Tämä konfigurointitiedosto on HOCON-muodossa:
# https://github.com/lightbend/config/blob/master/HOCON.md

webforj.entry = com.webforj.samples.Application
webforj.debug = true
webforj.reloadOnServerError = on
webforj.clientHeartbeatRate = 1s
```

### Konfigurointivaihtoehdot {#configuration-options}

| Ominaisuus                           | Tyyppi    | Selitys                                                       | Oletusarvo                |
|--------------------------------------|---------|-------------------------------------------------------------------|------------------------|
| **`webforj.assetsCacheControl`**     | Merkkijono  | Cache-Control-header staattisia resursseja varten.                        | `null` |
| **`webforj.assetsDir`**              | Merkkijono  | Reitin nimi, jota käytetään staattisten tiedostojen tarjoamiseen, kun taas varsinainen kansion nimi pysyy `static`. Tämä konfigurointi on hyödyllinen, jos oletusreitti `static` on ristiriidassa sovelluksessa määritellyn reitin kanssa, jolloin voit muuttaa reitin nimeä nimeämättä kansiota uudelleen.       | `null`               |
| **`webforj.assetsExt`**              | Merkkijono  | Oletustiedostopääte staattisille tiedostoille. | `null` |
| **`webforj.assetsIndex`**            | Merkkijono  | Oletustiedosto, joka tarjoillaan hakemustilanteissa (esim. index.html). | `null` |
| **`webforj.clientHeartbeatRate`**    | Merkkijono  | Aika, jolloin asiakas lähetää pingin palvelimelle tarkistaakseen, että se on yhä toiminnassa. Kehitysvaiheessa tämä on syytä asettaa lyhyemmäksi ajaksi, esimerkiksi `8s`, jotta palvelimen saatavuus voitaisiin nopeasti havaita. Tuotannossa asetetaan 50 sekuntia tai enemmän liiallisten pyyntöjen välttämiseksi. | `50s`           |
| **`webforj.components`**             | Merkkijono  | Kun määritetään, pohjareitti ohjaa DWC-komponenttien latausta. Oletuksena komponentit ladataan palvelimelta, joka isännöi sovellusta. Kustomoidun pohjareitin asettaminen mahdollistaa komponenttien lataamisen vaihtoehtoiselta palvelimelta tai CDN:ltä. Esimerkiksi, ladataksesi komponentteja jsdelivr.com-sivustolta, aseta pohjareitti seuraavasti: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version}. On tärkeää, että ladatut komponentit ovat yhteensopivia käytössä olevan webforJ-kehyksen version kanssa; muuten sovellus ei toimi odotetulla tavalla. Tällöin tätä asetusta ei oteta huomioon käytettäessä standardia BBj-asennusta ilman moottoria. Standardissa BBj-asennuksessa asetus voidaan hallita `!COMPONENTS` STBL:llä. | `null`          |
| **`webforj.debug`**                  | Boolean | Käynnistää virheenkorjaustilan. Virheenkorjaustilassa webforJ tulostaa lisätietoja konsoliin ja näyttää kaikki poikkeukset selaimessa. Virheenkorjaustila on oletusarvoisesti pois päältä. | `null`          |
| **`webforj.devtools.craftforj.enabled`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | **Vain kehitysympäristöille.** Käynnistää [craftforJ](../craftforj/overview.md), kehitysympäristön, joka tarkistaa toimivaa sovellusta, muokkaa komponenttiparametreja ja kirjoittaa muutokset takaisin Java-lähdekoodiin. Vaatii myös `webforj.debug`-asetuksen olevan päällä. Mikään ominaisuus ei riitä itsessään. | `false` |
| **`webforj.devtools.craftforj.hosts-allowed`**&nbsp;<DocChip chip='since' label='26.02' /> | Lista | Asiakasosoitteet, joilla on lupa päästä craftforJ:hin sen koneen ulkopuolelta, jolla sovellus toimii. Oletusarvoisesti vain tuon koneen selain voi päästä siihen. Tähdellä päättyvä merkintä vastaa etuliitettä, ja yksinkertainen `*` poistaa rajoituksen. Katso [craftforJ:n turvallisuus](../craftforj/security.md). | vain loopback |
| **`webforj.devtools.craftforj.source-changes`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | Voiko craftforJ kirjoittaa omaan lähdekoodiisi osiot ja reittipääsy-säännöt. | `true` |
| **`webforj.devtools.craftforj.stylesheet-changes`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | Voiko craftforJ tallentaa teemoja ja tyylejä sovelluksesi tyylitiedostoon. | `true` |
| **`webforj.devtools.craftforj.ai.enabled`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | Onko [craftforJ:n AI-avustaja](../craftforj/ai.md) käytettävissä. | `true` |
| **`webforj.devtools.craftforj.ai.freeform-changes`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | Voiko avustaja kirjoittaa omaa Java-koodia sen sijaan, että vain muuttaa ominaisuuksia. Jokaisen muutoksen on silti oltava käännettävä ja se tarvitsee hyväksyntäsi. | `true` |
| **`webforj.devtools.project-root`**&nbsp;<DocChip chip='since' label='26.03' /> | Merkkijono | Hakemisto, jota [craftforJ](../craftforj/configuration.md#project-root) tarkastaa lähteidesi löytämiseksi, tilanteissa joissa se ei voi määrittää sitä sovelluksen käynnistämistavasta. Korvataan `webforj.devtools.craftforj.project-root`, joka on vanhentunut mutta luetaan edelleen kun avain ei ole asetettu. | havaittu |
| **`webforj.entry`**                  | Merkkijono  | Määrittää sovelluksen sisäänkäyntipisteen määrittämällä täydellinen luokan nimi, joka laajentaa `webforj.App`:ia. Jos sisäänkäyntipistettä ei ole määritelty, webforJ skannaa automaattisesti luokkareitin luokkia, jotka laajentavat `webforj.App`:ia. Jos useita luokkia löytyy, virhe tapahtuu. Kun paketissa on enemmän kuin yksi mahdollinen sisäänkäyntipiste, tämän asettaminen nimenomaisesti on tarpeen epäselvyyksien välttämiseksi, tai vaihtoehtoisesti `AppEntry`-annotaatiota voidaan käyttää sisäänkäyntipisteen määrittämiseksi ajoajossa. | `null`          |
| **`webforj.i18n.supported-locales`**&nbsp;<DocChip chip='since' label='25.12' /> | Lista | Lista tuetuista kielistä BCP 47 -kielitunnuksina (esim. `"en"`, `"en-US"`, `"fr"`, `"de-DE"`). Kun automaattinen tunnistus on käytössä, selaimen suosituimmat kielet verrataan tähän lista. Ensimmäistä kieltä listassa käytetään oletustakaisuasetuksena. Katso [Käännös](../advanced/i18n-localization.md). | `[]` |
| **`webforj.i18n.auto-detect`**&nbsp;<DocChip chip='since' label='25.12' /> | Boolean | Kun `true`, sovellus asetetaan automaattisesti selaimen suosituimmasta kielestä käynnistyksessä. Kieli ratkotaan vertaamalla selaimen suosikkeja `supported-locales` -listaan. Kun `false` tai kun `supported-locales` on tyhjennetty, sovellus käyttää `webforj.locale`:a. Katso [Käännös](../advanced/i18n-localization.md). | `false` |
| **`webforj.fileUpload.accept`**      | Lista    | Sallittu tiedostotyyppien luettelo tiedostojen lataamista varten. Oletusarvoisesti kaikki tiedostotyypit ovat sallittuja. Tuetut muodot sisältävät MIME-tyypit kuten `image/*`, `application/pdf`, `text/plain`, tai tiedostopäätteet kuten `*.txt`. Kun käytetään standardia BBj-asennusta, tätä asetusta ei oteta huomioon ja sen hallinta tapahtuu `fileupload-accept.txt`:ssä. | `[]`            |
| **`webforj.fileUpload.maxSize`**     | Pitkä    | Suurin sallittu tiedostokoko latausta varten, tavuina. Oletusarvoisesti rajoitusta ei ole. Kun käytetään standardia BBj-asennusta, tätä asetusta ei oteta huomioon ja sen hallinta tapahtuu `fileupload-accept.txt`:ssä. | `null`          |
| **`webforj.iconsDir`**               | Merkkijono  | URL-päätepiste ikonihakemistoon (oletusarvoisesti tarjoaa `resources/icons/`). | `icons/` |
| **`webforj.legacyHtmlInText`**&nbsp;<DocChip chip='since' label='26.01' /> | Boolean | Kun `true`, `<html>`-elementissä oleva arvo renderöi sen sisällön HTML-muodossa. Kun `false`, sama arvo näytetään kirjaimellisesti. | `true` |
| **`webforj.license.cfg`**            | Merkkijono  | Lisenssikonfiguraation hakemisto. Oletusarvoisesti se on sama kuin webforJ-konfiguraatiohakemisto, mutta tämän voi mukauttaa tarvittaessa. | `"."`  |
| **`webforj.license.startupTimeout`** | Kokonaisluku | Lisenssin käynnistysajan aikaraja sekunteina. | `null` |
| **`webforj.locale`**                 | Merkkijono  | Sovelluksen kieli, joka määrittelee kielen, alueasetukset sekä päivämäärien, aikojen ja numeroiden muodot. | `null` |
| **`webforj.quiet`**                  | Boolean | Poistaa latauskuvan käytöstä sovelluksen käynnistyksen aikana. | `false` |
| **`webforj.reloadOnServerError`**    | Boolean | **Vain kehitysympäristöille.** Kehitysympäristössä latauksen automaattinen uusiminen virhetilanteissa, jotka liittyvät kuuman uudelleentoimittamiseen, mutta ei muissa virhetuissa. Kuuman uudelleentoimittamisen käytössä, jos asiakas lähettää pyynnön palvelimelle samalla kun se käynnistää, virhe voi ilmetä kun WAR-tiedosto vaihtuu. Koska palvelin todennäköisesti palaa pian takaisin verkkoon, tämä asetus mahdollistaa asiakkaan yrittää sivun lataamista automaattisesti.  | `false` |
| **`webforj.security.maxContentLength`**&nbsp;<DocChip chip='since' label='25.10' /> | Kokonaisluku | Suurin pyyntö, jonka sovellus hyväksyy, tavuina, suojana ylisuureilta pyynnöiltä, jotka on tarkoitettu palvelimen muistin kuluttamiseen. Aseta `0` rajoituksen poistamiseksi. | `0` |
| **`webforj.security.maxInitPerMinute`**&nbsp;<DocChip chip='since' label='25.10' /> | Kokonaisluku | Kuinka monta uutta sovellusistuntoa sovellus aloittaa minuutissa, suojana nopeita istuntojen luontia vastaan, jotka on tarkoitettu palvelinresurssien kuluttamiseen. Aseta `0` rajoituksen poistamiseksi. | `0` |
| **`webforj.servlets[n].name`**       | Merkkijono  | Servlet-nimi (käyttää luokan nimeä, jos ei ole määritetty). | `null` |
| **`webforj.servlets[n].className`**  | Merkkijono | Servletin täysin määritetty luokan nimi. | `null` |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Servletin alustamisasetukset. | `null` |
| **`webforj.sessionTimeout`**         | Kokonaisluku | Istunnon aikaraja sekunteina. | `60` |
| **`webforj.stringTable`**            | `Map<String,String>` | Avain-arvo-pareja, jota käytetään merkkijonojen tallentamiseen sovelluksessa. Hyödyllinen sovellusviestien tai etikettien tallentamiseen. Lisätietoja `StringTable`:sta löytyy [täältä](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`            |
| **`webforj.mime.extensions`**            | `Map<String,String>` | Mukautetut MIME-tyyppikartoitukset tiedostopäätteille staattisten tiedostojen tarjoamiseksi. Mahdollistaa oletus MIME-tyyppien korvaamisen tai määrittämään MIME-tyyppejä mukautetuille päätteille. Kartan avain on tiedostopäätteesi (ilman pistettä), ja arvo on MIME-tyyppi. | `{}`            |

## `web.xml`:n määrittäminen {#configuring-webxml}

`web.xml` -tiedosto on olennainen konfigurointitiedosto Java-verkkosovelluksille, ja webforJ:ssä se määrittää tärkeitä asetuksia, kuten servlet-konfiguraation, URL-mallit ja tervetulosivut. Tämä tiedosto tulisi sijaita projektin `WEB-INF` -hakemistossa.

| Asetus                                 | Selitys                                                                                                                                                                                   | Oletusarvo               |
| --------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------- |
| **`<display-name>`**                    | Määrittää verkkosovelluksen näyttönimen, joka yleensä johdetaan projektin nimestä. Tämä nimi näkyy sovelluspalvelimien hallintakonsolissa.                                                        | `${project.name}`         |
| **`<servlet>` ja `<servlet-mapping>`** | Määrittää `WebforjServlet`:in, ydinselvyn webforJ-pyyntöjen käsittelemiseksi. Tämä servlet kartoitetaan kaikkiin URL-osoitteisiin (`/*`), jolloin se toimii pääsisäänkäyntinä verkkopyynnöille.                     | `WebforjServlet`          |
| **`<load-on-startup>`**                 | Määrittää, että `WebforjServlet` -tiedosto tulisi ladata, kun sovellus käynnistetään. Asettamalla tämän arvoon `1` servlet ladataan heti, mikä parantaa alkuperäisen pyynnön käsittelyä.                | `1`                       |
