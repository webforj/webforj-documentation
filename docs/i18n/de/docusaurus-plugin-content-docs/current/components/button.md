---
title: Button
sidebar_position: 15
_i18n_hash: 186724659f1f66cdb6f85e7a1628def8
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

Eine `Button`-Komponente ist ein grundlegendes Benutzerschnittstellenelement, das in der Anwendungsentwicklung verwendet wird, um interaktive Elemente zu erstellen, die Aktionen oder Ereignisse auslösen, wenn sie angeklickt oder aktiviert werden. Sie dient als anklickbares Element, mit dem Benutzer interagieren können, um verschiedene Aktionen innerhalb einer Anwendung oder Webseite auszuführen.

Der Hauptzweck der `Button`-Komponente besteht darin, den Benutzern einen klaren und intuitiven Handlungsaufruf zu bieten, der sie anleitet, spezifische Aufgaben wie das Einreichen eines Formulars, das Navigieren zu einer anderen Seite, das Auslösen einer Funktion oder das Initiieren eines Prozesses auszuführen. Schaltflächen sind unerlässlich, um die Benutzerinteraktionen zu verbessern, die Zugänglichkeit zu fördern und ein ansprechenderes Benutzererlebnis zu schaffen.

<ComponentDemo 
path='/webforj/button?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonView.java'
height='300px'
/>

<!-- tabs={['ButtonDemo.java', 'demo_styles.css']} -->

## Verwendungen {#usages}

Die `Button`-Klasse ist eine vielseitige Komponente, die häufig in verschiedenen Situationen verwendet wird, in denen Benutzerinteraktionen und Aktionen ausgelöst werden müssen. Hier sind einige typische Szenarien, in denen Sie möglicherweise einen Button in Ihrer Anwendung benötigen:

1. **Formularübermittlung**: Schaltflächen werden häufig verwendet, um Formulardaten zu übermitteln. Zum Beispiel können Sie in einer Anwendung verwenden:

  > - Eine "Absenden"-Schaltfläche, um Daten an den Server zu senden
  > - Eine "Löschen"-Schaltfläche, um bereits im Formular vorhandene Informationen zu entfernen

2. **Benutzeraktionen**: Schaltflächen werden verwendet, um es Benutzern zu ermöglichen, spezifische Aktionen innerhalb der Anwendung auszuführen. Zum Beispiel können Sie eine Schaltfläche mit der Bezeichnung haben:

  > - "Löschen", um die Löschung eines ausgewählten Elements einzuleiten
  > - "Speichern", um Änderungen, die an einem Dokument oder einer Seite vorgenommen wurden, zu speichern.

3. **Bestätigungsdialoge**: Schaltflächen werden häufig in [`Dialog`](../components/dialog)-Komponenten integriert, die für verschiedene Zwecke entwickelt wurden, um den Benutzern Optionen zu bieten, um eine Aktion zu bestätigen oder abzubrechen oder eine andere Funktionalität, die in dem verwendeten [`Dialog`](../components/dialog) integriert ist.

4. **Interaktionsauslöser**: Schaltflächen können als Auslöser für Interaktionen oder Ereignisse innerhalb der Anwendung dienen. Durch Klicken auf eine Schaltfläche können Benutzer komplexe Aktionen initiieren oder Animationen auslösen, Inhalte aktualisieren oder die Anzeige ändern.

5. **Navigation**: Schaltflächen können zu Navigationszwecken verwendet werden, z. B. um zwischen verschiedenen Abschnitten oder Seiten innerhalb einer Anwendung zu wechseln. Navigationsschaltflächen könnten Folgendes beinhalten:

  > - "Weiter" - Leitet den Benutzer zur nächsten Seite oder zum nächsten Abschnitt der aktuellen Anwendung oder Seite.
  > - "Zurück" - Bringt den Benutzer zur vorherigen Seite der Anwendung oder zum Abschnitt, in dem er sich befindet.

## Hinzufügen von Symbolen zu Schaltflächen <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

Das Einfügen eines Symbols in eine Schaltfläche kann das Design Ihrer App erheblich verbessern und es den Benutzern ermöglichen, umgehend umsetzbare Elemente auf dem Bildschirm zu identifizieren. Die [`Icon`](./icon.md)-Komponente bietet eine breite Auswahl an Icons zur Auswahl.

Durch die Verwendung der Methoden `setPrefixComponent()` und `setSuffixComponent()` haben Sie die Flexibilität zu bestimmen, ob ein `Icon` vor oder nach dem Text auf einer Schaltfläche angezeigt werden soll. Alternativ kann die Methode `setIcon()` verwendet werden, um ein `Icon` nach dem Text, aber vor dem `suffix`-Slot der Schaltfläche hinzuzufügen.

