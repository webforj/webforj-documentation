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

`Upload`-komponentti on rivin sisäinen tiedostovalitsin, jonka avulla käyttäjä voi valita yhden tai useamman tiedoston paikalliselta koneeltaan ja lähettää ne palvelimelle. Toisin kuin [`FileUploadDialog`](/docs/components/option-dialogs/file-upload), joka esittää valitsimen modaalissa, joka estää sovellusta toimimasta, ennen kuin käyttäjä on valmis, `Upload` renderoituu suoraan sivun asetteluun. Se sopii mihin tahansa kohtaan, johon tiedostosyöte kuuluu: profiililomake, liitekenttä kommenttikentän vieressä tai pudotusalue mediahallintapageda.

<!-- INTRO_END -->

:::tip Milloin käyttää `Upload`-komponenttia
Käytä `Upload`-komponenttia, kun tiedostovalintaan liittyy muita toimintoja työnkulussa, kuten profiilin muokkaamista tai viestin luomista. Käytä sen sijaan [`FileUploadDialog`](/docs/components/option-dialogs/file-upload), kun tiedostojen lataamisen tulisi olla modaalista, esimerkiksi kun tiedosto on ehdottomasti vaadittu ennen kuin käyttäjä voi jatkaa.
:::

## Latauksen luominen {#creating-an-upload}

Oletusarvoisesti `Upload`-komponentti näyttää valintapainikkeen, pudotusalueen, nykyisten tiedostojen listan ja latauspainikkeen. Peruutuspainike on piilotettu oletusarvoisesti. `Upload`-komponentin luomisen jälkeen voit lisätä suodattimia, kuten sallitut tiedostotyypit, ja muuttaa mitkä osat ovat näkyvissä.

```java
Upload upload = new Upload();
upload.addFilter("Kuvat", "*.png;*.jpg");
upload.setVisible(false, Upload.Part.LIST);
layout.add(upload);
```

Seuraavassa esimerkissä lisätään työhakemuksen `Upload`, nimikentän ja lähetyspainikkeen oheen.

<ComponentDemo
path='/webforj/upload'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadView.java',
  'src/main/frontend/css/upload/upload.css'
]}
height='550px'
/>

## Tiedostojen valinta {#picking-files}

Valitsimen käyttäytymistä hallitaan muutamilla itsenäisillä asetuksilla: kuinka monta tiedostoa käyttäjä voi valita kerralla, mitä paikalliselta tiedostojärjestelmältä on valittavissa, ja mitä tyyppejä näkyy tiedostoselaimessa. Yhdessä ne muokkaavat valintakokemusta sopimaan kenttävaatimuksiin.

Tässä on galleria-lataaja, joka on määritetty sekä kuvien että videoiden suodattimilla, monivalinta-asetuksella ja 20 tiedoston ylärajalla:

<ComponentDemo
path='/webforj/uploadpickingfiles'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadPickingFilesView.java',
  'src/main/frontend/css/upload/upload.css'
]}
height='450px'
/>

### Valintatila {#selection-mode}

Valintatila rajoittaa valitsinta yhteen tiedostoon tai moneen. `MULTIPLE` on oletus ja sopii erätoimintoihin, kuten valokuvagallerioihin tai laskutusliitteisiin. `SINGLE` sopii kentille, jotka käsittelevät käsitteellisesti yhtä arvoa, kuten profiilikuva tai allekirjoitettu sopimus.

```java
upload.setSelectionMode(Upload.SelectionMode.SINGLE);
upload.setSelectionMode(Upload.SelectionMode.MULTIPLE);
```

### Valitsimen lähde {#picker-source}

Valitsimen lähde määrää, mitä käyttäjä voi valita paikalliselta tiedostojärjestelmältä. Oletusarvoisesti `FILES` avaa tavanomaisen tiedostoselaimen. `DIRECTORY` antaa käyttäjän valita kansion ja lataa sen ylimmät tiedostot. `DIRECTORY_RECURSIVE` kulkee koko rakennetta ja lataa kaikki tiedostot sen sisällä.

```java
upload.setPicker(Upload.Picker.DIRECTORY_RECURSIVE);
```

