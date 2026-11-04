---
title: Installation de BBjShip
sidebar_position: 4
description: >-
  Install BBjShip on your own machine with the webforJ Maven plugin, or on a
  shared BBj server with the BBj Plugin Manager.
_i18n_hash: b5b4d6d8d79017066eecfcda86557633
---
# Installation de BBjShip <DocChip chip='since' label='26.03' />

BBjShip s’exécute dans BBjServices ; il est donc toujours installé sur la machine qui exécute BBjServices. Une fois installé et son point de terminaison de déploiement activé, vos builds y sont déployés via l’adresse `/webforj-bbjship` du serveur web BBjServices, depuis cette même machine ou depuis une autre.

La méthode d’installation de BBjShip dépend de l’endroit où s’exécute BBjServices :

| Où s’exécute BBjServices | Qui installe BBjShip | Comment |
|---|---|---|
| Votre propre machine | Le plug-in de build webforJ | Automatiquement avec `mvn`, ou une seule fois avec `mvn webforj:bbj-setup` |
| Un serveur partagé ou de production | Un administrateur de ce serveur | Le BBj Plugin Manager, puis `bbjship remote enable` |

Dans les deux cas, installez la version de BBjShip correspondant à la version de webforJ de vos applications. Les déploiements échouent si les deux versions diffèrent.

## Sur votre propre machine {#on-your-own-machine}

Lorsque BBj s’exécute sur la même machine que votre build, vous n’avez pas à installer BBjShip vous-même. La première [exécution de développement](/docs/configuration/deploy-reload/bbjservices) d’un projet configuré pour BBjShip l’installe et active son point de terminaison de déploiement :

```bash
mvn
```

### Via le plug-in Maven {#through-the-maven-plugin}

Pour déployer avec `mvn install` avant de lancer toute exécution de développement, installez BBjShip une seule fois depuis le dossier de votre projet :

```bash
mvn webforj:bbj-setup
```

L’objectif installe la version de BBjShip correspondant à `webforj-maven-plugin` et active le point de terminaison de déploiement sur le port de votre URL de déploiement. Il nécessite :

- Que BBjServices soit en cours d’exécution et réponde sur le port de l’URL de déploiement
- Une URL de déploiement indiquant votre machine, par exemple `http://localhost:8888/webforj-bbjship`
- Le nom d’utilisateur et le mot de passe de l’administrateur BBj, définis dans `<admin>` ou par les propriétés `webforj.bbj.admin.*`. En leur absence, l’objectif utilise le compte d’usine `admin` avec le mot de passe `admin123` et affiche un avertissement.
- Le dossier d’installation de BBj, défini par `<home>`, `webforj.bbj.home` ou `BBJ_HOME`. En leur absence, l’objectif le détecte.

Après une mise à niveau de webforJ, remplacez la version installée de BBjShip par la nouvelle version :

```bash
mvn webforj:bbj-setup -Dwebforj.bbj.force=true
```

Vos applications déployées le restent lorsque vous relancez l’objectif.

:::tip Fichiers utilisés sous Windows
Sous Windows, le remplacement peut échouer si BBjServices garde ouverts les fichiers de BBjShip installé. Redémarrez BBjServices et relancez l’objectif. Consultez la section [Dépannage](/docs/configuration/bbjship/troubleshooting#windows).
:::

## Sur un serveur {#on-a-server}

Sur un serveur BBj partagé ou de production, un administrateur installe BBjShip une seule fois sur ce serveur. Les développeurs et les pipelines CI peuvent ensuite y déployer leurs applications depuis leurs propres machines via HTTPS.

1. Installez BBjShip avec le [BBj Plugin Manager](#through-the-bbj-plugin-manager).
2. [Activez le point de terminaison de déploiement](#enabling-the-deployment-endpoint) avec le port HTTPS du serveur web BBjServices.
3. Créez un jeton d’autorisation dans Enterprise Manager pour les builds qui effectuent les déploiements. Consultez [Déployer depuis une autre machine](/docs/configuration/bbjship/deploying#deploying-from-another-machine).

### Via le BBj Plugin Manager {#through-the-bbj-plugin-manager}

Dans le [BBj Plugin Manager](https://documentation.basis.cloud/BASISHelp/WebHelp/bbutil/BBj_Plugin_Manager.htm), installez BBjShip depuis le dépôt `webforj-bbjship` de l’organisation `BBj-Plugins`. Un tag installe une version de webforJ, tandis que `master` installe l’instantané de développement. Vous pouvez le mettre à jour ultérieurement dans l’onglet Installed.

### Activation du point de terminaison de déploiement {#enabling-the-deployment-endpoint}

Le point de terminaison de déploiement est l’adresse vers laquelle les builds effectuent les déploiements. Activez-le sur le serveur à l’aide de votre compte administrateur BBj et des ports sur lesquels le serveur web BBjServices est à l’écoute. `--password` prend le mot de passe comme valeur, que les autres utilisateurs de la machine peuvent voir dans la liste des processus pendant l’exécution de la commande. Sous macOS et Linux, `--password-stdin` lit le mot de passe depuis l’entrée standard, et `WEBFORJ_BBJ_PASSWORD` permet de ne pas l’exposer sur la ligne de commande, quel que soit le système :

```text
$ <BBj home>/plugins/webforj-bbjship/bbjship remote enable --username admin --password <value> --port 8888 --ssl-port 8443
Enabled http://localhost:8888/webforj-bbjship
Enabled https://localhost:8443/webforj-bbjship
```

Sans `--port`, le point de terminaison utilise le port 8888. Sans `--ssl-port`, il ne possède pas d’adresse HTTPS. Les builds exécutés sur d’autres machines doivent utiliser l’adresse HTTPS ; le point de terminaison répond par une erreur HTTP 403 à leurs requêtes HTTP, sauf si `--allow-unencrypted` est spécifié. BBjServices doit être en cours d’exécution. Consultez la page [Ligne de commande](/docs/configuration/bbjship/command-line) pour connaître toutes les commandes.

:::tip Mot de passe sous Windows
Sous Windows, exécutez `bbjship.cmd` et transmettez le mot de passe avec `--password` ou `WEBFORJ_BBJ_PASSWORD`. `--password-stdin` n’est pas disponible et renvoie le code de sortie `2`.
:::

## Désinstallation {#uninstalling}

Exécutez d’abord `bbjship reset`. Cette commande annule le déploiement de toutes les applications et supprime tout ce que BBjShip a configuré dans Enterprise Manager. Désinstallez ensuite BBjShip dans le BBj Plugin Manager.

:::warning Effectuez d’abord une sauvegarde
L’exécution de `bbjship reset` est irréversible. Sauvegardez `<BBj home>/cfg/BBj.properties` et conservez l’archive de déploiement de chaque application au préalable. Consultez la section [Réinitialisation](/docs/configuration/bbjship/command-line#reset).
:::
