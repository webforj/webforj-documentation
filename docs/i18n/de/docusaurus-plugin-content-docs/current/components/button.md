---
title: Button
sidebar_position: 15
_i18n_hash: 5e0b4998a50b6c7d935c53c9c11009d6
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

Ein `Button` ist ein klickbares Element, das eine Aktion auslöst, wenn es gedrückt wird. Er kann Text, Icons oder eine Kombination aus beidem anzeigen. Buttons unterstützen mehrere visuelle Themen und Größen und können deaktiviert werden, um während langandauernder Vorgänge oder wenn bestimmte Bedingungen nicht erfüllt sind, eine Interaktion zu verhindern.

<!-- INTRO_END -->

## Verwendungen {#usages}

Die `Button`-Klasse ist eine vielseitige Komponente, die häufig in verschiedenen Situationen verwendet wird, in denen Benutzerinteraktionen und Aktionen ausgelöst werden müssen. Hier sind einige typische Szenarien, in denen Sie in Ihrer Anwendung einen Button benötigen könnten:

1. **Formularübermittlung**: Buttons werden oft verwendet, um Formulardaten zu übermitteln. Zum Beispiel können Sie in einer Anwendung verwenden:

  > - Einen "Absenden"-Button, um Daten an den Server zu senden
  > - Einen "Löschen"-Button, um alle Informationen, die bereits im Formular vorhanden sind, zu entfernen


2. **Benutzeraktionen**: Buttons werden verwendet, um Benutzern die Durchführung bestimmter Aktionen innerhalb der Anwendung zu ermöglichen. Zum Beispiel können Sie einen Button mit der Bezeichnung haben:

  > - "Löschen", um das Löschen eines ausgewählten Elements einzuleiten
  > - "Speichern", um Änderungen an einem Dokument oder einer Seite zu speichern.

3. **Bestätigungsdialoge**: Buttons sind oft in [`Dialog`](../components/dialog) Komponenten enthalten, die für verschiedene Zwecke erstellt wurden, um den Benutzern Optionen zum Bestätigen oder Abbrechen einer Aktion oder einer anderen Funktionalität, die in den verwendeten [`Dialog`](../components/dialog) integriert ist, zu bieten.

4. **Interaktionsauslöser**: Buttons können als Auslöser für Interaktionen oder Ereignisse innerhalb der Anwendung dienen. Durch Klicken auf einen Button können Benutzer komplexe Aktionen auslösen oder Animationen, das Aktualisieren von Inhalten oder das Aktualisieren der Anzeige initiieren.

5. **Navigation**: Buttons können für Navigationszwecke verwendet werden, wie z.B. das Bewegen zwischen verschiedenen Abschnitten oder Seiten innerhalb einer Anwendung. Navigationsbuttons könnten Folgendes umfassen:

  > - "Weiter" - Führt den Benutzer zur nächsten Seite oder zum nächsten Abschnitt der aktuellen Anwendung oder Seite.
  > - "Zurück" - Bringt den Benutzer zur vorherigen Seite der Anwendung oder des Abschnitts, in dem er sich befindet.
  > - "Zurück" - Bringt den Benutzer zum ersten Teil der Anwendung oder Seite, in der er sich befindet.
  
Im folgenden Beispiel werden Buttons verwendet, um Formulardaten zu übermitteln und Eingaben zu löschen:

<ComponentDemo 
path='/webforj/button?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonView.java'
height='300px'
/>

## Hinzufügen von Icons zu Buttons <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

Das Einfügen eines Icons in einen Button kann das Design Ihrer App erheblich verbessern und es Benutzern ermöglichen, umgehend handlungsrelevante Elemente auf dem Bildschirm zu identifizieren. Die [`Icon`](./icon.md) Komponente bietet eine große Auswahl an Icons zur Auswahl.

Durch die Nutzung der Methoden `setPrefixComponent()` und `setSuffixComponent()` haben Sie die Flexibilität zu bestimmen, ob ein `Icon` vor oder nach dem Text auf einem Button erscheinen soll. Alternativ kann die `setIcon()` Methode verwendet werden, um ein `Icon` nach dem Text, aber vor dem `suffix` Slot des Buttons hinzuzufügen.

