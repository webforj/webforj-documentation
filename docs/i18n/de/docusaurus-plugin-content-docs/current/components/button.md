---
title: Button
sidebar_position: 15
_i18n_hash: 4f84dd4c618dafe32cbeb47c7dcbbe36
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

Ein `Button` ist ein klickbares Element, das eine Aktion auslöst, wenn er gedrückt wird. Er kann Text, Symbole oder eine Kombination aus beidem anzeigen. Schaltflächen unterstützen mehrere visuelle Themen und Größen und können deaktiviert werden, um die Interaktion während langlaufender Vorgänge oder wenn bestimmte Bedingungen nicht erfüllt sind, zu verhindern.

<!-- INTRO_END -->

## Anwendungen {#usages}

Die `Button`-Klasse ist eine vielseitige Komponente, die häufig in verschiedenen Situationen verwendet wird, in denen Benutzerinteraktionen und Aktionen ausgelöst werden müssen. Hier sind einige typische Szenarien, in denen Sie möglicherweise eine Schaltfläche in Ihrer Anwendung benötigen:

1. **Formularübermittlung**: Schaltflächen werden oft verwendet, um Formulardaten zu übermitteln. Beispielsweise können Sie in einer Anwendung:

  > - Eine "Übermitteln" Schaltfläche verwenden, um Daten an den Server zu senden
  > - Eine "Löschen" Schaltfläche verwenden, um alle bereits im Formular eingegebenen Informationen zu entfernen

2. **Benutzeraktionen**: Schaltflächen werden verwendet, um dem Benutzer zu ermöglichen, spezifische Aktionen innerhalb der Anwendung auszuführen. Zum Beispiel können Sie eine Schaltfläche mit der Bezeichnung:

  > - "Löschen" haben, um die Löschung eines ausgewählten Elements einzuleiten
  > - "Speichern" verwenden, um Änderungen, die an einem Dokument oder einer Seite vorgenommen wurden, zu speichern.

3. **Bestätigungsdialoge**: Schaltflächen sind oft Bestandteil von [`Dialog`](../components/dialog) Komponenten, die für verschiedene Zwecke erstellt wurden, um den Benutzern Optionen zum Bestätigen oder Abbrechen einer Aktion oder anderer Funktionen zu bieten, die in den verwendeten [`Dialog`](../components/dialog) integriert sind.

4. **Interaktionsauslöser**: Schaltflächen können als Auslöser für Interaktionen oder Ereignisse innerhalb der Anwendung dienen. Durch Klicken auf eine Schaltfläche können Benutzer komplexe Aktionen initiieren oder Animationen auslösen, Inhalte aktualisieren oder die Anzeige ändern.

5. **Navigation**: Schaltflächen können zu Navigationszwecken verwendet werden, zum Beispiel um zwischen verschiedenen Abschnitten oder Seiten innerhalb einer Anwendung zu wechseln. Navigationsschaltflächen könnten Folgendes umfassen:

  > - "Weiter" - Bringt den Benutzer zur nächsten Seite oder zum nächsten Abschnitt der aktuellen Anwendung oder Seite.
  > - "Zurück" - Bringt den Benutzer zur vorherigen Seite der Anwendung oder zum Abschnitt zurück, in dem er sich befindet.
  > - "Zurück" - Bringt den Benutzer zum ersten Teil der Anwendung oder Seite zurück, auf der er sich befindet.

Das folgende Beispiel zeigt Schaltflächen, die für das Übermitteln von Formularen und zum Löschen von Eingaben verwendet werden:

<ComponentDemo
path='/webforj/button'
files={['src/main/java/com/webforj/samples/views/button/ButtonView.java']}
height='300px'
/>

## Hinzufügen von Symbolen zu Schaltflächen <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

Das Einfügen eines Symbols in eine Schaltfläche kann das Design Ihrer App erheblich verbessern und es den Benutzern ermöglichen, actionable Elemente auf dem Bildschirm schnell zu identifizieren. Die [`Icon`](./icon.md) Komponente bietet eine große Auswahl an Symbolen zur Auswahl.

Durch die Verwendung der Methoden `setPrefixComponent()` und `setSuffixComponent()` haben Sie die Flexibilität zu bestimmen, ob ein `Icon` vor oder nach dem Text auf einer Schaltfläche erscheinen sollte. Alternativ kann die Methode `setIcon()` verwendet werden, um ein `Icon` nach dem Text, jedoch vor dem `suffix` Slot der Schaltfläche hinzuzufügen.

