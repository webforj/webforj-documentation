---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
_i18n_hash: 2e8bf7fded04e11ec6bab6d8a7c1c2b5
---
Los flujos de trabajo de desarrollo eficientes dependen de herramientas que detectan cambios en el código y actualizan automáticamente la aplicación en tiempo real. La Implementación Continua y la Recarga Dinámica trabajan juntas para simplificar el proceso de desarrollo al reducir pasos manuales, lo que te permite ver tus cambios rápidamente sin necesidad de reiniciar manualmente el servidor.

## Redistribución {#redeployment}

La redistribución en el desarrollo de Java se refiere a la detección y despliegue automáticos de cambios en el código, de modo que las actualizaciones se reflejen en la aplicación sin un reinicio manual del servidor. Este proceso generalmente implica actualizar clases de Java y recursos web al instante.

En una aplicación de webforJ, esto significa regenerar el archivo WAR cada vez que se realizan modificaciones en el código.

Los cambios en las clases de Java y recursos en el classpath son monitoreados típicamente por el IDE. Cuando una clase de Java se modifica y el archivo se guarda, ya sea automáticamente por el IDE o manualmente por el desarrollador, estas herramientas entran en acción para compilar y colocar los archivos de clase actualizados en el directorio de destino para aplicar estos cambios.

Se pueden agregar herramientas y configuraciones que automaticen u optimicen la recarga del navegador para una experiencia más fluida.

## Recarga en vivo {#live-reload}

La recarga en vivo garantiza que, una vez que se despliegan los cambios, el navegador refleje esas actualizaciones en tiempo real sin necesidad de una actualización manual del navegador.

En una aplicación de webforJ, la recarga en vivo puede actualizar automáticamente la vista, volviendo a renderizar componentes para mostrar el último estado de la aplicación, o incluso aplicar cambios según se necesiten bajo demanda.

## Temas {#topics}

<DocCardList className="topics-section" />
