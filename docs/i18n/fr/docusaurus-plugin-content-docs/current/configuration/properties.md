---
title: Configuration des propriétés
sidebar_position: 1
description: >-
  Set webforJ entry points, debug mode, locales, file upload limits, and servlet
  mappings through webforj.conf and web.xml.
sidebar_class_name: updated-content
_i18n_hash: fd84f72aa62a993485d33fe9a94ab0d1
---
# Configuration des propriétés webforJ

Pour déployer et exécuter avec succès une application webforJ, plusieurs fichiers de configuration clés sont nécessaires : `webforj.conf` et `web.xml`. Chacun de ces fichiers contrôle différents aspects du comportement de l'application, des points d'entrée et des paramètres de débogage aux mappages de servlets.

## Configuration de `webforj.conf` {#configuring-webforjconf}

Le fichier `webforj.conf` est un fichier de configuration essentiel dans webforJ, spécifiant les paramètres de l'application tels que les points d'entrée, le mode de débogage et l'interaction client-serveur. Le fichier est au format [HOCON](https://github.com/lightbend/config/blob/master/HOCON.md) et doit être situé dans le répertoire `resources`.

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

| Propriété                              | Type    | Explication                                                      | Valeur par défaut        |
|----------------------------------------|---------|------------------------------------------------------------------|--------------------------|
| **`webforj.assetsCacheControl`**      | String  | En-tête Cache-Control pour les ressources statiques.             | `null`                   |
| **`webforj.assetsDir`**               | String  | Le nom de la route utilisée pour servir les fichiers statiques, tandis que le nom du dossier reste `static`. Cette configuration est utile si la route par défaut `static` entre en conflit avec une route définie dans votre application, vous permettant de changer le nom de la route sans renommer le dossier lui-même. | `null`                   |
| **`webforj.assetsExt`**               | String  | Extension de fichier par défaut pour les fichiers statiques.     | `null`                   |
| **`webforj.assetsIndex`**             | String  | Fichier par défaut servi pour les requêtes de répertoire (par exemple, index.html). | `null`                   |
| **`webforj.clientHeartbeatRate`**     | String  | L'intervalle auquel le client interroge le serveur pour voir s'il est toujours vivant. Pour le développement, définissez cela sur un intervalle plus court, par exemple `8s`, pour détecter rapidement la disponibilité du serveur. Définissez à 50 secondes ou plus en production pour éviter des requêtes excessives. | `50s`                    |
| **`webforj.components`**              | String  | Lorsqu'il est spécifié, le chemin de base détermine l'endroit où les composants DWC sont chargés. Par défaut, les composants sont chargés depuis le serveur hébergeant l'application. Cependant, définir un chemin de base personnalisé permet de charger des composants depuis un serveur ou un CDN alternatif. Par exemple, pour charger les composants depuis jsdelivr.com, définissez le chemin de base sur : https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version}. Il est important que les composants chargés soient compatibles avec la version du cadre webforJ utilisé ; sinon, l'application peut ne pas fonctionner comme prévu. Ce paramètre est ignoré lors de l'utilisation d'une installation BBj standard sans le moteur. Pour une installation BBj standard, le paramètre peut être géré avec le `!COMPONENTS` STBL. | `null`                   |
| **`webforj.debug`**                   | Boolean | Active le mode débogage. En mode débogage, webforJ affichera des informations supplémentaires dans la console et montrera toutes les exceptions dans le navigateur. Le mode de débogage est désactivé par défaut. | `null`                   |
| **`webforj.devtools.craftforj.enabled`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | **Environnements de développement uniquement.** Active [craftforJ](../craftforj/overview.md), l'environnement de développement qui inspecte l'application en cours d'exécution, modifie les propriétés des composants et écrit les modifications dans le code source Java. Nécessite également l'activation de `webforj.debug`. Aucune des deux propriétés n'est suffisante à elle seule. | `false`                  |
| **`webforj.devtools.craftforj.hosts-allowed`**&nbsp;<DocChip chip='since' label='26.02' /> | Liste | Adresses clients autorisées à accéder à craftforJ au-delà de la machine exécutant l'application. Par défaut, seul un navigateur sur cette machine peut y accéder. Une entrée se terminant par `*` correspond à un préfixe, et un simple `*` supprime la restriction. Voir [sécurité craftforJ](../craftforj/security.md). | uniquement loopback      |
| **`webforj.devtools.craftforj.source-changes`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | Indique si craftforJ peut écrire des modifications de propriété et des règles d'accès aux routes dans votre code source Java. | `true`                   |
| **`webforj.devtools.craftforj.stylesheet-changes`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | Indique si craftforJ peut enregistrer des thèmes et des styles dans la feuille de style de votre application. | `true`                   |
| **`webforj.devtools.craftforj.ai.enabled`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | Indique si l'[assistant AI craftforJ](../craftforj/ai.md) est disponible. | `true`                   |
| **`webforj.devtools.craftforj.ai.freeform-changes`**&nbsp;<DocChip chip='since' label='26.02' /> | Boolean | Indique si l'assistant peut écrire du Java de lui-même plutôt que de simplement modifier des propriétés. Chaque modification doit toujours se compiler et nécessite toujours votre approbation. | `true`                   |
| **`webforj.devtools.project-root`**&nbsp;<DocChip chip='since' label='26.03' /> | String | Le répertoire [craftforJ](../craftforj/configuration.md#project-root) dans lequel il recherche vos sources, pour les cas où il ne peut pas le déterminer par la manière dont l'application a été démarrée. Remplace `webforj.devtools.craftforj.project-root`, qui est déprécié mais reste lu lorsque la clé n'est pas définie. | détecté                  |
| **`webforj.entry`**                   | String  | Définit le point d'entrée de l'application en spécifiant le nom entièrement qualifié de la classe qui étend `webforj.App`. Si aucun point d'entrée n'est défini, webforJ explorera automatiquement le classpath pour les classes qui étendent `webforj.App`. Si plusieurs classes sont trouvées, une erreur se produira. Lorsqu'un paquet comprend plus d'un point d'entrée potentiel, le réglage explicite est requis pour éviter l'ambiguïté, ou alternativement, l'annotation `AppEntry` peut être utilisée pour spécifier le point d'entrée à l'exécution. | `null`                   |
| **`webforj.i18n.supported-locales`**&nbsp;<DocChip chip='since' label='25.12' /> | Liste | Liste des locales prises en charge sous forme de balises de langue BCP 47 (par exemple, `"en"`, `"en-US"`, `"fr"`, `"de-DE"`). Lorsque la détection automatique est activée, les locales préférées du navigateur sont comparées à cette liste. La première locale de la liste est utilisée comme moyen de secours par défaut. Voir [Traduction](../advanced/i18n-localization.md). | `[]`                     |
| **`webforj.i18n.auto-detect`**&nbsp;<DocChip chip='since' label='25.12' /> | Boolean | Lorsque `true`, la locale de l'application est automatiquement définie à partir de la langue préférée du navigateur au démarrage. La locale est déterminée en comparant les locales préférées du navigateur à la liste `supported-locales`. Lorsque `false` ou lorsque `supported-locales` est vide, l'application utilise `webforj.locale`. Voir [Traduction](../advanced/i18n-localization.md). | `false`                  |
| **`webforj.fileUpload.accept`**       | Liste   | Les types de fichiers autorisés pour les téléchargements de fichiers. Par défaut, tous les types de fichiers sont autorisés. Les formats pris en charge incluent les types MIME tels que `image/*`, `application/pdf`, `text/plain`, ou des extensions de fichiers telles que `*.txt`. Lorsqu'une installation BBj standard est utilisée, ce paramètre est ignoré et géré via `fileupload-accept.txt`. | `[]`                     |
| **`webforj.fileUpload.maxSize`**      | Long    | La taille maximale du fichier autorisée pour les téléchargements de fichiers, en octets. Par défaut, il n'y a pas de limite. Lorsqu'une installation BBj standard est utilisée, ce paramètre est ignoré et géré via `fileupload-accept.txt`. | `null`                   |
| **`webforj.iconsDir`**                | String  | URL du répertoire des icônes (par défaut sert à partir de `resources/icons/`). | `icons/`                 |
| **`webforj.legacyHtmlInText`**&nbsp;<DocChip chip='since' label='26.01' /> | Boolean | Lorsque `true`, une valeur enveloppée dans `<html>` rend son contenu comme du HTML. Lorsque `false`, la même valeur est affichée littéralement. | `true`                   |
| **`webforj.license.cfg`**             | String  | Le répertoire pour la configuration de la licence. Par défaut, il est le même que le répertoire de configuration de webforJ, mais cela peut être personnalisé si nécessaire. | `"."`                     |
| **`webforj.license.startupTimeout`**  | Integer | Délai d'attente de démarrage de la licence en secondes. | `null`                   |
| **`webforj.locale`**                  | String  | La locale pour l'application, déterminant la langue, les paramètres régionaux et les formats pour les dates, les heures et les nombres. | `null`                   |
| **`webforj.quiet`**                   | Boolean | Désactive l'image de chargement pendant le démarrage de l'application. | `false`                  |
| **`webforj.reloadOnServerError`**     | Boolean | **Environnements de développement uniquement.** Dans un environnement de développement, rechargez automatiquement la page en cas d'erreurs liées à la redéploiement à chaud, mais pas pour d'autres types d'erreurs. Lors de l'utilisation d'un redéploiement à chaud, si le client envoie une requête au serveur pendant qu'il redémarre, une erreur peut survenir pendant que le fichier WAR est échangé. Comme le serveur sera probablement de nouveau opérationnel sous peu, ce paramètre permet au client d'essayer de recharger automatiquement la page. | `false`                  |
| **`webforj.security.maxContentLength`**&nbsp;<DocChip chip='since' label='25.10' /> | Integer | La plus grande requête que l'application acceptera, en octets, comme protection contre les requêtes trop volumineuses destinées à épuiser la mémoire du serveur. Définir à `0` pour désactiver la limite. | `0`                      |
| **`webforj.security.maxInitPerMinute`**&nbsp;<DocChip chip='since' label='25.10' /> | Integer | Combien de nouvelles sessions d'application l'application démarrera chaque minute, comme protection contre une création rapide de sessions destinée à épuiser les ressources du serveur. Définir à `0` pour désactiver la limitation de fréquence. | `0`                      |
| **`webforj.servlets[n].name`**        | String  | Nom du servlet (utilise le nom de la classe s'il n'est pas spécifié). | `null`                   |
| **`webforj.servlets[n].className`**   | String  | Nom de classe entièrement qualifié du servlet. | `null`                   |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Paramètres d'initialisation du servlet. | `null`                   |
| **`webforj.sessionTimeout`**          | Integer | Durée d'expiration de la session en secondes. | `60`                     |
| **`webforj.stringTable`**             | `Map<String,String>` | Une carte de paires clé-valeur utilisées pour stocker des chaînes pour une utilisation dans l'application. Utile pour stocker des messages ou des étiquettes d'application. Plus d'informations sur `StringTable` peuvent être trouvées [ici](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html). | `{}`                      |
| **`webforj.mime.extensions`**         | `Map<String,String>` | Mappages de type MIME personnalisés pour les extensions de fichiers lors du service de fichiers statiques. Vous permet de remplacer les types MIME par défaut ou de définir des types MIME pour des extensions personnalisées. La clé de la carte est l'extension de fichier (sans le point), et la valeur est le type MIME. | `{}`                      |

## Configuration de `web.xml` {#configuring-webxml}

Le fichier `web.xml` est un fichier de configuration essentiel pour les applications web Java, et dans webforJ, il définit des paramètres importants tels que la configuration des servlets, les motifs d'URL et les pages d'accueil. Ce fichier doit être situé dans le répertoire `WEB-INF` de la structure de déploiement de votre projet.

| Paramètre                              | Explication                                                                                                                                                                               | Valeur par défaut          |
|----------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------|
| **`<display-name>`**                   | Définit le nom d'affichage de l'application web, généralement dérivé du nom du projet. Ce nom apparaît dans les consoles de gestion des serveurs d'application.                          | `${project.name}`           |
| **`<servlet>` et `<servlet-mapping>`**| Définit le `WebforjServlet`, le servlet principal pour gérer les requêtes webforJ. Ce servlet est mappé à toutes les URL (`/*`), ce qui en fait le point d'entrée principal des requêtes web. | `WebforjServlet`            |
| **`<load-on-startup>`**                | Précise que `WebforjServlet` doit être chargé lors du démarrage de l'application. Le définir à `1` signifie que le servlet sera chargé immédiatement, améliorant ainsi le traitement de la première requête. | `1`                         |
