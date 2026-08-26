---
title: Prise en main
sidebar_position: 2
description: >-
  Add the devtools dependency, enable craftforJ in your configuration, and open
  craftforJ over a running webforJ app.
_i18n_hash: 81825a3ba8656a8aee4820dee71da732
---
<DocChip chip='since' label='26.02' />

craftforJ est livré avec webforJ, donc il n'y a rien à télécharger séparément. Cette page couvre ce dont votre application a besoin avant que craftforJ n'apparaisse, et comment l'ouvrir.

:::tip Déjà activé dans les projets générés
Les projets créés avec [startforJ](https://docs.webforj.com/startforj) ou à partir d'un [archétype](/docs/building-ui/archetypes/overview) webforJ sont livrés avec craftforJ activé. Si vous avez commencé à partir de l'un d'eux, exécutez votre application et passez directement à [Ouverture de craftforJ](#opening-craftforj).
:::

## Exigences {#requirements}

craftforJ s'attache à une application uniquement lorsque toutes les conditions suivantes sont remplies. Si l'une d'elles n'est pas satisfaite, rien n'apparaît sur la page.

### Ajouter la dépendance {#add-the-dependency}

Ajoutez `webforj-devtools` à votre projet s'il n'est pas déjà présent :

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-devtools</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### Mode débogage et le drapeau craftforJ {#debug-mode-and-the-craftforj-flag}

Ajoutez les propriétés suivantes à votre projet. Si vous avez une application webforJ standard, définissez les propriétés dans `webforj.conf`. Pour un projet webforJ utilisant [Spring](/docs/integrations/spring/overview), définissez les propriétés dans `application.properties`.

```ini
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

craftforJ ne fonctionne que lorsque les deux propriétés sont activées ; par conséquent, une application qui passe en production avec le mode débogage activé n'expose pas votre arbre source.

### Un navigateur local et une licence développeur {#a-local-browser-and-a-developer-license}

Ouvrez l'application depuis la machine qui l'exécute et assurez-vous d'avoir une licence développeur valide. Pour accéder à craftforJ depuis une autre machine, ajoutez son adresse à [`hosts-allowed`](/docs/craftforj/configuration#access).

Une fois ces éléments en place, redémarrez l'application et rechargez la page.

## Ouverture de craftforJ {#opening-craftforj}

Lorsque craftforJ est actif, un bouton de déclenchement apparaît sur votre application. Cliquez dessus pour ouvrir craftforJ, ou appuyez sur <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> de n'importe où dans l'application. Le même raccourci ferme à nouveau craftforJ, et vous pouvez faire glisser le déclencheur vers le coin qui vous convient.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/getting-started.mp4" type="video/mp4" />
  </video>
</div>

Ses onglets couvrent l'[arbre des composants](/docs/craftforj/inspector), [les routes](/docs/craftforj/routes), le [thème](/docs/craftforj/theme), et l'[assistant](/docs/craftforj/ai). Les paramètres et les informations sur l'application se trouvent à côté d'eux.

- **Le déclencheur** est le bouton qui ouvre et ferme craftforJ. Il reste discret pendant que craftforJ est fermé.
- **La barre d'onglets** s'étend le long du bord le plus proche de l'application et permet de passer entre ce que craftforJ vous montre.
- **Le menu de la fenêtre** contient tout ce qui concerne l'emplacement de craftforJ, couvert dans [Où se trouve craftforJ](#where-craftforj-sits).

:::info Raccourcis sur macOS
craftforJ écrit chaque raccourci en utilisant les modificateurs de la plateforme sur laquelle vous êtes, donc <kbd>Alt</kbd> apparaît comme <kbd>⌥</kbd> et <kbd>Ctrl</kbd> comme <kbd>⌘</kbd>. Appuyez sur <kbd>Shift</kbd> + <kbd>?</kbd> dans craftforJ pour voir la liste actuelle.
:::

## Où se trouve craftforJ {#where-craftforj-sits}

craftforJ flotte par défaut au-dessus de votre application. Faites-le glisser n'importe où sur la page, redimensionnez-le depuis n'importe quel bord et minimisez-le sur son déclencheur lorsque vous souhaitez vous approprier l'application. Le faire glisser sur un bord de la page le fixe là, en pleine hauteur ou pleine largeur, et chaque bord conserve la taille que vous lui avez donnée. Le faire glisser loin du bord le fait flotter à nouveau.

:::info Le docking couvre l'application, il ne la réorganise pas
craftforJ est dessiné par-dessus la page. Votre application ne se redimensionne pas, et rien dedans ne se déplace pour faire de la place, donc tout ce qui se trouve sous craftforJ est caché pendant qu'il est présent. Pour voir ce qui se trouve en dessous, déplacez craftforJ vers un autre bord ou retirez-le de la page.
:::

![craftforJ ancré à droite d'une page d'application, couvrant ce bord de l'application](/img/craftforj/getting-started/docking.png#rounded-border)

Pour ne plus couvrir l'application du tout, déplacez craftforJ hors de la page et dans une fenêtre ou un onglet de navigateur à part, ce qui convient à un second moniteur. Il inspecte toujours votre application via la page qui l'a ouvert, donc laissez cette page ouverte. Si vous la naviguez ailleurs ou la fermez, craftforJ n'a rien de plus à inspecter jusqu'à ce que vous ouvriez à nouveau l'application.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/undock-window.mp4" type="video/mp4" />
  </video>
</div>

Choisissez un onglet plutôt qu'une fenêtre si vous utilisez la vue fractionnée de Chrome, qui met votre application et craftforJ côte à côte et n'accepte que de véritables onglets. Cliquez avec le bouton droit sur l'onglet de votre application, ajoutez-le à une nouvelle vue fractionnée, puis sélectionnez l'onglet craftforJ.

:::info La vue fractionnée est une fonctionnalité de Chrome
Chrome fournit l'arrangement côte à côte, pas craftforJ. D'autres navigateurs n'ont pas d'équivalent, donc craftforJ dans d'autres navigateurs s'ouvre dans un onglet ordinaire auquel vous devez passer. craftforJ lui-même fonctionne de la même manière de toute façon.
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/split-view.mp4" type="video/mp4" />
  </video>
</div>

:::tip Déplacement pendant que l'assistant écrit
Déplacer craftforJ dans une autre fenêtre met fin à une réponse qui est encore en cours. craftforJ demande d'abord, et tout ce qui a été écrit jusqu'à ce moment reste dans le chat.
:::

## Apporter un premier changement {#making-a-first-change}

1. Appuyez sur <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> pour commencer à sélectionner un composant.
2. Survolez quelque chose dans votre application et cliquez dessus.
3. L'arbre sélectionne ce composant, et la barre latérale se remplit de ses propriétés.
4. Modifiez une propriété. L'application en cours se met à jour immédiatement.

Le changement n'affecte que l'application devant vous. Vos fichiers restent intacts jusqu'à ce que vous examiniez le changement et l'appliquiez, ce qui est couvert dans [Écriture des changements dans la source](/docs/craftforj/source-changes).

![craftforJ ouvert à côté d'une application en cours d'exécution avec un composant sélectionné](/img/craftforj/getting-started/first-open.png#rounded-border)

Si rien n'apparaît du tout, parcourez [Dépannage](/docs/craftforj/troubleshooting).
