---
title: Google Charts
sidebar_position: 50
_i18n_hash: 31a5912850ae78f116c6738b99910d25
---
<DocChip chip='shadow' />
<DocChip chip='name' label="google-chart" exclude= 'true' />
<DocChip chip='since' label='23.06' />
<JavadocLink type="googlecharts" location="com/webforj/component/googlecharts/GoogleChart" top='true'/>

The `GoogleChart` komponentti integroi [Google Charts](https://developers.google.com/chart) kirjaston webforJ:hin, antaen pääsyn kaaviotyyppeihin kuten palkki, viiva, piirakka, geo ja muita. Kaaviot konfiguroidaan Javalla käyttäen tyyppiä, tietojoukkoa ja vaihtoehtokarttaa, joka hallitsee ulkoasua ja käyttäytymistä.

<!-- INTRO_END -->

## Luominen kaavio {#creating-a-chart}

:::info Google Chartsin tuominen
Käytettäväksesi `GoogleChart`-luokkaa sovelluksessasi, käytä seuraavaa XML:ää POM-tiedostossasi:

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-googlecharts</artifactId>
  <version>${webforj.version}</version>
</dependency>
```
:::

Kaavion luomiseksi, määritä kaaviotyyppi, konfiguroi visuaaliset vaihtoehdot ja tarjoa näytettävä data.

Tässä esimerkissä luodaan geo-kaavio, joka kartoittaa liikevaihtotiedot eri maissa, mukautettujen värien, legendan asettelun ja kaavioalueen koon kanssa:

<ComponentDemo
path='/webforj/chart'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartView.java',
  'src/main/resources/static/css/googlecharts/chart.css',
]}
height='300px'
/>

## Kaaviotyypit {#chart-types}

`GoogleChart`-lisäosa tarjoaa kattavan valikoiman kaaviotyyppejä erilaisille tietovizualisaatio vaatimuksille. Sopivan kaaviotyypin valinta on olennaista, jotta tiedon tarina voidaan viestiä tehokkaasti. Katso alla olevaa galleriaa esimerkkejä yleisistä kaavioista, joita voidaan käyttää webforJ-sovelluksessa.

<ComponentDemo
path='/webforj/chartgallery'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartGalleryView.java',
  'src/main/resources/static/css/googlecharts/chartGallery.css',
]}
height='600px'
/>

## Vaihtoehdot {#options}

`GoogleChart`-lisäosa mahdollistaa laajan mukauttamisen eri vaihtoehtojen avulla. Nämä vaihtoehdot antavat sinun räätälöidä kaavioiden ulkoasun ja toiminnallisuuden sovelluksesi tarpeita varten. Vaihtoehtoja välitetään `Map<String, Object>` muodossa kaavion `setOptions()`-menetelmään.

Tässä esimerkki kaavion vaihtoehtojen asettamisesta:

```java
Map<String, Object> options = new HashMap<>();
options.put("title", "Kuukausittainen liikevaihto");
options.put("backgroundColor", "#EFEFEF");

// Käytä vaihtoehtoja kaaviossa
chart.setOptions(options);
```

Lisätietoja saatavilla olevista vaihtoehdoista tietyille kaavioille, katso [Google Visualization API viite (Kaaviogalleria)](https://developers.google.com/chart/interactive/docs/gallery).

## Datan asettaminen {#setting-data}

Datan visualisoiminen `GoogleChart`-kaaviolla edellyttää datan oikeaa rakennetta ja asettamista. Tämä opas käy läpi datan valmistelun ja sen soveltamisen kaavioihisi.

### Perusdatan asettaminen {#basic-data-setup}

Yksinkertaisin tapa määritellä data on käyttää `List<Object>`, missä jokainen rivi on lista arvoja.

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("Tehtävä", "Tunteja päivässä"));
data.add(Arrays.asList("Työ", 11));
data.add(Arrays.asList("Syö", 2));
data.add(Arrays.asList("Kulje", 2));
data.add(Arrays.asList("Katso TV", 2));
data.add(Arrays.asList("Nuku", 7));
chart.setData(data);
```

### Karttojen käyttö monimutkaisemmille rakenteille {#using-maps-for-more-complex-structures}

Monimutkaisempia tietorakenteita varten voit käyttää karttoja rivien esittämiseen ja muuntaa ne sitten vaadittuun muotoon.

```java
List<Object> data = new ArrayList<>();

// Otsikkorivi
data.add(Arrays.asList("Maa", "Liikevaihto"));

// Datarivit
Map<String, Object> row1 = Map.of("Maa", "Saksa", "Liikevaihto", 1000);
Map<String, Object> row2 = Map.of("Maa", "Yhdysvallat", "Liikevaihto", 1170);
Map<String, Object> row3 = Map.of("Maa", "Brasilia", "Liikevaihto", 660);

data.add(new ArrayList<>(row1.values()));
data.add(new ArrayList<>(row2.values()));
data.add(new ArrayList<>(row3.values()));

chart.setData(data);
```

Kun data on valmisteltu, se voidaan asettaa GoogleChart-kaavioon `setData`-menetelmällä.

<ComponentDemo
path='/webforj/chartsettingdata'
files={['src/main/java/com/webforj/samples/views/googlecharts/ChartSettingDataView.java']}
height='300px'
/>

### Datan ja vaihtoehtojen lataaminen JSON:sta {#loading-data-and-options-from-json}

Voit myös ladata dataa ja vaihtoehtoja JSON-tiedostoista käyttäen Gsonia helpomman hallinnan vuoksi. Tämä lähestymistapa auttaa pitämään datan ja vaihtoehdot järjestyksessä sekä helposti päivitettävänä.

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("Vuosi", "Myynti", "Kulut"));
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

