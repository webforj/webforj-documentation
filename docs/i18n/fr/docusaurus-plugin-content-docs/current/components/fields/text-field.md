---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
sidebar_class_name: updated-content
_i18n_hash: 71ebfc077bb8042752cbfea71a971e47
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

<ParentLink parent="Field" />

Le composant `TextField` permet aux utilisateurs d'entrer et de modifier du texte sur une seule ligne. Vous pouvez configurer le champ pour afficher un clavier virtuel spécifique, tel qu'un pavé numérique, une saisie d'email, une saisie de numéro de téléphone ou une saisie d'URL. Le composant fournit également une validation intégrée pour rejeter les valeurs qui ne respectent pas le type spécifié.

## Usages {#usages}

Le `TextField` est adapté à un large éventail de scénarios où une saisie ou une édition de texte est requise. Voici quelques exemples de situations où utiliser le `TextField` :

1. **Champs de Formulaire** : Un `TextField` est couramment utilisé dans les formulaires pour capturer les saisies des utilisateurs, comme les noms, adresses ou commentaires. Il est préférable d’utiliser un `TextField` dans une application lorsque la saisie est généralement suffisamment courte pour tenir sur une ligne.

2. **Fonctionnalité de Recherche** : Le `TextField` peut être utilisé pour fournir une boîte de recherche, permettant aux utilisateurs d'entrer des requêtes de recherche.

3. **Édition de Texte** : Un `TextField` est idéal pour les applications qui nécessitent une édition de texte ou la création de contenu, comme les éditeurs de documents, les applications de chat ou les applications de prise de notes.

## Types {#types}

Vous pouvez spécifier le type du TextField en utilisant la méthode `setType()`. De même, vous pouvez récupérer le type en utilisant la méthode `getType()`, qui renverra une valeur d'énumération.

- `Type.TEXT` : C'est le type par défaut pour un `TextField` et retire automatiquement les sauts de ligne de la valeur d'entrée.

- `Type.EMAIL` : Ce type est destiné à la saisie d'adresses email. Il valide l'entrée selon la syntaxe email standard. De plus, il fournit aux navigateurs et appareils compatibles un clavier dynamique qui simplifie le processus de saisie en incluant des touches couramment utilisées comme <kbd>.com</kbd> et <kbd>@</kbd>.

  :::note
  Bien que cette validation confirme le format de l'adresse email, elle ne garantit pas que l'email existe.
  :::

- `Type.TEL` : Ce type est utilisé pour entrer un numéro de téléphone. Le champ affichera un pavé numérique sur certains appareils avec des claviers dynamiques.

- `Type.URL` : Ce type est destiné à entrer des URL. Il valide si un utilisateur entre une URL qui inclut un schéma et un nom de domaine, par exemple : https://webforj.com. De plus, il fournit aux navigateurs et appareils compatibles un clavier dynamique qui simplifie le processus de saisie en incluant des touches couramment utilisées comme <kbd>:</kbd>, <kbd>/</kbd>, et <kbd>.com</kbd>.

- `Type.SEARCH` : Un champ de texte sur une seule ligne pour entrer des chaînes de recherche. Les sauts de ligne sont automatiquement supprimés de la valeur d'entrée.

<ComponentDemo 
path='/webforj/textfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java'
/>

## Valeur du Champ {#field-value}

La valeur d'un `TextField` représente la saisie actuelle de l'utilisateur sous forme de chaîne. Dans webforJ, cela peut être accédé en utilisant la méthode `getValue()`, ou mis à jour de manière programmatique avec `setValue(String)`.

```java
//Définir le contenu initial
textField.setValue("Contenu initial");

//Récupérer la valeur actuelle
String text = textField.getValue();
```

Si la méthode `getValue()` est utilisée sur un champ sans valeur actuelle, elle renvoie une chaîne vide (`""`).

Ce comportement est cohérent avec la manière dont l'élément HTML `<input type="text">` expose sa valeur via JavaScript.

