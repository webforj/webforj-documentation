---
sidebar_position: 2
title: Themes
_i18n_hash: afbc96c2eb0da1c5e0eb2e24a69827c2
---
webforJ inclut trois thèmes d'application intégrés et prend en charge la définition de vos propres thèmes personnalisés. Les thèmes par défaut sont :

- **light** : Un thème clair avec un fond léger (par défaut).
- **dark** : Un fond sombre teinté de la couleur principale.
- **dark-pure** : Un thème sombre entièrement neutre basé sur des tons de gris.

Pour appliquer un thème dans votre application, utilisez l'annotation `@AppTheme` ou la méthode `App.setTheme()`. Le nom du thème doit être l'un de : `system`, `light`, `dark`, `dark-pure`, ou un nom de thème personnalisé.

```java
@AppTheme("dark-pure")
class MyApp extends App {
  // code de l'application
}

// ou de manière programmatique
App.setTheme("dark-pure");
```

## Remplacement des thèmes par défaut {#overriding-default-themes}

Vous pouvez remplacer le thème **light** en redéfinissant les propriétés CSS personnalisées dans le sélecteur `:root`.

:::info Pseudo-classe `:root`
La pseudo-classe CSS `:root` cible l'élément racine du document. En HTML, elle représente l'élément `<html>` et a une spécificité plus élevée que le sélecteur `html` classique.
:::

Exemple :

```css
:root {
  --dwc-color-primary-h: 215;
  --dwc-color-primary-s: 100%;
  --dwc-font-size: var(--dwc-font-size-l);
}
```

Pour remplacer les thèmes **dark** ou **dark-pure**, utilisez des sélecteurs d'attributs sur l'élément `<html>` :

```css
html[data-app-theme="dark"] {
  --dwc-color-primary-s: 80%;
}
```

## Création de thèmes personnalisés {#creating-custom-themes}

Vous pouvez définir vos propres thèmes en utilisant le sélecteur `html[data-app-theme='THEME_NAME']`. Les thèmes personnalisés peuvent coexister avec les thèmes par défaut, et vous pouvez basculer entre eux dynamiquement à l'exécution.

```css
html[data-app-theme="new-theme"] {
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
}
```

Pour rendre un thème personnalisé sombre, définissez `--dwc-dark-mode: 1` et `color-scheme: dark` :

```css
html[data-app-theme="new-dark-theme"] {
  --dwc-dark-mode: 1;
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
  color-scheme: dark;
}
```

Puis dans votre application :

```java
@AppTheme("new-theme")
class MyApp extends App {
  // code de l'application
}

// ou de manière programmatique
App.setTheme("new-theme");
```

## Thèmes de composants {#component-themes}

En plus des thèmes au niveau de l'application, les composants webforJ prennent en charge un ensemble de **thèmes de composants** basés sur les palettes de couleurs par défaut : `default`, `primary`, `success`, `warning`, `danger`, `info`, et `gray`.

Chaque composant documente ses thèmes pris en charge dans la section **Styling → Themes**.
