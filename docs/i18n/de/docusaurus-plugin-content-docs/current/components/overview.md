---
title: UI Components
sidebar_position: 85
hide_table_of_contents: true
sidebar_class_name: has-new-content
hide_giscus_comments: true
_i18n_hash: c463da47b9db7f6619c8723b6105f9bd
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<Head>
  <title>UI-Komponenten | Komponenten zum Erstellen von Benutzeroberflächenanwendungen</title>
</Head>

In webforJ werden Anwendungen mithilfe von modularen Einheiten, die als Komponenten bekannt sind, erstellt, was eine schnelle und effiziente UI-Entwicklung erleichtert. Das Framework bietet eine Reihe von wesentlichen Komponenten wie Schaltflächen, Eingabeelemente und Layout-Container. Nachdem Sie die Grundlagen beherrschen, können Sie die [JavaDocs](https://javadoc.io/doc/com.webforj) für einen detaillierten Überblick über alle Komponenten und deren Funktionen konsultieren.

## Layouts {#layouts}

Layout-Komponenten bilden die Grundlage für die Strukturierung von Benutzeroberflächen und ermöglichen Entwicklern, Inhalte effizient zu organisieren. Diese Komponenten bieten verschiedene Möglichkeiten zur Steuerung der Anordnung von untergeordneten Komponenten, sowohl für einfache als auch für komplexe Layouts.

Die folgenden Layout-Komponenten sind darauf ausgelegt, eine Vielzahl von Anwendungsfällen zu bearbeiten, von responsive Designs bis hin zu fortgeschrittener Inhaltsverwaltung.

<GalleryGrid>
  <GalleryCard header="AppLayout" href="app-layout" image="/img/components/AppLayout.png">
    <p>Eine Container-Komponente, die ein strukturiertes Layout für die Navigation und Organisation des Inhalts der Anwendung auf oberster Ebene bereitstellt.</p>
  </GalleryCard>

  <GalleryCard header="Toolbar" href="toolbar" image="/img/components/Toolbar.png">
    <p>Eine horizontale Container-Komponente, die eine Reihe von Aktionsknöpfen, Symbolen oder anderen Steuerungen enthält, die normalerweise für Aufgaben verwendet werden, die mit dem aktuellen Kontext zusammenhängen.</p>
  </GalleryCard>

  <GalleryCard header="FlexLayout" href="flex-layout" image="/img/components/FlexLayout.png">
    <p>Eine Layout-Komponente, die ihre Kinder mithilfe von flexiblen Boxregeln (Flexbox) für responsive Designs und Ausrichtungen anordnet.</p>
  </GalleryCard>

  <GalleryCard header="ColumnsLayout" href="columns-layout" image="/img/components/ColumnsLayout.png">
    <p>Eine Layout-Komponente, die ihre Kinder in mehrere vertikale Spalten anordnet, nützlich zum Erstellen von Formularen und gitterähnlichen Strukturen.</p>
  </GalleryCard>

  <GalleryCard header="Splitter" href="splitter" image="/img/components/Splitter.png" effect="slideLeftRightScale">
    <p>Eine Layout-Komponente, die den verfügbaren Platz zwischen zwei untergeordneten Komponenten aufteilt und es Benutzern ermöglicht, sie durch Ziehen der Trennleiste zu ändern.</p>
  </GalleryCard>

  <GalleryCard header="Drawer" href="drawer" image="/img/components/Drawer.png" effect="slideUp">
    <p>Eine Schiebetafel-Komponente, die normalerweise für die Seitennavigation verwendet wird oder zusätzlichen Inhalt beherbergt, der angezeigt oder verborgen werden kann.</p>
  </GalleryCard>

  <GalleryCard header="Dialog" href="dialog" image="/img/components/Dialog.png">
    <p>Eine modale Fensterkomponente, die Inhalte überlagert, um wichtige Informationen anzuzeigen oder eine Benutzerinteraktion anzufordern, wobei häufig eine Benutzeraktion zum Schließen erforderlich ist.</p>
  </GalleryCard>

  <GalleryCard header="Login" href="login" image="/img/components/Login.png">
    <p>Eine Komponente, die eine vorgefertigte Benutzeroberfläche für die Benutzerauthentifizierung bereitstellt, die normalerweise Felder für Benutzername und Passwort sowie einen Absenden-Button enthält.</p>
  </GalleryCard>

  <GalleryCard header="TabbedPane" href="tabbedpane" image="/img/components/TabbedPane.png">
    <p>Eine Container-Komponente, die Inhalte in mehrere Tabs organisiert und es Benutzern ermöglicht, zwischen verschiedenen Ansichten oder Abschnitten zu wechseln.</p>
  </GalleryCard>
</GalleryGrid>

## Dateneingabe {#data-entry}

Dateneingabekomponenten bieten die wesentlichen Werkzeuge zum Erfassen von Benutzereingaben und zur Verwaltung von Interaktionen in Ihrer App. Diese Komponenten sind vielseitig und ermöglichen es Ihnen, interaktive Formulare zu erstellen und verschiedene Arten von Daten zu sammeln.

<GalleryGrid>
  <GalleryCard header="TextField" href="fields/textfield" image="/img/components/TextField.png">
    <p>Eine Eingabekomponente für die Eingabe und Bearbeitung von Textdaten in einer Zeile.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TextField" href="fields/masked/textfield" image="/img/components/MaskedTextField.png">
    <p>Eine Text Eingabekomponente, die die Benutzereingabe auf ein bestimmtes Format oder Muster einschränkt, typischerweise verwendet für Felder wie Telefonnummern, Daten oder Kreditkartennummern.</p>
  </GalleryCard>

  <GalleryCard header="NumberField" href="fields/numberfield" image="/img/components/NumberField.png">
    <p>Eine Komponente, die ein standardmäßiges, browserbasiertes Eingabefeld für die Eingabe von numerischen Werten bereitstellt, mit integrierten Steuerungen zum Erhöhen oder Verringern des Wertes.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>NumberField" href="fields/masked/numberfield" image="/img/components/MaskedNumberField.png">
    <p>Eine numerische Eingabekomponente, die die Benutzereingabe auf ein bestimmtes numerisches Format oder Muster einschränkt, um eine gültige Eingabe von Zahlen wie Währung, Prozentsätzen oder anderen formatierten Zahlen sicherzustellen.</p>
  </GalleryCard>

  <GalleryCard header="PasswordField" href="fields/passwordfield" image="/img/components/PasswordField.png">
    <p>Eine Eingabekomponente in einer Zeile zum sicheren Eingeben und Maskieren von Passwortdaten.</p>
  </GalleryCard>

  <GalleryCard header="DateField" href="fields/datefield" image="/img/components/DateField.png">
    <p>Eine Komponente, die einen standardmäßigen, browserbasierten Datumswähler für die Auswahl eines Datums über ein Eingabefeld bereitstellt.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>DateField" href="fields/masked/datefield" image="/img/components/MaskedDateField.png">
    <p>Eine Datum Eingabekomponente, die ein bestimmtes Datumsformat oder Muster erzwingt und sicherstellt, dass der Benutzer ein gültiges Datum gemäß der definierten Maske eingibt.</p>
  </GalleryCard>

  <GalleryCard header="TimeField" href="fields/timefield" image="/img/components/TimeField.png">
    <p>Eine Komponente, die einen standardmäßigen, browserbasierten Zeitwähler für die Auswahl eines Zeitwerts über ein Eingabefeld bereitstellt.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TimeField" href="fields/masked/timefield" image="/img/components/MaskedTimeField.png">
    <p>Eine Eingabekomponente für die Zeit, die ein bestimmtes Zeitformat oder Muster erzwingt, um sicherzustellen, dass der Benutzer eine gültige Zeit gemäß der definierten Maske eingibt.</p>
  </GalleryCard>

  <GalleryCard header="DateTimeField" href="fields/datetimefield" image="/img/components/DateTimeField.png">
    <p>Eine Komponente, die einen standardmäßigen, browserbasierten Datum- und Zeitwähler für die Auswahl von Datum und Uhrzeit über ein einzelnes Eingabefeld bereitstellt.</p>
  </GalleryCard>

  <GalleryCard header="ColorField" href="fields/colorfield" image="/img/components/ColorField.png">
    <p>Eine Komponente, die einen standardmäßigen, browserbasierten Farbwähler bereitstellt, mit dem Benutzer eine Farbe aus einem Eingabefeld auswählen können.</p>
  </GalleryCard>

  <GalleryCard header="TextArea" href="textarea" image="/img/components/TextArea.png">
    <p>Eine mehrzeilige Texteingabekomponente, die es Benutzern ermöglicht, größere Textblöcke einzugeben oder zu bearbeiten.</p>
  </GalleryCard>

  <GalleryCard header="CheckBox" href="checkbox" image="/img/components/CheckBox.png">
    <p>Eine Komponente, die eine binäre Option darstellt und es Benutzern ermöglicht, zwischen einem aktivierten (wahr) oder deaktivierten (falsch) Zustand umzuschalten.</p>
  </GalleryCard>

  <GalleryCard header="RadioButton" href="radiobutton" image="/img/components/RadioButton.png">
    <p>Eine Komponente, die es Benutzern ermöglicht, eine einzelne Option aus einer Gruppe von gegenseitig ausschließenden Auswahlmöglichkeiten zu wählen.</p>
  </GalleryCard>

  <GalleryCard header="Switch" href="radiobutton#switches" image="/img/components/Switch.png">
    <p>Eine Umschaltekomponente, die es Benutzern ermöglicht, zwischen zwei Zuständen, wie ein/aus oder wahr/falsch, mit einer Schiebebewegung zu wechseln.</p>
  </GalleryCard>

  <GalleryCard header="ChoiceBox" href="lists/choicebox" image="/img/components/ChoiceBox.png">
    <p>Eine Komponente, die eine Dropdown-Liste mit vordefinierten Optionen bereitstellt, damit Benutzer eine Option aus der Liste auswählen können.</p>
  </GalleryCard>

  <GalleryCard header="ComboBox" href="lists/combobox" image="/img/components/ComboBox.png">
    <p>Eine Komponente, die eine Dropdown-Liste mit einem bearbeitbaren Texteingabefeld kombiniert, damit Benutzer entweder eine Option aus der Liste auswählen oder einen benutzerdefinierten Wert eingeben können.</p>
  </GalleryCard>

  <GalleryCard header="ListBox" href="lists/listbox" image="/img/components/ListBox.png">
    <p>Eine Komponente, die eine scrollbare Liste von Optionen anzeigt und es Benutzern ermöglicht, ein oder mehrere Elemente aus der Liste auszuwählen.</p>
  </GalleryCard>
</GalleryGrid>

## Optionsdialoge {#option-dialogs}

Optionsdialoge bieten eine Möglichkeit, Benutzern Auswahlmöglichkeiten zu präsentieren oder sie zur Bestätigung aufzufordern, bevor sie mit einer Aktion fortfahren. Diese Komponenten sind entscheidend für die Erstellung interaktiver, entscheidungsbasierter Arbeitsabläufe, die es Benutzern ermöglichen, aus verschiedenen Optionen klar und strukturiert zu bestätigen, abzulehnen oder auszuwählen.

<GalleryGrid>
  <GalleryCard header="MessageDialog" href="option-dialogs/message" image="/img/components/MessageDialog.png">
    <p>Eine Dialog-Komponente, die verwendet wird, um Informationsnachrichten oder Warnungen an den Benutzer anzuzeigen, normalerweise mit einer einzigen Schaltfläche `OK`, um die Nachricht anzuerkennen.</p>
  </GalleryCard>

  <GalleryCard header="ConfirmDialog" href="option-dialogs/confirm" image="/img/components/ConfirmDialog.png">
    <p>Eine Dialog-Komponente, die den Benutzer auffordert, eine Aktion zu bestätigen oder abzubrechen, normalerweise mit den Schaltflächen `Ja` und `Nein` oder `OK` und `Abbrechen`.</p>
  </GalleryCard>
  
  <GalleryCard header="InputDialog" href="option-dialogs/input" image="/img/components/InputDialog.png">
    <p>Eine Dialog-Komponente, die den Benutzer auffordert, Text oder Daten einzugeben, normalerweise mit einem Eingabefeld und Aktionsschaltflächen wie `OK` und `Abbrechen`.</p>
  </GalleryCard>

  <GalleryCard header="FileChooserDialog" href="option-dialogs/file-chooser" image="/img/components/FileChooserDialog.png">
    <p>Eine Dialog-Komponente, die es Benutzern ermöglicht, Dateien vom Server-Dateisystem zu durchsuchen und auszuwählen.</p>
  </GalleryCard>

  <GalleryCard header="FileUploadDialog" href="option-dialogs/file-upload" image="/img/components/FileUploadDialog.png">
    <p>Eine Dialog-Komponente, die es Benutzern ermöglicht, Dateien von ihrem lokalen Dateisystem in die App hochzuladen.</p>
  </GalleryCard>

  <GalleryCard header="FileSaveDialog" href="option-dialogs/file-save" image="/img/components/FileSaveDialog.png">
    <p>Eine Dialog-Komponente, die es Benutzern ermöglicht, eine Datei an einem bestimmten Speicherort im Server-Dateisystem zu speichern.</p>
  </GalleryCard>
</GalleryGrid>

## Interaktion und Anzeige {#interaction-and-display}

Diese Kategorie umfasst Komponenten, die Benutzereingaben erleichtern und Daten oder App-Zustände visuell anzeigen. Diese Komponenten helfen Benutzern, durch die App zu navigieren, Aktionen auszulösen und Fortschritte oder Ergebnisse durch dynamische visuelle Elemente zu verstehen.

<GalleryGrid>
  <GalleryCard header="Table" href="table/overview" image="/img/components/Table.png">
    <p>Eine Komponente, die verwendet wird, um Daten in einem strukturierten, tabellarischen Format mit Zeilen und Spalten anzuzeigen und Funktionen wie Sortierung und Seitenanzahl unterstützt.</p>
  </GalleryCard>

  <GalleryCard header="GoogleCharts" href="google-charts" image="/img/components/GoogleCharts.png">
    <p>Eine Komponente, die sich mit Google Charts integriert, um verschiedene Arten von Diagrammen und visuellen Datenrepräsentationen in der App anzuzeigen.</p>
  </GalleryCard>

  <GalleryCard header="Button" href="button" image="/img/components/Button.png">
    <p>Eine klickbare Komponente, die eine Aktion oder ein Ereignis auslöst, wenn sie gedrückt wird.</p>
  </GalleryCard>

  <GalleryCard header="Toast" href="toast" image="/img/components/Toast.png"  effect="slideUp">
    <p>Eine leichte, nicht blockierende Benachrichtigungs-Komponente, die für kurze Zeit eine Nachricht an den Benutzer anzeigt, bevor sie automatisch verschwindet.</p>
  </GalleryCard>

  <GalleryCard header="Alert" href="alert" image="/img/components/Alert.png">
    <p>Eine Komponente, die wichtige Nachrichten oder Warnungen in einem auffälligen Format anzeigt, um die Aufmerksamkeit des Benutzers zu erregen.</p>
  </GalleryCard>

  <GalleryCard header="DesktopNotification" href="desktop-notification" image="/img/components/DesktopNotification.png">
    <p>Eine Komponente, die die native Benachrichtigungs-API des Browsers nutzt, um Benutzern benutzerdefinierte Desktopbenachrichtigungen anzuzeigen.</p>
  </GalleryCard>
  
  <GalleryCard header="Navigator" href="navigator" image="/img/components/Navigator.png">
    <p>Eine anpassbare Seitenzahl-Komponente für die Navigation durch Datensätze, die Layouts mit Schaltflächen für erstes, letztes, nächstes, vorheriges und schnelle Sprungfelder unterstützt.</p>
  </GalleryCard>

  <GalleryCard header="ProgressBar" href="progressbar" image="/img/components/ProgressBar.png">
    <p>Eine Komponente, die den Fortschritt einer Aufgabe oder eines Prozesses visuell darstellt, normalerweise als horizontale Leiste, die sich füllt, während Fortschritte erzielt werden.</p>
  </GalleryCard>

  <GalleryCard header="Slider" href="slider" image="/img/components/Slider.png">
    <p>Eine Komponente, die es Benutzern ermöglicht, einen Wert aus einem definierten Bereich auszuwählen, indem sie einen Griff entlang einer Schiene ziehen.</p>
  </GalleryCard>

  <GalleryCard header="BusyIndicator" href="busyindicator" image="/img/components/BusyIndicator.png">
    <p>Ein App-weites visuelles Indikator, typischerweise ein Spinner, der signalisiert, dass ein globaler Prozess im Gange ist.</p>
  </GalleryCard>

  <GalleryCard header="Loading" href="loading" image="/img/components/Loading.png">
    <p>Ein eingrenzender Ladeindikator, der innerhalb einer bestimmten übergeordneter Komponente angezeigt wird und darauf hinweist, dass Inhalte oder Daten in diesem Abschnitt geladen werden.</p>
  </GalleryCard>

  <GalleryCard header="Spinner" href="spinner" image="/img/components/Spinner.png">
    <p>Eine Komponente, die eine rotierende Animation anzeigt, die normalerweise verwendet wird, um anzuzeigen, dass ein Prozess oder eine Aktion im Gange ist.</p>
  </GalleryCard>

  <GalleryCard header="AppNav" href="appnav" image="/img/components/AppNav.png" effect="slideFromLeft">
    <p>Eine Komponente, die ein Navigationsmenü für die App bereitstellt, das normalerweise zum Auflisten von Links oder Navigationselementen verwendet wird, um zwischen verschiedenen Abschnitten oder Ansichten zu wechseln.</p>
  </GalleryCard>

  <GalleryCard header="Icon" href="icon" image="/img/components/Icons.png">
    <p>Eine Komponente, die ein grafisches Symbol oder Bild anzeigt, das häufig verwendet wird, um eine Aktion, einen Status oder eine Kategorie in der Benutzeroberfläche darzustellen.</p>
  </GalleryCard>

  <GalleryCard header="Terminal" href="terminal" image="/img/components/Terminal.png">
    <p>Eine Komponente, die eine Befehlszeilenschnittstelle (CLI) innerhalb der App simuliert, sodass Benutzer textbasierte Befehle eingeben und ausführen können.</p>
  </GalleryCard>
  
  <GalleryCard header="InfiniteScroll" href="infinitescroll" image="/img/components/InfiniteScroll.png">
    <p>Eine Komponente, die bei Scrollen weitere Elemente lädt, einen Loader anzeigt und verfolgt, wann alle Inhalte abgerufen wurden.</p>
  </GalleryCard>

  <GalleryCard header="Refresher" href="refresher" image="/img/components/Refresher.png">
    <p>Eine Komponente, die eine Pull-to-Refresh-Interaction innerhalb scrollbarer Container ermöglicht – ideal für dynamisches Laden von Daten.</p>
  </GalleryCard>

  <GalleryCard header="Tree" href="tree" image="/img/components/Tree.png">
    <p>Eine Komponente zur Anzeige hierarchischer Daten, die es Benutzern ermöglicht, Nester zu erweitern, zu reduzieren und mit zu interagieren.</p>
  </GalleryCard>
</GalleryGrid>
