---
sidebar_position: 55
title: Asynchronous Updates
_i18n_hash: ead192e1c1a415742cb0446e2d5c314c
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(java.lang.Runnable)" top='true'/>

La API `Environment.runLater()` proporciona un mecanismo para actualizar de manera segura la interfaz de usuario desde hilos en segundo plano en aplicaciones webforJ. Esta característica experimental permite operaciones asíncronas mientras se mantiene la seguridad de los hilos para las modificaciones de la interfaz de usuario.

:::warning API experimental
Esta API está marcada como experimental desde la versión 25.02 y puede cambiar en futuras versiones. La firma de la API, el comportamiento y las características de rendimiento están sujetos a modificación.
:::

## Comprendiendo el modelo de hilos {#understanding-the-thread-model}

webforJ impone un modelo de hilo estricto donde todas las operaciones de la interfaz de usuario deben ocurrir en el hilo de `Environment`. Esta restricción existe porque:

1. **Restricciones de la API webforJ**: La API subyacente de webforJ está vinculada al hilo que creó la sesión.
2. **Afinidad de hilo de componentes**: Los componentes de la interfaz de usuario mantienen un estado que no es seguro para múltiples hilos.
3. **Despacho de eventos**: Todos los eventos de la interfaz de usuario se procesan secuencialmente en un solo hilo.

Este modelo de un solo hilo previene condiciones de carrera y mantiene un estado consistente para todos los componentes de la interfaz de usuario, pero crea desafíos al integrar tareas de computación asincrónica de larga duración.

## API `RunLater` {#runlater-api}

La API `Environment.runLater()` proporciona dos métodos para programar actualizaciones de la interfaz de usuario:

```java title="Environment.java"
// Programa una tarea sin valor de retorno
public static PendingResult<Void> runLater(Runnable task)

// Programa una tarea que devuelve un valor
public static <T> PendingResult<T> runLater(Supplier<T> supplier)
```

Ambos métodos devuelven un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> que rastrea la finalización de la tarea y proporciona acceso al resultado o a cualquier excepción que haya ocurrido.

## Herencia de contexto de hilo {#thread-context-inheritance}

La herencia automática de contexto es una característica crítica de `Environment.runLater()`. Cuando un hilo que se ejecuta en un `Environment` crea hilos secundarios, esos hijos heredan automáticamente la capacidad de usar `runLater()`.

### Cómo funciona la herencia {#how-inheritance-works}

Cualquier hilo creado desde dentro de un hilo de `Environment` tiene acceso automáticamente a ese `Environment`. Esta herencia ocurre automáticamente, por lo que no necesitas pasar ningún contexto ni configurar nada.

```java
@Route
public class DataView extends Composite<Div> {
  private final ExecutorService executor = Executors.newCachedThreadPool();
  
  public DataView() {
    // Este hilo tiene contexto de Environment
    
    // Los hilos secundarios heredan el contexto automáticamente
    executor.submit(() -> {
      String data = fetchRemoteData();
      
      // Se puede usar runLater porque el contexto fue heredado
      Environment.runLater(() -> {
        dataLabel.setText(data);
        loadingSpinner.setVisible(false);
      });
    });
  }
}
```

### Hilos sin contexto {#threads-without-context}

Los hilos creados fuera del contexto de `Environment` no pueden usar `runLater()` y lanzarán una `IllegalStateException`:

```java
// Inicializador estático - sin contexto de Environment
static {
  new Thread(() -> {
    Environment.runLater(() -> {});  // Lanza IllegalStateException
  }).start();
}

// Hilos de temporizador del sistema - sin contexto de Environment  
Timer timer = new Timer();
timer.schedule(new TimerTask() {
  public void run() {
    Environment.runLater(() -> {});  // Lanza IllegalStateException
  }
}, 1000);

// Hilos de bibliotecas externas - sin contexto de Environment
httpClient.sendAsync(request, responseHandler)
  .thenAccept(response -> {
    Environment.runLater(() -> {});  // Lanza IllegalStateException
  });
```

## Comportamiento de ejecución {#execution-behavior}

El comportamiento de ejecución de `runLater()` depende de qué hilo lo llama:

### Desde el hilo de UI {#from-the-ui-thread}

Cuando se llama desde el propio hilo de `Environment`, las tareas se ejecutan **sincrónicamente e inmediatamente**:

