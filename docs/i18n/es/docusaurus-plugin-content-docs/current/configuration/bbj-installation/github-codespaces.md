---
sidebar_position: 2
title: Github Codespaces
description: >-
  Run the webforj-hello-world starter in a GitHub Codespace to develop and
  preview webforJ apps directly from the browser.
_i18n_hash: ffbe6dd8d2c6c81e95e7e97dbb8ff32e
---
[`webforj-hello-world`](https://github.com/webforj/webforj-hello-world) ha sido configurado para ejecutarse en Github Codespaces. Codespaces es un entorno de desarrollo basado en la nube, y te permite desarrollar y ejecutar aplicaciones webforJ directamente en tu navegador. Para comenzar a desarrollar con esta herramienta, sigue los pasos a continuación:

## 1. Navega al repositorio HelloWorldJava {#1-navigate-to-the-helloworldjava-repository}

Para empezar, necesitarás ir al repositorio HelloWorldJava, que se puede encontrar [en este enlace](https://github.com/webforj/webforj-hello-world). Una vez allí, haz clic en el botón verde **"Usar esta plantilla"**, y luego en la opción **"Abrir en un codespace"**.

![Botones de Codespace](/img/bbj-installation/github/1.png#rounded-border)

## 2. Ejecuta tu programa {#2-running-your-program}

Después de esperar a que se cargue el codespace, deberías ver una versión del navegador de VS Studio Code abierta con el programa de muestra "HelloWorldJava" cargado. Desde aquí, puedes ejecutar el programa de muestra o comenzar a desarrollar.

Para compilar un programa, abre la terminal en VS Code y ejecuta el comando `mvn install`.

![Instalación de Maven](/img/bbj-installation/github/2.png#rounded-border)

Si todo se completa con éxito, deberías ver el mensaje `BUILD SUCCESS`.

:::warning ADVERTENCIA
Asegúrate de usar el comando `mvn install` en lugar de la interfaz de Maven integrada de VS Code para instalar tu programa.
:::

Una vez hecho esto, necesitarás navegar a una dirección web específica para ver tu programa. Para hacer esto, primero haz clic en la pestaña **"Puertos"** en la parte inferior de VS Code. Aquí verás dos puertos, 8888 y uno más, listados.

![Puertos Reenviados](/img/bbj-installation/github/3.png#rounded-border)

Haz clic en el pequeño botón **"Abrir en el navegador"**, que parece un globo, en la sección **"Dirección local"** de la pestaña **Puertos**, lo que abrirá una nueva pestaña en tu navegador.

![Botón del Navegador](/img/bbj-installation/github/4.png#rounded-border)

Cuando la pestaña del navegador esté abierta, querrás agregar al final de la URL para asegurarte de que tu aplicación se ejecute. Primero, agrega un `/webapp` al final de la dirección web, que terminará en `github.dev`. Después de eso, agrega el nombre correcto de la aplicación y de la clase (si corresponde) para mostrar la aplicación deseada. Para ver cómo configurar correctamente la URL, [sigue esta guía](./configuration).

:::success Consejo
Si deseas ejecutar el programa predeterminado "Hello World", simplemente agrega `/hworld` después de `/webapp` en la URL:
<br />

![URL Modificada](/img/bbj-installation/github/5.png#rounded-border)
:::


Una vez hecho esto, deberías ver tu aplicación ejecutándose en el navegador y puedes modificarla en la instancia de VS Code que se está ejecutando dentro de codespaces.
