---
sidebar_position: 3
title: Elements
slug: element
_i18n_hash: 749e84016c244ec7349221d00dc0de9a
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

Les développeurs de webforJ ont la possibilité de choisir non seulement parmi la riche bibliothèque de composants fournie, mais aussi d'intégrer des composants d'ailleurs. Pour faciliter cela, le composant `Element` peut être utilisé pour simplifier l'intégration de tout, des éléments HTML simples aux composants web personnalisés plus complexes.

:::important
Le composant `Element` ne peut pas être étendu et n'est pas le composant de base de tous les composants dans webforJ. Pour en savoir plus sur la hiérarchie des composants de webforJ, lisez [cet article](../architecture/controls-components.md).
:::

<ComponentDemo 
path='/webforj/elementinputdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputDemoView.java'
cssURL='/css/element/elementInput.css'
/>

## Ajout d'événements {#adding-events}

Pour utiliser les événements qui peuvent accompagner votre élément, vous pouvez utiliser les méthodes `addEventListener` du composant `Element`. L'ajout d'un événement nécessite au moins le type/le nom de l'événement que le composant attend et un écouteur à ajouter à l'événement.

Il existe également des options supplémentaires pour personnaliser davantage les événements en utilisant les configurations des options d'événements.

<ComponentDemo 
path='/webforj/elementinputevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputEventView.java'
cssURL='/css/element/elementInputEvent.css'
height='240px'
/>

## Interaction des composants {#component-interaction}

Le composant `Element` agit comme un conteneur pour d'autres composants. Il fournit un moyen d'organiser et de récupérer des informations pour les composants enfants et offre un ensemble clair de fonctions pour ajouter ou supprimer ces composants enfants au besoin.

### Ajout de composants enfants {#adding-child-components}

Le composant `Element` supporte la composition de composants enfants. Les développeurs peuvent organiser et gérer des structures d'interface utilisateur complexes en ajoutant des composants en tant qu'enfants au `Element`. Trois méthodes existent pour définir le contenu d'un `Element` :

1. **`add(Component... components)`** : Cette méthode permet d'ajouter un ou plusieurs composants à une `String` optionnelle qui désigne un emplacement spécifié lorsqu'elle est utilisée avec un composant Web. Omettre l'emplacement ajoutera le composant entre les balises HTML.

2. **`setHtml(String html)`** : Cette méthode prend la `String` passée à la méthode et l'injecte comme HTML dans le composant. Selon le `Element`, cela peut être rendu de différentes manières.

3. **`setText(String text)`** : Cette méthode se comporte de manière similaire à la méthode `setHtml()`, mais injecte un texte littéral dans le `Element`.

<ComponentDemo 
path='/webforj/elementinputtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputTextView.java'
cssURL='/css/element/elementInput.css'
height='175px'
/>

:::tip
Appeler `setHtml()` ou `setText()` remplacera le contenu actuellement contenu entre les balises d'ouverture et de fermeture de l'élément.
:::

### Suppression de composants {#removing-components}

En plus d'ajouter des composants à un `Element`, les méthodes suivantes sont mises en œuvre pour la suppression de divers composants enfants :

1. **`remove(Component... components)`** : Cette méthode prend un ou plusieurs composants et les supprimera en tant que composants enfants.

2. **`removeAll()`** : Cette méthode supprimera tous les composants enfants du `Element`.

### Accès aux composants {#accessing-components}

Pour accéder aux différents composants enfants présents dans un `Element`, ou aux informations concernant ces composants, les méthodes suivantes sont disponibles :

1. **`getComponents()`** : Cette méthode renvoie une `Liste` Java de tous les enfants du `Element`. 

2. **`getComponents(String id)`** : Cette méthode est similaire à la méthode ci-dessus, mais prendra l'ID côté serveur d'un composant spécifique et le renverra lorsqu'il sera trouvé.

3. **`getComponentCount()`** : Renvoie le nombre de composants enfants présents dans le `Element`. 


