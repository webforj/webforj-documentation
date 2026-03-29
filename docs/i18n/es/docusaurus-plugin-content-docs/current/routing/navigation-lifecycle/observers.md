---
sidebar_position: 2
title: Lifecycle Observers
_i18n_hash: a584e996523ba2b98ecb9d7ab2f366f3
---
Los observadores permiten que los componentes reaccionen a eventos del ciclo de vida al implementar interfaces para etapas específicas. Este patrón asegura una separación clara de responsabilidades y simplifica el manejo de la lógica de navegación.

## Observadores disponibles {#available-observers}

- **`WillEnterObserver`**: Te permite manejar tareas antes de que se ingrese a una ruta, como recuperar datos necesarios o bloquear la navegación.
- **`DidEnterObserver`**: Ideal para manejar acciones después de que el componente ha sido adjuntado, como renderizar datos o activar animaciones.
- **`WillLeaveObserver`**: Proporciona una forma de gestionar la lógica antes de que un usuario abandone una ruta, como verificar cambios no guardados.
- **`DidLeaveObserver`**: Se utiliza para acciones de limpieza u otras tareas que deben ejecutarse después de que un componente se haya desacoplado del DOM.
- **`ActivateObserver`**: <DocChip chip='since' label='25.03' /> Se activa cuando un componente en caché es reactivado, como al navegar a la misma ruta con diferentes parámetros.

## Ejemplo: autenticación con `WillEnterObserver` {#example-authentication-with-willenterobserver}

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> implements WillEnterObserver {

  @Override
  public void onWillEnter(WillEnterEvent event, ParametersBag parameters) {
    boolean isAuthenticated = authService.isAuthenticated();
    event.veto(!isAuthenticated);

    if (!isAuthenticated) {
      event.getRouter().navigate(LoginView.class);
    }
  }
}
```

Aquí, `onWillEnter` verifica si el usuario está autenticado. Si no, se veta la navegación, evitando que se complete y redirigiendo a la página de inicio de sesión en su lugar.

:::warning Ejemplo de Rutas Autenticadas - No Listo para Producción
Este ejemplo es solo una ilustración de cómo usar rutas autenticadas.
**No es** un ejemplo de cómo escribir un sistema de autenticación a nivel de producción.
Necesitarás tomar los conceptos y patrones utilizados en este ejemplo y adaptarlos para que funcionen con tu flujo/sistema de autenticación para tu aplicación.
:::

## Ejemplo: recuperando datos al ingresar a la ruta con `DidEnterObserver` {#example-fetching-data-on-route-entry-with-didenterobserver}

```java
@Route(value = "profile")
public class ProfileView extends Composite<Div> implements DidEnterObserver {

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    String userId = parameters.get("userId").orElseThrow();
    UserService.fetchProfile(userId).thenAccept(
        profile -> updateProfileUI(profile));
  }

  private void updateProfileUI(Profile profile) {
    // Código para actualizar la interfaz de usuario con los datos del perfil
  }
}
```

Este ejemplo demuestra el uso de `DidEnterObserver` para recuperar y mostrar datos del perfil una vez que el componente está adjunto al DOM.

## Ejemplo: Manejando cambios no guardados con `WillLeaveObserver` {#example-handling-unsaved-changes-with-willleaveobserver}

```java
@Route(value = "edit-profile")
public class EditProfileView extends Composite<Div> implements WillLeaveObserver {
  private boolean hasUnsavedChanges = false;

  public EditProfileView() {
    // Lógica para detectar cambios no guardados
  }

  @Override
  public void onWillLeave(WillLeaveEvent event, ParametersBag parameters) {
    event.veto(hasUnsavedChanges);

    if(hasUnsavedChanges) {
      ConfirmDialog.Result result = showConfirmDialog(
          "Existen cambios no guardados. ¿Deseas descartarlos o guardarlos?",
          "Cambios No Guardados",
          ConfirmDialog.OptionType.OK_CANCEL,
          ConfirmDialog.MessageType.WARNING);
    }
  }
}
```

En este ejemplo, `onWillLeave` muestra un cuadro de diálogo de confirmación al usuario si hay cambios no guardados, vetando la navegación si el usuario elige quedarse.

:::info Bloqueo de Navegación y Manejo de Veto
Para más información sobre cómo bloquear la navegación, consulta [Bloqueo de Navegación y Manejo de Veto](./navigation-blocking)
:::

## Ejemplo: Limpieza con `DidLeaveObserver` {#example-cleanup-with-didleaveobserver}

```java
@Route(value = "notifications")
public class NotificationsView extends Composite<Div> implements DidLeaveObserver {

  @Override
  public void onDidLeave(DidLeaveEvent event, ParametersBag parameters) {
    NotificationService.clearActiveNotifications();
  }
}
```

Este ejemplo limpia las notificaciones después de que el usuario abandona el `NotificationsView`, utilizando el `DidLeaveObserver` para la limpieza.

## Ejemplo: Refrescando datos con `ActivateObserver` <DocChip chip='since' label='25.03' /> {#example-refreshing-data-with-activateobserver}

```java
@Route(value = "product/:id")
public class ProductView extends Composite<Div> implements ActivateObserver {
  private String currentProductId;

  @Override
  public void onActivate(ActivateEvent event, ParametersBag parameters) {
    String productId = parameters.get("id").orElseThrow();
    
    // El componente se está reutilizando con diferentes parámetros
    if (!productId.equals(currentProductId)) {
      currentProductId = productId;
      refreshProductData(productId);
    }
  }

  private void refreshProductData(String productId) {
    // Código para recuperar y mostrar nuevos datos del producto
    ProductService.fetchProduct(productId).thenAccept(
        product -> updateProductUI(product));
  }
}
```

Este ejemplo demuestra el uso de `ActivateObserver` para refrescar datos al navegar a la misma ruta con diferentes parámetros. El componente permanece en caché y se reactiva en lugar de recrearse, por lo que la interfaz de usuario se actualiza para mostrar los datos correctos para los parámetros actuales sin instanciar un nuevo componente.

:::tip Activación en Jerarquías de Componentes
Al navegar a una ruta, el evento `Activate` se dispara para **todos los componentes en caché en la jerarquía** que permanecen en la ruta actual. Por ejemplo, al navegar de `/products/123` a `/products/456`, tanto el componente padre `ProductsLayout` como el componente hijo `ProductView` reciben el evento `Activate` si están en caché y permanecen en la jerarquía de rutas.
:::
