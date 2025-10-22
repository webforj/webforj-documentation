---
title: Debugging
sidebar_position: 1
_i18n_hash: 057e00d21a3392bb3bf8d1fba1dea15f
---
La depuración es una parte esencial del desarrollo en Java, ayudando a los desarrolladores a identificar y solucionar problemas de manera eficiente. Esta guía explica cómo configurar la depuración en webforJ para Visual Studio Code, IntelliJ IDEA y Eclipse.

<Tabs>
<TabItem value="vscode" label="Visual Studio Code">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/vscode.mp4" type="video/mp4" />
      </video>
</div>

1. Abre tu proyecto de webforJ en VS Code.
2. Presiona <kbd>Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> (o <kbd>Cmd</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> en Mac) para abrir el panel de Ejecución y Depuración.
3. Haz clic en "crear un archivo launch.json"
4. Selecciona Java como el entorno.
5. Modifica `launch.json` para que coincida con lo siguiente:

```json title="launch.json"
{
  "version": "0.2.0",
  "configurations": [
    {
      "type": "java",
      "name": "Adjuntar a Jetty",
      "request": "attach",
      "hostName": "localhost",
      "port": 8000
    }
  ]
}
```

6. Guarda el archivo y haz clic en Iniciar Depuración.

</TabItem>
<TabItem value="intellij" label="IntelliJ IDEA">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/intellij.mp4" type="video/mp4" />
      </video>
</div>

1. Abre tu proyecto en IntelliJ IDEA.
2. Navega a Ejecutar → Editar configuraciones.
3. Haz clic en el botón <kbd>+</kbd> y selecciona Depuración remota de JVM.
4. Establece el host como `localhost` y el puerto como `8000`.
5. Guarda la configuración y haz clic en Depurar para adjuntarte a la aplicación en ejecución.

</TabItem>
<TabItem value="eclipse" label="Eclipse">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/eclipse.mp4" type="video/mp4" />
      </video>
</div>

1. Abre tu proyecto en Eclipse.
2. Ve a Ejecutar → Editar configuraciones.
3. Selecciona Aplicación Java Remota.
4. Haz clic en Nueva configuración y establece:
   - Host: `localhost`
   - Puerto: `8000`
5. Guarda y comienza el depurador.

</TabItem>
</Tabs>

## Ejecutando el depurador {#running-the-debugger}

Una vez que hayas configurado tu IDE:

1. Inicia tu aplicación webforJ usando el comando correspondiente: 
    - Para Jetty, usa `mvnDebug jetty:run` 
    - Para Spring Boot, usa `mvnDebug spring-boot:run`
2. Ejecuta la configuración de depuración en tu IDE.
3. Establece puntos de ruptura y comienza a depurar.

:::tip Consejos de Depuración
1. Asegúrate de que el puerto 8000 esté disponible y no esté bloqueado por ningún cortafuegos.
2. Si estás utilizando alguno de los arquetipos de webforJ y has cambiado el número de puerto en el archivo pom.xml, asegúrate de que el puerto utilizado para la depuración coincida con el valor actualizado.
:::
