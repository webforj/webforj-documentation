---
title: Icon
sidebar_position: 55
description: >-
  Render scalable SVG icons with the Icon component from Tabler, Feather, Font
  Awesome, or custom pools loaded on demand from a CDN.
_i18n_hash: f1572fcb6d1396a20fac32bfa282c091
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

Die `Icon`-Komponente zeigt Icons an, die auf jede Größe skaliert werden können, ohne dass die Qualität verloren geht. Sie können aus drei integrierten Icon-Pools wählen oder eigene erstellen. Icons dienen als visuelle Hinweise für Navigation und Aktionen und verringern die Notwendigkeit von Textbeschriftungen in Ihrer Benutzeroberfläche.

Jedes `Icon` wird als skalierbares Vektorgrafikbild (SVG) gerendert, das bei Bedarf von einem Content Delivery Network (CDN) geladen wird, um die Latenz niedrig zu halten. Um ein Icon zu erstellen, wählen Sie einen Icon-Pool und den Namen des Icons. Einige Icons bieten auch die Wahl zwischen einer Umriss- oder einer ausgefüllten Version über [Variationen](#variations).

<!-- INTRO_END -->

<ComponentDemo
path='/webforj/iconbasics'
files={['src/main/java/com/webforj/samples/views/icon/IconBasicsView.java']}
height='100px'
/>

:::tip Wussten Sie schon?
Einige Komponenten, wie `PasswordField` und `TimeField`, haben integrierte Icons, um den Endbenutzern Bedeutung zu vermitteln.
:::

## Pools {#pools}

Ein Icon-Pool ist eine Sammlung von häufig verwendeten Icons, die einfachen Zugriff und Wiederverwendung ermöglicht. Durch die Verwendung von Icons aus einem Icon-Pool können Sie sicherstellen, dass die Icons in Ihrer App erkennbar sind und einen konsistenten Stil aufweisen. Mit webforJ können Sie aus drei Pools wählen oder einen benutzerdefinierten Pool implementieren. Jeder Pool hat eine umfangreiche Sammlung von Open-Source-Icons, die kostenlos verwendet werden können. Die Verwendung von webforJ bietet Ihnen die Flexibilität, aus drei Pools zu wählen und diese als einzigartige Klassen zu verwenden, ohne sich um das direkte Herunterladen von Icons kümmern zu müssen.

| Icon-Pool                                         | webforJ-Klasse |
| --------                                          | ------- |
| [Tabler](https://tabler-icons.io/)                | `TablerIcon` und `DwcIcon`. <br/> `DwcIcon` ist eine Teilmenge der Tabler-Icons. |
| [Feather](https://feathericons.com/)              | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)    | `FontAwesomeIcon`   |

:::tip

Wenn Sie an der Erstellung Ihres eigenen Icon-Pools interessiert sind, sehen Sie sich [Erstellen benutzerdefinierter Pools](#creating-custom-pools) an.

:::

Sobald Sie den Pool oder die Pools ausgewählt haben, die Sie in Ihrer App einfügen möchten, besteht der nächste Schritt darin, den Namen des Icons anzugeben, das Sie verwenden möchten.

## Namen {#names}

Um ein Icon in Ihre App einzufügen, benötigen Sie nur den Icon-Pool und den Icon-Namen. Durchsuchen Sie die Website des Icon-Pools nach dem Icon, das Sie verwenden möchten, und verwenden Sie den Icon-Namen als Parameter der `create()`-Methode. Zusätzlich können Sie die Icons durch Enums für die Klassen `FeatherIcon` und `DwcIcon` erstellen, sodass sie in der Code-Vervollständigung erscheinen.

```java
// Erstellen eines Icons aus einem String-Namen
Icon image = TablerIcon.create("image");
// Erstellen eines Icons aus einem Enum
Icon image = FeatherIcon.IMAGE.create();
```

## Variationen {#variations}

Sie können Icons noch weiter personalisieren, indem Sie Variationen nutzen. Bestimmte Icons erlauben die Wahl zwischen einer Umriss- oder einer ausgefüllten Version, sodass Sie ein bestimmtes Icon basierend auf Ihrer Präferenz betonen können. `FontAwesomeIcon` und `Tabler`-Icons bieten Variationen.

### Variationen von `FontAwesomeIcon` {#fontawesomeicon-variations}

1. `REGULAR`: Die Umriss-Variation von Icons. Dies ist die Standardvariante.
2. `SOLID`: Die ausgefüllte Variation von Icons.
3. `BRAND`: Die Variation, wenn Sie die Icons von Marken verwenden.

### Variationen von `TablerIcon` {#tablericon-variations}

1. `OUTLINE`: Die Umriss-Variation von Icons. Dies ist die Standardvariante.
2. `FILLED`: Die ausgefüllte Variation von Icons.

```java
// Eine ausgefüllte Variation eines Icons von Font Awesome
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

Die folgende Demo zeigt, wie man Icons aus verschiedenen Pools verwendet, Variationen anwendet und sie nahtlos in Komponenten integriert.

<ComponentDemo
path='/webforj/iconvariations'
files={['src/main/java/com/webforj/samples/views/icon/IconVariationsView.java']}
height='100px'
/>

## Hinzufügen von Icons zu Komponenten {#adding-icons-to-components}

Integrieren Sie Icons in Ihre Komponenten mithilfe von Slots. Slots bieten flexible Optionen, um Komponenten nützlicher zu gestalten. Es ist vorteilhaft, einem Bestandteil ein `Icon` hinzuzufügen, um den beabsichtigten Sinn für Benutzer weiter zu verdeutlichen. Komponenten, die das `HasPrefixAndSuffix`-Interface implementieren, können ein `Icon` oder andere gültige Komponenten enthalten. Die hinzugefügten Komponenten können in den `prefix`- und `suffix`-Slots platziert werden und sowohl das Gesamtdesign als auch die Benutzererfahrung verbessern.

Durch die Verwendung von `prefix`- und `suffix`-Slots können Sie entscheiden, ob das Icon vor oder nach dem Text erscheinen soll, indem Sie die Methoden `setPrefixComponent()` und `setSuffixComponent()` verwenden.

Ob ein Icon vor oder nach dem Text einer Komponente platziert wird, hängt stark vom Zweck und Kontext des Designs ab.

### Platzierung des Icons: vor VS nach {#icon-placement-before-vs-after}

Icons, die vor dem Text der Komponente positioniert sind, helfen den Benutzern, schnell die primäre Aktion oder den Zweck der Komponente zu verstehen, insbesondere bei universell erkannten Icons wie dem Speichern-Icon. Icons vor dem Text einer Komponente bieten eine logische Verarbeitungsreihenfolge, die die Benutzer natürlich durch die beabsichtigte Aktion leitet, was für Schaltflächen, deren Hauptfunktion eine unmittelbare Aktion ist, von Vorteil ist.

Andererseits ist die Platzierung von Icons nach dem Text der Komponente bei Aktionen effektiv, die zusätzlichen Kontext oder Optionen bieten und die Klarheit sowie Hinweise zur Navigation verbessern. Icons nach dem Text einer Komponente sind ideal für Komponenten, die entweder ergänzende Informationen bereitstellen oder die Benutzer in einem Richtungsfluss leiten.

Letztendlich ist Konsistenz der Schlüssel. Sobald Sie einen Stil gewählt haben, halten Sie ihn auf Ihrer gesamten Seite ein, um ein kohärentes und benutzerfreundliches Design zu erzielen.

<ComponentDemo
path='/webforj/iconprefixsuffix'
files={['src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java']}
height='100px'
/>️

## Erstellen benutzerdefinierter Pools {#creating-custom-pools}

Über die Nutzung vorhandener Icon-Kollektionen hinaus haben Sie die Möglichkeit, einen benutzerdefinierten Pool zu erstellen, der für benutzerdefinierte Logos oder Avatare verwendet werden kann. Ein benutzerdefinierter Pool von Icons kann in einem zentralen Verzeichnis oder im Ressourcenordner (Kontext) gespeichert werden, wodurch der Icon-Management-Prozess vereinfacht wird. Ein benutzerdefinierter Pool macht die App-Erstellung konsistenter und reduziert die Wartung über verschiedene Komponenten und Module.

Benutzerdefinierte Pools können aus einem Ordner erstellt werden, der SVG-Bilder enthält, und durch die Verwendung der Klasse `IconPoolBuilder`. Von dort aus können Sie den Namen Ihres benutzerdefinierten Pools wählen und diesen zusammen mit den SVG-Dateinamen verwenden, um benutzerdefinierte Icon-Komponenten zu erstellen.

```java
// Erstellen eines benutzerdefinierten Pools namens "app-pool", der Bilder für ein Logo und einen Avatar hat.
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
Stellen Sie sicher, dass die Icons mit gleicher Breite und Höhe entworfen werden, da `Icon`-Komponenten darauf ausgelegt sind, einen quadratischen Raum einzunehmen.
:::

### Fabrik für benutzerdefinierte Pools {#custom-pool-factory}

Sie können auch eine Fabrikklasse für einen benutzerdefinierten Pool in webforJ erstellen, ähnlich wie `FeatherIcon`. Dies ermöglicht es Ihnen, Icon-Ressourcen innerhalb eines bestimmten Pools zu erstellen und zu verwalten und die Code-Vervollständigung zu unterstützen. Jedes Icon kann über die `create()`-Methode instanziiert werden, die ein `Icon` zurückgibt. Die Fabrikklasse sollte pool-spezifische Metadaten bereitstellen, wie den Poolnamen und die Identifikatoren des Icons, die dem Dateiformat des Bildes entsprechen. Dieses Design ermöglicht einen einfachen, standardisierten Zugriff auf Icon-Ressourcen aus dem benutzerdefinierten Pool unter Verwendung von Enum-Konstanten und unterstützt Skalierbarkeit und Wartungsfreundlichkeit im Icon-Management.

```java
/// Erstellen einer Fabrik für einen benutzerdefinierten Pool für "app-pool"
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
   * @return den Icon-Namen
   */
  @Override
  public String toString() {
    return this.name().toLowerCase(Locale.ENGLISH).replace('_', '-');
  }
}
```

Der folgende Code zeigt die zwei verschiedenen Möglichkeiten zur Verwendung eines benutzerdefinierten Pools.

```java
IconPoolBuilder.fromDirectory("app-pool", "context://icons");

// Erstellen eines Icons mit den Namen des benutzerdefinierten Pools und der Bilddatei
Icon customLogo = new Icon("logo", "app-pool");

// Erstellen eines Icons mit der benutzerdefinierten Pool-Fabrik aus dem vorherigen Snippet
Icon customLogo = AppPoolIcon.LOGO.create();
```

## Icon-Schaltflächen <DocChip chip="name" label="dwc-icon-button" /> {#icon-buttons}
Eine `Icon`-Komponente ist nicht auswählbar, aber für Aktionen, die am besten nur mit einem Icon dargestellt werden, wie Benachrichtigungen oder Warnungen, können Sie die `IconButton`-Komponente verwenden.

```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("Sie haben eine neue Nachricht!", "Ding Dong!")
});
```

## Beste Praktiken

- **Barrierefreiheit:** Verwenden Sie ein Tooltip oder ein Label für Icons, um Ihre App für sehbehinderte Benutzer, die auf Screenreader angewiesen sind, zugänglich zu machen.
- **Vermeiden Sie Mehrdeutigkeit:** Vermeiden Sie die Verwendung von Icons, wenn die Bedeutung nicht klar oder allgemein anerkannt ist. Wenn Benutzer raten müssen, was das Icon darstellt, wird der Zweck untergraben.
- **Verwenden Sie Icons sparsam:** Zu viele Icons können die Benutzer überfordern, verwenden Sie Icons nur, wenn sie Klarheit schaffen oder die Komplexität reduzieren.

## Stil
Ein Icon erbt das Design seines direkten Elternteils, aber Sie können dies überschreiben, indem Sie ein Design direkt auf ein `Icon` anwenden.

### Designs
Icon-Komponenten verfügen über sieben verschiedene Designs, die für eine schnelle Gestaltung ohne CSS integriert sind. Diese Designs sind vordefinierte Stile, die auf Icons angewendet werden können, um ihr Aussehen und ihre visuelle Präsentation zu ändern. Sie bieten eine schnelle und konsistente Möglichkeit, das Erscheinungsbild von Icons in einer App anzupassen.

Während es viele Anwendungen für jedes der verschiedenen Designs gibt, sind hier einige Beispiele:

- `DANGER`: Am besten geeignet für Aktionen mit schwerwiegenden Konsequenzen, wie das Löschen ausgefüllter Informationen oder das permanente Löschen eines Kontos/Daten.
- `DEFAULT`: Geeignet für Aktionen in einer App, die keiner besonderen Aufmerksamkeit bedürfen und allgemein sind, wie das Umschalten einer Einstellung.
- `PRIMARY`: Eignet sich als Haupt-"Call-to-Action" auf einer Seite, etwa zum Anmelden, Speichern von Änderungen oder Weiterschreiten zu einer anderen Seite.
- `SUCCESS`: Hervorragend geeignet zur Darstellung des erfolgreichen Abschlusses eines Elements in einer App, wie etwa zur Einreichung eines Formulars oder zum Abschluss eines Anmeldevorgangs. Das Erfolg-Design kann programmatisch angewendet werden, sobald eine erfolgreiche Aktion abgeschlossen wurde.
- `WARNING`: Nützlich, um darauf hinzuweisen, dass ein Benutzer dabei ist, eine potenziell riskante Aktion auszuführen, wie das Verlassen einer Seite mit nicht gespeicherten Änderungen. Diese Aktionen sind oft weniger bedeutsam als diejenigen, die das Gefahren-Design verwenden würden.
- `GRAY`: Gut für subtile Aktionen, wie geringfügige Einstellungen oder Aktionen, die eher ergänzend zu einer Seite sind und nicht Teil der Hauptfunktionalität.
- `INFO`: Gut, um Benutzern zusätzliche, erläuternde Informationen bereitzustellen.

<TableBuilder name={['Icon', 'IconButton']} />
