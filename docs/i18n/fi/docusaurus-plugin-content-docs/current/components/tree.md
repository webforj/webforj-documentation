---
title: Tree
sidebar_position: 150
_i18n_hash: b161d0d5855f65cb593cf23bc2695d5b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<DocChip chip='since' label='25.01' />
<JavadocLink type="foundation" location="com/webforj/component/tree/Tree" top='true'/>

`Tree`-komponentti järjestää tiedot solmujen hierarkiana. Jokaisella solmulla on ainutlaatuinen avain ja etiketti. Solmut yhdistyvät muodostaen vanhempi-lapsi-suhteita. Voit laajentaa tai supistaa solmuja näyttämään tai piilottamaan niiden lapsia. Kuvakkeet selkeyttävät, minkä tyyppisestä solmusta on kyse ja onko se valittu. Valinta tukee yhden tai useamman solmun valitsemista kerralla.

## Solmumalli ja puurakenne {#node-model-and-tree-structure}

### `TreeNode`:n rooli {#the-role-of-treenode}

Jokainen puun tietopala on kääritty `TreeNode`:iin. Tämä objekti sisältää avaimen, tekstietiketin sekä linkit sen vanhempiin ja lapsisolmuihin. Juurisolmu on erityinen: se on olemassa jokaisessa puussa, mutta ei ole näkyvissä. Se toimii kaikkien ylätason solmujen säilönä, mikä helpottaa puurakenteen hallintaa sisäisesti.

Koska solmut pitävät viittauksia vanhempiinsa ja lapsiinsa, puussa navigointi on suoraviivaista. Halusitpa siirtyä ylöspäin, alaspäin tai etsiä tiettyä solmua avaimen perusteella, yhteydet ovat aina saatavilla.

### Solmujen luominen ja hallinta {#node-creation-and-management}

Solmut luodaan yksinkertaisilla tehdasmenetelmillä, joko antamalla avain ja teksti tai pelkkä teksti (joka toimii myös avaimena). Tämä takaa, että jokainen solmu on voimassa ja ainutkertaisesti tunnistettavissa.

Solmujen lisääminen puuhun tapahtuu kutsumalla `add()` tai `insert()` vanhempisolmulle. Nämä menetelmät käsittelevät vanhemman viittauksen määrittämisen ja ilmoittavat puulle, että sen käyttöliittymä on päivitettävä.

Esimerkki:

```java
TreeNode parent = Tree.node("Parent");
TreeNode child = Tree.node("Child");
parent.add(child);
tree.add(parent);
```

:::info Vain yksi vanhempi
Yritettäessä antaa sama solmu useammalle vanhemmalle, syntyy poikkeus. Tämä suojaus takaa, että puu säilyttää oikean hierarkian estämällä solmuja saamasta useita vanhempia, mikä rikkoisi rakenteen eheyttä ja aiheuttaisi odottamattomia käyttäytymismalleja.
:::

<ComponentDemo 
path='/webforj/tree?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeView.java'
height='300px'
/>

### Solmujen muokkaaminen {#modifying-nodes}

Voit päivittää solmun etikettiä kutsumalla `setText(String text)`. Tämä menetelmä muuttaa puussa näytettävää tekstiä solmulle.

Voit poistaa tietyn lapsisolmun käyttämällä `remove(TreeNode child)`. Tämä irrottaa lapsen sen vanhemmasta ja poistaa sen puurakenteesta. Se myös tyhjentää vanhempi-viittauksen.

Jos haluat tyhjentää kaikki lapset solmusta, kutsu `removeAll()`. Tämä poistaa jokaisen lapsisolmun, tyhjentää niiden vanhempi-viittauksen ja tyhjentää lasten listan.

Jokainen solmu tukee lisätietojen tallentamista palvelinpuolella käyttämällä `setUserData(Object key, Object data)`. Tämä antaa sinun liittää satunnaista metatietoa tai viittauksia solmuun ilman, että tätä tietoa paljastetaan asiakkaalle tai käyttöliittymälle.

