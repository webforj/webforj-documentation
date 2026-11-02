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

BBjShip ist ein BBj-Plugin, das webforJ-Anwendungen auf BBjServices bereitstellt. Sie erstellen und deployen Ihre Anwendung mit dem [webforJ-Bautool](/docs/configuration/build-plugin), entweder auf der BBj-Maschine oder von einer anderen Maschine wie einem CI-Server, und die Anwendung läuft in BBjServices wie jede andere Anwendung, die Sie im Enterprise Manager verwalten.

BBjShip ersetzt das beschriebene webforJ-Installations-Plugin in [Running with BBjServices](/docs/configuration/bbj-installation/overview), das veraltet ist und in einer zukünftigen Version entfernt wird.

:::info Nur Maven
BBjShip funktioniert mit Maven-Projekten über das `webforj-maven-plugin`. Das webforJ-Gradle-Plugin unterstützt BBjShip nicht.
:::

## So funktioniert es {#how-it-works}

Ein Deployment durchläuft drei Teile:

1. **webforJ-Bautool**: paketiert Ihre Anwendung mit ihren Abhängigkeiten und lädt sie zu BBjShip hoch
2. **BBjShip**: installiert die Anwendung in BBjServices und veröffentlicht sie unter `/webapp/<publish name>`
3. **BBjServices**: führt die Anwendung aus, die Sie im Enterprise Manager wie jede andere Anwendung verwalten

Während der Entwicklung führt dasselbe Bau-Plugin Ihre Anwendung von Ihrem Projekt in den BBjServices Ihrer eigenen Maschine aus. Änderungen, die Sie kompilieren, gelangen ohne Deployment zur laufenden Anwendung, und die offenen Seiten werden aktualisiert, siehe [BBjServices](/docs/configuration/deploy-reload/bbjservices) in Redeployment und Live Reload.

## Themen {#topics}

<DocCardList className="topics-section" />
