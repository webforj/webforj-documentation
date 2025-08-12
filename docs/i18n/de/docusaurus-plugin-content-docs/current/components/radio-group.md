---
title: RadioButtonGroup
slug: radiobuttongroup
sidebar_position: 100
_i18n_hash: 91d753e882e3d6d59deef5044ee7bc4c
---
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

Die Klasse `RadioButtonGroup` wird verwendet, um verwandte Radio-Buttons zusammenzufassen, was hilft, die gegenseitige Exklusivität der Optionen innerhalb dieser Gruppe zu gewährleisten. Benutzer können innerhalb einer bestimmten Radiogruppe nur einen Radio-Button auswählen. Wenn ein Benutzer einen Radio-Button innerhalb einer Gruppe auswählt, wird jeder zuvor ausgewählte Radio-Button in der gleichen Gruppe automatisch abgewählt. Dies stellt sicher, dass jeweils nur eine Option ausgewählt werden kann.

:::tip
Eine `RadioButton`-Komponente speichert die Gruppe, zu der sie gehört, die über die Methode `getButtonGroup()` abgerufen werden kann.
:::

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

:::important
Die `RadioButtonGroup`-Komponente rendert kein HTML-Element auf der Seite. Vielmehr handelt es sich nur um Logik, die sicherstellt, dass eine Gruppe von RadioButtons als Gruppe und nicht einzeln funktioniert.
:::

## Usages {#usages}

Die `RadioButtonGroup` eignet sich am besten für Szenarien, in denen Benutzer eine einzelne Auswahl aus einer definierten Menge von Optionen treffen müssen, die als Radio-Buttons präsentiert werden. Hier sind einige Beispiele, wann die `RadioButtonGroup` verwendet werden sollte:

1. **Umfragen oder Fragebögen**: `RadioButtonGroup`-Komponenten werden häufig in Umfragen oder Fragebögen verwendet, in denen Benutzer eine einzige Antwort aus einer Liste von Optionen auswählen müssen.

2. **Einstellung von Präferenzen**: Anwendungen, die Einstellungen oder Präferenz-Panels beinhalten, verwenden oft die RadioButtonGroup-Komponente, um Benutzern die Auswahl einer einzelnen Option aus einer Menge von gegenseitig ausschließenden Auswahlmöglichkeiten zu ermöglichen.

3. **Filtern oder Sortieren**: Ein `RadioButton` kann in Anwendungen verwendet werden, die von Benutzern verlangen, eine einzelne Filter- oder Sortieroption auszuwählen, wie das Sortieren einer Liste von Elementen nach verschiedenen Kriterien.

<!-- vale off -->
## Hinzufügen und Entfernen von RadioButtons {#adding-and-removing-radiobuttons}
<!-- vale on -->

Es ist möglich, einzelne oder mehrere `RadioButton`-Objekte zu einer Gruppe hinzuzufügen oder zu entfernen, wobei sichergestellt wird, dass sie sich gegenseitig ausschließen und mit einem beliebigen Namen, der zur Gruppe gehören kann, verknüpft sind.

## Benennung {#naming}

Das Namensattribut in einer `RadioButtonGroup` gruppiert verwandte RadioButtons, sodass Benutzer eine einzelne Wahl aus den bereitgestellten Optionen treffen und die Exklusivität der RadioButtons durchsetzen können. Der Name einer Gruppe wird jedoch nicht im DOM angezeigt und ist ein Hilfswerkzeug für den Java-Entwickler.

## Best Practices {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der RadioButton-Komponente zu gewährleisten, beachten Sie die folgenden Best Practices:

1. **Klar beschriftete Optionen**: Stellen Sie klare und prägnante Beschriftungen für jede `RadioButton`-Option bereit, um die Wahl genau zu beschreiben. Die Beschriftungen sollten leicht verständlich und voneinander zu unterscheiden sein.

2. **Standardauswahl bereitstellen**: Wenn zutreffend, sollten Sie in Betracht ziehen, eine Standardauswahl für Radio-Buttons bereitzustellen, um Benutzer zu leiten, wenn sie zum ersten Mal mit den Optionen konfrontiert werden. Die Standardauswahl sollte der häufigsten oder bevorzugten Wahl entsprechen.
