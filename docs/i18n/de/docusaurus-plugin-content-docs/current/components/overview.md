---
title: UI Components
sidebar_position: 85
hide_table_of_contents: true
sidebar_class_name: has-new-content
hide_giscus_comments: true
_i18n_hash: 87c3a95cb124fa9d0468e7cfd5d9d8ac
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<Head>
  <title>UI-Komponenten | Komponenten für den Aufbau von Benutzeroberflächen</title>
</Head>

In webforJ werden Anwendungen mithilfe modularer Einheiten, die als Komponenten bekannt sind, erstellt, die eine schnelle und effiziente UI-Entwicklung ermöglichen. Das Framework bietet eine Vielzahl grundlegender Komponenten wie Schaltflächen, Eingabeelemente und Layout-Container. Nachdem Sie die Grundlagen gemeistert haben, können Sie die [JavaDocs](https://javadoc.io/doc/com.webforj) zu einer detaillierten Übersicht über alle Komponenten und deren Funktionen konsultieren.

## Layouts {#layouts}

Layout-Komponenten bieten die Grundlage für die Strukturierung von Benutzeroberflächen und ermöglichen Entwicklern, Inhalte effizient zu organisieren. Diese Komponenten bieten verschiedene Möglichkeiten, die Anordnung von untergeordneten Komponenten zu steuern, sei es für einfache oder komplexe Layouts.

Die folgenden Layout-Komponenten sind so konzipiert, dass sie eine breite Palette von Anwendungsfällen abdecken, von responsivem Design bis hin zu fortschrittlichem Content-Management.

<GalleryGrid>
  <GalleryCard header="AppLayout" href="app-layout" image="/img/components/AppLayout.png">
    <p>Eine Containerkomponente, die ein strukturiertes Layout für die übergeordnete App-Navigation und die Inhaltsorganisation bereitstellt.</p>
  </GalleryCard>

  <GalleryCard header="Toolbar" href="toolbar" image="/img/components/Toolbar.png">
    <p>Ein horizontaler Container, der eine Gruppe von Aktionsschaltflächen, Symbolen oder anderen Steuerelementen hält, die typischerweise für Aufgaben verwendet werden, die mit dem aktuellen Kontext verbunden sind.</p>
  </GalleryCard>

  <GalleryCard header="FlexLayout" href="flex-layout" image="/img/components/FlexLayout.png">
    <p>Eine Layout-Komponente, die ihre Kinder mithilfe flexibler Boxregeln (Flexbox) für responsives Design und Ausrichtung anordnet.</p>
  </GalleryCard>

  <GalleryCard header="ColumnsLayout" href="columns-layout" image="/img/components/ColumnsLayout.png">
    <p>Eine Layout-Komponente, die ihre Kinder in mehrere vertikale Spalten anordnet, nützlich für die Erstellung von Formularen und gitterartigen Strukturen.</p>
  </GalleryCard>

  <GalleryCard header="Splitter" href="splitter" image="/img/components/Splitter.png" effect="slideLeftRightScale">
    <p>Eine Layout-Komponente, die den verfügbaren Platz zwischen zwei untergeordneten Komponenten aufteilt und es den Benutzern ermöglicht, diese durch Ziehen des Splitterbalkens zu ändern.</p>
  </GalleryCard>

  <GalleryCard header="Drawer" href="drawer" image="/img/components/Drawer.png" effect="slideUp">
    <p>Eine schiebende Paneelkomponente, die typischerweise für die Seitennavigation oder zur Unterbringung zusätzlicher Inhalte verwendet wird, die angezeigt oder verborgen werden können.</p>
  </GalleryCard>

  <GalleryCard header="Dialog" href="dialog" image="/img/components/Dialog.png">
    <p>Eine modale Fensterkomponente, die Inhalte überlagert, um wichtige Informationen anzuzeigen oder die Interaktion des Benutzers anzufordern, oft mit der Notwendigkeit, dass der Benutzer eine Aktion zum Schließen ausführt.</p>
  </GalleryCard>

  <GalleryCard header="Login" href="login" image="/img/components/Login.png">
    <p>Eine Komponente, die eine vorkonzipierte Benutzeroberfläche für die Benutzerauthentifizierung bereitstellt, typischerweise mit Feldern für Benutzernamen und Passwort sowie einer Schaltfläche zum Absenden.</p>
  </GalleryCard>

  <GalleryCard header="Accordion" href="accordion" image="/img/components/Accordion.png">
    <p>Ein vertikal gestapeltes Set von zusammenklappbaren Paneelen, von denen jedes einen klickbaren Header hat, der die Sichtbarkeit seines Inhaltsbody umschaltet.</p>
  </GalleryCard>

  <GalleryCard header="TabbedPane" href="tabbedpane" image="/img/components/TabbedPane.png">
    <p>Eine Containerkomponente, die Inhalte in mehrere Registerkarten organisiert und es den Benutzern ermöglicht, zwischen verschiedenen Ansichten oder Abschnitten zu wechseln.</p>
  </GalleryCard>
</GalleryGrid>

## Dateneingabe {#data-entry}

Dateneingabekomponenten bieten essentielle Werkzeuge zum Erfassen von Benutzereingaben und für die Verwaltung von Interaktionen innerhalb Ihrer App. Diese Komponenten sind vielseitig und erleichtern den Aufbau interaktiver Formulare und das Sammeln verschiedener Datenarten.

<GalleryGrid>
  <GalleryCard header="TextField" href="fields/textfield" image="/img/components/TextField.png">
    <p>Eine Eingabekomponente für eine Zeile zum Eingeben und Bearbeiten von Textdaten.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TextField" href="fields/masked/textfield" image="/img/components/MaskedTextField.png">
    <p>Eine Texteingabekomponente, die die Benutzereingabe auf ein bestimmtes Format oder Muster beschränkt, typischerweise für Felder wie Telefonnummern, Daten oder Kreditkartennummern verwendet.</p>
  </GalleryCard>

  <GalleryCard header="NumberField" href="fields/numberfield" image="/img/components/NumberField.png">
    <p>Eine Komponente, die ein standardmäßiges browserbasiertes Eingabefeld für die Eingabe numerischer Werte bereitstellt, mit integrierten Steuerelementen zum Erhöhen oder Verringern des Wertes.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>NumberField" href="fields/masked/numberfield" image="/img/components/MaskedNumberField.png">
    <p>Eine numerische Eingabekomponente, die die Benutzereingabe auf ein bestimmtes numerisches Format oder Muster beschränkt und die gültige Eingabe von Zahlen wie Währungen, Prozentsätzen oder anderen formatierten Zahlen sicherstellt.</p>
  </GalleryCard>

  <GalleryCard header="PasswordField" href="fields/passwordfield" image="/img/components/PasswordField.png">
    <p>Eine Eingabekomponente für eine Zeile, um Daten zu Passwort sicher einzugeben und zu maskieren.</p>
  </GalleryCard>

  <GalleryCard header="DateField" href="fields/datefield" image="/img/components/DateField.png">
    <p>Eine Komponente, die einen standardmäßigen browserbasierten Dateiauswähler zur Auswahl eines Datums über ein Eingabefeld bereitstellt.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>DateField" href="fields/masked/datefield" image="/img/components/MaskedDateField.png">
    <p>Eine Dateneingabekomponente, die ein bestimmtes Datumsformat oder -muster durchsetzt und sicherstellt, dass der Benutzer ein gültiges Datum gemäß der definierten Maske eingibt.</p>
  </GalleryCard>

  <GalleryCard header="TimeField" href="fields/timefield" image="/img/components/TimeField.png">
    <p>Eine Komponente, die einen standardmäßigen browserbasierten Zeitauswähler zur Auswahl eines Zeitwerts über ein Eingabefeld bereitstellt.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TimeField" href="fields/masked/timefield" image="/img/components/MaskedTimeField.png">
    <p>Eine Zeiteingabekomponente, die ein bestimmtes Zeitformat oder -muster durchsetzt und sicherstellt, dass der Benutzer eine gültige Zeit nach der definierten Maske eingibt.</p>
  </GalleryCard>

  <GalleryCard header="DateTimeField" href="fields/datetimefield" image="/img/components/DateTimeField.png">
    <p>Eine Komponente, die einen standardmäßigen browserbasierten Datum- und Zeitauswähler zur Auswahl von Datum und Zeit über ein einziges Eingabefeld bereitstellt.</p>
  </GalleryCard>

  <GalleryCard header="ColorField" href="fields/colorfield" image="/img/components/ColorField.png">
    <p>Eine Komponente, die einen standardmäßigen browserbasierten Farbauswähler bereitstellt, mit dem Benutzer eine Farbe aus einem Eingabefeld auswählen können.</p>
  </GalleryCard>

  <GalleryCard header="TextArea" href="textarea" image="/img/components/TextArea.png">
    <p>Eine mehrzeilige Texteingabekomponente, die es Benutzern ermöglicht, größere Textblöcke einzugeben oder zu bearbeiten.</p>
  </GalleryCard>

  <GalleryCard header="CheckBox" href="checkbox" image="/img/components/CheckBox.png">
    <p>Eine Komponente, die eine binäre Option darstellt und es den Benutzern ermöglicht, zwischen einem aktiven (wahr) oder inaktiven (falsch) Zustand zu wechseln.</p>
  </GalleryCard>

  <GalleryCard header="RadioButton" href="radiobutton" image="/img/components/RadioButton.png">
    <p>Eine Komponente, die es Benutzern ermöglicht, eine einzige Option aus einer Gruppe von gegenseitig ausschließenden Optionen auszuwählen.</p>
  </GalleryCard>

  <GalleryCard header="Switch" href="radiobutton#switches" image="/img/components/Switch.png">
    <p>Eine Umschaltkomponente, die es Benutzern ermöglicht, zwischen zwei Zuständen zu wechseln, z. B. ein/aus oder wahr/falsch, mit einer gleitenden Aktion.</p>
  </GalleryCard>

  <GalleryCard header="ChoiceBox" href="lists/choicebox" image="/img/components/ChoiceBox.png">
    <p>Eine Komponente, die eine Dropdown-Liste vordefinierter Optionen bereitstellt und es den Benutzern ermöglicht, eine Option aus der Liste auszuwählen.</p>
  </GalleryCard>

  <GalleryCard header="ComboBox" href="lists/combobox" image="/img/components/ComboBox.png">
    <p>Eine Komponente, die eine Dropdown-Liste mit einem editierbaren Texteingabefeld kombiniert und es den Benutzern ermöglicht, entweder eine Option aus der Liste auszuwählen oder einen benutzerdefinierten Wert einzugeben.</p>
  </GalleryCard>

  <GalleryCard header="ListBox" href="lists/listbox" image="/img/components/ListBox.png">
    <p>Eine Komponente, die eine scrollbare Liste von Optionen anzeigt und es Benutzern ermöglicht, eines oder mehrere Elemente aus der Liste auszuwählen.</p>
  </GalleryCard>
</GalleryGrid>

## Optionsdialoge {#option-dialogs}

Optionsdialoge bieten eine Möglichkeit, den Benutzern Auswahlmöglichkeiten zu präsentieren oder sie um Bestätigung zu bitten, bevor sie mit einer Aktion fortfahren. Diese Komponenten sind unerlässlich, um interaktive, entscheidungsgetriebenen Workflows zu erstellen, und ermöglichen es den Benutzern, klar und strukturiert zu bestätigen, abzulehnen oder aus verschiedenen Optionen auszuwählen.

<GalleryGrid>
  <GalleryCard header="MessageDialog" href="option-dialogs/message" image="/img/components/MessageDialog.png">
    <p>Eine Dialogkomponente, die verwendet wird, um Informationsnachrichten oder Warnungen an den Benutzer anzuzeigen, typischerweise mit einer einzelnen `OK`-Schaltfläche zur Bestätigung der Nachricht.</p>
  </GalleryCard>

  <GalleryCard header="ConfirmDialog" href="option-dialogs/confirm" image="/img/components/ConfirmDialog.png">
    <p>Eine Dialogkomponente, die den Benutzer auffordert, eine Aktion zu bestätigen oder abzulehnen, typischerweise mit `Ja` und `Nein` oder `OK` und `Abbrechen`-Schaltflächen.</p>
  </GalleryCard>
  
  <GalleryCard header="InputDialog" href="option-dialogs/input" image="/img/components/InputDialog.png">
    <p>Eine Dialogkomponente, die den Benutzer auffordert, Text oder Daten einzugeben, typischerweise mit einem Eingabefeld sowie Aktionsschaltflächen wie `OK` und `Abbrechen`.</p>
  </GalleryCard>

  <GalleryCard header="FileChooserDialog" href="option-dialogs/file-chooser" image="/img/components/FileChooserDialog.png">
    <p>Eine Dialogkomponente, die es den Benutzern ermöglicht, Dateien aus dem Server-Dateisystem zu durchsuchen und auszuwählen.</p>
  </GalleryCard>

  <GalleryCard header="FileUploadDialog" href="option-dialogs/file-upload" image="/img/components/FileUploadDialog.png">
    <p>Eine Dialogkomponente, die es den Benutzern ermöglicht, Dateien von ihrem lokalen Dateisystem in die App hochzuladen.</p>
  </GalleryCard>

  <GalleryCard header="FileSaveDialog" href="option-dialogs/file-save" image="/img/components/FileSaveDialog.png">
    <p>Eine Dialogkomponente, die es den Benutzern ermöglicht, eine Datei an einem bestimmten Ort im Server-Dateisystem zu speichern.</p>
  </GalleryCard>
</GalleryGrid>

## Interaktion und Anzeige {#interaction-and-display}

Diese Kategorie umfasst Komponenten, die Benutzerinteraktionen erleichtern und Daten oder Anwendungszustände visuell anzeigen. Diese Komponenten helfen den Benutzern, sich in der App zu orientieren, Aktionen auszulösen und Fortschritte oder Ergebnisse durch dynamische visuelle Elemente zu verstehen.

<GalleryGrid>
  <GalleryCard header="Table" href="table/overview" image="/img/components/Table.png">
    <p> Eine Komponente, die Daten in einem strukturierten, tabellarischen Format mit Zeilen und Spalten anzeigt, wobei Funktionen wie Sortierung und Paginierung unterstützt werden.</p>
  </GalleryCard>

  <GalleryCard header="GoogleCharts" href="google-charts" image="/img/components/GoogleCharts.png">
    <p>Eine Komponente, die sich mit Google Charts integriert, um verschiedene Arten von Diagrammen und visuellen Datenrepräsentationen in der App anzuzeigen.</p>
  </GalleryCard>

  <GalleryCard header="Button" href="button" image="/img/components/Button.png">
    <p>Eine klickbare Komponente, die eine Aktion oder ein Ereignis auslöst, wenn sie gedrückt wird.</p>
  </GalleryCard>

  <GalleryCard header="Toast" href="toast" image="/img/components/Toast.png" effect="slideUp">
    <p>Eine leichte, nicht blockierende Benachrichtigungskomponente, die eine Nachricht kurzzeitig anzeigt, bevor sie automatisch verschwindet.</p>
  </GalleryCard>

  <GalleryCard header="Alert" href="alert" image="/img/components/Alert.png">
    <p>Eine Komponente, die wichtige Nachrichten oder Warnungen in einem auffälligen Format anzeigt, um die Aufmerksamkeit des Benutzers zu erregen.</p>
  </GalleryCard>

  <GalleryCard header="Badge" href="badge" image="/img/components/Badge.png">
    <p>Eine kleine Beschriftungskomponente zur Anzeige von Zählungen, Status oder kurzen Metadaten, mit Unterstützung für Themen, Größen und Symbole.</p>
  </GalleryCard>

  <GalleryCard header="DesktopNotification" href="desktop-notification" image="/img/components/DesktopNotification.png">
    <p>Eine Komponente, die die native Benachrichtigungs-API des Browsers nutzt, um Benutzer mit benutzerdefinierten Desktop-Benachrichtigungen zu alarmieren.</p>
  </GalleryCard>
  
  <GalleryCard header="Navigator" href="navigator" image="/img/components/Navigator.png">
    <p>Eine anpassbare Paginierungskomponente zum Navigieren durch Datensätze, die Layouts mit ersten, letzten, nächsten, vorherigen Schaltflächen und Schnellsprungfeldern unterstützt.</p>
  </GalleryCard>

  <GalleryCard header="ProgressBar" href="progressbar" image="/img/components/ProgressBar.png">
    <p>Eine Komponente, die den Fortschritt einer Aufgabe oder eines Prozesses visuell darstellt, typischerweise als horizontale Leiste, die sich füllt, während Fortschritte erzielt werden.</p>
  </GalleryCard>

  <GalleryCard header="Slider" href="slider" image="/img/components/Slider.png">
    <p>Eine Komponente, die es Benutzern erlaubt, einen Wert aus einem definierten Bereich auszuwählen, indem sie einen Schieberegler entlang einer Spur ziehen.</p>
  </GalleryCard>

  <GalleryCard header="BusyIndicator" href="busyindicator" image="/img/components/BusyIndicator.png">
    <p>Ein anwendungsweiter visueller Indikator, typischerweise ein Spinner, der signalisiert, dass ein globaler Prozess läuft.</p>
  </GalleryCard>

  <GalleryCard header="Loading" href="loading" image="/img/components/Loading.png">
    <p>Ein kontextbezogener Ladeindikator, der in einem bestimmten übergeordneten Element angezeigt wird und signalisiert, dass Inhalte oder Daten in diesem Abschnitt geladen werden.</p>
  </GalleryCard>

  <GalleryCard header="Spinner" href="spinner" image="/img/components/Spinner.png">
    <p>Eine Komponente, die eine rotierende Animation anzeigt, die typischerweise verwendet wird, um anzuzeigen, dass ein Prozess oder eine Aktion im Gange ist.</p>
  </GalleryCard>

  <GalleryCard header="AppNav" href="appnav" image="/img/components/AppNav.png" effect="slideFromLeft">
    <p>Eine Komponente, die ein Navigationsmenü für die App bereitstellt, typischerweise zur Auflistung von Links oder Navigationselementen zum Wechseln zwischen verschiedenen Abschnitten oder Ansichten.</p>
  </GalleryCard>

  <GalleryCard header="Icon" href="icon" image="/img/components/Icons.png">
    <p>Eine Komponente, die ein grafisches Symbol oder Bild anzeigt, das häufig verwendet wird, um eine Aktion, einen Status oder eine Kategorie in der Benutzeroberfläche darzustellen.</p>
  </GalleryCard>

  <GalleryCard header="Terminal" href="terminal" image="/img/components/Terminal.png">
    <p>Eine Komponente, die eine Befehlszeilenschnittstelle (CLI) innerhalb der App simuliert und es Benutzern ermöglicht, textbasierte Befehle einzugeben und auszuführen.</p>
  </GalleryCard>
  
  <GalleryCard header="InfiniteScroll" href="infinitescroll" image="/img/components/InfiniteScroll.png">
    <p>Eine Komponente, die beim Scrollen mehr Elemente lädt, einen Lader anzeigt und nachverfolgt, wann alle Inhalte abgerufen wurden.</p>
  </GalleryCard>

  <GalleryCard header="Refresher" href="refresher" image="/img/components/Refresher.png">
    <p>Eine Komponente, die eine Pull-to-Refresh-Interaktion innerhalb scrollbarer Container ermöglicht - ideal für das dynamische Laden von Daten.</p>
  </GalleryCard>

  <GalleryCard header="Tree" href="tree" image="/img/components/Tree.png">
    <p>Eine Komponente zur Anzeige hierarchischer Daten, die es Benutzern ermöglicht, verschachtelte Elemente zu erweitern, zu reduzieren und damit zu interagieren.</p>
  </GalleryCard>
  
  <GalleryCard header="Avatar" href="avatar" image="/img/components/Avatar.png">
    <p>Eine Komponente zur Anzeige von Benutzerprofilbildern oder Initialen, mit Unterstützung für verschiedene Größen, Formen und Themen.</p>
  </GalleryCard>
  
  <GalleryCard header="MarkdownViewer" href="markdownviewer" image="/img/components/MarkdownViewer.png">
    <p>Eine Komponente zur Anzeige von Markdown-Inhalten mit progressiver charakterweise Rendering, ideal für KI-Chat-Schnittstellen und Text-Streaming.</p>
  </GalleryCard>
  
</GalleryGrid>
