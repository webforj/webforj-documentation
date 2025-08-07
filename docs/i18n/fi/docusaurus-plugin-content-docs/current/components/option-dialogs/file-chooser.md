---
sidebar_position: 10
title: File Chooser
_i18n_hash: d0efdadb8ec1e44cfab2fb26f95efa0d
---
# Tiedoston valitsimen dialogi

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileChooserDialog" top='true'/>

`FileChooserDialog` on modaalinen dialogi, joka on suunniteltu sallimaan käyttäjälle tiedoston tai hakemiston valitseminen palvelimen tiedostojärjestelmästä. Dialogi estää sovelluksen suorittamisen, kunnes käyttäjä tekee valinnan tai sulkee dialogin.

```java
OptionDialog.showFileChooserDialog("Valitse tiedosto");
```

## Käyttötapaukset {#usages}

`FileChooserDialog` tarjoaa tavan valita tiedostoja tai hakemistoja tiedostojärjestelmästä, mahdollistamalla käyttäjien valita hakemistoja tiedon tallentamista varten tai suorittaa tiedostotoimintoja.

<ComponentDemo 
path='/webforj/filechooserdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogBasicView.java'
height = '600px'
/>

## Tulokset {#result}

`FileChooserDialog` palauttaa valitun tiedoston tai hakemiston merkkijonona. Jos käyttäjä sulkee dialogin ilman valintaa, tulos on `null`.

:::info
Palautettu merkkijono tulee näkymään `show()`-menetelmään, tai vastaavaan `OptionDialog`-menetelmään kuten alla on esitetty.
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

## Alkutie {#initial-path}

`FileChooserDialog` sallii määrittää alkupolun, johon dialogi avautuu esitettynä. Tämä voi tarjota käyttäjille lähtöpisteen tiedoston valinnassa.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Valitse tiedosto", "/home/user");
String result = dialog.show();
```

## Rajoitus {#restriction}

Voit rajoittaa dialogin tiettyyn hakemistoon estäen käyttäjiä navigoimasta sen ulkopuolelle käyttämällä `setRestricted(boolean restricted)`-menetelmää.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Valitse tiedosto", "/home/user");
dialog.setRestricted(true);
dialog.show();
```

## Suodattimet {#filters}

Kun valintatila on `FILES`, `FileChooserDialog` sallii suodattimien asettamisen, jotta voidaan rajata lueteltujen tiedostotyyppien valikoimaa. Voit määrittää suodattimet käyttämällä `setFilters(List<FileChooserFilter> filters)`-menetelmää.

<ComponentDemo 
path='/webforj/filechooserdialogfilters?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogFiltersView.java'
height = '600px'
/>

### Mukautetut suodattimet {#custom-filters}

Voit sallia käyttäjien lisätä mukautettuja suodattimia ottamalla käyttöön mukautetut suodatinominaisuudet käyttämällä `setCustomFilters(boolean customFilters)`-menetelmää.
Mukautetut suodattimet tallennetaan oletusarvoisesti selaimen paikalliseen tallennustilaan ja palautetaan, kun dialogi näytetään uudelleen.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Valitse tiedosto", "/home/user");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Kansainvälistäminen (i18n) {#internationalization-i18n}

Komponentin sisällä olevat otsikot, kuvastot, etikettit ja viestit ovat täysin mukautettavissa käyttäen `FileChooserI18n`-luokkaa. Tämä joustavuus mahdollistaa dialogin käyttöliittymän räätälöimisen erityisiin lokalisointivaatimuksiin tai personointipreferensseihin.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Valitse tiedosto", "/Users/habof/bbx");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Valitse");
i18n.setCancel("Peruuta");
dialog.setI18n(i18n);
```

## Parhaat käytännöt {#best-practices}

1. **Selkeät ja ytimekkäät kehottamiset**: Varmista, että kehotusviesti selvästi selittää, mitä käyttäjältä pyydetään valitsemaan.
2. **Sopivat valintatavat**: Valitse valintatiloja, jotka vastaavat vaadittua käyttäjätoimintoa varmistaaksesi tarkat ja relevantit valinnat.
3. **Loogiset alkupolut**: Aseta alkupolkuja, jotka tarjoavat käyttäjille hyödyllisen lähtöpisteen valinnalle.
4. **Rajoita hakemiston navigointia**: Rajoita dialogia tiettyyn hakemistoon, kun se on tarpeen, estääksesi käyttäjiä navigoimasta valtuuttamattomiin alueisiin.
