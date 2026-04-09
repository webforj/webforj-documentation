---
title: Spinner
sidebar_position: 110
_i18n_hash: bb61c6f2d3cf7073ca2d1c6fc6e1c0c4
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

Le composant `Spinner` fournit un indicateur visuel qui signale un traitement ou un chargement en cours en arrière-plan. Il est souvent utilisé pour montrer que le système récupère des données ou lorsqu'un processus prend du temps à se terminer. Le `Spinner` offre un retour d'utilisateur, signalant que le système est en train de travailler activement.

<!-- INTRO_END -->

## Bases {#basics}

Pour créer un `Spinner`, vous pouvez spécifier le thème et l'expanse. La syntaxe de base implique de créer une instance de `Spinner` et de définir son apparence et son comportement à travers des méthodes telles que `setTheme()` et `setExpanse()`.

<ComponentDemo 
path='/webforj/spinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java'
height = '225px'
/>

## Gestion de la vitesse et de la pause {#managing-speed-and-pausing}

Il est possible de définir la vitesse en millisecondes pour le `Spinner` et de mettre en pause/reprendre facilement l'animation.

Les scénarios d'utilisation pour définir les vitesses incluent la différenciation entre les processus de chargement. Par exemple, les `Spinners` plus rapides sont adaptés aux petites tâches, tandis que les `Spinners` plus lents conviennent mieux aux grandes tâches. La pause est utile lorsqu'une action ou une confirmation de l'utilisateur est requise avant de poursuivre le processus.

### Ajustement de la vitesse {#adjusting-speed}

Vous pouvez contrôler la vitesse de rotation du `Spinner` en ajustant sa vitesse en millisecondes à l'aide de la méthode `setSpeed()`. Une valeur plus basse fera tourner le `Spinner` plus rapidement, tandis que des valeurs plus élevées le ralentiront.

```java
spinner.setSpeed(500); // Tourne plus vite
```

:::info Vitesse par défaut
Par défaut, le `Spinner` mettra 1000 millisecondes pour effectuer une rotation complète.
:::

### Mise en pause et reprise {#pausing-and-resuming}

Mettre le `Spinner` en pause est utile lorsqu'un programme est temporairement arrêté ou en attente d'une entrée utilisateur. Cela permet aux utilisateurs de savoir que le programme est en attente, plutôt qu'en cours d'exécution, ce qui améliore la clarté lors des processus en plusieurs étapes.

Pour mettre en pause et reprendre le Spinner, utilisez la méthode `setPaused()`. Cela est particulièrement utile lorsque vous devez arrêter temporairement l'animation de rotation.      

```java
spinner.setPaused(true);  // Met le spinner en pause
spinner.setPaused(false); // Reprend le spinner
```

Cet exemple montre comment régler la vitesse et comment mettre en pause/reprendre le `Spinner` :

<ComponentDemo 
path='/webforj/spinnerspeeddemo?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java'
height = '150px'
/>

## Direction de rotation {#spin-direction}

La direction du `Spinner` peut être contrôlée pour tourner **dans le sens des aiguilles d'une montre** ou **dans le sens inverse des aiguilles d'une montre**. Vous pouvez spécifier ce comportement à l'aide de la méthode `setClockwise()`.

```java
spinner.setClockwise(false);  // Tourne dans le sens inverse des aiguilles d'une montre
spinner.setClockwise(true);   // Tourne dans le sens des aiguilles d'une montre
```

Cette option indique visuellement un état spécial ou sert de choix de design unique. Changer la direction de rotation peut aider à différencier les types de processus, tels que progrès vs. retour, ou fournir un indice visuel distinct dans des contextes spécifiques.

<ComponentDemo 
path='/webforj/spinnerdirectiondemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java'
height = '150px'
/>

## Style {#styling}

### Thèmes {#themes}

Le composant `Spinner` est livré avec plusieurs thèmes intégrés qui vous permettent d'appliquer rapidement des styles sans avoir besoin de CSS personnalisé. Ces thèmes changent l'apparence visuelle du spinner, le rendant approprié pour différents cas d'utilisation et contextes. L'utilisation de ces thèmes prédéfinis garantit la cohérence du style dans toute votre application.

Bien que les spinners servent à diverses situations, voici quelques exemples de cas d'utilisation pour les différents thèmes :

- **Primaire** : Idéal pour souligner un état de chargement qui constitue une partie clé du flux utilisateur, comme lors de la soumission d'un formulaire ou du traitement d'une action importante.
  
- **Succès** : Utile pour représenter des processus en arrière-plan réussis, comme lorsque l'utilisateur soumet un formulaire et que l'application effectue les dernières étapes du processus.
  
- **Danger** : Utilisez ceci pour des opérations risquées ou à enjeux élevés, comme la suppression de données importantes ou la réalisation de modifications irréversibles, où un indicateur visuel d'urgence ou de prudence est nécessaire.
  
- **Avertissement** : Utilisez ceci pour indiquer un processus de précaution ou moins urgent, comme lorsque l'utilisateur attend la validation des données, mais n'exige pas d'action immédiate.

- **Gris** : Fonctionne bien pour des processus de fond subtils, comme des tâches de chargement de faible priorité ou passives, comme lors de la récupération de données supplémentaires qui n'impactent pas directement l'expérience utilisateur.
  
- **Info** : Convient aux scénarios de chargement où vous fournissez des informations supplémentaires ou des clarifications à l'utilisateur, comme afficher un spinner aux côtés d'un message qui explique le processus en cours.

Vous pouvez appliquer ces thèmes de manière programmatique au spinner, fournissant des indices visuels qui s'alignent avec le contexte et l'importance de l'opération.

Vous pouvez spécifier ce comportement à l'aide de la méthode `setTheme()`.

<ComponentDemo 
path='/webforj/spinnerthemedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java'
height = '100px'
/>

### Expanses {#expanses}

Vous pouvez ajuster la taille du spinner, connue sous le nom d'**expanse**, pour s'adapter à l'espace visuel dont vous avez besoin. Le spinner prend en charge diverses tailles, y compris `Expanse.SMALL`, `Expanse.MEDIUM`, et `Expanse.LARGE`.

<ComponentDemo 
path= '/webforj/spinnerexpansedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java'
height = '100px'
/>

<TableBuilder name="Spinner" />
