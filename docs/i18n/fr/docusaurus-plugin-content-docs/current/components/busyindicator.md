---
title: BusyIndicator
sidebar_position: 10
_i18n_hash: a61f487d0d763856c6055898a7284011
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

Le `BusyIndicator` fournit des indices visuels pour s'assurer que les utilisateurs sont conscients des processus en cours, les empêchant d'interagir avec le système prématurément. Il couvre généralement l'ensemble de l'interface de l'application pour les opérations globales.

Alors que le composant [`Loading`](../components/loading) se concentre sur des zones ou des composants spécifiques dans l'application, le `BusyIndicator` gère les processus globaux de l'application et bloque l'interaction sur l'ensemble de l'interface. Cette différence de portée rend le composant [`Loading`](../components/loading) idéal pour des scénarios plus localisés et spécifiques à un composant, comme le chargement de données dans une section particulière d'une page. En revanche, le `BusyIndicator` est adapté aux opérations à l'échelle du système qui affectent l'ensemble de l'application, comme l'initialisation de l'application ou la synchronisation de données majeures.

## Basics {#basics}

Le `BusyIndicator` dans webforJ s'affiche comme un simple spinner, ce qui le rend facile à utiliser sans configuration. Cependant, vous pouvez le personnaliser en ajoutant un message, en ajustant le thème du spinner ou en modifiant les paramètres de visibilité. Cela vous permet de fournir plus de contexte ou de style tout en maintenant une solution fonctionnelle et prête à l'emploi.

Dans cet exemple, le `BusyIndicator` empêche toute action utilisateur sur l'ensemble de l'interface jusqu'à ce que l'opération soit terminée.

<ComponentDemo 
path='/webforj/busydemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java'
height = '300px'
/>

## Backdrops {#backdrops}

Le composant `BusyIndicator` dans webforJ vous permet d'afficher un fond pour bloquer l'interaction utilisateur pendant qu'un processus est en cours. Par défaut, le composant active le fond, mais vous avez la possibilité de le désactiver si nécessaire.

Le `BusyIndicator` affiche un fond par défaut. Vous pouvez contrôler la visibilité du fond en utilisant la méthode `setBackdropVisible()`, comme montré ci-dessous :

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Désactive le fond
busyIndicator.open();
```
:::info Désactivation du fond
Même lorsque vous désactivez le fond, le composant `BusyIndicator` continue de bloquer l'interaction utilisateur pour garantir que le processus sous-jacent se termine sans interruption. Le fond contrôle simplement le superposition visuelle, pas le comportement de blocage de l'interaction.
:::

## `Spinner` {#spinner}

Le composant `BusyIndicator` dans webforJ inclut un `Spinner` qui indique visuellement qu'une opération en arrière-plan est en cours. Vous pouvez personnaliser ce spinner avec plusieurs options, y compris sa taille, sa vitesse, sa direction, son thème et sa visibilité.

Voici un exemple de la façon dont vous pouvez personnaliser le spinner au sein d'un composant `BusyIndicator` :

<ComponentDemo 
path='/webforj/busyspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java'
height = '200px'
/>

## Use cases {#use-cases}
- **Traitement à l'échelle de la page**  
   Le `BusyIndicator` est bien adapté aux opérations plus larges à l'échelle de la page, par exemple lorsqu'un utilisateur initie une tâche qui affecte l'ensemble de la page, comme le téléchargement d'un fichier ou le traitement de données à travers plusieurs sections. Il peut informer les utilisateurs que l'ensemble de l'application est en cours d'exécution, empêchant d'autres interactions jusqu'à ce que le processus soit terminé.

- **Opérations critiques système**  
   Lors de l'exécution de tâches critiques pour le système telles que la synchronisation de données, l'application de mises à jour à l'échelle du système ou le traitement d'informations sensibles, le `BusyIndicator` offre un retour visuel clair qu'une opération majeure est en cours, permettant à l'utilisateur d'attendre jusqu'à ce qu'elle soit terminée.

- **Chargements de données asynchrones**  
   Dans des scénarios où un traitement de données asynchrone est impliqué, comme lors de l'appel de plusieurs APIs ou en attendant des calculs complexes, le composant `BusyIndicator` indique activement que le système est occupé, incitant les utilisateurs à attendre avant d'effectuer d'autres actions.

## Styling {#styling}

<TableBuilder name="BusyIndicator" />
