---
title: Muutosten käyttöönotto
sidebar_position: 4
sidebar_class_name: new-content
description: >-
  Review the changes you made in craftforJ as a diff, choose where each one is
  written, and apply them to your Java source.
_i18n_hash: de9ef40848946a35567c308c132dce0d
---
craftforJ-sovelluksessa ominaisuuden muuttaminen muuttaa käynnissä olevaa sovellusta eikä mitään muuta. Jos haluat säilyttää muutoksen, tarkista se ja kirjoita se takaisin Java-tiedostoon, josta se on peräisin. Tällä sivulla kuvataan tämä vaihe.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/apply-changes.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

:::warning craftforJ kirjoittaa projektiisi
Pidä työsi versionhallinnassa. Lue muutokset ennen niiden käyttöönottoa ja lue ne uudelleen ennen commitin tekemistä.
:::

## Odottavat muutokset {#pending-changes}

Jokainen muuttamasi ominaisuus kirjataan odottavaksi muutokseksi, ja craftforJ näyttää, kuinka monta muutosta odottaa käsittelyä. Odottavat muutokset säilyvät sivun uudelleenlatauksen ja reitinvaihdon yli, koska craftforJ ottaa ne uudelleen käyttöön, kun komponentit luodaan uudelleen.

## Muutosten tarkastelu ja käyttöönotto {#reviewing-and-applying}

Avaa tarkastelu painamalla <kbd>Cmd/Ctrl</kbd> + <kbd>S</kbd>. Muutokset ryhmitellään tiedoston mukaan, johon ne kirjoitetaan. Jokaisessa näkyy ominaisuus vanhoine ja uusine arvoineen, ja sen voi avata tiedoston muutosten tarkastelua varten. Jos muutos korvaisi lasketun arvon kiinteällä arvolla, craftforJ varoittaa siitä ja näyttää korvattavan lausekkeen. Mitään ei kirjoiteta ennen kuin otat muutokset käyttöön. Sitä ennen voit palauttaa tai hylätä kunkin muutoksen erikseen.

