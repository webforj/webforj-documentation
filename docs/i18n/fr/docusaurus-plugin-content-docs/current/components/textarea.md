---
title: TextArea
sidebar_position: 130
_i18n_hash: e8956f1a5bf39eab9a42244ff8d5ff21
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

Le composant `TextArea` fournit un champ de saisie de texte multi-lignes où les utilisateurs peuvent taper et éditer de plus longs blocs de texte. Il prend en charge des limites de caractères maximales, la structure des paragraphes, le retour à la ligne et des règles de validation pour contrôler la façon dont l'entrée est gérée.

<!-- INTRO_END -->

## Création d'un `TextArea` {#creating-a-textarea}

Créez un `TextArea` en passant une étiquette à son constructeur. Des propriétés comme le texte du placeholder, les limites de caractères et le comportement de retour à la ligne peuvent être configurées via des méthodes de définition.

<ComponentDemo 
path='/webforj/textarea?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaView.java'
height = '300px'
/>

## Gestion des paragraphes {#managing-paragraphs}

Le composant `TextArea` fournit des fonctionnalités pour gérer les paragraphes de texte, ce qui le rend idéal pour les applications nécessitant un editing de documents ou une saisie de texte structurée.

Voici un exemple rapide de la manière de construire et de manipuler le contenu des paragraphes :

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

**Les contraintes structurelles** se concentrent sur la façon dont le texte est organisé et disposé visuellement. Par exemple :
- `setLineCountLimit(int maxLines)` restreint le nombre de lignes autorisées dans le champ de texte.
- `setParagraphLengthLimit(int maxCharsPerLine)` limite le nombre de caractères par paragraphe (ou ligne), aidant à faire respecter des normes de lisibilité ou de mise en forme.

**Les contraintes de contenu**, quant à elles, concernent la quantité totale de texte saisie, peu importe comment elle est distribuée :
- `setMaxLength(int maxChars)` limite le nombre total de caractères autorisés dans tous les paragraphes.
- `setMinLength(int minChars)` impose une longueur minimale, garantissant qu'un contenu suffisant est fourni.

La démo suivante permet aux utilisateurs d'ajuster les limites de validation—telles que le nombre maximum de caractères, la longueur des paragraphes et le nombre de lignes—en temps réel et de voir comment le `TextArea` réagit.

<ComponentDemo 
path='/webforj/textareavalidation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java'
height = '550px'
/>

## Retour à la ligne et retour à la ligne {#word-wrap-and-line-wrapping}

Vous pouvez contrôler si le texte se renvoie ou défile horizontalement en utilisant `setLineWrap()`. Lorsque le retour à la ligne est désactivé, les lignes continuent horizontalement au-delà de la zone visible, nécessitant un défilement. Lorsqu'il est activé, le texte se renvoie automatiquement à la ligne suivante lorsqu'il atteint le bord du composant.

Pour affiner davantage le comportement du retour à la ligne, `setWrapStyle()` vous permet de choisir entre deux styles :
- `WORD_BOUNDARIES` renvoie le texte à des mots entiers, préservant le flux naturel de lecture.
- `CHARACTER_BOUNDARIES` renvoie à des caractères individuels, permettant un contrôle plus précis de la mise en page, notamment dans des conteneurs étroits ou à largeur fixe.

Ces options de retour à la ligne fonctionnent main dans la main avec des contraintes structurelles telles que les limites de compte de lignes et de longueur de paragraphes. Alors que le retour à la ligne détermine *comment* le texte s'écoule dans l'espace disponible, les limites structurelles définissent *combien d'espace* le texte est autorisé à occuper. Ensemble, ils aident à maintenir à la fois la structure visuelle et les limites des saisies utilisateur.

<ComponentDemo 
path='/webforj/textareawrap?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java'
height = '400px'
/>

## Texte prédit {#predicted-text}

Le composant `TextArea` prend en charge des suggestions de texte intelligentes pour aider les utilisateurs à taper plus rapidement et avec moins d'erreurs. Au fur et à mesure que les utilisateurs saisissent du texte, des suggestions prédictives apparaissent en fonction de l'entrée actuelle, leur permettant de compléter des phrases courantes ou attendues.

Les prédictions peuvent être acceptées en appuyant sur la touche `Tab` ou `ArrowRight`, insérant le texte suggéré dans l'entrée sans interruption. Si aucune prédiction appropriée n'est disponible à un moment donné, l'entrée demeure inchangée, et l'utilisateur peut continuer à taper sans interruption—garantissant que la fonctionnalité ne se met jamais en travers du chemin.

Ce comportement prédictif améliore à la fois la vitesse et la précision, en particulier dans des scénarios d'entrée répétitive ou des applications où la cohérence dans la formulation est importante.

<ComponentDemo 
path='/webforj/textareapredictedtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java'
cssURL='/css/textarea/text-area-predicted-text-view.css'
height = '400px'
/>

:::info
Cette démo utilise l'[API Datamuse](https://datamuse.com/) pour fournir des suggestions de mots basées sur l'entrée de l'utilisateur. La qualité et la pertinence des prédictions dépendent entièrement de l'ensemble de données et du mécanisme de scoring de l'API. Elle n'utilise pas de modèles d'IA ou de modèles de langage de grande taille (LLM) ; les suggestions sont générées à partir d'un moteur léger basé sur des règles axé sur la similarité lexicale.
:::

## État en lecture seule et désactivé {#read-only-and-disabled-state}

Le composant `TextArea` peut être configuré pour être en lecture seule ou désactivé pour contrôler l'interaction de l'utilisateur.

Un champ de texte **en lecture seule** permet aux utilisateurs de visualiser et de sélectionner le contenu, mais pas de l'éditer. Cela est utile pour afficher des informations dynamiques ou pré-remplies qui doivent rester inchangées.

Un champ de texte **désactivé**, en revanche, bloque toute interaction—y compris le focus et la sélection de texte—et est généralement stylé comme inactif ou grisé.

Utilisez le mode lecture seule lorsque le contenu est pertinent mais immuable, et le mode désactivé lorsque l'entrée n'est actuellement pas applicable ou doit être temporairement inactive.

<ComponentDemo 
path='/webforj/textareastates?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java'
height = '300px'
/>

## Style {#styling}

<TableBuilder name="TextArea" />
