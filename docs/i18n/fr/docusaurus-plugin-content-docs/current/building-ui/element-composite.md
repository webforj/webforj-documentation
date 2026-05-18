---
sidebar_position: 6
title: Element Composite
sidebar_class_name: has-new-content
slug: element_composite
_i18n_hash: de075e855ba84ee82ec08c2bef771ea8
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

La classe `ElementComposite` sert de fondation polyvalente pour la gestion des éléments composites dans les applications webforJ. Son objectif principal est de faciliter l'interaction avec les éléments HTML, représentés par la classe `Element`, en fournissant une approche structurée pour gérer les propriétés, les attributs et les écouteurs d'événements. Elle permet l'implémentation et la réutilisation d'éléments dans une application. Utilisez la classe `ElementComposite` lors de l'implémentation de Web Components pour une utilisation dans les applications webforJ.

Lors de l'utilisation de la classe `ElementComposite`, la méthode `getElement()` vous donne accès au composant `Element` sous-jacent. De même, la méthode `getNodeName()` vous fournit le nom de ce nœud dans le DOM.

:::tip
Il est possible de tout faire avec la classe `Element` elle-même, sans utiliser la classe `ElementComposite`. Cependant, les méthodes de `ElementComposite` vous offrent un moyen de réutiliser votre travail.
:::

