---
title: Icon
sidebar_position: 55
_i18n_hash: ae46080226d89087113b901c748f0942
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

Die `Icon`-Komponente zeigt Icons an, die in jeder Größe ohne Qualitätsverlust skalierbar sind. Sie können aus drei integrierten Icon-Pools wählen oder eigene erstellen. Icons dienen als visuelle Hinweise für Navigation und Aktionen und reduzieren die Notwendigkeit für Textbeschriftungen in Ihrer Benutzeroberfläche.

<!-- INTRO_END -->

## Grundlagen {#basics}

Jedes `Icon` ist als skalierbare Vektorgrafik (SVG) gestaltet, was bedeutet, dass es problemlos auf jede Größe skaliert werden kann, ohne an Klarheit oder Qualität zu verlieren. Darüber hinaus werden `Icon`-Komponenten nach Bedarf von einem Content Delivery Network (CDN) geladen, was hilft, die Latenz zu verringern und die Gesamtleistung zu verbessern.

Wenn Sie ein `Icon` erstellen, müssen Sie einen bestimmten Pool und den Namen des Icons selbst identifizieren. Einige Icons bieten auch die Wahl zwischen einer umrandeten oder einer gefüllten Version über [Variationen](#variations).

<ComponentDemo
path='/webforj/iconbasics'
files={['src/main/java/com/webforj/samples/views/icon/IconBasicsView.java']}
height='100px'
/>

:::tip Wussten Sie das?
Einige Komponenten, wie `PasswordField` und `TimeField`, haben integrierte Icons, um den Endbenutzern beim Vermitteln der Bedeutung zu helfen.
:::

### Pools {#pools}

Ein Icon-Pool ist eine Sammlung von häufig verwendeten Icons, die einen einfachen Zugriff und eine Wiederverwendbarkeit ermöglicht. Durch die Verwendung von Icons aus einem Icon-Pool können Sie sicherstellen, dass die Icons in Ihrer App erkennbar sind und einen einheitlichen Stil aufweisen. Die Verwendung von webforJ ermöglicht es Ihnen, aus drei Pools zu wählen oder einen benutzerdefinierten Pool zu implementieren. Jeder Pool hat eine umfangreiche Sammlung von Open-Source-Icons, die kostenlos verwendet werden können. Mit webforJ können Sie aus drei Pools wählen und diese als einzigartige Klassen verwenden, ohne sich um das direkte Herunterladen der Icons kümmern zu müssen.

| Icon-Pool                                         | webforJ-Klasse |
| --------                                          | ------- |
| [Tabler](https://tabler-icons.io/)                | `TablerIcon` und `DwcIcon`.<br/>`DwcIcon` ist eine Teilmenge der Tabler-Icons.|    
| [Feather](https://feathericons.com/)              | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)    | `FontAwesomeIcon`   |

:::tip

Wenn Sie daran interessiert sind, Ihren eigenen Icon-Pool zu erstellen, siehe [Erstellen benutzerdefinierter Pools](#creating-custom-pools).

:::

Sobald Sie den Pool oder die Pools ausgewählt haben, die Sie in Ihrer App verwenden möchten, besteht der nächste Schritt darin, den Namen des Icons, das Sie verwenden möchten, anzugeben.

### Namen {#names}

Um ein Icon in Ihrer App einzubeziehen, benötigen Sie nur den Icon-Pool und den Icon-Namen. Durchsuchen Sie die Website des Icon-Pools nach dem Icon, das Sie verwenden möchten, und verwenden Sie den Icon-Namen als Parameter der `create()`-Methode. Zusätzlich können Sie die Icons durch Enums für die Klassen `FeatherIcon` und `DwcIcon` erstellen, damit sie in der Codevervollständigung angezeigt werden.

```java
// Erstellen eines Icons aus einem String-Namen
Icon image = TablerIcon.create("image");
// Erstellen eines Icons aus einem Enum
Icon image = FeatherIcon.IMAGE.create();
```

### Variationen {#variations}

Sie können Icons noch weiter personalisieren, indem Sie Variationen nutzen. Bestimmte Icons ermöglichen die Wahl zwischen einer umrandeten oder einer gefüllten Version, wodurch Sie ein bestimmtes Icon je nach Vorliebe hervorheben können. `FontAwesomeIcon` und `Tabler`-Icons bieten Variationen.

#### `FontAwesomeIcon`-Variationen {#fontawesomeicon-variations}

1. `REGULAR`: Die umrandete Variation der Icons. Dies ist der Standard.
2. `SOLID`: Die gefüllte Variation der Icons.
3. `BRAND`: Die Variation für die Verwendung der Icons von Marken.

#### `TablerIcon`-Variationen {#tablericon-variations}

1. `OUTLINE`: Die umrandete Variation der Icons. Dies ist der Standard.
2. `FILLED`: Die gefüllte Variation der Icons.

```java
// Eine gefüllte Variation eines Icons von Font Awesome
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

Das folgende Demo zeigt, wie man Icons aus verschiedenen Pools verwendet, Variationen anwendet und sie nahtlos in Komponenten integriert.

<ComponentDemo
path='/webforj/iconvariations'
files={['src/main/java/com/webforj/samples/views/icon/IconVariationsView.java']}
height='100px'
/>

## Icons in Komponenten hinzufügen {#adding-icons-to-components}

Integrieren Sie Icons in Ihre Komponenten mithilfe von Slots. Slots bieten flexible Optionen, um Komponenten nützlicher zu machen. Es ist vorteilhaft, ein `Icon` zu einer Komponente hinzuzufügen, um die beabsichtigte Bedeutung für die Benutzer weiter zu verdeutlichen. Komponenten, die das Interface `HasPrefixAndSuffix` implementieren, können ein `Icon` oder andere gültige Komponenten einschließen. Die hinzugefügten Komponenten können in den `prefix`- und `suffix`-Slots platziert werden und sowohl das Gesamtdesign als auch die Benutzererfahrung verbessern.

Mit den `prefix`- und `suffix`-Slots können Sie bestimmen, ob Sie das Icon vor oder nach dem Text mithilfe der Methoden `setPrefixComponent()` und `setSuffixComponent()` platzieren möchten.

Die Entscheidung, ob ein Icon vor oder nach dem Text auf einer Komponente platziert werden soll, hängt weitgehend von Zweck und Designkontext ab.

### Icon-Platzierung: Vor vs. Nach {#icon-placement-before-vs-after}

Icons, die vor dem Komponententext positioniert sind, helfen den Benutzern, die hauptsächliche Aktion oder den Zweck der Komponente schnell zu verstehen, insbesondere bei allgemein anerkannten Icons wie dem Speichern-Icon. Icons vor dem Text einer Komponente bieten eine logische Verarbeitungsreihenfolge, die die Benutzer auf natürliche Weise durch die beabsichtigte Aktion führt, was für Schaltflächen, deren Hauptfunktion eine sofortige Aktion ist, vorteilhaft ist.

Andererseits ist es effektiv, Icons nach dem Komponententext zu platzieren, wenn es um Aktionen geht, die zusätzliche Kontexte oder Optionen bereitstellen, um Klarheit und Hinweise für die Navigation zu verbessern. Icons nach dem Text einer Komponente sind ideal für Komponenten, die entweder ergänzende Informationen bieten oder Benutzer in einem Richtungsfluss leiten.

Letztendlich ist Konsistenz der Schlüssel. Sobald Sie einen Stil gewählt haben, halten Sie ihn über Ihre gesamte Website hinweg für ein zusammenhängendes und benutzerfreundliches Design aufrecht.

<ComponentDemo
path='/webforj/iconprefixsuffix'
files={['src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java']}
height='100px'
/>️

## Erstellung benutzerdefinierter Pools {#creating-custom-pools}

Über die Nutzung bestehender Icon-Sammlungen hinaus haben Sie die Möglichkeit, einen benutzerdefinierten Pool zu erstellen, der für benutzerdefinierte Logos oder Avatare verwendet werden kann. Ein benutzerdefinierter Pool von Icons kann in einem zentralisierten Verzeichnis oder im Ressourcenordner (Kontext) gespeichert werden, was den Managementprozess für Icons vereinfacht. Ein benutzerdefinierter Pool macht die Erstellung von Apps konsistenter und reduziert den Wartungsaufwand über verschiedene Komponenten und Module hinweg.

Benutzerdefinierte Pools können aus einem Ordner erstellt werden, der SVG-Bilder enthält, und durch die Verwendung der Klasse `IconPoolBuilder`. Von dort aus können Sie den Namen Ihres benutzerdefinierten Pools auswählen und diesen mit den SVG-Dateinamen verwenden, um benutzerdefinierte Icon-Komponenten zu erstellen.

```java
// Erstellung eines benutzerdefinierten Pools mit dem Namen "app-pool", der Bilder für ein Logo und einen Avatar hat.
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
Stellen Sie sicher, dass Sie Icons mit gleicher Breite und Höhe entwerfen, da `Icon`-Komponenten so gestaltet sind, dass sie einen quadratischen Raum einnehmen.
:::

### Benutzerdefinierte Poolfabrik {#custom-pool-factory}

Sie können auch eine Fabrikklasse für einen benutzerdefinierten Pool in webforJ erstellen, ähnlich wie `FeatherIcon`. Dadurch können Sie Icon-Ressourcen innerhalb eines bestimmten Pools erstellen und verwalten sowie die Codevervollständigung ermöglichen. Jedes Icon kann über die Methode `create()` instanziiert werden, die ein `Icon` zurückgibt. Die Fabrikklasse sollte pool-spezifische Metadaten bereitstellen, wie den Namen des Pools und die Kennung des Icons, die dem Dateinamen des Bildes formatiert sind. Dieses Design ermöglicht einen einfachen, standardisierten Zugriff auf Icon-Assets aus dem benutzerdefinierten Pool unter Verwendung von Enum-Konstanten, was die Skalierbarkeit und Wartbarkeit im Icon-Management unterstützt.

```java
/// Erstellung einer benutzerdefinierten Poolfabrik für app-pool
public enum AppPoolIcon implements IconFactory {
  LOGO, AVATAR_DEFAULT;

  public Icon create() {
    return new Icon(String.valueOf(this), this.getPool());
  }

  /**
   * @return den Namen des Pools für die Icons
   */
  @Override
  public String getPool() {
    return "app-pool";
  }

  /**
   * @return den Icon-Namen
   */
  @Override
  public String toString() {
    return this.name().toLowerCase(Locale.ENGLISH).replace('_', '-');
  }
}
```

Das folgende Snippet zeigt die zwei verschiedenen Möglichkeiten, einen benutzerdefinierten Pool zu verwenden.

```java
IconPoolBuilder.fromDirectory("app-pool", "context://icons");

// Erstellen eines Icons unter Verwendung der Namen des benutzerdefinierten Pools und der Bilddatei
Icon customLogo = new Icon("logo", "app-pool");

// Erstellen eines Icons unter Verwendung der benutzerdefinierten Poolfabrik aus dem vorherigen Snippet
Icon customLogo = AppPoolIcon.LOGO.create();
```

## Icon-Buttons {#icon-buttons}
Eine `Icon`-Komponente ist nicht auswählbar, aber für Aktionen, die am besten nur mit einem Icon dargestellt werden, wie Benachrichtigungen oder Warnungen, können Sie den `IconButton` verwenden.

 ```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("Sie haben eine neue Nachricht!", "Ding Dong!")
  });
```

## Beste Praktiken

- **Zugänglichkeit:** Verwenden Sie ein Tooltip oder ein Label auf Icons, um Ihre App für sehbehinderte Benutzer, die auf Screenreader angewiesen sind, zugänglich zu machen.
- **Vermeiden von Mehrdeutigkeit:** Vermeiden Sie es, Icons zu verwenden, wenn die Bedeutung nicht klar oder allgemein verständlich ist. Wenn Benutzer raten müssen, was das Icon darstellt, ist das Ziel verloren.
- **Verwenden Sie Icons sparsam:** Zu viele Icons können Benutzer überfordern, verwenden Sie Icons also nur, wenn sie Klarheit bieten oder die Komplexität reduzieren.

## Styling
Ein Icon erbt das Thema seiner unmittelbaren Elternkomponente, aber Sie können dies überschreiben, indem Sie ein Thema direkt auf ein `Icon` anwenden.

### Themen
Icon-Komponenten verfügen über sieben diskrete Themen, die zum schnellen Styling ohne die Verwendung von CSS integriert sind. Diese Themen sind vordefinierte Stile, die auf Icons angewendet werden können, um deren Erscheinungsbild und visuelle Präsentation zu ändern. Sie bieten eine schnelle und konsistente Möglichkeit, das Aussehen von Icons in einer App anzupassen.

Während es viele Anwendungsfälle für jedes der verschiedenen Themen gibt, sind hier einige Beispiele:

- `DANGER`: Am besten für Aktionen mit schwerwiegenden Konsequenzen, wie das Löschen ausgefüllter Informationen oder das dauerhafte Löschen eines Kontos/Daten.
- `DEFAULT`: Geeignet für Aktionen in einer App, die keine besondere Aufmerksamkeit erfordern und generisch sind, wie das Aktivieren oder Deaktivieren einer Einstellung.
- `PRIMARY`: Geeignet als "Call-to-Action" auf einer Seite, wie das Anmelden, Speichern von Änderungen oder das Fortfahren zu einer anderen Seite.
- `SUCCESS`: Ausgezeichnet zur Visualisierung des erfolgreichen Abschlusses eines Elements in einer App, wie die Übermittlung eines Formulars oder den Abschluss eines Anmeldevorgangs. Das Erfolgsthema kann programmgesteuert angewendet werden, sobald eine erfolgreiche Aktion abgeschlossen ist.
- `WARNING`: Nützlich, um anzuzeigen, dass ein Benutzer beabsichtigt, eine potenziell riskante Aktion auszuführen, wie das Navigieren von einer Seite mit ungespeicherten Änderungen. Diese Aktionen sind oft weniger wirkungsvoll als solche, die das Danger-Thema verwenden.
- `GRAY`: Gut für subtile Aktionen, wie kleinere Einstellungen oder Aktionen, die eher ergänzend zu einer Seite sind und nicht zur Hauptfunktionalität gehören.
- `INFO`: Gut, um einem Benutzer zusätzliche klärende Informationen bereitzustellen.

<TableBuilder name={['Icon', 'IconButton']} />
