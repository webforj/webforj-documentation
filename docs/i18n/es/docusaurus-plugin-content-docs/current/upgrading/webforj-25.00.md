---
title: Upgrade to 25.00
description: Upgrade from 24.00 to 25.00
sidebar_position: 30
_i18n_hash: 6fdaf15e67e0015f7319572200ccc353
---
Esta documentación sirve como una guía para actualizar las aplicaciones webforJ de 24.00 a 25.00. Aquí están los cambios necesarios para que las aplicaciones existentes continúen funcionando sin problemas. Como siempre, consulta el [resumen de lanzamientos de GitHub](https://github.com/webforj/webforj/releases) para obtener una lista más completa de los cambios entre lanzamientos.

## Servidores web Jetty 12 {#jetty-12-web-servers}

webforJ 25.00 y versiones superiores utilizan Jetty 12, utilizando la arquitectura de servlets Jakarta EE10. Si estás utilizando el complemento Maven de Jetty para el desarrollo, migra de Jakarta EE8 a Jakarta EE10. Esta actualización también requerirá reemplazar cualquier cosa que dependa del paquete `javax.servlet` con el paquete `Jakarta.servlet`.

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

Varios métodos obsoletos de `App` se han eliminado en 25.00. Las siguientes secciones describen qué métodos fueron reemplazados y las recomendaciones de reemplazo.

### Registro en consola {#console-logging}

La clase utilitaria [`BrowserConsole`](/docs/advanced/browser-console), dedicada a crear registros con estilo en la consola del navegador, reemplaza los métodos `consoleLog()` y `consoleError()`. Obtén el `BrowserConsole` utilizando el método `console()`:

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

Para versiones anteriores a webforJ 25.00, la clase `App` tiene los métodos `getLocalStorage()`, `getSessionStorage()` y `getCookieStorage()` para obtener instancias de las clases `LocalStorage`, `SessionStorage` y `CookieStorage` respectivamente. En adelante, cada clase tiene un método `getCurrent()`.

Consulta [Almacenamiento Web](/docs/advanced/web-storage) para más información.

### Clase `Request` {#request-class}

La clase `Request` ahora es responsable de obtener la URL, puerto, host y protocolo de una aplicación. Así que en lugar de usar `App.getUrl()`, usa `App.getCurrent().getUrl()`. El método `getCurrent()` también reemplaza al método `getRequest()` para obtener una instancia de la clase `Request`.

:::info
La clase `Request` también ha eliminado métodos, salta a [`Request`](#request-changes) para verlos.
:::

### Clase `Page` {#page-class}

El método `getPage()` es reemplazado por `Page.getCurrent()` para obtener la instancia de página actual.

### Diálogos de opciones {#option-dialogs}

En lugar de usar el método `msgbox()`, usa [`OptionDialog.showMessageDialog()`](/docs/components/option-dialogs/message) para crear diálogos de mensajes.

### Terminación de la aplicación {#app-termination}

El método `cleanup()` ha sido eliminado. Ahora hay dos métodos para las terminaciones, `onWillTerminate()` y `onDidTerminate()`.

Consulta [Hooks para la terminación](/docs/advanced/terminate-and-error-actions#hooks-for-termination) para más información.

## Ordenación de tablas {#table-sorting}

Para webforJ 25.00 y versiones superiores, las tablas utilizan la ordenación de una sola columna por defecto. Las columnas solo se ordenarán por el encabezado de columna seleccionado más recientemente. Para hacer que una tabla utilice la ordenación de múltiples columnas, invoca el método [`setMultiSorting()`](/docs/components/table/sorting#multi-sorting):

```java
table.setMultiSorting(true);
```

## Cuerpo oculto de `TabbedPane` {#hidden-tabbedpane-body}

El método `hideBody()` es reemplazado por `setBodyHidden()` para mantener una convención de nomenclatura coherente para los métodos.

## Renderizando HTML dentro de componentes {#rendering-html-inside-components}

En webforJ 25.00 y versiones superiores, hay un método `setHtml()` para ayudar a distinguir entre establecer texto literal y HTML dentro de un componente. Establecer HTML usando el método `setText()` aún es posible, pero ahora requiere envolverlo explícitamente con etiquetas `<html>`.

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

El paquete `com.webforj.component.htmlcontainer` ya no está en webforJ. Utiliza el paquete `com.webforj.component.element` más completo en su lugar. Para una lista de clases de webforJ para elementos HTML estándar, visita [Componentes de Elementos HTML](/docs/components/html-elements).

## Cambios en `Request` {#request-changes}

- Al igual que la eliminación del método `getCookieStorage()` para la clase `App`, `Request` ya no tiene el método `getCookie()`. Esto refuerza el uso de `CookieStorgage.getCurrent()` para obtener una instancia de la clase `CookieStorage`.

- El método `getQueryParam()` ahora es `getQueryParameter()`.

## Cambios en `WebforjBBjBridge` {#webforjbbjbridge-changes}

### Obtener una instancia de `WebforjBBjBridge` {#getting-an-instance-of-webforjbbjbridge}

La clase `Environment` ya no tiene el método `getWebforjHelper()`, así que usa `getBridge()` en su lugar.

### Usando el componente `ConfirmDialog` para el método `msgbox()` {#using-the-confirmdialog-component-for-the-msgbox-method}

Las versiones anteriores de webforJ usaban cadenas e enteros directamente para el método `msgbox()` de `WebforjBBjBridge`. Sin embargo, los mensajes para `WebforjBBjBridge` en webforJ 25.00 y versiones superiores utilizan el componente [`ConfirmDialog`](/docs/components/option-dialogs/confirm). Esto permite un mayor control sobre qué botones se muestran y el tipo de mensaje.

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

## Corrección del error tipográfico en `PasswordMediation` {#passwordmediation-typo-correction}

La clase enum `PasswordMediation`, utilizada para indicar si se requiere que un usuario inicie sesión con cada visita a una aplicación con un componente `Login`, tiene un error tipográfico en versiones anteriores de webfroJ. `SILENT` reemplaza el error tipográfico `SILIENT` para webforJ 25.00 y versiones superiores.

## Métodos de enfoque automático {#auto-focusing-methods}

Para mantener la coherencia de webforJ, métodos como `setAutofocus()` e `isAutofocus()` ahora tienen una capitalización uniforme como la interfaz HasAutoFocus. Por lo tanto, componentes como `Dialog` y `Drawer` utilizan `setAutoFocus()` e `isAutoFocus()` para 25.00 y versiones superiores.

## `BBjWindowAdapter` y `Panel` marcados como `final` {#bbjwindowadapter-and-panel-marked-as-final}

Las clases `BBjWindowAdapter` y `Panel` ahora están declaradas como `final`, lo que significa que ya no se pueden subclasear. Este cambio mejora la estabilidad y refuerza patrones de uso consistentes.