Hakemistolataukset sopivat työkaluille, jotka peilaavat kansiorakenteita, kuten käyttöönottojärjestelmiä, omaisuushallintasovelluksia tai varmuuskopiointityökaluja. Useimmissa lomakekentissä oletustiedostovalitsin on oikea valinta.

### Suodattimet {#filters}

Suodattimet rajoittavat, mitä käyttäjä voi valita paikalliselta tiedostojärjestelmältä. Jokaisella suodattimella on kuvaus ja yksi tai useampi globuskaala, joka on erotettu puolipisteillä. Aktiivinen suodatin näkyy pudotusvalikossa valitsimen painikkeen vieressä, ja käyttäjä voi vaihtaa niiden välillä.

```java
upload.addFilter("Kuvat", "*.png;*.jpg;*.jpeg");
upload.addFilter("Asiakirjat", "*.pdf;*.docx");
upload.setActiveFilter("Kuvat");
```

Muutamat siihen liittyvät asetukset muokkaavat, miten suodatinpudotusvalikko käyttäytyy: `setFiltersVisible(false)` piilottaa pudotusvalikon pitäen suodattimet aktiivisina, `setMultiFilterSelection(true)` antaa käyttäjän yhdistää suodattimia, ja `setAllFilesFilterEnabled(false)` poistaa epäsuoran "Kaikki tiedostot" vaihtoehdon.

Muutamat näistä asetuksista koskevat vain tavanomaista valitsinta. Kun Tiedostojärjestelmän käytön API on käytössä, paikallinen käyttöjärjestelmän valitsija hallitsee suodatinvalinnan itse, joten `setFiltersVisible(false)` ignoroidaan ja `setMultiFilterSelection(true)` ei vaikuta (paikallinen valitsija hyväksyy vain yhden suodattimen kerrallaan). Poista käytöstä Tiedostojärjestelmän käytön API käyttämällä `setFileSystemAccess(false)` pitämään nämä asetukset luotettavina eri selaimissa.

### Pudotusalue {#drop-zone}

Tiedostoja voidaan vetää työpöydältä ja pudottaa komponenttiin. Pudotustunniste muuttuu, kun tiedosto leijuu sen päällä, mikä merkitsee, että pudotus hyväksytään. Pudotus on oletusarvoisesti käytössä ja voidaan poistaa käytöstä, kun valitsimen tulisi hyväksyä vain tiedostoja tiedostoselaimesta.

```java
upload.setDrop(false);
```

## Validointi ja rajoitukset {#validation-and-limits}

`setMaxFileSize` rajoittaa yksittäisen tiedoston tavu määrää, ja `setMaxFiles` rajoittaa erässä olevien tiedostojen kokonaismäärää. Molemmat suoritetaan ennen kuin mitään tavuja siirretään, joten liian suuri tiedosto hylätään asiakaspuolella ilman kaistanleveyden käyttöä.

```java
upload.setMaxFileSize(5 * 1024 * 1024); // 5 MB
upload.setMaxFiles(10);
```

Kun valittu tai pudotettu tiedosto ylittää joko rajan, `UploadRejectEvent` laukeaa syyn kanssa. Palvelinpuolen `webforj.fileUpload.maxSize` -ominaisuus on edelleen voimassa ja toimii tiukkana kattorajana, riippumatta asiakaspuolen rajoituksesta.

:::warning Palvelinpuolen validointi
Suodattimia, maksimikokoja ja maksimitiedostomääriä valvotaan käyttöliittymässä ohjaamaan käyttäjää, ei suojaamaan palvelinta. Jokainen ladattu tiedosto tulisi tarkistaa uudelleen palvelimella ennen tallentamista, ja väliaikaisten tiedostojen tulisi siirtyä tai poistua pian latauksen päätyttyä.
:::

## Latauskäyttäytyminen {#upload-behavior}

Kun tiedostot on valittu, kaksi päätöstä jää jäljelle: milloin lataus alkaa ja mitä tapahtuu olemassa oleville merkinnöille, kun käyttäjä valitsee jälleen. Oletusarvoisesti käyttäjän on napsautettava **Lataa** aloittaakseen siirron, ja olemassa olevat merkinnät pysyvät listalla, kunnes ne poistetaan nimenomaan.

### Automaattinen lataus {#auto-upload}

