---
title: Spinner
sidebar_position: 110
description: >-
  Indicate background activity with the Spinner component, configuring theme,
  expanse, rotation speed, and pause or resume.
_i18n_hash: 6a667297edb572a6990b58bee72c8b1b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

Le composant `Spinner` fournit un indicateur visuel qui signale un traitement ou un chargement en cours en arrière-plan. Il est souvent utilisé pour montrer que le système récupère des données ou lorsqu'un processus prend du temps à se compléter. Le `Spinner` offre un retour utilisateur, signalant que le système travaille activement.

<!-- INTRO_END -->

Créez une instance de `Spinner`, puis définissez son apparence et son comportement avec des méthodes telles que `setTheme()` et `setExpanse()`.

<ComponentDemo
path='/webforj/spinnerdemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java']}
height='225px'
/>

## Gestion de la vitesse et des pauses {#managing-speed-and-pausing}

Il est possible de définir la vitesse en millisecondes pour le `Spinner` et de mettre en pause/reprendre l'animation facilement.

Les cas d'utilisation pour la définition des vitesses incluent la différenciation entre les processus de chargement. Par exemple, les `Spinners` plus rapides sont adaptés pour des tâches plus petites, tandis que les `Spinners` plus lents conviennent mieux aux tâches plus importantes. La pause est utile lorsque l'action ou la confirmation de l'utilisateur est requise avant de continuer le processus.

### Ajustement de la vitesse {#adjusting-speed}

Vous pouvez contrôler la rapidité de rotation du `Spinner` en ajustant sa vitesse en millisecondes en utilisant la méthode `setSpeed()`. Une valeur plus basse fait tourner le `Spinner` plus rapidement, tandis que des valeurs plus élevées le ralentissent.

```java
spinner.setSpeed(500); // Tourne plus vite
```

:::info Vitesse par défaut
Par défaut, le `Spinner` mettra 1000 millisecondes pour effectuer une rotation complète.
:::

### Mise en pause et reprise {#pausing-and-resuming}

Mettre le `Spinner` en pause est utile lorsqu'un programme est temporairement arrêté ou en attente d'une saisie utilisateur. Cela permet aux utilisateurs de savoir que le programme est à l'arrêt, plutôt que de fonctionner activement, ce qui améliore la clarté lors des processus en plusieurs étapes.

Pour mettre en pause et reprendre le Spinner, utilisez la méthode `setPaused()`. Cela est particulièrement utile lorsque vous devez arrêter temporairement l'animation de rotation.

```java
spinner.setPaused(true);  // Met le spinner en pause
spinner.setPaused(false); // Reprend le spinner
```

Cet exemple montre comment définir la vitesse et comment mettre en pause/reprendre le `Spinner` :

<ComponentDemo
path='/webforj/spinnerspeeddemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java']}
height='150px'
/>

## Direction de la rotation {#spin-direction}

La direction du `Spinner` peut être contrôlée pour tourner **dans le sens des aiguilles d'une montre** ou **dans le sens inverse**. Vous pouvez spécifier ce comportement en utilisant la méthode `setClockwise()`.

```java
spinner.setClockwise(false);  // Tourne dans le sens inverse
spinner.setClockwise(true);   // Tourne dans le sens des aiguilles d'une montre
```

Cette option indique visuellement un état particulier ou sert de choix de design unique. Changer la direction de rotation peut aider à différencier les types de processus, comme progrès vs. retour en arrière, ou fournir un indice visuel distinct dans des contextes spécifiques.

<ComponentDemo
path='/webforj/spinnerdirectiondemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java']}
height='150px'
/>

## Stylisation {#styling}

### Thèmes {#themes}

Le composant `Spinner` est livré avec plusieurs thèmes intégrés qui vous permettent d'appliquer rapidement des styles sans avoir besoin de CSS personnalisé. Ces thèmes changent l'apparence visuelle du spinner, le rendant approprié pour différents cas d'utilisation et contextes. Utiliser ces thèmes prédéfinis garantit une cohérence dans le style tout au long de votre application.

Bien que les spinners servent à diverses situations, voici quelques exemples de cas d'utilisation pour les différents thèmes :

- **Principal** : Idéal pour souligner un état de chargement qui est une partie clé du flux utilisateur, comme lors de la soumission d'un formulaire ou du traitement d'une action importante.

- **Succès** : Utile pour représenter des processus en arrière-plan réussis, comme lorsque l'utilisateur soumet un formulaire et que l'application effectue les dernières étapes du processus.

- **Danger** : Utilisez ceci pour des opérations risquées ou à fort enjeu, comme la suppression de données importantes ou la réalisation de changements irréversibles, où un indicateur visuel d'urgence ou de précaution est nécessaire.

- **Avertissement** : Utilisez ceci pour indiquer un processus de précaution ou moins urgent, comme lorsque l'utilisateur attend une validation de données, mais n'exige pas d'action immédiate.

- **Gris** : Convient bien pour des processus en arrière-plan subtils, comme des tâches de chargement de faible priorité ou passives, comme lors de la récupération de données supplémentaires qui n'impactent pas directement l'expérience utilisateur.

- **Info** : Adapté pour des scénarios de chargement où vous fournissez des informations supplémentaires ou des clarifications à l'utilisateur, comme afficher un spinner avec un message qui explique le processus en cours.

Vous pouvez appliquer ces thèmes de manière programmatique au spinner, fournissant des indices visuels qui s'alignent avec le contexte et l'importance de l'opération.

Vous pouvez spécifier ce comportement en utilisant la méthode `setTheme()`.

<ComponentDemo
path='/webforj/spinnerthemedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java']}
height='100px'
/>

### Expansions {#expanses}

Vous pouvez ajuster la taille du spinner, connue sous le nom d'**expanse**, en utilisant `setExpanse()` avec l'énumération `SpinnerExpanse`. Les neuf tailles disponibles sont `XXXSMALL`, `XXSMALL`, `XSMALL`, `SMALL`, `MEDIUM`, `LARGE`, `XLARGE`, `XXLARGE`, et `XXXLARGE`. La valeur par défaut est `SpinnerExpanse.NONE`.

```java
spinner.setExpanse(SpinnerExpanse.LARGE);
```

<ComponentDemo
path='/webforj/spinnerexpansedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java']}
height='100px'
/>

<TableBuilder name="Spinner" />
