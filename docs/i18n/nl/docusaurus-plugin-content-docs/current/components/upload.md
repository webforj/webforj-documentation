---
title: Upload
sidebar_position: 160
sidebar_class_name: new-content
description: >-
  Select and upload one or more files from the local machine with the Upload
  component using drag-and-drop, filters, and per-file or batch event tracking.
_i18n_hash: 76f8c00c7754fed0a87c27e7963e2877
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-upload" />
<DocChip chip='since' label='26.01' />
<JavadocLink type="foundation" location="com/webforj/component/upload/Upload" top='true'/>

De `Upload` component is een inline-bestandselectie die de gebruiker in staat stelt één of meerdere bestanden van hun lokale machine te selecteren en naar de server te verzenden. In tegenstelling tot [`FileUploadDialog`](/docs/components/option-dialogs/file-upload), dat de selectie in een modaal venster presenteert dat de app blokkeert totdat de gebruiker klaar is, rendert `Upload` direct in de pagina-indeling. Het past overal waar een bestandinvoerveld thuishoort: een profielformulier, een bijlageveld naast een opmerking, of een dropzone op een mediabeheerpagina.

<!-- INTRO_END -->

:::tip Wanneer `Upload` te gebruiken
Gebruik de `Upload` component wanneer bestandsselectie gepaard gaat met andere acties in een workflow, zoals het bewerken van een profiel of het opbouwen van een bericht. Kies liever voor [`FileUploadDialog`](/docs/components/option-dialogs/file-upload) wanneer uploads modaal moeten zijn, bijvoorbeeld wanneer een bestand strikt vereist is voordat de gebruiker kan doorgaan.
:::

## Een upload maken {#creating-an-upload}

Standaard toont een `Upload` component een selectieknoop, een dropgebied, de huidige bestandlijst, en een uploadknop. De annuleringknop is standaard verborgen. Na het creëren van een `Upload` kun je filters toevoegen, zoals toegestane bestandstypen, en welke delen zichtbaar zijn.

```java
Upload upload = new Upload();
upload.addFilter("Afbeeldingen", "*.png;*.jpg");
upload.setVisible(false, Upload.Part.LIST);
layout.add(upload);
```

In het volgende voorbeeld wordt een cv `Upload` in een sollicitatieformulier geplaatst, naast een naamveld en een verzendknop.

<ComponentDemo
path='/webforj/upload'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadView.java',
  'src/main/frontend/css/upload/upload.css'
]}
height='550px'
/>

## Bestanden selecteren {#picking-files}

Hoe de selectie werkt, wordt geregeld door een paar onafhankelijke instellingen: hoeveel bestanden de gebruiker in één keer kan selecteren, wat selecteerbaar is vanuit het lokale bestandssysteem, en welke typen zichtbaar zijn in de bestandsdialoog. Samen vormen ze de selecteerervaring passend bij het veld.

Hier is een galerij-uploader geconfigureerd met zowel beeld- als video-filters, meerdere bestandsselecties, en een limiet van 20 bestanden:

<ComponentDemo
path='/webforj/uploadpickingfiles'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadPickingFilesView.java',
  'src/main/frontend/css/upload/upload.css'
]}
height='450px'
/>

### Selectiemodus {#selection-mode}

De selectiemodus beperkt de selectie tot één bestand of meerdere. `MULTIPLE` is de standaardinstelling en geschikt voor batchbewerkingen zoals fotogalerijen of factuurbijlagen. `SINGLE` past bij velden die conceptueel één waarde bevatten, zoals een profielfoto of een ondertekend contract.

```java
upload.setSelectionMode(Upload.SelectionMode.SINGLE);
upload.setSelectionMode(Upload.SelectionMode.MULTIPLE);
```

### Picker-bron {#picker-source}

De picker-bron bepaalt wat de gebruiker kan selecteren vanuit het lokale bestandssysteem. De standaardinstelling, `FILES`, opent een standaard bestandsdialoog. `DIRECTORY` laat de gebruiker een map selecteren en uploadt de top-level bestanden. `DIRECTORY_RECURSIVE` doorloopt de hele boom en uploadt elk bestand binnenin.

