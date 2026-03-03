---
title: Button
sidebar_position: 15
_i18n_hash: 6c3425f6d7138e710c5222d2baf84644
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

Ein `Button` ist ein klickbares Element, das eine Aktion auslöst, wenn es gedrückt wird. Er kann Text, Symbole oder eine Kombination aus beidem anzeigen. Buttons unterstützen mehrere visuelle Themen und Größen und können deaktiviert werden, um eine Interaktion während lang laufender Operationen oder wenn bestimmte Bedingungen nicht erfüllt sind, zu verhindern.

<!-- INTRO_END -->

## Anwendungen {#usages}

Die `Button`-Klasse ist eine vielseitige Komponente, die häufig in verschiedenen Situationen verwendet wird, in denen Benutzerinteraktionen und -aktionen ausgelöst werden müssen. Hier sind einige typische Szenarien, in denen Sie in Ihrer Anwendung einen Button benötigen könnten:

1. **Formularübermittlung**: Buttons werden häufig verwendet, um Formularanmeldedaten zu übermitteln. Zum Beispiel können Sie in einer Anwendung verwenden:

  > - Ein "Absenden"-Button, um Daten an den Server zu senden
  > - Ein "Löschen"-Button, um alle bereits im Formular vorhandenen Informationen zu entfernen

2. **Benutzeraktionen**: Buttons werden verwendet, um Benutzern zu ermöglichen, spezifische Aktionen innerhalb der Anwendung durchzuführen. Beispielsweise können Sie einen Button mit der Bezeichnung haben:

  > - "Löschen", um die Löschung eines ausgewählten Elements zu initiieren
  > - "Speichern", um Änderungen an einem Dokument oder einer Seite zu speichern.

3. **Bestätigungsdialoge**: Buttons sind häufig in [`Dialog`](../components/dialog)-Komponenten enthalten, die für verschiedene Zwecke erstellt wurden, um den Benutzern Optionen anzubieten, eine Aktion zu bestätigen oder abzubrechen oder eine andere Funktionalität, die in dem von Ihnen verwendeten [`Dialog`](../components/dialog) integriert ist.

4. **Interaktionsauslöser**: Buttons können als Auslöser für Interaktionen oder Ereignisse innerhalb der Anwendung dienen. Durch Klicken auf einen Button können Benutzer komplexe Aktionen einleiten, Animationen auslösen, Inhalte aktualisieren oder die Anzeige aktualisieren.

5. **Navigation**: Buttons können für Navigationszwecke verwendet werden, z. B. um zwischen verschiedenen Abschnitten oder Seiten innerhalb einer Anwendung zu wechseln. Navigationsbuttons könnten Folgendes umfassen:

  > - "Weiter" - Leitet den Benutzer zur nächsten Seite oder zum nächsten Abschnitt der aktuellen Anwendung oder Seite.
  > - "Zurück" - Bringt den Benutzer zur vorherigen Seite der Anwendung oder zum Abschnitt, in dem er sich befindet.
  > - "Zurück" Bringt den Benutzer zum ersten Teil der Anwendung oder Seite, in der er sich befindet.

Das folgende Beispiel zeigt Buttons, die für die Formularübermittlung und das Löschen von Eingaben verwendet werden:

<ComponentDemo 
path='/webforj/button?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonView.java'
height='300px'
/>

## Hinzufügen von Symbolen zu Buttons <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

Das Hinzufügen eines Symbols zu einem Button kann das Design Ihrer App erheblich verbessern, da es den Benutzern ermöglicht, schnell umsetzbare Elemente auf dem Bildschirm zu identifizieren. Die [`Icon`](./icon.md)-Komponente bietet eine große Auswahl an Symbolen zur Auswahl.

Durch die Nutzung der Methoden `setPrefixComponent()` und `setSuffixComponent()` haben Sie die Flexibilität, zu bestimmen, ob ein `Icon` vor oder nach dem Text auf einem Button erscheinen soll. Alternativ kann die Methode `setIcon()` verwendet werden, um ein `Icon` nach dem Text, aber vor dem `suffix`-Slot des Buttons hinzuzufügen.

<!-- Fügen Sie dies wieder hinzu, sobald das Icon zusammengeführt wurde -->
<!-- Weitere Informationen zur Konfiguration und Anpassung von Icons finden Sie auf der Seite [Icon-Komponente](../components/icon). -->

