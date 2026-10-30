---
title: Aloittaminen
sidebar_position: 2
description: >-
  Add the devtools dependency, enable craftforJ in your configuration, and open
  craftforJ over a running webforJ app.
_i18n_hash: fb5860ba7349ef5523c2dfb6bc425d9b
---
<DocChip chip='since' label='26.02' />

craftforJ toimitetaan webforJ:n mukana, joten erikseen ei tarvitse ladata mitään. Tämä sivu käsittelee, mitä sovelluksesi tarvitsee ennen craftforJ:n ilmestymistä, ja kuinka avata se.

:::tip Jo aktivoitu luoduissa projekteissa
Projekti, joka on luotu [startforJ](https://docs.webforj.com/startforj) -työkalulla tai webforJ [archetypelta](/docs/building-ui/archetypes/overview), tulee craftforJ aktivoituna. Jos aloitit yhdestä näistä, käytä sovellustasi ja siirry suoraan osioon [Avaaminen craftforJ](#opening-craftforj).
:::

## Vaatimukset {#requirements}

craftforJ liitetään sovellukseen vain, jos kaikki seuraavat ovat totta. Jos mikään näistä ei täyty, mitään ei näy sivulla.

### Lisää riippuvuus {#add-the-dependency}

Lisää `webforj-devtools` projektiisi, jos sitä ei vielä ole:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-devtools</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### Virheenkorjaustila ja craftforJ-lippu {#debug-mode-and-the-craftforj-flag}

Lisää seuraavat ominaisuudet projektiisi. Jos sinulla on standardi webforJ -sovellus, aseta ominaisuudet `webforj.conf` -tiedostoon. WebforJ-projektissa, joka käyttää [Spring](/docs/integrations/spring/overview), aseta ominaisuudet `application.properties` -tiedostoon.

```ini
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

craftforJ toimii vain, kun molemmat ominaisuudet ovat käytössä; sen vuoksi sovellus, joka siirtyy tuotantoon virheenkorjaustila päällä, ei paljasta lähdepuuasi.

### Paikallinen selaimen ja kehittäjälisenssi {#a-local-browser-and-a-developer-license}

Avaa sovellus koneelta, joka sen ajaa, ja varmista, että sinulla on voimassa oleva kehittäjälisenssi. Jos haluat käyttää craftforJ:ta toiselta koneelta, lisää sen osoite [`hosts-allowed`](/docs/craftforj/configuration#access) -asetuksiin.

Kun nämä asiat ovat kunnossa, käynnistä sovellus uudelleen ja lataa sivu.

## Avaaminen craftforJ {#opening-craftforj}

Kun craftforJ on aktiivinen, käyttöliittymän ylle ilmestyy triggereinä toimiva painike. Napsauta sitä avataksesi craftforJ:n tai paina <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> mistä tahansa sovelluksesta. Sama pikakuvake sulkee craftforJ:n uudelleen, ja voit vetää triggereitä mihin tahansa kulmaan, joka sinulle sopii.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/getting-started.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

Sen välilehdet kattavat [komponenttipuun](/docs/craftforj/inspector), [reitit](/docs/craftforj/routes), [teeman](/docs/craftforj/theme) ja [avustajan](/docs/craftforj/ai). Asetukset ja sovellustiedot sijaitsevat niiden vieressä.

- **Triggeri** on painike, joka avaa ja sulkee craftforJ:n. Se pysyy sivussa, kun craftforJ on suljettu.
- **Välinelistus** kulkee sovelluksen lähimmällä reunalla ja vaihtaa sen välillä, mitä craftforJ näyttää.
- **Ikkunavalikko** pitää sisällään kaiken, mikä liittyy craftforJ:n sijaintiin, käsitellään osiossa [Missä craftforJ sijaitsee](#where-craftforj-sits).

:::info Pikakuvakkeet macOS:llä
craftforJ näyttää jokaisen pikakuvakkeen käytettävissä olevilla muunnoksilla valitsemallasi alustalla, joten <kbd>Alt</kbd> näkyy <kbd>⌥</kbd> ja <kbd>Ctrl</kbd> <kbd>⌘</kbd>:nä. Paina <kbd>Shift</kbd> + <kbd>?</kbd> craftforJ:ssa nähdäksesi nykyisen luettelon.
:::

## Missä craftforJ sijaitsee {#where-craftforj-sits}

craftforJ leijuu oletuksena sovelluksesi päällä. Voit vetää sen mihin tahansa sivun osaan, muuttaa sen kokoa mistä tahansa reunasta, ja pienentää sen takaisin triggereen, kun haluat sovelluksen itsellesi. Vetämällä sen sivun reunalle se kiinnittyy siihen, täydellä korkeudella tai leveydellä, ja jokainen reuna säilyttää antamasi koon. Vetämällä sen kauemmas reunasta se leijuu jälleen.

:::info Kiinnitys peittää sovelluksen, se ei mukautu
craftforJ on piirtänyt sivun päälle. Sovelluksesi ei muutu koossa, eikä mikään sen osista siirry pois tieltä, joten kaikki, mitä craftforJ:n alla on, piiloutuu sen ollessa siellä. Näet sen alla olevan siirtämällä craftforJ:n toiseen reunaan tai viemällä sen ulos sivulta.
:::

![craftforJ kiinnitetty sovelluksen sivun oikealle puolelle, peittäen sovelluksen sen reunan](/img/craftforj/getting-started/docking.png#rounded-border)

Jos haluat lopettaa sovelluksen peittämisen, siirrä craftforJ sivun ulkopuolelle ja omaan selaimen ikkunaan tai välilehteen, mikä sopii toiseen näyttöön. Se tarkistaa edelleen sovellustasi sen sivun kautta, joka avasi sen, joten pidä kyseinen sivu auki. Siirrä se pois tai sulje se, ja craftforJ:llä ei ole enää mitään tarkistettavaa, kunnes avaat sovelluksen uudelleen.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/undock-window.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

Valitse välinelista sen sijaan, että käyttäisit ikkunaa, jos käytät Chrome:n jakosivua, joka asettaa sovelluksesi ja craftforJ:n rinnakkain ja hyväksyy vain oikeita välilehtiä. Napsauta hiiren kakkospainiketta sovelluksesi välilehdessä, lisää se uuteen jakonäkymään, ja valitse sitten craftforJ:n välinelista.

:::info Jakonäkymä on Chrome-ominaisuus
Chrome tarjoaa vierekkäisen järjestelyn, ei craftforJ. Muilla selaimilla ei ole vastaavaa, joten craftforJ muissa selaimissa avautuu normaalissa välilehdessä, johon vaihdat. craftforJ itsessään toimii samalla tavalla kummassakin tapauksessa.
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/split-view.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

:::tip Liikkuminen avustajan kirjoittaessa
CraftforJ:n siirtäminen toiseen ikkunaan päättää vielä kesken olevan vastauksen. craftforJ kysyy ensin, ja kaikki siihen mennessä kirjoitettu pysyy keskustelussa.
:::

## Ensimmäisen muutoksen tekeminen {#making-a-first-change}

1. Paina <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> aloittaaksesi komponentin valitsemisen.
2. Vie hiiri jonkin kohdalle sovelluksessasi ja napsauta sitä.
3. Puu valitsee kyseisen komponentin, ja sivupaneeli täyttyy sen ominaisuuksista.
4. Muuta ominaisuus. Toimiva sovellus päivittyy heti.

Muutokset vaikuttavat vain sinulle näkyvään sovellukseen. Tiedostosi jäävät koskemattomiksi, kunnes tarkistat muutoksen ja hyväksyt sen, mikä kattaa osion [Muutosten kirjoittaminen lähteeseen](/docs/craftforj/source-changes).

![craftforJ avoinna rinnakkain toimivan sovelluksen kanssa, komponentti valittuna](/img/craftforj/getting-started/first-open.png#rounded-border)

Jos mitään ei näy, käy läpi osio [Vianetsintä](/docs/craftforj/troubleshooting).
