---
title: AppLayout
sidebar_position: 5
sidebar_class_name: updated-content
_i18n_hash: 7f842a66a5bcca7efe7ee894a0b001b0
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

Das `AppLayout` ist eine umfassende responsive Layout-Komponente, die einen Header, einen Footer, ein Drawer und einen Inhaltsbereich bietet. Der Header und der Footer sind fixiert, das Drawer fährt in und aus dem Ansichtsfenster, und der Inhalt ist scrollbar.

Diese Komponente kann verwendet werden, um gängige App-Layouts zu erstellen, wie z.B. ein Dashboard.

## Funktionen {#features}

Das webforJ App Layout ist eine Komponente, die den Aufbau gängiger App-Layouts ermöglicht.

<ul>
    <li>Einfach zu verwenden und anzupassen</li>
    <li>Responsives Design</li>
    <li>Mehrere Layout-Optionen</li>
    <li>Funktioniert mit dem webforJ Dunkelmodus</li>
</ul>

Es bietet einen Header, Footer, Drawer und Inhaltsbereich, die alle in eine responsive Komponente integriert sind, die leicht angepasst werden kann, um schnell gängige App-Layouts wie ein Dashboard zu erstellen. Der Header und der Footer sind fixiert, das Drawer fährt in und aus dem Ansichtsfenster, und der Inhalt ist scrollbar.

