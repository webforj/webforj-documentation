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

BBjShip is een BBj-plugin die webforJ-apps naar BBjServices implementeert. Je bouwt en implementeert je app met de [webforJ build-plugin](/docs/configuration/build-plugin), hetzij op de BBj-machine of vanaf een andere machine, zoals een CI-server, en de app draait in BBjServices zoals elke andere app die je beheert in de Enterprise Manager.

BBjShip vervangt de webforJ-installatieplugin die beschreven is in [Running with BBjServices](/docs/configuration/bbj-installation/overview), welke verouderd is en in een toekomstige release zal worden verwijderd.

:::info Alleen Maven
BBjShip werkt met Maven-projecten via de `webforj-maven-plugin`. De webforJ Gradle-plugin ondersteunt BBjShip niet.
:::

## Hoe het werkt {#how-it-works}

Een implementatie verloopt via drie delen:

1. **webforJ build-plugin**: verpakt je app met zijn afhankelijkheden en uploadt deze naar BBjShip
2. **BBjShip**: installeert de app in BBjServices en publiceert deze onder `/webapp/<publish name>`
3. **BBjServices**: draait de app, die je beheert in de Enterprise Manager zoals elke andere app

Tijdens de ontwikkeling draait dezelfde build-plugin je app vanuit je project in de BBjServices van je eigen machine. Wijzigingen die je compileert bereiken de draaiende app zonder een implementatie, en de open pagina's worden bijgewerkt, zie [BBjServices](/docs/configuration/deploy-reload/bbjservices) in Herimplementatie en Live Reload.

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
