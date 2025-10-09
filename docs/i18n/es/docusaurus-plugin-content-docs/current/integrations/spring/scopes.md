---
title: Scopes
sidebar_position: 16
sidebar_class_name: new-content
_i18n_hash: 8c977fdef41f6125ac21239e7e397f4d
---
<!-- vale off -->

# Alcances <DocChip chip='since' label='25.03' />

<!-- vale on -->

Spring gestiona el ciclo de vida de los beans a través de alcances. Cada alcance define cuándo se crea un bean, cuánto tiempo vive y cuándo se destruye. Además de los alcances estándar de Spring, webforJ añade tres alcances personalizados: `@WebforjSessionScope`, `@EnvironmentScope` y `@RouteScope`.

:::tip[Aprende más sobre los alcances de Spring]
Para una cobertura completa del mecanismo de alcance de Spring y los alcances estándar, consulta la [documentación de los alcances de beans de Spring](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html).
:::

## Visión general

webforJ proporciona tres alcances personalizados diseñados para la gestión del estado de aplicaciones web:

- **`@WebforjSessionScope`**: Beans compartidos entre todas las pestañas/ventanas del navegador para la misma sesión de usuario. Perfecto para autenticación, preferencias de usuario y carritos de compra.
- **`@EnvironmentScope`**: Beans aislados a una sola pestaña/ventana del navegador. Ideal para flujos de trabajo específicos de la pestaña, datos de formularios y edición de documentos independiente.
- **`@RouteScope`**: Beans compartidos dentro de una jerarquía de rutas. Útil para el estado de navegación y datos que deben restablecerse cuando los usuarios navegan entre secciones de la aplicación.

[![alcances de spring de webforJ](/img/spring-scopes.svg)](/img/spring-scopes.svg)

## Alcance de sesión {#session-scope}

