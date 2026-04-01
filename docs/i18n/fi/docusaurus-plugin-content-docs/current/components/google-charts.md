---
title: Google Charts
sidebar_position: 50
_i18n_hash: 7421699c19919de6aab7db8a36123524
---
<DocChip chip='shadow' />
<DocChip chip='name' label="google-chart" exclude= 'true' />
<DocChip chip='since' label='23.06' />
<JavadocLink type="googlecharts" location="com/webforj/component/googlecharts/GoogleChart" top='true'/>

GoogleChart-komponentti integroi [Google Charts](https://developers.google.com/chart) -kirjaston webforJ:hen, antaen sinulle pääsyn kaaviotyyppeihin kuten palkki-, viiva-, piirakka-, geo- ja muihin. Kaaviot konfiguroidaan Javalla käyttämällä tyyppiä, datasarjaa ja vaihtoehtokarttaa, joka hallitsee ulkoasua ja toimintaa.

<!-- INTRO_END -->

## Luodaan kaavio {#creating-a-chart}

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

Luodaksesi kaavion määritä kaaviotyyppi, konfiguroi visuaaliset vaihtoehdot ja anna näytettävät tiedot.

Tässä esimerkissä luodaan geo-kaavio, joka kartoittaa tuloja eri maissa, mukautetuilla väreillä, legenda-asettelulla ja kaavioalueen koon säätöillä:

<ComponentDemo 
path='/webforj/chart?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartView.java'
cssURL='/css/googlecharts/chart.css'
height='300px'
/>

## Kaaviotyypit {#chart-types}

`GoogleChart`-lisäosa tarjoaa kattavan valikoiman kaaviotyyppejä erilaisia tietovisualisointitarpeita varten. Oikean kaaviotyypin valitseminen on tärkeää, jotta voi tehokkaasti välittää tiedon tarinan. Katso alla olevasta galleriasta esimerkkejä yleisistä kaavioista, joita voidaan käyttää webforJ-sovelluksessa.

<ComponentDemo 
path='/webforj/chartgallery?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartGalleryView.java'
cssURL='/css/googlecharts/chartGallery.css'
height='600px'
/>

## Vaihtoehdot {#options}

`GoogleChart`-lisäosa mahdollistaa laajan mukauttamisen erilaisten vaihtoehtojen avulla. Nämä vaihtoehdot antavat sinulle mahdollisuuden muokata kaavioiden ulkoasua ja toiminnallisuutta sovelluksesi tarpeiden mukaan. Vaihtoehdot välitetään `Map<String, Object>`-objektina kaavion `setOptions()`-menetelmälle. 

Tässä on esimerkki kaavion vaihtoehtojen asettamisesta:

```java
Map<String, Object> options = new HashMap<>();
options.put("title", "Kuukausittaiset tulot");
options.put("backgroundColor", "#EFEFEF");

// Ota vaihtoehdot käyttöön kaaviossa
chart.setOptions(options);
```

Lisätietoja erityisten kaavioiden käytettävissä olevista vaihtoehdoista löydät [Google Visualization API -viitteestä (Chart Gallery)](https://developers.google.com/chart/interactive/docs/gallery).

## Tietojen asettaminen {#setting-data}

Tietojen visualisoiminen `GoogleChart`-kaaviossa vaatii tietojen asianmukaista muotoilua ja asettamista. Tämä opas vie sinut läpi tiedon valmistelun ja soveltamisen kaavioihisi.

### Perusdatan valmistelu {#basic-data-setup}

Yksinkertaisin tapa määrittää tiedot on käyttää `List<Object>`-objektia, jossa jokainen rivi on lista arvoja.

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("Tehtävä", "Tunteja päivässä"));
data.add(Arrays.asList("Työ", 11));
data.add(Arrays.asList("Syö", 2));
data.add(Arrays.asList("Työmatka", 2));
data.add(Arrays.asList("Katso TV:tä", 2));
data.add(Arrays.asList("Nuku", 7));
chart.setData(data);
```

### Karttojen käyttö monimutkaisemmissa rakenteissa {#using-maps-for-more-complex-structures}

Monimutkaisempia tietorakenteita varten voit käyttää karttoja rivien edustamiseen ja muuntaa ne sitten vaadittuun muotoon.

```java
List<Object> data = new ArrayList<>();

// Otsikkorivi
data.add(Arrays.asList("Maa", "Tulot"));

// Tiedot rivit
Map<String, Object> row1 = Map.of("Maa", "Saksa", "Tulot", 1000);
Map<String, Object> row2 = Map.of("Maa", "Yhdysvallat", "Tulot", 1170);
Map<String, Object> row3 = Map.of("Maa", "Brasilia", "Tulot", 660);

data.add(new ArrayList<>(row1.values()));
data.add(new ArrayList<>(row2.values()));
data.add(new ArrayList<>(row3.values()));

chart.setData(data);
```

Kun tiedot on valmisteltu, ne voidaan asettaa GoogleChartin avulla setData-menetelmällä.

<ComponentDemo 
path='/webforj/chartsettingdata?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartSettingDataView.java'
height='300px'
/>

<!-- tabs={['ChartDemoSettingData.java']} -->

### Tietojen ja vaihtoehtojen lataaminen JSON:sta {#loading-data-and-options-from-json}

Voit myös ladata tietoja ja vaihtoehtoja JSON-tiedostoista käyttäen Gsonia helpompaa hallintaa varten. Tämä lähestymistapa auttaa pitämään tietosi ja vaihtoehtosi järjestyksessä ja helposti päivitettävissä.

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

## Kaavioiden visuaalisten ominaisuuksien päivittäminen {#updating-chart-visuals}

Kaavioiden ulkoasun päivittäminen tai päivittäminen tietomuutosten, käyttäjätapahtumien tai visuaalisten vaihtoehtojen säätöjen vastaisesti on yksinkertaista `redraw()`-menetelmällä. Tämä menetelmä varmistaa, että kaaviosi pysyvät tarkkoina ja visuaalisesti linjassa taustalla olevan datan tai asetusten muutosten kanssa.

Käytä `redraw()`-menetelmää sellaisissa tilanteissa, kuten:

- **Tietomuutosten jälkeen**: Varmistaa, että kaavio heijastaa muutoksia sen tietolähteessä.
- **Vaihtoehtojen muuttuessa**: Soveltaa uusia tyylit tai konfiguraatiomuutoksia kaavioon.
- **Joustavissa säädöissä**: Säätelee kaavion asettelua tai kokoa, kun säiliön mitat muuttuvat, varmistaen optimaalisen esityksen laitteiden välillä.

<ComponentDemo 
path='/webforj/chartredraw?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartRedrawView.java'
cssURL='/css/googlecharts/chartRedraw.css'
height='650px'
/>

<!-- tabs={['ChartDemoRedraw.java', 'redrawchart_styles.css']} -->

## Kaavioiden vieminen kuvina {#exporting-charts-as-images}

`getImageUri()`-menetelmä tarjoaa tavan viedä Google-kaaviosi base64-koodatuiksi PNG-kuviksi. Tämä menetelmä on erityisen hyödyllinen kaavioiden jakamisessa ulkopuolella verkkoympäristössä, upottamisessa sähköposteihin tai asiakirjoihin tai yksinkertaisesti arkistointitarkoituksiin.

Kutsu `getImageUri()`-menetelmää kaavio-instanssillasi kaavion oltua täysin renderöity. Tyypillisesti tätä menetelmää käytetään "valmiina" tapahtuman kuuntelijassa varmistaaksesi, että kaavio on valmis vientiä varten:

```java
chart.addReadyListener(e -> {
  String imageUri = chart.getImageUri();
  // Nyt voit käyttää imageUri:ta, esimerkiksi img-tagin src-attribuutti
});
```

## `GoogleChartSelectedEvent` {#googlechartselectedevent}

`GoogleChartSelectedEvent` käynnistetään aina, kun käyttäjä valitsee datapisteen tai segmentin Google Chart -komponentissa. Tämä tapahtuma mahdollistaa vuorovaikutuksen valitun kaaviotiedon kanssa, tarjoten tietoja valitusta. Tapahtumalle voidaan kuunnella käyttämällä `addSelectedListener()`-menetelmää `GoogleChart`-instanssilla.

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
    // Lisäkäsittely valinnan rivin/sarakkeen perusteella
  }
});
```

### Payload {#payload}
`GoogleChartSelectedEvent` tarjoaa pääsyn valintatietoihin, jotka voidaan noutaa `getSelection()`-menetelmällä kaavio-oliosta. Tämä menetelmä palauttaa listan objekteista, joista jokaisessa on seuraavat ominaisuudet:

- **row**: Rivin indeksi kaavion tietotaulukossa, joka oli valittuna.
- **column**: Sarakkeen indeksi tietotaulukossa, joka on valinnainen ja koskee kaavioita, jotka sallivat yksittäisten solujen valinnan, kuten taulukon kaavio.

Esimerkiksi piirakkakaavioissa tai palkkikaavioissa vain `row` yleensä annetaan, mikä osoittaa valitun datapisteen.

Esimerkki payloadista:
```java
[
  {
    "row": 3,  // Valitun rivin indeksi datassa
    "column": 2  // (Valinnainen) Valitun sarakkeen indeksi
  }
]
```

:::info Useiden datapisteiden valinta
Jos käyttäjä valitsee useita datapisteitä, `getSelection()`-menetelmä palauttaa objektimatriisin, joista jokainen esittää valittua elementtiä. Payload voidaan vaihdella kaaviotyypin ja käyttäjän tekemän vuorovaikutuksen mukaan.
:::
