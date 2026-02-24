---
sidebar_position: 11
title: Locale Management
sidebar_class_name: new-content
---

# Locale management <DocChip chip='since' label='25.10' />

webforJ provides built-in support for managing the app locale. The locale determines which language and regional formatting is used throughout the app. Components can react to locale changes through the `LocaleObserver` interface, allowing the UI to update immediately when the user switches languages.

## Setting the default locale {#setting-the-default-locale}

The app locale can be configured using the `webforj.locale` property. This sets the locale that the app uses from startup, affecting all locale-sensitive formatting and text. When `webforj.locale` isn't configured, the app falls back to the server's JVM default locale. You can read the current locale at any time with `App.getLocale()`.

See the [Configuration](/docs/configuration/properties) section to learn how to set properties for different environments.

## Changing the locale {#changing-the-locale}

To change the locale at runtime, call `App.setLocale()`. This updates the locale for the entire app and notifies all components that implement `LocaleObserver`, allowing the UI to update without a page reload.

```java
App.setLocale(Locale.GERMAN);
App.setLocale(Locale.forLanguageTag("fr"));
```

## Browser locale detection <DocChip chip='since' label='25.12' /> {#browser-locale-detection}

When auto-detect is enabled, webforJ reads the browser's preferred languages on startup and sets the app locale to the best match from the configured supported locales. If no match is found, the first supported locale is used as the default.

Enable auto-detection by setting `webforj.i18n.auto-detect` to `true` and configuring `webforj.i18n.supported-locales` with the locales your app supports. See the [Configuration](/docs/configuration/properties) section to learn how to set properties for different environments.

:::info Requires supported locales
Auto-detection requires `supported-locales` to be configured. If the list is empty, auto-detection has no effect and the app uses the default locale from `webforj.locale`.
:::

## The `LocaleObserver` interface {#the-localeobserver-interface}

Components that need to update their content when the locale changes should implement the `LocaleObserver` interface. webforJ automatically registers and unregisters observers as components are created and destroyed.

```java title="LocaleObserver.java"
@FunctionalInterface
public interface LocaleObserver {
    void onLocaleChange(LocaleEvent event);
}
```

When the locale changes, `onLocaleChange` is called with the new locale. Inside this method, update any locale-sensitive text or formatting:

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

:::tip Built-in translation support
Starting with version 25.12, webforJ provides a built-in [translation system](/docs/advanced/i18n-localization) that supports resource bundles, custom resolvers, automatic browser locale detection, and locale-aware data binding.
:::

### `LocaleEvent` {#localeevent}

The `LocaleEvent` passed to `onLocaleChange()` provides the new locale and the component that received the event:

| Method | Returns | Description |
|--------|---------|-------------|
| `getLocale()` | `Locale` | The new locale that was set |
| `getSource()` | `Object` | The component that received the event |

## Manual locale updates {#manual-locale-updates}

Not everything reacts to locale changes automatically. Some components, like [Masked Fields](/docs/components/fields/masked/overview), read `App.getLocale()` once during creation to configure locale-sensitive formatting but don't implement `LocaleObserver`. When the locale changes at runtime, these need to be updated explicitly inside your `onLocaleChange()` handler:

```java
public class OrderForm extends Composite<FlexLayout> implements LocaleObserver {
  private MaskedDateField dateField = new MaskedDateField("Date");
  private MaskedTimeField timeField = new MaskedTimeField("Time");

  @Override
  public void onLocaleChange(LocaleEvent event) {
    Locale newLocale = event.getLocale();
    dateField.setLocale(newLocale);
    timeField.setLocale(newLocale);
  }
}
```

:::tip Data binding
`BindingContext` supports locale-aware validation and transformation messages. See [dynamic validation messages](/docs/data-binding/validation/validators#dynamic-validation-messages) and [locale-aware Jakarta Validation](/docs/data-binding/validation/jakarta-validation#locale-aware-validation-messages).
:::
