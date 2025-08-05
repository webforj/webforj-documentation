---
sidebar_position: 4
title: Transformation
_i18n_hash: fccb434a8897618a0197f9883cd94795
---
Las transformaciones de datos son una característica fundamental, que facilitan la conversión fluida entre los tipos de datos utilizados en los componentes de la interfaz de usuario y los de su modelo de datos. Esta capacidad asegura que los tipos de datos sean compatibles y estén adecuadamente formateados al mover datos entre el frontend y el backend de sus aplicaciones.

:::tip
La configuración del transformador es mejor utilizarla cuando el tipo de dato de la propiedad del bean no coincide con el tipo de dato manejado por los componentes de la interfaz de usuario. Si simplemente necesita transformar datos del mismo tipo, configurar [los getters y setters de las vinculaciones](bindings#binding-getters-and-setters) es el enfoque preferido.
:::

## Configurando transformadores {#configuring-transformers}

Puede configurar transformaciones de datos directamente dentro de sus vinculaciones, lo que le permite definir cómo se deben transformar los datos durante el proceso de vinculación de datos.

Puede agregar transformadores a una vinculación utilizando el método `useTransformer` en el `BindingBuilder`. Los transformadores deben implementar la interfaz `Transformer`, que requiere definir métodos para ambas direcciones del flujo de datos: del modelo a la interfaz de usuario y de la interfaz de usuario al modelo.

```java
context.bind(salaryField, "salary")
    .useTransformer(new CurrencyTransformer())
    .add();
```

En el ejemplo anterior, el código configura un `CurrencyTransformer` para manejar conversiones entre el tipo de dato del modelo (por ejemplo, BigDecimal) y la representación de la interfaz de usuario (por ejemplo, una cadena formateada).

:::info
Cada vinculación está asociada con un único transformador. Si transformar un valor requiere múltiples pasos, se recomienda implementar su propio transformador para estos pasos.
:::

## Implementando un transformador {#implementing-a-transformer}

Aquí hay un ejemplo de implementación de un transformador simple que convierte entre un modelo `LocalDate` y una representación de interfaz de usuario `String`:

```java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.webforj.data.transformation.TransformationException;
import com.webforj.data.transformation.transformer.Transformer;

public class DateTransformer implements Transformer<LocalDate, String> {
  private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  @Override
  public LocalDate transformToComponent(String modelValue) {
    try {
      return LocalDate.parse(modelValue, formatter);
    } catch (Exception e) {
      throw new TransformationException("Formato de fecha inválido");
    }
  }

  @Override
  public String transformToModel(LocalDate componentValue) {
    try {
      return componentValue.format(formatter);
    } catch (Exception e) {
      throw new TransformationException("Formato de fecha inválido");
    }
  }
}
```

Este transformador facilita el manejo de campos de fecha, asegurando que las fechas se formateen correctamente cuando se muestren en la interfaz de usuario y se analicen correctamente de regreso en el modelo.

## Usando transformadores en vinculaciones {#using-transformers-in-bindings}

Una vez que haya definido un transformador, puede aplicarlo a múltiples vinculaciones dentro de su aplicación. Este enfoque es particularmente útil para formatos de datos estándar que necesitan un manejo consistente en diferentes partes de su aplicación.

```java
BindingContext<Employee> context = new BindingContext<>(Employee.class);
context.bind(startDateField, "startDate", String.class)
    .useTransformer(new DateTransformer())
    .add();
```

:::info Especificando el tipo de propiedad del Bean

En el método `bind`, especificar el tipo de la propiedad del bean como el tercer parámetro es esencial cuando hay una discrepancia entre el tipo de dato mostrado por el componente de la interfaz de usuario y el tipo de dato utilizado en el modelo. Por ejemplo, si el componente maneja `startDateField` como un `LocalDate` de Java dentro del componente pero se almacena como un `String` en el modelo, definir explícitamente el tipo como `String.class` asegura que el mecanismo de vinculación procese y convierta los datos entre los dos tipos diferentes utilizados por el componente y el bean utilizando el transformador y los validadores proporcionados.
:::

## Simplificando transformaciones con `Transformer.of` {#simplifying-transforms-with-transformerof}

Es posible simplificar la implementación de tales transformaciones utilizando el método `Transformer.of` proporcionado por el `Transformer`. Este método es azúcar sintáctico y permite escribir un método que maneje transformaciones en línea, en lugar de pasar una clase que implemente la interfaz `Transformer`.

En el siguiente ejemplo, el código maneja una interacción de una casilla de verificación dentro de una aplicación de viajes donde los usuarios pueden optar por servicios adicionales como el alquiler de coches. El estado de la casilla de verificación `boolean` necesita ser transformado en una representación de cadena `"yes"` o `"no"` que utiliza el modelo de backend.

```java
CheckBox carRental = new CheckBox("Alquiler de coches");
BindingContext<Trip> context = new BindingContext<>(Trip.class, true);
context.bind(carRental, "carRental", String.class)
  .useTransformer(
      Transformer.of(
        // convierte el valor del componente al valor del modelo
        bool -> Boolean.TRUE.equals(bool) ? "yes" : "no",
        // convierte el valor del modelo al valor del componente
        str -> str.equals("yes")
      ), 

      // en caso de que la transformación falle, muestre el siguiente
      // mensaje
      "La casilla debe estar marcada"
  )
  .add();
```
