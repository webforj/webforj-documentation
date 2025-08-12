---
sidebar_position: 10
title: Web Storage
_i18n_hash: 12a907c67d42dedcc6ca3b62fe99e549
---
<!-- vale off -->
# Almacenamiento Web <DocChip chip='since' label='23.06' />
<!-- vale on -->

[El almacenamiento web](https://developer.mozilla.org/en-US/docs/Web/API/Web_Storage_API) es un concepto fundamental en el desarrollo web que permite a los sitios web almacenar datos en el lado del cliente. Esto permite que las aplicaciones web guarden el estado, las preferencias y otra información localmente en el navegador del usuario. El almacenamiento web proporciona una manera de persistir datos a través de recargas de página y sesiones de navegador, reduciendo la necesidad de solicitudes repetidas al servidor y habilitando capacidades fuera de línea.

webforJ soporta tres mecanismos para almacenar datos del cliente: [**Cookies**](#cookies), [**Almacenamiento de sesión**](#session-storage) y [**Almacenamiento local**](#local-storage).

:::tip Almacenamiento Web en Herramientas de Desarrollo
Puedes ver las claves y valores actuales de cookies, almacenamiento local y almacenamiento de sesión en las herramientas de desarrollador de tu navegador.
:::

## Resumen de diferencias {#summary-of-differences}
| Característica      | Cookies                                      | Almacenamiento de Sesión                  | Almacenamiento Local                       |
|---------------------|----------------------------------------------|-------------------------------------------|-------------------------------------------|
| **Persistencia**    | Fecha de expiración configurable             | Duración de la sesión de la página       | Persistente hasta que se elimine explícitamente |
| **Tamaño de Almacenamiento** | [4 KB](https://en.wikipedia.org/wiki/HTTP_cookie#Implementation) por cookie                             | Alrededor de [5-10](https://en.wikipedia.org/wiki/Web_storage#Storage_size) MB                           | Alrededor de [5-10](https://en.wikipedia.org/wiki/Web_storage#Storage_size) MB                           |
| **Casos de Uso**    | Autenticación de usuario, preferencias, seguimiento | Datos temporales, datos de formularios    | Configuraciones persistentes, preferencias del usuario |
| **Seguridad**       | Vulnerable a XSS, puede ser asegurado con flags | Eliminado al finalizar la sesión, menos riesgo | Accesible a través de JavaScript, riesgo potencial|

## Usando el almacenamiento web {#using-web-storage}
Las clases <JavadocLink type="foundation" location="com/webforj/webstorage/CookieStorage" code='true'>CookieStorage</JavadocLink>, <JavadocLink type="foundation" location="com/webforj/webstorage/SessionStorage" code='true'>SessionStorage</JavadocLink>, y <JavadocLink type="foundation" location="com/webforj/webstorage/LocalStorage" code='true'>LocalStorage</JavadocLink> en webforJ extienden todas la clase abstracta <JavadocLink type="foundation" location="com/webforj/webstorage/WebStorage" code='true'>WebStorage</JavadocLink>. Para obtener el objeto apropiado, utiliza los métodos estáticos `CookieStorage.getCurrent()`, `SessionStorage.getCurrent()`, o `LocalStorage.getCurrent()`. Para agregar, obtener y eliminar pares clave-valor, usa los métodos `add(key, value)`, `get(key)`, y `remove(key)`.

## Cookies {#cookies}
[Las cookies](https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies) son pequeñas piezas de datos almacenadas en el lado del cliente y enviadas al servidor con cada solicitud HTTP. A menudo se utilizan para recordar sesiones de usuario, preferencias e información de autenticación. Además de los pares clave-valor, las cookies también pueden tener atributos. Para establecer atributos para las cookies, utiliza `add(key, value, attributes)`.

### Características clave: {#key-features}
- Pueden almacenar datos a través de diferentes dominios
- Soportan fechas de expiración
- Tamaño de almacenamiento pequeño, típicamente restringido a 4 KB
- Se envían con cada solicitud HTTP
- Pueden tener atributos

:::info Expiración de Cookies
Por defecto, las cookies en webforJ expiran después de 30 días. Puedes cambiar esto con los atributos `max-age` o `expires`.
:::

### Usando cookies {#using-cookies}

El siguiente fragmento de código demuestra el uso del objeto <JavadocLink type="foundation" location="com/webforj/webstorage/CookieStorage" code='true'>CookieStorage</JavadocLink>.

```java
// Acceder al almacenamiento de cookies
CookieStorage cookieStorage = CookieStorage.getCurrent();

// Agregar una nueva cookie o actualizar una existente
cookieStorage.add("username", "JohnDoe", "Max-Age=3600; Secure; HttpOnly");

// Acceder a una cookie con una clave dada
String username = cookieStorage.get("username");

// Eliminar una cookie con una clave dada
cookieStorage.remove("username");
```
:::info Seguridad de las Cookies
Ciertos atributos de las cookies, como `Secure` y `SameSite=None`, requieren un contexto seguro usando HTTPS. Estos atributos aseguran que las cookies solo se envíen a través de conexiones seguras, protegiéndolas de ser interceptadas. Para más información, consulta la [documentación de MDN sobre la seguridad de cookies](https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies#security).
:::

### Casos de uso {#use-cases}
Los siguientes casos de uso son adecuados para la utilización de cookies:

- **Autenticación de Usuario**: Almacenar tokens de sesión para mantener a los usuarios conectados.
- **Preferencias**: Guardar preferencias de usuario, como configuraciones de tema o idioma.
- **Seguimiento**: Recopilar información sobre el comportamiento del usuario para análisis.


## Almacenamiento de sesión {#session-storage}
El almacenamiento de sesión guarda datos durante la duración de una sesión de página. Los datos son accesibles solo dentro de la misma sesión y se eliminan cuando se cierra la página o pestaña. Sin embargo, los datos persisten en recargas y restauraciones. El almacenamiento de sesión es mejor para almacenar datos temporales durante una sola sesión de página y mantener el estado a través de diferentes páginas en la misma sesión.

### Características clave {#key-features-1}
- Los datos no se envían con cada solicitud HTTP
- Tamaño de almacenamiento más grande que las cookies
- Los datos se eliminan cuando se cierra la página o pestaña
- Los datos no se comparten entre pestañas

### Usando el almacenamiento de sesión en webforJ {#using-session-storage-in-webforj}

El siguiente fragmento de código demuestra el uso del objeto <JavadocLink type="foundation" location="com/webforj/webstorage/SessionStorage" code='true'>SessionStorage</JavadocLink>.

```java
// Acceder al almacenamiento de sesión
SessionStorage sessionStorage = SessionStorage.getCurrent();

// Agregar una nueva pareja de almacenamiento de sesión o actualizar una existente
sessionStorage.add("currentPage", "3");

// Acceder a una pareja de almacenamiento de sesión con una clave dada
String currentPage = sessionStorage.get("currentPage");

// Eliminar una pareja de almacenamiento de sesión con una clave dada
sessionStorage.remove("currentPage");
```

### Casos de uso {#use-cases-1}
Los siguientes casos de uso son adecuados para la utilización del almacenamiento de sesión:

- **Almacenamiento de Datos Temporales**: Almacenar datos que solo necesitan persistir mientras el usuario esté en una página o sesión particular.
- **Datos de Formulario**: Guardar temporalmente datos de formularios para su uso dentro de la sesión.

## Almacenamiento local {#local-storage}
El almacenamiento local guarda datos sin fecha de expiración. Persiste incluso después de cerrar el navegador y puede ser accedido cada vez que el usuario vuelve a visitar el sitio web. El almacenamiento local es mejor para almacenar preferencias del usuario o configuraciones, almacenar en caché datos para mejorar el rendimiento y guardar el estado de la aplicación entre sesiones.

### Características clave {#key-features-2}

- Los datos persisten entre sesiones
- Los datos no se envían con cada solicitud HTTP.
- Tamaño de almacenamiento más grande que las cookies
- No es adecuado para datos sensibles
- Debes gestionar los datos tú mismo, ya que el navegador nunca los elimina automáticamente

### Usando el almacenamiento local en webforJ {#using-local-storage-in-webforj}

El siguiente fragmento de código demuestra el uso del objeto <JavadocLink type="foundation" location="com/webforj/webstorage/LocalStorage" code='true'>LocalStorage</JavadocLink>.

```java
// Acceder al almacenamiento local
LocalStorage localStorage = LocalStorage.getCurrent();

// Agregar una nueva pareja de almacenamiento local o actualizar una existente
localStorage.add("theme", "dark");

// Acceder a una pareja de almacenamiento local con una clave dada
String theme = localStorage.get("theme");

// Eliminar una pareja de almacenamiento local con una clave dada
localStorage.remove("theme");
```

### Casos de uso {#use-cases-2}
Los siguientes casos de uso son adecuados para la utilización del almacenamiento local:

- **Datos Persistentes**: Almacenar datos que deben estar disponibles a través de múltiples sesiones.
- **Preferencias**: Guardar configuraciones y preferencias del usuario que persisten a lo largo del tiempo.
