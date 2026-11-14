---
title: Teema
sidebar_position: 6
description: >-
  Adjust the DWC design tokens of a running webforJ app, preview the result
  immediately, and save it into your stylesheet.
_i18n_hash: fe089afe4f44e457a98622707bf995e5
---
Teema-välilehdellä voit muuttaa sovelluksesi ulkoasua sen ollessa käynnissä. Se toimii sovelluksesi jo käyttämien [DWC-suunnittelutunnisteiden](/docs/styling/css-variables) kanssa, joten yksittäinen muutos vaikuttaa kaikkiin kyseistä tunnistetta käyttäviin komponentteihin sen sijaan, että muuttaisit sääntöjä yksi kerrallaan.

<VideoPlayer video="craftforj/theme-knobs" width={1920} height={992} title="Theme knobs" />

## Teeman muokkaaminen {#adjusting-a-theme}

Säätimet on ryhmitelty niiden vaikutuksen mukaan. Niillä voi muokata sovelluksen väripalettia, taustapintoja, reunojen ja kulmien muotoa, typografiaa sekä välistyksiä. Kunkin säätimen toiminta selitetään, sillä jotkin säätimet vaikuttavat sovelluksen luettavuuteen eivätkä ainoastaan sen ulkoasuun.

Teemalla on vaalea ja tumma puoli. Muutoksen voi ottaa käyttöön molemmilla puolilla tai vain toisella, ja voit vaihtaa sovellusta vaalean ja tumman tilan välillä nähdäksesi, mitä puolta olet muokkaamassa. Esikatselussa näkyvät yhdessä väripaletti, taustapinnat, tekstinäyte ja tilavärit. Näin huomaat ennen tallentamista, jos yhdistelmä toimii yhdellä näytöllä mutta ei toisella.

![Teeman säätimet esikatselun vieressä](/img/craftforj/theme/knob-rail.png#rounded-border)

## Teeman tallentaminen {#saving-a-theme}

Muokkaamasi teema otetaan käyttöön sovelluksessa, mutta sitä ei vielä tallenneta osaksi projektiasi. Sivun lataaminen uudelleen hylkää muutokset. Tallentaminen kirjoittaa teeman sovelluksesi tyylitiedostoon, jossa se säilyy uudelleenkäynnistysten yli, näkyy diffissäsi ja toimitetaan sovelluksesi mukana.

craftforJ kirjoittaa yhteen tyylitiedostoon, jonka se tunnistaa automaattisesti tai jonka määrität craftforJ-asetuksissa. Jos tiedosto sisältää jo teeman, tallennettaessa se korvataan kokonaan eikä sen päälle lisätä toista teemaa. craftforJ pyytää sinua ensin vahvistamaan tämän. Jos tiedosto on muuttunut sen jälkeen, kun craftforJ luki sen, mitään ei kirjoiteta ja craftforJ pyytää tallentamaan uudelleen.

Voit palauttaa teeman viimeksi tallennettuun tilaan tai poistaa sen kokonaan tyylitiedostosta vaikuttamatta tiedoston muuhun sisältöön.

## Teeman esiasetukset {#preset-themes}

Oletusulkoasun lisäksi craftforJ sisältää useita teeman esiasetuksia, joista voit valita. Seuraavassa vertaillaan teemoja App Default ja Portico.

<Tabs>
  <TabItem value="app-default" label="App Default" default>
    ![Sovellus App Default -teemalla](/img/craftforj/theme/theme-app-default.png#rounded-border)
  </TabItem>
  <TabItem value="portico" label="Portico">
    ![Sovellus Portico-teemalla](/img/craftforj/theme/theme-portico.png#rounded-border)
  </TabItem>
</Tabs>

## Toiminnon poistaminen käytöstä {#turning-it-off}

Voit poistaa sovelluksen tyylien tallentamisen käytöstä craftforJ-asetuksissa tai poistaa sen kokonaan [`stylesheet-changes`](/docs/craftforj/configuration#feature-flags)-ominaisuudella. Kun jompikumpi näistä on poistettu käytöstä, välilehti toimii edelleen ja päivittää käynnissä olevan sovelluksen ulkoasun, mutta tulosta ei voi tallentaa.
