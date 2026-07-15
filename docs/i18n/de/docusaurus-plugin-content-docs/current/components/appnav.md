---
title: AppNav
sidebar_position: 6
sidebar_class_name: new-content
description: >-
  Build hierarchical side navigation menus with AppNav and AppNavItem, linking
  to routes, registered views, or external URLs.
_i18n_hash: 7283cd36346dd18b131a5393db8e8fd3
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/>

Die `AppNav`-Komponente erstellt ein seitliches Navigationsmenü aus `AppNavItem`-Einträgen. Elemente können auf interne Ansichten oder externe Ressourcen verlinken, unter übergeordneten Elementen für hierarchische Menüs geschachtelt werden und Icons, Abzeichen oder andere Komponenten mitführen, um den Benutzern auf einen Blick mehr Kontext zu geben.

<!-- INTRO_END -->

## Hinzufügen und Verschachteln von Elementen {#adding-and-nesting-items}

`AppNavItem`-Instanzen werden verwendet, um die `AppNav`-Struktur zu füllen. Diese Elemente können einfache Links oder Gruppenüberschriften sein, die untergeordnete Elemente enthalten. Gruppenüberschriften ohne Links fungieren als erweiterbare Container.

Verwenden Sie `addItem()`, um Elemente in die Navigation einzufügen:

```java
AppNavItem dashboard = new AppNavItem("Dashboard", "/dashboard");
AppNavItem admin = new AppNavItem("Admin");
admin.addItem(new AppNavItem("Users", "/admin/users"));
admin.addItem(new AppNavItem("Settings", "/admin/settings"));

AppNav nav = new AppNav();
nav.addItem(dashboard);
nav.addItem(admin);
```

:::tip Gruppenartikel verlinken
Elemente der obersten Ebene in einem Navigationsbaum sind in der Regel nicht zum Klicken gedacht, sondern zum Erweitern. Das Setzen eines `path` auf solchen Elementen kann Benutzer verwirren, die erwarten, dass sie untergeordnete Elemente anzeigen, anstatt woanders zu navigieren.

Wenn Sie wünschen, dass die Gruppenüberschrift eine benutzerdefinierte Aktion (wie das Öffnen externer Dokumente) auslöst, lassen Sie den Gruppenpfad leer und fügen Sie stattdessen ein interaktives Steuerelement wie einen [`IconButton`](./icon#icon-buttons) zum Suffix des Elements hinzu. Dies hält die Benutzererfahrung konsistent und klar.
:::

<!--vale off-->
<ComponentDemo
path='/webforj/appnav/Social'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavPageView.java',
]}
/>
<!--vale on-->

## Elemente verlinken {#linking-items}

Jedes `AppNavItem` kann zu einer internen Ansicht oder einem externen Link navigieren. Sie können dies mithilfe von statischen Pfaden oder registrierten Sichtklassen definieren.

### Statische Pfade {#static-paths}

Verwenden Sie Zeichenfolgenpfade, um Links direkt zu definieren:

```java
AppNavItem docs = new AppNavItem("Docs", "/docs");
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
```

### Registrierte Ansichten {#registered-views}

Wenn Ihre Ansichten beim [Router](../routing/overview) registriert sind, können Sie anstelle einer fest codierten URL die Klasse übergeben:

```java
AppNavItem settings = new AppNavItem("Settings", SettingsView.class);
```

