---
sidebar_position: 5
title: Columns
slug: columns
sidebar_class_name: new-content
_i18n_hash: 6be8663364f0b321c603eb8b870cc104
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

`Table`-luokka käyttää sarakeinstansseja määrittämään ja mukauttamaan, kuinka tietoja näytetään. Sarakkeet hallitsevat, mitä tietoa näytetään, miltä se näyttää, ja miten käyttäjät voivat olla vuorovaikutuksessa sen kanssa. Tässä sivussa käsitellään sarakkeen identiteettiä, esitystä, kokoa, käyttäjän vuorovaikutuksia ja niihin liittyviä tapahtumia.

## Sarakkeen identiteetti {#column-identity}

Sarakkeen identiteetti määrittelee, miten se tunnistetaan `Table`:ssa. Tähän sisältyvät sen etiketti, sen tarjoama arvo ja onko se näkyvissä tai navigoitavissa.

### Etiketti {#label}

Sarakkeen etiketti on sen julkinen tunnistaja, joka auttaa selventämään näytettäviä tietoja.  

Käytä `setLabel()`-metodia asettaaksesi tai muuttaaksesi etikettiä.

:::tip
Oletusarvoisesti etiketti on sama kuin sarake-ID.
:::

```java
table.addColumn("Tuote-ID", Product::getProductId).setLabel("ID");
```

### Arvon tarjoajat {#value-providers}

Arvon tarjoaja on funktio, joka vastaa raakadatasta saatavan tiedon muuntamisesta tietyssä sarakkeessa näytettävään muotoon. Funktio, jonka määrittelet, ottaa vastaan rivin tietotyypin instanssin (T) ja palauttaa arvon, jota esitetään liittyvässä sarakkeessa kyseiselle riville.

Aseta arvon tarjoaja sarakkeelle käyttäen yhtä `addColumn()`-menetelmistä `Table`-komponentissa.

Seuraavassa koodiesimerkissä sarake yrittää ottaa dataa JSON-objektista, ja se renderöidään vain, jos data ei ole null.

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

On mahdollista asettaa sarakkeen näkyvyys, mikä määrittää, näytetäänkö se `Table`:ssa vai ei. Tämä voi olla hyödyllistä, kun halutaan esimerkiksi päättää, näytetäänkö arkaluontoista tietoa. 

```java
table.addColumn("Luottokortti", Customer::getCreditCardNumber).setHidden(true);
```

### Navigoitava {#navigable}

Navigoitava-attribuutti määrittää, voivatko käyttäjät olla vuorovaikutuksessa sarakkeen kanssa navigoinnin aikana. Asettamalla `setSuppressNavigable()` arvoksi true rajoitetaan käyttäjien vuorovaikutusta sarakkeen kanssa, mikä tarjoaa vain lukuoikeuden.

```java
table.addColumn("Vain luku -sarakke", Product::getDescription).setSuppressNavigable(true);
```

## Asettelu ja muotoilu {#layout-and-formatting}

Kun sarakkeen identiteetti on määritelty, seuraava askel on hallita sitä, miltä sen sisältö näyttää käyttäjille. Asetteluvalinnat, kuten kohdistus ja kiinnitys, määrittävät, mihin tiedot istuvat ja miten ne pysyvät näkyvissä työskennellessäsi `Table`:n kanssa.

### Kohdistus {#alignment}

Sarakkeen kohdistuksen asettaminen antaa sinun luoda järjestäytyneitä taulukoita, mikä voi auttaa käyttäjiä tunnistamaan eri osiot `Table`:ssa.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnAlignmentView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

`Table`-komponentti tukee kolmea kohdistusvaihtoehtoa:

- `Column.Alignment.LEFT`: Sopii tekstille tai kuvaileville tiedoille, joissa vasemmalle suuntautuvan vuoron pitäminen on intuitiivista. Hyödyllinen, kun halutaan korostaa sisällön alkupistettä.
- `Column.Alignment.CENTER`: Keskikohdistetut sarakkeet ovat ihanteellisia lyhyille arvoille, kuten merkkijonon avaimelle, tilalle tai muille tasapainoisille esityksille.
- `Column.Alignment.RIGHT`: Oikealle kohdistettua saraketta kannattaa käyttää numeeristen arvojen yhteydessä, joita on hyödyllistä selata nopeasti, kuten päivämäärät, määrät ja prosenttiosuudet.

