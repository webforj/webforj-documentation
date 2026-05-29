---
title: Badge
sidebar_position: 8
_i18n_hash: 1f599f2c8a833e09f2d945ed0ead5447
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-badge" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="badge" location="com/webforj/component/badge/Badge" top='true'/>

Ein `Badge` ist ein kompaktes, visuell markantes Label, das verwendet wird, um Status, Zähler oder kurze kontextuelle Informationen zu vermitteln. Egal, ob Sie einen Benachrichtigungszähler kennzeichnen, ein Element als "Neu" markieren oder auf eine Warnung aufmerksam machen möchten, Badges bieten Ihnen eine leichte Möglichkeit, diese Informationen direkt in der Benutzeroberfläche anzuzeigen.

<!-- INTRO_END -->

:::tip Verwendung eines `Badge`
Badges eignen sich gut für Benachrichtigungszähler, Statuslabels und kurze Metadaten wie Versionsbezeichnungen oder Veröffentlichungszustände. Halten Sie den Badge-Text auf ein oder zwei Wörter, damit das Label auf einen Blick lesbar ist.
:::

## Erstellen eines Badges {#creating-a-badge}

Das einfachste `Badge` nimmt eine Textzeichenfolge entgegen. Sie können auch ein `BadgeTheme` direkt im Konstruktor übergeben, um den visuellen Stil sofort festzulegen. Der Konstruktor ohne Argumente ist verfügbar, wenn Sie ein Badge dynamisch erstellen und es nach der Erstellung konfigurieren müssen.

```java
Badge badge = new Badge("Neu");

// Mit einem Thema
Badge primary = new Badge("Hervorgehoben", BadgeTheme.SUCCESS);

// Dynamisch erstellt
Badge status = new Badge();
status.setLabel("Ausstehend");
status.setTheme(BadgeTheme.WARNING);
```

## Label {#label}

Sie können den Textinhalt eines Badges jederzeit mit `setLabel()` festlegen oder aktualisieren. Die Methode `setText()` ist ein Alias für denselben Vorgang; verwenden Sie die, die im Kontext natürlicher wirkt. Beide haben entsprechende Getter, `getLabel()` und `getText()`, wenn Sie den aktuellen Wert zurücklesen müssen.

```java
Badge badge = new Badge();
badge.setLabel("Aktualisiert");

// Entsprechend
badge.setText("Aktualisiert");

// Wert zurücklesen
String current = badge.getLabel();
```

## Icons {#icons}

Manchmal ist ein visuellerer Ansatz nützlich, um Informationen mit einem `Badge` zu vermitteln. Badges unterstützen slot-in Icon-Inhalte. Übergeben Sie ein Icon zusammen mit dem Text unter Verwendung des `Badge(String, Component...)`-Konstruktors oder übergeben Sie nur ein Icon, um ein ikon-only Badge zu erstellen. In Kombination mit Text wird das Icon links vom Label angezeigt.

Icon-only Badges eignen sich besonders gut für kompakte Statusanzeigen in dichten Layouts, in denen ein kurzes Wort überladen wirken würde. Die Kombination eines Icons mit Text ist ein guter Mittelweg, wenn das Icon allein möglicherweise mehrdeutig ist. Ein Statussymbol wird für sich genommen allgemein verstanden, aber das Hinzufügen eines kurzen Textlabels beseitigt das Raten für Erstbenutzer. Sie können mehrere Komponenten an den Konstruktor übergeben, wenn Sie einen reichhaltigeren Präfix komposieren müssen, obwohl in der Praxis ein einzelnes Icon das häufigste Muster ist.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgeicons'
files={['src/main/java/com/webforj/samples/views/badge/BadgeIconsView.java']}
height='345px'
/>
<!-- vale on -->

```java
// Icon mit Text
Badge done = new Badge("Erledigt", FeatherIcon.CHECK_CIRCLE.create());
done.setTheme(BadgeTheme.SUCCESS);

// Nur Icon
Badge check = new Badge(FeatherIcon.CHECK.create());
check.setTheme(BadgeTheme.SUCCESS);
```

## Verwendung in anderen Komponenten {#usage-in-other-components}

### Schaltflächen {#buttons}

