---
title: Upgrade to 25.00
description: Upgrade from 24.00 to 25.00
pagination_next: null
_i18n_hash: 2553d37a63c097b7520f2989849f016b
---
Esta documentación sirve como guía para actualizar aplicaciones webforJ de la versión 24.00 a 25.00. Aquí están los cambios necesarios para que las aplicaciones existentes sigan funcionando sin problemas. Como siempre, consulta el [resumen de lanzamientos de GitHub](https://github.com/webforj/webforj/releases) para una lista más completa de cambios entre versiones.

## Jetty 12 servidores web {#jetty-12-web-servers}

webforJ 25.00 y superiores utilizan Jetty 12, usando la arquitectura de servlets Jakarta EE10. Si estás utilizando el complemento Jetty Maven para el desarrollo, migra de Jakarta EE8 a Jakarta EE10. Esta actualización también requerirá reemplazar cualquier cosa que dependa del paquete `javax.servlet` con el paquete `Jakarta.servlet`.

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

Varios métodos obsoletos de `App` han sido eliminados en 25.00. Las siguientes secciones describen qué métodos fueron reemplazados y las recomendaciones para su reemplazo.

### Registro en consola {#console-logging}

La clase utilitaria [`BrowserConsole`](../advanced/browser-console.md), dedicada a crear registros estilizados en la consola del navegador, reemplaza a los métodos `consoleLog()` y `consoleError()`. Obtén el `BrowserConsole` usando el método `console()`:

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

Consulta [Almacenamiento Web](../advanced/web-storage.md) para más información.

### Clase `Request` {#request-class}

La clase `Request` ahora es responsable de obtener la URL, puerto, host y protocolo de una aplicación. Así que en lugar de usar `App.getUrl()`, usa `App.getCurrent().getUrl()`. El método `getCurrent()` también reemplaza al método `getRequest()` para obtener una instancia de la clase `Request`.

:::info
La clase `Request` también ha eliminado métodos, salta a [`Request`](#request-changes) para verlos.
:::

### Clase `Page` {#page-class}

El método `getPage()` es reemplazado por `Page.getCurrent()` para obtener la instancia actual de la página.

### Diálogos de opciones {#option-dialogs}

En lugar de usar el método `msgbox()`, usa [`OptionDialog.showMessageDialog()`](../components/option-dialogs/message) para crear diálogos de mensajes.

### Terminación de la aplicación {#app-termination}

El método `cleanup()` ha sido eliminado. Ahora hay dos métodos para la terminación, `onWillTerminate()` y `onDidTerminate()`.

Consulta [Ganchos para la terminación](../advanced/terminate-and-error-actions.md#hooks-for-termination) para más información.

## Ordenación de tablas {#table-sorting}

Para webforJ 25.00 y versiones posteriores, las tablas utilizan la ordenación de una sola columna por defecto. Las columnas solo se ordenarán por el encabezado de columna seleccionado más recientemente. Para hacer que una tabla use la ordenación de varias columnas, invoca el método [`setMultiSorting()`](../components/table/sorting#multi-sorting):

```java
table.setMultiSorting(true);
```

## Cuerpo `TabbedPane` oculto {#hidden-tabbedpane-body}

El método `hideBody()` es reemplazado por `setBodyHidden()` para mantener una convención de nomenclatura consistente para los métodos.

## Renderizando HTML dentro de componentes {#rendering-html-inside-components}

En webforJ 25.00 y versiones posteriores, hay un método `setHtml()` para ayudar a distinguir entre establecer texto literal y HTML dentro de un componente. Establecer HTML utilizando el método `setText()` aún es posible, pero ahora requiere envolverlo explícitamente con etiquetas `<html>`.

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

El paquete `com.webforj.component.htmlcontainer` ya no está en webforJ. Usa el paquete `com.webforj.component.element`, que es más completo. Para obtener una lista de las clases webforJ para elementos HTML estándar, visita [Componentes de Elementos HTML](../building-ui/web-components/html-elements.md).

## Cambios en `Request` {#request-changes}

- Al igual que la eliminación del método `getCookieStorage()` para la clase `App`, `Request` ya no tiene el método `getCookie()`. Esto refuerza el uso de `CookieStorgage.getCurrent()` para obtener una instancia de la clase `CookieStorage`.

- El método `getQueryParam()` ahora es `getQueryParameter()`.

## Cambios en `WebforjBBjBridge` {#webforjbbjbridge-changes}

### Obtener una instancia de `WebforjBBjBridge` {#getting-an-instance-of-webforjbbjbridge}

La clase `Environment` ya no tiene el método `getWebforjHelper()`, así que usa `getBridge()` en su lugar.

### Usando el componente `ConfirmDialog` para el método `msgbox()` {#using-the-confirmdialog-component-for-the-msgbox-method}

Versiones anteriores de webforJ usan cadenas e enteros directamente para el método `msgbox()` de `WebforjBBjBridge`. Sin embargo, los mensajes para `WebforjBBjBridge` en webforJ 25.00 y versiones superiores utilizan el componente [`ConfirmDialog`](../components/option-dialogs/confirm.md). Esto proporciona más control sobre qué botones se muestran y el tipo de mensaje.

**Antes**
```java
Environment environment = Environment.getCurrent();
WebforjBBjBridge bridge = environment.getWebforjHelper();

int msgboxResult = bridge.msgbox("¿Estás seguro de que deseas eliminar este archivo?", 1, "Eliminación");
```

**Después**
```java
Environment environment = Environment.getCurrent();
WebforjBBjBridge bridge = environment.getBridge();

ConfirmDialog dialog = new ConfirmDialog(
      "¿Estás seguro de que deseas eliminar este archivo?", "Eliminación",
      ConfirmDialog.OptionType.OK_CANCEL, ConfirmDialog.MessageType.QUESTION);

int msgboxResult = bridge.msgbox(dialog);
```

## Corrección de errores tipográficos en `PasswordMediation` {#passwordmediation-typo-correction}

La clase enum `PasswordMediation`, utilizada para indicar si se requiere que un usuario inicie sesión en cada visita a una aplicación con un componente `Login`, tiene un error tipográfico en versiones anteriores de webforJ. `SILENT` reemplaza el error `SILIENT` para webforJ 25.00 y versiones superiores.

## Métodos de auto-foco {#auto-focusing-methods}

Para mantener la consistencia en webforJ, métodos como `setAutofocus()` y `isAutofocus()` ahora tienen una capitalización uniforme como la interfaz HasAutoFocus. Por lo tanto, componentes como `Dialog` y `Drawer` utilizan `setAutoFocus()` e `isAutoFocus()` para 25.00 y superiores.

## `BBjWindowAdapter` y `Panel` marcados como `final` {#bbjwindowadapter-and-panel-marked-as-final}

Las clases `BBjWindowAdapter` y `Panel` ahora están declaradas como `final`, lo que significa que ya no se pueden subclasificar. Este cambio mejora la estabilidad y refuerza patrones de uso consistentes.
