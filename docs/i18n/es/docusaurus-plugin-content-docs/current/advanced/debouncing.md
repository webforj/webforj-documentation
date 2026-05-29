---
sidebar_position: 21
title: Debouncing
slug: debouncing
_i18n_hash: 2096c774627674739fd237aed9a4f79e
---
<DocChip chip='since' label='25.11' />
<JavadocLink type="foundation" location="com/webforj/Debouncer" top='true'/>

El rebote (debouncing) es una técnica que retrasa la ejecución de una acción hasta que ha transcurrido un tiempo específico desde la última llamada. Cada nueva llamada reinicia el temporizador. Esto es útil para escenarios como la búsqueda mientras se escribe, donde deseas esperar a que el usuario deje de escribir antes de ejecutar una consulta de búsqueda.

<ComponentDemo
path='/webforj/debouncer'
files={['src/main/java/com/webforj/samples/views/debouncer/DebouncerView.java']}
height='265px'
/>

## Uso básico {#basic-usage}

La clase `Debouncer` proporciona una forma sencilla de debilitar acciones. Crea un `Debouncer` con un retraso en segundos y luego llama a `run()` con la acción que deseas debilitar:

```java
Debouncer debounce = new Debouncer(0.3f);

textField.onModify(e -> {
  debounce.run(() -> search(textField.getText()));
});
```

En este ejemplo, el método `search()` se llama solo después de que el usuario deja de escribir durante 300 milisegundos. Cada pulsación de tecla reinicia el temporizador a través del evento `onModify`, por lo que escribir rápidamente no activará múltiples búsquedas.

## Cómo funciona {#how-it-works}

Cuando llamas a `run()` con una acción:

1. Si no hay acción pendiente, el `Debouncer` programa la acción para que se ejecute después del retraso.
2. Si ya hay una acción pendiente, la acción anterior se cancela y el temporizador se reinicia con la nueva acción.
3. Una vez que transcurre el retraso sin otra llamada, se ejecuta la acción.

El `Debouncer` se ejecuta en el hilo de la interfaz de usuario utilizando el mecanismo de [`Interval`](/docs/advanced/interval) de webforJ, por lo que no necesitas envolver las actualizaciones de la interfaz de usuario en `Environment.runLater()`.

:::tip Unidades de retraso
El parámetro de retraso utiliza segundos como unidad, no milisegundos. Usa `0.3f` para 300 ms o `1.5f` para 1.5 segundos.
:::

## Controlando la ejecución {#controlling-execution}

Los siguientes métodos se pueden utilizar para manejar de manera más precisa la ejecución y uso del `Debouncer`:

### Cancelando una acción pendiente {#cancelling-a-pending-action}

Usa `cancel()` para detener la ejecución de una acción pendiente:

```java
Debouncer debounce = new Debouncer(1f);

debounce.run(() -> saveDocument());

// El usuario navega lejos antes de que se ejecute el guardado
debounce.cancel();
```

:::tip Cancelando rebotes pendientes
Al igual que con los intervalos, es buena práctica cancelar las acciones debiladas pendientes cuando un componente se destruye. Esto previene fugas de memoria y evita errores de acciones que se ejecutan en componentes destruidos:

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

Usa `flush()` para ejecutar una acción pendiente de inmediato:

```java
Debouncer debounce = new Debouncer(0.5f);

textField.onModify(e -> {
  debounce.run(() -> validateInput(textField.getText()));
});

// Fuerza la validación antes del envío del formulario
submitButton.onClick(e -> {
  debounce.flush();
  if (isValid()) {
    submitForm();
  }
});
```

### Verificando el estado pendiente {#checking-pending-status}

Usa `isPending()` para verificar si hay una acción esperando para ejecutarse:

```java
Debouncer debounce = new Debouncer(0.3f);

if (debounce.isPending()) {
  statusLabel.setText("Procesando...");
}
```

## Rebote a nivel de evento vs `Debouncer` {#event-level-debouncing-vs-debouncer}

webforJ proporciona dos enfoques para rebotes:

| Característica | `Debouncer` | `ElementEventOptions.setDebounce()` |
|----------------|-------------|-------------------------------------|
| Alcance        | Cualquier acción | Solo eventos de elementos |
| Ubicación      | Lado del servidor | Lado del cliente |
| Unidad         | Segundos (float) | Milisegundos (int) |
| Flexibilidad   | Control total con cancel/flush | Automático con evento |

Usa `Debouncer` cuando necesites control programático sobre el rebote, como cancelar o forzar la ejecución de acciones pendientes. Usa `ElementEventOptions` cuando quieras rebotes simples del lado del cliente para eventos de elementos sin viajes adicionales al servidor.

```java
// Usando ElementEventOptions para rebotes del lado del cliente
ElementEventOptions options = new ElementEventOptions();
options.setDebounce(300);

element.addEventListener("input", e -> {
  // Este manejador está reboteado en el cliente
}, options);
```
