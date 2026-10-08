---
title: Upload
sidebar_position: 160
description: >-
  Select and upload one or more files from the local machine with the Upload
  component using drag-and-drop, filters, and per-file or batch event tracking.
_i18n_hash: df26b1e4b51f3ba6ece7602ca1a1f33f
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-upload" />
<DocChip chip='since' label='26.01' />
<JavadocLink type="foundation" location="com/webforj/component/upload/Upload" top='true'/>

De `Upload` component is een inline bestandkiezer die de gebruiker in staat stelt om een of meer bestanden van hun lokale machine te selecteren en deze naar de server te verzenden. In tegenstelling tot [`FileUploadDialog`](/docs/components/option-dialogs/file-upload), die de kiezer presenteert in een modaal venster dat de app blokkeert totdat de gebruiker klaar is, renderen `Upload` direct in de paginalayout. Het past overal waar een bestandinvoer toebehoort: een profielformulier, een bijlageveld naast een opmerkingenvak, of een dropzone op een media-beheerpagina.

<!-- INTRO_END -->

:::tip Wanneer een `Upload` te gebruiken
Gebruik de `Upload` component wanneer bestandsselectie gepaard gaat met andere acties in een workflow, zoals het bewerken van een profiel of het opstellen van een bericht. Voor uploads die modaal moeten zijn, zoals wanneer een bestand strikt vereist is voordat de gebruiker kan doorgaan, gebruik je in plaats daarvan [`FileUploadDialog`](/docs/components/option-dialogs/file-upload).
:::

## Een upload maken {#creating-an-upload}

Standaard toont een `Upload` component een selecteer knop, een dropgebied, de huidige bestandslijst en een uploadknop. De annuleren knop is standaard verborgen. Na het maken van een `Upload`, kun je filters toevoegen, zoals toegestane bestandstypen, en veranderen welke delen zichtbaar zijn.

```java
Upload upload = new Upload();
upload.addFilter("Afbeeldingen", "*.png;*.jpg");
upload.setVisible(false, Upload.Part.LIST);
layout.add(upload);
```

Het volgende voorbeeld voegt een cv `Upload` toe aan een aanmeldingsformulier, naast een naamveld en een verzendknop.

<ComponentDemo
path='/webforj/upload'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadView.java',
  'src/main/frontend/css/upload/upload.css'
]}
height='550px'
/>

## Bestanden selecteren {#picking-files}

Hoe de kiezer zich gedraagt, wordt beheerst door een paar onafhankelijke instellingen: hoeveel bestanden de gebruiker in één keer kan kiezen, wat selecteerbaar is van het lokale bestandssysteem en welke types zichtbaar zijn in de bestandsdialoog. Samen vormen ze de selecteerervaring om bij het veld te passen.

Hier is een galerij uploader geconfigureerd met zowel afbeeldings- als video-filters, multi-bestandsselectie en een limiet van 20 bestanden:

<ComponentDemo
path='/webforj/uploadpickingfiles'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadPickingFilesView.java',
  'src/main/frontend/css/upload/upload.css'
]}
height='450px'
/>

### Selectiemodus {#selection-mode}

De selectiemodus beperkt de kiezer tot één bestand of meerdere. `MULTIPLE` is de standaard en past bij batchbewerkingen zoals fotogalerijen of factuurbijlagen. `SINGLE` past bij velden die conceptueel één waarde bevatten, zoals een profielfoto of een ondertekend contract.

```java
upload.setSelectionMode(Upload.SelectionMode.SINGLE);
upload.setSelectionMode(Upload.SelectionMode.MULTIPLE);
```

### Bron van de kiezer {#picker-source}

De bron van de kiezer bepaalt wat de gebruiker kan selecteren van het lokale bestandssysteem. De standaardwaarde, `FILES`, opent een standaard bestandsdialoog. `DIRECTORY` laat de gebruiker een map kiezen en uploadt de bovenliggende bestanden. `DIRECTORY_RECURSIVE` doorloopt de hele boom en uploadt elk bestand binnenin.

