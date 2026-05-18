---
title: AppLayout
sidebar_position: 5
_i18n_hash: 07c685c4fce66e48d5a4e6660b7bc991
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

Die `AppLayout`-Komponente bietet eine vorgefertigte Seitenstruktur mit einem festen Header und Footer, einem Schubladenelement, das ein- und ausgeblendet wird, sowie einem scrollbaren Inhaltsbereich. Zusammen decken diese Abschnitte die Layout-Anforderungen von Dashboards, Administrationspanels und den meisten mehrteiligen Schnittstellen ab.

<!-- INTRO_END -->

## Funktionen {#features}

Das webforJ App Layout ist eine Komponente, die den Aufbau gängiger App-Layouts ermöglicht.

<ul>
    <li>Einfach zu verwenden und anzupassen</li>
    <li>Reaktionsschnelles Design</li>
    <li>Verschiedene Layout-Optionen</li>
    <li>Funktioniert mit dem webforJ Dunkelmodus</li>
</ul>

Es bietet einen Header, Footer, eine Schublade und einen Inhaltsbereich, die alle in eine reaktionsschnelle Komponente integriert sind, die leicht angepasst werden kann, um gängige App-Layouts wie ein Dashboard schnell zu erstellen. Der Header und der Footer sind fixiert, die Schublade gleitet in den Viewport hinein und heraus, und der Inhalt ist scrollable.

