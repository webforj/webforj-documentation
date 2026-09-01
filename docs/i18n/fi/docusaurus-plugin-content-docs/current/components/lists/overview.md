---
sidebar_position: 20
title: Lists
hide_giscus_comments: true
sidebar_class_name: new-content
description: >-
  Manage shared list features across ChoiceBox, ComboBox, and ListBox, including
  ListItem objects, adding, removing, and selection APIs.
_i18n_hash: f75147986adfbf756ebf603caa663134
---
<JavadocLink type="foundation" location="com/webforj/component/list/DwcList"/>

:::info
Tässä osiossa kuvataan kaikkien listakomponenttien yhteiset ominaisuudet, eikä tämä ole luokka, jota voidaan instansioida tai käyttää suoraan.
:::

Sovelluksissasi on kolme tyyppiä listoja käytettävissä: [`ListBox`](listbox), [`ChoiceBox`](choicebox) ja [`ComboBox`](combobox). Nämä komponentit näyttävät kaikki joukon avain-arvo -kohteita ja tarjoavat menetelmiä kohteiden lisäämiseen, poistamiseen, valitsemiseen ja hallintaan listassa.

Tällä sivulla esitellään kaikkien listakomponenttien yhteiset ominaisuudet ja toiminta, kun taas kunkin erityiset tiedot käsitellään niiden omilla sivuillaan.

## Käyttäminen `ListItem` {#using-listitem}

Listakomponentit koostuvat <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> -objekteista, jotka edustavat yksittäisiä kohteita listassa. Jokaisella <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>:lla on ainutlaatuinen avain ja näyttöteksti. Tärkeitä ominaisuuksia <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> -luokassa ovat:

- <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> kapseloi ainutlaatuisen avaimen `Object` ja tekstin `String`, joka näytetään listakomponentissa.
- Voit rakentaa <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>:n antamalla avaimen ja tekstin tai määrittämällä vain tekstin, jolloin satunnainen avain luodaan.

## `ListItem`-objektien hallinta API:n avulla {#managing-listitem-objects-with-the-api}

Erilaiset listakomponentit tarjoavat useita menetelmiä kohdelistan hallitsemiseksi ja tasapainoisen tilan ylläpitämiseksi listan ja asiakkaan välillä. Käyttämällä näitä menetelmiä voit tehokkaasti hallita kohteita listassa. API mahdollistaa sinun vuorovaikuttaa ja manipuloida listaa sovelluksesi vaatimusten mukaisesti.

### Kohteiden lisääminen {#adding-items}

- **Kohteen lisääminen**:

   - Voit lisätä `ListItem` -kohteen listaan käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(com.webforj.component.list.ListItem)' code="true">add(ListItem item)</JavadocLink> -menetelmää.
   - Voit myös lisätä uuden `ListItem` määrittämällä avaimen ja tekstin käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.Object,java.lang.String)' code="true">add(Object key, String text)</JavadocLink> tai <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.String)' code="true">add(String text)</JavadocLink> -menetelmää.

- **Kohteen lisääminen tiettyyn indeksiin:**

   - Voit lisätä kohteen tiettyyn indeksiin käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,com.webforj.component.list.ListItem)' code="true">insert(int index, ListItem item)</JavadocLink> -menetelmää.
   - Voit syöttää kohteen avaimen ja tekstin käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.Object,java.lang.String)' code="true">insert(int index, Object key, String text)</JavadocLink> tai <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.String)' code="true">insert(int index, String text)</JavadocLink> -menetelmää.

- **Useiden kohteiden lisääminen:**

   - Voit lisätä useita kohteita määritettyyn indeksiin käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.util.List)' code="true">insert(int index, List< ListItem > items)</JavadocLink> -menetelmää.

:::tip
Suorituskyvyn optimoimiseksi, sen sijaan että laukaistaisit palvelin-asiakasviestin joka kerta käyttäessäsi `add()`-menetelmää, on tehokkaampaa luoda ensin List <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> -objekteista. Kun sinulla on tämä lista, voit lisätä ne kaikki kerralla käyttäen `insert(int index, List<ListItem> items)` -menetelmää. Tämä lähestymistapa vähentää palvelin-asiakasviestintää, parantaen kokonaiseffektiivisyyttä. Yksityiskohtaiset ohjeet tästä ja muista parhaista käytännöistä webforJ-arkkitehtuurissa löydät [Asiakkaan/Palvelimen vuorovaikutus](/docs/architecture/client-server).
:::

