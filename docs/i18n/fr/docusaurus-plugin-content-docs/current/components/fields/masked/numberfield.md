---
title: MaskedNumberField
sidebar_position: 10
_i18n_hash: a2b0a5275077beea1c053993d47aa861
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

Le `MaskedNumberField` est un champ de texte conçu pour la saisie numérique structurée. Il garantit que les nombres sont formatés de manière cohérente selon un masque défini, ce qui est particulièrement utile pour les formulaires financiers, les champs de prix ou toute saisie où la précision et la lisibilité comptent.

Ce composant prend en charge le formatage des nombres, la localisation des caractères décimaux et de regroupement, ainsi que des contraintes de valeurs optionnelles telles que les minimums ou maximums.

<!-- INTRO_END -->

## Basics {#basics}

Le `MaskedNumberField` peut être instancié avec ou sans paramètres. Il prend en charge la définition d'une valeur initiale, d'une étiquette, d'un espace réservé et d'un écouteur d'événements pour réagir aux changements de valeur.

Cette démo présente un **Calculateur de pourboires** qui utilise `MaskedNumberField` pour une saisie numérique intuitive. Un champ est configuré pour accepter un montant de facture formaté, tandis que l'autre capture un pourcentage de pourboire en nombre entier. Les deux champs appliquent des masques numériques pour garantir un formatage cohérent et prévisible.

<ComponentDemo
path='/webforj/maskednumberfield'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java']}
height='270px'
/>

## Mask rules {#mask-rules}

Le `MaskedNumberField` utilise une chaîne de masques pour contrôler la façon dont l'entrée numérique est formatée et affichée. 
Chaque caractère du masque définit un comportement de formatage spécifique, permettant un contrôle précis sur l'apparence des nombres.

:::tip Application des masques par programmation
Pour formater des nombres avec la même syntaxe de masque en dehors d'un champ, par exemple lors du rendu de données dans un [`Table`](/docs/components/table/overview), utilisez la classe utilitaire [`MaskDecorator`](/docs/advanced/mask-decorator).
:::

### Mask characters {#mask-characters}

| Caractère | Description |
|-----------|-------------|
| `0`       | Toujours remplacé par un chiffre (0–9). |
| `#`       | Supprime les zéros non significatifs. Remplacé par le caractère de remplissage à gauche du point décimal. Pour les chiffres de fin, remplacé par un espace ou un zéro. Sinon, remplacé par un chiffre. |
| `,`       | Utilisé comme séparateur de regroupement (par exemple, milliers). Remplacé par le caractère de remplissage si aucun chiffre ne le précède. Sinon, affiché comme une virgule. |
| `-`       | Affiche un signe moins (`-`) si le nombre est négatif. Remplacé par le caractère de remplissage si positif. |
| `+`       | Affiche `+` pour les nombres positifs ou `-` pour les nombres négatifs. |
| `$`       | Donne toujours un signe dollar. |
| `(`       | Insère une parenthèse gauche `(` pour les valeurs négatives. Remplacé par le caractère de remplissage si positif. |
| `)`       | Insère une parenthèse droite `)` pour les valeurs négatives. Remplacé par le caractère de remplissage si positif. |
| `CR`      | Affiche `CR` pour les nombres négatifs. Affiche deux espaces si le nombre est positif. |
| `DR`      | Affiche `CR` pour les nombres négatifs. Affiche `DR` pour les nombres positifs. |
| `*`       | Insère un astérisque `*`. |
| `.`       | Marque le point décimal. Si aucun chiffre n'apparaît dans la sortie, remplacé par le caractère de remplissage. Après le décimal, les caractères de remplissage sont traités comme des espaces. |
| `B`       | Devenu toujours un espace. Tout autre caractère littéral est affiché tel quel. |

Certains des caractères ci-dessus peuvent apparaître plusieurs fois dans le masque pour le formatage. Ceux-ci incluent `-`, `+`, `$`, et
`(`. Si l'un de ces caractères est présent dans le masque, le premier rencontré sera déplacé à la dernière position où un `#` ou `,` a été remplacé par le caractère de remplissage. S'il n'existe pas de telle position, le caractère double reste à sa place.

:::info Pas de Rounding Automatique
Un masque dans un champ ne fait **PAS** de cercle. Par exemple, lorsque vous placez une valeur telle que `12.34567`
dans un champ masqué avec `###0.00`, vous obtiendrez `12.34`.
:::

## Group and decimal separators {#group-and-decimal-separators}

Le `MaskedNumberField` prend en charge la personnalisation des caractères **de regroupement** et **décimaux**, ce qui facilite l'adaptation du formatage des nombres à différents lieux ou conventions commerciales.