<!-- Add this back in once Icon has been merged -->
<!-- Refer to the [Icon component](../components/icon) page for more information on configuring and customizing icons. -->

:::tip
Standardmäßig erbt ein `Icon` das Thema und die Ausdehnung der Schaltfläche.
:::

Unten sehen Sie Beispiele für Schaltflächen mit Text links und rechts sowie eine Schaltfläche mit nur einem Symbol:

<ComponentDemo
path='/webforj/buttonicon'
files={['src/main/java/com/webforj/samples/views/button/ButtonIconView.java']}
height='200px'
/>

### Namen {#names}

Die `Button`-Komponente nutzt Namensgebungen, die für die Barrierefreiheit verwendet werden. Wenn ein Name nicht ausdrücklich festgelegt ist, wird stattdessen die Bezeichnung der `Button` verwendet. Einige Symbole haben jedoch keine Beschriftungen und zeigen nur nicht-textliche Elemente an, wie z. B. Symbole. In diesem Fall ist es ratsam, die Methode `setName()` zu verwenden, um sicherzustellen, dass die erstellte `Button`-Komponente den Standards für die Barrierefreiheit entspricht.

## Deaktivieren einer Schaltfläche {#disabling-a-button}

Schaltflächenkomponenten können wie viele andere deaktiviert werden, um dem Benutzer zu signalisieren, dass eine bestimmte Aktion noch nicht oder nicht mehr verfügbar ist. Eine deaktivierte Schaltfläche verringert die Opazität der Schaltfläche und ist für alle Schaltflächenthemen und -ausdehnungen verfügbar.

<ComponentDemo
path='/webforj/buttondisable'
files={['src/main/java/com/webforj/samples/views/button/ButtonDisableView.java']}
/>

Eine Schaltfläche kann jederzeit im Code deaktiviert werden, indem die Funktion <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink> verwendet wird. Zur Erleichterung kann eine Schaltfläche auch beim Klicken mit der eingebauten <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink> Funktion deaktiviert werden.

In einigen Fällen löst ein Klick auf eine Schaltfläche eine lang laufende Aktion aus. Das Deaktivieren der Schaltfläche, bis Ihre App die Aktion verarbeitet hat, verhindert, dass der Benutzer die Schaltfläche mehrmals anklickt, insbesondere in Umgebungen mit hoher Latenz.

:::tip
Das Deaktivieren beim Klicken hilft nicht nur, die Verarbeitung von Aktionen zu optimieren, sondern verhindert auch, dass der Entwickler dieses Verhalten selbst implementieren muss, da diese Methode optimiert wurde, um die Kommunikationskosten zu reduzieren.
:::

## Stilgestaltung {#styling}

### Themen {#themes}

`Button`-Komponenten verfügen über <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 verschiedene Themen</JavadocLink>, die für eine schnelle Gestaltung ohne die Verwendung von CSS integriert sind. Diese Themen sind vordefinierte Stile, die auf Schaltflächen angewendet werden können, um deren Aussehen und visuelle Präsentation zu ändern. Sie bieten eine schnelle und konsistente Möglichkeit, das Erscheinungsbild von Schaltflächen in einer Anwendung anzupassen.

Obwohl es viele Anwendungsfälle für jedes der verschiedenen Themen gibt, sind einige Beispiele für die Verwendung:

  - **Gefahr**: Am besten geeignet für Aktionen mit schwerwiegenden Konsequenzen, wie das Löschen ausgefüllter Informationen oder das dauerhafte Löschen eines Kontos/Daten.
  - **Standard**: Geeignet für Aktionen in einer Anwendung, die keine besondere Aufmerksamkeit erfordern und generisch sind, wie das Umstellen eines Setzens.
  - **Primär**: Geeignet als Haupt-"Handlungsaufruf" auf einer Seite, wie sich anzumelden, Änderungen zu speichern oder zu einer anderen Seite fortzufahren.
  - **Erfolg**: Hervorragend geeignet, um den erfolgreichen Abschluss eines Elements in einer Anwendung zu visualisieren, wie das Einreichen eines Formulars oder den Abschluss eines Anmeldevorgangs. Das Erfolgsthema kann programmatisch angewendet werden, sobald eine erfolgreiche Aktion abgeschlossen ist.
  - **Warnung**: Nützlich, um anzuzeigen, dass der Benutzer eine potenziell riskante Aktion durchführen wird, wie das Navigieren von einer Seite mit nicht gespeicherten Änderungen weg. Diese Aktionen sind oft weniger bedeutsam als solche, die das Gefahrenthema verwenden würden.
  - **Grau**: Gut für subtile Aktionen, wie kleine Einstellungen oder Aktionen, die mehr ergänzend zu einer Seite sind und nicht Teil der Hauptfunktionalität.
  - **Info**: Gut geeignet, um zusätzlichen klärenden Informationen für einen Benutzer bereitzustellen.

