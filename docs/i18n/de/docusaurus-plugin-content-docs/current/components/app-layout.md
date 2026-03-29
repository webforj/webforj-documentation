---
title: AppLayout
sidebar_position: 5
_i18n_hash: 8b9351e865e2651e84f0ae16ef5efc21
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

Die `AppLayout`-Komponente bietet eine fertige Seitenstruktur mit einem festen Header und Footer, einer Schublade, die ein- und ausgefahren werden kann, und einem scrollbaren Inhaltsbereich. Zusammen decken diese Abschnitte die Layoutbedürfnisse von Dashboards, Administrationspanels und den meisten mehrteiligen Schnittstellen ab.

<!-- INTRO_END -->

## Funktionen {#features}

Das webforJ App Layout ist eine Komponente, die den Aufbau gängiger App-Layouts ermöglicht.

<ul>
    <li>Einfach zu verwenden und anzupassen</li>
    <li>Responsives Design</li>
    <li>Multiple Layout-Optionen</li>
    <li>Funktioniert mit dem webforJ Dark Mode</li>
</ul>

Es bietet einen Header, Footer, eine Schublade und einen Inhaltsbereich, die alle in eine responsive Komponente integriert sind, die leicht angepasst werden kann, um gängige App-Layouts wie ein Dashboard schnell zu erstellen. Der Header und Footer sind fest, die Schublade fährt in und aus dem Ansichtsfenster, und der Inhalt ist scrollbar.

