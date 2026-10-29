---
title: Google Charts
sidebar_position: 50
description: >-
  Render bar, line, pie, geo, and other Google Charts in webforJ using the
  GoogleChart component with a typed Java options map and data API.
_i18n_hash: 8e1916e526ef45c55ca75c6cd32a4e7f
---
<DocChip chip='shadow' />
<DocChip chip='name' label="google-chart" exclude= 'true' />
<DocChip chip='since' label='23.06' />
<JavadocLink type="googlecharts" location="com/webforj/component/googlecharts/GoogleChart" top='true'/>

`GoogleChart`-komponentti integroidaan [Google Charts](https://developers.google.com/chart) -kirjasto webforJ:heen, ja se antaa pääsyn kaaviotyyppeihin, kuten palkki-, viiva-, piirakka-, geo- ja moniin muihin. Kaavioita konfiguroidaan Java-koodilla tyypin, datan ja vaihtoehtojen avulla, jotka hallitsevat ulkoasua ja käyttäytymistä.

<!-- INTRO_END -->

## Kaavion luominen {#creating-a-chart}

:::info Google Chartsin tuominen
Käyttääksesi `GoogleChart`-luokkaa sovelluksessasi, käytä seuraavaa XML:ää POM-tiedostossasi:

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-googlecharts</artifactId>
  <version>${webforj.version}</version>
</dependency>
```
:::

Luodaksesi kaavion, määritä kaaviotyyppi, konfiguroi sen visuaaliset vaihtoehdot ja anna näytettävä data.

Tässä esimerkissä luodaan geo-kaavio, joka kuvastaa tuloja eri maissa, mukautetuilla väreillä, legenda-asettelulla ja kaavioalueen koon määrittelyllä:

<ComponentDemo
path='/webforj/chart'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartView.java',
  'src/main/frontend/css/googlecharts/chart.css',
]}
height='300px'
/>

## Kaaviotyypit {#chart-types}

`GoogleChart`-lisäosa tarjoaa kattavan valikoiman kaaviotyyppejä, jotka sopivat erilaisiin datavisualisointitarpeisiin. Oikean kaaviotyypin valitseminen on olennaista tiedon kertomuksen tehokkaaksi viestimiseksi. Katso alla oleva galleria esimerkkejä yleisistä kaavioista, joita voidaan käyttää webforJ-sovelluksessa.

<ComponentDemo
path='/webforj/chartgallery'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartGalleryView.java',
  'src/main/frontend/css/googlecharts/chartGallery.css',
]}
height='600px'
/>

## Vaihtoehdot {#options}

`GoogleChart`-lisäosa mahdollistaa laajan mukauttamisen erilaisten vaihtoehtojen avulla. Nämä vaihtoehdot antavat sinun räätälöidä kaavioidesi ulkoasua ja toiminnallisuutta sovelluksesi tarpeiden mukaisiksi. Vaihtoehtoja siirretään `Map<String, Object>`-tyyppinä kaavion `setOptions()`-metodille.

Tässä on esimerkki kaavion vaihtoehtojen asettamisesta:

```java
Map<String, Object> options = new HashMap<>();
options.put("title", "Kuukausittaiset tulot");
options.put("backgroundColor", "#EFEFEF");

// Sovita vaihtoehdot kaavioon
chart.setOptions(options);
```

Lisätietoja kaavioita varten saatavilla olevista vaihtoehdoista löytyy [Google Visualization API viittauksesta (Chart Gallery)](https://developers.google.com/chart/interactive/docs/gallery).

## Datan asettaminen {#setting-data}

Datavisualisointi `GoogleChart`in avulla vaatii datan asianmukaista rakentamista ja asettamista. Tämä opas vie sinut läpi datasi valmistelun ja sen soveltamisen kaavioihisi.

### Perusdatan määrittäminen {#basic-data-setup}

Yleisin tapa määrittää data on käyttää `List<Object>`-tietotyyppiä, jossa jokainen rivi on lista arvoja.

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("Tehtävä", "Tunnit päivässä"));
data.add(Arrays.asList("Työ", 11));
data.add(Arrays.asList("Syö", 2));
data.add(Arrays.asList("Matkustus", 2));
data.add(Arrays.asList("TV:n katsominen", 2));
data.add(Arrays.asList("Uni", 7));
chart.setData(data);
```

### Karttojen käyttö monimutkaisemmissa rakenteissa {#using-maps-for-more-complex-structures}

Monimutkaisempia tietorakenteita varten voit käyttää karttoja rivien esittämiseen ja muuntaa ne sitten vaadittuun muotoon.

```java
List<Object> data = new ArrayList<>();

// Otsikkorivi
data.add(Arrays.asList("Maa", "Tulokset"));

// Datarivit
Map<String, Object> row1 = Map.of("Maa", "Saksa", "Tulokset", 1000);
Map<String, Object> row2 = Map.of("Maa", "Yhdysvallat", "Tulokset", 1170);
Map<String, Object> row3 = Map.of("Maa", "Brasilia", "Tulokset", 660);

data.add(new ArrayList<>(row1.values()));
data.add(new ArrayList<>(row2.values()));
data.add(new ArrayList<>(row3.values()));

chart.setData(data);
```

Kun data on valmisteltu, se voidaan soveltaa GoogleChartille käyttäen setData-metodia.

<ComponentDemo
path='/webforj/chartsettingdata'
files={['src/main/java/com/webforj/samples/views/googlecharts/ChartSettingDataView.java']}
height='300px'
/>

### Datan ja vaihtoehtojen lataaminen JSON:sta {#loading-data-and-options-from-json}

Voit myös ladata dataa ja vaihtoehtoja JSON-tiedostoista käyttämällä Gsonia helpottamaan hallintaa. Tämä lähestymistapa auttaa pitämään datan ja vaihtoehdot järjestettyinä ja helposti päivitettävinä.

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("Vuosi", "Myynnit", "Kulut"));
data.add(Arrays.asList("2013", 1000, 400));
data.add(Arrays.asList("2014", 1170, 460));
data.add(Arrays.asList("2015", 660, null));
data.add(Arrays.asList("2016", 1030, 540));
chart.setData(data);

