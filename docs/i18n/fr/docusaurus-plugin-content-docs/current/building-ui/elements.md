---
sidebar_position: 5
title: Elements
slug: element
_i18n_hash: 820bed6c059dad74a523673f245f3b2a
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

Les développeurs webforJ ont la possibilité de choisir non seulement parmi la riche bibliothèque de composants fournis, mais aussi d'intégrer des composants d'ailleurs. Pour faciliter cela, le composant `Element` peut être utilisé pour simplifier l'intégration de tout, des simples éléments HTML, à des composants web personnalisés plus complexes.

:::important
Le composant `Element` ne peut pas être étendu et n'est pas le composant de base pour tous les composants au sein de webforJ. Pour en savoir plus sur la hiérarchie des composants de webforJ, lisez [cet article](../architecture/controls-components.md).
:::

<ComponentDemo
path='/webforj/elementinputdemo'
files={[
  'src/main/java/com.webforj/samples/views/element/ElementInputDemoView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
/>

## Ajouter des événements {#adding-events}

Afin d'utiliser des événements qui peuvent accompagner votre élément, vous pouvez utiliser les méthodes `addEventListener` du composant `Element`. Ajouter un événement nécessite au moins le type/le nom de l'événement que le composant attend et un listener à ajouter à l'événement.

Il existe également des options supplémentaires pour personnaliser davantage les événements en utilisant les configurations d'options d'événements.

<ComponentDemo
path='/webforj/elementinputevent'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputEventView.java',
  'src/main/resources/static/css/element/elementInputEvent.css',
]}
height='240px'
/>

## Interaction des composants {#component-interaction}

Le composant `Element` agit comme un conteneur pour d'autres composants. Il fournit un moyen d'organiser et de récupérer des informations pour les composants enfants, et offre un ensemble clair de fonctions pour ajouter ou supprimer ces composants enfants au besoin.

### Ajouter des composants enfants {#adding-child-components}

Le composant `Element` prend en charge la composition de composants enfants. Les développeurs peuvent organiser et gérer des structures d'interface utilisateur complexes en ajoutant des composants en tant qu'enfants au `Element`. Trois méthodes existent pour définir du contenu dans un `Element` :

1. **`add(Component... components)`** : Cette méthode permet d'ajouter un ou plusieurs composants à un `String` optionnel qui désigne un slot spécifié lorsqu'il est utilisé avec un composant web. Omettre le slot ajoutera le composant entre les balises HTML.

2. **`setHtml(String html)`** : Cette méthode prend le `String` passé à la méthode et l'injecte en tant que HTML dans le composant. Selon l'`Element`, cela peut être rendu de différentes manières.

3. **`setText(String text)`** : Cette méthode se comporte de manière similaire à la méthode `setHtml()`, mais injecte du texte littéral dans l'`Element`.

<ComponentDemo
path='/webforj/elementinputtext'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputTextView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
height='175px'
/>

:::tip
Appeler `setHtml()` ou `setText()` remplacera le contenu actuellement contenu entre les balises d'ouverture et de fermeture de l'élément.
:::

### Supprimer des composants {#removing-components}

En plus d'ajouter des composants dans un `Element`, les méthodes suivantes sont mises en œuvre pour la suppression de divers composants enfants :

1. **`remove(Component... components)`** : Cette méthode prend un ou plusieurs composants et les supprime en tant que composants enfants.

2. **`removeAll()`** : Cette méthode supprime tous les composants enfants du `Element`.

### Accéder aux composants {#accessing-components}

Pour accéder aux différents composants enfants présents dans un `Element`, ou aux informations concernant ces composants, les méthodes suivantes sont disponibles :

1. **`getComponents()`** : Cette méthode renvoie une `List` Java de tous les enfants de l'`Element`.

2. **`getComponents(String id)`** : Cette méthode est similaire à la méthode ci-dessus, mais prend l'ID côté serveur d'un composant spécifique et le renvoie lorsqu'il est trouvé.

