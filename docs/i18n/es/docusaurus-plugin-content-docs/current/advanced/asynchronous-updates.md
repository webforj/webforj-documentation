---
sidebar_position: 46
title: Asynchronous Updates
sidebar_class_name: new-content
_i18n_hash: 0db4be3f7e785c967b2e7efa442ca3ff
---
<DocChip chip='since' label='25.02' />
<DocChip chip='experimental' />
<JavadocLink type="foundation" location="com/webforj/Environment" anchor="runLater(java.lang.Runnable)" top='true'/>

La API `Environment.runLater()` proporciona un mecanismo para actualizar de manera segura la interfaz de usuario desde hilos en segundo plano en aplicaciones webforJ. Esta característica experimental permite operaciones asincrónicas mientras mantiene la seguridad de los hilos para las modificaciones de la interfaz de usuario.

:::warning API experimental
Esta API está marcada como experimental desde la versión 25.02 y puede cambiar en futuras versiones. La firma de la API, el comportamiento y las características de rendimiento están sujetos a modificación.
:::

## Comprendiendo el modelo de hilos {#understanding-the-thread-model}

webforJ impone un modelo de hilos estricto donde todas las operaciones de la interfaz de usuario deben realizarse en el hilo `Environment`. Esta restricción existe porque:

1. **Restricciones de la API webforJ**: La API webforJ subyacente se vincula al hilo que creó la sesión.
2. **Afinidad de hilos de componentes**: Los componentes de la interfaz de usuario mantienen un estado que no es seguro para los hilos.
3. **Gestión de eventos**: Todos los eventos de la interfaz de usuario se procesan secuencialmente en un solo hilo.

Este modelo de un solo hilo previene condiciones de carrera y mantiene un estado consistente para todos los componentes de la interfaz de usuario, pero crea desafíos al integrarse con tareas de computación asincrónicas de larga duración.

## API `RunLater` {#runlater-api}

La API `Environment.runLater()` proporciona dos métodos para programar actualizaciones de la interfaz de usuario:

```java title="Environment.java"
// Programar una tarea sin valor de retorno
public static PendingResult<Void> runLater(Runnable task)

// Programar una tarea que devuelve un valor
public static <T> PendingResult<T> runLater(Supplier<T> supplier)
```

Ambos métodos devuelven un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> que rastrea la finalización de la tarea y proporciona acceso al resultado o a cualquier excepción que haya ocurrido.

## Herencia del contexto de hilo {#thread-context-inheritance}

La herencia automática del contexto es una característica crítica de `Environment.runLater()`. Cuando un hilo que se ejecuta en un `Environment` crea hilos secundarios, esos hilos hijos heredan automáticamente la capacidad de usar `runLater()`.

### Cómo funciona la herencia {#how-inheritance-works}

Cualquier hilo creado desde dentro de un hilo `Environment` automáticamente tiene acceso a ese `Environment`. Esta herencia ocurre automáticamente, por lo que no necesitas pasar ningún contexto ni configurar nada.

