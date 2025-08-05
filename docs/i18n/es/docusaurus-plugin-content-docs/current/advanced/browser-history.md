---
title: Browser History
sidebar_position: 20
_i18n_hash: 877c6513ffd8f2b3ed8d4199bc2f5b39
---
<DocChip chip='since' label='24.12' />
<JavadocLink type="foundation" location="com/webforj/router/history/BrowserHistory" top='true'/>

La clase `BrowserHistory` en webforJ proporciona una API de alto nivel para interactuar con el historial del navegador. El historial del navegador permite a las aplicaciones web realizar un seguimiento de la navegación del usuario dentro de la aplicación. Al aprovechar el historial del navegador, los desarrolladores pueden habilitar funciones como la navegación hacia atrás y hacia adelante, la preservación del estado y la gestión dinámica de URL sin requerir recargas completas de página.

## Navegando a través del historial {#navigating-through-history}

Gestionar el historial del navegador es una característica fundamental para la mayoría de las aplicaciones web. La API `BrowserHistory` permite a los desarrolladores controlar cómo los usuarios navegan a través de las páginas y estados de sus aplicaciones, imitando o alterando el comportamiento estándar del navegador.

### Inicializando o recuperando una instancia de historial {#initializing-or-retrieving-a-history-instance}

Para utilizar la API `BrowserHistory`, tienes dos opciones principales para obtener una instancia de historial:

1) **Creando un nuevo objeto de historial**: Si estás trabajando de forma independiente de un contexto de enrutamiento, puedes crear una nueva instancia de la clase `BrowserHistory` directamente.

```java
BrowserHistory history = new BrowserHistory();
```
Este enfoque es adecuado para escenarios donde necesitas gestionar el historial explícitamente fuera de un marco de enrutamiento.

2) **Recuperando el historial del `Router`**: Si tu aplicación utiliza la [solución de enrutamiento](../routing/overview) de webforJ, el componente `Router` crea y gestiona una instancia compartida de `BrowserHistory`. Puedes acceder a esta instancia directamente desde el router, asegurando un enfoque consistente de gestión del historial en toda tu aplicación.

```java
BrowserHistory history = Router.getCurrent().getHistory();
```
Este método se recomienda cuando tu aplicación depende del enrutamiento, ya que mantiene la consistencia en la gestión del historial en todas las vistas y acciones de navegación.

### Gestionando el historial {#managing-history}
Los siguientes métodos se pueden utilizar para la navegación en el historial en una aplicación de webforJ:

- `back()`: Mueve el historial del navegador hacia atrás un paso, simulando que un usuario presiona el botón de atrás en su navegador. Si no hay más entradas en la pila de historial, permanece en la página actual.

  ```java
  history.back();
  ```

- `forward()`: Mueve el historial del navegador hacia adelante un paso, simulando que un usuario presiona el botón de adelante en su navegador. Esto solo funciona si hay una entrada adelante en la pila de historial.

  ```java
  history.forward();
  ```

- `go(int index)`: Navega a un punto específico en la pila de historial basado en un índice. Un número positivo se mueve hacia adelante, un número negativo se mueve hacia atrás, y cero recarga la página actual. Este método proporciona un control más granular en comparación con `back()` y `forward()`.

  ```java
  history.go(-2); // Mueve hacia atrás dos entradas en la pila de historial
  ```

- `size()`: Recupera el número total de entradas en la pila de historial de la sesión, incluida la página actualmente cargada. Esto puede ser útil para comprender el camino de navegación del usuario o para implementar controles de navegación personalizados.

  ```java
  int historySize = history.size();
  System.out.println("Longitud del Historial: " + historySize);
  ```

- `getLocation()`: Devuelve la ruta de URL actual relativa al origen de la aplicación. Este método ayuda a los desarrolladores a obtener la ruta actual, lo cual es útil para gestionar el enrutamiento basado en URL en aplicaciones de una sola página.

  ```java
  Optional<Location> location = history.getLocation();
  location.ifPresent(loc -> System.out.println("Ruta Actual: " + loc.getFullURI()));
  ```

