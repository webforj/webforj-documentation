---
sidebar_position: 5
title: ColorField
slug: colorfield
description: >-
  A component that provides a default browser-based color picker, allowing users
  to select a color from an input field.
_i18n_hash: 42e1e3270076a584d052295db1602298
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-color-chooser" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/ColorField" top='true'/>

Le composant `ColorField` permet aux utilisateurs de sélectionner une couleur via le sélecteur de couleurs natif du navigateur. Parce qu'il repose sur l'implémentation intégrée du navigateur, son apparence varie selon les navigateurs et les plateformes. Il peut se présenter sous la forme d'un simple champ de texte, d'un sélecteur de couleurs standard de la plateforme, ou d'une interface de sélection personnalisée. Cette variation joue en faveur de l'utilisateur, car le contrôle correspond à ce qu'il connaît déjà.

<!-- INTRO_END -->

## Utilisation de `ColorField` {#using-colorfield}

<ParentLink parent="Field" />

`ColorField` prolonge la classe partagée `Field`, qui fournit des fonctionnalités communes à tous les composants de champ. L'exemple suivant permet à l'utilisateur de choisir une couleur et d'afficher ses compléments tétradique.

<ComponentDemo 
path='/webforj/colorfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/colorfield/ColorFieldView.java'
cssURL='/css/fields/colorfield/colorFieldDemo.css'
height='300px'
/>

Le `ColorField` est idéal dans des scénarios où la sélection des couleurs est une partie cruciale de l'interface utilisateur ou de l'interface de l'application. Voici quelques scénarios où vous pouvez utiliser un `ColorField` efficacement :

1. **Outils de design graphique et d'édition d'images** : Les champs de couleur sont essentiels dans les applications qui impliquent des personnalisations via la sélection de couleurs.

2. **Personnalisation de thème** : Si votre application permet aux utilisateurs de personnaliser des thèmes, l'utilisation d'un champ de couleur leur permet de choisir des couleurs pour différents éléments de l'interface utilisateur, tels que des arrière-plans, du texte, des boutons, etc.

3. **Visualisation des données** : Offrez aux utilisateurs un champ de couleur pour sélectionner des couleurs pour des graphiques, des courbes, des cartes de chaleur et d'autres représentations visuelles.

## Valeur {#value}

Le `ColorField` utilise la classe [`java.awt.Color`](https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/Color.html) pour définir et récupérer des couleurs via les méthodes `setValue()` et `getValue()`. Bien que le composant côté client gère exclusivement des couleurs RGB complètement opaques en notation hexadécimale, webforJ simplifie le processus en convertissant automatiquement les valeurs `Color` dans le format correct.

:::tip Analyse hexadécimale
Lors de l'utilisation de la méthode `setText()` pour attribuer une valeur, le `ColorField` tentera d'analyser l'entrée comme une couleur hexadécimale. Si l'analyse échoue, une `IllegalArgumentException` sera levée.
:::

## Utilitaires statiques {#static-utilities}

La classe `ColorField` fournit également les méthodes utilitaires statiques suivantes :

- `fromHex(String hex)` : Convertir une chaîne de couleur au format hexadécimal en un objet `Color` qui peut ensuite être utilisé avec cette classe, ou ailleurs.

- `toHex(Color color)` : Convertir la valeur donnée en la représentation hexadécimale correspondante.

- `isValidHexColor(String hex)` : Vérifier si la valeur donnée est une couleur hexadécimale valide de 7 caractères.

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant `ColorField`, considérez les meilleures pratiques suivantes :

- **Assistance contextuelle** : Fournissez une assistance contextuelle, comme des info-bulles ou une étiquette, pour clarifier que les utilisateurs peuvent sélectionner une couleur et comprendre son but.

- **Fournir une couleur par défaut** : Ayez une couleur par défaut qui a du sens dans le contexte de votre application.

- **Proposer des couleurs prédéfinies** : Incluez une palette de couleurs couramment utilisées ou de couleurs de marque à côté du champ de couleur pour une sélection rapide.
