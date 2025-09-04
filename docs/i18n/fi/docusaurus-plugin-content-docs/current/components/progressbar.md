---
title: ProgressBar
sidebar_position: 90
_i18n_hash: 9b2f9ec23124d60ab5f8fca18e561acb
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-progressbar" />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/progressbar/ProgressBar" top='true'/>

ProgressBar on komponentti, joka näyttää visuaalisesti jonkin tehtävän edistymisen. Kun tehtävä etenee kohti valmistumista, edistymispalkki näyttää tehtävän valmistumisprosentin. Tämä prosenttiosuus esitetään visuaalisesti suorakulmiona, joka alkaa tyhjänä ja täyttyy vähitellen, kun tehtävä etenee. Lisäksi edistymispalkki voi näyttää tekstimuotoisen esityksen tästä prosentista.

<ComponentDemo 
path='/webforj/progressbarbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarBasicView.java'
height='150px'
/>

## Käyttötavat {#usages}

`ProgressBar`-komponentti on hyödyllinen tehtävien valmistumisasteen visualisoimiseksi. Se tukee:

- Konfiguroitavia vähimmäis- ja enimmäisarvoja.
- Määrittelemätöntä tilaa jatkuville tehtäville, joilla ei ole määritellyt loppua.
- Mahdollisuuksia tekstin näkyvyydelle, animaatiolle ja raidallisille muotoiluille paremman visuaalisen palautteen saamiseksi.

## Arvojen asettaminen {#setting-values}

ProgressBar-komponentilla voi asettaa ja saada sen nykyisen arvon, vähimmäis- ja enimmäisrajat.

```java showLineNumbers
ProgressBar bar = new ProgressBar();
bar.setMin(0);
bar.setMax(100);
bar.setValue(50);
```

## Suuntaus {#orientation}

`ProgressBar` voidaan suunnata vaakasuoraan tai pystysuoraan.

<ComponentDemo 
path='/webforj/progressbarorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarOrientationView.java'
height='175px'
/>

## Määrittelemätön tila {#indeterminate-state}

`ProgressBar` tukee määrittelemätöntä tilaa tehtäville, joiden valmistumisaikaa ei tiedetä.

<ComponentDemo 
path='/webforj/progressbardeterminate?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarDeterminateView.java'
height='25px'
/>

## Teksti ja tekstin näkyvyys {#text-and-text-visibility}

Oletuksena, kun se luodaan, edistymispalkki näyttää prosenttiosuuden valmiina muodossa `XX%`. Käyttämällä `setText()`-metodia voit käyttää paikkaa `{{x}}` saadaksesi nykyisen arvon prosentteina. Lisäksi voit käyttää paikkaa `{{value}}` saadaksesi raakamuotoisen nykyarvon.

```java
ProgressBar bar = new ProgressBar(15, "Lataus: {{x}}%");
```

## Tyylit {#styling}

### Teemat {#themes}

`ProgressBar`-komponentti sisältää <JavadocLink type="foundation" location="com/webforj/component/Theme"> teemoja </JavadocLink>, jotka on rakennettu nopeaa tyylittelyä varten ilman CSS:n käyttöä. Nämä teemat ovat ennalta määriteltyjä tyylejä, joita voidaan soveltaa painikkeisiin niiden ulkonäön ja visuaalisen esityksen muuttamiseksi. 
Ne tarjoavat nopean ja johdonmukaisen tavan mukauttaa ProgressBarien ulkoasua sovelluksessa.

<ComponentDemo 
path='/webforj/progressbarthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarThemesView.java'
height='320px'
/>

<TableBuilder name="ProgressBar" />

## Parhaat käytännöt {#best-practices}

- **Käytä asianmukaisia vähimmäis- ja enimmäisarvoja**: Aseta vähimmäis- ja enimmäisarvot tarkasti heijastamaan tehtävän rajaa.
- **Päivitä edistymistä säännöllisesti**: Jatkuvasti päivitä edistymisarvoa tarjotaksesi reaaliaikaista palautetta käyttäjille.
- **Hyödynnä määrittelemätöntä tilaa tuntemattomille kestolle**: Käytä määrittelemätöntä tilaa tehtäville, joiden kesto on ennakoimaton, osoittaaksesi jatkuvaa edistymistä.
- **Näytä tekstiä parempaa käyttäjäpalautetta varten**: Näytä teksti edistymispalkissa tarjotaksesi lisäkontekstia tehtävän edistymiselle.
