---
title: Komponenttien tarkastelu
sidebar_position: 3
description: >-
  Browse the component tree webforJ built, select components from the page, and
  change their properties while the app runs.
_i18n_hash: 93bf0d91de1f942373082ccebd0bab6d
---
Inspector näyttää komponenttipuun, jonka Java-koodisi on rakentanut. `Composite` näkyy luokkana, jonka kirjoitit, pitäen sisäkkäisiä komponentteja, jotka annoit sille, siinä järjestyksessä, jossa webforJ pitää niitä, joten rakenne craftforJ:ssa vastaa alkuperäisessä koodissasi olevaa rakennetta.

![Komponenttipuu, jossa komponentti on valittu ja korostettu käynnissä olevassa sovelluksessa](/img/craftforj/inspector/tree-selection.png#rounded-border)

## Komponentin valitseminen {#selecting-a-component}

Valitaksesi komponentin sivulta, paina <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> ja klikkaa sitä. craftforJ valitsee vastaavan solmun puussa. Solmun osoittaminen puussa tekee päinvastoin ja korostaa tuota komponenttia sivulla, joten voit liikkua näytön ja puun välillä kummassakin suunnassa.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/pick-mode.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

Puuta varten voit etsiä painamalla <kbd>Cmd/Ctrl</kbd> + <kbd>F</kbd>. Termi, joka on kääritty viivojen sisään, käsitellään säännöllisenä lausekkeena. Klikkaamalla hiiren oikealla napilla solmua avautuu toimintoja, joita on saatavilla sille. Voit avata sen lähdekoodin tai antaa sen [avustajalle](/docs/craftforj/ai).

## Ominaisuuksien lukeminen ja muuttaminen {#reading-and-changing-properties}

Komponentin valitseminen täyttää sivupalkin sen ominaisuuksilla, ryhmiteltyinä sen mukaan, mitä ne vaikuttavat. Mitkä ominaisuudet komponentilla on, riippuu itse komponentista, ja osa niistä on vain luettavissa. Ominaisuudet, jotka eivät lueta hyvin tavallisena tekstinä, saavat sijaan editorin, joka sopii niiden arvon käsittelyyn. Arvon muuttaminen tulee voimaan käynnissä olevassa sovelluksessa heti.

:::info Elävät muokkaukset eivät muuta tiedostojasi
Ominaisuuden muokkaaminen muuttaa sovellusta edessäsi eikä mitään muuta. Sen siirtäminen alkuperäiseen koodiin on erillinen vaihe, joka on tehtävä tarkoituksella, ja se on kuvattu osiossa [Muutosten kirjoittaminen lähteeseen](/docs/craftforj/source-changes).
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/property-edit.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

## Komponentin lähdekoodin tarkastelu {#viewing-the-source-of-a-component}

Voit jäljittää minkä tahansa komponentin takaisin Javaan, joka rakensi sen. Oletusarvoisesti lähdekoodi avautuu craftforJ:ssa vain luku -tilassa, sijoitettuna riville, joka loi komponentin. Voit konfiguroida craftforJ:n avaamaan sen sen sijaan editorissasi samalta riviltä. Kun komponenttia ei voida jäljittää riville, craftforJ ilmoittaa siitä sen avaamisen sijaan tyhjällä katseluohjelmalla.

![Lähdekatsoin sijoitettuna riville, joka loi valitun komponentin](/img/craftforj/inspector/source-viewer.png#rounded-border)
