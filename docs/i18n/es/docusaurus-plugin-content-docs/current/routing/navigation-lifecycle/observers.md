---
sidebar_position: 2
title: Lifecycle Observers
_i18n_hash: 18390849527056ed2780b761ae7919c1
---
Los observadores permiten que los componentes reaccionen a eventos del ciclo de vida al implementar interfaces para etapas específicas. Este patrón garantiza una separación clara de preocupaciones y simplifica el manejo de la lógica de navegación.

## Observadores disponibles {#available-observers}

- **`WillEnterObserver`**: Te permite manejar tareas antes de que se entre en una ruta, como la obtención de datos necesarios o bloquear la navegación.
- **`DidEnterObserver`**: Ideal para manejar acciones después de que el componente ha sido adjuntado, como renderizar datos o disparar animaciones.
- **`WillLeaveObserver`**: Proporciona una manera de gestionar la lógica antes de que un usuario salga de una ruta, como verificar si hay cambios no guardados.
- **`DidLeaveObserver`**: Se utiliza para acciones de limpieza u otras tareas que deben ejecutarse después de que un componente se haya separado del DOM.
- **`ActivateObserver`** (desde 25.03): Se activa cuando un componente en caché es reactivado, como al navegar a la misma ruta con diferentes parámetros.

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

Aquí, `onWillEnter` verifica si el usuario está autenticado. Si no lo está, la navegación es vetada, impidiendo que la navegación se complete y redirigiendo a la página de inicio de sesión en su lugar.

:::warning Ejemplo de Rutas Autenticadas - No Listo para Producción
Este ejemplo anterior es solo una demostración de cómo usar rutas autenticadas.
Esto **NO** es un ejemplo de cómo deberías escribir un sistema de autenticación a nivel de producción.
Necesitarás tomar los conceptos y patrones utilizados en este ejemplo y adaptarlos para trabajar con tu flujo/sistema de autenticación para tu aplicación.
:::

## Ejemplo: obteniendo datos al ingresar a la ruta con `DidEnterObserver` {#example-fetching-data-on-route-entry-with-didenterobserver}

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

Este ejemplo demuestra el uso de `DidEnterObserver` para obtener y mostrar los datos del perfil una vez que el componente está adjunto al DOM.

## Ejemplo: Manejo de cambios no guardados con `WillLeaveObserver` {#example-handling-unsaved-changes-with-willleaveobserver}

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
          "Hay cambios no guardados. ¿Quieres descartarlos o guardarlos?",
          "Cambios no guardados",
          ConfirmDialog.OptionType.OK_CANCEL,
          ConfirmDialog.MessageType.WARNING);
    }
  }
}
```

En este ejemplo, `onWillLeave` solicita al usuario un diálogo de confirmación si hay cambios no guardados, vetando la navegación si el usuario elige quedarse.

:::info Bloqueo de Navegación y Manejo de Veto
Para más información sobre el bloqueo de navegación, consulta [Bloqueo de Navegación y Manejo de Veto](./navigation-blocking)
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

Este ejemplo limpia las notificaciones después de que el usuario abandona la `NotificationsView`, utilizando `DidLeaveObserver` para la limpieza.

## Ejemplo: Refrescar datos con `ActivateObserver` {#example-refreshing-data-with-activateobserver}

:::info Desde 25.03
El `ActivateObserver` y `ActivateEvent` están disponibles a partir de la versión `25.03` de webforJ.
:::

```java
@Route(value = "product/:id")
public class ProductView extends Composite<Div> implements ActivateObserver {
  private String currentProductId;

  @Override
  public void onActivate(ActivateEvent event, ParametersBag parameters) {
    String productId = parameters.get("id").orElseThrow();
    
    // El componente está siendo reutilizado con diferentes parámetros
    if (!productId.equals(currentProductId)) {
      currentProductId = productId;
      refreshProductData(productId);
    }
  }

  private void refreshProductData(String productId) {
    // Código para obtener y mostrar nuevos datos del producto
    ProductService.fetchProduct(productId).thenAccept(
        product -> updateProductUI(product));
  }
}
```

Este ejemplo demuestra el uso de `ActivateObserver` para refrescar datos al navegar a la misma ruta con diferentes parámetros. El componente permanece en caché y es reactivado en lugar de recreado, por lo que la interfaz de usuario se actualiza para mostrar los datos correctos para los parámetros actuales sin instanciar un nuevo componente.

:::tip Activación en Jerarquías de Componentes
Al navegar a una ruta, el evento `Activate` se activa para **todos los componentes en caché en la jerarquía** que permanecen en el camino actual. Por ejemplo, al navegar de `/products/123` a `/products/456`, tanto el componente padre `ProductsLayout` como el componente hijo `ProductView` reciben el evento `Activate` si están en caché y permanecen en la jerarquía de la ruta.
:::
