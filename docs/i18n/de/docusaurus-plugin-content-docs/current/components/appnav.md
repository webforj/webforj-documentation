---
title: AppNav
sidebar_position: 6
sidebar_class_name: new-content
description: >-
  Build hierarchical side navigation menus with AppNav and AppNavItem, linking
  to routes, registered views, or external URLs.
_i18n_hash: c4ad786639b189058a38c808bd99ec8b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/layout/appnav/AppNav" top='true'/>

Die Komponente `AppNav` erstellt ein seitliches Navigationsmenü aus `AppNavItem`-Einträgen. Elemente können zu internen Ansichten oder externen Ressourcen verlinken, unter übergeordneten Elementen verschachtelt werden, um hierarchische Menüs zu bilden, und Icons, Abzeichen oder andere Komponenten enthalten, um den Benutzern auf einen Blick mehr Kontext zu geben.

<!-- INTRO_END -->

## Hinzufügen und Verschachteln von Elementen <DocChip chip="name" label="dwc-app-nav-item" /> {#adding-and-nesting-items}

Instanzen von `AppNavItem` werden verwendet, um die Struktur von `AppNav` zu befüllen. Diese Elemente können einfache Links oder verschachtelte Gruppenüberschriften sein, die untergeordnete Elemente enthalten. Gruppenüberschriften ohne Links fungieren als erweiterbare Container.

Verwenden Sie `addItem()`, um Elemente in die Navigation einzufügen:

```java
AppNavItem dashboard = new AppNavItem("Dashboard", "/dashboard");
AppNavItem admin = new AppNavItem("Admin");
admin.addItem(new AppNavItem("Benutzer", "/admin/users"));
admin.addItem(new AppNavItem("Einstellungen", "/admin/settings"));

AppNav nav = new AppNav();
nav.addItem(dashboard);
nav.addItem(admin);
```

:::tip Verlinken von Gruppenelementen
Elemente auf der obersten Ebene eines Navigationsbaums sollen typischerweise erweiterbar und keine anklickbaren Links sein. Das Festlegen eines `path` für solche Elemente kann Benutzer verwirren, die erwarten, dass sie untergeordnete Elemente anzeigen, anstatt woanders zu navigieren.

