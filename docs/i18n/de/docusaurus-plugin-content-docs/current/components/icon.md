---
title: Icon
sidebar_position: 55
_i18n_hash: 5c32d2def53818005b15e22290fb3d52
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

Die `Icon`-Komponente zeigt Icons an, die sich auf jede Größe skalieren lassen, ohne ihre Qualität zu verlieren. Sie können aus drei integrierten Icon-Pools wählen oder eigene erstellen. Icons dienen als visuelle Hinweise für Navigation und Aktionen und reduzieren die Notwendigkeit von Textlabels in Ihrer Benutzeroberfläche.

<!-- INTRO_END -->

## Grundlagen {#basics}

Jedes `Icon` ist als skalierbares Vektorgrafikbild (SVG) konzipiert, was bedeutet, dass es problemlos auf jede Größe skaliert werden kann, ohne Klarheit oder Qualität zu verlieren. Darüber hinaus werden `Icon`-Komponenten bedarfsgerecht von einem Content-Delivery-Network (CDN) geladen, was hilft, die Latenz zu reduzieren und die Gesamtleistung zu verbessern.

Beim Erstellen eines `Icons` müssen Sie einen bestimmten Pool und den Namen des Icons selbst angeben. Einige Icons bieten auch die Wahl zwischen einer Umrandung oder einer gefüllten Version über [Variationen](#variations).

<ComponentDemo 
path='/webforj/iconbasics?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconBasicsView.java'
height='100px'
/>

:::tip Wussten Sie schon?
Einige Komponenten, wie `PasswordField` und `TimeField`, haben integrierte Icons, um den Nutzern Bedeutungen zu vermitteln.
:::

### Pools {#pools}

Ein Icon-Pool ist eine Sammlung von häufig verwendeten Icons, die einen einfachen Zugriff und Wiederverwendung ermöglicht. Durch die Verwendung von Icons aus einem Icon-Pool können Sie sicherstellen, dass die Icons in Ihrer App erkennbar sind und einen konsistenten Stil aufweisen. Die Verwendung von webforJ ermöglicht es Ihnen, aus drei Pools zu wählen oder einen benutzerdefinierten Pool zu implementieren. Jeder Pool verfügt über eine umfangreiche Sammlung von Open-Source-Icons, die kostenlos verwendet werden können. Die Verwendung von webforJ bietet Ihnen die Flexibilität, aus drei Pools zu wählen und sie als einzigartige Klassen zu verwenden, ohne die Icons direkt herunterzuladen.

| Icon-Pool                                         | webforJ-Klasse |
| --------                                          | ------- |
| [Tabler](https://tabler-icons.io/)                | `TablerIcon` und `DwcIcon`.<br/>`DwcIcon` ist eine Teilmenge der Tabler-Icons.|    
| [Feather](https://feathericons.com/)              | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)    | `FontAwesomeIcon`   |

:::tip

Wenn Sie daran interessiert sind, Ihren eigenen Icon-Pool zu erstellen, siehe [Erstellen benutzerdefinierter Pools](#creating-custom-pools).

:::

Sobald Sie den Pool oder die Pools ausgewählt haben, die Sie in Ihrer App verwenden möchten, besteht der nächste Schritt darin, den Namen des Icons anzugeben, das Sie verwenden möchten.

### Namen {#names}

Um ein Icon in Ihrer App einzuschließen, benötigen Sie lediglich den Icon-Pool und den Icon-Namen. Durchsuchen Sie die Website des Icon-Pools nach dem Icon, das Sie verwenden möchten, und verwenden Sie den Icon-Namen als Parameter der Methode `create()`. Darüber hinaus können Sie die Icons über Enums für die Klassen `FeatherIcon` und `DwcIcon` erstellen, sodass sie in der Codevervollständigung erscheinen.

```java
// Erstellen Sie ein Icon aus einem String-Namen
Icon image = TablerIcon.create("image");
// Erstellen Sie ein Icon aus einem Enum
Icon image = FeatherIcon.IMAGE.create();
```

### Variationen {#variations}

Sie können Icons noch individueller gestalten, indem Sie Variationen nutzen. Bestimmte Icons erlauben es Ihnen, zwischen einer Umrandung oder einer gefüllten Version zu wählen, sodass Sie ein bestimmtes Icon basierend auf Ihren Vorlieben hervorheben können. `FontAwesomeIcon` und `Tabler`-Icons bieten Variationen.

#### `FontAwesomeIcon`-Variationen {#fontawesomeicon-variations}

1. `REGULAR`: Die umrissene Variation der Icons. Dies ist die Standardversion.
2. `SOLID`: Die gefüllte Variation der Icons.
3. `BRAND`: Die Variation für die Verwendung von Markensymbolen.

#### `TablerIcon`-Variationen {#tablericon-variations}

1. `OUTLINE`: Die umrissene Variation der Icons. Dies ist die Standardversion.
2. `FILLED`: Die gefüllte Variation der Icons.

```java
// Eine gefüllte Variation eines Icons von Font Awesome
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

Das folgende Demo zeigt, wie man Icons aus verschiedenen Pools verwendet, Variationen anwendet und sie nahtlos in Komponenten integriert.

<ComponentDemo 
path='/webforj/iconvariations?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconVariationsView.java'
height='100px'
/>

## Icons zu Komponenten hinzufügen {#adding-icons-to-components}

Integrieren Sie Icons in Ihre Komponenten mithilfe von Slots. Slots bieten flexible Optionen, um Komponenten nützlicher zu machen. Es ist vorteilhaft, ein `Icon` zu einer Komponente hinzuzufügen, um die beabsichtigte Bedeutung für die Nutzer weiter zu verdeutlichen. Komponenten, die das Interface `HasPrefixAndSuffix` implementieren, können ein `Icon` oder andere gültige Komponenten enthalten. Die hinzugefügten Komponenten können in den `prefix`- und `suffix`-Slots platziert werden und sowohl das Gesamtdesign als auch die Benutzererfahrung verbessern.

Mit den `prefix`- und `suffix`-Slots können Sie entscheiden, ob Sie das Icon vor oder nach dem Text mithilfe der Methoden `setPrefixComponent()` und `setSuffixComponent()` platzieren möchten.

Die Entscheidung, ob ein Icon vor oder nach dem Text einer Komponente platziert werden soll, hängt weitgehend vom Zweck und dem Designkontext ab.

### Icon-Platzierung: vor VS nach {#icon-placement-before-vs-after}

Icons, die vor dem Text der Komponente positioniert sind, helfen den Nutzern, die primäre Aktion oder den Zweck der Komponente schnell zu verstehen, insbesondere bei universell anerkannten Icons wie dem Speicher-Icon. Icons vor dem Text einer Komponente bieten eine logische Verarbeitungsreihenfolge und führen die Nutzer natürlich durch die beabsichtigte Aktion, was vorteilhaft ist für Schaltflächen, deren Hauptfunktion eine sofortige Handlung ist.

Andererseits ist die Platzierung von Icons nach dem Text der Komponente effektiv für Aktionen, die zusätzlichen Kontext oder Optionen bieten und Klarheit und Hinweise zur Navigation verbessern. Icons nach dem Text einer Komponente sind ideal für Komponenten, die entweder ergänzende Informationen bieten oder die Nutzer in einem Richtungsscrollfluss leiten.

Letztendlich ist Konsistenz der Schlüssel. Sobald Sie einen Stil gewählt haben, halten Sie ihn auf Ihrer gesamten Website für ein kohärentes und benutzerfreundliches Design ein.

<ComponentDemo 
path='/webforj/iconprefixsuffix?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java'
height='100px'
/>️

## Benutzerdefinierte Pools erstellen {#creating-custom-pools}

Neben der Nutzung vorhandener Icon-Sammlungen haben Sie die Möglichkeit, einen benutzerdefinierten Pool zu erstellen, der für benutzerdefinierte Logos oder Avatare verwendet werden kann. Ein benutzerdefinierter Pool von Icons kann in einem zentralen Verzeichnis oder im Ressourcenordner (Kontext) gespeichert werden, was den Prozess des Icon-Managements vereinfacht. Ein benutzerdefinierter Pool macht die Erstellung von Apps konsistenter und reduziert die Wartung über verschiedene Komponenten und Module hinweg.

Benutzerdefinierte Pools können aus einem Ordner mit SVG-Bildern erstellt werden und indem die Klasse `IconPoolBuilder` verwendet wird. Von dort aus können Sie den Namen Ihres benutzerdefinierten Pools wählen und diesen gemeinsam mit den SVG-Dateinamen verwenden, um benutzerdefinierte Icon-Komponenten zu erstellen.

```java
// Erstellen eines benutzerdefinierten Pools namens "app-pool", der Bilder für ein Logo und einen Avatar enthält.
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
Stellen Sie sicher, dass die Icons mit gleicher Breite und Höhe gestaltet sind, da `Icon`-Komponenten so konzipiert sind, dass sie einen quadratischen Raum einnehmen.
:::

### Benutzerdefinierte Poolfabrik {#custom-pool-factory}

Sie können auch eine Fabrikklasse für einen benutzerdefinierten Pool in webforJ erstellen, genau wie `FeatherIcon`. Dies ermöglicht es Ihnen, Icon-Ressourcen innerhalb eines bestimmten Pools zu erstellen und zu verwalten und die Codevervollständigung zu ermöglichen. Jedes Icon kann über die Methode `create()` instanziiert werden, die ein `Icon` zurückgibt. Die Fabrikklasse sollte pool-spezifische Metadaten bereitstellen, wie den Poolnamen und die Kennung des Icons, die im Format des Bilddateinamens formatiert sind. Dieses Design ermöglicht einen einfachen, standardisierten Zugriff auf Icon-Ressourcen aus dem benutzerdefinierten Pool mittels Enum-Konstanten und unterstützt die Skalierbarkeit und Wartbarkeit im Icon-Management.

```java
/// Erstellen einer benutzerdefinierten Poolfabrik für app-pool
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

Der folgende Code zeigt die zwei verschiedenen Möglichkeiten, einen benutzerdefinierten Pool zu verwenden.

```java
IconPoolBuilder.fromDirectory("app-pool", "context://icons");

// Erstellen Sie ein Icon unter Verwendung der Namen des benutzerdefinierten Pools und der Bilddatei
Icon customLogo = new Icon("logo", "app-pool");

// Erstellen Sie ein Icon unter Verwendung der benutzerdefinierten Poolfabrik aus dem vorherigen Snippet
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

- **Zugänglichkeit:** Verwenden Sie ein Tooltip oder ein Label für Icons, um Ihre App für sehbehinderte Nutzer zugänglich zu machen, die auf Bildschirmleser angewiesen sind.
- **Vermeiden Sie Mehrdeutigkeit:** Vermeiden Sie die Verwendung von Icons, wenn die Bedeutung nicht klar oder allgemein anerkannt ist. Wenn Nutzer raten müssen, was das Icon darstellt, erfüllt es seinen Zweck nicht.
- **Verwenden Sie Icons sparsam:** Zu viele Icons können Nutzer überwältigen, verwenden Sie Icons nur, wenn sie Klarheit hinzufügen oder die Komplexität reduzieren.

## Styling

Ein Icon erbt das Thema seiner direkten Elternkomponente, aber Sie können dies umgehen, indem Sie ein Thema direkt auf ein `Icon` anwenden.

### Themen

Icon-Komponenten bieten sieben verschiedene Themen für eine schnelle Stilgebung ohne CSS. Diese Themen sind vordefinierte Stile, die auf Icons angewendet werden können, um deren Erscheinungsbild und visuelle Präsentation zu ändern. Sie bieten eine schnelle und konsistente Möglichkeit, das Aussehen von Icons in der gesamten App anzupassen.

Während es viele Anwendungsfälle für jedes der verschiedenen Themen gibt, sind einige Beispiele:

- `DANGER`: Am besten geeignet für Aktionen mit schweren Konsequenzen, wie das Löschen ausgefüllter Informationen oder das permanente Löschen eines Kontos/Daten.
- `DEFAULT`: Angemessen für Aktionen in einer App, die keine besondere Aufmerksamkeit erfordern und allgemein sind, wie das Umschalten einer Einstellung.
- `PRIMARY`: Angemessen als Haupt-"Call-to-Action" auf einer Seite, wie das Anmelden, Speichern von Änderungen oder das Weiterführen auf eine andere Seite.
- `SUCCESS`: Hervorragend geeignet, um den erfolgreichen Abschluss eines Elements in einer App zu visualisieren, wie das Einreichen eines Formulars oder den Abschluss eines Anmeldevorgangs. Das Erfolgsthema kann programmgesteuert angewendet werden, sobald eine erfolgreiche Aktion abgeschlossen ist.
- `WARNING`: Nützlich, um anzuzeigen, dass ein Nutzer möglicherweise eine riskante Aktion ausführt, wie das Navigieren von einer Seite mit ungespeicherten Änderungen. Diese Aktionen sind oft weniger einschneidend als solche, die das Danger-Thema verwenden würden.
- `GRAY`: Gut für subtile Aktionen, wie geringfügige Einstellungen oder Aktionen, die mehr ergänzend zu einer Seite sind und nicht Teil der Hauptfunktionalität.
- `INFO`: Gut, um zusätzlichen klärenden Informationen an einen Nutzer bereitzustellen.

<TableBuilder name={['Icon', 'IconButton']} />
