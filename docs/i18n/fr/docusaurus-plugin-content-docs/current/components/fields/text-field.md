---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
_i18n_hash: 18d2a614ed2e9c53513a92017b3622e0
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

Le composant `TextField` permet aux utilisateurs d'entrer et de modifier du texte sur une seule ligne. Vous pouvez configurer le champ pour afficher un clavier virtuel spécifique, comme un pavé numérique, une saisie d'email, une saisie de téléphone ou une saisie d'URL. Le composant fournit également une validation intégrée pour rejeter les valeurs qui ne respectent pas le type spécifié.

<!-- INTRO_END -->

## Usages {#usages}

<ParentLink parent="Field" />

Le `TextField` est adapté à un large éventail de scénarios où une saisie ou une modification de texte est nécessaire. Voici quelques exemples de situations où utiliser le `TextField` :

1. **Saisies de formulaire** : Un `TextField` est couramment utilisé dans les formulaires pour capturer les entrées utilisateur, telles que les noms, les adresses ou les commentaires. Il est préférable d'utiliser un `TextField` dans une application lorsque la saisie est généralement suffisamment courte pour tenir sur une seule ligne.

2. **Fonctionnalité de recherche** : Le `TextField` peut être utilisé pour fournir une boîte de recherche, permettant aux utilisateurs d'entrer des requêtes de recherche.

3. **Édition de texte** : Un `TextField` est idéal pour les applications nécessitant l'édition de texte ou la création de contenu, telles que les éditeurs de documents, les applications de chat ou les applications de prise de notes.

## Types {#types}

Vous pouvez spécifier le type du `TextField` en utilisant la méthode `setType()`. De même, vous pouvez récupérer le type en utilisant la méthode `getType()`, qui renverra une valeur enum.

- `Type.TEXT` : C'est le type par défaut pour un `TextField` et supprime automatiquement les sauts de ligne de la valeur d'entrée.

- `Type.EMAIL` : Ce type est destiné à la saisie d'adresses email. Il valide l'entrée selon la syntaxe standard des emails. De plus, il fournit aux navigateurs et appareils compatibles un clavier dynamique qui facilite le processus de saisie en incluant des touches fréquemment utilisées comme <kbd>.com</kbd> et <kbd>@</kbd>.

  :::note
  Bien que cette validation confirme le format de l'adresse email, elle ne garantit pas que l'email existe.
  :::

- `Type.TEL` : Ce type est utilisé pour entrer un numéro de téléphone. Le champ affichera un clavier téléphonique sur certains appareils avec claviers dynamiques.

- `Type.URL` : Ce type est destiné à la saisie d'URLs. Il valide si un utilisateur entre une URL contenant un schéma et un nom de domaine, par exemple : https://webforj.com. De plus, il fournit aux navigateurs et appareils compatibles un clavier dynamique qui facilite le processus de saisie en incluant des touches fréquemment utilisées comme <kbd>:</kbd>, <kbd>/</kbd>, et <kbd>.com</kbd>.

- `Type.SEARCH` : Un champ de texte sur une seule ligne pour entrer des chaînes de recherche. Les sauts de ligne sont automatiquement supprimés de la valeur d'entrée.

<ComponentDemo
path='/webforj/textfield'
files={['src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java']}
height='250px'
/>

## Valeur du champ {#field-value}

La valeur d'un `TextField` représente l'entrée utilisateur actuelle sous forme de chaîne. Dans webforJ, cela peut être accès en utilisant la méthode `getValue()`, ou mis à jour programmétiquement avec `setValue(String)`.

```java
//Définir le contenu initial
textField.setValue("Contenu initial");

//Récupérer la valeur actuelle
String text = textField.getValue();
```

Si la méthode `getValue()` est utilisée sur un champ sans valeur actuelle, elle renvoie une chaîne vide (`""`).

Ce comportement est cohérent avec la façon dont l'élément HTML `<input type="text">` expose sa valeur via JavaScript.

