---
sidebar_position: 4
title: Context Results
_i18n_hash: 15fc4551d1ed2f2b5e35785975e66946
---
Cuando escribes datos de la interfaz de usuario al modelo, el método `write` del `BindingContext` activa las validaciones. Los resultados de la validación determinan si los datos son aceptables.

## Procesamiento de resultados de validación {#processing-validation-results}

Puedes procesar los resultados de la validación para brindar retroalimentación al usuario. Si una validación falla, puedes evitar la actualización de datos en el modelo y mostrar mensajes de error asociados con cada validación fallida.

```java
ValidationResult result = context.write(hero);
if (!result.isValid()) {
    displayErrors(result.getMessages());
} else {
    proceedWithUpdate();
}
```

<!-- vale off -->
## Estado de validación del contexto {#context-validation-state}
<!-- vale on -->

Siempre que el contexto valide los componentes, se activa un `BindingContextValidateEvent`. Este evento entrega el `ValidationResult` para todos los enlaces que han cambiado simultáneamente. Puedes usar estos resultados para activar acciones y responder adecuadamente, como habilitar o deshabilitar el botón de envío según la validez general del formulario.

```java
BindingContext<User> context = new BindingContext<>(User.class);

// Escucha el BindingContextValidateEvent que se dispara en cada interacción del usuario.
context.addValidateListener(event -> {
    submit.setEnabled(event.isValid());
});
```

## Violación de autoenfoque {#auto-focus-violation}

Al tratar con formularios que requieren validación en múltiples campos, enfocar automáticamente el primer campo con un error puede mejorar significativamente la experiencia del usuario. Esta característica ayuda a los usuarios a identificar y corregir errores de inmediato, simplificando el proceso de completado del formulario.

El `BindingContext` simplifica el proceso de configuración de autoenfoque en el primer componente con un error de validación. Al usar el método `setAutoFocusFirstViolation`, puedes habilitar esta función con un código mínimo, asegurando que la interfaz de usuario se vuelva más intuitiva y receptiva a los errores de entrada.

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.setAutoFocusFirstViolation(true);
```

:::info Consciente del enfoque
Esta función solo funciona para los componentes que implementan la preocupación `FocusAcceptorAware`.
:::
