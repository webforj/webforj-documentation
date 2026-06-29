---
title: Property Configuration
sidebar_position: 30
description: >-
  Set webforJ entry points, debug mode, locales, file upload limits, and servlet
  mappings through webforj.conf and web.xml.
sidebar_class_name: updated-content
_i18n_hash: c58a4908cfbde685bc0b30f6023e1df6
---
# Configuration des propriétés webforJ

Pour déployer et exécuter avec succès une application webforJ, deux fichiers de configuration clés sont requis : `webforj.conf` et `web.xml`. Chacun de ces fichiers contrôle différents aspects du comportement de l'application, des points d'entrée et des paramètres de débogage aux mappages de servlet.

## Configuration de `webforj.conf` {#configuring-webforjconf}

Le fichier `webforj.conf` est un fichier de configuration central dans webforJ, spécifiant des paramètres d'application tels que les points d'entrée, le mode débogage et l'interaction client-serveur. Le fichier est au format [HOCON](https://github.com/lightbend/config/blob/master/HOCON.md), et doit être situé dans le répertoire `resources`.

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

| Propriété                             | Type    | Explication                                                       | Valeur par défaut        |
|--------------------------------------|---------|-------------------------------------------------------------------|--------------------------|
| **`webforj.assetsCacheControl`**     | String  | En-tête Cache-Control pour les ressources statiques.             | `null`                   |
| **`webforj.assetsDir`**              | String  | Le nom de la route utilisé pour servir des fichiers statiques, tandis que le nom du dossier reste `static`. Cette configuration est utile si la route par défaut `static` entre en conflit avec une route définie dans votre application, vous permettant de changer le nom de la route sans renommer le dossier lui-même.       | `null`                   |
| **`webforj.assetsExt`**              | String  | Extension de fichier par défaut pour les fichiers statiques.      | `null`                   |
| **`webforj.assetsIndex`**            | String  | Fichier par défaut servi pour les requêtes de répertoire (par exemple, index.html). | `null`                   |
| **`webforj.clientHeartbeatRate`**    | String  | L'intervalle auquel le client interroge le serveur pour vérifier s'il est toujours actif. Pour le développement, définissez ceci sur un intervalle plus court, par exemple `8s`, pour détecter rapidement la disponibilité du serveur. Fixez à 50 secondes ou plus en production pour éviter des requêtes excessives. | `50s`                    |
| **`webforj.components`**             | String  | Lorsqu'il est spécifié, le chemin de base détermine d'où sont chargés les composants DWC. Par défaut, les composants sont chargés à partir du serveur hébergeant l'application. Cependant, définir un chemin de base personnalisé permet de charger des composants à partir d'un serveur alternatif ou d'un CDN. Par exemple, pour charger des composants à partir de jsdelivr.com, définissez le chemin de base sur : https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version}. Il est important que les composants chargés soient compatibles avec la version du framework webforJ en cours d'utilisation ; sinon, l'application peut ne pas fonctionner comme prévu. Ce paramètre est ignoré lors de l'utilisation d'une installation standard BBj sans le moteur. Pour une installation standard BBj, le paramètre peut être géré avec le `!COMPONENTS` STBL. | `null`                   |
| **`webforj.debug`**                  | Boolean | Active le mode débogage. En mode débogage, webforJ imprimera des informations supplémentaires dans la console et affichera toutes les exceptions dans le navigateur. Le mode débogage est désactivé par défaut. | `null`                   |
| **`webforj.entry`**                  | String  | Définit le point d'entrée de l'application en spécifiant le nom complètement qualifié de la classe qui étend `webforj.App`. Si aucun point d'entrée n'est défini, webforJ recherchera automatiquement dans le classpath des classes qui étendent `webforj.App`. Si plusieurs classes sont trouvées, une erreur se produira. Lorsqu'un package inclut plus d'un point d'entrée potentiel, il est nécessaire de le définir explicitement pour éviter toute ambiguïté, ou alternativement, l'annotation `AppEntry` peut être utilisée pour spécifier le point d'entrée au moment de l'exécution. | `null`                   |
| **`webforj.i18n.supported-locales`**&nbsp;<DocChip chip='since' label='25.12' /> | Liste | Liste des locales prises en charge sous forme d'étiquettes de langue BCP 47 (par exemple, `"en"`, `"en-US"`, `"fr"`, `"de-DE"`). Lorsque la détection automatique est activée, les locales préférées du navigateur sont mises en correspondance avec cette liste. La première locale de la liste est utilisée comme valeur de repli par défaut. Voir [Translation](../advanced/i18n-localization.md). | `[]`                     |
| **`webforj.i18n.auto-detect`**&nbsp;<DocChip chip='since' label='25.12' /> | Boolean | Lorsqu'il est `true`, la locale de l'application est automatiquement définie à partir de la langue préférée du navigateur au démarrage. La locale est résolue en faisant correspondre les locales préférées du navigateur avec la liste `supported-locales`. Lorsque `false` ou lorsque `supported-locales` est vide, l'application utilise `webforj.locale`. Voir [Translation](../advanced/i18n-localization.md). | `false`                  |
| **`webforj.fileUpload.accept`**      | Liste   | Les types de fichiers autorisés pour les téléchargements de fichiers. Par défaut, tous les types de fichiers sont autorisés. Les formats pris en charge incluent les types MIME comme `image/*`, `application/pdf`, `text/plain`, ou les extensions de fichiers comme `*.txt`. Lors de l'utilisation d'une installation standard BBj, ce paramètre est ignoré et géré via `fileupload-accept.txt`. | `[]`                     |
| **`webforj.fileUpload.maxSize`**     | Long    | La taille de fichier maximale autorisée pour les téléchargements de fichiers, en octets. Par défaut, il n'y a pas de limite. Lors de l'utilisation d'une installation standard BBj, ce paramètre est ignoré et géré via `fileupload-accept.txt`. | `null`                   |
| **`webforj.iconsDir`**               | String  | Point de terminaison URL pour le répertoire des icônes (par défaut, servi à partir de `resources/icons/`). | `icons/`                 |
| **`webforj.legacyHtmlInText`**&nbsp;<DocChip chip='since' label='26.01' /> | Boolean | Lorsqu'il est `true`, une valeur entourée de `<html>` rend son contenu comme du HTML. Lorsqu'il est `false`, la même valeur est affichée littéralement. | `true`                   |
| **`webforj.license.cfg`**            | String  | Le répertoire pour la configuration de la licence. Par défaut, c'est le même que le répertoire de configuration webforJ, mais cela peut être personnalisé si nécessaire. | `"."`                     |
| **`webforj.license.startupTimeout`** | Integer | Délai d'attente de démarrage de la licence en secondes. | `null`                   |
| **`webforj.locale`**                 | String  | La locale pour l'application, déterminant la langue, les paramètres régionaux et les formats pour les dates, heures et nombres. | `null`                   |
| **`webforj.quiet`**                  | Boolean | Désactive l'image de chargement lors du démarrage de l'application. | `false`                  |
| **`webforj.reloadOnServerError`**    | Boolean | **Environnements de développement uniquement.** Dans un environnement de développement, recharger automatiquement la page en cas d'erreurs liées à la redéployment à chaud, mais pas pour d'autres types d'erreurs. Lors de l'utilisation de la redéployment à chaud, si le client envoie une requête au serveur pendant qu'il redémarre, une erreur peut se produire pendant que le fichier WAR est échangé. Comme le serveur sera probablement de nouveau en ligne sous peu, ce réglage permet au client de tenter un rechargement de la page automatiquement. | `false`                  |
| **`webforj.security.maxContentLength`**&nbsp;<DocChip chip='since' label='25.10' /> | Integer | La plus grande requête que l'application acceptera, en octets, comme protection contre les requêtes trop volumineuses destinées à épuiser la mémoire du serveur. Définissez à `0` pour désactiver la limite. | `0`                      |
| **`webforj.security.maxInitPerMinute`**&nbsp;<DocChip chip='since' label='25.10' /> | Integer | Combien de nouvelles sessions d'application l'application démarrera chaque minute, comme protection contre la création rapide de sessions destinée à épuiser les ressources serveur. Définissez à `0` pour désactiver la limitation de fréquence. | `0`                      |
| **`webforj.servlets[n].name`**       | String  | Nom du servlet (utilise le nom de classe s'il n'est pas spécifié). | `null`                   |
| **`webforj.servlets[n].className`**  | String  | Nom de classe complètement qualifié du servlet. | `null`                   |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Paramètres d'initialisation du servlet. | `null`                   |
| **`webforj.sessionTimeout`**         | Integer | Durée d'expiration de la session en secondes. | `60`                     |
| **`webforj.stringTable`**            | `Map<String,String>` | Une carte de paires clé-valeur utilisées pour stocker des chaînes pour une utilisation dans l'application. Utile pour stocker des messages ou des étiquettes d'application. Plus d'informations sur `StringTable` peuvent être trouvées [ici](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`                     |
| **`webforj.mime.extensions`**            | `Map<String,String>` | Mappings de type MIME personnalisés pour les extensions de fichiers lors du service de fichiers statiques. Vous permet d'écraser les types MIME par défaut ou de définir des types MIME pour des extensions personnalisées. La clé de la carte est l'extension de fichier (sans le point), et la valeur est le type MIME. | `{}`                     |

## Configuration de `web.xml` {#configuring-webxml}

Le fichier `web.xml` est un fichier de configuration essentiel pour les applications web Java, et dans webforJ, il définit des paramètres importants comme la configuration du servlet, les patterns d'URL et les pages d'accueil. Ce fichier doit être situé dans le répertoire `WEB-INF` de la structure de déploiement de votre projet.

| Paramètre                                 | Explication                                                                                                                                                                                   | Valeur par défaut               |
| ----------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------- |
| **`<display-name>`**                      | Définit le nom d'affichage pour l'application web, dérivé typiquement du nom du projet. Ce nom apparaît dans les consoles de gestion des serveurs d'applications.                             | `${project.name}`               |
| **`<servlet>` et `<servlet-mapping>`**   | Définit le `WebforjServlet`, le servlet principal pour gérer les requêtes webforJ. Ce servlet est mappé à toutes les URL (`/*`), ce qui en fait le point d'entrée principal pour les requêtes web. | `WebforjServlet`                |
| **`<load-on-startup>`**                   | Spécifie que `WebforjServlet` doit être chargé lorsque l'application démarre. Fixer cela à `1` permet de charger le servlet immédiatement, ce qui améliore le traitement initial des requêtes.   | `1`                             |
