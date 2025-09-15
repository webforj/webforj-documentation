---
title: AppLayout
sidebar_position: 5
_i18n_hash: e6da714fff4ce713ceb5b486b8ab0026
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

Das `AppLayout` ist eine umfassende responsive Layout-Komponente, die eine Kopfzeile, eine Fußzeile, ein Schublade und einen Inhaltsbereich bereitstellt. Die Kopfzeile und Fußzeile sind fixiert, die Schublade fährt in und aus dem Ansichtsfenster, und der Inhalt ist scrollbar.

Diese Komponente kann verwendet werden, um gängige App-Layouts zu erstellen, wie z.B. ein Dashboard.

## Funktionen {#features}

Das webforJ App Layout ist eine Komponente, die das Erstellen gängiger App-Layouts ermöglicht.

<ul>
    <li>Einfach zu bedienen und anzupassen</li>
    <li>Responsives Design</li>
    <li>Mehrere Layout-Optionen</li>
    <li>Funktioniert mit dem webforJ Dunkelmodus</li>
</ul>

Es bietet eine Kopfzeile, Fußzeile, Schublade und Inhaltsbereich, die alle in einer responsiven Komponente integriert sind, die leicht angepasst werden kann, um schnell gängige App-Layouts wie ein Dashboard zu erstellen. Die Kopfzeile und Fußzeile sind fixiert, die Schublade fährt in und aus dem Ansichtsfenster, und der Inhalt ist scrollbar.

