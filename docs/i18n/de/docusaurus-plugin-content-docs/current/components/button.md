---
title: Button
sidebar_position: 15
description: >-
  Trigger click actions in webforJ with the Button component, including themes,
  expanses, prefix and suffix icons, and disabled state.
_i18n_hash: 31fa93b60126cba6b26198da5a5c15b5
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

Ein `Button` ist ein klickbares Element, das eine Aktion auslöst, wenn es gedrückt wird. Er kann Text, Icons oder eine Kombination aus beidem anzeigen. Buttons unterstützen mehrere visuelle Themen und Größen und können deaktiviert werden, um die Interaktion während langwieriger Operationen oder wenn bestimmte Bedingungen nicht erfüllt sind, zu verhindern.

<!-- INTRO_END -->

## Verwendungen {#usages}

Die `Button`-Klasse ist eine vielseitige Komponente, die häufig in verschiedenen Situationen verwendet wird, in denen Benutzerinteraktionen und Aktionen ausgelöst werden müssen. Hier sind einige typische Szenarien, in denen Sie möglicherweise einen Button in Ihrer Anwendung benötigen:

1. **Formularübermittlung**: Buttons werden oft verwendet, um Formulardaten zu übermitteln. Zum Beispiel können Sie in einer Anwendung verwenden:

  > - Einen "Absenden"-Button, um Daten an den Server zu senden
  > - Einen "Zurücksetzen"-Button, um Informationen, die bereits im Formular vorhanden sind, zu entfernen 

2. **Benutzeraktionen**: Buttons werden verwendet, um es Benutzern zu ermöglichen, spezifische Aktionen innerhalb der Anwendung auszuführen. Zum Beispiel können Sie einen Button mit der Bezeichnung haben:

  > - "Löschen", um die Löschung eines ausgewählten Elements einzuleiten
  > - "Speichern", um Änderungen an einem Dokument oder einer Seite zu speichern.

3. **Bestätigungsdialoge**: Buttons sind oft in [`Dialog`](../components/dialog)-Komponenten enthalten, die für verschiedene Zwecke erstellt wurden, um Benutzern Optionen zur Bestätigung oder zum Abbrechen einer Aktion oder jeder anderen Funktionalität zu bieten, die in dem von Ihnen verwendeten [`Dialog`](../components/dialog) integriert ist.

4. **Interaktionsauslöser**: Buttons können als Auslöser für Interaktionen oder Ereignisse innerhalb der Anwendung dienen. Durch Klicken auf einen Button können Benutzer komplexe Aktionen einleiten oder Animationen auslösen, Inhalte aktualisieren oder die Anzeige anpassen.

5. **Navigation**: Buttons können für Navigationszwecke verwendet werden, z. B. um zwischen verschiedenen Abschnitten oder Seiten innerhalb einer Anwendung zu wechseln. Navigationsbuttons könnten Folgendes enthalten:

  > - "Weiter" - Führt den Benutzer zur nächsten Seite oder zum nächsten Abschnitt der aktuellen Anwendung oder Seite.
  > - "Zurück" - Führt den Benutzer zur vorherigen Seite der Anwendung oder des Abschnitts zurück, in dem er sich befindet.
  > - "Zurück" - Führt den Benutzer zum ersten Teil der Anwendung oder Seite zurück, in der er sich befindet.

Das folgende Beispiel demonstriert Buttons, die für die Formularübermittlung und das Leeren von Eingaben verwendet werden:

<ComponentDemo
path='/webforj/button'
files={['src/main/java/com/webforj/samples/views/button/ButtonView.java']}
height='300px'
/>

## Hinzufügen von Icons zu Buttons <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

Das Einfügen eines Icons in einen Button kann das Design Ihrer App erheblich verbessern und es den Benutzern ermöglichen, schnell umsetzbare Elemente auf dem Bildschirm zu erkennen. Die [`Icon`](./icon.md)-Komponente bietet eine große Auswahl an Icons zur Auswahl.

Durch die Verwendung der Methoden `setPrefixComponent()` und `setSuffixComponent()` haben Sie die Flexibilität zu bestimmen, ob ein `Icon` vor oder nach dem Text auf einem Button erscheinen soll. Alternativ kann die Methode `setIcon()` verwendet werden, um ein `Icon` nach dem Text, aber vor dem `suffix`-Slot des Buttons hinzuzufügen.

