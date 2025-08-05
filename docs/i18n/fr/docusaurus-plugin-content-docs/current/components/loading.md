---
title: Loading
sidebar_position: 65
_i18n_hash: fd3e1e31d1a614494358f9d67a9d3cd8
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

Le composant `Loading` dans webforJ affiche une superposition qui signale le traitement d'une opération, empêchant temporairement l'interaction de l'utilisateur jusqu'à ce que la tâche soit terminée. Cette fonctionnalité améliore l'expérience utilisateur, en particulier dans les situations où des tâches comme le chargement de données, les calculs ou les processus en arrière-plan peuvent prendre un certain temps. Pour les processus globaux, à l'échelle de l'application, envisagez d'utiliser le composant [`BusyIndicator`](../components/busyindicator), qui bloque l'interaction dans toute l'interface.

## Basics {#basics}

La façon la plus simple de créer un composant `Loading` est de l'initialiser sans aucun paramètre supplémentaire. Par défaut, cela affiche un spinner de base sur son contenu parent. Cependant, vous pouvez également fournir un message pour plus de contexte.

Voici un exemple de création d'un composant `Loading` avec un message :

<ComponentDemo 
path='/webforj/loadingdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java'
cssURL='/css/loadingstyles/loadingdemo.css'
height = '300px'
/>

## Scoping {#scoping}

Le composant `Loading` dans webforJ peut se limiter à un conteneur parent spécifique, tel qu'une `Div`, garantissant qu'il bloque uniquement l'interaction de l'utilisateur dans cet élément. Par défaut, le composant `Loading` est relatif à son parent, ce qui signifie qu'il superpose le composant parent plutôt que l'ensemble de l'application.

Pour limiter le composant `Loading` à son parent, il suffit d'ajouter le composant `Loading` au conteneur parent. Par exemple, si vous l'ajoutez à une `Div`, la superposition de chargement s'applique uniquement à cette `Div` :

```java
Div parentDiv = new Div();  
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Loading ne bloquera l'interaction que dans le parentDiv
```

## Backdrop {#backdrop}

Le composant `Loading` dans webforJ vous permet d'afficher un arrière-plan pour bloquer l'interaction de l'utilisateur pendant qu'un processus est en cours. Par défaut, le composant active l'arrière-plan, mais vous avez la possibilité de le désactiver si nécessaire.

Pour le composant `Loading`, l'arrière-plan est visible par défaut. Vous pouvez explicitement activer ou désactiver en utilisant la méthode `setBackdropVisible()` :

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // Désactive l'arrière-plan
loading.open();
```
:::info Arrière-plan désactivé
Même lorsque vous désactivez l'arrière-plan, le composant `Loading` continue de bloquer l'interaction de l'utilisateur pour garantir que le processus sous-jacent se termine sans interruption. L'arrière-plan contrôle simplement la superposition visuelle, pas le comportement de blocage de l'interaction.
:::

## `Spinner` {#spinner}

Le composant `Loading` dans webforJ comprend un `Spinner` qui indique visuellement qu'une opération en arrière-plan est en cours. Vous pouvez personnaliser ce spinner avec plusieurs options, y compris sa taille, sa vitesse, sa direction, son thème et sa visibilité.

Voici un exemple de la façon dont vous pouvez personnaliser le spinner dans un composant `Loading` :

<ComponentDemo 
path='/webforj/loadingspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/loading/LoadingSpinnerDemoView.java'
cssURL='/css/loadingstyles/loadingspinnerdemo.css'
height = '300px'
/>

## Use cases {#use-cases}
- **Récupération de données**  
   Lors de la récupération de données depuis un serveur ou une API, le composant `Loading` superpose une section spécifique de l'interface utilisateur, comme une carte ou un formulaire, pour informer les utilisateurs que le système travaille en arrière-plan. C'est idéal lorsque vous voulez montrer le progrès sur une seule partie de l'écran sans bloquer l'ensemble de l'interface.

- **Chargement de contenu dans des cartes/sections**  
   Le composant `Loading` peut être limité à des zones spécifiques d'une page, comme des cartes ou des conteneurs individuels. Cela est utile lorsque vous souhaitez indiquer qu'une section particulière de l'interface utilisateur est toujours en cours de chargement tout en permettant aux utilisateurs d'interagir avec d'autres parties de la page.

- **Soumissions de formulaires complexes**  
   Pour des soumissions de formulaire plus longues où la validation ou le traitement prend du temps, le composant `Loading` fournit un retour visuel aux utilisateurs, leur assurant que leur saisie est en cours de traitement.

## Styling {#styling}

<TableBuilder name="Loading" />
