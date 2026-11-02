---
title: Installation de BBjShip
sidebar_position: 4
description: >-
  Install BBjShip on your own machine with the webforJ Maven plugin, or on a
  shared BBj server with the BBj Plugin Manager.
_i18n_hash: 1f408734cddab0f6acd5a9446a2d51e9
---
# Installation de BBjShip <DocChip chip='since' label='26.03' />

BBjShip fonctionne à l'intérieur de BBjServices, il est donc toujours installé sur la machine qui exécute BBjServices. Une fois qu'il est installé et que son point de déploiement est activé, vos constructions se déploient à travers l'adresse `/webforj-bbjship` sur le serveur web de BBjServices, que ce soit depuis cette même machine ou depuis une autre.

La façon dont vous installez BBjShip dépend de l'endroit où BBjServices fonctionne :

| Où BBjServices fonctionne | Qui installe BBjShip | Comment |
|---|---|---|
| Votre propre machine | Le plugin de construction webforJ | Automatiquement avec `mvn`, ou une fois avec `mvn webforj:bbj-setup` |
| Un serveur partagé ou de production | Un administrateur de ce serveur | Le BBj Plugin Manager, puis `bbjship remote enable` |

Dans les deux cas, installez la version de BBjShip qui correspond à la version de webforJ de vos applications. Les déploiements échouent lorsque les deux diffèrent.

## Sur votre propre machine {#on-your-own-machine}

Lorsque BBj fonctionne sur la même machine que votre construction, vous n'installez pas BBjShip vous-même. La première [exécution de développement](/docs/configuration/deploy-reload/bbjservices) d'un projet configuré pour BBjShip l'installe et active son point de déploiement :

```bash
mvn
```

### Via le plugin Maven {#through-the-maven-plugin}

Pour déployer avec `mvn install` avant de commencer toute exécution de développement, installez BBjShip une fois depuis le dossier de votre projet :

```bash
mvn webforj:bbj-setup
```

L'objectif installe la version de BBjShip qui correspond au `webforj-maven-plugin` et active le point de déploiement sur le port de votre URL de déploiement. Il a besoin de :

- BBjServices en cours d'exécution et répondant sur le port de l'URL de déploiement
- Une URL de déploiement qui nomme votre machine, par exemple `http://localhost:8888/webforj-bbjship`
- Le nom d'utilisateur et le mot de passe de l'administrateur BBj, provenant de `<admin>` ou des propriétés `webforj.bbj.admin.*`. Sans eux, l'objectif utilise le compte usine `admin` avec `admin123` et imprime un avertissement.
- Le dossier d'installation de BBj, provenant de `<home>`, `webforj.bbj.home` ou `BBJ_HOME`. Sans eux, l'objectif le détecte.

Après une mise à niveau webforJ, remplacez le BBjShip installé par la nouvelle version :

```bash
mvn webforj:bbj-setup -Dwebforj.bbj.force=true
```

Vos applications déployées restent déployées lorsque vous exécutez à nouveau l'objectif.

:::tip Fichiers en cours d'utilisation sur Windows
Sur Windows, un remplacement peut échouer pendant que BBjServices maintient les fichiers de BBjShip installé ouverts. Redémarrez BBjServices et exécutez à nouveau l'objectif, voir [Dépannage](/docs/configuration/bbjship/troubleshooting#windows).
:::

## Sur un serveur {#on-a-server}

Sur un serveur BBj partagé ou de production, un administrateur installe BBjShip une fois sur ce serveur. Les développeurs et les pipelines CI peuvent ensuite y déployer depuis leurs propres machines via HTTPS.

1. Installez BBjShip avec le [BBj Plugin Manager](#through-the-bbj-plugin-manager).
2. [Activez le point de déploiement](#enabling-the-deployment-endpoint) avec le port HTTPS du serveur web BBjServices.
3. Créez un jeton d'autorisation dans l'Enterprise Manager pour les constructions qui se déploient, voir [Déploiement depuis une autre machine](/docs/configuration/bbjship/deploying#deploying-from-another-machine).

### Via le BBj Plugin Manager {#through-the-bbj-plugin-manager}

Dans le [BBj Plugin Manager](https://documentation.basis.cloud/BASISHelp/WebHelp/bbutil/BBj_Plugin_Manager.htm), installez BBjShip depuis le dépôt `webforj-bbjship` de l'organisation `BBj-Plugins`. Un tag installe une version webforJ, et `master` installe le snapshot de développement. Mettez-le à jour plus tard sur l'onglet Installé.

### Activation du point de déploiement {#enabling-the-deployment-endpoint}

Le point de déploiement est l'adresse à laquelle les constructions se déploient. Activez-le sur le serveur avec votre compte administrateur BBj et les ports sur lesquels le serveur web BBjServices écoute. `--password` prend le mot de passe comme valeur, que d'autres utilisateurs de la machine voient dans la liste des processus pendant que la commande s'exécute. Sur macOS et Linux, `--password-stdin` le lit depuis l'entrée standard, et `WEBFORJ_BBJ_PASSWORD` le maintient hors de la ligne de commande sur tous les systèmes :

```text
$ <BBj home>/plugins/webforj-bbjship/bbjship remote enable --username admin --password <value> --port 8888 --ssl-port 8443
Activé http://localhost:8888/webforj-bbjship
Activé https://localhost:8443/webforj-bbjship
```

Sans `--port`, le point utilise le port 8888. Sans `--ssl-port`, il n'a pas d'adresse HTTPS. BBjServices doit être en cours d'exécution. Voir [Ligne de commande](/docs/configuration/bbjship/command-line) pour chaque commande.

:::tip Mot de passe sur Windows
Sur Windows, exécutez `bbjship.cmd` et passez le mot de passe avec `--password` ou `WEBFORJ_BBJ_PASSWORD`. `--password-stdin` n'est pas disponible et se termine par le code de sortie `2`.
:::

## Désinstallation {#uninstalling}

Exécutez d'abord `bbjship reset`. Cela désinstalle chaque application et supprime tout ce que BBjShip a configuré dans l'Enterprise Manager. Ensuite, désinstallez BBjShip dans le BBj Plugin Manager.

:::warning Sauvegardez d'abord
`bbjship reset` ne peut pas être annulé. Sauvegardez `<BBj home>/cfg/BBj.properties` et conservez l'archive de déploiement de chaque application au préalable, voir [Réinitialiser](/docs/configuration/bbjship/command-line#reset).
:::
