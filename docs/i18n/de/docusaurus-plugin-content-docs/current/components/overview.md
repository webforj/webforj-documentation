---
title: UI Components
sidebar_position: 85
hide_table_of_contents: true
sidebar_class_name: has-new-content
hide_giscus_comments: true
_i18n_hash: 3cecf991ebc3086900ecf15b1d0a7b20
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<Head>
  <title>UI-Komponenten | Komponenten zum Erstellen von Benutzeroberflächen-Anwendungen</title>
</Head>

In webforJ werden Anwendungen mit modularen Einheiten erstellt, die als Komponenten bekannt sind und eine schnelle und effiziente UI-Entwicklung ermöglichen. Das Framework bietet eine Reihe grundlegender Komponenten wie Schaltflächen, Eingabeelemente und Layout-Container. Nachdem Sie die Grundlagen beherrscht haben, können Sie die [JavaDocs](https://javadoc.io/doc/com.webforj) für einen detaillierten Überblick über alle Komponenten und deren Funktionen konsultieren.

## Layouts {#layouts}

Layout-Komponenten bilden die Grundlage für die Strukturierung von Benutzeroberflächen und ermöglichen Entwicklern, Inhalte effizient zu organisieren. Diese Komponenten bieten verschiedene Möglichkeiten zur Steuerung der Anordnung von Kindkomponenten, egal ob für einfache oder komplexe Layouts.

Die folgenden Layout-Komponenten sind darauf ausgelegt, eine Vielzahl von Anwendungsfällen abzudecken, von responsive Design bis hin zu fortgeschrittener Inhaltsverwaltung.

<GalleryGrid>
  <GalleryCard header="AppLayout" href="app-layout" image="/img/components/AppLayout.png">
    <p>Eine Container-Komponente, die ein strukturiertes Layout für die Navigation und die Organisation von Inhalten auf höchster Ebene bereitstellt.</p>
  </GalleryCard>

  <GalleryCard header="Toolbar" href="toolbar" image="/img/components/Toolbar.png">
    <p>Eine horizontale Container-Komponente, die eine Reihe von Aktionsschaltflächen, Symbolen oder anderen Steuerelementen enthält, die typischerweise verwendet werden, um Aufgaben im aktuellen Kontext auszuführen.</p>
  </GalleryCard>

  <GalleryCard header="FlexLayout" href="flex-layout" image="/img/components/FlexLayout.png">
    <p>Eine Layout-Komponente, die ihre Kinder mithilfe von flexiblen Box (Flexbox) Regeln für responsives Design und Ausrichtung anordnet.</p>
  </GalleryCard>

  <GalleryCard header="ColumnsLayout" href="columns-layout" image="/img/components/ColumnsLayout.png">
    <p>Eine Layout-Komponente, die ihre Kinder in mehrere vertikale Spalten anordnet, nützlich für die Erstellung von Formularen und rasterähnlichen Strukturen.</p>
  </GalleryCard>

  <GalleryCard header="Splitter" href="splitter" image="/img/components/Splitter.png" effect="slideLeftRightScale">
    <p>Eine Layout-Komponente, die den verfügbaren Platz zwischen zwei Kindkomponenten aufteilt und es Benutzern ermöglicht, diese durch Ziehen des Splitterbalkens zu vergrößern oder zu verkleinern.</p>
  </GalleryCard>

  <GalleryCard header="Drawer" href="drawer" image="/img/components/Drawer.png" effect="slideUp">
    <p>Eine Schiebepanel-Komponente, die typischerweise für die Seitennavigation oder zur Unterbringung zusätzlicher Inhalte verwendet wird, die angezeigt oder verborgen werden können.</p>
  </GalleryCard>

  <GalleryCard header="Dialog" href="dialog" image="/img/components/Dialog.png">
    <p>Eine modale Fensterkomponente, die Inhalte überlagert, um wichtige Informationen anzuzeigen oder eine Benutzerinteraktion anzufordern, wobei oft eine Benutzeraktion erforderlich ist, um sie zu schließen.</p>
  </GalleryCard>

  <GalleryCard header="Login" href="login" image="/img/components/Login.png">
    <p>Eine Komponente, die eine vorgefertigte Benutzeroberfläche für die Benutzerauthentifizierung bereitstellt, die typischerweise Felder für Benutzernamen und Passwort sowie einen Absende-Button umfasst.</p>
  </GalleryCard>

  <GalleryCard header="TabbedPane" href="tabbedpane" image="/img/components/TabbedPane.png">
    <p>Eine Container-Komponente, die Inhalte in mehrere Registerkarten organisiert, sodass Benutzer zwischen verschiedenen Ansichten oder Abschnitten wechseln können.</p>
  </GalleryCard>
</GalleryGrid>

## Dateneingabe {#data-entry}

Dateneingabekomponenten bieten wichtige Werkzeuge zur Erfassung von Benutzereingaben und zur Verwaltung der Interaktionen innerhalb Ihrer App. Diese Komponenten sind vielseitig, sodass es einfach ist, interaktive Formulare zu erstellen und verschiedene Arten von Daten zu sammeln.

<GalleryGrid>
  <GalleryCard header="TextField" href="fields/textfield" image="/img/components/TextField.png">
    <p>Eine Eingabekomponente für die Eingabe und Bearbeitung von Textdaten in einer Zeile.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TextField" href="fields/masked/textfield" image="/img/components/MaskedTextField.png">
    <p>Eine Texteingabekomponente, die die Benutzereingabe auf ein bestimmtes Format oder Muster beschränkt, typischerweise für Felder wie Telefonnummern, Daten oder Kreditkartennummern verwendet.</p>
  </GalleryCard>

  <GalleryCard header="NumberField" href="fields/numberfield" image="/img/components/NumberField.png">
    <p>Eine Komponente, die ein standardmäßiges, browserbasiertes Eingabefeld für die Eingabe von Zahlenwerten bereitstellt, mit integrierten Steuerelementen zum Erhöhen oder Verringern des Wertes.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>NumberField" href="fields/masked/numberfield" image="/img/components/MaskedNumberField.png">
    <p>Eine numerische Eingabekomponente, die die Benutzereingabe auf ein bestimmtes numerisches Format oder Muster beschränkt und so die gültige Eingabe von Zahlen, wie für Währungen, Prozentsätze oder andere formatierte Zahlen, sicherstellt.</p>
  </GalleryCard>

  <GalleryCard header="PasswordField" href="fields/passwordfield" image="/img/components/PasswordField.png">
    <p>Eine Eingabekomponente für die sichere Eingabe und Maskierung von Passwortdaten in einer Zeile.</p>
  </GalleryCard>

  <GalleryCard header="DateField" href="fields/datefield" image="/img/components/DateField.png">
    <p>Eine Komponente, die einen standardmäßigen, browserbasierten Dateiauswähler für die Auswahl eines Datums über ein Eingabefeld bereitstellt.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>DateField" href="fields/masked/datefield" image="/img/components/MaskedDateField.png">
    <p>Eine Datums-Eingabekomponente, die ein bestimmtes Datumsformat oder Muster durchsetzt und sicherstellt, dass der Benutzer ein gültiges Datum gemäß der definierten Maske eingibt.</p>
  </GalleryCard>

  <GalleryCard header="TimeField" href="fields/timefield" image="/img/components/TimeField.png">
    <p>Eine Komponente, die einen standardmäßigen, browserbasierten Zeitabwahl-Widget für die Auswahl eines Zeitwerts über ein Eingabefeld bereitstellt.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TimeField" href="fields/masked/timefield" image="/img/components/MaskedTimeField.png">
    <p>Eine Zeiteingabekomponente, die ein bestimmtes Zeitformat oder Muster durchsetzt und sicherstellt, dass der Benutzer eine gültige Zeit gemäß der definierten Maske eingibt.</p>
  </GalleryCard>

  <GalleryCard header="DateTimeField" href="fields/datetimefield" image="/img/components/DateTimeField.png">
    <p>Eine Komponente, die einen standardmäßigen, browserbasierten Datum- und Uhrzeitwähler für die Auswahl sowohl von Datum als auch Uhrzeit über ein einzelnes Eingabefeld bereitstellt.</p>
  </GalleryCard>

  <GalleryCard header="ColorField" href="fields/colorfield" image="/img/components/ColorField.png">
    <p>Eine Komponente, die einen standardmäßigen, browserbasierten Farbwähler bereitstellt, damit Benutzer eine Farbe aus einem Eingabefeld auswählen können.</p>
  </GalleryCard>

  <GalleryCard header="TextArea" href="textarea" image="/img/components/TextArea.png">
    <p>Eine mehrzeilige Texteingabekomponente, die es Benutzern ermöglicht, größere Textblöcke einzugeben oder zu bearbeiten.</p>
  </GalleryCard>

  <GalleryCard header="CheckBox" href="checkbox" image="/img/components/CheckBox.png">
    <p>Eine Komponente, die eine binäre Option darstellt und es Benutzern ermöglicht, zwischen einem aktivierten (wahr) oder deaktivierten (falsch) Zustand umzuschalten.</p>
  </GalleryCard>

  <GalleryCard header="RadioButton" href="radiobutton" image="/img/components/RadioButton.png">
    <p>Eine Komponente, die es Benutzern ermöglicht, eine einzelne Option aus einer Gruppe von gegenseitig ausschließenden Auswahlmöglichkeiten auszuwählen.</p>
  </GalleryCard>

  <GalleryCard header="Switch" href="radiobutton#switches" image="/img/components/Switch.png">
    <p>Eine Toggle-Komponente, die es Benutzern ermöglicht, zwischen zwei Zuständen, wie ein/ausschalten oder wahr/falsch, mit einer Schiebeaktion zu wechseln.</p>
  </GalleryCard>

  <GalleryCard header="ChoiceBox" href="lists/choicebox" image="/img/components/ChoiceBox.png">
    <p>Eine Komponente, die eine Dropdown-Liste vordefinierter Optionen bereitstellt, die es Benutzern ermöglicht, eine Option aus der Liste auszuwählen.</p>
  </GalleryCard>

  <GalleryCard header="ComboBox" href="lists/combobox" image="/img/components/ComboBox.png">
    <p>Eine Komponente, die eine Dropdown-Liste mit einem editierbaren Texteingabefeld kombiniert, sodass Benutzer entweder eine Option aus der Liste auswählen oder einen benutzerdefinierten Wert eingeben können.</p>
  </GalleryCard>

  <GalleryCard header="ListBox" href="lists/listbox" image="/img/components/ListBox.png">
    <p>Eine Komponente, die eine scrollbare Liste von Optionen anzeigt und es Benutzern ermöglicht, ein oder mehrere Elemente aus der Liste auszuwählen.</p>
  </GalleryCard>
</GalleryGrid>

## Optionsdialoge {#option-dialogs}

Optionsdialoge bieten eine Möglichkeit, den Benutzern choices oder Aufforderungen zur Bestätigung zu präsentieren, bevor sie mit einer Aktion fortfahren. Diese Komponenten sind entscheidend für die Erstellung interaktiver, entscheidungsorientierter Arbeitsabläufe, die es Benutzern ermöglichen, zu bestätigen, abzubrechen oder aus verschiedenen Optionen in einer klaren und strukturierten Weise zu wählen.

<GalleryGrid>
  <GalleryCard header="MessageDialog" href="option-dialogs/message" image="/img/components/MessageDialog.png">
    <p>Eine Dialogkomponente, die verwendet wird, um Informationen oder Warnmeldungen an den Benutzer anzuzeigen, in der Regel mit einem einzelnen `OK`-Knopf zur Bestätigung der Nachricht.</p>
  </GalleryCard>

  <GalleryCard header="ConfirmDialog" href="option-dialogs/confirm" image="/img/components/ConfirmDialog.png">
    <p>Eine Dialogkomponente, die den Benutzer auffordert, eine Aktion zu bestätigen oder abzubrechen, typischerweise mit den Schaltflächen `Ja` und `Nein` oder `OK` und `Abbrechen`.</p>
  </GalleryCard>
  
  <GalleryCard header="InputDialog" href="option-dialogs/input" image="/img/components/InputDialog.png">
    <p>Eine Dialogkomponente, die den Benutzer auffordert, Text oder Daten einzugeben, typischerweise mit einem Eingabefeld und Aktionsschaltflächen wie `OK` und `Abbrechen`.</p>
  </GalleryCard>

  <GalleryCard header="FileChooserDialog" href="option-dialogs/file-chooser" image="/img/components/FileChooserDialog.png">
    <p>Eine Dialogkomponente, die es den Benutzern ermöglicht, Dateien im Server-Dateisystem zu durchsuchen und auszuwählen.</p>
  </GalleryCard>

  <GalleryCard header="FileUploadDialog" href="option-dialogs/file-upload" image="/img/components/FileUploadDialog.png">
    <p>Eine Dialogkomponente, die es den Benutzern ermöglicht, Dateien von ihrem lokalen Dateisystem in die App hochzuladen.</p>
  </GalleryCard>

  <GalleryCard header="FileSaveDialog" href="option-dialogs/file-save" image="/img/components/FileSaveDialog.png">
    <p>Eine Dialogkomponente, die es den Benutzern ermöglicht, eine Datei an einem bestimmten Ort im Server-Dateisystem zu speichern.</p>
  </GalleryCard>
</GalleryGrid>

## Interaktion und Anzeige {#interaction-and-display}

Diese Kategorie umfasst Komponenten, die Benutzereingaben erleichtern und Daten oder Anwendungszustände visuell anzeigen. Diese Komponenten helfen Benutzern, sich in der App zurechtzufinden, Aktionen auszulösen und Fortschritte oder Ergebnisse durch dynamische visuelle Elemente zu verstehen.

<GalleryGrid>
  <GalleryCard header="Table" href="table/overview" image="/img/components/Table.png">
    <p>Eine Komponente, die Daten in einem strukturierten, tabellarischen Format mit Zeilen und Spalten anzeigt und Funktionen wie Sortierung und Pagination unterstützt.</p>
  </GalleryCard>

  <GalleryCard header="GoogleCharts" href="google-charts" image="/img/components/GoogleCharts.png">
    <p>Eine Komponente, die mit Google Charts integriert, um verschiedene Arten von Diagrammen und visuellen Datenrepräsentationen in der App anzuzeigen.</p>
  </GalleryCard>

  <GalleryCard header="Button" href="button" image="/img/components/Button.png">
    <p>Eine klickbare Komponente, die eine Aktion oder ein Ereignis auslöst, wenn sie gedrückt wird.</p>
  </GalleryCard>

  <GalleryCard header="Toast" href="toast" image="/img/components/Toast.png"  effect="slideUp">
    <p>Eine leichtgewichtige, nicht-blockierende Benachrichtigungskomponente, die kurz eine Nachricht an den Benutzer anzeigt, bevor sie automatisch verschwindet.</p>
  </GalleryCard>

  <GalleryCard header="Alert" href="alert" image="/img/components/Alert.png">
    <p>Eine Komponente, die wichtige Nachrichten oder Warnungen in einem bemerkbaren Format anzeigt, um die Aufmerksamkeit des Benutzers zu erregen.</p>
  </GalleryCard>

  <GalleryCard header="DesktopNotification" href="desktop-notification" image="/img/components/DesktopNotification.png">
    <p>Eine Komponente, die die native Benachrichtigungs-API des Browsers nutzt, um Benutzern benutzerdefinierte Desktop-Benachrichtigungen anzuzeigen.</p>
  </GalleryCard>
  
  <GalleryCard header="Navigator" href="navigator" image="/img/components/Navigator.png">
    <p>Eine anpassbare Paginierungs-Komponente zur Navigation durch Datensätze, die Layouts mit ersten, letzten, nächsten, vorherigen Schaltflächen und schnellen Sprungfeldern unterstützt.</p>
  </GalleryCard>

  <GalleryCard header="ProgressBar" href="progressbar" image="/img/components/ProgressBar.png">
    <p>Eine Komponente, die den Fortschritt einer Aufgabe oder eines Prozesses visuell darstellt und typischerweise als horizontale Leiste angezeigt wird, die sich mit dem Fortschritt füllt.</p>
  </GalleryCard>

  <GalleryCard header="Slider" href="slider" image="/img/components/Slider.png">
    <p>Eine Komponente, die es Benutzern ermöglicht, einen Wert aus einem definierten Bereich auszuwählen, indem sie einen Griff entlang einer Strecke ziehen.</p>
  </GalleryCard>

  <GalleryCard header="BusyIndicator" href="busyindicator" image="/img/components/BusyIndicator.png">
    <p>Ein app-weites visuelles Indiz, typischerweise ein Spinner, das signalisiert, dass ein globaler Prozess im Gange ist.</p>
  </GalleryCard>

  <GalleryCard header="Loading" href="loading" image="/img/components/Loading.png">
    <p>Ein lokalisierter Ladeindikator, der innerhalb einer bestimmten übergeordneten Komponente angezeigt wird und anzeigt, dass Inhalte oder Daten in diesem Abschnitt geladen werden.</p>
  </GalleryCard>

  <GalleryCard header="Spinner" href="spinner" image="/img/components/Spinner.png">
    <p>Eine Komponente, die eine rotierende Animation anzeigt und typischerweise verwendet wird, um anzuzeigen, dass ein Prozess oder eine Aktion im Gange ist.</p>
  </GalleryCard>

  <GalleryCard header="AppNav" href="appnav" image="/img/components/AppNav.png" effect="slideFromLeft">
    <p>Eine Komponente, die ein Navigationsmenü für die App bereitstellt, das typischerweise verwendet wird, um Links oder Navigationselemente aufzulisten, um zwischen verschiedenen Abschnitten oder Ansichten zu wechseln.</p>
  </GalleryCard>

  <GalleryCard header="Icon" href="icon" image="/img/components/Icons.png">
    <p>Eine Komponente, die ein grafisches Symbol oder Bild anzeigt, oft verwendet, um eine Aktion, einen Status oder eine Kategorie in der Benutzeroberfläche darzustellen.</p>
  </GalleryCard>

  <GalleryCard header="Terminal" href="terminal" image="/img/components/Terminal.png">
    <p>Eine Komponente, die eine Befehlszeilenschnittstelle (CLI) innerhalb der App simuliert und es Benutzern ermöglicht, textbasierte Befehle einzugeben und auszuführen.</p>
  </GalleryCard>
  
  <GalleryCard header="InfiniteScroll" href="infinitescroll" image="/img/components/InfiniteScroll.png">
    <p>Eine Komponente, die beim Scrollen weitere Elemente lädt, einen Lader anzeigt und verfolgt, wann alle Inhalte abgerufen wurden.</p>
  </GalleryCard>

  <GalleryCard header="Refresher" href="refresher" image="/img/components/Refresher.png">
    <p>Eine Komponente, die eine Pull-to-Refresh-Interaktion innerhalb von scrollbaren Containern ermöglicht – ideal für dynamisches Laden von Daten.</p>
  </GalleryCard>

  <GalleryCard header="Tree" href="tree" image="/img/components/Tree.png">
    <p>Eine Komponente zur Anzeige hierarchischer Daten, die es Benutzern ermöglicht, verschachtelte Elemente zu erweitern, zusammenzuklappen und damit zu interagieren.</p>
  </GalleryCard>
  
  <GalleryCard header="Avatar" href="avatar" image="/img/components/Avatar.png">
    <p>Eine Komponente zur Anzeige von Benutzerprofilbildern oder Initialen, die unterschiedliche Größen, Formen und Themen unterstützt.</p>
  </GalleryCard>
  
  <GalleryCard header="MarkdownViewer" href="markdownviewer" image="/img/components/MarkdownViewer.png">
    <p>Eine Komponente zur Anzeige von Markdown-Inhalten mit progressiver Zeichen-für-Zeichen-Darstellung, ideal für KI-Chat-Oberflächen und Streaming-Text.</p>
  </GalleryCard>
  
</GalleryGrid>
