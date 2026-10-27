---
title: BBjServices
sidebar_position: 45
sidebar_class_name: new-content
description: >-
  Set up live reload and hotswap for a webforJ app that runs in the BBjServices
  of your own machine through BBjShip.
_i18n_hash: 46a42789a4b05b258a057e9acae1ef12
---
Dans BBjServices, la construction ne démarre pas de serveur propre. `mvn compile webforj:watch` exécute l'application dans les BBjServices de votre propre machine à travers [BBjShip](/docs/configuration/bbjship/overview), directement depuis le projet. Un projet archétype avec la saveur BBj définit cela comme son objectif Maven par défaut, donc `mvn` sans arguments compile l'application, démarre le [frontend watch](/docs/configuration/deploy-reload/frontend-watch) et exécute l'application.

## Exigences {#requirements}

Un projet Maven configuré pour BBjShip, voir [Commencer](/docs/configuration/bbjship/getting-started) ou [Projets existants](/docs/configuration/bbjship/existing-projects), avec BBj installé sur la même machine que le projet. BBjServices charge les classes à partir des dossiers que la construction compile, donc un serveur BBj sur une autre machine ne reçoit que des [déploiements](/docs/configuration/bbjship/deploying), sans rechargement en direct ni hotswap.

## Activation du rechargement en direct {#turning-live-reload-on}

```ini title="webforj.conf"
webforj.devtools.livereload.enabled = true
```

Les clés restantes sont listées dans les [paramètres](/docs/configuration/deploy-reload/overview#settings).

## Changements de classe {#class-changes}

Dans BBjServices, un changement de classe atteint l'application en cours d'exécution uniquement à travers un [outil de hotswap](/docs/configuration/deploy-reload/hotswap). Sans celui-ci, un changement compilé ne s'applique pas et la page ne se recharge pas jusqu'à ce que la surveillance recommence. Un projet archétype configure HotswapAgent :

```xml title="pom.xml"
<hotswap>
  <hotswapAgent/>
</hotswap>
```

L'outil se connecte à BBjServices, pas à la construction. La première exécution l'ajoute aux arguments Java de BBjServices et redémarre BBjServices une fois. Les exécutions ultérieures démarrent sans redémarrage. Sur macOS et Linux, et sur Windows lorsque BBjServices n'est pas enregistré en tant que service Windows, les BBjServices redémarrés fonctionnent en dehors du gestionnaire de services de l'operating système jusqu'à ce que la machine redémarre. [Débogage](/docs/configuration/bbjship/troubleshooting#development-runs) liste les messages d'une exécution de développement et ce qu'il faut faire à propos de chacun.

:::warning Redémarre BBjServices
La première exécution change les paramètres Java de BBjServices dans `<BBj home>/cfg/BBj.properties` et redémarre BBjServices. Le redémarrage met fin à chaque session BBj en cours sur cette machine, pas seulement celles de webforJ, donc effectuez-le lorsque personne d'autre n'utilise ce BBj. Avant le premier changement, une copie du fichier est conservée sous le nom `BBj.properties.before-webforj` dans le dossier `<BBj home>/plugins/.webforj-bbjship-deployments`. Sauvegardez le fichier vous-même d'abord si vous l'avez changé manuellement. `mvn webforj:bbj-setup` apporte le même changement.
:::

Un changement de dépendance nécessite un nouveau démarrage de la surveillance.

### Java de BBjServices {#java-of-bbjservices}

Avec HotswapAgent, [les changements de structure de classe](/docs/configuration/deploy-reload/hotswap#class-structure-changes) dépendent de la version de Java sur laquelle BBjServices s'exécute, et non de la version de Java de la construction. La surveillance ne la change jamais. Pour exécuter BBjServices sur un [JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime/releases) :

1. Dans le Gestionnaire d'entreprise, allez à **BBjServices**, **Paramètres Java**.
2. Sélectionnez **BBjServices** sous **Application** et définissez le Java home.
3. Redémarrez BBjServices. **Détails Java** dans le Gestionnaire d'entreprise affichera ensuite le JDK et la version sur laquelle il s'exécute.

Voir [Prérequis](/docs/introduction/prerequisites#java-development-kit-jdk-21) pour l'installation d'un JetBrains Runtime.

## Versions de production {#production-builds}

`mvn install` déploie l'application empaquetée sans outils de développement, voir [Déployer](/docs/configuration/bbjship/deploying). La propriété `webforj.devtools.livereload.enabled` n'a aucun effet dans une application empaquetée.
