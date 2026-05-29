---
sidebar_position: 5
title: ColorField
slug: colorfield
description: >-
  A component that provides a default browser-based color picker, allowing users
  to select a color from an input field.
_i18n_hash: 50390b19b24346c878300024badc1380
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-color-chooser" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/ColorField" top='true'/>

`ColorField`-komponentti antaa käyttäjille mahdollisuuden valita väri selaimen natiivin värivalitsimen kautta. Koska se perustuu selaimen sisäänrakennettuun toteutukseen, sen ulkoasu vaihtelee selaimien ja alustojen välillä. Se voi näkyä yksinkertaisena tekstikenttänä, alustan mukaisena värivalitsimena tai mukautettuna valintarajapintana. Tämä vaihtelu toimii käyttäjän hyväksi, sillä ohjaus vastaa sitä, mihin he ovat jo tottuneet.

<!-- INTRO_END -->

## Using `ColorField` {#using-colorfield}

<ParentLink parent="Field" />

`ColorField` laajentaa jaettua `Field`-luokkaa, joka tarjoaa yhteisiä ominaisuuksia kaikille kenttäkomponenteille. Seuraava esimerkki antaa käyttäjän valita värin ja näyttää sen tetradic-komplementit.

<ComponentDemo
path='/webforj/colorfield'
files={[
  'src/main/java/com/webforj/samples/views/fields/colorfield/ColorFieldView.java',
  'src/main/resources/static/css/fields/colorfield/colorFieldDemo.css',
]}
height='300px'
/>

`ColorField`-komponenttia käytetään parhaiten tilanteissa, joissa värin valinta on tärkeä osa käyttäjäliittymää tai sovelluksen käyttöliittymää. Tässä on joitakin tilanteita, joissa voit käyttää `ColorField`-komponenttia tehokkaasti:

1. **Graafinen Suunnittelu ja Kuvankäsittelytyökalut**: Värikentät ovat välttämättömiä sovelluksissa, jotka sisältävät räätälöintiä värin valinnan kautta.

2. **Teeman Räätälöinti**: Jos sovelluksesi antaa käyttäjille mahdollisuuden räätälöidä teemoja, värikentän käyttö sallii heidän valita värejä erilaisille käyttöliittymäelementeille, kuten taustoille, tekstille, painikkeille jne.

3. **Datan Visualisointi**: Tarjoa käyttäjille värikenttä värien valitsemiseksi kaavioissa, graafeissa, lämpökartoissa ja muissa visuaalisissa esityksissä.

## Value {#value}

`ColorField` käyttää [`java.awt.Color`](https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/Color.html) -luokkaa värien asettamiseen ja noutamiseen `setValue()`- ja `getValue()`-menetelmien kautta. Vaikka asiakaspäätteen komponentti käsittelee vain täysin peittäviä RGB-värejä heksadesimaalimuodossa, webforJ yksinkertaistaa prosessia muuntamalla automaattisesti `Color`-arvot oikeaan muotoon.

:::tip Heksadesimaalinen käsittely
Kun käytät `setText()`-menetelmää arvon asettamiseen, `ColorField` yrittää käsitellä syötteen heksadesimaalivärinä. Jos käsittely epäonnistuu, heitettään `IllegalArgumentException`.
:::

## Static utilities {#static-utilities}

`ColorField`-luokka tarjoaa myös seuraavat staattiset utiliitit:

- `fromHex(String hex)`: Muuntaa värimerkkijonon heksadesimaalimuodossa `Color`-objektiksi, jota voidaan käyttää tämän luokan kanssa tai muualla.

- `toHex(Color color)`: Muuntaa annetun arvon vastaavaksi heksadesimaalimuodoksi.

- `isValidHexColor(String hex)`: Tarkistaa, onko annettu arvo kelvollinen 7 merkin heksadesimaaliväri.

## Best practices {#best-practices}

Varmistaaksesi optimaalisen käyttäjäkokemuksen `ColorField`-komponentin käytössä, harkitse seuraavia parhaita käytäntöjä:

- **Kontekstuaalinen Apua**: Tarjoa kontekstuaalista apua, kuten työkaluvihjeitä tai etiketti, selventääksesi, että käyttäjät voivat valita väri ja ymmärtää sen tarkoituksen.

- **Tarjoa Oletusväri**: Valitse oletusväri, joka on järkevä sovelluksesi kontekstissa.

- **Tarjoa Ennakkoon Määritettyjä Värejä**: Sisällytä paletti yleisesti käytetyistä tai brändätyistä väreistä nopeaa valintaa varten.
