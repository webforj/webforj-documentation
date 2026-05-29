---
sidebar_position: 10
title: Navigational Frame Titles
_i18n_hash: 7a3b1c6780e7032040802a936bfb83fb
---
En webforJ, todas las rutas se renderizan dentro de un Frame, que sirve como un contenedor de nivel superior responsable de mostrar el contenido de la ruta actual. A medida que los usuarios navegan entre diferentes rutas, el Título del Frame se actualiza dinámicamente para reflejar la vista activa, ayudando a proporcionar un contexto claro sobre la ubicación actual del usuario dentro de la aplicación.

El título de un frame puede establecerse de forma estática utilizando anotaciones o dinámicamente a través de código en tiempo de ejecución. Este enfoque flexible permite a los desarrolladores definir títulos que se alineen con el propósito de cada vista, mientras que también se adaptan a escenarios o parámetros específicos según sea necesario.

## Título del frame con anotaciones {#frame-title-with-annotations}

La forma más sencilla de establecer el título de un frame en vista es utilizando la anotación `@FrameTitle`. Esta anotación permite definir un título estático para cualquier componente de ruta, que luego se aplica al frame cuando se renderiza el componente.

### Usando la anotación `@FrameTitle` {#using-the-frametitle-annotation}

La anotación `@FrameTitle` se aplica a nivel de clase y permite especificar un valor de cadena que representa el título de la página. Cuando el router navega a un componente con esta anotación, el título especificado se establecerá automáticamente para la ventana del navegador.

Aquí hay un ejemplo:

```java
@Route
@FrameTitle("Dashboard")
public class DashboardView extends Composite<Div> {
  public DashboardView() {
     // lógica de vista
  }
}
```

En este ejemplo:
- La clase `DashboardView` está anotada con `@Route` para definir la ruta.
<!-- vale Google.Quotes = NO -->
- La anotación `@FrameTitle("Dashboard")` establece el título del frame como "Dashboard".
<!-- vale Google.Quotes = YES -->
- Cuando el usuario navega a `/dashboard`, el título del frame se actualizará automáticamente al valor especificado.

Este método es útil para rutas que tienen un título estático y no requieren actualizaciones frecuentes en función del contexto de la ruta.

:::tip `@AppTitle` y `@FrameTitle`  
Si se establece el título de la aplicación, el título del frame lo incorporará. Por ejemplo, si la aplicación define el título como `@AppTitle("webforJ")` y el título del frame se establece como `@FrameTitle("Dashboard")`, el título final de la página será `Dashboard - webforJ`. Puedes personalizar el formato del título final en la anotación `@AppTitle` utilizando el atributo `format` si es necesario.  
:::

## Títulos de frame dinámicos {#dynamic-frame-titles}

En casos donde el título del frame necesita cambiar dinámicamente en función del estado de la aplicación o los parámetros de la ruta, webforJ proporciona una interfaz llamada `HasFrameTitle`. Esta interfaz permite a los componentes proporcionar un título de frame basado en el contexto actual de navegación y los parámetros de la ruta.

### Implementando la interfaz `HasFrameTitle` {#implementing-the-hasframetitle-interface}

La interfaz `HasFrameTitle` contiene un único método `getFrameTitle()`, que se invoca antes de que se actualice el título del frame. Este método proporciona la flexibilidad para generar un título dinámicamente en función del contexto de navegación u otros factores dinámicos.

```java
@Route("profile/:id")
public class ProfileView extends Composite<Div> implements HasFrameTitle {
  private final Div self = getBoundComponent();

  public ProfileView() {
    self.add(new H1("Página de Perfil"));
  }
  
  @Override
  public String getFrameTitle(NavigationContext context, ParametersBag parameters) {
    // Establecer dinámicamente el título del frame utilizando parámetros de ruta
    String userId = parameters.get("id").orElse("Desconocido");
    return "Perfil - Usuario " + userId;
  }
}
```

En este ejemplo:
- El componente `ProfileView` implementa la interfaz `HasFrameTitle`.
- El método `getFrameTitle()` genera dinámicamente un título utilizando el parámetro `id` de la URL.
<!-- vale Google.Quotes = NO -->
- Si la ruta es `/profile/123`, el título se actualizará a "Perfil - Usuario 123".
<!-- vale Google.Quotes = YES -->

:::tip Combinando anotaciones y títulos dinámicos  
Puedes combinar métodos estáticos y dinámicos. Si un componente de ruta tiene tanto una anotación `@FrameTitle` como implementa la interfaz `HasFrameTitle`, el título proporcionado dinámicamente desde `getFrameTitle()` prevalecerá sobre el valor estático de la anotación.  
:::
