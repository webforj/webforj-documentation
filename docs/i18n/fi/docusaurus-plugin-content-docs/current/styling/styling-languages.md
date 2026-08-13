---
title: Styling languages
sidebar_position: 11
sidebar_class_name: new-content
description: >-
  Author your styles in CSS, compile them from Sass or Less, or generate them
  with Tailwind, and load the result into a webforJ app.
_i18n_hash: 98eca77023e33bac367a1a250da900d7
---
Tyylisi saapuvat sivulle CSS:nä, mutta sinun ei tarvitse kirjoittaa niitä CSS:nä. webforJ lataa sinun kirjoittamasi tyylitiedoston, kääntää sellaisen preprocessorin, kuten Sass tai Less, avulla tai generoi sen Tailwindista, ja tulos tyylittää näkymäsi samalla tavalla riippumatta siitä, mistä se tuli. DWC-tokenit, [CSS-mukautetut ominaisuudet](/docs/styling/css-variables) ja [varjopartit](/docs/styling/shadow-parts), joita käsitellään tämän osion muissa kohdissa, pätevät mikä tahansa niistä.

## Yksinkertainen CSS {#plain-css}

Kirjoittamasi tyylitiedoston ei tarvitse olla rakennettu. Kiinnitä se komponenttiin tai sovellukseen [`@StyleSheet`](/docs/managing-resources/importing-assets#importing-css-files) avulla. Kun käytät jo [frontend-bundleria](/docs/managing-resources/bundler/overview), voit sen sijaan sitoa `.css`-tiedoston luokkaan `@BundleEntry`, joka lataa sen tyyliksi sille näkymälle.

## Sass ja Less {#sass-and-less}

Kirjoittaaksesi tyylisi [Sassissa](https://sass-lang.com/) tai [Lessissä](https://lesscss.org/), käyttäen muuttujia, sisennystä ja funktioita, kirjoita lähde ja anna [frontend-bundlerin](/docs/managing-resources/bundler/overview) kääntää se CSS:ksi. Kääntäjä on [laajennus](/docs/managing-resources/bundler/extensions/overview), joka aktivoituu, kun sen tyyppistä lähdettä on olemassa, joten `.scss`, `.sass` tai `.less`-tiedoston kirjoittaminen on ainoa signaali, jota se tarvitsee. Sido lähde luokkaan samalla tavalla kuin sidot tyylitiedoston:

```java title="StyledView.java"
@Route("/styled")
@BundleEntry("styles/view.scss")
public class StyledView extends Composite<FlexLayout> {
  // rakenna näkymä
}
```

Laajennus kääntää `view.scss`-tiedoston CSS:ksi ja lataa sen näkymälle. Katso [SCSS ja Sass](/docs/managing-resources/bundler/extensions/scss) ja [Less](/docs/managing-resources/bundler/extensions/less) tiedostojen rakenteesta, latauspoluista ja valinnoista, joita kukin hyväksyy.

## Tailwind {#tailwind}

[Tailwind](https://tailwindcss.com/) generoituu tyylitiedosto utiliteettijärjestelmän nimien perusteella, joita näkymäsi käyttävät, ei tiedostosta, jonka kirjoitat. Kytke laajennus päälle ja lisää työkaluja luokkina ilman tuontia. webforJ jättää Tailwindin perusresetoinnin pois, jotta se ei taistelisi tyylien kanssa, joita komponenttisi jo kantavat, ja utiliteetti saavuttaa sen elementin, johon sen laitat, ei komponentin sisään. Katso [Tailwind-laajennus](/docs/managing-resources/bundler/extensions/tailwind) siitä, miten se generoi ja raja tekee tyylitiedostoa, ja missä utiliteettiluokat pätevät ja eivät päde.

## Toinen kieli {#another-language}

Jokaisen kielen kääntäjä on bundler-laajennus, ja malli on avoin. Kirjoittaaksesi tyylisi kielellä, jota webforJ ei toimita kääntäjälle, kirjoita pieni laajennus, joka tarjoaa tuon kääntäjän, samalla sopimuksella, jota Sass ja Less käyttävät. Katso [Oman laajennuksen kirjoittaminen](/docs/managing-resources/bundler/extensions/writing-your-own).
