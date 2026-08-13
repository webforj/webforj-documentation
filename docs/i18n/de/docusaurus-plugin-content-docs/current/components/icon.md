---
title: Icon
sidebar_position: 55
description: >-
  Render scalable SVG icons with the Icon component from Tabler, Feather, Font
  Awesome, or custom pools loaded on demand from a CDN.
_i18n_hash: 0e51ecab262c62fb63cd767ba8167084
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

Die `Icon`-Komponente zeigt Icons an, die auf jede Größe skaliert werden können, ohne an Qualität zu verlieren. Sie können aus drei integrierten Iconsammlungen wählen oder benutzerdefinierte erstellen. Icons dienen als visuelle Hinweise für Navigation und Aktionen, was den Bedarf an Textbeschriftungen in Ihrer Benutzeroberfläche verringert.

<!-- INTRO_END -->

## Grundlagen {#basics}

Jedes `Icon` ist als skalierbare Vektorgrafik (SVG) entworfen, was bedeutet, dass es problemlos auf jede Größe skaliert werden kann, ohne Klarheit oder Qualität zu verlieren. Darüber hinaus werden `Icon`-Komponenten bedarfsgerecht über ein Content Delivery Network (CDN) geladen, was hilft, die Latenz zu verringern und die Gesamtleistung zu verbessern.