### Kohteiden poistaminen {#removing-items}

- **Kohteen poistaminen:**

   - Poistaaksesi kohteen listasta, käytä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(int)' code="true">remove(int index)</JavadocLink> tai <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(java.lang.Object)' code="true">remove(Object key)</JavadocLink> -menetelmää.

- **Kaikkien kohteiden poistaminen:**
   - Voit poistaa kaikki kohteet listasta käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#removeAll()' code="true">removeAll()</JavadocLink>.

### Kohteiden valitseminen {#selecting-items}

Kaikki listatyypit toteuttavat `SelectableList`-rajapinnan. Tämä rajapinta mahdollistaa useita erilaisia tapoja valita nykyinen `ListItem`.

#### Tietyn `ListItem`:n kanssa {#with-a-given-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#select(com.webforj.component.list.ListItem)' code="true">select(ListItem item)</JavadocLink> ottaa `ListItem`:n parametrina valitsemiseksi.

```java {4}
List demoList = new List();
ListItem demoItem = new ListItem("demo","Demo Item");
demoList.add(demoItem);
demoList.select(demoItem);
```

#### Tietyn `ListItem`:n avaimen kanssa {#with-a-given-key-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectKey(java.lang.Object)' code="true">selectKey(Object key)</JavadocLink> ottaa avaimen `ListItem`:ille parametrina valitsemiseksi.

```java {3}
List demoList = new List();
demoList.add("demo","Demo Item");
demoList.selectKey("demo");
```

#### Tietyn `ListItem`:n indeksin kanssa {#with-a-given-index-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectIndex(int)' code="true">selectIndex(int index)</JavadocLink> ottaa indeksin `ListItem`:lle parametrina valitsemiseksi.

```java {3}
List demoList = new List();
demoList.add("demo","Demo Item");
demoList.selectKey(0);
```

### Muut listatoiminnot {#other-list-operations}

- **Kohteiden käyttämiseen ja päivittämiseen:**

   - Käyttääksesi kohteita avaimen tai indeksin avulla, käytä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByKey(java.lang.Object)' code="true">getByKey(Object key)</JavadocLink> tai <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByIndex(int)' code="true">getByIndex(int index)</JavadocLink>.
   - Voit päivittää kohteen tekstiä käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" suffix='#setText(java.lang.String)' code="true">setText(String text)</JavadocLink> -menetelmää <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> -luokassa.

- **Tietojen noutaminen listasta:**
   - Voit saada listan koon käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#size()' code="true">size()</JavadocLink> -menetelmää.
   - Tarkistaaksesi, onko lista tyhjennyt, käytä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#isEmpty()' code="true">isEmpty()</JavadocLink> -menetelmää.

### Listojen läpikäyminen {#iterating-over-lists}

Kaikki listakomponentit toteuttavat Java [`Iteratable`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Iterable.html) -rajapinnan, joka tarjoaa tehokkaan ja intuitiivisen tavan käydä läpi listan sisältöä. Tämän rajapinnan avulla voit helposti iteroida jokaisen `ListItem`:n läpi, mikä tekee yksinkertaiseksi käyttää, muokata tai suorittaa toimintoja jokaiselle kohteelle pienellä vaivalla. `Iterable`-rajapinta on standardimalli Java-kielellä, mikä varmistaa, että koodisi on tuttua ja ylläpidettävää mille tahansa Java-kehittäjälle.

Alla olevassa koodinpätkässä esitetään kaksi helppoa tapaa iteroida listan läpi:

```java
list.forEach(item -> {
   item.setText("Muokattu: " + item.getText());
});

for (ListItem item : list) {
   item.setText("Muokattu2: " + item.getText());
}
```

## Haku <DocChip chip='since' label='26.02' /> {#searching}

Kaikilla listakomponenteilla on integroitu hakukenttä, joka suodattaa kohteet niiden tekstin perusteella. Kenttä on oletuksena poissa käytöstä. Käytä `getSearch()` -menetelmää päästäksesi hakukonfigurointiin, sitten `setFieldVisible(true)` näyttääksesi kentän komponentin listan yläosassa.

```java
ComboBox comboBox = new ComboBox("Hedelmät");
comboBox.insert("Omena", "Banaani", "Kirsikka", "Aprikos", "Ananas");

comboBox.getSearch()
  .setFieldVisible(true)
  .setPlaceholder("Hae hedelmiä")
  .setEmptyMessage("Ei hedelmiä löytynyt");
```

