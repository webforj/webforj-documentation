---
sidebar_position: 7
title: State Management
_i18n_hash: cba905abd01a780dea1f459ec4397cda
---
Creating seamless, dynamic user experiences often requires that the state of your web app be reflected in the URL and retained across browser navigation events. You can achieve this without reloading the page by leveraging URL parameter updates and browser history state management. This ensures that users can share, bookmark, or return to specific views with the app fully aware of their prior interactions.

## Actualización de la URL {#updating-the-url}

Cuando el estado de una página web cambia, como al filtrar una lista de productos o navegar a través de diferentes vistas, a menudo necesitas que la URL refleje esos cambios. Puedes usar los métodos `replaceState` o `pushState` proporcionados por la clase `BrowserHistory` para manipular la URL sin recargar la página:

- **`pushState`**: Agrega una nueva entrada a la pila de historial del navegador sin recargar la página. Esto es útil para navegar entre diferentes vistas o contenido dinámico.
- **`replaceState`**: Actualiza la entrada actual en el historial del navegador sin agregar una nueva entrada. Esto es ideal para actualizar el estado dentro de la misma vista.

### Ejemplo: Actualizando la URL con parámetros de consulta {#example-updating-the-url-with-query-parameters}

En este ejemplo, cuando se hace clic en el botón "Actualizar URL", la interfaz de usuario se actualiza para mostrar la categoría seleccionada y el orden, y la URL se actualiza con nuevos parámetros de consulta para `category` y `sort`:

```java
@Route(value = "products")
public class ProductView extends Composite<Div> {
  Paragraph paragraph = new Paragraph();
  Random random = new Random();

  public ProductView() {
    Button update = new Button("Actualizar URL", ButtonTheme.PRIMARY);
    update.onClick(ev -> {
      filter("electronics", String.valueOf(random.nextInt(3) - 1));
    });

    Div div = getBoundComponent();
    div.add(update);
    div.add(paragraph);
  }

  public void filter(String category, String sort) {
    // actualizar la UI
    updateUI(category, sort);

    // actualizar la URL
    updateUrl(category, sort);
  }

  private void updateUI(String category, String sort) {
    paragraph.setText("Viendo categoría: " + category + " y ordenando por: " + sort);
  }

  private void updateUrl(String category, String sort) {
    ParametersBag queryParameters = new ParametersBag();
    queryParameters.put("category", category);
    queryParameters.put("sort", sort);

    Location newLocation = new Location("/products?" + queryParameters.getQueryString());
    Router.getCurrent().getHistory()
        // Actualiza la URL sin recargar la página
        .replaceState(null, newLocation);
  }
}
```

### Explicación: {#explanation}

- **Método `filter`**: El método se encarga de actualizar tanto la UI como la URL según la `category` y `sort` seleccionadas.
- **Método `updateUrl`**: Este método crea un nuevo `ParametersBag` para los parámetros de consulta, construye una nueva URL y luego usa `replaceState` para actualizar la URL del navegador sin recargar la página.
- **`replaceState`**: Este método cambia la URL a la nueva ubicación mientras mantiene el estado actual, sin causar una recarga de la página.

## Guardando y restaurando estado en el historial del navegador {#saving-and-restoring-state-in-browser-history}

Además de actualizar la URL, es posible guardar objetos de estado arbitrarios en el historial del navegador. Esto significa que puedes almacenar datos adicionales relacionados con la vista actual (por ejemplo: entradas de formularios, filtros, etc.) sin incrustarlos directamente en la URL.

### Ejemplo: Guardando el estado de la selección {#example-saving-selection-state}

En el siguiente ejemplo, una `ProfileView` consta de varias pestañas (Perfil, Pedidos y Configuraciones). Cuando el usuario cambia entre pestañas, el estado de la pestaña seleccionada se guarda en el historial del navegador usando `replaceState`. Esto permite que la aplicación recuerde la última pestaña activa si el usuario navega de regreso a esta vista o actualiza la página.

```java
@Route(value = "profile")
public class ProfileView extends Composite<Div> implements DidEnterObserver {
  TabbedPane sections = new TabbedPane();
  int currentSection = 0;

  public ProfileView() {
    sections.addTab("Perfil");
    sections.addTab("Pedidos");
    sections.addTab("Configuraciones");

    sections.onSelect(ev -> {
      currentSection = ev.getTabIndex();
       // Guardar el estado usando replaceState
      updateState(currentSection);
    });

    getBoundComponent().add(sections);
  }

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    // Intentar recuperar la última sección guardada del estado del historial del navegador
    Optional<Integer> lastSavedSection = event.getState(Integer.class);

    // Si se guardó una sección, restaurar la selección de la pestaña
    lastSavedSection.ifPresent(section -> sections.select(section));
  }

  private void updateState(int section) {
    Router router = Router.getCurrent();
    Location currentLocation = router.getHistory().getLocation().get();

    // Actualizar el estado actual con la sección seleccionada
    Router.getCurrent().getHistory()
        .replaceState(section, currentLocation);
  }
}
```

### Explicación: {#explanation-1}

1. **Componente TabbedPane**: La vista consiste en un componente `TabbedPane`, que tiene tres pestañas: Perfil, Pedidos y Configuraciones.
2. **Guardado de Estado en el Cambio de Pestaña**: Cada vez que se selecciona una pestaña, el índice de la sección actual se guarda en el historial del navegador usando el método `replaceState`.
3. **Restauración del Estado en la Navegación**: Cuando el usuario navega de regreso a la `ProfileView`, la aplicación recupera la sección guardada del historial usando `event.getState()` y restaura la selección correcta de la pestaña.
