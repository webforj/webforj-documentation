---
title: AppLayout
sidebar_position: 5
_i18n_hash: 0aea09dee535e578082dd6df642503d4
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

Die `AppLayout`-Komponente bietet Ihnen eine fertige Seitenstruktur mit einem fixen Header und Footer, einem Drawer, der hinein- und herausgleitet, sowie einem scrollbaren Inhaltsbereich. Zusammen decken diese Abschnitte die Layout-Bedürfnisse von Dashboards, Administrationspanels und den meisten mehrteiligen Schnittstellen ab.

<!-- INTRO_END -->

## Funktionen {#features}

Das webforJ App Layout ist eine Komponente, die den Bau gängiger App-Layouts ermöglicht.

<ul>
    <li>Einfache Verwendung und Anpassung</li>
    <li>Responsives Design</li>
    <li>Mehrere Layout-Optionen</li>
    <li>Funktioniert mit dem webforJ Dunkelmodus</li>
</ul>

Es stellt einen Header, Footer, Drawer und einen Inhaltsbereich bereit, die alle in eine reaktive Komponente integriert sind, die leicht angepasst werden kann, um gängige App-Layouts wie ein Dashboard schnell zu erstellen. Der Header und der Footer sind fixiert, der Drawer gleitet in und aus dem Ansichtsfenster, und der Inhalt ist scrollbar.

