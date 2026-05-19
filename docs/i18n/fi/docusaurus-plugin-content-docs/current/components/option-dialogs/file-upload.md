---
title: File Upload
sidebar_position: 20
_i18n_hash: fc6515e16590085708ed61b3aedff9f1
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileUploadDialog" top='true'/>

`FileUploadDialog` on muotoiltu modaalinen dialogi, joka on suunniteltu mahdollistamaan käyttäjän ladata tiedostoja paikalliselta tiedostojärjestelmältä. Dialogi estää sovelluksen suorittamisen, kunnes käyttäjä valitsee ladattavat tiedostot tai sulkee dialogin.

<!-- INTRO_END -->

## Käyttötilanteet {#usages}

`FileUploadDialog` tarjoaa tavan valita ja ladata tiedostoja, jolloin käyttäjät voivat lähettää asiakirjoja, kuvia tai muita sovelluksen vaatimuksia vastaavia tiedostotyyppejä. Käytä `showFileUploadDialog()` näyttämään dialogi ja tallentamaan ladattu tiedosto.

```java
UploadedFile result = OptionDialog.showFileUploadDialog("Lataa tiedosto");
```

## Tulokset {#result}

`FileUploadDialog` palauttaa `UploadedFile`-objektin, joka sisältää tietoja ladatusta tiedostosta, kuten sen nimi, koko ja sisältö. Jos käyttäjä sulkee dialogin ilman, että tiedostoa on valittu, tulos on `null`.

:::important
Palautettu merkkijono lähetetään `show()`-menetelmästä tai vastaavasta `OptionDialog`-menetelmästä, kuten alla on esitetty.
:::

<ComponentDemo
path='/webforj/fileuploaddialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/fileupload/FileUploadDialogBasicView.java']}
height='400px'
/>

### Ladattujen tiedostojen siirtäminen {#moving-uploaded-files}

Oletusarvoisesti webforJ tallentaa ladatut tiedostot tilapäiseen kansioon, jota siivotaan säännöllisesti. Jos et siirrä tiedostoa muualle, se poistetaan. Siirtääksesi tiedoston, käytä `move`-menetelmää ja määritä kohdepaino.

```java showLineNumbers
UploadedFile uploadedFile = OptionDialog.showFileUploadDialog("Valitse ladattava tiedosto");
try {
  File file = uploadedFile.move("my/full/path/" + uploadedFile.getSanitizedClientName());
  // ... tee jotakin tiedoston kanssa
} catch (IOException e) {
  // käsittele poikkeus
}
```
:::tip Puhdistettu asiakastieto
Käytä `getSanitizedClientName`-menetelmää saadaksesi puhdistetun version ladatun tiedoston nimestä. Tämä menetelmä auttaa estämään turvallisuusriskejä, kuten hakemistorakenteen kiertohyökkäykset tai virheelliset merkit tiedostonnimissä, varmistaen tiedostojärjestelmäsi eheys ja turvallisuus.
:::

## Suodattimet {#filters}

`FileUploadDialog` mahdollistaa suodattimien asettamisen rajatakseen ladattavien tiedostojen tyyppejä. Voit konfiguroida suodattimia käyttäen menetelmää `setFilters(List<FileChooserFilter> filters)`.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog(
  "Lataa tiedosto", 
  Arrays.asList(new FileChooserFilter("Tekstitiedostot", "*.txt")));
UploadedFile result = dialog.show();
```

:::warning Suodattimien validointi
Palvelin ei validoi ladattua tiedostoa suodattimien mukaan. Suodattimia sovelletaan vain käyttöliittymässä käyttäjän valinnan ohjaamiseksi. Sinun on toteutettava palvelinpuolen validointi varmistaaksesi, että ladatut tiedostot täyttävät sovelluksesi vaatimukset.
:::

## Maksimikoko {#max-size}

On mahdollista asettaa ladattavien tiedostojen maksimikoko varmistaaksesi, että käyttäjät eivät lataa tiedostoja, jotka ovat liian suuria sovelluksesi käsiteltäväksi. Tämä voidaan määrittää `setMaxFileSize(long maxSize)`-menetelmän avulla, jossa maxSize ilmoitetaan tavuina.

```java
dialog.setMaxFileSize(2 * 1024 * 1024); // Aseta maksimikoko 2 MB
```

## Kansainvälistäminen (i18n) {#internationalization-i18n}

Komponentin sisällä olevat otsikot, kuvaukset, etiketit ja viestit ovat täysin mukautettavissa käyttämällä `FileUploadI18n`-luokkaa. Tämä joustavuus mahdollistaa dialogin käyttöliittymän räätälöimisen tiettyjen lokalisointivaatimusten tai personoinnin mieltymysten mukaan.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog("Lataa tiedosto");
FileUploadI18n i18n = new FileUploadI18n();
i18n.setUpload("Lataa");
i18n.setCancel("Peruuta");
dialog.setI18n(i18n);
UploadedFile result = dialog.show();
```

## Parhaat käytännöt {#best-practices}

1. **Selkeät ja ytimekkäät kehotukset**: Varmista, että kehotusviesti selkeästi selittää, mitä käyttäjältä kysytään ladattavaksi.
2. **Sopivat suodattimet**: Aseta tiedostosuodattimet, jotka vastaavat tarvittavia tiedostotyyppejä varmistaaksesi, että käyttäjät lataavat asiaankuuluvia tiedostoja.
3. **Loogiset aloituspolut**: Aseta aloituspolut, jotka tarjoavat käyttäjille hyödyllisen lähtöpohjan tiedostovalinnalle.
4. **Rajoita hakemiston navigointia**: Rajoita dialogi tiettyyn hakemistoon tarvittaessa estääksesi käyttäjiä navigoimasta valtuuttamattomiin alueisiin.
5. **Johdonmukainen teema**: Yhdistä dialogin ja latauskentän teemat sovelluksesi muotoiluun yhtenäisen käyttäjäkokemuksen luomiseksi.
6. **Vähennä liiallista käyttöä**: Käytä tiedoston latausdialogeja säästeliäästi välttääksesi käyttäjien turhautumista. Varaudu niihin toimiin, jotka vaativat tiettyjen käyttäjän tiedostojen lataamista.
