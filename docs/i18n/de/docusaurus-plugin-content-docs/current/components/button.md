---
title: Button
sidebar_position: 15
_i18n_hash: 7df385d72b74249e5689c31575568ae8
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

Ein `Button` ist ein klickbares Element, das eine Aktion auslöst, wenn es gedrückt wird. Er kann Text, Symbole oder eine Kombination aus beidem anzeigen. Buttons unterstützen verschiedene visuelle Themen und Größen und können deaktiviert werden, um Interaktionen während lang laufender Vorgänge oder wenn bestimmte Bedingungen nicht erfüllt sind, zu verhindern.

<!-- INTRO_END -->

## Verwendungen {#usages}

Die `Button`-Klasse ist eine vielseitige Komponente, die häufig in verschiedenen Situationen verwendet wird, in denen Benutzerinteraktionen und Aktionen ausgelöst werden müssen. Hier sind einige typische Szenarien, in denen Sie einen Button in Ihrer Anwendung benötigen könnten:

1. **Formularübermittlung**: Buttons werden oft verwendet, um Formulardaten zu übermitteln. Zum Beispiel können Sie in einer Anwendung Folgendes verwenden:

  > - Ein "Absenden"-Button, um Daten an den Server zu senden
  > - Ein "Löschen"-Button, um Informationen, die bereits im Formular vorhanden sind, zu entfernen

2. **Benutzeraktionen**: Buttons werden verwendet, um es Benutzern zu ermöglichen, bestimmte Aktionen innerhalb der Anwendung durchzuführen. Beispielsweise können Sie einen Button mit folgender Beschriftung haben:

  > - "Löschen", um die Löschung eines ausgewählten Elements einzuleiten
  > - "Speichern", um Änderungen an einem Dokument oder einer Seite zu speichern.

3. **Bestätigungsdialoge**: Buttons sind oft in [`Dialog`](../components/dialog)-Komponenten enthalten, die für verschiedene Zwecke erstellt wurden, um den Benutzern Optionen zum Bestätigen oder Abbrechen einer Aktion oder andere Funktionalitäten, die in dem verwendeten [`Dialog`](../components/dialog) integriert sind, bereitzustellen.

4. **Interaktionsauslöser**: Buttons können als Auslöser für Interaktionen oder Ereignisse innerhalb der Anwendung dienen. Durch Klicken auf einen Button können Benutzer komplexe Aktionen initiieren oder Animationen auslösen, den Inhalt aktualisieren oder die Anzeige ändern.

5. **Navigation**: Buttons können für Navigationszwecke verwendet werden, z. B. um zwischen verschiedenen Abschnitten oder Seiten innerhalb einer Anwendung zu wechseln. Navigations-Buttons könnten Folgendes umfassen:

  > - "Weiter" - Leitet den Benutzer zur nächsten Seite oder zum nächsten Abschnitt der aktuellen Anwendung oder Seite.
  > - "Zurück" - Bringt den Benutzer zur vorherigen Seite der Anwendung oder zum Abschnitt, in dem er sich befindet.
  > - "Zurück" Bringt den Benutzer zurück zur ersten Stelle der Anwendung oder Seite, in der er sich befindet.

Das folgende Beispiel zeigt Buttons, die für die Formularübermittlung und das Löschen von Eingaben verwendet werden:

<ComponentDemo 
path='/webforj/button?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonView.java'
height='300px'
/>

## Hinzufügen von Symbolen zu Buttons <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

Das Hinzufügen eines Symbols zu einem Button kann das Design Ihrer Anwendung erheblich verbessern und es den Benutzern ermöglichen, schnell umsetzbare Elemente auf dem Bildschirm zu identifizieren. Die [`Icon`](./icon.md)-Komponente bietet eine große Auswahl an Symbolen zur Auswahl.

Durch die Verwendung der Methoden `setPrefixComponent()` und `setSuffixComponent()` haben Sie die Flexibilität zu bestimmen, ob ein `Icon` vor oder nach dem Text auf einem Button erscheinen soll. Alternativ kann die Methode `setIcon()` verwendet werden, um ein `Icon` nach dem Text, aber vor dem `suffix`-Slot des Buttons hinzuzufügen.

