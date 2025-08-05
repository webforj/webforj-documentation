---
sidebar_position: 2
title: Themes
_i18n_hash: aa6eb0baae2b881b24c45ae970a079dc
---
webforJ umfasst drei integrierte App-Themen und unterstützt die Definition eigener benutzerdefinierter Themen. Die Standardthemen sind:

- **light**: Ein helles Tema mit einem hellen Hintergrund (Standard).
- **dark**: Ein dunkler Hintergrund, der mit der Primärfarbe getönt ist.
- **dark-pure**: Ein vollständig neutrales dunkles Thema, das auf Grautönen basiert.

Um ein Thema in Ihrer App anzuwenden, verwenden Sie die `@AppTheme`-Annotation oder die Methode `App.setTheme()`. Der Themenname muss einer der folgenden sein: `system`, `light`, `dark`, `dark-pure` oder ein benutzerdefinierter Themenname.

```java
@AppTheme("dark-pure")
class MyApp extends App {
  // App-Code
}

// oder programmgesteuert
App.setTheme("dark-pure");
```

## Standardthemen überschreiben {#overriding-default-themes}

Sie können das **light**-Thema überschreiben, indem Sie CSS-Benutzerdefinierte Eigenschaften im `:root`-Selektor neu definieren.

:::info `:root` Pseudoklasse
Die `:root` CSS-Pseudoklasse zielt auf das Wurzel-Element des Dokuments ab. In HTML repräsentiert es das `<html>`-Element und hat eine höhere Spezifität als der einfache `html`-Selektor.
:::

Beispiel:

```css
:root {
  --dwc-color-primary-h: 215;
  --dwc-color-primary-s: 100%;
  --dwc-color-primary-c: 50;
  --dwc-font-size: var(--dwc-font-size-m);
}
```

Um die **dark**- oder **dark-pure**-Themen zu überschreiben, verwenden Sie Attribut-Selektoren am `<html>`-Element:

```css
html[data-app-theme="dark"] {
  --dwc-color-primary-s: 9%;
  --dwc-color-white: hsl(210, 17%, 82%);
}
```

## Benutzerdefinierte Themen erstellen {#creating-custom-themes}

Sie können Ihre eigenen Themen mit dem Selektor `html[data-app-theme='THEME_NAME']` definieren. Benutzerdefinierte Themen können neben den Standardthemen koexistieren, und Sie können während der Laufzeit dynamisch zwischen ihnen wechseln.

```css
html[data-app-theme="new-theme"] {
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
  --dwc-color-primary-c: 60;
}
```

Dann in Ihrer App:

```java
@AppTheme("new-theme")
class MyApp extends App {
  // App-Code
}

// oder programmgesteuert
App.setTheme("new-theme");
```

## Komponententhemen {#component-themes}

Neben den App-Übergreifenden Themen unterstützen webforJ-Komponenten eine Reihe von **Komponententhemen**, die auf den Standardfarbpaletten basieren: `default`, `primary`, `success`, `warning`, `danger`, `info` und `gray`.

Jede Komponente dokumentiert ihre unterstützten Themen im Abschnitt **Styling → Themen**.
