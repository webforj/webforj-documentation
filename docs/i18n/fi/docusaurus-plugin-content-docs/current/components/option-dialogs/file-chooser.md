---
sidebar_position: 10
title: File Chooser
_i18n_hash: 037ee8efaa2ed2f474d79c7e22dab3b5
---
# Tiedoston valinta -ikkuna

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileChooserDialog" top='true'/>

`FileChooserDialog` on modaalinen dialogi, joka on suunniteltu sallimaan käyttäjän valita tiedosto tai hakemisto palvelimen tiedostojärjestelmästä. Dialogi estää sovelluksen toiminnan, kunnes käyttäjä tekee valinnan tai sulkee dialogin.

```java
OptionDialog.showFileChooserDialog("Valitse tiedosto");
```

## Käytännöt {#usages}

`FileChooserDialog` tarjoaa tavan valita tiedostoja tai hakemistoja tiedostojärjestelmästä, mahdollistaen käyttäjien valita hakemistoja tietojen tallentamiseen tai suorittaa tiedosto-operaatioita.

<ComponentDemo 
path='/webforj/filechooserdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogBasicView.java'
height = '600px'
/>

## Tulokset {#result}

`FileChooserDialog` palauttaa valitun tiedoston tai hakemiston merkkijonona. Jos käyttäjä sulkee dialogin ilman valintaa, tulos on `null`.

:::info
Palautettava merkkijono palautetaan `show()`-menetelmästä tai vastaavasta `OptionDialog`-menetelmästä kuten alla on esitetty. 
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

`FileChooserDialog` tukee erilaisia valintatiloja, jolloin voit räätälöidä valintamenetelmän tarpeidesi mukaan:

1. **FILES**: Sallii vain tiedostojen valinnan.
2. **DIRECTORIES**: Sallii vain hakemistojen valinnan.
3. **FILES_AND_DIRECTORIES**: Sallii sekä tiedostojen että hakemistojen valinnan.

## Alkuperäinen polku {#initial-path}

`FileChooserDialog` sallii sinun määrittää alkuperäisen polun, johon dialogi avautuu, kun se näytetään. Tämä voi antaa käyttäjille aloituspisteen tiedoston valintaan.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Valitse tiedosto", "/home/user");
String result = dialog.show();
```

## Rajoitus {#restriction}

Voit rajoittaa dialogia tiettyyn hakemistoon estäen käyttäjiä navigoimasta sen ulkopuolelle käyttämällä `setRestricted(boolean restricted)`-menetelmää.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Valitse tiedosto", "/home/user");
dialog.setRestricted(true);
dialog.show();
```

## Suodattimet {#filters}

Kun valintatila on `FILES`, `FileChooserDialog` sallii sinun asettaa suodattimia rajoittaaksesi listattavien tiedostotyyppien määrää. Voit määrittää suodattimia käyttämällä `setFilters(List<FileChooserFilter> filters)`-menetelmää.

<ComponentDemo 
path='/webforj/filechooserdialogfilters?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogFiltersView.java'
height = '600px'
/>

### Mukautetut suodattimet {#custom-filters}

Voit antaa käyttäjille mahdollisuuden lisätä mukautettuja suodattimia ottamalla käyttöön mukautetut suodatinominaisuudet käyttämällä `setCustomFilters(boolean customFilters)`-menetelmää. Mukautetut suodattimet tallennetaan oletuksena selaimen paikalliseen tallennustilaan ja palautetaan, kun dialogi näytetään uudelleen.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Valitse tiedosto", "/home/user");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Käsittely eri kielillä (i18n) {#internationalization-i18n}

Komponentin otsikot, kuvastot, merkinnät ja viestit ovat täysin mukautettavissa  käyttämällä `FileChooserI18n`-luokkaa. Tämä joustavuus sallii sinun räätälöidä dialogin käyttöliittymää tiettyjen lokalisaatiovaatimusten tai personointipreferenssien mukaisesti.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Wählen Sie eine Datei aus", "/Users/habof/bbx");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Wählen");
i18n.setCancel("Stornieren");
dialog.setI18n(i18n);
```

## Parhaat käytännöt {#best-practices}

1. **Selkeät ja ytimekkäät kehykset**: Varmista, että kehysviesti selittää selkeästi, mitä käyttäjältä pyydetään valitsemaan.
2. **Sopivat valintatilat**: Valitse valintatilat, jotka vastaavat tarvittavaa käyttäjätoimintoa varmistaaksesi tarkat ja merkitykselliset valinnat.
3. **Loogiset alkuperäiset polut**: Aseta alkuperäiset polut, jotka tarjoavat käyttäjille hyödyllisen aloituspisteen valinnalle.
4. **Rajoita hakemistojen navigointia**: Rajoita dialogia tiettyyn hakemistoon, kun se on tarpeen, estääksesi käyttäjiä navigoimasta valtuuttamattomiin alueisiin.
