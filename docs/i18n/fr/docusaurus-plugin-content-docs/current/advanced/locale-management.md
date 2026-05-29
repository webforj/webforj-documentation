---
sidebar_position: 11
title: Locale Management
sidebar_class_name: new-content
_i18n_hash: d3dcb4b1ded50923232cb33225364239
---
# Gestion des locales <DocChip chip='since' label='25.10' />

webforJ fournit un support intégré pour la gestion de la locale de l'application. La locale détermine quelle langue et quel formatage régional sont utilisés dans toute l'application. Les composants peuvent réagir aux changements de locale via l'interface `LocaleObserver`, permettant à l'UI de se mettre à jour immédiatement lorsque l'utilisateur change de langue.

## Configuration de la locale par défaut {#setting-the-default-locale}

La locale de l'application peut être configurée à l'aide de la propriété `webforj.locale`. Cela définit la locale que l'application utilise dès son démarrage, affectant tout le formatage et le texte sensibles à la locale. Lorsque `webforj.locale` n'est pas configurée, l'application utilise la locale par défaut de la JVM du serveur. Vous pouvez lire la locale actuelle à tout moment avec `App.getLocale()`.

Consultez la section [Configuration](/docs/configuration/properties) pour apprendre comment définir des propriétés pour différents environnements.

## Changement de la locale {#changing-the-locale}

Pour changer la locale à l'exécution, appelez `App.setLocale()`. Cela met à jour la locale pour l'ensemble de l'application et notifie tous les composants qui implémentent `LocaleObserver`, permettant à l'UI de se mettre à jour sans recharger la page.

```java
App.setLocale(Locale.GERMAN);
App.setLocale(Locale.forLanguageTag("fr"));
```

## Détection de la locale du navigateur <DocChip chip='since' label='25.12' /> {#browser-locale-detection}

Lorsque la détection automatique est activée, webforJ lit les langues préférées du navigateur au démarrage et définit la locale de l'application sur le meilleur match parmi les locales supportées configurées. Si aucun match n'est trouvé, la première locale supportée est utilisée comme locale par défaut.

Activez la détection automatique en définissant `webforj.i18n.auto-detect` sur `true` et en configurant `webforj.i18n.supported-locales` avec les locales que votre application supporte. Consultez la section [Configuration](/docs/configuration/properties) pour apprendre comment définir des propriétés pour différents environnements.

:::info Nécessite des locales supportées
La détection automatique nécessite que les `supported-locales` soient configurées. Si la liste est vide, la détection automatique n'a aucun effet et l'application utilise la locale par défaut de `webforj.locale`.
:::

## L'interface `LocaleObserver` {#the-localeobserver-interface}

Les composants qui ont besoin de mettre à jour leur contenu lorsque la locale change doivent implémenter l'interface `LocaleObserver`. webforJ enregistre et désinscrit automatiquement les observateurs au fur et à mesure que les composants sont créés et détruits.

```java title="LocaleObserver.java"
@FunctionalInterface
public interface LocaleObserver {
  void onLocaleChange(LocaleEvent event);
}
```

Lorsque la locale change, `onLocaleChange` est appelé avec la nouvelle locale. À l'intérieur de cette méthode, mettez à jour tout texte ou formatage sensible à la locale :

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

:::tip Support de traduction intégré
À partir de la version 25.12, webforJ fournit un [système de traduction](/docs/advanced/i18n-localization) intégré qui prend en charge les bundles de ressources, les résolveurs personnalisés, la détection automatique de la locale du navigateur et la liaison de données sensible à la locale.
:::

### `LocaleEvent` {#localeevent}

Le `LocaleEvent` passé à `onLocaleChange()` fournit la nouvelle locale et le composant qui a reçu l'événement :

| Méthode | Retourne | Description |
|---------|----------|-------------|
| `getLocale()` | `Locale` | La nouvelle locale qui a été définie |
| `getSource()` | `Object` | Le composant qui a reçu l'événement |

## Mises à jour manuelles de la locale {#manual-locale-updates}

Tout ne réagit pas aux changements de locale automatiquement. Certains composants, comme les [Champs Masqués](/docs/components/fields/masked/overview), lisent `App.getLocale()` une seule fois lors de leur création pour configurer le formatage sensible à la locale, mais n'implémentent pas `LocaleObserver`. Lorsque la locale change à l'exécution, ceux-ci doivent être mis à jour explicitement dans votre gestionnaire `onLocaleChange()` :

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

:::tip Liaison des données
`BindingContext` prend en charge des messages de validation et de transformation sensibles à la locale. Consultez les [messages de validation dynamiques](/docs/data-binding/validation/validators#dynamic-validation-messages) et la [validation Jakarta sensible à la locale](/docs/data-binding/validation/jakarta-validation#locale-aware-validation-messages).
:::
