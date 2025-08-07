---
title: ProgressBar
sidebar_position: 90
_i18n_hash: 3c76010e8bda96d8694bffa5a260b851
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-progressbar" />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/progressbar/ProgressBar" top='true'/>

ProgressBar on komponentti, joka visuaalisesti näyttää jonkin tehtävän edistymistä. Kun tehtävä etenee kohti valmistumista, edistymispalkki näyttää tehtävän valmistumisprosentin. Tämä prosentti esitetään visuaalisesti suorakulmiona, joka alkaa tyhjistä ja täyttyy vähitellen, kun tehtävä etenee. Lisäksi edistymispalkki voi näyttää tekstimuotoisen esityksen tästä prosentista.

<ComponentDemo 
path='/webforj/progressbarbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarBasicView.java'
height='150px'
/>

## Käytöt {#usages}

`ProgressBar` -komponentti on hyödyllinen tehtävien valmistumisen tilan visualisoimiseksi. Se tukee:

- Säädettäviä vähimmäis- ja enimmäisarvoja.
- Määrittämätöntä tilaa jatkuville tehtäville, joilla ei ole selvää loppua.
- Vaihtoehtoja tekstin näkyvyydelle, animaatiolle ja raidallisiin muotoiluille paremman visuaalisen palautteen saamiseksi.

## Arvojen määrittäminen {#setting-values}

ProgressBar-komponentti mahdollistaa nykyisen arvon, vähimmäis- ja enimmäisrajojen asettamisen ja saamisen.

```java showLineNumbers
ProgressBar bar = new ProgressBar();
bar.setMin(0);
bar.setMax(100);
bar.setValue(50);
```

## Suuntaus {#orientation}

`ProgressBar` voidaan suuntaa vaakasuoraan tai pystysuoraan.

<ComponentDemo 
path='/webforj/progressbarorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarOrientationView.java'
height='175px'
/>

## Määrittämätön tila {#indeterminate-state}

`ProgressBar` tukee määrittämätöntä tilaa tehtäville, joiden valmistumisaikaa ei tiedetä.

<ComponentDemo 
path='/webforj/progressbardeterminate?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarDeterminateView.java'
height='25px'
/>

## Teksti ja tekstin näkyvyys {#text-and-text-visibility}

Oletuksena edistymispalkki näyttää valmiusprosentin muodossa `XX%` luotaessa. `setText()`-metodia käyttäen voit käyttää paikanninta `{{x}}` saadaksesi nykyisen arvon prosenttina. Lisäksi voit käyttää paikanninta `{{value}}` saadaksesi raakatason nykyarvon.

```java
ProgressBar bar = new ProgressBar(15, "Lataus: {{x}}%");
```

## Tyylittely {#styling}

### Teemat {#themes}

`ProgressBar` -komponentti sisältää <JavadocLink type="foundation" location="com/webforj/component/Theme"> teemoja </JavadocLink>, jotka on rakennettu nopeaa tyylittelyä varten ilman CSS:ää. Nämä teemat ovat ennaltamääritettyjä tyylejä, jotka voidaan soveltaa painikkeisiin niiden ulkonäön ja visuaalisen esityksen muuttamiseksi. Ne tarjoavat nopean ja johdonmukaisen tavan mukauttaa ProgressBarien ulkoasua sovelluksessa.

<ComponentDemo 
path='/webforj/progressbarthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarThemesView.java'
height='320px'
/>

<TableBuilder name="ProgressBar" />

## Parhaat käytännöt {#best-practices}

- **Käytä sopivia vähimmäis- ja enimmäisarvoja**: Aseta vähimmäis- ja enimmäisarvot tarkasti tehtävän alueen heijastamiseksi.
- **Päivitä edistymistä säännöllisesti**: Päivitä edistymisarvoa jatkuvasti tarjotaksesi reaaliaikaista palautetta käyttäjille.
- **Hyödynnä määrittämätöntä tilaa tuntemattomille kestolle**: Käytä määrittämätöntä tilaa tehtäville, joiden kestoa ei voida ennustaa, osoittaaksesi meneillään olevan edistyksen.
- **Näytä tekstiä paremman käyttäjäpalautteen saamiseksi**: Näytä teksti edistymispalkissa lisätiedon tarjoamiseksi tehtävän edistymisestä.
