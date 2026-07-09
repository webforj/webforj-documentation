---
title: MaskedNumberField
sidebar_position: 10
description: >-
  Format numeric input with the MaskedNumberField using configurable mask
  characters, grouping, decimal separators, and locale settings.
_i18n_hash: 1ce8783919180d45f2f7321c559fc26a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

Le `MaskedNumberField` est un champ de texte conçu pour la saisie numérique structurée. Il garantit que les chiffres sont formatés de manière cohérente en fonction d'un masque défini, ce qui le rend particulièrement utile pour les formulaires financiers, les champs de prix ou toute saisie où la précision et la lisibilité sont importantes.

Ce composant prend en charge le formatage des nombres, la localisation des caractères décimaux / de regroupement et des contraintes de valeur optionnelles telles que des minimums ou des maximums.

<!-- INTRO_END -->

## Basics {#basics}

Le `MaskedNumberField` peut être instancié avec ou sans paramètres. Il prend en charge la définition d'une valeur initiale, d'une étiquette, d'un espace réservé et d'un écouteur d'événements pour réagir aux modifications de valeur.

Cette démo présente un **calculateur de pourboire** qui utilise le `MaskedNumberField` pour une saisie numérique intuitive. Un champ est configuré pour accepter un montant de facture formaté, tandis que l'autre saisit un pourcentage de pourboire en nombre entier. Les deux champs appliquent des masques numériques pour garantir un formatage cohérent et prévisible.

<ComponentDemo
path='/webforj/maskednumberfield'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java']}
height='270px'
/>

## Mask rules {#mask-rules}

Le `MaskedNumberField` utilise une chaîne de masque pour contrôler la façon dont l'entrée numérique est formatée et affichée. Chaque caractère dans le masque définit un comportement de formatage spécifique, permettant un contrôle précis sur l'apparence des nombres.

:::tip Application des masques par programme
Pour formater des nombres avec la même syntaxe de masque en dehors d'un champ, par exemple lors du rendu de données dans un [`Table`](/docs/components/table/overview), utilisez la classe utilitaire [`MaskDecorator`](/docs/advanced/mask-decorator).
:::

### Mask characters {#mask-characters}

| Caractère | Description |
|-----------|-------------|
| `0`       | Toujours remplacé par un chiffre (0–9). |
| `#`       | Supprime les zéros initiaux. Remplacé par le caractère de remplissage à gauche du point décimal. Pour les chiffres fins, remplacé par un espace ou un zéro. Sinon, remplacé par un chiffre. |
| `,`       | Utilisé comme séparateur de regroupement (par exemple, milliers). Remplacé par le caractère de remplissage si aucun chiffre ne le précède. Sinon, affiché comme une virgule. |
| `-`       | Affiche un signe moins (`-`) si le nombre est négatif. Remplacé par le caractère de remplissage si positif. |
| `+`       | Affiche `+` pour les nombres positifs ou `-` pour les nombres négatifs. |
| `$`       | Résulte toujours par un signe dollar. |
| `(`       | Insère une parenthèse gauche `(` pour les valeurs négatives. Remplacé par le caractère de remplissage si positif. |
| `)`       | Insère une parenthèse droite `)` pour les valeurs négatives. Remplacé par le caractère de remplissage si positif. |
| `CR`      | Affiche `CR` pour les nombres négatifs. Affiche deux espaces si le nombre est positif. |
| `DR`      | Affiche `CR` pour les nombres négatifs. Affiche `DR` pour les nombres positifs. |
| `*`       | Insère un astérisque `*`. |
| `.`       | Marque le point décimal. Si aucun chiffre n'apparaît dans la sortie, remplacé par le caractère de remplissage. Après le décimal, les caractères de remplissage sont traités comme des espaces. |
| `B`       | Devenant toujours un espace. Tout autre caractère littéral est affiché tel quel. |

Certains des caractères ci-dessus peuvent apparaître plus d'une fois dans le masque pour le formatage. Ceux-ci incluent `-`, `+`, `$`, et `(`. Si l'un de ces caractères est présent dans le masque, le premier rencontré sera déplacé à la dernière position où un `#` ou `,` a été remplacé par le caractère de remplissage. Si aucune position de ce type n'existe, le caractère double reste à sa place.

:::info Pas de Rounding Automatique
Un masque dans un champ ne fait **PAS** de rounding. Par exemple, lorsque vous placez une valeur telle que `12.34567` dans un champ masqué avec `###0.00`, vous obtiendrez `12.34`.
:::

## Group and decimal separators {#group-and-decimal-separators}

