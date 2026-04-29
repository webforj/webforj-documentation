---
sidebar_position: 2
title: Themes
_i18n_hash: afbc96c2eb0da1c5e0eb2e24a69827c2
---
webforJ omvat drie ingebouwde app-thema's en ondersteunt het definiëren van je eigen aangepaste thema's. De standaard thema's zijn:

- **licht**: Een helder thema met een lichte achtergrond (standaard).
- **donker**: Een donkere achtergrond getint met de primaire kleur.
- **donker-puur**: Een volledig neutraal donker thema gebaseerd op grijstinten.

Om een thema toe te passen in je app, gebruik je de `@AppTheme` annotatie of de `App.setTheme()` methode. De themanaam moet een van de volgende zijn: `systeem`, `licht`, `donker`, `donker-puur`, of een aangepaste themanaam.

```java
@AppTheme("donker-puur")
class MyApp extends App {
  // app code
}

// of programmatisch
App.setTheme("donker-puur");
```

## Standaard thema's overschrijven {#overriding-default-themes}

Je kunt het **licht** thema overschrijven door CSS aangepaste eigenschappen opnieuw te definiëren in de `:root` selector.

:::info `:root` pseudo-klasse
De `:root` CSS pseudo-klasse richt zich op het root-element van het document. In HTML vertegenwoordigt het het `<html>` element en heeft het een hogere specificiteit dan de gewone `html` selector.
:::

Voorbeeld:

```css
:root {
  --dwc-color-primary-h: 215;
  --dwc-color-primary-s: 100%;
  --dwc-font-size: var(--dwc-font-size-l);
}
```

Om de **donker** of **donker-puur** thema's te overschrijven, gebruik je attribuutselectors op het `<html>` element:

```css
html[data-app-theme="donker"] {
  --dwc-color-primary-s: 80%;
}
```

## Aangepaste thema's maken {#creating-custom-themes}

Je kunt je eigen thema's definiëren met de `html[data-app-theme='THEME_NAME']` selector. Aangepaste thema's kunnen naast de standaardthema's bestaan en je kunt er dynamisch tussen schakelen tijdens runtime.

```css
html[data-app-theme="nieuw-thema"] {
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
}
```

Om een aangepast thema donker te maken, stel je `--dwc-dark-mode: 1` in en `color-scheme: dark`:

```css
html[data-app-theme="nieuw-donker-thema"] {
  --dwc-dark-mode: 1;
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
  color-scheme: dark;
}
```

Dan in je app:

```java
@AppTheme("nieuw-thema")
class MyApp extends App {
  // app code
}

// of programmatisch
App.setTheme("nieuw-thema");
```

## Componententhema's {#component-themes}

Naast app-niveau thema's ondersteunen webforJ-componenten een set **componententhema's** gebaseerd op de standaardkleurenpaletten: `default`, `primary`, `success`, `warning`, `danger`, `info`, en `gray`.

Elke component documenteert zijn ondersteunde thema's onder de sectie **Styling → Thema's**.
