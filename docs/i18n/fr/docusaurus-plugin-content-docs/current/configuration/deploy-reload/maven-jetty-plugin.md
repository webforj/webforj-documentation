---
title: Maven Jetty plugin
description: >-
  Tune the Maven Jetty plugin scan interval and webforJ reload properties to
  enable hot redeployment during webforJ development.
_i18n_hash: 7b8efc852651f2bea9db4ca110fe11ed
---
Le plugin Maven Jetty est un outil populaire permettant aux développeurs d'exécuter des applications web Java au sein d'un serveur Jetty intégré directement à partir de leurs projets Maven.

Le plugin Jetty lance un serveur Jetty intégré qui surveille les fichiers de votre application, y compris les classes Java et les ressources, à la recherche de modifications. Lorsqu'il détecte des mises à jour, il redéploie automatiquement l'application, ce qui accélère le développement en éliminant les étapes manuelles de construction et de déploiement.

:::tip Modifications frontend
Les modifications sous `src/main/frontend` sont gérées par le [frontend watch](/docs/configuration/deploy-reload/frontend-watch), qui les reconstruit et actualise le navigateur en même temps que le serveur.
:::

## Configurations Jetty {#jetty-configurations}

Voici quelques configurations essentielles pour affiner les paramètres de déploiement à chaud et d'interaction avec le serveur du plugin :

| Propriété                          | Description                                                                                                                                                                           | Par défaut     |
|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| **`scan`**                        | Configure la fréquence de vérification des changements de fichiers par le serveur Jetty dans le **`pom.xml`**. Le projet squelette définit cela à `2` secondes. Augmenter cet intervalle peut réduire la charge CPU mais peut également retarder la prise en compte des modifications dans l'application. | `1`            |

## Configurations webforJ {#webforj-configurations}

| Propriété                          | Description                                                                                                                                                                           | Par défaut     |
|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| **`webforj.reloadOnServerError`** | Lors de l'utilisation du redéploiement à chaud, l'ensemble du fichier WAR est échangé. Si le client envoie une requête pendant que le serveur redémarre, une erreur se produit. Ce paramètre permet au client de tenter de recharger la page, en supposant que le serveur sera de retour en ligne sous peu. S'applique uniquement aux environnements de développement et gère uniquement les erreurs spécifiques au redéploiement à chaud. | `on`           |
| **`webforj.clientHeartbeatRate`** | Définit l'intervalle pour les pings clients afin de vérifier la disponibilité du serveur. Cela maintient la communication client-serveur ouverte. En développement, utilisez des intervalles plus courts pour une détection plus rapide des erreurs. En production, définissez cela à au moins 50 secondes pour éviter les requêtes excessives. | `50s`          |

## Considérations d'utilisation {#usage-considerations}

Bien que le plugin Jetty soit très efficace pour le développement, il présente quelques limitations potentielles :

- **Utilisation de la mémoire et du CPU** : La vérification fréquente des fichiers, définie par des valeurs `scan` basses dans le `pom.xml`, peut augmenter la consommation de ressources, en particulier sur de grands projets. Augmenter l'intervalle peut réduire la charge mais ralentit également le redéploiement.

- **Utilisation limitée en production** : Le plugin Jetty est conçu pour le développement, et non pour les environnements de production. Il manque d'optimisations de performance et de configurations de sécurité requises pour la production, ce qui le rend mieux adapté aux tests locaux.

- **Gestion des sessions** : Lors du redéploiement à chaud, les sessions utilisateur peuvent ne pas être préservées, en particulier lorsque des changements structurels importants se produisent dans le code. Cela peut perturber les tests impliquant des données de session utilisateur, nécessitant une gestion manuelle des sessions ou des configurations alternatives pour le développement.