:::tip
Standardmäßig erbt ein `Icon` das Thema und die Ausdehnung des Buttons.
:::

Im Folgenden sind Beispiele für Buttons mit Text links und rechts sowie ein Button mit nur einem Symbol aufgeführt:

<ComponentDemo 
path='/webforj/buttonicon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonIconView.java'
height="200px"
/>

### Namen {#names}

Die `Button`-Komponente verwendet Namen, die für die Barrierefreiheit verwendet werden. Wenn kein Name ausdrücklich festgelegt ist, wird die Bezeichnung des `Button` stattdessen verwendet. Einige Icons verfügen jedoch nicht über Bezeichnungen und zeigen nur nicht-textliche Elemente wie Symbole an. In diesem Fall ist es ratsam, die Methode `setName()` zu verwenden, um sicherzustellen, dass die erstellte `Button`-Komponente den Barrierefreiheitsstandards entspricht.

## Deaktivieren eines Buttons {#disabling-a-button}

Button-Komponenten können, wie viele andere auch, deaktiviert werden, um dem Benutzer zu signalisieren, dass eine bestimmte Aktion noch nicht oder nicht mehr verfügbar ist. Ein deaktivierter Button verringert die Opazität des Buttons und ist für alle Button-Themen und -Bereiche verfügbar.

<ComponentDemo 
path='/webforj/buttondisable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonDisableView.java'
/>

<br />

Ein Button kann jederzeit im Code deaktiviert werden, indem die Funktion <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink> verwendet wird. Zu mehr Komfort kann ein Button auch deaktiviert werden, wenn er geklickt wird, indem die integrierte <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink>-Funktion verwendet wird.

In einigen Anwendungen löst das Klicken auf einen Button eine lang laufende Aktion aus. In den meisten Fällen möchte die Anwendung möglicherweise sicherstellen, dass nur ein Klick verarbeitet wird. Dies kann ein Problem in Umgebungen mit hoher Latenz sein, wenn der Benutzer den Button mehrmals klickt, bevor die Anwendung die Möglichkeit hat, mit der Verarbeitung der resultierenden Aktion zu beginnen.

:::tip
Das Deaktivieren beim Klicken hilft nicht nur, die Verarbeitung von Aktionen zu optimieren, sondern verhindert auch, dass der Entwickler dieses Verhalten selbst implementieren muss, da diese Methode optimiert wurde, um die Kommunikationsrunden zu reduzieren.
:::

## Styling {#styling}

### Themen {#themes}

`Button`-Komponenten verfügen über <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 verschiedene Themen </JavadocLink>, die für schnelles Styling ohne die Verwendung von CSS integriert sind. Diese Themen sind vordefinierte Stile, die auf Buttons angewendet werden können, um ihr Erscheinungsbild und ihre visuelle Darstellung zu ändern. Sie bieten eine schnelle und konsistente Möglichkeit, das Aussehen von Buttons in einer Anwendung anzupassen.

Während es viele Anwendungsfälle für jedes der verschiedenen Themen gibt, sind einige Beispiele:

  - **Gefahr**: Am besten für Aktionen mit schwerwiegenden Konsequenzen geeignet, wie das Löschen ausgefüllter Informationen oder das dauerhafte Löschen eines Kontos / von Daten.
  - **Standard**: Geeignet für Aktionen innerhalb einer Anwendung, die keine besondere Aufmerksamkeit erfordern und allgemein sind, wie das Umschalten einer Einstellung.
  - **Primär**: Geeignet als "Call-to-Action" auf einer Seite, wie sich anmelden, Änderungen speichern oder zu einer anderen Seite fortfahren.
  - **Erfolg**: Ausgezeichnet zur Visualisierung des erfolgreichen Abschlusses eines Elements in einer Anwendung, wie z. B. der Einreichung eines Formulars oder dem Abschluss eines Anmeldevorgangs. Das Erfolgsthema kann programmatisch angewendet werden, sobald eine erfolgreiche Aktion abgeschlossen ist.
  - **Warnung**: Nützlich, um anzuzeigen, dass ein Benutzer kurz davor ist, eine potenziell riskante Aktion auszuführen, z. B. das Verlassen einer Seite mit nicht gespeicherten Änderungen. Diese Aktionen sind oft weniger wirkungsvoll als die, die das Gefahren-Thema verwenden würden.
  - **Grau**: Gut für subtile Aktionen, wie geringfügige Einstellungen oder Aktionen, die eher ergänzend zu einer Seite sind und nicht Teil der Hauptfunktionalität sind.
  - **Info**: Gut, um zusätzliche klärende Informationen einem Benutzer bereitzustellen.

