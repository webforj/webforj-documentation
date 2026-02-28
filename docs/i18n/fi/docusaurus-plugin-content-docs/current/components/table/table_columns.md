---
sidebar_position: 5
title: Columns
slug: columns
_i18n_hash: 59dc1d0f2eff7880d818123654e8febf
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

`Table`-luokka käyttää sarakeinstansseja määrittämään ja mukauttamaan, miten dataa näytetään. Sarakkeet hallitsevat, mitä tietoa näytetään, miltä se näyttää ja miten käyttäjät voivat olla vuorovaikutuksessa sen kanssa. Tämä sivu käsittelee sarakkeen identiteettiä, esitystä, kokoa, käyttäjävuorovaikutuksia ja siihen liittyviä tapahtumia.

## Sarakkeen identiteetti {#column-identity}

Sarakkeen identiteetti määrittää, miten se tunnistetaan `Table`-komponentissa. Tämä sisältää sen etiketin, tarjoaman arvon ja sen, onko se näkyvä tai navigoitava.

### Etiketti {#label}

Sarakkeen etiketti on sen julkinen tunniste, joka auttaa selventämään näytettävää tietoa.  

Käytä `setLabel()`-metodia asetaksesi tai muokataksesi etikettiä.

:::tip
Oletusarvoisesti etiketti on sama kuin sarakkeen ID.
:::

```java
table.addColumn("Tuotteen ID", Product::getProductId).setLabel("ID");
```

### Arvon tarjoajat {#value-providers}

Arvon tarjoaja on funktio, joka vastaa raakadatapohjan kääntämisestä muodoksi, joka on sopiva näytettäväksi tietyssä sarakkeessa. Funktio, jonka määrität, ottaa vastaan rividatatyyppisen instanssin (T) ja palauttaa arvon, joka näytetään yhdessä sarakkeessa kyseiselle riville.

Aseta arvon tarjoaja sarakkeelle käyttämällä yhtä `addColumn()`-metodeista `Table`-komponentissa.

Seuraavassa koodinpätkässä sarake yrittää päästä käsiksi dataan JSON-objektista, renderöiden sen vain, jos data ei ole null.

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

On mahdollista määrittää sarakkeen näkyvyys, mikä määrää, näytetäänkö se vai ei `Table`-komponentissa. Tämä voi olla hyödyllistä muun muassa silloin, kun halutaan päättää, näytetäänkö arkaluontoista tietoa.

```java
table.addColumn("Luottokortti", Customer::getCreditCardNumber).setHidden(true);
```

### Navigoitava {#navigable}

Navigoitava-attribuutti määrittää, voivatko käyttäjät olla vuorovaikutuksessa sarakkeen kanssa navigoinnin aikana. Asettamalla `setSuppressNavigable()` arvoksi true rajoitetaan käyttäjien vuorovaikutusta sarakkeen kanssa, tarjoten vain lukuoikeuden.

```java
table.addColumn("Vain luku -sarake", Product::getDescription).setSuppressNavigable(true);
```

## Asettelu ja muotoilu {#layout-and-formatting}

Kun sarakkeen identiteetti on määritetty, seuraava askel on hallita, miten sen sisältö näkyy käyttäjille. Asetteluvalinnat, kuten kohdistus ja kiinnitys, määrittävät, minne data sijoitetaan ja miten se pysyy näkyvissä työskennellessäsi `Table`-komponentin kanssa.

### Kohdistus {#alignment}

Sarakkeen kohdistaminen auttaa luomaan järjestettyjä taulukoita, mikä voi auttaa käyttäjiä tunnistamaan erilaisia osioita `Table`-komponentissa.

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

- `Column.Alignment.LEFT`: Sopii tekstuaaliseen tai kuvailevaan dataan, jossa vasemmalle suuntautuvan alkuverkon ylläpitäminen on luonnollista. Hyödyllinen, kun haluat korostaa sisällön aloituspistettä.
- `Column.Alignment.CENTER`: Keskitetyt sarakkeet sopivat erinomaisesti lyhyille arvoille, kuten kirjainavain, tila tai muulle, jolla on tasapainoinen esitys.
- `Column.Alignment.RIGHT`: Harkitse oikealle kohdistettua saraketta numeeristen arvojen osalta, jotka ovat hyödyllisiä nopeaan silmäilyyn, kuten päivämäärät, summat ja prosentit.