- Le **séparateur de groupe** est utilisé pour séparer visuellement les milliers (par exemple, `1,000,000`).
- Le **séparateur décimal** indique la partie fractionnelle d'un nombre (par exemple, `123.45`).

Cela est utile dans des applications internationales où différentes régions utilisent différents caractères (par exemple, `.` vs `,`).

```java
field.setGroupCharacter(".");   // Par exemple, 1.000.000
field.setDecimalCharacter(","); // Par exemple, 123,45
```

:::tip Comportement par Défaut
Par défaut, le `MaskedNumberField` applique des séparateurs de groupe et décimaux basés sur la locale actuelle de l'application. Vous pouvez les remplacer à tout moment en utilisant les setters fournis.
:::

## Negateable {#negateable}

Le `MaskedNumberField` prend en charge une option pour contrôler si les nombres négatifs sont autorisés.

Par défaut, les valeurs négatives comme `-123.45` sont autorisées. Pour empêcher cela, utilisez `setNegateable(false)` pour restreindre l'entrée aux valeurs positives uniquement.

Cela est utile dans des scénarios commerciaux où des valeurs comme les quantités, les totaux ou les pourcentages doivent toujours être non négatifs.

```java
field.setNegateable(false);
```

Lorsque `negatable` est défini sur `false`, le champ bloque toute tentative d'entrée d'un signe moins ou d'autres valeurs négatives.

<ComponentDemo
path='/webforj/maskednumnegatable/'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java']}
height='150px'
/>

## Min and max values {#min-and-max-values}

Le `MaskedNumberField` prend en charge la définition de limites numériques à l'aide de `setMin()` et `setMax()`. 
Ces contraintes aident à garantir que la saisie de l'utilisateur reste dans une plage valide et attendue.

- **Valeur Minimale**  
  Utilisez `setMin()` pour définir le plus bas chiffre acceptable :

  ```java
  field.setMin(10.0); // Valeur minimale : 10
  ```

  Si l'utilisateur entre un nombre en dessous de ce seuil, il sera considéré comme invalide.

- **Valeur Maximale**  
  Utilisez `setMax()` pour définir le plus haut chiffre acceptable :

  ```java
  field.setMax(100.0); // Valeur maximale : 100
  ```

  Les valeurs supérieures à cette limite seront signalées comme invalides.

## Restoring the value {#restoring-the-value}

Le `MaskedNumberField` prend en charge une fonctionnalité de restauration qui réinitialise la valeur du champ à un état prédéfini. 
Cela peut être utile lorsque les utilisateurs ont besoin de revenir sur des modifications, d'annuler des modifications accidentelles ou de revenir à une valeur par défaut connue.

Pour activer ce comportement, définissez la valeur cible à l'aide de `setRestoreValue()`. 
Lorsque nécessaire, le champ peut être réinitialisé par programmation en utilisant `restoreValue()`.

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### Ways to restore the value {#ways-to-restore-the-value}

- **Par Programmation** en utilisant `restoreValue()`
- **Via le clavier**, en appuyant sur <kbd>ESC</kbd> (c'est la touche de restauration par défaut, sauf si remplacée)

La valeur de restauration doit être définie explicitement. Si elle n'est pas définie, la fonctionnalité ne revertira pas le champ.

<ComponentDemo
path='/webforj/maskednumrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java']}
height='150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

Le `MaskedNumberFieldSpinner` étend [`MaskedNumberField`](#basics) en ajoutant des contrôles de spinner qui permettent aux utilisateurs d'augmenter ou de diminuer la valeur à l'aide de boutons de pas ou de touches fléchées. 
Cela est idéal pour des saisies telles que des quantités, des ajustements de prix, des contrôles d'évaluation, ou toute situation où les utilisateurs effectuent des changements incrémentiels.

<ComponentDemo
path='/webforj/maskednumspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java']}
height='120px'
/>

### Key features {#key-features}

- **Incréments de Pas**  
  Utilisez `setStep()` pour définir de combien la valeur doit changer à chaque rotation :

  ```java
  spinner.setStep(5.0); // Chaque rotation ajoute ou soustrait 5
  ```

- **Contrôles Interactifs**  
  Les utilisateurs peuvent cliquer sur les boutons du spinner ou utiliser l'entrée au clavier pour ajuster la valeur.

- **Toutes les Fonctions de MaskedNumberField**  
  Prend entièrement en charge les masques, le formatage, les caractères de regroupement/décimaux, les contraintes min/max, et la logique de restauration.

## Styling {#styling}

<TableBuilder name="MaskedNumberField" />
