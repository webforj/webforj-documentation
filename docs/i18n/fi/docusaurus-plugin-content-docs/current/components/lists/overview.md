---
sidebar_position: 20
title: Lists
hide_giscus_comments: true
_i18n_hash: cf4917e0ffb74122b24c210ec7502cbf
---
<JavadocLink type="foundation" location="com/webforj/component/list/DwcList"/>

:::info
Tässä osiossa kuvataan kaikkien luettelokomponenttien yleiset ominaisuudet, eivätkä ne ole luokka, jota voidaan instanssia tai käyttää suoraan.
:::

Sovelluksissasi on kolme tyyppiä luetteloita: [`ListBox`](listbox), [`ChoiceBox`](choicebox) ja [`ComboBox`](combobox). Nämä komponentit näyttävät kaikki avain-arvo -esineitä ja tarjoavat menetelmiä esineiden lisäämiseen, poistamiseen, valitsemiseen ja hallintaan luettelossa.

Tällä sivulla kuvataan kaikkien luettelokomponenttien jaettavat ominaisuudet ja käyttäytyminen, kun taas kunkin erityiset yksityiskohdat käsitellään niiden omilla sivuilla.

## Käyttäminen `ListItem` {#using-listitem}

Luettelokomponentit koostuvat <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> -objekteista, jotka edustavat yksittäisiä esineitä luettelossa. Jokaisella <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> -objektilla on ainutlaatuinen avain ja näyttöteksti. Tärkeitä ominaisuuksia <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> -luokassa ovat:

- <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> kapseloi ainutlaatuisen avaimen `Object` ja tekstin `String`, joka näytetään luettelokomponentissa.
- Voit rakentaa <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> -objektin antamalla avaimen ja tekstin tai määrittämällä vain tekstin niin, että satunnainen avain luodaan.

## `ListItem` -objektien hallinta API:n avulla {#managing-listitem-objects-with-the-api}

Erilaiset luettelokomponentit tarjoavat useita menetelmiä esineiden hallitsemiseksi ja yhtenäisen tilan ylläpitämiseksi luettelon ja asiakkaan välillä. Käyttämällä näitä menetelmiä voit tehokkaasti hallita esineitä luettelossa. API mahdollistaa vuorovaikutuksen ja luettelon manipuloinnin sovelluksesi vaatimusten täyttämiseksi.

### Esineiden lisääminen {#adding-items}

- **Esineen lisääminen:**

   - Lisätäksesi `ListItem` luetteloon, voit käyttää <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(com.webforj.component.list.ListItem)' code="true">add(ListItem item)</JavadocLink> -menetelmää.
   - Voit myös lisätä uuden `ListItem` määrittämällä avaimen ja tekstin käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.Object,java.lang.String)' code="true">add(Object key, String text)</JavadocLink> tai <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.String)' code="true">add(String text)</JavadocLink> -menetelmää.

- **Esineen lisääminen tiettyyn indeksiin:**

   - Lisääksesi esineen tiettyyn indeksiin, käytä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,com.webforj.component.list.ListItem)' code="true">insert(int index, ListItem item)</JavadocLink> -menetelmää.
   - Voit lisätä esineen, jolla on avain ja teksti, käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.Object,java.lang.String)' code="true">insert(int index, Object key, String text)</JavadocLink> tai <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.String)' code="true">insert(int index, String text)</JavadocLink> -menetelmää.

- **Useiden esineiden lisääminen:**

   - Voit lisätä useita esineitä määritettyyn indeksiin käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.util.List)' code="true">insert(int index, List< ListItem > items)</JavadocLink> -menetelmää.

:::tip
Suorituskyvyn optimoimiseksi, sen sijaan että laukaise serveristä asiakkaille -viestin jokaisella kertaa, kun käytät `add()` -menetelmää, on tehokkaampaa luoda List <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> -objekteista ensin. Kun sinulla on tämä lista, voit lisätä ne kaikki kerralla käyttämällä `insert(int index, List<ListItem> items)` -menetelmää. Tämä lähestymistapa vähentää palvelin-asiakas -viestintää, mikä parantaa kokonaistehokkuutta. Yksityiskohtaisille ohjeille tästä ja muista parhaista käytännöistä webforJ-arkkitehtuurissa, katso [Asiakas/Palvelin vuorovaikutus](/docs/architecture/client-server).
:::

### Esineiden poistaminen {#removing-items}

- **Esineen poistaminen:**

   - Poistaaksesi esineen luettelosta, käytä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(int)' code="true">remove(int index)</JavadocLink> tai <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(java.lang.Object)' code="true">remove(Object key)</JavadocLink> -menetelmää.

- **Kaikkien esineiden poistaminen:**
   - Voit poistaa kaikki esineet luettelosta käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#removeAll()' code="true">removeAll()</JavadocLink>.

### Esineiden valitseminen {#selecting-items}

Kaikki luettelotyypit toteuttavat `SelectableList` -rajapinnan. Tämä rajapinta mahdollistaa useita erilaisia tapoja valita nykyinen `ListItem`.

#### Tietyn `ListItem` avulla {#with-a-given-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#select(com.webforj.component.list.ListItem)' code="true">select(ListItem item)</JavadocLink> ottaa `ListItem` -parametrin valitakseen.

```java {4}
List demoList = new List();
ListItem demoItem = new ListItem("demo","Demo Item");
demoList.add(demoItem);
demoList.select(demoItem);
```

