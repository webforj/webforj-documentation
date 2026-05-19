---
sidebar_position: 5
title: Columns
slug: columns
_i18n_hash: fbae9063370715e9f6dc2cb490a27511
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

`Table`-luokka käyttää sarakeinstansseja määrittämään ja mukauttamaan, miten dataa näytetään. Sarakkeet hallitsevat, mitä dataa näytetään, miltä se näyttää ja miten käyttäjät voivat vuorovaikuttaa sen kanssa. Tässä sivussa käsitellään sarakkeen identiteettiä, esitystä, kokoa, käyttäjävuorovaikutuksia ja niihin liittyviä tapahtumia.

## Sarakkeen identiteetti {#column-identity}

Sarakkeen identiteetti määrittää, miten se tunnistetaan `Table`:ssa. Tämä sisältää sen etiketin, sen tarjoaman arvon ja sen, onko se näkyvä tai navigoitava.

### Etiketti {#label}

Sarakkeen etiketti on sen julkinen tunniste, joka auttaa selkeyttämään näytettäviä tietoja.  

Käytä `setLabel()` asettaaksesi tai muuttaaksesi etikettiä.

:::tip
Oletusarvoisesti etiketti on sama kuin sarakkeen ID.
:::

```java
table.addColumn("Tuote ID", Product::getProductId).setLabel("ID");
```

### Arvojen toimittajat {#value-providers}

Arvojen toimittaja on funktio, joka vastaa raakadatankääntämisestä taustadatasta muotoon, joka on sopiva näytettäväksi tietyssä sarakkeessa. Määrittämäsi funktio ottaa rivin datatyypin (T) instanssin ja palauttaa arvon, jota esitetään kyseisessä sarakkeessa tälle riville.

Voit asettaa arvojen toimittajan sarakkeelle käyttämällä yhtä `addColumn()`-menetelmistä `Table`-komponentissa.

Seuraavassa pätkässä sarake yrittää päästä käsiksi tietoon JSON-objektista, renderöiden sen vain, jos data ei ole null.

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

On mahdollista asettaa sarakkeen näkyvyys, määrittäen, näkyykö se `Table`:ssa vai ei. Tämä voi olla hyödyllistä esimerkiksi sensitiivisten tietojen näyttämisen päättämisessä. 

```java
table.addColumn("Luottokortti", Customer::getCreditCardNumber).setHidden(true);
```

### Navigoitava {#navigable}

Navigoitava-attribuutti määrittää, voivatko käyttäjät vuorovaikuttaa sarakkeen kanssa navigoinnin aikana. Asettamalla `setSuppressNavigable()` arvoksi true rajoitetaan käyttäjien vuorovaikutusta sarakkeen kanssa, tarjoten vain lukuoikeuden.

```java
table.addColumn("Vain luku -sarake", Product::getDescription).setSuppressNavigable(true);
```

## Ulkoasu ja muotoilu {#layout-and-formatting}

Kun sarakkeen identiteetti on määritetty, seuraava askel on hallita, miten sen sisältö näkyy käyttäjille. Ulkoasuvaihtoehdot, kuten kohdistus ja kiinnitys, määrittävät, mihin data sijoittuu ja miten se pysyy näkyvänä työskennellessäsi `Table`:n kanssa.

### Kohdistus {#alignment}

Sarakkeen kohdistuksen asettaminen antaa sinun luoda järjestettyjä tauluja, mikä voi auttaa käyttäjiä tunnistamaan eri osiot `Table`:ssa.

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

`Table`-komponentti tukee kolmea kohdistusvaihtoehtoa:

- `Column.Alignment.LEFT`: Sopii tekstuaaliseen tai kuvailevaan dataan, jossa vasemmanpuoleinen virta on intuitiivinen. Hyödyllinen, kun halutaan korostaa sisällön aloituspistettä.
- `Column.Alignment.CENTER`: Keskikohdistetut sarakkeet ovat ideaaleita lyhyille arvoille, kuten merkkiavain, tila tai muille, joilla on tasapainoinen esitys.
- `Column.Alignment.RIGHT`: Oikeakohdistettua saraketta voi harkita numeroarvoille, joita on hyödyllistä selata nopeaa, kuten päivämäriä, summia ja prosentteja.

