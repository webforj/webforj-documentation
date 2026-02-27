---
title: RadioButton
slug: radiobutton
sidebar_position: 95
_i18n_hash: 0445bb7e995db7e0d725964c66690d19
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

Die `RadioButton`-Komponente ist ein Objekt, das ausgewählt oder abgewählt werden kann und seinen Status dem Benutzer anzeigt. Radiobuttons werden häufig verwendet, wenn sich gegenseitig ausschließend Optionen verfügbar sind, sodass der Benutzer eine einzelne Option aus einer Reihe von Auswahlmöglichkeiten auswählen kann.

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

:::tip Gruppierung von `RadioButton`-Komponenten
Verwenden Sie eine [`RadioButtonGroup`](/docs/components/radiobuttongroup), um eine Gruppe von Radiobuttons zu verwalten, wenn Sie möchten, dass die Benutzer eine einzige Option auswählen.
:::

## Verwendung {#usages}

Der `RadioButton` wird am besten in Szenarien verwendet, in denen Benutzer eine einzelne Auswahl aus einer vordefinierten Menge von Optionen treffen müssen. Hier sind einige Beispiele, wann der `RadioButton` verwendet werden sollte:

1. **Umfragen oder Fragebögen**: Radiobuttons werden häufig in Umfragen oder Fragebögen verwendet, bei denen Benutzer eine einzige Antwort aus einer Liste von Optionen auswählen müssen.

2. **Einstellungseinstellungen**: Anwendungen, die Einstellungs- oder Präferenzfenster beinhalten, verwenden häufig Radiobuttons, um den Benutzern zu ermöglichen, eine einzelne Option aus einer Gruppe von sich gegenseitig ausschließenden Auswahlmöglichkeiten zu wählen.

3. **Filtern oder Sortieren**: Ein `RadioButton` kann in Anwendungen verwendet werden, die erfordern, dass Benutzer eine einzelne Filter- oder Sortierungsoption auswählen, wie das Sortieren einer Liste von Elementen nach verschiedenen Kriterien.

## Text und Positionierung {#text-and-positioning}

Radiobuttons können die Methode ```setText(String text)``` nutzen, die in der Nähe des Radiobuttons positioniert wird, gemäß der integrierten `Position`.
Radiobuttons verfügen über eine integrierte Funktion, um Text anzuzeigen, der entweder rechts oder links von der Komponente angezeigt wird. Standardmäßig wird der Text rechts von der Komponente angezeigt. Die Positionierung des horizontalen Texts wird durch die Verwendung der Enum-Klasse `HorizontalAlignment` unterstützt. Nachfolgend sind die beiden Einstellungen aufgeführt: <br/>

<ComponentDemo 
path='/webforj/radiobuttontext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java'
height="120px"
/>

## Aktivierung {#activation}

Radiobuttons können mit zwei Arten von Aktivierungen gesteuert werden: manuelle Aktivierung und automatische Aktivierung. Diese bestimmen, wann sich ein `RadioButton` in seinem Status ändert.

<ComponentDemo 
path='/webforj/radiobuttonactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java'
height="175px"
/>

### Manuelle Aktivierung {#manual-activation}

Wenn ein Radiobutton auf manuelle Aktivierung eingestellt ist, bedeutet dies, dass er nicht automatisch aktiviert wird, wenn er den Fokus erhält.
Die manuelle Aktivierung ermöglicht es dem Benutzer, durch die Radiobutton-Optionen mit der Tastatur oder anderen Eingabemethoden zu navigieren, ohne sofort die ausgewählte Option zu ändern.

Wenn der Radiobutton Teil einer Gruppe ist, wird das Auswählen eines anderen Radiobuttons innerhalb der Gruppe automatisch den zuvor ausgewählten Radiobutton abwählen.
Die manuelle Aktivierung bietet eine genauere Kontrolle über den Auswahlprozess, wobei eine ausdrückliche Handlung des Benutzers erforderlich ist, um die ausgewählte Option zu ändern.

### Automatische Aktivierung {#auto-activation}

