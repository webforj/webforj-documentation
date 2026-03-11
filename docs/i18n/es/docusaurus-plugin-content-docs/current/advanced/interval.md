---
sidebar_position: 20
title: Interval
_i18n_hash: a220fb1607867630d6bfc03a1ce5d3e9
---
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/Interval" top='true'/>

La clase <JavadocLink type="foundation" location="com/webforj/Interval" code='true' >Interval</JavadocLink> representa un temporizador que dispara un [evento](../building-ui/events) con un retraso de tiempo fijo entre cada activación.

La clase `Interval` proporciona una manera sencilla de disparar eventos después de un retraso especificado. Es posible iniciar, detener y reiniciar un `Interval` según sea necesario. 
En webforJ, un `Interval` tiene un mejor rendimiento en comparación con un temporizador estándar de Java o el temporizador de Swing.
También soporta múltiples oyentes para el evento de tiempo transcurrido.

## Usos {#usages}
La clase `Interval` dispara eventos a un retraso de tiempo fijo. Al usar Intervals de manera creativa, puedes crear experiencias dinámicas e interesantes en tu aplicación:

1. **Verificación de Inactividad**: Muestra un componente [`Dialog`](../components/dialog) si no ha habido ninguna interacción en un formulario dentro de un tiempo determinado.

2. **Contenido Destacado**: Rota a través de artículos, productos o promociones destacados en tu página de inicio en cada Interval. Esto mantiene el contenido dinámico y atractivo.

3. **Datos en Vivo**: Actualiza los datos en tu aplicación, como precios de acciones, feeds de noticias o actualizaciones del clima, en cada Interval para mantener la información actualizada.

## Gestión de estados de `Interval`: inicio, detención y reinicio {#managing-interval-states-starting-stopping-and-restart}
Un Interval requiere activación manual; usa el método `start()` para iniciarlo. Para detener un Interval, usa el método `stop()`. El método `restart()` se puede usar para reiniciar el Interval.

## Ajustando el retraso del `Interval` {#adjusting-the-interval-delay}

Para modificar el retraso de un Interval, usa el método `setDelay(float delay)`. El nuevo valor de retraso se aplica después de que el Interval se detiene o se reinicia.


```java
//Cambiando el Retraso
Interval.setDelay(2f);
Interval.restart();
```

:::tip
El retraso puede ser en fracciones de segundo con resolución en milisegundos, pero un valor de tiempo de espera muy pequeño provoca una inundación de eventos más rápido de lo que el programa puede responder a ellos.
:::

## Agregando oyentes {#adding-listeners}

Puedes adjuntar oyentes adicionales a un Interval usando el método `addElapsedListener(EventListener\<Interval.ElapsedEvent\> listener)`. Una vez que se agrega un oyente, se activará automáticamente en el siguiente intervalo si el Interval ya está en ejecución.

```java
// Agregando Oyentes
float delay = 2f;

EventListener<Interval.ElapsedEvent> firstListener = (e -> {
// Código ejecutable
});

Interval interval = new Interval(delay, firstListener);

EventListener<Interval.ElapsedEvent> secondListener = (e -> {
// Código ejecutable
});

interval.addElapsedListener(secondListener);
```
