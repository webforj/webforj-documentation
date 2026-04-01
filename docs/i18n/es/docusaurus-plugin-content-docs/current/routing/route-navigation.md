---
sidebar_position: 4
title: Route Navigation
_i18n_hash: 91739f35b8d47f6e90e276623864aac4
---
En webforJ, navegar entre rutas es el mecanismo principal para cambiar vistas y componentes en función de las acciones del usuario o cambios en la URL. La navegación permite a los usuarios moverse sin problemas entre diferentes partes de la aplicación sin refrescar la página. Esta navegación del lado del cliente mantiene la aplicación receptiva y fluida mientras preserva el estado de la aplicación.

## Navegación programática {#programmatic-navigation}

Puedes activar la navegación desde cualquier lugar de tu aplicación utilizando la clase `Router`. Esto permite cambios dinámicos en los componentes mostrados basados en eventos como clics de botones u otras interacciones del usuario.

Aquí tienes un ejemplo de cómo navegar a una ruta específica:

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> {
  // Lógica del componente aquí
}
```

```java
// navegar a la vista
Router.getCurrent().navigate(DashboardView.class);
```

En este ejemplo, navegar al componente `DashboardView` de manera programática provoca que se renderice el componente `DashboardView` y que la URL del navegador se actualice a `/dashboard`.

También es posible navegar a la vista pasando una nueva `Location`

```java
Router.getCurrent().navigate(new Location("/dashboard"));
```

:::tip Clase vs. Ubicación: Métodos para el Enrutamiento de Vistas
Al navegar entre vistas, los desarrolladores tienen dos opciones: pueden pasar la clase de vista o ruta, permitiendo que el router genere automáticamente la URL y renderice la vista, o pasar la ubicación directamente. Ambos métodos son válidos, pero **usar la clase de vista es el enfoque preferido** porque ofrece mejor flexibilidad para cambios futuros. Por ejemplo, si decides más tarde actualizar la ruta, solo necesitas modificar la anotación `@Route`, sin tener que cambiar ningún código que utilice la clase de vista para la navegación.
:::

### Navegación con parámetros {#navigation-with-parameters}

Cuando necesitas pasar parámetros junto con la ruta, webforJ permite incrustar parámetros en la URL. Aquí te mostramos cómo navegar a una ruta con parámetros:

```java
@Route("user/:id")
public class UserProfileView extends Composite<Div> implements DidEnterObserver {
  private final Div self = getBoundComponent();
  H1 title = new H1();

  public UserProfileView() {
    self.add(title);
  }

  public void setTile(String title) {
    this.title.setText(title);
  }

  public String getTitle() {
    return title.getText();
  }

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String id = parameters.getAlpha("id").orElse("Desconocido");
    setTile(id);
  }
}
```

```java
// navegar a la vista y pasar el id del usuario
Router.getCurrent().navigate(
  UserProfileView.class,
  ParametersBag.of("id=JohnDoe")
);
```

Esto navega a `/user/JohnDoe`, donde `JohnDoe` podría representar un ID de usuario. El componente para esta ruta puede extraer el parámetro y usarlo en consecuencia.

## Instancia de vista creada {#created-view-instance}

El método `navigate` acepta un `Consumer` de Java que se invoca una vez que la navegación se completa. El `Consumer` recibe la instancia del componente de vista creado, envuelto en un `Optional` de Java, permitiendo que el desarrollador interactúe con la vista después de una navegación exitosa.

```java
Router.getCurrent().navigate(
  UserProfileView.class,
  ParametersBag.of("id=JohnDoe"), (component) -> {
    component.ifPresent(view -> {
      console().log("El nuevo título es: " + view.getTitle());
    });
  });
```

:::info Instancias nulas
El consumidor recibe un `Optional` de Java para el componente porque podría ser `null` o no creado por varias razones. Por ejemplo, el componente puede no ser renderizado si los observadores de navegación vetan la navegación y detienen el proceso.
:::

## Opciones de navegación {#navigation-options}

La clase `NavigationOptions` permite a los desarrolladores afinar cómo se maneja la navegación dentro de la aplicación. Al establecer opciones específicas, puedes controlar el comportamiento de la navegación, como si se debe actualizar el historial del navegador, invocar observadores del ciclo de vida, o incluso activar eventos de navegación.

```java
NavigationOptions options = new NavigationOptions();
options.setUpdateHistory(false);

Router.getCurrent().navigate(
  new Location("user/JohnDoe"), options);
```

### Establecer opciones de navegación {#setting-navigation-options}

La clase `NavigationOptions` proporciona varios métodos para personalizar el comportamiento de navegación. Estos incluyen controlar cómo se manejan las rutas, si se notifica a los observadores, y cómo se actualiza el historial del navegador.

Aquí están las principales opciones de configuración disponibles dentro de `NavigationOptions`:

1. **Tipo de Navegación (`setNavigationType`)**  
   Esta opción define si la nueva ruta debe agregarse al historial del navegador o reemplazar la ruta actual.

   - **`PUSH`**: Agrega la nueva ruta a la pila de historial, preservando la ubicación actual.
   - **`REPLACE`**: Reemplaza la ruta actual en la pila de historial con la nueva ubicación, evitando que el botón de retroceso navegue a la ruta anterior.

2. **Activar Eventos (`setFireEvents`)**  
   Determina si durante la navegación se deben activar [eventos de ciclo de vida](./navigation-lifecycle/navigation-events). Por defecto, esto está establecido en `true`, y se activan eventos. Si se establece en `false`, no se activarán eventos, lo cual es útil para una navegación silenciosa.

3. **Invocar Observadores (`setInvokeObservers`)**  
   Este indicador controla si la navegación debería activar [observadores](./navigation-lifecycle/observers) dentro de los componentes navegados. Los observadores normalmente manejan eventos como la entrada o salida de rutas. Establecer esto en `false` impide que se invoquen los observadores.

4. **Actualizar Historial (`setUpdateHistory`)**  
   Cuando se establece en `false`, esta opción previene que la ubicación en el historial se actualice. Esto es útil cuando quieres cambiar la vista sin afectar la navegación hacia atrás o hacia adelante del navegador. Solo afecta a la gestión del historial, no al ciclo de vida del componente o al manejo de rutas.

5. **Objeto de Estado (`setState`)**  
   [El objeto de estado](./state-management#saving-and-restoring-state-in-browser-history) permite pasar información adicional al actualizar el historial del navegador. Este objeto se almacena en el estado del historial del navegador y se puede usar más tarde para propósitos customizados, como guardar el estado de la aplicación durante la navegación.

## Generando ubicaciones para vistas {#generating-locations-for-views}

El router puede generar la ubicación para vistas basándose en el patrón de ruta definido en la vista. También puedes proporcionar parámetros adicionales para segmentos dinámicos y requeridos en la URL. Esto puede ser útil al construir enlaces o compartir puntos de acceso directos a vistas específicas en la aplicación.

Aquí te mostramos cómo generar una `Location` basada en una clase de vista y parámetros de ruta:

```java
Class<UserProfileView> userProfileView = UserProfileView.class;
ParametersBag params = ParametersBag.of("id=JohnDoe");

Optional<Location> location = Router.getCurrent().getLocation(userProfileView, params);
console().log(location.get());
```

Esto genera un objeto `Location` con la ruta `/user/JohnDoe`, la URI completa como una cadena.
