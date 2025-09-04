---
title: RadioButton
slug: radiobutton
sidebar_position: 95
_i18n_hash: efd1171b68ca07b593064abe0366ded7
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

Die Klasse `RadioButton` erstellt ein Objekt, das ausgewählt oder abgewählt werden kann und seinen Zustand dem Benutzer anzeigt. Nach der Konvention kann in einer Gruppe nur ein Radio-Button gleichzeitig ausgewählt sein. Radio-Buttons werden häufig verwendet, wenn sich gegenseitig ausschließende Optionen verfügbar sind, sodass der Benutzer eine einzelne Option aus einer Auswahl wählen kann.

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

## Verwendungen {#usages}

Der `RadioButton` wird am besten in Szenarien verwendet, in denen Benutzer eine einzige Auswahl aus einem vordefinierten Satz von Optionen treffen müssen. Hier sind einige Beispiele, wann der `RadioButton` verwendet werden sollte:

1. **Umfragen oder Fragebögen**: Radio-Buttons werden häufig in Umfragen oder Fragebögen verwendet, in denen Benutzer eine einzige Antwort aus einer Liste von Optionen auswählen müssen.

2. **Einstellungen für Präferenzen**: Anwendungen, die Präferenzen oder Einstellungsfenster umfassen, verwenden oft Radio-Buttons, um den Benutzern die Auswahl einer einzigen Option aus einer Gruppe von sich gegenseitig ausschließenden Optionen zu ermöglichen.

3. **Filtern oder Sortieren**: Ein `RadioButton` kann in Anwendungen verwendet werden, die es den Benutzern ermöglichen, eine einzelne Filter- oder Sortieroption auszuwählen, z. B. eine Liste von Elementen nach verschiedenen Kriterien zu sortieren.

## Text und Positionierung {#text-and-positioning}

Radio-Buttons können die Methode ```setText(String text)``` nutzen, die in der Nähe des Radio-Buttons positioniert wird, gemäß der integrierten `Position`. 
Radio-Buttons haben eine integrierte Funktionalität, um Text anzuzeigen, der entweder rechts oder links von der Komponente angezeigt wird. Standardmäßig wird der Text rechts von der Komponente angezeigt. Die Positionierung des horizontalen Texts wird durch die Verwendung der `HorizontalAlignment`-Enum-Klasse unterstützt. Unten sind die beiden Einstellungen aufgeführt: <br/>

<ComponentDemo 
path='/webforj/radiobuttontext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java'
height="120px"
/>

## Aktivierung {#activation}

Radio-Buttons können mit zwei Arten von Aktivierung gesteuert werden: manuelle Aktivierung und automatische Aktivierung. Diese bestimmen, wann ein `RadioButton` seinen Zustand ändert.

<ComponentDemo 
path='/webforj/radiobuttonactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java'
height="175px"
/>

### Manuelle Aktivierung {#manual-activation}

Wenn ein Radio-Button auf manuelle Aktivierung eingestellt ist, bedeutet dies, dass er nicht automatisch aktiviert wird, wenn er den Fokus erhält. 
Die manuelle Aktivierung ermöglicht es dem Benutzer, durch die Radio-Button-Optionen mit der Tastatur oder anderen Eingabemethoden zu navigieren, ohne sofort die ausgewählte Option zu ändern.

Wenn der Radio-Button Teil einer Gruppe ist, wird das Auswählen eines anderen Radio-Buttons innerhalb der Gruppe den zuvor ausgewählten Radio-Button automatisch abwählen. 
Die manuelle Aktivierung bietet eine feinere Kontrolle über den Selektionsprozess, da eine explizite Aktion des Benutzers erforderlich ist, um die ausgewählte Option zu ändern.

### Automatische Aktivierung {#auto-activation}

Die automatische Aktivierung ist der Standardzustand für einen `RadioButton` und bedeutet, dass der Button aktiviert wird, wenn er aus irgendeinem Grund den Fokus erhält. 
Das bedeutet, dass nicht nur ein Klick, sondern auch Autofokus oder die Tab-Navigation den Button aktivieren.

