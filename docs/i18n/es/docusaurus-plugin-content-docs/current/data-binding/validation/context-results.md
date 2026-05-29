---
sidebar_position: 4
title: Context Results
_i18n_hash: b86bc425ad8c1537e99a44fa34a93b3a
---
Cuando escribes datos de la interfaz de usuario al modelo, el método `write` del `BindingContext` activa las validaciones. Los resultados de la validación determinan si los datos son aceptables.

## Procesamiento de resultados de validación {#processing-validation-results}

Puedes procesar los resultados de validación para proporcionar retroalimentación al usuario. Si una validación falla, puedes prevenir la actualización de datos en el modelo y mostrar mensajes de error asociados con cada validación fallida.

```java
ValidationResult result = context.write(hero);
if (!result.isValid()) {
  displayErrors(result.getMessages());
} else {
  proceedWithUpdate();
}
```

<!-- vale off -->
## Estado de Validación del Contexto {#context-validation-state}
<!-- vale on -->

Cada vez que el contexto valida los componentes, genera un `BindingContextValidateEvent`. Este evento entrega el `ValidationResult` para todos los enlaces que han cambiado simultáneamente. Puedes usar estos resultados para activar acciones y responder apropiadamente, como habilitar o deshabilitar el botón de enviar según la validez general del formulario.

```java
BindingContext<User> context = new BindingContext<>(User.class);

// Escuchar el BindingContextValidateEvent que se dispara en cada interacción del usuario.
context.addValidateListener(event -> {
  submit.setEnabled(event.isValid());
});
```

## Violación de auto enfoque {#auto-focus-violation}

Al tratar con formularios que requieren validación en múltiples campos, enfocar automáticamente el primer campo con un error puede mejorar significativamente la experiencia del usuario. Esta función ayuda a los usuarios a identificar y corregir errores de inmediato, agilizando el proceso de completado del formulario.

El `BindingContext` simplifica el proceso de configuración del auto-enfoque en el primer componente con un error de validación. Usando el método `setAutoFocusFirstViolation`, puedes habilitar esta función con un código mínimo, asegurando que la interfaz de usuario sea más intuitiva y responda mejor a los errores de entrada.

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.setAutoFocusFirstViolation(true);
```

:::info Enfoque Consciente
Esta función solo funciona para los componentes que implementan la preocupación `FocusAcceptorAware`.
:::
