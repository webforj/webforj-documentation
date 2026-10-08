---
title: MaskedNumberField
sidebar_position: 10
description: >-
  Format numeric input with the MaskedNumberField using configurable mask
  characters, grouping, decimal separators, and locale settings.
_i18n_hash: bba6de4e793a65cc887af236d206bb46
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

Le `MaskedNumberField` est un champ de texte conçu pour formater les saisies numériques de manière cohérente, en fonction d'un masque défini. Il est utile pour les formulaires financiers, les champs de prix ou toute saisie où la précision et la lisibilité comptent.

Ce composant peut être instancié avec ou sans paramètres. Il prend en charge le formatage des nombres, la localisation des caractères décimaux/groupes, et des contraintes de valeur optionnelles comme les minimums ou maximums. Il permet également de définir une valeur initiale, une étiquette, un espace réservé, et un écouteur d'événements pour réagir aux changements de valeur.

<!-- INTRO_END -->

L'exemple ci-dessous présente un **Calculateur de Pourboire** qui utilise `MaskedNumberField` pour une saisie numérique intuitive. Un champ est configuré pour accepter un montant de facture formaté, tandis que l'autre capture un pourcentage de pourboire en nombre entier.

<ComponentDemo
path='/webforj/maskednumberfield'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java']}
height='270px'
/>

## Règles de masque {#mask-rules}

Le `MaskedNumberField` utilise une chaîne de masque pour contrôler la façon dont l'entrée numérique est formatée et affichée. Chaque caractère dans le masque définit un comportement de formatage spécifique, permettant un contrôle précis sur l'apparence des chiffres.

:::tip Application des masques par programmation
Pour formater des nombres avec la même syntaxe de masque en dehors d'un champ, par exemple lors du rendu de données dans un [`Table`](/docs/components/table/overview), utilisez la classe utilitaire [`MaskDecorator`](/docs/advanced/mask-decorator).
:::

### Caractères de masque {#mask-characters}

| Caractère | Description |
|-----------|-------------|
| `0`       | Toujours remplacé par un chiffre (0–9). |
| `#`       | Supprime les zéros à gauche. Remplacé par le caractère de remplissage à gauche du point décimal. Pour les chiffres suivants, remplacé par un espace ou un zéro. Sinon, remplacé par un chiffre. |
| `,`       | Utilisé comme séparateur de groupes (par exemple, milliers). Remplacé par le caractère de remplissage si aucun chiffre ne le précède. Sinon, affiché comme une virgule. |
| `-`       | Affiche un signe moins (`-`) si le nombre est négatif. Remplacé par le caractère de remplissage s'il est positif. |
| `+`       | Affiche `+` pour les nombres positifs ou `-` pour les nombres négatifs. |
| `$`       | Donne toujours un signe dollar. |
| `(`       | Insère une parenthèse gauche `(` pour les valeurs négatives. Remplacé par le caractère de remplissage s'il est positif. |
| `)`       | Insère une parenthèse droite `)` pour les valeurs négatives. Remplacé par le caractère de remplissage s'il est positif. |
| `CR`      | Affiche `CR` pour les nombres négatifs. Affiche deux espaces si le nombre est positif. |
| `DR`      | Affiche `CR` pour les nombres négatifs. Affiche `DR` pour les nombres positifs. |
| `*`       | Insère un astérisque `*`. |
| `.`       | Marque le point décimal. Si aucun chiffre n'apparaît dans la sortie, remplacé par le caractère de remplissage. Après le décimal, les caractères de remplissage sont traités comme des espaces. |
| `B`       | Devenu toujours un espace. Tout autre caractère littéral est affiché tel quel. |

Certains des caractères ci-dessus peuvent apparaître plus d'une fois dans le masque pour le formatage. Ceux-ci incluent `-`, `+`, `$`, et `(`. Si l'un de ces caractères est présent dans le masque, le premier rencontré sera déplacé à la dernière position où un `#` ou `,` a été remplacé par le caractère de remplissage. Si aucune position de ce type n’existe, le caractère double reste à sa place.

:::info Pas d'arrondi automatique
Un masque dans un champ ne **rondit pas**. Par exemple, lorsque vous placez une valeur telle que `12.34567` dans un champ masqué avec `###0.00`, vous obtiendrez `12.34`.
:::

## Séparateurs de groupes et décimaux {#group-and-decimal-separators}

Le `MaskedNumberField` prend en charge la personnalisation des caractères de **groupe** et **décimal**, facilitant l'adaptation du formatage des nombres à différents paramètres régionaux ou conventions commerciales.

