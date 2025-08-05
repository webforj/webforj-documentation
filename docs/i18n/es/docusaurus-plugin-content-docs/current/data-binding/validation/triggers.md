---
sidebar_position: 5
title: Triggers
_i18n_hash: a52300a9683a08701c4e1f1f6150dd9f
---
Por defecto, las bindings validan automáticamente los componentes cuando los usuarios modifican sus datos, como al ingresar nuevo texto, marcar una casilla de verificación o seleccionar una nueva opción en un botón de opción. Si prefieres desactivar las validaciones automáticas y solo reportarlas al escribir en el modelo de datos, puedes configurar la binding para desactivarlas. Esto te da control sobre cuándo y cómo ocurren las validaciones, permitiéndote gestionar las validaciones según las necesidades específicas de la aplicación o las interacciones del usuario.

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
Algunos componentes, como los componentes de campo, implementan la interfaz `ValueChangeModeAware`, que te permite controlar cuándo el sistema reporta un `ValueChangeEvent`. Por ejemplo, puedes configurar los componentes de campo para que reporten cambios de valor solo al perder el foco. Esta configuración reduce la frecuencia de las validaciones, optimizando el rendimiento y mejorando la experiencia del usuario al centrar las validaciones en los momentos en que el usuario completa una sesión de entrada, en lugar de durante la escritura activa.

```java
 emailField.setValueChangeMode(ValueChangeMode.ON_BLUR);
```
:::

## Revalidación {#revalidation}

Si bien las validaciones generalmente se activan automáticamente durante la escritura de datos, también puedes invocarlas manualmente para verificar el estado de los datos sin intentar escribirlos en el modelo. Este enfoque manual es particularmente útil en escenarios donde deseas habilitar o desactivar funciones basadas en la validez de los datos del formulario sin realizar una actualización.

Considera un ejemplo clásico de un Selector de Fechas de Viaje, donde un usuario debe seleccionar dos fechas: la fecha de inicio y la fecha de finalización de un viaje. No es válido elegir una fecha de finalización que ocurran antes de la fecha de inicio, o una fecha de inicio que ocurra después de la fecha de finalización. Puedes resolver estas dependencias activando las validaciones manualmente:

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
        .useValidator(Objects::nonNull, "La fecha de inicio es obligatoria")
        .useValidator(value -> endDate != null && value.isBefore(endDate),
            "La fecha de inicio debe ser anterior a la fecha de finalización")
        .add();

    context.bind(endDateField, "endDate")
        .useValidator(Objects::nonNull, "La fecha de finalización es obligatoria")
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
