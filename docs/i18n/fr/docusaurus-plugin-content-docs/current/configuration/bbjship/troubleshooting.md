---
title: Dépannage
sidebar_position: 8
description: >-
  What the messages of the BBjShip goals and the bbjship command line mean, and
  what to do about each, for setup, deploys, development runs, the command line
  and Windows.
_i18n_hash: d9235c64dc2d56c5acc41e60b2a2f7df
---
# Dépannage <DocChip chip='since' label='26.03' /> {#troubleshooting}

Cette page répertorie les messages que vous pouvez rencontrer lors de la configuration de BBjShip, du déploiement d'une application, du démarrage d'une exécution de développement ou de l'utilisation de la ligne de commande `bbjship`. Pour chaque message, elle donne la cause et ce qu'il faut faire.

Un objectif Maven échouant termine la construction avec un message court sur l'objectif, suivi de la raison. La raison est la partie que cette page liste :

```text
Le déploiement BBj a échoué. Vérifiez le package d'application, les dépendances et les paramètres de déploiement : le déploiement BBj a échoué avec HTTP 409. La classe d'entrée com.example.Application est déjà publiée comme inventaire. Retirez d'abord l'inventaire.
```

## Identifiants et connexion {#credentials-and-connection}

Ces messages proviennent de chaque objectif qui communique avec BBjShip : `bbj-setup`, `bbj-deploy`, `bbj-undeploy` et `watch`.

