---
sidebar_position: 12
title: Translation
sidebar_class_name: new-content
_i18n_hash: 57626c2969592f2378a55eff0dd01d48
---
# Traduction <DocChip chip='since' label='25.12' />

webforJ inclut un système de traduction intégré pour rechercher des chaînes localisées par clé. Le système se compose d'un résolveur de traduction qui associe les clés à des textes localisés, d'une interface de préoccupation `HasTranslation` qui fournit une méthode pratique `t()`, `App.getTranslation()` pour un accès direct partout, la détection automatique de la langue à partir du navigateur, et un support pour des sources de traduction personnalisées telles que des bases de données.

## Résolveur de traduction {#translation-resolver}

Le résolveur de traduction est le système qui recherche des chaînes localisées pour une clé et une locale données. webforJ fournit un résolveur par défaut, `BundleTranslationResolver`, qui charge les traductions à partir de fichiers de propriétés Java `ResourceBundle` sur le classpath. Cela fonctionne hors de la boîte sans dépendances supplémentaires.

### Fichiers de bundles de ressources

Placez vos fichiers de traduction dans le répertoire `src/main/resources`. Le résolveur par défaut recherche des fichiers nommés `messages` avec des suffixes de locale suivant la convention de nommage standard de Java `ResourceBundle` :

```text
messages.properties        # Traductions par défaut/de repli
messages_en.properties     # Anglais
messages_de.properties     # Allemand
messages_fr_CA.properties  # Français (Canada)
```

Chaque fichier contient des paires clé-valeur. Les clés sont des identifiants que vous utilisez dans le code et les valeurs sont les chaînes traduites. Vous pouvez inclure des espaces réservés [`MessageFormat`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/text/MessageFormat.html) comme `{0}`, `{1}` pour des valeurs dynamiques :

```properties title="messages.properties"
app.title=Boîte aux lettres
menu.inbox=Boîte de réception
menu.outbox=Boîte d'envoi
greeting=Bonjour {0}, vous avez {1} nouveaux messages
```

```properties title="messages_de.properties"
app.title=Postfach
menu.inbox=Posteingang
menu.outbox=Postausgang
greeting=Hallo {0}, Sie haben {1} neue Nachrichten
```

Le résolveur délègue à la chaîne de résolution standard de Java [`ResourceBundle`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/ResourceBundle.html), qui gère automatiquement la correspondance des locales et le repli.

### Configuration des locales supportées {#configuring-supported-locales}

Le paramètre `supported-locales` indique à webforJ quelles locales votre application prend en charge. Cette liste est utilisée par la détection automatique pour faire correspondre la locale du navigateur de l'utilisateur avec les traductions disponibles. La première locale de la liste est utilisée comme fallback par défaut lorsqu'aucune meilleure correspondance n'est trouvée. La clé de propriété est `webforj.i18n.supported-locales` et accepte une liste de balises de langue [BCP 47](https://en.wikipedia.org/wiki/IETF_language_tag), par exemple `en, de`.

:::info Plus d'infos
Voir la section [Configuration](/docs/configuration/properties) pour apprendre à définir des propriétés pour différents environnements.
:::

## La méthode `t()` {#the-t-method}

Les composants qui implémentent l'interface de préoccupation `HasTranslation` ont accès à la méthode `t()` pour traduire du texte. La méthode prend une clé de traduction et renvoie la chaîne localisée pour la locale actuelle de l'application :

```java
public class MainLayout extends Composite<AppLayout> implements HasTranslation {

  public MainLayout() {
    // Traduction simple
    String title = t("app.title");

    // Traduction avec des paramètres MessageFormat
    String greeting = t("greeting", userName, messageCount);

    // Traduction pour une locale spécifique
    String germanTitle = t(Locale.GERMAN, "app.title");
  }
}
```

Vous pouvez également utiliser `App.getTranslation()` directement n'importe où sans implémenter l'interface :

```java
String title = App.getTranslation("app.title");
```

:::info Repli gracieux
Si une clé de traduction n'est pas trouvée, `t()` renvoie la clé elle-même plutôt que de lancer une exception. Cela signifie que votre application ne se cassera pas si une traduction est manquante. La clé est affichée telle quelle et un avertissement est enregistré afin que vous puissiez suivre les traductions manquantes pendant le développement.
:::

## Mise en œuvre de composants traduits {#implementing-translated-components}

Un composant traduit combine généralement `HasTranslation` avec [`LocaleObserver`](/docs/advanced/locale-management#the-localeobserver-interface). Utilisez `t()` lors de la création d'éléments d'interface utilisateur pour définir le texte traduit initial. Pour prendre en charge le changement de langue à l'exécution, implémentez `LocaleObserver` et mettez à jour le même texte dans `onLocaleChange()`.

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

:::tip Liaison de données
Le système de liaison de données prend en charge les messages de validation et de transformation traduits utilisant `Supplier<String>` avec `t()`. Voir [messages de validation dynamiques](/docs/data-binding/validation/validators#dynamic-validation-messages), [messages de transformation dynamiques](/docs/data-binding/transformation#dynamic-transformer-error-messages), et [validation Jakarta consciente de la locale](/docs/data-binding/validation/jakarta-validation#locale-aware-validation-messages).
:::

## Résolveurs de traduction personnalisés {#custom-translation-resolvers}

Le résolveur par défaut charge les traductions à partir de fichiers de propriétés Java `ResourceBundle`. Pour charger des traductions à partir d'une autre source, comme une base de données ou un service distant, implémentez `TranslationResolver` :

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

### Enregistrement d'un résolveur personnalisé {#registering-a-custom-resolver}

Dans une application webforJ classique, définissez le résolveur avant que l'application ne démarre, par exemple en utilisant un [écouteur de cycle de vie de l'application](/docs/advanced/lifecycle-listeners) :

```java
App.setTranslationResolver(new DatabaseTranslationResolver(repository, supportedLocales));
```

Dans une application Spring Boot, exposez le résolveur en tant que bean :

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

:::info Résolveur par défaut dans Spring Boot
Lorsque aucun bean `TranslationResolver` personnalisé n'est défini, la configuration automatique de Spring fournit un `BundleTranslationResolver` par défaut configuré avec les locales prises en charge provenant de `application.properties`.
:::
