---
title: Aloittaminen
sidebar_position: 2
description: >-
  Add the devtools dependency, enable craftforJ in your configuration, and open
  craftforJ over a running webforJ app.
_i18n_hash: 6cb150ca0da866a142787024af9e2bd1
---
<!-- vale off -->
# Aloittaminen <DocChip chip='since' label='26.02' />
<!-- vale on -->

craftforJ toimitetaan webforJ:n mukana, joten sitä ei tarvitse ladata erikseen. Tällä sivulla kerrotaan, mitä sovelluksesi tarvitsee ennen kuin craftforJ tulee näkyviin ja miten se avataan.

:::tip Valmiiksi käytössä generoiduissa projekteissa
[startforJ](https://docs.webforj.com/startforj)-palvelulla luoduissa tai webforJ:n [arkkityypistä](/docs/building-ui/archetypes/overview) aloitetuissa projekteissa craftforJ on valmiiksi käytössä. Jos aloitit jommallakummalla tavalla, käynnistä sovelluksesi ja siirry kohtaan [craftforJ:n avaaminen](#opening-craftforj).
:::

## Vaatimukset {#requirements}

craftforJ liitetään sovellukseen vain, jos kaikki seuraavat ehdot täyttyvät. Jos jokin niistä ei täyty, mitään ei näy sivulla.

### Lisää riippuvuus {#add-the-dependency}

Lisää projektiisi `webforj-devtools`, jos sitä ei vielä ole siellä:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-devtools</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### Virheenkorjaustila ja craftforJ-lippu {#debug-mode-and-the-craftforj-flag}

Lisää seuraavat ominaisuudet projektiisi. Jos käytössäsi on tavallinen webforJ-sovellus, määritä ominaisuudet tiedostossa `webforj.conf`. Jos webforJ-projektisi käyttää [Springiä](/docs/integrations/spring/overview), määritä ominaisuudet tiedostossa `application.properties`.

```ini
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

craftforJ toimii vain, kun molemmat ominaisuudet ovat käytössä. Siksi sovellus, joka siirtyy tuotantoon virheenkorjaustila yhä käytössä, ei paljasta lähdekoodipuuta.

### Paikallinen selain ja kehittäjälisenssi {#a-local-browser-and-a-developer-license}

Avaa sovellus samalla koneella, jolla se suoritetaan, ja varmista, että sinulla on voimassa oleva kehittäjälisenssi. Jos haluat käyttää craftforJ:tä toiselta koneelta, lisää sen osoite [`hosts-allowed`](/docs/craftforj/configuration#access)-asetukseen.

Kun nämä ovat kunnossa, käynnistä sovellus uudelleen ja lataa sivu uudelleen.

## craftforJ:n avaaminen {#opening-craftforj}

Kun craftforJ on käytössä, sovelluksesi päällä näkyy käynnistyspainike. Avaa craftforJ napsauttamalla painiketta tai painamalla <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> missä tahansa sovelluksessa. Sama pikanäppäin sulkee craftforJ:n, ja voit vetää käynnistyspainikkeen haluamaasi kulmaan.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/getting-started.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

Sen välilehdillä voit tarkastella [komponenttipuuta](/docs/craftforj/inspector), [reittejä](/docs/craftforj/routes), [teemaa](/docs/craftforj/theme) ja [avustajaa](/docs/craftforj/ai). Niiden vieressä ovat asetukset ja sovelluksen tiedot.


- **Käynnistyspainike** avaa ja sulkee craftforJ:n. Se pysyy poissa tieltä, kun craftforJ on suljettu.
- **Välilehtipalkki** kulkee sovellusta lähinnä olevan reunan suuntaisesti, ja sen avulla vaihdetaan craftforJ:n näkymää.
- **Ikkunavalikko** sisältää craftforJ:n sijaintiin liittyvät asetukset. Niitä käsitellään kohdassa [craftforJ:n sijainti](#where-craftforj-sits).

:::info Pikanäppäimet macOS:ssä
craftforJ näyttää kunkin pikanäppäimen käyttämäsi alustan mukaisilla muokkausnäppäimillä. Siksi <kbd>Alt</kbd> näkyy muodossa <kbd>⌥</kbd> ja <kbd>Ctrl</kbd> muodossa <kbd>⌘</kbd>. Näet ajantasaisen luettelon painamalla craftforJ:ssä <kbd>Shift</kbd> + <kbd>?</kbd>.
:::

## craftforJ:n sijainti {#where-craftforj-sits}

Oletusarvoisesti craftforJ kelluu sovelluksesi päällä. Vedä se minne tahansa sivulla, muuta sen kokoa mistä tahansa reunasta ja pienennä se takaisin käynnistyspainikkeeksi, kun haluat sovelluksen kokonaan käyttöösi. Kun vedät sen sivun reunaan, se kiinnittyy siihen koko korkeudeltaan tai leveydeltään. Kullekin reunalle säilyy sille määrittämäsi koko. Kun vedät sen pois reunasta, se alkaa jälleen kellua.

:::info Kiinnittäminen peittää sovelluksen, mutta ei muuta sen asettelua
craftforJ piirretään sivun päälle. Sovelluksesi koko ei muutu eikä mikään sen sisällössä siirry pois tieltä, joten craftforJ:n alle jäävä sisältö peittyy. Jos haluat nähdä sen alla olevan sisällön, siirrä craftforJ toiselle reunalle tai siirrä se pois sivulta.
:::

![craftforJ kiinnitettynä sovellussivun oikeaan reunaan peittämässä sovelluksen reunaa](/img/craftforj/getting-started/docking.png#rounded-border)

Jos et halua craftforJ:n peittävän sovellusta lainkaan, siirrä se pois sivulta omaan selainikkunaan tai -välilehteensä. Tämä sopii käytettäväksi toisella näytöllä. craftforJ tarkastelee sovellustasi edelleen sen avaaman sivun kautta, joten pidä sivu avoinna. Jos siirryt siltä pois tai suljet sen, craftforJ:llä ei ole enää mitään tarkasteltavaa, ennen kuin avaat sovelluksen uudelleen.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/undock-window.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

Valitse ikkunan sijaan välilehti, jos käytät Chromen jaettua näkymää. Se asettaa sovelluksesi ja craftforJ:n vierekkäin ja hyväksyy vain varsinaiset välilehdet. Napsauta sovelluksesi välilehteä hiiren kakkospainikkeella, lisää se uuteen jaettuun näkymään ja valitse sitten craftforJ-välilehti.

:::info Jaettu näkymä on Chromen ominaisuus
Vierekkäisen näkymän tarjoaa Chrome, ei craftforJ. Muissa selaimissa ei ole vastaavaa ominaisuutta, joten craftforJ avautuu niissä tavalliselle välilehdelle, johon voit siirtyä. craftforJ toimii samalla tavalla kummassakin tapauksessa.
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/split-view.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

:::tip Siirtäminen avustajan kirjoittaessa
craftforJ:n siirtäminen toiseen ikkunaan keskeyttää yhä käynnissä olevan vastauksen suoratoiston. craftforJ kysyy ensin, ja siihen asti kirjoitettu sisältö säilyy keskustelussa.
:::

## Ensimmäisen muutoksen tekeminen {#making-a-first-change}

1. Aloita komponentin valinta painamalla <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd>.
2. Vie osoitin jonkin sovelluksesi kohteen päälle ja napsauta sitä.
3. Komponenttipuu valitsee kyseisen komponentin, ja sivupalkki näyttää sen ominaisuudet.
4. Muuta ominaisuutta. Käynnissä oleva sovellus päivittyy heti.

Muutos vaikuttaa vain edessäsi olevaan sovellukseen. Tiedostosi pysyvät ennallaan, kunnes tarkistat muutoksen ja otat sen käyttöön. Tätä käsitellään kohdassa [Muutosten kirjoittaminen lähdekoodiin](/docs/craftforj/source-changes).

![craftforJ avoinna käynnissä olevan sovelluksen vieressä, komponentti valittuna](/img/craftforj/getting-started/first-open.png#rounded-border)

Jos mitään ei näy, käy läpi kohta [Vianmääritys](/docs/craftforj/troubleshooting).