Edellisessä esimerkissä viimeinen sarake `Kustannus` on oikealle kohdistettu tarjoamaan selkeämpi visuaalinen erottelu.

### Kiinnitys {#pinning}

Sarakkeen kiinnitys on ominaisuus, joka mahdollistaa käyttäjille sarakkeen kiinnittämisen `Table`:n tiettyyn reunaan. Tämä on hyödyllistä, kun tietyt sarakkeet, kuten tunnisteet tai oleellinen tieto, on pidettävä näkyvissä, kun vieritetään vaakasuunnassa taulukossa.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnpinning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

Sarakkeiden kiinnittämiseen on kolme mahdollista suuntaa:

- `PinDirection.LEFT`: Kiinnittää sarakkeen vasemmalle puolelle.
- `PinDirection.RIGHT`: Kiinnittää sarakkeen oikealle puolelle.
- `PinDirection.AUTO`: Sarake näkyy syöttöjärjestyksen perusteella.

Kiinnityksen voi asettaa ohjelmallisesti, joten voit muuttaa kiinnityssuuntaa käyttäjän vuorovaikutusten tai sovelluksen logiikan mukaan.

## Sarakkeen koko <DocChip chip='since' label='25.03' /> {#column-sizing} 

### Kiinteä leveys {#fixed-width}

Aseta tarkka leveys sarakkeelle käytäen `setWidth()`-metodia, määrittäen halutun leveyden pikseleinä:

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

Leveysominaisuus määrittää sarakkeen halutun alkuperäisen leveyden. Miten tätä leveyttä käytetään, riippuu muista ominaisuuksista ja saraketyypistä:

- **Tavalliset sarakkeet**: Kun vain leveys on asetettu, sarake renderöityy määritettyyn leveyteen, mutta voi kutistua suhteellisesti, kun säiliö on liian pieni. Alkuperäinen leveys toimii haluttuna leveytenä, mutta ilman eksplisiittisiä minimirajoituksia sarake voi renderöidä pienempänä kuin asetettu leveys.
- [**Kiinnitetyt sarakkeet**](#pinning): Pidät aina tarkkaa leveyttään, eikä se osallistu responsiiviseen kutistumiseen.
- [**Flex-sarakkeet**](#flex-sizing): Leveyden asettaminen ei ole yhteensopivaa flexin kanssa. Käytä joko leveyttä (kiinteä) tai flexiä (proportionaali), ei molempia.

Jos leveys ei ole määritetty, sarake käyttää arvioitua leveyttään perustuen ensimmäisten rivien sisällön analyysiin.

```java
// Hanki nykyinen leveys
float currentWidth = column.getWidth();
```

### Minimileveys {#minimum-width}

`setMinWidth()`-metodi mahdollistaa sarakkeen vähimmäisleveyden määrittämisen. Jos vähimmäisleveyttä ei anneta, `Table` laskee vähimmäisleveyden sarakkeen sisällön mukaan.

```java
table.addColumn("Hinta", Product::getPrice).setMinWidth(100f);
```

Väliarvo, joka annetaan, edustaa vähimmäisleveyttä pikseleinä.

Minimileveysominaisuus hallitsee sarakkeen pienintä mahdollista leveyttä:

- **Tavalliset sarakkeet**: Kun vain minimileveys on asetettu, sarake käyttää minimileveyttä sekä haluttuna että minimileveytenä. Kun leveys + minimileveys on asetettu, sarake voi kutistua leveydestä minimileveyteen, mutta ei alemmas.
- [**Kiinnitetyt sarakkeet**](#pinning): Jos vain minimileveys on asetettu (ei leveyttä), siitä tulee kiinteä leveys.
- [**Flex-sarakkeet**](#flex-sizing): Estää saraketta kutistumasta tämän leveyden alapuolelle, vaikka säiliön tila olisi rajoitettua.

```java
// Hanki nykyinen minimileveys
float minWidth = column.getMinWidth();
```

### Maksimileveys {#maximum-width}

`setMaxWidth()`-metodi rajoittaa, kuinka leveäksi sarake voi kasvaa, estäen liian pitkän sisällön saattamasta sarakkeita liian leveiksi ja heikentämästä luettavuutta:

```java
table.addColumn("Kuvaus", Product::getDescription)
    .setMinWidth(100f)
    .setMaxWidth(300f);
```

`maxWidth`-ominaisuus rajoittaa sarakkeen kasvua kaikille saraketypeille, eikä se koskaan ylitä rajoja riippumatta sisällöstä, säiliön koosta tai flex-asetuksista.

```java
// Hanki nykyinen maksimileveys
float maxWidth = column.getMaxWidth();
```

### Flex-kokoaminen {#flex-sizing}

`setFlex()`-metodi mahdollistaa suhteellisen sarakekoon, jolloin sarakkeet jakavat käytettävissä olevan tilan kiinteiden sarakkeiden varaamisen jälkeen:

```java
// Otsikkosarake saa kaksinkertaisen tilan Artist-sarakkeeseen verrattuna
table.addColumn("Otsikko", Product::getTitle).setFlex(2f);
table.addColumn("Artist", Product::getArtist).setFlex(1f);
```

Tärkeät flex-ominaisuudet:

- **Flex-arvo**: Määrittää saatavilla olevan tilan osuutena. Sarake, jossa on flex=2, saa kaksinkertaisen tilan sarakkeeseen, jossa on flex=1.
- **Yhteensopimattomuus leveyden kanssa**: Ei voi käyttää yhdessä leveyden kanssa. Kun flex on suurempi kuin nolla, se toimii leveyden asettamisen yli.
- **Kunnioittaa rajoituksia**: Toimii minimileveys/maksimileveys -rajoitusten kanssa. Ilman minimileveyttä flex-sarakkeet voivat kutistua nollaan.

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
Leveys- ja flex-ominaisuudet ovat keskenään poissulkevia. Toisen asettaminen tyhjentää automaattisesti toisen. Käytä leveyttä tarkkaa hallintaa varten tai flexiä responsiivista käyttäytymistä varten.
:::

### Automatisoitu kokoaminen {#automatic-sizing}

Manuaalisten leveyden ja flex-asetusten lisäksi sarakkeiden kokoaminen voidaan myös automatisoida. Automaattinen kokoaminen antaa `Table`:n määrittää optimaaliset leveydet joko analysoimalla sisältöä tai jakamalla tilaa suhteellisesti.

#### Sisältöön perustuva automaattinen kokoaminen {#content-based-auto-sizing}

Käynnistä sarakkeiden automaattisen koon säätö niiden sisällön perustalla. `Table` analysoi kunkin sarakkeen tiedot ja laskee optimaalisen leveyden näytettävän sisällön ilman katkaisua.

```java
// Automaattisesti säädä kaikkien sarakkeiden koko sisältöön
table.setColumnsToAutoSize().thenAccept(c -> {
    // Kokoaminen valmis - sarakkeet sopivat nyt sisältöönsä
});

// Automaattisesti säädä tietty sarake
table.setColumnToAutoSize("kuvaus");
```

#### Suhteellinen automaattinen sopivuus {#proportional-auto-fit}

Jaa kaikki sarakkeet suhteellisesti käytettävissä olevan `Table`-leveyden mukaan. Tämä operaatio asettaa kunkin sarakkeen flex=1:äksi, jolloin ne jakavat kokonaisleveyden tasan riippumatta niiden sisällön pituudesta. Sarakkeet laajenevat tai supistuvat täyttämään tarkat `Table`-mitat ilman jäämätöntä tilaa.

```java
// Säädä sarakkeet taulukon leveyteen (vastaa kaikkien flex=1 asettamista)
table.setColumnsToAutoFit().thenAccept(ignored -> {
    // Kaikki sarakkeet jakavat nyt tilan tasan
});
```

:::info Asynkroniset toiminnot
Automaattiseen kokoamiseen liittyvät metodit palauttavat `PendingResult<Void>` koska ne vaativat asiakaspuolen laskentaa. Käytä `thenAccept()` suorittaaksesi koodin koon valmistuttua. Jos et tarvitse odottaa valmistumista, voit kutsua metodeja ilman `thenAccept()`
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

## Käyttäjän vuorovaikutukset <DocChip chip='since' label='25.03' /> {#user-interactions}

### Sarakkeen koon muuttaminen {#column-resizing}

Sarakkeen koon muuttaminen antaa käyttäjille hallintaa sen tilasta, kuinka paljon tilaa kukin sarake vie vetämällä sarakkeen rajoja.

Voit hallita koon muuttamista yksittäisillä sarakkeilla, kun rakennat taulukkoasi:

```java
// Ota käyttöön käyttäjän koon muutos tälle sarakkeelle
table.addColumn("Otsikko", Product::getTitle).setResizable(true);

// Estä koon muuttaminen
table.addColumn("ID", Product::getId).setResizable(false);

// Tarkista nykytila
boolean canResize = column.isResizable();
```

Taulukoissa, joissa haluat konsistenttia käyttäytymistä useiden sarakkeiden välillä, käytä massakokoonpanomenetelmiä:

```java
// Tee kaikki olemassa olevat sarakkeet koon muutettaviksi
table.setColumnsToResizable(true);

// Lukitse kaikki olemassa olevat sarakkeet koon muuttamiselta
table.setColumnsToResizable(false);
```

### Sarakkeen uudelleenjärjestäminen {#column-reordering}

Sarakkeen uudelleenjärjestäminen antaa käyttäjille mahdollisuuden vetää ja pudottaa sarakkeita haluamaansa järjestykseen, mukauttamalla `Table`:n asettelua heidän työnkulkunsa mukaan.

Määritä sarakkeen liikuttelumahdollisuudet, kun asetat taulukkoasi:

```java
// Salli käyttäjien siirtää tätä saraketta
table.addColumn("Otsikko", Product::getTitle).setMovable(true);

// Estä sarakkeen liikuttaminen (hyödyllistä ID- tai toimintasarjoissa)
table.addColumn("ID", Product::getId).setMovable(false);

// Tarkista nykytila
boolean canMove = column.isMovable();
```

Käytä liikutteluasetuksia useille sarakkeille samanaikaisesti:

```java
// Ota käyttöön uudelleenjärjestäminen kaikille olemassa oleville sarakkeille
table.setColumnsToMovable(true);

// Estä uudelleenjärjestäminen kaikille olemassa oleville sarakkeille  
table.setColumnsToMovable(false);
```

:::note Massatoiminnot
`setColumnsToResizable()` ja `setColumnsToMovable()` -menetelmät vaikuttavat vain olemassa oleviin sarakkeisiin kutsuhetkellä. Ne eivät aseta oletuksia tuleville sarakkeille.
:::

### Ohjelmallinen sarakkeen liikuttaminen {#programmatic-column-movement} 

Lisäksi vedon ja pudotuksen lisäksi voit myös siirtää sarakkeita ohjelmallisesti indeksin tai ID:n mukaan. Pidä mielessä, että indeksi perustuu vain näkyviin sarakkeisiin; kaikki piilotetut sarakkeet jätetään huomiotta, kun lasketaan sijainteja.

```java
// Siirrä sarake ensimmäiseen paikkaan
table.moveColumn("otsikko", 0);

// Siirrä sarake viimeiseen paikkaan
table.moveColumn(titleColumn, table.getColumns().size() - 1);

// Asynkroninen liikuttaminen palautteella
table.moveColumn("kuvaus", 2).thenAccept(c -> {
    // Sarake siirretty onnistuneesti
});
```

## Tapahtumien käsittely {#event-handling}

`Table`-komponentti lähettää tapahtumia, kun käyttäjät ovat vuorovaikutuksessa sarakkeiden kanssa, mikä mahdollistaa sinun reagoida asettelun muutoksiin ja tallentaa käyttäjän asetuksia.

Tuetut tapahtumat:

- `TableColumnResizeEvent`: Lähetetään, kun käyttäjä muuttaa sarakkeen kokoa vetämällä sen reunaa.
- `TableColumnMoveEvent`: Lähetetään, kun käyttäjä järjestää sarakkeen uudelleen vetämällä sen otsikkoa.

Voit liittää kuuntelijoita `Table`:n tapahtumien seuraamiseen, kun käyttäjät muuttavat taulukon asettelua.

```java
Table<Product> table = new Table<>();

table.onColumnResize(event -> {
  // Käsittele sarakkeen koon muutostapahtuma
  // Pääsy: event.getColumn(), event.getOldWidth(), event.getNewWidth()
});

table.onColumnMove(event -> {
  // Käsittele sarakkeen liikuttamistapahtuma  
  // Pääsy: event.getColumn(), event.getOldIndex(), event.getNewIndex()
});
```