Wenn Sie möchten, dass die Gruppenüberschrift eine benutzerdefinierte Aktion (wie das Öffnen externer Dokumente) auslöst, lassen Sie den Gruppenpfad leer und fügen Sie stattdessen eine interaktive Steuerung wie einen [`IconButton`](./icon#icon-buttons) zum Suffix des Elements hinzu. Dies hält die Benutzererfahrung konsistent und sauber.
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

## Verlinken von Elementen {#linking-items}

Jedes `AppNavItem` kann zu einer internen Ansicht oder einem externen Link navigieren. Sie können dies mithilfe statischer Pfade oder registrierter Ansichtsklassen definieren.

### Statische Pfade {#static-paths}

Verwenden Sie Zeichenfolgenpfade, um Links direkt zu definieren:

```java
AppNavItem docs = new AppNavItem("Dokumente", "/docs");
AppNavItem help = new AppNavItem("Hilfe", "https://support.example.com");
```

### Registrierte Ansichten {#registered-views}

Wenn Ihre Ansichten mit dem [Router](../routing/overview) registriert sind, können Sie die Klasse anstelle einer fest codierten URL übergeben:

```java
AppNavItem settings = new AppNavItem("Einstellungen", SettingsView.class);
```

Wenn Ihre annotierte Route [Routenparameter](../routing/route-patterns#named-parameters) unterstützt, können Sie auch einen `ParametersBag` übergeben:

```java
ParametersBag params = ParametersBag.of("id=123");
AppNavItem advanced = new AppNavItem("Benutzer", UserView.class, params);
```

### Mit Abfrageparametern {#with-query-parameters}

Übergeben Sie einen `ParametersBag`, um Abfragezeichenfolgen einzuschließen:

```java
ParametersBag params = ParametersBag.of("param1=value1&param2=value2");
AppNavItem advanced = new AppNavItem("Erweitert", SettingsView.class, params);
advanced.setQueryParameters(params);
```

## Zielverhalten {#target-behavior}

Steuern Sie, wie Links geöffnet werden, indem Sie `setTarget()` verwenden. Dies ist besonders nützlich für externe Links oder Pop-out-Ansichten.

- **`SELF`** (Voreinstellung): Öffnet sich in der aktuellen Ansicht.
- **`BLANK`**: Öffnet sich in einem neuen Tab oder Fenster.
- **`PARENT`**: Öffnet sich im übergeordneten Browsing-Kontext.
- **`TOP`**: Öffnet sich im übergeordneten Browsing-Kontext.

```java
AppNavItem help = new AppNavItem("Hilfe", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Präfix und Suffix {#prefix-and-suffix}

`AppNavItem` unterstützt Präfix- und Suffixkomponenten. Verwenden Sie diese, um visuelle Klarheit mit Icons, Abzeichen oder Schaltflächen zu bieten.

- **Präfix**: erscheint vor dem Label, nützlich für Icons.
- **Suffix**: erscheint nach dem Label, ideal für Abzeichen oder Aktionen.

```java
AppNavItem notifications = new AppNavItem("Benachrichtigungen");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Automatisches Öffnen von Gruppen {#auto-opening-groups}

Verwenden Sie `setAutoOpen(true)` auf der `AppNav`-Komponente, um verschachtelte Gruppen automatisch zu erweitern, wenn die App aktualisiert wird.

```java
nav.setAutoOpen(true);
```

## Abschnittsüberschriften <DocChip chip='since' label='26.02' /> <DocChip chip="name" label="dwc-app-nav-label" /> {#section-labels}

`AppNavLabel` ist eine nicht interaktive Überschrift, die eine Gruppe von Elementen betitelt. Eine Überschrift gilt für jedes nachfolgende Element, bis zur nächsten Überschrift oder dem Ende des Menüs, sodass eine lange Liste oberster Elemente als einige benannte Gruppen gelesen werden kann, ohne sie zu schachteln.

Überschriften werden mit `add()` anstelle von `addItem()` hinzugefügt, und die Reihenfolge der Aufrufe definiert die Abschnitte:

```java
AppNav nav = new AppNav();
nav.addItem(new AppNavItem("Dashboard", DashboardView.class, TablerIcon.create("layout-dashboard")));

nav.add(new AppNavLabel("Analytik"));
nav.addItem(new AppNavItem("Übersicht", OverviewView.class));
nav.addItem(new AppNavItem("Berichte", ReportsView.class));

nav.add(new AppNavLabel("Sonstiges"));
nav.addItem(new AppNavItem("Einstellungen", SettingsView.class));
```

Die Navigation blendet eine Überschrift automatisch aus, wenn ihr Abschnitt keine sichtbaren Elemente hat, sodass eine Überschrift verschwindet, wenn eine [Suche](#search) ihre Elemente herausfiltert oder wenn alle von ihnen [oben angedockt](#pinning) sind.

### Überschrift Präfix und Suffix {#label-prefix-and-suffix}

Wie `AppNavItem` unterstützt eine Überschrift Präfix- und Suffixkomponenten. Übergeben Sie ein Präfix an den Konstruktor oder setzen Sie eines von beiden danach:

```java
AppNavLabel analytics = new AppNavLabel("Analytik", TablerIcon.create("chart-pie"));
analytics.setSuffixComponent(new Badge().setText("2").setTheme(BadgeTheme.WARNING));

nav.add(analytics);
```

Das folgende Beispiel gruppiert ein Menü unter drei Überschriften, von denen die erste ein [`Icon`](./icon) Präfix und ein [`Badge`](./badge) Suffix trägt. Dashboard steht über der ersten Überschrift, sodass es zu keinem Abschnitt gehört.

<ComponentDemo
path='/webforj/appnavlabel/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavLabelView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavLabelPageView.java',
]}
/>

## Anheften <DocChip chip='since' label='26.01' /> {#pinning}

Das Anheften ermöglicht es einem Benutzer, die Elemente, nach denen er am häufigsten greift, in einer Gruppe oben in der Navigation anzuheben, sodass ein tiefes Menü eine kurze Liste von Favoriten mit nur einem Klick beibehält. Es ist standardmäßig deaktiviert. Aktivieren Sie es über die Anheftkonfiguration:

```java
AppNav nav = new AppNav();
nav.getPinning().setEnabled(true);
```

Sobald es aktiviert ist, zeigt jedes navigierbare Blattelement einen Pin-Toggle. Der Toggle wird bei Hover und beim Tastaturfokus angezeigt, sodass er ohne Maus erreichbar bleibt. Durch Aktivieren wird das Element in die angeheftete Gruppe oben in der Navigation verschoben.

Einige Regeln regeln, was angeheftet werden kann und wie sich die Gruppe verhält:

- Nur navigierbare Blattelemente sind anheftbar. Gruppenüberschriften (Elemente mit Kindern) sind niemals anheftbar.
- Die angeheftete Gruppe erscheint nur, wenn etwas angeheftet ist, und verschwindet wieder, wenn das letzte Element unangeheftet wird.
- Das Aufheben des Anheftens gibt ein Element an seine genaue ursprüngliche Position zurück, einschließlich Elemente, die mehrere Ebenen tief in Gruppen verschachtelt sind.
- Das Element wird verschoben, nicht kopiert, sodass alle Präfix- oder Suffixinhalte und alle daran angekoppelten Listener weiterhin funktionieren, während es in der angehefteten Gruppe sitzt.

Die folgende Demo hat das Anheften aktiviert, mit einem benutzerdefinierten Gruppennamen, und Dashboard wird beim Laden angeheftet. Hover oder fokussieren Sie ein Blattelement, um den Pin-Toggle anzuzeigen.

<ComponentDemo
path='/webforj/appnavpinning/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningPageView.java',
]}
/>

