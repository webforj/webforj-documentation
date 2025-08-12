---
sidebar_position: 5
title: Route Patterns
_i18n_hash: 2f1668e34197bb2f4bb6c5b3ec6e87e5
---
**Los Patrones de Ruta** se utilizan para definir cómo las URL se mapean a vistas específicas, incluyendo segmentos dinámicos y opcionales, expresiones regulares y comodines. Los patrones de ruta permiten al marco de trabajo hacer coincidir URL, extraer parámetros y generar URL dinámicamente. Juegan un papel crítico en la estructuración de la navegación y el renderizado de componentes de una aplicación según la ubicación del navegador.

## Sintaxis de patrones de ruta {#route-pattern-syntax}

Los patrones de ruta en webforJ son altamente flexibles, soportando las siguientes características:

- **Parámetros Nombrados:** Denotados por `:paramName`, son obligatorios a menos que se marquen como opcionales.
- **Parámetros Opcionales:** Denotados por `:paramName?`, pueden omitirse de la URL.
- **Segmentos Comodín:** Representados por `*`, capturan todos los segmentos restantes de la URL.
- **Restricciones de Expresiones Regulares:** Las restricciones pueden agregarse solo a los parámetros nombrados (por ejemplo, `:id<[0-9]+>`).

### Ejemplo de definiciones de patrones de ruta {#example-of-route-pattern-definitions}

```java
@Route("customer/:id<[0-9]+>/named/:name/*")
public class CustomerView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    int id = parameters.getInt("id").orElse(0);
    String name = parameters.getAlpha("name").orElse("Desconocido");
    String extra = parameters.getAlpha("*").orElse("");

    String result =
        "ID de Cliente: " + id + "-" +
        "Nombre: " + name + "-" +
        "*: " + extra;

    console().log(result);
  }
}
```

En este ejemplo:

- `:id<[0-9]+>` captura un ID de cliente numérico.
- `:name` captura un nombre.
- `*` captura cualquier segmento de ruta adicional más allá de `named/:name`.

## Parámetros nombrados {#named-parameters}

Los parámetros nombrados se definen prefijando dos puntos `:` al nombre del parámetro en el patrón. Son obligatorios a menos que se marquen como opcionales. Los parámetros nombrados también pueden tener [restricciones](#regular-expression-constraints) de expresiones regulares para validar los valores.

### Ejemplo: {#example}

```java
@Route("product/:id")
public class ProductView extends Composite<Div> {
  // Lógica del componente aquí
}
```

Este patrón coincide con URL como `/product/123` donde `id` es `123`.

## Parámetros opcionales {#optional-parameters}

Los parámetros opcionales se indican añadiendo un `?` después del nombre del parámetro. Estos segmentos no son obligatorios y pueden omitirse de la URL.

### Ejemplo: {#example-1}

```java
@Route("order/:id?<[0-9]+>")
public class OrderView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.getInt("id").ifPresentOrElse(
      id -> console().log("ID de Pedido: " + id),
      () -> console().log("No se proporcionó ID de Pedido")
    );
  }
}
```

Este patrón coincide tanto con `/order/123` para incluir un valor numérico, como con `/order`, permitiendo la omisión de un valor numérico cuando se ingresa `/order`.

## Restricciones de expresiones regulares {#regular-expression-constraints}

Puedes aplicar restricciones de expresiones regulares a los parámetros añadiéndolos dentro de corchetes angulares `<>`. Esto te permite especificar reglas de coincidencia más estrictas para los parámetros.

### Ejemplo: {#example-2}

```java
@Route("product/:code<[A-Z]{3}-[0-9]{4}>")
public class ProductView extends Composite<FlexLayout> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("code").ifPresentOrElse(
      code -> console().log("Código de producto: " + code),
      () -> console().error("Código de producto no encontrado"));
  }
}
```

Este patrón solo coincide con códigos de producto en el formato `ABC-1234`. Por ejemplo, `/product/XYZ-5678` coincidirá, pero `/product/abc-5678` no lo hará.

## Segmentos comodín {#wildcard-segments}

Los comodines se pueden usar para capturar rutas completas que siguen a un segmento específico de la ruta, pero solo pueden aparecer como el segmento final en el patrón, resolviendo todos los valores subsecuentes en la URL. Para una mejor legibilidad, los segmentos comodín pueden tener nombre. Sin embargo, a diferencia de los parámetros nombrados, los segmentos comodín no pueden tener ninguna restricción.

### Ejemplo: {#example-3}

```java
@Route("files/:pathname*")
public class FileManagerView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("pathname").ifPresentOrElse(
      pathname -> console().log("FileManagerView: " + pathname),
      () -> console().log("FileManagerView: No se proporcionó el parámetro pathname")
    );
  }
}
```

Este patrón coincide con cualquier URL que empiece con `/files` y captura el resto de la ruta como un comodín.

## Prioridad de ruta {#route-priority}

Cuando múltiples rutas coinciden con una URL determinada, el atributo de prioridad de una ruta determina qué ruta se selecciona primero. Esto es especialmente útil cuando dos o más rutas se superponen en sus patrones de ruta y necesitas una forma de controlar cuál tiene precedencia. El atributo de prioridad está disponible en las anotaciones `@Route` y `@RouteAlias`.

### Cómo funciona el sistema de prioridad {#how-the-priority-system-works}

El atributo de prioridad permite al enrutador determinar el orden en el que se evalúan las rutas cuando múltiples rutas podrían coincidir con una URL determinada. Las rutas se ordenan según sus valores de prioridad, siendo las de mayor prioridad (valores numéricos más bajos) las que se coinciden primero. Esto asegura que las rutas más específicas tengan precedencia sobre las más generales.

Si dos rutas comparten la misma prioridad, el enrutador resuelve el conflicto seleccionando la ruta que fue registrada primero. Este mecanismo asegura que se elija la ruta correcta, incluso cuando múltiples rutas se superponen en sus patrones de URL.

:::info Prioridad Predeterminada  
Por defecto, todas las rutas se asignan una prioridad de `10`.  
:::

### Ejemplo: Rutas en conflicto {#example-conflicting-routes}

Considera un escenario donde dos rutas coinciden con patrones de URL similares:

```java
@Route(value = "products/:category", priority = 9)
public class ProductCategoryView extends Composite<Div> implements DidEnterObserver {
  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String category = parameters.get("category").orElse("desconocido");
    console().log("Visualizando categoría: " + category);
  }
}

@Route(value = "products/:category/:productId?<[0-9]+>")
public class ProductView extends Composite<Div> implements DidEnterObserver {
  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String productId = parameters.get("productId").orElse("desconocido");
    console().log("Visualizando producto: " + productId);
  }
}
```

Aquí se muestra cómo el sistema de prioridad ayuda a resolver conflictos:

- **`ProductCategoryView`** coincide con URL como `/products/electronics`.
- **`ProductView`** coincide con URL más específicas como `/products/electronics/123`, donde `123` es el ID del producto.

En este caso, ambas rutas podrían coincidir con la URL `/products/electronics`. Sin embargo, debido a que `ProductCategoryView` tiene una prioridad más alta (prioridad = 9), se emparejará primero cuando no haya `productId` en la URL. Para URL como `/products/electronics/123`, `ProductView` será emparejado debido a la presencia del parámetro `productId`.
