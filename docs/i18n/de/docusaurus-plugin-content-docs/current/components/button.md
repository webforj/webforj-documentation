---
title: Button
sidebar_position: 15
_i18n_hash: 0282098a1b80b4d494409d4f416caa5d
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

Eine `Button`-Komponente ist ein grundlegendes Element der Benutzeroberfläche, das in der Anwendungsentwicklung verwendet wird, um interaktive Elemente zu erstellen, die Aktionen oder Ereignisse auslösen, wenn sie angeklickt oder aktiviert werden. Es fungiert als klickbares Element, mit dem Benutzer interagieren können, um verschiedene Aktionen innerhalb einer Anwendung oder Website auszuführen. 

Der Hauptzweck der `Button`-Komponente besteht darin, den Benutzern einen klaren und intuitiven Handlungsaufruf zu geben, der sie anleitet, spezifische Aufgaben auszuführen, wie z. B. das Einreichen eines Formulars, die Navigation zu einer anderen Seite, das Auslösen einer Funktion oder den Beginn eines Prozesses. Buttons sind entscheidend, um Benutzerinteraktionen zu verbessern, die Zugänglichkeit zu optimieren und ein ansprechenderes Benutzererlebnis zu schaffen.

<ComponentDemo 
path='/webforj/button?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonView.java'
height='300px'
/>

## Verwendungen {#usages}

Die `Button`-Klasse ist eine vielseitige Komponente, die häufig in verschiedenen Situationen eingesetzt wird, in denen Benutzerinteraktionen und -aktionen ausgelöst werden müssen. Hier sind einige typische Szenarien, in denen Sie einen Button in Ihrer Anwendung benötigen könnten:

1. **Formulareinreichung**: Buttons werden oft verwendet, um Formulardaten zu übermitteln. Zum Beispiel können Sie in einer Anwendung:

  > - Ein "Absenden"-Button, um Daten an den Server zu senden
  > - Ein "Löschen"-Button, um alle bereits im Formular eingegebenen Informationen zu entfernen


2. **Benutzeraktionen**: Buttons ermöglichen es Benutzern, spezifische Aktionen innerhalb der Anwendung durchzuführen. Beispielsweise können Sie einen Button mit der Beschriftung haben:

  > - "Löschen", um die Löschung eines ausgewählten Elements einzuleiten
  > - "Speichern", um Änderungen an einem Dokument oder einer Seite zu speichern.

3. **Bestätigungsdialoge**: Buttons sind oft in [`Dialog`](../components/dialog) Komponenten enthalten, die für verschiedene Zwecke gebaut wurden, um den Benutzern Optionen für die Bestätigung oder Stornierung einer Aktion oder jede andere Funktionalität, die in der verwendeten [`Dialog`](../components/dialog) enthalten ist, zu bieten.

4. **Interaktionsauslöser**: Buttons können als Auslöser für Interaktionen oder Ereignisse innerhalb der Anwendung dienen. Durch Klicken auf einen Button können Benutzer komplexe Aktionen einleiten oder Animationen auslösen, Inhalte aktualisieren oder die Anzeige ändern.

5. **Navigation**: Buttons können für Navigationszwecke verwendet werden, z. B. um zwischen verschiedenen Abschnitten oder Seiten einer Anwendung zu wechseln. Navigationsbuttons könnten Folgendes umfassen:

  > - "Weiter" - Leitet den Benutzer zur nächsten Seite oder zum nächsten Abschnitt der aktuellen Anwendung oder Seite.
  > - "Zurück" - Bringt den Benutzer zurück zur vorherigen Seite der Anwendung oder zum Abschnitt, in dem er sich befindet.
  > - "Zurück" - Bringt den Benutzer zum ersten Teil der Anwendung oder der Seite, in der er sich befindet.

## Hinzufügen von Icons zu Buttons <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

Das Einfügen eines Icons in einen Button kann das Design Ihrer App erheblich verbessern und es den Benutzern ermöglichen, schnell handlungsrelevante Elemente auf dem Bildschirm zu erkennen. Die [`Icon`](./icon.md) Komponente bietet eine breite Auswahl an Icons zur Auswahl.

