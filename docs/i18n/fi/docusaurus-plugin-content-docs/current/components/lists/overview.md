---
sidebar_position: 20
title: Lists
hide_giscus_comments: true
sidebar_class_name: new-content
description: >-
  Manage shared list features across ChoiceBox, ComboBox, and ListBox, including
  ListItem objects, adding, removing, and selection APIs.
_i18n_hash: 79a006a2c6a0435414d931e5bfb161bd
---
<JavadocLink type="foundation" location="com/webforj/component/list/DwcList"/>

:::info
Tässä osiossa kuvataan kaikkien listakomponenttien yhteisiä ominaisuuksia, eikä se ole luokka, jota voidaan instantiate tai käyttää suoraan.
:::

Sovelluksesi käytettävissä on kolme erilaista listatyyppiä: [`ListBox`](listbox), [`ChoiceBox`](choicebox) ja [`ComboBox`](combobox). Nämä komponentit näyttävät kaikki avain-arvo -elementtien listan ja tarjoavat menetelmiä elementtien lisäämiseen, poistamiseen, valintaan ja hallintaan.

Tällä sivulla käsitellään kaikkien listakomponenttien jaettuja ominaisuuksia ja käyttäytymistä, kun taas kukin erityinen yksityiskohta käsitellään omilla sivuillaan.

## Käyttäminen `ListItem` {#using-listitem}

Listakomponentit koostuvat <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> -objekteista, jotka edustavat yksittäisiä elementtejä listassa. Jokaisella <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>:lla on ainutlaatuinen avain ja näyttöteksti. Tärkeitä ominaisuuksia <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> -luokassa ovat:

- <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> kapseloi ainutlaatuisen avain `Object`-tyypin ja tekstin `String`, joka näytetään listakomponentissa.
- Voit rakentaa <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>:n antamalla avaimen ja tekstin tai määrittämällä vain tekstin, jota käytetään myös avaimena.

Avain tulee olla ainutlaatuinen listassa. Toisen tekstipohjaisen elementin lisääminen, jossa on sama teksti, heittää `IllegalArgumentException`-poikkeuksen. Toistuvien nimien näyttämiseksi on toimitettava erilliset avaimet noille elementeille.

## `ListItem` -objektien hallinta API:n avulla {#managing-listitem-objects-with-the-api}

Eri listakomponentit tarjoavat useita menetelmiä elementtien hallitsemiseksi ja johdonmukaisen tilan ylläpitämiseksi listan ja asiakkaan välillä. Näiden menetelmien käyttö auttaa sinua tehokkaasti hallitsemaan elementtejä listassa. API:n avulla voit vuorovaikuttaa ja manipuloida listaa sovelluksesi vaatimusten mukaan.

### Elementtien lisääminen {#adding-items}

- **Elementin lisääminen**:

   - Voit lisätä `ListItem`:n listaan käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(com.webforj.component.list.ListItem)' code="true">add(ListItem item)</JavadocLink> -metodia.
   - Voit myös lisätä uuden `ListItem`:n määrittämällä avaimen ja tekstin käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.Object,java.lang.String)' code="true">add(Object key, String text)</JavadocLink> tai <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.String)' code="true">add(String text)</JavadocLink> -metodia.

- **Elementin lisääminen tiettyyn indeksiin**:

   - Voit lisätä elementin tiettyyn indeksiin käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,com.webforj.component.list.ListItem)' code="true">insert(int index, ListItem item)</JavadocLink> -metodia.
   - Voit lisätä elementin avaimen ja tekstin avulla käyttäen <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.Object,java.lang.String)' code="true">insert(int index, Object key, String text)</JavadocLink> tai <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.String)' code="true">insert(int index, String text)</JavadocLink> -metodia.

- **Useiden elementtien lisääminen**:

   - Voit lisätä useita elementtejä määritettyyn indeksiin käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.util.List)' code="true">insert(int index, List< ListItem > items)</JavadocLink> -metodia.

:::tip
Suorituskyvyn optimoimiseksi on tehokkaampaa luoda ensin lista <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> -objekteista sen sijaan, että laukaise palvelimen ja asiakkaan välinen viesti joka kerta, kun käytät `add()` -metodia. Kun sinulla on tämä lista, voit lisätä ne kaikki kerralla käyttäen `insert(int index, List<ListItem> items)` -metodia. Tämä lähestymistapa vähentää palvelimen ja asiakkaan välistä viestintää, mikä parantaa kokonaisuudessaan tehokkuutta. Yksityiskohtaisia ohjeita tästä ja muista parhaista käytännöistä webforJ-arkkitehtuurissa on saatavilla kohdasta [Client/Server Interaction](/docs/architecture/client-server).
:::

