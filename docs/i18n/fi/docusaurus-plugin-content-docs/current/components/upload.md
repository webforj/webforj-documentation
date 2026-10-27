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

`Upload`-komponentti on inline-tiedostovalitsin, joka antaa käyttäjän valita yhden tai useamman tiedoston paikalliselta koneeltaan ja lähettää ne palvelimelle. Toisin kuin [`FileUploadDialog`](/docs/components/option-dialogs/file-upload), joka esittää valitsimen modaalissa, joka estää sovelluksen käytön, kunnes käyttäjä on valmis, `Upload` renderöidään suoraan sivun asettelussa. Se sopii mihin tahansa tiedostosyötteeseen: profiililomakkeeseen, liitetiedostokenttään kommenttiruudun viereen tai pudotusalueeseen mediasivustolla.

<!-- INTRO_END -->

:::tip Milloin käyttää `Upload`
Käytä `Upload`-komponenttia, kun tiedoston valinta liittyy muihin toimintoihin työprosessissa, kuten profiilin muokkaamiseen tai artikkelin kirjoittamiseen. Käytä sen sijaan [`FileUploadDialog`](/docs/components/option-dialogs/file-upload), kun latausten on oltava modaalisia, esimerkiksi kun tiedosto on pakollinen ennen kuin käyttäjä voi edetä.
:::

## Tiedoston lataaminen {#creating-an-upload}

Oletusarvoisesti `Upload`-komponentti näyttää valintapainikkeen, pudotusalueen, nykyisten tiedostojen luettelon ja latauspainikkeen. Peruutuspainike on oletusarvoisesti piilotettu. `Upload`-komponentin luomisen jälkeen voit lisätä suodattimia, kuten sallitut tiedostotyypit, ja muuttaa, mitkä osat ovat näkyvissä.

```java
Upload upload = new Upload();
upload.addFilter("Kuvat", "*.png;*.jpg");
upload.setVisible(false, Upload.Part.LIST);
layout.add(upload);
```

Seuraavassa esimerkissä ladataan ansioluettelo `Upload`-komponentti rekrytointilomakkeeseen nimen kentän ja lähetyspainikkeen viereen.

<ComponentDemo
path='/webforj/upload'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadView.java',
  'src/main/frontend/css/upload/upload.css'
]}
height='550px'
/>

## Tiedostojen valinta {#picking-files}

Valitsimen käyttäytymistä ohjataan muutamalla itsenäisellä asetuksella: kuinka monta tiedostoa käyttäjä voi valita kerralla, mitä voidaan valita paikalliselta tiedostojärjestelmältä ja mitä tyyppejä on näkyvissä tiedostovalintaikkunassa. Yhdessä ne muokkaavat valintakokemusta kenttään sopivaksi.

Tässä on galleria-lataaja, joka on määritetty sekä kuvat että videot suodattavia, monivalintatoimintoa sekä 20 tiedoston ylärajaa varten:

<ComponentDemo
path='/webforj/uploadpickingfiles'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadPickingFilesView.java',
  'src/main/frontend/css/upload/upload.css'
]}

height='450px'
/>

### Valintatila {#selection-mode}

Valintatila rajoittaa valitsimen yhdelle tai useammalle tiedostolle. `MULTIPLE` on oletusarvo ja sopii erätoiminnoille, kuten valokuvagallerioille tai laskuliitteille. `SINGLE` sopii kentille, joilla on käsitteellisesti vain yksi arvo, kuten profiilivalokuvalle tai allekirjoitetulle sopimukselle.

```java
upload.setSelectionMode(Upload.SelectionMode.SINGLE);
upload.setSelectionMode(Upload.SelectionMode.MULTIPLE);
```

### Valitsimen lähde {#picker-source}

Valitsimen lähde määrittää, mitä käyttäjä voi valita paikalliselta tiedostojärjestelmältä. Oletus, `FILES`, avaa standarditiedostovalintaikkunan. `DIRECTORY` antaa käyttäjän valita kansion ja lataa sen ylimmät tiedostot. `DIRECTORY_RECURSIVE` kulkee koko puun läpi ja lataa jokaisen tiedoston sisällä.

```java
upload.setPicker(Upload.Picker.DIRECTORY_RECURSIVE);
```

