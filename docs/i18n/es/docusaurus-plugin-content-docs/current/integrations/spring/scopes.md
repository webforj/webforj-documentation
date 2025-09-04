---
title: Scopes
sidebar_position: 16
_i18n_hash: 96d36a28887e551ba3f9acab0d7059bc
---
<!-- vale off -->
# Alcances <DocChip chip='since' label='25.03' />
<!-- vale on -->

Spring gestiona el ciclo de vida de los beans a través de alcances. Cada alcance define cuándo se crea un bean, cuánto tiempo vive y cuándo se destruye. webforJ añade dos alcances personalizados: `@EnvironmentScope` y `@RouteScope`, que se corresponden con la forma en que las aplicaciones webforJ manejan las sesiones del navegador y la navegación.

:::tip[Aprende más sobre los alcances de Spring]
Para una cobertura completa del mecanismo de alcance de Spring y los alcances estándar, consulta la [documentación de los alcances de beans de Spring](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html).
:::

## Alcance del entorno {#environment-scope}

La anotación `@EnvironmentScope` crea beans que viven durante la duración de una ventana o pestaña del navegador. Cuando un usuario abre la aplicación en una ventana o pestaña del navegador, webforJ crea un Entorno. Cualquier bean marcado con `@EnvironmentScope` se crea una vez por ventana/pestaña del navegador y permanece disponible hasta que el usuario cierra la pestaña o la sesión expira.

Cada Entorno representa una ventana o pestaña del navegador aislada. Los beans con alcance de entorno no se pueden compartir entre diferentes ventanas o pestañas del navegador: cada ventana/pestaña recibe su propia instancia.

Agrega `@EnvironmentScope` a cualquier componente de Spring:

```java title="UserSession.java" {2}
@Component
@EnvironmentScope
public class UserSession {
  private String userId;
  private Map<String, Object> attributes = new HashMap<>();
  
  public void setUserId(String userId) {
    this.userId = userId;
  }
  
  public String getUserId() {
    return userId;
  }
  
  public void setAttribute(String key, Object value) {
    attributes.put(key, value);
  }
  
  public Object getAttribute(String key) {
    return attributes.get(key);
  }
}
```

El bean `UserSession` mantiene el estado a lo largo de la vida de una ventana o pestaña del navegador. Cada ventana/pestaña del navegador recibe una instancia aislada.

### Uso de beans con alcance de entorno {#using-environment-scoped-beans}

Las rutas reciben beans con alcance de entorno a través de la inyección por constructor:

```java
@Route("/dashboard")
public class DashboardView extends Composite<Div> {
  
  public DashboardView(UserSession session) {
    String userId = session.getUserId();
    // Utiliza los datos de la sesión
    if (userId == null) {
      // Redirigir a inicio de sesión
    }
  }
}

@Route("/profile")
public class ProfileView extends Composite<Div> {
  
  public ProfileView(UserSession session) {
    // Misma instancia de UserSession que DashboardView
    session.setAttribute("lastView", "profile");
  }
}
```

Spring inyecta la misma instancia de `UserSession` en ambas vistas para la misma ventana/pestaña del navegador. La navegación entre el panel de control y el perfil preserva la instancia de la sesión. Si el usuario abre la aplicación en una nueva ventana o pestaña del navegador, esa ventana recibe su propia instancia distinta de `UserSession`.

## Alcance de ruta {#route-scope}

La anotación `@RouteScope` crea beans compartidos dentro de una jerarquía de rutas. Navegar a `/admin/users` construye una jerarquía de componentes con la vista de administrador como padre y la vista de usuarios como hijo. Los beans con alcance de ruta se instancian una vez por jerarquía y se comparten entre los componentes padre e hijo.

El alcance de ruta se diferencia del alcance de entorno en su granularidad. Mientras que los beans con alcance de entorno existen durante toda la sesión de la ventana/pestaña del navegador, los beans con alcance de ruta existen solo mientras el usuario permanezca dentro de una jerarquía de rutas específica. Navegar fuera de la jerarquía destruye los beans y regresar crea nuevas instancias. Este alcance es ideal para el estado que debe restablecerse cuando los usuarios navegan entre diferentes secciones de su aplicación.

Agrega `@RouteScope` a cualquier componente de Spring:

```java title="NavigationState" {2}
@Component
@RouteScope
public class NavigationState {
  private String activeTab;
  private List<String> breadcrumbs = new ArrayList<>();
  
  public void setActiveTab(String tab) {
    this.activeTab = tab;
  }
  
  public void addBreadcrumb(String crumb) {
    breadcrumbs.add(crumb);
  }
  
  public List<String> getBreadcrumbs() {
    return Collections.unmodifiableList(breadcrumbs);
  }
}
```

### Jerarquías de ruta y compartir {#route-hierarchies-and-sharing}

Las rutas forman jerarquías a través del parámetro `outlet`. La ruta padre proporciona un outlet donde se renderizan las rutas hijo. Cuando defines una ruta con un outlet, webforJ construye un árbol de componentes donde el componente outlet se convierte en el padre y el componente de ruta se convierte en el hijo. Esta relación padre-hijo determina qué componentes comparten los beans con alcance de ruta.

```java
@Route("/admin")
public class AdminView extends Composite<Div> {

  public AdminView(NavigationState navState) {
    navState.addBreadcrumb("Inicio");
    navState.addBreadcrumb("Administrador");
    // ...
  }
}

@Route(value = "users", outlet = AdminView.class)
public class UsersView extends Composite<Div> {
  
  public UsersView(NavigationState navState) {
    // Misma instancia de NavigationState que AdminView
    navState.setActiveTab("users");
    navState.addBreadcrumb("Usuarios");
  }
}
```

Las `AdminView` y `UsersView` comparten la misma instancia de `NavigationState`. El diseño establece la estructura de navegación, mientras que la vista actualiza el estado activo. La navegación fuera de la sección `admin` (por ejemplo, a `/public`) destruye la instancia actual de `NavigationState` y crea una nueva para la jerarquía siguiente.

El límite del alcance sigue la estructura del árbol de rutas. Todos los componentes desde la raíz de una jerarquía hasta las hojas comparten las mismas instancias de beans con alcance de ruta. Navegar hacia rutas hermanas dentro de la misma jerarquía preserva los beans, mientras que navegar hacia jerarquías no relacionadas desencadena la destrucción y recreación de beans.

### Personalizando límites de alcance con `@SharedFrom` {#customizing-scope-boundaries}

Por defecto, los beans con alcance de ruta se comparten desde el componente más alto. La anotación `@SharedFrom` especifica un componente raíz alternativo. Esta anotación cambia dónde en la jerarquía un bean se vuelve disponible, permitiéndote restringir el acceso a subárboles específicos de tu estructura de ruta:

```java title="TeamContext" {2,3}
@Component
@RouteScope
@SharedFrom(TeamSection.class)
public class TeamContext {
  private String teamId;
  private List<String> permissions = new ArrayList<>();
  
  public void setTeamId(String id) {
    this.teamId = id;
  }
  
  public String getTeamId() {
    return teamId;
  }
}
```

El bean es accesible exclusivamente dentro de `TeamSection` y sus componentes hijo:

```java
@Route("/")
public class MainView extends Composite<Div> {}

@Route(value = "teams", outlet = MainView.class)
public class TeamSection extends Composite<Div> {
  
  public TeamSection(TeamContext context) {
    // Bean creado aquí
    context.setTeamId("team-123");
  }
}

@Route(value = "public", outlet = MainView.class)
public class PublicSection extends Composite<Div> {

  public PublicSection(TeamContext context) {
    // No se puede inyectar TeamContext - está limitado a TeamSection
    // Intentar inyección lanza IllegalStateException
  }
}
```

La anotación `@SharedFrom` impone fronteras arquitectónicas. Los componentes fuera del alcance especificado no pueden acceder al bean. Cuando Spring intenta inyectar un bean `@SharedFrom` en un componente fuera de su jerarquía designada, la inyección falla con una `IllegalStateException`. Esta imposición ocurre en tiempo de ejecución cuando se accede a la ruta, por lo que los beans permanecen correctamente limitados a sus árboles de componentes destinados.

La anotación acepta un único parámetro: la clase de componente que debe servir como raíz para el compartir. Solo este componente y sus descendientes en la jerarquía de rutas pueden acceder al bean. Los componentes padres y las jerarquías hermanas no pueden inyectarlo.