Nachfolgend sind Beispielschaltflächen mit jeweils angewendeten Themen aufgeführt: <br/>

<ComponentDemo
path='/webforj/buttonthemes'
files={['src/main/java/com/webforj/samples/views/button/ButtonThemesView.java']}
height='175px'
/>

### Ausdehnungen {#expanses}

Die folgenden <JavadocLink type="foundation" location="com/webforj/component/Expanse">Ausdehnungswerte</JavadocLink> ermöglichen eine schnelle Gestaltung ohne die Verwendung von CSS. Dies ermöglicht die Manipulation der Abmessungen der Schaltfläche, ohne dass diese explizit mit einem Styling versehen werden muss. Neben der Vereinfachung des Stylings hilft es auch, eine Einheitlichkeit in Ihrer Anwendung zu schaffen und aufrechtzuerhalten. Die Standardausdehnung der `Button` ist `Expanse.MEDIUM`.

Verschiedene Größen sind oft für unterschiedliche Verwendungen geeignet:
  - **Größere** Ausdehnungswerte eignen sich für Schaltflächen, die Aufmerksamkeit erregen, Funktionalität betonen oder für die Hauptfunktionalität einer Anwendung oder Seite von wesentlicher Bedeutung sind.
  - **Mittlere** Ausdehnungs-Schaltflächen, die Standardgröße, sollten die Standardgröße von Schaltflächen sein. Die Funktionen dieser Schaltflächen sollten weder wichtiger noch weniger wichtig sein als bei ähnlichen Komponenten.
  - **Kleinere** Ausdehnungswerte sollten für Schaltflächen verwendet werden, die nicht über wesentliche Verhaltensweisen in der Anwendung verfügen und eine eher ergänzende oder utilitaristische Rolle einnehmen, anstatt eine wichtige Rolle in der Benutzerinteraktion zu spielen. Dies umfasst `Button`-Komponenten, die nur mit Symbolen für utilitaristische Zwecke verwendet werden.

Im Folgenden sind die verschiedenen für die `Button`-Komponente unterstützten Ausdehnungen aufgeführt: <br/>

<ComponentDemo
path='/webforj/buttonexpanses'
files={['src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java']}
height='200px'
/>

<TableBuilder name="Button" />

## Beste Praktiken {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der `Button`-Komponente zu gewährleisten, ziehen Sie die folgenden besten Praktiken in Betracht:

1. **Angemessener Text**: Verwenden Sie klaren und präzisen Text für den Text innerhalb Ihrer `Button`-Komponente, um einen klaren Hinweis auf deren Zweck zu geben.

2. **Angemessene visuelle Gestaltung**: Berücksichtigen Sie die visuelle Gestaltung und das Thema der `Button`, um die Konsistenz mit dem Design Ihrer Anwendung sicherzustellen. Zum Beispiel:
  > - Eine "Abbrechen" `Button`-Komponente sollte mit dem entsprechenden Thema oder CSS-Styling gestaltet sein, um sicherzustellen, dass die Benutzer sicher sind, dass sie eine Aktion abbrechen möchten
  > - Eine "Bestätigen" `Button` hätte eine andere Gestaltung als eine "Abbrechen" Schaltfläche, würde jedoch ebenfalls hervorstechen, um sicherzustellen, dass die Benutzer wissen, dass dies eine besondere Aktion ist.

3. **Effiziente Ereignisbehandlung**: Behandeln Sie `Button`-Ereignisse effizient und bieten Sie den Benutzern angemessenes Feedback. Konsultieren Sie [Ereignisse](../building-ui/events), um effiziente Verhaltensweisen beim Hinzufügen von Ereignissen zu überprüfen.

4. **Testen und Barrierefreiheit**: Testen Sie das Verhalten der Schaltfläche in verschiedenen Szenarien, z. B. wenn sie deaktiviert ist oder den Fokus erhält, um ein reibungsloses Benutzererlebnis zu gewährleisten. Befolgen Sie die Richtlinien zur Barrierefreiheit, um die `Button` für alle Benutzer, einschließlich derjenigen, die auf unterstützende Technologien angewiesen sind, nutzbar zu machen.