:::tip Combiner la gestion des valeurs avec la validation
Appliquez des contraintes comme un [modèle](#pattern-matching), une [longueur minimale](#setminlength), ou une [longueur maximale](#setmaxlength) pour définir quand une valeur est considérée comme valide. 
:::

## Texte de l'Indice {#placeholder-text}

Vous pouvez définir un texte de remplacement pour le `TextField` en utilisant la méthode `setPlaceholder()`. Le texte de remplacement est affiché lorsque le champ est vide, aidant à inciter l'utilisateur à entrer une saisie appropriée dans le `TextField`.

## Texte Sélectionné {#selected-text}

Il est possible d'interagir avec la classe `TextField` pour récupérer le texte sélectionné par un utilisateur, et pour obtenir des informations sur la sélection de l'utilisateur. Vous pouvez récupérer le texte sélectionné dans le `TextField` en utilisant la méthode `getSelectedText()`. Ce comportement serait couramment utilisé en liaison avec un événement.

```java
TextField textField = new TextField("Entrez quelque chose...");
Button getSelectionBtn = new Button("Obtenir le Texte Sélectionné");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("Texte sélectionné : " + selected);
});
```

De même, il est possible de récupérer la plage de sélection actuelle du `TextField` en utilisant la méthode `getSelectionRange()`. Cela renvoie un objet `SelectionRange` représentant les indices de début et de fin du texte sélectionné.

:::tip Utiliser `getSelectedText()` vs données de l'événement
Bien que vous puissiez appeler `getSelectedText()` manuellement à l'intérieur d'un gestionnaire d'événements, il est plus efficace d'utiliser les données de sélection fournies dans la charge utile de l'événement—comme dans un `SelectionChangeEvent`—pour éviter des recherches supplémentaires.
:::

## Correspondance de Modèle {#pattern-matching}

Vous pouvez utiliser la méthode `setPattern()` pour définir une règle de validation pour le `TextField` en utilisant une expression régulière. Définir un modèle ajoute une validation de contrainte qui exige que la valeur d'entrée corresponde au modèle spécifié.

Le modèle doit être une expression régulière [JavaScript valide](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), telle qu'interprétée par le navigateur. Le drapeau `u` (Unicode) est appliqué en interne pour garantir une correspondance précise des points de code Unicode. Ne pas envelopper le modèle dans des barres obliques (`/`), car celles-ci ne sont pas nécessaires et seront traitées comme des caractères littéraux.

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // e.g. ABC12
```

Si aucun modèle n'est fourni, ou si la syntaxe est invalide, la règle de validation est ignorée.

:::tip Aide Contextuelle
Lors de l'utilisation de modèles complexes pour un `TextField`, envisagez d'utiliser une combinaison des méthodes `setLabel()`, `setHelperText()`, et `setTooltipText()` pour fournir des indices et des conseils supplémentaires.
:::

## Longueur Minimale et Maximale {#minimum-and-maximum-length}

Le composant `TextField` prend en charge la validation des contraintes basées sur le nombre de caractères saisies par l'utilisateur. Cela peut être contrôlé en utilisant les méthodes `setMinLength()` et `setMaxLength()`. Utilisez les deux méthodes pour définir une limite claire des longueurs de saisie acceptables.

:::info Exigences de Longueur
Par défaut, le champ affiche un message lorsque la valeur d'entrée est hors limites, indiquant à l'utilisateur s'il doit raccourcir ou allonger sa saisie. Ce message peut être remplacé avec la méthode `setInvalidMessage()`.
:::

### `setMinLength()` {#setminlength}

Cette méthode définit le **nombre minimum d'unités de code UTF-16** qui doivent être saisies pour que le champ soit considéré comme valide. La valeur doit être un nombre entier et ne doit pas dépasser la longueur maximale configurée.

```java
textField.setMinLength(5); // L'utilisateur doit entrer au moins 5 caractères
```

Si la saisie contient moins de caractères que le minimum requis, la saisie échouera la validation de contrainte. Cette règle s'applique uniquement lorsque l'utilisateur change la valeur du champ.

### `setMaxLength()` {#setmaxlength}

Cette méthode définit le **nombre maximal d'unités de code UTF-16** autorisées dans le champ de texte. La valeur doit être `0` ou supérieure. Si elle n'est pas définie, ou définie sur une valeur invalide, aucune limite maximale n'est appliquée.

```java
textField.setMaxLength(20); // L'utilisateur ne peut pas entrer plus de 20 caractères
```

Le champ échoue à la validation de contrainte si la longueur de l'entrée dépasse la longueur minimale. Comme avec `setMinLength()`, cette validation n'est déclenchée que lorsque la valeur est changée par l'utilisateur.

## Meilleures Pratiques {#best-practices}

La section suivante décrit quelques meilleures pratiques suggérées pour l'utilisation du `TextField`.

- **Fournir des Étiquettes et Instructions Claires** : Étiquetez le `TextField` clairement pour indiquer le type d'information que les utilisateurs devraient entrer. Fournissez un texte d'instruction supplémentaire ou des valeurs de remplacement pour guider les utilisateurs et définir les attentes pour la saisie requise.

- **Correction Orthographique et Auto-Complétion** : Lorsqu'il est applicable, envisagez d'utiliser la vérification orthographique avec `setSpellCheck()` et/ou d'utiliser l'auto-complétion dans un `TextField`. Ces fonctionnalités peuvent aider les utilisateurs à saisir des informations plus rapidement et à réduire les erreurs en fournissant des valeurs suggérées basées sur des saisies précédentes ou des options prédéfinies.

- **Accessibilité** : Utilisez le composant `TextField` en tenant compte de l'accessibilité, en respectant les normes d'accessibilité telles que le bon étiquetage, la prise en charge de la navigation au clavier et la compatibilité avec les technologies d'assistance. Assurez-vous que les utilisateurs ayant des handicaps peuvent interagir efficacement avec le `TextField`.
