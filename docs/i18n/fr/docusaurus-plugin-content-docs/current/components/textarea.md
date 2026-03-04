---
title: TextArea
sidebar_position: 130
_i18n_hash: 423b70520e8f64a463d2c7b1d0e35ddc
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

Le composant `TextArea` fournit un champ de saisie de texte multi-lignes où les utilisateurs peuvent taper et modifier de plus longs blocs de texte. Il prend en charge des limites de caractères maximales, la structure des paragraphes, le retour à la ligne et des règles de validation pour contrôler la manière dont la saisie est gérée.

<!-- INTRO_END -->

## Création d'un `TextArea` {#creating-a-textarea}

Créez un `TextArea` en passant une étiquette à son constructeur. Des propriétés telles que le texte de l'espace réservé, les limites de caractères et le comportement de retour à la ligne peuvent être configurées via des méthodes de définition.

<ComponentDemo 
path='/webforj/textarea?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaView.java'
height = '300px'
/>

## Gestion des paragraphes {#managing-paragraphs}

Le composant `TextArea` fournit des fonctionnalités pour gérer des paragraphes de texte, ce qui le rend idéal pour les applications qui nécessitent l'édition de documents ou une saisie de texte structurée.

Voici un exemple rapide de la manière de construire et de manipuler le contenu des paragraphes :

```java
TextArea textArea = new TextArea();

// Insérer un paragraphe au début
textArea.addParagraph(0, "Ceci est le premier paragraphe.");

// Ajouter un autre paragraphe à la fin
textArea.addParagraph("Voici un deuxième paragraphe.");

// Ajouter du contenu supplémentaire au premier paragraphe
textArea.appendToParagraph(0, " Cette phrase prolonge la première.");

// Supprimer le deuxième paragraphe
textArea.removeParagraph(1);

// Récupérer et imprimer tous les paragraphes actuels
List<String> paragraphs = textArea.getParagraphs();
for (int i = 0; i < paragraphs.size(); i++) {
    System.out.println("Paragraphe " + i + ": " + paragraphs.get(i));
}
```

## Validation {#validation}

Le composant `TextArea` prend en charge deux types de validation complémentaires : contraintes structurelles et contraintes de contenu.

**Les contraintes structurelles** se concentrent sur la manière dont le texte est organisé et visuellement disposé. Par exemple :
- `setLineCountLimit(int maxLines)` restreint le nombre de lignes autorisées dans le champ de texte.
- `setParagraphLengthLimit(int maxCharsPerLine)` limite le nombre de caractères par paragraphe (ou ligne), aidant à faire respecter les normes de lisibilité ou de mise en forme.

**Les contraintes de contenu**, en revanche, concernent la quantité totale de texte saisie, quelle que soit sa distribution :
- `setMaxLength(int maxChars)` limite le nombre total de caractères autorisés dans tous les paragraphes.
- `setMinLength(int minChars)` impose une longueur minimale, garantissant qu'un contenu suffisant est fourni.

La démo suivante permet aux utilisateurs d'ajuster les limites de validation—telles que le nombre maximum de caractères, la longueur des paragraphes et le nombre de lignes—en temps réel et de voir comment le `TextArea` répond.

<ComponentDemo 
path='/webforj/textareavalidation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java'
height = '550px'
/>

## Retour à la ligne et retour à la ligne {#word-wrap-and-line-wrapping}

Vous pouvez contrôler si le texte est enroulé ou défile horizontalement en utilisant `setLineWrap()`. Lorsque l'enroulement est désactivé, les lignes continuent horizontalement au-delà de la zone visible, nécessitant un défilement. Lorsqu'il est activé, le texte s'enroule automatiquement à la ligne suivante lorsqu'il atteint le bord du composant.

Pour affiner davantage le comportement de l'enroulement, `setWrapStyle()` vous permet de choisir entre deux styles :
- `WORD_BOUNDARIES` enveloppe le texte par mots entiers, préservant le flux de lecture naturel.
- `CHARACTER_BOUNDARIES` enveloppe au niveau des caractères individuels, permettant un meilleur contrôle de la mise en page, en particulier dans des conteneurs étroits ou à largeur fixe.

Ces options d'enroulement travaillent de pair avec des contraintes structurelles telles que les limites du nombre de lignes et la longueur des paragraphes. Alors que l'enroulement détermine *comment* le texte s'écoule dans l'espace disponible, les limites structurelles définissent *combien* d'espace le texte est autorisé à occuper. Ensemble, ils aident à maintenir à la fois une structure visuelle et des limites de saisie utilisateur.

<ComponentDemo 
path='/webforj/textareawrap?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java'
height = '400px'
/>

## Texte prédit {#predicted-text}

Le composant `TextArea` prend en charge des suggestions de texte intelligentes pour aider les utilisateurs à taper plus rapidement et avec moins d'erreurs. À mesure que les utilisateurs saisissent du texte, des suggestions de texte apparaissent en fonction de la saisie actuelle, leur permettant de compléter des phrases courantes ou attendues.

Les prédictions peuvent être acceptées en appuyant sur la touche `Tab` ou la flèche droite, insérant le texte proposé dans la saisie de manière fluide. Si aucune prédiction adaptée n'est disponible à un moment donné, la saisie reste inchangée et l'utilisateur peut continuer à taper sans interruption, garantissant que la fonctionnalité ne gêne jamais.

Ce comportement prédictif améliore à la fois la vitesse et la précision, en particulier dans des scénarios de saisie répétitive ou dans des applications où la cohérence des formulations est importante.

<ComponentDemo 
path='/webforj/textareapredictedtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java'
height = '400px'
/>

:::info
Cette démo utilise l'[API Datamuse](https://datamuse.com/) pour fournir des suggestions de mots basées sur la saisie de l'utilisateur. La qualité et la pertinence des prédictions dépendent entièrement du jeu de données et du mécanisme de scoring de l'API. Elle n'utilise pas de modèles d'IA ou de modèles de langage de grande taille (LLMs) ; les suggestions sont générées à partir d'un moteur léger basé sur des règles axées sur la similarité lexicale.
:::

## État en lecture seule et état désactivé {#read-only-and-disabled-state}

Le composant `TextArea` peut être défini comme en lecture seule ou désactivé pour contrôler l'interaction utilisateur.

Un champ de texte **en lecture seule** permet aux utilisateurs de visualiser et de sélectionner le contenu, mais pas de le modifier. Cela est utile pour afficher des informations dynamiques ou pré-remplies qui devraient rester inchangées.

Un champ de texte **désactivé**, en revanche, bloque toute interaction—including focus et sélection de texte—et est généralement stylisé comme inactif ou grisé.

Utilisez le mode en lecture seule lorsque le contenu est pertinent mais immuable, et le mode désactivé lorsque la saisie n'est actuellement pas applicable ou devrait être temporairement inactive.

<ComponentDemo 
path='/webforj/textareastates?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java'
height = '300px'
/>

## Style {#styling}

<TableBuilder name="TextArea" />
