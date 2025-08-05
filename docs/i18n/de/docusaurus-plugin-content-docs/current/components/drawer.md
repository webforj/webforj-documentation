---
title: Drawer
sidebar_position: 35
_i18n_hash: e3b531e5fb7f1554e035f4d05aad8512
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-drawer" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="drawer" location="com/webforj/component/drawer/Drawer" top='true'/>

Die Schublade ist ein Container, der in das Ansichtsfenster gleitet, um zusätzliche Optionen und Informationen anzuzeigen. In einer Anwendung können mehrere Schubladen erstellt werden, die übereinander gestapelt werden.

Die Drawer-Komponente kann in vielen verschiedenen Situationen verwendet werden, z. B. indem sie ein Navigationsmenü bietet, das ein- und ausgeblendet werden kann, ein Panel, das ergänzende oder kontextbezogene Informationen anzeigt, oder um die Nutzung auf einem mobilen Gerät zu optimieren. Das folgende Beispiel zeigt eine mobile Anwendung, die die webforJ AppLayout-Komponente verwendet und beim ersten Laden eine „Willkommens-Popup“-Schublade am unteren Ende anzeigt. Darüber hinaus kann eine Navigationsschublade in der Anwendung durch Klicken auf das Hamburger-Menü umgeschaltet werden.

<AppLayoutViewer path='/webforj/drawerwelcome?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerWelcomeView.java'
cssURL='/css/drawer/drawerWelcome.css'
/>

## Anwendungen {#usages}

1. **Navigationsmenü**: Eine gängige Verwendung einer Schubladenkomponente ist als Navigationsmenü. Es bietet eine platzsparende Möglichkeit, Links zu verschiedenen Abschnitten oder Seiten Ihrer Anwendung anzuzeigen, insbesondere in mobilen oder responsiven Layouts. Benutzer können die Schublade öffnen und schließen, um auf die Navigationsoptionen zuzugreifen, ohne den Hauptinhalt überladen zu müssen.

2. **Filter und Sidebar**: Eine Schublade kann als Filter oder Sidebar in Anwendungen verwendet werden, die eine Liste von Elementen anzeigen. Benutzer können die Schublade erweitern, um Filteroptionen, Sortiersteuerungen oder zusätzliche Informationen zu den Listenartikeln anzuzeigen. Dadurch bleibt der Hauptinhalt auf die Liste fokussiert, während er erweiterte Funktionen auf zugängliche Weise bereitstellt.

3. **Benutzerprofil oder Einstellungen**: Sie können eine Schublade verwenden, um Benutzerprofilinformationen oder Anwendungseinstellungen anzuzeigen. Dies hält solche Informationen leicht zugänglich, aber verborgen, wenn sie nicht benötigt werden, und sorgt für eine saubere und aufgeräumte Benutzeroberfläche. Benutzer können die Schublade öffnen, um ihre Profile zu aktualisieren oder Einstellungen anzupassen.

4. **Benachrichtigungen**: Für Anwendungen mit Benachrichtigungen oder Alarmen kann eine Schublade hereingleiten, um neue Nachrichten oder Updates anzuzeigen. Benutzer können schnell Benachrichtigungen überprüfen und ablehnen, ohne ihre aktuelle Ansicht zu verlassen.

<ComponentDemo
path='/webforj/drawerdemo?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerDemoView.java'
height='600px'
/>

## Anpassung {#customization}

Es gibt verschiedene Eigenschaften, die die Anpassung verschiedener Attribute der Drawer-Komponente ermöglichen. Dieser Abschnitt beschreibt diese Eigenschaften mit Beispielen für deren Modifizierung.

## Autofokus {#autofocus}

Die Autofokus-Eigenschaft wurde entwickelt, um die Zugänglichkeit und Benutzerfreundlichkeit zu verbessern, indem beim Öffnen einer Schublade automatisch auf das erste Element innerhalb der Schublade fokussiert wird. Diese Funktion beseitigt die Notwendigkeit für Benutzer, manuell zu dem gewünschten Element zu navigieren, wodurch Zeit und Mühe gespart werden.

Wenn die Schublade geöffnet werden soll, sei es durch ein Ereignis, standardmäßig oder durch eine andere Interaktion, wird der Benutzerfokus auf das erste Element innerhalb der Schublade gelenkt. Dieses erste Element könnte eine Schaltfläche, ein Link, eine Menüauswahl oder ein anderes fokussierbares Element sein.

:::tip
Durch die automatische Fokussierung auf das erste Element stellt der Entwickler sicher, dass Benutzer sofort mit der relevantesten oder am häufigsten verwendeten Option interagieren können, ohne durch die gesamte Schublade zu tabben oder zu scrollen. Dieses Verhalten optimiert das Benutzererlebnis und fördert eine effiziente Navigation innerhalb der Benutzeroberfläche.
:::

Diese Eigenschaft kann auch besonders vorteilhaft für Personen sein, die auf Tastaturnavigation oder Hilfstechnologien wie Bildschirmleser angewiesen sind. Sie bietet einen klaren Ausgangspunkt innerhalb der Schublade und ermöglicht es Benutzern, die gewünschte Funktionalität ohne unnötige manuelle Eingabe zu erreichen.

<ComponentDemo
path='/webforj/drawerautofocus?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerAutoFocusView.java'
height='600px'
/>

## Label {#label}