### Elementtien poistaminen {#removing-items}

- **Elementin poistaminen**:

   - Voit poistaa elementin listasta käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(int)' code="true">remove(int index)</JavadocLink> tai <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(java.lang.Object)' code="true">remove(Object key)</JavadocLink> -metodia.

- **Kaikkien elementtien poistaminen**:
   - Voit poistaa kaikki elementit listasta käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#removeAll()' code="true">removeAll()</JavadocLink>.

### Elementtien valitseminen {#selecting-items}

Kaikki listatyypit toteuttavat `SelectableList` -rajapinnan. Tämä rajapinta mahdollistaa useita eri tapoja valita nykyinen `ListItem`.

#### Tietyn `ListItem` avulla {#with-a-given-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#select(com.webforj.component.list.ListItem)' code="true">select(ListItem item)</JavadocLink> ottaa parametrina `ListItem` -objektin valitsemiseksi.

```java {4}
ListBox demoList = new ListBox();
ListItem demoItem = new ListItem("demo","Demo Item");
demoList.add(demoItem);
demoList.select(demoItem);
```

#### Tietyn `ListItem` avaimen avulla {#with-a-given-key-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectKey(java.lang.Object)' code="true">selectKey(Object key)</JavadocLink> ottaa avaimen `ListItem` -objektin valitsemiseksi.

```java {3}
ListBox demoList = new ListBox();
demoList.add("demo","Demo Item");
demoList.selectKey("demo");
```

#### Tietyn `ListItem` indeksin avulla {#with-a-given-index-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectIndex(int)' code="true">selectIndex(int index)</JavadocLink> ottaa indeksin `ListItem` -objektin valitsemiseksi.

```java {3}
ListBox demoList = new ListBox();
demoList.add("demo","Demo Item");
demoList.selectIndex(0);
```

### Muut listatoiminnot {#other-list-operations}

- **Elementtien käyttö ja päivittäminen**:

   - Voit käyttää elementtejä avaimen tai indeksin mukaan, käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByKey(java.lang.Object)' code="true">getByKey(Object key)</JavadocLink> tai <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByIndex(int)' code="true">getByIndex(int index)</JavadocLink>.
   - Voit päivittää elementin tekstiä käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" suffix='#setText(java.lang.String)' code="true">setText(String text)</JavadocLink> -metodia <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> -luokassa.

- **Tietojen hakeminen listasta**:
   - Voit saada listan koon käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#size()' code="true">size()</JavadocLink> -metodia.
   - Tarkistaaksesi onko lista tyhjät, käytä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#isEmpty()' code="true">isEmpty()</JavadocLink> -metodia.

### Listoihin iterointi {#iterating-over-lists}

Kaikki listakomponentit toteuttavat Java [`Iterable`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Iterable.html) -rajapinnan. Käytä `forEach()`- tai `for`-silmukkaa päästäksesi käsiksi tai muokataksesi jokaista `ListItem`:ia.

Alla oleva koodin pätkä näyttää kaksi helppoa tapaa iteroida listan läpi:

```java
list.forEach(item -> {
   item.setText("Muokattu: " + item.getText());
});

for (ListItem item : list) {
   item.setText("Muokattu2: " + item.getText());
}
```

## Haku <DocChip chip='since' label='26.02' /> {#searching}

Kaikissa List-komponenteissa on sisäänrakennettu hakukenttä, joka suodattaa elementtejä niiden tekstin perusteella. Kenttä on oletuksena pois päältä. Käytä `getSearch()` päästäksesi hakukonfiguraatioon, sitten `setFieldVisible(true)` näyttääksesi kentän komponentin listan yläosassa.

