---
title: Icon
sidebar_position: 55
_i18n_hash: ab67367c75477c4366e5e86862dac630
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-icon" />
<DocChip chip='since' label='24.11' />
<JavadocLink type="icons" location="com/webforj/component/icons/Icon" top='true'/>

Die webforJ `Icon`-Komponente ermöglicht es Ihnen, Icons mühelos in Ihre Benutzeroberfläche einzufügen. 
Icons sind ein wesentlicher Bestandteil zur Verbesserung des Designs der Benutzeroberfläche, da sie es den Benutzern erleichtern, den Bildschirm nach umsetzbaren Elementen zu durchsuchen. 
Die Verwendung von Icons in Ihrer App schafft visuelle Hinweise für die Navigation und Aktionen, wodurch der benötigte Text reduziert und die Benutzeroberfläche vereinfacht werden kann. Sie können aus drei vorhandenen Icon-Pools wählen, und webforJ bietet Ihnen auch die Möglichkeit, neue von Grund auf zu erstellen.

:::tip Wussten Sie schon?

Einige Komponenten, wie `PasswordField` und `TimeField`, verfügen über integrierte Icons, um den Endbenutzern Bedeutung zu vermitteln.

:::

## Grundlagen {#basics}

Jedes `Icon` ist als Scalable Vector Graphics (SVG)-Bild entworfen, was bedeutet, dass es sich problemlos auf jede Größe skalieren lässt, ohne Klarheit oder Qualität zu verlieren. 
Darüber hinaus werden `Icon`-Komponenten bedarfsgerecht von einem Content Delivery Network (CDN) geladen, was hilft, die Latenz zu reduzieren und die Gesamtleistung zu verbessern.

