---
title: BusyIndicator
sidebar_position: 10
description: >-
  Block the entire interface during long-running operations using the
  BusyIndicator overlay with a customizable spinner, message, and backdrop.
_i18n_hash: a6ab827c0422e8f10369500acc310007
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

Le `BusyIndicator` est superposé sur tout l'écran pour signaler qu'un processus est en cours et bloque l'interaction de l'utilisateur jusqu'à sa fin. Il couvre toute l'interface pendant des opérations comme l'initialisation ou la synchronisation des données. Alors que le composant [`Loading`](../components/loading) se concentre sur des zones spécifiques de l'interface, le `BusyIndicator` s'applique globalement.

Le `BusyIndicator` s'affiche sous forme de spinner sans configuration requise. Ajoutez un message, changez le thème du spinner, ou ajustez les paramètres de visibilité lorsque le processus nécessite plus de contexte.

<!-- INTRO_END -->

<ComponentDemo
path='/webforj/busydemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java']}
height='300px'
/>

## Arrière-plans {#backdrops}

Le composant `BusyIndicator` de webforJ vous permet d'afficher un arrière-plan pour bloquer l'interaction de l'utilisateur pendant qu'un processus est en cours. Par défaut, le composant active l'arrière-plan, mais vous avez la possibilité de le désactiver si nécessaire.

Le `BusyIndicator` affiche un arrière-plan par défaut. Vous pouvez contrôler la visibilité de l'arrière-plan en utilisant la méthode `setBackdropVisible()`, comme indiqué ci-dessous :

```java
BusyIndicator busyIndicator = App.getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Désactive l'arrière-plan
busyIndicator.open();
```
:::info Désactivation de l'Arrière-plan
Même lorsque vous désactivez l'arrière-plan, le composant `BusyIndicator` continue de bloquer l'interaction de l'utilisateur pour garantir que le processus sous-jacent se termine sans interruption. L'arrière-plan contrôle simplement la superposition visuelle, pas le comportement de blocage de l'interaction.
:::

## `Spinner` {#spinner}

Le composant `BusyIndicator` de webforJ inclut un `Spinner` qui indique visuellement qu'une opération en arrière-plan est en cours. Vous pouvez personnaliser ce spinner avec plusieurs options, y compris sa taille, sa vitesse, sa direction, son thème et sa visibilité.

Voici un exemple de la façon de personnaliser le spinner au sein d'un composant `BusyIndicator` :

<ComponentDemo
path='/webforj/busyspinnerdemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java']}
height='200px'
/>

## Cas d'utilisation {#use-cases}
- **Traitement à l'Échelle de la Page**
   Le `BusyIndicator` est bien adapté aux opérations plus larges, à l'échelle de la page, comme lorsque l'utilisateur initie une tâche qui affecte l'ensemble de la page, comme le téléchargement d'un fichier ou le traitement de données à travers plusieurs sections. Il peut informer les utilisateurs que l'ensemble de l'application est en cours de travail, empêchant toute interaction supplémentaire jusqu'à ce que le processus soit terminé.

- **Opérations Systémiques Critiques**
   Lors de l'exécution de tâches critiques pour le système, telles que la synchronisation des données, l'application de mises à jour sur l'ensemble du système ou le traitement d'informations sensibles, le `BusyIndicator` donne un retour visuel clair qu'une opération majeure est en cours, permettant à l'utilisateur d'attendre jusqu'à ce qu'elle soit terminée.

- **Chargements de Données Asynchrones**
   Dans des scénarios où un traitement de données asynchrone est impliqué, comme lors de l'appel de plusieurs API ou de l'attente de calculs complexes, le composant `BusyIndicator` indique activement que le système est occupé, incitant les utilisateurs à attendre avant de réaliser des actions supplémentaires.

## Style {#styling}

<TableBuilder name="BusyIndicator" />
