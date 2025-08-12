---
title: Drawer
sidebar_position: 35
_i18n_hash: 73da264dca1e3f8cfd58b697e3e9d0dc
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

Der Drawer ist ein Container, der in den Ansichtsbereich gleitet, um zusätzliche Optionen und Informationen anzuzeigen. In einer Anwendung können mehrere Drawer erstellt werden, und sie werden übereinander gestapelt.

Die Drawer-Komponente kann in vielen verschiedenen Situationen verwendet werden, beispielsweise als Navigationsmenü, das umgeschaltet werden kann, als Panel, das ergänzende oder kontextbezogene Informationen anzeigt, oder um die Nutzung auf einem Mobilgerät zu optimieren. Das folgende Beispiel zeigt eine mobile Anwendung, die die webforJ AppLayout-Komponente verwendet und beim ersten Laden einen "Willkommens-Popup"-Drawer am unteren Rand anzeigt. Zusätzlich kann ein Navigations-Drawer in der Anwendung durch Klicken auf das Hamburger-Menü umgeschaltet werden.

<AppLayoutViewer path='/webforj/drawerwelcome?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java'
cssURL='/css/drawer/drawerWelcome.css'
/>

## Verwendungen {#usages}

1. **Navigationsmenü**: Eine häufige Verwendung eines Drawer-Komponenten ist als Navigationsmenü. Es bietet eine platzsparende Möglichkeit, Links zu verschiedenen Abschnitten oder Seiten Ihrer Anwendung anzuzeigen, insbesondere in mobilen oder responsiven Layouts. Benutzer können den Drawer öffnen und schließen, um auf Navigationsoptionen zuzugreifen, ohne den Hauptinhalt zu überladen.

2. **Filter und Sidebar**: Ein Drawer kann als Filter oder Sidebar in Anwendungen verwendet werden, die eine Liste von Elementen anzeigen. Benutzer können den Drawer erweitern, um Filteroptionen, Sortiersteuerungen oder zusätzliche Informationen zu den Listenelementen anzuzeigen. Dies hält den Hauptinhalt auf die Liste konzentriert, während er erweiterte Funktionen auf zugängliche Weise bereitstellt.

3. **Benutzerprofil oder Einstellungen**: Sie können einen Drawer verwenden, um Informationen zum Benutzerprofil oder zu den Anwendungseinstellungen anzuzeigen. Dies ermöglicht einen einfachen Zugriff auf solche Informationen, bleibt aber verborgen, wenn sie nicht benötigt werden, wodurch eine saubere und übersichtliche Benutzeroberfläche erhalten bleibt. Benutzer können den Drawer öffnen, um ihre Profile zu aktualisieren oder Einstellungen anzupassen.

4. **Benachrichtigungen**: Bei Anwendungen mit Benachrichtigungen oder Alerts kann ein Drawer hinein gleiten, um neue Nachrichten oder Aktualisierungen anzuzeigen. Benutzer können schnell Benachrichtigungen überprüfen und abweisen, ohne ihre aktuelle Ansicht zu verlassen.

<ComponentDemo
path='/webforj/drawerdemo?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerDemoView.java'
height='600px'
/>

## Anpassung {#customization}

Es gibt verschiedene Eigenschaften, die es ermöglichen, verschiedene Attribute der Drawer-Komponente anzupassen. Dieser Abschnitt erläutert diese Eigenschaften mit Beispielen für deren Modifikation.

## Autofokus {#autofocus}

Die Auto-Fokus-Eigenschaft wurde entwickelt, um die Benutzerfreundlichkeit und Zugänglichkeit zu verbessern, indem automatisch das erste Element innerhalb eines Drawers fokussiert wird, wenn er geöffnet wird. Diese Funktion beseitigt die Notwendigkeit für Benutzer, manuell zum gewünschten Element zu navigieren, was Zeit und Mühe spart.

Wenn der Drawer geöffnet wird, sei es durch ein Ereignis, standardmäßig oder durch eine andere Interaktion, wird der Fokus des Benutzers auf das erste Element innerhalb des Drawers gelenkt. Dieses erste Element könnte eine Schaltfläche, ein Link, eine Menüoption oder ein anderes fokussierbares Element sein.

:::tip
Durch das automatische Fokussieren des ersten Elements stellt der Entwickler sicher, dass Benutzer sofort mit der relevantesten oder am häufigsten verwendeten Option interagieren können, ohne durch den gesamten Drawer zu tabben oder zu scrollen. Dieses Verhalten optimiert das Benutzererlebnis und fördert eine effiziente Navigation innerhalb der Benutzeroberfläche.
:::

Diese Eigenschaft kann auch besonders vorteilhaft für Personen sein, die auf Tastaturnavigation oder unterstützende Technologien wie Bildschirmleser angewiesen sind. Sie bietet einen klaren Startpunkt innerhalb des Drawers und ermöglicht es den Benutzern, die gewünschte Funktionalität ohne unnötige manuelle Eingabe zu erreichen.

<ComponentDemo
path='/webforj/drawerautofocus?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java'
height='600px'
/>

## Label {#label}

