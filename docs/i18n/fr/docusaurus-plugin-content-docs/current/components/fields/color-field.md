---
sidebar_position: 5
title: ColorField
slug: colorfield
description: >-
  A component that provides a default browser-based color picker, allowing users
  to select a color from an input field.
_i18n_hash: d3392930b787f31c30ac78526b8e12d9
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-color-chooser" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/ColorField" top='true'/>

Le composant `ColorField` permet aux utilisateurs de sélectionner une couleur via le sélecteur de couleur natif du navigateur. Comme il s'appuie sur l'implémentation intégrée du navigateur, son apparence varie selon les navigateurs et les plateformes. Il peut apparaître comme un simple champ de texte, un sélecteur de couleur standard de la plateforme ou une interface de sélecteur personnalisée. Cette variation est avantageuse pour l'utilisateur, car le contrôle correspond à ce qu'il connaît déjà.

<!-- INTRO_END -->

## Utilisation de `ColorField` {#using-colorfield}

<ParentLink parent="Field" />

`ColorField` étend la classe partagée `Field`, qui fournit des fonctionnalités communes à tous les composants de champ. L'exemple suivant permet à l'utilisateur de choisir une couleur et affiche ses compléments tétradique.

<ComponentDemo
path='/webforj/colorfield'
files={[
  'src/main/java/com/webforj/samples/views/fields/colorfield/ColorFieldView.java',
  'src/main/frontend/css/fields/colorfield/colorFieldDemo.css',
]}
height='300px'
/>

Le `ColorField` est idéal dans les scénarios où la sélection de couleur est un élément crucial de l'interface utilisateur ou de l'interface de l'application. Voici quelques scénarios où vous pouvez utiliser efficacement un `ColorField` :

1. **Outils de Design Graphique et d'Édition d'Image** : Les champs de couleur sont essentiels dans les applications qui impliquent une personnalisation via la sélection de couleur.

2. **Personnalisation de Thème** : Si votre application permet aux utilisateurs de personnaliser des thèmes, l'utilisation d'un champ de couleur leur permet de choisir des couleurs pour différents éléments de l'interface utilisateur, tels que les arrière-plans, le texte, les boutons, etc.

3. **Visualisation de Données** : Offrez aux utilisateurs un champ de couleur pour sélectionner des couleurs pour des graphiques, des diagrammes, des cartes de chaleur et d'autres représentations visuelles.

## Valeur {#value}

Le `ColorField` utilise la classe [`java.awt.Color`](https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/Color.html) pour définir et récupérer des couleurs via les méthodes `setValue()` et `getValue()`. Bien que le composant côté client gère exclusivement les couleurs RGB complètement opaques en notation hexadécimale, webforJ simplifie le processus en convertissant automatiquement les valeurs `Color` au format correct.

:::tip Analyse hexadécimale
Lors de l'utilisation de la méthode `setText()` pour assigner une valeur, le `ColorField` tentera d'analyser l'entrée comme une couleur hexadécimale. Si l'analyse échoue, une `IllegalArgumentException` sera levée.
:::

## Utilitaires statiques {#static-utilities}

La classe `ColorField` fournit également les méthodes utilitaires statiques suivantes :

- `fromHex(String hex)` : Convertir une chaîne de couleur au format hexadécimal en un objet `Color` qui peut ensuite être utilisé avec cette classe, ou ailleurs.

- `toHex(Color color)` : Convertir la valeur donnée en la représentation hexadécimale correspondante.

- `isValidHexColor(String hex)` : Vérifier si la valeur donnée est une couleur hexadécimale valide de 7 caractères.

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant `ColorField`, envisagez les meilleures pratiques suivantes :

- **Assistance Contextuelle** : Fournissez une assistance contextuelle, comme des infobulles ou une étiquette, pour clarifier que les utilisateurs peuvent sélectionner une couleur et comprendre son but.

- **Fournir une Couleur par Défaut** : Ayez une couleur par défaut qui a du sens dans le contexte de votre application.

- **Offrir des Couleurs Préétablies** : Incluez une palette de couleurs couramment utilisées ou en accord avec la marque, en complément du champ de couleur pour une sélection rapide.
