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
  <title>UI Componenten | Componenten voor het bouwen van gebruikersinterfaces</title>
</Head>

In webforJ worden applicaties gemaakt met behulp van modulaire eenheden die bekend staan als Componenten, die snelle en efficiënte UI-ontwikkeling vergemakkelijken. Het raamwerk biedt een scala aan essentiële componenten zoals knoppen, invoerelementen en lay-outcontainers. Na het beheersen van de basisprincipes kun je de [JavaDocs](https://javadoc.io/doc/com.webforj) raadplegen voor een gedetailleerd overzicht van alle componenten en hun functionaliteiten.

## Lay-outs {#layouts}

Lay-outcomponenten bieden de basis voor het structureren van gebruikersinterfaces, waardoor ontwikkelaars inhoud efficiënt kunnen organiseren. Deze componenten bieden verschillende manieren om de opstelling van kindcomponenten te beheren, zowel voor eenvoudige als complexe lay-outs.

De volgende lay-outcomponenten zijn ontworpen om een breed scala aan gebruiksgevallen aan te pakken, van responsief ontwerp tot geavanceerd contentbeheer.

<GalleryGrid>
  <GalleryCard header="AppLayout" href="app-layout" image="/img/components/AppLayout.png">
    <p>Een containercomponent die een gestructureerde lay-out biedt voor de navigatie en organisatie van de inhoud van de app.</p>
  </GalleryCard>

  <GalleryCard header="Toolbar" href="toolbar" image="/img/components/Toolbar.png">
    <p>Een horizontale containercomponent die een set actieknoppen, pictogrammen of andere bedieningselementen bevat, meestal gebruikt voor het uitvoeren van taken die verband houden met de huidige context.</p>
  </GalleryCard>

  <GalleryCard header="FlexLayout" href="flex-layout" image="/img/components/FlexLayout.png">
    <p>Een lay-outcomponent die zijn kinderen rangschikt met behulp van flexibele box (flexbox)-regels voor responsief ontwerp en uitlijning.</p>
  </GalleryCard>

  <GalleryCard header="ColumnsLayout" href="columns-layout" image="/img/components/ColumnsLayout.png">
    <p>Een lay-outcomponent die zijn kinderen in meerdere verticale kolommen rangschikt, nuttig voor het creëren van formulieren en rasterachtige structuren.</p>
  </GalleryCard>

  <GalleryCard header="Splitter" href="splitter" image="/img/components/Splitter.png" effect="slideLeftRightScale">
    <p>Een lay-outcomponent die de beschikbare ruimte tussen twee kindcomponenten verdeelt, waardoor gebruikers ze kunnen vergroten of verkleinen door de splitsbalk te slepen.</p>
  </GalleryCard>

  <GalleryCard header="Drawer" href="drawer" image="/img/components/Drawer.png" effect="slideUp">
    <p>Een schuifpaneelcomponent die meestal wordt gebruikt voor zij-navigatie of het huisvesten van extra inhoud die kan worden weergegeven of verborgen.</p>
  </GalleryCard>

  <GalleryCard header="Dialog" href="dialog" image="/img/components/Dialog.png">
    <p>Een modaal venstercomponent die inhoud overlayt om belangrijke informatie weer te geven of gebruikersinteractie te vragen, vaak met vereiste actie van de gebruiker om te sluiten.</p>
  </GalleryCard>

  <GalleryCard header="Login" href="login" image="/img/components/Login.png">
    <p>Een component die een vooraf gebouwde UI voor gebruikersauthenticatie biedt, meestal met velden voor gebruikersnaam en wachtwoord, samen met een verzendknop.</p>
  </GalleryCard>

  <GalleryCard header="TabbedPane" href="tabbedpane" image="/img/components/TabbedPane.png">
    <p>Een containercomponent die inhoud organiseert in meerdere tabbladen, waardoor gebruikers kunnen schakelen tussen verschillende views of secties.</p>
  </GalleryCard>
</GalleryGrid>

## Gegevensinvoer {#data-entry}

Gegevensinvoercomponenten bieden essentiële tools voor het vastleggen van gebruikersinvoer en het beheren van interacties binnen je app. Deze componenten zijn veelzijdig, waardoor het eenvoudig is om interactieve formulieren te bouwen en verschillende soorten gegevens te verzamelen.

<GalleryGrid>
  <GalleryCard header="TextField" href="fields/textfield" image="/img/components/TextField.png">
    <p>Een invoercomponent voor één regel voor het invoeren en bewerken van tekstgegevens.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TextField" href="fields/masked/textfield" image="/img/components/MaskedTextField.png">
    <p>Een tekstinvoercmpoent die de gebruikersinvoer beperkt tot een specifiek formaat of patroon, doorgaans gebruikt voor velden zoals telefoonnummers, datums of creditcardnummers.</p>
  </GalleryCard>

  <GalleryCard header="NumberField" href="fields/numberfield" image="/img/components/NumberField.png">
    <p>Een component die een standaard browsergebaseerd invoerveld biedt voor het invoeren van numerieke waarden, met ingebouwde bedieningselementen voor het verhogen of verlagen van de waarde.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>NumberField" href="fields/masked/numberfield" image="/img/components/MaskedNumberField.png">
    <p>Een numerieke invoercomponent die de gebruikersinvoer beperkt tot een specifiek numeriek formaat of patroon, om geldige nummerinvoer te waarborgen zoals voor valuta, percentages of andere geformatteerde nummers.</p>
  </GalleryCard>

  <GalleryCard header="PasswordField" href="fields/passwordfield" image="/img/components/PasswordField.png">
    <p>Een invoercomponent voor één regel voor veilig invoeren en maskeren van wachtwoordgegevens.</p>
  </GalleryCard>

  <GalleryCard header="DateField" href="fields/datefield" image="/img/components/DateField.png">
    <p>Een component die een standaard browsergebaseerde datumkiezer biedt voor het selecteren van een datum via een invoerveld.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>DateField" href="fields/masked/datefield" image="/img/components/MaskedDateField.png">
    <p>Een datum invoercomponent die een specifiek datumformaat of patroon afdwingt, om ervoor te zorgen dat de gebruiker een geldige datum invoert volgens de gedefinieerde mask.</p>
  </GalleryCard>

  <GalleryCard header="TimeField" href="fields/timefield" image="/img/components/TimeField.png">
    <p>Een component die een standaard browsergebaseerde tijdkiezer biedt voor het selecteren van een tijdwaarde via een invoerveld.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TimeField" href="fields/masked/timefield" image="/img/components/MaskedTimeField.png">
    <p>Een tijd invoercomponent die een specifiek tijdformaat of patroon afdwingt, om ervoor te zorgen dat de gebruiker een geldige tijd invoert volgens de gedefinieerde mask.</p>
  </GalleryCard>

  <GalleryCard header="DateTimeField" href="fields/datetimefield" image="/img/components/DateTimeField.png">
    <p>Een component die een standaard browsergebaseerde datum- en tijdkiezer biedt voor het selecteren van zowel datum als tijd via één invoerveld.</p>
  </GalleryCard>

  <GalleryCard header="ColorField" href="fields/colorfield" image="/img/components/ColorField.png">
    <p>Een component die een standaard browsergebaseerde kleurkiezer biedt, waardoor gebruikers een kleur kunnen selecteren vanuit een invoerveld.</p>
  </GalleryCard>

  <GalleryCard header="TextArea" href="textarea" image="/img/components/TextArea.png">
    <p>Een multi-regel tekstinvoerveld dat gebruikers in staat stelt om grotere tekstblokken in te voeren of te bewerken.</p>
  </GalleryCard>

  <GalleryCard header="CheckBox" href="checkbox" image="/img/components/CheckBox.png">
    <p>Een component die een binaire optie vertegenwoordigt, waarmee gebruikers kunnen schakelen tussen een aangevinkte (waar) of niet aangevinkte (onwaar) status.</p>
  </GalleryCard>

  <GalleryCard header="RadioButton" href="radiobutton" image="/img/components/RadioButton.png">
    <p>Een component die gebruikers in staat stelt om een enkele optie te selecteren uit een groep onderling exclusieve keuzes.</p>
  </GalleryCard>

  <GalleryCard header="Switch" href="radiobutton#switches" image="/img/components/Switch.png">
    <p>Een schakelingcomponent die gebruikers in staat stelt om tussen twee toestanden te schakelen, zoals aan/uit of waar/onwaar, met een schuifactie.</p>
  </GalleryCard>

  <GalleryCard header="ChoiceBox" href="lists/choicebox" image="/img/components/ChoiceBox.png">
    <p>Een component die een dropdownlijst met vooraf gedefinieerde opties biedt, zodat gebruikers een optie uit de lijst kunnen selecteren.</p>
  </GalleryCard>

  <GalleryCard header="ComboBox" href="lists/combobox" image="/img/components/ComboBox.png">
    <p>Een component die een dropdownlijst combineert met een bewerkbaar tekstinvoerveld, zodat gebruikers een optie uit de lijst kunnen selecteren of een aangepaste waarde kunnen invoeren.</p>
  </GalleryCard>

  <GalleryCard header="ListBox" href="lists/listbox" image="/img/components/ListBox.png">
    <p>Een component die een doorlopende lijst van opties weergeeft, waarmee gebruikers een of meer items uit de lijst kunnen selecteren.</p>
  </GalleryCard>
</GalleryGrid>

## Optiedialogen {#option-dialogs}

Optiedialogen bieden een manier om gebruikers keuzes voor te leggen of hen om bevestiging te vragen voordat ze met een actie doorgaan. Deze componenten zijn essentieel voor het creëren van interactieve, besluitgestuurde workflows, waarmee gebruikers kunnen bevestigen, annuleren of uit verschillende opties kunnen kiezen op een duidelijke en gestructureerde manier.

<GalleryGrid>
  <GalleryCard header="MessageDialog" href="option-dialogs/message" image="/img/components/MessageDialog.png">
    <p>Een dialoogcomponent die wordt gebruikt om informatieve berichten of waarschuwingen aan de gebruiker weer te geven, meestal met een enkele `OK`-knop om het bericht te bevestigen.</p>
  </GalleryCard>

  <GalleryCard header="ConfirmDialog" href="option-dialogs/confirm" image="/img/components/ConfirmDialog.png">
    <p>Een dialoogcomponent die de gebruiker vraagt om een actie te bevestigen of te annuleren, doorgaans met knoppen `Ja` en `Nee` of `OK` en `Annuleren.`</p>
  </GalleryCard>
  
  <GalleryCard header="InputDialog" href="option-dialogs/input" image="/img/components/InputDialog.png">
    <p>Een dialoogcomponent die de gebruiker vraagt om tekst of gegevens in te voeren, meestal met een invoerveld en actieknoppen zoals `OK` en `Annuleren.`</p>
  </GalleryCard>

  <GalleryCard header="FileChooserDialog" href="option-dialogs/file-chooser" image="/img/components/FileChooserDialog.png">
    <p>Een dialoogcomponent waarmee gebruikers bestanden van het serversysteem kunnen doorbladeren en selecteren.</p>
  </GalleryCard>

  <GalleryCard header="FileUploadDialog" href="option-dialogs/file-upload" image="/img/components/FileUploadDialog.png">
    <p>Een dialoogcomponent waarmee gebruikers bestanden van hun lokale systeem naar de app kunnen uploaden.</p>
  </GalleryCard>

  <GalleryCard header="FileSaveDialog" href="option-dialogs/file-save" image="/img/components/FileSaveDialog.png">
    <p>Een dialoogcomponent die gebruikers in staat stelt om een bestand op een specifieke locatie op het serversysteem op te slaan.</p>
  </GalleryCard>
</GalleryGrid>

## Interactie en weergave {#interaction-and-display}

Deze categorie omvat componenten die gebruikersinteracties faciliteren en gegevens of app-statussen visueel weergeven. Deze componenten helpen gebruikers de app te navigeren, acties te triggeren en voortgang of resultaten te begrijpen via dynamische visuele elementen.

<GalleryGrid>
  <GalleryCard header="Table" href="table/overview" image="/img/components/Table.png">
    <p> Een component die gegevens in een gestructureerd, tabelvormig formaat met rijen en kolommen weergeeft, met ondersteuning voor functies zoals sorteren en paginering.</p>
  </GalleryCard>

  <GalleryCard header="GoogleCharts" href="google-charts" image="/img/components/GoogleCharts.png">
    <p>Een component die integreert met Google Charts om verschillende soorten grafieken en visuele gegevensrepresentaties in de app weer te geven.</p>
  </GalleryCard>

  <GalleryCard header="Button" href="button" image="/img/components/Button.png">
    <p>Een klikbare component die een actie of gebeurtenis triggert wanneer deze wordt ingedrukt.</p>
  </GalleryCard>

  <GalleryCard header="Toast" href="toast" image="/img/components/Toast.png" effect="slideUp">
    <p>Een lichte, niet-blokkerende notificatiecomponent die kort een bericht aan de gebruiker weergeeft voordat het automatisch verdwijnt.</p>
  </GalleryCard>

  <GalleryCard header="Alert" href="alert" image="/img/components/Alert.png">
    <p>Een component die belangrijke berichten of waarschuwingen in een opvallend formaat weergeeft om de aandacht van de gebruiker te trekken.</p>
  </GalleryCard>

  <GalleryCard header="DesktopNotification" href="desktop-notification" image="/img/components/DesktopNotification.png">
    <p>Een component die gebruik maakt van de native Notification API van de browser om gebruikers te waarschuwen met aangepaste desktopmeldingen.</p>
  </GalleryCard>
  
  <GalleryCard header="Navigator" href="navigator" image="/img/components/Navigator.png">
    <p>Een aanpasbare pagineringcomponent voor het navigeren door datasets, met ondersteuning voor lay-outs met knoppen voor eerst, laatst, volgende en vorige, en sneljump-velden.</p>
  </GalleryCard>

  <GalleryCard header="ProgressBar" href="progressbar" image="/img/components/ProgressBar.png">
    <p>Een component die visueel de voortgang van een taak of proces weergeeft, meestal weergegeven als een horizontale balk die vult naarmate de voortgang vordert.</p>
  </GalleryCard>

  <GalleryCard header="Slider" href="slider" image="/img/components/Slider.png">
    <p>Een component die gebruikers in staat stelt om een waarde uit een gedefinieerd bereik te selecteren door een handvat langs een baan te slepen.</p>
  </GalleryCard>

  <GalleryCard header="BusyIndicator" href="busyindicator" image="/img/components/BusyIndicator.png">
    <p> Een app-brede visuele indicator, meestal een spinner, die aangeeft dat een globaal proces aan de gang is.</p>
  </GalleryCard>

  <GalleryCard header="Loading" href="loading" image="/img/components/Loading.png">
    <p>Een scoped laadindicator die binnen een specifieke bovenliggende component toont dat inhoud of gegevens in dat gedeelte wordt geladen.</p>
  </GalleryCard>

  <GalleryCard header="Spinner" href="spinner" image="/img/components/Spinner.png">
    <p>Een component die een draaiende animatie toont, meestal gebruikt om aan te geven dat een proces of actie in uitvoering is.</p>
  </GalleryCard>

  <GalleryCard header="AppNav" href="appnav" image="/img/components/AppNav.png" effect="slideFromLeft">
    <p>Een component die een navigatiemenu voor de app biedt, meestal gebruikt voor het opsommen van links of navigatie-items voor het schakelen tussen verschillende secties of weergaven.</p>
  </GalleryCard>

  <GalleryCard header="Icon" href="icon" image="/img/components/Icons.png">
    <p>Een component die een grafisch symbool of afbeelding weergeeft, vaak gebruikt om een actie, status of categorie in de gebruikersinterface weer te geven.</p>
  </GalleryCard>

  <GalleryCard header="Terminal" href="terminal" image="/img/components/Terminal.png">
    <p>Een component die een command-line interface (CLI) simuleert binnen de app, waarmee gebruikers tekstgebaseerde opdrachten kunnen invoeren en uitvoeren.</p>
  </GalleryCard>
  
  <GalleryCard header="InfiniteScroll" href="infinitescroll" image="/img/components/InfiniteScroll.png">
    <p>Een component die meer items laadt bij scrollen, een loader toont en bijhoudt wanneer alle inhoud is opgehaald.</p>
  </GalleryCard>

  <GalleryCard header="Refresher" href="refresher" image="/img/components/Refresher.png">
    <p>Een component die een pull-to-refresh interactie binnen doorbladerbare containers mogelijk maakt - ideaal voor dynamisch laden van gegevens.</p>
  </GalleryCard>

  <GalleryCard header="Tree" href="tree" image="/img/components/Tree.png">
    <p>Een component voor het weergeven van hiërarchische gegevens, waarmee gebruikers geneste items kunnen uitbreiden, inkrimpen en ermee kunnen communiceren.</p>
  </GalleryCard>
  
  <GalleryCard header="Avatar" href="avatar" image="/img/components/Avatar.png">
    <p>Een component voor het weergeven van profielfoto's of initialen van gebruikers, met ondersteuning voor verschillende maten, vormen en thema's.</p>
  </GalleryCard>
  
</GalleryGrid>
