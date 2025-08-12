---
sidebar_position: 20
title: Lists
hide_giscus_comments: true
_i18n_hash: 15effbe238b9ea86c975499ed2faa30b
---
<JavadocLink type="foundation" location="com/webforj/component/list/DwcList"/>

:::info
Tässä osiossa kuvataan kaikkien luettelokomponenttien yleisiä ominaisuuksia, eikä se ole luokka, jota voidaan instansioida tai käyttää suoraan.
:::

Sovelluksissasi on käytettävissä kolme erilaista luetteloa: [`ListBox`](listbox), [`ChoiceBox`](choicebox) ja [`ComboBox`](combobox). Nämä komponentit näyttävät kaikki avain-arvo-itemien luettelon ja tarjoavat menetelmiä itemien lisäämiseen, poistamiseen, valitsemiseen ja hallintaan luettelossa.

Tällä sivulla esitetään kaikkien luettelokomponenttien yhteiset ominaisuudet ja käyttäytyminen, kun taas kunkin erityiset tiedot käsitellään niiden omilla sivuillaan.

## Käyttäminen `ListItem` {#using-listitem}

Luettelokomponentit koostuvat <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>-objekteista, jotka edustavat yksittäisiä itemeitä luettelossa. Jokainen <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> on liitetty yksilölliseen avaimen ja näyttetekstiin. Tärkeitä piirteitä <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>-luokassa ovat:

- <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> kapseloi ainutlaatuisen avaimen `Object` ja tekstin `String`, joka näytetään luettelokomponentissa. 
- Voit luoda <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>-objektin antamalla avaimen ja tekstin, tai määrittelemällä vain tekstin, jolloin satunnainen avain luodaan.

## `ListItem`-objektien hallinta API:n avulla {#managing-listitem-objects-with-the-api}

Erilaiset luettelokomponentit tarjoavat useita menetelmiä itemien hallintaan ja johdonmukaisen tilan ylläpitämiseen luettelon ja asiakkaan välillä. Näitä menetelmiä käyttämällä voit hallita tehokkaasti luettelon itemeitä. API:n avulla voit vuorovaikuttaa ja manipuloida luetteloa sovelluksesi vaatimusten mukaan.

### Itemien lisääminen {#adding-items}

- **Itemin lisääminen**:

   - Voit lisätä `ListItem`-objektin luetteloon käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(com.webforj.component.list.ListItem)' code="true">add(ListItem item)</JavadocLink> -menetelmää.
   - Voit myös lisätä uuden `ListItem`-objektin määrittämällä avaimen ja tekstin käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.Object,java.lang.String)' code="true">add(Object key, String text)</JavadocLink> tai <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.String)' code="true">add(String text)</JavadocLink> -menetelmää.


- **Itemin lisääminen tiettyyn indeksiin:**

   - Voit lisätä itemin tiettyyn indeksiin käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,com.webforj.component.list.ListItem)' code="true">insert(int index, ListItem item)</JavadocLink> -menetelmää.
   - Voit lisätä itemin, yhdistäen avaimen ja tekstin, käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.Object,java.lang.String)' code="true">insert(int index, Object key, String text)</JavadocLink> tai <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.String)' code="true">insert(int index, String text)</JavadocLink> -menetelmää.

- **Useiden itemien lisääminen:** 

   - Voit lisätä useita itemeitä määritettyyn indeksiin käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.util.List)' code="true">insert(int index, List< ListItem > items)</JavadocLink> -menetelmää.

:::tip
Suorituskyvyn optimoimiseksi, sen sijaan että laukaistaisit palvelin-asiakasviestin joka kerta, kun käytät `add()`-menetelmää, on tehokkaampaa luoda ensin <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>-objektien lista. Kun sinulla on tämä lista, voit lisätä ne kaikki kerralla käyttäen `insert(int index, List<ListItem> items)` -menetelmää. Tämä lähestymistapa vähentää palvelin-asiakasviestintää, parantaen koko tehokkuutta. Yksityiskohtaisia ohjeita tästä ja muista parhaista käytännöistä webforJ-arkkitehtuurissa, katso [Asiakas/Palvelin vuorovaikutus](/docs/architecture/client-server).
:::

### Itemien poistaminen {#removing-items}

- **Itemin poistaminen:**

   - Poistaaksesi itemin luettelosta, käytä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(int)' code="true">remove(int index)</JavadocLink> tai <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(java.lang.Object)' code="true">remove(Object key)</JavadocLink> -menetelmää.

- **Kaikkien itemien poistaminen:**
   - Voit poistaa kaikki itemit luettelosta käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#removeAll()' code="true">removeAll()</JavadocLink>.

### Itemien valitseminen {#selecting-items}

Kaikki luettelotyypit toteuttavat `SelectableList`-rajapinnan. Tämä rajapinta mahdollistaa useita eri tapoja valita nykyinen `ListItem`.

#### Annetun `ListItem` avulla {#with-a-given-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#select(com.webforj.component.list.ListItem)' code="true">select(ListItem item)</JavadocLink> ottaa `ListItem`-parametrin valitsemiseksi.

