---
title: Loading
sidebar_position: 65
_i18n_hash: 9bdb4d5c978b4070d3628566e5105088
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="loading" location="com/webforj/component/loading/Loading" top='true'/>

Le composant `Loading` dans webforJ affiche un overlay signalant le traitement d'une opération, empêchant temporairement l'interaction de l'utilisateur jusqu'à ce que la tâche soit terminée. Cette fonctionnalité améliore l'expérience utilisateur, en particulier dans les situations où des tâches comme le chargement de données, des calculs ou des processus en arrière-plan peuvent prendre du temps. Pour les processus globaux et à l'échelle de l'application, envisagez d'utiliser le composant [`BusyIndicator`](../components/busyindicator), qui bloque l'interaction sur toute l'interface.

## Basics {#basics}

La façon la plus simple de créer un composant `Loading` est de l'initialiser sans aucun paramètre supplémentaire. Par défaut, cela affiche un spinner basique au-dessus de son contenu parent. Cependant, vous pouvez également fournir un message pour plus de contexte.

Voici un exemple de création d'un composant `Loading` avec un message :

<ComponentDemo 
path='/webforj/loadingdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/loading/LoadingDemoView.java'
cssURL='/css/loadingstyles/loadingdemo.css'
height = '300px'
/>

## Scoping {#scoping}

Le composant `Loading` dans webforJ peut se limiter à un conteneur parent spécifique, comme un `Div`, assurant qu'il bloque uniquement l'interaction utilisateur à l'intérieur de cet élément. Par défaut, le composant `Loading` est relatif à son parent, ce qui signifie qu'il superpose le composant parent plutôt que l'application entière.

Pour limiter le composant `Loading` à son parent, il suffit d'ajouter le composant `Loading` au conteneur parent. Par exemple, si vous l'ajoutez à un `Div`, l'overlay de chargement s'applique uniquement à ce `Div` :

```java
Div parentDiv = new Div();  
parentDiv.setStyle("position", "relative");
Loading loading = new Loading();
parentDiv.add(loading);
loading.open();  // Loading ne bloquera que l'interaction dans le parentDiv
```

## Backdrop {#backdrop}

Le composant `Loading` dans webforJ vous permet d'afficher un arrière-plan pour bloquer l'interaction utilisateur pendant qu'un processus est en cours. Par défaut, le composant active l'arrière-plan, mais vous avez la possibilité de le désactiver si nécessaire.

Pour le composant `Loading`, l'arrière-plan est visible par défaut. Vous pouvez explicitement l'activer ou le désactiver en utilisant la méthode `setBackdropVisible()` :

```java
Loading loading = new Loading();
loading.setBackdropVisible(false);  // Désactive l'arrière-plan
loading.open();
```
:::info Arrière-plan désactivé
Même lorsque vous désactivez l'arrière-plan, le composant `Loading` continue de bloquer l'interaction utilisateur pour s'assurer que le processus sous-jacent se termine sans interruption. L'arrière-plan contrôle simplement l'overlay visuel, pas le comportement de blocage de l'interaction.
:::

## `Spinner` {#spinner}

Le composant `Loading` dans webforJ comprend un `Spinner` qui indique visuellement qu'une opération en arrière-plan est en cours. Vous pouvez personnaliser ce spinner avec plusieurs options, y compris sa taille, sa vitesse, sa direction, son thème et sa visibilité.

Voici un exemple de la façon dont vous pouvez personnaliser le spinner au sein d'un composant `Loading` :

<ComponentDemo 
path='/webforj/loadingspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/loading/LoadingSpinnerDemoView.java'
cssURL='/css/loadingstyles/loadingspinnerdemo.css'
height = '300px'
/>

## Use cases {#use-cases}
- **Récupération de données**  
   Lorsque vous récupérez des données d'un serveur ou d'une API, le composant `Loading` superpose une section spécifique de l'UI, comme une carte ou un formulaire, pour informer les utilisateurs que le système travaille en arrière-plan. Cela est idéal lorsque vous souhaitez montrer les progrès d'une seule partie de l'écran sans bloquer toute l'interface.

- **Chargement de contenu dans des cartes/sections**  
   Le composant `Loading` peut être limité à des zones spécifiques d'une page, telles que des cartes ou des conteneurs individuels. Cela est utile lorsque vous souhaitez indiquer qu'une section particulière de l'UI est encore en cours de chargement tout en permettant aux utilisateurs d'interagir avec d'autres parties de la page.

- **Soumissions de formulaires complexes**  
   Pour des soumissions de formulaires plus longues où la validation ou le traitement prend du temps, le composant `Loading` fournit un retour visuel aux utilisateurs, les rassurant que leur entrée est activement en cours de traitement.

## Styling {#styling}

<TableBuilder name="Loading" />
