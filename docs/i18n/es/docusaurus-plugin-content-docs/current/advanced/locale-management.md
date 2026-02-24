---
sidebar_position: 11
title: Locale Management
sidebar_class_name: new-content
_i18n_hash: 066d555fcf006756c6ae0e542d409d77
---
# Gestión de localización <DocChip chip='since' label='25.10' />

webforJ proporciona soporte integrado para gestionar la localización de la aplicación. La localización determina qué idioma y formato regional se utilizan en toda la aplicación. Los componentes pueden reaccionar a los cambios de localización a través de la interfaz `LocaleObserver`, lo que permite que la interfaz de usuario se actualice inmediatamente cuando el usuario cambia de idioma.

## Configuración de la localización predeterminada {#setting-the-default-locale}

La localización de la aplicación se puede configurar utilizando la propiedad `webforj.locale`. Esto establece la localización que la aplicación utiliza desde el inicio, afectando todo el formato y texto sensible a la localización. Cuando `webforj.locale` no se configura, la aplicación vuelve a la localización predeterminada de la JVM del servidor. Puedes leer la localización actual en cualquier momento con `App.getLocale()`.

Consulta la sección [Configuración](/docs/configuration/properties) para aprender cómo establecer propiedades para diferentes entornos.

## Cambiar la localización {#changing-the-locale}

Para cambiar la localización en tiempo de ejecución, llama a `App.setLocale()`. Esto actualiza la localización para toda la aplicación y notifica a todos los componentes que implementan `LocaleObserver`, permitiendo que la interfaz de usuario se actualice sin recargar la página.

```java
App.setLocale(Locale.GERMAN);
App.setLocale(Locale.forLanguageTag("fr"));
```

## Detección de localización del navegador <DocChip chip='since' label='25.12' /> {#browser-locale-detection}

Cuando la detección automática está habilitada, webforJ lee los idiomas preferidos del navegador al iniciar y establece la localización de la aplicación en la mejor coincidencia entre las localizaciones soportadas configuradas. Si no se encuentra ninguna coincidencia, se utiliza la primera localización soportada como predeterminada.

Habilita la detección automática configurando `webforj.i18n.auto-detect` en `true` y configurando `webforj.i18n.supported-locales` con las localizaciones que tu aplicación soporta. Consulta la sección [Configuración](/docs/configuration/properties) para aprender cómo establecer propiedades para diferentes entornos.

:::info Requiere localizaciones soportadas
La detección automática requiere que se configuren las `supported-locales`. Si la lista está vacía, la detección automática no tiene efecto y la aplicación utiliza la localización predeterminada de `webforj.locale`.
:::

## La interfaz `LocaleObserver` {#the-localeobserver-interface}

Los componentes que necesitan actualizar su contenido cuando cambia la localización deben implementar la interfaz `LocaleObserver`. webforJ registra y da de baja automáticamente a los observadores a medida que se crean y destruyen los componentes.

```java title="LocaleObserver.java"
@FunctionalInterface
public interface LocaleObserver {
    void onLocaleChange(LocaleEvent event);
}
```

Cuando cambia la localización, se llama a `onLocaleChange` con la nueva localización. Dentro de este método, actualiza cualquier texto o formato sensible a la localización:

```java title="MainLayout.java"
@Route
public class MainLayout extends Composite<AppLayout>
    implements HasTranslation, LocaleObserver {

  private AppLayout self = getBoundComponent();
  private AppNavItem inboxItem;
  private AppNavItem outboxItem;

  public MainLayout() {
    inboxItem = new AppNavItem(t("menu.inbox"), InboxView.class, TablerIcon.create("inbox"));
    outboxItem = new AppNavItem(t("menu.outbox"), OutboxView.class, TablerIcon.create("send-2"));

    AppNav appNav = new AppNav();
    appNav.addItem(inboxItem);
    appNav.addItem(outboxItem);

    self.addToDrawer(appNav);
  }

  @Override
  public void onLocaleChange(LocaleEvent event) {
    inboxItem.setText(t("menu.inbox"));
    outboxItem.setText(t("menu.outbox"));
  }
}
```

:::tip Soporte de traducción integrado
A partir de la versión 25.12, webforJ proporciona un [sistema de traducción](/docs/advanced/i18n-localization) integrado que soporta bundles de recursos, resolutores personalizados, detección automática de la localización del navegador y enlace de datos sensible a la localización.
:::

### `LocaleEvent` {#localeevent}

El `LocaleEvent` pasado a `onLocaleChange()` proporciona la nueva localización y el componente que recibió el evento:

| Método | Devuelve | Descripción |
|--------|---------|-------------|
| `getLocale()` | `Locale` | La nueva localización que se estableció |
| `getSource()` | `Object` | El componente que recibió el evento |

## Actualizaciones manuales de localización {#manual-locale-updates}

No todo reacciona a los cambios de localización automáticamente. Algunos componentes, como los [Campos enmascarados](/docs/components/fields/masked/overview), leen `App.getLocale()` una vez durante la creación para configurar el formato sensible a la localización, pero no implementan `LocaleObserver`. Cuando la localización cambia en tiempo de ejecución, estos deben actualizarse explícitamente dentro de tu manejador `onLocaleChange()`:

```java
public class OrderForm extends Composite<FlexLayout> implements LocaleObserver {
  private MaskedDateField dateField = new MaskedDateField("Fecha");
  private MaskedTimeField timeField = new MaskedTimeField("Hora");

  @Override
  public void onLocaleChange(LocaleEvent event) {
    Locale newLocale = event.getLocale();
    dateField.setLocale(newLocale);
    timeField.setLocale(newLocale);
  }
}
```

:::tip Enlace de datos
`BindingContext` soporta validación y mensajes de transformación sensibles a la localización. Consulta [mensajes de validación dinámicos](/docs/data-binding/validation/validators#dynamic-validation-messages) y [validación de Jakarta sensible a la localización](/docs/data-binding/validation/jakarta-validation#locale-aware-validation-messages).
:::
