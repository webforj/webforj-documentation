---
title: UI Components
sidebar_position: 85
hide_table_of_contents: true
sidebar_class_name: has-new-content
hide_giscus_comments: true
_i18n_hash: f66c1b418ace12cb1945ab33fd272362
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

In webforJ werden Anwendungen mit modularen Einheiten erstellt, die als Komponenten bekannt sind und eine schnelle und effiziente UI-Entwicklung ermöglichen. Das Framework bietet eine Reihe von grundlegenden Komponenten wie Schaltflächen, Eingabeelemente und Layout-Container. Nachdem Sie die Grundlagen gemeistert haben, können Sie die [JavaDocs](https://javadoc.io/doc/com.webforj) für eine detaillierte Übersicht über alle Komponenten und deren Funktionalitäten konsultieren.

## Layouts {#layouts}

Layout-Komponenten bieten die Grundlage zur Strukturierung von Benutzeroberflächen und ermöglichen Entwicklern, Inhalte effizient zu organisieren. Diese Komponenten bieten verschiedene Möglichkeiten zur Steuerung der Anordnung von Kindkomponenten, sowohl für einfache als auch für komplexe Layouts.

Die folgenden Layout-Komponenten sind darauf ausgelegt, eine Vielzahl von Anwendungsfällen abzudecken, von responsive Design bis hin zu fortgeschrittener Inhaltsverwaltung.

<GalleryGrid>
  <GalleryCard header="AppLayout" href="app-layout" image="/img/components/AppLayout.png">
    <p>Eine Container-Komponente, die ein strukturiertes Layout für die Navigation der Hauptanwendung und die Organisation von Inhalten bietet.</p>
  </GalleryCard>

  <GalleryCard header="Toolbar" href="toolbar" image="/img/components/Toolbar.png">
    <p>Eine horizontale Container-Komponente, die eine Reihe von Schaltflächen, Symbolen oder anderen Steuerelementen enthält, die typischerweise zur Ausführung von Aufgaben im aktuellen Kontext verwendet werden.</p>
  </GalleryCard>

  <GalleryCard header="FlexLayout" href="flex-layout" image="/img/components/FlexLayout.png">
    <p>Eine Layout-Komponente, die ihre Kinder gemäß den flexiblen Box-Regeln (Flexbox) für responsive Designs und Ausrichtungen anordnet.</p>
  </GalleryCard>

  <GalleryCard header="ColumnsLayout" href="columns-layout" image="/img/components/ColumnsLayout.png">
    <p>Eine Layout-Komponente, die ihre Kinder in mehrere vertikale Spalten anordnet und sich hervorragend für die Erstellung von Formularen und gitterähnlichen Strukturen eignet.</p>
  </GalleryCard>

  <GalleryCard header="Splitter" href="splitter" image="/img/components/Splitter.png" effect="slideLeftRightScale">
    <p>Eine Layout-Komponente, die den verfügbaren Platz zwischen zwei Kindkomponenten aufteilt und es Benutzern ermöglicht, diese durch Ziehen der Trennerleiste zu ändern.</p>
  </GalleryCard>

  <GalleryCard header="Drawer" href="drawer" image="/img/components/Drawer.png" effect="slideUp">
    <p>Eine schiebende Panel-Komponente, die typischerweise für die Seitenavigation oder zur Unterbringung zusätzlicher Inhalte verwendet wird, die angezeigt oder ausgeblendet werden können.</p>
  </GalleryCard>

  <GalleryCard header="Dialog" href="dialog" image="/img/components/Dialog.png">
    <p>Eine modale Fenster-Komponente, die Inhalte überlagert, um wichtige Informationen anzuzeigen oder die Benutzerinteraktion zu fördern, wobei oft eine Benutzeraktion erforderlich ist, um sie zu schließen.</p>
  </GalleryCard>

  <GalleryCard header="Login" href="login" image="/img/components/Login.png">
    <p>Eine Komponente, die ein vorgefertigtes UI für die Benutzerauthentifizierung bereitstellt, typischerweise mit Feldern für Benutzername und Passwort sowie einem Absenden-Button.</p>
  </GalleryCard>

  <GalleryCard header="TabbedPane" href="tabbedpane" image="/img/components/TabbedPane.png">
    <p>Eine Container-Komponente, die Inhalte in mehrere Registerkarten organisiert, sodass Benutzer zwischen verschiedenen Ansichten oder Abschnitten wechseln können.</p>
  </GalleryCard>
</GalleryGrid>

## Dateneingabe {#data-entry}

Dateneingabe-Komponenten bieten wesentliche Werkzeuge zur Erfassung von Benutzereingaben und zur Verwaltung von Interaktionen innerhalb Ihrer Anwendung. Diese Komponenten sind vielseitig und ermöglichen den einfachen Aufbau interaktiver Formulare sowie die Erfassung verschiedener Datentypen.

<GalleryGrid>
  <GalleryCard header="TextField" href="fields/textfield" image="/img/components/TextField.png">
    <p>Eine Eingabekomponente für die Eingabe und Bearbeitung von Textdaten in einer einzigen Zeile.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TextField" href="fields/masked/textfield" image="/img/components/MaskedTextField.png">
    <p>Eine Texteingabekomponente, die die Benutzereingabe auf ein bestimmtes Format oder Muster beschränkt, typischerweise für Felder wie Telefonnummern, Daten oder Kreditkartennummern verwendet.</p>
  </GalleryCard>

  <GalleryCard header="NumberField" href="fields/numberfield" image="/img/components/NumberField.png">
    <p>Eine Komponente, die ein standardmäßiges browserseitiges Eingabefeld für die Eingabe numerischer Werte bereitstellt, mit integrierten Steuerelementen zur Erhöhung oder Verminderung des Wertes.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>NumberField" href="fields/masked/numberfield" image="/img/components/MaskedNumberField.png">
    <p>Eine numerische Eingabekomponente, die die Benutzereingabe auf ein bestimmtes numerisches Format oder Muster beschränkt und die Eingabe gültiger Zahlen wie Währungen, Prozentsätze oder andere formatierte Zahlen sicherstellt.</p>
  </GalleryCard>

  <GalleryCard header="PasswordField" href="fields/passwordfield" image="/img/components/PasswordField.png">
    <p>Eine Eingabekomponente in einer einzigen Zeile zur sicheren Eingabe und Maskierung von Passwortdaten.</p>
  </GalleryCard>

  <GalleryCard header="DateField" href="fields/datefield" image="/img/components/DateField.png">
    <p>Eine Komponente, die einen standardmäßigen browserseitigen Datumsauswähler bietet, um ein Datum über ein Eingabefeld auszuwählen.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>DateField" href="fields/masked/datefield" image="/img/components/MaskedDateField.png">
    <p>Eine Datumseingabekomponente, die ein bestimmtes Datumsformat oder Muster durchsetzt, um sicherzustellen, dass der Benutzer ein gültiges Datum gemäß der definierten Maske eingibt.</p>
  </GalleryCard>

  <GalleryCard header="TimeField" href="fields/timefield" image="/img/components/TimeField.png">
    <p>Eine Komponente, die einen standardmäßigen browserseitigen Zeitauswähler bietet, um einen Zeitwert über ein Eingabefeld auszuwählen.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TimeField" href="fields/masked/timefield" image="/img/components/MaskedTimeField.png">
    <p>Eine Zeiteingabekomponente, die ein bestimmtes Zeitformat oder Muster durchsetzt, um sicherzustellen, dass der Benutzer eine gültige Zeit gemäß der definierten Maske eingibt.</p>
  </GalleryCard>

  <GalleryCard header="DateTimeField" href="fields/datetimefield" image="/img/components/DateTimeField.png">
    <p>Eine Komponente, die einen standardmäßigen browserseitigen Datum- und Uhrzeitauswähler bietet, um sowohl Datum als auch Uhrzeit über ein einzelnes Eingabefeld auszuwählen.</p>
  </GalleryCard>

  <GalleryCard header="ColorField" href="fields/colorfield" image="/img/components/ColorField.png">
    <p>Eine Komponente, die einen standardmäßigen browserseitigen Farbauswähler bereitstellt und es Benutzern ermöglicht, eine Farbe aus einem Eingabefeld auszuwählen.</p>
  </GalleryCard>

  <GalleryCard header="TextArea" href="textarea" image="/img/components/TextArea.png">
    <p>Eine mehrzeilige Texteingabekomponente, die es Benutzern ermöglicht, größere Textblöcke einzugeben oder zu bearbeiten.</p>
  </GalleryCard>

  <GalleryCard header="CheckBox" href="checkbox" image="/img/components/CheckBox.png">
    <p>Eine Komponente, die eine binäre Option darstellt und es Benutzern ermöglicht, zwischen einem aktivierten (wahr) oder deaktivierten (falsch) Zustand umzuschalten.</p>
  </GalleryCard>

  <GalleryCard header="RadioButton" href="radiobutton" image="/img/components/RadioButton.png">
    <p>Eine Komponente, die es Benutzern ermöglicht, eine einzelne Option aus einer Gruppe von sich gegenseitig ausschließenden Auswahlmöglichkeiten auszuwählen.</p>
  </GalleryCard>

  <GalleryCard header="Switch" href="radiobutton#switches" image="/img/components/Switch.png">
    <p>Eine Toggle-Komponente, die es Benutzern ermöglicht, zwischen zwei Zuständen, wie ein/aus oder wahr/falsch, mit einer Gleitbewegung zu wechseln.</p>
  </GalleryCard>

  <GalleryCard header="ChoiceBox" href="lists/choicebox" image="/img/components/ChoiceBox.png">
    <p>Eine Komponente, die eine Dropdown-Liste von vordefinierten Optionen bietet und es Benutzern ermöglicht, eine Option aus der Liste auszuwählen.</p>
  </GalleryCard>

  <GalleryCard header="ComboBox" href="lists/combobox" image="/img/components/ComboBox.png">
    <p>Eine Komponente, die eine Dropdown-Liste mit einer bearbeitbaren Texteingabe kombiniert, sodass Benutzer entweder eine Option aus der Liste auswählen oder einen benutzerdefinierten Wert eingeben können.</p>
  </GalleryCard>

  <GalleryCard header="ListBox" href="lists/listbox" image="/img/components/ListBox.png">
    <p>Eine Komponente, die eine scrollbarere Liste von Optionen anzeigt und es Benutzern ermöglicht, ein oder mehrere Elemente aus der Liste auszuwählen.</p>
  </GalleryCard>
</GalleryGrid>

## Optionsdialoge {#option-dialogs}

Optionsdialoge bieten eine Möglichkeit, Benutzern Entscheidungen zu präsentieren oder sie um Bestätigung zu bitten, bevor sie mit einer Aktion fortfahren. Diese Komponenten sind entscheidend für die Erstellung interaktiver, entscheidungsbasierter Workflows, die es Benutzern ermöglichen, aus verschiedenen Optionen klar und strukturiert zu bestätigen, abzubrechen oder auszuwählen.

<GalleryGrid>
  <GalleryCard header="MessageDialog" href="option-dialogs/message" image="/img/components/MessageDialog.png">
    <p>Eine Dialogkomponente, die verwendet wird, um dem Benutzer Informationsnachrichten oder Warnungen anzuzeigen, typischerweise mit einer einzelnen `OK`-Schaltfläche zur Bestätigung der Nachricht.</p>
  </GalleryCard>

  <GalleryCard header="ConfirmDialog" href="option-dialogs/confirm" image="/img/components/ConfirmDialog.png">
    <p>Eine Dialogkomponente, die den Benutzer auffordert, eine Aktion zu bestätigen oder abzubrechen, typischerweise mit `Ja` und `Nein` oder `OK` und `Abbrechen`-Schaltflächen.</p>
  </GalleryCard>
  
  <GalleryCard header="InputDialog" href="option-dialogs/input" image="/img/components/InputDialog.png">
    <p>Eine Dialogkomponente, die den Benutzer auffordert, Text oder Daten einzugeben und typischerweise ein Eingabefeld sowie Aktionsschaltflächen wie `OK` und `Abbrechen` bereitstellt.</p>
  </GalleryCard>

  <GalleryCard header="FileChooserDialog" href="option-dialogs/file-chooser" image="/img/components/FileChooserDialog.png">
    <p>Eine Dialogkomponente, die es Benutzern ermöglicht, Dateien vom Server-Dateisystem zu durchsuchen und auszuwählen.</p>
  </GalleryCard>

  <GalleryCard header="FileUploadDialog" href="option-dialogs/file-upload" image="/img/components/FileUploadDialog.png">
    <p>Eine Dialogkomponente, die es Benutzern ermöglicht, Dateien von ihrem lokalen Dateisystem in die Anwendung hochzuladen.</p>
  </GalleryCard>

  <GalleryCard header="FileSaveDialog" href="option-dialogs/file-save" image="/img/components/FileSaveDialog.png">
    <p>Eine Dialogkomponente, die es Benutzern ermöglicht, eine Datei an einem bestimmten Ort im Server-Dateisystem zu speichern.</p>
  </GalleryCard>
</GalleryGrid>

## Interaktion und Anzeige {#interaction-and-display}

Diese Kategorie umfasst Komponenten, die Benutzerinteraktionen erleichtern und Daten oder Anwendungszustände visuell darstellen. Diese Komponenten helfen Benutzern, sich in der Anwendung zu navigieren, Aktionen auszulösen und den Fortschritt oder die Ergebnisse durch dynamische visuelle Elemente zu verstehen.

<GalleryGrid>
  <GalleryCard header="Table" href="table/overview" image="/img/components/Table.png">
    <p> Eine Komponente, die verwendet wird, um Daten in einem strukturierten, tabellarischen Format mit Zeilen und Spalten anzuzeigen, und Funktionen wie Sortierung und Pagination unterstützt.</p>
  </GalleryCard>

  <GalleryCard header="GoogleCharts" href="google-charts" image="/img/components/GoogleCharts.png">
    <p>Eine Komponente, die mit Google Charts integriert ist, um verschiedene Arten von Diagrammen und visuellen Datenrepräsentationen in der Anwendung darzustellen.</p>
  </GalleryCard>

  <GalleryCard header="Button" href="button" image="/img/components/Button.png">
    <p>Eine klickbare Komponente, die eine Aktion oder ein Ereignis auslöst, wenn sie gedrückt wird.</p>
  </GalleryCard>

  <GalleryCard header="Toast" href="toast" image="/img/components/Toast.png" effect="slideUp">
    <p>Eine leichte, nicht-blockierende Benachrichtigungskomponente, die kurz eine Nachricht für den Benutzer anzeigt, bevor sie automatisch verschwindet.</p>
  </GalleryCard>

  <GalleryCard header="Alert" href="alert" image="/img/components/Alert.png">
    <p>Eine Komponente, die wichtige Nachrichten oder Warnungen in einem auffälligen Format anzeigt, um die Aufmerksamkeit des Benutzers zu erlangen.</p>
  </GalleryCard>

  <GalleryCard header="DesktopNotification" href="desktop-notification" image="/img/components/DesktopNotification.png">
    <p>Eine Komponente, die die native Benachrichtigungs-API des Browsers nutzt, um Benutzern benutzerdefinierte Desktop-Benachrichtigungen anzuzeigen.</p>
  </GalleryCard>
  
  <GalleryCard header="Navigator" href="navigator" image="/img/components/Navigator.png">
    <p>Eine anpassbare Paginierungskomponente zur Navigation in Datensätzen, die Layouts mit Schaltflächen für zuerst, zuletzt, weiter, zurück und schnellen Sprungfeldern unterstützt.</p>
  </GalleryCard>

  <GalleryCard header="ProgressBar" href="progressbar" image="/img/components/ProgressBar.png">
    <p>Eine Komponente, die den Fortschritt einer Aufgabe oder eines Prozesses visuell darstellt, typischerweise als horizontale Leiste, die sich füllt, während Fortschritte gemacht werden.</p>
  </GalleryCard>

  <GalleryCard header="Slider" href="slider" image="/img/components/Slider.png">
    <p>Eine Komponente, die es Benutzern ermöglicht, einen Wert aus einem definierten Bereich auszuwählen, indem sie einen Griff entlang einer Spur ziehen.</p>
  </GalleryCard>

  <GalleryCard header="BusyIndicator" href="busyindicator" image="/img/components/BusyIndicator.png">
    <p> Ein anwendungsweites visuelles Signal, typischerweise ein Spinner, das anzeigt, dass ein globaler Prozess im Gange ist.</p>
  </GalleryCard>

  <GalleryCard header="Loading" href="loading" image="/img/components/Loading.png">
    <p>Ein Scoped-Ladeindikator, der innerhalb einer bestimmten übergeordneten Komponente angezeigt wird und anzeigt, dass Inhalte oder Daten in diesem Abschnitt geladen werden.</p>
  </GalleryCard>

  <GalleryCard header="Spinner" href="spinner" image="/img/components/Spinner.png">
    <p>Eine Komponente, die eine rotierende Animation anzeigt, die typischerweise verwendet wird, um anzuzeigen, dass ein Prozess oder eine Aktion im Gange ist.</p>
  </GalleryCard>

  <GalleryCard header="AppNav" href="appnav" image="/img/components/AppNav.png" effect="slideFromLeft">
    <p>Eine Komponente, die ein Navigationsmenü für die Anwendung bereitstellt, das typischerweise verwendet wird, um Links oder Navigationselemente zum Wechseln zwischen verschiedenen Abschnitten oder Ansichten aufzulisten.</p>
  </GalleryCard>

  <GalleryCard header="Icon" href="icon" image="/img/components/Icons.png">
    <p>Eine Komponente, die ein grafisches Symbol oder Bild anzeigt, das häufig verwendet wird, um eine Aktion, einen Status oder eine Kategorie in der Benutzeroberfläche darzustellen.</p>
  </GalleryCard>

  <GalleryCard header="Terminal" href="terminal" image="/img/components/Terminal.png">
    <p>Eine Komponente, die eine Kommandozeilenoberfläche (CLI) innerhalb der Anwendung simuliert und es Benutzern ermöglicht, texteingabebasierte Befehle einzugeben und auszuführen.</p>
  </GalleryCard>
  
  <GalleryCard header="InfiniteScroll" href="infinitescroll" image="/img/components/InfiniteScroll.png">
    <p>Eine Komponente, die beim Scrollen mehr Elemente lädt, einen Loader anzeigt und nachverfolgt, wann alle Inhalte abgerufen wurden.</p>
  </GalleryCard>

  <GalleryCard header="Refresher" href="refresher" image="/img/components/Refresher.png">
    <p>Eine Komponente, die eine Pull-to-Refresh-Interaktion innerhalb scrollbarer Container ermöglicht – ideal für das dynamische Laden von Daten.</p>
  </GalleryCard>

  <GalleryCard header="Tree" href="tree" image="/img/components/Tree.png">
    <p>Eine Komponente zur Anzeige hierarchischer Daten, die es Benutzern ermöglicht, verschachtelte Elemente zu erweitern, zu reduzieren und damit zu interagieren.</p>
  </GalleryCard>
</GalleryGrid>