<ComponentDemo
path='/webforj/listsearch'
files={['src/main/java/com/webforj/samples/views/lists/listbox/ListSearchView.java']}
height='450px'
/>

Suodatus piilottaa vain ne kohteet, jotka eivät täsmää. Kohteiden indeksit ja nykyinen valinta pysyvät koskemattomina, joten `getSelectedIndex()` viittaa edelleen täydelliseen listaan, ei nykyisin näkyviin kohteisiin.

Hakukenttä voidaan piilottaa jälleen käyttämällä `setFieldVisible(false)`.

### Kentän konfigurointi {#configuring-the-field}

- `setPlaceholder()` asettaa hakukentän paikkamerkkitekstin. Oletus on `Hae`.

-  `setEmptyMessage()` asettaa viestin, joka näytetään, kun haku ei palauta tuloksia. Oletus on `Ei näyttöön osoitettavaa tietoa`.

Jokaiselle asetukselle on vastaava getter: `isFieldVisible()`, `getPlaceholder()`, `getEmptyMessage()`, ja `getTerm()`.

### Suodatus koodista {#filtering-from-code}

`setTerm()` asettaa haun termin ja suodattaa listan. Se toimii riippumatta siitä, onko kenttä näkyvissä vai ei, joten lista voidaan suodattaa näyttämättä hakukäyttöliittymää.

```java
listBox.getSearch().setTerm("omena");
```

:::warning `getTerm()` ja hakukenttä
Kirjoittaminen hakukenttään ei kirjoita termiä takaisin konfigurointiin. `getTerm()` palauttaa viimeisen arvon, joka on annettu `setTerm()`-menetelmälle, ei sitä, mitä käyttäjä on kirjoittanut.
:::

## Jaetut listan ominaisuudet {#shared-list-properties}

### Otsikko {#label}

Kaikille listakomponenteille voidaan määrittää otsikko, joka on kuvaava teksti tai otsikko, joka liittyy komponenttiin. Otsikot tarjoavat lyhyen selityksen tai kehotuksen auttaa käyttäjiä ymmärtämään kyseisen listan tarkoituksen tai odotettavan valinnan. Käytettävän helppouden lisäksi listan otsikoilla on tärkeä rooli myös esteettömyydessä, mahdollistaen ruudunlukijoiden ja apuvälineiden tarjoavan tarkkaa tietoa ja helpottamaan näppäimistön navigointia.

### Aputeksti {#helper-text}

Jokainen List-komponentti voi näyttää aputekstiä listan alla käyttämällä `setHelperText()`-menetelmää. Tämä aputeksti tarjoaa lisäkontekstia tai selityksiä saatavilla olevista vaihtoehdoista, varmistaen, että käyttäjillä on tarvittava tieto tietoisien valintojen tekemiseen.

### Vaakasuuntainen kohdistus {#horizontal-alignment}

Kaikki listakomponentit toteuttavat <JavadocLink type="foundation" location="com/webforj/concern/HasHorizontalAlignment" code='true'>HasHorizontalAlignment</JavadocLink>-rajapinnan, mikä antaa sinulle hallinnan tekstin ja sisällön kohdistuksessa komponentin sisällä.

Käytä `setHorizontalAlignment()`-menetelmää asettaaksesi kohdistuksen:

- `HorizontalAlignment.LEFT` (oletus)
- `HorizontalAlignment.MIDDLE`
- `HorizontalAlignment.RIGHT`

```java
ListBox<String> listBox = new ListBox<>();
listBox.setHorizontalAlignment(HorizontalAlignment.LEFT);
```

Hanki nykyinen kohdistus:
```java
HorizontalAlignment alignment = listBox.getHorizontalAlignment();
```

### Laajennukset {#expanses}

Kaikki webforJ:n listakomponentit toteuttavat myös <JavadocLink type="foundation" location="com/webforj/concern/HasExpanse" code='true'>HasExpanse</JavadocLink> -rajapinnan, mikä mahdollistaa komponentin kokonaiskoon ja visuaalisen painon säätämisen. Tämä on hyödyllistä komponentin mukauttamiseksi erilaisiin käyttöliittymäkonteksteihin, kuten lomakkeisiin, dialogeihin, sivupalkkeihin jne.

Käytä `setExpanse()`-menetelmää asettaaksesi laajennustason. Vaihtoehtoina ovat:

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

Voit noutaa nykyisen asetuksen käyttäen:
```java
Expanse current = listBox.getExpanse();
```

## Aiheet {#topics}

<DocCardList className="topics-section" />
