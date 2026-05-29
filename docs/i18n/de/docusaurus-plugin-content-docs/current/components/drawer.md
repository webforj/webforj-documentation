---
title: Drawer
sidebar_position: 35
_i18n_hash: 7edd08525f20625cb8d891316111ebb3
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

Das `Drawer`-Komponente in webforJ erstellt ein seitlich herausfahrendes Panel, das am Bildschirmrand erscheint und zusätzlichen Inhalt offenbart, ohne die aktuelle Ansicht zu verlassen. Es wird häufig für seitliche Navigation, Filtermenüs, Benutzereinstellungen oder kompakte Benachrichtigungen verwendet, die vorübergehend erscheinen müssen, ohne die Hauptoberfläche zu stören.

<!-- INTRO_END -->

## Stacking {#stacking}

Drawers stapeln sich automatisch, wenn mehrere geöffnet sind, was sie zu einer flexiblen Wahl für platzbeschränkte Schnittstellen macht.

Das folgende Beispiel zeigt dieses Verhalten innerhalb der [`AppLayout`](../components/app-layout) Komponente. Der Navigations-Drawer, der durch das Hamburger-Menü ausgelöst wird, ist in [`AppLayout`](../components/app-layout) integriert, während das Willkommens-Popup am unteren Ende eine eigenständige `Drawer`-Instanz nutzt. Beide koexistieren und stapeln sich unabhängig, was demonstriert, wie Drawers innerhalb von Layout-Komponenten integriert oder als eigenständige Elemente verwendet werden können.

<ComponentDemo
path='/webforj/drawerwelcome'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java',
  'src/main/resources/static/css/drawer/drawerWelcome.css',
]}
/>

## Autofocus

Die `Drawer`-Komponente unterstützt Autofokus, der automatisch den Fokus auf das erste fokussierbare Element setzt, wenn der `Drawer` geöffnet wird. Dies verbessert die Benutzerfreundlichkeit, indem die Aufmerksamkeit direkt auf das erste handlungsfähige Element gelenkt wird.

<ComponentDemo
path='/webforj/drawerautofocus'
files={['src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java']}
height='600px'
/>

<!-- Beispiel -->

## Label {#label}

Die Methode `setLabel()` kann eine aussagekräftige Beschreibung des Inhalts innerhalb eines `Drawer` bereitstellen. Wenn ein Label gesetzt ist, können unterstützende Technologien wie Bildschirmleser es ankündigen, was den Benutzern hilft, den Zweck des `Drawer` zu verstehen, ohne seinen visuellen Inhalt zu sehen.

```java
Drawer drawer = new Drawer();
drawer.setLabel("Aufgabenmanager");
```

:::tip Beschreibende Labels
Verwenden Sie prägnante und beschreibende Labels, die den Zweck des `Drawer` widerspiegeln. Vermeiden Sie allgemeine Begriffe wie „Menü“ oder „Panel“, wenn ein spezifischer Name verwendet werden kann.
:::

## Größe

Um die Größe eines `Drawer` zu steuern, setzen Sie einen Wert für die CSS-Eigenschaft `--dwc-drawer-size`. Dies legt die Breite des `Drawer` für die links/rechts Platzierung oder die Höhe für die oben/unten Platzierung fest.

Sie können den Wert mit jeder gültigen CSS-Einheit wie Prozent, Pixel oder vw/vh definieren, entweder mit Java oder CSS:

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

Um zu verhindern, dass der `Drawer` zu groß wird, verwenden Sie `--dwc-drawer-max-size` zusammen damit:

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

Die Methode `setPlacement()` steuert, wo der `Drawer` im Ansichtsfenster erscheint.

Verfügbare Platzierungsoptionen:

<!-- vale off -->
- **OBEN**: Positioniert den Drawer am oberen Rand des Ansichtsfensters.
- **OBEN_ZENTRAL**: Richtet den Drawer horizontal zentriert oben im Ansichtsfenster aus.
- **UNTEN**: Platziert den Drawer am unteren Rand des Ansichtsfensters.
- **UNTEN_ZENTRAL**: Zentriert den Drawer horizontal am unteren Rand des Ansichtsfensters.
- **LINKS**: Positioniert den Drawer entlang der linken Seite des Ansichtsfensters.
- **RECHTS**: Positioniert den Drawer entlang der rechten Seite des Ansichtsfensters.
<!-- vale on -->

<ComponentDemo
path='/webforj/drawerplacement'
files={['src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java']}
height='600px'
/>

## Ereignisbehandlung

Die `Drawer`-Komponente gibt Lebenszyklusereignisse aus, die verwendet werden können, um die App-Logik als Reaktion auf Änderungen ihres geöffneten oder geschlossenen Zustands auszulösen.

Unterstützte Ereignisse:

- `DrawerOpenEvent`: Wird ausgelöst, wenn der Drawer vollständig geöffnet ist.
- `DrawerCloseEvent`: Wird ausgelöst, wenn der Drawer vollständig geschlossen ist.

Sie können Listener an diese Ereignisse anhängen, um Logik auszuführen, wenn sich der Zustand des `Drawer` ändert.

```java
Drawer drawer = new Drawer();

drawer.addOpenListener(e -> {
  // Handle drawer opened event
});

drawer.addCloseListener(e -> {
  // Handle drawer closed event
});
```

## Beispiel: Kontaktwähler

Die `Drawer`-Komponente zeigt zusätzlichen Inhalt, ohne die aktuelle Ansicht zu stören. Dieses Beispiel platziert einen Drawer in der unteren Mitte, der eine scrollbare Kontaktliste enthält.

Jeder Kontakt zeigt ein Avatar, einen Namen, einen Standort und eine Aktionsschaltfläche für den schnellen Zugriff auf Details oder Kommunikation. Dieser Ansatz eignet sich gut für den Aufbau kompakter Werkzeuge wie Kontaktwähler, Einstellungs-Panels oder Benachrichtigungen.

<ComponentDemo
path='/webforj/drawercontact'
files={[
  'src/main/java/com/webforj/samples/views/drawer/DrawerContactView.java',
  'src/main/resources/css/drawer/drawerContact.css',
]}
height='600px'
/>

## Beispiel: Aufgabenmanager

Dieses Beispiel verwendet einen `Drawer` als Aufgabenmanager. Sie können Aufgaben hinzufügen, abhaken und erledigte Aufgaben löschen. Der Footer des `Drawer` enthält Formularsteuerelemente zur Interaktion mit der Aufgabenliste, und die „Aufgabe hinzufügen“ [`Button`](../components/button) deaktiviert sich, wenn 50 Aufgaben erreicht sind.

<ComponentDemo
path='/webforj/drawertask'
files={[
  'src/main/java/com/webforj/samples/views/drawer/DrawerTaskView.java',
  'src/main/resources/static/css/drawer/drawer-task-view.css',
]}
height='600px'
/>

## Styling {#styling}

<TableBuilder name="Drawer" />
