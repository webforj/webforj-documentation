---
title: Déploiement
sidebar_position: 5
description: >-
  Package, deploy and undeploy webforJ apps in BBjServices with the bbj-package,
  bbj-deploy and bbj-undeploy goals of the webforJ Maven plugin.
_i18n_hash: 77099210b317740da114b7881490bd09
---
# Déploiement <DocChip chip='since' label='26.03' /> {#deploying}

Le plugin Maven webforJ déploie une application sur [BBjShip](/docs/configuration/bbjship/overview) à l’aide de trois objectifs :

| Objectif | S’exécute | Fonction |
|---|---|---|
| `bbj-package` | avec la phase `package` | Écrit l’archive de déploiement `target/bbj/<publish name>.zip` |
| `bbj-deploy` | avec la phase `install` | Écrit l’archive et la déploie sur BBjShip |
| `bbj-undeploy` | manuellement | Supprime l’application de BBjServices |

Ces phases s’appliquent lorsque le plugin est déclaré avec `<extensions>true</extensions>` et un bloc `<bbj>`. Voir [Configuration d’un projet](/docs/configuration/bbjship/existing-projects).

## Déployer {#deploy}

```bash
mvn install
```

Sans l’extension, exécutez `mvn package webforj:bbj-deploy`. À la fin du déploiement, l’objectif affiche l’adresse de l’application :

```text
[INFO] BBj deployment of inventory completed at http://localhost:8888/webapp/inventory
```

En cas d’échec du déploiement, la version précédente continue de fonctionner.

:::warning Remplace l’application
Un déploiement remplace l’application portant déjà le même nom de publication. Le dossier du déploiement précédent est supprimé et les sessions actives de cette application prennent fin. Conservez l’archive de la version précédente si vous risquez de devoir revenir en arrière.
:::

