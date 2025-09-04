---
title: MaskedNumberField
sidebar_position: 10
_i18n_hash: 626c7e147632731dfdc761116a8abdc9
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

Le `MaskedNumberField` est un champ de texte conçu pour la saisie numérique structurée. Il garantit que les chiffres sont formatés de manière cohérente en fonction d'un masque défini, ce qui le rend particulièrement utile pour les formulaires financiers, les champs de prix ou toute saisie où la précision et la lisibilité comptent.

Ce composant prend en charge le formatage des nombres, la localisation des caractères décimaux et de groupement, ainsi que des contraintes facultatives de valeur comme les minimums ou maximums.

## Basics {#basics}

Le `MaskedNumberField` peut être instancié avec ou sans paramètres. Il permet de définir une valeur initiale, une étiquette, un espace réservé et un écouteur d'événement pour réagir aux changements de valeur.

Cette démo présente un **Calculateur de Pourboire** qui utilise `MaskedNumberField` pour une saisie numérique intuitive. Un champ est configuré pour accepter un montant de facture formaté, tandis que l'autre capture un pourcentage de pourboire en nombre entier. Les deux champs appliquent des masques numériques pour garantir un formatage cohérent et prévisible.

<ComponentDemo 
path='/webforj/maskednumberfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java'
height = '270px'
/>

## Mask rules {#mask-rules}

Le `MaskedNumberField` utilise une chaîne de masque pour contrôler comment la saisie numérique est formatée et affichée. 
Chaque caractère dans le masque définit un comportement de formatage spécifique, permettant un contrôle précis sur la manière dont les nombres apparaissent.

### Mask characters {#mask-characters}

| Caractère | Description |
|-----------|-------------|
| `0`       | Toujours remplacé par un chiffre (0–9). |
| `#`       | Supprime les zéros non significatifs. Remplacé par le caractère de remplissage à gauche du point décimal. Pour les chiffres suivants, remplacé par un espace ou un zéro. Sinon, remplacé par un chiffre. |
| `,`       | Utilisé comme séparateur de groupement (par ex. milliers). Remplacé par le caractère de remplissage s'il n'y a pas de chiffres qui le précèdent. Sinon, affiché comme une virgule. |
| `-`       | Affiche un signe moins (`-`) si le nombre est négatif. Remplacé par le caractère de remplissage s'il est positif. |
| `+`       | Affiche `+` pour les nombres positifs ou `-` pour les nombres négatifs. |
| `$`       | Donne toujours un signe dollar. |
| `(`       | Insère une parenthèse gauche `(` pour les valeurs négatives. Remplacé par le caractère de remplissage s'il est positif. |
| `)`       | Insère une parenthèse droite `)` pour les valeurs négatives. Remplacé par le caractère de remplissage s'il est positif. |
| `CR`      | Affiche `CR` pour les nombres négatifs. Affiche deux espaces si le nombre est positif. |
| `DR`      | Affiche `CR` pour les nombres négatifs. Affiche `DR` pour les nombres positifs. |
| `*`       | Insère un astérisque `*`. |
| `.`       | Marque le point décimal. Si aucun chiffre n'apparaît dans la sortie, remplacé par le caractère de remplissage. Après le décimal, les caractères de remplissage sont traités comme des espaces. |
| `B`       | Devenir toujours un espace. Tout autre caractère littéral est affiché tel quel. |

Certains des caractères ci-dessus peuvent apparaître plus d'une fois dans le masque pour le formatage. Ceux-ci incluent `-`, `+`, `$` et `(`. Si l'un de ces caractères est présent dans le masque, le premier rencontré sera déplacé à la dernière position où un `#` ou `,` a été remplacé par le caractère de remplissage. S'il n'existe pas de telle position, le Double caractère est laissé à sa place.

:::info Pas d'arrondi automatique
Un masque dans un champ ne fait **PAS** d'arrondi. Par exemple, en plaçant une valeur telle que `12.34567`
dans un champ masqué avec `###0.00`, vous obtiendrez `12.34`.
:::

## Group and decimal separators {#group-and-decimal-separators}

Le `MaskedNumberField` prend en charge la personnalisation des caractères **de groupement** et **décimaux**, ce qui facilite l'adaptation du formatage des nombres à différentes localités ou conventions commerciales.

- Le **séparateur de groupe** est utilisé pour séparer visuellement les milliers (par ex. `1,000,000`).
- Le **séparateur décimal** indique la partie fractionnaire d'un nombre (par ex. `123.45`).

