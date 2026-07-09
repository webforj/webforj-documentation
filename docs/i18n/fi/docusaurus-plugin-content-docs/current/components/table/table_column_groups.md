---
title: Column Groups
sidebar_position: 7
description: >-
  Group Table columns under shared, nestable multi-row headers using
  ColumnGroup.of and setColumnGroups for complex layouts.
_i18n_hash: 7a0ad76ea48fafddc0aa9965309b48b8
---
<DocChip chip='since' label='25.12' />

Sarakeryhmät antavat sinun järjestää liittyvät sarakkeet jaettujen, usean rivin otsikoiden alle. Ryhmän etiketti kattaa sen lapsisarakkeet, mikä helpottaa käyttäjien skannata ja ymmärtää monimutkaisten taulukoiden rakennetta. Ryhmiä voidaan sisällyttää mihin tahansa syvyyteen, ja `Table` renderöi automaattisesti oikean määrän otsikkorivejä.

## Sarakeryhmien luominen {#creating-column-groups}

Luo ryhmä `ColumnGroup.of()` -tehdasmenetelmällä, ja ketjuta `add()` -kutsuja täyttääksesi sen sarakeviitteillä, muilla ryhmillä tai sekoituksella molempia. Hae ryhmät `Table`-objektille käyttämällä `setColumnGroups()`.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

Kun ryhmät on asetettu, `Table` renderöi usean rivin otsikon, jossa jokainen ryhmän etiketti kattaa sen lapsisarakkeet. Sisäkkäisyyden syvyys määrää, kuinka monta otsikkoriviä näkyy. Tasainen ryhmä lisää yhden ylimääräisen rivin, kun taas kaksi tasoista sisäkkäisyyttä lisää kaksi, ja niin edelleen.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablenestedcolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TableNestedColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

<!-- vale Google.OxfordComma = NO -->
Ryhmät voidaan asettaa tai vaihtaa milloin tahansa, ennen tai jälkeen `Table`-renderöinnin. Siirrä `null` tai tyhjää lista `setColumnGroups()` -menetelmään poistaaksesi kaikki ryhmittelyt ja palataksesi yksiriviseen otsikkoon.
<!-- vale Google.OxfordComma = YES -->

```java
// Poista kaikki sarakeryhmät
table.setColumnGroups(null);
```

## Sarakejärjestys {#column-ordering}

Kun ryhmät ovat aktiivisia, visuaalinen sarakejärjestys määräytyy ryhmän puun mukaan eikä sen mukaan, missä järjestyksessä sarakkeet on lisätty `Table`:hen. Puu käydään läpi syvyys ensin, vasemmalta oikealle.

```
Lisätyt sarakkeet:  [A, B, C, D, E]
Ryhmä:            Ryhmä "G1" [C, A], Ryhmä "G2" [E, D]
Visuaalinen järjestys:   C, A, E, D, B
```

Ryhmittämättömät sarakkeet, joita ei käytetä missään ryhmässä, eivät ole piilotettuja. Ne näkyvät omassa luonnollisessa sijainnissaan suhteessa ryhmiteltyihin sarakkeisiin alkuperäisen lisäämisjärjestyksen mukaan.

Esimerkissä `Number` näkyy ensin, koska se lisättiin ennen `Title`:a. `Label` näkyy `Genre`:n ja `Cost`:in välissä, koska se lisättiin niiden väliin alkuperäisessä sarakejärjestyksessä:

```
Lisätyt sarakkeet:  [Number, Title, Artist, Genre, Label, Cost]
Ryhmä:            Ryhmä "Music" [Title, Artist, Genre], Ryhmä "Pricing" [Cost]
Visuaalinen järjestys:   Number, Title, Artist, Genre, Label, Cost
```

Seuraava demo havainnollistaa tätä käyttäytymistä. `Number` ja `Label` eivät ole mukana missään ryhmässä, mutta ne säilyttävät luonnolliset asemansa sen mukaan, missä järjestyksessä ne on lisätty `Table`-objektiin.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumngroupordering'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnGroupOrderingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

:::tip Ryhmittämättömien sarakkeiden sijoittamisen hallinta
Jos haluat hallita ryhmittämättömien sarakkeiden sijoittamista, lisää ne ylimmän tason sarakeviittauksina ryhmäpuuhun.
:::

## Sarakkeiden siirtäminen ryhmien sisällä {#column-moving-within-groups}

Kun ryhmät ovat aktiivisia, sarakkeiden vetäminen ja pudottaminen on rajoitettu säilyttämään ryhmän eheys:

