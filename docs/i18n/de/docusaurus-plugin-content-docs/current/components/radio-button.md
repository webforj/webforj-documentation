---
title: RadioButton
slug: radiobutton
sidebar_position: 95
description: >-
  Add a single-choice RadioButton with text positioning, activation modes, and
  grouping for mutually exclusive selections.
_i18n_hash: 32d2e2f74e7f255b901de15622e8e2cc
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

Die `RadioButton`-Komponente stellt eine einzelne Option dar, die ausgewählt oder abgewählt werden kann. Radio-Buttons sind typischerweise gruppiert, sodass das Auswählen eines Buttons automatisch die anderen abwählt, was den Benutzern ermöglicht, eine einzige Wahl aus einem Satz von sich gegenseitig ausschließenden Optionen zu treffen.

<!-- INTRO_END -->

## Anwendungen {#usages}

Der `RadioButton` wird am besten in Szenarien verwendet, in denen Benutzer eine einzige Auswahl aus einem vordefinierten Satz von Optionen treffen müssen. Hier sind einige Beispiele, wann der `RadioButton` verwendet werden sollte:

1. **Umfragen oder Fragebögen**: Radio-Buttons werden häufig in Umfragen oder Fragebögen verwendet, in denen Benutzer eine einzelne Antwort aus einer Liste von Optionen auswählen müssen.

2. **Präferenz-Einstellungen**: Anwendungen, die Präferenz- oder Einstellungsfenster umfassen, verwenden oft Radio-Buttons, um Benutzern zu ermöglichen, eine einzelne Option aus einer Reihe von sich gegenseitig ausschließenden Auswahlmöglichkeiten auszuwählen.

3. **Filtern oder Sortieren**: Ein `RadioButton` kann in Anwendungen verwendet werden, die von Benutzern verlangen, eine einzelne Filter- oder Sortieroption auszuwählen, z.B. um eine Liste von Elementen nach verschiedenen Kriterien zu sortieren.

:::tip Gruppierung von `RadioButton`-Komponenten
Verwenden Sie eine [`RadioButtonGroup`](/docs/components/radiobuttongroup), um eine Gruppe von Radio-Buttons zu verwalten, wenn Benutzer eine einzelne Option auswählen sollen.
:::

## Text und Positionierung {#text-and-positioning}

Radio-Buttons können die Methode ```setText(String text)``` verwenden, die in der Nähe des Radio-Buttons gemäß der integrierten `Position` positioniert wird. Radio-Buttons haben die integrierte Funktionalität, um Text entweder rechts oder links von der Komponente anzuzeigen. Standardmäßig wird der Text rechts von der Komponente angezeigt. Die Positionierung des horizontalen Textes wird durch die Verwendung der `HorizontalAlignment`-Enum-Klasse unterstützt. Unten sind die beiden Einstellungen dargestellt: <br/>

<ComponentDemo
path='/webforj/radiobuttontext'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java']}
height='120px'
/>


## Aktivierung {#activation}

Radio-Buttons können mit zwei Arten von Aktivierung gesteuert werden: manuelle Aktivierung und automatische Aktivierung. Diese bestimmen, wann ein `RadioButton` seinen Zustand ändern wird.

<ComponentDemo
path='/webforj/radiobuttonactivation'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java']}
height='175px'
/>

### Manuelle Aktivierung {#manual-activation}

Wenn ein Radio-Button auf manuelle Aktivierung eingestellt ist, bedeutet dies, dass er nicht automatisch aktiviert wird, wenn er den Fokus erhält. 
Die manuelle Aktivierung ermöglicht es dem Benutzer, durch die Radio-Button-Optionen mit der Tastatur oder anderen Eingabemethoden zu navigieren, ohne die ausgewählte Option sofort zu ändern.

Wenn der Radio-Button Teil einer Gruppe ist, wird das Auswählen eines anderen Radio-Buttons innerhalb der Gruppe automatisch den zuvor ausgewählten Radio-Button abwählen. Die manuelle Aktivierung bietet eine feiner abgestimmte Kontrolle über den Auswahlprozess, die eine explizite Aktion des Benutzers erfordert, um die ausgewählte Option zu ändern.


### Automatische Aktivierung {#auto-activation}

