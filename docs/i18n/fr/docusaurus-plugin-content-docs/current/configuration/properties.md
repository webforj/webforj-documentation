---
title: Property Configuration
sidebar_position: 30
description: >-
  Set webforJ entry points, debug mode, locales, file upload limits, and servlet
  mappings through webforj.conf and web.xml.
_i18n_hash: 2eb59302da44bcdd27d6366419bd78ad
---
# Configuration des propriétés de webforJ

Pour déployer et exécuter avec succès une application webforJ, quelques fichiers de configuration clés sont requis : `webforj.conf` et `web.xml`. Chacun de ces fichiers contrôle différents aspects du comportement de l'application, des points d'entrée et des paramètres de débogage aux mappages de servlets.

## Configuration de `webforj.conf` {#configuring-webforjconf}

Le fichier `webforj.conf` est un fichier de configuration central dans webforJ, spécifiant les paramètres de l'application tels que les points d'entrée, le mode de débogage et l'interaction client-serveur. Le fichier est au format [HOCON](https://github.com/lightbend/config/blob/master/HOCON.md) et doit être situé dans le répertoire `resources`.

:::tip
Si vous intégrez avec [Spring](../integrations/spring/overview.md), vous pouvez définir ces propriétés `webforj.conf` dans le fichier `application.properties`.
:::

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

