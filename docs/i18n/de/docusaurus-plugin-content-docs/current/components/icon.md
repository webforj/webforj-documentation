---
title: Icon
sidebar_position: 55
_i18n_hash: 2da7d4e8288df67fc46f2a3ba84e12ee
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

Die webforJ `Icon`-Komponente ermöglicht es Ihnen, Icons mühelos in Ihre Benutzeroberfläche einzufügen.
Icons sind ein grundlegender Bestandteil zur Verbesserung des Designs der Benutzeroberfläche und helfen den Benutzern, schneller nach handlungsrelevanten Elementen auf dem Bildschirm zu suchen.
Die Verwendung von Icons in Ihrer App schafft visuelle Hinweise für die Navigation und Aktionen, wodurch die Menge an benötigtem Text reduziert und die Benutzeroberfläche vereinfacht wird. Sie können aus drei bestehenden Icon-Pools wählen, und webforJ bietet Ihnen auch die Möglichkeit, neue Icons von Grund auf neu zu erstellen.

:::tip Wussten Sie schon?

Einige Komponenten, wie `PasswordField` und `TimeField`, haben eingebaute Icons, die helfen, die Bedeutung für Endbenutzer zu vermitteln.

:::

## Grundlagen {#basics}

Jedes `Icon` ist als skalierbares Vektorgrafikbild (SVG) konzipiert, das bedeutet, es kann problemlos auf jede Größe skaliert werden, ohne an Klarheit oder Qualität zu verlieren.
Darüber hinaus werden `Icon`-Komponenten bei Bedarf von einem Content Delivery Network (CDN) geladen, was hilft, die Latenz zu verringern und die Gesamtleistung zu verbessern.

