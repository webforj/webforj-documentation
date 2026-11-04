---
title: Ligne de commande
sidebar_position: 7
description: >-
  The bbjship commands a BBj administrator runs on the BBj machine to manage the
  deployment endpoint, deployed apps, development runs and BBjServices.
_i18n_hash: a80e91a6626a5c018604f40e37a5ff00
---
# Ligne de commande <DocChip chip='since' label='26.03' /> {#command-line}

La ligne de commande `bbjship` gère BBjShip sur la machine BBj. Elle se trouve dans `<BBj home>/plugins/webforj-bbjship`, sous le nom `bbjship.cmd` sous Windows. Chaque commande s’exécute dans BBjServices ; BBjServices doit donc être en cours d’exécution pour chaque commande.

```text
bbjship <group> <command> [options]
```

## Commandes {#commands}

| Commande | Description |
|---|---|
| [`remote enable [--port PORT] [--ssl-port PORT] [--allow-unencrypted]`](#remote-enable) | Active ou met à jour le point de terminaison de déploiement et affiche ses adresses. |
| [`remote disable`](#remote-disable) | Supprime le point de terminaison de déploiement. Les applications déployées continuent de fonctionner. |
| [`remote status`](#remote-status) | Affiche les adresses du point de terminaison, indique si les exécutions de développement sont activées et si les requêtes non chiffrées provenant d’une autre machine sont autorisées. |
| [`apps deploy ARCHIVE`](#apps-deploy) | Déploie une archive de déploiement. |
| [`apps list [--long]`](#apps-list) | Répertorie les applications déployées par BBjShip. |
| [`apps undeploy NAME`, `apps undeploy --all`](#apps-undeploy) | Supprime une ou toutes les applications déployées par BBjShip. |
| [`development enable`, `development disable`](#development) | Active ou désactive les [exécutions de développement](/docs/configuration/deploy-reload/bbjservices) sur cette machine. |
| [`reset [--yes] [--properties FILE]`](#reset) | Supprime toutes les applications et tout ce que BBjShip a configuré. |

## Options communes {#options}

| Paramètre | Valeur par défaut | Description |
|---|---|---|
| `--username USER` | Non défini | Nom d’utilisateur administrateur BBj. |
| `--password PASSWORD` | Non défini | Mot de passe administrateur BBj. |
| `--password-stdin` | Désactivé | Lit le mot de passe sur la première ligne de l’entrée standard. Non disponible sous Windows. |
| `--help`, `-h` | | Affiche l’aide de la ligne de commande ou d’une commande. |
| `--version`, `-V` | | Affiche la version de BBjShip. |

Un mot de passe fourni avec `--password` est visible par les autres utilisateurs de la machine dans la liste des processus pendant l’exécution de la commande. Sous macOS et Linux, un script peut plutôt transmettre le mot de passe sur l’entrée standard :

```bash
printf '%s\n' "$PASSWORD" | bbjship remote enable --username admin --password-stdin
```

Sur tous les systèmes, les variables permettent de ne pas exposer le mot de passe dans la ligne de commande :

```bash
WEBFORJ_BBJ_USERNAME=admin WEBFORJ_BBJ_PASSWORD="$PASSWORD" bbjship remote enable
```

Sous Windows, l’entrée standard n’est pas disponible ; `--password-stdin` se termine donc avec le code de sortie `2`. Fournissez le mot de passe comme valeur ou par l’intermédiaire des variables :

```text
bbjship.cmd remote enable --username admin --password <value>
```

Fournir à la fois `--password` et `--password-stdin`, un nom d’utilisateur sans mot de passe ou un mot de passe sans nom d’utilisateur entraîne le code de sortie `2` et un message indiquant les éléments à spécifier.

En l’absence d’identifiants, la commande utilise le compte d’usine `admin` avec le mot de passe `admin123` et affiche un avertissement.

## Variables d’environnement {#environment-variables}

| Variable | Valeur par défaut | Description |
|---|---|---|
| `WEBFORJ_BBJ_USERNAME` | Non défini | Nom d’utilisateur utilisé si `--username` est absent. |
| `WEBFORJ_BBJ_PASSWORD` | Non défini | Mot de passe utilisé si ni `--password` ni `--password-stdin` ne sont fournis. |
| `NO_COLOR` | Non défini | Désactive la sortie en couleur. |

## Distant {#remote}

Les commandes `remote` gèrent le point de terminaison de déploiement.

### `remote enable` {#remote-enable}

```text
bbjship remote enable [--port PORT] [--ssl-port PORT] [--allow-unencrypted]
```

Active ou met à jour le point de terminaison de déploiement et affiche ses adresses.

| Paramètre | Valeur par défaut | Description |
|---|---|---|
| `--port PORT` | `8888` | Port HTTP du serveur Web BBjServices pour le point de terminaison. |
| `--ssl-port PORT` | Aucun | Port SSL du serveur Web BBjServices pour le point de terminaison. Sans cette option, le point de terminaison n’a pas d’adresse HTTPS. |
| `--allow-unencrypted` | Désactivé | Autorise l’envoi des identifiants d’administration par HTTP depuis une autre machine. Sans cette option, une requête provenant d’une autre machine doit utiliser le port SSL, sinon le point de terminaison lui répond avec le code HTTP 403. Les requêtes provenant de la machine BBj elle-même utilisent HTTP dans tous les cas. |

Les ports doivent correspondre à des ports sur lesquels le serveur Web BBjServices est déjà à l’écoute. Dans le cas contraire, `remote enable` se termine avec le code de sortie `1`, indique le port qui ne répond pas et conserve le point de terminaison précédent.

Chaque exécution de `remote enable` enregistre à nouveau les ports et le paramètre `--allow-unencrypted`. Une exécution sans cette option désactive le paramètre et affiche `Refused unencrypted requests from another machine`. Une exécution avec cette option affiche `Allowed unencrypted requests from another machine`. Le build qui déploie via HTTP depuis une autre machine définit également [`allowUnencrypted`](/docs/configuration/bbjship/deploying#deploying-from-another-machine).

### `remote disable` {#remote-disable}

```text
bbjship remote disable
```

Supprime le point de terminaison de déploiement. Les applications déployées continuent de fonctionner.

<!-- vale Google.WordListCase = NO -->
:::warning Les builds ne peuvent pas déployer
`remote disable` supprime le point de terminaison de déploiement. Les builds et les tâches CI qui y déploient échouent jusqu’à ce que vous exécutiez de nouveau `remote enable`. Notez les ports et l’adresse du point de terminaison avant de le désactiver. Les applications déployées continuent de fonctionner.
:::
<!-- vale Google.WordListCase = YES -->

### `remote status` {#remote-status}

```text
bbjship remote status
```

Affiche les adresses du point de terminaison, indique si les exécutions de développement sont activées et si les requêtes non chiffrées provenant d’une autre machine sont autorisées.

```text
$ bbjship remote status
Enabled http://localhost:8888/webforj-bbjship
Enabled https://localhost:8443/webforj-bbjship
Development runs  disabled
Unencrypted requests  refused
```

## Applications {#apps}

Les commandes `apps` répertorient et suppriment uniquement les applications déployées par BBjShip. Elles n’affectent pas les applications enregistrées dans Enterprise Manager.

### `apps deploy` {#apps-deploy}

```text
bbjship apps deploy ARCHIVE
```

Déploie une archive de déploiement. `apps deploy` installe une archive créée par [`bbj-package`](/docs/configuration/bbjship/deploying#package-without-deploying), sans utiliser le point de terminaison de déploiement. Le nom du fichier sans `.zip` est le [nom de publication](/docs/configuration/bbjship/deploying#publish-names). BBjServices lit l’archive ; l’utilisateur du système d’exploitation qui exécute BBjServices doit donc disposer d’un accès en lecture à cette archive.

```text
$ bbjship apps deploy inventory.zip
Deployed inventory /webapp/inventory
```

:::warning Remplace l’application
Le déploiement d’une archive portant le nom d’une application déjà déployée remplace cette application. Le dossier du déploiement précédent est supprimé et les sessions en cours de l’application prennent fin. Conservez l’archive précédente si vous pensez devoir revenir à cette version.
:::

### `apps list` {#apps-list}

```text
bbjship apps list [--long]
```

Répertorie les applications déployées par BBjShip.

| Paramètre | Valeur par défaut | Description |
|---|---|---|
| `--long`, `-l` | Désactivé | Ajoute le dossier de déploiement et les dossiers de classes de chaque application. |

```text
$ bbjship apps list
NAME       URL
inventory  /webapp/inventory
```

### `apps undeploy` {#apps-undeploy}

```text
bbjship apps undeploy NAME
bbjship apps undeploy --all
```

Supprime une ou toutes les applications déployées par BBjShip.

| Paramètre | Valeur par défaut | Description |
|---|---|---|
| `--all` | Désactivé | Annule le déploiement de toutes les applications déployées par BBjShip. |

:::warning Action irréversible
`apps undeploy` supprime l’application d’Enterprise Manager, met fin à ses sessions en cours et supprime son dossier de déploiement. `apps undeploy --all` effectue cette opération pour toutes les applications déployées par BBjShip. Cette action est irréversible. Conservez l’archive de déploiement de chaque application, par exemple `target/bbj/<publish name>.zip`, afin de pouvoir la déployer à nouveau.
:::

## Développement {#development}

Les exécutions de développement sont désactivées sur une nouvelle installation ; le premier `webforj:watch` exécuté sur la même machine les active. `bbjship development disable` les désactive jusqu’à ce que le prochain `webforj:watch` exécuté sur cette machine les réactive. Une exécution de développement lancée depuis une autre machine est refusée, car la configuration qu’elle exécute en premier ne fonctionne que sur la machine BBj.

### `development enable` {#development-enable}

```text
bbjship development enable
```

Active les [exécutions de développement](/docs/configuration/deploy-reload/bbjservices) sur cette machine.

### `development disable` {#development-disable}

```text
bbjship development disable
```

Désactive les [exécutions de développement](/docs/configuration/deploy-reload/bbjservices) sur cette machine.

## Réinitialisation {#reset}

```text
bbjship reset [--yes] [--properties FILE]
```

`reset` annule le déploiement de toutes les applications déployées par BBjShip, supprime le point de terminaison de déploiement, le paramètre de développement et le paramètre `--allow-unencrypted`, restaure le fichier de propriétés BBjServices modifié par une exécution de développement et supprime les fichiers écrits par BBjShip. La commande n’arrête jamais BBjServices. Après la restauration du fichier de propriétés, redémarrez BBjServices pour appliquer les modifications. Le dossier du plug-in est conservé.

| Paramètre | Valeur par défaut | Description |
|---|---|---|
| `--yes` | Désactivé | Ignore la confirmation. |
| `--properties FILE` | `<BBj home>/cfg/BBj.properties` | Fichier de propriétés BBjServices que `reset` restaure. |

La commande affiche les opérations qu’elle effectue et demande une confirmation, sauf si `--yes` est fourni. Sous Windows, `reset` sans `--yes` se termine avec le code de sortie `2` avant de se connecter.

:::warning Effectuez d’abord une sauvegarde
`reset` est irréversible. Cette commande supprime le dossier de chaque application déployée par BBjShip et met fin aux sessions en cours. Avant de l’exécuter, conservez l’archive de déploiement de chaque application, par exemple le fichier `target/bbj/<publish name>.zip` du build, et sauvegardez `<BBj home>/cfg/BBj.properties`. Si une copie du fichier a été conservée dans le dossier d’exécution de BBjShip, `reset` la replace dans `cfg/BBj.properties` ; toute modification apportée au fichier après la première exécution de développement est alors perdue. Redémarrez ensuite BBjServices pour appliquer les paramètres Java restaurés.
:::

## Codes de sortie {#exit-codes}

| Code | Signification |
|---|---|
| `0` | La commande a réussi. |
| `1` | La commande a échoué et a affiché la cause, ou `reset` a été annulé. |
| `2` | Les arguments ne sont pas valides ou la commande nécessite l’entrée standard sous Windows. Le message indique les éléments à spécifier. |

La page [Dépannage](/docs/configuration/bbjship/troubleshooting#command-line) répertorie les messages et les mesures à prendre pour chacun.
