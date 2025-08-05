---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
_i18n_hash: d98e2e989be9fbc147c128f8c46f905e
---
Los flujos de trabajo de desarrollo eficientes dependen de herramientas que detectan cambios en el código y actualizan automáticamente la aplicación en tiempo real. La Implementación Continua y la Recarga Dinámica trabajan juntas para simplificar el proceso de desarrollo al reducir los pasos manuales, lo que te permite ver tus cambios rápidamente sin necesidad de reiniciar manualmente el servidor.

## Redistribución {#redeployment}

La redistribución en el desarrollo de Java se refiere a la detección y despliegue automáticos de cambios en el código, para que las actualizaciones se reflejen en la aplicación sin un reinicio manual del servidor. Este proceso normalmente implica la actualización de clases de Java y recursos web sobre la marcha.

En una aplicación webforJ, esto significa regenerar el archivo WAR cada vez que se realizan modificaciones en el código.

Los cambios en las clases de Java y los recursos en el classpath suelen ser monitoreados por el IDE. Cuando una clase de Java se modifica y se guarda el archivo, ya sea automáticamente por el IDE o manualmente por el desarrollador, estas herramientas se activan para compilar y colocar los archivos de clase actualizados en el directorio de destino para aplicar estos cambios.

Se pueden agregar herramientas y configuraciones que automaticen u optimicen la recarga del navegador para una experiencia más fluida.

## Recarga en vivo {#live-reload}

La recarga en vivo asegura que, una vez que se despliegan los cambios, el navegador refleje esas actualizaciones en tiempo real sin necesidad de una actualización manual del navegador.

En una aplicación webforJ, la recarga en vivo puede actualizar automáticamente la vista, volviendo a renderizar componentes para mostrar el estado más reciente de la aplicación, o incluso aplicar cambios según sea necesario bajo demanda.

## Temas {#topics}

<DocCardList className="topics-section" />