<!-- Fügen Sie dies wieder hinzu, sobald das Icon zusammengeführt wurde -->
<!-- Informationen zur Konfiguration und Anpassung von Icons finden Sie auf der Seite [Icon-Komponente](../components/icon). -->

:::tip
Standardmäßig erbt ein `Icon` das Thema und die Größe des Buttons.
:::

Im Folgenden finden Sie Beispiele für Buttons mit Text links und rechts sowie einen Button mit nur einem Icon:

<ComponentDemo
path='/webforj/buttonicon'
files={['src/main/java/com/webforj/samples/views/button/ButtonIconView.java']}
height='200px'
/>

### Namen {#names}

Die `Button`-Komponente verwendet Namen, die für die Barrierefreiheit genutzt werden. Wenn ein Name nicht ausdrücklich festgelegt ist, wird stattdessen das Label des `Buttons` verwendet. Einige Icons haben jedoch keine Labels und zeigen nur nicht-textuelle Elemente wie Icons an. In diesem Fall ist es sinnvoll, die Methode `setName()` zu verwenden, um sicherzustellen, dass die erstellte `Button`-Komponente den Standards für die Barrierefreiheit entspricht.

## Deaktivieren eines Buttons {#disabling-a-button}

Button-Komponenten sowie viele andere können deaktiviert werden, um einem Benutzer zu signalisieren, dass eine bestimmte Aktion noch nicht oder nicht mehr verfügbar ist. Ein deaktivierter Button verringert die Opazität des Buttons und ist für alle Button-Themen und -Größen verfügbar.

<ComponentDemo
path='/webforj/buttondisable'
files={['src/main/java/com/webforj/samples/views/button/ButtonDisableView.java']}
/>

Das Deaktivieren eines Buttons kann jederzeit im Code über die <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink>-Funktion erfolgen. Zur zusätzlichen Bequemlichkeit kann ein Button auch bei einem Klick deaktiviert werden, indem die integrierte <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink>-Funktion verwendet wird.

In einigen Fällen löst ein Klick auf einen Button eine langwierige Aktion aus. Das Deaktivieren des Buttons, bis Ihre App die Aktion verarbeitet hat, verhindert, dass der Benutzer den Button mehrmals anklickt, insbesondere in Umgebungen mit hoher Latenz.

:::tip
Das Deaktivieren mit einem Klick hilft nicht nur dabei, die Verarbeitung von Aktionen zu optimieren, sondern verhindert auch, dass der Entwickler dieses Verhalten selbst implementieren muss, da diese Methode optimiert wurde, um die Kommunikationsrunden zu reduzieren.
:::

## Styling {#styling}

### Themen {#themes}

`Button`-Komponenten verfügen über <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 diskrete Themen</JavadocLink>, die für schnelles Styling ohne Einsatz von CSS integriert sind. Diese Themen sind vordefinierte Stile, die auf Buttons angewendet werden können, um deren Erscheinungsbild und visuelle Präsentation zu ändern. Sie bieten eine schnelle und konsistente Möglichkeit, das Aussehen von Buttons in einer Anwendung anzupassen.

Es gibt viele Anwendungsfälle für jedes der verschiedenen Themen, einige Beispiele sind:

  - **Gefahr**: Am besten geeignet für Aktionen mit schwerwiegenden Konsequenzen, wie das Löschen ausgefüllter Informationen oder das dauerhafte Löschen eines Kontos/Daten.
  - **Standard**: Geeignet für Aktionen innerhalb einer Anwendung, die keine besondere Aufmerksamkeit erfordern und allgemein sind, wie das Umschalten einer Einstellung.
  - **Primär**: Geeignet als Haupt-"Call-to-Action" auf einer Seite, wie die Anmeldung, das Speichern von Änderungen oder das Fortsetzen zur nächsten Seite.
  - **Erfolg**: Hervorragend zur visualisierung des erfolgreichen Abschlusses eines Elements in einer Anwendung, wie die Einreichung eines Formulars oder den Abschluss eines Anmeldevorgangs. Das Erfolgsthema kann programmgesteuert angewendet werden, sobald eine erfolgreiche Aktion abgeschlossen wurde.
  - **Warnung**: Nützlich, um anzuzeigen, dass ein Benutzer dabei ist, eine potenziell riskante Aktion durchzuführen, wie das Navigieren von einer Seite mit nicht gespeicherten Änderungen. Diese Aktionen sind oft weniger schwerwiegend als solche, die das Gefahr-Thema verwenden.
  - **Grau**: Gut für subtile Aktionen, wie geringfügige Einstellungen oder Aktionen, die ergänzend zu einer Seite sind und keinen Teil der Hauptfunktionalität darstellen.
  - **Info**: Gut, um einem Benutzer zusätzliche, klärende Informationen bereitzustellen.

