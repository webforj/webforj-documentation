---
title: RadioButton
slug: radiobutton
sidebar_position: 95
_i18n_hash: 491fdadd826e3b34acc02b8833704faf
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

Die `RadioButton`-Komponente stellt eine einzelne Option dar, die ausgewählt oder abgewählt werden kann. Radio-Buttons werden typischerweise zusammen gruppiert, sodass die Auswahl einer automatisch die anderen abwählt, wodurch Benutzer eine einzige Wahl aus einem Satz von sich gegenseitig ausschließenden Optionen treffen können.

<!-- INTRO_END -->

## Verwendungszwecke {#usages}

Der `RadioButton` eignet sich am besten für Szenarien, in denen Benutzer eine einzige Auswahl aus einem vordefinierten Satz von Optionen treffen müssen. Hier sind einige Beispiele, wann der `RadioButton` verwendet werden sollte:

1. **Umfragen oder Fragebögen**: Radio-Buttons werden häufig in Umfragen oder Fragebögen verwendet, in denen Benutzer eine einzige Antwort aus einer Liste von Optionen auswählen müssen.

2. **Einstellungsmöglichkeiten**: Anwendungen, die Einstellungen oder Einstellungsfenster beinhalten, verwenden oft Radio-Buttons, um Benutzern zu ermöglichen, eine einzige Option aus einer Gruppe von sich gegenseitig ausschließenden Auswahlmöglichkeiten auszuwählen.

3. **Filtern oder Sortieren**: Ein `RadioButton` kann in Anwendungen verwendet werden, die erfordern, dass Benutzer eine einzelne Filter- oder Sortierungsoption auswählen, wie z. B. die Sortierung einer Liste von Elementen nach verschiedenen Kriterien.

:::tip Gruppierung von `RadioButton`-Komponenten
Verwenden Sie eine [`RadioButtonGroup`](/docs/components/radiobuttongroup), um eine Gruppe von Radio-Buttons zu verwalten, wenn Sie möchten, dass Benutzer eine einzelne Option auswählen.
:::

## Text und Positionierung {#text-and-positioning}

Radio-Buttons können die ```setText(String text)```-Methode verwenden, die in der Nähe des Radio-Buttons positioniert wird, gemäß der integrierten `Position`. Radio-Buttons verfügen über eine integrierte Funktionalität, um den Text entweder rechts oder links von der Komponente anzuzeigen. Standardmäßig wird der Text rechts von der Komponente angezeigt. Die Positionierung des horizontalen Textes wird durch die Verwendung der `HorizontalAlignment`-Enum-Klasse unterstützt. Hier sind die beiden Einstellungen: <br/>

<ComponentDemo
path='/webforj/radiobuttontext'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java']}
height='120px'
/>


## Aktivierung {#activation}

Radio-Buttons können mit zwei Arten von Aktivierungen gesteuert werden: manueller Aktivierung und automatischer Aktivierung. Diese bestimmen, wann ein `RadioButton` seinen Status ändert.

<ComponentDemo
path='/webforj/radiobuttonactivation'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java']}
height='175px'
/>

### Manuelle Aktivierung {#manual-activation}

Wenn ein Radio-Button auf manuelle Aktivierung eingestellt ist, bedeutet dies, dass er nicht automatisch markiert wird, wenn er den Fokus erhält. 
Die manuelle Aktivierung ermöglicht es dem Benutzer, durch die Radio-Button-Optionen mit der Tastatur oder anderen Eingabemethoden zu navigieren, ohne sofort die ausgewählte Option zu ändern.

Wenn der Radio-Button Teil einer Gruppe ist, wird das Auswählen eines anderen Radio-Buttons innerhalb der Gruppe automatisch den zuvor ausgewählten Radio-Button abwählen. 
Die manuelle Aktivierung bietet eine genauere Kontrolle über den Auswahlprozess, erfordert eine ausdrückliche Aktion des Benutzers, um die ausgewählte Option zu ändern.


### Automatische Aktivierung {#auto-activation}

