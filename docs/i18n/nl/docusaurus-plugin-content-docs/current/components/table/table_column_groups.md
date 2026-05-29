---
title: Column Groups
sidebar_position: 7
_i18n_hash: f4ab153f6d1e8c4d029e16c3abc41762
---
<DocChip chip='since' label='25.12' />

Kolomgroepen stellen je in staat om gerelateerde kolommen te organiseren onder gedeelde, meerzegelige koppen. Een groepslabel strekt zich uit over zijn kindcolumns, waardoor het voor gebruikers gemakkelijker wordt om de structuur van complexe tabellen te scannen en te begrijpen. Groepen kunnen tot elke diepte genest zijn, en de `Table` rendert automatisch het juiste aantal koprijen.

## Groepen van kolommen maken {#creating-column-groups}

Maak een groep met de `ColumnGroup.of()` fabrieksmethode en keten vervolgens `add()` oproepen om het te vullen met kolomreferenties, andere groepen, of een mix van beide. Pas de groepen toe op een `Table` met `setColumnGroups()`.

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

Wanneer groepen zijn ingesteld, rendert de `Table` een meerzegelige kop waar elk groepslabel zich uitstrekt over de kindcolumns. De nestdiepte bepaalt hoeveel koprijen verschijnen. Een platte groep voegt één extra rij toe, terwijl een twee-niveau nesting er twee toevoegt, en zo verder.

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
Groepen kunnen op elk moment worden ingesteld of gewijzigd, vóór of nadat de `Table` is gerenderd. Geef `null` of een lege lijst door aan `setColumnGroups()` om alle groepering te verwijderen en terug te keren naar een één-rij kop.
<!-- vale Google.OxfordComma = YES -->

```java
// Verwijder alle kolomgroepen
table.setColumnGroups(null);
```

## Kolom volgorde {#column-ordering}

Wanneer groepen actief zijn, wordt de visuele kolomvolgorde bepaald door de groepsstructuur in plaats van de volgorde waarin kolommen aan de `Table` zijn toegevoegd. De structuur wordt diepte-eerst van links naar rechts doorlopen.

```
Toegevoegde kolommen:  [A, B, C, D, E]
Groepen:              Groep "G1" [C, A], Groep "G2" [E, D]
Visuele volgorde:     C, A, E, D, B
```

Ongroepte kolommen, die niet in een groep zijn genoemd, worden niet verborgen. Ze verschijnen op hun natuurlijke positie ten opzichte van de gegroepeerde kolommen, op basis van de volgorde waarin ze oorspronkelijk aan de `Table` zijn toegevoegd.

In dit voorbeeld verschijnt `Nummer` als eerste omdat het vóór `Titel` is toegevoegd. `Label` verschijnt tussen `Genre` en `Kosten` omdat het tussen hen in de oorspronkelijke kolomvolgorde is toegevoegd:

```
Toegevoegde kolommen:  [Nummer, Titel, Artiest, Genre, Label, Kosten]
Groepen:              Groep "Muziek" [Titel, Artiest, Genre], Groep "Prijs" [Kosten]
Visuele volgorde:     Nummer, Titel, Artiest, Genre, Label, Kosten
```

De volgende demo illustreert dit gedrag. `Nummer` en `Label` worden in geen enkele groep genoemd, maar behouden hun natuurlijke posities op basis van de volgorde waarin ze aan de `Table` zijn toegevoegd.

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

:::tip Besturing van een groeploze kolomplaatsing
Om de plaatsing van een groeploze kolom expliciet te controleren, voeg ze als top-level kolomreferenties in de groepsstructuur op.
:::

## Kolom verplaatsen binnen groepen {#column-moving-within-groups}

Wanneer groepen actief zijn, is het slepen en neerzetten van kolommen beperkt om de groepsintegriteit te behouden:

- **Binnen een groep**: een kolom binnen een groep kan alleen binnen zijn directe oudergroep worden verplaatst. Het buiten de groep slepen wordt afgewezen, en de kolom springt terug naar zijn oorspronkelijke positie.
- **Ongroepte kolommen**: een ongroepte kolom kan alleen naar posities worden verplaatst die worden bezet door andere ongroepte kolommen. Het kan niet in het midden van een groep worden neergezet.
- **Herordenen van groepen**: een hele groep kan worden gesleept om deze opnieuw te ordenen onder zijn broers op hetzelfde nestniveau.

```
Groepen:  Groep "G1" [A, B, C], Groep "G2" [D, E]

Verplaats A naar positie 2 -> OK (binnen G1, resultaat: [B, C, A])
Verplaats A naar positie 3 -> Afgewezen (positie 3 bevindt zich binnen G2)
Verplaats D naar positie 4 -> OK (binnen G2, resultaat: [E, D])
Verplaats D naar positie 1 -> Afgewezen (positie 1 bevindt zich binnen G1)
```

## Groepen vastzetten {#pinning-groups}

Een groep kan links of rechts worden vastgezet met `setPinDirection()`. Alle kolommen binnen de groep nemen de pinrichting van de groep over, en individuele kolominstellingen voor vastzetten worden door de groep overschreven.

```java
ColumnGroup idInfo = ColumnGroup.of("id-info", "ID Info")
  .setPinDirection(PinDirection.LEFT)
  .add("number")
  .add("title");

// Zowel "number" als "title" zijn links vastgezet,
// ongeacht hun eigen vastzetinstellingen
```

Ongroepte kolommen behouden hun eigen pinrichting van hun kolomdefinitie.

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

## Hoogte van groepkoppen {#group-header-height}

De hoogte van de rij met groepskoppen kan onafhankelijk worden geregeld van reguliere kolomkoppen met `setGroupHeaderHeight()`.

```java
table.setGroupHeaderHeight(32); // Groeprijen zijn 32px hoog
table.setHeaderHeight(48);      // Kolomkoprij blijft op 48px
```

De standaardhoogte van de groepskop komt overeen met de standaardhoogte van de kop.

## Groepen stijlgeven met CSS-onderdelen {#styling-groups-with-css-parts}

Groepkoppen en kolommen bieden CSS-onderdelen voor styling via `::part()`. De volgende onderdelen zijn beschikbaar:

| Onderdeel | Beschrijving |
| --- | --- |
| `cell-group-{ID}` | Groepkopcel, gericht op groeps-ID |
| `cell-group-depth-{N}` | Groepkopcel, gericht op diepte (`0` = topniveau, `1` = tweede niveau, enz.) |
| `cell-column-{ID}` | Alle cellen (kop en lichaam) voor een gegeven kolom-ID |
| `cell-content-group-{ID}` | Inhoudwrapper binnen een groepkop |
| `cell-label-group-{ID}` | Label binnen een groepkop |

<!-- vale off -->
<ComponentDemo
path='/webforj/tablestyledcolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TableStyledColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
  'src/main/resources/static/css/table/tablestyledcolumngroups.css',
]}
height='600px'
/>
<!-- vale on -->

### Stijl geven op basis van groeps-ID {#styling-by-group-id}

Gebruik de groeps-ID om specifieke groepen aan te spreken met unieke kleuren of typografie.

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

### Stijl geven op basis van diepte {#styling-by-depth}

Diepten gebaseerde onderdelen zijn nuttig wanneer je consistente styling wilt toepassen op alle groepen op een bepaald nestniveau zonder elke groep afzonderlijk aan te spreken.

```css
/* Style alle groepen op topniveau */
dwc-table::part(cell-group-depth-0) {
  background: var(--dwc-color-primary-30);
  font-weight: 700;
}

/* Style alle groepen op tweede niveau */
dwc-table::part(cell-group-depth-1) {
  background: var(--dwc-color-primary-40);
}
```

## Verborgen kolommen {#hidden-columns}

Verborgen kolommen worden uitgesloten van de visuele volgorde en de headerlay-out. Als een groep een mix van zichtbare en verborgen kolommen bevat, verschijnen alleen de zichtbare en de groeps `colspan` past zich dienovereenkomstig aan. Als elke kolom in een groep verborgen is, wordt de groepskop helemaal niet gerenderd.

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
