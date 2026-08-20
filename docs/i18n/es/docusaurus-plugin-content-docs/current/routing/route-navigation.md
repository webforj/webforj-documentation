---
sidebar_position: 4
title: Route Navigation
description: >-
  Trigger client-side navigation programmatically with Router.navigate, pass
  parameters, and switch views without reloads.
sidebar_class_name: updated-content
_i18n_hash: 0284f2481f307d68da728d81f4b3a6a2
---
En webforJ, navegar entre rutas es el mecanismo central para cambiar vistas y componentes según las acciones del usuario o cambios en la URL. La navegación permite a los usuarios moverse sin problemas entre diferentes partes de la aplicación sin refrescar la página. Esta navegación del lado del cliente mantiene la aplicación receptiva y fluida mientras se preserva el estado de la aplicación.

## Navegación programática {#programmatic-navigation}

Puedes activar la navegación desde cualquier parte de tu aplicación utilizando la clase `Router`. Esto permite cambios dinámicos en los componentes mostrados basados en eventos como clics de botón u otras interacciones del usuario.

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

En este ejemplo, navegar al componente `DashboardView` programáticamente provoca que el componente `DashboardView` se renderice y la URL del navegador se actualice a `/dashboard`.

También es posible navegar a la vista pasando una nueva `Location`.

```java
Router.getCurrent().navigate(new Location("/dashboard"));
```

:::tip Clase vs. Location: Métodos para enrutamiento de vista
Al navegar entre vistas, los desarrolladores tienen dos opciones: pueden pasar la clase de vista o de ruta, permitiendo que el enrutador genere automáticamente la URL y renderice la vista, o pasar la ubicación directamente. Ambos métodos son válidos, pero **usar la clase de vista es el enfoque preferido** porque ofrece mejor flexibilidad para cambios futuros. Por ejemplo, si decides actualizar la ruta más adelante, solo necesitas modificar la anotación `@Route`, sin tener que cambiar ningún código que use la clase de vista para la navegación.
:::

### Navegación con parámetros {#navigation-with-parameters}

Cuando necesitas pasar parámetros junto con la ruta, webforJ te permite incrustar parámetros en la URL. Aquí tienes cómo navegar a una ruta con parámetros:

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

Esto navega a `/user/JohnDoe`, donde `JohnDoe` puede representar un ID de usuario. El componente para esta ruta puede luego extraer el parámetro y usarlo en consecuencia.

## Instancia de vista creada {#created-view-instance}

El método `navigate` acepta un `Consumer` de Java que se invoca una vez que la navegación se completa. El `Consumer` recibe la instancia del componente de vista creada, envuelta en un `Optional` de Java, permitiendo al desarrollador interactuar con la vista después de una navegación exitosa.

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
El consumidor recibe un `Optional` de Java para el componente porque podría ser `null`, o no creado por varias razones. Por ejemplo, el componente puede no ser renderizado si los observadores de navegación veto la navegación y detienen el proceso.
:::

## Opciones de navegación {#navigation-options}

La clase `NavigationOptions` permite a los desarrolladores ajustar cómo se maneja la navegación dentro de la aplicación. Al establecer opciones específicas, puedes controlar el comportamiento de la navegación, como si se debe actualizar el historial del navegador, invocar observadores del ciclo de vida, o incluso disparar eventos de navegación.

```java
NavigationOptions options = new NavigationOptions();
options.setUpdateHistory(false);

Router.getCurrent().navigate(
  new Location("user/JohnDoe"), options);
```

### Estableciendo opciones de navegación {#setting-navigation-options}

La clase `NavigationOptions` proporciona varios métodos para personalizar el comportamiento de navegación. Estos incluyen controlar cómo se manejan las rutas, si los observadores son notificados, y cómo se actualiza el historial del navegador.

Aquí están las principales opciones de configuración disponibles dentro de `NavigationOptions`:

1. **Tipo de Navegación (`setNavigationType`)**

   Esta opción define si la nueva ruta debe agregarse al historial del navegador o reemplazar la ruta actual.

   - **`PUSH`**: Agrega la nueva ruta a la pila de historial, preservando la ubicación actual.
   - **`REPLACE`**: Reemplaza la ruta actual en la pila de historial con la nueva ubicación, evitando que el botón de retroceso navegue a la ruta anterior.

2. **Disparar Eventos (`setFireEvents`)**

   Determina si los [eventos de ciclo de vida](./navigation-lifecycle/navigation-events) de navegación deben ser disparados durante la navegación. Por defecto, esto está configurado como `true`, y se disparan eventos. Si se establece en `false`, no se dispararán eventos, lo cual es útil para navegación silenciosa.

3. **Invocar Observadores (`setInvokeObservers`)**

   Esta bandera controla si la navegación debe activar [observadores](./navigation-lifecycle/observers) dentro de los componentes navegados. Los observadores típicamente manejan eventos como la entrada o salida de rutas. Establecer esto en `false` impide que los observadores sean invocados.

4. **Actualizar Historial (`setUpdateHistory`)**

   Cuando se establece en `false`, esta opción previene que la ubicación del historial sea actualizada. Esto es útil cuando deseas cambiar la vista sin afectar la navegación hacia atrás o hacia adelante del navegador. Solo afecta la gestión del historial, no el ciclo de vida del componente o el manejo de la ruta.

5. **Objeto de Estado (`setState`)**

   [El objeto de estado](./state-management#saving-and-restoring-state-in-browser-history) permite pasar información adicional al actualizar el historial del navegador. Este objeto se almacena en el estado del historial del navegador y puede ser usado posteriormente para propósitos personalizados, como guardar el estado de la aplicación durante la navegación.

6. **Recrear Instancias (`setRecreateFrom`)** <DocChip chip='since' label='26.02' />

    Cuando se especifica un componente de ruta, esta opción permite a la navegación destruir todas las instancias renderizadas de ese componente y los componentes que están por debajo de él antes de volver a renderizar. Esto permite que esa parte de la jerarquía use instancias nuevas, sin tocar las instancias renderizadas que preceden al componente dado.

    ```java
    NavigationOptions options = new NavigationOptions()
        .setRecreateFrom(DashboardView.class);

    Router.getCurrent().navigate(
        new Location("/dashboard"), options);
    ```

    La ruta predeterminada para `setRecreateFrom()` es `null`, permitiendo que el enrutador reutilice los componentes de ruta renderizados que permanecen en la ruta. Si el componente dado no tiene ninguna instancia renderizada, la navegación se comporta como de costumbre. Además, un observador del ciclo de vida puede veto la destrucción, lo que falla la navegación.

## Generando ubicaciones para vistas {#generating-locations-for-views}

El enrutador puede generar la ubicación para vistas basadas en el patrón de ruta definido en la vista. También puedes proporcionar parámetros adicionales para segmentos dinámicos y requeridos en la URL. Esto puede ser útil al construir enlaces o compartir puntos de acceso directos a vistas específicas en la aplicación.

Aquí tienes cómo generar una `Location` basada en una clase de vista y parámetros de ruta:

```java
Class<UserProfileView> userProfileView = UserProfileView.class;
ParametersBag params = ParametersBag.of("id=JohnDoe");

Optional<Location> location = Router.getCurrent().getLocation(userProfileView, params);
console().log(location.get());
```

Esto genera un objeto `Location` con la ruta `/user/JohnDoe`, la URI completa como una cadena.
