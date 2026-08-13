---
sidebar_position: 5
title: ColorField
slug: colorfield
description: >-
  A component that provides a default browser-based color picker, allowing users
  to select a color from an input field.
_i18n_hash: d3392930b787f31c30ac78526b8e12d9
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-color-chooser" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/ColorField" top='true'/>

`ColorField`-komponentti antaa käyttäjille mahdollisuuden valita väri selaimen natiivin värinvalitsimen kautta. Koska se perustuu selaimen sisäiseen toteutukseen, sen ulkoasu vaihtelee eri selaimien ja alustojen välillä. Se voi näkyä yksinkertaisena tekstikenttänä, alustastandardina värinvalitsimena tai mukautettuna valintaikkunana. Tämä vaihtelu toimii käyttäjän hyväksi, koska ohjaus vastaa sitä, mihin he ovat jo tottuneet.

<!-- INTRO_END -->

## Käyttäminen `ColorField` {#using-colorfield}

<ParentLink parent="Field" />

`ColorField` laajentaa jaettua `Field`-luokkaa, joka tarjoaa yhteisiä ominaisuuksia kaikille kenttäkomponenteille. Seuraavassa esimerkissä käyttäjä voi valita värin ja näyttää sen tetradiset vastavärit.

<ComponentDemo
path='/webforj/colorfield'
files={[
  'src/main/java/com/webforj/samples/views/fields/colorfield/ColorFieldView.java',
  'src/main/frontend/css/fields/colorfield/colorFieldDemo.css',
]}
height='300px'
/>

`ColorField` on parhaiten käytettävissä tilanteissa, joissa väriä valitseminen on tärkeä osa käyttöliittymää tai sovellusliittymää. Tässä on joitakin tilanteita, joissa voit käyttää `ColorField`-komponenttia tehokkaasti:

1. **Graafinen suunnittelu ja kuvankäsittelytyökalut**: Värikentät ovat olennaisia sovelluksissa, jotka sisältävät mukauttamista värin valinnan kautta.

2. **Teeman mukauttaminen**: Jos sovelluksesi sallii käyttäjien mukauttaa teemoja, värikentän käyttö mahdollistaa heidän valita värejä eri käyttöliittymäelementeille, kuten taustoille, teksteille, painikkeille jne.

3. **Tietojen visualisointi**: Tarjoa käyttäjille välikenttä, jonka avulla he voivat valita värejä kaavioita, kaavioita, lämpökarttoja ja muita visuaalisia esityksiä varten.

## Arvo {#value}

`ColorField` käyttää [`java.awt.Color`](https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/Color.html) -luokkaa värien asettamiseen ja noutamiseen `setValue()`- ja `getValue()`-menetelmien kautta. Vaikka asiakaspään komponentti käsittelee yksinomaan täysin läpinäkyviä RGB-värejä heksadesimaalimerkinnässä, webforJ yksinkertaistaa prosessia muuttamalla automaattisesti `Color`-arvot oikeaan muotoon.

:::tip Heksadesimaalinen parsiminen
Kun käytetään `setText()`-menetelmää arvon asettamiseksi, `ColorField` yrittää jäsentää syötteen heksadesimaaliväriksi. Jos jäsentäminen epäonnistuu, heitettään `IllegalArgumentException`.
:::

## Staattiset apumenetelmät {#static-utilities}

`ColorField`-luokka tarjoaa myös seuraavat staattiset apumenetelmät:

- `fromHex(String hex)`: Muunna väri merkkijono heksadesimaalimuodossa `Color`-olion, jota voidaan sitten käyttää tämän luokan kanssa tai muualla.

- `toHex(Color color)`: Muunna annettu arvo vastaavaan heksadesimaalimuotoon.

- `isValidHexColor(String hex)`: Tarkista, onko annettu arvo voimassa oleva 7 merkin heksadesimaaliväri.

## Parhaat käytännöt {#best-practices}

Jotta `ColorField`-komponentin käyttäjäkokemus olisi optimaalinen, harkitse seuraavia parhaita käytäntöjä:

- **Kontekstuaalinen apu**: Tarjoa kontekstuaalista apua, kuten työkaluvihjeitä tai etiketti, selvittääksesi, että käyttäjät voivat valita värin ja ymmärtää sen tarkoituksen.

- **Tarjoa oletusväri**: Valitse oletusväri, joka on järkevä sovelluksesi kontekstissa.

- **Tarjoa esiasetettuja värejä**: Sisällytä joukko yleisesti käytettyjä tai brändättyjä värejä värikentän viereen nopeaa valintaa varten.
