---
title: Drawer
sidebar_position: 35
_i18n_hash: 51577f27568214c5d39e43b7e6ce42d0
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

Die `Drawer`-Komponente in webforJ erstellt ein gleitendes Panel, das von der Bildschirmkante erscheint und zusätzlichen Inhalt offenbart, ohne die aktuelle Ansicht zu verlassen. Sie wird häufig für seitliche Navigation, Filtermenüs, Benutzereinstellungen oder kompakte Benachrichtigungen verwendet, die temporär erscheinen müssen, ohne die Hauptoberfläche zu stören.

<!-- INTRO_END -->

## Stacking {#stacking}

Drawers stapeln sich automatisch, wenn mehrere geöffnet sind, was sie zu einer flexiblen Wahl für platzbeschränkte Schnittstellen macht.

Das folgende Beispiel zeigt dieses Verhalten innerhalb der [`AppLayout`](../components/app-layout)-Komponente. Der Navigations-Drawer, der durch das Hamburger-Menü ausgelöst wird, ist in [`AppLayout`](../components/app-layout) integriert, während das Willkommens-Popup am Boden eine eigenständige `Drawer`-Instanz verwendet. Beide existieren nebeneinander und stapeln sich unabhängig, was demonstriert, wie Drawers innerhalb von Layout-Komponenten integriert oder als eigenständige Elemente verwendet werden können.

<AppLayoutViewer path='/webforj/drawerwelcome?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java'
cssURL='/css/drawer/drawerWelcome.css'
/>

## Autofocus

Die `Drawer`-Komponente unterstützt Autofokus, der automatisch den Fokus auf das erste fokussierbare Element setzt, wenn der `Drawer` geöffnet wird. Dies verbessert die Benutzerfreundlichkeit, indem die Aufmerksamkeit direkt auf das erste handlungsorientierte Element gelenkt wird.

<ComponentDemo
path='/webforj/drawerautofocus?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java'
height='600px'
/>

<!-- Beispiel -->

## Label {#label}

Die Methode `setLabel()` kann eine sinnvolle Beschreibung des Inhalts innerhalb eines `Drawer` bereitstellen. Wenn ein Label gesetzt ist, können unterstützende Technologien wie Screenreader es ankündigen, was den Nutzern hilft, den Zweck des `Drawer` zu verstehen, ohne seine visuellen Inhalte zu sehen.

```java
Drawer drawer = new Drawer();
drawer.setLabel("Task Manager");
```

:::tip Beschreibende Labels
Verwenden Sie prägnante und beschreibende Labels, die den Zweck des `Drawer` widerspiegeln. Vermeiden Sie generische Begriffe wie „Menü“ oder „Panel“, wenn ein spezifischerer Name verwendet werden kann.
:::

## Größe

Um die Größe eines `Drawer` zu steuern, setzen Sie einen Wert für die CSS- benutzerdefinierte Eigenschaft `--dwc-drawer-size`. Dies legt die Breite des `Drawer` für die Links/Rechts-Platzierung oder die Höhe für die Oben/Unten-Platzierung fest.

Sie können den Wert mit beliebigen gültigen CSS-Einheiten wie Prozent, Pixel oder vw/vh definieren, entweder mit Java oder CSS:

```java
// Java
drawer.setStyle("--dwc-drawer-size", "40%");
```

```css
/* CSS */
dwc-drawer {
  --dwc-drawer-size: 40%;
}
```

Um zu verhindern, dass der `Drawer` zu groß wird, verwenden Sie `--dwc-drawer-max-size` zusammen mit ihm:

```java
// Java
drawer.setStyle("--dwc-drawer-size", "40%");
drawer.setStyle("--dwc-drawer-max-size", "800px");
```

```css
/* CSS */
dwc-drawer {
  --dwc-drawer-size: 40%;
  --dwc-drawer-max-size: 800px;
}
```

## Platzierung {#placement}

Die Methode `setPlacement()` steuert, wo der `Drawer` im Ansichtsbereich erscheint.

Verfügbare Platzierungsoptionen:

<!-- vale off -->
- **OBEN**: Positioniert den Drawer an der oberen Kante des Ansichtsbereichs.
- **OBEN_MITTE**: Richtet den Drawer horizontal in der Mitte oben im Ansichtsbereich aus.
- **UNTEN**: Platziert den Drawer am unteren Rand des Ansichtsbereichs.
- **UNTEN_MITTE**: Zentriert den Drawer horizontal am unteren Rand des Ansichtsbereichs.
- **LINKS**: Positioniert den Drawer entlang der linken Kante des Ansichtsbereichs.
- **RECHTS**: Positioniert den Drawer entlang der rechten Kante des Ansichtsbereichs.
<!-- vale on -->

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Ereignisbehandlung

Die `Drawer`-Komponente gibt Lebenszyklusereignisse aus, die verwendet werden können, um die Anwendungslogik als Reaktion auf Änderungen ihres geöffneten oder geschlossenen Zustands auszulösen.

Unterstützte Ereignisse:

- `DrawerOpenEvent`: Wird ausgelöst, wenn der Drawer vollständig geöffnet ist.
- `DrawerCloseEvent`: Wird ausgelöst, wenn der Drawer vollständig geschlossen ist.

Sie können Listener an diese Ereignisse anhängen, um Logik auszuführen, wenn sich der Zustand des `Drawer` ändert.

```java
Drawer drawer = new Drawer();

drawer.addOpenListener(e -> {
  // Ereignis des Öffnens des Drawers behandeln
});

drawer.addCloseListener(e -> {
  // Ereignis des Schließens des Drawers behandeln
});
```

## Beispiel: Kontaktwähler

Die `Drawer`-Komponente zeigt zusätzlichen Inhalt an, ohne die aktuelle Ansicht zu stören. Dieses Beispiel platziert einen Drawer in der unteren Mitte, der eine scrollbare Kontaktliste enthält.

Jeder Kontakt zeigt ein Avatar, Namen, Standort und eine Aktions-Schaltfläche für den schnellen Zugriff auf Details oder Kommunikation. Dieser Ansatz eignet sich gut für den Bau kompakter Werkzeuge wie Kontaktwähler, Einstellungs-Panels oder Benachrichtigungen.

<ComponentDemo
path='/webforj/drawercontact?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerContactView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/drawer/drawerContact.css'
height='600px'
/>

## Beispiel: Aufgabenmanager

Dieses Beispiel verwendet einen `Drawer` als Aufgabenmanager. Sie können Aufgaben hinzufügen, sie abhaken und abgeschlossene löschen. Die Fußzeile des `Drawer` enthält Formularelemente, um mit der Aufgabendliste zu interagieren, und die „Aufgabe hinzufügen“ [`Button`](../components/button) deaktiviert sich selbst, wenn 50 Aufgaben erreicht sind.

<ComponentDemo
path='/webforj/drawertask?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerTaskView.java'
cssURL='/css/drawer/drawer-task-view.css'
height='600px'
/>

## Styling {#styling}

<TableBuilder name="Drawer" />
