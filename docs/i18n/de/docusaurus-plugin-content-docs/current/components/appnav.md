---
title: AppNav
sidebar_position: 6
sidebar_class_name: new-content
description: >-
  Build hierarchical side navigation menus with AppNav and AppNavItem, linking
  to routes, registered views, or external URLs.
_i18n_hash: afb61d8d44c3f5dcb03f533954baafc1
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip="name" label="dwc-app-nav-label" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/>

Die `AppNav`-Komponente erstellt ein Seitenmenü aus `AppNavItem`-Einträgen. Elemente können zu internen Ansichten oder externen Ressourcen verlinken, unter übergeordneten Elementen geschachtelt werden, um hierarchische Menüs zu bilden, und Icons, Abzeichen oder andere Komponenten tragen, um den Benutzern auf einen Blick mehr Kontext zu geben.

<!-- INTRO_END -->

## Elemente hinzufügen und schachteln {#adding-and-nesting-items}

`AppNavItem`-Instanzen werden verwendet, um die `AppNav`-Struktur zu füllen. Diese Elemente können einfache Links oder geschachtelte Gruppenheader sein, die untergeordnete Elemente enthalten. Gruppenheader ohne Links fungieren als erweiterbare Container.

Verwenden Sie `addItem()`, um Elemente im Menü einzufügen:

```java
AppNavItem dashboard = new AppNavItem("Dashboard", "/dashboard");
AppNavItem admin = new AppNavItem("Admin");
admin.addItem(new AppNavItem("Users", "/admin/users"));
admin.addItem(new AppNavItem("Settings", "/admin/settings"));

AppNav nav = new AppNav();
nav.addItem(dashboard);
nav.addItem(admin);
```

:::tip Gruppenelemente verlinken
Elemente auf oberster Ebene in einem Navigationsbaum sind in der Regel dazu gedacht, erweiterbar zu sein – nicht klickbare Links. Das Setzen eines `path` auf solchen Elementen kann Benutzer verwirren, die erwarten, dass sie untergeordnete Elemente offenbaren, anstatt woanders zu navigieren.

