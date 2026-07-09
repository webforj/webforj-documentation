---
title: File Upload
sidebar_position: 20
description: >-
  Capture client uploads with the FileUploadDialog, returning an UploadedFile
  that you can filter, move, and process server-side.
_i18n_hash: 708f41fa227a5a346bf58be64964dc9c
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileUploadDialog" top='true'/>

`FileUploadDialog` on uusi modaalinen dialogi, joka on suunniteltu sallimaan käyttäjän ladata tiedostoja paikalliselta tiedostojärjestelmältään. Dialogi estää sovelluksen toiminnan, kunnes käyttäjä valitsee ladattavat tiedostot tai sulkee dialogin.

<!-- INTRO_END -->

:::tip Inline component
Jos haluat tiedoston valitsin, joka renderöidään suoraan sivun asetteluun dialogin sijaan, harkitse [`Upload`](/docs/components/upload) komponentin käyttöä.
:::

## Usages {#usages}

`FileUploadDialog` tarjoaa tavan valita ja ladata tiedostoja, mahdollistaen käyttäjien lähettää asiakirjoja, kuvia tai muita tiedostotyyppejä, joita sovellus tarvitsee. Käytä `showFileUploadDialog()` näyttämään dialogi ja tallentamaan ladattu tiedosto.

```java
UploadedFile result = OptionDialog.showFileUploadDialog("Lataa tiedosto");
```

## Result {#result}

`FileUploadDialog` palauttaa `UploadedFile`-objektin, joka sisältää tietoa ladatusta tiedostosta, kuten sen nimen, koon ja sisällön. Jos käyttäjä sulkee dialogin ilman tiedoston valitsemista, tulos on `null`.

:::important
Tuloksena oleva merkkijono palautetaan `show()`-metodista tai vastaavasta `OptionDialog`-metodista, kuten alla on esitetty.
:::

<ComponentDemo
path='/webforj/fileuploaddialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/fileupload/FileUploadDialogBasicView.java']}
height='400px'
/>

### Liikkuvat ladatut tiedostot {#moving-uploaded-files}

Oletusarvoisesti webforJ tallentaa ladatut tiedostot väliaikaiseen kansioon, jota puhdistetaan säännöllisesti. Jos et siirrä tiedostoa muualle, se poistetaan. Tiedoston siirtämiseksi käytä `move`-metodia ja määritä kohdepolku.

```java showLineNumbers
UploadedFile uploadedFile = OptionDialog.showFileUploadDialog("Valitse ladattava tiedosto");
try {
  File file = uploadedFile.move("my/full/path/" + uploadedFile.getSanitizedClientName());
  // ... tee jotain tiedoston kanssa
} catch (IOException e) {
  // käsittele poikkeus
}
```
:::tip Sanitized Client Name
Käytä `getSanitizedClientName`-metodia saadaksesi puhdistettu versio ladatun tiedoston nimestä. Tämä metodi auttaa estämään turvallisuusriskit, kuten hakemistorakenteen läpi kulkemiset tai virheelliset merkit tiedoston nimissä, varmistaen tiedostojärjestelmäsi eheyden ja turvallisuuden.
:::

## Suodattimet {#filters}

`FileUploadDialog` mahdollistaa suodattimien asettamisen rajataksesi ladattavien tiedostojen tyyppejä. Voit määrittää suodattimet käyttämällä `setFilters(List<FileChooserFilter> filters)`-metodia.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog(
  "Lataa tiedosto",
  Arrays.asList(new FileChooserFilter("Tekstitiedostot", "*.txt")));
UploadedFile result = dialog.show();
```

:::warning Suodattimien validointi
Palvelin ei validoi ladattua tiedostoa suodattimien suhteen. Suodattimia käytetään vain käyttöliittymässä käyttäjän valinnan ohjaamiseen. Sinun on toteutettava palvelinpuolen validointi varmistaaksesi, että ladatut tiedostot täyttävät sovelluksesi vaatimukset.
:::

## Maksimikoko {#max-size}

On mahdollista asettaa maksimikoko ladattaville tiedostoille varmistaaksesi, että käyttäjät eivät lataa tiedostoja, jotka ovat liian suuria sovelluksesi käsiteltäväksi. Tämä voidaan määrittää käyttämällä `setMaxFileSize(long maxSize)`-metodia, jossa maxSize ilmoitetaan tavuina.

```java
dialog.setMaxFileSize(2 * 1024 * 1024); // Aseta max-koko 2 MB:ksi
```

## Kansainvälistäminen (i18n) {#internationalization-i18n}

Komponentin sisällä olevat otsikot, kuvaukset, etiketit ja viestit ovat täysin mukautettavissa käyttämällä `FileUploadI18n`-luokkaa. Tämä joustavuus mahdollistaa dialogikäyttöliittymän mukauttamisen erityisiin lokalisointivaatimuksiin tai henkilökohtaisiin mieltymyksiin.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog("Lataa tiedosto");
FileUploadI18n i18n = new FileUploadI18n();
i18n.setUpload("Lataa");
i18n.setCancel("Peruuta");
dialog.setI18n(i18n);
UploadedFile result = dialog.show();
```

## Parhaat käytännöt {#best-practices}

1. **Selkeät ja tiiviit kehotteet**: Varmista, että kehoteviesti selkeästi selittää, mitä käyttäjältä pyydetään lataamaan.
2. **Sopivat suodattimet**: Aseta tiedostosuodattimia, jotka vastaavat vaadittuja tiedostotyyppejä varmistaaksesi, että käyttäjät lataavat asiaankuuluvia tiedostoja.
3. **Loogiset aloituspolut**: Aseta aloituspolut, jotka antavat käyttäjille hyödyllisen lähtökohdan tiedostovalinnalle.
4. **Rajoita hakemistorakenteen navigointia**: Rajoita dialogi tiettyyn hakemistoon tarvittaessa estääksesi käyttäjiä navigoimasta valtuuttamattomiin alueisiin.
5. **Johdonmukainen teema**: Yhdistä dialogin ja latauskentän teemat sovelluksesi suunnittelun kanssa yhtenäisen käyttäjäkokemuksen saavuttamiseksi.
6. **Vähennä liiallista käyttöä**: Käytä tiedoston latausdialogeja säästeliäästi käyttäjäkärsimyksen välttämiseksi. Varaa ne toimintoihin, jotka vaativat erityisten käyttäjätiedostojen lataamista.
