---
sidebar_position: 4
_i18n_hash: a41d592f84a4dcd32f5398f3e57621a4
---
# Instalación Local

Esta sección de la documentación cubrirá los pasos requeridos solo para usuarios que deseen utilizar webforJ para el desarrollo web y/o de aplicaciones con una instancia local de BBj en su máquina. Esta instalación no permitirá que los usuarios contribuyan al código del proyecto webforJ.
<br/>

:::info
Este tutorial cubrirá la instalación en un sistema Windows; los pasos de instalación pueden variar para dispositivos Mac/Linux.
:::
<br/>

La instalación se desglosará en los siguientes pasos:

1. Descarga e instalación de BBj
2. Uso del Administrador de Plugins de BBj para crear tu aplicación
3. Lanzamiento de tu aplicación

:::tip Prerrequisitos
Antes de comenzar, asegúrate de haber revisado los [prerrequisitos](../../introduction/prerequisites) necesarios para configurar y usar webforJ. Esto garantiza que tienes todas las herramientas y configuraciones requeridas en su lugar antes de comenzar tu proyecto.
:::


## 1. Descarga e instalación de BBj {#1-bbj-download-and-installation}

<b>Mientras sigas este paso, asegúrate de instalar la versión de BBj que corresponde a la misma versión de webforJ. </b><br/><br/>

[Este video](https://www.youtube.com/watch?v=Ovk8kznQfGs&ab_channel=BBxCluesbyBASISEurope) puede ayudar con la instalación de BBj si necesitas asistencia con la configuración. La sección de instalación del sitio web de BASIS se puede encontrar [en este enlace](https://basis.cloud/download-product)

:::tip
Se recomienda utilizar la última versión estable de BBj y seleccionar "BBj" de la lista de opciones, sin Barista ni Addon.
:::

<a name='section3'></a>

## 2. Instalar y configurar el plugin de webforJ

Una vez que BBj ha sido instalado, se puede acceder al Administrador de Plugins para instalar herramientas necesarias para configurar webforJ. Para empezar, escribe "Plugin Manager" en el menú de inicio o Finder.

![Plugin manager configuration](/img/bbj-installation/local/Step_1l.png#rounded-border)

Una vez abierto el administrador de plugins, navega a la pestaña "Available Plugins" en la parte superior.

![Plugin manager configuration](/img/bbj-installation/local/Step_2l.png#rounded-border)

Una vez en esta sección, marca la casilla "Show versions under development"

![Plugin manager configuration](/img/bbj-installation/local/Step_3l.png#rounded-border)

La entrada DWCJ debería ser ahora visible en la lista de plugins disponibles para descargar. Haz clic en esta entrada en la lista para seleccionarla.

![Plugin manager configuration](/img/bbj-installation/local/Step_4l.png#rounded-border)

Con la entrada DWCJ seleccionada, haz clic en el botón "Install"

![Plugin manager configuration](/img/bbj-installation/local/Step_5l.png#rounded-border)

Una vez que el plugin ha terminado de instalarse, haz clic en la pestaña "Installed Plugins" en la parte superior.

![Plugin manager configuration](/img/bbj-installation/local/Step_6l.png#rounded-border)

Esta pestaña muestra los plugins instalados, que ahora deberían incluir la entrada DWCJ. Haz clic en la entrada dentro de la lista.

![Plugin manager configuration](/img/bbj-installation/local/Step_7l.png#rounded-border)

Con la entrada DWCJ seleccionada, haz clic en el botón "Configure"

![Plugin manager configuration](/img/bbj-installation/local/Step_8l.png#rounded-border)

En la ventana que se abre, haz clic en el botón "Enable Maven Remote Install" en la parte inferior izquierda de la ventana.

![Plugin manager configuration](/img/bbj-installation/local/Step_9l.png#rounded-border)

:::tip 

Alternativamente, navega al directorio `bin` dentro de tu carpeta `bbx` y ejecuta el siguiente comando:

```bbj
./bbj -tIO DWCJ/cli.bbj - enable_remote_install
```
:::

Debería aparecer un cuadro de diálogo que indique que la instalación remota ha sido habilitada. Haz clic en "OK" para cerrar este diálogo.

## 3. Usando el proyecto inicial
Una vez que BBj y el plugin requerido de webforJ estén instalados y configurados, puedes crear un nuevo proyecto estructurado desde la línea de comandos. Este proyecto viene con las herramientas necesarias para ejecutar tu primer programa webforJ.

<ComponentArchetype
project="bbj-hello-world"
/>

## 4. Lanzando la aplicación

Una vez que se haya hecho esto, ejecuta un `mvn install` en tu directorio del proyecto. Esto ejecutará el plugin de instalación de webforJ y te permitirá acceder a tu aplicación. Para ver la aplicación, debes ir a la siguiente URL:

`http://localhost:YourHostPort/webapp/YourPublishName`

Reemplaza `YourHostPort` con el puerto de host que configuraste con Docker, y `YourPublishName` se reemplaza por el texto dentro de la etiqueta `<publishname>` del POM. 
Si se hizo correctamente, deberías ver tu aplicación renderizada.