Cela est utile dans les applications internationales où différentes régions utilisent différents caractères (par ex. `.` vs `,`).

```java
field.setGroupCharacter(".");   // par ex. 1.000.000
field.setDecimalCharacter(","); // par ex. 123,45
```

:::tip Comportement par défaut
Par défaut, `MaskedNumberField` applique des séparateurs de groupe et décimaux en fonction de la locale actuelle de l'application. Vous pouvez les remplacer à tout moment en utilisant les setters fournis.
:::

## Negateable {#negateable}

Le `MaskedNumberField` prend en charge une option pour contrôler si les nombres négatifs sont autorisés.

Par défaut, les valeurs négatives comme `-123.45` sont autorisées. Pour empêcher cela, utilisez `setNegateable(false)` pour restreindre la saisie uniquement aux valeurs positives.

Cela est utile dans des scénarios commerciaux où des valeurs comme les quantités, les totaux ou les pourcentages doivent toujours être non négatifs.

```java
field.setNegateable(false);
```

Lorsque `negatable` est défini sur `false`, le champ bloque toutes les tentatives d'entrer un signe moins ou d'autres valeurs négatives.

<ComponentDemo 
path='/webforj/maskednumnegatable/?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java'
height = '150px'
/>

## Min and max values {#min-and-max-values}

Le `MaskedNumberField` prend en charge la définition de limites numériques à l'aide de `setMin()` et `setMax()`. 
Ces contraintes aident à garantir que la saisie de l'utilisateur reste dans une plage valide et attendue.

- **Valeur minimale**  
  Utilisez `setMin()` pour définir le plus bas nombre acceptable :

  ```java
  field.setMin(10.0); // Valeur minimale : 10
  ```

  Si l'utilisateur entre un nombre inférieur à ce seuil, il sera considéré comme invalide.

- **Valeur maximale**  
  Utilisez `setMax()` pour définir le plus haut nombre acceptable :

  ```java
  field.setMax(100.0); // Valeur maximale : 100
  ```

  Les valeurs au-dessus de cette limite seront signalées comme invalides.

## Restoring the value {#restoring-the-value}

Le `MaskedNumberField` prend en charge une fonction de restauration qui réinitialise la valeur du champ à un état prédéfini. 
Cela peut être utile lorsque les utilisateurs ont besoin d'annuler des modifications, de revenir sur des modifications accidentelles ou de revenir à une valeur par défaut connue.

Pour activer ce comportement, définissez la valeur cible à l'aide de `setRestoreValue()`. 
Lorsque c'est nécessaire, le champ peut être réinitialisé par programmation à l'aide de `restoreValue()`.

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### Ways to restore the value {#ways-to-restore-the-value}

- **Par programmation** en utilisant `restoreValue()`
- **Via le clavier**, en appuyant sur <kbd>ESC</kbd> (c'est la touche de restauration par défaut, sauf si elle est remplacée)

La valeur de restauration doit être définie explicitement. Si elle n'est pas définie, la fonctionnalité ne réinitialisera pas le champ.

<ComponentDemo 
path='/webforj/maskednumrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java'
height = '150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

Le `MaskedNumberFieldSpinner` étend [`MaskedNumberField`](#basics) en ajoutant des contrôles de molette qui permettent aux utilisateurs d'augmenter ou de diminuer la valeur à l'aide de boutons de pas ou des flèches. 
C'est idéal pour des entrées telles que les quantités, les ajustements de prix, les contrôles d'évaluation ou tout scénario où les utilisateurs effectuent des modifications incrémentielles.

<ComponentDemo 
path='/webforj/maskednumspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java'
height = '120px'
/>

### Key features {#key-features}

- **Incréments de pas**  
  Utilisez `setStep()` pour définir combien la valeur doit changer à chaque rotation :

  ```java
  spinner.setStep(5.0); // Chaque rotation ajoute ou soustrait 5
  ```

- **Contrôles interactifs**  
  Les utilisateurs peuvent cliquer sur les boutons de la molette ou utiliser la saisie au clavier pour ajuster la valeur.

- **Toutes les fonctionnalités de MaskedNumberField**  
  Prend totalement en charge les masques, le formatage, les caractères de groupement/décimaux, les contraintes min/max et la logique de restauration.

## Styling {#styling}

<TableBuilder name="MaskedNumberField" />
