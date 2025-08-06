---
title: JRebel
_i18n_hash: 9e2b7ce994eb40e656cf61dc4967ec7e
---
JRebel est un outil de développement Java qui s'intègre à la JVM pour détecter les modifications de code et remplacer les classes modifiées directement dans la mémoire, permettant ainsi aux développeurs de voir les changements de code immédiatement sans redémarrer le serveur.

Lorsqu'un changement est apporté à une classe, une méthode ou un champ, JRebel compile et injecte le bytecode mis à jour à la volée, éliminant ainsi le besoin d'un redémarrage complet du serveur. En appliquant les modifications directement à l'application en cours d'exécution, JRebel rationalise le flux de développement, économisant du temps et préservant l'état de l'application, y compris les sessions utilisateur.

## Installation {#installation}

Le site officiel de JRebel fournit [des instructions de démarrage rapide](https://www.jrebel.com/products/jrebel/learn) pour mettre le produit en marche dans divers IDE populaires. Suivez ces instructions pour intégrer JRebel dans votre environnement de développement.

Après la configuration, ouvrez un projet webforJ et assurez-vous que la propriété `scan` de jetty dans le fichier `pom.xml` est définie sur `0` pour désactiver le redémarrage automatique du serveur. Une fois cela fait, utilisez la commande suivante :

```bash
mvn jetty:run
```

Si cela est fait correctement, JRebel affichera des informations de journalisation dans le terminal, et les modifications apportées à votre programme devraient se refléter à la demande.

:::info Voir vos changements
Si un changement est apporté à une vue ou à un composant déjà affiché, JRebel ne forcera pas le rechargement de la page, car le serveur n'est pas redémarré.
:::
