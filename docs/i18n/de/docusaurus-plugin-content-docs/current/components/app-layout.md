---
title: AppLayout
sidebar_position: 5
description: >-
  Build dashboards and admin shells with the AppLayout component, providing a
  fixed header, footer, sliding drawer, and scrollable content area.
_i18n_hash: 4e36be1fd55d6c17c810ace6ef3f1fd8
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

Die `AppLayout`-Komponente bietet Ihnen eine gebrauchsfertige Seitenstruktur mit einem festen Header und Footer, einem Schrank, der hinein- und herausgleitet, sowie einem scrollbaren Inhaltsbereich. Zusammen decken diese Abschnitte die Layoutbedürfnisse von Dashboards, Admin-Panels und den meisten mehrteiligen Schnittstellen ab.

<!-- INTRO_END -->

## Funktionen {#features}

Das webforJ App Layout ist eine Komponente, die es ermöglicht, gängige App-Layouts zu erstellen.

<ul>
    <li>Einfach zu bedienen und anzupassen</li>
    <li>Responsives Design</li>
    <li>Verschiedene Layout-Optionen</li>
    <li>Kompatibel mit dem webforJ Dunkelmodus</li>
</ul>

Es bietet einen Header, Footer, Schrank und einen Inhaltsbereich, die alle in eine responsive Komponente integriert sind, die leicht angepasst werden kann, um schnell gängige App-Layouts wie ein Dashboard zu erstellen. Der Header und Footer sind fixiert, der Schrank gleitet in und aus dem Ansichtsfenster und der Inhalt ist scrollbar.

