---
sidebar_position: 2
title: Themes
_i18n_hash: afb80b03bfe243ffa93d6f72a05809e2
---
webforJ umfasst drei integrierte App-Themen und unterstützt die Definition eigener benutzerdefinierter Themen. Die Standardthemen sind:

- **light**: Ein helles Thema mit einem hellen Hintergrund (Standard).
- **dark**: Ein dunkler Hintergrund, getönt mit der Primärfarbe.
- **dark-pure**: Ein vollständig neutrales dunkles Thema, basierend auf Grautönen.

Um ein Thema in Ihrer App anzuwenden, verwenden Sie die `@AppTheme` Annotation oder die `App.setTheme()` Methode. Der Themenname muss einer der folgenden sein: `system`, `light`, `dark`, `dark-pure` oder ein benutzerdefinierter Themenname.

```java
@AppTheme("dark-pure")
class MyApp extends App {
  // app code
}

// oder programmatisch
App.setTheme("dark-pure");
```

## Überschreiben von Standardthemen {#overriding-default-themes}

Sie können das **light** Thema überschreiben, indem Sie CSS-Benutzerdefinierte Eigenschaften im `:root` Selektor neu definieren.

:::info `:root` Pseudoklasse
Die `:root` CSS Pseudoklasse zielt auf das Wurzelement des Dokuments ab. In HTML repräsentiert es das `<html>` Element und hat eine höhere Spezifität als der einfache `html` Selektor.
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

Um die **dark** oder **dark-pure** Themen zu überschreiben, verwenden Sie Attributselektoren am `<html>` Element:

```css
html[data-app-theme="dark"] {
  --dwc-color-primary-s: 9%;
  --dwc-color-white: hsl(210, 17%, 82%);
}
```

## Erstellen benutzerdefinierter Themen {#creating-custom-themes}

Sie können Ihre eigenen Themen mit dem `html[data-app-theme='THEME_NAME']` Selektor definieren. Benutzerdefinierte Themen können neben den Standardthemen koexistieren, und Sie können dynamisch zur Laufzeit zwischen ihnen wechseln.

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
  // app code
}

// oder programmatisch
App.setTheme("new-theme");
```

## Komponenten-Themen {#component-themes}

Neben den App-Übergeordneten Themen unterstützen die webforJ Komponenten eine Reihe von **Komponenten-Themen**, die auf den Standardfarbpaletten basieren: `default`, `primary`, `success`, `warning`, `danger`, `info` und `gray`.

Jede Komponente dokumentiert ihre unterstützten Themen im Abschnitt **Styling → Themen**.
