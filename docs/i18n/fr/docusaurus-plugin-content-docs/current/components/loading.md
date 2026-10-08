---
title: Loading
sidebar_position: 65
description: >-
  Overlay a parent container with the Loading component to block interaction
  during async tasks, with backdrop and spinner customization.
_i18n_hash: 6e4493f64eb29033ed8a5d594accdb33
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

Le composant `Loading` affiche un superposition sur un composant ou une zone spécifique, signalant qu'une opération est en cours et bloquant temporairement l'interaction. Il convient bien pour des tâches comme le chargement de données, les calculs ou les processus en arrière-plan. Pour les processus globaux à l'échelle de l'application, le composant [`BusyIndicator`](../components/busyindicator) couvre l'ensemble de l'interface.

<!-- INTRO_END -->

L'initialisation d'un composant `Loading` sans aucun paramètre supplémentaire affiche un chargeur au-dessus de son contenu parent. Passez un message, comme dans l'exemple ci-dessous, lorsque le processus nécessite plus de contexte.

<ComponentDemo
path='/webforj/loadingdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java',
  'src/main/frontend/css/loadingstyles/loadingdemo.css',
]}
height='300px'
/>

## Scoping {#scoping}

Le composant `Loading` dans webforJ peut se limiter à un conteneur parent spécifique, tel qu'un `Div`, garantissant qu'il bloque uniquement l'interaction utilisateur à l'intérieur de cet élément. Par défaut, le composant `Loading` est relatif à son parent, ce qui signifie qu'il superpose le composant parent plutôt que l'ensemble de l'application.

Pour limiter le composant `Loading` à son parent, il suffit d'ajouter le composant `Loading` au conteneur parent. Par exemple, si vous l'ajoutez à un `Div`, la superposition de chargement ne s'applique qu'à ce `Div` :

```java
Div parentDiv = new Div();
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Le chargement n'interrompra l'interaction que dans parentDiv
```

## Backdrop {#backdrop}

Le composant `Loading` dans webforJ vous permet d'afficher un fond pour bloquer l'interaction utilisateur pendant qu'un processus est en cours. Par défaut, le composant active le fond, mais vous avez la possibilité de le désactiver si nécessaire.

Pour le composant `Loading`, le fond est visible par défaut. Vous pouvez explicitement l'activer ou le désactiver à l'aide de la méthode `setBackdropVisible()` :

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // Désactive le fond
loading.open();
```
:::info Fond désactivé
Même lorsque vous désactivez le fond, le composant `Loading` continue de bloquer l'interaction utilisateur pour garantir que le processus sous-jacent s'achève sans interruption. Le fond contrôle simplement la superposition visuelle, et non le blocage de l'interaction.
:::

## `Spinner` {#spinner}

Le composant `Loading` dans webforJ inclut un `Spinner` qui indique visuellement qu'une opération de fond est en cours. Vous pouvez personnaliser ce spinner avec plusieurs options, y compris sa taille, sa vitesse, sa direction, son thème et sa visibilité.

Voici un exemple de la façon dont vous pouvez personnaliser le spinner à l'intérieur d'un composant `Loading` :

<ComponentDemo
path='/webforj/loadingspinnerdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingSpinnerDemoView.java',
]}
height='300px'
/>

## Cas d'utilisation {#use-cases}
- **Récupération de données**
   Lors de la récupération de données depuis un serveur ou une API, le composant `Loading` superpose une section spécifique de l'interface utilisateur, telle qu'une carte ou un formulaire, pour informer les utilisateurs que le système travaille en arrière-plan. Ceci est idéal lorsque vous souhaitez montrer la progression d'une seule partie de l'écran sans bloquer l'ensemble de l'interface.

- **Chargement de contenu dans des cartes/sections**
   Le composant `Loading` peut être limité à des zones spécifiques d'une page, telles que des cartes ou des conteneurs individuels. Cela est utile lorsque vous souhaitez indiquer qu'une section particulière de l'interface utilisateur est encore en cours de chargement tout en permettant aux utilisateurs d'interagir avec d'autres parties de la page.

- **Soumissions de formulaires complexes**
   Pour des soumissions de formulaire plus longues où la validation ou le traitement prend du temps, le composant `Loading` fournit un retour visuel aux utilisateurs, les rassurant sur le fait que leur saisie est en cours de traitement actif.

## Style {#styling}

<TableBuilder name="Loading" />
