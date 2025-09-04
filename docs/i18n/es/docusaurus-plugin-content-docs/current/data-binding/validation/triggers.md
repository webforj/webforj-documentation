---
sidebar_position: 5
title: Triggers
_i18n_hash: b158a924f67b7141be94d56b9be8bba3
---
Por defecto, las vinculaciones vuelven a validar automáticamente los componentes cuando los usuarios modifican sus datos, como al ingresar nuevo texto, marcar una casilla de verificación o seleccionar una nueva opción en un botón de radio. Si prefieres desactivar las validaciones automáticas y solo reportarlas al escribir en el modelo de datos, puedes configurar la vinculación para desactivarlas. Esto te brinda control sobre cuándo y cómo ocurren las validaciones, permitiendo gestionar las validaciones según las necesidades específicas de la aplicación o las interacciones del usuario.

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(emailField, "email")
    .useValidator(
        Validator.from(new EmailValidator(), "Mensaje personalizado para dirección de correo electrónico no válida"))
    .autoValidate(false)
    .add();
```

También es posible desactivar las auto-validaciones para todo el contexto.

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.setAutoValidate(false);
```

:::tip Modo de Cambio de Valor
Algunos componentes, como los campos, implementan la interfaz `ValueChangeModeAware`, que te permite controlar cuándo el sistema reporta un `ValueChangeEvent`. Por ejemplo, puedes configurar los campos para que reporten cambios de valor solo al perder el foco. Esta configuración reduce la frecuencia de las validaciones, optimizando el rendimiento y mejorando la experiencia del usuario al centrarse en las validaciones en momentos en que el usuario completa una sesión de entrada, en lugar de durante la escritura activa.

```java
 emailField.setValueChangeMode(ValueChangeMode.ON_BLUR);
```
:::

## Revalidación {#revalidation}

Si bien las validaciones generalmente se activan automáticamente durante la escritura de datos, también puedes invocarlas manualmente para verificar el estado de los datos sin intentar escribirlos en el modelo. Este enfoque manual es particularmente útil en escenarios donde deseas habilitar o desactivar características en función de la validez de los datos del formulario sin realizar una actualización.

Considera un ejemplo clásico de un Selector de Fechas de Viaje, donde un usuario debe seleccionar dos fechas: la fecha de inicio y la fecha de fin de un viaje. No es válido elegir una fecha de fin que ocurra antes de la fecha de inicio, o una fecha de inicio que ocurra después de la fecha de fin. Puedes resolver estas dependencias activando validaciones manualmente:

<Tabs>
<TabItem value="TripBooking" label="TripBooking.java">

```java showLineNumbers
public class TripBooking extends App {
  DateTimeField startDateField = new DateTimeField("Fecha de Inicio");
  DateTimeField endDateField = new DateTimeField("Fecha de Fin");
  FlexLayout layout = FlexLayout.create(startDateField, endDateField).vertical().build().setStyle("margin", "20px auto")
      .setMaxWidth("400px");

  LocalDateTime startDate;
  LocalDateTime endDate;

  @Override
  public void run() throws WebforjException {
    BindingContext<Trip> context = new BindingContext<>(Trip.class);
    context.bind(startDateField, "startDate")
        .useValidator(Objects::nonNull, "Se requiere la fecha de inicio")
        .useValidator(value -> endDate != null && value.isBefore(endDate),
            "La fecha de inicio debe ser anterior a la fecha de fin")
        .add();

    context.bind(endDateField, "endDate")
        .useValidator(Objects::nonNull, "Se requiere la fecha de fin")
        .useValidator(value -> startDate != null && value.isAfter(startDate),
            "La fecha de fin debe ser posterior a la fecha de inicio")
        .add();

    startDateField.setValueChangeMode(ValueChangeMode.ON_BLUR);
    startDateField.addValueChangeListener(event -> {
      startDate = event.getValue();
      context.getBinding("endDate").validate();
    });

    endDateField.setValueChangeMode(ValueChangeMode.ON_BLUR);
    endDateField.addValueChangeListener(event -> {
      endDate = event.getValue();
      context.getBinding("startDate").validate();
    });

    Frame frame = new Frame();
    frame.add(layout);
  }
}
```

</TabItem>
<TabItem value="Trip" label="Trip.java">

```java showLineNumbers
public class Trip {
  private LocalDateTime startDate;
  private LocalDateTime endDate;

  public LocalDateTime getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDateTime startDate) {
    this.startDate = startDate;
  }

  public LocalDateTime getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDateTime endDate) {
    this.endDate = endDate;
  }
}
```

</TabItem>
</Tabs>
