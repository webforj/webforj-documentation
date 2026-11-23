---
title: UI-Komponenten
sidebar_position: 85
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Entdecken Sie den Katalog der webforJ-UI-Komponenten zu Layouts, Dateneingabe,
  Navigation, Feedback und Visualisierung.
_i18n_title_hash: 45ac037def4dc03161f6bedffcc3b307
_i18n_description_hash: 97182b12d746c9ed301eeeced3480321
_i18n_hash: 9bc06f09a5141c15a7f4f21d22457571
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<Head>
  <title>Java-UI-Komponenten | webforJ</title>
</Head>

In webforJ werden Anwendungen mithilfe modularer Einheiten, sogenannter Komponenten, erstellt, die eine schnelle und effiziente Entwicklung von Benutzeroberflächen ermöglichen. Das Framework bietet eine Reihe grundlegender Komponenten wie Schaltflächen, Eingabeelemente und Layout-Container. Nachdem Sie die Grundlagen beherrschen, finden Sie in den [JavaDocs](https://javadoc.io/doc/com.webforj) eine detaillierte Übersicht über alle Komponenten und ihre Funktionen.

## Layouts {#layouts}

Layout-Komponenten bilden die Grundlage für die Strukturierung von Benutzeroberflächen und ermöglichen es Entwicklern, Inhalte effizient zu organisieren. Diese Komponenten bieten verschiedene Möglichkeiten, die Anordnung untergeordneter Komponenten zu steuern – sowohl für einfache als auch für komplexe Layouts.

Die folgenden Layout-Komponenten sind für eine Vielzahl von Anwendungsfällen ausgelegt, vom responsiven Design bis hin zur fortgeschrittenen Inhaltsverwaltung.

<GalleryGrid>
  <GalleryCard header="AppLayout" href="app-layout" image="/img/components/light/AppLayout.webp" imageDark="/img/components/dark/AppLayout.webp">
    <p>Eine Container-Komponente, die ein strukturiertes Layout für die Navigation und Inhaltsorganisation auf oberster Ebene der Anwendung bereitstellt.</p>
  </GalleryCard>

  <GalleryCard header="Toolbar" href="toolbar" image="/img/components/light/Toolbar.webp" imageDark="/img/components/dark/Toolbar.webp">
    <p>Eine horizontale Container-Komponente mit einer Reihe von Aktionsschaltflächen, Symbolen oder anderen Steuerelementen, die üblicherweise zum Ausführen von Aufgaben im aktuellen Kontext verwendet werden.</p>
  </GalleryCard>

  <GalleryCard header="FlexLayout" href="flex-layout" image="/img/components/light/FlexLayout.webp" imageDark="/img/components/dark/FlexLayout.webp">
    <p>Eine Layout-Komponente, die ihre untergeordneten Elemente mithilfe der Regeln des Flexbox-Layouts für responsives Design und Ausrichtung anordnet.</p>
  </GalleryCard>

  <GalleryCard header="ColumnsLayout" href="columns-layout" image="/img/components/light/ColumnsLayout.webp" imageDark="/img/components/dark/ColumnsLayout.webp">
    <p>Eine Layout-Komponente, die ihre untergeordneten Elemente in mehreren vertikalen Spalten anordnet und sich zum Erstellen von Formularen und rasterartigen Strukturen eignet.</p>
  </GalleryCard>

  <GalleryCard header="Splitter" href="splitter" image="/img/components/light/Splitter.webp" imageDark="/img/components/dark/Splitter.webp">
    <p>Eine Layout-Komponente, die den verfügbaren Platz zwischen zwei untergeordneten Komponenten aufteilt. Benutzer können deren Größe ändern, indem sie die Trennleiste ziehen.</p>
  </GalleryCard>

  <GalleryCard header="Drawer" href="drawer" image="/img/components/light/Drawer.webp" imageDark="/img/components/dark/Drawer.webp">
    <p>Eine verschiebbare Seitenleiste, die üblicherweise für die Seitennavigation oder für zusätzliche Inhalte verwendet wird, die ein- und ausgeblendet werden können.</p>
  </GalleryCard>

  <GalleryCard header="Dialog" href="dialog" image="/img/components/light/Dialog.webp" imageDark="/img/components/dark/Dialog.webp">
    <p>Ein modales Fenster, das Inhalte überlagert, um wichtige Informationen anzuzeigen oder eine Benutzeraktion anzufordern. Zum Schließen ist häufig eine Aktion erforderlich.</p>
  </GalleryCard>

  <GalleryCard header="Login" href="login" image="/img/components/light/Login.webp" imageDark="/img/components/dark/Login.webp">
    <p>Eine Komponente mit einer vorgefertigten Benutzeroberfläche für die Benutzerauthentifizierung, die üblicherweise Felder für Benutzername und Passwort sowie eine Schaltfläche zum Absenden enthält.</p>
  </GalleryCard>

  <GalleryCard header="Accordion" href="accordion" image="/img/components/light/Accordion.webp" imageDark="/img/components/dark/Accordion.webp">
    <p>Eine vertikal angeordnete Gruppe einklappbarer Bereiche, deren anklickbare Kopfzeilen die Sichtbarkeit des jeweiligen Inhalts umschalten.</p>
  </GalleryCard>

  <GalleryCard header="TabbedPane" href="tabbedpane" image="/img/components/light/TabbedPane.webp" imageDark="/img/components/dark/TabbedPane.webp">
    <p>Eine Container-Komponente, die Inhalte in mehreren Tabs organisiert und es Benutzern ermöglicht, zwischen verschiedenen Ansichten oder Bereichen zu wechseln.</p>
  </GalleryCard>

  <GalleryCard header="Card" href="card" image="/img/components/light/Card.webp" imageDark="/img/components/dark/Card.webp">
    <p>Eine Oberfläche, die zusammengehörige Inhalte und Aktionen gruppiert und Bereiche für Medien, Kopfzeilen, Inhalte und Fußzeilen bietet.</p>
  </GalleryCard>
</GalleryGrid>

## Dateneingabe {#data-entry}

Dateneingabekomponenten bieten wichtige Werkzeuge zum Erfassen von Benutzereingaben und zum Verwalten von Interaktionen innerhalb Ihrer Anwendung. Diese vielseitigen Komponenten erleichtern die Erstellung interaktiver Formulare und das Erfassen verschiedener Datentypen.

<GalleryGrid>
  <GalleryCard header="TextField" href="fields/textfield" image="/img/components/light/TextField.webp" imageDark="/img/components/dark/TextField.webp">
    <p>Eine einzeilige Eingabekomponente zum Eingeben und Bearbeiten von Textdaten.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TextField" href="fields/masked/textfield" image="/img/components/light/MaskedTextField.webp" imageDark="/img/components/dark/MaskedTextField.webp">
    <p>Eine Texteingabekomponente, die Benutzereingaben auf ein bestimmtes Format oder Muster beschränkt. Sie wird üblicherweise für Felder wie Telefonnummern, Datumsangaben oder Kreditkartennummern verwendet.</p>
  </GalleryCard>

  <GalleryCard header="NumberField" href="fields/numberfield" image="/img/components/light/NumberField.webp" imageDark="/img/components/dark/NumberField.webp">
    <p>Eine Komponente mit einem standardmäßigen, browserbasierten Eingabefeld für numerische Werte und integrierten Steuerelementen zum Erhöhen oder Verringern des Werts.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>NumberField" href="fields/masked/numberfield" image="/img/components/light/MaskedNumberField.webp" imageDark="/img/components/dark/MaskedNumberField.webp">
    <p>Eine numerische Eingabekomponente, die Benutzereingaben auf ein bestimmtes Zahlenformat oder Muster beschränkt und so die Eingabe gültiger Zahlen gewährleistet, etwa für Währungen, Prozentsätze oder andere formatierte Zahlen.</p>
  </GalleryCard>

  <GalleryCard header="PasswordField" href="fields/passwordfield" image="/img/components/light/PasswordField.webp" imageDark="/img/components/dark/PasswordField.webp">
    <p>Eine einzeilige Eingabekomponente zur sicheren Eingabe und Maskierung von Passwortdaten.</p>
  </GalleryCard>

  <GalleryCard header="DateField" href="fields/datefield" image="/img/components/light/DateField.webp" imageDark="/img/components/dark/DateField.webp">
    <p>Eine Komponente mit einer standardmäßigen, browserbasierten Datumsauswahl, mit der sich über ein Eingabefeld ein Datum auswählen lässt.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>DateField" href="fields/masked/datefield" image="/img/components/light/MaskedDateField.webp" imageDark="/img/components/dark/MaskedDateField.webp">
    <p>Eine Datumseingabekomponente, die ein bestimmtes Datumsformat oder Muster erzwingt und so sicherstellt, dass der Benutzer gemäß der festgelegten Maske ein gültiges Datum eingibt.</p>
  </GalleryCard>

  <GalleryCard header="TimeField" href="fields/timefield" image="/img/components/light/TimeField.webp" imageDark="/img/components/dark/TimeField.webp">
    <p>Eine Komponente mit einer standardmäßigen, browserbasierten Zeitauswahl, mit der sich über ein Eingabefeld eine Uhrzeit auswählen lässt.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TimeField" href="fields/masked/timefield" image="/img/components/light/MaskedTimeField.webp" imageDark="/img/components/dark/MaskedTimeField.webp">
    <p>Eine Zeiteingabekomponente, die ein bestimmtes Zeitformat oder Muster erzwingt und so sicherstellt, dass der Benutzer gemäß der festgelegten Maske eine gültige Uhrzeit eingibt.</p>
  </GalleryCard>

  <GalleryCard header="DateTimeField" href="fields/datetimefield" image="/img/components/light/DateTimeField.webp" imageDark="/img/components/dark/DateTimeField.webp">
    <p>Eine Komponente mit einer standardmäßigen, browserbasierten Datums- und Zeitauswahl, mit der sich Datum und Uhrzeit über ein einziges Eingabefeld auswählen lassen.</p>
  </GalleryCard>

  <GalleryCard header="ColorField" href="fields/colorfield" image="/img/components/light/ColorField.webp" imageDark="/img/components/dark/ColorField.webp">
    <p>Eine Komponente mit einer standardmäßigen, browserbasierten Farbauswahl, mit der Benutzer über ein Eingabefeld eine Farbe auswählen können.</p>
  </GalleryCard>

  <GalleryCard header="TextArea" href="textarea" image="/img/components/light/TextArea.webp" imageDark="/img/components/dark/TextArea.webp">
    <p>Eine mehrzeilige Texteingabekomponente, mit der Benutzer größere Textblöcke eingeben oder bearbeiten können.</p>
  </GalleryCard>

  <GalleryCard header="CheckBox" href="checkbox" image="/img/components/light/CheckBox.webp" imageDark="/img/components/dark/CheckBox.webp">
    <p>Eine Komponente für eine binäre Option, mit der Benutzer zwischen einem aktivierten (true) und einem deaktivierten (false) Zustand wechseln können.</p>
  </GalleryCard>

  <GalleryCard header="RadioButton" href="radiobutton" image="/img/components/light/RadioButton.webp" imageDark="/img/components/dark/RadioButton.webp">
    <p>Eine Komponente, mit der Benutzer eine einzelne Option aus einer Gruppe sich gegenseitig ausschließender Optionen auswählen können.</p>
  </GalleryCard>

  <GalleryCard header="Switch" href="radiobutton#switches" image="/img/components/light/Switch.webp" imageDark="/img/components/dark/Switch.webp">
    <p>Eine Umschaltkomponente, mit der Benutzer durch eine Schiebebewegung zwischen zwei Zuständen wechseln können, beispielsweise ein/aus oder true/false.</p>
  </GalleryCard>

  <GalleryCard header="ChoiceBox" href="lists/choicebox" image="/img/components/light/ChoiceBox.webp" imageDark="/img/components/dark/ChoiceBox.webp">
    <p>Eine Komponente mit einer Dropdown-Liste vordefinierter Optionen, aus der Benutzer eine Option auswählen können.</p>
  </GalleryCard>

  <GalleryCard header="ComboBox" href="lists/combobox" image="/img/components/light/ComboBox.webp" imageDark="/img/components/dark/ComboBox.webp">
    <p>Eine Komponente, die eine Dropdown-Liste mit einem bearbeitbaren Texteingabefeld kombiniert. Benutzer können entweder eine Option aus der Liste auswählen oder einen eigenen Wert eingeben.</p>
  </GalleryCard>

  <GalleryCard header="ListBox" href="lists/listbox" image="/img/components/light/ListBox.webp" imageDark="/img/components/dark/ListBox.webp">
    <p>Eine Komponente, die eine scrollbare Liste von Optionen anzeigt, aus der Benutzer ein oder mehrere Elemente auswählen können.</p>
  </GalleryCard>

  <GalleryCard header="Upload" href="upload" image="/img/components/light/Upload.webp" imageDark="/img/components/dark/Upload.webp">
    <p>Ein integrierter Dateiauswahldialog, mit dem Benutzer eine oder mehrere Dateien auf ihrem lokalen Gerät auswählen und auf den Server hochladen können. Unterstützt werden Drag-and-drop, Filter und die Ereignisverfolgung für einzelne Dateien.</p>
  </GalleryCard>
</GalleryGrid>

## Auswahldialoge {#option-dialogs}

Auswahldialoge bieten eine Möglichkeit, Benutzern Optionen anzuzeigen oder vor dem Fortfahren mit einer Aktion um Bestätigung zu bitten. Diese Komponenten sind unverzichtbar für interaktive, entscheidungsbasierte Abläufe und ermöglichen es Benutzern, klar und strukturiert zu bestätigen, abzubrechen oder aus verschiedenen Optionen auszuwählen.

<GalleryGrid>
  <GalleryCard header="MessageDialog" href="option-dialogs/message" image="/img/components/light/MessageDialog.webp" imageDark="/img/components/dark/MessageDialog.webp">
    <p>Eine Dialogkomponente zum Anzeigen von Informationen oder Warnmeldungen, üblicherweise mit einer einzelnen Schaltfläche `OK`, um die Meldung zu bestätigen.</p>
  </GalleryCard>

  <GalleryCard header="ConfirmDialog" href="option-dialogs/confirm" image="/img/components/light/ConfirmDialog.webp" imageDark="/img/components/dark/ConfirmDialog.webp">
    <p>Eine Dialogkomponente, in der Benutzer aufgefordert werden, eine Aktion zu bestätigen oder abzubrechen. Üblicherweise enthält sie die Schaltflächen `Yes` und `No` oder `OK` und `Cancel`.</p>
  </GalleryCard>

  <GalleryCard header="InputDialog" href="option-dialogs/input" image="/img/components/light/InputDialog.webp" imageDark="/img/components/dark/InputDialog.webp">
    <p>Eine Dialogkomponente, in der Benutzer zur Eingabe von Text oder Daten aufgefordert werden. Üblicherweise enthält sie ein Eingabefeld sowie Aktionsschaltflächen wie `OK` und `Cancel`.</p>
  </GalleryCard>

  <GalleryCard header="FileChooserDialog" href="option-dialogs/file-chooser" image="/img/components/light/FileChooserDialog.webp" imageDark="/img/components/dark/FileChooserDialog.webp">
    <p>Eine Dialogkomponente, mit der Benutzer Dateien im Serverdateisystem durchsuchen und auswählen können.</p>
  </GalleryCard>

  <GalleryCard header="FileUploadDialog" href="option-dialogs/file-upload" image="/img/components/light/FileUploadDialog.webp" imageDark="/img/components/dark/FileUploadDialog.webp">
    <p>Eine Dialogkomponente, mit der Benutzer Dateien von ihrem lokalen Dateisystem in die Anwendung hochladen können.</p>
  </GalleryCard>

  <GalleryCard header="FileSaveDialog" href="option-dialogs/file-save" image="/img/components/light/FileSaveDialog.webp" imageDark="/img/components/dark/FileSaveDialog.webp">
    <p>Eine Dialogkomponente, mit der Benutzer eine Datei an einem angegebenen Speicherort im Serverdateisystem speichern können.</p>
  </GalleryCard>
</GalleryGrid>

## Interaktion und Anzeige {#interaction-and-display}

Diese Kategorie umfasst Komponenten, die Benutzerinteraktionen ermöglichen und Daten oder Anwendungszustände visuell darstellen. Sie helfen Benutzern dabei, durch die Anwendung zu navigieren, Aktionen auszulösen und den Fortschritt oder Ergebnisse anhand dynamischer visueller Elemente zu verstehen.

<GalleryGrid>
  <GalleryCard header="Table" href="table/overview" image="/img/components/light/Table.webp" imageDark="/img/components/dark/Table.webp">
    <p>Eine Komponente zur strukturierten Darstellung von Daten in Tabellenform mit Zeilen und Spalten, die Funktionen wie Sortierung und Paginierung unterstützt.</p>
  </GalleryCard>

  <GalleryCard header="GoogleCharts" href="google-charts" image="/img/components/light/GoogleCharts.webp" imageDark="/img/components/dark/GoogleCharts.webp">
    <p>Eine Komponente, die Google Charts integriert, um verschiedene Diagrammtypen und visuelle Datendarstellungen in der Anwendung anzuzeigen.</p>
  </GalleryCard>

  <GalleryCard header="Button" href="button" image="/img/components/light/Button.webp" imageDark="/img/components/dark/Button.webp">
    <p>Eine anklickbare Komponente, die beim Drücken eine Aktion oder ein Ereignis auslöst.</p>
  </GalleryCard>

  <GalleryCard header="Toast" href="toast" image="/img/components/light/Toast.webp" imageDark="/img/components/dark/Toast.webp">
    <p>Eine leichtgewichtige, nicht blockierende Benachrichtigungskomponente, die eine Meldung kurz anzeigt und anschließend automatisch ausgeblendet wird.</p>
  </GalleryCard>

  <GalleryCard header="Alert" href="alert" image="/img/components/light/Alert.webp" imageDark="/img/components/dark/Alert.webp">
    <p>Eine Komponente, die wichtige Meldungen oder Warnungen auffällig darstellt, um die Aufmerksamkeit der Benutzer zu gewinnen.</p>
  </GalleryCard>

  <GalleryCard header="Badge" href="badge" image="/img/components/light/Badge.webp" imageDark="/img/components/dark/Badge.webp">
    <p>Eine kleine Beschriftungskomponente zur Anzeige von Zählwerten, Statusangaben oder kurzen Metadaten, die Themes, Größen und Symbole unterstützt.</p>
  </GalleryCard>

  <GalleryCard header="DesktopNotification" href="desktop-notification" image="/img/components/light/DesktopNotification.webp" imageDark="/img/components/dark/DesktopNotification.webp">
    <p>Eine Komponente, die die native Notification-API des Browsers nutzt, um Benutzer mit benutzerdefinierten Desktopbenachrichtigungen zu informieren.</p>
  </GalleryCard>

  <GalleryCard header="Navigator" href="navigator" image="/img/components/light/Navigator.webp" imageDark="/img/components/dark/Navigator.webp">
    <p>Eine anpassbare Paginierungskomponente zum Navigieren durch Datensätze. Sie unterstützt Layouts mit Schaltflächen für erste, letzte, nächste und vorherige Seite sowie Feldern zum direkten Wechseln zu einer Seite.</p>
  </GalleryCard>

  <GalleryCard header="ProgressBar" href="progressbar" image="/img/components/light/ProgressBar.webp" imageDark="/img/components/dark/ProgressBar.webp">
    <p>Eine Komponente, die den Fortschritt einer Aufgabe oder eines Vorgangs visuell darstellt, üblicherweise als horizontaler Balken, der sich mit zunehmendem Fortschritt füllt.</p>
  </GalleryCard>

  <GalleryCard header="Slider" href="slider" image="/img/components/light/Slider.webp" imageDark="/img/components/dark/Slider.webp">
    <p>Eine Komponente, mit der Benutzer einen Wert aus einem festgelegten Bereich auswählen können, indem sie einen Griff entlang einer Schiene ziehen.</p>
  </GalleryCard>

  <GalleryCard header="BusyIndicator" href="busyindicator" image="/img/components/light/BusyIndicator.webp" imageDark="/img/components/dark/BusyIndicator.webp">
    <p>Ein anwendungsweiter visueller Indikator, üblicherweise ein Drehkreis, der signalisiert, dass ein globaler Vorgang ausgeführt wird.</p>
  </GalleryCard>

  <GalleryCard header="Loading" href="loading" image="/img/components/light/Loading.webp" imageDark="/img/components/dark/Loading.webp">
    <p>Ein auf einen bestimmten Bereich beschränkter Ladeindikator, der innerhalb einer übergeordneten Komponente angezeigt wird und signalisiert, dass Inhalte oder Daten in diesem Bereich geladen werden.</p>
  </GalleryCard>

  <GalleryCard header="Spinner" href="spinner" image="/img/components/light/Spinner.webp" imageDark="/img/components/dark/Spinner.webp">
    <p>Eine Komponente, die eine Drehanimation anzeigt und üblicherweise signalisiert, dass ein Vorgang oder eine Aktion ausgeführt wird.</p>
  </GalleryCard>

  <GalleryCard header="AppNav" href="appnav" image="/img/components/light/AppNav.webp" imageDark="/img/components/dark/AppNav.webp">
    <p>Eine Komponente, die ein Navigationsmenü für die Anwendung bereitstellt. Sie wird üblicherweise verwendet, um Links oder Navigationselemente zum Wechseln zwischen verschiedenen Bereichen oder Ansichten aufzulisten.</p>
  </GalleryCard>

  <GalleryCard header="Icon" href="icon" image="/img/components/light/Icon.webp" imageDark="/img/components/dark/Icon.webp">
    <p>Eine Komponente, die ein grafisches Symbol oder Bild anzeigt, das häufig eine Aktion, einen Status oder eine Kategorie in der Benutzeroberfläche darstellt.</p>
  </GalleryCard>

  <GalleryCard header="Terminal" href="terminal" image="/img/components/light/Terminal.webp" imageDark="/img/components/dark/Terminal.webp">
    <p>Eine Komponente, die eine Befehlszeilenschnittstelle (CLI) innerhalb der Anwendung simuliert und es Benutzern ermöglicht, textbasierte Befehle einzugeben und auszuführen.</p>
  </GalleryCard>

  <GalleryCard header="InfiniteScroll" href="infinitescroll" image="/img/components/light/InfiniteScroll.webp" imageDark="/img/components/dark/InfiniteScroll.webp">
    <p>Eine Komponente, die beim Scrollen weitere Elemente lädt, einen Ladeindikator anzeigt und erfasst, wann alle Inhalte geladen wurden.</p>
  </GalleryCard>

  <GalleryCard header="Refresher" href="refresher" image="/img/components/light/Refresher.webp" imageDark="/img/components/dark/Refresher.webp">
    <p>Eine Komponente, die in scrollbaren Containern eine Aktualisierung durch Herunterziehen ermöglicht – ideal zum dynamischen Laden von Daten.</p>
  </GalleryCard>

  <GalleryCard header="Tree" href="tree" image="/img/components/light/Tree.webp" imageDark="/img/components/dark/Tree.webp">
    <p>Eine Komponente zur Darstellung hierarchischer Daten, mit der Benutzer verschachtelte Elemente auf- und zuklappen und mit ihnen interagieren können.</p>
  </GalleryCard>

  <GalleryCard header="Avatar" href="avatar" image="/img/components/light/Avatar.webp" imageDark="/img/components/dark/Avatar.webp">
    <p>Eine Komponente zur Anzeige von Profilbildern oder Initialen, die verschiedene Größen, Formen und Themes unterstützt.</p>
  </GalleryCard>

  <GalleryCard header="MarkdownViewer" href="markdownviewer" image="/img/components/light/MarkdownViewer.webp" imageDark="/img/components/dark/MarkdownViewer.webp">
    <p>Eine Komponente zur Anzeige von Markdown-Inhalten mit schrittweiser Darstellung Zeichen für Zeichen, ideal für KI-Chat-Oberflächen und gestreamte Texte.</p>
  </GalleryCard>

</GalleryGrid>
