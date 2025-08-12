---
sidebar_position: 4
title: Element Composite
slug: element_composite
_i18n_hash: 78629dd08e77cbd5f111aabb094f8db8
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

La classe `ElementComposite` sert de fondation polyvalente pour la gestion des éléments composites dans les applications webforJ. Son objectif principal est de faciliter l'interaction avec les éléments HTML, représentés par la classe `Element`, en fournissant une approche structurée pour gérer les propriétés, les attributs et les écouteurs d'événements. Elle permet l'implémentation et la réutilisation des éléments dans une application. Utilisez la classe `ElementComposite` lors de l'implémentation de Web Components à utiliser dans les applications webforJ.

Lors de l'utilisation de la classe `ElementComposite`, utiliser la méthode `getElement()` vous donnera accès au composant `Element` sous-jacent. De même, la méthode `getNodeName()` vous donne le nom de ce nœud dans le DOM.

:::tip
Il est possible de tout faire avec la classe `Element` elle-même, sans utiliser la classe `ElementComposite`. Cependant, les méthodes fournies dans `ElementComposite` donnent aux utilisateurs un moyen de réutiliser le travail effectué.
:::

Tout au long de ce guide, nous allons implémenter le [composant web QR code Shoelace](https://shoelace.style/components/qr-code) en utilisant la classe `ElementComposite`.

<ComponentDemo 
path='/webforj/qrdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java'
height='175px'
/>

## Descripteurs de propriété et d'attribut {#property-and-attribute-descriptors}

Les propriétés et les attributs dans les composants web représentent l'état du composant. Ils sont souvent utilisés pour gérer des données ou des configurations. La classe `ElementComposite` fournit un moyen pratique de travailler avec des propriétés et des attributs.

Les propriétés et les attributs peuvent être déclarés et initialisés en tant que membres `PropertyDescriptor` de la classe `ElementComposite` en cours d'écriture, puis utilisés dans le code. Pour définir des propriétés et des attributs, utilisez la méthode `set()` pour définir la valeur d'une propriété. Par exemple, `set(PropertyDescriptor<V> property, V value)` définit une propriété à une valeur spécifiée.

:::info
Les propriétés sont accessibles et manipulées en interne dans le code du composant et ne se reflètent pas dans le DOM. Les attributs, en revanche, font partie de l'interface externe du composant et peuvent être utilisés pour transmettre des informations à un composant de l'extérieur, fournissant ainsi un moyen pour les éléments ou scripts externes de configurer le composant.
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

En plus de définir une propriété, utilisez la méthode `get()` dans la classe `ElementComposite` pour accéder à et lire les propriétés. La méthode `get()` peut recevoir une valeur `boolean` optionnelle, qui est fausse par défaut, pour indiquer si la méthode doit effectuer un déplacement vers le client pour récupérer la valeur. Cela a un impact sur les performances, mais peut être nécessaire si la propriété peut être modifiée uniquement côté client.

Un `Type` peut également être passé à la méthode, ce qui indique quel type doit être converti à partir du résultat récupéré.

:::tip
Ce `Type` n'est pas explicitement nécessaire et ajoute une couche supplémentaire de spécification lors de la récupération des données.
:::

```java
// Exemple de propriété appelée TITLE dans une classe ElementComposite
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

Dans la démonstration ci-dessous, des propriétés ont été ajoutées pour le QR code en fonction de la documentation pour le composant web. Des méthodes ont ensuite été implémentées permettant aux utilisateurs d'obtenir et de définir les différentes propriétés qui ont été mises en œuvre.

<ComponentDemo 
path='/webforj/qrproperties?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java'
height='250px'
/>

## Enregistrement d'événements {#event-registration}

Les événements sont une partie cruciale des composants web, permettant la communication entre différentes parties d'une application. La classe `ElementComposite` simplifie l'enregistrement et le traitement des événements. Pour enregistrer un écouteur d'événements, utilisez la méthode `addEventListener()` pour enregistrer des écouteurs d'événements pour des types d'événements spécifiques. Spécifiez la classe d'événement, l'écouteur et les options d'événements facultatives.

```java
// Exemple : Ajout d'un écouteur d'événement de clic
addEventListener(ClickEvent.class, event -> {
    // Gérer l'événement de clic
});
```

:::info
Les événements `ElementComposite` sont différents des événements `Element`, en ce sens qu'ils n'autorisent aucune classe, mais uniquement les classes `Event` spécifiées.
:::

Dans la démonstration ci-dessous, un événement de clic a été créé et ajouté au composant QR code. Cet événement, lorsqu'il est déclenché, affichera la coordonnée "X" de la souris au moment du clic sur le composant, qui est fournie comme données à l'événement Java. Une méthode est ensuite mise en œuvre pour permettre à l'utilisateur d'accéder à ces données, qui est la façon dont elles sont affichées dans l'application.
<ComponentDemo 
path='/webforj/qrevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java'
height='300px'
/>

## Interagir avec les Slots {#interacting-with-slots}

Les composants web utilisent souvent des slots pour permettre aux développeurs de définir la structure d'un composant de l'extérieur. Un slot est un espace réservé à l'intérieur d'un composant web qui peut être rempli avec du contenu lors de l'utilisation du composant. Dans le contexte de la classe `ElementComposite`, les slots offrent un moyen de personnaliser le contenu à l'intérieur d'un composant. Les méthodes suivantes sont fournies pour permettre aux développeurs d'interagir et de manipuler les slots :

1. **`findComponentSlot()`** : Cette méthode est utilisée pour rechercher un composant spécifique dans tous les slots d'un système de composants. Elle renvoie le nom du slot où se trouve le composant. Si le composant n'est trouvé dans aucun slot, une chaîne vide est renvoyée.

2. **`getComponentsInSlot()`** : Cette méthode récupère la liste des composants assignés à un slot donné dans un système de composants. En option, passez un type de classe spécifique pour filtrer les résultats de la méthode.

3. **`getFirstComponentInSlot()`** : Cette méthode est conçue pour récupérer le premier composant assigné au slot. Passez en option un type de classe spécifique pour filtrer les résultats de cette méthode.

Il est également possible d'utiliser la méthode `add()` avec un paramètre `String` pour spécifier le slot souhaité dans lequel ajouter le composant passé.

Ces interactions permettent aux développeurs de tirer parti de la puissance des composants web en fournissant une API claire et directe pour manipuler des slots, des propriétés et gérer des événements au sein de la classe `ElementComposite`.