```java
button.onClick(e -> {
  System.out.println("Antes: " + Thread.currentThread().getName());
  
  PendingResult<String> result = Environment.runLater(() -> {
    System.out.println("Dentro: " + Thread.currentThread().getName());
    return "completado";
  });
  
  System.out.println("Después: " + result.isDone());  // true
});
```

Con este comportamiento sincrónico, las actualizaciones de la interfaz de usuario de los controladores de eventos se aplican inmediatamente y no incurren en ningún costo innecesario de encolado.

### Desde hilos en segundo plano {#from-background-threads}

Cuando se llama desde un hilo en segundo plano, las tareas se **encolan para ejecución asíncrona**:

```java
@Override
public void onDidCreate() {
  CompletableFuture.runAsync(() -> {
    // Esto se ejecuta en un hilo de ForkJoinPool
    System.out.println("Fondo: " + Thread.currentThread().getName());
    
    PendingResult<Void> result = Environment.runLater(() -> {
      // Esto se ejecuta en el hilo de Environment
      System.out.println("Actualización de UI: " + Thread.currentThread().getName());
      statusLabel.setText("Procesamiento completo");
    });
    
    // result.isDone() sería falso aquí
    // La tarea está en cola y se ejecutará de manera asíncrona
  });
}
```

webforJ procesa tareas enviadas desde hilos en segundo plano en un **estricto orden FIFO**, preservando la secuencia de operaciones incluso cuando se envían desde múltiples hilos simultáneamente. Con esta garantía de orden, las actualizaciones de la interfaz de usuario se aplican en el mismo orden en que fueron enviadas. Así que si el hilo A envía la tarea 1, y luego el hilo B envía la tarea 2, la tarea 1 siempre se ejecutará antes que la tarea 2 en el hilo de UI. Procesar tareas en orden FIFO previene inconsistencias en la interfaz de usuario.

## Cancelación de tareas {#task-cancellation}

El <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> devuelto por `Environment.runLater()` soporta cancelación, lo que permite evitar que tareas en cola se ejecuten. Al cancelar las tareas pendientes, puedes evitar pérdidas de memoria y prevenir que operaciones de larga duración actualicen la interfaz de usuario después de que ya no sean necesarias.

### Cancelación básica {#basic-cancellation}

```java
PendingResult<Void> result = Environment.runLater(() -> {
  updateUI();
});

// Cancelar si no se ha ejecutado aún
if (!result.isDone()) {
  result.cancel();
}
```

### Gestión de múltiples actualizaciones {#managing-multiple-updates}

Cuando se realizan operaciones de larga duración con frecuentes actualizaciones de la interfaz de usuario, rastrea todos los resultados pendientes:

```java
public class LongRunningTask {
  private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();
  private volatile boolean isCancelled = false;
  
  public void startTask() {
    CompletableFuture.runAsync(() -> {
      for (int i = 0; i <= 100; i++) {
        if (isCancelled) return;
        
        final int progress = i;
        PendingResult<Void> update = Environment.runLater(() -> {
          progressBar.setValue(progress);
        });
        
        // Rastrear para una posible cancelación
        pendingUpdates.add(update);
        
        Thread.sleep(100);
      }
    });
  }
  
  public void cancelTask() {
    isCancelled = true;
    
    // Cancelar todas las actualizaciones de UI pendientes
    for (PendingResult<?> pending : pendingUpdates) {
      if (!pending.isDone()) {
        pending.cancel();
      }
    }
    pendingUpdates.clear();
  }
}
```

### Gestión del ciclo de vida de componentes {#component-lifecycle-management}

Cuando los componentes son destruidos (por ejemplo, durante la navegación), cancela todas las actualizaciones pendientes para evitar pérdidas de memoria:

```java
@Route
public class CleanupView extends Composite<Div> {
  private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();
  
  @Override
  protected void onDestroy() {
    super.onDestroy();
    
    // Cancelar todas las actualizaciones pendientes para prevenir pérdidas de memoria
    for (PendingResult<?> pending : pendingUpdates) {
      if (!pending.isDone()) {
        pending.cancel();
      }
    }
    pendingUpdates.clear();
  }
}
```

## Consideraciones de diseño {#design-considerations}

1. **Requisitos de contexto**: Los hilos deben haber heredado un contexto de `Environment`. Los hilos de bibliotecas externas, temporizadores del sistema y inicializadores estáticos no pueden usar esta API.

2. **Prevención de pérdidas de memoria**: Siempre rastrea y cancela objetos `PendingResult` en los métodos del ciclo de vida del componente. Las lambdas en cola capturan referencias a componentes de la interfaz de usuario, impidiendo la recolección de basura si no son canceladas.