Durch die Verwendung der Methoden `setPrefixComponent()` und `setSuffixComponent()` haben Sie die Flexibilität zu bestimmen, ob ein `Icon` vor oder nach dem Text auf einem Button erscheinen soll. Alternativ kann die Methode `setIcon()` verwendet werden, um ein `Icon` nach dem Text, aber vor dem `suffix`-Slot des Buttons hinzuzufügen.

:::tip
Standardmäßig erbt ein `Icon` das Theme und die Größe des Buttons.
:::

Nachfolgend sind Beispiele von Buttons mit Text links und rechts sowie ein Button mit nur einem Icon dargestellt:

<ComponentDemo 
path='/webforj/buttonicon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonIconView.java'
height="200px"
/>

### Namen {#names}

Die `Button`-Komponente verwendet Namen, die für die Zugänglichkeit wichtig sind. Wenn kein Name explizit festgelegt ist, wird das Label des `Button` stattdessen verwendet. Einige Icons haben jedoch keine Labels und zeigen nur nicht-textliche Elemente, wie Icons, an. In diesem Fall ist es sinnvoll, die Methode `setName()` zu verwenden, um sicherzustellen, dass die erstellte `Button`-Komponente den Standards für die Zugänglichkeit entspricht.

## Deaktivieren eines Buttons {#disabling-a-button}

Button-Komponenten können, wie viele andere auch, deaktiviert werden, um dem Benutzer zu signalisieren, dass eine bestimmte Aktion noch nicht oder nicht mehr verfügbar ist. Ein deaktivierter Button verringert die Opazität des Buttons und ist für alle Button-Themen und -Größen verfügbar.

<ComponentDemo 
path='/webforj/buttondisable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonDisableView.java'
/>

<br />

Ein Button kann jederzeit im Code deaktiviert werden, indem die Funktion <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink> verwendet wird. Zur zusätzlichen Bequemlichkeit kann ein Button auch beim Klicken mit der eingebauten <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink> Funktion deaktiviert werden.

In einigen Anwendungen löst das Klicken auf einen Button eine lang laufende Aktion aus. In den meisten Fällen möchte die Anwendung sicherstellen, dass nur ein einziger Klick verarbeitet wird. Dies kann in Umgebungen mit hoher Latenz ein Problem darstellen, wenn der Benutzer den Button mehrmals klickt, bevor die Anwendung die Möglichkeit hat, mit der Verarbeitung der resultierenden Aktion zu beginnen. 

:::tip
Das Deaktivieren beim Klicken hilft nicht nur, die Verarbeitung von Aktionen zu optimieren, sondern verhindert auch, dass der Entwickler dieses Verhalten selbst implementieren muss, da diese Methode optimiert wurde, um Rundreisekommunikationen zu reduzieren.
:::

## Styling {#styling}

### Themen {#themes}

`Button`-Komponenten kommen mit <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 diskreten Themen</JavadocLink>, die für eine schnelle Gestaltung ohne CSS vordefiniert sind. Diese Themen sind vordefinierte Stile, die auf Buttons angewendet werden können, um deren Erscheinungsbild und visuelle Präsentation zu ändern. Sie bieten eine schnelle und konsistente Möglichkeit, das Aussehen von Buttons in einer Anwendung anzupassen. 

Während es viele Anwendungsfälle für jedes der verschiedenen Themen gibt, sind einige Beispiele:

  - **Gefährlich**: Am besten geeignet für Aktionen mit schwerwiegenden Folgen, wie das Löschen ausgefüllter Informationen oder das dauerhafte Löschen eines Kontos/Daten.
  - **Standard**: Geeignet für Aktionen innerhalb einer Anwendung, die keine besondere Aufmerksamkeit erfordern und generisch sind, wie das Umschalten einer Einstellung.
  - **Primär**: Geeignet als Haupt-"Handlungsaufruf" auf einer Seite, wie z. B. Anmeldung, Speichern von Änderungen oder Fortfahren zu einer anderen Seite.
  - **Erfolg**: Ausgezeichnet zur Visualisierung des erfolgreichen Abschlusses eines Elements in einer Anwendung, wie dem Einsenden eines Formulars oder dem Abschluss eines Anmeldevorgangs. Das Erfolgsthema kann programmgesteuert angewendet werden, sobald eine erfolgreiche Aktion abgeschlossen ist.
  - **Warnung**: Nützlich, um anzuzeigen, dass ein Benutzer dabei ist, eine potenziell riskante Aktion durchzuführen, wie das Navigieren von einer Seite mit nicht gespeicherten Änderungen. Diese Aktionen sind oft weniger Auswirkungen als die, die das Gefahren-Thema verwenden würden.
  - **Grau**: Gut für subtile Aktionen, wie geringfügige Einstellungen oder Aktionen, die mehr ergänzend zur Seite sind und nicht Teil der Hauptfunktionalität.
  - **Info**: Gut, um dem Benutzer zusätzliche klärende Informationen bereitzustellen.

