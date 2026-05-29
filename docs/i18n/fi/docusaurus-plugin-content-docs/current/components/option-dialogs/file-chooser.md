---
title: File Chooser
sidebar_position: 10
_i18n_hash: 3fb68fdcc1fc0d263114babc2a64a6f4
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileChooserDialog" top='true'/>

`FileChooserDialog` on modaalinen dialogi, joka on suunniteltu mahdollistamaan käyttäjän valita tiedosto tai hakemisto palvelimen tiedostojärjestelmästä. Dialogi estää sovelluksen suorittamisen, kunnes käyttäjä tekee valinnan tai sulkee dialogin.

<!-- INTRO_END -->

## Käytännöt {#usages}

`FileChooserDialog` tarjoaa tavan valita tiedostoja tai hakemistoja tiedostojärjestelmästä, mikä mahdollistaa käyttäjien valita hakemistoja tietojen tallentamista varten tai suorittaa tiedosto-operaatioita.

<ComponentDemo
path='/webforj/filechooserdialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogBasicView.java']}
height='600px'
/>

## Tulos {#result}

`FileChooserDialog` palauttaa valitun tiedoston tai hakemiston merkkijonona. Jos käyttäjä sulkee dialogin tekemättä valintaa, tulos on `null`.

:::info
Palautettava merkkijono palautuu `show()`-metodista tai vastaavasta `OptionDialog`-metodista, kuten alla on esitetty.
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

`FileChooserDialog` tukee erilaisia valintatyylejä, mikä mahdollistaa valintamenetelmän räätälöinnin tarpeidesi mukaan:

1. **FILES**: Mahdollistaa vain tiedostojen valinnan.
2. **DIRECTORIES**: Mahdollistaa vain hakemistojen valinnan.
3. **FILES_AND_DIRECTORIES**: Mahdollistaa sekä tiedostojen että hakemistojen valinnan.

## Alkuperäinen polku {#initial-path}

`FileChooserDialog` mahdollistaa määrittää alkuperäisen polun, johon dialogi avataan näytettäessä. Tämä voi tarjota käyttäjille lähtöpaikan tiedoston valitsemiseksi.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Valitse tiedosto", "/home/user");
String result = dialog.show();
```

## Rajoitus {#restriction}

Voit rajoittaa dialogia tiettyyn hakemistoon estäen käyttäjiä navigoimasta sen ulkopuolelle käyttämällä `setRestricted(boolean restricted)`-metodia.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Valitse tiedosto", "/home/user");
dialog.setRestricted(true);
dialog.show();
```

## Suodattimet {#filters}

Kun valintatila on `FILES`, `FileChooserDialog` sallii suodattimien asettamisen tiedostotyyppien rajoittamiseksi, jotka listataan. Voit määrittää suodattimia käyttämällä `setFilters(List<FileChooserFilter> filters)`-metodia.

<ComponentDemo
path='/webforj/filechooserdialogfilters'
files={['src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogFiltersView.java']}
height='600px'
/>

### Mukautetut suodattimet {#custom-filters}

Voit sallia käyttäjien lisätä mukautettuja suodattimia mahdollistaen mukautetut suodattimet -ominaisuuden käyttämällä `setCustomFilters(boolean customFilters)`-metodia. Mukautetut suodattimet tallennetaan oletuksena selaimen paikalliseen tallennustilaan ja palautetaan, kun dialogi näytetään uudelleen.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Valitse tiedosto", "/home/user");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Kansainvälistäminen (i18n) {#internationalization-i18n}

Komponentin otsikot, kuvastot, etiketit ja viestit ovat täysin mukautettavissa käyttämällä `FileChooserI18n`-luokkaa. Tämä joustavuus mahdollistaa dialogin käyttöliittymän räätälöimisen tietyihin lokalisoimisiin vaatimuksiin tai henkilökohtaisiin mieltymyksiin.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Wählen Sie eine Datei aus", "/Users/habof/bbx");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Wählen");
i18n.setCancel("Stornieren");
dialog.setI18n(i18n);
```

## Parhaat käytännöt {#best-practices}

1. **Selkeät ja tiiviit kysymykset**: Varmista, että kysymysviesti selkeästi selittää, mitä käyttäjältä kysytään valittavaksi.
2. **Oikeat valintatilat**: Valitse valintatyylit, jotka vastaavat tarvittavaa käyttäjatoimintoa varmistaaksesi tarkat ja asiaankuuluvat valinnat.
3. **Loogiset alkuperäiset polut**: Aseta alkuperäiset polut, jotka tarjoavat käyttäjille käyttökelpoisen lähtöpaikan valinnalleen.
4. **Rajoita hakemistojen navigointia**: Rajoita dialogi tiettyyn hakemistoon, kun se on tarpeen, estääksesi käyttäjiä navigoimasta valtuuttamattomiin alueisiin.
