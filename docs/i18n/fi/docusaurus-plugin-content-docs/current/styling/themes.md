---
sidebar_position: 2
title: Themes
_i18n_hash: aa6eb0baae2b881b24c45ae970a079dc
---
webforJ sisältää kolme sisäänrakennettua sovellusteemaa ja tukee omien mukautettujen teemojen määrittelemistä. Oletusteemat ovat:

- **light**: Kirkas teema, jossa on vaalea tausta (oletus).
- **dark**: Tumma tausta, joka on sävytetty pääväri kanssa.
- **dark-pure**: Täysin neutraali tumma teema, joka perustuu harmaan sävyihin.

Voit soveltaa teemaa sovelluksessasi käyttämällä `@AppTheme`-annotaatiota tai `App.setTheme()`-metodia. Teeman nimen on oltava yksi seuraavista: `system`, `light`, `dark`, `dark-pure`, tai mukautettu teeman nimi.

```java
@AppTheme("dark-pure")
class MyApp extends App {
  // sovelluskoodi
}

// tai ohjelmallisesti
App.setTheme("dark-pure");
```

## Oletusteemojen ylittäminen {#overriding-default-themes}

Voit ylittää **light**-teeman määrittelemällä uudelleen CSS:n mukautetut ominaisuudet `:root`-valitsimessa.

:::info `:root` -pseudo-luokka
`:root` CSS:n pseudo-luokka kohdistaa asiakirjan juurielementtiin. HTML:ssä se edustaa `<html>`-elementtiä ja sillä on suurempi spesifisyys kuin pelkällä `html`-valitsimella.
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

Ylitääksesi **dark**- tai **dark-pure**-teemat, käytä attribuuttivalitsimia `<html>`-elementissä:

```css
html[data-app-theme="dark"] {
  --dwc-color-primary-s: 9%;
  --dwc-color-white: hsl(210, 17%, 82%);
}
```

## Mukautettujen teemojen luominen {#creating-custom-themes}

Voit määrittää omat teemasi käyttämällä `html[data-app-theme='TEEMA_NIMI']`-valitsinta. Mukautetut teemat voivat olla rinnakkain oletusteemojen kanssa, ja voit vaihtaa niiden välillä dynaamisesti ajon aikana.

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

Sovellustason teemojen lisäksi webforJ-komponentit tukevat joukkoa **komponenttiteemoja**, jotka perustuvat oletusväripalettien: `default`, `primary`, `success`, `warning`, `danger`, `info`, ja `gray` kanssa.

Jokainen komponentti dokumentoi tukemansa teemat **Styling → Themes** -osiossa.
