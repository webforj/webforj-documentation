---
title: Spinner
sidebar_position: 110
description: >-
  Indicate background activity with the Spinner component, configuring theme,
  expanse, rotation speed, and pause or resume.
_i18n_hash: 22812c9195f148410b746c3547a0f118
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

Die `Spinner`-Komponente bietet einen visuellen Indikator, der anzeigt, dass im Hintergrund Vorgänge oder Ladevorgänge stattfinden. Sie wird häufig verwendet, um zu zeigen, dass das System Daten abruft oder wenn ein Prozess Zeit benötigt, um abgeschlossen zu werden. Der `Spinner` bietet dem Benutzer Feedback und signalisiert, dass das System aktiv arbeitet.

<!-- INTRO_END -->

Erstellen Sie eine Instanz des `Spinners` und definieren Sie dann sein Aussehen und Verhalten mit Methoden wie `setTheme()` und `setExpanse()`.

<ComponentDemo
path='/webforj/spinnerdemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java']}
height='225px'
/>

## Geschwindigkeit und Pausierung verwalten {#managing-speed-and-pausing}

Es ist möglich, die Geschwindigkeit in Millisekunden für den `Spinner` festzulegen und die Animation einfach zu pausieren oder fortzusetzen.

Anwendungsfälle für die Einstellung von Geschwindigkeiten umfassen die Unterscheidung zwischen Ladeprozessen. Schneller `Spinners` eignen sich zum Beispiel für kleinere Aufgaben, während langsamere `Spinners` besser für größere Aufgaben geeignet sind. Pausieren ist nützlich, wenn eine Benutzeraktion oder Bestätigung erforderlich ist, bevor der Prozess fortgesetzt wird.

### Geschwindigkeit anpassen {#adjusting-speed}

Sie können steuern, wie schnell sich der `Spinner` dreht, indem Sie seine Geschwindigkeit in Millisekunden mit der Methode `setSpeed()` anpassen. Ein niedrigerer Wert lässt den `Spinner` schneller drehen, während höhere Werte ihn verlangsamen.

```java
spinner.setSpeed(500); // Dreht schneller
```

:::info Standardgeschwindigkeit
Standardmäßig benötigt der `Spinner` 1000 Millisekunden, um eine volle Umdrehung abzuschließen.
:::

### Pausieren und Fortsetzen {#pausing-and-resuming}

Das Pausieren des `Spinners` ist nützlich, wenn ein Programm vorübergehend gestoppt oder auf Benutzereingaben wartet. Es informiert die Benutzer darüber, dass das Programm angehalten wurde und nicht aktiv läuft, was die Klarheit bei mehrstufigen Prozessen verbessert.

Um den Spinner zu pausieren und fortzusetzen, verwenden Sie die Methode `setPaused()`. Dies ist besonders hilfreich, wenn Sie die Drehanimation vorübergehend stoppen müssen.

```java
spinner.setPaused(true);  // Pausiert den Spinner
spinner.setPaused(false); // Setzt den Spinner fort
```

Dieses Beispiel zeigt, wie Sie die Geschwindigkeit einstellen und den `Spinner` pausieren/festlegen können:

<ComponentDemo
path='/webforj/spinnerspeeddemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java']}
height='150px'
/>

## Drehrichtung {#spin-direction}

Die Richtung des `Spinners` kann so gesteuert werden, dass er **im Uhrzeigersinn** oder **gegen den Uhrzeigersinn** dreht. Sie können dieses Verhalten mit der Methode `setClockwise()` angeben.

```java
spinner.setClockwise(false);  // Dreht gegen den Uhrzeigersinn
spinner.setClockwise(true);   // Dreht im Uhrzeigersinn
```

Diese Option zeigt visuell einen speziellen Zustand an oder dient als einzigartige Designwahl. Das Ändern der Drehrichtung kann helfen, zwischen verschiedenen Arten von Prozessen zu unterscheiden, z. B. Fortschritt vs. Umkehrung oder in bestimmten Kontexten einen klaren visuellen Hinweis zu geben.

<ComponentDemo
path='/webforj/spinnerdirectiondemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java']}
height='150px'
/>

## Styling {#styling}

### Themen {#themes}

Die `Spinner`-Komponente verfügt über mehrere integrierte Themen, die es Ihnen ermöglichen, schnell Stile anzuwenden, ohne benutzerdefiniertes CSS erstellen zu müssen. Diese Themen ändern das visuelle Erscheinungsbild des Spinners, sodass er für verschiedene Anwendungsfälle und Kontexte geeignet ist. Durch die Verwendung dieser vordefinierten Themen wird eine Konsistenz im Styling in Ihrer Anwendung sichergestellt.

Obwohl Spinner in verschiedenen Situationen verwendet werden, sind hier einige Beispielanwendungsfälle für die verschiedenen Themen:

- **Primär**: Ideal, um einen Ladezustand zu betonen, der ein Schlüsselteil des Benutzerflusses ist, z. B. beim Ausfüllen eines Formulars oder bei der Verarbeitung einer wichtigen Aktion.

- **Erfolg**: Nützlich, um erfolgreiche Hintergrundprozesse darzustellen, z. B. wenn ein Benutzer ein Formular einreicht und die App die letzten Schritte des Prozesses durchführt.

- **Gefahr**: Verwenden Sie dies für riskante oder kritische Vorgänge, wie das Löschen wichtiger Daten oder das Vornehmen unwiderruflicher Änderungen, bei denen ein visueller Hinweis auf Dringlichkeit oder Vorsicht erforderlich ist.

- **Warnung**: Verwenden Sie dies, um einen vorsichtigen oder weniger dringenden Prozess anzuzeigen, z. B. wenn der Benutzer auf die Datenvalidierung wartet, aber keine sofortige Aktion erforderlich ist.

- **Grau**: Funktioniert gut für subtile Hintergrundprozesse, z. B. für Aufgaben mit niedriger Priorität oder passives Laden, etwa beim Abrufen zusätzlicher Daten, die die Benutzererfahrung nicht direkt beeinflussen.

- **Info**: Geeignet für Ladeszenarien, in denen Sie dem Benutzer zusätzliche Informationen oder Klarstellungen bieten, z. B. indem Sie einen Spinner zusammen mit einer Nachricht anzeigen, die den laufenden Prozess erklärt.

Sie können diese Themen programmgesteuert auf den Spinner anwenden, um visuelle Hinweise zu geben, die mit dem Kontext und der Bedeutung des Vorgangs übereinstimmen.

Sie können dieses Verhalten mit der Methode `setTheme()` angeben.

<ComponentDemo
path='/webforj/spinnerthemedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java']}
height='100px'
/>

### Größen {#expanses}

Sie können die Größe des Spinners anpassen, die als **Größe** bezeichnet wird, um den visuellen Raum zu erfüllen, den Sie benötigen. Der Spinner unterstützt verschiedene Größen, einschließlich `Expanse.SMALL`, `Expanse.MEDIUM` und `Expanse.LARGE`.

<ComponentDemo
path='/webforj/spinnerexpansedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java']}
height='100px'
/>

<TableBuilder name="Spinner" />
