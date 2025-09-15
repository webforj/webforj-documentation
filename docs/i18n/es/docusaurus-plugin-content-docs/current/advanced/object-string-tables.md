---
title: Object and String Tables
sidebar_position: 45
_i18n_hash: 2ec33737ccaf06670b4c1cd16369d858
---
La `ObjectTable`, `SessionObjectTable` y `StringTable` proporcionan acceso estático a datos compartidos en un entorno webforJ. Todos son accesibles desde cualquier parte de su aplicación y sirven diferentes propósitos:

- `ObjectTable`: Para almacenar y recuperar objetos Java en toda su aplicación.
- `SessionObjectTable`: Para almacenar y recuperar objetos Java en el contexto de la sesión HTTP.
- `StringTable`: Para trabajar con pares de cadena de clave-valor persistentes, a menudo utilizados para datos de configuración o estilo de entorno.

Estas tablas están disponibles a nivel de entorno y no requieren gestión de instancias.

## `ObjectTable` {#objecttable}

`ObjectTable` es un mapa de clave-valor accesible globalmente para almacenar cualquier objeto Java. Proporciona acceso simple al estado compartido sin necesidad de instanciar o configurar nada. Solo hay una instancia de ObjectTable y se borra cuando la aplicación se actualiza o se finaliza. Es útil en escenarios donde necesita hacer que los datos estén disponibles en múltiples componentes o contextos sin mantener una cadena de referencia.

### Configuración y recuperación de objetos {#setting-and-retrieving-objects}

```java
ObjectTable.put("userInfo", new User("Alice", "admin"));
User user = (User) ObjectTable.get("userInfo");
```

### Comprobando la presencia {#checking-for-presence}

```java
if (ObjectTable.contains("userInfo")) {
  // La clave existe
}
```

### Eliminando entradas {#removing-entries}

```java
ObjectTable.clear("userInfo");
```

### Tamaño de la tabla {#table-size}

```java
int total = ObjectTable.size();
```

## `SessionObjectTable` <DocChip chip='since' label='25.03' /> {#sessionobjecttable}

`SessionObjectTable` proporciona acceso estático a atributos de sesión HTTP cuando se ejecuta en un contenedor Jakarta Servlet 6.1+. A diferencia de `ObjectTable`, que es de alcance de aplicación, `SessionObjectTable` almacena datos en la sesión HTTP del usuario, haciéndolos persistentes entre solicitudes pero únicos para cada sesión de usuario.

Sigue el mismo patrón de API que `ObjectTable` por consistencia.

:::warning
Los objetos almacenados en `SessionObjectTable` deben implementar `Serializable` para admitir la persistencia de la sesión, replicación y pasivación en contenedores de servlets.
:::

:::warning Disponibilidad en `BBjServices`
Esta característica aún no está disponible al ejecutarse con BBjServices en la versión 25.03.
:::

### Configuración y recuperación de objetos de sesión {#setting-and-retrieving-session-objects}

```java
// ShoppingCart debe implementar Serializable
SessionObjectTable.put("cart", new ShoppingCart());
ShoppingCart cart = (ShoppingCart) SessionObjectTable.get("cart");
```

### Comprobando la presencia {#checking-for-presence-session}

```java
if (SessionObjectTable.contains("cart")) {
  // La sesión tiene el carrito
}
```

### Eliminando entradas de sesión {#removing-session-entries}

```java
SessionObjectTable.clear("cart");
```

### Tamaño de la tabla de sesiones {#session-table-size}

```java
int total = SessionObjectTable.size();
```

## `StringTable` {#stringtable}

`StringTable` proporciona acceso estático a variables de cadena globales. Es persistente y está limitado a la aplicación actual. Los valores pueden ser modificados programáticamente o inyectados a través de la configuración del entorno. Este mecanismo es particularmente útil para almacenar valores de configuración, banderas y configuraciones que deben ser accesibles en toda la aplicación pero que no necesitan llevar datos complejos.

### Obtener y configurar valores de cadena {#getting-and-setting-string-values}

```java
StringTable.put("COMPANY", "Acme Corp");
String company = StringTable.get("COMPANY");
```

### Valores preconfigurados desde la configuración {#pre-configured-values-from-config}

Puede definir claves en su archivo [`webforj.conf`](../configuration/properties#configuring-webforjconf):

```
webforj.stringTable = {
  COMPANY: 'Acme Corp'
}
```

Luego acceda a ello en el código:

```java
String val = StringTable.get("COMPANY");
```

### Comprobando la presencia {#checking-for-presence-1}

```java
if (StringTable.contains("COMPANY")) {
  // La clave está establecida
}
```

### Limpiando una clave {#clearing-a-key}

```java
StringTable.clear("COMPANY");
```