Edellisessä esimerkissä `Kustannus`-sarakkeelle on asetettu oikea kohdistus selvän visuaalisen erottelun aikaansaamiseksi.

### Kiinnitys {#pinning}

Sarakkeen kiinnitys on ominaisuus, joka mahdollistaa käyttäjien kiinnittää tai "kiinnittää" sarakkeen `Table`-komponentin tiettyyn reunaan. Tämä on hyödyllistä, kun tietyt sarakkeet, kuten tunnisteet tai olennaiset tiedot, on pidettävä näkyvissä, kun vieritellään vaakasuunnassa taulukon läpi.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnpinning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

Sarakkeelle on kolme saatavilla olevaa kiinnitysosuussuuntaa:

- `PinDirection.LEFT`: Kiinnittää sarakkeen vasemmalle puolelle.
- `PinDirection.RIGHT`: Kiinnittää sarakkeen oikealle puolelle.
- `PinDirection.AUTO`: Sarake ilmestyy lisäysjärjestyksen mukaan.

Kiinnitys voidaan asettaa ohjelmallisesti, jolloin voit muuttaa kiinnityssuuntaa käyttäjävuorovaikutusten tai sovelluksen logiikan mukaan.

## Sarakkeiden koko <DocChip chip='since' label='25.03' /> {#column-sizing} 

### Kiinteä leveys {#fixed-width}

Aseta tarkka leveys sarakkeelle käyttämällä `setWidth()`-metodia, määrittämällä haluttu leveys pikseleinä:

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

Leveysominaisuus määrittää halutun alkuperäisen leveyden sarakkeelle. Miten tätä leveyttä käytetään, riippuu muista ominaisuuksista ja saraketyypistä:

