---
title: Pour commencer
sidebar_position: 2
description: >-
  Add the devtools dependency, enable craftforJ in your configuration, and open
  craftforJ over a running webforJ app.
_i18n_hash: 6cb150ca0da866a142787024af9e2bd1
---
<!-- vale off -->
# Premiers pas <DocChip chip='since' label='26.02' />
<!-- vale on -->

craftforJ est fourni avec webforJ, il n’y a donc rien à télécharger séparément. Cette page décrit ce dont votre application a besoin avant que craftforJ apparaisse et comment l’ouvrir.

:::tip Déjà activé dans les projets générés
Les projets créés avec [startforJ](https://docs.webforj.com/startforj) ou à partir d’un [archétype](/docs/building-ui/archetypes/overview) webforJ sont livrés avec craftforJ activé. Si vous avez démarré avec l’un d’eux, lancez votre application et passez directement à [Ouvrir craftforJ](#opening-craftforj).
:::

## Prérequis {#requirements}

craftforJ se rattache à une application uniquement si toutes les conditions suivantes sont remplies. Si l’une d’elles ne l’est pas, rien n’apparaît sur la page.

### Ajouter la dépendance {#add-the-dependency}

Ajoutez `webforj-devtools` à votre projet si elle n’y figure pas déjà :

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-devtools</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### Mode débogage et indicateur craftforJ {#debug-mode-and-the-craftforj-flag}

Ajoutez les propriétés suivantes à votre projet. Si vous avez une application webforJ standard, définissez les propriétés dans `webforj.conf`. Pour un projet webforJ utilisant [Spring](/docs/integrations/spring/overview), définissez-les dans `application.properties`.

```ini
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

craftforJ ne fonctionne que si les deux propriétés sont activées ; ainsi, une application mise en production alors que le mode débogage est toujours activé n’expose pas votre arborescence des sources.

### Un navigateur local et une licence développeur {#a-local-browser-and-a-developer-license}

Ouvrez l’application depuis la machine qui l’exécute et vérifiez que vous disposez d’une licence développeur valide. Pour accéder à craftforJ depuis une autre machine, ajoutez son adresse à [`hosts-allowed`](/docs/craftforj/configuration#access).

Une fois ces éléments en place, redémarrez l’application et rechargez la page.

## Ouvrir craftforJ {#opening-craftforj}

Lorsque craftforJ est actif, un bouton de déclenchement apparaît par-dessus votre application. Cliquez dessus pour ouvrir craftforJ, ou appuyez sur <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> depuis n’importe quel endroit de l’application. Le même raccourci ferme craftforJ, et vous pouvez faire glisser le bouton de déclenchement vers le coin de votre choix.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/getting-started.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

Ses onglets couvrent l’[arborescence des composants](/docs/craftforj/inspector), les [routes](/docs/craftforj/routes), le [thème](/docs/craftforj/theme) et l’[assistant](/docs/craftforj/ai). Les paramètres et les informations sur l’application se trouvent à côté.


- **Le bouton de déclenchement** ouvre et ferme craftforJ. Il reste discret lorsque craftforJ est fermé.
- **La barre d’onglets** longe le bord le plus proche de l’application et permet de passer d’un affichage à l’autre dans craftforJ.
- **Le menu de la fenêtre** regroupe tout ce qui concerne l’emplacement de craftforJ, décrit dans [Emplacement de craftforJ](#where-craftforj-sits).

:::info Raccourcis sur macOS
craftforJ affiche chaque raccourci avec les modificateurs de la plateforme utilisée ; <kbd>Alt</kbd> apparaît donc comme <kbd>⌥</kbd> et <kbd>Ctrl</kbd> comme <kbd>⌘</kbd>. Appuyez sur <kbd>Shift</kbd> + <kbd>?</kbd> dans craftforJ pour afficher la liste actuelle.
:::

## Emplacement de craftforJ {#where-craftforj-sits}

Par défaut, craftforJ flotte au-dessus de votre application. Faites-le glisser n’importe où sur la page, redimensionnez-le depuis n’importe quel bord et réduisez-le à son bouton de déclenchement lorsque vous souhaitez utiliser l’application sans lui. En le faisant glisser sur un bord de la page, vous l’ancrez à cet endroit, sur toute la hauteur ou toute la largeur ; chaque bord conserve la taille que vous lui avez donnée. En l’éloignant du bord, vous le faites de nouveau flotter.

:::info L’ancrage recouvre l’application, il ne réorganise pas son contenu
craftforJ est dessiné par-dessus la page. Votre application ne se redimensionne pas et aucun de ses éléments ne se déplace pour lui faire de la place. Tout ce qui se trouve sous craftforJ est donc masqué tant qu’il est là. Pour voir ce qui se trouve dessous, déplacez craftforJ vers un autre bord ou faites-le sortir de la page.
:::

![craftforJ ancré à droite d’une page d’application, recouvrant ce bord de l’application](/img/craftforj/getting-started/docking.png#rounded-border)

Pour ne plus recouvrir l’application, déplacez craftforJ hors de la page et placez-le dans sa propre fenêtre ou son propre onglet de navigateur, une disposition qui convient à un deuxième écran. Il continue d’inspecter votre application par l’intermédiaire de la page qui l’a ouvert : laissez donc cette page ouverte. Si vous la quittez ou la fermez, craftforJ n’aura plus rien à inspecter tant que vous n’ouvrirez pas de nouveau l’application.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/undock-window.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

Choisissez un onglet plutôt qu’une fenêtre si vous utilisez la vue partagée de Chrome, qui place votre application et craftforJ côte à côte et n’accepte que de vrais onglets. Cliquez avec le bouton droit sur l’onglet de votre application, ajoutez-le à une nouvelle vue partagée, puis choisissez l’onglet craftforJ.

:::info La vue partagée est une fonctionnalité de Chrome
C’est Chrome, et non craftforJ, qui fournit l’affichage côte à côte. Les autres navigateurs n’ont pas d’équivalent ; craftforJ s’y ouvre donc dans un onglet ordinaire auquel vous pouvez accéder. Le fonctionnement de craftforJ reste le même dans les deux cas.
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/split-view.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

:::tip Déplacer craftforJ pendant que l’assistant rédige
Déplacer craftforJ dans une autre fenêtre met fin à une réponse en cours de génération. craftforJ vous le demande d’abord, et tout ce qui a été rédigé jusque-là reste dans la conversation.
:::

## Effectuer une première modification {#making-a-first-change}

1. Appuyez sur <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> pour commencer à sélectionner un composant.
2. Survolez un élément de votre application et cliquez dessus.
3. L’arborescence sélectionne ce composant et la barre latérale affiche ses propriétés.
4. Modifiez une propriété. L’application en cours d’exécution est mise à jour immédiatement.

La modification ne concerne que l’application affichée devant vous. Vos fichiers restent intacts jusqu’à ce que vous examiniez la modification et l’appliquiez, comme décrit dans [Écrire les modifications dans le code source](/docs/craftforj/source-changes).

![craftforJ ouvert à côté d’une application en cours d’exécution, avec un composant sélectionné](/img/craftforj/getting-started/first-open.png#rounded-border)

Si rien n’apparaît, suivez les étapes de la section [Dépannage](/docs/craftforj/troubleshooting).