Jeder Teil des Layouts ist ein `Div`, das jede gültige webforJ-Steuerung enthalten kann. Für beste Ergebnisse sollte die App ein Viewport-Meta-Tag enthalten, das viewport-fit=cover enthält. Das Meta-Tag bewirkt, dass der Viewport so skaliert wird, dass er den Bildschirm des Geräts ausfüllt.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Übersicht {#overview}

Der folgende Code-Sample führt zu einer App mit einer zusammenklappbaren Seitenleiste, die ein Logo und Registerkarten für verschiedene Inhaltsoptionen sowie eine Kopfzeile enthält. Die Demo verwendet die web-Komponente dwc-icon-button, um einen Schubladen-Umschalter zu erstellen. Der Knopf hat das Attribut data-drawer-toggle, das den DwcAppLayout anweist, auf Klickereignisse von dieser Komponente zu hören, um den Schubladenstatus zu wechseln.

<AppLayoutViewer path='/webforj/applayout/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java'
cssURL='/css/applayout/applayout.css'
/>

## Vollbreite Navbar {#full-width-navbar}

Standardmäßig rendert das AppLayout die Kopfzeile und die Fußzeile im Off-Screen-Modus. Der Off-Screen-Modus bedeutet, dass die Position der Kopfzeile und der Fußzeile verschoben wird, um neben der geöffneten Schublade zu passen. Das Deaktivieren dieses Modus führt dazu, dass die Kopfzeile und Fußzeile den gesamten verfügbaren Raum einnehmen und die Position der Schublade oben und unten anpassen, um mit der Kopfzeile und der Fußzeile zu passen.

```java showLineNumbers
AppLayout myApp = new AppLayout();

myApp.setHeaderOffscreen(false);
myApp.setFooterOffscreen(false);
```

<AppLayoutViewer path='/webforj/applayoutfullnavbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarView.java'
cssURL='/css/applayout/applayout.css'/>

## Mehrere Werkzeugleisten {#multiple-toolbars}

Die Navbar hat keine Begrenzung für die Anzahl der Werkzeugleisten, die Sie hinzufügen können. Eine `Toolbar` ist eine horizontale Container-Komponente, die eine Reihe von Aktionsschaltflächen, Symbolen oder anderen Steuerelementen enthält. Um eine zusätzliche Werkzeugleiste hinzuzufügen, verwenden Sie einfach die Methode `addToHeader()`, um eine weitere `Toolbar`-Komponente hinzuzufügen.

Die folgende Demo zeigt, wie man zwei Werkzeugleisten verwendet. Die erste enthält die Umschalttaste der Schublade und den Titel der App. Die zweite Werkzeugleiste enthält ein sekundäres Navigationsmenü.

<AppLayoutViewer path='/webforj/applayoutmultipleheaders/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java'
cssURL='/css/applayout/applayout.css'/>

## Sticky Werkzeugleisten {#sticky-toolbars}

Eine Sticky-Werkzeugleiste ist eine Werkzeugleiste, die am oberen Rand der Seite sichtbar bleibt, wenn der Benutzer nach unten scrollt, jedoch die Höhe der Navbar reduziert wird, um mehr Platz für den Inhalt der Seite zur Verfügung zu stellen. Normalerweise enthält diese Art von Werkzeugleiste ein festes Navigationsmenü, das für die aktuelle Seite relevant ist.

Es ist möglich, Sticky-Werkzeugleisten mithilfe der CSS-Custom-Property `--dwc-app-layout-header-collapse-height` und der Option `AppLayout.setHeaderReveal()` zu erstellen.

Wenn `AppLayout.setHeaderReveal(true)` aufgerufen wird, wird die Kopfzeile beim ersten Rendern sichtbar und dann verborgen, wenn der Benutzer beginnt, nach unten zu scrollen. Sobald der Benutzer beginnt, wieder nach oben zu scrollen, wird die Kopfzeile enthüllt.

Mit Hilfe der CSS-Custom-Property `--dwc-app-layout-header-collapse-height` ist es möglich, festzulegen, wie viel von der Kopfzeilen-Navigation verborgen wird.

<AppLayoutViewer path='/webforj/applayoutstickytoolbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java'
cssURL='/css/applayout/applayout.css'/>

## Mobile Navigationslayout {#mobile-navigation-layout}

Die untere Navbar kann verwendet werden, um eine andere Version der Navigation am unteren Rand der App bereitzustellen. Diese Art der Navigation ist besonders in mobilen Apps beliebt.

Beachten Sie, wie die Schublade in der folgenden Demo verborgen ist. Das AppLayout-Widget unterstützt drei Schubladenpositionen: `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT` und `DrawerPlacement.HIDDEN`.

Wie bei `AppLayout.setHeaderReveal()` wird auch `AppLayout.setFooterReveal()` unterstützt. Wenn `AppLayout.setFooterReveal(true)` aufgerufen wird, wird die Fußzeile beim ersten Rendern sichtbar und verborgen, wenn der Benutzer beginnt, nach oben zu scrollen. Sobald der Benutzer wieder nach unten scrollt, wird die Fußzeile enthüllt.

Standardmäßig wird die Schublade bei einer Bildschirmbreite von 800px oder weniger in den Popover-Modus versetzt. Dies wird als Breakpoint bezeichnet. Der Popover-Modus bedeutet, dass die Schublade über den Inhaltsbereich mit einem Overlay aufspringt. Es ist möglich, den Breakpoint mithilfe der Methode `setDrawerBreakpoint()` zu konfigurieren, und der Breakpoint muss eine gültige [Media Query](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries) sein.

<AppLayoutViewer path='/webforj/applayoutmobiledrawer/?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java'
cssURL='/css/applayout/applayout.css'
/>

## Schubladen-Dienstprogramme {#drawer-utilities}

Die `AppLayout` Schubladen-Dienstprogramme sind für die integrierte Navigation und kontextuelle Menüs innerhalb des Haupt-App-Layouts konzipiert, während eigenständige [`Drawer`](https://docs.webforj.com/docs/components/drawer) Komponenten flexible, unabhängige Schiebepanels bieten, die überall in Ihrer App für zusätzlichen Inhalt, Filter oder Benachrichtigungen verwendet werden können. Dieser Abschnitt konzentriert sich auf die integrierten Schubladenmerkmale und -dienstprogramme, die von AppLayout bereitgestellt werden.

### Schubladen-Breakpoint {#drawer-breakpoint}

Standardmäßig wird die Schublade bei einer Bildschirmbreite von 800px oder weniger in den Popover-Modus versetzt. Dies wird als Breakpoint bezeichnet. Der Popover-Modus bedeutet, dass die Schublade über den Inhaltsbereich mit einem Overlay aufspringt. Es ist möglich, den Breakpoint mithilfe der Methode `setDrawerBreakpoint()` zu konfigurieren, und der Breakpoint muss eine gültige Media Query sein.

Zum Beispiel ist im folgenden Beispiel der Schubladen-Breakpoint auf 500px oder weniger konfiguriert.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### Schubladentitel {#drawer-title}

Die `AppLayout` Komponente stellt eine Methode `addToDrawerTitle()` zur Verfügung, um einen benutzerdefinierten Titel anzuzeigen, der im Schubladenkopf angezeigt wird. 

```java
layout.addToDrawerTitle(new Div("Menü"));
```

### Schubladenaktionen {#drawer-actions}

Die `AppLayout` Komponente ermöglicht es Ihnen, benutzerdefinierte Komponenten wie Schaltflächen oder Symbole in den **Schubladenkopf-Aktionsbereich** zu platzieren, indem Sie die Methode `addToDrawerHeaderActions()` verwenden.

```java
layout.addToDrawerHeaderActions(
    new IconButton(TablerIcon.create("bell")),
);
```

Es ist möglich, mehrere Komponenten als Argumente zu übergeben:

```java
layout.addToDrawerHeaderActions(
    new IconButton(TablerIcon.create("bell")),
    new Button("Profil")
);
```

Schubladenaktionen erscheinen im **rechtsbündigen Bereich** des Schubladenkopfes.

<AppLayoutViewer path='/webforj/applayoutdrawerutility/content/Dashboard/?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java'
cssURL='/css/applayout/applayout.css'
/>

## `AppDrawerToggle` <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

Die [`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) Komponente ist eine serverseitige webforJ-Klasse, die einen Knopf darstellt, der verwendet wird, um die Sichtbarkeit einer Navigationsschublade in einem `AppLayout` umzuschalten. Sie entspricht dem clientseitigen `<dwc-app-drawer-toggle>` Element und ist standardmäßig so gestaltet, dass sie sich wie ein traditionelles Hamburger-Menü-Symbol verhält. Dieses Verhalten kann angepasst werden.

### Übersicht {#overview-1}

Die `AppDrawerToggle` erweitert `IconButton` und verwendet standardmäßig das "menu-2" Symbol aus dem Tabler-Symbolsatz. Sie wendet automatisch das Attribut `data-drawer-toggle` an, um sich in das clientseitige Schubladenverhalten zu integrieren.

```java
// Keine Ereignisregistrierung erforderlich:
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// Der Schubladen-Umschalter funktioniert sofort—keine manuellen Ereignislistener erforderlich.
```
## Styling {#styling}

<TableBuilder name="AppLayout" />
