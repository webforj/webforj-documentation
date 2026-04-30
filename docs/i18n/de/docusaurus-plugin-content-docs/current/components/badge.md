---
title: Badge
sidebar_position: 8
sidebar_class_name: new-content
_i18n_hash: 83dfb4c5ec1d554fc78e7e860128fb46
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-badge" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="badge" location="com/webforj/component/badge/Badge" top='true'/>

Ein `Badge` ist ein kompaktes, visuell unterscheidbares Etikett, das verwendet wird, um Status, Zählungen oder kurze Stücke kontextbezogener Informationen zu übermitteln. Egal, ob Sie eine Benachrichtigungsanzahl kennzeichnen, ein Element als "Neu" markieren oder auf eine Warnung aufmerksam machen möchten, Badges bieten Ihnen eine leichte Möglichkeit, diese Informationen direkt in der Benutzeroberfläche anzuzeigen.

<!-- INTRO_END -->

:::tip Verwendung eines `Badge`
Badges eignen sich gut für Benachrichtigungsanzahlen, Statusetiketten und kurze Metadaten wie Versionsmarkierungen oder Veröffentlichungszustände. Halten Sie den Badge-Text auf ein oder zwei Wörter beschränkt, damit das Etikett auf einen Blick lesbar ist.
:::

## Erstellung eines Badges {#creating-a-badge}

Das einfachste `Badge` nimmt einen Textstring. Sie können auch direkt im Konstruktor ein `BadgeTheme` übergeben, um den visuellen Stil sofort festzulegen. Der Konstruktor ohne Argumente ist verfügbar, wenn Sie ein Badge dynamisch erstellen und es nach der Erstellung konfigurieren müssen.

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

Sie können den Textinhalt eines Badges jederzeit mit `setLabel()` setzen oder aktualisieren. Die Methode `setText()` ist ein Alias für denselben Vorgang; verwenden Sie, was im Kontext natürlicher klingt. Beide haben entsprechende Getter, `getLabel()` und `getText()`, falls Sie den aktuellen Wert zurücklesen müssen.

```java
Badge badge = new Badge();
badge.setLabel("Aktualisiert");

// Entsprechend
badge.setText("Aktualisiert");

// Den Wert zurücklesen
String current = badge.getLabel();
```

## Icons {#icons}

Manchmal ist ein visuelleren Ansatz nützlich, um Informationen mit einem `Badge` zu übermitteln. Badges unterstützen eingestellte Icon-Inhalte. Übergeben Sie ein Icon zusammen mit dem Text mit dem Konstruktor `Badge(String, Component...)` oder übergeben Sie nur ein Icon, um ein Icon-Only-Badge zu erstellen. Wenn es mit Text kombiniert wird, wird das Icon links vom Label gerendert.

Icon-only Badges eignen sich besonders gut für kompakte Statusanzeigen in dichten Layouts, wo ein kurzes Wort überladen wirken würde. Die Kombination eines Icons mit Text ist ein guter Mittelweg, wenn das Icon allein mehrdeutig sein könnte. Ein Statusymbol wird alleine weithin verstanden, aber das Hinzufügen eines kurzen Textlabels beseitigt Rätselraten für Erstnutzer. Sie können mehrere Komponenten an den Konstruktor übergeben, wenn Sie einen reichhaltigeren Präfix erstellen müssen, obwohl in der Praxis ein einzelnes Icon das häufigste Muster ist.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgeicons?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeIconsView.java'
height='345px'
/>
<!-- vale on -->

```java
// Icon mit Text
Badge done = new Badge("Fertig", FeatherIcon.CHECK_CIRCLE.create());
done.setTheme(BadgeTheme.SUCCESS);

// Nur Icon
Badge check = new Badge(FeatherIcon.CHECK.create());
check.setTheme(BadgeTheme.SUCCESS);
```

## Verwendung in anderen Komponenten {#usage-in-other-components}

### Schaltflächen {#buttons}

