---
sidebar_position: 15
title: Interval
_i18n_hash: 07054545ea64670e83423a6b11a5cce3
---
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/Interval" top='true'/>

La clase <JavadocLink type="foundation" location="com/webforj/Interval" code='true' >Interval</JavadocLink> representa un temporizador que desencadena un [evento](../building-ui/events) con un retraso de tiempo fijo entre cada activación.

La clase `Interval` proporciona una forma sencilla de activar eventos después de un retraso específico. Es posible iniciar, detener y reiniciar un `Interval` según sea necesario. Además, los Intervalos pueden soportar múltiples oyentes para el evento transcurrido.
Optimizado para el marco de trabajo webforJ, ofrece un mejor rendimiento en comparación con el temporizador estándar de Java o el temporizador de Swing.

## Usos {#usages}
La clase `Interval` desencadena evento(s) a un retraso de tiempo fijo. Aprovechando los Intervalos de manera creativa, puedes mejorar la interacción y el compromiso del usuario en tu sitio web mientras mantienes la experiencia dinámica e interesante:

1. **Verificar Inactividad**: Mostrar un componente [`Dialog`](../components/dialog) si no ha habido interacción en un formulario dentro de un tiempo determinado.

2. **Contenido Destacado**: Rotar artículos, productos o promociones destacados en tu página de inicio en cada Intervalo. Esto mantiene el contenido dinámico y atractivo.

3. **Datos en Vivo**: Actualizar datos en tu aplicación, como precios de acciones, feeds de noticias o actualizaciones meteorológicas, en cada Intervalo para mantener los datos actuales.

## Gestión de estados de `Interval`: iniciar, detener y reiniciar {#managing-interval-states-starting-stopping-and-restart}
Un Intervalo requiere activación manual; utiliza el método `start()` para iniciarlo. Para detener un Intervalo, utiliza el método `stop()`. El método `restart()` se puede utilizar para reiniciar el Intervalo.

## Ajustando el retraso del `Interval` {#adjusting-the-interval-delay}

Para modificar el retraso de un Intervalo, utiliza el método `setDelay(float delay)`. El nuevo valor de retraso se aplica después de que el Intervalo se detenga o se reinicie.

```java
//Cambio del Retraso
Interval.setDelay(2f);
Interval.restart();
```

:::tip
El retraso puede ser de segundos fraccionarios con resolución en milisegundos, pero un valor de tiempo de espera muy pequeño provoca una inundación de eventos más rápido de lo que el programa puede responder a ellos.
:::

## Agregando oyentes {#adding-listeners}

Puedes adjuntar oyentes adicionales a un Intervalo utilizando el método `addElapsedListener(EventListener\<Interval.ElapsedEvent\> listener)`. Una vez que se agrega un oyente, se activa automáticamente en el siguiente intervalo si el Intervalo ya está en funcionamiento.

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
