---
title: UI Components
sidebar_position: 85
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Browse the webforJ UI component catalog covering layouts, data entry,
  navigation, feedback, and visualization components.
_i18n_hash: 5533b760c7585442c917bc506c2dd763
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<Head>
  <title>UI-Komponenten | Komponenten für die Benutzeroberflächenanwendung</title>
</Head>

In webforJ werden Anwendungen mithilfe von modularen Einheiten erstellt, die als Komponenten bekannt sind und eine schnelle und effiziente Entwicklung von Benutzeroberflächen ermöglichen. Das Framework bietet eine Vielzahl wesentlicher Komponenten wie Schaltflächen, Eingabeelemente und Layout-Container. Nachdem Sie die Grundlagen beherrschen, können Sie die [JavaDocs](https://javadoc.io/doc/com.webforj) für einen detaillierten Überblick über alle Komponenten und deren Funktionalitäten konsultieren.

## Layouts {#layouts}

Layout-Komponenten bilden die Grundlage für die Strukturierung von Benutzeroberflächen und ermöglichen es Entwicklern, Inhalte effizient zu organisieren. Diese Komponenten bieten verschiedene Möglichkeiten, die Anordnung von Kindkomponenten zu steuern, sei es für einfache oder komplexe Layouts.

Die folgenden Layout-Komponenten sind dafür konzipiert, eine Vielzahl von Anwendungsfällen zu behandeln, von responsive Designs bis hin zu fortgeschrittener Inhaltsverwaltung.

<GalleryGrid>
  <GalleryCard header="AppLayout" href="app-layout" image="/img/components/light/AppLayout.webp" imageDark="/img/components/dark/AppLayout.webp">
    <p>Eine Container-Komponente, die ein strukturiertes Layout für die Navigation und die Organisation von Inhalten auf der obersten Ebene bereitstellt.</p>
  </GalleryCard>

  <GalleryCard header="Toolbar" href="toolbar" image="/img/components/light/Toolbar.webp" imageDark="/img/components/dark/Toolbar.webp">
    <p>Eine horizontale Container-Komponente, die eine Reihe von Aktionsschaltflächen, Symbolen oder anderen Steuerelementen enthält, die typischerweise zum Ausführen von Aufgaben im aktuellen Kontext verwendet werden.</p>
  </GalleryCard>

  <GalleryCard header="FlexLayout" href="flex-layout" image="/img/components/light/FlexLayout.webp" imageDark="/img/components/dark/FlexLayout.webp">
    <p>Eine Layout-Komponente, die ihre Kinder mithilfe von flexiblen Boxen (Flexbox) für responsives Design und Ausrichtung anordnet.</p>
  </GalleryCard>

  <GalleryCard header="ColumnsLayout" href="columns-layout" image="/img/components/light/ColumnsLayout.webp" imageDark="/img/components/dark/ColumnsLayout.webp">
    <p>Eine Layout-Komponente, die ihre Kinder in mehrere vertikale Spalten anordnet, nützlich zum Erstellen von Formularen und gitterähnlichen Strukturen.</p>
  </GalleryCard>

  <GalleryCard header="Splitter" href="splitter" image="/img/components/light/Splitter.webp" imageDark="/img/components/dark/Splitter.webp">
    <p>Eine Layout-Komponente, die den verfügbaren Platz zwischen zwei Kindkomponenten aufteilt und es den Benutzern ermöglicht, diese durch Ziehen der Trennleiste zu ändern.</p>
  </GalleryCard>

  <GalleryCard header="Drawer" href="drawer" image="/img/components/light/Drawer.webp" imageDark="/img/components/dark/Drawer.webp">
    <p>Eine Gleitschrank-Komponente, die typischerweise für die seitliche Navigation oder zur Aufnahme zusätzlicher Inhalte verwendet wird, die ein- oder ausgeblendet werden können.</p>
  </GalleryCard>

  <GalleryCard header="Dialog" href="dialog" image="/img/components/light/Dialog.webp" imageDark="/img/components/dark/Dialog.webp">
    <p>Eine modale Fenster-komponente, die Inhalte überlagert, um wichtige Informationen anzuzeigen oder die Benutzerinteraktion zu fördern, wobei häufig eine Benutzeraktion erforderlich ist, um sie zu schließen.</p>
  </GalleryCard>

  <GalleryCard header="Login" href="login" image="/img/components/light/Login.webp" imageDark="/img/components/dark/Login.webp">
    <p>Eine Komponente, die eine vorgefertigte Benutzeroberfläche für die Benutzer-Authentifizierung bereitstellt, typischerweise mit Feldern für Benutzernamen und Passwort sowie einer Schaltfläche zum Senden.</p>
  </GalleryCard>

  <GalleryCard header="Accordion" href="accordion" image="/img/components/light/Accordion.webp" imageDark="/img/components/dark/Accordion.webp">
    <p>Eine vertikal gestapelte Reihe von zusammenklappbaren Panels, von denen jedes einen klickbaren Header hat, der die Sichtbarkeit des dazugehörigen Inhalts umschaltet.</p>
  </GalleryCard>

  <GalleryCard header="TabbedPane" href="tabbedpane" image="/img/components/light/TabbedPane.webp" imageDark="/img/components/dark/TabbedPane.webp">
    <p>Eine Container-Komponente, die Inhalte in mehrere Tabs organisiert und es den Benutzern ermöglicht, zwischen verschiedenen Ansichten oder Abschnitten zu wechseln.</p>
  </GalleryCard>
</GalleryGrid>

## Dateneingabe {#data-entry}

Dateneingabekomponenten bieten wichtige Werkzeuge zum Erfassen von Benutzereingaben und zum Verwalten von Interaktionen innerhalb Ihrer App. Diese Komponenten sind vielseitig und erleichtern das Erstellen interaktiver Formulare und das Sammeln verschiedener Arten von Daten.

<GalleryGrid>
  <GalleryCard header="TextField" href="fields/textfield" image="/img/components/light/TextField.webp" imageDark="/img/components/dark/TextField.webp">
    <p>Eine Eingabekomponente für eine Zeile zum Eingeben und Bearbeiten von Textdaten.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TextField" href="fields/masked/textfield" image="/img/components/light/MaskedTextField.webp" imageDark="/img/components/dark/MaskedTextField.webp">
    <p>Eine Texteingabekomponente, die die Benutzereingabe auf ein bestimmtes Format oder Muster beschränkt, typischerweise verwendet für Felder wie Telefonnummern, Daten oder Kreditkartennummern.</p>
  </GalleryCard>

  <GalleryCard header="NumberField" href="fields/numberfield" image="/img/components/light/NumberField.webp" imageDark="/img/components/dark/NumberField.webp">
    <p>Eine Komponente, die ein standardmäßiges, browserbasiertes Eingabefeld für die Eingabe numerischer Werte bereitstellt, mit integrierten Steuerelementen zur Erhöhung oder Verringerung des Wertes.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>NumberField" href="fields/masked/numberfield" image="/img/components/light/MaskedNumberField.webp" imageDark="/img/components/dark/MaskedNumberField.webp">
    <p>Eine numerische Eingabekomponente, die die Benutzereingabe auf ein bestimmtes numerisches Format oder Muster beschränkt, um die gültige Eingabe von Zahlen wie Währungen, Prozentsätzen oder anderen formatierten Zahlen sicherzustellen.</p>
  </GalleryCard>

  <GalleryCard header="PasswordField" href="fields/passwordfield" image="/img/components/light/PasswordField.webp" imageDark="/img/components/dark/PasswordField.webp">
    <p>Eine Eingabekomponente für eine Zeile zum sicheren Eingeben und Maskieren von Passwortdaten.</p>
  </GalleryCard>

  <GalleryCard header="DateField" href="fields/datefield" image="/img/components/light/DateField.webp" imageDark="/img/components/dark/DateField.webp">
    <p>Eine Komponente, die einen standardmäßigen browserbasierten Datumsauswähler für die Auswahl eines Datums über ein Eingabefeld bereitstellt.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>DateField" href="fields/masked/datefield" image="/img/components/light/MaskedDateField.webp" imageDark="/img/components/dark/MaskedDateField.webp">
    <p>Eine Datumseingabekomponente, die ein bestimmtes Datumsformat oder Muster durchsetzt und sicherstellt, dass der Benutzer ein gültiges Datum gemäß der definierten Maske eingibt.</p>
  </GalleryCard>

  <GalleryCard header="TimeField" href="fields/timefield" image="/img/components/light/TimeField.webp" imageDark="/img/components/dark/TimeField.webp">
    <p>Eine Komponente, die einen standardmäßigen browserbasierten Zeitpicker für die Auswahl eines Zeitwerts über ein Eingabefeld bereitstellt.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TimeField" href="fields/masked/timefield" image="/img/components/light/MaskedTimeField.webp" imageDark="/img/components/dark/MaskedTimeField.webp">
    <p>Eine Zeiteingabekomponente, die ein bestimmtes Zeitformat oder Muster durchsetzt und sicherstellt, dass der Benutzer eine gültige Zeit gemäß der definierten Maske eingibt.</p>
  </GalleryCard>

  <GalleryCard header="DateTimeField" href="fields/datetimefield" image="/img/components/light/DateTimeField.webp" imageDark="/img/components/dark/DateTimeField.webp">
    <p>Eine Komponente, die einen standardmäßigen browserbasierten Datum- und Zeitwähler für die Auswahl von Datum und Uhrzeit über ein einzelnes Eingabefeld bereitstellt.</p>
  </GalleryCard>

  <GalleryCard header="ColorField" href="fields/colorfield" image="/img/components/light/ColorField.webp" imageDark="/img/components/dark/ColorField.webp">
    <p>Eine Komponente, die einen standardmäßigen browserbasierten Farbwähler bereitstellt, mit dem Benutzer eine Farbe aus einem Eingabefeld auswählen können.</p>
  </GalleryCard>

  <GalleryCard header="TextArea" href="textarea" image="/img/components/light/TextArea.webp" imageDark="/img/components/dark/TextArea.webp">
    <p>Eine mehrzeilige Texteingabekomponente, die es Benutzern ermöglicht, größere Textblöcke einzugeben oder zu bearbeiten.</p>
  </GalleryCard>

  <GalleryCard header="CheckBox" href="checkbox" image="/img/components/light/CheckBox.webp" imageDark="/img/components/dark/CheckBox.webp">
    <p>Eine Komponente, die eine binäre Option darstellt und es Benutzern ermöglicht, zwischen einem markierten (wahr) oder einem nicht markierten (falsch) Zustand zu wechseln.</p>
  </GalleryCard>

  <GalleryCard header="RadioButton" href="radiobutton" image="/img/components/light/RadioButton.webp" imageDark="/img/components/dark/RadioButton.webp">
    <p>Eine Komponente, die es Benutzern ermöglicht, eine einzige Option aus einer Gruppe von sich gegenseitig ausschließenden Auswahlmöglichkeiten auszuwählen.</p>
  </GalleryCard>

  <GalleryCard header="Switch" href="radiobutton#switches" image="/img/components/light/Switch.webp" imageDark="/img/components/dark/Switch.webp">
    <p>Eine Umschaltkomponente, die es Benutzern ermöglicht, zwischen zwei Zuständen wie ein/aus oder wahr/falsch mit einer Schiebebewegung zu wechseln.</p>
  </GalleryCard>

  <GalleryCard header="ChoiceBox" href="lists/choicebox" image="/img/components/light/ChoiceBox.webp" imageDark="/img/components/dark/ChoiceBox.webp">
    <p>Eine Komponente, die eine Dropdown-Liste vordefinierter Optionen bereitstellt und es Benutzern ermöglicht, eine Option aus der Liste auszuwählen.</p>
  </GalleryCard>

  <GalleryCard header="ComboBox" href="lists/combobox" image="/img/components/light/ComboBox.webp" imageDark="/img/components/dark/ComboBox.webp">
    <p>Eine Komponente, die eine Dropdown-Liste mit einem bearbeitbaren Texteingabefeld kombiniert und es Benutzern ermöglicht, entweder eine Option aus der Liste auszuwählen oder einen benutzerdefinierten Wert einzugeben.</p>
  </GalleryCard>

  <GalleryCard header="ListBox" href="lists/listbox" image="/img/components/light/ListBox.webp" imageDark="/img/components/dark/ListBox.webp">
    <p>Eine Komponente, die eine scrollbare Liste von Optionen anzeigt und es Benutzern ermöglicht, ein oder mehrere Elemente aus der Liste auszuwählen.</p>
  </GalleryCard>

  <GalleryCard header="Upload" href="upload" image="/img/components/light/Upload.webp" imageDark="/img/components/dark/Upload.webp">
    <p>Ein Inline-Dateiauswähler, der es Benutzern ermöglicht, eine oder mehrere Dateien von ihrem lokalen Computer auszuwählen und sie auf den Server hochzuladen, mit Drag-and-Drop, Filtern und Datei-spezifischem Event-Tracking.</p>
  </GalleryCard>
</GalleryGrid>

## Optionsdialoge {#option-dialogs}

Optionsdialoge bieten eine Möglichkeit, Benutzern Entscheidungen zu präsentieren oder sie vor dem Fortfahren mit einer Aktion um Bestätigung zu bitten. Diese Komponenten sind entscheidend für die Erstellung interaktiver, entscheidungsorientierter Arbeitsabläufe, die es Benutzern ermöglichen, zu bestätigen, abzulehnen oder aus verschiedenen Optionen klar und strukturiert zu wählen.

<GalleryGrid>
  <GalleryCard header="MessageDialog" href="option-dialogs/message" image="/img/components/light/MessageDialog.webp" imageDark="/img/components/dark/MessageDialog.webp">
    <p>Eine Dialogkomponente, die verwendet wird, um Informationsnachrichten oder Warnungen an den Benutzer anzuzeigen, typischerweise mit einer einzigen `OK`-Schaltfläche, um die Nachricht anzuerkennen.</p>
  </GalleryCard>

  <GalleryCard header="ConfirmDialog" href="option-dialogs/confirm" image="/img/components/light/ConfirmDialog.webp" imageDark="/img/components/dark/ConfirmDialog.webp">
    <p>Eine Dialogkomponente, die den Benutzer um Bestätigung oder Absage einer Aktion bittet, typischerweise mit Schaltflächen für `Ja` und `Nein` oder `OK` und `Abbrechen`.</p>
  </GalleryCard>

  <GalleryCard header="InputDialog" href="option-dialogs/input" image="/img/components/light/InputDialog.webp" imageDark="/img/components/dark/InputDialog.webp">
    <p>Eine Dialogkomponente, die den Benutzer auffordert, Text oder Daten einzugeben, oft mit einem Eingabefeld sowie Aktionsschaltflächen wie `OK` und `Abbrechen`.</p>
  </GalleryCard>

  <GalleryCard header="FileChooserDialog" href="option-dialogs/file-chooser" image="/img/components/light/FileChooserDialog.webp" imageDark="/img/components/dark/FileChooserDialog.webp">
    <p>Eine Dialogkomponente, die es Benutzern erlaubt, Dateien im Server-Dateisystem zu durchsuchen und auszuwählen.</p>
  </GalleryCard>

  <GalleryCard header="FileUploadDialog" href="option-dialogs/file-upload" image="/img/components/light/FileUploadDialog.webp" imageDark="/img/components/dark/FileUploadDialog.webp">
    <p>Eine Dialogkomponente, die es Benutzern ermöglicht, Dateien von ihrem lokalen Dateisystem in die App hochzuladen.</p>
  </GalleryCard>

  <GalleryCard header="FileSaveDialog" href="option-dialogs/file-save" image="/img/components/light/FileSaveDialog.webp" imageDark="/img/components/dark/FileSaveDialog.webp">
    <p>Eine Dialogkomponente, die es Benutzern ermöglicht, eine Datei an einem bestimmten Ort im Server-Dateisystem zu speichern.</p>
  </GalleryCard>
</GalleryGrid>

## Interaktion und Anzeige {#interaction-and-display}

Diese Kategorie umfasst Komponenten, die Benutzereingaben erleichtern und Daten oder Anwendungszustände visuell anzeigen. Diese Komponenten helfen Benutzern, durch die App zu navigieren, Aktionen auszulösen und Fortschritte oder Ergebnisse durch dynamische visuelle Elemente zu verstehen.

<GalleryGrid>
  <GalleryCard header="Table" href="table/overview" image="/img/components/light/Table.webp" imageDark="/img/components/dark/Table.webp">
    <p>Eine Komponente, die Daten in einem strukturierten, tabellarischen Format mit Zeilen und Spalten anzeigt und Funktionen wie Sortierung und Seitenumbruch unterstützt.</p>
  </GalleryCard>

  <GalleryCard header="GoogleCharts" href="google-charts" image="/img/components/light/GoogleCharts.webp" imageDark="/img/components/dark/GoogleCharts.webp">
    <p>Eine Komponente, die mit Google Charts integriert ist, um verschiedene Arten von Diagrammen und visuellen Daten darzustellen.</p>
  </GalleryCard>

  <GalleryCard header="Button" href="button" image="/img/components/light/Button.webp" imageDark="/img/components/dark/Button.webp">
    <p>Eine klickbare Komponente, die eine Aktion oder ein Ereignis auslöst, wenn sie gedrückt wird.</p>
  </GalleryCard>

  <GalleryCard header="Toast" href="toast" image="/img/components/light/Toast.webp" imageDark="/img/components/dark/Toast.webp">
    <p>Eine leichte, nicht-blockierende Benachrichtigungs-Komponente, die kurzzeitig eine Nachricht an den Benutzer anzeigt, bevor sie automatisch verschwindet.</p>
  </GalleryCard>

  <GalleryCard header="Alert" href="alert" image="/img/components/light/Alert.webp" imageDark="/img/components/dark/Alert.webp">
    <p>Eine Komponente, die wichtige Nachrichten oder Warnungen in einem auffälligen Format anzeigt, um die Aufmerksamkeit des Benutzers zu erregen.</p>
  </GalleryCard>

  <GalleryCard header="Badge" href="badge" image="/img/components/light/Badge.webp" imageDark="/img/components/dark/Badge.webp">
    <p>Eine kleine Etikettenkomponente zur Anzeige von Zähler, Status oder kurzen Metadaten mit Unterstützung für Themen, Größen und Symbole.</p>
  </GalleryCard>

  <GalleryCard header="DesktopNotification" href="desktop-notification" image="/img/components/light/DesktopNotification.webp" imageDark="/img/components/dark/DesktopNotification.webp">
    <p>Eine Komponente, die die native Notification-API des Browsers nutzt, um Benutzer mit benutzerdefinierten Desktopbenachrichtigungen zu benachrichtigen.</p>
  </GalleryCard>

  <GalleryCard header="Navigator" href="navigator" image="/img/components/light/Navigator.webp" imageDark="/img/components/dark/Navigator.webp">
    <p>Eine anpassbare Seitenkomponente zur Navigation durch Datensätze, die Layouts mit ersten, letzten, nächsten, vorherigen Tasten und Schnellzugriffsfeldern unterstützt.</p>
  </GalleryCard>

  <GalleryCard header="ProgressBar" href="progressbar" image="/img/components/light/ProgressBar.webp" imageDark="/img/components/dark/ProgressBar.webp">
    <p>Eine Komponente, die den Fortschritt einer Aufgabe oder eines Prozesses visuell darstellt, typischerweise als horizontale Leiste, die sich füllt, während Fortschritte gemacht werden.</p>
  </GalleryCard>

  <GalleryCard header="Slider" href="slider" image="/img/components/light/Slider.webp" imageDark="/img/components/dark/Slider.webp">
    <p>Eine Komponente, die es Benutzern ermöglicht, einen Wert aus einem definierten Bereich auszuwählen, indem sie einen Griff entlang einer Spur ziehen.</p>
  </GalleryCard>

  <GalleryCard header="BusyIndicator" href="busyindicator" image="/img/components/light/BusyIndicator.webp" imageDark="/img/components/dark/BusyIndicator.webp">
    <p>Ein anwendungsweites visuelles Indikator, typischerweise ein Spinner, der signalisiert, dass ein globaler Prozess im Gange ist.</p>
  </GalleryCard>

  <GalleryCard header="Loading" href="loading" image="/img/components/light/Loading.webp" imageDark="/img/components/dark/Loading.webp">
    <p>Ein örtlicher Ladeindikator, der innerhalb einer bestimmten übergeordneten Komponente angezeigt wird und anzeigt, dass Inhalte oder Daten in diesem Abschnitt geladen werden.</p>
  </GalleryCard>

  <GalleryCard header="Spinner" href="spinner" image="/img/components/light/Spinner.webp" imageDark="/img/components/dark/Spinner.webp">
    <p>Eine Komponente, die eine rotierende Animation anzeigt, typischerweise verwendet, um anzuzeigen, dass ein Prozess oder eine Aktion im Gange ist.</p>
  </GalleryCard>

  <GalleryCard header="AppNav" href="appnav" image="/img/components/light/AppNav.webp" imageDark="/img/components/dark/AppNav.webp">
    <p>Eine Komponente, die ein Navigationsmenü für die App bereitstellt, typischerweise verwendet zum Auflisten von Links oder Navigationselementen zum Wechseln zwischen verschiedenen Abschnitten oder Ansichten.</p>
  </GalleryCard>

  <GalleryCard header="Icon" href="icon" image="/img/components/light/Icon.webp" imageDark="/img/components/dark/Icon.webp">
    <p>Eine Komponente, die ein grafisches Symbol oder Bild anzeigt, das häufig verwendet wird, um eine Aktion, einen Status oder eine Kategorie in der Benutzeroberfläche darzustellen.</p>
  </GalleryCard>

  <GalleryCard header="Terminal" href="terminal" image="/img/components/light/Terminal.webp" imageDark="/img/components/dark/Terminal.webp">
    <p>Eine Komponente, die eine Befehlszeilenschnittstelle (CLI) innerhalb der App simuliert und es Benutzern ermöglicht, textbasierte Befehle einzugeben und auszuführen.</p>
  </GalleryCard>

  <GalleryCard header="InfiniteScroll" href="infinitescroll" image="/img/components/light/InfiniteScroll.webp" imageDark="/img/components/dark/InfiniteScroll.webp">
    <p>Eine Komponente, die beim Scrollen mehr Elemente lädt, einen Ladeindikator anzeigt und verfolgt, wann alle Inhalte abgerufen wurden.</p>
  </GalleryCard>

  <GalleryCard header="Refresher" href="refresher" image="/img/components/light/Refresher.webp" imageDark="/img/components/dark/Refresher.webp">
    <p>Eine Komponente, die eine Pull-to-Refresh-Interaktion innerhalb scrollbarer Container ermöglicht – ideal zum dynamischen Laden von Daten.</p>
  </GalleryCard>

  <GalleryCard header="Tree" href="tree" image="/img/components/light/Tree.webp" imageDark="/img/components/dark/Tree.webp">
    <p>Eine Komponente zur Anzeige hierarchischer Daten, die es Benutzern ermöglicht, verschachtelte Elemente zu erweitern, zu minimieren und mit ihnen zu interagieren.</p>
  </GalleryCard>

  <GalleryCard header="Avatar" href="avatar" image="/img/components/light/Avatar.webp" imageDark="/img/components/dark/Avatar.webp">
    <p>Eine Komponente zur Anzeige von Benutzerprofilbildern oder Initialen, mit Unterstützung für verschiedene Größen, Formen und Themen.</p>
  </GalleryCard>

  <GalleryCard header="MarkdownViewer" href="markdownviewer" image="/img/components/light/MarkdownViewer.webp" imageDark="/img/components/dark/MarkdownViewer.webp">
    <p>Eine Komponente zur Anzeige von Markdown-Inhalten mit schrittweiser, zeichenweiser Darstellung, ideal für KI-Chatoberflächen und Streaming-Text.</p>
  </GalleryCard>

</GalleryGrid>
