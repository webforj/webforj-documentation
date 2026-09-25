---
title: Loading
sidebar_position: 65
description: >-
  Overlay a parent container with the Loading component to block interaction
  during async tasks, with backdrop and spinner customization.
_i18n_hash: 8106f15ba96904324822afd0169ec09b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

Le composant `Loading` affiche un overlay sur un composant ou une zone spécifique, signalant qu'une opération est en cours et bloquant temporairement l'interaction. Il est particulièrement utile pour des tâches comme le chargement de données, des calculs ou des processus en arrière-plan. Pour les processus globaux à l'échelle de l'application, le composant [`BusyIndicator`](../components/busyindicator) couvre l'ensemble de l'interface.

<!-- INTRO_END -->

L'initialisation d'un composant `Loading` sans paramètres supplémentaires affiche un spinner au-dessus de son contenu parent. Transmettez un message, comme dans l'exemple ci-dessous, lorsque le processus nécessite plus de contexte.

<ComponentDemo
path='/webforj/loadingdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java',
  'src/main/frontend/css/loadingstyles/loadingdemo.css',
]}
height='300px'
/>

## Scoping {#scoping}

Le composant `Loading` dans webforJ peut se restreindre à un conteneur parent spécifique, tel qu'un `Div`, garantissant qu'il bloque uniquement l'interaction utilisateur à l'intérieur de cet élément. Par défaut, le composant `Loading` est relatif à son parent, ce qui signifie qu'il superpose le composant parent plutôt que l'ensemble de l'application.

Pour limiter le composant `Loading` à son parent, il vous suffit d'ajouter le composant `Loading` au conteneur parent. Par exemple, si vous l'ajoutez à un `Div`, l'overlay de chargement ne s'applique qu'à ce `Div` :

```java
Div parentDiv = new Div();
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Le chargement bloquera uniquement l'interaction à l'intérieur du parentDiv
```

## Backdrop {#backdrop}

Le composant `Loading` dans webforJ vous permet d'afficher un arrière-plan pour bloquer l'interaction utilisateur pendant qu'un processus est en cours. Par défaut, le composant active l'arrière-plan, mais vous avez la possibilité de l'éteindre si nécessaire.

Pour le composant `Loading`, l'arrière-plan est visible par défaut. Vous pouvez l'activer explicitement ou le désactiver en utilisant la méthode `setBackdropVisible()` :

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // Désactive l'arrière-plan
loading.open();
```
:::info Arrière-plan désactivé
Même lorsque vous désactivez l'arrière-plan, le composant `Loading` continue de bloquer l'interaction utilisateur pour garantir que le processus sous-jacent se termine sans interruption. L'arrière-plan contrôle simplement l'affichage visuel, pas le comportement de blocage de l'interaction.
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
   Lors de la récupération de données à partir d'un serveur ou d'une API, le composant `Loading` superpose une section spécifique de l'interface utilisateur, comme une carte ou un formulaire, pour informer les utilisateurs que le système travaille en arrière-plan. Cela est idéal lorsque vous souhaitez montrer l'avancement d'une seule partie de l'écran sans bloquer l'ensemble de l'interface.

- **Chargement de contenu dans des cartes/sections**
   Le composant `Loading` peut être restreint à des zones spécifiques d'une page, telles que des cartes ou des conteneurs individuels. Cela est utile lorsque vous souhaitez indiquer qu'une section particulière de l'interface utilisateur est encore en cours de chargement tout en permettant aux utilisateurs d'interagir avec d'autres parties de la page.

- **Soumissions de formulaires complexes**
   Pour les soumissions de formulaires plus longues où la validation ou le traitement prend du temps, le composant `Loading` fournit un retour visuel aux utilisateurs, les rassurant sur le fait que leurs saisies sont en cours de traitement.

## Stylisation {#styling}

<TableBuilder name="Loading" />
