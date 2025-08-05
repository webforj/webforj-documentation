---
title: RadioButton
slug: radiobutton
sidebar_position: 95
_i18n_hash: bf7e30274560f1e29fc307b5894c533a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

Die Klasse `RadioButton` erstellt ein Objekt, das ausgewählt oder abgewählt werden kann und seinen Zustand dem Benutzer anzeigt. Nach Konvention kann in einer Gruppe immer nur ein Radio-Button gleichzeitig ausgewählt sein. Radio-Buttons werden häufig verwendet, wenn gegenseitig ausschließende Optionen zur Verfügung stehen, die es dem Benutzer ermöglichen, eine einzelne Option aus einer Menge von Auswahlmöglichkeiten auszuwählen.

<ComponentDemo 
path='/webforj/radiobuttongroup?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonGroupView.java'
height="200px"
/>

## Usos {#usages}

Der `RadioButton` eignet sich am besten für Szenarien, in denen Benutzer eine einzige Auswahl aus einem vordefinierten Satz von Optionen treffen müssen. Hier sind einige Beispiele, wann der `RadioButton` verwendet werden sollte:

1. **Umfragen oder Fragebögen**: Radio-Buttons werden häufig in Umfragen oder Fragebögen verwendet, in denen Benutzer eine einzige Antwort aus einer Liste von Optionen auswählen müssen.

2. **Einstellungen**: Anwendungen, die Präferenz- oder Einstellungsfenster enthalten, verwenden häufig Radio-Buttons, um es Benutzern zu ermöglichen, eine einzelne Option aus einer Menge von gegenseitig ausschließenden Auswahlmöglichkeiten zu wählen.

3. **Filtern oder Sortieren**: Ein `RadioButton` kann in Anwendungen verwendet werden, die es Benutzern erfordern, eine einzelne Filter- oder Sortieroption auszuwählen, z. B. das Sortieren einer Liste von Elementen nach verschiedenen Kriterien.

## Text und Positionierung {#text-and-positioning}

Radio-Buttons können die Methode ```setText(String text)``` nutzen, die in der Nähe des Radio-Buttons je nach integrierter `Position` positioniert wird. 
Radio-Buttons verfügen über eine integrierte Funktionalität, um den Text entweder rechts oder links des Elements anzuzeigen. Standardmäßig wird der Text rechts des Elements angezeigt. Die Positionierung des horizontalen Texts wird durch die Verwendung der Enum-Klasse `HorizontalAlignment` unterstützt. Nachfolgend sind die beiden Einstellungen dargestellt: <br/>

<ComponentDemo 
path='/webforj/radiobuttontext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java'
height="120px"
/>

## Aktivierung {#activation}

Radio-Buttons können mit zwei Arten von Aktivierung gesteuert werden: manuelle Aktivierung und automatische Aktivierung. Diese bestimmen, wann ein `RadioButton` seinen Zustand ändern wird.

<ComponentDemo 
path='/webforj/radiobuttonactivation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java'
height="175px"
/>

### Manuelle Aktivierung {#manual-activation}

Wenn ein Radio-Button auf manuelle Aktivierung eingestellt ist, bedeutet dies, dass er nicht automatisch überprüft wird, wenn er den Fokus erhält.
Die manuelle Aktivierung ermöglicht es dem Benutzer, durch die Optionen der Radio-Buttons mit der Tastatur oder anderen Eingabemethoden zu navigieren, ohne sofort die ausgewählte Option zu ändern.

Wenn der Radio-Button Teil einer Gruppe ist, wird das Auswählen eines anderen Radio-Buttons innerhalb der Gruppe automatisch den zuvor ausgewählten Radio-Button abwählen. 
Die manuelle Aktivierung bietet eine genauere Kontrolle über den Auswahlprozess und erfordert eine explizite Aktion des Benutzers, um die ausgewählte Option zu ändern.

### Automatische Aktivierung {#auto-activation}

Die automatische Aktivierung ist der Standardzustand für einen `RadioButton` und bedeutet, dass der Button immer überprüft wird, wenn er aus irgendeinem Grund den Fokus erhält. Das bedeutet, dass nicht nur durch Klicken, sondern auch durch Autofokus oder Tab-Navigation der Button überprüft wird.

