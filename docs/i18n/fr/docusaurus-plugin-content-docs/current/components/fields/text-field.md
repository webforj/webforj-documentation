---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
_i18n_hash: 0d350dce3bf30a09bc54f089107b7a25
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

Le composant `TextField` permet aux utilisateurs d'entrer et de modifier du texte sur une seule ligne. Vous pouvez configurer le champ pour afficher un clavier virtuel spécifique, tel qu'un pavé numérique, une saisie d'e-mail, une saisie de téléphone ou une saisie d'URL. Le composant fournit également une validation intégrée pour rejeter les valeurs qui ne respectent pas le type spécifié.

<!-- INTRO_END -->

## Usages {#usages}

<ParentLink parent="Field" />

Le `TextField` est adapté à un large éventail de scénarios où une entrée ou une modification de texte est requise. Voici quelques exemples de quand utiliser le `TextField` :

1. **Entrées de formulaire** : Un `TextField` est couramment utilisé dans les formulaires pour capturer les saisies des utilisateurs, comme des noms, des adresses ou des commentaires. Il est préférable d'utiliser un `TextField` dans une application lorsque l'entrée est généralement suffisamment courte pour tenir sur une ligne.

2. **Fonctionnalité de recherche** : Le `TextField` peut être utilisé pour fournir une zone de recherche, permettant aux utilisateurs d'entrer des requêtes de recherche.

3. **Édition de texte** : Un `TextField` est idéal pour les applications nécessitant une modification de texte ou la création de contenu, telles que les éditeurs de documents, les applications de chat ou les applications de prise de notes.

## Types {#types}

Vous pouvez spécifier le type du `TextField` en utilisant la méthode `setType()`. De même, vous pouvez récupérer le type en utilisant la méthode `getType()`, qui renverra une valeur d'énumération.

- `Type.TEXT` : C'est le type par défaut d'un `TextField` et il supprime automatiquement les sauts de ligne de la valeur d'entrée.

- `Type.EMAIL` : Ce type est destiné à la saisie d'adresses e-mail. Il valide l'entrée selon la syntaxe standard des e-mails. De plus, il fournit aux navigateurs et appareils compatibles un clavier dynamique qui simplifie le processus de saisie en incluant des touches couramment utilisées comme <kbd>.com</kbd> et <kbd>@</kbd>.

  :::note
  Bien que cette validation confirme le format de l'adresse e-mail, elle ne garantie pas que l'adresse e-mail existe.
  :::

- `Type.TEL` : Ce type est utilisé pour entrer un numéro de téléphone. Le champ affichera un pavé numérique sur certains appareils avec des claviers dynamiques.

- `Type.URL` : Ce type est destiné à la saisie d'URL. Il valide si un utilisateur saisit une URL qui inclut un schéma et un nom de domaine, par exemple : https://webforj.com. De plus, il fournit aux navigateurs et appareils compatibles un clavier dynamique qui simplifie le processus de saisie en incluant des touches couramment utilisées comme <kbd>:</kbd>, <kbd>/</kbd> et <kbd>.com</kbd>.

- `Type.SEARCH` : Un champ de texte sur une seule ligne pour entrer des chaînes de recherche. Les sauts de ligne sont automatiquement supprimés de la valeur d'entrée.

<ComponentDemo
path='/webforj/textfield'
files={['src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java']}
height='250px'
/>

## Valeur du champ {#field-value}

La valeur d'un `TextField` représente la saisie actuelle de l'utilisateur sous forme de chaîne. Dans webforJ, cela peut être accessible en utilisant la méthode `getValue()`, ou mis à jour programatiquement avec `setValue(String)`.

```java
//Définir le contenu initial
textField.setValue("Contenu initial");

//Récupérer la valeur actuelle
String text = textField.getValue();
```

Si la méthode `getValue()` est utilisée sur un champ sans valeur actuelle, elle renvoie une chaîne vide (`""`).

Ce comportement est conforme à la façon dont l'élément HTML `<input type="text">` expose sa valeur via JavaScript.

