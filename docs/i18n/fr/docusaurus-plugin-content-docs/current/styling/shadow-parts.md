---
sidebar_position: 2
title: Shadow Parts
_i18n_hash: 8dbd7759364573b73d0b1b00c6d7e219
---
CSS **Shadow Parts** offrent aux développeurs un moyen de styliser des éléments à l'intérieur du shadow DOM d'un composant depuis l'extérieur, tout en préservant l'encapsulation.

## Introduction {#introduction}

Les composants webforJ sont construits en utilisant [Web Components](https://developer.mozilla.org/en-US/docs/Web/Web_Components), qui s'appuient sur le [Shadow DOM](https://developer.mozilla.org/en-US/docs/Web/Web_Components/Using_shadow_DOM) pour encapsuler la structure interne et les styles d'un composant.

:::tip Web Components
Les Web Components sont un ensemble de technologies qui vous permettent de créer des éléments personnalisés réutilisables et encapsulés pour une utilisation dans des applications web.
:::

Le **Shadow DOM** empêche les styles et le balisage internes de s'échapper du composant ou d'être affectés par des styles externes. Cette encapsulation garantit que les composants restent autonomes, réduisant le risque de conflits de style.

:::tip  Encapsulation des Web Components
L'encapsulation est un avantage clé des Web Components. En gardant la structure, les styles et le comportement d'un composant séparés du reste de votre application, vous évitez les conflits et maintenez un code propre et maintenable.
:::

Cependant, en raison de cette encapsulation, vous **ne pouvez pas styliser directement** les éléments à l'intérieur d'un shadow DOM en utilisant des sélecteurs CSS standard.

Par exemple, le composant `dwc-button` rend la structure suivante :

```html {2}
<dwc-button>
  #shadow-root (open)
  <span class="control__prefix">...</span>
  <span class="control__label">Bouton</span>
  <span class="control__suffix">...</span>
  ...
</dwc-button>
```

Si vous essayez de styliser le `label` comme ceci :

```css
/* Ne fonctionne PAS */
dwc-button .control__label {
  color: pink;
}
```

cela n'aura aucun effet, car l'élément `.control__label` se trouve à l'intérieur du shadow root.

C'est ici que les **CSS Shadow Parts** entrent en jeu.

## Styliser avec les shadow parts {#styling-with-shadow-parts}

Les shadow parts permettent aux feuilles de style externes de cibler des éléments spécifiques à l'intérieur d'un arbre shadow, mais **uniquement si** ces éléments sont explicitement marqués comme "exposés" par le composant.

### Comment les parties sont exposées {#how-parts-are-exposed}

Pour exposer un élément pour le stylisme externe, l'auteur du composant doit lui attribuer un attribut `part` à l'intérieur du shadow DOM.

Tous les composants webforJ exposent automatiquement les parties pertinentes pour le stylisme. Vous pouvez trouver la liste des parties supportées dans la section **Stylisation > Shadow parts** de la documentation de chaque composant.

Par exemple, le composant `dwc-button` expose des parties comme `prefix`, `label` et `suffix` :

```html
<dwc-button>
  #shadow-root (open)
  <span part="prefix" class="control__prefix">...</span>
  <span part="label" class="control__label">Bouton</span>
  <span part="suffix" class="control__suffix">...</span>
</dwc-button>
```

Une fois exposées, ces parties peuvent être stylisées depuis l'extérieur du composant en utilisant le pseudo-élément [`::part()`](https://developer.mozilla.org/en-US/docs/Web/CSS/::part).

### Le pseudo-élément `::part()` {#the-part-pseudo-element}

Le sélecteur `::part()` vous permet d'appliquer des styles aux éléments dans le shadow DOM qui ont été marqués avec un attribut `part`.

Par exemple, pour changer la couleur de la partie `label` dans un `dwc-button` :

```css
dwc-button::part(label) {
  color: red;
}
```

Vous pouvez combiner `::part()` avec d'autres sélecteurs, comme `:hover` :

```css
dwc-button::part(label):hover {
  color: pink;
}
```

:::warning Limitations du sélecteur ::part()
Vous ne pouvez pas sélectionner *à l'intérieur* d'une partie shadow. Ce qui suit **ne fonctionnera pas** :

```css
/* Ne fonctionne PAS */
dwc-button::part(label) span {
  /* Le CSS va ici */
}
```
:::
