---
title: BusyIndicator
sidebar_position: 10
description: >-
  Block the entire interface during long-running operations using the
  BusyIndicator overlay with a customizable spinner, message, and backdrop.
_i18n_hash: 663fb0d605695631bad3753aadf178e5
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

Le `BusyIndicator` est un superposition plein écran qui signale un processus en cours et bloque l'interaction de l'utilisateur jusqu'à ce qu'il soit terminé. Il couvre l'ensemble de l'interface pendant des opérations comme l'initialisation ou la synchronisation des données. Alors que le composant [`Loading`](../components/loading) se concentre sur des zones spécifiques de l'interface, le `BusyIndicator` s'applique globalement.

Le `BusyIndicator` s'affiche sous forme de spinner sans configuration requise. Ajoutez un message, changez le thème du spinner ou ajustez les paramètres de visibilité lorsqu'un processus nécessite plus de contexte.

<!-- INTRO_END -->

<ComponentDemo
path='/webforj/busydemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java']}
height='300px'
/>

## Toiles de fond {#backdrops}

Le composant `BusyIndicator` dans webforJ vous permet d'afficher une toile de fond pour bloquer l'interaction de l'utilisateur pendant qu'un processus est en cours. Par défaut, le composant active la toile de fond, mais vous avez la possibilité de la désactiver si nécessaire.

Le `BusyIndicator` montre une toile de fond par défaut. Vous pouvez contrôler la visibilité de la toile de fond en utilisant la méthode `setBackdropVisible()`, comme indiqué ci-dessous :

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Désactive la toile de fond
busyIndicator.open();
```
:::info Désactivation de la toile de fond
Même lorsque vous désactivez la toile de fond, le composant `BusyIndicator` continue de bloquer l'interaction de l'utilisateur pour garantir que le processus sous-jacent se termine sans interruption. La toile de fond contrôle simplement le superposition visuel, pas le comportement de blocage de l'interaction.
:::

## `Spinner` {#spinner}

Le composant `BusyIndicator` dans webforJ inclut un `Spinner` qui indique visuellement qu'une opération en arrière-plan est en cours. Vous pouvez personnaliser ce spinner avec plusieurs options, y compris sa taille, sa vitesse, sa direction, son thème et sa visibilité.

Voici un exemple de la façon dont vous pouvez personnaliser le spinner au sein d'un composant `BusyIndicator` :

<ComponentDemo
path='/webforj/busyspinnerdemo'
files={['src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java']}
height='200px'
/>

## Cas d'utilisation {#use-cases}
- **Traitement sur l'ensemble de la page**
   Le `BusyIndicator` est bien adapté pour des opérations plus larges, sur l'ensemble de la page, telles que lorsque l'utilisateur initie une tâche qui affecte toute la page, comme le téléchargement d'un fichier ou le traitement de données sur plusieurs sections. Il peut informer les utilisateurs que l'ensemble de l'application est en cours d'exécution, empêchant toute interaction supplémentaire jusqu'à ce que le processus soit terminé.

- **Opérations système critiques**
   Lors de l'exécution de tâches critiques pour le système telles que la synchronisation des données, l'application de mises à jour à l'échelle du système ou le traitement d'informations sensibles, le `BusyIndicator` fournit un retour visuel clair qu'une opération majeure est en cours, permettant à l'utilisateur d'attendre jusqu'à ce qu'elle soit terminée.

- **Chargements de données asynchrones**
   Dans les scénarios où le traitement des données asynchrones est impliqué, par exemple lors de l'appel de plusieurs API ou en attendant des calculs complexes, le composant `BusyIndicator` indique activement que le système est occupé, incitant les utilisateurs à attendre avant d'effectuer d'autres actions.

## Style {#styling}

<TableBuilder name="BusyIndicator" />
