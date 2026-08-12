---
title: craftforJ Assistant
sidebar_position: 2
sidebar_class_name: new-content
description: >-
  A coding agent inside your running webforJ app that writes Java freely,
  compiles it, and applies it with your approval.
_i18n_hash: 2c2a04b29b7b6de57e5689628cd659d0
---
L'assistant craftforJ est un agent de codage qui fonctionne à l'intérieur de votre **application en cours d'exécution**. Il écrit du Java librement, compile ce qu'il a écrit avant que vous ne le voyiez, applique le changement, et continue de travailler après le redémarrage de votre application. Il est livré avec webforJ comme partie de [craftforJ](/docs/craftforj), l'environnement de développement qui vous fournit l'arbre de composants, les itinéraires, les propriétés en direct, et la thématique d'une application pendant qu'elle fonctionne.

## Comment les deux se comparent {#how-the-two-compare}

| | [plugin AI webforJ](/docs/ai-tooling) | assistant craftforJ |
|---|---|---|
| **Vit dans** | Votre éditeur | L'application en cours d'exécution |
| **Lit** | Vos fichiers source | Votre application, en direct, avec ses vraies valeurs |
| **Fait** | Écrit du code | Écrit du code, et inspecte, change, navigue, et thématise l'application en cours d'exécution |
| **Vérifie par** | Votre prochaine compilation | Compilation de chaque modification avant que vous ne le voyiez, puis vous montrant le résultat en cours d'exécution |
| **Adapté à** | Construire quelque chose de nouveau à partir de zéro | Comprendre, corriger, construire, et prototyper contre l'application devant vous |

Les deux sont complémentaires et peuvent se transmettre des tâches. Une fois que le travail dépasse craftforJ, vous pouvez [transférer une conversation craftforJ](/docs/craftforj/ai#conversations) à votre éditeur.

## Ce qu'il peut faire {#what-it-can-do}

Vous donnez à l'agent un objectif plutôt qu'une commande. Il planifie, inspecte tout ce dont il a besoin, agit, vérifie le résultat, et se corrige à travers de nombreuses étapes en un seul tour.

Il écrit du Java librement, donc il n'est pas limité aux changements de propriétés que vous pouvez faire à la main. Chaque modification est mise en attente plutôt que écrite sur le disque, envoyée à un vrai compilateur Java, et corrigée par l'agent en fonction des diagnostics qui reviennent, de sorte que ce qui atteint votre révision compile déjà contre votre application en cours d'exécution. L'application redémarre, et l'agent reprend son plan une fois qu'elle est de nouveau opérationnelle.

De plus, il atteint tout ce que craftforJ sait : l'arbre de composants en direct et les vraies valeurs des propriétés, votre source Java, la table de routage et les règles d'accès aux itinéraires, le thème et la feuille de style, la page elle-même pour le CSS et les scripts, des captures d'écran d'un composant, et la base de connaissances webforJ ainsi que les outils de jetons `--dwc-*` intégrés. Consultez [Assistant AI](/docs/craftforj/ai) pour plus de détails.

## Configurer un modèle {#configuring-a-model}

craftforJ n'expédie pas de modèle propre, donc vous choisissez celui qui l'exécute. Ajoutez une clé API pour l'un des fournisseurs pris en charge, ou pointez craftforJ vers un modèle s'exécutant localement avec Ollama. Votre clé est stockée sur la machine exécutant votre application et est conservée dans le navigateur uniquement tant que la page est ouverte, et l'assistant discute avec votre fournisseur depuis le navigateur plutôt que via votre serveur. Voir [Configurer un modèle](/docs/craftforj/ai#configuring-a-model).

:::warning L'IA peut encore faire des erreurs
Travailler contre l'application en cours d'exécution et compiler sa propre sortie rend l'agent considérablement plus précis qu'un agent qui écrit à l'aveugle. Il peut encore se tromper. Vérifiez ce qu'il a fait avant de l'adopter.
:::

## Commencer {#getting-started}

craftforJ est désactivé jusqu'à ce que vous l'activiez, et il fonctionne uniquement en développement :

```ini title="webforj.conf"
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

Ouvrez craftforJ avec <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> et passez à l'onglet AI Assistant. Pour la configuration complète, voir [Commencer](/docs/craftforj/getting-started).