```java
upload.setPicker(Upload.Picker.DIRECTORY_RECURSIVE);
```

Mapuploads zijn geschikt voor tools die mappenstructuren spiegelen, zoals implementatiesystemen, assetbeheer-apps of back-upprogramma's. Voor de meeste formuliervelden is de standaard bestandskiezer de juiste keuze.

### Filters {#filters}

Filters beperken wat de gebruiker kan selecteren vanuit het lokale bestandssysteem. Elke filter heeft een beschrijving en één of meer glob-patronen gescheiden door puntkomma's. De actieve filter verschijnt in een dropdown naast de pickerknop, en de gebruiker kan tussen die filters switchen.

```java
upload.addFilter("Afbeeldingen", "*.png;*.jpg;*.jpeg");
upload.addFilter("Documenten", "*.pdf;*.docx");
upload.setActiveFilter("Afbeeldingen");
```

Een paar verwante instellingen bepalen hoe de filter-dropdown zich gedraagt: `setFiltersVisible(false)` verbergt de dropdown terwijl de filters actief blijven, `setMultiFilterSelection(true)` laat de gebruiker filters combineren, en `setAllFilesFilterEnabled(false)` verwijdert de impliciete "Alle Bestanden" optie.

Een paar van deze instellingen zijn alleen van toepassing op de standaard picker. Wanneer de File System Access API in gebruik is, beheert de native bestandskiezer de filterselectie zelf, zodat `setFiltersVisible(false)` wordt genegeerd en `setMultiFilterSelection(true)` geen effect heeft (de native picker accepteert slechts één filter tegelijk). Schakel de File System Access API uit met `setFileSystemAccess(false)` om die instellingen betrouwbaarder te maken in verschillende browsers.

### Dropzone {#drop-zone}

Bestanden kunnen van het bureaublad worden gesleept en op de component worden neergezet. Het droplabel verandert wanneer een bestand er bovenop zweeft, wat aangeeft dat de drop zal worden geaccepteerd. Drop is standaard ingeschakeld en kan worden uitgeschakeld wanneer de picker alleen bestanden moet accepteren vanuit de bestandsdialoog.

```java
upload.setDrop(false);
```

## Validatie en limieten {#validation-and-limits}

`setMaxFileSize` beperkt de bytegrootte van een enkel bestand, en `setMaxFiles` beperkt het totale aantal bestanden in een batch. Beide worden uitgevoerd voordat er bytes worden overgedragen, zodat een te groot bestand aan de client wordt afgewezen zonder bandbreedte te verbruiken.

```java
upload.setMaxFileSize(5 * 1024 * 1024); // 5 MB
upload.setMaxFiles(10);
```

Wanneer een geselecteerd of gedropt bestand een van beide limieten overschrijdt, wordt `UploadRejectEvent` geactiveerd met de reden. De serverzijde `webforj.fileUpload.maxSize` eigenschap blijft nog steeds van toepassing en fungeert als een harde limiet, ongeacht de clientzijde limiet.

:::warning Server-side validatie
Filters, maximale grootte en maximaal aantal bestanden worden afgedwongen in de gebruikersinterface om de gebruiker te begeleiden, niet om de server te beschermen. Elk geüpload bestand moet op de server opnieuw worden gecontroleerd voordat het wordt opgeslagen, en de tijdelijke bestanden moeten kort na de upload worden verplaatst of verwijderd.
:::

## Uploadgedrag {#upload-behavior}

Zodra bestanden zijn geselecteerd, blijven er twee beslissingen over: wanneer de upload begint, en wat er met bestaande vermeldingen gebeurt wanneer de gebruiker opnieuw selecteert. Standaard klikt de gebruiker op **Upload** om de overdracht te starten, en bestaande vermeldingen blijven in de lijst totdat ze expliciet worden gewist.

