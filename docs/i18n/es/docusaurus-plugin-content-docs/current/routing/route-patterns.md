---
sidebar_position: 5
title: Route Patterns
_i18n_hash: d18952e5072af2c542c1459e3f65d787
---
**Patrones de Ruta** se utilizan para definir cómo las URL se asignan a vistas específicas, incluyendo segmentos dinámicos y opcionales, expresiones regulares y comodines. Los patrones de ruta permiten que el marco coincida con las URL, extraiga parámetros y genere URL de manera dinámica. Juegan un papel crítico en la estructuración de la navegación de una aplicación y la representación de componentes según la ubicación del navegador.

## Sintaxis de patrones de ruta {#route-pattern-syntax}

Los patrones de ruta en webforJ son altamente flexibles, admitiendo las siguientes características:

- **Parámetros Nombrados:** Denotados por `:paramName`, son obligatorios a menos que se marquen como opcionales.
- **Parámetros Opcionales:** Denotados por `:paramName?`, pueden omitirse de la URL.
- **Segmentos Comodín:** Representados por `*`, capturan todos los segmentos restantes de la URL.
- **Restricciones de Expresiones Regulares:** Las restricciones se pueden agregar solo a parámetros nombrados (por ejemplo, `:id<[0-9]+>`).

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

Los parámetros opcionales se indican agregando un `?` después del nombre del parámetro. Estos segmentos no son obligatorios y pueden omitirse de la URL.

### Ejemplo: {#example-1}

```java
@Route("order/:id?<[0-9]+>")
public class OrderView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.getInt("id").ifPresentOrElse(
      id -> console().log("ID de Orden: " + id),
      () -> console().log("No se proporcionó ID de Orden")
    );
  }
}
```

Este patrón coincide tanto con `/order/123` para incluir un valor numérico, como con `/order`, permitiendo la omisión de un valor numérico cuando se ingresa `/order`.

## Restricciones de expresiones regulares {#regular-expression-constraints}

Puedes aplicar restricciones de expresiones regulares a los parámetros agregándolas dentro de corchetes angulares `<>`. Esto te permite especificar reglas de coincidencia más estrictas para los parámetros.

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

Los comodines se pueden utilizar para capturar rutas enteras que siguen a un segmento de ruta específico, pero solo pueden aparecer como el segmento final en el patrón, resolviendo todos los valores subsecuentes en la URL. Para mejor legibilidad, los segmentos comodín pueden tener nombre. Sin embargo, a diferencia de los parámetros nombrados, los segmentos comodín no pueden tener ninguna restricción.

### Ejemplo: {#example-3}

```java
@Route("files/:pathname*")
public class FileManagerView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    parameters.get("pathname").ifPresentOrElse(
      pathname -> console().log("FileManagerView: " + pathname),
      () -> console().log("FileManagerView: No hay parámetro de pathname")
    );
  }
}
```

Este patrón coincide con cualquier URL que comience con `/files` y captura el resto de la ruta como un comodín.

## Prioridad de ruta {#route-priority}

Cuando varias rutas coinciden con una URL dada, el atributo de prioridad de una ruta determina qué ruta se selecciona primero. Esto es especialmente útil cuando dos o más rutas se superponen en sus patrones de ruta, y necesitas una forma de controlar cuál tiene prioridad. El atributo de prioridad está disponible en las anotaciones `@Route` y `@RouteAlias`.

### Cómo funciona el sistema de prioridad {#how-the-priority-system-works}

El atributo de prioridad permite que el enrutador determine el orden en que se evalúan las rutas cuando múltiples rutas podrían coincidir con una URL dada. Las rutas se ordenan en función de sus valores de prioridad, con una prioridad más alta (valores numéricos más bajos) coincidiendo primero. Esto asegura que las rutas más específicas tengan prioridad sobre las más generales.

Si dos rutas comparten la misma prioridad, el enrutador resuelve el conflicto seleccionando la ruta que se registró primero. Este mecanismo asegura que se elija la ruta correcta, incluso cuando múltiples rutas se superponen en sus patrones de URL.

:::info Prioridad Predeterminada  
Por defecto, todas las rutas se asignan una prioridad de `10`.  
:::

### Ejemplo: Rutas en conflicto {#example-conflicting-routes}

Considera un escenario en el que dos rutas coinciden con patrones de URL similares:

```java
@Route(value = "products/:category", priority = 9)
public class ProductCategoryView extends Composite<Div> implements DidEnterObserver {
  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String category = parameters.get("category").orElse("desconocido");
    console().log("Viendo categoría: " + category);
  }
}

@Route(value = "products/:category/:productId?<[0-9]+>")
public class ProductView extends Composite<Div> implements DidEnterObserver {
  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String productId = parameters.get("productId").orElse("desconocido");
    console().log("Viendo producto: " + productId);
  }
}
```

Aquí está cómo el sistema de prioridad ayuda a resolver conflictos:

- **`ProductCategoryView`** coincide con URL como `/products/electronics`.
- **`ProductView`** coincide con URL más específicas como `/products/electronics/123`, donde `123` es el ID del producto.

En este caso, ambas rutas podrían coincidir con la URL `/products/electronics`. Sin embargo, dado que `ProductCategoryView` tiene una prioridad más alta (prioridad = 9), se hará la coincidencia primero cuando no haya `productId` en la URL. Para URL como `/products/electronics/123`, se hará la coincidencia con `ProductView` debido a la presencia del parámetro `productId`.
