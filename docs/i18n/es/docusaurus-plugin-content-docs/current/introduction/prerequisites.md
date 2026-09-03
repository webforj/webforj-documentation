---
title: Requisitos previos
description: >-
  What a webforJ development environment needs, a Java 21 or higher JDK, Maven
  or Gradle, and an editor with Java support.
sidebar_position: 1
_i18n_hash: 038e0cf692852d650329b263c25aaf55
---
Comenzar con webforJ es simple, porque solo hay un par de requisitos previos. Usa esta guía para configurar tu entorno de desarrollo con las herramientas esenciales que necesitarás para empezar a trabajar con webforJ.

<!-- vale off -->
## Java Development Kit (JDK) {#java-development-kit-jdk-21}
<!-- vale on -->

webforJ requiere Java **21** o superior. Cualquier distribución en esa versión funciona, así que elige la que ya utiliza tu equipo.

:::tip Recomendado para desarrollo
Desarrolla en una versión de [JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime/releases). Acepta la opción `-XX:+AllowEnhancedClassRedefinition`, que es lo que permite a una [herramienta de hotswap](/docs/configuration/deploy-reload/hotswap) llevar un cambio en la estructura de una clase, un nuevo campo o un nuevo método, a la aplicación en funcionamiento.

En cualquier otra versión, las ediciones dentro del cuerpo de un método aún se aplican en su lugar, y un cambio en la estructura de una clase espera a un reinicio. La elección solo concierne a la máquina en la que desarrollas, y no afecta lo que empaquetas o dónde lo despliegas.
:::

Un gestor de versiones es la forma más fácil de instalar un JDK, y la forma más fácil de moverte entre versiones más adelante. [SDKMAN!](https://sdkman.io/) cubre sistemas UNIX, y [Jabba](https://github.com/Jabba-Team/jabba) cubre sistemas UNIX y Windows. Con SDKMAN!, `sdk install java 21.0.11-jbr` te proporciona un JetBrains Runtime.

Para descargar una versión tú mismo:

- **Oracle JDK**: la página de [Descargas de Java](https://www.oracle.com/java/technologies/downloads/), con la [guía de instalación de Oracle](https://docs.oracle.com/en/java/javase/23/install/overview-jdk-installation.html).
- **Eclipse Temurin**: la página de [últimas versiones](https://adoptium.net/temurin/releases/), con la [guía de instalación de Adoptium](https://adoptium.net/installation/).
- **JetBrains Runtime**: la página de [versiones](https://github.com/JetBrains/JetBrainsRuntime/releases).

Ejecuta `java -version` para confirmar qué versión está en tu ruta.

## Build tool {#build-tool}

webforJ se construye con Maven o Gradle. Los [Arquetipos](/docs/introduction/getting-started) generan proyectos Maven, así que Maven es la forma más rápida de crear una nueva aplicación, y una construcción Gradle existente funciona de la misma manera.

<Tabs>
<TabItem value="maven" label="Maven">

Instala Maven desde la [página de descarga de Apache Maven](https://maven.apache.org/download.cgi), siguiendo las [instrucciones de instalación de Maven](https://maven.apache.org/install.html) o la [guía de Baeldung para cada sistema operativo](https://www.baeldung.com/install-maven-on-windows-linux-mac).

Ejecuta `mvn -v` para confirmar la instalación.

</TabItem>
<TabItem value="gradle" label="Gradle">

Instala Gradle siguiendo la [guía de instalación de Gradle](https://gradle.org/install/).

Ejecuta `gradle -v` para confirmar la instalación. Un proyecto que incluye un envoltorio de Gradle no necesita ninguna instalación en absoluto, ya que `./gradlew` obtiene la versión que el proyecto especifica.

</TabItem>
</Tabs>

Cualquiera de las construcciones ejecuta el trabajo de tiempo de construcción de webforJ a través del [plugin de construcción de webforJ](/docs/configuration/build-plugin), que un proyecto creado a partir de un arquetipo ya tiene.

## Editor {#java-ide}

Cualquier editor con soporte para Java funciona, así que usa el que se adapte a tu flujo de trabajo. Elecciones comunes:

- **[IntelliJ IDEA](https://www.jetbrains.com/idea/download/)**: soporte para Java y un ecosistema de plugins desde el principio.
- **[Visual Studio Code](https://code.visualstudio.com/Download)**: un editor ligero que obtiene su soporte para Java de extensiones.
- **[Zed](https://zed.dev/download)**: un editor de código que adquiere Java a través de una extensión, que descarga y gestiona el servidor de lenguaje Java de Eclipse por ti.