```java
@Route
public class DataView extends Composite<Div> {
    private final ExecutorService executor = Executors.newCachedThreadPool();
    
    public DataView() {
        // Este hilo tiene contexto de Environment
        
        // Los hilos hijos heredan el contexto automáticamente
        executor.submit(() -> {
            String data = fetchRemoteData();
            
            // Puede usar runLater porque se heredó el contexto
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

El comportamiento de ejecución de `runLater()` depende de qué hilo lo llame:

### Desde el hilo de la interfaz de usuario {#from-the-ui-thread}

Cuando se llama desde el propio hilo `Environment`, las tareas se ejecutan **de manera sincrónica e inmediata**:

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

Con este comportamiento sincrónico, las actualizaciones de la interfaz de usuario desde los controladores de eventos se aplican de inmediato y no incurren en ninguna sobrecarga de cola innecesaria.

### Desde hilos en segundo plano {#from-background-threads}

Cuando se llama desde un hilo en segundo plano, las tareas se **ponen en cola para ejecución asincrónica**:

```java
@Override
public void onDidCreate() {
    CompletableFuture.runAsync(() -> {
        // Esto se ejecuta en un hilo de ForkJoinPool
        System.out.println("En segundo plano: " + Thread.currentThread().getName());
        
        PendingResult<Void> result = Environment.runLater(() -> {
            // Esto se ejecuta en el hilo de Environment
            System.out.println("Actualización de la UI: " + Thread.currentThread().getName());
            statusLabel.setText("Procesamiento completo");
        });
        
        // result.isDone() sería falso aquí
        // La tarea está en cola y se ejecutará de manera asincrónica
    });
}
```

webforJ procesa las tareas enviadas desde hilos en segundo plano en **estricto orden FIFO**, preservando la secuencia de operaciones incluso cuando se envían desde múltiples hilos de manera concurrente. Con esta garantía de orden, las actualizaciones de la interfaz de usuario se aplican en el orden exacto en que fueron enviadas. Así que si el hilo A envía la tarea 1, y luego el hilo B envía la tarea 2, la tarea 1 siempre se ejecutará antes que la tarea 2 en el hilo de la interfaz de usuario. Procesar las tareas en orden FIFO previene inconsistencias en la interfaz de usuario.

## Cancelación de tareas {#task-cancellation}

El <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> devuelto por `Environment.runLater()` admite la cancelación, lo que le permite evitar que las tareas en cola se ejecuten. Al cancelar tareas pendientes, puede evitar fugas de memoria y prevenir que operaciones de larga duración actualicen la interfaz de usuario después de que ya no son necesarias.

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

Al realizar operaciones de larga duración con actualizaciones frecuentes de la interfaz de usuario, rastree todos los resultados pendientes:

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
                
                // Seguir por si se necesita cancelar
                pendingUpdates.add(update);
                
                Thread.sleep(100);
            }
        });
    }
    
    public void cancelTask() {
        isCancelled = true;
        
        // Cancelar todas las actualizaciones de interfaz pendientes
        for (PendingResult<?> pending : pendingUpdates) {
            if (!pending.isDone()) {
                pending.cancel();
            }
        }
        pendingUpdates.clear();
    }
}
```

### Gestión del ciclo de vida del componente {#component-lifecycle-management}

Cuando se destruyen los componentes (por ejemplo, durante la navegación), cancele todas las actualizaciones pendientes para prevenir fugas de memoria:

```java
@Route
public class CleanupView extends Composite<Div> {
    private final List<PendingResult<?>> pendingUpdates = new ArrayList<>();
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        // Cancelar todas las actualizaciones pendientes para prevenir fugas de memoria
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

1. **Requisito de contexto**: Los hilos deben haber heredado un contexto de `Environment`. Los hilos de bibliotecas externas, los temporizadores del sistema y los inicializadores estáticos no pueden usar esta API.

2. **Prevención de fugas de memoria**: Siempre rastree y cancele objetos `PendingResult` en los métodos del ciclo de vida del componente. Las lambdas en cola capturan referencias a componentes de la interfaz de usuario, previniendo la recolección de basura si no se cancelan.

3. **Ejecución FIFO**: Todas las tareas se ejecutan en estricto orden FIFO sin importar su importancia. No hay un sistema de prioridad.

4. **Limitaciones de cancelación**: La cancelación solo previene la ejecución de tareas en cola. Las tareas que ya se están ejecutando completarán normalmente.

## Estudio de caso completo: `LongTaskView` {#complete-case-study-longtaskview}

Lo siguiente es una implementación completa, lista para producción, que demuestra todas las mejores prácticas para actualizaciones asincrónicas de la interfaz de usuario:

<!-- vale off -->

<ExpandableCode title="LongTaskView.java" language="java" startLine={91} endLine={159}>
{`
@Route("/")
public class LongTaskView extends Composite<FlexLayout> {
  // Utilizar un ejecutor de un solo hilo para prevenir el agotamiento de recursos
  // Para producción, considere usar un grupo de hilos compartido a nivel de aplicación
  private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
  });

  // Rastrear la tarea actual y actualizaciones de UI pendientes
  private CompletableFuture<Void> currentTask = null;
  private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
  private volatile boolean isCancelled = false;

  // Componentes de UI
  private FlexLayout self = getBoundComponent();
  private H2 titleLabel = new H2("Demostración de actualizaciones de UI en segundo plano");
  private Paragraph descriptionPara = new Paragraph(
      "Esta demostración muestra cómo Environment.runLater() permite actualizaciones de UI seguras desde hilos en segundo plano. " +
          "Haga clic en 'Iniciar tarea larga' para ejecutar un cálculo en segundo plano de 10 segundos que actualiza el progreso de la UI. " +
          "El botón 'Probar UI' prueba que la UI permanece receptiva durante la operación en segundo plano.");
  private TextField statusField = new TextField("Estado");
  private ProgressBar progressBar = new ProgressBar();
  private TextField resultField = new TextField("Resultado");
  private Button startButton = new Button("Iniciar tarea larga");
  private Button cancelButton = new Button("Cancelar tarea");
  private Button testButton = new Button("Probar UI - ¡Haga clic en mí!");
  private Paragraph footerPara = new Paragraph(
      "Nota: La tarea puede cancelarse en cualquier momento, demostrando la limpieza adecuada tanto del " +
          "hilo en segundo plano como de las actualizaciones de UI en cola.");
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

    // Limpiar referencia a la tarea
    currentTask = null;

    // Apagar el ejecutor de instancia de manera ordenada
    executor.shutdown();
  }

  private void startLongTask() {
    startButton.setEnabled(false);
    cancelButton.setEnabled(true);
    statusField.setValue("Iniciando tarea en segundo plano...");
    progressBar.setValue(0);
    resultField.setValue("");

    // Restablecer la bandera de cancelación y limpiar actualizaciones pendientes
    isCancelled = false;
    pendingUIUpdates.clear();

    // Iniciar tarea en segundo plano con ejecutor explícito
    // Nota: cancel(true) interrumpirá el hilo, haciendo que Thread.sleep() lance
    // InterruptedException
    currentTask = CompletableFuture.runAsync(() -> {
      double result = 0;

      // Simular una tarea larga con 100 pasos
      for (int i = 0; i <= 100; i++) {
        // Verificar si se canceló
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
          // El hilo fue interrumpido - salida inmediata
          Thread.currentThread().interrupt(); // Restaurar estado interrumpido
          return;
        }

        // Realizar algún cálculo (determinista para la demostración)
        // Produce valores entre 0 y 1
        result += Math.sin(i) * 0.5 + 0.5;

        // Actualizar progreso desde el hilo en segundo plano
        final int progress = i;
        PendingResult<Void> updateResult = Environment.runLater(() -> {
          progressBar.setValue(progress);
          statusField.setValue("Procesando... " + progress + "%");
        });
        pendingUIUpdates.add(updateResult);
      }

      // Actualización final con el resultado (este código solo se alcanza si la tarea se completó sin
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

#### 1. Gestión de grupos de hilos {#1-thread-pool-management}
```java
private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
    Thread t = new Thread(r, "LongTaskView-Worker");
    t.setDaemon(true);
    return t;
});
```
- Utiliza un **ejecutor de un solo hilo** para prevenir el agotamiento de recursos.
- Crea **hilos demonios** que no impedirán el cierre de la JVM.

#### 2. Rastreo de actualizaciones pendientes {#2-tracking-pending-updates}
```java
private final List<PendingResult<?>> pendingUIUpdates = new ArrayList<>();
```
Cada llamada a `Environment.runLater()` se rastrea para permitir:
- Cancelación cuando el usuario hace clic en cancelar.
- Prevención de fugas de memoria en `onDestroy()`.
- Limpieza adecuada durante el ciclo de vida del componente.

#### 3. Cancelación cooperativa {#3-cooperative-cancellation}
```java
private volatile boolean isCancelled = false;
```
El hilo en segundo plano verifica esta bandera en cada iteración, permitiendo:
- Respuesta inmediata a la cancelación.
- Salida limpia del bucle.
- Prevención de actualizaciones adicionales de la interfaz de usuario.

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
Crítico para prevenir fugas de memoria al:
- Cancelar todas las actualizaciones de UI pendientes.
- Interrumpir hilos en ejecución.
- Apagar el ejecutor.

#### 5. Pruebas de capacidad de respuesta de la interfaz de usuario {#5-ui-responsiveness-testing}
```java
testButton.onClick(e -> {
    int count = clickCount.incrementAndGet();
    showToast("Clic #" + count + " - ¡La UI es receptiva!", Theme.GRAY);
});
```
Demuestra que el hilo de la interfaz de usuario permanece receptivo durante las operaciones en segundo plano.
