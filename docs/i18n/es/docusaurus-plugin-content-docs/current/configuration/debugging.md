---
title: Depuración
sidebar_position: 15
description: >-
  Attach a remote Java debugger to a running webforJ app from Visual Studio
  Code, IntelliJ IDEA, or Eclipse using Jetty on port 8000.
_i18n_hash: d3f71dbbc660b90f2f4c33fd071c9819
---
La depuración es una parte esencial del desarrollo en Java: ayuda a los desarrolladores a identificar y solucionar problemas de forma eficiente. Esta guía explica cómo configurar la depuración en webforJ con Visual Studio Code, IntelliJ IDEA y Eclipse.

<Tabs>
<TabItem value="vscode" label="Visual Studio Code">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/vscode.mp4" type="video/mp4" />
      </video>
</div>

1. Abre tu proyecto de webforJ en VS Code.
2. Pulsa <kbd>Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> (o <kbd>Cmd</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> en Mac) para abrir el panel Ejecutar y depurar.
3. Haz clic en «create a launch.json file».
4. Selecciona Java como entorno.
5. Modifica `launch.json` para que coincida con lo siguiente:

```json title="launch.json"
{
  "version": "0.2.0",
  "configurations": [
    {
      "type": "java",
      "name": "Attach to Jetty",
      "request": "attach",
      "hostName": "localhost",
      "port": 8000
    }
  ]
}
```

6. Guarda el archivo y haz clic en Iniciar depuración.

</TabItem>
<TabItem value="intellij" label="IntelliJ IDEA">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/intellij.mp4" type="video/mp4" />
      </video>
</div>

1. Abre tu proyecto en IntelliJ IDEA.
2. Ve a Ejecutar → Editar configuraciones.
3. Haz clic en el botón <kbd>+</kbd> y selecciona Depuración remota de JVM.
4. Establece el host en `localhost` y el puerto en `8000`.
5. Guarda la configuración y haz clic en Depurar para conectarte a la aplicación en ejecución.

</TabItem>
<TabItem value="eclipse" label="Eclipse">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/eclipse.mp4" type="video/mp4" />
      </video>
</div>

1. Abre tu proyecto en Eclipse.
2. Ve a Ejecutar → Editar configuraciones.
3. Selecciona Aplicación Java remota.
4. Haz clic en Nueva configuración y establece:
   - Host: `localhost`
   - Puerto: `8000`
5. Guarda la configuración e inicia el depurador.

</TabItem>
</Tabs>

## Ejecutar el depurador {#running-the-debugger}

Una vez que hayas configurado tu IDE:

1. Inicia tu aplicación de webforJ con `mvnDebug`.
2. Ejecuta la configuración de depuración en tu IDE.
3. Establece puntos de interrupción y comienza a depurar.

:::tip Consejos de depuración
1. Asegúrate de que el puerto 8000 esté disponible y que ningún cortafuegos lo bloquee.
2. Si utilizas alguno de los arquetipos de webforJ y has cambiado el número de puerto en el archivo pom.xml, asegúrate de que el puerto usado para la depuración coincida con el valor actualizado.
:::

## Inspeccionar la aplicación en ejecución {#inspecting-the-running-app}

Un depurador te muestra lo que hace tu código. [craftforJ](/docs/craftforj) te muestra la aplicación que genera ese código, incluido el árbol de componentes que webforJ construyó, las propiedades de cada componente, la ruta activa y quién tiene permiso para acceder a ella. Puedes cambiar una propiedad, ver el resultado en la aplicación en ejecución y escribir ese cambio de vuelta en el código Java del que provino.

craftforJ se incluye con webforJ y utiliza el mismo modo de depuración que ya habilitaste, además de una propiedad adicional:

```ini title="webforj.conf"
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

Consulta [Primeros pasos con craftforJ](/docs/craftforj/getting-started).
