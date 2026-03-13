---
title: CheckBox
sidebar_position: 20
_i18n_hash: e5ace9c598a0892cfa456f376035c87a
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-checkbox" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/CheckBox" top='true'/>

Ein `CheckBox` kann ausgewählt oder abgewählt werden und zeigt seinen aktuellen Zustand als Häkchen an. Checkboxen eignen sich gut zum Umschalten einzelner Einstellungen oder um Benutzern zu ermöglichen, mehrere Optionen aus einer Menge auszuwählen.

<!-- INTRO_END -->

## Anwendungen {#usages}

Der `CheckBox` eignet sich am besten für Szenarien, in denen Benutzer mehrere Auswahlmöglichkeiten aus einer Liste von Optionen treffen müssen. Hier sind einige Beispiele, wann der `CheckBox` verwendet werden sollte:

1. **Aufgaben- oder Feature-Auswahl**: Checkboxen werden häufig verwendet, wenn Benutzer mehrere Aufgaben oder Funktionen auswählen müssen, um bestimmte Aktionen oder Konfigurationen durchzuführen.

2. **Einstellungen**: Anwendungen, die Präferenz- oder Einstellungs-Panels beinhalten, verwenden häufig Checkboxen, um Benutzern zu ermöglichen, mehrere Optionen aus einer Auswahl auszuwählen. Dies eignet sich am besten für Optionen, die nicht gegenseitig ausschließend sind. Zum Beispiel:

> - Aktivieren oder Deaktivieren von Benachrichtigungen
> - Wählen eines Dunkel- oder Hellmodus-Themes
> - Auswählen von E-Mail-Benachrichtigungspräferenzen

3. **Filtern oder Sortieren**: Ein `CheckBox` kann in Anwendungen verwendet werden, die es Benutzern erfordern, mehrere Filter oder Kategorien auszuwählen, wie das Filtern von Suchergebnissen oder das Auswählen mehrerer Elemente für weitere Aktionen.

4. **Formular Eingaben**: Checkboxen werden häufig in Formularen verwendet, um Benutzern zu ermöglichen, mehrere Optionen auszuwählen oder binäre Entscheidungen zu treffen. Zum Beispiel:
   > - Anmeldung für einen Newsletter
   > - Zustimmung zu den Geschäftsbedingungen
   > - Auswählen von Artikeln zum Kauf oder zur Buchung

## Text und Positionierung {#text-and-positioning}

Checkboxen können die <JavadocLink type="foundation" location="com/webforj/component/AbstractOptionInput" code='true' suffix='#setText(java.lang.String)'>setText(String text)</JavadocLink>-Methode nutzen, die in der Nähe des Kontrollkästchens gemäß der integrierten <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink> positioniert wird.

Checkboxen haben eine integrierte Funktionalität, um Text anzuzeigen, der entweder rechts oder links vom Kästchen angezeigt wird. Standardmäßig wird der Text rechts vom Komponenten angezeigt. Die Positionierung des Textes wird durch die Verwendung des <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink>-Enums unterstützt. Nachfolgend sind die beiden Einstellungen aufgeführt: <br/>

<ComponentDemo 
path='/webforj/checkboxhorizontaltext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxHorizontalTextView.java'
height = '200px'
/>

<br/>

## Indeterminismus {#indeterminism}

Die `CheckBox`-Komponente unterstützt Indeterminismus, ein UI-Muster, das häufig in Formularen und Listen verwendet wird, um anzuzeigen, dass eine Gruppe von Checkboxen eine Mischung aus ausgewählten und nicht ausgewählten Zuständen hat. Dieser Zustand wird durch einen dritten visuellen Zustand dargestellt, der typischerweise als gefülltes Quadrat oder ein Strich innerhalb des Kontrollkästchens angezeigt wird. Es gibt einige gängige Anwendungsfälle, die mit Indeterminismus verbunden sind:

- **Auswahl mehrerer Elemente**: Indeterminismus ist nützlich, wenn Benutzer mehrere Elemente aus einer Liste oder einer Auswahl auswählen müssen. Er ermöglicht es Benutzern, anzugeben, dass sie einige, aber nicht alle verfügbaren Auswahlmöglichkeiten auswählen möchten.

- **Hierarchische Daten**: Indeterminismus kann in Szenarien eingesetzt werden, in denen eine hierarchische Beziehung zwischen Checkboxen besteht. Wenn beispielsweise Kategorien und Unterkategorien ausgewählt werden, kann Indeterminismus darstellen, dass einige Unterkategorien ausgewählt sind, während andere nicht ausgewählt sind, und die übergeordnete Komponente sich im indeterminierten Zustand befindet.

<ComponentDemo 
path='/webforj/checkboxindeterminate?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxIndeterminateView.java'
height = '150px'
/>

## Styling {#styling}

### Expanses {#expanses}

Die folgenden <JavadocLink type="foundation" location="com/webforj/component/Expanse">Expanses-Werte</JavadocLink> ermöglichen schnelles Styling, ohne CSS zu verwenden. Expanses werden durch die Verwendung der `Expanse`-Enum-Klasse unterstützt. Nachfolgend sind die Expanses, die für die Checkbox-Komponente unterstützt werden: <br/>

<ComponentDemo 
path='/webforj/checkboxexpanse?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxExpanseView.java'
height = '150px'
/>

<br/>

<TableBuilder name="Checkbox" />

## Beste Praktiken {#best-practices}

Um eine optimale Benutzererfahrung bei der Verwendung der `Checkbox`-Komponente zu gewährleisten, berücksichtige die folgenden besten Praktiken:

1. **Optionen klar kennzeichnen**: Biete klare und prägnante Beschriftungen für jede `CheckBox`-Option an, um die Auswahl genau zu beschreiben. Die Beschriftungen sollten leicht zu verstehen und voneinander zu unterscheiden sein.

2. **Checkboxen gruppieren**: Gruppiere verwandte Checkboxen, um deren Zusammenhang anzuzeigen. Dies hilft Benutzern zu verstehen, dass mehrere Optionen innerhalb einer bestimmten Gruppe ausgewählt werden können.

3. **Standardauswahl bereitstellen**: Falls zutreffend, ziehe in Betracht, eine Standardauswahl für Checkboxen anzubieten, um Benutzer zu leiten, wenn sie zum ersten Mal auf die Optionen stoßen. Die Standardauswahl sollte mit der häufigsten oder bevorzugten Wahl übereinstimmen.

4. **Indeterminismus**: Wenn eine übergeordnete `CheckBox`-Komponente mehrere zugehörige Komponenten aufweist, von denen einige ausgewählt und andere abgewählt werden können, verwende die indeterminate-Eigenschaft, um anzuzeigen, dass nicht alle `CheckBox`-Komponenten ausgewählt oder abgewählt sind.
