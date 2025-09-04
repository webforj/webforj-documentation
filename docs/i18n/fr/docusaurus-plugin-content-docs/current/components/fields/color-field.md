---
sidebar_position: 5
title: ColorField
slug: colorfield
description: >-
  A component that provides a default browser-based color picker, allowing users
  to select a color from an input field.
_i18n_hash: 4c7128082457a29ae8c0bf3afed1f666
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-color-chooser" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/ColorField" top='true'/>

<ParentLink parent="Field" />

Le composant `ColorField` est un outil polyvalent qui permet aux utilisateurs d'explorer et de sélectionner des couleurs de manière interactive au sein de votre application. Il offre une approche fluide pour que les utilisateurs puissent trouver la teinte, la saturation et la luminosité parfaites pour correspondre à leur vision créative.

Le composant `ColorField` est implémenté comme une fonctionnalité native du navigateur, de sorte que la présentation peut varier considérablement en fonction du navigateur et de la plateforme. Cependant, cette variation est bénéfique, car elle s'aligne sur l'environnement familier de l'utilisateur. Il peut apparaître comme une simple zone de texte pour garantir une valeur de couleur correctement formatée, un sélecteur de couleur standard de la plateforme, ou même une interface personnalisée de sélecteur de couleur.

<ComponentDemo 
path='/webforj/colorfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/colorfield/ColorFieldView.java'
cssURL='/css/fields/colorfield/colorFieldDemo.css'
height='300px'
/>

## Usages {#usages}

Le `ColorField` est idéal dans les scénarios où la sélection de couleurs est un élément crucial de l'interface utilisateur ou de l'interface de l'application. Voici quelques scénarios où vous pouvez utiliser efficacement un `ColorField` :

1. **Outils de conception graphique et d'édition d'images** : Les champs de couleur sont essentiels dans les applications qui impliquent une personnalisation via la sélection de couleurs.

2. **Personnalisation de thèmes** : Si votre application permet aux utilisateurs de personnaliser des thèmes, l'utilisation d'un champ de couleur leur permet de choisir des couleurs pour différents éléments de l'interface utilisateur, tels que les arrière-plans, le texte, les boutons, etc.

3. **Visualisation des données** : Offrez aux utilisateurs un champ de couleur pour sélectionner des couleurs pour des graphiques, des diagrammes, des cartes de chaleur et d'autres représentations visuelles.

## Value {#value}

Le `ColorField` utilise la classe [`java.awt.Color`](https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/Color.html) pour définir et récupérer des couleurs via les méthodes `setValue()` et `getValue()`. Bien que le composant côté client gère exclusivement des couleurs RGB entièrement opaques en notation hexadécimale, webforJ simplifie le processus en convertissant automatiquement les valeurs `Color` au format correct.

:::tip Analyse hexadécimale
Lors de l'utilisation de la méthode `setText()` pour attribuer une valeur, le `ColorField` tentera d'analyser l'entrée comme une couleur hexadécimale. Si l'analyse échoue, une `IllegalArgumentException` sera levée.
:::

## Static utilities {#static-utilities}

La classe `ColorField` fournit également les méthodes utilitaires statiques suivantes :

- `fromHex(String hex)` : Convertit une chaîne de couleur au format hexadécimal en un objet `Color` qui peut ensuite être utilisé avec cette classe ou ailleurs.

- `toHex(Color color)` : Convertit la valeur donnée en la représentation hexadécimale correspondante.

- `isValidHexColor(String hex)` : Vérifie si la valeur donnée est une couleur hexadécimale valide à 7 caractères.

## Best practices {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant `ColorField`, considérez les meilleures pratiques suivantes :

- **Assistance contextuelle** : Fournissez une assistance contextuelle, comme des infobulles ou une étiquette, pour clarifier que les utilisateurs peuvent sélectionner une couleur et comprendre son objectif.

- **Fournir une couleur par défaut** : Ayez une couleur par défaut qui a du sens dans le contexte de votre application.

- **Offrir des couleurs prédéfinies** : Incluez une palette de couleurs couramment utilisées ou de couleurs de marque avec le champ de couleur pour une sélection rapide.
