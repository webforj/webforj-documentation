---
sidebar_position: 2
title: Themes
_i18n_hash: afbc96c2eb0da1c5e0eb2e24a69827c2
---
webforJ umfasst drei integrierte App-Themen und unterstützt die Definition eigener benutzerdefinierter Themen. Die Standardthemen sind:

- **light**: Ein helles Thema mit einem hellen Hintergrund (Standard).
- **dark**: Ein dunkler Hintergrund, der mit der Primärfarbe getönt ist.
- **dark-pure**: Ein vollständig neutrales dunkles Thema, das auf Grautönen basiert.

Um ein Thema in Ihrer App anzuwenden, verwenden Sie die `@AppTheme`-Annotation oder die Methode `App.setTheme()`. Der Themenname muss einer der folgenden sein: `system`, `light`, `dark`, `dark-pure` oder ein benutzerdefinierter Themenname.

```java
@AppTheme("dark-pure")
class MyApp extends App {
  // App-Code
}

// oder programmatisch
App.setTheme("dark-pure");
```

## Standardthemen überschreiben {#overriding-default-themes}

Sie können das **light**-Thema überschreiben, indem Sie CSS-CSS-Eigenschaften im `:root`-Selektor neu definieren.

:::info `:root`-Pseudoklasse
Die `:root` CSS-Pseudoklasse zielt auf das Wurzelement des Dokuments ab. In HTML steht sie für das `<html>`-Element und hat eine höhere Spezifität als der einfache `html`-Selektor.
:::

Beispiel:

```css
:root {
  --dwc-color-primary-h: 215;
  --dwc-color-primary-s: 100%;
  --dwc-font-size: var(--dwc-font-size-l);
}
```

Um die **dark**- oder **dark-pure**-Themen zu überschreiben, verwenden Sie Attributselektoren am `<html>`-Element:

```css
html[data-app-theme="dark"] {
  --dwc-color-primary-s: 80%;
}
```

## Benutzerdefinierte Themen erstellen {#creating-custom-themes}

Sie können Ihre eigenen Themen mit dem Selektor `html[data-app-theme='THEME_NAME']` definieren. Benutzerdefinierte Themen können mit den Standardthemen koexistieren, und Sie können zur Laufzeit dynamisch zwischen ihnen wechseln.

```css
html[data-app-theme="new-theme"] {
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
}
```

Um ein benutzerdefiniertes Thema dunkel zu machen, setzen Sie `--dwc-dark-mode: 1` und `color-scheme: dark`:

```css
html[data-app-theme="new-dark-theme"] {
  --dwc-dark-mode: 1;
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
  color-scheme: dark;
}
```

Dann in Ihrer App:

```java
@AppTheme("new-theme")
class MyApp extends App {
  // App-Code
}

// oder programmatisch
App.setTheme("new-theme");
```

## Komponententhemen {#component-themes}

Neben den App-Level-Themen unterstützen webforJ-Komponenten eine Reihe von **Komponententhemen**, die auf den Standardfarbpaletten basieren: `default`, `primary`, `success`, `warning`, `danger`, `info` und `gray`.

Jede Komponente dokumentiert ihre unterstützten Themen im Abschnitt **Styling → Themes**.
