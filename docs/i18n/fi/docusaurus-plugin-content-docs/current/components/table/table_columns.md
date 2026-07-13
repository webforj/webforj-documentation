---
sidebar_position: 5
title: Columns
slug: columns
description: >-
  Define Table columns with labels, value providers, visibility, navigability,
  sizing, and user-interaction events.
_i18n_hash: 5ca9d9959c258db42780e52dad8c463d
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

`Table`-luokka käyttää sarakeinstansseja määrittämään ja mukauttamaan, miten dataa näytetään. Sarakkeet hallitsevat, mitä dataa näytetään, miltä se näyttää ja miten käyttäjät voivat olla vuorovaikutuksessa sen kanssa. Tämä sivu käsittelee sarakkeen identiteettiä, esitystä, kokoa, käyttäjävuorovaikutuksia ja niihin liittyviä tapahtumia.

## Sarakkeen identiteetti {#column-identity}

Sarakkeen identiteetti määrittää, miten se tunnistetaan `Table`:ssä. Tämä sisältää sen kuvaston, siihen liittyvän arvon ja sen, onko se näkyvä tai navigoitava.

### Kuvasto {#label}

Sarakkeen kuvasto on sen julkinen tunniste, joka auttaa selventämään näytettävää dataa.

Käytä `setLabel()` asettaaksesi tai muokataksesi kuvastoa.

:::tip
Oletusarvoisesti kuvasto on sama kuin sarakkeen ID.
:::

```java
table.addColumn("Tuote ID", Product::getProductId).setLabel("ID");
```

### Arvontuottajat {#value-providers}

Arvontuottaja on funktio, joka vastaa raakadatassa olevien tietojen muuntamisesta näytettäväksi sopivaan muotoon tietyssä sarakkeessa. Funktio, jonka määrittelet, ottaa rividatan tyypin (T) instanssin ja palauttaa arvon, joka näytetään kyseisessä sarakkeessa asianomaiselle riville.

Aseta arvontuottaja sarakkeelle käyttämällä yhtä `addColumn()`-menetelmistä `Table`-komponentissa.

Seuraavassa koodipätkässä sarake yrittää käyttää dataa JSON-objektista ja renderöi sen vain, jos data ei ole null.