| Message | Cause | Que faire |
|---|---|---|
| `Avertissement : le compte administrateur de la fabrique BBj admin est utilisé avec son mot de passe de fabrique...` | Aucun nom d'utilisateur ni mot de passe n'est configuré, donc l'objectif se connecte en tant que `admin` avec `admin123`. | Changez le mot de passe dans Enterprise Manager et configurez les nouveaux identifiants, voir [Paramètres](/docs/configuration/bbjship/deploying#settings). |
| `Le nom d'utilisateur d'administration BBj est manquant`, `Le mot de passe d'administration BBj est manquant` | Un seul des noms d'utilisateur et du mot de passe est configuré. | Configurez les deux, ou aucun pour utiliser le compte de fabrique. `bbj-deploy` et `bbj-undeploy` acceptent également un jeton. |
| `Le déploiement BBj nécessite bbj.deployment.url...` | Aucune URL de déploiement n'est configurée. | Définissez `<bbj><deployment><url>` ou passez `-Dwebforj.bbj.deployment.url=<URL>`. |
| `L'URL de déploiement BBj doit être une URL HTTP ou HTTPS sans identifiants, requête ou fragment` | L'URL a un autre schéma, ou contient un nom d'utilisateur, une requête ou un fragment. | Utilisez `http://<host>:<port>/webforj-bbjship` ou `https://<host>:<port>/webforj-bbjship`, et mettez les identifiants dans `<admin>`. |
| `Échec de la connexion à ... : Connection refusée` | Rien ne répond sur l'hôte et le port de l'URL de déploiement. | Démarrez BBjServices, et vérifiez l'hôte et le port de l'URL par rapport au serveur web de BBjServices. |
| `Impossible de lire la version de BBjShip à <URL>, HTTP 404...` | L'URL ne mène pas à BBjShip, ou BBjShip n'est pas installé ou son point de terminaison n'est pas activé sur ce port. | Vérifiez le chemin `/webforj-bbjship` et le port. Sur la machine BBj, exécutez `mvn webforj:bbj-setup`, ou `bbjship remote enable` sur un serveur. |
| `échec avec HTTP 401. Vérifiez les identifiants d'administration BBj ou le jeton` | BBjServices a refusé le nom d'utilisateur et le mot de passe, ou le jeton. | Vérifiez les identifiants. Renouvelez un jeton qui a expiré ou a été révoqué dans Enterprise Manager. |
| `La version de BBjShip <version> à <URL> ne correspond pas à la version de webforJ <version>...` | La BBjShip installée et le `webforj-maven-plugin` ont des versions différentes. | Sur votre propre machine, exécutez `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`. Sur un serveur, installez la BBjShip correspondante avec le BBj Plugin Manager. |
| `Impossible d'établir une connexion HTTPS de confiance à <URL>...` | Java ne fait pas confiance au certificat du serveur web de BBjServices. | Installez un certificat pour le nom d'hôte de l'URL sur la machine BBj, ou ajoutez son certificat au magasin de confiance Java de la construction. |
| `L'URL de déploiement BBj <URL> envoie les identifiants d'administration lisibles sur le réseau...` | Un avertissement pour une URL `http://` vers une autre machine. | Utilisez une URL `https://` avec le port SSL, voir [Déploiement depuis une autre machine](/docs/configuration/bbjship/deploying#deploying-from-another-machine). |

## Configuration {#setup}

Ces messages proviennent de `mvn webforj:bbj-setup` et de la première [exécution de développement](/docs/configuration/deploy-reload/bbjservices) d'un projet, qui configure BBjShip de la même manière.

| Message | Cause | Que faire |
|---|---|---|
| `Échec de la configuration BBj. Vérifiez l'artefact du plugin BBj et les paramètres de configuration` | L'objectif a échoué. La raison est indiquée ensuite. Une raison concernant la résolution d'artefact signifie que Maven n'a pas pu télécharger `webforj-bbjship-plugin` dans la version du `webforj-maven-plugin`. | Lisez la raison. Pour la résolution d'artefact, vérifiez les dépôts et l'accès réseau de la construction. |
| `La configuration BBj est uniquement locale. Exécutez la configuration sur l'hôte BBj` | L'URL de déploiement nomme une autre machine. | Exécutez la configuration sur la machine BBj, ou installez BBjShip là avec le [BBj Plugin Manager](/docs/configuration/bbjship/installing#through-the-bbj-plugin-manager). |
| `La configuration BBj ne peut pas résoudre le nom d'hôte de l'URL de déploiement <host>...` | Le nom d'hôte de l'URL de déploiement est inconnu. | Utilisez `localhost` ou un nom de cette machine. |
| `La configuration BBj nécessite le mot de passe d'administration. Configurez bbj.admin.password à la place d'un jeton` | Une exécution de développement a un jeton configuré. Les exécutions de développement se connectent uniquement avec un nom d'utilisateur et un mot de passe. | Configurez `<admin><username>` et `<admin><password>`. |
| `Impossible de détecter une installation BBj. Configurez bbj.home ou BBJ_HOME` | L'objectif n'a trouvé aucune installation BBj sur cette machine. | Définissez `<bbj><home>`, `-Dwebforj.bbj.home` ou la variable `BBJ_HOME` vers le dossier d'installation de BBj. |
| `BBj home invalide. Configurez une installation BBj locale`, `L'exécutable BBj est manquant ou non exécutable. Vérifiez bbj.home` | Le dossier configuré n'est pas une installation BBj complète. | Orientez le paramètre vers le dossier contenant `bin` et `cfg` de BBj. |
| `La version de BBjShip installée <version> ne correspond pas à <version>...` | Une autre version de BBjShip est installée, par exemple après une mise à jour de webforJ. | Exécutez `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`. Les applications déployées restent déployées. |
| `La version de BBjShip installée est manquante...` | Le dossier BBjShip installé est incomplet. | Exécutez `mvn webforj:bbj-setup -Dwebforj.bbj.force=true`. |
| `Le répertoire des plugins est un lien symbolique...`, `La destination du plugin BBj ne doit pas contenir de liens symboliques` | Le dossier `plugins` de BBj, ou le dossier BBjShip à l'intérieur, est un lien symbolique ou en contient un. | Installez dans un dossier régulier. |
| `La commande de configuration BBj a échoué. Vérifiez les identifiants et les BBjServices locaux.` suivi de la sortie de la commande | La commande qui active le point de terminaison de déploiement a échoué. La sortie nomme la raison, par exemple un login refusé ou un port sur lequel le serveur web n'écoute pas. | Vérifiez les identifiants, que BBjServices fonctionne, et que le port de l'URL de déploiement est un port du serveur web BBjServices. |
| `La commande de configuration BBj a expiré. Vérifiez les BBjServices locaux` | La commande n'a pas fini dans les 120 secondes. | Vérifiez que BBjServices fonctionne et répond, puis exécutez de nouveau l'objectif. |
| `Les fichiers BBjShip précédents sont conservés à <folder>` | Un remplacement a échoué, et les fichiers de la version précédente ont été conservés dans ce dossier. | Corrigez la raison de l'échec et exécutez de nouveau l'objectif. |

## Déployer et retirer {#deploy-and-undeploy}

### Avant le téléchargement {#before-the-upload}

Ces messages proviennent de `bbj-package`, `bbj-deploy` et `bbj-undeploy` avant que quoi que ce soit n'atteigne BBjShip.

| Message | Cause | Que faire |
|---|---|---|
| `Le déploiement BBj nécessite un packaging jar, ce projet est empaqueté en tant que <packaging>` | Le projet n'est pas empaqueté en tant que jar. | Définissez `<packaging>jar</packaging>`. |
| `Le déploiement BBj nécessite le jar de l'application empaquetée...` | L'objectif a été exécuté sans la phase `package` dans la même construction. | Exécutez `mvn install`, ou `mvn package webforj:bbj-deploy`. |
| `Le déploiement BBj ne doit pas comporter webforj-engine, BBj est le moteur...` | `webforj-engine` est sur le classpath d'exécution de l'application. | Excluez-le de la dépendance `webforj`, voir [Sans le plugin d'installation](/docs/configuration/bbjship/existing-projects#without-the-install-plugin). |
| `Le déploiement BBj nécessite webforj-foundation sur le classpath d'exécution` | L'application n'a pas de dépendance webforJ à l'exécution. | Ajoutez la dépendance `webforj` dans le scope `compile`. |
| `La dépendance de déploiement BBj est non résolue...` | Une dépendance de l'application n'a pas de fichier. | Vérifiez que Maven résout chaque dépendance du projet. |
| `Le nom de publication <name> est invalide...` | Le nom de publication enfreint les règles dans [Noms de publication](/docs/configuration/bbjship/deploying#publish-names). | Choisissez un autre `<publishName>`. |
| `Le nom de publication <name> est réservé par BBjServices pour ses propres chemins web...` | BBjServices utilise ce nom pour ses propres adresses. | Choisissez un autre `<publishName>`. |
| `L'archive de déploiement BBj n'a pas pu être écrite...` | `bbj-package` n'a pas pu écrire `target/bbj/<publish name>.zip`. La raison suit. | Lisez la raison. |

### Réponses de BBjShip {#answers-of-bbjship}

Lorsque BBjShip refuse un déploiement ou un retrait, le message commence par `Le déploiement BBj a échoué avec HTTP <status>` ou `Le retrait BBj a échoué avec HTTP <status>`, suivi de la raison.

| Statut | Raison | Cause | Que faire |
|---|---|---|---|
| 400 | `Le nom de publication <name> est invalide...` ou `...est réservé par BBjServices...` | Le nom de publication est refusé. | Choisissez un autre nom de publication. |
| 400 | `Le fichier webforj.conf de déploiement BBj est invalide. <detail>` | Le `webforj.conf` de l'application ne peut pas être lu, ou un [paramètre d'application](/docs/configuration/bbjship/deploying#app-settings) a le mauvais type. | Corrigez le fichier comme le détail le dit. |
| 400 | `Le corps téléchargé n'est pas une archive de déploiement BBj...`, `Entrée d'archive de déploiement BBj invalide : <entry>` ou une autre raison concernant l'archive | L'archive n'a pas été écrite par le plugin de construction, ou a été modifiée par la suite. | Déployez l'archive que la construction écrit, avec la même version de webforJ que BBjShip. |
| 401 | `Vérifiez les identifiants d'administration BBj ou le jeton` | La connexion a été refusée. | Vérifiez les identifiants ou renouvelez le jeton. |
| 403 | `Les exécutions de développement ne sont pas activées sur cette machine BBj. Exécutez bbjship development enable` | Une exécution de développement a atteint une machine où les exécutions de développement sont désactivées. | Exécutez `bbjship development enable` sur la machine BBj. |
| 404 | `Aucune application BBj nommée <name> n'a été déployée par BBjShip. Vérifiez le nom de publication` | Un retrait a nommé une application que BBjShip n'a pas déployée. | Vérifiez le nom de publication avec `bbjship apps list`. |
| 409 | `La classe d'entrée <class> est déjà publiée comme <name>. Retirez d'abord <name>` | La classe d'entrée de l'application fonctionne déjà sous un autre nom de publication. | Retirez l'autre application ou déployez sous son nom. |
| 409 | `L'application BBj publiée <name> n'est pas détenue par BBjShip...` | Une application enregistrée dans Enterprise Manager utilise déjà le nom. | Choisissez un autre nom de publication, ou retirez cette application dans Enterprise Manager. |
| 409 | `L'application BBj publiée <other> diffère de <name> uniquement par la casse...` | Une autre application utilise le même nom avec une autre casse. | Choisissez un autre nom de publication. |
| 409 | `<name> est une exécution de développement. Arrêtez d'abord le watch` | Une exécution de développement de l'application est active. | Arrêtez le watch avec `Ctrl+C`, puis déployez. |
| 409 | `<name> est déployé à partir d'une archive. Retirez-le d'abord` | Une exécution de développement utilise le nom d'une application déployée. | Retirez l'application, ou définissez un autre `<publishName>` pour l'exécution de développement. |
| 413 | `L'archive de déploiement BBj dépasse la taille autorisée de 1 GiB` | L'archive est plus grande que ce que BBjShip accepte. | Supprimez les dépendances dont l'application n'a pas besoin à l'exécution. |
| 500 | `Le déploiement BBj a échoué. Vérifiez le journal de BBjServices` | BBjServices a échoué lors de l'installation ou de la suppression de l'application. | Lisez les journaux de BBjServices. |

Un déploiement qui se termine par `n'a pas confirmé l'application demandée. Vérifiez la version de BBjShip et le journal de BBjServices` a atteint BBjShip mais n'a pas reçu de confirmation pour l'application. Vérifiez que BBjShip et le `webforj-maven-plugin` ont la même version, puis lisez le journal de BBjServices.

### L'application en cours d'exécution {#the-running-app}

| Message | Cause | Que faire |
|---|---|---|
| `Le protocole des icônes nécessite webforj.assetsSource = packaged lors de l'exécution avec BBj Services` | L'application utilise `icons://` sans actifs empaquetés. | Ajoutez `webforj.assetsSource = packaged` à `webforj.conf` et déployez à nouveau. |

## Exécutions de développement {#development-runs}

Ces messages proviennent de `mvn` ou `mvn compile webforj:watch` dans un projet configuré pour BBjShip. Une exécution de développement échouée commence par `L'exécution de développement BBj a échoué. Vérifiez les paramètres de déploiement et que les exécutions de développement sont activées sur la machine BBj`, suivi de la raison provenant de [Configuration](#setup), [Identifiants et connexion](#credentials-and-connection), ou [Réponses de BBjShip](#answers-of-bbjship).

| Message | Cause | Que faire |
|---|---|---|
| `Le dossier de classe <folder> n'existe pas. Exécutez compile avant webforj:watch` | Le watch a démarré avant que le projet ne soit compilé. | Exécutez `mvn compile webforj:watch`, ou `mvn` dans un projet archétype. |
| `Le dossier de classe de développement BBj doit être un répertoire absolu sur la machine BBj` | `<development><classFolder>` nomme un dossier que BBjServices ne peut pas trouver. | Définissez le chemin absolu du dossier de classe sur la machine BBj. |
| `Fichier de propriétés de BBjServices non trouvé à <file>. Configurez bbj.properties` | Le fichier de propriétés de BBjServices n'est pas à l'endroit où l'exécution le recherche. | Définissez `<bbj><properties>` ou `-Dwebforj.bbj.properties` vers le fichier. |
| `BBjServices n'a pas arrêté dans les 180 secondes` | L'exécution a demandé à BBjServices de s'arrêter pour appliquer les paramètres de hotswap, et il a continué à fonctionner. | Arrêtez BBjServices vous-même, redémarrez-le, et exécutez le watch à nouveau. |
| `Le programme de démarrage BBjServices est manquant : <program>` | L'installation BBj n'a pas de programme pour démarrer BBjServices. | Démarrez BBjServices vous-même et exécutez le watch à nouveau. |
| `BBjServices n'a pas démarré. Le programme s'est terminé avec le code de sortie <code>`, `BBjServices n'a pas démarré dans les 120 secondes` | BBjServices n'est pas revenu après le redémarrage. | Démarrez BBjServices vous-même, puis exécutez le watch à nouveau. |
| `BBjShip n'a pas répondu dans les 120 secondes` | BBjServices a démarré, mais le point de terminaison de déploiement n'a pas répondu. | Vérifiez le journal de BBjServices, puis exécutez le watch à nouveau. |
| `BBjServices a redémarré sans les paramètres de hotswap. Vérifiez <file>` | BBjServices a démarré avec des arguments Java autres que ceux écrits dans le fichier de propriétés. | Vérifiez les paramètres Java de BBjServices dans Enterprise Manager et dans ce fichier. |
| `webforJ watch : l'exécution de développement de <name> n'a pas été arrêtée. <reason>` | L'application n'a pas pu être supprimée lorsque la construction s'est arrêtée. | Retirez-la avec `bbjship apps undeploy <name>`, ou redémarrez à nouveau le watch, ce qui la remplace. |

La première exécution de développement qui ajoute les paramètres de hotswap redémarre BBjServices. Elle démarre BBjServices avec `bin/bbjservices` sur macOS et Linux. Sous Windows, elle démarre le service Windows `BBjServices` lorsqu'un service est enregistré, et exécute `bin\BBjServices.exe -noservice` sinon. Un BBjServices démarré par `bin/bbjservices` ou `-noservice` fonctionne en dehors du gestionnaire de services du système d'exploitation jusqu'à ce que la machine redémarre.

## Ligne de commande {#command-line}

Chaque commande `bbjship` s'exécute à l'intérieur de BBjServices, donc BBjServices doit être en cours d'exécution. Sans lui, la commande ne peut pas démarrer.

Les arguments invalides se terminent par le code de sortie `2` et un message qui indique ce qu'il faut spécifier :

| Message | Que faire |
|---|---|
| `Spécifiez une commande. Utilisez --help pour les commandes disponibles`, `Commande inconnue <command>...` | Nommez une commande, voir [Ligne de commande](/docs/configuration/bbjship/command-line). |
| `Option inconnue <option>...`, `L'option <option> n'est pas disponible pour <command>...` | Utilisez une option de cette commande, comme l'énumère `--help`. |
| `Spécifiez un nom d'utilisateur après --username`, `Spécifiez un mot de passe après --password`, `Spécifiez un port après --port` | Donnez une valeur à l'option. |
| `Spécifiez --port de 1 à 65535`, `Spécifiez --ssl-port de 1 à 65535` | Donnez un numéro de port. |
| `Spécifiez <option> une seule fois` | Supprimez l'option répétée. |
| `Spécifiez seulement un des deux --password et --password-stdin` | Conservez l'un des deux. |
| `Le mot de passe d'administration BBj est manquant. Spécifiez --username et --password <value> ou --password-stdin, ou définissez WEBFORJ_BBJ_USERNAME et WEBFORJ_BBJ_PASSWORD` | Donnez le nom d'utilisateur et le mot de passe ensemble. Le message nomme celui qui manque. |
| `Spécifiez un nom d'application ou --all`, `Spécifiez un seul nom d'application` | Donnez à `apps undeploy` un nom de publication, ou `--all`. |
| `Spécifiez le chemin d'une archive de déploiement se terminant par .zip`, `Spécifiez une archive de déploiement` | Donnez à `apps deploy` un fichier `.zip`. |
| `L'archive de déploiement <path> n'existe pas`, `L'archive de déploiement <path> ne peut pas être lue` | Vérifiez le chemin. BBjServices lit le fichier, donc l'utilisateur du système d'exploitation de BBjServices doit avoir accès en lecture. |
| `Les archives de développement sont déployées par le watch, pas par apps deploy` | Déployez l'archive de `bbj-package`, pas celle d'une exécution de développement. |
| `Le nom de publication <name> est invalide...`, `Le nom de publication <name> est réservé...` | Renommez l'archive. Son nom de fichier sans `.zip` est le nom de publication. |

Une commande qui échoue se termine par le code de sortie `1` et `Commande échouée.` suivie de la raison :

| Raison | Cause | Que faire |
|---|---|---|
| Un échec de connexion de BBjServices | Le nom d'utilisateur ou le mot de passe est incorrect. | Vérifiez les identifiants. |
| `Le serveur web de BBjServices ne répond pas sur le port <port>. Utilisez le port HTTP du serveur web de BBjServices` | `--port` ou `--ssl-port` n'est pas un port sur lequel le serveur web de BBjServices écoute. Le point de terminaison précédent reste. | Utilisez les ports configurés pour le serveur web dans Enterprise Manager. |
| `L'application <name> n'est pas déployée par BBjShip` | `apps undeploy` a nommé une application que BBjShip n'a pas déployée. | Vérifiez le nom avec `bbjship apps list`. |
| `Un autre déploiement est en cours` | Un autre déploiement ou retrait a occupé la machine pendant 30 secondes. | Attendez qu'il se termine, puis exécutez de nouveau la commande. |
| `Le contexte de BBjShip appartient à une autre application`, `Le contexte des actifs appartient à une autre application` | Une autre application dans Enterprise Manager utilise le contexte web `webforj-bbjship` ou `webforj-bbjship-assets`. | Renommez ou retirez ce contexte dans Enterprise Manager. |
| `Le classpath de commande BBjShip appartient à une autre application`, `Le classpath de base de BBjShip contient une bibliothèque non détenue` | Les classpaths `_webforj_bbjship_cli` ou `_webforj_bbjship` dans Enterprise Manager ont été modifiés. | Supprimez les deux classpaths dans Enterprise Manager et exécutez de nouveau la commande, ce qui les recréera. |
| `Le servlet BBjShip est manquant. Vérifiez l'installation du plugin`, `La bibliothèque BBjShip est manquante. Vérifiez l'installation du plugin` | Le dossier BBjShip est incomplet. | Installez de nouveau BBjShip. |
| `BBjServices ne gère pas les contextes web. Mettez à jour BBj` | La version de BBj est trop ancienne pour BBjShip. | Installez la version de BBj qui correspond à webforJ. |

`reset` se termine par le code de sortie `1` et `Réinitialisation annulée` lorsque la réponse à `继续? [y/N]` n'est pas `y` ou `Y`. Lorsqu'il a restauré les paramètres Java de BBjServices, il se termine par `Redémarrez BBjServices maintenant pour appliquer les paramètres Java restaurés`. Redémarrez BBjServices pour l'exécuter sans les paramètres de hotswap.

## Windows {#windows}

| Situation | Que faire |
|---|---|
| Exécuter `bbj.exe` avec le programme BBjShip n'affiche rien. | Exécutez `bbjship.cmd` dans `<BBj home>\plugins\webforj-bbjship`, qui montre la sortie de la commande. |
| `--password-stdin` se termine par le code de sortie `2` et `L'entrée standard n'est pas disponible sous Windows. Utilisez --password <value> ou WEBFORJ_BBJ_PASSWORD`. | Passez le mot de passe avec `--password`, ou définissez `WEBFORJ_BBJ_USERNAME` et `WEBFORJ_BBJ_PASSWORD`. |
| `reset` se termine par le code de sortie `2` et `L'entrée standard n'est pas disponible sous Windows. Exécutez reset --yes pour confirmer`. | Exécutez `bbjship.cmd reset --yes`. |
| `apps undeploy` ou `reset` imprime `Non retiré : <folder>. <reason>` et se termine par le code de sortie `0`. | BBjServices maintient toujours des fichiers de l'application ouverts. L'application a été retirée de Enterprise Manager. Redémarrez BBjServices. Le dossier sera supprimé lors du prochain déploiement de ce nom, ou supprimez-le après le redémarrage. |
| L'installation de BBjShip sur une version installée échoue car ses fichiers sont en cours d'utilisation. | BBjServices maintient toujours les fichiers de la version installée de BBjShip ouverts. Redémarrez BBjServices, puis installez à nouveau. |
| BBjServices ne s'exécute pas en tant que service Windows après qu'une exécution de développement l'ait redémarré. | L'exécution de développement l'a démarré avec `BBjServices.exe -noservice` car aucun service `BBjServices` n'est enregistré. Il fonctionne en dehors du gestionnaire de services Windows jusqu'à ce que la machine redémarre. |
| Une commande `bbjship.cmd` ne peut pas démarrer. | Démarrez BBjServices. Chaque commande `bbjship` a besoin qu'il soit en cours d'exécution. |
