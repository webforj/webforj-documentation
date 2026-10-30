---
title: File Upload
sidebar_position: 20
description: >-
  Capture client uploads with the FileUploadDialog, returning an UploadedFile
  that you can filter, move, and process server-side.
_i18n_hash: 411ef7de8459154002fa2a620f073b0d
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileUploadDialog" top='true'/>

`FileUploadDialog` on modaalinen valintaikkuna, joka on suunniteltu mahdollistamaan käyttäjän tiedostojen lataamisen paikalliselta tiedostojärjestelmältä. Ikkuna estää sovelluksen suorituksen, kunnes käyttäjä valitsee ladattavat tiedostot tai sulkee ikkunan.

<!-- INTRO_END -->

:::tip Inline-komponentti
Jos haluat tiedostovalitsimen, joka renderöidään suoraan sivun asettelussa ikkunan sijaan, harkitse [`Upload`](/docs/components/upload) komponentin käyttämistä.
:::

## Käytännöt {#usages}

`FileUploadDialog` tarjoaa tavan valita ja ladata tiedostoja, jolloin käyttäjät voivat toimittaa asiakirjoja, kuvia tai muita sovelluksen tarvitsemia tiedostotyyppejä. Käytä `showFileUploadDialog()` -metodia näyttääksesi ikkunan ja tallentaaksesi ladatun tiedoston.

```java
UploadedFile result = OptionDialog.showFileUploadDialog("Lataa tiedosto");
```

## Tulos {#result}

`FileUploadDialog` palauttaa `UploadedFile` -objektin, joka sisältää tietoja ladatusta tiedostosta, kuten sen nimi, koko ja sisältö. Jos käyttäjä sulkee dialogin ilman tiedoston valitsemista, tulos on `null`.

:::important
Palautettu `UploadedFile` -objekti saadaan `show()` -metodista tai vastaavasta `OptionDialog`-metodista kuten alla näkyy.
:::

<ComponentDemo
path='/webforj/fileuploaddialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/fileupload/FileUploadDialogBasicView.java']}
height='400px'
/>

### Ladattujen tiedostojen siirtäminen {#moving-uploaded-files}

Oletusarvoisesti webforJ tallentaa ladatut tiedostot väliaikaiseen kansioon, jota siivotaan säännöllisesti. Jos et siirrä tiedostoa muualle, se poistetaan. Tiedoston siirtämiseksi käytä `move`-metodia ja määritä sijaintipolku.

```java showLineNumbers
UploadedFile uploadedFile = OptionDialog.showFileUploadDialog("Valitse ladattava tiedosto");
try {
  File file = uploadedFile.move("my/full/path/" + uploadedFile.getSanitizedClientName());
  // ... tee jotain tiedoston kanssa
} catch (IOException e) {
  // käsittele poikkeus
}
```
:::tip Puhdistettu asiakasnimi
Käytä `getSanitizedClientName`-metodia saadaksesi puhdistetun version ladatun tiedoston nimestä. Tämä metodi auttaa estämään turvallisuusriskejä, kuten hakemistojen läpi kulkemista tai virheellisiä merkkejä tiedostonimissä, varmistaen tiedostojärjestelmäsi eheyden ja turvallisuuden.
:::

## Suodattimet {#filters}

`FileUploadDialog` mahdollistaa suodattimien asettamisen rajataksesi ladattavien tiedostojen tyyppejä. Voit konfiguroida suodattimet käyttämällä `setFilters(List<FileChooserFilter> filters)`-metodia.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog(
  "Lataa tiedosto",
  Arrays.asList(new FileChooserFilter("Tekstitiedostot", "*.txt")));
UploadedFile result = dialog.show();
```

:::warning Suodattimien vahvistaminen
Palvelin ei vahvista ladattua tiedostoa suodattimien mukaan. Suodattimia sovelletaan vain käyttöliittymässä käyttämisen ohjaamiseksi. Sinun on toteutettava palvelinpuoleinen vahvistus varmistaaksesi, että ladatut tiedostot täyttävät sovelluksesi vaatimukset.
:::

## Suurin koko {#max-size}

Käytä `setMaxFileSize(Number maxSize)`-metodia asettaaksesi suurimman latauskoon tavuina.

```java
dialog.setMaxFileSize(2 * 1024 * 1024); // Aseta suurin koko 2 MB:ksi
```

## Kansainvälistäminen (i18n) {#internationalization-i18n}

Komponentin otsikot, kuvaukset, etiketit ja viestit ovat täysin muokattavissa käyttämällä `FileUploadI18n`-luokkaa. Tämä joustavuus mahdollistaa dialogin käyttöliittymän muokkaamisen erityisten paikallistamisvaatimusten tai henkilökohtaisten mieltymysten mukaan.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog("Lataa tiedosto");
FileUploadI18n i18n = new FileUploadI18n();
i18n.setUpload("Lataa");
i18n.setCancel("Peruuta");
dialog.setI18n(i18n);
UploadedFile result = dialog.show();
```

## Parhaat käytännöt {#best-practices}

1. **Selkeät ja ytimekkäät kehykset**: Varmista, että kehysviesti selkeästi selittää, mitä käyttäjältä pyydetään lataamaan.
2. **Sopivat suodattimet**: Aseta tiedostosuodattimia, jotka vastaavat vaadittuja tiedostotyyppejä varmistaaksesi, että käyttäjät lataavat asiaankuuluvia tiedostoja.
3. **Johdonmukainen teema**: Yhdistä dialogin ja latauskentän teemat sovelluksesi muotoiluun.
4. **Yliannostuksen minimointi**: Käytä tiedoston latausdialogeja säästeliäästi käyttäjäturhauman välttämiseksi. Varaa ne toimille, jotka vaativat erityisiä käyttäjän tiedostolatauksia.