```java
List<String> columnsList = List.of("urheilija", "ikä", "maa", "vuosi", "laji", "kulta", "hopea", "pronssi", "yhteensä");
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

On mahdollista asettaa sarakkeen näkyvyys, mikä määrittää, näytetäänkö se `Table`:ssä vai ei. Tämä voi olla hyödyllistä, kun halutaan esimerkiksi päättää, näytetäänkö arkaluonteista tietoa.

```java
table.addColumn("Luottokortti", Customer::getCreditCardNumber).setHidden(true);
```

### Navigoitava {#navigable}

Navigoitava-attribuutti määrittää, voivatko käyttäjät olla vuorovaikutuksessa sarakkeen kanssa navigoinnin aikana. Asettaessaan `setSuppressNavigable()` arvoksi true rajoittaa käyttäjän vuorovaikutusta sarakkeen kanssa, tarjoten vain lukuoikeuden.

```java
table.addColumn("Vain luku -sarake", Product::getDescription).setSuppressNavigable(true);
```

## Asettelu ja muotoilu {#layout-and-formatting}

Kun sarakkeen identiteetti on määritetty, seuraava vaihe on hallita, miltä sen sisältö näyttää käyttäjille. Asetteluvalinnat, kuten tasaus ja kiinnitys, määrittävät, missä data sijaitsee ja miten se pysyy näkyvänä työskennellessäsi `Table`:n kanssa.

### Tasaus {#alignment}

Sarakkeen tasasuuntaaminen antaa sinun luoda järjestettyjä taulukoita, mikä voi auttaa käyttäjiä erottamaan eri osiot `Table`:ssä.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumnalignment'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnAlignmentView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

`Table`-komponentti tukee kolmea tasausvaihtoehtoa:

- `Column.Alignment.LEFT`: Sopii tekstuaalisiin tai kuvastollisiin tietoihin, joissa vasemmanpuoleinen virtaus on intuitiivista. Hyödyllinen, kun korostetaan sisällön aloituspistettä.
- `Column.Alignment.CENTER`: Keskitetyt sarakkeet ovat ihanteellisia lyhyille arvoille, kuten merkkiavaimelle, tilalle tai muille asioille, joilla on tasapainoinen esitys.
- `Column.Alignment.RIGHT`: Vasemmanpuoleisesti tasattua saraketta kannattaa käyttää numeerisille arvoille, joita on hyödyllistä skannailla nopeasti, kuten päivämääriä, määriä ja prosentteja.

Edellisessä esimerkissä viimeinen sarake, jota kutsutaan `Cost`, on tasaus oikealle saadakseen selvemmän visuaalisen erottuvuuden.

### Kiinnitys {#pinning}

Sarakkeen kiinnitys on ominaisuus, joka mahdollistaa käyttäjien kiinnittämisen tai "pinnittämisen" sarakkeen tietyn puolen `Table`:ssä. Tämä on hyödyllistä, kun tietyt sarakkeet, kuten tunnisteet tai oleellinen tieto, on pidettävä näkyvänä, kun vieritään vaakasuoraan taulukon läpi.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumnpinning'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

Sarakkeen kiinnittämiseen on kolme käytettävissä olevaa suuntaa:

- `PinDirection.LEFT`: Kiinnittää sarakkeen vasemmalle puolelle.
- `PinDirection.RIGHT`: Kiinnittää sarakkeen oikealle puolelle.
- `PinDirection.AUTO`: Sarake näkyy syöttöjärjestyksen mukaan.

Kiinnitys voidaan asettaa ohjelmallisesti, jolloin voit muuttaa kiinnityssuuntaa käyttäjävuorovaikutuksen tai sovelluksen logiikan perusteella.

## Sarakkeen kokoaminen <DocChip chip='since' label='25.03' /> {#column-sizing}

### Kiinteä leveys {#fixed-width}

Aseta sarakkeelle tarkka leveys käyttämällä `setWidth()`-metodia, jossa määritetään haluttu leveys pikseleinä:

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

Leveysominaisuus määrittää halutun alkuperäisen leveyden sarakkeelle. Miten tätä leveyttä käytetään riippuu muista ominaisuuksista ja sarakkeen tyypistä:

- **Normaalit sarakkeet**: Kun vain leveys on asetettu, sarake renderöidään määritettyyn leveyteen, mutta se voi kutistua suhteellisesti, kun säiliö on liian pieni. Alkuperäinen leveys toimii haluttuna leveytenä, mutta ilman nimenomaisia minimirajoituksia sarake voi renderöityä pienemmäksi kuin asetettu leveys.
- [**Kiinnitetyt sarakkeet**](#pinning): Pitää aina tarkkaa leveyttään, ei osallistu responsiiviseen kutistumiseen.
- [**Flex-sarakkeet**](#flex-sizing): Leveyden asettaminen on yhteensopimatonta flexin kanssa. Käytä joko leveyttä (kiinteä) tai flexiä (suhteellinen), ei kumpaakaan.

Jos sitä ei määritetä, sarake käyttää arvioitua leveyttä, joka perustuu sisältöanalyysiin ensimmäisistä riveistä.

```java
// Hanki nykyinen leveys
float currentWidth = column.getWidth();
```

### Minimileveys {#minimum-width}

`setMinWidth()`-metodi mahdollistaa sarakkeen minimileveyden määrittämisen. Jos minimileveyttä ei anneta, `Table` laskee minimileveyden sarakkeen sisällön perusteella.

```java
table.addColumn("Hinta", Product::getPrice).setMinWidth(100f);
```

Väliarvoa edustaa minimileveys pikseleinä.

Minimileveysominaisuus hallitsee sarakkeen pienintä mahdollista leveyttä:

- **Normaalit sarakkeet**: Kun vain minimileveys on asennettuna, sarake käyttää minimileveyttä sekä halutuna että minimileveytenä. Kun leveys + minimileveys on asetettu, sarake voi kutistua leveydestä minimileveyteen, mutta ei enempää.
- [**Kiinnitetyt sarakkeet**](#pinning): Jos vain minimileveys on asetettu (ei leveyksiä), se muuttuu kiinteäksi leveydeltä.
- [**Flex-sarakkeet**](#flex-sizing): Estää saraketta kutistumasta alle tämän leveyden, vaikka säiliön tila olisi rajoitettu.

```java
// Hanki nykyinen minimileveys
float minWidth = column.getMinWidth();
```

### Maksimileveys {#maximum-width}

`setMaxWidth()`-metodi rajoittaa, kuinka leveäksi sarake voi kasvaa, estäen pitkän sisällön omaavien sarakkeiden tulemasta liian leveiksi ja vaikuttamasta luettavuuteen:

```java
table.addColumn("Kuvaus", Product::getDescription)
  .setMinWidth(100f)
  .setMaxWidth(300f);