:::tip Hinweis
Der Standardaktivierungswert ist **`MANUELL`** aktivierung.
:::

## Schalter {#switches}

Ein `RadioButton` kann auch so eingestellt werden, dass er als Schalter angezeigt wird, was eine alternative visuelle Darstellung zur Auswahl von Optionen bietet. Normalerweise sind Radio-Buttons rund oder abgerundet und zeigen eine einzige Wahl aus einer Gruppe von Optionen an.

<ComponentDemo 
path='/webforj/radiobuttonswitch?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java'
height="120px"
/>

Ein `RadioButton` kann in einen Schalter verwandelt werden, der einem Kippschalter oder Schieberegler ähnelt, indem eine der beiden Methoden verwendet wird:

1. **Die Fabrikmethode**: Der RadioButton kann unter Verwendung der folgenden Fabrikmethoden erstellt werden:

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

Diese Methoden spiegeln einen `RadioButton`-Konstruktor wider und erstellen die Komponente mit der Schalter-Eigenschaft bereits aktiviert.

2. **Setter**: Es ist auch möglich, einen bereits vorhandenen `RadioButton` in einen Schalter zu ändern, indem der entsprechende Setter verwendet wird:

```java
myRadioButton.setSwitch(true);
```

Wenn ein `RadioButton` als Schalter angezeigt wird, sieht er typischerweise wie eine längliche Form mit einem Indikator aus, der ein- oder ausgeschaltet werden kann. Diese visuelle Darstellung bietet den Benutzern eine intuitivere und vertrautere Schnittstelle, ähnlich zu physischen Schaltern, die häufig in elektronischen Geräten zu finden sind.

Einen `RadioButton` so einzustellen, dass er als Schalter angezeigt wird, kann die Benutzererfahrung verbessern, indem eine klare und direkte Möglichkeit zur Auswahl von Optionen bereitgestellt wird. Es kann die visuelle Attraktivität und Benutzbarkeit von Formularen, Einstellungsfenstern oder anderen Schnittstellenelementen, die mehrere Auswahlmöglichkeiten erfordern, steigern.

:::info
Das Verhalten des `RadioButton` bleibt dasselbe, wenn er als Schalter dargestellt wird, was bedeutet, dass innerhalb einer Gruppe nur eine Option gleichzeitig ausgewählt werden kann. Das schalterartige Erscheinungsbild ist eine visuelle Transformation, die die Funktionalität eines `RadioButton` beibehält.
:::

<br/>

## Stilgestaltung {#styling}

### Erweiterungen {#expanses}
Es gibt fünf Checkbox-Erweiterungen, die unterstützt werden und eine schnelle Stilgestaltung ohne CSS ermöglichen.
Erweiterungen werden durch die Verwendung der `Expanse`-Enum-Klasse unterstützt. Unten sind die unterstützten Erweiterungen für die Checkbox-Komponente aufgeführt: <br/>

<TableBuilder name="RadioButton" />

## Best Practices {#best-practices}

Um eine optimale Benutzererfahrung bei der Verwendung der RadioButton-Komponente sicherzustellen, sollten die folgenden Best Practices berücksichtigt werden:

1. **Optionen klar kennzeichnen**: Stellen Sie klaren und prägnanten Text für jede `RadioButton`-Option bereit, um die Wahl genau zu beschreiben. Der Text sollte leicht verständlich und voneinander zu unterscheiden sein.

2. **Radio-Buttons gruppieren**: Gruppieren Sie verwandte Radio-Buttons, um deren Zugehörigkeit anzuzeigen. Dies hilft den Benutzern zu verstehen, dass innerhalb einer bestimmten Gruppe nur eine Option ausgewählt werden kann. Dies kann effektiv mithilfe der [`RadioButtonGroup`](/docs/components/radiobuttongroup) Komponente durchgeführt werden.

3. **Standardauswahl anbieten**: Falls zutreffend, erwägen Sie, eine Standardauswahl für Radio-Buttons bereitzustellen, um Benutzern bei der ersten Begegnung mit den Optionen zu helfen. Die Standardauswahl sollte mit der häufigsten oder bevorzugten Wahl übereinstimmen.
