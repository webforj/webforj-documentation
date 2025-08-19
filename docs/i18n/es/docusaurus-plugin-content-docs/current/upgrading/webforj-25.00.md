---
title: Upgrade to 25.00
description: Upgrade from 24.00 to 25.00
pagination_next: null
_i18n_hash: 71f184a02c2552f5af34bfc3ec47c385
---
Esta documentación sirve como guía para actualizar las aplicaciones de webforJ de 24.00 a 25.00.
Aquí están los cambios necesarios para que las aplicaciones existentes sigan funcionando sin problemas.
Como siempre, consulte la [visión general de lanzamientos en GitHub](https://github.com/webforj/webforj/releases) para obtener una lista más completa de cambios entre lanzamientos.


## Servidores web Jetty 12 {#jetty-12-web-servers}

webforJ 25.00 y superior utilizan Jetty 12, usando la arquitectura de servlet Jakarta EE10. Si está utilizando el complemento Maven de Jetty para desarrollo, migre de Jakarta EE8 a Jakarta EE10. Esta actualización también requerirá reemplazar cualquier cosa que dependa del paquete `javax.servlet` con el paquete `Jakarta.servlet`.

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

Varios métodos obsoletos de `App` se eliminan en 25.00. Las siguientes secciones describen qué métodos fueron reemplazados y las sustituciones recomendadas.

### Registro en consola {#console-logging}

La clase utilitaria [`BrowserConsole`](../advanced/browser-console.md), dedicada a crear registros estilizados en la consola del navegador, reemplaza los métodos `consoleLog()` y `consoleError()`. Obtenga el `BrowserConsole` mediante el método `console()`:

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

Para versiones anteriores a webforJ 25.00, la clase `App` tiene los métodos `getLocalStorage()`, `getSessionStorage()`, y `getCookieStorage()` para obtener instancias de las clases `LocalStorage`, `SessionStorage` y `CookieStorage`, respectivamente. A partir de ahora, cada clase tiene un método `getCurrent()`.

Consulte [Almacenamiento Web](../advanced/web-storage.md) para obtener más información.

### Clase `Request` {#request-class}

La clase `Request` ahora es responsable de obtener la URL, el puerto, el host y el protocolo de una aplicación. Así que en lugar de usar `App.getUrl()`, use `App.getCurrent().getUrl()`. El método `getCurrent()` también reemplaza el método `getRequest()` para obtener una instancia de la clase `Request`.

:::info
La clase `Request` también ha eliminado métodos, salte a [`Request`](#request-changes) para verlos.
:::

### Clase `Page` {#page-class}

El método `getPage()` se reemplaza con `Page.getCurrent()` para obtener la instancia de la página actual.

### Diálogos de opciones {#option-dialogs}

En lugar de usar el método `msgbox()`, utilice [`OptionDialog.showMessageDialog()`](../components/option-dialogs/message) para crear diálogos de mensaje.

### Terminación de la aplicación {#app-termination}

El método `cleanup()` ha sido eliminado. Ahora hay dos métodos para terminaciones, `onWillTerminate()` y `onDidTerminate()`.

Consulte [Hooks para la terminación](../advanced/terminate-and-error-actions.md#hooks-for-termination) para obtener más información.

## Ordenamiento de tablas {#table-sorting}

Para webforJ 25.00 y versiones superiores, las tablas utilizan ordenamiento de una sola columna por defecto. Las columnas solo se ordenarán por la cabecera de columna seleccionada más reciente. Para hacer que una tabla utilice ordenamiento de múltiples columnas, invoque el método [`setMultiSorting()`](../components/table/sorting#multi-sorting):

```java
table.setMultiSorting(true);
```

## Cuerpo de `TabbedPane` oculto {#hidden-tabbedpane-body}

El método `hideBody()` se reemplaza con `setBodyHidden()` para mantener una convención de nomenclatura consistente para los métodos.

## Renderizando HTML dentro de componentes {#rendering-html-inside-components}

En webforJ 25.00 y versiones superiores, hay un método `setHtml()` para ayudar a distinguir entre establecer texto literal y HTML dentro de un componente.
Aún es posible establecer HTML usando el método `setText()`, pero ahora requiere envolverlo explícitamente con etiquetas `<html>`.

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

El paquete `com.webforj.component.htmlcontainer` ya no está en webforJ. Utilice el paquete más completo `com.webforj.component.element` en su lugar. Para una lista de clases de webforJ para elementos HTML estándar, vaya a [Componentes de Elementos HTML](../building-ui/web-components/html-elements.md).

## Cambios en `Request` {#request-changes}

- Al igual que la eliminación del método `getCookieStorage()` para la clase `App`, `Request` ya no tiene el método `getCookie()`. Esto refuerza el uso de `CookieStorgage.getCurrent()` para obtener una instancia de la clase `CookieStorage`.

- El método `getQueryParam()` ahora es `getQueryParameter()`.

## Cambios en `WebforjBBjBridge` {#webforjbbjbridge-changes}

### Obteniendo una instancia de `WebforjBBjBridge` {#getting-an-instance-of-webforjbbjbridge}

La clase `Environment` ya no tiene el método `getWebforjHelper()`, por lo que utilice `getBridge()` en su lugar.

### Usando el componente `ConfirmDialog` para el método `msgbox()` {#using-the-confirmdialog-component-for-the-msgbox-method}

Versiones anteriores de webforJ usaron cadenas e enteros directamente para el método `msgbox()` de `WebforjBBjBridge`. Sin embargo, los mensajes para `WebforjBBjBridge` en webforJ 25.00 y versiones superiores utilizan el componente [`ConfirmDialog`](../components/option-dialogs/confirm.md). Esto proporciona más control sobre qué botones se muestran y el tipo de mensaje.


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

<!-- ## Environment.logError eliminado -->

## Corrección de tipografía de `PasswordMediation` {#passwordmediation-typo-correction}

La clase enum `PasswordMediation`, utilizada para indicar si se requiere que un usuario inicie sesión en cada visita a una aplicación con un componente `Login`, tiene un error tipográfico en versiones anteriores de webforJ. `SILENT` reemplaza el error tipográfico `SILIENT` para webforJ 25.00 y versiones superiores.

## Métodos de auto-enfoque {#auto-focusing-methods}

Para mantener la consistencia de webforJ, métodos como `setAutofocus()` e `isAutofocus()` ahora tienen una capitalización uniforme como la interfaz HasAutoFocus. Así que componentes como `Dialog` y `Drawer` utilizan `setAutoFocus()` e `isAutoFocus()` para 25.00 y versiones superiores.

## `BBjWindowAdapter` y `Panel` marcados como `final` {#bbjwindowadapter-and-panel-marked-as-final}

Las clases `BBjWindowAdapter` y `Panel` ahora están declaradas como `final`, lo que significa que ya no se pueden subclasear. Este cambio mejora la estabilidad y refuerza patrones de uso consistentes.
