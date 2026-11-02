---
title: File Save
sidebar_position: 15
description: >-
  Prompt users for a save location with the FileSaveDialog, configuring initial
  path, default filename, and overwrite behavior.
_i18n_hash: 861257322ca233cfa9e0eed5e4d3aa95
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileSaveDialog" top='true'/>

`FileSaveDialog` on modaalinen valintaikkuna, joka on suunniteltu sallimaan käyttäjien tallentaa tiedosto tiettyyn sijaintiin palvelimen tiedostojärjestelmässä. Ikkuna estää sovelluksen suorituksen, kunnes käyttäjä antaa tiedostonimen ja vahvistaa toiminnon tai peruuttaa dialogin.

<!-- INTRO_END -->

## Käytöt {#usages}

`FileSaveDialog` tarjoaa sujuvan tavan tallentaa tiedostoja tiedostojärjestelmään, tarjoten käyttäjän konfiguroitavissa olevia vaihtoehtoja tiedoston nimeämiselle ja olemassa olevien tiedostojen käsittelylle.

<ComponentDemo
path='/webforj/filesavedialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogBasicView.java']}
height='800px'
/>

## Tulos {#result}

`FileSaveDialog` palauttaa valitun polun merkkijonona. Jos käyttäjä peruuttaa dialogin, tulos on `null`.

:::important Dialogin tarkoitus
Tämä dialogi ei itse asiassa tallenna tiedostoja, vaan palauttaa tiedostonimen, jonka käyttäjä on valinnut.
:::

:::info
Palautettu merkkijono palautetaan `show()`-metodista tai vastaavasta `OptionDialog`-metodista kuten alla on esitetty.
:::

```java showLineNumbers
String result = OptionDialog.showFileSaveDialog(
    "Tallenna tiedostosi", "/home/user/documents", "report.xls");

if (result != null) {
  OptionDialog.showMessageDialog("Valittu polku: " + result, "Polku valittu");
} else {
  OptionDialog.showMessageDialog("Ei polkua ole valittu", "Polku valittu",
      MessageDialog.MessageType.ERROR);
}
```

## Olemassa oleva toiminto {#exists-action}

`FileSaveDialog` tarjoaa konfiguroitavaa käyttäytymistä, kun tiedosto, jonka nimi on määritetty, on jo olemassa:

* **ACCEPT_WITHOUT_ACTION**: Valinta hyväksytään ilman lisätoimenpiteitä käyttäjältä.
* **ERROR_DIALOGUE**: Käyttäjälle esitetään virhe dialogi; valinta ei ole sallittu.
* **CONFIRMATION_DIALOGUE**: Käyttäjälle esitetään dialogi, jossa pyydetään vahvistusta. Tämä on oletus.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Tallenna tiedostosi", "/home/user/documents", "report.xls");
dialog.setExistsAction(FileSaveDialog.ExistsAction.ERROR_DIALOGUE);
String result = dialog.show();
```

## Valintatila {#selection-mode}

`FileSaveDialog` tukee erilaisia valintatiloja, mikä mahdollistaa valintamenetelmän mukauttamisen erityisiin tarpeisiin:

1. **TIEDOSTOT**: Sallii vain tiedostojen valinnan.
2. **KANSIOT**: Sallii vain kansioiden valinnan.
3. **TIEDOSTOT_JA_KANSIOT**: Sallii sekä tiedostojen että kansioiden valinnan.

## Alkuperäinen polku {#initial-path}

Määritä hakemisto, jossa dialogi avautuu alkuperäisellä polulla. Tämä auttaa käyttäjiä aloittamaan loogisessa hakemistossa tallennusprosessiaan varten.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Tallenna tiedostosi", "/home/user/documents", "report.xls");
String result = dialog.show();
```

## Rajoitus {#restriction}

Voit rajoittaa dialogia tiettyyn hakemistoon, estäen käyttäjiä navigoimasta sen ulkopuolelle käyttämällä `setRestricted(boolean restricted)`-metodia.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Tallenna tiedostosi", "/home/user/documents", "report.xls");
dialog.setRestricted(true);
dialog.show();
```

## Tiedostonimi {#filename}

Määritä oletustiedostonimi tallennustoimintoa varten ohjataksesi käyttäjiä ja minimoidaksesi virheitä.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Tallenna tiedostosi");
dialog.setName("report.xls");
String result = dialog.show();
```

## Kansainvälistäminen (i18n) {#internationalization-i18n}

Käytä `FileChooserI18n`-luokkaa mukauttamaan dialogin otsikoita, kuvastoja, etikettejä ja viestejä eri alueille tai sovelluksen vaatimuksille.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Tallenna tiedostosi");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Valitse");
i18n.setCancel("Peruuta");
dialog.setI18n(i18n);
```

## Suodattimet {#filters}

`FileSaveDialog` sallii asettaa suodattimia rajoittaaksemme tallennettavien tiedostojen tyyppejä käyttämällä `setFilters(List<FileChooserFilter> filters)`-metodia.

<ComponentDemo
path='/webforj/filesavedialogfilters'
files={['src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogFiltersView.java']}
height='800px'
/>

### Mukautetut suodattimet {#custom-filters}

Voit ottaa käyttöön mukautetut suodattimet, jotta käyttäjät voivat määrittää omia tiedostosuodattimiaan käyttämällä `setCustomFilters(boolean customFilters)`-metodia. Suodattimet tallennetaan oletusarvoisesti paikalliseen tallennustilaan ja palautetaan seuraavissa dialogiominaisuuksissa.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Tallenna tiedostosi", "/home/user/documents");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Parhaat käytännöt {#best-practices}

* **Esimääritetyt tiedostonimet**: Tarjoa looginen oletustiedostonimi, kun se on mahdollista.
* **Vahvista ylitykset**: Käytä `CONFIRMATION_DIALOGUE` `ExistsAction`-valinnalle estääksesi vahingossa tapahtuvat ylitykset.
* **Intuitiivinen alkuperäinen polku**: Aseta alkuperäinen polku, joka vastaa käyttäjien odotuksia.
* **Kansainvälistäminen**: Mukauta dialogiteksti parantaaksesi käytettävyyttä kansainvälisille yleisöille.
* **Tiedostotyyppisuodattimet**: Hyödynnä suodattimia rajoittaaksesi tiedostotyyppejä ja ohjataksesi käyttäjiä kohti kelvollisia tiedostopäätteitä.
