---
sidebar_position: 11
title: Execute JavaScript
sidebar_class_name: new-content
description: >-
  Run client-side JavaScript from Java with executeJs, executeJsAsync, and
  executeJsVoidAsync at the app or element level.
slug: execute-javascript
_i18n_hash: c1d5b030c6f39ac6c83afc05ca4bb398
---
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

webforJ se ejecuta en el servidor, pero hay momentos en los que necesitas llegar al cliente: desplazar la ventana, enfocar un campo, leer un valor del navegador o llamar a un método en un componente web. La interfaz <JavadocLink type="foundation" location="com/webforj/concern/HasJsExecution" code='true'>HasJsExecution</JavadocLink> proporciona ese puente. Se implementa en dos niveles:

- La [`Page`](#app-level-execution) ejecuta scripts en el contexto de toda la página.
- Un [`Element`](#element-level-execution) ejecuta scripts limitados a un único elemento del cliente.

Ambos exponen los mismos tres métodos, así que una vez que conozcas las formas de abajo, se leen igual, ya sea que los llames en `Page` o en un `Element`.

## Métodos de ejecución {#execution-methods}

Cada nivel ofrece un método síncrono y dos asincrónicos. La diferencia es si el hilo que llama espera y si se recibe un resultado.

1. **`executeJs(String script)`**: ejecuta el script de forma síncrona. El **hilo de ejecución está bloqueado** hasta que el cliente regresa, lo que cuesta un viaje de ida y vuelta del servidor al cliente. El resultado regresa como un `Object` que puedes convertir y usar en Java.

2. **`executeJsAsync(String script)`**: ejecuta el script de forma asincrónica y **no bloquea el hilo de ejecución**. Devuelve un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> que se completa cuando el script termina, por lo que puedes reaccionar al resultado más tarde.

3. **`executeJsVoidAsync(String script)`**: ejecuta el script de forma asincrónica y no devuelve nada al servidor. Úsalo para trabajos de fuego y olvido donde no necesitas el resultado. Disponible desde `24.11`.

:::tip Elegir un método
Utiliza `executeJsVoidAsync` por defecto cuando solo estés causando un efecto secundario en el cliente (desplazamiento, enfoque, llamada a un método). Utiliza `executeJsAsync` cuando necesites el valor pero quieras mantener la no bloqueante, y reserva el síncrono `executeJs` para el caso raro donde necesitas el resultado antes de que se ejecute la siguiente línea de Java, ya que bloquea el hilo durante un viaje completo de ida y vuelta.
:::

### Leyendo resultados {#reading-results}

Cuando un script devuelve un valor, webforJ lo convierte al tipo de Java correspondiente:

| Valor de JavaScript      | Tipo de Java                           |
| ------------------------ | -------------------------------------- |
| number                   | `Integer`, `Long` o `Double`          |
| string                   | `String`                               |
| boolean                  | `Boolean`                              |
| `null` o `undefined`     | `null`                                 |
| cualquier otro tipo      | su representación en cadena            |

Lee valores con `executeJsAsync`, que aplica la conversión de manera confiable. Un número devuelto puede llegar como `Integer`, `Long` o `Double`, así que léelo a través de `Number`:

```java
Page.getCurrent()
    .executeJsAsync("return window.innerWidth;")
    .thenAccept(result -> {
      int width = ((Number) result).intValue();
      // usar width
    });
```

:::warning Prefiere la forma asincrónica cuando necesites el valor
El `executeJs` síncrono devuelve `null` cuando el contexto de ejecución no está listo, por ejemplo, cuando se llama antes de que el componente esté adjunto. Usa `executeJsAsync` siempre que dependas del valor devuelto, y evita convertir un resultado síncrono a un tipo específico.
:::

## Ejecución a nivel de aplicación {#app-level-execution}

Llama a los métodos en <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page</JavadocLink> cuando el script se refiere a la página en su totalidad en lugar de a un solo componente. Obtén la página actual con `Page.getCurrent()`.

Un caso común es desplazarse de nuevo hacia la parte superior después de un cambio de ruta. No necesita regresarse nada, así que `executeJsVoidAsync` es adecuado:

```java
Page.getCurrent().executeJsVoidAsync(
    "window.scrollTo({ top: 0, behavior: 'smooth' });");
```

Cuando necesitas un valor del cliente en el servidor, léelo de forma asincrónica y actúa sobre el resultado cuando llegue:

```java
Page.getCurrent()
    .executeJsAsync("return navigator.language;")
    .thenAccept(language -> {
      // language es la configuración regional del navegador, por ejemplo "en-US"
      applyLocale(String.valueOf(language));
    });
```

:::info Página versus alcance de elemento
Utiliza [ejecución a nivel de elemento](#element-level-execution) cuando el script necesite actuar sobre un elemento cliente específico en lugar de sobre la página en su conjunto.
:::

En la demostración a continuación, seleccionar **Copiar enlace** ejecuta un script a través de `Page` con `executeJsVoidAsync` para escribir el enlace de invitación en el portapapeles del visitante. Copiar es un efecto secundario sin nada que devolver, así que el método de fuego y olvido es el adecuado.

<ComponentDemo
path='/webforj/executejavascript'
files={[
  'src/main/java/com/webforj/samples/views/javascript/ExecuteJavaScriptView.java',
]}
height='260px'
/>

## Ejecución a nivel de elemento {#element-level-execution}

Llamar a los mismos métodos en un <JavadocLink type="foundation" location="com/webforj/component/element/Element" code='true'>Element</JavadocLink> limita el script a ese elemento en lugar de a la página. Los valores de retorno y el comportamiento síncrono y asincrónico coinciden con los métodos a nivel de página anteriores.

Los scripts de elementos se ponen en cola hasta que el elemento esté adjunto al DOM, luego se ejecutan, por lo que puedes llamarlos durante la configuración sin esperar a la adjunción tú mismo.

### Llamando a una función en un elemento {#calling-a-function}

Cuando deseas invocar una función nombrada del lado del cliente en lugar de ejecutar una cadena de script, `Element` ofrece un conjunto paralelo de métodos. En lugar de un script, pasas el nombre de la función y sus argumentos, que webforJ serializa y pasa. Dos tipos de argumento se manejan de manera especial: `this` se reemplaza con el elemento del cliente y cualquier argumento `Component` se reemplaza con su instancia del cliente una vez adjunta.

Estos reflejan los métodos de ejecución, diferenciándose solo en si el hilo espera y si se devuelve un resultado:

1. **`callJsFunction(String name, Object... args)`**: llama a la función de forma síncrona y devuelve su resultado como un `Object`. El hilo de ejecución se bloquea durante un viaje de ida y vuelta.

2. **`callJsFunctionAsync(String name, Object... args)`**: llama a la función de forma asincrónica sin bloquear, devolviendo un `PendingResult` que se completa con el resultado de la función. Disponible desde `24.11`.

3. **`callJsFunctionVoidAsync(String name, Object... args)`**: llama a la función de forma asincrónica y no devuelve nada al servidor. Úsalo para llamadas de fuego y olvido donde no necesitas el valor de retorno. Disponible desde `24.11`.

Dado que la llamada espera a que cada argumento `Component` se adjunte antes de ejecutarse, una llamada que pasa un componente que nunca se adjunta nunca se completa.

```java
// Enfocar la entrada de un componente web llamando a su método del lado del cliente
searchElement.callJsFunctionVoidAsync("focus");
```
