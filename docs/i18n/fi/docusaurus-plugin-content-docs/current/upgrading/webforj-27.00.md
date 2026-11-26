---
title: Päivitys versioon 27.00
description: Päivitä versiosta 26.00 versioon 27.00
sidebar_position: 10
sidebar_class_name: new-content
draft: true
_i18n_title_hash: f521441c4a5da00c1bc05ae7307c3644
_i18n_description_hash: 2ca050c7f4bc5f02fb7c8680550799a3
_i18n_hash: 2757e1b06263f629ef0c1e5c15785122
---
Tämä dokumentaatio opastaa päivittämään webforJ-sovellukset versiosta 26.00 versioon 27.00.
Alla on lueteltu muutokset, jotka tarvitaan, jotta nykyiset sovellukset toimivat jatkossakin sujuvasti.
Kattavamman luettelon versioiden välisistä muutoksista löydät [GitHubin julkaisuyleiskatsauksesta](https://github.com/webforj/webforj/releases).

<!-- INTRO_END -->

<AutomatedUpgradeTip />

## Teksti- ja HTML-sisältö {#text-and-html-content}

Aiemmissa versioissa `<html>`-elementtiin käärittyä ja `setText()`-metodille annettua arvoa käsiteltiin HTML:nä. Tämä toiminta on vanhentunut, ja se poistetaan webforJ-versiosta 27.00. Toimintaa hallitaan asetuksella `webforj.legacyHtmlInText`:

- `true` (oletus): `<html>`-elementtiin kääritty arvo renderöidään HTML-sisältönä.
- `false`: Sama arvo näytetään sellaisenaan.

`<html>`-elementtiä ei näytetä kummassakaan tapauksessa.

Kun `<html>`-elementtiin kääritty arvo annetaan ensimmäisen kerran `setText()`-metodille, lokiin kirjoitetaan varoitus, jossa mainitaan komponentti ja kutsukohta. Näin kutsu voidaan siirtää `setHtml()`-metodiin.

## Pakollinen sidonta `@Size`-määrityksellä {#required-binding-using-size-annotation}

Ennen versiota 27.00 [Jakarta-annotaation](/docs/data-binding/validation/jakarta-validation) `@Size` käyttö merkitsi sidotun komponentin automaattisesti pakolliseksi.
Jatkossa `@Size` merkitsee sidotun komponentin pakolliseksi vain, jos sille on määritetty `min`-arvo.

```java
// ei enää merkitse kenttää pakolliseksi
@Size(max = 80, message = "Kadun nimen on oltava alle 80 merkkiä pitkä")
private String street;
```

```java
// merkitsee kentän pakolliseksi
@Size(min = 2, max = 80, message = "Kadun nimen on oltava 2–80 merkkiä pitkä")
private String street;
```
