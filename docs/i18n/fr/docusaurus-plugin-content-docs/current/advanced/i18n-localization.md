---
sidebar_position: 11
title: Localisation
sidebar_class_name: new-content
_i18n_hash: 91f5af285113e5e76d50a201a2fbf88f
---
# Localization <DocChip chip='since' label='25.10' />

Les composants implémentant l'interface `LocaleObserver` reçoivent des notifications automatiques lorsque la locale change. Cela permet aux éléments de l'interface utilisateur de mettre à jour leur texte, leur formatage et d'autres contenus spécifiques à la locale sans coordination manuelle.

## L'interface `LocaleObserver` {#the-localeobserver-interface}

```java title="LocaleObserver.java"
@FunctionalInterface
public interface LocaleObserver extends Serializable {
    void onLocaleChange(LocaleEvent event);
}
```

Lorsqu'un composant implémente cette interface, webforJ enregistre automatiquement :
- Le composant lors de sa création pour recevoir les événements de changement de locale
- Désenregistre le composant lors de sa destruction
- Appelle `onLocaleChange()` chaque fois que la locale est changée

Cette inscription se fait à travers le cycle de vie du composant.

## Gestion des traductions {#handling-translations}

Lorsque `onLocaleChange()` est appelé, les composants reçoivent la nouvelle locale. Comment ils chargent et appliquent les traductions dépend du développeur. Les approches courantes incluent :

- Java `ResourceBundle` avec des fichiers de propriétés
- Requêtes en base de données pour les traductions
- Fournisseurs de traductions personnalisés
- Cartes codées en dur pour des cas simples

Cet exemple utilise `ResourceBundle`, qui stocke les traductions dans des fichiers de propriétés :

```
messages.properties        # Sauvegarde/par défaut
messages_en.properties     # Anglais
messages_de.properties     # Allemand
```

Les fichiers de propriétés contiennent des paires clé-valeur :

```properties title="messages_en.properties"
app.title=Mailbox
menu.inbox=Inbox
```

```properties title="messages_de.properties"
app.title=Postfach
menu.inbox=Posteingang
```
## Changer la locale {#changing-the-locale}

Utilisez `App.setLocale()` pour changer la locale de l'application. Cela déclenche des notifications à tous les observateurs enregistrés :

```java
App.setLocale(Locale.GERMAN);
App.setLocale(Locale.forLanguageTag("fr"));
```

Une implémentation typique pourrait utiliser un composant de sélection ou de choix :

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

Lorsque l'utilisateur sélectionne une langue, `App.setLocale()` est appelé, et tous les composants implémentant `LocaleObserver` reçoivent la mise à jour.

## Implémentation des observateurs {#implementing-observers}

Lorsqu'un composant implémente `LocaleObserver`, il doit gérer deux scénarios : le rendu initial avec la locale actuelle et la mise à jour lorsque la locale change. L'exemple suivant illustre ce modèle avec un composant qui affiche du texte localisé et des liens.

Le composant stocke des références aux éléments qui nécessitent des mises à jour de traduction. Lors de sa construction, il charge les traductions de la locale actuelle. Lorsque la locale change, `onLocaleChange()` est appelé, permettant au composant de recharger les traductions et de mettre à jour son texte affiché.

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

Le composant stocke des références aux éléments qui affichent du contenu traduit (`titleElement` et `anchor`). Les traductions sont chargées dans le constructeur en utilisant la locale actuelle. Lorsque la locale change, `onLocaleChange()` met à jour uniquement le texte nécessitant une traduction.

## Gestion du cycle de vie {#lifecycle-management}

Le framework gère automatiquement l'enregistrement des observateurs à travers les hooks du cycle de vie du composant :

- **À la création** : Les composants implémentant `LocaleObserver` sont enregistrés dans `LocaleObserverRegistry`
- **À la destruction** : Les composants sont désenregistrés pour prévenir les fuites de mémoire

Chaque instance d'application maintient son propre registre d'observateurs. Cette gestion automatique signifie :

- Pas d'appels manuels d'enregistrement/désenregistrement
- Pas de fuites de mémoire provenant des composants détruits
- Notifications concurrentes thread-safe

:::info Registre par application
Chaque instance d'application maintient son propre registre d'observateurs. Les observateurs enregistrés dans une application ne reçoivent pas de notifications d'autres applications s'exécutant dans la même JVM.
:::

## `LocaleEvent` {#localeevent}

L'`LocaleEvent` passé à `onLocaleChange()` fournit :

| Méthode | Renvoie | Description |
|---------|---------|-------------|
| `getLocale()` | `Locale` | La nouvelle locale qui a été définie |
| `getSource()` | `Object` | Le composant qui a reçu l'événement |

```java
@Override
public void onLocaleChange(LocaleEvent event) {
  Locale newLocale = event.getLocale();
  Object source = event.getSource();

  // Mettre à jour le composant en utilisant la nouvelle locale
  ResourceBundle bundle = ResourceBundle.getBundle("messages", newLocale);
  updateUI(bundle);
}
```
