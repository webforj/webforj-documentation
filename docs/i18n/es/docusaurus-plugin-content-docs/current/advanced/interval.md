---
sidebar_position: 20
title: Interval
_i18n_hash: 1fd4c3fc2bf38df65a68d909a6ff77a3
---
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/Interval" top='true'/>

La clase <JavadocLink type="foundation" location="com/webforj/Interval" code='true' >Interval</JavadocLink> representa un temporizador que activa un [evento](../building-ui/events) con un retraso de tiempo fijo entre cada activación.

Puedes [iniciar, detener y reiniciar](#managing-interval-states-starting-stopping-and-restart) un `Interval` según sea necesario y agregar múltiples [escuchas](#adding-listeners) para el evento de tiempo transcurrido.
En webforJ, un `Interval` tiene [mejor rendimiento](#performance) en comparación con un temporizador estándar de Java o un temporizador de Swing.

## Usos {#usages}
La clase `Interval` activa eventos con un retraso de tiempo fijo. Al usar Intervalos de manera creativa, puedes crear experiencias dinámicas e interesantes en tu aplicación:

1. **Verificar Inactividad**: Muestra un componente [`Dialog`](../components/dialog) si no ha habido interacción en un formulario dentro de un tiempo determinado.

2. **Contenido Destacado**: Rota a través de artículos, productos o promociones destacados en tu página de inicio en cada intervalo. Esto mantiene el contenido dinámico y atractivo.

3. **Datos en Vivo**: Actualiza los datos en tu aplicación, como precios de acciones, feeds de noticias o actualizaciones del clima, en cada intervalo para mantener los datos actuales.

## Gestión de estados del `Interval`: iniciando, deteniendo y reiniciando {#managing-interval-states-starting-stopping-and-restart}
Un Interval requiere activación manual; usa el método `start()` para iniciarlo. Para detener un Interval, usa el método `stop()`. El método `restart()` se puede usar para reiniciar el Interval.

## Ajustando el retraso del `Interval` {#adjusting-the-interval-delay}

Para modificar el retraso de un Interval, utiliza el método `setDelay(float delay)`. El nuevo valor de retraso se aplica después de que el Interval es detenido o reiniciado.

```java
//Cambiando el Retraso
Interval.setDelay(2f);
Interval.restart();
```

:::tip
El retraso puede ser en segundos fraccionarios con resolución en milisegundos, pero un valor de tiempo de espera muy pequeño provoca una inundación de eventos más rápida de lo que el programa puede responder.
:::

## Agregar escuchas {#adding-listeners}

Puedes adjuntar escuchas adicionales a un Interval utilizando el método `addElapsedListener(EventListener\<Interval.ElapsedEvent\> listener)`. Una vez que se agrega una escucha, se activa automáticamente en el siguiente intervalo si el Interval ya está en funcionamiento.

```java
// Agregando Escuchas
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

## Rendimiento {#performance}

La clase `Interval` está diseñada específicamente para proporcionar mejor rendimiento y fiabilidad para las grandes cargas que encuentran las aplicaciones web.
En Java Swing, el mismo comportamiento se puede manejar adecuadamente con un `Timer` o un nuevo hilo, pero ese enfoque no escala bien para aplicaciones web.
Las aplicaciones web probablemente tendrán muchos usuarios concurrentes, y si cada usuario crea un nuevo Timer o hilo, el sistema puede romperse rápidamente cuando se quede sin hilos.

Existen varias opciones viables que funcionan a esta escala: [**hilos virtuales**](https://docs.oracle.com/en/java/javase/21/core/virtual-threads.html), [**Spring TaskExecutor y TaskScheduler**](https://docs.spring.io/spring-framework/reference/integration/scheduling.html), y **`Interval`**.
Dependiendo de tu aplicación y caso de uso, cualquiera de estas puede ser la mejor opción para ti.
Como predeterminado, `Interval` es una opción confiable diseñada específicamente para trabajar con webforJ, y no requiere configuración adicional.