Unten sind Beispielbuttons mit jedem der unterstützten Themen aufgeführt: <br/>

<ComponentDemo 
path='/webforj/buttonthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonThemesView.java'
cssURL='/css/button/buttonThemes.css'
height='175px'
/>

### Ausdehnungen {#expanses}
Die folgenden <JavadocLink type="foundation" location="com/webforj/component/Expanse">Expanses-Werte </JavadocLink> ermöglichen eine schnelle Anpassung des Stylings ohne Verwendung von CSS. Dies ermöglicht die Manipulation der Abmessungen des Buttons, ohne dass diese explizit mit Styles festgelegt werden müssen. Außerdem hilft es, eine Einheitlichkeit in Ihrer Anwendung zu schaffen und aufrechtzuerhalten. Die Standardausdehnung des `Button` ist `Expanse.MEDIUM`.

Verschiedene Größen sind oft für unterschiedliche Verwendungen geeignet:
  - **Größere** Expanse-Werte eignen sich für Buttons, die Aufmerksamkeit erregen, Funktionalität betonen oder für die Kernfunktionalität einer Anwendung oder Seite von wesentlicher Bedeutung sind.
  - **Mittlere** Expanse-Buttons, die Standardgröße, sollten als "Standardgröße" verwendet werden, wenn das Verhalten eines Buttons nicht mehr oder weniger wichtig ist als bei anderen ähnlichen Komponenten.
  - **Kleinere** Expanse-Werte sollten für Buttons verwendet werden, die keine wesentlichen Verhaltensweisen in der Anwendung haben und eine eher ergänzende oder utilitaristische Rolle spielen, anstatt eine wichtige Rolle bei der Benutzerinteraktion zu spielen. Dies umfasst `Button`-Komponenten, die nur mit Symbolen für utilitaristische Zwecke verwendet werden.

Im Folgenden sind die verschiedenen unterstützten Expanse für die `Button`-Komponente aufgeführt: <br/>

<ComponentDemo 
path='/webforj/buttonexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java'
height='200px'
/>

<TableBuilder name="Button" />

## Best Practices {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der `Button`-Komponente sicherzustellen, sollten die folgenden Best Practices berücksichtigt werden:

1. **Eindeutiger Text**: Verwenden Sie klaren und prägnanten Text innerhalb Ihrer `Button`-Komponente, um einen klaren Hinweis auf ihren Zweck zu geben.

2. **Angemessene visuelle Gestaltung**: Berücksichtigen Sie das visuelle Styling und das Thema des `Button`, um sicherzustellen, dass es mit dem Design Ihrer Anwendung übereinstimmt. Zum Beispiel:
  > - Ein "Abbrechen"-`Button` sollte mit dem entsprechenden Thema oder CSS-Styling gestaltet werden, um sicherzustellen, dass die Benutzer sicher sind, dass sie eine Aktion abbrechen möchten.
  > - Ein "Bestätigen"-`Button` sollte sich von einem "Abbrechen"-Button unterscheiden, aber ebenfalls herausstechen, um sicherzustellen, dass die Benutzer wissen, dass dies eine besondere Aktion ist.

3. **Effiziente Ereignisbehandlung**: Behandeln Sie `Button`-Ereignisse effizient und bieten Sie den Benutzern angemessenes Feedback. Verweisen Sie auf [Ereignisse](../building-ui/events), um effiziente Ereignisverhaltensweisen zu überprüfen.

4. **Testen und Barrierefreiheit**: Testen Sie das Verhalten des Buttons in verschiedenen Szenarien, wie z. B. wenn er deaktiviert ist oder den Fokus erhält, um ein reibungsloses Benutzererlebnis zu gewährleisten.
Befolgen Sie die Richtlinien zur Barrierefreiheit, um den `Button` für alle Benutzer, einschließlich derjenigen, die auf unterstützende Technologien angewiesen sind, nutzbar zu machen.
