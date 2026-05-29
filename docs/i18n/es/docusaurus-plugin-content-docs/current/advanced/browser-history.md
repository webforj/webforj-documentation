---
title: Browser History
sidebar_position: 30
_i18n_hash: 918006c1e505baa4bbffbfb32eb3d9d7
---
<DocChip chip='since' label='24.12' />
<JavadocLink type="foundation" location="com/webforj/router/history/BrowserHistory" top='true'/>

La clase `BrowserHistory` en webforJ proporciona una API de alto nivel para interactuar con el historial del navegador. El historial del navegador permite que las aplicaciones web realicen un seguimiento de la navegación del usuario dentro de la aplicación. Al utilizar el historial del navegador, los desarrolladores pueden habilitar funciones como la navegación hacia atrás y hacia adelante, la preservación del estado y la gestión dinámica de URL sin requerir recargas completas de página.

## Navegando a través del historial {#navigating-through-history}

Gestionar el historial del navegador es una característica fundamental de la mayoría de las aplicaciones web. La API `BrowserHistory` permite a los desarrolladores controlar cómo los usuarios navegan a través de las páginas y estados de sus aplicaciones, imitando o alterando el comportamiento estándar del navegador.

### Inicializando o recuperando una instancia de historial {#initializing-or-retrieving-a-history-instance}

Para usar la API `BrowserHistory`, tienes dos opciones principales para obtener una instancia de historial:

1) **Crear un nuevo objeto de historial**: Si estás trabajando de manera independiente de un contexto de enrutamiento, puedes crear una nueva instancia de la clase `BrowserHistory` directamente.

```java
BrowserHistory history = new BrowserHistory();
```
Este enfoque es adecuado para escenarios donde necesitas gestionar el historial explícitamente fuera de un marco de enrutamiento.

2) **Recuperar el historial del `Router`**: Si tu aplicación utiliza la [solución de enrutamiento](../routing/overview) de webforJ, el componente `Router` crea y gestiona una instancia compartida de `BrowserHistory`. Puedes acceder a esta instancia directamente desde el enrutador para un enfoque consistente de gestión del historial en toda tu aplicación.

```java
BrowserHistory history = Router.getCurrent().getHistory();
```
Este método se recomienda cuando tu aplicación depende del enrutamiento, ya que mantiene la consistencia en la gestión del historial a través de todas las vistas y acciones de navegación.

### Gestionando el historial {#managing-history}
Los siguientes métodos se pueden usar para la navegación por el historial en una aplicación webforJ:

- `back()`: Mueve el historial del navegador un paso hacia atrás, simulando que un usuario presiona el botón de retroceso en su navegador. Si no hay más entradas en la pila de historial, se queda en la página actual.

  ```java
  history.back();
  ```

- `forward()`: Mueve el historial del navegador un paso hacia adelante, simulando que un usuario presiona el botón de avanzar en su navegador. Esto solo funciona si hay una entrada adelante en la pila de historial.

  ```java
  history.forward();
  ```

- `go(int index)`: Navega a un punto específico en la pila de historial según un índice. Un número positivo avanza, un número negativo retrocede y cero recarga la página actual. Este método proporciona un control más granular en comparación con `back()` y `forward()`.

  ```java
  history.go(-2); // Retrocede dos entradas en la pila de historial
  ```

- `size()`: Recupera el número total de entradas en la pila de historial de la sesión, incluidas la página cargada actualmente. Esto puede ser útil para comprender el camino de navegación del usuario o para implementar controles de navegación personalizados.

  ```java
  int historySize = history.size();
  System.out.println("Longitud del historial: " + historySize);
  ```

- `getLocation()`: Retorna la ruta URL actual relativa al origen de la aplicación. Este método ayuda a los desarrolladores a obtener la ruta actual, lo que es útil para gestionar el enrutamiento basado en URL en aplicaciones de una sola página.

  ```java
  Optional<Location> location = history.getLocation();
  location.ifPresent(loc -> System.out.println("Ruta Actual: " + loc.getFullURI()));
  ```

