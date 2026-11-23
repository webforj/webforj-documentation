---
title: UI-componenten
sidebar_position: 85
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Bekijk de catalogus met webforJ UI-componenten voor lay-outs, gegevensinvoer,
  navigatie, feedback en visualisatie.
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
  <title>Java UI-componenten | webforJ</title>
</Head>

In webforJ worden toepassingen gemaakt met modulaire eenheden die Componenten worden genoemd. Hiermee kun je snel en efficiënt UI's ontwikkelen. Het framework biedt een reeks essentiële componenten, zoals knoppen, invoerelementen en lay-outcontainers. Nadat je de basis onder de knie hebt, kun je de [JavaDocs](https://javadoc.io/doc/com.webforj) raadplegen voor een gedetailleerd overzicht van alle componenten en hun functionaliteiten.

## Lay-outs {#layouts}

Lay-outcomponenten bieden de basis voor het structureren van gebruikersinterfaces, zodat ontwikkelaars inhoud efficiënt kunnen ordenen. Deze componenten bieden verschillende manieren om de indeling van onderliggende componenten te bepalen, voor zowel eenvoudige als complexe lay-outs.

De volgende lay-outcomponenten zijn geschikt voor uiteenlopende toepassingen, van responsief ontwerp tot geavanceerd inhoudsbeheer.

<GalleryGrid>
  <GalleryCard header="AppLayout" href="app-layout" image="/img/components/light/AppLayout.webp" imageDark="/img/components/dark/AppLayout.webp">
    <p>Een containercomponent die een gestructureerde lay-out biedt voor de navigatie op het hoogste niveau van de app en de ordening van inhoud.</p>
  </GalleryCard>

  <GalleryCard header="Toolbar" href="toolbar" image="/img/components/light/Toolbar.webp" imageDark="/img/components/dark/Toolbar.webp">
    <p>Een horizontale containercomponent met een reeks actieknoppen, pictogrammen of andere bedieningselementen. Deze wordt doorgaans gebruikt voor taken die betrekking hebben op de huidige context.</p>
  </GalleryCard>

  <GalleryCard header="FlexLayout" href="flex-layout" image="/img/components/light/FlexLayout.webp" imageDark="/img/components/dark/FlexLayout.webp">
    <p>Een lay-outcomponent die de onderliggende elementen indeelt met flexbox-regels voor responsief ontwerp en uitlijning.</p>
  </GalleryCard>

  <GalleryCard header="ColumnsLayout" href="columns-layout" image="/img/components/light/ColumnsLayout.webp" imageDark="/img/components/dark/ColumnsLayout.webp">
    <p>Een lay-outcomponent die de onderliggende elementen in meerdere verticale kolommen indeelt. Handig voor het maken van formulieren en rasterachtige structuren.</p>
  </GalleryCard>

  <GalleryCard header="Splitter" href="splitter" image="/img/components/light/Splitter.webp" imageDark="/img/components/dark/Splitter.webp">
    <p>Een lay-outcomponent die de beschikbare ruimte verdeelt over twee onderliggende componenten. Gebruikers kunnen de grootte ervan aanpassen door de scheidingsbalk te verslepen.</p>
  </GalleryCard>

  <GalleryCard header="Drawer" href="drawer" image="/img/components/light/Drawer.webp" imageDark="/img/components/dark/Drawer.webp">
    <p>Een uitschuifbaar paneel dat doorgaans wordt gebruikt voor navigatie aan de zijkant of voor extra inhoud die kan worden weergegeven of verborgen.</p>
  </GalleryCard>

  <GalleryCard header="Dialog" href="dialog" image="/img/components/light/Dialog.webp" imageDark="/img/components/dark/Dialog.webp">
    <p>Een modaal venster dat over de inhoud wordt weergegeven om belangrijke informatie te tonen of om gebruikers om een actie te vragen. De gebruiker moet het venster vaak zelf sluiten.</p>
  </GalleryCard>

  <GalleryCard header="Login" href="login" image="/img/components/light/Login.webp" imageDark="/img/components/dark/Login.webp">
    <p>Een component met een kant-en-klare UI voor gebruikersauthenticatie, doorgaans met velden voor een gebruikersnaam en wachtwoord en een verzendknop.</p>
  </GalleryCard>

  <GalleryCard header="Accordion" href="accordion" image="/img/components/light/Accordion.webp" imageDark="/img/components/dark/Accordion.webp">
    <p>Een verticaal gestapelde reeks inklapbare panelen, elk met een klikbare koptekst waarmee de zichtbaarheid van de inhoud kan worden omgeschakeld.</p>
  </GalleryCard>

  <GalleryCard header="TabbedPane" href="tabbedpane" image="/img/components/light/TabbedPane.webp" imageDark="/img/components/dark/TabbedPane.webp">
    <p>Een containercomponent die inhoud in meerdere tabbladen ordent, zodat gebruikers tussen verschillende weergaven of secties kunnen schakelen.</p>
  </GalleryCard>

  <GalleryCard header="Card" href="card" image="/img/components/light/Card.webp" imageDark="/img/components/dark/Card.webp">
    <p>Een oppervlak dat gerelateerde inhoud en acties groepeert, met gedeelten voor media, kopteksten, hoofdinhoud en voetteksten.</p>
  </GalleryCard>