| Propriété                              | Type    | Explication                                                       | Valeur par défaut        |
|---------------------------------------|---------|-------------------------------------------------------------------|---------------------------|
| **`webforj.assetsCacheControl`**      | String  | En-tête Cache-Control pour les ressources statiques.              | `null`                    |
| **`webforj.assetsDir`**               | String  | Le nom de la route utilisé pour servir des fichiers statiques, tandis que le nom du dossier reste `static`. Cette configuration est utile si la route par défaut `static` entre en conflit avec une route définie dans votre application, vous permettant de changer le nom de la route sans renommer le dossier lui-même. | `null`                    |
| **`webforj.assetsExt`**               | String  | Extension de fichier par défaut pour les fichiers statiques.      | `null`                    |
| **`webforj.assetsIndex`**             | String  | Fichier par défaut servi pour les requêtes de répertoire (e.g., index.html). | `null`                    |
| **`webforj.clientHeartbeatRate`**     | String  | L'intervalle auquel le client vérifie si le serveur est toujours actif. Pour le développement, définissez cela sur un intervalle plus court, par exemple `8s`, pour détecter rapidement la disponibilité du serveur. Définissez-le à 50 secondes ou plus en production pour éviter des requêtes excessives. | `50s`                     |
| **`webforj.components`**              | String  | Lorsqu'il est spécifié, le chemin de base détermine d'où les composants DWC sont chargés. Par défaut, les composants sont chargés depuis le serveur hébergeant l'application. Cependant, en définissant un chemin de base personnalisé, il est possible de charger des composants depuis un serveur alternatif ou un CDN. Par exemple, pour charger des composants depuis jsdelivr.com, définissez le chemin de base sur : https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version}. Il est important que les composants chargés soient compatibles avec la version du framework webforJ utilisée ; sinon, l'application peut ne pas fonctionner comme prévu. Ce paramètre est ignoré lors de l'utilisation d'une installation BBj standard sans le moteur. Pour une installation BBj standard, le paramètre peut être géré avec le `!COMPONENTS` STBL. | `null`                    |
| **`webforj.debug`**                   | Boolean | Active le mode de débogage. En mode débogage, webforJ imprimera des informations supplémentaires dans la console et affichera toutes les exceptions dans le navigateur. Le mode de débogage est désactivé par défaut. | `null`                    |
| **`webforj.entry`**                   | String  | Définit le point d'entrée de l'application en spécifiant le nom complètement qualifié de la classe qui prolonge `webforj.App`. Si aucun point d'entrée n'est défini, webforJ va automatiquement scanner le classpath pour des classes qui prolonge `webforj.App`. Si plusieurs classes sont trouvées, une erreur se produira. Lorsqu'un package inclut plus d'un point d'entrée potentiel, il est nécessaire de le définir explicitement pour éviter toute ambiguïté, ou alternativement, l'annotation `AppEntry` peut être utilisée pour spécifier le point d'entrée au moment de l'exécution. | `null`                    |
| **`webforj.i18n.supported-locales`**&nbsp;<DocChip chip='since' label='25.12' /> | Liste   | Liste des locales prises en charge sous forme de balises de langue BCP 47 (e.g., `"en"`, `"en-US"`, `"fr"`, `"de-DE"`). Lorsque la détection automatique est activée, les locales préférées du navigateur sont comparées à cette liste. La première locale de la liste est utilisée comme valeur de secours par défaut. Voir [Traduction](../advanced/i18n-localization.md). | `[]`                      |
| **`webforj.i18n.auto-detect`**&nbsp;<DocChip chip='since' label='25.12' /> | Boolean | Lorsque `true`, la locale de l'application est automatiquement définie à partir de la langue préférée du navigateur au démarrage. La locale est déterminée en comparant les locales préférées du navigateur à la liste `supported-locales`. Lorsque `false` ou lorsque `supported-locales` est vide, l'application utilise `webforj.locale`. Voir [Traduction](../advanced/i18n-localization.md). | `false`                   |
| **`webforj.fileUpload.accept`**       | Liste   | Types de fichiers autorisés pour les téléchargements de fichiers. Par défaut, tous les types de fichiers sont autorisés. Les formats pris en charge incluent des types MIME comme `image/*`, `application/pdf`, `text/plain`, ou des extensions de fichiers comme `*.txt`. Lors d'une installation BBj standard, ce paramètre est ignoré et géré par `fileupload-accept.txt`. | `[]`                      |
| **`webforj.fileUpload.maxSize`**      | Long    | La taille maximale de fichier autorisée pour les téléchargements de fichiers, en octets. Par défaut, il n'y a pas de limite. Lors d'une installation BBj standard, ce paramètre est ignoré et géré par `fileupload-accept.txt`. | `null`                    |
| **`webforj.iconsDir`**                | String  | Point de terminaison URL pour le répertoire des icônes (par défaut, servi depuis `resources/icons/`). | `icons/`                  |
| **`webforj.license.cfg`**             | String  | Le répertoire pour la configuration de la licence. Par défaut, il est le même que le répertoire de configuration de webforJ, mais cela peut être personnalisé si nécessaire. | `"."`                      |
| **`webforj.license.startupTimeout`**  | Integer | Délai d'expiration de la licence au démarrage en secondes. | `null`                    |
| **`webforj.locale`**                  | String  | La locale pour l'application, déterminant la langue, les paramètres régionaux et les formats pour les dates, heures et nombres. | `null`                    |
| **`webforj.quiet`**                   | Boolean | Désactive l'image de chargement lors du démarrage de l'application. | `false`                   |
| **`webforj.reloadOnServerError`**     | Boolean | **Environnements de développement seulement.** Dans un environnement de développement, recharger automatiquement la page en cas d'erreurs liées à la redéploiement à chaud, mais pas pour d'autres types d'erreurs. Lors de l'utilisation d'une redeployment à chaud, si le client envoie une requête au serveur pendant qu'il redémarre, une erreur peut se produire tandis que le fichier WAR est échangé. Parce que le serveur sera probablement de retour en ligne sous peu, ce paramètre permet au client d'essayer de recharger automatiquement la page. | `false`                   |
| **`webforj.security.maxContentLength`**&nbsp;<DocChip chip='since' label='25.10' /> | Integer | La plus grande requête que l'application acceptera, en octets, comme protection contre les requêtes surdimensionnées destinées à épuiser la mémoire du serveur. Défini à `0` pour désactiver la limite. | `0`                       |
| **`webforj.security.maxInitPerMinute`**&nbsp;<DocChip chip='since' label='25.10' /> | Integer | Combien de nouvelles sessions d'application l'application démarrera chaque minute, comme protection contre la création rapide de sessions destinée à épuiser les ressources du serveur. Défini à `0` pour désactiver la limitation du débit. | `0`                       |
| **`webforj.servlets[n].name`**        | String  | Nom du servlet (utilise le nom de la classe si non spécifié). | `null`                    |
| **`webforj.servlets[n].className`**   | String | Nom de classe complètement qualifié du servlet. | `null`                    |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Paramètres d'initialisation du servlet. | `null`                    |
| **`webforj.sessionTimeout`**          | Integer | Durée de l'expiration de session en secondes. | `60`                      |
| **`webforj.stringTable`**             | `Map<String,String>` | Une carte de paires clé-valeur utilisées pour stocker des chaînes dans l'application. Utile pour le stockage de messages ou d'étiquettes de l'application. Plus d'informations sur `StringTable` peuvent être trouvées [ici](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`                       |
| **`webforj.mime.extensions`**            | `Map<String,String>` | Mappages de type MIME personnalisés pour les extensions de fichiers lors du service de fichiers statiques. Vous permet de remplacer les types MIME par défaut ou de définir des types MIME pour des extensions personnalisées. La clé de la carte est l'extension de fichier (sans le point), et la valeur est le type MIME. | `{}`                       |

## Configuration de `web.xml` {#configuring-webxml}

Le fichier `web.xml` est un fichier de configuration essentiel pour les applications web Java, et dans webforJ, il définit des paramètres importants tels que la configuration du servlet, les modèles d'URL et les pages de bienvenue. Ce fichier doit être situé dans le répertoire `WEB-INF` de la structure de déploiement de votre projet.

| Paramètre                              | Explication                                                                                                                                                                                   | Valeur par défaut         |
|---------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------|
| **`<display-name>`**                  | Définit le nom affiché pour l'application web, dérivé typiquement du nom du projet. Ce nom apparaît dans les consoles de gestion des serveurs d'applications.                              | `${project.name}`         |
| **`<servlet>` et `<servlet-mapping>`** | Définit le `WebforjServlet`, le servlet principal pour gérer les requêtes webforJ. Ce servlet est mappé à toutes les URL (`/*`), en faisant le principal point d'entrée pour les requêtes web. | `WebforjServlet`          |
| **`<load-on-startup>`**               | Spécifie que `WebforjServlet` doit être chargé lorsque l'application démarre. En définissant cela à `1`, le servlet se charge immédiatement, ce qui améliore la gestion des requêtes initiales. | `1`                       |
