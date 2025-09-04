---
sidebar_position: 2
title: Shadow Parts
_i18n_hash: bad90a86a29eaf34485d5ee9150aacb3
---
CSS **Shadow Parts** offre aux développeurs un moyen de styliser les éléments à l'intérieur du DOM d'ombre d'un composant depuis l'extérieur, tout en préservant l'encapsulation.

## Introduction {#introduction}

Les composants webforJ sont construits en utilisant [Web Components](https://developer.mozilla.org/en-US/docs/Web/Web_Components), qui s'appuient sur le [Shadow DOM](https://developer.mozilla.org/en-US/docs/Web/Web_Components/Using_shadow_DOM) pour encapsuler la structure et les styles internes d'un composant.

:::tip Web Components
Les Web Components sont un ensemble de technologies qui vous permettent de créer des éléments personnalisés réutilisables et encapsulés à utiliser dans des applications web.
:::

Le **Shadow DOM** empêche les styles et le balisage internes de fuir hors du composant ou d'être affectés par des styles externes. Cette encapsulation garantit que les composants restent autonomes, réduisant le risque de conflits de styles.

:::tip  Encapsulation des Web Components
L'encapsulation est un avantage clé des Web Components. En gardant la structure, les styles et le comportement d’un composant séparés du reste de votre application, vous évitez les conflits et maintenez un code propre et maintenable.
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

cela n'aura aucun effet, car l'élément `.control__label` vit à l'intérieur du shadow root.

C'est là que les **CSS Shadow Parts** interviennent.

## Stylisation avec des parties d'ombre {#styling-with-shadow-parts}

Les parties d'ombre permettent aux feuilles de style externes de cibler des éléments spécifiques à l'intérieur d'un arbre d'ombre, mais **seulement si** ces éléments sont explicitement marqués comme "exposés" par le composant.

### Comment les parties sont exposées {#how-parts-are-exposed}

Pour exposer un élément pour un style externe, l'auteur du composant doit lui attribuer un attribut `part` à l'intérieur du shadow DOM.

Tous les composants webforJ exposent automatiquement les parties pertinentes pour le stylage. Vous pouvez trouver la liste des parties prises en charge dans la section **Stylisation > Parties d'ombre** de la documentation de chaque composant.

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

Le sélecteur `::part()` vous permet d'appliquer des styles à des éléments à l'intérieur du shadow DOM qui ont été marqués avec un attribut `part`.

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
Vous ne pouvez pas sélectionner *à l'intérieur* d'une partie d'ombre. Ce qui suit **ne fonctionnera pas** :

```css
/* Ne fonctionne PAS */
dwc-button::part(label) span {
  /* Le CSS va ici */
}
```
:::
