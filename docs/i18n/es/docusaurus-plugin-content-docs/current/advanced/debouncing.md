---
sidebar_position: 21
title: Debouncing
slug: debouncing
sidebar_class_name: new-content
_i18n_hash: be654f5efb68050d8632a27166954583
---
<DocChip chip='since' label='25.11' />
<JavadocLink type="foundation" location="com/webforj/Debouncer" top='true'/>

El debouncing es una técnica que retrasa la ejecución de una acción hasta que ha transcurrido un tiempo específico desde la última llamada. Cada nueva llamada reinicia el temporizador. Esto es útil para escenarios como la búsqueda a medida que se escribe, donde quieres esperar hasta que el usuario deja de escribir antes de ejecutar una consulta de búsqueda.

<ComponentDemo
path='/webforj/debouncer?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/debouncer/DebouncerDemoView.java'
height='265px'
/>

## Uso básico {#basic-usage}

La clase `Debouncer` proporciona una manera sencilla de deboncear acciones. Crea un `Debouncer` con un retraso en segundos, luego llama a `run()` con la acción que deseas deboncear:

```java
Debouncer debounce = new Debouncer(0.3f);

textField.onModify(e -> {
  debounce.run(() -> search(textField.getText()));
});
```

En este ejemplo, el método `search()` se llama solo después de que el usuario deja de escribir durante 300 milisegundos. Cada pulsación de tecla reinicia el temporizador a través del evento `onModify`, así que escribir rápidamente no activará múltiples búsquedas.

## Cómo funciona {#how-it-works}

Cuando llamas a `run()` con una acción:

1. Si no hay ninguna acción pendiente, el `Debouncer` programa la acción para ejecutarse después del retraso.
2. Si ya hay una acción pendiente, la acción anterior se cancela y el temporizador se reinicia con la nueva acción.
3. Una vez que transcurre el retraso sin otra llamada, la acción se ejecuta.

El `Debouncer` se ejecuta en el hilo de UI utilizando el mecanismo de [`Interval`](/docs/advanced/interval) de webforJ, así que no necesitas envolver actualizaciones de la UI en `Environment.runLater()`.

:::tip Unidades de retraso
El parámetro de retraso utiliza segundos como unidad, no milisegundos. Usa `0.3f` para 300 ms o `1.5f` para 1.5 segundos.
:::

## Controlando la ejecución {#controlling-execution}

Los siguientes métodos se pueden utilizar para manejar de manera más precisa la ejecución y el uso del `Debouncer`:

### Cancelando una acción pendiente {#cancelling-a-pending-action}

Usa `cancel()` para detener una acción pendiente de ejecutarse:

```java
Debouncer debounce = new Debouncer(1f);

debounce.run(() -> saveDocument());

// El usuario navega lejos antes de que se ejecute el guardado
debounce.cancel();
```

:::tip Cancelando debounces pendientes
Al igual que con los intervalos, es buena práctica cancelar las acciones debonceadas pendientes cuando un componente es destruido. Esto previene fugas de memoria y evita errores de acciones que se ejecutan en componentes destruidos:

```java
public class SearchPanel extends Composite<Div> {
  private final Debouncer debounce = new Debouncer(0.3f);

  @Override
  protected void onDidDestroy() {
    debounce.cancel();
  }
}
```
:::

### Forzando la ejecución inmediata {#forcing-immediate-execution}

Usa `flush()` para ejecutar una acción pendiente inmediatamente:

```java
Debouncer debounce = new Debouncer(0.5f);

textField.onModify(e -> {
  debounce.run(() -> validateInput(textField.getText()));
});

// Forzar validación antes de enviar el formulario
submitButton.onClick(e -> {
  debounce.flush();
  if (isValid()) {
    submitForm();
  }
});
```

### Comprobando el estado pendiente {#checking-pending-status}

Usa `isPending()` para verificar si una acción está esperando para ejecutarse:

```java
Debouncer debounce = new Debouncer(0.3f);

if (debounce.isPending()) {
  statusLabel.setText("Procesando...");
}
```

## Debouncing a nivel de evento vs `Debouncer` {#event-level-debouncing-vs-debouncer}

webforJ proporciona dos enfoques para el debouncing:

| Característica | `Debouncer` | `ElementEventOptions.setDebounce()` |
|----------------|-------------|-------------------------------------|
| Alcance        | Cualquier acción | Solo eventos de elemento |
| Ubicación      | Lado del servidor | Lado del cliente |
| Unidad         | Segundos (float) | Milisegundos (int) |
| Flexibilidad   | Control total con cancelación/vaciado | Automático con evento |

Usa `Debouncer` cuando necesites control programático sobre el debouncing, como cancelar o vaciar acciones pendientes. Usa `ElementEventOptions` cuando desees un debouncing simple del lado del cliente para eventos de elementos sin viajes adicionales al servidor.

```java
// Usando ElementEventOptions para debouncing del lado del cliente
ElementEventOptions options = new ElementEventOptions();
options.setDebounce(300);

element.addEventListener("input", e -> {
  // Este manejador está debonceado en el cliente
}, options);
```
