---
title: Toast
sidebar_position: 140
description: >-
  Show transient notifications with the Toast component, configuring duration,
  theme, and placement via Toast.show or open.
_i18n_hash: e0312bf77de08272221f84c9c231c2df
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

Un `Toast` es una notificación pequeña y temporal que aparece para dar retroalimentación a los usuarios sobre una acción o evento. Los Toasts muestran mensajes como confirmaciones de éxito, advertencias o errores sin interrumpir el flujo de trabajo actual, y desaparecen automáticamente después de un tiempo establecido.

<!-- INTRO_END -->

El método `Toast.show()` crea un `Toast`, lo añade al `Frame`, y lo muestra en una sola línea de código. Pasa parámetros a `show()` para configurar el `Toast` que aparece:

```java
Toast.show("¡Operación completada con éxito!", Theme.SUCCESS);
```

Si deseas un mayor control sobre el componente, también puedes crear un `Toast` con un constructor estándar y usar el método `open()` para mostrarlo.

```java
Toast toast = new Toast("¡Operación completada con éxito!", 3000, Theme.SUCCESS, Placement.TOP);
toast.open();
```

<ComponentDemo
path='/webforj/toast'
files={[
  'src/main/java/com/webforj/samples/views/toast/ToastView.java',
  'src/main/frontend/css/toast/toastTheme.css',
]}
height='200px'
/>

:::info Comportamiento Predeterminado
A diferencia de otros componentes, un `Toast` no necesita ser agregado explícitamente a un contenedor como un `Frame`. Cuando llamas al método `open()`, el `Toast` se adjunta automáticamente al primer `Frame` de la aplicación.
:::

Los Toasts son versátiles y proporcionan notificaciones sutiles para retroalimentación en tiempo real. Por ejemplo:

- **Retroalimentación en tiempo real** para acciones como envíos de formularios, guardado de datos o errores.
- **Temas personalizables** para diferenciar entre mensajes de éxito, error, advertencia o información.
- **Opciones de colocación flexibles** para mostrar notificaciones en diferentes áreas de la pantalla sin interrumpir el flujo de trabajo del usuario.

## Duración {#duration}

Puedes configurar las notificaciones de `Toast` para que desaparezcan después de un tiempo establecido o para que persistan en la pantalla hasta que se desechen, dependiendo de tus necesidades. Puedes personalizar la duración con el método `setDuration()`, o simplemente suministrar un parámetro de duración al constructor o al método `show()`.

:::info Duración Predeterminada
Por defecto, un `Toast` se cierra automáticamente después de 5000 milisegundos.
:::

```java
Toast toast = new Toast("Notificación de Ejemplo");
toast.setDuration(10000);
toast.open();
```

### Toasts Persistentes {#persistent-toasts}

Puedes crear un `Toast` persistente estableciendo una duración negativa. Las notificaciones de `Toast` persistentes no se cerrarán automáticamente, lo que puede ser útil para alertas críticas o en casos donde se requiere alguna interacción o reconocimiento del usuario.

:::caution
Ten cuidado con las notificaciones de `Toast` persistentes, y asegúrate de proporcionar una forma para que el usuario cierre la notificación. Usa el método `close()` para ocultar el `Toast` una vez que el usuario lo haya reconocido o haya completado cualquier interacción requerida.
:::

```java
Toast toast = new Toast("¡Operación completada con éxito!", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## Colocación {#placement}

Con el componente `Toast` de webforJ, puedes elegir dónde aparece la notificación en la pantalla para adaptarse al diseño y requisitos de usabilidad de tu aplicación. Por defecto, las notificaciones de `Toast` aparecen en la parte inferior central de la pantalla.

Puedes establecer la `colocación` de una notificación de Toast con el método `setPlacement` usando el enum `Toast.Placement` con uno de los siguientes valores:

- **BOTTOM**: Coloca la notificación en el centro inferior de la pantalla.
- **BOTTOM_LEFT**: Coloca la notificación en la esquina inferior izquierda de la pantalla.
- **BOTTOM_RIGHT**: Coloca la notificación en la esquina inferior derecha de la pantalla.
- **TOP**: Coloca la notificación en el centro superior de la pantalla.
- **TOP_LEFT**: Coloca la notificación en la esquina superior izquierda de la pantalla.
- **TOP_RIGHT**: Coloca la notificación en la esquina superior derecha de la pantalla.

Estas opciones te permiten controlar la colocación de la notificación de `Toast` según el diseño y las necesidades de usabilidad de tu aplicación.

```java
Toast toast = new Toast("Notificación de Ejemplo");
toast.setPlacement(Toast.Placement.TOP_LEFT);
toast.open();
```

<ComponentDemo
path='/webforj/toastplacement'
files={['src/main/java/com/webforj/samples/views/toast/ToastPlacementView.java']}
height='600px'
/>

Al personalizar la colocación de tus notificaciones de `Toast`, puedes asegurarte de que los usuarios reciban información de una manera que sea apropiada para cualquier aplicación, diseño de pantalla y contexto dados.

## Apilamiento {#stacking}

El componente `Toast` puede mostrar múltiples notificaciones simultáneamente, apilándolas verticalmente según su colocación. Las notificaciones más nuevas aparecen más cerca del borde de colocación, empujando las notificaciones más antiguas más lejos. Esto asegura que los usuarios no se pierdan información importante, incluso cuando hay mucho sucediendo.

## Acciones e Interactividad {#actions-and-interactivity}

Aunque las notificaciones `Toast` no requieren interacción del usuario por defecto, webforJ te permite agregar botones u otros elementos interactivos para hacerlas más útiles que simples notificaciones.

<ComponentDemo
path='/webforj/toastcookies'
files={['src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java']}
height='350px'
/>

Al agregar este tipo de interactividad, puedes dar a los usuarios la capacidad de manejar tareas y realizar acciones sin navegar fuera de su pantalla actual, transformando una notificación de `Toast` en un canal valioso de interacción y compromiso.

## Estilización {#styling}

Puedes estilizar las notificaciones de `Toast` con temas, igual que otros componentes de webforJ, proporcionando a los usuarios valiosa información sobre el tipo de información que se muestra, y creando un estilo consistente en toda tu aplicación. Puedes establecer el tema al crear el `Toast` o usar el método `setTheme()`.

```java
Toast toast = new Toast("Notificación de Ejemplo", Theme.INFO);
```

```java
Toast toast = new Toast("Notificación de Ejemplo");
toast.setTheme(Theme.INFO);
```

### Temas Personalizados {#custom-themes}

Además de usar temas integrados, puedes crear tus propios temas personalizados para las notificaciones de `Toast`. Esto permite una experiencia de usuario más personalizada y de marca, dándote control total sobre el estilo general del `Toast`.

Para añadir un tema personalizado a un `Toast`, puedes definir variables CSS personalizadas, que modifican la apariencia del componente. El siguiente ejemplo muestra cómo crear un `Toast` con un tema personalizado usando webforJ.

:::info Objetivación de `Toast`
Dado que el `Toast` no se encuentra en una posición específica en el DOM, puedes enfocarlo usando variables CSS. Estas variables facilitan la aplicación de estilos personalizados consistentes a todas las notificaciones de Toast.
:::

<ComponentDemo
path='/webforj/toasttheme'
files={[
  'src/main/java/com/webforj/samples/views/toast/ToastThemeView.java',
  'src/main/frontend/css/toast/toastTheme.css',
]}
height='200px'
/>

<TableBuilder name="Toast" />
