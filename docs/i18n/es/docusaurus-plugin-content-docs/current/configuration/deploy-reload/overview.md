---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 9b0d2672241250200ed14343e57d3926
---
Los flujos de trabajo de desarrollo eficientes se basan en herramientas que detectan cambios en el código y actualizan automáticamente la aplicación en tiempo real. La Implementación Continua y la Recarga Dinámica trabajan juntas para simplificar el proceso de desarrollo al reducir pasos manuales, permitiéndote ver tus cambios rápidamente sin necesidad de reiniciar manualmente el servidor.

## Redistribución {#redeployment}

La redistribución en el desarrollo de Java se refiere a detectar y desplegar automáticamente cambios de código, de modo que las actualizaciones se reflejen en la aplicación sin un reinicio manual del servidor. Este proceso normalmente implica actualizar clases de Java y recursos web sobre la marcha.

En una aplicación webforJ, esto significa regenerar el archivo WAR cada vez que se realizan modificaciones en el código.

Los cambios en las clases de Java y recursos en el classpath son generalmente monitoreados por el IDE. Cuando se modifica una clase de Java y se guarda el archivo, ya sea automáticamente por el IDE o manualmente por el desarrollador, estas herramientas se ponen en marcha para compilar y colocar los archivos de clase actualizados en el directorio de destino para aplicar los cambios.

Para obtener la mejor experiencia, utiliza la redistribución automática en combinación con herramientas o configuraciones que automaticen la recarga del navegador.

## Recarga en vivo {#live-reload}

Una vez que se implementan los cambios, la recarga en vivo recarga automáticamente la aplicación para que el navegador refleje las actualizaciones inmediatamente, sin requerir una actualización manual del navegador.

En una aplicación webforJ, la recarga en vivo puede refrescar automáticamente la vista, volviendo a renderizar componentes para mostrar el último estado de la aplicación, o incluso aplicar cambios según sea necesario bajo demanda.

## Temas {#topics}

<DocCardList className="topics-section" />
