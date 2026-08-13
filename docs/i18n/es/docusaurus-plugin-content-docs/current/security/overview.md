---
sidebar_position: 1
title: Security
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Protect webforJ routes with declarative annotations and centralized
  authentication and authorization enforcement.
_i18n_hash: 850b9636996cb17a07a7aff25ac3cd0e
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
Esta característica está en vista pública y lista para su uso en producción. Durante el período de vista previa, las API pueden ser refinadas en base a los comentarios de la comunidad de desarrolladores. Cualquier cambio será anunciado con anticipación a través de las notas de la versión y se proporcionarán guías de migración cuando sea necesario.
:::

En aplicaciones web modernas, **la seguridad** se refiere a controlar el acceso a diferentes partes de tu aplicación basado en la identidad y permisos del usuario. En webforJ, la seguridad proporciona un marco para el **control de acceso a nivel de ruta**, donde puedes proteger vistas, requerir autenticación y hacer cumplir permisos basados en roles.

<AISkillTip skill="webforj-securing-apps" />

## Routing Tradicional VS asegurado {#traditional-vs-secured-routing}

En el enrutamiento tradicional no asegurado, todas las rutas en tu aplicación son accesibles para cualquiera que conozca la URL. Esto significa que los usuarios pueden navegar a páginas sensibles como paneles de administración o tableros de usuarios sin ninguna verificación de autenticación o autorización. La carga recae sobre los desarrolladores para verificar manualmente los permisos en cada componente, lo que lleva a una aplicación inconsistente de la seguridad y posibles vulnerabilidades.

Este enfoque introduce varios problemas:

1. **Controles manuales**: Los desarrolladores deben recordar agregar lógica de seguridad en cada vista o diseño protegido.
2. **Aplicación inconsistente**: Los controles de seguridad dispersos a lo largo del código conducen a brechas y errores.
3. **Sobrecarga de mantenimiento**: Cambiar las reglas de acceso requiere actualizar múltiples archivos.
4. **Sin control centralizado**: No hay un solo lugar para entender o gestionar la seguridad de la aplicación.

El **enrutamiento asegurado** en webforJ resuelve esto al habilitar el control de acceso directamente a nivel de ruta. El sistema de seguridad aplica automáticamente las reglas antes de que se renderice cualquier componente, proporcionando un enfoque centralizado y declarativo para la seguridad de la aplicación. Así es como funciona:

1. **Anotaciones declarativas**: Marca las rutas con anotaciones de seguridad para definir los requisitos de acceso.
2. **Aplicación automática**: El sistema de seguridad verifica permisos antes de renderizar cualquier vista.
3. **Configuración centralizada**: Define el comportamiento de seguridad en un solo lugar y aplícalo de manera consistente.
4. **Implementaciones flexibles**: Elige entre la integración de Spring Security o una implementación personalizada en Java puro.

Este diseño permite **autenticación** (verificar la identidad del usuario) y **autorización** (verificar a qué puede acceder el usuario), de modo que solo se concede acceso a rutas protegidas a usuarios autorizados. Los usuarios no autorizados son redirigidos automáticamente o se les deniega el acceso basado en las reglas de seguridad configuradas.

## Ejemplo de enrutamiento asegurado en webforJ {#example-of-secured-routing-in-webforj}

Aquí tienes un ejemplo que muestra diferentes niveles de seguridad en una aplicación webforJ:

```java title="LoginView.java"
// Página de inicio de sesión pública - cualquier persona puede acceder
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

- `LoginView` está marcado con `@AnonymousAccess`, lo que permite a los usuarios no autenticados acceder a él.
- `ProductsView` no tiene anotación de seguridad, lo que significa que requiere autenticación por defecto (cuando se habilita el modo `secure-by-default`).
- `InvoicesView` requiere el rol de `ACCOUNTANT`, por lo que solo los usuarios con permisos contables pueden acceder a las facturas.

## Cómo funciona la seguridad {#how-security-works}

Cuando un usuario intenta navegar a una ruta, el sistema de seguridad sigue este flujo:

1. **Navegación iniciada**: El usuario hace clic en un enlace o ingresa una URL.
2. **Verificación de seguridad**: Antes de renderizar el componente, el sistema evalúa las anotaciones y reglas de seguridad.
3. **Decisión**: Basado en el estado de autenticación del usuario y los roles:
   - **Conceder**: Permite la navegación y renderiza el componente.
   - **Denegar**: Bloquea la navegación y redirige a la página de inicio de sesión o a la página de acceso denegado.
4. **Renderizar o redirigir**: Se muestra el componente solicitado, o se redirige al usuario apropiadamente.

Con la aplicación automática de las reglas de seguridad, se aplican de manera consistente en toda tu aplicación, por lo que el control de acceso se gestiona antes de que se renderice cualquier componente y los desarrolladores no necesitan agregar verificaciones manuales en cada vista.

## Autenticación VS autorización {#authentication-vs-authorization}

Para implementar correctamente la seguridad en tu aplicación, es importante conocer la diferencia entre estos dos conceptos:

- **Autenticación**: Verificar quién es el usuario. Esto suele ocurrir durante el inicio de sesión cuando el usuario proporciona sus credenciales (nombre de usuario y contraseña). Una vez autenticado, la identidad del usuario se almacena en la sesión o en el contexto de seguridad.

- **Autorización**: Verificar a qué puede acceder el usuario autenticado. Esto implica comprobar si el usuario tiene los roles o permisos requeridos para acceder a una ruta específica. La autorización ocurre cada vez que un usuario navega a una ruta protegida.

El sistema de seguridad de webforJ gestiona ambos aspectos:

- Anotaciones como `@PermitAll` manejan los requisitos de autenticación.
- Anotaciones como `@RolesAllowed` manejan los requisitos de autorización.

## Empezando {#getting-started}

Esta guía asume que estás utilizando **Spring Boot con Spring Security**, que es el enfoque recomendado para la mayoría de las aplicaciones webforJ. Spring Security proporciona autenticación y autorización estándar de la industria con configuración automática a través de Spring Boot.

El resto de esta documentación te guiará a través de la seguridad de tus rutas con Spring Security, desde la configuración básica hasta características avanzadas. Si no estás utilizando Spring Boot o necesitas una implementación de seguridad personalizada, consulta la [guía de Arquitectura de Seguridad](/docs/security/architecture/overview) para aprender cómo funciona el sistema y cómo implementar seguridad personalizada.

## Temas {#topics}

<DocCardList className="topics-section" />