- **Ryhmässä**: ryhmän sisällä olevaa saraketta voidaan siirtää vain sen välittömässä vanhempiryhmässä. Sen vetäminen ryhmän ulkopuolelle hylätään, ja sarake palautuu alkuperäiseen paikkaansa.
- **Ryhmittämättömät sarakkeet**: ryhmittämättömät sarakkeet voivat siirtyä vain paikkoihin, jotka ovat muiden ryhmittämättömien sarakkeiden käytössä. Niitä ei voida pudottaa ryhmän keskelle.
- **Ryhmien uudelleenjärjestäminen**: koko ryhmää voidaan vetää siirtämään se uudelleen sen sisaruuksien joukossa samalla sisäkkäisyystasolla.

```
Ryhmä:  Ryhmä "G1" [A, B, C], Ryhmä "G2" [D, E]

Siirrä A paikkaan 2 -> OK (G1:n sisällä, tulos: [B, C, A])
Siirrä A paikkaan 3 -> Hylätty (paikka 3 on G2:n sisällä)
Siirrä D paikkaan 4 -> OK (G2:n sisällä, tulos: [E, D])
Siirrä D paikkaan 1 -> Hylätty (paikka 1 on G1:n sisällä)
```

## Ryhmien kiinnittäminen {#pinning-groups}

Ryhmä voidaan kiinnittää vasemmalle tai oikealle käyttämällä `setPinDirection()`. Kaikki ryhmän sisällä olevat sarakkeet perivät ryhmän kiinnitys suunnan, ja yksittäisten sarakkeiden kiinnitysasetukset ohitetaan ryhmän toimesta.

```java
ColumnGroup idInfo = ColumnGroup.of("id-info", "ID Info")
  .setPinDirection(PinDirection.LEFT)
  .add("number")
  .add("title");

// Sekä "number" että "title" on kiinnitetty vasemmalle,
// riippumatta omista kiinnitysasetuksistaan
```

Ryhmittämättömät sarakkeet säilyttävät omat kiinnityssuuntaustensa sarake määrittelystään.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablepinnedcolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TablePinnedColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

## Ryhmän otsikkokorkeus {#group-header-height}

Ryhmän otsikkorivin korkeutta voidaan hallita itsenäisesti tavallisista sarakeotsikoista käyttämällä `setGroupHeaderHeight()`.

```java
table.setGroupHeaderHeight(32); // Ryhmän rivit ovat 32px korkeita
table.setHeaderHeight(48);      // Sarakeotsikkorivi pysyy 48px korkeana
```

Oletusarvoinen ryhmän otsikkokorkeus vastaa oletusotsikkokorkeutta.

## Ryhmien tyylitys CSS-osien avulla {#styling-groups-with-css-parts}

Ryhmäotsikot ja sarakkeet altistavat CSS-osia tyylitys varten `::part()`. Seuraavat osat ovat käytettävissä:

| Osa | Kuvaus |
| --- | --- |
| `cell-group-{ID}` | Ryhmän otsikkosolu, johon kohdistuu ryhmän ID |
| `cell-group-depth-{N}` | Ryhmän otsikkosolu, johon kohdistuu syvyys (`0` = ylimmän tason, `1` = toisen tason jne.) |
| `cell-column-{ID}` | Kaikki solut (otsikko- ja runkosolut) annetulle sarake-ID:lle |
| `cell-content-group-{ID}` | Sisällön ympäröivä elementti ryhmäotsikossa |
| `cell-label-group-{ID}` | Etiketti ryhmäotsikossa |

<!-- vale off -->
<ComponentDemo
path='/webforj/tablestyledcolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TableStyledColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
  'src/main/frontend/css/table/tablestyledcolumngroups.css',
]}
height='600px'
/>
<!-- vale on -->

### Tyylitys ryhmän ID:n mukaan {#styling-by-group-id}

Käytä ryhmän ID:tä kohdistamaan erityisiä ryhmiä ainutlaatuisilla väreillä tai typografialla.

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

### Tyylitys syvyyden mukaan {#styling-by-depth}

Syvyyteen perustuvat osat ovat hyödyllisiä, kun haluat soveltaa johdonmukaista tyylitystä kaikille tietyllä sisäkkäisyystasolla oleville ryhmille ilman, että kohdistat niitä yksitellen.

```css
/* Tyylitä kaikki ylimmän tason ryhmät */
dwc-table::part(cell-group-depth-0) {
  background: var(--dwc-color-primary-30);
  font-weight: 700;
}

/* Tyylitä kaikki toisen tason ryhmät */
dwc-table::part(cell-group-depth-1) {
  background: var(--dwc-color-primary-40);
}
```

## Piilotetut sarakkeet {#hidden-columns}

Piilotetut sarakkeet on suljettu pois visuaalisesta järjestyksestä ja otsikkorakenteesta. Jos ryhmässä on sekoitus näkyviä ja piilotettuja sarakkeita, vain näkyvät sarakkeet näkyvät ja ryhmän `colspan` säätyy sen mukaisesti. Jos kaikki ryhmässä olevat sarakkeet ovat piilotettuja, ryhmän otsikkoa ei renderöidä lainkaan.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablehiddencolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TableHiddenColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->
