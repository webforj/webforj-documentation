---
title: CheckBox
sidebar_position: 20
_i18n_hash: 7946f6753a03194ecdcf7e20a7053012
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-checkbox" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/CheckBox" top='true'/>

Ein `CheckBox` kann ausgewählt oder abgewählt werden und zeigt seinen aktuellen Zustand als Häkchen an. Checkboxes eignen sich gut zum Umschalten individueller Einstellungen oder um Nutzern die Auswahl mehrerer Optionen aus einer Reihe von Möglichkeiten zu ermöglichen.

<!-- INTRO_END -->

## Verwendungen {#usages}

Der `CheckBox` eignet sich am besten für Szenarien, in denen Nutzer mehrere Auswahlen aus einer Liste von Optionen treffen müssen. Hier sind einige Beispiele, wann der `CheckBox` verwendet werden sollte:

1. **Auswahl von Aufgaben oder Funktionen**: Checkboxes werden häufig verwendet, wenn Nutzer mehrere Aufgaben oder Funktionen auswählen müssen, um bestimmte Aktionen oder Konfigurationen durchzuführen.

2. **Präferenz-Einstellungen**: Anwendungen, die mit Präferenz- oder Einstellungsfenstern zu tun haben, verwenden oft Checkboxes, um Nutzern die Auswahl mehrerer Optionen aus einer Auswahl zu ermöglichen. Dies ist am besten für Optionen geeignet, die sich nicht gegenseitig ausschließen. Zum Beispiel:

> - Aktivieren oder Deaktivieren von Benachrichtigungen
> - Auswahl eines Dunkel- oder Hellmodus-Themes
> - Auswahl von E-Mail-Benachrichtigungspräferenzen

3. **Filtern oder Sortieren**: Ein `CheckBox` kann in Anwendungen verwendet werden, die Nutzer erfordern, mehrere Filter oder Kategorien auszuwählen, wie das Filtern von Suchergebnissen oder das Auswählen mehrerer Elemente für weitere Aktionen.

4. **Formulareingaben**: Checkboxes werden häufig in Formularen verwendet, um Nutzern zu ermöglichen, mehrere Optionen auszuwählen oder binäre Entscheidungen zu treffen. Zum Beispiel:
   > - Für einen Newsletter anmelden
   > - Bedingungen und Konditionen zustimmen
   > - Artikel für den Kauf oder die Buchung auswählen

## Text und Positionierung {#text-and-positioning}

Check Boxen können die <JavadocLink type="foundation" location="com/webforj/component/AbstractOptionInput" code='true' suffix='#setText(java.lang.String)'>setText(String text)</JavadocLink> Methode nutzen, die in der Nähe der Check Box positioniert wird, gemäß der integrierten <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink>.

Checkboxes haben eine integrierte Funktionalität, um Text anzuzeigen, entweder rechts oder links von der Box. Standardmäßig wird der Text rechts von der Komponente angezeigt. Die Positionierung des Textes wird durch die Verwendung des <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink> enums unterstützt. Nachfolgend sind die beiden Einstellungen aufgezeigt: <br/>

<ComponentDemo
path='/webforj/checkboxhorizontaltext'
files={['src/main/java/com/webforj/samples/views/checkbox/CheckboxHorizontalTextView.java']}
height='200px'
/>

<br/>

## Indeterminismus {#indeterminism}

Die `CheckBox` Komponente unterstützt Indeterminismus, ein UI-Muster, das häufig in Formularen und Listen verwendet wird, um anzuzeigen, dass eine Gruppe von Checkboxes eine Mischung aus ausgewählten und nicht ausgewählten Zuständen hat. Dieser Zustand wird durch einen dritten visuellen Zustand dargestellt, der typischerweise als gefülltes Quadrat oder ein Strich innerhalb der Checkbox angezeigt wird. Es gibt einige häufige Anwendungsfälle, die mit Indeterminismus verbunden sind:

- **Auswahl mehrerer Elemente**: Indeterminismus ist nützlich, wenn Nutzer mehrere Elemente aus einer Liste oder einer Auswahl von Optionen auswählen müssen. Es ermöglicht Nutzern, anzuzeigen, dass sie einige, aber nicht alle, der verfügbaren Auswahlmöglichkeiten auswählen möchten.

- **Hierarchische Daten**: Indeterminismus kann in Szenarien eingesetzt werden, in denen eine hierarchische Beziehung zwischen CheckBoxes besteht. Zum Beispiel, wenn Kategorien und Unterkategorien ausgewählt werden, kann Indeterminismus darstellen, dass einige Unterkategorien ausgewählt sind, während andere nicht ausgewählt sind, und die übergeordnete Komponente sich im unbestimmten Zustand befindet.

<ComponentDemo
path='/webforj/checkboxindeterminate'
files={['src/main/java/com/webforj/samples/views/checkbox/CheckboxIndeterminateView.java']}
height='150px'
/>

## Styling {#styling}

### Ausdehnungen {#expanses}

Die folgenden <JavadocLink type="foundation" location="com/webforj/component/Expanse"> Ausdehnungswerte </JavadocLink> ermöglichen eine schnelle Stilgestaltung ohne Verwendung von CSS. Ausdehnungen werden durch die Verwendung der `Expanse` Enum-Klasse unterstützt. Nachfolgend sind die für die Checkbox-Komponente unterstützten Ausdehnungen aufgeführt: <br/>

<ComponentDemo
path='/webforj/checkboxexpanse'
files={['src/main/java/com/webforj/samples/views/checkbox/CheckboxExpanseView.java']}
height='150px'
/>

<br/>

<TableBuilder name="Checkbox" />

## Best Practices {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der `Checkbox` Komponente zu gewährleisten, beachten Sie die folgenden Best Practices:

1. **Optionen klar beschriften**: Stellen Sie klare und prägnante Beschreibungen für jede `CheckBox`-Option bereit, um die Auswahl genau zu beschreiben. Labels sollten leicht verständlich und voneinander zu unterscheiden sein.

2. **CheckBoxes gruppieren**: Gruppieren Sie verwandte Checkboxes zusammen, um ihre Zugehörigkeit anzuzeigen. Dies hilft Nutzern zu verstehen, dass mehrere Optionen innerhalb einer bestimmten Gruppe ausgewählt werden können.

3. **Standardauswahl bereitstellen**: Falls zutreffend, sollten Sie in Betracht ziehen, eine Standardauswahl für Checkboxes bereitzustellen, um Nutzern eine Orientierung zu bieten, wenn sie die Optionen zum ersten Mal antreffen. Die Standardauswahl sollte mit der häufigsten oder bevorzugten Wahl übereinstimmen.

4. **Indeterminismus**: Wenn eine übergeordnete `CheckBox` Komponente mehrere Komponenten umfasst, bei denen einige aktiviert und andere deaktiviert werden können, verwenden Sie die indeterminate Eigenschaft, um anzuzeigen, dass nicht alle `CheckBox` Komponenten aktiviert oder deaktiviert sind.
