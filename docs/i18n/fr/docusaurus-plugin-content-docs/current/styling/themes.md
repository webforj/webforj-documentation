---
sidebar_position: 2
title: Themes
_i18n_hash: aa6eb0baae2b881b24c45ae970a079dc
---
webforJ inclut trois thèmes d'application intégrés et prend en charge la définition de vos propres thèmes personnalisés. Les thèmes par défaut sont :

- **light** : Un thème lumineux avec un arrière-plan clair (par défaut).
- **dark** : Un arrière-plan sombre teinté de la couleur primaire.
- **dark-pure** : Un thème sombre entièrement neutre basé sur des tons de gris.

Pour appliquer un thème dans votre application, utilisez l'annotation `@AppTheme` ou la méthode `App.setTheme()`. Le nom du thème doit être l'un des suivants : `system`, `light`, `dark`, `dark-pure`, ou un nom de thème personnalisé.

```java
@AppTheme("dark-pure")
class MyApp extends App {
  // code de l'app
}

// ou de manière programmatique
App.setTheme("dark-pure");
```

## Surcharge des thèmes par défaut {#overriding-default-themes}

Vous pouvez surcharger le thème **light** en redéfinissant les propriétés CSS personnalisées dans le sélecteur `:root`.

:::info `:root` pseudo-classe
La pseudo-classe CSS `:root` cible l'élément racine du document. En HTML, elle représente l'élément `<html>` et a une spécificité plus élevée que le sélecteur `html` ordinaire.
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

Pour surcharger les thèmes **dark** ou **dark-pure**, utilisez des sélecteurs d'attribut sur l'élément `<html>` :

```css
html[data-app-theme="dark"] {
  --dwc-color-primary-s: 9%;
  --dwc-color-white: hsl(210, 17%, 82%);
}
```

## Création de thèmes personnalisés {#creating-custom-themes}

Vous pouvez définir vos propres thèmes en utilisant le sélecteur `html[data-app-theme='THEME_NAME']`. Les thèmes personnalisés peuvent coexister avec les thèmes par défaut, et vous pouvez passer d'un à l'autre de manière dynamique à l'exécution.

```css
html[data-app-theme="new-theme"] {
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
  --dwc-color-primary-c: 60;
}
```

Puis dans votre application :

```java
@AppTheme("new-theme")
class MyApp extends App {
  // code de l'app
}

// ou de manière programmatique
App.setTheme("new-theme");
```

## Thèmes de composants {#component-themes}

En plus des thèmes au niveau de l'application, les composants webforJ prennent en charge un ensemble de **thèmes de composants** basés sur les palettes de couleurs par défaut : `default`, `primary`, `success`, `warning`, `danger`, `info`, et `gray`.

Chaque composant documente ses thèmes pris en charge dans la section **Styling → Themes**.