:::tip Hinweis
Der Standardwert für die Aktivierung ist **`MANUAL`** Aktivierung.
:::

## Schalter {#switches}

Ein `RadioButton` kann auch so eingestellt werden, dass er als Schalter angezeigt wird, was eine alternative visuelle Darstellung für die Auswahl von Optionen bietet. Normalerweise sind Radio-Buttons kreisförmig oder abgerundet und zeigen eine einzelne Wahl aus einer Gruppe von Optionen an.

<ComponentDemo 
path='/webforj/radiobuttonswitch?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java'
height="120px"
/>

Ein `RadioButton` kann in einen Schalter umgewandelt werden, der einem Kippschalter oder Schieberegler ähnelt, indem eine der beiden Methoden verwendet wird:

1. **Die Fabrikmethode**: Der RadioButton kann mithilfe der folgenden Fabrikmethoden erstellt werden:

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

Diese Methoden spiegeln einen `RadioButton` Konstruktor wider und erstellen das Element mit der Schalter-Eigenschaft bereits aktiviert.

2. **Setter**: Es ist auch möglich, einen bereits bestehenden `RadioButton` in einen Schalter zu ändern, indem der entsprechende Setter verwendet wird:

```java
myRadioButton.setSwitch(true);
```

Wenn ein `RadioButton` als Schalter angezeigt wird, erscheint er normalerweise als oblonger Form mit einem Indikator, der ein- oder ausgeschaltet werden kann. Diese visuelle Darstellung bietet den Benutzern eine intuitivere und vertrautere Schnittstelle, die physischen Schaltern ähnelt, die häufig in elektronischen Geräten zu finden sind.

Die Einstellung eines `RadioButton`, um als Schalter angezeigt zu werden, kann das Benutzererlebnis verbessern, indem eine klare und unkomplizierte Möglichkeit zur Auswahl von Optionen bereitgestellt wird. Sie kann die visuelle Attraktivität und Benutzerfreundlichkeit von Formularen, Einstellungsfeldern oder anderen Schnittstellenelementen verbessern, die mehrere Auswahlmöglichkeiten erfordern.

:::info
Das Verhalten des `RadioButton` bleibt gleich, wenn er als Schalter dargestellt wird, was bedeutet, dass immer nur eine Option pro Gruppe ausgewählt werden kann. Das schalterähnliche Aussehen ist eine visuelle Transformation, die die Funktionalität eines `RadioButton` beibehält.
:::

<br/>

## Styling {#styling}

### Expanses {#expanses}
Es gibt fünf Checkbox-Expanses, die unterstützt werden und eine schnelle Gestaltung ohne Verwendung von CSS ermöglichen. 
Die Expanses werden durch die Verwendung der Enum-Klasse `Expanse` unterstützt. Nachfolgend sind die unterstützten Expanses für das Checkbox-Komponenten aufgeführt: <br/>

<TableBuilder name="RadioButton" />

## Beste Vorgehensweisen {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der RadioButton-Komponente zu gewährleisten, berücksichtigen Sie die folgenden bewährten Verfahren:

1. **Optionen klar kennzeichnen**: Stellen Sie für jede `RadioButton`-Option klaren und prägnanten Text bereit, um die Wahl genau zu beschreiben. Der Text sollte leicht verständlich und voneinander zu unterscheiden sein.

2. **Radio-Buttons gruppieren**: Gruppieren Sie verwandte Radio-Buttons, um ihre Zugehörigkeit anzuzeigen. Dies hilft den Benutzern zu verstehen, dass innerhalb einer bestimmten Gruppe immer nur eine Option ausgewählt werden kann. Dies kann effektiv mit der [`RadioButtonGroup`](/docs/components/radiobuttongroup) Komponente erfolgen.

3. **Standardauswahl bereitstellen**: Falls zutreffend, ziehen Sie in Betracht, eine Standardauswahl für Radio-Buttons bereitzustellen, um Benutzer zu leiten, wenn sie zum ersten Mal auf die Optionen stoßen. Die Standardauswahl sollte mit der häufigsten oder bevorzugten Wahl übereinstimmen.
