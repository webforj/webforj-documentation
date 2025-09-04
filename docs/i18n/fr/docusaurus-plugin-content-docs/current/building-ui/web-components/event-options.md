---
sidebar_position: 4
title: Event Options
_i18n_hash: d780e41b809f0e3df55f65a1c71983a0
---
<!-- sidebar_class_name: sidebar--item__hidden -->
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="foundation" location="com/webforj/component/element/event/ElementEventOptions" top='true'/>

`ElementEventOptions` est un outil polyvalent de webforJ conçu pour encapsuler et gérer les paramètres de configuration pour les événements `Element` au sein des applications webforJ. En tant que conteneur pour diverses options, il permet aux développeurs de dicter précisément comment les événements associés aux éléments doivent être traités.

## Données d'événements {#event-data}

Les données d'événements sont une fonctionnalité clé de `ElementEventOptions`, permettant aux développeurs d'attacher des informations spécifiques aux options d'événements. Cette fonctionnalité facilite le passage de données personnalisées du client au serveur lorsqu'un événement est déclenché. Cette capacité est instrumentale pour transmettre un contexte ou des paramètres supplémentaires associés à l'événement, et permet à des informations d'être accessibles et utilisées sans nécessiter de demandes supplémentaires au client.

Par exemple, considérez un scénario où vous avez un événement de clic sur un bouton, et vous souhaitez passer le nom d'utilisateur du client actuel avec l'événement. Au lieu de récupérer le nom d'utilisateur d'un client à chaque fois, envoyez cette information avec l'événement en tant que données.

:::tip
Pour plus d'informations, consultez les pages sur les [événements](../../building-ui/events) et sur l'[Interaction Client/Serveur](../../architecture/client-server).
:::

Pour ajouter des données aux options de l'événement, vous pouvez utiliser la méthode `addData()`.

<!-- ### Exemple -->

## Exécution de JavaScript {#executing-javascript}

La classe `ElementEventOptions` permet aux développeurs de spécifier du code JavaScript à évaluer côté client avant que l'événement associé ne soit déclenché. Cette fonctionnalité permet aux clients de préparer les données de l'événement ou de déclencher d'autres événements si nécessaire. Cela est utile dans de nombreux cas, par exemple lorsqu'il s'agit de valider les données d'un formulaire côté client avant de les soumettre via un événement de soumission de formulaire.

### Utilisation {#usage}
Pour définir le code de l'événement, utilisez la méthode `setCode()`.

## Filtrage des événements {#filtering-events}

`ElementEventOptions` comprend une fonctionnalité visant à définir une expression de filtre à évaluer côté client avant que l'événement ne soit déclenché. Cette expression de filtre permet au client de déterminer si l'événement doit continuer ou être arrêté en fonction de certaines conditions. Considérez un champ de saisie où vous souhaitez déclencher un événement uniquement si le texte saisi répond à des critères spécifiques, tels qu'une longueur minimale.

### Utilisation {#usage-1}
Pour définir le filtre de l'événement, utilisez la méthode `setFilter()`.

## Debouncing et throttling {#debouncing-and-throttling}

### Objectif {#purpose}
`ElementEventOptions` fournit des mécanismes pour le debouncing et le throttling des événements. Ces fonctionnalités sont utiles pour contrôler la fréquence des écouteurs d'événements, garantissant qu'ils ne sont déclenchés que dans certaines conditions.

### Utilisation {#usage-2}
- Pour définir le debouncing, utilisez la méthode `setDebounce`.
- Pour définir le throttling, utilisez la méthode `setThrottle`.

### Exemple {#example}
Dans les scénarios où vous souhaitez gérer des saisies utilisateur rapides, comme les champs de saisie de recherche, vous pouvez utiliser le debouncing pour retarder l'exécution jusqu'à ce que l'utilisateur ait terminé de taper.

## Fusion des options d'événements {#merging-event-options}

La classe `ElementEventOptions` prend en charge la fusion avec d'autres instances, permettant aux développeurs d'agréger diverses options. Cette fonctionnalité est utile lors de la combinaison de paramètres provenant de différentes sources.

## Annotations {#annotations}

### Objectif {#purpose-1}
Pour plus de commodité, `ElementEventOptions` peut être configuré à l'aide d'annotations. Ces annotations offrent un moyen plus concis et expressif de définir les options d'événements.

### Exemple {#example-1}
Considérez l'exemple d'annotation suivant :

```java
@EventOptions(data = {@EventData(key = "value", exp = "component.value")},
      debounce = @DebounceSettings(value = 200))
```
