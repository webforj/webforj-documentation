---
title: BusyIndicator
sidebar_position: 10
_i18n_hash: 456b6118cd6219f530c5292611ba46e0
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

Le `BusyIndicator` est une superposition en plein écran qui signale un processus en cours et bloque l'interaction de l'utilisateur jusqu'à ce qu'il soit terminé. Il couvre toute l'interface pendant des opérations comme l'initialisation ou la synchronisation des données. Alors que le composant [`Loading`](../components/loading) se concentre sur des zones spécifiques de l'interface, le `BusyIndicator` s'applique globalement.

<!-- INTRO_END -->

## Basics {#basics}

Le `BusyIndicator` dans webforJ se présente comme un simple spinner, ce qui le rend facile à utiliser sans configuration. Cependant, vous pouvez le personnaliser en ajoutant un message, en ajustant le thème du spinner ou en modifiant les paramètres de visibilité. Cela vous permet de fournir plus de contexte ou de style tout en maintenant une solution fonctionnelle et prête à l'emploi.

Dans cet exemple, le `BusyIndicator` empêche toute action de l'utilisateur à travers toute l'interface jusqu'à ce que l'opération soit terminée.

<ComponentDemo
path='/webforj/busydemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java']}
height='300px'
/>

## Backdrops {#backdrops}

Le composant `BusyIndicator` dans webforJ vous permet d'afficher un backdrop pour bloquer l'interaction de l'utilisateur pendant qu'un processus est en cours. Par défaut, le composant active le backdrop, mais vous avez la possibilité de le désactiver si nécessaire.

Le `BusyIndicator` affiche un backdrop par défaut. Vous pouvez contrôler la visibilité du backdrop en utilisant la méthode `setBackdropVisible()`, comme montré ci-dessous :

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Désactive le backdrop
busyIndicator.open();
```
:::info Désactivation du Backdrop
Même lorsque vous désactivez le backdrop, le composant `BusyIndicator` continue de bloquer l'interaction de l'utilisateur pour garantir que le processus sous-jacent se termine sans interruption. Le backdrop contrôle simplement la superposition visuelle, pas le comportement de blocage de l'interaction.
:::

## `Spinner` {#spinner}

Le composant `BusyIndicator` dans webforJ inclut un `Spinner` qui indique visuellement qu'une opération en arrière-plan est en cours. Vous pouvez personnaliser ce spinner avec plusieurs options, y compris sa taille, sa vitesse, sa direction, son thème et sa visibilité.

Voici un exemple de la façon dont vous pouvez personnaliser le spinner dans un composant `BusyIndicator` :

<ComponentDemo
path='/webforj/busyspinnerdemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java']}
height='200px'
/>

## Use cases {#use-cases}
- **Traitement à l'échelle de la page**  
   Le `BusyIndicator` est bien adapté aux opérations à grande échelle, sur toute la page, lorsqu'un utilisateur initie une tâche affectant toute la page, comme le téléchargement d'un fichier ou le traitement de données dans plusieurs sections. Il peut informer les utilisateurs que toute l'application est en cours de fonctionnement, empêchant toute autre interaction jusqu'à ce que le processus soit terminé.

- **Opérations critiques du système**  
   Lors de l'exécution de tâches critiques pour le système telles que la synchronisation des données, l'application de mises à jour à l'échelle du système ou le traitement d'informations sensibles, le `BusyIndicator` donne un retour visuel clair qu'une opération majeure est en cours, permettant à l'utilisateur d'attendre qu'elle soit terminée.

- **Chargements de données asynchrones**  
   Dans les scénarios où un traitement de données asynchrones est impliqué, tels que l'appel de plusieurs API ou l'attente de calculs complexes, le composant `BusyIndicator` indique activement que le système est occupé, incitant les utilisateurs à attendre avant d'effectuer des actions supplémentaires.

## Styling {#styling}

<TableBuilder name="BusyIndicator" />
