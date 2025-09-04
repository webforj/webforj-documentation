---
title: Google Charts
sidebar_position: 50
_i18n_hash: b477c90cfb24a59329f3047d7ae7d24c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="google-chart" exclude='true' />
<DocChip chip='since' label='23.06' />
<JavadocLink type="googlecharts" location="com/webforj/component/googlecharts/GoogleChart" top='true'/>

<!-- Componentin lyhyt yleiskatsaus ja mitä se on/tekee -->

:::info Google Chartsin tuonti
Käytä `GoogleChart`-luokkaa sovelluksessasi seuraavalla XML:llä POM-tiedostossasi:

```xml
<dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-googlecharts</artifactId>
    <version>${webforj.version}</version>
</dependency>
```
:::

`GoogleChart`-luokka on kattava ratkaisu rikkaiden, interaktiivisten kaavioiden upottamiseen web-sovelluksiin. Tämä luokka toimii silkkana [Google Charts](https://developers.google.com/chart) -kirjastoon, tarjoten laajan valikoiman kaaviotyyppejä, jotka sopivat mihin tahansa datan visualisointitehtävään.

<ComponentDemo 
path='/webforj/chart?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartView.java'
cssURL='/css/googlecharts/chart.css'
height='300px'
/>

## Kaaviotyypit {#chart-types}

`GoogleChart`-lisäosa tarjoaa kattavan valikoiman kaaviotyyppejä erilaisten datan visualisointivaatimusten täyttämiseksi. Oikean kaaviotyypin valinta on olennaista, jotta datan tarina voidaan kommunikoida tehokkaasti. Katso alla olevasta galleriasta esimerkkejä yleisimmistä kaavioista, joita voidaan käyttää webforJ-sovelluksessa.

<ComponentDemo 
path='/webforj/chartgallery?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartGalleryView.java'
cssURL='/css/googlecharts/chartGallery.css'
height='600px'
/>

## Vaihtoehdot {#options}

`GoogleChart`-lisäosa mahdollistaa laajan persoonallisuuden erilaisilla vaihtoehdoilla. Nämä vaihtoehdot antavat sinun räätälöidä kaavioidesi ulkoasun ja toiminnallisuuden sovelluksesi tarpeisiin. Vaihtoehtoja välitetään kaavion `setOptions()`-menetelmään `Map<String, Object>`-rakenteena.

Esimerkki kaavion vaihtoehtojen asettamisesta:

```java
Map<String, Object> options = new HashMap<>();
options.put("title", "Kuukausittainen liikevaihto");
options.put("backgroundColor", "#EFEFEF");

// Käytä vaihtoehtoja kaavioon
chart.setOptions(options);
```

Lisätietoja tiettyjen kaavioiden käytettävissä olevista vaihtoehdoista löytyy [Google Visualization API -viittaus (Chart Gallery)](https://developers.google.com/chart/interactive/docs/gallery).

## Datan asettaminen {#setting-data}

Datalla visualisointi `GoogleChart`-kaavioilla vaatii datan oikeaa rakennetta ja asettamista. Tämä opas käynnistää sinut datasi valmistelussa ja sen soveltamisessa kaavioihisi.

### Perusdatan asetaminen {#basic-data-setup}

Yksinkertaisin tapa määrittää data on käyttää `List<Object>`, jossa kukin rivi on lista arvoista.

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("Tehtävä", "Tunteja päivässä"));
data.add(Arrays.asList("Työ", 11));
data.add(Arrays.asList("Syö", 2));
data.add(Arrays.asList("Matkusta", 2));
data.add(Arrays.asList("Katso TV", 2));
data.add(Arrays.asList("Nuku", 7));
chart.setData(data);
```

### Karttojen käyttäminen monimutkaisemmille rakenteille {#using-maps-for-more-complex-structures}

Monimutkaisempien tietorakenteiden osalta voit käyttää karttoja rivien esittämiseen ja sitten muuntaa ne tarvittavaan muotoon.

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

Kun data on valmisteltu, se voidaan soveltaa GoogleChartiin käyttämällä setData-menetelmää.

<ComponentDemo 
path='/webforj/chartsettingdata?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartSettingDataView.java'
height='300px'
/>

<!-- tabs={['ChartDemoSettingData.java']} -->

### Datan ja vaihtoehtojen lataaminen JSON:sta {#loading-data-and-options-from-json}

Voit myös ladata dataa ja vaihtoehtoja JSON-tiedostoista käyttäen Gsonia helpompaa hallintaa varten. Tämä lähestymistapa auttaa pitämään datan ja vaihtoehdot organisoituna ja helposti päivitettävänä.

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

## Kaavioiden visuaalien päivittäminen {#updating-chart-visuals}

Kaavioiden ulkoasun päivittäminen tai päivittäminen datan muutosten, käyttäjäinteraktioiden tai visuaalisten vaihtoehtojen säädön myötä on yksinkertaista `redraw()`-menetelmällä. Tämä menetelmä varmistaa, että kaaviosi pysyvät tarkkoina ja visuaalisesti linjassa taustadatansa tai minkä tahansa muutoksen kanssa niiden asetuksissa.

Kutsu `redraw()` seuraavissa tilanteissa:

- **Datamuutosten jälkeen**: Varmistaa, että kaavio heijastaa päivitykset sen datalähteeseen.
- **Vaihtoehtojen vaihtamisen yhteydessä**: Soveltaa uusia tyylimuutoksia tai asetuksia kaavioon.
- **Vastaavasti mukautettaessa**: Säädöt kaavion asettelua tai kokoa, kun säilön mitat muuttuvat, varmistaen optimaaliset näyttöasetukset laitteiden välillä.

<ComponentDemo 
path='/webforj/chartredraw?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartRedrawView.java'
cssURL='/css/googlecharts/chartRedraw.css'
height='650px'
/>

<!-- tabs={['ChartDemoRedraw.java', 'redrawchart_styles.css']} -->

## Kaavioiden vienti kuviksi {#exporting-charts-as-images}

`getImageUri()`-menetelmä tarjoaa tavan viedä Google Charts base64-koodattuina PNG-kuvina. Tämä menetelmä on erityisen hyödyllinen kaavioiden jakamiseen web-ympärön ulkopuolella, upottamiseen sähköposteihin tai asiakirjoihin tai yksinkertaisesti arkistointitarkoituksiin.

Kutsu `getImageUri()` kaavio-instanssillasi sen jälkeen, kun kaavio on täysin renderöity. Tyypillisesti tätä menetelmää käytetään "ready"-tapahtuman kuuntelijassa varmistaaksesi, että kaavio on valmis vientiin:

```java
chart.addReadyListener(e -> {
    String imageUri = chart.getImageUri();
    // Nyt voit käyttää imageUri, esimerkiksi img-tagin src-attribuutti
});
```

## `GoogleChartSelectedEvent` {#googlechartselectedevent}

`GoogleChartSelectedEvent` laukaisee aina, kun käyttäjä valitsee datan kohdan tai segmentin Google Chart -komponentissa. Tämä tapahtuma mahdollistaa vuorovaikutuksen valitun kaavion datan kanssa, tarjoten yksityiskohtia siitä, mitä on valittu. Tapahtumaan voi kuunnella käyttämällä `addSelectedListener()`-menetelmää `GoogleChart`-instanssilla.

`GoogleChartSelectedEvent` on hyödyllinen sovelluksissa, joissa käyttäjän vuorovaikutus kaavion kanssa on tarpeen. 

```java
GoogleChart chart = new GoogleChart(GoogleChart.Type.BAR);

// Lisää valittu kuuntelija kaavioon
chart.addSelectedListener(event -> {
    // Hanki valinta
    List<Object> selection = chart.getSelection();
    
    // Käsittele valittua tapahtumaa
    if (!selection.isEmpty()) {
        System.out.println("Valittu rivi: " + selection.get(0));
        // Lisäkäsittely valinnan rivin/sarakkeen perusteella
    }
});
```

### Payload {#payload}
`GoogleChartSelectedEvent` tarjoaa pääsyn valintadataan, joka voidaan hankkia käyttämällä `getSelection()`-menetelmää kaavio-objektista. Tämä menetelmä palauttaa listan objekteja, joissa jokaisessa objektissa on seuraavat ominaisuudet:

- **row**: Valitun rivin indeksi kaavion datataulukossa.
- **column**: Datan taulukon sarakkeen indeksi, joka on valinnainen ja koskee kaavioita, jotka sallivat yksittäisten solujen valinnan, kuten taulukko-kaavio.

Piemien kaavioiden, kuten piirakka- tai palkkikaavioiden, osalta ainoastaan `row`-ominaisuus on tyypillisesti saatavilla, mikä osoittaa valitun datakohdan.

Esimerkki payloadista:
```java
[
  {
    "row": 3,  // Valitun rivin indeksi datassa
    "column": 2  // (Valinnainen) Valitun sarakkeen indeksi
  }
]
```

:::info Useiden datakohtien valinta
Jos käyttäjä valitsee useita datakohtia, `getSelection()`-menetelmä palauttaa objektitaulukon, joka jokainen esittää valittua elementtiä. Payload voi vaihdella kaaviotyypin ja käyttäjän suorittaman vuorovaikutuksen mukaan.
:::