```java {4}
List demoList = new List();
ListItem demoItem = new ListItem("demo","Demo Item");
demoList.add(demoItem);
demoList.select(demoItem);
```

#### Annetun `ListItem`:n avaimen avulla {#with-a-given-key-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectKey(java.lang.Object)' code="true">selectKey(Object key)</JavadocLink> ottaa avaimen `ListItem`-valitsemiseksi.

```java {3}
List demoList = new List();
demoList.add("demo","Demo Item");
demoList.selectKey("demo");
```

#### Annetun `ListItem`:n indeksin avulla {#with-a-given-index-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectIndex(int)' code="true">selectIndex(int index)</JavadocLink> ottaa indeksin `ListItem`-valitsemiseksi.

```java {3}
List demoList = new List();
demoList.add("demo","Demo Item");
demoList.selectKey(0);
```

### Muut luettelotoiminnot {#other-list-operations}

- **Itemien saavutettavuus ja päivittäminen:**

   - Saadaksesi itemit avaimen tai indeksin kautta, käytä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByKey(java.lang.Object)' code="true">getByKey(Object key)</JavadocLink> tai <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByIndex(int)' code="true">getByIndex(int index)</JavadocLink>.
   - Voit päivittää itemin tekstiä käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" suffix='#setText(java.lang.String)' code="true">setText(String text)</JavadocLink> -menetelmää <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>-luokassa.

- **Tietojen hakeminen listasta:**
   - Voit saada luettelon koon käyttämällä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#size()' code="true">size()</JavadocLink> -menetelmää.
   - Tarkistaaksesi, onko luettelo tyhjentynyt, käytä <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#isEmpty()' code="true">isEmpty()</JavadocLink> -menetelmää.

### Listojen läpikäynti {#iterating-over-lists}

Kaikki Luettelo-n komponentit toteuttavat Java [`Iteratable`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Iterable.html) -rajapinnan, joka tarjoaa tehokkaan ja intuitiivisen tavan käydä läpi luettelon sisältöä. Tämän rajapinnan avulla voit helposti silmukoida jokaisen `ListItem`-objektin läpi, mikä helpottaa jokaisen itemin saavuttamista, muokkaamista tai käsittelemistä vähällä vaivalla. `Iterable`-rajapinta on standardimalli Java-kielessä, varmistaen, että koodisi on tuttu ja ylläpidettävä kaikille Java-kehittäjille.

Alla esitetty koodinpätkä osoittaa kaksi helppoa tapaa käydä läpi luetteloa:

```java
list.forEach(item -> {
   item.setText("Muokattu: " + item.getText());
});

for (ListItem item : list) {
   item.setText("Muokattu2: " + item.getText());
}
```

## Yhteiset luettelo-ominaisuudet {#shared-list-properties}

### Tekstiosio {#label}

Kaikille Luettelo-n komponenteille voidaan määrittää teksti, joka on kuvaava teksti tai otsikko, joka liittyy komponenttiin. Etiketit tarjoavat lyhyen selityksen tai kehotteen auttaakseen käyttäjiä ymmärtämään kyseisen luettelon tarkoituksen tai odotetun valinnan. Käytettävyyden lisäksi luettelon etiketit ovat tärkeäksi tehtäväksi esteettömyydessä, mahdollistaen ruudunlukuohjelmien ja apuvälineiden tarjoaman tarkan tiedon sekä helpottamaan näppäimistön navigointia.

### Aputeksti {#helper-text}

Jokainen Luettelo-n komponentti voi näyttää aputekstiä luettelon alla käyttämällä `setHelperText()`-menetelmää. Tämä aputeksti tarjoaa lisäkontekstia tai selityksiä käytettävissä olevista vaihtoehdoista, varmistaen, että käyttäjillä on tarpeelliset tiedot tietoon tekemiseksi.

### Vaakasuora kohdistus {#horizontal-alignment}

Kaikki luettelokomponentit toteuttavat <JavadocLink type="foundation" location="com/webforj/concern/HasHorizontalAlignment" code='true'>HasHorizontalAlignment</JavadocLink> -rajapinnan, antamalla sinulle hallinnan siitä, miten teksti ja sisältö kohdistuvat komponentin sisällä.

Käytä `setHorizontalAlignment()`-menetelmää kohdistuksen asettamiseen:

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

### Laajuudet {#expanses}

Kaikki luettelokomponentit webforJ:ssä toteuttavat myös <JavadocLink type="foundation" location="com/webforj/concern/HasExpanse" code='true'>HasExpanse</JavadocLink> -rajapinnan, jonka avulla voit säätää komponentin yleistä kokoa ja visuaalista painetta. Tämä on hyödyllistä mukautettaessa komponenttia eri käyttöliittymäyhteyksiin, kuten lomakkeisiin, dialogeihin, sivupaneeleihin jne.

Käytä `setExpanse()`-menetelmää säätääksesi laajuustasoa. Vaihtoehdoista löytyy:

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

Voit palauttaa nykyisen asetuksen seuraavasti:
```java
Expanse current = listBox.getExpanse();
```

## Aiheet {#topics}

<DocCardList className="topics-section" />
