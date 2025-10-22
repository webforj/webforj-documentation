---
title: AppLayout
sidebar_position: 5
_i18n_hash: 7bc8b2a8bfc772644cf2107199615515
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

Der `AppLayout` ist eine umfassende responsive Layout-Komponente, die einen Header, einen Footer, eine Schublade und einen Inhaltsbereich bietet. Der Header und der Footer sind fixiert, die Schublade fährt in und aus dem Ansichtsbereich, und der Inhalt ist scrollbar.

Diese Komponente kann verwendet werden, um häufige App-Layouts zu erstellen, wie z.B. ein Dashboard.

## Funktionen {#features}

Das webforJ App Layout ist eine Komponente, die den Aufbau gängiger App-Layouts ermöglicht.

<ul>
    <li>Einfach zu verwenden und anzupassen</li>
    <li>Responsive Design</li>
    <li>Mehrere Layout-Optionen</li>
    <li>Funktioniert mit dem webforJ Dunkelmodus</li>
</ul>

Es bietet einen Header, einen Footer, eine Schublade und einen Inhaltsbereich, die alle in eine responsive Komponente integriert sind, die leicht angepasst werden kann, um schnell gängige App-Layouts wie ein Dashboard zu erstellen. Der Header und der Footer sind fixiert, die Schublade fährt in und aus dem Ansichtsbereich, und der Inhalt ist scrollbar.

