---
title: Drawer
sidebar_position: 35
_i18n_hash: d0c9ff09e591673c99918aa6875af28a
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

Die `Drawer`-Komponente in webforJ erstellt ein schiebendes Panel, das von der Kante des Bildschirms erscheint und zusätzlichen Inhalt offenbart, ohne die aktuelle Ansicht zu verlassen. Sie wird häufig für Seitennavigation, Filtermenüs, Benutzereinstellungen oder kompakte Benachrichtigungen verwendet, die temporär angezeigt werden müssen, ohne die Hauptschnittstelle zu stören.

<!-- INTRO_END -->

## Stapeln {#stacking}

Drawers stapeln sich automatisch, wenn mehrere geöffnet werden, was sie zu einer flexiblen Wahl für platzbeschränkte Schnittstellen macht.

Das unten stehende Beispiel zeigt dieses Verhalten innerhalb der [`AppLayout`](../components/app-layout)-Komponente. Der Navigations-Drawer, der durch das Hamburger-Menü ausgelöst wird, ist in [`AppLayout`](../components/app-layout) integriert, während das Willkommens-Popup unten eine eigenständige `Drawer`-Instanz verwendet. Beide coexistieren und stapeln sich unabhängig, was demonstriert, wie Drawers in Layout-Komponenten integriert oder als eigenständige Elemente verwendet werden können.

<AppLayoutViewer path='/webforj/drawerwelcome?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java'
cssURL='/css/drawer/drawerWelcome.css'
/>

## Autofokus

Die `Drawer`-Komponente unterstützt Autofokus, der automatisch den Fokus auf das erste fokussierbare Element setzt, wenn die `Drawer` geöffnet wird. Dies verbessert die Benutzerfreundlichkeit, indem die Aufmerksamkeit direkt auf das erste handlungsfähige Element gelenkt wird.

<ComponentDemo
path='/webforj/drawerautofocus?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java'
height='600px'
/>

<!-- Beispiel -->

## Etikett {#label}

Die Methode `setLabel()` kann eine aussagekräftige Beschreibung des Inhalts innerhalb eines `Drawers` bereitstellen. Wenn ein Etikett gesetzt ist, können assistive Technologien wie Bildschirmlesegeräte es ankündigen, was den Benutzern hilft, den Zweck des `Drawers` zu verstehen, ohne dessen visuelle Inhalte zu sehen.

```java
Drawer drawer = new Drawer();
drawer.setLabel("Aufgabenmanager");
```

:::tip Beschreibende Etiketten
Verwenden Sie prägnante und beschreibende Etiketten, die den Zweck des `Drawers` widerspiegeln. Vermeiden Sie generische Begriffe wie „Menü“ oder „Panel“, wenn ein spezifischerer Name verwendet werden kann.
:::

## Größe

Um die Größe eines `Drawers` zu steuern, setzen Sie einen Wert für die CSS-Custom-Property `--dwc-drawer-size`. Dies legt die Breite des `Drawers` für die links/rechts Platzierung oder die Höhe für die oben/unten Platzierung fest.

Sie können den Wert mit jeder gültigen CSS-Einheit wie einem Prozentsatz, Pixeln oder vw/vh definieren, sowohl in Java als auch in CSS:

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

Die Methode `setPlacement()` steuert, wo der `Drawer` im Ansichtsfenster angezeigt wird.

Verfügbare Platzierungsoptionen:

<!-- vale off -->
- **OBEN**: Positioniert den Drawer am oberen Rand des Ansichtsfensters.
- **OBEN_ZENTRAL**: Richtet den Drawer horizontal zentriert am oberen Rand des Ansichtsfensters aus.
- **UNTEN**: Platziert den Drawer am unteren Rand des Ansichtsfensters.
- **UNTEN_ZENTRAL**: Zentriert den Drawer horizontal am unteren Rand des Ansichtsfensters.
- **LINKS**: Positioniert den Drawer entlang der linken Kante des Ansichtsfensters.
- **RECHTS**: Positioniert den Drawer entlang der rechten Kante des Ansichtsfensters.
<!-- vale on -->

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Ereignisbehandlung

Die `Drawer`-Komponente gibt Lebenszyklusereignisse aus, die verwendet werden können, um App-Logik als Reaktion auf Änderungen im offenen oder geschlossenen Zustand auszulösen.

Unterstützte Ereignisse:

- `DrawerOpenEvent`: Wird ausgelöst, wenn der Drawer vollständig geöffnet ist.
- `DrawerCloseEvent`: Wird ausgelöst, wenn der Drawer vollständig geschlossen ist.

Sie können Listener an diesen Ereignissen anhängen, um Logik auszuführen, wenn sich der Zustand des `Drawers` ändert.

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

Die `Drawer`-Komponente gibt zusätzlichen Inhalt ohne Störung der aktuellen Ansicht aus. Dieses Beispiel platziert einen Drawer am unteren Zentrum, der eine scrollbare Kontaktliste enthält.

Jeder Kontakt zeigt ein Avatar, Namen, Standort und Aktionsschaltfläche für schnellen Zugriff auf Details oder Kommunikation an. Dieser Ansatz funktioniert gut für den Aufbau kompakter Werkzeuge wie Kontaktwähler, Einstellungen-Panels oder Benachrichtigungen.

<ComponentDemo
path='/webforj/drawercontact?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerContactView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/drawer/drawerContact.css'
height='600px'
/>

## Beispiel: Aufgabenmanager

Dieses Beispiel verwendet einen `Drawer` als Aufgabenmanager. Sie können Aufgaben hinzufügen, abhaken und abgeschlossene löschen. Die Fußzeile des `Drawers` enthält Formularsteuerelemente zur Interaktion mit der Aufgabenliste, und die „Aufgabe hinzufügen“ [`Button`](../components/button) deaktiviert sich selbst, wenn 50 Aufgaben erreicht sind.

<ComponentDemo
path='/webforj/drawertask?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerTaskView.java'
height='600px'
/>

## Styling {#styling}

<TableBuilder name="Drawer" />
