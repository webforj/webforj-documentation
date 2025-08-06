---
title: MaskedTextField
sidebar_position: 15
_i18n_hash: 31f236456f3f30e15115a03275be9132
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-textfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

Le composant `MaskedTextField` vise à fournir une entrée de texte configurable et facilement validable. Il convient aux applications nécessitant une saisie formatée, telles que les applications financières, de commerce électronique et de santé.

## Principes de base {#basics}

Le `MaskedTextField` peut être instancié avec ou sans paramètres. Vous pouvez définir une valeur initiale, une étiquette, un texte d'espace réservé et un écouteur en cas de changement de valeur.

```java
MaskedTextField field = new MaskedTextField("ID de compte");
field.setMask("ZZZZ-0000")
  .setHelperText("Masque : ZZZZ-0000 - par exemple : SAVE-2025")
```

## Règles de masque {#mask-rules}

Le `MaskedTextField` formate l'entrée de texte en utilisant un masque - une chaîne qui définit quels caractères sont autorisés à chaque position. Cela garantit une saisie cohérente et structurée pour des éléments tels que les numéros de téléphone, les codes postaux et les formats d'ID.

### Caractères de masque pris en charge {#supported-mask-characters}

| Caractère | Description                                                                                 |
|-----------|---------------------------------------------------------------------------------------------|
| `X`       | Tout caractère imprimable                                                                     |
| `a`       | Tout caractère alphabétique (majuscule ou minuscule)                                           |
| `A`       | Tout caractère alphabétique ; les lettres minuscules sont converties en majuscules                      |
| `0`       | Tout chiffre (0–9)                                                                             |
| `z`       | Tout chiffre ou lettre (majuscule ou minuscule)                                                |
| `Z`       | Tout chiffre ou lettre ; les lettres minuscules sont converties en majuscules                           |

Tous les autres caractères du masque sont traités comme des littéraux et doivent être tapés exactement. 
Par exemple, un masque comme `XX@XX` exige que l'utilisateur entre un `@` au milieu.

- **Les caractères invalides** sont silencieusement ignorés.
- **Une saisie courte** est complétée par des espaces.
- **Une saisie longue** est tronquée pour correspondre au masque.

### Exemples {#examples}

```java
field.setMask("(000) 000-0000");     // Exemple : (123) 456-7890
field.setMask("A00 000");            // Exemple : A1B 2C3 (code postal canadien)
field.setMask("ZZZZ-0000");          // Exemple : ABCD-1234
field.setMask("0000-0000-0000-0000");// Exemple : 1234-5678-9012-3456
```

:::tip Entrée complète autorisée
Si le masque contient seulement `X`, le champ se comporte comme un [`TextField`](../text-field.md) standard, permettant toute saisie imprimable.
Ceci est utile lorsque vous souhaitez conserver la capacité de formater sans appliquer de règles strictes sur les caractères.
:::

<ComponentDemo 
path='/webforj/maskedtextfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldView.java'
height='250px'
/>

## Modèles de validation {#validation-patterns}

Bien que les masques définissent la structure de l’entrée, vous pouvez les combiner avec des modèles de validation pour appliquer des règles d’entrée plus spécifiques. Cela ajoute une couche supplémentaire de validation côté client à l’aide d’expressions régulières.

Utilisez la méthode `setPattern()` pour appliquer une expression régulière personnalisée :

```java
field.setPattern("[A-Za-z0-9]{10}"); // Imposed a code alphanumérique de 10 caractères
```

Cela garantit que l'entrée correspond non seulement au masque mais aussi à une structure définie, telle que la longueur ou les caractères autorisés.

Ceci est particulièrement utile lorsque :

- Le masque autorise trop de flexibilité
- Vous souhaitez imposer une longueur exacte ou un format spécifique (par exemple hex, Base64, UUID)

:::tip Format d'expression régulière
Le modèle doit être une [expression régulière JavaScript valide](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), comme utilisé par le type `RegExp`. Vous pouvez trouver plus de détails dans la [documentation de l'attribut de motif HTML](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview).
:::

## Rétablissement de la valeur {#restoring-the-value}

Le `MaskedTextField` comprend une fonction de rétablissement qui remet la valeur du champ à un état prédéfini ou original. 
Cela peut être utile pour annuler les modifications de l'utilisateur ou revenir à une entrée par défaut.

```java
field.setRestoreValue("ABC123");
field.restoreValue();
```

### Façons de rétablir la valeur {#ways-to-restore-the-value}

- **Programmatique**, en appelant `restoreValue()`
- **Via le clavier**, en appuyant sur <kbd>ESC</kbd> (c'est la touche de rétablissement par défaut à moins qu'elle ne soit remplacée par un écouteur d'événements)

Vous pouvez définir la valeur à rétablir avec `setRestoreValue()`. Si aucune valeur de rétablissement n'est définie, le champ reviendra à la valeur initiale au moment où il a été rendu.

<ComponentDemo 
path='/webforj/maskedtextfieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldRestoreView.java'
height='200px'
/>

## `MaskedTextFieldSpinner` {#maskedtextfieldspinner}

Le `MaskedTextFieldSpinner` étend [`MaskedTextField`](#basics) en ajoutant des commandes de molette qui permettent aux utilisateurs de parcourir une liste de valeurs prédéfinies. 
Cela améliore l'expérience utilisateur dans les situations où l'entrée doit être limitée à un ensemble fixe d'options valides.

<ComponentDemo 
path='/webforj/maskedtextfieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldSpinnerView.java'
height='120px'
/>

### Fonctionnalités clés {#key-features}

- **Support de la liste d'options**  
  Remplissez la molette avec une liste de valeurs de chaîne valides à l'aide de `setOptions()` :

  ```java
  spinner.setOptions(List.of("Option A", "Option B", "Option C"));
  ```

- **Spin programmatique**  
  Utilisez `spinUp()` et `spinDown()` pour parcourir les options :

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
  Hérite entièrement de tous les formats, règles de masque et validations de modèle de `MaskedTextField`.

## Style {#styling}

<TableBuilder name="MaskedTextField" />
