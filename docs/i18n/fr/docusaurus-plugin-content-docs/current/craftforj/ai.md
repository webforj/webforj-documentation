---
title: Assistant IA
sidebar_position: 7
description: >-
  A coding agent that works inside your running webforJ app, writes Java freely
  behind a compile gate, and applies changes with your approval.
_i18n_hash: 863d36cce987eedd9b580968afadcc18
---
craftforJ inclut un agent de codage complet qui fonctionne à l'intérieur de votre **application en cours d'exécution**. Il écrit du Java librement, compile ce qu'il a écrit avant que vous ne le voyiez, applique le changement, et continue de travailler après le redémarrage de votre application. Tout ce qu'il fait, il le fait sur l'application qui s'exécute réellement devant vous, plutôt que sur une supposition faite à partir de votre dépôt.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/ai-conversation.mp4" type="video/mp4" />
  </video>
</div>

:::warning L'IA peut encore faire des erreurs
Travailler contre l'application en cours d'exécution et compiler sa propre sortie rend l'agent considérablement plus précis qu'un agent qui écrit à l'aveugle. Il peut toujours se tromper. Revoyez ce qu'il a fait avant de l'accepter.
:::

## Il écrit du Java {#it-writes-java}

L'agent n'est pas limité aux modifications de propriété que vous pouvez effectuer manuellement. Décrivez un problème, et il écrit le code pour le résoudre, ajoutant des méthodes, modifiant la logique et restructurant une vue selon les besoins de la tâche.

Chaque modification qu'il écrit est mise en attente plutôt que directement écrite sur le disque. Les modifications mises en attente vont directement à un véritable compilateur Java, et l'agent lit les diagnostics qui reviennent et corrige ses propres erreurs avant que le changement ne soit jamais proposé. Ce que vous examinez est du code qui compile déjà contre votre application en cours d'exécution.

Une validation complète nécessite un JDK. Sur un JRE, craftforJ revient à l'analyse du code, marque la modification comme non vérifiée, et indique à l'agent de le signaler plutôt que de le présenter comme vérifié.

Appliquer un changement redémarre votre application. L'agent attend le redémarrage, se reconnecte et reprend là où il s'est arrêté, de sorte qu'une tâche qui s'étend sur plusieurs modifications et redémarrages s'achève.

## Il fonctionne par étapes {#it-works-in-steps}

Vous donnez à l'agent un objectif, pas un commandement. Il planifie, inspecte tout ce dont il a besoin, agit, vérifie le résultat et se corrige, exécutant de nombreuses étapes en un seul tour sans que vous ayez à les diriger. Chaque étape apparaît dans la transcription au fur et à mesure qu'elle se produit, et vous pouvez développer chacune d'elles pour voir exactement ce que l'agent a appelé et ce qui est revenu.

## Ce qu'il peut atteindre {#what-it-can-reach}

L'agent dispose d'un large éventail d'outils couvrant tout ce que craftforJ sait sur votre application, y compris :

- **Vos composants** - l'arbre en direct, les véritables valeurs des propriétés, et le Java qui a construit chacun d'eux. Il peut changer des propriétés, supprimer des composants, et en mettre un en valeur dans la page.
- **Votre source** - lecture de tout fichier sous la racine de votre projet, mise en attente de modifications, affichage des différences et application de celles-ci.
- **Vos routes** - la table de routage, la route active, navigation n'importe où, et changement des règles d'accès déclarées sur une route.
- **Votre thème et vos styles** - lecture et définition des jetons de design, sauvegarde d'un thème, et recherche des polices et icônes disponibles.
- **La page elle-même** - injection de CSS et JavaScript contre la page en direct, et prise d'une capture d'écran d'un composant pour l'examiner.
- **La base de connaissances webforJ** - la même documentation, la surface de stylisation des composants, et les outils de jetons `--dwc-*` que le [serveur MCP webforJ](/docs/ai-tooling/mcp) donne à votre éditeur. C'est intégré et toujours disponible.

Parce qu'il atteint tout cela à travers craftforJ, il fonctionne avec les mêmes informations que vous. Il lit des valeurs réelles, pas celles que votre source implique.

