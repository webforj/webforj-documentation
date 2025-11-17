---
sidebar_position: 7
title: Gestión del Estado
_i18n_hash: e10d155e02722ea38419a79813a2f5af
---
Crear experiencias de usuario dinámicas y fluidas a menudo requiere que el estado de tu aplicación web se refleje en la URL y se mantenga a través de eventos de navegación del navegador. Puedes lograr esto sin recargar la página aprovechando las actualizaciones de parámetros de URL y la gestión del estado del historial del navegador. Esto asegura que los usuarios puedan compartir, marcar o regresar a vistas específicas con la aplicación plenamente consciente de sus interacciones previas.

## Actualizando la URL {#updating-the-url}

Cuando el estado de una página web cambia, como al filtrar una lista de productos o navegar a través de diferentes vistas, a menudo necesitas que la URL refleje esos cambios. Puedes utilizar los métodos `replaceState` o `pushState` proporcionados por la clase `BrowserHistory` para manipular la URL sin recargar la página:

- **`pushState`**: Agrega una nueva entrada a la pila de historial del navegador sin recargar la página. Esto es útil para navegar entre diferentes vistas o contenido dinámico.
- **`replaceState`**: Actualiza la entrada actual en el historial del navegador sin añadir una nueva entrada. Esto es ideal para actualizar el estado dentro de la misma vista.

### Ejemplo: Actualizando la URL con parámetros de consulta {#example-updating-the-url-with-query-parameters}

En este ejemplo, cuando se hace clic en el botón "Actualizar URL", la interfaz se actualiza para mostrar la categoría seleccionada y la ordenación, y la URL se actualiza con nuevos parámetros de consulta para `category` y `sort`:

```java
@Route(value = "products")
public class ProductView extends Composite<Div> {
  Paragraph paragraph = new Paragraph();
  Random random = new Random();

  public ProductView() {
    Button update = new Button("Update URL", ButtonTheme.PRIMARY);
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
        // Actualizar la URL sin recargar la página
        .replaceState(null, newLocation);
  }
}
```

### Explicación: {#explanation}

- **Método `filter`**: El método maneja la actualización tanto de la UI como de la URL en función de la `category` y `sort` seleccionadas.
- **Método `updateUrl`**: Este método crea un nuevo `ParametersBag` para los parámetros de consulta, construye una nueva URL y luego usa `replaceState` para actualizar la URL del navegador sin recargar la página.
- **`replaceState`**: Este método cambia la URL a la nueva ubicación mientras mantiene el estado actual, sin causar una recarga de la página.

## Guardando y restaurando el estado en el historial del navegador {#saving-and-restoring-state-in-browser-history}

Además de actualizar la URL, es posible guardar objetos de estado arbitrarios en el historial del navegador. Esto significa que puedes almacenar datos adicionales relacionados con la vista actual (por ejemplo: entradas de formularios, filtros, etc.) sin incrustarlos directamente en la URL.

### Ejemplo: Guardando el estado de selección {#example-saving-selection-state}

En el siguiente ejemplo, un `ProfileView` consiste en varias pestañas (Perfil, Pedidos y Configuración). Cuando el usuario cambia entre pestañas, el estado de la pestaña seleccionada se guarda en el historial del navegador usando `replaceState`. Esto permite que la aplicación recuerde la última pestaña activa si el usuario vuelve a esta vista o actualiza la página.

```java
@Route(value = "profile")
public class ProfileView extends Composite<Div> implements DidEnterObserver {
  TabbedPane sections = new TabbedPane();
  int currentSection = 0;

  public ProfileView() {
    sections.addTab("Profile");
    sections.addTab("Orders");
    sections.addTab("Settings");

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

1. **Componente TabbedPane**: La vista consiste en un componente `TabbedPane`, que tiene tres pestañas: Perfil, Pedidos y Configuración.
2. **Guardando el estado al cambiar de pestaña**: Cada vez que se selecciona una pestaña, el índice de la sección actual se guarda en el historial del navegador utilizando el método `replaceState`.
3. **Restaurando el estado en la navegación**: Cuando el usuario navega de regreso a la `ProfileView`, la aplicación recupera la sección guardada del historial usando `event.getState()` y restaura la selección de pestaña correcta.