Jeder Teil des Layouts ist ein `Div`, der jede gültige webforJ-Steuerung enthalten kann. Für die besten Ergebnisse sollte die App ein Viewport-Meta-Tag enthalten, das viewport-fit=cover hat. Das Meta-Tag sorgt dafür, dass der Viewport so skaliert wird, dass er den Bildschirm des Geräts ausfüllt.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Übersicht {#overview}

Der folgende Codeausschnitt führt zu einer App mit einer einklappbaren Seitenleiste, die ein Logo und Registerkarten für verschiedene Inhaltsoptionen sowie einen Header enthält. Die Demo verwendet die web-Komponente dwc-icon-button, um eine Schubladenumschaltfläche zu erstellen. Die Schaltfläche hat das Attribut data-drawer-toggle, das den DwcAppLayout anweist, auf Klickereignisse von dieser Komponente zu hören, um den Zustand der Schublade umzuschalten.

<!--vale off-->
<ComponentDemo
path='/webforj/applayout/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/resources/static/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Vollbild-Navigationsleiste {#full-width-navbar}

Standardmäßig rendert das AppLayout den Header und den Footer im Off-Screen-Modus. Der Off-Screen-Modus bedeutet, dass die Position von Header und Footer verschoben wird, um neben der geöffneten Schublade zu passen. Das Deaktivieren dieses Modus führt dazu, dass Header und Footer den gesamten verfügbaren Platz einnehmen und die oberen und unteren Positionen der Schublade angepasst werden, um an Header und Footer anzupassen.

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
  'src/main/resources/static/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Mehrere Symbolleisten {#multiple-toolbars}

Die Navigationsleiste hat keine Begrenzung für die Anzahl der Symbolleisten, die Sie hinzufügen können. Eine `Toolbar` ist eine horizontale Containerkomponente, die eine Vielzahl von Aktionsschaltflächen, Symbolen oder anderen Steuerelementen hält. Um eine zusätzliche Symbolleiste hinzuzufügen, verwenden Sie einfach die Methode `addToHeader()`, um eine weitere `Toolbar`-Komponente hinzuzufügen.

Die folgende Demo zeigt, wie zwei Symbolleisten verwendet werden. Die erste beherbergt die Schubladenumschaltfläche und den Titel der App. Die zweite Symbolleiste beherbergt ein sekundäres Navigationsmenü.

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutmultipleheaders/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeaderContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/resources/static/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Anheftbare Symbolleisten {#sticky-toolbars}

Eine anheftbare Symbolleiste ist eine Symbolleiste, die oben auf der Seite sichtbar bleibt, wenn der Benutzer nach unten scrollt, aber die Höhe der Navigationsleiste wird reduziert, um mehr Platz für den Inhalt der Seite zu schaffen. Üblicherweise enthält diese Art von Symbolleiste ein festes Navigationsmenü, das für die aktuelle Seite relevant ist.

Es ist möglich, anheftbare Symbolleisten zu erstellen, indem die CSS-Custom-Property `--dwc-app-layout-header-collapse-height` und die Option `AppLayout.setHeaderReveal()` verwendet werden.

Wenn `AppLayout.setHeaderReveal(true)` aufgerufen wird, ist der Header bei der ersten Darstellung sichtbar und wird dann ausgeblendet, wenn der Benutzer zu scrollen beginnt. Sobald der Benutzer wieder nach oben scrollt, wird der Header wieder angezeigt.

Mit Hilfe der CSS-Custom-Property `--dwc-app-layout-header-collapse-height` ist es möglich zu kontrollieren, wie viel von der Header-Navigationsleiste verborgen wird.

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutstickytoolbar/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/resources/static/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Mobiles Navigationslayout {#mobile-navigation-layout}

Die untere Navigationsleiste kann verwendet werden, um eine andere Version der Navigation am unteren Rand der App bereitzustellen. Diese Art der Navigation ist insbesondere in mobilen Apps beliebt.

Beachten Sie, wie die Schublade in der folgenden Demo verborgen ist. Das AppLayout-Widget unterstützt drei Schubladenpositionen: `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT` und `DrawerPlacement.HIDDEN`.

Ebenso wie `AppLayout.setHeaderReveal()` wird auch `AppLayout.setFooterReveal()` unterstützt. Wenn `AppLayout.setFooterReveal(true)` aufgerufen wird, ist der Footer bei der ersten Darstellung sichtbar und wird dann ausgeblendet, wenn der Benutzer nach oben scrollt. Sobald der Benutzer wieder nach unten scrollt, wird der Footer wieder angezeigt.

Standardmäßig wird die Schublade auf Popover-Modus umgeschaltet, wenn die Bildschirmbreite 800px oder weniger beträgt. Dies wird als Breakpoint bezeichnet. Der Popover-Modus bedeutet, dass die Schublade über den Inhaltsbereich mit einem Overlay poppt. Es ist möglich, den Breakpoint zu konfigurieren, indem die Methode `setDrawerBreakpoint()` verwendet wird, und der Breakpoint muss eine gültige [Media Query](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries) sein.

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutmobiledrawer/'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/resources/static/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Schubladendienstprogramme {#drawer-utilities}

Die `AppLayout`-Schubladendienstprogramme sind für integrierte Navigation und kontextuelle Menüs innerhalb des Haupt-App-Layouts konzipiert, während eigenständige [`Drawer`](https://docs.webforj.com/docs/components/drawer)-Komponenten flexible, unabhängige Schiebetafeln anbieten, die an beliebiger Stelle in Ihrer App für zusätzlichen Inhalt, Filter oder Benachrichtigungen verwendet werden können. Dieser Abschnitt konzentriert sich auf die eingebauten Schubladenmerkmale und -dienstprogramme, die von AppLayout bereitgestellt werden.

### Schubladen-Breakpoint {#drawer-breakpoint}

Standardmäßig wird die Schublade auf Popover-Modus umgeschaltet, wenn die Bildschirmbreite 800px oder weniger beträgt. Dies wird als Breakpoint bezeichnet. Der Popover-Modus bedeutet, dass die Schublade über den Inhaltsbereich mit einem Overlay poppt. Es ist möglich, den Breakpoint zu konfigurieren, indem die Methode `setDrawerBreakpoint()` verwendet wird, und der Breakpoint muss eine gültige Medienabfrage sein.

Im folgenden Beispiel wird der Schubladen-Breakpoint auf 500px oder weniger konfiguriert.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### Schubladentitel {#drawer-title}

Die `AppLayout`-Komponente bietet eine Methode `addToDrawerTitle()`, um einen benutzerdefinierten Titel zu definieren, der im Schubladen-Header angezeigt werden soll.

```java
layout.addToDrawerTitle(new Div("Menü"));
```

### Schubladenaktionen {#drawer-actions}

Die `AppLayout`-Komponente erlaubt es Ihnen, benutzerdefinierte Komponenten wie Schaltflächen oder Symbole in den **Aktionen des Schubladen-Headers** unter Verwendung der Methode `addToDrawerHeaderActions()` zu platzieren.

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

Die Schubladenaktionen erscheinen im **rechtsbündigen Bereich** des Schubladen-Headers.

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutdrawerutility/content/Dashboard/'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/resources/static/css/applayout/applayout.css',
]}
/>
<!--vale on-->


## `AppDrawerToggle` <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

Die [`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html)-Komponente ist eine serverseitige webforJ-Klasse, die eine Schaltfläche darstellt, die verwendet wird, um die Sichtbarkeit eines Navigations-Dockers in einem `AppLayout` umzuschalten. Sie entspricht dem Client-seitigen `<dwc-app-drawer-toggle>`-Element und ist standardmäßig so gestaltet, dass es sich wie ein traditionelles Hamburger-Menü-Symbol verhält, dieses Verhalten kann angepasst werden.

### Übersicht {#overview-1}

Der `AppDrawerToggle` erweitert `IconButton` und verwendet standardmäßig das "menu-2"-Symbol aus dem Tabler-Symbolsatz. Es wird automatisch das Attribut `data-drawer-toggle` angewendet, um sich in das Client-seitige Schubladenverhalten zu integrieren.

```java
// Keine Ereignisanmeldung erforderlich:
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// Der Schubladenumschalter funktioniert sofort—keine manuellen Ereignis-Listener benötigt.
```
## Stil {#styling}

<TableBuilder name="AppLayout" />