</GalleryGrid>

## Gegevensinvoer {#data-entry}

Componenten voor gegevensinvoer bieden essentiële hulpmiddelen om gebruikersinvoer vast te leggen en interacties in je app te beheren. Deze veelzijdige componenten maken het eenvoudig om interactieve formulieren te bouwen en verschillende soorten gegevens te verzamelen.

<GalleryGrid>
  <GalleryCard header="TextField" href="fields/textfield" image="/img/components/light/TextField.webp" imageDark="/img/components/dark/TextField.webp">
    <p>Een invoercomponent voor één regel om tekstgegevens in te voeren en te bewerken.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TextField" href="fields/masked/textfield" image="/img/components/light/MaskedTextField.webp" imageDark="/img/components/dark/MaskedTextField.webp">
    <p>Een tekstinvoercomponent die gebruikersinvoer beperkt tot een specifieke indeling of een bepaald patroon. Deze wordt doorgaans gebruikt voor velden zoals telefoonnummers, datums of creditcardnummers.</p>
  </GalleryCard>

  <GalleryCard header="NumberField" href="fields/numberfield" image="/img/components/light/NumberField.webp" imageDark="/img/components/dark/NumberField.webp">
    <p>Een component met een standaard invoerveld van de browser voor numerieke waarden, inclusief ingebouwde bedieningselementen om de waarde te verhogen of te verlagen.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>NumberField" href="fields/masked/numberfield" image="/img/components/light/MaskedNumberField.webp" imageDark="/img/components/dark/MaskedNumberField.webp">
    <p>Een numerieke invoercomponent die gebruikersinvoer beperkt tot een specifieke numerieke indeling of een bepaald patroon. Zo wordt gegarandeerd dat geldige getallen worden ingevoerd, bijvoorbeeld voor valuta, percentages of andere getalnotaties.</p>
  </GalleryCard>

  <GalleryCard header="PasswordField" href="fields/passwordfield" image="/img/components/light/PasswordField.webp" imageDark="/img/components/dark/PasswordField.webp">
    <p>Een invoercomponent voor één regel om wachtwoorden veilig in te voeren en af te schermen.</p>
  </GalleryCard>

  <GalleryCard header="DateField" href="fields/datefield" image="/img/components/light/DateField.webp" imageDark="/img/components/dark/DateField.webp">
    <p>Een component met een standaard datumkiezer van de browser om via een invoerveld een datum te selecteren.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>DateField" href="fields/masked/datefield" image="/img/components/light/MaskedDateField.webp" imageDark="/img/components/dark/MaskedDateField.webp">
    <p>Een datumcomponent die een specifieke datumnotatie of een bepaald patroon afdwingt, zodat de gebruiker een geldige datum invoert volgens het gedefinieerde masker.</p>
  </GalleryCard>

  <GalleryCard header="TimeField" href="fields/timefield" image="/img/components/light/TimeField.webp" imageDark="/img/components/dark/TimeField.webp">
    <p>Een component met een standaard tijdkiezer van de browser om via een invoerveld een tijd te selecteren.</p>
  </GalleryCard>

  <GalleryCard header="<span style='color: var(--ifm-color-primary)'>Masked</span>TimeField" href="fields/masked/timefield" image="/img/components/light/MaskedTimeField.webp" imageDark="/img/components/dark/MaskedTimeField.webp">
    <p>Een tijdinvoercomponent die een specifieke tijdnotatie of een bepaald patroon afdwingt, zodat de gebruiker een geldige tijd invoert volgens het gedefinieerde masker.</p>
  </GalleryCard>

  <GalleryCard header="DateTimeField" href="fields/datetimefield" image="/img/components/light/DateTimeField.webp" imageDark="/img/components/dark/DateTimeField.webp">
    <p>Een component met een standaard datum- en tijdkiezer van de browser om via één invoerveld zowel een datum als een tijd te selecteren.</p>
  </GalleryCard>

  <GalleryCard header="ColorField" href="fields/colorfield" image="/img/components/light/ColorField.webp" imageDark="/img/components/dark/ColorField.webp">
    <p>Een component met een standaard kleurenkiezer van de browser waarmee gebruikers via een invoerveld een kleur kunnen selecteren.</p>
  </GalleryCard>

  <GalleryCard header="TextArea" href="textarea" image="/img/components/light/TextArea.webp" imageDark="/img/components/dark/TextArea.webp">
    <p>Een tekstinvoercomponent met meerdere regels waarmee gebruikers grotere tekstblokken kunnen invoeren of bewerken.</p>
  </GalleryCard>

  <GalleryCard header="CheckBox" href="checkbox" image="/img/components/light/CheckBox.webp" imageDark="/img/components/dark/CheckBox.webp">
    <p>Een component die een binaire optie weergeeft, zodat gebruikers kunnen schakelen tussen de aangevinkte status (true) en de niet-aangevinkte status (false).</p>
  </GalleryCard>

  <GalleryCard header="RadioButton" href="radiobutton" image="/img/components/light/RadioButton.webp" imageDark="/img/components/dark/RadioButton.webp">
    <p>Een component waarmee gebruikers één optie kunnen selecteren uit een groep onderling uitsluitende keuzes.</p>
  </GalleryCard>

  <GalleryCard header="Switch" href="radiobutton#switches" image="/img/components/light/Switch.webp" imageDark="/img/components/dark/Switch.webp">
    <p>Een schakelcomponent waarmee gebruikers met een schuifbeweging tussen twee statussen kunnen wisselen, zoals aan/uit of true/false.</p>
  </GalleryCard>

  <GalleryCard header="ChoiceBox" href="lists/choicebox" image="/img/components/light/ChoiceBox.webp" imageDark="/img/components/dark/ChoiceBox.webp">
    <p>Een component met een vervolgkeuzelijst met vooraf gedefinieerde opties, waaruit gebruikers één optie kunnen selecteren.</p>
  </GalleryCard>

  <GalleryCard header="ComboBox" href="lists/combobox" image="/img/components/light/ComboBox.webp" imageDark="/img/components/dark/ComboBox.webp">
    <p>Een component die een vervolgkeuzelijst combineert met een bewerkbaar tekstinvoerveld. Gebruikers kunnen een optie uit de lijst selecteren of een aangepaste waarde invoeren.</p>
  </GalleryCard>

  <GalleryCard header="ListBox" href="lists/listbox" image="/img/components/light/ListBox.webp" imageDark="/img/components/dark/ListBox.webp">
    <p>Een component die een schuifbare lijst met opties weergeeft, waaruit gebruikers een of meer items kunnen selecteren.</p>
  </GalleryCard>

  <GalleryCard header="Upload" href="upload" image="/img/components/light/Upload.webp" imageDark="/img/components/dark/Upload.webp">
    <p>Een inline bestandskiezer waarmee gebruikers een of meer bestanden op hun lokale computer kunnen selecteren en naar de server kunnen uploaden, met ondersteuning voor slepen en neerzetten, filters en gebeurtenistracering per bestand.</p>
  </GalleryCard>
