---
sidebar_position: 4
description: >-
  Install BBj locally and use the Plugin Manager to configure the webforJ plugin
  for running apps against a local BBjServices instance.
_i18n_hash: 5fbe602fa83b6147a07c4e2ad8ac3230
---
# Instalación Local

:::danger Deprecado y será eliminado
El complemento webforJ del Gestor de Complementos BBj descrito en esta página está deprecado y será removido en una futura versión. No comience nuevos proyectos con él. Mueva los proyectos existentes a [BBjShip](/docs/configuration/bbjship/overview), que instala, despliega y ejecuta aplicaciones webforJ en BBjServices. [Proyectos existentes](/docs/configuration/bbjship/existing-projects) enumera los cambios que necesita un proyecto.
:::

Esta sección de la documentación cubrirá los pasos requeridos solo para los usuarios que deseen usar webforJ para el desarrollo web y/o de aplicaciones con una instancia local de BBj en su máquina. Esta instalación no permitirá a los usuarios contribuir al código base de webforJ.
<br/>

:::info
Este tutorial cubrirá la instalación en un sistema Windows; los pasos de instalación pueden variar para dispositivos Mac/Linux.
:::
<br/>

La instalación se desglosará en los siguientes pasos:

1. Descarga e instalación de BBj
2. Uso del Gestor de Complementos BBj para crear su aplicación
3. Lanzamiento de su aplicación

:::tip Prerrequisitos
Antes de comenzar, asegúrese de haber revisado los [prerrequisitos](../../introduction/prerequisites) necesarios para configurar y usar webforJ. Esto asegura que tiene todas las herramientas y configuraciones requeridas en su lugar antes de comenzar su proyecto.
:::


## 1. Descarga e instalación de BBj {#1-bbj-download-and-installation}

<b>Mientras sigue este paso, asegúrese de instalar la versión de BBj que corresponda a la misma versión de webforJ. </b><br/><br/>

[Este video](https://www.youtube.com/watch?v=Ovk8kznQfGs&ab_channel=BBxCluesbyBASISEurope) puede ayudar con la instalación de BBj si necesita asistencia con la configuración. La sección de instalación del sitio web de BASIS se puede encontrar [en este enlace](https://basis.cloud/download-product)

:::tip
Se recomienda utilizar la última revisión estable de BBj y seleccionar "BBj" de la lista de opciones, sin Barista o Addon.
:::

<a name='section3'></a>

## 2. Instalar y configurar el complemento webforJ

Una vez que BBj ha sido instalado, se puede acceder al Gestor de Complementos para instalar las herramientas necesarias para configurar webforJ. Para comenzar, escriba "Gestor de Complementos" en el menú de inicio o Finder.

![Configuración del gestor de complementos](/img/bbj-installation/local/Step_1l.png#rounded-border)

Una vez en esta sección, seleccione la casilla "Mostrar versiones en desarrollo".

![Configuración del gestor de complementos](/img/bbj-installation/local/Step_2l.png#rounded-border)

La entrada DWCJ ahora debería ser visible en la lista de complementos disponibles para descargar. Haga clic en esta entrada en la lista para seleccionarla.

![Configuración del gestor de complementos](/img/bbj-installation/local/Step_3l.png#rounded-border)

Con la entrada DWCJ seleccionada, haga clic en el botón "Instalar".

![Configuración del gestor de complementos](/img/bbj-installation/local/Step_4l.png#rounded-border)

Una vez que el complemento se haya instalado, haga clic en la pestaña "Complementos Instalados" en la parte superior.

![Configuración del gestor de complementos](/img/bbj-installation/local/Step_5l.png#rounded-border)

Esta pestaña muestra los complementos instalados, que ahora debería incluir la entrada DWCJ. Haga clic en la entrada dentro de la lista.

![Configuración del gestor de complementos](/img/bbj-installation/local/Step_6l.png#rounded-border)

Con la entrada DWCJ seleccionada, haga clic en el botón "Configurar".

![Configuración del gestor de complementos](/img/bbj-installation/local/Step_7l.png#rounded-border)

En la ventana que se abre, haga clic en el botón "Habilitar Instalación Remota de Maven" en la parte inferior izquierda de la ventana.

![Configuración del gestor de complementos](/img/bbj-installation/local/Step_8l.png#rounded-border)

:::tip

Alternativamente, navegue al directorio `bin` dentro de su carpeta `bbx` y ejecute el siguiente comando:

```bbj
./bbj -tIO DWCJ/cli.bbj - enable_remote_install
```
:::

Un diálogo debería mostrarse indicando que la instalación remota ha sido habilitada. Haga clic en "OK" para cerrar este diálogo.

![Configuración del gestor de complementos](/img/bbj-installation/local/Step_9l.png#rounded-border)

## 3. Uso del proyecto inicial
Una vez que BBj y el complemento webforJ requerido estén instalados y configurados, puede crear un nuevo proyecto estructurado desde la línea de comandos. Este proyecto viene con las herramientas necesarias para ejecutar su primer programa webforJ.

<ComponentArchetype
project="bbj-hello-world"
/>

## 4. Lanzamiento de la aplicación

Una vez que esto se ha hecho, ejecute un `mvn install` en el directorio de su proyecto. Esto ejecutará el complemento de instalación de webforJ y le permitirá acceder a su aplicación. Para ver la aplicación, querrá ir a la siguiente URL:

`http://localhost:YourHostPort/webapp/YourPublishName`

Reemplace `YourHostPort` con el puerto de host que configuró con Docker, y `YourPublishName` se reemplaza por el texto dentro de la etiqueta `<publishname>` del POM. Si se hizo correctamente, debería ver su aplicación renderizarse.
