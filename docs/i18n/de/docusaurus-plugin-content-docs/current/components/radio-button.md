---
title: RadioButton
slug: radiobutton
sidebar_position: 95
description: >-
  Add a single-choice RadioButton with text positioning, activation modes, and
  grouping for mutually exclusive selections.
_i18n_hash: b261deb2944ba67d2e9ae8cbbb8b26eb
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-radio" />
<DocChip chip='since' label='23.01' />
<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButton" top='true'/>

Die `RadioButton`-Komponente stellt eine einzelne Option dar, die ausgewählt oder abgewählt werden kann. Radio-Buttons werden typischerweise gruppiert, sodass die Auswahl eines Buttons automatisch die Auswahl der anderen aufhebt, wodurch Benutzer eine einzige Wahl aus einer Menge von sich gegenseitig ausschließenden Optionen treffen können.

<!-- INTRO_END -->

## Verwendungsmöglichkeiten {#usages}

Der `RadioButton` eignet sich am besten für Szenarien, in denen Benutzer eine einzelne Auswahl aus einem vordefinierten Set von Optionen treffen müssen. Hier sind einige Beispiele, wann der `RadioButton` verwendet werden sollte:

1. **Umfragen oder Fragebögen**: Radio-Buttons werden häufig in Umfragen oder Fragebögen eingesetzt, bei denen Benutzer eine einzelne Antwort aus einer Liste von Optionen auswählen müssen.

2. **Einstellungsmöglichkeiten**: Anwendungen, die Einstellungs- oder Präferenzpaneele umfassen, verwenden oft Radio-Buttons, um Benutzern zu erlauben, eine einzelne Option aus einer Menge von sich gegenseitig ausschließenden Auswahlmöglichkeiten zu wählen.

3. **Filtern oder Sortieren**: Ein `RadioButton` kann in Anwendungen verwendet werden, die von den Benutzern verlangen, eine einzelne Filter- oder Sortieroption auszuwählen, wie etwa das Sortieren einer Liste von Elementen nach verschiedenen Kriterien.

:::tip Gruppierung von `RadioButton`-Komponenten
Verwenden Sie eine [`RadioButtonGroup`](/docs/components/radiobuttongroup), um eine Gruppe von Radio-Buttons zu verwalten, wenn Benutzer eine einzige Option auswählen sollen.
:::

## Text und Positionierung {#text-and-positioning}

Verwenden Sie `setText(String text)`, um das Label eines Radio-Buttons festzulegen. Standardmäßig erscheint der Text rechts von der Komponente. Um die Position zu ändern, verwenden Sie `setTextPosition(Position.LEFT)` oder `setTextPosition(Position.RIGHT)` mit dem `HasTextPosition.Position`-Enum.

<ComponentDemo
path='/webforj/radiobuttontext'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonTextView.java']}
height='120px'
/>


## Aktivierung {#activation}

Radio-Buttons können über zwei Arten der Aktivierung gesteuert werden: manuelle Aktivierung und automatische Aktivierung. Diese bestimmen, wann ein `RadioButton` seinen Zustand ändert. Standardmäßig ist der Zustand `Activation.MANUAL`, aus dem `RadioButton.Activation`-Enum.

<ComponentDemo
path='/webforj/radiobuttonactivation'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonActivationView.java']}
height='175px'
/>

### Manuelle Aktivierung {#manual-activation}

Wenn ein Radio-Button auf manuelle Aktivierung eingestellt ist, bedeutet das, dass er nicht automatisch aktiviert wird, wenn er den Fokus erhält. Manuelle Aktivierung erlaubt es dem Benutzer, durch die Optionen des Radio-Buttons mit der Tastatur oder anderen Eingabemethoden zu navigieren, ohne sofort die ausgewählte Option zu ändern.

Wenn der Radio-Button Teil einer Gruppe ist, wird das Auswählen eines anderen Radio-Buttons innerhalb der Gruppe automatisch den zuvor ausgewählten Radio-Button abwählen. Manuelle Aktivierung bietet eine genauere Kontrolle über den Auswahlprozess, da eine explizite Aktion des Benutzers erforderlich ist, um die ausgewählte Option zu ändern.


### Automatische Aktivierung {#auto-activation}

