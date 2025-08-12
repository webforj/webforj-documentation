---
title: Maven Jetty plugin
_i18n_hash: 7311fe4d0b6c5382244d898f099b9435
---
Le plugin Maven Jetty est un outil populaire qui permet aux développeurs d'exécuter des applications web Java dans un serveur Jetty intégré directement à partir de leurs projets Maven. 

Le plugin Jetty lance un serveur Jetty intégré qui surveille les fichiers de votre application, y compris les classes Java et les ressources, à la recherche de changements. Lorsqu'il détecte des mises à jour, il redéploie automatiquement l'application, ce qui accélère le développement en éliminant les étapes manuelles de construction et de déploiement. 

## Configurations Jetty {#jetty-configurations}

Voici quelques configurations essentielles pour affiner les paramètres de déploiement à chaud et d'interaction du serveur du plugin :

| Propriété                          | Description                                                                                                                                                                           | Par défaut     |
|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| **`scan`**                        | Configure la fréquence à laquelle le serveur Jetty recherche des changements de fichiers dans le **`pom.xml`**. Le projet squelette fixe cela à `2` secondes. Augmenter cet intervalle peut réduire la charge du CPU mais peut retarder la mise à jour des modifications dans l'application. | `1`            |

## Configurations webforJ {#webforj-configurations}

| Propriété                          | Description                                                                                                                                                                           | Par défaut     |
|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| **`webforj.reloadOnServerError`** | Lors de l'utilisation du déploiement à chaud, le fichier WAR entier est échangé. Si le client envoie une demande pendant que le serveur redémarre, une erreur se produit. Ce paramètre permet au client de tenter un rechargement de la page, en supposant que le serveur sera à nouveau en ligne sous peu. Ne s'applique qu'aux environnements de développement et gère uniquement les erreurs spécifiques au redéploiement à chaud. | `on`           |
| **`webforj.clientHeartbeatRate`** | Définit l'intervalle pour les pings clients afin de vérifier la disponibilité du serveur. Cela maintient la communication client-serveur ouverte. Pour le développement, utilisez des intervalles plus courts pour une détection d'erreurs plus rapide. En production, définissez cela à au moins 50 secondes pour éviter des demandes excessives. | `50s`          |

## Considérations d'utilisation {#usage-considerations}

Bien que le plugin Jetty soit très efficace pour le développement, il présente quelques limitations potentielles :

- **Utilisation de la mémoire et du CPU** : Une vérification fréquente des fichiers, définie par de faibles valeurs de `scan` dans le `pom.xml`, peut augmenter la consommation de ressources, en particulier sur de grands projets. Augmenter l'intervalle peut réduire la charge mais ralentit également le redéploiement.

- **Utilisation limitée en production** : Le plugin Jetty est conçu pour le développement, pas pour les environnements de production. Il manque d'optimisation des performances et de configurations de sécurité requises pour la production, ce qui le rend mieux adapté aux tests locaux.

- **Gestion des sessions** : Lors du redéploiement à chaud, les sessions utilisateur peuvent ne pas être préservées, notamment lorsque des changements structurels importants se produisent dans le code. Cela peut perturber les tests impliquant des données de session utilisateur, nécessitant une gestion manuelle des sessions ou des configurations alternatives pour le développement.