:::tip Demoa käyttämällä solmun tekstin muokkaamista
Demossa voit kaksoisnapauttaa solmua avataksesi editorin sen tekstille. Syötä uusi teksti ja tallenna se päivittääksesi solmun etiketti puussa.
:::

<ComponentDemo 
path='/webforj/treemodify?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeModifyView.java'
height='320px'
/>

## Kuvakkeet {#icons}

Kuvakkeet tarjoavat visuaalisia vihjeitä siitä, mitä solmut edustavat ja mikä niiden tila on. Ne parantavat luettavuutta erottamalla solmujen tyypit ja valintatilat yhdellä silmäyksellä. `Tree`-komponentti tukee oletuskuvakkeiden asettamista globaalisti, kuvakkeiden räätälöimistä per solmu ja kuvakkeiden näkyvyyden vaihtamista.

### Globaalit kuvakkeet {#global-icons}

Puu sallii oletuskuvakkeiden asettamisen supistettaville ryhmille, laajennetuille ryhmille, lehtisolmuille ja valituille lehdille.

Esimerkki:

```java
tree.setCollapsedIcon(TablerIcon.create("folder"));
tree.setExpandedIcon(TablerIcon.create("folder-opened"));
tree.setLeafIcon(TablerIcon.create("file"));
tree.setLeafSelectedIcon(TablerIcon.create("file-checked"));
```

:::tip Kuvakejatkoja
Kuvake voi olla mikä tahansa voimassa oleva webforJ [kuvake](./icon) määritelmä tai resurssitiedosto, joka ladataan webforJ [tuettujen resurssiprotokollien](../managing-resources/assets-protocols) kautta.
:::

### Per-solmukuvakkeet {#per-node-icons}

Voit ohittaa globaalit oletukset määrittämällä kuvakkeet yksittäisille solmuille. Tämä on hyödyllistä, kun tietyt solmut edustavat erilaisia käsitteitä, kuten "projekti" kansioita tai erityisiä tiedostoja.

Esimerkki:

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### Kuvakkeiden näkyvyys {#icon-visibility}

Joskus saatat haluta piilottaa kuvakkeet ryhmiltä tai lehdiltä, jotta kaos vähenee. Komponentti sallii globaalin näkyvyyden kytkemisen tälle kategorioille, mikä antaa sinun yksinkertaistaa puun ulkoasua ilman, että rakenne katoaa.

Esimerkki:

```java
tree.setGroupIconsVisible(false);
tree.setLeafIconsVisible(false);
```

<ComponentDemo 
path='/webforj/treeicons?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeIconsView.java'
height='320px'
/>

## Solmujen laajentaminen ja supistaminen {#node-expansion-and-collapse}

Solmuja voidaan laajentaa tai supistaa ohjaamaan, mitkä osat puusta ovat näkyvissä. Tämä mahdollistaa keskittymisen olennaisiin osiin ja tukee skenaarioita, kuten laiskaa lataamista tai dynaamisia tietopäivityksiä.

### Laajennus- ja supistustoiminnot {#expand-and-collapse-operations}

Puu tukee yksittäisten solmujen laajentamista ja supistamista joko niiden avaimen tai suoran viittauksen kautta. Voit myös laajentaa tai supistaa kaikkia solmun jälkeläisiä kerralla.

Nämä toiminnot antavat sinulle hallinnan siitä, kuinka suuri osa puusta on näkyvissä, ja tukevat laiskaa datan lataamista tai keskittymistä kiinnostaviin alueisiin.

Esimerkki:

