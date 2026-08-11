---
title: Background Jobs
sidebar_position: 25
description: >-
  Run Spring @Async services from webforJ views and marshal progress and results
  back to the UI thread with Environment.runLater.
_i18n_hash: 1b265d2e723c0f58c97fd2c4375f15a1
---
Cuando los usuarios hacen clic en un botón para generar un informe o procesar datos, esperan que la interfaz siga siendo receptiva. Las barras de progreso deben animarse, los botones deben reaccionar al pasar el mouse y la aplicación no debe congelarse. La anotación `@Async` de Spring hace esto posible al mover operaciones de larga duración a hilos en segundo plano.

webforJ impone la seguridad de los hilos para los componentes de la interfaz de usuario: todas las actualizaciones deben realizarse en el hilo de la UI. Esto crea un desafío: ¿cómo actualizan las tareas en segundo plano las barras de progreso o muestran resultados? La respuesta es `Environment.runLater()`, que transfiere de manera segura las actualizaciones de la UI desde los hilos en segundo plano de Spring al hilo de la UI de webforJ.

## Habilitando la ejecución asincrónica {#enabling-asynchronous-execution}

La ejecución de métodos asincrónicos de Spring requiere una configuración explícita. Sin ella, los métodos anotados con `@Async` se ejecutan de forma sincrónica, lo que anula su propósito.

Agrega `@EnableAsync` a la clase de tu aplicación Spring Boot:

```java {2}
@SpringBootApplication
@EnableAsync
@Routify(packages = { "com.example.views" })
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
```

La anotación `@EnableAsync` activa la infraestructura de Spring para detectar métodos `@Async` y ejecutarlos en hilos en segundo plano.

:::tip[Guía de asincronía de Spring]
Para una introducción rápida a la anotación `@Async` de Spring y patrones de uso básicos, consulta [Creando Métodos Asincrónicos](https://spring.io/guides/gs/async-method).
:::

## Creando servicios asincrónicos {#creating-async-services}

Los servicios anotados con `@Service` pueden tener métodos marcados con `@Async` para ejecutarse en hilos en segundo plano. Estos métodos típicamente devuelven `CompletableFuture` para permitir una correcta gestión de la finalización y la cancelación:

```java
@Service
public class BackgroundService {

  @Async
  public CompletableFuture<String> performLongRunningTask(Consumer<Integer> progressCallback) {
    try {
      for (int i = 0; i <= 10; i++) {
          // Reportar progreso
          int progress = i * 10;
          if (progressCallback != null) {
              progressCallback.accept(progress);
          }

          // Simular trabajo
          Thread.sleep(500);
      }

      return CompletableFuture.completedFuture(
        "¡Tarea completada exitosamente desde el servicio en segundo plano!");
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      return CompletableFuture.failedFuture(e);
    }
  }
}
```

Este servicio acepta un callback de progreso (`Consumer<Integer>`) que se llama desde el hilo en segundo plano. El patrón de callback permite que el servicio informe el progreso sin conocer los componentes de la interfaz de usuario.

El método simula una tarea de 5 segundos con 10 actualizaciones de progreso. En producción, esto sería trabajo real como consultas a base de datos o procesamiento de archivos. El manejo de excepciones restaura el estado de interrupción para soportar la cancelación adecuada de la tarea cuando se llama a `cancel(true)`.

## Usando tareas en segundo plano en vistas {#using-background-tasks-in-views}

La vista recibe el servicio en segundo plano a través de la inyección de constructor:

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {
  private Button asyncBtn = new Button("Iniciar Tarea en Segundo Plano");
  private ProgressBar progressBar = new ProgressBar();
  private CompletableFuture<String> currentTask;

  public HelloWorldView(BackgroundService backgroundService) {
    // El servicio es inyectado por Spring
    asyncBtn.addClickListener(e -> {
      currentTask = backgroundService.performLongRunningTask(progress -> {
        Environment.runLater(() -> {
          progressBar.setValue(progress);
        });
      });
    });
  }
}
```

Spring inyecta el `BackgroundService` en el constructor de la vista, al igual que cualquier otro bean de Spring. La vista luego utiliza este servicio para iniciar tareas en segundo plano. El concepto clave: los callbacks del servicio se ejecutan en hilos en segundo plano, por lo que cualquier actualización de la UI dentro de esos callbacks debe usar `Environment.runLater()` para transferir la ejecución al hilo de la UI.

El manejo de la finalización requiere la misma cuidadosa gestión de hilos:

```java
currentTask.whenComplete((result, error) -> {
  Environment.runLater(() -> {
    asyncBtn.setEnabled(true);
    progressBar.setVisible(false);
    if (error != null) {
      Toast.show("La tarea falló: " + error.getMessage(), Theme.DANGER);
    } else {
      Toast.show(result, Theme.SUCCESS);
    }
  });
});
```

El callback `whenComplete` también se ejecuta en un hilo en segundo plano. Cada operación de UI - habilitar el botón, ocultar la barra de progreso, mostrar toasts - debe estar envuelta en `Environment.runLater()`. Sin este envolvimiento, webforJ lanza excepciones porque los hilos en segundo plano no pueden acceder a los componentes de la UI.

:::warning[Seguridad de hilos]
Cada actualización de la UI desde un hilo en segundo plano debe estar envuelta en `Environment.runLater()`. Esta regla no tiene excepciones. El acceso directo a componentes desde métodos `@Async` siempre falla.
:::

:::tip[Aprende más sobre la seguridad de hilos]
Para información detallada sobre el modelo de hilos de webforJ, el comportamiento de ejecución y qué operaciones requieren `Environment.runLater()`, consulta [Actualizaciones Asincrónicas](../../advanced/asynchronous-updates).
:::

## Cancelación de tareas y limpieza {#task-cancellation-and-cleanup}

La correcta gestión del ciclo de vida previene fugas de memoria y actualizaciones indeseadas de la UI. La vista almacena la referencia de `CompletableFuture`:

```java
private CompletableFuture<String> currentTask;
```

Cuando la vista se destruye, cancela cualquier tarea en ejecución:

```java
@Override
protected void onDestroy() {
  // Cancelar la tarea si la vista se destruye
  if (currentTask != null && !currentTask.isDone()) {
    currentTask.cancel(true);
  }
}
```

El parámetro `cancel(true)` es crucial. Interrumpe el hilo en segundo plano, haciendo que operaciones bloqueantes como `Thread.sleep()` lancen `InterruptedException`. Esto permite la terminación inmediata de la tarea. Sin la bandera de interrupción (`cancel(false)`), la tarea continuaría ejecutándose hasta que se verifique explícitamente la cancelación.

Esta limpieza previene varios problemas:
- Los hilos en segundo plano continúan consumiendo recursos después de que la vista ha desaparecido
- Las actualizaciones de la UI intentan modificar componentes destruidos
- Fugas de memoria de callbacks que mantienen referencias a componentes de la UI
