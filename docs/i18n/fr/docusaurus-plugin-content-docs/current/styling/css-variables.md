---
sidebar_position: 1
title: CSS Variables
_i18n_hash: b753c1b13cfcc45f72d6712e980ef952
---
Les variables CSS jouent un rôle central dans la personnalisation de l'apparence des composants webforJ. Ces variables stockent des valeurs réutilisables telles que des couleurs, des tailles de police et des espacements, qui peuvent être appliquées de manière cohérente dans votre application.

Contrairement aux approches traditionnelles qui s'appuyaient sur des préprocesseurs CSS comme [SASS](https://sass-lang.com/) ou [LESS](https://lesscss.org/), les variables CSS permettent un **style dynamique à l'exécution**. Elles réduisent la répétition, améliorent la maintenabilité et rendent les feuilles de style plus faciles à lire et à gérer.

## Définir des variables CSS {#defining-css-variables}

Les variables CSS sont définies en utilisant un préfixe double tiret (`--`), et peuvent être limitées à n'importe quel sélecteur CSS. Cependant, la pratique la plus courante est de les définir dans le sélecteur `:root`, qui les limite globalement.

```css
:root {
  --app-background: orange;
}
```

:::tip La pseudo-classe `:root`
La pseudo-classe [`:root`](https://developer.mozilla.org/en-US/docs/Web/CSS/:root) cible l'élément racine du document—typiquement `<html>` en HTML. Elle se comporte comme `html`, mais avec une spécificité plus élevée.
:::

Les variables CSS peuvent contenir n'importe quelle chaîne, pas seulement des valeurs CSS valides. Cette flexibilité est particulièrement utile lors de l'utilisation de JavaScript.

```css
html {
  --app-title: webforJ;
}
```

## Variables spécifiques au composant {#component-specific-variables}

Pour définir ou remplacer une variable pour un composant spécifique, déclarez-la dans le sélecteur du composant :

```css
dwc-button {
  --dwc-button-font-weight: 400;
}
```

:::tip Référence pour le style spécifique au composant
Chaque composant webforJ prend en charge un ensemble spécifique de variables CSS qui contrôlent son apparence. Celles-ci sont documentées sous la section **Styling > Propriétés CSS** pour chaque composant. 
:::


## Utiliser des variables CSS {#using-css-variables}

Utilisez la fonction [`var()`](https://developer.mozilla.org/en-US/docs/Web/CSS/var()) pour appliquer la valeur d'une variable dans vos styles :

```css
.panel {
  background-color: var(--app-background);
}
```

Vous pouvez également spécifier une valeur de remplacement au cas où la variable ne serait pas définie :

```css
.frame {
  background-color: var(--app-background, red);
}
```

## Manipuler des variables avec webforJ {#manipulating-variables-with-webforj}

Les variables CSS peuvent être mises à jour dynamiquement via l'API webforJ, permettant un style en temps réel :

```java
// Définir une variable CSS
button.setStyle('--dwc-button-font-weight', '400');
```

:::tip Manipuler des variables CSS avec JavaScript
webforJ vous permet d'exécuter JavaScript côté client en utilisant l'API Page ou Element. Cela signifie que vous pouvez manipuler dynamiquement les variables CSS à l'exécution, tout comme vous le feriez dans des applications web standard.

```javascript
// Définir une variable CSS
const el = document.querySelector('dwc-button');
el.style.setProperty('--dwc-button-font-weight', '400');

// Obtenir une variable CSS
const value = el.style.getPropertyValue('--dwc-font-size-m');
```
:::

## Ressources supplémentaires {#additional-resources}

- [Utiliser des propriétés personnalisées CSS (MDN)](https://developer.mozilla.org/en-US/docs/Web/CSS/Using_CSS_custom_properties)  
- [Un guide complet des propriétés personnalisées (CSS-Tricks)](https://css-tricks.com/a-complete-guide-to-custom-properties/)
