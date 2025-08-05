---
title: Property Configuration
sidebar_position: 30
_i18n_hash: dea9eb679150ca6124fb625c7d04d27c
---
# Configuration des propriétés de webforJ

Pour déployer et exécuter avec succès une application webforJ, plusieurs fichiers de configuration clés sont nécessaires : `webforJ.conf` et `web.xml`. Chacun de ces fichiers contrôle différents aspects du comportement de l'application, des points d'entrée et des paramètres de débogage aux mappages de servlet.

## Configuration de `webforj.conf` {#configuring-webforjconf}

Le fichier `webforJ.conf` est un fichier de configuration central dans webforJ, spécifiant les paramètres de l'application tels que les points d'entrée, le mode débogage et l'interaction client-serveur. Le fichier est écrit en [format HOCON](https://github.com/lightbend/config/blob/master/HOCON.md) et doit se trouver dans le répertoire `resources`.

### Exemple de fichier `webforj.conf` {#example-webforjconf-file}

```Ini
# Ce fichier de configuration est au format HOCON :
# https://github.com/lightbend/config/blob/master/HOCON.md

webforj.entry = com.webforj.samples.Application
webforj.debug = true
webforj.reloadOnServerError = on
webforj.clientHeartbeatRate = 1s
```

### Options de configuration {#configuration-options}

| Propriété                       | Explication                                                                                                                                                                            | Par défaut         |
|--------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------|
| **`webforj.entry`**            | Définit le point d'entrée de l'application en spécifiant le nom complet de la classe qui étend `webforj.App`. Si aucun point d'entrée n'est défini, webforJ recherche automatiquement dans le chemin de classe les classes qui étendent `webforj.App`. Si plusieurs classes sont trouvées, une erreur se produira. Lorsque qu'un paquet inclut plus d'un point d'entrée potentiel, il est nécessaire de le définir explicitement pour éviter toute ambiguïté, ou alternativement, l'annotation `AppEntry` peut être utilisée pour spécifier le point d'entrée à l'exécution. | `null`              |
| **`webforj.debug`**            | Active le mode débogage. En mode débogage, webforJ affichera des informations supplémentaires dans la console et montrera toutes les exceptions dans le navigateur. Le mode débogage est désactivé par défaut. | `null`              |
| **`webforj.reloadOnServerError`** | Lors de l'utilisation du redeploiement à chaud, l'ensemble du fichier WAR est échangé. Si le client essaie d'envoyer une requête au serveur pendant qu'il redémarre, une erreur se produit. Ce paramètre permet au client d'essayer de recharger la page si le serveur est temporairement indisponible, en espérant qu'il sera de nouveau en ligne sous peu. Cela ne s'applique qu'aux environnements de développement et ne gère que les erreurs spécifiques au redeploiement à chaud, pas d'autres types d'erreurs. | `on`                |
| **`webforj.clientHeartbeatRate`** | Définit l'intervalle auquel le client envoie un ping au serveur pour voir s'il est toujours en vie. Cela aide à maintenir la communication. Pour le développement, définissez cela sur un intervalle plus court, par exemple `8s`, pour détecter rapidement la disponibilité du serveur. Ne pas le définir en dessous de 50 secondes en production pour éviter des demandes excessives. | `50s`               |
| **`webforj.components`**       | Lorsqu'il est spécifié, le chemin de base détermine où les composants DWC sont chargés. Par défaut, les composants sont chargés à partir du serveur hébergeant l'application. Cependant, définir un chemin de base personnalisé permet de charger les composants à partir d'un serveur alternatif ou d'un CDN. Par exemple, pour charger des composants depuis jsdelivr.com, définissez le chemin de base sur : https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version}. Il est important que les composants chargés soient compatibles avec la version du framework webforJ utilisé ; sinon, l'application peut ne pas fonctionner comme prévu. Ce paramètre est ignoré lors de l'utilisation d'une installation BBj standard sans le moteur. Pour une installation BBj standard, le paramètre peut être géré avec le `!COMPONENTS` STBL. | `null`              |
| **`webforj.locale`**           | Définit la locale pour l'application, déterminant la langue, les paramètres régionaux et les formats pour les dates, heures et nombres. | `null`               |
| **`webforj.assetsDir`**        | Spécifie le nom de route utilisé pour servir les fichiers statiques, tandis que le nom de dossier physique reste `static`. Cette configuration est utile si la route `static` par défaut entre en conflit avec une route définie dans votre application, vous permettant de modifier le nom de la route sans renommer le dossier lui-même. | `static`            |
| **`webforj.stringTable`**      | Une carte de paires clé-valeur utilisée pour stocker des chaînes à utiliser dans l'application. Utile pour stocker des messages ou des étiquettes d'application. Plus d'informations sur `StringTable` peuvent être trouvées [ici](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`                |
| **`webforj.fileUpload.accept`** | Spécifie les types de fichiers autorisés pour les téléchargements de fichiers. Par défaut, tous les types de fichiers sont autorisés. Les formats pris en charge incluent des types MIME tels que `image/*`, `application/pdf`, `text/plain`, ou des extensions de fichiers comme `*.txt`. Lors de l'utilisation d'une installation BBj standard, ce paramètre est ignoré et géré via `fileupload-accept.txt`. | `[]`                |
| **`webforj.fileUpload.maxSize`** | Définit la taille maximale de fichier autorisée pour les téléchargements de fichiers, en octets. Par défaut, il n'y a pas de limite. Lors de l'utilisation d'une installation BBj standard, ce paramètre est ignoré et géré via `fileupload-accept.txt`. | `null`              |
| **`license.cfg`**              | Configure le répertoire pour la configuration de la licence. Par défaut, il est le même que le répertoire de configuration webforJ, mais cela peut être personnalisé si nécessaire. | `"."`               |

## Configuration de `web.xml` {#configuring-webxml}

Le fichier web.xml est un fichier de configuration essentiel pour les applications web Java, et dans webforJ, il définit des paramètres importants tels que la configuration des servlets, les modèles d'URL, les pages d'accueil. Ce fichier doit se trouver dans le répertoire `WEB-INF` de la structure de déploiement de votre projet.

| Paramètre                                 | Explication                                                                                                                                                                                   | Valeur par défaut               |
| --------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------- |
| **`<display-name>`**                    | Définit le nom d'affichage de l'application web, généralement dérivé du nom du projet. Ce nom apparaît dans les consoles de gestion des serveurs d'applications.                                | `${project.name}`               |
| **`<servlet>` et `<servlet-mapping>`** | Définit le `WebforjServlet`, le servlet principal pour gérer les requêtes webforJ. Ce servlet est mappé à toutes les URL (`/*`), en faisant le point d'entrée principal pour les requêtes web. | `WebforjServlet`                |
| **`<load-on-startup>`**                 | Spécifie que `WebforjServlet` doit être chargé lorsque l'application démarre. En définissant cela sur `1`, cela garantit que le servlet se charge immédiatement, ce qui améliore le traitement des premières requêtes.                | `1`                             |
