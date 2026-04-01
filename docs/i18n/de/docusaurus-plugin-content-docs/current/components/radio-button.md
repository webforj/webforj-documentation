---
title: RadioButton
slug: radiobutton
sidebar_position: 95
_i18n_hash: 19e51a9c57a6524781ac008abcebc790
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

Die `RadioButton`-Komponente stellt eine einzelne Option dar, die ausgewählt oder abgewählt werden kann. Radiobuttons sind typischerweise gruppiert, sodass die Auswahl einer Option automatisch die anderen abwählt, wodurch es den Benutzern ermöglicht wird, eine einzelne Wahl aus einer Menge von sich gegenseitig ausschließenden Optionen zu treffen.

<!-- INTRO_END -->

## Usages {#usages}

Der `RadioButton` eignet sich am besten für Szenarien, in denen Benutzer eine einzelne Auswahl aus einer vordefinierten Menge von Optionen treffen müssen. Hier sind einige Beispiele, wann der `RadioButton` verwendet werden sollte:

1. **Umfragen oder Fragebögen**: Radiobuttons werden häufig in Umfragen oder Fragebögen verwendet, in denen Benutzer eine einzelne Antwort aus einer Liste von Optionen auswählen müssen.

2. **Präferenzeinstellungen**: Anwendungen, die mit Präferenz- oder Einstellungsfeldern zu tun haben, verwenden oft Radiobuttons, um den Benutzern die Auswahl einer einzelnen Option aus einer Menge von sich gegenseitig ausschließenden Möglichkeiten zu ermöglichen.

3. **Filterung oder Sortierung**: Ein `RadioButton` kann in Anwendungen verwendet werden, die von Benutzern verlangen, eine einzelne Filter- oder Sortieroption auszuwählen, wie z.B. das Sortieren einer Liste von Elementen nach verschiedenen Kriterien.

:::tip Gruppierung von `RadioButton`-Komponenten
Verwenden Sie eine [`RadioButtonGroup`](/docs/components/radiobuttongroup), um eine Menge von Radiobuttons zu verwalten, wenn Sie möchten, dass die Benutzer eine einzige Option auswählen.
:::

## Text und Positionierung {#text-and-positioning}

Radiobuttons können die ```setText(String text)```-Methode nutzen, die in der Nähe des Radiobuttons in Übereinstimmung mit der integrierten `Position` positioniert wird. Radiobuttons haben eine integrierte Funktionalität, um Text, der entweder rechts oder links von der Komponente angezeigt wird, festzulegen. Standardmäßig wird der Text rechts von der Komponente angezeigt. Die Positionierung des horizontalen Texts wird durch die Verwendung der Enum-Klasse `HorizontalAlignment` unterstützt. Unten sind die beiden Einstellungen aufgeführt: <br/>

<ComponentDemo 
path='/webforj/radiobuttontext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java'
height="120px"
/>


## Aktivierung {#activation}

Radiobuttons können mit zwei Arten von Aktivierung gesteuert werden: manuelle Aktivierung und automatische Aktivierung. Diese bestimmen, wann ein `RadioButton` seinen Zustand ändern wird.

<ComponentDemo 
path='/webforj/radiobuttonactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java'
height="175px"
/>

### Manuelle Aktivierung {#manual-activation}

Wenn ein Radiobutton auf manuelle Aktivierung eingestellt ist, bedeutet dies, dass er nicht automatisch aktiviert wird, wenn er den Fokus erhält. Manuelle Aktivierung ermöglicht es dem Benutzer, durch die Radiobutton-Optionen mithilfe der Tastatur oder anderer Eingabemethoden zu navigieren, ohne sofort die ausgewählte Option zu ändern.

Wenn der Radiobutton Teil einer Gruppe ist, wird das Auswählen eines anderen Radiobuttons innerhalb der Gruppe automatisch den zuvor ausgewählten Radiobutton abwählen. Manuelle Aktivierung bietet eine feinere Kontrolle über den Auswahlprozess, da eine ausdrückliche Aktion des Benutzers erforderlich ist, um die ausgewählte Option zu ändern.

### Automatische Aktivierung {#auto-activation}