```java
upload.setPicker(Upload.Picker.DIRECTORY_RECURSIVE);
```

Mapuploads zijn geschikt voor tools die mapstructuren spiegelen, zoals implementatiesystemen, assetmanagement-apps of back-uptools. Voor de meeste formulier velden is de standaard bestandkiezer de juiste keuze.

### Filters {#filters}

Filters beperken wat de gebruiker kan kiezen van het lokale bestandssysteem. Elke filter heeft een beschrijving en een of meer glob-patronen gescheiden door puntkomma's. De actieve filter verschijnt in een dropdown naast de picker-knop, en de gebruiker kan tussen hen wisselen.

```java
upload.addFilter("Afbeeldingen", "*.png;*.jpg;*.jpeg");
upload.addFilter("Documenten", "*.pdf;*.docx");
upload.setActiveFilter("Afbeeldingen");
```

Een paar gerelateerde instellingen bepalen hoe het filter dropdown zich gedraagt: `setFiltersVisible(false)` verbergt de dropdown terwijl de filters actief blijven, `setMultiFilterSelection(true)` laat de gebruiker filters combineren, en `setAllFilesFilterEnabled(false)` verwijdert de impliciete "Alle bestanden" optie.

Een paar van deze instellingen zijn alleen van toepassing op de standaardkiezer. Wanneer de File System Access API in gebruik is, beheert de native OS kiezer zelf de filterselectie, zodat `setFiltersVisible(false)` genegeerd wordt en `setMultiFilterSelection(true)` geen effect heeft (de native kiezer accepteert slechts één filter tegelijk). Schakel de File System Access API uit met `setFileSystemAccess(false)` om deze instellingen betrouwbaar te maken over verschillende browsers.

### Dropzone {#drop-zone}

Bestanden kunnen van het bureaublad worden gesleept en op de component worden neergezet. Het droplabel verandert wanneer een bestand eroverheen zweeft, wat aangeeft dat de drop zal worden geaccepteerd. Drop is standaard aan, en kan worden uitgeschakeld wanneer de kiezer alleen bestanden uit de bestandsdialoog zou moeten accepteren.

```java
upload.setDrop(false);
```

## Validatie en limieten {#validation-and-limits}

`setMaxFileSize` beperkt de bytegrootte van een enkel bestand, en `setMaxFiles` beperkt het totale aantal bestanden in een batch. Beide werken voordat er bytes worden overgedragen, zodat een te groot bestand op de client wordt afgewezen zonder bandbreedte te verbruiken.

```java
upload.setMaxFileSize(5 * 1024 * 1024); // 5 MB
upload.setMaxFiles(10);
```

Wanneer een geselecteerd of neergezet bestand de limieten overschrijdt, wordt `UploadRejectEvent` geactiveerd met de reden. De serverzijde `webforj.fileUpload.maxSize` eigenschap blijft van toepassing en fungeert als een harde limiet, ongeacht de limiet aan de clientzijde.

:::warning Serverzijde validatie
Filters, maximale grootte en maximaal aantal bestanden worden in de gebruikersinterface enforced om de gebruiker te begeleiden, niet om de server te beschermen. Elk geüpload bestand moet op de server opnieuw worden gecontroleerd voordat het opgeslagen wordt, en de tijdelijke bestanden moeten kort na de upload worden verplaatst of verwijderd.
:::

## Uploadgedrag {#upload-behavior}

Zodra bestanden zijn gekozen, blijven er twee beslissingen over: wanneer de upload begint, en wat er met bestaande invoer gebeurt wanneer de gebruiker opnieuw kiest. Standaard klikt de gebruiker op **Uploaden** om de overdracht te starten, en bestaande invoeren blijven in de lijst totdat ze expliciet worden gewist.

### Automatische upload {#auto-upload}