Nachfolgend sind Beispiel-Buttons mit jeweils angewendetem Thema dargestellt: <br/>

<ComponentDemo 
path='/webforj/buttonthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonThemesView.java'
cssURL='/css/button/buttonThemes.css'
height='175px'
/>

### Größen {#expanses}
Die folgenden <JavadocLink type="foundation" location="com/webforj/component/Expanse">Größenwerte</JavadocLink> ermöglichen eine schnelle Gestaltung ohne Verwendung von CSS. Dies ermöglicht die Manipulation der Dimensionen des Buttons, ohne dass diese explizit über Styling festgelegt werden müssen. Neben der Vereinfachung des Stylings trägt es auch dazu bei, eine Einheitlichkeit in Ihrer Anwendung zu schaffen und aufrechtzuerhalten. Die Standardgröße des `Button` ist `Expanse.MEDIUM`.

Unterschiedliche Größen sind oft für unterschiedliche Verwendungen geeignet:
  - **Größere** Größenwerte eignen sich für Buttons, die Aufmerksamkeit erregen, Funktionalität betonen oder Teil der Hauptfunktionalität einer Anwendung oder Seite sind.
  - **Medium**-Button, die Standardgröße, sollten als "Standardgröße" verwendet werden, wenn das Verhalten eines Buttons nicht wichtiger oder weniger wichtig ist als bei anderen ähnlichen Komponenten.
  - **Kleinere** Größenwerte sollten für Buttons verwendet werden, die kein integriertes Verhalten in der Anwendung haben und eine ergänzende oder utilitaristische Rolle spielen, anstatt eine wichtige Rolle in der Benutzerinteraktion zu spielen. Dazu gehören `Button`-Komponenten, die nur mit Icons für utilitaristische Zwecke verwendet werden.

Nachfolgend sind die verschiedenen Größe unterstützt für die `Button`-Komponente dargestellt: <br/>

<ComponentDemo 
path='/webforj/buttonexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java'
height='200px'
/>

<TableBuilder name="Button" />

## Best Practices {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der `Button`-Komponente zu gewährleisten, beachten Sie bitte die folgenden Best Practices:

1. **Klarer Text**: Verwenden Sie klaren und prägnanten Text innerhalb Ihrer `Button`-Komponente, um einen klaren Hinweis auf deren Zweck zu geben.

2. **Angemessene visuelle Gestaltung**: Berücksichtigen Sie die visuelle Gestaltung und das Thema des `Button`, um Konsistenz mit dem Design Ihrer Anwendung sicherzustellen. Zum Beispiel:
  > - Ein "Abbrechen"-`Button` sollte mit dem entsprechenden Thema oder CSS-Styling gestaltet werden, um sicherzustellen, dass den Benutzern klar ist, dass sie eine Aktion abbrechen möchten.
  > - Ein "Bestätigen"-`Button` hätte ein anderes Styling als ein "Abbrechen"-Button, würde aber ebenso hervorstechen, um sicherzustellen, dass die Benutzer wissen, dass dies eine besondere Aktion ist.

3. **Effiziente Ereignisbehandlung**: Behandeln Sie `Button`-Ereignisse effizient und geben Sie den Benutzern angemessenes Feedback. Überprüfen Sie [Ereignisse](../building-ui/events), um effiziente Verhaltensweisen zur Ereignisbehandlung zu überprüfen.

4. **Testen und Zugänglichkeit**: Testen Sie das Verhalten des Buttons in verschiedenen Szenarien, z. B. wenn er deaktiviert ist oder den Fokus erhält, um ein reibungsloses Benutzererlebnis zu gewährleisten. Befolgen Sie die Richtlinien zur Barrierefreiheit, um den `Button` für alle Benutzer, einschließlich derjenigen, die auf unterstützende Technologien angewiesen sind, benutzbar zu machen.
