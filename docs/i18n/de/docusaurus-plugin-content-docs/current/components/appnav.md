---
title: AppNav
sidebar_position: 6
_i18n_hash: faa14d827865b1697b369a9787315dcd
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/> 

Die `AppNav`-Komponente in webforJ bietet ein flexibles und organisiertes Seiten-Navigationsmenü mit Unterstützung sowohl für flache als auch hierarchische Strukturen. Jedes Element ist ein `AppNavItem`, das einen einfachen Link oder eine Gruppe mit Unterelementen repräsentieren kann. Elemente können mit internen Ansichten oder externen Ressourcen verknüpft werden und werden durch Symbole, Abzeichen oder andere Komponenten ergänzt.

## Hinzufügen und Verschachteln von Elementen {#adding-and-nesting-items}

Instanzen von `AppNavItem` werden verwendet, um die `AppNav`-Struktur zu füllen. Diese Elemente können einfache Links oder verschachtelte Gruppenüberschriften sein, die untergeordnete Elemente enthalten. Gruppenüberschriften ohne Links fungieren als erweiterbare Container.

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

:::tip Verlinken von Gruppenobjekten
Obere Elemente in einem Navigationsbaum sollen in der Regel erweiterbar und keine klickbaren Links sein. Das Setzen eines `path` auf solchen Elementen kann Benutzer verwirren, die erwarten, dass sie Unterelemente offenbaren, anstatt woanders zu navigieren.

Wenn Sie möchten, dass die Gruppenüberschrift eine benutzerdefinierte Aktion auslöst (wie das Öffnen externer Dokumente), lassen Sie den Gruppenpfad leer und fügen Sie stattdessen eine interaktive Steuerung wie einen [`IconButton`](./icon#icon-buttons) am Suffix des Elements hinzu. Dies sorgt für eine konsistente und saubere Benutzererfahrung.
:::

<!--vale off-->
<AppLayoutViewer 
path='/webforj/appnav/Social?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/appnav/AppNavView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/appnav/AppNavPageView.java']}
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

Steuern Sie, wie Links geöffnet werden, mit `setTarget()`. Dies ist besonders nützlich für externe Links oder Pop-out-Ansichten.

- **`SELF`** (Standard): Öffnet in der aktuellen Ansicht.
- **`BLANK`**: Öffnet in einem neuen Tab oder Fenster.
- **`PARENT`**: Öffnet im übergeordneten Browsing-Kontext.
- **`TOP`**: Öffnet im obersten Browsing-Kontext.

```java
AppNavItem help = new AppNavItem("Hilfe", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Präfix und Suffix {#prefix-and-suffix}

`AppNavItem` unterstützt Präfix- und Suffixkomponenten. Verwenden Sie diese, um visuelle Klarheit mit Symbolen, Abzeichen oder Schaltflächen zu schaffen.

- **Präfix**: erscheint vor dem Label und ist nützlich für Symbole.
- **Suffix**: erscheint nach dem Label und ist großartig für Abzeichen oder Aktionen.

```java
AppNavItem notifications = new AppNavItem("Warnungen");
notifications.setPrefixComponent(TablerIcon.create("alert"));
notifications.setSuffixComponent(TablerIcon.create("link"));
```

## Automatisches Öffnen von Gruppen {#auto-opening-groups}

Verwenden Sie `setAutoOpen(true)` an der `AppNav`-Komponente, um automatisch verschachtelte Gruppen zu erweitern, wenn die App aktualisiert wird.

```java
nav.setAutoOpen(true);
```

## Styling von `AppNavItem` {#styling-appnavitem}

<TableBuilder name="AppNavItem" />
