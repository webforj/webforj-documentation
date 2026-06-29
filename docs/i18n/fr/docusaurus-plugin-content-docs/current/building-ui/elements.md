---
sidebar_position: 5
title: Elements
description: >-
  Integrate raw HTML tags and custom web components in webforJ using the Element
  class to add children, set content, and invoke JavaScript.
slug: element
_i18n_hash: d941e314cdd63d19471e80936ef5d6bc
---
<JavadocLink type="foundation" location="com/webforj/component/element/Element" top='true'/>

Les développeurs de webforJ ont la possibilité de choisir non seulement parmi la riche bibliothèque de composants fournis, mais aussi d'intégrer des composants provenant d'ailleurs. Pour faciliter cela, le composant `Element` peut être utilisé pour simplifier l'intégration de tout, des éléments HTML simples aux composants web personnalisés plus complexes.

:::important
Le composant `Element` ne peut pas être étendu et n'est pas le composant de base pour tous les composants au sein de webforJ. Pour en savoir plus sur la hiérarchie des composants de webforJ, lisez [cet article](../architecture/controls-components.md).
:::

<ComponentDemo
path='/webforj/elementinputdemo'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputDemoView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
/>

## Ajout d'événements {#adding-events}

Afin d'utiliser les événements qui peuvent accompagner votre élément, vous pouvez utiliser les méthodes `addEventListener` du composant `Element`. L'ajout d'un événement nécessite au moins le type/le nom de l'événement que le composant attend, et un auditeur à ajouter à l'événement.

Il existe également des options supplémentaires pour personnaliser davantage les événements en utilisant les configurations des options d'événements.

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

### Ajout de composants enfants {#adding-child-components}

Le composant `Element` prend en charge la composition de composants enfants. Les développeurs peuvent organiser et gérer des structures d'interface utilisateur complexes en ajoutant des composants en tant qu'enfants au `Element`. Trois méthodes existent pour définir le contenu au sein d'un `Element` :

1. **`add(Component... components)`** : Cette méthode permet d'ajouter un ou plusieurs composants à un `String` optionnel qui désigne un emplacement spécifié lorsqu'il est utilisé avec un composant web. Omettre l'emplacement ajoutera le composant entre les balises HTML.

2. **`setText(String text)`** : Cette méthode se comporte de manière similaire à la méthode `setHtml()`, mais injecte du texte littéral dans le `Element`.

  ```java
  // Affiché comme les caractères littéraux "<b>Status: ready</b>"
  element.setText("<b>Status: ready</b>");
  ```

  :::note Utilisation de la balise `<html>`
  Les versions antérieures de webforJ considéraient une valeur enveloppée dans `<html>` et passée à `setText()` comme HTML. Ce comportement est obsolète et sera supprimé dans webforJ 27.00.

  La première fois qu'une valeur enveloppée dans `<html>` atteint `setText()`, un avertissement est enregistré mentionnant le composant et le site d'appel, afin que l'appel puisse être déplacé vers `setHtml()`.

  Pour adopter le comportement par défaut de webforJ 27.00 à l'avance, définissez `webforj.legacyHtmlInText` sur `false`. Dans une application Spring, la même valeur est définie via `webforj.legacy-html-in-text`.

  ```java
  // webforj.legacyHtmlInText = true (par défaut)
  element.setText("<html><b>Status: ready</b></html>"); // rend gras

  // webforj.legacyHtmlInText = false
  element.setText("<html><b>Status: ready</b></html>"); // affiche les caractères <b>Status: ready</b>
  ```
  :::