<!-- Add this back in once Icon has been merged -->
<!-- Refer to the [Icon component](../components/icon) page for more information on configuring and customizing icons. -->

:::tip
Standardmäßig erbt ein `Icon` das Thema und die Größe der Schaltfläche.
:::

Im Folgenden sind Beispiele für Schaltflächen mit Text links und rechts sowie eine Schaltfläche mit nur einem Symbol aufgeführt:

<ComponentDemo 
path='/webforj/buttonicon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonIconView.java'
height="200px"
/>

### Namen {#names}

Die `Button`-Komponente verwendet Namen, die für die Zugänglichkeit genutzt werden. Wenn ein Name nicht explizit festgelegt ist, wird das Label der `Button` stattdessen verwendet. Einige Symbole haben jedoch keine Labels und zeigen nur Nicht-Text-Elemente, wie z. B. Icons, an. In diesem Fall ist es sinnvoll, die Methode `setName()` zu verwenden, um sicherzustellen, dass die erstellte `Button`-Komponente den Standards für die Zugänglichkeit entspricht.

## Deaktivieren einer Schaltfläche {#disabling-a-button}

Schaltflächenkomponenten, wie viele andere, können deaktiviert werden, um dem Benutzer mitzuteilen, dass eine bestimmte Aktion noch nicht oder nicht mehr verfügbar ist. Eine deaktivierte Schaltfläche verringert die Opazität der Schaltfläche und steht für alle Schaltflächenthemen und -größen zur Verfügung.

<ComponentDemo 
path='/webforj/buttondisable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonDisableView.java'
/>

<br />

Eine Schaltfläche kann jederzeit im Code deaktiviert werden, indem die Funktion <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink> verwendet wird. Für zusätzlichen Komfort kann eine Schaltfläche auch bei einem Klick deaktiviert werden, indem die integrierte <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink>-Funktion verwendet wird.

In einigen Anwendungen löst ein Klick auf eine Schaltfläche eine langwierige Aktion aus. In den meisten Fällen möchte die Anwendung möglicherweise sicherstellen, dass nur ein einzelner Klick verarbeitet wird. Dies kann in Umgebungen mit hoher Latenz ein Problem sein, wenn der Benutzer die Schaltfläche mehrfach klickt, bevor die Anwendung die Möglichkeit hatte, die daraus resultierende Aktion zu verarbeiten.

:::tip
Das Deaktivieren bei einem Klick hilft nicht nur, die Verarbeitung von Aktionen zu optimieren, sondern verhindert auch, dass der Entwickler dieses Verhalten selbst implementieren muss, da diese Methode optimiert wurde, um die Kommunikation zu reduzieren.
:::

## Styling {#styling}

### Themen {#themes}

`Button`-Komponenten sind mit <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 unterschiedlichen Themen</JavadocLink> ausgestattet, die eine schnelle Gestaltung ohne Verwendung von CSS ermöglichen. Diese Themen sind vordefinierte Stile, die auf Schaltflächen angewendet werden können, um ihr Aussehen und ihre visuelle Präsentation zu ändern. Sie bieten eine schnelle und konsistente Möglichkeit, das Aussehen von Schaltflächen in einer Anwendung anzupassen.

Obwohl es viele Anwendungsfälle für jedes der verschiedenen Themen gibt, sind einige Beispiele für die Verwendung:

  - **Gefahr**: Am besten geeignet für Aktionen mit schwerwiegenden Konsequenzen, wie das Löschen von ausgefüllten Informationen oder das permanente Löschen eines Kontos/Daten.
  - **Standard**: Geeignet für Aktionen in einer Anwendung, die keine besondere Aufmerksamkeit erfordern und allgemein sind, wie das Umschalten einer Einstellung.
  - **Primär**: Geeignet als Haupt-"Handlungsaufruf" auf einer Seite, wie z.B. sich anzumelden, Änderungen zu speichern oder zu einer anderen Seite fortzufahren.
  - **Erfolg**: Hervorragend geeignet zur Visualisierung des erfolgreichen Abschlusses eines Elements in einer Anwendung, wie z.B. das Einreichen eines Formulars oder den Abschluss eines Anmeldevorgangs. Das Erfolgsthema kann programmgesteuert angewendet werden, sobald eine erfolgreiche Aktion abgeschlossen wurde.
  - **Warnung**: Nützlich, um anzuzeigen, dass ein Benutzer eine potenziell riskante Aktion durchführen wird, z.B. das Navigieren von einer Seite mit nicht gespeicherten Änderungen. Diese Aktionen sind oft weniger belastend als die, die das Gefahr-Thema verwenden würden.
  - **Grau**: Gut für subtile Aktionen, wie z.B. kleinere Einstellungen oder Aktionen, die eher ergänzend zu einer Seite sind und nicht Teil der Hauptfunktionalität.
  - **Info**: Gut dazu geeignet, zusätzlichen klärenden Informationen für einen Benutzer bereitzustellen.