Kansioiden lataaminen sopii työkaluille, jotka peilaavat kansiorakenteita, kuten toteutusjärjestelmille, materiaalinhallintaohjelmille tai varmuuskopiointipalveluille. Useimmissa lomakekentissä oletustiedostovalitsin on oikea valinta.

### Suodattimet {#filters}

Suodattimet rajoittavat mitä käyttäjä voi valita paikalliselta tiedostojärjestelmältä. Jokaisella suodattimella on kuvaus ja yksi tai useampi glob-malli erottimena puolipiste. Aktiivinen suodatin näkyy pudotusvalikossa valitsinpainikkeen vieressä, ja käyttäjä voi vaihtaa niiden välillä.

```java
upload.addFilter("Kuvat", "*.png;*.jpg;*.jpeg");
upload.addFilter("Dokumentit", "*.pdf;*.docx");
upload.setActiveFilter("Kuvat");
```

Muutamat näihin liittyvät asetukset muokkaavat, miten suodatinpudotusvalikko käyttäytyy: `setFiltersVisible(false)` piilottaa pudotusvalikon mutta pitää suodattimet aktiivisina, `setMultiFilterSelection(true)` antaa käyttäjän yhdistää suodattimia ja `setAllFilesFilterEnabled(false)` poistaa oletusarvoisen "Kaikki tiedostot" vaihtoehdon.

Muutama näistä asetuksista koskevat vain standardivalitsinta. Kun tiedostojärjestelmän käyttöliittymä on käytössä, käyttöjärjestelmän natiivivalitsin hallitsee suodattimien valintaa itse, joten `setFiltersVisible(false)` ignoroituu eikä `setMultiFilterSelection(true)` vaikuta (natiivi valitsin hyväksyy vain yhden suodattimen kerrallaan). Poista tiedostojärjestelmän käytön käyttöliittymä käytöstä `setFileSystemAccess(false)` tehdaksesi näistä asetuksista luotettavia eri selaimissa.

### Pudotusalue {#drop-zone}

Tiedostoja voidaan vetää työpöydältä ja pudottaa komponenttiin. Pudotustunnus muuttuu, kun tiedosto on sen päällä, mikä osoittaa, että pudotus hyväksytään. Pudotus on oletusarvoisesti käytössä ja voidaan poistaa käytöstä, kun valitsimen tulisi hyväksyä vain tiedostoja tiedostovalintaikkunasta.

```java
upload.setDrop(false);
```

## Vahvistus ja rajoitukset {#validation-and-limits}

`setMaxFileSize` rajoittaa yksittäisen tiedoston tavumäärää ja `setMaxFiles` rajoittaa kokonaismäärää tiedostoja erässä. Molemmat toimivat ennen kuin yhtäkään tavua siirretään, joten liian suuri tiedosto hylätään asiakkaalla ilman kaistanleveyden kuluttamista.

```java
upload.setMaxFileSize(5 * 1024 * 1024); // 5 MB
upload.setMaxFiles(10);
```

Kun valittu tai pudotettu tiedosto ylittää jonkin rajan, `UploadRejectEvent` laukaisee syyn. Palvelinpuolen `webforj.fileUpload.maxSize` -ominaisuus on edelleen voimassa ja toimii kovana kattona riippumatta asiakaspuolen rajasta.

:::warning Palvelinpuolen vahvistus
Suodattimia, enimmäiskokoa ja enimmäistiedostomääriä säädetään käyttöliittymässä käyttäjän ohjaamiseksi, ei suojatakseen palvelinta. Jokainen ladattu tiedosto tulisi tarkistaa uudelleen palvelimella ennen sen tallentamista, ja tilapäistiedostot tulisi siirtää tai poistaa pian latauksen päätyttyä.
:::

## Latauskäyttäytyminen {#upload-behavior}

Kun tiedostot on valittu, kaksi päätöstä jää jäljelle: milloin lataus alkaa, ja mitä tapahtuu olemassa oleville kohteille, kun käyttäjä valitsee uudelleen. Oletusarvoisesti käyttäjä napsauttaa **Lataa** aloittaakseen siirron, ja olemassa olevat kohteet pysyvät luettelossa, kunnes ne poistetaan erikseen.

### Automaattinen lataus {#auto-upload}

