---
title: Spinner
sidebar_position: 110
_i18n_hash: d93d5704fff2acc975910f1a10e34d0b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

Die `Spinner`-Komponente bietet ein visuelles Indiz, das anzeigt, dass ein Vorgang oder eine Hintergrundladung gerade stattfindet. Sie wird häufig verwendet, um zu zeigen, dass das System Daten abruft oder wenn ein Prozess Zeit zum Abschluss benötigt. Der `Spinner` bietet Rückmeldung an den Benutzer und signalisiert, dass das System aktiv arbeitet.

<!-- INTRO_END -->

## Grundlagen {#basics}

Um einen `Spinner` zu erstellen, können Sie das Thema und die Größe angeben. Die grundlegende Syntax besteht darin, eine `Spinner`-Instanz zu erstellen und ihr Aussehen sowie Verhalten durch Methoden wie `setTheme()` und `setExpanse()` zu definieren.

<ComponentDemo
path='/webforj/spinnerdemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java']}
height='225px'
/>

## Geschwindigkeit verwalten und Pausieren {#managing-speed-and-pausing}

Es ist möglich, die Geschwindigkeit in Millisekunden für den `Spinner` einzustellen und die Animation einfach zu pausieren/wiederaufzunehmen.

Anwendungsfälle für Geschwindigkeitsanpassungen umfassen die Differenzierung zwischen Ladeprozessen. Beispielsweise sind schnellere `Spinner` für kleinere Aufgaben geeignet, während langsamere `Spinner` besser für größere Aufgaben sind. Pausen sind nützlich, wenn eine Benutzeraktion oder Bestätigung erforderlich ist, bevor der Prozess fortgesetzt wird.

### Geschwindigkeit anpassen {#adjusting-speed}

Sie können die Rotationsgeschwindigkeit des `Spinner` steuern, indem Sie seine Geschwindigkeit in Millisekunden mit der Methode `setSpeed()` anpassen. Ein niedrigerer Wert lässt den `Spinner` schneller rotieren, während höhere Werte ihn verlangsamen.

```java
spinner.setSpeed(500); // Rotiert schneller
```

:::info Standardgeschwindigkeit
Standardmäßig benötigt der `Spinner` 1000 Millisekunden für eine volle Umdrehung.
:::

### Pausieren und Fortsetzen {#pausing-and-resuming}

Das Pausieren des `Spinner` ist nützlich, wenn ein Programm vorübergehend angehalten wird oder auf Benutzereingaben wartet. Es lässt die Benutzer wissen, dass das Programm angehalten ist und nicht aktiv läuft, was die Klarheit während mehrstufiger Prozesse verbessert.

Um den Spinner zu pausieren und wieder aufzunehmen, verwenden Sie die Methode `setPaused()`. Dies ist besonders hilfreich, wenn Sie die drehende Animation vorübergehend stoppen müssen.

```java
spinner.setPaused(true);  // Pausiere den Spinner
spinner.setPaused(false); // Setze den Spinner fort
```

Dieses Beispiel zeigt, wie man die Geschwindigkeit einstellt und wie man den `Spinner` pausiert/fortsetzt:

<ComponentDemo
path='/webforj/spinnerspeeddemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java']}
height='150px'
/>

## Rotationsrichtung {#spin-direction}

Die Richtung des `Spinner` kann gesteuert werden, um **im Uhrzeigersinn** oder **gegen den Uhrzeigersinn** zu rotieren. Sie können dieses Verhalten mit der Methode `setClockwise()` festlegen.

```java
spinner.setClockwise(false);  // Rotiert gegen den Uhrzeigersinn
spinner.setClockwise(true);   // Rotiert im Uhrzeigersinn
```

Diese Option zeigt visuell einen besonderen Zustand an oder dient als einzigartige Designwahl. Das Ändern der Rotationsrichtung kann helfen, zwischen verschiedenen Prozessen zu unterscheiden, wie beispielsweise Fortschritt vs. Umkehrung oder einen deutlichen visuellen Hinweis in bestimmten Kontexten zu geben.

<ComponentDemo
path='/webforj/spinnerdirectiondemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java']}
height='150px'
/>

## Styling {#styling}

### Themen {#themes}

Die `Spinner`-Komponente verfügt über mehrere integrierte Themen, die es Ihnen ermöglichen, Stile schnell anzuwenden, ohne benutzerdefiniertes CSS zu benötigen. Diese Themen verändern das visuelle Erscheinungsbild des Spinners und machen ihn für verschiedene Anwendungsfälle und Kontexte geeignet. Durch die Verwendung dieser vordefinierten Themen wird Konsistenz im Styling Ihrer App sichergestellt.

Während Spinners verschiedene Situationen bedienen, sind hier einige Anwendungsbeispiele für die unterschiedlichen Themen:

- **Primär**: Ideal, um einen Ladezustand zu betonen, der ein Schlüsselteil des Benutzerflusses ist, z. B. beim Einreichen eines Formulars oder der Verarbeitung einer wichtigen Aktion.
  
- **Erfolg**: Nützlich, um erfolgreiche Hintergrundprozesse darzustellen, z. B. wenn ein Benutzer ein Formular einreicht und die App die letzten Schritte des Prozesses durchführt.
  
- **Gefahr**: Verwenden Sie dies für riskante oder hochriskante Operationen, wie das Löschen wichtiger Daten oder das Vornehmen irreversibler Änderungen, bei denen ein visuelles Indiz für Dringlichkeit oder Vorsicht erforderlich ist.
  
- **Warnung**: Verwenden Sie dies, um einen vorsichtigen oder weniger dringenden Prozess anzuzeigen, z. B. wenn der Benutzer auf die Datenvalidierung wartet, aber keine sofortige Aktion erforderlich ist.

- **Grau**: Funktioniert gut für subtile Hintergrundprozesse, wie niedrigpriorisierte oder passive Ladeaufgaben, z. B. beim Abrufen zusätzlicher Daten, die die Benutzererfahrung nicht direkt beeinflussen.
  
- **Info**: Geeignet für Ladeszenarien, bei denen Sie dem Benutzer zusätzliche Informationen oder Klarstellungen bieten, z. B. indem Sie einen Spinner neben einer Nachricht anzeigen, die den laufenden Prozess erklärt.

Sie können diese Themen programmatisch auf den Spinner anwenden und visuelle Hinweise geben, die mit dem Kontext und der Wichtigkeit der Operation übereinstimmen.

Sie können dieses Verhalten mit der Methode `setTheme()` festlegen.

<ComponentDemo
path='/webforj/spinnerthemedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java']}
height='100px'
/>

### Größen {#expanses}

Sie können die Größe des Spinners, bekannt als **Größe**, anpassen, um den visuellen Raum zu füllen, den Sie benötigen. Der Spinner unterstützt verschiedene Größen, einschließlich `Expanse.SMALL`, `Expanse.MEDIUM` und `Expanse.LARGE`.

<ComponentDemo
path='/webforj/spinnerexpansedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java']}
height='100px'
/>

<TableBuilder name="Spinner" />
