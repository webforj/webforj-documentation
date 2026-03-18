---
title: Upgrade to 25.00
description: Upgrade from 24.00 to 25.00
pagination_next: null
_i18n_hash: 53afcc2a74e5569086bcf7daeb6582d7
---
Esta documentación sirve como guía para actualizar aplicaciones webforJ de 24.00 a 25.00. Aquí están los cambios necesarios para que las aplicaciones existentes sigan funcionando sin problemas. Como siempre, consulta la [visión general de lanzamientos de GitHub](https://github.com/webforj/webforj/releases) para obtener una lista más completa de cambios entre versiones.

## Servidores web Jetty 12 {#jetty-12-web-servers}

webforJ 25.00 y versiones superiores utilizan Jetty 12, empleando la arquitectura de servlets Jakarta EE10. Si estás utilizando el plugin Jetty Maven para el desarrollo, migra de Jakarta EE8 a Jakarta EE10. Esta actualización también requerirá reemplazar cualquier cosa que dependiera del paquete `javax.servlet` por el paquete `Jakarta.servlet`.

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

Se han eliminado varios métodos obsoletos de `App` en 25.00. Las siguientes secciones describen qué métodos fueron reemplazados y cuáles son las recomendaciones de reemplazo.

### Registro en consola {#console-logging}

La clase utilitaria [`BrowserConsole`](/docs/advanced/browser-console), dedicada a crear logs estilizados en la consola del navegador, reemplaza los métodos `consoleLog()` y `consoleError()`. Obtén el `BrowserConsole` utilizando el método `console()`:

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

Para versiones anteriores a webforJ 25.00, la clase `App` tiene los métodos `getLocalStorage()`, `getSessionStorage()` y `getCookieStorage()` para obtener instancias de las clases `LocalStorage`, `SessionStorage` y `CookieStorage`, respectivamente. En adelante, cada clase tiene un método `getCurrent()`.

Consulta [Almacenamiento Web](/docs/advanced/web-storage) para más información.

### Clase `Request` {#request-class}

La clase `Request` ahora es responsable de obtener la URL, puerto, host y protocolo de una aplicación. Así que en lugar de utilizar `App.getUrl()`, usa `App.getCurrent().getUrl()`. El método `getCurrent()` también reemplaza al método `getRequest()` para obtener una instancia de la clase `Request`.

:::info
La clase `Request` también ha eliminado métodos, salta a [`Request`](#request-changes) para verlos.
:::

### Clase `Page` {#page-class}

El método `getPage()` es reemplazado por `Page.getCurrent()` para obtener la instancia de la página actual.

### Diálogos de opciones {#option-dialogs}

En lugar de usar el método `msgbox()`, utiliza [`OptionDialog.showMessageDialog()`](/docs/components/option-dialogs/message) para crear diálogos de mensajes.

### Terminación de la aplicación {#app-termination}

El método `cleanup()` ha sido eliminado. Ahora hay dos métodos para las terminaciones, `onWillTerminate()` y `onDidTerminate()`.

Consulta [Hooks para la terminación](/docs/advanced/terminate-and-error-actions#hooks-for-termination) para más información.

## Ordenación de tablas {#table-sorting}

Para webforJ 25.00 y versiones superiores, las tablas utilizan ordenación por columna única por defecto. Las columnas solo se ordenarán por el encabezado de columna seleccionado más recientemente. Para que una tabla use ordenación por múltiples columnas, invoca el método [`setMultiSorting()`](/docs/components/table/sorting#multi-sorting):

```java
table.setMultiSorting(true);
```

## Cuerpo oculto de `TabbedPane` {#hidden-tabbedpane-body}

El método `hideBody()` es reemplazado por `setBodyHidden()` para mantener una convención de nombrado consistente para los métodos.

## Renderizando HTML dentro de componentes {#rendering-html-inside-components}

En webforJ 25.00 y versiones superiores, hay un método `setHtml()` para ayudar a distinguir entre establecer texto literal y HTML dentro de un componente. Establecer HTML utilizando el método `setText()` sigue siendo posible, pero ahora requiere envolverlo explícitamente con etiquetas `<html>`.

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

El paquete `com.webforj.component.htmlcontainer` ya no está en webforJ. Usa el paquete más completo `com.webforj.component.element` en su lugar. Para una lista de clases de webforJ para Elementos HTML estándar, ve a [Componentes de Elementos HTML](/docs/components/html-elements).

## Cambios en `Request` {#request-changes}

- Al igual que la eliminación del método `getCookieStorage()` para la clase `App`, `Request` ya no tiene el método `getCookie()`. Esto refuerza el uso de `CookieStorage.getCurrent()` para obtener una instancia de la clase `CookieStorage`.

- El método `getQueryParam()` ahora es `getQueryParameter()`.

## Cambios en `WebforjBBjBridge` {#webforjbbjbridge-changes}

### Obtener una instancia de `WebforjBBjBridge` {#getting-an-instance-of-webforjbbjbridge}

La clase `Environment` ya no tiene el método `getWebforjHelper()`, así que usa `getBridge()` en su lugar.

### Usar el componente `ConfirmDialog` para el método `msgbox()` {#using-the-confirmdialog-component-for-the-msgbox-method}

Las versiones anteriores de webforJ utilizan cadenas e enteros directamente para el método `msgbox()` de `WebforjBBjBridge`. Sin embargo, los mensajes para `WebforjBBjBridge` en webforJ 25.00 y versiones superiores utilizan el componente [`ConfirmDialog`](/docs/components/option-dialogs/confirm). Esto ofrece más control sobre qué botones se muestran y el tipo de mensaje.

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

<!-- ## Environment.logError eliminado -->

## Corrección de typo en `PasswordMediation` {#passwordmediation-typo-correction}

La clase enum `PasswordMediation`, utilizada para indicar si se requiere que un usuario inicie sesión en cada visita a una aplicación con un componente `Login`, tiene un error tipográfico en versiones anteriores de webforJ. `SILENT` reemplaza el typo `SILIENT` para webforJ 25.00 y versiones superiores.

## Métodos de autoenfoque {#auto-focusing-methods}

Para mantener la consistencia en webforJ, métodos como `setAutofocus()` e `isAutofocus()` ahora tienen una capitalización uniforme como la interfaz HasAutoFocus. Así que componentes como `Dialog` y `Drawer` utilizan `setAutoFocus()` e `isAutoFocus()` para 25.00 y versiones superiores.

## `BBjWindowAdapter` y `Panel` marcados como `final` {#bbjwindowadapter-and-panel-marked-as-final}

Las clases `BBjWindowAdapter` y `Panel` ahora están declaradas como `final`, lo que significa que ya no pueden ser subclaseadas. Este cambio mejora la estabilidad y refuerza patrones de uso consistentes.