Beim Erstellen eines `Icon` müssen Sie einen bestimmten Pool und den Namen des Icons selbst identifizieren.
Einige Icons bieten auch die Wahl zwischen einer umrandeten oder einer gefüllten Version über [Variationen](#variations).

<ComponentDemo 
path='/webforj/iconbasics?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconBasicsView.java'
height='100px'
/>

### Pools {#pools}

Ein Icon-Pool ist eine Sammlung von häufig verwendeten Icons, die einfachen Zugriff und Wiederverwendbarkeit ermöglicht. Indem Sie Icons aus einem Icon-Pool verwenden, können Sie sicherstellen, dass die Icons in Ihrer App erkennbar sind und einen konsistenten Stil aufweisen.
Die Verwendung von webforJ ermöglicht es Ihnen, aus drei Pools auszuwählen oder einen benutzerdefinierten Pool umzusetzen.
Jeder Pool verfügt über eine umfangreiche Sammlung von Open-Source-Icons, die kostenlos verwendet werden können.
Die Verwendung von webforJ gibt Ihnen die Flexibilität, aus drei Pools zu wählen und diese als einzigartige Klassen zu verwenden, ohne die Icons direkt herunterladen zu müssen.

| Icon-Pool                                         | webforJ-Klasse |
| --------                                          | ------- |
| [Tabler](https://tabler-icons.io/)                | `TablerIcon` und `DwcIcon`.<br/>`DwcIcon` ist eine Teilmenge der Tabler-Icons.|    
| [Feather](https://feathericons.com/)              | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)    | `FontAwesomeIcon`   |

:::tip

Wenn Sie daran interessiert sind, Ihren eigenen Icon-Pool zu erstellen, lesen Sie [Creating custom pools](#creating-custom-pools).

:::

Sobald Sie den Pool oder die Pools ausgewählt haben, die Sie in Ihrer App einfügen möchten, ist der nächste Schritt, den Namen des Icons anzugeben, das Sie verwenden möchten.

### Namen {#names}

Um ein Icon in Ihrer App einzufügen, benötigen Sie lediglich den Icon-Pool und den Namen des Icons. Durchsuchen Sie die Website des Icon-Pools nach dem Icon, das Sie verwenden möchten, und benutzen Sie den Icon-Namen als Parameter der `create()`-Methode.
Zusätzlich können Sie die Icons über Enums für die Klassen `FeatherIcon` und `DwcIcon` erstellen, wodurch sie in der Codevervollständigung erscheinen.

```java
// Erstellen eines Icons aus einem String-Namen
Icon image = TablerIcon.create("image");
// Erstellen eines Icons aus einem Enum
Icon image = FeatherIcon.IMAGE.create();
```

### Variationen {#variations}

Sie können Icons noch mehr personalisieren, indem Sie Variationen nutzen.
Bestimmte Icons erlauben es Ihnen, zwischen einer umrandeten oder einer gefüllten Version zu wählen, wodurch Sie ein bestimmtes Icon basierend auf Ihrer Präferenz hervorheben können. `FontAwesomeIcon` und `Tabler`-Icons bieten Variationen.

#### `FontAwesomeIcon`-Variationen {#fontawesomeicon-variations}

1. `REGULAR`: Die umrandete Variation der Icons. Dies ist der Standard.
2. `SOLID`: Die gefüllte Variation der Icons.
3. `BRAND`: Die Variation, wenn Sie die Icons von Marken verwenden.

#### `TablerIcon`-Variationen {#tablericon-variations}

1. `OUTLINE`: Die umrandete Variation der Icons. Dies ist der Standard.
2. `FILLED`: Die gefüllte Variation der Icons.

```java
// Eine gefüllte Variation eines Icons von Font Awesome
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

Die folgende Demo zeigt, wie man Icons aus verschiedenen Pools verwendet, Variationen anwendet und sie nahtlos in Komponenten integriert.

<ComponentDemo 
path='/webforj/iconvariations?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconVariationsView.java'
height='100px'
/>

## Icons in Komponenten hinzufügen {#adding-icons-to-components}

Integrieren Sie Icons in Ihre Komponenten mithilfe von Slots. Slots bieten flexible Optionen, um Komponenten nützlicher zu machen. Es ist vorteilhaft, ein `Icon` zu einer Komponente hinzuzufügen, um die beabsichtigte Bedeutung für die Benutzer weiter zu klären.
Komponenten, die das Interface `HasPrefixAndSuffix` implementieren, können ein `Icon` oder andere gültige Komponenten enthalten. Die hinzugefügten Komponenten können in den `prefix`- und `suffix`-Slots platziert werden und sowohl das Gesamtdesign als auch die Benutzererfahrung verbessern.

Mit den `prefix`- und `suffix`-Slots können Sie festlegen, ob das Icon vor oder nach dem Text unter Verwendung der Methoden `setPrefixComponent()` und `setSuffixComponent()` platziert werden soll.

Die Entscheidung, ob ein Icon vor oder nach dem Text in einer Komponente platziert werden soll, hängt weitgehend vom Zweck und dem Gestaltungskontext ab.

### Icon-Platzierung: vor VS nach {#icon-placement-before-vs-after}

Icons, die vor dem Text der Komponente platziert sind, helfen den Benutzern, die Hauptaktion oder den Zweck der Komponente schnell zu verstehen, insbesondere bei allgemein anerkannten Icons wie dem Speichern-Icon.
Icons vor dem Text einer Komponente bieten eine logische Verarbeitungsreihenfolge, die den Benutzern auf natürliche Weise durch die beabsichtigte Aktion führt, was vorteilhaft für Schaltflächen ist, deren Hauptfunktion eine sofortige Aktion ist.

Andererseits ist die Platzierung von Icons nach dem Text der Komponente effektiv für Aktionen, die zusätzlichen Kontext oder Optionen bieten, wodurch Klarheit und Hinweise zur Navigation verbessert werden.
Icons nach dem Text einer Komponente sind ideal für Komponenten, die entweder ergänzende Informationen bieten oder die Benutzer in einem Richtungskontext leiten.

Letztendlich ist Konsistenz der Schlüssel. Sobald Sie einen Stil ausgewählt haben, sollten Sie ihn auf Ihrer gesamten Website beibehalten, um ein kohärentes und benutzerfreundliches Design zu gewährleisten.

<ComponentDemo 
path='/webforj/iconprefixsuffix?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java'
height='100px'
/>️

## Erstellung benutzerdefinierter Pools {#creating-custom-pools}

Über die Nutzung bestehender Icon-Sammlungen hinaus haben Sie die Möglichkeit, einen benutzerdefinierten Pool zu erstellen, der für benutzerdefinierte Logos oder Avatare verwendet werden kann.
Ein benutzerdefinierter Pool von Icons kann in einem zentralen Verzeichnis oder im Ressourcenordner (Kontext) gespeichert werden, was den Managementprozess der Icons vereinfacht.
Ein benutzerdefinierter Pool macht die App-Erstellung konsistenter und reduziert den Wartungsaufwand über verschiedene Komponenten und Module hinweg.

Benutzerdefinierte Pools können aus einem Ordner erstellt werden, der SVG-Bilder enthält, und unter Verwendung der Klasse `IconPoolBuilder`. Von dort aus können Sie den Namen Ihres benutzerdefinierten Pools auswählen und diesen mit den SVG-Dateinamen verwenden, um benutzerdefinierte Icon-Komponenten zu erstellen.

```java
// Erstellung eines benutzerdefinierten Pools namens "app-pool", der Bilder für ein Logo und einen Avatar enthält.
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
Achten Sie darauf, Icons mit gleicher Breite und Höhe zu entwerfen, da `Icon`-Komponenten dafür ausgelegt sind, einen quadratischen Raum einzunehmen.
:::

### Fabrik für benutzerdefinierte Pools {#custom-pool-factory}

Sie können auch eine Fabrikklasse für einen benutzerdefinierten Pool in webforJ erstellen, ähnlich wie `FeatherIcon`. Dies ermöglicht es Ihnen, Icon-Ressourcen innerhalb eines bestimmten Pools zu erstellen und zu verwalten und die Codevervollständigung zu ermöglichen.
Jedes Icon kann über die `create()`-Methode instanziiert werden, die ein `Icon` zurückgibt. Die Fabrikklasse sollte poolspezifische Metadaten bereitstellen, wie den Poolnamen und die Kennung des Icons, die dem Dateinamen des Bildes entsprechen.
Dieses Design ermöglicht einen einfachen, standardisierten Zugriff auf Icon-Assets aus dem benutzerdefinierten Pool mithilfe von Enum-Konstanten, die Skalierbarkeit und Wartbarkeit im Icon-Management unterstützen.

```java
/// Erstellung einer Fabrik für benutzerdefinierte Pools für app-pool
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

// Erstellen eines Icons mit den Namen des benutzerdefinierten Pools und der Bilddatei
Icon customLogo = new Icon("logo", "app-pool");

// Erstellen eines Icons mit der benutzerdefinierten Poolfabrik aus dem vorherigen Snippet
Icon customLogo = AppPoolIcon.LOGO.create();
```

## Icon-Schaltflächen {#icon-buttons}
Ein `Icon`-Komponente ist nicht auswählbar, aber für Aktionen, die am besten nur mit einem Icon dargestellt werden, wie Benachrichtigungen oder Alarme, können Sie die `IconButton` verwenden.

 ```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("Sie haben eine neue Nachricht!", "Ding Dong!")
  });
```

## Best Practices

- **Zugänglichkeit:** Verwenden Sie ein Tool-Tipp oder ein Label für Icons, um Ihre App für sehbehinderte Benutzer zugänglich zu machen, die sich auf Bildschirmlesegeräte verlassen.
- **Vermeiden Sie Mehrdeutigkeit:** Vermeiden Sie die Verwendung von Icons, wenn die Bedeutung nicht klar oder weit verbreitet ist. Wenn Benutzer raten müssen, was das Icon darstellt, ist der Zweck verloren.
- **Verwenden Sie Icons sparsam:** Zu viele Icons können die Benutzer überwältigen, verwenden Sie Icons daher nur, wenn sie zur Klarheit beitragen oder die Komplexität reduzieren.

## Styling
Ein Icon erbt das Thema seiner unmittelbaren übergeordneten Komponente, aber Sie können dies überschreiben, indem Sie ein Thema direkt auf ein `Icon` anwenden.

### Themen
Icon-Komponenten verfügen über sieben unterschiedliche integrierte Themen für eine schnelle Stilgestaltung ohne die Verwendung von CSS. Diese Themen sind vordefinierte Stile, die auf Icons angewendet werden können, um deren Erscheinungsbild und visuelle Präsentation zu ändern. Sie bieten eine schnelle und konsistente Möglichkeit, das Aussehen von Icons in einer App anzupassen.

Während es viele Anwendungsfälle für jedes der verschiedenen Themen gibt, sind hier einige Beispiele:

- `DANGER`: Am besten für Aktionen mit schwerwiegenden Konsequenzen, wie das Leeren ausgefüllter Informationen oder das permanente Löschen eines Kontos/Daten.
- `DEFAULT`: Geeignet für Aktionen in einer App, die keine besondere Aufmerksamkeit erfordern und generisch sind, wie das Umschalten einer Einstellung.
- `PRIMARY`: Geeignet als Haupt-"Handlungsaufruf" auf einer Seite, wie das Anmelden, Speichern von Änderungen oder Fortfahren zu einer anderen Seite.
- `SUCCESS`: Ausgezeichnet zur Visualisierung des erfolgreichen Abschlusses eines Elements in einer App, wie das Einreichen eines Formulars oder das Abschließen eines Anmeldevorgangs. Das Erfolgsthema kann programmatisch angewendet werden, sobald eine erfolgreiche Aktion abgeschlossen ist.
- `WARNING`: Nützlich, um anzuzeigen, dass ein Benutzer dabei ist, eine potenziell riskante Aktion auszuführen, wie das Navigieren von einer Seite mit ungespeicherten Änderungen. Diese Aktionen sind oft weniger wirkungsvoll als diejenigen, die das Gefahrenthema verwenden.
- `GRAY`: Gut für subtile Aktionen, wie geringfügige Einstellungen oder Aktionen, die eher ergänzend zu einer Seite sind und nicht Teil der Hauptfunktionalität.
- `INFO`: Gut, um Benutzern zusätzliche klärende Informationen zu bieten.

<TableBuilder name="Icon" />
