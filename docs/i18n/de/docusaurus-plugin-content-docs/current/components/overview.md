---
title: UI Components
sidebar_position: 85
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Browse the webforJ UI component catalog covering layouts, data entry,
  navigation, feedback, and visualization components.
_i18n_hash: 200027a33988025dba52cd07c34d2e27
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<Head>
  <title>UI-Komponenten | Benutzeroberflächen-Anwendungsbaukomponenten</title>
</Head>

In webforJ werden Anwendungen mit modularen Einheiten, die als Komponenten bekannt sind, entwickelt, die eine schnelle und effiziente UI-Entwicklung ermöglichen. Das Framework bietet eine Reihe wichtiger Komponenten wie Schaltflächen, Eingabeelemente und Layout-Container. Nachdem Sie die Grundlagen beherrschen, können Sie die [JavaDocs](https://javadoc.io/doc/com.webforj) für einen detaillierten Überblick über alle Komponenten und deren Funktionalitäten konsultieren.

## Layouts {#layouts}

Layout-Komponenten bieten die Grundlage zur Strukturierung von Benutzeroberflächen und ermöglichen es Entwicklern, Inhalte effizient zu organisieren. Diese Komponenten bieten verschiedene Möglichkeiten zur Steuerung der Anordnung von Kindkomponenten, unabhängig davon, ob es sich um einfache oder komplexe Layouts handelt.

Die folgenden Layout-Komponenten sind so konzipiert, dass sie eine Vielzahl von Anwendungsfällen abdecken, von responsivem Design bis hin zu fortgeschrittener Inhaltsverwaltung.

<GalleryGrid>
  <GalleryCard header="AppLayout" href="app-layout" image="/img/components/light/AppLayout.webp" imageDark="/img/components/dark/AppLayout.webp">
    <p>Eine Container-Komponente, die ein strukturiertes Layout für die Navigation und Organisation von Inhalten auf oberster Ebene der App bereitstellt.</p>
  </GalleryCard>

  <GalleryCard header="Toolbar" href="toolbar" image="/img/components/light/Toolbar.webp" imageDark="/img/components/dark/Toolbar.webp">
    <p>Eine horizontale Container-Komponente, die eine Reihe von Aktionsschaltflächen, Symbolen oder anderen Steuerelementen enthält, die typischerweise zum Ausführen von Aufgaben im aktuellen Kontext verwendet werden.</p>
  </GalleryCard>

  <GalleryCard header="FlexLayout" href="flex-layout" image="/img/components/light/FlexLayout.webp" imageDark="/img/components/dark/FlexLayout.webp">
    <p>Eine Layout-Komponente, die ihre Kinder mithilfe von flexiblen Boxregeln (Flexbox) für responsives Design und Ausrichtung anordnet.</p>
  </GalleryCard>

  <GalleryCard header="ColumnsLayout" href="columns-layout" image="/img/components/light/ColumnsLayout.webp" imageDark="/img/components/dark/ColumnsLayout.webp">
    <p>Eine Layout-Komponente, die ihre Kinder in mehrere vertikale Spalten anordnet, nützlich für die Erstellung von Formularen und gitterartigen Strukturen.</p>
  </GalleryCard>

  <GalleryCard header="Splitter" href="splitter" image="/img/components/light/Splitter.webp" imageDark="/img/components/dark/Splitter.webp">
    <p>Eine Layout-Komponente, die den verfügbaren Platz zwischen zwei Kindkomponenten aufteilt und es Benutzern ermöglicht, sie durch Ziehen der Trennleiste zu ändern.</p>
  </GalleryCard>

  <GalleryCard header="Drawer" href="drawer" image="/img/components/light/Drawer.webp" imageDark="/img/components/dark/Drawer.webp">
    <p>Ein schiebepanel-Komponente, die typischerweise für die seitliche Navigation oder zur Unterbringung zusätzlicher Inhalte verwendet wird, die ein- oder ausgeblendet werden können.</p>
  </GalleryCard>

  <GalleryCard header="Dialog" href="dialog" image="/img/components/light/Dialog.webp" imageDark="/img/components/dark/Dialog.webp">
    <p>Eine modale Fensterkomponente, die Inhalte überlagert, um wichtige Informationen anzuzeigen oder die Benutzerinteraktion zu fördern, oft mit der Aufforderung, die Eingabe zu bestätigen, um sie zu schließen.</p>
  </GalleryCard>

  <GalleryCard header="Login" href="login" image="/img/components/light/Login.webp" imageDark="/img/components/dark/Login.webp">
    <p>Eine Komponente, die eine vorgefertigte Benutzeroberfläche für die Benutzerauthentifizierung bereitstellt, typischerweise mit Feldern für Benutzernamen und Passwort sowie einem Absenden-Button.</p>
  </GalleryCard>

  <GalleryCard header="Accordion" href="accordion" image="/img/components/light/Accordion.webp" imageDark="/img/components/dark/Accordion.webp">
    <p>Ein vertikal gestapeltes Set von ausklappbaren Panels, von denen jedes eine klickbare Kopfzeile hat, die die Sichtbarkeit seines Inhalts umschaltet.</p>
  </GalleryCard>

  <GalleryCard header="TabbedPane" href="tabbedpane" image="/img/components/light/TabbedPane.webp" imageDark="/img/components/dark/TabbedPane.webp">
    <p>Eine Container-Komponente, die Inhalte in mehrere Registerkarten organisiert, sodass Benutzer zwischen verschiedenen Ansichten oder Abschnitten wechseln können.</p>
  </GalleryCard>

  <GalleryCard header="Card" href="card" image="/img/components/light/Card.webp" imageDark="/img/components/dark/Card.webp">
    <p>Eine Fläche, die verwandte Inhalte und Aktionen gruppiert, mit Bereichen für Medien, Kopfzeilen, Hauptinhalte und Fußzeilen.</p>
  </GalleryCard>
</GalleryGrid>

## Dateneingabe {#data-entry}

Dateneingabekomponenten bieten wichtige Werkzeuge zur Erfassung von Benutzereingaben und zur Verwaltung von Interaktionen innerhalb Ihrer App. Diese Komponenten sind vielseitig und erleichtern den Aufbau interaktiver Formulare und die Erfassung verschiedener Datenarten.

<GalleryGrid>
  <GalleryCard header="TextField" href="fields/textfield" image="/img/components/light/TextField.webp" imageDark="/img/components/dark/TextField.webp">
    <p>Eine Eingabekomponente für die Eingabe und Bearbeitung von Textdaten in einer Zeile.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TextField" href="fields/masked/textfield" image="/img/components/light/MaskedTextField.webp" imageDark="/img/components/dark/MaskedTextField.webp">
    <p>Eine Texteingabekomponente, die die Benutzereingabe auf ein bestimmtes Format oder Muster beschränkt und typischerweise für Felder wie Telefonnummern, Daten oder Kreditkartennummern verwendet wird.</p>
  </GalleryCard>

  <GalleryCard header="NumberField" href="fields/numberfield" image="/img/components/light/NumberField.webp" imageDark="/img/components/dark/NumberField.webp">
    <p>Eine Komponente, die ein standardmäßiges, browserbasiertes Eingabefeld für die Eingabe numerischer Werte bereitstellt, mit integrierten Steuerelementen zum Erhöhen oder Verringern des Werts.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>NumberField" href="fields/masked/numberfield" image="/img/components/light/MaskedNumberField.webp" imageDark="/img/components/dark/MaskedNumberField.webp">
    <p>Eine numerische Eingabekomponente, die die Benutzereingabe auf ein bestimmtes numerisches Format oder Muster beschränkt und eine gültige Zahlen eingabe für Währungen, Prozentsätze oder andere formatierte Zahlen sicherstellt.</p>
  </GalleryCard>

  <GalleryCard header="PasswordField" href="fields/passwordfield" image="/img/components/light/PasswordField.webp" imageDark="/img/components/dark/PasswordField.webp">
    <p>Eine Eingabekomponente für die sichere Eingabe und Maskierung von Passwortdaten in einer Zeile.</p>
  </GalleryCard>

  <GalleryCard header="DateField" href="fields/datefield" image="/img/components/light/DateField.webp" imageDark="/img/components/dark/DateField.webp">
    <p>Eine Komponente, die einen standardmäßigen, browserbasierten Datumswähler für die Auswahl eines Datums über ein Eingabefeld bereitstellt.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>DateField" href="fields/masked/datefield" image="/img/components/light/MaskedDateField.webp" imageDark="/img/components/dark/MaskedDateField.webp">
    <p>Eine Datumseingabekomponente, die ein bestimmtes Datumsformat oder Muster durchsetzt, um sicherzustellen, dass der Benutzer ein gültiges Datum gemäß der definierten Maske eingibt.</p>
  </GalleryCard>

  <GalleryCard header="TimeField" href="fields/timefield" image="/img/components/light/TimeField.webp" imageDark="/img/components/dark/TimeField.webp">
    <p>Eine Komponente, die einen standardmäßigen, browserbasierten Zeitwähler für die Auswahl eines Zeitwerts über ein Eingabefeld bereitstellt.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TimeField" href="fields/masked/timefield" image="/img/components/light/MaskedTimeField.webp" imageDark="/img/components/dark/MaskedTimeField.webp">
    <p>Eine Zeiteingabekomponente, die ein bestimmtes Zeitformat oder Muster durchsetzt, um sicherzustellen, dass der Benutzer eine gültige Zeit gemäß der definierten Maske eingibt.</p>
  </GalleryCard>

  <GalleryCard header="DateTimeField" href="fields/datetimefield" image="/img/components/light/DateTimeField.webp" imageDark="/img/components/dark/DateTimeField.webp">
    <p>Eine Komponente, die einen standardmäßigen, browserbasierten Datum und Zeitwähler für die Auswahl von Datum und Uhrzeit über ein einzelnes Eingabefeld bereitstellt.</p>
  </GalleryCard>

  <GalleryCard header="ColorField" href="fields/colorfield" image="/img/components/light/ColorField.webp" imageDark="/img/components/dark/ColorField.webp">
    <p>Eine Komponente, die einen standardmäßigen, browserbasierten Farb wähler bereitstellt, der es Benutzern ermöglicht, eine Farbe über ein Eingabefeld auszuwählen.</p>
  </GalleryCard>

  <GalleryCard header="TextArea" href="textarea" image="/img/components/light/TextArea.webp" imageDark="/img/components/dark/TextArea.webp">
    <p>Eine mehrzeilige Texteingabekomponente, die es Benutzern ermöglicht, größere Textblöcke einzugeben oder zu bearbeiten.</p>
  </GalleryCard>

  <GalleryCard header="CheckBox" href="checkbox" image="/img/components/light/CheckBox.webp" imageDark="/img/components/dark/CheckBox.webp">
    <p>Eine Komponente, die eine binäre Option darstellt und es Benutzern ermöglicht, zwischen einem aktivierten (wahr) oder deaktivierten (falsch) Zustand umzuschalten.</p>
  </GalleryCard>

  <GalleryCard header="RadioButton" href="radiobutton" image="/img/components/light/RadioButton.webp" imageDark="/img/components/dark/RadioButton.webp">
    <p>Eine Komponente, die es Benutzern ermöglicht, eine einzelne Option aus einer Gruppe von gegenseitig ausschließenden Auswahlmöglichkeiten zu wählen.</p>
  </GalleryCard>

  <GalleryCard header="Switch" href="radiobutton#switches" image="/img/components/light/Switch.webp" imageDark="/img/components/dark/Switch.webp">
    <p>Eine Umschaltkomponente, die es Benutzern ermöglicht, zwischen zwei Zuständen, wie ein/aus oder wahr/falsch, durch eine ziehende Aktion umzuschalten.</p>
  </GalleryCard>

  <GalleryCard header="ChoiceBox" href="lists/choicebox" image="/img/components/light/ChoiceBox.webp" imageDark="/img/components/dark/ChoiceBox.webp">
    <p>Eine Komponente, die eine Dropdown-Liste mit vordefinierten Optionen bereitstellt, sodass Benutzer eine Option aus der Liste auswählen können.</p>
  </GalleryCard>

  <GalleryCard header="ComboBox" href="lists/combobox" image="/img/components/light/ComboBox.webp" imageDark="/img/components/dark/ComboBox.webp">
    <p>Eine Komponente, die eine Dropdown-Liste mit einer editierbaren Texteingabe kombiniert und es Benutzern ermöglicht, entweder eine Option aus der Liste auszuwählen oder einen benutzerdefinierten Wert einzugeben.</p>
  </GalleryCard>

  <GalleryCard header="ListBox" href="lists/listbox" image="/img/components/light/ListBox.webp" imageDark="/img/components/dark/ListBox.webp">
    <p>Eine Komponente, die eine scrollbare Liste von Optionen anzeigt, sodass Benutzer eines oder mehrere Elemente aus der Liste auswählen können.</p>
  </GalleryCard>

  <GalleryCard header="Upload" href="upload" image="/img/components/light/Upload.webp" imageDark="/img/components/dark/Upload.webp">
    <p>Ein inline-Dateiwähler, der es Benutzern ermöglicht, eine oder mehrere Dateien von ihrem lokalen Gerät auszuwählen und an den Server hochzuladen, mit Drag-and-Drop, Filtern und einer Ereignisverfolgung pro Datei.</p>
  </GalleryCard>
</GalleryGrid>

## Optionsdialoge {#option-dialogs}

Optionsdialoge bieten eine Möglichkeit, den Benutzern Auswahlmöglichkeiten zu präsentieren oder sie zur Bestätigung aufzufordern, bevor sie mit einer Aktion fortfahren. Diese Komponenten sind entscheidend für die Erstellung interaktiver, entscheidungsorientierter Workflows, die es den Benutzern ermöglichen, zu bestätigen, abzulehnen oder aus verschiedenen Optionen auf klare und strukturierte Weise zu wählen.

<GalleryGrid>
  <GalleryCard header="MessageDialog" href="option-dialogs/message" image="/img/components/light/MessageDialog.webp" imageDark="/img/components/dark/MessageDialog.webp">
    <p>Eine Dialogkomponente, die verwendet wird, um Informationsnachrichten oder Warnungen an den Benutzer anzuzeigen, typischerweise mit einer einzigen `OK`-Taste zur Bestätigung der Nachricht.</p>
  </GalleryCard>

  <GalleryCard header="ConfirmDialog" href="option-dialogs/confirm" image="/img/components/light/ConfirmDialog.webp" imageDark="/img/components/dark/ConfirmDialog.webp">
    <p>Eine Dialogkomponente, die den Benutzer fragt, ob er eine Aktion bestätigen oder ablehnen möchte, typischerweise mit den Tasten `Ja` und `Nein` oder `OK` und `Abbrechen`.</p>
  </GalleryCard>

  <GalleryCard header="InputDialog" href="option-dialogs/input" image="/img/components/light/InputDialog.webp" imageDark="/img/components/dark/InputDialog.webp">
    <p>Eine Dialogkomponente, die den Benutzer auffordert, Text oder Daten einzugeben, typischerweise mit einem Eingabefeld und Aktionstasten wie `OK` und `Abbrechen`.</p>
  </GalleryCard>

  <GalleryCard header="FileChooserDialog" href="option-dialogs/file-chooser" image="/img/components/light/FileChooserDialog.webp" imageDark="/img/components/dark/FileChooserDialog.webp">
    <p>Eine Dialogkomponente, die es Benutzern ermöglicht, Dateien auf dem Serverdateisystem zu durchsuchen und auszuwählen.</p>
  </GalleryCard>

  <GalleryCard header="FileUploadDialog" href="option-dialogs/file-upload" image="/img/components/light/FileUploadDialog.webp" imageDark="/img/components/dark/FileUploadDialog.webp">
    <p>Eine Dialogkomponente, die es Benutzern ermöglicht, Dateien vom lokalen Dateisystem in die App hochzuladen.</p>
  </GalleryCard>

  <GalleryCard header="FileSaveDialog" href="option-dialogs/file-save" image="/img/components/light/FileSaveDialog.webp" imageDark="/img/components/dark/FileSaveDialog.webp">
    <p>Eine Dialogkomponente, die es Benutzern ermöglicht, eine Datei an einem bestimmten Speicherort im Server-Dateisystem zu speichern.</p>
  </GalleryCard>
</GalleryGrid>

## Interaktion und Anzeige {#interaction-and-display}

Diese Kategorie umfasst Komponenten, die Benutzereingaben erleichtern und Daten oder Anwendungszustände visuell anzeigen. Diese Komponenten helfen Benutzern, in der App zu navigieren, Aktionen auszulösen und Fortschritt oder Ergebnisse durch dynamische visuelle Elemente zu verstehen.

<GalleryGrid>
  <GalleryCard header="Table" href="table/overview" image="/img/components/light/Table.webp" imageDark="/img/components/dark/Table.webp">
    <p>Eine Komponente, die Daten in einem strukturierten, tabellarischen Format mit Zeilen und Spalten anzeigt und Funktionen wie Sortierung und Seitenanzahl unterstützt.</p>
  </GalleryCard>

  <GalleryCard header="GoogleCharts" href="google-charts" image="/img/components/light/GoogleCharts.webp" imageDark="/img/components/dark/GoogleCharts.webp">
    <p>Eine Komponente, die sich mit Google Charts integriert, um verschiedene Arten von Diagrammen und visuellen Daten darzustellen.</p>
  </GalleryCard>

  <GalleryCard header="Button" href="button" image="/img/components/light/Button.webp" imageDark="/img/components/dark/Button.webp">
    <p>Eine klickbare Komponente, die eine Aktion oder ein Ereignis auslöst, wenn sie gedrückt wird.</p>
  </GalleryCard>

  <GalleryCard header="Toast" href="toast" image="/img/components/light/Toast.webp" imageDark="/img/components/dark/Toast.webp">
    <p>Eine leichte, nicht blockierende Benachrichtigungskomponente, die eine Nachricht kurz anzeigt, bevor sie automatisch verschwindet.</p>
  </GalleryCard>

  <GalleryCard header="Alert" href="alert" image="/img/components/light/Alert.webp" imageDark="/img/components/dark/Alert.webp">
    <p>Eine Komponente, die wichtige Nachrichten oder Warnungen in einem auffälligen Format anzeigt, um die Aufmerksamkeit des Benutzers zu erregen.</p>
  </GalleryCard>

  <GalleryCard header="Badge" href="badge" image="/img/components/light/Badge.webp" imageDark="/img/components/dark/Badge.webp">
    <p>Eine kleine Etikettenkomponente zur Anzeige von Zählungen, Status oder kurzen Metadaten, mit Unterstützung für Themen, Größen und Symbole.</p>
  </GalleryCard>

  <GalleryCard header="DesktopNotification" href="desktop-notification" image="/img/components/light/DesktopNotification.webp" imageDark="/img/components/dark/DesktopNotification.webp">
    <p>Eine Komponente, die die native Notification API des Browsers nutzt, um Benutzern benutzerdefinierte Desktop-Benachrichtigungen zu senden.</p>
  </GalleryCard>

  <GalleryCard header="Navigator" href="navigator" image="/img/components/light/Navigator.webp" imageDark="/img/components/dark/Navigator.webp">
    <p>Eine anpassbare Paginierungs-Komponente für die Navigation durch Datensätze, die Layouts mit ersten, letzten, nächsten, vorherigen Schaltflächen und schnellen Sprungfeldern unterstützt.</p>
  </GalleryCard>

  <GalleryCard header="ProgressBar" href="progressbar" image="/img/components/light/ProgressBar.webp" imageDark="/img/components/dark/ProgressBar.webp">
    <p>Eine Komponente, die den Fortschritt einer Aufgabe oder eines Prozesses visuell darstellt, typischerweise als horizontale Leiste, die sich füllt, während Fortschritte erzielt werden.</p>
  </GalleryCard>

  <GalleryCard header="Slider" href="slider" image="/img/components/light/Slider.webp" imageDark="/img/components/dark/Slider.webp">
    <p>Eine Komponente, die es Benutzern ermöglicht, einen Wert aus einem definierten Bereich auszuwählen, indem sie einen Griff entlang einer Leiste ziehen.</p>
  </GalleryCard>

  <GalleryCard header="BusyIndicator" href="busyindicator" image="/img/components/light/BusyIndicator.webp" imageDark="/img/components/dark/BusyIndicator.webp">
    <p>Ein appweites visuelles Indikator, typischerweise ein Spinner, der signalisiert, dass ein globaler Prozess im Gange ist.</p>
  </GalleryCard>

  <GalleryCard header="Loading" href="loading" image="/img/components/light/Loading.webp" imageDark="/img/components/dark/Loading.webp">
    <p>Ein lokalisierter Ladeindikator, der innerhalb einer bestimmten übergeordneten Komponente angezeigt wird und darauf hinweist, dass Inhalte oder Daten in diesem Abschnitt geladen werden.</p>
  </GalleryCard>

  <GalleryCard header="Spinner" href="spinner" image="/img/components/light/Spinner.webp" imageDark="/img/components/dark/Spinner.webp">
    <p>Eine Komponente, die eine rotierende Animation anzeigt, die typischerweise verwendet wird, um darauf hinzuweisen, dass ein Prozess oder eine Aktion im Gange ist.</p>
  </GalleryCard>

  <GalleryCard header="AppNav" href="appnav" image="/img/components/light/AppNav.webp" imageDark="/img/components/dark/AppNav.webp">
    <p>Eine Komponente, die ein Navigationsmenü für die App bereitstellt, typischerweise verwendet, um Links oder Navigationselemente aufzulisten, um zwischen verschiedenen Abschnitten oder Ansichten zu wechseln.</p>
  </GalleryCard>

  <GalleryCard header="Icon" href="icon" image="/img/components/light/Icon.webp" imageDark="/img/components/dark/Icon.webp">
    <p>Eine Komponente, die ein grafisches Symbol oder Bild anzeigt, oft verwendet, um eine Aktion, einen Status oder eine Kategorie in der Benutzeroberfläche darzustellen.</p>
  </GalleryCard>

  <GalleryCard header="Terminal" href="terminal" image="/img/components/light/Terminal.webp" imageDark="/img/components/dark/Terminal.webp">
    <p>Eine Komponente, die eine Befehlszeilenoberfläche (CLI) innerhalb der App simuliert und es Benutzern ermöglicht, textbasierte Befehle einzugeben und auszuführen.</p>
  </GalleryCard>

  <GalleryCard header="InfiniteScroll" href="infinitescroll" image="/img/components/light/InfiniteScroll.webp" imageDark="/img/components/dark/InfiniteScroll.webp">
    <p>Eine Komponente, die beim Scrollen weitere Elemente lädt, einen Ladeindikator anzeigt und verfolgt, wann alle Inhalte abgerufen wurden.</p>
  </GalleryCard>

  <GalleryCard header="Refresher" href="refresher" image="/img/components/light/Refresher.webp" imageDark="/img/components/dark/Refresher.webp">
    <p>Eine Komponente, die eine Pull-to-Refresh-Interaktion innerhalb scrollbarer Container ermöglicht – ideal für dynamisches Laden von Daten.</p>
  </GalleryCard>

  <GalleryCard header="Tree" href="tree" image="/img/components/light/Tree.webp" imageDark="/img/components/dark/Tree.webp">
    <p>Eine Komponente zur Anzeige hierarchischer Daten, die es Benutzern ermöglicht, verschachtelte Elemente zu erweitern, zu minimieren und mit ihnen zu interagieren.</p>
  </GalleryCard>

  <GalleryCard header="Avatar" href="avatar" image="/img/components/light/Avatar.webp" imageDark="/img/components/dark/Avatar.webp">
    <p>Eine Komponente zur Anzeige von Benutzerprofilbildern oder Initialen, mit Unterstützung für verschiedene Größen, Formen und Themen.</p>
  </GalleryCard>

  <GalleryCard header="MarkdownViewer" href="markdownviewer" image="/img/components/light/MarkdownViewer.webp" imageDark="/img/components/dark/MarkdownViewer.webp">
    <p>Eine Komponente zur Anzeige von Markdown-Inhalten mit progressiver Zeichen-für-Zeichen-Darstellung, ideal für AI-Chat-Schnittstellen und Streaming von Text.</p>
  </GalleryCard>

</GalleryGrid>