</GalleryGrid>

## Optiedialogen {#option-dialogs}

Optiedialogen bieden een manier om gebruikers keuzes voor te leggen of om bevestiging te vragen voordat een actie wordt uitgevoerd. Deze componenten zijn essentieel voor interactieve workflows waarbij beslissingen moeten worden genomen. Ze stellen gebruikers in staat om op een duidelijke en gestructureerde manier te bevestigen, te annuleren of uit verschillende opties te kiezen.

<GalleryGrid>
  <GalleryCard header="MessageDialog" href="option-dialogs/message" image="/img/components/light/MessageDialog.webp" imageDark="/img/components/dark/MessageDialog.webp">
    <p>Een dialoogcomponent waarmee informatieve berichten of waarschuwingen aan de gebruiker worden getoond, meestal met één knop `OK` om het bericht te bevestigen.</p>
  </GalleryCard>

  <GalleryCard header="ConfirmDialog" href="option-dialogs/confirm" image="/img/components/light/ConfirmDialog.webp" imageDark="/img/components/dark/ConfirmDialog.webp">
    <p>Een dialoogcomponent die de gebruiker vraagt een actie te bevestigen of te annuleren, meestal met de knoppen `Ja` en `Nee` of `OK` en `Annuleren`.</p>
  </GalleryCard>

  <GalleryCard header="InputDialog" href="option-dialogs/input" image="/img/components/light/InputDialog.webp" imageDark="/img/components/dark/InputDialog.webp">
    <p>Een dialoogcomponent die de gebruiker vraagt tekst of gegevens in te voeren, meestal met een invoerveld en actieknoppen zoals `OK` en `Annuleren`.</p>
  </GalleryCard>

  <GalleryCard header="FileChooserDialog" href="option-dialogs/file-chooser" image="/img/components/light/FileChooserDialog.webp" imageDark="/img/components/dark/FileChooserDialog.webp">
    <p>Een dialoogcomponent waarmee gebruikers bestanden in het bestandssysteem van de server kunnen zoeken en selecteren.</p>
  </GalleryCard>

  <GalleryCard header="FileUploadDialog" href="option-dialogs/file-upload" image="/img/components/light/FileUploadDialog.webp" imageDark="/img/components/dark/FileUploadDialog.webp">
    <p>Een dialoogcomponent waarmee gebruikers bestanden van hun lokale bestandssysteem naar de app kunnen uploaden.</p>
  </GalleryCard>

  <GalleryCard header="FileSaveDialog" href="option-dialogs/file-save" image="/img/components/light/FileSaveDialog.webp" imageDark="/img/components/dark/FileSaveDialog.webp">
    <p>Een dialoogcomponent waarmee gebruikers een bestand op een opgegeven locatie in het bestandssysteem van de server kunnen opslaan.</p>
  </GalleryCard>
