---
title: Accessing User
sidebar_position: 4
_i18n_hash: 5a29cabdc472de49bcc1db895b982485
---
Spring Security almacena la información del usuario autenticado en el `SecurityContextHolder`, proporcionando acceso al nombre de usuario, roles y autoridades a lo largo de tu aplicación. Esta sección muestra cómo recuperar y utilizar esta información en las vistas y componentes de webforJ.

## Obtener información del usuario actual {#get-current-user-information}

Spring Security almacena la información del usuario autenticado en el `SecurityContextHolder`, que proporciona acceso seguro a hilos en cualquier parte de tu aplicación:

```java
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;

// Obtener autenticación actual
Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

// Obtener nombre de usuario
String username = authentication.getName();

// Obtener autoridades (roles)
Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

// Verificar si el usuario tiene un rol específico
boolean isAdmin = authorities.stream()
  .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
```

El `SecurityContextHolder` es el mecanismo central de Spring Security para acceder a los detalles de autenticación del usuario actual. Funciona a lo largo de tu aplicación, incluyendo en los constructores y métodos de vistas de webforJ.

## Mostrar información del usuario en vistas {#display-user-information-in-views}

Accede a la información del usuario directamente en tus vistas de webforJ para mostrar contenido personalizado:

```java title="DashboardView.java"
@Route("/dashboard")
@PermitAll
public class DashboardView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public DashboardView() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    // Mostrar información del usuario
    H1 welcome = new H1("¡Bienvenido, " + username + "!");

    self.add(welcome);
  }
}
```

## Renderizado condicional basado en roles {#conditional-rendering-based-on-roles}

Muestra u oculta elementos de la interfaz de usuario según los roles del usuario para controlar qué funciones ven los usuarios:

```java title="DashboardView.java"
@Route("/dashboard")
@PermitAll
public class DashboardView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public DashboardView() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    // Verificar rol específico
    boolean isAdmin = auth.getAuthorities().stream()
      .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

    // Agregar condicionalmente botón solo para administradores
    if (isAdmin) {
      Button adminPanel = new Button("Panel de Administración");
      adminPanel.onClick(e -> Router.getCurrent().navigate(AdminView.class));
      self.add(adminPanel);
    }
  }
}
```