### Auto-upload {#auto-upload}

De standaardmodus is `NONE`, waarbij de gebruiker op **Upload** klikt om de overdracht te starten. `setAutoUpload()` verwijdert die klik en start de overdracht zodra bestanden zijn geselecteerd, gedropt, of beide.

- **`NONE`** laat uploaden aan de gebruiker over, die op **Upload** klikt.
- **`ON_SELECT`** uploadt zodra bestanden via de bestandsdialoog zijn geselecteerd.
- **`ON_DROP`** uploadt zodra bestanden op de component zijn gedropt.
- **`ALWAYS`** dekt beide paden.

:::tip Combineren met presets
Auto-upload werkt goed in combinatie met de `BUTTON_ONLY` of `INLINE` presets, waarbij er geen Upload-knop is voor de gebruiker om op te klikken. Voor workflows waarbij de gebruiker de selectie moet bekijken voordat deze wordt verzonden, laat je auto-upload uit.
:::

### Auto wissen {#auto-clear}

Wanneer de gebruiker een nieuwe batch kiest, beslist auto wissen wat er gebeurt met de al in de lijst bestaande vermeldingen. Wissen gebeurt op het moment van de volgende selectie, niet bij de voltooiing van de upload, zodat voltooide uploads zichtbaar blijven totdat de gebruiker opnieuw selecteert.

- **`COMPLETED`** wist met succes geüploade vermeldingen.
- **`IN_PROGRESS`** annuleert en wist vermeldingen die nog worden overgedragen.
- **`ALL`** wist alles.
In wachtrij staande vermeldingen die nog niet zijn begonnen met uploaden blijven behouden ongeacht de instelling.

```java
upload.setAutoClear(Upload.AutoClear.COMPLETED);
upload.setAutoClear(Upload.AutoClear.IN_PROGRESS);
upload.setAutoClear(Upload.AutoClear.ALL);
```

:::warning Auto clear heeft subtiele triggers
Auto clear gaat pas in werking zodra een eerder geselecteerd bestand daadwerkelijk is begonnen met uploaden of is voltooid. Zonder een upload tussen selecties, komt er geen bestand overeen met de filter en blijft de lijst groeien.
:::

Kies voor `COMPLETED` in uploaders die op het scherm blijven tijdens meerdere acties, zoals een chatcomposer waarbij elk bericht zijn eigen bijlagen heeft, of een reactieformulier dat voor elk antwoord opnieuw wordt gebruikt. Zonder het zal de lijst met eerdere successen zich ophopen terwijl de gebruiker werkt.

### Programma-acties {#programmatic-actions}

De meeste uploads beginnen vanuit een gebruikersklik, maar dezelfde acties zijn beschikbaar vanuit servercode. Beide werken met de bestanden die de gebruiker al heeft geselecteerd; er is geen manier om bestanden namens de gebruiker vanaf de server te selecteren.

```java
// Upload de huidige selectie, alsof de gebruiker op Upload heeft geklikt
upload.upload();

// Annuleer alle in-progress overdrachten
upload.cancel();
```

Roep `upload()` aan om de overdracht uit te voeren vanuit een controle buiten de component, zoals een enkele verzendknop die wordt gedeeld door een groter formulier. Roep `cancel()` aan vanuit een "stop"-knop buiten de component, of vanuit een routebewaker wanneer de gebruiker weg navigeert tijdens de transfer.

## Mobiele capture {#mobile-capture}

Op mobiele apparaten opent capture de camera of microfoon als de picker-bron in plaats van de bestandsbrowser. `USER` richt zich op de frontcamera of microfoon, `ENVIRONMENT` richt zich op de achtercamera, en `NONE` (de standaard) gebruikt de standaard bestandskiezer.

```java
upload.setCapture(Upload.Capture.ENVIRONMENT);
upload.addFilter("Foto", "*.jpg;*.png");
```

