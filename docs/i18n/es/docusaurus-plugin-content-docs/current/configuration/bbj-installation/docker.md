---
sidebar_position: 1
title: Docker
_i18n_hash: 8cc797ca5ca7e8ba3a8cd9f3aec41d74
---
# Instalación de Docker

Esta sección de la documentación cubrirá los pasos necesarios para los usuarios que deseen desarrollar utilizando Docker. Los cambios en tu código se realizarán en tu máquina de desarrollo, y la aplicación resultante se ejecutará en Docker.

## 1. Descargando Docker {#1-downloading-docker}

El proceso de instalación de Docker variará ligeramente entre usuarios de Windows, Mac y Linux. Consulta la sección a continuación que corresponde a tu sistema operativo.

### Windows {#windows}

:::info
Se recomienda descargar la versión más reciente de Windows Subsystem for Linux. Más información se puede encontrar [en este enlace](https://learn.microsoft.com/en-us/windows/wsl/install)
:::

**1. Descarga Docker Desktop:**
>- Visita la página de descarga de Docker Desktop para Windows: [Docker Desktop para Windows](https://www.docker.com/products/docker-desktop/)
>- Haz clic en el botón "Obtener Docker Desktop para Windows" para descargar el instalador.

**2. Instala Docker Desktop:**
>- Ejecuta el instalador que descargaste.
>- Sigue el asistente de instalación y asegúrate de habilitar Hyper-V (si se solicita) ya que Docker para Windows utiliza Hyper-V para la virtualización.
>- Una vez completada la instalación, Docker Desktop se iniciará automáticamente.

**3. Verifica la instalación:**
>- Abre una terminal y ejecuta el comando `docker --version` para verificar que Docker está instalado y funcionando correctamente.

### Mac {#mac}

**1. Descarga Docker Desktop:**
>- Visita la página de descarga de Docker Desktop para Mac: [Docker Desktop para Mac](https://www.docker.com/products/docker-desktop/)

**2. Instala Docker Desktop:**
>- Ejecuta el instalador que descargaste.
>- Una vez completada la instalación, Docker Desktop se iniciará automáticamente.

**3. Verifica la instalación:**
>- Abre una terminal y ejecuta el comando `docker --version` para verificar que Docker está instalado y funcionando correctamente.

## 2. Configuración {#2-configuration}

Una vez que Docker Desktop ha sido descargado, busca la imagen más reciente de webforJ, que actualmente tiene el nombre `webforj/sandbox`.

![Búsqueda de Imagen DWCJ](/img/bbj-installation/docker/Step_1l.png#rounded-border)

Haz clic en la lista de etiquetas para ver las opciones disponibles.

![Búsqueda de Imagen DWCJ](/img/bbj-installation/docker/Step_2l.png#rounded-border)

Para la última compilación, selecciona "rc".

![Búsqueda de Imagen DWCJ](/img/bbj-installation/docker/Step_3l.png#rounded-border)

Obtén la imagen para iniciar tu contenedor.

![Búsqueda de Imagen DWCJ](/img/bbj-installation/docker/Step_4l.png#rounded-border)

Una vez que la descarga esté completa, haz clic en el botón de ejecutar, que abrirá la configuración.

![Búsqueda de Imagen DWCJ](/img/bbj-installation/docker/Step_5l.png#rounded-border)

Abre el menú "Configuraciones opcionales".

![Búsqueda de Imagen DWCJ](/img/bbj-installation/docker/Step_6l.png#rounded-border)

Selecciona un puerto de host deseado donde podrás ver tu aplicación en ejecución dentro de Docker.

![Búsqueda de Imagen DWCJ](/img/bbj-installation/docker/Step_7l.png#rounded-border)

Haz clic en "Ejecutar" para iniciar el contenedor.

![Búsqueda de Imagen DWCJ](/img/bbj-installation/docker/Step_8l.png#rounded-border)

:::success Importante
Asegúrate de tomar nota del número de puerto de host personalizado que proporcionas, ya que será necesario más adelante.
:::

## 3. Ejecutando tu aplicación {#3-running-your-app}

Una vez que el contenedor ha sido creado, las aplicaciones de webforJ se pueden ejecutar dentro del contenedor en lugar de localmente. Primero, es necesario configurar correctamente el archivo POM de tu proyecto. Una vez hecho esto, ir a una URL específica en el navegador mostrará la aplicación.

### Configurando tu archivo POM {#configuring-your-pom-file}

Ejecutar un proyecto de webforJ en el contenedor de Docker requerirá el uso del Plugin de Instalación de webforJ, que se puede configurar utilizando tu archivo POM:

Crea una nueva entrada `<plugin>` en la sección `<plugins>` del POM. El siguiente código muestra una entrada de inicio que se puede usar y ajustar según sea necesario para tu proyecto:

:::important
Si tu archivo POM no tiene una sección `<plugins>`, crea una.
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

- Cambia la entrada `<deployurl>` para que use el número de puerto que coincida con el **Host port** que configuraste para tu contenedor en el paso anterior.

- Asegúrate de que la entrada `<classname>` coincida con el nombre de la aplicación que deseas ejecutar.

- Si tus credenciales `<username>` y `<password>` son diferentes para tu instalación de BBj, cámbialas.

### Usando el proyecto inicial {#using-the-starter-project}

<ComponentArchetype
project="bbj-hello-world"
/>

### Lanzando la aplicación {#launching-the-app}

Una vez hecho esto, ejecuta un `mvn install` en el directorio de tu proyecto. Esto ejecutará el plugin de instalación de webforJ y te permitirá acceder a tu aplicación. Para ver la aplicación, querrás ir a la siguiente URL:

`http://localhost:YourHostPort/webapp/YourPublishName`

Reemplaza `YourHostPort` con el puerto de host que configuraste con Docker, y `YourPublishName` se reemplaza por el texto dentro de la etiqueta `<publishname>` del POM. Si se hizo correctamente, deberías ver renderizada tu aplicación.