Jeder Teil des Layouts ist ein `Div`, das jede gültige webforJ-Steuerung enthalten kann. Für beste Ergebnisse sollte die App ein Viewport-Meta-Tag enthalten, das viewport-fit=cover enthält. Das Meta-Tag bewirkt, dass das Viewport an die Anzeige des Geräts skaliert wird.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Übersicht {#overview}

Das folgende Codebeispiel ergibt eine App mit einer einklappbaren Seitenleiste, die ein Logo und Registerkarten für verschiedene Inhaltsoptionen sowie einen Header enthält. Die Demo verwendet die webkomponente dwc-icon-button, um einen Schrank-Umschaltknopf zu erstellen. Der Knopf hat das Attribut data-drawer-toggle, das den DwcAppLayout anweist, auf Klickereignisse von dieser Komponente zu hören, um den Schrankstatus umzuschalten.

<!--vale off-->
<ComponentDemo
path='/webforj/applayout/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Vollbreite Navbar {#full-width-navbar}

Standardmäßig rendert das AppLayout den Header und den Footer im Offscreen-Modus. Der Offscreen-Modus bedeutet, dass die Position von Header und Footer verschoben wird, um neben dem geöffneten Schrank zu passen. Das Deaktivieren dieses Modus führt dazu, dass Header und Footer den gesamten verfügbaren Platz einnehmen und die Position von Schrank oben und unten verschoben wird, um mit dem Header und Footer übereinzustimmen.

```java showLineNumbers
AppLayout myApp = new AppLayout();

myApp.setHeaderOffscreen(false);
myApp.setFooterOffscreen(false);
```

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutfullnavbar/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarView.java',
  'src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Mehrere Werkzeugleisten {#multiple-toolbars}

Die Navbar hat keine Begrenzung für die Anzahl der Werkzeugleisten, die Sie hinzufügen können. Eine `Toolbar` ist eine horizontale Containerkomponente, die eine Reihe von Aktionsschaltflächen, Symbolen oder anderen Steuerelementen enthält. Um eine zusätzliche Werkzeugleiste hinzuzufügen, verwenden Sie einfach die Methode `addToHeader()`, um eine weitere `Toolbar`-Komponente hinzuzufügen.

Die folgende Demo zeigt, wie man zwei Werkzeugleisten verwendet. Die erste beherbergt den Schrank-Umschaltknopf und den Titel der App. Die zweite Werkzeugleiste enthält ein sekundäres Navigationsmenü.

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutmultipleheaders/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeaderContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Sticky Werkzeugleisten {#sticky-toolbars}

Eine sticky Werkzeugleiste ist eine Werkzeugleiste, die oben auf der Seite sichtbar bleibt, wenn der Benutzer nach unten scrollt, während die Höhe der Navbar zusammengeklappt wird, um mehr Platz für den Seiteninhalt zu schaffen. Normalerweise enthält diese Art von Werkzeugleiste ein festes Navigationsmenü, das für die aktuelle Seite relevant ist.

Es ist möglich, sticky Werkzeugleisten mit der CSS-Custom-Property `--dwc-app-layout-header-collapse-height` und der Option `AppLayout.setHeaderReveal()` zu erstellen.

Wenn `AppLayout.setHeaderReveal(true)` aufgerufen wird, wird der Header beim ersten Rendern sichtbar und dann ausgeblendet, wenn der Benutzer beginnt, nach unten zu scrollen. Sobald der Benutzer wieder nach oben scrollt, wird der Header wieder angezeigt.

Mit Hilfe der CSS-Custom-Property `--dwc-app-layout-header-collapse-height` kann gesteuert werden, wie viel von der Header-Navi ausgeblendet wird.

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutstickytoolbar/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Mobiles Navigationslayout {#mobile-navigation-layout}

Die untere Navbar kann verwendet werden, um eine andere Version der Navigation am unteren Ende der App bereitzustellen. Diese Art der Navigation ist insbesondere in mobilen Apps beliebt.

Beachten Sie, wie der Schrank in der folgenden Demo ausgeblendet ist. Das AppLayout-Widget unterstützt drei Schrankpositionen: `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT` und `DrawerPlacement.HIDDEN`.

Ähnlich wie `AppLayout.setHeaderReveal()` wird auch `AppLayout.setFooterReveal()` unterstützt. Wenn `AppLayout.setFooterReveal(true)` aufgerufen wird, wird der Footer beim ersten Rendern sichtbar und dann ausgeblendet, wenn der Benutzer beginnt, nach oben zu scrollen. Sobald der Benutzer wieder nach unten scrollt, wird der Footer wieder angezeigt.

Standardmäßig wird der Schrank bei einer Bildschirmbreite von 800px oder weniger in den Popover-Modus geschaltet. Dies wird als Breakpoint bezeichnet. Der Popover-Modus bedeutet, dass der Schrank über den Inhaltsbereich mit einer Überlagerung poppt. Es ist möglich, den Breakpoint mit der Methode `setDrawerBreakpoint()` zu konfigurieren, und der Breakpoint muss eine gültige [Media Query](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries) sein.

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutmobiledrawer/'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Schrankdienstprogramme {#drawer-utilities}

Die `AppLayout`-Schrankdienstprogramme sind für die integrierte Navigation und kontextbezogene Menüs innerhalb des Haupt-App-Layouts ausgelegt, während eigenständige [`Drawer`](https://docs.webforj.com/docs/components/drawer) Komponenten flexible, unabhängige Schiebepanels bieten, die überall in Ihrer App für zusätzliche Inhalte, Filter oder Benachrichtigungen verwendet werden können. Dieser Abschnitt konzentriert sich auf die integrierten Schrankfunktionen und -dienstprogramme, die von AppLayout bereitgestellt werden.

### Schrank-Breakpoint {#drawer-breakpoint}

Standardmäßig wird der Schrank bei einer Bildschirmbreite von 800px oder weniger in den Popover-Modus geschaltet. Dies wird als Breakpoint bezeichnet. Der Popover-Modus bedeutet, dass der Schrank über den Inhaltsbereich mit einer Überlagerung poppt. Es ist möglich, den Breakpoint mit der Methode `setDrawerBreakpoint()` zu konfigurieren, und der Breakpoint muss eine gültige Media Query sein.

Zum Beispiel wird im folgenden Beispiel der Schrank-Breakpoint auf 500px oder weniger konfiguriert.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### Schranktitel {#drawer-title}

Die `AppLayout`-Komponente bietet eine Methode `addToDrawerTitle()`, um einen benutzerdefinierten Titel im Schrankheader anzuzeigen.

```java
layout.addToDrawerTitle(new Div("Menü"));
```

### Schrankaktionen {#drawer-actions}

Die `AppLayout`-Komponente ermöglicht es Ihnen, benutzerdefinierte Komponenten wie Schaltflächen oder Symbole in den **Schrankheader-Aktionsbereich** mithilfe der Methode `addToDrawerHeaderActions()` zu platzieren.

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

Schrankaktionen erscheinen im **rechtsbündigen Abschnitt** des Headers des Schranks.

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutdrawerutility/content/Dashboard/'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->


## `AppDrawerToggle` <DocChip chip='name' label="dwc-icon-button" /> <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

Die [`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) Komponente ist eine serverseitige webforJ-Klasse, die einen Knopf darstellt, der verwendet wird, um die Sichtbarkeit eines Navigationsschranks in einem `AppLayout` umzuschalten. Es entspricht dem clientseitigen `<dwc-app-drawer-toggle>`-Element und ist standardmäßig so gestaltet, dass es wie ein traditionelles Hamburger-Symbol funktioniert. Dieses Verhalten kann angepasst werden.

### Übersicht {#overview-1}

Der `AppDrawerToggle` erweitert `IconButton` und verwendet standardmäßig das "menu-2"-Symbol aus dem Tabler-Symbolsatz. Es wird automatisch das `data-drawer-toggle`-Attribut angewendet, um sich in das clientseitige Verhalten des Schranks zu integrieren.

```java
// Keine Ereignisregistrierung erforderlich:
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// Der Schrank-Umschalter funktioniert sofort – keine manuellen Ereignislistener erforderlich.
```
## Stilgebung {#styling}

<TableBuilder name="AppLayout" />