:::tip Combiner la gestion de la valeur avec la validation
Appliquez des contraintes comme un [patron](#pattern-matching), une [longueur minimale](#setminlength), ou une [longueur maximale](#setmaxlength) pour définir quand une valeur est considérée comme valide.
:::

## Texte d'espace réservé {#placeholder-text}

Vous pouvez définir un texte d'espace réservé pour le `TextField` en utilisant la méthode `setPlaceholder()`. Le texte d'espace réservé est affiché lorsque le champ est vide, aidant à inciter l'utilisateur à saisir une entrée appropriée dans le `TextField`.

## Texte sélectionné {#selected-text}

Il est possible d'interagir avec la classe `TextField` pour récupérer le texte sélectionné par un utilisateur et obtenir des informations sur la sélection de l'utilisateur. Vous pouvez récupérer le texte sélectionné dans le `TextField` en utilisant la méthode `getSelectedText()`. Ce comportement serait couramment utilisé en conjonction avec un événement.

```java
TextField textField = new TextField("Entrez quelque chose...");
Button getSelectionBtn = new Button("Obtenir le texte sélectionné");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("Texte sélectionné : " + selected);
});
```

De même, il est possible de récupérer la plage de sélection actuelle du `TextField` en utilisant la méthode `getSelectionRange()`. Cela renvoie un objet `SelectionRange` représentant les indices de début et de fin du texte sélectionné.

:::tip Changements de valeur et sélection
Utilisez `onValueChange()` pour gérer les modifications de la valeur du champ. Son `ValueChangeEvent<String>` fournit la nouvelle valeur via `event.getValue()`, mais ne contient pas de données de sélection. Pour lire la sélection actuelle, appelez `getSelectedText()` ou `getSelectionRange()` sur le champ.
:::

## Correspondance de motif {#pattern-matching}

Vous pouvez utiliser la méthode `setPattern()` pour définir une règle de validation pour le `TextField` en utilisant une expression régulière. Définir un motif ajoute une validation de contrainte qui exige que la valeur d'entrée corresponde au motif spécifié.

Le motif doit être une [expression régulière JavaScript valide](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), telle qu'interprétée par le navigateur. Le drapeau `u` (Unicode) est appliqué en interne pour garantir un appariement précis des points de code Unicode. Ne pas entourer le motif de barres obliques (`/`), car celles-ci ne sont pas requises et seront traitées comme des caractères littéraux.

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // par exemple, ABC12
```

Si aucun motif n'est fourni, ou si la syntaxe est invalide, la règle de validation est ignorée.

:::tip Aide contextuelle
Lorsque vous utilisez des motifs complexes pour un `TextField`, envisagez d'utiliser une combinaison des méthodes `setLabel()`, `setHelperText()`, et `setTooltipText()` pour fournir des indices et des conseils supplémentaires.
:::

## Longueur minimale et maximale {#minimum-and-maximum-length}

Le composant `TextField` prend en charge la validation de contrainte basée sur le nombre de caractères saisis par l'utilisateur. Cela peut être contrôlé en utilisant les méthodes `setMinLength()` et `setMaxLength()`. Utilisez les deux méthodes pour définir une limite claire des longueurs d'entrée acceptables.

:::info Exigences de longueur
Par défaut, le champ affiche un message lorsque la valeur d'entrée est hors limites, indiquant à l'utilisateur s'il doit raccourcir ou allonger son entrée. Ce message peut être remplacé par la méthode `setInvalidMessage()`.
:::

### `setMinLength()` {#setminlength}

Cette méthode définit le **nombre minimum d'unités de code UTF-16** qui doivent être saisies pour que le champ soit considéré comme valide. La valeur doit être un nombre entier et ne doit pas dépasser la longueur maximale configurée.

```java
textField.setMinLength(5); // L'utilisateur doit entrer au moins 5 caractères
```

Si l'entrée contient moins de caractères que le minimum requis, l'entrée échouera la validation de contrainte. Cette règle ne s'applique que lorsque l'utilisateur modifie la valeur du champ.

### `setMaxLength()` {#setmaxlength}

Cette méthode définit le **nombre maximum d'unités de code UTF-16** autorisées dans le champ de texte. La valeur doit être `0` ou supérieure. Si elle n'est pas définie, ou si elle est définie à une valeur invalide, aucune limite maximale n'est appliquée.

```java
textField.setMaxLength(20); // L'utilisateur ne peut pas entrer plus de 20 caractères
```

Le champ échoue la validation de contrainte si la longueur de l'entrée dépasse la longueur minimale. Tout comme avec `setMinLength()`, cette validation est uniquement déclenchée lorsque la valeur est modifiée par l'utilisateur.

## Meilleures pratiques {#best-practices}

La section suivante décrit quelques meilleures pratiques suggérées pour l'utilisation du `TextField`.

- **Fournir des étiquettes et des instructions claires** : Étiquetez clairement le `TextField` pour indiquer le type d'informations que les utilisateurs doivent saisir. Fournissez un texte d'instruction supplémentaire ou des valeurs d'espace réservé pour guider les utilisateurs et définir des attentes quant à l'entrée requise.

- **Vérification orthographique et auto-complétion** : Lorsque cela est applicable, envisagez d'utiliser la vérification orthographique avec `setSpellCheck()` et/ou d'utiliser l'auto-complétion dans un `TextField`. Ces fonctionnalités peuvent aider les utilisateurs à saisir des informations plus rapidement et à réduire les erreurs en fournissant des valeurs suggérées basées sur des saisies précédentes ou des options prédéfinies.

- **Accessibilité** : fournissez des étiquettes claires, la navigation au clavier et le support pour les technologies d'assistance afin que les utilisateurs handicapés puissent utiliser le champ.
