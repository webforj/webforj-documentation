---
title: AppNav
sidebar_position: 6
_i18n_hash: 859da44bd50a1b3e985139da624ed4d4
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/> 

Die `AppNav`-Komponente erstellt ein seitliches Navigationsmenü aus `AppNavItem`-Einträgen. Elemente können auf interne Ansichten oder externe Ressourcen verlinken, unter übergeordneten Elementen verschachtelt werden, um hierarchische Menüs zu bilden, und Icons, Badges oder andere Komponenten tragen, um den Benutzern auf einen Blick mehr Kontext zu geben.

<!-- INTRO_END -->

## Hinzufügen und Verschachteln von Elementen {#adding-and-nesting-items}

`AppNavItem`-Instanzen werden verwendet, um die `AppNav`-Struktur zu befüllen. Diese Elemente können einfache Links oder verschachtelte Gruppenüberschriften sein, die untergeordnete Elemente enthalten. Gruppenüberschriften ohne Links fungieren als erweiterbare Container.

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

:::tip Verlinkung von Gruppenobjekten
Oberste Elemente in einem Navigationsbaum sollen typischerweise erweiterbar – nicht klickbare Links – sein. Das Setzen eines `path` auf solchen Elementen kann Benutzer verwirren, die erwarten, dass sie untergeordnete Elemente enthüllen, anstatt anderswohin zu navigieren.

Wenn Sie möchten, dass die Gruppenüberschrift eine benutzerdefinierte Aktion auslöst (z. B. das Öffnen externen Dokuments), lassen Sie den Gruppenpfad leer und fügen Sie stattdessen ein interaktives Steuerungselement wie einen [`IconButton`](./icon#icon-buttons) zum Suffix des Elements hinzu. Dies hält die Benutzererfahrung konsistent und sauber.
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

Jedes `AppNavItem` kann zu einer internen Ansicht oder einem externen Link navigieren. Sie können dies mit statischen Pfaden oder registrierten Sichtklassen definieren.

### Statische Pfade {#static-paths}

Verwenden Sie Zeichenfolgenpfade, um Links direkt zu definieren:

```java
AppNavItem docs = new AppNavItem("Docs", "/docs");
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
```

### Registrierte Sichten {#registered-views}

Wenn Ihre Sichten beim [Router](../routing/overview) registriert sind, können Sie die Klasse anstelle einer hardcodierten URL übergeben:

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

Steuern Sie, wie Links geöffnet werden, mit `setTarget()`. Dies ist besonders nützlich für externe Links oder Pop-out-Ansichten.

- **`SELF`** (Standard): Öffnet in der aktuellen Ansicht.
- **`BLANK`**: Öffnet in einem neuen Tab oder Fenster.
- **`PARENT`**: Öffnet im übergeordneten Browsing-Kontext.
- **`TOP`**: Öffnet im übergeordneten Browsing-Kontext.

```java
AppNavItem help = new AppNavItem("Help", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Präfix und Suffix {#prefix-and-suffix}

`AppNavItem` unterstützt Präfix- und Suffixkomponenten. Verwenden Sie diese, um visuelle Klarheit mit Icons, Badges oder Schaltflächen zu bieten.

- **Präfix**: erscheint vor dem Label, nützlich für Icons.
- **Suffix**: erscheint nach dem Label, großartig für Badges oder Aktionen.

```java
AppNavItem notifications = new AppNavItem("Alerts");
notifications.setPrefixComponent(TablerIcon.create("alert");
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Automatisches Öffnen von Gruppen {#auto-opening-groups}

Verwenden Sie `setAutoOpen(true)` an der `AppNav`-Komponente, um automatisch verschachtelte Gruppen zu erweitern, wenn die App aktualisiert wird.

```java
nav.setAutoOpen(true);
```

## Styling von `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
