---
sidebar_position: 10
title: Web Storage
_i18n_hash: ec80b71a3de50c878acee0f99d4eb371
---
<!-- vale off -->
# Almacenamiento Web <DocChip chip='since' label='23.06' />
<!-- vale on -->

[Almacenamiento web](https://developer.mozilla.org/en-US/docs/Web/API/Web_Storage_API) es un concepto fundamental en el desarrollo web que permite a los sitios web almacenar datos en el lado del cliente. Esto permite que las aplicaciones web guarden el estado, preferencias y otra información localmente en el navegador del usuario. El almacenamiento web proporciona una manera de persistir datos a través de recargas de página y sesiones del navegador, reduciendo la necesidad de solicitudes repetidas al servidor y habilitando capacidades fuera de línea.

webforJ admite tres mecanismos para almacenar datos del cliente: [**Cookies**](#cookies), [**Almacenamiento de Sesión**](#session-storage) y [**Almacenamiento Local**](#local-storage).

:::tip Almacenamiento Web en Herramientas de Desarrollo
Puedes ver las claves y valores actuales de cookies, almacenamiento local y almacenamiento de sesión en las herramientas de desarrollo de tu navegador.
:::

## Resumen de diferencias {#summary-of-differences}
| Característica      | Cookies                                      | Almacenamiento de Sesión                          | Almacenamiento Local                            |
|--------------------|----------------------------------------------|------------------------------------------|------------------------------------------|
| **Persistencia**    | Fecha de expiración configurable             | Duración de la sesión de la página             | Persistente hasta que se elimine explícitamente      |
| **Tamaño de Almacenamiento**   | [4 KB](https://en.wikipedia.org/wiki/HTTP_cookie#Implementation) por cookie                             | Alrededor de [5-10](https://en.wikipedia.org/wiki/Web_storage#Storage_size) MB                           | Alrededor de [5-10](https://en.wikipedia.org/wiki/Web_storage#Storage_size) MB                           |
| **Casos de Uso**      | Autenticación de usuario, preferencias, seguimiento   | Datos temporales, datos de formularios                | Configuraciones persistentes, preferencias de usuario    |
| **Seguridad**       | Vulnerable a XSS, se puede asegurar con banderas | Se borra al finalizar la sesión, menos riesgo        | Accesible a través de JavaScript, riesgo potencial|

## Uso del almacenamiento web {#using-web-storage}
Las clases <JavadocLink type="foundation" location="com/webforj/webstorage/CookieStorage" code='true'>CookieStorage</JavadocLink>, <JavadocLink type="foundation" location="com/webforj/webstorage/SessionStorage" code='true'>SessionStorage</JavadocLink> y <JavadocLink type="foundation" location="com/webforj/webstorage/LocalStorage" code='true'>LocalStorage</JavadocLink> en webforJ extienden todas la clase abstracta <JavadocLink type="foundation" location="com/webforj/webstorage/WebStorage" code='true'>WebStorage</JavadocLink>. Para obtener el objeto apropiado, utiliza los métodos estáticos `CookieStorage.getCurrent()`, `SessionStorage.getCurrent()`, o `LocalStorage.getCurrent()`. Para añadir, obtener y eliminar pares clave-valor, utiliza los métodos `add(key, value)`, `get(key)` y `remove(key)`.

## Cookies {#cookies}
[Las Cookies](https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies) son pequeñas piezas de datos almacenadas en el lado del cliente y enviadas al servidor con cada solicitud HTTP. A menudo se utilizan para recordar sesiones de usuario, preferencias e información de autenticación. Además de los pares clave-valor, las cookies también pueden tener atributos. Para establecer atributos para las cookies, utiliza `add(key, value, attributes)`.

### Características clave: {#key-features}
- Puede almacenar datos a través de diferentes dominios
- Soporta fechas de expiración
- Tamaño de almacenamiento pequeño, típicamente restringido a 4 KB
- Se envían con cada solicitud HTTP
- Pueden tener atributos

:::info Expiración de Cookies
Por defecto, las cookies en webforJ expiran después de 30 días. Puedes cambiar esto con los atributos `max-age` o `expires`.
:::

### Uso de cookies {#using-cookies}

El siguiente fragmento de código demuestra el uso del objeto <JavadocLink type="foundation" location="com/webforj/webstorage/CookieStorage" code='true'>CookieStorage</JavadocLink>.

```java
// Acceder al almacenamiento de cookies
CookieStorage cookieStorage = CookieStorage.getCurrent();

// Añadir una nueva cookie o actualizar una cookie existente
cookieStorage.add("username", "JohnDoe", "Max-Age=3600; Secure; HttpOnly");

// Acceder a una cookie con una clave dada
String username = cookieStorage.get("username");

// Eliminar una cookie con una clave dada
cookieStorage.remove("username");
```
:::info Seguridad de las Cookies
Ciertos atributos de cookies, como `Secure` y `SameSite=None`, requieren un contexto seguro utilizando HTTPS. Estos atributos aseguran que las cookies solo se envíen a través de conexiones seguras, protegiéndolas de ser interceptadas. Para más información, consulta la [documentación de MDN sobre la seguridad de cookies](https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies#security).
:::

### Casos de uso {#use-cases}
Los siguientes casos de uso son adecuados para la utilización de cookies:

- **Autenticación de Usuario**: Almacenar tokens de sesión para mantener a los usuarios conectados.
- **Preferencias**: Guardar preferencias de usuario, como configuraciones de tema o idioma.
- **Seguimiento**: Recoger información sobre el comportamiento del usuario para análisis.

## Almacenamiento de sesión {#session-storage}
El almacenamiento de sesión almacena datos durante la duración de una sesión de página. Los datos son accesibles solo dentro de la misma sesión y se eliminan cuando se cierra la página o la pestaña. Sin embargo, los datos persisten para recargas y restauraciones. El almacenamiento de sesión es mejor para almacenar datos temporales durante una única sesión de página y mantener el estado a través de diferentes páginas en la misma sesión.

### Características clave {#key-features-1}
- Los datos no se envían con cada solicitud HTTP
- Tamaño de almacenamiento más grande que las cookies
- Los datos se eliminan cuando se cierra la página o la pestaña
- Los datos no se comparten entre pestañas

### Uso del almacenamiento de sesión en webforJ {#using-session-storage-in-webforj}

El siguiente fragmento de código demuestra el uso del objeto <JavadocLink type="foundation" location="com/webforj/webstorage/SessionStorage" code='true'>SessionStorage</JavadocLink>.

```java
// Acceder al almacenamiento de sesión
SessionStorage sessionStorage = SessionStorage.getCurrent();

// Añadir un nuevo par de almacenamiento de sesión o actualizar uno existente
sessionStorage.add("currentPage", "3");

// Acceder a un par de almacenamiento de sesión con una clave dada
String currentPage = sessionStorage.get("currentPage");

// Eliminar un par de almacenamiento de sesión con una clave dada
sessionStorage.remove("currentPage");
```

### Casos de uso {#use-cases-1}
Los siguientes casos de uso son adecuados para la utilización del almacenamiento de sesión:

- **Almacenamiento Temporal de Datos**: Almacenar datos que solo necesitan persistir mientras el usuario esté en una página o sesión particular.
- **Datos de Formularios**: Guardar temporalmente datos de formularios para uso dentro de la sesión.

## Almacenamiento local {#local-storage}
El almacenamiento local almacena datos sin fecha de expiración. Persiste incluso después de que se cierra el navegador y puede accederse siempre que el usuario vuelva a visitar el sitio web. El almacenamiento local es mejor para almacenar preferencias o configuraciones del usuario, almacenar en caché datos para mejorar el rendimiento y guardar el estado de la aplicación a través de sesiones.

### Características clave {#key-features-2}

- Los datos persisten entre sesiones
- Los datos no se envían con cada solicitud HTTP.
- Tamaño de almacenamiento más grande que las cookies
- No es adecuado para datos sensibles
- Debes gestionar los datos tú mismo, ya que el navegador nunca los elimina automáticamente

### Uso del almacenamiento local en webforJ {#using-local-storage-in-webforj}

El siguiente fragmento de código demuestra el uso del objeto <JavadocLink type="foundation" location="com/webforj/webstorage/LocalStorage" code='true'>LocalStorage</JavadocLink>.

```java
// Acceder al almacenamiento local
LocalStorage localStorage = LocalStorage.getCurrent();

// Añadir un nuevo par de almacenamiento local o actualizar uno existente
localStorage.add("theme", "dark");

// Acceder a un par de almacenamiento local con una clave dada
String theme = localStorage.get("theme");

// Eliminar un par de almacenamiento local con una clave dada
localStorage.remove("theme");
```

### Casos de uso {#use-cases-2}
Los siguientes casos de uso son adecuados para la utilización del almacenamiento local:

- **Datos Persistentes**: Almacenar datos que deben estar disponibles a través de múltiples sesiones.
- **Preferencias**: Guardar configuraciones y preferencias del usuario que persisten en el tiempo.