Oletustila on `NONE`, jolloin käyttäjä napsauttaa **Lataa** aloittaakseen siirron. `setAutoUpload()` poistaa tuon napsautuksen ja aloittaa siirron heti, kun tiedostot on valittu, pudotettu tai molemmat.

- **`NONE`** jättää lataamisen käyttäjän tehtäväksi, joka napsauttaa **Lataa**.
- **`ON_SELECT`** lataa heti, kun tiedostot valitaan tiedostovalintaikkunassa.
- **`ON_DROP`** lataa heti, kun tiedostot pudotetaan komponenttiin.
- **`ALWAYS`** kattaa molemmat reitit.

:::tip Yhdistäminen esiasetuksiin
Automaattinen lataus toimii hyvin `BUTTON_ONLY` tai `INLINE` -esiasetusten kanssa, jossa käyttäjälle ei ole Lataa-painiketta napsautettavaksi. Työprosesseissa, joissa käyttäjän on tarkistettava valinta ennen lähettämistä, jätä automaattinen lataus pois.
:::

### Automaattinen tyhjennys {#auto-clear}

Kun käyttäjä valitsee uuden erän, automaattinen tyhjennys päättää mitä tehdä luettelon aikaisempien kohteiden kanssa. Tyhjennys tapahtuu seuraavan valinnan yhteydessä, ei latauksen päättyessä, joten valmiit lataukset pysyvät näkyvissä, kunnes käyttäjä tekee uuden valinnan.

- **`COMPLETED`** tyhjentää onnistuneesti ladattuja kohteita.
- **`IN_PROGRESS`** peruuttaa ja tyhjentää vielä siirrettäviä kohteita.
- **`ALL`** tyhjentää kaiken.
Jonoon tarkoitetut kohteet, jotka eivät ole vielä aloittaneet lataamista, säilyvät riippumatta asetuksesta.

```java
upload.setAutoClear(Upload.AutoClear.COMPLETED);
upload.setAutoClear(Upload.AutoClear.IN_PROGRESS);
upload.setAutoClear(Upload.AutoClear.ALL);
```

:::warning Automaattinen tyhjennys on hienovaraisia laukaisimia
Automaattinen tyhjennys tulee voimaan vain, kun aiemmin valittu tiedosto on todella alkanut latautua tai valmis. Ilman latausta valintojen välillä mikään tiedosto ei vastaa suodatinta, ja luettelo kasvaa.
:::

Valitse `COMPLETED` lataajille, jotka ovat näkyvissä useissa toiminnoissa, kuten keskustelukomponentissa, jossa jokaisella viestillä on omat liitteensä, tai kommenttilomakkeessa, jota käytetään jokaiselle vastaukselle. Ilman sitä aiempien onnistumisten luettelo kasautuu, kun käyttäjä työskentelee.

### Ohjelmalliset toimet {#programmatic-actions}

Useimmat lataukset alkavat käyttäjän napsautuksesta, mutta samat toimet ovat käytettävissä palvelinkoodista. Molemmat toimivat tiedostojen kanssa, jotka käyttäjä on jo valinnut; ei ole tapaa valita tiedostoja käyttäjän puolesta palvelimelta.

```java
// Lataa nykyinen valinta ikään kuin käyttäjä napsauttaisi Lataa
upload.upload();

// Peruuta kaikki meneillään olevat siirrot
upload.cancel();
```

Kutsu `upload()` laukaise siirto ohjausobjektilta komponentin ulkopuolelta, kuten yhdeltä lähetyspainikkeelta, jota jaetaan laajemmassa lomakkeessa. Kutsu `cancel()` "lopeta" -painikkeesta komponentin ulkopuolelta tai reittisuojasta, kun käyttäjä siirtyy pois kesken siirron.

## Mobiilikuvaus {#mobile-capture}

Mobiililaitteilla kaappaus avaa kameran tai mikrofonin valitsimen lähteeksi sen sijaan, että avattaisiin tiedostotyyppivalitsin. `USER` kohdistaa etukameran tai mikrofonin, `ENVIRONMENT` takakameran, ja `NONE` (oletus) käyttää standarditiedostovalitsinta.

```java
upload.setCapture(Upload.Capture.ENVIRONMENT);
upload.addFilter("Valokuva", "*.jpg;*.png");
```

