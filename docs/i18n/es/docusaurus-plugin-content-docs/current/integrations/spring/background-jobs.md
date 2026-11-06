---
title: Tareas en segundo plano
sidebar_position: 25
description: >-
  Run Spring @Async services from webforJ views and marshal progress and results
  back to the UI thread with Environment.runLater.
_i18n_hash: 2760d6db6a7b7045e7612ca359d04a28
---
Cuando los usuarios hacen clic en un botón para generar un informe o procesar datos, esperan que la interfaz siga respondiendo. Las barras de progreso deberían animarse, los botones deberían reaccionar al pasar el cursor y la aplicación no debería congelarse. La anotación `@Async` de Spring lo hace posible al trasladar las operaciones de larga duración a subprocesos en segundo plano.

webforJ impone la seguridad de subprocesos para los componentes de la interfaz de usuario: todas las actualizaciones deben realizarse en el subproceso de la interfaz de usuario. Esto plantea un desafío: ¿cómo pueden las tareas en segundo plano actualizar las barras de progreso o mostrar resultados? La respuesta es `Environment.runLater()`, que transfiere de forma segura las actualizaciones de la interfaz de usuario desde los subprocesos en segundo plano de Spring al subproceso de la interfaz de usuario del usuario que inició la tarea.

## Habilitar la ejecución asíncrona {#enabling-asynchronous-execution}

La ejecución asíncrona de métodos de Spring requiere una configuración explícita. Sin ella, los métodos anotados con `@Async` se ejecutan de forma síncrona, lo que anula su propósito.

Añade `@EnableAsync` a la clase de tu aplicación Spring Boot:

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

La anotación `@EnableAsync` activa la infraestructura de Spring para detectar los métodos `@Async` y ejecutarlos en subprocesos en segundo plano.

