---
title: File Chooser
sidebar_position: 10
description: >-
  Open a blocking FileChooserDialog to let users pick files or directories from
  the server, with selection modes and initial paths.
_i18n_hash: c86dfab4207241cab3bb28da3e1236ab
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileChooserDialog" top='true'/>

`FileChooserDialog` on modaalinen dialogi, joka on suunniteltu antamaan käyttäjälle mahdollisuus valita tiedosto tai hakemisto palvelimen tiedostojärjestelmästä. Dialogi estää sovelluksen toiminnan, kunnes käyttäjä tekee valinnan tai sulkee dialogin.

<!-- INTRO_END -->

## Käytännöt {#usages}

`FileChooserDialog` tarjoaa tavan valita tiedostoja tai hakemistoja tiedostojärjestelmästä, jolloin käyttäjät voivat valita hakemistoja tietojen tallentamista varten tai suorittaa tiedostotoimintoja.

<ComponentDemo
path='/webforj/filechooserdialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogBasicView.java']}
height='600px'
/>

## Tulosta {#result}

`FileChooserDialog` palauttaa valitun tiedoston tai hakemiston merkkijonona. Jos käyttäjä sulkee dialogin ilman, että hän tekee valintaa, tulos on `null`.

:::info
Palautettava merkkijono palautetaan `show()` -menetelmästä tai vastaavasta `OptionDialog`-menetelmästä, kuten alla on esitetty.
:::

```java showLineNumbers
String result = OptionDialog.showFileChooserDialog(
  "Valitse tiedosto", "/home/user", FileChooserDialog.SelectionMode.FILES);

if (result != null) {
  OptionDialog.showMessageDialog("Valitsit: " + result, "Valinta tehty");
} else {
  OptionDialog.showMessageDialog("Ei valintaa tehty", "Valinta peruutettu");
}
```

## Valintatila {#selection-mode}

`FileChooserDialog` tukee erilaisia valintatiloja, jolloin voit räätälöidä valintamenetelmää omien tarpeidesi mukaan:

1. **FILES**: Sallii vain tiedostojen valinnan.
2. **DIRECTORIES**: Sallii vain hakemistojen valinnan.
3. **FILES_AND_DIRECTORIES**: Sallii sekä tiedostojen että hakemistojen valinnan.

## Alkuperäinen polku {#initial-path}

`FileChooserDialog` mahdollistaa alkuperäisen polun määrittämisen, johon dialogi avautuu esitettaessä. Tämä voi antaa käyttäjille lähtöpisteen tiedostovalinnalle.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Valitse tiedosto", "/home/user");
String result = dialog.show();
```

## Rajoitus {#restriction}

Voit rajoittaa dialogia tiettyyn hakemistoon, estäen käyttäjiä navigoimasta sen ulkopuolelle käyttämällä `setRestricted(boolean restricted)`-menetelmää.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Valitse tiedosto", "/home/user");
dialog.setRestricted(true);
dialog.show();
```

## Suodattimet {#filters}

Kun valintatila on `FILES`, `FileChooserDialog` mahdollistaa suodattimien asettamisen tiedostojen rajoittamiseksi. Voit määrittää suodattimia käyttämällä `setFilters(List<FileChooserFilter> filters)`-menetelmää.

<ComponentDemo
path='/webforj/filechooserdialogfilters'
files={['src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogFiltersView.java']}
height='600px'
/>

### Mukautetut suodattimet {#custom-filters}

Voit sallia käyttäjien lisätä mukautettuja suodattimia mahdollistamalla mukautetut suodattimet -ominaisuuden käyttäen `setCustomFilters(boolean customFilters)`-menetelmää. Mukautetut suodattimet tallennetaan oletuksena selaimen paikalliseen tallennustilaan ja palautetaan, kun dialogi näytetään uudelleen.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Valitse tiedosto", "/home/user");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Kansainvälistaminen (i18n) {#internationalization-i18n}

Komponentin otsikot, kuvaukset, etiketit ja viestit ovat täysin määritettävissä `FileChooserI18n`-luokan avulla. Tämä joustavuus mahdollistaa dialogin käyttöliittymän räätälöimisen erityisten paikallistamisvaatimusten tai personointisääntöjen mukaiseksi.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Valitse tiedosto", "/Users/habof/bbx");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Valitse");
i18n.setCancel("Peruuta");
dialog.setI18n(i18n);
```

## Parhaat käytännöt {#best-practices}

1. **Selkeät ja ytimekkäät kehotteet**: Varmista, että kehotteessa on selkeästi kerrottu, mitä käyttäjältä pyydetään valitsemaan.
2. **Sopivat valintatilat**: Valitse valintatilat, jotka vastaavat vaadittua käyttäjätoimintoa varmistaaksesi tarkat ja asiaankuuluvat valinnat.
3. **Loogiset alkuperäiset polut**: Aseta alkuperäiset polut, jotka tarjoavat käyttäjille hyödyllisen lähtöpisteen valinnalle.
4. **Rajoita hakemiston navigointia**: Rajoita dialogia tiettyyn hakemistoon, kun se on tarpeen, estääksesi käyttäjiä navigoimasta valtuuttamattomilla alueilla.
