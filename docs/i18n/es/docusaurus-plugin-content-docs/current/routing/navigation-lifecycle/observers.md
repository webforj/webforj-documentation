---
sidebar_position: 2
title: Lifecycle Observers
_i18n_hash: 2c66b4194e4d93a762d9a8cd75918e49
---
Los observadores permiten que los componentes reaccionen a eventos del ciclo de vida al implementar interfaces para etapas específicas. Este patrón asegura una separación clara de preocupaciones y simplifica el manejo de la lógica de navegación.

## Observadores disponibles {#available-observers}

- **`WillEnterObserver`**: Te permite manejar tareas antes de que se entre a una ruta, como la obtención de datos necesarios o bloquear la navegación.
- **`DidEnterObserver`**: Ideal para manejar acciones después de que el componente ha sido adjuntado, como renderizar datos o activar animaciones.
- **`WillLeaveObserver`**: Proporciona una manera de gestionar la lógica antes de que un usuario salga de una ruta, como verificar cambios no guardados.
- **`DidLeaveObserver`**: Se utiliza para acciones de limpieza u otras tareas que deben ejecutarse después de que un componente se haya separado del DOM.

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

Aquí, `onWillEnter` verifica si el usuario está autenticado. Si no, la navegación es vetada, impidiendo que se complete la navegación y redirigiendo a la página de inicio de sesión en su lugar.

:::warning Ejemplo de Rutas Autenticadas - No Listo para Producción
Este anterior es solo un ejemplo de cómo usar rutas autenticadas.
Este **No es** un ejemplo de cómo escribir un sistema de autenticación a nivel de producción.
Necesitarás tomar los conceptos y patrones utilizados en este ejemplo y adaptarlos para trabajar con tu flujo/sistema de autenticación para tu aplicación.
:::

## Ejemplo: obteniendo datos al entrar a la ruta con `DidEnterObserver` {#example-fetching-data-on-route-entry-with-didenterobserver}

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
    // Código para actualizar la UI con los datos del perfil
  }
}
```

Este ejemplo demuestra el uso de `DidEnterObserver` para obtener y mostrar datos del perfil una vez que el componente está adjunto al DOM.

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
          "Cambios No Guardados",
          ConfirmDialog.OptionType.OK_CANCEL,
          ConfirmDialog.MessageType.WARNING);
    }
  }
}
```

En este ejemplo, `onWillLeave` presenta al usuario un cuadro de diálogo de confirmación si hay cambios no guardados, vetando la navegación si el usuario elige quedarse.

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

Este ejemplo limpia las notificaciones después de que el usuario deja la `NotificationsView`, utilizando el `DidLeaveObserver` para la limpieza.
