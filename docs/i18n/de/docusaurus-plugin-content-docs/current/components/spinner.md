---
title: Spinner
sidebar_position: 110
_i18n_hash: b1137c43133bce5c5a16df51c0aa82e3
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

Die `Spinner`-Komponente bietet einen visuellen Indikator, der anzeigt, dass im Hintergrund ein Prozess oder Laden stattfindet. Sie wird häufig verwendet, um zu zeigen, dass das System Daten abruft oder wenn ein Prozess Zeit benötigt, um abzuschließen. Der Spinner bietet dem Benutzer Rückmeldung und signalisiert, dass das System aktiv arbeitet.

## Grundlagen {#basics}

Um einen `Spinner` zu erstellen, können Sie das Design und die Größe anpassen. Die grundlegende Syntax umfasst das Erstellen einer `Spinner`-Instanz und das Definieren ihres Aussehens und Verhaltens durch Methoden wie `setTheme()` und `setExpanse()`.

<ComponentDemo 
path='/webforj/spinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java'
cssURL='/css/spinnerstyles/spinnerdemo.css'
height = '225px'
/>

## Geschwindigkeit verwalten und pausieren {#managing-speed-and-pausing}

Es ist möglich, die Geschwindigkeit in Millisekunden für den `Spinner` einzustellen und die Animation einfach zu pausieren oder fortzusetzen.

Verwendungsszenarien für das Einstellen von Geschwindigkeiten umfassen die Unterscheidung zwischen Ladeprozessen. Schnellere `Spinners` eignen sich für kleinere Aufgaben, während langsamere `Spinners` besser für größere Aufgaben geeignet sind. Pausieren ist nützlich, wenn eine Benutzeraktion oder -bestätigung erforderlich ist, bevor der Prozess fortgesetzt wird.

### Geschwindigkeit anpassen {#adjusting-speed}

Sie können steuern, wie schnell sich der `Spinner` dreht, indem Sie seine Geschwindigkeit in Millisekunden mit der Methode `setSpeed()` anpassen. Ein niedrigerer Wert lässt den `Spinner` schneller drehen, während höhere Werte ihn verlangsamen.

```java
spinner.setSpeed(500); // Dreht schneller
```

:::info Standardgeschwindigkeit
Standardmäßig benötigt der `Spinner` 1000 Millisekunden, um eine volle Umdrehung zu machen.
:::

### Pausieren und Fortsetzen {#pausing-and-resuming}

Das Pausieren des `Spinner` ist nützlich, wenn ein Programm vorübergehend angehalten wird oder auf Benutzereingaben wartet. Es zeigt den Benutzern, dass das Programm nicht aktiv läuft, was die Klarheit während mehrstufiger Prozesse verbessert.

Um den Spinner zu pausieren und fortzusetzen, verwenden Sie die Methode `setPaused()`. Dies ist besonders hilfreich, wenn Sie die drehende Animation vorübergehend stoppen müssen.

```java
spinner.setPaused(true);  // Spinner pausieren
spinner.setPaused(false); // Spinner fortsetzen
```

Dieses Beispiel zeigt, wie die Geschwindigkeit eingestellt wird und wie man den `Spinner` pausiert/fortsetzt:

<ComponentDemo 
path='/webforj/spinnerspeeddemo?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java'
cssURL='/css/spinnerstyles/spinnerspeeddemo.css'
height = '150px'
/>

## Drehrichtung {#spin-direction}

Die Richtung des `Spinner` kann gesteuert werden, um **im Uhrzeigersinn** oder **gegen den Uhrzeigersinn** zu rotieren. Dieses Verhalten können Sie mit der Methode `setClockwise()` festlegen.

```java
spinner.setClockwise(false);  // Dreht gegen den Uhrzeigersinn
spinner.setClockwise(true);   // Dreht im Uhrzeigersinn
```

Diese Option zeigt visuell einen speziellen Status an oder dient als einzigartiges Designelement. Das Ändern der Drehrichtung kann helfen, zwischen verschiedenen Arten von Prozessen zu unterscheiden, z.B. Fortschritt vs. Rückwärtsbewegung, oder einen spezifischen visuellen Hinweis in bestimmten Kontexten zu geben.

<ComponentDemo 
path='/webforj/spinnerdirectiondemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java'
height = '150px'
/>

## Stilgestaltung {#styling}

### Themen {#themes}

Die `Spinner`-Komponente bietet mehrere integrierte Themen, die es Ihnen ermöglichen, Stile schnell anzuwenden, ohne benutzerdefiniertes CSS zu benötigen. Diese Themen ändern das visuelle Erscheinungsbild des Spinners, sodass er für verschiedene Verwendungsszenarien und Kontexte geeignet ist. Die Verwendung dieser vordefinierten Themen sorgt für Konsistenz im Styling Ihrer App.

Während Spinner für verschiedene Situationen verwendet werden, sind hier einige Beispielverwendungsmöglichkeiten für die verschiedenen Themen:

- **Primär**: Ideal zur Hervorhebung eines Ladezustands, der ein wichtiger Teil des Benutzerablaufs ist, z.B. beim Einreichen eines Formulars oder Verarbeiten einer wichtigen Aktion.
  
- **Erfolg**: Nützlich, um erfolgreich abgeschlossene Hintergrundprozesse darzustellen, z.B. wenn ein Benutzer ein Formular einreicht und die App die letzten Schritte des Prozesses ausführt.
  
- **Gefahr**: Verwenden Sie dies für riskante oder kritische Operationen, wie das Löschen wichtiger Daten oder das Vornehmen unwiderruflicher Änderungen, bei denen ein visueller Hinweis auf Dringlichkeit oder Vorsicht erforderlich ist.
  
- **Warnung**: Verwenden Sie dies, um einen vorsichtigen oder weniger dringenden Prozess anzuzeigen, z.B. wenn der Benutzer auf Datenvalidierung wartet, aber keine sofortige Aktion erforderlich ist.

- **Grau**: Eignet sich gut für subtile Hintergrundprozesse, wie niedrig-priorisierte oder passive Ladeaufgaben, z.B. beim Abrufen ergänzender Daten, die keinen direkten Einfluss auf die Benutzererfahrung haben.
  
- **Info**: Geeignet für Ladeszenarien, in denen Sie zusätzliche Informationen oder Klarstellungen für den Benutzer bereitstellen, z.B. indem Sie einen Spinner zusammen mit einer Nachricht anzeigen, die den laufenden Prozess erklärt.

Sie können diese Themen programmatisch auf den Spinner anwenden und visuelle Hinweise bereitstellen, die den Kontext und die Wichtigkeit der Operation widerspiegeln.

Dieses Verhalten können Sie mit der Methode `setTheme()` festlegen.

<ComponentDemo 
path='/webforj/spinnerthemedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java'
cssURL='/css/spinnerstyles/spinnerthemedemo.css'
height = '100px'
/>

### Größen {#expanses}

Sie können die Größe des Spinners, bekannt als **Größe**, anpassen, um den visuellen Raum zu nutzen, den Sie benötigen. Der Spinner unterstützt verschiedene Größen, einschließlich `Expanse.SMALL`, `Expanse.MEDIUM` und `Expanse.LARGE`.

<ComponentDemo 
path= '/webforj/spinnerexpansedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java'
cssURL='/css/spinnerstyles/spinnerexpansedemo.css'
height = '100px'
/>

<TableBuilder name="Spinner" />
