---
title: Spinner
sidebar_position: 110
_i18n_hash: d93d5704fff2acc975910f1a10e34d0b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

Le composant `Spinner` fournit un indicateur visuel indiquant un traitement en cours ou un chargement en arrière-plan. Il est souvent utilisé pour montrer que le système est en train de récupérer des données ou lorsque un processus prend du temps à se terminer. Le `Spinner` offre un retour utilisateur, signalant que le système est en cours de travail.

<!-- INTRO_END -->

## Bases {#basics}

Pour créer un `Spinner`, vous pouvez spécifier le thème et l'expansion. La syntaxe de base implique la création d'une instance de `Spinner` et la définition de son apparence et de son comportement à l'aide de méthodes telles que `setTheme()` et `setExpanse()`.

<ComponentDemo
path='/webforj/spinnerdemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java']}
height='225px'
/>

## Gestion de la vitesse et de la pause {#managing-speed-and-pausing}

Il est possible de définir la vitesse en millisecondes pour le `Spinner` et de mettre en pause/reprendre l'animation facilement.

Les cas d'utilisation pour définir des vitesses incluent la différenciation entre les processus de chargement. Par exemple, les `Spinners` plus rapides conviennent aux petites tâches, tandis que les `Spinners` plus lents sont mieux adaptés aux tâches plus importantes. La mise en pause est utile lorsque l'action ou la confirmation de l'utilisateur est requise avant de continuer le processus.

### Ajustement de la vitesse {#adjusting-speed}

Vous pouvez contrôler la vitesse de rotation du `Spinner` en ajustant sa vitesse en millisecondes à l'aide de la méthode `setSpeed()`. Une valeur plus basse fait tourner le `Spinner` plus rapidement, tandis que des valeurs plus élevées le ralentiront.

```java
spinner.setSpeed(500); // Tourne plus vite
```

:::info Vitesse par défaut
Par défaut, le `Spinner` mettra 1000 millisecondes pour effectuer une rotation complète.
:::

### Mise en pause et reprise {#pausing-and-resuming}

Mettre le `Spinner` en pause est utile lorsque un programme est temporairement arrêté ou en attente de l'entrée d'un utilisateur. Cela permet aux utilisateurs de savoir que le programme est en pause, plutôt qu'en cours d'exécution, ce qui améliore la clarté lors des processus en plusieurs étapes.

Pour mettre en pause et reprendre le `Spinner`, utilisez la méthode `setPaused()`. Cela est particulièrement utile lorsque vous devez arrêter temporairement l'animation de rotation.      

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

## Direction de rotation {#spin-direction}

La direction du `Spinner` peut être contrôlée pour tourner **dans le sens des aiguilles d'une montre** ou **dans le sens inverse des aiguilles d'une montre**. Vous pouvez spécifier ce comportement en utilisant la méthode `setClockwise()`.

```java
spinner.setClockwise(false);  // Tourne dans le sens inverse des aiguilles d'une montre
spinner.setClockwise(true);   // Tourne dans le sens des aiguilles d'une montre
```

Cette option indique visuellement un état spécial ou sert de choix de design unique. Changer la direction de rotation peut aider à différencier les types de processus, comme le progrès par rapport au renversement, ou fournir un indice visuel distinct dans des contextes spécifiques.

<ComponentDemo
path='/webforj/spinnerdirectiondemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java']}
height='150px'
/>

## Stylisation {#styling}

### Thèmes {#themes}

Le composant `Spinner` est livré avec plusieurs thèmes intégrés qui vous permettent d'appliquer rapidement des styles sans avoir besoin de CSS personnalisé. Ces thèmes modifient l'apparence visuelle du spinner, le rendant approprié pour différents cas d'utilisation et contextes. Utiliser ces thèmes prédéfinis garantit la cohérence de la stylisation dans toute votre application.

Alors que les spinners servent à diverses situations, voici quelques cas d'utilisation pour les différents thèmes :

- **Principal** : Idéal pour mettre en avant un état de chargement qui est une partie clé du flux utilisateur, comme lors de la soumission d'un formulaire ou du traitement d'une action importante.
  
- **Succès** : Utile pour représenter des processus en arrière-plan réussis, comme lorsque un utilisateur soumet un formulaire et que l'application effectue les dernières étapes du processus.
  
- **Danger** : Utilisez cela pour des opérations risquées ou à enjeux élevés, comme la suppression de données importantes ou la réalisation de changements irréversibles, où un indicateur visuel d'urgence ou de prudence est nécessaire.
  
- **Avertissement** : Utilisez ceci pour indiquer un processus de précaution ou moins urgent, comme lorsque l'utilisateur attend la validation des données, mais ne nécessite pas d'action immédiate.

- **Gris** : Convient bien pour des processus en arrière-plan subtils, tels que des tâches de chargement de faible priorité ou passives, comme lorsqu'on récupère des données supplémentaires qui n'impactent pas directement l'expérience utilisateur.
  
- **Info** : Adapté aux scénarios de chargement où vous fournissez des informations ou des clarifications supplémentaires à l'utilisateur, comme l'affichage d'un spinner aux côtés d'un message qui explique le processus en cours.

Vous pouvez appliquer ces thèmes par programmation au spinner, fournissant des indices visuels qui s'alignent avec le contexte et l'importance de l'opération.

Vous pouvez spécifier ce comportement en utilisant la méthode `setTheme()`.

<ComponentDemo
path='/webforj/spinnerthemedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java']}
height='100px'
/>

### Expanses {#expanses}

Vous pouvez ajuster la taille du spinner, connue sous le nom d'**expanse**, pour s'adapter à l'espace visuel dont vous avez besoin. Le spinner prend en charge différentes tailles allant de `Expanse.SMALL`, `Expanse.MEDIUM`, à `Expanse.LARGE`.

<ComponentDemo
path='/webforj/spinnerexpansedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java']}
height='100px'
/>

<TableBuilder name="Spinner" />