</GalleryGrid>

## Interactie en weergave {#interaction-and-display}

Deze categorie bevat componenten die gebruikersinteractie mogelijk maken en gegevens of app-statussen visueel weergeven. Met deze componenten kunnen gebruikers door de app navigeren, acties uitvoeren en voortgang of resultaten begrijpen aan de hand van dynamische visuele elementen.

<GalleryGrid>
  <GalleryCard header="Table" href="table/overview" image="/img/components/light/Table.webp" imageDark="/img/components/dark/Table.webp">
    <p>Een component om gegevens in een gestructureerd tabeloverzicht met rijen en kolommen weer te geven, met functies zoals sorteren en paginering.</p>
  </GalleryCard>

  <GalleryCard header="GoogleCharts" href="google-charts" image="/img/components/light/GoogleCharts.webp" imageDark="/img/components/dark/GoogleCharts.webp">
    <p>Een component die integreert met Google Charts om verschillende soorten grafieken en visuele gegevensweergaven in de app te tonen.</p>
  </GalleryCard>

  <GalleryCard header="Button" href="button" image="/img/components/light/Button.webp" imageDark="/img/components/dark/Button.webp">
    <p>Een klikbare component die bij het indrukken een actie uitvoert of een gebeurtenis activeert.</p>
  </GalleryCard>

  <GalleryCard header="Toast" href="toast" image="/img/components/light/Toast.webp" imageDark="/img/components/dark/Toast.webp">
    <p>Een lichtgewicht, niet-blokkerende meldingscomponent die kort een bericht aan de gebruiker toont en daarna automatisch verdwijnt.</p>
  </GalleryCard>

  <GalleryCard header="Alert" href="alert" image="/img/components/light/Alert.webp" imageDark="/img/components/dark/Alert.webp">
    <p>Een component die belangrijke berichten of waarschuwingen opvallend weergeeft om de aandacht van de gebruiker te trekken.</p>
  </GalleryCard>

  <GalleryCard header="Badge" href="badge" image="/img/components/light/Badge.webp" imageDark="/img/components/dark/Badge.webp">
    <p>Een klein label voor het weergeven van aantallen, statussen of korte metagegevens, met ondersteuning voor thema's, formaten en pictogrammen.</p>
  </GalleryCard>

  <GalleryCard header="DesktopNotification" href="desktop-notification" image="/img/components/light/DesktopNotification.webp" imageDark="/img/components/dark/DesktopNotification.webp">
    <p>Een component die de native Notification API van de browser gebruikt om gebruikers via aangepaste bureaubladmeldingen te waarschuwen.</p>
  </GalleryCard>

  <GalleryCard header="Navigator" href="navigator" image="/img/components/light/Navigator.webp" imageDark="/img/components/dark/Navigator.webp">
    <p>Een aanpasbare pagineringscomponent om door gegevenssets te navigeren, met lay-outs met knoppen voor eerste, laatste, volgende en vorige, en velden om snel naar een pagina te springen.</p>
  </GalleryCard>

  <GalleryCard header="ProgressBar" href="progressbar" image="/img/components/light/ProgressBar.webp" imageDark="/img/components/dark/ProgressBar.webp">
    <p>Een component die de voortgang van een taak of proces visueel weergeeft, meestal als een horizontale balk die verder wordt gevuld naarmate de voortgang toeneemt.</p>
  </GalleryCard>

  <GalleryCard header="Slider" href="slider" image="/img/components/light/Slider.webp" imageDark="/img/components/dark/Slider.webp">
    <p>Een component waarmee gebruikers een waarde uit een bepaald bereik kunnen selecteren door een greep langs een rail te verslepen.</p>
  </GalleryCard>

  <GalleryCard header="BusyIndicator" href="busyindicator" image="/img/components/light/BusyIndicator.webp" imageDark="/img/components/dark/BusyIndicator.webp">
    <p>Een visuele indicator voor de hele app, meestal een draaiknop, die aangeeft dat er een globaal proces bezig is.</p>
  </GalleryCard>

  <GalleryCard header="Loading" href="loading" image="/img/components/light/Loading.webp" imageDark="/img/components/dark/Loading.webp">
    <p>Een laadindicator die binnen een specifieke bovenliggende component wordt weergegeven om aan te geven dat inhoud of gegevens in dat gedeelte worden geladen.</p>
  </GalleryCard>

  <GalleryCard header="Spinner" href="spinner" image="/img/components/light/Spinner.webp" imageDark="/img/components/dark/Spinner.webp">
    <p>Een component die een draaiende animatie weergeeft, meestal om aan te geven dat een proces of actie bezig is.</p>
  </GalleryCard>

  <GalleryCard header="AppNav" href="appnav" image="/img/components/light/AppNav.webp" imageDark="/img/components/dark/AppNav.webp">
    <p>Een component met een navigatiemenu voor de app, doorgaans gebruikt om links of navigatie-items weer te geven waarmee je tussen verschillende secties of weergaven kunt schakelen.</p>
  </GalleryCard>

  <GalleryCard header="Icon" href="icon" image="/img/components/light/Icon.webp" imageDark="/img/components/dark/Icon.webp">
    <p>Een component die een grafisch symbool of een afbeelding weergeeft, vaak gebruikt om een actie, status of categorie in de gebruikersinterface aan te duiden.</p>
  </GalleryCard>

  <GalleryCard header="Terminal" href="terminal" image="/img/components/light/Terminal.webp" imageDark="/img/components/dark/Terminal.webp">
    <p>Een component die een opdrachtregelinterface (CLI) in de app nabootst, zodat gebruikers tekstgebaseerde opdrachten kunnen invoeren en uitvoeren.</p>
  </GalleryCard>

  <GalleryCard header="InfiniteScroll" href="infinitescroll" image="/img/components/light/InfiniteScroll.webp" imageDark="/img/components/dark/InfiniteScroll.webp">
    <p>Een component die tijdens het scrollen meer items laadt, een laadindicator toont en bijhoudt wanneer alle inhoud is opgehaald.</p>
  </GalleryCard>

  <GalleryCard header="Refresher" href="refresher" image="/img/components/light/Refresher.webp" imageDark="/img/components/dark/Refresher.webp">
    <p>Een component waarmee je omlaag kunt trekken om te vernieuwen in scrollbare containers. Ideaal voor het dynamisch laden van gegevens.</p>
  </GalleryCard>

  <GalleryCard header="Tree" href="tree" image="/img/components/light/Tree.webp" imageDark="/img/components/dark/Tree.webp">
    <p>Een component voor het weergeven van hiërarchische gegevens, waarmee gebruikers geneste items kunnen uitklappen, inklappen en ermee kunnen werken.</p>
  </GalleryCard>

  <GalleryCard header="Avatar" href="avatar" image="/img/components/light/Avatar.webp" imageDark="/img/components/dark/Avatar.webp">
    <p>Een component voor het weergeven van profielfoto's of initialen van gebruikers, met ondersteuning voor verschillende formaten, vormen en thema's.</p>
  </GalleryCard>

  <GalleryCard header="MarkdownViewer" href="markdownviewer" image="/img/components/light/MarkdownViewer.webp" imageDark="/img/components/dark/MarkdownViewer.webp">
    <p>Een component voor het weergeven van Markdown-inhoud met een geleidelijke teken-voor-tekenweergave. Ideaal voor AI-chatinterfaces en het streamen van tekst.</p>
  </GalleryCard>

</GalleryGrid>
