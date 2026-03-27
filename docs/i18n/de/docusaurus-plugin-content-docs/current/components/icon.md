---
title: Icon
sidebar_position: 55
_i18n_hash: 8350df59fb9ce335776bc0556861cda5
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

Die `Icon`-Komponente zeigt Icons an, die in jede Größe skaliert werden können, ohne an Qualität zu verlieren. Sie können aus drei integrierten Icon-Pools auswählen oder eigene erstellen. Icons dienen als visuelle Hinweise für Navigation und Aktionen, wodurch die Notwendigkeit von Textlabeln in Ihrer Benutzeroberfläche verringert wird.

<!-- INTRO_END -->

## Grundlagen {#basics}

Jedes `Icon` ist als skalierbares Vektorgrafikbild (SVG) gestaltet, was bedeutet, dass es problemlos auf jede Größe skaliert werden kann, ohne an Klarheit oder Qualität zu verlieren. Darüber hinaus werden `Icon`-Komponenten bei Bedarf von einem Content Delivery Network (CDN) geladen, was hilft, die Latenz zu verringern und die Gesamtleistung zu verbessern.

Wenn Sie ein `Icon` erstellen, müssen Sie einen bestimmten Pool und den Namen des Icons selbst angeben. Einige Icons bieten auch die Wahl zwischen einer umrißenen oder einer gefüllten Version über [Variationen](#variations).

<ComponentDemo 
path='/webforj/iconbasics?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconBasicsView.java'
height='100px'
/>

:::tip Wusstest du schon?
Einige Komponenten, wie `PasswordField` und `TimeField`, haben integrierte Icons, um den Endbenutzern Bedeutung zu vermitteln.
:::

### Pools {#pools}

Ein Icon-Pool ist eine Sammlung von häufig verwendeten Icons, die einen einfachen Zugriff und die Wiederverwendung ermöglichen. Durch die Verwendung von Icons aus einem Icon-Pool können Sie sicherstellen, dass die Icons in Ihrer App erkennbar sind und einen konsistenten Stil haben. Die Verwendung von webforJ ermöglicht es Ihnen, aus drei Pools zu wählen oder einen benutzerdefinierten Pool zu implementieren. Jeder Pool enthält eine umfangreiche Sammlung von Open-Source-Icons, die kostenlos genutzt werden können. Die Verwendung von webforJ gibt Ihnen die Flexibilität, aus drei Pools zu wählen und sie als einzigartige Klassen zu verwenden, ohne sich um das direkte Herunterladen von Icons kümmern zu müssen.

| Icon-Pool                                         | webforJ-Klasse |
| --------                                          | ------- |
| [Tabler](https://tabler-icons.io/)                | `TablerIcon` und `DwcIcon`.<br/>`DwcIcon` ist eine Teilmenge der Tabler-Icons.|    
| [Feather](https://feathericons.com/)              | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)    | `FontAwesomeIcon`   |

:::tip

Wenn Sie daran interessiert sind, Ihren eigenen Icon-Pool zu erstellen, sehen Sie sich [Erstellen benutzerdefinierter Pools](#creating-custom-pools) an.

:::

Sobald Sie den Pool oder die Pools ausgewählt haben, die Sie in Ihrer App einbeziehen möchten, besteht der nächste Schritt darin, den Namen des Icons anzugeben, das Sie verwenden möchten.

### Namen {#names}

Um ein Icon in Ihrer App einzuschließen, benötigen Sie nur den Icon-Pool und den Icon-Namen. Durchsuchen Sie die Website des Icon-Pools nach dem Icon, das Sie verwenden möchten, und verwenden Sie den Icon-Namen als Parameter der `create()`-Methode. Darüber hinaus können Sie die Icons durch Enums für die Klassen `FeatherIcon` und `DwcIcon` erstellen, sodass sie in der Codevervollständigung erscheinen.

```java
// Erstellen eines Icons aus einem String-Namen
Icon image = TablerIcon.create("image");
// Erstellen eines Icons aus einem Enum
Icon image = FeatherIcon.IMAGE.create();
```

### Variationen {#variations}

Sie können Icons noch persönlicher gestalten, indem Sie Variationen verwenden. Bestimmte Icons erlauben Ihnen die Wahl zwischen einer umrißenen oder einer gefüllten Version, sodass Sie ein bestimmtes Icon je nach Vorliebe hervorheben können. `FontAwesomeIcon` und `Tabler`-Icons bieten Variationen.

#### `FontAwesomeIcon`-Variationen {#fontawesomeicon-variations}

1. `REGULAR`: Die umrandete Variation der Icons. Dies ist die Standardvariante.
2. `SOLID`: Die gefüllte Variation der Icons.
3. `BRAND`: Die Variation, wenn Sie die Icons von Marken verwenden.

#### `TablerIcon`-Variationen {#tablericon-variations}

1. `OUTLINE`: Die umrandete Variation der Icons. Dies ist die Standardvariante.
2. `FILLED`: Die gefüllte Variation der Icons.

```java
// Eine gefüllte Variation eines Icons aus Font Awesome
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

Die folgende Demo veranschaulicht, wie man Icons aus unterschiedlichen Pools verwendet, Variationen anwendet und nahtlos in Komponenten integriert.

<ComponentDemo 
path='/webforj/iconvariations?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconVariationsView.java'
height='100px'
/>

## Hinzufügen von Icons zu Komponenten {#adding-icons-to-components}

Integrieren Sie Icons in Ihre Komponenten mithilfe von Slots. Slots bieten flexible Optionen, um Komponenten nützlicher zu machen. Es ist vorteilhaft, ein `Icon` zu einer Komponente hinzuzufügen, um die beabsichtigte Bedeutung für Benutzer weiter zu verdeutlichen. Komponenten, die das Interface `HasPrefixAndSuffix` implementieren, können ein `Icon` oder andere gültige Komponenten enthalten. Die hinzugefügten Komponenten können in den Slots `prefix` und `suffix` platziert werden und sowohl das Gesamtdesign als auch die Benutzererfahrung verbessern.

Mit den `prefix`- und `suffix`-Slots können Sie bestimmen, ob das Icon vor oder nach dem Text erscheinen soll, indem Sie die Methoden `setPrefixComponent()` und `setSuffixComponent()` verwenden.

Die Entscheidung, ob ein Icon vor oder nach dem Text einer Komponente platziert werden soll, hängt weitgehend vom Zweck und dem Designkontext ab.

### Icon-Platzierung: vor VS nach {#icon-placement-before-vs-after}

Icons, die vor dem Komponententext positioniert sind, helfen Benutzern dabei, die primäre Aktion oder den Zweck der Komponente schnell zu verstehen, insbesondere bei allgemein anerkannten Icons wie dem Speicher-Icon. Icons vor einem Komponententext bieten eine logische Verarbeitungsreihenfolge und führen Benutzer auf natürliche Weise durch die beabsichtigte Aktion, was für Schaltflächen, deren Hauptfunktion eine unmittelbare Aktion ist, von Vorteil ist.

Andererseits ist die Platzierung von Icons nach dem Komponententext effektiv für Aktionen, die zusätzlichen Kontext oder Optionen bieten und die Klarheit und Hinweise zur Navigation verbessern. Icons nach dem Text einer Komponente sind ideal für Komponenten, die entweder ergänzende Informationen bereitstellen oder Benutzer in einem Richtungsfluss führen.

Letztendlich ist Konsistenz der Schlüssel. Sobald Sie einen Stil gewählt haben, halten Sie ihn auf Ihrer gesamten Website für ein kohärentes und benutzerfreundliches Design.

<ComponentDemo 
path='/webforj/iconprefixsuffix?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java'
height='100px'
/>️

## Erstellen benutzerdefinierter Pools {#creating-custom-pools}

Neben der Verwendung bestehender Icon-Sammlungen haben Sie die Möglichkeit, einen benutzerdefinierten Pool zu erstellen, der für benutzerdefinierte Logos oder Avatare verwendet werden kann. Ein benutzerdefinierter Pool von Icons kann in einem zentralen Verzeichnis oder im Ressourcenordner (Kontext) gespeichert werden, was den Prozess der Icon-Verwaltung vereinfacht. Ein benutzerdefinierter Pool sorgt für eine konsistentere App-Erstellung und reduziert den Wartungsaufwand über verschiedene Komponenten und Module hinweg.

Benutzerdefinierte Pools können aus einem Ordner erstellt werden, der SVG-Bilder enthält, und durch die Verwendung der Klasse `IconPoolBuilder`. Von dort aus können Sie den Namen Ihres benutzerdefinierten Pools auswählen und diesen mit den SVG-Dateinamen verwenden, um benutzerdefinierte Icon-Komponenten zu erstellen.

```java
// Erstellung eines benutzerdefinierten Pools namens "app-pool", der Bilder für ein Logo und einen Avatar enthält.
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
Stellen Sie sicher, dass Sie Icons mit gleicher Breite und Höhe entwerfen, da `Icon`-Komponenten dafür ausgelegt sind, einen quadratischen Raum einzunehmen.
:::

### Fabrik für benutzerdefinierte Pools {#custom-pool-factory}

Sie können auch eine Fabrikklasse für einen benutzerdefinierten Pool in webforJ erstellen, ähnlich wie bei `FeatherIcon`. Dadurch können Sie Icon-Ressourcen innerhalb eines bestimmten Pools erstellen und verwalten und die Codevervollständigung ermöglichen. Jedes Icon kann durch die Methode `create()` instanziiert werden, die ein `Icon` zurückgibt. Die Fabrikklasse sollte pool-spezifische Metadaten bereitstellen, wie den Poolnamen und die Kennung des Icons, formatiert nach dem Dateinamen des Bildes. Dieses Design ermöglicht einen einfachen, standardisierten Zugriff auf Icon-Ressourcen aus dem benutzerdefinierten Pool unter Verwendung von Enum-Konstanten und unterstützt die Skalierbarkeit und Wartbarkeit in der Icon-Verwaltung.

```java
/// Erstellung einer Fabrik für benutzerdefinierte Pools für app-pool
public enum AppPoolIcon implements IconFactory {
  LOGO, AVATAR_DEFAULT;

  public Icon create() {
    return new Icon(String.valueOf(this), this.getPool());
  }

  /**
   * @return der Poolname für die Icons
   */
  @Override
  public String getPool() {
    return "app-pool";
  }

  /**
   * @return der Iconname
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

// Erstellen eines Icons mit der benutzerdefinierten Pool-Fabrik aus dem vorherigen Snippet
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

- **Barrierefreiheit:** Verwenden Sie ein Tooltip oder ein Label für Icons, um Ihre App für sehbehinderte Benutzer, die auf Bildschirmlesegeräte angewiesen sind, zugänglich zu machen.
- **Vermeiden Sie Mehrdeutigkeit:** Vermeiden Sie die Verwendung von Icons, wenn die Bedeutung nicht klar oder weit verbreitet ist. Wenn Benutzer raten müssen, was das Icon darstellt, wird der Zweck untergraben.
- **Verwenden Sie Icons sparsam:** Zu viele Icons können die Benutzer überwältigen, verwenden Sie also Icons nur, wenn sie Klarheit hinzufügen oder die Komplexität verringern.

## Styling
Ein Icon erbt das Thema seiner übergeordneten Komponente, aber Sie können dies übersteuern, indem Sie ein Thema direkt auf ein `Icon` anwenden.

### Themen
Icon-Komponenten verfügen über sieben verschiedene Themen, die eingebaut sind, um eine schnelle Gestaltung ohne die Verwendung von CSS zu ermöglichen. Diese Themen sind vordefinierte Stile, die auf Icons angewendet werden können, um deren Aussehen und visuelle Darstellung zu ändern. Sie bieten eine schnelle und konsistente Möglichkeit, das Aussehen von Icons in einer App anzupassen.

Es gibt viele Anwendungsfälle für jedes der verschiedenen Themen, einige Beispiele sind:

- `DANGER`: Am besten geeignet für Aktionen mit schweren Folgen, z. B. das Löschen ausgefüllter Informationen oder das permanente Löschen eines Kontos/Daten.
- `DEFAULT`: Geeignet für Aktionen in einer App, die keine besondere Aufmerksamkeit erfordern und allgemein sind, wie das Umstellen einer Einstellung.
- `PRIMARY`: Geeignet als Haupt-"Call-to-Action" auf einer Seite, z. B. für die Anmeldung, das Speichern von Änderungen oder das Fortsetzen zu einer anderen Seite.
- `SUCCESS`: Hervorragend geeignet für die Visualisierung der erfolgreichen Durchführung eines Elements in einer App, z. B. für das Einreichen eines Formulars oder den Abschluss eines Anmeldeprozesses. Das Erfolgsthema kann programmgesteuert angewendet werden, sobald eine erfolgreiche Aktion abgeschlossen wurde.
- `WARNING`: Nützlich, um anzuzeigen, dass ein Benutzer eine potenziell riskante Aktion ausführt, z. B. das Navigieren weg von einer Seite mit nicht gespeicherten Änderungen. Diese Aktionen sind oft weniger einschneidend als solche, die das Danger-Thema verwenden würden.
- `GRAY`: Gut für subtile Aktionen, wie z. B. kleinere Einstellungen oder Aktionen, die eher ergänzend zu einer Seite sind und nicht Teil der Hauptfunktionalität sind.
- `INFO`: Gut, um zusätzliche klärende Informationen für einen Benutzer bereitzustellen.

<TableBuilder name="Icon" />