Die Drawer-Label-Eigenschaft ist eine Funktion, die darauf abzielt, die Zugänglichkeit zu verbessern und beschreibenden Kontext für einen Drawer innerhalb einer Benutzeroberfläche bereitzustellen. Diese Eigenschaft ermöglicht es Entwicklern, einem Drawer ein Label zuzuweisen, hauptsächlich zu Zwecken der Zugänglichkeit, und sicherzustellen, dass Bildschirmleser und andere unterstützende Technologien den Zweck und den Inhalt des Drawers genau an die Benutzer vermitteln können.

Wenn die Drawer-Label-Eigenschaft verwendet wird, wird das zugewiesene Label zu einem integralen Bestandteil der Zugänglichkeitsinfrastruktur des Drawers. Dies ermöglicht es Benutzern, die auf unterstützende Technologien angewiesen sind, die Funktion des Drawers zu verstehen und durch die Benutzeroberfläche effektiver zu navigieren.

Durch die Bereitstellung eines Labels für den Drawer stellen Entwickler sicher, dass Bildschirmleser den Zweck des Drawers für sehbehinderte Benutzer ankündigen. Diese Informationen ermöglichen es den Einzelnen, informierte Entscheidungen über die Interaktion mit dem Drawer zu treffen, da sie seinen Inhalt und seine Relevanz innerhalb der breiteren Benutzeroberfläche verstehen können.

Die Label-Eigenschaft kann an den spezifischen Kontext und die Designanforderungen der Anwendung angepasst werden. Entwickler haben die Flexibilität, prägnante und beschreibende Labels bereitzustellen, die den Inhalt oder die Funktionalität des Drawers genau repräsentieren.

## Platzierung {#placement}

Die Platzierungseigenschaft der Drawer-Benutzeroberflächenkomponente ermöglicht es Entwicklern, die Position und Ausrichtung des Drawers innerhalb des Ansichtsbereichs anzugeben. Diese Eigenschaft bietet eine Reihe von Enum-Werten, die Flexibilität bei der Bestimmung bieten, wo der Drawer in Bezug auf den Hauptinhalt erscheint.

Die verfügbaren Enum-Werte für die Platzierungseigenschaft sind wie folgt:

- **OBEN**: Dieser Wert platziert den Drawer oben im Ansichtsbereich, sodass er den obersten Bereich einnimmt.

- **OBEN_ZENTRAL**: Mit diesem Wert wird der Drawer in der Mitte des oberen Bereichs des Ansichtsbereichs positioniert. Er ist horizontal mittig ausgerichtet, was ein ausgewogenes Layout schafft.

- **UNTEN**: Wenn dieser Wert verwendet wird, befindet sich der Drawer am unteren Ende des Ansichtsbereichs, unter dem Hauptinhalt.

- **UNTEN_ZENTRAL**: Dieser Wert zentriert den Drawer horizontal am unteren Rand des Ansichtsbereichs. Er bietet eine visuell ausgewogene Komposition.

- **LINKS**: Wenn dieser Wert ausgewählt wird, wird der Drawer auf der linken Seite des Ansichtsbereichs positioniert, angrenzend an den Hauptinhalt.

- **RECHTS**: Durch die Verwendung dieses Wertes wird der Drawer auf der rechten Seite des Ansichtsbereichs platziert, wobei eine Nähe zum Hauptinhalt gewahrt bleibt.

Die Platzierungseigenschaft ermöglicht es Entwicklern, die am besten geeignete Position für den Drawer basierend auf den spezifischen Design- und Benutzererfahrungsanforderungen auszuwählen. Die Enum-Werte bieten eine Vielzahl von Platzierungsoptionen, um unterschiedlichen Benutzeroberflächenlayouts und visuellen Hierarchien gerecht zu werden.

Durch die Nutzung der Platzierungseigenschaft können Entwickler intuitive und effiziente Benutzeroberflächen erstellen. Zum Beispiel ermöglicht das Platzieren des Drawers auf der linken oder rechten Seite einen schnellen Zugriff auf zusätzliche Funktionen oder Navigationsoptionen, während obere oder untere Platzierungen gut für kontextbezogene Informationen oder ergänzende Inhalte geeignet sind.

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Styling {#styling}

<TableBuilder name="Drawer" />

## Best Practices {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der `Drawer`-Komponente sicherzustellen, sollten folgende Best Practices berücksichtigt werden:

1. **Platzierung**: Entscheiden Sie, ob der Drawer von links, rechts, oben oder unten hereingleiten soll, basierend auf dem Layout und den Benutzererfahrungsüberlegungen Ihrer Anwendung. Berücksichtigen Sie Benutzerpräferenzen und Designrichtlinien.

2. **Zugänglichkeit**: Achten Sie besonders auf die Zugänglichkeit. Stellen Sie sicher, dass Benutzer den Drawer mit Tastenkombinationen öffnen und schließen können und dass Bildschirmleser seine Anwesenheit und seinen Status ankündigen können. Bereitstellen Sie bei Bedarf ARIA-Rollen und -Labels.

3. **Wischgesten**: Auf touchfähigen Geräten sollten Wischgesten zum Öffnen und Schließen des Drawers unterstützt werden. Dies ist eine intuitive Möglichkeit für Benutzer, damit zu interagieren.