Oletustila on `NONE`, jolloin käyttäjän on napsautettava **Lataa** aloittaakseen siirron. `setAutoUpload()` poistaa sen napsautuksen ja aloittaa siirron heti, kun tiedostot on valittu, pudotettu tai molemmat.

- **`NONE`** jättää lataamisen käyttäjän tehtäväksi, joka napsauttaa **Lataa**.
- **`ON_SELECT`** lataa heti, kun tiedostot on valittu tiedostoselaimessa.
- **`ON_DROP`** lataa heti, kun tiedostot pudotetaan komponenttiin.
- **`ALWAYS`** kattaa molemmat polut.

:::tip Yhdistäminen esiasetusten kanssa
Automaattinen lataus yhdistyy hyvin `BUTTON_ONLY`- tai `INLINE`-esiasetuksiin, joissa ei ole Lataa-painiketta, jota käyttäjän tarvitsee napsauttaa. Työnkuluissa, joissa käyttäjän on tarkistettava valinta ennen lähettämistä, pidä automaattinen lataus pois päältä.
:::

### Automaattinen tyhjennys {#auto-clear}

Kun käyttäjä valitsee uuden erän, automaattinen tyhjennys päättää, mitä tehdä listalla jo olevien merkinnöiden kanssa. Tyhjennys tapahtuu seuraavan valinnan hetkellä, ei latauksen päättämisen yhteydessä, joten valmiit lataukset pysyvät näkyvissä, kunnes käyttäjä valitsee jälleen.

- **`COMPLETED`** tyhjentää onnistuneesti ladatut merkinnät.
- **`IN_PROGRESS`** peruuttaa ja tyhjentää edelleen siirrettäviä merkintöjä.
- **`ALL`** tyhjentää kaiken.
Jonossa olevat merkinnät, jotka eivät ole vielä aloittaneet lataamista, säilytetään asetuksesta riippumatta.

```java
upload.setAutoClear(Upload.AutoClear.COMPLETED);
upload.setAutoClear(Upload.AutoClear.IN_PROGRESS);
upload.setAutoClear(Upload.AutoClear.ALL);
```

:::warning Automaattisen tyhjennyksen hienovaraiset laukaisijat
Automaattinen tyhjennys tulee voimaan vain silloin, kun aiemmin valittu tiedosto on todella aloittanut lataamisen tai päättynyt. Ilman latausta valintojen välillä ei mikään tiedosto vastaa suodattimia, ja lista jatkaa kasvamistaan.
:::

Käytä `COMPLETED`-asetusta lataajissa, jotka ovat näkyvissä useiden toimintojen aikana, kuten keskustelukentässä, jossa jokaisella viestillä on omat liitteensä, tai kommenttilomakkeessa, joka käytetään jokaiselle vastaukselle. Ilman sitä aiempien onnistumisien lista kasvaa, kun käyttäjä työskentelee.

### Ohjelmalliset toiminnot {#programmatic-actions}

Useimmat lataukset alkavat käyttäjän napsautuksesta, mutta samat toiminnot ovat käytettävissä palvelinkoodista. Molemmat toimivat käyttäjän jo valitsemien tiedostojen kanssa; ei ole mahdollista valita tiedostoja käyttäjän puolesta palvelimelta.

```java
// Lataa nykyinen valinta, ikään kuin käyttäjä napsauttaisi Lataa
upload.upload();

// Peruuta kaikki käynnissä olevat siirrot
upload.cancel();
```

Kutsu `upload()` laukaistaksesi siirron ohjauskomponentista, kuten yhdestä lähetyspainikkeesta, joka jaetaan laajemman lomakkeen kanssa. Kutsu `cancel()` "pysäytä"-painikkeesta, joka on ulkopuolella komponentista, tai reititysvahdista, kun käyttäjä navigoi pois siirron aikana.

## Mobiilikuvaus {#mobile-capture}

Mobiililaitteilla kuvaus avaa kameran tai mikrofonin valitsimen lähteen sijasta tiedostoselainta. `USER` kohdistaa etukameran tai mikrofonin, `ENVIRONMENT` kohdistaa takakameran, ja `NONE` (oletus) käyttää tavanomaista tiedostovalitsijaa.

```java
upload.setCapture(Upload.Capture.ENVIRONMENT);
upload.addFilter("Kuva", "*.jpg;*.png");
```

