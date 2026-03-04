---
title: Spinner
sidebar_position: 110
_i18n_hash: c60e7d3c3604a39de7f659f169d973a6
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

Le composant `Spinner` fournit un indicateur visuel qui signale un traitement ou un chargement en cours en arrière-plan. Il est souvent utilisé pour montrer que le système récupère des données ou lorsque un processus prend du temps à s'achever. Le `Spinner` offre un retour d'information à l'utilisateur, signalant que le système est en train de travailler activement.

<!-- INTRO_END -->

## Bases {#basics}

Pour créer un `Spinner`, vous pouvez spécifier le thème et l'expanse. La syntaxe de base consiste à créer une instance de `Spinner` et à définir son apparence et son comportement à l'aide de méthodes telles que `setTheme()` et `setExpanse()`.

<ComponentDemo 
path='/webforj/spinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java'
cssURL='/css/spinnerstyles/spinnerdemo.css'
height = '225px'
/>

## Gestion de la vitesse et mise en pause {#managing-speed-and-pausing}

Il est possible de définir la vitesse en millisecondes pour le `Spinner` et de mettre en pause/reprendre l'animation facilement. 

Les cas d'utilisation pour les vitesses incluent la distinction entre les processus de chargement. Par exemple, des `Spinners` plus rapides conviennent pour des tâches plus petites, tandis que des `Spinners` plus lents sont mieux pour des tâches plus grandes. La mise en pause est utile lorsque l'action ou la confirmation de l'utilisateur est requise avant de continuer le processus.

### Ajustement de la vitesse {#adjusting-speed}

Vous pouvez contrôler la vitesse de rotation du `Spinner` en ajustant sa vitesse en millisecondes à l'aide de la méthode `setSpeed()`. Une valeur inférieure permet au `Spinner` de tourner plus vite, tandis que des valeurs plus élevées le ralentissent.

```java
spinner.setSpeed(500); // Tourne plus vite
```

:::info Vitesse par défaut
Par défaut, le `Spinner` mettra 1000 millisecondes pour effectuer une rotation complète.
:::

### Mise en pause et reprise {#pausing-and-resuming}

Mettre le `Spinner` en pause est utile lorsqu'un programme est temporairement arrêté ou en attente d'une entrée de l'utilisateur. Cela permet aux utilisateurs de savoir que le programme est en attente, plutôt que de s'exécuter activement, ce qui améliore la clarté lors de processus en plusieurs étapes.

Pour mettre en pause et reprendre le `Spinner`, utilisez la méthode `setPaused()`. Cela est particulièrement utile lorsque vous devez arrêter temporairement l'animation.

```java
spinner.setPaused(true);  // Met le spinner en pause
spinner.setPaused(false); // Reprend le spinner
```

Cet exemple montre comment définir la vitesse et comment mettre en pause/reprendre le `Spinner` :

<ComponentDemo 
path='/webforj/spinnerspeeddemo?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java'
cssURL='/css/spinnerstyles/spinnerspeeddemo.css'
height = '150px'
/>

## Direction de rotation {#spin-direction}

La direction du `Spinner` peut être contrôlée pour tourner **dans le sens horaire** ou **dans le sens antihoraire**. Vous pouvez spécifier ce comportement à l'aide de la méthode `setClockwise()`.

```java
spinner.setClockwise(false);  // Tourne dans le sens antihoraire
spinner.setClockwise(true);   // Tourne dans le sens horaire
```

Cette option indique visuellement un état particulier ou sert de choix de design unique. Changer la direction de rotation peut aider à différencier les types de processus, tels que le progrès et le retour, ou fournir un indice visuel distinct dans des contextes spécifiques.

<ComponentDemo 
path='/webforj/spinnerdirectiondemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java'
height = '150px'
/>

## Stylisation {#styling}

### Thèmes {#themes}

Le composant `Spinner` est fourni avec plusieurs thèmes intégrés qui vous permettent d'appliquer rapidement des styles sans avoir besoin de CSS personnalisé. Ces thèmes changent l'apparence visuelle du spinner, le rendant approprié pour différents cas d'utilisation et contextes. L'utilisation de ces thèmes prédéfinis garantit la cohérence dans le style de votre application.

Bien que les spinners servent diverses situations, voici quelques exemples d'utilisation pour les différents thèmes :

- **Primaire** : Idéal pour souligner un état de chargement qui est une partie clé du flux utilisateur, comme lors de la soumission d'un formulaire ou du traitement d'une action importante.
  
- **Succès** : Utile pour représenter des processus de fond réussis, comme lorsque un utilisateur soumet un formulaire et que l'application effectue les étapes finales du processus.
  
- **Danger** : Utilisez ceci pour des opérations risquées ou critiques, comme la suppression de données importantes ou des modifications irréversibles, où un indicateur visuel d'urgence ou de prudence est nécessaire.
  
- **Avertissement** : Utilisez ceci pour indiquer un processus de précaution ou moins urgent, comme lorsque l'utilisateur attend la validation des données, mais ne nécessite pas d'action immédiate.

- **Gris** : Fonctionne bien pour des processus de fond subtils, tels que des tâches de chargement de faible priorité ou passives, comme lorsqu'on récupère des données supplémentaires qui n'impactent pas directement l'expérience utilisateur.
  
- **Info** : Convient aux scénarios de chargement où vous fournissez des informations ou des clarifications supplémentaires à l'utilisateur, comme afficher un spinner avec un message qui explique le processus en cours.

Vous pouvez appliquer ces thèmes de manière programmatique au spinner, fournissant des indices visuels qui s'alignent avec le contexte et l'importance de l'opération.

Vous pouvez spécifier ce comportement en utilisant la méthode `setTheme()`.

<ComponentDemo 
path='/webforj/spinnerthemedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java'
cssURL='/css/spinnerstyles/spinnerthemedemo.css'
height = '100px'
/>

### Expanses {#expanses}

Vous pouvez ajuster la taille du spinner, connue sous le nom d'**expanse**, pour s'adapter à l'espace visuel dont vous avez besoin. Le spinner prend en charge différentes tailles, y compris `Expanse.SMALL`, `Expanse.MEDIUM` et `Expanse.LARGE`.

<ComponentDemo 
path= '/webforj/spinnerexpansedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java'
cssURL='/css/spinnerstyles/spinnerexpansedemo.css'
height = '100px'
/>

<TableBuilder name="Spinner" />
