---
title: BusyIndicator
sidebar_position: 10
_i18n_hash: 0ecb07a1364b90d27e17484ade2199ae
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

Le `BusyIndicator` fournit des indices visuels pour s'assurer que les utilisateurs sont conscients des processus en cours, les empêchant d'interagir avec le système prématurément. Il couvre généralement toute l'interface de l'application pour les opérations globales.

Alors que le composant [`Loading`](../components/loading) se concentre sur des zones ou des composants spécifiques au sein de l'application, le `BusyIndicator` gère les processus globaux, à l'échelle de l'application, et bloque l'interaction sur l'ensemble de l'interface. Cette différence de portée rend le composant [`Loading`](../components/loading) idéal pour des scénarios plus localisés et spécifiques aux composants, comme le chargement de données dans une section particulière d'une page. En revanche, le `BusyIndicator` est adapté aux opérations à l'échelle du système qui affectent l'ensemble de l'application, comme l'initialisation de l'application ou l'exécution d'une synchronisation majeure des données.

## Basics {#basics}

Le `BusyIndicator` dans webforJ s'affiche sous forme de simple spinner, ce qui le rend facile à utiliser sans configuration. Cependant, vous pouvez le personnaliser en ajoutant un message, en ajustant le thème du spinner ou en modifiant les paramètres de visibilité. Cela vous permet de fournir plus de contexte ou de style tout en maintenant une solution fonctionnelle, prête à l'emploi.

Dans cet exemple, le `BusyIndicator` empêche toute action de l'utilisateur sur l'ensemble de l'interface jusqu'à ce que l'opération soit terminée.

<ComponentDemo 
path='/webforj/busydemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java'
height = '300px'
/>

## Backdrops {#backdrops}

Le composant `BusyIndicator` dans webforJ vous permet d'afficher un arrière-plan pour bloquer l'interaction utilisateur pendant qu'un processus est en cours. Par défaut, le composant active l'arrière-plan, mais vous avez la possibilité de le désactiver si nécessaire.

Le `BusyIndicator` montre un arrière-plan par défaut. Vous pouvez contrôler la visibilité de l'arrière-plan en utilisant la méthode `setBackdropVisible()`, comme montré ci-dessous :

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Désactive l'arrière-plan
busyIndicator.open();
```
:::info Désactivation de l'Arrière-plan
Même lorsque vous désactivez l'arrière-plan, le composant `BusyIndicator` continue de bloquer l'interaction utilisateur pour garantir que le processus sous-jacent se termine sans interruption. L'arrière-plan contrôle simplement la superposition visuelle, et non le comportement de blocage de l'interaction.
:::

## `Spinner` {#spinner}

Le composant `BusyIndicator` dans webforJ inclut un `Spinner` qui indique visuellement qu'une opération en arrière-plan est en cours. Vous pouvez personnaliser ce spinner avec plusieurs options, y compris sa taille, sa vitesse, sa direction, son thème et sa visibilité.

Voici un exemple de la façon dont vous pouvez personnaliser le spinner dans un composant `BusyIndicator` :

<ComponentDemo 
path='/webforj/busyspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java'
height = '200px'
/>

## Use cases {#use-cases}
- **Traitement à l'échelle de la page**  
   Le `BusyIndicator` est bien adapté pour de plus grandes opérations à l'échelle de la page, par exemple lorsque l'utilisateur initie une tâche qui affecte l'ensemble de la page, comme le téléchargement d'un fichier ou le traitement de données sur plusieurs sections. Il peut informer les utilisateurs que l'ensemble de l'application est en cours d'exécution, empêchant toute interaction jusqu'à ce que le processus soit terminé.

- **Opérations critiques du système**  
   Lors de l'exécution de tâches critiques pour le système telles que la synchronisation des données, l'application de mises à jour systématiques ou le traitement d'informations sensibles, le `BusyIndicator` fournit un retour visuel clair indiquant qu'une opération majeure est en cours, permettant à l'utilisateur d'attendre qu'elle soit terminée.

- **Chargements de données asynchrones**  
   Dans les scénarios où un traitement de données asynchrones est impliqué, comme lors de l'appel de plusieurs API ou de l'attente de calculs complexes, le composant `BusyIndicator` indique activement que le système est occupé, incitant les utilisateurs à patienter avant d'effectuer d'autres actions.

## Styling {#styling}

<TableBuilder name="BusyIndicator" />
