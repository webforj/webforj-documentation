---
sidebar_position: 4
title: Transformation
sidebar_class_name: updated-content
_i18n_hash: 3b1655fdbfa9c303ae1445beee9ee327
---
Las transformaciones de datos convierten entre los tipos de datos utilizados en los componentes de la interfaz de usuario (UI) y aquellos en tu modelo de datos. Esto mantiene los tipos de datos compatibles y apropiadamente formateados al mover datos entre el frontend y el backend de tus aplicaciones.

:::tip
La configuración del transformador se utiliza mejor cuando el tipo de dato de la propiedad del bean no coincide con el tipo de dato manejado por los componentes de la UI. Si solo necesitas transformar datos del mismo tipo, configurar [los getters y setters de los enlaces](bindings#binding-getters-and-setters) es el enfoque preferido.
:::

## Configuración de transformadores {#configuring-transformers}

Puedes configurar transformaciones de datos directamente dentro de tus enlaces, lo que te permite definir cómo se deben transformar los datos durante el proceso de enlace de datos.

Puedes agregar transformadores a un enlace utilizando el método `useTransformer` en el `BindingBuilder`. Los transformadores deben implementar la interfaz `Transformer`, que requiere definir métodos para ambas direcciones del flujo de datos: del modelo a la UI y de la UI al modelo.

```java
context.bind(salaryField, "salary")
    .useTransformer(new CurrencyTransformer())
    .add();
```

En el ejemplo anterior, el código configura un `CurrencyTransformer` para manejar conversiones entre el tipo de dato del modelo (por ejemplo, BigDecimal) y la representación de la UI (por ejemplo, una cadena formateada).

:::info
Cada enlace está asociado con un solo transformador. Si transformar un valor requiere múltiples pasos, se recomienda implementar tu propio transformador para estos pasos.
:::

## Implementación de un transformador {#implementing-a-transformer}

Aquí hay un ejemplo de implementación de un transformador simple que convierte entre un modelo `LocalDate` y una representación `String` de la UI:

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
      throw new TransformationException("Formato de fecha no válido");
    }
  }

  @Override
  public String transformToModel(LocalDate componentValue) {
    try {
      return componentValue.format(formatter);
    } catch (Exception e) {
      throw new TransformationException("Formato de fecha no válido");
    }
  }
}
```

Este transformador maneja campos de fecha, formateando las fechas cuando se muestran en la UI y analizándolas nuevamente en el modelo.

### Uso de transformadores en enlaces {#using-transformers-in-bindings}

Una vez que hayas definido un transformador, puedes aplicarlo en múltiples enlaces dentro de tu aplicación. Este enfoque es particularmente útil para formatos de datos estándar que necesitan un manejo consistente en diferentes partes de tu aplicación.

```java
BindingContext<Employee> context = new BindingContext<>(Employee.class);
context.bind(startDateField, "startDate", String.class)
    .useTransformer(new DateTransformer())
    .add();
```

:::info Especificación del tipo de propiedad del bean

En el método `bind`, especificar el tipo de la propiedad del bean como el tercer parámetro es esencial cuando hay una discrepancia entre el tipo de dato mostrado por el componente de la UI y el tipo de dato utilizado en el modelo. Por ejemplo, si el componente maneja `startDateField` como un `LocalDate` de Java dentro del componente pero se almacena como un `String` en el modelo, definir explícitamente el tipo como `String.class` le indica al mecanismo de enlace procesar y convertir los datos entre los dos tipos diferentes utilizados por el componente y el bean utilizando el transformador y los validadores proporcionados.
:::

### Simplificando transformaciones con `Transformer.of` {#simplifying-transforms-with-transformerof}

Es posible simplificar la implementación de tales transformaciones utilizando el método `Transformer.of` proporcionado por el `Transformer`. Este método es azúcar sintáctica y te permite escribir un método que maneje las transformaciones en línea, en lugar de pasar una clase que implemente la interfaz `Transformer`.

En el siguiente ejemplo, el código maneja una interacción de casilla de verificación dentro de una aplicación de viajes donde los usuarios pueden optar por servicios adicionales como el alquiler de coches. El estado de la casilla de verificación `boolean` necesita ser transformado en una representación de cadena `"yes"` o `"no"` que utiliza el modelo del backend.

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

### Mensajes de error de transformador dinámico <DocChip chip='since' label='25.12' /> {#dynamic-transformer-error-messages}

Por defecto, el mensaje de error mostrado cuando una transformación falla es una cadena estática. En aplicaciones que admiten múltiples idiomas, puedes pasar un `Supplier<String>` en su lugar, de modo que el mensaje se resuelva cada vez que la transformación falla:

```java {7}
context.bind(quantityField, "quantity", Integer.class)
    .useTransformer(
        Transformer.of(
            str -> Integer.parseInt(str),
            val -> String.valueOf(val)
        ),
        () -> t("validation.quantity.invalid")
    )
    .add();
```

El proveedor se invoca solo cuando la transformación lanza una `TransformationException`. Esto significa que el mensaje siempre refleja el idioma actual en el momento de la falla.

#### Transformadores conscientes del locale {#locale-aware-transformers}

Para transformadores reutilizables que necesitan acceso al locale actual internamente (por ejemplo, para formatear números o fechas según convenciones regionales), implementa la interfaz `LocaleAware`. Cuando el locale cambia a través de `BindingContext.setLocale()`, el contexto propaga automáticamente el nuevo locale a los transformadores que implementan esta interfaz.
