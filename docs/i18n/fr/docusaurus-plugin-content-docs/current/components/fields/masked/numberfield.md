---
title: MaskedNumberField
sidebar_position: 10
description: >-
  Format numeric input with the MaskedNumberField using configurable mask
  characters, grouping, decimal separators, and locale settings.
_i18n_hash: f247d28107239e0db4a24823c8a1ef60
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

Le `MaskedNumberField` est un champ de texte conçu pour formater les saisies numériques de manière cohérente, en fonction d'un masque défini. Il est utile pour les formulaires financiers, les champs de prix, ou toute entrée où la précision et la lisibilité sont importantes.

Ce composant peut être instancié avec ou sans paramètres. Il prend en charge le formatage des nombres, la localisation des caractères décimaux/de groupement, et des contraintes de valeur optionnelles comme des minimums ou maximums. Il permet également de définir une valeur initiale, une étiquette, un espace réservé et un écouteur d'événements pour réagir aux changements de valeur.

<!-- INTRO_END -->

L'exemple ci-dessous présente un **Calculateur de pourboire** qui utilise `MaskedNumberField` pour une saisie numérique intuitive. Un champ est configuré pour accepter un montant de facture formaté, tandis que l'autre capture un pourcentage de pourboire sous forme de nombre entier.

<ComponentDemo
path='/webforj/maskednumberfield'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java']}
height='270px'
/>

## Règles de masque {#mask-rules}

Le `MaskedNumberField` utilise une chaîne de masque pour contrôler comment la saisie numérique est formatée et affichée. Chaque caractère du masque définit un comportement de formatage spécifique, permettant un contrôle précis sur l'apparence des nombres.

:::tip Application de masques par programmation
Pour formater les nombres avec la même syntaxe de masque en dehors d'un champ, par exemple lors du rendu de données dans un [`Table`](/docs/components/table/overview), utilisez la classe utilitaire [`MaskDecorator`](/docs/advanced/mask-decorator).
:::

### Caractères de masque {#mask-characters}

| Caractère | Description |
|-----------|-------------|
| `0`       | Toujours remplacé par un chiffre (0–9). |
| `#`       | Supprime les zéros non significatifs. Remplace par le caractère de remplissage à gauche de la virgule. Pour les chiffres suivants, remplacé par un espace ou un zéro. Sinon, remplacé par un chiffre. |
| `,`       | Utilisé comme séparateur de groupement (ex. milliers). Remplacé par le caractère de remplissage si aucun chiffre ne le précède. Sinon, affiché comme une virgule. |
| `-`       | Affiche un signe moins (`-`) si le nombre est négatif. Remplacé par le caractère de remplissage si positif. |
| `+`       | Affiche `+` pour les nombres positifs ou `-` pour les nombres négatifs. |
| `$`       | Résulte toujours en un signe dollar. |
| `(`       | Insère une parenthèse gauche `(` pour les valeurs négatives. Remplacé par le caractère de remplissage si positif. |
| `)`       | Insère une parenthèse droite `)` pour les valeurs négatives. Remplacé par le caractère de remplissage si positif. |
| `CR`      | Affiche `CR` pour les nombres négatifs. Affiche deux espaces si le nombre est positif. |
| `DR`      | Affiche `CR` pour les nombres négatifs. Affiche `DR` pour les nombres positifs. |
| `*`       | Insère un astérisque `*`. |
| `.`       | Marque le point décimal. Si aucun chiffre n'apparaît dans la sortie, remplacé par le caractère de remplissage. Après le décimal, les caractères de remplissage sont traités comme des espaces. |
| `B`       | Devient toujours un espace. Tout autre caractère littéral est affiché tel quel. |

Certains des caractères ci-dessus peuvent apparaître plus d'une fois dans le masque pour le formatage. Ceci inclut `-`, `+`, `$`, et `(`. Si l'un de ces caractères est présent dans le masque, le premier rencontré sera déplacé à la dernière position où un `#` ou une `,` a été remplacé par le caractère de remplissage. Si aucune position de ce type n'existe, le caractère double reste à sa place.

:::info Pas d'arrondi automatique
Un masque dans un champ ne fait **PAS** d'arrondi. Par exemple, lorsque vous placez une valeur telle que `12.34567` dans un champ masqué avec `###0.00`, vous obtiendrez `12.34`.
:::

## Séparateurs de groupe et décimaux {#group-and-decimal-separators}

Le `MaskedNumberField` prend en charge la personnalisation des caractères de **groupement** et **décimaux**, facilitant ainsi l'adaptation du formatage des nombres aux différentes locales ou conventions commerciales.

