---
sidebar_position: 1
title: Component Basics
slug: basics
draft: false
_i18n_hash: d517f6169f7ac0798ed073bb27348eb5
---
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/>

Los componentes son bloques de construcción fundamentales que se pueden agregar a una ventana, proporcionando funcionalidad de interfaz de usuario y comportamiento personalizado. En webforJ, la clase `Component` sirve como la base para todos los componentes dentro del motor.

## Gestión del ciclo de vida {#lifecycle-management}

Entender el ciclo de vida del componente es esencial para crear, gestionar y utilizar componentes de manera efectiva. Los siguientes dos estados del ciclo de vida tienen métodos para manipular su comportamiento. Estos métodos no deben ser llamados explícitamente por el usuario.

### Hooks de creación y destrucción {#create-and-destroy-hooks}

Todas las clases que extienden la clase `Component` son responsables de implementar la funcionalidad que se ejecutará cuando se cree el `Component`, y cuando se destruya. Esto se hace sobrescribiendo los métodos `onCreate()` y `onDestroy()`, respectivamente.

#### `onCreate()` {#oncreate}

El método `onCreate()` se llama cuando el componente se agrega a una ventana. Crear componentes implica establecer su estado inicial y funcionalidad. Aquí es donde defines lo que el componente debería hacer cuando se crea por primera vez. Ya sea inicializando variables, configurando oyentes de eventos o realizando cualquier otra configuración, el método `onCreate()` es tu punto de entrada para personalizar el comportamiento del componente.

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
El método `onCreate()` es donde el componente y cualquier componente constituyente deben ser añadidos a la ventana.
:::

#### `onDestroy()` {#ondestroy}

Destruir componentes es una parte esencial de la gestión de recursos y asegurar la limpieza adecuada. Destruir un componente es necesario cuando ya no se necesita o cuando deseas liberar recursos asociados a él. Esto permite a un desarrollador realizar tareas de limpieza, como detener temporizadores, liberar memoria o desasociar oyentes de eventos. También permite que se llame al método `destroy()` en cualquier componente constituyente.

:::tip
El método `onDestroy()` es responsable de llamar al método `destroy()` en cualquier componente constituyente. De lo contrario, estos componentes seguirán existiendo en el DOM, pero no serán accesibles a través de la API.
:::

### Adición asíncrona {#asynchronous-attachment}

El método `whenAttached()` permite que la funcionalidad se ejecute después de que un componente ha sido agregado a una ventana. Este método devuelve un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, que permite que se ejecute un comportamiento adicional especificado de forma asíncrona una vez que el componente está adjunto en el DOM.

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
      showMessageDialog("¡Estoy adjunto!", "Adición asíncrona");
    });
  
    // se llama al método onCreate()
    window.add(button); 
  }
}
```

### Observadores {#observers}

Los observadores juegan un papel vital en el seguimiento de los eventos del ciclo de vida del componente. Los observadores pueden ser añadidos y removidos utilizando los métodos `addLifecycleObserver()` y `removeLifecycleObserver()`, y reciben notificaciones sobre eventos como la creación y destrucción de componentes.

Al añadir observadores, puedes tomar medidas cuando un componente es creado o destruido. Esto es particularmente útil para implementar lógica personalizada o manejar escenarios específicos basados en eventos de componentes.

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

Las IDs de componente sirven como identificadores únicos para los componentes, permitiéndote interactuar con ellos y gestionar su estado de manera efectiva.

#### ID de componente del lado del servidor {#server-side-component-id}

Cada componente creado a partir de la clase `Component` se le asigna automáticamente un identificador del lado del servidor. Las IDs del lado del servidor son esenciales para el rastreo interno e identificación de componentes dentro del marco. Puedes recuperar el ID del componente del lado del servidor utilizando el método `getComponentId()`.

Esto puede ser útil en muchas situaciones donde tener un identificador único del lado del servidor es necesario, como consultar un componente específico dentro de un contenedor.

#### ID de componente del lado del cliente {#client-side-component-id}

Las IDs del lado del cliente permiten al usuario obtener la representación del cliente del componente del servidor creado en Java. Todos los componentes proporcionados por webforJ tienen una implementación de esta ID proporcionada. Si deseas acceder y usar el componente del lado del cliente, puedes ejecutar `object.get()` con la ID del cliente para obtener el componente del cliente deseado.

:::important
Esta ID **no** es el atributo ID del elemento en el DOM.
:::

En el siguiente ejemplo, un evento `onClick` se añade a un botón, que luego se activa llamando al método en el componente cliente después de haberlo obtenido usando el método `object.get()`.

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

### Datos de usuario {#user-data}

La clase `Component` te permite incluir información adicional dentro del componente utilizando el método `setUserData()`. Esta información es accesible solo en el lado del servidor del componente a través del método `getUserData()`, y no se envía al cliente.

Esto es bastante útil cuando hay información que debería ser incluida con un componente, y cuando esa información debería ser accesible sin tener que hacer un viaje al cliente para recuperarla.