:::tip Capture en filters
Beperk de selectie tot afbeeldingsextensies zodat de camera in stille modus opent, of tot video-extensies zodat deze in opname-modus opent. Zonder een bijbehorende filter valt een capture-modus terug op de standaardpicker op de meeste platforms. Desktop-browsers negeren de capture-instelling volledig.
:::

Voor mobiele-apps die eerst worden gemaakt, werkt capture goed met [installeerbare apps](/docs/configuration/installable-apps), waar de camera en microfoon een natuurlijk onderdeel van de ervaring op het startscherm worden.

## Toegang tot het native bestandssysteem {#native-file-system-access}

De component gebruikt de [File System Access API](https://developer.mozilla.org/en-US/docs/Web/API/File_System_Access_API) van de browser wanneer het platform dit ondersteunt. De native picker kan de pagina permanente toestemming geven voor een map, zodat de gebruiker één keer selecteert en daaropvolgende uploads vanuit dezelfde map de dialoog overslaan. In browsers zonder ondersteuning valt de component automatisch terug op de standaard picker.

```java
upload.setFileSystemAccess(false); // force de standaard picker
```

Schakel het uit wanneer elke upload moet beginnen vanuit een vers dialoogvenster, of wanneer consistente werking in elke browser belangrijker is dan het gemak van permanente toestemming.

## De lay-out aanpassen {#customizing-the-layout}

De component is opgebouwd uit vijf delen: de picker-knop, het drop-label, de bestandslijst, de uploadknop, en de annuleringknop. De eerste vier zijn standaard zichtbaar; de annuleringknop is verborgen en kan worden weergegeven met `setVisible(true, Upload.Part.CANCEL_BUTTON)`. De lay-out kan worden hervormd met presets voor veelvoorkomende picker-vormen, of met zichtbaarheidselementen per deel voor fijnere aanpassingen.

### Presets {#presets}

Presets bundelen verschillende instellingen voor de zichtbaarheid van onderdelen in benoemde picker-vormen. Ze zijn een snellere manier om bij een veel voorkomende configuratie te komen dan afzonderlijk onderdelen te schakelen.

- **`FULL`**: Picker-knop, drop-label, bestandslijst en uploadknop. De standaard.
- **`INLINE`**: Picker-knop en drop-label, met de huidige selectie weergegeven als tekst naast de picker. Nuttig voor compacte formuliervelden.
- **`BUTTON_ONLY`**: De picker-knop op zichzelf. Nuttig wanneer de omliggende gebruikersinterface al de geselecteerde bestanden toont.
- **`DROPZONE`**: Drop-label en bestandslijst, zonder picker-knop. Nuttig wanneer slepen en neerzetten de enige manier moet zijn om bestanden toe te voegen.
- **`HEADLESS`**: Elk onderdeel verborgen, met de buitenrand, radius, en padding samengedrukt zodat geprojecteerde inhoud vlak binnen de component-grenzen zit.

```java
upload.setPreset(Upload.Preset.INLINE);
```

<ComponentDemo
path='/webforj/uploadpresets'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadPresetsView.java',
  'src/main/frontend/css/upload/uploadPresets.css'
]}
height='650px'
/>

### Zichtbaarheid van onderdelen {#part-visibility}

Wanneer een preset dicht in de buurt komt maar niet precies de gewenste vorm heeft, kunnen individuele onderdelen worden weergegeven of verborgen. Dit is nuttig voor kleine aanpassingen zoals het verbergen van de annuleringknop op een enkele bestandsselectie die onmiddellijk uploadt, of het verbergen van het drop-label op een veld dat alleen knoppen toont maar nog steeds drops toestaat. Wanneer je `setPreset()` en `setVisible()` samen gebruikt, bel je eerst `setPreset()` aan.

```java
upload.setVisible(false, Upload.Part.DROP_LABEL);
upload.setVisible(false, Upload.Part.CANCEL_BUTTON);
```

### Standaard slot {#default-slot}

