---
title: Comenzando
sidebar_position: 2
description: >-
  Create a webforJ app that runs with BBjServices from startforJ or the Maven
  archetypes, run it on your local BBj and deploy it with BBjShip.
_i18n_hash: 913c105077586054f3af87216fe848e0
---
Esta guía crea una aplicación webforJ que se ejecuta con BBjServices, la ejecuta en tu BBj local durante el desarrollo y la implementa con BBjShip.

:::tip Requisitos previos
Necesitas un proyecto Maven y una instalación de BBj de la misma versión que webforJ; por ejemplo, BBj 26.03 para webforJ 26.03, con BBjServices en ejecución. BBjShip no es compatible con Gradle. Consulta los [requisitos previos](/docs/introduction/prerequisites) para conocer las demás herramientas.
:::

## Crear una aplicación BBj {#create-a-bbj-app}

Tienes dos opciones para crear una aplicación webforJ nueva que se ejecute con BBjServices: usar la herramienta gráfica startforJ o la línea de comandos de Maven.

<!-- vale off -->
### Opción 1: usar startforJ {#option-1-using-startforj}
<!-- vale on -->

Al crear una aplicación con [startforJ](https://docs.webforj.com/startforj), puedes personalizarla proporcionando la siguiente información:

- Metadatos básicos del proyecto (nombre de la aplicación, ID de grupo, ID de artefacto)
- Versión de webforJ y versión de Java
- Color del tema e icono
- Arquetipo
- **Runs on**: selecciona **BBjServices** para crear un proyecto que se ejecute con BBjServices

### Opción 2: usar la línea de comandos {#option-2-using-the-command-line}

Si prefieres usar la línea de comandos, genera el proyecto con los [arquetipos oficiales de webforJ](/docs/building-ui/archetypes/overview):

<!-- vale off -->
<Tabs>
  <TabItem value="bash" label="Bash/Zsh" default>
    ```bash {8}
    mvn -B archetype:generate \
    -DarchetypeGroupId=com.webforj \
    -DarchetypeArtifactId=webforj-archetype-hello-world \
    -DarchetypeVersion=LATEST \
    -DgroupId=org.example \
    -DartifactId=my-app \
    -Dversion=1.0-SNAPSHOT \
    -Dflavor=webforj-bbj
    ```
  </TabItem>
  <TabItem value="powershell" label="PowerShell">
    ```powershell {8}
    mvn -B archetype:generate `
    -DarchetypeGroupId="com.webforj" `
    -DarchetypeArtifactId="webforj-archetype-blank" `
    -DarchetypeVersion="LATEST" `
    -DgroupId="org.example" `
    -DartifactId="my-app" `
    -Dversion="1.0-SNAPSHOT" `
    -Dflavor="webforj-bbj"
    ```
  </TabItem>
  <TabItem value="cmd" label="Command Prompt">
    ```console {8}
    mvn -B archetype:generate ^
    -DarchetypeGroupId="com.webforj" ^
    -DarchetypeArtifactId="webforj-archetype-blank" ^
    -DarchetypeVersion="LATEST" ^
    -DgroupId="org.example" ^
    -DartifactId="my-app" ^
    -Dversion="1.0-SNAPSHOT" ^
    -Dflavor="webforj-bbj"
    ```
  </TabItem>
</Tabs>
<!-- vale on -->

Con el parámetro `flavor`, el arquetipo genera un proyecto que se ejecuta con BBjServices.

Esto crea un proyecto con:
- La dependencia `webforj` sin el motor webforJ, que proporciona BBjServices
- El complemento Maven de webforJ, configurado para implementar la aplicación en BBjShip en tu BBj local, usando el ID de artefacto como nombre de la aplicación
- HotswapAgent para las ejecuciones de desarrollo
- Vistas de ejemplo
- Archivos de configuración de webforJ para desarrollo y producción

## Ejecutar la aplicación {#run-the-app}

Un proyecto de arquetipo establece su objetivo Maven predeterminado, por lo que `mvn` sin argumentos compila la aplicación e inicia una [ejecución de desarrollo](/docs/configuration/deploy-reload/bbjservices) en tu BBjServices local:

```bash
mvn
```

La primera ejecución prepara tu BBj local. Instala BBjShip y agrega HotswapAgent a BBjServices, lo que provoca que BBjServices se reinicie una vez. Luego, la compilación muestra la dirección de la aplicación:

```text
[INFO] webforJ watch: my-app is running at http://localhost:8888/webapp/my-app
[INFO] webforJ watch: press <kbd>Ctrl</kbd>+<kbd>C</kbd> to stop the app and the watch
```

Las clases que compiles llegan a la aplicación en ejecución sin necesidad de reiniciarla. Detén la compilación con <kbd>Ctrl</kbd>+<kbd>C</kbd> para detener la aplicación.

## Implementar la aplicación {#deploy-the-app}

Para implementar la aplicación empaquetada, ejecuta:

```bash
mvn install
```

La aplicación se publica en `http://localhost:8888/webapp/my-app` hasta que vuelvas a implementarla o la elimines. Consulta [Implementación](/docs/configuration/bbjship/deploying) para implementarla en otro servidor BBj, por ejemplo, desde una canalización de CI.
