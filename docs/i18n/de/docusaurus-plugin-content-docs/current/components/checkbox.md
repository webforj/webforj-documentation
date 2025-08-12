---
title: CheckBox
sidebar_position: 20
_i18n_hash: c2be55222401962b275faf28ff6ddba3
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-checkbox" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/CheckBox" top='true'/>

Die Klasse `CheckBox` erstellt eine Komponente, die ausgewählt oder abgewählt werden kann und ihren Zustand dem Benutzer anzeigt. Wenn sie angeklickt wird, erscheint ein Häkchen im Kästchen, um eine positive Auswahl (ein) anzuzeigen. Wenn sie erneut angeklickt wird, verschwindet das Häkchen und zeigt eine negative Auswahl (aus) an.

Durch eine klare und einfache visuelle Anzeige des Auswahlstatus verbessern Kontrollkästchen die Benutzerinteraktion und Entscheidungsfindung, was sie zu einem wesentlichen Element moderner Benutzeroberflächen macht.

## Anwendungen {#usages}

Das `CheckBox` wird am besten in Szenarien verwendet, in denen Benutzer mehrere Auswahlmöglichkeiten aus einer Liste von Optionen treffen müssen. Hier sind einige Beispiele, wann das `CheckBox` verwendet werden sollte:

1. **Aufgaben- oder Funktionsauswahl**: Kontrollkästchen werden häufig verwendet, wenn Benutzer mehrere Aufgaben oder Funktionen auswählen müssen, um bestimmte Aktionen oder Konfigurationen durchzuführen.

2. **Einstellungsmöglichkeiten**: Anwendungen, die Einstellungs- oder Präferenzfenster enthalten, nutzen oft Kontrollkästchen, um Benutzern die Auswahl mehrerer Optionen aus einem Set von Auswahlmöglichkeiten zu ermöglichen. Dies ist am besten für Optionen geeignet, die sich nicht gegenseitig ausschließen. Zum Beispiel:

> - Benachrichtigungen aktivieren oder deaktivieren
> - Auswahl eines dunklen oder hellen Modus
> - Auswahl von E-Mail-Benachrichtigungseinstellungen

3. **Filtern oder Sortieren**: Ein `CheckBox` kann in Anwendungen verwendet werden, die es Benutzern erfordern, mehrere Filter oder Kategorien auszuwählen, wie z.B. das Filtern von Suchergebnissen oder das Auswählen mehrerer Elemente für weitere Aktionen.

4. **Formulareingaben**: Kontrollkästchen werden häufig in Formularen verwendet, um Benutzern zu ermöglichen, mehrere Optionen auszuwählen oder binäre Entscheidungen zu treffen. Zum Beispiel:
   > - Abonnieren eines Newsletters
   > - Zustimmung zu den Allgemeinen Geschäftsbedingungen
   > - Auswahl von Artikeln zum Kauf oder zur Buchung

## Text und Positionierung {#text-and-positioning}

Kontrollkästchen können die <JavadocLink type="foundation" location="com/webforj/component/AbstractOptionInput" code='true' suffix='#setText(java.lang.String)'>setText(String text)</JavadocLink>-Methode nutzen, die in der Nähe des Kontrollkästchens positioniert wird, gemäß der eingebauten <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink>.

Kontrollkästchen verfügen über eingebaute Funktionen, um den Text entweder rechts oder links von der Box anzuzeigen. Standardmäßig wird der Text rechts von der Komponente angezeigt. Die Positionierung des Textes wird durch die Verwendung der <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink>-Enum unterstützt. Unten sind die beiden Einstellungen dargestellt: <br/>

<ComponentDemo 
path='/webforj/checkboxhorizontaltext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxHorizontalTextView.java'
height = '200px'
/>

<br/>

## Indeterminismus {#indeterminism}

Die `CheckBox`-Komponente unterstützt Indeterminismus, ein UI-Muster, das häufig in Formularen und Listen verwendet wird, um anzuzeigen, dass eine Gruppe von Kontrollkästchen eine Mischung aus ausgewählten und nicht ausgewählten Zuständen aufweist. Dieser Zustand wird durch einen dritten visuellen Zustand dargestellt, der typischerweise als gefülltes Quadrat oder als Strich innerhalb des Kontrollkästchens angezeigt wird. Es gibt einige gängige Anwendungsfälle, die mit Indeterminismus verbunden sind:

- **Auswählen mehrerer Elemente**: Indeterminismus ist nützlich, wenn Benutzer mehrere Elemente aus einer Liste oder einer Auswahl von Optionen auswählen müssen. Es ermöglicht den Benutzern anzuzeigen, dass sie einige, aber nicht alle verfügbaren Auswahlmöglichkeiten auswählen möchten.

- **Hierarchische Daten**: Indeterminismus kann in Szenarien eingesetzt werden, in denen eine hierarchische Beziehung zwischen Kontrollkästchen besteht. Wenn beispielsweise Kategorien und Unterkategorien ausgewählt werden, kann Indeterminismus darstellen, dass einige Unterkategorien ausgewählt sind, während andere nicht ausgewählt sind, und die übergeordnete Komponente sich im indeterminierten Zustand befindet.

<ComponentDemo 
path='/webforj/checkboxindeterminate?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxIndeterminateView.java'
height = '150px'
/>

## Styling {#styling}

### Ausdehnungen {#expanses}

Die folgenden <JavadocLink type="foundation" location="com/webforj/component/Expanse"> Ausdehnungswerte </JavadocLink> ermöglichen eine schnelle Stilgestaltung ohne CSS. 
Ausdehnungen werden durch die Verwendung der `Expanse`-Enum-Klasse unterstützt. Unten sind die Ausdehnungen aufgelistet, die für die Kontrollkästchenkomponente unterstützt werden: <br/>

<ComponentDemo 
path='/webforj/checkboxexpanse?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxExpanseView.java'
height = '150px'
/>

<br/>

<TableBuilder name="Checkbox" />

## Beste Praktiken {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der `Checkbox`-Komponente sicherzustellen, beachten Sie die folgenden besten Praktiken:

1. **Optionen klar beschriften**: Geben Sie klare und prägnante Beschriftungen für jede `CheckBox`-Option an, um die Auswahl genau zu beschreiben. Beschriftungen sollten leicht verständlich und voneinander zu unterscheiden sein.

2. **Gruppieren Sie Kontrollkästchen**: Gruppieren Sie verwandte Kontrollkästchen, um ihre Zugehörigkeit anzuzeigen. Dies hilft den Benutzern zu verstehen, dass innerhalb einer bestimmten Gruppe mehrere Optionen ausgewählt werden können.

3. **Standardauswahl bereitstellen**: Wenn zutreffend, sollten Sie in Betracht ziehen, eine Standardauswahl für Kontrollkästchen bereitzustellen, um Benutzer bei ihrer ersten Begegnung mit den Optionen zu führen. Die Standardauswahl sollte mit der häufigsten oder bevorzugten Wahl übereinstimmen.

4. **Indeterminismus**: Wenn eine übergeordnete `CheckBox`-Komponente mehrere zugehörige Komponenten hat, bei denen einige aktiv und andere inaktiv sein können, verwenden Sie die indeterminate-Eigenschaft, um anzuzeigen, dass nicht alle `CheckBox`-Komponenten ausgewählt oder nicht ausgewählt sind.
