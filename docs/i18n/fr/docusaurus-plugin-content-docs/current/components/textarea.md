---
title: TextArea
sidebar_position: 130
_i18n_hash: c25007720c315e5b0b26197e1fdfff61
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

Le composant `TextArea` fournit un champ de saisie de texte multi-lignes où les utilisateurs peuvent taper et éditer de longs blocs de texte. Il prend en charge des limites maximales de caractères, la structure des paragraphes, le renvoi à la ligne, et des règles de validation pour contrôler la manière dont la saisie est traitée.

<!-- INTRO_END -->

## Création d'un `TextArea` {#creating-a-textarea}

Créez un `TextArea` en passant un label à son constructeur. Des propriétés comme le texte de remplacement, les limites de caractères, et le comportement de renvoi à la ligne peuvent être configurées via des méthodes de setter.

<ComponentDemo 
path='/webforj/textarea?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaView.java'
height = '300px'
/>

## Gestion des paragraphes {#managing-paragraphs}

Le composant `TextArea` fournit des fonctionnalités pour gérer les paragraphs, ce qui le rend idéal pour des applications nécessitant l'édition de documents ou la saisie de texte structuré.

Voici un exemple rapide de la façon de construire et de manipuler le contenu des paragraphes :

```java
TextArea textArea = new TextArea();

// Insérer un paragraphe au début
textArea.addParagraph(0, "Ceci est le premier paragraphe.");

// Ajouter un autre paragraphe à la fin
textArea.addParagraph("Voici un deuxième paragraphe.");

// Ajouter du contenu supplémentaire au premier paragraphe
textArea.appendToParagraph(0, " Cette phrase continue la première.");

// Supprimer le deuxième paragraphe
textArea.removeParagraph(1);

// Récupérer et imprimer tous les paragraphes actuels
List<String> paragraphs = textArea.getParagraphs();
for (int i = 0; i < paragraphs.size(); i++) {
  System.out.println("Paragraphe " + i + ": " + paragraphs.get(i));
}
```

## Validation {#validation}

Le composant `TextArea` prend en charge deux types complémentaires de validation : les contraintes structurelles et les contraintes de contenu.

**Les contraintes structurelles** se concentrent sur l'organisation et la présentation visuelle du texte. Par exemple :
- `setLineCountLimit(int maxLines)` limite le nombre de lignes autorisées dans le champ de texte.
- `setParagraphLengthLimit(int maxCharsPerLine)` limite le nombre de caractères par paragraphe (ou ligne), aidant à faire respecter les normes de lisibilité ou de formatage.

**Les contraintes de contenu**, en revanche, traitent de la quantité totale de texte saisie, indépendamment de la façon dont elle est distribuée :
- `setMaxLength(int maxChars)` fixe le nombre maximal de caractères autorisés dans tous les paragraphes.
- `setMinLength(int minChars)` impose une longueur minimale, garantissant qu'un contenu suffisant soit fourni.

La démonstration suivante permet aux utilisateurs d'ajuster en temps réel les limites de validation—comme le nombre maximum de caractères, la longueur des paragraphes, et le nombre de lignes—et de voir comment le `TextArea` réagit.
	
<ComponentDemo 
path='/webforj/textareavalidation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java'
height = '550px'
/>

## Renvoi de mots et renvoi à la ligne {#word-wrap-and-line-wrapping}

Vous pouvez contrôler si le texte se renvoie ou défile horizontalement en utilisant `setLineWrap()`. Lorsque le renvoi est désactivé, les lignes continuent horizontalement au-delà de la zone visible, nécessitant un défilement. Lorsqu'il est activé, le texte se renvoie automatiquement à la ligne suivante lorsqu'il atteint le bord du composant.

Pour affiner davantage le comportement du renvoi, `setWrapStyle()` vous permet de choisir entre deux styles :
- `WORD_BOUNDARIES` renvoie le texte à des mots entiers, préservant ainsi le flux de lecture naturel.
- `CHARACTER_BOUNDARIES` renvoie à des caractères individuels, permettant un meilleur contrôle sur la mise en page, surtout dans des conteneurs étroits ou à largeur fixe.

Ces options de renvoi fonctionnent en tandem avec des contraintes structurelles comme les limites de nombre de lignes et de longueur de paragraphes. Tandis que le renvoi détermine *comment* le texte s'écoule dans l'espace disponible, les limites structurelles définissent *combien* d'espace le texte est autorisé à occuper. Ensemble, elles aident à maintenir à la fois la structure visuelle et les limites de saisie utilisateur.

<ComponentDemo 
path='/webforj/textareawrap?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java'
height = '400px'
/>

## Texte prédit {#predicted-text}

Le composant `TextArea` prend en charge des suggestions de texte intelligentes pour aider les utilisateurs à taper plus rapidement et avec moins d'erreurs. À mesure que les utilisateurs saisissent du texte, des suggestions préventives apparaissent en fonction de l'entrée actuelle, leur permettant de compléter des phrases courantes ou attendues.

Les prédictions peuvent être acceptées en appuyant sur la touche `Tab` ou `Flèche droite`, insérant ainsi le texte suggéré dans la saisie de manière transparente. Si aucune prédiction appropriée n'est disponible à un moment donné, la saisie reste inchangée, et l'utilisateur peut continuer à taper sans interruption—garantissant que cette fonctionnalité ne gêne jamais.

Ce comportement prédictif améliore à la fois la rapidité et l'exactitude, en particulier dans des scénarios de saisie répétitive ou des applications où la cohérence des phrases est importante.

<ComponentDemo 
path='/webforj/textareapredictedtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java'
height = '400px'
/>

:::info
Cette démonstration utilise l'[API Datamuse](https://datamuse.com/) pour fournir des suggestions de mots basées sur l'entrée de l'utilisateur. La qualité et la pertinence des prédictions dépendent entièrement du jeu de données et du mécanisme de notation de l'API. Cela n'utilise pas de modèles d'IA ou de modèles de langage d'envergure (LLMs) ; les suggestions sont générées à partir d'un moteur léger basé sur des règles axées sur la similarité lexicale.
:::

## État de lecture seule et désactivé {#read-only-and-disabled-state}

Le composant `TextArea` peut être réglé en mode lecture seule ou désactivé pour contrôler l'interaction de l'utilisateur.

Un champ de texte **en lecture seule** permet aux utilisateurs de voir et de sélectionner le contenu, mais pas de l'éditer. Cela est utile pour afficher des informations dynamiques ou pré-remplies qui doivent rester inchangées.

Un champ de texte **désactivé**, en revanche, bloque toute interaction—y compris le focus et la sélection de texte—et est généralement stylisé comme inactif ou grisé.

Utilisez le mode lecture seule lorsque le contenu est pertinent mais immuable, et le mode désactivé lorsque la saisie n'est pas actuellement applicable ou devrait être temporairement inactive.

<ComponentDemo 
path='/webforj/textareastates?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java'
height = '300px'
/>

## Stylisation {#styling}

<TableBuilder name="TextArea" />
