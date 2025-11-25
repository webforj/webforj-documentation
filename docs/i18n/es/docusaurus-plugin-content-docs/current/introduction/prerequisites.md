---
title: Prerequisitos
sidebar_position: 1
_i18n_hash: 079539f07a72647e2faa9a9a5eda5634
---
Comenzar con webforJ es simple, porque hay solo un par de requisitos previos. Usa esta guía para configurar tu entorno de desarrollo con las herramientas esenciales que necesitarás para comenzar a trabajar con webforJ.

<!-- vale off -->
## Java Development Kit (JDK) 21 {#java-development-kit-jdk-21}

<!-- vale on -->

Un Java Development Kit (JDK) es el requisito más importante para desarrollar con webforJ, proporcionando las herramientas necesarias para compilar, ejecutar y gestionar aplicaciones Java. 
Java **21** es requerido para garantizar la compatibilidad con webforJ y acceder a las últimas características y actualizaciones de seguridad del ecosistema Java. El marco webforJ es compatible con los JDK oficiales de Oracle y los JDKs de Eclipse Temurin de código abierto.
<!-- vale off -->
### JDK installation links: {#jdk-installation-links}
<!-- vale on -->
:::tip  
Si estás utilizando un sistema operativo basado en UNIX, se recomienda usar [SDKMAN!](https://sdkman.io/) para gestionar tu entorno Java. Te permite cambiar fácilmente entre diferentes proveedores de Java sin complicaciones adicionales.  

Alternativamente, puedes usar [Jabba](https://github.com/shyiko/jabba), que funciona tanto en sistemas basados en UNIX como en Windows. Es una solución sólida multiplataforma para gestionar versiones de Java.  
:::

- Los JDKs oficiales de Oracle se pueden encontrar en la página de [Descargas de Java](https://www.oracle.com/java/technologies/downloads/) de Oracle. 
  - Selecciona la versión de Java **21**.
  - Haz clic en la pestaña para Linux, macOS o Windows.
  - Haz clic en el enlace que corresponde a la arquitectura de tu computadora. 
  - Consulta la [Guía de instalación de JDK](https://docs.oracle.com/en/java/javase/23/install/overview-jdk-installation.html) de Oracle para obtener información completa sobre la instalación de un JDK de Oracle.
- Los JDKs de código abierto se pueden encontrar en la página de [Últimas versiones de Eclipse Temurin™](https://adoptium.net/temurin/releases/) de Adoptium. 
  - Usa los menús desplegables para seleccionar el sistema operativo, la arquitectura, el tipo de paquete y la versión de JDK **21**.
  - Haz clic en el enlace en la tabla para el tipo de archivo que deseas descargar.
  - Consulta la [Guía de instalación](https://adoptium.net/installation/) de Adoptium para obtener información completa sobre la instalación de un JDK de Eclipse Temurin.

<!-- vale off -->
### Verify your JDK installation {#verify-your-jdk-installation}
<!-- vale on -->
Después de instalar el JDK, verifica la instalación ejecutando el siguiente comando en tu terminal o símbolo del sistema:

```bash
java -version
```

Si tu JDK está instalado correctamente, verás una salida con los detalles de la versión de tu JDK, indicando la versión **21**.
<!-- vale off -->
## Apache Maven {#apache-maven}
<!-- vale on -->

[Apache Maven](https://maven.apache.org/index.html) es una herramienta de automatización de compilación y gestión de dependencias que simplifica el proceso de incluir bibliotecas externas como webforJ en tu proyecto. 
Además de ayudar con la gestión de dependencias, Maven puede automatizar tareas como compilar código, ejecutar pruebas y empaquetar aplicaciones.

### Maven installation links {#maven-installation-links}
- Para instalar la última versión de Maven, ve a la [Página de descarga de Apache Maven](https://maven.apache.org/download.cgi). 
  - La página de [Instalación de Apache Maven](https://maven.apache.org/install.html) de Maven tiene un resumen del proceso de instalación. 
  - La guía más detallada de Baeldung sobre [Cómo instalar Maven en Windows, Linux y Mac](https://www.baeldung.com/install-maven-on-windows-linux-mac) es una guía de instalación más profunda para cada sistema operativo.

<!-- vale off -->
### Verify your Maven installation {#verify-your-maven-installation}

<!-- vale on -->

Después de instalar Maven, verifica la instalación ejecutando el siguiente comando en tu terminal o símbolo del sistema:

```bash
mvn -v
```

Si Maven está instalado correctamente, la salida debería mostrar la versión de Maven, la versión de Java y la información del sistema operativo.

## Java IDE {#java-ide}

Un IDE de Java proporciona un entorno integral para escribir, probar y depurar tu código. Hay muchos IDEs para elegir, así que puedes elegir el que mejor se adapte a tu flujo de trabajo. Algunas opciones populares para el desarrollo en Java incluyen:

- **[Visual Studio Code](https://code.visualstudio.com/Download)**: Un editor de código ligero y extensible con soporte para Java a través de plugins.
- **[IntelliJ IDEA](https://www.jetbrains.com/idea/download/)**: Conocido por su potente soporte para Java y su rico ecosistema de plugins.
- **[NetBeans](https://netbeans.apache.org/download/index.html)**: Un IDE gratuito y de código abierto para Java y otros lenguajes, conocido por su facilidad de uso y plantillas de proyecto integradas.
