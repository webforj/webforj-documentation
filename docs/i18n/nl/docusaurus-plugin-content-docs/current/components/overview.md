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
  <title>UI-componenten | Componenten voor het bouwen van gebruikersinterfaces</title>
</Head>

In webforJ worden applicaties gemaakt met behulp van modulaire eenheden die bekend staan als Componenten, die snelle en efficiënte UI-ontwikkeling mogelijk maken. Het framework biedt een scala aan essentiële componenten zoals knoppen, invoerelementen en lay-outcontainers. Nadat je de basisprincipes hebt beheerst, kun je de [JavaDocs](https://javadoc.io/doc/com.webforj) raadplegen voor een gedetailleerd overzicht van alle componenten en hun functionaliteiten.

## Lay-outs {#layouts}

Lay-outcomponenten bieden de basis voor het structureren van gebruikersinterfaces, waarmee ontwikkelaars content efficiënt kunnen organiseren. Deze componenten bieden verschillende manieren om de indeling van kindercomponenten te beheersen, of het nu gaat om eenvoudige of complexe lay-outs.

De volgende lay-outcomponenten zijn ontworpen voor een breed scala aan gebruikssituaties, van responsief ontwerp tot geavanceerd contentbeheer

<GalleryGrid>
  <GalleryCard header="AppLayout" href="app-layout" image="/img/components/light/AppLayout.webp" imageDark="/img/components/dark/AppLayout.webp">
    <p>Een containercomponent die een gestructureerde lay-out biedt voor de navigatie en contentorganisatie van de applicatie.</p>
  </GalleryCard>

  <GalleryCard header="Toolbar" href="toolbar" image="/img/components/light/Toolbar.webp" imageDark="/img/components/dark/Toolbar.webp">
    <p>Een horizontale containercomponent die een set actieknoppen, pictogrammen of andere bedieningselementen bevat, meestal gebruikt voor het uitvoeren van taken die verband houden met de huidige context.</p>
  </GalleryCard>

  <GalleryCard header="FlexLayout" href="flex-layout" image="/img/components/light/FlexLayout.webp" imageDark="/img/components/dark/FlexLayout.webp">
    <p>Een lay-outcomponent die zijn kinderen rangschikt met behulp van flexibele box (flexbox) regels voor responsief ontwerp en uitlijning.</p>
  </GalleryCard>

  <GalleryCard header="ColumnsLayout" href="columns-layout" image="/img/components/light/ColumnsLayout.webp" imageDark="/img/components/dark/ColumnsLayout.webp">
    <p>Een lay-outcomponent die zijn kinderen in meerdere verticale kolommen rangschikt, nuttig voor het maken van formulieren en grid-achtige structuren.</p>
  </GalleryCard>

  <GalleryCard header="Splitter" href="splitter" image="/img/components/light/Splitter.webp" imageDark="/img/components/dark/Splitter.webp">
    <p>Een lay-outcomponent die de beschikbare ruimte verdeelt tussen twee kindercomponenten, waardoor gebruikers ze kunnen vergroten of verkleinen door de splitterbalk te slepen.</p>
  </GalleryCard>

  <GalleryCard header="Drawer" href="drawer" image="/img/components/light/Drawer.webp" imageDark="/img/components/dark/Drawer.webp">
    <p>Een schuifpaneelcomponent die meestal wordt gebruikt voor zij-navigatie of het huisvesten van extra content die kan worden weergegeven of verborgen.</p>
  </GalleryCard>

  <GalleryCard header="Dialog" href="dialog" image="/img/components/light/Dialog.webp" imageDark="/img/components/dark/Dialog.webp">
    <p>Een modaal venstercomponent die inhoud overlagert om belangrijke informatie weer te geven of om gebruikersinteractie te vragen, vaak vereist het dat de gebruiker actie onderneemt om te sluiten.</p>
  </GalleryCard>

  <GalleryCard header="Login" href="login" image="/img/components/light/Login.webp" imageDark="/img/components/dark/Login.webp">
    <p>Een component die een kant-en-klare UI biedt voor gebruikersauthenticatie, meestal met velden voor gebruikersnaam en wachtwoord, samen met een verzendknop.</p>
  </GalleryCard>

  <GalleryCard header="Accordion" href="accordion" image="/img/components/light/Accordion.webp" imageDark="/img/components/dark/Accordion.webp">
    <p>Een verticaal gestapeld pakket van inklapbare panelen, elk met een klikbare kop die de zichtbaarheid van de lichaaminhoud toggleert.</p>
  </GalleryCard>

  <GalleryCard header="TabbedPane" href="tabbedpane" image="/img/components/light/TabbedPane.webp" imageDark="/img/components/dark/TabbedPane.webp">
    <p>Een containercomponent die inhoud organiseert in meerdere tabbladen, waarmee gebruikers tussen verschillende weergaven of secties kunnen schakelen.</p>
  </GalleryCard>
</GalleryGrid>

## Gegevensinvoer {#data-entry}

Gegevensinvoercomponenten bieden essentiële tools voor het vastleggen van gebruikersinvoer en het beheren van interacties binnen je app. Deze componenten zijn veelzijdig, waardoor het eenvoudig is om interactieve formulieren te bouwen en verschillende soorten gegevens te verzamelen.

<GalleryGrid>
  <GalleryCard header="TextField" href="fields/textfield" image="/img/components/light/TextField.webp" imageDark="/img/components/dark/TextField.webp">
    <p>Een enkelregel invoercomponent voor het invoeren en bewerken van tekstgegevens.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TextField" href="fields/masked/textfield" image="/img/components/light/MaskedTextField.webp" imageDark="/img/components/dark/MaskedTextField.webp">
    <p>Een tekstinvoerveld dat de gebruikersinvoer beperkt tot een specifiek formaat of patroon, meestal gebruikt voor velden zoals telefoonnummers, datums of creditcardnummers.</p>
  </GalleryCard>

  <GalleryCard header="NumberField" href="fields/numberfield" image="/img/components/light/NumberField.webp" imageDark="/img/components/dark/NumberField.webp">
    <p>Een component die een standaard browsergebaseerd invoerveld biedt voor het invoeren van numerieke waarden, met ingebouwde bedieningselementen voor het verhogen of verlagen van de waarde.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>NumberField" href="fields/masked/numberfield" image="/img/components/light/MaskedNumberField.webp" imageDark="/img/components/dark/MaskedNumberField.webp">
    <p>Een numeriek invoerveld dat de gebruikersinvoer beperkt tot een specifiek numeriek formaat of patroon, wat zorgt voor geldige invoer zoals voor valuta, percentages of andere geformatteerde nummers.</p>
  </GalleryCard>

  <GalleryCard header="PasswordField" href="fields/passwordfield" image="/img/components/light/PasswordField.webp" imageDark="/img/components/dark/PasswordField.webp">
    <p>Een enkelregel invoercomponent voor veilig invoeren en maskeren van wachtwoordgegevens.</p>
  </GalleryCard>

  <GalleryCard header="DateField" href="fields/datefield" image="/img/components/light/DateField.webp" imageDark="/img/components/dark/DateField.webp">
    <p>Een component die een standaard browsergebaseerde datumkiezer biedt voor het selecteren van een datum via een invoerveld.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>DateField" href="fields/masked/datefield" image="/img/components/light/MaskedDateField.webp" imageDark="/img/components/dark/MaskedDateField.webp">
    <p>Een datuminvoerveld dat een specifiek datumformaat of patroon afdwingt, waardoor de gebruiker een geldige datum invoert volgens de gedefinieerde mask.</p>
  </GalleryCard>

  <GalleryCard header="TimeField" href="fields/timefield" image="/img/components/light/TimeField.webp" imageDark="/img/components/dark/TimeField.webp">
    <p>Een component die een standaard browsergebaseerde tijdkiezer biedt voor het selecteren van een tijdwaarde via een invoerveld.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TimeField" href="fields/masked/timefield" image="/img/components/light/MaskedTimeField.webp" imageDark="/img/components/dark/MaskedTimeField.webp">
    <p>Een tijdinvoerveld dat een specifiek tijdformaat of patroon afdwingt, waardoor de gebruiker een geldige tijd invoert volgens de gedefinieerde mask.</p>
  </GalleryCard>

  <GalleryCard header="DateTimeField" href="fields/datetimefield" image="/img/components/light/DateTimeField.webp" imageDark="/img/components/dark/DateTimeField.webp">
    <p>Een component die een standaard browsergebaseerde datum- en tijdkiezer biedt voor het selecteren van zowel datum als tijd via een enkel invoerveld.</p>
  </GalleryCard>

  <GalleryCard header="ColorField" href="fields/colorfield" image="/img/components/light/ColorField.webp" imageDark="/img/components/dark/ColorField.webp">
    <p>Een component die een standaard browsergebaseerde kleurkiezer biedt, waarmee gebruikers een kleur kunnen selecteren uit een invoerveld.</p>
  </GalleryCard>

  <GalleryCard header="TextArea" href="textarea" image="/img/components/light/TextArea.webp" imageDark="/img/components/dark/TextArea.webp">
    <p>Een multi-regel tekstinvoerveld waarmee gebruikers grotere tekstblokken kunnen invoeren of bewerken.</p>
  </GalleryCard>

  <GalleryCard header="CheckBox" href="checkbox" image="/img/components/light/CheckBox.webp" imageDark="/img/components/dark/CheckBox.webp">
    <p>Een component die een binaire optie vertegenwoordigt, waarmee gebruikers kunnen schakelen tussen een aangevinkte (waar) of niet-aangevinkte (onwaar) toestand.</p>
  </GalleryCard>

  <GalleryCard header="RadioButton" href="radiobutton" image="/img/components/light/RadioButton.webp" imageDark="/img/components/dark/RadioButton.webp">
    <p>Een component die gebruikers in staat stelt een enkele optie te selecteren uit een groep onderling uitsluitende keuzes.</p>
  </GalleryCard>

  <GalleryCard header="Switch" href="radiobutton#switches" image="/img/components/light/Switch.webp" imageDark="/img/components/dark/Switch.webp">
    <p>Een togglecomponent waarmee gebruikers tussen twee toestanden kunnen schakelen, zoals aan/uit of waar/onwaar, met een schuifactie.</p>
  </GalleryCard>

  <GalleryCard header="ChoiceBox" href="lists/choicebox" image="/img/components/light/ChoiceBox.webp" imageDark="/img/components/dark/ChoiceBox.webp">
    <p>Een component die een dropdownlijst met vooraf gedefinieerde opties biedt, waarmee gebruikers één optie uit de lijst kunnen selecteren.</p>
  </GalleryCard>

  <GalleryCard header="ComboBox" href="lists/combobox" image="/img/components/light/ComboBox.webp" imageDark="/img/components/dark/ComboBox.webp">
    <p>Een component die een dropdownlijst combineert met een bewerkbaar tekstinvoerveld, zodat gebruikers zowel een optie uit de lijst kunnen selecteren als een aangepaste waarde kunnen invoeren.</p>
  </GalleryCard>

  <GalleryCard header="ListBox" href="lists/listbox" image="/img/components/light/ListBox.webp" imageDark="/img/components/dark/ListBox.webp">
    <p>Een component die een doorbloembare lijst met opties weergeeft, waarmee gebruikers één of meerdere items uit de lijst kunnen selecteren.</p>
  </GalleryCard>

  <GalleryCard header="Upload" href="upload" image="/img/components/light/Upload.webp" imageDark="/img/components/dark/Upload.webp">
    <p>Een inline bestandskiezer waarmee gebruikers één of meerdere bestanden van hun lokale machine kunnen selecteren en naar de server kunnen uploaden, met slepen-en-neerzetten, filters en per-bestand evenementtracking.</p>
  </GalleryCard>
</GalleryGrid>

## Optiedialogen {#option-dialogs}

Optiedialogen bieden een manier om gebruikers keuzes voor te stellen of hen om bevestiging te vragen voordat ze met een actie doorgaan. Deze componenten zijn essentieel voor het creëren van interactieve, beslissinggedreven workflows, waarmee gebruikers bevestigen, annuleren of uit verschillende opties kunnen kiezen op een duidelijke en gestructureerde manier.

<GalleryGrid>
  <GalleryCard header="MessageDialog" href="option-dialogs/message" image="/img/components/light/MessageDialog.webp" imageDark="/img/components/dark/MessageDialog.webp">
    <p>Een dialoogcomponent die wordt gebruikt om informatieve berichten of waarschuwingen aan de gebruiker weer te geven, meestal met een enkele `OK`-knop om het bericht te bevestigen.</p>
  </GalleryCard>

  <GalleryCard header="ConfirmDialog" href="option-dialogs/confirm" image="/img/components/light/ConfirmDialog.webp" imageDark="/img/components/dark/ConfirmDialog.webp">
    <p>Een dialoogcomponent die aan de gebruiker vraagt om een actie te bevestigen of te annuleren, doorgaans met `Ja` en `Nee` of `OK` en `Annuleer` knoppen.</p>
  </GalleryCard>

  <GalleryCard header="InputDialog" href="option-dialogs/input" image="/img/components/light/InputDialog.webp" imageDark="/img/components/dark/InputDialog.webp">
    <p>Een dialoogcomponent die de gebruiker vraagt om tekst of gegevens in te voeren, meestal met een invoerveld en actieknoppen zoals `OK` en `Annuleer`.</p>
  </GalleryCard>

  <GalleryCard header="FileChooserDialog" href="option-dialogs/file-chooser" image="/img/components/light/FileChooserDialog.webp" imageDark="/img/components/dark/FileChooserDialog.webp">
    <p>Een dialoogcomponent die gebruikers in staat stelt om bestanden te doorbladeren en te selecteren vanuit het serverbestandssysteem.</p>
  </GalleryCard>

  <GalleryCard header="FileUploadDialog" href="option-dialogs/file-upload" image="/img/components/light/FileUploadDialog.webp" imageDark="/img/components/dark/FileUploadDialog.webp">
    <p>Een dialoogcomponent die gebruikers in staat stelt om bestanden van hun lokale bestandssysteem naar de app te uploaden.</p>
  </GalleryCard>

  <GalleryCard header="FileSaveDialog" href="option-dialogs/file-save" image="/img/components/light/FileSaveDialog.webp" imageDark="/img/components/dark/FileSaveDialog.webp">
    <p>Een dialoogcomponent die gebruikers in staat stelt om een bestand op een opgegeven locatie in het server bestandssysteem op te slaan.</p>
  </GalleryCard>
</GalleryGrid>

## Interactie en weergave {#interaction-and-display}

Deze categorie omvat componenten die gebruikersinteracties vergemakkelijken en gegevens of app-statussen visueel weergeven. Deze componenten helpen gebruikers de app te navigeren, acties te triggeren en voortgang of resultaten te begrijpen via dynamische visuele elementen.

<GalleryGrid>
  <GalleryCard header="Table" href="table/overview" image="/img/components/light/Table.webp" imageDark="/img/components/dark/Table.webp">
    <p>Een component die wordt gebruikt om gegevens in een gestructureerd, tabelvorm te weergeven met rijen en kolommen, met ondersteuning voor functies zoals sorteren en paginering.</p>
  </GalleryCard>

  <GalleryCard header="GoogleCharts" href="google-charts" image="/img/components/light/GoogleCharts.webp" imageDark="/img/components/dark/GoogleCharts.webp">
    <p>Een component die integreert met Google Charts om verschillende soorten grafieken en visuele gegevensrepresentaties in de app weer te geven.</p>
  </GalleryCard>

  <GalleryCard header="Button" href="button" image="/img/components/light/Button.webp" imageDark="/img/components/dark/Button.webp">
    <p>Een klikbare component die een actie of evenement triggert wanneer deze wordt ingedrukt.</p>
  </GalleryCard>

  <GalleryCard header="Toast" href="toast" image="/img/components/light/Toast.webp" imageDark="/img/components/dark/Toast.webp">
    <p>Een lichte, niet-blokkerende notificatiecomponent die kort een bericht aan de gebruiker weergeeft voordat deze automatisch verdwijnt.</p>
  </GalleryCard>

  <GalleryCard header="Alert" href="alert" image="/img/components/light/Alert.webp" imageDark="/img/components/dark/Alert.webp">
    <p>Een component die belangrijke berichten of waarschuwingen in een opvallend formaat weergeeft om de aandacht van de gebruiker te trekken.</p>
  </GalleryCard>

  <GalleryCard header="Badge" href="badge" image="/img/components/light/Badge.webp" imageDark="/img/components/dark/Badge.webp">
    <p>Een klein labelcomponent voor het weergeven van tellingen, statussen of korte metadata, met ondersteuning voor thema's, groottes en pictogrammen.</p>
  </GalleryCard>

  <GalleryCard header="DesktopNotification" href="desktop-notification" image="/img/components/light/DesktopNotification.webp" imageDark="/img/components/dark/DesktopNotification.webp">
    <p>Een component die gebruik maakt van de native Notification API van de browser om gebruikers te waarschuwen met aangepaste desktopnotificaties.</p>
  </GalleryCard>

  <GalleryCard header="Navigator" href="navigator" image="/img/components/light/Navigator.webp" imageDark="/img/components/dark/Navigator.webp">
    <p>Een aanpasbare pagineringcomponent voor het navigeren door gegevenssets, met ondersteuning voor lay-outs met knoppen voor eerste, laatste, volgende, vorige en snelle sprongvelden.</p>
  </GalleryCard>

  <GalleryCard header="ProgressBar" href="progressbar" image="/img/components/light/ProgressBar.webp" imageDark="/img/components/dark/ProgressBar.webp">
    <p>Een component die visueel de voortgang van een taak of proces weergeeft, meestal weergegeven als een horizontale balk die opvult naarmate er voortgang wordt gemaakt.</p>
  </GalleryCard>

  <GalleryCard header="Slider" href="slider" image="/img/components/light/Slider.webp" imageDark="/img/components/dark/Slider.webp">
    <p>Een component waarmee gebruikers een waarde vanuit een gedefinieerd bereik kunnen selecteren door een handvat langs een track te slepen.</p>
  </GalleryCard>

  <GalleryCard header="BusyIndicator" href="busyindicator" image="/img/components/light/BusyIndicator.webp" imageDark="/img/components/dark/BusyIndicator.webp">
    <p>Een visuele indicator voor de hele app, meestal een spinner, die aangeeft dat een wereldwijd proces gaande is.</p>
  </GalleryCard>

  <GalleryCard header="Loading" href="loading" image="/img/components/light/Loading.webp" imageDark="/img/components/dark/Loading.webp">
    <p>Een scoped laadindicator die binnen een specifieke bovenliggende component wordt weergegeven, wat aangeeft dat inhoud of gegevens worden geladen in dat gedeelte.</p>
  </GalleryCard>

  <GalleryCard header="Spinner" href="spinner" image="/img/components/light/Spinner.webp" imageDark="/img/components/dark/Spinner.webp">
    <p>Een component die een draaiende animatie weergeeft, meestal gebruikt om aan te geven dat een proces of actie aan de gang is.</p>
  </GalleryCard>

  <GalleryCard header="AppNav" href="appnav" image="/img/components/light/AppNav.webp" imageDark="/img/components/dark/AppNav.webp">
    <p>Een component die een navigatiemenu voor de app biedt, meestal gebruikt voor het opsommen van links of navigatie-items om over verschillende secties of weergaven te schakelen.</p>
  </GalleryCard>

  <GalleryCard header="Icon" href="icon" image="/img/components/light/Icon.webp" imageDark="/img/components/dark/Icon.webp">
    <p>Een component die een grafisch symbool of afbeelding weergeeft, vaak gebruikt om een actie, status of categorie in de gebruikersinterface weer te geven.</p>
  </GalleryCard>

  <GalleryCard header="Terminal" href="terminal" image="/img/components/light/Terminal.webp" imageDark="/img/components/dark/Terminal.webp">
    <p>Een component die een command-line interface (CLI) simuleert binnen de app, waarmee gebruikers tekstgebaseerde commando's kunnen invoeren en uitvoeren.</p>
  </GalleryCard>

  <GalleryCard header="InfiniteScroll" href="infinitescroll" image="/img/components/light/InfiniteScroll.webp" imageDark="/img/components/dark/InfiniteScroll.webp">
    <p>Een component die meer items laadt bij scrollen, een loader toont en bijhoudt wanneer alle inhoud is opgehaald.</p>
  </GalleryCard>

  <GalleryCard header="Refresher" href="refresher" image="/img/components/light/Refresher.webp" imageDark="/img/components/dark/Refresher.webp">
    <p>Een component die een pull-to-refresh interactie binnen scrollbare container mogelijk maakt—ideaal voor dynamische gegevensbelasting.</p>
  </GalleryCard>

  <GalleryCard header="Tree" href="tree" image="/img/components/light/Tree.webp" imageDark="/img/components/dark/Tree.webp">
    <p>Een component voor het weergeven van hiërarchische gegevens, waarmee gebruikers geneste items kunnen uitbreiden, inkrimpen en ermee kunnen communiceren.</p>
  </GalleryCard>

  <GalleryCard header="Avatar" href="avatar" image="/img/components/light/Avatar.webp" imageDark="/img/components/dark/Avatar.webp">
    <p>Een component voor het weergeven van profielfoto's of initialen van gebruikers, met ondersteuning voor verschillende groottes, vormen en thema's.</p>
  </GalleryCard>

  <GalleryCard header="MarkdownViewer" href="markdownviewer" image="/img/components/light/MarkdownViewer.webp" imageDark="/img/components/dark/MarkdownViewer.webp">
    <p>Een component voor het weergeven van markdown-inhoud met progressieve karakters-voor-karakterweergave, ideaal voor AI-chatinterfaces en streamingtekst.</p>
  </GalleryCard>

</GalleryGrid>