Wenn Sie möchten, dass der Gruppenheader eine benutzerdefinierte Aktion auslöst (z. B. das Öffnen externer Dokumente), lassen Sie den Gruppenpfad leer und fügen Sie stattdessen ein interaktives Steuerelement wie einen [`IconButton`](./icon#icon-buttons) als Suffix des Elements hinzu. Dies hält die Benutzererfahrung konsistent und klar.
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

Jedes `AppNavItem` kann zu einer internen Ansicht oder einem externen Link navigieren. Sie können dies mithilfe von statischen Pfaden oder registrierten Ansichtsklassen definieren.

### Statische Pfade {#static-paths}

Verwenden Sie Zeichenfolgenpfade, um Links direkt zu definieren:

```java
AppNavItem docs = new AppNavItem("Docs", "/docs");
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
```

### Registrierte Ansichten {#registered-views}

Wenn Ihre Ansichten beim [Router](../routing/overview) registriert sind, können Sie die Klasse anstelle einer fest codierten URL übergeben:

```java
AppNavItem settings = new AppNavItem("Settings", SettingsView.class);
```

Wenn Ihre annotierte Route [Routenparameter](../routing/route-patterns#named-parameters) unterstützt, können Sie auch einen `ParametersBag` übergeben:

```java
ParametersBag params = ParametersBag.of("id=123");
AppNavItem advanced = new AppNavItem("User", UserView.class, params);
```

### Mit Abfrageparametern {#with-query-parameters}

Übergeben Sie einen `ParametersBag`, um Abfragezeichenfolgen einzuschließen:

```java
ParametersBag params = ParametersBag.of("param1=value1&param2=value2");
AppNavItem advanced = new AppNavItem("Advanced", SettingsView.class, params);
advanced.setQueryParameters(params);
```

## Zielverhalten {#target-behavior}

Steuern Sie, wie Links geöffnet werden, mit `setTarget()`. Dies ist insbesondere für externe Links oder Pop-out-Ansichten nützlich.

- **`SELF`** (Standard): Öffnet in der aktuellen Ansicht.
- **`BLANK`**: Öffnet in einem neuen Tab oder Fenster.
- **`PARENT`**: Öffnet im übergeordneten Browsing-Kontext.
- **`TOP`**: Öffnet im übergeordneten Browsing-Kontext.

```java
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Präfix und Suffix {#prefix-and-suffix}

`AppNavItem` unterstützt Präfix- und Suffixkomponenten. Verwenden Sie diese, um visuelle Klarheit mit Icons, Abzeichen oder Schaltflächen bereitzustellen.

- **Präfix**: erscheint vor dem Label, nützlich für Icons.
- **Suffix**: erscheint nach dem Label, großartig für Abzeichen oder Aktionen.

```java
AppNavItem notifications = new AppNavItem("Alerts");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Automatisches Öffnen von Gruppen {#auto-opening-groups}

Verwenden Sie `setAutoOpen(true)` an der `AppNav`-Komponente, um geschachtelte Gruppen beim Aktualisieren der App automatisch zu erweitern.

```java
nav.setAutoOpen(true);
```

## Abschnittsbezeichnungen <DocChip chip='since' label='26.02' /> {#section-labels}

`AppNavLabel` ist eine nicht interaktive Überschrift, die eine Reihe von Elementen betitelt. Eine Bezeichnung gilt für jedes Element, das ihr folgt, bis zur nächsten Bezeichnung oder zum Ende des Menüs, sodass eine lange Liste von Elementen auf oberster Ebene als einige benannte Gruppen gelesen werden kann, ohne sie zu schachteln.

Bezeichnungen werden mit `add()` anstelle von `addItem()` hinzugefügt, und die Reihenfolge der Aufrufe definiert die Abschnitte:

```java
AppNav nav = new AppNav();
nav.addItem(new AppNavItem("Dashboard", DashboardView.class, TablerIcon.create("layout-dashboard")));

nav.add(new AppNavLabel("Analytics"));
nav.addItem(new AppNavItem("Overview", OverviewView.class));
nav.addItem(new AppNavItem("Reports", ReportsView.class));

nav.add(new AppNavLabel("Other"));
nav.addItem(new AppNavItem("Settings", SettingsView.class));
```

Das Menü blendet eine Bezeichnung automatisch aus, wenn ihr Abschnitt keine sichtbaren Elemente hat, sodass eine Bezeichnung verschwindet, wenn ein [Suchbegriff](#search) ihre Elemente herausfiltert oder wenn alle von ihnen [oben](#pinning) im Menü angeheftet sind.

### Bezeichnungspräfix und -suffix {#label-prefix-and-suffix}

Wie `AppNavItem` unterstützt eine Bezeichnung Präfix- und Suffixkomponenten. Übergeben Sie ein Präfix an den Konstruktor oder setzen Sie eines von beiden danach:

```java
AppNavLabel analytics = new AppNavLabel("Analytics", TablerIcon.create("chart-pie"));
analytics.setSuffixComponent(new Badge().setText("2").setTheme(BadgeTheme.WARNING));

nav.add(analytics);
```

Das folgende Beispiel gruppiert ein Menü unter drei Bezeichnungen, von denen die erste ein [`Icon`](./icon) Präfix und ein [`Badge`](./badge) Suffix trägt. Dashboard steht über der ersten Bezeichnung, sodass es zu keinem Abschnitt gehört.

<ComponentDemo
path='/webforj/appnavlabel/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavLabelView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavLabelPageView.java',
]}
/>

## Anheften <DocChip chip='since' label='26.01' /> {#pinning}

Anheften ermöglicht es einem Benutzer, die Elemente, die er am häufigsten benötigt, in einer Gruppe oben in der Navigation zu platzieren, sodass ein tiefes Menü immer eine kurze Liste von Favoriten mit nur einem Klick behält. Es ist standardmäßig deaktiviert. Aktivieren Sie es über die Anheftkonfiguration:

```java
AppNav nav = new AppNav();
nav.getPinning().setEnabled(true);
```

Sobald aktiviert, zeigt jedes navigierbare Blattelement einen Anheftschalter an. Der Schalter wird bei Hover und bei Tastaturfokus angezeigt, sodass er ohne Maus erreichbar bleibt. Das Aktivieren bewegt das Element in die angeheftete Gruppe oben im Menü.

Einige Regeln regeln, was angeheftet werden kann und wie sich die Gruppe verhält:

- Nur navigierbare Blattelemente sind anheftbar. Gruppenheader (Elemente mit Kindern) sind niemals anheftbar.
- Die angeheftete Gruppe erscheint nur, wenn etwas angeheftet ist, und verschwindet wieder, wenn das letzte Element abgehängt wurde.
- Das Abheften gibt ein Element an seine ursprüngliche Position zurück, einschließlich Elementen, die mehrere Ebenen tief in Gruppen geschachtelt sind.
- Das Element wird bewegt, nicht kopiert, sodass alle Präfix- oder Suffixinhalte und alle damit verbundenen Listener weiterhin funktionieren, während es sich in der angehefteten Gruppe befindet.

Die folgende Demo hat das Anheften aktiviert mit einem benutzerdefinierten Gruppentitel und Dashboard anfangs angeheftet. Fahren Sie mit der Maus oder fokussieren Sie ein Blattelement, um den Anheftschalter anzuzeigen.

<ComponentDemo
path='/webforj/appnavpinning/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningPageView.java',
]}
/>

