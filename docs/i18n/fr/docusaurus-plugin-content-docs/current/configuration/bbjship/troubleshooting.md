---
title: Dépannage
sidebar_position: 8
description: >-
  What the messages of the BBjShip goals and the bbjship command line mean, and
  what to do about each, for setup, deploys, development runs, the command line
  and Windows.
_i18n_hash: c517c84b00b3b7eb6bb9d485c528c4f7
---
# Dépannage <DocChip chip='since' label='26.03' /> {#troubleshooting}

Cette page répertorie les messages que vous pouvez rencontrer lors de la configuration de BBjShip, du déploiement d’une application, du démarrage d’une exécution de développement ou de l’utilisation de la ligne de commande `bbjship`. Pour chaque message, elle indique la cause et la marche à suivre.

Un objectif Maven en échec arrête la compilation avec un message court indiquant l’objectif, suivi de la raison. Cette page répertorie la raison :

```text
BBj deployment failed. Check the application package, dependencies and deployment settings: BBj deployment failed with HTTP 409. Entry class com.example.Application is already published as inventory. Undeploy inventory first
```

## Identifiants et connexion {#credentials-and-connection}

Ces messages peuvent être générés par tout objectif qui communique avec BBjShip : `bbj-setup`, `bbj-deploy`, `bbj-undeploy` et `watch`.

