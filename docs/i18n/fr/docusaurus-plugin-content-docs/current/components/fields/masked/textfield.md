---
title: MaskedTextField
sidebar_position: 15
_i18n_hash: b910fd6dedb911a21f3d37b17658c2cc
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-textfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

Le composant `MaskedTextField` fournit un champ de texte configurable qui impose des règles de formatage et de validation. Il est particulièrement adapté aux applications nécessitant une saisie structurée, telles que les systèmes financiers, de commerce électronique et de santé.

<!-- INTRO_END -->

## Bases {#basics}

Le `MaskedTextField` peut être instancié avec ou sans paramètres. Vous pouvez définir une valeur initiale, une étiquette, un texte d'espace réservé et un écouteur au cas où la valeur changerait.

```java
MaskedTextField field = new MaskedTextField("ID de compte");
field.setMask("ZZZZ-0000")
  .setHelperText("Masque : ZZZZ-0000 - par exemple : SAVE-2025")
```

## Règles de masque {#mask-rules}

Le `MaskedTextField` formate la saisie de texte à l'aide d'un masque - une chaîne qui définit quels caractères sont autorisés à chaque position. Cela garantit une saisie cohérente et structurée pour des éléments comme les numéros de téléphone, les codes postaux et les formats d'identification.

### Caractères de masque pris en charge {#supported-mask-characters}

| Caractère | Description                                                                                 |
|-----------|---------------------------------------------------------------------------------------------|
| `X`       | Tout caractère imprimable                                                                     |
| `a`       | Tout caractère alphabétique (majuscules ou minuscules)                                           |
| `A`       | Tout caractère alphabétique ; les lettres minuscules sont converties en majuscules                      |
| `0`       | Tout chiffre (0–9)                                                                             |
| `z`       | Tout chiffre ou lettre (majuscules ou minuscules)                                                |
| `Z`       | Tout chiffre ou lettre ; les lettres minuscules sont converties en majuscules                           |

Tous les autres caractères dans le masque sont traités comme littéraux et doivent être tapés exactement. 
Par exemple, un masque comme `XX@XX` exige que l'utilisateur entre un `@` au milieu.

- **Les caractères invalides** sont silencieusement ignorés.
- **Une saisie courte** est complétée par des espaces.
- **Une saisie longue** est tronquée pour s'adapter au masque.

### Exemples {#examples}

```java
field.setMask("(000) 000-0000");     // Exemple : (123) 456-7890
field.setMask("A00 000");            // Exemple : A1B 2C3 (code postal canadien)
field.setMask("ZZZZ-0000");          // Exemple : ABCD-1234
field.setMask("0000-0000-0000-0000");// Exemple : 1234-5678-9012-3456
```

:::tip Saisie complète autorisée
Si le masque ne contient que `X`, le champ se comporte comme un [`TextField`](../textfield), permettant toute saisie imprimable.
Ceci est utile lorsque vous souhaitez réserver la capacité de formatage sans appliquer des règles strictes sur les caractères.
:::

<ComponentDemo 
path='/webforj/maskedtextfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldView.java'
height='250px'
/>

## Modèles de validation {#validation-patterns}

Bien que les masques définissent la structure de la saisie, vous pouvez les combiner avec des modèles de validation pour imposer des règles de saisie plus spécifiques. Cela ajoute une couche de validation côté client supplémentaire à l'aide d'expressions régulières.

Utilisez la méthode `setPattern()` pour appliquer une expression régulière personnalisée :

```java
field.setPattern("[A-Za-z0-9]{10}"); // Imposent un code alphanumérique de 10 caractères
```

Cela garantit que l'entrée correspond non seulement au masque, mais également à une structure définie, comme la longueur ou les caractères autorisés.

Ceci est particulièrement utile lorsque :

- Le masque permet trop de flexibilité
- Vous souhaitez imposer une longueur exacte ou un format spécifique (par exemple, hex, Base64, UUID)

:::tip Format d'expression régulière
Le modèle doit être une [expression régulière JavaScript valide](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), comme utilisé par le type `RegExp`. Vous pouvez trouver plus de détails dans la [documentation sur l'attribut de modèle HTML](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview).
:::

## Restauration de la valeur {#restoring-the-value}

Le `MaskedTextField` comprend une fonctionnalité de restauration qui réinitialise la valeur du champ à un état prédéfini ou d'origine. 
Cela peut être utile pour annuler les modifications de l'utilisateur ou revenir à une saisie par défaut.

```java
field.setRestoreValue("ABC123");
field.restoreValue();
```

### Façons de restaurer la valeur {#ways-to-restore-the-value}

- **Programatiquement**, en appelant `restoreValue()`
- **Via clavier**, en appuyant sur <kbd>ESC</kbd> (c'est la touche de restauration par défaut à moins qu'elle ne soit remplacée par un écouteur d'événements)

Vous pouvez définir la valeur à restaurer avec `setRestoreValue()`. Si aucune valeur de restauration n'est définie, le champ reviendra à la valeur initiale au moment où il a été rendu.

<ComponentDemo 
path='/webforj/maskedtextfieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldRestoreView.java'
height='200px'
/>

## `MaskedTextFieldSpinner` {#maskedtextfieldspinner}

Le `MaskedTextFieldSpinner` étend [`MaskedTextField`](#basics) en ajoutant des contrôles de spinner qui permettent aux utilisateurs de faire défiler une liste de valeurs prédéfinies. 
Cela améliore l'expérience utilisateur dans les situations où la saisie doit être limitée à un ensemble fixe d'options valides.

<ComponentDemo 
path='/webforj/maskedtextfieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldSpinnerView.java'
height='120px'
/>

### Caractéristiques principales {#key-features}

- **Prise en charge de la liste d'option**  
  Remplissez le spinner avec une liste de valeurs de chaîne valides à l'aide de `setOptions()` :

  ```java
  spinner.setOptions(List.of("Option A", "Option B", "Option C"));
  ```

- **Rotation programmatique**  
  Utilisez `spinUp()` et `spinDown()` pour naviguer à travers les options :

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
  Hérite entièrement de toutes les règles de formatage, de masque et de validation de modèle de `MaskedTextField`.

## Style {#styling}

<TableBuilder name="MaskedTextField" />
