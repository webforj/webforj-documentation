---
sidebar_position: 15
title: File Save
_i18n_hash: 477f92765ae539fd69106297baa9a0da
---
# Tallenna tiedosto -valintaikkuna

<DocChip chip='shadow' />
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileSaveDialog" top='true'/>

`FileSaveDialog` on modaalinen valintaikkuna, joka on suunniteltu mahdollistamaan käyttäjille tiedoston tallentamisen määrättyyn sijaintiin palvelimen tiedostojärjestelmässä. Ikkuna estää sovelluksen suorittamisen, kunnes käyttäjä syöttää tiedostonimen ja vahvistaa toiminnon tai peruuttaa dialogin.

```java
OptionDialog.showFileSaveDialog("Tallenna tiedostosi");
```

## Käyttötavat {#usages}

`FileSaveDialog` tarjoaa yksinkertaistetun tavan tallentaa tiedostoja tiedostojärjestelmään, tarjoten käyttäjän määriteltäviä vaihtoehtoja tiedoston nimeämiseen ja olemassa olevien tiedostojen käsittelyyn.

<ComponentDemo 
path='/webforj/filesavedialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogBasicView.java'
height = '800px'
/>

## Tulokset {#result}

`FileSaveDialog` palauttaa valitun polun merkkijonona. Jos käyttäjä peruuttaa dialogin, tulos on `null`.

:::important Dialogin tarkoitus
Tämä dialogi ei oikeasti tallenna tiedostoja, vaan palauttaa käyttäjän valitsemat tiedostonimen.
:::

:::info
Tuloksena oleva merkkijono palautuu `show()`-metodista tai vastaavasta `OptionDialog`-metodista, kuten alla demonstroidaan.
:::

```java showLineNumbers
String result = OptionDialog.showFileSaveDialog(
    "Tallenna tiedostosi", "/home/user/documents", "report.xls");

if (result != null) {
  OptionDialog.showMessageDialog("Tallennettu tiedosto osoitteeseen: " + path, "Valittu polku");
} else {
  OptionDialog.showMessageDialog("Ei polkua valittu", "Valittu polku",
      MessageDialog.MessageType.ERROR);
}
```

## Olemassaolon toiminta {#exists-action}

`FileSaveDialog` tarjoaa konfiguroitavaa käyttäytymistä, kun tiedosto, jonka nimellä on jo olemassa:

* **ACCEPT_WITHOUT_ACTION**: Valinta hyväksytään ilman lisätoimenpiteitä käyttäjältä.
* **ERROR_DIALOGUE**: Käyttäjälle esitetään virhedialogi; valintaa ei sallita.
* **CONFIRMATION_DIALOGUE**: Käyttäjälle esitetään dialogi, joka pyytää vahvistusta. Tämä on oletus.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Tallenna tiedostosi", "/home/user/documents", "report.xls");
dialog.setExistsAction(FileSaveDialog.ExistsAction.ERROR_DIALOGUE);
String result = dialog.show();
```

## Valintatila {#selection-mode}

`FileSaveDialog` tukee erilaisia valintatiloja, jotta voit räätälöidä valintamenetelmän tarpeidesi mukaan:

1. **TIEDOSTOT**: Sallii vain tiedostojen valinnan.
2. **KANSIO**: Sallii vain kansioiden valinnan.
3. **TIEDOSTOT_JA_KANSIOT**: Sallii sekä tiedostojen että kansioiden valinnan.

## Alustava polku {#initial-path}

Määritä hakemisto, josta dialogi avautuu, käyttämällä alustavaa polkua. Tämä auttaa käyttäjiä aloittamaan järkevistä hakemistoista tallennustoimintaansa varten.

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

Aseta oletustiedostonimi tallennustoimintoa varten, jotta käyttäjät saavat ohjeita ja virheiden mahdollisuudet minimoidaan.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Tallenna tiedostosi");
dialog.setName("report.xls");
String result = dialog.show();
```

## Kansainvälisyys (i18n) {#internationalization-i18n}

Komponentin otsikot, kuvastot, etikettit ja viestit ovat täysin muokattavissa käyttämällä `FileSaveI18n`-luokkaa. Tämä varmistaa, että dialogi voidaan räätälöidä erilaisiin paikallistamis- tai henkilökohtaisiin vaatimuksiin.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Tallenna tiedostosi");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Valitse");
i18n.setCancel("Peruuta");
dialog.setI18n(i18n);
```

## Suodattimet {#filters}

`FileSaveDialog` sallii suodattimien asettamisen rajatakseen tallennettavien tiedostotyyppien määrää `setFilters(List<FileSaveFilter> filters)`-metodin avulla.

<ComponentDemo 
path='/webforj/filesavedialogfilters?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogFiltersView.java'
height = '800px'
/>

### Räätälöidyt suodattimet {#custom-filters}

Voit mahdollistaa räätälöidyt suodattimet, jotta käyttäjät voivat määrittää omat tiedostosovittimensa käyttämällä `setCustomFilters(boolean customFilters)`-metodia. Suodattimet tallennetaan paikalliseen tallennukseen oletuksena ja palautetaan seuraavilla dialogin kutsuilla.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Tallenna tiedostosi", "/home/user/documents");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Parhaat käytännöt {#best-practices}

* **Ennaltamäärätyt tiedostonimet**: Tarjoa järkevä oletustiedostonimi tarvittaessa.
* **Vahvista ylikirjoitukset**: Käytä `CONFIRMATION_DIALOGUE`-valintaa `ExistsAction`-toiminnolle estääksesi tahattomat ylikirjoitukset.
* **Ymmärrettävä alustava polku**: Aseta alustava polku, joka vastaa käyttäjien odotuksia.
* **Kansainvälisyys**: Mukauta dialogitekstiä parantaaksesi käytettävyyttä kansainvälisille yleisöille.
* **Tiedostotyypin suodattimet**: Hyödynnä suodattimia rajataksesi tiedostotyyppejä ja ohjataksesi käyttäjiä kohti kelvollisia tiedostopäätteitä.
