---
title: AppLayout
sidebar_position: 5
description: >-
  Build dashboards and admin shells with the AppLayout component, providing a
  fixed header, footer, sliding drawer, and scrollable content area.
_i18n_hash: 559d0c63a8e61e2e3d79086aa08922c1
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

Die `AppLayout`-Komponente bietet Ihnen eine fertige Seitenstruktur mit einem festen Header und Footer, einem Drawer, der ein- und ausgeblendet werden kann, und einem scrollbaren Inhaltsbereich. Zusammen decken diese Abschnitte die Layout-Anforderungen für Dashboards, Administrationspanels und die meisten mehrteiligen Benutzeroberflächen ab.

<!-- INTRO_END -->

## Funktionen {#features}

Das webforJ App Layout ist eine Komponente, die den Aufbau gängiger App-Layouts ermöglicht.

<ul>
    <li>Einfach zu bedienen und anzupassen</li>
    <li>Responsives Design</li>
    <li>Mehrere Layout-Optionen</li>
    <li>Funktioniert mit dem webforJ Dunkelmodus</li>
</ul>

Es bietet einen Header, einen Footer, einen Drawer und einen Inhaltsbereich, die alle in eine responsive Komponente integriert sind, die leicht angepasst werden kann, um gängige App-Layouts wie ein Dashboard schnell zu erstellen. Der Header und der Footer sind fest, der Drawer gleitet in und aus dem Ansichtsbereich, und der Inhalt ist scrollbar.

Jeder Teil des Layouts ist ein `Div`, der jede gültige webforJ-Steuerung enthalten kann. Für die besten Ergebnisse sollte die App ein Viewport-Meta-Tag enthalten, das viewport-fit=cover enthält. Das Meta-Tag sorgt dafür, dass der Viewport so skaliert wird, dass er den Bildschirm des Geräts ausfüllt.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Überblick {#overview}

Das folgende Codebeispiel führt zu einer App mit einem umklappbaren Sidebar, das ein Logo und Registerkarten für verschiedene Inhaltsoptionen sowie einem Header enthält. Die Demo verwendet die dwc-icon-button-Webkomponente, um einen Schalter für den Drawer zu erstellen. Der Button hat das Attribut data-drawer-toggle, das den DwcAppLayout anweist, auf Klickereignisse von dieser Komponente zu hören, um den Status des Drawers umzuschalten.

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

Standardmäßig rendert das AppLayout den Header und den Footer im Off-Screen-Modus. Der Off-Screen-Modus bedeutet, dass die Position von Header und Footer angepasst wird, um neben dem geöffneten Drawer zu passen. Wenn dieser Modus deaktiviert wird, nehmen der Header und der Footer den gesamten verfügbaren Platz ein und verschieben die obere und untere Position des Drawers, um mit dem Header und dem Footer übereinzustimmen.

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

## Mehrere Toolbars {#multiple-toolbars}

Die Navbar hat keine Einschränkung in der Anzahl der Toolbars, die Sie hinzufügen können. Eine `Toolbar` ist eine horizontale Containerkomponente, die eine Reihe von Aktionsbuttons, Symbolen oder anderen Steuerelementen enthält. Um eine zusätzliche Toolbar hinzuzufügen, verwenden Sie einfach die Methode `addToHeader()`, um eine weitere `Toolbar`-Komponente hinzuzufügen.

Die folgende Demo zeigt, wie man zwei Toolbars verwendet. Die erste enthält den Schalter für den Drawer und den Titel der App. Die zweite Toolbar enthält ein sekundäres Navigationsmenü.

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

## Sticky Toolbars {#sticky-toolbars}

Eine sticky Toolbar ist eine Toolbar, die oben auf der Seite sichtbar bleibt, wenn der Benutzer nach unten scrollt, während die Höhe der Navbar verkleinert wird, um mehr Platz für den Inhalt der Seite verfügbar zu machen. In der Regel enthält diese Art von Toolbar ein festes Navigationsmenü, das für die aktuelle Seite relevant ist.

Es ist möglich, sticky Toolbars mit der CSS-Benutzerdefinierten Eigenschaft `--dwc-app-layout-header-collapse-height` und der Option `AppLayout.setHeaderReveal()` zu erstellen.

Wenn `AppLayout.setHeaderReveal(true)` aufgerufen wird, ist der Header beim ersten Rendern sichtbar und wird dann ausgeblendet, wenn der Benutzer anfängt, nach unten zu scrollen. Sobald der Benutzer wieder nach oben scrollt, wird der Header wieder sichtbar.

Mit Hilfe der CSS-Benutzerdefinierten Eigenschaft `--dwc-app-layout-header-collapse-height` kann gesteuert werden, wie viel von der Header-Navbar ausgeblendet wird.

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutstickytoolbar/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/applayout.css',
]}
/>
<!--vale on-->

