---
title: BusyIndicator
sidebar_position: 10
_i18n_hash: e8d5c8ba0e26f0cc8fb98a640069347f
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-loading" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

Le `BusyIndicator` est un superposé plein écran qui signale un processus en cours et bloque l'interaction utilisateur jusqu'à ce qu'il se termine. Il couvre l'ensemble de l'interface pendant des opérations telles que l'initialisation ou les synchronisations de données. Alors que le composant [`Loading`](../components/loading) se concentre sur des zones spécifiques au sein de l'interface, le `BusyIndicator` s'applique globalement.

<!-- INTRO_END -->

## Bases {#basics}

Le `BusyIndicator` dans webforJ s'affiche comme un simple spinner, ce qui le rend facile à utiliser sans configuration. Cependant, vous pouvez le personnaliser en ajoutant un message, en ajustant le thème du spinner ou en modifiant les paramètres de visibilité. Cela vous permet de fournir plus de contexte ou de style tout en maintenant une solution fonctionnelle prête à l'emploi.

Dans cet exemple, le `BusyIndicator` empêche toute action utilisateur sur l'ensemble de l'interface jusqu'à ce que l'opération soit terminée.

<ComponentDemo 
path='/webforj/busydemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java'
height = '300px'
/>

## Fond d'écran {#backdrops}

Le composant `BusyIndicator` dans webforJ vous permet d'afficher un fond d'écran pour bloquer l'interaction utilisateur pendant qu'un processus est en cours. Par défaut, le composant active le fond d'écran, mais vous avez la possibilité de le désactiver si nécessaire.

Le `BusyIndicator` montre un fond d'écran par défaut. Vous pouvez contrôler la visibilité du fond d'écran à l'aide de la méthode `setBackdropVisible()`, comme indiqué ci-dessous :

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Désactive le fond d'écran
busyIndicator.open();
```
:::info Désactivation du fond d'écran
Même lorsque vous désactivez le fond d'écran, le composant `BusyIndicator` continue de bloquer l'interaction utilisateur pour garantir que le processus sous-jacent se termine sans interruption. Le fond d'écran ne contrôle que l'affichage visuel, et non le comportement de blocage de l'interaction.
:::

## `Spinner` {#spinner}

Le composant `BusyIndicator` dans webforJ inclut un `Spinner` qui indique visuellement qu'une opération de fond est en cours. Vous pouvez personnaliser ce spinner avec plusieurs options, notamment sa taille, sa vitesse, sa direction, son thème et sa visibilité.

Voici un exemple de la façon dont vous pouvez personnaliser le spinner au sein d'un composant `BusyIndicator` :

<ComponentDemo 
path='/webforj/busyspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java'
height = '200px'
/>

## Cas d'utilisation {#use-cases}
- **Traitement sur l'ensemble de la page**  
   Le `BusyIndicator` est bien adapté aux opérations plus importantes sur l'ensemble de la page, comme lorsque l'utilisateur engage une tâche qui affecte l'ensemble de la page, comme le téléchargement d'un fichier ou le traitement de données dans plusieurs sections. Il peut informer les utilisateurs que l'ensemble de l'application est en cours de traitement, empêchant toute interaction jusqu'à ce que le processus soit terminé.

- **Opérations critiques du système**  
   Lors de l'exécution de tâches critiques pour le système, telles que la synchronisation des données, l'application de mises à jour à l'échelle du système ou le traitement d'informations sensibles, le `BusyIndicator` fournit un retour visuel clair qu'une opération majeure est en cours, permettant à l'utilisateur d'attendre jusqu'à ce qu'elle soit terminée.

- **Chargements de données asynchrones**  
   Dans des scénarios impliquant un traitement de données asynchrones, comme lors de l'appel de plusieurs API ou en attendant des calculs complexes, le composant `BusyIndicator` indique activement que le système est occupé, incitant les utilisateurs à attendre avant de réaliser des actions supplémentaires.

## Style {#styling}

<TableBuilder name="BusyIndicator" />
