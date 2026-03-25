---
sidebar_position: 5
title: Triggers
_i18n_hash: 97f59b66c18e6a2d02174c1ba99f88f1
---
Por defecto, las vinculaciones validan automáticamente los componentes cuando los usuarios modifican sus datos, como al ingresar nuevo texto, marcar una casilla de verificación o seleccionar una nueva opción en un botón de radio. Si prefieres desactivar las validaciones automáticas y solo informar sobre ellas al escribir en el modelo de datos, puedes configurar la vinculación para desactivarlas. Esto te da control sobre cuándo y cómo ocurren las validaciones, lo que te permite gestionar las validaciones según las necesidades específicas de la aplicación o las interacciones del usuario.

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(emailField, "email")
  .useValidator(
    Validator.from(new EmailValidator(), "Mensaje personalizado para dirección de correo electrónico inválida"))
  .autoValidate(false)
  .add();
```

También es posible desactivar las auto-validaciones para todo el contexto.

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.setAutoValidate(false);
```

:::tip Modo de Cambio de Valor
Algunos componentes, como los componentes de campo, implementan la interfaz `ValueChangeModeAware`, lo que te permite controlar cuándo el sistema informa un `ValueChangeEvent`. Por ejemplo, puedes configurar los componentes de campo para informar cambios de valor solo cuando se pierde el foco. Esta configuración reduce la frecuencia de las validaciones, optimizando el rendimiento y mejorando la experiencia del usuario al centrarse en las validaciones en momentos en que el usuario completa una sesión de entrada, en lugar de durante la escritura activa.

```java
 emailField.setValueChangeMode(ValueChangeMode.ON_BLUR);
```
:::

## Revalidación {#revalidation}

Si bien las validaciones normalmente se activan automáticamente durante la escritura de datos, también puedes invocarlas manualmente para verificar el estado de los datos sin intentar escribirlo en el modelo. Este enfoque manual es particularmente útil en escenarios donde deseas habilitar o desactivar características basadas en la validez de los datos del formulario sin realizar una actualización.

Considera un ejemplo clásico de un Selector de Fechas de Viaje, donde un usuario debe seleccionar dos fechas: la fecha de inicio y la fecha de finalización de un viaje. No es válido elegir una fecha de finalización que ocurra antes de la fecha de inicio, o una fecha de inicio que ocurra después de la fecha de finalización. Puedes resolver estas dependencias activando validaciones manualmente:

<Tabs>
<TabItem value="TripBooking" label="TripBooking.java">

```java showLineNumbers
public class TripBooking extends App {
  DateTimeField startDateField = new DateTimeField("Fecha de Inicio");
  DateTimeField endDateField = new DateTimeField("Fecha de Finalización");
  FlexLayout layout = FlexLayout.create(startDateField, endDateField).vertical().build().setStyle("margin", "20px auto")
      .setMaxWidth("400px");

  LocalDateTime startDate;
  LocalDateTime endDate;

  @Override
  public void run() throws WebforjException {
    BindingContext<Trip> context = new BindingContext<>(Trip.class);
    context.bind(startDateField, "startDate")
        .useValidator(Objects::nonNull, "Se requiere una fecha de inicio")
        .useValidator(value -> endDate != null && value.isBefore(endDate),
            "La fecha de inicio debe ser anterior a la fecha de finalización")
        .add();

    context.bind(endDateField, "endDate")
        .useValidator(Objects::nonNull, "Se requiere una fecha de finalización")
        .useValidator(value -> startDate != null && value.isAfter(startDate),
            "La fecha de finalización debe ser posterior a la fecha de inicio")
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
