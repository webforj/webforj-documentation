---
title: Badge
sidebar_position: 8
sidebar_class_name: new-content
_i18n_hash: 112f61dea5c6c0d434267a25ccc61b9e
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-badge" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="badge" location="com/webforj/component/badge/Badge" top='true'/>

Ein `Badge` ist ein kompaktes, visuell unterscheidbares Etikett, das verwendet wird, um Status, Zählungen oder kurze kontextuelle Informationen zu vermitteln. Ob Sie eine Benachrichtigungsanzahl kennzeichnen, ein Element als "Neu" markieren oder auf eine Warnung aufmerksam machen müssen, Badges bieten Ihnen eine leichte Möglichkeit, diese Informationen direkt in der Benutzeroberfläche anzuzeigen.

<!-- INTRO_END -->

:::tip Verwendung eines `Badge`
Badges eignen sich gut für Benachrichtigungszählen, Statusetiketten und kurze Metadaten wie Versionsetiketten oder Veröffentlichungszustände. Halten Sie den Badge-Text auf ein oder zwei Wörter beschränkt, damit das Etikett auf einen Blick lesbar ist.
:::

## Erstellen eines Badges {#creating-a-badge}

Das einfachste `Badge` nimmt einen Textstring an. Sie können auch ein `BadgeTheme` direkt im Konstruktor übergeben, um den visuellen Stil sofort festzulegen. Der Konstruktor ohne Argumente ist verfügbar, wenn Sie ein Badge dynamisch erstellen und es nach der Erstellung konfigurieren müssen.

```java
Badge badge = new Badge("Neu");

// Mit einem Thema
Badge primary = new Badge("Hervorgehoben", BadgeTheme.SUCCESS);

// Dynamisch erstellt
Badge status = new Badge();
status.setLabel("Ausstehend");
status.setTheme(BadgeTheme.WARNING);
```

## Etikett {#label}

Sie können den Textinhalt eines Badges jederzeit mit `setLabel()` festlegen oder aktualisieren. Die Methode `setText()` ist ein Alias für dieselbe Operation; verwenden Sie diejenige, die im Kontext natürlicher klingt. Beide verfügen über entsprechende Getter, `getLabel()` und `getText()`, falls Sie den aktuellen Wert zurücklesen möchten.

```java
Badge badge = new Badge();
badge.setLabel("Aktualisiert");

// Entspricht
badge.setText("Aktualisiert");

// Wert zurücklesen
String current = badge.getLabel();
```

## Icons {#icons}

Manchmal ist ein visuellerer Ansatz nützlich, um Informationen mit einem `Badge` zu vermitteln. Badges unterstützen den Einsatz von Icons. Übergeben Sie ein Icon zusammen mit Text, indem Sie den Konstruktor `Badge(String, Component...)` verwenden, oder übergeben Sie ein Icon allein, um ein nur aus Icon bestehendes Badge zu erstellen. Wenn Sie ein Icon mit Text kombinieren, wird das Icon links vom Etikett dargestellt.

Nur-Icon-Badges eignen sich besonders gut als kompakte Statusindikatoren in dichten Layouts, bei denen ein kurzes Wort überladen wirken würde. Die Kombination von Icon und Text ist ein guter Mittelweg, wenn das Icon allein mehrdeutig sein könnte. Ein Status-Symbol wird allgemein verstanden, aber die Hinzufügung eines kurzen Textetiketts beseitigt das Rätselraten für Erstbenutzer. Sie können mehrere Komponenten an den Konstruktor übergeben, wenn Sie eine reichhaltigere Präfixkomposition benötigen, obwohl in der Praxis ein einzelnes Icon das gängigste Muster ist.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgeicons?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeIconsView.java'
height='320px'
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

