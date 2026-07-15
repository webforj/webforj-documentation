---
title: Drawer
sidebar_position: 35
description: >-
  Slide panels in from any screen edge with the Drawer component, ideal for side
  navigation, filter menus, settings, and stackable popovers.
_i18n_hash: c04ddb6b8576656535eb1fa09ea587e3
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

Die `Drawer`-Komponente in webforJ erzeugt ein schiebendes Panel, das von der Kante des Bildschirms erscheint und zusätzlichen Inhalt anzeigt, ohne die aktuelle Ansicht zu verlassen. Sie wird häufig für Seitennavigation, Filtermenüs, Benutzereinstellungen oder kompakte Benachrichtigungen verwendet, die temporär angezeigt werden müssen, ohne die Hauptoberfläche zu stören.

<!-- INTRO_END -->

## Stacking {#stacking}

Drawers stapeln sich automatisch, wenn mehrere geöffnet sind, wodurch sie eine flexible Wahl für platzbeschränkte Schnittstellen darstellen.

Im folgenden Beispiel wird dieses Verhalten innerhalb der [`AppLayout`](../components/app-layout) Komponente gezeigt. Der Navigations-Drawer, der durch das Hamburger-Menü ausgelöst wird, ist in [`AppLayout`](../components/app-layout) eingebaut, während das Willkommens-Popup am unteren Rand eine eigenständige `Drawer`-Instanz verwendet. Beide koexistieren und stapeln sich unabhängig, was zeigt, wie Drawers innerhalb von Layout-Komponenten integriert oder als eigenständige Elemente verwendet werden können.

<ComponentDemo
path='/webforj/drawerwelcome'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java',
  'src/main/frontend/css/drawer/drawerWelcome.css',
]}
/>

## Autofokus {#autofocus}

Die `Drawer`-Komponente unterstützt Autofokus, welcher automatisch den Fokus auf das erste fokussierbare Element setzt, wenn der `Drawer` geöffnet wird. Dies verbessert die Benutzerfreundlichkeit, indem es die Aufmerksamkeit direkt auf das erste handlungsfähige Element lenkt.

<ComponentDemo
path='/webforj/drawerautofocus'
files={['src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java']}
height='600px'
/>

<!-- Beispiel -->

## Label {#label}

Die `setLabel()`-Methode kann eine bedeutungsvolle Beschreibung des Inhalts innerhalb eines `Drawer` bereitstellen. Wenn ein Label gesetzt ist, können assistive Technologien wie Screenreader dies ansagen, was den Benutzern hilft, den Zweck des `Drawer` zu verstehen, ohne dessen visuelle Inhalte zu sehen.

```java
Drawer drawer = new Drawer();
drawer.setLabel("Aufgabenmanager");
```

:::tip Beschreibende Labels
Verwenden Sie prägnante und beschreibende Labels, die den Zweck des `Drawer` widerspiegeln. Vermeiden Sie allgemeine Begriffe wie „Menü“ oder „Panel“, wenn ein spezifischerer Name verwendet werden kann.
:::

## Größe {#size}

Um die Größe eines `Drawer` zu steuern, setzen Sie einen Wert für die CSS-Custom-Property `--dwc-drawer-size`. Dies legt die Breite des `Drawer` für die Platzierung links/rechts oder die Höhe für die Platzierung oben/unten fest.

Sie können den Wert mit einer beliebigen gültigen CSS-Einheit wie Prozent, Pixel oder vw/vh definieren, sowohl in Java als auch in CSS:

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

Die `setPlacement()`-Methode steuert, wo der `Drawer` im Ansichtsfenster erscheint.

Verfügbare Platzierungsoptionen:

<!-- vale off -->
- **OBEN**: Platziert den Drawer am oberen Rand des Ansichtsfensters.
- **OBEN_ZENTRAL**: Richtet den Drawer horizontal zentriert am oberen Rand des Ansichtsfensters aus.
- **UNTEN**: Platziert den Drawer am unteren Rand des Ansichtsfensters.
- **UNTEN_ZENTRAL**: Zentriert den Drawer horizontal am unteren Rand des Ansichtsfensters.
- **LINKS**: Platziert den Drawer entlang der linken Kante des Ansichtsfensters.
- **RECHTS**: Platziert den Drawer entlang der rechten Kante des Ansichtsfensters.
<!-- vale on -->

<ComponentDemo
path='/webforj/drawerplacement'
files={['src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java']}
height='600px'
/>

## Ereignisbehandlung {#event-handling}

Die `Drawer`-Komponente emittiert Lebenszyklusereignisse, die verwendet werden können, um Anwendungslogik als Reaktion auf Änderungen ihres offenen oder geschlossenen Status auszulösen.

Unterstützte Ereignisse:

- `DrawerOpenEvent`: Wird ausgelöst, wenn der Drawer vollständig geöffnet ist.
- `DrawerCloseEvent`: Wird ausgelöst, wenn der Drawer vollständig geschlossen ist.

Sie können Listener an diese Ereignisse anhängen, um Logik auszuführen, wenn sich der Status des `Drawer` ändert.

```java
Drawer drawer = new Drawer();

drawer.addOpenListener(e -> {
  // Ereignis für geöffneten Drawer behandeln
});

drawer.addCloseListener(e -> {
  // Ereignis für geschlossenen Drawer behandeln
});
```

## Beispiel: Kontaktwähler {#example-contact-picker}

Die `Drawer`-Komponente zeigt zusätzlichen Inhalt an, ohne die aktuelle Ansicht zu stören. Dieses Beispiel platziert einen Drawer in der Mitte unten und enthält eine scrollbare Kontaktliste.

Jeder Kontakt zeigt ein Avatar, einen Namen, einen Standort und eine Aktionsschaltfläche für den schnellen Zugriff auf Details oder Kommunikation an. Dieser Ansatz eignet sich hervorragend zum Erstellen kompakter Werkzeuge wie Kontaktwähler, Einstellungsfenster oder Benachrichtigungen.

<ComponentDemo
path='/webforj/drawercontact'
files={[
  'src/main/java/com/webforj/samples/views/drawer/DrawerContactView.java',
  'src/main/resources/css/drawer/drawerContact.css',
]}
height='600px'
/>

## Beispiel: Aufgabenmanager {#example-task-manager}

Dieses Beispiel verwendet einen `Drawer` als Aufgabenmanager. Sie können Aufgaben hinzufügen, abhaken und erledigte entfernen. Die Fußzeile des `Drawer` enthält Formularsteuerelemente zur Interaktion mit der Aufgabenliste, und die „Aufgabe hinzufügen“ [`Button`](../components/button) deaktiviert sich, wenn 50 Aufgaben erreicht sind.

<ComponentDemo
path='/webforj/drawertask'
files={[
  'src/main/java/com/webforj/samples/views/drawer/DrawerTaskView.java',
  'src/main/frontend/css/drawer/drawer-task-view.css',
]}
height='600px'
/>

## Stil {#styling}

<TableBuilder name="Drawer" />
