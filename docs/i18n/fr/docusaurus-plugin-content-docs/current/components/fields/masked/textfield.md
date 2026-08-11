---
title: MaskedTextField
sidebar_position: 15
description: >-
  Enforce formatted text entry with the MaskedTextField, supporting mask
  characters for digits, letters, and literals for IDs and codes.
_i18n_hash: 10866226b1025c8c4c0a28499d46de38
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-textfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

Le composant `MaskedTextField` fournit une entrée de texte configurable qui impose des règles de formatage et de validation. Il est bien adapté aux applications nécessitant une saisie structurée, telles que les systèmes financiers, de commerce électronique et de santé.

<!-- INTRO_END -->

## Bases {#basics}

Le `MaskedTextField` peut être instancié avec ou sans paramètres. Vous pouvez définir une valeur initiale, une étiquette, un texte d'espace réservé et un écouteur en cas de changement de valeur.

```java
MaskedTextField field = new MaskedTextField("ID de compte");
field.setMask("ZZZZ-0000")
  .setHelperText("Masque : ZZZZ-0000 - par exemple : SAVE-2025")
```

## Règles de masque {#mask-rules}

Le `MaskedTextField` formate l'entrée de texte à l'aide d'un masque - une chaîne qui définit quels caractères sont autorisés à chaque position. Cela assure une saisie cohérente et structurée pour des éléments tels que les numéros de téléphone, les codes postaux et les formats d'identification.

:::tip Application de masques par programme
Pour formater des chaînes avec la même syntaxe de masque en dehors d'un champ, par exemple lors de l'affichage de données dans un [`Table`](/docs/components/table/overview), utilisez la classe utilitaire [`MaskDecorator`](/docs/advanced/mask-decorator).
:::

### Caractères de masque pris en charge {#supported-mask-characters}

| Caractère | Description                                                                                 |
|-----------|---------------------------------------------------------------------------------------------|
| `X`       | Tout caractère imprimable                                                                     |
| `a`       | Tout caractère alphabétique (majuscule ou minuscule)                                           |
| `A`       | Tout caractère alphabétique ; les lettres minuscules sont converties en majuscules                      |
| `0`       | Tout chiffre (0–9)                                                                             |
| `z`       | Tout chiffre ou lettre (majuscule ou minuscule)                                                |
| `Z`       | Tout chiffre ou lettre ; les lettres minuscules sont converties en majuscules                           |

Tous les autres caractères dans le masque sont traités comme des littéraux et doivent être saisis exactement.
Par exemple, un masque comme `XX@XX` exige que l'utilisateur saisisse un `@` au milieu.

- **Les caractères invalides** sont ignorés silencieusement.
- **Les entrées courtes** sont complétées par des espaces.
- **Les entrées longues** sont tronquées pour s'adapter au masque.

### Exemples {#examples}

```java
field.setMask("(000) 000-0000");     // Exemple : (123) 456-7890
field.setMask("A00 000");            // Exemple : A1B 2C3 (code postal canadien)
field.setMask("ZZZZ-0000");          // Exemple : ABCD-1234
field.setMask("0000-0000-0000-0000");// Exemple : 1234-5678-9012-3456
```

:::tip Entrée complète autorisée
Si le masque ne contient que `X`, le champ se comporte comme un [`TextField`](../textfield) standard, permettant toute entrée imprimable.
Ceci est utile lorsque vous souhaitez réserver la possibilité de formater sans appliquer de règles strictes de caractères.
:::

<ComponentDemo
path='/webforj/maskedtextfield'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldView.java']}
height='250px'
/>

## Modèles de validation {#validation-patterns}

Bien que les masques définissent la structure de l'entrée, vous pouvez les combiner avec des modèles de validation pour imposer des règles d'entrée plus spécifiques. Cela ajoute une couche supplémentaire de validation côté client à l'aide d'expressions régulières.

Utilisez la méthode `setPattern()` pour appliquer une expression régulière personnalisée :

```java
field.setPattern("[A-Za-z0-9]{10}"); // Imposera un code alphanumérique de 10 caractères
```

Cela garantit que l'entrée correspond non seulement au masque, mais se conforme également à une structure définie, telle que la longueur ou les caractères autorisés.

Cela est particulièrement utile lorsque :

- Le masque permet trop de flexibilité
- Vous souhaitez imposer une longueur exacte ou un format spécifique (ex. hex, Base64, UUID)

:::tip Format d'expression régulière
Le modèle doit être une expression régulière JavaScript valide, comme utilisé par le type `RegExp`. Vous pouvez trouver plus de détails dans la documentation de l'attribut HTML pattern.
:::

## Restauration de la valeur {#restoring-the-value}

Le `MaskedTextField` comprend une fonction de restauration qui réinitialise la valeur du champ à un état prédéfini ou d'origine.
Cela peut être utile pour annuler des modifications utilisateur ou revenir à une entrée par défaut.

```java
field.setRestoreValue("ABC123");
field.restoreValue();
```

### Façons de restaurer la valeur {#ways-to-restore-the-value}

- **Par programme**, en appelant `restoreValue()`
- **Via le clavier**, en appuyant sur <kbd>ESC</kbd> (c'est la touche de restauration par défaut à moins d'être remplacée par un écouteur d'événements)

Vous pouvez définir la valeur à restaurer avec `setRestoreValue()`. Si aucune valeur de restauration n'est définie, le champ revertira à la valeur initiale au moment de son rendu.

<ComponentDemo
path='/webforj/maskedtextfieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldRestoreView.java']}
height='200px'
/>

## `MaskedTextFieldSpinner` {#maskedtextfieldspinner}

Le `MaskedTextFieldSpinner` étend le [`MaskedTextField`](#basics) en ajoutant des contrôles de spinner qui permettent aux utilisateurs de parcourir une liste de valeurs prédéfinies.
Cela améliore l'expérience utilisateur dans les situations où l'entrée doit être contrainte à un ensemble fixe d'options valides.

<ComponentDemo
path='/webforj/maskedtextfieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldSpinnerView.java']}
height='120px'
/>

### Caractéristiques clés {#key-features}

- **Support de liste d'options**
  Remplissez le spinner avec une liste de valeurs de chaîne valides à l'aide de `setOptions()` :

  ```java
  spinner.setOptions(List.of("Option A", "Option B", "Option C"));
  ```

- **Rotation programmatique**
  Utilisez `spinUp()` et `spinDown()` pour parcourir les options :

  ```java
  spinner.spinUp();   // Sélectionne l'option suivante
  spinner.spinDown(); // Sélectionne l'option précédente
  ```

- **Contrôle de l'index**
  Définissez ou récupérez l'index de sélection actuel avec :

  ```java
  spinner.setOptionIndex(1);
  int current = spinner.getOptionIndex();
  ```

- **Compatibilité de masque**
  Hérite entièrement de toutes les règles de formatage, de masque et de validation des modèles du `MaskedTextField`.

## Stylisation {#styling}

<TableBuilder name="MaskedTextField" />