- Le **séparateur de groupe** est utilisé pour séparer visuellement les milliers (ex. `1,000,000`).
- Le **séparateur décimal** indique la partie fractionnaire d'un nombre (ex. `123.45`).

Cela est utile dans les applications internationales où différentes régions utilisent différents caractères (ex. `.` vs `,`).

```java
field.setGroupCharacter(".");   // ex. 1.000.000
field.setDecimalCharacter(","); // ex. 123,45
```

:::tip Comportement par défaut
Par défaut, `MaskedNumberField` applique les séparateurs de groupe et décimaux en fonction de la locale actuelle de l'application. Vous pouvez les remplacer à tout moment en utilisant les setters fournis.
:::

## Négatif {#negateable}

Le `MaskedNumberField` prend en charge une option pour contrôler si les nombres négatifs sont autorisés.

Par défaut, des valeurs négatives comme `-123.45` sont acceptées. Pour empêcher cela, utilisez `setNegateable(false)` pour restreindre l'entrée aux valeurs positives uniquement.

Cela est utile dans les scénarios commerciaux où des valeurs comme les quantités, les totaux ou les pourcentages doivent toujours être non négatifs.

```java
field.setNegateable(false);
```

Lorsque `negatable` est défini sur `false`, le champ bloque toute tentative d'entrer un signe moins ou d'autres valeurs négatives.

<ComponentDemo
path='/webforj/maskednumnegatable/'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java']}
height='150px'
/>

## Valeurs min et max {#min-and-max-values}

Le `MaskedNumberField` prend en charge la définition de limites numériques à l'aide de `setMin()` et `setMax()`. Ces contraintes aident à garantir que les saisies des utilisateurs restent dans une plage valide et attendue.

- **Valeur minimale**
  Utilisez `setMin()` pour définir le nombre le plus bas acceptable :

  ```java
  field.setMin(10.0); // Valeur minimale : 10
  ```

  Si l'utilisateur saisit un nombre en dessous de ce seuil, il sera considéré comme invalide.

- **Valeur maximale**
  Utilisez `setMax()` pour définir le nombre le plus élevé acceptable :

  ```java
  field.setMax(100.0); // Valeur maximale : 100
  ```

  Les valeurs au-dessus de cette limite seront marquées comme invalides.

## Restauration de la valeur {#restoring-the-value}

Le `MaskedNumberField` prend en charge une fonction de restauration qui réinitialise la valeur du champ à un état prédéfini. Cela peut être utile lorsque les utilisateurs ont besoin d'annuler des modifications, de revenir sur des modifications accidentelles ou de retourner à une valeur par défaut connue.

Pour restaurer une valeur spécifique, définissez-la en utilisant `setRestoreValue()`. Lorsque cela est nécessaire, le champ peut être réinitialisé par programme à l'aide de `restoreValue()`.

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### Façons de restaurer la valeur {#ways-to-restore-the-value}

- **Par programme** en utilisant `restoreValue()`
- **Via le clavier**, en appuyant sur <kbd>ESC</kbd> (c'est la touche de restauration par défaut, sauf si elle est remplacée)

Si aucune valeur de restauration n'est définie, à la fois `restoreValue()` et <kbd>ESC</kbd> définissent la valeur du champ sur `null`.

<ComponentDemo
path='/webforj/maskednumrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java']}
height='150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

Le `MaskedNumberFieldSpinner` étend le `MaskedNumberField` en ajoutant des contrôles de sélection qui permettent aux utilisateurs d'augmenter ou de diminuer la valeur à l'aide de boutons ou des flèches du clavier. Cela est idéal pour des entrées comme les quantités, les ajustements de prix, les contrôles d'évaluation, ou toute situation où les utilisateurs effectuent des changements incrémentaux.

<ComponentDemo
path='/webforj/maskednumspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java']}
height='120px'
/>

### Fonctionnalités clés {#key-features}

- **Incréments par étapes**
  Utilisez `setStep()` pour définir combien la valeur doit changer à chaque rotation :

  ```java
  spinner.setStep(5.0); // Chaque rotation ajoute ou soustrait 5
  ```

- **Contrôles interactifs**
  Les utilisateurs peuvent cliquer sur des boutons de sélection ou utiliser l'entrée au clavier pour ajuster la valeur.

- **Toutes les fonctionnalités de MaskedNumberField**
  Prend entièrement en charge les masques, le formatage, le groupement/de caractères décimaux, les contraintes min/max, et la logique de restauration.

## Style {#styling}

<TableBuilder name="MaskedNumberField" />
