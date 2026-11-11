---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
_i18n_hash: 2f01abb14f66d83dd6edd3cc18ac560e
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

Le composant `TextField` permet aux utilisateurs de saisir et de modifier du texte sur une seule ligne. Vous pouvez configurer le champ pour afficher un clavier virtuel spécifique, comme un pavé numérique ou un clavier adapté à la saisie d’adresses e-mail, de numéros de téléphone ou d’URL. Le composant intègre également une validation qui rejette les valeurs non conformes au type spécifié.

<!-- INTRO_END -->

## Utilisations {#usages}

<ParentLink parent="Field" />

Le composant `TextField` convient à un large éventail de situations nécessitant la saisie ou la modification de texte. Voici quelques exemples d’utilisation du composant `TextField` :

1. **Champs de formulaire** : un `TextField` est couramment utilisé dans les formulaires pour recueillir des informations auprès des utilisateurs, comme des noms, des adresses ou des commentaires. Il est préférable d’utiliser un `TextField` dans une application lorsque la saisie est généralement assez courte pour tenir sur une seule ligne.

2. **Fonctionnalité de recherche** : un `TextField` peut servir de champ de recherche dans lequel les utilisateurs saisissent leurs requêtes.

3. **Modification de texte** : un `TextField` est idéal pour les applications qui nécessitent la modification de texte ou la création de contenu, comme les éditeurs de documents, les applications de discussion ou les applications de prise de notes.

## Types {#types}

Vous pouvez spécifier le type du `TextField` à l’aide de la méthode `setType()`. De même, vous pouvez récupérer le type à l’aide de la méthode `getType()`, qui renvoie une valeur d’énumération.

- `Type.TEXT` : type par défaut d’un `TextField`. Il supprime automatiquement les retours à la ligne de la valeur saisie.

- `Type.EMAIL` : ce type sert à saisir des adresses e-mail. Il valide la saisie selon la syntaxe standard des adresses e-mail. De plus, sur les navigateurs et les appareils compatibles, il affiche un clavier dynamique qui simplifie la saisie en proposant des touches couramment utilisées, comme <kbd>.com</kbd> et <kbd>@</kbd>.

  :::note
  Cette validation vérifie le format de l’adresse e-mail, mais ne garantit pas que cette adresse existe.
  :::

- `Type.TEL` : ce type sert à saisir un numéro de téléphone. Sur certains appareils équipés d’un clavier dynamique, le champ affiche un pavé numérique de téléphone.

- `Type.URL` : ce type sert à saisir des URL. Il vérifie que l’URL saisie comporte un schéma et un nom de domaine, par exemple : https://webforj.com. De plus, sur les navigateurs et les appareils compatibles, il affiche un clavier dynamique qui simplifie la saisie en proposant des touches couramment utilisées, comme <kbd>:</kbd>, <kbd>/</kbd> et <kbd>.com</kbd>.

- `Type.SEARCH` : champ de texte sur une seule ligne destiné à la saisie de chaînes de recherche. Les retours à la ligne sont automatiquement supprimés de la valeur saisie.

<ComponentDemo
path='/webforj/textfield'
files={['src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java']}
height='250px'
/>

## Valeur du champ {#field-value}

La valeur d’un `TextField` correspond à la saisie actuelle de l’utilisateur, sous forme de chaîne. Dans webforJ, vous pouvez y accéder à l’aide de la méthode `getValue()` ou la modifier par programmation avec `setValue(String)`.

```java
// Définir le contenu initial
textField.setValue("Initial content");

// Récupérer la valeur actuelle
String text = textField.getValue();
```

Si la méthode `getValue()` est appelée sur un champ sans valeur actuelle, elle renvoie une chaîne vide (`""`).

Ce comportement correspond à la manière dont l’élément HTML `<input type="text">` expose sa valeur via JavaScript.

