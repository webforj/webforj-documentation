---
title: Frontend watch
sidebar_position: 20
sidebar_class_name: new-content
description: >-
  Rebuild frontend sources while a webforJ app runs and refresh the browser,
  with stylesheet and image changes applied in place and script changes
  reloading the view.
_i18n_hash: efb22f8bcac71567979d21178e62ba7c
---
Frontend-watch rakentaa `src/main/frontend` -lähteet, kun sovellus toimii ja päivittää selainta, joten frontend-muutokset näkyvät ilman manuaalista rakennusta tai palvelimen uudelleenkäynnistystä. Se on kehityspuoli [frontend-bundlerista](/docs/managing-resources/bundler/overview).

## Ajaminen watch {#running-the-watch}

Aja se rinnakkain palvelimesi kanssa, tavoitteena ennen sitä, joka käynnistää sovelluksen. Spring-projekti asettaa tämän oletustavoitteeksi, joten `mvn` ilman argumentteja käynnistää molemmat:

```bash
mvn compile webforj:watch spring-boot:run
```

[ Maven Jetty pluginin](/docs/configuration/deploy-reload/maven-jetty-plugin) kanssa, käynnistä se samalla tavalla:

```bash
mvn compile webforj:watch jetty:run
```

Watchina rakennusvaiheena, katso [Rakennus ja testit](/docs/managing-resources/bundler/build-and-tests#the-development-watch).

## Mitä tapahtuu muutoksessa {#what-happens-on-a-change}

Kun tallennat lähteen, watch rakentelee uudelleen ja lähettää muuttuneet tiedostot selaimeen. Se, mitä selain tekee, riippuu ulostulosta, ei muokatusta lähteestä:

- **Tyylitiedosto** korjataan paikallaan, ilman latausta, joten lomaketiedot ja vierityksen sijainti pysyvät. Täällä päätyy .css, .scss, .sass tai .less muutos.
- **Kuva** vaihdetaan paikallaan, ilman latausta.
- **Mikä tahansa muu** latautuu uudelleen. Täällä päätyy .ts, .tsx tai .js muutos, koska uusi käyttäytyminen tarvitsee tuoreen sivun.

Jos uudelleenrakennus koskee useita tiedostoja ja kaikki voidaan korjata paikallaan, selain pysyy paikallaan. Jos edes yksi ei voi, se lataa uudelleen, joten et koskaan näe muutosta puolivälissä.

## Palvelimen uudelleenkäynnistyksen yhteydessä {#across-a-server-restart}

Java-muutos käynnistää palvelimen uudelleen [Spring DevTools](/docs/configuration/deploy-reload/spring-devtools), [Maven Jetty pluginin](/docs/configuration/deploy-reload/maven-jetty-plugin) tai [JRebelin](/docs/configuration/deploy-reload/jrebel) kautta. Watch pitää frontendin samalla tasolla sen kanssa:

- **Tyylisi pysyvät mukana** uudelleenkäynnistämisen aikana, sen sijaan, että vilkkuvat ilman tyyliä.
- **Sivu latautuu, kun sovellus on valmis**, ei ennen, joten et kohtaa virhettä liian aikaisesta lataamisesta. Pieni indikaattori näkyy, kun palvelin käynnistyy uudelleen; manuaalinen lataus ei näytä mitään.
- **`@BundleEntry`:n lisääminen tai poistaminen tulee voimaan seuraavassa uudelleenkäynnistyksessä.**

## Konfiguraatio {#configuration}

Live-reload on webforJ-asetus, joten se koskee mitä tahansa palvelinta, jota käytät. Spring-sovellus lukee nämä avaimet `application.properties`-tiedostosta; itsenäinen palvelin, kuten [Maven Jetty plugin](/docs/configuration/deploy-reload/maven-jetty-plugin), lukee samat avaimet `webforj.conf`-tiedostosta.

```ini title="application.properties"
# Päivitä selain muutoksessa
webforj.devtools.livereload.enabled=true

# Korjaa tyylitiedostot ja kuvat paikallaan lataamisen sijasta (oletus: true)
webforj.devtools.livereload.static-resources-enabled=true

# Portti ja polku, johon selain muodostaa yhteyden (oletukset: 35730, /webforj-devtools-ws)
webforj.devtools.livereload.websocket-port=35730
webforj.devtools.livereload.websocket-path=/webforj-devtools-ws

# Sydämenlyöntiväli millisekunteina (oletus: 30000)
webforj.devtools.livereload.heartbeat-interval=30000
```
