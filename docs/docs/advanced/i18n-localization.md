---
sidebar_position: 11
title: Localization
sidebar_class_name: new-content
---

# Localization <DocChip chip='since' label='25.10' />

Components implementing the `LocaleObserver` interface receive automatic notifications when the locale changes. This enables UI elements to update their text, formatting, and other locale-specific content without manual coordination.

## The `LocaleObserver` interface {#the-localeobserver-interface}

```java title="LocaleObserver.java"
@FunctionalInterface
public interface LocaleObserver extends Serializable {
    void onLocaleChange(LocaleEvent event);
}
```

When a component implements this interface, webforJ automatically:
- Registers the component when created to receive locale change events
- Unregisters the component when destroyed
- Calls `onLocaleChange()` whenever the locale is changed

This registration happens through the component lifecycle.

## Handling translations {#handling-translations}

When `onLocaleChange()` is called, components receive the new locale. How they load and apply translations is up to the developer. Common approaches include:

- Java `ResourceBundle` with property files
- Database queries for translations
- Custom translation providers
- Hard-coded maps for simple cases

This example uses `ResourceBundle`, which stores translations in property files:

```
messages.properties        # Fallback/default
messages_en.properties     # English
messages_de.properties     # German
```

Property files contain key-value pairs:

```properties title="messages_en.properties"
app.title=Mailbox
menu.inbox=Inbox
```

```properties title="messages_de.properties"
app.title=Postfach
menu.inbox=Posteingang
```
## Changing the locale {#changing-the-locale}

Use `App.setLocale()` to change the app locale. This triggers notifications to all registered observers:

```java
App.setLocale(Locale.GERMAN);
App.setLocale(Locale.forLanguageTag("fr"));
```

A typical implementation could use a dropdown or choice component:

```java
ChoiceBox languageSelector = new ChoiceBox();
languageSelector.add("en", "English");
languageSelector.add("de", "Deutsch");
languageSelector.add("fr", "Français");

languageSelector.onSelect(e -> {
  String lang = (String) e.getSelectedItem().getKey();
  Locale newLocale = Locale.forLanguageTag(lang);

  App.setLocale(newLocale);
});
```

When the user selects a language, `App.setLocale()` fires, and all components implementing `LocaleObserver` receive the update.

## Implementing observers {#implementing-observers}

When a component implements `LocaleObserver`, it needs to handle two scenarios: initial rendering with the current locale, and updating when the locale changes. The following example demonstrates this pattern with a component that displays localized text and links.

The component stores references to elements that need translation updates. When constructed, it loads the current locale's translations. When the locale changes, `onLocaleChange()` fires, allowing the component to reload translations and update its displayed text.

```java title="TranslationService.java"
import com.webforj.App;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class TranslationService {
  private final MessageSource messageSource;

  public TranslationService(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public String get(String key) {
    return messageSource.getMessage(key, null, App.getLocale());
  }
}
```

```java title="Explore.java"
public class Explore extends Composite<FlexLayout> implements LocaleObserver {
  private final TranslationService i18n;
  private FlexLayout self = getBoundComponent();
  private H3 titleElement;
  private Anchor anchor;
  private String titleKey;

  public Explore(TranslationService i18n, String titleKey) {
    this.i18n = i18n;
    this.titleKey = titleKey;

    self.addClassName("explore-component");
    self.setStyle("margin", "1em auto");
    self.setDirection(FlexDirection.COLUMN);
    self.setAlignment(FlexAlignment.CENTER);
    self.setMaxWidth(300);
    self.setSpacing(".3em");

    Img img = new Img(String.format("ws://explore/%s.svg", titleKey), "mailbox");
    img.setMaxWidth(250);

    String translatedTitle = i18n.get("menu." + titleKey.toLowerCase());
    titleElement = new H3(translatedTitle);

    anchor = new Anchor("https://docs.webforj.com/docs/components/overview", i18n.get("explore.link"));
    anchor.setTarget("_blank");

    self.add(img, titleElement, anchor);
  }

  @Override
  public void onLocaleChange(LocaleEvent event) {
    titleElement.setText(i18n.get("menu." + titleKey.toLowerCase()));
    anchor.setText(i18n.get("explore.link"));
  }
}
```

The component stores references to elements that display translated content (`titleElement` and `anchor`). Translations are loaded in the constructor using the current locale. When the locale changes, `onLocaleChange()` updates only the text that needs translation.

## Lifecycle management {#lifecycle-management}

The framework handles observer registration automatically through component lifecycle hooks:

- **On create**: Components implementing `LocaleObserver` are registered in `LocaleObserverRegistry`
- **On destroy**: Components are unregistered to prevent memory leaks

Each app instance maintains its own observer registry. This automatic management means:

- No manual register/unregister calls
- No memory leaks from destroyed components
- Thread-safe concurrent notifications

:::info Per-app registry
Each app instance maintains its own observer registry. Observers registered in one app don't receive notifications from other apps running in the same JVM.
:::

## `LocaleEvent` {#localeevent}

The `LocaleEvent` passed to `onLocaleChange()` provides:

| Method | Returns | Description |
|--------|---------|-------------|
| `getLocale()` | `Locale` | The new locale that was set |
| `getSource()` | `Object` | The component that received the event |

```java
@Override
public void onLocaleChange(LocaleEvent event) {
  Locale newLocale = event.getLocale();
  Object source = event.getSource();

  // Update component using new locale
  ResourceBundle bundle = ResourceBundle.getBundle("messages", newLocale);
  updateUI(bundle);
}
```