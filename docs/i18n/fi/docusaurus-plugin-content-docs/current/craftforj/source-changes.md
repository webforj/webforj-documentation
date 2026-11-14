---
title: Muutosten soveltaminen
sidebar_position: 4
sidebar_class_name: new-content
description: >-
  Review the changes you made in craftforJ as a diff, choose where each one is
  written, and apply them to your Java source.
_i18n_hash: c421d594499c29fdae8dd88ca6e35e3d
---
Ominaisuuden muuttaminen craftforJ:ssä muuttaa käynnissä olevaa sovellusta eikä mitään muuta. Jos haluat säilyttää muutoksen, tarkista se ja kirjoita se Java-tiedostoon, josta se on peräisin. Tällä sivulla kuvataan tämä vaihe.

<VideoPlayer video="craftforj/apply-changes" width={1920} height={992} title="Apply changes" />

:::warning craftforJ kirjoittaa projektiisi
Pidä työsi versionhallinnassa. Lue erot ennen muutosten käyttöönottoa ja lue ne uudelleen ennen commitin tekemistä.
:::

## Odottavat muutokset {#pending-changes}

Jokainen muuttamasi ominaisuus kirjataan odottavaksi muutokseksi, ja craftforJ näyttää, montako muutosta odottaa käsittelyä. Odottavat muutokset säilyvät sivun uudelleenlatauksen ja reitin vaihdon jälkeen, koska craftforJ ottaa ne uudelleen käyttöön komponentteja rakennettaessa.

## Muutosten tarkistaminen ja käyttöönotto {#reviewing-and-applying}

Avaa tarkistus painamalla <kbd>Cmd/Ctrl</kbd> + <kbd>S</kbd>. Muutokset ryhmitellään tiedoston mukaan, johon ne kirjoitetaan. Kunkin muutoksen yhteydessä näkyy ominaisuus vanhoine ja uusine arvoineen, ja sen voi avata tiedoston erojen tarkastelua varten. Jos muutos korvaisi lasketun arvon kiinteällä arvolla, craftforJ varoittaa siitä ja kertoo, minkä lausekkeen se aikoo korvata. Mitään ei kirjoiteta ennen kuin otat muutokset käyttöön. Sitä ennen voit palauttaa tai hylätä jokaisen muutoksen erikseen.

