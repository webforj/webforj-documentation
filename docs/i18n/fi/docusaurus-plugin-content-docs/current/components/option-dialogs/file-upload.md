---
title: File Upload
sidebar_position: 20
_i18n_hash: 70bfb275a09a977cf365a14535aaf02e
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileUploadDialog" top='true'/>

`FileUploadDialog` on modaalinen dialogi, joka on suunniteltu sallimaan käyttäjän ladata tiedostoja paikalliselta tiedostojärjestelmältään. Dialogi estää sovelluksen suorittamisen, kunnes käyttäjä valitsee ladattavat tiedostot tai sulkee dialogin.

<!-- INTRO_END -->

## Käyttötarkoitukset {#usages}

`FileUploadDialog` tarjoaa tavan valita ja ladata tiedostoja, mahdollistaen käyttäjien lähettää asiakirjoja, kuvia tai muita tiedostotyyppejä, joita sovellus vaatii. Käytä `showFileUploadDialog()`-metodia näyttämään dialogi ja tallentamaan ladattu tiedosto.

```java
UploadedFile result = OptionDialog.showFileUploadDialog("Lataa tiedosto");
```

## Tulos {#result}

`FileUploadDialog` palauttaa `UploadedFile`-objektin, joka sisältää tietoja ladatusta tiedostosta, kuten sen nimen, koon ja sisällön. Jos käyttäjä sulkee dialogin ilman tiedoston valitsemista, tulos on `null`.

:::important
Tuloksena oleva merkkijono palautetaan `show()`-metodista tai vastaavasta `OptionDialog`-metodista, kuten alla on esitetty. 
:::

<ComponentDemo 
path='/webforj/fileuploaddialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/fileupload/FileUploadDialogBasicView.java'
height = '400px'
/>

### Ladattujen tiedostojen siirtäminen {#moving-uploaded-files}

Oletusarvoisesti webforJ tallentaa ladatut tiedostot tilapäiseen hakemistoon, jota siivotaan säännöllisesti. Jos et siirrä tiedostoa muualle, se poistetaan. Siirtääksesi tiedoston, käytä `move`-metodia ja määritä kohdepolku.

```java showLineNumbers
UploadedFile uploadedFile = OptionDialog.showFileUploadDialog("Valitse tiedosto ladattavaksi");
try {
    File file = uploadedFile.move("my/full/path/" + uploadedFile.getSanitizedClientName());
    // ... tee jotain tiedoston kanssa
} catch (IOException e) {
    // käsittele poikkeus
}
```
:::tip Puhtaat asiakastiedostonimen
Käytä `getSanitizedClientName`-metodia saadaksesi puhtaan version ladatun tiedoston nimestä. Tämä metodi auttaa estämään turvallisuusriskejä, kuten hakemustason läpäisyn tai virheelliset merkit tiedostonimissä, varmistaen tiedostojärjestelmäsi eheyden ja turvallisuuden.
:::

## Suodattimet {#filters}

`FileUploadDialog` sallii sinun asettaa suodattimia rajoittaaksesi ladattavien tiedostojen tyyppejä. Voit konfiguroida suodattimia käyttämällä `setFilters(List<FileChooserFilter> filters)`-metodia.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog(
    "Lataa tiedosto", 
    Arrays.asList(new FileChooserFilter("Tekstitiedostot", "*.txt")));
UploadedFile result = dialog.show();
```

:::warning Suodattimien validointi
Palvelin ei validoi ladattua tiedostoa suodattimien mukaan. Suodattimia sovelletaan vain käyttöliittymässä, jotta käyttäjän valintaa ohjataan. Sinun on toteutettava palvelinpuolen validointi varmistaaksesi, että ladatut tiedostot täyttävät sovelluksesi vaatimukset.
:::

## Maksimikoko {#max-size}

On mahdollista asettaa ladattavien tiedostojen maksimikoko varmistaaksesi, että käyttäjät eivät lataa tiedostoja, jotka ovat liian suuria sovelluksesi käsiteltäväksi. Tämä voidaan konfiguroida käyttämällä `setMaxFileSize(long maxSize)`-metodia, jossa maxSize määritetään tavuina.

```java
dialog.setMaxFileSize(2 * 1024 * 1024); // Aseta maksimi koko 2 MB
```

## Kansainvälistäminen (i18n) {#internationalization-i18n}

Komponentin otsikot, kuvaukset, labelit ja viestit ovat täysin mukautettavissa käyttämällä `FileUploadI18n`-luokkaa. Tämä joustavuus mahdollistaa dialogin käyttöliittymän räätälöimisen tiettyjen lokalisointivaatimusten tai personointimieltymysten täyttämiseksi.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog("Lataa tiedosto");
FileUploadI18n i18n = new FileUploadI18n();
i18n.setUpload("Lataa");
i18n.setCancel("Peruuta");
dialog.setI18n(i18n);
UploadedFile result = dialog.show();
```

## Parhaat käytännöt {#best-practices}

1. **Selkeät ja ytimekkäät kehotteet**: Varmista, että kehotteessa selvästi kerrotaan, mitä käyttäjältä pyydetään lataamaan.
2. **Sopivat suodattimet**: Aseta tiedostosuodattimet, jotka vastaavat vaadittuja tiedostotyyppejä varmistaaksesi, että käyttäjät lataavat asiaankuuluvia tiedostoja.
3. **Loogiset alkuperäiset polut**: Aseta alkuperäiset polut, jotka tarjoavat käyttäjille hyödyllisen lähtöpisteen tiedostovalintojaan varten.
4. **Rajoita hakemistorajapintaa**: Rajoita dialogi tiettyyn hakemistoon tarvittaessa estääksesi käyttäjiä navigoimasta luvattomiin alueisiin.
5. **Johdonmukainen teema**: Yhdistä dialogin ja latauskentän teemat sovelluksesi suunnittelun kanssa yhtenäisen käyttäjäkokemuksen saavuttamiseksi.
6. **Vältä liiallista käyttöä**: Käytä tiedostojen latausdialogeja säästeliäästi estääksesi käyttäjien turhautumista. Varaudu käyttämään niitä vain toimissa, jotka vaativat erityisiä käyttäjän tiedostojen latauksia.
