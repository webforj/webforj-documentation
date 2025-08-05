---
title: CheckBox
sidebar_position: 20
_i18n_hash: 0c55e1e2b7f92aa8f1f65151bfa3e096
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-checkbox" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/CheckBox" top='true'/>

Die Klasse `CheckBox` erstellt eine Komponente, die ausgewählt oder abgewählt werden kann und ihren Zustand dem Benutzer anzeigt. Bei einem Klick erscheint ein Häkchen im Feld, um eine bejahende Wahl (ein) anzuzeigen. Bei einem weiteren Klick verschwindet das Häkchen, was eine negative Wahl (aus) anzeigt.

Durch die Bereitstellung einer klaren und einfachen visuellen Indikation des Auswahlstatus verbessern Kontrollkästchen die Benutzerinteraktion und Entscheidungsfindung, weshalb sie ein wichtiges Element moderner Benutzeroberflächen sind.

## Verwendungen {#usages}

Das `CheckBox` ist am besten in Szenarien geeignet, in denen Benutzer mehrere Auswahlen aus einer Liste von Optionen treffen müssen. Hier sind einige Beispiele, wann das `CheckBox` verwendet werden sollte:

1. **Auswahl von Aufgaben oder Funktionen**: Kontrollkästchen werden häufig verwendet, wenn Benutzer mehrere Aufgaben oder Funktionen auswählen müssen, um bestimmte Aktionen oder Konfigurationen durchzuführen.

2. **Einstellungen zur Präferenz**: Anwendungen, die mit Präferenzen oder Einstellungsfenstern zu tun haben, verwenden oft Kontrollkästchen, um Benutzern die Auswahl mehrerer Optionen aus einer Reihe von Auswahlmöglichkeiten zu ermöglichen. Dies ist am besten für Optionen geeignet, die sich nicht gegenseitig ausschließen. Zum Beispiel:

> - Benachrichtigungen aktivieren oder deaktivieren
> - Auswahl eines dunklen oder hellen Modus-Themes
> - Auswahl der E-Mail-Benachrichtigungspräferenzen

3. **Filtern oder Sortieren**: Ein `CheckBox` kann in Anwendungen verwendet werden, die von Benutzern erfordern, mehrere Filter oder Kategorien auszuwählen, z. B. beim Filtern von Suchergebnissen oder beim Auswählen mehrerer Elemente für weitere Aktionen.

4. **Formular-Eingaben**: Kontrollkästchen werden häufig in Formularen verwendet, um Benutzern die Auswahl mehrerer Optionen oder binaerer Entscheidungen zu ermöglichen. Zum Beispiel:
   > - Anmeldung für einen Newsletter
   > - Zustimmung zu den Geschäftsbedingungen
   > - Auswahl von Artikeln für den Kauf oder die Buchung

## Text und Positionierung {#text-and-positioning}

Kontrollkästchen können die <JavadocLink type="foundation" location="com/webforj/component/AbstractOptionInput" code='true' suffix='#setText(java.lang.String)'>setText(String text)</JavadocLink>-Methode nutzen, die in der Nähe des Kontrollkästchens gemäß der integrierten <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink> positioniert wird.

Kontrollkästchen verfügen über integrierte Funktionen, um Text entweder rechts oder links des Feldes anzuzeigen. Standardmäßig wird der Text rechts von der Komponente angezeigt. Die Positionierung des Textes wird durch die Verwendung der <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink>-Enum unterstützt. Unten sind die beiden Einstellungen dargestellt: <br/>

<ComponentDemo 
path='/webforj/checkboxhorizontaltext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxHorizontalTextView.java'
height = '200px'
/>

<br/>

## Indeterminismus {#indeterminism}

Die `CheckBox`-Komponente unterstützt Indeterminismus, ein UI-Muster, das häufig in Formularen und Listen verwendet wird, um anzuzeigen, dass eine Gruppe von Kontrollkästchen eine Mischung aus ausgewählten und nicht ausgewählten Zuständen hat. Dieser Zustand wird durch einen dritten visuellen Zustand dargestellt, der typischerweise als gefülltes Quadrat oder ein Strich im Kontrollkästchen angezeigt wird. Es gibt einige häufige Anwendungsfälle für Indeterminismus:

- **Auswahl mehrerer Elemente**: Indeterminismus ist nützlich, wenn Benutzer mehrere Elemente aus einer Liste oder einer Reihe von Optionen auswählen müssen. Er ermöglicht den Benutzern, anzuzeigen, dass sie einige, aber nicht alle verfügbaren Optionen auswählen möchten.

- **Hierarchische Daten**: Indeterminismus kann in Szenarien eingesetzt werden, in denen es eine hierarchische Beziehung zwischen Kontrollkästchen gibt. Zum Beispiel, wenn Kategorien und Unterkategorien ausgewählt werden, kann Indeterminismus darstellen, dass einige Unterkategorien ausgewählt sind, während andere dies nicht sind, und die übergeordnete Komponente im unbestimmten Zustand ist.

<ComponentDemo 
path='/webforj/checkboxindeterminate?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxIndeterminateView.java'
height = '150px'
/>

## Stilgebung {#styling}

### Größen {#expanses}

Die folgenden <JavadocLink type="foundation" location="com/webforj/component/Expanse"> Größenwerte </JavadocLink> ermöglichen eine schnelle Stilgebung, ohne CSS verwenden zu müssen. Größen werden durch die Verwendung der `Expanse`-Enum-Klasse unterstützt. Unten sind die Größen aufgeführt, die für die Kontrollkästchenkomponente unterstützt werden: <br/>

<ComponentDemo 
path='/webforj/checkboxexpanse?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxExpanseView.java'
height = '150px'
/>

<br/>

<TableBuilder name="Checkbox" />

## Beste Praktiken {#best-practices}

Um ein optimales Benutzererlebnis beim Einsatz der `Checkbox`-Komponente sicherzustellen, sollten die folgenden Best Practices berücksichtigt werden:

1. **Optionen klar kennzeichnen**: Geben Sie klare und prägnante Bezeichnungen für jede `CheckBox`-Option an, um die Wahl genau zu beschreiben. Die Labels sollten leicht verständlich und voneinander unterscheidbar sein.

2. **Kontrollkästchen gruppieren**: Gruppieren Sie verwandte Kontrollkästchen, um ihre Assoziation zu kennzeichnen. Dies hilft Benutzern zu verstehen, dass mehrere Optionen innerhalb einer bestimmten Gruppe ausgewählt werden können.

3. **Standardauswahl bereitstellen**: Wenn zutreffend, erwägen Sie, eine Standardauswahl für die Kontrollkästchen anzubieten, um Benutzern zu helfen, wenn sie zuerst auf die Optionen stoßen. Die Standardauswahl sollte mit der häufigsten oder bevorzugten Wahl übereinstimmen.

4. **Indeterminismus**: Wenn eine übergeordnete `CheckBox`-Komponente mehrere zugehörige Komponenten hat, bei denen einige aktiviert und andere deaktiviert werden können, verwenden Sie die indeterminate-Eigenschaft, um anzuzeigen, dass nicht alle `CheckBox`-Komponenten aktiviert oder deaktiviert sind.