Die automatische Aktivierung ist der Standardzustand für einen `RadioButton` und bedeutet, dass der Button bei jedem Erhalt des Fokus aus irgendeinem Grund aktiviert wird. Das bedeutet, dass nicht nur ein Klick, sondern auch Autofokus oder Tab-Navigation den Button auswählen.

:::tip Hinweis
Der Standardwert für die Aktivierung ist **`MANUAL`**-Aktivierung.
:::


## Schalter {#switches}

Ein `RadioButton` kann auch so eingestellt werden, dass er als Schalter angezeigt wird, der eine alternative visuelle Darstellung zur Auswahl von Optionen bietet. Normalerweise haben Radio-Buttons eine kreisförmige oder abgerundete Form und geben eine einzelne Wahl aus einer Gruppe von Optionen an.

<ComponentDemo
path='/webforj/radiobuttonswitch'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java']}
height='120px'
/>

Ein `RadioButton` kann mit einer der zwei Methoden in einen Schalter verwandelt werden:

1. **Die Fabrikmethode**: Der RadioButton kann mit den folgenden Fabrikmethoden erstellt werden:

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


Wenn ein `RadioButton` als Schalter angezeigt wird, erscheint er typischerweise in einer länglichen Form mit einem Indikator, der ein- oder ausgeschaltet werden kann. Diese visuelle Darstellung bietet den Benutzern eine intuitivere und vertrautere Benutzeroberfläche, ähnlich wie physische Schalter, die häufig in elektronischen Geräten zu finden sind. 

Die Einstellung eines `RadioButton` als Schalter kann die Benutzererfahrung verbessern, indem eine klare und unkomplizierte Möglichkeit zur Auswahl von Optionen angeboten wird. Sie kann die visuelle Anziehungskraft und Benutzerfreundlichkeit von Formularen, Einstellungsfenstern oder anderen Benutzeroberflächenelementen, die mehrere Auswahlmöglichkeiten erfordern, erhöhen.

:::info
Das Verhalten des `RadioButton` bleibt gleich, wenn er als Schalter gerendert wird, das bedeutet, dass immer nur eine Option innerhalb einer Gruppe ausgewählt werden kann. Das schalterähnliche Aussehen ist eine visuelle Transformation, die die Funktionalität eines `RadioButton` beibehält.
:::

<br/>

## Stil {#styling}

### Erweiterungen {#expanses}
Es gibt fünf Checkbox-Erweiterungen, die unterstützt werden und eine schnelle Stilgebung ohne CSS ermöglichen.
Erweiterungen werden durch die Verwendung der `Expanse`-Enum-Klasse unterstützt. Im Folgenden sind die unterstützten Erweiterungen für die Checkbox-Komponente aufgeführt: <br/>

<TableBuilder name="RadioButton" />

## Best Practices {#best-practices}

Um eine optimale Benutzererfahrung bei der Verwendung der RadioButton-Komponente sicherzustellen, sollten die folgenden Best Practices berücksichtigt werden:

1. **Optionen klar kennzeichnen**: Bereitstellen von klaren und präzisen Texten für jede `RadioButton`-Option, um die Wahl genau zu beschreiben. Der Text sollte leicht verständlich und voneinander unterscheidbar sein.

2. **Radio-Buttons gruppieren**: Gruppieren Sie verwandte Radio-Buttons zusammen, um ihre Assoziation zu kennzeichnen. Dies hilft den Benutzern zu verstehen, dass innerhalb einer bestimmten Gruppe nur eine Option ausgewählt werden kann. Dies kann effektiv mit der [`RadioButtonGroup`](/docs/components/radiobuttongroup)-Komponente durchgeführt werden.

3. **Standardauswahl bereitstellen**: Falls zutreffend, erwägen Sie, eine Standardauswahl für Radio-Buttons anzubieten, um den Benutzern eine Orientierung zu geben, wenn sie die Optionen zum ersten Mal sehen. Die Standardauswahl sollte mit der häufigsten oder bevorzugten Wahl übereinstimmen.