<!-- Fügen Sie dies wieder hinzu, sobald das Icon integriert ist -->
<!-- Weitere Informationen zur Konfiguration und Anpassung von Icons finden Sie auf der Seite [Icon-Komponente](../components/icon). -->

:::tip
Standardmäßig erbt ein `Icon` das Thema und die Größe des Buttons.
:::

Im Folgenden sind Beispiele für Buttons mit Text links und rechts sowie ein Button mit nur einem Icon aufgeführt:

<ComponentDemo 
path='/webforj/buttonicon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonIconView.java'
height="200px"
/>

### Namen {#names}

Die `Button`-Komponente verwendet Bezeichnungen, die für die Zugänglichkeit verwendet werden. Wenn ein Name nicht ausdrücklich festgelegt ist, wird das Label des `Button` stattdessen verwendet. Einige Icons haben jedoch keine Labels und zeigen nur Nicht-Text-Elemente wie Icons an. In diesem Fall ist es sinnvoll, die Methode `setName()` zu verwenden, um sicherzustellen, dass die erstellte `Button`-Komponente den Zugänglichkeitsstandards entspricht.

## Deaktivieren eines Buttons {#disabling-a-button}

Button-Komponenten können, wie viele andere auch, deaktiviert werden, um einem Benutzer anzuzeigen, dass eine bestimmte Aktion noch nicht oder nicht mehr verfügbar ist. Ein deaktivierter Button verringert die Opazität des Buttons und ist für alle Button-Themen und -Größen verfügbar.

<ComponentDemo 
path='/webforj/buttondisable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonDisableView.java'
/>

Ein Deaktivieren eines Buttons kann zu jeder Zeit im Code erfolgen, indem Sie die <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink> Funktion verwenden. Zur zusätzlichen Bequemlichkeit kann ein Button auch deaktiviert werden, wenn er angeklickt wird, indem Sie die integrierte <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink> Funktion verwenden.

In einigen Fällen löst das Klicken auf einen Button eine langandauernde Aktion aus. Das Deaktivieren des Buttons, bis Ihre App die Aktion bearbeitet hat, verhindert, dass der Benutzer den Button mehrfach klickt, insbesondere in Umgebungen mit hoher Latenz.

:::tip
Das Deaktivieren bei Klick hilft nicht nur, die Verarbeitung von Aktionen zu optimieren, sondern verhindert auch, dass der Entwickler dieses Verhalten selbst implementieren muss, da diese Methode optimiert wurde, um Rundreise-Kommunikationen zu reduzieren.
:::

## Styling {#styling}

### Themen {#themes}

`Button`-Komponenten besitzen <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 diskrete Themen</JavadocLink>, die für ein schnelles Styling ohne Verwendung von CSS integriert sind. Diese Themen sind vordefinierte Stile, die auf Buttons angewendet werden können, um ihr Erscheinungsbild und ihre visuelle Präsentation zu ändern. Sie bieten eine schnelle und konsistente Möglichkeit, das Aussehen von Buttons in der gesamten Anwendung zu personalisieren.

Es gibt viele Anwendungsfälle für jedes der verschiedenen Themen, einige Beispiele sind:

  - **Gefahr**: Am besten für Aktionen mit schwerwiegenden Folgen geeignet, wie das Löschen ausgefüllter Informationen oder das endgültige Löschen eines Kontos/Daten.
  - **Standard**: Geeignet für Aktionen in der gesamten Anwendung, die keine besondere Aufmerksamkeit erfordern und allgemein sind, wie das Umschalten einer Einstellung.
  - **Primär**: Geeignet als Haupt-"Call-to-Action" auf einer Seite, wie z.B. die Anmeldung, das Speichern von Änderungen oder das Fortfahren zu einer anderen Seite.
  - **Erfolg**: Ausgezeichnet zur Visualisierung des erfolgreichen Abschlusses eines Elements in einer Anwendung, wie z.B. das Einreichen eines Formulars oder der Abschluss eines Anmeldeprozesses. Das Erfolgsthema kann programmatisch angewendet werden, sobald eine erfolgreiche Aktion abgeschlossen wurde.
  - **Warnung**: Nützlich, um anzuzeigen, dass ein Benutzer kurz davor steht, eine potenziell riskante Aktion auszuführen, wie z.B. das Verlassen einer Seite mit nicht gespeicherten Änderungen. Diese Aktionen sind oft weniger gravierend als diejenigen, die das Gefahr-Thema verwenden würden.
  - **Grau**: Gut für subtile Aktionen, wie kleinere Einstellungen oder Aktionen, die mehr ergänzender Natur sind und nicht Teil der Hauptfunktionalität.
  - **Info**: Gut für zusätzliche klärende Informationen für den Benutzer.

