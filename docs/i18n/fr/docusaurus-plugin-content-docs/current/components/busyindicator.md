---
title: BusyIndicator
sidebar_position: 10
description: >-
  Block the entire interface during long-running operations using the
  BusyIndicator overlay with a customizable spinner, message, and backdrop.
_i18n_hash: 30ca15f8b8170f6d7da6a786ddafea7f
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

Le `BusyIndicator` est un superposition plein écran qui signale un processus en cours et bloque l'interaction de l'utilisateur jusqu'à ce qu'il soit terminé. Il couvre l'intégralité de l'interface lors des opérations telles que l'initialisation ou la synchronisation des données. Alors que le composant [`Loading`](../components/loading) se concentre sur des zones spécifiques de l'interface, le `BusyIndicator` s'applique globalement.

<!-- INTRO_END -->

## Basics {#basics}

Le `BusyIndicator` dans webforJ apparaît comme un simple spinner, ce qui le rend facile à utiliser sans configuration. Cependant, vous pouvez le personnaliser en ajoutant un message, en ajustant le thème du spinner ou en modifiant les paramètres de visibilité. Cela vous permet de fournir plus de contexte ou de style tout en maintenant une solution fonctionnelle, prête à l'emploi.

Dans cet exemple, le `BusyIndicator` empêche toute action utilisateur sur l'ensemble de l'interface jusqu'à ce que l'opération soit terminée.

<ComponentDemo
path='/webforj/busydemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java']}
height='300px'
/>

## Backdrops {#backdrops}

Le composant `BusyIndicator` dans webforJ vous permet d'afficher un arrière-plan pour bloquer l'interaction utilisateur pendant qu'un processus est en cours. Par défaut, le composant active l'arrière-plan, mais vous avez la possibilité de le désactiver si nécessaire.

Le `BusyIndicator` montre un arrière-plan par défaut. Vous pouvez contrôler la visibilité de l'arrière-plan en utilisant la méthode `setBackdropVisible()`, comme montré ci-dessous :

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Désactive l'arrière-plan
busyIndicator.open();
```
:::info Désactiver l'Arrière-plan
Même lorsque vous désactivez l'arrière-plan, le composant `BusyIndicator` continue de bloquer l'interaction utilisateur pour garantir que le processus sous-jacent se termine sans interruption. L'arrière-plan contrôle simplement la superposition visuelle, pas le comportement de blocage de l'interaction.
:::

## `Spinner` {#spinner}

Le composant `BusyIndicator` dans webforJ comprend un `Spinner` qui indique visuellement qu'une opération en arrière-plan est en cours. Vous pouvez personnaliser ce spinner avec plusieurs options, y compris sa taille, sa vitesse, sa direction, son thème et sa visibilité.

Voici un exemple de la façon dont vous pouvez personnaliser le spinner au sein d'un composant `BusyIndicator` :

<ComponentDemo
path='/webforj/busyspinnerdemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java']}
height='200px'
/>

## Use cases {#use-cases}
- **Traitement sur l'ensemble de la page**
   Le `BusyIndicator` est particulièrement adapté aux opérations plus importantes, s'étendant sur toute la page, telles que lorsqu'un utilisateur initie une tâche qui affecte l'ensemble de la page, comme le téléchargement d'un fichier ou le traitement de données dans plusieurs sections. Cela peut informer les utilisateurs que l'ensemble de l'application est en cours d'exécution, empêchant toute interaction supplémentaire jusqu'à ce que le processus soit terminé.

- **Opérations système critiques**
   Lorsqu'il s'agit de tâches critiques pour le système, telles que la synchronisation des données, l'application de mises à jour à l'échelle du système ou le traitement d'informations sensibles, le `BusyIndicator` fournit un retour visuel clair qu'une opération majeure est en cours, permettant à l'utilisateur d'attendre qu'elle soit terminée.

- **Chargements de données asynchrones**
   Dans les scénarios où un traitement de données asynchrone est impliqué, comme lors de l'appel de plusieurs API ou de l'attente de calculs complexes, le composant `BusyIndicator` indique activement que le système est occupé, incitant les utilisateurs à attendre avant d'effectuer des actions supplémentaires.

## Styling {#styling}

<TableBuilder name="BusyIndicator" />