:::tip Combinez la gestion des valeurs avec la validation
Appliquez des contraintes comme un [pattern](#pattern-matching), une [longueur minimale](#setminlength), ou une [longueur maximale](#setmaxlength) pour définir quand une valeur est considérée comme valide.
:::

## Texte de remplacement {#placeholder-text}

Vous pouvez définir un texte de remplacement pour le `TextField` en utilisant la méthode `setPlaceholder()`. Le texte de remplacement est affiché lorsque le champ est vide, aidant à inciter l'utilisateur à entrer une saisie appropriée dans le `TextField`.

## Texte sélectionné {#selected-text}

Il est possible d'interagir avec la classe `TextField` pour récupérer le texte sélectionné par l'utilisateur, et pour obtenir des informations sur la sélection de l'utilisateur. Vous pouvez récupérer le texte sélectionné dans le `TextField` en utilisant la méthode `getSelectedText()`. Ce comportement serait couramment utilisé en conjonction avec un événement.

```java
TextField textField = new TextField("Entrez quelque chose...");
Button getSelectionBtn = new Button("Obtenir le texte sélectionné");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("Texte sélectionné : " + selected);
});
```

De même, il est possible de récupérer la plage de sélection actuelle du `TextField` en utilisant la méthode `getSelectionRange()`. Cela renvoie un objet `SelectionRange` représentant les indices de début et de fin du texte sélectionné.

:::tip Utilisation de `getSelectedText()` vs charge utile d'événement
Bien que vous puissiez appeler `getSelectedText()` manuellement à l'intérieur d'un gestionnaire d'événements, il est plus efficace d'utiliser les données de sélection fournies dans la charge utile de l'événement—comme dans un `SelectionChangeEvent`—pour éviter des recherches supplémentaires.
:::

## Correspondance de motifs {#pattern-matching}

Vous pouvez utiliser la méthode `setPattern()` pour définir une règle de validation pour le `TextField` en utilisant une expression régulière. La définition d'un motif ajoute une validation des contraintes qui exige que la valeur d'entrée corresponde au motif spécifié.

Le motif doit être une [expression régulière JavaScript valide](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), telle qu'interprétée par le navigateur. Le drapeau `u` (Unicode) est appliqué en interne pour garantir un appariement précis des points de code Unicode. Ne pas envelopper le motif dans des barres obliques (`/`), car elles ne sont pas nécessaires et seront traitées comme des caractères littéraux.

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // par exemple ABC12
```

Si aucun motif n'est fourni, ou si la syntaxe est invalide, la règle de validation est ignorée.

:::tip Aide contextuelle
Lors de l'utilisation de motifs complexes pour un `TextField`, envisagez d'utiliser une combinaison des méthodes `setLabel()`, `setHelperText()` et `setTooltipText()`
pour fournir des indications et des conseils supplémentaires.
:::

## Longueur minimale et maximale {#minimum-and-maximum-length}

Le composant `TextField` prend en charge la validation des contraintes basée sur le nombre de caractères saisis par l'utilisateur. Cela peut être contrôlé en utilisant les méthodes `setMinLength()` et `setMaxLength()`. Utilisez les deux méthodes pour définir une limite claire des longueurs d'entrée acceptables.

:::info Exigences de longueur
Par défaut, le champ affiche un message lorsque la valeur d'entrée est hors de portée, indiquant à l'utilisateur s'il doit raccourcir ou allonger sa saisie. Ce message peut être remplacé par la méthode `setInvalidMessage()`.
:::

### `setMinLength()` {#setminlength}

Cette méthode définit le **nombre minimum d'unités de code UTF-16** qui doivent être saisies pour que le champ soit considéré comme valide. La valeur doit être un nombre entier et ne devrait pas dépasser la longueur maximale configurée.

```java
textField.setMinLength(5); // L'utilisateur doit entrer au moins 5 caractères
```

Si l'entrée contient moins de caractères que la longueur minimale requise, l'entrée échouera la validation des contraintes. Cette règle ne s'applique que lorsque l'utilisateur change la valeur du champ.

### `setMaxLength()` {#setmaxlength}

Cette méthode définit le **nombre maximum d'unités de code UTF-16** autorisées dans le champ de texte. La valeur doit être `0` ou supérieure. Si elle n'est pas définie ou définie sur une valeur invalide, aucune longueur maximale n'est appliquée.

```java
textField.setMaxLength(20); // L'utilisateur ne peut pas entrer plus de 20 caractères
```

Le champ échoue la validation des contraintes si la longueur d'entrée dépasse la longueur minimale. Comme avec `setMinLength()`, cette validation n'est déclenchée que lorsque la valeur est modifiée par l'utilisateur.

## Meilleures pratiques {#best-practices}

La section suivante décrit quelques meilleures pratiques suggérées pour l'utilisation du `TextField`.

- **Fournir des étiquettes et des instructions claires** : Étiquetez le `TextField` clairement pour indiquer le type d'informations que les utilisateurs doivent entrer. Fournissez un texte d'instruction supplémentaire ou des valeurs de remplacement pour guider les utilisateurs et définir des attentes pour la saisie requise.

- **Vérification orthographique et auto-complétion** : Lorsque cela est applicable, envisagez d'utiliser la vérification orthographique avec `setSpellCheck()` et/ou d'utiliser l'auto-complétion dans un `TextField`. Ces fonctionnalités peuvent aider les utilisateurs à entrer des informations plus rapidement et à réduire les erreurs en fournissant des valeurs suggérées basées sur des saisies précédentes ou des options prédéfinies.

- **Accessibilité** : Utilisez le composant `TextField` en tenant compte de l'accessibilité, en respectant les normes d'accessibilité telles que l'étiquetage approprié, le support de la navigation au clavier et la compatibilité avec les technologies d'assistance. Assurez-vous que les utilisateurs handicapés peuvent interagir avec le `TextField` de manière efficace.