<!-- Fügen Sie dies ein, sobald das Icon zusammengeführt wurde -->
<!-- Weitere Informationen zur Konfiguration und Anpassung von Symbolen finden Sie auf der Seite [Icon component](../components/icon). -->

:::tip
Standardmäßig erbt ein `Icon` das Thema und die Größe des Buttons.
:::

Im Folgenden finden Sie Beispiele für Buttons mit Text links und rechts sowie einen Button mit nur einem Symbol:

<ComponentDemo 
path='/webforj/buttonicon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonIconView.java'
height="200px"
/>

### Namen {#names}

Die `Button`-Komponente verwendet Namen, die für die Barrierefreiheit wichtig sind. Wenn ein Name nicht ausdrücklich festgelegt wird, wird stattdessen das Label des `Button` verwendet. Einige Symbole haben jedoch keine Labels und zeigen nur nicht-textliche Elemente, wie Symbole, an. In diesem Fall ist es sinnvoll, die Methode `setName()` zu verwenden, um sicherzustellen, dass die erstellte `Button`-Komponente den Standards für Barrierefreiheit entspricht.

## Deaktivieren eines Buttons {#disabling-a-button}

Button-Komponenten können wie viele andere deaktiviert werden, um dem Benutzer mitzuteilen, dass eine bestimmte Aktion noch nicht oder nicht mehr verfügbar ist. Ein deaktivierter Button verringert die Opazität des Buttons und ist für alle Button-Themen und -Größen verfügbar.

<ComponentDemo 
path='/webforj/buttondisable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonDisableView.java'
/>

Ein Button kann jederzeit im Code deaktiviert werden, indem die Funktion <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink> verwendet wird. Zusätzlich kann ein Button auch deaktiviert werden, wenn er geklickt wird, indem die integrierte <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink>-Funktion verwendet wird.

In einigen Fällen löst das Klicken auf einen Button eine lang laufende Aktion aus. Das Deaktivieren des Buttons, bis Ihre Anwendung die Aktion verarbeitet, verhindert, dass der Benutzer den Button mehrmals klickt, insbesondere in Umgebungen mit hoher Latenz.

:::tip
Das Deaktivieren beim Klicken hilft nicht nur, die Verarbeitung von Aktionen zu optimieren, sondern verhindert auch, dass der Entwickler dieses Verhalten selbst implementieren muss, da diese Methode optimiert wurde, um die Kommunikation zu reduzieren.
:::

## Styling {#styling}

### Themen {#themes}

Die `Button`-Komponenten verfügen über <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 einzelne Themen</JavadocLink>, die für ein schnelles Styling ohne Verwendung von CSS integriert sind. Diese Themen sind vordefinierte Stile, die auf Buttons angewendet werden können, um ihr Aussehen und ihre visuelle Darstellung zu ändern. Sie bieten eine schnelle und konsistente Möglichkeit, das Aussehen von Buttons in einer Anwendung anzupassen.

Obwohl es viele Anwendungsfälle für jedes der verschiedenen Themen gibt, sind einige Anwendungsbeispiele:

  - **Gefährlich**: Am besten geeignet für Aktionen mit schwerwiegenden Folgen, wie das Löschen ausgefüllter Informationen oder das endgültige Löschen eines Kontos/Daten.
  - **Standard**: Geeignet für Aktionen in einer Anwendung, die keine besondere Aufmerksamkeit erfordern und allgemein sind, wie das Umschalten eines Einstellung.
  - **Primär**: Geeignet als Haupt-"Handlungsaufforderung" auf einer Seite, wie das Anmelden, Speichern von Änderungen oder Fortfahren zu einer anderen Seite.
  - **Erfolg**: Hervorragend geeignet, um den erfolgreichen Abschluss eines Elements in einer Anwendung zu visualisieren, wie das Einreichen eines Formulars oder den Abschluss eines Anmeldevorgangs. Das Erfolgsthema kann programmatisch angewendet werden, nachdem eine erfolgreiche Aktion abgeschlossen wurde.
  - **Warnung**: Nützlich, um anzuzeigen, dass ein Benutzer dabei ist, eine potenziell riskante Aktion auszuführen, wie das Navigieren von einer Seite mit nicht gespeicherten Änderungen. Diese Aktionen sind oft weniger bedeutend als solche, die das Gefährlich-Stil verwenden.
  - **Grau**: Gut für subtile Aktionen, wie geringfügige Einstellungen oder Aktionen, die eher ergänzend zu einer Seite sind und nicht Teil der Hauptfunktionalität.
  - **Info**: Gut, um den Benutzern zusätzliche klärende Informationen bereitzustellen.

