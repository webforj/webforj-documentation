---
title: Drawer
sidebar_position: 35
sidebar_class_name: updated-content
_i18n_hash: a19d1b8c8e0b74cecee529e86649d449
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

Die `Drawer`-Komponente in webforJ erstellt ein gleitendes Panel, das von der Bildschirmkante erscheint und zusätzlichen Inhalt offenbart, ohne die aktuelle Ansicht zu verlassen. Sie wird häufig für Seitennavigation, Filtermenüs, Benutzereinstellungen oder kompakte Benachrichtigungen verwendet, die vorübergehend angezeigt werden müssen, ohne die Hauptoberfläche zu stören.

`Drawers` stapeln sich automatisch, wenn mehrere geöffnet werden, was sie zu einer flexiblen Wahl für platzbeschränkte Schnittstellen macht.

Das Beispiel unten zeigt dieses Verhalten innerhalb der [`AppLayout`](../components/app-layout)-Komponente. Der Navigationsschublade, die durch das hamburger Menü ausgelöst wird, ist in die [`AppLayout`](../components/app-layout) integriert, während das Willkommens-Pop-up am unteren Ende eine eigenständige `Drawer`-Instanz verwendet. Beide koexistieren und stapeln sich unabhängig, was demonstriert, wie `Drawers` in Layout-Komponenten integriert oder als eigenständige Elemente verwendet werden können.

<AppLayoutViewer path='/webforj/drawerwelcome?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java'
cssURL='/css/drawer/drawerWelcome.css'
/>

## Autofokus

Die `Drawer`-Komponente unterstützt Autofokus, der automatisch den Fokus auf das erste fokussierbare Element setzt, wenn die `Drawer` geöffnet wird. Dies verbessert die Benutzerfreundlichkeit, indem die Aufmerksamkeit direkt auf das erste interaktive Element gerichtet wird.

<ComponentDemo
path='/webforj/drawerautofocus?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java'
height='600px'
/>

<!-- Beispiel -->

## Label {#label}

Die Methode `setLabel()` kann eine sinnvolle Beschreibung des Inhalts innerhalb eines `Drawer` bereitstellen. Wenn ein Label gesetzt ist, können unterstützende Technologien wie Bildschirmleseprogramme es ankündigen, was den Benutzern hilft, den Zweck des `Drawer` zu verstehen, ohne seinen visuellen Inhalt sehen zu müssen.

```java
Drawer drawer = new Drawer();
drawer.setLabel("Aufgabenmanager");
```

:::tip Beschreibende Labels
Verwenden Sie prägnante und beschreibende Labels, die den Zweck des `Drawer` widerspiegeln. Vermeiden Sie allgemeine Begriffe wie „Menü“ oder „Panel“, wenn ein spezifischer Name verwendet werden kann.
:::

## Größe

Um die Größe eines `Drawer` zu steuern, setzen Sie einen Wert für die CSS-Custom-Property `--dwc-drawer-size`. Dies setzt die Breite des `Drawer` für die Links/Rechts-Platzierung oder die Höhe für die Oben/Unten-Platzierung.

Sie können den Wert mit einer beliebigen gültigen CSS-Einheit wie Prozent, Pixeln oder vw/vh definieren, entweder mit Java oder CSS:

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

## Platzierung

Die Methode `setPlacement()` steuert, wo der `Drawer` im Ansichtsfenster erscheint.

Verfügbare Platzierungsoptionen:

<!-- vale off -->
- **OBEN**: Positioniert den Schublade am oberen Rand des Ansichtsfensters.
- **OBEN_MITTE**: Richtet den Schublade horizontal zentriert am oberen Rand des Ansichtsfensters aus.
- **UNTEN**: Platziert den Schublade am unteren Rand des Ansichtsfensters.
- **UNTEN_MITTE**: Zentriert den Schublade horizontal am unteren Rand des Ansichtsfensters.
- **LINKS**: Positioniert den Schublade am linken Rand des Ansichtsfensters.
- **RECHTS**: Positioniert den Schublade am rechten Rand des Ansichtsfensters.
<!-- vale on -->

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Ereignisbehandlung

Die `Drawer`-Komponente gibt Lebenszyklusereignisse aus, die verwendet werden können, um die App-Logik als Reaktion auf Änderungen ihres offenen oder geschlossenen Zustands auszulösen.

Unterstützte Ereignisse:

- `DrawerOpenEvent`: Wird ausgelöst, wenn der Schublade vollständig geöffnet ist.
- `DrawerCloseEvent`: Wird ausgelöst, wenn der Schublade vollständig geschlossen ist.

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

Die `Drawer`-Komponente zeigt zusätzlichen Inhalt an, ohne die aktuelle Ansicht zu stören. Dieses Beispiel platziert einen Schublade in der Mitte unten, der eine scrollbar Kontaktliste enthält.

Jeder Kontakt zeigt ein Avatar, einen Namen, einen Standort und eine Aktionsschaltfläche für einen schnellen Zugriff auf Details oder Kommunikation. Dieser Ansatz eignet sich gut für den Aufbau kompakter Werkzeuge wie Kontaktwähler, Einstellungs-Panels oder Benachrichtigungen.

<ComponentDemo
path='/webforj/drawercontact?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerContactView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/drawer/drawerContact.css'
height='600px'
/>

## Beispiel: Aufgabenmanager

Dieses Beispiel verwendet einen `Drawer` als Aufgabenmanager. Sie können Aufgaben hinzufügen, abhaken und abgeschlossene löschen. Die Fußzeile des `Drawer` enthält Formularelemente, um mit der Aufgabenliste zu interagieren, und die Schaltfläche „Aufgabe hinzufügen“ [`Button`](../components/button) deaktiviert sich, wenn 50 Aufgaben erreicht werden.

<ComponentDemo
path='/webforj/drawertask?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerTaskView.java'
height='600px'
/>

## Styling {#styling}

<TableBuilder name="Drawer" />