De standaardmodus is `NONE`, waarbij de gebruiker op **Uploaden** klikt om de overdracht te starten. `setAutoUpload()` verwijdert die klik en start de overdracht Zodra bestanden worden gekozen, neergezet, of beide.

- **`NONE`** laat het uploaden aan de gebruiker over, die op **Uploaden** klikt.
- **`ON_SELECT`** uploadt zodra bestanden worden gekozen via de bestandsdialoog.
- **`ON_DROP`** uploadt zodra bestanden op de component worden neergezet.
- **`ALWAYS`** dekt beide paden.

:::tip Combineren met presets
Automatische upload past goed bij de `BUTTON_ONLY` of `INLINE` presets, waar er eigenlijk geen Upload-knop is voor de gebruiker om op te klikken. Voor workflows waarin de gebruiker de selectie moet herzien voordat deze wordt verzonden, laat je de automatische upload uit.
:::

### Automatisch wissen {#auto-clear}

Wanneer de gebruiker een nieuwe batch kiest, besluit automatisch wissen wat er met de invoeren die al in de lijst staan gebeurt. Wissen gebeurt op het moment van de volgende keuze, niet bij de voltooiing van de upload, zodat voltooide uploads zichtbaar blijven totdat de gebruiker opnieuw kiest.

- **`COMPLETED`** wist succesvol geüploade invoeren.
- **`IN_PROGRESS`** annuleert en wist invoeren die nog worden overgedragen.
- **`ALL`** wist alles.
In de wachtstaande invoeren die nog niet zijn begonnen met uploaden worden ongeacht de instelling behouden.

```java
upload.setAutoClear(Upload.AutoClear.COMPLETED);
upload.setAutoClear(Upload.AutoClear.IN_PROGRESS);
upload.setAutoClear(Upload.AutoClear.ALL);
```

:::warning Automatisch wissen heeft subtiele triggers
Automatisch wissen treedt alleen in werking zodra een eerder gekozen bestand daadwerkelijk is begonnen met uploaden of is voltooid. Zonder een upload tussen de keuzes, komt er geen bestand overeen met het filter en blijft de lijst groeien.
:::

Kies `COMPLETED` in uploaders die op het scherm leven over meerdere acties, zoals een chatcomposer waar elk bericht zijn eigen bijlagen heeft, of een opmerkingenformulier dat opnieuw wordt gebruikt voor elke reactie. Zonder het, accumulateert de lijst van eerdere successen terwijl de gebruiker werkt.

### Programma-actie {#programmatic-actions}

De meeste uploads starten vanuit een gebruikersklik, maar dezelfde acties zijn beschikbaar vanuit servercode. Beide opereren op de bestanden die de gebruiker al heeft gekozen; er is geen manier om bestanden namens de gebruiker van de server te selecteren.

```java
// Upload de huidige selectie, alsof de gebruiker op Upload heeft geklikt
upload.upload();

// Annuleer alle lopende overdrachten
upload.cancel();
```

Roep `upload()` aan om de overdracht te activeren vanuit een controle buiten de component, zoals een enkele verzendknop die door een groter formulier wordt gedeeld. Roep `cancel()` aan vanuit een "stop" knop buiten de component, of vanuit een routebewaker wanneer de gebruiker tussentijds weg navigeert.

## Mobiel vastleggen {#mobile-capture}

Op mobiele apparaten opent vastleggen de camera of microfoon als de bron van de kiezer in plaats van de bestandsbrowser. `USER` richt zich op de voorkant camera of microfoon, `ENVIRONMENT` richt zich op de achtercamera, en `NONE` (de standaard) gebruikt de standaard bestandkiezer.

```java
upload.setCapture(Upload.Capture.ENVIRONMENT);
upload.addFilter("Foto", "*.jpg;*.png");
```