`Upload` implementeert `HasComponents`. Kinderen die via `add()` worden toegevoegd, worden weergegeven binnen het dropgebied, bovenop de standaard chrome. In combinatie met de `HEADLESS` preset, laat de slot je de visuele oppervlakte volledig overnemen terwijl de picker-, drop-, en uploadgedragingen intact blijven.

```java
upload.setPreset(Upload.Preset.HEADLESS);
upload.add(new Table<>());
```

In het volgende voorbeeld wordt de `HEADLESS` preset gebruikt om een `Table` in de grenzen van Upload te projecteren. Drop een CSV en zijn rijen worden direct in de component weergegeven, met kolommen opgebouwd vanuit de headerrij van het bestand.

<ComponentDemo
path='/webforj/uploaddefaultslot'
files={['src/main/java/com/webforj/samples/views/upload/UploadDefaultSlotView.java']}
height='400px'
/>

## Evenementen {#events}

`Upload` genereert evenementen op drie niveaus: dingen die de gebruiker met de hele component doet, de overdrachtsstatus van een enkel bestand, en de levenscyclus van de batch als geheel. De meeste apps registreren een paar luisteraars over deze niveaus, afhankelijk van wat ze moeten reageren. Een formulier heeft misschien alleen `onUpload` nodig om te weten wanneer bestanden de server bereiken; een uploader met een voortgangsinterface heeft `onListProgress` en `onComplete` nodig; een dropzone die afwijzingen moet weergeven, heeft `onReject` nodig.

De meeste evenementen die bestanden dragen, hebben zowel `getFile()` (het eerste of enige bestand in de payload) als `getFiles()` (de volledige lijst). Gebruik `getFile()` voor enkel-bestandsevenementen zoals `onReject`, en `getFiles()` wanneer je een batch verwacht. `UploadCompleteEvent` is de uitzondering; het heeft zijn eigen `getUploadedFiles()` en `getFailedFiles()` accessors aangezien het batchresultaat gesplitst is tussen successen en mislukkingen.

### Gebruikersacties {#user-actions}

Deze worden geactiveerd als reactie op iets dat de gebruiker met de component als geheel doet. Ze geven niets aan over de voortgang van de overdracht, alleen dat de gebruiker iets heeft gedaan waar de app op kan reageren.

| Evenement | Wordt geactiveerd |
| --- | --- |
| `UploadChangeEvent` | Wanneer de lijst met geselecteerde bestanden verandert |
| `UploadEvent` | Wanneer de gebruiker op **Upload** klikt en de bestanden de server bereiken |
| `UploadCancelEvent` | Wanneer de gebruiker op **Annuleer** klikt |
| `UploadFilterChangeEvent` | Wanneer de actieve filter verandert |

```java
upload.onChange(e -> {
    // Wordt geactiveerd telkens wanneer de geselecteerde bestandlijst verandert.
    List<UploadedFile> files = e.getFiles();
});

upload.onUpload(e -> {
    // Wordt geactiveerd wanneer de upload wordt getriggerd; bestanden zijn de server bereikt.
});
```

`UploadEvent` en `UploadCompleteEvent` lijken op het eerste gezicht vergelijkbaar, maar ze beantwoorden verschillende vragen. `UploadEvent` wordt geactiveerd wanneer de gebruiker expliciet de upload triggert (of `setAutoUpload()` deze namens hen triggert) en is de natuurlijke plek om de geüploade bestanden op te slaan of over te dragen. `UploadCompleteEvent` wordt geactiveerd zodra de overdracht van elk bestand in de wachtrij is voltooid en is de juiste haak voor "de batch is klaar" UI-updates.

### Per-bestandsoverdracht {#per-file-transfer}

Deze worden één keer per bestand geactiveerd, terwijl een overdracht plaatsvindt of direct nadat deze mislukt. Gebruik ze wanneer de gebruikersinterface de status van individuele bestanden moet weerspiegelen in plaats van de batch.

