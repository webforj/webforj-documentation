---
sidebar_position: 5
title: Elements
description: >-
  Integrate raw HTML tags and custom web components in webforJ using the Element
  class to add children, set content, and invoke JavaScript.
slug: element
_i18n_hash: 2af99ca4f1e5c8c2f7c31b3d7f647b41
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

Les développeurs de webforJ ont la possibilité de choisir non seulement parmi la riche bibliothèque de composants fournis, mais également d'intégrer des composants provenant d'autres sources. Pour faciliter cela, le composant `Element` peut être utilisé pour simplifier l'intégration de tout, des simples éléments HTML aux composants web personnalisés plus complexes.

:::important
Le composant `Element` ne peut pas être étendu et n'est pas le composant de base pour tous les composants au sein de webforJ. Pour en savoir plus sur la hiérarchie des composants de webforJ, lisez [cet article](../architecture/controls-components.md).
:::

<ComponentDemo
path='/webforj/elementinputdemo'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputDemoView.java',
  'src/main/frontend/css/element/elementInput.css',
]}
/>

## Ajouter des événements {#adding-events}

Afin d'utiliser des événements qui peuvent accompagner votre élément, vous pouvez utiliser les méthodes `addEventListener` du composant `Element`. Ajouter un événement nécessite au moins le type/nome de l'événement que le composant attend, et un écouteur à ajouter à l'événement.

Il existe également des options supplémentaires pour personnaliser davantage les événements en utilisant les configurations des options d'événement.

<ComponentDemo
path='/webforj/elementinputevent'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputEventView.java',
  'src/main/frontend/css/element/elementInputEvent.css',
]}
height='240px'
/>

## Interaction entre composants {#component-interaction}

Le composant `Element` agit comme un conteneur pour d'autres composants. Il fournit un moyen d'organiser et de récupérer des informations pour les composants enfants, et propose un ensemble clair de fonctions pour ajouter ou supprimer ces composants enfants au besoin.

### Ajouter des composants enfants {#adding-child-components}

Le composant `Element` prend en charge la composition de composants enfants. Les développeurs peuvent organiser et gérer des structures d'interface utilisateur complexes en ajoutant des composants en tant qu'enfants au `Element`. Trois méthodes existent pour définir le contenu d'un `Element` :

1. **`add(Component... components)`** : Cette méthode permet d'ajouter un ou plusieurs composants à un `String` optionnel qui désigne un slot spécifié lorsqu'il est utilisé avec un composant web. L'omission du slot ajoutera le composant entre les balises HTML.

2. **`setText(String text)`** : Cette méthode se comporte de manière similaire à la méthode `setHtml()`, mais injecte du texte littéral dans le `Element`.

  ```java
  // Affiché comme les caractères littéraux "<b>Status: prêt</b>"
  element.setText("<b>Status: prêt</b>");
  ```

  :::note Utilisation de la balise `<html>`
  Les versions antérieures de webforJ traitaient une valeur enveloppée dans `<html>` et passée à `setText()` comme du HTML. Ce comportement est obsolète et sera supprimé dans webforJ 27.00.

  La première fois qu'une valeur enveloppée dans `<html>` atteint `setText()`, un avertissement est enregistré qui nomme le composant et le site d'appel, afin que l'appel puisse être déplacé vers `setHtml()`.

  Pour adopter le défaut de webforJ 27.00 à l'avance, définissez `webforj.legacyHtmlInText` sur `false`. Dans une application Spring, la même valeur est définie via `webforj.legacy-html-in-text`.

  ```java
  // webforj.legacyHtmlInText = true (par défaut)
  element.setText("<html><b>Status: prêt</b></html>"); // rend en gras

  // webforj.legacyHtmlInText = false
  element.setText("<html><b>Status: prêt</b></html>"); // montre les caractères <b>Status: prêt</b>
  ```
  :::

3. **`setHtml(String html)`** : Cette méthode prend le `String` passé à la méthode et l'injecte en tant que HTML au sein du composant. En fonction du `Element`, cela peut être rendu de différentes manières.

  :::danger Cross-site Scripting (XSS)
  Par précaution contre les [attaques de cross-site scripting (XSS)](/docs/security/application-security/common-threats#cross-site-scripting-xss), n'utilisez `setHtml()` qu'avec du contenu que vous contrôlez directement.
  :::

<ComponentDemo
path='/webforj/elementinputtext'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputTextView.java',
  'src/main/frontend/css/element/elementInput.css',
]}
height='175px'
/>

:::tip
Appeler `setHtml()` ou `setText()` remplacera le contenu actuellement contenu entre les balises d'ouverture et de fermeture de l'élément.
:::

### Suppression de composants {#removing-components}