```java
ComboBox comboBox = new ComboBox("Hedelmä");
comboBox.insert("Omena", "Banaani", "Kirsikka", "Aprikoosi", "Ananas");

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

Suodatus piilottaa vain ne elementit, jotka eivät vastaa. Elementtien indeksit ja nykyinen valinta pysyvät muuttumattomina, joten `getSelectedIndex()` viittaa edelleen koko listaan eikä vain nykyisin näkyviin elementteihin.

Hakukenttä voidaan piilottaa uudelleen käyttämällä `setFieldVisible(false)`.

### Kentän konfigurointi {#configuring-the-field}

- `setPlaceholder()` asettaa hakukentän paikkamerkkitekstin. Oletusarvo on `Hae`.

-  `setEmptyMessage()` asettaa viestin, joka näkyy, kun haku ei palauta tuloksia. Oletusarvo on `Ei näyttödataa`.

Jokaiselle asetukselle on vastaava getter: `isFieldVisible()`, `getPlaceholder()`, `getEmptyMessage()`, ja `getTerm()`.

### Suodatus koodista {#filtering-from-code}

`setTerm()` asettaa hakuteremin ja suodattaa listan. Se toimii riippumatta siitä, onko kenttä näkyvissä, joten lista voidaan suodattaa ilman, että hakui-käyttöliittymää näytetään.

```java
listBox.getSearch().setTerm("omena");
```

:::warning `getTerm()` ja hakukenttä
Kirjoittaminen hakukenttään ei kirjoita termiä takaisin konfiguraatioon. `getTerm()` palauttaa viimeisen arvon, joka annettiin `setTerm()`:lle, eikä sitä, mitä käyttäjä on kirjoittanut.
:::

## Yhteiset listan ominaisuudet {#shared-list-properties}

### Otsikko {#label}

Kaikki List-komponentit voidaan liittää otsikkoon, joka on kuvaava teksti tai nimi, joka liittyy komponenttiin. Otsikot tarjoavat lyhyen selityksen tai kehotuksen, joka auttaa käyttäjiä ymmärtämään tietyn listan tarkoituksen tai odotetun valinnan. Käytettävyyden lisäksi listan otsikoilla on myös tärkeä rooli saavutettavuudessa, mahdollistaen näyttöruudun lukijoiden ja apuvälineiden tarjoamat tarkat tiedot ja helpottaen näppäimistön navigointia.

### Aputeksti {#helper-text}

Jokainen List-komponentti voi näyttää aputekstiä listan alla käyttämällä `setHelperText()` -metodia. Tämä aputeksti tarjoaa lisäkontekstia tai selityksiä saatavilla olevista vaihtoehdoista, varmistaen, että käyttäjillä on tarvittavat tiedot harkittuihin valintoihin.

### Vaaka-koordinaatio {#horizontal-alignment}

Kaikki listakomponentit toteuttavat <JavadocLink type="foundation" location="com/webforj/concern/HasHorizontalAlignment" code='true'>HasHorizontalAlignment</JavadocLink> -rajapinnan, joka antaa sinulle hallintaa siitä, miten teksti ja sisältö on kohdistettu komponentissa.

Käytä `setHorizontalAlignment()` -metodia yhdessä `HasHorizontalAlignment.Alignment` -enumeraation kanssa asettaaksesi kohdistuksen:

- `Alignment.LEFT` (oletus)
- `Alignment.MIDDLE`
- `Alignment.RIGHT`

```java
ListBox listBox = new ListBox();
listBox.setHorizontalAlignment(Alignment.LEFT);
```

Hanki nykyinen kohdistus:
```java
Alignment alignment = listBox.getHorizontalAlignment();
```

### Laajuus {#expanses}

Kaikki listakomponentit webforJ:ssa toteuttavat myös <JavadocLink type="foundation" location="com/webforj/concern/HasExpanse" code='true'>HasExpanse</JavadocLink> -rajapinnan, jolloin voit säätää komponentin kokoluokkaa ja visuaalista painoa. Tämä on hyödyllistä mukautettaessa komponenttia eri käyttöliittymäyhteyksiin, kuten lomakkeisiin, dialogeihin, sivupalkkeihin jne.

Käytä `setExpanse()` -metodia säätääksesi laajuusastetta. Vaihtoehtoja ovat:

- `Expanse.NONE`
- `Expanse.XSMALL`
- `Expanse.SMALL`
- `Expanse.MEDIUM` (oletus)
- `Expanse.LARGE`
- `Expanse.XLARGE`

```java
ListBox listBox = new ListBox();
listBox.setExpanse(Expanse.LARGE);
```

Voit noutaa nykyisen asetuksen käyttämällä:
```java
Expanse current = listBox.getExpanse();
```

## Aiheet {#topics}

<DocCardList className="topics-section" />
