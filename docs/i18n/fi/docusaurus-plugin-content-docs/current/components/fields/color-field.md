---
sidebar_position: 5
title: ColorField
slug: colorfield
description: >-
  A component that provides a default browser-based color picker, allowing users
  to select a color from an input field.
_i18n_hash: 27d7acb036714332e6ad5c5af2c5e684
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-color-chooser" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/ColorField" top='true'/>

<ParentLink parent="Field" />

`ColorField`-komponentti on monipuolinen työkalu, joka antaa käyttäjille mahdollisuuden tutkia ja valita värejä vuorovaikutteisesti sovelluksessasi. Se tarjoaa saumatonta lähestymistapaa, jotta käyttäjät voivat löytää täydellisen sävyn, kylläisyyden ja kirkkauden, jotka vastaavat heidän luovaa visiota.

`ColorField`-komponentti on toteutettu natiivina selaimen ominaisuutena, joten esitystapa voi vaihdella suuresti selaimen ja alustan mukaan. Kuitenkin tämä vaihtelu on hyödyllistä, sillä se vastaa käyttäjän tutulle ympäristölle. Se voi näkyä yksinkertaisena tekstikenttä, jossa varmistetaan oikein muotoiltu värin arvo, alustastandardina väri-valitsijana tai jopa räätälöitynä väri-valitsijakäyttöliittymänä.

<ComponentDemo 
path='/webforj/colorfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/colorfield/ColorFieldView.java'
cssURL='/css/fields/colorfield/colorFieldDemo.css'
height='300px'
/>

## Käytöt {#usages}

`ColorField` on parasta käyttää tilanteissa, joissa värin valinta on olennainen osa käyttäjäliittymää tai sovelluksen käyttöliittymää. Tässä on joitakin tilanteita, joissa voit käyttää `ColorField`-komponenttia tehokkaasti:

1. **Graafinen Suunnittelu ja Kuvankäsittelytyökalut**: Värikentät ovat välttämättömiä sovelluksissa, jotka sisältävät muokkausta värin valinnan avulla.

2. **Teeman Mukauttaminen**: Jos sovelluksesi sallii käyttäjien mukauttaa teemoja, värikentän käyttö mahdollistaa heidän valita värejä eri käyttöliittymäelementeille, kuten taustoille, tekstille, painikkeille jne.

3. **Datan Visualisointi**: Tarjoa käyttäjille värikenttä, jolla he voivat valita värejä kaavioille, grafiikoille, lämpökartoille ja muille visuaalisille esitysmuodoille.

## Arvo {#value}

`ColorField` käyttää [`java.awt.Color`](https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/Color.html) -luokkaa värien asettamiseen ja hakemiseen `setValue()` ja `getValue()` -menetelmien kautta. Vaikka asiakaspuolen komponentti käsittelee yksinomaan täysin läpinäkyviä RGB-värejä heksadesimaalimuodossa, webforJ sujuvoittaa prosessia automaattisesti muuntamalla `Color`-arvot oikeaan muotoon.

:::tip Heksadesimaaliparsinta
Kun käytät `setText()`-menetelmää arvon asettamiseen, `ColorField` yrittää jäsentää syötteen heksadesimaali värinä. Jos jäsennys epäonnistuu, heitetään `IllegalArgumentException`.
:::

## Staattiset työkalut {#static-utilities}

`ColorField`-luokka tarjoaa myös seuraavat staattiset utiliittimenetelmät:

- `fromHex(String hex)`: Muuntaa värimerkin heksamuodossa `Color`-objektiksi, jota voi sitten käyttää tässä luokassa tai muualla.

- `toHex(Color color)`: Muuntaa annetun arvon vastaavaksi heksamuodoksi.

- `isValidHexColor(String hex)`: Tarkistaa, onko annettu arvo kelvollinen 7 merkin heksaväri.

## Parhaat käytännöt {#best-practices}

Jotta `ColorField`-komponentin käyttö olisi optimaalista käyttäjäkokemuksen kannalta, harkitse seuraavia parhaita käytäntöjä:

- **Kontekstuaalinen Apua**: Tarjoa kontekstuaalista apua, kuten työkaluvihjeitä tai etiketti, selvittääksesi, että käyttäjät voivat valita värin ja ymmärtää sen tarkoituksen.

- **Anna Oletusväri**: Valitse oletusväri, joka on järkevä sovelluksesi kontekstissa.

- **Tarjoa Esivalittuja Värejä**: Sisällytä joukko yleisesti käytettyjä tai brändille sopivia värejä värikentän viereen nopeaa valintaa varten.
