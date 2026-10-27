---
title: Teema
sidebar_position: 6
description: >-
  Adjust the DWC design tokens of a running webforJ app, preview the result
  immediately, and save it into your stylesheet.
_i18n_hash: 98545075c2ac2777380812af08d71345
---
Teema-välilehti antaa sinun muuttaa sovelluksen ulkoasua sen käytön aikana. Se toimii jo käytössä olevien [DWC-suunnittelutunnusten](/docs/styling/css-variables) kanssa, joten yksi muutos vaikuttaa jokaiseen komponenttiin, joka lukee tuota tunnusta, sen sijaan että se vaikuttaisi yhteen sääntöön kerrallaan.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/theme-knobs.mp4" type="video/mp4" />
  </video>
</div>

## Teeman säätäminen {#adjusting-a-theme}

Säätöelementit on ryhmitelty sen mukaan, mihin ne vaikuttavat, kattaen sovelluksen perustan paletin, sen taustat, reunojen ja kulmien muodon, typografian ja välin. Jokainen säätöelementti selittää, mitä se tekee, sillä osa niistä vaikuttaa siihen, kuinka luettavaa sovellus on, ei vain sen ulkonäkoon.

Teemalla on vaalea ja tumma puoli. Voit soveltaa muutosta molempiin tai vain yhteen, ja vaihtaa sovellusta niiden välillä nähdäksesi, kummalla puolella työskentelet. Esikatselu näyttää paletin, taustat, typografianäytteen ja tila-värit yhdessä, jotta voit huomata yhdistelmän, joka toimii yhdellä näytöllä mutta ei toisella ennen kuin tallennat sen.

![Teeman säädöt esikatselun vieressä](/img/craftforj/theme/knob-rail.png#rounded-border)

## Teeman tallentaminen {#saving-a-theme}

Työn alla oleva teema on sovelluksessa käytössä, mutta se ei ole vielä osa projektiasi, ja sivun uudelleenlataaminen hylkää sen. Tallentaminen kirjoittaa sen sovelluksesi tyylitiedostoon, jossa se säilyy uudelleenkäynnistyksissä, näkyy muutoksissasi ja toimitetaan sovelluksesi mukana.

craftforJ kirjoittaa yhteen tyylitiedostoon, johon se itse havaitsee tai jonka nimeät craftforJ-asetuksissa. Jos kyseisessä tiedostossa on jo teema, tallentaminen korvataan kokonaisuudessaan sen sijaan, että siihen kerrostettaisiin toinen teema, ja craftforJ kysyy ensin vahvistusta. Jos tiedosto on muuttunut sen jälkeen, kun craftforJ on lukenut sen, mitään ei kirjoiteta, ja craftforJ pyytää sinua tallentamaan uudelleen.

Voit palauttaa teeman viimeiseen tallennettuun tilaan tai poistaa sen kokonaan tyylitiedostosta vaikuttamatta muuhun sisältöön tiedostossa.

## Esimuotoillut teemat {#preset-themes}

Olemassa olevan ulkoasun lisäksi craftforJ:llä on useita teemasuunnitelmia valittavaksi. Seuraavassa verrataan App Default -teemaa ja Portico-teemaa.

<Tabs>
  <TabItem value="app-default" label="App Default" default>
    ![Sovellus App Default -teemalla](/img/craftforj/theme/theme-app-default.png#rounded-border)
  </TabItem>
  <TabItem value="portico" label="Portico">
    ![Sovellus Portico-teemalla](/img/craftforj/theme/theme-portico.png#rounded-border)
  </TabItem>
</Tabs>

## Sen poistaminen käytöstä {#turning-it-off}

Voit kytkeä tyylien tallentamisen pois käytöstä craftforJ-asetuksissa tai poistaa sen kokonaan [`stylesheet-changes`](/docs/craftforj/configuration#feature-flags) ominaisuuden avulla. Molempien ollessa pois päältä, välilehti toimii silti ja värjää käynnissä olevaa sovellusta, mutta et voi tallentaa tulosta.
