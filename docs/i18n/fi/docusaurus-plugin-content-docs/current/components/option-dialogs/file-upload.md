---
title: File Upload
sidebar_position: 20
_i18n_hash: 0c52346e43f2f615464dde85f39d7cd0
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileUploadDialog" top='true'/>

`FileUploadDialog` on modaalinen dialogi, joka on suunniteltu sallimaan käyttäjän ladata tiedostoja paikalliselta tiedostojärjestelmältään. Dialogi estää sovelluksen suorittamisen, kunnes käyttäjä valitsee ladattavat tiedostot tai sulkee dialogin.

<!-- INTRO_END -->

## Käytöt {#usages}

`FileUploadDialog` tarjoaa tavan valita ja ladata tiedostoja, mikä mahdollistaa käyttäjien lähettää asiakirjoja, kuvia tai muita sovelluksen vaatimuksia vastaavia tiedostotyyppejä. Käytä `showFileUploadDialog()` näyttämään dialogi ja tallentaaksesi ladatun tiedoston.

```java
UploadedFile result = OptionDialog.showFileUploadDialog("Lataa tiedosto");
```

## Tulos {#result}

`FileUploadDialog` palauttaa `UploadedFile` -objektin, joka sisältää tietoja ladatusta tiedostosta, kuten sen nimen, koon ja sisällön. Jos käyttäjä sulkee dialogin ilman tiedoston valitsemista, tulos on `null`.

:::important
Saatu merkkijono palautetaan `show()`-metodista tai vastaavasta `OptionDialog`-metodista, kuten alla on esitetty. 
:::

<ComponentDemo 
path='/webforj/fileuploaddialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/fileupload/FileUploadDialogBasicView.java'
height = '400px'
/>

### Ladattujen tiedostojen siirtäminen {#moving-uploaded-files}

Oletusarvoisesti webforJ tallentaa ladatut tiedostot väliaikaiseen kansioon, jota siivotaan säännöllisesti. Jos et siirrä tiedostoa muualle, se poistetaan. Tiedoston siirtämiseksi käytä `move`-metodia ja määritä kohdepoli.

```java showLineNumbers
UploadedFile uploadedFile = OptionDialog.showFileUploadDialog("Valitse ladattava tiedosto");
try {
  File file = uploadedFile.move("my/full/path/" + uploadedFile.getSanitizedClientName());
  // ... tee jotain tiedoston kanssa
} catch (IOException e) {
  // käsittele poikkeus
}
```
:::tip Puhtaaksi muokattu asiakasnimi
Käytä `getSanitizedClientName` -metodia saadaksesi puhdistetun version ladatun tiedoston nimestä. Tämä menetelmä auttaa estämään tietoturvariskit, kuten hakemistorakenteen hyökkäykset tai virheelliset merkit tiedostonimissä, varmistaen tiedostojärjestelmäsi eheyden ja turvallisuuden.
:::

## Suodattimet {#filters}

`FileUploadDialog` sallii sinun asettaa suodattimia rajoittaaksesi ladattavien tiedostojen tyyppejä. Voit määrittää suodattimia käyttämällä `setFilters(List<FileChooserFilter> filters)` -metodia.

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

On mahdollista asettaa maksimi tiedostokoko ladattaville tiedostoille varmistaaksesi, että käyttäjät eivät lataa liian suuria tiedostoja sovelluksellesi. Tämä voidaan määrittää käyttämällä `setMaxFileSize(long maxSize)` -metodia, jossa maxSize on määritelty tavuina.

```java
dialog.setMaxFileSize(2 * 1024 * 1024); // Aseta maksimi koko 2 MB
```

## Kansainvälistäminen (i18n) {#internationalization-i18n}

Komponentin otsikot, kuvaukset, tunnisteet ja viestit ovat täysin mukautettavissa käyttämällä `FileUploadI18n` -luokkaa. Tämä joustavuus mahdollistaa dialogin käyttöliittymän säätämisen erityisten lokalisointivaatimusten tai henkilökohtaisten mieltymysten mukaiseksi.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog("Lataa tiedosto");
FileUploadI18n i18n = new FileUploadI18n();
i18n.setUpload("Lataa");
i18n.setCancel("Peruuta");
dialog.setI18n(i18n);
UploadedFile result = dialog.show();
```

## Parhaat käytännöt {#best-practices}

1. **Selkeät ja ytimekkäät kehottavat viestit**: Varmista, että kehottava viesti selkeästi selittää, mitä käyttäjältä pyydetään lataamaan.
2. **Sopivat suodattimet**: Aseta tiedostosuojaimia, jotka vastaavat vaadittuja tiedostotyyppejä varmistaaksesi, että käyttäjät lataavat asiaankuuluvia tiedostoja.
3. **Loogiset alkuperäiset polut**: Aseta alkupolut, jotka tarjoavat käyttäjille hyödyllisen lähtökohdan tiedostovalintaan.
4. **Rajoita hakemistorakenteen navigointia**: Rajoita dialogi tiettyyn hakemistoon tarpeen mukaan estääksesi käyttäjiä navigoimasta luvattomille alueille.
5. **Johdonmukainen teema**: Sovita dialogin ja latauskentän teemat sovelluksen ulkoasuun yhtenäisen käyttäjäkokemuksen saavuttamiseksi.
6. **Vähennä liikakäyttöä**: Käytä tiedoston latausdialogeja säästeliäästi käyttäjäärsytyksen välttämiseksi. Varaa ne toimenpiteille, jotka vaativat erityisiä käyttäjän tiedostolatauksia.
