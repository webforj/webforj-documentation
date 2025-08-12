---
sidebar_position: 20
title: File Upload
_i18n_hash: 1218c7729c6cb025d2d6b4312bd95658
---
# tiedoston latausdialogi

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileUploadDialog" top='true'/>

`FileUploadDialog` on modaalinen dialogi, joka on suunniteltu sallimaan käyttäjän ladata tiedostoja paikalliselta tiedostojärjestelmältään. Dialogi estää sovelluksen suorittamisen, kunnes käyttäjä valitsee ladattavat tiedostot tai sulkee dialogin.

```java
UploadedFile result = OptionDialog.showFileUploadDialog("Lataa tiedosto");
```

## Käytännöt {#usages}

`FileUploadDialog` tarjoaa tavan valita ja ladata tiedostoja, jolloin käyttäjät voivat toimittaa asiakirjoja, kuvia tai muita sovelluksen tarvitsemia tiedostotyyppejä.

## Tulos {#result}

`FileUploadDialog` palauttaa `UploadedFile`-objektin, joka sisältää tietoa ladatusta tiedostosta, kuten sen nimen, koon ja sisällön. Jos käyttäjä sulkee dialogin valitsematta tiedostoa, tulos on `null`.

:::important
Tuloksena oleva merkkijono palautetaan `show()`-menetelmästä tai vastaavasta `OptionDialog`-menetelmästä, kuten alla on esitetty. 
:::

<ComponentDemo 
path='/webforj/fileuploaddialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/fileupload/FileUploadDialogBasicView.java'
height = '400px'
/>

### Laddettujen tiedostojen siirtäminen {#moving-uploaded-files}

Oletuksena webforJ tallentaa ladatut tiedostot väliaikaiseen kansioon, joka siivotaan säännöllisesti. Jos et siirrä tiedostoa muualle, se poistetaan. Siirtääksesi tiedoston, käytä `move`-menetelmää ja määritä kohdepolku.

```java showLineNumbers
UploadedFile uploadedFile = OptionDialog.showFileUploadDialog("Valitse ladattava tiedosto");
try {
    File file = uploadedFile.move("my/full/path/" + uploadedFile.getSanitizedClientName());
    // ... tee jotain tiedoston kanssa
} catch (IOException e) {
    // käsittele poikkeus
}
```
:::tip Puhtaat asiakasnimet
Käytä `getSanitizedClientName`-menetelmää saadaksesi puhtaan version ladatun tiedoston nimestä. Tämä menetelmä auttaa estämään turvallisuusriskejä, kuten hakemistoon navigointi hyökkäyksiä tai virheellisiä merkkejä tiedostonimissä, varmistaen tiedostojärjestelmäsi eheyden ja turvallisuuden.
:::

## Suodattimet {#filters}

`FileUploadDialog` sallii suodattimien asettamisen, jotta voidaan rajoittaa ladattavien tiedostojen tyyppejä. Voit määrittää suodattimia `setFilters(List<FileChooserFilter> filters)`-menetelmällä.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog(
    "Lataa tiedosto", 
    Arrays.asList(new FileChooserFilter("Tekstitiedostot", "*.txt")));
UploadedFile result = dialog.show();
```

:::warning Suodattimien validointi
Palvelin ei validoi ladattua tiedostoa suodattimien mukaan. Suodattimia sovelletaan vain käyttöliittymässä ohjaamaan käyttäjän valintaa. Sinun on toteutettava palvelinpuolen validointi varmistaaksesi, että ladatut tiedostot täyttävät sovelluksesi vaatimukset.
:::

## Suurin koko {#max-size}

On mahdollista asettaa uploadien maksimaalinen tiedostokoko varmistaaksesi, että käyttäjät eivät lataa tiedostoja, jotka ovat liian suuria sovelluksesi käsiteltäväksi. Tämä voidaan määrittää `setMaxFileSize(long maxSize)`-menetelmällä, jossa maxSize määritellään tavuina.

```java
dialog.setMaxFileSize(2 * 1024 * 1024); // Aseta maksimikoko 2 MB
```

## Kansainvälistäminen (i18n) {#internationalization-i18n}

Komponentin otsikot, kuvaukset, tunnisteet ja viestit ovat täysin räätälöitävissä `FileUploadI18n`-luokan avulla. Tämä joustavuus mahdollistaa dialogikäyttöliittymän mukauttamisen erityisten lokalisointivaatimusten tai henkilökohtaisten mieltymysten täyttämiseksi.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog("Lataa tiedosto");
FileUploadI18n i18n = new FileUploadI18n();
i18n.setUpload("Lataa");
i18n.setCancel("Peruuta");
dialog.setI18n(i18n);
UploadedFile result = dialog.show();
```

## Parhaat käytännöt {#best-practices}

1. **Selkeät ja ytimekkäät ohjeet**: Varmista, että kehotusviesti selkeästi selittää, mitä käyttäjältä kysytään ladattavaksi.
2. **Sopivat suodattimet**: Aseta tiedostosuodattimet, jotka vastaavat vaadittuja tiedostotyyppejä, jotta käyttäjät lataavat asiaankuuluvia tiedostoja.
3. **Loogiset aloituspolut**: Aseta aloituspolut, jotka tarjoavat käyttäjille hyödyllisen lähtöpisteen tiedostovalinnalle.
4. **Rajoita hakemiston navigointia**: Rajoita dialogi tiettyyn hakemistoon tarpeen mukaan estääksesi käyttäjiä navigoimasta oikeudettomiin alueisiin.
5. **Johdonmukainen teema**: Yhdistele dialogin ja latauskentän teemat sovelluksesi designiin yhtenäisen käyttäjäkokemuksen varmistamiseksi.
6. **Vähennä liiallista käyttöä**: Käytä tiedoston latausdialogeja säästeliäästi käyttäjäfrustraation välttämiseksi. Varaa niitä erityisiin käyttäjän tiedoston lataustoimintoihin.