| Evenement | Wordt geactiveerd |
| --- | --- |
| `UploadProgressEvent` | Terwijl een enkel bestand wordt overgedragen |
| `UploadErrorEvent` | Wanneer een enkele bestandoverdracht mislukt |
| `UploadRejectEvent` | Wanneer een gekozen of gedropt bestand niet voldoet aan de ingestelde voorwaarden |

```java
upload.onProgress(e -> {
    // Wordt herhaaldelijk geactiveerd tijdens de overdracht van een enkel bestand.
    double percent = e.getProgress();
});

upload.onReject(e -> {
    // Wordt geactiveerd wanneer een bestand wordt afgewezen om grootte-, aantal-, of filterredenen.
    String reason = e.getMessage();
});
```

Binnen deze groep is `UploadRejectEvent` de vreemde eend in de bijt. Het wordt geactiveerd voordat er ook maar bytes verplaatst worden, wanneer een bestand een client-side beperking zoals `setMaxFileSize` of `setMaxFiles` niet haalt. `UploadErrorEvent`, daarentegen, wordt geactiveerd nadat de overdracht is begonnen en er iets misging onderweg naar de server.

### Hele batch {#whole-batch}

Deze worden geactiveerd op de batch in plaats van op een enkel bestand. Gebruik ze voor aggregaatweergaven, zoals een algemene voortgangsbalk of een "klaar"-bericht dat de hele selectie samenvat.

| Evenement | Wordt geactiveerd |
| --- | --- |
| `UploadListProgressEvent` | Samen met `UploadProgressEvent`, met de staat van de hele lijst |
| `UploadCompleteEvent` | Een keer per batch, wanneer elk bestand is voltooid met overdragen |

```java
upload.onComplete(e -> {
    // Wordt één keer geactiveerd wanneer de hele batch klaar is.
    List<UploadedFile> succeeded = e.getUploadedFiles();
    List<UploadedFile> failed = e.getFailedFiles();
});
```

`onProgress` en `onListProgress` dekken dezelfde overdracht vanuit twee hoeken. `onProgress` is per bestand en is de juiste haak wanneer elk bestand zijn eigen voortgangsinterface heeft. `onListProgress` wordt tegelijk geactiveerd met aggregaatcijfers (`getListTotal`, `getListRemaining`, `getListProgress`) voor een enkele batchbrede indicator.

In het volgende voorbeeld, sturen `onChange`, `onListProgress`, en `onComplete` een voortgangsbalk en statuslijn die wordt bijgewerkt naarmate de bestandslijst verandert en bestanden worden overgedragen.

<ComponentDemo
path='/webforj/uploadevents'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadEventsView.java',
  'src/main/frontend/css/upload/uploadEvents.css'
]}
height='450px'
/>

## Internationalisatie (i18n) {#internationalization-i18n}

De labels en berichten in de component zijn aanpasbaar via de `FileUploadI18n` bundel. Het bundeltype behoudt de naam `FileUploadI18n` omdat het wordt gedeeld met de modale [`FileUploadDialog`](/docs/components/option-dialogs/file-upload).

```java
FileUploadI18n bundle = new FileUploadI18n();
bundle.setUpload("Verzenden");
bundle.setCancel("Annuleren");
bundle.setDropFile("Drop het bestand hier");
upload.setI18n(bundle);
```

## Thema's {#themes}

`UploadTheme` spiegelt het standaard DWC-thema palet en bevat omtrekvarianten voor een lichtere visuele uitstraling. Thema's passen op de picker-, upload-, en annuleringknoppen. De lijst en het dropgebied behouden neutrale styling, ongeacht het thema.

```java
upload.setTheme(UploadTheme.PRIMARY);
upload.setTheme(UploadTheme.SUCCESS);
upload.setTheme(UploadTheme.OUTLINED_GRAY);
```

De demo hieronder toont het `PRIMARY` thema in combinatie met de `INLINE` preset.

<ComponentDemo
path='/webforj/uploadthemes'
files={['src/main/java/com/webforj/samples/views/upload/UploadThemesView.java']}
height='200px'
/>

## Styling {#styling}

<TableBuilder name="Upload" />
