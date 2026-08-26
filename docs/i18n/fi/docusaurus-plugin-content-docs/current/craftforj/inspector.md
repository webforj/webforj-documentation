---
title: Komponenttien tarkastelu
sidebar_position: 3
description: >-
  Browse the component tree webforJ built, select components from the page, and
  change their properties while the app runs.
_i18n_hash: 5dd1df77df56d81dd4e54c1998289e71
---
Inspektori näyttää komponenttipuun, jonka Java-koodisi rakensi. `Composite` näkyy sinä luokkana, jonka kirjoitit, pitäen sisäiset komponentit, jotka annoit sille, siinä järjestyksessä, jossa webforJ pitää niitä, joten rakenne craftforJ:ssa vastaa lähdekoodisi rakennetta.

![Komponenttipuu, jossa komponentti valittuna ja korostettuna käynnissä olevassa sovelluksessa](/img/craftforj/inspector/tree-selection.png#rounded-border)

## Komponentin valitseminen {#selecting-a-component}

Jos haluat valita komponentin sivulta, paina <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> ja napsauta sitä. craftforJ valitsee vastaavan solmun puusta. Kun hiiren osoitin on solmun kohdalla puussa, se korostaa kyseistä komponenttia sivulla, jolloin voit liikkua näytön ja puun välillä kumpaan suuntaan tahansa.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/pick-mode.mp4" type="video/mp4" />
  </video>
</div>

Voit etsiä puusta painamalla <kbd>Cmd/Ctrl</kbd> + <kbd>F</kbd>. Termi, joka on kääritty vinoviivoihin, käsitellään säännöllisenä lausekkeena. Napsauttamalla hiiren oikealla painikkeella solmua avautuu käytettävissä olevat toiminnot. Voit avata sen lähteen tai antaa sen [avustajalle](/docs/craftforj/ai).

## Ominaisuuksien lukeminen ja muuttaminen {#reading-and-changing-properties}

Komponentin valitseminen täyttää sivupalkin sen ominaisuuksilla, ryhmiteltyinä sen vaikutusten mukaan. Mitkä ominaisuudet komponentti tarjoaa, riippuu komponentista, ja osa niistä on vain luku -tilassa. Ominaisuudet, joita ei voi lukea hyvin yksinkertaisena tekstinä, saavat sen sijaan arvoonsa sopivan editorin. Arvon muuttaminen on voimassa heti käynnissä olevassa sovelluksessa.

:::info Suorat muokkaukset eivät muuta tiedostoja
Ominaisuuden muokkaus muuttaa sovellusta edessäsi eikä mitään muuta. Sen saaminen lähteeseesi on erillinen vaihe, joka toteutetaan tietoisesti, kuvattu kohdassa [Muutosten kirjoittaminen lähteeseen](/docs/craftforj/source-changes).
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/property-edit.mp4" type="video/mp4" />
  </video>
</div>

## Komponentin lähteen tarkastelu {#viewing-the-source-of-a-component}

Voit jäljittää minkä tahansa komponentin takaisin sen Java-koodiin. Oletusarvoisesti lähde avautuu craftforJ:ssa vain luku -tilassa, sijoitettuna riville, joka loi komponentin. Voit määrittää craftforJ:n avaamaan sen sen sijaan editorissasi samalla rivillä. Kun komponenttia ei voi jäljittää riville, craftforJ ilmoittaa siitä sen sijaan, että avaisi tyhjää katselijaa.

![Lähteen katselija sijoitettuna riville, joka loi valitun komponentin](/img/craftforj/inspector/source-viewer.png#rounded-border)
