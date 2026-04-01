---
title: Column Groups
sidebar_position: 7
sidebar_class_name: new-content
_i18n_hash: fccec5a60bfd614d344758d3624bc602
---
<DocChip chip='since' label='25.12' />

Sarake ryhmät antavat sinun järjestää liittyvät sarakkeet jaettujen, monirivisten otsikoiden alle. Ryhmätunnus kattaa lapsisarakkeet, mikä helpottaa käyttäjiä skannaamaan ja ymmärtämään monimutkaisten taulukoiden rakennetta. Ryhmiä voi pesiä mihin tahansa syvyyteen, ja `Table` renderöi automaattisesti oikean määrän otsikkorivejä.

## Sarake ryhmien luominen {#creating-column-groups}

Luo ryhmä käyttämällä `ColumnGroup.of()`-tehdasmetodia, ja ketjuta `add()`-kutsuja täyttääksesi sen sarakeviittauksilla, muilla ryhmillä tai sekoituksella molemmista. Käytä ryhmiä `Table`-komponentissa `setColumnGroups()`-metodilla.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumngroups?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnGroupsView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

Kun ryhmät on asetettu, `Table` renderöi monirivisen otsikon, jossa jokainen ryhmätunnus kattaa lapsisarakkeet. Pesimissyvyys määrittää, kuinka monta otsikkoriviä näkyy. Tasainen ryhmä lisää yhden ylimääräisen rivin, kun taas kaksi tasoa lisää kaksi, ja niin edelleen.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablenestedcolumngroups?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableNestedColumnGroupsView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

<!-- vale Google.OxfordComma = NO -->
Ryhmiä voidaan asettaa tai muuttaa milloin tahansa, ennen tai jälkeen kuin `Table` on renderöity. Siirrä `null` tai tyhjää listaa `setColumnGroups()`-metodiin poistaaksesi kaikki ryhmittelyt ja palataksesi yksiriviseen otsikkoon.
<!-- vale Google.OxfordComma = YES -->

```java
// Poista kaikki sarake ryhmät
table.setColumnGroups(null);
```

## Sarakkeen järjestys {#column-ordering}

Kun ryhmät ovat aktiivisia, visuaalinen sarakejärjestys määräytyy ryhmän puun mukaan eikä sen mukaan, missä järjestyksessä sarakkeet lisättiin `Table`-komponenttiin. Puuta kävellään syvyyssuunnassa, vasemmalta oikealle.

```
Lisätyt sarakkeet:  [A, B, C, D, E]
Ryhmä:           Ryhmä "G1" [C, A], Ryhmä "G2" [E, D]
Visuaalinen järjestys:   C, A, E, D, B
```

Ei-ryhmitellyt sarakkeet, joita ei viitata mihinkään ryhmään, eivät ole piilossa. Ne näkyvät luonnollisessa sijainnissaan suhteessa ryhmiteltyihin sarakkeisiin, perustuen järjestykseen, jolla ne alun perin lisättiin `Table`-komponenttiin.

Tässä esimerkissä `Number` näkyy ensin, koska se lisättiin ennen `Title`. `Label` näkyy `Genre` ja `Cost` välillä, koska se lisättiin niiden väliin alkuperäisessä sarakejärjestyksessä:

```
Lisätyt sarakkeet:  [Number, Title, Artist, Genre, Label, Cost]
Ryhmä:           Ryhmä "Music" [Title, Artist, Genre], Ryhmä "Pricing" [Cost]
Visuaalinen järjestys:   Number, Title, Artist, Genre, Label, Cost
```

Seuraava demo havainnollistaa tätä käyttäytymistä. `Number` ja `Label` eivät ole viitattu mihinkään ryhmään, mutta ne säilyttävät luonnolliset asemansa perustuen järjestykseen, jossa ne lisättiin `Table`-komponenttiin.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumngroupordering?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnGroupOrderingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

:::tip Ungrouped sarakkeiden hallinta
Hallitaksesi ryhmittelyjen ulkopuolisten sarakkeiden sijoittamista erikseen, sisällytä ne pääryhmän sarakeviittauksiksi ryhmän puuhun.
:::

## Sarakkeen siirtäminen ryhmien sisällä {#column-moving-within-groups}

Kun ryhmät ovat aktiivisia, sarakkeen vetäminen ja pudottaminen on rajoitettu ryhmän eheyden ylläpitämiseksi:

- **Ryhmässä**: ryhmän sisällä oleva sarake voidaan siirtää vain sen välittömässä vanhempiryhmässä. Sen vetäminen ryhmän ulkopuolelle hylätään, ja sarake palaa alkuperäiseen sijaintiinsa.
- **Ei-ryhmitellyt sarakkeet**: ei-ryhmitelty sarake voi siirtyä vain paikkoihin, joita muut ei-ryhmitellyt sarakkeet pitävät. Sitä ei voi pudottaa ryhmän keskelle.
- **Ryhmien uudelleenarviointi**: koko ryhmä voidaan vetää uusiksi järjestettäväksi muiden saman pesimisasteen sisarusten joukossa.