Wenn Sie ein `Icon` erstellen, müssen Sie einen bestimmten Pool und den Namen des Symbols selbst identifizieren. 
Einige Icons bieten auch die Wahl zwischen einer umrissenen oder einer gefüllten Version über [Variationen](#variations).

<ComponentDemo 
path='/webforj/iconbasics?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconBasicsView.java'
height='100px'
/>

### Pools {#pools}

Ein Icon-Pool ist eine Sammlung von häufig verwendeten Icons, die einen einfachen Zugriff und eine Wiederverwendung ermöglichen. Durch die Verwendung von Icons aus einem Icon-Pool können Sie sicherstellen, dass die Icons in Ihrer App erkennbar sind und einen konsistenten Stil teilen. 
Die Nutzung von webforJ ermöglicht es Ihnen, aus drei Pools zu wählen oder einen benutzerdefinierten Pool zu implementieren. 
Jeder Pool verfügt über eine umfangreiche Sammlung von Open-Source-Icons, die kostenlos verwendet werden können. 
Die Verwendung von webforJ bietet Ihnen die Flexibilität, aus drei Pools zu wählen und diese als einzigartige Klassen zu nutzen, ohne den Aufwand, einige der Icons direkt herunterzuladen.

| Icon-Pool                                         | webforJ-Klasse |
| --------                                          | ------- |
| [Tabler](https://tabler-icons.io/)               | `TablerIcon` und `DwcIcon`.<br/>`DwcIcon` ist eine Teilmenge der Tabler-Icons.|    
| [Feather](https://feathericons.com/)             | `FeatherIcon`    |
| [Font Awesome](https://fontawesome.com/search)   | `FontAwesomeIcon`   |

:::tip

Wenn Sie daran interessiert sind, Ihren eigenen Icon-Pool zu erstellen, siehe [Erstellen von benutzerdefinierten Pools](#creating-custom-pools).

:::

Sobald Sie den oder die Pools ausgewählt haben, die Sie in Ihrer App einfügen möchten, besteht der nächste Schritt darin, den Namen des Icons anzugeben, das Sie verwenden möchten.

### Namen {#names}

Um ein Icon in Ihre App einzufügen, benötigen Sie nur den Icon-Pool und den Icon-Namen. Durchsuchen Sie die Website des Icon-Pools nach dem Icon, das Sie verwenden möchten, und verwenden Sie den Icon-Namen als Parameter der Methode `create()`. 
Zusätzlich können Sie die Icons durch Enums für die Klassen `FeatherIcon` und `DwcIcon` erstellen, um sie in der Codevervollständigung anzuzeigen.

```java
// Erstellen Sie ein Icon aus einem String-Namen
Icon image = TablerIcon.create("image");
// Erstellen Sie ein Icon aus einem Enum
Icon image = FeatherIcon.IMAGE.create();
```

### Variationen {#variations}

Sie können Icons noch weiter personalisieren, indem Sie Variationen verwenden. 
Bestimmte Icons ermöglichen es Ihnen, zwischen einer umrissenen oder einer gefüllten Version zu wählen, sodass Sie ein bestimmtes Icon basierend auf Ihren Vorlieben hervorheben können. `FontAwesomeIcon`- und `Tabler`-Icons bieten Variationen.

#### `FontAwesomeIcon`-Variationen {#fontawesomeicon-variations}

1. `REGULAR`: Die umrissene Variante der Icons. Dies ist die Standardversion. 
2. `SOLID`: Die gefüllte Variante der Icons. 
3. `BRAND`: Die Variante, wenn Sie die Icons von Marken verwenden.

#### `TablerIcon`-Variationen {#tablericon-variations}

1. `OUTLINE`: Die umrissene Variante der Icons. Dies ist die Standardversion. 
2. `FILLED`: Die gefüllte Variante der Icons.

```java
// Eine gefüllte Variante eines Icons von Font Awesome
Icon music = FontAwesomeIcon.create("user", FontAwesomeIcon.Variate.SOLID);
```

Die folgende Demo veranschaulicht, wie man Icons aus verschiedenen Pools verwendet, Variationen anwendet und sie nahtlos in Komponenten integriert.

<ComponentDemo 
path='/webforj/iconvariations?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconVariationsView.java'
height='100px'
/>

## Icons zu Komponenten hinzufügen {#adding-icons-to-components}

Integrieren Sie Icons in Ihre Komponenten mithilfe von Slots. Slots bieten flexible Optionen zur Verbesserung der Nützlichkeit von Komponenten. Es ist vorteilhaft, ein `Icon` zu einer Komponente hinzuzufügen, um den beabsichtigten Sinn für die Benutzer weiter zu verdeutlichen. 
Komponenten, die das `HasPrefixAndSuffix`-Interface implementieren, können ein `Icon` oder andere gültige Komponenten enthalten. Die hinzugefügten Komponenten können in den `prefix`- und `suffix`-Slots platziert werden und sowohl das Gesamtdesign als auch die Benutzererfahrung verbessern.

Mit den `prefix`- und `suffix`-Slots können Sie bestimmen, ob das Icon vor oder nach dem Text angezeigt werden soll, indem Sie die Methoden `setPrefixComponent()` und `setSuffixComponent()` verwenden.

Die Entscheidung, ob ein Icon vor oder nach dem Text einer Komponente platziert werden soll, hängt weitgehend von dem Zweck und dem Designkontext ab.

### Icon-Platzierung: vor vs. nach {#icon-placement-before-vs-after}

Icons, die vor dem Text der Komponente platziert sind, helfen den Benutzern, die primäre Aktion oder den Zweck der Komponente schnell zu verstehen, insbesondere bei allgemein anerkannten Icons wie dem Speichern-Icon. 
Icons vor dem Text einer Komponente bieten eine logische Verarbeitungsreihenfolge und führen die Benutzer auf natürliche Weise zur beabsichtigten Aktion, was besonders vorteilhaft für Schaltflächen ist, deren Hauptfunktion eine unmittelbare Aktion ist.

Andererseits ist es effektiv, Icons nach dem Text einer Komponente zu platzieren, um Aktionen zu kennzeichnen, die zusätzlichen Kontext oder Optionen bieten und die Klarheit und Hinweise zur Navigation erhöhen. 
Icons nach dem Text einer Komponente sind ideal für Komponenten, die entweder zusätzliche Informationen bieten oder die Benutzer in einem Fluss leiten.

Letztendlich ist Konsistenz der Schlüssel. Sobald Sie einen Stil ausgewählt haben, halten Sie ihn auf Ihrer gesamten Website für ein einheitliches und benutzerfreundliches Design aufrecht.

<ComponentDemo 
path='/webforj/iconprefixsuffix?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/icon/IconPrefixSuffixView.java'
height='100px'
/>

## Erstellen benutzerdefinierter Pools {#creating-custom-pools}

Neben der Nutzung vorhandener Icon-Sammlungen haben Sie die Möglichkeit, einen benutzerdefinierten Pool zu erstellen, der für benutzerdefinierte Logos oder Avatare verwendet werden kann. 
Ein benutzerdefinierter Pool von Icons kann in einem zentralen Verzeichnis oder im Ressourcenordner (Kontext) gespeichert werden, was den Verwaltungsprozess der Icons vereinfacht. 
Ein benutzerdefinierter Pool sorgt für eine konsistentere App-Erstellung und reduziert den Wartungsaufwand für verschiedene Komponenten und Module.

Benutzerdefinierte Pools können aus einem Ordner erstellt werden, der SVG-Bilder enthält, und mithilfe der `IconPoolBuilder`-Klasse. Von dort aus können Sie den Namen Ihres benutzerdefinierten Pools wählen und diesen mit den SVG-Dateinamen verwenden, um benutzerdefinierte Icon-Komponenten zu erstellen.

```java
// Erstellung eines benutzerdefinierten Pools mit dem Namen "app-pool", der Bilder für ein Logo und einen Avatar enthält.
IconPoolBuilder.fromDirectory("app-pool", "context://icons");
Icon customLogo = new Icon("logo", "app-pool");
Icon customAvatar = new Icon("avatar-default", "app-pool");
```

:::tip
Stellen Sie sicher, dass Sie Icons mit gleicher Breite und Höhe gestalten, da `Icon`-Komponenten dazu entworfen sind, einen quadratischen Raum einzunehmen.
:::

### Benutzerdefinierte Pool-Fabrik {#custom-pool-factory}

Sie können auch eine Fabrikklasse für einen benutzerdefinierten Pool in webforJ erstellen, genau wie `FeatherIcon`. Dadurch können Sie Icon-Ressourcen innerhalb eines bestimmten Pools erstellen und verwalten und die Codevervollständigung ermöglichen. 
Jedes Icon kann über die Methode `create()` instanziiert werden, die ein `Icon` zurückgibt. Die Fabrikklasse sollte pool-spezifische Metadaten bereitstellen, wie den Poolnamen und die Kennung des Icons, die dem Bilddateinamen entspricht. 
Dieses Design ermöglicht einen einfachen, standardisierten Zugriff auf Icon-Ressourcen aus dem benutzerdefinierten Pool mithilfe von Enum-Konstanten und unterstützt die Skalierbarkeit und Wartbarkeit im Icon-Management.

```java
// Erstellen einer benutzerdefinierten Pool-Fabrik für app-pool
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

Der folgende Snippet zeigt die zwei verschiedenen Möglichkeiten, einen benutzerdefinierten Pool zu verwenden.

```java
IconPoolBuilder.fromDirectory("app-pool", "context://icons");

// Erstellen eines Icons unter Verwendung der Namen des benutzerdefinierten Pools und des Bilddateinamens
Icon customLogo = new Icon("logo", "app-pool");

// Erstellen eines Icons unter Verwendung der benutzerdefinierten Pool-Fabrik aus dem vorherigen Snippet
Icon customLogo = AppPoolIcon.LOGO.create();
```

## Icon-Buttons {#icon-buttons}
Eine `Icon`-Komponente ist nicht wählbar, aber für Aktionen, die am besten nur mit einem Icon dargestellt werden, wie Benachrichtigungen oder Warnungen, können Sie den `IconButton` verwenden.

```java
IconButton bell = new IconButton(FeatherIcon.BELL.create());
bell.onClick(e -> {
  showMessageDialog("Sie haben eine neue Nachricht!", "Ding Dong!")
});
```

## Beste Praktiken

- **Barrierefreiheit:** Verwenden Sie ein Tooltip oder ein Label auf Icons, um Ihre App für sehbehinderte Benutzer, die auf Bildschirmlesegeräte angewiesen sind, zugänglich zu machen.
- **Vermeiden von Mehrdeutigkeit:** Vermeiden Sie die Verwendung von Icons, wenn die Bedeutung nicht klar oder allgemein anerkannt ist. Wenn Benutzer raten müssen, was das Symbol darstellt, verfehlt es den Zweck.
- **Icons sparsam verwenden:** Zu viele Icons können Benutzer überwältigen. Verwenden Sie Icons daher nur, wenn sie Klarheit hinzufügen oder die Komplexität reduzieren.

## Styling
Ein Icon erbt das Thema seiner direkten Elternkomponente, aber Sie können dies überschreiben, indem Sie ein Thema direkt auf ein `Icon` anwenden.

### Themen
Icon-Komponenten verfügen über sieben verschiedene Themen, die für eine schnelle Gestaltung ohne die Verwendung von CSS eingebaut sind. Diese Themen sind vordefinierte Stile, die auf Icons angewendet werden können, um ihr Aussehen und ihre visuelle Präsentation zu ändern. Sie bieten eine schnelle und konsistente Möglichkeit, das Erscheinungsbild von Icons in einer App anzupassen.

Während es viele Verwendungsmöglichkeiten für jedes dieser verschiedenen Themen gibt, sind einige Beispiele:

- `DANGER`: Am besten für Aktionen mit schwerwiegenden Konsequenzen, wie das Löschen ausgefüllter Informationen oder das dauerhafte Löschen eines Kontos/Daten.
- `DEFAULT`: Angemessen für Aktionen in einer App, die keine besondere Aufmerksamkeit erfordern und allgemein sind, wie das Umschalten einer Einstellung.
- `PRIMARY`: Angemessen als Haupt-"Call-to-Action" auf einer Seite, wie das Registrieren, Speichern von Änderungen oder das Fortfahren zu einer anderen Seite.
- `SUCCESS`: Hervorragend zur Visualisierung des erfolgreichen Abschlusses eines Elements in einer App, wie der Einreichung eines Formulars oder dem Abschluss eines Registrierungsprozesses. Das Erfolgsthema kann programmatisch angewendet werden, sobald eine erfolgreiche Aktion abgeschlossen ist.
- `WARNING`: Nützlich, um anzuzeigen, dass ein Benutzer eine potenziell riskante Aktion ausführen will, z. B. einer Seite mit nicht gespeicherten Änderungen zu entkommen. Diese Aktionen sind oft weniger wirkungsvoll als solche, die das Danger-Thema verwenden.
- `GRAY`: Gut für subtile Aktionen, wie geringfügige Einstellungen oder Aktionen, die eher ergänzend zu einer Seite sind und nicht Teil der Hauptfunktionalität.
- `INFO`: Gut für die Bereitstellung zusätzlicher klärender Informationen für einen Benutzer.

<TableBuilder name="Icon" />
