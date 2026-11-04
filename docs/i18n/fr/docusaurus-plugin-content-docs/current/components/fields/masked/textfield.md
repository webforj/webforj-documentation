---
title: MaskedTextField
sidebar_position: 15
description: >-
  Enforce formatted text entry with the MaskedTextField, supporting mask
  characters for digits, letters, and literals for IDs and codes.
_i18n_hash: 9ee372191dc17b2510f020bc908f0060
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-textfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

Le composant `MaskedTextField` fournit une entrée de texte configurable qui impose des règles de formatage et des validations. Il est bien adapté aux applications nécessitant une saisie structurée, comme les systèmes financiers, de commerce électronique et de santé.

Ce composant peut être instancié avec ou sans paramètres. Vous pouvez définir une valeur initiale, une étiquette, un texte d'espace réservé et un écouteur au cas où la valeur changerait.

<!-- INTRO_END -->

```java
MaskedTextField field = new MaskedTextField("Identifiant de compte");
field.setMask("ZZZZ-0000")
  .setHelperText("Masque : ZZZZ-0000 - par exemple : SAVE-2025");
```

## Règles de masque {#mask-rules}

Le `MaskedTextField` formate l'entrée de texte à l'aide d'un masque - une chaîne qui définit quels caractères sont autorisés à chaque position. Cela garantit une saisie cohérente et structurée pour des éléments tels que les numéros de téléphone, les codes postaux et les formats d'identifiant.

:::tip Application de masques par programmation
Pour formater des chaînes avec la même syntaxe de masque en dehors d'un champ, par exemple lors du rendu de données dans un [`Table`](/docs/components/table/overview), utilisez la classe utilitaire [`MaskDecorator`](/docs/advanced/mask-decorator).
:::

### Caractères de masque pris en charge {#supported-mask-characters}

| Caractère | Description                                                                                 |
|-----------|---------------------------------------------------------------------------------------------|
| `X`       | Tout caractère imprimable                                                                     |
| `a`       | Tout caractère alphabétique (majuscule ou minuscule)                                           |
| `A`       | Tout caractère alphabétique ; les lettres minuscules sont converties en majuscules           |
| `0`       | Tout chiffre (0–9)                                                                             |
| `z`       | Tout chiffre ou lettre (majuscule ou minuscule)                                                |
| `Z`       | Tout chiffre ou lettre ; les lettres minuscules sont converties en majuscules                 |

Tous les autres caractères dans le masque sont traités comme des littéraux et doivent être saisies exactement.
Par exemple, un masque comme `XX@XX` exige que l'utilisateur saisisse un `@` au milieu.

- **Caractères non valides** sont silencieusement ignorés.
- **Entrée courte** est complétée par des espaces.
- **Entrée longue** est tronquée pour s'adapter au masque.

### Exemples {#examples}

```java
field.setMask("(000) 000-0000");     // Exemple : (123) 456-7890
field.setMask("A0A 0A0");            // Exemple : A1B 2C3 (code postal canadien)
field.setMask("ZZZZ-0000");          // Exemple : ABCD-1234
field.setMask("0000-0000-0000-0000");// Exemple : 1234-5678-9012-3456
```

:::tip Saisie complète autorisée
Si le masque ne contient que des `X`, le champ se comporte comme un [`TextField`](../textfield) standard, permettant toute saisie imprimable.
Ceci est utile lorsque vous souhaitez réserver la possibilité de formater sans appliquer de règles strictes sur les caractères.
:::

<ComponentDemo
path='/webforj/maskedtextfield'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldView.java']}
height='250px'
/>

## Modèles de validation {#validation-patterns}

Tandis que les masques définissent la structure de l'entrée, vous pouvez les combiner avec des modèles de validation pour imposer des règles d'entrée plus spécifiques. Cela ajoute une couche supplémentaire de validation côté client à l'aide d'expressions régulières.

Utilisez la méthode `setPattern()` pour appliquer une expression régulière personnalisée :

```java
field.setPattern("[A-Za-z0-9]{10}"); // Imposer un code alphanumérique de 10 caractères
```

Cela garantit que l'entrée correspond non seulement au masque mais aussi à une structure définie, telle que la longueur ou les caractères autorisés.

Ceci est particulièrement utile lorsque :

- Le masque autorise trop de flexibilité
- Vous souhaitez imposer une longueur exacte ou un format spécifique (par exemple hex, Base64, UUID)

:::tip Format d'expression régulière
Le modèle doit être une expression régulière valide en [JavaScript](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), comme utilisé par le type `RegExp`. Vous pouvez trouver plus de détails dans la [documentation de l'attribut pattern HTML](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview).
:::

## Restauration de la valeur {#restoring-the-value}

Le `MaskedTextField` comprend une fonctionnalité de restauration qui réinitialise la valeur du champ à un état prédéfini ou original.
Cela peut être utile pour annuler les modifications de l'utilisateur ou revenir à une saisie par défaut.

```java
field.setRestoreValue("ABC123");
field.restoreValue();
```

### Moyens de restaurer la valeur {#ways-to-restore-the-value}

- **Programmatiquement**, en appelant `restoreValue()`
- **Via le clavier**, en appuyant sur <kbd>ESC</kbd> (c'est la touche de restauration par défaut à moins qu'elle ne soit remplacée par un écouteur d'événements)

Vous pouvez définir la valeur à restaurer avec `setRestoreValue()`. Si aucune valeur de restauration n'est définie, à la fois `restoreValue()` et <kbd>ESC</kbd> définissent la valeur du champ sur `null`.

<ComponentDemo
path='/webforj/maskedtextfieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldRestoreView.java']}
height='200px'
/>

## `MaskedTextFieldSpinner` {#maskedtextfieldspinner}

Le `MaskedTextFieldSpinner` étend le `MaskedTextField` en ajoutant des contrôles de spinner qui permettent aux utilisateurs de passer en revue une liste de valeurs prédéfinies.
Cela améliore l'expérience utilisateur dans les situations où l'entrée doit être contrainte à un ensemble fixe d'options valides.

<ComponentDemo
path='/webforj/maskedtextfieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldSpinnerView.java']}
height='120px'
/>

### Caractéristiques clés {#key-features}

- **Support de liste d'options**
  Remplissez le spinner avec une liste de chaînes valides à l'aide de `setOptions()` :

  ```java
  spinner.setOptions(List.of("Option A", "Option B", "Option C"));
  ```

- **Saut programmatique**
  Utilisez `spinUp()` et `spinDown()` pour naviguer entre les options :

  ```java
  spinner.spinUp();   // Sélectionne l'option suivante
  spinner.spinDown(); // Sélectionne l'option précédente
  ```

- **Contrôle d'index**
  Définissez ou récupérez l'index de sélection actuel avec :

  ```java
  spinner.setOptionIndex(1);
  int current = spinner.getOptionIndex();
  ```

- **Compatibilité de masque**
  Hérite entièrement de toutes les règles de formatage, les règles de masque et la validation de modèle du `MaskedTextField`.

## Stylisation {#styling}

<TableBuilder name="MaskedTextField" />
