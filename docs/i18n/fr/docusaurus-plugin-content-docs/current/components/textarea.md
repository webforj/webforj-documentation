---
title: TextArea
sidebar_position: 130
_i18n_hash: f109f006fcd252bf81b6cccb83d38a50
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

Le composant `TextArea` dans webforJ offre une solution pour l'entrée de texte multi-lignes. Les utilisateurs finaux peuvent taper et éditer du texte librement, tandis que les développeurs peuvent établir des limites raisonnables grâce à des fonctionnalités telles que les limites de caractères maximum, la structure des paragraphes et les règles de validation.

Voici un exemple d'un `TextArea` pour saisir du texte multi-lignes :

<ComponentDemo 
path='/webforj/textarea?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaView.java'
height = '300px'
/>

## Gestion des paragraphes {#managing-paragraphs}

Le composant `TextArea` fournit des fonctionnalités pour gérer les paragraphes de texte, ce qui le rend idéal pour les applications qui nécessitent l'édition de documents ou une entrée de texte structurée.

Voici un exemple rapide de la façon dont composer et manipuler le contenu des paragraphes :

```java
TextArea textArea = new TextArea();

// Insérer un paragraphe au début
textArea.addParagraph(0, "Ceci est le premier paragraphe.");

// Ajouter un autre paragraphe à la fin
textArea.addParagraph("Voici un deuxième paragraphe.");

// Ajouter un contenu supplémentaire au premier paragraphe
textArea.appendToParagraph(0, " Cette phrase poursuit la première.");

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

**Les contraintes structurelles** portent sur la façon dont le texte est organisé et visuellement disposé. Par exemple :
- `setLineCountLimit(int maxLines)` limite le nombre de lignes autorisées dans la zone de texte.
- `setParagraphLengthLimit(int maxCharsPerLine)` limite le nombre de caractères par paragraphe (ou ligne), contribuant à faire respecter les normes de lisibilité ou de mise en forme.

**Les contraintes de contenu**, en revanche, traitent de l'ensemble de la quantité de texte saisie, indépendamment de sa distribution :
- `setMaxLength(int maxChars)` fixe le nombre total de caractères autorisés dans tous les paragraphes.
- `setMinLength(int minChars)` impose une longueur minimum, assurant qu'un contenu suffisant est fourni.

La démo suivante permet aux utilisateurs d'ajuster les limites de validation—telles que le nombre maximum de caractères, la longueur des paragraphes et le nombre de lignes—en temps réel et de voir comment le `TextArea` réagit.

<ComponentDemo 
path='/webforj/textareavalidation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java'
height = '550px'
/>

## Retour à la ligne et Enroulement de ligne {#word-wrap-and-line-wrapping}

Vous pouvez contrôler si le texte s'enroule ou défile horizontalement à l'aide de `setLineWrap()`. Lorsque l'enroulement est désactivé, les lignes continuent horizontalement au-delà de la zone visible, nécessitant un défilement. Lorsque l'enroulement est activé, le texte s'enroule automatiquement à la ligne suivante lorsqu'il atteint le bord du composant.

Pour affiner davantage le comportement de l'enroulement, `setWrapStyle()` vous permet de choisir entre deux styles :
- `WORD_BOUNDARIES` enroule le texte à des mots entiers, préservant le flux de lecture naturel.
- `CHARACTER_BOUNDARIES` enroule à des caractères individuels, permettant un meilleur contrôle sur la mise en page, en particulier dans des conteneurs étroits ou à largeur fixe.

Ces options d'enroulement fonctionnent main dans la main avec des contraintes structurelles telles que le nombre de lignes et les limites de longueur des paragraphes. Alors que l'enroulement détermine *comment* le texte s'écoule dans l'espace disponible, les limites structurelles définissent *combien* d'espace le texte est autorisé à occuper. Ensemble, ils aident à maintenir à la fois la structure visuelle et les limites d'entrée de l'utilisateur.

<ComponentDemo 
path='/webforj/textareawrap?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java'
height = '400px'
/>

## Texte prédit {#predicted-text}

Le composant `TextArea` prend en charge des suggestions de texte intelligentes pour aider les utilisateurs à taper plus vite et avec moins d'erreurs. Au fur et à mesure que les utilisateurs saisissent du texte, des suggestions prédictives apparaissent en fonction de l'entrée actuelle, leur permettant de compléter des phrases courantes ou attendues.

Les prédictions peuvent être acceptées en appuyant sur la touche `Tab` ou `Flèche Droite`, insérant le texte suggéré dans l'entrée de manière transparente. Si aucune prédiction appropriée n'est disponible à un moment donné, l'entrée reste inchangée, et l'utilisateur peut continuer à taper sans interruption—ce qui garantit que la fonctionnalité ne gêne jamais.

Ce comportement prédictif améliore à la fois la vitesse et la précision, en particulier dans les scénarios d'entrée répétitive ou les applications où la cohérence de la formulation est importante.

<ComponentDemo 
path='/webforj/textareapredictedtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java'
height = '400px'
/>

:::info
Cette démo utilise l'[API Datamuse](https://datamuse.com/) pour fournir des suggestions de mots basées sur l'entrée de l'utilisateur. La qualité et la pertinence des prévisions dépendent entièrement de l'ensemble de données et du mécanisme de notation de l'API. Elle n'utilise pas de modèles d'IA ou de modèles de langage à grande échelle (LLM) ; les suggestions sont générées à partir d'un moteur léger basé sur des règles axées sur la similarité lexicale.
:::

## État lecture seule et désactivé {#read-only-and-disabled-state}

Le composant `TextArea` peut être défini comme lecture seule ou désactivé pour contrôler l'interaction de l'utilisateur.

Une zone de texte **lecture seule** permet aux utilisateurs de visualiser et de sélectionner le contenu, mais pas de l'éditer. Cela est utile pour afficher des informations dynamiques ou pré-remplies qui doivent rester inchangées.

Une zone de texte **désactivée**, en revanche, bloque toute interaction—y compris le focus et la sélection de texte—et est généralement stylée comme inactive ou grisée.

Utilisez le mode lecture seule lorsque le contenu est pertinent mais immuable, et le mode désactivé lorsque l'entrée n'est pas actuellement applicable ou doit être temporairement inactive.

<ComponentDemo 
path='/webforj/textareastates?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java'
height = '300px'
/>

## Stylisation {#styling}

<TableBuilder name="TextArea" />