![Tarkastelu, jossa muutokset on ryhmitelty tiedostoittain ja yhden muutoksen erotus on avattu](/img/craftforj/source-changes/review.png#rounded-border)

## Muutoksen kirjoituskohteen valitseminen {#choosing-where-a-change-is-written}

Muutoksen kirjoituskohde määrittää, kuinka laajalle sen vaikutus ulottuu. Kun komponentti luodaan suoraan näkymässä, muutos kirjoitetaan kyseiseen näkymään. Kun komponentti luodaan uudelleenkäytettävän luokan sisällä, voit valita kahdesta vaihtoehdosta:

- **Käyttökohta** – paikka, jossa komponenttia käytetään. Muutos vaikuttaa vain edessäsi olevaan näkymään. Tämä on oletusvalinta.
- **Määritelmä** – paikka, jossa komponentti luodaan. Muutos vaikuttaa kaikkiin näkymiin, joissa sitä käytetään.

Kunkin odottavan muutoksen kohdalla näkyy, kumpaa vaihtoehtoa se koskee, ja voit vaihtaa vaihtoehtoa. Jotkin ominaisuudet voidaan kirjoittaa vain määritelmään, koska komponentti asettaa ne itse sen sijaan, että se vastaanottaisi ne kutsujalta. craftforJ merkitsee nämä ennen muutosten käyttöönottoa.

## Käyttöönoton jälkeen {#after-you-apply}

Java-koodiin kirjoittaminen käynnistää sovelluksen uudelleenrakennuksen ja uudelleenkäynnistyksen. craftforJ ilmoittaa uudelleenkäynnistyksestä, odottaa sen valmistumista ja muodostaa yhteyden uudelleen valintasi ja jäljellä olevien odottavien muutosten säilyessä ennallaan. Käyttöönotetut muutokset poistuvat odottavien muutosten luettelosta, kun ne on kirjoitettu tiedostoihisi.

Vain tässä vaiheessa uudelleenlatausasetuksillasi on merkitystä. craftforJ ei tarvitse live reload -toimintoa, sillä kaikki tarkastelun aikana tekemäsi muutokset tulevat voimaan käynnissä olevassa sovelluksessa heti ilman uudelleenrakennusta. Lähdekoodiin kirjoittaminen on eri asia: se muuttaa tiedostoa, josta sovellus on rakennettu, joten sovellus on rakennettava uudelleen, jotta muutos tulee koodistasi eikä craftforJ:stä. Kun [live reload](/docs/configuration/deploy-reload/overview) on määritetty, tämä tapahtuu automaattisesti. Muussa tapauksessa käynnistä sovellus itse uudelleen.

## Kumoa ja tee uudelleen <DocChip chip='since' label='26.03' /> {#undoing-and-redoing}

Kumoa viimeisin muutos craftforJ:ssä painamalla <kbd>Cmd/Ctrl</kbd> + <kbd>Z</kbd>. Tee muutos uudelleen painamalla <kbd>Cmd/Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>Z</kbd>.

Kumoa-toiminto koskee käynnissä olevassa sovelluksessa tekemiäsi muutoksia [ominaisuuksien muokkauksista](/docs/craftforj/inspector#reading-and-changing-properties) [teeman säätämiseen](/docs/craftforj/theme#adjusting-a-theme). Se koskee myös muutoksia, jotka craftforJ on kirjoittanut projektiisi, kuten käyttöönotettuja ominaisuusmuutoksia, [tallennettua teemaa](/docs/craftforj/theme#saving-a-theme) tai [lähdekoodista poistettua komponenttia](/docs/craftforj/inspector#removing-a-component). Kirjoitetun muutoksen kumoaminen palauttaa tiedoston aiempaan tilaansa. Jos kyseessä on Java-tiedosto, sovellus on rakennettava uudelleen ennen kuin muutos näkyy, kuten [käyttöönoton jälkeenkin](#after-you-apply).

<!-- TODO: add undo/redo recording -->

Kun kumoat useita kertoja, muutokset palautetaan yksi kerrallaan uusimmasta alkaen. Tee uudelleen -toiminto toistaa muutokset siinä järjestyksessä kuin teit ne. Jos teet uuden muutoksen kumoamisen jälkeen, kaikkia uudelleen tehtävissä olleita muutoksia ei enää voi palauttaa.

### Kun muutoksen kumoaminen estyy {#when-an-undo-is-blocked}

Jos muokkaat tiedostoa omassa editorissasi ja tallennat sen sen jälkeen, kun craftforJ on kirjoittanut siihen, kyseisen vaiheen kumoaminen tai uudelleen tekeminen korvaisi muokkauksesi. craftforJ kieltäytyy toiminnosta, jättää tiedoston ennalleen ja ilmoittaa tiedoston nimen:

```
Muokattu craftforJ:n ulkopuolella: ContactFormView.java. Kumoa muokkaus editorissasi tai ohita tämä vaihe.
```

Voit jatkaa kahdella tavalla:

- Kumoa muokkauksesi editorissasi, jotta tiedosto vastaa craftforJ:n kirjoittamaa versiota, ja yritä sitten uudelleen.
- Valitse **Ohita**. Tiedosto jää ennalleen ja kyseinen vaihe poistetaan historiasta. Ohittaminen ei palauta mitään, joten samaan tiedostoon vaikuttaneet aiemmat vaiheet voivat edelleen estyä.

## Poistaminen käytöstä {#turning-it-off}

Voit poistaa Java-kirjoitukset käytöstä sovelluksessa craftforJ:n asetuksista tai poistaa ominaisuuden kokonaan [`source-changes`](/docs/craftforj/configuration#feature-flags)-ominaisuudella. Kun kumpi tahansa on poistettu käytöstä, ominaisuuksia voi edelleen muokata, mutta muutokset näkyvät vain käynnissä olevassa sovelluksessa.
