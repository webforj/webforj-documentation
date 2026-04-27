---
title: Toast
sidebar_position: 140
_i18n_hash: c98ff64ae02ebe46d84c803492685d05
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

Un `Toast` es una notificación pequeña y temporal que aparece para dar a los usuarios retroalimentación sobre una acción o evento. Los Toasts presentan mensajes como confirmaciones de éxito, advertencias o errores sin interrumpir el flujo de trabajo actual, y desaparecen automáticamente después de un tiempo determinado.

<!-- INTRO_END -->

## Básicos {#basics}

webforJ proporciona una forma rápida y fácil de crear un componente `Toast` en una sola línea de código con el método `Toast.show()`, que crea un componente `Toast`, lo añade al `Frame` y lo muestra. Puedes pasar parámetros al método `show` para configurar el `Toast` mostrado:

```java
Toast.show("¡Operación completada con éxito!", Theme.SUCCESS);
```

Si deseas un control más detallado sobre el componente, también puedes crear un `Toast` con un constructor estándar y usar el método `open()` para mostrarlo.

```java
Toast toast = new Toast("¡Operación completada con éxito!", 3000, Theme.SUCCESS, Placement.TOP);
toast.open();
```

<ComponentDemo 
path='/webforj/toast?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastView.java'
cssURL='/css/toast/toastTheme.css'
height='200px'
/>

:::info Comportamiento Predeterminado
A diferencia de otros componentes, un `Toast` no necesita ser añadido explícitamente a un contenedor como un `Frame`. Cuando llamas al método `open()`, el `Toast` se adjunta automáticamente al primer `Frame` de la aplicación.
:::

Los Toasts son versátiles y proporcionan notificaciones sutiles para retroalimentación en tiempo real. Por ejemplo:

- **Retroalimentación en tiempo real** para acciones como envíos de formularios, guardados de datos o errores.
- **Temas personalizables** para diferenciar entre mensajes de éxito, error, advertencia o informativos.
- **Opciones de colocación flexibles** para mostrar notificaciones en diferentes áreas de la pantalla sin interrumpir el flujo de trabajo del usuario.

## Duración {#duration}

Puedes configurar las notificaciones `Toast` para que desaparezcan después de un tiempo determinado o persistan en la pantalla hasta que sean desechadas, dependiendo de tus necesidades. Puedes personalizar la duración con el método `setDuration()`, o simplemente suministrar un parámetro de duración al constructor o al método `show()`.

:::info Duración Predeterminada
Por defecto, un `Toast` se cierra automáticamente después de 5000 milisegundos.
:::

```java
Toast toast = new Toast("Notificación de Muestra");
toast.setDuration(10000);
toast.open();
```

### Toasts persistentes {#persistent-toasts}

Puedes crear un `Toast` persistente configurando una duración negativa. Las notificaciones `Toast` persistentes no se cierran automáticamente, lo que puede ser útil para alertas críticas o en casos donde se requiere alguna interacción o reconocimiento por parte del usuario.

:::caution
Ten cuidado con las notificaciones `Toast` persistentes, y asegúrate de proporcionar una forma para que el usuario descarte la notificación. Usa el método `close()` para ocultar el `Toast` una vez que el usuario lo haya reconocido o completado cualquier interacción requerida.
:::

```java
Toast toast = new Toast("¡Operación completada con éxito!", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## Colocación {#placement}

Con el componente `Toast` de webforJ, puedes elegir dónde aparecerá la notificación en la pantalla para adaptarse al diseño y los requisitos de usabilidad de tu aplicación. Por defecto, las notificaciones `Toast` aparecen en la parte inferior central de la pantalla.

Puedes establecer la `colocación` de una notificación `Toast` con el método `setPlacement` utilizando el enumerador `Toast.Placement` con uno de los siguientes valores:

- **BOTTOM**: Coloca la notificación en el centro inferior de la pantalla.
- **BOTTOM_LEFT**: Coloca la notificación en la esquina inferior izquierda de la pantalla.
- **BOTTOM_RIGHT**: Coloca la notificación en la esquina inferior derecha de la pantalla.
- **TOP**: Coloca la notificación en el centro superior de la pantalla.
- **TOP_LEFT**: Coloca la notificación en la esquina superior izquierda de la pantalla.
- **TOP_RIGHT**: Coloca la notificación en la esquina superior derecha de la pantalla.

Estas opciones te permiten controlar la colocación de la notificación `Toast` según las necesidades de diseño y usabilidad de tu aplicación.

```java
Toast toast = new Toast("Notificación de Muestra");
toast.setPlacement(Toast.Placement.TOP_LEFT);
toast.open();
```

<ComponentDemo 
path='/webforj/toastplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastPlacementView.java'
height='600px'
/>

Al personalizar la colocación de tus notificaciones `Toast`, puedes asegurarte de que los usuarios reciban información de una manera que sea apropiada para cualquier aplicación, diseño de pantalla y contexto dados.

## Apilamiento {#stacking}

El componente `Toast` puede mostrar múltiples notificaciones simultáneamente, apilándolas verticalmente en función de su colocación. Las notificaciones más nuevas aparecen más cerca del borde de colocación, empujando las notificaciones más antiguas más lejos. Esto garantiza que los usuarios no se pierdan información importante, incluso cuando hay mucho en curso.

## Acciones e Interactividad {#actions-and-interactivity}

Aunque las notificaciones `Toast` no requieren interacción del usuario por defecto, webforJ te permite agregar botones u otros elementos interactivos para hacerlas más útiles que simples notificaciones.

<ComponentDemo 
path='/webforj/toastcookies?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java'
height='350px'
/>

Al agregar este tipo de interactividad, puedes darle a los usuarios la capacidad de manejar tareas y realizar acciones sin navegar fuera de su pantalla actual, transformando una notificación `Toast` en un valioso canal de interacción y compromiso. 

## Estilización {#styling}

Puedes estilizar las notificaciones `Toast` con temas al igual que otros componentes de webforJ, proporcionando a los usuarios contexto valioso sobre el tipo de información que se muestra y creando un estilo consistente en toda tu aplicación. Puedes definir el tema al crear el `Toast` o utilizar el método `setTheme()`.

```java
Toast toast = new Toast("Notificación de Muestra", Theme.INFO);
```

```java
Toast toast = new Toast("Notificación de Muestra");
toast.setTheme(Theme.INFO);
```

### Temas personalizados {#custom-themes}

Además de usar temas incorporados, puedes crear tus propios temas personalizados para las notificaciones `Toast`. Esto permite una experiencia de usuario más personalizada y con marca, dándote control total sobre el estilo general del `Toast`.

Para agregar un tema personalizado a un `Toast`, puedes definir variables CSS personalizadas, que modifican la apariencia del componente. El siguiente ejemplo demuestra cómo crear un `Toast` con un tema personalizado utilizando webforJ.

:::info `Toast` Objetivos
Dado que el `Toast` no está ubicado en una posición específica en el DOM, puedes dirigirte a él utilizando variables CSS. Estas variables facilitan la aplicación de estilos personalizados consistentes en todas las notificaciones Toast.
:::

<ComponentDemo 
path='/webforj/toasttheme?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastThemeView.java'
cssURL='/css/toast/toastTheme.css'
height='200px'
/>

<TableBuilder name="Toast" />
