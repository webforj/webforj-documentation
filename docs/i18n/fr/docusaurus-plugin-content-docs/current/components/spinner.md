---
title: Spinner
sidebar_position: 110
description: >-
  Indicate background activity with the Spinner component, configuring theme,
  expanse, rotation speed, and pause or resume.
_i18n_hash: 22812c9195f148410b746c3547a0f118
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

Le composant `Spinner` fournit un indicateur visuel qui indique un traitement ou un chargement en cours en arrière-plan. Il est souvent utilisé pour montrer que le système accède à des données ou lorsque un processus prend du temps à se terminer. Le `Spinner` offre un retour utilisateur, signalant que le système est activement en train de travailler.

<!-- INTRO_END -->

Créez une instance de `Spinner`, puis définissez son apparence et son comportement avec des méthodes telles que `setTheme()` et `setExpanse()`.

<ComponentDemo
path='/webforj/spinnerdemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java']}
height='225px'
/>

## Gestion de la vitesse et des pauses {#managing-speed-and-pausing}

Il est possible de définir la vitesse en millisecondes pour le `Spinner` et de mettre en pause/reprendre l'animation facilement.

Les cas d'utilisation pour définir les vitesses incluent la différenciation des processus de chargement. Par exemple, des `Spinners` plus rapides conviennent aux petites tâches, tandis que des `Spinners` plus lents sont mieux adaptés pour des tâches plus volumineuses. La mise en pause est utile lorsque l'action de l'utilisateur ou une confirmation est requise avant de poursuivre le processus.

### Ajustement de la vitesse {#adjusting-speed}

Vous pouvez contrôler la vitesse de rotation du `Spinner` en ajustant sa vitesse en millisecondes à l’aide de la méthode `setSpeed()`. Une valeur plus basse fait tourner le `Spinner` plus vite, tandis que des valeurs plus élevées le ralentiront.

```java
spinner.setSpeed(500); // Tourne plus vite
```

:::info Vitesse par défaut
Par défaut, le `Spinner` prendra 1000 millisecondes pour compléter une rotation complète.
:::

### Mise en pause et reprise {#pausing-and-resuming}

Mettre le `Spinner` en pause est utile lorsqu’un programme est temporairement arrêté ou en attente d'une entrée utilisateur. Cela avertit les utilisateurs que le programme est en attente, plutôt que de fonctionner activement, ce qui améliore la clarté lors de processus en plusieurs étapes.

Pour mettre en pause et reprendre le `Spinner`, utilisez la méthode `setPaused()`. Ceci est particulièrement utile lorsque vous devez arrêter temporairement l'animation de rotation.

```java
spinner.setPaused(true);  // Met le spinner en pause
spinner.setPaused(false); // Reprend le spinner
```

Cet exemple montre comment définir la vitesse ainsi que comment mettre en pause/reprendre le `Spinner` :

<ComponentDemo
path='/webforj/spinnerspeeddemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java']}
height='150px'
/>

## Direction de rotation {#spin-direction}

La direction du `Spinner` peut être contrôlée pour tourner **dans le sens des aiguilles d'une montre** ou **dans le sens inverse**. Vous pouvez spécifier ce comportement en utilisant la méthode `setClockwise()`.

```java
spinner.setClockwise(false);  // Tourne dans le sens inverse
spinner.setClockwise(true);   // Tourne dans le sens des aiguilles d'une montre
```

Cette option indique visuellement un état spécial ou sert de choix de design unique. Changer la direction de rotation peut aider à différencier entre les types de processus, comme le progrès par rapport au retour, ou fournir un indice visuel distinct dans des contextes spécifiques.

<ComponentDemo
path='/webforj/spinnerdirectiondemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java']}
height='150px'
/>

## Style {#styling}

### Thèmes {#themes}

Le composant `Spinner` est livré avec plusieurs thèmes intégrés qui vous permettent d'appliquer rapidement des styles sans avoir besoin de CSS personnalisé. Ces thèmes changent l'apparence visuelle du spinner, le rendant approprié pour différents cas d'utilisation et contextes. L'utilisation de ces thèmes prédéfinis garantit la cohérence des styles dans toute votre application.

Bien que les spinners servent diverses situations, voici quelques exemples de cas d'utilisation pour les différents thèmes :

- **Primary** : Idéal pour mettre en évidence un état de chargement qui est un élément clé du flux utilisateur, comme lors de la soumission d'un formulaire ou du traitement d'une action importante.

- **Success** : Utile pour représenter des processus en arrière-plan réussis, comme lorsque l'utilisateur soumet un formulaire et que l'application effectue les dernières étapes du processus.

- **Danger** : Utilisez ceci pour des opérations risquées ou à enjeux élevés, comme la suppression de données importantes ou l'exécution de changements irréversibles, où un indicateur visuel d'urgence ou de prudence est nécessaire.

- **Warning** : Utilisez ceci pour indiquer un processus de mise en garde ou moins urgent, comme lorsque l'utilisateur attend la validation des données, mais ne nécessite pas d'action immédiate.

- **Gray** : Fonctionne bien pour des processus en arrière-plan subtils, tels que des tâches de chargement de faible priorité ou passives, comme lorsqu'on récupère des données supplémentaires qui n'impactent pas directement l'expérience utilisateur.

- **Info** : Convient pour des scénarios de chargement où vous fournissez des informations supplémentaires ou des clarifications à l'utilisateur, comme afficher un spinner avec un message expliquant le processus en cours.

Vous pouvez appliquer ces thèmes de manière programmatique au spinner, fournissant des indices visuels qui s'alignent sur le contexte et l'importance de l'opération.

Vous pouvez spécifier ce comportement en utilisant la méthode `setTheme()`.

<ComponentDemo
path='/webforj/spinnerthemedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java']}
height='100px'
/>

### Expanses {#expanses}

Vous pouvez ajuster la taille du spinner, connue sous le nom d'**expanse**, pour s'adapter à l'espace visuel dont vous avez besoin. Le spinner prend en charge différentes tailles, y compris `Expanse.SMALL`, `Expanse.MEDIUM`, et `Expanse.LARGE`.

<ComponentDemo
path='/webforj/spinnerexpansedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java']}
height='100px'
/>

<TableBuilder name="Spinner" />