:::tip Combinez la gestion des valeurs avec la validation
Appliquez des contraintes telles qu’un [motif](#pattern-matching), une [longueur minimale](#setminlength) ou une [longueur maximale](#setmaxlength) pour définir les conditions de validité d’une valeur.
:::

## Texte indicatif {#placeholder-text}

Vous pouvez définir un texte indicatif pour le `TextField` à l’aide de la méthode `setPlaceholder()`. Ce texte s’affiche lorsque le champ est vide et invite l’utilisateur à saisir une valeur appropriée dans le `TextField`.

## Texte sélectionné {#selected-text}

La classe `TextField` permet de récupérer le texte sélectionné par l’utilisateur et d’obtenir des informations sur sa sélection. Pour récupérer le texte sélectionné dans le `TextField`, utilisez la méthode `getSelectedText()`. Ce comportement est souvent utilisé conjointement avec un événement.

```java
TextField textField = new TextField("Saisissez quelque chose...");
Button getSelectionBtn = new Button("Récupérer le texte sélectionné");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("Texte sélectionné : " + selected);
});
```

De même, vous pouvez récupérer la plage de sélection actuelle du `TextField` à l’aide de la méthode `getSelectionRange()`. Celle-ci renvoie un objet `SelectionRange` qui représente les indices de début et de fin du texte sélectionné.

:::tip Modifications de valeur et sélection
Utilisez `onValueChange()` pour gérer les modifications de la valeur du champ. L’événement `ValueChangeEvent<String>` fournit la nouvelle valeur via `event.getValue()`, mais ne contient pas de données de sélection. Pour lire la sélection actuelle, appelez `getSelectedText()` ou `getSelectionRange()` sur le champ.
:::

## Correspondance de motif {#pattern-matching}

Vous pouvez utiliser la méthode `setPattern()` pour définir une règle de validation du `TextField` à l’aide d’une expression régulière. La définition d’un motif ajoute une contrainte de validation qui exige que la valeur saisie corresponde au motif spécifié.

Le motif doit être une [expression régulière JavaScript](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) valide, telle qu’interprétée par le navigateur. L’indicateur `u` (Unicode) est appliqué en interne pour garantir une correspondance exacte des points de code Unicode. N’encadrez pas le motif de barres obliques (`/`), car elles ne sont pas nécessaires et seront traitées comme des caractères littéraux.

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // p. ex. ABC12
```

Si aucun motif n’est fourni ou si sa syntaxe est invalide, la règle de validation est ignorée.

:::tip Aide contextuelle
Lorsque vous utilisez des motifs complexes pour un `TextField`, pensez à combiner les méthodes `setLabel()`, `setHelperText()` et `setTooltipText()`
pour fournir des indications et des conseils supplémentaires.
:::

## Longueurs minimale et maximale {#minimum-and-maximum-length}

Le composant `TextField` prend en charge la validation des contraintes en fonction du nombre de caractères saisis par l’utilisateur. Ce nombre peut être contrôlé à l’aide des méthodes `setMinLength()` et `setMaxLength()`. Utilisez les deux méthodes pour définir clairement les limites de longueur acceptables.

:::info Exigences de longueur
Par défaut, le champ affiche un message lorsque la valeur saisie est hors limites, indiquant à l’utilisateur s’il doit raccourcir ou allonger sa saisie. Ce message peut être remplacé à l’aide de la méthode `setInvalidMessage()`.
:::

### `setMinLength()` {#setminlength}

Cette méthode définit le **nombre minimal d’unités de code UTF-16** à saisir pour que le champ soit considéré comme valide. La valeur doit être un nombre entier et ne doit pas dépasser la longueur maximale configurée.

```java
textField.setMinLength(5); // L’utilisateur doit saisir au moins 5 caractères
```

Si la saisie contient moins de caractères que le minimum requis, elle échoue à la validation des contraintes. Cette règle s’applique uniquement lorsque l’utilisateur modifie la valeur du champ.

### `setMaxLength()` {#setmaxlength}

Cette méthode définit le **nombre maximal d’unités de code UTF-16** autorisé dans le champ de texte. La valeur doit être supérieure ou égale à `0`. Si elle n’est pas définie ou si sa valeur est invalide, aucune limite maximale n’est appliquée.

```java
textField.setMaxLength(20); // L’utilisateur ne peut pas saisir plus de 20 caractères
```

La validation des contraintes échoue si la longueur de la saisie dépasse la longueur maximale. Comme pour `setMinLength()`, cette validation n’est déclenchée que lorsque l’utilisateur modifie la valeur.

## Bonnes pratiques {#best-practices}

La section suivante présente quelques bonnes pratiques recommandées pour l’utilisation du `TextField`.

- **Fournir des libellés et des instructions clairs** : indiquez clairement le type d’information que les utilisateurs doivent saisir dans le `TextField`. Ajoutez des instructions ou des textes indicatifs pour les guider et préciser les attentes concernant la saisie requise.

- **Correction orthographique et saisie semi-automatique** : le cas échéant, pensez à activer la vérification orthographique avec `setSpellCheck()` et/ou à utiliser la saisie semi-automatique dans un `TextField`. Ces fonctionnalités peuvent aider les utilisateurs à saisir des informations plus rapidement et à réduire les erreurs en proposant des valeurs suggérées à partir de saisies précédentes ou d’options prédéfinies.

- **Accessibilité** : fournissez des libellés clairs, une navigation au clavier et une prise en charge des technologies d’assistance afin que les personnes en situation de handicap puissent utiliser le champ.