:::tip Taltiointi ja suodattimet
Rajoita valinta kuvaesitteisiin, jotta kamera avautuu still-tilaan, tai videoesitteisiin, jotta se avautuu tallennustilaan. Ilman vastaavaa suodatinmuotoa, kuvaustila palautuu tavanomaiseen valitsijaan useimmilla alustoilla. Työpöytäselaimet ohittavat taltiointa asetuksen kokonaan.
:::

Mobiililähtöisissä sovelluksissa taltiointi yhdistyy hyvin [asennettaviin sovelluksiin](/docs/configuration/installable-apps), joissa kamera ja mikrofoni tulevat luonnolliseksi osaksi aloitusnäytön kokemusta.

## Paikallinen tiedostojärjestelmän käyttö {#native-file-system-access}

Komponentti käyttää selaimen [Tiedostojärjestelmän käytön API:a](https://developer.mozilla.org/en-US/docs/Web/API/File_System_Access_API), kun alusta tukee sitä. Paikallinen valitsija voi myöntää sivulle pysyvän pääsyn kansioon, jolloin käyttäjä valitsee kerran ja myöhemmät lataukset samasta kansiosta ohittavat dialogin. Selaimissa, joissa ei ole tukea, komponentti siirtyy automaattisesti käyttämään tavanomaista valitsijaa.

```java
upload.setFileSystemAccess(false); // pakottaa käyttämään tavanomaista valitsijaa
```

Kytke se pois, kun jokaisen latauksen pitäisi alkaa tuoreesta dialogista, tai kun johdonmukainen käyttäytyminen kaikissa selaimissa on tärkeämpää kuin pysyvän luvan mukavuus.

## Asettelu muokkaaminen {#customizing-the-layout}

Komponentti koostuu viidestä osasta: valitsinpainike, pudotustunniste, tiedostolista, latauspainike ja peruutuspainike. Ensimmäiset neljä ovat näkyviä oletusarvoisesti; peruutuspainike on piilotettu ja se voidaan näyttää kutsumalla `setVisible(true, Upload.Part.CANCEL_BUTTON)`. Asettelua voidaan muokata esiasetuksilla tai osakohtaisten näkyvyysohjausten avulla hienovaraisemmiksi säätöjä varten.

### Esiasetukset {#presets}

Esiasetukset kokoavat yhteen useita osan näkyvyysasetuksia nimettyihin valitsimen muotoihin. Ne ovat nopeampi tapa saavuttaa yleinen kokoonpano kuin osien yksilöllinen kytkeminen.

- **`FULL`**: Valitsinpainike, pudotustunnus, tiedostolista ja latauspainike. Oletus.
- **`INLINE`**: Valitsinpainike ja pudotustunnus, johon nykyinen valinta renderoidaan tekstinä valitsimen viereen. Kätevä kompaktille lomakekentille.
- **`BUTTON_ONLY`**: Vain valitsinpainike. Hyödyllinen silloin, kun ympäröivä käyttöliittymä jo näyttää valitut tiedostot.
- **`DROPZONE`**: Pudotustunnus ja tiedostolista, ilman valitsinpainiketta. Hyödyllinen kun vetäminen ja pudottaminen on ainoa tapa lisätä tiedostoja.
- **`HEADLESS`**: Jokainen osa piilotettuna, ulkoreuna, säde ja pehmustus romahtavat niin, että projisoitu sisältö istuu tiukasti komponentin rajoissa.

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

### Osan näkyvyys {#part-visibility}

Kun esiasetus on lähellä haluttua muotoa, mutta ei aivan, yksittäisiä osia voidaan näyttää tai piilottaa. Tämä on hyödyllistä hienovaraisissa säätöissä, kuten peruutuspainikkeen piilottamisessa yhden tiedoston valitsimessa, joka lataa heti, tai pudotustunnuksen piilottamisessa painikeseinä, joka silti sallii pudotukset. Kun käytetään `setPreset()` ja `setVisible()` yhdessä, soita ensin `setPreset()`.

```java
upload.setVisible(false, Upload.Part.DROP_LABEL);
upload.setVisible(false, Upload.Part.CANCEL_BUTTON);
```

### Oletusslotti {#default-slot}

`Upload` toteuttaa `HasComponents`. Lapset, jotka on lisätty kautta `add()`, renderoidaan pudotusalueen sisälle, perusvälilehden päällä. Yhdistettynä `HEADLESS`-esiasetukseen, slot antaa sinun ottaa visuaalinen pinta kokonaan haltuun, samalla kun pidät valitsimen, pudotuksen ja latauskäyttäytymisen ehjänä.

```java
upload.setPreset(Upload.Preset.HEADLESS);
upload.add(new Table<>());
```

Seuraavassa esimerkissä käytetään `HEADLESS`-esiasetusta projektin `Table` projisoimiseksi lataajan rajoissa. Pudota CSV ja sen rivit renderoidaan suoraan komponentin sisään, sarakkeet rakennetaan tiedoston otsikkorivistä.

<ComponentDemo
path='/webforj/uploaddefaultslot'
files={['src/main/java/com/webforj/samples/views/upload/UploadDefaultSlotView.java']}
height='400px'
/>

## Tapahtumat {#events}

`Upload` lähettää tapahtumia kolmella tasolla: asioista, joita käyttäjä tekee työkalun kanssa, yksittäisen tiedoston siirtotilasta ja koko erän elinkaaresta. Useimmat sovellukset rekisteröivät muutaman kuuntelijan näissä tasoissa riippuen siitä, mihin heidän tarvitsee reagoida. Lomake saattaa tarvita vain `onUpload`, jotta tietää, milloin tiedostot saavuttavat palvelimen; lataaja, jolla on edistymisen käyttöliittymä, tarvitsee `onListProgress` ja `onComplete`; pudotusalue, joka on merkittävä hylkäyksille, tarvitsee `onReject`.

Useimmat tiedostot sisältävät tapahtumat tarjoavat sekä `getFile()` (ensimmäinen tai ainoa tiedosto lähetetty) että `getFiles()` (kokonaislista). Käytä `getFile()`-tiedostoille, kuten `onReject`, ja `getFiles()` kun odotat erää. `UploadCompleteEvent` on poikkeus; sillä on omat `getUploadedFiles()` ja `getFailedFiles()`-pääsyt, koska erän tulos on jaettu menestyksien ja epäonnistumisten välillä.

### Käyttäjätoiminnot {#user-actions}

Nämä laukeavat vastauksena siihen, mitä käyttäjä tekee koko komponentille. Ne eivät kerro mitään siirron edistymisestä, vain sen, että käyttäjä on tehnyt jotain, johon sovelluksen saattaa olla syytä reagoida.

| Tapahtuma | Laukeaa |
| --- | --- |
| `UploadChangeEvent` | Kun valittujen tiedostojen lista muuttuu |
| `UploadEvent` | Kun käyttäjä napsauttaa **Lataa** ja tiedostot saavuttavat palvelimen |
| `UploadCancelEvent` | Kun käyttäjä napsauttaa **Peruuta** |
| `UploadFilterChangeEvent` | Kun aktiivinen suodatin muuttuu |

```java
upload.onChange(e -> {
    // Laukeaa, kun valittujen tiedostojen lista muuttuu.
    List<UploadedFile> files = e.getFiles();
});

upload.onUpload(e -> {
    // Laukeaa, kun lataus laukaistaan; tiedostot ovat saavuttaneet palvelimen.
});
```

`UploadEvent` ja `UploadCompleteEvent` näyttävät samanlaisilta vilkaisulla, mutta ne vastaavat eri kysymyksiin. `UploadEvent` laukeaa, kun käyttäjä laukaisee latauksen selvästi (tai `setAutoUpload()` laukaisee sen heidän puolestaan), ja on luonnollinen paikka tallentaa tai siirtää ladatut tiedostot. `UploadCompleteEvent` laukeaa, kun jokaisen jonossa olevan tiedoston siirto on päättynyt, ja on oikea koukku "koko erä on valmis" käyttöliittymään.

### Erätiedoston siirto {#per-file-transfer}

Nämä laukeavat kerran per tiedosto, kun siirto on käynnissä tai heti sen epäonnistuttua. Käytä niitä, kun käyttöliittymä tarvitsee heijastaa yksittäisten tiedostojen tilaa eikä erää.

| Tapahtuma | Laukeaa |
| --- | --- |
| `UploadProgressEvent` | Kun yksittäistä tiedostoa siirretään |
| `UploadErrorEvent` | Kun yksittäisen tiedoston siirto epäonnistuu |
| `UploadRejectEvent` | Kun valittu tai pudotettu tiedosto ei täytä määritettyjä rajoituksia |

```java
upload.onProgress(e -> {
    // Laukeaa useita kertoja, kun yksittäistä tiedostoa siirretään.
    double percent = e.getProgress();
});

upload.onReject(e -> {
    // Laukeaa, kun tiedosto hylätään koon, määrän tai suodatin syistä.
    String reason = e.getMessage();
});
```

Tässä ryhmässä `UploadRejectEvent` on outo, koska se laukeaa ennen kuin mitään tavuja siirretään, kun tiedosto hylätään asiakaspuolella olevan rajoituksen, kuten `setMaxFileSize` tai `setMaxFiles`, takia. `UploadErrorEvent` sen sijaan laukeaa sen jälkeen, kun siirto aloitettiin ja jokin meni vikaan matkalla palvelimelle.

### Koko erä {#whole-batch}

Nämä laukeavat erälle sen sijaan, että ne laukeaisivat yhdelle tiedostolle. Käytä niitä aggregaatti-käyttöliittymän, kuten kokonaista edistymispalkkia tai "valmiina"-viestiä, joka tiivistää koko valinnan.

| Tapahtuma | Laukeaa |
| --- | --- |
| `UploadListProgressEvent` | Yhdessä `UploadProgressEvent`-tapahtuman kanssa, koko listan tila |
| `UploadCompleteEvent` | Kerran erässä, kun jokaisen tiedoston siirto on päättynyt |

```java
upload.onComplete(e -> {
    // Laukeaa kerran, kun koko erä on valmis.
    List<UploadedFile> succeeded = e.getUploadedFiles();
    List<UploadedFile> failed = e.getFailedFiles();
});
```

`onProgress` ja `onListProgress` kattaa saman siirron kahdesta näkökulmasta. `onProgress` on per-tiedosto, ja se on oikea koukku, kun jokaisella tiedostolla on oma edistymiskäyttöliittymänsä. `onListProgress` laukeaa sen mukana aggregaattilaskureilla (`getListTotal`, `getListRemaining`, `getListProgress`) yksittäistä eräkokoista indikaattoria varten.

Seuraavassa esimerkissä `onChange`, `onListProgress` ja `onComplete` ohjaavat edistymispalkkia ja tila-ruutua, jotka päivittyvät tiedostolistan muuttuessa ja tiedostojen siirtyessä.

<ComponentDemo
path='/webforj/uploadevents'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadEventsView.java',
  'src/main/frontend/css/upload/uploadEvents.css'
]}
height='450px'
/>

