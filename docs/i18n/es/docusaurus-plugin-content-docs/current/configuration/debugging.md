---
title: Depuración
sidebar_position: 15
description: >-
  Attach a remote Java debugger to a running webforJ app from Visual Studio
  Code, IntelliJ IDEA, or Eclipse using Jetty on port 8000.
_i18n_hash: 6e761f399c4a1f8d562acf85f92af615
---
La depuración es una parte esencial del desarrollo en Java, ya que ayuda a los desarrolladores a identificar y solucionar problemas de manera eficiente. Esta guía explica cómo configurar la depuración en webforJ para Visual Studio Code, IntelliJ IDEA y Eclipse.

<Tabs>
<TabItem value="vscode" label="Visual Studio Code">

<VideoPlayer video="debugging/vscode" width={1920} height={1080} title="VS Code" />

1. Abre tu proyecto webforJ en VS Code.
2. Pulsa <kbd>Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> (o <kbd>Cmd</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> en Mac) para abrir el panel Run and Debug.
3. Haz clic en "create a launch.json file"
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

6. Guarda el archivo y haz clic en Start Debugging.

</TabItem>
<TabItem value="intellij" label="IntelliJ IDEA">

<VideoPlayer video="debugging/intellij" width={1920} height={1080} title="IntelliJ" />

1. Abre tu proyecto en IntelliJ IDEA.
2. Ve a Run → Edit Configurations.
3. Haz clic en el botón <kbd>+</kbd> y selecciona Remote JVM Debug.
4. Establece el host en `localhost` y el puerto en `8000`.
5. Guarda la configuración y haz clic en Debug para conectarte a la aplicación en ejecución.

</TabItem>
<TabItem value="eclipse" label="Eclipse">

<VideoPlayer video="debugging/eclipse" width={1920} height={1080} title="Eclipse" />

1. Abre tu proyecto en Eclipse.
2. Ve a Run → Edit Configurations.
3. Selecciona Remote Java Application.
4. Haz clic en New Configuration y establece:
   - Host: `localhost`
   - Port: `8000`
5. Guarda e inicia el depurador.

</TabItem>
</Tabs>

## Ejecutar el depurador {#running-the-debugger}

Una vez que hayas configurado tu IDE:

1. Inicia tu aplicación webforJ con `mvnDebug`.
2. Ejecuta la configuración de depuración en tu IDE.
3. Establece puntos de interrupción y comienza a depurar.

:::tip Consejos de depuración
1. Asegúrate de que el puerto 8000 esté disponible y no lo bloquee ningún firewall.
2. Si usas alguno de los arquetipos de webforJ y has cambiado el número de puerto en el archivo pom.xml, asegúrate de que el puerto usado para la depuración coincida con el valor actualizado.
:::

## Inspeccionar la aplicación en ejecución {#inspecting-the-running-app}

Un depurador te muestra lo que hace tu código. [craftforJ](/docs/craftforj) te muestra la aplicación que produjo ese código, incluido el árbol de componentes que creó webforJ, las propiedades de cada componente, la ruta activa y quién tiene permiso para acceder a ella. Puedes cambiar una propiedad, ver el resultado en la aplicación en ejecución y volver a escribir ese cambio en el código Java del que provino.

craftforJ se incluye con webforJ y usa el mismo modo de depuración que ya habilitaste, además de una propiedad adicional:

```ini title="webforj.conf"
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

Consulta [Primeros pasos con craftforJ](/docs/craftforj/getting-started).