Jeder Teil des Layouts ist ein `Div`, der jede gültige webforJ Steuerung enthalten kann. Für die besten Ergebnisse sollte die App ein Viewport-Meta-Tag enthalten, das viewport-fit=cover enthält. Das Meta-Tag sorgt dafür, dass der Viewport so skaliert wird, dass er den Gerätedisplay ausfüllt.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Übersicht {#overview}

Der folgende Codeausschnitt führt zu einer App mit einem einklappbaren Sidebar, das ein Logo und Tabs für verschiedene Inhaltsoptionen sowie einen Header enthält. Die Demo verwendet die web-Komponente dwc-icon-button, um einen Schalter für das Drawer-Toggle zu erstellen. Der Button hat das Attribut data-drawer-toggle, das den DwcAppLayout anweist, auf Klickereignisse von dieser Komponente zu hören, um den Zustand des Drawers zu toggeln.

<AppLayoutViewer path='/webforj/applayout/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java'
cssURL='/css/applayout/applayout.css'
/>

## Navbar in voller Breite {#full-width-navbar}

Standardmäßig rendert das AppLayout den Header und den Footer im Off-Screen-Modus. Der Off-Screen-Modus bedeutet, dass die Position von Header und Footer verschoben wird, um neben dem geöffneten Drawer zu passen. Das Deaktivieren dieses Modus führt dazu, dass Header und Footer den gesamten verfügbaren Platz einnehmen und die obere und untere Position des Drawers angepasst werden, um mit Header und Footer übereinzustimmen.

```java showLineNumbers
AppLayout myApp = new AppLayout();

myApp.setHeaderOffscreen(false);
myApp.setFooterOffscreen(false);
```

<AppLayoutViewer path='/webforj/applayoutfullnavbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarView.java'
cssURL='/css/applayout/applayout.css'/>

## Mehrere Werkzeugleisten {#multiple-toolbars}

Die Navbar hat keine Begrenzung für die Anzahl der Werkzeugleisten, die Sie hinzufügen können. Eine `Toolbar` ist eine horizontale Containerkomponente, die eine Reihe von Aktionsschaltflächen, Symbolen oder anderen Steuerungen hält. Um eine zusätzliche Toolbar hinzuzufügen, verwenden Sie einfach die Methode `addToHeader()`, um eine andere `Toolbar`-Komponente hinzuzufügen.

Die folgende Demo zeigt, wie man zwei Werkzeugleisten verwendet. Die erste beherbergt den Schalter für das Drawer und den Titel der App. Die zweite Werkzeugleiste beherbergt ein sekundäres Navigationsmenü.

<AppLayoutViewer path='/webforj/applayoutmultipleheaders/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java'
cssURL='/css/applayout/applayout.css'/>

## Sticky Werkzeugleisten {#sticky-toolbars}

Eine Sticky-Werkzeugleiste ist eine Werkzeugleiste, die oben auf der Seite sichtbar bleibt, wenn der Benutzer nach unten scrollt, aber die Höhe der Navbar wird verkleinert, um mehr Platz für den Seiteninhalt zu schaffen. Normalerweise enthält diese Art von Werkzeugleiste ein festes Navigationsmenü, das relevant für die aktuelle Seite ist.

Es ist möglich, Sticky-Werkzeugleisten mit der CSS-Custom-Property `--dwc-app-layout-header-collapse-height` und der Option `AppLayout.setHeaderReveal()` zu erstellen.

Wenn `AppLayout.setHeaderReveal(true)` aufgerufen wird, wird der Header bei der ersten Darstellung sichtbar und dann ausgeblendet, wenn der Benutzer nach unten scrollt. Sobald der Benutzer wieder nach oben scrollt, wird der Header wieder sichtbar.

Mit Hilfe der CSS-Custom-Property `--dwc-app-layout-header-collapse-height` ist es möglich zu steuern, wie viel von der Header-Navbar ausgeblendet wird.

<AppLayoutViewer path='/webforj/applayoutstickytoolbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java'
cssURL='/css/applayout/applayout.css'/>

## Mobile Navigationslayout {#mobile-navigation-layout}

Die untere Navbar kann verwendet werden, um eine andere Version der Navigation am unteren Rand der App bereitzustellen. Diese Art der Navigation ist besonders beliebt in mobilen Apps.

Beachten Sie, wie das Drawer in der folgenden Demo ausgeblendet ist. Das AppLayout-Widget unterstützt drei Drawer-Positionen: `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT` und `DrawerPlacement.HIDDEN`.

Ähnlich wie `AppLayout.setHeaderReveal()` wird auch `AppLayout.setFooterReveal()` unterstützt. Wenn `AppLayout.setFooterReveal(true)` aufgerufen wird, wird der Footer bei der ersten Darstellung sichtbar und dann ausgeblendet, wenn der Benutzer nach oben scrollt. Sobald der Benutzer wieder nach unten scrollt, wird der Footer wieder sichtbar.

Standardmäßig, wenn die Bildschirmbreite 800px oder weniger beträgt, wechselt der Drawer in den Popover-Modus. Dies wird als Breakpoint bezeichnet. Der Popover-Modus bedeutet, dass der Drawer über dem Inhaltsbereich mit einer Überlagerung angezeigt wird. Es ist möglich, den Breakpoint zu konfigurieren, indem Sie die Methode `setDrawerBreakpoint()` verwenden, und der Breakpoint muss eine gültige [Media Query](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries) sein.

<AppLayoutViewer path='/webforj/applayoutmobiledrawer/?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java'
cssURL='/css/applayout/applayoutMobile.css'
/>

## Drawer-Hilfsprogramme {#drawer-utilities}

Die `AppLayout`-Drawer-Hilfsprogramme sind für die integrierte Navigation und kontextuelle Menüs innerhalb des Haupt-App-Layouts konzipiert, während eigenständige [`Drawer`](https://docs.webforj.com/docs/components/drawer) Komponenten flexible, unabhängige Schiebepanels bieten, die überall in Ihrer App für zusätzliche Inhalte, Filter oder Benachrichtigungen verwendet werden können. Dieser Abschnitt konzentriert sich auf die integrierten Drawer-Funktionen und -Hilfen, die von AppLayout bereitgestellt werden.

### Drawer-Breakpoint {#drawer-breakpoint}

Standardmäßig, wenn die Bildschirmbreite 800px oder weniger beträgt, wechselt der Drawer in den Popover-Modus. Dies wird als Breakpoint bezeichnet. Der Popover-Modus bedeutet, dass der Drawer über dem Inhaltsbereich mit einer Überlagerung angezeigt wird. Es ist möglich, den Breakpoint zu konfigurieren, indem Sie die Methode `setDrawerBreakpoint()` verwenden, und der Breakpoint muss eine gültige Media Query sein.

Zum Beispiel wird im folgenden Beispiel der Drawer-Breakpoint auf 500px oder weniger konfiguriert.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### Drawer-Titel {#drawer-title}

Die `AppLayout`-Komponente bietet eine Methode `addToDrawerTitle()`, um einen benutzerdefinierten Titel zu definieren, der im Drawer-Header angezeigt wird.

```java
layout.addToDrawerTitle(new Div("Menü"));
```

### Drawer-Aktionen {#drawer-actions}

Die `AppLayout`-Komponente ermöglicht es Ihnen, benutzerdefinierte Komponenten wie Schaltflächen oder Symbole im **Drawer-Header-Aktionsbereich** mit der Methode `addToDrawerHeaderActions()` zu platzieren.

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

Drawer-Aktionen erscheinen im **rechtsbündigen Bereich** des Headers des Drawers.

<AppLayoutViewer path='/webforj/applayoutdrawerutility/content/Dashboard/?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java'
cssURL='/css/applayout/applayout.css'
/>

## `AppDrawerToggle` <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

Die [`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) Komponente ist eine serverseitige webforJ-Klasse, die eine Schaltfläche darstellt, die verwendet wird, um die Sichtbarkeit eines Navigationsdrawers in einem `AppLayout` umzuschalten. Sie entspricht dem clientseitigen `<dwc-app-drawer-toggle>`-Element und ist standardmäßig so gestaltet, dass sie wie ein traditionelles Hamburger-Menü-Icon funktioniert; dieses Verhalten kann angepasst werden.

### Übersicht {#overview-1}

Die `AppDrawerToggle` erweitert `IconButton` und verwendet standardmäßig das "menu-2"-Symbol aus dem Tabler-Symbolsatz. Sie wendet automatisch das Attribut `data-drawer-toggle` an, um sich in das clientseitige Drawer-Verhalten zu integrieren.

```java
// Keine Ereignisanmeldung erforderlich:
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// Der Drawer-Toggle funktioniert sofort—keine manuellen Ereignis-Listener erforderlich.
```
## Styling {#styling}

<TableBuilder name="AppLayout" />