3. **`setHtml(String html)`** : Cette méthode prend le `String` passé à la méthode et l'injecte en tant qu'HTML au sein du composant. En fonction du `Element`, cela peut être rendu de différentes manières.

  :::danger Cross-site Scripting (XSS)
  Par précaution contre les [attaques par injection de scripts inter-sites (XSS)](/docs/security/application-security/common-threats#cross-site-scripting-xss), utilisez `setHtml()` uniquement avec du contenu que vous contrôlez directement.
  :::

<ComponentDemo
path='/webforj/elementinputtext'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputTextView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
height='175px'
/>

:::tip
L'appel de `setHtml()` ou `setText()` remplacera le contenu actuellement contenu entre les balises d'ouverture et de fermeture de l'élément.
:::

### Suppression de composants {#removing-components}

En plus d'ajouter des composants à un `Element`, les méthodes suivantes sont mises en œuvre pour la suppression de divers composants enfants :

1. **`remove(Component... components)`** : Cette méthode prend un ou plusieurs composants et les supprime en tant que composants enfants.

2. **`removeAll()`** : Cette méthode supprime tous les composants enfants du `Element`.

### Accès aux composants {#accessing-components}

Pour accéder aux différents composants enfants présents dans un `Element`, ou aux informations concernant ces composants, les méthodes suivantes sont disponibles :

1. **`getComponents()`** : Cette méthode retourne une `List` Java de tous les enfants du `Element`.

2. **`getComponents(String id)`** : Cette méthode est similaire à la méthode précédente, mais prend l'ID côté serveur d'un composant spécifique et le retourne s'il est trouvé.

3. **`getComponentCount()`** : Retourne le nombre de composants enfants présents dans le `Element`.

## Appel de fonctions JavaScript {#calling-javascript-functions}

Le composant `Element` fournit deux méthodes API qui permettent d'appeler des fonctions JavaScript sur des éléments HTML.

1. **`callJsFunction(String functionName, Object... arguments)`** : Cette méthode prend un nom de fonction sous forme de chaîne et prend éventuellement un ou plusieurs objets comme paramètres pour la fonction. Cette méthode est exécutée de manière synchrone, ce qui signifie que **le fil d'exécution est bloqué** jusqu'à ce que la méthode JS retourne, entraînant un aller-retour. Les résultats de la fonction sont retournés en tant qu'`Object`, qui peut être converti et utilisé en Java.

2. **`callJsFunctionAsync(String functionName, Object... arguments)`** : Comme avec la méthode précédente, un nom de fonction et des arguments optionnels pour la fonction peuvent être passés. Cette méthode s'exécute de manière asynchrone et **ne bloque pas le fil d'exécution**. Elle retourne un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, qui permet une interaction supplémentaire avec la fonction et sa charge utile.

### Passage de paramètres {#passing-parameters}

Les arguments qui sont passés à ces méthodes et qui sont utilisés dans l'exécution des fonctions JS sont sérialisés sous la forme d'un tableau JSON. Il existe deux types d'arguments notables qui sont gérés comme suit :
- `this` : L'utilisation du mot-clé `this` donnera à la méthode une référence à la version côté client du composant invoquant.
- `Component` : Toute instance de composant Java passée dans l'une des méthodes JsFunction sera remplacée par la version côté client du composant.

:::info
Les appels de fonctions synchrones et asynchrones attendront que le `Element` ait été ajouté au DOM avant d'exécuter une fonction, mais `callJsFunction()` n'attendra pas que des arguments `component` soient attachés, ce qui peut entraîner un échec. En revanche, l'invocation de `callJsFunctionAsync()` peut ne jamais se terminer si un argument composant n'est jamais attaché.
:::

Dans la démonstration ci-dessous, un événement est ajouté à un bouton HTML. Cet événement est ensuite déclenché de manière programmatique en appelant la méthode `callJsFunctionAsync()`. Le <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> résultant est ensuite utilisé pour créer une autre boîte de message une fois que la fonction asynchrone a été complétée.

<ComponentDemo
path='/webforj/elementinputfunction'
files={[
  'src/main/java/com/webforj/samples/views/element/ElementInputFunctionView.java',
  'src/main/resources/static/css/element/elementInput.css',
]}
height='240px'
/>

## Exécution de JavaScript {#executing-javascript}

En plus d'exécuter JavaScript au niveau de l'application, il est possible d'exécuter JavaScript au niveau de l'`Element` également. Effectuer cette exécution au niveau de l'`Element` permet d'inclure le contexte de l'élément HTML dans l'exécution. C'est un outil puissant qui agit comme un conduit pour les développeurs vers des capacités interactives avec les environnements côté client.

De manière similaire à l'exécution de fonctions, l'exécution de JavaScript peut se faire de manière synchrone ou asynchrone avec les méthodes suivantes :

1. **`executeJs(String script)`** : Cette méthode prend une `String`, qui sera exécutée comme code JavaScript sur le client. Ce script est exécuté de manière synchrone, ce qui signifie que **le fil d'exécution est bloqué** jusqu'à ce que l'exécution de JS retourne, entraînant un aller-retour. Les résultats de la fonction sont retournés sous forme d'`Object`, qui peut être converti et utilisé en Java.

2. **`executeJsAsync(String script)`** : Comme avec la méthode précédente, un paramètre `String` passé sera exécuté comme code JavaScript sur le client. Cette méthode s'exécute de manière asynchrone et **ne bloque pas le fil d'exécution**. Elle retourne un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, qui permet une interaction supplémentaire avec la fonction et sa charge utile.

:::tip
Ces méthodes ont accès au mot-clé `component`, qui donne au code JavaScript accès à l'instance côté client du composant exécutant le JavaScript.
:::
