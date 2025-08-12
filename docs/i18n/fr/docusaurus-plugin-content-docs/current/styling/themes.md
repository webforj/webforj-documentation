---
sidebar_position: 2
title: Themes
_i18n_hash: afb80b03bfe243ffa93d6f72a05809e2
---
webforJ inclut trois thèmes d'application intégrés et prend en charge la définition de vos propres thèmes personnalisés. Les thèmes par défaut sont :

- **clair** : Un thème lumineux avec un arrière-plan clair (par défaut).
- **sombre** : Un arrière-plan sombre teinté de la couleur principale.
- **sombre-pur** : Un thème sombre totalement neutre basé sur des tons de gris.

Pour appliquer un thème dans votre application, utilisez l'annotation `@AppTheme` ou la méthode `App.setTheme()`. Le nom du thème doit être l'un des suivants : `system`, `light`, `dark`, `dark-pure`, ou un nom de thème personnalisé.

```java
@AppTheme("dark-pure")
class MyApp extends App {
  // code de l'application
}

// ou de manière programmatique
App.setTheme("dark-pure");
```

## Remplacer les thèmes par défaut {#overriding-default-themes}

Vous pouvez remplacer le thème **clair** en redéfinissant les propriétés CSS personnalisées dans le sélecteur `:root`.

:::info `:root` pseudo-classe
La pseudo-classe CSS `:root` cible l'élément racine du document. En HTML, elle représente l'élément `<html>` et a une spécificité plus élevée que le sélecteur `html` simple.
:::

Exemple :

```css
:root {
  --dwc-color-primary-h: 215;
  --dwc-color-primary-s: 100%;
  --dwc-color-primary-c: 50;
  --dwc-font-size: var(--dwc-font-size-m);
}
```

Pour remplacer les thèmes **sombre** ou **sombre-pur**, utilisez des sélecteurs d'attribut sur l'élément `<html>` :

```css
html[data-app-theme="dark"] {
  --dwc-color-primary-s: 9%;
  --dwc-color-white: hsl(210, 17%, 82%);
}
```

## Création de thèmes personnalisés {#creating-custom-themes}

Vous pouvez définir vos propres thèmes en utilisant le sélecteur `html[data-app-theme='THEME_NAME']`. Les thèmes personnalisés peuvent coexister avec les thèmes par défaut, et vous pouvez passer de l'un à l'autre dynamiquement à l'exécution.

```css
html[data-app-theme="new-theme"] {
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
  --dwc-color-primary-c: 60;
}
```

Ensuite, dans votre application :

```java
@AppTheme("new-theme")
class MyApp extends App {
  // code de l'application
}

// ou de manière programmatique
App.setTheme("new-theme");
```

## Thèmes des composants {#component-themes}

En plus des thèmes au niveau de l'application, les composants webforJ prennent en charge un ensemble de **thèmes de composants** basés sur les palettes de couleurs par défaut : `default`, `primary`, `success`, `warning`, `danger`, `info`, et `gray`.

Chaque composant documente ses thèmes pris en charge dans la section **Style → Thèmes**.
