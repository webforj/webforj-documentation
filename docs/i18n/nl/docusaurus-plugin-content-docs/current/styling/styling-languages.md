---
title: Styling languages
sidebar_position: 11
sidebar_class_name: new-content
description: >-
  Author your styles in CSS, compile them from Sass or Less, or generate them
  with Tailwind, and load the result into a webforJ app.
_i18n_hash: 98eca77023e33bac367a1a250da900d7
---
Je stijlen bereiken de pagina als CSS, maar je hoeft ze niet als CSS te schrijven. webforJ laadt een stylesheet die je hebt geschreven, compileert er één vanuit een preprocessor zoals Sass of Less, of genereert er één vanuit Tailwind, en het resultaat stijlt je weergaven op dezelfde manier, ongeacht waar het vandaan komt. De DWC-tokens, [CSS aangepaste eigenschappen](/docs/styling/css-variables), en [schaduw onderdelen](/docs/styling/shadow-parts) die in de rest van deze sectie worden besproken, zijn van toepassing binnen elk van hen.

## Gewone CSS {#plain-css}

Een stylesheet die je schrijft heeft geen build nodig. Koppel het aan een component of de app met [`@StyleSheet`](/docs/managing-resources/importing-assets#importing-css-files). Als je al de [frontend bundler](/docs/managing-resources/bundler/overview) draait, kun je in plaats daarvan een `.css` bestand aan een klasse koppelen met `@BundleEntry`, wat het laadt als stijlen voor die weergave.

## Sass en Less {#sass-and-less}

Om je stijlen te schrijven in [Sass](https://sass-lang.com/) of [Less](https://lesscss.org/), met variabelen, nesting en functies, schrijf je de bron en laat je de [frontend bundler](/docs/managing-resources/bundler/overview) deze naar CSS compileren. De compiler is een [uitbreiding](/docs/managing-resources/bundler/extensions/overview) die zichzelf inschakelt wanneer een bron van zijn type aanwezig is, dus het schrijven van een `.scss`, `.sass`, of `.less` bestand is het enige signaal dat het nodig heeft. Koppel de bron aan een klasse op dezelfde manier als je een stylesheet koppelt:

```java title="StyledView.java"
@Route("/styled")
@BundleEntry("styles/view.scss")
public class StyledView extends Composite<FlexLayout> {
  // bouw de weergave
}
```

De uitbreiding compileert `view.scss` naar CSS en laadt het voor de weergave. Zie [SCSS en Sass](/docs/managing-resources/bundler/extensions/scss) en [Less](/docs/managing-resources/bundler/extensions/less) voor de bestandsindeling, laadschema's en opties die elk accepteert.

## Tailwind {#tailwind}

[Tailwind](https://tailwindcss.com/) genereert een stylesheet op basis van de utility class-namen die je weergaven gebruiken, in plaats van vanuit een bestand dat je hebt geschreven. Zet de uitbreiding aan, en voeg vervolgens utilities toe als class-namen zonder iets te importeren. webforJ laat de basis reset van Tailwind weg zodat het niet in conflict komt met de styling die je componenten al hebben, en een utility bereikt het element waarop je het toepast, niet de binnenkant van een component. Zie [Tailwind Extension](/docs/managing-resources/bundler/extensions/tailwind) voor hoe het zijn stylesheet genereert en scopeert, en waar utility classes wel en niet van toepassing zijn.

## Een andere taal {#another-language}

De compiler voor elke taal is een bundeluitbreiding, en het model is open. Om je stijlen in een taal te schrijven waarvoor webforJ geen compiler levert, schrijf een kleine uitbreiding die die compiler bijdraagt, op hetzelfde contract dat Sass en Less gebruiken. Zie [Je eigen uitbreiding schrijven](/docs/managing-resources/bundler/extensions/writing-your-own).
