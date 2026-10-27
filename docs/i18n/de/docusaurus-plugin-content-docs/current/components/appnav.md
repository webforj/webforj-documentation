---
title: AppNav
sidebar_position: 6
sidebar_class_name: new-content
description: >-
  Build hierarchical side navigation menus with AppNav and AppNavItem, linking
  to routes, registered views, or external URLs.
_i18n_hash: 4f5eaa3e9738a7f959de22544589d240
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip="name" label="dwc-app-nav-label" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/layout/appnav/AppNav" top='true'/>

Die `AppNav`-Komponente erstellt ein seitliches Navigationsmenü aus `AppNavItem`-Einträgen. Elemente können auf interne Ansichten oder externe Ressourcen verlinken, unter übergeordneten Elementen geschachtelt werden, um hierarchische Menüs zu bilden, und Symbole, Abzeichen oder andere Komponenten tragen, um den Benutzern auf einen Blick mehr Kontext zu geben.

<!-- INTRO_END -->

## Hinzufügen und Verknüpfen von Elementen {#adding-and-nesting-items}

`AppNavItem`-Instanzen werden verwendet, um die `AppNav`-Struktur zu füllen. Diese Elemente können einfache Links oder geschachtelte Gruppenköpfe sein, die untergeordnete Elemente enthalten. Gruppenköpfe ohne Links fungieren als erweiterbare Container.

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

:::tip Verlinkung von Gruppenelementen
Elemente der obersten Ebene in einem Navigationsbaum sollen typischerweise erweiterbar und keine klickbaren Links sein. Das Festlegen eines `path` auf solchen Elementen kann Benutzer verwirren, die erwarten, dass diese Unterelemente anzeigen, anstatt anderswohin zu navigieren.

