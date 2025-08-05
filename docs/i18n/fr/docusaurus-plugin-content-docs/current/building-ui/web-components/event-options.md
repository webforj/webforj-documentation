---
sidebar_position: 4
title: Event Options
_i18n_hash: 8bf57e40eec8e571f3d62266e388f114
---
<!-- sidebar_class_name: sidebar--item__hidden -->
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="foundation" location="com/webforj/component/element/event/ElementEventOptions" top='true'/>

`ElementEventOptions` est un outil polyvalent de webforJ conçu pour encapsuler et gérer les paramètres de configuration pour les événements `Element` au sein des applications webforJ. En tant que conteneur pour diverses options, il permet aux développeurs de dicter précisément comment les événements associés aux éléments doivent être traités.

## Données d'événement {#event-data}

Les données d'événement sont une fonctionnalité clé de `ElementEventOptions`, permettant aux développeurs d'attacher des informations spécifiques aux options d'événements. Cette fonctionnalité facilite le passage de données personnalisées du client au serveur lorsqu'un événement est déclenché. Cette capacité est essentielle pour transmettre un contexte ou des paramètres supplémentaires associés à l'événement, et permet d'accéder à des informations sans nécessiter de déplacements supplémentaires vers le client.

Par exemple, considérons un scénario où vous avez un événement de clic sur un bouton, et vous souhaitez passer le nom d'utilisateur actuel de l'utilisateur avec l'événement. Au lieu de requêter le nom d'utilisateur d'un client à chaque fois, envoyez cette information avec l'événement en tant que données.

:::tip
Pour plus d'informations, consultez les pages [événements](../../building-ui/events) et [Interaction Client/Serveur](../../architecture/client-server).
:::

Pour ajouter des données aux options d'événements, vous pouvez utiliser la méthode `addData()`.

<!-- ### Example -->

## Exécution de JavaScript {#executing-javascript}

La classe `ElementEventOptions` permet aux développeurs de spécifier du code JavaScript à évaluer côté client avant que l'événement associé ne soit déclenché. Cette fonctionnalité permet aux clients de préparer des données d'événements ou de déclencher d'autres événements selon les besoins. Cela est utile dans de nombreux cas, par exemple lors de la validation des données de formulaire côté client avant de les soumettre via un événement de soumission de formulaire.

### Utilisation {#usage}
Pour définir le code de l'événement, utilisez la méthode `setCode()`.

## Filtrage des événements {#filtering-events}

`ElementEventOptions` inclut une fonctionnalité pour définir une expression de filtre à évaluer côté client avant que l'événement ne soit déclenché. Cette expression de filtre permet au client de déterminer si l'événement doit se poursuivre ou être arrêté en fonction de certaines conditions. Considérez un champ de saisie où vous souhaitez déclencher un événement uniquement si le texte saisi répond à des critères spécifiques, tels qu'une longueur minimale.

### Utilisation {#usage-1}
Pour définir le filtre d'événement, utilisez la méthode `setFilter()`.

## Debouncing et throttling {#debouncing-and-throttling}

### Objectif {#purpose}
`ElementEventOptions` fournit des mécanismes pour le debouncing et le throttling des événements. Ces fonctionnalités sont utiles pour contrôler la fréquence des écouteurs d'événements, en veillant à ce qu'ils soient déclenchés uniquement dans certaines conditions.

### Utilisation {#usage-2}
- Pour définir le debouncing, utilisez la méthode `setDebounce`.
- Pour définir le throttling, utilisez la méthode `setThrottle`.

### Exemple {#example}
Dans des scénarios où vous souhaitez gérer des entrées utilisateur rapides, comme des champs de recherche, vous pouvez utiliser le debouncing pour retarder l'exécution jusqu'à ce que l'utilisateur ait terminé de tape.

## Fusion des options d'événements {#merging-event-options}

La classe `ElementEventOptions` prend en charge la fusion avec d'autres instances, permettant aux développeurs d'agréger différentes options. Cette fonctionnalité est utile pour combiner des paramètres de sources diverses.

## Annotations {#annotations}

### Objectif {#purpose-1}
Pour plus de commodité, `ElementEventOptions` peut être configuré à l'aide d'annotations. Ces annotations offrent un moyen plus concis et expressif de définir les options d'événements.

### Exemple {#example-1}
Considérez l'exemple d'annotation suivant :

```java
@EventOptions(data = {@EventData(key = "value", exp = "component.value")},
      debounce = @DebounceSettings(value = 200))
```
