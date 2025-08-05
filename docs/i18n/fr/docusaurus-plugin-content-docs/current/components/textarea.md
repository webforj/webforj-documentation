---
title: TextArea
sidebar_position: 130
_i18n_hash: 0ca8e9c1163e55bb86adf44931de139a
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

Le composant `TextArea` dans webforJ offre une solution pour la saisie de texte multi-lignes. Les utilisateurs finaux peuvent librement taper et modifier du texte, tandis que les développeurs peuvent définir des limites raisonnables à l'aide de fonctionnalités comme les limites de caractères maximum, la structure des paragraphes et les règles de validation.

Voici un exemple d'un `TextArea` pour entrer du texte multi-lignes :

<ComponentDemo 
path='/webforj/textarea?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaView.java'
height = '300px'
/>

## Gestion des paragraphes {#managing-paragraphs}

Le composant `TextArea` offre des fonctionnalités pour gérer les paragraphes de texte, ce qui le rend idéal pour les applications nécessitant l'édition de documents ou une saisie de texte structurée.

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

Le composant `TextArea` prend en charge deux types de validation complémentaires : les contraintes structurelles et les contraintes de contenu.

**Les contraintes structurelles** se concentrent sur la façon dont le texte est organisé et visuellement disposé. Par exemple :
- `setLineCountLimit(int maxLines)` limite le nombre de lignes autorisées dans le texte area.
- `setParagraphLengthLimit(int maxCharsPerLine)` limite le nombre de caractères par paragraphe (ou ligne), ce qui aide à faire respecter des normes de lisibilité ou de formatage.

**Les contraintes de contenu**, quant à elles, concernent la quantité totale de texte saisie, indépendamment de sa répartition :
- `setMaxLength(int maxChars)` fixe le nombre total de caractères autorisés dans tous les paragraphes.
- `setMinLength(int minChars)` impose une longueur minimum, garantissant qu'un contenu suffisant soit fourni.

La démonstration suivante permet aux utilisateurs d'ajuster les limites de validation—telles que le nombre maximum de caractères, la longueur des paragraphes et le nombre de lignes—en temps réel et de voir comment le `TextArea` réagit.

<ComponentDemo 
path='/webforj/textareavalidation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java'
height = '550px'
/>

## Renvoyer et envelopper des lignes {#word-wrap-and-line-wrapping}

Vous pouvez contrôler si le texte se renvoie ou défile horizontalement en utilisant `setLineWrap()`. Lorsque le renvoi est désactivé, les lignes continuent horizontalement au-delà de la zone visible, nécessitant du défilement. Lorsqu'il est activé, le texte se renvoie automatiquement à la ligne suivante lorsqu'il atteint le bord du composant.

Pour affiner davantage le comportement du renvoi, `setWrapStyle()` vous permet de choisir entre deux styles :
- `WORD_BOUNDARIES` renvoie le texte à des mots entiers, préservant le flux de lecture naturel.
- `CHARACTER_BOUNDARIES` renvoie aux caractères individuels, permettant un contrôle plus strict sur la mise en page, en particulier dans des conteneurs étroits ou à largeur fixe.

Ces options de renvoi fonctionnent de pair avec des contraintes structurelles telles que les limites du nombre de lignes et de longueur des paragraphes. Alors que le renvoi détermine *comment* le texte s'écoule dans l'espace disponible, les limites structurelles définissent *combien* d'espace le texte est autorisé à occuper. Ensemble, ils aident à maintenir à la fois la structure visuelle et les limites de saisie des utilisateurs.

<ComponentDemo 
path='/webforj/textareawrap?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java'
height = '400px'
/>

## Texte prédit {#predicted-text}

Le composant `TextArea` prend en charge des suggestions de texte intelligentes pour aider les utilisateurs à taper plus rapidement et avec moins d'erreurs. Au fur et à mesure que les utilisateurs saisissent du texte, des suggestions prédictives apparaissent en fonction de l'entrée actuelle, leur permettant de compléter des phrases courantes ou attendues.

Les prédictions peuvent être acceptées en appuyant sur la touche `Tab` ou `FlècheDroite`, insérant le texte suggéré dans l'entrée sans interruption. Si aucune prédiction appropriée n'est disponible à un moment donné, l'entrée reste inchangée et l'utilisateur peut continuer à taper sans interruption—assurant que la fonctionnalité ne se met jamais en travers du chemin.

Ce comportement prédictif améliore à la fois la vitesse et l'exactitude, en particulier dans des scénarios d'entrée répétitifs ou des applications où la cohérence de la formulation est importante.

<ComponentDemo 
path='/webforj/textareapredictedtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java'
height = '400px'
/>

:::info
Cette démonstration utilise l'[API Datamuse](https://datamuse.com/) pour fournir des suggestions de mots basées sur l'entrée de l'utilisateur. La qualité et la pertinence des prédictions dépendent entièrement du jeu de données et du mécanisme de score de l'API. Elle n'utilise pas de modèles d'IA ou de modèles de langage de grande taille (LLMs); les suggestions sont générées à partir d'un moteur léger basé sur des règles axé sur la similarité lexicale.
:::

## État en lecture seule et désactivé {#read-only-and-disabled-state}

Le composant `TextArea` peut être défini comme en lecture seule ou désactivé pour contrôler l'interaction de l'utilisateur.

Une zone de texte **en lecture seule** permet aux utilisateurs de visualiser et de sélectionner le contenu, mais pas de le modifier. Cela est utile pour afficher des informations dynamiques ou pré-remplies qui doivent rester inchangées.

Une zone de texte **désactivée**, quant à elle, bloque toute interaction—y compris le focus et la sélection de texte—et est généralement stylée comme inactive ou grisée.

Utilisez le mode en lecture seule lorsque le contenu est pertinent mais immuable, et le mode désactivé lorsque l'entrée n'est actuellement pas applicable ou doit être temporairement inactive.

<ComponentDemo 
path='/webforj/textareastates?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java'
height = '300px'
/>

## Stylisation {#styling}

<TableBuilder name="TextArea" />
