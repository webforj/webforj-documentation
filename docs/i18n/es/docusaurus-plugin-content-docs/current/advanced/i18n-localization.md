---
sidebar_position: 11
title: Localization
sidebar_class_name: new-content
_i18n_hash: 91f5af285113e5e76d50a201a2fbf88f
---
# Localización <DocChip chip='since' label='25.10' />

Los componentes que implementan la interfaz `LocaleObserver` reciben notificaciones automáticas cuando el locale cambia. Esto permite que los elementos de la interfaz de usuario actualicen su texto, formato y otros contenidos específicos del locale sin necesidad de coordinación manual.

## La interfaz `LocaleObserver` {#the-localeobserver-interface}

```java title="LocaleObserver.java"
@FunctionalInterface
public interface LocaleObserver extends Serializable {
    void onLocaleChange(LocaleEvent event);
}
```

Cuando un componente implementa esta interfaz, webforJ registra automáticamente:
- El componente al ser creado para recibir eventos de cambio de locale
- El componente al ser destruido
- Llama a `onLocaleChange()` cada vez que el locale cambia

Este registro ocurre a través del ciclo de vida del componente.

## Manejo de traducciones {#handling-translations}

Cuando se llama a `onLocaleChange()`, los componentes reciben el nuevo locale. Cómo cargan y aplican las traducciones depende del desarrollador. Los enfoques comunes incluyen:

- `ResourceBundle` de Java con archivos de propiedades
- Consultas a bases de datos para traducciones
- Proveedores de traducción personalizados
- Mapas codificados para casos simples

Este ejemplo utiliza `ResourceBundle`, que almacena traducciones en archivos de propiedades:

```
messages.properties        # Predeterminado
messages_en.properties     # Inglés
messages_de.properties     # Alemán
```

Los archivos de propiedades contienen pares clave-valor:

```properties title="messages_en.properties"
app.title=Mailbox
menu.inbox=Inbox
```

```properties title="messages_de.properties"
app.title=Postfach
menu.inbox=Posteingang
```
## Cambiando el locale {#changing-the-locale}

Utiliza `App.setLocale()` para cambiar el locale de la aplicación. Esto desencadena notificaciones a todos los observadores registrados:

```java
App.setLocale(Locale.GERMAN);
App.setLocale(Locale.forLanguageTag("fr"));
```

Una implementación típica podría utilizar un componente de lista desplegable o de selección:

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

Cuando el usuario selecciona un idioma, se ejecuta `App.setLocale()`, y todos los componentes que implementan `LocaleObserver` reciben la actualización.

## Implementando observadores {#implementing-observers}

Cuando un componente implementa `LocaleObserver`, necesita manejar dos escenarios: la representación inicial con el locale actual y la actualización cuando el locale cambia. El siguiente ejemplo demuestra este patrón con un componente que muestra texto y enlaces localizados.

El componente almacena referencias a elementos que necesitan actualizaciones de traducción. Al ser construido, carga las traducciones del locale actual. Cuando el locale cambia, se dispara `onLocaleChange()`, lo que permite al componente recargar las traducciones y actualizar su texto mostrado.

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

El componente almacena referencias a elementos que muestran contenido traducido (`titleElement` y `anchor`). Las traducciones se cargan en el constructor utilizando el locale actual. Cuando el locale cambia, `onLocaleChange()` actualiza solo el texto que necesita traducción.

## Gestión del ciclo de vida {#lifecycle-management}

El marco maneja la registración de observadores automáticamente a través de los ganchos del ciclo de vida del componente:

- **Al crear**: Los componentes que implementan `LocaleObserver` se registran en `LocaleObserverRegistry`
- **Al destruir**: Los componentes se desregistran para evitar fugas de memoria

Cada instancia de aplicación mantiene su propio registro de observadores. Esta gestión automática significa:

- No hay llamadas manuales de registro/desregistro
- Sin fugas de memoria por componentes destruidos
- Notificaciones concurrentes seguras para hilos

:::info Registro por aplicación
Cada instancia de aplicación mantiene su propio registro de observadores. Los observadores registrados en una aplicación no reciben notificaciones de otras aplicaciones que se ejecutan en la misma JVM.
:::

## `LocaleEvent` {#localeevent}

El `LocaleEvent` que se pasa a `onLocaleChange()` proporciona:

| Método | Devuelve | Descripción |
|--------|---------|-------------|
| `getLocale()` | `Locale` | El nuevo locale que se estableció |
| `getSource()` | `Object` | El componente que recibió el evento |

```java
@Override
public void onLocaleChange(LocaleEvent event) {
  Locale newLocale = event.getLocale();
  Object source = event.getSource();

  // Actualizar componente usando el nuevo locale
  ResourceBundle bundle = ResourceBundle.getBundle("messages", newLocale);
  updateUI(bundle);
}
```