Unten sind Beispielbuttons mit jedem der unterstützten Themen aufgeführt: <br/>

<ComponentDemo 
path='/webforj/buttonthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonThemesView.java'
cssURL='/css/button/buttonThemes.css'
height='175px'
/>

### Größen {#expanses}
Die folgenden <JavadocLink type="foundation" location="com/webforj/component/Expanse">Größenwerte</JavadocLink> ermöglichen ein schnelles Styling, ohne CSS zu verwenden. Dies ermöglicht die Anpassung der Dimensionen des Buttons, ohne dass diese explizit über Styling festgelegt werden müssen. Neben der Vereinfachung des Stylings hilft es auch, eine Einheitlichkeit in Ihrer Anwendung zu schaffen und aufrechtzuerhalten. Die Standardgröße des `Button` ist `Expanse.MEDIUM`.

Verschiedene Größen sind oft für unterschiedliche Verwendungen geeignet:
  - **Größere** Größenwerte sind für Buttons geeignet, die Aufmerksamkeit erregen, Funktionalität betonen oder für die Kernfunktionalität einer Anwendung oder Seite von wesentlicher Bedeutung sind.
  - **Mittlere** Buttons, die Standardgröße, sollten die Standardgröße von Buttons sein. Die Funktionen dieser Buttons sollten weder kritischer noch weniger kritisch sein als vergleichbare Komponenten.
  - **Kleinere** Größenwerte sollten für Buttons verwendet werden, die keine wesentlichen Funktionen in der Anwendung haben und eine ergänzende oder utilitaristische Rolle spielen, anstatt eine wichtige Rolle in der Benutzerinteraktion zu übernehmen. Dazu gehören `Button`-Komponenten, die nur mit Symbolen für utilitaristische Zwecke verwendet werden.

Unten sind die verschiedenen Größen aufgeführt, die für die `Button`-Komponente unterstützt werden: <br/>

<ComponentDemo 
path='/webforj/buttonexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java'
height='200px'
/>

<TableBuilder name="Button" />

## Best Practices {#best-practices}

Um ein optimales Benutzererlebnis mit der `Button`-Komponente zu gewährleisten, beachten Sie die folgenden Best Practices:

1. **Eindeutiger Text**: Verwenden Sie klaren und präzisen Text für den Text innerhalb Ihrer `Button`-Komponente, um klar anzuzeigen, wofür sie verwendet wird.

2. **Angemessenes visuelles Styling**: Berücksichtigen Sie das visuelle Styling und das Thema des `Button`, um die Konsistenz mit dem Design Ihrer Anwendung sicherzustellen. Zum Beispiel:
  > - Ein "Abbrechen"-`Button` sollte mit dem entsprechenden Thema oder CSS-Style versehen sein, damit die Benutzer sicher sind, dass sie eine Aktion abbrechen wollen.
  > - Ein "Bestätigen"-`Button` hätte ein anderes Styling als ein "Abbrechen"-Button, würde jedoch ebenfalls hervorstechen, damit die Benutzer wissen, dass dies eine besondere Aktion ist.

3. **Effiziente Ereignisbehandlung**: Behandeln Sie die `Button`-Ereignisse effizient und bieten Sie den Benutzern angemessenes Feedback. Verweisen Sie auf [Ereignisse](../building-ui/events), um das effiziente Hinzufügen von Ereignissen zu überprüfen.

4. **Testen und Barrierefreiheit**: Testen Sie das Verhalten des Buttons in verschiedenen Szenarien, wie z. B. wenn er deaktiviert ist oder den Fokus erhält, um ein reibungsloses Benutzererlebnis zu gewährleisten. Befolgen Sie die Richtlinien zur Barrierefreiheit, um sicherzustellen, dass der `Button` für alle Benutzer, einschließlich derer, die auf unterstützende Technologien angewiesen sind, nutzbar ist.
