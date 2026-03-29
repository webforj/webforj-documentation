---
title: AppNav
sidebar_position: 6
_i18n_hash: d4756db6bed23bc005fbcd2be222b4ea
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/>

Die `AppNav`-Komponente erstellt ein seitliches Navigationsmenü aus `AppNavItem`-Einträgen. Artikel können auf interne Ansichten oder externe Ressourcen verlinken, unter übergeordneten Artikeln genest werden, um hierarchische Menüs zu bilden, und Icons, Abzeichen oder andere Komponenten tragen, um den Benutzern auf einen Blick mehr Kontext zu geben.

<!-- INTRO_END -->

## Hinzufügen und Nesten von Elementen {#adding-and-nesting-items}

`AppNavItem`-Instanzen werden verwendet, um die `AppNav`-Struktur zu bevölkern. Diese Elemente können einfache Links oder verschachtelte Gruppenheader sein, die untergeordnete Elemente enthalten. Gruppenheader ohne Links fungieren als erweiterbare Container.

Verwenden Sie `addItem()`, um Elemente in der Navigation einzufügen:

```java
AppNavItem dashboard = new AppNavItem("Dashboard", "/dashboard");
AppNavItem admin = new AppNavItem("Admin");
admin.addItem(new AppNavItem("Benutzer", "/admin/users"));
admin.addItem(new AppNavItem("Einstellungen", "/admin/settings"));

AppNav nav = new AppNav();
nav.addItem(dashboard);
nav.addItem(admin);
```

:::tip Verlinken von Gruppenartikeln
Elemente auf der obersten Ebene in einem Navigationsbaum sollen typischerweise erweiterbar und keine klickbaren Links sein. Das Setzen eines `path` für solche Elemente kann Benutzer verwirren, die erwarten, dass sie untergeordnete Elemente offenbaren, anstatt anderswohin zu navigieren.

Wenn Sie möchten, dass der Gruppenheader eine benutzerdefinierte Aktion auslöst (z. B. das Öffnen externer Dokumente), lassen Sie den Gruppennamen leer und fügen Sie stattdessen eine interaktive Steuerung wie einen [`IconButton`](./icon#icon-buttons) zum Suffix des Elements hinzu. Dies hält die Benutzererfahrung konsistent und sauber.
:::

<!--vale off-->
<AppLayoutViewer 
path='/webforj/appnav/Social?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/appnav/AppNavView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/appnav/AppNavPageView.java']}
/>
<!--vale on-->

## Verlinken von Elementen {#linking-items}

Jedes `AppNavItem` kann zu einer internen Ansicht oder einem externen Link navigieren. Sie können dies mit statischen Pfaden oder registrierten Ansichtsklassen definieren.

### Statische Pfade {#static-paths}

Verwenden Sie Zeichenfolgenpfade, um Links direkt zu definieren:

```java
AppNavItem docs = new AppNavItem("Dokumente", "/docs");
AppNavItem help = new AppNavItem("Hilfe", "https://support.example.com");
```

### Registrierte Ansichten {#registered-views}

Wenn Ihre Ansichten beim [Router](../routing/overview) registriert sind, können Sie die Klasse anstelle einer fest codierten URL übergeben:

```java
AppNavItem settings = new AppNavItem("Einstellungen", SettingsView.class);
```

Wenn Ihre annotierte Route [Routenparameter](../routing/route-patterns#named-parameters) unterstützt, können Sie auch eine `ParametersBag` übergeben:

```java
ParametersBag params = ParametersBag.of("id=123");
AppNavItem advanced = new AppNavItem("Benutzer", UserView.class, params);
```

### Mit Abfrageparametern {#with-query-parameters}

Übergeben Sie eine `ParametersBag`, um Abfragezeichenfolgen einzuschließen:

```java
ParametersBag params = ParametersBag.of("param1=value1&param2=value2");
AppNavItem advanced = new AppNavItem("Erweitert", SettingsView.class, params);
advanced.setQueryParameters(params);
```

## Zielverhalten {#target-behavior}

Steuern Sie, wie Links geöffnet werden, mithilfe von `setTarget()`. Dies ist besonders nützlich für externe Links oder Pop-out-Ansichten.

- **`SELF`** (standard): Wird in der aktuellen Ansicht geöffnet.
- **`BLANK`**: Wird in einem neuen Tab oder Fenster geöffnet.
- **`PARENT`**: Wird im übergeordneten Browsing-Kontext geöffnet.
- **`TOP`**: Wird im übergeordneten Browsing-Kontext geöffnet.

```java
AppNavItem help = new AppNavItem("Hilfe", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Präfix und Suffix {#prefix-and-suffix}

`AppNavItem` unterstützt Präfix- und Suffix-Komponenten. Verwenden Sie diese, um visuelle Klarheit mit Icons, Abzeichen oder Schaltflächen bereitzustellen.

- **Präfix**: erscheint vor dem Label, nützlich für Icons.
- **Suffix**: erscheint nach dem Label, ideal für Abzeichen oder Aktionen.

```java
AppNavItem notifications = new AppNavItem("Alarme");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Automatisches Öffnen von Gruppen {#auto-opening-groups}

Verwenden Sie `setAutoOpen(true)` auf der `AppNav`-Komponente, um automatisch verschachtelte Gruppen zu erweitern, wenn die App aktualisiert wird.

```java
nav.setAutoOpen(true);
```

## Styling `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
