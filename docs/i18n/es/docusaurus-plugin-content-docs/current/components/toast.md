---
title: Toast
sidebar_position: 140
description: >-
  Show transient notifications with the Toast component, configuring duration,
  theme, and placement via Toast.show or open.
_i18n_hash: d46665cad03749940ad45e5be8b3991d
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

Un `Toast` es una pequeña notificación temporal que aparece para dar retroalimentación a los usuarios sobre una acción o evento. Los Toasts muestran mensajes como confirmaciones de éxito, advertencias o errores sin interrumpir el flujo de trabajo actual, y desaparecen automáticamente después de un tiempo establecido.

<!-- INTRO_END -->

El método `Toast.show()` crea un `Toast`, lo agrega al `Frame` y lo muestra en una sola línea de código. Pasa parámetros a `show()` para configurar el `Toast` que aparece:

```java
Toast.show("¡Operación completada con éxito!", Theme.SUCCESS);
```

Si deseas un control más detallado sobre el componente, también puedes crear un `Toast` con un constructor estándar y usar el método `open()` para mostrarlo.

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

- **Retroalimentación en tiempo real** para acciones como envíos de formularios, guardados de datos o errores.
- **Temas personalizables** para diferenciar entre mensajes de éxito, error, advertencia o información.
- **Opciones de colocación flexibles** para mostrar notificaciones en diferentes áreas de la pantalla sin interrumpir el flujo de trabajo del usuario.

## Duración {#duration}

Puedes configurar las notificaciones de `Toast` para que desaparezcan después de un tiempo establecido o persistan en la pantalla hasta que sean desestimadas, según tus necesidades. Puedes personalizar la duración con el método `setDuration()`, o simplemente proporcionar un parámetro de duración al constructor o al método `show()`.

:::info Duración Predeterminada
Por defecto, un `Toast` se cierra automáticamente después de 5000 milisegundos.
:::

```java
Toast toast = new Toast("Notificación de ejemplo");
toast.setDuration(10000);
toast.open();
```

### Toasts persistentes {#persistent-toasts}

Puedes crear un `Toast` persistente estableciendo una duración negativa. Las notificaciones de `Toast` persistentes no se cerrarán automáticamente, lo que puede ser útil para alertas críticas o en casos donde se requiera algún tipo de interacción o reconocimiento por parte del usuario.

:::caution
Ten cuidado con las notificaciones `Toast` persistentes, y asegúrate de proporcionar una forma para que el usuario pueda cerrar la notificación. Usa el método `close()` para ocultar el `Toast` una vez que el usuario lo haya reconocido o completado cualquier interacción necesaria.
:::

```java
Toast toast = new Toast("¡Operación completada con éxito!", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## Colocación {#placement}

Con el componente `Toast` de webforJ, puedes elegir dónde aparecerá la notificación en la pantalla para adaptarse al diseño y requisitos de usabilidad de tu aplicación. Por defecto, las notificaciones de `Toast` aparecen en la parte inferior central de la pantalla.

Puedes establecer la `colocación` de una notificación `Toast` con el método `setPlacement` utilizando el enumerado `Toast.Placement` con uno de los siguientes valores:

- **BOTTOM**: Coloca la notificación en el centro inferior de la pantalla.
- **BOTTOM_LEFT**: Coloca la notificación en la esquina inferior izquierda de la pantalla.
- **BOTTOM_RIGHT**: Coloca la notificación en la esquina inferior derecha de la pantalla.
- **CENTER**: Coloca la notificación en el centro de la pantalla.
- **TOP**: Coloca la notificación en el centro superior de la pantalla.
- **TOP_LEFT**: Coloca la notificación en la esquina superior izquierda de la pantalla.
- **TOP_RIGHT**: Coloca la notificación en la esquina superior derecha de la pantalla.

Estas opciones te permiten controlar la colocación de la notificación `Toast` según el diseño y las necesidades de usabilidad de tu aplicación.

```java
Toast toast = new Toast("Notificación de ejemplo");
toast.setPlacement(Toast.Placement.TOP_LEFT);
toast.open();
```

<ComponentDemo
path='/webforj/toastplacement'
files={['src/main/java/com/webforj/samples/views/toast/ToastPlacementView.java']}
height='600px'
/>

Al personalizar la colocación de tus notificaciones `Toast`, puedes asegurarte de que los usuarios reciban información de una manera que sea apropiada para cualquier aplicación, diseño de pantalla y contexto.

## Apilamiento {#stacking}

El componente `Toast` puede mostrar múltiples notificaciones simultáneamente, apilándolas verticalmente según su colocación. Las notificaciones más nuevas aparecen más cerca del borde de colocación, empujando las notificaciones más antiguas más lejos. Esto asegura que los usuarios no se pierdan información importante, incluso cuando hay mucho sucediendo.

## Acciones e Interactividad {#actions-and-interactivity}

Aunque las notificaciones `Toast` no requieren interacción del usuario por defecto, webforJ te permite agregar botones u otros elementos interactivos para hacerlas más útiles que simples notificaciones.

<ComponentDemo
path='/webforj/toastcookies'
files={['src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java']}
height='350px'
/>

Al agregar este tipo de interactividad, puedes dar a los usuarios la capacidad de manejar tareas y realizar acciones sin navegar lejos de su pantalla actual, transformando una notificación `Toast` en un valioso canal de interacción y participación.

## Estilización {#styling}

Puedes estilizar las notificaciones `Toast` con temas al igual que otros componentes de webforJ, proporcionando a los usuarios un contexto valioso sobre el tipo de información que se está mostrando y creando un estilo consistente en toda tu aplicación. Puedes establecer el tema al crear el Toast o usar el método `setTheme()`.

```java
Toast toast = new Toast("Notificación de ejemplo", Theme.INFO);
```

```java
Toast toast = new Toast("Notificación de ejemplo");
toast.setTheme(Theme.INFO);
```

### Temas personalizados {#custom-themes}

Además de usar temas integrados, puedes crear tus propios temas personalizados para las notificaciones `Toast`. Esto permite una experiencia de usuario más personalizada y de marca, dándote el control total sobre la estilización general del `Toast`.

Para agregar un tema personalizado a un `Toast`, puedes definir variables CSS personalizadas que modifiquen la apariencia del componente. El siguiente ejemplo demuestra cómo crear un `Toast` con un tema personalizado utilizando webforJ.

:::info `Toast` Apuntando
Dado que el `Toast` no está ubicado en una posición específica en el DOM, puedes dirigirte a él usando variables CSS. Estas variables facilitan la aplicación de estilos personalizados consistentes en todas las notificaciones Toast.
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
