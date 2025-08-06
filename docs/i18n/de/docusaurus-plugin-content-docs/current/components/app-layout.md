---
title: AppLayout
sidebar_position: 5
sidebar_class_name: updated-content
_i18n_hash: 46ea0f38e27d84ef944e7a26fd0c5666
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

Das `AppLayout` ist eine umfassende responsive Layout-Komponente, die einen Header, einen Footer, eine Seitenleiste und einen Inhaltsbereich bereitstellt. Der Header und der Footer sind fest, die Seitenleiste fährt in und aus dem Ansichtsbereich, und der Inhalt ist scrollbar.

Diese Komponente kann verwendet werden, um gängige App-Layouts zu erstellen, wie beispielsweise ein Dashboard.

## Funktionen {#features}

Das webforJ App Layout ist eine Komponente, die den Aufbau gängiger App-Layouts ermöglicht.

<ul>
    <li>Einfache Verwendung und Anpassung</li>
    <li>Responsive Design</li>
    <li>Mehrere Layout-Optionen</li>
    <li>Funktioniert mit dem webforJ Dunkelmodus</li>
</ul>

Es bietet einen Header, einen Footer, eine Seitenleiste und einen Inhaltsbereich, die alle in eine responsive Komponente integriert sind, die leicht angepasst werden kann, um schnell gängige App-Layouts wie ein Dashboard zu erstellen. Der Header und der Footer sind fest, die Seitenleiste fährt in und aus dem Ansichtsbereich, und der Inhalt ist scrollbar.

