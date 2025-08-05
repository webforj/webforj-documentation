---
title: Spinner
sidebar_position: 110
_i18n_hash: 8ab95efdcfcc1e42df56c372da27cc81
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

Le composant `Spinner` fournit un indicateur visuel qui indique un traitement ou un chargement en cours en arrière-plan. Il est souvent utilisé pour montrer que le système récupère des données ou qu'un processus prend du temps à se terminer. Le spinner offre un retour d'information à l'utilisateur, signalant que le système est activement en cours d'exécution.

## Basics {#basics}

Pour créer un `Spinner`, vous pouvez spécifier le thème et l'expansion. La syntaxe de base consiste à créer une instance de `Spinner` et à définir son apparence et son comportement via des méthodes telles que `setTheme()` et `setExpanse()`.

<ComponentDemo 
path='/webforj/spinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java'
cssURL='/css/spinnerstyles/spinnerdemo.css'
height = '225px'
/>

## Gestion de la vitesse et mise en pause {#managing-speed-and-pausing}

Il est possible de définir la vitesse en millisecondes pour le `Spinner` et de mettre en pause/reprendre l'animation facilement.

Les cas d'utilisation pour définir les vitesses incluent la différenciation entre les processus de chargement. Par exemple, des `Spinners` plus rapides conviennent aux tâches plus petites, tandis que des `Spinners` plus lents sont mieux adaptés aux tâches plus importantes. La mise en pause est utile lorsqu'une action ou une confirmation de l'utilisateur est requise avant de poursuivre le processus.

### Ajustement de la vitesse {#adjusting-speed}

Vous pouvez contrôler la vitesse à laquelle le `Spinner` tourne en ajustant sa vitesse en millisecondes à l'aide de la méthode `setSpeed()`. Une valeur inférieure fait que le `Spinner` tourne plus vite, tandis que des valeurs plus élevées le ralentissent.

```java
spinner.setSpeed(500); // Tourne plus vite
```

:::info Vitesse par défaut
Par défaut, le `Spinner` prendra 1000 millisecondes pour effectuer une rotation complète.
:::

### Mise en pause et reprise {#pausing-and-resuming}

Mettre le `Spinner` en pause est utile lorsqu'un programme est temporairement arrêté ou en attente d'une saisie utilisateur. Cela permet aux utilisateurs de savoir que le programme est en attente, plutôt que d'être activement en cours d'exécution, ce qui améliore la clarté lors des processus en plusieurs étapes.

Pour mettre en pause et reprendre le spinner, utilisez la méthode `setPaused()`. Ceci est particulièrement utile lorsque vous devez arrêter temporairement l'animation de rotation.

```java
spinner.setPaused(true);  // Mettre le spinner en pause
spinner.setPaused(false); // Reprendre le spinner
```

Cet exemple montre comment régler la vitesse et comment mettre en pause/reprendre le `Spinner` :

<ComponentDemo 
path='/webforj/spinnerspeeddemo?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java'
cssURL='/css/spinnerstyles/spinnerspeeddemo.css'
height = '150px'
/>

## Direction de rotation {#spin-direction}

La direction du `Spinner` peut être contrôlée pour tourner **dans le sens des aiguilles d'une montre** ou **dans le sens inverse des aiguilles d'une montre**. Vous pouvez spécifier ce comportement à l'aide de la méthode `setClockwise()`.

```java
spinner.setClockwise(false);  // Tourne dans le sens inverse des aiguilles d'une montre
spinner.setClockwise(true);   // Tourne dans le sens des aiguilles d'une montre
```

Cette option indique visuellement un état spécial ou constitue un choix de conception unique. Changer la direction de rotation peut aider à différencier les types de processus, tels que la progression par rapport au retournement, ou fournir un indice visuel distinct dans des contextes spécifiques.

<ComponentDemo 
path='/webforj/spinnerdirectiondemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java'
height = '150px'
/>

## Stylisation {#styling}

### Thèmes {#themes}

Le composant `Spinner` est livré avec plusieurs thèmes intégrés qui vous permettent d'appliquer rapidement des styles sans avoir besoin de CSS personnalisé. Ces thèmes modifient l'apparence visuelle du spinner, le rendant approprié pour différents cas d'utilisation et contextes. Utiliser ces thèmes prédéfinis garantit la cohérence du style tout au long de votre application.

Bien que les spinners servent à diverses situations, voici quelques exemples d'utilisation pour les différents thèmes :

- **Principal** : Idéal pour souligner un état de chargement qui fait partie intégrante du flux utilisateur, comme lors de la soumission d'un formulaire ou du traitement d'une action importante.
  
- **Succès** : Utile pour représenter des processus en arrière-plan réussis, comme lorsque l'utilisateur soumet un formulaire et que l'application effectue les étapes finales du processus.
  
- **Danger** : Utilisez ceci pour des opérations risquées ou à enjeux élevés, comme la suppression de données importantes ou l'apport de modifications irréversibles, où un indicateur visuel d'urgence ou de prudence est nécessaire.
  
- **Avertissement** : Utilisez ceci pour indiquer un processus de précaution ou moins urgent, comme lorsque l'utilisateur attend une validation des données, mais ne nécessite pas d'action immédiate.

- **Gris** : Fonctionne bien pour les processus en arrière-plan subtils, tels que des tâches de chargement de faible priorité ou passives, comme lorsque des données supplémentaires sont récupérées et n'ont pas d'impact direct sur l'expérience utilisateur.
  
- **Info** : Convient aux scénarios de chargement où vous fournissez des informations ou des clarification supplémentaires à l'utilisateur, comme l'affichage d'un spinner accompagné d'un message qui explique le processus en cours.

Vous pouvez appliquer ces thèmes par programmation au spinner, fournissant des indices visuels qui s'alignent avec le contexte et l'importance de l'opération.

Vous pouvez spécifier ce comportement à l'aide de la méthode `setTheme()`.

<ComponentDemo 
path='/webforj/spinnerthemedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java'
cssURL='/css/spinnerstyles/spinnerthemedemo.css'
height = '100px'
/>

### Expansions {#expanses}

Vous pouvez ajuster la taille du spinner, connue sous le nom d'**expanse**, pour s'adapter à l'espace visuel dont vous avez besoin. Le spinner prend en charge plusieurs tailles, y compris `Expanse.SMALL`, `Expanse.MEDIUM` et `Expanse.LARGE`.

<ComponentDemo 
path= '/webforj/spinnerexpansedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java'
cssURL='/css/spinnerstyles/spinnerexpansedemo.css'
height = '100px'
/>

<TableBuilder name="Spinner" />
