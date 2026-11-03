---
title: Commencer
sidebar_position: 2
description: >-
  Add the devtools dependency, enable craftforJ in your configuration, and open
  craftforJ over a running webforJ app.
_i18n_hash: fb5860ba7349ef5523c2dfb6bc425d9b
---
<DocChip chip='since' label='26.02' />

craftforJ est fourni avec webforJ, il n'y a donc rien à télécharger séparément. Cette page couvre ce dont votre application a besoin avant que craftforJ n'apparaisse et comment l'ouvrir.

:::tip Déjà activé dans les projets générés
Les projets créés avec [startforJ](https://docs.webforj.com/startforj) ou à partir d'un [archétype](/docs/building-ui/archetypes/overview) de webforJ viennent avec craftforJ activé. Si vous avez commencé à partir de l'un d'eux, exécutez votre application et passez directement à [Ouverture de craftforJ](#opening-craftforj).
:::

## Exigences {#requirements}

craftforJ s'attache à une application uniquement lorsque toutes les conditions suivantes sont remplies. Si l'une d'elles n'est pas satisfaite, rien n'apparaît sur la page.

### Ajoutez la dépendance {#add-the-dependency}

Ajoutez `webforj-devtools` à votre projet s'il n'y est pas déjà :

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

Ouvrez l'application depuis la machine qui l'exécute, et assurez-vous d'avoir une licence développeur valide. Pour accéder à craftforJ depuis une autre machine, ajoutez son adresse à [`hosts-allowed`](/docs/craftforj/configuration#access).

Une fois ces éléments en place, redémarrez l'application et rechargez la page.

## Ouverture de craftforJ {#opening-craftforj}

Lorsque craftforJ est actif, un bouton de déclenchement apparaît au-dessus de votre application. Cliquez dessus pour ouvrir craftforJ, ou appuyez sur <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> depuis n'importe où dans l'application. Le même raccourci ferme à nouveau craftforJ, et vous pouvez faire glisser le déclencheur vers le coin qui vous convient.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/getting-started.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

Ses onglets couvrent l'[arbre des composants](/docs/craftforj/inspector), les [routes](/docs/craftforj/routes), le [thème](/docs/craftforj/theme), et l'[assistant](/docs/craftforj/ai). Les paramètres et les informations de l'application se trouvent à côté d'eux.


- **Le déclencheur** est le bouton qui ouvre et ferme craftforJ. Il reste à l'écart pendant que craftforJ est fermé.
- **La bande des onglets** s'étend le long du bord le plus proche de l'application et permet de passer d'une vue à l'autre dans craftforJ.
- **Le menu de la fenêtre** contient tout ce qui concerne l'emplacement de craftforJ, couvert dans [Où se trouve craftforJ](#where-craftforj-sits).

:::info Raccourcis sur macOS
craftforJ écrit chaque raccourci en utilisant les modificateurs de la plateforme sur laquelle vous vous trouvez, donc <kbd>Alt</kbd> apparaît comme <kbd>⌥</kbd> et <kbd>Ctrl</kbd> comme <kbd>⌘</kbd>. Appuyez sur <kbd>Shift</kbd> + <kbd>?</kbd> dans craftforJ pour voir la liste actuelle.
:::

## Où se trouve craftforJ {#where-craftforj-sits}

craftforJ flotte par défaut au-dessus de votre application. Faites-le glisser n'importe où sur la page, redimensionnez-le depuis n'importe quel bord, et minimisez-le de nouveau sur son déclencheur lorsque vous souhaitez avoir l'application pour vous seul. Si vous le faites glisser vers un bord de la page, il y est amarré, avec une hauteur ou une largeur complètes, et chaque bord conserve la taille que vous lui avez donnée. En le faisant glisser loin du bord, il flotte à nouveau.

:::info L'amarrage couvre l'application, il ne la recompose pas
craftforJ est dessiné au-dessus de la page. Votre application ne change pas de taille, et rien à l'intérieur ne bouge de place, donc tout ce qui se trouve sous craftforJ est caché pendant qu'il est là. Pour voir ce qu'il y a en dessous, déplacez craftforJ vers un autre bord ou retirez-le de la page.
:::

![craftforJ ancré à droite d'une page d'application, couvrant ce bord de l'application](/img/craftforj/getting-started/docking.png#rounded-border)

Pour ne plus couvrir l'application du tout, déplacez craftforJ hors de la page et dans une fenêtre ou un onglet de navigateur à part, ce qui convient à un deuxième moniteur. Il inspecte toujours votre application à travers la page qui l'a ouvert, donc laissez cette page ouverte. Si vous naviguez loin ou la fermez, craftforJ n'a plus rien à inspecter jusqu'à ce que vous ouvriez à nouveau l'application.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/undock-window.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

Choisissez un onglet plutôt qu'une fenêtre si vous utilisez la vue fractionnée de Chrome, qui place votre application et craftforJ côte à côte et accepte uniquement de véritables onglets. Faites un clic droit sur l'onglet de votre application, ajoutez-le à une nouvelle vue fractionnée, puis sélectionnez l'onglet craftforJ.

:::info La vue fractionnée est une fonctionnalité de Chrome
Chrome fournit l'agencement côte à côte, pas craftforJ. D'autres navigateurs n'ont pas d'équivalent, donc craftforJ dans d'autres navigateurs s'ouvre dans un onglet ordinaire auquel vous passez. craftforJ lui-même fonctionne de la même manière dans les deux cas.
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/split-view.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

:::tip Déplacement pendant que l'assistant écrit
Déplacer craftforJ dans une autre fenêtre met fin à une réponse qui est encore en cours de diffusion. craftforJ demande d'abord, et tout ce qui a été écrit jusqu'à ce moment reste dans le chat.
:::

## Faire un premier changement {#making-a-first-change}

1. Appuyez sur <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> pour commencer à choisir un composant.
2. Survolez quelque chose dans votre application et cliquez dessus.
3. L'arbre sélectionne ce composant, et la barre latérale se remplit de ses propriétés.
4. Changez une propriété. L'application en cours s'actualise immédiatement.

Le changement n'affecte que l'application devant vous. Vos fichiers restent intacts jusqu'à ce que vous examiniez le changement et l'appliquiez, ce qui est couvert dans [Écriture des changements dans le code source](/docs/craftforj/source-changes).

![craftforJ ouvert à côté d'une application en cours d'exécution avec un composant sélectionné](/img/craftforj/getting-started/first-open.png#rounded-border)

Si rien n'apparaît, suivez [Dépannage](/docs/craftforj/troubleshooting).