Im Folgenden sind Beispielbuttons mit jedem der unterstützten Themen abgebildet: <br/>

<ComponentDemo
path='/webforj/buttonthemes'
files={['src/main/java/com/webforj/samples/views/button/ButtonThemesView.java']}
height='175px'
/>

### Größen {#expanses}
Die folgenden <JavadocLink type="foundation" location="com/webforj/component/Expanse">Expanse-Werte</JavadocLink> ermöglichen schnelles Styling ohne CSS. Dies ermöglicht die Manipulation der Abmessungen des Buttons, ohne sie explizit über ein Styling festlegen zu müssen. Neben der Vereinfachung des Stylings hilft es auch, eine Einheitlichkeit in Ihrer Anwendung zu schaffen und aufrechtzuerhalten. Die Standardgröße für den `Button` ist `Expanse.MEDIUM`.

Unterschiedliche Größen sind oft für unterschiedliche Verwendungen geeignet:
  - **Größere** Expansionswerte eignen sich für Buttons, die Aufmerksamkeit erregen, Funktionalität betonen oder für die Kernfunktionalität einer Anwendung oder Seite von wesentlicher Bedeutung sind.
  - **Mittlere** Expansionsbuttons, die Standardgröße, sollten die Standardgröße für Buttons sein. Die Funktionen dieser Buttons sollten weder wichtiger noch weniger wichtig sein als ähnliche Komponenten.
  - **Kleinere** Expansionswerte sollten für Buttons verwendet werden, die keine integralen Verhaltensweisen in der Anwendung aufweisen und eine eher ergänzende oder utilitaristische Rolle spielen, anstatt eine wichtige Rolle bei der Benutzerinteraktion zu spielen. Dies umfasst `Button`-Komponenten, die nur mit Icons für utilitaristische Zwecke verwendet werden.

Nachfolgend sind die verschiedenen unterstützten Expanswerte für die `Button`-Komponente aufgeführt: <br/>

<ComponentDemo
path='/webforj/buttonexpanses'
files={['src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java']}
height='200px'
/>

<TableBuilder name="Button" />

## Beste Praktiken {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der `Button`-Komponente sicherzustellen, beachten Sie die folgenden besten Praktiken:

1. **Eindeutiger Text**: Verwenden Sie klaren und prägnanten Text innerhalb Ihrer `Button`-Komponente, um eine klare Angabe über deren Zweck zu geben.

2. **Angemessene visuelle Gestaltung**: Berücksichtigen Sie die visuelle Gestaltung und das Thema des `Buttons`, um die Konsistenz mit dem Design Ihrer Anwendung sicherzustellen. Zum Beispiel:
  > - Eine "Abbrechen"-`Button`-Komponente sollte mit dem entsprechenden Thema oder CSS-Styling gestaltet werden, um sicherzustellen, dass die Benutzer sicher sind, dass sie eine Aktion abbrechen möchten.
  > - Ein "Bestätigen"-`Button` würde eine andere Gestaltung aufweisen als ein "Abbrechen"-Button, sollte jedoch ebenfalls hervorstechen, um sicherzustellen, dass die Benutzer wissen, dass dies eine spezielle Aktion ist.

3. **Effiziente Ereignisverarbeitung**: Verarbeiten Sie `Button`-Ereignisse effizient und geben Sie geeignetes Feedback an die Benutzer. Siehe [Ereignisse](../building-ui/events) für einen Überblick über effiziente Ereignisverhalten hinzuzufügen.

4. **Testen und Barrierefreiheit**: Testen Sie das Verhalten des Buttons in verschiedenen Szenarien, z. B. wenn er deaktiviert ist oder den Fokus erhält, um ein reibungsloses Benutzererlebnis zu gewährleisten. Beachten Sie die Richtlinien zur Barrierefreiheit, um sicherzustellen, dass der `Button` für alle Benutzer zugänglich ist, einschließlich solcher, die auf unterstützende Technologien angewiesen sind.
