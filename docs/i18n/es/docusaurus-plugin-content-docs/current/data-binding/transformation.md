---
sidebar_position: 4
title: Transformation
description: >-
  Convert between UI and model data types in webforJ bindings by implementing
  the Transformer interface and wiring it via useTransformer.
_i18n_hash: 7a8064dc7b603cd86ad965a41216c55c
---
Las transformaciones de datos convierten entre los tipos de datos utilizados en los componentes de la interfaz de usuario y los de su modelo de datos. Esto mantiene compatibles y adecuadamente formateados los tipos de datos al mover datos entre el frontend y el backend de sus aplicaciones.

:::tip
La configuración del transformador es mejor utilizarla cuando el tipo de datos de la propiedad del bean no coincide con el tipo de datos manejado por los componentes de la interfaz de usuario. Si simplemente necesita transformar datos del mismo tipo, configurar [los getters y setters de los bindings](bindings#binding-getters-and-setters) es el enfoque preferido.
:::

## Configurando transformadores {#configuring-transformers}

Puede configurar transformaciones de datos directamente dentro de sus bindings, lo que le permite definir cómo deben transformarse los datos durante el proceso de enlace de datos.

Puede agregar transformadores a un binding utilizando el método `useTransformer` en el `BindingBuilder`. Los transformadores deben implementar la interfaz `Transformer`, que requiere definir métodos para ambas direcciones del flujo de datos: del modelo a la interfaz de usuario y de la interfaz de usuario al modelo.

```java
context.bind(salaryField, "salary")
  .useTransformer(new CurrencyTransformer())
  .add();
```

En el ejemplo anterior, el código configura un `CurrencyTransformer` para manejar conversiones entre el tipo de datos del modelo (por ejemplo, BigDecimal) y la representación de la interfaz de usuario (por ejemplo, una cadena formateada).

:::info
Cada binding está asociado con un solo transformador. Si transformar un valor requiere varios pasos, se recomienda implementar su propio transformador para estos pasos.
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

Este transformador maneja campos de fecha, formateando las fechas cuando se muestran en la interfaz de usuario y analizándolas de nuevo en el modelo.

### Usando transformadores en bindings {#using-transformers-in-bindings}

Una vez que ha definido un transformador, puede aplicarlo en múltiples bindings dentro de su aplicación. Este enfoque es particularmente útil para formatos de datos estándar que necesitan un manejo consistente en diferentes partes de su aplicación.

```java
BindingContext<Employee> context = new BindingContext<>(Employee.class);
context.bind(startDateField, "startDate", String.class)
  .useTransformer(new DateTransformer())
  .add();
```

:::info Especificando el Tipo de Propiedad del Bean

En el método `bind`, especificar el tipo de la propiedad del bean como el tercer parámetro es esencial cuando hay una discrepancia entre el tipo de datos mostrado por el componente de la interfaz de usuario y el tipo de datos utilizado en el modelo. Por ejemplo, si el componente maneja `startDateField` como un `LocalDate` de Java dentro del componente pero se almacena como un `String` en el modelo, definir explícitamente el tipo como `String.class` indica al mecanismo de enlace procesar y convertir con precisión los datos entre los dos tipos diferentes utilizados por el componente y el bean utilizando el transformador y validadores proporcionados.
:::

### Simplificando transformaciones con `Transformer.of` {#simplifying-transforms-with-transformerof}

Es posible simplificar la implementación de tales transformaciones utilizando el método `Transformer.of` proporcionado por el `Transformer`. Este método es azúcar sintáctico y permite escribir un método que maneje transformaciones en línea, en lugar de pasar una clase que implemente la interfaz `Transformer`.

En el siguiente ejemplo, el código maneja una interacción de casilla de verificación dentro de una aplicación de viaje donde los usuarios pueden optar por servicios adicionales como el alquiler de automóviles. El estado de la casilla de verificación `boolean` necesita transformarse en una representación de cadena `"sí"` o `"no"` que utiliza el modelo del backend.

```java
CheckBox carRental = new CheckBox("Alquiler de coche");
BindingContext<Trip> context = new BindingContext<>(Trip.class, true);
context.bind(carRental, "carRental", String.class)
  .useTransformer(
      Transformer.of(
        // convertir el valor del componente a valor del modelo
        bool -> Boolean.TRUE.equals(bool) ? "yes" : "no",
        // convertir el valor del modelo a valor del componente
        str -> str.equals("yes")
      ),

      // en caso de que la transformación falle, mostrar el siguiente
      // mensaje
      "La casilla de verificación debe estar marcada"
  )
  .add();
```

### Mensajes de error dinámicos del transformador <DocChip chip='since' label='25.12' /> {#dynamic-transformer-error-messages}

Por defecto, el mensaje de error mostrado cuando una transformación falla es una cadena estática. En aplicaciones que admiten varios idiomas, puede pasar un `Supplier<String>` para que el mensaje se resuelva cada vez que la transformación falle:

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

El proveedor solo se invoca cuando la transformación arroja una `TransformationException`. Esto significa que el mensaje siempre refleja la configuración regional actual en el momento del fallo.

#### Transformadores conscientes de la localidad {#locale-aware-transformers}

Para transformadores reutilizables que necesitan acceso interno a la configuración regional actual (por ejemplo, para formatear números o fechas de acuerdo con convenciones regionales), implemente la interfaz `LocaleAware`. Cuando la configuración regional cambia a través de `BindingContext.setLocale()`, el contexto propagará automáticamente la nueva configuración regional a los transformadores que implementen esta interfaz.
