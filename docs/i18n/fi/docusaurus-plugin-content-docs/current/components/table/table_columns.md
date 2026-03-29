---
sidebar_position: 5
title: Columns
slug: columns
_i18n_hash: 19fe294c57ad6b7d105039c25aedab11
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

Luokka `Table` käyttää sarakeinstansseja määrittämään ja mukauttamaan, kuinka tiedot näytetään. Sarakkeet ohjaavat, mitkä tiedot näkyvät, miltä ne näyttävät ja miten käyttäjät voivat vuorovaikuttaa niiden kanssa. Tämä sivu käsittelee sarakkeen identiteettiä, esittämistä, kokoa, käyttäjävuorovaikutuksia ja liittyviä tapahtumia.

## Sarakkeen identiteetti {#column-identity}

Sarakkeen identiteetti määrittelee, miten se tunnistetaan `Table`-komponentissa. Tämä sisältää sen nimen, sen tarjoaman arvon sekä sen, onko se näkyvissä tai navigoitavissa.

### Nimi {#label}

Sarakkeen nimi on sen julkinen tunnistaja, jonka avulla tiedot selkeytyvät.  

Käytä `setLabel()`-metodia nimen asettamiseen tai muokkaamiseen.

:::tip
Oletusarvoisesti nimi on sama kuin sarakkeen ID.
:::

```java
table.addColumn("Tuotteen ID", Product::getProductId).setLabel("ID");
```

### Arvontuottajat {#value-providers}

Arvontuottaja on funktio, joka on vastuussa raakadatasta peräisin olevan tiedon muuntamisesta esityskelpoiseksi muodoksi tiettyä saraketta varten. Määrittelemäsi funktio ottaa vastaan rivejä kuvaavan datatyypin (T) instanssin ja palauttaa arvon, joka esitetään kyseisessä sarakkeessa kyseiselle riville.

Aseta arvontuottaja sarakkeelle käyttämällä jotain `addColumn()`-metodista `Table`-komponentissa.

Seuraavassa koodipätkässä sarake yrittää käyttää tietoja JSON-objektista, renderöiden sen vain, jos tiedot eivät ole null.

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

On mahdollista määrittää sarakkeen näkyvyys, mikä vaikuttaa siihen, näytetäänkö se `Table`-komponentissa vai ei. Tämä voi olla hyödyllistä, kun esimerkiksi määritetään, näytetäänkö arkaluontoista tietoa.

```java
table.addColumn("Luottokortti", Customer::getCreditCardNumber).setHidden(true);
```

### Navigoitavuus {#navigable}

Navigoitavuus-attribuutti määrittää, voivatko käyttäjät vuorovaikuttaa sarakkeen kanssa navigoinnin aikana. Asettaessasi `setSuppressNavigable()` arvoksi true rajoitat käyttäjien vuorovaikutusta sarakkeen kanssa, tarjoten vain lukuoikeudet.

```java
table.addColumn("Vain luku -sarake", Product::getDescription).setSuppressNavigable(true);
```

## Asettelu ja muotoilu {#layout-and-formatting}

Kun sarakkeen identiteetti on määritetty, seuraava askel on hallita, miten sen sisältö näkyy käyttäjille. Asetteluoption, kuten kohdistuksen ja pinnauksen, avulla voidaan määrätä, missä tiedot sijaitsevat ja miten se pysyy näkyvissä työskennellessäsi `Table`-komponentin kanssa.

### Kohdistus {#alignment}

Sarakkeen kohdistaminen antaa sinun luoda järjestäytyneitä tauluja, mikä voi auttaa käyttäjiä tunnistamaan `Table`-komponentin eri osiot.

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

- `Column.Alignment.LEFT`: Sopii tekstuaalisten tai kuvailevien tietojen esittämiseen, missä vasemmalle kohdistaminen on luontevaa. Hyödyllinen, kun halutaan korostaa sisällön alkuosaa.
- `Column.Alignment.CENTER`: Keskitetyt sarakkeet ovat ihanteellisia lyhyille arvoille, kuten merkkiavaimille, statuksille tai mille tahansa muulle, jolla on tasapainoinen esitys.
- `Column.Alignment.RIGHT`: Oikealle kohdistettua saraketta kannattaa käyttää numeerisille arvoille, joita on helppo skannata, kuten päivämäärille, summille ja prosenteille.

