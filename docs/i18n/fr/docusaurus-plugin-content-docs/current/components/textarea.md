---
title: TextArea
sidebar_position: 130
description: >-
  Capture multi-line input with the TextArea component, including paragraph
  management, character limits, wrapping, and validation.
_i18n_hash: f9863352a124e1af3575a849204b97ed
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

Le composant `TextArea` fournit un champ de saisie de texte multi-lignes où les utilisateurs peuvent taper et éditer de plus longs blocs de texte. Il prend en charge les limites de caractères maximales, la structure des paragraphes, le retour à la ligne et les règles de validation pour contrôler la manière dont l'entrée est gérée.

<!-- INTRO_END -->

## Création d'un `TextArea` {#creating-a-textarea}

Créez un `TextArea` en passant un label à son constructeur. Des propriétés comme le texte d'espace réservée, les limites de caractères et le comportement de retour à la ligne peuvent être configurées via des méthodes de définition.

<ComponentDemo
path='/webforj/textarea'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaView.java']}
height='300px'
/>

## Gestion des paragraphes {#managing-paragraphs}

Le composant `TextArea` fournit des fonctionnalités pour la gestion des paragraphes de texte, ce qui le rend idéal pour les applications nécessitant l'édition de documents ou la saisie de texte structuré.

Voici un exemple rapide de comment construire et manipuler le contenu des paragraphes :

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

Le composant `TextArea` prend en charge deux types de validation complémentaires : les contraintes structurelles et les contraintes de contenu.

**Les contraintes structurelles** se concentrent sur la façon dont le texte est organisé et visuellement agencé. Par exemple :
- `setLineCountLimit(int maxLines)` limite le nombre de lignes autorisées dans la zone de texte.
- `setParagraphLengthLimit(int maxCharsPerLine)` limite le nombre de caractères par paragraphe (ou ligne), aidant à faire respecter la lisibilité ou les normes de formatage.

**Les contraintes de contenu**, en revanche, traitent de la quantité totale de texte saisi, indépendamment de la façon dont il est distribué :
- `setMaxLength(int maxChars)` plafonne le nombre total de caractères autorisés dans tous les paragraphes.
- `setMinLength(int minChars)` impose une longueur minimale, garantissant qu'un contenu suffisant soit fourni.

La démo suivante permet aux utilisateurs d'ajuster les limites de validation—comme le nombre maximum de caractères, la longueur des paragraphes et le nombre de lignes—en temps réel et de voir comment le `TextArea` réagit.

<ComponentDemo
path='/webforj/textareavalidation'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java']}
height='550px'
/>

## Retour à la ligne et retour automatique à la ligne {#word-wrap-and-line-wrapping}

Vous pouvez contrôler si le texte se renvoie à la ligne ou défile horizontalement en utilisant `setLineWrap()`. Lorsque le retour à la ligne est désactivé, les lignes continuent horizontalement au-delà de la zone visible, nécessitant un défilement. Lorsqu'il est activé, le texte se renvoie automatiquement à la ligne suivante lorsqu'il atteint le bord du composant.

Pour affiner davantage le comportement du retour à la ligne, `setWrapStyle()` vous permet de choisir entre deux styles :
- `WORD_BOUNDARIES` renvoie le texte à des mots entiers, préservant le flux de lecture naturel.
- `CHARACTER_BOUNDARIES` renvoie à des caractères individuels, permettant un meilleur contrôle sur la mise en page, surtout dans des conteneurs étroits ou de largeur fixe.

Ces options de retour à la ligne travaillent main dans la main avec les contraintes structurelles comme les limites de nombre de lignes et de longueur de paragraphes. Tandis que le retour à la ligne détermine *comment* le texte s'écoule dans l'espace disponible, les limites structurelles définissent *combien* d'espace le texte est autorisé à occuper. Ensemble, elles aident à maintenir à la fois la structure visuelle et les limites d'entrée des utilisateurs.

<ComponentDemo
path='/webforj/textareawrap'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java']}
height='400px'
/>

## Texte prédit {#predicted-text}

Le composant `TextArea` prend en charge des suggestions de texte intelligentes pour aider les utilisateurs à taper plus rapidement et avec moins d'erreurs. Au fur et à mesure que les utilisateurs saisissent du texte, des suggestions prédictives apparaissent en fonction de l'entrée actuelle, leur permettant de compléter des phrases courantes ou attendues.

Les prédictions peuvent être acceptées en appuyant sur la touche `Tab` ou `ArrowRight`, insérant le texte suggéré dans l'entrée de manière transparente. Si aucune prédiction adéquate n'est disponible à un moment donné, l'entrée reste inchangée, et l'utilisateur peut continuer à taper sans interruption—garantissant que la fonctionnalité ne gêne jamais.

Ce comportement prédictif améliore à la fois la vitesse et l'exactitude, en particulier dans des scénarios d'entrée répétitive ou dans des applications où la cohérence des phrases est importante.

<ComponentDemo
path='/webforj/textareapredictedtext'
files={[
  'src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java',
  'src/main/frontend/css/textarea/text-area-predicted-text-view.css',
]}
height='400px'
/>

:::info
Cette démo utilise l'[API Datamuse](https://datamuse.com/) pour fournir des suggestions de mots basées sur l'entrée de l'utilisateur. La qualité et la pertinence des prédictions dépendent entièrement de l'ensemble de données et du mécanisme de notation de l'API. Elle n'utilise pas de modèles d'IA ou de modèles de langage de grande taille (LLM) ; les suggestions sont générées par un moteur léger basé sur des règles axées sur la similarité lexicale.
:::

## État en lecture seule et désactivé {#read-only-and-disabled-state}

Le composant `TextArea` peut être réglé sur lecture seule ou désactivé pour contrôler l'interaction de l'utilisateur.

Une zone de texte **en lecture seule** permet aux utilisateurs de voir et de sélectionner le contenu, mais pas de l'éditer. Cela est utile pour afficher des informations dynamiques ou pré-remplies qui doivent rester inchangées.

Une zone de texte **désactivée**, en revanche, bloque toute interaction—y compris le focus et la sélection de texte—et est généralement stylée comme inactive ou grisée.

Utilisez le mode lecture seule lorsque le contenu est pertinent mais immutable, et le mode désactivé lorsque l'entrée n'est pas actuellement applicable ou doit être temporairement inactive.

<ComponentDemo
path='/webforj/textareastates'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java']}
height='300px'
/>

## Style {#styling}

<TableBuilder name="TextArea" />
