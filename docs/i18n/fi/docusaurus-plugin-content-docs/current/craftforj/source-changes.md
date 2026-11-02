---
title: Kirjoittaminen Lähteeseen
sidebar_position: 4
description: >-
  Review the changes you made in craftforJ as a diff, choose where each one is
  written, and apply them to your Java source.
_i18n_hash: dd96407889e9e9fa1de4af299809dcad
---
Muuttamalla ominaisuutta craftforJ:ssä muutat toimintoa sovelluksessa eikä mitään muuta. Muutoksen säilyttämiseksi tarkistat sen ja kirjoitat sen takaisin alkuperäiseen Java-tiedostoon. Tämä sivu kuvaa tämän vaiheen.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/apply-changes.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

:::warning craftforJ kirjoittaa projektiisi
Pidä työsi versionhallinnassa. Lue muutos ennen sen soveltamista ja lue se uudelleen ennen sitoutumista.
:::

## Odottavat muutokset {#pending-changes}

Jokainen muuttamasi ominaisuus tallennetaan odottavaksi muutokseksi, ja craftforJ näyttää, kuinka monta on odottamassa. Odottavat muutokset säilyvät sivun uudelleenlatauksessa ja reitin muutoksessa, koska craftforJ soveltaa ne uudelleen, kun komponenttisi rakennetaan.

## Tarkistaminen ja soveltaminen {#reviewing-and-applying}

Paina <kbd>Cmd/Ctrl</kbd> + <kbd>S</kbd> avataksesi tarkastelun. Muutokset on ryhmitelty sen tiedoston mukaan, johon ne menevät. Jokainen niistä näyttää ominaisuuden vanhan ja uuden arvon, ja laajenee tiedoston diffiin. Jos muutos korvaisi lasketun arvon kiinteällä, craftforJ varoittaa sinua ja nimeää ilmaisun, jonka se on juuri korvaamassa. Mikään ei tallenneta ennen kuin sovellat sen. Ennen soveltamista voit peruuttaa tai hylätä jokaisen muutoksen erikseen.

![Tarkastelu, jossa muutokset ryhmitellään tiedoston mukaan ja yksi on laajennettu sen diffiin](/img/craftforj/source-changes/review.png#rounded-border)

## Muutoksen kirjoituspaikan valitseminen {#choosing-where-a-change-is-written}

Se, mihin muutosta kirjoitetaan, määrää, kuinka pitkälle se ulottuu. Kun komponentti rakennetaan suoraan näkymässä, muutos menee siihen näkymään. Kun se rakennetaan uudelleenkäytettävän luokan sisälle, sinulla on kaksi vaihtoehtoa:

- **Käyttö** - paikka, jossa komponenttia käytetään, mikä muuttaa vain sen näytön, joka on edessäsi. Tämä on oletus.
- **Määrittely** - paikka, jossa komponentti rakennetaan, mikä muuttaa jokaista näyttöä, joka käyttää sitä.

Jokainen odottava muutos näyttää, mikä kahdesta pätee ja antaa sinun vaihtaa niiden välillä. Jotkin ominaisuudet voidaan kirjoittaa vain määrittelyssä, koska komponentti asettaa ne itse sen sijaan, että vastaanottaisi ne kutsujalta. craftforJ merkitsee nämä ennen soveltamista.

## Sen jälkeen kun olet soveltanut {#after-you-apply}

Javaan kirjoittaminen aiheuttaa sovelluksen uudelleenrakentamisen ja uudelleenkäynnistyksen. craftforJ raportoi uudelleenkäynnistyksen, odottaa sitä ja yhdistää valintasi ja jäljellä olevat odottavat muutoksesi ehjinä. Sovelletut muutokset poistuvat odottavalta listalta, kun ne ovat tiedostoissasi.

Tämä on ainoa hetki, jolloin uudelleenlatausasetuksesi on merkityksellinen. craftforJ ei tarvitse live-reloadia toimiakseen, koska kaikki, mitä muutat tarkistettaessa, vaikuttaa heti käynnissä olevaan sovellukseen ilman uudelleenrakentamista. Tiedostoon kirjoittaminen on erilaista: se muuttaa tiedostoa, josta sovelluksesi rakennettiin, joten sovelluksen on rakennettava uudelleen, ennen kuin muutos tulee koodistasi eikä craftforJ:stä. Kun [live-reload](/docs/configuration/deploy-reload/overview) on määritetty, se tapahtuu automaattisesti. Ilman sitä käynnistä sovellus itse uudelleen.

## Sammuttaminen {#turning-it-off}

Voit kytkeä kirjoittamisen Javaan pois päältä craftforJ-asetuksissa tai poistaa sen kokonaan käytöstä [`source-changes`](/docs/craftforj/configuration#feature-flags) ominaisuuden avulla. Molempien ollessa pois päältä, ominaisuuden muokkaaminen toimii edelleen, mutta pysyy live-tilassa.
