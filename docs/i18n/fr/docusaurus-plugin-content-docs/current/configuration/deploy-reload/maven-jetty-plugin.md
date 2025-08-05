---
title: Maven Jetty plugin
_i18n_hash: 13b8de676f30b5a21eb7e9c2b49945b6
---
Le plugin Maven Jetty est un outil populaire qui permet aux développeurs d'exécuter des applications web Java dans un serveur Jetty intégré directement à partir de leurs projets Maven.

Le Plugin Jetty lance un serveur Jetty intégré qui surveille les fichiers de votre application, y compris les classes Java et les ressources, pour détecter les modifications. Lorsqu'il détecte des mises à jour, il redéploie automatiquement l'application, ce qui accélère le développement en éliminant les étapes manuelles de construction et de déploiement.

## Configurations Jetty {#jetty-configurations}

Voici quelques configurations essentielles pour affiner les paramètres de déploiement à chaud du plugin et l'interaction avec le serveur :

| Propriété                          | Description                                                                                                                                                                           | Par défaut     |
|------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| **`scan`**         | Configure la fréquence à laquelle le serveur Jetty vérifie les modifications de fichiers dans le **`pom.xml`**. Le projet de base définit cela à `2` secondes. Augmenter cet intervalle peut réduire la charge CPU mais peut retarder la réflexion des changements dans l'application. | `1`            |

## Configurations webforJ {#webforj-configurations}

| Propriété                                | Description                                                                                                                                                                           | Par défaut     |
|------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| **`webforj.reloadOnServerError`**       | Lors de l'utilisation du déploiement à chaud, l'ensemble du fichier WAR est échangé. Si le client envoie une requête pendant que le serveur redémarre, une erreur se produit. Ce paramètre permet au client d'essayer de recharger la page, en supposant que le serveur sera à nouveau en ligne sous peu. S'applique uniquement aux environnements de développement et ne gère que les erreurs spécifiques au déploiement à chaud. | `on`           |
| **`webforj.clientHeartbeatRate`**       | Définit l'intervalle pour les pings clients afin d'interroger la disponibilité du serveur. Cela maintient la communication client-serveur ouverte. Pour le développement, utilisez des intervalles plus courts pour une détection d'erreurs plus rapide. En production, réglez ceci à au moins 50 secondes pour éviter des requêtes excessives. | `50s`          |

## Considérations d'utilisation {#usage-considerations}

Bien que le Plugin Jetty soit très efficace pour le développement, il présente quelques limitations potentielles :

- **Utilisation de la mémoire et du CPU** : Une vérification fréquente des fichiers, définie par de faibles valeurs de `scan` dans le `pom.xml`, peut augmenter la consommation de ressources, surtout sur des projets volumineux. Augmenter l'intervalle peut réduire la charge mais ralentit également le redéploiement.

- **Utilisation limitée en production** : Le Plugin Jetty est conçu pour le développement, et non pour les environnements de production. Il manque les optimisations de performance et les configurations de sécurité nécessaires pour la production, ce qui le rend mieux adapté aux tests locaux.

- **Gestion des sessions** : Pendant le déploiement à chaud, les sessions utilisateur peuvent ne pas être préservées, surtout lorsque des changements structurels importants se produisent dans le code. Cela peut interrompre les tests impliquant des données de session utilisateur, nécessitant une gestion manuelle des sessions ou des configurations de contournement pour le développement.