3. **Ejecución FIFO**: Todas las tareas se ejecutan en estricto orden FIFO independientemente de la importancia. No hay un sistema de prioridad.

4. **Limitaciones de cancelación**: La cancelación solo previene la ejecución de tareas en cola. Las tareas que ya se están ejecutando completarán normalmente.

## Estudio de caso completo: `LongTaskView` {#complete-case-study-longtaskview}

Lo siguiente es una implementación completa y lista para producción que demuestra todas las mejores prácticas para actualizaciones asíncronas de la interfaz de usuario:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={159}>
{`
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Usa un ejecutor de hilo único para prevenir agotamiento de recursos
  // Para producción, considera usar un grupo de hilos compartido a nivel de aplicación
  private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
  });

  // Rastrea la tarea actual y las actualizaciones de UI pendientes
  private CompletableFuture<Void> currentTask = null;
  private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
  private volatile boolean isCancelled = false;

  // Componentes de la interfaz de usuario
  private final FlexLayout self = getBoundComponent();
  private H2 titleLabel = new H2("Demostración de Actualizaciones de UI en Segundo Plano");
  private Paragraph descriptionPara = new Paragraph(
      "Esta demostración muestra cómo Environment.runLater() permite actualizaciones seguras de la UI desde hilos en segundo plano. " +
          "Haz clic en 'Iniciar Tarea Larga' para ejecutar un cálculo de 10 segundos en segundo plano que actualiza el progreso de la UI. " +
          "El botón 'Probar UI' prueba que la interfaz de usuario permanece receptiva durante la operación en segundo plano.");
  private TextField statusField = new TextField("Estado");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Resultado");
  private Button startButton = new Button("Iniciar Tarea Larga");
  private Button cancelButton = new Button("Cancelar Tarea");
  private Button testButton = new Button("Probar UI - ¡Haz Clic En Mí!");
  private Paragraph footerPara = new Paragraph(
      "Nota: La tarea se puede cancelar en cualquier momento, demostrando una limpieza adecuada tanto del " +
          "hilo en segundo plano como de las actualizaciones de la UI en cola.");
  private Toast globalToast = new Toast("", 3000, Theme.GRAY);
  private AtomicInteger clickCount = new AtomicInteger(0);

  public LongTaskView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(400);
    self.setStyle("margin", "1em auto");

    // Configura los campos
    statusField.setReadOnly(true);
    statusField.setValue("Listo para comenzar");
    statusField.setLabel("Estado");

    // Configura la barra de progreso
    progressBar.setMin(0);
    progressBar.setMax(100);
    progressBar.setValue(0);
    progressBar.setAnimated(true);
    progressBar.setStriped(true);
    progressBar.setText("Progreso: {{x}}%");
    progressBar.setTheme(Theme.PRIMARY);

    resultField.setReadOnly(true);
    resultField.setValue("");
    resultField.setLabel("Resultado");

    // Configura los botones
    startButton.setTheme(ButtonTheme.PRIMARY);
    startButton.onClick(e -> startLongTask());

    cancelButton.setTheme(ButtonTheme.DANGER);
    cancelButton.setEnabled(false);
    cancelButton.onClick(e -> cancelTask());

    testButton.onClick(e -> {
      int count = clickCount.incrementAndGet();
      showToast("Clic #" + count + " - ¡La UI es receptiva!", Theme.GRAY);
    });

    // Añade componentes
    self.add(titleLabel, descriptionPara, statusField, progressBar, resultField,
        startButton, cancelButton, testButton, footerPara);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Cancela cualquier tarea en ejecución y actualizaciones de UI pendientes
    cancelTask();

    // Limpia la referencia a la tarea
    currentTask = null;

    // Apaga el ejecutor de forma controlada
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Iniciando tarea en segundo plano...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Reinicia la bandera de cancelado y limpia actualizaciones pendientes anteriores
    isCancelled = false;
    pendingUIUpdates.clear();

    // Inicia tarea en segundo plano con ejecutor explícito
    // Nota: cancel(true) interrumpirá el hilo, haciendo que Thread.sleep() lance
    // InterruptedException
    currentTask = CompletableFuture.runAsync(() -> {
      double result = 0;

      // Simula tarea larga con 100 pasos
      for (int i = 0; i <= 100; i++) {
        // Verifica si se canceló
        if (isCancelled) {
          PendingResult<Void> cancelUpdate = Environment.runLater(() -> {
            statusField.setValue("¡Tarea cancelada!");
            progressBar.setValue(0);
            resultField.setValue("");
            startButton.setEnabled(true);
            cancelButton.setEnabled(false);
            showToast("La tarea fue cancelada", Theme.GRAY);
          });
          pendingUIUpdates.add(cancelUpdate);
          return;
        }

        try {
          Thread.sleep(100); // 10 segundos en total
        } catch (InterruptedException e) {
          // El hilo fue interrumpido - salir inmediatamente
          Thread.currentThread().interrupt(); // Restaurar estado interrumpido
          return;
        }

        // Realiza algún cálculo (determinista para la demostración)
        // Produce valores entre 0 y 1
        result += Math.sin(i) * 0.5 + 0.5;

        // Actualiza el progreso desde el hilo en segundo plano
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(() -> {
          progressBar.setValue(progress);
          statusField.setValue("Procesando... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // Actualización final con el resultado (este código solo se alcanza si la tarea se completa sin
      // cancelación)
      if (!isCancelled) {
        final double finalResult = result;
        PendingResult<Void> finalUpdate = Environment.runLater(() -> {
          statusField.setValue("¡Tarea completada!");
          resultField.setValue("Resultado: " + String.format("%.2f", finalResult));
          startButton.setEnabled(true);
          cancelButton.setEnabled(false);
          showToast("¡Tarea en segundo plano finalizada!", Theme.SUCCESS);
        });
        pendingUIUpdates.add(finalUpdate);
      }
    }, executor);
  }

  private void cancelTask() {
    if (currentTask != null && !currentTask.isDone()) {
      // Establecer la bandera de cancelado
      isCancelled = true;

      // Cancelar la tarea principal (interrumpe el hilo)
      currentTask.cancel(true);

      // Cancelar todas las actualizaciones de UI pendientes
      for (PendingResult<?> pending : pendingUIUpdates) {
        if (!pending.isDone()) {
          pending.cancel();
        }
      }

      if (!statusField.isDestroyed() && !cancelButton.isDestroyed()) {
        statusField.setValue("Cancelando tarea...");
        cancelButton.setEnabled(false);

        showToast("Cancelación solicitada", Theme.GRAY);
      }
    }
  }

  private void showToast(String message, Theme theme) {
    if (!globalToast.isDestroyed()) {
      globalToast.setText(message);
      globalToast.setTheme(theme);
      globalToast.open();
    }
  }
}
`}
</ExpandableCode>

