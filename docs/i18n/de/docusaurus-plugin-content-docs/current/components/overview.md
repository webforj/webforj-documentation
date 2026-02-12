---
title: UI Components
sidebar_position: 85
hide_table_of_contents: true
sidebar_class_name: has-new-content
hide_giscus_comments: true
_i18n_hash: 80950952d9226a7a35503663c4155da7
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

In webforJ werden Anwendungen mit modularen Einheiten erstellt, die als Komponenten bekannt sind und die schnelle und effiziente UI-Entwicklung ermöglichen. Das Framework bietet eine Reihe von wesentlichen Komponenten wie Schaltflächen, Eingabeelemente und Layout-Container. Nachdem Sie die Grundlagen beherrscht haben, können Sie die [JavaDocs](https://javadoc.io/doc/com.webforj) für einen detaillierten Überblick über alle Komponenten und deren Funktionen konsultieren.

## Layouts {#layouts}

Layoutkomponenten bieten die Grundlage für die Strukturierung von Benutzeroberflächen und ermöglichen es Entwicklern, Inhalte effizient zu organisieren. Diese Komponenten bieten verschiedene Möglichkeiten zur Steuerung der Anordnung von Kindkomponenten, ob für einfache oder komplexe Layouts.

Die folgenden Layoutkomponenten sind darauf ausgelegt, eine Vielzahl von Anwendungsfällen zu bewältigen, von responsive Design bis hin zu fortgeschrittener Inhaltsverwaltung.

<GalleryGrid>
  <GalleryCard header="AppLayout" href="app-layout" image="/img/components/AppLayout.png">
    <p>Eine Containerkomponente, die ein strukturiertes Layout für die Navigation und die Organisation des Inhalts auf oberster Ebene bereitstellt.</p>
  </GalleryCard>

  <GalleryCard header="Toolbar" href="toolbar" image="/img/components/Toolbar.png">
    <p>Eine horizontale Containerkomponente, die eine Reihe von Aktionsschaltflächen, Symbolen oder anderen Steuerungen enthält, die typischerweise zur Durchführung von Aufgaben im aktuellen Kontext verwendet werden.</p>
  </GalleryCard>

  <GalleryCard header="FlexLayout" href="flex-layout" image="/img/components/FlexLayout.png">
    <p>Eine Layoutkomponente, die ihre Kinder mithilfe von flexiblen Boxregeln (Flexbox) für responsive Designs und Ausrichtungen anordnet.</p>
  </GalleryCard>

  <GalleryCard header="ColumnsLayout" href="columns-layout" image="/img/components/ColumnsLayout.png">
    <p>Eine Layoutkomponente, die ihre Kinder in mehrere vertikale Spalten anordnet und nützlich ist, um Formulare und gitterartige Strukturen zu erstellen.</p>
  </GalleryCard>

  <GalleryCard header="Splitter" href="splitter" image="/img/components/Splitter.png" effect="slideLeftRightScale">
    <p>Eine Layoutkomponente, die den verfügbaren Platz zwischen zwei Kindkomponenten aufteilt und es den Benutzern ermöglicht, sie durch Ziehen der Splitterleiste zu ändern.</p>
  </GalleryCard>

  <GalleryCard header="Drawer" href="drawer" image="/img/components/Drawer.png" effect="slideUp">
    <p>Eine schiebende Panelkomponente, die typischerweise für die seitliche Navigation oder das Unterbringen zusätzlicher Inhalte verwendet wird, die angezeigt oder verborgen werden können.</p>
  </GalleryCard>

  <GalleryCard header="Dialog" href="dialog" image="/img/components/Dialog.png">
    <p>Eine modale Fensterkomponente, die Inhalte überlagert, um wichtige Informationen anzuzeigen oder die Interaktion des Benutzers anzufordern, und oft eine Benutzeraktion zum Schließen erfordert.</p>
  </GalleryCard>

  <GalleryCard header="Login" href="login" image="/img/components/Login.png">
    <p>Eine Komponente, die eine vorgefertigte Benutzeroberfläche für die Benutzerauthentifizierung bereitstellt, typischerweise mit Feldern für Benutzernamen und Passwort sowie einer Schaltfläche zum Absenden.</p>
  </GalleryCard>

  <GalleryCard header="TabbedPane" href="tabbedpane" image="/img/components/TabbedPane.png">
    <p>Eine Containerkomponente, die Inhalte in mehreren Reitern organisiert, sodass Benutzer zwischen verschiedenen Ansichten oder Abschnitten wechseln können.</p>
  </GalleryCard>
</GalleryGrid>

## Dateneingabe {#data-entry}

Dateneingabekomponenten bieten wesentliche Werkzeuge zur Erfassung von Benutzereingaben und zur Verwaltung von Interaktionen innerhalb Ihrer App. Diese Komponenten sind vielseitig und ermöglichen es, interaktive Formulare zu erstellen und verschiedene Datentypen zu erfassen.

<GalleryGrid>
  <GalleryCard header="TextField" href="fields/textfield" image="/img/components/TextField.png">
    <p>Eine Eingabekomponente für die Eingabe und Bearbeitung von Textdaten in einer Zeile.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TextField" href="fields/masked/textfield" image="/img/components/MaskedTextField.png">
    <p>Eine Texteingabekomponente, die die Benutzereingabe auf ein bestimmtes Format oder Muster beschränkt und typischerweise für Felder wie Telefonnummern, Daten oder Kreditkartennummern verwendet wird.</p>
  </GalleryCard>

  <GalleryCard header="NumberField" href="fields/numberfield" image="/img/components/NumberField.png">
    <p>Eine Komponente, die ein standardmäßiges browserbasiertes Eingabefeld zur Eingabe numerischer Werte bereitstellt, mit integrierten Steuerelementen zum Erhöhen oder Verringern des Wertes.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>NumberField" href="fields/masked/numberfield" image="/img/components/MaskedNumberField.png">
    <p>Eine numerische Eingabekomponente, die die Benutzereingabe auf ein bestimmtes numerisches Format oder Muster beschränkt und sicherstellt, dass gültige Zahlen wie Währungen, Prozentsätze oder andere formatierte Zahlen eingegeben werden.</p>
  </GalleryCard>

  <GalleryCard header="PasswordField" href="fields/passwordfield" image="/img/components/PasswordField.png">
    <p>Eine Eingabekomponente für die sichere Eingabe und Maskierung von Passwortdaten in einer Zeile.</p>
  </GalleryCard>

  <GalleryCard header="DateField" href="fields/datefield" image="/img/components/DateField.png">
    <p>Eine Komponente, die einen standardmäßigen browserbasierten Datumsauswahl-Dialog bietet, um ein Datum über ein Eingabefeld auszuwählen.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>DateField" href="fields/masked/datefield" image="/img/components/MaskedDateField.png">
    <p>Eine Datumseingabekomponente, die ein bestimmtes Datumsformat oder Muster durchsetzt und sicherstellt, dass der Benutzer ein gültiges Datum gemäß der definierten Maske eingibt.</p>
  </GalleryCard>

  <GalleryCard header="TimeField" href="fields/timefield" image="/img/components/TimeField.png">
    <p>Eine Komponente, die einen standardmäßigen browserbasierten Zeitpicker bietet, um einen Zeitwert über ein Eingabefeld auszuwählen.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TimeField" href="fields/masked/timefield" image="/img/components/MaskedTimeField.png">
    <p>Eine Zeiteingabekomponente, die ein bestimmtes Zeitformat oder Muster durchsetzt und sicherstellt, dass der Benutzer eine gültige Zeit gemäß der definierten Maske eingibt.</p>
  </GalleryCard>

  <GalleryCard header="DateTimeField" href="fields/datetimefield" image="/img/components/DateTimeField.png">
    <p>Eine Komponente, die einen standardmäßigen browserbasierten Datum- und Zeitpicker bietet, um sowohl Datum als auch Uhrzeit über ein einzelnes Eingabefeld auszuwählen.</p>
  </GalleryCard>

  <GalleryCard header="ColorField" href="fields/colorfield" image="/img/components/ColorField.png">
    <p>Eine Komponente, die einen standardmäßigen browserbasierten Farbwähler bereitstellt, der es Benutzern ermöglicht, eine Farbe über ein Eingabefeld auszuwählen.</p>
  </GalleryCard>

  <GalleryCard header="TextArea" href="textarea" image="/img/components/TextArea.png">
    <p>Eine mehrzeilige Texteingabekomponente, die es Benutzern ermöglicht, größere Textabschnitte einzugeben oder zu bearbeiten.</p>
  </GalleryCard>

  <GalleryCard header="CheckBox" href="checkbox" image="/img/components/CheckBox.png">
    <p>Eine Komponente, die eine binäre Option darstellt und es Benutzern ermöglicht, zwischen einem angekreuzten (wahr) oder nicht angekreuzten (falsch) Zustand umzuschalten.</p>
  </GalleryCard>

  <GalleryCard header="RadioButton" href="radiobutton" image="/img/components/RadioButton.png">
    <p>Eine Komponente, die es Benutzern ermöglicht, eine einzelne Option aus einer Gruppe von gegenseitig ausschließenden Auswahlmöglichkeiten auszuwählen.</p>
  </GalleryCard>

  <GalleryCard header="Switch" href="radiobutton#switches" image="/img/components/Switch.png">
    <p>Eine Toggle-Komponente, die es Benutzern ermöglicht, zwischen zwei Zuständen, wie ein/ausschalten oder wahr/falsch, mit einer gleitenden Aktion umzuschalten.</p>
  </GalleryCard>

  <GalleryCard header="ChoiceBox" href="lists/choicebox" image="/img/components/ChoiceBox.png">
    <p>Eine Komponente, die eine Dropdown-Liste von vordefinierten Optionen bereitstellt, die es Benutzern ermöglicht, eine Option aus der Liste auszuwählen.</p>
  </GalleryCard>

  <GalleryCard header="ComboBox" href="lists/combobox" image="/img/components/ComboBox.png">
    <p>Eine Komponente, die eine Dropdown-Liste mit einem bearbeitbaren Texteingang kombiniert, die es Benutzern ermöglicht, entweder eine Option aus der Liste auszuwählen oder einen benutzerdefinierten Wert einzugeben.</p>
  </GalleryCard>

  <GalleryCard header="ListBox" href="lists/listbox" image="/img/components/ListBox.png">
    <p>Eine Komponente, die eine durchscrollbare Liste von Optionen anzeigt und es Benutzern ermöglicht, ein oder mehrere Elemente aus der Liste auszuwählen.</p>
  </GalleryCard>
</GalleryGrid>

## Optionsdialoge {#option-dialogs}

Optionsdialoge bieten eine Möglichkeit, Benutzern Auswahlmöglichkeiten zu präsentieren oder sie um Bestätigung zu bitten, bevor sie mit einer Aktion fortfahren. Diese Komponenten sind entscheidend für die Erstellung interaktiver, entscheidungsorientierter Workflows und ermöglichen es Benutzern, aus verschiedenen Optionen in klarer und strukturierter Weise zu bestätigen, abzubrechen oder auszuwählen.

<GalleryGrid>
  <GalleryCard header="MessageDialog" href="option-dialogs/message" image="/img/components/MessageDialog.png">
    <p>Eine Dialogkomponente, die verwendet wird, um informative Nachrichten oder Warnungen an den Benutzer anzuzeigen, typischerweise mit einer einzelnen `OK`-Schaltfläche zur Bestätigung der Nachricht.</p>
  </GalleryCard>

  <GalleryCard header="ConfirmDialog" href="option-dialogs/confirm" image="/img/components/ConfirmDialog.png">
    <p>Eine Dialogkomponente, die den Benutzer fragt, ob er eine Aktion bestätigen oder abbrechen möchte, typischerweise mit `Ja`- und `Nein`- oder `OK`- und `Abbrechen`-Schaltflächen.</p>
  </GalleryCard>
  
  <GalleryCard header="InputDialog" href="option-dialogs/input" image="/img/components/InputDialog.png">
    <p>Eine Dialogkomponente, die den Benutzer auffordert, Text oder Daten einzugeben, typischerweise mit einem Eingabefeld und Aktionsschaltflächen wie `OK` und `Abbrechen`.</p>
  </GalleryCard>

  <GalleryCard header="FileChooserDialog" href="option-dialogs/file-chooser" image="/img/components/FileChooserDialog.png">
    <p>Eine Dialogkomponente, die es Benutzern ermöglicht, Dateien im Dateisystem des Servers zu durchsuchen und auszuwählen.</p>
  </GalleryCard>

  <GalleryCard header="FileUploadDialog" href="option-dialogs/file-upload" image="/img/components/FileUploadDialog.png">
    <p>Eine Dialogkomponente, die es Benutzern ermöglicht, Dateien von ihrem lokalen Dateisystem in die App hochzuladen.</p>
  </GalleryCard>

  <GalleryCard header="FileSaveDialog" href="option-dialogs/file-save" image="/img/components/FileSaveDialog.png">
    <p>Eine Dialogkomponente, die es Benutzern ermöglicht, eine Datei an einem bestimmten Ort im Dateisystem des Servers zu speichern.</p>
  </GalleryCard>
</GalleryGrid>

## Interaktion und Anzeige {#interaction-and-display}

Diese Kategorie umfasst Komponenten, die Benutzerinteraktionen erleichtern und Daten oder App-Zustände visuell anzeigen. Diese Komponenten helfen Benutzern, durch die App zu navigieren, Aktionen auszulösen und Fortschritte oder Ergebnisse durch dynamische visuelle Elemente zu verstehen.

<GalleryGrid>
  <GalleryCard header="Table" href="table/overview" image="/img/components/Table.png">
    <p> Eine Komponente, die zur Anzeige von Daten in einem strukturierten, tabellarischen Format mit Zeilen und Spalten verwendet wird und Funktionen wie Sortierung und Seitenumbruch unterstützt.</p>
  </GalleryCard>

  <GalleryCard header="GoogleCharts" href="google-charts" image="/img/components/GoogleCharts.png">
    <p>Eine Komponente, die mit Google Charts integriert ist, um verschiedene Arten von Diagrammen und visuellen Datenrepräsentationen in der App anzuzeigen.</p>
  </GalleryCard>

  <GalleryCard header="Button" href="button" image="/img/components/Button.png">
    <p>Eine klickbare Komponente, die eine Aktion oder ein Ereignis auslöst, wenn sie gedrückt wird.</p>
  </GalleryCard>

  <GalleryCard header="Toast" href="toast" image="/img/components/Toast.png" effect="slideUp">
    <p>Eine leichte, nicht-blockierende Benachrichtigungskomponente, die eine Nachricht kurz anzeigt und dann automatisch verschwindet.</p>
  </GalleryCard>

  <GalleryCard header="Alert" href="alert" image="/img/components/Alert.png">
    <p>Eine Komponente, die wichtige Nachrichten oder Warnungen in einem auffälligen Format anzeigt, um die Aufmerksamkeit des Benutzers zu erregen.</p>
  </GalleryCard>

  <GalleryCard header="DesktopNotification" href="desktop-notification" image="/img/components/DesktopNotification.png">
    <p>Eine Komponente, die die native Benachrichtigungs-API des Browsers nutzt, um Benutzern benutzerdefinierte Desktopbenachrichtigungen anzuzeigen.</p>
  </GalleryCard>
  
  <GalleryCard header="Navigator" href="navigator" image="/img/components/Navigator.png">
    <p>Eine anpassbare Paginierungskomponente zur Navigation durch Datensätze, die Layouts mit Ersten, Letzten, Nächsten, Vorherigen Schaltflächen und Schnelljumpfeldern unterstützt.</p>
  </GalleryCard>

  <GalleryCard header="ProgressBar" href="progressbar" image="/img/components/ProgressBar.png">
    <p>Eine Komponente, die den Fortschritt einer Aufgabe oder eines Prozesses visuell darstellt, typischerweise als eine horizontale Leiste, die sich füllt, während Fortschritt erzielt wird.</p>
  </GalleryCard>

  <GalleryCard header="Slider" href="slider" image="/img/components/Slider.png">
    <p>Eine Komponente, die es Benutzern ermöglicht, einen Wert aus einem definierten Bereich auszuwählen, indem sie einen Griff entlang einer Spur ziehen.</p>
  </GalleryCard>

  <GalleryCard header="BusyIndicator" href="busyindicator" image="/img/components/BusyIndicator.png">
    <p> Ein visueller Indikator für die gesamte App, typischerweise ein Spinner, der signalisiert, dass ein globaler Prozess im Gange ist.</p>
  </GalleryCard>

  <GalleryCard header="Loading" href="loading" image="/img/components/Loading.png">
    <p>Ein kontextueller Ladeindikator, der innerhalb einer bestimmten übergeordneten Komponente angezeigt wird und darauf hinweist, dass Inhalte oder Daten in diesem Abschnitt geladen werden.</p>
  </GalleryCard>

  <GalleryCard header="Spinner" href="spinner" image="/img/components/Spinner.png">
    <p>Eine Komponente, die eine rotierende Animation anzeigt, die typischerweise verwendet wird, um anzuzeigen, dass ein Prozess oder eine Aktion im Gange ist.</p>
  </GalleryCard>

  <GalleryCard header="AppNav" href="appnav" image="/img/components/AppNav.png" effect="slideFromLeft">
    <p>Eine Komponente, die ein Navigationsmenü für die App bereitstellt und typischerweise dazu verwendet wird, Links oder Navigationselemente aufzulisten, um zwischen verschiedenen Abschnitten oder Ansichten zu wechseln.</p>
  </GalleryCard>

  <GalleryCard header="Icon" href="icon" image="/img/components/Icons.png">
    <p>Eine Komponente, die ein grafisches Symbol oder Bild anzeigt, das häufig verwendet wird, um eine Aktion, einen Status oder eine Kategorie in der Benutzeroberfläche darzustellen.</p>
  </GalleryCard>

  <GalleryCard header="Terminal" href="terminal" image="/img/components/Terminal.png">
    <p>Eine Komponente, die eine Befehlszeilenschnittstelle (CLI) innerhalb der App simuliert und es Benutzern ermöglicht, textbasierte Befehle einzugeben und auszuführen.</p>
  </GalleryCard>
  
  <GalleryCard header="InfiniteScroll" href="infinitescroll" image="/img/components/InfiniteScroll.png">
    <p>Eine Komponente, die beim Scrollen mehr Elemente lädt, einen Loader anzeigt und verfolgt, wann alle Inhalte abgerufen wurden.</p>
  </GalleryCard>

  <GalleryCard header="Refresher" href="refresher" image="/img/components/Refresher.png">
    <p>Eine Komponente, die eine Pull-to-Refresh-Interaktion innerhalb von scrollbaren Containern zulässt – ideal für das dynamische Laden von Daten.</p>
  </GalleryCard>

  <GalleryCard header="Tree" href="tree" image="/img/components/Tree.png">
    <p>Eine Komponente zum Anzeigen hierarchischer Daten, die es Benutzern ermöglicht, verschachtelte Elemente zu erweitern, zu reduzieren und damit zu interagieren.</p>
  </GalleryCard>
  
  <GalleryCard header="Avatar" href="avatar" image="/img/components/Avatar.png">
    <p>Eine Komponente zum Anzeigen von Benutzerdprofilbildern oder Initialen, mit Unterstützung für verschiedene Größen, Formen und Themen.</p>
  </GalleryCard>
  
</GalleryGrid>