Jeder Teil des Layouts ist ein `Div`, der jede gültige webforJ-Steuerelement enthalten kann. Für die besten Ergebnisse sollte die App ein Viewport-Meta-Tag enthalten, das viewport-fit=cover ist. Das Meta-Tag sorgt dafür, dass der Viewport so skaliert wird, dass er das Display des Geräts ausfüllt.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Übersicht {#overview}

Das folgende Codebeispiel ergibt eine App mit einer einklappbaren Seitenleiste, die ein Logo und Registerkarten für verschiedene Inhaltsoptionen sowie einen Header enthält. Die Demo verwendet die webkomponente dwc-icon-button, um eine Schubladenumschaltfläche zu erstellen. Die Schaltfläche hat das Attribut data-drawer-toggle, das die DwcAppLayout anweist, auf Klickereignisse zu hören, die von dieser Komponente kommen, um den Schubladenstatus zu wechseln.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayout/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Vollbreite Navbar {#full-width-navbar}

Standardmäßig rendert das AppLayout den Header und den Footer im Off-Screen-Modus. Der Off-Screen-Modus bedeutet, dass die Position von Header und Footer verschoben wird, um neben der geöffneten Schublade zu passen. Das Deaktivieren dieses Modus führt dazu, dass der Header und Footer den gesamten verfügbaren Platz einnehmen und die Position der Schublade oben und unten an den Header und Footer angepasst wird.

```java showLineNumbers
AppLayout myApp = new AppLayout();

myApp.setHeaderOffscreen(false);
myApp.setFooterOffscreen(false);
```

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutfullnavbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Mehrere Werkzeugleisten {#multiple-toolbars}

Die Navbar hat keine Begrenzung hinsichtlich der Anzahl von Werkzeugleisten, die Sie hinzufügen können. Eine `Toolbar` ist ein horizontaler Container, der eine Menge von Aktionsschaltflächen, Symbolen oder anderen Steuerelementen enthält. Um eine zusätzliche Werkzeugleiste hinzuzufügen, verwenden Sie einfach die Methode `addToHeader()`, um eine weitere `Toolbar`-Komponente hinzuzufügen.

Die folgende Demo zeigt, wie man zwei Werkzeugleisten verwendet. Die erste enthält die Umschaltfläche für die Schublade und den Titel der App. Die zweite Werkzeugleiste enthält ein sekundäres Navigationsmenü.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutmultipleheaders/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeaderContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Sticky-Werkzeugleisten {#sticky-toolbars}

Eine Sticky-Werkzeugleiste ist eine Werkzeugleiste, die sichtbar bleibt, wenn der Benutzer nach unten scrollt, während die Höhe der Navbar zusammengeklappt wird, um mehr Platz für den Inhalt der Seite zu schaffen. Normalerweise enthält diese Art von Werkzeugleiste ein festes Navigationsmenü, das für die aktuelle Seite relevant ist.

Es ist möglich, Sticky-Werkzeugleisten mithilfe der CSS-CSS-Variablen `--dwc-app-layout-header-collapse-height` und der Option `AppLayout.setHeaderReveal()` zu erstellen.

Wenn `AppLayout.setHeaderReveal(true)` gesetzt wird, ist der Header beim ersten Rendern sichtbar und wird dann ausgeblendet, wenn der Benutzer beginnt, nach unten zu scrollen. Sobald der Benutzer wieder nach oben scrollt, wird der Header wieder sichtbar.

Mit Hilfe der CSS-CSS-Variablen `--dwc-app-layout-header-collapse-height` ist es möglich, zu steuern, wie viel von der Kopfzeile der Navbar verborgen bleibt.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutstickytoolbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Mobile Navigationslayout {#mobile-navigation-layout}

Die untere Navbar kann verwendet werden, um eine andere Version der Navigation am unteren Rand der App bereitzustellen. Diese Art der Navigation ist besonders in mobilen Apps beliebt.

Beachten Sie, wie die Schublade in der folgenden Demo verborgen ist. Das AppLayout-Widget unterstützt drei Schubladenpositionen: `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT` und `DrawerPlacement.HIDDEN`.

Genau wie `AppLayout.setHeaderReveal()` wird auch `AppLayout.setFooterReveal()` unterstützt. Wenn `AppLayout.setFooterReveal(true)` aufgerufen wird, ist der Footer beim ersten Rendern sichtbar und wird dann ausgeblendet, wenn der Benutzer beginnt, nach oben zu scrollen. Sobald der Benutzer wieder nach unten scrollt, wird der Footer wieder sichtbar.

Standardmäßig wird die Schublade bei einer Bildschirmbreite von 800px oder weniger in den Popover-Modus umgeschaltet. Dies wird als Breakpoint bezeichnet. Der Popover-Modus bedeutet, dass die Schublade über den Inhaltsbereich mit einer Überlagerung aufgeht. Es ist möglich, den Breakpoint zu konfigurieren, indem die Methode `setDrawerBreakpoint()` verwendet wird, und der Breakpoint muss eine gültige [Media Query](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries) sein.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutmobiledrawer/?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Schubladenwerkzeuge {#drawer-utilities}

Die Schubladenwerkzeuge des `AppLayout` sind für die integrierte Navigation und kontextbezogene Menüs innerhalb des Haupt-App-Layouts konzipiert, während eigenständige [`Drawer`](https://docs.webforj.com/docs/components/drawer)-Komponenten flexible, unabhängige Schiebe-Panels bieten, die überall in Ihrer App für zusätzlichen Inhalt, Filter oder Benachrichtigungen verwendet werden können. In diesem Abschnitt geht es um die integrierten Schubladenfunktionen und -werkzeuge, die von AppLayout bereitgestellt werden.

### Schubladen-Breakpoint {#drawer-breakpoint}

Standardmäßig wird die Schublade bei einer Bildschirmbreite von 800px oder weniger in den Popover-Modus umgeschaltet. Dies wird als Breakpoint bezeichnet. Der Popover-Modus bedeutet, dass die Schublade über den Inhaltsbereich mit einer Überlagerung aufgeht. Der Breakpoint kann konfiguriert werden, indem die Methode `setDrawerBreakpoint()` verwendet wird und der Breakpoint muss eine gültige Media Query sein.

Zum Beispiel wird im folgenden Beispiel der Schubladen-Breakpoint auf 500px oder weniger konfiguriert.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### Schubladentitel {#drawer-title}

Die `AppLayout`-Komponente bietet eine Methode `addToDrawerTitle()`, um einen benutzerdefinierten Titel im Schubladenheader anzuzeigen. 

```java
layout.addToDrawerTitle(new Div("Menü"));
```

### Schubladenaktionen {#drawer-actions}

Die `AppLayout`-Komponente ermöglicht es Ihnen, benutzerdefinierte Komponenten wie Schaltflächen oder Symbole in den **Header-Aktionsbereich der Schublade** mit der Methode `addToDrawerHeaderActions()` zu platzieren.

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

Schubladenaktionen erscheinen im **rechtsbündigen Bereich** des Schubladenheaders.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutdrawerutility/content/Dashboard/?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->


## `AppDrawerToggle` <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

Die [`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) Komponente ist eine serverseitige webforJ-Klasse, die eine Schaltfläche darstellt, die verwendet wird, um die Sichtbarkeit einer Navigationsschublade in einem `AppLayout` zu wechseln. Sie entspricht dem clientseitigen `<dwc-app-drawer-toggle>`-Element und ist standardmäßig so gestaltet, dass sie sich wie ein traditionelles Hamburger-Menü-Symbol verhält. Dieses Verhalten kann angepasst werden.

### Übersicht {#overview-1}

Die `AppDrawerToggle` erweitert `IconButton` und verwendet standardmäßig das "menu-2"-Symbol aus dem Tabler-Symbolsatz. Sie wendet automatisch das Attribut `data-drawer-toggle` an, um sich in das clientseitige Schubladenverhalten zu integrieren.

```java
// Keine Ereignisregistrierung erforderlich:
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// Der Schubladenumschalter funktioniert sofort—keine manuellen Ereignislistener erforderlich.
```
## Stil {#styling}

<TableBuilder name="AppLayout" />
