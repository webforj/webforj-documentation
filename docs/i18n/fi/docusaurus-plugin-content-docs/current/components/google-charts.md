---
title: Google Charts
sidebar_position: 50
description: >-
  Render bar, line, pie, geo, and other Google Charts in webforJ using the
  GoogleChart component with a typed Java options map and data API.
_i18n_hash: a733a52b4d9ffb87eae039e9729b9cb9
---
<DocChip chip='shadow' />
<DocChip chip='name' label="google-chart" exclude= 'true' />
<DocChip chip='since' label='23.06' />
<JavadocLink type="googlecharts" location="com/webforj/component/googlecharts/GoogleChart" top='true'/>

`GoogleChart` -komponentti integroi [Google Charts](https://developers.google.com/chart) -kirjaston webforJ:hen, mikä antaa sinulle pääsyn erilaisiin kaarotyyppiin, kuten palkki-, viiva-, piirakka-, geo- ja muihin. Kaaviot konfiguroidaan Javalla käyttäen tyyppiä, datasarjaa ja vaihtoehtokarttaa, joka hallitsee ulkonäköä ja käyttäytymistä.

<!-- INTRO_END -->

## Creating a chart {#creating-a-chart}

:::info Google Chartsin tuonti
Käytä `GoogleChart` -luokkaa sovelluksessasi, käyttämällä seuraavaa XML:ää POM-tiedostossasi:

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-googlecharts</artifactId>
  <version>${webforj.version}</version>
</dependency>
```
:::

Kaavion luomiseksi määritä kaaviotyyppi, konfiguroi sen visuaaliset asetukset ja anna näytettävä data.

Tässä esimerkissä luodaan geo-kaavio, joka kartoittaa tulotietoja eri maista, mukautetuilla väreillä, legenda-asetuksilla ja kaavioalueen koon säädöillä:

<ComponentDemo
path='/webforj/chart'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartView.java',
  'src/main/frontend/css/googlecharts/chart.css',
]}
height='300px'
/>

## Chart types {#chart-types}

`GoogleChart` -lisäosa tarjoaa kattavan valikoiman kaaviotyyppejä, jotka sopivat erilaisiin datan visualisointitarpeisiin. Oikean kaaviotyypin valitseminen on olennaista datan tarinan tehokkaaksi välittämiseksi. Katso alla olevaa galleriaa esimerkkejä yleisistä kaavioista, joita voidaan käyttää webforJ-sovelluksessa.

<ComponentDemo
path='/webforj/chartgallery'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartGalleryView.java',
  'src/main/frontend/css/googlecharts/chartGallery.css',
]}
height='600px'
/>

## Options {#options}

`GoogleChart` -lisäosa mahdollistaa laajan mukauttamisen erilaisten vaihtoehtojen avulla. Nämä vaihtoehdot antavat mahdollisuuden räätälöidä kaavioiden ulkonäkö ja toiminnallisuus sovelluksesi tarpeiden mukaan. Vaihtoehdot välitetään `Map<String, Object>` -rakenteena kaavion `setOptions()` -menetelmälle.

Tässä on esimerkki kaavion vaihtoehtojen asettamisesta:

```java
Map<String, Object> options = new HashMap<>();
options.put("title", "Kuukausittaiset tulot");
options.put("backgroundColor", "#EFEFEF");

// Sovita vaihtoehdot kaavioon
chart.setOptions(options);
```

Lisätietoja tiettyjen kaavioiden käytettävissä olevista vaihtoehdoista löydät [Google Visualization API -viitteestä (Chart Gallery)](https://developers.google.com/chart/interactive/docs/gallery).

## Setting data {#setting-data}

Datamateriaalin visualisointi `GoogleChart` -komponentilla edellyttää datan asianmukaista muotoilua ja asettamista. Tämä opas käynnistää sinut valmistamaan datasi ja soveltamaan sitä kaavioihisi.

### Basic data setup {#basic-data-setup}

Yksinkertaisin tapa määrittää data on käyttää `List<Object>` -rakennetta, jossa jokainen rivi on lista arvoja.

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("Tehtävä", "Tuntia päivässä"));
data.add(Arrays.asList("Työ", 11));
data.add(Arrays.asList("Syö", 2));
data.add(Arrays.asList("Työmatka", 2));
data.add(Arrays.asList("Katso televisiota", 2));
data.add(Arrays.asList("Nuku", 7));
chart.setData(data);
```

### Using maps for more complex structures {#using-maps-for-more-complex-structures}

Monimutkaisempia datarakenteita varten voit käyttää karttoja rivien esittämiseen ja muuntaa ne sitten vaadittuun muotoon.

```java
List<Object> data = new ArrayList<>();

// Otsikkorivi
data.add(Arrays.asList("Maa", "Tulot"));

// Datarivit
Map<String, Object> row1 = Map.of("Maa", "Saksa", "Tulot", 1000);
Map<String, Object> row2 = Map.of("Maa", "Yhdysvallat", "Tulot", 1170);
Map<String, Object> row3 = Map.of("Maa", "Brasilia", "Tulot", 660);

data.add(new ArrayList<>(row1.values()));
data.add(new ArrayList<>(row2.values()));
data.add(new ArrayList<>(row3.values()));

chart.setData(data);
```

Kun data on valmisteltu, se voidaan soveltaa GoogleChartille käyttämällä setData-menetelmää.

<ComponentDemo
path='/webforj/chartsettingdata'
files={['src/main/java/com/webforj/samples/views/googlecharts/ChartSettingDataView.java']}
height='300px'
/>

### Loading data and options from JSON {#loading-data-and-options-from-json}

Voit myös ladata dataa ja vaihtoehtoja JSON-tiedostoista käyttämällä Gsonia helppoa hallintaa varten. Tämä lähestymistapa auttaa pitämään datasi ja vaihtoehtosi järjestettyinä ja helposti päivitettävissä.

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("Vuosi", "Myynti", "Kustannukset"));
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