Fügen Sie einem `Button` ein `Badge` mithilfe von `setBadge()` hinzu. Das Badge erscheint in der oberen rechten Ecke des Buttons und überlappt die Kante des Buttons. Dies ist ein gängiges Muster für Benachrichtigungsanzahlen bei Toolbar-Aktionen oder Symbolschaltflächen. Da das Badge eine eigenständige Komponente ist, ist es völlig unabhängig vom Thema und der Größe des Buttons. Sie können eine primäre Schaltfläche mit einem Gefahren-Badge oder eine Geisterschaltfläche mit einem Erfolgsbadge kombinieren, und jede Seite der Kombination stylt sich selbstständig ohne Konflikte. Die Aktualisierung der Zahl später ist so einfach wie das Aufrufen von `badge.setLabel()` mit einem neuen Wert; der Button muss nicht berührt werden.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgebuttons?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeButtonsView.java'
height='290px'
/>
<!-- vale on -->

### Tabbed Pane {#tabbed-pane}

Fügen Sie ein `Badge` als Suffix auf einem `Tab` mit `setSuffixComponent()` hinzu. Dies passt natürlich zu Zählungen oder Statusanzeigen im Stil eines Posteingangs auf jedem Tab. Es ist das Muster, das man bei E-Mail-Clients oder Aufgabenmanagern sieht, wo es wichtig ist, Aktivitäten in jedem Abschnitt auf einen Blick zu signalisieren. Das Badge befindet sich an der hinteren Kante des Tab-Labels, nach allen Präfix-Inhalten, und bleibt sichtbar, unabhängig davon, welcher Tab gerade aktiv ist. Diese Persistenz ist absichtlich: das Verstecken des Badges auf inaktiven Tabs würde es schwieriger machen zu wissen, welche Abschnitte Aufmerksamkeit benötigen, ohne zu jedem zu wechseln.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgetabbedpane?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeTabbedPaneView.java'
height='360px'
/>
<!-- vale on -->

## Styling {#styling}

Badges unterstützen mehrere Stilrichtungen: Themenfarben zur Übermittlung von Bedeutungen, einen Expansionsmaßstab zur Steuerung der Größe und CSS-Eigenschaften für eine feinere Anpassung.

### Themen {#themes}

Wie bei vielen Komponenten in webforJ, kommt das `Badge` in vierzehn Themen: sieben gefüllte und sieben umrandete Varianten.

Gefüllte Themen verwenden einen soliden Hintergrund und berechnen automatisch eine Textfarbe, die den Kontrastanforderungen entspricht. Umrandete Varianten verwenden stattdessen einen getönten Hintergrund mit einem farbigen Rand, was sie zu einer subtileren Option macht, wenn das Badge den umliegenden Inhalten ergänzen und nicht dominieren soll.

Wenden Sie ein Thema mit `setTheme()` oder über den Konstruktor an.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgethemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeThemesView.java'
height='260px'
/>
<!-- vale on -->

### Benutzerdefinierte Farbe {#custom-color}

Wenn die integrierten Themen nicht zu Ihrer Farbpalette passen, setzen Sie eine benutzerdefinierte Seedfarbe mit der CSS-Eigenschaft `--dwc-badge-seed`. Aus diesem einzelnen Wert leitet das Badge automatisch die Hintergrund-, Text- und Randfarben ab, sodass jede Kombination lesbar bleibt, ohne dass Sie jede einzeln angeben müssen. Das bedeutet, dass Sie ein Badge in jeder Farbe Ihres Designs mit Vertrauen gestalten können. Farbton, Sättigung und Helligkeit (HSL)-Werte sind hier besonders praktisch; das alleinige Tauschen des Farbtons reicht aus, um eine völlig andere Farbfamilie zu erzeugen, während der Kontrast intakt bleibt.

```java
Badge badge = new Badge("Benutzerdefiniert");
badge.setStyle("--dwc-badge-seed", "hsl(262, 52%, 47%)");
```

### Größen {#sizing}

Verwenden Sie `setExpanse()`, um die Badge-Größe zu steuern. Neun Größen sind verfügbar, von `XXXSMALL` bis `XXXLARGE`, und die Standardeinstellung ist `SMALL`.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgesizes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeSizesView.java'
height='300px'
/>
<!-- vale on -->

### Teile und CSS-Variablen {#parts-and-css-variables}

<TableBuilder name="Badge" />
