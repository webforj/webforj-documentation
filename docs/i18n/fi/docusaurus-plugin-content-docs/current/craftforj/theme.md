---
title: Theme
sidebar_position: 6
description: >-
  Adjust the DWC design tokens of a running webforJ app, preview the result
  immediately, and save it into your stylesheet.
_i18n_hash: c7dd349ebf7570b2e34953fce990173d
---
Teema-välilehti sallii sinulle vaihdon sovelluksesi ulkoa näyttäessäsi. Se toimii sovelluksesi jo käyttämien [DWC-suunnittelutunnusten](/docs/styling/css-variables) kanssa, joten yksi muutos saavuttaa jokaisen komponentin, joka lukee tuota tunnusta, sen sijaan että muokkaisit sääntöjä yksi kerrallaan.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/theme-knobs.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

## Teeman säätö {#adjusting-a-theme}

Ohjaimet on ryhmitelty sen mukaan, mitä ne vaikuttavat, kattaen sovelluksen rakennuspalletin, sen taustat, reunoja ja kulmia, typografian ja väliotsikot. Jokainen ohjain selittää, mitä se tekee, sillä jotkut niistä muuttavat sovelluksen luettavuutta ennemmin kuin vain ulkonäköä.

Teemalla on vaalea puoli ja tumma puoli. Voit soveltaa muokkausta molempiin tai vain yhteen, ja kääntää sovellusta niiden välillä nähdäksesi, mihin puoleen työskentelet. Esikatselu näyttää paletin, pinnat, tyypinäytteen ja tilavärit yhdessä, jotta voit havaita yhdistelmän, joka toimii yhdellä näytöllä mutta ei toisella, ennen kuin tallennat sen.

![Teeman ohjaimet esikatselun vieressä](/img/craftforj/theme/knob-rail.png#rounded-border)

## Teeman tallennus {#saving-a-theme}

Työstämäsi teema on sovelluksessa käytössä, mutta se ei vielä kuulu projektiisi, ja sivun uudelleenlataus hylkää sen. Tallentaminen kirjoittaa sen sovelluksesi tyyliarkistoon, missä se säilyy uudelleenkäynnistyksissä, näkyy erotuksissasi ja toimitetaan sovelluksesi mukana.

craftforJ kirjoittaa yhteen tyyliarkistoon, jonka se havaitsee itsenäisesti tai jonka nimeät craftforJ-asetuksissa. Jos tiedosto pitää jo teemaa, tallentaminen korvataan kokonaisuudessaan sen sijaan, että kerrostaisi toisen sen päälle, ja craftforJ pyytää sinua ensin vahvistamaan. Jos tiedostoa on muutettu craftforJ:n sen lukeman jälkeen, mitään ei kirjoiteta ja craftforJ pyytää sinua tallentamaan uudelleen.

Voit palauttaa teeman viimeiseen tallennettuun tilaan tai poistaa sen täysin tyyliarkistosta vaikuttamatta muihin tiedostossa.

## Esiasetetut teemat {#preset-themes}

Olemassa olevan ulkonäön ja tunteen lisäksi craftforJ:llä on useita teeman esiasetuksia valittavana. Seuraavassa on vertailu teemojen App Default ja Portico välillä.

<Tabs>
  <TabItem value="app-default" label="App Default" default>
    ![Sovellus App Default -teeman kanssa](/img/craftforj/theme/theme-app-default.png#rounded-border)
  </TabItem>
  <TabItem value="portico" label="Portico">
    ![Sovellus Portico-teeman kanssa](/img/craftforj/theme/theme-portico.png#rounded-border)
  </TabItem>
</Tabs>

## Sammuttaminen {#turning-it-off}

Voit sammuttaa tyylien tallentamisen sovellukselle craftforJ-asetuksissa tai poistaa sen kokonaan [`stylesheet-changes`](/docs/craftforj/configuration#feature-flags) ominaisuudella. Jommankumman ollessa pois päältä, välilehti toimii edelleen ja maalauksia ajavalle sovellukselle, mutta et voi tallentaa tulosta.