Fügen Sie einem `Button` ein `Badge` hinzu, indem Sie `setBadge()` verwenden. Das Badge erscheint in der oberen rechten Ecke der Schaltfläche und überlappt die Kante der Schaltfläche. Dies ist ein gängiges Muster für Benachrichtigungszahlen bei Toolbar-Aktionen oder Icon-Schaltflächen. Da das Badge eine eigenständige Komponente ist, ist es völlig unabhängig vom Thema und der Größe der Schaltfläche. Sie können eine primäre Schaltfläche mit einem Gefahren-Badge oder eine Geister-Schaltfläche mit einem Erfolgs-Badge kombinieren, und jede Seite der Kombination stylisiert sich selbst ohne Konflikte. Die spätere Aktualisierung der Anzahl ist so einfach wie das Aufrufen von `badge.setLabel()` mit einem neuen Wert; die Schaltfläche muss nicht angepasst werden.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgebuttons?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeButtonsView.java'
height='250px'
/>
<!-- vale on -->

### Registerkartenpaneel {#tabbed-pane}

Fügen Sie einem `Tab` ein `Badge` als Suffix hinzu, indem Sie `setSuffixComponent()` verwenden. Dies passt gut für Zählungen oder Statusindikatoren im Stil von Posteingängen auf jeder Registerkarte. Es handelt sich um ein Muster, das Sie bei E-Mail-Clients oder Aufgabenmanagern sehen, bei denen es wichtig ist, Aktivitäten auf jedem Abschnitt auf einen Blick anzuzeigen. Das Badge befindet sich am hinteren Ende des Registerkartenetiketts, nach beliebigem Präfixinhalt, und bleibt sichtbar, unabhängig davon, welche Registerkarte gerade aktiv ist. Diese Persistenz ist beabsichtigt: Das Verstecken des Badges auf inaktiven Registerkarten würde es schwieriger machen zu wissen, welche Abschnitte Aufmerksamkeit benötigen, ohne zu jedem einzelnen zu wechseln.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgetabbedpane?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeTabbedPaneView.java'
height='325px'
/>
<!-- vale on -->

## Styling {#styling}

Badges unterstützen mehrere Styling-Dimensionen: Themenfarben zur Übermittlung von Bedeutungen, eine Expansionsskala zur Steuerung der Größe und CSS-Eigenschaften für feine Anpassungen.

### Themen {#themes}

Wie bei vielen Komponenten in webforJ kommt das `Badge` in vierzehn Themen: sieben gefüllten und sieben umrandeten Varianten.

Gefüllte Themen verwenden einen soliden Hintergrund und berechnen automatisch eine Textfarbe, die den Kontrastanforderungen entspricht. Umrandete Varianten verwenden stattdessen einen gefärbten Hintergrund mit einem farbigen Rand und stellen eine subtilere Option dar, wenn das Badge die umgebenden Inhalte ergänzen soll, anstatt sie zu dominieren.

Wenden Sie ein Thema mit `setTheme()` oder über den Konstruktor an.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgethemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeThemesView.java'
height='260px'
/>
<!-- vale on -->

### Benutzerdefinierte Farbe {#custom-color}

Wenn die integrierten Themen nicht zu Ihrem Farbschema passen, legen Sie eine benutzerdefinierte Grundfarbe mit der CSS-Eigenschaft `--dwc-badge-seed` fest. Aus diesem einzelnen Wert leitet das Badge automatisch die Farben für den Hintergrund, den Text und den Rand ab, sodass jede Kombination lesbar bleibt, ohne dass Sie jede einzeln festlegen müssen. Das bedeutet, dass Sie ein Badge in jeder Farbe Ihres Designsystems mit Zuversicht gestalten können. Farbton-, Sättigungs- und Helligkeitswerte (HSL) sind hierbei besonders praktisch; das einfache Austauschen des Farbtons reicht aus, um eine völlig andere Farbfamilie zu erzeugen und dabei den Kontrast intakt zu halten.

```java
Badge badge = new Badge("Benutzerdefiniert");
badge.setStyle("--dwc-badge-seed", "hsl(262, 52%, 47%)");
```

### Größen {#sizing}

Verwenden Sie `setExpanse()`, um die Größe des Badges zu steuern. Neun Größen sind verfügbar, von `XXXSMALL` bis `XXXLARGE`, und die Standardgröße ist `SMALL`.

<!-- vale off -->
<ComponentDemo
path='/webforj/badgesizes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/badge/BadgeSizesView.java'
height='260px'
/>
<!-- vale on -->

### Teile und CSS-Variablen {#parts-and-css-variables}

<TableBuilder name="Badge" />
