---
sidebar_position: 3
title: Component Basics
slug: basics
draft: false
_i18n_hash: 0a9127dc9219a32aeb1eef280b386d77
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/>

Los componentes son bloques de construcción fundamentales que se pueden agregar a una ventana, proporcionando funcionalidad de interfaz de usuario y comportamiento personalizado. En webforJ, la clase `Component` sirve como base para todos los componentes dentro del motor.

## Gestión del ciclo de vida {#lifecycle-management}

Comprender el ciclo de vida del componente es esencial para crear, gestionar y utilizar componentes de manera efectiva. Los dos estados del ciclo de vida siguientes tienen métodos para manipular su comportamiento. Estos métodos no deben ser llamados explícitamente por el usuario.

### Hooks de creación y destrucción {#create-and-destroy-hooks}

Todas las clases que extienden la clase `Component` son responsables de implementar la funcionalidad que se ejecutará cuando se cree el `Component`, y cuando se destruya. Esto se realiza sobrescribiendo los métodos `onCreate()` y `onDestroy()`, respectivamente.

#### `onCreate()` {#oncreate}

El método `onCreate()` se llama cuando el componente se agrega a una ventana. Crear componentes implica configurar su estado inicial y funcionalidad. Aquí es donde defines lo que el componente debe hacer cuando se crea por primera vez. Ya sea inicializando variables, configurando oyentes de eventos o realizando cualquier otra configuración, el método `onCreate()` es tu punto de entrada para personalizar el comportamiento del componente.

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
El método `onCreate()` es donde el componente y cualquier constituyente deben ser agregados a la ventana.
:::

#### `onDestroy()` {#ondestroy}

Destruir componentes es una parte esencial de la gestión de recursos y de garantizar una limpieza adecuada. Destruir un componente es necesario cuando ya no se necesita o cuando deseas liberar recursos asociados con él. Permite a un desarrollador realizar tareas de limpieza, como detener temporizadores, liberar memoria o desactivar oyentes de eventos. También permite que se llame al método `destroy()` en cualquier componente constituyente.

:::tip
El método `onDestroy()` es responsable de llamar al método `destroy()` en cualquier componente constituyente. De lo contrario, estos componentes seguirán existiendo en el DOM, pero no serán alcanzables a través de la API.
:::

### Adjunción asincrónica {#asynchronous-attachment}

El método `whenAttached()` permite que la funcionalidad se ejecute después de que un componente haya sido agregado a una ventana. Este método devuelve un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, que permite que un comportamiento adicional especificado se ejecute asincrónicamente una vez que el componente esté adjunto en el DOM.

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
      showMessageDialog("¡Estoy adjunto!", "Adjunción asincrónica");
    });
  
    // se llama al método onCreate()
    window.add(button); 
  }
}
```

### Observadores {#observers}

Los observadores juegan un papel vital en el seguimiento de los eventos del ciclo de vida del componente. Los observadores se pueden agregar y eliminar utilizando los métodos `addLifecycleObserver()` y `removeLifecycleObserver()`, y reciben notificaciones sobre eventos como la creación y destrucción de componentes.

Al agregar observadores, puedes tomar acciones cuando un componente es creado o destruido. Esto es particularmente útil para implementar lógica personalizada o manejar escenarios específicos basados en eventos de componentes.

```java
Button button = new Button();
button.addLifecycleObserver((button, lifecycleEvent) -> {
  if (lifecycleEvent == ComponentLifecycleObserver.LifecycleEvent.DESTROY) {
    // lógica implementada para ejecutar cuando se destruye el botón
  }
});
```

## Propiedades del componente {#component-properties}

### Identificadores de componentes {#component-identifiers}

Los IDs de componente sirven como identificadores únicos para los componentes, permitiendo interactuar con ellos y gestionar su estado de manera efectiva.

#### ID de componente del lado del servidor {#server-side-component-id}

Cada componente creado a partir de la clase `Component` se le asigna un identificador del lado del servidor automáticamente. Los IDs del lado del servidor son esenciales para el seguimiento interno e identificación de componentes dentro del marco. Puedes recuperar el ID del componente del lado del servidor utilizando el método `getComponentId()`.

Esto puede ser útil en muchas situaciones donde tener un identificador único del lado del servidor es necesario, como consultar un componente específico dentro de un contenedor.

#### ID de componente del lado del cliente {#client-side-component-id}

Los IDs del lado del cliente permiten al usuario obtener la representación del cliente del componente del servidor creado en Java. Todos los componentes proporcionados por webforJ tienen una implementación de este ID proporcionada. Si deseas obtener acceso y usar el componente del lado del cliente, puedes ejecutar `object.get()` con el ID del cliente para obtener el componente del cliente deseado.

:::important
Este ID **no** es el atributo ID del elemento en el DOM.
:::

En el siguiente ejemplo, un evento `onClick` se agrega a un botón, que luego se activa llamando al método en el componente del cliente después de haberlo obtenido utilizando el método `object.get()`.

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

La clase `Component` te permite incluir información adicional dentro del componente utilizando el método `setUserData()`. Esta información es accesible solo en el lado del servidor del componente a través del método `getUserData()`, y no se envía al cliente.

Esto es bastante útil cuando hay información que debe ser incluida con un componente, y cuando esa información debe ser accesible sin hacer un viaje al cliente para recuperarla.
