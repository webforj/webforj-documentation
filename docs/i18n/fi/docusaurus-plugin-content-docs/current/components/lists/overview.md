---
sidebar_position: 20
title: Lists
hide_giscus_comments: true
description: >-
  Manage shared list features across ChoiceBox, ComboBox, and ListBox, including
  ListItem objects, adding, removing, and selection APIs.
_i18n_hash: 1fa51afef9c5af46944d4d74a6b5ec61
---
<JavadocLink type="foundation" location="com/webforj/component/list/DwcList"/>

:::info
Tämä osa kuvaa kaikkien luettelo-komponenttien yleisiä ominaisuuksia, eikä se ole luokka, jota voidaan instansioida tai käyttää suoraan.
:::

Käyttöösi on saatavilla kolme erilaista luetteloa sovelluksissasi: [`ListBox`](listbox), [`ChoiceBox`](choicebox) ja [`ComboBox`](combobox). Nämä komponentit Display a list of key-value items, and provide methods to add, remove, select, and manage the items within the list.

Tällä sivulla on kuvattu kaikkien luettelo-komponenttien jaettavat ominaisuudet ja käyttäytyminen, kun taas kunkin tarkat tiedot käsitellään niiden omilla sivuilla.

## Käyttäminen `ListItem` {#using-listitem}

Luettelo-komponentit koostuvat <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> -objekteista, jotka edustavat yksittäisiä kohteita luettelossa. Jokaisella <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> -objektilla on ainutlaatuinen avain ja näytettävä teksti. Tärkeitä ominaisuuksia <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> -luokassa ovat:

- <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> kapseloi ainutlaatuisen avaimen `Object` ja tekstin `String`, joka näytetään luettelo-komponentissa.
- Voit rakentaa <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> -objektin tarjoamalla avaimen ja tekstin tai määrittelemällä vain tekstin, jolloin satunnainen avain generoidaan.

## `ListItem`-objektien hallinta API:n avulla {#managing-listitem-objects-with-the-api}

Eri luettelo-komponentit tarjoavat useita menetelmiä kohteiden luettelon hallitsemiseksi ja johdonmukaisen tilan ylläpitämiseksi luettelon ja asiakkaan välillä. Näiden menetelmien avulla voit tehokkaasti hallita luettelon kohteita. API mahdollistaa vuorovaikutuksen ja manipulaation luettelon kanssa, jotta se täyttää sovelluksesi vaatimukset.

### Kohteiden lisääminen {#adding-items}

- **Kohteen lisääminen**:

   - Voit lisätä `ListItem`-kohteen luetteloon käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(com.webforj.component.list.ListItem)' code="true">add(ListItem item)</JavadocLink> -menetelmää.
   - Voit myös lisätä uuden `ListItem`-kohteen määrittelemällä avaimen ja tekstin käyttäen <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.Object,java.lang.String)' code="true">add(Object key, String text)</JavadocLink> tai <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.String)' code="true">add(String text)</JavadocLink> -menetelmää.

- **Kohteen lisääminen tiettyyn indeksiin:**

   - Voit lisätä kohteen tiettyyn indeksiin käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,com.webforj.component.list.ListItem)' code="true">insert(int index, ListItem item)</JavadocLink> -menetelmää.
   - Voit lisätä kohteen, jolla on avain ja teksti, käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.Object,java.lang.String)' code="true">insert(int index, Object key, String text)</JavadocLink> tai <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.String)' code="true">insert(int index, String text)</JavadocLink> -menetelmää.

- **Useiden kohteiden lisääminen:**

   - Voit lisätä useita kohteita määritettyyn indeksiin käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.util.List)' code="true">insert(int index, List< ListItem > items)</JavadocLink> -menetelmää.

:::tip
Suorituskyvyn optimoimiseksi on tehokkaampaa luoda ensin `List` <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> -objekteista sen sijaan, että laukaistaan palvelin-asiakasviestiä joka kerta, kun käytät `add()`-menetelmää. Kun sinulla on tämä lista, voit lisätä ne kaikki kerralla käyttämällä `insert(int index, List<ListItem> items)` -menetelmää. Tämä lähestymistapa vähentää palvelin-asiakasviestintää ja parantaa kokonaistehokkuutta. Yksityiskohtaisia ohjeita tästä ja muista parhaista käytännöistä webforJ-arkkitehtuurissa löytyy [Client/Server Interaction](/docs/architecture/client-server).
:::

### Kohteiden poistaminen {#removing-items}

- **Kohteen poistaminen:**

   - Voit poistaa kohteen luettelosta käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(int)' code="true">remove(int index)</JavadocLink> tai <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(java.lang.Object)' code="true">remove(Object key)</JavadocLink> -menetelmää.

- **Kaikkien kohteiden poistaminen:**
   - Voit poistaa kaikki kohteet luettelosta käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#removeAll()' code="true">removeAll()</JavadocLink>.

### Kohteiden valitseminen {#selecting-items}

Kaikki luettelotyypit toteuttavat `SelectableList`-rajapinnan. Tämä rajapinta mahdollistaa useita eri tapoja valita nykyinen `ListItem`.

#### Tietyn `ListItem` avulla {#with-a-given-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#select(com.webforj.component.list.ListItem)' code="true">select(ListItem item)</JavadocLink> ottaa parametriksi `ListItem`-objektin valitsemiseksi.

```java {4}
List demoList = new List();
ListItem demoItem = new ListItem("demo","Demo Item");
demoList.add(demoItem);
demoList.select(demoItem);
```

