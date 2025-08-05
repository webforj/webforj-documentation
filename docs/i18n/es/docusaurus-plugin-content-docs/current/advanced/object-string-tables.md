---
title: Object and String Tables
sidebar_position: 35
_i18n_hash: a20240ac42fa56a5a7044aaeb969faa7
---
La `ObjectTable` y la `StringTable` proporcionan acceso estático a datos compartidos en un entorno webforJ. Ambas son accesibles desde cualquier lugar en tu aplicación y cumplen diferentes propósitos:

- `ObjectTable`: Para almacenar y recuperar objetos Java en toda tu aplicación.
- `StringTable`: Para trabajar con pares clave-valor de cadenas persistentes, a menudo utilizadas para datos de configuración o de estilo de entorno.

Estas tablas están disponibles a nivel de entorno y no requieren gestión de instancias.

## `ObjectTable` {#objecttable}

`ObjectTable` es un mapa clave-valor globalmente accesible para almacenar cualquier objeto Java. Proporciona acceso simple al estado compartido sin necesidad de instanciar o configurar nada. Solo hay una instancia de ObjectTable y se borra cuando la aplicación se actualiza o termina. Es útil para escenarios en los que necesitas hacer que los datos estén disponibles en múltiples componentes o contextos sin mantener una cadena de referencias.

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

## `StringTable` {#stringtable}

`StringTable` proporciona acceso estático a variables de cadena globales. Es persistente y está limitado a la aplicación actual. Los valores se pueden modificar programáticamente o inyectar a través de la configuración del entorno. Este mecanismo es particularmente útil para almacenar valores de configuración, banderas y ajustes que deben ser accesibles en toda la aplicación, pero que no necesitan llevar datos complejos.

### Obtener y establecer valores de cadena {#getting-and-setting-string-values}

```java
StringTable.put("COMPANY", "Acme Corp");
String company = StringTable.get("COMPANY");
```

### Valores preconfigurados desde config {#pre-configured-values-from-config}

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
