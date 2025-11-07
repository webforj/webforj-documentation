---
sidebar_position: 11
title: Localization
sidebar_class_name: new-content
_i18n_hash: 91f5af285113e5e76d50a201a2fbf88f
---
# Lokalisatie <DocChip chip='since' label='25.10' />

Componenten die de `LocaleObserver` interface implementeren, ontvangen automatische meldingen wanneer de locale verandert. Dit stelt UI-elementen in staat om hun tekst, opmaak en andere locale-specifieke inhoud bij te werken zonder handmatige coördinatie.

## De `LocaleObserver` interface {#the-localeobserver-interface}

```java title="LocaleObserver.java"
@FunctionalInterface
public interface LocaleObserver extends Serializable {
    void onLocaleChange(LocaleEvent event);
}
```

Wanneer een component deze interface implementeert, registreert webforJ automatisch:
- De component wanneer deze wordt aangemaakt om locale-wijzigingsgebeurtenissen te ontvangen
- Decomponent wanneer deze wordt vernietigd
- Roept `onLocaleChange()` aan telkens wanneer de locale verandert

Deze registratie gebeurt via de componentlevenscyclus.

## Behandelen van vertalingen {#handling-translations}

Wanneer `onLocaleChange()` wordt aangeroepen, ontvangen componenten de nieuwe locale. Hoe ze vertalingen laden en toepassen, hangt af van de ontwikkelaar. Veel voorkomende benaderingen zijn onder meer:

- Java `ResourceBundle` met eigenschapsbestanden
- Databasequery's voor vertalingen
- Aangepaste vertaalproviders
- Hardcoded kaarten voor eenvoudige gevallen

Dit voorbeeld gebruikt `ResourceBundle`, dat vertalingen opslaat in eigenschapsbestanden:

```
messages.properties        # Back-up/default
messages_en.properties     # Engels
messages_de.properties     # Duits
```

Eigenschapsbestanden bevatten sleutel-waarde paren:

```properties title="messages_en.properties"
app.title=Mailbox
menu.inbox=Inbox
```

```properties title="messages_de.properties"
app.title=Postfach
menu.inbox=Posteingang
```
## De locale wijzigen {#changing-the-locale}

Gebruik `App.setLocale()` om de app-locale te wijzigen. Dit triggert meldingen naar alle geregistreerde waarnemers:

```java
App.setLocale(Locale.GERMAN);
App.setLocale(Locale.forLanguageTag("fr"));
```

Een typische implementatie kan een dropdown- of keuzecomponent gebruiken:

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

Wanneer de gebruiker een taal selecteert, wordt `App.setLocale()` aangeroepen, en alle componenten die `LocaleObserver` implementeren ontvangen de update.

## Waarnemers implementeren {#implementing-observers}

Wanneer een component `LocaleObserver` implementeert, moet deze twee scenario's afhandelen: initiële weergave met de huidige locale en bijwerken wanneer de locale verandert. Het volgende voorbeeld demonstreert dit patroon met een component die gelocaliseerde tekst en links weergeeft.

De component slaat referenties op naar elementen die vertaalupdates nodig hebben. Wanneer deze wordt geconstrueerd, laadt deze de vertalingen van de huidige locale. Wanneer de locale verandert, wordt `onLocaleChange()` aangeroepen, waardoor de component vertalingen opnieuw laadt en de weergegeven tekst bijwerkt.

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

De component slaat referenties op naar elementen die vertaalde inhoud weergeven (`titleElement` en `anchor`). Vertalingen worden in de constructor geladen met de huidige locale. Wanneer de locale verandert, update `onLocaleChange()` alleen de tekst die vertaling nodig heeft.

## Levenscyclusbeheer {#lifecycle-management}

Het framework beheert de registratie van waarnemers automatisch via componentlevenscyclushooks:

- **Bij aanmaken**: Componenten die `LocaleObserver` implementeren, worden geregistreerd in `LocaleObserverRegistry`
- **Bij vernietigen**: Componenten worden afgemeld om geheugenlekken te voorkomen

Elke app-instantie houdt zijn eigen waarnemersregister bij. Dit automatische beheer betekent:

- Geen handmatige registratie/afmelding
- Geen geheugenlekken van vernietigde componenten
- Draadveilige gelijktijdige meldingen

:::info Per-app register
Elke app-instantie houdt zijn eigen waarnemersregister bij. Waarnemers die in één app zijn geregistreerd, ontvangen geen meldingen van andere apps die in dezelfde JVM draaien.
:::

## `LocaleEvent` {#localeevent}

De `LocaleEvent` die aan `onLocaleChange()` wordt doorgegeven, biedt:

| Methode | Retourneert | Beschrijving |
|--------|---------|-------------|
| `getLocale()` | `Locale` | De nieuwe locale die is ingesteld |
| `getSource()` | `Object` | De component die de gebeurtenis heeft ontvangen |

```java
@Override
public void onLocaleChange(LocaleEvent event) {
  Locale newLocale = event.getLocale();
  Object source = event.getSource();

  // Update component met nieuwe locale
  ResourceBundle bundle = ResourceBundle.getBundle("messages", newLocale);
  updateUI(bundle);
}
```
