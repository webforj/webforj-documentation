---
title: JRebel
description: >-
  Use JRebel with webforJ to hot-swap modified classes into a running Jetty
  server and skip full restarts during development.
_i18n_hash: 639c97ac6892efd7261824c13b7162da
---
JRebel est un outil de développement Java qui s'intègre au JVM pour détecter les modifications de code et remplacer les classes modifiées directement en mémoire, permettant aux développeurs de voir les changements de code immédiatement sans redémarrer le serveur.

Lorsque des modifications sont apportées à une classe, une méthode ou un champ, JRebel compile et injecte le bytecode mis à jour à la volée, éliminant le besoin d'un redémarrage complet du serveur. En appliquant les modifications directement à l'application en cours d'exécution, JRebel rationalise le flux de travail de développement, économisant du temps et préservant l'état de l'application, y compris les sessions utilisateur.

:::tip Modifications frontend
Les modifications sous `src/main/frontend` sont gérées par le [frontend watch](/docs/configuration/deploy-reload/frontend-watch), qui les reconstruit et actualise le navigateur en même temps que le serveur.
:::

## Installation {#installation}

Le site officiel de JRebel fournit [des instructions de démarrage rapide](https://www.jrebel.com/products/jrebel/learn) pour faire fonctionner le produit dans divers IDE populaires. Suivez ces instructions pour intégrer JRebel dans votre environnement de développement.

Après avoir terminé la configuration, ouvrez un projet webforJ et assurez-vous que la propriété `scan` de jetty dans le fichier `pom.xml` est définie sur `0` pour désactiver le redémarrage automatique du serveur. Une fois cela fait, utilisez la commande suivante :

```bash
mvn jetty:run
```

Si tout est fait correctement, JRebel affichera des informations de journalisation dans le terminal, et les modifications apportées à votre programme devraient se refléter à la demande.

:::info Voir vos modifications
Si une modification est apportée à une vue ou à un composant déjà affiché, JRebel ne forcera pas un rechargement de la page, car le serveur n'est pas redémarré.
:::
