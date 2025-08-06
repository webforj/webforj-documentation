---
sidebar_position: 20
title: File Upload
_i18n_hash: e25933325d4f0d5a7044a5e0776e3741
---
# Tiedostojen latausdialogi

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileUploadDialog" top='true'/>

`FileUploadDialog` on modalinen dialogi, joka on suunniteltu sallimaan käyttäjän ladata tiedostoja omalta paikalliselta tiedostojärjestelmältään. Dialogi estää sovelluksen suorittamisen, kunnes käyttäjä valitsee ladattavat tiedostot tai sulkee dialogin.

```java
UploadedFile result = OptionDialog.showFileUploadDialog("Lataa tiedosto");
```

## Käytöt {#usages}

`FileUploadDialog` tarjoaa tavan valita ja ladata tiedostoja, mahdollistaen käyttäjien lähettää asiakirjoja, kuvia tai muita tiedostotyyppejä, joita sovellus tarvitsee.

## Tulos {#result}

`FileUploadDialog` palauttaa `UploadedFile`-objektin, joka sisältää tietoja ladatusta tiedostosta, kuten sen nimen, koon ja sisällön. Jos käyttäjä sulkee dialogin ilman tiedoston valitsemista, tulos on `null`.

:::important
Palautettava merkkijono palautuu `show()`-menetelmästä tai vastaavasta `OptionDialog`-menetelmästä, kuten alla on esitetty.
:::

<ComponentDemo 
path='/webforj/fileuploaddialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/fileupload/FileUploadDialogBasicView.java'
height = '400px'
/>

### Ladattujen tiedostojen siirtäminen {#moving-uploaded-files}

Oletusarvoisesti webforJ tallentaa ladatut tiedostot tilapäiseen kansioon, joka puhdistetaan säännöllisesti. Jos et siirrä tiedostoa muualle, se poistetaan. Tiedoston siirtämiseen käytä `move`-menetelmää ja määritä kohdepolku.

```java showLineNumbers
UploadedFile uploadedFile = OptionDialog.showFileUploadDialog("Valitse ladattava tiedosto");
try {
    File file = uploadedFile.move("my/full/path/" + uploadedFile.getSanitizedClientName());
    // ... tee jotain tiedoston kanssa
} catch (IOException e) {
    // käsittele poikkeus
}
```
:::tip Suojattu asiakastieto
Käytä `getSanitizedClientName`-menetelmää saadaksesi suojatun version ladatun tiedoston nimestä. Tämä menetelmä auttaa estämään turvallisuusriskit, kuten hakemistorakenteen hyökkäykset tai virheelliset merkit tiedoston nimissä, varmistaen tiedostojärjestelmäsi eheyden ja turvallisuuden.
:::

## Suodattimet {#filters}

`FileUploadDialog` mahdollistaa suodattimien asettamisen rajoittaakseen ladattavien tiedostotyyppien valintaa. Voit määrittää suodattimet käyttämällä `setFilters(List<FileChooserFilter> filters)`-menetelmää.

```java showLineNumbers
FileUploadDialog dialog = new FileUploadDialog(
    "Lataa tiedosto", 
    Arrays.asList(new FileChooserFilter("Tekstitiedostot", "*.txt")));
UploadedFile result = dialog.show();
```

:::warning Suodattimien validointi
Palvelin ei validoi ladattua tiedostoa suodattimien mukaan. Suodattimia käytetään vain käyttöliittymässä ohjaamaan käyttäjän valintaa. Sinun on toteutettava palvelinpuolen validointi varmistaaksesi, että ladatut tiedostot vastaavat sovelluksesi vaatimuksia.
:::

## Maksimikoko {#max-size}

On mahdollista asettaa maksimi tiedostokoko ladattaville tiedostoille varmistaaksesi, ettei käyttäjät lataa liian suuria tiedostoja sovelluksesi käsiteltäväksi. Tämä voidaan määrittää käyttämällä `setMaxFileSize(long maxSize)`-menetelmää, jossa maxSize on ilmoitettu tavuina.

```java
dialog.setMaxFileSize(2 * 1024 * 1024); // Aseta maksimi koko 2 MB:ksi
```

## Kansainvälistäminen (i18n) {#internationalization-i18n}

Komponentin otsikot, kuvastot, etiketit ja viestit ovat täysin mukautettavissa käyttämällä `FileUploadI18n`-luokkaa. Tämä joustavuus mahdollistaa dialogin käyttöliittymän räätälöimisen erityisten lokalisointivaatimusten tai henkilökohtaisten mieltymysten mukaan.

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
2. **Sopivat suodattimet**: Aseta tiedostosuodattimia, jotka vastaavat vaadittuja tiedostotyyppejä varmistaaksesi, että käyttäjät lataavat relevantteja tiedostoja.
3. **Loogiset aloituspolut**: Aseta aloituspolkuja, jotka tarjoavat käyttäjille hyödyllisen lähdön tiedostovalinnalleen.
4. **Rajoita hakemistorakenteen navigointia**: Rajoita dialogia tiettyyn hakemistoon, kun se on tarpeen, estääksesi käyttäjiä navigoimasta valtuuttamattomiin alueisiin.
5. **Johdonmukainen teema**: Yhdistele dialogin ja latauskentän teemat sovelluksesi suunnittelun kanssa yhtenäisen käyttäjäkokemuksen luomiseksi.
6. **Vähennä liikakäyttöä**: Käytä tiedostojen latausdialogeja säästeliäästi käyttäjätyytyväisyyden välttämiseksi. Varaudu niihin toimiin, jotka vaativat erityisiä käyttäjän tiedostojen latauksia.