Die Drawer-Label-Eigenschaft ist eine Funktion, die entwickelt wurde, um die Zugänglichkeit zu verbessern und den Benutzern einen beschreibenden Kontext für eine Schublade innerhalb einer Benutzeroberfläche bereitzustellen. Diese Eigenschaft ermöglicht es Entwicklern, einer Schublade ein Label zuzuweisen, hauptsächlich zu Zugänglichkeitszwecken, damit Bildschirmleser und andere Hilfstechnologien den Zweck und den Inhalt der Schublade genau an die Benutzer vermitteln können.

Wenn die Drawer-Label-Eigenschaft verwendet wird, wird das zugewiesene Label zu einem integralen Bestandteil der Zugänglichkeitsinfrastruktur der Schublade. Es ermöglicht Benutzern, die auf Hilfstechnologien angewiesen sind, die Funktion der Schublade zu verstehen und effektiver durch die Benutzeroberfläche zu navigieren.

Durch die Bereitstellung eines Labels für die Schublade stellen Entwickler sicher, dass Bildschirmleser den Zweck der Schublade für sehbehinderte Benutzer ankündigen. Diese Informationen befähigen Einzelpersonen, informierte Entscheidungen zu treffen, wie sie mit der Schublade interagieren, da sie deren Inhalt und Relevanz innerhalb der breiteren Benutzeroberfläche verstehen können.

Die Label-Eigenschaft kann angepasst werden, um den spezifischen Kontext und die Designanforderungen der Anwendung zu erfüllen. Entwickler haben die Flexibilität, prägnante und beschreibende Labels bereitzustellen, die den Inhalt oder die Funktionalität der Schublade genau wiedergeben.

## Platzierung {#placement}

Die Platzierungseigenschaft der Drawer-UI-Komponente ermöglicht es Entwicklern, die Position und Ausrichtung der Schublade im Ansichtsfenster festzulegen. Diese Eigenschaft bietet eine Reihe von Enumerationswerten, die Flexibilität bieten, um zu bestimmen, wo die Schublade in Bezug auf den Hauptinhalt erscheint.

Die verfügbaren Enumerationswerte für die Platzierungseigenschaft sind wie folgt:

- **OBEN**: Dieser Wert platziert die Schublade oben im Ansichtsfenster, sodass sie den obersten Bereich einnimmt.

- **OBEN_MITTE**: Mit diesem Wert wird die Schublade in der Mitte des oberen Teils des Ansichtsfensters positioniert. Sie ist horizontal in der Mitte ausgerichtet und erzeugt ein ausgewogenes Layout.

- **UNTEN**: Bei Verwendung dieses Wertes befindet sich die Schublade unten im Ansichtsfenster und erscheint unter dem Hauptinhalt.

- **UNTEN_MITTE**: Dieser Wert zentriert die Schublade horizontal am unteren Rand des Ansichtsfensters. Er sorgt für eine visuell ausgewogene Komposition.

- **LINKS**: Wenn dieser Wert ausgewählt wird, wird die Schublade auf der linken Seite des Ansichtsfensters platziert, angrenzend an den Hauptinhalt.

- **RECHTS**: Mit diesem Wert wird die Schublade auf der rechten Seite des Ansichtsfensters platziert, wobei sie in unmittelbare Nähe zum Hauptinhalt bleibt.

Die Platzierungseigenschaft ermöglicht es Entwicklern, die am besten geeignete Position für die Schublade basierend auf den spezifischen Design- und Benutzererfahrungsanforderungen auszuwählen. Die Enumerationswerte bieten eine Vielzahl von Platzierungsoptionen, um verschiedene Schnittstellengestaltungen und visuelle Hierarchien zu berücksichtigen.

Durch die Nutzung der Platzierungseigenschaft können Entwickler intuitive und effiziente Benutzeroberflächen schaffen. Beispielsweise ermöglicht die Platzierung der Schublade auf der linken oder rechten Seite schnellen Zugriff auf zusätzliche Funktionen oder Navigationsoptionen, während Platzierungen oben oder unten gut geeignet sind für kontextuelle Informationen oder ergänzende Inhalte.

<ComponentDemo
path='/webforj/drawerplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/drawer/DrawerPlacementView.java'
height='600px'
/>

## Styling {#styling}

<TableBuilder name="Drawer" />

## Beste Praktiken {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der `Drawer`-Komponente zu gewährleisten, beachten Sie die folgenden besten Praktiken:

1. **Platzierung**: Entscheiden Sie, ob die Schublade von links, rechts, oben oder unten hereingleiten soll, basierend auf dem Layout und den Überlegungen zur Benutzererfahrung Ihrer Anwendung. Berücksichtigen Sie die Vorlieben der Benutzer und die Designkonventionen.

2. **Zugänglichkeit**: Achten Sie besonders auf die Zugänglichkeit. Stellen Sie sicher, dass Benutzer die Schublade mit Tastaturbefehlen öffnen und schließen können und dass Bildschirmleser ihre Anwesenheit und ihren Zustand ankündigen können. Stellen Sie bei Bedarf ARIA-Rollen und -Labels bereit.

3. **Wischgeste**: Unterstützen Sie auf berührungseingabefähigen Geräten Wischgesten zum Öffnen und Schließen der Schublade. Dies ist eine intuitive Möglichkeit für Benutzer, mit ihr zu interagieren.