![Tarkistusnäkymä, jossa muutokset on ryhmitelty tiedostoittain ja yhden muutoksen erot on avattu](/img/craftforj/source-changes/review.png#rounded-border)

## Muutoksen kirjoituskohdan valitseminen {#choosing-where-a-change-is-written}

Muutoksen kirjoituskohta määrittää, miten laajalle se vaikuttaa. Kun komponentti rakennetaan suoraan näkymässä, muutos kirjoitetaan kyseiseen näkymään. Kun komponentti rakennetaan uudelleenkäytettävän luokan sisällä, sinulla on kaksi vaihtoehtoa:

- **Käyttökohta** – kohta, jossa komponenttia käytetään. Tämä muuttaa vain edessäsi olevaa näkymää. Tämä on oletusvaihtoehto.
- **Määrittely** – kohta, jossa komponentti rakennetaan. Tämä muuttaa kaikkia näkymiä, joissa komponenttia käytetään.

Jokaisen odottavan muutoksen yhteydessä näkyy, kumpaa vaihtoehtoa se koskee, ja voit vaihtaa niiden välillä. Jotkin ominaisuudet voidaan kirjoittaa vain määrittelyyn, koska komponentti asettaa ne itse sen sijaan, että ottaisi ne vastaan kutsujalta. craftforJ merkitsee tällaiset ominaisuudet ennen muutosten käyttöönottoa.

## Käyttöönoton jälkeen {#after-you-apply}

Java-koodin kirjoittaminen käynnistää sovelluksen koontiversion luomisen ja uudelleenkäynnistyksen. craftforJ ilmoittaa uudelleenkäynnistyksestä, odottaa sen valmistumista ja muodostaa yhteyden uudelleen niin, että valintasi ja jäljellä olevat odottavat muutokset säilyvät. Käyttöönotetut muutokset poistuvat odottavien muutosten luettelosta, kun ne on kirjoitettu tiedostoihisi.

Tässä vaiheessa uudelleenlatauksen asetuksilla on merkitystä. craftforJ ei tarvitse live reload -toimintoa, sillä kaikki tarkastelun aikana tekemäsi muutokset tulevat voimaan käynnissä olevassa sovelluksessa heti ilman uudelleenkoontia. Lähdekoodiin kirjoittaminen on eri asia: se muuttaa tiedostoa, josta sovellus on koottu, joten sovellus on koottava uudelleen, ennen kuin muutos tulee voimaan koodistasi eikä craftforJ:stä. Jos [live reload](/docs/configuration/deploy-reload/overview) on määritetty, tämä tapahtuu automaattisesti. Muussa tapauksessa käynnistä sovellus uudelleen itse.

## Kumoa ja tee uudelleen <DocChip chip='since' label='26.03' /> {#undoing-and-redoing}

Kumoa viimeisin muutoksesi craftforJ:ssä painamalla <kbd>Cmd/Ctrl</kbd> + <kbd>Z</kbd> ja tee se uudelleen painamalla <kbd>Cmd/Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>Z</kbd>.

Kumoa-toiminto koskee käynnissä olevassa sovelluksessa tekemiäsi muutoksia [ominaisuuksien muokkauksista](/docs/craftforj/inspector#reading-and-changing-properties) [teeman säätöihin](/docs/craftforj/theme#adjusting-a-theme). Se toimii myös craftforJ:n projektiisi kirjoittamiin muutoksiin, kuten käyttöönotettuihin ominaisuusmuutoksiin, [tallennettuun teemaan](/docs/craftforj/theme#saving-a-theme) tai [lähdekoodista poistettuun komponenttiin](/docs/craftforj/inspector#removing-a-component). Kirjoitetun muutoksen kumoaminen palauttaa tiedoston aiempaan tilaansa. Jos kyseessä on Java-tiedosto, sovellus on koottava uudelleen ennen kuin muutos näkyy, kuten [muutosten käyttöönoton jälkeen](#after-you-apply).

<!-- TODO: add undo/redo recording -->

Kun kumoat useita kertoja, muutokset peruutetaan yksi kerrallaan, uusimmasta alkaen. Tee uudelleen toistaa muutokset siinä järjestyksessä kuin teit ne. Uuden muutoksen tekeminen kumoamisen jälkeen tyhjentää kaikki muutokset, jotka olisi voinut tehdä uudelleen.

### Kun kumoaminen estyy {#when-an-undo-is-blocked}

Jos muokkaat ja tallennat tiedoston omassa editorissasi sen jälkeen, kun craftforJ on kirjoittanut siihen, kyseisen vaiheen kumoaminen tai tekeminen uudelleen korvaisi muokkauksesi. craftforJ ei tee sitä, vaan jättää tiedoston ennalleen ja ilmoittaa sen nimen:

```
Muokattu craftforJ:n ulkopuolella: ContactFormView.java. Kumoa muokkaus editorissasi tai ohita tämä vaihe.
```

Voit jatkaa kahdella tavalla:

- Kumoa muokkauksesi editorissasi, jotta tiedosto vastaa craftforJ:n kirjoittamaa versiota, ja yritä sitten uudelleen.
- Valitse **Ohita**. Tiedosto jää ennalleen, ja kyseinen vaihe poistetaan historiasta. Ohittaminen ei palauta mitään, joten aiemmat samaan tiedostoon kohdistuneet vaiheet voivat yhä estyä.

## Toiminnon poistaminen käytöstä {#turning-it-off}

Voit poistaa Java-kirjoituksen käytöstä sovelluksessa craftforJ:n asetuksista tai poistaa sen kokonaan käytöstä [`source-changes`](/docs/craftforj/configuration#feature-flags)-ominaisuudella. Kumman tahansa ollessa pois käytöstä ominaisuuksien muokkaus toimii edelleen, mutta muutokset pysyvät vain käynnissä olevassa sovelluksessa.