Die automatische Aktivierung ist der Standardzustand für einen `RadioButton` und bedeutet, dass die Schaltfläche jedes Mal aktiviert wird, wenn sie aus irgendeinem Grund den Fokus erhält. Das bedeutet, dass nicht nur ein Klick, sondern auch automatisches Fokussieren oder Tab-Navigation die Schaltfläche aktiviert.

:::tip Hinweis
Der Standardwert für die Aktivierung ist **`MANUAL`** Aktivierung.
:::


## Schalter {#switches}

Ein `RadioButton` kann auch als Schalter angezeigt werden, der eine alternative visuelle Darstellung zur Auswahl von Optionen bietet. Normalerweise sind Radiobuttons kreisförmig oder abgerundet und zeigen eine einzelne Wahl aus einer Gruppe von Optionen an.

<ComponentDemo 
path='/webforj/radiobuttonswitch?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java'
height="120px"
/>

Ein `RadioButton` kann mithilfe einer von zwei Methoden in einen Schalter verwandelt werden:

1. **Die Fabrikmethode**: Der Radiobutton kann mit den folgenden Fabrikmethoden erstellt werden:

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

Diese Methoden spiegeln einen `RadioButton`-Konstruktor wider und erstellen die Komponente mit dem Schalter, der bereits aktiviert ist.

2. **Setter**: Es ist auch möglich, einen bereits vorhandenen `RadioButton` in einen Schalter zu ändern, indem der entsprechende Setter verwendet wird:

```java
myRadioButton.setSwitch(true);
```

Wenn ein `RadioButton` als Schalter angezeigt wird, erscheint er typischerweise als längliche Form mit einem Indikator, der ein- oder ausgeschaltet werden kann. Diese visuelle Darstellung gibt den Benutzern eine intuitivere und vertrautere Oberfläche, ähnlich wie physische Schalter, die häufig in elektronischen Geräten zu finden sind.

Einen `RadioButton` so einzustellen, dass er als Schalter angezeigt wird, kann das Benutzererlebnis verbessern, indem er eine klare und unkomplizierte Möglichkeit bietet, Optionen auszuwählen. Es kann die visuelle Anziehungskraft und Benutzerfreundlichkeit von Formularen, Einstellungsfeldern oder jedem anderen Interface-Element verbessern, das mehrere Auswahlmöglichkeiten erfordert.

:::info
Das Verhalten des `RadioButton` bleibt das gleiche, wenn er als Schalter gerendert wird, was bedeutet, dass immer nur eine Option innerhalb einer Gruppe ausgewählt werden kann. Das schalterähnliche Erscheinungsbild ist eine visuelle Transformation, die die Funktionalität eines `RadioButton` beibehält.
:::

<br/>

## Styling {#styling}

### Ausmaße {#expanses}
Es gibt fünf Checkbox-Ausmaße, die unterstützt werden, die eine schnelle Gestaltung ohne Verwendung von CSS ermöglichen. Ausmaße werden durch die Verwendung der Enum-Klasse `Expanse` unterstützt. Unten sind die für die Checkbox-Komponente unterstützten Ausmaße aufgeführt: <br/>

<TableBuilder name="RadioButton" />

## Beste Praktiken {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der `RadioButton`-Komponente zu gewährleisten, sollten die folgenden besten Praktiken beachtet werden:

1. **Optionen klar kennzeichnen**: Stellen Sie klaren und prägnanten Text für jede `RadioButton`-Option zur Verfügung, um die Wahl genau zu beschreiben. Der Text sollte leicht verständlich und unterscheidbar sein.

2. **Radiobuttons gruppieren**: Gruppieren Sie verwandte Radiobuttons zusammen, um ihre Assoziation anzuzeigen. Dies hilft den Benutzern zu verstehen, dass nur eine Option innerhalb einer bestimmten Gruppe ausgewählt werden kann. Dies kann effektiv unter Verwendung der [`RadioButtonGroup`](/docs/components/radiobuttongroup) Komponente durchgeführt werden.

3. **Standardauswahl bereitstellen**: Wenn möglich, sollten Sie in Betracht ziehen, eine Standardauswahl für Radiobuttons bereitzustellen, um den Benutzern zu helfen, wenn sie erstmals mit den Optionen konfrontiert werden. Die Standardauswahl sollte mit der häufigsten oder bevorzugten Wahl übereinstimmen.