## Mobile Navigationslayout {#mobile-navigation-layout}

Die untere Navbar kann verwendet werden, um eine andere Version der Navigation am unteren Rand der App bereitzustellen. Diese Art der Navigation ist besonders in mobilen Apps beliebt.

Beachten Sie, wie der Drawer in der folgenden Demo ausgeblendet ist. Das AppLayout-Widget unterstützt drei Drawer-Positionen: `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT` und `DrawerPlacement.HIDDEN`.

Genauso wie `AppLayout.setHeaderReveal()`, wird `AppLayout.setFooterReveal()` unterstützt. Wenn `AppLayout.setFooterReveal(true)` aufgerufen wird, ist der Footer beim ersten Rendern sichtbar und wird ausgeblendet, wenn der Benutzer anfängt, nach oben zu scrollen. Sobald der Benutzer wieder nach unten scrollt, wird der Footer wieder sichtbar.

Standardmäßig, wenn die Bildschirmbreite 800px oder weniger beträgt, wechselt der Drawer in den Popover-Modus. Dies wird als Breakpoint bezeichnet. Der Popover-Modus bedeutet, dass der Drawer über den Inhaltsbereich mit einem Overlay angezeigt wird. Es ist möglich, den Breakpoint zu konfigurieren, indem Sie die Methode `setDrawerBreakpoint()` verwenden und der Breakpoint muss eine gültige [Media Query](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries) sein.

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

## Drawer-Dienstprogramme {#drawer-utilities}

Die Drawer-Dienstprogramme der `AppLayout` sind für die integrierte Navigation und kontextuelle Menüs innerhalb des Haupt-App-Layouts konzipiert, während Standalone [`Drawer`](https://docs.webforj.com/docs/components/drawer) Komponenten flexible, unabhängige Schiebe-Panels bieten, die überall in Ihrer App für zusätzlichen Inhalt, Filter oder Benachrichtigungen verwendet werden können. Dieser Abschnitt konzentriert sich auf die integrierten Drawer-Funktionen und -Dienstprogramme, die von AppLayout bereitgestellt werden.

### Drawer-Breakpoint {#drawer-breakpoint}

Standardmäßig, wenn die Bildschirmbreite 800px oder weniger beträgt, wechselt der Drawer in den Popover-Modus. Dies wird als Breakpoint bezeichnet. Der Popover-Modus bedeutet, dass der Drawer über den Inhaltsbereich mit einem Overlay angezeigt wird. Es ist möglich, den Breakpoint zu konfigurieren, indem Sie die Methode `setDrawerBreakpoint()` verwenden und der Breakpoint muss eine gültige Media Query sein.

Zum Beispiel wird im folgenden Beispiel der Drawer-Breakpoint auf 500px oder weniger konfiguriert.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### Drawer-Titel {#drawer-title}

Die `AppLayout`-Komponente bietet eine Methode `addToDrawerTitle()`, um einen benutzerdefinierten Titel zu definieren, der in der Drawer-Header angezeigt wird.

```java
layout.addToDrawerTitle(new Div("Menü"));
```

### Drawer-Aktionen {#drawer-actions}

Die `AppLayout`-Komponente ermöglicht es Ihnen, benutzerdefinierte Komponenten wie Buttons oder Icons in den **Aktionen des Drawer-Headers** unter Verwendung der Methode `addToDrawerHeaderActions()` zu platzieren.

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

Drawer-Aktionen erscheinen im **rechtsbündigen Abschnitt** des Headers des Drawers.

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


## `AppDrawerToggle` <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

Die [`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) Komponente ist eine serverseitige webforJ-Klasse, die einen Button darstellt, der verwendet wird, um die Sichtbarkeit eines Navigationsdrawers in einem `AppLayout` umzuschalten. Sie korreliert mit dem clientseitigen `<dwc-app-drawer-toggle>`-Element und ist standardmäßig so gestaltet, dass sie sich wie ein traditionelles Hamburger-Menü-Icon verhält; dieses Verhalten kann angepasst werden.

### Überblick {#overview-1}

Der `AppDrawerToggle` erweitert `IconButton` und verwendet standardmäßig das "menu-2"-Symbol aus dem Tabler-Symbolset. Es wird automatisch das Attribut `data-drawer-toggle` angewendet, um sich mit dem clientseitigen Drawer-Verhalten zu integrieren.

```java
// Keine Ereignisregistrierung erforderlich:
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// Der Drawer-Toggle funktioniert sofort—keine manuellen Ereignis-Listener erforderlich.
```
## Styling {#styling}

<TableBuilder name="AppLayout" />
