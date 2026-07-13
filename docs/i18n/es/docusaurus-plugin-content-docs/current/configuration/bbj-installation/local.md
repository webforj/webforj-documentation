---
sidebar_position: 4
description: >-
  Install BBj locally and use the Plugin Manager to configure the webforJ plugin
  for running apps against a local BBjServices instance.
_i18n_hash: c6cfdc6b07db675d741ea4a096f286ca
---
# Instalación Local

Esta sección de la documentación cubrirá los pasos requeridos únicamente para los usuarios que deseen usar webforJ para el desarrollo web y/o de aplicaciones con una instancia local de BBj en su máquina. Esta instalación no permitirá a los usuarios contribuir al código base de la fundación webforJ.
<br/>

:::info
Esta guía cubrirá la instalación en un sistema Windows; los pasos de instalación pueden variar para dispositivos Mac/Linux.
:::
<br/>

La instalación se dividirá en los siguientes pasos:


1. Descarga e instalación de BBj
2. Uso del Administrador de Plugins de BBj para crear tu aplicación
3. Lanzamiento de tu aplicación


:::tip Requisitos previos
Antes de comenzar, asegúrate de haber revisado los [requisitos previos](../../introduction/prerequisites) necesarios para configurar y usar webforJ. Esto asegura que tengas todas las herramientas y configuraciones requeridas antes de comenzar tu proyecto.
:::


## 1. Descarga e instalación de BBj {#1-bbj-download-and-installation}

<b>Mientras sigas este paso, asegúrate de instalar la versión de BBj que corresponda a la misma versión de webforJ.</b><br/><br/>

[Este video](https://www.youtube.com/watch?v=Ovk8kznQfGs&ab_channel=BBxCluesbyBASISEurope) puede ayudarte con la instalación de BBj si necesitas asistencia con la configuración. La sección de instalación del sitio web de BASIS se puede encontrar [en este enlace](https://basis.cloud/download-product)

:::tip
Se recomienda usar la última versión estable de BBj y seleccionar "BBj" de la lista de opciones, sin Barista o Addon.
:::

<a name='section3'></a>

## 2. Instalar y configurar el plugin de webforJ

Una vez que BBj haya sido instalado, se puede acceder al Administrador de Plugins para instalar herramientas necesarias para configurar webforJ. Para comenzar, escribe "Administrador de Plugins" en el menú de inicio o Finder.

![Configuración del administrador de plugins](/img/bbj-installation/local/Step_1l.png#rounded-border)

Una vez en esta sección, selecciona la casilla "Mostrar versiones en desarrollo"

![Configuración del administrador de plugins](/img/bbj-installation/local/Step_2l.png#rounded-border)

La entrada DWCJ debería ser ahora visible en la lista de plugins disponibles para descarga. Haz clic en esta entrada de la lista para seleccionarla.

![Configuración del administrador de plugins](/img/bbj-installation/local/Step_3l.png#rounded-border)

Con la entrada DWCJ seleccionada, haz clic en el botón "Instalar"

![Configuración del administrador de plugins](/img/bbj-installation/local/Step_4l.png#rounded-border)

Una vez que el plugin haya terminado de instalarse, haz clic en la pestaña "Plugins instalados" en la parte superior.

![Configuración del administrador de plugins](/img/bbj-installation/local/Step_5l.png#rounded-border)

Esta pestaña muestra los plugins instalados, que ahora deberían incluir la entrada DWCJ. Haz clic en la entrada dentro de la lista.

![Configuración del administrador de plugins](/img/bbj-installation/local/Step_6l.png#rounded-border)

Con la entrada DWCJ seleccionada, haz clic en el botón "Configurar"

![Configuración del administrador de plugins](/img/bbj-installation/local/Step_7l.png#rounded-border)

En la ventana que se abre, haz clic en el botón "Habilitar Instalación Remota de Maven" en la parte inferior izquierda de la ventana.

![Configuración del administrador de plugins](/img/bbj-installation/local/Step_8l.png#rounded-border)

:::tip

Alternativamente, navega al directorio `bin` dentro de tu carpeta `bbx` y ejecuta el siguiente comando:

```bbj
./bbj -tIO DWCJ/cli.bbj - enable_remote_install
```
:::

Un diálogo debería mostrar que la instalación remota ha sido habilitada. Haz clic en "OK" para cerrar este diálogo.

![Configuración del administrador de plugins](/img/bbj-installation/local/Step_9l.png#rounded-border)

## 3. Usar el proyecto inicial
Una vez que BBj y el plugin requerido de webforJ estén instalados y configurados, puedes crear un nuevo proyecto estructurado desde la línea de comandos. Este proyecto viene con las herramientas necesarias para ejecutar tu primer programa webforJ.

<ComponentArchetype
project="bbj-hello-world"
/>

## 4. Lanzar la aplicación

Una vez que esto se haya hecho, ejecuta un `mvn install` en tu directorio de proyecto. Esto ejecutará el plugin de instalación de webforJ y te permitirá acceder a tu aplicación. Para ver la aplicación, debes ir a la siguiente URL:

`http://localhost:YourHostPort/webapp/YourPublishName`

Reemplaza `YourHostPort` con el puerto host que configuraste con Docker, y `YourPublishName` es reemplazado por el texto dentro de la etiqueta `<publishname>` del POM. Si se hace correctamente, deberías ver tu aplicación renderizada.