```

`maxWidth`-ominaisuus rajoittaa sarakkeen kasvua kaikille saraketyypeille ja sitä ei ylitetä riippumatta sisällöstä, säiliön koosta tai flex-asetuksista.

```java
// Hanki nykyinen maksimi leveys
float maxWidth = column.getMaxWidth();
```

### Flex-kokoaminen {#flex-sizing}

`setFlex()`-metodi mahdollistaa suhteellisen sarakekoon, jolloin sarakkeet jakavat saatavilla olevan tilan kiinteän leveyden sarakkeiden jälkeen:

```java
// Otsikkosarake saa kaksinkertaisen tilan Artist-sarakkeeseen verrattuna
table.addColumn("Otsikko", Product::getTitle).setFlex(2f);
table.addColumn("Artist", Product::getArtist).setFlex(1f);
```

Käytön avainkäytännöt:

- **Flex-arvo**: Määrittää saatavilla olevan tilan osuuden. Sarake, jolla on flex=2, saa kaksinkertaisen tilan sarakkeeseen, jolla on flex=1.
- **Yhteensopimaton leveyden kanssa**: Ei voi käyttää yhdessä leveyden ominaisuuden kanssa. Kun flex on suurempi kuin nolla, se ottaa vaikutuksen leveyden asetuksesta.
- **Kunnioittaa rajoitteita**: Toimii minimileveys-/maksimileveysrajoitteiden kanssa. Ilman minimileveyttä flex-sarakkeet voivat kutistua nollaan.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumnflexsizing'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnFlexSizingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='550px'
/>
<!-- vale on -->

:::info Leveys vs Flex
Leveys ja flexominaisuudet ovat keskenään poissulkevia. Yhden asettaminen tyhjentää automaattisesti toisen. Käytä leveyttä tarkan hallinnan vuoksi tai flexiä responsiivisen käyttäytymisen vuoksi.
:::

### Automaattinen kokoaminen {#automatic-sizing}

Manuaalisten leveys- ja flex-asetusten lisäksi sarakkeet voidaan myös automaattisesti koosta. Automaattinen kokoaminen antaa `Table`:n määrittää optimaalisen leveyden joko analysoimalla sisältöä tai jakamalla tilaa suhteellisesti.

#### Sisältöön perustuva automaattinen kokoaminen {#content-based-auto-sizing}

Sarakkeita voidaan automaattisesti koosta niiden sisällön perusteella. `Table` analysoi kunkin sarakkeen datan ja laskee optimaalisen leveyden, jolla sisältö näkyy ilman katkaisua.

```java
// Automaattisesti kokoa kaikki sarakkeet sisällön mukaan
table.setColumnsToAutoSize().thenAccept(c -> {
  // Kokoaminen valmis - sarakkeet sopii nyt sisältöönsä
});

// Automaattisesti kokoa tietty sarake
table.setColumnToAutoSize("kuvaus");
```

#### Suhteellinen automaattinen sovitus {#proportional-auto-fit}

Jaa kaikki sarakkeet suhteellisesti käytettävissä olevan `Table`:n leveyden mukaan. Tämä toimenpide asettaa jokaisen sarakkeen flex=1, jolloin ne jakavat koko `Table`:n leveyden tasan, riippumatta niiden sisällön pituudesta. Sarakkeet laajenevat tai kutistuvat täyttämään tarkat `Table`:n mitat ilman jäljelle jäävää tilaa.

```java
// Sopii sarakkeet taulukon leveyteen (vastaa flex=1 asettamista kaikille)
table.setColumnsToAutoFit().thenAccept(ignored -> {
  // Kaikki sarakkeet jakavat nyt tilan tasan
});
```

:::info Asynkroniset toiminnot
Automaattiset kokoamismenetelmät palauttavat `PendingResult<Void>` sen vuoksi, että ne vaativat asiakaspään laskelmia. Käytä `thenAccept()` suorittaaksesi koodia kokoamisen jälkeen. Jos et tarvitse odottaa valmistumista, voit kutsua metodeja ilman `thenAccept()`-asettelua.
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumnautosizing'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnAutoSizingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='550px'
/>
<!-- vale on -->

## Käyttäjävuorovaikutukset <DocChip chip='since' label='25.03' /> {#user-interactions}

### Sarakkeen koon muuttaminen {#column-resizing}

Sarakkeen koon muuttaminen antaa käyttäjille mahdollisuuden hallita, kuinka paljon tilaa kukin sarake vie vetämällä sarakkeen rajoja.

Voit hallita koon muuttamiskäyttäytymistä yksittäisillä sarakkeilla taulukkoa rakentaessasi:

```java
// Ota käyttäjäresize käyttöön tälle sarakkeelle
table.addColumn("Otsikko", Product::getTitle).setResizable(true);