3. **`getComponentCount()`** : Renvoie le nombre de composants enfants présents dans l'`Element`.

## Appeler des fonctions JavaScript {#calling-javascript-functions}

Le composant `Element` fournit deux méthodes API qui permettent d'appeler des fonctions JavaScript sur des éléments HTML.

1. **`callJsFunction(String functionName, Object... arguments)`** : Cette méthode prend un nom de fonction sous forme de chaîne et prend en option un ou plusieurs objets en tant que paramètres pour la fonction. Cette méthode est exécutée de manière synchrone, ce qui signifie que le **fil d'exécution est bloqué** jusqu'à ce que la méthode JS renvoie, et entraîne un aller-retour. Les résultats de la fonction sont renvoyés sous forme d'`Object`, qui peut être converti et utilisé en Java.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`** : Comme pour la méthode précédente, un nom de fonction et des arguments optionnels pour la fonction peuvent être passés. Cette méthode s'exécute de manière asynchrone et **ne bloque pas le fil d'exécution**. Elle renvoie un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, qui permet d'interagir davantage avec la fonction et sa charge utile.

### Passer des paramètres {#passing-parameters}

Les arguments qui sont passés à ces méthodes utilisées dans l'exécution des fonctions JS sont sérialisés en tant que tableau JSON. Il existe deux types d'arguments notables qui sont traités comme suit :
- `this` : L'utilisation du mot-clé `this` donnera à la méthode une référence à la version côté client du composant invoquant.
- `Component` : Toute instance de composant Java passée dans l'une des méthodes JsFunction sera remplacée par la version côté client du composant.

:::info
Les appels de fonction synchrones et asynchrones attendront que l'`Element` ait été ajouté au DOM avant d'exécuter une fonction, mais `callJsFunction()` n'attendra pas que des arguments `component` soient attachés, ce qui peut entraîner une erreur. En revanche, invoquer `callJsFunctionAsync()` peut ne jamais se compléter si un argument composant n'est jamais attaché.
:::

Dans la démo ci-dessous, un événement est ajouté à un `Button` HTML. Cet événement est ensuite déclenché par programme en appelant la méthode `callJsFunctionAsync()`. Le <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> résultant est ensuite utilisé pour créer une autre boîte de message une fois que la fonction asynchrone a été complétée.

<ComponentDemo
path='/webforj/elementinputfunction'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
height='240px'
/>

## Exécuter JavaScript {#executing-javascript}

En plus d'exécuter JavaScript au niveau de l'application, il est possible d'exécuter JavaScript au niveau de l'`Element` également. Effectuer cette exécution au niveau de l'`Element` permet d'inclure le contexte de l'élément HTML dans l'exécution. C'est un outil puissant qui agit comme un conduit pour le développeur vers des capacités interactives avec les environnements côté client.

Tout comme l'exécution de fonction, l'exécution de JavaScript peut se faire de manière synchrone ou asynchrone avec les méthodes suivantes :

1. **`executeJs(String script)`** : Cette méthode prend une `String`, qui sera exécutée en tant que code JavaScript dans le client. Ce script est exécuté de manière synchrone, ce qui signifie que le **fil d'exécution est bloqué** jusqu'à ce que l'exécution JS renvoie, entraînant un aller-retour. Les résultats de la fonction sont renvoyés sous forme d'`Object`, qui peut être converti et utilisé en Java.

2. **`executeJsAsync(String script)`** : Comme avec la méthode précédente, un paramètre `String` passé sera exécuté en tant que code JavaScript sur le client. Cette méthode s'exécute de manière asynchrone et **ne bloque pas le fil d'exécution**. Elle renvoie un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, qui permet d'interagir davantage avec la fonction et sa charge utile.

:::tip
Ces méthodes ont accès au mot-clé `component`, ce qui donne au code JavaScript accès à l'instance côté client du composant exécutant le JavaScript.
:::
