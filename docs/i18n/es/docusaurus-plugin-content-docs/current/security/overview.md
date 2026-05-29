---
sidebar_position: 1
title: Security
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: fe28b9f0c456b9880785afcc5d4d5f23
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Seguridad <DocChip chip='since' label='25.10' />

:::note Vista Pública
Esta función está en vista pública y lista para su uso en producción. Durante el período de vista previa, las API pueden mejorarse según los comentarios de la comunidad de desarrolladores. Cualquier cambio se anunciará con anticipación a través de notas de versión y se proporcionarán guías de migración cuando sea necesario.
:::

En las aplicaciones web modernas, **la seguridad** se refiere al control de acceso a diferentes partes de tu aplicación en función de la identidad y los permisos del usuario. En webforJ, la seguridad proporciona un marco para el **control de acceso a nivel de ruta**, donde puedes proteger vistas, requerir autenticación y hacer cumplir permisos basados en roles.

<AISkillTip skill="webforj-securing-apps" />

## Routing Tradicional VS asegurado {#traditional-vs-secured-routing}

En el enrutamiento tradicional no asegurado, todas las rutas en tu aplicación son accesibles para cualquiera que conozca la URL. Esto significa que los usuarios pueden navegar a páginas sensibles como paneles de administración o paneles de usuario sin ninguna verificación de autenticación o autorización. La carga recae en los desarrolladores para que verifiquen manualmente los permisos en cada componente, lo que lleva a una aplicación inconsistente de la seguridad y potenciales vulnerabilidades.

Este enfoque introduce varios problemas:

1. **Verificaciones manuales**: Los desarrolladores deben recordar agregar lógica de seguridad en cada vista o diseño protegido.
2. **Aplicación inconsistente**: Las verificaciones de seguridad dispersas a lo largo de la base de código conducen a lagunas y errores.
3. **Sobrecarga de mantenimiento**: Cambiar las reglas de acceso requiere actualizar múltiples archivos.
4. **Sin control centralizado**: No hay un lugar único para comprender o gestionar la seguridad de la aplicación.

**El enrutamiento asegurado** en webforJ resuelve esto al habilitar el control de acceso directamente a nivel de ruta. El sistema de seguridad aplica automáticamente las reglas antes de renderizar cualquier componente, proporcionando un enfoque centralizado y declarativo para la seguridad de la aplicación. Así es como funciona:

1. **Anotaciones declarativas**: Marca rutas con anotaciones de seguridad para definir los requisitos de acceso.
2. **Aplicación automática**: El sistema de seguridad verifica los permisos antes de renderizar cualquier vista.
3. **Configuración centralizada**: Define el comportamiento de seguridad en un solo lugar y aplícalo de manera consistente.
4. **Implementaciones flexibles**: Elige entre la integración de Spring Security o una implementación personalizada en Java puro.

Este diseño permite la **autenticación** (verificación de la identidad del usuario) y la **autorización** (verificación de lo que el usuario puede acceder), por lo que solo se concede acceso a rutas protegidas a los usuarios autorizados. Los usuarios no autorizados son redirigidos automáticamente o se les niega el acceso según las reglas de seguridad configuradas.

## Ejemplo de enrutamiento asegurado en webforJ {#example-of-secured-routing-in-webforj}

Aquí hay un ejemplo que muestra diferentes niveles de seguridad en una aplicación webforJ:

```java title="LoginView.java"
// Página de inicio de sesión pública - cualquiera puede acceder
@Route("/login")
@AnonymousAccess
public class LoginView extends Composite<Login> {
  private final Login self = getBoundComponent();

  public LoginView() {  
    self.onSubmit(e -> {
      handleLogin(e.getUsername(), e.getPassword());
    });

    whenAttached().thenAccept(c -> {
      self.open();
    });
  }
}
```

```java title="ProductsView.java"
// Productos - requiere autenticación
@Route(value = "/", outlet = MainLayout.class)
public class ProductsView extends Composite<FlexLayout> {

  public ProductsView() {
    // vista de productos
  }
}
```

```java title="InvoicesView.java"
// Facturas - requiere rol de CONTADOR
@Route(value = "/invoices", outlet = MainLayout.class)
@RolesAllowed("ACCOUNTANT")
public class InvoicesView extends Composite<FlexLayout> {

  public InvoicesView() {
    // vista de facturas
  }
}
```

En esta configuración:

- La `LoginView` está marcada con `@AnonymousAccess`, permitiendo a los usuarios no autenticados acceder a ella.
- La `ProductsView` no tiene anotación de seguridad, lo que significa que requiere autenticación por defecto (cuando se activa el modo `secure-by-default`).
- La `InvoicesView` requiere el rol de `ACCOUNTANT`, por lo que solo los usuarios con permisos contables pueden acceder a las facturas.

## Cómo funciona la seguridad {#how-security-works}

Cuando un usuario intenta navegar a una ruta, el sistema de seguridad sigue este flujo:

1. **Navegación iniciada**: El usuario hace clic en un enlace o ingresa una URL.
2. **Verificación de seguridad**: Antes de renderizar el componente, el sistema evalúa las anotaciones y reglas de seguridad.
3. **Decisión**: Según el estado de autenticación y roles del usuario:
   - **Conceder**: Permitir la navegación y renderizar el componente.
   - **Denegar**: Bloquear la navegación y redirigir a la página de inicio de sesión o a la página de acceso denegado.
4. **Renderizar o redirigir**: Se muestra el componente solicitado o se redirige al usuario adecuadamente.

Con la aplicación automática, las reglas de seguridad se aplican de manera consistente en toda tu aplicación, por lo que el control de acceso se maneja antes de que se renderice cualquier componente y los desarrolladores no necesitan agregar verificaciones manuales en cada vista.

## Autenticación VS autorización {#authentication-vs-authorization}

Para implementar la seguridad correctamente en tu aplicación, es importante conocer la diferencia entre estos dos conceptos:

- **Autenticación**: Verificación de quién es el usuario. Esto suele ocurrir durante el inicio de sesión, cuando el usuario proporciona credenciales (nombre de usuario y contraseña). Una vez autenticado, la identidad del usuario se almacena en la sesión o en el contexto de seguridad.

- **Autorización**: Verificación de a qué puede acceder el usuario autenticado. Esto implica verificar si el usuario tiene los roles o permisos requeridos para acceder a una ruta específica. La autorización ocurre cada vez que un usuario navega a una ruta protegida.

El sistema de seguridad de webforJ maneja ambos aspectos:

- Anotaciones como `@PermitAll` manejan requisitos de autenticación.
- Anotaciones como `@RolesAllowed` manejan requisitos de autorización.

## Empezando {#getting-started}

Esta guía supone que estás utilizando **Spring Boot con Spring Security**, que es el enfoque recomendado para la mayoría de las aplicaciones webforJ. Spring Security proporciona autenticación y autorización estándar de la industria con configuración automática a través de Spring Boot.

El resto de esta documentación te guiará a través de la seguridad de tus rutas con Spring Security, desde la configuración básica hasta características avanzadas. Si no estás utilizando Spring Boot o necesitas una implementación de seguridad personalizada, consulta la [guía de Arquitectura de Seguridad](/docs/security/architecture/overview) para aprender cómo funciona el sistema y cómo implementar seguridad personalizada.

## Temas {#topics}

<DocCardList className="topics-section" />
