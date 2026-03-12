---
sidebar_position: 55
title: Asynchronous Updates
_i18n_hash: 9ea7ae8b53ce19e2fee19e72929c732e
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(java.lang.Runnable)" top='true'/>

La API `Environment.runLater()` proporciona un mecanismo para actualizar de manera segura la interfaz de usuario desde hilos de fondo en aplicaciones webforJ. Esta característica experimental permite operaciones asincrónicas mientras mantiene la seguridad de los hilos para las modificaciones de la interfaz de usuario.

:::warning API Experimental
Esta API está marcada como experimental desde la versión 25.02 y puede cambiar en futuras versiones. La firma de la API, el comportamiento y las características de rendimiento están sujetas a modificación.
:::

## Comprendiendo el modelo de hilos {#understanding-the-thread-model}

webforJ impone un modelo de hilos estricto donde todas las operaciones de la interfaz de usuario deben ocurrir en el hilo de `Environment`. Esta restricción existe porque:

1. **Restricciones de la API de webforJ**: La API subyacente de webforJ está ligada al hilo que creó la sesión.
2. **Afinidad de los hilos de los componentes**: Los componentes de la interfaz de usuario mantienen un estado que no es seguro para los hilos.
3. **Despatch de eventos**: Todos los eventos de la interfaz de usuario se procesan de manera secuencial en un solo hilo.

Este modelo de un solo hilo previene condiciones de carrera y mantiene un estado consistente para todos los componentes de la interfaz de usuario, pero crea desafíos al integrarse con tareas de computación asincrónicas y de larga duración.

## API `RunLater` {#runlater-api}

La API `Environment.runLater()` proporciona dos métodos para programar actualizaciones de la interfaz de usuario:

```java title="Environment.java"
// Programa una tarea sin valor de retorno
public static PendingResult<Void> runLater(Runnable task)

// Programa una tarea que devuelve un valor
public static <T> PendingResult<T> runLater(Supplier<T> supplier)
```

Ambos métodos devuelven un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> que rastrea la finalización de la tarea y proporciona acceso al resultado o cualquier excepción que haya ocurrido.

## Herencia del contexto del hilo {#thread-context-inheritance}

La herencia automática del contexto es una característica crítica de `Environment.runLater()`. Cuando un hilo que se ejecuta en un `Environment` crea hilos secundarios, esos hijos heredan automáticamente la capacidad de usar `runLater()`.

### Cómo funciona la herencia {#how-inheritance-works}

Cualquier hilo creado desde un hilo `Environment` tiene acceso automáticamente a ese `Environment`. Esta herencia ocurre automáticamente, por lo que no necesitas pasar ningún contexto ni configurar nada.

```java
@Route
public class DataView extends Composite<Div> {
    private final ExecutorService executor = Executors.newCachedThreadPool();

    public DataView() {
        // Este hilo tiene contexto de Environment

        // Los hilos secundarios heredan el contexto automáticamente
        executor.submit(() -> {
            String data = fetchRemoteData();

            // Puede usar runLater porque el contexto fue heredado
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

### Desde el hilo de la interfaz de usuario {#from-the-ui-thread}

Cuando se llama desde el hilo de `Environment` mismo, las tareas se ejecutan **sincrónicamente e inmediatamente**:

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

Con este comportamiento sincrónico, las actualizaciones de la interfaz de usuario desde los controladores de eventos se aplican de inmediato y no incurren en gastos innecesarios de espera en la cola.

### Desde hilos de fondo {#from-background-threads}

Cuando se llama desde un hilo de fondo, las tareas se **enquistan para ejecución asincrónica**:

```java
@Override
public void onDidCreate() {
    CompletableFuture.runAsync(() -> {
        // Esto se ejecuta en el hilo ForkJoinPool
        System.out.println("De fondo: " + Thread.currentThread().getName());

        PendingResult<Void> result = Environment.runLater(() -> {
            // Esto se ejecuta en el hilo de Environment
            System.out.println("Actualización de UI: " + Thread.currentThread().getName());
            statusLabel.setText("Procesamiento completado");
        });

        // result.isDone() sería falso aquí
        // La tarea está en la cola y se ejecutará de forma asincrónica
    });
}
```

webforJ procesa las tareas enviadas desde hilos de fondo en **estricto orden FIFO**, preservando la secuencia de operaciones incluso cuando se envían desde múltiples hilos de forma concurrente. Con esta garantía de orden, las actualizaciones de la interfaz de usuario se aplican en el orden exacto en que se enviaron. Por lo tanto, si el hilo A envía la tarea 1 y luego el hilo B envía la tarea 2, la tarea 1 siempre se ejecutará antes que la tarea 2 en el hilo de la interfaz de usuario. Procesar tareas en orden FIFO previene inconsistencias en la interfaz de usuario.

## Cancelación de tareas {#task-cancellation}

El <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> devuelto por `Environment.runLater()` admite cancelación, lo que te permite evitar que las tareas en cola se ejecuten. Al cancelar tareas pendientes, puedes evitar pérdidas de memoria y prevenir que operaciones de larga duración actualicen la interfaz de usuario después de que ya no sean necesarias.

### Cancelación básica {#basic-cancellation}

```java
PendingResult<Void> result = Environment.runLater(() -> {
    updateUI();
});

