---
title: Loading
sidebar_position: 65
_i18n_hash: c81b8d0ced3e4097693a186a05f18dbf
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

Le composant `Loading` affiche une superposition sur un composant ou une zone spécifique, signalant qu'une opération est en cours et bloquant temporairement l'interaction. Il est idéal pour des tâches comme le chargement de données, des calculs ou des processus en arrière-plan. Pour les processus globaux au niveau de l'application, le composant [`BusyIndicator`](../components/busyindicator) couvre toute l'interface.

<!-- INTRO_END -->

## Principes de base {#basics}

La manière la plus simple de créer un composant `Loading` est de l'initialiser sans aucun paramètre supplémentaire. Par défaut, cela affiche un simple spinner sur le contenu parent. Cependant, vous pouvez également fournir un message pour plus de contexte.

Voici un exemple de création d'un composant `Loading` avec un message :

<ComponentDemo
path='/webforj/loadingdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java',
  'src/main/resources/static/css/loadingstyles/loadingdemo.css',
]}
height='300px'
/>

## Portée {#scoping}

Le composant `Loading` dans webforJ peut se limiter à un conteneur parent spécifique, comme un `Div`, garantissant qu'il ne bloque l'interaction des utilisateurs que dans cet élément. Par défaut, le composant `Loading` est relatif à son parent, ce qui signifie qu'il superpose le composant parent plutôt que l'ensemble de l'application.

Pour limiter le composant `Loading` à son parent, il suffit d'ajouter le composant `Loading` au conteneur parent. Par exemple, si vous l'ajoutez à un `Div`, la superposition de chargement s'applique uniquement à ce `Div` :

```java
Div parentDiv = new Div();  
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Le chargement ne bloquera que l'interaction dans le parentDiv
```

## Fond d'écran {#backdrop}

Le composant `Loading` dans webforJ vous permet d'afficher un fond d'écran pour bloquer l'interaction des utilisateurs pendant qu'un processus est en cours. Par défaut, le composant active le fond d'écran, mais vous avez la possibilité de le désactiver si nécessaire.

Pour le composant `Loading`, le fond d'écran est visible par défaut. Vous pouvez explicitement activer ou désactiver en utilisant la méthode `setBackdropVisible()` :

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // Désactive le fond d'écran
loading.open();
```
:::info Fond d'écran désactivé
Même lorsque vous désactivez le fond d'écran, le composant `Loading` continue de bloquer l'interaction des utilisateurs pour garantir que le processus sous-jacent se termine sans interruption. Le fond d'écran contrôle simplement la superposition visuelle, et non le blocage de l'interaction.
:::

## `Spinner` {#spinner}

Le composant `Loading` dans webforJ comprend un `Spinner` qui indique visuellement qu'une opération en arrière-plan est en cours. Vous pouvez personnaliser ce spinner avec plusieurs options, notamment sa taille, sa vitesse, sa direction, son thème et sa visibilité.

Voici un exemple de la façon dont vous pouvez personnaliser le spinner dans un composant `Loading` :

<ComponentDemo
path='/webforj/loadingspinnerdemo'
files={[
  'src/main/java/com/webforj/samples/views/loading/LoadingSpinnerDemoView.java',
  'src/main/resources/static/css/loadingstyles/loadingspinnerdemo.css',
]}
height='300px'
/>

## Cas d'utilisation {#use-cases}
- **Récupération de données**  
   Lors de la récupération de données à partir d'un serveur ou d'une API, le composant `Loading` superpose une section spécifique de l'interface utilisateur, telle qu'une carte ou un formulaire, pour informer les utilisateurs que le système travaille en arrière-plan. Cela est idéal lorsque vous souhaitez afficher une progression sur une partie de l'écran sans bloquer l'ensemble de l'interface.

- **Chargement de contenu dans des cartes/sections**  
   Le composant `Loading` peut être limité à des zones spécifiques d'une page, telles que des cartes ou des conteneurs individuels. Cela est utile lorsque vous souhaitez indiquer qu'une section particulière de l'interface utilisateur est encore en cours de chargement tout en permettant aux utilisateurs d'interagir avec d'autres parties de la page.

- **Soumissions de formulaires complexes**  
   Pour les soumissions de formulaires plus longues où la validation ou le traitement prend du temps, le composant `Loading` fournit un retour visuel aux utilisateurs, leur assurant que leur saisie est en cours de traitement.

## Style {#styling}

<TableBuilder name="Loading" />