Wenn Sie möchten, dass der Gruppenheader eine benutzerdefinierte Aktion auslöst (z. B. das Öffnen externer Dokumente), lassen Sie den Gruppenpfad leer und fügen Sie stattdessen eine interaktive Steuerung wie einen [`IconButton`](./icon#icon-buttons) zum Suffix des Elements hinzu. Dies hält die Benutzererfahrung konsistent und sauber.
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

## Verlinkung von Elementen {#linking-items}

Jedes `AppNavItem` kann zu einer internen Ansicht oder einem externen Link navigieren. Sie können dies mit statischen Pfaden oder registrierten Ansichtsklassen definieren.

### Statische Pfade {#static-paths}

Verwenden Sie Zeichenfolgen-Pfade, um Links direkt zu definieren:

```java
AppNavItem docs = new AppNavItem("Docs", "/docs");
AppNavItem help = new AppNavItem("Hilfe", "https://support.example.com");
```

### Registrierte Ansichten {#registered-views}

Wenn Ihre Ansichten im [Router](../routing/overview) registriert sind, können Sie die Klasse anstelle einer fest codierten URL übergeben:

```java
AppNavItem settings = new AppNavItem("Einstellungen", SettingsView.class);
```

Wenn Ihre annotierte Route [Routenparameter](../routing/route-patterns#named-parameters) unterstützt, können Sie auch ein `ParametersBag` übergeben:

```java
ParametersBag params = ParametersBag.of("id=123");
AppNavItem advanced = new AppNavItem("Benutzer", UserView.class, params);
```

### Mit Abfrageparametern {#with-query-parameters}

Übergeben Sie ein `ParametersBag`, um Abfragezeichenfolgen einzuschließen:

```java
ParametersBag params = ParametersBag.of("param1=value1&param2=value2");
AppNavItem advanced = new AppNavItem("Erweitert", SettingsView.class, params);
advanced.setQueryParameters(params);
```

## Zielverhalten {#target-behavior}

Steuern Sie, wie Links geöffnet werden, mit `setTarget()`. Dies ist besonders nützlich für externe Links oder Pop-out-Ansichten.

- **`SELF`** (Standard): Öffnet in der aktuellen Ansicht.
- **`BLANK`**: Öffnet in einem neuen Tab oder Fenster.
- **`PARENT`**: Öffnet im übergeordneten Browsing-Kontext.
- **`TOP`**: Öffnet im übergeordneten Browsing-Kontext.

```java
AppNavItem help = new AppNavItem("Hilfe", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Präfix und Suffix {#prefix-and-suffix}

`AppNavItem` unterstützt Präfix- und Suffixkomponenten. Verwenden Sie diese, um visuelle Klarheit mit Symbolen, Abzeichen oder Schaltflächen zu schaffen.

- **Präfix**: erscheint vor dem Etikett, nützlich für Symbole.
- **Suffix**: erscheint nach dem Etikett, großartig für Abzeichen oder Aktionen.

```java
AppNavItem notifications = new AppNavItem("Warnungen");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Automatisches Öffnen von Gruppen {#auto-opening-groups}

Verwenden Sie `setAutoOpen(true)` an der `AppNav`-Komponente, um geschachtelte Gruppen automatisch zu erweitern, wenn die App aktualisiert wird.

```java
nav.setAutoOpen(true);
```

## Abschnittsbezeichnungen <DocChip chip='since' label='26.02' /> {#section-labels}

`AppNavLabel` ist eine nicht-interaktive Überschrift, die eine Reihe von Elementen betitelt. Eine Bezeichnung gilt für jedes Element, das ihr folgt, bis zur nächsten Bezeichnung oder dem Ende des Menüs, sodass eine lange Liste von Elementen der obersten Ebene als einige benannte Gruppen gelesen werden kann, ohne sie zu schachteln.

Bezeichnungen werden mit `add()` anstelle von `addItem()` hinzugefügt, und die Reihenfolge der Aufrufe definiert die Abschnitte:

```java
AppNav nav = new AppNav();
nav.addItem(new AppNavItem("Dashboard", DashboardView.class, TablerIcon.create("layout-dashboard")));

nav.add(new AppNavLabel("Analytics"));
nav.addItem(new AppNavItem("Überblick", OverviewView.class));
nav.addItem(new AppNavItem("Berichte", ReportsView.class));

nav.add(new AppNavLabel("Andere"));
nav.addItem(new AppNavItem("Einstellungen", SettingsView.class));
```

Die Navigation blendet eine Bezeichnung automatisch aus, wenn ihr Abschnitt keine sichtbaren Elemente hat, sodass eine Bezeichnung verschwindet, wenn eine [Suche](#search) ihre Elemente herausfiltert oder wenn alle von ihnen [oben](#pinning) im Menü fixiert sind.

### Bezeichnisprefix und -suffix {#label-prefix-and-suffix}

Wie `AppNavItem` unterstützt eine Bezeichnung Präfix- und Suffixkomponenten. Übergeben Sie ein Präfix an den Konstruktor oder setzen Sie eines danach:

```java
AppNavLabel analytics = new AppNavLabel("Analytics", TablerIcon.create("chart-pie"));
analytics.setSuffixComponent(new Badge().setText("2").setTheme(BadgeTheme.WARNING));

nav.add(analytics);
```

Im folgenden Beispiel wird ein Menü unter drei Bezeichnungen gruppiert, von denen die erste ein [`Icon`](./icon) -Präfix und ein [`Badge`](./badge) -Suffix trägt. Dashboard steht über der ersten Bezeichnung, sodass es zu keinem Abschnitt gehört.

<ComponentDemo
path='/webforj/appnavlabel/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavLabelView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavLabelPageView.java',
]}
/>

## Anheften <DocChip chip='since' label='26.01' /> {#pinning}

Anheften ermöglicht es einem Benutzer, die Elemente, die er am häufigsten erreicht, in einer Gruppe oben in der Navigation zu platzieren, sodass ein tiefes Menü weiterhin eine kurze Liste von Favoriten innerhalb eines Klicks behält. Es ist standardmäßig deaktiviert. Aktivieren Sie es über die Anheftkonfiguration:

```java
AppNav nav = new AppNav();
nav.getPinning().setEnabled(true);
```

Sobald es aktiviert ist, zeigt jedes navigierbare Blatt-Element einen Anheft-Toggle. Der Toggle wird bei Hover und beim Tastaturfokus angezeigt, sodass er ohne Maus erreichbar bleibt. Das Aktivieren verschiebt das Element in die angeheftete Gruppe oben in der Navigation.

Einige Regeln regeln, was angeheftet werden kann und wie sich die Gruppe verhält:

- Nur navigierbare Blatt-Elemente können angeheftet werden. Gruppenköpfe (Elemente mit Kindern) sind niemals anheftbar.
- Die angeheftete Gruppe erscheint nur, wenn etwas angeheftet wird, und verschwindet wieder, wenn das letzte Element abgehängt wird.
- Das Abheften bringt ein Element an seine ursprüngliche Position zurück, einschließlich Elemente, die mehrere Ebenen tief in Gruppen geschachtelt sind.
- Das Element wird verschoben, nicht kopiert, sodass alle Präfix- oder Suffixinhalte und alle angehängten Listener weiterhin funktionieren, während es sich in der angehefteten Gruppe befindet.

Die folgende Demo hat das Anheften aktiviert, mit einem benutzerdefinierten Gruppentitel und Dashboard beim Laden angeheftet. Bewegen Sie den Mauszeiger über oder fokussieren Sie ein Blatt-Element, um den Anheft-Toggle anzuzeigen.

<ComponentDemo
path='/webforj/appnavpinning/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningView.java',
  'src/main/java/com/webforj/samples/views/appnav/AppNavPinningPageView.java',
]}
/>