Wenn Sie ein `Icon` erstellen, müssen Sie einen spezifischen Pool und den Namen des Icons selbst identifizieren. Einige Icons bieten auch die Wahl zwischen einer Umriss- oder einer ausgefüllten Version über [Variationen](#variations).

<ComponentDemo
path='/webforj/iconbasics'
files={['src/main/java/com/webforj/samples/views/icon/IconBasicsView.java']}
height='100px'
/>

:::tip Wussten Sie schon?
Einige Komponenten, wie `PasswordField` und `TimeField`, haben integrierte Icons, um den Endbenutzern Bedeutung zu vermitteln.
:::

### Pools {#pools}

Ein Iconspeicher ist eine Sammlung von häufig verwendeten Icons, die einen einfachen Zugriff und eine Wiederverwendung ermöglichen. Durch die Verwendung von Icons aus einem Iconspeicher können Sie sicherstellen, dass die Icons in Ihrer App erkennbar sind und einen einheitlichen Stil teilen. Die Verwendung von webforJ ermöglicht es Ihnen, aus drei Speichern zu wählen oder einen benutzerdefinierten Speicher zu implementieren. Jeder Speicher hat eine umfangreiche Sammlung von Open-Source-Icons, die kostenlos verwendet werden können. Die Verwendung von webforJ gibt Ihnen die Flexibilität, aus drei Speichern zu wählen und sie als einzigartige Klassen zu verwenden, ohne die Icons direkt herunterladen zu müssen.

| Iconspeicher                                       | webforJ-Klasse |
| ------------------------------------------------- | --------------- |
| [Tabler](https://tabler-icons.io/)                | `TablerIcon` und `DwcIcon`.<br/>`DwcIcon` ist eine Teilmenge der Tabler-Icons.|
| [Feather](https://feathericons.com/)              | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)    | `FontAwesomeIcon`   |

:::tip

Wenn Sie daran interessiert sind, Ihren eigenen Iconspeicher zu erstellen, lesen Sie [Erstellen benutzerdefinierter Pools](#creating-custom-pools).

:::

Sobald Sie den oder die Pools ausgewählt haben, die Sie in Ihrer App einfügen möchten, besteht der nächste Schritt darin, den Namen des Icons anzugeben, das Sie verwenden möchten.

### Namen {#names}

Um ein Icon in Ihre App einzufügen, benötigen Sie lediglich den Iconspeicher und den Iconnamen. Durchsuchen Sie die Iconspeicher-Website nach dem Icon, das Sie verwenden möchten, und verwenden Sie den Iconnamen als Parameter der `create()`-Methode. Darüber hinaus können Sie die Icons über Enums für die Klassen `FeatherIcon` und `DwcIcon` erstellen, sodass sie in der Code-Vervollständigung erscheinen können.

```java
// Erstellen eines Icons aus einem String-Namen
Icon image = TablerIcon.create("image");
// Erstellen eines Icons aus einem Enum
Icon image = FeatherIcon.IMAGE.create();
```

### Variationen {#variations}

Sie können Icons noch individueller gestalten, indem Sie Variationen nutzen. Bestimmte Icons ermöglichen es Ihnen, zwischen einer Umriss- oder einer ausgefüllten Version zu wählen, sodass Sie ein spezifisches Icon basierend auf Ihren Vorlieben betonen können. `FontAwesomeIcon` und `Tabler`-Icons bieten Variationen.

#### `FontAwesomeIcon`-Variationen {#fontawesomeicon-variations}

1. `REGULAR`: Die umrissene Variante von Icons. Dies ist die Standardeinstellung.
2. `SOLID`: Die ausgefüllte Variante von Icons.
3. `BRAND`: Die Variante, wenn Sie die Icons von Marken verwenden.

#### `TablerIcon`-Variationen {#tablericon-variations}

1. `OUTLINE`: Die umrissene Variante von Icons. Dies ist die Standardeinstellung.
2. `FILLED`: Die ausgefüllte Variante von Icons.

```java
// Eine ausgefüllte Variante eines Icons aus Font Awesome
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

Die folgende Demo zeigt, wie man Icons aus verschiedenen Speicherpools verwendet, Variationen anwendet und sie nahtlos in Komponenten integriert.

<ComponentDemo
path='/webforj/iconvariations'
files={['src/main/java/com/webforj/samples/views/icon/IconVariationsView.java']}
height='100px'
/>

## Hinzufügen von Icons zu Komponenten {#adding-icons-to-components}

Integrieren Sie Icons in Ihre Komponenten mithilfe von Slots. Slots bieten flexible Optionen, um Komponenten nützlicher zu machen. Es ist vorteilhaft, einem `Icon` zu einer Komponente hinzuzufügen, um die beabsichtigte Bedeutung für die Benutzer weiter zu verdeutlichen. Komponenten, die das `HasPrefixAndSuffix`-Interface implementieren, können ein `Icon` oder andere gültige Komponenten enthalten. Die hinzugefügten Komponenten können in den `prefix`- und `suffix`-Slots platziert werden und sowohl das Gesamtdesign als auch die Benutzererfahrung verbessern.

Mit den `prefix`- und `suffix`-Slots können Sie festlegen, ob Sie das Icon vor oder nach dem Text mit den Methoden `setPrefixComponent()` und `setSuffixComponent()` platzieren möchten.

Die Entscheidung, ob ein Icon vor oder nach dem Text auf einer Komponente platziert wird, hängt weitgehend von Zweck und Designkontext ab.

### Icon-Platzierung: vor VS nach {#icon-placement-before-vs-after}

Icons, die vor dem Komponententext positioniert sind, helfen den Benutzern, die Hauptaktion oder Funktion der Komponente schnell zu verstehen, insbesondere bei universell erkennbaren Icons wie dem Speicher-Icon. Icons vor dem Text einer Komponente bieten eine logische Verarbeitungsreihenfolge, die die Benutzer natürlich durch die beabsichtigte Aktion führt, was für Schaltflächen, deren Hauptfunktion eine sofortige Aktion ist, vorteilhaft ist.

Andererseits ist es effektiv, Icons nach dem Komponententext zu platzieren, um Aktionen anzuzeigen, die zusätzlichen Kontext oder Optionen bieten, und die Klarheit und Hinweise zur Navigation zu verbessern. Icons nach dem Text einer Komponente sind ideal für Komponenten, die entweder ergänzende Informationen bieten oder die Benutzer in einem Richtungfluss leiten.

Letztendlich ist Konsistenz der Schlüssel. Sobald Sie einen Stil gewählt haben, halten Sie ihn über Ihre gesamte Website hinweg für ein kohärentes und benutzerfreundliches Design aufrecht.

<ComponentDemo
path='/webforj/iconprefixsuffix'
files={['src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java']}
height='100px'
/>️

## Erstellen benutzerdefinierter Pools {#creating-custom-pools}

Über die Nutzung bestehender Iconsammlungen hinaus haben Sie die Möglichkeit, einen benutzerdefinierten Pool zu erstellen, der für benutzerdefinierte Logos oder Avatare verwendet werden kann. Ein benutzerdefinierter Pool von Icons kann in einem zentralen Verzeichnis oder im Ressourcenordner (Kontext) gespeichert werden, wodurch der Icon-Verwaltungsprozess vereinfacht wird. Ein benutzerdefinierter Pool macht die App-Erstellung konsistenter und reduziert die Wartung über verschiedene Komponenten und Module hinweg.

Benutzerdefinierte Pools können aus einem Ordner erstellt werden, der SVG-Bilder enthält, und durch Verwendung der Klasse `IconPoolBuilder`. Von dort aus können Sie den Namen Ihres benutzerdefinierten Pools wählen und diesen mit den SVG-Dateinamen verwenden, um benutzerdefinierte Icon-Komponenten zu erstellen.

```java
// Erstellen eines benutzerdefinierten Pools namens "app-pool", der Bilder für ein Logo und einen Avatar hat.
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
Stellen Sie sicher, dass Sie Icons mit gleicher Breite und Höhe entwerfen, da `Icon`-Komponenten darauf ausgelegt sind, einen quadratischen Raum einzunehmen.
:::

### Benutzerdefinierte Pool-Fabrik {#custom-pool-factory}

Sie können auch eine Fabrikklasse für einen benutzerdefinierten Pool in webforJ erstellen, ähnlich wie `FeatherIcon`. Dies ermöglicht es Ihnen, Icon-Ressourcen innerhalb eines bestimmten Pools zu erstellen und zu verwalten und die Code-Vervollständigung zu ermöglichen. Jedes Icon kann über die Methode `create()` instanziiert werden, die ein `Icon` zurückgibt. Die Fabrikklasse sollte poolspezifische Metadaten bereitstellen, wie den Poolnamen und die Kennung des Icons, formatiert nach dem Dateinamen des Bildes. Dieses Design ermöglicht einen einfachen, standardisierten Zugriff auf Icon-Ressourcen aus dem benutzerdefinierten Pool mithilfe von Enum-Konstanten, was Skalierbarkeit und Wartungsfähigkeit in der Icon-Verwaltung unterstützt.

```java
/// Erstellen einer benutzerdefinierten Pool-Fabrik für den app-pool
public enum AppPoolIcon implements IconFactory {
  LOGO, AVATAR_DEFAULT;

  public Icon create() {
    return new Icon(String.valueOf(this), this.getPool());
  }

  /**
   * @return den Poolnamen für die Icons
   */
  @Override
  public String getPool() {
    return "app-pool";
  }

  /**
   * @return den Iconnamen
   */
  @Override
  public String toString() {
    return this.name().toLowerCase(Locale.ENGLISH).replace('_', '-');
  }
}
```

Der folgende Code zeigt die beiden verschiedenen Möglichkeiten, einen benutzerdefinierten Pool zu verwenden.

```java
IconPoolBuilder.fromDirectory("app-pool", "context://icons");

// Ein Icon erstellen, das die Namen des benutzerdefinierten Pools und der Bilddatei verwendet
Icon customLogo = new Icon("logo", "app-pool");

// Ein Icon erstellen, das die benutzerdefinierte Pool-Fabrik aus dem vorherigen Code verwendet
Icon customLogo = AppPoolIcon.LOGO.create();
```

## Icon-Schaltflächen {#icon-buttons}
Eine `Icon`-Komponente ist nicht auswählbar, aber für Aktionen, die am besten nur mit einem Icon dargestellt werden, wie Benachrichtigungen oder Warnungen, können Sie die `IconButton` verwenden.

 ```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("Sie haben eine neue Nachricht!", "Ding Dong!")
  });
```

## Beste Praktiken

- **Barrierefreiheit:** Verwenden Sie ein Tooltip oder ein Label für Icons, um Ihre App für sehbehinderte Benutzer zugänglich zu machen, die auf Bildschirmlesegeräte angewiesen sind.
- **Vermeidung von Mehrdeutigkeit:** Vermeiden Sie die Verwendung von Icons, wenn die Bedeutung nicht klar oder weithin verstanden wird. Wenn Benutzer raten müssen, was das Icon darstellt, verfehlt es seinen Zweck.
- **Icons sparsam verwenden:** Zu viele Icons können Benutzer überwältigen, verwenden Sie also Icons nur, wenn sie Klarheit bieten oder die Komplexität reduzieren.

## Stil
Ein Icon erbt das Thema seiner direkten Elternkomponente, aber Sie können dies übersteuern, indem Sie ein Thema direkt auf ein `Icon` anwenden.

### Themen
Icon-Komponenten verfügen über sieben separate Themen, die für ein schnelles Styling ohne CSS bereitgestellt werden. Diese Themen sind vordefinierte Stile, die auf Icons angewendet werden können, um deren Erscheinungsbild und visuelle Präsentation zu ändern. Sie bieten einen schnellen und konsistenten Weg, das Aussehen von Icons in einer App anzupassen.

Während es viele Anwendungsmöglichkeiten für jedes der verschiedenen Themen gibt, sind einige Beispiele:

- `DANGER`: Am besten für Aktionen mit schweren Folgen geeignet, wie das Löschen ausgefüllter Informationen oder das permanente Löschen eines Kontos/Daten.
- `DEFAULT`: Geeignet für Aktionen in einer App, die keine besondere Aufmerksamkeit erfordern und allgemein sind, wie das Umschalten einer Einstellung.
- `PRIMARY`: Geeignet als Haupt-„Call-to-Action“ auf einer Seite, wie Anmelden, Änderungen speichern oder zu einer anderen Seite weitergehen.
- `SUCCESS`: Ausgezeichnet zur Visualisierung des erfolgreichen Abschlusses eines Elements in einer App, wie das Einreichen eines Formulars oder den Abschluss eines Anmeldevorgangs. Das Erfolgsthema kann programmgesteuert angewendet werden, sobald eine erfolgreiche Aktion abgeschlossen ist.
- `WARNING`: Nützlich, um anzuzeigen, dass ein Benutzer dabei ist, eine potenziell riskante Aktion auszuführen, wie das Navigieren von einer Seite mit ungespeicherten Änderungen. Diese Aktionen sind oft weniger einschneidend als solche, die das Danger-Thema verwenden würden.
- `GRAY`: Gut für subtile Aktionen, wie kleinere Einstellungen oder Aktionen, die mehr ergänzend zu einer Seite sind und nicht Teil der Hauptfunktionalität.
- `INFO`: Gut zur Bereitstellung zusätzlicher erläuternder Informationen für einen Benutzer.

<TableBuilder name={['Icon', 'IconButton']} />
