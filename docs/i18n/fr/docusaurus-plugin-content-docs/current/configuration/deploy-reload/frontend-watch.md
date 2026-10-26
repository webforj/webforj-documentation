---
title: Surveillance du frontend
sidebar_position: 20
sidebar_class_name: new-content
description: >-
  Rebuild the sources under src/main/frontend while a webforJ app runs, applying
  stylesheet and image output in place and reloading the view for script output.
_i18n_hash: 8307e05aa7a4c55b75fe8667be1f6b27
---
Le watch frontend reconstruit les sources sous `src/main/frontend` pendant que l'application est en cours d'exécution et envoie la sortie au navigateur. C'est le côté développement du [bundler frontend](/docs/managing-resources/bundler/overview) et nécessite que `webforj.devtools.livereload.enabled` soit activé, voir les [paramètres](/docs/configuration/deploy-reload/overview#settings).

## Exécution du watch {#running-the-watch}

Exécutez l'objectif `watch` avant l'objectif qui démarre l'application. Un projet archétype définit cela comme son objectif par défaut, donc `mvn` sans arguments exécute les deux :

```bash
mvn compile webforj:watch spring-boot:run
```

```bash
mvn compile webforj:watch jetty:run
```

Pour exécuter le watch en tant qu'étape de construction autonome, voir [Construction et tests](/docs/managing-resources/bundler/build-and-tests#the-development-watch).

## Comment la sortie s'applique {#how-the-output-applies}

L'action du navigateur dépend de la sortie produite, pas du fichier modifié :

| Sortie | Action du navigateur |
|---|---|
| Feuille de style, à partir d'une source `.css`, `.scss`, `.sass` ou `.less` | Appliquée sur place. Pas de rechargement, les données de formulaire et la position de défilement restent. |
| Image | Échangée sur place. Pas de rechargement. |
| Toute autre sortie, comme les fichiers compilés `.ts`, `.tsx` ou `.js` | La vue se recharge. |

Lorsque une reconstruction produit plusieurs fichiers, le navigateur les applique sur place uniquement si chaque fichier est valide. Sinon, il se recharge une fois, donc un changement ne s'applique jamais partiellement.

## Pendant un redémarrage du serveur {#during-a-server-restart}

Un changement Java sans outil de [hotswap](/docs/configuration/deploy-reload/hotswap) redémarre le serveur. Lors du redémarrage :

- Les styles appliqués restent sur la page.
- Un indicateur s'affiche pendant que le serveur est hors ligne. Il apparaît uniquement pour un redémarrage, pas pour un rechargement manuel.
- La page se recharge lorsque l'application est prête, pas avant.

Un ajout ou une suppression de `@BundleEntry` prend effet lorsque ce redémarrage est terminé.