#### Tietyn `ListItem` avaimen avulla {#with-a-given-key-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectKey(java.lang.Object)' code="true">selectKey(Object key)</JavadocLink> ottaa avaimen `ListItem` -parametrille valitakseen.

```java {3}
List demoList = new List();
demoList.add("demo","Demo Item");
demoList.selectKey("demo");
```

#### Tietyn `ListItem` indeksin avulla {#with-a-given-index-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectIndex(int)' code="true">selectIndex(int index)</JavadocLink> ottaa indeksin `ListItem` -parametriksi valitakseen.

```java {3}
List demoList = new List();
demoList.add("demo","Demo Item");
demoList.selectKey(0);
```

### Muut luettelo-operaatiot {#other-list-operations}

- **Esineiden käsitteleminen ja päivittäminen:**

   - Käyttääksesi esineitä avaimen tai indeksin mukaan, käytä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByKey(java.lang.Object)' code="true">getByKey(Object key)</JavadocLink> tai <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByIndex(int)' code="true">getByIndex(int index)</JavadocLink>.
   - Voit päivittää esineen tekstiä käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" suffix='#setText(java.lang.String)' code="true">setText(String text)</JavadocLink> -menetelmää <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> -luokassa.

- **Tietojen hakeminen luettelosta:**
   - Voit saada luettelon koon käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#size()' code="true">size()</JavadocLink> -menetelmää.
   - Tarkistaaksesi, onko luettelo tyhjää, käytä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#isEmpty()' code="true">isEmpty()</JavadocLink> -menetelmää.

### Luetteloiden läpikäynti {#iterating-over-lists}

Kaikki Luettelo komponentit toteuttavat Java [`Iteratable`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Iterable.html) -rajapinnan, joka tarjoaa tehokkaan ja intuitiivisen tavan iteratiivisesti käydä läpi luettelon sisältöä. Tämän rajapinnan avulla voit helposti silmukoida jokaisen `ListItem` läpi, mikä helpottaa kutakin esinettä pääsyä, muokkausta tai toimintojen suorittamista vähäisellä vaivalla. `Iterable` -rajapinta on standardimalli Java-kielessä, mikä varmistaa, että koodisi on tuttua ja ylläpidettävää mille tahansa Java-kehittäjälle.

Koodipätkä alla demonstroi kahta helppoa tapaa käydä läpi luetteloa:

```java
list.forEach(item -> {
   item.setText("Muokattu: " + item.getText());
});

for (ListItem item : list) {
   item.setText("Muokattu2: " + item.getText());
}
```

## Jaetut luettelon ominaisuudet {#shared-list-properties}

### Otsikko {#label}

Kaikille luettelokomponenteille voidaan määrittää otsikko, joka on kuvaileva teksti tai nimi, joka liittyy komponenttiin. Otsikot tarjoavat lyhyen selityksen tai kehotteen auttamaan käyttäjiä ymmärtämään kyseisen luettelon tarkoituksen tai odotetun valinnan. Käytettävyyden lisäksi luettelo-otsikoilla on tärkeä rooli saavutettavuudessa, mahdollistamalla ruudunlukijoiden ja apuvälineiden tarjoamaan tarkkaa tietoa ja helpottamaan näppäimistön navigointia.

### Apua teksti {#helper-text}

Jokainen luettelokomponentti voi näyttää aputekstiä luettelon alapuolella käyttämällä `setHelperText()` -menetelmää. Tämä aputeksti tarjoaa lisäkontekstia tai selityksiä käytettävissä olevista vaihtoehdoista, varmistaen, että käyttäjillä on tarvittavat tiedot järkevien valintojen tekemiseen.

### Vaakasuora kohdistus {#horizontal-alignment}

Kaikki luettelokomponentit toteuttavat <JavadocLink type="foundation" location="com/webforj/concern/HasHorizontalAlignment" code='true'>HasHorizontalAlignment</JavadocLink> -rajapinnan, joka antaa sinulle hallinnan siitä, miten teksti ja sisältö kohdistuvat komponentin sisällä.

Käytä `setHorizontalAlignment()` -menetelmää koostaaksesi kohdistuksen:

- `HorizontalAlignment.LEFT` (oletus)
- `HorizontalAlignment.MIDDLE`
- `HorizontalAlignment.RIGHT`

```java
ListBox<String> listBox = new ListBox<>();
listBox.setHorizontalAlignment(HorizontalAlignment.LEFT);
```

Saadaksesi nykyisen kohdistuksen:
```java
HorizontalAlignment alignment = listBox.getHorizontalAlignment();
```

### Laajennukset {#expanses}

Kaikki webforJ:n luettelokomponentit toteuttavat myös <JavadocLink type="foundation" location="com/webforj/concern/HasExpanse" code='true'>HasExpanse</JavadocLink> -rajapinnan, mikä mahdollistaa komponentin kokonaiskoon ja visuaalisen painoarvon säätämisen. Tämä on hyödyllistä komponentin sopeuttamiseksi erilaisiin käyttöliittymäyhteyksiin, kuten lomakkeisiin, dialogeihin, sivupalkkeihin jne.

Käytä `setExpanse()` -menetelmää asettaaksesi laajennustason. Vaihtoehtoja ovat:

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

Voit noutaa nykyisen asetuksen käyttämällä:
```java
Expanse current = listBox.getExpanse();
```

## Aiheet {#topics}

<DocCardList className="topics-section" />
