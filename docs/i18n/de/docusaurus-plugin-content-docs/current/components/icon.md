---
title: Icon
sidebar_position: 55
description: >-
  Render scalable SVG icons with the Icon component from Tabler, Feather, Font
  Awesome, or custom pools loaded on demand from a CDN.
_i18n_hash: c526ee2878756d5dd13fa2972dfef56e
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

Die `Icon`-Komponente zeigt Symbole an, die auf jede Größe skaliert werden können, ohne an Qualität zu verlieren. Sie können aus drei integrierten Symbolpools wählen oder eigene erstellen. Symbole dienen als visuelle Hinweise für die Navigation und Aktionen und reduzieren die Notwendigkeit von Textbeschriftungen in Ihrer Benutzeroberfläche.

Jedes `Icon` wird als skalierbares Vektorgrafik (SVG)-Bild gerendert, das bei Bedarf von einem Content Delivery Network (CDN) geladen wird, um die Latenz niedrig zu halten. Um eines zu erstellen, wählen Sie einen Symbolpool und den Namen eines Symbols. Einige Symbole bieten auch die Wahl zwischen einer umrissenen oder einer ausgefüllten Version über [Variationen](#variations).

<!-- INTRO_END -->

<ComponentDemo
path='/webforj/iconbasics'
files={['src/main/java/com/webforj/samples/views/icon/IconBasicsView.java']}
height='100px'
/>

:::tip Wussten Sie schon?
Einige Komponenten, wie `PasswordField` und `TimeField`, haben eingebaute Symbole, um den Endbenutzern die Bedeutung zu vermitteln.
:::

## Pools {#pools}

Ein Symbolpool ist eine Sammlung von häufig verwendeten Symbolen, die einen einfachen Zugriff und eine Wiederverwendung ermöglichen. Durch die Verwendung von Symbolen aus einem Symbolpool können Sie sicherstellen, dass die Symbole in Ihrer App wiedererkennbar sind und einen konsistenten Stil aufweisen. Die Verwendung von webforJ ermöglicht es Ihnen, aus drei Pools zu wählen oder einen benutzerdefinierten Pool zu implementieren. Jeder Pool hat eine umfangreiche Sammlung von Open-Source-Symbolen, die kostenlos verwendet werden können. Die Verwendung von webforJ gibt Ihnen die Flexibilität, aus drei Pools zu wählen und diese als einzigartige Klassen zu verwenden, ohne den Aufwand, eines der Symbole direkt herunterzuladen.

| Symbolpool                                         | webforJ-Klasse |
| --------                                          | ------- |
| [Tabler](https://tabler-icons.io/)                | `TablerIcon` und `DwcIcon`.<br/>`DwcIcon` ist eine Teilmenge der Tabler-Symbole.|
| [Feather](https://feathericons.com/)              | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)    | `FontAwesomeIcon`   |

:::tip

Wenn Sie daran interessiert sind, Ihren eigenen Symbolpool zu erstellen, sehen Sie sich [Creating custom pools](#creating-custom-pools) an.

:::

Sobald Sie den Pool oder die Pools ausgewählt haben, die Sie in Ihrer App verwenden möchten, besteht der nächste Schritt darin, den Namen des Symbols anzugeben, das Sie verwenden möchten.

## Namen {#names}

Um ein Symbol in Ihrer App einzufügen, benötigen Sie nur den Symbolpool und den Symbolnamen. Durchsuchen Sie die Website des Symbolpools nach dem Symbol, das Sie verwenden möchten, und verwenden Sie den Symbolnamen als Parameter der Methode `create()`. Darüber hinaus können Sie die Symbole auch durch Enums für die Klassen `FeatherIcon` und `DwcIcon` erstellen, wodurch sie in der Codevervollständigung erscheinen.

```java
// Erstellen eines Symbols aus einem String-Namen
Icon image = TablerIcon.create("image");
// Erstellen eines Symbols aus einem Enum
Icon image = FeatherIcon.IMAGE.create();
```

## Variationen {#variations}

Sie können Symbole noch weiter personalisieren, indem Sie Variationen nutzen. Bestimmte Symbole ermöglichen es Ihnen, zwischen einer umrissenen oder einer ausgefüllten Version zu wählen, sodass Sie ein bestimmtes Symbol basierend auf Ihrer Präferenz hervorheben können. `FontAwesomeIcon` und `Tabler`-Symbole bieten Variationen.

### `FontAwesomeIcon`-Variationen {#fontawesomeicon-variations}

1. `REGULAR`: Die umrissene Variation der Symbole. Dies ist die Standardversion.
2. `SOLID`: Die ausgefüllte Variation der Symbole.
3. `BRAND`: Die Variation für die Verwendung von Brandsymbolen.

### `TablerIcon`-Variationen {#tablericon-variations}

1. `OUTLINE`: Die umrissene Variation der Symbole. Dies ist die Standardversion.
2. `FILLED`: Die ausgefüllte Variation der Symbole.

```java
// Eine ausgefüllte Variation eines Symbols aus Font Awesome
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

Die folgende Demo zeigt, wie man Symbole aus verschiedenen Pools verwendet, Variationen anwendet und nahtlos in Komponenten integriert.

<ComponentDemo
path='/webforj/iconvariations'
files={['src/main/java/com/webforj/samples/views/icon/IconVariationsView.java']}
height='100px'
/>

## Symbole zu Komponenten hinzufügen {#adding-icons-to-components}

Integrieren Sie Symbole in Ihre Komponenten mithilfe von Slots. Slots bieten flexible Optionen, um Komponenten nützlicher zu machen. Es ist vorteilhaft, ein `Icon` zu einer Komponente hinzuzufügen, um die beabsichtigte Bedeutung für die Benutzer weiter zu verdeutlichen. Komponenten, die das Interface `HasPrefixAndSuffix` implementieren, können ein `Icon` oder andere gültige Komponenten enthalten. Die hinzugefügten Komponenten können in den `prefix`- und `suffix`-Slots platziert werden und sowohl das Gesamtdesign als auch die Benutzererfahrung verbessern.

Mit den `prefix`- und `suffix`-Slots können Sie bestimmen, ob Sie das Symbol vor oder nach dem Text mit den Methoden `setPrefixComponent()` und `setSuffixComponent()` haben möchten.

Die Entscheidung, ob ein Symbol vor oder nach dem Text einer Komponente platziert werden soll, hängt weitgehend vom Zweck und dem Designkontext ab.

### Symbolanordnung: vor VS nach {#icon-placement-before-vs-after}

Symbole, die vor dem Komponententext positioniert sind, helfen den Benutzern, schnell die Hauptaktion oder den Zweck der Komponente zu verstehen, insbesondere bei allgemein anerkannten Symbolen wie dem Speichersymbol. Symbole vor dem Text einer Komponente bieten eine logische Verarbeitungsreihenfolge und führen die Benutzer auf natürliche Weise durch die beabsichtigte Aktion, was vorteilhaft für Schaltflächen ist, deren Hauptfunktion eine sofortige Aktion ist.

Andererseits ist die Platzierung von Symbolen nach dem Komponententext effektiv für Aktionen, die zusätzlichen Kontext oder Optionen bieten, was die Klarheit und Hinweise zur Navigation verbessert. Symbole nach dem Text einer Komponente eignen sich ideal für Komponenten, die entweder ergänzende Informationen bieten oder die Benutzer in einem Richtungsfluss führen.

Letztendlich ist Konsistenz der Schlüssel. Sobald Sie einen Stil gewählt haben, halten Sie ihn über Ihre gesamte Website hinweg für ein kohärentes und benutzerfreundliches Design aufrecht.

<ComponentDemo
path='/webforj/iconprefixsuffix'
files={['src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java']}
height='100px'
/>

## Erstellen benutzerdefinierter Pools {#creating-custom-pools}

Über die Nutzung bestehender Symbolkollektionen hinaus haben Sie die Möglichkeit, einen benutzerdefinierten Pool zu erstellen, der für benutzerdefinierte Logos oder Avatare verwendet werden kann. Ein benutzerdefinierter Pool von Symbolen kann in einem zentralen Verzeichnis oder im Ressourcenordner (Kontext) gespeichert werden, um den Prozess der Symbolverwaltung zu vereinfachen. Ein benutzerdefinierter Pool sorgt dafür, dass die App-Erstellung konsistenter wird und die Wartung über verschiedene Komponenten und Module hinweg reduziert wird.

Benutzerdefinierte Pools können aus einem Ordner mit SVG-Bildern erstellt werden, und durch die Verwendung der `IconPoolBuilder`-Klasse. Von dort aus können Sie den Namen Ihres benutzerdefinierten Pools wählen und diesen mit den SVG-Dateinamen verwenden, um benutzerdefinierte Symbolkomponenten zu erstellen.

```java
// Erstellen eines benutzerdefinierten Pools mit dem Namen "app-pool", der Bilder für ein Logo und einen Avatar enthält.
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
Stellen Sie sicher, dass Sie Symbole mit gleicher Breite und Höhe entwerfen, da `Icon`-Komponenten dazu bestimmt sind, einen quadratischen Raum einzunehmen.
:::

### Benutzerdefinierte Poolfabrik {#custom-pool-factory}

Sie können auch eine Fabrikklasse für einen benutzerdefinierten Pool in webforJ erstellen, ähnlich wie `FeatherIcon`. Dies ermöglicht es Ihnen, Symbolressourcen innerhalb eines bestimmten Pools zu erstellen und zu verwalten und die Codevervollständigung zuzulassen. Jedes Symbol kann über die Methode `create()` instanziiert werden, die ein `Icon` zurückgibt. Die Fabrikklasse sollte pool-spezifische Metadaten bereitstellen, wie den Poolnamen und die Kennung des Symbols, die im Format des Bilddateinamens vorliegen. Dieses Design ermöglicht einen einfachen, standardisierten Zugriff auf Symbolressourcen aus dem benutzerdefinierten Pool mithilfe von Enum-Konstanten, die Skalierbarkeit und Wartbarkeit in der Symbolverwaltung unterstützen.

```java
/// Erstellung einer benutzerdefinierten Poolfabrik für app-pool
public enum AppPoolIcon implements IconFactory {
  LOGO, AVATAR_DEFAULT;

  public Icon create() {
    return new Icon(String.valueOf(this), this.getPool());
  }

  /**
   * @return den Poolnamen für die Symbole
   */
  @Override
  public String getPool() {
    return "app-pool";
  }

  /**
   * @return den Symbolnamen
   */
  @Override
  public String toString() {
    return this.name().toLowerCase(Locale.ENGLISH).replace('_', '-');
  }
}
```

Im Folgenden zeigt der Codeausschnitt die beiden verschiedenen Möglichkeiten zur Verwendung eines benutzerdefinierten Pools.

```java
IconPoolBuilder.fromDirectory("app-pool", "context://icons");

// Erstellen eines Symbols mit den Namen des benutzerdefinierten Pools und der Bilddatei
Icon customLogo = new Icon("logo", "app-pool");

// Erstellen eines Symbols mit der benutzerdefinierten Poolfabrik aus dem vorherigen Snippet
Icon customLogo = AppPoolIcon.LOGO.create();
```

## Symbols-Schaltflächen {#icon-buttons}
Eine `Icon`-Komponente ist nicht auswählbar, aber für Aktionen, die am besten mit nur einem Symbol dargestellt werden, wie Benachrichtigungen oder Warnungen, können Sie die `IconButton` verwenden.

 ```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("Sie haben eine neue Nachricht!", "Ding Dong!")
  });
```

## Best Practices

- **Zugänglichkeit:** Verwenden Sie einen Tooltip oder ein Label für Symbole, um Ihre App für sehbehinderte Benutzer, die auf Screenreader angewiesen sind, zugänglich zu machen.
- **Vermeiden Sie Mehrdeutigkeit:** Vermeiden Sie die Verwendung von Symbolen, wenn die Bedeutung nicht klar oder weit verbreitet ist. Wenn Benutzer raten müssen, was das Symbol darstellt, hat es keinen Sinn.
- **Verwenden Sie Symbole sparsam:** Zu viele Symbole können die Benutzer überwältigen, verwenden Sie Symbole nur, wenn sie Klarheit hinzufügen oder die Komplexität verringern.

## Styling
Ein Icon erbt das Thema seiner direkt übergeordneten Komponente, aber Sie können dies überschreiben, indem Sie ein Thema direkt auf ein `Icon` anwenden.

### Themen
Symbolkomponenten verfügen über sieben separate integrierte Themen für ein schnelles Styling ohne CSS. Diese Themen sind vordefinierte Stile, die auf Symbole angewendet werden können, um ihr Aussehen und ihre visuelle Präsentation zu ändern. Sie bieten eine schnelle und konsistente Möglichkeit, das Aussehen von Symbolen in einer App anzupassen.

Obwohl es viele Anwendungsfälle für jedes der verschiedenen Themen gibt, sind einige Beispiele:

- `DANGER`: Am besten geeignet für Handlungen mit schwerwiegenden Folgen, wie das Löschen von ausgefüllten Informationen oder das dauerhafte Löschen eines Kontos/Daten.
- `DEFAULT`: Geeignet für Handlungen in einer App, die keine besondere Aufmerksamkeit erfordern und allgemein sind, wie das Umschalten einer Einstellung.
- `PRIMARY`: Geeignet als Haupt-"Call-to-Action" auf einer Seite, wie Anmeldung, Änderungen speichern oder zu einer anderen Seite fortfahren.
- `SUCCESS`: Hervorragend zur Visualisierung des erfolgreichen Abschlusses eines Elements in einer App, wie das Einreichen eines Formulars oder den Abschluss eines Anmeldevorgangs. Das Erfolgsthema kann programmgesteuert angewendet werden, sobald eine erfolgreiche Aktion abgeschlossen ist.
- `WARNING`: Nützlich, um anzuzeigen, dass ein Benutzer dabei ist, eine potenziell riskante Aktion auszuführen, wie das Verlassen einer Seite mit nicht gespeicherten Änderungen. Diese Aktionen sind oft weniger nachteilig als die, die das Danger-Thema verwenden würden.
- `GRAY`: Gut für subtile Aktionen, wie geringfügige Einstellungen oder Aktionen, die eher ergänzend zu einer Seite sind und nicht Teil der Hauptfunktionalität sind.
- `INFO`: Gut, um zusätzliche klärende Informationen an einen Benutzer zu geben.

<TableBuilder name={['Icon', 'IconButton']} />