En plus d'ajouter des composants à un `Element`, les méthodes suivantes sont mises en œuvre pour la suppression de divers composants enfants :

1. **`remove(Component... components)`** : Cette méthode prend un ou plusieurs composants et les supprime en tant que composants enfants.

2. **`removeAll()`** : Cette méthode supprime tous les composants enfants du `Element`.

### Accéder aux composants {#accessing-components}

Pour accéder aux divers composants enfants présents dans un `Element`, ou des informations concernant ces composants, les méthodes suivantes sont disponibles :

1. **`getComponents()`** : Cette méthode renvoie une liste Java de tous les enfants du `Element`.

2. **`getComponents(String id)`** : Cette méthode est similaire à la méthode ci-dessus, mais prend l'ID côté serveur d'un composant spécifique et le renvoie lorsqu'il est trouvé.

3. **`getComponentCount()`** : Renvoie le nombre de composants enfants présents dans le `Element`.


## Appel de fonctions JavaScript {#calling-javascript-functions}

Le composant `Element` fournit deux méthodes API qui permettent d'appeler des fonctions JavaScript sur des éléments HTML.

1. **`callJsFunction(String functionName, Object... arguments)`** : Cette méthode prend un nom de fonction sous forme de chaîne, et prend en option un ou plusieurs objets comme paramètres pour la fonction. Cette méthode s'exécute de manière synchrone, ce qui signifie que le **fil d'exécution est bloqué** jusqu'à ce que la méthode JS retourne, entraînant un aller-retour. Les résultats de la fonction sont renvoyés sous forme d'un `Object`, qui peut être converti et utilisé en Java.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`** : Comme pour la méthode précédente, un nom de fonction et des arguments optionnels pour la fonction peuvent être passés. Cette méthode s'exécute de manière asynchrone et **ne bloque pas le fil d'exécution**. Elle renvoie un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, qui permet une interaction supplémentaire avec la fonction et sa charge utile.

### Passage de paramètres {#passing-parameters}

Les arguments qui sont passés à ces méthodes et qui sont utilisés dans l'exécution des fonctions JS sont sérialisés sous forme de tableau JSON. Il existe deux types d'arguments notables qui sont traités comme suit :
- `this` : L'utilisation du mot-clé `this` donnera à la méthode une référence à la version côté client du composant invoquant.
- `Component` : Toute instance de composant Java passée dans l'une des méthodes JsFunction sera remplacée par la version côté client du composant.

:::info
Les appels de fonctions synchrones et asynchrones attendront que le `Element` ait été ajouté au DOM avant d'exécuter une fonction, mais `callJsFunction()` n'attendra pas que des arguments `component` soient attachés, ce qui peut entraîner un échec. À l'inverse, invoquer `callJsFunctionAsync()` peut ne jamais se terminer si un argument de composant n'est jamais attaché.
:::

Dans la démo ci-dessous, un événement est ajouté à un `Button` HTML. Cet événement est alors déclenché de manière programmatique en appelant la méthode `callJsFunctionAsync()`. Le <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> résultant est ensuite utilisé pour créer une autre boîte de message une fois que la fonction asynchrone a été complétée.

<ComponentDemo
path='/webforj/elementinputfunction'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java',
  'src/main/frontend/css/element/elementInput.css',
]}
height='240px'
/>

## Exécution de JavaScript {#executing-javascript}

En plus d'exécuter JavaScript au niveau de l'application, il est également possible d'exécuter JavaScript au niveau du `Element`. Effectuer cette exécution au niveau du `Element` permet d'inclure le contexte de l'élément HTML dans l'exécution. C'est un outil puissant qui agit comme un conduit pour les développeurs d'accéder aux capacités interactives des environnements côté client.

Similaire à l'exécution de fonctions, l'exécution de JavaScript peut être effectuée de manière synchrone ou asynchrone avec les méthodes suivantes :

1. **`executeJs(String script)`** : Cette méthode prend un `String`, qui sera exécuté en tant que code JavaScript sur le client. Ce script s'exécute de manière synchrone, ce qui signifie que le **fil d'exécution est bloqué** jusqu'à ce que l'exécution JS retourne, entraînant un aller-retour. Les résultats de la fonction sont renvoyés sous forme d'un `Object`, qui peut être converti et utilisé en Java.

2. **`executeJsAsync(String script)`** : Comme pour la méthode précédente, un paramètre de type `String` passé sera exécuté en tant que code JavaScript sur le client. Cette méthode s'exécute de manière asynchrone et **ne bloque pas le fil d'exécution**. Elle renvoie un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, qui permet une interaction supplémentaire avec la fonction et sa charge utile.

:::tip
Ces méthodes ont accès au mot-clé `component`, qui donne au code JavaScript accès à l'instance côté client du composant exécutant le JavaScript.
:::
