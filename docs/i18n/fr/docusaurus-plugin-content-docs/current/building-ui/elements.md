---
sidebar_position: 5
title: Éléments
sidebar_class_name: updated-content
description: >-
  Integrate raw HTML tags and custom web components in webforJ using the Element
  class to add children, set content, and call JavaScript functions.
slug: element
_i18n_hash: 988b2a49584036eee3b0475215a707ae
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

Les développeurs de webforJ ont la possibilité de choisir non seulement parmi la riche bibliothèque de composants fournis, mais aussi d'intégrer des composants provenant d'ailleurs. Pour faciliter cela, le composant `Element` peut être utilisé pour simplifier l'intégration de tout, des éléments HTML simples aux composants web personnalisés plus complexes.

:::important
Le composant `Element` ne peut pas être étendu et n'est pas le composant de base pour tous les composants au sein de webforJ. Pour en savoir plus sur la hiérarchie des composants de webforJ, lisez [cet article](../architecture/controls-components.md).
:::

<ComponentDemo
path='/webforj/elementmeter'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementMeterView.java',
  'src/main/resources/static/css/element/elementMeter.css',
]}
height='240px'
/>

## Ajout d'événements {#adding-events}

Afin d'utiliser les événements qui peuvent accompagner votre élément, vous pouvez utiliser les méthodes `addEventListener` du composant `Element`. Ajouter un événement nécessite au moins le type/nom de l'événement que le composant attend, et un écouteur à ajouter à l'événement.

Il existe également des options supplémentaires pour personnaliser davantage les événements en utilisant les configurations d'options d'événements.

<ComponentDemo
path='/webforj/elementtaginput'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementTagInputView.java',
  'src/main/resources/static/css/element/elementTagInput.css',
]}
height='240px'
/>

## Interaction entre composants {#component-interaction}

Le composant `Element` agit comme un conteneur pour d'autres composants. Il fournit un moyen d'organiser et de récupérer les informations pour les composants enfants, et offre un ensemble clair de fonctions pour ajouter ou supprimer ces composants enfants au besoin.


### Ajout de composants enfants {#adding-child-components}

Le composant `Element` prend en charge la composition de composants enfants. Les développeurs peuvent organiser et gérer des structures UI complexes en ajoutant des composants en tant qu'enfants au `Element`. Trois méthodes existent pour définir le contenu d'un `Element` :

1. **`add(Component... components)`**: Cette méthode permet d'ajouter un ou plusieurs composants à un `String` optionnel qui désigne un emplacement spécifié lorsqu'il est utilisé avec un Web Component. L'omission de l'emplacement ajoutera le composant entre les balises HTML.

2. **`setHtml(String html)`**: Cette méthode prend le `String` passé à la méthode et l'injecte en tant que HTML dans le composant. Selon le `Element`, cela peut être rendu de différentes manières.

3. **`setText(String text)`**: Cette méthode se comporte de manière similaire à la méthode `setHtml()`, mais injecte du texte littéral dans le `Element`.


<ComponentDemo
path='/webforj/elementfigure'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementFigureView.java',
  'src/main/resources/static/css/element/elementFigure.css',
]}
height='240px'
/>

:::warning Remplacement de contenu
Appeler `setHtml()` ou `setText()` remplacera le contenu actuellement contenu entre les balises ouvrantes et fermantes de l'élément.
:::

### Suppression de composants {#removing-components}

En plus d'ajouter des composants à un `Element`, les méthodes suivantes sont mises en œuvre pour la suppression de divers composants enfants :

1. **`remove(Component... components)`**: Cette méthode prend un ou plusieurs composants et les supprime en tant que composants enfants.

2. **`removeAll()`**: Cette méthode supprime tous les composants enfants du `Element`.

### Accès aux composants {#accessing-components}

Pour accéder aux différents composants enfants présents dans un `Element`, ou aux informations concernant ces composants, les méthodes suivantes sont disponibles :

1. **`getComponents()`**: Cette méthode renvoie une `List` Java de tous les enfants du `Element`.

2. **`getComponents(String id)`**: Cette méthode est similaire à la méthode ci-dessus, mais prend l'ID côté serveur d'un composant spécifique et le renvoie lorsqu'il est trouvé.

3. **`getComponentCount()`**: Renvoie le nombre de composants enfants présents dans le `Element`.


## Appel de fonctions JavaScript {#calling-javascript-functions}

Le composant `Element` fournit deux méthodes API qui permettent d'appeler des fonctions JavaScript sur des éléments HTML.

1. **`callJsFunction(String functionName, Object... arguments)`**: Cette méthode prend un nom de fonction sous forme de chaîne, et prend facultativement un ou plusieurs objets comme paramètres pour la fonction. Cette méthode est exécutée de manière synchrone, ce qui signifie que le **fil d'exécution est bloqué** jusqu'à ce que la méthode JS renvoie, resultant en un aller-retour. Les résultats de la fonction sont renvoyés sous forme d'`Object`, qui peut être converti et utilisé en Java.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`**: Comme pour la méthode précédente, un nom de fonction et des arguments facultatifs pour la fonction peuvent être passés. Cette méthode s'exécute de manière asynchrone et **ne bloque pas le fil d'exécution**. Elle renvoie un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, qui permet une interaction supplémentaire avec la fonction et son payload.

### Passage de paramètres {#passing-parameters}

Les arguments qui sont passés à ces méthodes, utilisés dans l'exécution des fonctions JS, sont sérialisés sous forme de tableau JSON. Il y a deux types d'arguments notables qui sont traités comme suit :
- `this`: Utiliser le mot-clé `this` donnera à la méthode une référence à la version client du composant d'appel.
- `Component`: Toute instance de composant Java passée dans l'une des méthodes JsFunction sera remplacée par la version client du composant.

:::warning Attente des arguments de composants
L'appel de fonctions synchrone et asynchrone attendra que le `Element` ait été ajouté au DOM avant d'exécuter une fonction, mais `callJsFunction()` n'attendra pas que des arguments de `component` soient attachés, ce qui peut entraîner un échec. Inversement, appeler `callJsFunctionAsync()` peut ne jamais se terminer si un argument de composant n'est jamais attaché.
:::

Dans la démo ci-dessous, sélectionner **Focus search** appelle la méthode native `focus()` sur l'entrée de recherche avec `callJsFunctionAsync()`. Le <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> résultant est utilisé pour confirmer l'appel avec une notification une fois que la fonction asynchrone est terminée.

<ComponentDemo
path='/webforj/elementsearch'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementSearchView.java',
  'src/main/resources/static/css/element/elementSearch.css',
]}
height='240px'
/>

## Exécution de JavaScript {#executing-javascript}

Au-delà de l'appel de fonctions nommées, un `Element` peut exécuter des scripts bruts restreints à cet élément avec `executeJs`, `executeJsAsync` et `executeJsVoidAsync`. Voir [Exécuter JavaScript](./execute-javascript.md) pour ces méthodes, leur comportement synchrone et asynchrone, et comment les valeurs renvoyées se convertissent en types Java.