| Message | Cause | Marche à suivre |
|---|---|---|
| `Warning: the BBj factory administrator account admin is in use with its factory password...` | Aucun nom d’utilisateur ni mot de passe n’est configuré ; l’objectif se connecte donc en tant que `admin` avec le mot de passe `admin123`. | Changez le mot de passe dans Enterprise Manager et configurez les nouveaux identifiants. Consultez [Paramètres](/docs/configuration/bbjship/deploying#settings). |
| `The BBj administration user name is missing`, `The BBj administration password is missing` | Un seul des deux éléments — nom d’utilisateur ou mot de passe — est configuré. | Configurez les deux, ou aucun pour utiliser le compte par défaut. `bbj-deploy` et `bbj-undeploy` acceptent aussi un jeton. |
| `BBj deployment requires bbj.deployment.url...` | Aucune URL de déploiement n’est configurée. | Définissez `<bbj><deployment><url>` ou passez `-Dwebforj.bbj.deployment.url=<URL>`. |
| `BBj deployment URL must be an HTTP or HTTPS URL without credentials, query or fragment` | L’URL utilise un autre schéma, ou contient un nom d’utilisateur, une chaîne de requête ou un fragment. | Utilisez `http://<host>:<port>/webforj-bbjship` ou `https://<host>:<port>/webforj-bbjship`, et placez les identifiants dans `<admin>`. |
| `Connect to ... failed: Connection refused` | Aucun service ne répond sur l’hôte et le port de l’URL de déploiement. | Démarrez BBjServices et vérifiez que l’hôte et le port de l’URL correspondent au serveur Web de BBjServices. |
| `could not read the BBjShip version at <URL>, HTTP 404...` | L’URL ne pointe pas vers BBjShip, BBjShip n’est pas installé ou son point de terminaison n’est pas activé sur ce port. | Vérifiez le chemin `/webforj-bbjship` et le port. Sur la machine BBj, exécutez `mvn webforj:bbj-setup` ou, sur un serveur, `bbjship remote enable`. |
| `failed with HTTP 401. Check the BBj administration credentials or token` | BBjServices a refusé le nom d’utilisateur et le mot de passe, ou le jeton. | Vérifiez les identifiants. Renouvelez dans Enterprise Manager tout jeton expiré ou révoqué. |
| `BBjShip <version> at <URL> does not match webforJ <version>...` | Les versions de BBjShip installé et de `webforj-maven-plugin` sont différentes. | Sur votre machine, exécutez `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`. Sur un serveur, installez la version de BBjShip correspondante avec le BBj Plugin Manager. |
| `could not establish a trusted HTTPS connection to <URL>...` | Java ne fait pas confiance au certificat du serveur Web BBjServices. | Installez sur la machine BBj un certificat correspondant au nom d’hôte de l’URL, ou ajoutez ce certificat au magasin de confiance Java de la compilation. |
| `BBj deployment URL <URL> sends the administration credentials over an unencrypted connection to another machine...` | L’URL de déploiement est une URL `http://` vers une autre machine. L’objectif s’arrête avant d’envoyer quoi que ce soit. Avec `allowUnencrypted`, le même message devient un avertissement et l’objectif continue. | Utilisez une URL `https://` avec le port SSL, ou autorisez HTTP des deux côtés. Consultez [Déployer depuis une autre machine](/docs/configuration/bbjship/deploying#deploying-from-another-machine). |

## Configuration {#setup}

Ces messages proviennent de `mvn webforj:bbj-setup` et de la première [exécution de développement](/docs/configuration/deploy-reload/bbjservices) d’un projet, qui configure BBjShip de la même manière.

| Message | Cause | Marche à suivre |
|---|---|---|
| `BBj setup failed. Check the BBj plugin artifact and setup settings` | L’objectif a échoué. La raison suit ce message. Une raison relative à la résolution de l’artefact signifie que Maven n’a pas pu télécharger `webforj-bbjship-plugin` dans la version de `webforj-maven-plugin`. | Consultez la raison. En cas de problème de résolution de l’artefact, vérifiez les dépôts et l’accès réseau de la compilation. |
| `BBj setup is local only. Run setup on the BBj host` | L’URL de déploiement désigne une autre machine. | Exécutez la configuration sur la machine BBj, ou installez-y BBjShip avec le [BBj Plugin Manager](/docs/configuration/bbjship/installing#through-the-bbj-plugin-manager). |
| `BBj setup cannot resolve the deployment URL host <host>...` | Le nom d’hôte de l’URL de déploiement est inconnu. | Utilisez `localhost` ou le nom de cette machine. |
| `BBj setup requires the administration password. Configure bbj.admin.password instead of a token` | Un jeton est configuré pour une exécution de développement. Les exécutions de développement se connectent uniquement avec un nom d’utilisateur et un mot de passe. | Configurez `<admin><username>` et `<admin><password>`. |
| `Cannot detect a BBj installation. Configure bbj.home or BBJ_HOME` | L’objectif n’a trouvé aucune installation de BBj sur cette machine. | Définissez `<bbj><home>`, `-Dwebforj.bbj.home` ou la variable `BBJ_HOME` sur le dossier d’installation de BBj. |
| `Invalid BBj home. Configure a local BBj installation`, `BBj executable is missing or not executable. Check bbj.home` | Le dossier configuré ne correspond pas à une installation BBj complète. | Indiquez le dossier contenant les répertoires `bin` et `cfg` de BBj. |
| `Installed BBjShip version <version> does not match <version>...` | Une autre version de BBjShip est installée, par exemple après une mise à niveau de webforJ. | Exécutez `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`. Les applications déployées restent déployées. |
| `Installed BBjShip version is missing...` | Le dossier BBjShip installé est incomplet. | Exécutez `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`. |
| `The plugin directory is a symbolic link...`, `BBj plugin destination must not contain symbolic links` | Le dossier `plugins` de BBj, ou le dossier BBjShip qu’il contient, est un lien symbolique ou en contient un. | Effectuez l’installation dans un dossier normal. |
| `BBj setup command failed. Check credentials and local BBjServices.` suivi de la sortie de la commande | La commande qui active le point de terminaison de déploiement a échoué. La sortie indique la raison, par exemple un refus de connexion ou un port sur lequel le serveur Web n’écoute pas. | Vérifiez les identifiants, le fonctionnement de BBjServices et que le port de l’URL de déploiement est bien un port du serveur Web BBjServices. |
| `BBj setup command timed out. Check local BBjServices` | La commande ne s’est pas terminée dans les 120 secondes. | Vérifiez que BBjServices fonctionne et répond, puis relancez l’objectif. |
| `Previous BBjShip files are preserved at <folder>` | Le remplacement a échoué et les fichiers de la version précédente ont été conservés dans ce dossier. | Corrigez la cause de l’échec et relancez l’objectif. |

## Déploiement et retrait du déploiement {#deploy-and-undeploy}

### Avant le transfert {#before-the-upload}

Ces messages proviennent de `bbj-package`, `bbj-deploy` et `bbj-undeploy`, avant que quoi que ce soit n’atteigne BBjShip.

| Message | Cause | Marche à suivre |
|---|---|---|
| `BBj deployment requires jar packaging, this project is packaged as <packaging>` | Le projet n’est pas empaqueté sous forme de fichier JAR. | Définissez `<packaging>jar</packaging>`. |
| `BBj deployment requires the packaged application jar...` | L’objectif a été exécuté sans la phase `package` dans la même compilation. | Exécutez `mvn install` ou `mvn package webforj:bbj-deploy`. |
| `BBj deployment must not carry webforj-engine, BBj is the engine...` | `webforj-engine` figure dans le classpath d’exécution de l’application. | Excluez-le de la dépendance `webforj`. Consultez [Sans le plug-in d’installation](/docs/configuration/bbjship/existing-projects#without-the-install-plugin). |
| `BBj deployment requires webforj-foundation on the runtime classpath` | L’application n’a aucune dépendance webforJ à l’exécution. | Ajoutez la dépendance `webforj` avec la portée `compile`. |
| `BBj deployment dependency is unresolved...` | Un fichier est manquant pour une dépendance de l’application. | Vérifiez que Maven résout toutes les dépendances du projet. |
| `Publish name <name> is invalid...` | Le nom de publication ne respecte pas les règles de la section [Noms de publication](/docs/configuration/bbjship/deploying#publish-names). | Choisissez une autre valeur pour `<publishName>`. |
| `The publish name <name> is reserved by BBjServices for its own web paths...` | BBjServices utilise ce nom pour ses propres adresses. | Choisissez une autre valeur pour `<publishName>`. |
| `BBj deployment archive could not be written...` | `bbj-package` n’a pas pu écrire `target/bbj/<publish name>.zip`. La raison suit ce message. | Consultez la raison. |

### Réponses de BBjShip {#answers-of-bbjship}

Lorsque BBjShip refuse un déploiement ou un retrait du déploiement, le message commence par `BBj deployment failed with HTTP <status>` ou `BBj undeployment failed with HTTP <status>`, suivi de la raison.

| Statut | Raison | Cause | Marche à suivre |
|---|---|---|---|
| 400 | `Publish name <name> is invalid...` ou `...is reserved by BBjServices...` | Le nom de publication est refusé. | Choisissez un autre nom de publication. |
| 400 | `BBj deployment webforj.conf is invalid. <detail>` | Le fichier `webforj.conf` de l’application est illisible, ou un [paramètre d’application](/docs/configuration/bbjship/deploying#app-settings) est du mauvais type. | Corrigez le fichier selon les indications fournies. |
| 400 | `The uploaded body is not a BBj deployment archive...`, `Invalid BBj deployment archive entry: <entry>` ou une autre raison relative à l’archive | L’archive n’a pas été créée par le plug-in de compilation, ou a été modifiée par la suite. | Déployez l’archive produite par la compilation, avec la même version de webforJ que BBjShip. |
| 401 | `Check the BBj administration credentials or token` | La connexion a été refusée. | Vérifiez les identifiants ou renouvelez le jeton. |
| 403 | `BBjShip refuses the administration credentials over an unencrypted connection from another machine. Use the SSL port of the BBjServices web server` | La requête est arrivée par HTTP depuis une autre machine. | Utilisez l’adresse HTTPS du point de terminaison ou exécutez `bbjship remote enable --allow-unencrypted` sur la machine BBj. |
| 403 | `Development runs are not enabled on this BBj machine. Run bbjship development enable` | Une exécution de développement a atteint une machine où ces exécutions sont désactivées. | Exécutez `bbjship development enable` sur la machine BBj. |
| 404 | `No BBj application named <name> was deployed by BBjShip. Check the publish name` | La commande de retrait du déploiement désigne une application que BBjShip n’a pas déployée. | Vérifiez le nom de publication avec `bbjship apps list`. |
| 409 | `Entry class <class> is already published as <name>. Undeploy <name> first` | La classe d’entrée de l’application est déjà exécutée sous un autre nom de publication. | Retirez le déploiement de l’autre application ou déployez sous son nom. |
| 409 | `Published BBj application <name> is not owned by BBjShip...` | Une application enregistrée dans Enterprise Manager utilise déjà ce nom. | Choisissez un autre nom de publication ou supprimez cette application dans Enterprise Manager. |
| 409 | `Published BBj application <other> differs from <name> only by letter case...` | Une autre application utilise le même nom, avec une casse différente. | Choisissez un autre nom de publication. |
| 409 | `<name> is a development run. Stop the watch first` | Une exécution de développement de l’application est active. | Arrêtez le processus `watch` avec `Ctrl+C`, puis déployez l’application. |
| 409 | `<name> is deployed from an archive. Undeploy it first` | Une exécution de développement utilise le nom d’une application déployée. | Retirez le déploiement de l’application ou définissez une autre valeur pour `<publishName>` dans l’exécution de développement. |
| 413 | `BBj deployment archive exceeds the allowed size of 1 GiB` | L’archive dépasse la taille maximale acceptée par BBjShip. | Supprimez les dépendances dont l’application n’a pas besoin à l’exécution. |
| 500 | `BBj deployment failed. Check the BBjServices log` | BBjServices a échoué lors de l’installation ou du retrait de l’application. | Consultez les journaux de BBjServices. |

Un déploiement qui se termine par `did not confirm the requested application. Check the BBjShip version and BBjServices log` a bien atteint BBjShip, mais n’a reçu aucune confirmation pour l’application. Vérifiez que BBjShip et `webforj-maven-plugin` ont la même version, puis consultez le journal de BBjServices.

### Application en cours d’exécution {#the-running-app}

| Message | Cause | Marche à suivre |
|---|---|---|
| `Icons protocol requires webforj.assetsSource = packaged when running with BBj Services` | L’application utilise `icons://` sans ressources empaquetées. | Ajoutez `webforj.assetsSource = packaged` à `webforj.conf`, puis redéployez l’application. |

## Exécutions de développement {#development-runs}

Ces messages proviennent de `mvn` ou de `mvn compile webforj:watch` dans un projet configuré pour BBjShip. Une exécution de développement qui échoue commence par `BBj development run failed. Check the deployment settings and that development runs are enabled on the BBj machine`, suivi de la raison provenant de la section [Configuration](#setup), [Identifiants et connexion](#credentials-and-connection) ou [Réponses de BBjShip](#answers-of-bbjship).

| Message | Cause | Marche à suivre |
|---|---|---|
| `The class folder <folder> does not exist. Run compile before webforj:watch` | Le processus `watch` a démarré avant la compilation du projet. | Exécutez `mvn compile webforj:watch`, ou `mvn` dans un projet d’archétype. |
| `BBj development class folder must be an absolute directory on the BBj machine` | `<development><classFolder>` désigne un dossier introuvable par BBjServices. | Définissez le chemin absolu du dossier de classes sur la machine BBj. |
| `BBjServices properties file not found at <file>. Configure bbj.properties` | Le fichier de propriétés BBjServices ne se trouve pas à l’emplacement recherché par l’exécution. | Définissez `<bbj><properties>` ou `-Dwebforj.bbj.properties` sur le fichier. |
| `BBjServices did not stop within 180 seconds` | L’exécution a demandé l’arrêt de BBjServices pour appliquer les paramètres de hotswap, mais le service est resté actif. | Arrêtez BBjServices vous-même, redémarrez-le, puis relancez `watch`. |
| `BBjServices start program is missing: <program>` | L’installation de BBj ne contient aucun programme permettant de démarrer BBjServices. | Démarrez BBjServices vous-même, puis relancez `watch`. |
| `BBjServices did not start. The program ended with exit code <code>`, `BBjServices did not start within 120 seconds` | BBjServices n’a pas redémarré après son arrêt. | Démarrez BBjServices vous-même, puis relancez `watch`. |
| `BBjShip did not answer within 120 seconds` | BBjServices a démarré, mais le point de terminaison de déploiement n’a pas répondu. | Consultez le journal de BBjServices, puis relancez `watch`. |
| `BBjServices restarted without the hotswap settings. Check <file>` | BBjServices a démarré avec des arguments Java différents de ceux inscrits dans le fichier de propriétés. | Vérifiez les paramètres Java de BBjServices dans Enterprise Manager et dans ce fichier. |
| `webforJ watch: development run of <name> was not stopped. <reason>` | L’application n’a pas pu être supprimée à l’arrêt de la compilation. | Supprimez-la avec `bbjship apps undeploy <name>` ou redémarrez `watch`, qui la remplacera. |

La première exécution de développement qui ajoute les paramètres de hotswap redémarre BBjServices. Elle démarre BBjServices avec `bin/bbjservices` sur macOS et Linux. Sur Windows, elle démarre le service Windows `BBjServices` s’il est enregistré, et exécute `bin\BBjServices.exe -noservice` dans le cas contraire. Un BBjServices démarré avec `bin/bbjservices` ou `-noservice` s’exécute en dehors du gestionnaire de services du système d’exploitation jusqu’au redémarrage de la machine.

## Ligne de commande {#command-line}

Chaque commande `bbjship` s’exécute dans BBjServices ; BBjServices doit donc être en cours d’exécution. Sans lui, la commande ne peut pas démarrer.

Les arguments non valides entraînent le code de sortie `2` et un message indiquant ce qu’il faut spécifier :

| Message | Marche à suivre |
|---|---|
| `Specify a command. Use --help for available commands`, `Unknown command <command>...` | Indiquez une commande. Consultez la [ligne de commande](/docs/configuration/bbjship/command-line). |
| `Unknown option <option>...`, `Option <option> is not available for <command>...` | Utilisez une option disponible pour cette commande, comme indiqué par `--help`. |
| `Specify a user name after --username`, `Specify a password after --password`, `Specify a port after --port` | Fournissez une valeur pour l’option. |
| `Specify --port from 1 to 65535`, `Specify --ssl-port from 1 to 65535` | Fournissez un numéro de port. |
| `Specify <option> only once` | Supprimez l’option en double. |
| `Specify only one of --password and --password-stdin` | Conservez une seule des deux options. |
| `The BBj administration password is missing. Specify --username and --password <value> or --password-stdin, or set WEBFORJ_BBJ_USERNAME and WEBFORJ_BBJ_PASSWORD` | Fournissez le nom d’utilisateur et le mot de passe ensemble. Le message indique l’élément manquant. |
| `Specify an application name or --all`, `Specify one application name` | Fournissez à `apps undeploy` un nom de publication ou `--all`. |
| `Specify the path of a deployment archive ending in .zip`, `Specify one deployment archive` | Fournissez un fichier `.zip` à `apps deploy`. |
| `Deployment archive <path> does not exist`, `Deployment archive <path> cannot be read` | Vérifiez le chemin. BBjServices lit le fichier ; l’utilisateur du système d’exploitation qui exécute BBjServices doit donc pouvoir le lire. |
| `Development archives are deployed by the watch, not by apps deploy` | Déployez l’archive créée par `bbj-package`, et non celle d’une exécution de développement. |
| `Publish name <name> is invalid...`, `The publish name <name> is reserved...` | Renommez l’archive. Son nom de fichier sans `.zip` correspond au nom de publication. |

Une commande qui échoue se termine avec le code de sortie `1` et affiche `Command failed.`, suivi de la raison :

| Raison | Cause | Marche à suivre |
|---|---|---|
| Échec de connexion à BBjServices | Le nom d’utilisateur ou le mot de passe est incorrect. | Vérifiez les identifiants. |
| `The BBjServices web server does not answer on port <port>. Use the HTTP port of the BBjServices web server` | `--port` ou `--ssl-port` ne correspond pas à un port sur lequel le serveur Web BBjServices écoute. Le point de terminaison précédent reste actif. | Utilisez les ports configurés pour le serveur Web dans Enterprise Manager. |
| `Application <name> is not deployed by BBjShip` | `apps undeploy` désigne une application que BBjShip n’a pas déployée. | Vérifiez le nom avec `bbjship apps list`. |
| `Another deployment is in progress` | Un autre déploiement ou retrait du déploiement a verrouillé la machine pendant 30 secondes. | Attendez la fin de l’opération, puis relancez la commande. |
| `The BBjShip context belongs to another application`, `The assets context belongs to another application` | Une autre application dans Enterprise Manager utilise le contexte Web `webforj-bbjship` ou `webforj-bbjship-assets`. | Renommez ou supprimez ce contexte dans Enterprise Manager. |
| `The BBjShip command classpath belongs to another application`, `The BBjShip base classpath contains an unowned library` | Les classpaths `_webforj_bbjship_cli` ou `_webforj_bbjship` dans Enterprise Manager ont été modifiés. | Supprimez les deux classpaths dans Enterprise Manager, puis relancez la commande, qui les recréera. |
| `The BBjShip servlet is missing. Check the plugin installation`, `The BBjShip library is missing. Check the plugin installation` | Le dossier BBjShip est incomplet. | Réinstallez BBjShip. |
| `BBjServices does not manage web contexts. Update BBj` | La version de BBj est trop ancienne pour BBjShip. | Installez la version de BBj correspondant à webforJ. |

`reset` se termine avec le code de sortie `1` et affiche `Reset cancelled` si la réponse à `Continue? [y/N]` n’est ni `y` ni `Y`. Si la commande a restauré les paramètres Java de BBjServices, elle se termine en affichant `Restart BBjServices now to apply the restored Java settings`. Redémarrez BBjServices pour l’exécuter sans les paramètres de hotswap.

## Windows {#windows}

| Situation | Marche à suivre |
|---|---|
| L’exécution de `bbj.exe` avec le programme BBjShip n’affiche rien. | Exécutez `bbjship.cmd` dans `<BBj home>\plugins\webforj-bbjship` pour afficher la sortie de la commande. |
| `--password-stdin` se termine avec le code de sortie `2` et affiche `Standard input is not available on Windows. Use --password <value> or WEBFORJ_BBJ_PASSWORD`. | Passez le mot de passe avec `--password`, ou définissez `WEBFORJ_BBJ_USERNAME` et `WEBFORJ_BBJ_PASSWORD`. |
| `reset` se termine avec le code de sortie `2` et affiche `Standard input is not available on Windows. Run reset --yes to confirm`. | Exécutez `bbjship.cmd reset --yes`. |
| `apps undeploy` ou `reset` affiche `Not removed: <folder>. <reason>` et se termine avec le code de sortie `0`. | BBjServices a encore des fichiers de l’application ouverts. L’application est supprimée d’Enterprise Manager. Redémarrez BBjServices. Le dossier sera supprimé lors du prochain déploiement sous ce nom, ou vous pourrez le supprimer après le redémarrage. |
| L’installation de BBjShip par-dessus une version installée échoue, car des fichiers sont utilisés. | BBjServices a encore ouverts les fichiers de BBjShip installé. Redémarrez BBjServices, puis relancez l’installation. |
| BBjServices ne s’exécute plus comme service Windows après son redémarrage par une exécution de développement. | L’exécution de développement l’a démarré avec `BBjServices.exe -noservice`, car aucun service `BBjServices` n’est enregistré. Il s’exécute en dehors du gestionnaire de services Windows jusqu’au redémarrage de la machine. |
| Une commande `bbjship.cmd` ne démarre pas. | Démarrez BBjServices. Toutes les commandes `bbjship` nécessitent qu’il soit en cours d’exécution. |