```
Ryhmä:  Ryhmä "G1" [A, B, C], Ryhmä "G2" [D, E]

Siirrä A paikkaan 2 -> OK (G1 sisällä, tulos: [B, C, A])
Siirrä A paikkaan 3 -> Hylätty (paikka 3 on G2:ssä)
Siirrä D paikkaan 4 -> OK (G2 sisällä, tulos: [E, D])
Siirrä D paikkaan 1 -> Hylätty (paikka 1 on G1:ssä)
```

## Ryhmien kiinnittäminen {#pinning-groups}

Ryhmän voi kiinnittää vasemmalle tai oikealle käyttämällä `setPinDirection()`-metodia. Kaikki ryhmän sisällä olevat sarakkeet perivät ryhmän kiinnitys suunnan, ja yksittäisten sarakkeiden kiinnitys asetukset ohittuvat ryhmän toimesta.

```java
ColumnGroup idInfo = ColumnGroup.of("id-info", "ID Info")
  .setPinDirection(PinDirection.LEFT)
  .add("number")
  .add("title");

// Sekä "number" että "title" ovat kiinnitetty vasemmalle,
// riippumatta omista kiinnitysasetuksistaan
```

Ei-ryhmitellyt sarakkeet säilyttävät oman kiinnityssuunnansa sarake määrittelystään.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablepinnedcolumngroups?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TablePinnedColumnGroupsView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

## Ryhmän otsikon korkeus {#group-header-height}

Ryhmäotsikon rivikorkeutta voidaan hallita erikseen tavallisista sarakeotsikoista käyttäen `setGroupHeaderHeight()`-metodia.

```java
table.setGroupHeaderHeight(32); // Ryhmän rivit ovat 32px korkeat
table.setHeaderHeight(48);      // Sarakeotsikkorivi pysyy 48px korkeana
```

Oletusryhmän otsikkokorkeus vastaa oletusotsikkokorkeutta.

## Ryhmien tyylittely CSS-osilla {#styling-groups-with-css-parts}

Ryhmäotsikot ja sarakkeet tarjoavat CSS-osia tyylittämistä varten `::part()`-metodilla. Seuraavat osat ovat saatavilla:

| Osa | Kuvasto |
| --- | --- |
| `cell-group-{ID}` | Ryhmäotsikon solu, joka on kohdennettu ryhmätunnuksen perusteella |
| `cell-group-depth-{N}` | Ryhmäotsikon solu, joka on kohdennettu syvyyden mukaan (`0` = päättasolla, `1` = toisen tason, jne.) |
| `cell-column-{ID}` | Kaikki solut (otsikko ja runko) tietyn sarake ID:n mukaisesti |
| `cell-content-group-{ID}` | Sisältökääre ryhmäotsikossa |
| `cell-label-group-{ID}` | Tunniste ryhmäotsikossa |

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablestyledcolumngroups?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableStyledColumnGroupsView.java'
cssURL='/css/table/tablestyledcolumngroups.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

### Tyylittely ryhmätunnuksen mukaan {#styling-by-group-id}

Käytä ryhmätunnusta kohdentamaan tiettyjä ryhmiä ainutlaatuisten värien tai typografian avulla.

```css
dwc-table::part(cell-group-catalog) {
  background: var(--dwc-color-primary-30);
  color: var(--dwc-color-primary-text-30);
  font-weight: 600;
}

dwc-table::part(cell-group-bio) {
  background: var(--dwc-color-primary-40);
  color: var(--dwc-color-primary-text-40);
}

dwc-table::part(cell-column-title) {
  font-weight: 600;
}
```

### Tyylittely syvyyden mukaan {#styling-by-depth}

Syvyyspohjaiset osat ovat hyödyllisiä, kun haluat soveltaa yhdenmukaista tyylitystä kaikkiin ryhmiin tietyllä pesimistasolla ilman, että kohtaat kutakin ryhmää erikseen.

```css
/* Tyylittele kaikki pääryhmät */
dwc-table::part(cell-group-depth-0) {
  background: var(--dwc-color-primary-30);
  font-weight: 700;
}

/* Tyylittele kaikki toisen tason ryhmät */
dwc-table::part(cell-group-depth-1) {
  background: var(--dwc-color-primary-40);
}
```

## Piilotetut sarakkeet {#hidden-columns}

Piilotetut sarakkeet on poistettu visuaalisesta järjestyksestä ja otsikkokokonaisuudesta. Jos ryhmä sisältää yhdistelmän näkyviä ja piilotettuja sarakkeita, vain näkyvät sarakkeet näkyvät, ja ryhmän `colspan` säätyy vastaavasti. Jos jokainen sarake ryhmässä on piilotettu, ryhmäotsikkoa ei renderöidä lainkaan.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablehiddencolumngroups?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableHiddenColumnGroupsView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->