### Ein Element standardmäßig angeheftet starten {#starting-an-item-pinned}

Starten Sie ein Element in der angehefteten Gruppe, indem Sie seinen angehefteten Zustand festlegen. Verwenden Sie `isPinned()`, um den aktuellen Zustand auszulesen.

```java
AppNavItem reports = new AppNavItem("Reports", "/reports");
reports.setPinned(true);
```

:::info Anheften muss aktiviert sein
`setPinned(true)` hat nur dann Wirkung, wenn das Anheften am `AppNav` durch `getPinning().setEnabled(true)` aktiviert ist. Ohne dies hat der Aufruf keine Wirkung.
:::

### Titel der angehefteten Gruppe {#pinned-group-title}

Die angeheftete Gruppe wird standardmäßig mit `Pinned` beschriftet. Ändern Sie es, um zu Ihrem App zu passen:

```java
nav.getPinning().setTitle("Favoriten");
```

### Anheftschlüssel {#pin-keys}

Jedes anheftbare Element trägt einen Schlüssel, der es für die Persistenz und das [Anheftereignis](#reacting-to-pin-changes) identifiziert. Wenn Sie keinen festlegen, fällt der Schlüssel auf den Pfad des Elements zurück, sodass `getPinKey()` immer einen verwendbaren Wert zurückgibt.

```java
AppNavItem reports = new AppNavItem("Reports", "/reports");
reports.setPinKey("reports");
```

Setzen Sie einen expliziten Schlüssel, wenn der Pfad zur Laufzeit geändert werden kann. Ein stabiler Schlüssel hält eine Anheftung mit dem richtigen Element über Neu-Ladevorgänge hinweg, selbst wenn sich die URL ändert.

### Automatisches Speichern im lokalen Speicher {#autosave}

Anheftungen leben nur für die aktuelle Seitenansicht, es sei denn, Sie speichern sie. Automatisches Speichern ist die einfachste Option: Es speichert die Menge an angehefteten Elementen im lokalen Speicher des Browsers und stellt sie beim Neuladen wieder her. Es ist standardmäßig deaktiviert. Es benötigt eine stabile `id` (oder einen Namen) am Element für den Speicher-Schlüssel, und der Konstruktor `AppNav(String id)` ist der bequeme Weg, um einen festzulegen:

```java
AppNav nav = new AppNav("main-nav"); // gibt dem automatischen Speichern einen stabilen Speicher-Schlüssel
nav.getPinning().setAutosave(true);
```

:::info Automatisches Speichern benötigt eine id
Ohne `id` (oder Namen) am Element funktioniert das automatische Speichern stillschweigend nicht, da kein stabiler Schlüssel vorhanden ist, unter dem gespeichert werden kann. Die Persistenz erfolgt pro Browser, sodass Anheftungen den Benutzer nicht auf ein anderes Gerät oder einen anderen Browser begleiten.
:::

### Benutzerdefinierte Persistenz {#custom-persistence}

Um die Persistenz zu steuern, z. B. pro Benutzer auf dem Server, deaktivieren Sie das automatische Speichern und steuern Sie es selbst über das [Anheftereignis](#reacting-to-pin-changes) und `setPinned`:

```java
nav.getPinning().setAutosave(false);

// Speichern Sie die aktuelle Menge an angehefteten Schlüsseln, wann immer sie sich ändert
nav.onPin(event -> savePins(event.getKeys()));

// Stellen Sie beim Laden jeden gespeicherten Schlüssel wieder her
restoredKeys.forEach(key -> findItem(key).setPinned(true));
```

### Auf Anheftänderungen reagieren {#reacting-to-pin-changes}

Das Anheftereignis wird jedes Mal ausgelöst, wenn ein Element angeheftet oder abgehängt wird. Es trägt das Element, das sich geändert hat, seinen Schlüssel, den neuen angehefteten Zustand und die gesamte geordnete Menge an angehefteten Schlüsseln:

```java
nav.onPin(event -> {
  AppNavItem item = event.getItem(); // das Element, das sich geändert hat, oder null, wenn es nicht mehr in der Navigation ist
  boolean pinned = event.isPinned();
  String key = event.getKey();
  List<String> all = event.getKeys(); // jeder angeheftete Schlüssel, in angehefteter Reihenfolge
});
```

`getItem()` löst das Element, indem es seinen Anheftschlüssel abgleicht, und gibt `null` zurück, wenn das Element nicht mehr Teil der Navigation ist.

### Anheftsymbole {#pin-icons}

Der Schalter verwendet das integrierte `dwc:pin`-Symbol, während ein Element nicht angeheftet ist, und `dwc:pinned-off`, während es angeheftet ist. Tauschen Sie Ihr eigenes über `setUnpinnedIcon` und `setPinnedIcon` aus, die jede `IconDefinition` akzeptieren:

```java
nav.getPinning()
   .setUnpinnedIcon(TablerIcon.create("pin"))
   .setPinnedIcon(TablerIcon.create("pinned-off"));
```

### Anheftschalter auf Touchscreens {#pin-toggle-on-touchscreens}

Touchscreens haben kein Hover, um das Anheften anzuzeigen, sodass der Schalter dort standardmäßig verborgen ist. Halten Sie ihn auf Touchscreens sichtbar und antippbar mit `setTouchVisible(true)`:

```java
nav.getPinning().setTouchVisible(true);
```

## Suche <DocChip chip='since' label='26.01' /> {#search}

Das Suchfeld filtert das Menü nach dem Bezeichnungslabel des Elements, während der Benutzer tippt. Es ist standardmäßig deaktiviert. Sie können es anzeigen und ihm einen Platzhalter über die Suchkonfiguration geben:

```java
nav.getSearch().setFieldVisible(true);
nav.getSearch().setPlaceholder("Suche");
```

Während der Benutzer tippt, filtert das Menü Elemente nach Bezeichnung, öffnet jede Gruppe, die eine Übereinstimmung enthält, und zeigt eine leere Meldung an, wenn nichts übereinstimmt. Angehängte Verknüpfungen bleiben während der Suche sichtbar, sodass die Favoriten eines Benutzers auch während des Filters einen Klick entfernt bleiben.

<ComponentDemo
path='/webforj/appnavsearch/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavSearchPageView.java',
]}
/>

### Leere Meldung {#search-empty-message}

Setzen Sie die Meldung, die angezeigt wird, wenn eine Suche keine Ergebnisse liefert. Reintext wird als Text gerendert:

```java
nav.getSearch().setEmptyMessage("Keine Elemente gefunden");
```

### Suche von Ihrem eigenen Feld steuern {#custom-search-box}

Blenden Sie das integrierte Feld aus und versorgen Sie den Filter von einem eigenen Eingabefeld. Schieben Sie den aktuellen Begriff über `setTerm` hinein:

```java
nav.getSearch().setFieldVisible(false);

myField.onModify(event -> nav.getSearch().setTerm(event.getText()));
```

Um zu reagieren, was der Benutzer im integrierten Feld eintippt, hören Sie auf das Suchereignis:

```java
nav.onSearch(event -> log(event.getTerm()));
```

## Styling `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
