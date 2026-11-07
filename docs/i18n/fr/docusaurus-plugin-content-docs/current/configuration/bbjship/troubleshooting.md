---
title: Dépannage
sidebar_position: 8
description: >-
  What the messages of the BBjShip goals and the bbjship command line mean, and
  what to do about each, for setup, deploys, development runs, the command line
  and Windows.
_i18n_hash: 2db5dad70f56c06a6aab75752a9c260f
---
# Dépannage <DocChip chip='since' label='26.03' /> {#troubleshooting}

Cette page répertorie les messages que vous pouvez rencontrer lors de la configuration de BBjShip, du déploiement d’une application, du lancement d’une exécution de développement ou de l’utilisation de la ligne de commande `bbjship`. Pour chaque message, elle en indique la cause et la marche à suivre.

Un objectif Maven en échec termine la compilation par un court message indiquant l’objectif, suivi de la raison. Cette page répertorie les raisons :

```text
BBj deployment failed. Check the application package, dependencies and deployment settings: BBj deployment failed with HTTP 409. Entry class com.example.Application is already published as inventory. Undeploy inventory first
```

## Identifiants et connexion {#credentials-and-connection}

Ces messages peuvent provenir de tout objectif qui communique avec BBjShip : `bbj-setup`, `bbj-deploy`, `bbj-undeploy` et `watch`.

