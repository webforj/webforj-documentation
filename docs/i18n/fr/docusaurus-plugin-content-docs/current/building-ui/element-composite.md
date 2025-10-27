---
sidebar_position: 1
title: Element Composite
sidebar_class_name: has-new-content
slug: element_composite
_i18n_hash: 6e201040e3dfd4be12037094eb9e978e
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

La classe `ElementComposite` sert de fondation polyvalente pour gérer des éléments composites dans les applications webforJ. Son objectif principal est de faciliter l'interaction avec les éléments HTML, représentés par la classe `Element`, en fournissant une approche structurée pour gérer les propriétés, les attributs et les écouteurs d'événements. Elle permet d'implémenter et de réutiliser des éléments dans une application. Utilisez la classe `ElementComposite` lors de l'implémentation de Web Components à utiliser dans les applications webforJ.

Lors de l'utilisation de la classe `ElementComposite`, l'utilisation de la méthode `getElement()` vous donnera accès au composant `Element` sous-jacent. De même, la méthode `getNodeName()` vous donne le nom de ce nœud dans le DOM.

:::tip
Il est possible de tout faire avec la classe `Element` elle-même, sans utiliser la classe `ElementComposite`. Cependant, les méthodes fournies dans l'`ElementComposite` offrent aux utilisateurs un moyen de réutiliser le travail effectué.
:::

