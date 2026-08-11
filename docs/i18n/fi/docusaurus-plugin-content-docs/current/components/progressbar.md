---
title: ProgressBar
sidebar_position: 90
description: >-
  Visualize task completion with the ProgressBar component, supporting
  determinate and indeterminate modes, orientation, and themes.
_i18n_hash: 47c51276d2b1da6c6bef337f76403515
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-progressbar" />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/progressbar/ProgressBar" top='true'/>

`ProgressBar`-komponentti esittää visuaalisesti operaation valmistumisasteen. Kun työ etenee, suorakulmio täyttyy vähitellen heijastaakseen nykyistä prosenttiluku. Baarissa voi myös olla tekstiesitys sen arvosta, ja se tukee sekä määrätietoisia että määrättömiä tiloja tehtäville, joiden kestoa ei tiedetä.

<!-- INTRO_END -->

## Käyttöönotot {#usages}

`ProgressBar`-komponentti on hyödyllinen tehtävien valmistumisasteen visualisoimisessa. Se tukee:

- Määritettäviä vähimmäis- ja enimmäisarvoja.
- Määrättömän tilan käyttöä jatkuville tehtäville, joilla ei ole selvää loppua.
- Mahdollisuuksia tekstin näkyvyyteen, animaatioon ja raidallisiin muotoiluihin paremman visuaalisen palautteen saamiseksi.

Seuraavassa esimerkissä on raidallinen, animoitu edistymispalkki, jossa on alku-, tauko- ja nollausohjaimet:

<ComponentDemo
path='/webforj/progressbarbasic'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarBasicView.java']}
height='150px'
/>

## Arvojen asettaminen {#setting-values}

ProgressBar-komponentti mahdollistaa sen nykyisen arvon, minimirajan ja enimmäisrajan asettamisen ja hakemisen.

```java showLineNumbers
ProgressBar bar = new ProgressBar();
bar.setMin(0);
bar.setMax(100);
bar.setValue(50);
```

## Suunta {#orientation}

`ProgressBar` voidaan asettaa vaakasuoraan tai pystysuoraan.

<ComponentDemo
path='/webforj/progressbarorientation'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarOrientationView.java']}
height='175px'
/>

## Määrättömässä tilassa {#indeterminate-state}

`ProgressBar` tukee määrätöntä tilaa tehtäville, joiden valmistumisaikaa ei tiedetä.

<ComponentDemo
path='/webforj/progressbardeterminate'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarDeterminateView.java']}
height='25px'
/>

## Teksti ja tekstin näkyvyys {#text-and-text-visibility}

Oletuksena, kun progressiivinen palkki luodaan, se näyttää prosenttiprosentin muodossa `XX%`. Käyttämällä `setText()`-metodia voit käyttää täytemerkkiä `{{x}}` saadaksesi nykyisen arvon prosentteina. Lisäksi voit käyttää täytemerkkiä `{{value}}` saadaksesi raakavälin nykyisen arvon.

```java
ProgressBar bar = new ProgressBar(15, "Ladataan: {{x}}%");
```

## Tyylittely {#styling}

### Teemat {#themes}

`ProgressBar`-komponentti tulee sisäänrakennettujen <JavadocLink type="foundation" location="com/webforj/component/Theme"> teemojen </JavadocLink> kanssa nopeaa tyylittelyä varten ilman CSS:n käyttöä. Nämä teemat ovat määritettyjä tyylejä, joita voidaan soveltaa painikkeisiin niiden ulkonäön ja visuaalisen esityksen muuttamiseksi.
Ne tarjoavat nopean ja johdonmukaisen tavan mukauttaa ProgressBarien ilmettä sovelluksessa.

<ComponentDemo
path='/webforj/progressbarthemes'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarThemesView.java']}
height='320px'
/>

<TableBuilder name="ProgressBar" />

## Parhaat käytännöt {#best-practices}

- **Käytä sopivia vähimmäis- ja enimmäisarvoja**: Aseta vähimmäis- ja enimmäisarvot tarkasti heijastamaan tehtävän arvoaluetta.
- **Päivitä edistystä säännöllisesti**: Päivitä jatkuvasti edistymisarvo käyttäjille reaaliaikaisen palautteen tarjoamiseksi.
- **Hyödynnä määrättömiä tiloja tuntemattomille kestolle**: Käytä määrätöntä tilaa ennakoimattomille kestolle, jotta voidaan indikoida jatkuvaa edistymistä.
- **Näytä tekstiä paremman käyttäjäpalautteen tarjoamiseksi**: Näytä teksti edistymispalkissa tarjotaksesi lisäkontekstia tehtävän edistymisestä.
