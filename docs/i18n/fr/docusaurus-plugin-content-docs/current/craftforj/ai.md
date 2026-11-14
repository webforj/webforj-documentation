---
title: Assistant IA
sidebar_position: 7
description: >-
  A coding agent that works inside your running webforJ app, writes Java freely
  behind a compile gate, and applies changes with your approval.
_i18n_hash: e9d943043e52eae5368dfe4ba7795142
---
craftforJ inclut un agent de programmation complet qui fonctionne au sein de votre **application en cours d’exécution**. Il écrit librement du Java, compile son code avant même que vous le voyiez, applique les modifications et reprend son travail après le redémarrage de votre application. Tout ce qu’il fait, il le fait sur l’application réellement en cours d’exécution devant vous, et non à partir d’une supposition fondée sur votre dépôt.

<VideoPlayer video="craftforj/ai-conversation" width={1920} height={1000} title="AI conversation" />

:::warning L’IA peut quand même faire des erreurs
Le fait de travailler sur l’application en cours d’exécution et de compiler son propre code rend l’agent nettement plus précis qu’un agent qui travaille à l’aveugle. Il peut tout de même se tromper. Vérifiez son travail avant de le conserver.
:::

## Il écrit du Java {#it-writes-java}

L’agent ne se limite pas aux modifications de propriétés que vous pouvez effectuer manuellement. Décrivez un problème : il écrit le code nécessaire, en ajoutant des méthodes, en modifiant la logique et en restructurant une vue selon les besoins de la tâche.

Chaque modification qu’il écrit est mise en attente plutôt qu’écrite sur le disque. Les modifications en attente sont directement soumises à un véritable compilateur Java. L’agent lit les diagnostics renvoyés et corrige ses propres erreurs avant même que la modification ne vous soit proposée. Le code que vous examinez compile déjà avec votre application en cours d’exécution.

Une validation complète nécessite un JDK. Avec un JRE, craftforJ se rabat sur l’analyse du code, marque la modification comme non vérifiée et indique à l’agent de le préciser au lieu de la présenter comme vérifiée.

L’application d’une modification redémarre votre application. L’agent attend le redémarrage, se reconnecte et reprend son plan là où il l’avait laissé. Ainsi, une tâche comportant plusieurs modifications et redémarrages est menée à bien.

## Il procède par étapes {#it-works-in-steps}

Vous donnez un objectif à l’agent, pas une commande. Il planifie, inspecte ce dont il a besoin, agit, vérifie le résultat et se corrige, en exécutant de nombreuses étapes au cours d’un seul tour sans que vous ayez à le guider à chacune d’elles. Chaque étape apparaît dans la transcription au fur et à mesure. Vous pouvez développer chacune d’elles pour voir précisément les appels effectués par l’agent et les résultats renvoyés.

## Ce à quoi il peut accéder {#what-it-can-reach}

L’agent dispose d’un vaste ensemble d’outils couvrant tout ce que craftforJ connaît de votre application, notamment :

- **Vos composants** - l’arborescence en direct, les valeurs réelles des propriétés et le code Java qui a construit chaque composant. Il peut modifier les propriétés, supprimer des composants et en mettre un en évidence sur la page.
- **Votre code source** - la lecture de tout fichier situé sous la racine de votre projet, la mise en attente de modifications, l’affichage des différences et leur application.
- **Vos routes** - la table de routage, la route active, la navigation vers n’importe quelle destination et la modification des règles d’accès déclarées pour une route.
- **Votre thème et vos styles** - la lecture et la définition des jetons de conception, l’enregistrement d’un thème et la recherche parmi les polices et icônes disponibles.
- **La page elle-même** - l’injection de CSS et de JavaScript dans la page en direct, ainsi que la capture d’écran d’un composant pour l’examiner.
- **La base de connaissances webforJ** - la même documentation, la même interface de stylisation des composants et les mêmes outils de jetons `--dwc-*` que ceux fournis à votre éditeur par le [serveur MCP webforJ](/docs/ai-tooling/mcp). Ils sont intégrés et toujours disponibles.

Comme il accède à tout cela par l’intermédiaire de craftforJ, il dispose des mêmes informations que vous. Il lit les valeurs réelles, et non celles que votre code source laisse supposer.

## Approbations {#approvals}

