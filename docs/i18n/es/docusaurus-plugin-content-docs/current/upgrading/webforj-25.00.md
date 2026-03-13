---
title: Upgrade to 25.00
description: Upgrade from 24.00 to 25.00
pagination_next: null
_i18n_hash: cbb2bd70fa3e51df1096018ff2519878
---
Esta documentación sirve como una guía para actualizar aplicaciones webforJ de 24.00 a 25.00. Aquí están los cambios necesarios para que las aplicaciones existentes continúen funcionando sin problemas. Como siempre, consulte el [resumen de la versión de GitHub](https://github.com/webforj/webforj/releases) para obtener una lista más completa de los cambios entre versiones.

## Servidores web Jetty 12 {#jetty-12-web-servers}

webforJ 25.00 y superiores utilizan Jetty 12, usando la arquitectura de servlets de Jakarta EE10. Si está utilizando el complemento Maven de Jetty para el desarrollo, migre de Jakarta EE8 a Jakarta EE10. Esta actualización también requerirá reemplazar cualquier cosa que dependiera del paquete `javax.servlet` con el paquete `Jakarta.servlet`.

### Cambios en el archivo POM {#pom-file-changes}

**Antes**

```xml {2-4}
<plugin>
  <groupId>org.eclipse.jetty.ee8</groupId>
  <artifactId>jetty-ee8-maven-plugin</artifactId>
  <version>10.x.xx</version>
```
**Después**

```xml {2-4}
<plugin>
  <groupId>org.eclipse.jetty.ee10</groupId>
  <artifactId>jetty-ee10-maven-plugin</artifactId>
  <version>12.x.xx</version>
```

## Cambios en la API para la clase `App` {#api-changes-for-the-app-class}

Se han eliminado varios métodos obsoletos de la clase `App` en 25.00. Las siguientes secciones describen qué métodos fueron reemplazados y las recomendaciones de reemplazo.

### Registro en la consola {#console-logging}

La clase utilitaria [`BrowserConsole`](/docs/advanced/browser-console), dedicada a crear registros con estilo en la consola del navegador, reemplaza los métodos `consoleLog()` y `consoleError()`. Obtenga el `BrowserConsole` utilizando el método `console()`:

```java
public class Application extends App{
  
  @Override
  public void run() throws WebforjException {
    console().log("Mensaje de registro");
    console().error("Mensaje de error");
  }
}
```

### Almacenamiento web {#web-storage}

Para las versiones anteriores a webforJ 25.00, la clase `App` tiene los métodos `getLocalStorage()`, `getSessionStorage()` y `getCookieStorage()` para obtener instancias de las clases `LocalStorage`, `SessionStorage` y `CookieStorage` respectivamente. En adelante, cada clase tiene un método `getCurrent()`.

Consulte [Almacenamiento Web](/docs/advanced/web-storage) para más información.

### Clase `Request` {#request-class}

La clase `Request` ahora es responsable de obtener la URL, el puerto, el host y el protocolo de una aplicación. Así que en lugar de usar `App.getUrl()`, use `App.getCurrent().getUrl()`. El método `getCurrent()` también reemplaza el método `getRequest()` para obtener una instancia de la clase `Request`.

:::info
La clase `Request` también ha eliminado métodos, salte a [`Request`](#request-changes) para verlos.
:::

### Clase `Page` {#page-class}

El método `getPage()` es reemplazado por `Page.getCurrent()` para obtener la instancia de la página actual.

### Diálogos de opciones {#option-dialogs}

En lugar de usar el método `msgbox()`, use [`OptionDialog.showMessageDialog()`](/docs/components/option-dialogs/message) para crear diálogos de mensaje.

### Terminación de la aplicación {#app-termination}

El método `cleanup()` ha sido eliminado. Ahora hay dos métodos para las terminaciones, `onWillTerminate()` y `onDidTerminate()`.

Consulte [Hooks para la terminación](/docs/advanced/terminate-and-error-actions#hooks-for-termination) para más información.

## Ordenación de tablas {#table-sorting}

Para webforJ 25.00 y superiores, las tablas utilizan ordenación de una sola columna de forma predeterminada. Las columnas solo se ordenarán por el encabezado de columna seleccionado más recientemente. Para hacer que una tabla use ordenación de múltiples columnas, invoque el método [`setMultiSorting()`](/docs/components/table/sorting#multi-sorting):

```java
table.setMultiSorting(true);
```

## Cuerpo oculto de `TabbedPane` {#hidden-tabbedpane-body}

El método `hideBody()` es reemplazado por `setBodyHidden()` para mantener una convención de nomenclatura consistente para los métodos.

## Renderización de HTML dentro de componentes {#rendering-html-inside-components}

En webforJ 25.00 y superiores, hay un método `setHtml()` para ayudar a distinguir entre establecer texto literal y HTML dentro de un componente. Establecer HTML utilizando el método `setText()` sigue siendo posible, pero ahora requiere explícitamente envolverlo con etiquetas `<html>`.

```java
// Usos válidos de setText() y setHtml()
Button home = new Button();

home.setText("""
  <html>
    <h1>Inicio</h1>
  </html>
""");

home.setHtml("<h1>Inicio</h1>");

home.setText("Inicio");
```

```java
// Usos inválidos de setText() y setHtml()
Button home = new Button();
home.setText("<h1>Inicio</h1>");
```

## Contenedores HTML {#html-containers}

El paquete `com.webforj.component.htmlcontainer` ya no está en webforJ. Utilice el paquete más rico en características `com.webforj.component.element` en su lugar. Para una lista de clases de webforJ para elementos HTML estándar, dirígete a [Componentes de Elementos HTML](/docs/components/html-elements).

## Cambios en `Request` {#request-changes}

- Al igual que la eliminación del método `getCookieStorage()` para la clase `App`, `Request` ya no tiene el método `getCookie()`. Esto refuerza el uso de `CookieStorgage.getCurrent()` para obtener una instancia de la clase `CookieStorage`.

- El método `getQueryParam()` ahora es `getQueryParameter()`.

## Cambios en `WebforjBBjBridge` {#webforjbbjbridge-changes}

### Obtener una instancia de `WebforjBBjBridge` {#getting-an-instance-of-webforjbbjbridge}

La clase `Environment` ya no tiene el método `getWebforjHelper()`, así que use `getBridge()` en su lugar.

### Usando el componente `ConfirmDialog` para el método `msgbox()` {#using-the-confirmdialog-component-for-the-msgbox-method}

Las versiones anteriores de webforJ utilizan cadenas e enteros directamente para el método `msgbox()` de `WebforjBBjBridge`. Sin embargo, los mensajes para `WebforjBBjBridge` en webforJ 25.00 y superiores utilizan el componente [`ConfirmDialog`](/docs/components/option-dialogs/confirm). Esto proporciona más control sobre qué botones se muestran y el tipo de mensaje.

**Antes**
```java
Environment environment = Environment.getCurrent();
WebforjBBjBridge bridge = environment.getWebforjHelper();

int msgboxResult = bridge.msgbox("¿Está seguro de que desea eliminar este archivo?", 1, "Eliminación");
```

**Después**
```java
Environment environment = Environment.getCurrent();
WebforjBBjBridge bridge = environment.getBridge();

ConfirmDialog dialog = new ConfirmDialog(
      "¿Está seguro de que desea eliminar este archivo?", "Eliminación",
      ConfirmDialog.OptionType.OK_CANCEL, ConfirmDialog.MessageType.QUESTION);

int msgboxResult = bridge.msgbox(dialog);
```

<!-- ## Error de Environment.logError eliminado -->

## Corrección de error tipográfico en `PasswordMediation` {#passwordmediation-typo-correction}

La clase enum `PasswordMediation`, utilizada para indicar si se requiere que un usuario inicie sesión en cada visita a una aplicación con un componente `Login`, tiene un error tipográfico en versiones anteriores de webfroJ. `SILENT` reemplaza el error tipográfico `SILIENT` para webforJ 25.00 y superiores.

## Métodos de enfoque automático {#auto-focusing-methods}

Para mantener la consistencia en webforJ, métodos como `setAutofocus()` e `isAutofocus()` ahora tienen una capitalización uniforme al igual que la interfaz HasAutoFocus. Así que componentes como `Dialog` y `Drawer` utilizan `setAutoFocus()` e `isAutoFocus()` para 25.00 y superiores.

## `BBjWindowAdapter` y `Panel` marcados como `final` {#bbjwindowadapter-and-panel-marked-as-final}

Las clases `BBjWindowAdapter` y `Panel` ahora están declaradas como `final`, lo que significa que ya no se pueden subclasificar. Este cambio mejora la estabilidad y refuerza patrones de uso consistentes.
