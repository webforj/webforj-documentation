---
title: Configuración del proyecto
sidebar_position: 1
description: >-
  Discover where to download the tutorial project, how to navigate it, and run
  the apps within.
_i18n_hash: 95bc825bd041018765a732bf464d77b7
---
Para comenzar este tutorial, necesitas una ubicación para tu proyecto donde puedas administrar tus clases y recursos. En las siguientes secciones se describen las distintas formas de crear tu proyecto webforJ para este tutorial.

## Usar el código fuente {#using-source-code}

La forma más sencilla de seguir este tutorial es consultar su código fuente. Puedes descargar el proyecto completo o clonarlo desde GitHub:

<!-- vale off -->
- Descargar ZIP: [webforj-tutorial.zip](https://github.com/webforj/webforj-tutorial/archive/refs/heads/main.zip)
- Repositorio de GitHub: clona el proyecto [directamente desde GitHub](https://github.com/webforj/webforj-tutorial)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-tutorial.git
```

<!-- <VideoPlayer video="tutorial/project-setup" width={1920} height={1080} title="Project setup" /> -->

### Estructura del proyecto {#project-structure}

El proyecto tiene seis subdirectorios, uno por cada paso del tutorial, y cada uno contiene una aplicación ejecutable. Si sigues el tutorial, podrás ver cómo la aplicación evoluciona desde una configuración básica hasta un sistema de administración de clientes completamente funcional.

```
webforj-tutorial
│   .gitignore
│   LICENSE
│   README.md
│
├───1-creating-a-basic-app
├───2-working-with-data
├───3-routing-and-composites
├───4-observers-and-route-parameters
├───5-validating-and-binding-data
└───6-integrating-an-app-layout
```

<!-- vale off -->
## Usar startforJ {#using-startforj}
<!-- vale on -->

Si prefieres crear un proyecto nuevo, puedes usar [startforJ](https://docs.webforj.com/startforj) para generar un proyecto inicial mínimo. Consulta [Introducción](/docs/introduction/getting-started) para obtener información más detallada sobre el uso de startforJ.

:::note Configuración necesaria
- En el menú desplegable **webforJ version**, elige la versión de webforJ **26.01 o superior**.
- En **Runs on**, elige **Spring Boot**.

## Usar la línea de comandos {#using-command-line}

También puedes generar un proyecto nuevo con el siguiente comando:

<!-- vale off -->
<Tabs>
  <TabItem value="bash" label="Bash/Zsh" default>
```bash
mvn -B archetype:generate \
  -DarchetypeGroupId=com.webforj \
  -DarchetypeArtifactId=webforj-archetype-hello-world \
  -DarchetypeVersion=LATEST \
  -DgroupId=com.webforj.tutorial \
  -DartifactId=customer-app \
  -Dversion=1.0-SNAPSHOT \
  -Dflavor=webforj-spring
```
  </TabItem>
  <TabItem value="powershell" label="PowerShell">
```powershell
mvn -B archetype:generate `
  -DarchetypeGroupId="com.webforj" `
  -DarchetypeArtifactId="webforj-archetype-hello-world" `
  -DarchetypeVersion="LATEST" `
  -DgroupId="com.webforj.tutorial" `
  -DartifactId="customer-app" `
  -Dversion="1.0-SNAPSHOT" `
  -Dflavor="webforj-spring"
```
  </TabItem>
  <TabItem value="cmd" label="Command Prompt">
```
mvn -B archetype:generate ^
  -DarchetypeGroupId="com.webforj" ^
  -DarchetypeArtifactId="webforj-archetype-hello-world" ^
  -DarchetypeVersion="LATEST" ^
  -DgroupId="com.webforj.tutorial" ^
  -DartifactId="customer-app" ^
  -Dversion="1.0-SNAPSHOT" ^
  -Dflavor="webforj-spring"
```
  </TabItem>
</Tabs>
<!-- vale on -->

## Configuraciones {#configurations}

Las dos formas mencionadas de crear un proyecto nuevo usan [arquetipos](/docs/building-ui/archetypes/overview) de webforJ, que agregan automáticamente al proyecto las configuraciones necesarias. Esto incluye las [dependencias](/docs/integrations/spring/spring-boot) de Spring, el complemento Maven de webforJ que compila y supervisa los recursos de frontend, y las siguientes propiedades en `src/main/resources/application.properties`:

```
spring.application.name=CustomerApplication
server.port=8080
webforj.entry = com.webforj.tutorial.Application
webforj.debug=true
```

## Ejecutar la aplicación {#running-the-app}

Para ver la aplicación en acción a medida que avanzas en el tutorial:

1. Ve al directorio del paso que quieras. Debe ser el directorio de nivel superior de ese paso, que contiene el archivo `pom.xml`.

2. Usa el siguiente comando de Maven para ejecutar localmente la aplicación Spring Boot:
    ```bash
    mvn
    ```

   El POM generado configura este comando predeterminado para compilar la aplicación, iniciar el supervisor de frontend de webforJ y ejecutar Spring Boot.

<!-- vale Google.WordList = NO -->
Al ejecutar la aplicación, se abre automáticamente una nueva ventana del navegador en `http://localhost:8080`.
<!-- vale Google.WordList = YES -->
