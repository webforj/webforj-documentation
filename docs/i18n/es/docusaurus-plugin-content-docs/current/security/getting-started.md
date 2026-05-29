---
title: Getting Started
sidebar_position: 2
_i18n_hash: becd2e7bd488a077c08ef5a64dbe0f61
---
Spring Security proporciona autenticación y autorización para aplicaciones Spring Boot. Cuando se integra con webforJ, protege rutas utilizando anotaciones mientras Spring maneja la gestión de usuarios y sesiones.

Esta guía cubre cómo agregar Spring Security a tu aplicación webforJ, configurando la autenticación, creando vistas de inicio de sesión y protegiendo rutas con control de acceso basado en roles.

:::tip[Aprende más sobre Spring Security]
Para una comprensión completa de las características y conceptos de Spring Security, consulta la [documentación de Spring Security](https://docs.spring.io/spring-security/reference/).
:::

## Agregar dependencia de Spring Security {#add-spring-security-dependency}

Agrega el inicio de Spring Security a tu `pom.xml`:

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

Esta única dependencia trae el marco de autenticación de Spring Security, codificadores de contraseñas y gestión de sesiones. La versión es gestionada automáticamente por tu POM padre de Spring Boot.

## Configurar Spring Security {#configure-spring-security}

Crea una clase de configuración de seguridad que conecte Spring Security con webforJ. Esta clase define cómo se autentican los usuarios y qué páginas manejan el inicio de sesión y la denegación de acceso:

```java title="SecurityConfig.java"
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
        .with(WebforjSecurityConfigurer.webforj(), configurer -> configurer
            .loginPage(LoginView.class)
            .accessDeniedPage(AccessDenyView.class)
            .logout())
        .build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
    UserDetails user = User.builder()
        .username("user")
        .password(passwordEncoder.encode("password"))
        .roles("USER")
        .build();

    UserDetails admin = User.builder()
        .username("admin")
        .password(passwordEncoder.encode("admin"))
        .roles("USER", "ADMIN")
        .build();

    return new InMemoryUserDetailsManager(user, admin);
  }

  @Bean
  AuthenticationManager authenticationManager(
      UserDetailsService userDetailsService,
      PasswordEncoder passwordEncoder) {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder);

    return new ProviderManager(authenticationProvider);
  }
}
```

Esta configuración crea cuatro beans de Spring que trabajan juntos:

**`SecurityFilterChain`** conecta Spring Security con el sistema de enrutamiento de webforJ. El método `WebforjSecurityConfigurer.webforj()` integra la autenticación de Spring Security con el enrutamiento de webforJ. Especificas qué clases de componentes manejan el inicio de sesión y la denegación de acceso, y Spring impondrá la autenticación antes de renderizar rutas protegidas.

- El método `loginPage()` indica a Spring Security dónde deben autenticar los usuarios. Pasa tu clase de componente de vista de inicio de sesión, y webforJ resolverá automáticamente la ruta desde la anotación `@Route`. Cuando usuarios no autenticados intenten acceder a rutas protegidas, serán redirigidos aquí.

- El método `accessDeniedPage()` define a dónde van los usuarios autenticados cuando carecen de permisos para una ruta. Por ejemplo, un usuario que intenta acceder a una ruta solo para administradores es redirigido a esta página.

- El método `logout()` habilita el punto final de cierre de sesión en `/logout`. Después de cerrar sesión, los usuarios son redirigidos de nuevo a la página de inicio de sesión con un parámetro `?logout`.

**`PasswordEncoder`** utiliza BCrypt para codificar contraseñas de manera segura. Spring Security aplica automáticamente este codificador durante el inicio de sesión para comparar la contraseña enviada con el hash almacenado.

**`UserDetailsService`** indica a Spring Security dónde encontrar la información del usuario durante la autenticación. Este ejemplo utiliza un almacenamiento en memoria con dos usuarios: `user/password` y `admin/admin`.

**`AuthenticationManager`** coordina el proceso de autenticación. Utiliza un proveedor que carga usuarios desde el `UserDetailsService` y verifica contraseñas con el `PasswordEncoder`.

## Crear vista de inicio de sesión {#create-login-view}

Crea una vista que presente un diálogo de inicio de sesión y envíe credenciales a Spring Security. La siguiente vista utiliza el componente [`Login`](/docs/components/login):

```java title="LoginView.java"
@Route("/signin")
@AnonymousAccess
public class LoginView extends Composite<Login> implements DidEnterObserver {
  private final Login self = getBoundComponent();

  public LoginView() {
    self.setAction("/signin");
    whenAttached().thenAccept(c -> self.open());
  }

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag params) {
    ParametersBag queryParams = event.getLocation().getQueryParameters();

    if (queryParams.containsKey("error")) {
      Toast.show("Nombre de usuario o contraseña inválidos. Por favor, inténtalo de nuevo.", Theme.DANGER);
    }

    if (queryParams.containsKey("logout")) {
      Toast.show("Has cerrado sesión exitosamente.", Theme.GRAY);
    }
  }
}
```

La anotación `@AnonymousAccess` marca esta ruta como pública para que los usuarios no autenticados puedan acceder a la página de inicio de sesión. Sin esta anotación, los usuarios serían redirigidos fuera de la página de inicio de sesión, creando un bucle infinito.

La línea `setAction("/signin")` es crítica, configura el componente de inicio de sesión para enviar credenciales al punto final de autenticación de Spring. Spring intercepta esta presentación, verifica las credenciales y concede acceso o redirige de nuevo con un parámetro de error.

El observador `onDidEnter` comprueba los parámetros de consulta que Spring agrega para comunicar resultados. Cuando la autenticación falla, Spring redirige a `/signin?error`. Después de cerrar sesión, redirige a `/signin?logout`. El observador muestra mensajes apropiados basados en estos parámetros.

:::tip Emparejamiento de puntos finales
La ruta en `setAction("/signin")` debe coincidir con tu ruta `@Route("/signin")`. Spring intercepta las presentaciones de formularios a esta ruta exacta para la autenticación. Si necesitas diferentes rutas para la página de inicio de sesión y el procesamiento de la autenticación, configúralas por separado en `SecurityConfig`:

```java
.loginPage("/signin", "/authenticate")
```

Esto muestra la página de inicio de sesión en `/signin` pero procesa la autenticación en `/authenticate`.
:::

## Crear vista de acceso denegado {#create-access-denied-view}

Crea una vista que se muestra cuando los usuarios no tienen permiso para acceder a una ruta:

```java title="AccessDenyView.java"
@Route(value = "/access-denied", outlet = MainLayout.class)
public class AccessDenyView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public AccessDenyView() {
    Paragraph message = new Paragraph("¡Ups! Esta área es solo para VIP.");
    Paragraph subMessage = new Paragraph(
        "¡Parece que intentaste colarte en el salón ejecutivo! O bien consigue mejores credenciales o regresa a las áreas públicas donde el café es gratis de todos modos.");

    self.add(message, subMessage);
  }
}
```

Esta vista se renderiza cuando los usuarios autenticados intentan acceder a rutas para las cuales no tienen permiso, como un usuario que intenta acceder a una ruta solo para administradores.

## Proteger tus rutas {#protect-your-routes}

Con la autenticación configurada, ahora puedes proteger tus rutas usando anotaciones de seguridad. Estas anotaciones indican a Spring Security quién puede acceder a cada vista, y el sistema de seguridad aplica automáticamente estas reglas antes de renderizar cualquier componente.

Cuando un usuario navega a una ruta, Spring Security intercepta la navegación y verifica las anotaciones de seguridad de la ruta. Si el usuario está autenticado (con credenciales válidas) y tiene los permisos requeridos, la vista se renderiza normalmente. Si no, se le redirige ya sea a la página de inicio de sesión o a la página de acceso denegado.

```java title="InboxView.java"
// Bandeja de entrada - accesible para todos los usuarios autenticados
@Route(value = "/", outlet = MainLayout.class)
public class InboxView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public InboxView() {
    self.setHeight("100%");
    self.setAlignment(FlexAlignment.CENTER);
    self.add(new Explore("Bandeja de entrada"));
  }
}
```

```java title="TeamsView.java" {3}
// Equipos - requiere rol ADMIN
@Route(value = "/teams", outlet = MainLayout.class)
@RolesAllowed("ADMIN")
public class TeamsView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public TeamsView() {
    self.setHeight("100%");
    self.setAlignment(FlexAlignment.CENTER);
    self.add(new Explore("Equipos"));
  }
}
```

La `InboxView` no tiene anotación, lo que significa que cualquier usuario autenticado puede acceder a ella. Cuando un usuario inicia sesión con éxito con las credenciales que definiste en `UserDetailsService` (`user/password` o `admin/admin`), puede ver esta ruta. Los usuarios no autenticados que intentan acceder a esta ruta son redirigidos a la página de inicio de sesión.

La `TeamsView` usa `@RolesAllowed("ADMIN")` para restringir el acceso a usuarios con el rol de administrador. A pesar de que ambas cuentas, "user" y "admin", son usuarios autenticados, solo los usuarios con el rol de administrador pueden acceder a esta ruta ya que tiene roles tanto `USER` como `ADMIN`. La cuenta de usuario solo tiene el rol de `USER`, por lo que intentar acceder a esta ruta los redirige a la página de acceso denegado.

:::tip Anotaciones de seguridad
Consulta la [guía de Anotaciones de Seguridad](/docs/security/annotations) para todas las anotaciones disponibles.
:::

## Agregar capacidad de cierre de sesión {#add-logout-capability}

Usa `SpringSecurityFormSubmitter` para crear un botón de cierre de sesión:

```java
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.icons.IconButton;
import com.webforj.spring.security.SpringSecurityFormSubmitter;

IconButton logout = new IconButton(FeatherIcon.LOG_OUT.create());
logout.onClick(e -> SpringSecurityFormSubmitter.logout("/logout").submit());
```

Cuando se hace clic, esto envía un formulario al punto final `/logout` de Spring Security, que borra la sesión del usuario y redirige a la página de inicio de sesión con un mensaje de éxito de cierre de sesión.

## Cómo funciona todo junto {#how-it-works-together}

Cuando Spring Boot inicia tu aplicación:

1. **La auto-configuración detecta** tanto `webforj-spring-boot-starter` como `spring-boot-starter-security`
2. **El gestor de seguridad se crea** automáticamente para unir el enrutamiento de webforJ y la autenticación de Spring Security
3. **Se registran evaluadores de seguridad** para manejar las anotaciones `@AnonymousAccess`, `@PermitAll`, `@RolesAllowed` y `@DenyAll`
4. **Se adjunta un observador de rutas** para interceptar la navegación y evaluar las reglas de seguridad antes de renderizar componentes

No tienes que enlazar manualmente estos componentes: la auto-configuración de Spring Boot maneja la integración. Solo defines tu `SecurityConfig` con gestión de usuarios y ubicaciones de páginas.

Cuando un usuario navega:

1. El observador de seguridad intercepta la navegación
2. Los evaluadores verifican las anotaciones de seguridad de la ruta
3. El `SecurityContextHolder` de Spring Security proporciona información de autenticación
4. Si está autorizado, el componente se renderiza; de lo contrario, el usuario es redirigido