Wenn Ihre annotierte Route [Routenparameter](../routing/route-patterns#named-parameters) unterstützt, können Sie auch eine `ParametersBag` übergeben:

```java
ParametersBag params = ParametersBag.of("id=123");
AppNavItem advanced = new AppNavItem("User", UserView.class, params);
```

### Mit Abfrageparametern {#with-query-parameters}

Übergeben Sie eine `ParametersBag`, um Abfragezeichenfolgen einzuschließen:

```java
ParametersBag params = ParametersBag.of("param1=value1&param2=value2");
AppNavItem advanced = new AppNavItem("Advanced", SettingsView.class, params);
advanced.setQueryParameters(params);
```

## Zielverhalten {#target-behavior}

Steuern Sie, wie Links geöffnet werden, mit `setTarget()`. Dies ist besonders nützlich für externe Links oder Pop-out-Ansichten.

- **`SELF`** (Standard): Wird in der aktuellen Ansicht geöffnet.
- **`BLANK`**: Wird in einem neuen Tab oder Fenster geöffnet.
- **`PARENT`**: Wird im übergeordneten Browsing-Kontext geöffnet.
- **`TOP`**: Wird im übergeordneten Browsing-Kontext der obersten Ebene geöffnet.

```java
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Präfix und Suffix {#prefix-and-suffix}

`AppNavItem` unterstützt Präfix- und Suffixkomponenten. Verwenden Sie diese, um visuelle Klarheit mit Icons, Abzeichen oder Buttons zu bieten.

- **Präfix**: erscheint vor dem Label, nützlich für Icons.
- **Suffix**: erscheint nach dem Label, großartig für Abzeichen oder Aktionen.

```java
AppNavItem notifications = new AppNavItem("Alerts");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Automatisches Öffnen von Gruppen {#auto-opening-groups}

Verwenden Sie `setAutoOpen(true)` an der `AppNav`-Komponente, um geschachtelte Gruppen automatisch zu erweitern, wenn die App aktualisiert wird.

```java
nav.setAutoOpen(true);
```

## Anheften <DocChip chip='since' label='26.01' /> {#pinning}

Anheften ermöglicht es einem Benutzer, die Elemente, die er am häufigsten erreicht, in eine Gruppe oben in der Navigation zu heben, sodass ein tiefes Menü dennoch eine kurze Liste von Favoriten innerhalb eines Klicks behält. Es ist standardmäßig deaktiviert. Aktivieren Sie es über die Anheftkonfiguration:

```java
AppNav nav = new AppNav();
nav.getPinning().setEnabled(true);
```

Sobald es aktiviert ist, zeigt jedes navigierbare Blattobjekt einen Anheft-Umschalter. Der Umschalter wird beim Hover und beim Tastaturfokus angezeigt, sodass er ohne Maus erreichbar bleibt. Durch Aktivieren wird das Element in die angeheftete Gruppe oben in der Navigation verschoben.

Einige Regeln bestimmen, was angeheftet werden kann und wie sich die Gruppe verhält:

- Nur navigierbare Blattobjekte sind anheftbar. Gruppenüberschriften (Elemente mit Kindern) sind niemals anheftbar.
- Die angeheftete Gruppe erscheint nur, wenn etwas angeheftet ist, und verschwindet wieder, wenn das letzte Element losgelöst wird.
- Das Loslösen bringt ein Element an seine genaue ursprüngliche Position zurück, einschließlich Elemente, die sich mehrere Ebenen tief innerhalb von Gruppen befinden.
- Das Element wird verschoben, nicht kopiert, sodass alle Präfix- oder Suffixinhalte und alle daran angehängten Listener weiterhin funktionieren, während es sich in der angehefteten Gruppe befindet.

Die folgende Demo hat das Anheften aktiviert mit einem benutzerdefinierten Gruppentitel und Dashboard, das beim Laden angeheftet ist. Bewegen Sie den Mauszeiger über oder fokussieren Sie ein Blattobjekt, um dessen Anheft-Umschalter zu zeigen.

<ComponentDemo
path='/webforj/appnavpinning/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningPageView.java',
]}
/>

### Ein Element standardmäßig angeheftet starten {#starting-an-item-pinned}

Starten Sie ein Element in der angehefteten Gruppe, indem Sie seinen angehefteten Zustand festlegen. Verwenden Sie `isPinned()`, um den aktuellen Zustand zu lesen.

```java
AppNavItem reports = new AppNavItem("Reports", "/reports");
reports.setPinned(true);
```

:::info Anheften muss aktiviert sein
`setPinned(true)` hat nur dann Wirkung, wenn Anheften an der `AppNav` über `getPinning().setEnabled(true)` aktiviert ist. Ohne dies hat der Aufruf keine Wirkung.
:::

### Titel der angehefteten Gruppe {#pinned-group-title}

Die angeheftete Gruppe wird standardmäßig mit `Angeschnallt` bezeichnet. Ändern Sie es, um zu Ihrem App zu passen:

```java
nav.getPinning().setTitle("Favoriten");
```

### Anheftschlüssel {#pin-keys}

