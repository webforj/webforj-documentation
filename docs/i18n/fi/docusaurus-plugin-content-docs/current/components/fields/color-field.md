---
sidebar_position: 5
title: ColorField
slug: colorfield
description: >-
  A component that provides a default browser-based color picker, allowing users
  to select a color from an input field.
_i18n_hash: 42e1e3270076a584d052295db1602298
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-color-chooser" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/ColorField" top='true'/>

`ColorField`-komponentti sallii käyttäjien valita värin selaimen natiivin värivalitsimen kautta. Koska se perustuu selaimen sisäiseen toteutukseen, sen ulkonäkö vaihtelee selainten ja alustojen välillä. Se voi näkyä yksinkertaisena tekstikenttänä, alustastandardina värivalitsimena tai mukautettuna valitsimen käyttöliittymänä. Tämä vaihtelu toimii käyttäjän eduksi, sillä ohjaus vastaa jo tuttua.

<!-- INTRO_END -->

## Käyttö `ColorField` {#using-colorfield}

<ParentLink parent="Field" />

`ColorField` laajentaa jaettua `Field`-luokkaa, joka tarjoaa yleisiä ominaisuuksia kaikille kenttäkomponenteille. Seuraava esimerkki mahdollistaa käyttäjän valita värin ja näyttää sen tetradiset vastavärit.

<ComponentDemo 
path='/webforj/colorfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/colorfield/ColorFieldView.java'
cssURL='/css/fields/colorfield/colorFieldDemo.css'
height='300px'
/>

`ColorField` on parhaimmillaan tilanteissa, joissa värin valinta on olennaista käyttäjäliittymässä tai sovellusliittymässä. Tässä on joitakin tilanteita, joissa voit käyttää `ColorField`-komponenttia tehokkaasti:

1. **Grafiikkasuunnittelu ja kuvankäsittelytyökalut**: Värikentät ovat välttämättömiä sovelluksissa, jotka sisältävät mukauttamista värin valinnan kautta.

2. **Teeman mukauttaminen**: Jos sovelluksesi sallii käyttäjien mukauttaa teemoja, värikentän käyttäminen mahdollistaa heidän valita värejä eri käyttöliittymäelementeille, kuten taustoille, tekstille, painikkeille jne.

3. **Tietojen visualisointi**: Tarjoa käyttäjille värikenttä, josta he voivat valita värejä kaavioille, grafiikoille, lämpökarttoille ja muille visuaalisille esityksille.

## Arvo {#value}

`ColorField` käyttää [`java.awt.Color`](https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/Color.html) -luokkaa värien asettamiseen ja hakemiseen `setValue()` ja `getValue()` -menetelmien kautta. Vaikka asiakaspään komponentti käsittelee yksinomaan täysin läpinäkyviä RGB-värejä heksadesimaali-merkinnässä, webforJ yksinkertaistaa prosessia automaattisesti muuntamalla `Color`-arvot oikeaan muotoon.

:::tip Heksadesimaali-parsing
Kun käytät `setText()`-menetelmää arvon määrittämiseen, `ColorField` yrittää analysoida syötteen heksadesimaalivärinä. Jos analysointi epäonnistuu, heitetään `IllegalArgumentException`.
:::

## Staattiset työkalut {#static-utilities}

`ColorField`-luokka tarjoaa myös seuraavat staattiset apumenetelmät:

- `fromHex(String hex)`: Muuntaa värimerkkijonon heksamuodossa `Color`-objektiksi, jota voidaan käyttää tässä luokassa tai muualla.

- `toHex(Color color)`: Muuntaa annetun arvon vastaavaksi heksamuotoiseksi esitykseksi.

- `isValidHexColor(String hex)`: Tarkistaa, onko annettu arvo voimassa oleva 7 merkin heksaväri.

## Parhaat käytännöt {#best-practices}

Jotta `ColorField`-komponentin käyttäjäkokemus olisi optimaalinen, harkitse seuraavia parhaita käytäntöjä:

- **Kontekstuaalinen tuki**: Tarjoa kontekstuaalista tukea, kuten työkaluvihjeitä tai etiketti, selventämään, että käyttäjät voivat valita värin ja ymmärtää sen tarkoituksen.

- **Tarjoa oletusväri**: Varmista, että oletusväri on järkevä sovelluksesi kontekstissa.

- **Tarjoa esiasetettuja värejä**: Sisällytä paletteja yleisesti käytetyistä tai brändin mukaisista väreistä värikentän viereen nopeaa valintaa varten.
