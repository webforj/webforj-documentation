---
sidebar_position: 6
title: Element Composite
sidebar_class_name: has-new-content
slug: element_composite
_i18n_hash: 1fc82a7db864ec48118fb611a94a57fc
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

La classe `ElementComposite` sert de fondation polyvalente pour la gestion des éléments composites dans les applications webforJ. Son but principal est de faciliter l'interaction avec les éléments HTML, représentés par la classe `Element`, en fournissant une approche structurée pour gérer les propriétés, les attributs et les écouteurs d'événements. Elle permet la mise en œuvre et la réutilisation des éléments dans une application. Utilisez la classe `ElementComposite` lors de l'implémentation des Web Components pour une utilisation dans les applications webforJ.

Lors de l'utilisation de la classe `ElementComposite`, l'utilisation de la méthode `getElement()` vous donnera accès au composant `Element` sous-jacent. De même, la méthode `getNodeName()` vous donne le nom de ce nœud dans le DOM.

:::tip
Il est possible de tout faire avec la classe `Element` elle-même, sans utiliser la classe `ElementComposite`. Cependant, les méthodes fournies dans la `ElementComposite` offrent aux utilisateurs un moyen de réutiliser le travail effectué.
:::

Ce guide démontre comment implémenter le [composant web QR code Shoelace](https://shoelace.style/components/qr-code) en utilisant la classe `ElementComposite`.

<ComponentDemo 
path='/webforj/qrdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java'
height='175px'
/>

## Descripteurs de propriété et d'attribut {#property-and-attribute-descriptors}

Les propriétés et attributs dans les composants web représentent l'état du composant. Ils sont souvent utilisés pour gérer des données ou des configurations. La classe `ElementComposite` fournit un moyen pratique de travailler avec des propriétés et des attributs.

Les propriétés et attributs peuvent être déclarés et initialisés comme des membres `PropertyDescriptor` de la classe `ElementComposite` en cours d'écriture, puis utilisés dans le code. Pour définir des propriétés et des attributs, utilisez la méthode `set()` pour définir la valeur d'une propriété. Par exemple, `set(PropertyDescriptor<V> property, V value)` définit une propriété à une valeur spécifiée.

:::info
Les propriétés sont accessibles et manipulées en interne dans le code du composant et ne se reflètent pas dans le DOM. Les attributs, en revanche, font partie de l'interface externe du composant et peuvent être utilisés pour transmettre des informations à un composant depuis l'extérieur, fournissant ainsi un moyen pour des éléments ou des scripts externes de configurer le composant.
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

En plus de définir une propriété, utilisez la méthode `get()` dans la classe `ElementComposite` pour accéder et lire les propriétés. La méthode `get()` peut prendre une valeur `boolean` optionnelle, qui est fausse par défaut, pour indiquer si la méthode doit faire un aller-retour au client pour récupérer la valeur. Cela a un impact sur les performances, mais peut être nécessaire si la propriété peut être modifiée uniquement sur le client.

Un `Type` peut également être passé à la méthode, ce qui indique vers quoi le résultat récupéré doit être converti.

:::tip
Ce `Type` n'est pas clairement nécessaire et ajoute une couche supplémentaire de spécification lors de la récupération des données.
:::

```java
// Exemple de propriété appelée TITLE dans une classe ElementComposite
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

Dans la démo ci-dessous, des propriétés ont été ajoutées pour le QR code en fonction de la documentation du composant web. Des méthodes ont ensuite été mises en œuvre pour permettre aux utilisateurs d'obtenir et de définir les différentes propriétés mises en œuvre.

<ComponentDemo 
path='/webforj/qrproperties?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java'
height='250px'
/>

## Enregistrement des événements {#event-registration}

Les événements permettent la communication entre différentes parties de votre application webforJ. La classe `ElementComposite` fournit une gestion des événements avec prise en charge du debouncing, throttling, filtrage et collecte de données d'événements personnalisés.

Inscrivez des écouteurs d'événements en utilisant la méthode `addEventListener()` :

```java
// Exemple : Ajout d'un écouteur d'événement click
addEventListener(ElementClickEvent.class, event -> {
  // Gérer l'événement de clic
});
```

:::info
Les événements de la `ElementComposite` sont différents des événements de `Element`, car cela ne permet aucune classe, mais uniquement les classes `Event` spécifiées.
:::

### Classes d'événements intégrées {#built-in-event-classes}

webforJ fournit des classes d'événements préconstruites avec accès aux données typées :

- **ElementClickEvent** : Événements de clic de souris avec coordonnées (`getClientX()`, `getClientY()`), informations sur le bouton (`getButton()`) et clés modificateurs (`isCtrlKey()`, `isShiftKey()`, etc.)
- **ElementDefinedEvent** : Déclenché lorsqu'un élément personnalisé est défini dans le DOM et prêt à être utilisé
- **ElementEvent** : Classe d'événement de base fournissant l'accès aux données d'événement brutes, au type d'événement (`getType()`) et à l'ID d'événement (`getId()`)

### Charge utile des événements {#event-payloads}

Les événements transportent des données du client vers votre code Java. Accédez à ces données via `getData()` pour les données d'événements brutes ou utilisez des méthodes typées lorsque disponibles sur les classes d'événements intégrés. Pour plus de détails sur l'utilisation efficace des charges utiles d'événements, voir le [guide des événements](../building-ui/events).

## Classes d'événements personnalisées {#custom-event-classes}

Pour un traitement d'événements spécialisé, créez des classes d'événements personnalisées avec des charges utiles configurées en utilisant les annotations `@EventName` et `@EventOptions`.

Dans l'exemple ci-dessous, un événement de clic a été créé puis ajouté au composant QR code. Cet événement, lorsqu'il est déclenché, affichera la coordonnée "X" de la souris au moment où l'utilisateur clique sur le composant, ce qui est fourni à l'événement Java en tant que données. Une méthode est ensuite mise en œuvre pour permettre à l'utilisateur d'accéder à ces données, qui est la façon dont elles sont affichées dans l'application.

<ComponentDemo 
path='/webforj/qrevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java'
height='300px'
/>

## `ElementEventOptions` {#elementeventoptions}

`ElementEventOptions` vous permet de personnaliser le comportement des événements en configurant quelles données collecter, quand les événements se déclenchent et comment ils sont traités. Voici un extrait de code complet montrant toutes les options de configuration :

```java
ElementEventOptions options = new ElementEventOptions()
  // Collecter des données personnalisées depuis le client
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")
  
  // Exécuter JavaScript avant que l'événement ne se déclenche
  .setCode("component.classList.add('processing');")
  
  // Ne se déclenche que si les conditions sont remplies
  .setFilter("component.value.length >= 2")
  
  // Retarder l'exécution jusqu'à ce que l'utilisateur arrête de taper (300ms)
  .setDebounce(300, DebouncePhase.TRAILING);

addEventListener("input", this::handleSearch, options);
```

### Contrôle des performances {#performance-control}

Contrôlez quand et à quelle fréquence les événements se déclenchent :

**Le debouncing** retarde l'exécution jusqu'à ce que l'activité cesse :

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Attendre 300ms après le dernier événement
```

**Le throttling** limite la fréquence d'exécution :

```java
options.setThrottle(100); // Déclenche au maximum une fois toutes les 100ms
```

Phases de debounce disponibles :

- `LEADING` : Déclenche immédiatement, puis attend
- `TRAILING` : Attend une période de silence, puis déclenche (défaut)
- `BOTH` : Déclenche immédiatement et après la période de silence

## Fusion des options {#options-merging}

Combinez les configurations d'événements provenant de différentes sources en utilisant `mergeWith()`. Les options de base fournissent des données communes pour tous les événements, tandis que des options spécifiques ajoutent des configurations spécialisées. Les options ultérieures remplacent les paramètres en conflit.

```java
ElementEventOptions merged = baseOptions.mergeWith(specificOptions);
```

## Interaction avec les slots {#interacting-with-slots}

Les composants web utilisent souvent des slots pour permettre aux développeurs de définir la structure d'un composant depuis l'extérieur. Un slot est un espace réservé à l'intérieur d'un composant web qui peut être rempli de contenu lors de l'utilisation du composant. Dans le contexte de la classe `ElementComposite`, les slots offrent un moyen de personnaliser le contenu au sein d'un composant. Les méthodes suivantes sont fournies pour permettre aux développeurs d'interagir avec et de manipuler les slots :

1. **`findComponentSlot()`** : Cette méthode est utilisée pour rechercher un composant spécifique dans tous les slots d'un système de composants. Elle renvoie le nom du slot où le composant se trouve. Si le composant n'est trouvé dans aucun slot, une chaîne vide est renvoyée.

2. **`getComponentsInSlot()`** : Cette méthode récupère la liste des composants assignés à un slot donné dans un système de composants. En option, passez un type de classe spécifique pour filtrer les résultats de la méthode.

3. **`getFirstComponentInSlot()`** : Cette méthode est conçue pour récupérer le premier composant assigné au slot. En option, passez un type de classe spécifique pour filtrer les résultats de cette méthode.

Il est également possible d'utiliser la méthode `add()` avec un paramètre `String` pour spécifier le slot désiré dans lequel ajouter le composant passé.

Ces interactions permettent aux développeurs d'exploiter la puissance des composants web en fournissant une API propre et directe pour manipuler les slots, les propriétés et gérer les événements au sein de la classe `ElementComposite`.
