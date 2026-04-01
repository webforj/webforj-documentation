---
title: Project Setup
sidebar_position: 1
description: >-
  Discover where to download the tutorial project, how to navigate it, and run
  the apps within.
_i18n_hash: f9a028daac660f61634ad84d00cb0130
---
Para comenzar este tutorial, necesitas una ubicación para tu proyecto donde puedas gestionar tus clases y recursos. Las siguientes secciones describen las diferentes formas en que puedes crear tu proyecto webforJ para este tutorial.

## Usando el código fuente {#using-source-code}

La forma más sencilla de seguir este tutorial es consultar su código fuente. Puedes descargar todo el proyecto o clonarlo desde GitHub:

<!-- vale off -->
- Descargar ZIP: [webforj-tutorial.zip](https://github.com/webforj/webforj-tutorial/archive/refs/heads/main.zip)
- Repositorio de GitHub: Clona el proyecto [directamente desde GitHub](https://github.com/webforj/webforj-tutorial)
<!-- vale on -->
```bash
git clone https://github.com/webforj/webforj-tutorial.git
```

### Estructura del proyecto {#project-structure}

El proyecto tiene cinco subdirectorios, uno para cada paso del tutorial, y cada uno contiene una aplicación ejecutable. Seguirlo te permite ver cómo la aplicación progresa desde una configuración básica hasta un sistema de gestión de clientes completamente funcional.

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
└───5-validating-and-binding-data
```

## Usando startforJ {#using-startforj}

Si prefieres crear un nuevo proyecto, puedes usar [startforJ](https://docs.webforj.com/startforj) para generar un proyecto mínimo de inicio. Consulta [Guía de inicio](/docs/introduction/getting-started) para obtener información más detallada sobre el uso de startforJ.

:::note Configuraciones requeridas
- En el menú desplegable de **versión de webforJ**, elige la versión **25.10 o superior** de webforJ.
- En el menú desplegable de **Sabor**, elige **webforJ + Spring Boot**. 
:::

## Usando la línea de comandos {#using-command-line}

También puedes generar un nuevo proyecto con el siguiente comando:

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
  <TabItem value="cmd" label="Símbolo del sistema">
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

## Configuraciones

Las dos formas mencionadas de crear un nuevo proyecto utilizan [arquetipos](/docs/building-ui/archetypes/overview) de webforJ, que añaden automáticamente las configuraciones necesarias a tu proyecto, como las [dependencias](/docs/integrations/spring/spring-boot#step-2-add-spring-dependencies) de Spring a tu POM y las siguientes propiedades en `src/main/resources/application.properties`:

```
spring.application.name=CustomerApplication
server.port=8080
webforj.entry = com.webforj.tutorial.Application
webforj.debug=true
```

## Ejecutando la aplicación {#running-the-app}

Para ver la aplicación en acción mientras avanzas por el tutorial:

1. Navega al directorio para el paso deseado. Este debe ser el directorio de nivel superior para ese paso, que contiene el `pom.xml`.

2. Usa el siguiente comando de Maven para ejecutar la aplicación Spring Boot localmente:
    ```bash
    mvn
    ```

Ejecutar la aplicación abre automáticamente un nuevo navegador en `http://localhost:8080`.
