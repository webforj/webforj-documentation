---
sidebar_position: 5
title: Elements
slug: element
_i18n_hash: 2ea3ba8ae8756dcea1ee5d0eb9fb0cf9
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

Les développeurs de webforJ ont la possibilité de choisir non seulement parmi la riche bibliothèque de composants fournie, mais également d'intégrer des composants provenant d'ailleurs. Pour faciliter cela, le composant `Element` peut être utilisé pour simplifier l'intégration de tout, des éléments HTML simples aux composants web personnalisés plus complexes.

:::important
Le composant `Element` ne peut pas être étendu et n'est pas le composant de base pour tous les composants au sein de webforJ. Pour en savoir plus sur la hiérarchie des composants de webforJ, consultez [cet article](../architecture/controls-components.md).
:::

<ComponentDemo 
path='/webforj/elementinputdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputDemoView.java'
cssURL='/css/element/elementInput.css'
/>

## Ajouter des événements {#adding-events}

Pour utiliser les événements qui peuvent accompagner votre élément, vous pouvez utiliser les méthodes `addEventListener` du composant `Element`. Ajouter un événement nécessite au moins le type/nombre de l'événement que le composant attend et un écouteur à ajouter à l'événement.

Il existe également des options supplémentaires pour personnaliser davantage les événements à l'aide des configurations d'options d'événements.

<ComponentDemo 
path='/webforj/elementinputevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputEventView.java'
cssURL='/css/element/elementInputEvent.css'
height='240px'
/>

## Interaction des composants {#component-interaction}

Le composant `Element` agit comme un conteneur pour d'autres composants. Il fournit un moyen d'organiser et de récupérer des informations pour les composants enfants, et offre un ensemble clair de fonctions pour ajouter ou supprimer ces composants enfants selon les besoins.

### Ajouter des composants enfants {#adding-child-components}

Le composant `Element` prend en charge la composition de composants enfants. Les développeurs peuvent organiser et gérer des structures UI complexes en ajoutant des composants comme enfants au `Element`. Trois méthodes existent pour définir le contenu à l'intérieur d'un `Element` :

1. **`add(Component... components)`** : Cette méthode permettra d'ajouter un ou plusieurs composants à un `String` optionnel qui désigne un emplacement spécifié lorsqu'il est utilisé avec un composant Web. L'omission de l'emplacement ajoutera le composant entre les balises HTML.

2. **`setHtml(String html)`** : Cette méthode prend le `String` passé à la méthode et l'injecte comme HTML au sein du composant. Selon le `Element`, cela peut être rendu de différentes manières.

3. **`setText(String text)`** : Cette méthode se comporte de manière similaire à la méthode `setHtml()`, mais injecte du texte littéral dans l'`Element`.

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

1. **`remove(Component... components)`** : Cette méthode prend un ou plusieurs composants et les supprimera comme composants enfants.

2. **`removeAll()`** : Cette méthode supprimera tous les composants enfants du `Element`.

### Accéder aux composants {#accessing-components}

Pour accéder aux différents composants enfants présents dans un `Element`, ou aux informations concernant ces composants, les méthodes suivantes sont disponibles :

1. **`getComponents()`** : Cette méthode retourne une liste Java de tous les enfants du `Element`.

2. **`getComponents(String id)`** : Cette méthode est similaire à la méthode ci-dessus, mais prendra l'ID côté serveur d'un composant spécifique et le retournera lorsqu'il est trouvé.

3. **`getComponentCount()`** : Retourne le nombre de composants enfants présents dans le `Element`.

## Appeler des fonctions JavaScript {#calling-javascript-functions}

Le composant `Element` fournit deux méthodes API qui permettent d'appeler des fonctions JavaScript sur des éléments HTML.

1. **`callJsFunction(String functionName, Object... arguments)`** : Cette méthode prend un nom de fonction sous forme de chaîne et prend en option un ou plusieurs objets comme paramètres pour la fonction. Cette méthode est exécutée de manière synchrone, ce qui signifie que le **fil d'exécution est bloqué** jusqu'à ce que la méthode JS retourne, et résulte en un aller-retour. Les résultats de la fonction sont retournés sous forme d'`Object`, qui peut être converti et utilisé en Java.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`** : Comme avec la méthode précédente, un nom de fonction et des arguments optionnels pour la fonction peuvent être passés. Cette méthode s'exécute de manière asynchrone et **ne bloque pas le fil d'exécution**. Elle retourne un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, qui permet d'interagir davantage avec la fonction et sa charge utile.

### Passer des paramètres {#passing-parameters}

Les arguments passés à ces méthodes, qui sont utilisés dans l'exécution des fonctions JS, sont sérialisés sous forme de tableau JSON. Il existe deux types d'arguments notables qui sont traités comme suit :
- `this` : L'utilisation du mot-clé `this` donnera à la méthode une référence à la version côté client du composant appelant.
- `Component` : Toutes les instances de composants Java passées dans l'une des méthodes JsFunction seront remplacées par la version côté client du composant.

:::info
L'appel de fonctions à la fois synchrones et asynchrones attendra d'appeler une méthode jusqu'à ce que le `Element` ait été ajouté au DOM avant d'exécuter une fonction, mais `callJsFunction()` n'attendra pas que des arguments `component` soient attachés, ce qui peut entraîner un échec. En revanche, invoquer `callJsFunctionAsync()` peut ne jamais se terminer si un argument de composant n'est jamais attaché.
:::

Dans la démo ci-dessous, un événement est ajouté à un bouton HTML. Cet événement est ensuite déclenché par programmation en appelant la méthode `callJsFunctionAsync()`. Le <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> résultant est ensuite utilisé pour créer une autre boîte de message une fois que la fonction asynchrone a été complétée.

<ComponentDemo 
path='/webforj/elementinputfunction?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java'
cssURL='/css/element/elementInput.css'
height='240px'
/>

## Exécution de JavaScript {#executing-javascript}

En plus d'exécuter JavaScript depuis le niveau de l'application, il est également possible d'exécuter JavaScript depuis le niveau `Element`. Effectuer cette exécution au niveau de l'`Element` permet d'inclure le contexte de l'élément HTML dans l'exécution. C'est un outil puissant qui agit comme un conduit pour le développeur vers des capacités interactives avec des environnements côté client.

Semblable à l'exécution de fonctions, l'exécution JavaScript peut être effectuée de manière synchrone ou asynchrone avec les méthodes suivantes :

1. **`executeJs(String script)`** : Cette méthode prend une `String`, qui sera exécutée comme code JavaScript dans le client. Ce script est exécuté de manière synchrone, ce qui signifie que le **fil d'exécution est bloqué** jusqu'à ce que l'exécution JS retourne et résulte en un aller-retour. Les résultats de la fonction sont retournés sous forme d'`Object`, qui peut être converti et utilisé en Java.

2. **`executeJsAsync(String script)`** : Comme avec la méthode précédente, une chaîne de paramètre passée sera exécutée comme code JavaScript sur le client. Cette méthode s'exécute de manière asynchrone et **ne bloque pas le fil d'exécution**. Elle retourne un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, qui permet d'interagir davantage avec la fonction et sa charge utile.

:::tip
Ces méthodes ont accès au mot-clé `component`, qui donne au code JavaScript accès à l'instance côté client du composant exécutant le JavaScript.
:::
