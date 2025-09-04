---
sidebar_position: 1
title: Docker
_i18n_hash: ec7566378c3ec80f071b7391742ec353
---
# Instalación de Docker

Esta sección de la documentación cubrirá los pasos necesarios para los usuarios que deseen desarrollar utilizando Docker. Los cambios en su código se realizarán en su máquina de desarrollo, y la aplicación resultante se ejecutará en Docker.

## 1. Descargando Docker {#1-downloading-docker}

El proceso de instalación de Docker diferirá ligeramente entre usuarios de Windows, Mac y Linux. Consulte la sección a continuación que corresponda a su sistema operativo.

### Windows {#windows}

:::info
Se recomienda descargar la última versión del Subsistema de Windows para Linux. Más información se puede encontrar [en este enlace](https://learn.microsoft.com/en-us/windows/wsl/install)
:::

**1. Descargar Docker Desktop:**
>- Visite la página de descarga de Docker Desktop para Windows: [Docker Desktop para Windows](https://www.docker.com/products/docker-desktop/)
>- Haga clic en el botón "Obtener Docker Desktop para Windows" para descargar el instalador.

**2. Instalar Docker Desktop:**
>- Ejecute el instalador que descargó.
>- Siga el asistente de instalación y asegúrese de habilitar Hyper-V (si se le solicita), ya que Docker para Windows utiliza Hyper-V para la virtualización.
>- Una vez que la instalación esté completa, Docker Desktop se iniciará automáticamente.

**3. Verificar la instalación:**
>- Abra una terminal y ejecute el comando `docker --version` para verificar que Docker está instalado y funcionando correctamente.

### Mac {#mac}

**1. Descargar Docker Desktop:**
>- Visite la página de descarga de Docker Desktop para Mac: [Docker Desktop para Mac](https://www.docker.com/products/docker-desktop/)

**2. Instalar Docker Desktop:**
>- Ejecute el instalador que descargó.
>- Una vez que la instalación esté completa, Docker Desktop se iniciará automáticamente.

**3. Verificar la instalación:**
>- Abra una terminal y ejecute el comando `docker --version` para verificar que Docker está instalado y funcionando correctamente.

## 2. Configuración {#2-configuration}

Una vez que Docker Desktop ha sido descargado, busque la última imagen de webforJ, que actualmente se encuentra bajo el nombre `webforj/sandbox`.

![Búsqueda de imagen DWCJ](/img/bbj-installation/docker/Step_1l.png#rounded-border)

Haga clic en la lista de etiquetas para ver las opciones disponibles

![Búsqueda de imagen DWCJ](/img/bbj-installation/docker/Step_2l.png#rounded-border)

Para la compilación más reciente, seleccione "rc"

![Búsqueda de imagen DWCJ](/img/bbj-installation/docker/Step_3l.png#rounded-border)

Descargue la imagen para iniciar su contenedor

![Búsqueda de imagen DWCJ](/img/bbj-installation/docker/Step_4l.png#rounded-border)

Una vez que la descarga esté completa, haga clic en el botón de ejecutar, que abrirá la configuración

![Búsqueda de imagen DWCJ](/img/bbj-installation/docker/Step_5l.png#rounded-border)

Abra el menú de "Configuraciones opcionales"

![Búsqueda de imagen DWCJ](/img/bbj-installation/docker/Step_6l.png#rounded-border)

Seleccione un puerto de host deseado donde pueda ver su aplicación ejecutándose dentro de Docker

![Búsqueda de imagen DWCJ](/img/bbj-installation/docker/Step_7l.png#rounded-border)

Haga clic en "Ejecutar" para iniciar el contenedor

![Búsqueda de imagen DWCJ](/img/bbj-installation/docker/Step_8l.png#rounded-border)

:::success Importante
Asegúrese de tomar nota del número de puerto de host personalizado que proporciona, ya que será necesario más adelante.
:::

## 3. Ejecutando su aplicación {#3-running-your-app}

Una vez que el contenedor ha sido creado, las aplicaciones webforJ se pueden ejecutar dentro del contenedor en lugar de localmente. Primero, es necesario configurar correctamente el archivo POM de su proyecto. Una vez que esto se haya hecho, ir a una URL específica en el navegador mostrará la aplicación.

### Configurando su archivo POM {#configuring-your-pom-file}

Ejecutar un proyecto webforJ en el contenedor de Docker requerirá el uso del Plugin de Instalación de webforJ, que se puede configurar utilizando su archivo POM:

Cree una nueva entrada `<plugin>` en la sección `<plugins>` del POM. El siguiente código muestra una entrada inicial que se puede utilizar y ajustar según sea necesario para su proyecto:

:::important
Si su archivo POM no tiene una sección `<plugins>`, créela.
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

Una vez que se haya creado una entrada similar a la anterior, personalice la siguiente información:

- Cambie la entrada `<deployurl>` para usar el número de puerto que coincida con el **Puerto de host** que configuró para su contenedor en el paso anterior.

- Asegúrese de que la entrada `<classname>` coincida con el nombre de la aplicación que desea ejecutar.

- Si sus credenciales `<username>` y `<password>` son diferentes para su instalación de BBj, cámbielas.

### Usando el proyecto inicial {#using-the-starter-project}

<ComponentArchetype
project="bbj-hello-world"
/>

### Lanzando la aplicación {#launching-the-app}

Una vez que esto haya sido hecho, ejecute un `mvn install` en su directorio del proyecto. Esto ejecutará el plugin de instalación de webforJ y le permitirá acceder a su aplicación. Para ver la aplicación, querrá ir a la siguiente URL:

`http://localhost:YourHostPort/webapp/YourPublishName`

Reemplace `YourHostPort` con el puerto de host que configuró con Docker, y `YourPublishName` se reemplaza con el texto dentro de la etiqueta `<publishname>` del POM. Si se hace correctamente, debería ver su aplicación renderizarse.
