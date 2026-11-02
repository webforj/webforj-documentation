---
title: BBjShip
sidebar_position: 1
hide_table_of_contents: true
description: >-
  Deploy webforJ apps to BBjServices with BBjShip and the webforJ Maven plugin,
  and run them on your local BBj during development.
_i18n_hash: c2a89de1c4f3ad8b0f4bc5d364b27a67
---
# BBjShip <DocChip chip='since' label='26.03' />

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

BBjShip est un plugin BBj qui déploie des applications webforJ vers BBjServices. Vous construisez et déployez votre application avec le [plugin de construction webforJ](/docs/configuration/build-plugin), soit sur la machine BBj, soit à partir d'une autre machine telle qu'un serveur CI, et l'application s'exécute dans BBjServices comme n'importe quelle autre application que vous gérez dans Enterprise Manager.

BBjShip remplace le plugin d'installation webforJ décrit dans [Exécution avec BBjServices](/docs/configuration/bbj-installation/overview), qui est obsolète et sera supprimé dans une future version.

:::info Maven uniquement
BBjShip fonctionne avec des projets Maven via le `webforj-maven-plugin`. Le plugin Gradle webforJ ne prend pas en charge BBjShip.
:::

## Comment ça fonctionne {#how-it-works}

Un déploiement se compose de trois parties :

1. **plugin de construction webforJ** : emballe votre application avec ses dépendances et la télécharge vers BBjShip
2. **BBjShip** : installe l'application dans BBjServices et la publie sous `/webapp/<nom de publication>`
3. **BBjServices** : exécute l'application, que vous gérez dans Enterprise Manager comme n'importe quelle autre application

Pendant le développement, le même plugin de construction exécute votre application depuis votre projet dans les BBjServices de votre propre machine. Les modifications que vous compilez atteignent l'application en cours d'exécution sans nécessiter de déploiement, et les pages ouvertes se mettent à jour, voir [BBjServices](/docs/configuration/deploy-reload/bbjservices) dans Redéploiement et Rechargement en Direct.

## Sujets {#topics}

<DocCardList className="topics-section" />