// Cancelar si aún no se ha ejecutado
if (!result.isDone()) {
    result.cancel();
}
```

### Gestión de múltiples actualizaciones {#managing-multiple-updates}

Cuando se realizan operaciones de larga duración con actualizaciones frecuentes de la interfaz de usuario, rastrea todos los resultados pendientes:

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

### Gestión del ciclo de vida de los componentes {#component-lifecycle-management}

Cuando los componentes son destruidos (por ejemplo, durante la navegación), cancela todas las actualizaciones pendientes para prevenir pérdidas de memoria:

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

1. **Requisito de contexto**: Los hilos deben tener un contexto de `Environment` heredado. Los hilos de bibliotecas externas, temporizadores del sistema y inicializadores estáticos no pueden usar esta API.

2. **Prevención de pérdidas de memoria**: Siempre rastrea y cancela objetos `PendingResult` en los métodos del ciclo de vida de los componentes. Las lambdas en cola capturan referencias a componentes de la interfaz de usuario, evitando la recolección de basura si no se cancelan.

3. **Ejecución FIFO**: Todas las tareas se ejecutan en estricto orden FIFO independientemente de su importancia. No hay un sistema de prioridades.

4. **Limitaciones de cancelación**: La cancelación solo previene la ejecución de tareas en cola. Las tareas que ya se están ejecutando completarán su ejecución normalmente.

## Estudio de caso completo: `LongTaskView` {#complete-case-study-longtaskview}

Lo siguiente es una implementación completa, lista para producción, que demuestra todas las mejores prácticas para actualizaciones asíncronas de la interfaz de usuario:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={159}>
{`
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Usa un ejecutor de un solo hilo para prevenir el agotamiento de recursos
  // Para producción, considera usar un pool de hilos compartido a nivel de aplicación
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
  private H2 titleLabel = new H2("Demostración de actualizaciones de UI en segundo plano");
  private Paragraph descriptionPara = new Paragraph(
      "Esta demostración muestra cómo Environment.runLater() permite actualizaciones seguras de la UI desde hilos de fondo. " +
          "Haz clic en 'Iniciar Tarea Larga' para ejecutar una computación de fondo de 10 segundos que actualiza el progreso de la UI. " +
          "El botón 'Probar UI' demuestra que la UI sigue siendo receptiva durante la operación en segundo plano.");
  private TextField statusField = new TextField("Estado");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Resultado");
  private Button startButton = new Button("Iniciar Tarea Larga");
  private Button cancelButton = new Button("Cancelar Tarea");
  private Button testButton = new Button("Probar UI - ¡Haz clic en mí!");
  private Paragraph footerPara = new Paragraph(
      "Nota: La tarea puede cancelarse en cualquier momento, demostrando la limpieza adecuada tanto del " +
          "hilo de fondo como de las actualizaciones de UI en cola.");
  private Toast globalToast = new Toast("", 3000, Theme.GRAY);
  private AtomicInteger clickCount = new AtomicInteger(0);

  public LongTaskView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(400);
    self.setStyle("margin", "1em auto");

    // Configurar campos
    statusField.setReadOnly(true);
    statusField.setValue("Listo para comenzar");
    statusField.setLabel("Estado");

    // Configurar barra de progreso
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

    // Configurar botones
    startButton.setTheme(ButtonTheme.PRIMARY);
    startButton.onClick(e -> startLongTask());

    cancelButton.setTheme(ButtonTheme.DANGER);
    cancelButton.setEnabled(false);
    cancelButton.onClick(e -> cancelTask());

    testButton.onClick(e -> {
      int count = clickCount.incrementAndGet();
      showToast("Clic #" + count + " - ¡La UI es receptiva!", Theme.GRAY);
    });

    // Agregar componentes
    self.add(titleLabel, descriptionPara, statusField, progressBar, resultField,
        startButton, cancelButton, testButton, footerPara);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Cancelar cualquier tarea en ejecución y actualizaciones de UI pendientes
    cancelTask();

    // Limpiar referencia de tarea
    currentTask = null;

    // Apagar el ejecutor de instancia de manera segura
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Iniciando tarea en segundo plano...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Restablecer la bandera de cancelación y limpiar las actualizaciones pendientes anteriores
    isCancelled = false;
    pendingUIUpdates.clear();

    // Iniciar tarea de fondo con ejecutor explícito
    // Nota: cancel(true) interrumpirá el hilo, lo que hará que Thread.sleep() lance
    // InterruptedException
    currentTask = CompletableFuture.runAsync(() -> {
      double result = 0;

      // Simular tarea larga con 100 pasos
      for (int i = 0; i <= 100; i++) {
        // Verificar si fue cancelada
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
          Thread.currentThread().interrupt(); // Restaurar estado de interrupción
          return;
        }

        // Realizar algún cálculo (determinístico para la demostración)
        // Produce valores entre 0 y 1
        result += Math.sin(i) * 0.5 + 0.5;

        // Actualizar el progreso desde el hilo de fondo
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(() -> {
          progressBar.setValue(progress);
          statusField.setValue("Procesando... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // Actualización final con resultado (este código solo se alcanza si la tarea se completó sin
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
      // Establecer la bandera de cancelación
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
}`}
</ExpandableCode>

<div class="videos-container" style={{maxWidth: '400px', margin: '0 auto'}}>
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/webforj-long-tasks.mp4" type="video/mp4"/>
  </video>
</div>

<!-- vale on -->

### Análisis del estudio de caso {#case-study-analysis}

Esta implementación demuestra varios patrones críticos:

#### 1. Gestión del pool de hilos {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
});
```
- Utiliza un **ejecutor de un solo hilo** para prevenir el agotamiento de recursos.
- Crea **hilos de daemon** que no evitarán el apagado de la JVM.

#### 2. Rastrear actualizaciones pendientes {#2-tracking-pending-updates}
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
El hilo en segundo plano verifica esta bandera en cada iteración, permitiendo:
- Respuesta inmediata a la cancelación.
- Salida limpia del bucle.
- Prevención de futuras actualizaciones de UI.

#### 4. Gestión del ciclo de vida {#4-lifecycle-management}
```java
@Override
protected void onDestroy() {
    super.onDestroy();
    cancelTask();  // Reutiliza la lógica de cancelación
    currentTask = null;
    executor.shutdown();
}
```
Crítico para prevenir pérdidas de memoria mediante:
- Cancelación de todas las actualizaciones de UI pendientes.
- Interrupción de hilos en ejecución.
- Apagar el ejecutor.

#### 5. Prueba de la receptividad de la UI {#5-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
    int count = clickCount.incrementAndGet();
    showToast("Clic #" + count + " - ¡La UI es receptiva!", Theme.GRAY);
});
```
Demuestra que el hilo de la interfaz de usuario sigue siendo receptivo durante operaciones en segundo plano.