## Approbations {#approvals}

Vous décidez à l'avance combien l'agent peut faire par lui-même :

- **Demander avant d'agir** - chaque action ayant un effet s'arrête pour votre approbation.
- **Appliquer les modifications automatiquement** - l'agent travaille librement mais demande toujours avant de supprimer quelque chose ou d'exécuter un script.
- **Travailler de manière autonome** - l'agent travaille sans s'arrêter.

Lorsque l'agent demande, la demande apparaît en ligne dans la transcription avec l'action qu'il souhaite entreprendre, et vous pouvez lui permettre une fois ou pour le reste de la conversation.

![L'assistant demandant avant d'agir, en ligne dans la transcription](/img/craftforj/ai/approval-prompt.png#rounded-border)

Si vous êtes nouveau avec l'agent, commencez par lui faire demander tout. Une fois que vous l'avez vu travailler, le laisser appliquer ses propres modifications élimine la plupart des interruptions tout en conservant les décisions qui comptent avec vous.

## Travailler avec l'application dans une conversation {#working-with-the-app-in-a-conversation}

L'agent lit ce dont il a besoin au fur et à mesure plutôt que de se voir remettre votre application entière au départ, et craftforJ vous montre ce qui est lié à la conversation. Vous pouvez lui remettre un composant directement depuis l'arbre, ou en choisir un sur la page au milieu d'une conversation. Pour des questions sur l'apparence de quelque chose, l'agent peut prendre une capture d'écran d'un composant. Cela nécessite un modèle qui accepte les images.

:::warning Les captures d'écran incluent tout ce qui est à l'écran
Une capture d'écran contient toutes les données que votre application affiche à ce moment-là. Prenez cela en considération avant de pointer un modèle hébergé vers une application fonctionnant avec de vraies données.
:::

## Configurer un modèle {#configuring-a-model}

craftforJ n'expédie aucun modèle à lui seul, donc vous choisissez celui qui l'exécute. Ajoutez une clé API pour l'un des fournisseurs pris en charge, ou pointez craftforJ vers un modèle fonctionnant localement. Votre clé est stockée sur la machine exécutant votre application, et l'assistant la conserve en mémoire seulement tant que la page est ouverte, jamais dans le stockage du navigateur. Il s'adresse au fournisseur que vous avez choisi depuis le navigateur plutôt que par votre serveur, et à personne d'autre.

Le sélecteur de modèles montre ce qui distingue un modèle d'un autre, y compris combien de votre application et de conversation tient à la fois, combien coûte une conversation, et si le modèle accepte des images ou raisonne avant de répondre. Un modèle qui ne peut pas appeler d'outils peut tenir une conversation mais ne peut inspecter ni changer quoi que ce soit.

![Le sélecteur de modèles montrant ce qui distingue les modèles disponibles](/img/craftforj/ai/model-picker.png#rounded-border)

Exécuter un modèle localement conserve tout sur votre machine. Les modèles locaux ont souvent par défaut une petite fenêtre de contexte, qu'une conversation sur une application réelle remplit rapidement, donc donnez au modèle autant de contexte que votre machine peut porter.

## Conversations {#conversations}

Les conversations sont conservées par application, et l'agent peut revenir sur des précédentes lorsque la question fait référence à un travail que vous avez fait auparavant. Lorsque la conversation dépasse le contexte du modèle, craftforJ résume les messages plus anciens afin que le travail continue plutôt que d'échouer, et note dans le chat qu'il l'a fait.

Lorsque le travail dépasse craftforJ, vous pouvez résumer la conversation et la remettre à l'assistant de votre éditeur. Cet assistant prend le travail de manière plus précise avec le [plugin d'IA webforJ](/docs/ai-tooling) installé.

## L'éteindre {#turning-it-off}

La propriété [`ai.enabled`](/docs/craftforj/configuration#feature-flags) supprime entièrement l'assistant de craftforJ. La propriété [`ai.freeform-changes`](/docs/craftforj/configuration#feature-flags) conserve l'assistant mais l'empêche d'écrire son propre Java.