Im Folgenden sind Beispiel-Buttons mit jedem der unterstützten Themen dargestellt: <br/>

<ComponentDemo 
path='/webforj/buttonthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonThemesView.java'
height='175px'
/>

### Größen {#expanses}
Die folgenden <JavadocLink type="foundation" location="com/webforj/component/Expanse"> Größenwerte </JavadocLink> ermöglichen ein schnelles Styling ohne Verwendung von CSS. Dies ermöglicht die Manipulation der Dimensionen des Buttons, ohne dass diese explizit mit Styling festgelegt werden müssen. Neben der Vereinfachung des Stylings hilft es auch, eine Einheitlichkeit in Ihrer Anwendung zu schaffen und aufrechtzuerhalten. Die Standardgröße des `Button` ist `Expanse.MEDIUM`.

Unterschiedliche Größen sind oft für unterschiedliche Verwendungen geeignet:
  - **Größere** Größenwerte eignen sich für Buttons, die Aufmerksamkeit erregen, die Funktionalität betonen oder für die Kernfunktionalität einer Anwendung oder Seite von wesentlicher Bedeutung sind.
  - **Mittlere** Größen-Buttons, die Standardgröße, sollten die Standardgröße von Buttons sein. Die Funktionen dieser Buttons sollten weder wichtiger noch weniger wichtig als ähnliche Komponenten sein.
  - **Kleinere** Größenwerte sollten für Buttons verwendet werden, die keine integralen Verhaltensweisen in der Anwendung haben und eine eher ergänzende oder utilitaristische Rolle spielen, anstatt eine wichtige Rolle in der Benutzerinteraktion zu spielen. Dazu gehören `Button`-Komponenten, die nur mit Icons für utilitaristische Zwecke verwendet werden.

Im Folgenden sind die verschiedenen Größen aufgeführt, die für die `Button`-Komponente unterstützt werden: <br/>

<ComponentDemo 
path='/webforj/buttonexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java'
height='200px'
/>

<TableBuilder name="Button" />

## Best Practices {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der `Button`-Komponente sicherzustellen, sollten Sie die folgenden Best Practices berücksichtigen:

1. **Klarer Text**: Verwenden Sie klaren und prägnanten Text in Ihrer `Button`-Komponente, um eine deutliche Anzeige ihres Zwecks zu geben.

2. **Angemessene visuelle Gestaltung**: Berücksichtigen Sie das visuelle Styling und das Thema des `Button`, um Konsistenz mit dem Design Ihrer Anwendung sicherzustellen. Zum Beispiel:
  > - Eine "Abbrechen"-`Button`-Komponente sollte mit dem entsprechenden Thema oder CSS-Styles gestylt werden, um sicherzustellen, dass die Benutzer sich sicher sind, dass sie eine Aktion abbrechen möchten.
  > - Ein "Bestätigen"-`Button` würde ein anderes Styling als ein "Abbrechen"-Button haben, wäre aber ähnlich auffällig, um sicherzustellen, dass die Benutzer wissen, dass dies eine besondere Aktion ist.

3. **Effiziente Ereignisbehandlung**: Behandeln Sie `Button`-Ereignisse effizient und geben Sie den Benutzern angemessenes Feedback. Überprüfen Sie [Ereignisse](../building-ui/events), um effiziente Ereignis-Hinzufügungs-Verhalten zu überprüfen.

4. **Testen und Zugänglichkeit**: Testen Sie das Verhalten des Buttons in verschiedenen Szenarien, wie z.B. wenn er deaktiviert ist oder den Fokus erhält, um eine reibungslose Benutzererfahrung sicherzustellen. Befolgen Sie die Vorgaben zur Zugänglichkeit, um den `Button` für alle Benutzer, einschließlich derjenigen, die auf unterstützende Technologien angewiesen sind, nutzbar zu machen.
