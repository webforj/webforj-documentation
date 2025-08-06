---
sidebar_position: 6
title: Query Parameters
_i18n_hash: c3b57611c46f7cd4fa9946ff704213cc
---
Los parámetros de consulta permiten pasar datos adicionales a través de URL, utilizando el formato `?key1=value1&key2=value2`. Mientras que los parámetros de ruta se utilizan para pasar datos requeridos dentro de la ruta de la URL, los parámetros de consulta proporcionan un mecanismo flexible para pasar datos opcionales o adicionales. Son especialmente útiles al filtrar contenido, ordenar o manejar múltiples valores para la misma clave.

## Resumen de parámetros de consulta {#query-parameters-overview}

Los parámetros de consulta en webforJ siguen la convención típica de URL: pares clave-valor separados por `=` y concatenados con `&`. Se agregan a la URL después de un `?` y proporcionan una forma flexible de pasar datos opcionales, como preferencias de filtrado u ordenamiento.

Por ejemplo:

```
/products?category=electronics&sort=price
```

## Recuperación de parámetros de consulta {#retrieving-query-parameters}

Los parámetros de consulta se acceden a través del objeto `ParametersBag`. Para recuperar parámetros de consulta, utiliza el método `getQueryParameters()` del objeto `Location`.

Aquí te mostramos cómo puedes recuperar parámetros de consulta de una URL en una vista:

```java
@Route(value = "products")
public class ProductView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    ParametersBag queryParameters = event.getLocation().getQueryParameters();

    String category = queryParameters.get("category").orElse("all");
    String sort = queryParameters.get("sort").orElse("default");

    console().log("Categoría: " + category);
    console().log("Ordenar: " + sort);
  }
}
```

En este ejemplo:
- El método `onDidEnter` recupera los parámetros de consulta del objeto `Location` proporcionado por el `DidEnterEvent`.
- El `ParametersBag` te permite recuperar parámetros de consulta específicos usando `get()`, que devuelve un `Optional<String>`. Puedes especificar un valor predeterminado utilizando `orElse()` si el parámetro no está presente.

:::tip Métodos de acceso de `ParametersBag`
El `ParametersBag` proporciona varias variaciones de métodos de acceso para ayudar con el casting del valor de los parámetros de consulta a tipos específicos y filtrarlos. La siguiente es la lista completa de métodos de acceso disponibles:

- **`get(String key)`**: Recupera el valor del parámetro como un `String`.
- **`getAlpha(String key)`**: Devuelve solo caracteres alfabéticos del valor del parámetro.
- **`getAlnum(String key)`**: Devuelve solo caracteres alfanuméricos del valor del parámetro.
- **`getDigits(String key)`**: Devuelve solo los dígitos numéricos del valor del parámetro.
- **`getInt(String key)`**: Analiza y devuelve el valor del parámetro como un `Integer`.
- **`getFloat(String key)`**: Analiza y devuelve el valor del parámetro como un `Float`.
- **`getDouble(String key)`**: Analiza y devuelve el valor del parámetro como un `Double`.
- **`getBoolean(String key)`**: Analiza y devuelve el valor del parámetro como un `Boolean`.

Estos métodos te ayudan a asegurar que los valores estén formateados y convertidos correctamente, evitando la necesidad de análisis o validación manual.
:::

## Manejo de múltiples valores para un parámetro de consulta {#handling-multiple-values-for-a-query-parameter}

A veces, un parámetro de consulta puede tener múltiples valores para la misma clave, como en el siguiente ejemplo:

```
/products?category=electronics,appliances&sort=price
```

El `ParametersBag` proporciona un método para manejar esto al recuperar valores como una lista:

```java
@Route(value = "products")
public class ProductView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    ParametersBag queryParameters = event.getLocation().getQueryParameters();

    List<String> categories = queryParameters.getList("category").orElse(List.of("all"));
    String sort = queryParameters.get("sort").orElse("default");

    console().log("Categorías: " + categories);
    console().log("Ordenar: " + sort);
  }
}
```

En este ejemplo:
- `getList("category")` recupera todos los valores asociados con la clave `category`, devolviéndolos como una lista.

:::tip Delimitador de múltiples valores
Por defecto, el método `getList()` utiliza una coma (`,`) como delimitador. Puedes personalizar el delimitador pasando un carácter diferente o una expresión regular como segundo parámetro al método `getList(String key, String regex)`.
:::

## Casos de uso para parámetros de consulta {#use-cases-for-query-parameters}

- **Filtrado de contenido**: Los parámetros de consulta se utilizan a menudo para aplicar filtros, como categorías o palabras clave de búsqueda.
- **Ordenamiento de datos**: Puedes pasar preferencias de ordenamiento a través de parámetros de consulta, como ordenar por precio, calificación o fecha.
- **Manejo de parámetros opcionales**: Cuando necesitas pasar datos que no son parte de la estructura de ruta requerida, los parámetros de consulta ofrecen flexibilidad.
- **Pasar múltiples valores**: Los parámetros de consulta te permiten enviar múltiples valores para una sola clave, lo cual es útil cuando los usuarios seleccionan múltiples opciones, como categorías de productos o filtros.
