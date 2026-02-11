---
title: Drawer
sidebar_position: 35
_i18n_hash: 1ac0b2efc50e748c9fd18f92de8d0e6e
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

Der `Drawer`-Komponente in webforJ erstellt ein schiebendes Panel, das von der Kante des Bildschirms erscheint und zusätzlichen Inhalt offenbart, ohne die aktuelle Ansicht zu verlassen. Er wird häufig für Seitennavigation, Filtermenüs, Benutzereinstellungen oder kompakte Benachrichtigungen verwendet, die vorübergehend erscheinen müssen, ohne die Hauptoberfläche zu stören.

`Drawers` stapeln sich automatisch, wenn mehrere geöffnet sind, wodurch sie eine flexible Wahl für raumbegrenzte Schnittstellen darstellen.

Das folgende Beispiel zeigt dieses Verhalten innerhalb der [`AppLayout`](../components/app-layout)-Komponente. Der Navigationsschublade, die durch das Hamburger-Menü aktiviert wird, ist in [`AppLayout`](../components/app-layout) integriert, während das Willkommens-Popup am unteren Rand eine eigenständige `Drawer`-Instanz verwendet. Beide koexistieren und stapeln sich unabhängig, was demonstriert, wie `Drawers` in Layout-Komponenten integriert oder als eigenständige Elemente verwendet werden können.

<AppLayoutViewer path='/webforj/drawerwelcome?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java'
cssURL='/css/drawer/drawerWelcome.css'
/>

## Autofokus

Die `Drawer`-Komponente unterstützt Autofokus, der den Fokus automatisch auf das erste fokussierbare Element setzt, wenn der `Drawer` geöffnet wird. Dies verbessert die Benutzerfreundlichkeit, indem die Aufmerksamkeit direkt auf das erste handlungsorientierte Element gelenkt wird.

<ComponentDemo
path='/webforj/drawerautofocus?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java'
height='600px'
/>

<!-- Beispiel -->

## Label {#label}

Die Methode `setLabel()` kann eine aussagekräftige Beschreibung des Inhalts innerhalb eines `Drawer` bereitstellen. Wenn ein Label gesetzt wird, können unterstützende Technologien wie Bildschirmleser es ankündigen, was den Benutzern hilft, den Zweck des `Drawer` zu verstehen, ohne seinen visuellen Inhalt zu sehen.

```java
Drawer drawer = new Drawer();
drawer.setLabel("Aufgabenmanager");
```

:::tip Beschreibende Labels
Verwenden Sie prägnante und beschreibende Labels, die den Zweck des `Drawer` widerspiegeln. Vermeiden Sie generische Begriffe wie „Menü“ oder „Panel“, wenn ein spezifischerer Name verwendet werden kann.
:::

## Größe

Um die Größe eines `Drawer` zu steuern, setzen Sie einen Wert für die CSS-Benutzerdefinierte Eigenschaft `--dwc-drawer-size`. Dies setzt die Breite des `Drawer` für links/rechts Platzierung oder die Höhe für oben/unten Platzierung.

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

Die Methode `setPlacement()` steuert, wo der `Drawer` im Ansichtsfenster erscheint.

Verfügbare Platzierungsoptionen:

<!-- vale off -->
- **OBEN**: Positioniert den Drawer an der oberen Kante des Ansichtsfensters.
- **OBEN_ZENTRAL**: Richtet den Drawer horizontal zentriert am oberen Rand des Ansichtsfensters aus.
- **UNTEN**: Platziert den Drawer am unteren Rand des Ansichtsfensters.
- **UNTEN_ZENTRAL**: Zentriert den Drawer horizontal am unteren Rand des Ansichtsfensters.
- **LINKS**: Positioniert den Drawer am linken Rand des Ansichtsfensters.
- **RECHTS**: Positioniert den Drawer am rechten Rand des Ansichtsfensters.
<!-- vale on -->

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Ereignisbehandlung

Die `Drawer`-Komponente gibt Lebenszyklusereignisse aus, die verwendet werden können, um Logik der App als Reaktion auf Änderungen in ihrem offenen oder geschlossenen Status auszulösen. 

Unterstützte Ereignisse:

- `DrawerOpenEvent`: Wird ausgelöst, wenn der Drawer vollständig geöffnet ist.
- `DrawerCloseEvent`: Wird ausgelöst, wenn der Drawer vollständig geschlossen ist.

Sie können Listener an diese Ereignisse anhängen, um Logik auszuführen, wenn sich der Status des `Drawer` ändert.

```java
Drawer drawer = new Drawer();

drawer.addOpenListener(e -> {
  // Behandlung des geöffneten Drawer-Ereignisses
});

drawer.addCloseListener(e -> {
  // Behandlung des geschlossenen Drawer-Ereignisses
});
```

## Beispiel: Kontaktwähler

Die `Drawer`-Komponente zeigt zusätzlichen Inhalt an, ohne die aktuelle Ansicht zu stören. Dieses Beispiel platziert einen Drawer in der unteren Mitte, der eine scrollbare Kontaktliste enthält.

Jeder Kontakt zeigt ein Avatar, Namen, Standort und eine Schaltfläche für schnellen Zugriff auf Details oder Kommunikation. Dieser Ansatz eignet sich gut zum Erstellen kompakter Werkzeuge wie Kontaktwähler, Einstellungen-Panels oder Benachrichtigungen.

<ComponentDemo
path='/webforj/drawercontact?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerContactView.java'
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/css/drawer/drawerContact.css'
height='600px'
/>

## Beispiel: Aufgabenmanager

Dieses Beispiel verwendet einen `Drawer` als Aufgabenmanager. Sie können Aufgaben hinzufügen, abhaken und erledigte löschen. Die Fußzeile des `Drawer` enthält Formularsteuerelemente zur Interaktion mit der Aufgabenliste, und die „Aufgabe hinzufügen“-[`Button`](../components/button) deaktiviert sich selbst, wenn 50 Aufgaben erreicht sind.

<ComponentDemo
path='/webforj/drawertask?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerTaskView.java'
height='600px'
/>

## Styling {#styling}

<TableBuilder name="Drawer" />