Map<String, Object> options = new Gson().fromJson(
  Assets.contentOf("options.json"),
  new TypeToken<Map<String, Object>>() {}.getType()
);
chart.setOptions(options);
```

## Kaavion visuaalien päivittäminen {#updating-chart-visuals}

`GoogleChart` päivittyy automaattisesti, kun sen data, vaihtoehdot, tyyppi tai valinta muuttuu. Sinun ei tarvitse kutsua `redraw()`-metodia käytettäessä `setData()`, `setOptions()`, `setType()`, tai `setSelection()`.

Komponentti kuuntelee myös selainikkunan koon muutoksia ja piirtää itsensä automaattisesti uuteen kokoon.

Kutsu `redraw()`, kun säiliön koko muuttuu, mutta se ei laukaise ikkuna koon muutosta. Esimerkkejä ovat paneelin laajentaminen ja sivupalkin koon muuttaminen. Kutsu se sen jälkeen, kun säiliön uudet mitat on otettu käyttöön.

Seuraava esimerkki päivittää kaavion dataa `setData()`:n avulla. Kaavio piirtää itsensä automaattisesti, kun uusi data on sovellettu.

<ComponentDemo
path='/webforj/chartredraw'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartRedrawView.java',
  'src/main/frontend/css/googlecharts/chartRedraw.css',
]}
height='650px'
/>

## Kaavioiden vienti kuvina {#exporting-charts-as-images}

`getImageUri()`-metodi tarjoaa tavan viedä Google Kaaviosi base64-koodattuna PNG-kuvana. Tämä metodi on erityisen hyödyllinen kaavioiden jakamiseen verkkoympäristön ulkopuolella, niiden upottamiseen sähköposteihin tai asiakirjoihin, tai yksinkertaisesti arkistointitarkoituksiin.

Kutsu `getImageUri()`-metodia kaavio-instanssillesi sen jälkeen, kun kaavio on täysin renderöity. Tyypillisesti tätä metodia käytetään "ready" -tapahtuman kuuntelijassa varmistaaksesi, että kaavio on valmis vientiä varten:

```java
chart.addReadyListener(e -> {
  String imageUri = chart.getImageUri();
  // Nyt voit käyttää imageUri-arvoa, esimerkiksi img-tagin src-attribuuttina
});
```

## `GoogleChartSelectedEvent` {#googlechartselectedevent}

`GoogleChartSelectedEvent` laukeaa aina, kun käyttäjä valitsee datan pisteen tai segmentin Google Chart -komponentissa. Tämä tapahtuma mahdollistaa vuorovaikutuksen valitun kaavion datan kanssa, tarjoamalla tietoja valinnasta. Tapahtumaa voidaan kuunnella käyttämällä `addSelectedListener()`-metodia `GoogleChart`-instanssilla.

`GoogleChartSelectedEvent` on hyödyllinen sovelluksissa, joissa käyttäjän vuorovaikutus kaavion kanssa on tarpeen.

```java
GoogleChart chart = new GoogleChart(GoogleChart.Type.BAR);

// Lisää valintakuuntelija kaavioon
chart.addSelectedListener(event -> {
  // Hanki valinta
  List<Object> selection = chart.getSelection();

  // Käsittele valintatapahtuma
  if (!selection.isEmpty()) {
    System.out.println("Valittu rivi: " + selection.get(0));
    // Edelleen käsittely valinnan rivin/ylemmän mukaan
  }
});
```

### Payload {#payload}
`GoogleChartSelectedEvent` antaa pääsyn valintadataan, joka voidaan noutaa käyttäen `getSelection()`-metodia kaavio-objektilla. Tämä metodi palauttaa listan objekteja, joissa jokaisella objektilla on seuraavat ominaisuudet:

- **row**: Valitun rivin indeksi kaavion datataulukossa.
- **column**: Datan taulukon sarakkeen indeksi, mikä on valinnainen ja koskee kaavioita, jotka mahdollistavat yksittäisten solujen valinnan, kuten taulukko-kaavio.

Kaavioissa, kuten piirakka-kaavioissa tai palkka-kaavioissa, vain `row` annetaan tyypillisesti, mikä osoittaa valitun datapisteen.

Tässä on esimerkki payloadista:
```java
[
  {
    "row": 3,  // Valitun rivin indeksi datassa
    "column": 2  // (Valinnainen) Valitun sarakkeen indeksi
  }
]
```

:::info Useiden datapisteiden valinta
Jos käyttäjä valitsee useita datapisteitä, `getSelection()`-metodi palauttaa objektien taulukon, kukin edustaen valittua elementtiä. Payload voi vaihdella kaaviotyypin ja käyttäjän suorittaman vuorovaikutuksen perusteella.
:::
