---
title: Aloittaminen
sidebar_position: 2
description: >-
  Add the devtools dependency, enable craftforJ in your configuration, and open
  craftforJ over a running webforJ app.
_i18n_hash: 5d30f5dd80df008a05be5d4617c40369
---
<!-- vale off -->
# Käytön aloittaminen <DocChip chip='since' label='26.02' />
<!-- vale on -->

craftforJ toimitetaan webforJ:n mukana, joten sitä ei tarvitse ladata erikseen. Tällä sivulla kerrotaan, mitä sovelluksesi tarvitsee ennen kuin craftforJ tulee näkyviin ja miten sen voi avata.

:::tip Käytössä jo luoduissa projekteissa
[startforJ](https://docs.webforj.com/startforj)-palvelulla tai webforJ:n [archetype-pohjasta](/docs/building-ui/archetypes/overview) luoduissa projekteissa craftforJ on käytössä valmiiksi. Jos aloitit tällaisesta projektista, käynnistä sovelluksesi ja siirry suoraan kohtaan [craftforJ:n avaaminen](#opening-craftforj).
:::

## Vaatimukset {#requirements}

craftforJ liitetään sovellukseen vain, kun kaikki seuraavat ehdot täyttyvät. Jos jokin niistä ei täyty, sivulla ei näy mitään.

### Lisää riippuvuus {#add-the-dependency}

Lisää projektiisi `webforj-devtools`, jos sitä ei vielä ole:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-devtools</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### Vianmääritystila ja craftforJ-lippu {#debug-mode-and-the-craftforj-flag}

Lisää projektiisi seuraavat ominaisuudet. Jos käytössäsi on tavallinen webforJ-sovellus, määritä ominaisuudet tiedostossa `webforj.conf`. Jos käytössäsi on webforJ-projekti, jossa käytetään [Springiä](/docs/integrations/spring/overview), määritä ominaisuudet tiedostossa `application.properties`.

```ini
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

craftforJ toimii vain, kun molemmat ominaisuudet ovat käytössä. Siksi sovellus, joka viedään tuotantoon vianmääritystilan ollessa päällä, ei paljasta lähdekoodipuuta.

### Paikallinen selain ja kehittäjälisenssi {#a-local-browser-and-a-developer-license}

Avaa sovellus samalta koneelta, jolla se toimii, ja varmista, että sinulla on voimassa oleva kehittäjälisenssi. Jos haluat käyttää craftforJ:tä toiselta koneelta, lisää sen osoite [`hosts-allowed`](/docs/craftforj/configuration#access)-asetukseen.

Kun nämä ehdot täyttyvät, käynnistä sovellus uudelleen ja lataa sivu uudelleen.

## craftforJ:n avaaminen {#opening-craftforj}

Kun craftforJ on käytössä, sovelluksesi päällä näkyy avauspainike. Avaa craftforJ napsauttamalla painiketta tai painamalla <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> missä tahansa sovelluksessa. Sama pikanäppäin sulkee craftforJ:n, ja voit vetää avauspainikkeen haluamaasi kulmaan.

<VideoPlayer video="craftforj/getting-started" width={1920} height={984} title="Getting started" />

Välilehdillä voit tarkastella [komponenttipuuta](/docs/craftforj/inspector), [reittejä](/docs/craftforj/routes), [teemaa](/docs/craftforj/theme) ja [avustajaa](/docs/craftforj/ai). Niiden vieressä ovat asetukset ja sovelluksen tiedot.


- **Avauspainike** avaa ja sulkee craftforJ:n. Se pysyy poissa tieltä, kun craftforJ on suljettuna.
- **Välilehtipalkki** kulkee sovellusta lähinnä olevaa reunaa pitkin, ja sen avulla voit vaihtaa craftforJ:n näkymää.
- **Ikkunavalikossa** ovat craftforJ:n sijaintiin liittyvät toiminnot, joita käsitellään kohdassa [craftforJ:n sijainti](#where-craftforj-sits).

:::info Pikanäppäimet macOS:ssä
craftforJ näyttää pikanäppäimet käyttämäsi alustan mukaisilla muokkausnäppäimillä, joten <kbd>Alt</kbd> näkyy muodossa <kbd>⌥</kbd> ja <kbd>Ctrl</kbd> muodossa <kbd>⌘</kbd>. Näet nykyisen pikanäppäinluettelon painamalla craftforJ:ssä <kbd>Shift</kbd> + <kbd>?</kbd>.
:::

## craftforJ:n sijainti {#where-craftforj-sits}

craftforJ kelluu oletuksena sovelluksesi päällä. Voit vetää sen mihin tahansa sivulla, muuttaa sen kokoa mistä tahansa reunasta ja pienentää sen takaisin avauspainikkeeksi, kun haluat käyttää sovellusta rauhassa. Kun vedät sen sivun reunaan, se kiinnittyy siihen koko korkeudeltaan tai leveydeltään, ja se muistaa kullekin reunalle määrittämäsi koon. Kun vedät sen pois reunasta, se kelluu jälleen.

:::info Kiinnitys peittää sovelluksen, ei muuta sen asettelua
craftforJ piirretään sivun päälle. Sovelluksesi koko ei muutu eikä mikään siirry pois sen tieltä, joten craftforJ:n alle jäävä sisältö on sen ollessa paikallaan piilossa. Näet sen siirtämällä craftforJ:n toiselle reunalle tai poistamalla sen sivulta.
:::

![craftforJ kiinnitettynä sovellussivun oikeaan reunaan ja peittämässä kyseisen reunan sisältöä](/img/craftforj/getting-started/docking.png#rounded-border)

Jos et halua craftforJ:n peittävän sovellusta lainkaan, siirrä se sivulta omaan selainikkunaan tai -välilehteen. Tämä sopii erityisesti silloin, kun käytössäsi on toinen näyttö. craftforJ tarkastelee sovellustasi edelleen sen avanneen sivun kautta, joten jätä kyseinen sivu auki. Jos siirryt siltä toiselle sivulle tai suljet sen, craftforJ:llä ei ole enää mitään tarkasteltavaa ennen kuin avaat sovelluksen uudelleen.

<VideoPlayer video="craftforj/undock-window" width={1920} height={1048} title="Undock window" />

Valitse ikkunan sijaan välilehti, jos käytät Chromen jaettua näkymää. Se näyttää sovelluksesi ja craftforJ:n vierekkäin ja hyväksyy vain oikeita välilehtiä. Napsauta sovelluksesi välilehteä hiiren kakkospainikkeella, lisää se uuteen jaettuun näkymään ja valitse sitten craftforJ-välilehti.

:::info Jaettu näkymä on Chromen ominaisuus
Vierekkäisen näkymän tarjoaa Chrome, ei craftforJ. Muissa selaimissa ei ole vastaavaa ominaisuutta, joten craftforJ avautuu niissä tavalliselle välilehdelle, johon vaihdat itse. Itse craftforJ toimii kummassakin tapauksessa samalla tavalla.
:::

<VideoPlayer video="craftforj/split-view" width={1920} height={1080} title="Split view" />

:::tip Siirtäminen avustajan kirjoittaessa
craftforJ:n siirtäminen toiseen ikkunaan keskeyttää parhaillaan virtaavan vastauksen. craftforJ kysyy ensin, ja siihen mennessä kirjoitettu sisältö säilyy keskustelussa.
:::

## Ensimmäisen muutoksen tekeminen {#making-a-first-change}

1. Aloita komponentin valinta painamalla <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd>.
2. Vie osoitin jonkin sovelluksesi kohteen päälle ja napsauta sitä.
3. Komponenttipuu valitsee kyseisen komponentin, ja sivupalkkiin tulevat sen ominaisuudet.
4. Muuta ominaisuutta. Käynnissä oleva sovellus päivittyy heti.

Muutos vaikuttaa vain edessäsi olevaan sovellukseen. Tiedostosi pysyvät ennallaan, kunnes tarkistat ja otat muutoksen käyttöön. Tätä käsitellään kohdassa [Muutosten kirjoittaminen lähdekoodiin](/docs/craftforj/source-changes).

![craftforJ avoinna käynnissä olevan sovelluksen vieressä, ja yksi komponentti on valittuna](/img/craftforj/getting-started/first-open.png#rounded-border)

Jos mitään ei näy, käy läpi [vianmääritysohjeet](/docs/craftforj/troubleshooting).