:::tip Vastleggen en filters
Beperk de selectie tot afbeeldingsextensies zodat de camera in stillmodus opent, of tot video-extensies zodat deze in opname-modus opent. Zonder een bijbehorende filter, valt een vastlegmodus terug naar de standaardkiezer op de meeste platforms. Desktopbrowsers negeren de vastleginstelling volledig.
:::

Voor mobiele apps is vastleggen goed te combineren met [installeerbare apps](/docs/configuration/installable-apps), waarbij de camera en microfoon een natuurlijk onderdeel van de ervaring op het startscherm worden.

## Toegang tot het native bestandssysteem {#native-file-system-access}

De component gebruikt de [File System Access API](https://developer.mozilla.org/en-US/docs/Web/API/File_System_Access_API) van de browser wanneer het platform dit ondersteunt. De native kiezer kan de pagina permanente toestemming geven voor een map, zodat de gebruiker één keer kiest en daaropvolgende uploads vanuit dezelfde map de dialoog overslaan. In browsers zonder ondersteuning valt de component automatisch terug op de standaardkiezer.

```java
upload.setFileSystemAccess(false); // forceer de standaardkiezer
```

Schakel het uit wanneer elke upload vanaf een verse dialoog moet beginnen, of wanneer consistente gedrag over elke browser belangrijker is dan het gemak van permanente toestemming.

## De lay-out aanpassen {#customizing-the-layout}

De component is opgebouwd uit vijf delen: de picker-knop, het droplabel, de bestandslijst, de uploadknop en de annuleren knop. De eerste vier zijn standaard zichtbaar; de annuleren knop is verborgen en kan worden getoond met `setVisible(true, Upload.Part.CANCEL_BUTTON)`. De lay-out kan worden hervormd met presets voor veelvoorkomende picker vormen, of met zichtbaarheidstools per onderdeel voor fijnere aanpassingen.

### Presets {#presets}

Presets bundelen verschillende zichtbaarheid-instellingen van onderdelen in genummerde picker vormen. Ze zijn een snellere manier om een veelvoorkomende configuratie te bereiken dan individuele onderdelen in te schakelen.

- **`FULL`**: Picker-knop, drop label, bestandslijst en uploadknop. De standaard.
- **`INLINE`**: Picker-knop en drop label, met de huidige selectie weergegeven als tekst naast de picker. Handig voor compacte formulier velden.
- **`BUTTON_ONLY`**: De picker-knop alleen. Handig wanneer de omliggende UI al de geselecteerde bestanden toont.
- **`DROPZONE`**: Drop label en bestandslijst, zonder picker-knop. Handig wanneer drag-and-drop de enige manier is om bestanden toe te voegen.
- **`HEADLESS`**: Elk onderdeel verborgen, met de buitenste rand, straal en padding samengedrukt zodat geprojecteerde inhoud direct binnen de grenzen van de component zit.

```java
upload.setPreset(Upload.Preset.INLINE);
```

<ComponentDemo
path='/webforj/uploadpresets'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadPresetsView.java',
]}
height='650px'
/>

### Zichtbaarheid van onderdelen {#part-visibility}

Wanneer een preset dichtbij komt maar niet helemaal de gewenste vorm heeft, kunnen individuele onderdelen zichtbaar of verborgen worden gemaakt. Dit is nuttig voor kleine aanpassingen, zoals het verbergen van de annuleren knop op een enkel-bestand kiezer die direct uploadt, of het verbergen van het drop label op een knop-alleen veld dat nog steeds drops toestaat. Bij gebruik van `setPreset()` en `setVisible()` samen, roep je eerst `setPreset()` aan.

```java
upload.setVisible(false, Upload.Part.DROP_LABEL);
upload.setVisible(false, Upload.Part.CANCEL_BUTTON);
```

### Standaard slot {#default-slot}

`Upload` implementeert `HasComponents`. Kinderen toegevoegd via `add()` renderen binnen het dropgebied, bovenop de standaard chroom. Samen met het `HEADLESS` preset, stelt de slot je in staat om het visuele oppervlak volledig over te nemen terwijl je het picker-, drop- en uploadgedrag intact houdt.