La section [Résolution des problèmes](/docs/configuration/bbjship/troubleshooting#deploy-and-undeploy) répertorie les messages associés à un déploiement échoué et indique la marche à suivre pour chacun.

## Empaqueter sans déployer {#package-without-deploying}

`bbj-package` écrit la même archive, sans URL de déploiement ni identifiants. Une tâche CI peut joindre `target/bbj/<publish name>.zip` à la compilation, puis un administrateur BBj la déploie sur la machine BBj à l’aide de [`bbjship apps deploy`](/docs/configuration/bbjship/command-line#apps).

## Annuler le déploiement {#undeploy}

```bash
mvn webforj:bbj-undeploy
```

L’application s’arrête et est supprimée d’Enterprise Manager, ainsi que ses fichiers.

:::warning Opération irréversible
`bbj-undeploy` met fin aux sessions actives de l’application et supprime son dossier de déploiement. Cette opération est irréversible. Conservez l’archive `target/bbj/<publish name>.zip` si vous risquez de vouloir redéployer l’application.
:::

## Noms de publication {#publish-names}

Par défaut, le nom de publication correspond au nom final de la compilation et figure dans l’adresse de l’application. Il commence par une lettre ou un chiffre, contient uniquement des lettres, des chiffres, `_`, `.` et `-`, et ne se termine pas par un point. Les noms réservés, tels que les chemins BBjServices `webapp` et `files` ou le nom de périphérique Windows `con`, sont refusés avant l’écriture de l’archive.

Chaque classe d’entrée appartient à une seule application déployée. Le déploiement de cette classe sous un deuxième nom échoue avec le message suivant :

```text
BBj deployment failed with HTTP 409. Entry class com.example.Application is already published as inventory. Undeploy inventory first
```

## Déployer depuis une autre machine {#deploying-from-another-machine}

Un déploiement depuis une autre machine, par exemple un serveur CI, utilise un jeton d’autorisation sur HTTPS :

1. Sur la machine BBj, [activez le point de terminaison](/docs/configuration/bbjship/installing#enabling-the-deployment-endpoint) avec un port SSL.
2. Dans Enterprise Manager, créez un jeton sous Security, Authentication Tokens. Vous pouvez également le renouveler et le révoquer à cet endroit.
3. Transmettez le jeton à la compilation :

```bash
mvn package webforj:bbj-deploy \
  -Dwebforj.bbj.deployment.url=https://bbj.example.com:8443/webforj-bbjship \
  -Dwebforj.bbj.admin.token="$BBJ_ADMIN_TOKEN"
```

Le certificat du serveur web BBjServices doit correspondre au nom d’hôte de l’URL ou se trouver dans le truststore Java de la compilation. Java ne fait pas confiance au certificat par défaut de BBj, car il est auto-signé.

Une URL `http://` vers une autre machine est refusée des deux côtés. L’objectif s’arrête avant d’envoyer quoi que ce soit, et BBjShip répond HTTP 403 à toute requête reçue via HTTP depuis une autre machine. Une URL désignant la machine BBj elle-même — `localhost` ou l’une de ses propres adresses — fonctionne avec HTTP.

Pour déployer malgré tout via HTTP vers une autre machine, par exemple une machine virtuelle sur votre ordinateur, autorisez cette méthode des deux côtés. Sur la machine BBj, exécutez `bbjship remote enable --allow-unencrypted` avec les ports du point de terminaison. Dans la compilation, définissez `<deployment><allowUnencrypted>true</allowUnencrypted>` ou transmettez la propriété :

```bash
mvn package webforj:bbj-deploy \
  -Dwebforj.bbj.deployment.url=http://bbj.example.com:8888/webforj-bbjship \
  -Dwebforj.bbj.deployment.allowUnencrypted=true
```

L’objectif affiche alors un avertissement et procède au déploiement. Les identifiants sont transmis en clair sur le réseau pour cette connexion.

## Paramètres de l’application {#app-settings}

À chaque déploiement, BBjShip lit le fichier `webforj.conf` de l’application et applique ces propriétés à l’application dans Enterprise Manager. Un redéploiement prend en compte toute valeur modifiée. Une propriété non définie conserve la valeur par défaut de BBjServices.

```hocon title="webforj.conf"
webforj.assetsSource = packaged
webforj.disallowConsole = true
webforj.programArguments = ["mode=demo", "region eu"]
```

| Propriété | Valeur par défaut | Description |
|---|---|---|
| `webforj.assetsSource` | Non définie | `packaged` sert les fichiers statiques `ws://` et les icônes `icons://` de l’application à l’adresse `/webforj/<publish name>/`. Sans cette propriété, une application utilisant `icons://` ne démarre pas. |
| `webforj.showClientConfirmationDialog` | `false` | Indique si le navigateur demande une confirmation avant la fermeture de la page. |
| `webforj.disallowConsole` | `true` | Indique si la console est désactivée dans l’application. |
| `webforj.secure` | Valeur par défaut de BBjServices | Indique si l’application s’exécute sur une connexion sécurisée. |
| `webforj.configFile` | Valeur par défaut de BBjServices | Chemin du fichier de configuration BBj utilisé par l’application, tel que BBjServices le lit sur la machine BBj. Le chemin est transmis tel quel ; il peut donc désigner un fichier accessible uniquement au serveur. |
| `webforj.programArguments` | Aucun | Arguments du programme de l’application, sous forme de liste de chaînes ou d’une seule chaîne. |

Un fichier `webforj.conf` non valide fait échouer le déploiement avec une réponse HTTP 400 et la raison de l’échec.

## Paramètres {#settings}

Les paramètres se trouvent dans le bloc `<bbj>` de la configuration de `webforj-maven-plugin`, comme indiqué dans [Plugin de compilation](/docs/configuration/bbjship/existing-projects#the-build-plugin). Chaque propriété remplace l’élément correspondant sur la ligne de commande.

| Élément Maven | Propriété Maven | Valeur par défaut | Description |
|---|---|---|---|
| `<home>` | `webforj.bbj.home` | Détecté automatiquement | Dossier d’installation de BBj, utilisé par `bbj-setup` et `watch`. |
| `<publishName>` | `webforj.bbj.publishName` | Nom final de la compilation | Nom sous lequel l’application est publiée ; voir [Noms de publication](#publish-names). |
| `<deployment><url>` | `webforj.bbj.deployment.url` | Aucune, obligatoire | Adresse de BBjShip : `http://<host>:<HTTP port>/webforj-bbjship` ou `https://<host>:<SSL port>/webforj-bbjship`. |
| `<deployment><allowUnencrypted>` | `webforj.bbj.deployment.allowUnencrypted` | `false` | Autorise `bbj-deploy` et `bbj-undeploy` à transmettre les identifiants via une URL `http://` vers une autre machine ; voir [Déployer depuis une autre machine](#deploying-from-another-machine). |
| `<admin><username>` | `webforj.bbj.admin.username` | `admin` si le nom d’utilisateur et le mot de passe sont tous deux absents | Nom d’utilisateur de l’administrateur BBj. |
| `<admin><password>` | `webforj.bbj.admin.password` | `admin123` si le nom d’utilisateur et le mot de passe sont tous deux absents | Mot de passe de l’administrateur BBj. |
| `<admin><token>` | `webforj.bbj.admin.token` | Vide | Jeton d’autorisation pour `bbj-deploy` et `bbj-undeploy`. Prioritaire sur le nom d’utilisateur et le mot de passe. |
| `<properties>` | `webforj.bbj.properties` | `<home>/cfg/BBj.properties` | Fichier de propriétés BBjServices dans lequel une [exécution de développement](/docs/configuration/deploy-reload/bbjservices) écrit les paramètres de remplacement à chaud. |
| `<development><classFolder>` | — | Répertoire de sortie de la compilation | Dossier de classes d’une exécution de développement, tel que BBjServices le lit. |
| — | `webforj.bbj.force` | `false` | Permet à `bbj-setup` de remplacer une installation de BBjShip d’une autre version. Propriété uniquement. |

Si le nom d’utilisateur est fourni sans mot de passe, ou le mot de passe sans nom d’utilisateur, l’objectif s’arrête.
