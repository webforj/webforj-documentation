---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
description: >-
  Combine automatic redeployment with live browser reload so code changes appear
  in a running webforJ app without manual restarts.
_i18n_hash: dc1833dbce97bdbf387e98fab07967ca
---
Los flujos de trabajo de desarrollo eficientes dependen de herramientas que detectan cambios en el código y actualizan automáticamente la aplicación en tiempo real. La Implementación Continua y la Recarga Dinámica trabajan juntas para simplificar el proceso de desarrollo al reducir los pasos manuales, permitiéndote ver tus cambios rápidamente sin necesidad de reiniciar manualmente el servidor.

## Reimplementación {#redeployment}

La reimplementación en el desarrollo de Java se refiere a la detección y despliegue automáticos de cambios en el código, de modo que las actualizaciones se reflejen en la aplicación sin necesidad de un reinicio manual del servidor. Este proceso generalmente implica actualizar clases de Java y recursos web sobre la marcha.

En una aplicación webforJ, esto significa regenerar el archivo WAR cada vez que se hacen modificaciones en el código.

Los cambios en las clases de Java y los recursos en el classpath son monitoreados generalmente por el IDE. Cuando se modifica una clase de Java y se guarda el archivo, ya sea automáticamente por el IDE o manualmente por el desarrollador, estas herramientas entran en acción para compilar y colocar los archivos de clase actualizados en el directorio de destino para aplicar los cambios.

Para la mejor experiencia, utiliza la reimplementación automática en combinación con herramientas o configuraciones que automaticen la recarga del navegador.

## Recarga en vivo {#live-reload}

Una vez que se implementan los cambios, la recarga en vivo recarga automáticamente la aplicación para que el navegador refleje las actualizaciones de inmediato, sin requerir una actualización manual del navegador.

En una aplicación webforJ, la recarga en vivo puede actualizar automáticamente la vista, volviendo a renderizar los componentes para mostrar el estado más reciente de la aplicación, o incluso parchear cambios según sea necesario a demanda.

Para las fuentes frontales, el [frontend watch](/docs/configuration/deploy-reload/frontend-watch) reconstruye con cada cambio y parchea una hoja de estilos o imagen en su lugar, recargando la vista solo cuando cambia un script.

## Temas {#topics}

<DocCardList className="topics-section" />
