---
title: AppNav
sidebar_position: 6
_i18n_hash: 1e9ac3fc8372d76faee53a4b9ee2cf88
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/> 

Die `AppNav`-Komponente in webforJ bietet ein flexibles und organisiertes seitliches Navigationsmenü mit Unterstützung für flache und hierarchische Strukturen. Jeder Eintrag ist ein `AppNavItem`, das einen einfachen Link oder eine Gruppe mit untergeordneten Elementen darstellen kann. Elemente können mit internen Ansichten oder externen Ressourcen verknüpft werden und werden mit Symbolen, Abzeichen oder anderen Komponenten ergänzt.

## Hinzufügen und Verschachteln von Elementen {#adding-and-nesting-items}

`AppNavItem`-Instanzen werden verwendet, um die `AppNav`-Struktur zu füllen. Diese Elemente können einfache Links oder verschachtelte Gruppenkopfzeilen sein, die untergeordnete Elemente enthalten. Gruppenkopfzeilen ohne Links fungieren als erweiterbare Container.

Verwenden Sie `addItem()`, um Elemente in die Navigation aufzunehmen:

```java
AppNavItem dashboard = new AppNavItem("Dashboard", "/dashboard");
AppNavItem admin = new AppNavItem("Admin");
admin.addItem(new AppNavItem("Users", "/admin/users"));
admin.addItem(new AppNavItem("Settings", "/admin/settings"));

AppNav nav = new AppNav();
nav.addItem(dashboard);
nav.addItem(admin);
```

:::tip Verlinken von Gruppenelementen
Oberste Elemente in einem Navigationsbaum sind in der Regel dazu gedacht, erweiterbar zu sein – nicht als klickbare Links. Das Festlegen eines `path` für solche Elemente kann Benutzer verwirren, die erwarten, dass sie untergeordnete Elemente offenbaren, anstatt anderswohin zu navigieren.

Wenn Sie möchten, dass die Gruppenkopfzeile eine benutzerdefinierte Aktion auslöst (z. B. das Öffnen externer Dokumente), lassen Sie den Gruppenpfad leer und fügen Sie stattdessen eine interaktive Steuerung wie einen [`IconButton`](./icon#icon-buttons) zum Suffix des Elements hinzu. Dies hält die Benutzererfahrung konsistent und klar.
:::

<AppLayoutViewer 
path='/webforj/appnav/Social?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/appnav/AppNavView.java'
/>

## Verlinken von Elementen {#linking-items}

Jedes `AppNavItem` kann zu einer internen Ansicht oder einem externen Link navigieren. Sie können dies mit statischen Pfaden oder registrierten Ansichtsklassen definieren.

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

Steuern Sie, wie Links geöffnet werden, indem Sie `setTarget()` verwenden. Dies ist besonders nützlich für externe Links oder Popup-Ansichten.

- **`SELF`** (Standard): Öffnet in der aktuellen Ansicht.
- **`BLANK`**: Öffnet in einem neuen Tab oder Fenster.
- **`PARENT`**: Öffnet im übergeordneten Browsing-Kontext.
- **`TOP`**: Öffnet im übergeordneten Browsing-Kontext.

```java
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Präfix und Suffix {#prefix-and-suffix}

`AppNavItem` unterstützt Präfix- und Suffixkomponenten. Verwenden Sie diese, um visuelle Klarheit mit Symbolen, Abzeichen oder Schaltflächen zu bieten.

- **Präfix**: erscheint vor dem Label, nützlich für Symbole.
- **Suffix**: erscheint nach dem Label, ideal für Abzeichen oder Aktionen.

```java
AppNavItem notifications = new AppNavItem("Alerts");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Automatisches Öffnen von Gruppen {#auto-opening-groups}

Verwenden Sie `setAutoOpen(true)` an der `AppNav`-Komponente, um verschachtelte Gruppen automatisch zu erweitern, wenn die App aktualisiert wird.

```java
nav.setAutoOpen(true);
```

## Styling `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