Hängen Sie ein `Badge` an eine `Button` mit `setBadge()`. Das Badge erscheint in der oberen rechten Ecke der Schaltfläche und überlappt die Kante der Schaltfläche. Dies ist ein gängiges Muster für Benachrichtigungszähler bei Toolbar-Aktionen oder Iconschaltflächen. Da das Badge eine eigenständige Komponente ist, ist es vollständig unabhängig vom eigenen Thema und der Größe der Schaltfläche. Sie können eine primäre Schaltfläche mit einem Gefahren-Badge oder eine Geister-Schaltfläche mit einem Erfolg-Badge kombinieren, und jede Seite der Kombination gestaltet sich ohne Konflikte selbst. Die Aktualisierung der Zähler ist so einfach wie das Aufrufen von `badge.setLabel()` mit einem neuen Wert; die Schaltfläche muss nicht berührt werden.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgebuttons'
files={['src/main/java/com/webforj/samples/views/badge/BadgeButtonsView.java']}
height='290px'
/>
<!-- vale on -->

### Tabbed Pane {#tabbed-pane}

Fügen Sie ein `Badge` als Suffix zu einem `Tab` mit `setSuffixComponent()` hinzu. Dies passt gut zu Zählern im Inbox-Stil oder Statusanzeigen auf jedem Tab. Es ist das Muster, das Sie in E-Mail-Clients oder Aufgabenmanagern sehen, wo es wichtig ist, Aktivitäten auf jedem Abschnitt auf einen Blick zu signalisieren. Das Badge sitzt am hinteren Ende des Tab-Labels, nach jedem Präfixinhalt, und bleibt sichtbar, unabhängig davon, welcher Tab gerade aktiv ist. Diese Persistenz ist Absicht: Das Verstecken des Badges auf inaktiven Tabs würde es schwieriger machen zu wissen, welche Abschnitte Aufmerksamkeit benötigen, ohne zu jedem zu wechseln.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgetabbedpane'
files={['src/main/java/com/webforj/samples/views/badge/BadgeTabbedPaneView.java']}
height='360px'
/>
<!-- vale on -->

## Styling {#styling}

Badges unterstützen mehrere Styling-Dimensionen: Themenfarben zur Übermittlung von Bedeutungen, eine Expansionsskala zur Steuerung der Größe und CSS-Eigenschaften für eine feinkörnige Anpassung.

### Themen {#themes}

Wie bei vielen Komponenten in webforJ gibt es das `Badge` in vierzehn Themen: sieben gefüllte und sieben umrissene Varianten.

Gefüllte Themen verwenden einen soliden Hintergrund und berechnen automatisch eine Textfarbe, die den Kontrastanforderungen entspricht. Umrissene Varianten verwenden stattdessen einen getönten Hintergrund mit einem farbigen Rand und bieten eine subtilere Option, wenn Sie möchten, dass das Badge die umgebenden Inhalte ergänzt, anstatt sie zu dominieren.

Wenden Sie ein Thema mit `setTheme()` oder über den Konstruktor an.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgethemes'
files={['src/main/java/com/webforj/samples/views/badge/BadgeThemesView.java']}
height='260px'
/>
<!-- vale on -->

### Benutzerdefinierte Farbe {#custom-color}

Wenn die integrierten Themen nicht zu Ihrer Farbpalette passen, setzen Sie eine benutzerdefinierte Grundfarbe mit der CSS-Eigenschaft `--dwc-badge-seed`. Aus diesem einzelnen Wert leitet das Badge automatisch die Hintergrund-, Text- und Randfarben ab, sodass jede Kombination lesbar bleibt, ohne dass Sie jede individuell festlegen müssen. Das bedeutet, dass Sie ein Badge in jeder Farbe Ihres Designs mit Sicherheit gestalten können. Farbton, Sättigung und Helligkeit (HSL)-Werte sind hier besonders praktisch; das Wechseln des Farbtons allein reicht aus, um eine völlig andere Farbpalette zu erzeugen, während der Kontrast intakt bleibt.

```java
Badge badge = new Badge("Benutzerdefiniert");
badge.setStyle("--dwc-badge-seed", "hsl(262, 52%, 47%)");
```

### Größen {#sizing}

Verwenden Sie `setExpanse()`, um die Badge-Größe zu steuern. Neun Größen sind verfügbar, die von `XXXSMALL` bis `XXXLARGE` reichen, und die Standardgröße ist `SMALL`.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgesizes'
files={['src/main/java/com/webforj/samples/views/badge/BadgeSizesView.java']}
height='300px'
/>
<!-- vale on -->

### Teile und CSS-Variablen {#parts-and-css-variables}

<TableBuilder name="Badge" />