Jeder Teil des Layouts ist ein `Div`, das jede gültige webforJ-Steuerung enthalten kann. Für beste Ergebnisse sollte die App ein Viewport-Meta-Tag enthalten, das viewport-fit=cover beinhaltet. Das Meta-Tag bewirkt, dass der Ansichtsbereich so skaliert wird, dass er den Display des Geräts ausfüllt.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Übersicht {#overview}

Das folgende Codebeispiel führt zu einer App mit einer zusammenklappbaren Seitenleiste, die ein Logo und Registerkarten für verschiedene Inhaltsoptionen sowie einen Header enthält. Die Demo verwendet die web-Komponente dwc-icon-button, um einen Schalter für die Seitenleiste zu erstellen. Der Button hat das Attribut data-drawer-toggle, das den DwcAppLayout anweist, auf Klickereignisse von dieser Komponente zu hören, um den Zustand der Seitenleiste umzuschalten.

<AppLayoutViewer path='/webforj/applayout/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java'
cssURL='/css/applayout/applayout.css'
/>

## Volle Breite Navbar {#full-width-navbar}

Standardmäßig rendert das AppLayout den Header und den Footer im Offscreen-Modus. Der Offscreen-Modus bedeutet, dass die Position von Header und Footer verschoben wird, um neben der geöffneten Seitenleiste zu passen. Das Deaktivieren dieses Modus führt dazu, dass der Header und der Footer den gesamten verfügbaren Platz einnehmen und die Position der Seitenleiste nach oben und unten verschoben wird, um mit dem Header und dem Footer zu passen.

```java showLineNumbers
AppLayout myApp = new AppLayout();

myApp.setHeaderOffscreen(false);
myApp.setFooterOffscreen(false);
```

<AppLayoutViewer path='/webforj/applayoutfullnavbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarView.java'
cssURL='/css/applayout/applayout.css' />

## Mehrere Toolbars {#multiple-toolbars}

Die Navbar hat keine Begrenzung für die Anzahl der Toolbars, die Sie hinzufügen können. Eine `Toolbar` ist eine horizontale Container-Komponente, die eine Reihe von Aktionsbuttons, Symbolen oder anderen Steuerelementen enthält. Um eine zusätzliche Toolbar hinzuzufügen, verwenden Sie einfach die Methode `addToHeader()`, um eine weitere `Toolbar`-Komponente hinzuzufügen.

Die folgende Demo zeigt, wie zwei Toolbars verwendet werden. Die erste enthält den Schalter für die Seitenleiste und den Titel der App. Die zweite Toolbar enthält ein sekundäres Navigationsmenü.

<AppLayoutViewer path='/webforj/applayoutmultipleheaders/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java'
cssURL='/css/applayout/applayout.css' />

## Sticky Toolbars {#sticky-toolbars}

Eine sticky Toolbar ist eine Toolbar, die am oberen Ende der Seite sichtbar bleibt, wenn der Benutzer nach unten scrollt, während die Höhe der Navbar verkleinert wird, um mehr Platz für den Inhalt der Seite verfügbar zu machen. Üblicherweise enthält diese Art von Toolbar ein festes Navigationsmenü, das für die aktuelle Seite relevant ist.

Es ist möglich, sticky Toolbars mit der CSS-Custom-Eigenschaft `--dwc-app-layout-header-collapse-height` und der Option `AppLayout.setHeaderReveal()` zu erstellen.

Wenn `AppLayout.setHeaderReveal(true)` aufgerufen wird, ist der Header beim ersten Rendern sichtbar und wird dann verborgen, wenn der Benutzer beginnt, nach unten zu scrollen. Sobald der Benutzer wieder nach oben scrollt, wird der Header wieder sichtbar.

Mit der Hilfe der CSS-Custom-Eigenschaft `--dwc-app-layout-header-collapse-height` ist es möglich zu steuern, wie viel der Header-Navigation verborgen wird.

<AppLayoutViewer path='/webforj/applayoutstickytoolbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java'
cssURL='/css/applayout/applayout.css' />

## Mobiles Navigationslayout {#mobile-navigation-layout}

Die untere Navbar kann verwendet werden, um eine andere Version der Navigation am unteren Ende der App bereitzustellen. Diese Art der Navigation ist insbesondere in mobilen Apps beliebt.

Beachten Sie, wie die Seitenleiste in der folgenden Demo verborgen ist. Das AppLayout-Widget unterstützt drei Positionen für die Seitenleiste: `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT` und `DrawerPlacement.HIDDEN`.

Ähnlich wie bei `AppLayout.setHeaderReveal()` wird auch `AppLayout.setFooterReveal()` unterstützt. Wenn `AppLayout.setFooterReveal(true)` aufgerufen wird, ist der Footer beim ersten Rendern sichtbar und wird dann verborgen, wenn der Benutzer beginnt, nach oben zu scrollen. Sobald der Benutzer wieder nach unten scrollt, wird der Footer sichtbar.

Standardmäßig wird die Seitenleiste bei einer Bildschirmbreite von 800px oder weniger in den Popover-Modus gewechselt. Dies wird als Breakpoint bezeichnet. Der Popover-Modus bedeutet, dass die Seitenleiste über den Inhaltsbereich mit einer Überlagerung angezeigt wird. Es ist möglich, den Breakpoint mit der Methode `setDrawerBreakpoint()` zu konfigurieren, wobei der Breakpoint eine gültige [Media Query](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries) sein muss.

<AppLayoutViewer path='/webforj/applayoutmobiledrawer/?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java'
cssURL='/css/applayout/applayoutMobile.css'
/>

## Seitenleisten-Dienstprogramme {#drawer-utilities}

Die `AppLayout`-Seitenleisten-Dienstprogramme sind für die integrierte Navigation und kontextbezogene Menüs innerhalb des Haupt-App-Layouts konzipiert, während eigenständige [`Drawer`](https://docs.webforj.com/docs/components/drawer) -Komponenten flexible, unabhängige Schiebe-Panels sind, die überall in Ihrer App für zusätzlichen Inhalt, Filter oder Benachrichtigungen verwendet werden können. Dieser Abschnitt konzentriert sich auf die integrierten Seitenleistenfunktionen und -dienstprogramme, die von AppLayout bereitgestellt werden.

### Seitenleisten-Breakpoint {#drawer-breakpoint}

Standardmäßig wird die Seitenleiste bei einer Bildschirmbreite von 800px oder weniger in den Popover-Modus gewechselt. Dies wird als Breakpoint bezeichnet. Der Popover-Modus bedeutet, dass die Seitenleiste über den Inhaltsbereich mit einer Überlagerung angezeigt wird. Es ist möglich, den Breakpoint mit der Methode `setDrawerBreakpoint()` zu konfigurieren, wobei der Breakpoint eine gültige Media Query sein muss.

Im folgenden Beispiel wird der Seitenleisten-Breakpoint auf 500px oder weniger eingerichtet.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### Seitenleisten-Titel {#drawer-title}

Die `AppLayout`-Komponente stellt die Methode `addToDrawerTitle()` zur Verfügung, um einen benutzerdefinierten Titel anzuzeigen, der im Header der Seitenleiste angezeigt wird.

```java
layout.addToDrawerTitle(new Div("Menü"));
```

### Seitenleisten-Aktionen {#drawer-actions}

Die `AppLayout`-Komponente ermöglicht es Ihnen, benutzerdefinierte Komponenten wie Buttons oder Symbole in den **Aktionen des Seitenleistenheaders** mit der Methode `addToDrawerHeaderActions()` einzufügen.

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

Seitenleisten-Aktionen erscheinen im **rechtsbündigen Bereich** des Headers der Seitenleiste.

<AppLayoutViewer path='/webforj/applayoutdrawerutility/content/Dashboard/?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java'
cssURL='/css/applayout/applayout.css'
/>

## `AppDrawerToggle` <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

Die [`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) -Komponente ist eine serverseitige webforJ-Klasse, die einen Button darstellt, der verwendet wird, um die Sichtbarkeit einer Navigationsseitenleiste in einem `AppLayout` umzuschalten. Sie mappt zu dem clientseitigen `<dwc-app-drawer-toggle>`-Element und ist standardmäßig so gestaltet, dass sie sich wie ein traditionelles Hamburger-Menü-Symbol verhält; dieses Verhalten kann angepasst werden.

### Übersicht {#overview-1}

Die `AppDrawerToggle` erweitert `IconButton` und verwendet standardmäßig das "menu-2" Symbol aus dem Tabler-Symbolsatz. Es wird automatisch das Attribut `data-drawer-toggle` angewendet, um sich in das clientseitige Seitenleistenverhalten zu integrieren.

```java
// Keine Ereignisregistrierung erforderlich:
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// Der Seitenleisen-Schalter funktioniert sofort ohne manuelle Ereignis-Listener.
```
## Styling {#styling}

<TableBuilder name="AppLayout" />