Aktivieren Sie die automatische Aktivierung mit `setActivation(Activation.AUTO)`. In diesem Modus wird der Radio-Button jedes Mal aktiviert, wenn er den Fokus erhält, einschließlich durch Klicken, automatische Fokussierung oder Tab-Navigation.


## Schalter {#switches}

Ein `RadioButton` kann auch so eingestellt werden, dass er als Schalter angezeigt wird, der eine alternative visuelle Darstellung zur Auswahl von Optionen bietet. Normalerweise sind Radio-Buttons kreisförmig oder abgerundet und zeigen eine einzelne Wahl aus einer Gruppe von Optionen an.

<ComponentDemo
path='/webforj/radiobuttonswitch'
files={['src/main/java/com/webforj/samples/views/radiobutton/RadioButtonSwitchView.java']}
height='120px'
/>

Ein `RadioButton` kann in einen Schalter verwandelt werden, der einem Kippschalter oder Schieberegler ähnelt, indem einer von zwei Methoden verwendet wird:

1. **Die Fabrikmethode**: Der RadioButton kann mit den folgenden Fabrikmethoden erstellt werden:

```java
Switch(String text, boolean checked);
Switch(String text);
Switch();
```

Diese Methoden spiegeln den `RadioButton`-Konstruktor wider und erstellen die Komponente mit der Schalter-Eigenschaft, die bereits aktiviert ist.

2. **Setter**: Es ist auch möglich, einen bereits vorhandenen `RadioButton` in einen Schalter zu ändern, indem der entsprechende Setter verwendet wird:

```java
myRadioButton.setSwitch(true);
```


Wenn ein `RadioButton` als Schalter angezeigt wird, erscheint er typischerweise als oblonges Format mit einem Indikator, der ein- oder ausgeschaltet werden kann. Diese visuelle Darstellung gibt Benutzern eine intuitivere und vertrautere Benutzeroberfläche, ähnlich wie physische Schalter, die häufig in elektronischen Geräten zu finden sind.

Einen `RadioButton` als Schalter anzuzeigen, kann die Benutzererfahrung verbessern, indem eine klare und unkomplizierte Möglichkeit zur Auswahl von Optionen bereitgestellt wird. Es kann die visuelle Attraktivität und Benutzerfreundlichkeit von Formularen, Einstellungsfenstern oder jedem anderen Interface-Element verbessern, das mehrere Auswahlmöglichkeiten erfordert.

:::info
Das Verhalten des `RadioButton` bleibt dasselbe, wenn er als Schalter dargestellt wird, was bedeutet, dass innerhalb einer Gruppe immer nur eine Option ausgewählt werden kann. Das schalterartige Aussehen ist eine visuelle Transformation, die die Funktionalität eines `RadioButton` beibehält.
:::

<br/>

## Styling {#styling}

### Größen {#expanses}

Verwenden Sie `setExpanse()` mit dem `Expanse`-Enum, um die Größe eines `RadioButton` anzupassen, ohne CSS schreiben zu müssen. Die verfügbaren Werte sind `XSMALL`, `SMALL`, `MEDIUM`, `LARGE`, `XLARGE` und `NONE`.

<TableBuilder name="RadioButton" />

## Best Practices {#best-practices}

Um eine optimale Benutzererfahrung bei der Verwendung der RadioButton-Komponente sicherzustellen, sollten folgende Best Practices berücksichtigt werden:

1. **Optionen klar kennzeichnen**: Stellen Sie für jede `RadioButton`-Option klaren und prägnanten Text zur Verfügung, um die Auswahl genau zu beschreiben. Der Text sollte leicht verständlich und voneinander zu unterscheiden sein.

2. **Radio-Buttons gruppieren**: Gruppieren Sie verwandte Radio-Buttons, um deren Zusammenhang anzuzeigen. Dies hilft den Benutzern zu verstehen, dass innerhalb einer bestimmten Gruppe nur eine Option ausgewählt werden kann. Dies kann effektiv mittels der [`RadioButtonGroup`](/docs/components/radiobuttongroup)-Komponente erfolgen.

3. **Standardauswahl bereitstellen**: Falls zutreffend, ziehen Sie in Betracht, eine Standardauswahl für Radio-Buttons bereitzustellen, um Benutzer zu leiten, wenn sie das erste Mal mit den Optionen konfrontiert werden. Die Standardauswahl sollte mit der häufigsten oder bevorzugten Wahl übereinstimmen.