Edellisessä esimerkissä viimeinen sarake `Kustannus` on oikeakohdistettu, jotta saadaan enemmän selkeää visuaalista eroa.

### Kiinnitys {#pinning}

Sarakkeen kiinnittäminen on ominaisuus, joka sallii käyttäjien kiinnittää tai "pinnata" sarakkeen tietyn puolen `Table`:ssa. Tämä on hyödyllistä, kun tietyt sarakkeet, kuten tunnisteet tai keskeiset tiedot, tulee pitää näkyvänä vaakasuoran vierittämisen aikana.

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

Sarakkeen kiinnittämiseen on kolme saatavilla olevaa suuntaa:

- `PinDirection.LEFT`: Kiinnittää sarakkeen vasemmalle puolelle.
- `PinDirection.RIGHT`: Kiinnittää sarakkeen oikealle puolelle.
- `PinDirection.AUTO`: Sarake näkyy lisäysjärjestyksen mukaan.

Kiinnitys voidaan asettaa ohjelmallisesti, jolloin voit muuttaa kiinnityssuuntaa käyttäjävuorovaikutusten tai sovelluksen logiikan perusteella.

## Sarakkeen koko <DocChip chip='since' label='25.03' /> {#column-sizing}

### Kiinteä leveys {#fixed-width}

Aseta tarkka leveys sarakkeelle käyttämällä `setWidth()`-metodia, määrittäen halutun leveyden pikseleinä:

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

Leveysominaisuus määrittää halutun alkuperäisen leveyden sarakkeelle. Miten tätä leveyttä käytetään, riippuu muista ominaisuuksista ja saraketyypistä:

