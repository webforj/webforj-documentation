---
sidebar_position: 2
title: Themes
_i18n_hash: afb80b03bfe243ffa93d6f72a05809e2
---
webforJ sisältää kolme sisäänrakennettua sovellusteemaa ja tukee omien mukautettujen teemojen määrittämistä. Oletusteemat ovat:

- **light**: Kirkas teema, jossa on vaalea taustaväri (oletus).
- **dark**: Tumma tausta, jossa on pääväriä.
- **dark-pure**: Täysin neutraali tumma teema, joka perustuu harmaisiin sävyihin.

Teeman käyttämiseksi sovelluksessasi, käytä `@AppTheme` -annotaatiota tai `App.setTheme()` -metodia. Teeman nimen on oltava yksi seuraavista: `system`, `light`, `dark`, `dark-pure` tai mukautettu teeman nimi.

```java
@AppTheme("dark-pure")
class MyApp extends App {
  // sovelluskoodi
}

// tai ohjelmallisesti
App.setTheme("dark-pure");
```

## Oletusteemojen ylikirjoittaminen {#overriding-default-themes}

Voit ylikirjoittaa **light**-teeman määrittelemällä CSS-mukautettuja muuttujia `:root`-valitsimessa.

:::info `:root` -pseudo-luokka
`:root` CSS -pseudo-luokka kohdistaa asiakirjan juurielementtiin. HTML: ssä se edustaa `<html>`-elementtiä ja sillä on suurempi tarkkuus kuin pelkällä `html`-valitsimella.
:::

Esimerkki:

```css
:root {
  --dwc-color-primary-h: 215;
  --dwc-color-primary-s: 100%;
  --dwc-color-primary-c: 50;
  --dwc-font-size: var(--dwc-font-size-m);
}
```

Voit ylikirjoittaa **dark**- tai **dark-pure**-teeman käyttämällä attribuuttivalitsimia `<html>`-elementissä:

```css
html[data-app-theme="dark"] {
  --dwc-color-primary-s: 9%;
  --dwc-color-white: hsl(210, 17%, 82%);
}
```

## Mukautettujen teemojen luominen {#creating-custom-themes}

Voit määrittää omia teemoja käyttämällä valitsinta `html[data-app-theme='THEME_NAME']`. Mukautetut teemat voivat olla rinnakkain oletusteemojen kanssa, ja voit vaihtaa niiden välillä dynaamisesti ajonaikana.

```css
html[data-app-theme="new-theme"] {
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
  --dwc-color-primary-c: 60;
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

Sovellustason teemojen lisäksi webforJ-komponentit tukevat joukkoa **komponenttiteemoja**, jotka perustuvat oletusväriasteikkoihin: `default`, `primary`, `success`, `warning`, `danger`, `info` ja `gray`.

Jokainen komponentti dokumentoi tukemansa teemat **Styling → Teemat** -osiossa.
