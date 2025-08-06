---
title: AppNav
sidebar_position: 6
_i18n_hash: 47432ed72280efdc4d1b48e72d95b87d
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-app-nav" />
<DocChip chip="name" label="dwc-app-nav-item" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="appnav" location="com/webforj/component/appnav/AppNav" top='true'/> 

Die `AppNav`-Komponente in webforJ bietet ein flexibles und organisiertes seitliches Navigationsmenü mit Unterstützung sowohl für flache als auch für hierarchische Strukturen. Jeder Eintrag ist ein `AppNavItem`, das einen einfachen Link oder eine Gruppe mit Unterelementen darstellen kann. Elemente können mit internen Ansichten oder externen Ressourcen verknüpft werden, ergänzt durch Icons, Abzeichen oder andere Komponenten.

## Hinzufügen und Verschachteln von Elementen {#adding-and-nesting-items}

`AppNavItem`-Instanzen werden verwendet, um die Struktur des `AppNav` zu füllen. Diese Elemente können einfache Links oder verschachtelte Gruppenüberschriften sein, die untergeordnete Elemente enthalten. Gruppenüberschriften ohne Links fungieren als erweiterbare Container.

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

:::tip Gruppenelemente verlinken
Elemente auf der obersten Ebene in einem Navigationsbaum sollen in der Regel erweiterbar sein – nicht klickbare Links. Das Setzen eines `path` für solche Elemente kann Benutzer verwirren, die erwarten, dass sie Unterelemente anzeigen, anstatt woanders zu navigieren.

Wenn Sie möchten, dass die Gruppenüberschrift eine benutzerdefinierte Aktion auslöst (wie das Öffnen externer Dokumente), lassen Sie den Gruppenpfad leer und fügen Sie stattdessen eine interaktive Steuerung wie einen [`IconButton`](./icon#icon-buttons) zum Suffix des Elements hinzu. Dies hält die Benutzererfahrung konsistent und klar.
:::

<AppLayoutViewer 
path='/webforj/appnav/Social?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/appnav/AppNavView.java'
/>

## Elemente verlinken {#linking-items}

Jedes `AppNavItem` kann zu einer internen Ansicht oder einem externen Link navigieren. Dies können Sie mit statischen Pfaden oder registrierten View-Klassen definieren.

### Statische Pfade {#static-paths}

Verwenden Sie Zeichenfolgenpfade, um Links direkt zu definieren:

```java
AppNavItem docs = new AppNavItem("Dokumente", "/docs");
AppNavItem help = new AppNavItem("Hilfe", "https://support.example.com");
```

### Registrierte Views {#registered-views}

Wenn Ihre Views beim [Router](../routing/overview) registriert sind, können Sie die Klasse anstelle einer fest codierten URL übergeben:

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

Steuern Sie, wie Links geöffnet werden, mit `setTarget()`. Dies ist besonders nützlich für externe Links oder Pop-out-Views.

- **`SELF`** (Standard): Öffnet in der aktuellen Ansicht.
- **`BLANK`**: Öffnet in einem neuen Tab oder Fenster.
- **`PARENT`**: Öffnet im übergeordneten Browsing-Kontext.
- **`TOP`**: Öffnet im übergeordneten Browsing-Kontext.

```java
AppNavItem help = new AppNavItem("Hilfe", "https://support.example.com");
help.setTarget(AppNavItem.NavigationTarget.BLANK);
```

## Präfix und Suffix {#prefix-and-suffix}

`AppNavItem` unterstützt Präfix- und Suffixkomponenten. Verwenden Sie diese, um visuelle Klarheit mit Icons, Abzeichen oder Schaltflächen zu schaffen.

- **Präfix**: erscheint vor dem Label, nützlich für Icons.
- **Suffix**: erscheint nach dem Label, ideal für Abzeichen oder Aktionen.

```java
AppNavItem notifications = new AppNavItem("Benachrichtigungen");
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