La anotación `@WebforjSessionScope` crea beans que persisten a lo largo de toda la sesión de webforJ. A diferencia del [alcance de entorno](#environment-scope) que aísla los beans por ventana/pestaña del navegador, los beans con alcance de sesión se comparten entre todas las ventanas y pestañas del mismo navegador. Estos beans viven mientras la sesión de webforJ se mantenga activa, típicamente hasta que el usuario cierre sesión o la sesión expire.

El alcance de sesión es ideal para el estado de autenticación, preferencias de usuario, carritos de compra y datos que deben persistir entre múltiples pestañas del navegador, pero permanecer aislados entre diferentes usuarios. Cada sesión de navegador de un usuario recibe su propia instancia de beans con alcance de sesión.

:::info Los beans necesitan ser serializables
Los beans con alcance de sesión deben implementar `Serializable` ya que se almacenan en los atributos de la sesión HTTP. Todos los campos no transitorios también deben ser serializables (primitivos, `String` o clases que implementen `Serializable`). Marca los campos como `transient` si no deberían ser persistidos.
:::

Añade `@WebforjSessionScope` a cualquier componente de Spring:

```java title="AuthenticationService.java" {2}
@Service
@WebforjSessionScope
public class AuthenticationService {
  private User authenticatedUser;
  private Instant loginTime;

  public void login(String username, String password) {
    // Autenticar usuario
    authenticatedUser = authenticate(username, password);
    loginTime = Instant.now();
  }

  public void logout() {
    authenticatedUser = null;
    loginTime = null;
    // Invalidar sesión
  }

  public boolean isAuthenticated() {
    return authenticatedUser != null;
  }

  public User getCurrentUser() {
    return authenticatedUser;
  }
}
```

### Compartición de sesión entre pestañas {#session-sharing-across-tabs}

Los beans con alcance de sesión mantienen el estado en todas las ventanas y pestañas del navegador. Abrir la aplicación en múltiples pestañas comparte la misma instancia de bean:

```java
@Route
public class LoginView extends Composite<Div> {

  public LoginView(AuthenticationService authService) {
    if (authService.isAuthenticated()) {
      // Usuario ya ha iniciado sesión desde otra pestaña
      Router.getCurrent().navigate("/dashboard");
      return;
    }

    Button loginButton = new Button("Iniciar sesión");
    loginButton.onClick(e -> {
      authService.login(username, password);
      // El usuario ahora está conectado en todas las pestañas
    });
  }
}

@Route
public class DashboardView extends Composite<Div> {

  public DashboardView(AuthenticationService authService) {
    // La misma instancia de AuthenticationService en todas las pestañas
    User user = authService.getCurrentUser();
    if (user == null) {
      Router.getCurrent().navigate("/login");
      return;
    }

    // Mostrar el panel de usuario
  }
}
```

Cuando un usuario inicia sesión a través de una pestaña, todas las demás pestañas tienen acceso inmediato al estado autenticado. Abrir nuevas pestañas o ventanas mantiene el estado de inicio de sesión. Cerrar sesión desde cualquier pestaña afecta todas las pestañas, ya que comparten el mismo bean con alcance de sesión.

## Alcance de entorno {#environment-scope}

La anotación `@EnvironmentScope` crea beans que viven durante la duración de una sesión de ventana o pestaña del navegador. Cuando un usuario abre la aplicación en una ventana o pestaña del navegador, webforJ crea un Entorno. Cualquier bean marcado con `@EnvironmentScope` se crea una vez por ventana/pestaña del navegador y permanece disponible hasta que el usuario cierra la pestaña o la sesión expira.

Cada Entorno representa una ventana o pestaña del navegador aislada. Los beans con alcance de entorno no se pueden compartir entre diferentes ventanas o pestañas del navegador porque cada ventana/pestaña recibe su propia instancia.

Añade `@EnvironmentScope` a cualquier componente de Spring:

```java title="TabWorkspace.java" {2}
@Component
@EnvironmentScope
public class TabWorkspace {
  private String documentId;
  private Map<String, Object> workspaceData = new HashMap<>();

  public void setDocumentId(String documentId) {
    this.documentId = documentId;
  }

  public String getDocumentId() {
    return documentId;
  }

  public void setWorkspaceData(String key, Object value) {
    workspaceData.put(key, value);
  }

  public Object getWorkspaceData(String key) {
    return workspaceData.get(key);
  }
}
```

El bean `TabWorkspace` mantiene el estado a lo largo de la vida de una ventana o pestaña del navegador. Cada ventana/pestaña del navegador recibe una instancia aislada.

### Uso de beans con alcance de entorno {#using-environment-scoped-beans}

Las rutas reciben beans con alcance de entorno a través de la inyección de constructor:

```java
@Route
public class EditorView extends Composite<Div> {

  public EditorView(TabWorkspace workspace) {
    String documentId = workspace.getDocumentId();
    // Cargar documento para esta pestaña
    if (documentId == null) {
      // Crear nuevo documento
      workspace.setDocumentId(generateDocumentId());
    }
  }
}

@Route
public class PreviewView extends Composite<Div> {

  public PreviewView(TabWorkspace workspace) {
    // La misma instancia de TabWorkspace que EditorView en esta pestaña
    workspace.setWorkspaceData("lastView", "preview");
    String documentId = workspace.getDocumentId();
    // Previsualizar el documento que se está editando en esta pestaña
  }
}
```

Spring inyecta la misma instancia de `TabWorkspace` en ambas vistas para la misma ventana/pestaña del navegador. La navegación entre el editor y la vista previa preserva la instancia del espacio de trabajo. Si el usuario abre la aplicación en una nueva ventana o pestaña del navegador, esa ventana recibe su propia instancia distinta de `TabWorkspace`, permitiendo la edición independiente de diferentes documentos.

## Alcance de ruta {#route-scope}

La anotación `@RouteScope` crea beans compartidos dentro de una jerarquía de rutas. Navegar a `/admin/users` construye una jerarquía de componentes con la vista de administrador como padre y la vista de usuarios como hijo. Los beans con alcance de ruta se instancian una vez por jerarquía y se comparten entre los componentes padre e hijo.

El alcance de ruta difiere del alcance de entorno en granularidad. Mientras que los beans con alcance de entorno existen durante toda la sesión de la ventana/pestaña del navegador, los beans con alcance de ruta existen solo mientras el usuario permanece dentro de una jerarquía de ruta específica. Navegar lejos de la jerarquía destruye los beans, y volver crea nuevas instancias. Este alcance es ideal para el estado que debe restablecerse cuando los usuarios navegan entre diferentes secciones de tu aplicación.

Añade `@RouteScope` a cualquier componente de Spring:

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

### Jerarquías de ruta y compartición {#route-hierarchies-and-sharing}

Las rutas forman jerarquías a través del parámetro `outlet`. La ruta padre proporciona un outlet donde se renderizan las rutas hijas. Cuando defines una ruta con un outlet, webforJ construye un árbol de componentes donde el componente de outlet se convierte en padre y el componente de ruta se convierte en hijo. Esta relación padre-hijo determina qué componentes comparten beans con alcance de ruta.

```java {11}
@Route
public class AdminView extends Composite<Div> {

  public AdminView(NavigationState navState) {
    navState.addBreadcrumb("Inicio");
    navState.addBreadcrumb("Administración");
    // ...
  }
}

@Route(value = "users", outlet = AdminView.class)
public class UsersView extends Composite<Div> {

  public UsersView(NavigationState navState) {
    // La misma instancia de NavigationState que AdminView
    navState.setActiveTab("users");
    navState.addBreadcrumb("Usuarios");
  }
}
```

La `AdminView` y la `UsersView` comparten la misma instancia de `NavigationState`. El diseño establece la estructura de navegación mientras que la vista actualiza el estado activo. Navegar fuera de la sección `admin` (por ejemplo, a `/public`) destruye la instancia actual de `NavigationState` y crea una nueva para la jerarquía subsiguiente.

El límite del alcance sigue la estructura del árbol de rutas. Todos los componentes desde la raíz de una jerarquía hasta las hojas comparten las mismas instancias de beans con alcance de ruta. Navegar a rutas hermanos dentro de la misma jerarquía preserva los beans, mientras que la navegación a jerarquías no relacionadas activa la destrucción y recreación del bean.

### Personalizando los límites de alcance con `@SharedFrom` {#customizing-scope-boundaries}

Los beans con alcance de ruta se comparten desde el componente más alto por defecto. La anotación `@SharedFrom` especifica un componente raíz alternativo. Esta anotación cambia dónde en la jerarquía un bean se vuelve disponible, permitiéndote restringir el acceso a subárboles específicos de tu estructura de rutas:

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

El bean es accesible exclusivamente dentro de `TeamSection` y sus componentes hijos:

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
    // Intentar la inyección lanza IllegalStateException
  }
}
```

La anotación `@SharedFrom` hace cumplir límites arquitectónicos. Los componentes fuera del alcance especificado no pueden acceder al bean. Cuando Spring intenta inyectar un bean `@SharedFrom` en un componente fuera de su jerarquía designada, la inyección falla con una `IllegalStateException`. Este cumplimiento ocurre en tiempo de ejecución cuando se accede a la ruta, por lo que los beans permanecen adecuadamente limitados a sus árboles de componentes previstos.

La anotación acepta un solo parámetro: la clase del componente que debe servir como raíz para compartir. Solo este componente y sus descendientes en la jerarquía de rutas pueden acceder al bean. Los componentes padres y las jerarquías hermanos no pueden inyectarlo.