Nachfolgend sind Beispielschaltflächen mit jedem der unterstützten Themen angezeigt: <br/>

<ComponentDemo 
path='/webforj/buttonthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonThemesView.java'
cssURL='/css/button/buttonThemes.css'
height='175px'
/>

### Größen {#expanses}
Die folgenden <JavadocLink type="foundation" location="com/webforj/component/Expanse">Größenwerte</JavadocLink> ermöglichen eine schnelle Gestaltung ohne Verwendung von CSS. Dies ermöglicht die Manipulation der Abmessungen des Buttons, ohne dass diese explizit über Styling festgelegt werden müssen. Zusätzlich zur Vereinfachung des Stylings trägt es auch zur Schaffung und Aufrechterhaltung einer Einheitlichkeit in Ihrer Anwendung bei. Die Standardgröße des `Button` ist `Expanse.MEDIUM`.

Verschiedene Größen sind oft für unterschiedliche Verwendungen geeignet:
  - **Größere** Größenwerte sind geeignet für Schaltflächen, die Aufmerksamkeit erregen, Funktionalität betonen oder integraler Bestandteil der Hauptfunktionalität einer Anwendung oder Seite sind.
  - **Mittlere** Schaltflächen, die Standardgröße, sollten als "Standardgröße" verwendet werden, wenn das Verhalten einer Schaltfläche nicht mehr oder weniger wichtig ist als andere ähnliche Komponenten.
  - **Kleinere** Größenwerte sollten für Schaltflächen verwendet werden, die kein integrales Verhalten in der Anwendung haben und eine eher unterstützende oder utilitaristische Rolle spielen, anstatt eine wichtige Rolle in der Benutzerinteraktion zu spielen. Dies umfasst `Button`-Komponenten, die nur mit Icons für utilitaristische Zwecke verwendet werden.

Unten sind die verschiedenen unterstützten Größen für die `Button`-Komponente aufgeführt: <br/>

<ComponentDemo 
path='/webforj/buttonexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java'
height='200px'
/>

<TableBuilder name="Button" />

## Best Practices {#best-practices}

Um ein optimales Benutzererlebnis bei der Verwendung der `Button`-Komponente sicherzustellen, beachten Sie die folgenden Best Practices:

1. **Klare Texte**: Verwenden Sie klare und präzise Texte innerhalb Ihrer `Button`-Komponente, um den Zweck deutlich anzuzeigen.

2. **Angemessene visuelle Gestaltung**: Berücksichtigen Sie die visuelle Gestaltung und das Thema des `Button`, um Konsistenz mit dem Design Ihrer Anwendung sicherzustellen. Zum Beispiel:
  > - Eine "Abbrechen"-`Button`-Komponente sollte mit dem entsprechenden Thema oder CSS-Styling gestaltet werden, um sicherzustellen, dass Benutzer sicher sind, dass sie eine Aktion abbrechen möchten.
  > - Eine "Bestätigen"-`Button` hätte eine andere Gestaltung als eine "Abbrechen"-Schaltfläche, sollte aber ebenfalls hervorstechen, um sicherzustellen, dass Benutzer wissen, dass dies eine besondere Aktion ist.

3. **Effiziente Ereignisbehandlung**: Bearbeiten Sie `Button`-Ereignisse effizient und bieten Sie Benutzern angemessenes Feedback. Weitere Informationen finden Sie unter [Ereignisse](../building-ui/events), um effiziente Ereignisverhaltensweisen zu überprüfen.

4. **Testen und Zugänglichkeit**: Testen Sie das Verhalten des Buttons in verschiedenen Szenarien, z.B. wenn er deaktiviert ist oder Fokus erhält, um ein reibungsloses Benutzererlebnis sicherzustellen. Befolgen Sie die Richtlinien zur Zugänglichkeit, um den `Button` für alle Benutzer nutzbar zu machen, einschließlich derjenigen, die auf Hilfstechnologien angewiesen sind.