Jeder Teil des Layouts ist ein `Div`, der jede gültige webforJ-Kontrolle enthalten kann. Für die besten Ergebnisse sollte die App ein Viewport-Meta-Tag enthalten, das viewport-fit=cover enthält. Das Meta-Tag sorgt dafür, dass der Viewport skaliert wird, um den Bildschirm des Geräts auszufüllen.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Übersicht {#overview}

Der folgende Code-Schnipsel ergibt eine App mit einer ausklappbaren Seitenleiste, die ein Logo und Registerkarten für verschiedene Inhaltsoptionen sowie einen Header enthält. Die Demo verwendet die web-Komponente dwc-icon-button, um eine Schaltfläche zum Umschalten des Drawers zu erstellen. Die Schaltfläche hat das Attribut data-drawer-toggle, das den DwcAppLayout anweist, auf Klickereignisse von dieser Komponente zu hören, um den Zustand des Drawers umzuschalten.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayout/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Vollbreite-Navigationsleiste {#full-width-navbar}

Standardmäßig rendert das AppLayout den Header und den Footer im Off-Screen-Modus. Der Off-Screen-Modus bedeutet, dass die Position des Headers und des Footers verschoben wird, um neben dem geöffneten Drawer zu passen. Das Deaktivieren dieses Modus führt dazu, dass der Header und der Footer den gesamten verfügbaren Raum einnehmen und die oberen und unteren Positionen des Drawers verschoben werden, um mit dem Header und dem Footer zu harmonieren.

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

Die Navigationsleiste hat keine Begrenzung für die Anzahl der Werkzeugleisten, die Sie hinzufügen können. Eine `Toolbar` ist eine horizontale Containerkomponente, die eine Reihe von Aktionsschaltflächen, Symbolen oder anderen Steuerelementen enthält. Um eine zusätzliche Werkzeugleiste hinzuzufügen, verwenden Sie einfach die Methode `addToHeader()`, um eine weitere `Toolbar`-Komponente hinzuzufügen.

Die folgende Demo zeigt, wie man zwei Werkzeugleisten verwendet. Die erste enthält die Umschalttaste des Drawers und den Titel der App. Die zweite Werkzeugleiste enthält ein sekundäres Navigationsmenü.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutmultipleheaders/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeaderContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Sticky-Werkzeugleisten {#sticky-toolbars}

Eine Sticky-Werkzeugleiste ist eine Werkzeugleiste, die am oberen Rand der Seite sichtbar bleibt, wenn der Benutzer nach unten scrollt, während die Höhe der Navigationsleiste zusammengeklappt wird, um mehr Platz für den Seiteninhalt zu schaffen. In der Regel enthält diese Art von Werkzeugleiste ein festes Navigationsmenü, das für die aktuelle Seite relevant ist.

Es ist möglich, Sticky-Werkzeugleisten mithilfe der CSS-Custom-Property `--dwc-app-layout-header-collapse-height` und der Option `AppLayout.setHeaderReveal()` zu erstellen.

Wenn `AppLayout.setHeaderReveal(true)` aufgerufen wird, ist der Header beim ersten Rendern sichtbar und wird ausgeblendet, wenn der Benutzer nach unten zu scrollen beginnt. Sobald der Benutzer wieder nach oben zu scrollen beginnt, wird der Header angezeigt.

Mit Hilfe der CSS-Custom-Property `--dwc-app-layout-header-collapse-height` ist es möglich, zu steuern, wie viel von der Header-Navigationsleiste ausgeblendet wird.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutstickytoolbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Mobile-Navigationslayout {#mobile-navigation-layout}

Die untere Navigationsleiste kann verwendet werden, um eine andere Version der Navigation am unteren Rand der App bereitzustellen. Diese Art von Navigation ist besonders in mobilen Apps beliebt.

Beachten Sie, wie der Drawer in der folgenden Demo ausgeblendet ist. Das AppLayout-Widget unterstützt drei Drawer-Positionen: `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT` und `DrawerPlacement.HIDDEN`.

Genauso wie `AppLayout.setHeaderReveal()` wird auch `AppLayout.setFooterReveal()` unterstützt. Wenn `AppLayout.setFooterReveal(true)` aufgerufen wird, ist der Footer beim ersten Rendern sichtbar und wird ausgeblendet, wenn der Benutzer nach oben zu scrollen beginnt. Sobald der Benutzer wieder nach unten zu scrollen beginnt, wird der Footer angezeigt.

Standardmäßig wird der Drawer bei einer Bildschirmbreite von 800 px oder weniger in den Popover-Modus gewechselt. Dies wird als Breakpoint bezeichnet. Der Popover-Modus bedeutet, dass der Drawer mit einer Überlagerung über den Inhaltsbereich poppen wird. Es ist möglich, den Breakpoint mithilfe der Methode `setDrawerBreakpoint()` zu konfigurieren, und der Breakpoint muss eine gültige [Media Query](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries) sein.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutmobiledrawer/?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Drawer-Dienstprogramme {#drawer-utilities}

Die `AppLayout`-Drawer-Dienstprogramme sind für integrierte Navigation und kontextuelle Menüs innerhalb des Haupt-App-Layouts konzipiert, während eigenständige [`Drawer`](https://docs.webforj.com/docs/components/drawer) Komponenten flexible, unabhängige Gleitschranken bieten, die überall in Ihrer App für zusätzliche Inhalte, Filter oder Benachrichtigungen verwendet werden können. Dieser Abschnitt konzentriert sich auf die integrierten Drawer-Funktionen und -Dienste, die von AppLayout bereitgestellt werden.

### Drawer-Breakpoint {#drawer-breakpoint}

Standardmäßig wird der Drawer bei einer Bildschirmbreite von 800 px oder weniger in den Popover-Modus gewechselt. Dies wird als Breakpoint bezeichnet. Der Popover-Modus bedeutet, dass der Drawer mit einer Überlagerung über den Inhaltsbereich poppen wird. Es ist möglich, den Breakpoint mithilfe der Methode `setDrawerBreakpoint()` zu konfigurieren, und der Breakpoint muss eine gültige Media Query sein.

Im Folgenden wird zum Beispiel der Drawer-Breakpoint so konfiguriert, dass er 500 px oder weniger beträgt.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### Drawer-Titel {#drawer-title}

Die `AppLayout`-Komponente bietet eine Methode `addToDrawerTitle()`, um einen benutzerdefinierten Titel für die Anzeige im Drawer-Header festzulegen. 

```java
layout.addToDrawerTitle(new Div("Menü"));
```

### Drawer-Aktionen {#drawer-actions}

Die `AppLayout`-Komponente ermöglicht es Ihnen, benutzerdefinierte Komponenten wie Schaltflächen oder Symbole in den **Aktionen des Drawer-Headers** mithilfe der Methode `addToDrawerHeaderActions()` zu platzieren.

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
<AppLayoutViewer path='/webforj/applayoutdrawerutility/content/Dashboard/?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->


## `AppDrawerToggle` <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

Die [`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) Komponente ist eine serverseitige webforJ-Klasse, die eine Schaltfläche darstellt, die die Sichtbarkeit eines Navigationsdrawers in einem `AppLayout` umschaltet. Sie entspricht dem Client-seitigen `<dwc-app-drawer-toggle>`-Element und ist standardmäßig so gestaltet, dass sie sich wie ein traditionelles Hamburger-Menü-Icon verhält; dieses Verhalten kann angepasst werden.

### Überblick {#overview-1}

Die `AppDrawerToggle` erweitert `IconButton` und verwendet standardmäßig das "menu-2"-Symbol aus dem Tabler-Iconsatz. Es wendet automatisch das Attribut `data-drawer-toggle` an, um sich mit dem Client-seitigen Drawer-Verhalten zu integrieren.

```java
// Keine Ereignisanmeldung erforderlich:
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// Der Drawer-Umschalter funktioniert sofort – keine manuellen Ereignislistener erforderlich.
```
## Stil {#styling}

<TableBuilder name="AppLayout" />