Die automatische Aktivierung ist der Standardzustand für einen `RadioButton` und bedeutet, dass der Button aktiviert wird, wenn er aus irgendeinem Grund den Fokus erhält. Dies bedeutet, dass nicht nur das Klicken, sondern auch das Autofokus oder die Tab-Navigation den Button aktivieren.

:::tip Hinweis
Der Standardwert für die Aktivierung ist **`MANUAL`** Aktivierung.
:::

## Schalter {#switches}

Ein `RadioButton` kann auch als Schalter angezeigt werden, der eine alternative visuelle Darstellung für die Auswahl von Optionen bietet. Normalerweise sind Radiobuttons kreisförmig oder abgerundet und zeigen eine einzelne Wahl aus einer Gruppe von Optionen an.

<ComponentDemo 
path='/webforj/radiobuttonswitch?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java'
height="120px"
/>

Ein `RadioButton` kann in einen Schalter umgewandelt werden, der einem Kippschalter oder Schieberegler ähnelt, indem eine von zwei Methoden verwendet wird:

1. **Die Fabrikmethode**: Der Radiobutton kann mit den folgenden Fabrikmethoden erstellt werden:

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

Wenn ein `RadioButton` als Schalter angezeigt wird, hat er typischerweise eine längliche Form mit einem Indikator, der ein- oder ausgeschaltet werden kann. Diese visuelle Darstellung bietet den Benutzern eine intuitivere und vertrautere Benutzeroberfläche, ähnlich wie physische Schalter, die häufig in elektronischen Geräten zu finden sind.

Das Einstellen eines `RadioButton` zur Anzeige als Schalter kann die Benutzererfahrung verbessern, indem es eine klare und unkomplizierte Möglichkeit bietet, Optionen auszuwählen. Es kann die visuelle Anziehungskraft und Benutzerfreundlichkeit von Formularen, Einstellungsfenstern oder anderen Benutzerschnittstellenelementen, die mehrere Auswahlmöglichkeiten erfordern, erhöhen.

:::info
Das Verhalten des `RadioButton` bleibt dasselbe, wenn es als Schalter gerendert wird, was bedeutet, dass innerhalb einer Gruppe nur eine Option ausgewählt werden kann. Das schalterähnliche Aussehen ist eine visuelle Transformation, die die Funktionalität eines `RadioButton` beibehält.
:::

<br/>

## Styling {#styling}

### Ausdehnungen {#expanses}
Es gibt fünf Checkbox-Ausdehnungen, die unterstützt werden und eine schnelle Stilgestaltung ohne Verwendung von CSS ermöglichen.
Ausdehnungen werden durch die Verwendung der Enum-Klasse `Expanse` unterstützt. Nachfolgend sind die unterstützten Ausdehnungen für die Checkbox-Komponente aufgeführt: <br/>

<TableBuilder name="RadioButton" />

## Beste Praktiken {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der RadioButton-Komponente zu gewährleisten, sollten Sie die folgenden besten Praktiken berücksichtigen:

1. **Optionen deutlich kennzeichnen**: Geben Sie klaren und präzisen Text für jede `RadioButton`-Option an, um die Wahl genau zu beschreiben. Der Text sollte leicht verständlich und voneinander zu unterscheiden sein.

2. **Radiobuttons gruppieren**: Gruppieren Sie verwandte Radiobuttons, um ihre Zugehörigkeit anzuzeigen. Dies hilft den Benutzern, zu verstehen, dass innerhalb einer bestimmten Gruppe nur eine Option ausgewählt werden kann. Dies kann effektiv mit der [`RadioButtonGroup`](/docs/components/radiobuttongroup) Komponente erreicht werden.

3. **Voreingestellte Auswahl bieten**: Wenn anwendbar, sollten Sie in Betracht ziehen, eine voreingestellte Auswahl für Radiobuttons bereitzustellen, um die Benutzer zu leiten, wenn sie zum ersten Mal auf die Optionen stoßen. Die voreingestellte Auswahl sollte mit der häufigsten oder bevorzugten Wahl übereinstimmen.