Le `MaskedNumberField` prend en charge la personnalisation des caractères de **regroupement** et **décimaux**, facilitant ainsi l'adaptation du formatage des nombres à différentes conventions locales ou commerciales.

- Le **séparateur de groupe** est utilisé pour séparer visuellement les milliers (par exemple, `1,000,000`).
- Le **séparateur décimal** indique la partie fractionnaire d'un nombre (par exemple, `123.45`).

Ceci est utile dans les applications internationales où différentes régions utilisent des caractères différents (par exemple, `.` contre `,`).

```java
field.setGroupCharacter(".");   // par exemple, 1.000.000
field.setDecimalCharacter(","); // par exemple, 123,45
```

:::tip Comportement par défaut
Par défaut, le `MaskedNumberField` applique des séparateurs de groupe et décimaux en fonction de la locale actuelle de l'application. Vous pouvez les remplacer à tout moment en utilisant les setters fournis.
:::

## Negateable {#negateable}

Le `MaskedNumberField` prend en charge une option pour contrôler si les nombres négatifs sont autorisés.

Par défaut, les valeurs négatives comme `-123.45` sont autorisées. Pour empêcher cela, utilisez `setNegateable(false)` pour restreindre l'entrée uniquement aux valeurs positives.

Cela est utile dans les scénarios commerciaux où des valeurs telles que des quantités, des totaux ou des pourcentages doivent toujours être non négatifs.

```java
field.setNegateable(false);
```

Lorsque `negateable` est défini sur `false`, le champ bloque toute tentative d'entrer un signe moins ou d'autres valeurs négatives.

<ComponentDemo
path='/webforj/maskednumnegatable/'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java']}
height='150px'
/>

## Min and max values {#min-and-max-values}

Le `MaskedNumberField` prend en charge la définition de limites numériques à l'aide de `setMin()` et `setMax()`. Ces contraintes aident à garantir que l'entrée utilisateur reste dans une plage valide et attendue.

- **Valeur Min**
  Utilisez `setMin()` pour définir le plus bas nombre acceptable :

  ```java
  field.setMin(10.0); // Valeur minimum : 10
  ```

  Si l'utilisateur saisit un nombre en dessous de ce seuil, il sera considéré comme invalide.

- **Valeur Max**
  Utilisez `setMax()` pour définir le plus haut nombre acceptable :

  ```java
  field.setMax(100.0); // Valeur maximum : 100
  ```

  Les valeurs au-dessus de cette limite seront signalées comme invalides.

## Restoring the value {#restoring-the-value}

Le `MaskedNumberField` prend en charge une fonction de restauration qui réinitialise la valeur du champ à un état prédéfini. Cela peut être utile lorsque les utilisateurs doivent annuler des changements, revenir sur des modifications accidentelles ou retourner à une valeur par défaut connue.

Pour activer ce comportement, définissez la valeur cible à l'aide de `setRestoreValue()`. Lorsque cela est nécessaire, le champ peut être réinitialisé par programme à l'aide de `restoreValue()`.

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### Ways to restore the value {#ways-to-restore-the-value}

- **Par programme** en utilisant `restoreValue()`
- **Via le clavier**, en appuyant sur <kbd>ESC</kbd> (c'est la touche de restauration par défaut, sauf si elle est remplacée)

La valeur de restauration doit être définie explicitement. Si elle n'est pas définie, la fonction ne rétablira pas le champ.

<ComponentDemo
path='/webforj/maskednumrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java']}
height='150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

Le `MaskedNumberFieldSpinner` étend le [`MaskedNumberField`](#basics) en ajoutant des commandes de spinner qui permettent aux utilisateurs d'augmenter ou de diminuer la valeur à l'aide de boutons de pas ou de flèches. Ceci est idéal pour des entrées comme des quantités, des ajustements de prix, des contrôles de notation ou toute situation où les utilisateurs effectuent des modifications incrémentales.

<ComponentDemo
path='/webforj/maskednumspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java']}
height='120px'
/>

### Key features {#key-features}

- **Incréments de pas**
  Utilisez `setStep()` pour définir de combien la valeur doit changer à chaque rotation :

  ```java
  spinner.setStep(5.0); // Chaque rotation ajoute ou soustrait 5
  ```

- **Contrôles interactifs**
  Les utilisateurs peuvent cliquer sur les boutons du spinner ou utiliser l'entrée par clavier pour ajuster la valeur.

- **Toutes les fonctionnalités du MaskedNumberField**
  Prend entièrement en charge les masques, le formatage, les caractères de regroupement / décimaux, les contraintes min / max et la logique de restauration.

## Styling {#styling}

<TableBuilder name="MaskedNumberField" />
