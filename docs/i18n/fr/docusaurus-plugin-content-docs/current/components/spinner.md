---
title: Spinner
sidebar_position: 110
_i18n_hash: b1137c43133bce5c5a16df51c0aa82e3
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

Le composant `Spinner` fournit un indicateur visuel qui indique un traitement en cours ou un chargement en arrière-plan. Il est souvent utilisé pour montrer que le système récupère des données ou lorsque un processus nécessite du temps pour se compléter. Le spinner offre un retour d'information à l'utilisateur, signalant que le système est en train de travailler activement.

## Basics {#basics}

Pour créer un `Spinner`, vous pouvez spécifier le thème et l'expanse. La syntaxe de base consiste à créer une instance de `Spinner` et à définir son apparence et son comportement à l'aide de méthodes telles que `setTheme()` et `setExpanse()`.

<ComponentDemo 
path='/webforj/spinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java'
cssURL='/css/spinnerstyles/spinnerdemo.css'
height = '225px'
/>

## Gestion de la vitesse et pause {#managing-speed-and-pausing}

Il est possible de définir la vitesse en millisecondes pour le `Spinner` et de mettre en pause/reprendre l'animation facilement.

Les cas d'utilisation pour définir des vitesses incluent la différenciation entre les processus de chargement. Par exemple, les `Spinners` plus rapides conviennent aux petites tâches, tandis que les `Spinners` plus lents sont meilleurs pour les tâches plus grandes. La mise en pause est utile lorsque l'action de l'utilisateur ou une confirmation est requise avant de poursuivre le processus.

### Ajustement de la vitesse {#adjusting-speed}

Vous pouvez contrôler la vitesse de rotation du `Spinner` en ajustant sa vitesse en millisecondes en utilisant la méthode `setSpeed()`. Une valeur inférieure fait tourner le `Spinner` plus rapidement, tandis que des valeurs plus élevées le ralentiront.

```java
spinner.setSpeed(500); // Rotate plus rapidement
```

:::info Vitesse par défaut
Par défaut, le `Spinner` prendra 1000 millisecondes pour compléter une rotation complète.
:::

### Mise en pause et reprise {#pausing-and-resuming}

Mettre le `Spinner` en pause est utile lorsque le programme est temporairement suspendu ou en attente d'une entrée utilisateur. Cela informe les utilisateurs que le programme est en pause, plutôt qu'en cours d'exécution, ce qui améliore la clarté lors de processus en plusieurs étapes.

Pour mettre en pause et reprendre le Spinner, utilisez la méthode `setPaused()`. Cela est particulièrement utile lorsque vous devez arrêter temporairement l'animation de rotation.

```java
spinner.setPaused(true);  // Mettre le spinner en pause
spinner.setPaused(false); // Reprendre le spinner
```

Cet exemple montre comment définir la vitesse et comment mettre en pause/reprendre le `Spinner` :

<ComponentDemo 
path='/webforj/spinnerspeeddemo?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java'
cssURL='/css/spinnerstyles/spinnerspeeddemo.css'
height = '150px'
/>

## Direction de rotation {#spin-direction}

La direction du `Spinner` peut être contrôlée pour tourner **dans le sens horaire** ou **dans le sens antihoraire**. Vous pouvez spécifier ce comportement en utilisant la méthode `setClockwise()`.

```java
spinner.setClockwise(false);  // Tourne dans le sens antihoraire
spinner.setClockwise(true);   // Tourne dans le sens horaire
```

Cette option indique visuellement un état spécial ou sert de choix de design unique. Changer la direction de rotation peut aider à différencier les types de processus, tels que le progrès par rapport à l'inversion, ou fournir un indice visuel distinct dans des contextes spécifiques.

<ComponentDemo 
path='/webforj/spinnerdirectiondemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java'
height = '150px'
/>

## Style {#styling}

### Thèmes {#themes}

Le composant `Spinner` est accompagné de plusieurs thèmes intégrés qui vous permettent d'appliquer rapidement des styles sans avoir besoin de CSS personnalisé. Ces thèmes changent l'apparence visuelle du spinner, le rendant approprié pour différents cas d'utilisation et contextes. L'utilisation de ces thèmes prédéfinis assure une cohérence de style tout au long de votre application.

Bien que les spinners servent diverses situations, voici quelques exemples de cas d'utilisation pour les différents thèmes :

- **Principal** : Idéal pour souligner un état de chargement qui fait partie intégrante du flux utilisateur, tel que lors de la soumission d'un formulaire ou du traitement d'une action importante.
  
- **Succès** : Utile pour représenter des processus en arrière-plan réussis, comme lorsque l'utilisateur soumet un formulaire et que l'application effectue les étapes finales du processus.
  
- **Danger** : Utilisez ceci pour des opérations risquées ou à enjeux élevés, comme la suppression de données importantes ou la réalisation de modifications irréversibles, où un indicateur visuel d'urgence ou de prudence est nécessaire.
  
- **Avertissement** : Utilisez ceci pour indiquer un processus de mise en garde ou moins urgent, comme lorsque l'utilisateur attend la validation des données, mais ne requiert pas d'action immédiate.

- **Gris** : Fonctionne bien pour des processus en arrière-plan subtils, tels que des tâches de chargement de faible priorité ou passives, comme lors de la récupération de données supplémentaires qui n'impactent pas directement l'expérience utilisateur.
  
- **Info** : Convient aux scénarios de chargement où vous fournissez des informations ou des clarification supplémentaires à l'utilisateur, comme l'affichage d'un spinner aux côtés d'un message qui explique le processus en cours.

Vous pouvez appliquer ces thèmes de manière programmatique au spinner, fournissant des indices visuels qui s'alignent avec le contexte et l'importance de l'opération.

Vous pouvez spécifier ce comportement en utilisant la méthode `setTheme()`.

<ComponentDemo 
path='/webforj/spinnerthemedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java'
cssURL='/css/spinnerstyles/spinnerthemedemo.css'
height = '100px'
/>

### Expanses {#expanses}

Vous pouvez ajuster la taille du spinner, connue sous le nom d'**expanse**, pour s'adapter à l'espace visuel dont vous avez besoin. Le spinner prend en charge plusieurs tailles, y compris `Expanse.SMALL`, `Expanse.MEDIUM`, et `Expanse.LARGE`.

<ComponentDemo 
path= '/webforj/spinnerexpansedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java'
cssURL='/css/spinnerstyles/spinnerexpansedemo.css'
height = '100px'
/>

<TableBuilder name="Spinner" />
