---
sidebar_position: 12
title: Translation
_i18n_hash: 276130dcd9ff26441b844042d4cdc5dd
---
# Translation <DocChip chip='since' label='25.12' />

webforJ incluye un sistema de traducción incorporado para buscar cadenas localizadas por clave. El sistema consta de un resolvedor de traducciones que mapea claves a texto localizado, una interfaz de preocupación `HasTranslation` que proporciona un conveniente método `t()`, `App.getTranslation()` para acceso directo en cualquier lugar, detección automática de la configuración regional desde el navegador y soporte para fuentes de traducción personalizadas, como bases de datos.

<AISkillTip skill="webforj-localizing-apps" />

## Resolvedor de traducciones {#translation-resolver}

El resolvedor de traducciones es el sistema que busca cadenas localizadas para una clave y configuración regional dada. webforJ proporciona un resolvedor predeterminado, `BundleTranslationResolver`, que carga traducciones desde archivos de propiedades `ResourceBundle` de Java en el classpath. Esto funciona directamente sin dependencias adicionales.

### Archivos de paquete de recursos

Coloca tus archivos de traducción en el directorio `src/main/resources`. El resolvedor predeterminado busca archivos llamados `messages` con sufijos de configuración regional que siguen la convención de nomenclatura estándar de `ResourceBundle` de Java:

```text
messages.properties        # Traducciones predeterminadas/de respaldo
messages_en.properties     # Inglés
messages_de.properties     # Alemán
messages_fr_CA.properties  # Francés (Canadá)
```

Cada archivo contiene pares clave-valor. Las claves son identificadores que utilizas en el código, y los valores son las cadenas traducidas. Puedes incluir marcadores de posición de [`MessageFormat`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/text/MessageFormat.html) como `{0}`, `{1}` para valores dinámicos:

```properties title="messages.properties"
app.title=Mailbox
menu.inbox=Inbox
menu.outbox=Outbox
greeting=Hello {0}, you have {1} new messages
```

```properties title="messages_de.properties"
app.title=Postfach
menu.inbox=Posteingang
menu.outbox=Postausgang
greeting=Hallo {0}, Sie haben {1} neue Nachrichten
```

El resolvedor delega en la cadena de resolución estándar de [`ResourceBundle`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/ResourceBundle.html) de Java, que maneja la coincidencia de configuración regional y el respaldo automáticamente.

### Configurando configuraciones regionales soportadas {#configuring-supported-locales}

La configuración `supported-locales` le indica a webforJ qué configuraciones regionales soporta tu aplicación. Esta lista es utilizada por la detección automática para hacer coincidir la configuración regional del navegador del usuario con las traducciones disponibles. La primera configuración regional en la lista se utiliza como la predeterminada de respaldo cuando no se encuentra una mejor coincidencia. La clave de propiedad es `webforj.i18n.supported-locales` y acepta una lista de etiquetas de idioma [BCP 47](https://en.wikipedia.org/wiki/IETF_language_tag), por ejemplo `en, de`.

:::info Más info
Consulta la sección [Configuración](/docs/configuration/properties) para aprender cómo establecer propiedades para diferentes entornos.
:::

## El método `t()` {#the-t-method}

Los componentes que implementan la interfaz de preocupación `HasTranslation` obtienen acceso al método `t()` para traducir texto. El método toma una clave de traducción y devuelve la cadena localizada para la configuración regional actual de la aplicación:

```java
public class MainLayout extends Composite<AppLayout> implements HasTranslation {

  public MainLayout() {
    // Traducción simple
    String title = t("app.title");

    // Traducción con parámetros de MessageFormat
    String greeting = t("greeting", userName, messageCount);

    // Traducción para una configuración regional específica
    String germanTitle = t(Locale.GERMAN, "app.title");
  }
}
```

También puedes usar `App.getTranslation()` directamente en cualquier lugar sin implementar la interfaz:

```java
String title = App.getTranslation("app.title");
```

:::info Respaldo suave
Si no se encuentra una clave de traducción, `t()` devuelve la clave misma en lugar de lanzar una excepción. Esto significa que tu aplicación no se romperá si falta una traducción. La clave se muestra tal cual, y se registra una advertencia para que puedas rastrear las traducciones faltantes durante el desarrollo.
:::

## Implementando componentes traducidos {#implementing-translated-components}

Un componente traducido combina típicamente `HasTranslation` con [`LocaleObserver`](/docs/advanced/locale-management#the-localeobserver-interface). Usa `t()` al crear elementos de la interfaz de usuario para establecer el texto traducido inicial. Para admitir el cambio de idioma en tiempo de ejecución, implementa `LocaleObserver` y actualiza el mismo texto en `onLocaleChange()`.

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

:::tip Vinculación de datos
El sistema de vinculación de datos admite mensajes de validación y transformación traducidos utilizando `Supplier<String>` con `t()`. Consulta [mensajes de validación dinámica](/docs/data-binding/validation/validators#dynamic-validation-messages), [mensajes de transformador dinámico](/docs/data-binding/transformation#dynamic-transformer-error-messages), y [validación consciente de la configuración regional en Jakarta](/docs/data-binding/validation/jakarta-validation#locale-aware-validation-messages).
:::

## Resolvedores de traducción personalizados {#custom-translation-resolvers}

El resolvedor predeterminado carga traducciones desde archivos de propiedades `ResourceBundle` de Java. Para cargar traducciones desde una fuente diferente, como una base de datos o un servicio remoto, implementa `TranslationResolver`:

```java title="DatabaseTranslationResolver.java"
public class DatabaseTranslationResolver implements TranslationResolver {
  private final TranslationRepository repository;
  private final List<Locale> supportedLocales;

  public DatabaseTranslationResolver(TranslationRepository repository,
      List<Locale> supportedLocales) {
    this.repository = repository;
    this.supportedLocales = List.copyOf(supportedLocales);
  }

  @Override
  public String resolve(String key, Locale locale, Object... args) {
    String value = repository
        .findByKeyAndLocale(key, locale.getLanguage())
        .map(Translation::getValue)
        .orElse(key);

    if (args != null && args.length > 0) {
      value = new MessageFormat(value, locale).format(args);
    }

    return value;
  }

  @Override
  public List<Locale> getSupportedLocales() {
    return supportedLocales;
  }
}
```

### Registrando un resolvedor personalizado {#registering-a-custom-resolver}

En una aplicación webforJ simple, establece el resolvedor antes de que la aplicación comience, por ejemplo, utilizando un [escuchador del ciclo de vida de la aplicación](/docs/advanced/lifecycle-listeners):

```java
App.setTranslationResolver(new DatabaseTranslationResolver(repository, supportedLocales));
```

En una aplicación Spring Boot, expone el resolvedor como un bean:

```java title="MessageSourceConfig.java"
@Configuration
public class MessageSourceConfig {

  @Bean
  TranslationResolver translationResolver(TranslationRepository repository,
      SpringConfigurationProperties properties) {
    List<Locale> supportedLocales = properties.getI18n().getSupportedLocales().stream()
        .map(Locale::forLanguageTag)
        .toList();
    return new DatabaseTranslationResolver(repository, supportedLocales);
  }
}
```

:::info Resolvedor predeterminado en Spring Boot
Cuando no se define un bean `TranslationResolver` personalizado, la auto-configuración de Spring proporciona un `BundleTranslationResolver` predeterminado configurado con las configuraciones regionales soportadas de `application.properties`.
:::
