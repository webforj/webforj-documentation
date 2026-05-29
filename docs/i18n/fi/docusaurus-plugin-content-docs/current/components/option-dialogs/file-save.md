---
title: File Save
sidebar_position: 15
_i18n_hash: 7cad72847c86a30f8ad6000a283a51c2
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileSaveDialog" top='true'/>

`FileSaveDialog` on modalin dialogi, joka on suunniteltu mahdollistamaan käyttäjien tallentaa tiedosto tiettyyn sijaintiin palvelimen tiedostojärjestelmässä. Dialogi estää sovelluksen suorittamisen, kunnes käyttäjä antaa tiedostonimen ja vahvistaa toiminnon tai peruu dialogin.

## Käytöt {#usages}

`FileSaveDialog` tarjoaa sujuvan tavan tallentaa tiedostoja tiedostojärjestelmään, tarjoamalla käyttäjän määriteltäviä vaihtoehtoja tiedostojen nimeämiseen ja olemassa olevien tiedostojen käsittelyyn.

<ComponentDemo
path='/webforj/filesavedialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogBasicView.java']}
height='800px'
/>

## Tulokset {#result}

`FileSaveDialog` palauttaa valitun polun merkkijonona. Jos käyttäjä peruu dialogin, tulos on `null`.

:::important Dialogin tarkoitus
Tämä dialogi ei itse asiassa tallenna tiedostoja, vaan palauttaa tiedostonimen, jonka käyttäjä on valinnut.
:::

:::info
Tuloksena oleva merkkijono palautetaan `show()`-metodista tai vastaavasta `OptionDialog`-metodista, kuten alla on esitetty.
:::

```java showLineNumbers
String result = OptionDialog.showFileSaveDialog(
    "Tallenna tiedostosi", "/home/user/documents", "report.xls");

if (result != null) {
  OptionDialog.showMessageDialog("Tallennettu tiedosto: " + path, "Valittu polku");
} else {
  OptionDialog.showMessageDialog("Ei polkua ole valittu", "Valittu polku",
      MessageDialog.MessageType.ERROR);
}
```

## Olemassaolon toiminta {#exists-action}

`FileSaveDialog` tarjoaa määriteltävän käyttäytymisen, kun tiedosto, jonka nimeksi on annettu, on jo olemassa:

* **ACCEPT_WITHOUT_ACTION**: Valinta hyväksytään ilman lisätoimia.
* **ERROR_DIALOGUE**: Käyttäjälle esitetään virhedialogi; valintaa ei sallita.
* **CONFIRMATION_DIALOGUE**: Käyttäjälle esitetään dialogi, jossa pyydetään vahvistusta. Tämä on oletusarvo.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Tallenna tiedostosi", "/home/user/documents", "report.xls");
dialog.setExistsAction(FileSaveDialog.ExistsAction.ERROR_DIALOGUE);
String result = dialog.show();
```

## Valintatila {#selection-mode}

`FileSaveDialog` tukee erilaisia valintamoodja, mikä mahdollistaa valintamenetelmän räätälöimisen tarpeidesi mukaan:

1. **TIEDOSTOT**: Salli vain tiedostojen valinta.
2. **KANSIOT**: Salli vain kansioiden valinta.
3. **TIEDOSTOT_JA_KANSIOT**: Salli sekä tiedostojen että kansioiden valinta.

## Alustava polku {#initial-path}

Määritä hakemisto, josta dialogi avataan käyttämällä alustavaa polkua. Tämä auttaa käyttäjiä aloittamaan loogisesta hakemistosta tallennustoimenpidettään varten.

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

Aseta oletustiedostonimi tallennustoimintoa varten ohjeistaaksesi käyttäjiä ja minimoidaksesi virheitä.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Tallenna tiedostosi");
dialog.setName("report.xls");
String result = dialog.show();
```

## Kansainvälisyys (i18n) {#internationalization-i18n}

Komponentin sisällä olevat otsikot, kuvaukset, etiketit ja viestit ovat täysin räätälöitävissä käyttämällä `FileSaveI18n`-luokkaa. Tämä varmistaa, että dialogi voidaan räätälöidä erilaisiin lokalisointi- tai personointivaatimuksiin.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Tallenna tiedostosi");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Valitse");
i18n.setCancel("Peruuta");
dialog.setI18n(i18n);
```

## Suodattimet {#filters}

`FileSaveDialog` mahdollistaa suodattimien asettamisen rajoittaakseen tallennettavien tiedostotyyppien määrää käyttämällä `setFilters(List<FileSaveFilter> filters)`-metodia.

<ComponentDemo
path='/webforj/filesavedialogfilters'
files={['src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogFiltersView.java']}
height='800px'
/>

### Mukautetut suodattimet {#custom-filters}

Voit ottaa käyttöön mukautetut suodattimet salliaksesi käyttäjien määrittää omat tiedostosuodattimensa käyttämällä `setCustomFilters(boolean customFilters)`-metodia. Suodattimet tallennetaan paikalliseen tallennukseen oletusarvoisesti ja palautetaan seuraavilla dialogikutsuilla.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Tallenna tiedostosi", "/home/user/documents");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Parhaat käytännöt {#best-practices}

* **Ennalta määritellyt tiedostonimet**: Tarjoa looginen oletustiedostonimi, kun se on mahdollista.
* **Vahvista Ylikirjoitukset**: Käytä `CONFIRMATION_DIALOGUE`-asetusta `ExistsAction`-toiminnossa estääksesi vahingossa tapahtuvat ylikirjoitukset.
* **Intuitiivinen Alustava Polku**: Aseta alustava polku, joka vastaa käyttäjien odotuksia.
* **Kansainvälisyys**: Mukauta dialogiteksti parantaaksesi käytettävyyttä kansainvälisille käyttäjille.
* **Tiedostotyyppisuodattimet**: Hyödynnä suodattimia rajoittaaksesi tiedostotyyppejä ja ohjataksesi käyttäjiä voimassa oleviin tiedostopäätteisiin.