<div class="videos-container" style={{maxWidth: '400px', margin: '0 auto'}}>
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/webforj-long-tasks.mp4" type="video/mp4"/>
  </video>
</div>

<!-- vale on -->

### Análisis del estudio de caso {#case-study-analysis}

Esta implementación demuestra varios patrones críticos:

#### 1. Gestión del grupo de hilos {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
  Thread t = new Thread(r, "LongTaskView-Worker");
  t.setDaemon(true);
  return t;
});
```
- Utiliza un **ejecutor de hilo único** para prevenir el agotamiento de recursos.
- Crea **hilos demonio** que no impedirán el apagado de la JVM.

#### 2. Rastreo de actualizaciones pendientes {#2-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Cada llamada a `Environment.runLater()` es rastreada para permitir:
- Cancelación cuando el usuario hace clic en cancelar.
- Prevención de pérdidas de memoria en `onDestroy()`.
- Limpieza adecuada durante el ciclo de vida del componente.

#### 3. Cancelación cooperativa {#3-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
El hilo en segundo plano verifica esta bandera en cada iteración, lo que permite:
- Respuesta inmediata a la cancelación.
- Salida limpia del bucle.
- Prevención de actualizaciones adicionales de la IU.

#### 4. Gestión del ciclo de vida {#4-lifecycle-management}
```java
@Override
protected void onDestroy() {
  super.onDestroy();
  cancelTask();  // Reutiliza la lógica de cancelación.
  currentTask = null;
  executor.shutdown();
}
```
Crítico para prevenir pérdidas de memoria al:
- Cancelar todas las actualizaciones de IU pendientes.
- Interrumpir hilos en ejecución.
- Apagar el ejecutor.

#### 5. Pruebas de receptividad de la UI {#5-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
  int count = clickCount.incrementAndGet();
  showToast("Clic #" + count + " - ¡La UI es receptiva!", Theme.GRAY);
});
```
Demuestra que el hilo de la interfaz de usuario permanece receptivo durante las operaciones en segundo plano.
