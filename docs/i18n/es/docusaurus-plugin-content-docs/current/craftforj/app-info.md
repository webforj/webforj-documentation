---
title: Información de la app
sidebar_position: 10
description: >-
  La información de la aplicación de craftforJ muestra lo que realmente ejecuta
  tu app: las versiones de webforJ y BBj Services, el entorno de ejecución de
  Java, el sistema operativo y la raíz del proyecto.
_i18n_title_hash: a98b1ca473ce24658a6b0d7e3da0ce51
_i18n_description_hash: 2e5f0241dcd8b09393fd3e5ddeca035c
_i18n_hash: c2bd1fec7e37fa34291d3ca88047dc04
---
La información de la aplicación informa sobre lo que tu aplicación está ejecutando realmente, lo cual no siempre coincide con lo que tu `pom.xml` dice que debería estar ejecutando. Junto con las versiones de webforJ y los Servicios BBj, cubre el tiempo de ejecución de Java, el sistema operativo y dónde está almacenada la aplicación en el disco.

![La pestaña de Información de la Aplicación](/img/craftforj/app-info/app-info-tab.png#rounded-border)

Dos de estos valores afectan cómo se comporta craftforJ:

- **La raíz del proyecto** es donde craftforJ busca tus fuentes. [Escribir en la fuente](/docs/craftforj/source-changes) no puede funcionar cuando está incorrecto, así que establece [`project-root`](/docs/craftforj/configuration#project-root) si el valor reportado no coincide con tu proyecto.
- **El tiempo de ejecución de Java** determina cuán exhaustivamente se validan los [cambios de Java](/docs/craftforj/ai#it-writes-java) del asistente, porque la validación completa necesita un compilador.

:::tip Presentar un problema
Incluye todo en esta página, junto con un registro descargado de la configuración de resolución de problemas de craftforJ.
:::
