---
sidebar_position: 1
title: Docker
_i18n_hash: 642936b8f7fd836ca4510eab19087a8c
---
# Instalación de Docker

Esta sección de la documentación cubrirá los pasos requeridos para los usuarios que deseen desarrollar usando Docker. Los cambios en tu código se realizarán en tu máquina de desarrollo, y la aplicación resultante se ejecutará en Docker. 

## 1. Descargando Docker {#1-downloading-docker}

El proceso de instalación para Docker diferirá ligeramente entre los usuarios de Windows, Mac y Linux. Consulta la sección a continuación que corresponda a tu sistema operativo.


### Windows {#windows}

:::info
Se recomienda descargar la última versión del Subsistema de Windows para Linux. Más información se puede encontrar [en este enlace](https://learn.microsoft.com/en-us/windows/wsl/install)
:::

**1. Descargar Docker Desktop:**
>- Visita la página de descarga de Docker Desktop para Windows: [Docker Desktop para Windows](https://www.docker.com/products/docker-desktop/)
>- Haz clic en el botón "Obtener Docker Desktop para Windows" para descargar el instalador.

**2. Instalar Docker Desktop:**
>- Ejecuta el instalador que descargaste.
>- Sigue el asistente de instalación y asegúrate de habilitar Hyper-V (si se solicita), ya que Docker para Windows usa Hyper-V para virtualización.
>- Una vez que la instalación esté completa, Docker Desktop se iniciará automáticamente.

**3. Verificar la instalación:**
>- Abre una terminal y ejecuta el comando `docker --version` para verificar que Docker está instalado y funcionando correctamente.

### Mac {#mac}

**1. Descargar Docker Desktop:**
>- Visita la página de descarga de Docker Desktop para Mac: [Docker Desktop para Mac](https://www.docker.com/products/docker-desktop/)

**2. Instalar Docker Desktop:**
>- Ejecuta el instalador que descargaste.
>- Una vez que la instalación esté completa, Docker Desktop se iniciará automáticamente.

**3. Verificar la instalación:**
>- Abre una terminal y ejecuta el comando `docker --version` para verificar que Docker está instalado y funcionando correctamente.

## 2. Configuración {#2-configuration}

Una vez que Docker Desktop haya sido descargado, busca la última imagen de webforJ, que actualmente está bajo el nombre `webforj/sandbox`.

![Búsqueda de imagen DWCJ](/img/bbj-installation/docker/Step_1l.png#rounded-border)

Haz clic en la lista de etiquetas para ver las opciones disponibles

![Búsqueda de imagen DWCJ](/img/bbj-installation/docker/Step_2l.png#rounded-border)

Para la compilación más reciente, selecciona "rc"

![Búsqueda de imagen DWCJ](/img/bbj-installation/docker/Step_3l.png#rounded-border)

Extrae la imagen para iniciar tu contenedor

![Búsqueda de imagen DWCJ](/img/bbj-installation/docker/Step_4l.png#rounded-border)

Una vez que la descarga esté completa, haz clic en el botón de ejecutar, que abrirá la configuración

![Búsqueda de imagen DWCJ](/img/bbj-installation/docker/Step_5l.png#rounded-border)

Abre el menú "Configuraciones opcionales"

![Búsqueda de imagen DWCJ](/img/bbj-installation/docker/Step_6l.png#rounded-border)

Selecciona un puerto de host deseado donde puedas ver tu aplicación ejecutándose dentro de Docker

![Búsqueda de imagen DWCJ](/img/bbj-installation/docker/Step_7l.png#rounded-border)

Haz clic en "Ejecutar" para iniciar el contenedor

![Búsqueda de imagen DWCJ](/img/bbj-installation/docker/Step_8l.png#rounded-border)

:::success Importante
Asegúrate de tomar nota del número de puerto de host personalizado que proporcionas, ya que será necesario más adelante.
:::

## 3. Ejecutando tu aplicación {#3-running-your-app}

Una vez que el contenedor ha sido creado, las aplicaciones webforJ se pueden ejecutar dentro del contenedor en lugar de localmente. Primero, es necesario configurar correctamente el archivo POM de tu proyecto. Una vez hecho esto, ir a una URL específica en el navegador mostrará la aplicación.

### Configurando tu archivo POM {#configuring-your-pom-file}

Ejecutar un proyecto webforJ en el contenedor de Docker requerirá el uso del WebforJ Install Plugin, que se puede configurar utilizando tu archivo POM:


Crea una nueva entrada `<plugin>` en la sección `<plugins>` del POM. El siguiente código muestra una entrada inicial que se puede usar y modificar según sea necesario para tu proyecto:

:::important
Si tu archivo POM no tiene una sección `<plugins>`, créala.
:::

```xml
<plugin>
<groupId>com.webforj</groupId>
<artifactId>webforj-install-maven-plugin</artifactId>
<version>${webforj.version}</version>
<executions>
    <execution>
    <goals>
        <goal>install</goal>
    </goals>
    </execution>
</executions>
<configuration>
    <deployurl>http://localhost:8888/webforj-install</deployurl>
    <classname>samples.HelloWorldApp</classname>
    <publishname>hello-world</publishname>
    <debug>true</debug>
</configuration>
</plugin>
```

Una vez que se haya creado una entrada similar a la anterior, personaliza la siguiente información:

- Cambia la entrada `<deployurl>` para utilizar el número de puerto que coincide con el **Host port** que configuraste para tu contenedor en el paso anterior.

- Asegúrate de que la entrada `<classname>` coincida con el nombre de la aplicación que deseas ejecutar.

- Si tus credenciales `<username>` y `<password>` son diferentes para tu instalación de BBj, cámbialas.


### Usando el proyecto inicial {#using-the-starter-project}

<ComponentArchetype
project="bbj-hello-world"
/>

### Lanzando la aplicación {#launching-the-app}

Una vez que esto se haya hecho, ejecuta un `mvn install` en el directorio de tu proyecto. Esto ejecutará el WebforJ Install Plugin y te permitirá acceder a tu aplicación. Para ver la aplicación, querrás ir a la siguiente URL:

`http://localhost:YourHostPort/webapp/YourPublishName`

Reemplaza `YourHostPort` con el Host port que configuraste con Docker, y `YourPublishName` es reemplazado por el texto dentro de la etiqueta `<publishname>` del POM. Si todo se hizo correctamente, deberías ver tu aplicación renderizar.
