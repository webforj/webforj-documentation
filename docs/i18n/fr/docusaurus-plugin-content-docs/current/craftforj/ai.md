---
title: AI Assistant
sidebar_position: 7
description: >-
  A coding agent that works inside your running webforJ app, writes Java freely
  behind a compile gate, and applies changes with your approval.
_i18n_hash: 716e267ebccf57080f5238335670d1b7
---
craftforJ inclut un agent de codage complet qui fonctionne à l'intérieur de votre **application en cours d'exécution**. Il écrit du Java librement, compile ce qu'il a écrit avant même que vous ne le voyiez, applique le changement et continue de fonctionner après le redémarrage de votre application. Tout ce qu'il fait, il le fait contre l'application qui s'exécute réellement devant vous, plutôt que contre une supposition faite à partir de votre référentiel.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/ai-conversation.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

:::warning L'IA peut encore faire des erreurs
Travailler contre l'application en cours d'exécution et compiler sa propre sortie rend l'agent considérablement plus précis que celui qui écrit à l'aveugle. Il peut encore se tromper. Revoyez ce qu'il a fait avant de le conserver.
:::

## Il écrit du Java {#it-writes-java}

L'agent n'est pas limité aux modifications de propriété que vous pouvez effectuer à la main. Décrivez un problème et il écrit le code pour cela, ajoutant des méthodes, modifiant la logique et restructurant une vue selon les exigences de la tâche.

Chaque modification qu'il écrit est en attente plutôt qu'écrite sur le disque. Les modifications en attente vont directement à un vrai compilateur Java, et l'agent lit les diagnostics qui reviennent et corrige ses propres erreurs avant que le changement ne soit jamais proposé. Ce qui atteint votre révision est du code qui se compile déjà contre votre application en cours d'exécution.

Une validation complète nécessite un JDK. Sur un JRE, craftforJ revient à l'analyse du code, marque la modification comme non vérifiée et demande à l'agent de le dire plutôt que de la présenter comme vérifiée.

Appliquer un changement redémarre votre application. L'agent attend le redémarrage, se reconnecte et reprend son plan là où il s'était arrêté, de sorte qu'une tâche qui s'étend sur plusieurs modifications et redémarrages s'exécute jusqu'à son achèvement.

## Il travaille par étapes {#it-works-in-steps}

Vous donnez à l'agent un objectif, pas un ordre. Il planifie, inspecte ce dont il a besoin, agit, vérifie le résultat et se corrige, en exécutant de nombreuses étapes en un seul tour sans que vous ayez à piloter chacune d'entre elles. Chaque étape apparaît dans la transcription au fur et à mesure qu'elle se produit, et vous pouvez développer chacune d'elles pour voir exactement ce que l'agent a appelé et ce qui est revenu.

## Ce qu'il peut atteindre {#what-it-can-reach}

L'agent dispose d'une grande boîte à outils couvrant tout ce que craftforJ sait de votre application, incluant :

- **Vos composants** - l'arbre en direct, les vraies valeurs de propriété et le Java qui a construit chacun d'eux. Il peut changer des propriétés, supprimer des composants et en mettre un en surbrillance dans la page.
- **Votre code source** - lisant n'importe quel fichier sous la racine de votre projet, mettant en attente des modifications, montrant des différences et les appliquant.
- **Vos routes** - le tableau de routage, la route active, naviguant n'importe où et changeant les règles d'accès déclarées sur une route.
- **Votre thème et vos styles** - lisant et définissant des jetons de conception, enregistrant un thème et recherchant les polices et icônes disponibles.
- **La page elle-même** - injectant du CSS et du JavaScript contre la page en direct, et prenant une capture d'écran d'un composant pour l'analyser.
- **La base de connaissances de webforJ** - la même documentation, surface de stylisation des composants et outils de jetons `--dwc-*` que le [serveur MCP de webforJ](/docs/ai-tooling/mcp) fournit à votre éditeur. C'est intégré et toujours disponible.

Parce qu'il accède à tout cela via craftforJ, il fonctionne avec les mêmes informations que vous. Il lit des valeurs réelles, pas celles que votre source implique.

