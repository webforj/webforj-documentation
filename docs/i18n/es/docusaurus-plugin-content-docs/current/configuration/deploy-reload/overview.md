---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
_i18n_hash: ec300e413c9fab01c4723f90f0e4c532
---
Los flujos de trabajo de desarrollo eficientes dependen de herramientas que detectan cambios en el código y actualizan automáticamente la aplicación en tiempo real. La Implementación Continua y la Recarga Dinámica trabajan juntas para simplificar el proceso de desarrollo al reducir los pasos manuales, lo que le permite ver sus cambios rápidamente sin necesidad de reiniciar manualmente el servidor.

## Reimplementación {#redeployment}

La reimplementación en el desarrollo de Java se refiere a la detección y despliegue automáticos de cambios en el código, de modo que las actualizaciones se reflejan en la aplicación sin un reinicio manual del servidor. Este proceso generalmente implica la actualización de clases de Java y recursos web sobre la marcha.

En una aplicación webforJ, esto significa regenerar el archivo WAR cada vez que se realizan modificaciones en el código.

Los cambios en las clases de Java y recursos en el classpath son monitoreados típicamente por el IDE. Cuando una clase de Java se modifica y se guarda el archivo, ya sea de forma automática por el IDE o manualmente por el desarrollador, estas herramientas se activan para compilar y colocar los archivos de clase actualizados en el directorio de destino para aplicar estos cambios.

Se pueden agregar herramientas y configuraciones que automaticen u optimicen la recarga del navegador para una experiencia más fluida.

## Recarga en vivo {#live-reload}

La recarga en vivo asegura que una vez que los cambios están desplegados, el navegador refleja esas actualizaciones en tiempo real sin necesidad de una actualización manual del navegador.

En una aplicación webforJ, la recarga en vivo puede actualizar automáticamente la vista, renderizando de nuevo componentes para mostrar el último estado de la aplicación, o incluso aplicar cambios según sea necesario a demanda.

## Temas {#topics}

<DocCardList className="topics-section" />
