---
sidebar_position: 2
title: Themes
_i18n_hash: afbc96c2eb0da1c5e0eb2e24a69827c2
---
webforJ sisältää kolme sisäänrakennettua sovellusteemaa ja tukee omien mukautettujen teemojen määrittämistä. Oletusteemat ovat:

- **light**: Kirkas teema, jossa on vaalea tausta (oletusarvo).
- **dark**: Tumma tausta, jossa on päävärin sävy.
- **dark-pure**: Täysin neutraali tumma teema, joka perustuu harmaan sävyihin.

Teeman soveltamiseksi sovelluksessa käytä `@AppTheme`-annotaatiota tai `App.setTheme()`-metodia. Teeman nimen on oltava yksi seuraavista: `system`, `light`, `dark`, `dark-pure` tai mukautetun teeman nimi.

```java
@AppTheme("dark-pure")
class MyApp extends App {
  // sovelluskoodi
}

// tai ohjelmallisesti
App.setTheme("dark-pure");
```

## Oletusteemojen ylikirjoittaminen {#overriding-default-themes}

Voit ylikirjoittaa **light**-teeman määrittämällä CSS-mukautettuja ominaisuuksia `:root`-valitsimessa.

:::info `:root` pseudoluokka
`:root` CSS-pseudoluokka kohdistuu asiakirjan juurielementtiin. HTML:ssä se edustaa `<html>`-elementtiä ja sillä on suurempi spesifisyys kuin tavallisella `html`-valitsimella.
:::

Esimerkki:

```css
:root {
  --dwc-color-primary-h: 215;
  --dwc-color-primary-s: 100%;
  --dwc-font-size: var(--dwc-font-size-l);
}
```

Voit ylikirjoittaa **dark** tai **dark-pure** teemoja käyttämällä attribuuttivalitsimia `<html>`-elementissä:

```css
html[data-app-theme="dark"] {
  --dwc-color-primary-s: 80%;
}
```

## Mukautettujen teemojen luominen {#creating-custom-themes}

Voit määrittää omat teemasi käyttämällä `html[data-app-theme='THEME_NAME']`-valitsinta. Mukautetut teemat voivat elää rinnakkain oletusteemojen kanssa, ja voit vaihtaa niiden välillä dynaamisesti ajonaikaisesti.

```css
html[data-app-theme="new-theme"] {
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
}
```

Tehdäksesi mukautetusta teemasta tumman, aseta `--dwc-dark-mode: 1` ja `color-scheme: dark`:

```css
html[data-app-theme="new-dark-theme"] {
  --dwc-dark-mode: 1;
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
  color-scheme: dark;
}
```

Sitten sovelluksessasi:

```java
@AppTheme("new-theme")
class MyApp extends App {
  // sovelluskoodi
}

// tai ohjelmallisesti
App.setTheme("new-theme");
```

## Komponenttiteemat {#component-themes}

Sovellustason teemojen lisäksi webforJ-komponentit tukevat joukkoa **komponenttiteemoja**, jotka perustuvat oletusväripaletteihin: `default`, `primary`, `success`, `warning`, `danger`, `info` ja `gray`.

Jokainen komponentti dokumentoi tukemansa teemat **Styling → Themes**-osiolla.