Die automatische Aktivierung ist der Standardzustand für einen `RadioButton` und bedeutet, dass der Button aktiviert wird, wann immer er aus irgendeinem Grund den Fokus erhält. Das bedeutet, dass sowohl Klicks als auch Auto-Fokus oder Tab-Navigation den Button aktivieren werden.

:::tip Hinweis
Der Standardwert für die Aktivierung ist **`MANUELLE`** Aktivierung.
:::


## Schalter {#switches}

Ein `RadioButton` kann auch so eingestellt werden, dass er als Schalter angezeigt wird, der eine alternative visuelle Darstellung zur Auswahl von Optionen bietet. Normalerweise sind Radio-Buttons kreisförmig oder abgerundet und zeigen eine einzelne Wahl aus einer Gruppe von Optionen an.

<ComponentDemo
path='/webforj/radiobuttonswitch'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java']}
height='120px'
/>

Ein `RadioButton` kann in einen Schalter verwandelt werden, der einem umschaltbaren Schalter oder Slider ähnelt, indem eine der zwei Methoden verwendet wird:

1. **Die Factory-Methode**: Der RadioButton kann mit den folgenden Factory-Methoden erstellt werden:

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

Diese Methoden spiegeln einen `RadioButton`-Konstruktor wider und erstellen die Komponente mit der Schaltereigenschaft, die bereits aktiviert ist.

2. **Setter**: Es ist auch möglich, einen bereits vorhandenen `RadioButton` in einen Schalter zu ändern, indem der entsprechende Setter verwendet wird:

```java
myRadioButton.setSwitch(true);
```


Wenn ein `RadioButton` als Schalter angezeigt wird, erscheint er typischerweise in oblong Form mit einem Indikator, der ein- oder ausgeschaltet werden kann. Diese visuelle Darstellung bietet den Benutzern eine intuitivere und vertrautere Benutzeroberfläche, ähnlich wie physische Schalter, die häufig in elektronischen Geräten zu finden sind.

Einen `RadioButton` so einzustellen, dass er als Schalter angezeigt wird, kann die Benutzererfahrung verbessern, indem es eine klare und unkomplizierte Möglichkeit bietet, Optionen auszuwählen. Es kann die visuelle Anziehungskraft und Benutzerfreundlichkeit von Formularen, Einstellungsfenstern oder anderen Benutzeroberflächenelementen, die mehrere Auswahlmöglichkeiten erfordern, verbessern.

:::info
Das Verhalten des `RadioButton` bleibt gleich, wenn er als Schalter dargestellt wird, d.h. es kann jeweils nur eine Option innerhalb einer Gruppe ausgewählt werden. Das schalterähnliche Erscheinungsbild ist eine visuelle Transformation, die die Funktionalität eines `RadioButton` beibehält.
:::

<br/>

## Styling {#styling}

### Ausdehnungen {#expanses}
Es gibt fünf Checkbox-Ausdehnungen, die unterstützt werden und eine schnelle Gestaltung ohne CSS ermöglichen.
Ausdehnungen werden durch die Verwendung der `Expanse`-Enum-Klasse unterstützt. Unten sind die für die Checkbox-Komponente unterstützten Ausdehnungen aufgeführt: <br/>

<TableBuilder name="RadioButton" />

## Best Practices {#best-practices}

Um eine optimale Benutzererfahrung bei der Verwendung der RadioButton-Komponente zu gewährleisten, sollten folgende Best Practices berücksichtigt werden:

1. **Optionen klar beschriften**: Stellen Sie klaren und präzisen Text für jede `RadioButton`-Option zur Verfügung, um die Wahl genau zu beschreiben. Der Text sollte leicht verständlich und voneinander unterscheidbar sein.

2. **Radio-Buttons gruppieren**: Gruppieren Sie verwandte Radio-Buttons zusammen, um deren Zugehörigkeit anzuzeigen. Dies hilft Benutzern zu verstehen, dass innerhalb einer bestimmten Gruppe nur eine Option ausgewählt werden kann. Dies kann effektiv mit der [`RadioButtonGroup`](/docs/components/radiobuttongroup) Komponente umgesetzt werden.

3. **Vorgabewahl anbieten**: Wenn möglich, sollten Sie eine Vorgabewahl für Radio-Buttons bereitstellen, um Benutzer zu leiten, wenn sie die Optionen zum ersten Mal sehen. Die Vorgabewahl sollte mit der gängigsten oder bevorzugten Wahl übereinstimmen.
