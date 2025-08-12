---
sidebar_position: 2
title: Themes
_i18n_hash: afb80b03bfe243ffa93d6f72a05809e2
---
webforJ bevat drie ingebouwde app-thema's en ondersteunt het definiëren van je eigen aangepaste thema's. De standaardthema's zijn:

- **licht**: Een helder thema met een lichte achtergrond (standaard).
- **donker**: Een donkere achtergrond getint met de primaire kleur.
- **donker-puur**: Een volledig neutraal donker thema gebaseerd op grijstinten.

Om een thema in je app toe te passen, gebruik je de `@AppTheme` annotatie of de `App.setTheme()` methode. De themanaam moet een van de volgende zijn: `system`, `light`, `dark`, `dark-pure`, of een aangepaste themanaam.

```java
@AppTheme("dark-pure")
class MyApp extends App {
  // app code
}

// of programmatically
App.setTheme("dark-pure");
```

## Standaardthema's overschrijven {#overriding-default-themes}

Je kunt het **lichte** thema overschrijven door CSS aangepaste eigenschappen opnieuw te definiëren in de `:root` selector.

:::info `:root` pseudo-klasse
De `:root` CSS pseudo-klasse richt zich op het hoofdelement van het document. In HTML vertegenwoordigt het het `<html>` element en heeft het een hogere specificiteit dan de gewone `html` selector.
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

Om de **donkere** of **donker-puur** thema's te overschrijven, gebruik je attributeselectoren op het `<html>` element:

```css
html[data-app-theme="dark"] {
  --dwc-color-primary-s: 9%;
  --dwc-color-white: hsl(210, 17%, 82%);
}
```

## Aangepaste thema's creëren {#creating-custom-themes}

Je kunt je eigen thema's definiëren met de `html[data-app-theme='THEME_NAME']` selector. Aangepaste thema's kunnen naast de standaardthema's bestaan, en je kunt er tijdens runtime dynamisch tussen schakelen.

```css
html[data-app-theme="new-theme"] {
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
  --dwc-color-primary-c: 60;
}
```

Dan in je app:

```java
@AppTheme("new-theme")
class MyApp extends App {
  // app code
}

// of programmatically
App.setTheme("new-theme");
```

## Componentthema's {#component-themes}

Naast app-thema's ondersteunen webforJ componenten een set van **componentthema's** gebaseerd op de standaard kleurpaletten: `default`, `primary`, `success`, `warning`, `danger`, `info`, en `gray`.

Elk component documenteert zijn ondersteunde thema's onder de sectie **Styling → Thema's**.
