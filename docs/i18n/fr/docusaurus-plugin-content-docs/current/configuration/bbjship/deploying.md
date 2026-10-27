---
title: Déploiement
sidebar_position: 5
description: >-
  Package, deploy and undeploy webforJ apps in BBjServices with the bbj-package,
  bbj-deploy and bbj-undeploy goals of the webforJ Maven plugin.
_i18n_hash: 19fc6feafc7d9e17580d710ced7b962b
---
# Déploiement de <DocChip chip='since' label='26.03' /> {#deploying}

Le plugin Maven webforJ déploie une application sur [BBjShip](/docs/configuration/bbjship/overview) avec trois objectifs :

| Objectif | Exécute | Ce qu'il fait |
|---|---|---|
| `bbj-package` | avec la phase `package` | Écrit l'archive de déploiement `target/bbj/<nom de publication>.zip` |
| `bbj-deploy` | avec la phase `install` | Écrit l'archive et la déploie sur BBjShip |
| `bbj-undeploy` | manuellement | Supprime l'application des BBjServices |

Les phases s'appliquent lorsque le plugin est déclaré avec `<extensions>true</extensions>` et un bloc `<bbj>`, voir [Configurer un projet](/docs/configuration/bbjship/existing-projects).

## Déployer {#deploy}

```bash
mvn install
```

Sans l'extension, exécutez `mvn package webforj:bbj-deploy`. L'objectif imprime l'adresse de l'application lorsque le déploiement est terminé :

```text
[INFO] Déploiement BBj de l'inventaire terminé à http://localhost:8888/webapp/inventory
```

Lorsqu'un déploiement échoue, la version précédente continue à fonctionner.

:::warning Remplace l'application
Un déploiement remplace l'application qui a déjà le même nom de publication. Le dossier de déploiement précédent est supprimé et les sessions en cours de cette application se terminent. Conservez l'archive de la version précédente si vous devez revenir en arrière.
:::