Entender cómo navegar de manera eficiente es la piedra angular para construir aplicaciones dinámicas. Una vez que comprendas los fundamentos de la navegación, es esencial saber cómo acceder y actualizar las URL asociadas con estos eventos de navegación.

## Accediendo y actualizando la URL {#accessing-and-updating-url}

Un aspecto clave de la navegación y gestión del historial del navegador es poder acceder y actualizar la ruta de URL actual de manera eficiente. Esto es esencial en las aplicaciones web modernas, donde los cambios en la URL corresponden a diferentes vistas o estados dentro de la aplicación. La API `BrowserHistory` ofrece una forma simple de recuperar y manipular la ruta actual relativa a la raíz de la aplicación.

:::tip webforJ `Router`
Consulta el [artículo `Router`](../routing/overview) para aprender más sobre la gestión integral de URLs y rutas.
:::

`getLocation()` recupera la ruta de URL actual relativa al origen de la aplicación. El método `getLocation()` devuelve un `Optional<Location>`, permitiéndote obtener la porción de ruta de la URL sin el dominio.

```java
Optional<Location> location = history.getLocation();
location.ifPresent(loc -> System.out.println("Ruta Actual: " + loc.getFullURI()));
```

## Gestionando el estado {#managing-state}

`BrowserHistory` te permite guardar y gestionar información de estado personalizada utilizando los métodos `pushState()` y `replaceState()`. Al usar métodos de gestión del estado, puedes controlar qué información se almacena como parte de la entrada del historial, lo que ayuda a mantener una experiencia de usuario consistente al navegar hacia atrás y hacia adelante en tu aplicación. Los siguientes métodos se pueden usar para gestionar el estado en tu aplicación de webforJ.

- `pushState(Object state, Location location)`: Agrega una nueva entrada a la pila de historial. Acepta un objeto de estado y un objeto `Location` que representa la nueva URL.

```java
Location location = new Location("/nueva-pagina");
history.pushState(myStateObject, location);
```

- `replaceState(Object state, Location location)`: Reemplaza la entrada actual del historial. Esto no crea una nueva entrada en la pila como el método anterior.

```java
Location location = new Location("/pagina-actualizada");
history.replaceState(myStateObject, location);
```

- `getState(Class<T> classOfT)`: Recupera el objeto de estado asociado con la entrada actual del historial. Este método devuelve un Optional que contiene el objeto de estado, que se deserializa en la clase especificada.

```java
Optional<MyState> currentState = history.getState(MyState.class);
currentState.ifPresent(state -> System.out.println("Página Actual: " + state.getViewName()));
```

### Escuchando cambios de estado {#listening-for-state-changes}
La clase `BrowserHistory` proporciona la capacidad de registrar oyentes de eventos que responden a cambios en el estado del historial.

El método `addHistoryStateChangeListener(EventListener<HistoryStateChangeEvent> listener)` registra un oyente que se activa cuando el estado cambia, como cuando el usuario hace clic en los botones de atrás o adelante del navegador. Este método configura un oyente para el evento `popstate` del navegador, permitiendo que tu aplicación responda a acciones del usuario o cambios de estado activados programáticamente.

```java
history.addHistoryStateChangeListener(event -> {
    System.out.println("El estado del historial ha cambiado a: " + event.getLocation().getFullURI());
});
```

Gestionar el estado de manera efectiva te permite crear aplicaciones que responden dinámicamente a las acciones del usuario. Los usuarios pueden navegar a través de tu aplicación sin perder el contexto, lo que contribuye a una experiencia más fluida e intuitiva. Además, guardar el estado habilita funciones avanzadas como restaurar posiciones de vista, mantener configuraciones de filtro o clasificación, y soportar enlaces profundos, todo lo cual contribuye a una aplicación más atractiva y confiable.
