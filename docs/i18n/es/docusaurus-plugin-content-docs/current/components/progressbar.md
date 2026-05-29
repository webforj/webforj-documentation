---
title: ProgressBar
sidebar_position: 90
_i18n_hash: 6acac582ce905eb255ee09e499fd561f
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-progressbar" />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/progressbar/ProgressBar" top='true'/>

El componente `ProgressBar` representa visualmente el estado de finalización de una operación. A medida que avanza el trabajo, un rectángulo se llena gradualmente para reflejar el porcentaje actual. La barra también puede mostrar una representación textual de su valor y admite tanto estados determinados como indeterminados para tareas con duración conocida o desconocida.

<!-- INTRO_END -->

## Usos {#usages}

El componente `ProgressBar` es útil para visualizar el estado de finalización de tareas. Admite:

- Valores mínimos y máximos configurables.
- Modo indeterminado para tareas en curso sin un final definido.
- Opciones para visibilidad de texto, animación y diseños rayados para una mejor retroalimentación visual.

El siguiente ejemplo muestra una barra de progreso rayada y animada con controles para iniciar, pausar y reiniciar:

<ComponentDemo
path='/webforj/progressbarbasic'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarBasicView.java']}
height='150px'
/>

## Estableciendo valores {#setting-values}

El componente ProgressBar permite establecer y obtener su valor actual, límites mínimos y máximos.

```java showLineNumbers
ProgressBar bar = new ProgressBar();
bar.setMin(0);
bar.setMax(100);
bar.setValue(50);
```

## Orientación {#orientation}

El `ProgressBar` puede orientarse horizontal o verticalmente.

<ComponentDemo
path='/webforj/progressbarorientation'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarOrientationView.java']}
height='175px'
/>

## Estado indeterminado {#indeterminate-state}

El `ProgressBar` admite un estado indeterminado para tareas con tiempo de finalización desconocido.

<ComponentDemo
path='/webforj/progressbardeterminate'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarDeterminateView.java']}
height='25px'
/>

## Texto y visibilidad del texto {#text-and-text-visibility}

Por defecto, cuando se crea, la barra de progreso muestra el porcentaje completado en el formato `XX%`. Usando el método `setText()`, puedes usar el marcador de posición `{{x}}` para obtener el valor actual como un porcentaje. Además, puedes usar el marcador de posición 
`{{value}}` para obtener el valor actual en bruto.

```java
ProgressBar bar = new ProgressBar(15, "Descargando: {{x}}%");
```

## Estilo {#styling}

### Temas {#themes}

El componente `ProgressBar` viene con <JavadocLink type="foundation" location="com/webforj/component/Theme"> temas </JavadocLink> integrados para un estilizado rápido sin el uso de CSS. Estos temas son estilos predefinidos que se pueden aplicar a botones para cambiar su apariencia y presentación visual. 
Ofrecen una forma rápida y consistente de personalizar la apariencia de las ProgressBars en toda una aplicación.

<ComponentDemo
path='/webforj/progressbarthemes'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarThemesView.java']}
height='320px'
/>

<TableBuilder name="ProgressBar" />

## Mejores prácticas {#best-practices}

- **Usa Valores Mínimos y Máximos Apropiados**: Establece los valores mínimos y máximos para reflejar con precisión el rango de la tarea.
- **Actualiza el Progreso Regularmente**: Actualiza continuamente el valor de progreso para proporcionar retroalimentación en tiempo real a los usuarios.
- **Utiliza el Estado Indeterminado para Duraciones Desconocidas**: Usa el estado indeterminado para tareas con duraciones impredecibles para indicar progreso en curso.
- **Muestra Texto para Mejor Retroalimentación del Usuario**: Muestra texto en la barra de progreso para ofrecer un contexto adicional sobre el progreso de la tarea.
