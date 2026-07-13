---
title: Local BLS License
sidebar_class_name: new-content
sidebar_position: 25
description: >-
  Configure a webforJ project to use a locally installed BLS certificate and
  client configuration.
_i18n_hash: 981d7333984cb25b65a90d27775eab9f
---
Un servicio de Licencia BASIS local (BLS) permite que una aplicación webforJ solicite una licencia de un servicio que se ejecuta en su máquina de desarrollo o red interna. Esta configuración es útil cuando tiene un número de serie y un número de autorización y desea que el proyecto utilice los archivos de licencia locales generados en lugar de la configuración de licencia predeterminada.

Un proyecto webforJ creado con [startforJ](https://docs.webforj.com/startforj) incluye dos archivos de licencia en `src/main/resources`:

```text
src/main/resources/blsclient.conf
src/main/resources/certificate.bls
```

Aquí se explica cómo reemplazar los archivos de licencia predeterminados por los generados con una instalación local de BLS:

## Prerrequisitos {#prerequisites}

Antes de comenzar, asegúrese de tener:

- Java 21 o Java 25 disponible para ejecutar el instalador de BLS 26.
- Un número de serie y un número de autorización.
- Un proyecto webforJ con un directorio `src/main/resources`.
- Acceso a la máquina donde se ejecutará BLS.

## 1. Descargar el instalador de BLS {#1-download-bls}

Para obtener el instalador de BLS, dirígete a la página de [Descargas de la Suite de Productos BASIS](https://basis.cloud/bbj-downloads/).
Una vez que haya seleccionado un idioma deseado para el formulario, vaya a la sección **Seleccionar Producto**. En el menú desplegable **Producto**, seleccione `BLS`, y en el menú desplegable **Revisión**, seleccione la última versión. Las versiones de Java requeridas para ejecutar el BLS están en el menú desplegable **Revisión**.

Luego, complete el formulario dentro de **Información de Contacto** y seleccione las casillas dentro de **Términos de Descarga**.
Una vez que haya completado el formulario, seleccione el botón `Descargar` para descargar el JAR del instalador de BLS.

![Formulario de descarga con BLS seleccionado como producto](/img/configuration/local-bls-license/download-bls.png#rounded-border)

*Formulario de descarga con `BLS` seleccionado como producto.*

## 2. Instalar y Configurar el BLS {#2-install-andc-onfig-bls}

El JAR ejecutable descargado tiene la siguiente convención de nombres: `BLS<revision><date>_<time>.jar`. Localice el JAR y haga doble clic en él para iniciar el instalador, o ejecútelo desde una consola de comandos:

```bash
java -jar <downloaded-bls-installer>.jar
```

Siga las indicaciones del instalador y complete los detalles requeridos.

Por defecto, el BLS se instala en directorios específicos dependiendo del sistema operativo, pero se puede cambiar en la ventana **Selección de Directorio**. En adelante, `<blshome>` se refiere a la ubicación de instalación del BLS.

- **Windows**: `C:\bls`
- **macOS**: `/Applications/bls`
- **Otros sistemas operativos**: `/usr/local/bls`

Una vez que haya instalado el BLS, se abrirá el **Asistente de Registro de Licencia**.

### Registro de licencia {#license-registration}

1. En el Asistente de Registro de Licencia, elija la opción `Recuperar una licencia`:

![Asistente de Registro de Licencia con Recuperar una licencia seleccionado](/img/configuration/local-bls-license/retrieve-license.png#rounded-border)

*Asistente de Registro de Licencia con `Recuperar una licencia` seleccionado.*

2. En las siguientes ventanas, ingrese su información de contacto, número de serie y número de autorización.

3. Cuando llegue a la ventana **Métodos de Registro y Entrega de Licencias**, elija `Registrar e instalar una licencia automáticamente`.

Después de registrar su licencia, termine de configurar el BLS local según sea necesario. Si, en un momento posterior, necesita cambiar la configuración de su BLS o recuperar otra licencia, use el BLS Admin en `<blshome>/bin/BLSAdmin`.

## 3. Copiar los archivos de licencia generados {#3-copy-the-generated-license-files}

Ahora, dirígete al directorio `<blshome>/cfg` y localiza los archivos de licencia generados `blsclient.conf` y `certificate.bls`:

![Carpeta cfg de BLS que contiene la configuración de cliente generada y el certificado](/img/configuration/local-bls-license/bls-cfg-folder.png#rounded-border)

*Carpeta de instalación de BLS `cfg` que contiene la configuración de cliente generada y el certificado.*

Copia `blsclient.conf` y `certificate.bls` en tu proyecto webforJ y reemplaza cualquier archivo existente con los mismos nombres en el directorio de recursos. Ahora, cuando tu BLS local esté en funcionamiento, tu aplicación webforJ solicitará la licencia de ese servicio.

```
src
├───main
│   ├───java/
│   └───resources
│       ├───icons/
│       ├───static/
│       ├───application.properties
│       ├───banner.txt
// highlight-next-line
│       ├───blsclient.conf
// highlight-next-line
│       └───certificate.bls
```

:::tip
Si tus archivos de licencia se encuentran fuera del directorio de configuración predeterminado de webforJ, puedes configurar el directorio de licencias con [`webforj.license.cfg`](./properties#configuration-options).
:::
