---
sidebar_position: 7
title: Event Options
_i18n_hash: 64cfa37f974517956ccb3fd75618df50
---
<!-- sidebar_class_name: sidebar--item__hidden -->
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="foundation" location="com/webforj/component/element/event/ElementEventOptions" top='true'/>

`ElementEventOptions` est un outil polyvalent de webforJ conçu pour encapsuler et gérer les paramètres de configuration pour les événements `Element` au sein des applications webforJ. En tant que conteneur pour diverses options, il permet aux développeurs de dictater précisément comment les événements associés aux éléments doivent être traités.

## Données d'événements {#event-data}

Les données d'événements sont une fonctionnalité clé de `ElementEventOptions`, permettant aux développeurs d'attacher des informations spécifiques aux options d'événements. Cette fonctionnalité facilite le passage de données personnalisées du client au serveur lorsqu'un événement est déclenché. Cette capacité est essentielle pour transmettre un contexte ou des paramètres supplémentaires associés à l'événement, et permet d'accéder et d'utiliser des informations sans nécessiter des déplacements supplémentaires vers le client.

Par exemple, considérons un scénario où vous avez un événement de clic sur un bouton, et vous souhaitez passer le nom d'utilisateur du client actuel avec l'événement. Au lieu de requêter le nom d'utilisateur d'un client chaque fois, envoyez cette information avec l'événement en tant que données.

:::tip
Pour plus d'informations, consultez les pages [events](/docs/building-ui/events) et [Client/Server Interaction](/docs/architecture/client-server).
:::

Pour ajouter des données aux options d'événements, vous pouvez utiliser la méthode `addData()`.

<!-- ### Exemple -->

## Exécution de JavaScript {#executing-javascript}

La classe `ElementEventOptions` permet aux développeurs de spécifier le code JavaScript à évaluer côté client avant que l'événement associé ne se déclenche. Cette fonctionnalité permet aux clients de préparer les données d'événements ou de déclencher d'autres événements si nécessaire. Cela est utile dans de nombreux cas, par exemple, lorsqu'il s'agit de valider les données de formulaire côté client avant de les soumettre via un événement de soumission de formulaire.

### Utilisation {#usage}
Pour définir le code de l'événement, utilisez la méthode `setCode()`.

## Filtrage des événements {#filtering-events}

`ElementEventOptions` comprend une fonctionnalité pour définir une expression de filtre à évaluer côté client avant que l'événement ne se déclenche. Cette expression de filtre permet au client de déterminer si l'événement doit se poursuivre ou être arrêté en fonction de certaines conditions. Considérez un champ de saisie où vous souhaitez déclencher un événement uniquement si le texte saisi répond à des critères spécifiques, tels qu'une longueur minimale.

### Utilisation {#usage-1}
Pour définir le filtre d'événements, utilisez la méthode `setFilter()`.

## Débounce et throttling {#debouncing-and-throttling}

### Objectif {#purpose}
`ElementEventOptions` fournit des mécanismes pour le debounce et le throttling des événements. Ces fonctionnalités sont utiles pour contrôler la fréquence des gestionnaires d'événements, garantissant qu'ils ne sont déclenchés que sous certaines conditions.

### Utilisation {#usage-2}
- Pour définir le debounce, utilisez la méthode `setDebounce`.
- Pour définir le throttle, utilisez la méthode `setThrottle`.

### Exemple {#example}
Dans des scénarios où vous souhaitez gérer une saisie utilisateur rapide, comme dans des champs de saisie de recherche, vous pouvez utiliser le debounce pour retarder l'exécution jusqu'à ce que l'utilisateur ait terminé de taper.

## Fusion des options d'événements {#merging-event-options}

La classe `ElementEventOptions` prend en charge la fusion avec d'autres instances, permettant aux développeurs d'agréger diverses options. Cette fonctionnalité est utile lors de la combinaison des paramètres de différentes sources.

## Annotations {#annotations}

### Objectif {#purpose-1}
Pour plus de commodité, `ElementEventOptions` peut être configuré à l'aide d'annotations. Ces annotations offrent un moyen plus concis et expressif de définir les options d'événements.

### Exemple {#example-1}
Considérez l'exemple d'annotation suivant :

```java
@EventOptions(data = {@EventData(key = "value", exp = "component.value")},
debounce = @DebounceSettings(value = 200))
```
