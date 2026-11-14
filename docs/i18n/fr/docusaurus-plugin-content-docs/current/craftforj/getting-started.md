---
title: Pour commencer
sidebar_position: 2
description: >-
  Add the devtools dependency, enable craftforJ in your configuration, and open
  craftforJ over a running webforJ app.
_i18n_hash: 5d30f5dd80df008a05be5d4617c40369
---
<!-- vale off -->
# Premiers pas <DocChip chip='since' label='26.02' />
<!-- vale on -->

craftforJ est fourni avec webforJ, vous n’avez donc rien à télécharger séparément. Cette page présente les prérequis nécessaires pour que craftforJ s’affiche dans votre application, ainsi que la façon de l’ouvrir.

:::tip Déjà activé dans les projets générés
Les projets créés avec [startforJ](https://docs.webforj.com/startforj) ou à partir d’un [archétype](/docs/building-ui/archetypes/overview) webforJ sont fournis avec craftforJ activé. Si vous êtes parti de l’un d’eux, lancez votre application et passez directement à [Ouvrir craftforJ](#opening-craftforj).
:::

## Prérequis {#requirements}

craftforJ se connecte à une application uniquement si toutes les conditions suivantes sont remplies. Si l’une d’elles ne l’est pas, rien ne s’affiche sur la page.

### Ajouter la dépendance {#add-the-dependency}

Ajoutez `webforj-devtools` à votre projet si ce n’est pas déjà fait :

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-devtools</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### Mode débogage et indicateur craftforJ {#debug-mode-and-the-craftforj-flag}

Ajoutez les propriétés suivantes à votre projet. Pour une application webforJ standard, définissez-les dans `webforj.conf`. Pour un projet webforJ utilisant [Spring](/docs/integrations/spring/overview), définissez-les dans `application.properties`.

```ini
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

craftforJ ne fonctionne que lorsque les deux propriétés sont activées ; ainsi, une application déployée en production avec le mode débogage activé n’expose pas votre arborescence de sources.

### Un navigateur local et une licence développeur {#a-local-browser-and-a-developer-license}

Ouvrez l’application depuis la machine qui l’exécute et assurez-vous de disposer d’une licence développeur valide. Pour accéder à craftforJ depuis une autre machine, ajoutez son adresse à [`hosts-allowed`](/docs/craftforj/configuration#access).

Une fois ces conditions réunies, redémarrez l’application et actualisez la page.

## Ouvrir craftforJ {#opening-craftforj}

Lorsque craftforJ est actif, un bouton d’activation apparaît par-dessus votre application. Cliquez dessus pour ouvrir craftforJ, ou appuyez sur <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> depuis n’importe où dans l’application. Le même raccourci ferme craftforJ. Vous pouvez également faire glisser le bouton d’activation vers le coin de votre choix.

<VideoPlayer video="craftforj/getting-started" width={1920} height={984} title="Getting started" />

Ses onglets donnent accès à [l’arborescence des composants](/docs/craftforj/inspector), aux [routes](/docs/craftforj/routes), au [thème](/docs/craftforj/theme) et à [l’assistant](/docs/craftforj/ai). Les paramètres et les informations sur l’application se trouvent à côté.


- **Le bouton d’activation** ouvre et ferme craftforJ. Il reste discret lorsque craftforJ est fermé.
- **La barre d’onglets** longe le bord le plus proche de l’application et permet de choisir ce que craftforJ affiche.
- **Le menu de la fenêtre** regroupe les options de positionnement de craftforJ, présentées dans [Positionnement de craftforJ](#where-craftforj-sits).

:::info Raccourcis sur macOS
craftforJ adapte l’affichage des raccourcis aux touches modificatrices de la plateforme utilisée. Ainsi, <kbd>Alt</kbd> apparaît sous la forme <kbd>⌥</kbd> et <kbd>Ctrl</kbd> sous la forme <kbd>⌘</kbd>. Appuyez sur <kbd>Shift</kbd> + <kbd>?</kbd> dans craftforJ pour afficher la liste actuelle.
:::

## Positionnement de craftforJ {#where-craftforj-sits}

Par défaut, craftforJ flotte au-dessus de votre application. Faites-le glisser n’importe où sur la page, redimensionnez-le depuis n’importe quel bord et réduisez-le au bouton d’activation lorsque vous souhaitez utiliser l’application sans craftforJ. Faites-le glisser vers le bord de la page pour l’y ancrer, sur toute la hauteur ou toute la largeur ; chaque bord conserve la taille que vous lui avez attribuée. Faites-le glisser loin du bord pour qu’il flotte à nouveau.

:::info L’ancrage recouvre l’application sans réorganiser sa mise en page
craftforJ est affiché au-dessus de la page. La taille de votre application ne change pas et aucun élément ne se déplace pour lui faire de la place ; le contenu situé sous craftforJ est donc masqué tant qu’il se trouve là. Pour voir ce qui se trouve dessous, déplacez craftforJ vers un autre bord ou sortez-le de la page.
:::

![craftforJ ancré à droite d’une page d’application, recouvrant ce bord de l’application](/img/craftforj/getting-started/docking.png#rounded-border)

Pour ne plus recouvrir l’application, sortez craftforJ de la page et placez-le dans une fenêtre ou un onglet de navigateur distinct, une configuration adaptée à un deuxième écran. craftforJ continue d’inspecter votre application via la page qui l’a ouvert ; laissez donc cette page ouverte. Si vous quittez cette page ou la fermez, craftforJ n’aura plus rien à inspecter jusqu’à ce que vous rouvriez l’application.

<VideoPlayer video="craftforj/undock-window" width={1920} height={1048} title="Undock window" />

Choisissez un onglet plutôt qu’une fenêtre si vous utilisez l’affichage partagé de Chrome, qui place votre application et craftforJ côte à côte et n’accepte que de vrais onglets. Faites un clic droit sur l’onglet de votre application, ajoutez-le à un nouvel affichage partagé, puis sélectionnez l’onglet craftforJ.

:::info L’affichage partagé est une fonctionnalité de Chrome
C’est Chrome, et non craftforJ, qui permet cette disposition côte à côte. Les autres navigateurs ne proposent pas d’équivalent ; dans ces navigateurs, craftforJ s’ouvre dans un onglet ordinaire auquel vous pouvez accéder. Le fonctionnement de craftforJ est le même dans les deux cas.
:::

<VideoPlayer video="craftforj/split-view" width={1920} height={1080} title="Split view" />

:::tip Déplacer craftforJ pendant que l’assistant rédige
Déplacer craftforJ dans une autre fenêtre interrompt une réponse en cours de génération. craftforJ vous le demande au préalable, et tout ce qui a été rédigé jusque-là reste dans la conversation.
:::

## Effectuer une première modification {#making-a-first-change}

1. Appuyez sur <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> pour commencer à sélectionner un composant.
2. Survolez un élément de votre application et cliquez dessus.
3. L’arborescence sélectionne ce composant et la barre latérale affiche ses propriétés.
4. Modifiez une propriété. L’application en cours d’exécution est mise à jour immédiatement.

La modification ne concerne que l’application affichée devant vous. Vos fichiers restent inchangés tant que vous n’avez pas examiné et appliqué la modification, comme expliqué dans [Écrire les modifications dans le code source](/docs/craftforj/source-changes).

![craftforJ ouvert à côté d’une application en cours d’exécution, avec un composant sélectionné](/img/craftforj/getting-started/first-open.png#rounded-border)

Si rien ne s’affiche, suivez les étapes de la section [Dépannage](/docs/craftforj/troubleshooting).