[Troubleshooting](/docs/configuration/bbjship/troubleshooting#deploy-and-undeploy) répertorie les messages d'un déploiement échoué et ce qu'il faut faire pour chacun.

## Packager sans déployer {#package-without-deploying}

`bbj-package` écrit la même archive sans URL de déploiement ni identifiants. Un job CI peut attacher `target/bbj/<nom de publication>.zip` à la construction, et un administrateur BBj le déploie sur la machine BBj avec [`bbjship apps deploy`](/docs/configuration/bbjship/command-line#apps).

## Désinstaller {#undeploy}

```bash
mvn webforj:bbj-undeploy
```

L'application s'arrête et est supprimée de l'Enterprise Manager, avec ses fichiers.

:::warning Impossible à annuler
`bbj-undeploy` met fin aux sessions en cours de l'application et supprime son dossier de déploiement. Cela ne peut pas être annulé. Conservez l'archive de `target/bbj/<nom de publication>.zip` si vous souhaitez redéployer l'application.
:::

## Noms de publication {#publish-names}

Le nom de publication est par défaut le nom final de la construction et apparaît dans l'adresse de l'application. Il commence par une lettre ou un chiffre, ne contient que des lettres, des chiffres, `_`, `.` et `-`, et ne se termine pas par un point. Les noms réservés, tels que les chemins BBjServices `webapp` et `files` ou le nom de périphérique Windows `con`, sont refusés avant que l'archive ne soit écrite.

Chaque classe d'entrée appartient à une application déployée. La déployer sous un second nom échoue avec :

```text
Échec du déploiement BBj avec HTTP 409. La classe d'entrée com.example.Application est déjà publiée sous le nom d’inventaire. Désinstallez d'abord l'inventaire.
```

## Déploiement depuis une autre machine {#deploying-from-another-machine}

Un déploiement depuis une autre machine, comme un serveur CI, utilise un jeton d'autorisation via HTTPS :

1. Sur la machine BBj, [activez le point de terminaison](/docs/configuration/bbjship/installing#enabling-the-deployment-endpoint) avec un port SSL.
2. Dans l'Enterprise Manager, créez un jeton sous Sécurité, Jetons d'authentification. Renouveler et révoquer également là-bas.
3. Transmettez le jeton à la construction :

```bash
mvn package webforj:bbj-deploy \
  -Dwebforj.bbj.deployment.url=https://bbj.example.com:8443/webforj-bbjship \
  -Dwebforj.bbj.admin.token="$BBJ_ADMIN_TOKEN"
```

Le certificat du serveur web BBjServices doit correspondre au nom d'hôte de l'URL, ou être dans le magasin de confiance Java de la construction. Java ne fait pas confiance au certificat par défaut de BBj, qui est auto-signé. Une URL `http://` vers une autre machine envoie les identifiants lisibles sur le réseau, et les objectifs impriment un avertissement à cet égard.

## Paramètres de l'application {#app-settings}

BBjShip lit le `webforj.conf` de l'application à chaque déploiement et applique ces propriétés à l'application dans l'Enterprise Manager. Un redéploiement prend en compte une valeur modifiée. Une propriété qui n'est pas définie conserve la valeur par défaut de BBjServices.

```hocon title="webforj.conf"
webforj.assetsSource = packaged
webforj.disallowConsole = true
webforj.programArguments = ["mode=demo", "region eu"]
```

| Propriété | Par défaut | Description |
|---|---|---|
| `webforj.assetsSource` | Non défini | `packaged` sert les fichiers statiques `ws://` et les icônes `icons://` de l'application, à `/webforj/<nom de publication>/`. Sans cela, une application qui utilise `icons://` échoue au démarrage. |
| `webforj.showClientConfirmationDialog` | `false` | Indique si le navigateur demande une confirmation avant la fermeture de la page. |
| `webforj.disallowConsole` | `true` | Indique si la console est interdite dans l'application. |
| `webforj.secure` | Par défaut de BBjServices | Indique si l'application fonctionne sur une connexion sécurisée. |
| `webforj.configFile` | Par défaut de BBjServices | Chemin du fichier de configuration BBj avec lequel l'application fonctionne, tel que lu par BBjServices sur la machine BBj. Le chemin est transmis tel quel, il peut donc nommer un fichier que seul le serveur possède. |
| `webforj.programArguments` | Aucun | Arguments du programme de l'application, sous forme de liste de chaînes ou d'une seule chaîne. |

Un `webforj.conf` invalide échoue au déploiement avec HTTP 400 et la raison.

## Paramètres {#settings}

Les paramètres se trouvent dans le bloc `<bbj>` de la configuration du `webforj-maven-plugin`, comme indiqué dans [Plugin de construction](/docs/configuration/bbjship/existing-projects#the-build-plugin). Chaque propriété remplace son élément sur la ligne de commande.

| Élément Maven | Propriété Maven | Par défaut | Description |
|---|---|---|---|
| `<home>` | `webforj.bbj.home` | Détecté | Dossier d'installation de BBj, utilisé par `bbj-setup` et `watch`. |
| `<publishName>` | `webforj.bbj.publishName` | Nom final de la construction | Nom sous lequel l'application est publiée, voir [Noms de publication](#publish-names). |
| `<deployment><url>` | `webforj.bbj.deployment.url` | Aucun, requis | Adresse de BBjShip, `http://<hôte>:<port HTTP>/webforj-bbjship` ou `https://<hôte>:<port SSL>/webforj-bbjship`. |
| `<admin><username>` | `webforj.bbj.admin.username` | `admin` lorsque le nom d'utilisateur et le mot de passe sont tous deux absents | Nom d'utilisateur administrateur BBj. |
| `<admin><password>` | `webforj.bbj.admin.password` | `admin123` lorsque le nom d'utilisateur et le mot de passe sont tous deux absents | Mot de passe administrateur BBj. |
| `<admin><token>` | `webforj.bbj.admin.token` | Vide | Jeton d'autorisation pour `bbj-deploy` et `bbj-undeploy`. Prend la priorité sur le nom d'utilisateur et le mot de passe. |
| `<properties>` | `webforj.bbj.properties` | `<home>/cfg/BBj.properties` | Fichier de propriétés BBjServices dans lequel un [exécution de développement](/docs/configuration/deploy-reload/bbjservices) écrit les paramètres de hotswap. |
| `<development><classFolder>` | — | Répertoire de sortie de construction | Dossier de classe d'une exécution de développement tel que lu par BBjServices. |
| — | `webforj.bbj.force` | `false` | Permet à `bbj-setup` de remplacer une installation de BBjShip d'une autre version. Propriété uniquement. |

Un nom d'utilisateur sans mot de passe, ou un mot de passe sans nom d'utilisateur, interrompt l'objectif.