## Updating chart visuals {#updating-chart-visuals}

Kaavioiden ulkoasun päivittäminen tai päivittäminen datan muutosten, käyttäjäinteraktioiden tai visuaalisten vaihtoehtojen säätöjen seurauksena on helppoa `redraw()`-menetelmällä. Tämä menetelmä varmistaa, että kaavioisi pysyvät tarkkoina ja visuaalisesti linjassa taustatietojen tai asetusten muutosten kanssa.

Käytä `redraw()`-menetelmää seuraavissa tilanteissa:

- **Datan muokkauksen jälkeen**: Varmistaa, että kaavio heijastaa kaiken päivityksen datalähteessään.
- **Vaihtoehtojen muuttamisen yhteydessä**: Sovittaa uudet tyylit tai konfiguraatio muutokset kaavioon.
- **Reaktiiviset säädöt**: Säädä kaavion asettelu tai koko, kun säiliön mitat muuttuvat, varmistaen optimaalisen näyttämisen eri laitteilla.

<ComponentDemo
path='/webforj/chartredraw'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartRedrawView.java',
  'src/main/frontend/css/googlecharts/chartRedraw.css',
]}
height='650px'
/>

## Exporting charts as images {#exporting-charts-as-images}

`getImageUri()` -menetelmä tarjoaa tavan viedä Google-kaavioita base64-koodatuina PNG-kuvina. Tämä menetelmä on erityisen hyödyllinen kaavioiden jakamiseen verkkoympäristön ulkopuolella, niiden upottamiseen sähköposteihin tai asiakirjoihin tai yksinkertaisesti arkistointitarkoituksessa.

Kutsu `getImageUri()` kaaviotilanteesi jälkeen, kun kaavio on täysin rendattu. Tyypillisesti tätä menetelmää käytetään "valmiina" tapahtumankuuntelijassa varmistaaksesi, että kaavio on valmis vientiä varten:

```java
chart.addReadyListener(e -> {
  String imageUri = chart.getImageUri();
  // Nyt voit käyttää imageUri, esimerkiksi img-tagin src-ominaisuutena
});
```

## `GoogleChartSelectedEvent` {#googlechartselectedevent}

`GoogleChartSelectedEvent` -tapahtuma laukaistaan, kun käyttäjä valitsee datan pisteen tai segmentin Google Chart -komponentissa. Tämä tapahtuma mahdollistaa vuorovaikutuksen valitun kaaviotiedon kanssa, tarjoten tietoa siitä, mitä on valittu. Tapahtumaa voidaan kuunnella käyttämällä `addSelectedListener()` -menetelmää `GoogleChart`-instanssilla.

`GoogleChartSelectedEvent` on hyödyllinen sovelluksissa, joissa käyttäjän vuorovaikutus kaavion kanssa on tarpeen.

```java
GoogleChart chart = new GoogleChart(GoogleChart.Type.BAR);

// Lisää valittu kuuntelija kaavioon
chart.addSelectedListener(event -> {
  // Hanki valinta
  List<Object> selection = chart.getSelection();

  // Käsittele valittu tapahtuma
  if (!selection.isEmpty()) {
    System.out.println("Valittu rivi: " + selection.get(0));
    // Jatko käsittely valinnan rivin/kolumnin perusteella
  }
});
```

### Payload {#payload}
`GoogleChartSelectedEvent` tarjoaa pääsyn valintatietoihin, jotka voidaan noutaa käyttäen `getSelection()` -menetelmää kaavio-objektissa. Tämä menetelmä palauttaa listan objekteista, joista jokaisella on seuraavat ominaisuudet:

- **row**: Valitun rivin indeksi kaavion datataulussa.
- **column**: Datataulun sarakkeen indeksi, joka on valinnainen ja koskee kaavioita, jotka sallivat yksittäisten solujen valinnan, kuten taulukko kaaviot.

Kuten piirakka- tai palkkikaavioissa, vain `row`-tieto on tyypillisesti saatavilla, joka osoittaa valitun datapisteen.

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
Jos käyttäjä valitsee useita datapisteitä, `getSelection()` -menetelmä palauttaa objektien taulukon, jotka kukin esittävät valittua elementtiä. Payload voi vaihdella kaaviotyypin ja käyttäjän suorittaman vuorovaikutuksen mukaan.
:::