```java
tree.expand(node);
tree.collapse(key);

// supista alapuustoja
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info Juuren supistaminen
Juurisolmu ankkuroi puun mutta pysyy piilossa. Juuren supistaminen piilottaisi normaalisti kaiken, mikä saisi puun näyttämään tyhjältä. Välttääksen tämän, juuren supistaminen supistaa itse asiassa kaikki sen lapset, mutta pitää juuren laajana sisäisesti, varmistaen, että puu näyttää edelleen sisältönsä oikein.
:::

### Laiska lasten lataaminen {#lazy-loading-nodes}

Puu tukee lasten laiskaa lataamista reagoimalla laajentumistapahtumiin. Kun käyttäjä laajentaa solmua, sovelluksesi voi ladata tai luoda sen lapsia dynaamisesti. Tämä parantaa suorituskykyä lataamalla vain näkyvät osat puusta tarvittaessa.

Käytä `onExpand`-tapahtumaa tunnistaaksesi, kun solmu on laajennettu. Käsittelijässä tarkista, ovatko solmun lapset paikkamerkkejä (esimerkiksi pyörivä latauskuvake tai tyhjät solmut) ja korvata ne oikeilla tiedoilla ladattaessa.

<ComponentDemo 
path='/webforj/treelazyload?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java'
height='250px'
/>

## Valinta {#selection}

Valinta ohjaa, mitkä solmut käyttäjä on valinnut. `Tree`-komponentti tukee joustavia tiloja ja rajapintoja solmujen valitsemiseksi, poistamiseksi ja kysymiseksi.

### Valintatilat {#selection-modes}

Voit valita, sallitaanko puun valita vain yksi solmu kerrallaan vai useita solmuja samanaikaisesti. Siirtyminen useasta valinnasta yksittäiseen valintaan poistaa automaattisesti kaikki paitsi ensimmäisen valitun solmun.

Esimerkki:

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip Monivalintatoiminto
Kun puu on asetettu monivalintatilaan, käyttäjät voivat valita useamman kuin yhden solmun kerrallaan. Tämä toiminta riippuu laitteesta:

* **Työpöytä (hiiri ja näppäimistö):** Käyttäjät pitävät alas **Ctrl**-näppäintä (tai **Cmd**-näppäintä macOS:llä) ja napsauttavat solmuja lisätäkseen tai poistaakseen ne nykyisestä valinnasta. Tämä mahdollistaa useiden yksittäisten solmujen valitsemisen ilman, että muita poistetaan valinnasta.
* **Mobiili ja kosketuslaitteet:** Koska muokkausnäppäimiä ei ole käytettävissä, käyttäjät napauttavat yksinkertaisesti solmuja valitakseen tai poistaakseen ne valinnastaan. Jokainen napautus vaihtaa kyseisen solmun valintatilaa, mahdollistaen helpon monivalinnan yksinkertaisilla napautuksilla.
:::

### Valitseminen ja poistaminen {#selecting-and-deselecting}

Solmuja voidaan valita tai poistaa viittauksen, avaimen, yksittäin tai ryhmissä. Voit myös valita tai poistaa kaikki solmun lapset yhdellä kutsulla.

Esimerkki:

```java
// valitse solmu viittauksen tai avaimen mukaan
tree.select(node);
tree.selectKey(key);

// poista solmu viittauksen tai avaimen mukaan
tree.deselect(node);
tree.deselectAll();

// valitaan tai poistetaan solmujen lapsia
tree.selectChildren(parentNode);
tree.deselectChildren(parentNode);
```

### Valintatilanteen hakeminen {#selection-state-retrieval}

Voit saada nykyisen valinnan hyödyntämällä alla olevaa koodia:

```java
// saat valitun solmun viittauksen
TreeNode selected = tree.getSelected();
List<TreeNode> selectedItems = tree.getSelectedItems();

// saat valitun solmun avaimen
Object selectedKey = tree.getSelectedKey();
List<Object> selectedKeys = tree.getSelectedKeys();
```

<ComponentDemo 
path='/webforj/treeselection?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeSelectionView.java'
height='400px'
/>

## Tyylittely {#styling}

<TableBuilder name="Tree" />