// Poista resize käytöstä
table.addColumn("ID", Product::getId).setResizable(false);

// Tarkista nykytila
boolean canResize = column.isResizable();
```

Taulukoissa, joissa haluat yhtenäisen käyttäytymisen useiden sarakkeiden kesken, käytä massakokoonpanomenetelmiä:

```java
// Tee kaikista olemassa olevista sarakkeista muokattavia
table.setColumnsToResizable(true);

// Lukitse kaikilta olemassa olevilta sarakkeilta resize
table.setColumnsToResizable(false);
```

### Sarakkeen järjestäminen {#column-reordering}

Sarakkeen järjestäminen mahdollistaa käyttäjille sarakkeiden vetämisen ja pudottamisen heidän suosimiensa järjestykseen, personoiden `Table`-asettelua heidän työnkulkuunsa.

Määritä sarakkeiden liikuttamisluvat, kun asetat taulukkoasi:

```java
// Salli käyttäjien liikuttaa tätä saraketta
table.addColumn("Otsikko", Product::getTitle).setMovable(true);

// Estä sarakkeen liikuttaminen (hyödyllinen ID- tai toimintasarakkeille)
table.addColumn("ID", Product::getId).setMovable(false);

// Tarkista nykytila
boolean canMove = column.isMovable();
```

Käytä liikuttamisasetuksia useille sarakkeille samanaikaisesti:

```java
// Ota järjestäminen käyttöön kaikille olemassa oleville sarakkeille
table.setColumnsToMovable(true);

// Poista järjestäminen kaikilta olemassa olevilta sarakkeilta
table.setColumnsToMovable(false);
```

:::note Massatoiminnot
`setColumnsToResizable()` ja `setColumnsToMovable()` menetelmät vaikuttavat vain olemassa oleviin sarakkeisiin kutsuhetkellä. Ne eivät määritä oletusarvoja tuleville sarakkeille.
:::

### Ohjelmallinen sarakkeen liikuttaminen {#programmatic-column-movement}

Vedä ja pudota -toimintojen lisäksi voit myös siirtää sarakkeita ohjelmallisesti indeksin tai ID:n mukaan. Huomaa, että indeksi perustuu vain näkyviin sarakkeisiin; piilotetut sarakkeet on ignoroitu, kun lasketaan sijainteja.

```java
// Siirrä sarake ensimmäiseen paikkaan
table.moveColumn("otsikko", 0);

// Siirrä sarake viimeiseen paikkaan
table.moveColumn(titleColumn, table.getColumns().size() - 1);

// Asynkroninen liikuttaminen palautteen kanssa
table.moveColumn("kuvaus", 2).thenAccept(c -> {
  // Sarake siirretty onnistuneesti
});
```

## Tapahtumien käsittely {#event-handling}

`Table`-komponentti lähettää tapahtumia, kun käyttäjät ovat vuorovaikutuksessa sarakkeiden kanssa, jolloin voit reagoida asettelun muutoksiin ja tallentaa käyttäjäasetuksia.

Tuetut tapahtumat:

- `TableColumnResizeEvent`: Lähetetään, kun käyttäjä muuttaa sarakkeen kokoa vetämällä sen reunaa.
- `TableColumnMoveEvent`: Lähetetään, kun käyttäjä järjestää sarakkeen vetämällä sen otsikkoa.

Voit liittää kuuntelijoita `Table`:een reagoidaksesi, kun käyttäjät muokkaavat taulukon asettelua.

```java
Table<Product> table = new Table<>();

table.onColumnResize(event -> {
  // Käsittele sarakkeen koon muuttamisen tapahtuma
  // Pääsy: event.getColumn(), event.getOldWidth(), event.getNewWidth()
});

table.onColumnMove(event -> {
  // Käsittele sarakkeen liikuttamisen tapahtuma
  // Pääsy: event.getColumn(), event.getOldIndex(), event.getNewIndex()
});
```
