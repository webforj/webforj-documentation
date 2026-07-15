---
title: Loading
sidebar_position: 65
description: >-
  Overlay a parent container with the Loading component to block interaction
  during async tasks, with backdrop and spinner customization.
_i18n_hash: e17c9249d41752ed1f4b98d18028371a
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

Le composant `Loading` affiche une superposition sur un composant ou une zone spécifique, signalant qu'une opération est en cours et bloquant temporairement l'interaction. Il fonctionne bien pour des tâches telles que le chargement de données, les calculs ou les processus en arrière-plan. Pour des processus globaux au niveau de l'application, le composant [`BusyIndicator`](../components/busyindicator) couvre plutôt toute l'interface.

<!-- INTRO_END -->

## Bases {#basics}

La manière la plus simple de créer un composant `Loading` est de l'initialiser sans paramètres supplémentaires. Par défaut, cela affiche un indicateur de chargement de base sur son contenu parent. Cependant, vous pouvez également fournir un message pour plus de contexte.

Voici un exemple de création d'un composant `Loading` avec un message :

<ComponentDemo
path='/webforj/loadingdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java',
  'src/main/frontend/css/loadingstyles/loadingdemo.css',
]}
height='300px'
/>

## Portée {#scoping}

Le composant `Loading` dans webforJ peut se limiter à un conteneur parent spécifique, tel qu'un `Div`, garantissant qu'il ne bloque l'interaction utilisateur que dans cet élément. Par défaut, le composant `Loading` est relatif à son parent, ce qui signifie qu'il superpose le composant parent plutôt que l'ensemble de l'application.

Pour limiter le composant `Loading` à son parent, il suffit d'ajouter le composant `Loading` au conteneur parent. Par exemple, si vous l'ajoutez à un `Div`, la superposition de chargement ne s'applique qu'à ce `Div` :

```java
Div parentDiv = new Div();
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Le chargement bloquera uniquement l'interaction au sein du parentDiv
```

## Arrière-plan {#backdrop}

Le composant `Loading` dans webforJ vous permet d'afficher un arrière-plan pour bloquer l'interaction utilisateur pendant qu'un processus est en cours. Par défaut, le composant active l'arrière-plan, mais vous avez la possibilité de le désactiver si besoin.

Pour le composant `Loading`, l'arrière-plan est visible par défaut. Vous pouvez l'activer ou le désactiver explicitement à l'aide de la méthode `setBackdropVisible()` :

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // Désactive l'arrière-plan
loading.open();
```
:::info Arrière-plan désactivé
Même lorsque vous désactivez l'arrière-plan, le composant `Loading` continue de bloquer l'interaction utilisateur pour garantir que le processus sous-jacent se termine sans interruption. L'arrière-plan contrôle simplement la superposition visuelle, pas le comportement de blocage de l'interaction.
:::

## `Spinner` {#spinner}

Le composant `Loading` dans webforJ comprend un `Spinner` qui indique visuellement qu'une opération en arrière-plan est en cours. Vous pouvez personnaliser ce spinner avec plusieurs options, y compris sa taille, sa vitesse, sa direction, son thème et sa visibilité.

Voici un exemple de la manière dont vous pouvez personnaliser le spinner dans un composant `Loading` :

<ComponentDemo
path='/webforj/loadingspinnerdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingSpinnerDemoView.java',
  'src/main/frontend/css/loadingstyles/loadingspinnerdemo.css',
]}
height='300px'
/>

## Cas d'utilisation {#use-cases}
- **Récupération de données**
   Lors de la récupération de données depuis un serveur ou une API, le composant `Loading` superpose une section spécifique de l'interface utilisateur, telle qu'une carte ou un formulaire, pour informer les utilisateurs que le système travaille en arrière-plan. Cela est idéal lorsque vous souhaitez montrer l'avancement d'une seule partie de l'écran sans bloquer l'ensemble de l'interface.

- **Chargement de contenu dans des cartes/sections**
   Le composant `Loading` peut être limité à des zones spécifiques d'une page, telles que des cartes individuelles ou des conteneurs. Cela est utile lorsque vous souhaitez indiquer qu'un section particulière de l'interface utilisateur est encore en cours de chargement tout en permettant aux utilisateurs d'interagir avec d'autres parties de la page.

- **Soumissions de formulaires complexes**
   Pour les soumissions de formulaires plus longues où la validation ou le traitement prend du temps, le composant `Loading` fournit un retour visuel aux utilisateurs, les rassurant que leurs données sont en cours de traitement.

## Style {#styling}

<TableBuilder name="Loading" />