### Ein Element mit Anheften starten {#starting-an-item-pinned}

Starten Sie ein Element in der angehefteten Gruppe, indem Sie seinen Anheftstatus festlegen. Verwenden Sie `isPinned()`, um den aktuellen Status zu lesen.

```java
AppNavItem reports = new AppNavItem("Berichte", "/reports");
reports.setPinned(true);
```

:::info Anheften muss aktiviert sein
`setPinned(true)` hat nur dann Wirkung, wenn das Anheften auf der `AppNav` über `getPinning().setEnabled(true)` aktiviert ist. Andernfalls hat der Aufruf keine Auswirkungen.
:::

### Titel der angehefteten Gruppe {#pinned-group-title}

Die angeheftete Gruppe wird standardmäßig als `Angenagelt` bezeichnet. Ändern Sie sie, um zu Ihrer App zu passen:

```java
nav.getPinning().setTitle("Favoriten");
```

### Pin-Tasten {#pin-keys}

Jedes anheftbare Element trägt einen Schlüssel, der es für die Persistenz und für das [Pin-Event](#reacting-to-pin-changes) identifiziert. Wenn Sie keinen festlegen, fällt der Schlüssel auf den Pfad des Elements zurück, sodass `getPinKey()` immer einen verwendbaren Wert zurückgibt.

```java
AppNavItem reports = new AppNavItem("Berichte", "/reports");
reports.setPinKey("reports");
```

Setzen Sie einen expliziten Schlüssel, wenn der Pfad zur Laufzeit geändert werden kann. Ein stabiler Schlüssel hält einen Pin an das richtige Element über Reloads hinweg, selbst wenn sich die URL verschiebt.

### Automatisches Speichern im lokalen Speicher {#autosave}

Pins leben nur für die aktuelle Seitenansicht, es sei denn, Sie persistieren sie. Automatisches Speichern ist die einfachste Option: Es speichert die Menge der angehefteten Elemente im lokalen Speicher des Browsers und stellt sie beim Reload wieder her. Es ist standardmäßig deaktiviert. Es benötigt eine stabile `id` (oder einen Namen) für die Komponente für den Speicher-Schlüssel, und der Konstruktor `AppNav(String id)` ist der bequeme Weg, um einen festzulegen:

```java
AppNav nav = new AppNav("main-nav"); // gibt dem automatischen Speichern einen stabilen Speicher-Schlüssel
nav.getPinning().setAutosave(true);
```

:::info Automatisches Speichern benötigt eine Id
Ohne `id` (oder Namen) an der Komponente tut das automatische Speichern still nichts, da ihm kein stabiler Schlüssel zum Speichern vorliegt. Die Persistenz erfolgt pro Browser, sodass Pins einem Benutzer nicht auf ein anderes Gerät oder einen anderen Browser folgen.
:::

### Benutzerdefinierte Persistenz {#custom-persistence}

Für Persistenz, die Sie selbst steuern, z. B. pro Benutzer auf dem Server, deaktivieren Sie das automatische Speichern und steuern Sie es selbst über das [Pin-Event](#reacting-to-pin-changes) und `setPinned`:

```java
nav.getPinning().setAutosave(false);

// speichern Sie die aktuelle Menge an angehefteten Schlüsseln, wann immer sie sich ändern
nav.onPin(event -> savePins(event.getKeys()));

// beim Laden jeder gespeicherten Schlüssel wiederherstellen
restoredKeys.forEach(key -> findItem(key).setPinned(true));
```

### Reagieren auf Pin-Änderungen {#reacting-to-pin-changes}

Das Pin-Event wird jedes Mal ausgelöst, wenn ein Element angeheftet oder unangeheftet wird. Es trägt das Element, das sich geändert hat, seinen Schlüssel, den neuen angehefteten Zustand und die vollständige geordnete Menge der angehefteten Schlüssel:

```java
nav.onPin(event -> {
  AppNavItem item = event.getItem(); // das Element, das sich geändert hat, oder null, wenn es nicht mehr in der Navigation ist
  boolean pinned = event.isPinned();
  String key = event.getKey();
  List<String> all = event.getKeys(); // jeder angeheftete Schlüssel, in angehefteter Reihenfolge
});
```

`getItem()` löst das Element auf, indem es seinen Pin-Schlüssel abgleicht, und gibt `null` zurück, wenn das Element nicht mehr Teil der Navigation ist.

### Pin-Icons {#pin-icons}

Der Toggle verwendet das integrierte Icon `dwc:pin`, während ein Element unangeheftet ist, und `dwc:pinned-off`, während es angeheftet ist. Tauschen Sie Ihre eigenen durch `setUnpinnedIcon` und `setPinnedIcon` aus, die jede `IconDefinition` akzeptieren:

```java
nav.getPinning()
   .setUnpinnedIcon(TablerIcon.create("pin"))
   .setPinnedIcon(TablerIcon.create("pinned-off"));
```

### Pin-Toggle auf Touchscreens {#pin-toggle-on-touchscreens}

Touchscreens haben kein Hover, um den Pin anzuzeigen, sodass der Toggle dort standardmäßig verborgen ist. Halten Sie ihn sichtbar und antippbar auf Touchscreens mithilfe von `setTouchVisible(true)`:

```java
nav.getPinning().setTouchVisible(true);
```

## Suche <DocChip chip='since' label='26.01' /> {#search}

Das Suchfeld filtert das Menü nach Elementbeschriftung, während der Benutzer tippt. Es ist standardmäßig deaktiviert. Sie können es anzeigen und ihm einen Platzhalter über die Suchkonfiguration geben:

```java
nav.getSearch().setFieldVisible(true);
nav.getSearch().setPlaceholder("Suche");
```

Während der Benutzer tippt, filtert die Navigation Elemente nach Beschriftung, öffnet jede Gruppe, die ein Treffer enthält, und zeigt eine leere Nachricht an, wenn nichts übereinstimmt. Angehängte Verknüpfungen bleiben während der Suche sichtbar, sodass die Favoriten eines Benutzers selbst mittendrin weiterhin mit einem Klick erreichbar sind.

<ComponentDemo
path='/webforj/appnavsearch/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchPageView.java',
]}
/>

### Leere Nachricht {#search-empty-message}

Legen Sie die Nachricht fest, die angezeigt wird, wenn eine Suche keine Ergebnisse zurückgibt. Einfacher Text wird als Text gerendert:

```java
nav.getSearch().setEmptyMessage("Keine Elemente gefunden");
```

### Suche von Ihrem eigenen Feld steuern {#custom-search-box}

Blenden Sie das integrierte Feld aus und füttern Sie den Filter von einem eigenen Eingabefeld. Drücken Sie den aktuellen Begriff über `setTerm`:

```java
nav.getSearch().setFieldVisible(false);

myField.onModify(event -> nav.getSearch().setTerm(event.getText()));
```

Um auf das, was der Benutzer im integrierten Feld eingibt, zu reagieren, hören Sie auf das Suchereignis:

```java
nav.onSearch(event -> log(event.getTerm()));
```

## Styling `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
