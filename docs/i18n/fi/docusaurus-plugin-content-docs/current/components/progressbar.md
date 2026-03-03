---
title: ProgressBar
sidebar_position: 90
_i18n_hash: 7612411ef90d5344a2bab79b7e221141
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-progressbar" />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/progressbar/ProgressBar" top='true'/>

`ProgressBar`-komponentti kertoo visuaalisesti toiminnan valmistumistilan. Kun työ etenee, suorakulmio täyttyy vähitellen heijastamaan nykyistä prosenttiosuutta. Baarissa voi myös näyttää tekstimuotoisen arvon, ja se tukee sekä määrättyjä että määräämättömiä tiloja tehtäville, joiden kesto on tunnettu tai tuntematon.

<!-- INTRO_END -->

## Käyttökohteet {#usages}

`ProgressBar`-komponentti on hyödyllinen tehtävien valmistumistilan visualisoimiseksi. Se tukee:

- Määritettävät minimija maksimiväli.
- Määräämätön tila jatkuville tehtäville, joilla ei ole selkeää loppua.
- Vaihtoehtoja tekstin näkyvyyteen, animaatioihin ja raidallisiin muotoiluihin paremman visuaalisen palautteen saamiseksi.

Seuraavassa esimerkissä on raidallinen, animaatiota sisältävä progress bar, jossa on aloita, keskeytä ja nollaustilat:

<ComponentDemo 
path='/webforj/progressbarbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarBasicView.java'
height='150px'
/>

## Arvojen asettaminen {#setting-values}

ProgressBar-komponentti mahdollistaa sen nykyarvon, minimirajan ja maksimirajan asettamisen ja hakemisen.

```java showLineNumbers
ProgressBar bar = new ProgressBar();
bar.setMin(0);
bar.setMax(100);
bar.setValue(50);
```

## Suunta {#orientation}

`ProgressBar` voidaan asettaa vaakasuoraan tai pystysuoraan.

<ComponentDemo 
path='/webforj/progressbarorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarOrientationView.java'
height='175px'
/>

## Määräämätön tila {#indeterminate-state}

`ProgressBar` tukee määräämätöntä tilaa tehtäville, joiden valmistumisaika on tuntematon.

<ComponentDemo 
path='/webforj/progressbardeterminate?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarDeterminateView.java'
height='25px'
/>

## Teksti ja tekstin näkyvyys {#text-and-text-visibility}

Oletuksena luotaessa progress bar näyttää prosenttiosuuden muodossa `XX%`. Käyttämällä `setText()`-metodia voit käyttää paikkamerkkiä `{{x}}` saadaksesi nykyarvon prosentteina. Lisäksi voit käyttää paikkamerkkiä 
`{{value}}` saadaksesi raakaan nykyarvoon.

```java
ProgressBar bar = new ProgressBar(15, "Lataaminen: {{x}}%");
```

## Tyylit {#styling}

### Teemat {#themes}

`ProgressBar`-komponentissa on sisäänrakennettuna <JavadocLink type="foundation" location="com/webforj/component/Theme"> teemoja </JavadocLink> nopeaa tyylittelyä varten ilman CSS:n käyttöä. Nämä teemat ovat ennalta määriteltyjä tyylejä, joita voidaan soveltaa painikkeisiin niiden ulkoasun ja visuaalisen esityksen muuttamiseksi. 
Ne tarjoavat nopean ja johdonmukaisen tavan mukauttaa ProgressBarien ulkoasua koko sovelluksessa. 

<ComponentDemo 
path='/webforj/progressbarthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarThemesView.java'
height='320px'
/>

<TableBuilder name="ProgressBar" />

## Parhaat käytännöt {#best-practices}

- **Käytä Sopivia Minimi- ja Maksimiväliä**: Aseta minimija maksimiväli tehtävän alueen tarkkaa heijastamista varten.
- **Päivitä Edistystä Säännöllisesti**: Päivitä jatkuvasti edistymisarvo tarjotaksesi käyttäjille reaaliaikaista palautetta.
- **Hyödynnä Määräämätöntä Tilaa Tuntemattomille Kestoille**: Käytä määräämätöntä tilaa tehtäville, joiden kesto on arvaamaton, osoittaaksesi jatkuvaa edistymistä.
- **Näytä Teksti Parempaa Käyttäjäpalautetta Varten**: Näytä teksti progress barissa tarjotaksesi lisäkontekstia tehtävän edistyksestä.
