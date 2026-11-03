---
title: Spinner
sidebar_position: 110
description: >-
  Indicate background activity with the Spinner component, configuring theme,
  expanse, rotation speed, and pause or resume.
_i18n_hash: 6a667297edb572a6990b58bee72c8b1b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

Die `Spinner`-Komponente bietet einen visuellen Indikator, der anzeigt, dass im Hintergrund ein Vorgang oder Laden stattfindet. Sie wird häufig verwendet, um zu zeigen, dass das System Daten abruft oder wenn ein Prozess Zeit benötigt, um abzuschließen. Der `Spinner` bietet Пользовательfeedback und signalisiert, dass das System aktiv arbeitet.

<!-- INTRO_END -->

Erstellen Sie eine `Spinner`-Instanz und definieren Sie dann ihr Aussehen und Verhalten mithilfe von Methoden wie `setTheme()` und `setExpanse()`.

<ComponentDemo
path='/webforj/spinnerdemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java']}
height='225px'
/>

## Geschwindigkeit und Pause verwalten {#managing-speed-and-pausing}

Es ist möglich, die Geschwindigkeit in Millisekunden für den `Spinner` festzulegen und die Animation problemlos zu pausieren oder fortzusetzen.

Einsatzmöglichkeiten für Geschwindigkeitsanpassungen umfassen die Unterscheidung zwischen Ladeprozessen. Schnellere `Spinner` eignen sich für kleinere Aufgaben, während langsamere `Spinner` besser für größere Aufgaben geeignet sind. Pausieren ist nützlich, wenn eine Benutzeraktion oder Bestätigung erforderlich ist, bevor der Prozess fortgesetzt wird.

### Geschwindigkeit anpassen {#adjusting-speed}

Sie können steuern, wie schnell sich der `Spinner` dreht, indem Sie seine Geschwindigkeit in Millisekunden mit der Methode `setSpeed()` anpassen. Ein niedrigerer Wert lässt den `Spinner` schneller rotieren, während höhere Werte ihn langsamer machen.

```java
spinner.setSpeed(500); // Dreht schneller
```

:::info Standardgeschwindigkeit
Standardmäßig benötigt der `Spinner` 1000 Millisekunden, um eine volle Umdrehung abzuschließen.
:::

### Pausieren und Fortsetzen {#pausing-and-resuming}

Das Pausieren des `Spinners` ist nützlich, wenn ein Programm vorübergehend angehalten wird oder auf die Benutzereingabe wartet. Es zeigt den Benutzern, dass das Programm pausiert ist und nicht aktiv läuft, was die Klarheit bei mehrstufigen Prozessen verbessert.

Um den Spinner zu pausieren und fortzusetzen, verwenden Sie die Methode `setPaused()`. Dies ist besonders hilfreich, wenn Sie die Drehanimation vorübergehend anhalten müssen.

```java
spinner.setPaused(true);  // Spinner anhalten
spinner.setPaused(false); // Spinner fortsetzen
```

Dieses Beispiel zeigt, wie Sie die Geschwindigkeit einstellen und wie Sie den `Spinner` pausieren/fortsetzen:

<ComponentDemo
path='/webforj/spinnerspeeddemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java']}
height='150px'
/>

## Drehrichtung {#spin-direction}

Die Richtung des `Spinners` kann so gesteuert werden, dass er **im Uhrzeigersinn** oder **gegen den Uhrzeigersinn** dreht. Sie können dieses Verhalten mit der Methode `setClockwise()` festlegen.

```java
spinner.setClockwise(false);  // Dreht gegen den Uhrzeigersinn
spinner.setClockwise(true);   // Dreht im Uhrzeigersinn
```

Diese Option zeigt visuell einen speziellen Zustand an oder dient als einzigartige Designentscheidung. Das Ändern der Drehrichtung kann helfen, zwischen verschiedenen Prozessarten zu unterscheiden, wie Fortschritt vs. Rückwärtsbewegung, oder einen deutlichen visuellen Hinweis in spezifischen Kontexten zu geben.

<ComponentDemo
path='/webforj/spinnerdirectiondemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java']}
height='150px'
/>

## Styling {#styling}

### Themen {#themes}

Die `Spinner`-Komponente wird mit mehreren integrierten Themen geliefert, mit denen Sie schnell Stile anwenden können, ohne benutzerdefiniertes CSS erstellen zu müssen. Diese Themen ändern das visuelle Erscheinungsbild des Spinners und machen ihn für verschiedene Anwendungsfälle und Kontexte geeignet. Die Verwendung dieser vordefinierten Themen stellt sicher, dass das Styling in Ihrer App konsistent bleibt.

Obwohl Spinner für verschiedene Situationen dienen, sind hier einige Anwendungsbeispiele für die unterschiedlichen Themen:

- **Primär**: Ideal, um einen Ladezustand zu betonen, der ein zentraler Bestandteil des Benutzerflusses ist, wie beim Absenden eines Formulars oder bei der Verarbeitung einer wichtigen Aktion.

- **Erfolg**: Nützlich zur Darstellung erfolgreicher Hintergrundprozesse, wie wenn ein Benutzer ein Formular einreicht und die App die letzten Schritte des Prozesses durchführt.

- **Gefahr**: Verwenden Sie dies für riskante oder hochriskante Operationen, wie das Löschen wichtiger Daten oder das Vornehmen irreversibler Änderungen, wo ein visueller Hinweis auf Dringlichkeit oder Vorsicht erforderlich ist.

- **Warnung**: Verwenden Sie dies, um einen vorsichtigen oder weniger dringenden Prozess anzuzeigen, z. B. wenn der Benutzer auf die Datenvalidierung wartet, aber keine sofortige Aktion erforderlich ist.

- **Grau**: Eignet sich gut für subtile Hintergrundprozesse, wie niedrigpriorisierte oder passive Ladeaufgaben, z. B. beim Abrufen von ergänzenden Daten, die die Benutzererfahrung nicht direkt beeinflussen.

- **Info**: Geeignet für Ladeszenarien, bei denen Sie dem Benutzer zusätzliche Informationen oder Klarstellungen bieten, z. B. beim Anzeigen eines Spinners zusammen mit einer Nachricht, die den laufenden Prozess erklärt.

Sie können diese Themen programmgesteuert auf den Spinner anwenden, um visuelle Hinweise zu geben, die mit dem Kontext und der Wichtigkeit des Vorgangs übereinstimmen.

Sie können dieses Verhalten mit der Methode `setTheme()` festlegen.

<ComponentDemo
path='/webforj/spinnerthemedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java']}
height='100px'
/>

### Größen {#expanses}

Sie können die Größe des Spinners, bekannt als **Expanse**, mithilfe von `setExpanse()` mit dem Enum `SpinnerExpanse` anpassen. Die neun verfügbaren Größen sind `XXXSMALL`, `XXSMALL`, `XSMALL`, `SMALL`, `MEDIUM`, `LARGE`, `XLARGE`, `XXLARGE` und `XXXLARGE`. Der Standardwert ist `SpinnerExpanse.NONE`.

```java
spinner.setExpanse(SpinnerExpanse.LARGE);
```

<ComponentDemo
path='/webforj/spinnerexpansedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java']}
height='100px'
/>

<TableBuilder name="Spinner" />
