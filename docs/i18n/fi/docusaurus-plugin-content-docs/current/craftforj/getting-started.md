---
title: Aloittaminen
sidebar_position: 2
description: >-
  Add the devtools dependency, enable craftforJ in your configuration, and open
  craftforJ over a running webforJ app.
_i18n_hash: 81825a3ba8656a8aee4820dee71da732
---
<DocChip chip='since' label='26.02' />

craftforJ toimitetaan webforJ:n kanssa, joten mitään ei tarvitse ladata erikseen. Tämä sivu käsittelee mitä sovelluksesi tarvitsee ennen kuin craftforJ tulee näkyviin, ja miten sen voi avata.

:::tip Jo aktivoitu generoituissa projekteissa
Projekti, joka on luotu [startforJ](https://docs.webforj.com/startforj) -työkalun tai webforJ [archetypen](/docs/building-ui/archetypes/overview) avulla, sisältää craftforJ:n oletuksena. Jos aloitit jostain näistä, suorita sovelluksesi ja siirry suoraan kohtaan [Avata craftforJ](#opening-craftforj).
:::

## Vaatimukset {#requirements}

craftforJ liitetään sovellukseen vain, kun kaikki seuraavat ehdot täyttyvät. Jos jokin niistä ei täyty, mitään ei näy sivulla.

### Lisää riippuvuus {#add-the-dependency}

Lisää `webforj-devtools` projektiisi, jos se ei ole siellä jo:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-devtools</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### Virheenkorjaustila ja craftforJ-lippu {#debug-mode-and-the-craftforj-flag}

Lisää seuraavat ominaisuudet projektiisi. Jos sinulla on tavanomainen webforJ-sovellus, aseta ominaisuudet `webforj.conf` -tiedostoon. webforJ-projektissa, joka käyttää [Springia](/docs/integrations/spring/overview), aseta ominaisuudet `application.properties` -tiedostoon.

```ini
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

craftforJ toimii vain, kun molemmat ominaisuudet ovat käytössä; joten sovellus, joka menee tuotantoon virheenkorjaustila päällä, ei paljasta lähdekoodipuun rakennetta.

### Paikallinen selain ja kehittäjälisenssi {#a-local-browser-and-a-developer-license}

Avaa sovellus koneelta, joka sen ajaa, ja varmista, että sinulla on voimassa oleva kehittäjälisenssi. Jos haluat käyttää craftforJ:ta toiselta koneelta, lisää sen osoite [`hosts-allowed`](/docs/craftforj/configuration#access).

Kun nämä asiat on kunnossa, käynnistä sovellus uudelleen ja lataa sivu.

## Avata craftforJ {#opening-craftforj}

Kun craftforJ on aktiivinen, sovelluksesi ylle ilmestyy aktivointipainike. Klikkaa sitä avataksesi craftforJ, tai paina <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> mistä tahansa sovelluksessa. Sama pikanäppäin sulkee craftforJ:n taas, ja voit vetää aktivointipainiketta haluamaasi nurkkaan.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/getting-started.mp4" type="video/mp4" />
  </video>
</div>

Sen välilehdet kattavat [komponenttipuun](/docs/craftforj/inspector), [reitit](/docs/craftforj/routes), [teeman](/docs/craftforj/theme) ja [avustajan](/docs/craftforj/ai). Asetukset ja sovellustiedot sijaitsevat niiden vieressä.

- **Aktivointipainike** on nappi, joka avaa ja sulkee craftforJ:n. Se pysyy syrjässä niin kauan kuin craftforJ on suljettuna.
- **Välilehtipalkki** kulkee sovellusta lähimpänä reunassa ja vaihtaa sitä, mitä craftforJ sinulle näyttää.
- **Ikkunavalikko** sisältää kaiken artikkelin craftforJ:n sijainnista, joka käsitellään kohdassa [Missä craftforJ sijaitsee](#where-craftforj-sits).

:::info Pikanäppäimet macOS:llä
craftforJ kirjoittaa jokaisen pikanäppäimen käyttäen alustan modifioijia, joten <kbd>Alt</kbd> näkyy <kbd>⌥</kbd> ja <kbd>Ctrl</kbd> <kbd>⌘</kbd> -näppäimenä. Paina <kbd>Shift</kbd> + <kbd>?</kbd> craftforJ:ssa nähdäksesi nykyisen luettelon.
:::

## Missä craftforJ sijaitsee {#where-craftforj-sits}

craftforJ kelluu oletuksena sovelluksesi ylle. Voit vetää sitä mihin tahansa sivulle, muuttaa sen kokoa mistä tahansa reunasta tai pienentää sen takaisin aktivointipainikkeeksi, jotta voit käyttää sovellusta rauhassa. Vetäminen sivun reunalle kiinnittää sen sinne, täysikorkeudelle tai -leveydelle, ja jokainen reuna pitää koon, jonka annoit sille. Vetäminen pois reunasta kelluttaa sen jälleen.

:::info Kiinnitys peittää sovelluksen, se ei muokkaa sitä
craftforJ piirretään sivun päälle. Sovelluksesi ei muutu koossa, eikä mikään siinä siirry pois tieltä, joten kaikki, mikä on craftforJ:n alla, on piilossa niin kauan kuin se on siinä. Näkymättömän sisällön näkemiseksi siirrä craftforJ toiseen reunaan tai poista se kokonaan sivulta.
:::

![craftforJ kiinnitettynä sovellussivun oikealle puolelle, peittäen sen reunan](/img/craftforj/getting-started/docking.png#rounded-border)

Jos haluat lopettaa sovelluksen peittämisen kokonaan, siirrä craftforJ pois sivulta ja omaan selainikkunaan tai -välilehteen, joka sopii toiseen näyttöön. Se tarkistaa edelleen sovellustasi sen sivun kautta, joka avasi sen, joten jätä se sivu auki. Navigoi pois tai sulje se, ja craftforJ:llä ei ole mitään tarkistettavaa, kunnes avaat sovelluksen uudelleen.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/undock-window.mp4" type="video/mp4" />
  </video>
</div>

Valitse välilehti ikkunoiden sijasta, jos käytät Chromen jakonäkymää, joka asettaa sovelluksesi ja craftforJ:n vierekkäin ja hyväksyy vain todelliset välilehdet. Napsauta hiiren oikealla sovelluksesi välilehteä, lisää se uuteen jakonäkymään ja valitse sitten craftforJ:n välilehti.

:::info Jakonäkymä on Chromen ominaisuus
Chrome tarjoaa vierekkäisen asettelun, ei craftforJ. Muilla selaimilla ei ole vastaavaa toimintoa, joten craftforJ muissa selaimissa avautuu tavallisessa välilehdessä, johon vaihdat. craftforJ toimii kummassakin tapauksessa samalla tavalla.
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/split-view.mp4" type="video/mp4" />
  </video>
</div>

:::tip Liikkuminen avustajan kirjoittaessa
craftforJ:n siirtäminen toiseen ikkunaan lopettaa vastauksen, joka on vielä käynnissä. craftforJ kysyy ensin, ja kaikki siihen asti kirjoitettu pysyy keskustelussa.
:::

## Ensimmäinen muutos {#making-a-first-change}

1. Paina <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> aloittaaksesi komponentin valitsemisen.
2. Vie hiiri jotakin kohdetta yli sovelluksessasi ja napsauta sitä.
3. Puu valitsee kyseisen komponentin, ja sivupaneeli täyttyy sen ominaisuuksista.
4. Muuta ominaisuus. Toiminnassa oleva sovellus päivitetään heti.

Muutos vaikuttaa vain edessäsi olevaan sovellukseen. Tiedostosi jäävät koskemattomiksi, kunnes tarkistat muutoksen ja vahvistat sen, mikä käsitellään kohdassa [Muutosten kirjoittaminen lähteeseen](/docs/craftforj/source-changes).

![craftforJ auki juoksevan sovelluksen vieressä valitun komponentin kanssa](/img/craftforj/getting-started/first-open.png#rounded-border)

Jos mitään ei näy, tutustu [Vianetsintään](/docs/craftforj/troubleshooting).
