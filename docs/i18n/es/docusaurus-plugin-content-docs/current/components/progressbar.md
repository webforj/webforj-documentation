---
title: ProgressBar
sidebar_position: 90
_i18n_hash: 9b2f9ec23124d60ab5f8fca18e561acb
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-progressbar" />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/progressbar/ProgressBar" top='true'/>

El ProgressBar es un componente que muestra visualmente el progreso de alguna tarea. A medida que la tarea avanza hacia la finalización, la barra de progreso muestra el porcentaje de finalización de la tarea. Este porcentaje se representa visualmente por un rectángulo que comienza vacío y se va llenando gradualmente a medida que avanza la tarea. Además, la barra de progreso puede mostrar una representación textual de este porcentaje.

<ComponentDemo 
path='/webforj/progressbarbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarBasicView.java'
height='150px'
/>

## Usos {#usages}

El componente `ProgressBar` es útil para visualizar el estado de finalización de las tareas. Soporta:

- Valores mínimos y máximos configurables.
- Modo indeterminado para tareas en curso sin un final definido.
- Opciones para la visibilidad del texto, animación y diseños a rayas para una mejor retroalimentación visual.

## Establecer valores {#setting-values}

El componente ProgressBar permite establecer y obtener su valor actual, mínimos y máximos límites.

```java showLineNumbers
ProgressBar bar = new ProgressBar();
bar.setMin(0);
bar.setMax(100);
bar.setValue(50);
```

## Orientación {#orientation}

El `ProgressBar` puede orientarse horizontal o verticalmente.

<ComponentDemo 
path='/webforj/progressbarorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarOrientationView.java'
height='175px'
/>

## Estado indeterminado {#indeterminate-state}

El `ProgressBar` soporta un estado indeterminado para tareas con tiempo de finalización desconocido.

<ComponentDemo 
path='/webforj/progressbardeterminate?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarDeterminateView.java'
height='25px'
/>

## Texto y visibilidad del texto {#text-and-text-visibility}

Por defecto, al ser creado, la barra de progreso muestra el porcentaje completo en el formato `XX%`. Usando el método `setText()`, puedes usar el marcador de posición `{{x}}` para obtener el valor actual como un porcentaje. Además, puedes usar el marcador de posición 
`{{value}}` para obtener el valor actual sin procesar.

```java
ProgressBar bar = new ProgressBar(15, "Descargando: {{x}}%");
```

## Estilo {#styling}

### Temas {#themes}

El componente `ProgressBar` viene con <JavadocLink type="foundation" location="com/webforj/component/Theme"> temas </JavadocLink> incorporados para un rápido estilizado sin el uso de CSS. Estos temas son estilos predefinidos que pueden aplicarse a botones para cambiar su apariencia y presentación visual. 
Ofrecen una forma rápida y consistente de personalizar el aspecto de los ProgressBars a lo largo de una aplicación. 

<ComponentDemo 
path='/webforj/progressbarthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarThemesView.java'
height='320px'
/>

<TableBuilder name="ProgressBar" />

## Mejores prácticas {#best-practices}

- **Usar valores mínimos y máximos apropiados**: Establecer los valores mínimos y máximos para reflejar con precisión el rango de la tarea.
- **Actualizar el progreso regularmente**: Actualizar continuamente el valor de progreso para proporcionar retroalimentación en tiempo real a los usuarios.
- **Utilizar estado indeterminado para duraciones desconocidas**: Usar el estado indeterminado para tareas con duraciones impredecibles para indicar progreso en curso.
- **Mostrar texto para una mejor retroalimentación del usuario**: Mostrar texto en la barra de progreso para ofrecer contexto adicional sobre el progreso de la tarea.