- **Tavalliset sarakkeet**: Kun vain leveys asetetaan, sarake renderöidään määritetyn leveydensä, mutta se voi kutistua suhteellisesti, kun säilö on liian pieni. Alkuperäinen leveys toimii haluttuna leveytenä, mutta ilman eksplisiittisiä vähimmäisrajoja sarake voi renderöidä itsensä pienemmäksi kuin asetettu leveys.
- [**Kiinnitetyt sarakkeet**](#pinning): Pitävät aina tarkan leveyden, eivätkä osallistu responsiiviseen kutistumiseen.
- [**Flex-sarakkeet**](#flex-sizing): Leveyden asettaminen ei ole sopusoinnussa flexin kanssa. Käytä joko leveyttä (kiinteä) tai flexiä (suhteellinen), älä molempia.

Jos ei ole määritetty, sarake käyttää arvioitua leveyttään, joka perustuu ensimmäisten rivien sisältöanalyysiin.

```java
// Hanki nykyinen leveys
float currentWidth = column.getWidth();
```

### Vähimmäisleveys {#minimum-width}

`setMinWidth()`-metodi sallii määrittää sarakkeen vähimmäisleveyden. Jos vähimmäisleveyttä ei anneta, `Table` laskee vähimmäisleveyden sarakkeen sisällön mukaan.

```java
table.addColumn("Hinta", Product::getPrice).setMinWidth(100f);
```

Annettu arvo edustaa vähimmäisleveyttä pikseleinä.

Vähimmäisleveysominaisuus hallitsee pienintä leveyttä, joka sarakkeella voi olla:

- **Tavalliset sarakkeet**: Kun vain vähimmäisleveys asetetaan, sarake käyttää vähimmäisleveyttä sekä haluttuna että vähimmäisleveytenä. Kun leveys + vähimmäisleveys, sarake voi kutistua leveydestä vähimmäisleveyteen, mutta ei siihen pidemmälle.
- [**Kiinnitetyt sarakkeet**](#pinning): Jos vain vähimmäisleveys asetetaan (ilman leveyttä), se muuttuu kiinteäksi leveydellä.
- [**Flex-sarakkeet**](#flex-sizing): Estää saraketta kutistumasta alle tämän leveyden, vaikka säilötilaa olisi rajoitetusti.

```java
// Hanki nykyinen vähimmäisleveys
float minWidth = column.getMinWidth();
```

### Enimmäisleveys {#maximum-width}

`setMaxWidth()`-metodi rajoittaa, kuinka leveä sarake voi kasvaa, estäen pitkän sisällön sisältäviä sarakkeita liian leveiltä ja heikentämästä luettavuutta:

```java
table.addColumn("Kuvaus", Product::getDescription)
    .setMinWidth(100f)
    .setMaxWidth(300f);
```

`maxWidth`-ominaisuus rajoittaa sarakkeen kasvua kaikille saraketyypeille, eikä sitä koskaan ylitetä sisällöstä, säilön koosta tai flex-asetuksista huolimatta.

```java
// Hanki nykyinen enimmäisleveys
float maxWidth = column.getMaxWidth();
```

### Flex-kokoaminen {#flex-sizing}

`setFlex()`-metodi mahdollistaa suhteellisen sarakkeen koon, jolloin sarakkeet jakavat käytettävissä olevaa tilaa kiinteiden leveysarvojen jälkeen:

```java
// Otsikkosarake saa kaksinkertaisen tilan kuin Artistisarake
table.addColumn("Otsikko", Product::getTitle).setFlex(2f);
table.addColumn("Artisti", Product::getArtist).setFlex(1f);
```

Tärkeät flex-käyttäytymiset:

- **Flex-arvo**: Määrittää käytettävissä olevan tilan osuuden. Sarake, jolla on flex=2, saa kaksi kertaa tilaa kuin sarakkeella, jolla on flex=1.
- **Yhteensopimattomuus leveyden kanssa**: Ei voi käyttää yhdessä leveyden ominaisuuden kanssa. Kun flex on suurempi kuin nolla, se vaikuttaa leveyden asetukseen.
- **Kunnioittaa rajoja**: Toimii vähimmäisleveys/enimmäisleveysrajojen kanssa. Ilman vähimmäisleveyttä flex-sarakkeet voivat kutistua nollaan.

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
Leveys- ja flex-ominaisuudet ovat keskenään poissulkevia. Yhden asettaminen tyhjentää automaattisesti toisen. Käytä leveyttä tarkalle hallinnalle tai flexiä responsiiviseen käyttäytymiseen.
:::

### Automaattinen kokoaminen {#automatic-sizing}

Manuaalisten leveys- ja flex-asetusten lisäksi sarakkeiden koot voivat myös olla automaattisia. Automaattinen kokoaminen antaa `Table`-komponentin määrittää optimaalisen leveyden joko analysoimalla sisältöä tai jakamalla tilaa suhteellisesti.

#### Sisältöön perustuva automaattinen kokoaminen {#content-based-auto-sizing}

Automatisoi sarakkeiden kokoaminen niiden sisällön mukaan. `Table` analysoi kunkin sarakkeen tiedot ja laskee optimaalisen leveyden sisällön näyttämiseksi ilman katkaisemista.

```java
// Automaattisesti kokoa kaikki sarakkeet mahtuakseen sisältöön
table.setColumnsToAutoSize().thenAccept(c -> {
    // Kokoaminen suoritettu - sarakkeet mahtuvat nyt sisältöön
});

// Automaattisesti kokoa tietty sarake
table.setColumnToAutoSize("kuvaus");
```

#### Suhteellinen automaattinen sovitus {#proportional-auto-fit}

Jaa kaikki sarakkeet suhteellisesti käytettävissä olevan `Table`-leveyden yli. Tämä operaatio asettaa jokaisen sarakkeen flex=1, jolloin ne jakavat kokonaisleveyden tasaisesti riippumatta niiden sisällön pituudesta. Sarakkeet laajenevat tai kutistuvat täyttääkseen tarkat `Table`-mitat ilman jäljellä olevaa tilaa.

```java
// Sovita sarakkeet taulukon leveyteen (vastaa flex=1 asettamista kaikille)
table.setColumnsToAutoFit().thenAccept(ignored -> {
    // Kaikki sarakkeet jakavat nyt tilaa yhtä lailla
});
```

:::info Asynkroniset toiminnot
Automaatiokootuottavat menetelmät palauttavat `PendingResult<Void>`-arvon, koska ne tarvitsevat asiakaspääden laskentaa. Käytä `thenAccept()` suorittaaksesi koodia, kun kokoamisen suoritus on valmis. Jos et tarvitse odottaa valmistumista, voit kutsua menetelmiä ilman `thenAccept()`
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

Sarakkeen koon muuttaminen antaa käyttäjille hallinta oikeuksista, kuinka paljon tilaa kukin sarake vie vetämällä sarakkeen reunoja.

Voit hallita koon muuttamiskäyttäytymistä yksittäisissä sarakkeissa rakentaessasi taulukkoasi:

```java
// Ota käyttöön käyttäjän koon muuttaminen tälle sarakkeelle
table.addColumn("Otsikko", Product::getTitle).setResizable(true);

// Poista koon muuttaminen käytöstä
table.addColumn("ID", Product::getId).setResizable(false);

// Tarkista nykytila
boolean canResize = column.isResizable();
```

Taulukoissa, joissa haluat johdonmukaisen käyttäytymisen useissa sarakkeissa, käytä suurten asetusten menetelmiä:

```java
// Tee kaikki olemassa olevat sarakkeet resizeableiksi
table.setColumnsToResizable(true);

// Lukitse kaikki olemassa olevat sarakkeet koon muuttamiselta
table.setColumnsToResizable(false);
```

### Sarakkeen uudelleenjärjestely {#column-reordering}

Sarakkeiden uudelleenjärjestely antaa käyttäjille mahdollisuuden raahata ja pudottaa sarakkeita heidän haluamaansa järjestykseen, mukauttamaan `Table`-asemointia työnkululleen.

Määritä sarakkeen liikuttamisluvat taulukkoasi luodessasi:

```java
// Salli käyttäjien siirtää tätä saraketta
table.addColumn("Otsikko", Product::getTitle).setMovable(true);

// Estä sarakkeen liikuttaminen (hyödyllistä ID- tai toimintasarakkeille)
table.addColumn("ID", Product::getId).setMovable(false);

// Tarkista nykytila
boolean canMove = column.isMovable();
```

Sovella liikkuvuusasetuksia useisiin sarakkeisiin samanaikaisesti:

```java
// Ota käyttöön uudelleenjärjestäminen kaikille olemassa oleville sarakkeille
table.setColumnsToMovable(true);

// Poista uudelleenjärjestäminen kaikilta olemassa olevilta sarakkeilta  
table.setColumnsToMovable(false);
```

:::note Suuret toiminnot
`setColumnsToResizable()`- ja `setColumnsToMovable()`-menetelmät vaikuttavat vain olemassa oleviin sarakkeisiin kutsuhetkellä. Ne eivät aseta oletuksia tuleville sarakkeille.
:::

### Ohjelmallinen sarakkeen liikuttaminen {#programmatic-column-movement} 

Raahauksen lisäksi voit siirtää sarakkeita ohjelmallisesti indeksin tai ID:n mukaan. Muista, että indeksi perustuu vain näkyviin sarakkeisiin; kaikki piilotetut sarakkeet jätetään huomiotta, kun lasketaan asemia.

```java
// Siirrä sarake ensimmäiseen asentoon
table.moveColumn("otsikko", 0);

// Siirrä sarake viimeiseen asentoon
table.moveColumn(titleColumn, table.getColumns().size() - 1);

// Asynkroninen liikuttaminen takaisinsoitolla
table.moveColumn("kuvaus", 2).thenAccept(c -> {
    // Sarake siirretty onnistuneesti
});
```

## Tapahtumien käsittely {#event-handling}

`Table`-komponentti lähettää tapahtumia, kun käyttäjät ovat vuorovaikutuksessa sarakkeiden kanssa, jolloin voit reagoida asettelun muutoksiin ja tallentaa käyttäjien asetuksia.

Tuettuja tapahtumia:

- `TableColumnResizeEvent`: Laukeaa, kun käyttäjä muuttaa sarakkeen kokoa vetämällä sen reunaa.
- `TableColumnMoveEvent`: Laukeaa, kun käyttäjä järjestää sarakkeen uudelleen vetämällä sen otsikkoa.

Voit liittää kuuntelijoita `Table`-komponenttiin reagoidaksesi, kun käyttäjät muokkaavat taulukon asettelua.

```java
Table<Product> table = new Table<>();

table.onColumnResize(event -> {
  // Käsittele sarakkeen koon muuttamis tapahtuma
  // Pääsy: event.getColumn(), event.getOldWidth(), event.getNewWidth()
});

table.onColumnMove(event -> {
  // Käsittele sarakkeen liikuttamistehtävä  
  // Pääsy: event.getColumn(), event.getOldIndex(), event.getNewIndex()
});
```