| Message | Cause | Marche à suivre |
|---|---|---|
| `Warning: the BBj factory administrator account admin is in use with its factory password...` | Aucun nom d’utilisateur ni mot de passe n’est configuré ; l’objectif se connecte donc avec le compte `admin` et le mot de passe d’usine `admin123`. | Modifiez le mot de passe dans Enterprise Manager et configurez les nouveaux identifiants. Voir [Paramètres](/docs/configuration/bbjship/deploying#settings). |
| `The BBj administration user name is missing`, `The BBj administration password is missing` | Un seul des deux éléments, le nom d’utilisateur ou le mot de passe, est configuré. | Configurez les deux, ou aucun pour utiliser le compte d’usine. `bbj-deploy` et `bbj-undeploy` acceptent également un jeton. |
| `BBj deployment requires bbj.deployment.url...` | Aucune URL de déploiement n’est configurée. | Définissez `<bbj><deployment><url>` ou transmettez `-Dwebforj.bbj.deployment.url=<URL>`. |
| `BBj deployment URL must be an HTTP or HTTPS URL without credentials, query or fragment` | L’URL utilise un autre schéma ou contient un nom d’utilisateur, une requête ou un fragment. | Utilisez `http://<host>:<port>/webforj-bbjship` ou `https://<host>:<port>/webforj-bbjship` et placez les identifiants dans `<admin>`. |
| `Connect to ... failed: Connection refused` | Aucun service ne répond sur l’hôte et le port de l’URL de déploiement. | Démarrez BBjServices et vérifiez que l’hôte et le port de l’URL correspondent à ceux du serveur Web BBjServices. |
| `could not read the BBjShip version at <URL>, HTTP 404...` | L’URL ne mène pas à BBjShip, ou BBjShip n’est pas installé ou son point de terminaison n’est pas activé sur ce port. | Vérifiez le chemin `/webforj-bbjship` et le port. Sur la machine BBj, exécutez `mvn webforj:bbj-setup`, ou `bbjship remote enable` sur un serveur. |
| `failed with HTTP 401. Check the BBj administration credentials or token` | BBjServices a refusé le nom d’utilisateur et le mot de passe, ou le jeton. | Vérifiez les identifiants. Renouvelez dans Enterprise Manager tout jeton expiré ou révoqué. |
| `BBjShip <version> at <URL> does not match webforJ <version>...` | Les versions de BBjShip installé et de `webforj-maven-plugin` sont différentes. | Sur votre machine, exécutez `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`. Sur un serveur, installez la version correspondante de BBjShip avec le BBj Plugin Manager. |
| `could not establish a trusted HTTPS connection to <URL>...` | Java ne fait pas confiance au certificat du serveur Web BBjServices. | Installez sur la machine BBj un certificat pour le nom d’hôte de l’URL, ou ajoutez son certificat au magasin de confiance Java de la compilation. |
| `BBj deployment URL <URL> sends the administration credentials over an unencrypted connection to another machine...` | L’URL de déploiement utilise `http://` et pointe vers une autre machine. L’objectif s’arrête avant d’envoyer quoi que ce soit. Avec `allowUnencrypted`, le même texte est un avertissement et l’objectif continue. | Utilisez une URL `https://` avec le port SSL, ou autorisez HTTP des deux côtés. Voir [Déploiement depuis une autre machine](/docs/configuration/bbjship/deploying#deploying-from-another-machine). |

## Configuration {#setup}

Ces messages proviennent de `mvn webforj:bbj-setup` et de la première [exécution de développement](/docs/configuration/deploy-reload/bbjservices) d’un projet, qui configure BBjShip de la même manière.

| Message | Cause | Marche à suivre |
|---|---|---|
| `BBj setup failed. Check the BBj plugin artifact and setup settings` | L’objectif a échoué. La raison est indiquée ensuite. Une raison liée à la résolution d’artefact signifie que Maven n’a pas pu télécharger `webforj-bbjship-plugin` dans la version de `webforj-maven-plugin`. | Lisez la raison. En cas de problème de résolution d’artefact, vérifiez les dépôts et l’accès réseau de la compilation. |
| `BBj setup is local only. Run setup on the BBj host` | L’URL de déploiement désigne une autre machine. | Exécutez la configuration sur la machine BBj, ou installez-y BBjShip avec le [BBj Plugin Manager](/docs/configuration/bbjship/installing#through-the-bbj-plugin-manager). |
| `BBj setup cannot resolve the deployment URL host <host>...` | Le nom d’hôte de l’URL de déploiement est inconnu. | Utilisez `localhost` ou le nom de cette machine. |
| `BBj setup requires the administration password. Configure bbj.admin.password instead of a token` | Un jeton est configuré pour une exécution de développement. Les exécutions de développement se connectent uniquement avec un nom d’utilisateur et un mot de passe. | Configurez `<admin><username>` et `<admin><password>`. |
| `Cannot detect a BBj installation. Configure bbj.home or BBJ_HOME` | L’objectif n’a trouvé aucune installation BBj sur cette machine. | Définissez `<bbj><home>`, `-Dwebforj.bbj.home` ou la variable `BBJ_HOME` sur le dossier d’installation de BBj. |
| `Invalid BBj home. Configure a local BBj installation`, `BBj executable is missing or not executable. Check bbj.home` | Le dossier configuré ne contient pas une installation BBj complète. | Indiquez le dossier contenant `bin` et `cfg` de BBj. |
| `Installed BBjShip version <version> does not match <version>...` | Une autre version de BBjShip est installée, par exemple à la suite d’une mise à niveau de webforJ. | Exécutez `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`. Les applications déployées restent en place. |
| `Installed BBjShip version is missing...` | Le dossier BBjShip installé est incomplet. | Exécutez `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`. |
| `The plugin directory is a symbolic link...`, `BBj plugin destination must not contain symbolic links` | Le dossier `plugins` de BBj, ou le dossier BBjShip qu’il contient, est un lien symbolique ou en contient un. | Installez dans un dossier ordinaire. |
| `BBj setup command failed. Check credentials and local BBjServices.` suivi de la sortie de la commande | La commande qui active le point de terminaison de déploiement a échoué. La sortie indique la raison, par exemple un refus de connexion ou un port sur lequel le serveur Web n’écoute pas. | Vérifiez les identifiants, le fonctionnement de BBjServices et que le port de l’URL de déploiement est bien un port du serveur Web BBjServices. |
| `BBj setup command timed out. Check local BBjServices` | La commande ne s’est pas terminée dans le délai de 120 secondes. | Vérifiez que BBjServices fonctionne et répond, puis relancez l’objectif. |
| `Previous BBjShip files are preserved at <folder>` | Le remplacement a échoué et les fichiers de la version précédente ont été conservés dans ce dossier. | Corrigez la cause de l’échec et relancez l’objectif. |

## Déploiement et retrait du déploiement {#deploy-and-undeploy}

### Avant l’envoi {#before-the-upload}

Ces messages proviennent de `bbj-package`, `bbj-deploy` et `bbj-undeploy`, avant que quoi que ce soit n’atteigne BBjShip.

| Message | Cause | Marche à suivre |
|---|---|---|
| `BBj deployment requires jar packaging, this project is packaged as <packaging>` | Le projet n’est pas empaqueté sous forme de fichier jar. | Définissez `<packaging>jar</packaging>`. |
| `BBj deployment requires the packaged application jar...` | L’objectif a été exécuté sans la phase `package` dans la même compilation. | Exécutez `mvn install`, ou `mvn package webforj:bbj-deploy`. |
| `BBj deployment must not carry webforj-engine, BBj is the engine...` | `webforj-engine` figure dans le classpath d’exécution de l’application. | Excluez-le de la dépendance `webforj`. Voir [Sans le plug-in d’installation](/docs/configuration/bbjship/existing-projects#without-the-install-plugin). |
| `BBj deployment requires webforj-foundation on the runtime classpath` | L’application n’a aucune dépendance webforJ à l’exécution. | Ajoutez la dépendance `webforj` avec la portée `compile`. |
| `BBj deployment dependency is unresolved...` | Un fichier manque pour une dépendance de l’application. | Vérifiez que Maven résout toutes les dépendances du projet. |
| `Publish name <name> is invalid...` | Le nom de publication ne respecte pas les règles de [Noms de publication](/docs/configuration/bbjship/deploying#publish-names). | Choisissez une autre valeur pour `<publishName>`. |
| `The publish name <name> is reserved by BBjServices for its own web paths...` | BBjServices utilise ce nom pour ses propres adresses. | Choisissez une autre valeur pour `<publishName>`. |
| `BBj deployment archive could not be written...` | `bbj-package` n’a pas pu écrire `target/bbj/<publish name>.zip`. La raison est indiquée ensuite. | Lisez la raison. |

### Réponses de BBjShip {#answers-of-bbjship}

Lorsque BBjShip refuse un déploiement ou un retrait de déploiement, le message commence par `BBj deployment failed with HTTP <status>` ou `BBj undeployment failed with HTTP <status>`, suivi de la raison.

| Statut | Raison | Cause | Marche à suivre |
|---|---|---|---|
| 400 | `Publish name <name> is invalid...` ou `...is reserved by BBjServices...` | Le nom de publication est refusé. | Choisissez un autre nom de publication. |
| 400 | `BBj deployment webforj.conf is invalid. <detail>` | Le fichier `webforj.conf` de l’application est illisible, ou un [paramètre d’application](/docs/configuration/bbjship/deploying#app-settings) est du mauvais type. | Corrigez le fichier selon les indications de détail. |
| 400 | `The uploaded body is not a BBj deployment archive...`, `Invalid BBj deployment archive entry: <entry>` ou une autre raison concernant l’archive | L’archive n’a pas été créée par le plug-in de compilation ou a été modifiée par la suite. | Déployez l’archive créée par la compilation, avec la même version de webforJ que BBjShip. |
| 401 | `Check the BBj administration credentials or token` | La connexion a été refusée. | Vérifiez les identifiants ou renouvelez le jeton. |
| 403 | `BBjShip refuses the administration credentials over an unencrypted connection from another machine. Use the SSL port of the BBjServices web server` | La requête est arrivée par HTTP depuis une autre machine. | Utilisez l’adresse HTTPS du point de terminaison, ou exécutez `bbjship remote enable --allow-unencrypted` sur la machine BBj. |
| 403 | `Development runs are not enabled on this BBj machine. Run bbjship development enable` | Une exécution de développement a atteint une machine sur laquelle les exécutions de développement sont désactivées. | Exécutez `bbjship development enable` sur la machine BBj. |
| 404 | `No BBj application named <name> was deployed by BBjShip. Check the publish name` | Le retrait de déploiement désigne une application que BBjShip n’a pas déployée. | Vérifiez le nom de publication avec `bbjship apps list`. |
| 409 | `Entry class <class> is already published as <name>. Undeploy <name> first` | La classe d’entrée de l’application est déjà exécutée sous un autre nom de publication. | Retirez le déploiement de l’autre application, ou déployez sous son nom. |
| 409 | `Published BBj application <name> is not owned by BBjShip...` | Une application enregistrée dans Enterprise Manager utilise déjà ce nom. | Choisissez un autre nom de publication, ou supprimez cette application dans Enterprise Manager. |
| 409 | `Published BBj application <other> differs from <name> only by letter case...` | Une autre application utilise le même nom, avec une casse différente. | Choisissez un autre nom de publication. |
| 409 | `<name> is a development run. Stop the watch first` | Une exécution de développement de l’application est en cours. | Arrêtez le watch avec <kbd>Ctrl</kbd>+<kbd>C</kbd>, puis déployez. |
| 409 | `<name> is deployed from an archive. Undeploy it first` | Une exécution de développement utilise le nom d’une application déployée. | Retirez le déploiement de l’application, ou définissez une autre valeur pour `<publishName>` dans l’exécution de développement. |
| 413 | `BBj deployment archive exceeds the allowed size of 1 GiB` | L’archive dépasse la taille acceptée par BBjShip. | Supprimez les dépendances dont l’application n’a pas besoin à l’exécution. |
| 500 | `BBj deployment failed. Check the BBjServices log` | BBjServices a échoué lors de l’installation ou de la suppression de l’application. | Consultez les journaux de BBjServices. |

Un déploiement qui se termine par `did not confirm the requested application. Check the BBjShip version and BBjServices log` a atteint BBjShip, mais n’a reçu aucune confirmation pour l’application. Vérifiez que BBjShip et `webforj-maven-plugin` ont la même version, puis consultez le journal de BBjServices.

### L’application en cours d’exécution {#the-running-app}

| Message | Cause | Marche à suivre |
|---|---|---|
| `Icons protocol requires webforj.assetsSource = packaged when running with BBj Services` | L’application utilise `icons://` sans ressources empaquetées. | Ajoutez `webforj.assetsSource = packaged` à `webforj.conf`, puis redéployez. |

## Exécutions de développement {#development-runs}

Ces messages proviennent de `mvn` ou de `mvn compile webforj:watch` dans un projet configuré pour BBjShip. Une exécution de développement en échec commence par `BBj development run failed. Check the deployment settings and that development runs are enabled on the BBj machine`, suivi de la raison issue de [Configuration](#setup), des [Identifiants et connexion](#credentials-and-connection) ou des [Réponses de BBjShip](#answers-of-bbjship).

| Message | Cause | Marche à suivre |
|---|---|---|
| `The class folder <folder> does not exist. Run compile before webforj:watch` | Le watch a démarré avant la compilation du projet. | Exécutez `mvn compile webforj:watch`, ou `mvn` dans un projet généré à partir d’un archétype. |
| `BBj development class folder must be an absolute directory on the BBj machine` | `<development><classFolder>` désigne un dossier introuvable par BBjServices. | Définissez le chemin absolu du dossier de classes sur la machine BBj. |
| `BBjServices properties file not found at <file>. Configure bbj.properties` | Le fichier de propriétés BBjServices ne se trouve pas à l’emplacement recherché par l’exécution. | Définissez `<bbj><properties>` ou `-Dwebforj.bbj.properties` sur le fichier. |
| `BBjServices did not stop within 180 seconds` | L’exécution a demandé l’arrêt de BBjServices pour appliquer les paramètres de hotswap, mais le service a continué de fonctionner. | Arrêtez BBjServices vous-même, redémarrez-le, puis relancez le watch. |
| `BBjServices start program is missing: <program>` | L’installation de BBj ne contient aucun programme permettant de démarrer BBjServices. | Démarrez BBjServices vous-même, puis relancez le watch. |
| `BBjServices did not start. The program ended with exit code <code>`, `BBjServices did not start within 120 seconds` | BBjServices n’a pas redémarré après son arrêt. | Démarrez BBjServices vous-même, puis relancez le watch. |
| `BBjShip did not answer within 120 seconds` | BBjServices a démarré, mais le point de terminaison de déploiement n’a pas répondu. | Consultez le journal de BBjServices, puis relancez le watch. |
| `BBjServices restarted without the hotswap settings. Check <file>` | BBjServices a démarré avec des arguments Java différents de ceux écrits dans le fichier de propriétés. | Vérifiez les paramètres Java de BBjServices dans Enterprise Manager et dans ce fichier. |
| `webforJ watch: development run of <name> was not stopped. <reason>` | L’application n’a pas pu être supprimée à l’arrêt de la compilation. | Supprimez-la avec `bbjship apps undeploy <name>`, ou relancez le watch, qui la remplacera. |

La première exécution de développement qui ajoute les paramètres de hotswap redémarre BBjServices. Sur macOS et Linux, elle démarre BBjServices avec `bin/bbjservices`. Sous Windows, elle démarre le service Windows `BBjServices` s’il est enregistré ; sinon, elle exécute `bin\BBjServices.exe -noservice`. Un BBjServices démarré avec `bin/bbjservices` ou `-noservice` s’exécute hors du gestionnaire de services du système d’exploitation jusqu’au redémarrage de la machine.

## Ligne de commande {#command-line}

Chaque commande `bbjship` s’exécute dans BBjServices ; BBjServices doit donc être en cours d’exécution. Sans lui, la commande ne peut pas démarrer.

Les arguments non valides se terminent par le code de sortie `2` et un message qui indique ce qu’il faut spécifier :

| Message | Marche à suivre |
|---|---|
| `Specify a command. Use --help for available commands`, `Unknown command <command>...` | Indiquez une commande. Voir [Ligne de commande](/docs/configuration/bbjship/command-line). |
| `Unknown option <option>...`, `Option <option> is not available for <command>...` | Utilisez une option prise en charge par cette commande, comme indiqué par `--help`. |
| `Specify a user name after --username`, `Specify a password after --password`, `Specify a port after --port` | Donnez une valeur à l’option. |
| `Specify --port from 1 to 65535`, `Specify --ssl-port from 1 to 65535` | Indiquez un numéro de port. |
| `Specify <option> only once` | Supprimez l’option en double. |
| `Specify only one of --password and --password-stdin` | Ne conservez qu’une seule des deux options. |
| `The BBj administration password is missing. Specify --username and --password <value> or --password-stdin, or set WEBFORJ_BBJ_USERNAME and WEBFORJ_BBJ_PASSWORD` | Indiquez ensemble le nom d’utilisateur et le mot de passe. Le message précise lequel manque. |
| `Specify an application name or --all`, `Specify one application name` | Indiquez un nom de publication ou `--all` pour `apps undeploy`. |
| `Specify the path of a deployment archive ending in .zip`, `Specify one deployment archive` | Indiquez un fichier `.zip` pour `apps deploy`. |
| `Deployment archive <path> does not exist`, `Deployment archive <path> cannot be read` | Vérifiez le chemin. BBjServices lit le fichier ; l’utilisateur du système d’exploitation qui exécute BBjServices doit donc disposer d’un accès en lecture. |
| `Development archives are deployed by the watch, not by apps deploy` | Déployez l’archive créée par `bbj-package`, pas celle d’une exécution de développement. |
| `Publish name <name> is invalid...`, `The publish name <name> is reserved...` | Renommez l’archive. Son nom de fichier sans `.zip` correspond au nom de publication. |

Une commande en échec se termine par le code de sortie `1` et par `Command failed.`, suivi de la raison :

| Raison | Cause | Marche à suivre |
|---|---|---|
| Un échec de connexion à BBjServices | Le nom d’utilisateur ou le mot de passe est incorrect. | Vérifiez les identifiants. |
| `The BBjServices web server does not answer on port <port>. Use the HTTP port of the BBjServices web server` | `--port` ou `--ssl-port` ne correspond pas à un port sur lequel le serveur Web BBjServices écoute. Le point de terminaison précédent reste actif. | Utilisez les ports configurés pour le serveur Web dans Enterprise Manager. |
| `Application <name> is not deployed by BBjShip` | `apps undeploy` désigne une application que BBjShip n’a pas déployée. | Vérifiez le nom avec `bbjship apps list`. |
| `Another deployment is in progress` | Un autre déploiement ou retrait de déploiement a verrouillé la machine pendant 30 secondes. | Attendez la fin de l’opération, puis relancez la commande. |
| `The BBjShip context belongs to another application`, `The assets context belongs to another application` | Une autre application dans Enterprise Manager utilise le contexte Web `webforj-bbjship` ou `webforj-bbjship-assets`. | Renommez ou supprimez ce contexte dans Enterprise Manager. |
| `The BBjShip command classpath belongs to another application`, `The BBjShip base classpath contains an unowned library` | Les classpaths `_webforj_bbjship_cli` ou `_webforj_bbjship` dans Enterprise Manager ont été modifiés. | Supprimez les deux classpaths dans Enterprise Manager et relancez la commande, qui les recréera. |
| `The BBjShip servlet is missing. Check the plugin installation`, `The BBjShip library is missing. Check the plugin installation` | Le dossier BBjShip est incomplet. | Réinstallez BBjShip. |
| `BBjServices does not manage web contexts. Update BBj` | La version de BBj est trop ancienne pour BBjShip. | Installez la version de BBj correspondant à webforJ. |

`reset` se termine par le code de sortie `1` et par `Reset cancelled` si la réponse à `Continue? [y/N]` n’est ni `y` ni `Y`. S’il a restauré les paramètres Java de BBjServices, il se termine par `Restart BBjServices now to apply the restored Java settings`. Redémarrez BBjServices pour l’exécuter sans les paramètres de hotswap.

## Windows {#windows}

| Situation | Marche à suivre |
|---|---|
| L’exécution de `bbj.exe` avec le programme BBjShip n’affiche rien. | Exécutez `bbjship.cmd` dans `<BBj home>\plugins\webforj-bbjship` pour afficher la sortie de la commande. |
| `--password-stdin` se termine par le code de sortie `2` et `Standard input is not available on Windows. Use --password <value> or WEBFORJ_BBJ_PASSWORD`. | Transmettez le mot de passe avec `--password`, ou définissez `WEBFORJ_BBJ_USERNAME` et `WEBFORJ_BBJ_PASSWORD`. |
| `reset` se termine par le code de sortie `2` et `Standard input is not available on Windows. Run reset --yes to confirm`. | Exécutez `bbjship.cmd reset --yes`. |
| `apps undeploy` ou `reset` affiche `Not removed: <folder>. <reason>` et se termine par le code de sortie `0`. | BBjServices utilise encore les fichiers de l’application. L’application est supprimée d’Enterprise Manager. Redémarrez BBjServices. Le dossier sera supprimé lors du prochain déploiement portant ce nom, ou vous pourrez le supprimer après le redémarrage. |
| L’installation de BBjShip par-dessus une version déjà installée échoue, car ses fichiers sont utilisés. | BBjServices utilise encore les fichiers de BBjShip installé. Redémarrez BBjServices, puis relancez l’installation. |
| BBjServices ne fonctionne pas comme service Windows après avoir été redémarré par une exécution de développement. | L’exécution de développement l’a démarré avec `BBjServices.exe -noservice`, car aucun service `BBjServices` n’est enregistré. Il s’exécute hors du gestionnaire de services Windows jusqu’au redémarrage de la machine. |
| Une commande `bbjship.cmd` ne peut pas démarrer. | Démarrez BBjServices. Toutes les commandes `bbjship` nécessitent qu’il soit en cours d’exécution. |