:::tip Kaappaus ja suodattimet
Rajoita valinta vain kuva- tai videotiedostotyyppiin, jotta kamera avataan pitäen kuvaustilassa, tai vain videotiedostoihin, jotta se avataan nauhoitustilassa. Ilman vastaavaa suodattinta kaappaustila palaa yleensä standardivalitsimeen useimmilla alustoilla. Työpöytäselaimet ohittavat kaappausasetuksen kokonaan.
:::

Mobiiliensimmäisissä sovelluksissa kaappaus toimii hyvin [asennettavien sovellusten](/docs/configuration/installable-apps) kanssa, joissa kamera ja mikrofoni ovat luonnollinen osa aloitusnäytön kokemusta.

## Natiivin tiedostojärjestelmän käyttöoikeus {#native-file-system-access}

Komponentti käyttää selaimen [File System Access API](https://developer.mozilla.org/en-US/docs/Web/API/File_System_Access_API) -liittymää, kun alusta tukee sitä. Natiivivalitsin voi myöntää sivulle pysyvän oikeuden kansioon, joten käyttäjän ei tarvitse valita uudelleen samaa kansiota, ja seuraavat lataukset ohittavat valintaikkunan. Selaimissa, joissa ei ole tukea, komponentti palautuu automaattisesti standardivalitsimeen.

```java
upload.setFileSystemAccess(false); // pakota standardivalitsin
```

Kytke se pois päältä, kun jokaisen latauksen pitäisi alkaa tuoreesta valintaikkunasta, tai kun johdonmukainen käyttäytyminen eri selaimissa on tärkeämpää kuin pysyvän käyttöoikeuden mukavuus.

## Asettelun mukauttaminen {#customizing-the-layout}

Komponentti koostuu viidestä osasta: valitsinpainikkeesta, pudotustunnuksesta, tiedostoluettelosta, latauspainikkeesta ja peruutuspainikkeesta. Ensimmäiset neljä ovat oletusarvoisesti näkyvissä; peruutuspainike on piilotettu, ja sen voi näyttää komennolla `setVisible(true, Upload.Part.CANCEL_BUTTON)`. Asettelua voidaan muokata esiasetusten avulla yleisiin valitsimen muotoihin tai osakohtaisilla näkyvyysasetuksilla tarkemmiksi säätöiksi.

### Esiasetukset {#presets}

Esiasetukset kokoavat useita osan näkyvyysasetuksia nimettyihin valitsinmuotoihin. Ne ovat nopeampi tapa saavuttaa yleinen asetus kuin kytkeä osia erikseen.

- **`FULL`**: Valitsinpainike, pudotustunnus, tiedostoluettelo ja latauspainike. Oletus.
- **`INLINE`**: Valitsinpainike ja pudotustunnus, nykyinen valinta renderoidaan tekstinä valitsimen viereen. Hyödyllinen tiiviissä lomakekentissä.
- **`BUTTON_ONLY`**: Valitsinpainike yksinään. Hyödyllinen, kun ympäröivä käyttöliittymä näyttää jo valitut tiedostot.
- **`DROPZONE`**: Pudotustunnus ja tiedostoluettelo, ilman valitsinpainiketta. Hyödyllinen, kun vedä ja pudota on ainoa tapa lisätä tiedostoja.
- **`HEADLESS`**: Jokainen osa piilotettu, ulkoinen reuna, säde ja pehmeä muoto puristettu niin, että projektin sisältö istuu ihan komponentin rajoissa.

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

### Osan näkyvyys {#part-visibility}

Kun esiasetus on lähes, mutta ei aivan halutun muotoinen, yksittäisiä osia voidaan näyttää tai piilottaa. Tämä on hyödyllistä pienissä säädöissä, kuten peruutuspainikkeen piilottamisessa yksittäisten tiedostojen lataajalta, joka lataa välittömästi, tai pudotustunnuksen piilottamisessa vain-painoskentältä, joka edelleen sallii pudotukset. Kun käytetään `setPreset()` ja `setVisible()` yhdessä, kutsu `setPreset()` ensin.

```java
upload.setVisible(false, Upload.Part.DROP_LABEL);
upload.setVisible(false, Upload.Part.CANCEL_BUTTON);
```

### Oletusosa {#default-slot}

`Upload` toteuttaa `HasComponents`. Lapsia, jotka on lisätty `add()` -komennolla, renderoidaan pudotusalueelle, perinteisen kromin päälle. Yhdistettynä `HEADLESS`-esiasetukseen, osa antaa sinun ottaa täysin hallinta visuaalisesta pinnasta säilyttäen samalla valitsin-, pudotus- ja latauskäyttäytymisen.

```java
upload.setPreset(Upload.Preset.HEADLESS);
upload.add(new Table<>());
```

Seuraavassa esimerkissä käytetään `HEADLESS`-esiasetusta, joka projisoi `Table`-komponentin Uploadin rajoihin. Pudota CSV-tiedosto, ja sen rivit renderoidaan suoraan komponentin sisään, sarakkeet rakennetaan tiedoston otsikkorivistä.

<ComponentDemo
path='/webforj/uploaddefaultslot'
files={['src/main/java/com/webforj/samples/views/upload/UploadDefaultSlotView.java']}
height='400px'
/>

## Tapahtumat {#events}

`Upload` laukaisee tapahtumia kolmella tasolla: asioista, jotka käyttäjä tekee koko komponentille, yksittäisen tiedoston siirtotilasta ja koko erän elinkaarelta. Useimmat sovellukset rekisteröivät muutaman kuuntelijan näillä tasoilla riippuen siitä, mihin heidän tarvitsee reagoida. Lomake voi tarvita vain `onUpload`, jotta se tietää, milloin tiedostot saavuttavat palvelimen; lataaja, jossa on edistymisliittymä, tarvitsee `onListProgress` ja `onComplete`; pudotusalue, joka joutuu näyttämään hylkäykset tarvitsee `onReject`.

Useimmat tiedostoja kantavat tapahtumat tarjoavat sekä `getFile()` (ensimmäinen tai ainoa tiedosto kuormassa) että `getFiles()` (täydellinen luettelo). Käytä `getFile()` yksittäisten tiedostojen tapahtumissa, kuten `onReject`, ja `getFiles()` silloin, kun odotat erää. `UploadCompleteEvent` on poikkeus; sillä on oma `getUploadedFiles()` ja `getFailedFiles()` pääsyominaisuudet, koska erätila on jaettu onnistumisten ja epäonnistumisten välillä.

### Käyttäjän toimet {#user-actions}

Nämä laukaisevat vastauksena siihen, mitä käyttäjä tekee koko komponentilla. Ne eivät kerro mitään siirron etenemisestä, vain että käyttäjä on tehnyt jotain, mihin sovelluksen voisi olla hyvä reagoida.

| Tapahtuma | Laukaisee |
| --- | --- |
| `UploadChangeEvent` | Kun valittujen tiedostojen luettelo muuttuu |
| `UploadEvent` | Kun käyttäjä napsauttaa **Lataa** ja tiedostot saavuttavat palvelimen |
| `UploadCancelEvent` | Kun käyttäjä napsauttaa **Peruuta** |
| `UploadFilterChangeEvent` | Kun aktiivinen suodatin muuttuu |

```java
upload.onChange(e -> {
    // Laukaisee aina, kun valittujen tiedostojen luettelo muuttuu.
    List<UploadedFile> files = e.getFiles();
});

upload.onUpload(e -> {
    // Laukaisee, kun lataus käynnistyy; tiedostot ovat saavuttaneet palvelimen.
});
```

`UploadEvent` ja `UploadCompleteEvent` näyttävät ensi silmäyksellä samankaltaisilta, mutta ne vastaavat eri kysymyksiin. `UploadEvent` laukaisee, kun käyttäjä aloittaa latauksen nimenomaan (tai `setAutoUpload()` laukaisee sen heidän puolestaan), ja on luonnollinen paikka tallentaa tai siirtää ladatut tiedostot. `UploadCompleteEvent` laukaisee, kun kaikkien jonossa olevien tiedostojen siirto on päättynyt, ja on oikea koukku "erä on valmis" käyttöliittymäpäivityksille.

### Tiedostokohtainen siirto {#per-file-transfer}

Nämä laukaisevat kerran per tiedosto, kun siirto on käynnissä tai juuri sen jälkeen, kun se epäonnistuu. Käytä niitä, kun käyttöliittymän on kuvastettava yksittäisten tiedostojen tilaa pikemminkin kuin erää.

| Tapahtuma | Laukaisee |
| --- | --- |
| `UploadProgressEvent` | Kun yksittäistä tiedostoa siirretään |
| `UploadErrorEvent` | Kun yksittäisen tiedoston siirto epäonnistuu |
| `UploadRejectEvent` | Kun valittu tai pudotettu tiedosto ei täytä asetettuja vaatimuksia |

```java
upload.onProgress(e -> {
    // Laukaisee toistuvasti yksittäisen tiedoston siirron aikana.
    double percent = e.getProgress();
});

upload.onReject(e -> {
    // Laukaisee, kun tiedosto hylätään koon, määrän tai suodattimien vuoksi.
    String reason = e.getMessage();
});
```

Tässä ryhmässä `UploadRejectEvent` on poikkeus. Se laukaisee ennen kuin yhtäkään tavua siirretään, kun tiedosto epäonnistuu asiakaspuolen rajoituksessa, kuten `setMaxFileSize` tai `setMaxFiles`. `UploadErrorEvent`, puolestaan, laukaisee sen jälkeen, kun siirto on alkanut ja jotain meni pieleen matkalla palvelimelle.

### Koko erä {#whole-batch}

Nämä laukaisevat erälle riippumatta siitä, mihin tiedostoon. Käytä niitä aggregaattikäyttöliittymässä kuten yleisessä edistymispalkissa tai "valmis" viestissä, joka tiivistää koko valinnan.

| Tapahtuma | Laukaisee |
| --- | --- |
| `UploadListProgressEvent` | Yhdessä `UploadProgressEvent`-tapahtuman kanssa, koko luettelon tilan kanssa |
| `UploadCompleteEvent` | Kerran erässä, kun jokainen tiedosto on siirretty |

```java
upload.onComplete(e -> {
    // Laukaisee kerran, kun koko erä on valmis.
    List<UploadedFile> succeeded = e.getUploadedFiles();
    List<UploadedFile> failed = e.getFailedFiles();
});
```

`onProgress` ja `onListProgress` kattavat saman siirron kahdelta kulmalta. `onProgress` on tiedostokohtainen, ja on oikea koukku, kun jokaisella tiedostolla on oma edistymisliittymä. `onListProgress` laukaisee sen rinnalla aggregaattilaskureilla (`getListTotal`, `getListRemaining`, `getListProgress`) yksittäiselle erälaajuiselle indikaattorille.

Seuraavassa esimerkissä `onChange`, `onListProgress` ja `onComplete` ohjaavat edistymispalkkia ja tilariviä, jotka päivittyvät, kun tiedostoluettelo muuttuu ja tiedostot siirtyvät.

<ComponentDemo
path='/webforj/uploadevents'
files={[
  'src/main/java/com/webforj/samples/views/upload/UploadEventsView.java',
]}
height='450px'
/>

## Kansainvälisyys (i18n) {#internationalization-i18n}

Komponentin sisällä olevat etiketit ja viestit ovat mukautettavissa `FileUploadI18n`-paketin kautta. Pakettityyppi säilyttää `FileUploadI18n`-nimen, koska se jaetaan modaalisen [`FileUploadDialog`](/docs/components/option-dialogs/file-upload) kanssa.

```java
FileUploadI18n bundle = new FileUploadI18n();
bundle.setUpload("Lähetä");
bundle.setCancel("Hylkää");
bundle.setDropFile("Pudota tiedosto tähän");
upload.setI18n(bundle);
```

## Teemat {#themes}

`UploadTheme` peilaa standardia DWC-teemaväriä ja sisältää ääriviivaversioita kevyemmän visuaalisen painoarvon saavuttamiseksi. Teemat sovelletaan valitsimeen, lataus- ja peruutuspainikkeisiin. Luettelo ja pudotusalue säilyttävät neutraalin tyylin riippumatta teemasta.

```java
upload.setTheme(UploadTheme.PRIMARY);
upload.setTheme(UploadTheme.SUCCESS);
upload.setTheme(UploadTheme.OUTLINED_GRAY);
```

Alla oleva esimerkki näyttää `PRIMARY`-teeman yhdistettynä `INLINE`-esiasetukseen.

<ComponentDemo
path='/webforj/uploadthemes'
files={['src/main/java/com/webforj/samples/views/upload/UploadThemesView.java']}
height='200px'
/>

## Tyylittely {#styling}

<TableBuilder name="Upload" />