Jeder Teil des Layouts ist ein `Div`, der jede gültige webforJ-Komponente enthalten kann. Für beste Ergebnisse sollte die App ein Viewport-Meta-Tag enthalten, das viewport-fit=cover hat. Das Meta-Tag sorgt dafür, dass der Viewport skaliert wird, um die Anzeige des Geräts auszufüllen.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Übersicht {#overview}

Der folgende Code-Schnipsel führt zu einer App mit einer einklappbaren Seitenleiste, die ein Logo und Registerkarten für verschiedene Inhaltsoptionen sowie einen Header enthält. Die Demo verwendet die webkomponente dwc-icon-button, um einen Schubladenumschalter zu erstellen. Der Button hat das Attribut data-drawer-toggle, das den DwcAppLayout anweist, auf Klickereignisse von dieser Komponente zu hören, um den Zustand der Schublade umzuschalten.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayout/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Vollbreite-Navigationsleiste {#full-width-navbar}

Standardmäßig rendert das AppLayout den Header und den Footer im Offscreen-Modus. Der Offscreen-Modus bedeutet, dass die Position von Header und Footer verschoben wird, um neben der geöffneten Schublade zu passen. Das Deaktivieren dieses Modus führt dazu, dass der Header und der Footer den gesamten verfügbaren Raum einnehmen und die obere und untere Position der Schublade an Header und Footer angepasst werden.

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

Die Navigationsleiste hat keine Einschränkung bezüglich der Anzahl von Werkzeugleisten, die Sie hinzufügen können. Eine `Toolbar` ist eine horizontale Containerkomponente, die eine Reihe von Aktionsknöpfen, Symbolen oder anderen Steuerelementen hält. Um eine zusätzliche Werkzeugleiste hinzuzufügen, verwenden Sie einfach die Methode `addToHeader()`, um eine weitere `Toolbar`-Komponente hinzuzufügen.

Die folgende Demo zeigt, wie man zwei Werkzeugleisten verwendet. Die erste beherbergt den Umschalter für die Schublade und den Titel der App. Die zweite Werkzeugleiste beherbergt ein sekundäres Navigationsmenü.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutmultipleheaders/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeaderContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Sticky Werkzeugleisten {#sticky-toolbars}

Eine Sticky-Werkzeugleiste ist eine Werkzeugleiste, die oben auf der Seite sichtbar bleibt, wenn der Benutzer nach unten scrollt, während die Höhe der Navigationsleiste verringert wird, um mehr Platz für die Seiteninhalte freizugeben. In der Regel enthält diese Art von Werkzeugleiste ein fixes Navigationsmenü, das für die aktuelle Seite relevant ist.

Es ist möglich, Sticky-Werkzeugleisten mit der CSS-Anpassungsvariable `--dwc-app-layout-header-collapse-height` und der Option `AppLayout.setHeaderReveal()` zu erstellen.

Wenn `AppLayout.setHeaderReveal(true)` gesetzt wird, ist der Header beim ersten Rendern sichtbar und wird dann ausgeblendet, wenn der Benutzer beginnt, nach unten zu scrollen. Sobald der Benutzer wieder nach oben scrollt, wird der Header wieder angezeigt.

Mit Hilfe der CSS-Anpassungsvariable `--dwc-app-layout-header-collapse-height` ist es möglich, zu steuern, wie viel von der Header-Navigationsleiste ausgeblendet wird.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutstickytoolbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Mobile Navigationslayout {#mobile-navigation-layout}

Die untere Navigationsleiste kann verwendet werden, um eine andere Version der Navigation am unteren Rand der App bereitzustellen. Diese Art der Navigation ist besonders beliebt in mobilen Apps.

Beachten Sie, dass die Schublade in der folgenden Demo ausgeblendet ist. Das AppLayout-Widget unterstützt drei Schubladenpositionen: `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT` und `DrawerPlacement.HIDDEN`.

Ähnlich wie `AppLayout.setHeaderReveal()`, wird auch `AppLayout.setFooterReveal()` unterstützt. Wenn `AppLayout.setFooterReveal(true)` aufgerufen wird, ist der Footer beim ersten Rendern sichtbar und wird dann ausgeblendet, wenn der Benutzer beginnt, nach oben zu scrollen. Sobald der Benutzer wieder nach unten scrollt, wird der Footer wieder angezeigt.

Standardmäßig, wenn die Bildschirmbreite 800px oder weniger beträgt, wechselt die Schublade in den Popover-Modus. Dies wird als Breakpoint bezeichnet. Der Popover-Modus bedeutet, dass die Schublade über den Inhaltsbereich mit einer Überlagerung erscheint. Es ist möglich, den Breakpoint mit der Methode `setDrawerBreakpoint()` zu konfigurieren, und der Breakpoint muss eine gültige [Medienabfrage](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries) sein.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutmobiledrawer/?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Schubladenutilities {#drawer-utilities}

Die Schubladenutilities des `AppLayout` sind für die integrierte Navigation und kontextbezogene Menüs innerhalb des Haupt-App-Layouts konzipiert, während eigenständige [`Drawer`](https://docs.webforj.com/docs/components/drawer) Komponenten flexible, unabhängige Schiebepanele bieten, die überall in Ihrer App für zusätzliche Inhalte, Filter oder Benachrichtigungen verwendet werden können. Dieser Abschnitt konzentriert sich auf die integrierten Schubladenfunktionen und -utilities, die vom AppLayout bereitgestellt werden.

### Schubladen-Breakpoint {#drawer-breakpoint}

Standardmäßig wechselt die Schublade, wenn die Bildschirmbreite 800px oder weniger beträgt, in den Popover-Modus. Dies wird als Breakpoint bezeichnet. Der Popover-Modus bedeutet, dass die Schublade über den Inhaltsbereich mit einer Überlagerung erscheint. Es ist möglich, den Breakpoint mit der Methode `setDrawerBreakpoint()` zu konfigurieren, und der Breakpoint muss eine gültige Medienabfrage sein.

Zum Beispiel wird im folgenden Beispiel der Schubladen-Breakpoint auf 500px oder weniger konfiguriert.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### Schubladentitel {#drawer-title}

Die `AppLayout`-Komponente bietet eine Methode `addToDrawerTitle()`, um einen benutzerdefinierten Titel zu definieren, der im Schubladenkopf angezeigt werden soll.

```java
layout.addToDrawerTitle(new Div("Menü"));
```

### Schubladenaktionen {#drawer-actions}

Die `AppLayout`-Komponente ermöglicht es Ihnen, benutzerdefinierte Komponenten wie Schaltflächen oder Symbole in den **Actions-Bereich des Schubladenkopfes** mit der Methode `addToDrawerHeaderActions()` einzufügen.

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

Die Schubladenaktionen erscheinen im **rechtsbündigen Bereich** des Schubladenkopfes.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutdrawerutility/content/Dashboard/?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## `AppDrawerToggle` <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

Die [`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) Komponente ist eine serverseitige webforJ-Klasse, die eine Schaltfläche repräsentiert, die zur Umschaltung der Sichtbarkeit einer Navigationsschublade in einem `AppLayout` verwendet wird. Sie entspricht dem clientseitigen `<dwc-app-drawer-toggle>`-Element und ist standardmäßig so gestaltet, dass sie sich wie ein traditionelles Hamburger-Menüsymbol verhält. Dieses Verhalten kann angepasst werden.

### Übersicht {#overview-1}

Die `AppDrawerToggle` erweitert `IconButton` und verwendet standardmäßig das "menu-2"-Symbol aus dem Tabler-Icon-Set. Es wendet automatisch das Attribut `data-drawer-toggle` an, um sich mit dem clientseitigen Schubladenverhalten zu integrieren.

```java
// Keine Ereignisregistrierung erforderlich:
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// Der Schubladenumschalter funktioniert direkt – keine manuellen Ereignislistener erforderlich.
```
## Stil {#styling}

<TableBuilder name="AppLayout" />