Jedes anheftbare Element trägt einen Schlüssel, der es zur Persistenz und für das [Anheftereignis](#reacting-to-pin-changes) identifiziert. Wenn Sie keinen festlegen, fällt der Schlüssel auf den Pfad des Elements zurück, sodass `getPinKey()` immer einen verwendbaren Wert zurückgibt.

```java
AppNavItem reports = new AppNavItem("Reports", "/reports");
reports.setPinKey("reports");
```

Setzen Sie einen expliziten Schlüssel, wenn der Pfad zur Laufzeit ändern kann. Ein stabiler Schlüssel hält einen Pin mit dem richtigen Element über Neuladevorgänge hinweg maschen, selbst wenn sich die URL verschiebt.

### Automatische Speicherung im lokalen Speicher {#autosave}

Pins leben nur für die aktuelle Seitenansicht, es sei denn, Sie persistieren sie. Automatische Speicherung ist die einfachste Option: sie speichert die Menge an angehefteten Elementen im lokalen Speicher des Browsers und stellt sie beim Neuladen wieder her. Sie ist standardmäßig deaktiviert. Sie benötigt eine stabile `id` (oder einen Namen) für die Komponente für den Speicher-Schlüssel, und der `AppNav(String id)`-Konstruktor ist der praktische Weg, einen festzulegen:

```java
AppNav nav = new AppNav("main-nav"); // gibt der automatischen Speicherung einen stabilen Speicher-Schlüssel
nav.getPinning().setAutosave(true);
```

:::info Automatische Speicherung benötigt eine id
Ohne `id` (oder Namen) an der Komponente tut die automatische Speicherung heimlich nichts, da es keinen stabilen Schlüssel zum Speichern gibt. Die Persistenz erfolgt pro Browser, sodass Pins den Benutzer nicht zu einem anderen Gerät oder Browser begleiten.
:::

### Benutzerdefinierte Persistenz {#custom-persistence}

Für Persistenz, die Sie steuern, zum Beispiel pro Benutzer auf dem Server, deaktivieren Sie die automatische Speicherung und steuern Sie es selbst über das [Anheftereignis](#reacting-to-pin-changes) und `setPinned`:

```java
nav.getPinning().setAutosave(false);

// speichern Sie die aktuelle Menge der angehefteten Schlüssel, wann immer sie geändert wird
nav.onPin(event -> savePins(event.getKeys()));

// beim Laden, stellen Sie jeden gespeicherten Schlüssel wieder her
restoredKeys.forEach(key -> findItem(key).setPinned(true));
```

### Auf Anheftänderungen reagieren {#reacting-to-pin-changes}

Das Anheftereignis wird immer dann ausgelöst, wenn ein Element angeheftet oder losgelöst wird. Es enthält das Element, das sich geändert hat, seinen Schlüssel, den neuen angehefteten Zustand und die vollständige geordnete Menge angehefteter Schlüssel:

```java
nav.onPin(event -> {
  AppNavItem item = event.getItem(); // das Element, das sich verändert hat, oder null, wenn es nicht mehr in der Navigation vorhanden ist
  boolean pinned = event.isPinned();
  String key = event.getKey();
  List<String> all = event.getKeys(); // jeder angeheftete Schlüssel, in angehefteter Reihenfolge
});
```

`getItem()` löst das Element auf, indem es seinen Anheftschlüssel abgleicht und gibt `null` zurück, wenn das Element nicht mehr Teil der Navigation ist.

### Anheftsymbole {#pin-icons}

Der Umschalter verwendet das integrierte `dwc:pin`-Symbol, während ein Element nicht angeheftet ist, und `dwc:pinned-off`, während es angeheftet ist. Tauschen Sie Ihr eigenes durch `setUnpinnedIcon` und `setPinnedIcon` aus, die jede `IconDefinition` akzeptieren:

```java
nav.getPinning()
   .setUnpinnedIcon(TablerIcon.create("pin"))
   .setPinnedIcon(TablerIcon.create("pinned-off"));
```

### Anheftumschalter auf Touchscreens {#pin-toggle-on-touchscreens}

Touchscreens haben keinen Hover, um das Pin zu offenbaren, sodass der Umschalter dort standardmäßig verborgen ist. Halten Sie ihn sichtbar und tippbar auf Touchscreens mit `setTouchVisible(true)`:

```java
nav.getPinning().setTouchVisible(true);
```

## Suche <DocChip chip='since' label='26.01' /> {#search}

Das Suchfeld filtert das Menü nach Elementbezeichnung, während der Benutzer tippt. Es ist standardmäßig deaktiviert. Sie können es anzeigen und ihm einen Platzhalter über die Suchkonfiguration geben:

```java
nav.getSearch().setFieldVisible(true);
nav.getSearch().setPlaceholder("Suchen");
```

Während der Benutzer tippt, filtert die Navigation die Elemente nach Bezeichnung, öffnet jede Gruppe, die einen Treffer enthält, und zeigt eine leere Nachricht, wenn nichts übereinstimmt. Angeheftete Verknüpfungen bleiben während der Suche sichtbar, sodass die Favoriten eines Benutzers auch während des Filterns innerhalb eines Klicks erreichbar bleiben.

<ComponentDemo
path='/webforj/appnavsearch/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchPageView.java',
]}
/>

### Leere Nachricht {#search-empty-message}

Legen Sie die Nachricht fest, die angezeigt wird, wenn eine Suche keine Ergebnisse zurückgibt. Klarer Text wird als Text gerendert:

```java
nav.getSearch().setEmptyMessage("Keine Elemente gefunden");
```

### Suche über Ihr eigenes Feld steuern {#custom-search-box}

Blenden Sie das integrierte Feld aus und füttern Sie den Filter von einem eigenen Eingabefeld aus. Drücken Sie den aktuellen Begriff über `setTerm`:

```java
nav.getSearch().setFieldVisible(false);

myField.onModify(event -> nav.getSearch().setTerm(event.getText()));
```

Um auf das zu reagieren, was der Benutzer im integrierten Feld eingibt, hören Sie auf das Suchereignis:

```java
nav.onSearch(event -> log(event.getTerm()));
```

## Stylisierung von `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
