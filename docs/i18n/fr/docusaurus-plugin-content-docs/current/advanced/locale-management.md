---
sidebar_position: 11
title: Locale Management
sidebar_class_name: new-content
_i18n_hash: 066d555fcf006756c6ae0e542d409d77
---
# Gestion des paramètres régionaux <DocChip chip='since' label='25.10' />

webforJ offre un support intégré pour la gestion des paramètres régionaux de l'application. Le paramètre régional détermine quelle langue et quel formatage régional sont utilisés dans toute l'application. Les composants peuvent réagir aux changements de paramètres régionaux grâce à l'interface `LocaleObserver`, permettant à l'interface utilisateur de se mettre à jour immédiatement lorsque l'utilisateur change de langue.

## Configuration du paramètre régional par défaut {#setting-the-default-locale}

Le paramètre régional de l'application peut être configuré en utilisant la propriété `webforj.locale`. Cela définit le paramètre régional que l'application utilise au démarrage, affectant tout le formatage et le texte sensibles aux paramètres régionaux. Lorsque `webforj.locale` n'est pas configuré, l'application revient au paramètre régional par défaut de la JVM du serveur. Vous pouvez lire le paramètre régional actuel à tout moment avec `App.getLocale()`.

Consultez la section [Configuration](/docs/configuration/properties) pour apprendre comment définir des propriétés pour différents environnements.

## Changement de paramètres régionaux {#changing-the-locale}

Pour changer le paramètre régional à l'exécution, appelez `App.setLocale()`. Cela met à jour le paramètre régional pour toute l'application et notifie tous les composants qui implémentent `LocaleObserver`, permettant à l'interface utilisateur de se mettre à jour sans recharger la page.

```java
App.setLocale(Locale.GERMAN);
App.setLocale(Locale.forLanguageTag("fr"));
```

## Détection des paramètres régionaux du navigateur <DocChip chip='since' label='25.12' /> {#browser-locale-detection}

Lorsque la détection automatique est activée, webforJ lit les langues préférées du navigateur au démarrage et définit le paramètre régional de l'application sur le meilleur match parmi les paramètres régionaux pris en charge configurés. Si aucune correspondance n'est trouvée, le premier paramètre régional pris en charge est utilisé par défaut.

Activez la détection automatique en définissant `webforj.i18n.auto-detect` sur `true` et en configurant `webforj.i18n.supported-locales` avec les paramètres régionaux que votre application prend en charge. Consultez la section [Configuration](/docs/configuration/properties) pour apprendre comment définir des propriétés pour différents environnements.

:::info Nécessite des paramètres régionaux pris en charge
La détection automatique nécessite que `supported-locales` soit configuré. Si la liste est vide, la détection automatique n'a aucun effet et l'application utilise le paramètre régional par défaut de `webforj.locale`.
:::

## L'interface `LocaleObserver` {#the-localeobserver-interface}

Les composants qui doivent mettre à jour leur contenu lorsque le paramètre régional change doivent implémenter l'interface `LocaleObserver`. webforJ enregistre et désenregistre automatiquement les observateurs à mesure que les composants sont créés et détruits.

```java title="LocaleObserver.java"
@FunctionalInterface
public interface LocaleObserver {
    void onLocaleChange(LocaleEvent event);
}
```

Lorsque le paramètre régional change, `onLocaleChange` est appelé avec le nouveau paramètre régional. À l'intérieur de cette méthode, mettez à jour tout texte ou formatage sensible aux paramètres régionaux :

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

:::tip Support de traduction intégré
À partir de la version 25.12, webforJ fournit un [système de traduction](https://docs/advanced/i18n-localization) intégré qui prend en charge les ensembles de ressources, les résolveurs personnalisés, la détection automatique des paramètres régionaux du navigateur et la liaison de données sensible aux paramètres régionaux.
:::

### `LocaleEvent` {#localeevent}

L'`LocaleEvent` passé à `onLocaleChange()` fournit le nouveau paramètre régional et le composant qui a reçu l'événement :

| Méthode | Renvoie | Description |
|---------|---------|-------------|
| `getLocale()` | `Locale` | Le nouveau paramètre régional qui a été défini |
| `getSource()` | `Object` | Le composant qui a reçu l'événement |

## Mises à jour manuelles des paramètres régionaux {#manual-locale-updates}

Tout ne réagit pas automatiquement aux changements de paramètres régionaux. Certains composants, comme [Champs masqués](/docs/components/fields/masked/overview), lisent `App.getLocale()` une fois lors de la création pour configurer le formatage sensible aux paramètres régionaux, mais n'implémentent pas `LocaleObserver`. Lorsque le paramètre régional change à l'exécution, ceux-ci doivent être mis à jour explicitement dans votre gestionnaire `onLocaleChange()` :

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

:::tip Liaison de données
`BindingContext` prend en charge la validation sensible aux paramètres régionaux et les messages de transformation. Consultez les [messages de validation dynamiques](/docs/data-binding/validation/validators#dynamic-validation-messages) et la [validation Jakarta sensible aux paramètres régionaux](/docs/data-binding/validation/jakarta-validation#locale-aware-validation-messages).
:::
