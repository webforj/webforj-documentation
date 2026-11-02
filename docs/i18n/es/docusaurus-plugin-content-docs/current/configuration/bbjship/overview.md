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

BBjShip es un complemento de BBj que despliega aplicaciones webforJ en BBjServices. Construyes y despliegas tu aplicación con el [complemento de construcción webforJ](/docs/configuration/build-plugin), ya sea en la máquina BBj o desde otra máquina como un servidor CI, y la aplicación se ejecuta en BBjServices como cualquier otra aplicación que gestionas en Enterprise Manager.

BBjShip reemplaza el complemento de instalación webforJ descrito en [Ejecutando con BBjServices](/docs/configuration/bbj-installation/overview), que está obsoleto y será eliminado en una futura versión.

:::info Solo Maven
BBjShip funciona con proyectos Maven a través del `webforj-maven-plugin`. El complemento Gradle de webforJ no es compatible con BBjShip.
:::

## Cómo funciona {#how-it-works}

Un despliegue pasa por tres partes:

1. **complemento de construcción webforJ**: empaqueta tu aplicación con sus dependencias y la sube a BBjShip
2. **BBjShip**: instala la aplicación en BBjServices y la publica bajo `/webapp/<nombre de publicación>`
3. **BBjServices**: ejecuta la aplicación, que gestionas en Enterprise Manager como cualquier otra aplicación

Durante el desarrollo, el mismo complemento de construcción ejecuta tu aplicación desde tu proyecto en los BBjServices de tu propia máquina. Los cambios que compilas alcanzan la aplicación en ejecución sin un despliegue, y las páginas abiertas se actualizan, consulta [BBjServices](/docs/configuration/deploy-reload/bbjservices) en Redeployment y Live Reload.

## Temas {#topics}

<DocCardList className="topics-section" />
