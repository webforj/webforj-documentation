---
title: Property Configuration
sidebar_position: 30
_i18n_hash: e2cc183e859c85e0d1f4a24c196b8a55
---
# Configuration des propriétés webforJ

Pour déployer et exécuter avec succès une application webforJ, quelques fichiers de configuration clés sont requis : `webforJ.conf` et `web.xml`. Chacun de ces fichiers contrôle différents aspects du comportement de l'application, des points d'entrée et des paramètres de débogage aux mappages de servlets.

## Configuration de `webforj.conf` {#configuring-webforjconf}

Le fichier `webforJ.conf` est un fichier de configuration central dans webforJ, spécifiant les paramètres de l'application tels que les points d'entrée, le mode de débogage et l'interaction client-serveur. Le fichier est écrit en [format HOCON](https://github.com/lightbend/config/blob/master/HOCON.md) et doit être situé dans le répertoire `resources`.

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

| Propriété                       | Explication                                                                                                                                                                            | Valeur par défaut         |
|---------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------|
| **`webforj.entry`**            | Définit le point d'entrée de l'application en spécifiant le nom complètement qualifié de la classe qui étend `webforj.App`. Si aucun point d'entrée n'est défini, webforJ scannera automatiquement le classpath pour trouver des classes qui étendent `webforj.App`. Si plusieurs classes sont trouvées, une erreur se produira. Lorsque un package inclut plus d'un point d'entrée potentiel, le fait de le définir explicitement est nécessaire pour éviter l'ambiguïté, ou alternativement, l'annotation `AppEntry` peut être utilisée pour spécifier le point d'entrée au moment de l'exécution. | `null`                    |
| **`webforj.debug`**            | Active le mode de débogage. En mode de débogage, webforJ affichera des informations supplémentaires dans la console et montrera toutes les exceptions dans le navigateur. Le mode de débogage est désactivé par défaut. | `null`                    |
| **`webforj.reloadOnServerError`** | Lors de l'utilisation du redeploiement à chaud, l'ensemble du fichier WAR sera échangé. Si le client essaie d'envoyer une demande au serveur pendant son redémarrage, une erreur se produira. Ce paramètre permet au client de tenter un rechargement de la page si le serveur est temporairement indisponible, espérant qu'il sera de nouveau en ligne sous peu. Cela ne s'applique qu'aux environnements de développement et gère uniquement les erreurs spécifiques au redeploiement à chaud, pas d'autres types d'erreurs. | `on`                      |
| **`webforj.clientHeartbeatRate`** | Définit l'intervalle auquel le client interroge le serveur pour voir s'il est toujours en vie. Cela aide à maintenir la communication. Pour le développement, définissez cela à un intervalle plus court, par exemple `8s`, pour détecter rapidement la disponibilité du serveur. Ne définissez pas cela en dessous de 50 secondes en production pour éviter des requêtes excessives. | `50s`                     |
| **`webforj.components`**       | Lorsqu'il est spécifié, le chemin de base détermine d'où les composants DWC sont chargés. Par défaut, les composants sont chargés à partir du serveur hébergeant l'application. Cependant, définir un chemin de base personnalisé permet de charger des composants à partir d'un serveur ou d'un CDN alternatif. Par exemple, pour charger des composants depuis jsdelivr.com, définissez le chemin de base sur : https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version} Il est important que les composants chargés soient compatibles avec la version du framework webforJ utilisée ; autrement, l'application peut ne pas fonctionner comme prévu. Ce paramètre est ignoré lors de l'utilisation d'une installation BBj standard sans le moteur. Pour une installation BBj standard, le paramètre peut être géré avec le STBL `!COMPONENTS`. | `null`                    |
| **`webforj.locale`**           | Définit la locale pour l'application, déterminant la langue, les paramètres régionaux et les formats pour les dates, heures et nombres. | `null`                    |
| **`webforj.assetsDir`**           | Spécifie le nom de route utilisé pour servir des fichiers statiques, tandis que le nom physique du dossier reste `static`. Cette configuration est utile si la route par défaut `static` entre en conflit avec une route définie dans votre application, vous permettant de changer le nom de route sans renommer le dossier lui-même. | `static`                  |
| **`webforj.stringTable`**      | Une carte de paires clé-valeur utilisée pour stocker des chaînes à utiliser dans l'application. Utile pour stocker des messages ou des étiquettes d'application. Plus d'informations sur `StringTable` peuvent être trouvées [ici](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`                      |
| **`webforj.fileUpload.accept`** | Spécifie les types de fichiers autorisés pour les téléchargements de fichiers. Par défaut, tous les types de fichiers sont autorisés. Les formats pris en charge incluent des types MIME comme `image/*`, `application/pdf`, `text/plain`, ou des extensions de fichiers comme `*.txt`. Lors de l'utilisation d'une installation BBj standard, ce paramètre est ignoré et géré via `fileupload-accept.txt`. | `[]`                      |
| **`webforj.fileUpload.maxSize`** | Définit la taille maximale de fichier autorisée pour les téléchargements de fichiers, en octets. Par défaut, il n'y a pas de limite. Lors de l'utilisation d'une installation BBj standard, ce paramètre est ignoré et géré via `fileupload-accept.txt`. | `null`                    |
| **`license.cfg`**              | Configure le répertoire pour la configuration de la licence. Par défaut, il est identique au répertoire de configuration de webforJ, mais cela peut être personnalisé si nécessaire. | `"."`                     |

## Configuration de `web.xml` {#configuring-webxml}

Le fichier web.xml est un fichier de configuration essentiel pour les applications web Java, et dans webforJ, il définit des paramètres importants tels que la configuration du servlet, les modèles d'URL, les pages d'accueil. Ce fichier doit être situé dans le répertoire `WEB-INF` de la structure de déploiement de votre projet.

| Paramètre                               | Explication                                                                                                                                                                                   | Valeur par défaut               |
| --------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------- |
| **`<display-name>`**                    | Définit le nom d'affichage de l'application web, dérivé typiquement du nom du projet. Ce nom apparaît dans les consoles de gestion des serveurs d'applications.                               | `${project.name}`               |
| **`<servlet>` et `<servlet-mapping>`** | Définit le `WebforjServlet`, le servlet principal pour traiter les requêtes webforJ. Ce servlet est mappé à toutes les URLs (`/*`), ce qui en fait le point d'entrée principal pour les requêtes web. | `WebforjServlet`                |
| **`<load-on-startup>`**                 | Spécifie que `WebforjServlet` doit être chargé lorsque l'application démarre. Le définir à `1` garantit que le servlet se charge immédiatement, ce qui améliore le traitement initial des requêtes. | `1`                             |
