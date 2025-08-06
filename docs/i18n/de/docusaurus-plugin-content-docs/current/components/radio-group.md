---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
_i18n_hash: 8e58efd7b052a00eaf8cfce276cda92e
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

Die `RadioButtonGroup`-Klasse wird verwendet, um verwandte Radio-Buttons zusammenzufassen, was dazu beiträgt, die gegenseitige Exklusivität der Optionen innerhalb dieser Gruppe zu gewährleisten. Benutzer können innerhalb einer bestimmten Radio-Gruppe nur einen Radio-Button auswählen. Wenn ein Benutzer einen Radio-Button innerhalb einer Gruppe auswählt, wird jeder zuvor ausgewählte Radio-Button in der gleichen Gruppe automatisch deaktiviert. Dies stellt sicher, dass nur eine Option zu einem Zeitpunkt gewählt werden kann.

:::tip
Eine `RadioButton`-Komponente speichert die Gruppe, zu der sie gehört, die über die Methode `getButtonGroup()` abgerufen werden kann.
:::

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

:::important
Die `RadioButtonGroup`-Komponente rendert kein HTML-Element auf der Seite. Vielmehr handelt es sich nur um eine Logik, die sicherstellt, dass eine Gruppe von RadioButtons wie eine Gruppe und nicht einzeln verhält.
:::

## Verwendungen {#usages}

Die `RadioButtonGroup` eignet sich am besten für Szenarien, in denen Benutzer eine einzige Auswahl aus einer vordefinierten Reihe von Optionen treffen müssen, die als Radio-Buttons präsentiert werden. Hier sind einige Beispiele, wann die `RadioButtonGroup` verwendet werden sollte:

1. **Umfragen oder Fragebögen**: `RadioButtonGroup`-Komponenten werden häufig in Umfragen oder Fragebögen verwendet, in denen Benutzer eine einzelne Antwort aus einer Liste von Optionen auswählen müssen.

2. **Einstellungspräferenzen**: Anwendungen, die Präferenz- oder Einstellungspanels beinhalten, nutzen oft die RadioButtonGroup-Komponente, um Benutzern die Auswahl einer einzigen Option aus einer Gruppe von sich gegenseitig ausschließenden Auswahlmöglichkeiten zu ermöglichen.

3. **Filtern oder Sortieren**: Ein `RadioButton` kann in Anwendungen verwendet werden, die von Benutzern verlangen, eine einzelne Filter- oder Sortieroption auszuwählen, wie z. B. das Sortieren einer Liste von Elementen nach unterschiedlichen Kriterien.

<!-- vale off -->
## Hinzufügen und Entfernen von RadioButtons {#adding-and-removing-radiobuttons}
<!-- vale on -->

Es ist möglich, einzelne oder mehrere `RadioButton`-Objekte zu einer Gruppe hinzuzufügen oder zu entfernen, um sicherzustellen, dass sie ein gegenseitig exklusives Auswahlverhalten aufweisen und mit jedem Namen assoziiert sind, der zur Gruppe gehören kann.

## Benennung {#naming}

Das Namensattribut in einer `RadioButtonGroup` fasst verwandte RadioButtons zusammen, sodass Benutzer eine einzelne Wahl aus den bereitgestellten Optionen treffen können, wobei die Exklusivität unter den RadioButtons durchgesetzt wird. Der Name einer Gruppe wird nicht im DOM widergespiegelt, ist jedoch ein praktisches Hilfsmittel für den Java-Entwickler.

## Beste Praktiken {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der RadioButton-Komponente zu gewährleisten, sollten die folgenden besten Praktiken beachtet werden:

1. **Optionen Deutlich Beschriften**: Bereitstellung klarer und prägnanter Labels für jede `RadioButton`-Option, um die Wahl genau zu beschreiben. Labels sollten leicht verständlich und voneinander unterscheidbar sein.

2. **Standardauswahl Bereitstellen**: Falls zutreffend, sollte in Betracht gezogen werden, eine Standardauswahl für Radio-Buttons bereitzustellen, um Benutzer zu leiten, wenn sie zum ersten Mal auf die Optionen stoßen. Die Standardauswahl sollte mit der häufigsten oder bevorzugten Wahl übereinstimmen.
