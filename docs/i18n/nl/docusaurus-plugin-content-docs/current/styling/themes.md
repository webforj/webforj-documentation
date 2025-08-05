---
sidebar_position: 2
title: Themes
_i18n_hash: aa6eb0baae2b881b24c45ae970a079dc
---
webforJ omvat drie ingebouwde app-thema's en ondersteunt het definiëren van uw eigen aangepaste thema's. De standaardthema's zijn:

- **light**: Een helder thema met een lichte achtergrond (standaard).
- **dark**: Een donkere achtergrond getint met de primaire kleur.
- **dark-pure**: Een volledig neutraal donker thema gebaseerd op grijstinten.

Om een thema in uw app toe te passen, gebruikt u de `@AppTheme` annotatie of de `App.setTheme()` methode. De thema naam moet een van de volgende zijn: `system`, `light`, `dark`, `dark-pure`, of een aangepaste themanaam.

```java
@AppTheme("dark-pure")
class MyApp extends App {
  // app code
}

// of programatisch
App.setTheme("dark-pure");
```

## Standaardthema's overschrijven {#overriding-default-themes}

U kunt het **light** thema overschrijven door CSS aangepaste eigenschappen opnieuw te definiëren in de `:root` selector.

:::info `:root` pseudo-klasse
De `:root` CSS pseudo-klasse richt zich op het belangrijkste element van het document. In HTML vertegenwoordigt het het `<html>` element en heeft het een hogere specificiteit dan de gewone `html` selector.
:::

Voorbeeld:

```css
:root {
  --dwc-color-primary-h: 215;
  --dwc-color-primary-s: 100%;
  --dwc-color-primary-c: 50;
  --dwc-font-size: var(--dwc-font-size-m);
}
```

Om de **dark** of **dark-pure** thema's te overschrijven, gebruikt u attribuutselectoren op het `<html>` element:

```css
html[data-app-theme="dark"] {
  --dwc-color-primary-s: 9%;
  --dwc-color-white: hsl(210, 17%, 82%);
}
```

## Aangepaste thema's maken {#creating-custom-themes}

U kunt uw eigen thema's definiëren met behulp van de `html[data-app-theme='THEME_NAME']` selector. Aangepaste thema's kunnen naast de standaardthema's bestaan, en u kunt er dynamisch tussen schakelen tijdens runtime.

```css
html[data-app-theme="new-theme"] {
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
  --dwc-color-primary-c: 60;
}
```

Dan in uw app:

```java
@AppTheme("new-theme")
class MyApp extends App {
  // app code
}

// of programatisch
App.setTheme("new-theme");
```

## Componentthema's {#component-themes}

Naast de thema's op app-niveau ondersteunen webforJ componenten een reeks **componentthema's** gebaseerd op de standaard kleurpaletten: `default`, `primary`, `success`, `warning`, `danger`, `info`, en `gray`.

Elk component documenteert de ondersteunde thema's onder de sectie **Styling → Themes**.
