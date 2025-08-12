---
title: Object and String Tables
sidebar_position: 35
_i18n_hash: aa2c014d8043f9ad53dfabcdc39844da
---
La `ObjectTable` y la `StringTable` proporcionan acceso estático a datos compartidos en un entorno webforJ. Ambas son accesibles desde cualquier parte de tu aplicación y sirven para diferentes propósitos:

- `ObjectTable`: Para almacenar y recuperar objetos Java a través de tu aplicación.
- `StringTable`: Para trabajar con pares de clave-valor de cadenas persistentes, a menudo utilizados para datos de configuración o de estilo de entorno.

Estas tablas están disponibles a nivel de entorno y no requieren gestión de instancias.

## `ObjectTable` {#objecttable}

`ObjectTable` es un mapa de clave-valor accesible globalmente para almacenar cualquier objeto Java. Proporciona acceso simple a un estado compartido sin necesidad de instanciar o configurar nada. Solo hay una instancia de ObjectTable y se borra cuando la aplicación se actualiza o termina. Es útil para escenarios donde necesitas hacer datos disponibles a través de múltiples componentes o contextos sin mantener una cadena de referencias.

### Configuración y recuperación de objetos {#setting-and-retrieving-objects}

```java
ObjectTable.put("userInfo", new User("Alice", "admin"));
User user = (User) ObjectTable.get("userInfo");
```

### Comprobación de presencia {#checking-for-presence}

```java
if (ObjectTable.contains("userInfo")) {
  // La clave existe
}
```

### Eliminación de entradas {#removing-entries}

```java
ObjectTable.clear("userInfo");
```

### Tamaño de la tabla {#table-size}

```java
int total = ObjectTable.size();
```

## `StringTable` {#stringtable}

`StringTable` proporciona acceso estático a variables de cadena globales. Es persistente y está limitado a la aplicación actual. Los valores se pueden modificar programáticamente o inyectar a través de la configuración del entorno. Este mecanismo es particularmente útil para almacenar valores de configuración, flags y ajustes que deben ser accesibles en toda la aplicación pero no necesitan llevar datos complejos.

### Obtener y establecer valores de cadena {#getting-and-setting-string-values}

```java
StringTable.put("COMPANY", "Acme Corp");
String company = StringTable.get("COMPANY");
```

### Valores preconfigurados desde la configuración {#pre-configured-values-from-config}

Puedes definir claves en tu archivo [`webforj.conf`](../configuration/properties#configuring-webforjconf):

```
webforj.stringTable = {
  COMPANY: 'Acme Corp'
}
```

Luego accede a ella en el código:

```java
String val = StringTable.get("COMPANY");
```

### Comprobación de presencia {#checking-for-presence-1}

```java
if (StringTable.contains("COMPANY")) {
  // La clave está configurada
}
```

### Limpiar una clave {#clearing-a-key}

```java
StringTable.clear("COMPANY");
```