- Le **séparateur de groupe** est utilisé pour séparer visuellement les milliers (par exemple, `1 000 000`).
- Le **séparateur décimal** indique la partie fractionnaire d'un nombre (par exemple, `123,45`).

Cela est utile dans les applications internationales où différentes régions utilisent différents caractères (par exemple, `.` contre `,`).

```java
field.setGroupCharacter(".");   // par exemple 1.000.000
field.setDecimalCharacter(","); // par exemple 123,45
```

:::tip Comportement par défaut
Par défaut, `MaskedNumberField` applique les séparateurs de groupes et décimaux en fonction de la langue du programme en cours. Vous pouvez les remplacer à tout moment en utilisant les setters fournis.
:::

## Négatable {#negateable}

Le `MaskedNumberField` prend en charge une option permettant de contrôler si les nombres négatifs sont autorisés.

Par défaut, les valeurs négatives comme `-123,45` sont autorisées. Pour empêcher cela, utilisez `setNegateable(false)` pour restreindre les saisies uniquement aux valeurs positives.

Cela est utile dans des situations commerciales où des valeurs comme les quantités, les totaux ou les pourcentages doivent toujours être non négatives.

```java
field.setNegateable(false);
```

Lorsque `negatable` est défini sur `false`, le champ bloque toute tentative de saisie d'un signe moins ou d'autres valeurs négatives.

<ComponentDemo
path='/webforj/maskednumnegatable/'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java']}
height='150px'
/>

## Valeurs min et max {#min-and-max-values}

Le `MaskedNumberField` prend en charge la définition de limites numériques à l'aide de `setMin()` et `setMax()`. Ces contraintes aident à garantir que la saisie de l'utilisateur reste dans une plage valide et attendue.

- **Valeur minimum**
  Utilisez `setMin()` pour définir le nombre le plus bas acceptable :

  ```java
  field.setMin(10.0); // Valeur minimum : 10
  ```

  Si l'utilisateur saisit un nombre inférieur à ce seuil, il sera considéré comme invalide.

- **Valeur maximum**
  Utilisez `setMax()` pour définir le nombre le plus élevé acceptable :

  ```java
  field.setMax(100.0); // Valeur maximum : 100
  ```

  Les valeurs supérieures à cette limite seront signalées comme invalides.

## Rétablissement de la valeur {#restoring-the-value}

Le `MaskedNumberField` prend en charge une fonctionnalité de restauration qui réinitialise la valeur du champ à un état prédéfini. Cela peut être utile lorsque les utilisateurs ont besoin d'annuler des modifications, de revenir à des modifications accidentelles ou de retrouver une valeur par défaut connue.

Pour activer ce comportement, définissez la valeur cible à l'aide de `setRestoreValue()`. Lorsque nécessaire, le champ peut être réinitialisé par programmation à l'aide de `restoreValue()`.

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### Façons de restaurer la valeur {#ways-to-restore-the-value}

- **Par programmation** en utilisant `restoreValue()`
- **Via le clavier**, en appuyant sur <kbd>ESC</kbd> (c'est la touche de restauration par défaut, sauf si elle est remplacée)

La valeur de restauration doit être explicitement définie. Si elle n'est pas définie, la fonctionnalité ne rétablira pas le champ.

<ComponentDemo
path='/webforj/maskednumrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java']}
height='150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

Le `MaskedNumberFieldSpinner` étend le `MaskedNumberField` en ajoutant des contrôles de spinner qui permettent aux utilisateurs d'augmenter ou de diminuer la valeur à l'aide de boutons d'étape ou de touches fléchées. Cela est idéal pour les saisies comme les quantités, les ajustements de prix, les contrôles de notation, ou toute situation où les utilisateurs apportent des changements progressifs.

<ComponentDemo
path='/webforj/maskednumspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java']}
height='120px'
/>

### Fonctionnalités clés {#key-features}

- **Incréments par étape**
  Utilisez `setStep()` pour définir combien la valeur doit changer à chaque rotation :

  ```java
  spinner.setStep(5.0); // Chaque rotation ajoute ou soustrait 5
  ```

- **Contrôles interactifs**
  Les utilisateurs peuvent cliquer sur les boutons de spinner ou utiliser des saisies au clavier pour ajuster la valeur.

- **Toutes les caractéristiques du MaskedNumberField**
  Prend pleinement en charge les masques, le formatage, les caractères de groupe/décimal, les contraintes min/max, et la logique de restauration.

## Style {#styling}

<TableBuilder name="MaskedNumberField" />