Entender cómo navegar de manera eficiente es la piedra angular para construir aplicaciones dinámicas. Una vez que tienes los fundamentos de la navegación, es esencial saber cómo acceder y actualizar las URL asociadas con estos eventos de navegación.

## Accediendo y actualizando la URL {#accessing-and-updating-url}

Un aspecto fundamental de navegar y gestionar el historial del navegador es poder acceder y actualizar la ruta URL actual de manera eficiente. Esto es esencial en las aplicaciones web modernas, donde los cambios de URL corresponden a diferentes vistas o estados dentro de la aplicación. La API `BrowserHistory` ofrece una manera simple de recuperar y manipular la ruta actual relativa a la raíz de la aplicación.

:::tip webforJ `Router`
Consulta el [artículo de `Router`](../routing/overview) para aprender más sobre la gestión integral de URL y rutas.
:::

`getLocation()` recupera la ruta URL actual relativa al origen de la aplicación. El método `getLocation()` retorna un `Optional<Location>`, lo que te permite obtener la porción de la ruta de la URL sin el dominio.

```java
Optional<Location> location = history.getLocation();
location.ifPresent(loc -> System.out.println("Ruta Actual: " + loc.getFullURI()));
```

## Gestionando el estado {#managing-state}

`BrowserHistory` te permite guardar y gestionar información de estado personalizada utilizando los métodos `pushState()` y `replaceState()`. Al utilizar métodos de gestión del estado, puedes controlar qué información se almacena como parte de la entrada del historial, lo que ayuda a mantener una experiencia de usuario consistente al navegar hacia atrás y hacia adelante dentro de tu aplicación. Los siguientes métodos se pueden usar para gestionar el estado en tu aplicación webforJ.

- `pushState(Object state, Location location)`: Agrega una nueva entrada a la pila de historial. Acepta un objeto de estado y un objeto `Location` que representa la nueva URL.

```java
Location location = new Location("/new-page");
history.pushState(myStateObject, location);
```

- `replaceState(Object state, Location location)`: Reemplaza la entrada actual del historial. Esto no crea una nueva entrada en la pila como el método anterior.

```java
Location location = new Location("/updated-page");
history.replaceState(myStateObject, location);
```

- `getState(Class<T> classOfT)`: Recupera el objeto de estado asociado con la entrada actual del historial. Este método devuelve un Optional que contiene el objeto de estado, que se deserializa en la clase especificada.

```java
Optional<MyState> currentState = history.getState(MyState.class);
currentState.ifPresent(state -> System.out.println("Página Actual: " + state.getViewName()));
```

### Escuchando cambios en el estado {#listening-for-state-changes}
La clase `BrowserHistory` proporciona la capacidad de registrar oyentes de eventos que responden a cambios en el estado del historial.

El método `addHistoryStateChangeListener(EventListener<HistoryStateChangeEvent> listener)` registra un oyente que se activa cuando el estado cambia, como cuando el usuario hace clic en los botones de retroceso o avanzar del navegador. Este método configura un oyente para el evento `popstate` del navegador, lo que permite que tu aplicación responda a las acciones del usuario o a los cambios de estado activados programáticamente.

```java
history.addHistoryStateChangeListener(event -> {
  System.out.println("Estado del historial cambiado a: " + event.getLocation().getFullURI());
});
```

Gestionar eficazmente el estado te permite crear aplicaciones que responden dinámicamente a las acciones del usuario. Los usuarios pueden navegar a través de tu aplicación sin perder el contexto, lo que proporciona una experiencia más fluida e intuitiva. Además, guardar el estado permite características avanzadas como restaurar posiciones de vista, mantener configuraciones de filtro o clasificación, y soportar enlaces profundos, todo lo cual contribuye a una aplicación más atractiva y fiable.
