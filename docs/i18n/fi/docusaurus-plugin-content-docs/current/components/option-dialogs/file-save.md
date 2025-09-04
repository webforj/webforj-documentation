---
sidebar_position: 15
title: File Save
_i18n_hash: 9f5ecfb61386cfa8c4eb3c31305b1838
---
# Tallenna tiedosto -valintaikkuna

<DocChip chip='shadow' />
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileSaveDialog" top='true'/>

`FileSaveDialog` on modaalinen valintaikkuna, joka on suunniteltu mahdollistamaan käyttäjien tiedoston tallentaminen tiettyyn sijaintiin palvelimen tiedostojärjestelmässä. Ikkuna estää sovelluksen suorituksen, kunnes käyttäjä antaa tiedostonimen ja vahvistaa toiminnon tai peruuttaa ikkunan.

```java
OptionDialog.showFileSaveDialog("Tallenna tiedostosi");
```

## Käyttöesimerkit {#usages}

`FileSaveDialog` tarjoaa virtaviivaisen menetelmän tiedostojen tallentamiseen tiedostojärjestelmään, tarjoten käyttäjän määriteltäviä vaihtoehtoja tiedoston nimeämiseen ja olemassa olevien tiedostojen käsittelyyn.

<ComponentDemo 
path='/webforj/filesavedialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogBasicView.java'
height = '800px'
/>

## Tulokset {#result}

`FileSaveDialog` palauttaa valitun polun merkkijonona. Jos käyttäjä peruuttaa valintaikkunan, tulos on `null`.

:::important Valintaikkunan tarkoitus
Tämä valintaikkuna ei oikeastaan tallenna tiedostoja, vaan palauttaa sen tiedostonimen, jonka käyttäjä on valinnut.
:::

:::info
Tuloksena oleva merkkijono palautuu `show()`-menetelmällä tai vastaavalla `OptionDialog`-menetelmällä kuten alla osoitetaan.
:::

```java showLineNumbers
String result = OptionDialog.showFileSaveDialog(
    "Tallenna tiedostosi", "/home/user/documents", "raportti.xls");

if (result != null) {
  OptionDialog.showMessageDialog("Tallennettu tiedosto: " + path, "Valittu polku");
} else {
  OptionDialog.showMessageDialog("Ei polkua ole valittu", "Valittu polku",
      MessageDialog.MessageType.ERROR);
}
```

## Olemassa olevat toiminnot {#exists-action}

`FileSaveDialog` tarjoaa säädettävän käyttäytymisen, kun tiedosto, jolla on määritetty nimi, on jo olemassa:

* **ACCEPT_WITHOUT_ACTION**: Valinta hyväksytään ilman lisätoimia käyttäjältä.
* **ERROR_DIALOGUE**: Käyttäjälle näytetään virheilmoitusikkuna; valintaa ei sallita.
* **CONFIRMATION_DIALOGUE**: Käyttäjälle näytetään ikkuna, joka pyytää vahvistusta. Tämä on oletus.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Tallenna tiedostosi", "/home/user/documents", "raportti.xls");
dialog.setExistsAction(FileSaveDialog.ExistsAction.ERROR_DIALOGUE);
String result = dialog.show();
```

## Valintatila {#selection-mode}

`FileSaveDialog` tukee erilaisia valintatiloja, jolloin voit mukauttaa valintatavan tarpeidesi mukaan:

1. **FILES**: Mahdollistaa vain tiedostojen valitsemisen.
2. **DIRECTORIES**: Mahdollistaa vain kansioiden valitsemisen.
3. **FILES_AND_DIRECTORIES**: Mahdollistaa sekä tiedostojen että kansioiden valitsemisen.

## Alustava polku {#initial-path}

Määritä hakemisto, jossa valintaikkuna avataan käyttämällä alustavaa polkua. Tämä auttaa käyttäjiä aloittamaan loogisessa hakemistossa tallennustoimintoa varten.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Tallenna tiedostosi", "/home/user/documents", "raportti.xls");
String result = dialog.show();
```

## Rajoitus {#restriction}

Voit rajoittaa valintaikkunaa tiettyyn hakemistoon, estäen käyttäjiä navigoimasta sen ulkopuolelle käyttämällä `setRestricted(boolean restricted)` -menetelmää.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Tallenna tiedostosi", "/home/user/documents", "raportti.xls");
dialog.setRestricted(true);
dialog.show();
```

## Tiedostonimi {#filename}

Aseta oletustiedostonimi tallennusoperaatiota varten ohjataksesi käyttäjiä ja minimoidaksesi virheitä.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Tallenna tiedostosi");
dialog.setName("raportti.xls");
String result = dialog.show();
```

## Kansainvälistäminen (i18n) {#internationalization-i18n}

Komponentin sisällä olevat otsikot, kuvaukset, etiketit ja viestit ovat täysin mukautettavissa käyttämällä `FileSaveI18n`-luokkaa. Tämä varmistaa, että valintaikkuna voidaan muokata erilaisten lokalisaatio- tai personointivaatimusten mukaan.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Tallenna tiedostosi");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Valitse");
i18n.setCancel("Peruuta");
dialog.setI18n(i18n);
```

## Suodattimet {#filters}

`FileSaveDialog` mahdollistaa suodattimien asettamisen, jotta voidaan rajoittaa tallennettavien tiedostotyyppien valikoimaa käyttämällä `setFilters(List<FileSaveFilter> filters)` -menetelmää.

<ComponentDemo 
path='/webforj/filesavedialogfilters?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogFiltersView.java'
height = '800px'
/>

### Mukautetut suodattimet {#custom-filters}

Voit mahdollistaa mukautetut suodattimet, jotta käyttäjät voivat määrittää omat tiedostosarjansa käyttämällä `setCustomFilters(boolean customFilters)` -menetelmää. Suodattimet tallennetaan paikalliseen tallennustilaan oletuksena ja palautetaan seuraavilla valintaikkunan avauksilla.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Tallenna tiedostosi", "/home/user/documents");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Parhaat käytännöt {#best-practices}

* **Ennakoidut tiedostonimet**: Tarjoa looginen oletustiedostonimi, missä se on soveltuva.
* **Vahvista ylittämiset**: Käytä `CONFIRMATION_DIALOGUE`-valintaa `ExistsAction`:in osalta estämään tahattomia ylityksiä.
* **Intuitiivinen alustava polku**: Aseta alustava polku, joka vastaa käyttäjän odotuksia.
* **Kansainvälistäminen**: Mukauta valintaikkunan tekstiä käytettävyyden parantamiseksi kansainvälisille käyttäjille.
* **Tiedostotyyppisuodattimet**: Hyödynnä suodattimia rajoittamaan tiedostotyyppejä ja ohjaamaan käyttäjiä kohti kelvollisia tiedostopäätteen.
