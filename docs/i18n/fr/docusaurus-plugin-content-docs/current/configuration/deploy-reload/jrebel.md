---
title: JRebel
_i18n_hash: e0a60884cfab5835f788e6f225047d2c
---
JRebel est un outil de développement Java qui s'intègre à la JVM pour détecter les modifications de code et remplacer les classes modifiées directement en mémoire, permettant aux développeurs de voir les changements de code immédiatement sans redémarrer le serveur.

Lorsqu'une modification est apportée à une classe, une méthode ou un champ, JRebel compile et injecte le bytecode mis à jour à la volée, éliminant ainsi le besoin d'un redémarrage complet du serveur. En appliquant les modifications directement à l'application en cours d'exécution, JRebel simplifie le flux de travail de développement, faisant gagner du temps et préservant l'état de l'application, y compris les sessions utilisateur.

## Installation {#installation}

Le site officiel de JRebel fournit [des instructions de démarrage rapide](https://www.jrebel.com/products/jrebel/learn) pour mettre le produit en service dans divers IDE populaires. Suivez ces instructions pour intégrer JRebel dans votre environnement de développement.

Une fois la configuration terminée, ouvrez un projet webforJ et assurez-vous que la propriété `scan` de jetty dans le fichier `pom.xml` est définie sur `0` pour désactiver le redémarrage automatique du serveur. Une fois cela fait, utilisez la commande suivante :

```bash
mvn jetty:run
```

Si cela est bien fait, JRebel affichera des informations de journalisation dans le terminal, et les modifications apportées à votre programme devraient se refléter à la demande.

:::info Voir vos modifications
Si une modification est apportée à une vue ou à un composant déjà affiché, JRebel ne forcera pas un rechargement de la page, car le serveur n'est pas redémarré.
:::