Edellyttävässä esimerkissä `Cost`-sarakkeelle on asetettu oikea kohdistus, jotta saavutetaan näkyvä erottelu.

### Pinnaus {#pinning}

Sarakkeen pinnaaminen on toiminto, joka antaa käyttäjien kiinnittää sarakkeen tietyyn kohtaan `Table`-komponentissa. Tämä on hyödyllistä, kun tiettyjen sarakkeiden, kuten tunnistimien tai olennaisten tietojen, tulee pysyä näkyvissä vaakasuorassa vierityksessä taulua.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnpinning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

Sarake voidaan kiinnittää kolmeen eri suuntaan:

- `PinDirection.LEFT`: Kiinnittää sarakkeen vasempaan reunaan.
- `PinDirection.RIGHT`: Kiinnittää sarakkeen oikeaan reunaan.
- `PinDirection.AUTO`: Sarake näkyy syöttöjärjestyksen mukaan.

Pinnaus voidaan asettaa ohjelmallisesti, jolloin voit muuttaa pinnaussuunnan käyttäjävuorovaikutusten tai sovelluksen logiikan mukaan.

## Sarakkeen koko <DocChip chip='since' label='25.03' /> {#column-sizing} 

### Kiinteä leveys {#fixed-width}

Aseta tarkka leveys sarakkeelle käyttämällä `setWidth()`-metodia, määrittäen toivotun leveyden pikseleinä:

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

Leveysominaisuus määrittelee sarakkeen alkuperäisen toivotun leveyden. Tämä leveys vaikuttaa muihin ominaisuuksiin ja saraketyypiin:

- **Säännölliset sarakkeet**: Pelkästään leveys asetettuna, sarake renderöidään määritellyssä leveydessä, mutta se voi kutistua suhteessa, kun säiliö on liian pieni. Alkuperäinen leveys toimii toivottuna leveytenä, mutta ilman nimenomaisia vähimmäisrajoja sarake voi renderöityä pienemmäksi kuin asetettu leveys.
- [**Pinnatut sarakkeet**](#pinning): Säilyttävät aina tarkat mitat etkä osallistu responsiiviseen kutistumiseen.
- [**Flex-sarakkeet**](#flex-sizing): Leveyden asettaminen on yhteensopimaton flexin kanssa. Käytä joko leveyttä (kiinteä) tai flexiä (suhteellinen), ei molempia.

Jos leveys ei ole määritelty, sarake käyttää sen arvioitua leveyttä perustuen ensimmäisten rivejä koskevaan analyysiin.

```java
// Hae nykyinen leveys
float currentWidth = column.getWidth();
```

### Vähimmäisleveys {#minimum-width}

Metodi `setMinWidth()` antaa sinun määrittää sarakkeen vähimmäisleveyden. Jos vähimmäisleveyttä ei ole annettu, `Table` laskee vähimmäisleveyden sarakkeen sisällön mukaan.

```java
table.addColumn("Hinta", Product::getPrice).setMinWidth(100f);
```

Annettu arvo edustaa vähimmäisleveyttä pikseleinä.

Vähimmäisleveysominaisuus ohjaa sarakkeen pienintä mahdollista leveyttä:

- **Säännölliset sarakkeet**: Pelkästään vähimmäisleveys asetettuna, sarake käyttää vähimmäisleveyttä sekä toivottuna että vähimmäisleveytenä. Leveys + vähimmäisleveys - yhdistelmässä sarake voi kutistua leveydestä vähimmäisleveyteen, mutta ei sen alle.
- [**Pinnatut sarakkeet**](#pinning): Jos vain vähimmäisleveys on asetettu (ilman leveyttä), siitä tulee kiinteä leveys.
- [**Flex-sarakkeet**](#flex-sizing): Estää sarakkeen kutistumisen alle tämän leveyden, jopa kun säiliön tila on rajallinen.

```java
// Hae nykyinen vähimmäisleveys
float minWidth = column.getMinWidth();
```

### Maksimileveys {#maximum-width}

Metodi `setMaxWidth()` rajoittaa, kuinka leveäksi sarake voi kasvaa, estäen pitkän sisällön sisältäviä sarakkeita tulemasta liian leveiksi ja vaikuttamasta luettavuuteen:

```java
table.addColumn("Kuvaus", Product::getDescription)
  .setMinWidth(100f)
  .setMaxWidth(300f);
```

`maxWidth` -ominaisuus rajoittaa sarakkeen kasvua kaikille saraketypeille eikä sitä ylitetä koskaan riippumatta sisällöstä, säiliön koosta tai flex-asetuksista.

```java
// Hae nykyinen maksimi leveys
float maxWidth = column.getMaxWidth();
```

### Flex-kokoaminen {#flex-sizing}

Metodi `setFlex()` mahdollistaa suhteellisen sarakkeen koon, jolloin sarakkeet jakavat käytettävissä olevaa tilaa kiinteäleveysten sarakkeiden jälkeen:

```java
// Otsikkosarake saa kaksi kertaa enemmän tilaa kuin Taiteilijasarake
table.addColumn("Otsikko", Product::getTitle).setFlex(2f);
table.addColumn("Taiteilija", Product::getArtist).setFlex(1f);
```

Keskeisiä flex-ominaisuuksia:

- **Flex-arvo**: Määrittää käytettävän tilan osan. Sarake, jolla on flex=2, saa kaksi kertaa enemmän tilaa kuin sarake, jolla on flex=1.
- **Yhteensopimaton leveyden kanssa**: Ei voi käyttää yhdessä leveysominaisuuden kanssa. Kun flex > 0, se vaikuttaa leveysasetukseen.
- **Kunnioittaa rajoja**: Työskentelee vähimmäis- ja enimmäisleveysrajojen kanssa. Ilman vähimmäisleveyttä flex-sarakkeet voivat kutistua nollaan.

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
Leveys- ja flex-ominaisuudet ovat toisiaan poissulkevia. Yhden asettaminen tyhjentää automaattisesti toisen. Käytä leveyttä tarkkaan hallintaan tai flexiä responsiiviseen käyttäytymiseen.
:::

### Automaattinen kokoaminen {#automatic-sizing}

Manuaalisten leveys- ja flex-asetusten lisäksi sarakkeita voidaan myös kokoaa automaattisesti. Automaattinen kokoaminen antaa `Table`-komponentin määrittää optimaalisen leveyden joko sisältöä analysoimalla tai jakamalla tilaa suhteellisesti.

#### Sisällön perusteella automaattinen kokoaminen {#content-based-auto-sizing}

Sarakkeet voidaan automaattisesti kooa niiden sisällön perusteella. `Table` analysoi jokaisen sarakkeen tiedot ja laskee optimaalisen leveyden sisällön näyttämiseksi ilman katkaisemista.

```java
// Automaattinen kokoaminen kaikille sarakkeille sopimaan sisältöön
table.setColumnsToAutoSize().thenAccept(c -> {
  // Kokoaminen valmis - sarakkeet sopivat nyt sisältöönsä
});

// Automaattinen kokoaminen tietyälle sarakkeelle
table.setColumnToAutoSize("kuvaus");
```

#### Suhteellinen automaattinen kokoaminen {#proportional-auto-fit}

Jakaa kaikki sarakkeet suhteellisesti käytettävissä olevan `Table` leveyden yli. Tämä operaatio asettaa jokaisen sarakkeen flex=1, jolloin ne jakavat kokonaisleveyden yhtä lailla riippumatta sisällön pituudesta. Sarakkeet laajenevat tai supistuvat täyttämään tarkat `Table` mitat ilman ylimääräistä tilaa.

```java
// Aseta sarakkeet sopimaan taulun leveyteen (vastaa setting flex=1 kaikille)
table.setColumnsToAutoFit().thenAccept(ignored -> {
  // Kaikki sarakkeet jakavat nyt tilan yhtä lailla
});
```

:::info Asynkroniset toiminnot
Automaattiseen kokoamiseen liittyvät metodit palauttavat `PendingResult<Void>` -tyypin, koska ne vaativat asiakaspuolen laskentaa. Käytä `thenAccept()`-metodia suorittaaksesi koodia, kun kokoaminen on valmis. Jos et tarvitse odottaa valmistumista, voit kutsua metodeja ilman `thenAccept()`-metodia.
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

### Sarakkeen koon muutos {#column-resizing}

Sarakkeen koon muutos antaa käyttäjille hallinnan siihen, kuinka paljon tilaa jokainen sarake vie, vetämällä sarakkeen rajoja.

Voit hallita koon muutoskäyttäytymistä yksittäisissä sarakkeissa taulua rakentaessasi:

```java
// Ota käyttöön käyttäjän muutosmahdollisuus tälle sarakkeelle
table.addColumn("Otsikko", Product::getTitle).setResizable(true);

// Estä koon muutos
table.addColumn("ID", Product::getId).setResizable(false);

// Tarkista nykyinen tila
boolean canResize = column.isResizable();
```

Tauluissa, joissa haluat yhdenmukaista käyttäytymistä useissa sarakkeissa, käytä massamääräysmenetelmiä:

```java
// Tee kaikki olemassa olevat sarakkeet muokattaviksi
table.setColumnsToResizable(true);

// Lukitse kaikki olemassa olevat sarakkeet koon muutokselta
table.setColumnsToResizable(false);
```

### Sarakkeiden järjestyksen muuttaminen {#column-reordering}

Sarakkeiden järjestyksen muuttaminen antaa käyttäjille mahdollisuuden vetää ja pudottaa sarakkeita haluamaansa järjestykseen, mukauttaen `Table`-komponentin asettelua työnkulkuunsa.

Määritä sarakemuutto-oikeudet taulua rakentaessasi:

```java
// Salli käyttäjien liikkua tässä sarakkeessa
table.addColumn("Otsikko", Product::getTitle).setMovable(true);

// Estä sarakkeen liikuttaminen (hyödyllinen ID- tai toimintasarake)
table.addColumn("ID", Product::getId).setMovable(false);

// Tarkista nykyinen tila
boolean canMove = column.isMovable();
```

Voit soveltaa liikkuvuusasetuksia useisiin sarakkeisiin samanaikaisesti:

```java
// Ota käyttöön uudelleensijoittaminen kaikille olemassa oleville sarakkeille
table.setColumnsToMovable(true);

// Estä uudelleensijoittaminen kaikille olemassa oleville sarakkeille  
table.setColumnsToMovable(false);
```

:::note Massatoiminnot
`setColumnsToResizable()` ja `setColumnsToMovable()` -menetelmät vaikuttavat vain olemassa oleviin sarakkeisiin kutsuhetkellä. Ne eivät määritä oletuksia tuleville sarakkeille.
:::

### Ohjelmallinen sarakeliikuttaminen {#programmatic-column-movement} 

Vetäminen ja pudottaminen -sarakkeiden lisäksi voit myös siirtää sarakkeita ohjelmallisesti indeksin tai ID:n perusteella. Pidä mielessä, että indeksi perustuu vain näkyviin sarakkeisiin; piilotetut sarakkeet jätetään huomiotta sijaintia laskettaessa.

```java
// Siirrä sarake ensimmäiseen kohtaan
table.moveColumn("otsikko", 0);

// Siirrä sarake viimeiseen kohtaan
table.moveColumn(titleColumn, table.getColumns().size() - 1);

// Asynkroninen siirto callbackilla
table.moveColumn("kuvaus", 2).thenAccept(c -> {
  // Sarake siirretty onnistuneesti
});
```

## Tapahtumien käsittely {#event-handling}

`Table`-komponentti lähettää tapahtumia, kun käyttäjät vuorovaikuttavat sarakkeiden kanssa, jolloin voit reagoida asettelun muutoksiin ja tallentaa käyttäjäasetuksia.

Tuetut tapahtumat:

- `TableColumnResizeEvent`: Lähetetään, kun käyttäjä muuttaa sarakkeen kokoa vetämällä sen reunaa.
- `TableColumnMoveEvent`: Lähetetään, kun käyttäjä järjestää sarakkeen uudelleen vetämällä sen otsikkoa.

Voit liittää kuuntelijoita `Table`-komponenttiin vastataksesi, kun käyttäjät muokkaavat taulukon asettelua.

```java
Table<Product> table = new Table<>();

table.onColumnResize(event -> {
  // Käsittele sarakkeen koon muutos tapahtuma
  // Pääsy: event.getColumn(), event.getOldWidth(), event.getNewWidth()
});

table.onColumnMove(event -> {
  // Käsittele sarakkeen liikkeen tapahtuma  
  // Pääsy: event.getColumn(), event.getOldIndex(), event.getNewIndex()
});
```
