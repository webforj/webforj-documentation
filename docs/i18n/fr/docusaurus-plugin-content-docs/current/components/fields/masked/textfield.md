---
title: MaskedTextField
sidebar_position: 15
description: >-
  Enforce formatted text entry with the MaskedTextField, supporting mask
  characters for digits, letters, and literals for IDs and codes.
_i18n_hash: 5f6c175ffd4b8d75f3b65c7b77bb13fe
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-textfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

Le composant `MaskedTextField` fournit un champ de texte configurable qui impose des règles de formatage et de validation. Il est particulièrement adapté aux applications nécessitant une saisie structurée, telles que les systèmes financiers, de commerce électronique et de santé.

Ce composant peut être instancié avec ou sans paramètres. Vous pouvez définir une valeur initiale, une étiquette, un texte de remplacement et un écouteur en cas de changement de valeur.

<!-- INTRO_END -->

```java
MaskedTextField field = new MaskedTextField("ID de compte");
field.setMask("ZZZZ-0000")
  .setHelperText("Masque : ZZZZ-0000 - par exemple : SAVE-2025")
```

## Règles de masque {#mask-rules}

Le `MaskedTextField` formate la saisie textuelle à l'aide d'un masque - une chaîne qui définit quels caractères sont autorisés à chaque position. Cela garantit une saisie cohérente et structurée pour des éléments tels que les numéros de téléphone, les codes postaux et les formats d'identification.

:::tip Application de masques par programme
Pour formater des chaînes avec la même syntaxe de masque en dehors d'un champ, par exemple lors du rendu de données dans un [`Table`](/docs/components/table/overview), utilisez la classe utilitaire [`MaskDecorator`](/docs/advanced/mask-decorator).
:::

### Caractères de masque pris en charge {#supported-mask-characters}

| Caractère | Description                                                                                  |
|-----------|----------------------------------------------------------------------------------------------|
| `X`       | Tout caractère imprimable                                                                    |
| `a`       | Tout caractère alphabétique (majuscule ou minuscule)                                         |
| `A`       | Tout caractère alphabétique ; les lettres minuscules sont converties en majuscules          |
| `0`       | Tout chiffre (0–9)                                                                            |
| `z`       | Tout chiffre ou lettre (majuscule ou minuscule)                                             |
| `Z`       | Tout chiffre ou lettre ; les lettres minuscules sont converties en majuscules               |

Tous les autres caractères dans le masque sont traités comme littéraux et doivent être saisis exactement.
Par exemple, un masque comme `XX@XX` nécessite que l'utilisateur entre un `@` au milieu.

- **Les caractères invalides** sont silencieusement ignorés.
- **La saisie courte** est complétée par des espaces.
- **La saisie longue** est tronquée pour s'adapter au masque.

### Exemples {#examples}

```java
field.setMask("(000) 000-0000");     // Exemple : (123) 456-7890
field.setMask("A00 000");            // Exemple : A1B 2C3 (code postal canadien)
field.setMask("ZZZZ-0000");          // Exemple : ABCD-1234
field.setMask("0000-0000-0000-0000");// Exemple : 1234-5678-9012-3456
```

:::tip Saisie complète autorisée
Si le masque ne contient que `X`, le champ se comporte comme un [`TextField`](../textfield) standard, permettant toute saisie imprimable.
C'est utile lorsque vous souhaitez réserver la possibilité de formater sans appliquer de règles de caractère strictes.
:::

<ComponentDemo
path='/webforj/maskedtextfield'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldView.java']}
height='250px'
/>

## Modèles de validation {#validation-patterns}

Alors que les masques définissent la structure de la saisie, vous pouvez les combiner avec des modèles de validation pour imposer des règles de saisie plus spécifiques. Cela ajoute une couche supplémentaire de validation côté client utilisant des expressions régulières.

Utilisez la méthode `setPattern()` pour appliquer une expression régulière personnalisée :

```java
field.setPattern("[A-Za-z0-9]{10}"); // Imposer un code alphanumérique de 10 caractères
```

Cela garantit que l'entrée non seulement correspond au masque mais aussi se conforme à une structure définie, telle que la longueur ou les caractères autorisés.

C'est particulièrement utile lorsque :

- Le masque permet trop de flexibilité
- Vous souhaitez imposer une longueur exacte ou un format spécifique (par exemple, hex, Base64, UUID)

:::tip Format d'expressions régulières
Le modèle doit être une expression régulière valide [JavaScript](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), comme utilisé par le type `RegExp`. Vous pouvez trouver plus de détails dans la [documentation de l'attribut pattern HTML](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview).
:::

## Récupération de la valeur {#restoring-the-value}

Le `MaskedTextField` comprend une fonctionnalité de restauration qui réinitialise la valeur du champ à un état prédéfini ou original.
Cela peut être utile pour annuler des modifications apportées par l'utilisateur ou revenir à une saisie par défaut.

```java
field.setRestoreValue("ABC123");
field.restoreValue();
```

### Manières de restaurer la valeur {#ways-to-restore-the-value}

- **Par programme**, en appelant `restoreValue()`
- **Via le clavier**, en appuyant sur <kbd>ESC</kbd> (c'est la touche de restauration par défaut, sauf si elle est remplacée par un écouteur d'événements)

Vous pouvez définir la valeur à restaurer avec `setRestoreValue()`. Si aucune valeur de restauration n'est définie, le champ reviendra à la valeur initiale au moment où il a été rendu.

<ComponentDemo
path='/webforj/maskedtextfieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldRestoreView.java']}
height='200px'
/>

## `MaskedTextFieldSpinner` {#maskedtextfieldspinner}

Le `MaskedTextFieldSpinner` étend le `MaskedTextField` en ajoutant des contrôles de type spinner qui permettent aux utilisateurs de faire défiler une liste de valeurs prédéfinies.
Cela améliore l'expérience utilisateur dans les situations où la saisie doit être limitée à un ensemble fixe d'options valides.

<ComponentDemo
path='/webforj/maskedtextfieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldSpinnerView.java']}
height='120px'
/>

### Caractéristiques clés {#key-features}

- **Support de liste d'options**
  Remplissez le spinner avec une liste de valeurs de chaîne valides en utilisant `setOptions()` :

  ```java
  spinner.setOptions(List.of("Option A", "Option B", "Option C"));
  ```

- **Rotation programmatique**
  Utilisez `spinUp()` et `spinDown()` pour passer à travers les options :

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

- **Compatibilité des masques**
  Hérite pleinement de toutes les règles de formatage, de masque et de validation des modèles du `MaskedTextField`.

## Style {#styling}

<TableBuilder name="MaskedTextField" />
