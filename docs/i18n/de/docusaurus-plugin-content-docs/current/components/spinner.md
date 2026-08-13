---
title: Spinner
sidebar_position: 110
description: >-
  Indicate background activity with the Spinner component, configuring theme,
  expanse, rotation speed, and pause or resume.
_i18n_hash: bd35c3da6c5fc265d0bb249bbde86215
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

Die `Spinner`-Komponente bietet einen visuellen Indikator, der anzeigt, dass im Hintergrund eine Verarbeitung oder das Laden im Gange ist. Sie wird oft verwendet, um anzuzeigen, dass das System Daten abruft oder wenn ein Prozess Zeit benötigt, um abgeschlossen zu werden. Der `Spinner` bietet dem Benutzer Feedback und signalisiert, dass das System aktiv arbeitet.

<!-- INTRO_END -->

## Grundlagen {#basics}

Um einen `Spinner` zu erstellen, können Sie das Design und die Größe angeben. Die grundlegende Syntax besteht darin, eine `Spinner`-Instanz zu erstellen und ihr Aussehen sowie Verhalten durch Methoden wie `setTheme()` und `setExpanse()` zu definieren.

<ComponentDemo
path='/webforj/spinnerdemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java']}
height='225px'
/>

## Geschwindigkeit steuern und pausieren {#managing-speed-and-pausing}

Es ist möglich, die Geschwindigkeit in Millisekunden für den `Spinner` festzulegen und die Animation einfach zu pausieren oder fortzusetzen.

Anwendungsfälle für Geschwindigkeitsanpassungen umfassen die Differenzierung zwischen Ladeprozessen. Beispielsweise sind schnellere `Spinner` für kleinere Aufgaben geeignet, während langsamere `Spinner` besser für größere Aufgaben sind. Pausieren ist nützlich, wenn eine Benutzeraktion oder -bestätigung erforderlich ist, bevor der Prozess fortgesetzt wird.

### Geschwindigkeit anpassen {#adjusting-speed}

Sie können steuern, wie schnell sich der `Spinner` dreht, indem Sie dessen Geschwindigkeit in Millisekunden mit der Methode `setSpeed()` anpassen. Ein niedrigerer Wert lässt den `Spinner` schneller rotieren, während höhere Werte ihn langsamer machen.

```java
spinner.setSpeed(500); // Rotiert schneller
```

:::info Standardgeschwindigkeit
Standardmäßig benötigt der `Spinner` 1000 Millisekunden, um eine vollständige Umdrehung abzuschließen.
:::

### Pausieren und Fortsetzen {#pausing-and-resuming}

Das Pausieren des `Spinner` ist nützlich, wenn ein Programm vorübergehend gestoppt oder auf Benutzereingaben wartet. Es lässt die Benutzer wissen, dass das Programm pausiert ist, anstatt aktiv zu laufen, was die Klarheit während mehrstufiger Prozesse verbessert.

Um den Spinner zu pausieren und fortzusetzen, verwenden Sie die Methode `setPaused()`. Dies ist besonders hilfreich, wenn Sie die sich drehende Animation vorübergehend stoppen müssen.

```java
spinner.setPaused(true);  // Pause den Spinner
spinner.setPaused(false); // Setze den Spinner fort
```

Dieses Beispiel zeigt, wie man die Geschwindigkeit einstellt und wie man den `Spinner` pausiert/fortsetzt:

<ComponentDemo
path='/webforj/spinnerspeeddemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java']}
height='150px'
/>

## Drehrichtung {#spin-direction}

Die Drehrichtung des `Spinner` kann so gesteuert werden, dass er **im Uhrzeigersinn** oder **gegen den Uhrzeigersinn** rotiert. Sie können dieses Verhalten mit der Methode `setClockwise()` angeben.

```java
spinner.setClockwise(false);  // Rotiert gegen den Uhrzeigersinn
spinner.setClockwise(true);   // Rotiert im Uhrzeigersinn
```

Diese Option zeigt visuell einen besonderen Zustand an oder dient als einzigartige Designentscheidung. Das Ändern der Drehrichtung kann helfen, zwischen verschiedenen Prozessen zu unterscheiden, wie zum Beispiel Fortschritt gegen Rückgängigmachen, oder in bestimmten Kontexten einen klaren visuellen Hinweis zu geben.

<ComponentDemo
path='/webforj/spinnerdirectiondemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java']}
height='150px'
/>

## Stilgestaltung {#styling}

### Themen {#themes}

Die `Spinner`-Komponente kommt mit mehreren integrierten Themen, die es Ihnen ermöglichen, schnell Stile anzuwenden, ohne benutzerdefiniertes CSS schreiben zu müssen. Diese Themen ändern das visuelle Erscheinungsbild des Spinners und machen ihn für unterschiedliche Anwendungsfälle und Kontexte geeignet. Die Verwendung dieser vordefinierten Themen gewährleistet Konsistenz in der Gestaltung über Ihre App hinweg.

Während Spinner in verschiedenen Situationen eingesetzt werden, sind hier einige Beispielanwendungsfälle für die verschiedenen Themen:

- **Primär**: Ideal, um einen Ladezustand zu betonen, der ein zentraler Bestandteil des Benutzerflusses ist, wie beispielsweise beim Einreichen eines Formulars oder bei der Verarbeitung einer wichtigen Aktion.

- **Erfolgreich**: Nützlich, um erfolgreiche Hintergrundprozesse darzustellen, wie wenn ein Benutzer ein Formular einreicht und die App die letzten Schritte des Prozesses durchführt.

- **Gefahr**: Verwenden Sie dies für riskante oder kritische Operationen, wie das Löschen wichtiger Daten oder das Vornehmen von unumkehrbaren Änderungen, bei denen ein visueller Hinweis auf Dringlichkeit oder Vorsicht erforderlich ist.

- **Warnung**: Verwenden Sie dies, um einen vorsichtigen oder weniger dringenden Prozess anzuzeigen, wie wenn der Benutzer auf eine Datenvalidierung wartet, aber keine sofortige Aktion erforderlich ist.

- **Grau**: Funktioniert gut für subtile Hintergrundprozesse, wie niedrigpriorisierte oder passive Ladeaufgaben, wie bei der Abfrage zusätzlicher Daten, die die Benutzererfahrung nicht direkt beeinflussen.

- **Info**: Geeignet für Ladeszenarien, in denen Sie dem Benutzer zusätzliche Informationen oder Klarstellungen bereitstellen, wie das Anzeigen eines Spinners zusammen mit einer Nachricht, die den laufenden Prozess erklärt.

Sie können diese Themen programmgesteuert auf den Spinner anwenden und visuelle Hinweise geben, die mit dem Kontext und der Wichtigkeit der Operation übereinstimmen.

Sie können dieses Verhalten mit der Methode `setTheme()` angeben.

<ComponentDemo
path='/webforj/spinnerthemedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java']}
height='100px'
/>

### Größen {#expanses}

Sie können die Größe des Spinners, die als **Größe** bekannt ist, anpassen, um den visuellen Raum zu nutzen, den Sie benötigen. Der Spinner unterstützt verschiedene Größen einschließlich `Expanse.SMALL`, `Expanse.MEDIUM` und `Expanse.LARGE`.

<ComponentDemo
path='/webforj/spinnerexpansedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java']}
height='100px'
/>

<TableBuilder name="Spinner" />
