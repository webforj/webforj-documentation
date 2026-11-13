---
sidebar_class_name: experimental-content
sidebar_position: 55
title: Actualizaciones asíncronas
description: >-
  Run background work off the UI thread and push updates back to webforJ
  components safely with Environment.runLater and PendingResult.
_i18n_hash: 96865999eae550e8c20172458695ee01
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(com.webforj.Environment,java.lang.Runnable)" top='true'/>

La API `Environment.runLater()` proporciona un mecanismo para actualizar la interfaz de usuario de forma segura desde hilos en segundo plano en aplicaciones webforJ. Esta función en versión preliminar permite realizar operaciones asíncronas y, al mismo tiempo, mantener la seguridad de los hilos al modificar la interfaz de usuario.

<ExperimentalWarning />

<AISkillTip skill="webforj-handling-timers-and-async" />

## Comprender el modelo de hilos {#understanding-the-thread-model}

webforJ impone un modelo de hilos estricto, según el cual todas las operaciones de la interfaz de usuario deben ejecutarse en el hilo de `Environment`. Esta restricción existe porque:

1. **Restricciones de la API de webforJ**: la API subyacente de webforJ se vincula al hilo que creó la sesión
2. **Afinidad de hilo de los componentes**: los componentes de la interfaz de usuario mantienen un estado que no es seguro para los hilos
3. **Despacho de eventos**: todos los eventos de la interfaz de usuario se procesan secuencialmente en un solo hilo

Este modelo de un solo hilo evita las condiciones de carrera y mantiene un estado coherente para todos los componentes de la interfaz de usuario, pero plantea dificultades al integrarse con tareas de cálculo asíncronas y de larga duración.

Cada sesión de usuario tiene su propio `Environment` y su propio hilo de `Environment`. Un hilo en segundo plano no pertenece a ninguna sesión, por lo que hay que indicarle qué `Environment` debe actualizar.

## API de `RunLater` {#runlater-api}

La API `Environment.runLater()` proporciona dos métodos para programar actualizaciones de la interfaz de usuario. Ambos reciben el `Environment` de destino como primer argumento:

```java title="Environment.java"
// Schedule a task with no return value
public static PendingResult<Void> runLater(Environment target, Runnable task)

// Schedule a task that returns a value
public static <T> PendingResult<T> runLater(Environment target, Supplier<T> supplier)
```

Ambos métodos devuelven un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> que realiza el seguimiento de la finalización de la tarea y permite acceder al resultado o a las excepciones que se hayan producido.

:::warning[Variantes sin un destino]
Las variantes `runLater(Runnable)` y `runLater(Supplier)` deducen el destino a partir del hilo que creó el hilo que realiza la llamada. En un grupo de hilos compartido por varios usuarios, todas las tareas se ejecutan en el `Environment` del usuario cuya llamada creó ese hilo del grupo. Estas variantes están obsoletas desde la versión 26.10 y está previsto eliminarlas en la próxima versión principal. Pasa siempre el destino.
:::

## Pasar el destino {#passing-the-target}

`Environment.getCurrent()` devuelve el `Environment` del hilo actual. En el hilo de `Environment`, que es donde se ejecutan los constructores, los controladores de eventos y los métodos del ciclo de vida, devuelve el `Environment` de la sesión. En un hilo en segundo plano, devuelve `null`.

Lee el destino allí donde esté disponible, antes de que comience el trabajo en segundo plano, y pásalo al trabajo:

```java
@Route
public class DataView extends Composite<Div> {
  private final ExecutorService executor = Executors.newCachedThreadPool();

  public DataView() {
    // The constructor runs on the Environment thread
    Environment env = Environment.getCurrent();

    executor.submit(() -> {
      String data = fetchRemoteData();

      Environment.runLater(env, () -> {
        dataLabel.setText(data);
        loadingSpinner.setVisible(false);
      });
    });
  }
}
```

Como el destino se pasa como argumento, la llamada funciona desde cualquier hilo: uno creado por la vista, un hilo `@Async` de Spring, el `ForkJoinPool` común, un hilo de temporizador o un hilo de devolución de llamada de un cliente HTTP.

### Leer el destino desde el hilo equivocado {#reading-the-target-on-the-wrong-thread}

El único error que hay que evitar es leer `Environment.getCurrent()` dentro del trabajo en segundo plano. En ese contexto devuelve `null`, y `runLater()` lanza una `NullPointerException`:

```java
executor.submit(() -> {
  String data = fetchRemoteData();

  // Wrong: getCurrent() returns null on a background thread
  Environment.runLater(Environment.getCurrent(), () -> dataLabel.setText(data));
});
```

Lo mismo se aplica a los inicializadores estáticos, los hilos de `java.util.Timer` y las devoluciones de llamada de bibliotecas externas. Ninguno de ellos tiene un `Environment`, por lo que el destino debe obtenerse de un campo, un parámetro o una variable local capturada cuyo valor se haya establecido en el hilo de `Environment`.

## Comportamiento de ejecución {#execution-behavior}

El comportamiento de ejecución de `runLater()` depende del hilo desde el que se llama:

### Desde el hilo de la interfaz de usuario {#from-the-ui-thread}

Cuando se llama desde el propio hilo de `Environment` del destino, las tareas se ejecutan **de forma síncrona e inmediata**:

```java
button.onClick(e -> {
  Environment env = Environment.getCurrent();
  System.out.println("Before: " + Thread.currentThread().getName());

  PendingResult<String> result = Environment.runLater(env, () -> {
    System.out.println("Inside: " + Thread.currentThread().getName());
    return "completed";
  });

  System.out.println("After: " + result.isDone());  // true
});
```

Con este comportamiento síncrono, las actualizaciones de la interfaz de usuario desde los controladores de eventos se aplican de inmediato y no generan una sobrecarga innecesaria de colas.

### Desde hilos en segundo plano {#from-background-threads}

Cuando se llama desde cualquier otro hilo, las tareas se **ponen en cola para su ejecución asíncrona**:

```java
@Override
public void onDidCreate() {
  Environment env = Environment.getCurrent();

  CompletableFuture.runAsync(() -> {
    // This runs on a ForkJoinPool thread
    System.out.println("Background: " + Thread.currentThread().getName());

    PendingResult<Void> result = Environment.runLater(env, () -> {
      // This runs on the Environment thread
      System.out.println("UI Update: " + Thread.currentThread().getName());
      statusLabel.setText("Processing complete");
    });

    // result.isDone() would be false here
    // The task is queued and will execute asynchronously
  });
}
```

webforJ procesa las tareas enviadas desde hilos en segundo plano en **estricto orden FIFO**, conservando la secuencia de operaciones incluso cuando se envían simultáneamente desde varios hilos. Esta garantía de orden asegura que las actualizaciones de la interfaz de usuario se apliquen exactamente en el orden en que se enviaron. Por ejemplo, si el hilo A envía la tarea 1 y después el hilo B envía la tarea 2, la tarea 1 siempre se ejecutará antes que la tarea 2 en el hilo de la interfaz de usuario. Procesar las tareas en orden FIFO evita incoherencias en la interfaz de usuario.

## Cancelación de tareas {#task-cancellation}

El <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> que devuelve `Environment.runLater()` admite la cancelación, lo que permite impedir la ejecución de tareas en cola. Al cancelar las tareas pendientes, puedes evitar pérdidas de memoria e impedir que las operaciones de larga duración actualicen la interfaz de usuario cuando ya no sea necesario.

### Cancelación básica {#basic-cancellation}

```java
PendingResult<Void> result = Environment.runLater(env, () -> {
  updateUI();
});

// Cancel if not yet executed
if (!result.isDone()) {
  result.cancel();
}
```

### Administrar varias actualizaciones {#managing-multiple-updates}

Al realizar operaciones de larga duración con actualizaciones frecuentes de la interfaz de usuario, realiza un seguimiento de todos los resultados pendientes:

```java
public class LongRunningTask {
  private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();
  private volatile boolean isCancelled = false;

  public void startTask() {
    Environment env = Environment.getCurrent();

    CompletableFuture.runAsync(() -> {
      for (int i = 0; i <= 100; i++) {
        if (isCancelled) return;

        final int progress = i;
        PendingResult<Void> update = Environment.runLater(env, () -> {
          progressBar.setValue(progress);
        });

        // Track for potential cancellation
        pendingUpdates.add(update);

        Thread.sleep(100);
      }
    });
  }

  public void cancelTask() {
    isCancelled = true;

    // Cancel all pending UI updates
    for (PendingResult<?> pending : pendingUpdates) {
      if (!pending.isDone()) {
        pending.cancel();
      }
    }
    pendingUpdates.clear();
  }
}
```

### Administración del ciclo de vida de los componentes {#component-lifecycle-management}

Cuando se destruyen los componentes (por ejemplo, durante la navegación), cancela todas las actualizaciones pendientes para evitar pérdidas de memoria:

```java
@Route
public class CleanupView extends Composite<Div> {
  private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Cancel all pending updates to prevent memory leaks
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

1. **Requisito del destino**: el `Environment` de destino se obtiene con `Environment.getCurrent()` en el hilo de `Environment` y se pasa al trabajo en segundo plano. Si se lee en un hilo en segundo plano, devuelve `null`.

2. **Prevención de pérdidas de memoria**: realiza siempre un seguimiento de los objetos `PendingResult` y cancélalos en los métodos del ciclo de vida de los componentes. Las lambdas en cola capturan referencias a componentes de la interfaz de usuario e impiden que se recolecten como basura si no se cancelan.

3. **Ejecución FIFO**: todas las tareas se ejecutan en estricto orden FIFO, independientemente de su importancia. No hay un sistema de prioridades.

4. **Limitaciones de la cancelación**: la cancelación solo impide la ejecución de tareas en cola. Las tareas que ya se están ejecutando se completarán normalmente.

## Caso práctico completo: `LongTaskView` {#complete-case-study-longtaskview}

A continuación se presenta una implementación completa y lista para producción que muestra las prácticas recomendadas para las actualizaciones asíncronas de la interfaz de usuario:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={161}>

```java
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Use a single thread executor to prevent resource exhaustion
  // For production, consider using a shared application-wide thread pool
  private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
  });

  // Track the current task and pending UI updates
  private CompletableFuture<Void> currentTask = null;
  private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
  private volatile boolean isCancelled = false;

  // UI components
  private final FlexLayout self = getBoundComponent();
  private H2 titleLabel = new H2("Background UI Updates Demo");
  private Paragraph descriptionPara = new Paragraph(
      "This demo shows how Environment.runLater() enables safe UI updates from background threads. " +
          "Click 'Start Long Task' to run a 10-second background computation that updates the UI progress. " +
          "The 'Test UI' button proves the UI remains responsive during the background operation.");
  private TextField statusField = new TextField("Status");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Result");
  private Button startButton = new Button("Start Long Task");
  private Button cancelButton = new Button("Cancel Task");
  private Button testButton = new Button("Test UI - Click Me!");
  private Paragraph footerPara = new Paragraph(
      "Note: The task can be cancelled at any time, demonstrating proper cleanup of both the " +
          "background thread and queued UI updates.");
  private Toast globalToast = new Toast("", 3000, Theme.GRAY);
  private AtomicInteger clickCount = new AtomicInteger(0);

  public LongTaskView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(400);
    self.setStyle("margin", "1em auto");

    // Configure fields
    statusField.setReadOnly(true);
    statusField.setValue("Ready to start");
    statusField.setLabel("Status");

    // Configure progress bar
    progressBar.setMin(0);
    progressBar.setMax(100);
    progressBar.setValue(0);
    progressBar.setAnimated(true);
    progressBar.setStriped(true);
    progressBar.setText("Progress: {{x}}%");
    progressBar.setTheme(Theme.PRIMARY);

    resultField.setReadOnly(true);
    resultField.setValue("");
    resultField.setLabel("Result");

    // Configure buttons
    startButton.setTheme(ButtonTheme.PRIMARY);
    startButton.onClick(e -> startLongTask());

    cancelButton.setTheme(ButtonTheme.DANGER);
    cancelButton.setEnabled(false);
    cancelButton.onClick(e -> cancelTask());

    testButton.onClick(e -> {
      int count = clickCount.incrementAndGet();
      showToast("Click #" + count + " - UI is responsive!", Theme.GRAY);
    });

    // Add components
    self.add(titleLabel, descriptionPara, statusField, progressBar, resultField,
        startButton, cancelButton, testButton, footerPara);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Cancel any running task and pending UI updates
    cancelTask();

    // Clear task reference
    currentTask = null;

    // Shutdown the instance executor gracefully
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Starting background task...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Reset cancelled flag and clear previous pending updates
    isCancelled = false;
    pendingUIUpdates.clear();

    // Read the target Environment on the UI thread, before the background task starts
    Environment env = Environment.getCurrent();

    // Start background task with explicit executor
    // Note: cancel(true) will interrupt the thread, causing Thread.sleep() to throw
    // InterruptedException
    currentTask = CompletableFuture.runAsync(() -> {
      double result = 0;

      // Simulate long task with 100 steps
      for (int i = 0; i <= 100; i++) {
        // Check if cancelled
        if (isCancelled) {
          PendingResult<Void> cancelUpdate = Environment.runLater(env, () -> {
            statusField.setValue("Task cancelled!");
            progressBar.setValue(0);
            resultField.setValue("");
            startButton.setEnabled(true);
            cancelButton.setEnabled(false);
            showToast("Task was cancelled", Theme.GRAY);
          });
          pendingUIUpdates.add(cancelUpdate);
          return;
        }

        try {
          Thread.sleep(100); // 10 seconds total
        } catch (InterruptedException e) {
          // Thread was interrupted - exit immediately
          Thread.currentThread().interrupt(); // Restore interrupted status
          return;
        }

        // Do some calculation (deterministic for demo)
        // Produces values between 0 and 1
        result += Math.sin(i) * 0.5 + 0.5;

        // Update progress from background thread
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(env, () -> {
          progressBar.setValue(progress);
          statusField.setValue("Processing... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // Final update with result (this code is only reached if the task completed without
      // cancellation)
      if (!isCancelled) {
        final double finalResult = result;
        PendingResult<Void> finalUpdate = Environment.runLater(env, () -> {
          statusField.setValue("Task completed!");
          resultField.setValue("Result: " + String.format("%.2f", finalResult));
          startButton.setEnabled(true);
          cancelButton.setEnabled(false);
          showToast("Background task finished!", Theme.SUCCESS);
        });
        pendingUIUpdates.add(finalUpdate);
      }
    }, executor);
  }

  private void cancelTask() {
    if (currentTask != null && !currentTask.isDone()) {
      // Set the cancelled flag
      isCancelled = true;

      // Cancel the main task (interrupts the thread)
      currentTask.cancel(true);

      // Cancel all pending UI updates
      for (PendingResult<?> pending : pendingUIUpdates) {
        if (!pending.isDone()) {
          pending.cancel();
        }
      }

      if (!statusField.isDestroyed() && !cancelButton.isDestroyed()) {
        statusField.setValue("Cancelling task...");
        cancelButton.setEnabled(false);

        showToast("Cancellation requested", Theme.GRAY);
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
```

</ExpandableCode>

<div class="videos-container" style={{maxWidth: '400px', margin: '0 auto'}}>
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/webforj-long-tasks.mp4" type="video/mp4"/>
  </video>
</div>

<!-- vale on -->

### Análisis del caso práctico {#case-study-analysis}

Esta implementación muestra varios patrones fundamentales:

#### 1. Administración del grupo de hilos {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
  Thread t = new Thread(r, "LongTaskView-Worker");
  t.setDaemon(true);
  return t;
});
```
- Usa un **ejecutor de un solo hilo** para evitar el agotamiento de recursos
- Crea **hilos daemon** que no impedirán el cierre de la JVM

#### 2. Nombrar el destino {#2-naming-the-target}
```java
Environment env = Environment.getCurrent();
```
`startLongTask()` se ejecuta en el hilo de `Environment`, por lo que lee allí el destino y la tarea en segundo plano lo pasa a cada llamada a `Environment.runLater()`. El mismo código funciona sin cambios en un grupo de hilos compartido por toda la aplicación.

#### 3. Seguimiento de las actualizaciones pendientes {#3-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Se realiza un seguimiento de cada llamada a `Environment.runLater()` para permitir:
- La cancelación cuando el usuario pulsa el botón de cancelar
- La prevención de pérdidas de memoria en `onDestroy()`
- La limpieza adecuada durante el ciclo de vida del componente

#### 4. Cancelación cooperativa {#4-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
El hilo en segundo plano comprueba esta marca en cada iteración, lo que permite:
- Responder de inmediato a la cancelación
- Salir limpiamente del bucle
- Evitar más actualizaciones de la interfaz de usuario

#### 5. Administración del ciclo de vida {#5-lifecycle-management}
```java
@Override
protected void onDestroy() {
  super.onDestroy();
  cancelTask();  // Reuses cancellation logic
  currentTask = null;
  executor.shutdown();
}
```
Es fundamental para evitar pérdidas de memoria mediante:
- La cancelación de todas las actualizaciones pendientes de la interfaz de usuario
- La interrupción de los hilos en ejecución
- El apagado del ejecutor

#### 6. Prueba de la capacidad de respuesta de la interfaz de usuario {#6-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
  int count = clickCount.incrementAndGet();
  showToast("Click #" + count + " - UI is responsive!", Theme.GRAY);
});
```
Demuestra que el hilo de la interfaz de usuario sigue respondiendo durante las operaciones en segundo plano.