## Kansainvälistäminen (i18n) {#internationalization-i18n}

Komponentin sisäisiä etikettejä ja viestejä voidaan muokata `FileUploadI18n`-paketin kautta. Paketin tyyppi pitää `FileUploadI18n`-nimen, koska se on jaettu modaalin [`FileUploadDialog`](/docs/components/option-dialogs/file-upload) kanssa.

```java
FileUploadI18n bundle = new FileUploadI18n();
bundle.setUpload("Lähetä");
bundle.setCancel("Hylkää");
bundle.setDropFile("Pudota tiedosto tähän");
upload.setI18n(bundle);
```

## Teemat {#themes}

`UploadTheme` peilaa DWC-teeman oletuspalettia ja sisältää ääriviivavariaatioita kevyemmälle visuaaliselle painolle. Teemat koskevat valitsinta, lataus- ja peruutuspainikkeita. Lista ja pudotusalue pitävät neutraalia tyylittelyä teeman riippumatta.

```java
upload.setTheme(UploadTheme.PRIMARY);
upload.setTheme(UploadTheme.SUCCESS);
upload.setTheme(UploadTheme.OUTLINED_GRAY);
```

Alla oleva demo näyttää `PRIMARY`-teeman yhdistettynä `INLINE`-esiasetukseen.

<ComponentDemo
path='/webforj/uploadthemes'
files={['src/main/java/com/webforj/samples/views/upload/UploadThemesView.java']}
height='200px'
/>

## Tyylittely {#styling}

<TableBuilder name="Upload" />
