---
title: Column Groups
sidebar_position: 7
sidebar_class_name: new-content
_i18n_hash: fccec5a60bfd614d344758d3624bc602
---
<DocChip chip='since' label='25.12' />

Kolomgroepen stellen je in staat om gerelateerde kolommen te organiseren onder gedeelde, meerregelige kopteksten. Een groepslabel strekt zich uit over zijn kindkolommen, waardoor het voor gebruikers makkelijker wordt om de structuur van complexe tabellen te scannen en te begrijpen. Groepen kunnen tot elke diepte genest worden, en de `Table` rendert automatisch het juiste aantal koprijen.

## Kolomgroepen maken {#creating-column-groups}

Maak een groep met de `ColumnGroup.of()` fabrieksmethode, en keten vervolgens `add()` aanroepen om het te vullen met kolomreferenties, andere groepen, of een mix van beide. Pas de groepen toe op een `Table` met `setColumnGroups()`.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumngroups?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnGroupsView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

Wanneer groepen zijn ingesteld, rendert de `Table` een meerregelige kop waar elk groepslabel zich uitstrekt over zijn kindkolommen. De diepte van de nesting bepaalt hoeveel koprijen er verschijnen. Een platte groep voegt één extra rij toe, terwijl een genestelde structuur met twee niveaus er twee toevoegt, enzovoorts.

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
Groepen kunnen op elk moment worden ingesteld of gewijzigd, voor of na de rendering van de `Table`. Geef `null` of een lege lijst door aan `setColumnGroups()` om alle groepsinstellingen te verwijderen en terug te keren naar een enkele koprij.
<!-- vale Google.OxfordComma = YES -->

```java
// Verwijder alle kolomgroepen
table.setColumnGroups(null);
```

## Kolomvolgorde {#column-ordering}

Wanneer groepen actief zijn, wordt de visuele kolomvolgorde bepaald door de groepstructuur in plaats van de volgorde waarin kolommen aan de `Table` zijn toegevoegd. De structuur wordt diepte-eerst doorlopen, van links naar rechts.

```
Toegevoegde kolommen:  [A, B, C, D, E]
Groepen:              Groep "G1" [C, A], Groep "G2" [E, D]
Visuele volgorde:     C, A, E, D, B
```

Ongroeperde kolommen, die in geen enkele groep worden verwezen, worden niet verborgen. Ze verschijnen op hun natuurlijke positie ten opzichte van de gegroepeerde kolommen, op basis van de volgorde waarin ze oorspronkelijk aan de `Table` zijn toegevoegd.

In dit voorbeeld verschijnt `Nummer` eerst omdat het voor `Titel` is toegevoegd. `Label` verschijnt tussen `Genre` en `Kosten` omdat het tussen hen in de oorspronkelijke kolomvolgorde is toegevoegd:

```
Toegevoegde kolommen:  [Nummer, Titel, Artiest, Genre, Label, Kosten]
Groepen:              Groep "Muziek" [Titel, Artiest, Genre], Groep "Prijsstelling" [Kosten]
Visuele volgorde:     Nummer, Titel, Artiest, Genre, Label, Kosten
```

De volgende demo illustreert dit gedrag. `Nummer` en `Label` worden in geen enkele groep verwezen, maar behouden hun natuurlijke posities op basis van de volgorde waarin ze aan de `Table` zijn toegevoegd.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumngroupordering?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnGroupOrderingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

:::tip Controle over plaatsing van ongroeperde kolommen
Om de plaatsing van ongroeperde kolommen expliciet te controleren, voeg ze toe als top-level kolomreferenties in de groepstructuur.
:::

## Kolommen verplaatsen binnen groepen {#column-moving-within-groups}

Wanneer groepen actief zijn, is het slepen en neerzetten van kolommen beperkt om de integriteit van de groep te behouden:

- **Binnen een groep**: een kolom binnen een groep kan alleen binnen de directe bovenliggende groep worden verplaatst. Het naar buiten slepen van de groep wordt afgewezen, en de kolom springt terug naar zijn oorspronkelijke positie.
- **Ongroeperde kolommen**: een ongroeperde kolom kan alleen worden verplaatst naar posities die door andere ongroeperde kolommen worden bezet. Het kan niet in het midden van een groep worden neergezet.
- **Groepen herordenen**: een hele groep kan worden gesleept om deze onder zijn broers en zussen op hetzelfde nestingniveau te herordenen.

```
Groepen:  Groep "G1" [A, B, C], Groep "G2" [D, E]

Verplaats A naar positie 2 -> OK (binnen G1, resultaat: [B, C, A])
Verplaats A naar positie 3 -> Afgewezen (positie 3 is binnen G2)
Verplaats D naar positie 4 -> OK (binnen G2, resultaat: [E, D])
Verplaats D naar positie 1 -> Afgewezen (positie 1 is binnen G1)
```

## Groepen vastpinnen {#pinning-groups}

Een groep kan links of rechts worden vastgepind met `setPinDirection()`. Alle kolommen binnen de groep erven de vastpinrichting van de groep, en individuele kolominstellingen voor vastpinnen worden door de groep overschreven.

```java
ColumnGroup idInfo = ColumnGroup.of("id-info", "ID Info")
  .setPinDirection(PinDirection.LEFT)
  .add("nummer")
  .add("titel");

// Zowel "nummer" als "titel" zijn links vastgepind,
// ongeacht hun eigen pin-instellingen
```

Ongroeperde kolommen behouden hun eigen vastpinrichting zoals gedefinieerd in hun kolom.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablepinnedcolumngroups?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TablePinnedColumnGroupsView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

## Hoogte van groepskoppen {#group-header-height}

De hoogte van de groepskoprij kan onafhankelijk van reguliere kolomkoppen worden gecontroleerd met `setGroupHeaderHeight()`.

```java
table.setGroupHeaderHeight(32); // Groepsrijen zijn 32px hoog
table.setHeaderHeight(48);      // Kolomkoprij blijft op 48px
```

De standaardhoogte van de groepskop komt overeen met de standaardhoogte van de kop.

## Groepen stylen met CSS-onderdelen {#styling-groups-with-css-parts}

Groepskoppen en kolommen maken CSS-onderdelen beschikbaar voor styling via `::part()`. De volgende onderdelen zijn beschikbaar:

| Onderdeel | Beschrijving |
| --- | --- |
| `cell-group-{ID}` | Groepskopcel, gericht op groep-ID |
| `cell-group-depth-{N}` | Groepskopcel, gericht op diepte (`0` = topniveau, `1` = tweede niveau, enz.) |
| `cell-column-{ID}` | Alle cellen (kop en lichaam) voor een gegeven kolom-ID |
| `cell-content-group-{ID}` | Inhoudwrapper binnen een groepskop |
| `cell-label-group-{ID}` | Label binnen een groepskop |

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

### Stylen op groeps-ID {#styling-by-group-id}

Gebruik de groeps-ID om specifieke groepen te richten met unieke kleuren of typografie.

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

### Stylen op diepte {#styling-by-depth}

Diepte-gebaseerde onderdelen zijn nuttig wanneer je consistente styling wilt toepassen op alle groepen op een bepaald nestingniveau zonder elke groep afzonderlijk te targeten.

```css
/* Style alle topniveau groepen */
dwc-table::part(cell-group-depth-0) {
  background: var(--dwc-color-primary-30);
  font-weight: 700;
}

/* Style alle tweede niveau groepen */
dwc-table::part(cell-group-depth-1) {
  background: var(--dwc-color-primary-40);
}
```

## Verborgen kolommen {#hidden-columns}

Verborgen kolommen zijn uitgesloten van de visuele volgorde en de koplay-out. Als een groep een mix van zichtbare en verborgen kolommen bevat, verschijnen alleen de zichtbare. De groeps `colspan` past zich dienovereenkomstig aan. Als elke kolom in een groep verborgen is, wordt de groepskop helemaal niet gerenderd.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablehiddencolumngroups?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableHiddenColumnGroupsView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->