```java
upload.setPreset(Upload.Preset.HEADLESS);
upload.add(new Table<>());
```

In het volgende voorbeeld wordt het `HEADLESS` preset gebruikt om een `Table` in de Upload-grenzen te projecteren. Sleep een CSV en zijn rijen renderen direct binnen de component, waarbij de kolommen zijn opgebouwd uit de header rij van het bestand.

<ComponentDemo
path='/webforj/uploaddefaultslot'
files={['src/main/java/com/webforj/samples/views/upload/UploadDefaultSlotView.java']}
height='400px'
/>

## Gebeurtenissen {#events}

`Upload` zendt gebeurtenissen uit op drie niveaus: dingen die de gebruiker doet met de gehele component, de overdrachtsstatus van een enkel bestand, en de levenscyclus van de batch als geheel. De meeste apps registreren een paar luisteraars over deze niveaus afhankelijk van waar ze op willen reageren. Een formulier heeft misschien alleen `onUpload` nodig om te weten wanneer bestanden de server bereiken; een uploader met een voortgang UI heeft `onListProgress` en `onComplete` nodig; een dropzone die afwijzingen moet zichtbaar maken heeft `onReject` nodig.

De meeste evenementen die bestanden bevatten geven zowel `getFile()` (het eerste of enige bestand in de payload) als `getFiles()` (de volledige lijst) bloot. Gebruik `getFile()` voor enkel-bestand gebeurtenissen zoals `onReject`, en `getFiles()` wanneer je een batch verwacht. `UploadCompleteEvent` is de uitzondering; het heeft zijn eigen `getUploadedFiles()` en `getFailedFiles()` accessors aangezien het batchresultaat is opgesplitst tussen successen en fouten.

### Acties van de gebruiker {#user-actions}

Deze worden geactiveerd als reactie op iets dat de gebruiker doet met de gehele component. Ze geven niets aan over de voortgang van de overdracht, alleen dat de gebruiker iets heeft gedaan waar de app op wil reageren.

| Evenement | Vindt plaats |
| --- | --- |
| `UploadChangeEvent` | Wanneer de lijst met geselecteerde bestanden verandert |
| `UploadEvent` | Wanneer de gebruiker op **Uploaden** klikt en de bestanden de server bereiken |
| `UploadCancelEvent` | Wanneer de gebruiker op **Annuleren** klikt |
| `UploadFilterChangeEvent` | Wanneer het actieve filter verandert |

```java
upload.onChange(e -> {
    // Wordt geactiveerd wanneer de geselecteerde bestands lijst verandert.
    List<UploadedFile> files = e.getFiles();
});

upload.onUpload(e -> {
    // Wordt geactiveerd wanneer de upload wordt geactiveerd; bestanden zijn op de server aangekomen.
});
```

`UploadEvent` en `UploadCompleteEvent` lijken op het eerste gezicht vergelijkbaar, maar ze beantwoorden verschillende vragen. `UploadEvent` wordt geactiveerd wanneer de gebruiker expliciet de upload activeert (of `setAutoUpload()` het voor hen activeert), en is de natuurlijke plaats om de geüploade bestanden op te slaan of door te geven. `UploadCompleteEvent` wordt geactiveerd zodra de overdracht van elk bestand in de wachtlijst is voltooid, en is de juiste haak voor "de batch is gedaan" UI-updates.

### Per-bestand overdracht {#per-file-transfer}

Deze worden eenmaal per bestand geactiveerd, terwijl een overdracht plaatsvindt of direct nadat deze mislukt. Gebruik ze wanneer de UI de status van individuele bestanden moet weerspiegelen in plaats van die van de batch.