Vous choisissez à l’avance la marge de manœuvre dont dispose l’agent :

- **Demander avant d’agir** - toute action ayant un effet est soumise à votre approbation.
- **Appliquer automatiquement les modifications** - l’agent travaille librement, mais demande toujours votre autorisation avant de supprimer un élément ou d’exécuter un script.
- **Exécuter de façon autonome** - l’agent travaille sans s’interrompre.

Lorsque l’agent vous demande une autorisation, sa requête apparaît directement dans la transcription, avec l’action qu’il souhaite effectuer. Vous pouvez l’autoriser une seule fois ou pour le reste de la conversation.

![L’assistant demande votre autorisation avant d’agir, directement dans la transcription](/img/craftforj/ai/approval-prompt.png#rounded-border)

Si vous découvrez l’agent, commencez par lui demander de solliciter votre autorisation pour chaque action. Une fois que vous l’aurez vu travailler, le laisser appliquer lui-même ses modifications supprimera la plupart des interruptions, tout en vous laissant les décisions importantes.

## Travailler avec l’application au fil d’une conversation {#working-with-the-app-in-a-conversation}

L’agent lit les informations dont il a besoin au moment où il en a besoin, au lieu de recevoir d’emblée l’ensemble de votre application. craftforJ vous indique ce qui est associé à la conversation. Vous pouvez lui transmettre directement un composant depuis l’arborescence ou en sélectionner un sur la page au cours d’une conversation. Pour répondre à vos questions sur l’apparence d’un élément, l’agent peut en prendre une capture d’écran. Cela nécessite un modèle qui accepte les images.

:::warning Les captures d’écran incluent tout ce qui est affiché à l’écran
Une capture d’écran contient toutes les données que votre application affiche à ce moment-là. Tenez-en compte avant de connecter un modèle hébergé à une application qui utilise des données réelles.
:::

## Configurer un modèle {#configuring-a-model}

craftforJ ne fournit aucun modèle : vous choisissez donc celui qui l’exécute. Ajoutez une clé API pour l’un des fournisseurs pris en charge ou indiquez à craftforJ l’adresse d’un modèle exécuté localement. Votre clé est stockée sur la machine qui exécute votre application, et l’assistant ne la conserve en mémoire que tant que la page est ouverte, jamais dans le stockage du navigateur. Il communique avec le fournisseur que vous avez choisi depuis le navigateur, et non par l’intermédiaire de votre serveur, et ne communique avec aucun autre fournisseur.

Le sélecteur de modèles présente les caractéristiques qui distinguent les modèles, notamment la quantité de contenu de votre application et de votre conversation qu’ils peuvent traiter à la fois, le coût d’une conversation et leur capacité à accepter des images ou à raisonner avant de répondre. Un modèle qui ne peut pas appeler d’outils peut tenir une conversation, mais ne peut ni inspecter ni modifier quoi que ce soit.

![Le sélecteur de modèles présentant les caractéristiques qui distinguent les modèles disponibles](/img/craftforj/ai/model-picker.png#rounded-border)

L’exécution locale d’un modèle garde toutes vos données sur votre machine. Les modèles locaux utilisent souvent par défaut une fenêtre de contexte réduite, vite remplie par une conversation sur une véritable application. Attribuez donc au modèle autant de contexte que votre machine peut en prendre en charge.

## Conversations {#conversations}

Les conversations sont conservées pour chaque application. L’agent peut revenir sur les conversations précédentes lorsqu’une question fait référence à un travail effectué auparavant. Lorsque le contenu d’une conversation dépasse la capacité de contexte du modèle, craftforJ résume les messages plus anciens pour permettre à la tâche de se poursuivre, et le signale dans la conversation.

Lorsque la tâche dépasse les capacités de craftforJ, vous pouvez résumer la conversation et la transmettre à l’assistant de votre éditeur. Cet assistant reprendra le travail avec plus de précision si le [plug-in webforJ AI](/docs/ai-tooling) est installé.

## Le désactiver {#turning-it-off}

La propriété [`ai.enabled`](/docs/craftforj/configuration#feature-flags) supprime complètement l’assistant de craftforJ. La propriété [`ai.freeform-changes`](/docs/craftforj/configuration#feature-flags) conserve l’assistant, mais l’empêche d’écrire lui-même du Java.
