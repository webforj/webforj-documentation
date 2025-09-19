---
sidebar_position: 5
title: Columns
slug: columns
sidebar_class_name: new-content
_i18n_hash: 280a70bb65c45d3b200157f3462d7b10
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

Luokka `Table` käyttää sarakeinstansseja määrittelemään ja mukauttamaan, kuinka tietoja näytetään. Sarakkeet hallitsevat, mitä tietoa näytetään, miltä se näyttää ja kuinka käyttäjät voivat vuorovaikuttaa sen kanssa. Tämä sivu käsittelee sarakkeen identiteettiä, esitystä, kokoa, käyttäjävuorovaikutuksia ja niihin liittyviä tapahtumia.

## Sarakkeen identiteetti {#column-identity}

Sarakkeen identiteetti määrittelee, miten sitä tunnistetaan `Table`-komponentissa. Tämä sisältää sen tunnisteen, arvon, jonka se tarjoaa, ja sen, onko se näkyvä tai navigoitava.

### Tunniste {#label}

Sarakkeen tunniste on sen julkinen tunnistus, joka auttaa selventämään näytettävää tietoa.

Käytä `setLabel()`-metodia asettaaksesi tai muuttaaksesi tunnistetta.

:::tip
Oletusarvoisesti tunniste on sama kuin sarakkeen ID.
:::

```java
table.addColumn("Tuote ID", Product::getProductId).setLabel("ID");
```

### Arvon tarjoajat {#value-providers}

Arvon tarjoaja on funktio, joka on vastuussa raakadatasta kääntämisestä taustalla olevalta tietoaineistolta esityskelpoiseen muotoon tietyssä sarakkeessa. Määrittelemäsi funktio saa syötteenä rivi-datatyyppisen instanssin (T) ja palauttaa arvon, joka näytetään kyseisen rivin mukana olevassa sarakkeessa.

Aseta arvon tarjoaja sarakkeelle käyttämällä yhtä `addColumn()`-menetelmistä `Table`-komponentissa.

Seuraavassa koodinäytteessä sarake yrittää käyttää tietoa JSON-objektista, ja se renderöidään vain, jos tieto ei ole null.

```java
    List<String> columnsList = Arrays.asList("urheilija", "ikä", "maa", "vuosi", "laji", "kulta", "hopea", "pronssi", "yhteensä");

    for (String column : columnsList) {
      table.addColumn(column, (JsonObject person) -> {
        JsonElement element = person.get(column);
        if (!element.isJsonNull()) {
          return element.getAsString();
        }
        return "";
      });
    }
```

### Näkyvyys {#visibility}

Voit asettaa sarakkeen näkyvyyden, mikä määrittää, näytetäänkö se `Table`-komponentissa vai ei. Tämä voi olla hyödyllistä, esimerkiksi herkkien tietojen näyttämisen yhteydessä.

```java
table.addColumn("Luottokortti", Customer::getCreditCardNumber).setHidden(true);
```

### Navigoitava {#navigable}

Navigoitava-attribuutti määrittää, voivatko käyttäjät vuorovaikuttaa sarakkeen kanssa navigoinnin aikana. Asettaessasi `setSuppressNavigable()` arvoksi true rajoitat käyttäjän vuorovaikutusta sarakkeen kanssa, mikä tarjoaa vain luku -kokemuksen.

```java
table.addColumn("Luku Column", Product::getDescription).setSuppressNavigable(true);
```

## Asettelu ja muotoilu {#layout-and-formatting}

Kun sarakkeen identiteetti on määritetty, seuraava askel on hallita, miltä sen sisältö näyttää käyttäjille. Asetteluvaihtoehdot, kuten tasaus ja kiinnitys, määrittävät, missä tiedot sijaitsevat ja kuinka ne pysyvät näkyvissä työskennellessäsi `Table`-yleisessä.

### Tasaus {#alignment}

Sarakkeen tason asettaminen antaa sinun luoda jäsenneltyjä taulukoita, mikä voi auttaa käyttäjiä tunnistamaan eri osiot `Table`-komponentissa.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnAlignmentView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

`Table`-komponentti tukee kolmea tasausvaihtoehtoa:

- `Column.Alignment.LEFT`: Sopii tekstimuotoiselle tai kuvailevalle datalle, jossa vasemmalle suuntautuvan virtauksen ylläpitäminen on intuitiivista. Hyödyllinen, kun halutaan korostaa sisällön alkupistettä.
- `Column.Alignment.CENTER`: Keskitetyt sarakkeet ovat ihanteellisia lyhyille arvoille, kuten merkkikeylle, tilalle tai mille tahansa muulle, joka on tasapainoisesti esitetty.
- `Column.Alignment.RIGHT`: Harkitse oikealle tasattua saraketta numeeristen arvojen osalta, jotka ovat hyödyllisiä nopeassa silmäilyssä, kuten päivämäärät, summat ja prosenttiosuudet.

Edellisessä esimerkissä viimeinen sarake `Hinta` on oikealle tasattu, jotta visuaalinen erottelu olisi selvempää.

### Kiinnitys {#pinning}

Sarakkeen kiinnitys on ominaisuus, joka mahdollistaa käyttäjien kiinnittävän sarakkeen `Table`-komponentin tiettyyn sivuun. Tämä on hyödyllistä, kun tietyt sarakkeet, kuten tunnistimet tai olennaiset tiedot, on pidettävä näkyvissä, kun selaat vaakasuunnassa taulukkoa.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnpinning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

Sarake voidaan kiinnittää kolmeen suuntaan:

- `PinDirection.LEFT`: Kiinnittää sarakkeen vasemmalle puolelle.
- `PinDirection.RIGHT`: Kiinnittää sarakkeen oikealle puolelle.
- `PinDirection.AUTO`: Sarake näkyy lisäysjärjestyksen mukaan.

Kiinnitys voidaan asettaa ohjelmallisesti, jolloin voit vaihtaa kiinnityssuuntaa käyttäjän vuorovaikutusten tai sovelluksen logiikan perusteella.

## Sarakkeen koko <DocChip chip='since' label='25.03' /> {#column-sizing} 

### Kiinteä leveys {#fixed-width}

Aseta tarkka leveys sarakkeelle käyttämällä `setWidth()`-metodia, määrittäen halutun leveyden pikseleinä:

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

Leveysominaisuus määrittää halutun alkuperäisen leveyden sarakkeelle. Miten tätä leveyttä käytetään riippuu muista ominaisuuksista ja sarakkeen tyypistä:

- **Säännölliset sarakkeet**: Vain leveys asetettuna, sarake renderöidään määritetyn leveyden mukaan, mutta se voi kutistua suhteellisesti, kun säiliö on liian pieni. Alkuperäinen leveys toimii haluttuna leveytenä, mutta ilman eksplisiittisiä minimirajoja sarake voi renderöidä pienemmäksi kuin asetettu leveys.
- [**Kiinnitetyille sarakkeille**](#pinning): Ovat aina tarkasti niiden leveyden yllä, eivätkä osallistu responsiiviseen kutistumiseen.
- [**Flex-sarakkeille**](#flex-sizing): Leveyden asettaminen on yhteensopimaton flexin kanssa. Käytä joko leveyttä (kiinteä) tai flexiä (prosentuaalinen), ei molempia.

Jos sitä ei ole määritetty, sarake käyttää arvioitua leveyttään perustuen sisältöanalyysiin ensimmäisistä riveistä.

```java
// Hanki nykyinen leveys
float currentWidth = column.getWidth();
```

### Minimileveys {#minimum-width}

`setMinWidth()`-metodi sallii sinun määrittää sarakkeen minimileveyden. Jos minimileveyttä ei ole annettu, `Table` laskee minimileveyden sarakkeen sisällön perusteella.

```java
table.addColumn("Hinta", Product::getPrice).setMinWidth(100f);
```

Annettu arvo edustaa minimileveyttä pikseleinä.

Minimileveysominaisuus hallitsee pienintä leveyttä, johon sarake voi päästä:

- **Säännölliset sarakkeet**: Vain minimileveys asetettuna, sarake käyttää minimileveyttä sekä haluttuna että minimileveytenä. Leveys + minimileveys -asetuksilla sarake voi kutistua leveydeltään minimileveyteen, mutta ei sen alle.
- [**Kiinnitetyille sarakkeille**](#pinning): Jos vain minimileveys on asetettu (ei leveyttä), siitä tulee kiinteä leveys.
- [**Flex-sarakkeille**](#flex-sizing): Estää saraketta kutistumasta tämän leveyden alapuolelle, vaikka säiliön tila olisi rajallinen.

```java
// Hanki nykyinen minimileveys
float minWidth = column.getMinWidth();
```

### Maksimileveys {#maximum-width}

`setMaxWidth()`-metodi rajoittaa, kuinka leveäksi sarake voi kasvaa, estäen pitkiä sisältöjä sisältävien sarakkeiden kasvamasta liian leveiksi ja vaikuttamasta luettavuuteen:

```java
table.addColumn("Kuvaus", Product::getDescription)
    .setMinWidth(100f)
    .setMaxWidth(300f);
```

`maxWidth`-ominaisuus rajoittaa sarakkeen kasvua kaikentyyppisille sarakkeille ja sitä ei koskaan ylitetä, riippumatta sisällöstä, säiliön koosta tai flex-asetuksista.

```java
// Hanki nykyinen maksimileveys
float maxWidth = column.getMaxWidth();
```

### Flex-kokoaminen {#flex-sizing}

`setFlex()`-metodi mahdollistaa suhteellisen sarakkeen koon, jolloin sarakkeet jakavat käytettävissä olevan tilan kiinteäleveydisten sarakkeiden jälkeen:

```java
// Otsikkosarake saa kaksinkertaisen tilan Taiteilija-sarakkeeseen verrattuna
table.addColumn("Otsikko", Product::getTitle).setFlex(2f);
table.addColumn("Taiteilija", Product::getArtist).setFlex(1f);
```

Keskeiset flex-käyttäytymiset:

- **Flex-arvo**: Määrittää käytettävissä olevan tilan osuuden. Sarake, jonka flex=2, saa kaksinkertaisen tilan sarakkeeseen, jonka flex=1.
- **Yhteensopimaton leveyden kanssa**: Ei voi käyttää samanaikaisesti leveyden ominaisuuden kanssa. Kun flex on suurempi kuin nolla, se vaikuttaa leveyden asetukseen.
- **Kunnioittaa rajoja**: Toimii minimileveys/maksimileveys-rajoitusten kanssa. Ilman minimileveyttä flex-sarakkeet voivat kutistua nollaan.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnflexsizing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnFlexSizingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='550px'
/>
<!-- vale on -->

:::info Leveys vs Flex
Leveys- ja flex-ominaisuudet ovat toisiaan pois sulkevia. Yhden asettaminen tyhjentää automaattisesti toisen. Käytä leveyttä tarkkaan kontrolliin tai flexiä responsiiviseen käyttäytymiseen.
:::

### Automaattinen kokoaminen {#automatic-sizing}

Manuaalisten leveyden ja flex-asetusten lisäksi sarakkeet voidaan myös mitoittaa automaattisesti. Automaattinen kokoaminen mahdollistaa `Table`-komponentin selvittää optimaalisen leveyden joko analysoimalla sisältöä tai jakamalla tilaa suhteellisesti.

#### Sisältöön perustuva automaattinen kokoaminen {#content-based-auto-sizing}

Sarakkeet mitoitetaaan automaattisesti niiden sisällön perusteella. `Table` analysoi jokaisen sarakkeen dataa ja laskee optimaalisen leveyden näyttää sisältö katkaisematta.

```java
// Automaattinen kokoaminen kaikille sarakkeille sisällön mukaan
table.setColumnsToAutoSize().thenAccept(c -> {
    // Kokoaminen valmis - sarakkeet nyt sopivat sisältöönsä
});

// Automaattinen kokoaminen tietyssä sarakkeessa
table.setColumnToAutoSize("kuvaus");
```

#### Suhteellinen automaattinen sovitus {#proportional-auto-fit}

Jaa kaikki sarakkeet suhteellisesti käytettävissä olevan `Table`-leveyden yli. Tämä toimenpide asettaa jokaisen sarakkeen flex=1, jolloin ne jakavat koko `Table`-leveyden tasan, riippumatta niiden sisällön pituudesta. Sarakkeet laajenevat tai supistuvat täyttämään täsmälliset `Table`-mitat ilman jäävää tilaa.

```java
// Sopii sarakkeet taulukon leveyteen (vastaa flex=1 asettamista kaikille)
table.setColumnsToAutoFit().thenAccept(ignored -> {
    // Kaikki sarakkeet jakavat nyt tilan tasaisesti
});
```

:::info Asynkroniset toiminnot
Automaattiset kokoamismetodit palauttavat `PendingResult<Void>` -koska ne vaativat asiakaspuolen laskelmia. Käytä `thenAccept()` suorittaaksesi koodia kokoamisen jälkeen. Jos et tarvitse odottaa valmistumista, voit kutsua metodeja ilman `thenAccept()`.
:::

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnautosizing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnAutoSizingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='550px'
/>
<!-- vale on -->

## Käyttäjävuorovaikutukset <DocChip chip='since' label='25.03' /> {#user-interactions}

### Sarakkeen koon muuttaminen {#column-resizing}

Sarakkeen koon muuttaminen antaa käyttäjille hallinnan siitä, kuinka paljon tilaa kukin sarake vie vetämällä sarakekappaleiden rajoja.

Voit hallita koon muuttamisen käyttäytymistä yksittäisille sarakkeille taulukkoa rakentaessasi:

```java
// Ota käyttöön käyttäjän koon muuttaminen tälle sarakkeelle
table.addColumn("Otsikko", Product::getTitle).setResizable(true);

// Poista koon muuttaminen käytöstä
table.addColumn("ID", Product::getId).setResizable(false);

// Tarkista nykyinen tila
boolean canResize = column.isResizable();
```

Taulukoissa, joissa haluat johdonmukaisen käyttäytymisen useiden sarakkeiden kesken, käytä joukkokokoonpano-menetelmiä:

```java
// Tee kaikki olemassa olevat sarakkeet koon muutettaviksi
table.setColumnsToResizable(true);

// Lukitse kaikki olemassa olevat sarakkeet koon muuttamisen ulkopuolelle
table.setColumnsToResizable(false);
```

### Sarakkeen uudelleenjärjestäminen {#column-reordering}

Sarakkeen uudelleenjärjestäminen mahdollistaa käyttäjien vetää ja pudottaa sarakkeita haluamaansa järjestykseen, henkilökohtaisesti muokaten `Table`-asettelua heidän työnkululleen.

Määritä sarakkeen siirtomahdollisuudet, kun asetat taulukkoasi:

```java
// Salli käyttäjien siirtää tätä saraketta
table.addColumn("Otsikko", Product::getTitle).setMovable(true);

// Estä sarakkeen siirto (hyödyllinen ID:tä tai toimintasarjoja varten)
table.addColumn("ID", Product::getId).setMovable(false);

// Tarkista nykyinen tila
boolean canMove = column.isMovable();
```

Voit soveltaa liikkuvuusasetuksia useille sarakkeille samanaikaisesti:

```java
// Ota käyttöön uudelleenjärjestäminen kaikille olemassa oleville sarakkeille
table.setColumnsToMovable(true);

// Poista uudelleenjärjestäminen käytöstä kaikille olemassa oleville sarakkeille  
table.setColumnsToMovable(false);
```

:::note Joukkotoiminnot
`setColumnsToResizable()` ja `setColumnsToMovable()` -metodit vaikuttavat vain olemassa oleviin sarakkeisiin kutsuhetkellä. Ne eivät aseta oletuksia tuleville sarakkeille.
:::

### Ohjelmallinen sarakkeen siirto {#programmatic-column-movement}

Lisäksi vedä ja pudota-toimintojen ohella voit myös siirtää sarakkeita ohjelmallisesti indeksin tai ID:n perusteella. Huomaa, että indeksi perustuu vain näkyviin sarakkeisiin; piilotettuja sarakkeita ei oteta huomioon asemien laskemiseen.

```java
// Siirrä sarake ensimmäiseen sijaintiin
table.moveColumn("otsikko", 0);

// Siirrä sarake viimeiseen sijaintiin
table.moveColumn(titleColumn, table.getColumns().size() - 1);

// Asynkroninen liike palautteen kanssa
table.moveColumn("kuvaus", 2).thenAccept(c -> {
    // Sarake siirretty onnistuneesti
});
```

## Tapahtumankäsittely {#event-handling}

`Table`-komponentti lähettää tapahtumia, kun käyttäjät vuorovaikuttavat sarakkeiden kanssa, mikä mahdollistaa reagoida asettelun muutoksiin ja tallentaa käyttäjien mieltymyksiä.

Tuetut tapahtumat:

- `TableColumnResizeEvent`: Lähetetään, kun käyttäjä muuttaa sarakkeen kokoa vetämällä sen reunaa.
- `TableColumnMoveEvent`: Lähetetään, kun käyttäjä uudelleenjärjestää sarakkeen vetämällä sen otsikkoa.

Voit liittää kuuntelijoita `Table`-komponenttiin vastataksesi käyttäjien muokkaavia taulukon asettelua.

```java
Table<Product> table = new Table<>();

table.onColumnResize(event -> {
  // Käsittele sarakkeen koon muuttamis tapahtuma
  // Pääsy: event.getColumn(), event.getOldWidth(), event.getNewWidth()
});

table.onColumnMove(event -> {
  // Käsittele sarakkeen siirtotapahtuma  
  // Pääsy: event.getColumn(), event.getOldIndex(), event.getNewIndex()
});
```