:::tip[Guía de Spring para la ejecución asíncrona]
Para una introducción rápida a la anotación `@Async` de Spring y sus patrones básicos de uso, consulta [Crear métodos asíncronos](https://spring.io/guides/gs/async-method).
:::

## Crear servicios asíncronos {#creating-async-services}

Los servicios anotados con `@Service` pueden tener métodos marcados con `@Async` para ejecutarse en subprocesos en segundo plano. Estos métodos suelen devolver un `CompletableFuture` para permitir gestionar correctamente su finalización y cancelación:

```java
@Service
public class BackgroundService {

  @Async
  public CompletableFuture<String> performLongRunningTask(Consumer<Integer> progressCallback) {
    try {
      for (int i = 0; i <= 10; i++) {
          // Informar del progreso
          int progress = i * 10;
          if (progressCallback != null) {
              progressCallback.accept(progress);
          }

          // Simular el trabajo
          Thread.sleep(500);
      }

      return CompletableFuture.completedFuture(
        "¡La tarea se completó correctamente desde el servicio en segundo plano!");
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      return CompletableFuture.failedFuture(e);
    }
  }
}
```

Este servicio acepta una función de devolución de llamada de progreso (`Consumer<Integer>`) que se invoca desde el subproceso en segundo plano. Este patrón permite que el servicio informe del progreso sin conocer los componentes de la interfaz de usuario.

El método simula una tarea de 5 segundos con 10 actualizaciones de progreso. En producción, aquí se realizaría trabajo real, como consultas a bases de datos o procesamiento de archivos. La gestión de excepciones restablece el estado de interrupción para permitir la cancelación correcta de la tarea cuando se llama a `cancel(true)`.

## Usar tareas en segundo plano en las vistas {#using-background-tasks-in-views}

La vista recibe el servicio en segundo plano mediante la inyección por constructor:

```java
@Route("/")
public class HelloWorldView extends Composite<FlexLayout> {
  private Button asyncBtn = new Button("Iniciar tarea en segundo plano");
  private ProgressBar progressBar = new ProgressBar();
  private CompletableFuture<String> currentTask;

  public HelloWorldView(BackgroundService backgroundService) {
    // Spring inyecta el servicio
    asyncBtn.addClickListener(e -> {
      // El controlador del clic se ejecuta en el subproceso de la interfaz de usuario de este usuario
      Environment env = Environment.getCurrent();

      currentTask = backgroundService.performLongRunningTask(progress -> {
        Environment.runLater(env, () -> {
          progressBar.setValue(progress);
        });
      });
    });
  }
}
```

Spring inyecta `BackgroundService` en el constructor de la vista, igual que cualquier otro bean de Spring. Después, la vista usa este servicio para iniciar tareas en segundo plano. El concepto clave es que las funciones de devolución de llamada del servicio se ejecutan en el grupo de subprocesos de Spring, que comparten todos los usuarios de la aplicación, por lo que un subproceso del grupo no puede saber qué interfaz de usuario de usuario debe actualizar. El controlador del clic obtiene el `Environment` del usuario con `Environment.getCurrent()` mientras aún está en el subproceso de la interfaz de usuario, y cada actualización de la interfaz de usuario dentro de las funciones de devolución de llamada se lo pasa a `Environment.runLater()`.

La gestión de la finalización requiere la misma atención a los subprocesos:

```java
currentTask.whenComplete((result, error) -> {
  Environment.runLater(env, () -> {
    asyncBtn.setEnabled(true);
    progressBar.setVisible(false);
    if (error != null) {
      Toast.show("Error en la tarea: " + error.getMessage(), Theme.DANGER);
    } else {
      Toast.show(result, Theme.SUCCESS);
    }
  });
});
```

La función de devolución de llamada `whenComplete` también se ejecuta en un subproceso en segundo plano. Cada operación de la interfaz de usuario —habilitar el botón, ocultar la barra de progreso y mostrar notificaciones— debe envolverse en `Environment.runLater()` con el mismo `env`. Si no se hace, webforJ lanza excepciones porque los subprocesos en segundo plano no pueden acceder a los componentes de la interfaz de usuario.

:::warning[Seguridad de subprocesos]
Cada actualización de la interfaz de usuario desde un subproceso en segundo plano debe envolverse en `Environment.runLater()`, usando el `Environment` obtenido en el subproceso de la interfaz de usuario antes de iniciar la tarea. Esta regla no admite excepciones. El acceso directo a componentes desde métodos `@Async` siempre falla, y `Environment.getCurrent()` devuelve `null` en un subproceso del grupo.
:::

:::tip[Más información sobre la seguridad de subprocesos]
Para obtener información detallada sobre el modelo de subprocesos de webforJ, el comportamiento de ejecución y las operaciones que requieren `Environment.runLater()`, consulta [Actualizaciones asíncronas](../../advanced/asynchronous-updates).
:::

## Cancelación y limpieza de tareas {#task-cancellation-and-cleanup}

Una gestión adecuada del ciclo de vida evita fugas de memoria y actualizaciones no deseadas de la interfaz de usuario. La vista almacena la referencia a `CompletableFuture`:

```java
private CompletableFuture<String> currentTask;
```

Cuando se destruye la vista, cancela cualquier tarea en curso:

```java
@Override
protected void onDestroy() {
  // Cancelar la tarea si se destruye la vista
  if (currentTask != null && !currentTask.isDone()) {
    currentTask.cancel(true);
  }
}
```

El parámetro `cancel(true)` es fundamental. Interrumpe el subproceso en segundo plano y hace que las operaciones bloqueantes, como `Thread.sleep()`, lancen `InterruptedException`. Esto permite terminar la tarea de inmediato. Sin la marca de interrupción (`cancel(false)`), la tarea seguiría ejecutándose hasta que comprobara explícitamente si se había cancelado.

Esta limpieza evita varios problemas:
- Los subprocesos en segundo plano siguen consumiendo recursos después de que desaparezca la vista.
- Las actualizaciones de la interfaz de usuario intentan modificar componentes destruidos.
- Se producen fugas de memoria porque las funciones de devolución de llamada mantienen referencias a los componentes de la interfaz de usuario.
