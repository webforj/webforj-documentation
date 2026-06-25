---
title: ProgressBar
sidebar_position: 90
_i18n_hash: 6acac582ce905eb255ee09e499fd561f
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-progressbar" />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/progressbar/ProgressBar" top='true'/>

`ProgressBar` komponentti esittää visuaalisesti toiminnan valmistumisen tilan. Työn edetessä suorakulmio täyttyy vähitellen heijastamaan nykyistä prosenttia. Baarissa voidaan myös näyttää tekstimuotoinen esitys sen arvosta, ja se tukee sekä määritettyjä että määrittämättömiä tiloja tunnetuilla tai tuntemattomilla kestolla.

<!-- INTRO_END -->

## Käytöt {#usages}

`ProgressBar` komponentti on hyödyllinen tehtävien valmistumisen tilan visualisoimiseksi. Se tukee:

- Konfiguroitavia vähimmäis- ja enimmäisarvoja.
- Määrittämätöntä tilaa jatkuville tehtäville, joilla ei ole tarkkaa loppua.
- Vaihtoehtoja tekstin näkyvyyteen, animaatioon ja raidoitettuihin suunnitteluihin paremman visuaalisen palautteen saamiseksi.

Seuraava esimerkki näyttää raidallisen, animaatiolla varustetun edistymispalkin, jossa on käynnistys-, tauko- ja nollausohjaimet:

<ComponentDemo
path='/webforj/progressbarbasic'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarBasicView.java']}
height='150px'
/>

## Arvojen asettaminen {#setting-values}

ProgressBar komponentti sallii nykyisen arvonsa, vähimmäis- ja enimmäisrajat asettamisen ja saamisen.

```java showLineNumbers
ProgressBar bar = new ProgressBar();
bar.setMin(0);
bar.setMax(100);
bar.setValue(50);
```

## Suunta {#orientation}

`ProgressBar` voidaan suunnata vaakasuoraan tai pystysuoraan.

<ComponentDemo
path='/webforj/progressbarorientation'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarOrientationView.java']}
height='175px'
/>

## Määrittämätön tila {#indeterminate-state}

`ProgressBar` tukee määrittämätöntä tilaa tehtäville, joiden valmistumisaika on tuntematon.

<ComponentDemo
path='/webforj/progressbardeterminate'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarDeterminateView.java']}
height='25px'
/>

## Teksti ja tekstin näkyvyys {#text-and-text-visibility}

Oletuksena, kun progress bar luodaan, se näyttää prosenttiosuuden valmistumisesta muodossa `XX%`. Käyttämällä `setText()` menetelmää voit käyttää paikkamerkkiä `{{x}}` saadaksesi nykyisen arvon prosentteina. Lisäksi voit käyttää paikkamerkkiä 
`{{value}}` saadaksesi raakamuotoisen nykyarvon.

```java
ProgressBar bar = new ProgressBar(15, "Lataus: {{x}}%");
```

## Tyylittely {#styling}

### Teemat {#themes}

`ProgressBar` komponentti tulee mukana <JavadocLink type="foundation" location="com/webforj/component/Theme"> teemoja </JavadocLink>, jotka on sisällytetty nopeaan tyylittelyyn ilman CSS:ää. Nämä teemat ovat ennalta määritettyjä tyylejä, joita voidaan soveltaa painikkeisiin niiden ulkoasun ja visuaalisen esityksen muuttamiseksi. 
Ne tarjoavat nopean ja johdonmukaisen tavan mukauttaa ProgressBarien ulkoasua sovelluksessa.

<ComponentDemo
path='/webforj/progressbarthemes'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarThemesView.java']}
height='320px'
/>

<TableBuilder name="ProgressBar" />

## Parhaat käytännöt {#best-practices}

- **Käytä sopivia vähimmäis- ja enimmäisarvoja**: Aseta vähimmäis- ja enimmäisarvot tarkasti tehtävän alueen heijastamiseksi.
- **Päivitä edistystä säännöllisesti**: Päivitä jatkuvasti edistyksen arvoa antaaksesi käyttäjille reaaliaikaista palautetta.
- **Hyödynnä määrittämätöntä tilaa tuntemattomille kestolle**: Käytä määrittämätöntä tilaa tehtäville, joilla on arvaamaton kesto, osoittaaksesi jatkuvaa edistystä.
- **Näytä tekstiä paremman käyttäjäpalautteen saamiseksi**: Näytä tekstiä edistymispalkissa tarjotaksesi lisäkonseptia tehtävän edistymisestä.
