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
  <title>UI Componenten | Componenten voor het bouwen van gebruikersinterface-applicaties</title>
</Head>

In webforJ worden applicaties gemaakt met behulp van modulaire eenheden die bekend staan als Componenten, die een snelle en efficiënte UI-ontwikkeling mogelijk maken. Het framework biedt een scala aan essentiële componenten zoals knoppen, invoerelementen en lay-outcontainers. Nadat je de basisprincipes hebt beheerst, kun je de [JavaDocs](https://javadoc.io/doc/com.webforj) raadplegen voor een gedetailleerd overzicht van alle componenten en hun functionaliteiten.

## Lay-outs {#layouts}

Lay-outcomponenten bieden de basis voor het structureren van gebruikersinterfaces, waardoor ontwikkelaars inhoud efficiënt kunnen organiseren. Deze componenten bieden verschillende manieren om de indeling van kindcomponenten te regelen, of het nu gaat om eenvoudige of complexe lay-outs.

De volgende lay-outcomponenten zijn ontworpen om een breed scala aan gebruikssituaties aan te pakken, van responsief ontwerp tot geavanceerd beheer van inhoud.

<GalleryGrid>
  <GalleryCard header="AppLayout" href="app-layout" image="/img/components/AppLayout.png">
    <p>Een containercomponent die een gestructureerde lay-out biedt voor de navigatie en organisatie van de inhoud van de bovenliggende app.</p>
  </GalleryCard>

  <GalleryCard header="Toolbar" href="toolbar" image="/img/components/Toolbar.png">
    <p>Een horizontale containercomponent die een reeks actieknoppen, pictogrammen of andere bedieningselementen bevat, die typisch worden gebruikt voor het uitvoeren van taken die verband houden met de huidige context.</p>
  </GalleryCard>

  <GalleryCard header="FlexLayout" href="flex-layout" image="/img/components/FlexLayout.png">
    <p>Een lay-outcomponent die zijn kinderen ordent met behulp van flexibele box (flexbox) regels voor responsief ontwerp en uitlijning.</p>
  </GalleryCard>

  <GalleryCard header="ColumnsLayout" href="columns-layout" image="/img/components/ColumnsLayout.png">
    <p>Een lay-outcomponent die zijn kinderen in meerdere verticale kolommen ordent, handig voor het maken van formulieren en rasterachtige structuren.</p>
  </GalleryCard>

  <GalleryCard header="Splitter" href="splitter" image="/img/components/Splitter.png" effect="slideLeftRightScale">
    <p>Een lay-outcomponent die de beschikbare ruimte tussen twee kindcomponenten verdeelt, zodat gebruikers deze kunnen aanpassen door de splitbalk te slepen.</p>
  </GalleryCard>

  <GalleryCard header="Drawer" href="drawer" image="/img/components/Drawer.png" effect="slideUp">
    <p>Een schuifpaneelcomponent die typisch wordt gebruikt voor zij-navigatie of het huisvesten van aanvullende inhoud die kan worden weergegeven of verborgen.</p>
  </GalleryCard>

  <GalleryCard header="Dialog" href="dialog" image="/img/components/Dialog.png">
    <p>Een modaal venstercomponent die bovenop inhoud wordt weergegeven om belangrijke informatie weer te geven of gebruikersinteractie te vragen, vaak met actie van de gebruiker om te sluiten.</p>
  </GalleryCard>

  <GalleryCard header="Login" href="login" image="/img/components/Login.png">
    <p>Een component die een vooraf gebouwde gebruikersinterface biedt voor gebruikersauthenticatie, meestal met velden voor gebruikersnaam en wachtwoord, samen met een verzendknop.</p>
  </GalleryCard>

  <GalleryCard header="TabbedPane" href="tabbedpane" image="/img/components/TabbedPane.png">
    <p>Een containercomponent die inhoud organiseert in meerdere tabbladen, zodat gebruikers tussen verschillende weergaven of secties kunnen schakelen.</p>
  </GalleryCard>
</GalleryGrid>

## Gegevensinvoer {#data-entry}

Gegevensinvoercomponenten bieden essentiële hulpmiddelen voor het vastleggen van gebruikersinvoer en het beheren van interacties binnen je app. Deze componenten zijn veelzijdig, waardoor het eenvoudig is om interactieve formulieren te bouwen en verschillende soorten gegevens te verzamelen.

<GalleryGrid>
  <GalleryCard header="TextField" href="fields/textfield" image="/img/components/TextField.png">
    <p>Een invoercomponent voor één regel voor het invoeren en bewerken van tekstgegevens.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TextField" href="fields/masked/textfield" image="/img/components/MaskedTextField.png">
    <p>Een tekstinvoerelement dat de gebruikersinvoer beperkt tot een specifiek formaat of patroon, meestal gebruikt voor velden zoals telefoonnummers, datums of creditcardnummers.</p>
  </GalleryCard>

  <GalleryCard header="NumberField" href="fields/numberfield" image="/img/components/NumberField.png">
    <p>Een component die een standaard invoerveld op basis van de browser biedt voor het invoeren van numerieke waarden, met ingebouwde bedieningselementen voor het verhogen of verlagen van de waarde.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>NumberField" href="fields/masked/numberfield" image="/img/components/MaskedNumberField.png">
    <p>Een numeriek invoerelement dat de gebruikersinvoer beperkt tot een specifiek numeriek formaat of patroon, waardoor geldige nummerinvoer zoals voor valuta, percentages of andere opgemaakte nummers wordt gegarandeerd.</p>
  </GalleryCard>

  <GalleryCard header="PasswordField" href="fields/passwordfield" image="/img/components/PasswordField.png">
    <p>Een invoercomponent voor één regel voor het veilig invoeren en maskeren van wachtwoordgegevens.</p>
  </GalleryCard>

  <GalleryCard header="DateField" href="fields/datefield" image="/img/components/DateField.png">
    <p>Een component die een standaard datepicker op basis van de browser biedt voor het selecteren van een datum via een invoerveld.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>DateField" href="fields/masked/datefield" image="/img/components/MaskedDateField.png">
    <p>Een datum invoerelement dat een specifiek datumformaat of patroon afdwingt, waardoor de gebruiker een geldige datum volgens de gedefinieerde mask moet invoeren.</p>
  </GalleryCard>

  <GalleryCard header="TimeField" href="fields/timefield" image="/img/components/TimeField.png">
    <p>Een component die een standaard tijdkiezer op basis van de browser biedt voor het selecteren van een tijdwaarde via een invoerveld.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TimeField" href="fields/masked/timefield" image="/img/components/MaskedTimeField.png">
    <p>Een tijdinvoerelement dat een specifiek tijdformaat of patroon afdwingt, waardoor de gebruiker een geldige tijd volgens de gedefinieerde mask moet invoeren.</p>
  </GalleryCard>

  <GalleryCard header="DateTimeField" href="fields/datetimefield" image="/img/components/DateTimeField.png">
    <p>Een component die een standaard date- en tijdkiezer op basis van de browser biedt voor het selecteren van zowel datum als tijd via één invoerveld.</p>
  </GalleryCard>

  <GalleryCard header="ColorField" href="fields/colorfield" image="/img/components/ColorField.png">
    <p>Een component die een standaard kleuroverzicht op basis van de browser biedt, waarmee gebruikers een kleur vanuit een invoerveld kunnen selecteren.</p>
  </GalleryCard>

  <GalleryCard header="TextArea" href="textarea" image="/img/components/TextArea.png">
    <p>Een multi-regel tekstinvoerelement dat gebruikers in staat stelt om grotere tekstblokken in te voeren of te bewerken.</p>
  </GalleryCard>

  <GalleryCard header="CheckBox" href="checkbox" image="/img/components/CheckBox.png">
    <p>Een component die een binaire optie vertegenwoordigt, waarmee gebruikers tussen een aangevinkte (waar) of niet aangevinkte (onwaar) staat kunnen schakelen.</p>
  </GalleryCard>

  <GalleryCard header="RadioButton" href="radiobutton" image="/img/components/RadioButton.png">
    <p>Een component die gebruikers in staat stelt om een enkele optie te selecteren uit een groep onderling exclusieve keuzes.</p>
  </GalleryCard>

  <GalleryCard header="Switch" href="radiobutton#switches" image="/img/components/Switch.png">
    <p>Een togglecomponent die gebruikers in staat stelt om tussen twee toestanden te schakelen, zoals aan/uit of waar/onwaar, met een schuifactie.</p>
  </GalleryCard>

  <GalleryCard header="ChoiceBox" href="lists/choicebox" image="/img/components/ChoiceBox.png">
    <p>Een component die een dropdownlijst met vooraf gedefinieerde opties biedt, waarmee gebruikers één optie uit de lijst kunnen selecteren.</p>
  </GalleryCard>

  <GalleryCard header="ComboBox" href="lists/combobox" image="/img/components/ComboBox.png">
    <p>Een component die een dropdownlijst combineert met een bewerkbaar tekstinvoerveld, zodat gebruikers ofwel een optie uit de lijst kunnen selecteren of een aangepaste waarde kunnen invoeren.</p>
  </GalleryCard>

  <GalleryCard header="ListBox" href="lists/listbox" image="/img/components/ListBox.png">
    <p>Een component die een scrollbare lijst met opties weergeeft, zodat gebruikers één of meerdere items uit de lijst kunnen selecteren.</p>
  </GalleryCard>
</GalleryGrid>

## Optiedialogen {#option-dialogs}

Optiedialogen bieden een manier om gebruikers keuzes voor te leggen of hen om bevestiging te vragen voordat ze doorgaan met een actie. Deze componenten zijn essentieel voor het creëren van interactieve, beslissingsgestuurde workflows, waarmee gebruikers kunnen bevestigen, annuleren of uit verschillende opties kunnen kiezen op een duidelijke en gestructureerde manier.

<GalleryGrid>
  <GalleryCard header="MessageDialog" href="option-dialogs/message" image="/img/components/MessageDialog.png">
    <p>Een dialoogcomponent die wordt gebruikt om informatieve berichten of waarschuwingen aan de gebruiker weer te geven, meestal met een enkele `OK`-knop om het bericht te bevestigen.</p>
  </GalleryCard>

  <GalleryCard header="ConfirmDialog" href="option-dialogs/confirm" image="/img/components/ConfirmDialog.png">
    <p>Een dialoogcomponent die de gebruiker vraagt om een actie te bevestigen of te annuleren, meestal met `Ja` en `Nee` of `OK` en `Annuleren` knoppen.</p>
  </GalleryCard>
  
  <GalleryCard header="InputDialog" href="option-dialogs/input" image="/img/components/InputDialog.png">
    <p>Een dialoogcomponent die de gebruiker vraagt om tekst of gegevens in te voeren, meestal met een invoerveld en actieknoppen zoals `OK` en `Annuleren`.</p>
  </GalleryCard>

  <GalleryCard header="FileChooserDialog" href="option-dialogs/file-chooser" image="/img/components/FileChooserDialog.png">
    <p>Een dialoogcomponent waarmee gebruikers bestanden kunnen doorbladeren en selecteren vanuit het bestanden systeem van de server.</p>
  </GalleryCard>

  <GalleryCard header="FileUploadDialog" href="option-dialogs/file-upload" image="/img/components/FileUploadDialog.png">
    <p>Een dialoogcomponent die gebruikers in staat stelt om bestanden van hun lokale besturingssysteem naar de app te uploaden.</p>
  </GalleryCard>

  <GalleryCard header="FileSaveDialog" href="option-dialogs/file-save" image="/img/components/FileSaveDialog.png">
    <p>Een dialoogcomponent die gebruikers in staat stelt om een bestand op een opgegeven locatie op het bestandssysteem van de server op te slaan.</p>
  </GalleryCard>
</GalleryGrid>

## Interactie en weergave {#interaction-and-display}

Deze categorie omvat componenten die gebruikersinteracties faciliteren en gegevens of app-staten visueel weergeven. Deze componenten helpen gebruikers om door de app te navigeren, acties te activeren en voortgang of resultaten te begrijpen via dynamische visuele elementen.

<GalleryGrid>
  <GalleryCard header="Table" href="table/overview" image="/img/components/Table.png">
    <p>Een component die wordt gebruikt om gegevens in een gestructureerd, tabelvormig formaat weer te geven met rijen en kolommen, met ondersteuning voor functies zoals sorteren en paginering.</p>
  </GalleryCard>

  <GalleryCard header="GoogleCharts" href="google-charts" image="/img/components/GoogleCharts.png">
    <p>Een component die integreert met Google Charts om verschillende soorten grafieken en visuele gegevenspresentaties in de app weer te geven.</p>
  </GalleryCard>

  <GalleryCard header="Button" href="button" image="/img/components/Button.png">
    <p>Een klikbare component die een actie of gebeurtenis activeert wanneer erop wordt gedrukt.</p>
  </GalleryCard>

  <GalleryCard header="Toast" href="toast" image="/img/components/Toast.png" effect="slideUp">
    <p>Een lichte, niet-blokkerende notificatiecomponent die kort een bericht aan de gebruiker weergeeft voordat deze automatisch verdwijnt.</p>
  </GalleryCard>

  <GalleryCard header="Alert" href="alert" image="/img/components/Alert.png">
    <p>Een component die belangrijke berichten of waarschuwingen op een opvallende manier weergeeft om de aandacht van de gebruiker te trekken.</p>
  </GalleryCard>

  <GalleryCard header="DesktopNotification" href="desktop-notification" image="/img/components/DesktopNotification.png">
    <p>Een component die gebruik maakt van de native Notification API van de browser om gebruikers te waarschuwen met aangepaste desktopnotificaties.</p>
  </GalleryCard>
  
  <GalleryCard header="Navigator" href="navigator" image="/img/components/Navigator.png">
    <p>Een aanpasbare pagineringcomponent voor navigatie door gegevenssets, met ondersteuning voor lay-outs met eerste, laatste, volgende, vorige knoppen en snelle sprongvelden.</p>
  </GalleryCard>

  <GalleryCard header="ProgressBar" href="progressbar" image="/img/components/ProgressBar.png">
    <p>Een component die de voortgang van een taak of proces visueel weergeeft, meestal weergegeven als een horizontale balk die zich vult naarmate de voortgang vordert.</p>
  </GalleryCard>

  <GalleryCard header="Slider" href="slider" image="/img/components/Slider.png">
    <p>Een component die gebruikers in staat stelt een waarde uit een gedefinieerd bereik te selecteren door een hendel langs een baan te slepen.</p>
  </GalleryCard>

  <GalleryCard header="BusyIndicator" href="busyindicator" image="/img/components/BusyIndicator.png">
    <p>Een visuele indicator voor de hele app, meestal een spinner, die aangeeft dat een wereldwijde proces gaande is.</p>
  </GalleryCard>

  <GalleryCard header="Loading" href="loading" image="/img/components/Loading.png">
    <p>Een beperkte laadindicator die binnen een specifieke bovenliggende component wordt weergegeven, waarmee wordt aangegeven dat inhoud of gegevens in dat gedeelte worden geladen.</p>
  </GalleryCard>

  <GalleryCard header="Spinner" href="spinner" image="/img/components/Spinner.png">
    <p>Een component die een draaiende animatie weergeeft, meestal gebruikt om aan te geven dat een proces of actie gaande is.</p>
  </GalleryCard>

  <GalleryCard header="AppNav" href="appnav" image="/img/components/AppNav.png" effect="slideFromLeft">
    <p>Een component die een navigatiemenu voor de app biedt, meestal gebruikt voor het opsommen van links of navigatie-items voor het schakelen tussen verschillende secties of weergaven.</p>
  </GalleryCard>

  <GalleryCard header="Icon" href="icon" image="/img/components/Icons.png">
    <p>Een component die een grafisch symbool of afbeelding weergeeft, vaak gebruikt om een actie, status of categorie in de gebruikersinterface weer te geven.</p>
  </GalleryCard>

  <GalleryCard header="Terminal" href="terminal" image="/img/components/Terminal.png">
    <p>Een component die een command-line interface (CLI) binnen de app simuleert, waardoor gebruikers tekstgebaseerde opdrachten kunnen invoeren en uitvoeren.</p>
  </GalleryCard>
  
  <GalleryCard header="InfiniteScroll" href="infinitescroll" image="/img/components/InfiniteScroll.png">
    <p>Een component die meer items laadt bij het scrollen, een lader weergeeft en bijhoudt wanneer alle inhoud is opgehaald.</p>
  </GalleryCard>

  <GalleryCard header="Refresher" href="refresher" image="/img/components/Refresher.png">
    <p>Een component die een pull-to-refresh interactie binnen scrollbare containers mogelijk maakt, ideaal voor dynamisch gegevensladen.</p>
  </GalleryCard>

  <GalleryCard header="Tree" href="tree" image="/img/components/Tree.png">
    <p>Een component voor het weergeven van hiërarchische gegevens, waarmee gebruikers geneste items kunnen uit- en inklappen en ermee kunnen interageren.</p>
  </GalleryCard>
</GalleryGrid>