#### Tietyn `ListItem` avaimen avulla {#with-a-given-key-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectKey(java.lang.Object)' code="true">selectKey(Object key)</JavadocLink> ottaa parametriksi avaimen `ListItem`-objektiin valitsemiseksi.

```java {3}
List demoList = new List();
demoList.add("demo","Demo Item");
demoList.selectKey("demo");
```

#### Tietyn `ListItem` indeksin avulla {#with-a-given-index-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectIndex(int)' code="true">selectIndex(int index)</JavadocLink> ottaa parametrina indeksin `ListItem`-objektiin valitsemiseksi.

```java {3}
List demoList = new List();
demoList.add("demo","Demo Item");
demoList.selectKey(0);
```

### Muita luettelo-operaatioita {#other-list-operations}

- **Kohteiden hakeminen ja päivittäminen:**

   - Voit käyttää kohteita avaimen tai indeksin avulla, käyttäen <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByKey(java.lang.Object)' code="true">getByKey(Object key)</JavadocLink> tai <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByIndex(int)' code="true">getByIndex(int index)</JavadocLink>.
   - Voit päivittää kohteen tekstiä käyttäen <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" suffix='#setText(java.lang.String)' code="true">setText(String text)</JavadocLink> -metodia <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> -luokassa.

- **Tietojen hakeminen luettelosta:**
   - Voit saada luettelon koon käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#size()' code="true">size()</JavadocLink> -metodia.
   - Tarkistaaksesi onko luettelo tyhjää, käytä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#isEmpty()' code="true">isEmpty()</JavadocLink> -metodia.

### Luetteloiden iteroiminen {#iterating-over-lists}

Kaikki luettelo-komponentit toteuttavat Java [`Iteratable`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Iterable.html) -rajapinnan, joka tarjoaa tehokkaan ja intuitiivisen tavan iteroida luettelon sisältöä. Tämän rajapinnan avulla voit helposti käydä läpi jokaisen `ListItem`-kohteen, mikä tekee yksittäisten kohteiden käsittelemisestä ja muuttamisesta vaivatonta. `Iterable`-rajapinta on Java-kielen standardiominaisuus, mikä varmistaa, että koodisi on tuttua ja ylläpidettävää kaikille Java-kehittäjille.

Alla olevassa koodiesimerkissä on esitetty kaksi helppoa tapaa iteroida läpi luetteloa:

```java
list.forEach(item -> {
   item.setText("Muokattu: " + item.getText());
});

for (ListItem item : list) {
   item.setText("Muokattu2: " + item.getText());
}
```

## Yhteiset luettelo-ominaisuudet {#shared-list-properties}

### Otsikko {#label}

Kaikille luettelo-komponenteille voidaan määrittää otsikko, joka on kuvaileva teksti tai nimi, joka liittyy komponenttiin. Otsikot tarjoavat lyhyen selityksen tai kehotuksen, joka auttaa käyttäjiä ymmärtämään kyseisen luettelon tarkoitusta tai odotettua valintaa. Olemalla tärkeitä käytettävyyden kannalta, luettelo-otsikot näyttelevät myös keskeistä roolia saavutettavuudessa, mahdollistamalla ruudunlukijoiden ja apuvälineiden antavan tarkkaa tietoa ja helpottavan näppäimistön navigointia.

### Aputeksti {#helper-text}

Jokainen Luettelo-komponentti voi näyttää aputekstiä luettelon alla käyttäen `setHelperText()` -metodia. Tämä aputeksti tarjoaa lisäkontekstia tai selityksiä käytettävissä olevista vaihtoehdoista, varmistaen, että käyttäjillä on tarvittavat tiedot, jotta he voivat tehdä tietoon perustuvia valintoja.

### Vaakatasaus {#horizontal-alignment}

Kaikki luettelo-komponentit toteuttavat <JavadocLink type="foundation" location="com/webforj/concern/HasHorizontalAlignment" code='true'>HasHorizontalAlignment</JavadocLink> -rajapinnan, joka antaa sinulle hallinnan siitä, miten teksti ja sisältö on kohdistettu komponentin sisällä.

Käytä `setHorizontalAlignment()` -metodia asettaaksesi tasaus:

- `HorizontalAlignment.LEFT` (oletus)
- `HorizontalAlignment.MIDDLE`
- `HorizontalAlignment.RIGHT`

```java
ListBox<String> listBox = new ListBox<>();
listBox.setHorizontalAlignment(HorizontalAlignment.LEFT);
```

Hankkiaksesi nykyisen tasaamisen:
```java
HorizontalAlignment alignment = listBox.getHorizontalAlignment();
```

### Laajuus {#expanses}

Kaikki webforJ:n luettelo-komponentit toteuttavat myös <JavadocLink type="foundation" location="com/webforj/concern/HasExpanse" code='true'>HasExpanse</JavadocLink> -rajapinnan, joka mahdollistaa komponentin koon ja visuaalisen painon säätämisen. Tämä on hyödyllistä, kun mukautat komponentin erilaisiin käyttöliittymäkonteksteihin, kuten lomakkeisiin, dialogeihin, sivupalkkeihin jne.

Käytä `setExpanse()` -metodia asettaaksesi laajuustason. Vaihtoehtoina ovat:

- `Expanse.NONE`
- `Expanse.XSMALL`
- `Expanse.SMALL`
- `Expanse.MEDIUM` (oletus)
- `Expanse.LARGE`
- `Expanse.XLARGE`

```java
ListBox<String> listBox = new ListBox<>();
listBox.setExpanse(Expanse.LARGE);
```

Voit tarvittaessa hankkia nykyisen asetuksen:
```java
Expanse current = listBox.getExpanse();
```

## Aiheet {#topics}

<DocCardList className="topics-section" />
