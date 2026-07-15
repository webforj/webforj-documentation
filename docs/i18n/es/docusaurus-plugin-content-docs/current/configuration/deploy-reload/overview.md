---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
description: >-
  Combine automatic redeployment with live browser reload so code changes appear
  in a running webforJ app without manual restarts.
_i18n_hash: 1b9e4b7fe64a9bcb0aa2aa16b0866ec9
---
Los flujos de trabajo de desarrollo eficientes dependen de herramientas que detectan cambios en el código y actualizan automáticamente la aplicación en tiempo real. La Implementación Continua y la Recarga Dinámica trabajan juntas para simplificar el proceso de desarrollo al reducir los pasos manuales, lo que te permite ver tus cambios rápidamente sin necesidad de reiniciar manualmente el servidor.

## Reimplementación {#redeployment}

La reimplementación en el desarrollo de Java se refiere a detectar automáticamente y desplegar cambios en el código, de modo que las actualizaciones se reflejen en la aplicación sin un reinicio manual del servidor. Este proceso generalmente implica actualizar las clases de Java y los recursos web sobre la marcha.

En una aplicación webforJ, esto significa regenerar el archivo WAR cada vez que se realizan modificaciones en el código.

Los cambios en las clases de Java y los recursos en el classpath son monitoreados típicamente por el IDE. Cuando se modifica una clase de Java y se guarda el archivo, ya sea automáticamente por el IDE o manualmente por el desarrollador, estas herramientas se activan para compilar y colocar los archivos de clase actualizados en el directorio de destino para aplicar los cambios.

Para obtener la mejor experiencia, utiliza la reimplementación automática en combinación con herramientas o configuraciones que automaticen la recarga del navegador.

## Recarga en vivo {#live-reload}

Una vez que se implementan los cambios, la recarga en vivo actualiza automáticamente la aplicación para que el navegador refleje las actualizaciones de inmediato, sin requerir una actualización manual del navegador.

En una aplicación webforJ, la recarga en vivo puede actualizar automáticamente la vista, re-renderizando los componentes para mostrar el último estado de la aplicación, o incluso aplicar cambios según sea necesario bajo demanda.

Para las fuentes de frontend, el [frontend watch](/docs/configuration/deploy-reload/frontend-watch) reconstruye con cada cambio y aplica una hoja de estilos o imagen en su lugar, recargando la vista solo cuando cambia un script.

## Temas {#topics}

<DocCardList className="topics-section" />
