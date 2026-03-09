---
title: ProgressBar
sidebar_position: 90
_i18n_hash: 7612411ef90d5344a2bab79b7e221141
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
- Opciones para visibilidad del texto, animación y diseños a rayas para una mejor retroalimentación visual.

El siguiente ejemplo muestra una barra de progreso a rayas y animada con controles de inicio, pausa y reinicio:

<ComponentDemo 
path='/webforj/progressbarbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarBasicView.java'
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

La `ProgressBar` puede estar orientada horizontal o verticalmente.

<ComponentDemo 
path='/webforj/progressbarorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarOrientationView.java'
height='175px'
/>

## Estado indeterminado {#indeterminate-state}

La `ProgressBar` admite un estado indeterminado para tareas con tiempo de finalización desconocido.

<ComponentDemo 
path='/webforj/progressbardeterminate?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarDeterminateView.java'
height='25px'
/>

## Texto y visibilidad del texto {#text-and-text-visibility}

Por defecto, al ser creada, la barra de progreso muestra el porcentaje completo en el formato `XX%`. Usando el método `setText()`, puedes usar el marcador de posición `{{x}}` para obtener el valor actual como un porcentaje. Además, puedes usar el marcador de posición `{{value}}` para obtener el valor actual en bruto.

```java
ProgressBar bar = new ProgressBar(15, "Descargando: {{x}}%");
```

## Estilo {#styling}

### Temas {#themes}

El componente `ProgressBar` viene con <JavadocLink type="foundation" location="com/webforj/component/Theme"> temas </JavadocLink> integrados para un estilo rápido sin el uso de CSS. Estos temas son estilos predefinidos que se pueden aplicar a botones para cambiar su apariencia y presentación visual. 
Ofrecen una manera rápida y consistente de personalizar el aspecto de las ProgressBars en toda una aplicación. 

<ComponentDemo 
path='/webforj/progressbarthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarThemesView.java'
height='320px'
/>

<TableBuilder name="ProgressBar" />

## Mejores prácticas {#best-practices}

- **Usar valores mínimos y máximos apropiados**: Establecer los valores mínimo y máximo para reflejar con precisión el rango de la tarea.
- **Actualizar el progreso regularmente**: Actualizar continuamente el valor de progreso para proporcionar retroalimentación en tiempo real a los usuarios.
- **Utilizar el estado indeterminado para duraciones desconocidas**: Usar el estado indeterminado para tareas con duraciones impredecibles para indicar progreso en curso.
- **Mostrar texto para mejor retroalimentación del usuario**: Mostrar texto en la barra de progreso para ofrecer contexto adicional sobre el avance de la tarea.
