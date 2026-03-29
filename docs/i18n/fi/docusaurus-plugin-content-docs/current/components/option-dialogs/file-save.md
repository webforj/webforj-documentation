---
title: File Save
sidebar_position: 15
_i18n_hash: 728fd2b9211c993aed9b00abddb29b3e
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileSaveDialog" top='true'/>

`FileSaveDialog` on modaalinen dialogi, joka on suunniteltu sallimaan käyttäjien tallentaa tiedosto määritettyyn sijaintiin palvelimen tiedostojärjestelmässä. Dialogi estää sovelluksen suorittamisen, kunnes käyttäjä antaa tiedostonimen ja vahvistaa toiminnan tai peruuttaa dialogin.

<!-- INTRO_END -->

## Käyttötarkoitukset {#usages}

`FileSaveDialog` tarjoaa sujuvan tavan tallentaa tiedostoja tiedostojärjestelmään, tarjoten käyttäjän määritettäviä vaihtoehtoja tiedostojen nimeämiseksi ja olemassa olevien tiedostojen käsittelemiseksi.

<ComponentDemo 
path='/webforj/filesavedialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogBasicView.java'
height = '800px'
/>

## Tulokset {#result}

`FileSaveDialog` palauttaa valitun polun merkkijonona. Jos käyttäjä peruuttaa dialogin, paluuarvo on `null`.

:::important Dialogin tarkoitus
Tämä dialogi ei itse asiassa tallenna tiedostoja, vaan palauttaa käyttäjän valitsemat tiedostonumerot.
:::

:::info
Palautettu merkkijono saadaan `show()`-metodista tai vastaavasta `OptionDialog`-metodista, kuten alla on esitetty.
:::

```java showLineNumbers
String result = OptionDialog.showFileSaveDialog(
    "Tallenna tiedostosi", "/home/user/documents", "report.xls");

if (result != null) {
  OptionDialog.showMessageDialog("Tiedosto tallennettu: " + path, "Valittu polku");
} else {
  OptionDialog.showMessageDialog("Ei polkua ole valittu", "Valittu polku",
      MessageDialog.MessageType.ERROR);
}
```

## Olemassa oleva toiminto {#exists-action}

`FileSaveDialog` tarjoaa säädettävän käyttäytymisen, kun tiedosto, jolla on määrätty nimi, on jo olemassa:

* **ACCEPT_WITHOUT_ACTION**: Valinta hyväksytään ilman lisätoimia käyttäjältä.
* **ERROR_DIALOGUE**: Käyttäjälle esitetään virhedialogi; valintaa ei sallita.
* **CONFIRMATION_DIALOGUE**: Käyttäjälle esitetään dialogi, jossa pyydetään vahvistusta. Tämä on oletus.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Tallenna tiedostosi", "/home/user/documents", "report.xls");
dialog.setExistsAction(FileSaveDialog.ExistsAction.ERROR_DIALOGUE);
String result = dialog.show();
```

## Valintatapa {#selection-mode}

`FileSaveDialog` tukee erilaisia valintatapoja, jolloin voit räätälöidä valintamenetelmän tarpeidesi mukaan:

1. **TIEDOSTOT**: Sallii vain tiedostojen valinnan.
2. **HAKEMISTOT**: Sallii vain hakemiston valinnan.
3. **TIEDOSTOT_JA_HAKEMISTOT**: Sallii sekä tiedostojen että hakemistojen valinnan.

## Alkuperäinen polku {#initial-path}

Määritä hakemisto, josta dialogi avautuu alkuperäisellä polulla. Tämä auttaa käyttäjiä aloittamaan järkevästä hakemistosta tallennustoiminnolle.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Tallenna tiedostosi", "/home/user/documents", "report.xls");
String result = dialog.show();
```

## Rajoitus {#restriction}

Voit rajoittaa dialogin tiettyyn hakemistoon estäen käyttäjiä siirtymästä sen ulkopuolelle käyttämällä `setRestricted(boolean restricted)`-metodia.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Tallenna tiedostosi", "/home/user/documents", "report.xls");
dialog.setRestricted(true);
dialog.show();
```

## Tiedostonimi {#filename}

Aseta oletustiedostonimi tallennustoimenpiteelle, jotta voit opastaa käyttäjiä ja minimoida virheitä.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Tallenna tiedostosi");
dialog.setName("report.xls");
String result = dialog.show();
```

## Kansainvälistäminen (i18n) {#internationalization-i18n}

Komponentin otsikot, kuvaukset, merkinnät ja viestit ovat täysin mukautettavissa käyttämällä `FileSaveI18n`-luokkaa. Tämä varmistaa, että dialogi voidaan räätälöidä eri lokalisointi- tai personointitarpeisiin.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Tallenna tiedostosi");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Valitse");
i18n.setCancel("Peruuta");
dialog.setI18n(i18n);
```

## Suodattimet {#filters}

`FileSaveDialog` sallii suodattimien asettamisen tiedostotyyppien rajoittamiseksi, jotka voidaan tallentaa käyttämällä `setFilters(List<FileSaveFilter> filters)`-metodia.

<ComponentDemo 
path='/webforj/filesavedialogfilters?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogFiltersView.java'
height = '800px'
/>

### Mukautetut suodattimet {#custom-filters}

Voit mahdollistaa mukautetut suodattimet, jotka sallivat käyttäjien määrittää omia tiedostosuodattimia käyttämällä `setCustomFilters(boolean customFilters)`-metodia. Suodattimet tallennetaan oletuksena paikalliseen tallennustilaan ja palautetaan seuraavissa dialogien kutsuissa.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Tallenna tiedostosi", "/home/user/documents");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Parhaat käytännöt {#best-practices}

* **Ennalta määritellyt tiedostonimet**: Tarjoa järkevä oletustiedostonimi, kun se on mahdollista.
* **Vahvista ylikirjoitukset**: Käytä `CONFIRMATION_DIALOGUE`-asetusta `ExistsAction`-toiminnossa estämään vahingossa tapahtuvat ylikirjoitukset.
* **Intuitiivinen alkuperäinen polku**: Aseta alkuperäinen polku, joka vastaa käyttäjien odotuksia.
* **Kansainvälistäminen**: Mukauta dialogin tekstiä käytettävyyden parantamiseksi kansainvälisille käyttäjille.
* **Tiedostotyypin suodattimet**: Hyödynnä suodattimia rajoittaaksesi tiedostotyyppejä ja ohjataksesi käyttäjiä kohti voimassa olevia tiedostopäätteitä.