### Ein Element angeheftet starten {#starting-an-item-pinned}

Starten Sie ein Element in der angehefteten Gruppe, indem Sie seinen angehefteten Zustand festlegen. Verwenden Sie `isPinned()`, um den aktuellen Zustand zu lesen.

```java
AppNavItem reports = new AppNavItem("Berichte", "/reports");
reports.setPinned(true);
```

:::info Anheften muss aktiviert sein
`setPinned(true)` hat nur Wirkung, wenn das Anheften an der `AppNav` über `getPinning().setEnabled(true)` aktiviert ist. Ohne dies hat der Aufruf keine Auswirkungen.
:::

### Titel der angehefteten Gruppe {#pinned-group-title}

Die angeheftete Gruppe wird standardmäßig `Pinned` bezeichnet. Ändern Sie sie, um zu Ihrer App zu passen:

```java
nav.getPinning().setTitle("Favoriten");
```

### Anheftschlüssel {#pin-keys}

Jedes anheftbare Element trägt einen Schlüssel, der es zur Speicherung und für das [Anheftereignis](#reacting-to-pin-changes) identifiziert. Wenn Sie keinen festlegen, fällt der Schlüssel auf den Pfad des Elements zurück, sodass `getPinKey()` immer einen verwendbaren Wert zurückgibt.

```java
AppNavItem reports = new AppNavItem("Berichte", "/reports");
reports.setPinKey("reports");
```

Legen Sie einen expliziten Schlüssel fest, wenn der Pfad zur Laufzeit geändert werden kann. Ein stabiler Schlüssel hält einen Pin mit dem richtigen Element über Neuladevorgänge hinweg, selbst wenn sich die URL ändert.

### Automatisches Speichern im lokalen Speicher {#autosave}

Pins leben nur für die aktuelle Seitenansicht, es sei denn, Sie bewahren sie auf. Automatisches Speichern ist die einfachste Option: Es speichert die Menge an angehefteten Elementen im lokalen Speicher des Browsers und stellt sie beim Neuladen wieder her. Es ist standardmäßig deaktiviert. Es benötigt eine stabile `id` (oder einen Namen) an der Komponente für den Speicher-Schlüssel, und der Konstruktor `AppNav(String id)` ist der bequeme Weg, um einen festzulegen:

```java
AppNav nav = new AppNav("main-nav"); // gibt dem automatischen Speichern einen stabilen Speicher-Schlüssel
nav.getPinning().setAutosave(true);
```

:::info Automatisches Speichern benötigt eine id
Ohne `id` (oder einen Namen) an der Komponente speichert das automatische Speichern still nichts, da es keinen stabilen Schlüssel zum Speichern hat. Die Persistenz erfolgt pro Browser, sodass Pins nicht zu einem anderen Gerät oder Browser eines Benutzers folgen.
:::

### Benutzerdefinierte Persistenz {#custom-persistence}

Für Persistenz, die Sie selbst steuern, zum Beispiel pro Benutzer auf dem Server, deaktivieren Sie das automatische Speichern und steuern Sie es selbst über das [Anheftereignis](#reacting-to-pin-changes) und `setPinned`:

```java
nav.getPinning().setAutosave(false);

// speichern Sie die aktuelle Menge an angehefteten Schlüsseln, wann immer sie sich ändert
nav.onPin(event -> savePins(event.getKeys()));

// beim Laden, stellen Sie jeden gespeicherten Schlüssel wieder her
restoredKeys.forEach(key -> findItem(key).setPinned(true));
```

### Reagieren auf Anheftänderungen {#reacting-to-pin-changes}

Das Anheftereignis wird ausgelöst, wenn ein Element angeheftet oder abgehängt wird. Es enthält das Element, das sich geändert hat, seinen Schlüssel, den neuen angehefteten Zustand und die vollständige, geordnete Menge von angehefteten Schlüsseln:

```java
nav.onPin(event -> {
  AppNavItem item = event.getItem(); // das Element, das sich geändert hat, oder null, wenn es nicht mehr Teil der Navigation ist
  boolean pinned = event.isPinned();
  String key = event.getKey();
  List<String> all = event.getKeys(); // jeder angeheftete Schlüssel, in angehefteter Reihenfolge
});
```

`getItem()` löst das Element auf, indem es seinen Anheftschlüssel abgleicht, und gibt `null` zurück, wenn das Element nicht mehr Teil der Navigation ist.

### Anheftsymbole {#pin-icons}

Der Toggle verwendet das integrierte `dwc:pin`-Symbol, während ein Element nicht angeheftet ist, und `dwc:pinned-off`, während es angeheftet ist. Ersetzen Sie es durch Ihr eigenes über `setUnpinnedIcon` und `setPinnedIcon`, die jede `IconDefinition` akzeptieren:

```java
nav.getPinning()
   .setUnpinnedIcon(TablerIcon.create("pin"))
   .setPinnedIcon(TablerIcon.create("pinned-off"));
```

### Anhefttoggle auf Touchscreens {#pin-toggle-on-touchscreens}

Touchscreens haben kein Hover, um den Pin anzuzeigen, sodass der Toggle standardmäßig dort ausgeblendet ist. Halten Sie ihn sichtbar und anklickbar auf Touchscreens mit `setTouchVisible(true)`:

```java
nav.getPinning().setTouchVisible(true);
```

## Suche <DocChip chip='since' label='26.01' /> {#search}

Das Suchfeld filtert das Menü nach Elementbezeichnungen, während der Benutzer tippt. Es ist standardmäßig deaktiviert. Sie können es anzeigen und ihm einen Platzhalter geben, indem Sie die Suchkonfiguration festlegen:

```java
nav.getSearch().setFieldVisible(true);
nav.getSearch().setPlaceholder("Suche");
```

Während der Benutzer tippt, filtert die Navigation Elemente nach Bezeichnung, öffnet jede Gruppe, die ein Übereinstimmung enthält, und zeigt eine leere Nachricht an, wenn nichts übereinstimmt. Angehängte Verknüpfungen bleiben während der Suche sichtbar, sodass die Favoriten des Benutzers auch während der Filterung mit einem Klick erreichbar sind.

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

### Suchen mit Ihrem eigenen Feld steuern {#custom-search-box}

Blenden Sie das integrierte Feld aus und steuern Sie den Filter von einem eigenen Inputfeld. Übergeben Sie den aktuellen Begriff über `setTerm`:

```java
nav.getSearch().setFieldVisible(false);

myField.onModify(event -> nav.getSearch().setTerm(event.getText()));
```

Um auf das, was der Benutzer im integrierten Feld tippt, zu reagieren, hören Sie auf das Suchereignis:

```java
nav.onSearch(event -> log(event.getTerm()));
```

## Styling von `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
