---
title: File Chooser
sidebar_position: 10
_i18n_hash: 49a069004ead8d962b32e132183819e8
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileChooserDialog" top='true'/>

`FileChooserDialog` on modaalinen dialogi, joka on suunniteltu mahdollistamaan käyttäjän valita tiedosto tai hakemisto palvelimen tiedostojärjestelmästä. Dialogi estää sovelluksen suorittamisen, kunnes käyttäjä tekee valinnan tai sulkee dialogin.

<!-- INTRO_END -->

## Käyttötarkoitukset {#usages}

`FileChooserDialog` tarjoaa tavan valita tiedostoja tai hakemistoja tiedostojärjestelmästä, mahdollistaen käyttäjien valita hakemistoja tietojen tallentamista varten tai suorittaa tiedosto-operaatioita.

<ComponentDemo 
path='/webforj/filechooserdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogBasicView.java'
height = '600px'
/>

## Tulos {#result}

`FileChooserDialog` palauttaa valitun tiedoston tai hakemiston merkkijonona. Jos käyttäjä sulkee dialogin ilman valintaa, tulos on `null`.

:::info
Palautettu merkkijono palautetaan `show()`-metodista tai vastaavasta `OptionDialog`-metodista, kuten alla on esitetty.
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

`FileChooserDialog` tukee erilaisia ​​valintatiloja, jolloin voit räätälöidä valintamenetelmän tarpeidesi mukaan:

1. **FILES**: Sallii vain tiedostojen valinnan.
2. **DIRECTORIES**: Sallii vain hakemistojen valinnan.
3. **FILES_AND_DIRECTORIES**: Sallii sekä tiedostojen että hakemistojen valinnan.

## Alkuperäinen polku {#initial-path}

`FileChooserDialog` mahdollistaa alkuperäisen polun määrittämisen, mihin dialogi avataan näyttämisen yhteydessä. Tämä voi tarjota käyttäjille lähtöpaikan tiedostovalinnalle.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Valitse tiedosto", "/home/user");
String result = dialog.show();
```

## Rajoitus {#restriction}

Voit rajoittaa dialogin tiettyyn hakemistoon, estäen käyttäjiä navigoimasta sen ulkopuolelle `setRestricted(boolean restricted)`-metodin avulla.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Valitse tiedosto", "/home/user");
dialog.setRestricted(true);
dialog.show();
```

## Suodattimet {#filters}

Kun valintatila on `FILES`, `FileChooserDialog` mahdollistaa suodattimien asettamisen rajoittaaksesi lueteltujen tiedostotyyppien määrittämistä. Voit konfiguroida suodattimia käyttämällä `setFilters(List<FileChooserFilter> filters)`-metodia.

<ComponentDemo 
path='/webforj/filechooserdialogfilters?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogFiltersView.java'
height = '600px'
/>

### Mukautetut suodattimet {#custom-filters}

Voit sallia käyttäjien lisätä mukautettuja suodattimia mahdollistamalla mukautettujen suodattimien ominaisuuden käyttämällä `setCustomFilters(boolean customFilters)`-metodia. Mukautetut suodattimet tallennetaan oletuksena selaimen paikalliseen tallennustilaan ja palautetaan takaisin, kun dialogi näytetään uudelleen.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Valitse tiedosto", "/home/user");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Kansainvälisyys (i18n) {#internationalization-i18n}

Komponentin otsikot, kuvaukset, tunnisteet ja viestit ovat täysin mukautettavissa käyttämällä `FileChooserI18n`-luokkaa. Tämä joustavuus sallii sinun räätälöidä dialogin käyttöliittymää vastaamaan erityisiä lokalisaatiovaatimuksia tai henkilökohtaisia ​​mieltymyksiä.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Valitse tiedosto", "/Users/habof/bbx");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Valitse");
i18n.setCancel("Peruuta");
dialog.setI18n(i18n);
```

## Parhaat käytännöt {#best-practices}

1. **Selkeät ja ytimekkäät kehotteet**: Varmista, että kehotteet selkeästi selittävät, mitä käyttäjältä pyydetään valitsemaan.
2. **Sopivat valintatilat**: Valitse valintatilat, jotka vastaavat vaadittua käyttäjätoimintoa varmistaaksesi tarkat ja asianmukaiset valinnat.
3. **Loogiset alkuperäiset polut**: Aseta alkuperäiset polut, jotka tarjoavat käyttäjille hyödyllisen lähtöpaikan valinnalleen.
4. **Rajoita hakemiston navigointia**: Rajoita dialogi tiettyyn hakemistoon tarpeen mukaan estääksesi käyttäjiä navigoimasta valtuuttamattomiin alueisiin.
