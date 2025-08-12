---
sidebar_position: 1
title: Component Basics
slug: basics
draft: false
_i18n_hash: e4d0cb9dd9f53dabda8bebe6664bf0d3
---
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/>

Los componentes son bloques de construcción fundamentales que se pueden agregar a una ventana, proporcionando funcionalidad de interfaz de usuario y comportamiento personalizado. En webforJ, la clase `Component` sirve como la base para todos los componentes dentro del motor.

## Gestión del ciclo de vida {#lifecycle-management}

Entender el ciclo de vida del componente es esencial para crear, gestionar y utilizar componentes de manera efectiva. Los siguientes dos estados del ciclo de vida tienen métodos para manipular su comportamiento. Estos métodos no deben ser llamados explícitamente por el usuario.

### Hooks de creación y destrucción {#create-and-destroy-hooks}

Todas las clases que extienden la clase `Component` son responsables de implementar la funcionalidad que se ejecutará cuando el `Component` se cree y cuando se destruya. Esto se hace al sobrescribir los métodos `onCreate()` y `onDestroy()`, respectivamente.

#### `onCreate()` {#oncreate}

El método `onCreate()` se llama cuando el componente se agrega a una ventana. Crear componentes implica configurar su estado y funcionalidad inicial. Aquí es donde defines lo que debe hacer el componente cuando se crea por primera vez. Ya sea inicializando variables, configurando oyentes de eventos o realizando cualquier otra configuración, el método `onCreate()` es tu punto de entrada para personalizar el comportamiento del componente.

Este hook recibe una instancia de ventana que permite la adición de componentes contenidos dentro del componente.

```java
@Override
protected void onCreate(Window window) {
  TextField text = new TextField();
  Button btn = new Button();

  window.add(text, btn);
}
```

:::tip
El método `onCreate()` es donde se deben agregar el componente y cualquier constituyente a la ventana.
:::

#### `onDestroy()` {#ondestroy}

Destruir componentes es una parte esencial de la gestión de recursos y garantizar la limpieza adecuada. Destruir un componente es necesario cuando ya no se necesita o cuando deseas liberar los recursos asociados con él. Permite a un desarrollador realizar tareas de limpieza, como detener temporizadores, liberar memoria o desvincular oyentes de eventos. También permite que se llame al método `destroy()` en cualquier componente constitutivo.

:::tip
El método `onDestroy()` es responsable de llamar al método `destroy()` en cualquier componente constitutivo. De lo contrario, estos componentes seguirán existiendo en el DOM, pero no estarán accesibles a través de la API.
:::

### Adición asincrónica {#asynchronous-attachment}

El método `whenAttached()` permite que se ejecute funcionalidad después de que un componente ha sido agregado a una ventana. Este método devuelve un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, que permite que se ejecute comportamiento adicional especificado de forma asincrónica una vez que el componente esté adjunto en el DOM.

:::tip
A diferencia de los tres métodos anteriores, `whenAttached()` está destinado a ser llamado explícitamente por el usuario.
:::

```java
public class Demo extends App {
  @Override
  public void run() throws WebforjException {
    Frame window = new Frame();

    Button button = new Button(); 

    /* Llamada explícita a whenAttached() que mostrará un 
    cuadro de mensaje cuando el botón esté adjunto al Frame.*/
    button.whenAttached().thenAccept( e -> {
      showMessageDialog("¡Estoy adjunto!", "Adición asincrónica");
    });
  
    // se llama al método onCreate()
    window.add(button); 
  }
}
```

### Observadores {#observers}

Los observadores juegan un papel vital en el seguimiento de los eventos del ciclo de vida del componente. Los observadores se pueden agregar y eliminar usando los métodos `addLifecycleObserver()` y `removeLifecycleObserver()`, y reciben notificaciones sobre eventos como la creación y destrucción de componentes.

Al agregar observadores, puedes tomar medidas cuando un componente es creado o destruido. Esto es particularmente útil para implementar lógica personalizada o manejar escenarios específicos basados en eventos de componentes.

```java
Button button = new Button();
button.addLifecycleObserver((button, lifecycleEvent) -> {
  if (lifecycleEvent == ComponentLifecycleObserver.LifecycleEvent.DESTROY) {
    // lógica implementada para ejecutar cuando se destruye el botón
  }
});
```

## Propiedades del componente {#component-properties}

### Identificadores de componente {#component-identifiers}

Los IDs de componentes sirven como identificadores únicos para los componentes, permitiéndote interactuar con ellos y gestionar su estado de manera efectiva.

#### ID de componente del lado del servidor {#server-side-component-id}

Cada componente creado a partir de la clase `Component` se asigna automáticamente un identificador del lado del servidor. Los IDs del lado del servidor son esenciales para el seguimiento e identificación internos de los componentes dentro del marco. Puedes recuperar el ID del componente del lado del servidor utilizando el método `getComponentId()`.

Esto puede ser útil en muchas situaciones donde tener un identificador único del lado del servidor es necesario, como consultar un componente específico dentro de un contenedor.

#### ID de componente del lado del cliente {#client-side-component-id}

Los IDs del lado del cliente permiten al usuario obtener la representación del cliente del componente del servidor creado en Java. Todos los componentes webforJ proporcionados tienen una implementación de este ID disponible. Si deseas obtener acceso y usar el componente del lado del cliente, puedes ejecutar `object.get()` con el ID del cliente para obtener el componente del cliente deseado.

:::important
Este ID **no** es el atributo ID del elemento en el DOM.
:::

En el siguiente ejemplo, se agrega un evento `onClick` a un botón, que luego se activa llamando al método en el componente del cliente después de que se obtiene utilizando el método `object.get()`.

```java
@Override
public void run() throws WebforjException {
  Frame frame = new Frame();
  Button btn = new Button("Haz clic en mí");
  btn.onClick(e -> {
    showMessageDialog("Se hizo clic en el botón", "Ocurrió un evento");
  });

  btn.whenAttached().thenAccept(e -> {
    getPage().executeJs("objects.get('" + btn.getClientComponentId() + "').click()");
  });
  frame.add(btn);
}
```

### Datos del usuario {#user-data}

La clase `Component` permite incluir información adicional dentro del componente utilizando el método `setUserData()`. Esta información solo es accesible en el lado del servidor del componente a través del método `getUserData()`, y no se envía al cliente. 

Esto es bastante útil cuando hay información que debe incluirse con un componente, y cuando esa información debe ser accesible sin hacer un viaje al cliente para recuperarla.