- **Normaalit sarakkeet**: Ainoastaan leveys asetettuna, sarake renderöidään määritettyyn leveyteen, mutta se voi kutistua suhteellisesti, kun säiliö on liian pieni. Alkuperäinen leveys toimii haluttuna leveytenä, mutta ilman ilmeisiä minimirajoja sarake voi renderöityä pienemmäksi kuin asetettu leveys.
- [**Kiinnitetyt sarakkeet**](#pinning): Pitävät aina tarkan leveyden, eivätkä osallistu responsiiviseen kutistumiseen.
- [**Flex-sarakkeet**](#flex-sizing): Leveyden asettaminen on yhdistelemätöntä flexin kanssa. Käytä joko leveyttä (kiinteä) tai flexiä (suhteellinen), ei molempia.

Jos ei ole määritetty, sarake hyödyntää arvioitua leveyttään sisällön analyysin perusteella ensimmäisten muutaman rivin aikana.

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

Minimileveysominaisuus hallitsee pienintä leveyttä, joka sarakkeella voi olla:

- **Normaalit sarakkeet**: Vain minimileveys asetettuna, sarake käyttää minimileveyttä sekä haluttuna että minimileveytenä. Leveyden + minimileveyden kanssa sarake voi kutistua leveydestä minimileveyteen, mutta ei pidemmälle.
- [**Kiinnitetyt sarakkeet**](#pinning): Jos vain minimileveys on asetettu (ilman leveyttä), se muuttuu kiinteäksi leveysarvoksi.
- [**Flex-sarakkeet**](#flex-sizing): Estää saraketta kutistumasta tätä leveyttä pienemmäksi, vaikka säiliöt eivät olisi riittäviä.

```java
// Hanki nykyinen minimileveys
float minWidth = column.getMinWidth();
```

### Maksimileveys {#maximum-width}

`setMaxWidth()`-metodi rajoittaa, kuinka leveäksi sarake voi kasvaa, estäen sarakkeita, joilla on pitkiä sisältöjä, tulemasta liian leveiksi ja vaikuttamasta luettavuuteen:

```java
table.addColumn("Kuvaus", Product::getDescription)
  .setMinWidth(100f)
  .setMaxWidth(300f);
```

`maxWidth`-ominaisuus rajoittaa sarakkeen kasvua kaikille saraketyypeille, eikä sitä koskaan ylitetä riippumatta sisällöstä, säiliön koosta tai flex-asetuksista.

```java
// Hanki nykyinen maksimileveys
float maxWidth = column.getMaxWidth();
```

### Flex-kokoaminen {#flex-sizing}

`setFlex()`-metodi mahdollistaa suhteellisen sarakkeen koon, jolloin sarakkeet jakavat käytettävissä olevan tilan sen jälkeen, kun kiinteät leveydet on varattu:

```java
// Otsikkosarake saa kaksi kertaa tilaa kuin Artist-sarake
table.addColumn("Otsikko", Product::getTitle).setFlex(2f);
table.addColumn("Artist", Product::getArtist).setFlex(1f);
```

Keskeiset flex-toiminnot:

- **Flex-arvo**: Määrittää suhteellisen tilan jakamisen. Sarake, jolla on flex=2, saa kaksi kertaa tilaa kuin sarake, jolla on flex=1.
- **Yhteensopimattomuus leveyden kanssa**: Ei voi käyttää yhdessä leveyden kanssa. Kun flex on suurempi kuin nolla, se vaikuttaa leveyden asetukseen.
- **Kunnioittaa rajoja**: Toimii minimileveys/maksimileveys-rajat kanssa. Ilman minimileveyttä flex-sarakkeet voivat kutistua 0:aan.

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
Leveys- ja flex-ominaisuudet ovat toisensa poissulkevia. Toisen asettaminen tyhjentää automaattisesti toisen. Käytä leveyttä tarkkaan kontrolliin tai flexiä responsiiviseen käyttäytymiseen.
:::

### Automaattinen kokoaminen {#automatic-sizing}

Manuaalisten leveyden ja flex-asetusten lisäksi sarakkeet voidaan myös kokoa automaattisesti. Automaattinen kokoaminen mahdollistaa `Table`:n määrittää optimaalisen leveyden joko analysoimalla sisältöä tai jakamalla tilaa suhteellisesti.

#### Sisältöperusteinen automaattinen kokoaminen {#content-based-auto-sizing}

Kokoa sarakkeet automaattisesti niiden sisällön perusteella. `Table` analysoi tiedot kussakin sarakkeessa ja laskee optimaalisen leveyden, jotta sisältö ei katkeaisi.

```java
// Automaattisesti kokoa kaikki sarakkeet mahtumaan sisältöön
table.setColumnsToAutoSize().thenAccept(c -> {
  // Kokoaminen valmis - sarakkeet nyt mahtuvat sisältöön
});

// Automaattisesti kokoa tietty sarake
table.setColumnToAutoSize("kuvaus");
```

#### Suhteellinen automaattinen sopivuus {#proportional-auto-fit}

Jaa kaikki sarakkeet suhteellisesti käytettävissä olevan `Table`-leveyden mukaan. Tämä operaatio asettaa kunkin sarakkeen flex=1, jolloin ne jakavat koko `Table`-leveyden tasaisesti, riippumatta niiden sisällön pituudesta. Sarakkeet laajenevat tai kutistuvat täyttämään tarkan `Table`-mitan ilman jäljelle jäävää tilaa.

```java
// Sovi sarakkeet taulukon leveyteen (vastaa kaikkien flex=1 asettamista)
table.setColumnsToAutoFit().thenAccept(ignored -> {
  // Kaikki sarakkeet jakavat tilaa yhtä lailla
});
```

:::info Asynkroniset toiminnot
Automaattisen koon asetukset palauttavat `PendingResult<Void>`-arvon, koska ne vaativat asiakaspuolen laskentaa. Käytä `thenAccept()` suorittaaksesi koodia koon asettamisen jälkeen. Jos et tarvitse odottaa valmistumista, voit kutsua menetelmiä ilman `thenAccept()`-kutsuja.
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

### Sarakkeen koon säätäminen {#column-resizing}

Sarakkeen koon säätäminen antaa käyttäjille hallinnan siitä, kuinka paljon tilaa kukin sarake vie vetämällä sarakkeen rajoja.

Voit hallita koon säätökäyttäytymistä yksittäisissä sarakkeissa, kun rakennat taulukkoasi:

```java
// Ota käyttöön käyttäjien säädettävissä tämä sarake
table.addColumn("Otsikko", Product::getTitle).setResizable(true);

// Poista koon säätö käytöstä
table.addColumn("ID", Product::getId).setResizable(false);

// Tarkista nykyinen tila
boolean canResize = column.isResizable();
```

Taulukoissa, joissa haluat johdonmukaisen käyttäytymisen useiden sarakkeiden kesken, käytä suuria konfigurointimenetelmiä:

```java
// Tee kaikki olemassa olevat sarakkeet säädettävissä
table.setColumnsToResizable(true);

// Estä kaikkien olemassa olevien sarakkeiden säätö
table.setColumnsToResizable(false);
```

### Sarakkeen järjestäminen {#column-reordering}

Sarakkeen järjestäminen sallii käyttäjien vetää ja pudottaa sarakkeita mieluisaan järjestykseensä, personoiden `Table`-ulkoasua heidän työnkulkuaan varten.

Määritä sarakkeen liikkumislupa, kun asetat taulukkoasi:

```java
// Salli käyttäjien siirtää tämä sarake
table.addColumn("Otsikko", Product::getTitle).setMovable(true);

// Estä sarakkeen liikkuminen (hyödyllinen ID- tai toimintasarakkeille)
table.addColumn("ID", Product::getId).setMovable(false);

// Tarkista nykyinen tila
boolean canMove = column.isMovable();
```

Käytä liikkuvuusasetuksia useimmissa sarakkeissa samanaikaisesti:

```java
// Ota uudelleenjärjestäminen käyttöön kaikissa olemassa olevissa sarakkeissa
table.setColumnsToMovable(true);

// Estä uudelleenjärjestäminen kaikissa olemassa olevissa sarakkeissa  
table.setColumnsToMovable(false);
```

:::note Massatoiminnot
`setColumnsToResizable()`- ja `setColumnsToMovable()`-menetelmät vaikuttavat vain olemassa oleviin sarakkeisiin kutsuhetkellä. Ne eivät aseta oletuksia tuleville sarakkeille.
:::

### Ohjelmallinen sarakkeen liikkuminen {#programmatic-column-movement} 

Vedon ja pudotuksen lisäksi voit myös siirtää sarakkeita ohjelmallisesti indeksin tai ID:n perusteella. Muista, että indeksi perustuu vain näkyviin sarakkeisiin; kaikki piilotetut sarakkeet ohitetaan laskentaa laskettaessa.

```java
// Siirrä sarake ensimmäiseen sijaintiin
table.moveColumn("otsikko", 0);

// Siirrä sarake viimeiseen sijaintiin
table.moveColumn(titleColumn, table.getColumns().size() - 1);

// Asynkroninen liikkuminen palautteen kanssa
table.moveColumn("kuvaus", 2).thenAccept(c -> {
  // Sarake siirretty onnistuneesti
});
```

## Tapahtumankäsittely {#event-handling}

`Table`-komponentti lähettää tapahtumia, kun käyttäjät vuorovaikuttavat sarakkeiden kanssa, mikä mahdollistaa vastaamisen ulkoasu muutoksiin ja käyttäjäasetusten tallentamiseen.

Tuetut tapahtumat:

- `TableColumnResizeEvent`: Laudattiin, kun käyttäjä säätää saraketta vetämällä sen rajaa.
- `TableColumnMoveEvent`: Laudattiin, kun käyttäjä järjestää saraketta vetämällä sen otsikkoa.

Voit liittää kuuntelijoita `Table`-komponenttiin reagointiin, kun käyttäjät muokkaavat taulukon ulkoasua.

```java
Table<Product> table = new Table<>();

table.onColumnResize(event -> {
  // Käsittele sarakkeen koon säätö tapahtuma
  // Pääsy: event.getColumn(), event.getOldWidth(), event.getNewWidth()
});

table.onColumnMove(event -> {
  // Käsittele sarakkeen siirtotapahtuma  
  // Pääsy: event.getColumn(), event.getOldIndex(), event.getNewIndex()
});
```
