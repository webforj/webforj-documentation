---
title: Upgrade to 27.00
description: Upgrade from 26.00 to 27.00
sidebar_position: 10
sidebar_class_name: new-content
draft: true
_i18n_hash: 85416371e550d0aedae0a0771aff67be
---
Tämä dokumentaatio toimii oppaana webforJ-sovellusten päivittämiseen versiosta 26.00 versioon 27.00. Tässä ovat muutokset, jotka tarvitaan, jotta olemassa olevat sovellukset voivat jatkaa sujuvaa toimintaa. Kuten aina, katso [GitHubin julkaisutiedot](https://github.com/webforj/webforj/releases) saadaksesi kattavamman luettelon muutoksista versioiden välillä.

<!-- INTRO_END -->

<AutomatedUpgradeTip />

## Teksti- ja HTML-sisältö {#text-and-html-content}

Aikaisemmat versiot käsittelivät `<html>`-tagin sisällä olevaa arvoa, joka annettiin `setText()`-metodille, HTML:nä. Tämä toiminta on poistettu käytöstä ja se on poistettu webforJ 27.00:sta. Asetus `webforj.legacyHtmlInText` hallitsee tätä:

- `true` (oletusarvo): `<html>`-tagin sisällä oleva arvo renderöi sen sisällön HTML:nä.
- `false`: Sama arvo näytetään kirjaimellisesti.

`<html>`-kääreä ei koskaan näytetä kummassakaan tapauksessa.

Ensimmäisen kerran, kun `<html>`-kääreellä varustettu arvo saavuttaa `setText()`, lokitetaan varoitus, jossa mainitaan komponentti ja kutsupaikka, jotta kutsu voidaan siirtää `setHtml()`-metodiin.
