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
  <title>UI Componenten | Componenten voor het bouwen van gebruikersinterface-applicaties</title>
</Head>

In webforJ worden applicaties gemaakt met behulp van modulaire eenheden, bekend als Componenten, die een snelle en efficiënte ontwikkeling van de gebruikersinterface mogelijk maken. Het framework biedt een scala aan essentiële componenten zoals knoppen, invoerelementen en lay-outcontainers. Na het beheersen van de basisprincipes kun je de [JavaDocs](https://javadoc.io/doc/com.webforj) raadplegen voor een gedetailleerd overzicht van alle componenten en hun functionaliteiten.

## Lay-outs {#layouts}

Lay-outcomponenten bieden de basis voor het structureren van gebruikersinterfaces, waardoor ontwikkelaars inhoud efficiënt kunnen organiseren. Deze componenten bieden verschillende manieren om de indeling van kindcomponenten te controleren, of het nu gaat om eenvoudige of complexe lay-outs.

De volgende lay-outcomponenten zijn ontworpen om een breed scala aan gebruiksscenario's aan te pakken, van responsief ontwerp tot geavanceerd contentbeheer.

<GalleryGrid>
  <GalleryCard header="AppLayout" href="app-layout" image="/img/components/light/AppLayout.webp" imageDark="/img/components/dark/AppLayout.webp">
    <p>Een containercomponent die een gestructureerde lay-out biedt voor navigatie en organisatie van inhoud op het hoogste niveau.</p>
  </GalleryCard>

  <GalleryCard header="Toolbar" href="toolbar" image="/img/components/light/Toolbar.webp" imageDark="/img/components/dark/Toolbar.webp">
    <p>Een horizontale containercomponent die een set actieknoppen, pictogrammen of andere bedieningselementen bevat, meestal gebruikt voor het uitvoeren van taken die verband houden met de huidige context.</p>
  </GalleryCard>

  <GalleryCard header="FlexLayout" href="flex-layout" image="/img/components/light/FlexLayout.webp" imageDark="/img/components/dark/FlexLayout.webp">
    <p>Een lay-outcomponent die zijn kinderen rangschikt met behulp van flexibele box (flexbox) regels voor responsief ontwerp en uitlijning.</p>
  </GalleryCard>

  <GalleryCard header="ColumnsLayout" href="columns-layout" image="/img/components/light/ColumnsLayout.webp" imageDark="/img/components/dark/ColumnsLayout.webp">
    <p>Een lay-outcomponent die zijn kinderen rangschikt in meerdere verticale kolommen, nuttig voor het maken van formulieren en roosterachtige structuren.</p>
  </GalleryCard>

  <GalleryCard header="Splitter" href="splitter" image="/img/components/light/Splitter.webp" imageDark="/img/components/dark/Splitter.webp">
    <p>Een lay-outcomponent die de beschikbare ruimte tussen twee kindcomponenten verdeelt, waardoor gebruikers ze kunnen aanpassen door de splitterbalk te slepen.</p>
  </GalleryCard>

  <GalleryCard header="Drawer" href="drawer" image="/img/components/light/Drawer.webp" imageDark="/img/components/dark/Drawer.webp">
    <p>Een schuifpaneelcomponent die typisch wordt gebruikt voor zij-navigatie of voor het huisvesten van extra inhoud die kan worden weergegeven of verborgen.</p>
  </GalleryCard>

  <GalleryCard header="Dialog" href="dialog" image="/img/components/light/Dialog.webp" imageDark="/img/components/dark/Dialog.webp">
    <p>Een modaal venstercomponent die inhoud overlayt om belangrijke informatie weer te geven of gebruikersinteractie te vragen, vaak met vereiste gebruikersactie om te sluiten.</p>
  </GalleryCard>

  <GalleryCard header="Login" href="login" image="/img/components/light/Login.webp" imageDark="/img/components/dark/Login.webp">
    <p>Een component die een vooraf gebouwde gebruikersinterface biedt voor gebruikersauthenticatie, meestal met velden voor gebruikersnaam en wachtwoord, samen met een knop om te verzenden.</p>
  </GalleryCard>

  <GalleryCard header="Accordion" href="accordion" image="/img/components/light/Accordion.webp" imageDark="/img/components/dark/Accordion.webp">
    <p>Een verticaal gestapeld stel uitklapbare panelen, elk met een klikbare kop die de zichtbaarheid van de lichaam inhoud omschakelt.</p>
  </GalleryCard>

  <GalleryCard header="TabbedPane" href="tabbedpane" image="/img/components/light/TabbedPane.webp" imageDark="/img/components/dark/TabbedPane.webp">
    <p>Een containercomponent die inhoud organiseert in meerdere tabbladen, waardoor gebruikers tussen verschillende weergaven of secties kunnen schakelen.</p>
  </GalleryCard>

  <GalleryCard header="Card" href="card" image="/img/components/light/Card.webp" imageDark="/img/components/dark/Card.webp">
    <p>Een oppervlak dat gerelateerde inhoud en acties groepeert, met gebieden voor media, koppen, hoofdinhoud en voetteksten.</p>
  </GalleryCard>
</GalleryGrid>

## Gegevensinvoer {#data-entry}

Gegevensinvoerencomponenten bieden essentiële tools voor het vastleggen van gebruikersinvoer en het beheren van interacties binnen je app. Deze componenten zijn veelzijdig en maken het eenvoudig om interactieve formulieren te bouwen en verschillende soorten gegevens te verzamelen.

<GalleryGrid>
  <GalleryCard header="TextField" href="fields/textfield" image="/img/components/light/TextField.webp" imageDark="/img/components/dark/TextField.webp">
    <p>Een enkele regel invoerencomponent voor het invoeren en bewerken van tekstgegevens.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Gemaskerde</span>TextField" href="fields/masked/textfield" image="/img/components/light/MaskedTextField.webp" imageDark="/img/components/dark/MaskedTextField.webp">
    <p>Een tekstinvoerencomponent die de gebruikersinvoer beperkt tot een specifiek formaat of patroon, typisch gebruikt voor velden zoals telefoonnummers, data of creditcardnummers.</p>
  </GalleryCard>

  <GalleryCard header="NumberField" href="fields/numberfield" image="/img/components/light/NumberField.webp" imageDark="/img/components/dark/NumberField.webp">
    <p>Een component die een standaard webbrowser-gebaseerd invoerveld biedt voor het invoeren van numerieke waarden, met ingebouwde bedieningselementen voor het verhogen of verlagen van de waarde.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Gemaskerde</span>NumberField" href="fields/masked/numberfield" image="/img/components/light/MaskedNumberField.webp" imageDark="/img/components/dark/MaskedNumberField.webp">
    <p>Een numerieke invoerencomponent die de gebruikersinvoer beperkt tot een specifiek numeriek formaat of patroon, en zorgt ervoor dat geldige nummerinvoer, zoals voor valuta, percentages of andere geformatteerde nummers.</p>
  </GalleryCard>

  <GalleryCard header="PasswordField" href="fields/passwordfield" image="/img/components/light/PasswordField.webp" imageDark="/img/components/dark/PasswordField.webp">
    <p>Een enkele regel invoerencomponent voor veilig invoeren en maskeren van wachtwoordgegevens.</p>
  </GalleryCard>

  <GalleryCard header="DateField" href="fields/datefield" image="/img/components/light/DateField.webp" imageDark="/img/components/dark/DateField.webp">
    <p>Een component die een standaard webbrowser-gebaseerde datumkiezer biedt voor het selecteren van een datum via een invoerveld.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Gemaskerde</span>DateField" href="fields/masked/datefield" image="/img/components/light/MaskedDateField.webp" imageDark="/img/components/dark/MaskedDateField.webp">
    <p>Een datuminvoerencomponent die een specifiek datumformaat of -patroon afdwingt, zodat de gebruiker een geldige datum invoert volgens de gedefinieerde masker.</p>
  </GalleryCard>

  <GalleryCard header="TimeField" href="fields/timefield" image="/img/components/light/TimeField.webp" imageDark="/img/components/dark/TimeField.webp">
    <p>Een component die een standaard webbrowser-gebaseerde tijdkiezer biedt voor het selecteren van een tijdwaarde via een invoerveld.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Gemaskerde</span>TimeField" href="fields/masked/timefield" image="/img/components/light/MaskedTimeField.webp" imageDark="/img/components/dark/MaskedTimeField.webp">
    <p>Een tijdinvoerencomponent die een specifiek tijdformaat of -patroon afdwingt, en ervoor zorgt dat de gebruiker een geldige tijd invoert volgens de gedefinieerde masker.</p>
  </GalleryCard>

  <GalleryCard header="DateTimeField" href="fields/datetimefield" image="/img/components/light/DateTimeField.webp" imageDark="/img/components/dark/DateTimeField.webp">
    <p>Een component die een standaard webbrowser-gebaseerde datum- en tijdkiezer biedt voor het selecteren van zowel datum als tijd via een enkel invoerveld.</p>
  </GalleryCard>

  <GalleryCard header="ColorField" href="fields/colorfield" image="/img/components/light/ColorField.webp" imageDark="/img/components/dark/ColorField.webp">
    <p>Een component die een standaard webbrowser-gebaseerde kleurkiezer biedt, waarmee gebruikers een kleur kunnen selecteren vanuit een invoerveld.</p>
  </GalleryCard>

  <GalleryCard header="TextArea" href="textarea" image="/img/components/light/TextArea.webp" imageDark="/img/components/dark/TextArea.webp">
    <p>Een multi-regel tekstinvoerencomponent die gebruikers toestaat om grotere blokken tekst in te voeren of te bewerken.</p>
  </GalleryCard>

  <GalleryCard header="CheckBox" href="checkbox" image="/img/components/light/CheckBox.webp" imageDark="/img/components/dark/CheckBox.webp">
    <p>Een component die een binaire optie vertegenwoordigt, waarmee gebruikers kunnen schakelen tussen een aangevinkte (waar) of niet-aangevinkte (onwaar) status.</p>
  </GalleryCard>

  <GalleryCard header="RadioButton" href="radiobutton" image="/img/components/light/RadioButton.webp" imageDark="/img/components/dark/RadioButton.webp">
    <p>Een component die gebruikers toestaat om een enkele optie te selecteren uit een groep onderling exclusieve keuzes.</p>
  </GalleryCard>

  <GalleryCard header="Switch" href="radiobutton#switches" image="/img/components/light/Switch.webp" imageDark="/img/components/dark/Switch.webp">
    <p>Een schakelaardcomponent die gebruikers toestaat om tussen twee staten te schakelen, zoals aan/uit of waar/onwaar, met een schuifbeweging.</p>
  </GalleryCard>

  <GalleryCard header="ChoiceBox" href="lists/choicebox" image="/img/components/light/ChoiceBox.webp" imageDark="/img/components/dark/ChoiceBox.webp">
    <p>Een component die een dropdownlijst biedt van vooraf gedefinieerde opties, waarmee gebruikers één optie uit de lijst kunnen selecteren.</p>
  </GalleryCard>

  <GalleryCard header="ComboBox" href="lists/combobox" image="/img/components/light/ComboBox.webp" imageDark="/img/components/dark/ComboBox.webp">
    <p>Een component die een dropdownlijst combineert met een bewerkbaar tekstinvoerveld, waarbij gebruikers ofwel een optie uit de lijst kunnen selecteren of een aangepaste waarde kunnen invoeren.</p>
  </GalleryCard>

  <GalleryCard header="ListBox" href="lists/listbox" image="/img/components/light/ListBox.webp" imageDark="/img/components/dark/ListBox.webp">
    <p>Een component die een doorScrollbare lijst van opties weergeeft, waarmee gebruikers een of meer items uit de lijst kunnen selecteren.</p>
  </GalleryCard>

  <GalleryCard header="Upload" href="upload" image="/img/components/light/Upload.webp" imageDark="/img/components/dark/Upload.webp">
    <p>Een inline-bestandkiezer waarmee gebruikers een of meer bestanden van hun lokale machine kunnen selecteren en uploaden naar de server, met slepen en neerzetten, filters en per-bestand gebeurtenistracering.</p>
  </GalleryCard>
</GalleryGrid>

## Optiedialogen {#option-dialogs}

Optiedialogen bieden een manier om gebruikers keuzes voor te leggen of hen om bevestiging te vragen voordat ze doorgaan met een actie. Deze componenten zijn essentieel voor het creëren van interactieve, besluitgedreven workflows, waarmee gebruikers kunnen bevestigen, annuleren of kiezen uit verschillende opties op een duidelijke en gestructureerde manier.

<GalleryGrid>
  <GalleryCard header="MessageDialog" href="option-dialogs/message" image="/img/components/light/MessageDialog.webp" imageDark="/img/components/dark/MessageDialog.webp">
    <p>Een dialoogcomponent die wordt gebruikt om informatieve berichten of waarschuwingen aan de gebruiker weer te geven, typisch met een enkele `OK`-knop om het bericht te erkennen.</p>
  </GalleryCard>

  <GalleryCard header="ConfirmDialog" href="option-dialogs/confirm" image="/img/components/light/ConfirmDialog.webp" imageDark="/img/components/dark/ConfirmDialog.webp">
    <p>Een dialoogcomponent die de gebruiker vraagt om een actie te bevestigen of te annuleren, meestal met knoppen `Ja` en `Nee` of `OK` en `Annuleren`.</p>
  </GalleryCard>

  <GalleryCard header="InputDialog" href="option-dialogs/input" image="/img/components/light/InputDialog.webp" imageDark="/img/components/dark/InputDialog.webp">
    <p>Een dialoogcomponent die de gebruiker vraagt om tekst of gegevens in te voeren, meestal met een invoerveld en actieknoppen zoals `OK` en `Annuleren`.</p>
  </GalleryCard>

  <GalleryCard header="FileChooserDialog" href="option-dialogs/file-chooser" image="/img/components/light/FileChooserDialog.webp" imageDark="/img/components/dark/FileChooserDialog.webp">
    <p>Een dialoogcomponent waarmee gebruikers bestanden kunnen doorbladeren en selecteren van het serverbestandssysteem.</p>
  </GalleryCard>

  <GalleryCard header="FileUploadDialog" href="option-dialogs/file-upload" image="/img/components/light/FileUploadDialog.webp" imageDark="/img/components/dark/FileUploadDialog.webp">
    <p>Een dialoogcomponent die gebruikers in staat stelt om bestanden van hun lokale bestandssysteem naar de app te uploaden.</p>
  </GalleryCard>

  <GalleryCard header="FileSaveDialog" href="option-dialogs/file-save" image="/img/components/light/FileSaveDialog.webp" imageDark="/img/components/dark/FileSaveDialog.webp">
    <p>Een dialoogcomponent die gebruikers de mogelijkheid biedt om een bestand op te slaan op een specifieke locatie op het serverbestandssysteem.</p>
  </GalleryCard>
</GalleryGrid>

## Interactie en weergave {#interaction-and-display}

Deze categorie omvat componenten die gebruikerse interacties faciliteren en gegevens of app-statusen visueel weergeven. Deze componenten helpen gebruikers de app te navigeren, acties te activeren en de voortgang of resultaten inzichtelijk te maken via dynamische visuele elementen.

<GalleryGrid>
  <GalleryCard header="Table" href="table/overview" image="/img/components/light/Table.webp" imageDark="/img/components/dark/Table.webp">
    <p>Een component die wordt gebruikt om gegevens weer te geven in een gestructureerd, tabelvormig formaat met rijen en kolommen, met ondersteunende functies zoals sorteren en paginering.</p>
  </GalleryCard>

  <GalleryCard header="GoogleCharts" href="google-charts" image="/img/components/light/GoogleCharts.webp" imageDark="/img/components/dark/GoogleCharts.webp">
    <p>Een component die is geïntegreerd met Google Charts om verschillende soorten grafieken en visuele gegevensrepresentaties in de app weer te geven.</p>
  </GalleryCard>

  <GalleryCard header="Button" href="button" image="/img/components/light/Button.webp" imageDark="/img/components/dark/Button.webp">
    <p>Een klikbare component die een actie of gebeurtenis activeert wanneer erop wordt gedrukt.</p>
  </GalleryCard>

  <GalleryCard header="Toast" href="toast" image="/img/components/light/Toast.webp" imageDark="/img/components/dark/Toast.webp">
    <p>Een lichte, niet-blokkerende notificatiecomponent die kort een bericht aan de gebruiker weergeeft voordat het automatisch verdwijnt.</p>
  </GalleryCard>

  <GalleryCard header="Alert" href="alert" image="/img/components/light/Alert.webp" imageDark="/img/components/dark/Alert.webp">
    <p>Een component die belangrijke berichten of waarschuwingen in een opvallend formaat weergeeft om de aandacht van de gebruiker te trekken.</p>
  </GalleryCard>

  <GalleryCard header="Badge" href="badge" image="/img/components/light/Badge.webp" imageDark="/img/components/dark/Badge.webp">
    <p>Een klein labelcomponent voor het weergeven van tellingen, statussen of korte metadata, met ondersteuning voor thema's, maten en pictogrammen.</p>
  </GalleryCard>

  <GalleryCard header="DesktopNotification" href="desktop-notification" image="/img/components/light/DesktopNotification.webp" imageDark="/img/components/dark/DesktopNotification.webp">
    <p>Een component die gebruikmaakt van de native Notification API van de browser om gebruikers te waarschuwen met aangepaste desktopnotificaties.</p>
  </GalleryCard>

  <GalleryCard header="Navigator" href="navigator" image="/img/components/light/Navigator.webp" imageDark="/img/components/dark/Navigator.webp">
    <p>Een aanpasbare pagineringscomponent voor het navigeren door gegevenssets, met ondersteuning voor lay-outs met eerste, laatste, volgende, vorige knoppen en snelle springvelden.</p>
  </GalleryCard>

  <GalleryCard header="ProgressBar" href="progressbar" image="/img/components/light/ProgressBar.webp" imageDark="/img/components/dark/ProgressBar.webp">
    <p>Een component die de voortgang van een taak of proces visueel weergeeft, typisch weergegeven als een horizontale balk die zich vult naarmate de voortgang vordert.</p>
  </GalleryCard>

  <GalleryCard header="Slider" href="slider" image="/img/components/light/Slider.webp" imageDark="/img/components/dark/Slider.webp">
    <p>Een component die gebruikers toestaat om een waarde uit een gedefinieerd bereik te selecteren door een handgreep langs een baan te slepen.</p>
  </GalleryCard>

  <GalleryCard header="BusyIndicator" href="busyindicator" image="/img/components/light/BusyIndicator.webp" imageDark="/img/components/dark/BusyIndicator.webp">
    <p>Een visuele indicator, typisch een spinner, die aangeeft dat een globale proces aan de gang is.</p>
  </GalleryCard>

  <GalleryCard header="Loading" href="loading" image="/img/components/light/Loading.webp" imageDark="/img/components/dark/Loading.webp">
    <p>Een loading-indicator die binnen een specifieke bovenliggende component toont, wat aangeeft dat inhoud of gegevens worden geladen in dat gedeelte.</p>
  </GalleryCard>

  <GalleryCard header="Spinner" href="spinner" image="/img/components/light/Spinner.webp" imageDark="/img/components/dark/Spinner.webp">
    <p>Een component die een roterende animatie weergeeft, meestal gebruikt om aan te geven dat een proces of actie aan de gang is.</p>
  </GalleryCard>

  <GalleryCard header="AppNav" href="appnav" image="/img/components/light/AppNav.webp" imageDark="/img/components/dark/AppNav.webp">
    <p>Een component die een navigatiemenu voor de app biedt, meestal gebruikt voor het opsommen van links of navigatie-items voor het schakelen tussen verschillende secties of weergaven.</p>
  </GalleryCard>

  <GalleryCard header="Icon" href="icon" image="/img/components/light/Icon.webp" imageDark="/img/components/dark/Icon.webp">
    <p>Een component die een grafisch symbool of afbeelding weergeeft, vaak gebruikt om een actie, status of categorie in de gebruikersinterface weer te geven.</p>
  </GalleryCard>

  <GalleryCard header="Terminal" href="terminal" image="/img/components/light/Terminal.webp" imageDark="/img/components/dark/Terminal.webp">
    <p>Een component die een commandoregelinterface (CLI) binnen de app simuleert, waarmee gebruikers tekstgebaseerde opdrachten kunnen invoeren en uitvoeren.</p>
  </GalleryCard>

  <GalleryCard header="InfiniteScroll" href="infinitescroll" image="/img/components/light/InfiniteScroll.webp" imageDark="/img/components/dark/InfiniteScroll.webp">
    <p>Een component die meer items laadt bij scrollen, toont een laadbalk en houdt bij wanneer alle inhoud is opgehaald.</p>
  </GalleryCard>

  <GalleryCard header="Refresher" href="refresher" image="/img/components/light/Refresher.webp" imageDark="/img/components/dark/Refresher.webp">
    <p>Een component die een pull-naar-verversen-interactie binnen scrollbare containers mogelijk maakt—ideaal voor dynamisch gegevensladen.</p>
  </GalleryCard>

  <GalleryCard header="Tree" href="tree" image="/img/components/light/Tree.webp" imageDark="/img/components/dark/Tree.webp">
    <p>Een component voor het weergeven van hiërarchische gegevens, waarmee gebruikers genestelde items kunnen uitbreiden, samenvouwen en ermee interageren.</p>
  </GalleryCard>

  <GalleryCard header="Avatar" href="avatar" image="/img/components/light/Avatar.webp" imageDark="/img/components/dark/Avatar.webp">
    <p>Een component voor het weergeven van profielfoto's of initialen van gebruikers, met ondersteuning voor verschillende maten, vormen en thema's.</p>
  </GalleryCard>

  <GalleryCard header="MarkdownViewer" href="markdownviewer" image="/img/components/light/MarkdownViewer.webp" imageDark="/img/components/dark/MarkdownViewer.webp">
    <p>Een component voor het weergeven van markdown-inhoud met progressieve karakter-voor-karakter rendering, ideaal voor AI-chatinterfaces en streamingtekst.</p>
  </GalleryCard>
</GalleryGrid>