## Kaavioiden visuaalien päivittäminen {#updating-chart-visuals}

Kaavioidesi ulkoasun päivittäminen tai uudistaminen datan muutosten, käyttäjävuorovaikutusten tai visuaalisten vaihtoehtojen sääntöjen mukaisesti on suoraviivaista `redraw()`-menetelmän avulla. Tämä menetelmä varmistaa, että kaaviosi pysyvät tarkkoina ja visuaalisesti kohdistettuina taustadatan tai asetusten muutoksille.

Kutsu `redraw()` esimerkiksi seuraavissa tilanteissa:

- **Datan muutosten jälkeen**: Varmistaa, että kaavio heijastaa kaikki päivitykset sen tietolähteeseen.
- **Vaihtoehtojen muuttuessa**: Soveltaa uusia tyylittely- tai konfigurointimuutoksia kaavioon.
- **Vastaaviin säätöihin**: Säädä kaavion asettelua tai kokoa, kun säilön mitat muuttuvat, varmistaen optimaalisen näyttämisen eri laitteilla.

<ComponentDemo
path='/webforj/chartredraw'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartRedrawView.java',
  'src/main/resources/static/css/googlecharts/chartRedraw.css',
]}
height='650px'
/>

## Kaavioiden vieminen kuvina {#exporting-charts-as-images}

`getImageUri()`-menetelmä tarjoaa tavan viedä Google Charts Base64-koodattujen PNG-kuvina. Tämä menetelmä on erityisen hyödyllinen kaavioiden jakamisessa web-ympärön ulkopuolella, niiden upottamisessa sähköposteihin tai asiakirjoihin tai yksinkertaisesti arkistointitarkoituksiin.

Kutsu `getImageUri()` kaavioinstanssisi jälkeen, kun kaavio on täysin renderöity. Tyypillisesti tätä menetelmää käytetään "valmis" tapahtuman kuuntelijassa varmistaaksesi, että kaavio on valmis vientiin:

```java
chart.addReadyListener(e -> {
  String imageUri = chart.getImageUri();
  // Nyt voit käyttää imageUri, esimerkiksi img-tagina src-attribuuttina
});
```

## `GoogleChartSelectedEvent` {#googlechartselectedevent}

`GoogleChartSelectedEvent` laukaisee aina, kun käyttäjä valitsee datan pisteen tai segmentin Google Chart -komponentissa. Tämä tapahtuma mahdollistaa vuorovaikutuksen valitun kaavidatan kanssa ja antaa tietoja siitä, mitä on valittu. Tapahtumaan voidaan reagoida käyttämällä `addSelectedListener()`-menetelmää `GoogleChart`-instanssilla.

`GoogleChartSelectedEvent` on hyödyllinen sovelluksissa, joissa käyttäjän vuorovaikutus kaavion kanssa on tarpeen.

```java
GoogleChart chart = new GoogleChart(GoogleChart.Type.BAR);

// Lisää valitsemisen kuuntelija kaavioon
chart.addSelectedListener(event -> {
  // Hanki valinta
  List<Object> selection = chart.getSelection();
  
  // Käsittele valittua tapahtumaa
  if (!selection.isEmpty()) {
    System.out.println("Valittu rivi: " + selection.get(0));
    // Lisäprosessi valinnan rivin/kolumnin perusteella
  }
});
```

### Payload {#payload}
`GoogleChartSelectedEvent` tarjoaa pääsyn valintadataksi, joka voidaan hakea kaavio-objektin `getSelection()`-menetelmällä. Tämä menetelmä palauttaa listan objekteista, joissa jokaisessa objektissa on seuraavat ominaisuudet:

- **row**: Valitun rivin indeksi kaavion tietotaulukossa.
- **column**: Tietotaulukon kolumnin indeksi, joka on valinnainen ja pätee kaavioille, jotka sallivat yksittäisten solujen valinnan, kuten taulukko kaavio.

Esimerkiksi piirakka- tai palkkikaavioissa tarjoillaan yleensä vain `row`, mikä osoittaa valittua datakohtaa.

Esimerkki kuormasta:
```java
[
  {
    "row": 3,  // Valitun rivin indeksi datassa
    "column": 2  // (Valinnainen) Valitun kolumnin indeksi
  }
]
```

:::info Monien datakohtien valinta
Jos käyttäjä valitsee useita datakohtia, `getSelection()`-menetelmä palauttaa taulukon objekteista, joista kukin edustaa valittua elementtiä. Kuorma voi vaihdella kaaviotyypin ja käyttäjän suorittaman vuorovaikutuksen mukaan.
:::