| Evenement | Vindt plaats |
| --- | --- |
| `UploadProgressEvent` | Terwijl een enkel bestand wordt overgedragen |
| `UploadErrorEvent` | Wanneer een enkele bestandsoverdracht mislukt |
| `UploadRejectEvent` | Wanneer een geselecteerd of neergezet bestand niet voldoet aan de geconfigureerde beperkingen |

```java
upload.onProgress(e -> {
    // Wordt herhaaldelijk geactiveerd tijdens de overdracht van een enkel bestand.
    double percent = e.getProgress();
});

upload.onReject(e -> {
    // Wordt geactiveerd wanneer een bestand wordt afgewezen om redenen van grootte, aantal of filter.
    String reason = e.getMessage();
});
```

Binnen deze groep is `UploadRejectEvent` de vreemde eend in de bijt. Het wordt geactiveerd voordat er ook maar één byte verplaatst, wanneer een bestand niet voldoet aan een beperking aan de klantzijde zoals `setMaxFileSize` of `setMaxFiles`. `UploadErrorEvent`, daarentegen, wordt geactiveerd nadat de overdracht is gestart en er iets mis is gegaan onderweg naar de server.

### Volledige batch {#whole-batch}

Deze worden geactiveerd op de batch in plaats van op een enkel bestand. Gebruik ze voor een aggregaat UI zoals een voortgangsbalk of een "klaar" bericht dat de hele selectie samenvat.

| Evenement | Vindt plaats |
| --- | --- |
| `UploadListProgressEvent` | Samen met `UploadProgressEvent`, met de hele lijststatus |
| `UploadCompleteEvent` | Een keer per batch, wanneer elk bestand is overgedragen |

```java
upload.onComplete(e -> {
    // Wordt eenmaal geactiveerd wanneer de hele batch is voltooid.
    List<UploadedFile> succeeded = e.getUploadedFiles();
    List<UploadedFile> failed = e.getFailedFiles();
});
```

`onProgress` en `onListProgress` dekken dezelfde overdracht vanuit twee invalshoeken. `onProgress` is per-bestand en is de juiste haak wanneer elk bestand zijn eigen voortgang UI heeft. `onListProgress` wordt tegelijkertijd geactiveerd met globale tellers (`getListTotal`, `getListRemaining`, `getListProgress`) voor een enkele batch-brede indicator.

In het volgende voorbeeld, drijven `onChange`, `onListProgress`, en `onComplete` een voortgangsbalk en statuslijn die bijwerken terwijl de bestandslijst verandert en terwijl bestanden worden overgedragen.

<ComponentDemo
path='/webforj/uploadevents'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadEventsView.java',
]}
height='450px'
/>

## Internationalisatie (i18n) {#internationalization-i18n}

De labels en berichten binnen de component zijn aanpasbaar via de `FileUploadI18n` bundel. Het bundeltype behoudt de naam `FileUploadI18n` omdat het wordt gedeeld met de modale [`FileUploadDialog`](/docs/components/option-dialogs/file-upload).

```java
FileUploadI18n bundle = new FileUploadI18n();
bundle.setUpload("Verzenden");
bundle.setCancel("Afval");
bundle.setDropFile("Laat het bestand hier vallen");
upload.setI18n(bundle);
```

## Thema's {#themes}

`UploadTheme` weerspiegelt het standaard DWC-thema palet en bevat omrande varianten voor een lichtere visuele belasting. Thema's worden toegepast op de picker, upload- en annuleerknoppen. De lijst en dropgebied behouden neutrale styling, ongeacht het thema.

```java
upload.setTheme(UploadTheme.PRIMARY);
upload.setTheme(UploadTheme.SUCCESS);
upload.setTheme(UploadTheme.OUTLINED_GRAY);
```

De demo hieronder toont het `PRIMARY` thema gecombineerd met het `INLINE` preset.

<ComponentDemo
path='/webforj/uploadthemes'
files={['src/main/java/com/webforj/samples/views/upload/UploadThemesView.java']}
height='200px'
/>

## Styling {#styling}

<TableBuilder name="Upload" />
