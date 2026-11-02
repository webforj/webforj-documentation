---
title: Command Line
sidebar_position: 7
description: >-
  The bbjship commands a BBj administrator runs on the BBj machine to manage the
  deployment endpoint, deployed apps, development runs and BBjServices.
_i18n_hash: 976400b6e439ac682858351e8f9ade17
---
# Ligne de commande <DocChip chip='since' label='26.03' /> {#command-line}

La ligne de commande `bbjship` gère BBjShip sur la machine BBj. Elle se trouve dans `<BBj home>/plugins/webforj-bbjship`, en tant que `bbjship.cmd` sur Windows. Chaque commande s'exécute à l'intérieur de BBjServices, donc BBjServices doit être en cours d'exécution pour chaque commande.

```text
bbjship <groupe> <commande> [options]
```

## Commandes {#commands}

| Commande | Description |
|---|---|
| [`remote enable [--port PORT] [--ssl-port PORT]`](#remote-enable) | Active ou met à jour le point de terminaison de déploiement et imprime ses adresses. |
| [`remote disable`](#remote-disable) | Supprime le point de terminaison de déploiement. Les applications déployées continuent de fonctionner. |
| [`remote status`](#remote-status) | Imprime les adresses du point de terminaison et si les exécutions de développement sont activées. |
| [`apps deploy ARCHIVE`](#apps-deploy) | Déploie une archive de déploiement. |
| [`apps list [--long]`](#apps-list) | Liste les applications déployées par BBjShip. |
| [`apps undeploy NAME`, `apps undeploy --all`](#apps-undeploy) | Supprime une application ou toutes les applications déployées par BBjShip. |
| [`development enable`, `development disable`](#development) | Active ou désactive les [exécutions de développement](/docs/configuration/deploy-reload/bbjservices) sur cette machine. |
| [`reset [--yes] [--properties FILE]`](#reset) | Supprime chaque application et tout ce que BBjShip a mis en place. |

## Options partagées {#options}

| Paramètre | Défaut | Description |
|---|---|---|
| `--username USER` | Non défini | Nom d’utilisateur administrateur BBj. |
| `--password PASSWORD` | Non défini | Mot de passe administrateur BBj. |
| `--password-stdin` | Désactivé | Lit le mot de passe à partir de la première ligne de l'entrée standard. Non disponible sur Windows. |
| `--help`, `-h` | | Imprime l'utilisation de la ligne de commande ou d'une commande. |
| `--version`, `-V` | | Imprime la version de BBjShip. |

Un mot de passe donné avec `--password` est visible pour les autres utilisateurs de la machine dans la liste des processus pendant que la commande s'exécute. Sur macOS et Linux, un script passe le mot de passe sur l'entrée standard à la place :

```bash
printf '%s\n' "$PASSWORD" | bbjship remote enable --username admin --password-stdin
```

Sur chaque système, les variables gardent le mot de passe hors de la ligne de commande :

```bash
WEBFORJ_BBJ_USERNAME=admin WEBFORJ_BBJ_PASSWORD="$PASSWORD" bbjship remote enable
```

Sur Windows, l'entrée standard n'est pas disponible, donc `--password-stdin` se termine avec le code de sortie `2`. Passez le mot de passe en tant que valeur ou via les variables :

```text
bbjship.cmd remote enable --username admin --password <valeur>
```

Donner à la fois `--password` et `--password-stdin`, un nom d'utilisateur sans mot de passe, ou un mot de passe sans nom d'utilisateur se termine avec le code de sortie `2` et un message indiquant ce qu'il faut spécifier.

Sans identifiants, la commande utilise le compte d’usine `admin` avec `admin123` et imprime un avertissement.

## Variables d'environnement {#environment-variables}

| Variable | Défaut | Description |
|---|---|---|
| `WEBFORJ_BBJ_USERNAME` | Non défini | Nom d'utilisateur lorsque `--username` est absent. |
| `WEBFORJ_BBJ_PASSWORD` | Non défini | Mot de passe lorsque ni `--password` ni `--password-stdin` n'est donné. |
| `NO_COLOR` | Non défini | Désactive la sortie colorée. |

## Distant {#remote}

Les commandes `remote` gèrent le point de terminaison de déploiement.

### `remote enable` {#remote-enable}

```text
bbjship remote enable [--port PORT] [--ssl-port PORT]
```

Active ou met à jour le point de terminaison de déploiement et imprime ses adresses.

| Paramètre | Défaut | Description |
|---|---|---|
| `--port PORT` | `8888` | Port HTTP du serveur web BBjServices pour le point de terminaison. |
| `--ssl-port PORT` | Aucun | Port SSL du serveur web BBjServices pour le point de terminaison. Sans cela, le point de terminaison n'a pas d'adresse HTTPS. |

Les ports doivent être des ports que le serveur web BBjServices écoute déjà. Sinon, `remote enable` se termine avec le code de sortie `1`, désigne le port qui ne répond pas, et garde le point de terminaison précédent.

### `remote disable` {#remote-disable}

```text
bbjship remote disable
```

Supprime le point de terminaison de déploiement. Les applications déployées continuent de fonctionner.

<!-- vale Google.WordListCase = NO -->
:::warning Les déploiements ne peuvent pas être effectués
`remote disable` supprime le point de terminaison de déploiement. Les constructions et les travaux CI qui y déploient échouent jusqu'à ce que vous exécutiez à nouveau `remote enable`. Notez les ports et l'adresse du point de terminaison avant de le désactiver. Les applications déployées continuent de fonctionner.
:::
<!-- vale Google.WordListCase = YES -->

### `remote status` {#remote-status}

```text
bbjship remote status
```

Imprime les adresses du point de terminaison et si les exécutions de développement sont activées.

```text
$ bbjship remote status
Activé http://localhost:8888/webforj-bbjship
Activé https://localhost:8443/webforj-bbjship
Les exécutions de développement désactivées
```

## Applications {#apps}

Les commandes `apps` listent et suppriment uniquement les applications déployées par BBjShip. Les applications enregistrées dans l'Enterprise Manager ne sont pas affectées.

### `apps deploy` {#apps-deploy}

```text
bbjship apps deploy ARCHIVE
```

Déploie une archive de déploiement. `apps deploy` installe une archive écrite par [`bbj-package`](/docs/configuration/bbjship/deploying#package-without-deploying), sans le point de terminaison de déploiement. Le nom de fichier sans `.zip` est le [nom de publication](/docs/configuration/bbjship/deploying#publish-names). BBjServices lit l'archive, donc l'utilisateur du système d'exploitation de BBjServices doit avoir accès en lecture.

```text
$ bbjship apps deploy inventory.zip
Déployé inventory /webapp/inventory
```

:::warning Remplace l'application
Déployer une archive sous le nom d'une application déjà déployée remplace cette application. Le dossier de déploiement précédent est supprimé et les sessions en cours de l'application se terminent. Conservez l'archive précédente si vous devez éventuellement revenir en arrière.
:::

### `apps list` {#apps-list}

```text
bbjship apps list [--long]
```

Liste les applications déployées par BBjShip.

| Paramètre | Défaut | Description |
|---|---|---|
| `--long`, `-l` | Désactivé | Ajoute le dossier de déploiement et les dossiers de classe de chaque application. |

```text
$ bbjship apps list
NOM       URL
inventory  /webapp/inventory
```

### `apps undeploy` {#apps-undeploy}

```text
bbjship apps undeploy NAME
bbjship apps undeploy --all
```

Supprime une ou toutes les applications déployées par BBjShip.

| Paramètre | Défaut | Description |
|---|---|---|
| `--all` | Désactivé | Désinstalle toutes les applications déployées par BBjShip. |

:::warning Ne peut pas être annulé
`apps undeploy` supprime l'application de l'Enterprise Manager, termine ses sessions en cours et supprime son dossier de déploiement. `apps undeploy --all` fait cela pour chaque application déployée par BBjShip. Cela ne peut pas être annulé. Conservez l'archive de déploiement de chaque application, par exemple `target/bbj/<publish name>.zip`, afin que vous puissiez la redéployer.
:::

## Développement {#development}

Les exécutions de développement sont désactivées lors d'une nouvelle installation, et le premier `webforj:watch` sur la même machine les active. `bbjship development disable` les désactive jusqu'à ce que le prochain `webforj:watch` sur cette machine les active à nouveau. Une exécution de développement depuis une autre machine est refusée, car la configuration qu'elle exécute d'abord fonctionne uniquement sur la machine BBj.

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

## Réinitialiser {#reset}

```text
bbjship reset [--yes] [--properties FILE]
```

`reset` désinstalle chaque application déployée par BBjShip, supprime le point de terminaison de déploiement et le commutateur de développement, restaure le fichier de propriétés de BBjServices qu'une exécution de développement a modifié, et supprime les fichiers que BBjShip a écrits. La commande n'arrête jamais BBjServices. Lorsqu'il a restauré le fichier de propriétés, redémarrez BBjServices pour l'appliquer. Le dossier du plugin reste.

| Paramètre | Défaut | Description |
|---|---|---|
| `--yes` | Désactivé | Ignore la confirmation. |
| `--properties FILE` | `<BBj home>/cfg/BBj.properties` | Fichier de propriétés de BBjServices que `reset` restaure. |

La commande imprime ce qu'elle fait et demande une confirmation sauf si `--yes` est donné. Sur Windows, `reset` sans `--yes` se termine avec le code de sortie `2` avant de se connecter.

:::warning Faites d'abord une sauvegarde
`reset` ne peut pas être annulé. Il supprime le dossier de chaque application déployée par BBjShip et termine leurs sessions en cours. Avant de l'exécuter, conservez l'archive de déploiement de chaque application, par exemple le `target/bbj/<publish name>.zip` de la construction, et sauvegardez `<BBj home>/cfg/BBj.properties`. Lorsqu'une copie du fichier a été conservée dans le dossier d’exécution de BBjShip, `reset` remet cette copie sur `cfg/BBj.properties`, de sorte que toute modification que vous avez apportée au fichier après la première exécution de développement est perdue. Redémarrez BBjServices par la suite pour appliquer les paramètres Java restaurés.
:::

## Codes de sortie {#exit-codes}

| Code | Signification |
|---|---|
| `0` | La commande a réussi. |
| `1` | La commande a échoué et a imprimé la cause, ou `reset` a été annulé. |
| `2` | Les arguments sont invalides ou la commande nécessite une entrée standard sur Windows. Le message indique ce qu'il faut spécifier. |

[Débogage](/docs/configuration/bbjship/troubleshooting#command-line) liste les messages et ce qu'il faut faire pour chacun.
