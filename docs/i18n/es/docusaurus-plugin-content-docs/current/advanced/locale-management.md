---
sidebar_position: 11
title: Locale Management
sidebar_class_name: new-content
_i18n_hash: cfcad51aaedd77b781334fc048b0a4f1
---
# Gestión de locales <DocChip chip='since' label='25.10' />

webforJ proporciona soporte integrado para gestionar el locale de la aplicación. El locale determina qué idioma y formato regional se utiliza en toda la aplicación. Los componentes pueden reaccionar a los cambios de locale a través de la interfaz `LocaleObserver`, lo que permite que la interfaz de usuario se actualice inmediatamente cuando el usuario cambia de idioma.

## Configuración del locale predeterminado {#setting-the-default-locale}

El locale de la aplicación se puede configurar utilizando la propiedad `webforj.locale`. Esto establece el locale que utiliza la aplicación desde el inicio, afectando a todo formato y texto sensible al locale. Cuando `webforj.locale` no está configurado, la aplicación utiliza el locale predeterminado de la JVM del servidor. Puedes leer el locale actual en cualquier momento con `App.getLocale()`.

Consulta la sección de [Configuración](/docs/configuration/properties) para aprender cómo establecer propiedades para diferentes entornos.

## Cambiando el locale {#changing-the-locale}

Para cambiar el locale en tiempo de ejecución, llama a `App.setLocale()`. Esto actualiza el locale para toda la aplicación y notifica a todos los componentes que implementan `LocaleObserver`, permitiendo que la interfaz de usuario se actualice sin recargar la página.

```java
App.setLocale(Locale.GERMAN);
App.setLocale(Locale.forLanguageTag("fr"));
```

## Detección del locale del navegador <DocChip chip='since' label='25.12' /> {#browser-locale-detection}

Cuando la auto-detección está habilitada, webforJ lee los idiomas preferidos del navegador al inicio y establece el locale de la aplicación en la mejor coincidencia de los locales soportados configurados. Si no se encuentra coincidencia, se utiliza el primer locale soportado como predeterminado.

Habilita la auto-detección configurando `webforj.i18n.auto-detect` en `true` y configurando `webforj.i18n.supported-locales` con los locales que tu aplicación soporta. Consulta la sección de [Configuración](/docs/configuration/properties) para aprender cómo establecer propiedades para diferentes entornos.

:::info Requiere locales soportados
La auto-detección requiere que `supported-locales` esté configurado. Si la lista está vacía, la auto-detección no tiene efecto y la aplicación utiliza el locale predeterminado de `webforj.locale`.
:::

## La interfaz `LocaleObserver` {#the-localeobserver-interface}

Los componentes que necesitan actualizar su contenido cuando cambia el locale deben implementar la interfaz `LocaleObserver`. webforJ registra y desregistra automáticamente los observadores a medida que los componentes son creados y destruidos.

```java title="LocaleObserver.java"
@FunctionalInterface
public interface LocaleObserver {
    void onLocaleChange(LocaleEvent event);
}
```

Cuando el locale cambia, se llama a `onLocaleChange` con el nuevo locale. Dentro de este método, actualiza cualquier texto o formato sensible al locale:

```java title="MainLayout.java"
@Route
public class MainLayout extends Composite<AppLayout>
    implements HasTranslation, LocaleObserver {

  private final AppLayout self = getBoundComponent();
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
A partir de la versión 25.12, webforJ proporciona un [sistema de traducción](/docs/advanced/i18n-localization) integrado que soporta bundles de recursos, resolutores personalizados, detección automática del locale del navegador y vinculación de datos consciente del locale.
:::

### `LocaleEvent` {#localeevent}

El `LocaleEvent` pasado a `onLocaleChange()` proporciona el nuevo locale y el componente que recibió el evento:

| Método | Devuelve | Descripción |
|--------|---------|-------------|
| `getLocale()` | `Locale` | El nuevo locale que se estableció |
| `getSource()` | `Object` | El componente que recibió el evento |

## Actualizaciones manuales del locale {#manual-locale-updates}

No todo reacciona a los cambios de locale automáticamente. Algunos componentes, como los [Campos enmascarados](/docs/components/fields/masked/overview), leen `App.getLocale()` una vez durante la creación para configurar el formato sensible al locale, pero no implementan `LocaleObserver`. Cuando el locale cambia en tiempo de ejecución, estos necesitan ser actualizados explícitamente dentro de tu manejador `onLocaleChange()`:

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

:::tip Vinculación de datos
`BindingContext` soporta mensajes de validación y transformación conscientes del locale. Consulta [mensajes de validación dinámicos](/docs/data-binding/validation/validators#dynamic-validation-messages) y [validación de Jakarta consciente del locale](/docs/data-binding/validation/jakarta-validation#locale-aware-validation-messages).
:::