## Approbations {#approvals}

Vous décidez à l'avance combien l'agent peut faire de manière autonome :

- **Demander avant d'agir** - chaque action ayant un effet s'arrête pour votre approbation.
- **Appliquer des modifications automatiquement** - l'agent fonctionne librement mais demande toujours avant de supprimer quelque chose ou d'exécuter un script.
- **Fonctionner de manière autonome** - l'agent travaille sans interruption.

Lorsque l'agent demande, la demande apparaît en ligne dans la transcription avec l'action qu'il souhaite effectuer, et vous pouvez lui permettre de le faire une fois ou pour le reste de la conversation.

![L'assistant demandant avant d'agir, en ligne dans la transcription](/img/craftforj/ai/approval-prompt.png#rounded-border)

Si vous êtes nouveau avec l'agent, commencez par lui faire demander tout. Une fois que vous l'avez vu à l'œuvre, lui laisser appliquer ses propres modifications élimine la plupart des interruptions tout en conservant les décisions importantes avec vous.

## Travailler avec l'application dans une conversation {#working-with-the-app-in-a-conversation}

L'agent lit ce dont il a besoin au fur et à mesure de ses besoins plutôt que de recevoir l'intégralité de votre application d'un coup, et craftforJ vous montre ce qui est lié à la conversation. Vous pouvez lui donner un composant directement depuis l'arbre, ou en choisir un sur la page au milieu d'une conversation. Pour des questions concernant l'apparence de quelque chose, l'agent peut prendre une capture d'écran d'un composant. Cela nécessite un modèle qui accepte les images.

:::warning Les captures d'écran incluent tout ce qui est à l'écran
Une capture d'écran contient toute donnée que votre application affiche à ce moment-là. Considérez cela avant de pointer un modèle hébergé vers une application fonctionnant avec des données réelles.
:::

## Configurer un modèle {#configuring-a-model}

craftforJ ne livre aucun modèle de son propre chef, vous choisissez donc celui qui l'exécute. Ajoutez une clé d'API pour l'un des fournisseurs pris en charge, ou pointez craftforJ vers un modèle fonctionnant localement. Votre clé est stockée sur la machine exécutant votre application, et l'assistant la conserve en mémoire uniquement tant que la page est ouverte, jamais dans le stockage du navigateur. Il parle au fournisseur que vous avez choisi depuis le navigateur plutôt que par l'intermédiaire de votre serveur, et à personne d'autre.

Le sélecteur de modèles montre ce qui distingue un modèle d'un autre, y compris combien de votre application et conversation tient à la fois, le coût d'une conversation, et si le modèle accepte des images ou réfléchit avant de répondre. Un modèle qui ne peut pas appeler d'outils peut tenir une conversation mais ne peut pas inspecter ou modifier quoi que ce soit.

![Le sélecteur de modèles montrant ce qui distingue les modèles disponibles](/img/craftforj/ai/model-picker.png#rounded-border)

Exécuter un modèle localement conserve tout sur votre machine. Les modèles locaux ont souvent une fenêtre de contexte par défaut réduite, que remplit rapidement une conversation sur une application réelle, donc donnez au modèle autant de contexte que votre machine peut supporter.

## Conversations {#conversations}

Les conversations sont conservées par application, et l'agent peut revenir sur des précédentes lorsqu'une question se réfère à un travail que vous avez fait avant. Lorsque la conversation dépasse le contexte du modèle, craftforJ résume les messages plus anciens afin que le travail continue plutôt que d'échouer, et le note dans le chat.

Lorsque le travail excède craftforJ, vous pouvez résumer la conversation et la transmettre à l'assistant de votre éditeur. Cet assistant reprend le travail plus précisément avec le [plugin AI de webforJ](/docs/ai-tooling) installé.

## Désactiver {#turning-it-off}

La propriété [`ai.enabled`](/docs/craftforj/configuration#feature-flags) supprime complètement l'assistant de craftforJ. La propriété [`ai.freeform-changes`](/docs/craftforj/configuration#feature-flags) conserve l'assistant mais empêche celui-ci d'écrire du Java de son propre chef.