Ce guide démontre comment implémenter le [composant web QR code Shoelace](https://shoelace.style/components/qr-code) en utilisant la classe `ElementComposite`.

<ComponentDemo 
path='/webforj/qrdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java'
height='175px'
/>

## Descripteurs de propriété et d'attribut {#property-and-attribute-descriptors}

Les propriétés et attributs dans les composants web représentent l'état du composant. Ils sont souvent utilisés pour gérer des données ou des configurations. La classe `ElementComposite` fournit un moyen pratique de travailler avec des propriétés et des attributs.

Les propriétés et attributs peuvent être déclarés et initialisés en tant que membres `PropertyDescriptor` de la classe `ElementComposite` en cours d'écriture, puis utilisés dans le code. Pour définir des propriétés et des attributs, utilisez la méthode `set()` pour définir la valeur d'une propriété. Par exemple, `set(PropertyDescriptor<V> property, V value)` définit une propriété à une valeur spécifiée.

:::info
Les propriétés sont accédées et manipulées en interne dans le code du composant et ne se reflètent pas dans le DOM. Les attributs, en revanche, font partie de l'interface externe du composant et peuvent être utilisés pour passer des informations à un composant depuis l'extérieur, fournissant un moyen pour des éléments ou des scripts externes de configurer le composant.
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

En plus de définir une propriété, utilisez la méthode `get()` dans la classe `ElementComposite` pour accéder et lire les propriétés. La méthode `get()` peut prendre une valeur `boolean` optionnelle, qui est fausse par défaut, pour indiquer si la méthode doit effectuer un aller-retour vers le client pour récupérer la valeur. Cela impacte les performances, mais peut être nécessaire si la propriété peut être modifiée uniquement sur le client.

Un `Type` peut également être passé à la méthode, ce qui indique à quoi caster le résultat récupéré.

:::tip
Ce `Type` n'est pas strictement nécessaire et ajoute une couche supplémentaire de spécification au moment où les données sont récupérées.
:::

```java
// Exemple de propriété appelée TITLE dans une classe ElementComposite
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

Dans la démonstration ci-dessous, des propriétés ont été ajoutées pour le code QR sur la base de la documentation du composant web. Des méthodes ont ensuite été mises en œuvre permettant aux utilisateurs d'obtenir et de définir les diverses propriétés qui ont été implémentées.

<ComponentDemo 
path='/webforj/qrproperties?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java'
height='250px'
/>

## Enregistrement d'événements {#event-registration}

Les événements permettent la communication entre différentes parties de votre application webforJ. La classe `ElementComposite` fournit un traitement des événements avec support pour le débouncing, le throttling, le filtrage et la collecte de données d'événements personnalisés.

Enregistrez des écouteurs d'événements à l'aide de la méthode `addEventListener()` :

```java
// Exemple : Ajout d'un écouteur d'événement de clic
addEventListener(ElementClickEvent.class, event -> {
    // Gérer l'événement de clic
});
```

:::info
Les événements `ElementComposite` sont différents des événements `Element`, en ce sens que cela ne permet pas n'importe quelle classe, mais uniquement les classes `Event` spécifiées.
:::

### Classes d'événements intégrées {#built-in-event-classes}

webforJ fournit des classes d'événements préconstruites avec accès aux données typées :

- **ElementClickEvent** : Événements de clic de souris avec coordonnées (`getClientX()`, `getClientY()`), informations sur le bouton (`getButton()`) et touches de modification (`isCtrlKey()`, `isShiftKey()`, etc.)
- **ElementDefinedEvent** : Déclenché lorsqu'un élément personnalisé est défini dans le DOM et prêt à être utilisé
- **ElementEvent** : Classe d'événement de base fournissant un accès aux données d'événements brutes, type d'événement (`getType()`) et ID d'événement (`getId()`)

### Charges utiles des événements {#event-payloads}

Les événements transportent des données du client vers votre code Java. Accédez à ces données via `getData()` pour les données d'événements brutes ou utilisez des méthodes typées lorsque disponibles sur les classes d'événements intégrées. Pour plus de détails sur l'utilisation efficace des charges utiles d'événements, consultez le [guide des événements](../building-ui/events).

## Classes d'événements personnalisées {#custom-event-classes}

Pour un traitement d'événements spécialisé, créez des classes d'événements personnalisées avec des charges utiles configurées en utilisant les annotations `@EventName` et `@EventOptions`.

Dans l'exemple ci-dessous, un événement de clic a été créé puis ajouté au composant QR code. Cet événement, lorsqu'il est déclenché, affichera la coordonnée "X" de la souris au moment du clic sur le composant, qui est fournie au code Java en tant que donnée. Une méthode est ensuite mise en œuvre pour permettre à l'utilisateur d'accéder à ces données, ce qui est la manière dont elles sont affichées dans l'application.

<ComponentDemo 
path='/webforj/qrevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java'
height='300px'
/>

## `ElementEventOptions` {#elementeventoptions}

`ElementEventOptions` vous permet de personnaliser le comportement des événements en configurant quelles données collecter, quand les événements sont déclenchés et comment ils sont traités. Voici un extrait de code complet montrant toutes les options de configuration :

```java
ElementEventOptions options = new ElementEventOptions()
    // Collecter des données personnalisées du client
    .addData("query", "component.value")
    .addData("timestamp", "Date.now()")
    .addData("isValid", "component.checkValidity()")
    
    // Exécuter JavaScript avant que l'événement ne se déclenche
    .setCode("component.classList.add('processing');")
    
    // Ne déclencher que si les conditions sont remplies
    .setFilter("component.value.length >= 2")
    
    // Retarder l'exécution jusqu'à ce que l'utilisateur arrête de taper (300 ms)
    .setDebounce(300, DebouncePhase.TRAILING);

addEventListener("input", this::handleSearch, options);
```

### Contrôle de performance {#performance-control}

Contrôlez quand et à quelle fréquence les événements se déclenchent :

**Le débouncing** retarde l'exécution jusqu'à ce que l'activité s'arrête :

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Attendre 300 ms après le dernier événement
```

**Le throttling** limite la fréquence d'exécution :

```java
options.setThrottle(100); // Déclencher au maximum une fois par 100 ms
```

Phases de débounce disponibles :

- `LEADING` : Déclencher immédiatement, puis attendre
- `TRAILING` : Attendre une période de calme, puis déclencher (par défaut)
- `BOTH` : Déclencher immédiatement et après la période de calme

## Fusion des options {#options-merging}

Combinez les configurations d'événements provenant de différentes sources à l'aide de `mergeWith()`. Les options de base fournissent des données communes pour tous les événements, tandis que des options spécifiques ajoutent une configuration spécialisée. Les options ultérieures remplacent les paramètres conflictuels.

```java
ElementEventOptions merged = baseOptions.mergeWith(specificOptions);
```

## Interaction avec les slots {#interacting-with-slots}

Les composants web utilisent souvent des slots pour permettre aux développeurs de définir la structure d'un composant depuis l'extérieur. Un slot est un espace réservé à l'intérieur d'un composant web pouvant être rempli avec du contenu lors de l'utilisation du composant. Dans le contexte de la classe `ElementComposite`, les slots fournissent un moyen de personnaliser le contenu au sein d'un composant. Les méthodes suivantes sont fournies pour permettre aux développeurs d'interagir avec et de manipuler les slots :

1. **`findComponentSlot()`** : Cette méthode est utilisée pour rechercher un composant spécifique à travers tous les slots dans un système de composants. Elle renvoie le nom du slot où le composant est situé. Si le composant n'est pas trouvé dans un slot, une chaîne vide est renvoyée.

2. **`getComponentsInSlot()`** : Cette méthode récupère la liste des composants assignés à un slot donné dans un système de composants. Optionnellement, passez un type de classe spécifique pour filtrer les résultats de la méthode.

3. **`getFirstComponentInSlot()`** : Cette méthode est conçue pour récupérer le premier composant assigné au slot. Optionnellement, passez un type de classe spécifique pour filtrer les résultats de cette méthode.

Il est également possible d'utiliser la méthode `add()` avec un paramètre `String` pour spécifier le slot désiré dans lequel ajouter le composant passé.

Ces interactions permettent aux développeurs d'exploiter la puissance des composants web en fournissant une API claire et simple pour manipuler les slots, les propriétés et gérer les événements au sein de la classe `ElementComposite`.