Les exemples sur cette page démontrent comment implémenter le [composant web QR code de Shoelace](https://shoelace.style/components/qr-code) en utilisant la classe `ElementComposite`.

<ComponentDemo
path='/webforj/qrdemo'
files={['src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java']}
height='175px'
/>

## Descripteurs de propriétés et d'attributs {#property-and-attribute-descriptors}

Les propriétés et attributs dans les web components représentent l'état du composant. Ils sont souvent utilisés pour gérer des données ou des configurations. La classe `ElementComposite` fournit un moyen pratique de travailler avec des propriétés et des attributs.

Les propriétés et attributs peuvent être déclarés et initialisés en tant que membres `PropertyDescriptor` de la classe `ElementComposite` en cours d'écriture, puis utilisés dans le code. Pour définir des propriétés et des attributs, utilisez la méthode `set()` pour définir la valeur d'une propriété. Par exemple, `set(PropertyDescriptor<V> property, V value)` définit une propriété à une valeur spécifiée.

:::info
Les propriétés sont accessibles et manipulées en interne dans le code du composant et ne se reflètent pas dans le DOM. Les attributs, en revanche, font partie de l'interface externe du composant et peuvent être utilisés pour transmettre des informations dans un composant venant de l'extérieur, fournissant un moyen pour des éléments ou des scripts externes de configurer le composant.
:::

```java
// Exemple de propriété appelée TITLE dans une classe ElementComposite
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
// Exemple d'attribut appelé VALUE dans une classe ElementComposite
private final PropertyDescriptor<String> VALUE = PropertyDescriptor.attribute("value", "");
//...
set(TITLE, "Mon Titre");
set(VALUE, "Ma Valeur");
```

Utilisez la méthode `get()` dans la classe `ElementComposite` pour accéder et lire les propriétés. La méthode `get()` peut accepter une valeur `boolean` optionnelle, qui est false par défaut, pour indiquer si la méthode doit aller chercher la valeur côté client. Cela affecte les performances, mais peut être nécessaire si la propriété peut être modifiée uniquement sur le client.

Un `Type` peut également être passé à la méthode, ce qui dicte le type de résultat récupéré.

:::tip
Ce `Type` n'est pas explicitement nécessaire et ajoute une couche de spécification supplémentaire lors de la récupération des données.
:::

```java
// Exemple de propriété appelée TITLE dans une classe ElementComposite
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

Dans la démo ci-dessous, des propriétés ont été ajoutées pour le code QR en fonction de la documentation du web component. Des méthodes ont ensuite été mises en œuvre pour permettre aux utilisateurs d'obtenir et de définir les différentes propriétés qui ont été mises en œuvre.

<ComponentDemo
path='/webforj/qrproperties'
files={['src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java']}
height='250px'
/>

## Enregistrement d'événements {#event-registration}

Les événements permettent la communication entre différentes parties de votre application webforJ. La classe `ElementComposite` fournit un traitement des événements avec support pour le debounce, le throttle, le filtrage et la collecte de données d'événements personnalisées.

Enregistrez des écouteurs d'événements en utilisant la méthode `addEventListener()` :

```java
// Exemple : Ajouter un écouteur d'événements de clic
addEventListener(ElementClickEvent.class, event -> {
  // Gérer l'événement de clic
});
```

:::info
Les événements `ElementComposite` sont différents des événements `Element`, en ce sens qu'ils ne permettent aucune classe, mais seulement les classes `Event` spécifiées.
:::

### Classes d'événements intégrées {#built-in-event-classes}

webforJ fournit des classes d'événements pré-construites avec accès aux données typées :

- **ElementClickEvent** : Événements de clic de souris avec coordonnées (`getClientX()`, `getClientY()`), informations sur le bouton (`getButton()`) et touches de modification (`isCtrlKey()`, `isShiftKey()`, etc.)
- **ElementDefinedEvent** : Déclenché lorsqu'un élément personnalisé est défini dans le DOM et prêt à l'emploi
- **ElementEvent** : Classe d'événement de base fournissant un accès aux données brutes d'événements, type d'événement (`getType()`) et ID d'événement (`getId()`)

### Charges utiles des événements {#event-payloads}

Les événements transportent des données du client vers votre code Java. Accédez à ces données via `getData()` pour les données brutes d'événements ou utilisez des méthodes typées lorsque disponibles sur les classes d'événements intégrées. Pour plus de détails sur l'utilisation efficace des charges utiles des événements, consultez le [guide des événements](../building-ui/events).

## Classes d'événements personnalisées {#custom-event-classes}

Pour un traitement d'événements spécialisé, créez des classes d'événements personnalisées avec des charges utiles configurées en utilisant les annotations `@EventName` et `@EventOptions`.

Dans l'exemple ci-dessous, un événement de clic a été créé et ajouté au composant de code QR. Cet événement, lorsqu'il est déclenché, affichera la coordonnée "X" de la souris au moment du clic sur le composant, qui est fournie au Java événement en tant que données. Une méthode est ensuite mise en œuvre pour permettre à l'utilisateur d'accéder à ces données, qui est la façon dont elles sont affichées dans l'application.

<ComponentDemo
path='/webforj/qrevent'
files={['src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java']}
height='300px'
/>

## `ElementEventOptions` {#elementeventoptions}

`ElementEventOptions` vous permet de personnaliser le comportement des événements en configurant quelles données collecter, quand les événements sont déclenchés et comment ils sont traités. Voici un extrait de code complet montrant toutes les options de configuration :

```java
ElementEventOptions options = new ElementEventOptions()
  // Collecter des données personnalisées à partir du client
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")
  
  // Exécuter JavaScript avant le déclenchement de l'événement
  .setCode("component.classList.add('processing');")
  
  // Ne déclencher que si les conditions sont remplies
  .setFilter("component.value.length >= 2")
  
  // Retarder l'exécution jusqu'à ce que l'utilisateur arrête de taper (300ms)
  .setDebounce(300, DebouncePhase.TRAILING);

addEventListener("input", this::handleSearch, options);
```

### Contrôle des performances {#performance-control}

Contrôlez quand et à quelle fréquence les événements sont déclenchés :

**Debounce** retarde l'exécution jusqu'à ce que l'activité s'arrête :

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Attendre 300ms après le dernier événement
```

**Throttle** limite la fréquence d'exécution :

```java
options.setThrottle(100); // Déclencher au maximum une fois toutes les 100ms
```

Phases de debounce disponibles :

- `LEADING` : Déclencher immédiatement, puis attendre
- `TRAILING` : Attendre une période de calme, puis déclencher (par défaut)
- `BOTH` : Déclencher immédiatement et après une période de calme

## Fusion des options {#options-merging}

Combinez les configurations d'événements de différentes sources en utilisant `mergeWith()`. Les options de base fournissent des données communes pour tous les événements, tandis que les options spécifiques ajoutent une configuration spécialisée. Les options ultérieures remplacent les paramètres conflictuels.

```java
ElementEventOptions merged = baseOptions.mergeWith(specificOptions);
```

## Interaction avec les slots {#interacting-with-slots}

Les web components utilisent souvent des slots pour permettre aux développeurs de définir la structure d'un composant depuis l'extérieur. Un slot est un espace réservé à l'intérieur d'un web component qui peut être rempli avec du contenu lors de l'utilisation du composant. Dans le contexte de la classe `ElementComposite`, les slots offrent un moyen de personnaliser le contenu au sein d'un composant. Les méthodes suivantes sont fournies pour permettre aux développeurs d'interagir avec et de manipuler les slots :

1. **`findComponentSlot()`** : Cette méthode est utilisée pour rechercher un composant spécifique dans tous les slots d'un système de composants. Elle renvoie le nom du slot où le composant est situé. Si le composant n'est trouvé dans aucun slot, une chaîne vide est renvoyée.

2. **`getComponentsInSlot()`** : Cette méthode récupère la liste des composants assignés à un slot donné dans un système de composants. En option, passez un type de classe spécifique pour filtrer les résultats de la méthode.

3. **`getFirstComponentInSlot()`** : Cette méthode est conçue pour récupérer le premier composant assigné au slot. En option, passez un type de classe spécifique pour filtrer les résultats de cette méthode.

Il est également possible d'utiliser la méthode `add()` avec un paramètre `String` pour spécifier le slot souhaité dans lequel ajouter le composant passé.

Ces interactions permettent aux développeurs de tirer parti de la puissance des web components en fournissant une API claire et simple pour manipuler les slots, les propriétés et gérer les événements au sein de la classe `ElementComposite`.
