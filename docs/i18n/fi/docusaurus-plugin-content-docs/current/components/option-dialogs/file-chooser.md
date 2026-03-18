---
title: File Chooser
sidebar_position: 10
_i18n_hash: c8d1ebc420bc1e1749c5c98a9fd3284c
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileChooserDialog" top='true'/>

`FileChooserDialog` on muotodialogi, joka on suunniteltu antamaan käyttäjälle mahdollisuus valita tiedosto tai hakemisto palvelimen tiedostojärjestelmästä. Dialogi estää sovelluksen suorittamisen, kunnes käyttäjä on tehnyt valinnan tai sulkenut dialogin.

<!-- INTRO_END -->

## Käytöt {#usages}

`FileChooserDialog` tarjoaa tavan valita tiedostoja tai hakemistoja tiedostojärjestelmästä, mikä mahdollistaa käyttäjien valita hakemistoja tietojen tallentamiseen tai suorittaa tiedosto-operaatioita.

<ComponentDemo 
path='/webforj/filechooserdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogBasicView.java'
height = '600px'
/>

## Tulos {#result}

`FileChooserDialog` palauttaa valitun tiedoston tai hakemiston merkkijonona. Jos käyttäjä sulkee dialogin ilman valintaa, tulos on `null`.

:::info
Palautettava merkkijono palautuu `show()`-metodista tai vastaavasta `OptionDialog`-metodista kuten alla on esitetty.
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

`FileChooserDialog` tukee erilaisia valintatiloja, jolloin voit mukauttaa valintatapaa erityistarpeidesi mukaan:

1. **FILES**: Sallii vain tiedostojen valinnan.
2. **DIRECTORIES**: Sallii vain hakemistojen valinnan.
3. **FILES_AND_DIRECTORIES**: Sallii sekä tiedostojen että hakemistojen valinnan.

## Alkuperäinen polku {#initial-path}

`FileChooserDialog` sallii sinun määrittää alkuperäisen polun, johon dialogi avautuu näyttämisen yhteydessä. Tämä voi tarjota käyttäjille lähtökohdan tiedoston valintaan.

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

Kun valintatila on `FILES`, `FileChooserDialog` sallii suodattimien asettamisen rajoittaaksesi lueteltavien tiedostojen tyyppejä. Voit määrittää suodattimia käyttämällä `setFilters(List<FileChooserFilter> filters)`-metodia.

<ComponentDemo 
path='/webforj/filechooserdialogfilters?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogFiltersView.java'
height = '600px'
/>

### Mukautetut suodattimet {#custom-filters}

Voit antaa käyttäjille mahdollisuuden lisätä mukautettuja suodattimia sallimalla mukautettujen suodattimien ominaisuuden käyttämällä `setCustomFilters(boolean customFilters)`-metodia. Mukautetut suodattimet tallennetaan oletusarvoisesti selaimen paikalliseen tallennustilaan ja palautetaan, kun dialogi näytetään jälleen.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Valitse tiedosto", "/home/user");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Kansainvälistäminen (i18n) {#internationalization-i18n}

Komponentin otsikot, kuvaukset, merkinnät ja viestit ovat täysin mukautettavissa käyttämällä `FileChooserI18n`-luokkaa. Tämä joustavuus mahdollistaa dialogin käyttöliittymän mukauttamisen erityisten lokalisointivaatimusten tai henkilökohtaisten mieltymysten mukaan.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Valitse tiedosto", "/Users/habof/bbx");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Valitse");
i18n.setCancel("Peruuta");
dialog.setI18n(i18n);
```

## Parhaat käytännöt {#best-practices}

1. **Selkeät ja ytimekkäät kehottajat**: Varmista, että kehotusviesti selkeästi selittää, mitä käyttäjän on määrä valita.
2. **Sopivat valintatilat**: Valitse valintatiloja, jotka vastaavat vaadittua käyttäjätoimintoa varmistaaksesi tarkat ja relevantit valinnat.
3. **Loogiset alkuperäiset polut**: Aseta alkuperäiset polut, jotka tarjoavat käyttäjille hyödyllisen lähtökohdan valinnalleen.
4. **Rajoita hakemiston navigointia**: Rajoita dialogi tiettyyn hakemistoon tarpeen mukaan estääksesi käyttäjiä navigoimasta luvattomiin alueisiin.