## Appel de fonctions JavaScript {#calling-javascript-functions}

Le composant `Element` fournit deux méthodes API qui permettent d'appeler des fonctions JavaScript sur les éléments HTML.

1. **`callJsFunction(String functionName, Object... arguments)`** : Cette méthode prend un nom de fonction sous forme de chaîne et accepte facultativement un ou plusieurs objets comme paramètres pour la fonction. Cette méthode est exécutée de manière synchrone, ce qui signifie que le **fil d'exécution est bloqué** jusqu'à ce que la méthode JS retourne, ce qui entraîne un aller-retour. Les résultats de la fonction sont renvoyés sous forme d'un `Object`, qui peut être casté et utilisé en Java.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`** : Comme avec la méthode précédente, un nom de fonction et des arguments optionnels pour la fonction peuvent être passés. Cette méthode s'exécute de manière asynchrone et **ne bloque pas le fil d'exécution**. Elle retourne un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, qui permet une interaction supplémentaire avec la fonction et sa charge utile.

### Passage de paramètres {#passing-parameters}

Les arguments passés à ces méthodes qui sont utilisés dans l'exécution des fonctions JS sont sérialisés sous forme de tableau JSON. Deux types d'arguments notables sont traités comme suit :
- `this` : Utiliser le mot-clé `this` donnera à la méthode une référence à la version côté client du composant invoquant.
- `Component` : Toute instance de composant Java passée dans l'une des méthodes JsFunction sera remplacée par la version côté client du composant.

:::info
Les appels de fonction synchrones et asynchrones attendront pour appeler une méthode jusqu'à ce que le `Element` ait été ajouté au DOM avant d'exécuter une fonction, mais `callJsFunction()` n'attendra pas que des arguments `component` soient attachés, ce qui peut entraîner un échec. À l'inverse, l'invocation de `callJsFunctionAsync()` peut ne jamais se terminer si un argument de composant n'est jamais attaché.
:::

Dans la démo ci-dessous, un événement est ajouté à un `Button` HTML. Cet événement est ensuite déclenché de manière programmatique en appelant la méthode `callJsFunctionAsync()`. Le <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> résultant est ensuite utilisé pour créer une autre boîte de message une fois que la fonction asynchrone a été complétée.

<ComponentDemo 
path='/webforj/elementinputfunction?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java'
cssURL='/css/element/elementInput.css'
height='240px'
/>

## Exécution de JavaScript {#executing-javascript}

En plus d'exécuter JavaScript au niveau de l'application, il est également possible d'exécuter JavaScript au niveau du `Element`. Effectuer cette exécution au niveau du `Element` permet d'inclure le contexte de l'élément HTML dans l'exécution. C'est un outil puissant qui agit comme un conduit pour les développeurs vers des capacités interactives avec des environnements côté client.

Tout comme l'exécution de fonctions, l'exécution de JavaScript peut être effectuée de manière synchrone ou asynchrone avec les méthodes suivantes :

1. **`executeJs(String script)`** : Cette méthode prend une `String`, qui sera exécutée comme code JavaScript chez le client. Ce script est exécuté de manière synchrone, ce qui signifie que le **fil d'exécution est bloqué** jusqu'à ce que l'exécution JS retourne et entraîne un aller-retour. Les résultats de la fonction sont renvoyés sous forme d'un `Object`, qui peut être casté et utilisé en Java.

2. **`executeJsAsync(String script)`** : Comme avec la méthode précédente, un paramètre `String` passé sera exécuté comme code JavaScript côté client. Cette méthode s'exécute de manière asynchrone et **ne bloque pas le fil d'exécution**. Elle retourne un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, qui permet une interaction supplémentaire avec la fonction et sa charge utile.

:::tip
Ces méthodes ont accès au mot-clé `component`, qui donne au code JavaScript accès à l'instance côté client du composant exécutant le JavaScript.
:::
