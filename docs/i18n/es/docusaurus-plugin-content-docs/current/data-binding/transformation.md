---
sidebar_position: 4
title: Transformation
_i18n_hash: fe3acbd17750ab0092cbc3609b967969
---
Las transformaciones de datos son una característica fundamental, facilitando la conversión fluida entre los tipos de datos utilizados en los componentes de la interfaz de usuario y los de tu modelo de datos. Esta capacidad asegura que los tipos de datos sean compatibles y estén formateados adecuadamente al mover datos entre el frontend y el backend de tus aplicaciones.

:::tip
La configuración del transformador es mejor utilizarla cuando el tipo de datos de la propiedad del bean no coincide con el tipo de datos que manejan los componentes de la interfaz de usuario. Si simplemente necesitas transformar datos del mismo tipo, configurar [los getters y setters de los bindings](bindings#binding-getters-and-setters) es el enfoque preferido.
:::

## Configuración de transformadores {#configuring-transformers}

Puedes configurar las transformaciones de datos directamente dentro de tus bindings, lo que te permite definir cómo se deben transformar los datos durante el proceso de enlace de datos.

Puedes agregar transformadores a un binding utilizando el método `useTransformer` en el `BindingBuilder`. Los transformadores deben implementar la interfaz `Transformer`, que requiere definir métodos para ambas direcciones del flujo de datos: del modelo a la interfaz de usuario y de la interfaz de usuario al modelo.

```java
context.bind(salaryField, "salary")
    .useTransformer(new CurrencyTransformer())
    .add();
```

En el ejemplo anterior, el código configura un `CurrencyTransformer` para manejar conversiones entre el tipo de datos del modelo (por ejemplo, BigDecimal) y la representación en la interfaz de usuario (por ejemplo, una cadena formateada).

:::info
Cada binding está asociado con un único transformador. Si transformar un valor requiere múltiples pasos, se recomienda implementar tu propio transformador para estos pasos.
:::

## Implementación de un transformador {#implementing-a-transformer}

Aquí tienes un ejemplo de implementación de un transformador simple que convierte entre un modelo `LocalDate` y una representación de interfaz de usuario `String`:

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

Este transformador facilita el manejo de campos de fecha, asegurando que las fechas estén correctamente formateadas cuando se muestran en la interfaz de usuario y que se analicen correctamente de nuevo en el modelo.

## Uso de transformadores en bindings {#using-transformers-in-bindings}

Una vez que hayas definido un transformador, puedes aplicarlo en múltiples bindings dentro de tu aplicación. Este enfoque es especialmente útil para formatos de datos estándar que necesitan un manejo consistente en diferentes partes de tu aplicación.

```java
BindingContext<Employee> context = new BindingContext<>(Employee.class);
context.bind(startDateField, "startDate", String.class)
    .useTransformer(new DateTransformer())
    .add();
```

:::info Especificando el Tipo de Propiedad del Bean

En el método `bind`, especificar el tipo de la propiedad del bean como el tercer parámetro es esencial cuando hay una discrepancia entre el tipo de datos mostrado por el componente de la interfaz de usuario y el tipo de datos utilizado en el modelo. Por ejemplo, si el componente maneja `startDateField` como un `LocalDate` de Java dentro del componente pero se almacena como un `String` en el modelo, definir explícitamente el tipo como `String.class` asegura que el mecanismo de binding procese y convierta correctamente los datos entre los dos tipos diferentes utilizados por el componente y el bean utilizando el transformador y los validadores proporcionados.
:::

## Simplificando transformaciones con `Transformer.of` {#simplifying-transforms-with-transformerof}

Es posible simplificar la implementación de tales transformaciones utilizando el método `Transformer.of` proporcionado por el `Transformer`. Este método es una azúcar sintáctica y permite escribir un método que maneja transformaciones en línea, en lugar de pasar una clase que implementa la interfaz `Transformer`. 

En el siguiente ejemplo, el código maneja una interacción con una casilla de verificación dentro de una aplicación de viajes donde los usuarios pueden optar por servicios adicionales como alquiler de coches. El estado de la casilla de verificación `boolean` necesita ser transformado en una representación de cadena `"yes"` o `"no"` que utiliza el modelo del backend.

```java
CheckBox carRental = new CheckBox("Alquiler de coches");
BindingContext<Trip> context = new BindingContext<>(Trip.class, true);
context.bind(carRental, "carRental", String.class)
  .useTransformer(
      Transformer.of(
        // convertir valor del componente a valor del modelo
        bool -> Boolean.TRUE.equals(bool) ? "yes" : "no",
        // convertir valor del modelo a valor del componente
        str -> str.equals("yes")
      ), 

      // en caso de que la transformación falle, mostrar el siguiente
      // mensaje
      "La casilla de verificación debe estar marcada"
  )
  .add();
```
